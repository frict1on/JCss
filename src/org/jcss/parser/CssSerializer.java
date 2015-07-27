package org.jcss.parser;

import org.jcss.JCssException;
import org.jcss.model.CssClass;

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
 * CssSerializer is a Single Abstract Method interface providing a method to serialize POJO Css contents to a Css String.
 *
 * @author frict1on@github
 */
public interface CssSerializer {

    public String serialize(List<CssClass> styleClass) throws JCssException;
}
