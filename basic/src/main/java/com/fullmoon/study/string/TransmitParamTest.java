package com.fullmoon.study.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jingping.liu
 * @date 2021-04-13
 * @description 传值测试
 */
public class TransmitParamTest {

    private static void proceed(List<Node> nodes) {
        // 在 JAVA 中都是方法“传值”
        // 拿到的参数实际上是 nodes 数据的一份拷贝，在方法中无法直接修改它
        // 不过可以通过它提供的方法进行修改
        System.out.println("[proceed 3]" + nodes);
        nodes = new ArrayList<>();
        System.out.println("[proceed 4]" + nodes);
    }

    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        System.out.println("[main 1]" + nodes);
        Node node = new Node();
        node.setName("1");
        nodes.add(node);
        System.out.println("[main 2]" + nodes);
        proceed(nodes);
        System.out.println("[main 5]" + nodes.size());
        System.out.println("[main 6]" + nodes);
    }

    public static class Node {
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString(){
            return "name: " + this.name;
        }
    }
}
