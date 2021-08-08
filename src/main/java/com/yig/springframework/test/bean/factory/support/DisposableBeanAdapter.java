package com.yig.springframework.test.bean.factory.support;

import cn.hutool.core.util.StrUtil;
import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.DisposableBean;
import com.yig.springframework.test.bean.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 销毁方法有两种甚至多种方式，目前有实现接口DisposableBean、配置信息destroy-method，两种方式。
 * 而这两种方式的销毁动作是由AbstractApplicationContext在注册虚拟机钩子后，虚拟机关闭前执行的操作动作。
 * 那么在销毁执行时不太希望还得关注都销毁哪些类型的方法，它的使用上更希望是有一个统一的接口进行销毁，所以这里新增适配类，做统一处理。
 * @author yig
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        //实现接口DisposableBean
        if (bean instanceof DisposableBean){
            ((DisposableBean)bean).destroy();
        }
        //配置信息destroy-method。判断是为了避免二次执行销毁
        if (StrUtil.isNotEmpty(destroyMethodName)
                && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))){
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod){
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName +"' on bean with name '" + beanName +"'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
