package test;

import junit.framework.Assert;
import org.jcss.JCssException;
import org.jcss.writer.CssWriter;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

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

public class CssWriterTest {

    @Test
    public void testCssWriterWithComments() throws JCssException, IOException {
        String result = "/*frict1on's CSS Writer*/\n" +
                ".frict1on h1 {\n" +
                "\twidth:10px;\n" +
                "\tfloat:right;\n" +
                "}\n" +
                ".frict1on h2 {\n" +
                "\tfont-family:Verdana;\n" +
                "\tmargin-left:10px;\n" +
                "}\n";

        Writer internal = new StringWriter();
        CssWriter writer = new CssWriter(internal);
        writer.comment("frict1on's CSS Writer");
        writer.startClass(".frict1on h1");
        writer.property("width");
        writer.value("10px");
        writer.property("float");
        writer.value("right");
        writer.endClass();

        writer.startClass(".frict1on h2");
        writer.property("font-family");
        writer.value("Verdana");
        writer.property("margin-left");
        writer.value("10px");
        writer.endClass();

        Assert.assertEquals(result, writer.build());
    }

}
