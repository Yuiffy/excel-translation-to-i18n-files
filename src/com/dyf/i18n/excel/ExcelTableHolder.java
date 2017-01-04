package com.dyf.i18n.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuiff on 2017/1/3.
 */
public class ExcelTableHolder implements TableHolder{
    private Sheet sheet;

    public ExcelTableHolder(String filename) throws IOException, InvalidFormatException {
        this(new File(filename));
    }

    public ExcelTableHolder(File file) throws IOException, InvalidFormatException {
        InputStream inp = new FileInputStream(file);
        //根据上述创建的输入流 创建工作簿对象
        Workbook workbook = WorkbookFactory.create(inp);
        sheet = workbook.getSheetAt(0);
    }

    @Override
    public List<String> getFirstRowString() {
        Row firstRow = sheet.getRow(0);
        List<String> list = new ArrayList<>();
        for (Cell cell : firstRow) {
            list.add(cell.toString());
        }
        return list;
    }

    /**
     * with out first row
     *
     * @param colnum
     * @return
     */
    @Override
    public List<String> getColStringWithOutFirstRow(int colnum) {
        List<String> list = new ArrayList<>();
        int siz = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < siz; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(colnum);
            list.add(cell.toString());
        }
        return list;
    }

    /**
     * with out first row
     *
     * @param keyColNum
     * @param valueColNum
     * @param prefix
     * @param suffix      @return
     */
    @Override
    public Map<String, String> getKeyValueMapByTwoCol(int keyColNum, int valueColNum, String prefix, String suffix) {
        List<String> keyList = getColStringWithOutFirstRow(keyColNum);
        List<String> valueList = getColStringWithOutFirstRow(valueColNum);
        if (keyList.size() != valueList.size()) {
            System.out.println("size error! " + keyList.size() + " != " + valueList.size());
            return null;
        }
        keyList = addPrefixSuffix(keyList, prefix, suffix);
        valueList = addPrefixSuffix(valueList, prefix, suffix);
        int siz = keyList.size();
        Map<String, String> mp = new HashMap<>();
        for (int i = 0; i < siz; i++) {
            mp.put(keyList.get(i), valueList.get(i));
        }
        return mp;
    }

    @Override
    public Map<String, String> getKeyValueMapByTwoCol(int keyColNum, int valueColNum) {
        return getKeyValueMapByTwoCol(keyColNum, valueColNum, null, null);
    }

    private List<String> addPrefixSuffix(List<String> keyList, String prefix, String suffix) {
        String pre = prefix == null ? "" : prefix;
        String suf = suffix == null ? "" : suffix;
        List<String> ret = new ArrayList<>(keyList.size());
        for (String k : keyList)
            ret.add(pre + k + suf);
        return ret;
    }

}
