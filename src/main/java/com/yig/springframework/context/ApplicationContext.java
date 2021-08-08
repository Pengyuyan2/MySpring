package com.yig.springframework.context;

import com.yig.springframework.test.bean.factory.HierarchicalBeanFactory;
import com.yig.springframework.test.bean.factory.ListableBeanFactory;
import com.yig.springframework.core.io.ResourceLoader;

/**
 * Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 * @author yig
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
