package com.yig.springframework.context.event;

import com.yig.springframework.context.ApplicationEvent;
import com.yig.springframework.context.ApplicationListener;

/**
 * 事件广播器
 * @author yig
 */
public interface ApplicationEventMulticaster {
    /**
     * Add a listener to be notified of all events.
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * Remove a listener from the notification list.
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * Multicast the given application event to appropriate listeners
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
