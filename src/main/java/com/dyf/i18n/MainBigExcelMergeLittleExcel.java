package com.dyf.i18n;

import com.dyf.i18n.service.FileConvertService;
import com.dyf.i18n.service.TableMergeService;
import com.dyf.i18n.table.ExcelTableHolder;
import com.dyf.i18n.table.TableHolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by yuiff on 2017/8/29.
 */
public class MainBigExcelMergeLittleExcel {
    public static void main(String[] args) throws Exception {
        mainBigExcelMergeLittleExcel();
    }

    public static void mainBigExcelMergeLittleExcel() throws Exception {
        final String inputDirString = "./workfiles/mergeExcel/inputDir/";
        final String outputDirString = "./workfiles/mergeExcel/outputDir/";
        final String mainExcelFileName = "mainExcel.xls";
        final String littleExcelName = "littleExcel.xls";
        final String mergedMainExcelFileName = "mergedMainExcel.zip";

        File mainExcelFile = new File(inputDirString + mainExcelFileName);
        File littleExcelFile = new File(inputDirString + littleExcelName);

        TableMergeService service = new TableMergeService();

        final TableHolder mainTableHolder = new ExcelTableHolder(mainExcelFile);
        final TableHolder littleTableHolder = new ExcelTableHolder(littleExcelFile);
//
//        Map<String, List<String>> ret = service.mergeLittleTableIntoMainTable(littleTableHolder, mainTableHolder);
        OutputStream os = new FileOutputStream(outputDirString + mergedMainExcelFileName);
//        mainTableHolder.write(os);
        ByteArrayOutputStream bo = service.mergeLittleTableIntoMainTableZipWithTip(littleTableHolder, mainTableHolder);
        bo.writeTo(os);
    }
}
