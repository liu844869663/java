package com.fullmoon.study.list;

import java.util.*;

/**
 * 关于HashMap的学习 <p>
 *
 * 为什么HashMap的默认值为16？ <p>
 * 我认为默认值为16是在一定程度上解决哈希碰撞 <p>
 * 提示：如果大致清楚存储的元素的个数,初始化时最好指定容量大小,避免后续的容量扩增造成更多性能的损耗 <p>
 *
 * 为什么HashMap容量必须为2的n次方？ <p>
 * 通过哈希算法得到key的哈希值时,需要对其取模（对数组的大小求余）计算出在数组中的位置, X % ( 2^n ) = X & ( 2^n - 1),按位与（&）运算符比 % 求余运算符性能上提高很多,
 * 因为&直接对内存中的数据进行计算,无须转换成二进制,
 * 还有一个好处就是可以解决负数的问题,2^n - 1一定是一个正数,最高位为0,按位与计算后最高位一定是0,得到的数一定是一个正数 <p>
 *
 * 为什么HashMap的负载因子为0.75？ <p>
 * 我认为是对空间和时间上的一个权衡,扩容是对整个数组进行重新计算,需要耗费很多时间,
 * 当设置为0.5时,假如容量大小为1024,在容量超过一半时就扩容,且当容量越来越大,空余的空间也大,
 * 当设置为0.9时,存储的个数更多,增加了哈希碰撞的概率 <p>
 *
 * 为什么HashMap的设置的最大容量为2的30次方？ <p>
 * 因为Integer的最大值为2的31次方减1 <p>
 *
 * 为什么HashMap一个桶由链表转换成红黑树的阈值为8？ <p>
 * 空间和时间上的权衡，桶中的个数大于8的概率几乎为0 <p>
 * 红黑树的新增成本高，查询成本低 O(log n) <p>
 * 链表的新增成本低，查询成本高 O(n) <p>
 * 桶中的个数小于8红黑树的查询比链表并没有很大的提升效率，反而增加了新增元素时候的成本，且大于8的概率几乎为0 <p>
 *
 * HashMap什么时候会进行rehash？ <p>
 * 当容器中存放的数据的个数大于阈值（负载因子*容器大小）时，进行扩容,
 * 当一个桶中的元素大于8并且容器的容量小于64时，进行扩容 <p>
 *
 * JDK8中HashMap做了哪些修改？ <p>
 * 1.8的HashMap添加了红黑树的存储方式，当一个桶中的元素个数大于一定值时，将之前的链表存储方式转换成红黑树存储方式 <p>
 * 提升了查询效率，解决了之前在多线程下可能会导致的死锁现象 <p>
 *
 * 为什么JDK8版本前会出现死锁？ <p>
 * 因为当JDK8版本前的HashMap需要进行扩容时，里面的rehash方法中可能会将一个桶中的数据（采用链表）进行倒序 <p>
 * 假如 3 -> 7，会变成7 -> 3 <p>
 * 多线程下，当Thread1执行到获取3的下一个元素7时，Thread2可能已经倒序了，而Thread1获取到3的下一个元素7又指向了3，一直循环，从而导致死锁现象 <p>
 * JDK8的优化是采用的是位桶 + 链表/红黑树的方式，当某个位桶的链表的长度超过 8 的时候，这个链表就将转换成红黑树 <p>
 *
 * 为什么HashMap的key和value都可以为null，而HashTable和ConcurrentHashMap的key和value都不能为null？ <p>
 * 首先我们要清楚Map的key或者value一般不存为null <p>
 * 因为HashTable和ConcurrentHashMap是线程安全的，多用于多线程，并发,
 * 获取到key对应的value为null时，如果我们通过containsKey()方法去判断是value的值为null，还是这个key根本不存在容器中，无法判断 ,
 * 因为get方法到containsKey期间可能被其他线程修改了该key的值，所以无法确认 <p>
 *
 * 为什么对HashMap进行遍历的时候无法对元素进行操作？
 * HashMap的remove方法中删除前会对现在的数量和开始集合的数量进行比较，不相等会抛出异常
 * 当我们在遍历的时候，第一次删除一个元素不会有问题，因为删除之前数量没有变，在第二次开始删除之前会进行比较，不相等所以抛出异常
 *
 * @author jingping.liu
 * @date 2019-09-12
 *
 */
public class HashMapTest {
    public static void main(String[] args){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(null, "2");
        System.out.println(map.get(null));
        System.out.println(1 << 30);
        System.out.println(Integer.MAX_VALUE);
        /*Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }*/
        map.put(3,"3");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            map.remove(entry.getKey());
        }
        System.out.println(map.size());
        Integer i = 1;
        i.hashCode();
    }
}
