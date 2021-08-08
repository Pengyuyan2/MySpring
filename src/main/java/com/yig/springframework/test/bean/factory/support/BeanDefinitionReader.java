package com.yig.springframework.test.bean.factory.support;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.core.io.Resource;
import com.yig.springframework.core.io.ResourceLoader;

/**
 * 这是一个Simple interface for bean definition readers.
 * 其实里面无非定义了几个方法，包括：getRegistry()、getResourceLoader()，以及三个加载Bean定义的方法。
 * 这里需要注意 getRegistry()、getResourceLoader()，都是用于提供给后面三个方法的工具，
 * 加载和注册，这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法。
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;
}
