package com.yig.springframework.test.bean.factory;

/**
 * 实现此接口，既能感知到所属的ClassLoader
 * @author yig
 */
public interface BeanClassLoaderAware extends Aware{
    void setBeanClassLoader(ClassLoader classLoader);
}
