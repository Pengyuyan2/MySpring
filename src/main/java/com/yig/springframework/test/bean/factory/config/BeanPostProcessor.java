package com.yig.springframework.test.bean.factory.config;

import com.yig.springframework.test.bean.BeansException;

/**
 * 提供修改新实例化Bean对象的扩展点
 * @author yig
 */
public interface BeanPostProcessor {
    /**
     * 在Bean对象执行初始化方法之前，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在Bean对象执行初始化方法之后，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
