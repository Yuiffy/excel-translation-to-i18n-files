package com.dyf.i18n.file;

import com.dyf.i18n.util.FileType;
import com.dyf.i18n.util.escaper.DontEscaper;
import com.dyf.i18n.util.escaper.Escaper;
import com.dyf.i18n.util.escaper.JsonEscaper;
import com.dyf.i18n.util.escaper.XmlEscaper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by yuiff on 2017/1/18.
 */
public class FileHandlerFactory {
    public static KeyValueFileHandler getEscaper(File file, FileType fileType) throws IOException, SAXException, ParserConfigurationException {
        switch (fileType) {
            case json:
                return new JsonFileHandler("");
            case xml:
                return new XmlFileHandler(file);
            default:
                return null;
        }
    }
}
