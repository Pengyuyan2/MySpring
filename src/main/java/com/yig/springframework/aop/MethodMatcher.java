package com.yig.springframework.aop;

import java.lang.reflect.Method;

/**
 * Part of a {@link Pointcut}: Checks whether the target method is eligible for advice.
 * @author yig
 */
public interface MethodMatcher {
    /**
     * Perform static checking whether the given method matches.
     * @param method
     * @param targetClass
     * @return whether or not this method matches statically
     */
    boolean matches(Method method, Class<?> targetClass);
}
