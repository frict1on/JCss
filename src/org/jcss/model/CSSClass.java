package org.jcss.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CssClass {
    private Map<String, String> properties = new HashMap<>();
    private Set<String> classNames = new HashSet<>();

    public boolean hasPropertyValue(String key, String value) {
        if(key == null || value == null) {
            return  false;
        }

        key = key.trim();
        value = value.trim();

        String storedValue = properties.get(key);
        return value.equals(storedValue);
    }

    public void addPropertyValue(String property, String value) {
        properties.put(property, value);
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
        if(!(o instanceof CssClass) || o == null) {
            return false;
        }
        CssClass other = (CssClass) o;
        if(other.properties.size() != properties.size()) {
            return  false;
        }

        for(Map.Entry styleEnt : properties.entrySet()) {
            if(!other.properties.entrySet().contains(styleEnt)) {
                return false;
            }
        }

        return true;
    }

    public Set<String> getClassNames(){
        return Collections.unmodifiableSet(classNames);
    }

    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }
}
