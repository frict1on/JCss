package org.jcss.parser;

import org.jcss.JCssException;
import org.jcss.model.CssClass;
import org.jcss.writer.CssWriter;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by i307274 on 7/27/15.
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
