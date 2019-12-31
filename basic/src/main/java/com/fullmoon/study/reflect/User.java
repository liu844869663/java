package com.fullmoon.study.reflect;

import java.util.Map;

/**
 * @Author jingping.liu
 * @Date 2019-09-27
 * @Description TODO
 */
public class User extends Person implements Car<String> {
    private String name;
    private int age;
    private boolean married;
    private Map<String, Object> fCode;

    public User(String name, int age){
        super();
        this.name = name;
        this.age = age;
    }

    public User(int age){
        super();
        this.age = age;
    }

    public User(String name){
        super();
        this.name = name;
    }

    public User(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + "]";
    }

    public void exit(){
        System.out.println( this.name + "exit.");
    }

    public void login(String name, int age){
        System.out.println( "用户名:" + name);
        System.out.println( "年龄:" + age);
    }

    private String checkInfo(){
        return "年龄:" + this.age;
    }

    public Person<String> createPerson(){
        return new Person<String>();
    }

    @Override
    public String getCar(String name) {
        return name + ":奥迪";
    }

}
