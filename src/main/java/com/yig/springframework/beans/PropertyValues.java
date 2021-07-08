package com.yig.springframework.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author wmx
 * @Date 2021/6/4 09:22
 * @Description 属性可能会有很多，所以这里定义一个集合包装一下
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue v){
        this.propertyValues.add(v);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        Optional<PropertyValue> optional = this.propertyValues.parallelStream()
                .filter(value -> value.equals(propertyName)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

}
