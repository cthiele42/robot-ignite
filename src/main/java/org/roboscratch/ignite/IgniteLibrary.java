/*
 * Copyright 2016 Claas Thiele.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.roboscratch.ignite;

import org.apache.ignite.Ignite;
import org.robotframework.javalib.library.AnnotationLibrary;

/**
 * Created by cthiele on 30.05.16.
 */
public class IgniteLibrary extends AnnotationLibrary {

    private static final String KEYWORD_PATTERN = "org/roboscratch/ignite/keywords/**/*.class";

    public static Ignite ignite;

    public IgniteLibrary() {
        addKeywordPattern(KEYWORD_PATTERN);
    }

    @Override
    public String getKeywordDocumentation(String keywordName) {
        if (keywordName.equals("__intro__"))
            return "A robot keyword library for Apache Ignite.";
        return super.getKeywordDocumentation(keywordName);
    }

    public Ignite getIgnite() {
        return ignite;
    }

    public void setIgnite(Ignite ignite) {
        this.ignite = ignite;
    }
}
