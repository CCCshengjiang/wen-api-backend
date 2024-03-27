package com.wen.wenapiproject.util;

import com.wen.wenapicommon.common.BaseCode;
import com.wen.wenapicommon.exception.BusinessException;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.BaseCodec;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Java 分布式锁工具类
 *
 * @author wen
 */
@Component
public class RedissonLockUtil {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 分布式锁
     *
     * @param lockName 锁的名称
     * @param supplier 要执行的操作
     * @param baseCode 错误码
     * @param errorMessage 错误信息
     * @return 操作结果
     * @param <T> 泛型
     */
    public <T> T redissonDistributedLock(String lockName, Supplier<T> supplier,
                                         BaseCode baseCode, String errorMessage) {
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (rLock.tryLock(0, 8, TimeUnit.SECONDS)) {
                return supplier.get();
            }
            throw new BusinessException(baseCode, errorMessage);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new BusinessException(BaseCode.INTERNAL_ERROR, e.getMessage());
        }finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }

    }

}
