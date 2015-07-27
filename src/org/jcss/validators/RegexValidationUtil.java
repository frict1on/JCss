package org.jcss.validators;

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

import java.util.regex.Pattern;

/**
 * RegexValidationUtil is Utility class which manages the regular expressions for a valid CSS property and value.
 *
 * Regex picked up from: http://stackoverflow.com/questions/2812072/allowed-characters-for-css-identifiers
 * @author frict1on@github
 */
public class RegexValidationUtil {

    private static String h = "[0-9a-f]";
    private static String unicode = "\\\\{h}{1,6}(\\r\\n|[ \\t\\r\\n\\f])?".replace("{h}", h);
    private static String escape = "({unicode}|\\\\[^\\r\\n\\f0-9a-f])".replace("{unicode}", unicode);
    private static String nonascii = "[\\x240-\\x377]";
    private static String nmchar = "([_a-z0-9-]|{nonascii}|{escape})".replace("{nonascii}", nonascii).replace("{escape}", escape);
    private static String nmstart = "([_a-z]|{nonascii}|{escape})".replace("{nonascii}", nonascii).replace("{escape}", escape);
    private static final String ident = "-?{nmstart}{nmchar}*".replace("{nmstart}", nmstart).replace("{nmchar}", nmchar);


//    private static final String  badcomment1 = "\\/\\*[^*]*\\*+([^/*][^*]*\\*+)*";
//    private static final String badcomment2	= "\\/\\*[^*]*(\\*+[^/*][^*]*)*";

//    private static final String  badcomment = "{badcomment1}|{badcomment2}".replace("{badcomment1}",badcomment1).replace("{badcomment2}", badcomment2);

    private static final String badcomment = "\\*/";

    private static Pattern cssPat = Pattern.compile(ident);
    private static Pattern cmmntPat = Pattern.compile(badcomment);

    public static boolean isValidCssToken(String token) {

        if(token == null) {
            return false;
        }
        return cssPat.matcher(token).matches();
    }

    public static boolean isValidComment(String comment) {

        if(comment == null) {
            return false;
        }
        return !cmmntPat.matcher(comment).find();
    }
}
