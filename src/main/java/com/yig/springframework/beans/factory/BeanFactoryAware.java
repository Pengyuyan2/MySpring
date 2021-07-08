package com.yig.springframework.beans.factory;

import com.yig.springframework.beans.BeansException;

/**
 * 实现此接口，既能感知到所属的BeanFactory
 * @author yig
 */
public interface BeanFactoryAware extends Aware {
    /**
     * Set BeanFactory
     * @param beanFactory
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
