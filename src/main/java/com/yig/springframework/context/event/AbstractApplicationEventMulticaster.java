package com.yig.springframework.context.event;

import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.factory.BeanFactory;
import com.yig.springframework.test.bean.factory.BeanFactoryAware;
import com.yig.springframework.context.ApplicationEvent;
import com.yig.springframework.context.ApplicationListener;
import com.yig.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * AbstractApplicationEventMulticaster是对事件广播器的公用方法提取，在这个类中可以实现一些基本功能，避免所有直接实现接口的类还需要处理细节。
 * @author yig
 */
public class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>)listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {

    }

    /**
     * 提取符合广播事件中的监听处理器
     * @param event
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event){
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)){
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 主要包括对Cglib、Simple不同实例化需要获取目标Class，Cglib代理类需要获取父类的Class，普通实例化的不需要。
     * 通过提取接口和对应的ParameterizedType和eventClassName，方便最后确认是否为子类和父类的关系，以此证明此事件归这个符合的类处理。
     * @param listener
     * @param event
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = listener.getClass();
        //按照CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy不同的实例化类型，判断后获取目标class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        //判定此eventClassName对象所表示的类或接口与指定的event.getClass()参数所表示的类或接口是否相同，或者是否是其超类。
        //isAssignableFrom是用来判断子类和父类的关系，或者接口的实现类和接口的关系，默认所有的类的终极父类都是Object。如果A.isAssignableFrom(B)结果是true，证明B可以转换成A，也就是A可以由B转换而来。
        return eventClassName.isAssignableFrom(event.getClass());
    }

}
