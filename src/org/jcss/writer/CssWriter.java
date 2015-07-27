package org.jcss.writer;

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

import org.jcss.JCssException;
import org.jcss.validators.RegexValidationUtil;

import java.io.IOException;
import java.io.Writer;


/**
 * CssWriter provides a convenient way to write CSS text conforming to CSS Syntax rules
 *
 * @author frict1on@github
 */
public class CssWriter {
    private Writer writer;
    private int classesStarted;
    private int classesEnded;
    private String currentClass;
    private boolean propertyStarted;
    private String currentProperty;

    public CssWriter(Writer writer) {
        this.writer = writer;
    }

    public CssWriter startClass(String className) throws JCssException {
        if(isClassOpen()) {
            throw new IllegalArgumentException("You need to close the previous CSS class before writing a new one");
        }
        try {
            writer.append(className);
            writer.append(" {\n");
            currentClass = className;
            classesStarted++;

        } catch (IOException e) {
            throw new JCssException("Exception while creating class:" + currentClass, e);
        }

        return this;
    }

    private boolean isClassOpen() {
        return classesStarted != classesEnded;
    }

    public CssWriter endClass() throws JCssException {
        if(isClassClosed()) {
            throw new IllegalArgumentException("You need to start a new CSS class before invoking endClass");
        }
        try {
            writer.append("}\n");
            classesEnded++;

        } catch (IOException e) {
            throw new JCssException("Exception while creating class:" + currentClass, e);
        }

        return this;
    }

    private boolean isClassClosed() {
        return classesStarted == classesEnded;
    }

    public CssWriter comment(String commentTxt) throws JCssException {
        if(!RegexValidationUtil.isValidComment(commentTxt)) {
            throw new JCssException("Not a well formed comment");
        }
        try {
            writer.append("/*");
            writer.append(commentTxt);
            writer.append("*/\n");

        } catch (IOException e) {
            throw new JCssException("Exception while adding comment:" + commentTxt, e);
        }

        return this;
    }

    public CssWriter property(String key)  throws JCssException {
        if(isClassClosed() || propertyStarted) {
            throw new IllegalArgumentException("Cannot add a property without starting a class");
        }

        if(!validatePropertyKey(key)) {
            throw new IllegalArgumentException("Property key validation failed; property name may not have a \":\" in it");
        }

        try {
            writer.append("\t");
            writer.append(key);
            writer.append(":");
            propertyStarted = true;
            currentProperty = key;
        } catch (IOException e) {
            throw new JCssException("Exception while writing property :" + key + " for class: " + currentClass, e);
        }

        return this;
    }

    public CssWriter value(String value) throws JCssException {
        return value(value, false);
    }

    public CssWriter value(String value, boolean isImportant) throws JCssException {
        if(!propertyStarted) {
            throw new IllegalArgumentException("Cannot add a value without a key property");
        }
        try {
            writer.append(value);
            if(isImportant) {
                writer.append(" !important");
            }
            writer.append(";\n");
            propertyStarted = false;
        } catch (IOException e) {
            throw new JCssException("Exception while writing property :" + value + " for property " + currentProperty + " in class: " + currentClass, e);
        }

        return this;
    }

    private boolean validatePropertyKey(String key) {
        if(key == null || key.isEmpty()) {
            return false;
        }

        if(!RegexValidationUtil.isValidCssToken(key)) {
            return false;
        }
        return true;
    }

    public String build() throws JCssException {

        try {
            writer.flush();
            return writer.toString();
        } catch (IOException e) {
            throw new JCssException("Exception while flushing written data", e);
        }

    }
}
