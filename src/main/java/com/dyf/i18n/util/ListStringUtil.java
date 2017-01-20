package com.dyf.i18n.util;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;

/**
 * Created by yuiff on 2017/1/11.
 */
public class ListStringUtil {
    static private void addPrefixSuffix(String s, String prefix, String suffix) {
        s = prefix + s + suffix;
    }

    static public List<String> addPrefixSuffix(List<String> list, String prefix, String suffix) {
        String pre = prefix == null ? "" : prefix;
        String suf = suffix == null ? "" : suffix;
        List<String> ret = new ArrayList<>(list.size());
        for(String item:list){
            ret.add(pre + item + suf);
        }
        return ret;
    }

    static public List<String> escapeXml(List<String> list) {
        List<String> ret = new ArrayList<>(list.size());
        for(String item:list){
            ret.add(StringEscapeUtils.escapeXml11(item));
        }
        return ret;
    }

    static public List<String> unescapeXml(List<String> list) {
        List<String> ret = new ArrayList<>(list.size());
        for(String item:list){
            ret.add(StringEscapeUtils.unescapeXml(item));
        }
        return ret;
    }

    static public List<String> escapeJson(List<String> list) {
        List<String> ret = new ArrayList<>(list.size());
        for(String item:list){
            ret.add(StringEscapeUtils.escapeJson(item));
        }
        return ret;
    }

    static public List<String> unescapeJson(List<String> list) {
        List<String> ret = new ArrayList<>(list.size());
        for(String item:list){
            ret.add(StringEscapeUtils.unescapeJson(item));
        }
        return ret;
    }

    static public <K,V> Map<K, V> list2map(List<K> keyList, List<V> valueList) {
        Iterator<K> i1 = keyList.iterator();
        Iterator<V> i2 = valueList.iterator();
        Map<K, V> ret = new HashMap<>();
        while (i1.hasNext() && i2.hasNext()) {
            ret.put(i1.next(), i2.next());
        }
        if (i1.hasNext() || i2.hasNext())
            System.out.println("key value list not same size: " + keyList.size() + "," + valueList.size());
        return ret;
    }
}
