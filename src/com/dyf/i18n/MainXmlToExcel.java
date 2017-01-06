package com.dyf.i18n;

import com.dyf.i18n.file.KeyValueFileHandler;
import com.dyf.i18n.file.XmlFileHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by yuiff on 2017/1/6.
 */
public class MainXmlToExcel {
    public static void main(String[] args) throws Exception {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse("strings.xml");
//        NodeList stringList = doc.getElementsByTagName("string");
//        System.out.println("共有" + stringList.getLength() + "个string节点");
//        for (int i = 0; i < stringList.getLength(); i++){
//            Node stringNode = stringList.item(i);
//            String name = ((Element)stringNode).getAttribute("name");
//            String value = stringNode.getFirstChild().getNodeValue();
//            System.out.println("name:" + name +"\tvalue:"+value);
//        }
        KeyValueFileHandler xmlFileHandler = new XmlFileHandler(new File("strings.xml"));
        List<String> keyList = xmlFileHandler.getKeyList();
        xmlFileHandler.put(keyList.get(0), "wow!");
        String fs = xmlFileHandler.getString();
        File outf = new File("gank.xml");
        try (PrintWriter out = new PrintWriter(outf)) {
            out.println(fs);
        }
    }
}
