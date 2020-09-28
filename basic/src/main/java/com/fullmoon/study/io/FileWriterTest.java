package com.fullmoon.study.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author jingping.liu
 * @date 2020-01-08
 * @description FileWriter
 */
public class FileWriterTest {
    public static void main(String[] args) {
        Writer writer = null;
        try {
            // 创建一个FileWriter流对象, 可将流中字节刷出到文件中, append表示是否追加内容, 默认为false(直接覆盖)
            // 这里也会创建一个FileOutputStream对象然后生产一个StreamEncoder对象
            writer = new FileWriter("basic/src/main/resources/file.txt", true);
            writer.write("\r\n");
            writer.write("中国");
            writer.write("\r\n");
            writer.write("我爱你");
            // 将流中字节刷出到文件中, 如果文件实际上不存在则生成一个新的文件并写入
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    // 关闭流
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
