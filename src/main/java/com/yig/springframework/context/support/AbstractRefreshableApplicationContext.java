package com.yig.springframework.context.support;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yig.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yig.springframework.context.ApplicationContext;

/**
 * Base class for {@link ApplicationContext}
 * implementations which are supposed to support multiple calls to {@link #refresh()},
 * creating a new internal bean factory instance every time.
 * Typically (but not necessarily), such a context will be driven by
 * a set of config locations to load bean definitions from.
 * @author yig
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    /**
     * 获取Bean工厂并加载资源
     * @throws BeansException
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 加载资源配置，在加载完成后即可完成对spring.xml配置文件中Bean对象的定义和注册，由子类实现
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    /**
     * 实例化DefaultListableBeanFactory
     * @return
     */
    private DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    /**
     * 获取ConfigurableListableBeanFactory对象
     * @return
     */
    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
