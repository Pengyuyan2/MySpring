package com.yig.springframework.beans.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wmx
 * @Date 2021/6/4 09:54
 * @Description
 */
public class UserDao {
    private static Map<String, String> hashMap = new HashMap<>();

    public void initDataMethod(){
        System.out.println("执行：init-method");
        hashMap.put("1001", "老大");
        hashMap.put("1002", "老二");
        hashMap.put("1003", "老三");
    }

    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        hashMap.clear();
    }

    public String queryUserName(String uid) {
        return hashMap.get(uid);
    }
}
