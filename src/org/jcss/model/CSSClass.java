package org.jcss.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSSClass {
    public Map<String, String> styles = new HashMap<>();
    public List<String> classNames = new ArrayList<>();

    public boolean hasStyle(String key, String value) {
        if(key == null || value == null) {
            return  false;
        }

        key = key.trim();
        value = value.trim();

        String storedValue = styles.get(key);
        return value.equals(storedValue);

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
