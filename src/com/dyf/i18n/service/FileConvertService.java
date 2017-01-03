package com.dyf.i18n.service;

import com.dyf.i18n.excel.Exceller;
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
import java.util.regex.Pattern;

/**
 * Created by yuiff on 2017/1/3.
 */
public class FileConvertService {
    public int fileConvert(String excelFilename, String templateFilename, String outputDir) throws IOException, InvalidFormatException {
        String[] baseAndExtension = templateFilename.split("\\.(?=[^\\.]+$)");
        String outputFileNameHead = baseAndExtension[0] + "_";
        String outputFIleNameTail = baseAndExtension[1];
        new File(outputDir).mkdirs();
        Exceller exceller = new Exceller(excelFilename);
        String template = new String(Files.readAllBytes(Paths.get(templateFilename)));
        List<String> titles = exceller.getFirstRowString();
        //System.out.println(Pattern.quote("Try another channel."));
        //System.out.println(template);
        System.out.println(exceller.getColStringWithOutFirstRow(0));
        for (int i = 1; i < titles.size(); i++) {
            Map<String, String> kvMap = exceller.getKeyValueMapByTwoCol(0, i);
            String lang = titles.get(i);
            String outputFileName = outputDir + outputFileNameHead + lang + "."+outputFIleNameTail;
            String outputString = getTranslatedString(template, kvMap);
            //System.out.println(kvMap);
            //System.out.println(exceller.getColStringWithOutFirstRow(i));
            //System.out.println(outputString);
            try (PrintWriter out = new PrintWriter(outputFileName)) {
                out.println(outputString);
            }
        }
        return 0;
    }

    private String getTranslatedString(String template, Map<String, String> kvMap) {
        Replacer replacer = new NormalReplacer(kvMap);
        return replacer.doReplace(template);
    }
}
