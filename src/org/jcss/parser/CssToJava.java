package org.jcss.parser;

import org.jcss.model.CSSClass;

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

    public static List<CSSClass> readCSSContents(File cssFile){

        List<CSSClass> styleSheets = new ArrayList<>();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(cssFile))) {
            String data;
            CSSClass cssStyle = null;
            List<String> names = new ArrayList<>();
            while(( data = fileReader.readLine()) != null) {
                System.out.println(data);

                int cmmntStrtIdx = data.indexOf("/*");
                int cmmtEndIdx = data.indexOf("*/");
                if(cmmntStrtIdx != -1 && cmmtEndIdx != -1) {
                    data = data.substring(0, cmmntStrtIdx) + data.substring(cmmtEndIdx + 2);
                    data = data.trim();
                }

                if(data.isEmpty()) {
                    continue;
                }
                if(data.contains("{")) {
                    cssStyle = new CSSClass();
                    cssStyle.addClassNames(names);
                    names.clear();
                    cssStyle.addClassName(data.substring(0, data.indexOf("{")));
                } else if(data.contains("}")) {
                    if(cssStyle == null) {
                        continue;
                    }
                    styleSheets.add(cssStyle);
                    cssStyle = null;
                } else if(cssStyle == null) {
                    for(String s : data.split(",")) {
                        names.add(s);
                    }
                } else {

                    String[] styles = data.split(";");
                    for(String style : styles) {
                        String[] cssStyleData = style.split(":", -1);
                        if(cssStyleData.length > 2) {
                            StringBuilder complexStyle = new StringBuilder();
                            for(int i=1; i < cssStyleData.length; i++) {
                                complexStyle.append(cssStyleData[i].trim());
                                complexStyle.append(":");
                            }
                            complexStyle.deleteCharAt(complexStyle.length()-1);
                            cssStyle.addStyle(cssStyleData[0].trim(), complexStyle.toString());
                        } else {
                            cssStyle.addStyle(cssStyleData[0].trim(), cssStyleData[1].trim());
                        }

                    }
                }
            }

            return styleSheets;
        } catch (IOException ioe) {

        }
        return null;
    }
}


