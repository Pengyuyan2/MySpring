package com.yig.springframework.beans.bean;

import com.yig.springframework.beans.dao.IUserDao;
import com.yig.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个实现接口FactoryBean的代理类ProxyBeanFactory名称，主要是模拟了UserDao的原有功能，类似于MyBatis框架中的代理操作。
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        //提供一个InvocationHandler的代理对象，当有方法调用的时候，则执行代理对象的功能
        InvocationHandler handler = (proxy, method, args) -> {
            Map<String, String> map = new HashMap<>();
            map.put("1001", "老大");
            map.put("1002", "老二");
            map.put("1003", "老三");
            return "你被代理了" + method.getName() + ": " + map.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
