package com.yig.springframework.context.support;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.context.ApplicationEvent;

/**
 * Standalone XML application context, taking the context definition files
 * from the class path, interpreting plain paths as class path resource names
 * that include the package path (e.g. "mypackage/myresource.txt"). Useful for
 * test harnesses as well as for application contexts embedded within JARs.
 *
 * 对外提供应用上下文方法。主要是对继承抽象类中方法的调用和提供了配置文件地址信息
 * @author yig
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从xml中加载BeanDefinition，并刷新上下文
     * @param configLocations
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    /**
     * 从xml中加载BeanDefinition，并刷新上下文
     * @param configLocations
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
