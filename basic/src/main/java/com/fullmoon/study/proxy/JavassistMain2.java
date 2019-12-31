package com.fullmoon.study.proxy;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @Author jingping.liu
 * @Date 2019-10-12
 * @Description Javassist动态代理测试类
 */
public class JavassistMain2 {

    public void execute(){
        System.out.println("1");
    }
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ProxyFactory factory = new ProxyFactory();
        // 指定父类。ProxyFactory会动态生成继承该父类的子类
        factory.setSuperclass(JavassistMain2.class);
        // 设置过滤器，判断哪些方法的调用需要被拦截
        factory.setFilter((Method method) -> {
            if(method.getName().equals("execute")){
                return true;
            }
            return false;
        });

        // 设置拦截处理
        factory.setHandler((o, method, method1, objects) -> {
            System.out.println("前置处理");
            Object result = method1.invoke(o, objects);
            System.out.println("后置处理");
            return result;
        });

        Class<?> c = factory.createClass();
        JavassistMain2 javassistMain2 = (JavassistMain2) c.newInstance();
        javassistMain2.execute();
    }
}
