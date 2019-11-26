package com.example.rpctools.service.impl;

import com.example.rpctools.domain.RequestHandler;
import com.example.rpctools.domain.RpcRequest;
import com.example.rpctools.service.RpcServer;
import com.sun.deploy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
@Service
public class BioRpcServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(BioRpcServer.class);

    private static final ExecutorService es = Executors.newCachedThreadPool();

    private int port = 9090;

    private volatile boolean shutdown = false;

    BioRpcServer(){}

    public BioRpcServer(int port){
        this.port = port;
    }

    @Override
    public void start() {

        try {
            //服务启动！
            //准备链接端口
            ServerSocket server = new ServerSocket(this.port);
            System.out.println("服务已启动：{}, " + this.port);
            //等待接入
            while (!this.shutdown){
                Socket client = server.accept();
                es.execute(() ->{
                    try	(
                        //使用JDK的序列化流
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())
                    ){
                        //读取请求参数
                        RpcRequest request = (RpcRequest) in.readObject();
                        System.out.println("接收请求，{}.{}({})" + request.getClassName() +" " + request.getMethodName() +" "+ StringUtils.join(Arrays.asList(request.getParameterTypes()),", "));
                        System.out.println("请求参数：{}" + StringUtils.join(Arrays.asList(request.getParameters()),	", "));
                        //处理请求
                        out.writeObject(RequestHandler.requestHandler(request));
                    } catch	(Exception e) {
                        System.out.println(("客户端连接异常，客户端{}:{}" + client.getInetAddress().toString()));
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("服务启动失败" + e);
        }

    }

    @Override
    public void stop() {
        this.shutdown = true;
        System.out.println("服务已停止");
    }
}
