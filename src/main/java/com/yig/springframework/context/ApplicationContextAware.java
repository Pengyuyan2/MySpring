package com.yig.springframework.context;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.Aware;

/**
 * 实现此接口，既能感知到所属的 ApplicationContext
 * @author yig
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
