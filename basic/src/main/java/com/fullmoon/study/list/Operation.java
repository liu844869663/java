package com.fullmoon.study.list;

/**
 * 关于运算符的计算
 * 5  0000 0101
 * 11 0000 1011
 *
 * 5&11=1 ,    0000 0001 按位与,都1才1
 * 5|11=15,    0000 1111 按位或,有1则1
 * 5^11=14,    0000 1110 按位异或,不同为1
 *
 * ~5  =-6,    按位非
 * 1111 1010   将位全部取反得到补码,最高位1表示负数,所以补码需减1
 * 1111 1001   反码
 * 1000 0110   原码,得出为-6
 *
 * 关于原码、补码、反码,底层通过补码进行计算(正数的补码、反码和原码相同)
 * -5的补码
 * 1000 0101 原码（二进制）
 * 1111 1010 反码（除最高位,其余都取反）
 * 1111 1011 补码（反码加1）
 *
 */
public class Operation {
    public static void main(String[] args){
        System.out.println(5 & 11);
        System.out.println(5 | 11);
        System.out.println(5 ^ 11);
        System.out.println(~5);
        System.out.println("-------------------------------");
        // 按位向左移,不分正负,低位补0
        System.out.println(1 << 4);
        System.out.println(-1 << 4);
        // 按位向右移,如果该数为正数 高位补0,如果该数为负数 高位补1
        System.out.println(11 >> 4);
        System.out.println(-11 >> 4);
        // 按位无符号向右移（逻辑右移）,不管正数还是负数 高位补0 ,没有<<<运算符
        System.out.println(16 >>> 4);
    }
}
