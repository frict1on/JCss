package org.jcss.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
 * A POJO class used to represent CSS data as Java objects.
 *
 *
 * @author frict1on@github
 */
public class CssClass {
    private Map<String, String> properties = new LinkedHashMap<>();
    private Set<String> selectors = new HashSet<>();

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

    public void addSelector(String className) {
        selectors.add(className);
    }

    public void addSelectors(Collection<String> newClassNames) {
        selectors.addAll(newClassNames);
    }

    public String toString() {
        return selectors.toString();
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

    public Set<String> getSelectors(){
        return Collections.unmodifiableSet(selectors);
    }

    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }
}
