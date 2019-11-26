package com.example.rpctools.client;

import com.example.rpctools.domain.RpcRequest;
import com.example.rpctools.domain.RpcResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
@Data
@AllArgsConstructor
public class RpcProxyFactory<T> implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcProxyFactory.class);

    Class<?> clazz;

    public T getProxyObject(){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //处理object中的方法
        if(Object.class == method.getDeclaringClass()){
            String name = method.getName();
            if ("equals".equals(name)){
                return proxy == args[0];
            } else if ("hasCode".equals(name)){
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)){
                return	proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy))	+
                        ",	with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        //封装请求参数
        RpcRequest request = new RpcRequest();
        request.setClassName(clazz.getName());
        request.setMethodName(clazz.getName());
        request.setParameters(args);
        request.setParameterTypes(method.getParameterTypes());

        try	{
            //发起网络请求,并接收响应
            RpcClient client = new BioRpcClient("127.0.0.1", 9000 );
            RpcResponse response = client.sendRequest(request);
            //解析并返回
            if (response.getStatus() ==	1){
                System.out.println("调用远程服务成功！");
                return	response.getData();
            }
            System.out.println("远程服务调用失败，{}。" + response.getError());
            return	null;
        } catch	(Exception e){
            System.out.println("远程调用异常"+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
