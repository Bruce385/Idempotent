package com.pay.common.exception;

/**
 * @ClassName: IdempotentException
 * @Description: 自定义幂等异常类
 * @author: Bruce cyc
 * @date: 2020/11/29 16:21
 * @Copyright:
 */
public class IdempotentException extends RuntimeException{

    public IdempotentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
