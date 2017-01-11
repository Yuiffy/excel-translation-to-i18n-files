package com.dyf.i18n.service;

import com.dyf.i18n.excel.ExcelTableHolder;
import com.dyf.i18n.excel.TableHolder;
import com.dyf.i18n.file.KeyValueFileHandler;
import com.dyf.i18n.file.XmlFileHandler;
import com.dyf.i18n.replace.NormalReplacer;
import com.dyf.i18n.replace.Replacer;
import com.dyf.i18n.util.ListStringUtil;
import com.dyf.i18n.util.escaper.Escaper;
import com.dyf.i18n.util.escaper.JsonEscaper;
import com.dyf.i18n.util.escaper.XmlEscaper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by yuiff on 2017/1/3.
 */
public class FileConvertService {
    public int excelToOtherAndOutputToFile(TableHolder tableHolder, File templateFilenameFile, String outputDir, String stringPrefix, String stringSuffix, String escapeType) throws IOException, InvalidFormatException {
        new File(outputDir).mkdirs();
        String template = new String(Files.readAllBytes(Paths.get(templateFilenameFile.getPath())));
        List<String> titles = tableHolder.getFirstRowString();
        Escaper escaper = getEscaper(escapeType);
        List<String> keyList = ListStringUtil.addPrefixSuffix(
                escaper.escape(
                        tableHolder.getColStringWithOutFirstRow(0)
                ), stringPrefix, stringSuffix);
        for (int i = 1; i < titles.size(); i++) {
            List<String> valueList = ListStringUtil.addPrefixSuffix(
                    escaper.escape(
                            tableHolder.getColStringWithOutFirstRow(i)
                    ), stringPrefix, stringSuffix);
            Map<String, String> kvMap = ListStringUtil.list2map(keyList, valueList);

            String lang = titles.get(i);
            String outputFileName = getOutputFileName(templateFilenameFile.getName(), outputDir, lang);
            String outputString = getTranslatedString(template, kvMap);
            try (PrintWriter out = new PrintWriter(outputFileName)) {
                out.println(outputString);
            }
            System.out.println("output File finish:" + outputFileName);
        }
        return 0;
    }

    private Escaper getEscaper(String escapeType) {
        Escaper escaper;
        if (escapeType.equals("xml")) escaper = new XmlEscaper();
        else if (escapeType.equals("json")) escaper = new JsonEscaper();
        else escaper = new JsonEscaper();
        return escaper;
    }

    public String getOutputFileName(String templateFilename, String outputDir, String lang) {
        String[] baseAndExtension = templateFilename.split("\\.(?=[^\\.]+$)");
        String outputFileNameHead = baseAndExtension[0] + "_";
        String outputFIleNameTail = baseAndExtension[1];
        String outputFileName = outputDir + outputFileNameHead + lang + "." + outputFIleNameTail;
        return outputFileName;
    }

    private String getTranslatedString(String template, Map<String, String> kvMap) {
        Replacer replacer = new NormalReplacer(kvMap);
        return replacer.doReplace(template);
    }

    public void ManyXmlToOneExcelFile(List<File> xmlFiles, OutputStream excelOutputStream) throws IOException, SAXException, ParserConfigurationException {
        TableHolder excelHolder = new ExcelTableHolder();
        XmlFileHandler firstXmlHandler = new XmlFileHandler(xmlFiles.get(0));
        List<String> keyList = firstXmlHandler.getKeyList();
        excelHolder.setColumn("string_id", keyList, 0);
        for (int i = 0; i < xmlFiles.size(); i++) {
            File file = xmlFiles.get(i);
            KeyValueFileHandler xmlHandler = new XmlFileHandler(file);
            Map<String, String> kvMap = xmlHandler.getKeyValueMap();
            excelHolder.addColumn(file.getName(), kvMap, 0);
        }
        excelHolder.write(excelOutputStream);
    }
}
