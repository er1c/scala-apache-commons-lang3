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

import java.io.CharArrayWriter
import java.io.IOException
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests for {@link org.apache.commons.lang3.text.translate.UnicodeUnpairedSurrogateRemover}.
  */
@deprecated
class UnicodeUnpairedSurrogateRemoverTest extends JUnitSuite {
  final private[translate] val subject = new UnicodeUnpairedSurrogateRemover
  final private[translate] val writer = new CharArrayWriter // nothing is ever written to it

  @Test
  @throws[IOException]
  def testValidCharacters(): Unit = {
    assertFalse(subject.translate(0xd7ff, writer))
    assertFalse(subject.translate(0xe000, writer))
    assertEquals(0, writer.size)
  }

  @Test
  @throws[IOException]
  def testInvalidCharacters(): Unit = {
    assertTrue(subject.translate(0xd800, writer))
    assertTrue(subject.translate(0xdfff, writer))
    assertEquals(0, writer.size)
  }
}
