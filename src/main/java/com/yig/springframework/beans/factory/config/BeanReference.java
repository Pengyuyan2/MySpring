package com.yig.springframework.beans.factory.config;

/**
 * @Author wmx
 * @Date 2021/6/4 09:41
 * @Description
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
