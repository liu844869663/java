package com.fullmoon.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StarProxy implements InvocationHandler {

    private Object target;

    public void setTarget(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("收钱");
        Object result = method.invoke(this.target, args);
        return result;
    }
}
