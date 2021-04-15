package com.fullmoon.study.proxy;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @author jingping.liu
 * @date 2021-04-15
 * @description [Javassist](http://www.javassist.org/) 是一个操作 Java 字节码的类库
 */
public class JavassistTest {

    public static void main(String[] args) throws Exception {
        // 获取默认的 ClassPool 对象
        ClassPool cp = ClassPool.getDefault();
        // 使用 ClassPool 生成指定名称的 CtClass 对象，用于创建 Class 对象
        CtClass clazz = cp.makeClass("com.study.proxy.Person");

        // 创建一个字段，设置类型和名称
        CtField field = new CtField(cp.get("java.lang.String"), "name", clazz);
        // 设置该字段的修饰符
        field.setModifiers(Modifier.PRIVATE);

        // 为 CtClass 添加两个方法，上面 `field` 字段的 setter、getter 方法
        clazz.addMethod(CtNewMethod.setter("setName", field));
        clazz.addMethod(CtNewMethod.getter("getName", field));
        // 将 `field` 字段添加到 CtClass 对象中，并设置默认值
        clazz.addField(field, CtField.Initializer.constant("jingping"));

        // 创建一个构造方法，指定参数类型、所属类
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, clazz);
        StringBuffer body = new StringBuffer();
        body.append("{\n this.name=\"liujingping\"; \n}");
        // 设置构造器的内容
        ctConstructor.setBody(body.toString());
        // 将构造器添加到 CtClass 对象中
        clazz.addConstructor(ctConstructor);

        // 创建一个方法，指定返回类型、名称、参数类型、所属类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "say", new CtClass[]{}, clazz);
        // 设置方法的修饰符
        ctMethod.setModifiers(Modifier.PUBLIC);
        body = new StringBuffer();
        body.append("{\n System.out.println(\"say: hello, \" + this.name); \n}");
        // 设置方法的内容
        ctMethod.setBody(body.toString());
        // 将方法添加到 CtClass 对象中
        clazz.addMethod(ctMethod);
        // 设置 .class 文件的保存路径
        clazz.writeFile("D:\\workspaces\\idea\\study-java");

        // 通过 CtClass 对象创建一个 Class 对象
        Class<?> c = clazz.toClass();
        // 创建一个实例对象
        Object obj = c.newInstance();
        Method get = obj.getClass().getMethod("getName");
        System.out.println(get.invoke(obj));
        Method say = obj.getClass().getDeclaredMethod("say");
        say.invoke(obj);
        Method set = obj.getClass().getDeclaredMethod("setName", String.class);
        set.invoke(obj, "liujingping2");
        System.out.println(get.invoke(obj));
        say.invoke(obj);
    }
}
