package com.acai.example.rpc.server;

import com.acai.example.rpc.DemoService;

public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String msg) {
        System.out.println("msg:" + msg);
        return "response:" + msg;
    }

    @Override
    public String echo(String msg) {
        return null;
    }
}
