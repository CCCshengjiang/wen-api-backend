package com.wen.wenapigateway;

import com.wen.wenapiclient.util.SignUtil;
import com.wen.wenapicommon.model.domain.InterfaceInfo;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.service.InnerInterfaceInfoService;
import com.wen.wenapicommon.service.InnerUserInterfaceInfoService;
import com.wen.wenapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 全局过滤器
 *
 * @author wen
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = List.of("127.0.0.1");

    @DubboReference
    private InnerUserService userService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService userInterfaceInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //  1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识：" + request.getId());
        String url = request.getPath().toString();
        log.info("请求路径：" + url);
        String method = request.getMethod().toString();
        log.info("请求方法：" + method);
        log.info("服务器的 IP 地址：" + request.getLocalAddress());
        String remoteAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        log.info("客户端的主机号：" + remoteAddress);
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        log.info("请求参数：" + queryParams);
        //  2. 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(remoteAddress)) {
            return handleNoAuth(response);
        }
        //  3. 用户鉴权
        // 从请求头中获取参数信息,先判断 aK 和 sK
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        User invokeUser;
        try {
            invokeUser = userService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser 获取参数信息异常" + e.getMessage());
            return handleNoAuth(response);
        }
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        String serveSign = SignUtil.getSign(body, invokeUser.getSecretKey());
        if (sign == null || !sign.equals(serveSign)) {
            return handleNoAuth(response);
        }
        // 再判断随机数不能超过十万，时间戳不能超过当前时间五分钟（防止刷量）
        String nonce = headers.getFirst("nonce");
        if (nonce == null || Long.parseLong(nonce) > 100000L) {
            return handleNoAuth(response);
        }
        String timeStamp = headers.getFirst("timeStamp");
        long currentTime = System.currentTimeMillis() / 1000;
        final long fiveMinutes = 60 * 5L;
        if (timeStamp == null || (currentTime - Long.parseLong(timeStamp)) >= fiveMinutes) {
            return handleNoAuth(response);
        }
        //  4. 请求的模拟接口是否存在
        InterfaceInfo interfaceInfo;
        try {
            interfaceInfo = interfaceInfoService.getInterfaceInfo(url, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo 请求的模拟接口异常" + e.getMessage());
            return handleInvokeError(response);
        }
        // 调用模拟接口
        //  5. 请求转发，调用模拟接口
        try {
            // 调用成功，接口调用次数 + 1
            userInterfaceInfoService.invokeCount(interfaceInfo.getId(), invokeUser.getId());
        } catch (Exception e) {
            log.error("invokeCount 调用接口操作异常" + e.getMessage());
            return handleInvokeError(response);
        }
        // 请求转发 + 响应日志
        return handleResponse(exchange, chain);
    }


    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatusCode statusCode = originalResponse.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                //降级处理返回数据
                return chain.filter(exchange);
            }
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            // 合并多个流集合，解决返回体分段传输
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer buff = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[buff.readableByteCount()];
                            buff.read(content);
                            DataBufferUtils.release(buff);//释放掉内存
                            //排除Excel导出，不是application/json不打印。若请求是上传图片则在最上面判断。
                            MediaType contentType = originalResponse.getHeaders().getContentType();
                            if (!MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                                return bufferFactory.wrap(content);
                            }
                            // 构建返回日志
                            String joinData = new String(content);
                            List<Object> rspArgs = new ArrayList<>();
                            rspArgs.add(statusCode.value());
                            rspArgs.add(exchange.getRequest().getURI());
                            rspArgs.add(joinData);
                            log.info("<-- {} {}\n{}", rspArgs.toArray());
                            getDelegate().getHeaders().setContentLength(joinData.getBytes().length);
                            return bufferFactory.wrap(joinData.getBytes());
                        }));
                    } else {
                        log.error("<-- {} 响应code异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            return chain.filter(exchange.mutate().response(decoratedResponse).build());

        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

    /**
     * 无权限调用接口通用返回类
     *
     * @param response 请求信息
     * @return 无权限调用接口信息
     */
    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    /**
     * 调用接口失败返回类
     *
     * @param response 请求信息
     * @return 接口调用失败（500）
     */
    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

