package com.yig.springframework.beans.factory.support;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FactoryBeanRegistrySupport类主要处理的是关于FactoryBean此类对象的注册操作，之所以放到这样一个单独的类里，
 * 就是希望做到不同领域模块下只负责各自需要完成的功能，避免因为扩展导致类膨胀到难以维护。
 * 同样这里也定义了缓存操作factoryBeanObjectCache，用于存放单例类型的对象，避免重复创建。在日常使用用，基本也都是创建的单例对象
 * @author yig
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    /**
     * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return object;
    }

    /**
     * 因为既有缓存的处理也有对象的获取，所以这里额外提供getObjectFromFactoryBean进行逻辑包装
     * @param factoryBean
     * @param beanName
     * @return
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName){
        if (factoryBean.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                this.factoryBeanObjectCache.put(beanName, object != null ? object : null);
            }
            return object;
        }else{
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }

    }

    /**
     * 具体的获取FactoryBean#getObject()方法
     * @param factoryBean
     * @param beanName
     * @return
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factoryBean, final String beanName) {
        try{
            return factoryBean.getObject();
        } catch (Exception e){
            throw new BeansException("FactoryBean threw exception on object["+beanName+"] creation", e);
        }
    }


}
