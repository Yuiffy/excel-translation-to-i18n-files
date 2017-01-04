package com.dyf.i18n.excel;

import java.util.List;
import java.util.Map;

/**
 * Created by yuiff on 2017/1/4.
 */
public interface TableHolder {

    List<String> getFirstRowString();

    List<String> getColStringWithOutFirstRow(int colnum);

    Map<String, String> getKeyValueMapByTwoCol(int keyColNum, int valueColNum, String prefix, String suffix);

    Map<String, String> getKeyValueMapByTwoCol(int keyColNum, int valueColNum);
}
