package com.yig.springframework.aop.aspectj;

import com.yig.springframework.aop.ClassFilter;
import com.yig.springframework.aop.MethodMatcher;
import com.yig.springframework.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * Spring {@link Pointcut} implementation that uses the AspectJ weaver to evaluate a pointcut expression.
 * <p>
 * 切点表达式
 * <p>
 * @author yig
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private String expression;

    public AspectJExpressionPointcut() {
    }

    public AspectJExpressionPointcut(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return null;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return null;
    }
}
