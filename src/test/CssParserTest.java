package test;

import junit.framework.Assert;
import org.jcss.JCssException;
import org.jcss.model.CssClass;
import org.jcss.parser.CssToJava;
import org.jcss.parser.JavaToCss;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
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

public class CssParserTest {


    @Test
    public void testDeserialize() throws JCssException, IOException {
        String input = "/*Anurag's CSS Writer*/\n" +
                ".Anurag h1 {\n" +
                "\twidth:10px;\n" +
                "\tfloat:right;\n" +
                "}\n" +
                ".Anurag1 h2 {\n" +
                "\tfont-family:Verdana;\n" +
                "\tmargin-left:10px;\n" +
                "}\n";

        List<CssClass> classes = new ArrayList<>();

        CssClass classData = new CssClass();
        classData.addSelector(".Anurag h1");
        classData.addPropertyValue("width", "10px");
        classData.addPropertyValue("float", "right");

        classes.add(classData);

        classData = new CssClass();
        classData.addSelector(".Anurag1 h2");
        classData.addPropertyValue("font-family", "Verdana");
        classData.addPropertyValue("margin-left", "10px");

        classes.add(classData);

        Assert.assertEquals(classes, CssToJava.getInstance().deserialize(input));
    }

    @Test
    public void testSerialize()  throws JCssException, IOException {
        String input =
                ".jcss h1 {\n" +
                        "\twidth:10px;\n" +
                        "\tfloat:right;\n" +
                        "}\n" +
                        ".jcss h2 {\n" +
                        "\tfont-family:Verdana;\n" +
                        "\tmargin-left:10px;\n" +
                        "}\n";

        List<CssClass> classes = new ArrayList<>();

        CssClass classData = new CssClass();
        classData.addSelector(".jcss h1");
        classData.addPropertyValue("width", "10px");
        classData.addPropertyValue("float", "right");

        classes.add(classData);

        classData = new CssClass();
        classData.addSelector(".jcss h2");
        classData.addPropertyValue("font-family", "Verdana");
        classData.addPropertyValue("margin-left", "10px");

        classes.add(classData);

        Assert.assertEquals(input, JavaToCss.getInstance().serialize(classes));
    }
}
