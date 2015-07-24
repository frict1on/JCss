package org.jcss.writer;

import org.jcss.JCssException;

import java.io.IOException;
import java.io.Writer;

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

        return true;
    }
 }
