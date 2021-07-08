package com.yig.springframework.beans.factory.support;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author wmx
 * @Date 2021/6/2 09:17
 * @Description 实例化策略接口
 */
public interface InstantiationStrategy {
    /**
     *
     * @param beanDefinition
     * @param beanName
     * @param constructor java.lang.reflect 包下的 Constructor 类，里面包含了一些必要的类信息，这个参数的目的就是为了拿到符合入参信息相对应的构造函数。
     * @param args
     * @return
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object... args) throws BeansException;
}
