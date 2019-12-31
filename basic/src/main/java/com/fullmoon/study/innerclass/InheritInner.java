package com.fullmoon.study.innerclass;

public class InheritInner extends WithInner.Inner {

    // InheritInner() 是不能通过编译的，一定要加上形参
    // 构造器中必须有指向外部类对象的引用，并通过这个引用调用super()
    InheritInner(WithInner wi) {
        wi.super(); //必须有这句调用
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner obj = new InheritInner(wi);
    }
}
