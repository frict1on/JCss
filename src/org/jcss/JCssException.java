package org.jcss;

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
 * An Exception class which will be used to wrap all the Exceptions encountered during serializing/de-serializing or constructing a CSS class.
 *
 * @author frict1on@github
 */
public class JCssException extends Exception {
    public JCssException() {
        super();
    }

    public JCssException(String message) {
        super(message);
    }

    public JCssException(String message, Throwable cause) {
        super(message, cause);
    }

}
