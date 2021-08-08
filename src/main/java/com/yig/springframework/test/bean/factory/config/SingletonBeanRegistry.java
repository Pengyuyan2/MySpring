package com.yig.springframework.test.bean.factory.config;

/**
 * @Author wmx
 * @Date 2021/5/25 10:01
 * @Description 定义一个获取单例对象的接口
 */
public interface SingletonBeanRegistry {
    /**
     * 获取单例对象
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例对象
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);
}
