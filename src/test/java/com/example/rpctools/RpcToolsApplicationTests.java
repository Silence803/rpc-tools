package com.example.rpctools;

import com.example.rpctools.domain.ServiceRegistry;
import com.example.rpctools.service.HelloService;
import com.example.rpctools.service.impl.BioRpcServer;
import com.example.rpctools.service.impl.HelloServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RpcToolsApplicationTests {

    @Test
    public void contextLoads() {

        //注册服务
        ServiceRegistry.registerService(HelloService.class,	HelloServiceImpl.class);
        //启动服务
        new BioRpcServer(9000).start();

    }

}

