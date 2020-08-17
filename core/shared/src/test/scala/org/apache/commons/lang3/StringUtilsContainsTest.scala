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

import java.util.Locale
import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.StringUtils} - Contains methods
  */
object StringUtilsContainsTest {
  /**
    * Supplementary character U+20000
    * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
    */
  private val CharU20000 = "\uD840\uDC00"
  /**
    * Supplementary character U+20001
    * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
    */
  private val CharU20001 = "\uD840\uDC01"
  /**
    * Incomplete supplementary character U+20000, high surrogate only.
    * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
    */
  private val CharUSuppCharHigh = "\uDC00"
  /**
    * Incomplete supplementary character U+20000, low surrogate only.
    * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
    */
  private val CharUSuppCharLow = "\uD840"
}

class StringUtilsContainsTest extends JUnitSuite {
  @Test def testContains_Char(): Unit = {
    assertFalse(StringUtils.contains(null, ' '))
    assertFalse(StringUtils.contains("", ' '))
    assertFalse(StringUtils.contains("", null))
    assertFalse(StringUtils.contains(null, null))
    assertTrue(StringUtils.contains("abc", 'a'))
    assertTrue(StringUtils.contains("abc", 'b'))
    assertTrue(StringUtils.contains("abc", 'c'))
    assertFalse(StringUtils.contains("abc", 'z'))
  }

  @Test def testContains_String(): Unit = {
    assertFalse(StringUtils.contains(null, null))
    assertFalse(StringUtils.contains(null, ""))
    assertFalse(StringUtils.contains(null, "a"))
    assertFalse(StringUtils.contains("", null))
    assertTrue(StringUtils.contains("", ""))
    assertFalse(StringUtils.contains("", "a"))
    assertTrue(StringUtils.contains("abc", "a"))
    assertTrue(StringUtils.contains("abc", "b"))
    assertTrue(StringUtils.contains("abc", "c"))
    assertTrue(StringUtils.contains("abc", "abc"))
    assertFalse(StringUtils.contains("abc", "z"))
  }

  /**
    * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
    */
  @Test def testContains_StringWithBadSupplementaryChars()
    : Unit = { // Test edge case: 1/2 of a (broken) supplementary char
    assertFalse(StringUtils.contains(StringUtilsContainsTest.CharUSuppCharHigh, StringUtilsContainsTest.CharU20001))
    assertFalse(StringUtils.contains(StringUtilsContainsTest.CharUSuppCharLow, StringUtilsContainsTest.CharU20001))
    assertFalse(StringUtils.contains(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharHigh))
    assertEquals(0, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharLow))
    assertTrue(StringUtils.contains(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharLow))
    assertTrue(
      StringUtils.contains(StringUtilsContainsTest.CharU20001 + StringUtilsContainsTest.CharUSuppCharLow + "a", "a"))
    assertTrue(
      StringUtils.contains(StringUtilsContainsTest.CharU20001 + StringUtilsContainsTest.CharUSuppCharHigh + "a", "a"))
  }

  @Test def testContains_StringWithSupplementaryChars(): Unit = {
    assertTrue(
      StringUtils.contains(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20000))
    assertTrue(
      StringUtils.contains(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20001))
    assertTrue(StringUtils.contains(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20000))
    assertFalse(StringUtils.contains(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20001))
  }

  @Test def testContainsAny_StringCharArray(): Unit = {
    assertFalse(StringUtils.containsAny(null, null.asInstanceOf[Array[Char]]))
    assertFalse(StringUtils.containsAny(null, new Array[Char](0)))
    assertFalse(StringUtils.containsAny(null, 'a', 'b'))
    assertFalse(StringUtils.containsAny("", null.asInstanceOf[Array[Char]]))
    assertFalse(StringUtils.containsAny("", new Array[Char](0)))
    assertFalse(StringUtils.containsAny("", 'a', 'b'))
    assertFalse(StringUtils.containsAny("zzabyycdxx", null.asInstanceOf[Array[Char]]))
    assertFalse(StringUtils.containsAny("zzabyycdxx", new Array[Char](0)))
    assertTrue(StringUtils.containsAny("zzabyycdxx", 'z', 'a'))
    assertTrue(StringUtils.containsAny("zzabyycdxx", 'b', 'y'))
    assertTrue(StringUtils.containsAny("zzabyycdxx", 'z', 'y'))
    assertFalse(StringUtils.containsAny("ab", 'z'))
  }

  @Test def testContainsAny_StringCharArrayWithBadSupplementaryChars(): Unit = {
    assertFalse(
      StringUtils
        .containsAny(StringUtilsContainsTest.CharUSuppCharHigh, StringUtilsContainsTest.CharU20001.toCharArray))
    assertFalse(
      StringUtils.containsAny(
        "abc" + StringUtilsContainsTest.CharUSuppCharHigh + "xyz",
        StringUtilsContainsTest.CharU20001.toCharArray))
    assertEquals(-1, StringUtilsContainsTest.CharUSuppCharLow.indexOf(StringUtilsContainsTest.CharU20001))
    assertFalse(
      StringUtils.containsAny(StringUtilsContainsTest.CharUSuppCharLow, StringUtilsContainsTest.CharU20001.toCharArray))
    assertFalse(
      StringUtils
        .containsAny(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharHigh.toCharArray))
    assertEquals(0, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharLow))
    assertTrue(
      StringUtils.containsAny(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharLow.toCharArray))
  }

  @Test def testContainsAny_StringCharArrayWithSupplementaryChars(): Unit = {
    assertTrue(
      StringUtils.containsAny(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20000.toCharArray))
    assertTrue(
      StringUtils
        .containsAny("a" + StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001, "a".toCharArray))
    assertTrue(
      StringUtils
        .containsAny(StringUtilsContainsTest.CharU20000 + "a" + StringUtilsContainsTest.CharU20001, "a".toCharArray))
    assertTrue(
      StringUtils
        .containsAny(StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001 + "a", "a".toCharArray))
    assertTrue(
      StringUtils.containsAny(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20001.toCharArray))
    assertTrue(
      StringUtils.containsAny(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20000.toCharArray))
    // Sanity check:
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001))
    assertEquals(0, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(0)))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(1)))
    // Test:
    assertFalse(
      StringUtils.containsAny(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20001.toCharArray))
    assertFalse(
      StringUtils.containsAny(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharU20000.toCharArray))
  }

  @Test def testContainsAny_StringString(): Unit = {
    assertFalse(StringUtils.containsAny(null, null.asInstanceOf[String]))
    assertFalse(StringUtils.containsAny(null, ""))
    assertFalse(StringUtils.containsAny(null, "ab"))
    assertFalse(StringUtils.containsAny("", null.asInstanceOf[String]))
    assertFalse(StringUtils.containsAny("", ""))
    assertFalse(StringUtils.containsAny("", "ab"))
    assertFalse(StringUtils.containsAny("zzabyycdxx", null.asInstanceOf[String]))
    assertFalse(StringUtils.containsAny("zzabyycdxx", ""))
    assertTrue(StringUtils.containsAny("zzabyycdxx", "za"))
    assertTrue(StringUtils.containsAny("zzabyycdxx", "by"))
    assertTrue(StringUtils.containsAny("zzabyycdxx", "zy"))
    assertFalse(StringUtils.containsAny("ab", "z"))
  }

  @Test def testContainsAny_StringWithBadSupplementaryChars(): Unit = {
    assertFalse(StringUtils.containsAny(StringUtilsContainsTest.CharUSuppCharHigh, StringUtilsContainsTest.CharU20001))
    assertEquals(-1, StringUtilsContainsTest.CharUSuppCharLow.indexOf(StringUtilsContainsTest.CharU20001))
    assertFalse(StringUtils.containsAny(StringUtilsContainsTest.CharUSuppCharLow, StringUtilsContainsTest.CharU20001))
    assertFalse(StringUtils.containsAny(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharHigh))
    assertEquals(0, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharLow))
    assertTrue(StringUtils.containsAny(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharLow))
  }

  @Test def testContainsAny_StringWithSupplementaryChars(): Unit = {
    assertTrue(
      StringUtils.containsAny(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20000))
    assertTrue(
      StringUtils.containsAny(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20001))
    assertTrue(StringUtils.containsAny(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20000))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001))
    assertEquals(0, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(0)))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(1)))
    assertFalse(StringUtils.containsAny(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20001))
    assertFalse(StringUtils.containsAny(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharU20000))
  }

  @Test def testContainsAny_StringStringArray(): Unit = {
    assertFalse(StringUtils.containsAny(null, null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.containsAny(null, new Array[String](0)))
    assertFalse(StringUtils.containsAny(null, Array[String]("hello")))
    assertFalse(StringUtils.containsAny("", null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.containsAny("", new Array[String](0)))
    assertFalse(StringUtils.containsAny("", Array[String]("hello")))
    assertFalse(StringUtils.containsAny("hello, goodbye", null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.containsAny("hello, goodbye", new Array[String](0)))
    assertTrue(StringUtils.containsAny("hello, goodbye", Array[String]("hello", "goodbye")))
    assertTrue(StringUtils.containsAny("hello, goodbye", Array[String]("hello", "Goodbye")))
    assertFalse(StringUtils.containsAny("hello, goodbye", Array[String]("Hello", "Goodbye")))
    assertFalse(StringUtils.containsAny("hello, goodbye", Array[String]("Hello", null)))
    assertFalse(StringUtils.containsAny("hello, null", Array[String]("Hello", null)))
    // Javadoc examples:
    assertTrue(StringUtils.containsAny("abcd", "ab", null))
    assertTrue(StringUtils.containsAny("abcd", "ab", "cd"))
    assertTrue(StringUtils.containsAny("abc", "d", "abc"))
  }

  //@DefaultLocale(language = "de", country = "DE")
  @Test def testContainsIgnoreCase_LocaleIndependence(): Unit = {
    val locales = Array(Locale.ENGLISH, new Locale("tr"), Locale.getDefault)
    val tdata = Array(
      Array("i", "I"),
      Array("I", "i"),
      Array("\u03C2", "\u03C3"),
      Array("\u03A3", "\u03C2"),
      Array("\u03A3", "\u03C3"))
    val fdata = Array(Array("\u00DF", "SS"))
    for (testLocale <- locales) {
      Locale.setDefault(testLocale)
      for (j <- 0 until tdata.length) {
        assertTrue(
          Locale.getDefault + ": " + j + " " + tdata(j)(0) + " " + tdata(j)(1),
          StringUtils.containsIgnoreCase(tdata(j)(0), tdata(j)(1)))
      }
      for (j <- 0 until fdata.length) {
        assertFalse(
          Locale.getDefault + ": " + j + " " + fdata(j)(0) + " " + fdata(j)(1),
          StringUtils.containsIgnoreCase(fdata(j)(0), fdata(j)(1)))
      }
    }
  }

  @Test def testContainsIgnoreCase_StringString(): Unit = {
    assertFalse(StringUtils.containsIgnoreCase(null, null))
    // Null tests
    assertFalse(StringUtils.containsIgnoreCase(null, ""))
    assertFalse(StringUtils.containsIgnoreCase(null, "a"))
    assertFalse(StringUtils.containsIgnoreCase(null, "abc"))
    assertFalse(StringUtils.containsIgnoreCase("", null))
    assertFalse(StringUtils.containsIgnoreCase("a", null))
    assertFalse(StringUtils.containsIgnoreCase("abc", null))
    // Match len = 0
    assertTrue(StringUtils.containsIgnoreCase("", ""))
    assertTrue(StringUtils.containsIgnoreCase("a", ""))
    assertTrue(StringUtils.containsIgnoreCase("abc", ""))
    // Match len = 1
    assertFalse(StringUtils.containsIgnoreCase("", "a"))
    assertTrue(StringUtils.containsIgnoreCase("a", "a"))
    assertTrue(StringUtils.containsIgnoreCase("abc", "a"))
    assertFalse(StringUtils.containsIgnoreCase("", "A"))
    assertTrue(StringUtils.containsIgnoreCase("a", "A"))
    assertTrue(StringUtils.containsIgnoreCase("abc", "A"))
    // Match len > 1
    assertFalse(StringUtils.containsIgnoreCase("", "abc"))
    assertFalse(StringUtils.containsIgnoreCase("a", "abc"))
    assertTrue(StringUtils.containsIgnoreCase("xabcz", "abc"))
    assertFalse(StringUtils.containsIgnoreCase("", "ABC"))
    assertFalse(StringUtils.containsIgnoreCase("a", "ABC"))
    assertTrue(StringUtils.containsIgnoreCase("xabcz", "ABC"))
  }

  @Test def testContainsNone_CharArray(): Unit = {
    val str1 = "a"
    val str2 = "b"
    val str3 = "ab."
    val chars1 = Array('b')
    val chars2 = Array('.')
    val chars3 = Array('c', 'd')
    val emptyChars = new Array[Char](0)
    assertTrue(StringUtils.containsNone(null, null.asInstanceOf[Array[Char]]))
    assertTrue(StringUtils.containsNone("", null.asInstanceOf[Array[Char]]))
    assertTrue(StringUtils.containsNone(null, emptyChars))
    assertTrue(StringUtils.containsNone(str1, emptyChars))
    assertTrue(StringUtils.containsNone("", emptyChars))
    assertTrue(StringUtils.containsNone("", chars1))
    assertTrue(StringUtils.containsNone(str1, chars1))
    assertTrue(StringUtils.containsNone(str1, chars2))
    assertTrue(StringUtils.containsNone(str1, chars3))
    assertFalse(StringUtils.containsNone(str2, chars1))
    assertTrue(StringUtils.containsNone(str2, chars2))
    assertTrue(StringUtils.containsNone(str2, chars3))
    assertFalse(StringUtils.containsNone(str3, chars1))
    assertFalse(StringUtils.containsNone(str3, chars2))
    assertTrue(StringUtils.containsNone(str3, chars3))
  }

  @Test def testContainsNone_CharArrayWithBadSupplementaryChars(): Unit = {
    assertTrue(
      StringUtils
        .containsNone(StringUtilsContainsTest.CharUSuppCharHigh, StringUtilsContainsTest.CharU20001.toCharArray))
    assertEquals(-1, StringUtilsContainsTest.CharUSuppCharLow.indexOf(StringUtilsContainsTest.CharU20001))
    assertTrue(
      StringUtils
        .containsNone(StringUtilsContainsTest.CharUSuppCharLow, StringUtilsContainsTest.CharU20001.toCharArray))
    assertEquals(-1, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharHigh))
    assertTrue(
      StringUtils
        .containsNone(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharHigh.toCharArray))
    assertEquals(0, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharLow))
    assertFalse(
      StringUtils
        .containsNone(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharLow.toCharArray))
  }

  @Test def testContainsNone_CharArrayWithSupplementaryChars(): Unit = {
    assertFalse(
      StringUtils.containsNone(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20000.toCharArray))
    assertFalse(
      StringUtils.containsNone(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20001.toCharArray))
    assertFalse(
      StringUtils.containsNone(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20000.toCharArray))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001))
    assertEquals(0, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(0)))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(1)))
    assertTrue(
      StringUtils.containsNone(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20001.toCharArray))
    assertTrue(
      StringUtils.containsNone(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharU20000.toCharArray))
  }

  @Test def testContainsNone_String(): Unit = {
    val str1 = "a"
    val str2 = "b"
    val str3 = "ab."
    val chars1 = "b"
    val chars2 = "."
    val chars3 = "cd"
    assertTrue(StringUtils.containsNone(null, null.asInstanceOf[String]))
    assertTrue(StringUtils.containsNone("", null.asInstanceOf[String]))
    assertTrue(StringUtils.containsNone(null, ""))
    assertTrue(StringUtils.containsNone(str1, ""))
    assertTrue(StringUtils.containsNone("", ""))
    assertTrue(StringUtils.containsNone("", chars1))
    assertTrue(StringUtils.containsNone(str1, chars1))
    assertTrue(StringUtils.containsNone(str1, chars2))
    assertTrue(StringUtils.containsNone(str1, chars3))
    assertFalse(StringUtils.containsNone(str2, chars1))
    assertTrue(StringUtils.containsNone(str2, chars2))
    assertTrue(StringUtils.containsNone(str2, chars3))
    assertFalse(StringUtils.containsNone(str3, chars1))
    assertFalse(StringUtils.containsNone(str3, chars2))
    assertTrue(StringUtils.containsNone(str3, chars3))
  }

  @Test def testContainsNone_StringWithBadSupplementaryChars(): Unit = {
    assertTrue(StringUtils.containsNone(StringUtilsContainsTest.CharUSuppCharHigh, StringUtilsContainsTest.CharU20001))
    assertEquals(-1, StringUtilsContainsTest.CharUSuppCharLow.indexOf(StringUtilsContainsTest.CharU20001))
    assertTrue(StringUtils.containsNone(StringUtilsContainsTest.CharUSuppCharLow, StringUtilsContainsTest.CharU20001))
    assertEquals(-1, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharHigh))
    assertTrue(StringUtils.containsNone(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharHigh))
    assertEquals(0, StringUtilsContainsTest.CharU20001.indexOf(StringUtilsContainsTest.CharUSuppCharLow))
    assertFalse(StringUtils.containsNone(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharUSuppCharLow))
  }

  @Test def testContainsNone_StringWithSupplementaryChars(): Unit = {
    assertFalse(
      StringUtils.containsNone(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20000))
    assertFalse(
      StringUtils.containsNone(
        StringUtilsContainsTest.CharU20000 + StringUtilsContainsTest.CharU20001,
        StringUtilsContainsTest.CharU20001))
    assertFalse(StringUtils.containsNone(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20000))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001))
    assertEquals(0, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(0)))
    assertEquals(-1, StringUtilsContainsTest.CharU20000.indexOf(StringUtilsContainsTest.CharU20001.charAt(1)))
    assertTrue(StringUtils.containsNone(StringUtilsContainsTest.CharU20000, StringUtilsContainsTest.CharU20001))
    assertTrue(StringUtils.containsNone(StringUtilsContainsTest.CharU20001, StringUtilsContainsTest.CharU20000))
  }

  @Test def testContainsOnly_CharArray(): Unit = {
    val str1 = "a"
    val str2 = "b"
    val str3 = "ab"
    val chars1 = Array('b')
    val chars2 = Array('a')
    val chars3 = Array('a', 'b')
    val emptyChars = new Array[Char](0)
    assertFalse(StringUtils.containsOnly(null, null.asInstanceOf[Array[Char]]))
    assertFalse(StringUtils.containsOnly("", null.asInstanceOf[Array[Char]]))
    assertFalse(StringUtils.containsOnly(null, emptyChars))
    assertFalse(StringUtils.containsOnly(str1, emptyChars))
    assertTrue(StringUtils.containsOnly("", emptyChars))
    assertTrue(StringUtils.containsOnly("", chars1))
    assertFalse(StringUtils.containsOnly(str1, chars1))
    assertTrue(StringUtils.containsOnly(str1, chars2))
    assertTrue(StringUtils.containsOnly(str1, chars3))
    assertTrue(StringUtils.containsOnly(str2, chars1))
    assertFalse(StringUtils.containsOnly(str2, chars2))
    assertTrue(StringUtils.containsOnly(str2, chars3))
    assertFalse(StringUtils.containsOnly(str3, chars1))
    assertFalse(StringUtils.containsOnly(str3, chars2))
    assertTrue(StringUtils.containsOnly(str3, chars3))
  }

  @Test def testContainsOnly_String(): Unit = {
    val str1 = "a"
    val str2 = "b"
    val str3 = "ab"
    val chars1 = "b"
    val chars2 = "a"
    val chars3 = "ab"
    assertFalse(StringUtils.containsOnly(null, null.asInstanceOf[String]))
    assertFalse(StringUtils.containsOnly("", null.asInstanceOf[String]))
    assertFalse(StringUtils.containsOnly(null, ""))
    assertFalse(StringUtils.containsOnly(str1, ""))
    assertTrue(StringUtils.containsOnly("", ""))
    assertTrue(StringUtils.containsOnly("", chars1))
    assertFalse(StringUtils.containsOnly(str1, chars1))
    assertTrue(StringUtils.containsOnly(str1, chars2))
    assertTrue(StringUtils.containsOnly(str1, chars3))
    assertTrue(StringUtils.containsOnly(str2, chars1))
    assertFalse(StringUtils.containsOnly(str2, chars2))
    assertTrue(StringUtils.containsOnly(str2, chars3))
    assertFalse(StringUtils.containsOnly(str3, chars1))
    assertFalse(StringUtils.containsOnly(str3, chars2))
    assertTrue(StringUtils.containsOnly(str3, chars3))
  }

  @Test def testContainsWhitespace(): Unit = {
    assertFalse(StringUtils.containsWhitespace(""))
    assertTrue(StringUtils.containsWhitespace(" "))
    assertFalse(StringUtils.containsWhitespace("a"))
    assertTrue(StringUtils.containsWhitespace("a "))
    assertTrue(StringUtils.containsWhitespace(" a"))
    assertTrue(StringUtils.containsWhitespace("a\t"))
    assertTrue(StringUtils.containsWhitespace("\n"))
  }
}
