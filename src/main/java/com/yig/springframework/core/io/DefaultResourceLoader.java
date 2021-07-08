package com.yig.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 在获取资源的实现中，主要是把三种不同类型的资源处理方式进行了包装，分为：判断是否为ClassPath、URL以及文件。
 * 虽然DefaultResourceLoader类实现的过程简单，但这也是设计模式约定的具体结果，
 * 比如这里不会让外部调用方知道过多的细节，而是仅关心具体调用结果即可。
 * @author yig
 */
public class DefaultResourceLoader implements ResourceLoader{
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_URL_PREFIX)){
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }else{
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}
