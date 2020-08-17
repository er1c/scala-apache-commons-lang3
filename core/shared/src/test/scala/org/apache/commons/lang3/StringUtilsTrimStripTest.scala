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

import org.junit.Assert.{assertArrayEquals, assertEquals, assertNull}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.StringUtils} - Trim/Strip methods
  */
object StringUtilsTrimStripTest {
  private val FOO = "foo"
}

class StringUtilsTrimStripTest extends JUnitSuite {
  @Test def testTrim(): Unit = {
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trim(StringUtilsTrimStripTest.FOO + "  "))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trim(" " + StringUtilsTrimStripTest.FOO + "  "))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trim(" " + StringUtilsTrimStripTest.FOO))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trim(StringUtilsTrimStripTest.FOO + ""))
    assertEquals("", StringUtils.trim(" \t\r\n\b "))
    assertEquals("", StringUtils.trim(StringUtilsTest.TRIMMABLE))
    assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trim(StringUtilsTest.NON_TRIMMABLE))
    assertEquals("", StringUtils.trim(""))
    assertNull(StringUtils.trim(null))
  }

  @Test def testTrimToNull(): Unit = {
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToNull(StringUtilsTrimStripTest.FOO + "  "))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToNull(" " + StringUtilsTrimStripTest.FOO + "  "))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToNull(" " + StringUtilsTrimStripTest.FOO))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToNull(StringUtilsTrimStripTest.FOO + ""))
    assertNull(StringUtils.trimToNull(" \t\r\n\b "))
    assertNull(StringUtils.trimToNull(StringUtilsTest.TRIMMABLE))
    assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trimToNull(StringUtilsTest.NON_TRIMMABLE))
    assertNull(StringUtils.trimToNull(""))
    assertNull(StringUtils.trimToNull(null))
  }

  @Test def testTrimToEmpty(): Unit = {
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToEmpty(StringUtilsTrimStripTest.FOO + "  "))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToEmpty(" " + StringUtilsTrimStripTest.FOO + "  "))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToEmpty(" " + StringUtilsTrimStripTest.FOO))
    assertEquals(StringUtilsTrimStripTest.FOO, StringUtils.trimToEmpty(StringUtilsTrimStripTest.FOO + ""))
    assertEquals("", StringUtils.trimToEmpty(" \t\r\n\b "))
    assertEquals("", StringUtils.trimToEmpty(StringUtilsTest.TRIMMABLE))
    assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trimToEmpty(StringUtilsTest.NON_TRIMMABLE))
    assertEquals("", StringUtils.trimToEmpty(""))
    assertEquals("", StringUtils.trimToEmpty(null))
  }

  //-----------------------------------------------------------------------
  @Test def testStrip_String(): Unit = {
    assertNull(StringUtils.strip(null))
    assertEquals("", StringUtils.strip(""))
    assertEquals("", StringUtils.strip("        "))
    assertEquals("abc", StringUtils.strip("  abc  "))
    assertEquals(
      StringUtilsTest.NON_WHITESPACE,
      StringUtils.strip(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE))
  }

  @Test def testStripToNull_String(): Unit = {
    assertNull(StringUtils.stripToNull(null))
    assertNull(StringUtils.stripToNull(""))
    assertNull(StringUtils.stripToNull("        "))
    assertNull(StringUtils.stripToNull(StringUtilsTest.WHITESPACE))
    assertEquals("ab c", StringUtils.stripToNull("  ab c  "))
    assertEquals(
      StringUtilsTest.NON_WHITESPACE,
      StringUtils.stripToNull(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE))
  }

  @Test def testStripToEmpty_String(): Unit = {
    assertEquals("", StringUtils.stripToEmpty(null))
    assertEquals("", StringUtils.stripToEmpty(""))
    assertEquals("", StringUtils.stripToEmpty("        "))
    assertEquals("", StringUtils.stripToEmpty(StringUtilsTest.WHITESPACE))
    assertEquals("ab c", StringUtils.stripToEmpty("  ab c  "))
    assertEquals(
      StringUtilsTest.NON_WHITESPACE,
      StringUtils.stripToEmpty(
        StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE))
  }

  @Test def testStrip_StringString(): Unit = { // null strip
    assertNull(StringUtils.strip(null, null))
    assertEquals("", StringUtils.strip("", null))
    assertEquals("", StringUtils.strip("        ", null))
    assertEquals("abc", StringUtils.strip("  abc  ", null))
    assertEquals(
      StringUtilsTest.NON_WHITESPACE,
      StringUtils.strip(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null))
    // "" strip
    assertNull(StringUtils.strip(null, ""))
    assertEquals("", StringUtils.strip("", ""))
    assertEquals("        ", StringUtils.strip("        ", ""))
    assertEquals("  abc  ", StringUtils.strip("  abc  ", ""))
    assertEquals(StringUtilsTest.WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE, ""))
    // " " strip
    assertNull(StringUtils.strip(null, " "))
    assertEquals("", StringUtils.strip("", " "))
    assertEquals("", StringUtils.strip("        ", " "))
    assertEquals("abc", StringUtils.strip("  abc  ", " "))
    // "ab" strip
    assertNull(StringUtils.strip(null, "ab"))
    assertEquals("", StringUtils.strip("", "ab"))
    assertEquals("        ", StringUtils.strip("        ", "ab"))
    assertEquals("  abc  ", StringUtils.strip("  abc  ", "ab"))
    assertEquals("c", StringUtils.strip("abcabab", "ab"))
    assertEquals(StringUtilsTest.WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE, ""))
  }

  @Test def testStripStart_StringString(): Unit = { // null stripStart
    assertNull(StringUtils.stripStart(null, null))
    assertEquals("", StringUtils.stripStart("", null))
    assertEquals("", StringUtils.stripStart("        ", null))
    assertEquals("abc  ", StringUtils.stripStart("  abc  ", null))
    assertEquals(
      StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE,
      StringUtils
        .stripStart(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null)
    )
    // "" stripStart
    assertNull(StringUtils.stripStart(null, ""))
    assertEquals("", StringUtils.stripStart("", ""))
    assertEquals("        ", StringUtils.stripStart("        ", ""))
    assertEquals("  abc  ", StringUtils.stripStart("  abc  ", ""))
    assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE, ""))
    // " " stripStart
    assertNull(StringUtils.stripStart(null, " "))
    assertEquals("", StringUtils.stripStart("", " "))
    assertEquals("", StringUtils.stripStart("        ", " "))
    assertEquals("abc  ", StringUtils.stripStart("  abc  ", " "))
    // "ab" stripStart
    assertNull(StringUtils.stripStart(null, "ab"))
    assertEquals("", StringUtils.stripStart("", "ab"))
    assertEquals("        ", StringUtils.stripStart("        ", "ab"))
    assertEquals("  abc  ", StringUtils.stripStart("  abc  ", "ab"))
    assertEquals("cabab", StringUtils.stripStart("abcabab", "ab"))
    assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE, ""))
  }

  @Test def testStripEnd_StringString(): Unit = { // null stripEnd
    assertNull(StringUtils.stripEnd(null, null))
    assertEquals("", StringUtils.stripEnd("", null))
    assertEquals("", StringUtils.stripEnd("        ", null))
    assertEquals("  abc", StringUtils.stripEnd("  abc  ", null))
    assertEquals(
      StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE,
      StringUtils
        .stripEnd(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null)
    )
    // "" stripEnd
    assertNull(StringUtils.stripEnd(null, ""))
    assertEquals("", StringUtils.stripEnd("", ""))
    assertEquals("        ", StringUtils.stripEnd("        ", ""))
    assertEquals("  abc  ", StringUtils.stripEnd("  abc  ", ""))
    assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE, ""))
    // " " stripEnd
    assertNull(StringUtils.stripEnd(null, " "))
    assertEquals("", StringUtils.stripEnd("", " "))
    assertEquals("", StringUtils.stripEnd("        ", " "))
    assertEquals("  abc", StringUtils.stripEnd("  abc  ", " "))
    // "ab" stripEnd
    assertNull(StringUtils.stripEnd(null, "ab"))
    assertEquals("", StringUtils.stripEnd("", "ab"))
    assertEquals("        ", StringUtils.stripEnd("        ", "ab"))
    assertEquals("  abc  ", StringUtils.stripEnd("  abc  ", "ab"))
    assertEquals("abc", StringUtils.stripEnd("abcabab", "ab"))
    assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE, ""))
  }

  @Test def testStripAll(): Unit = { // test stripAll method, merely an array version of the above strip
    val empty = new Array[String](0)
    val fooSpace = Array[String](
      "  " + StringUtilsTrimStripTest.FOO + "  ",
      "  " + StringUtilsTrimStripTest.FOO,
      StringUtilsTrimStripTest.FOO + "  ")
    val fooDots = Array[String](
      ".." + StringUtilsTrimStripTest.FOO + "..",
      ".." + StringUtilsTrimStripTest.FOO,
      StringUtilsTrimStripTest.FOO + "..")
    val foo = Array[String](StringUtilsTrimStripTest.FOO, StringUtilsTrimStripTest.FOO, StringUtilsTrimStripTest.FOO)
    assertNull(StringUtils.stripAll(null.asInstanceOf[Array[String]]))
    // Additional varargs tests
    //assertArrayEquals(empty, StringUtils.stripAll()) // empty array
    assertArrayEquals(
      Array[String](null).asInstanceOf[Array[Object]],
      StringUtils.stripAll(null.asInstanceOf[String]).asInstanceOf[Array[Object]]
    ) // == new String[]{null}
    assertArrayEquals(empty.asInstanceOf[Array[Object]], StringUtils.stripAll(empty).asInstanceOf[Array[Object]])
    assertArrayEquals(foo.asInstanceOf[Array[Object]], StringUtils.stripAll(fooSpace).asInstanceOf[Array[Object]])
    assertNull(StringUtils.stripAll(null.asInstanceOf[Array[String]], null.asInstanceOf[String]))
    assertArrayEquals(foo.asInstanceOf[Array[Object]], StringUtils.stripAll(fooSpace, null).asInstanceOf[Array[Object]])
    assertArrayEquals(foo.asInstanceOf[Array[Object]], StringUtils.stripAll(fooDots, ".").asInstanceOf[Array[Object]])
  }

  @Test def testStripAccents(): Unit = {
    val cue = "\u00C7\u00FA\u00EA"
    assertEquals("Failed to strip accents from " + cue, "Cue", StringUtils.stripAccents(cue))
    val lots =
      "\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C7\u00C8\u00C9" + "\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D1\u00D2\u00D3" + "\u00D4\u00D5\u00D6\u00D9\u00DA\u00DB\u00DC\u00DD"
    assertEquals("Failed to strip accents from " + lots, "AAAAAACEEEEIIIINOOOOOUUUUY", StringUtils.stripAccents(lots))
    assertNull("Failed null safety", StringUtils.stripAccents(null))
    assertEquals("Failed empty String", "", StringUtils.stripAccents(""))
    assertEquals("Failed to handle non-accented text", "control", StringUtils.stripAccents("control"))
    assertEquals("Failed to handle easy example", "eclair", StringUtils.stripAccents("\u00E9clair"))
    assertEquals(
      "ALOSZZCN aloszzcn",
      StringUtils.stripAccents(
        "\u0104\u0141\u00D3\u015A\u017B\u0179\u0106\u0143 " + "\u0105\u0142\u00F3\u015B\u017C\u017A\u0107\u0144"))
  }
}
