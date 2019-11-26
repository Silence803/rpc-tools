package com.example.rpctools.client;

import com.example.rpctools.domain.RpcRequest;
import com.example.rpctools.domain.RpcResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Creat by ZhangXueRong on 2018/12/29
 */
@Data
@AllArgsConstructor
public class BioRpcClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(BioRpcClient.class);

    private String host;
    private int port;

    @Override
    public RpcResponse sendRequest(RpcRequest request) throws Exception {
        try(
                Socket socket = new Socket(host, port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
                )
        {
            System.out.println("链接建立成功:{}:{}" + host + port);
            out.writeObject(request);
            System.out.println(("发起请求,目标主机：{}:{}, 服务：{},{}({})" + host + port +
                    request.getClassName() + request.getMethodName()));

            //获取结果
            return (RpcResponse) in.readObject();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
