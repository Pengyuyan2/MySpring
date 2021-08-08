package com.yig.springframework.test.bean.factory;

/**
 * FactoryBean中提供3个方法，获取对象、对象类型，以及是否是单例对象，如果是单例对象，会被放到内存中
 * @author yig
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;
    Class<?> getObjectType();
    boolean isSingleton();
}
