package com.fullmoon.study.proxy;

import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author jingping.liu
 * @date 2021-04-15
 * @description Javassist 动态代理
 */
public class JavassistProxy {

    public void say() {
        System.out.println("hello, liujingping");
    }

    public static void main(String[] args) throws Exception {
        ProxyFactory factory = new ProxyFactory();
        // 指定代理类的父类
        factory.setSuperclass(JavassistProxy.class);
        // 设置方法过滤器，用于判断是否拦截方法
        factory.setFilter((Method method) -> {
            // 拦截 `say` 方法
            return "say".equals(method.getName());
        });
        factory.getClass().getDeclaredField("writeDirectory").set(factory, ".");
        JavassistProxy proxy = (JavassistProxy) factory.create(new Class<?>[]{}, new Object[]{}, (o, method, method1, objects) -> {
            System.out.println("前置处理");
            Object result = method1.invoke(o, objects);
            System.out.println("后置处理");
            return result;
        });
        proxy.say();
    }
}
