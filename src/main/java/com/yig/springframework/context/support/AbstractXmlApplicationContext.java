package com.yig.springframework.context.support;

import com.yig.springframework.test.bean.factory.support.DefaultListableBeanFactory;
import com.yig.springframework.test.bean.factory.xml.XmlBeanDefinitionReader;
import com.yig.springframework.context.ApplicationContext;

/**
 * Convenient base class for {@link ApplicationContext}
 * implementations, drawing configuration from XML documents containing bean definitions
 * understood by an {@link XmlBeanDefinitionReader}.
 * @author yig
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    /**
     * 处理XML文件配置信息
     * @param beanFactory
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 从入口上下文类，拿到配置信息的地址描述
     * @return
     */
    protected abstract String[] getConfigLocations();
}
