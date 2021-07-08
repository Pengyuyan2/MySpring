package com.yig.springframework.beans.common;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.PropertyValue;
import com.yig.springframework.beans.PropertyValues;
import com.yig.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yig.springframework.beans.factory.config.BeanDefinition;
import com.yig.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "修改：无敌宇宙公司"));
    }
}
