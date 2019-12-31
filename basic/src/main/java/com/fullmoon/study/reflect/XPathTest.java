package com.fullmoon.study.reflect;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

/**
 * @Author jingping.liu
 * @Date 2019-09-27
 * @Description TODO
 */
public class XPathTest {
    public static void test() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // 设置相关配置
        documentBuilderFactory.setValidating(false);
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(false);
        documentBuilderFactory.setCoalescing(false);
        documentBuilderFactory.setExpandEntityReferences(true);
        // 创建DocumentBuilder
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        // 设置异常处理
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("error:" + exception.getMessage());
            }
            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("fatalError:" + exception.getMessage());
            }
            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("WARN:" + exception.getMessage());
            }
        });
        // 将文档加载到Document中
        Document doc = builder.parse("basic/src/main/resources/test.xml");
        // 创建XPath工程
        XPathFactory factory = XPathFactory.newInstance();
        // 创建XPath对象
        XPath xpath = factory.newXPath();
        // 编译XPath表达式
        XPathExpression expression = xpath.compile("//book[author='Neal Stephenson']/title/text()");
        // 通过XPath结合表达式获取Document对象中的结果
        Object result = expression.evaluate(doc, XPathConstants.NODESET);
        // 将结果强制转换成节点
        NodeList nodes = (NodeList)result;
        for(int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
    }

    public static void main(String[] args) throws Exception {
        XPathTest.test();
    }
}
