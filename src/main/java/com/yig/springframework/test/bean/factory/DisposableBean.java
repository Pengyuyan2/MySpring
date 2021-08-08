package com.yig.springframework.test.bean.factory;

/**
 * Interface to be implemented by beans that want to release resources
 * on destruction. A BeanFactory is supposed to invoke the destroy
 * method if it disposes a cached singleton. An application context
 * is supposed to dispose all of its singletons on close.
 * @author yig
 */
public interface DisposableBean {
    /**
     * 销毁Bean
     * @throws Exception
     */
    void destroy() throws Exception;
}
