package com.fullmoon.study.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 */
public class TestProxy {
    public static void main(String args[]){

        // 代理对象
        LiuDeHua liuDeHua = new LiuDeHua();

        // 实现定义好的代理类实例
        StarProxy starProxy = new StarProxy();
        starProxy.setTarget(liuDeHua);

        Star proxy1 = (Star) Proxy.newProxyInstance(liuDeHua.getClass().getClassLoader(), liuDeHua.getClass().getInterfaces(), starProxy);
        System.out.println(proxy1.sing("1"));

        System.out.println("-------------------------------------");

        Star proxy2 = (Star) Proxy.newProxyInstance(liuDeHua.getClass().getClassLoader(), liuDeHua.getClass().getInterfaces(), (proxy, method, arg) -> {
            if(method.getName().equals("sing")){
                System.out.println("收钱");
                return method.invoke(liuDeHua, arg);
            }
            if(method.getName().equals("dance")){
                System.out.println("收钱");
                return method.invoke(liuDeHua, arg);
            }
            return null;
        });

        System.out.println(proxy2.dance("2"));

    }
}
