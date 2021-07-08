package com.yig.springframework.beans.factory.support;

import com.yig.springframework.core.io.DefaultResourceLoader;
import com.yig.springframework.core.io.ResourceLoader;

/**
 * 抽象类把BeanDefinitionReader接口的前两个方法全部实现完了，并提供了构造函数，让外部的调用使用方，把Bean定义注入类，传递进来。
 * 这样在接口BeanDefinitionReader的具体实现类中，就可以把解析后的XML 文件中的Bean信息，注册到Spring容器去了。
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;


    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
