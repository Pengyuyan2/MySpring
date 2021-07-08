package com.yig.springframework.beans.service;

import com.yig.springframework.beans.BeansException;
import com.yig.springframework.beans.dao.IUserDao;
import com.yig.springframework.beans.factory.*;
import com.yig.springframework.beans.dao.UserDao;
import com.yig.springframework.context.ApplicationContext;
import com.yig.springframework.context.ApplicationContextAware;

/**
 * @Author wmx
 * @Date 2021/5/26 09:43
 * @Description
 */
public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware,BeanFactoryAware {
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String uid;
    private String company;
    private String location;

    private IUserDao userDao;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader: " + classLoader);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name is: " + name);
    }

    public String queryUser(){
        return userDao.queryUserName(uid) + ", company: " + company + ", location: " + location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public UserDao getUserDao() {
//        return userDao;
//    }
//
//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("执行：UserService.destroy");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("执行：UserService.afterPropertiesSet");
//    }
}
