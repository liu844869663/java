package com.fullmoon.study.reflect;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * @Author jingping.liu
 * @Date 2019-09-29
 * @Description TODO
 */
public interface Car<T> {
    String getCar(T name);

    public static void main(String[] args) throws IOException {
        String packageName = "com.fullmoon.study.reflect";
        packageName = packageName == null ? null : packageName.replace('.','/');
        // 获取该包名下的URL对象(绝对路径)
        List<URL> list = Collections.list(Thread.currentThread().getContextClassLoader().getResources(packageName));
        URL url = list.get(0);
        try {
            for (;;) {
                url = new URL(url.getFile());
            }
        } catch (MalformedURLException e) {
            // This will happen at some point and serves as a break in the loop
        }

        StringBuilder jarUrl = new StringBuilder("12799903.jar");
        jarUrl.setLength(4);
        System.out.println(jarUrl);
    }
}
