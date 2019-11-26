package com.example.rpctools.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
public class ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRegistry.class);

    private static final Map<String, Object> registeredService = new HashMap<>();
    public static <T> T getService(String className){
        return (T) registeredService.get(className);
    }

    public static void registerService (Class<?> interfaceClass, Class<?> implClass){
        try {
            registeredService.put(interfaceClass.getName(), implClass.newInstance());
            System.out.println("服务注册成功,接口：{},实现{}" + interfaceClass.getName() + implClass.getName());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println(("服务"	+ implClass + "注册失败" + e));
        }
    }
}
