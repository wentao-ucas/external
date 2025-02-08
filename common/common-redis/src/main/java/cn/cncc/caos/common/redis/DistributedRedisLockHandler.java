package cn.cncc.caos.common.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class DistributedRedisLockHandler {

    private static final Logger logger = LoggerFactory.getLogger(DistributedRedisLockHandler.class);
    private final static long LOCK_EXPIRE = 30 * 1000L;//单个业务持有锁的时间30s，防止死锁
    private final static long LOCK_TRY_INTERVAL = 30L;//默认30ms尝试一次
    private final static long LOCK_TRY_TIMEOUT = 20 * 1000L;//默认尝试20s

    @Autowired
    private StringRedisTemplate template;

    /**
     * 尝试获取全局锁
     *
     * @param redisLock 锁的名称
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(RedisLock redisLock) {
        return getLock(redisLock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param redisLock    锁的名称
     * @param timeout 获取超时时间 单位ms
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(RedisLock redisLock, long timeout) {
        return getLock(redisLock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param redisLock        锁的名称
     * @param timeout     获取锁的超时时间
     * @param tryInterval 多少毫秒尝试获取一次
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(RedisLock redisLock, long timeout, long tryInterval) {
        return getLock(redisLock, timeout, tryInterval, LOCK_EXPIRE);
    }

    /**
     * 尝试获取全局锁
     *
     * @param redisLock           锁的名称
     * @param timeout        获取锁的超时时间
     * @param tryInterval    多少毫秒尝试获取一次
     * @param lockExpireTime 锁的过期
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(RedisLock redisLock, long timeout, long tryInterval, long lockExpireTime) {
        return getLock(redisLock, timeout, tryInterval, lockExpireTime);
    }


    /**
     * 操作redis获取全局锁
     *
     * @param redisLock           锁的名称
     * @param timeout        获取的超时时间
     * @param tryInterval    多少ms尝试一次
     * @param lockExpireTime 获取成功后锁的过期时间
     * @return true 获取成功，false获取失败
     */
    public boolean getLock(RedisLock redisLock, long timeout, long tryInterval, long lockExpireTime) {
        try {
            if (StringUtils.isEmpty(redisLock.getName()) || StringUtils.isEmpty(redisLock.getValue())) {
                return false;
            }
            long startTime = System.currentTimeMillis();
            do{
                if (!template.hasKey(redisLock.getName())) {
                    ValueOperations<String, String> ops = template.opsForValue();
                    ops.set(redisLock.getName(), redisLock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
                    return true;
                } else {//存在锁
                    logger.debug("redisLock is exist!！！");
//                    log.info("redisLock is exist!！！");
                }
                if (System.currentTimeMillis() - startTime > timeout) {//尝试超过了设定值之后直接跳出循环
                    return false;
                }
                Thread.sleep(tryInterval);
            }
            while (template.hasKey(redisLock.getName())) ;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
//            log.error("", e);
            return false;
        }
        return false;
    }

    /**
     * 释放锁
     */
    public void releaseLock(RedisLock redisLock) {
        if (!StringUtils.isEmpty(redisLock.getName())) {
            template.delete(redisLock.getName());
        }
    }

}
