package com.fullmoon.study.reflect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author jingping.liu
 * @Date 2019-09-27
 * @Description TODO
 */
public class Person<T> implements Serializable,Cloneable {

    private String gender;

    private T brother;

    public Person(){
        super();
    }

    public Person(String gender){
        super();
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public T getBrother() {
        return brother;
    }

    public void setBrother(T brother) {
        this.brother = brother;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person("1");
        System.out.println(person.getGender());
        Person person1 = (Person) person.clone();
        person1.setGender("2");
        System.out.println(person.getGender());
        List<Person> list = new ArrayList<>();
        list.add(new User());
        System.out.println(list.remove(0));
    }
}
