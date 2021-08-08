package com.yig.springframework.test.bean.factory;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.config.AutowireCapableBeanFactory;
import com.yig.springframework.test.bean.factory.config.BeanDefinition;
import com.yig.springframework.test.bean.factory.config.BeanPostProcessor;
import com.yig.springframework.test.bean.factory.config.ConfigurableBeanFactory;

/**
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
