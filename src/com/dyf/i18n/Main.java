package com.dyf.i18n;

import com.dyf.i18n.excel.ExcelTableHolder;
import com.dyf.i18n.service.FileConvertService;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        final String excelDirString = "./excels";
        final String templateFilenameString = "template.json";
        final String outputDirString = "outputFiles/";
        final String stringPrefix = "\"";
        final String stringSuffix = "\"";

        FileConvertService fileCon = new FileConvertService();
        File excelDir = new File("./excels/");
        File[] excelFiles = excelDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xls");
            }
        });
        for (File xlsfile : excelFiles) {
            System.out.println(xlsfile);
            fileCon.fileConvertAndOutputToFile(new ExcelTableHolder(xlsfile),
                    templateFilenameString, outputDirString + xlsfile.getName()+"/", stringPrefix, stringSuffix);
        }
    }
}
