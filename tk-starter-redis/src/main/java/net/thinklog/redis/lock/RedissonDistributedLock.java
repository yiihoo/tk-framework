package net.thinklog.redis.lock;

import com.saite.common.config.BaseConstant;
import com.saite.common.exception.LockException;
import com.saite.common.lock.DistributedLock;
import com.saite.common.lock.ZLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁实现，基本锁功能的抽象实现
 * 本接口能满足绝大部分的需求，高级的锁功能，请自行扩展或直接使用原生api
 * https://gitbook.cn/gitchat/activity/5f02746f34b17609e14c7d5a
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "st.lock", name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {
    @Resource
    private RedissonClient redissonClient;

    private ZLock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redissonClient.getFairLock(BaseConstant.LOCK_KEY_PREFIX + key);
        } else {
            lock = redissonClient.getLock(BaseConstant.LOCK_KEY_PREFIX + key);
        }
        return new ZLock(lock, this);
    }

    @Override
    public ZLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        ZLock zLock = getLock(key, isFair);
        RLock lock = (RLock) zLock.getLock();
        lock.lock(leaseTime, unit);
        return zLock;
    }

    @Override
    public ZLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        ZLock zLock = getLock(key, isFair);
        RLock lock = (RLock) zLock.getLock();
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return zLock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock) lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
