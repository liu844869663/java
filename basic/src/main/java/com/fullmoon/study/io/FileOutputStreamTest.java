package com.fullmoon.study.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jingping.liu
 * @date 2020-01-08
 * @description FileOutputStream
 */
public class FileOutputStreamTest {
    public static void main(String[] args) {
        File file = new File("basic/src/main/resources/file.txt");
        OutputStream os = null;
        try {
            // 创建文件输出流FileOutputStream对象, 可将流中的字节刷出到file, append表示是否追加内容, 默认为false(直接覆盖)
            os = new FileOutputStream(file, true);
            byte[] bytes = "我爱中国".getBytes();
            // 将byte数组写入流中,底层是一个一个字节写入的
            os.write(bytes);
            // 将流刷出到文件中, 如果file实际不存在, 则生成一个新的文件并写入bytes
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    // 关闭流
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
