package com.dyf.i18n;

import com.dyf.i18n.excel.ExcelTableHolder;
import com.dyf.i18n.service.FileConvertService;

import java.io.*;

public class MainExcelToOthers {
    //convert excel to many files like timplate
    public static void main(String[] args) throws Exception {
        final String excelDirString = "./workfiles/excel2others/excelinput/";
        final String templateFilenameString = "./workfiles/excel2others/templateinput/template.json";
        final String outputDirString = "./workfiles/excel2others/outputFiles/";
        final String stringPrefix = "\"";
        final String stringSuffix = "\"";

        FileConvertService fileCon = new FileConvertService();
        File excelDir = new File(excelDirString);
        File[] excelFiles = excelDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xls");
            }
        });
        if(excelFiles==null) System.out.println(excelDir.getAbsolutePath()+" have not .xls files in it!");
        for (File xlsfile : excelFiles) {
            System.out.println(xlsfile);
            fileCon.excelToManyAndOutputToFile(new ExcelTableHolder(xlsfile),
                    new File(templateFilenameString), outputDirString + xlsfile.getName()+"/", stringPrefix, stringSuffix);
        }
    }
}
