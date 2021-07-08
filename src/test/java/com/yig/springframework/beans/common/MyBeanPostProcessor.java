package com.yig.springframework.beans.common;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.factory.config.BeanPostProcessor;
import com.yig.springframework.beans.service.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)){
            UserService userService = (UserService) bean;
            userService.setLocation("修改：上海");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
