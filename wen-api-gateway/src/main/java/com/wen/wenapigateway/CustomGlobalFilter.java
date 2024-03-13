package com.wen.wenapigateway;

import com.wen.wenapiclient.util.SignUtil;
import com.wen.wenapicommon.model.domain.User;
import com.wen.wenapicommon.service.InnerUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    @Resource
    private InnerUserService innerUserService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //  1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + request.getPath());
        log.info("请求方法：" + request.getMethod());
        log.info("服务器的 IP 地址：" + request.getLocalAddress());
        String remoteAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        log.info("客户端的主机号：" + remoteAddress);
        log.info("请求参数：" + request.getQueryParams());
        //  2. 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(remoteAddress)) {
            return handleNoAuth(response);
        }
        //  3. 用户鉴权
        // 从请求头中获取参数信息,先判断 aK 和 sK
        HttpHeaders headers = response.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
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
        final long FIVE_MINUTES = 60 * 5L;
        if (timeStamp == null || (currentTime - Long.parseLong(timeStamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        //  4. 请求的模拟接口是否存在
        //  f. 请求转发，调用模拟接口
        //  g. 响应日志
        //  h. 调用成功，接口调用次数 + 1
        //  i. 调用失败，返回一个规范的错误码
        log.info("custom global filter");
        return chain.filter(exchange);
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

    @Override
    public int getOrder() {
        return -1;
    }
}

