package com.yig.springframework.test.bean.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yig.springframework.test.bean.BeansException;
import com.yig.springframework.test.bean.PropertyValue;
import com.yig.springframework.test.bean.PropertyValues;
import com.yig.springframework.test.bean.factory.*;
import com.yig.springframework.test.bean.factory.config.AutowireCapableBeanFactory;
import com.yig.springframework.test.bean.factory.config.BeanDefinition;
import com.yig.springframework.test.bean.factory.config.BeanPostProcessor;
import com.yig.springframework.test.bean.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Abstract bean factory superclass that implements default bean creation,
 * with the full capabilities specified by the class.
 * Implements the {@link AutowireCapableBeanFactory}
 * interface in addition to AbstractBeanFactory's {@link #createBean} method.
 * <p>
 * @Author wmx
 * @Date 2021/5/25 18:46
 * @Description 在AbstractAutowireCapableBeanFactory类中实现了Bean的实例化操作newInstance
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     *
     * @param beanName
     * @param beanDefinition
     * @param args
     * @return
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition, beanName, args);
            //填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
            //执行Bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed.", e);
        }

        //注册实现了DisposableBean接口的Bean对象。
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        //判断SCOPE_SINGLETON、SCOPE_PROTOTYPE
        if (beanDefinition.isSingleton()) {
            //在处理完Bean对象的实例化后，直接调用addSingleton方法存放到单例对象的缓存中去。
            registerSingleton(beanName, bean);
        }
        return bean;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition){
        //invokeAwareMethods
        if (bean instanceof Aware){
            if (bean instanceof BeanFactoryAware){
                ((BeanFactoryAware)bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware){
                ((BeanClassLoaderAware)bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware){
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        //执行BeanPostProcessor Before处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //执行Bean对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean [" + beanName +"] failed", e);
        }
        //执行BeanPostProcessor After处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    /**
     * 在创建Bean对象的实例的时候，需要把销毁方法保存起来，方便后续执行销毁动作进行调用。这个销毁方法的具体方法信息，
     * 会被注册到DefaultSingletonBeanRegistry中新增加的Map<String, DisposableBean> disposableBeans属性中去，
     * 因为这个接口的方法最终可以被类AbstractApplicationContext的close方法通过getBeanFactory().destroySingletons()调用。
     * 在注册销毁方法的时候，会根据是接口类型和配置类型统一交给DisposableBeanAdapter销毁适配器类来做统一处理。
     * 实现了某个接口的类可以被instanceof判断或者强转后调用接口方法。
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition){
        //非Singleton类型的的Bean不执行销毁方法
        if (!beanDefinition.isSingleton()){
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        //循环比对出构造函数集合与入参信息args的匹配情况，这里我们对比的方式比较简单，只是一个数量对比，
        //而实际Spring源码中还需要比对入参类型，否则相同数量不同入参类型的情况，就会抛异常了。
        for (Constructor declaredConstructor : declaredConstructors) {
            if (null != args && declaredConstructor.getParameterTypes().length == args.length) {
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructor, args);
    }

    /**
     * 填充Bean属性
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            //通过获取beanDefinition.getPropertyValues()循环进行属性填充操作
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                //如果遇到的是BeanReference，那么就需要递归获取Bean实例，调用getBean方法
                if (value instanceof BeanReference) {
                    //A依赖B，获取B的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                //属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (BeansException e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }

    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception{
        //实现接口InitializingBean
        if (bean instanceof InitializingBean){
            ((InitializingBean)bean).afterPropertiesSet();
        }
        //配置信息init-method。判断是为了避免二次执行销毁
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)){
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod){
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException{
        Object result = existingBean;
        for(BeanPostProcessor processor:getBeanPostProcessors()){
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current){
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanname) throws BeansException{
        Object result =existingBean;
        for(BeanPostProcessor processor:getBeanPostProcessors()){
            Object current = processor.postProcessAfterInitialization(result, beanname);
            if (null==current){
                return result;
            }
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
