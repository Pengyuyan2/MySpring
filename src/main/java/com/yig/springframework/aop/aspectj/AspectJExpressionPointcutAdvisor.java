package com.yig.springframework.aop.aspectj;

import com.yig.springframework.aop.Pointcut;
import com.yig.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * AspectJExpressionPointcutAdvisor实现了PointcutAdvisor接口，把切面pointcut、拦截方法advice和具体的拦截表达式包装在一起。
 * 这样就可以在xml的配置中定义一个pointcutAdvisor切面拦截器了。
 * @author yig
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 具体拦截方法
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
