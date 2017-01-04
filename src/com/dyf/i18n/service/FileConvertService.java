package com.dyf.i18n.service;

import com.dyf.i18n.excel.ExcelTableHolder;
import com.dyf.i18n.excel.TableHolder;
import com.dyf.i18n.replace.NormalReplacer;
import com.dyf.i18n.replace.Replacer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by yuiff on 2017/1/3.
 */
public class FileConvertService {
    public int fileConvertAndOutputToFile(TableHolder tableHolder, String templateFilename, String outputDir, String stringPrefix, String stringSuffix) throws IOException, InvalidFormatException {

        new File(outputDir).mkdirs();
        String template = new String(Files.readAllBytes(Paths.get(templateFilename)));
        List<String> titles = tableHolder.getFirstRowString();
        for (int i = 1; i < titles.size(); i++) {
            Map<String, String> kvMap = tableHolder.getKeyValueMapByTwoCol(0, i, stringPrefix, stringSuffix);
            String lang = titles.get(i);
            String outputFileName = getOutputFileName(templateFilename, outputDir, lang);
            String outputString = getTranslatedString(template, kvMap);
            try (PrintWriter out = new PrintWriter(outputFileName)) {
                out.println(outputString);
            }
            System.out.println("output File finish:"+outputFileName);
        }
        return 0;
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
}
