package com.example.rpctools.domain;

import com.sun.deploy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static RpcResponse requestHandler(RpcRequest request){


        try {
            Object service = ServiceRegistry.getService(request.getClassName());
            if (service != null) {
                Class<?> clazz = service.getClass();
                //获取方法
                Method method = clazz.getMethod(request.getMethodName(), request.getParameterTypes());
                //执行方法
                Object result = method.invoke(service, request.getParameters());
                return RpcResponse.ok(result);
            } else {
                System.out.println("请求的服务未找到:{}.{}({})" + request.getClassName() + request.getMethodName() + StringUtils.join(Arrays.asList(request.getParameterTypes()), ", "));
                return RpcResponse.error("未知服务");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("处理请求失败" + e.getMessage());
            return RpcResponse.error(e.getMessage());
        }
    }

}
