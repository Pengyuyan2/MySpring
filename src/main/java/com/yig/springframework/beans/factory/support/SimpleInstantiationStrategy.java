package com.yig.springframework.beans.factory.support;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author wmx
 * @Date 2021/6/2 09:22
 * @Description 基于Java本身自带的DeclaredConstructor方法，创建Bean对象
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object... args) throws BeansException {
        //通过beanDefinition获取Class信息，这个Class信息是在Bean定义的时候传递进来的
        Class clazz = beanDefinition.getBeanClass();
        try {
            //判断constructor是否为空，如果为空则是无构造函数实例化，否则就是需要有构造函数的实例化。
            if (null != constructor){
                return clazz.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException|NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate ["+ clazz.getName()+"]", e);
        }
    }
}
