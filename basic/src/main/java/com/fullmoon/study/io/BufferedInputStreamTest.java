package com.fullmoon.study.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author jingping.liu
 * @date 2020-01-08
 * @description BufferedInputStream
 */
public class BufferedInputStreamTest {
    public static void main(String[] args) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

        }

    }
}
