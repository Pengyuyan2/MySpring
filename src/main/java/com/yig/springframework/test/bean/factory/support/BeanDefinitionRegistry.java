package com.yig.springframework.test.bean.factory.support;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.config.BeanDefinition;

/**
 * @Author wmx
 * @Date 2021/5/25 18:50
 * @Description 注册BeanDefinition
 */
public interface BeanDefinitionRegistry{
    /**
     * 向注册表中注册BeanDefinition
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断是否包含指定名称的BeanDfinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 使用Bean名称查询BeanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 返回注册表中所有的Bean名称
     * @return
     */
    String[] getBeanDefinitionNames();

}
