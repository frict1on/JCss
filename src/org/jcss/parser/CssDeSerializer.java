package org.jcss.parser;

import org.jcss.JCssException;
import org.jcss.model.CssClass;

import java.util.List;

/**
 * Created by i307274 on 7/27/15.
 */
public interface CssDeSerializer {
    public List<CssClass> deserialize (String cssData) throws JCssException;
}
