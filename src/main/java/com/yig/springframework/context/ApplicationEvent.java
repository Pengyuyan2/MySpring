package com.yig.springframework.context;

import java.util.EventObject;

/**
 * 以继承java.util.EventObject定义出具备事件功能的抽象类ApplicationEvent，后续所有事件的类都需要继承这个类
 * @author yig
 */
public abstract class ApplicationEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
