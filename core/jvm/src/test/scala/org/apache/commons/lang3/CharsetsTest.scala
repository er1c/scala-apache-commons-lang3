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

package org.apache.commons.lang3

import java.nio.charset.{Charset, StandardCharsets}
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Tests {@link Charsets}.
  */
class CharsetsTest extends JUnitSuite {
  @Test def testToCharset_Charset(): Unit = {
    assertEquals(Charset.defaultCharset, Charsets.toCharset(null.asInstanceOf[Charset]))
    assertEquals(Charset.defaultCharset, Charsets.toCharset(Charset.defaultCharset))
    assertEquals(StandardCharsets.UTF_8, Charsets.toCharset(StandardCharsets.UTF_8))
  }

  @Test def testToCharset_String(): Unit = {
    assertEquals(Charset.defaultCharset, Charsets.toCharset(null.asInstanceOf[String]))
    assertEquals(Charset.defaultCharset, Charsets.toCharset(Charset.defaultCharset.name))
    assertEquals(StandardCharsets.UTF_8, Charsets.toCharset("UTF-8"))
  }

  @Test def testToCharsetName(): Unit = {
    assertEquals(Charset.defaultCharset.name, Charsets.toCharsetName(null.asInstanceOf[String]))
    assertEquals("UTF-8", Charsets.toCharsetName("UTF-8"))
  }
}