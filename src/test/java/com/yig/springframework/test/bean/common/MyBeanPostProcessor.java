package com.yig.springframework.test.bean.common;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.config.BeanPostProcessor;
import com.yig.springframework.test.bean.service.UserService;

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
