package org.jcss.parser;

import org.jcss.JCssException;
import org.jcss.model.CssClass;
import org.jcss.writer.CssWriter;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * JavaToCss: Use this class to serialize your CSS POJO components into CSS String.
 *
 * @author frict1on@github
 */
public class JavaToCss implements CssSerializer {

    private static volatile JavaToCss instance = null;

    public static JavaToCss getInstance(){
        if(instance == null) {
            synchronized (CssToJava.class) {
                if(instance == null) {
                    instance = new JavaToCss();
                }
            }

        }

        return instance;
    }

    private JavaToCss(){};

    @Override
    public String serialize(List<CssClass> styleClass) throws JCssException {
        StringWriter stringWriter = new StringWriter();
        CssWriter cssBuilder = new CssWriter(stringWriter);
        for(CssClass css : styleClass) {
            Set<String> classNames = css.getClassNames();
            StringBuilder classNameBuilder = new StringBuilder();
            for(String className : classNames) {
                classNameBuilder.append(className + ",");
            }

            classNameBuilder.deleteCharAt(classNameBuilder.length()-1);
            cssBuilder.startClass(classNameBuilder.toString()) ;

            for(Map.Entry<String, String> prop : css.getProperties().entrySet()){
                cssBuilder.property(prop.getKey()).value(prop.getValue());
            }

            cssBuilder.endClass();
        }
        return cssBuilder.build();
    }
}
