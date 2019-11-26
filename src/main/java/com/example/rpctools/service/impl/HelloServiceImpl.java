package com.example.rpctools.service.impl;

import com.example.rpctools.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Creat by ZhangXueRong on 2018/12/29
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        return "hello, " + msg;
    }

}
