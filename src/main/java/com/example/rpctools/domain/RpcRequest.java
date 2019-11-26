package com.example.rpctools.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
@Data
public class RpcRequest implements Serializable {
    private	static final long serialVersionUID = 1L;
    private	String className;
    private	String methodName;
    private	Class<?>[] parameterTypes;
    private	Object[] parameters;
}
