package com.dyf.i18n;

import com.dyf.i18n.replace.NormalReplacer;
import com.dyf.i18n.replace.Replacer;
import com.dyf.i18n.service.FileConvertService;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {

    //使用POI读入excel工作簿文件
    public static void readWorkBook() throws Exception {
        // poi读取excel
        //创建要读入的文件的输入流
        InputStream inp = new FileInputStream("workbook.xls");

        //根据上述创建的输入流 创建工作簿对象
        Workbook wb = WorkbookFactory.create(inp);
        //得到第一页 sheet
        //页Sheet是从0开始索引的
        Sheet sheet = wb.getSheetAt(0);
        //利用foreach循环 遍历sheet中的所有行
        System.out.println(sheet.getRow(0).getPhysicalNumberOfCells());
        for (Row row : sheet) {
            //遍历row中的所有方格
            for (Cell cell : row) {
                //输出方格中的内容，以空格间隔
                System.out.print(cell.toString() + "  ");
            }
            //每一个行输出之后换行
            System.out.println();
        }
        //关闭输入流
        inp.close();
    }

    public static void main(String[] args) throws Exception {
        FileConvertService fileCon = new FileConvertService();
        fileCon.fileConvert("PN16-B-033 CA E-Manual字符串翻译需求-4国.xls", "English823.json", "outputFiles\\");

        Replacer replacer = new NormalReplacer();
        replacer.put("cat", "Garfield");
        replacer.put("beverage", "coffee");
        String template = "cat really needs some beverage.";
        String result = replacer.doReplace(template);
        System.out.println(result);
    }
}
