package com.dyf.i18n.service;

import com.dyf.i18n.table.ExcelTableHolder;
import com.dyf.i18n.table.TableHolder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by yuiff on 2017/8/29.
 */
public class TableMergeService {

    public Map<String, List<String>> mergeLittleTableIntoMainTable(TableHolder littleTableHolder, TableHolder mainTableHolder) {
        /*初始化main表的行和列的map，用于根据little表的第一列和第一行来定位main表的对应位置*/
        List<String> mainTitles = mainTableHolder.getFirstRowString();
        Map<String, Integer> mainTitlesNameColMap = new HashMap<>();
        for (int i = 0; i < mainTitles.size(); i++) {
            mainTitlesNameColMap.put(mainTitles.get(i), i);
        }

        List<String> mainKeys = mainTableHolder.getColStringWithOutFirstRow(0);
        Map<String, Integer> mainKeyColMap = new HashMap<>();
        //已经不包含firstRow
        for (int i = 0; i < mainKeys.size(); i++) {
            mainKeyColMap.put(mainKeys.get(i), i);
        }

        List<String> theLanguagesNotInMain = new ArrayList<>();
        List<String> theKeyNotInMain = new ArrayList<>();

        List<String> littleTitles = littleTableHolder.getFirstRowString();
        List<String> littleKeys = littleTableHolder.getColStringWithOutFirstRow(0);
        Boolean isFirstRunCol = true;
        /*第0列是key，所以j从1开始*/
        for (int j = 1; j < littleTitles.size(); j++) {
            String colTitle = littleTitles.get(j);
            Integer mainColNum = mainTitlesNameColMap.get(colTitle);
            System.out.println(j + ". " + colTitle + " , " + mainColNum);
            if (mainColNum == null) {
//                System.out.println("can not find the title in main table:" + colTitle);
//                System.out.println("mainTitles=" + mainTitles);
                theLanguagesNotInMain.add(colTitle);
                //TODO:统计
            } else {
                List<String> mainCol = mainTableHolder.getColStringWithOutFirstRow(mainColNum);
                List<String> littleCol = littleTableHolder.getColStringWithOutFirstRow(j);
                for (int i = 0; i < littleKeys.size(); i++) {
                    String littleKey = littleKeys.get(i);
                    Integer mainRowNum = mainKeyColMap.get(littleKey);
                    if (mainRowNum == null) {
                        //TODO:统计2
//                        System.out.println("can not find the key in first column of main table:" + littleKey);
                        if (isFirstRunCol) theKeyNotInMain.add(littleKey);
                    } else {
                        String littleValue = littleCol.get(i);
                        String oldMainValue = mainCol.get(mainRowNum);//TODO: 这个变量无转换用途，是要用于统计或log
                        mainCol.set(mainRowNum, littleValue);
//                        System.out.println("change!"+oldMainValue+" => "+littleValue);
                    }
                }
                isFirstRunCol = false;
                mainTableHolder.setColumn(colTitle, mainCol, mainColNum);
            }
        }
        System.out.println("the title not in main:" + theLanguagesNotInMain);
        System.out.println("the key not in main:" + theKeyNotInMain);
        HashMap<String, List<String>> ret = new HashMap<>();
        ret.put("languageNotIn", theLanguagesNotInMain);
        ret.put("keyNotIn", theKeyNotInMain);
        return ret;
    }

    public ByteArrayOutputStream mergeLittleTableIntoMainTableZipWithTip(TableHolder littleTableHolder, TableHolder mainTableHolder) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bo);
        Map<String, List<String>> thisResult = mergeLittleTableIntoMainTable(littleTableHolder, mainTableHolder);

        List<String> languageNotIn = thisResult.get("languageNotIn");
        List<String> keyNotIn = thisResult.get("keyNotIn");
        TableHolder languageNotInHolder = new ExcelTableHolder();
        languageNotInHolder.addRow(languageNotIn);
        TableHolder keyNotInHolder = new ExcelTableHolder();
        keyNotInHolder.setColumn("ENG", keyNotIn, 0);

        zipOut.putNextEntry(new ZipEntry("mergedMainTable.xls"));
        mainTableHolder.write(zipOut);
//        zipOut.write(outputString.getBytes("UTF-8"));  //这个地方一定要加字符集，因为getBytes字符集是根据系统而定的，这里必须写死
        zipOut.closeEntry();

        zipOut.putNextEntry(new ZipEntry("tip_engNotInMainTable.xls"));
        languageNotInHolder.write(zipOut);
        zipOut.closeEntry();

        zipOut.putNextEntry(new ZipEntry("tip_languageNotInMainTable.xls"));
        keyNotInHolder.write(zipOut);
        zipOut.closeEntry();

        zipOut.close();
        return bo;
    }
}