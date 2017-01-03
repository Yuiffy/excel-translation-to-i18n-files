package com.dyf.i18n.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

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
public class Exceller {
    private Sheet sheet;
    public Exceller(String filename) throws IOException, InvalidFormatException {
        InputStream inp = new FileInputStream(filename);

        //根据上述创建的输入流 创建工作簿对象
        Workbook workbook = WorkbookFactory.create(inp);
        sheet = workbook.getSheetAt(0);
    }
    public int colSize(){
        //如果中间有空格会获取不到，如果需要处理这样的情况，可以考虑getLast什么的和getFirst什么的。
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    public List<String> getFirstRowString(){
        Row firstRow = sheet.getRow(0);
        List<String> list = new ArrayList<>();
        for(Cell cell:firstRow){
            list.add(cell.toString());
        }
        return list;
    }

    /**
     * with out first row
     * @param colnum
     * @return
     */
    public List<String> getColStringWithOutFirstRow(int colnum){
        List<String>list = new ArrayList<>();
        int siz = sheet.getPhysicalNumberOfRows();
        for (int i=1;i<siz;i++ ) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(colnum);
            list.add(cell.toString());
        }
        return list;
    }

    /**
     * with out first row
     * @param keyColNum
     * @param valueColNum
     * @return
     */
    public Map<String,String> getKeyValueMapByTwoCol(int keyColNum, int valueColNum){
        List<String> keyList = getColStringWithOutFirstRow(keyColNum);
        List<String> valueList = getColStringWithOutFirstRow(valueColNum);
        if(keyList.size()!=valueList.size()){
            System.out.println("size error! "+keyList.size()+" != "+valueList.size());
            return null;
        }
        int siz = keyList.size();
        Map<String,String> mp = new HashMap<>();
        for(int i =0; i<siz; i++){
            mp.put(keyList.get(i), valueList.get(i));
        }
        return mp;
    }

}
