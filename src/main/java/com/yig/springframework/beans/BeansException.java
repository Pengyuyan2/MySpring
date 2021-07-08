package com.yig.springframework.beans;

/**
 * @Author wmx
 * @Date 2021/5/25 18:43
 * @Description
 */
public class BeansException extends RuntimeException {
    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }

}
