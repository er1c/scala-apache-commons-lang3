/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.lang3.text.translate

import java.io.IOException
import java.io.StringWriter
import org.junit.Assert._
import org.junit.Test

/**
  * Unit tests for {@link org.apache.commons.lang3.text.translate.LookupTranslator}.
  */
@deprecated
class LookupTranslatorTest {
  @Test
  @throws[IOException]
  def testBasicLookup(): Unit = {
    val lt = new LookupTranslator(Array("one", "two"))
    val out = new StringWriter
    val result = lt.translate("one", 0, out)
    assertEquals("Incorrect codepoint consumption", 3, result)
    assertEquals("Incorrect value", "two", out.toString)
  }

  /**
    * @see <a href="https://issues.apache.org/jira/browse/LANG-882">LANG-882<a>
    */
  @Test
  @throws[IOException]
  def testLang882(): Unit = {
    val lt = new LookupTranslator(Array(new StringBuffer("one"), new StringBuffer("two")))
    val out = new StringWriter
    val result = lt.translate(new StringBuffer("one"), 0, out)
    assertEquals("Incorrect codepoint consumption", 3, result)
    assertEquals("Incorrect value", "two", out.toString)
  }
}
