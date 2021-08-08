package com.yig.springframework.aop;

/**
 * @author yig
 */
public interface PointcutAdvisor extends Advisor{
    /**
     * Get the Pointcut that drivers this advisor.
     * @return
     */
    Pointcut getPointcut();
}
