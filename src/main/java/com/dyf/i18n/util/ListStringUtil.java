package com.dyf.i18n.util;

import com.dyf.i18n.util.escaper.Escaper;
import com.dyf.i18n.util.escaper.JsonEscaper;
import com.dyf.i18n.util.escaper.XmlEscaper;

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
        for (String item : list) {
            ret.add(pre + item + suf);
        }
        return ret;
    }

    static public List<String> escapeXml(List<String> list) {
        Escaper escaper = new XmlEscaper();
        return escaper.escape(list);
    }

    static public List<String> unescapeXml(List<String> list) {
        Escaper escaper = new XmlEscaper();
        return escaper.unescape(list);
    }

    static public List<String> escapeJson(List<String> list) {
        Escaper escaper = new JsonEscaper();
        return escaper.escape(list);
    }

    static public List<String> unescapeJson(List<String> list) {
        Escaper escaper = new JsonEscaper();
        return escaper.unescape(list);
    }

    static public <K, V> Map<K, V> list2map(List<K> keyList, List<V> valueList) {
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

    static public boolean isNotTranslated(String str, String engString, String[] prefix, String[] suffix) {
        if (str == null || str.isEmpty() || "*".equals(str) || str.equals(engString) || str.equals("数据库未找到"))
            return true;
        for (int i = 0; i < prefix.length; i++) {
            if (str.equals(prefix[i] + suffix[i]) || (prefix[i] + "*" + suffix[i]).equals(str) || str.equals(engString) || str.equals(prefix[i] + "数据库未找到" + suffix[i]))
                return true;
        }
        return false;
    }

    static public <K, V> Map<K, V> list2mapPreferDifferent(List<K> keyList, List<V> valueList, String[] prefix, String[] suffix) {
        Iterator<K> i1 = keyList.iterator();
        Iterator<V> i2 = valueList.iterator();
        Map<K, V> ret = new HashMap<>();
        while (i1.hasNext() && i2.hasNext()) {
            K key = i1.next();
            V value = i2.next();
            if (key.equals(": \"Cooking\"")) {
                System.out.println("Cooking! value=" + value + ", key=" + key + ", isnottran=" + isNotTranslated((String) value, (String) key, prefix, suffix) + ", contain=" + ret.containsKey(key));
            }
            if (ret.containsKey(key) && isNotTranslated((String) value, (String) key, prefix, suffix)) {
                //已经存过这个key的value了，而且当前的value又像没翻译，就不存。这就是更倾向于key和value不同的情况。
            } else {
                ret.put(key, value);
            }
        }
        if (i1.hasNext() || i2.hasNext())
            System.out.println("key value list not same size: " + keyList.size() + "," + valueList.size());
        return ret;
    }

    static public <K, V> Map<K, V> array2map(K[] keyList, V[] valueList) {
        Map<K, V> ret = new HashMap<>();
        int i;
        for (i = 0; i < keyList.length && i < valueList.length; i++) {
            ret.put(keyList[i], valueList[i]);
        }
        if (i < keyList.length || i < valueList.length)
            System.out.println("key value list not same size: " + keyList.length + "," + valueList.length);
        return ret;
    }

    static public boolean isLookLikeEmpty(String s) {
        if (s == null || s.isEmpty()) return true;
        if (s.trim().isEmpty()) return true;
        return false;
    }
}
