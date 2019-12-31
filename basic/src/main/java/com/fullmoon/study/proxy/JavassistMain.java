package com.fullmoon.study.proxy;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @Author jingping.liu
 * @Date 2019-10-11
 * @Description Javassist测试类
 */
public class JavassistMain {
    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault(); // 创建ClassPool
        // 使用ClassPool生成指定类名的类
        CtClass clazz = cp.makeClass("com.fullmoon.study.proxy.JavassistTest");

        StringBuffer body = null;
        // 创建字段，指定字段类型、名称、所属类
        CtField field = new CtField(cp.get("java.lang.String"),"prop", clazz);
        // 设置该字段的修饰符
        field.setModifiers(Modifier.PRIVATE);

        // 设置字段的setter和getter方法
        clazz.addMethod(CtNewMethod.setter("setProp", field));
        clazz.addMethod(CtNewMethod.getter("getProp", field));
        // 设置字段的初始化值并将字段添加到类中
        clazz.addField(field, CtField.Initializer.constant("MyName"));

        // 创建构造方法，指定构造方法的参数类型和构造方法所属类
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, clazz);
        body = new StringBuffer(); // 设置方法体
        body.append("{\n prop=\"MyName\";\n}");
        ctConstructor.setBody(body.toString());
        clazz.addConstructor(ctConstructor); // 将无参构造方法添加至类中

        // 创建指定名称的方法，设置返回值，方法名称，方法参数列表，所属类
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "execute", new CtClass[]{}, clazz);
        ctMethod.setModifiers(Modifier.PUBLIC); // 设置方法的修饰符
        body = new StringBuffer(); // 该方法的方法体
        body.append("{\n System.out.println(\"execute():\" + this.prop);\n}");
        ctMethod.setBody(body.toString());
        clazz.addMethod(ctMethod); // 将该方法添加至类中
        //clazz.writeFile("D:\\toolkits"); // 将上面定义的类保存至指定目录

        // 通过反射对该类进行操作
        Class<?> c = clazz.toClass();
        Object o = c.newInstance();
        Method method = o.getClass().getDeclaredMethod("execute");
        method.invoke(o);
        Method method1 = o.getClass().getDeclaredMethod("setProp",String.class);
        method1.invoke(o,"test");
        Method method2 = o.getClass().getMethod("getProp");
        Object result = method2.invoke(o);
        System.out.println(result);
        method.invoke(o);
    }
}
