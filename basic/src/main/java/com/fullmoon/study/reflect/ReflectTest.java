package com.fullmoon.study.reflect;

import java.lang.reflect.*;

/**
 * @Author jingping.liu
 * @Date 2019-09-27
 * @Description TODO
 */
public class ReflectTest {

    private static final byte[] JAR_MAGIC = { 'P', 'K', 3, 4 };

    public static void main(String[] args) throws Exception {
        // Class<?> clazz = User.class;
        Class<?> clazz = Class.forName("com.fullmoon.study.reflect.User");
        System.out.println(clazz);
//        Class<?> clazz2 = new User().getClass();
//        System.out.println(clazz2 == clazz);
//        Class<?> clazz3 = Class.forName("com.fullmoon.study.reflect.User");
//        System.out.println(clazz3 == clazz);

        // 创建一个User实例
        Object obj = clazz.newInstance();
        System.out.println(obj);

        // getMethods获取所有public修饰的方法，getDeclaredMethods获取所有定义的方法
        System.out.println("-----------------获取所有方法-----------------");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods){
            // 判断是否为桥接方法，在jdk1.5后引入泛型，当一个类实现了某个泛型接口<T>时，如果指定了入参类型(String param)，
            // 编译器编译时会生成一个方法类型为(Object param)的方法，内部调用(String param)方法，该方法则为编译器生成的桥接方法
            // 例如其中的 public volatile java.lang.String getCar(java.lang.Object);
            m.isBridge();
            StringBuffer str = new StringBuffer();
            str.append(Modifier.toString(m.getModifiers()));
            str.append(" ");
            str.append(m.getReturnType().getSimpleName());
            str.append(" ");
            str.append(m.getName());
            str.append("(");
            if(m.getParameterCount() > 0){
                for(int i = 0; i < m.getParameterCount(); i++){
                    if (i != 0){
                        str.append(",");
                    }
                    str.append(m.getParameterTypes()[i].getName());
                }
            }
            str.append(");");
            System.out.println(str.toString());
        }
        System.out.println("-----------------获取单个方法-----------------");
        // 获取login方法
        Method method = clazz.getDeclaredMethod("login", String.class, int.class);
        // 取消语言访问检查，关闭安全检查
        method.setAccessible(true);
        Object result = method.invoke(obj, "23", 20);
        System.out.println(result);

        // 当调用setAccessible时，里面的方法会执行如下操作，根据Java的安全管理器来检测suppressAccessChecks权限，不通过则抛出异常
        // 一般我们在setAccessible(true)之前最好自己执行下面操作，如果通过则可以setAccessible(true)，否则不执行，避免抛出异常
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
        }

        System.out.println("-----------------获取所有字段-----------------");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            Type t = field.getGenericType();
            if(t instanceof ParameterizedType){ // 如果为泛型类型
                // 参数化类型
                ParameterizedType pt = (ParameterizedType)field.getGenericType();
                Type[] actualTypes = pt.getActualTypeArguments(); // 获取实际参数类型
                System.out.print(pt.getRawType().getTypeName() + "<"); // 获取ParameterizedType类型
                for(Type type : actualTypes){
                    System.out.print(type.getTypeName() + " ");
                }
                System.out.println(">");
                System.out.println(pt.getOwnerType());
            }
            StringBuffer str = new StringBuffer();
            str.append(Modifier.toString(field.getModifiers()) + " ");
            str.append(field.getType().getName() + " ");
            str.append(field.getName());
            System.out.println(str.toString());
        }

        System.out.println("-----------------获取单个字段-----------------");
        // 只能getDeclaredField才能获取到private修饰的字段
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        field.set(obj, "小明");
        System.out.println(field.get(obj));

        System.out.println("-----------------获取所有构造器-----------------");
        Constructor<?>[] cons = clazz.getConstructors();
        for(Constructor<?> con : cons){
            if(con.getParameterCount() == 0){
                Object user = con.newInstance();
                Method setName = clazz.getDeclaredMethod("setName", String.class);
                setName.invoke(user, "小黑");
                Method setAge = clazz.getDeclaredMethod("setAge", int.class);
                setAge.invoke(user, 19);
                System.out.println(user.toString());
            }
            if(con.getParameterCount() == 2){
                Object user = con.newInstance("小白",20);
                System.out.println(user.toString());
            }
        }

        System.out.println("-----------------获取单个构造器-----------------");
        Constructor<?> con = clazz.getConstructor(String.class);
        Object o = con.newInstance("小蓝");
        System.out.println(o.toString());

        Class<?> clazz2 = Class.forName("com.fullmoon.study.reflect.User").getSuperclass();
        System.out.println(clazz2.getName());

        System.out.println("-----------------获取该类实现的接口-----------------");
        // 如果某个类使用了abstract修饰并且没有实现接口中的某些方法，可以通过下述方法获取接口内的信息
        Class<?>[] classes = clazz.getInterfaces();
        for (Class<?> c : classes){
            // 接口中不允许出现private、protected修饰符，所以只需getMethods()
            for (Method m : c.getMethods()){
                // 获取方法返回类型(如果返回类型是泛型则会包含泛型类型，Person<String>)，而getReturnType只会返回Person
                System.out.println(m.getGenericReturnType());
                // 获取方法所属类
                System.out.println(m.getDeclaringClass().getName());
                // getSimpleName()获取类的名称，不包含包名
                System.out.println(c.getSimpleName() + "$" + m.getName());
            }
        }
    }



}
