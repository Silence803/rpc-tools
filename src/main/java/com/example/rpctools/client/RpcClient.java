package com.example.rpctools.client;


import com.example.rpctools.domain.RpcRequest;
import com.example.rpctools.domain.RpcResponse;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
public interface RpcClient {
    /**
     * 发起请求
      * @param request
     * @return
     * @throws Exception
     */
    RpcResponse sendRequest(RpcRequest request) throws Exception;
}
