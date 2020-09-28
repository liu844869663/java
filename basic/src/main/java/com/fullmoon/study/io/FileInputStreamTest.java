package com.fullmoon.study.io;

import java.io.*;

/**
 * @author jingping.liu
 * @date 2020-01-08
     * @description FileInputStream, 任何文件都可操作
 */
public class FileInputStreamTest {
    public static void main(String[] args) {
        /*
         * File是文件或者目录路径名的抽象表现形式,提供操作文件外部的能力,不能操作文件内部的内容
         * 能够定义存在或者不存在的文件或者路径,所以抽象表现形式
         * exists()方法可判断是否真的存在
         * mkdirs()方法如果是目录路径名且不存在则可创建该目录
         * createNewFile()方法如果是文件且不存在则可创建该名称的空文件
         */
        File file = new File("basic/src/main/resources/file.txt");
        InputStream is = null;
        try {
            /*
             * 创建文件输入流FileInputStream对象,从file中获取输入字节,可对其进行操作
             * 如果file实际上不存在,则会抛出FileNotFoundException异常
             */
            is = new FileInputStream(file);
            byte[] bytes = new byte[6];
            int len = -1;
            /*
             * read(byte b[], int off, int len)方法可从FileInputStream流中读取字节至bytes中,底层是一个一个字节读取的
             * 返回读取到的字节长度,如果未读取到则返回-1
             * 最后一次读取如果没有bytes数组大小个字节数,则后面的字节还是上次读取到的
             * 例如bytes数组大小为6,文件中9个字节,第二次读取到3个字节,但实际上bytes中仍有6个字节,后面的3个字节为上次读取到的
             * 所以这里得注意,转换成字符串的时候加len
             */
            while ((len = is.read(bytes)) != -1) {
                // 将bytes中从0开始到len的字节转换成字符串并输出
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    // 不使用了需要关闭
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
