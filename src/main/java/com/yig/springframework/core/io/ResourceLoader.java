package com.yig.springframework.core.io;

public interface ResourceLoader {
    /**
     * Pseudo URL prefix for loading from the class path"
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取资源接口，传递 location 地址即可
     */
    Resource getResource(String location);

}
