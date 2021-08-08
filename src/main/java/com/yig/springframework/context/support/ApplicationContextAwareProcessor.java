package com.yig.springframework.context.support;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.config.BeanPostProcessor;
import com.yig.springframework.context.ApplicationContext;
import com.yig.springframework.context.ApplicationContextAware;

/**
 * 由于ApplicationContext的获取并不能直接在创建Bean的时候就可以拿到，所以需要在refresh操作时，
 * 把ApplicationContext写入到一个包装的BeanPostProcessor中去，
 * 再由AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization方法调用。
 * @author yig
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware){
            ((ApplicationContextAware)bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
