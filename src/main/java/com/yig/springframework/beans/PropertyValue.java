package com.yig.springframework.beans;

/**
 * @Author wmx
 * @Date 2021/6/4 09:21
 * @Description 创建出一个用于传递类中属性信息的类
 */
public class PropertyValue {
    private final String name;
    private final Object value;

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
