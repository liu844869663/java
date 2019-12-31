package com.fullmoon.study.innerclass;

public class Test {
    private String name = "你好";

    class Bean1{
        public int i = 0;
        public void printName(){
            System.out.println(name);
        }
    }
    class Bean2{
        public int j = 1;
    }

    public void show(final String str){
        class Inner{
            public void print(){
                System.out.println(name + str);
            }
        }
        Inner inner = new Inner();
        inner.print();
    }

    //1、匿名内部类不能有构造器，匿名内部类没有类名，肯定无法声明构造器。
    //
    //2、匿名内部类必须继承或实现一个接口，指定给new的类型为匿名类的超类型，匿名类不能有显示的extends或implements子句，也不能有任何修饰符。
    //
    //3、匿名内部类和成员内部类、局部内部类一样，也不能声明静态成员。
    public void print(final String str2){
        new Inner() {
            public void print() {
                System.out.println(name + str2);
            }
        }.print();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.show(":lalala");
        test.print(":nenene");

        Test.Bean1 bean1 = test.new Bean1();
        System.out.println(bean1.i);
        bean1.printName();

        Test.Bean2 bean2 = test.new Bean2();
        System.out.println(bean2.j);

        Bean bean = new Bean();
        Bean.Bean3 bean3 = bean.new Bean3();
        System.out.println(bean3.k);
    }
}
