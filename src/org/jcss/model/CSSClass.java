package org.jcss.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CSSClass {
    private Map<String, String> styles = new HashMap<>();
    private Set<String> classNames = new HashSet<>();

    public boolean hasStyle(String key, String value) {
        if(key == null || value == null) {
            return  false;
        }

        key = key.trim();
        value = value.trim();

        String storedValue = styles.get(key);
        return value.equals(storedValue);
    }

    public void addStyle(String property, String value) {
        styles.put(property, value);
    }

    public void addClassName(String className) {
        classNames.add(className);
    }

    public void addClassNames(Collection<String> newClassNames) {
        classNames.addAll(newClassNames);
    }

    public String toString() {
        return classNames.toString();
    }

    public boolean equals (Object o) {
        if(!(o instanceof CSSClass) || o == null) {
            return false;
        }
        CSSClass other = (CSSClass) o;
        if(other.styles.size() != styles.size()) {
            return  false;
        }

        for(Map.Entry styleEnt : styles.entrySet()) {
            if(!other.styles.entrySet().contains(styleEnt)) {
                return false;
            }
        }

        return true;
    }
}
