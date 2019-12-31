package com.fullmoon.study.string;

/**
 * 关于字符串的学习
 *
 * String
 * String对象是不可变的，内部维护的char[]长度不可变，为final修饰，String类也是final修饰，不存在扩容
 * 看起来会修改值，实际上都是创建了一个新的对象，最初的对象没有变动
 * Java为了避免在一个系统中产生大量的String对象，引入了字符串常量池
 * 创建一个字符串时，会检查常量池中是否有相同的对象，有就直接引用，没有则创建对象
 *
 * StringBuilder
 * 内部为char[]保存数据，初始容量为16，可扩容
 * append拼接字符串方法内部调用System的native方法arraycopy，进行数组的拷贝，不会重新生成新的StringBuilder对象
 * 非线程安全的字符串操作类，其每次调用toString方法而重新生成的String对象，不会共享StringBuilder对象内部的char[]，会进行一次char[]的copy操作
 *
 * StringBuffer
 * 和StringBuilder基本上一致，但其为线程安全的字符串操作类
 * 大部分方法都采用了synchronized关键字修改，以此来实现在多线程下的操作字符串的安全性
 * 其toString方法而重新生成的String对象，会共享StringBuffer对象中的toStringCache属性（char[]），但是每次的StringBuffer对象修改，都会置null该属性值
 *
 */
public class StringTest {

    public static void main(String[] args){
        String s1 = "s";
        String s2 = "s"; // 检查字符串常量池, 直接引用
        String s3 = new String("s"); // 不会检查字符串常量池, 创建一个对象
        System.out.println(s1 == s2); // true
        System.out.println(s1 == s3); // false
        System.out.println("---------------");
        String a = "hello2";
        String a1 = "hello" + 2; // 在编译期间已经被优化成"hello2"，因此在运行期间两者指向的是同一个对象
        System.out.println(a == a1); // true
        System.out.println("---------------");
        String b = "hello";
        String b1 = b + 2; // 因为有+符号引用的存在，所以不会再编译期间优化，生成的对象保存在堆里面
        System.out.println(a == b1); // false
        System.out.println("---------------");
        final String c = "hello";
        String c1 = c + 2; // 被final修饰的变量会在class文件常量池中保存一个副本，在编译期间会被直接替代为真实的值，那么在编译期间被优化成"hello2"
        System.out.println(a == c1); // true
        System.out.println("---------------");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1");
        String sb = stringBuilder.toString();
        String sb1 = stringBuilder.toString();
        System.out.println(sb == sb1);
        System.out.println("---------------");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("1");
        String sbf = stringBuffer.toString();
        String sbf1 = stringBuffer.toString();
        System.out.println(sbf == sbf1);
        System.out.println("---------------");

    }
}
