package com.fullmoon.study.proxy;

/**
 * @Author jingping.liu
 * @Date 2019-10-11
 * @Description CGLib动态代理测试类
 */
public class CGLibTest {
    private String method(String str){
        System.out.println(str);
        return "CGLibTest.method():" + str;
    }

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        // 生成代理对象
        CGLibTest proxyImpl = (CGLibTest)proxy.getProxy(CGLibTest.class);
        // 调用代理对象的method方法
        String result = proxyImpl.method("test");
        System.out.println(result);
    }
}
