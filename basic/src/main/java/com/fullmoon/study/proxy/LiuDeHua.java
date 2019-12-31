package com.fullmoon.study.proxy;

public class LiuDeHua implements Star {
    @Override
    public String sing(String name) {
        System.out.println("给我一杯忘情水");
        return name + ",唱完";
    }

    @Override
    public String dance(String name) {
        System.out.println("Put your hands on");
        return name + ",跳完";
    }
}
