package com.yig.springframework.test.bean.common;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.PropertyValue;
import com.yig.springframework.test.bean.PropertyValues;
import com.yig.springframework.test.bean.factory.ConfigurableListableBeanFactory;
import com.yig.springframework.test.bean.factory.config.BeanDefinition;
import com.yig.springframework.test.bean.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "修改：无敌宇宙公司"));
    }
}
