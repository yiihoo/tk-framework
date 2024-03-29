package net.thinklog.common.lock;

import net.thinklog.common.exception.LockException;
import net.thinklog.common.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 分布式锁切面
 *
 * @author azhao
 * @date 2020/6/6
 */
@Slf4j
@Aspect
public class LockAspect {
    @Autowired(required = false)
    private DistributedLock locker;

    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@within(lock) || @annotation(lock)")
    public Object aroundLock(ProceedingJoinPoint point, Lock lock) throws Throwable {
        log.info("===分布式锁切点监听==={}", point.getTarget().getClass().getName());
        if (lock == null) {
            // 获取类上的注解
            lock = point.getTarget().getClass().getDeclaredAnnotation(Lock.class);
        }
        String lockKey = lock.key();
        log.info("====加锁开始===={}", lockKey);
        if (locker == null) {
            throw new LockException("DistributedLock is null");
        }
        if (StrKit.isBlank(lockKey)) {
            throw new LockException("lockKey is null");
        }

        if (lockKey.contains("#")) {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            //获取方法参数值
            Object[] args = point.getArgs();
            lockKey = getValBySpel(lockKey, methodSignature, args);
        }
        DLock lockObj = null;
        try {
            //加锁
            if (lock.waitTime() > 0) {
                lockObj = locker.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.unit(), lock.isFair());
            } else {
                lockObj = locker.lock(lockKey, lock.leaseTime(), lock.unit(), lock.isFair());
            }

            if (lockObj != null) {
                log.info("====加锁成功===={}", lockKey);
                return point.proceed();
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            locker.unlock(lockObj);
            log.info("====解锁成功===={}", lockKey);
        }
    }

    /**
     * 解析spEL表达式
     */
    private String getValBySpel(String spel, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spel);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context).toString();
        }
        return null;
    }
}
