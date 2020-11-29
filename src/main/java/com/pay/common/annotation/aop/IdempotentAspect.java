package com.pay.common.annotation.aop;

import com.pay.common.annotation.Idempotent;
import com.pay.common.exception.IdempotentException;
import com.pay.common.util.IdempotentKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: IdempotentAspect
 * @Description: 自定义幂等aop切点
 * @author: Bruce cyc
 * @date: 2020/11/29 22:42
 * @Copyright:
 */
@Component
@Slf4j
@Aspect
@ConditionalOnClass(RedisTemplate.class)
public class IdempotentAspect {

    private static final String KEY_TEMPLATE = "idempotent_%S";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 切点(自定义注解)
     */
    @Pointcut("@annotation(com.pay.common.annotation.Idempotent)")
    public void executeIdempotent() {

    }

    /**
     * 切点业务
     *
     * @throws Throwable
     */
    @Around("executeIdempotent()")
    public Object arountd(ProceedingJoinPoint jPoint) throws Throwable {
        //获取当前方法信息
        Method method = ((MethodSignature) jPoint.getSignature()).getMethod();
        //获取注解
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        //生成Key
        Object[] args = jPoint.getArgs();
        int[] custArgs = idempotent.custKeysByParameterIndexArr();

        String key = String.format(KEY_TEMPLATE, idempotent.key() + "_" + IdempotentKeyUtil.generate(method, custArgs, args));
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, "0", idempotent.expirMillis(), TimeUnit.SECONDS);

        if (result) {
            return jPoint.proceed();
        } else {
            log.info("数据幂等错误");
            throw new IdempotentException("幂等校验失败。key值为：" + IdempotentKeyUtil.getKeyOriginalString(method, custArgs, args));
        }
    }
}
