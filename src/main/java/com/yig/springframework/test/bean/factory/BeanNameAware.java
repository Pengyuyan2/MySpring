package com.yig.springframework.test.bean.factory;

/**
 * 实现此接口，既能感知到所属的BeanName
 * @author yig
 */
public interface BeanNameAware extends Aware{
    void setBeanName(String name);
}
