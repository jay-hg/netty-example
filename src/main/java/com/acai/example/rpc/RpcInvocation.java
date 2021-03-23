package com.acai.example.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;

public class RpcInvocation implements Serializable {
    private Method method;
    private Object[] args;

    public RpcInvocation(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
