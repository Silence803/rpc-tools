package com.example.rpctools.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
@Data
@NoArgsConstructor
public class RpcResponse implements Serializable {
    private	static final long serialVersionUID = 2L;
    private int status;
    private String error;
    private Object data;

    public static RpcResponse ok(Object data){
        return build(1, null, data);
    }

    public static RpcResponse error(String error){
        return build(0, error, null);
    }

    public static RpcResponse build(int status, String error, Object data){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setStatus(status);
        rpcResponse.setError(error);
        rpcResponse.setData(data);
        return rpcResponse;
    }
}
