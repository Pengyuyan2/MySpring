package com.yig.springframework.context.support;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.ConfigurableListableBeanFactory;
import com.yig.springframework.test.bean.factory.config.BeanFactoryPostProcessor;
import com.yig.springframework.test.bean.factory.config.BeanPostProcessor;
import com.yig.springframework.context.ApplicationListener;
import com.yig.springframework.context.ConfigurableApplicationContext;
import com.yig.springframework.context.event.*;
import com.yig.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 应用上下文抽象类实现
 * AbstractApplicationContext继承DefaultResourceLoader是为了处理spring.xml配置资源的加载
 * 定义抽象方法refreshBeanFactory()、getBeanFactory()，由继承此抽象类的其他抽象类实现
 * @author yig
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * refresh() 方法就是整个 Spring 容器的操作过程
     *
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        //1、创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        //2、获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //3、添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        //4、在Bean实例化之前，执行BeanFactoryPostProcessor，通过上下文中调用注册bean的工厂处理器。
        invokeBeanFactoryPostProcessors(beanFactory);
        //5、BeanPostProcessor需要提前于其他Bean对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);
        //6、提前实例化单例Bean对象
//        beanFactory.preInstantiateSingletons();
        //7、初始化事件发布者
        initApplicationEventMulticaster();
        //8、注册事件监听器
        registerListeners();
        //9、发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 发布容器刷新完成事件
     */
    private void finishRefresh(){
        publishEvent(new ContextRefreshedEvent(this));
    }

    public void publishEvent(ApplicationContextEvent event){
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * 注册事件监听器
     */
    private void registerListeners(){
        //通过getBeansOfType方法获取到所有从spring.xml中加载到的事件配置Bean对象。
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //实例化一个SimpleApplicationEventMulticaster，这是一个事件广播器。
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     *
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     *
     * @return
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     *
     * @param beanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     *
     * @param beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));
        //执行销毁单例bean的销毁方法
        getBeanFactory().destroySingletons();
    }
}
