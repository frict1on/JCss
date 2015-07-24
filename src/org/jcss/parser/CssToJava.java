package org.jcss.parser;

import org.jcss.JCssException;
import org.jcss.model.CssClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Copyright 2015 frict1on@github
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * CssToJava class provides De-serialization logic to read data from cssFile and convert them into JCss specific POJO objects
 *
 * @author frict1on@github
 */
public class CssToJava {

    private static final String CMNT_STRT = "/*";
    private static final String CMNT_END = "*/";
    public static final String CLASS_START = "{";
    public static final String CLASS_END = "}";
    public static final String PROP_VAL_DELIM = ":";
    public static final String PROP_DELIM = ";";

    public static List<CssClass> deSerialize(File cssFile) throws JCssException {

        List<CssClass> styleSheetClasses = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(cssFile))) {
            String data;
            CssClass cssStyle = null;
            List<String> names = new ArrayList<>();
            while ((data = fileReader.readLine()) != null) {
                data = stripComments(data);

                if (data.isEmpty()) {
                    continue;
                }

                if (data.contains(CLASS_START)) {
                    cssStyle = initCssClass(data, names);
                } else if (data.contains(CLASS_END)) {
                    if (cssStyle == null) {
                        continue;
                    }
                    styleSheetClasses.add(cssStyle);
                    cssStyle = null;
                } else if (cssStyle == null) {
                    //Group Selectors
                    for (String s : data.split(",")) {
                        names.add(s);
                    }
                } else {
                    addProperties(data, cssStyle);

                }
            }

            return styleSheetClasses;
        } catch (IOException ioe) {
            throw new JCssException("Exception ocurred while parsing CSS", ioe);

        }
    }

    private static CssClass initCssClass(String data, List<String> names) {
        CssClass cssStyle;
        cssStyle = new CssClass();
        cssStyle.addClassNames(names);
        names.clear();
        cssStyle.addClassName(data.substring(0, data.indexOf(CLASS_START)));
        return cssStyle;
    }

    private static void addProperties(String data, CssClass cssStyle) {
        String[] styles = data.split(PROP_DELIM);
        for (String style : styles) {
            int delimeterIdx = style.lastIndexOf(PROP_VAL_DELIM);
            cssStyle.addPropertyValue(style.substring(0, delimeterIdx).trim(), style.substring(delimeterIdx + 1).trim());
        }
    }

    private static String stripComments(String data) {
        int cmmntStrtIdx = data.indexOf(CMNT_STRT);
        int cmmtEndIdx = data.indexOf(CMNT_END);
        if (cmmntStrtIdx != -1 && cmmtEndIdx != -1) {
            data = data.substring(0, cmmntStrtIdx) + data.substring(cmmtEndIdx + CMNT_END.length());
            data = data.trim();
        }
        return data;
    }
}


