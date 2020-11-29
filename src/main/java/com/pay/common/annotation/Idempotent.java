package com.pay.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Idempotent
 * @Description: 自定义幂等注解
 * @author: Bruce cyc
 * @date: 2020/11/29 22:33
 * @Copyright:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    //注解自定义redis的key的前缀，后面拼接参数
    String key();

    //自定义的传入参数序列作为key的后缀，默认的全部参数作为key的后缀拼接。参数定义示例：{0,1}
    int[] custKeysByParameterIndexArr() default {};

    //过期时间，单位秒。可以是毫秒，需要修改切点类的设置redis值的代码参数。
    long expirMillis();
}