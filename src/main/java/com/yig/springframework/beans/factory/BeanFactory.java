package com.yig.springframework.beans.factory;

import com.yig.springframework.beans.BeansException;

/**
 * @Author wmx
 * @Date 2021/5/25 18:39
 * @Description BeanFactory 的定义由 AbstractBeanFactory 抽象类实现接口的 getBean 方法
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    /**
     * 重载了一个含有入参信息args的getBean方法，这样就可以方便的传递入参给构造函数实例化了。
     *
     * @param beanName
     * @param args
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName, Object... args) throws BeansException;

    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

}