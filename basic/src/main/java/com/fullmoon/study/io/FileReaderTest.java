package com.fullmoon.study.io;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author jingping.liu
 * @date 2020-01-08
 * @description FileReader, 只能操作纯文本
 */
public class FileReaderTest {
    public static void main(String[] args) {
        Reader reader = null;
        try {
            // 创建FileReader流对象, 和FileInputStream差不多, 不过这里是对字符的操作
            // 这里也会创建一个FileInputStream对象然后生产一个StreamDecoder对象
            reader = new FileReader("basic/src/main/resources/file.txt");
            char[] chars = new char[2];
            int len = -1;
            // 读取字符并写入char数组中,返回读取到的字符长度,未读取到返回-1
            while ((len = reader.read(chars)) != -1) {
                System.out.println(new String(chars, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    // 关闭流
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
