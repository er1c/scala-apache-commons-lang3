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

import java.io.UnsupportedEncodingException
import java.lang.{Long => JavaLong}
import java.nio.CharBuffer
import java.nio.charset.{Charset, StandardCharsets}
import java.util
import java.util.function.Supplier
import java.util.{Collections, Locale, Objects}
import java.util.regex.PatternSyntaxException
import org.apache.commons.lang3.mutable.MutableInt
import org.apache.commons.lang3.text.WordUtils
import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows
//import org.junit.Disabled

object StringUtilsTest {
  private val ARRAY_LIST: Array[String] = Array("foo", "bar", "baz")
  private val EMPTY_ARRAY_LIST: Array[String] = Array()
  private val NULL_ARRAY_LIST: Array[String] = Array(null)
  private val NULL_TO_STRING_LIST: Array[AnyRef] = Array(new AnyRef {
    override def toString: String = null
  })
  private val MIXED_ARRAY_LIST: Array[String] = Array(null, "", "foo")
  private val MIXED_TYPE_LIST: Array[AnyRef] = Array("foo", JavaLong.valueOf(2L))
  private val LONG_PRIM_LIST: Array[Long] = Array(1, 2)
  private val INT_PRIM_LIST: Array[Int] = Array(1, 2)
  private val BYTE_PRIM_LIST: Array[Byte] = Array(1, 2)
  private val SHORT_PRIM_LIST: Array[Short] = Array(1, 2)
  private val CHAR_PRIM_LIST: Array[Char] = Array('1', '2')
  private val FLOAT_PRIM_LIST: Array[Float] = Array(1, 2)
  private val DOUBLE_PRIM_LIST: Array[Double] = Array(1, 2)
  private val MIXED_STRING_LIST: List[String] = List(null, "", "foo")
  private val MIXED_TYPE_OBJECT_LIST: List[AnyRef] = List[AnyRef]("foo", JavaLong.valueOf(2L))
  private val STRING_LIST: List[String] = List("foo", "bar", "baz")
  private val EMPTY_STRING_LIST: List[String] = List.empty[String]
  private val NULL_STRING_LIST: List[String] = List[String](null)
  private val SEPARATOR: String = ","
  private val SEPARATOR_CHAR: Char = ';'
  private val TEXT_LIST: String = "foo,bar,baz"
  private val TEXT_LIST_CHAR: String = "foo;bar;baz"
  private val TEXT_LIST_NOSEP: String = "foobarbaz"
  private val FOO_UNCAP: String = "foo"
  private val FOO_CAP: String = "Foo"
  private val SENTENCE_UNCAP: String = "foo bar baz"
  private val SENTENCE_CAP: String = "Foo Bar Baz"

  private[lang3] def WHITESPACE: String = whitespaceImpl
  private[lang3] def NON_WHITESPACE: String = nonWhitespaceImpl
  private[lang3] def HARD_SPACE: String = hardspaceImpl
  private[lang3] def TRIMMABLE: String = trimmableImpl
  private[lang3] def NON_TRIMMABLE: String = nonTrimmableImpl

  private[this] val (
    whitespaceImpl: String,
    nonWhitespaceImpl: String,
    hardspaceImpl: String,
    trimmableImpl: String,
    nonTrimmableImpl: String
  ) = {
    val ws: StringBuilder = new StringBuilder
    val nws: StringBuilder = new StringBuilder
    val hs: String = String.valueOf((160.toChar))
    val tr: StringBuilder = new StringBuilder
    val ntr: StringBuilder = new StringBuilder

    for (i <- 0 until Character.MAX_VALUE) {
      if (Character.isWhitespace(i.toChar)) {
        ws.append(String.valueOf(i.toChar))
        if (i > 32) {
          ntr.append(String.valueOf(i.toChar))
        }
      } else {
        if (i < 40) {
          nws.append(String.valueOf(i.toChar))
        }
      }
    }

    for (i <- 0 to 32) {
      tr.append(String.valueOf(i.toChar))
    }

    (
      ws.result,
      nws.result,
      hs,
      tr.result,
      ntr.result
    )
  }

}

/**
  * Unit tests for methods of {@link org.apache.commons.lang3.StringUtils}
  * which been moved to their own test classes.
  */
@SuppressWarnings(Array("deprecation"))
class StringUtilsTest {
  private def assertAbbreviateWithAbbrevMarkerAndOffset(
    expected: String,
    abbrevMarker: String,
    offset: Int,
    maxWidth: Int): Unit = {
    val abcdefghijklmno = "abcdefghijklmno"
    val message = "abbreviate(String,String,int,int) failed"
    val actual = StringUtils.abbreviate(abcdefghijklmno, abbrevMarker, offset, maxWidth)
    if (offset >= 0 && offset < abcdefghijklmno.length)
      assertTrue(message + " -- should contain offset character", actual.indexOf(('a' + offset).toChar) != -1)
    assertTrue(message + " -- should not be greater than maxWidth", actual.length <= maxWidth)
    assertEquals(message, expected, actual)
  }

  private def assertAbbreviateWithOffset(expected: String, offset: Int, maxWidth: Int): Unit = {
    val abcdefghijklmno = "abcdefghijklmno"
    val message = "abbreviate(String,int,int) failed"
    val actual = StringUtils.abbreviate(abcdefghijklmno, offset, maxWidth)
    if (offset >= 0 && offset < abcdefghijklmno.length)
      assertTrue(message + " -- should contain offset character", actual.indexOf(('a' + offset).toChar) != -1)
    assertTrue(message + " -- should not be greater than maxWidth", actual.length <= maxWidth)
    assertEquals(message, expected, actual)
  }

  private def innerTestSplit(separator: Char, sepStr: String, noMatch: Char): Unit = {
    val msg = "Failed on separator hex(" + Integer.toHexString(separator) + "), noMatch hex(" + Integer.toHexString(
      noMatch) + "), sepStr(" + sepStr + ")"
    val str = "a" + separator + "b" + separator + separator + noMatch + "c"
    var res: Array[String] = null

    // (str, sepStr)
    res = StringUtils.split(str, sepStr)
    assertEquals(msg, 3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals(noMatch + "c", res(2))
    val str2 = separator + "a" + separator
    res = StringUtils.split(str2, sepStr)
    assertEquals(msg, 1, res.length)
    assertEquals(msg, "a", res(0))
    res = StringUtils.split(str, sepStr, -1)
    assertEquals(msg, 3, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, "b", res(1))
    assertEquals(msg, noMatch + "c", res(2))
    res = StringUtils.split(str, sepStr, 0)
    assertEquals(msg, 3, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, "b", res(1))
    assertEquals(msg, noMatch + "c", res(2))
    res = StringUtils.split(str, sepStr, 1)
    assertEquals(msg, 1, res.length)
    assertEquals(msg, str, res(0))
    res = StringUtils.split(str, sepStr, 2)
    assertEquals(msg, 2, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, str.substring(2), res(1))
  }

  private def innerTestSplitPreserveAllTokens(separator: Char, sepStr: String, noMatch: Char): Unit = {
    val msg = "Failed on separator hex(" + Integer.toHexString(separator) + "), noMatch hex(" + Integer.toHexString(
      noMatch) + "), sepStr(" + sepStr + ")"
    val str = "a" + separator + "b" + separator + separator + noMatch + "c"
    var res: Array[String] = null

    res = StringUtils.splitPreserveAllTokens(str, sepStr)
    assertEquals(msg, 4, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, "b", res(1))
    assertEquals(msg, "", res(2))
    assertEquals(msg, noMatch + "c", res(3))
    val str2 = separator + "a" + separator
    res = StringUtils.splitPreserveAllTokens(str2, sepStr)
    assertEquals(msg, 3, res.length)
    assertEquals(msg, "", res(0))
    assertEquals(msg, "a", res(1))
    assertEquals(msg, "", res(2))
    res = StringUtils.splitPreserveAllTokens(str, sepStr, -1)
    assertEquals(4, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, "b", res(1))
    assertEquals(msg, "", res(2))
    assertEquals(msg, noMatch + "c", res(3))
    res = StringUtils.splitPreserveAllTokens(str, sepStr, 0)
    assertEquals(msg, 4, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, "b", res(1))
    assertEquals(msg, "", res(2))
    assertEquals(msg, noMatch + "c", res(3))
    res = StringUtils.splitPreserveAllTokens(str, sepStr, 1)
    assertEquals(msg, 1, res.length)
    assertEquals(msg, str, res(0))
    res = StringUtils.splitPreserveAllTokens(str, sepStr, 2)
    assertEquals(msg, 2, res.length)
    assertEquals(msg, "a", res(0))
    assertEquals(msg, str.substring(2), res(1))
  }

  //Fixed LANG-1463
  @Test def testAbbreviateMarkerWithEmptyString(): Unit = {
    val greaterThanMaxTest = "much too long text"
    assertEquals("much too long", StringUtils.abbreviate(greaterThanMaxTest, "", 13))
  }

  @Test def testAbbreviate_StringInt(): Unit = {
    assertNull(StringUtils.abbreviate(null, 10))
    assertEquals("", StringUtils.abbreviate("", 10))
    assertEquals("short", StringUtils.abbreviate("short", 10))
    assertEquals(
      "Now is ...",
      StringUtils.abbreviate("Now is the time for all good men to come to the aid of their party.", 10))
    val raspberry = "raspberry peach"
    assertEquals("raspberry p...", StringUtils.abbreviate(raspberry, 14))
    assertEquals("raspberry peach", StringUtils.abbreviate("raspberry peach", 15))
    assertEquals("raspberry peach", StringUtils.abbreviate("raspberry peach", 16))
    assertEquals("abc...", StringUtils.abbreviate("abcdefg", 6))
    assertEquals("abcdefg", StringUtils.abbreviate("abcdefg", 7))
    assertEquals("abcdefg", StringUtils.abbreviate("abcdefg", 8))
    assertEquals("a...", StringUtils.abbreviate("abcdefg", 4))
    assertEquals("", StringUtils.abbreviate("", 4))
    assertThrows[IllegalArgumentException](
      StringUtils.abbreviate("abc", 3)
    ) //, "StringUtils.abbreviate expecting IllegalArgumentException")
    ()
  }

  @Test def testAbbreviate_StringIntInt(): Unit = {
    assertNull(StringUtils.abbreviate(null, 10, 12))
    assertEquals("", StringUtils.abbreviate("", 0, 10))
    assertEquals("", StringUtils.abbreviate("", 2, 10))
    assertThrows[IllegalArgumentException](
      StringUtils.abbreviate("abcdefghij", 0, 3)
    ) //, "StringUtils.abbreviate expecting IllegalArgumentException")
    assertThrows[IllegalArgumentException](
      StringUtils.abbreviate("abcdefghij", 5, 6)
    ) //, "StringUtils.abbreviate expecting IllegalArgumentException")
    val raspberry = "raspberry peach"
    assertEquals("raspberry peach", StringUtils.abbreviate(raspberry, 11, 15))
    assertNull(StringUtils.abbreviate(null, 7, 14))
    assertAbbreviateWithOffset("abcdefg...", -1, 10)
    assertAbbreviateWithOffset("abcdefg...", 0, 10)
    assertAbbreviateWithOffset("abcdefg...", 1, 10)
    assertAbbreviateWithOffset("abcdefg...", 2, 10)
    assertAbbreviateWithOffset("abcdefg...", 3, 10)
    assertAbbreviateWithOffset("abcdefg...", 4, 10)
    assertAbbreviateWithOffset("...fghi...", 5, 10)
    assertAbbreviateWithOffset("...ghij...", 6, 10)
    assertAbbreviateWithOffset("...hijk...", 7, 10)
    assertAbbreviateWithOffset("...ijklmno", 8, 10)
    assertAbbreviateWithOffset("...ijklmno", 9, 10)
    assertAbbreviateWithOffset("...ijklmno", 10, 10)
    assertAbbreviateWithOffset("...ijklmno", 10, 10)
    assertAbbreviateWithOffset("...ijklmno", 11, 10)
    assertAbbreviateWithOffset("...ijklmno", 12, 10)
    assertAbbreviateWithOffset("...ijklmno", 13, 10)
    assertAbbreviateWithOffset("...ijklmno", 14, 10)
    assertAbbreviateWithOffset("...ijklmno", 15, 10)
    assertAbbreviateWithOffset("...ijklmno", 16, 10)
    assertAbbreviateWithOffset("...ijklmno", Integer.MAX_VALUE, 10)
  }

  @Test def testAbbreviate_StringStringInt(): Unit = {
    assertNull(StringUtils.abbreviate(null, null, 10))
    assertNull(StringUtils.abbreviate(null, "...", 10))
    assertEquals("paranaguacu", StringUtils.abbreviate("paranaguacu", null, 10))
    assertEquals("", StringUtils.abbreviate("", "...", 2))
    assertEquals("wai**", StringUtils.abbreviate("waiheke", "**", 5))
    assertEquals("And af,,,,", StringUtils.abbreviate("And after a long time, he finally met his son.", ",,,,", 10))
    val raspberry = "raspberry peach"
    assertEquals("raspberry pe..", StringUtils.abbreviate(raspberry, "..", 14))
    assertEquals("raspberry peach", StringUtils.abbreviate("raspberry peach", "---*---", 15))
    assertEquals("raspberry peach", StringUtils.abbreviate("raspberry peach", ".", 16))
    assertEquals("abc()(", StringUtils.abbreviate("abcdefg", "()(", 6))
    assertEquals("abcdefg", StringUtils.abbreviate("abcdefg", ";", 7))
    assertEquals("abcdefg", StringUtils.abbreviate("abcdefg", "_-", 8))
    assertEquals("abc.", StringUtils.abbreviate("abcdefg", ".", 4))
    assertEquals("", StringUtils.abbreviate("", 4))
    assertThrows[IllegalArgumentException](
      StringUtils.abbreviate("abcdefghij", "...", 3)
    ) // , "StringUtils.abbreviate expecting IllegalArgumentException")
    ()
  }

  @Test def testAbbreviate_StringStringIntInt(): Unit = {
    assertNull(StringUtils.abbreviate(null, null, 10, 12))
    assertNull(StringUtils.abbreviate(null, "...", 10, 12))
    assertEquals("", StringUtils.abbreviate("", null, 0, 10))
    assertEquals("", StringUtils.abbreviate("", "...", 2, 10))
    assertThrows[IllegalArgumentException](
      StringUtils.abbreviate("abcdefghij", "::", 0, 2)
    ) //, "StringUtils.abbreviate expecting IllegalArgumentException")
    assertThrows[IllegalArgumentException](
      StringUtils.abbreviate("abcdefghij", "!!!", 5, 6)
    ) // , "StringUtils.abbreviate expecting IllegalArgumentException")

    val raspberry = "raspberry peach"
    assertEquals("raspberry peach", StringUtils.abbreviate(raspberry, "--", 12, 15))
    assertNull(StringUtils.abbreviate(null, ";", 7, 14))
    assertAbbreviateWithAbbrevMarkerAndOffset("abcdefgh;;", ";;", -1, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("abcdefghi.", ".", 0, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("abcdefgh++", "++", 1, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("abcdefghi*", "*", 2, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("abcdef{{{{", "{{{{", 4, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("abcdef____", "____", 5, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("==fghijk==", "==", 5, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("___ghij___", "___", 6, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("/ghijklmno", "/", 7, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("/ghijklmno", "/", 8, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("/ghijklmno", "/", 9, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("///ijklmno", "///", 10, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("//hijklmno", "//", 10, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("//hijklmno", "//", 11, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("...ijklmno", "...", 12, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("/ghijklmno", "/", 13, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("/ghijklmno", "/", 14, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("999ijklmno", "999", 15, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("_ghijklmno", "_", 16, 10)
    assertAbbreviateWithAbbrevMarkerAndOffset("+ghijklmno", "+", Integer.MAX_VALUE, 10)
  }

  @Test def testAbbreviateMiddle(): Unit = { // javadoc examples
    assertNull(StringUtils.abbreviateMiddle(null, null, 0))
    assertEquals("abc", StringUtils.abbreviateMiddle("abc", null, 0))
    assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 0))
    assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 3))
    assertEquals("ab.f", StringUtils.abbreviateMiddle("abcdef", ".", 4))
    // JIRA issue (LANG-405) example (slightly different than actual expected result)
    assertEquals(
      "A very long text with un...f the text is complete.",
      StringUtils.abbreviateMiddle(
        "A very long text with unimportant stuff in the middle but interesting start and " + "end to see if the text is complete.",
        "...",
        50)
    )
    // Test a much longer text :)
    val longText = "Start text" + StringUtils.repeat("x", 10000) + "Close text"
    assertEquals("Start text->Close text", StringUtils.abbreviateMiddle(longText, "->", 22))
    // Test negative length
    assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", -1))
    // Test boundaries
    // Fails to change anything as method ensures first and last char are kept
    assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 1))
    assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 2))
    // Test length of n=1
    assertEquals("a", StringUtils.abbreviateMiddle("a", ".", 1))
    // Test smallest length that can lead to success
    assertEquals("a.d", StringUtils.abbreviateMiddle("abcd", ".", 3))
    // More from LANG-405
    assertEquals("a..f", StringUtils.abbreviateMiddle("abcdef", "..", 4))
    assertEquals("ab.ef", StringUtils.abbreviateMiddle("abcdef", ".", 5))
  }

  /**
    * Tests {@code appendIfMissing}.
    */
  @Test def testAppendIfMissing(): Unit = {
    assertNull("appendIfMissing(null,null)", StringUtils.appendIfMissing(null, null))
    assertEquals("appendIfMissing(abc,null)", "abc", StringUtils.appendIfMissing("abc", null))
    assertEquals("appendIfMissing(\"\",xyz)", "xyz", StringUtils.appendIfMissing("", "xyz"))
    assertEquals("appendIfMissing(abc,xyz)", "abcxyz", StringUtils.appendIfMissing("abc", "xyz"))
    assertEquals("appendIfMissing(abcxyz,xyz)", "abcxyz", StringUtils.appendIfMissing("abcxyz", "xyz"))
    assertEquals("appendIfMissing(aXYZ,xyz)", "aXYZxyz", StringUtils.appendIfMissing("aXYZ", "xyz"))
    assertNull(
      "appendIfMissing(null,null,null)",
      StringUtils.appendIfMissing(null, null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissing(abc,null,null)",
      "abc",
      StringUtils.appendIfMissing("abc", null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissing(\"\",xyz,null))",
      "xyz",
      StringUtils.appendIfMissing("", "xyz", null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissing(abc,xyz,{null})",
      "abcxyz",
      StringUtils.appendIfMissing("abc", "xyz", null.asInstanceOf[String]))
    assertEquals("appendIfMissing(abc,xyz,\"\")", "abc", StringUtils.appendIfMissing("abc", "xyz", ""))
    assertEquals("appendIfMissing(abc,xyz,mno)", "abcxyz", StringUtils.appendIfMissing("abc", "xyz", "mno"))
    assertEquals("appendIfMissing(abcxyz,xyz,mno)", "abcxyz", StringUtils.appendIfMissing("abcxyz", "xyz", "mno"))
    assertEquals("appendIfMissing(abcmno,xyz,mno)", "abcmno", StringUtils.appendIfMissing("abcmno", "xyz", "mno"))
    assertEquals("appendIfMissing(abcXYZ,xyz,mno)", "abcXYZxyz", StringUtils.appendIfMissing("abcXYZ", "xyz", "mno"))
    assertEquals("appendIfMissing(abcMNO,xyz,mno)", "abcMNOxyz", StringUtils.appendIfMissing("abcMNO", "xyz", "mno"))
  }

  /**
    * Tests {@code appendIfMissingIgnoreCase}.
    */
  @Test def testAppendIfMissingIgnoreCase(): Unit = {
    assertNull("appendIfMissingIgnoreCase(null,null)", StringUtils.appendIfMissingIgnoreCase(null, null))
    assertEquals("appendIfMissingIgnoreCase(abc,null)", "abc", StringUtils.appendIfMissingIgnoreCase("abc", null))
    assertEquals("appendIfMissingIgnoreCase(\"\",xyz)", "xyz", StringUtils.appendIfMissingIgnoreCase("", "xyz"))
    assertEquals("appendIfMissingIgnoreCase(abc,xyz)", "abcxyz", StringUtils.appendIfMissingIgnoreCase("abc", "xyz"))
    assertEquals(
      "appendIfMissingIgnoreCase(abcxyz,xyz)",
      "abcxyz",
      StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz"))
    assertEquals(
      "appendIfMissingIgnoreCase(abcXYZ,xyz)",
      "abcXYZ",
      StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz"))
    assertNull(
      "appendIfMissingIgnoreCase(null,null,null)",
      StringUtils.appendIfMissingIgnoreCase(null, null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissingIgnoreCase(abc,null,null)",
      "abc",
      StringUtils.appendIfMissingIgnoreCase("abc", null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissingIgnoreCase(\"\",xyz,null)",
      "xyz",
      StringUtils.appendIfMissingIgnoreCase("", "xyz", null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissingIgnoreCase(abc,xyz,{null})",
      "abcxyz",
      StringUtils.appendIfMissingIgnoreCase("abc", "xyz", null.asInstanceOf[CharSequence]))
    assertEquals(
      "appendIfMissingIgnoreCase(abc,xyz,\"\")",
      "abc",
      StringUtils.appendIfMissingIgnoreCase("abc", "xyz", ""))
    assertEquals(
      "appendIfMissingIgnoreCase(abc,xyz,mno)",
      "abcxyz",
      StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "mno"))
    assertEquals(
      "appendIfMissingIgnoreCase(abcxyz,xyz,mno)",
      "abcxyz",
      StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz", "mno"))
    assertEquals(
      "appendIfMissingIgnoreCase(abcmno,xyz,mno)",
      "abcmno",
      StringUtils.appendIfMissingIgnoreCase("abcmno", "xyz", "mno"))
    assertEquals(
      "appendIfMissingIgnoreCase(abcXYZ,xyz,mno)",
      "abcXYZ",
      StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz", "mno"))
    assertEquals(
      "appendIfMissingIgnoreCase(abcMNO,xyz,mno)",
      "abcMNO",
      StringUtils.appendIfMissingIgnoreCase("abcMNO", "xyz", "mno"))
  }

  @Test def testCapitalize(): Unit = {
    assertNull(StringUtils.capitalize(null))
    assertEquals("capitalize(empty-string) failed", "", StringUtils.capitalize(""))
    assertEquals("capitalize(single-char-string) failed", "X", StringUtils.capitalize("x"))
    assertEquals("capitalize(String) failed", StringUtilsTest.FOO_CAP, StringUtils.capitalize(StringUtilsTest.FOO_CAP))
    assertEquals(
      "capitalize(string) failed",
      StringUtilsTest.FOO_CAP,
      StringUtils.capitalize(StringUtilsTest.FOO_UNCAP))
    assertEquals("capitalize(String) is not using TitleCase", "\u01C8", StringUtils.capitalize("\u01C9"))
    // Javadoc examples
    assertNull(StringUtils.capitalize(null))
    assertEquals("", StringUtils.capitalize(""))
    assertEquals("Cat", StringUtils.capitalize("cat"))
    assertEquals("CAt", StringUtils.capitalize("cAt"))
    assertEquals("'cat'", StringUtils.capitalize("'cat'"))
  }

  @Test def testCenter_StringInt(): Unit = {
    assertNull(StringUtils.center(null, -1))
    assertNull(StringUtils.center(null, 4))
    assertEquals("    ", StringUtils.center("", 4))
    assertEquals("ab", StringUtils.center("ab", 0))
    assertEquals("ab", StringUtils.center("ab", -1))
    assertEquals("ab", StringUtils.center("ab", 1))
    assertEquals("    ", StringUtils.center("", 4))
    assertEquals(" ab ", StringUtils.center("ab", 4))
    assertEquals("abcd", StringUtils.center("abcd", 2))
    assertEquals(" a  ", StringUtils.center("a", 4))
    assertEquals("  a  ", StringUtils.center("a", 5))
  }

  @Test def testCenter_StringIntChar(): Unit = {
    assertNull(StringUtils.center(null, -1, ' '))
    assertNull(StringUtils.center(null, 4, ' '))
    assertEquals("    ", StringUtils.center("", 4, ' '))
    assertEquals("ab", StringUtils.center("ab", 0, ' '))
    assertEquals("ab", StringUtils.center("ab", -1, ' '))
    assertEquals("ab", StringUtils.center("ab", 1, ' '))
    assertEquals("    ", StringUtils.center("", 4, ' '))
    assertEquals(" ab ", StringUtils.center("ab", 4, ' '))
    assertEquals("abcd", StringUtils.center("abcd", 2, ' '))
    assertEquals(" a  ", StringUtils.center("a", 4, ' '))
    assertEquals("  a  ", StringUtils.center("a", 5, ' '))
    assertEquals("xxaxx", StringUtils.center("a", 5, 'x'))
  }

  @Test def testCenter_StringIntString(): Unit = {
    assertNull(StringUtils.center(null, 4, null))
    assertNull(StringUtils.center(null, -1, " "))
    assertNull(StringUtils.center(null, 4, " "))
    assertEquals("    ", StringUtils.center("", 4, " "))
    assertEquals("ab", StringUtils.center("ab", 0, " "))
    assertEquals("ab", StringUtils.center("ab", -1, " "))
    assertEquals("ab", StringUtils.center("ab", 1, " "))
    assertEquals("    ", StringUtils.center("", 4, " "))
    assertEquals(" ab ", StringUtils.center("ab", 4, " "))
    assertEquals("abcd", StringUtils.center("abcd", 2, " "))
    assertEquals(" a  ", StringUtils.center("a", 4, " "))
    assertEquals("yayz", StringUtils.center("a", 4, "yz"))
    assertEquals("yzyayzy", StringUtils.center("a", 7, "yz"))
    assertEquals("  abc  ", StringUtils.center("abc", 7, null))
    assertEquals("  abc  ", StringUtils.center("abc", 7, ""))
  }

  @Test def testChomp(): Unit = {
    val chompCases: Array[Array[String]] = Array(
      Array(StringUtilsTest.FOO_UNCAP + "\r\n", StringUtilsTest.FOO_UNCAP),
      Array(StringUtilsTest.FOO_UNCAP + "\n", StringUtilsTest.FOO_UNCAP),
      Array(StringUtilsTest.FOO_UNCAP + "\r", StringUtilsTest.FOO_UNCAP),
      Array(StringUtilsTest.FOO_UNCAP + " \r", StringUtilsTest.FOO_UNCAP + " "),
      Array(StringUtilsTest.FOO_UNCAP, StringUtilsTest.FOO_UNCAP),
      Array(StringUtilsTest.FOO_UNCAP + "\n\n", StringUtilsTest.FOO_UNCAP + "\n"),
      Array(StringUtilsTest.FOO_UNCAP + "\r\n\r\n", StringUtilsTest.FOO_UNCAP + "\r\n"),
      Array("foo\nfoo", "foo\nfoo"),
      Array("foo\n\rfoo", "foo\n\rfoo"),
      Array("\n", ""),
      Array("\r", ""),
      Array("a", "a"),
      Array("\r\n", ""),
      Array("", ""),
      Array(null, null),
      Array(StringUtilsTest.FOO_UNCAP + "\n\r", StringUtilsTest.FOO_UNCAP + "\n")
    )

    for (chompCase <- chompCases) {
      val original = chompCase(0)
      val expectedResult = chompCase(1)
      val actualResult = StringUtils.chomp(original)
      assertEquals(
        s"chomp(String) failed: '$expectedResult', original: '$original', res: '$actualResult'",
        expectedResult,
        actualResult)
    }

    assertEquals("chomp(String, String) failed", "foo", StringUtils.chomp("foobar", "bar"))
    assertEquals("chomp(String, String) failed", "foobar", StringUtils.chomp("foobar", "baz"))
    assertEquals("chomp(String, String) failed", "foo", StringUtils.chomp("foo", "foooo"))
    assertEquals("chomp(String, String) failed", "foobar", StringUtils.chomp("foobar", ""))
    assertEquals("chomp(String, String) failed", "foobar", StringUtils.chomp("foobar", null))
    assertEquals("chomp(String, String) failed", "", StringUtils.chomp("", "foo"))
    assertEquals("chomp(String, String) failed", "", StringUtils.chomp("", null))
    assertEquals("chomp(String, String) failed", "", StringUtils.chomp("", ""))
    assertNull("chomp(String, String) failed", StringUtils.chomp(null, "foo"))
    assertNull("chomp(String, String) failed", StringUtils.chomp(null, null))
    assertNull("chomp(String, String) failed", StringUtils.chomp(null, ""))
    assertEquals("chomp(String, String) failed", "", StringUtils.chomp("foo", "foo"))
    assertEquals("chomp(String, String) failed", " ", StringUtils.chomp(" foo", "foo"))
    assertEquals("chomp(String, String) failed", "foo ", StringUtils.chomp("foo ", "foo"))
  }

  @Test def testChop(): Unit = {
    val chopCases: Array[Array[String]] =
      Array(
        Array(StringUtilsTest.FOO_UNCAP + "\r\n", StringUtilsTest.FOO_UNCAP),
        Array(StringUtilsTest.FOO_UNCAP + "\n", StringUtilsTest.FOO_UNCAP),
        Array(StringUtilsTest.FOO_UNCAP + "\r", StringUtilsTest.FOO_UNCAP),
        Array(StringUtilsTest.FOO_UNCAP + " \r", StringUtilsTest.FOO_UNCAP + " "),
        Array("foo", "fo"),
        Array("foo\nfoo", "foo\nfo"),
        Array("\n", ""),
        Array("\r", ""),
        Array("\r\n", ""),
        Array(null, null),
        Array("", ""),
        Array("a", "")
      )

    for (chopCase <- chopCases) {
      val original = chopCase(0)
      val expectedResult = chopCase(1)
      assertEquals("chop(String) failed", expectedResult, StringUtils.chop(original))
    }
  }

//  @Test def testConstructor(): Unit = {
//    assertNotNull(new StringUtils.type)
//    val cons = classOf[StringUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[StringUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[StringUtils.type].getModifiers))
//  }

  @Test def testDefault_String(): Unit = {
    assertEquals("", StringUtils.defaultString(null))
    assertEquals("", StringUtils.defaultString(""))
    assertEquals("abc", StringUtils.defaultString("abc"))
  }

  @Test def testDefault_StringString(): Unit = {
    assertEquals("NULL", StringUtils.defaultString(null, "NULL"))
    assertEquals("", StringUtils.defaultString("", "NULL"))
    assertEquals("abc", StringUtils.defaultString("abc", "NULL"))
  }

  @Test def testDefaultIfBlank_CharBuffers(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfBlank(CharBuffer.wrap(""), CharBuffer.wrap("NULL")).toString)
    assertEquals("NULL", StringUtils.defaultIfBlank(CharBuffer.wrap(" "), CharBuffer.wrap("NULL")).toString)
    assertEquals("abc", StringUtils.defaultIfBlank(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL")).toString)
    assertNull(StringUtils.defaultIfBlank(CharBuffer.wrap(""), null.asInstanceOf[CharBuffer]))
    // Tests compatibility for the API return type
    val s = StringUtils.defaultIfBlank(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL"))
    assertEquals("abc", s.toString)
  }

  @Test def testDefaultIfBlank_StringBuffers(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuffer(""), new StringBuffer("NULL")).toString)
    assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuffer(" "), new StringBuffer("NULL")).toString)
    assertEquals("abc", StringUtils.defaultIfBlank(new StringBuffer("abc"), new StringBuffer("NULL")).toString)
    assertNull(StringUtils.defaultIfBlank(new StringBuffer(""), null.asInstanceOf[StringBuffer]))
    val s = StringUtils.defaultIfBlank(new StringBuffer("abc"), new StringBuffer("NULL"))
    assertEquals("abc", s.toString)
  }

  @Test def testDefaultIfBlank_StringBuilders(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuilder(""), new StringBuilder("NULL")).toString)
    assertEquals("NULL", StringUtils.defaultIfBlank(new StringBuilder(" "), new StringBuilder("NULL")).toString)
    assertEquals("abc", StringUtils.defaultIfBlank(new StringBuilder("abc"), new StringBuilder("NULL")).toString)
    assertNull(StringUtils.defaultIfBlank(new StringBuilder(""), null.asInstanceOf[StringBuilder]))
    val s = StringUtils.defaultIfBlank(new StringBuilder("abc"), new StringBuilder("NULL"))
    assertEquals("abc", s.toString)
  }

  @Test def testDefaultIfBlank_StringString(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfBlank(null, "NULL"))
    assertEquals("NULL", StringUtils.defaultIfBlank("", "NULL"))
    assertEquals("NULL", StringUtils.defaultIfBlank(" ", "NULL"))
    assertEquals("abc", StringUtils.defaultIfBlank("abc", "NULL"))
    assertNull(StringUtils.defaultIfBlank("", null.asInstanceOf[String]))
    val s = StringUtils.defaultIfBlank("abc", "NULL")
    assertEquals("abc", s)
  }

  private def nullSupplier: Supplier[String] =
    new Supplier[String] {
      override def get(): String = null.asInstanceOf[String]
    }

  private def nullStringSupplier: Supplier[String] =
    new Supplier[String] {
      override def get(): String = "NULL"
    }

  @Test def testGetIfBlank_StringStringSupplier(): Unit = {
    assertEquals("NULL", StringUtils.getIfBlank(null, nullStringSupplier))
    assertEquals("NULL", StringUtils.getIfBlank("", nullStringSupplier))
    assertEquals("NULL", StringUtils.getIfBlank(" ", nullStringSupplier))
    assertEquals("abc", StringUtils.getIfBlank("abc", nullStringSupplier))
    assertNull(StringUtils.getIfBlank("", nullSupplier))
    assertNull(StringUtils.defaultIfBlank("", null.asInstanceOf[String]))
    val s = StringUtils.getIfBlank("abc", nullStringSupplier)
    assertEquals("abc", s)

    //Checking that default value supplied only on demand
    val numberOfCalls = new MutableInt(0)
    val countingDefaultSupplier: Supplier[String] = new Supplier[String] {
      override def get(): String = {
        numberOfCalls.increment()
        "NULL"
      }
    }

    StringUtils.getIfBlank("abc", countingDefaultSupplier)
    assertEquals(0, numberOfCalls.getValue)
    StringUtils.getIfBlank("", countingDefaultSupplier)
    assertEquals(1, numberOfCalls.getValue)
    StringUtils.getIfBlank(" ", countingDefaultSupplier)
    assertEquals(2, numberOfCalls.getValue)
    StringUtils.getIfBlank(null, countingDefaultSupplier)
    assertEquals(3, numberOfCalls.getValue)
  }

  @Test def testDefaultIfEmpty_CharBuffers(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfEmpty(CharBuffer.wrap(""), CharBuffer.wrap("NULL")).toString)
    assertEquals("abc", StringUtils.defaultIfEmpty(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL")).toString)
    assertNull(StringUtils.defaultIfEmpty(CharBuffer.wrap(""), null.asInstanceOf[CharBuffer]))
    val s = StringUtils.defaultIfEmpty(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL"))
    assertEquals("abc", s.toString)
  }

  @Test def testDefaultIfEmpty_StringBuffers(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfEmpty(new StringBuffer(""), new StringBuffer("NULL")).toString)
    assertEquals("abc", StringUtils.defaultIfEmpty(new StringBuffer("abc"), new StringBuffer("NULL")).toString)
    assertNull(StringUtils.defaultIfEmpty(new StringBuffer(""), null.asInstanceOf[StringBuffer]))
    val s = StringUtils.defaultIfEmpty(new StringBuffer("abc"), new StringBuffer("NULL"))
    assertEquals("abc", s.toString)
  }

  @Test def testDefaultIfEmpty_StringBuilders(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfEmpty(new StringBuilder(""), new StringBuilder("NULL")).toString)
    assertEquals("abc", StringUtils.defaultIfEmpty(new StringBuilder("abc"), new StringBuilder("NULL")).toString)
    assertNull(StringUtils.defaultIfEmpty(new StringBuilder(""), null.asInstanceOf[StringBuilder]))
    val s = StringUtils.defaultIfEmpty(new StringBuilder("abc"), new StringBuilder("NULL"))
    assertEquals("abc", s.toString)
  }

  @Test def testDefaultIfEmpty_StringString(): Unit = {
    assertEquals("NULL", StringUtils.defaultIfEmpty(null, "NULL"))
    assertEquals("NULL", StringUtils.defaultIfEmpty("", "NULL"))
    assertEquals("abc", StringUtils.defaultIfEmpty("abc", "NULL"))
    assertNull(StringUtils.getIfEmpty("", null))
    val s = StringUtils.defaultIfEmpty("abc", "NULL")
    assertEquals("abc", s)
  }

  @Test def testGetIfEmpty_StringStringSupplier(): Unit = {
    assertEquals("NULL", StringUtils.getIfEmpty(null.asInstanceOf[String], nullStringSupplier))
    assertEquals("NULL", StringUtils.getIfEmpty("", nullStringSupplier))
    assertEquals("abc", StringUtils.getIfEmpty("abc", nullStringSupplier))
    assertNull(StringUtils.getIfEmpty("", nullSupplier))
    assertNull(StringUtils.defaultIfEmpty("", null.asInstanceOf[String]))
    val s = StringUtils.getIfEmpty("abc", nullStringSupplier)
    assertEquals("abc", s)
    val numberOfCalls = new MutableInt(0)

    val countingDefaultSupplier: Supplier[String] = new Supplier[String] {
      override def get(): String = {
        numberOfCalls.increment()
        "NULL"

      }
    }

    StringUtils.getIfEmpty("abc", countingDefaultSupplier)
    assertEquals(0, numberOfCalls.getValue)
    StringUtils.getIfEmpty("", countingDefaultSupplier)
    assertEquals(1, numberOfCalls.getValue)
    StringUtils.getIfEmpty(null, countingDefaultSupplier)
    assertEquals(2, numberOfCalls.getValue)
  }

  @Test def testDeleteWhitespace_String(): Unit = {
    assertNull(StringUtils.deleteWhitespace(null))
    assertEquals("", StringUtils.deleteWhitespace(""))
    assertEquals("", StringUtils.deleteWhitespace("  \u000C  \t\t\u001F\n\n \u000B  "))
    assertEquals("", StringUtils.deleteWhitespace(StringUtilsTest.WHITESPACE))
    assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.deleteWhitespace(StringUtilsTest.NON_WHITESPACE))
    // Note: u-2007 and u-000A both cause problems in the source code
    // it should ignore 2007 but delete 000A
    assertEquals("\u00A0\u202F", StringUtils.deleteWhitespace("  \u00A0  \t\t\n\n \u202F  "))
    assertEquals("\u00A0\u202F", StringUtils.deleteWhitespace("\u00A0\u202F"))
    assertEquals("test", StringUtils.deleteWhitespace("\u000Bt  \t\n\u0009e\rs\n\n   \tt"))
  }

  @Test def testDifference_StringString(): Unit = {
    assertNull(StringUtils.difference(null, null))
    assertEquals("", StringUtils.difference("", ""))
    assertEquals("abc", StringUtils.difference("", "abc"))
    assertEquals("", StringUtils.difference("abc", ""))
    assertEquals("i am a robot", StringUtils.difference(null, "i am a robot"))
    assertEquals("i am a machine", StringUtils.difference("i am a machine", null))
    assertEquals("robot", StringUtils.difference("i am a machine", "i am a robot"))
    assertEquals("", StringUtils.difference("abc", "abc"))
    assertEquals("you are a robot", StringUtils.difference("i am a robot", "you are a robot"))
  }

  @Test def testDifferenceAt_StringArray(): Unit = {
    // TODO: Handle array case
    //assertEquals(-1, StringUtils.indexOfDifference(null.asInstanceOf[Array[String]]))
    //assertEquals(-1, StringUtils.indexOfDifference())
    assertEquals(-1, StringUtils.indexOfDifference("abc"))
    assertEquals(-1, StringUtils.indexOfDifference(null, null))
    assertEquals(-1, StringUtils.indexOfDifference("", ""))
    assertEquals(0, StringUtils.indexOfDifference("", null))
    assertEquals(0, StringUtils.indexOfDifference("abc", null, null))
    assertEquals(0, StringUtils.indexOfDifference(null, null, "abc"))
    assertEquals(0, StringUtils.indexOfDifference("", "abc"))
    assertEquals(0, StringUtils.indexOfDifference("abc", ""))
    assertEquals(-1, StringUtils.indexOfDifference("abc", "abc"))
    assertEquals(1, StringUtils.indexOfDifference("abc", "a"))
    assertEquals(2, StringUtils.indexOfDifference("ab", "abxyz"))
    assertEquals(2, StringUtils.indexOfDifference("abcde", "abxyz"))
    assertEquals(0, StringUtils.indexOfDifference("abcde", "xyz"))
    assertEquals(0, StringUtils.indexOfDifference("xyz", "abcde"))
    assertEquals(7, StringUtils.indexOfDifference("i am a machine", "i am a robot"))
  }

  @Test def testDifferenceAt_StringString(): Unit = {
    assertEquals(-1, StringUtils.indexOfDifference(null, null))
    assertEquals(0, StringUtils.indexOfDifference(null, "i am a robot"))
    assertEquals(-1, StringUtils.indexOfDifference("", ""))
    assertEquals(0, StringUtils.indexOfDifference("", "abc"))
    assertEquals(0, StringUtils.indexOfDifference("abc", ""))
    assertEquals(0, StringUtils.indexOfDifference("i am a machine", null))
    assertEquals(7, StringUtils.indexOfDifference("i am a machine", "i am a robot"))
    assertEquals(-1, StringUtils.indexOfDifference("foo", "foo"))
    assertEquals(0, StringUtils.indexOfDifference("i am a robot", "you are a robot"))
  }

  /**
    * A sanity check for {@link StringUtils# EMPTY}.
    */
  @Test def testEMPTY(): Unit = {
    assertNotNull(StringUtils.EMPTY)
    assertEquals("", StringUtils.EMPTY)
    assertEquals(0, StringUtils.EMPTY.length)
  }

  @Test def testEscapeSurrogatePairs(): Unit = {
    assertEquals("\uD83D\uDE30", StringEscapeUtils.escapeCsv("\uD83D\uDE30"))
    // Examples from https://en.wikipedia.org/wiki/UTF-16
    assertEquals("\uD800\uDC00", StringEscapeUtils.escapeCsv("\uD800\uDC00"))
    assertEquals("\uD834\uDD1E", StringEscapeUtils.escapeCsv("\uD834\uDD1E"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeCsv("\uDBFF\uDFFD"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeHtml3("\uDBFF\uDFFD"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeHtml4("\uDBFF\uDFFD"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.escapeXml("\uDBFF\uDFFD"))
  }

  /**
    * Tests LANG-858.
    */
  @Test def testEscapeSurrogatePairsLang858(): Unit = {
    assertEquals("\\uDBFF\\uDFFD", StringEscapeUtils.escapeJava("\uDBFF\uDFFD")) //fail LANG-858
    assertEquals("\\uDBFF\\uDFFD", StringEscapeUtils.escapeEcmaScript("\uDBFF\uDFFD"))
  }

  @Test def testGetBytes_Charset(): Unit = {
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, StringUtils.getBytes(null, null.asInstanceOf[Charset]))
    assertArrayEquals(StringUtils.EMPTY.getBytes, StringUtils.getBytes(StringUtils.EMPTY, null.asInstanceOf[Charset]))
    assertArrayEquals(
      StringUtils.EMPTY.getBytes(StandardCharsets.US_ASCII),
      StringUtils.getBytes(StringUtils.EMPTY, StandardCharsets.US_ASCII))
  }

  @Test
  @throws[UnsupportedEncodingException]
  def testGetBytes_String(): Unit = {
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, StringUtils.getBytes(null, null.asInstanceOf[String]))
    assertArrayEquals(StringUtils.EMPTY.getBytes, StringUtils.getBytes(StringUtils.EMPTY, null.asInstanceOf[String]))
    assertArrayEquals(
      StringUtils.EMPTY.getBytes(StandardCharsets.US_ASCII.name),
      StringUtils.getBytes(StringUtils.EMPTY, StandardCharsets.US_ASCII.name))
  }

  @Test def testGetCommonPrefix_StringArray(): Unit = {
    //assertEquals("", StringUtils.getCommonPrefix(null.asInstanceOf[Array[String]]))
    assertEquals("", StringUtils.getCommonPrefix())
    assertEquals("abc", StringUtils.getCommonPrefix("abc"))
    assertEquals("", StringUtils.getCommonPrefix(null, null))
    assertEquals("", StringUtils.getCommonPrefix("", ""))
    assertEquals("", StringUtils.getCommonPrefix("", null))
    assertEquals("", StringUtils.getCommonPrefix("abc", null, null))
    assertEquals("", StringUtils.getCommonPrefix(null, null, "abc"))
    assertEquals("", StringUtils.getCommonPrefix("", "abc"))
    assertEquals("", StringUtils.getCommonPrefix("abc", ""))
    assertEquals("abc", StringUtils.getCommonPrefix("abc", "abc"))
    assertEquals("a", StringUtils.getCommonPrefix("abc", "a"))
    assertEquals("ab", StringUtils.getCommonPrefix("ab", "abxyz"))
    assertEquals("ab", StringUtils.getCommonPrefix("abcde", "abxyz"))
    assertEquals("", StringUtils.getCommonPrefix("abcde", "xyz"))
    assertEquals("", StringUtils.getCommonPrefix("xyz", "abcde"))
    assertEquals("i am a ", StringUtils.getCommonPrefix("i am a machine", "i am a robot"))
  }

  @Test def testGetDigits(): Unit = {
    assertNull(StringUtils.getDigits(null))
    assertEquals("", StringUtils.getDigits(""))
    assertEquals("", StringUtils.getDigits("abc"))
    assertEquals("1000", StringUtils.getDigits("1000$"))
    assertEquals("12345", StringUtils.getDigits("123password45"))
    assertEquals("5417543010", StringUtils.getDigits("(541) 754-3010"))
    assertEquals("\u0967\u0968\u0969", StringUtils.getDigits("\u0967\u0968\u0969"))
  }

//  @Test def testGetFuzzyDistance(): Unit = {
//    assertEquals(0, StringUtils.getFuzzyDistance("", "", Locale.ENGLISH))
//    assertEquals(0, StringUtils.getFuzzyDistance("Workshop", "b", Locale.ENGLISH))
//    assertEquals(1, StringUtils.getFuzzyDistance("Room", "o", Locale.ENGLISH))
//    assertEquals(1, StringUtils.getFuzzyDistance("Workshop", "w", Locale.ENGLISH))
//    assertEquals(2, StringUtils.getFuzzyDistance("Workshop", "ws", Locale.ENGLISH))
//    assertEquals(4, StringUtils.getFuzzyDistance("Workshop", "wo", Locale.ENGLISH))
//    assertEquals(3, StringUtils.getFuzzyDistance("Apache Software Foundation", "asf", Locale.ENGLISH))
//  }
//
//  @Test def testGetFuzzyDistance_NullNullNull(): Unit = assertThrows[IllegalArgumentException](StringUtils.getFuzzyDistance(null, null, null))
//
//  @Test def testGetFuzzyDistance_NullStringLocale(): Unit = assertThrows[IllegalArgumentException](StringUtils.getFuzzyDistance(null, "clear", Locale.ENGLISH))
//
//  @Test def testGetFuzzyDistance_StringNullLoclae(): Unit = assertThrows[IllegalArgumentException](StringUtils.getFuzzyDistance(" ", null, Locale.ENGLISH))
//
//  @Test def testGetFuzzyDistance_StringStringNull(): Unit = assertThrows[IllegalArgumentException](StringUtils.getFuzzyDistance(" ", "clear", null))
//
//  @Test def testGetJaroWinklerDistance_NullNull(): Unit = assertThrows[IllegalArgumentException](StringUtils.getJaroWinklerDistance(null, null))
//
//  @Test def testGetJaroWinklerDistance_NullString(): Unit = assertThrows[IllegalArgumentException](StringUtils.getJaroWinklerDistance(null, "clear"))
//
//  @Test def testGetJaroWinklerDistance_StringNull() = assertThrows[IllegalArgumentException](StringUtils.getJaroWinklerDistance(" ", null))
//
//  @Test def testGetJaroWinklerDistance_StringString(): Unit = {
//    assertEquals(0.93d, StringUtils.getJaroWinklerDistance("frog", "fog"))
//    assertEquals(0.0d, StringUtils.getJaroWinklerDistance("fly", "ant"))
//    assertEquals(0.44d, StringUtils.getJaroWinklerDistance("elephant", "hippo"))
//    assertEquals(0.84d, StringUtils.getJaroWinklerDistance("dwayne", "duane"))
//    assertEquals(0.93d, StringUtils.getJaroWinklerDistance("ABC Corporation", "ABC Corp"))
//    assertEquals(0.95d, StringUtils.getJaroWinklerDistance("D N H Enterprises Inc", "D & H Enterprises, Inc."))
//    assertEquals(0.92d, StringUtils.getJaroWinklerDistance("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"))
//    assertEquals(0.88d, StringUtils.getJaroWinklerDistance("PENNSYLVANIA", "PENNCISYLVNIA"))
//    assertEquals(0.63d, StringUtils.getJaroWinklerDistance("Haus Ingeborg", "Ingeborg Esser"))
//  }
//
//  @Test def testGetLevenshteinDistance_NullString(): Unit = assertThrows[IllegalArgumentException](StringUtils.getLevenshteinDistance("a", null))
//
//  @Test def testGetLevenshteinDistance_NullStringInt(): Unit = assertThrows[IllegalArgumentException](StringUtils.getLevenshteinDistance(null, "a", 0))
//
//  @Test def testGetLevenshteinDistance_StringNull(): Unit = assertThrows[IllegalArgumentException](StringUtils.getLevenshteinDistance(null, "a"))
//
//  @Test def testGetLevenshteinDistance_StringNullInt(): Unit = assertThrows[IllegalArgumentException](StringUtils.getLevenshteinDistance("a", null, 0))
//
//  @Test def testGetLevenshteinDistance_StringString(): Unit = {
//    assertEquals(0, StringUtils.getLevenshteinDistance("", ""))
//    assertEquals(1, StringUtils.getLevenshteinDistance("", "a"))
//    assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", ""))
//    assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog"))
//    assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant"))
//    assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo"))
//    assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant"))
//    assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz"))
//    assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo"))
//    assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo"))
//  }
//
//  @Test def testGetLevenshteinDistance_StringStringInt(): Unit = { // empty strings
//    assertEquals(0, StringUtils.getLevenshteinDistance("", "", 0))
//    assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "", 8))
//    assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "", 7))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("aaapppp", "", 6))
//    // unequal strings, zero threshold
//    assertEquals(-1, StringUtils.getLevenshteinDistance("b", "a", 0))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("a", "b", 0))
//    // equal strings
//    assertEquals(0, StringUtils.getLevenshteinDistance("aa", "aa", 0))
//    assertEquals(0, StringUtils.getLevenshteinDistance("aa", "aa", 2))
//    // same length
//    assertEquals(-1, StringUtils.getLevenshteinDistance("aaa", "bbb", 2))
//    assertEquals(3, StringUtils.getLevenshteinDistance("aaa", "bbb", 3))
//    // big stripe
//    assertEquals(6, StringUtils.getLevenshteinDistance("aaaaaa", "b", 10))
//    // distance less than threshold
//    assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "b", 8))
//    assertEquals(3, StringUtils.getLevenshteinDistance("a", "bbb", 4))
//    // distance equal to threshold
//    assertEquals(7, StringUtils.getLevenshteinDistance("aaapppp", "b", 7))
//    assertEquals(3, StringUtils.getLevenshteinDistance("a", "bbb", 3))
//    // distance greater than threshold
//    assertEquals(-1, StringUtils.getLevenshteinDistance("a", "bbb", 2))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("bbb", "a", 2))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("aaapppp", "b", 6))
//    // stripe runs off array, strings not similar
//    assertEquals(-1, StringUtils.getLevenshteinDistance("a", "bbb", 1))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("bbb", "a", 1))
//    // stripe runs off array, strings are similar
//    assertEquals(-1, StringUtils.getLevenshteinDistance("12345", "1234567", 1))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("1234567", "12345", 1))
//    // old getLevenshteinDistance test cases
//    assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog", 1))
//    assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant", 3))
//    assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo", 7))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("elephant", "hippo", 6))
//    assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant", 7))
//    assertEquals(-1, StringUtils.getLevenshteinDistance("hippo", "elephant", 6))
//    assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz", 8))
//    assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo", 8))
//    assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo", 1))
//    assertEquals(1, StringUtils.getLevenshteinDistance("frog", "fog", Integer.MAX_VALUE))
//    assertEquals(3, StringUtils.getLevenshteinDistance("fly", "ant", Integer.MAX_VALUE))
//    assertEquals(7, StringUtils.getLevenshteinDistance("elephant", "hippo", Integer.MAX_VALUE))
//    assertEquals(7, StringUtils.getLevenshteinDistance("hippo", "elephant", Integer.MAX_VALUE))
//    assertEquals(8, StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz", Integer.MAX_VALUE))
//    assertEquals(8, StringUtils.getLevenshteinDistance("zzzzzzzz", "hippo", Integer.MAX_VALUE))
//    assertEquals(1, StringUtils.getLevenshteinDistance("hello", "hallo", Integer.MAX_VALUE))
//  }
//
//  @Test def testGetLevenshteinDistance_StringStringNegativeInt() = assertThrows[IllegalArgumentException](StringUtils.getLevenshteinDistance("a", "a", -1))

  /**
    * Test for {@link StringUtils# isAllLowerCase ( CharSequence )}.
    */
  @Test def testIsAllLowerCase(): Unit = {
    assertFalse(StringUtils.isAllLowerCase(null))
    assertFalse(StringUtils.isAllLowerCase(StringUtils.EMPTY))
    assertFalse(StringUtils.isAllLowerCase("  "))
    assertTrue(StringUtils.isAllLowerCase("abc"))
    assertFalse(StringUtils.isAllLowerCase("abc "))
    assertFalse(StringUtils.isAllLowerCase("abc\n"))
    assertFalse(StringUtils.isAllLowerCase("abC"))
    assertFalse(StringUtils.isAllLowerCase("ab c"))
    assertFalse(StringUtils.isAllLowerCase("ab1c"))
    assertFalse(StringUtils.isAllLowerCase("ab/c"))
  }

  /**
    * Test for {@link StringUtils#isAllUpperCase}.
    */
  @Test def testIsAllUpperCase(): Unit = {
    assertFalse(StringUtils.isAllUpperCase(null))
    assertFalse(StringUtils.isAllUpperCase(StringUtils.EMPTY))
    assertFalse(StringUtils.isAllUpperCase("  "))
    assertTrue(StringUtils.isAllUpperCase("ABC"))
    assertFalse(StringUtils.isAllUpperCase("ABC "))
    assertFalse(StringUtils.isAllUpperCase("ABC\n"))
    assertFalse(StringUtils.isAllUpperCase("aBC"))
    assertFalse(StringUtils.isAllUpperCase("A C"))
    assertFalse(StringUtils.isAllUpperCase("A1C"))
    assertFalse(StringUtils.isAllUpperCase("A/C"))
  }

  /**
    * Test for {@link StringUtils#isMixedCase}.
    */
  @Test def testIsMixedCase(): Unit = {
    assertFalse(StringUtils.isMixedCase(null))
    assertFalse(StringUtils.isMixedCase(StringUtils.EMPTY))
    assertFalse(StringUtils.isMixedCase(" "))
    assertFalse(StringUtils.isMixedCase("A"))
    assertFalse(StringUtils.isMixedCase("a"))
    assertFalse(StringUtils.isMixedCase("/"))
    assertFalse(StringUtils.isMixedCase("A/"))
    assertFalse(StringUtils.isMixedCase("/b"))
    assertFalse(StringUtils.isMixedCase("abc"))
    assertFalse(StringUtils.isMixedCase("ABC"))
    assertTrue(StringUtils.isMixedCase("aBc"))
    assertTrue(StringUtils.isMixedCase("aBc "))
    assertTrue(StringUtils.isMixedCase("A c"))
    assertTrue(StringUtils.isMixedCase("aBc\n"))
    assertTrue(StringUtils.isMixedCase("A1c"))
    assertTrue(StringUtils.isMixedCase("a/C"))
  }

  @Test def testJoin_ArrayCharSeparator(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[AnyRef]], ','))
    assertEquals(
      StringUtilsTest.TEXT_LIST_CHAR,
      StringUtils.join(StringUtilsTest.ARRAY_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals(";;foo", StringUtils.join(StringUtilsTest.MIXED_ARRAY_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("foo;2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertNull(StringUtils.join(null.asInstanceOf[Array[AnyRef]], ',', 0, 1))
    assertEquals(
      "/",
      StringUtils.join(StringUtilsTest.MIXED_ARRAY_LIST, '/', 0, StringUtilsTest.MIXED_ARRAY_LIST.length - 1))
    assertEquals("foo", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, '/', 0, 1))
    assertEquals("null", StringUtils.join(StringUtilsTest.NULL_TO_STRING_LIST, '/', 0, 1))
    assertEquals("foo/2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, '/', 0, 2))
    assertEquals("2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, '/', 1, 2))
    assertEquals("", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, '/', 2, 1))
  }

  @Test def testJoin_ArrayOfBytes(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Byte]], ','))
    assertEquals("1;2", StringUtils.join(StringUtilsTest.BYTE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2", StringUtils.join(StringUtilsTest.BYTE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Byte]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.BYTE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.BYTE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayOfChars(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Char]], ','))
    assertEquals("1;2", StringUtils.join(StringUtilsTest.CHAR_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2", StringUtils.join(StringUtilsTest.CHAR_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Char]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.CHAR_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.CHAR_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayOfDoubles(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Double]], ','))
    assertEquals("1.0;2.0", StringUtils.join(StringUtilsTest.DOUBLE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2.0", StringUtils.join(StringUtilsTest.DOUBLE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Double]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.DOUBLE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.DOUBLE_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayOfFloats(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Float]], ','))
    assertEquals("1.0;2.0", StringUtils.join(StringUtilsTest.FLOAT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2.0", StringUtils.join(StringUtilsTest.FLOAT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Float]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.FLOAT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.FLOAT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayOfInts(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Int]], ','))
    assertEquals("1;2", StringUtils.join(StringUtilsTest.INT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2", StringUtils.join(StringUtilsTest.INT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Int]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.INT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.INT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayOfLongs(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Long]], ','))
    assertEquals("1;2", StringUtils.join(StringUtilsTest.LONG_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2", StringUtils.join(StringUtilsTest.LONG_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Long]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.LONG_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.LONG_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayOfShorts(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[Short]], ','))
    assertEquals("1;2", StringUtils.join(StringUtilsTest.SHORT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("2", StringUtils.join(StringUtilsTest.SHORT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 2))
    assertNull(StringUtils.join(null.asInstanceOf[Array[Short]], StringUtilsTest.SEPARATOR_CHAR, 0, 1))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.SHORT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 0, 0))
    assertEquals(
      StringUtils.EMPTY,
      StringUtils.join(StringUtilsTest.SHORT_PRIM_LIST, StringUtilsTest.SEPARATOR_CHAR, 1, 0))
  }

  @Test def testJoin_ArrayString(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Array[AnyRef]], null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.ARRAY_LIST, null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.ARRAY_LIST, ""))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_ARRAY_LIST, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, ""))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(StringUtilsTest.TEXT_LIST, StringUtils.join(StringUtilsTest.ARRAY_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(",,foo", StringUtils.join(StringUtilsTest.MIXED_ARRAY_LIST, StringUtilsTest.SEPARATOR))
    assertEquals("foo,2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(
      "/",
      StringUtils.join(StringUtilsTest.MIXED_ARRAY_LIST, "/", 0, StringUtilsTest.MIXED_ARRAY_LIST.length - 1))
    assertEquals(
      "",
      StringUtils.join(StringUtilsTest.MIXED_ARRAY_LIST, "", 0, StringUtilsTest.MIXED_ARRAY_LIST.length - 1))
    assertEquals("foo", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, "/", 0, 1))
    assertEquals("foo/2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, "/", 0, 2))
    assertEquals("2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, "/", 1, 2))
    assertEquals("", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST, "/", 2, 1))
  }

  @Test def testJoin_IterableChar(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Iterable[_]], ','))
    assertEquals(
      StringUtilsTest.TEXT_LIST_CHAR,
      StringUtils.join(StringUtilsTest.ARRAY_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_ARRAY_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("foo", StringUtils.join(Collections.singleton[String]("foo"), 'x'))
  }

  @Test def testJoin_IterableString(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[Iterable[_]], null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.ARRAY_LIST, null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.ARRAY_LIST, ""))
    assertEquals("foo", StringUtils.join(Collections.singleton("foo"), "x"))
    assertEquals("foo", StringUtils.join(Collections.singleton("foo"), null))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_ARRAY_LIST, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, ""))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(StringUtilsTest.TEXT_LIST, StringUtils.join(StringUtilsTest.ARRAY_LIST, StringUtilsTest.SEPARATOR))
  }

  @Test def testJoin_IteratorChar(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[util.Iterator[_]], ','))
    assertEquals(
      StringUtilsTest.TEXT_LIST_CHAR,
      StringUtils.join(StringUtilsTest.ARRAY_LIST.iterator, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_ARRAY_LIST.iterator, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST.iterator, StringUtilsTest.SEPARATOR_CHAR))
    assertEquals("foo", StringUtils.join(Collections.singleton("foo").iterator, 'x'))
  }

  @Test def testJoin_IteratorString(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[util.Iterator[_]], null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.ARRAY_LIST.iterator, null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.ARRAY_LIST.iterator, ""))
    assertEquals("foo", StringUtils.join(Collections.singleton("foo").iterator, "x"))
    assertEquals("foo", StringUtils.join(Collections.singleton("foo").iterator, null))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_ARRAY_LIST.iterator, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST.iterator, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST.iterator, ""))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST.iterator, StringUtilsTest.SEPARATOR))
    assertEquals(
      StringUtilsTest.TEXT_LIST,
      StringUtils.join(StringUtilsTest.ARRAY_LIST.iterator, StringUtilsTest.SEPARATOR))
    assertNull(StringUtils.join(StringUtilsTest.NULL_TO_STRING_LIST.iterator, StringUtilsTest.SEPARATOR))
  }

  @Test def testJoin_List(): Unit = {
    assertNull(StringUtils.join(null.asInstanceOf[util.List[String]], null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.STRING_LIST, null))
    assertEquals(StringUtilsTest.TEXT_LIST_NOSEP, StringUtils.join(StringUtilsTest.STRING_LIST, ""))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_STRING_LIST, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_STRING_LIST, null))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_STRING_LIST, ""))
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_STRING_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(StringUtilsTest.TEXT_LIST, StringUtils.join(StringUtilsTest.STRING_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(",,foo", StringUtils.join(StringUtilsTest.MIXED_STRING_LIST, StringUtilsTest.SEPARATOR))
    assertEquals("foo,2", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, StringUtilsTest.SEPARATOR))
    assertEquals(
      "/",
      StringUtils.join(StringUtilsTest.MIXED_STRING_LIST, "/", 0, StringUtilsTest.MIXED_STRING_LIST.size - 1))
    assertEquals(
      "",
      StringUtils.join(StringUtilsTest.MIXED_STRING_LIST, "", 0, StringUtilsTest.MIXED_STRING_LIST.size - 1))
    assertEquals("foo", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, "/", 0, 1))
    assertEquals("foo/2", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, "/", 0, 2))
    assertEquals("2", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, "/", 1, 2))
    assertEquals("", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, "/", 2, 1))
    assertNull(null, StringUtils.join(null.asInstanceOf[util.List[_]], "/", 0, 1))
    assertEquals(
      "/",
      StringUtils.join(StringUtilsTest.MIXED_STRING_LIST, '/', 0, StringUtilsTest.MIXED_STRING_LIST.size - 1))
    assertEquals("foo", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, '/', 0, 1))
    assertEquals("foo/2", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, '/', 0, 2))
    assertEquals("2", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, '/', 1, 2))
    assertEquals("", StringUtils.join(StringUtilsTest.MIXED_TYPE_OBJECT_LIST, '/', 2, 1))
    assertNull(null, StringUtils.join(null.asInstanceOf[util.List[_]], '/', 0, 1))
  }

  @Test def testJoin_Objectarray(): Unit = { //        assertNull(StringUtils.join(null)); // generates warning
    assertNull(StringUtils.join(null.asInstanceOf[Array[AnyRef]])) // equivalent explicit cast
    // test additional varargs calls
    assertEquals("", StringUtils.join()) // empty array
    assertEquals("", StringUtils.join(null.asInstanceOf[Any])) // => new Object[]{null}
    assertEquals("", StringUtils.join(StringUtilsTest.EMPTY_ARRAY_LIST))
    assertEquals("", StringUtils.join(StringUtilsTest.NULL_ARRAY_LIST))
    assertEquals("null", StringUtils.join(StringUtilsTest.NULL_TO_STRING_LIST))
    assertEquals("abc", StringUtils.join("a", "b", "c"))
    assertEquals("a", StringUtils.join(null, "a", ""))
    assertEquals("foo", StringUtils.join(StringUtilsTest.MIXED_ARRAY_LIST))
    assertEquals("foo2", StringUtils.join(StringUtilsTest.MIXED_TYPE_LIST))
  }

  //  @Disabled
  //  @Test def testLang1593(): Unit = {
  //    val arr = Array[Int](1, 2, 3, 4, 5, 6, 7)
  //    val expected = StringUtils.join(arr, '-')
  //    val actual = StringUtils.join(arr, "-")
  //    assertEquals(expected, actual)
  //  }

  @Test def testJoin_Objects(): Unit = {
    assertEquals("abc", StringUtils.join("a", "b", "c"))
    assertEquals("a", StringUtils.join(null, "", "a"))
    assertNull(StringUtils.join(null.asInstanceOf[Array[AnyRef]]))
  }

  @Test def testJoinWith(): Unit = {
    assertEquals("", StringUtils.joinWith(","))
    assertEquals("", StringUtils.joinWith(",", StringUtilsTest.NULL_ARRAY_LIST))
    assertEquals("null", StringUtils.joinWith(",", StringUtilsTest.NULL_TO_STRING_LIST)) //toString method prints 'null'
    assertEquals("a,b,c", StringUtils.joinWith(",", "a", "b", "c"))
    assertEquals(",a,", StringUtils.joinWith(",", null, "a", ""))
    assertEquals(",a,", StringUtils.joinWith(",", "", "a", ""))
    assertEquals("ab", StringUtils.joinWith(null, "a", "b"))
  }

  @Test def testJoinWithThrowsException(): Unit = {
    assertThrows[IllegalArgumentException](StringUtils.joinWith(",", null.asInstanceOf[Array[AnyRef]]))
    ()
  }

  @Test def testLang623(): Unit = {
    assertEquals("t", StringUtils.replaceChars("\u00DE", '\u00DE', 't'))
    assertEquals("t", StringUtils.replaceChars("\u00FE", '\u00FE', 't'))
  }

  @Test def testLANG666(): Unit = {
    assertEquals("12", StringUtils.stripEnd("120.00", ".0"))
    assertEquals("121", StringUtils.stripEnd("121.00", ".0"))
  }

  @Test def testLeftPad_StringInt(): Unit = {
    assertNull(StringUtils.leftPad(null, 5))
    assertEquals("     ", StringUtils.leftPad("", 5))
    assertEquals("  abc", StringUtils.leftPad("abc", 5))
    assertEquals("abc", StringUtils.leftPad("abc", 2))
  }

  @Test def testLeftPad_StringIntChar(): Unit = {
    assertNull(StringUtils.leftPad(null, 5, ' '))
    assertEquals("     ", StringUtils.leftPad("", 5, ' '))
    assertEquals("  abc", StringUtils.leftPad("abc", 5, ' '))
    assertEquals("xxabc", StringUtils.leftPad("abc", 5, 'x'))
    assertEquals("\uffff\uffffabc", StringUtils.leftPad("abc", 5, '\uffff'))
    assertEquals("abc", StringUtils.leftPad("abc", 2, ' '))
    val str: String = StringUtils.leftPad("aaa", 10000, 'a') // bigger than pad length
    assertEquals(10000, str.length)
    assertTrue(StringUtils.containsOnly(str, 'a'))
  }

  @Test def testLeftPad_StringIntString(): Unit = {
    assertNull(StringUtils.leftPad(null, 5, "-+"))
    assertNull(StringUtils.leftPad(null, 5, null))
    assertEquals("     ", StringUtils.leftPad("", 5, " "))
    assertEquals("-+-+abc", StringUtils.leftPad("abc", 7, "-+"))
    assertEquals("-+~abc", StringUtils.leftPad("abc", 6, "-+~"))
    assertEquals("-+abc", StringUtils.leftPad("abc", 5, "-+~"))
    assertEquals("abc", StringUtils.leftPad("abc", 2, " "))
    assertEquals("abc", StringUtils.leftPad("abc", -1, " "))
    assertEquals("  abc", StringUtils.leftPad("abc", 5, null))
    assertEquals("  abc", StringUtils.leftPad("abc", 5, ""))
  }

  @Test def testLength_CharBuffer(): Unit = {
    assertEquals(0, StringUtils.length(CharBuffer.wrap("")))
    assertEquals(1, StringUtils.length(CharBuffer.wrap("A")))
    assertEquals(1, StringUtils.length(CharBuffer.wrap(" ")))
    assertEquals(8, StringUtils.length(CharBuffer.wrap("ABCDEFGH")))
  }

  @Test def testLengthString(): Unit = {
    assertEquals(0, StringUtils.length(null))
    assertEquals(0, StringUtils.length(""))
    assertEquals(0, StringUtils.length(StringUtils.EMPTY))
    assertEquals(1, StringUtils.length("A"))
    assertEquals(1, StringUtils.length(" "))
    assertEquals(8, StringUtils.length("ABCDEFGH"))
  }

  @Test def testLengthStringBuffer(): Unit = {
    assertEquals(0, StringUtils.length(new StringBuffer("")))
    assertEquals(0, StringUtils.length(new StringBuffer(StringUtils.EMPTY)))
    assertEquals(1, StringUtils.length(new StringBuffer("A")))
    assertEquals(1, StringUtils.length(new StringBuffer(" ")))
    assertEquals(8, StringUtils.length(new StringBuffer("ABCDEFGH")))
  }

  @Test def testLengthStringBuilder(): Unit = {
    assertEquals(0, StringUtils.length(new StringBuilder("")))
    assertEquals(0, StringUtils.length(new StringBuilder(StringUtils.EMPTY)))
    assertEquals(1, StringUtils.length(new StringBuilder("A")))
    assertEquals(1, StringUtils.length(new StringBuilder(" ")))
    assertEquals(8, StringUtils.length(new StringBuilder("ABCDEFGH")))
  }

  @Test def testLowerCase(): Unit = {
    assertNull(StringUtils.lowerCase(null))
    assertNull(StringUtils.lowerCase(null, Locale.ENGLISH))
    assertEquals("lowerCase(String) failed", "foo test thing", StringUtils.lowerCase("fOo test THING"))
    assertEquals("lowerCase(empty-string) failed", "", StringUtils.lowerCase(""))
    assertEquals(
      "lowerCase(String, Locale) failed",
      "foo test thing",
      StringUtils.lowerCase("fOo test THING", Locale.ENGLISH))
    assertEquals("lowerCase(empty-string, Locale) failed", "", StringUtils.lowerCase("", Locale.ENGLISH))
  }

  @Test def testNormalizeSpace(): Unit = { // Java says a non-breaking whitespace is not a whitespace.
    assertFalse(Character.isWhitespace('\u00A0'))
    //
    assertNull(StringUtils.normalizeSpace(null))
    assertEquals("", StringUtils.normalizeSpace(""))
    assertEquals("", StringUtils.normalizeSpace(" "))
    assertEquals("", StringUtils.normalizeSpace("\t"))
    assertEquals("", StringUtils.normalizeSpace("\n"))
    assertEquals("", StringUtils.normalizeSpace("\u0009"))
    assertEquals("", StringUtils.normalizeSpace("\u000B"))
    assertEquals("", StringUtils.normalizeSpace("\u000C"))
    assertEquals("", StringUtils.normalizeSpace("\u001C"))
    assertEquals("", StringUtils.normalizeSpace("\u001D"))
    assertEquals("", StringUtils.normalizeSpace("\u001E"))
    assertEquals("", StringUtils.normalizeSpace("\u001F"))
    assertEquals("", StringUtils.normalizeSpace("\f"))
    assertEquals("", StringUtils.normalizeSpace("\r"))
    assertEquals("a", StringUtils.normalizeSpace("  a  "))
    assertEquals("a b c", StringUtils.normalizeSpace("  a  b   c  "))
    assertEquals("a b c", StringUtils.normalizeSpace("a\t\f\r  b\u000B   c\n"))
    assertEquals(
      "a   b c",
      StringUtils.normalizeSpace(
        "a\t\f\r  " + StringUtilsTest.HARD_SPACE + StringUtilsTest.HARD_SPACE + "b\u000B   c\n"))
    assertEquals("b", StringUtils.normalizeSpace("\u0000b"))
    assertEquals("b", StringUtils.normalizeSpace("b\u0000"))
  }

  @Test def testOverlay_StringStringIntInt(): Unit = {
    assertNull(StringUtils.overlay(null, null, 2, 4))
    assertNull(StringUtils.overlay(null, null, -2, -4))
    assertEquals("", StringUtils.overlay("", null, 0, 0))
    assertEquals("", StringUtils.overlay("", "", 0, 0))
    assertEquals("zzzz", StringUtils.overlay("", "zzzz", 0, 0))
    assertEquals("zzzz", StringUtils.overlay("", "zzzz", 2, 4))
    assertEquals("zzzz", StringUtils.overlay("", "zzzz", -2, -4))
    assertEquals("abef", StringUtils.overlay("abcdef", null, 2, 4))
    assertEquals("abef", StringUtils.overlay("abcdef", null, 4, 2))
    assertEquals("abef", StringUtils.overlay("abcdef", "", 2, 4))
    assertEquals("abef", StringUtils.overlay("abcdef", "", 4, 2))
    assertEquals("abzzzzef", StringUtils.overlay("abcdef", "zzzz", 2, 4))
    assertEquals("abzzzzef", StringUtils.overlay("abcdef", "zzzz", 4, 2))
    assertEquals("zzzzef", StringUtils.overlay("abcdef", "zzzz", -1, 4))
    assertEquals("zzzzef", StringUtils.overlay("abcdef", "zzzz", 4, -1))
    assertEquals("zzzzabcdef", StringUtils.overlay("abcdef", "zzzz", -2, -1))
    assertEquals("zzzzabcdef", StringUtils.overlay("abcdef", "zzzz", -1, -2))
    assertEquals("abcdzzzz", StringUtils.overlay("abcdef", "zzzz", 4, 10))
    assertEquals("abcdzzzz", StringUtils.overlay("abcdef", "zzzz", 10, 4))
    assertEquals("abcdefzzzz", StringUtils.overlay("abcdef", "zzzz", 8, 10))
    assertEquals("abcdefzzzz", StringUtils.overlay("abcdef", "zzzz", 10, 8))
  }

  /**
    * Tests {@code prependIfMissing}.
    */
  @Test def testPrependIfMissing(): Unit = {
    assertNull("prependIfMissing(null,null)", StringUtils.prependIfMissing(null, null))
    assertEquals("prependIfMissing(abc,null)", "abc", StringUtils.prependIfMissing("abc", null))
    assertEquals("prependIfMissing(\"\",xyz)", "xyz", StringUtils.prependIfMissing("", "xyz"))
    assertEquals("prependIfMissing(abc,xyz)", "xyzabc", StringUtils.prependIfMissing("abc", "xyz"))
    assertEquals("prependIfMissing(xyzabc,xyz)", "xyzabc", StringUtils.prependIfMissing("xyzabc", "xyz"))
    assertEquals("prependIfMissing(XYZabc,xyz)", "xyzXYZabc", StringUtils.prependIfMissing("XYZabc", "xyz"))
    assertNull(
      "prependIfMissing(null,null null)",
      StringUtils.prependIfMissing(null, null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "prependIfMissing(abc,null,null)",
      "abc",
      StringUtils.prependIfMissing("abc", null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "prependIfMissing(\"\",xyz,null)",
      "xyz",
      StringUtils.prependIfMissing("", "xyz", null.asInstanceOf[CharSequence]))
    assertEquals("prependIfMissing(abc,xyz,{null})", "xyzabc", StringUtils.prependIfMissing("abc", "xyz", null))
    assertEquals("prependIfMissing(abc,xyz,\"\")", "abc", StringUtils.prependIfMissing("abc", "xyz", ""))
    assertEquals("prependIfMissing(abc,xyz,mno)", "xyzabc", StringUtils.prependIfMissing("abc", "xyz", "mno"))
    assertEquals("prependIfMissing(xyzabc,xyz,mno)", "xyzabc", StringUtils.prependIfMissing("xyzabc", "xyz", "mno"))
    assertEquals("prependIfMissing(mnoabc,xyz,mno)", "mnoabc", StringUtils.prependIfMissing("mnoabc", "xyz", "mno"))
    assertEquals("prependIfMissing(XYZabc,xyz,mno)", "xyzXYZabc", StringUtils.prependIfMissing("XYZabc", "xyz", "mno"))
    assertEquals("prependIfMissing(MNOabc,xyz,mno)", "xyzMNOabc", StringUtils.prependIfMissing("MNOabc", "xyz", "mno"))
  }

  /**
    * Tests {@code prependIfMissingIgnoreCase}.
    */
  @Test def testPrependIfMissingIgnoreCase(): Unit = {
    assertNull("prependIfMissingIgnoreCase(null,null)", StringUtils.prependIfMissingIgnoreCase(null, null))
    assertEquals("prependIfMissingIgnoreCase(abc,null)", "abc", StringUtils.prependIfMissingIgnoreCase("abc", null))
    assertEquals("prependIfMissingIgnoreCase(\"\",xyz)", "xyz", StringUtils.prependIfMissingIgnoreCase("", "xyz"))
    assertEquals("prependIfMissingIgnoreCase(abc,xyz)", "xyzabc", StringUtils.prependIfMissingIgnoreCase("abc", "xyz"))
    assertEquals(
      "prependIfMissingIgnoreCase(xyzabc,xyz)",
      "xyzabc",
      StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz"))
    assertEquals(
      "prependIfMissingIgnoreCase(XYZabc,xyz)",
      "XYZabc",
      StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz"))
    assertNull(
      "prependIfMissingIgnoreCase(null,null null)",
      StringUtils.prependIfMissingIgnoreCase(null, null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "prependIfMissingIgnoreCase(abc,null,null)",
      "abc",
      StringUtils.prependIfMissingIgnoreCase("abc", null, null.asInstanceOf[CharSequence]))
    assertEquals(
      "prependIfMissingIgnoreCase(\"\",xyz,null)",
      "xyz",
      StringUtils.prependIfMissingIgnoreCase("", "xyz", null.asInstanceOf[CharSequence]))
    assertEquals(
      "prependIfMissingIgnoreCase(abc,xyz,{null})",
      "xyzabc",
      StringUtils.prependIfMissingIgnoreCase("abc", "xyz", null))
    assertEquals(
      "prependIfMissingIgnoreCase(abc,xyz,\"\")",
      "abc",
      StringUtils.prependIfMissingIgnoreCase("abc", "xyz", ""))
    assertEquals(
      "prependIfMissingIgnoreCase(abc,xyz,mno)",
      "xyzabc",
      StringUtils.prependIfMissingIgnoreCase("abc", "xyz", "mno"))
    assertEquals(
      "prependIfMissingIgnoreCase(xyzabc,xyz,mno)",
      "xyzabc",
      StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz", "mno"))
    assertEquals(
      "prependIfMissingIgnoreCase(mnoabc,xyz,mno)",
      "mnoabc",
      StringUtils.prependIfMissingIgnoreCase("mnoabc", "xyz", "mno"))
    assertEquals(
      "prependIfMissingIgnoreCase(XYZabc,xyz,mno)",
      "XYZabc",
      StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz", "mno"))
    assertEquals(
      "prependIfMissingIgnoreCase(MNOabc,xyz,mno)",
      "MNOabc",
      StringUtils.prependIfMissingIgnoreCase("MNOabc", "xyz", "mno"))
  }

  @Test def testReCapitalize(): Unit = { // reflection type of tests: Sentences.
    assertEquals(
      "uncapitalize(capitalize(String)) failed",
      StringUtilsTest.SENTENCE_UNCAP,
      StringUtils.uncapitalize(StringUtils.capitalize(StringUtilsTest.SENTENCE_UNCAP))
    )
    assertEquals(
      "capitalize(uncapitalize(String)) failed",
      StringUtilsTest.SENTENCE_CAP,
      StringUtils.capitalize(StringUtils.uncapitalize(StringUtilsTest.SENTENCE_CAP)))
    // reflection type of tests: One word.
    assertEquals(
      "uncapitalize(capitalize(String)) failed",
      StringUtilsTest.FOO_UNCAP,
      StringUtils.uncapitalize(StringUtils.capitalize(StringUtilsTest.FOO_UNCAP)))
    assertEquals(
      "capitalize(uncapitalize(String)) failed",
      StringUtilsTest.FOO_CAP,
      StringUtils.capitalize(StringUtils.uncapitalize(StringUtilsTest.FOO_CAP)))
  }

  @Test def testRemove_char(): Unit = { // StringUtils.remove(null, *)       = null
    assertNull(StringUtils.remove(null, 'a'))
    assertNull(StringUtils.remove(null, 'a'))
    assertNull(StringUtils.remove(null, 'a'))
    // StringUtils.remove("", *)          = ""
    assertEquals("", StringUtils.remove("", 'a'))
    assertEquals("", StringUtils.remove("", 'a'))
    assertEquals("", StringUtils.remove("", 'a'))
    // StringUtils.remove("queued", 'u') = "qeed"
    assertEquals("qeed", StringUtils.remove("queued", 'u'))
    // StringUtils.remove("queued", 'z') = "queued"
    assertEquals("queued", StringUtils.remove("queued", 'z'))
  }

  @Test def testRemove_String(): Unit = { // StringUtils.remove(null, *)        = null
    assertNull(StringUtils.remove(null, null))
    assertNull(StringUtils.remove(null, ""))
    assertNull(StringUtils.remove(null, "a"))
    assertEquals("", StringUtils.remove("", null))
    assertEquals("", StringUtils.remove("", ""))
    assertEquals("", StringUtils.remove("", "a"))
    // StringUtils.remove(*, null)        = *
    assertNull(StringUtils.remove(null, null))
    assertEquals("", StringUtils.remove("", null))
    assertEquals("a", StringUtils.remove("a", null))
    // StringUtils.remove(*, "")          = *
    assertNull(StringUtils.remove(null, ""))
    assertEquals("", StringUtils.remove("", ""))
    assertEquals("a", StringUtils.remove("a", ""))
    // StringUtils.remove("queued", "ue") = "qd"
    assertEquals("qd", StringUtils.remove("queued", "ue"))
    // StringUtils.remove("queued", "zz") = "queued"
    assertEquals("queued", StringUtils.remove("queued", "zz"))
  }

  @Test def testRemoveAll_StringString(): Unit = {
    assertNull(StringUtils.removeAll(null, ""))
    assertEquals("any", StringUtils.removeAll("any", null))
    assertEquals("any", StringUtils.removeAll("any", ""))
    assertEquals("", StringUtils.removeAll("any", ".*"))
    assertEquals("", StringUtils.removeAll("any", ".+"))
    assertEquals("", StringUtils.removeAll("any", ".?"))
    assertEquals("A\nB", StringUtils.removeAll("A<__>\n<__>B", "<.*>"))
    assertEquals("AB", StringUtils.removeAll("A<__>\n<__>B", "(?s)<.*>"))
    assertEquals("ABC123", StringUtils.removeAll("ABCabc123abc", "[a-z]"))
    assertThrows[PatternSyntaxException](
      StringUtils.removeAll("any", "{badRegexSyntax}")
    ) //, "StringUtils.removeAll expecting PatternSyntaxException")
    ()
  }

  @Test def testRemoveEnd(): Unit = { // StringUtils.removeEnd("", *)        = ""
    assertNull(StringUtils.removeEnd(null, null))
    assertNull(StringUtils.removeEnd(null, ""))
    assertNull(StringUtils.removeEnd(null, "a"))
    // StringUtils.removeEnd(*, null)      = *
    assertEquals(StringUtils.removeEnd("", null), "")
    assertEquals(StringUtils.removeEnd("", ""), "")
    assertEquals(StringUtils.removeEnd("", "a"), "")
    // All others:
    assertEquals(StringUtils.removeEnd("www.domain.com.", ".com"), "www.domain.com.")
    assertEquals(StringUtils.removeEnd("www.domain.com", ".com"), "www.domain")
    assertEquals(StringUtils.removeEnd("www.domain", ".com"), "www.domain")
    assertEquals(StringUtils.removeEnd("domain.com", ""), "domain.com")
    assertEquals(StringUtils.removeEnd("domain.com", null), "domain.com")
  }

  @Test def testRemoveEndIgnoreCase(): Unit = {
    // StringUtils.removeEndIgnoreCase("", *)        = ""
    assertNull("removeEndIgnoreCase(null, null)", StringUtils.removeEndIgnoreCase(null, null))
    assertNull("removeEndIgnoreCase(null, \"\")", StringUtils.removeEndIgnoreCase(null, ""))
    assertNull("removeEndIgnoreCase(null, \"a\")", StringUtils.removeEndIgnoreCase(null, "a"))
    assertEquals("removeEndIgnoreCase(\"\", null)", StringUtils.removeEndIgnoreCase("", null), "")
    assertEquals("removeEndIgnoreCase(\"\", \"\")", StringUtils.removeEndIgnoreCase("", ""), "")
    assertEquals("removeEndIgnoreCase(\"\", \"a\")", StringUtils.removeEndIgnoreCase("", "a"), "")
    assertEquals(
      "removeEndIgnoreCase(\"www.domain.com.\", \".com\")",
      StringUtils.removeEndIgnoreCase("www.domain.com.", ".com"),
      "www.domain.com.")
    assertEquals(
      "removeEndIgnoreCase(\"www.domain.com\", \".com\")",
      StringUtils.removeEndIgnoreCase("www.domain.com", ".com"),
      "www.domain")
    assertEquals(
      "removeEndIgnoreCase(\"www.domain\", \".com\")",
      StringUtils.removeEndIgnoreCase("www.domain", ".com"),
      "www.domain")
    assertEquals(
      "removeEndIgnoreCase(\"domain.com\", \"\")",
      StringUtils.removeEndIgnoreCase("domain.com", ""),
      "domain.com")
    assertEquals(
      "removeEndIgnoreCase(\"domain.com\", null)",
      StringUtils.removeEndIgnoreCase("domain.com", null),
      "domain.com")
    // Case insensitive:
    assertEquals(
      "removeEndIgnoreCase(\"www.domain.com\", \".COM\")",
      StringUtils.removeEndIgnoreCase("www.domain.com", ".COM"),
      "www.domain")
    assertEquals(
      "removeEndIgnoreCase(\"www.domain.COM\", \".com\")",
      StringUtils.removeEndIgnoreCase("www.domain.COM", ".com"),
      "www.domain")
  }

  @Test def testRemoveFirst_StringString(): Unit = {
    assertNull(StringUtils.removeFirst(null, ""))
    assertEquals("any", StringUtils.removeFirst("any", null))
    assertEquals("any", StringUtils.removeFirst("any", ""))
    assertEquals("", StringUtils.removeFirst("any", ".*"))
    assertEquals("", StringUtils.removeFirst("any", ".+"))
    assertEquals("bc", StringUtils.removeFirst("abc", ".?"))
    assertEquals("A\n<__>B", StringUtils.removeFirst("A<__>\n<__>B", "<.*>"))
    assertEquals("AB", StringUtils.removeFirst("A<__>\n<__>B", "(?s)<.*>"))
    assertEquals("ABCbc123", StringUtils.removeFirst("ABCabc123", "[a-z]"))
    assertEquals("ABC123abc", StringUtils.removeFirst("ABCabc123abc", "[a-z]+"))
    assertThrows[PatternSyntaxException](
      StringUtils.removeFirst("any", "{badRegexSyntax}")
    ) // , "StringUtils.removeFirst expecting PatternSyntaxException")
    ()
  }

  @Test def testRemoveIgnoreCase_String(): Unit = { // StringUtils.removeIgnoreCase(null, *) = null
    assertNull(StringUtils.removeIgnoreCase(null, null))
    assertNull(StringUtils.removeIgnoreCase(null, ""))
    assertNull(StringUtils.removeIgnoreCase(null, "a"))
    // StringUtils.removeIgnoreCase("", *) = ""
    assertEquals("", StringUtils.removeIgnoreCase("", null))
    assertEquals("", StringUtils.removeIgnoreCase("", ""))
    assertEquals("", StringUtils.removeIgnoreCase("", "a"))
    // StringUtils.removeIgnoreCase(*, null) = *
    assertNull(StringUtils.removeIgnoreCase(null, null))
    assertEquals("", StringUtils.removeIgnoreCase("", null))
    assertEquals("a", StringUtils.removeIgnoreCase("a", null))
    // StringUtils.removeIgnoreCase(*, "") = *
    assertNull(StringUtils.removeIgnoreCase(null, ""))
    assertEquals("", StringUtils.removeIgnoreCase("", ""))
    assertEquals("a", StringUtils.removeIgnoreCase("a", ""))
    // StringUtils.removeIgnoreCase("queued", "ue") = "qd"
    assertEquals("qd", StringUtils.removeIgnoreCase("queued", "ue"))
    // StringUtils.removeIgnoreCase("queued", "zz") = "queued"
    assertEquals("queued", StringUtils.removeIgnoreCase("queued", "zz"))
    // IgnoreCase
    // StringUtils.removeIgnoreCase("quEUed", "UE") = "qd"
    assertEquals("qd", StringUtils.removeIgnoreCase("quEUed", "UE"))
    // StringUtils.removeIgnoreCase("queued", "zZ") = "queued"
    assertEquals("queued", StringUtils.removeIgnoreCase("queued", "zZ"))
    // StringUtils.removeIgnoreCase("\u0130x", "x") = "\u0130"
    assertEquals("\u0130", StringUtils.removeIgnoreCase("\u0130x", "x"))
    // LANG-1453
    StringUtils.removeIgnoreCase("İa", "a")

    ()
  }

  @Test def testRemovePattern_StringString(): Unit = {
    assertNull(StringUtils.removePattern(null, ""))
    assertEquals("any", StringUtils.removePattern("any", null))
    assertEquals("", StringUtils.removePattern("", ""))
    assertEquals("", StringUtils.removePattern("", ".*"))
    assertEquals("", StringUtils.removePattern("", ".+"))
    assertEquals("AB", StringUtils.removePattern("A<__>\n<__>B", "<.*>"))
    assertEquals("AB", StringUtils.removePattern("A<__>\\n<__>B", "<.*>"))
    assertEquals("", StringUtils.removePattern("<A>x\\ny</A>", "<A>.*</A>"))
    assertEquals("", StringUtils.removePattern("<A>\nxy\n</A>", "<A>.*</A>"))
    assertEquals("ABC123", StringUtils.removePattern("ABCabc123", "[a-z]"))
  }

  @Test def testRemoveStart(): Unit = { // StringUtils.removeStart("", *)        = ""
    assertNull(StringUtils.removeStart(null, null))
    assertNull(StringUtils.removeStart(null, ""))
    assertNull(StringUtils.removeStart(null, "a"))
    // StringUtils.removeStart(*, null)      = *
    assertEquals(StringUtils.removeStart("", null), "")
    assertEquals(StringUtils.removeStart("", ""), "")
    assertEquals(StringUtils.removeStart("", "a"), "")
    assertEquals(StringUtils.removeStart("www.domain.com", "www."), "domain.com")
    assertEquals(StringUtils.removeStart("domain.com", "www."), "domain.com")
    assertEquals(StringUtils.removeStart("domain.com", ""), "domain.com")
    assertEquals(StringUtils.removeStart("domain.com", null), "domain.com")
  }

  @Test def testRemoveStartIgnoreCase(): Unit = {
    assertNull("removeStartIgnoreCase(null, null)", StringUtils.removeStartIgnoreCase(null, null))
    assertNull("removeStartIgnoreCase(null, \"\")", StringUtils.removeStartIgnoreCase(null, ""))
    assertNull("removeStartIgnoreCase(null, \"a\")", StringUtils.removeStartIgnoreCase(null, "a"))
    assertEquals("removeStartIgnoreCase(\"\", null)", StringUtils.removeStartIgnoreCase("", null), "")
    assertEquals("removeStartIgnoreCase(\"\", \"\")", StringUtils.removeStartIgnoreCase("", ""), "")
    assertEquals("removeStartIgnoreCase(\"\", \"a\")", StringUtils.removeStartIgnoreCase("", "a"), "")
    assertEquals(
      "removeStartIgnoreCase(\"www.domain.com\", \"www.\")",
      StringUtils.removeStartIgnoreCase("www.domain.com", "www."),
      "domain.com")
    assertEquals(
      "removeStartIgnoreCase(\"domain.com\", \"www.\")",
      StringUtils.removeStartIgnoreCase("domain.com", "www."),
      "domain.com")
    assertEquals(
      "removeStartIgnoreCase(\"domain.com\", \"\")",
      StringUtils.removeStartIgnoreCase("domain.com", ""),
      "domain.com")
    assertEquals(
      "removeStartIgnoreCase(\"domain.com\", null)",
      StringUtils.removeStartIgnoreCase("domain.com", null),
      "domain.com")
    assertEquals(
      "removeStartIgnoreCase(\"www.domain.com\", \"WWW.\")",
      StringUtils.removeStartIgnoreCase("www.domain.com", "WWW."),
      "domain.com")
  }

  @Test def testRepeat_CharInt(): Unit = {
    assertEquals("zzz", StringUtils.repeat('z', 3))
    assertEquals("", StringUtils.repeat('z', 0))
    assertEquals("", StringUtils.repeat('z', -2))
  }

  @Test def testRepeat_StringInt(): Unit = {
    assertNull(StringUtils.repeat(null, 2))
    assertEquals("", StringUtils.repeat("ab", 0))
    assertEquals("", StringUtils.repeat("", 3))
    assertEquals("aaa", StringUtils.repeat("a", 3))
    assertEquals("", StringUtils.repeat("a", -2))
    assertEquals("ababab", StringUtils.repeat("ab", 3))
    assertEquals("abcabcabc", StringUtils.repeat("abc", 3))
    val str = StringUtils.repeat("a", 10000) // bigger than pad limit
    assertEquals(10000, str.length)
    assertTrue(StringUtils.containsOnly(str, 'a'))
  }

  @Test def testRepeat_StringStringInt(): Unit = {
    assertNull(StringUtils.repeat(null, null, 2))
    assertNull(StringUtils.repeat(null, "x", 2))
    assertEquals("", StringUtils.repeat("", null, 2))
    assertEquals("", StringUtils.repeat("ab", "", 0))
    assertEquals("", StringUtils.repeat("", "", 2))
    assertEquals("xx", StringUtils.repeat("", "x", 3))
    assertEquals("?, ?, ?", StringUtils.repeat("?", ", ", 3))
  }

  /**
    * Test method for 'StringUtils.replaceEach(String, String[], String[])'
    */
  @Test def testReplace_StringStringArrayStringArray(): Unit = { //JAVADOC TESTS START
    assertNull(StringUtils.replaceEach(null, Array[String]("a"), Array[String]("b")))
    assertEquals(StringUtils.replaceEach("", Array[String]("a"), Array[String]("b")), "")
    assertEquals(StringUtils.replaceEach("aba", null, null), "aba")
    assertEquals(StringUtils.replaceEach("aba", new Array[String](0), null), "aba")
    assertEquals(StringUtils.replaceEach("aba", null, new Array[String](0)), "aba")
    assertEquals(StringUtils.replaceEach("aba", Array[String]("a"), null), "aba")
    assertEquals(StringUtils.replaceEach("aba", Array[String]("a"), Array[String]("")), "b")
    assertEquals(StringUtils.replaceEach("aba", Array[String](null), Array[String]("a")), "aba")
    assertEquals(StringUtils.replaceEach("abcde", Array[String]("ab", "d"), Array[String]("w", "t")), "wcte")
    assertEquals(StringUtils.replaceEach("abcde", Array[String]("ab", "d"), Array[String]("d", "t")), "dcte")
    //JAVADOC TESTS END
    assertEquals("bcc", StringUtils.replaceEach("abc", Array[String]("a", "b"), Array[String]("b", "c")))
    assertEquals(
      "q651.506bera",
      StringUtils.replaceEach(
        "d216.102oren",
        Array[String](
          "a",
          "b",
          "c",
          "d",
          "e",
          "f",
          "g",
          "h",
          "i",
          "j",
          "k",
          "l",
          "m",
          "n",
          "o",
          "p",
          "q",
          "r",
          "s",
          "t",
          "u",
          "v",
          "w",
          "x",
          "y",
          "z",
          "A",
          "B",
          "C",
          "D",
          "E",
          "F",
          "G",
          "H",
          "I",
          "J",
          "K",
          "L",
          "M",
          "N",
          "O",
          "P",
          "Q",
          "R",
          "S",
          "T",
          "U",
          "V",
          "W",
          "X",
          "Y",
          "Z",
          "1",
          "2",
          "3",
          "4",
          "5",
          "6",
          "7",
          "8",
          "9"
        ),
        Array[String](
          "n",
          "o",
          "p",
          "q",
          "r",
          "s",
          "t",
          "u",
          "v",
          "w",
          "x",
          "y",
          "z",
          "a",
          "b",
          "c",
          "d",
          "e",
          "f",
          "g",
          "h",
          "i",
          "j",
          "k",
          "l",
          "m",
          "N",
          "O",
          "P",
          "Q",
          "R",
          "S",
          "T",
          "U",
          "V",
          "W",
          "X",
          "Y",
          "Z",
          "A",
          "B",
          "C",
          "D",
          "E",
          "F",
          "G",
          "H",
          "I",
          "J",
          "K",
          "L",
          "M",
          "5",
          "6",
          "7",
          "8",
          "9",
          "1",
          "2",
          "3",
          "4"
        )
      )
    )
    // Test null safety inside arrays - LANG-552
    assertEquals(StringUtils.replaceEach("aba", Array[String]("a"), Array[String](null)), "aba")
    assertEquals(StringUtils.replaceEach("aba", Array[String]("a", "b"), Array[String]("c", null)), "cbc")
    assertThrows[IllegalArgumentException](
      StringUtils.replaceEach("abba", Array[String]("a"), Array[String]("b", "a"))
    ) // , "StringUtils.replaceEach(String, String[], String[]) expecting IllegalArgumentException")
    ()
  }

  /**
    * Test method for 'StringUtils.replaceEachRepeatedly(String, String[], String[])'
    */
  @Test def testReplace_StringStringArrayStringArrayBoolean(): Unit = {
    assertNull(StringUtils.replaceEachRepeatedly(null, Array[String]("a"), Array[String]("b")))
    assertEquals("", StringUtils.replaceEachRepeatedly("", Array[String]("a"), Array[String]("b")))
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", null, null))
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", new Array[String](0), null))
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", null, new Array[String](0)))
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", new Array[String](0), null))
    assertEquals("b", StringUtils.replaceEachRepeatedly("aba", Array[String]("a"), Array[String]("")))
    assertEquals("aba", StringUtils.replaceEachRepeatedly("aba", Array[String](null), Array[String]("a")))
    assertEquals("wcte", StringUtils.replaceEachRepeatedly("abcde", Array[String]("ab", "d"), Array[String]("w", "t")))
    assertEquals("tcte", StringUtils.replaceEachRepeatedly("abcde", Array[String]("ab", "d"), Array[String]("d", "t")))
    assertEquals("blaan", StringUtils.replaceEachRepeatedly("blllaan", Array[String]("llaan"), Array[String]("laan")))
    assertThrows[IllegalStateException](
      StringUtils.replaceEachRepeatedly("abcde", Array[String]("ab", "d"), Array[String]("d", "ab"))
    ) // , "Should be a circular reference")
    ()
  }

  @Test def testReplace_StringStringString(): Unit = {
    assertNull(StringUtils.replace(null, null, null))
    assertNull(StringUtils.replace(null, null, "any"))
    assertNull(StringUtils.replace(null, "any", null))
    assertNull(StringUtils.replace(null, "any", "any"))
    assertEquals("", StringUtils.replace("", null, null))
    assertEquals("", StringUtils.replace("", null, "any"))
    assertEquals("", StringUtils.replace("", "any", null))
    assertEquals("", StringUtils.replace("", "any", "any"))
    assertEquals("FOO", StringUtils.replace("FOO", "", "any"))
    assertEquals("FOO", StringUtils.replace("FOO", null, "any"))
    assertEquals("FOO", StringUtils.replace("FOO", "F", null))
    assertEquals("FOO", StringUtils.replace("FOO", null, null))
    assertEquals("", StringUtils.replace("foofoofoo", "foo", ""))
    assertEquals("barbarbar", StringUtils.replace("foofoofoo", "foo", "bar"))
    assertEquals("farfarfar", StringUtils.replace("foofoofoo", "oo", "ar"))
  }

  @Test def testReplace_StringStringStringInt(): Unit = {
    assertNull(StringUtils.replace(null, null, null, 2))
    assertNull(StringUtils.replace(null, null, "any", 2))
    assertNull(StringUtils.replace(null, "any", null, 2))
    assertNull(StringUtils.replace(null, "any", "any", 2))
    assertEquals("", StringUtils.replace("", null, null, 2))
    assertEquals("", StringUtils.replace("", null, "any", 2))
    assertEquals("", StringUtils.replace("", "any", null, 2))
    assertEquals("", StringUtils.replace("", "any", "any", 2))
    val str = new String(Array[Char]('o', 'o', 'f', 'o', 'o'))
    assertSame(str, StringUtils.replace(str, "x", "", -1))
    assertEquals("f", StringUtils.replace("oofoo", "o", "", -1))
    assertEquals("oofoo", StringUtils.replace("oofoo", "o", "", 0))
    assertEquals("ofoo", StringUtils.replace("oofoo", "o", "", 1))
    assertEquals("foo", StringUtils.replace("oofoo", "o", "", 2))
    assertEquals("fo", StringUtils.replace("oofoo", "o", "", 3))
    assertEquals("f", StringUtils.replace("oofoo", "o", "", 4))
    assertEquals("f", StringUtils.replace("oofoo", "o", "", -5))
    assertEquals("f", StringUtils.replace("oofoo", "o", "", 1000))
  }

  @Test def testReplaceAll_StringStringString(): Unit = {
    assertNull(StringUtils.replaceAll(null, "", ""))
    assertEquals("any", StringUtils.replaceAll("any", null, ""))
    assertEquals("any", StringUtils.replaceAll("any", "", null))
    assertEquals("zzz", StringUtils.replaceAll("", "", "zzz"))
    assertEquals("zzz", StringUtils.replaceAll("", ".*", "zzz"))
    assertEquals("", StringUtils.replaceAll("", ".+", "zzz"))
    assertEquals("ZZaZZbZZcZZ", StringUtils.replaceAll("abc", "", "ZZ"))
    assertEquals("z\nz", StringUtils.replaceAll("<__>\n<__>", "<.*>", "z"))
    assertEquals("z", StringUtils.replaceAll("<__>\n<__>", "(?s)<.*>", "z"))
    assertEquals("ABC___123", StringUtils.replaceAll("ABCabc123", "[a-z]", "_"))
    assertEquals("ABC_123", StringUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "_"))
    assertEquals("ABC123", StringUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", ""))
    assertEquals("Lorem_ipsum_dolor_sit", StringUtils.replaceAll("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"))
    assertThrows[PatternSyntaxException](
      StringUtils.replaceAll("any", "{badRegexSyntax}", "")
    ) //, "StringUtils.replaceAll expecting PatternSyntaxException")
    ()
  }

  @Test def testReplaceChars_StringCharChar(): Unit = {
    assertNull(StringUtils.replaceChars(null, 'b', 'z'))
    assertEquals("", StringUtils.replaceChars("", 'b', 'z'))
    assertEquals("azcza", StringUtils.replaceChars("abcba", 'b', 'z'))
    assertEquals("abcba", StringUtils.replaceChars("abcba", 'x', 'z'))
  }

  @Test def testReplaceChars_StringStringString(): Unit = {
    assertNull(StringUtils.replaceChars(null, null, null))
    assertNull(StringUtils.replaceChars(null, "", null))
    assertNull(StringUtils.replaceChars(null, "a", null))
    assertNull(StringUtils.replaceChars(null, null, ""))
    assertNull(StringUtils.replaceChars(null, null, "x"))
    assertEquals("", StringUtils.replaceChars("", null, null))
    assertEquals("", StringUtils.replaceChars("", "", null))
    assertEquals("", StringUtils.replaceChars("", "a", null))
    assertEquals("", StringUtils.replaceChars("", null, ""))
    assertEquals("", StringUtils.replaceChars("", null, "x"))
    assertEquals("abc", StringUtils.replaceChars("abc", null, null))
    assertEquals("abc", StringUtils.replaceChars("abc", null, ""))
    assertEquals("abc", StringUtils.replaceChars("abc", null, "x"))
    assertEquals("abc", StringUtils.replaceChars("abc", "", null))
    assertEquals("abc", StringUtils.replaceChars("abc", "", ""))
    assertEquals("abc", StringUtils.replaceChars("abc", "", "x"))
    assertEquals("ac", StringUtils.replaceChars("abc", "b", null))
    assertEquals("ac", StringUtils.replaceChars("abc", "b", ""))
    assertEquals("axc", StringUtils.replaceChars("abc", "b", "x"))
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yz"))
    assertEquals("ayya", StringUtils.replaceChars("abcba", "bc", "y"))
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yzx"))
    assertEquals("abcba", StringUtils.replaceChars("abcba", "z", "w"))
    assertSame("abcba", StringUtils.replaceChars("abcba", "z", "w"))
    // Javadoc examples:
    assertEquals("jelly", StringUtils.replaceChars("hello", "ho", "jy"))
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yz"))
    assertEquals("ayya", StringUtils.replaceChars("abcba", "bc", "y"))
    assertEquals("ayzya", StringUtils.replaceChars("abcba", "bc", "yzx"))
    // From https://issues.apache.org/bugzilla/show_bug.cgi?id=25454
    assertEquals("bcc", StringUtils.replaceChars("abc", "ab", "bc"))
    assertEquals(
      "q651.506bera",
      StringUtils.replaceChars(
        "d216.102oren",
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789",
        "nopqrstuvwxyzabcdefghijklmNOPQRSTUVWXYZABCDEFGHIJKLM567891234")
    )
  }

  @Test def testReplaceFirst_StringStringString(): Unit = {
    assertNull(StringUtils.replaceFirst(null, "", ""))
    assertEquals("any", StringUtils.replaceFirst("any", null, ""))
    assertEquals("any", StringUtils.replaceFirst("any", "", null))
    assertEquals("zzz", StringUtils.replaceFirst("", "", "zzz"))
    assertEquals("zzz", StringUtils.replaceFirst("", ".*", "zzz"))
    assertEquals("", StringUtils.replaceFirst("", ".+", "zzz"))
    assertEquals("ZZabc", StringUtils.replaceFirst("abc", "", "ZZ"))
    assertEquals("z\n<__>", StringUtils.replaceFirst("<__>\n<__>", "<.*>", "z"))
    assertEquals("z", StringUtils.replaceFirst("<__>\n<__>", "(?s)<.*>", "z"))
    assertEquals("ABC_bc123", StringUtils.replaceFirst("ABCabc123", "[a-z]", "_"))
    assertEquals("ABC_123abc", StringUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_"))
    assertEquals("ABC123abc", StringUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", ""))
    assertEquals(
      "Lorem_ipsum  dolor   sit",
      StringUtils.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"))
    assertThrows[PatternSyntaxException](
      StringUtils.replaceFirst("any", "{badRegexSyntax}", "")
    ) // , "StringUtils.replaceFirst expecting PatternSyntaxException")
    ()
  }

  @Test def testReplaceIgnoreCase_StringStringString(): Unit = {
    assertNull(StringUtils.replaceIgnoreCase(null, null, null))
    assertNull(StringUtils.replaceIgnoreCase(null, null, "any"))
    assertNull(StringUtils.replaceIgnoreCase(null, "any", null))
    assertNull(StringUtils.replaceIgnoreCase(null, "any", "any"))
    assertEquals("", StringUtils.replaceIgnoreCase("", null, null))
    assertEquals("", StringUtils.replaceIgnoreCase("", null, "any"))
    assertEquals("", StringUtils.replaceIgnoreCase("", "any", null))
    assertEquals("", StringUtils.replaceIgnoreCase("", "any", "any"))
    assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", "", "any"))
    assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", null, "any"))
    assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", "F", null))
    assertEquals("FOO", StringUtils.replaceIgnoreCase("FOO", null, null))
    assertEquals("", StringUtils.replaceIgnoreCase("foofoofoo", "foo", ""))
    assertEquals("barbarbar", StringUtils.replaceIgnoreCase("foofoofoo", "foo", "bar"))
    assertEquals("farfarfar", StringUtils.replaceIgnoreCase("foofoofoo", "oo", "ar"))
    assertEquals("", StringUtils.replaceIgnoreCase("foofoofoo", "FOO", ""))
    assertEquals("barbarbar", StringUtils.replaceIgnoreCase("fooFOOfoo", "foo", "bar"))
    assertEquals("farfarfar", StringUtils.replaceIgnoreCase("foofOOfoo", "OO", "ar"))
  }

  @Test def testReplaceIgnoreCase_StringStringStringInt(): Unit = {
    assertNull(StringUtils.replaceIgnoreCase(null, null, null, 2))
    assertNull(StringUtils.replaceIgnoreCase(null, null, "any", 2))
    assertNull(StringUtils.replaceIgnoreCase(null, "any", null, 2))
    assertNull(StringUtils.replaceIgnoreCase(null, "any", "any", 2))
    assertEquals("", StringUtils.replaceIgnoreCase("", null, null, 2))
    assertEquals("", StringUtils.replaceIgnoreCase("", null, "any", 2))
    assertEquals("", StringUtils.replaceIgnoreCase("", "any", null, 2))
    assertEquals("", StringUtils.replaceIgnoreCase("", "any", "any", 2))
    val str = new String(Array[Char]('o', 'o', 'f', 'o', 'o'))
    assertSame(str, StringUtils.replaceIgnoreCase(str, "x", "", -1))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "o", "", -1))
    assertEquals("oofoo", StringUtils.replaceIgnoreCase("oofoo", "o", "", 0))
    assertEquals("ofoo", StringUtils.replaceIgnoreCase("oofoo", "o", "", 1))
    assertEquals("foo", StringUtils.replaceIgnoreCase("oofoo", "o", "", 2))
    assertEquals("fo", StringUtils.replaceIgnoreCase("oofoo", "o", "", 3))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "o", "", 4))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "o", "", -5))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "o", "", 1000))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "O", "", -1))
    assertEquals("oofoo", StringUtils.replaceIgnoreCase("oofoo", "O", "", 0))
    assertEquals("ofoo", StringUtils.replaceIgnoreCase("oofoo", "O", "", 1))
    assertEquals("foo", StringUtils.replaceIgnoreCase("oofoo", "O", "", 2))
    assertEquals("fo", StringUtils.replaceIgnoreCase("oofoo", "O", "", 3))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "O", "", 4))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "O", "", -5))
    assertEquals("f", StringUtils.replaceIgnoreCase("oofoo", "O", "", 1000))
  }

  @Test def testReplaceOnce_StringStringString(): Unit = {
    assertNull(StringUtils.replaceOnce(null, null, null))
    assertNull(StringUtils.replaceOnce(null, null, "any"))
    assertNull(StringUtils.replaceOnce(null, "any", null))
    assertNull(StringUtils.replaceOnce(null, "any", "any"))
    assertEquals("", StringUtils.replaceOnce("", null, null))
    assertEquals("", StringUtils.replaceOnce("", null, "any"))
    assertEquals("", StringUtils.replaceOnce("", "any", null))
    assertEquals("", StringUtils.replaceOnce("", "any", "any"))
    assertEquals("FOO", StringUtils.replaceOnce("FOO", "", "any"))
    assertEquals("FOO", StringUtils.replaceOnce("FOO", null, "any"))
    assertEquals("FOO", StringUtils.replaceOnce("FOO", "F", null))
    assertEquals("FOO", StringUtils.replaceOnce("FOO", null, null))
    assertEquals("foofoo", StringUtils.replaceOnce("foofoofoo", "foo", ""))
  }

  @Test def testReplaceOnceIgnoreCase_StringStringString(): Unit = {
    assertNull(StringUtils.replaceOnceIgnoreCase(null, null, null))
    assertNull(StringUtils.replaceOnceIgnoreCase(null, null, "any"))
    assertNull(StringUtils.replaceOnceIgnoreCase(null, "any", null))
    assertNull(StringUtils.replaceOnceIgnoreCase(null, "any", "any"))
    assertEquals("", StringUtils.replaceOnceIgnoreCase("", null, null))
    assertEquals("", StringUtils.replaceOnceIgnoreCase("", null, "any"))
    assertEquals("", StringUtils.replaceOnceIgnoreCase("", "any", null))
    assertEquals("", StringUtils.replaceOnceIgnoreCase("", "any", "any"))
    assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", "", "any"))
    assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", null, "any"))
    assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", "F", null))
    assertEquals("FOO", StringUtils.replaceOnceIgnoreCase("FOO", null, null))
    assertEquals("foofoo", StringUtils.replaceOnceIgnoreCase("foofoofoo", "foo", ""))
    // Ignore Case
    assertEquals("Foofoo", StringUtils.replaceOnceIgnoreCase("FoOFoofoo", "foo", ""))
  }

  @Test def testReplacePattern_StringStringString(): Unit = {
    assertNull(StringUtils.replacePattern(null, "", ""))
    assertEquals("any", StringUtils.replacePattern("any", null, ""))
    assertEquals("any", StringUtils.replacePattern("any", "", null))
    assertEquals("zzz", StringUtils.replacePattern("", "", "zzz"))
    assertEquals("zzz", StringUtils.replacePattern("", ".*", "zzz"))
    assertEquals("", StringUtils.replacePattern("", ".+", "zzz"))
    assertEquals("z", StringUtils.replacePattern("<__>\n<__>", "<.*>", "z"))
    assertEquals("z", StringUtils.replacePattern("<__>\\n<__>", "<.*>", "z"))
    assertEquals("X", StringUtils.replacePattern("<A>\nxy\n</A>", "<A>.*</A>", "X"))
    assertEquals("ABC___123", StringUtils.replacePattern("ABCabc123", "[a-z]", "_"))
    assertEquals("ABC_123", StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_"))
    assertEquals("ABC123", StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", ""))
    assertEquals("Lorem_ipsum_dolor_sit", StringUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"))
  }

  @Test def testReverse_String(): Unit = {
    assertNull(StringUtils.reverse(null))
    assertEquals("", StringUtils.reverse(""))
    assertEquals("sdrawkcab", StringUtils.reverse("backwards"))
  }

  @Test def testReverseDelimited_StringChar(): Unit = {
    assertNull(StringUtils.reverseDelimited(null, '.'))
    assertEquals("", StringUtils.reverseDelimited("", '.'))
    assertEquals("c.b.a", StringUtils.reverseDelimited("a.b.c", '.'))
    assertEquals("a b c", StringUtils.reverseDelimited("a b c", '.'))
    assertEquals("", StringUtils.reverseDelimited("", '.'))
  }

  @Test def testRightPad_StringInt(): Unit = {
    assertNull(StringUtils.rightPad(null, 5))
    assertEquals("     ", StringUtils.rightPad("", 5))
    assertEquals("abc  ", StringUtils.rightPad("abc", 5))
    assertEquals("abc", StringUtils.rightPad("abc", 2))
    assertEquals("abc", StringUtils.rightPad("abc", -1))
  }

  @Test def testRightPad_StringIntChar(): Unit = {
    assertNull(StringUtils.rightPad(null, 5, ' '))
    assertEquals("     ", StringUtils.rightPad("", 5, ' '))
    assertEquals("abc  ", StringUtils.rightPad("abc", 5, ' '))
    assertEquals("abc", StringUtils.rightPad("abc", 2, ' '))
    assertEquals("abc", StringUtils.rightPad("abc", -1, ' '))
    assertEquals("abcxx", StringUtils.rightPad("abc", 5, 'x'))
    val str = StringUtils.rightPad("aaa", 10000, 'a')
    assertEquals(10000, str.length)
    assertTrue(StringUtils.containsOnly(str, 'a'))
  }

  @Test def testRightPad_StringIntString(): Unit = {
    assertNull(StringUtils.rightPad(null, 5, "-+"))
    assertEquals("     ", StringUtils.rightPad("", 5, " "))
    assertNull(StringUtils.rightPad(null, 8, null))
    assertEquals("abc-+-+", StringUtils.rightPad("abc", 7, "-+"))
    assertEquals("abc-+~", StringUtils.rightPad("abc", 6, "-+~"))
    assertEquals("abc-+", StringUtils.rightPad("abc", 5, "-+~"))
    assertEquals("abc", StringUtils.rightPad("abc", 2, " "))
    assertEquals("abc", StringUtils.rightPad("abc", -1, " "))
    assertEquals("abc  ", StringUtils.rightPad("abc", 5, null))
    assertEquals("abc  ", StringUtils.rightPad("abc", 5, ""))
  }

  @Test def testRotate_StringInt(): Unit = {
    assertNull(StringUtils.rotate(null, 1))
    assertEquals("", StringUtils.rotate("", 1))
    assertEquals("abcdefg", StringUtils.rotate("abcdefg", 0))
    assertEquals("fgabcde", StringUtils.rotate("abcdefg", 2))
    assertEquals("cdefgab", StringUtils.rotate("abcdefg", -2))
    assertEquals("abcdefg", StringUtils.rotate("abcdefg", 7))
    assertEquals("abcdefg", StringUtils.rotate("abcdefg", -7))
    assertEquals("fgabcde", StringUtils.rotate("abcdefg", 9))
    assertEquals("cdefgab", StringUtils.rotate("abcdefg", -9))
    assertEquals("efgabcd", StringUtils.rotate("abcdefg", 17))
    assertEquals("defgabc", StringUtils.rotate("abcdefg", -17))
  }

  @Test def testSplit_String(): Unit = {
    assertNull(StringUtils.split(null))
    assertEquals(0, StringUtils.split("").length)
    var str = "a b  .c"
    var res = StringUtils.split(str)
    assertEquals(3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals(".c", res(2))
    str = " a "
    res = StringUtils.split(str)
    assertEquals(1, res.length)
    assertEquals("a", res(0))
    str = "a" + StringUtilsTest.WHITESPACE + "b" + StringUtilsTest.NON_WHITESPACE + "c"
    res = StringUtils.split(str)
    assertEquals(2, res.length)
    assertEquals("a", res(0))
    assertEquals("b" + StringUtilsTest.NON_WHITESPACE + "c", res(1))
  }

  @Test def testSplit_StringChar(): Unit = {
    assertNull(StringUtils.split(null, '.'))
    assertEquals(0, StringUtils.split("", '.').length)
    var str = "a.b.. c"
    var res = StringUtils.split(str, '.')
    assertEquals(3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals(" c", res(2))
    str = ".a."
    res = StringUtils.split(str, '.')
    assertEquals(1, res.length)
    assertEquals("a", res(0))
    str = "a b c"
    res = StringUtils.split(str, ' ')
    assertEquals(3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals("c", res(2))
  }

  @Test def testSplit_StringString_StringStringInt(): Unit = {
    assertNull(StringUtils.split(null, "."))
    assertNull(StringUtils.split(null, ".", 3))
    assertEquals(0, StringUtils.split("", ".").length)
    assertEquals(0, StringUtils.split("", ".", 3).length)
    innerTestSplit('.', ".", ' ')
    innerTestSplit('.', ".", ',')
    innerTestSplit('.', ".,", 'x')
    for (i <- 0 until StringUtilsTest.WHITESPACE.length) {
      for (j <- 0 until StringUtilsTest.NON_WHITESPACE.length) {
        innerTestSplit(StringUtilsTest.WHITESPACE.charAt(i), null, StringUtilsTest.NON_WHITESPACE.charAt(j))
        innerTestSplit(
          StringUtilsTest.WHITESPACE.charAt(i),
          String.valueOf(StringUtilsTest.WHITESPACE.charAt(i)),
          StringUtilsTest.NON_WHITESPACE.charAt(j))
      }
    }
    var results: Array[String] = null

    val expectedResults = Array("ab", "de fg")
    results = StringUtils.split("ab   de fg", null, 2)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }
    val expectedResults2 = Array("ab", "cd:ef")
    results = StringUtils.split("ab:cd:ef", ":", 2)
    assertEquals(expectedResults2.length, results.length)
    for (i <- 0 until expectedResults2.length) {
      assertEquals(expectedResults2(i), results(i))
    }
  }

  @Test def testSplitByCharacterType(): Unit = {
    assertNull(StringUtils.splitByCharacterType(null))
    assertEquals(0, StringUtils.splitByCharacterType("").length)
    assertTrue(
      Objects.deepEquals(Array[String]("ab", " ", "de", " ", "fg"), StringUtils.splitByCharacterType("ab de fg")))
    assertTrue(
      Objects.deepEquals(Array[String]("ab", "   ", "de", " ", "fg"), StringUtils.splitByCharacterType("ab   de fg")))
    assertTrue(
      Objects.deepEquals(Array[String]("ab", ":", "cd", ":", "ef"), StringUtils.splitByCharacterType("ab:cd:ef")))
    assertTrue(Objects.deepEquals(Array[String]("number", "5"), StringUtils.splitByCharacterType("number5")))
    assertTrue(Objects.deepEquals(Array[String]("foo", "B", "ar"), StringUtils.splitByCharacterType("fooBar")))
    assertTrue(
      Objects.deepEquals(Array[String]("foo", "200", "B", "ar"), StringUtils.splitByCharacterType("foo200Bar")))
    assertTrue(Objects.deepEquals(Array[String]("ASFR", "ules"), StringUtils.splitByCharacterType("ASFRules")))
  }

  @Test def testSplitByCharacterTypeCamelCase(): Unit = {
    assertNull(StringUtils.splitByCharacterTypeCamelCase(null))
    assertEquals(0, StringUtils.splitByCharacterTypeCamelCase("").length)
    assertTrue(
      Objects
        .deepEquals(Array[String]("ab", " ", "de", " ", "fg"), StringUtils.splitByCharacterTypeCamelCase("ab de fg")))
    assertTrue(Objects
      .deepEquals(Array[String]("ab", "   ", "de", " ", "fg"), StringUtils.splitByCharacterTypeCamelCase("ab   de fg")))
    assertTrue(
      Objects
        .deepEquals(Array[String]("ab", ":", "cd", ":", "ef"), StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")))
    assertTrue(Objects.deepEquals(Array[String]("number", "5"), StringUtils.splitByCharacterTypeCamelCase("number5")))
    assertTrue(Objects.deepEquals(Array[String]("foo", "Bar"), StringUtils.splitByCharacterTypeCamelCase("fooBar")))
    assertTrue(
      Objects.deepEquals(Array[String]("foo", "200", "Bar"), StringUtils.splitByCharacterTypeCamelCase("foo200Bar")))
    assertTrue(Objects.deepEquals(Array[String]("ASF", "Rules"), StringUtils.splitByCharacterTypeCamelCase("ASFRules")))
  }

  @Test def testSplitByWholeSeparatorPreserveAllTokens_StringString(): Unit = {
    assertArrayEquals(
      null.asInstanceOf[Array[Object]],
      StringUtils.splitByWholeSeparatorPreserveAllTokens(null, ".").asInstanceOf[Array[Object]])
    assertEquals(0, StringUtils.splitByWholeSeparatorPreserveAllTokens("", ".").length)
    // test whitespace
    var input = "ab   de fg"
    var expected = Array[String]("ab", "", "", "de", "fg")
    var actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, null)
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
    // test delimiter singlechar
    input = "1::2:::3::::4"
    expected = Array[String]("1", "", "2", "", "", "3", "", "", "", "4")
    actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, ":")
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
    // test delimiter multichar
    input = "1::2:::3::::4"
    expected = Array[String]("1", "2", ":3", "", "4")
    actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, "::")
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
  }

  @Test def testSplitByWholeSeparatorPreserveAllTokens_StringStringInt(): Unit = {
    assertArrayEquals(
      null.asInstanceOf[Array[Object]],
      StringUtils.splitByWholeSeparatorPreserveAllTokens(null, ".", -1).asInstanceOf[Array[Object]])
    assertEquals(0, StringUtils.splitByWholeSeparatorPreserveAllTokens("", ".", -1).length)
    var input = "ab   de fg"
    var expected = Array[String]("ab", "", "", "de", "fg")
    var actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, null, -1)
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
    input = "1::2:::3::::4"
    expected = Array[String]("1", "", "2", "", "", "3", "", "", "", "4")
    actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, ":", -1)
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
    input = "1::2:::3::::4"
    expected = Array[String]("1", "2", ":3", "", "4")
    actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, "::", -1)
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
    // test delimiter char with max
    input = "1::2::3:4"
    expected = Array[String]("1", "", "2", ":3:4")
    actual = StringUtils.splitByWholeSeparatorPreserveAllTokens(input, ":", 4)
    assertEquals(expected.length, actual.length)
    for (i <- 0 until actual.length) {
      assertEquals(expected(i), actual(i))
    }
  }

  @Test def testSplitByWholeString_StringStringBoolean(): Unit = {
    assertArrayEquals(
      null.asInstanceOf[Array[Object]],
      StringUtils.splitByWholeSeparator(null, ".").asInstanceOf[Array[Object]])
    assertEquals(0, StringUtils.splitByWholeSeparator("", ".").length)
    val stringToSplitOnNulls = "ab   de fg"
    val splitOnNullExpectedResults = Array("ab", "de", "fg")
    val splitOnNullResults = StringUtils.splitByWholeSeparator(stringToSplitOnNulls, null)
    assertEquals(splitOnNullExpectedResults.length, splitOnNullResults.length)
    for (i <- 0 until splitOnNullExpectedResults.length) {
      assertEquals(splitOnNullExpectedResults(i), splitOnNullResults(i))
    }
    val stringToSplitOnCharactersAndString = "abstemiouslyaeiouyabstemiously"
    val splitOnStringExpectedResults = Array("abstemiously", "abstemiously")
    val splitOnStringResults = StringUtils.splitByWholeSeparator(stringToSplitOnCharactersAndString, "aeiouy")
    assertEquals(splitOnStringExpectedResults.length, splitOnStringResults.length)
    for (i <- 0 until splitOnStringExpectedResults.length) {
      assertEquals(splitOnStringExpectedResults(i), splitOnStringResults(i))
    }
    val splitWithMultipleSeparatorExpectedResults = Array("ab", "cd", "ef")
    val splitWithMultipleSeparator = StringUtils.splitByWholeSeparator("ab:cd::ef", ":")
    assertEquals(splitWithMultipleSeparatorExpectedResults.length, splitWithMultipleSeparator.length)
    for (i <- 0 until splitWithMultipleSeparatorExpectedResults.length) {
      assertEquals(splitWithMultipleSeparatorExpectedResults(i), splitWithMultipleSeparator(i))
    }
  }

  @Test def testSplitByWholeString_StringStringBooleanInt(): Unit = {
    assertArrayEquals(
      null.asInstanceOf[Array[Object]],
      StringUtils.splitByWholeSeparator(null, ".", 3).asInstanceOf[Array[Object]])
    assertEquals(0, StringUtils.splitByWholeSeparator("", ".", 3).length)
    val stringToSplitOnNulls = "ab   de fg"
    val splitOnNullExpectedResults = Array("ab", "de fg")
    //String[] splitOnNullExpectedResults = { "ab", "de" } ;
    val splitOnNullResults = StringUtils.splitByWholeSeparator(stringToSplitOnNulls, null, 2)
    assertEquals(splitOnNullExpectedResults.length, splitOnNullResults.length)
    for (i <- 0 until splitOnNullExpectedResults.length) {
      assertEquals(splitOnNullExpectedResults(i), splitOnNullResults(i))
    }
    val stringToSplitOnCharactersAndString = "abstemiouslyaeiouyabstemiouslyaeiouyabstemiously"
    val splitOnStringExpectedResults = Array("abstemiously", "abstemiouslyaeiouyabstemiously")
    //String[] splitOnStringExpectedResults = { "abstemiously", "abstemiously" } ;
    val splitOnStringResults = StringUtils.splitByWholeSeparator(stringToSplitOnCharactersAndString, "aeiouy", 2)
    assertEquals(splitOnStringExpectedResults.length, splitOnStringResults.length)
    for (i <- 0 until splitOnStringExpectedResults.length) {
      assertEquals(splitOnStringExpectedResults(i), splitOnStringResults(i))
    }
  }

  @Test def testSplitPreserveAllTokens_String(): Unit = {
    assertNull(StringUtils.splitPreserveAllTokens(null))
    assertEquals(0, StringUtils.splitPreserveAllTokens("").length)
    var str = "abc def"
    var res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(2, res.length)
    assertEquals("abc", res(0))
    assertEquals("def", res(1))
    str = "abc  def"
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(3, res.length)
    assertEquals("abc", res(0))
    assertEquals("", res(1))
    assertEquals("def", res(2))
    str = " abc "
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(3, res.length)
    assertEquals("", res(0))
    assertEquals("abc", res(1))
    assertEquals("", res(2))
    str = "a b .c"
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals(".c", res(2))
    str = " a b .c"
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(4, res.length)
    assertEquals("", res(0))
    assertEquals("a", res(1))
    assertEquals("b", res(2))
    assertEquals(".c", res(3))
    str = "a  b  .c"
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(5, res.length)
    assertEquals("a", res(0))
    assertEquals("", res(1))
    assertEquals("b", res(2))
    assertEquals("", res(3))
    assertEquals(".c", res(4))
    str = " a  "
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(4, res.length)
    assertEquals("", res(0))
    assertEquals("a", res(1))
    assertEquals("", res(2))
    assertEquals("", res(3))
    str = " a  b"
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(4, res.length)
    assertEquals("", res(0))
    assertEquals("a", res(1))
    assertEquals("", res(2))
    assertEquals("b", res(3))
    str = "a" + StringUtilsTest.WHITESPACE + "b" + StringUtilsTest.NON_WHITESPACE + "c"
    res = StringUtils.splitPreserveAllTokens(str)
    assertEquals(StringUtilsTest.WHITESPACE.length + 1, res.length)
    assertEquals("a", res(0))
    for (i <- 1 until StringUtilsTest.WHITESPACE.length - 1) {
      assertEquals("", res(i))
    }
    assertEquals("b" + StringUtilsTest.NON_WHITESPACE + "c", res(StringUtilsTest.WHITESPACE.length))
  }

  @Test def testSplitPreserveAllTokens_StringChar(): Unit = {
    assertNull(StringUtils.splitPreserveAllTokens(null, '.'))
    assertEquals(0, StringUtils.splitPreserveAllTokens("", '.').length)
    var str = "a.b. c"
    var res = StringUtils.splitPreserveAllTokens(str, '.')
    assertEquals(3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals(" c", res(2))
    str = "a.b.. c"
    res = StringUtils.splitPreserveAllTokens(str, '.')
    assertEquals(4, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals("", res(2))
    assertEquals(" c", res(3))
    str = ".a."
    res = StringUtils.splitPreserveAllTokens(str, '.')
    assertEquals(3, res.length)
    assertEquals("", res(0))
    assertEquals("a", res(1))
    assertEquals("", res(2))
    str = ".a.."
    res = StringUtils.splitPreserveAllTokens(str, '.')
    assertEquals(4, res.length)
    assertEquals("", res(0))
    assertEquals("a", res(1))
    assertEquals("", res(2))
    assertEquals("", res(3))
    str = "..a."
    res = StringUtils.splitPreserveAllTokens(str, '.')
    assertEquals(4, res.length)
    assertEquals("", res(0))
    assertEquals("", res(1))
    assertEquals("a", res(2))
    assertEquals("", res(3))
    str = "..a"
    res = StringUtils.splitPreserveAllTokens(str, '.')
    assertEquals(3, res.length)
    assertEquals("", res(0))
    assertEquals("", res(1))
    assertEquals("a", res(2))
    str = "a b c"
    res = StringUtils.splitPreserveAllTokens(str, ' ')
    assertEquals(3, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals("c", res(2))
    str = "a  b  c"
    res = StringUtils.splitPreserveAllTokens(str, ' ')
    assertEquals(5, res.length)
    assertEquals("a", res(0))
    assertEquals("", res(1))
    assertEquals("b", res(2))
    assertEquals("", res(3))
    assertEquals("c", res(4))
    str = " a b c"
    res = StringUtils.splitPreserveAllTokens(str, ' ')
    assertEquals(4, res.length)
    assertEquals("", res(0))
    assertEquals("a", res(1))
    assertEquals("b", res(2))
    assertEquals("c", res(3))
    str = "  a b c"
    res = StringUtils.splitPreserveAllTokens(str, ' ')
    assertEquals(5, res.length)
    assertEquals("", res(0))
    assertEquals("", res(1))
    assertEquals("a", res(2))
    assertEquals("b", res(3))
    assertEquals("c", res(4))
    str = "a b c "
    res = StringUtils.splitPreserveAllTokens(str, ' ')
    assertEquals(4, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals("c", res(2))
    assertEquals("", res(3))
    str = "a b c  "
    res = StringUtils.splitPreserveAllTokens(str, ' ')
    assertEquals(5, res.length)
    assertEquals("a", res(0))
    assertEquals("b", res(1))
    assertEquals("c", res(2))
    assertEquals("", res(3))
    assertEquals("", res(3))

    // Match example in javadoc
    var results: Array[String] = null
    val expectedResults = Array("a", "", "b", "c")
    results = StringUtils.splitPreserveAllTokens("a..b.c", '.')
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }
  }

  @Test def testSplitPreserveAllTokens_StringString_StringStringInt(): Unit = {
    assertNull(StringUtils.splitPreserveAllTokens(null, "."))
    assertNull(StringUtils.splitPreserveAllTokens(null, ".", 3))
    assertEquals(0, StringUtils.splitPreserveAllTokens("", ".").length)
    assertEquals(0, StringUtils.splitPreserveAllTokens("", ".", 3).length)
    innerTestSplitPreserveAllTokens('.', ".", ' ')
    innerTestSplitPreserveAllTokens('.', ".", ',')
    innerTestSplitPreserveAllTokens('.', ".,", 'x')
    for (i <- 0 until StringUtilsTest.WHITESPACE.length) {
      for (j <- 0 until StringUtilsTest.NON_WHITESPACE.length) {
        innerTestSplitPreserveAllTokens(
          StringUtilsTest.WHITESPACE.charAt(i),
          null,
          StringUtilsTest.NON_WHITESPACE.charAt(j))
        innerTestSplitPreserveAllTokens(
          StringUtilsTest.WHITESPACE.charAt(i),
          String.valueOf(StringUtilsTest.WHITESPACE.charAt(i)),
          StringUtilsTest.NON_WHITESPACE.charAt(j))
      }
    }

    var results: Array[String] = null
    var expectedResults: Array[String] = Array("ab", "de fg")

    results = StringUtils.splitPreserveAllTokens("ab de fg", null, 2)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    results = null
    expectedResults = Array("ab", "  de fg")
    results = StringUtils.splitPreserveAllTokens("ab   de fg", null, 2)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    results = null
    expectedResults = Array("ab", "::de:fg")
    results = StringUtils.splitPreserveAllTokens("ab:::de:fg", ":", 2)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("ab", "", " de fg")
    results = StringUtils.splitPreserveAllTokens("ab   de fg", null, 3)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("ab", "", "", "de fg")
    results = StringUtils.splitPreserveAllTokens("ab   de fg", null, 4)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("ab", "cd:ef")
    results = StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("ab", ":cd:ef")
    results = StringUtils.splitPreserveAllTokens("ab::cd:ef", ":", 2)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("ab", "", ":cd:ef")
    results = StringUtils.splitPreserveAllTokens("ab:::cd:ef", ":", 3)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("ab", "", "", "cd:ef")
    results = StringUtils.splitPreserveAllTokens("ab:::cd:ef", ":", 4)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("", "ab", "", "", "cd:ef")
    results = StringUtils.splitPreserveAllTokens(":ab:::cd:ef", ":", 5)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }

    expectedResults = Array("", "", "ab", "", "", "cd:ef")
    results = StringUtils.splitPreserveAllTokens("::ab:::cd:ef", ":", 6)
    assertEquals(expectedResults.length, results.length)
    for (i <- 0 until expectedResults.length) {
      assertEquals(expectedResults(i), results(i))
    }
  }

  // Methods on StringUtils that are immutable in spirit (i.e. calculate the length)
  // should take a CharSequence parameter. Methods that are mutable in spirit (i.e. capitalize)
  // should take a String or String[] parameter and return String or String[].
  // This test enforces that this is done.
  @Test def testStringUtilsCharSequenceContract(): Unit = {
    val c: Class[_] = StringUtils.getClass
    // Methods that are expressly excluded from testStringUtilsCharSequenceContract()
    val excludeMethods = Array(
      "public int org.apache.commons.lang3.StringUtils$.compare(java.lang.String,java.lang.String)",
      "public int org.apache.commons.lang3.StringUtils$.compare(java.lang.String,java.lang.String,boolean)",
      "public int org.apache.commons.lang3.StringUtils$.compareIgnoreCase(java.lang.String,java.lang.String)",
      "public int org.apache.commons.lang3.StringUtils$.compareIgnoreCase(java.lang.String,java.lang.String,boolean)",
      "public byte[] org.apache.commons.lang3.StringUtils$.getBytes(java.lang.String,java.nio.charset.Charset)",
      "public byte[] org.apache.commons.lang3.StringUtils$.getBytes(java.lang.String,java.lang.String) throws java.io.UnsupportedEncodingException"
    )

    val methods = c.getMethods
    for {
      m <- methods
      methodStr = m.toString
      if !methodStr.contains("$anonfun$")
    } {
      if ((m.getReturnType eq classOf[String]) || (m.getReturnType eq classOf[Array[String]])) { // Assume this is mutable and ensure the first parameter is not CharSequence.
        // It may be String or it may be something else (String[], Object, Object[]) so
        // don't actively test for that.
        val params = m.getParameterTypes
        if (params.length > 0 && ((params(0) eq classOf[CharSequence]) || (params(0) eq classOf[Array[CharSequence]])))
          assertTrue(
            "The method \"" + methodStr + "\" appears to be mutable in spirit and therefore must not accept a CharSequence",
            !ArrayUtils.contains(excludeMethods, methodStr)
          )
      } else { // Assume this is immutable in spirit and ensure the first parameter is not String.
        // As above, it may be something other than CharSequence.
        val params = m.getParameterTypes
        if (params.length > 0 && ((params(0) eq classOf[String]) || (params(0) eq classOf[Array[String]])))
          assertTrue(
            "The method \"" + methodStr + "\" appears to be immutable in spirit and therefore must not accept a String",
            ArrayUtils.contains(excludeMethods, methodStr)
          )
      }
    }
  }

  @Test def testSwapCase_String(): Unit = {
    assertNull(StringUtils.swapCase(null))
    assertEquals("", StringUtils.swapCase(""))
    assertEquals("  ", StringUtils.swapCase("  "))
    assertEquals("i", WordUtils.swapCase("I"))
    assertEquals("I", WordUtils.swapCase("i"))
    assertEquals("I AM HERE 123", StringUtils.swapCase("i am here 123"))
    assertEquals("i aM hERE 123", StringUtils.swapCase("I Am Here 123"))
    assertEquals("I AM here 123", StringUtils.swapCase("i am HERE 123"))
    assertEquals("i am here 123", StringUtils.swapCase("I AM HERE 123"))
    val test = "This String contains a TitleCase character: \u01C8"
    val expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9"
    assertEquals(expect, WordUtils.swapCase(test))
    assertEquals(expect, StringUtils.swapCase(test))
  }

  @Test def testToCodePoints(): Unit = {
    val orphanedHighSurrogate = 0xd801
    val orphanedLowSurrogate = 0xdc00
    val supplementary = 0x2070e
    val codePoints = Array('a', orphanedHighSurrogate, 'b', 'c', supplementary, 'd', orphanedLowSurrogate, 'e')
    val s = new String(codePoints, 0, codePoints.length)
    assertArrayEquals(codePoints, StringUtils.toCodePoints(s))
    assertNull(StringUtils.toCodePoints(null))
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, StringUtils.toCodePoints(""))
  }

  /**
    * Tests {@link StringUtils# toEncodedString ( byte[ ], Charset)}
    *
    * @see StringUtils#toEncodedString(byte[], Charset)
    */
  @Test def testToEncodedString(): Unit = {
    val expectedString = "The quick brown fox jumps over the lazy dog."
    var encoding = SystemUtils.FILE_ENCODING
    var expectedBytes = expectedString.getBytes(Charset.defaultCharset)
    // sanity check start
    assertArrayEquals(expectedBytes, expectedString.getBytes)
    // sanity check end
    assertEquals(expectedString, StringUtils.toEncodedString(expectedBytes, Charset.defaultCharset))
    assertEquals(expectedString, StringUtils.toEncodedString(expectedBytes, Charset.forName(encoding)))
    encoding = "UTF-16"
    expectedBytes = expectedString.getBytes(Charset.forName(encoding))
    assertEquals(expectedString, StringUtils.toEncodedString(expectedBytes, Charset.forName(encoding)))
  }

  /**
    * Tests {@link StringUtils# toString ( byte[ ], String)}
    *
    * @throws java.io.UnsupportedEncodingException because the method under test max throw it
    * @see StringUtils#toString(byte[], String)
    */
  @Test
  @throws[UnsupportedEncodingException]
  def testToString(): Unit = {
    val expectedString = "The quick brown fox jumps over the lazy dog."
    var expectedBytes = expectedString.getBytes(Charset.defaultCharset)
    assertArrayEquals(expectedBytes, expectedString.getBytes)
    assertEquals(expectedString, StringUtils.toString(expectedBytes, null))
    assertEquals(expectedString, StringUtils.toString(expectedBytes, SystemUtils.FILE_ENCODING))
    val encoding = "UTF-16"
    expectedBytes = expectedString.getBytes(Charset.forName(encoding))
    assertEquals(expectedString, StringUtils.toString(expectedBytes, encoding))
  }

  @Test def testTruncate_StringInt(): Unit = {
    assertNull(StringUtils.truncate(null, 12))
    assertThrows[IllegalArgumentException](StringUtils.truncate(null, -1)) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate(null, -10)) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate(null, Integer.MIN_VALUE)
    ) //, "maxWith cannot be negative")
    assertEquals("", StringUtils.truncate("", 10))
    assertEquals("", StringUtils.truncate("", 10))
    assertEquals("abc", StringUtils.truncate("abcdefghij", 3))
    assertEquals("abcdef", StringUtils.truncate("abcdefghij", 6))
    assertEquals("", StringUtils.truncate("abcdefghij", 0))
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", -1)) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", -100)) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", Integer.MIN_VALUE)
    ) //, "maxWith cannot be negative")
    assertEquals("abcdefghij", StringUtils.truncate("abcdefghijklmno", 10))
    assertEquals("abcdefghijklmno", StringUtils.truncate("abcdefghijklmno", Integer.MAX_VALUE))
    assertEquals("abcde", StringUtils.truncate("abcdefghijklmno", 5))
    assertEquals("abc", StringUtils.truncate("abcdefghijklmno", 3))
  }

  @Test def testTruncate_StringIntInt(): Unit = {
    assertNull(StringUtils.truncate(null, 0, 12))
    assertThrows[IllegalArgumentException](StringUtils.truncate(null, -1, 0)) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate(null, -10, -4)) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate(null, Integer.MIN_VALUE, Integer.MIN_VALUE)
    ) //, "offset cannot be negative")
    assertNull(StringUtils.truncate(null, 10, 12))
    assertEquals("", StringUtils.truncate("", 0, 10))
    assertEquals("", StringUtils.truncate("", 2, 10))
    assertEquals("abc", StringUtils.truncate("abcdefghij", 0, 3))
    assertEquals("fghij", StringUtils.truncate("abcdefghij", 5, 6))
    assertEquals("", StringUtils.truncate("abcdefghij", 0, 0))
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", 0, -1)) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", 0, -10)) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", 0, -100)
    ) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", 1, -100)
    ) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", 0, Integer.MIN_VALUE)
    ) //, "maxWith cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", -1, 0)) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", -10, 0)) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", -100, 1)) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", Integer.MIN_VALUE, 0)
    ) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](StringUtils.truncate("abcdefghij", -1, -1)) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", -10, -10)
    ) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", -100, -100)
    ) //, "offset cannot be negative")
    assertThrows[IllegalArgumentException](
      StringUtils.truncate("abcdefghij", Integer.MIN_VALUE, Integer.MIN_VALUE)
    ) //, "offset cannot be negative")
    val raspberry = "raspberry peach"
    assertEquals("peach", StringUtils.truncate(raspberry, 10, 15))
    assertEquals("abcdefghij", StringUtils.truncate("abcdefghijklmno", 0, 10))
    assertEquals("abcdefghijklmno", StringUtils.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE))
    assertEquals("bcdefghijk", StringUtils.truncate("abcdefghijklmno", 1, 10))
    assertEquals("cdefghijkl", StringUtils.truncate("abcdefghijklmno", 2, 10))
    assertEquals("defghijklm", StringUtils.truncate("abcdefghijklmno", 3, 10))
    assertEquals("efghijklmn", StringUtils.truncate("abcdefghijklmno", 4, 10))
    assertEquals("fghijklmno", StringUtils.truncate("abcdefghijklmno", 5, 10))
    assertEquals("fghij", StringUtils.truncate("abcdefghijklmno", 5, 5))
    assertEquals("fgh", StringUtils.truncate("abcdefghijklmno", 5, 3))
    assertEquals("klm", StringUtils.truncate("abcdefghijklmno", 10, 3))
    assertEquals("klmno", StringUtils.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE))
    assertEquals("n", StringUtils.truncate("abcdefghijklmno", 13, 1))
    assertEquals("no", StringUtils.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE))
    assertEquals("o", StringUtils.truncate("abcdefghijklmno", 14, 1))
    assertEquals("o", StringUtils.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE))
    assertEquals("", StringUtils.truncate("abcdefghijklmno", 15, 1))
    assertEquals("", StringUtils.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE))
    assertEquals("", StringUtils.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE))
  }

  @Test def testUnCapitalize(): Unit = {
    assertNull(StringUtils.uncapitalize(null))
    assertEquals(
      "uncapitalize(String) failed",
      StringUtilsTest.FOO_UNCAP,
      StringUtils.uncapitalize(StringUtilsTest.FOO_CAP))
    assertEquals(
      "uncapitalize(string) failed",
      StringUtilsTest.FOO_UNCAP,
      StringUtils.uncapitalize(StringUtilsTest.FOO_UNCAP))
    assertEquals("uncapitalize(empty-string) failed", "", StringUtils.uncapitalize(""))
    assertEquals("uncapitalize(single-char-string) failed", "x", StringUtils.uncapitalize("X"))
    // Examples from uncapitalize Javadoc
    assertEquals("cat", StringUtils.uncapitalize("cat"))
    assertEquals("cat", StringUtils.uncapitalize("Cat"))
    assertEquals("cAT", StringUtils.uncapitalize("CAT"))
  }

  @Test def testUnescapeSurrogatePairs(): Unit = {
    assertEquals("\uD83D\uDE30", StringEscapeUtils.unescapeCsv("\uD83D\uDE30"))
    assertEquals("\uD800\uDC00", StringEscapeUtils.unescapeCsv("\uD800\uDC00"))
    assertEquals("\uD834\uDD1E", StringEscapeUtils.unescapeCsv("\uD834\uDD1E"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.unescapeCsv("\uDBFF\uDFFD"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.unescapeHtml3("\uDBFF\uDFFD"))
    assertEquals("\uDBFF\uDFFD", StringEscapeUtils.unescapeHtml4("\uDBFF\uDFFD"))
  }

  @Test def testUnwrap_StringChar(): Unit = {
    assertNull(StringUtils.unwrap(null, null))
    assertNull(StringUtils.unwrap(null, CharUtils.NUL))
    assertNull(StringUtils.unwrap(null, '1'))
    assertEquals("abc", StringUtils.unwrap("abc", null))
    assertEquals("a", StringUtils.unwrap("a", "a"))
    assertEquals("", StringUtils.unwrap("aa", "a"))
    assertEquals("abc", StringUtils.unwrap("\'abc\'", '\''))
    assertEquals("abc", StringUtils.unwrap("AabcA", 'A'))
    assertEquals("AabcA", StringUtils.unwrap("AAabcAA", 'A'))
    assertEquals("abc", StringUtils.unwrap("abc", 'b'))
    assertEquals("#A", StringUtils.unwrap("#A", '#'))
    assertEquals("A#", StringUtils.unwrap("A#", '#'))
    assertEquals("ABA", StringUtils.unwrap("AABAA", 'A'))
  }

  @Test def testUnwrap_StringString(): Unit = {
    assertNull(StringUtils.unwrap(null, null))
    assertNull(StringUtils.unwrap(null, ""))
    assertNull(StringUtils.unwrap(null, "1"))
    assertEquals("abc", StringUtils.unwrap("abc", null))
    assertEquals("abc", StringUtils.unwrap("abc", ""))
    assertEquals("a", StringUtils.unwrap("a", "a"))
    assertEquals("", StringUtils.unwrap("aa", "a"))
    assertEquals("abc", StringUtils.unwrap("\'abc\'", "\'"))
    assertEquals("abc", StringUtils.unwrap("\"abc\"", "\""))
    assertEquals("abc\"xyz", StringUtils.unwrap("\"abc\"xyz\"", "\""))
    assertEquals("abc\"xyz\"", StringUtils.unwrap("\"abc\"xyz\"\"", "\""))
    assertEquals("abc\'xyz\'", StringUtils.unwrap("\"abc\'xyz\'\"", "\""))
    assertEquals("\"abc\'xyz\'\"", StringUtils.unwrap("AA\"abc\'xyz\'\"AA", "AA"))
    assertEquals("\"abc\'xyz\'\"", StringUtils.unwrap("123\"abc\'xyz\'\"123", "123"))
    assertEquals("AA\"abc\'xyz\'\"", StringUtils.unwrap("AA\"abc\'xyz\'\"", "AA"))
    assertEquals("AA\"abc\'xyz\'\"AA", StringUtils.unwrap("AAA\"abc\'xyz\'\"AAA", "A"))
    assertEquals("\"abc\'xyz\'\"AA", StringUtils.unwrap("\"abc\'xyz\'\"AA", "AA"))
  }

  @Test def testUpperCase(): Unit = {
    assertNull(StringUtils.upperCase(null))
    assertNull(StringUtils.upperCase(null, Locale.ENGLISH))
    assertEquals("upperCase(String) failed", "FOO TEST THING", StringUtils.upperCase("fOo test THING"))
    assertEquals("upperCase(empty-string) failed", "", StringUtils.upperCase(""))
    assertEquals(
      "upperCase(String, Locale) failed",
      "FOO TEST THING",
      StringUtils.upperCase("fOo test THING", Locale.ENGLISH))
    assertEquals("upperCase(empty-string, Locale) failed", "", StringUtils.upperCase("", Locale.ENGLISH))
  }

  @Test def testWrap_StringChar(): Unit = {
    assertNull(StringUtils.wrap(null, CharUtils.NUL))
    assertNull(StringUtils.wrap(null, '1'))
    assertEquals("", StringUtils.wrap("", CharUtils.NUL))
    assertEquals("xabx", StringUtils.wrap("ab", 'x'))
    assertEquals("\"ab\"", StringUtils.wrap("ab", '\"'))
    assertEquals("\"\"ab\"\"", StringUtils.wrap("\"ab\"", '\"'))
    assertEquals("'ab'", StringUtils.wrap("ab", '\''))
    assertEquals("''abcd''", StringUtils.wrap("'abcd'", '\''))
    assertEquals("'\"abcd\"'", StringUtils.wrap("\"abcd\"", '\''))
    assertEquals("\"'abcd'\"", StringUtils.wrap("'abcd'", '\"'))
  }

  @Test def testWrap_StringString(): Unit = {
    assertNull(StringUtils.wrap(null, null))
    assertNull(StringUtils.wrap(null, ""))
    assertNull(StringUtils.wrap(null, "1"))
    assertNull(StringUtils.wrap(null, null))
    assertEquals("", StringUtils.wrap("", ""))
    assertEquals("ab", StringUtils.wrap("ab", null))
    assertEquals("xabx", StringUtils.wrap("ab", "x"))
    assertEquals("\"ab\"", StringUtils.wrap("ab", "\""))
    assertEquals("\"\"ab\"\"", StringUtils.wrap("\"ab\"", "\""))
    assertEquals("'ab'", StringUtils.wrap("ab", "'"))
    assertEquals("''abcd''", StringUtils.wrap("'abcd'", "'"))
    assertEquals("'\"abcd\"'", StringUtils.wrap("\"abcd\"", "'"))
    assertEquals("\"'abcd'\"", StringUtils.wrap("'abcd'", "\""))
  }

  @Test def testWrapIfMissing_StringChar(): Unit = {
    assertNull(StringUtils.wrapIfMissing(null, CharUtils.NUL))
    assertNull(StringUtils.wrapIfMissing(null, '1'))
    assertEquals("", StringUtils.wrapIfMissing("", CharUtils.NUL))
    assertEquals("xabx", StringUtils.wrapIfMissing("ab", 'x'))
    assertEquals("\"ab\"", StringUtils.wrapIfMissing("ab", '\"'))
    assertEquals("\"ab\"", StringUtils.wrapIfMissing("\"ab\"", '\"'))
    assertEquals("'ab'", StringUtils.wrapIfMissing("ab", '\''))
    assertEquals("'abcd'", StringUtils.wrapIfMissing("'abcd'", '\''))
    assertEquals("'\"abcd\"'", StringUtils.wrapIfMissing("\"abcd\"", '\''))
    assertEquals("\"'abcd'\"", StringUtils.wrapIfMissing("'abcd'", '\"'))
    assertEquals("/x/", StringUtils.wrapIfMissing("x", '/'))
    assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z", '/'))
    assertEquals("/x/y/z/", StringUtils.wrapIfMissing("/x/y/z", '/'))
    assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z/", '/'))
    assertSame("/", StringUtils.wrapIfMissing("/", '/'))
    assertSame("/x/", StringUtils.wrapIfMissing("/x/", '/'))
  }

  @Test def testWrapIfMissing_StringString(): Unit = {
    assertNull(StringUtils.wrapIfMissing(null, "\u0000"))
    assertNull(StringUtils.wrapIfMissing(null, "1"))
    assertEquals("", StringUtils.wrapIfMissing("", "\u0000"))
    assertEquals("xabx", StringUtils.wrapIfMissing("ab", "x"))
    assertEquals("\"ab\"", StringUtils.wrapIfMissing("ab", "\""))
    assertEquals("\"ab\"", StringUtils.wrapIfMissing("\"ab\"", "\""))
    assertEquals("'ab'", StringUtils.wrapIfMissing("ab", "\'"))
    assertEquals("'abcd'", StringUtils.wrapIfMissing("'abcd'", "\'"))
    assertEquals("'\"abcd\"'", StringUtils.wrapIfMissing("\"abcd\"", "\'"))
    assertEquals("\"'abcd'\"", StringUtils.wrapIfMissing("'abcd'", "\""))
    assertEquals("/x/", StringUtils.wrapIfMissing("x", "/"))
    assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z", "/"))
    assertEquals("/x/y/z/", StringUtils.wrapIfMissing("/x/y/z", "/"))
    assertEquals("/x/y/z/", StringUtils.wrapIfMissing("x/y/z/", "/"))
    assertEquals("/", StringUtils.wrapIfMissing("/", "/"))
    assertEquals("ab/ab", StringUtils.wrapIfMissing("/", "ab"))
    assertSame("ab/ab", StringUtils.wrapIfMissing("ab/ab", "ab"))
    assertSame("//x//", StringUtils.wrapIfMissing("//x//", "//"))
  }

  @Test def testToRootLowerCase(): Unit = {
    assertEquals(null, StringUtils.toRootLowerCase(null))
    assertEquals("a", StringUtils.toRootLowerCase("A"))
    assertEquals("a", StringUtils.toRootLowerCase("a"))
    val TURKISH = Locale.forLanguageTag("tr")
    // Sanity checks:
    assertNotEquals("title", "TITLE".toLowerCase(TURKISH))
    assertEquals("title", "TITLE".toLowerCase(Locale.ROOT))
    assertEquals("title", StringUtils.toRootLowerCase("TITLE"))
    // Make sure we are not using the default Locale:
    val defaultLocale = Locale.getDefault
    try {
      Locale.setDefault(TURKISH)
      assertEquals("title", StringUtils.toRootLowerCase("TITLE"))
    } finally Locale.setDefault(defaultLocale)
  }

  @Test def testToRootUpperCase(): Unit = {
    assertEquals(null, StringUtils.toRootUpperCase(null))
    assertEquals("A", StringUtils.toRootUpperCase("a"))
    assertEquals("A", StringUtils.toRootUpperCase("A"))
    val TURKISH = Locale.forLanguageTag("tr")
    assertNotEquals("TITLE", "title".toUpperCase(TURKISH))
    assertEquals("TITLE", "title".toUpperCase(Locale.ROOT))
    assertEquals("TITLE", StringUtils.toRootUpperCase("title"))
    val defaultLocale = Locale.getDefault
    try {
      Locale.setDefault(TURKISH)
      assertEquals("TITLE", StringUtils.toRootUpperCase("title"))
    } finally Locale.setDefault(defaultLocale)
  }

  @Test def testGeorgianSample(): Unit = {
    val arrayI = Array[Char]( //Latin Small Letter dotless I
      0x0131.toChar, //Greek Capital Letter Theta
      0x03f4.toChar)
    val arrayJ = Array[Char]( //Latin Capital Letter I with dot above
      0x0130.toChar, //Greek Theta Symbol
      0x03d1.toChar)
    for (i <- arrayI) {
      for (j <- arrayJ) {
        val si = String.valueOf(i)
        val sj = String.valueOf(j)
        val res1 = si.equalsIgnoreCase(sj)
        val ci = new StringBuilder(si)
        val cj = new StringBuilder(sj)
        var res2 = StringUtils.startsWithIgnoreCase(ci, cj)
        assertEquals("si : " + si + " sj : " + sj, res1, res2)
        res2 = StringUtils.endsWithIgnoreCase(ci, cj)
        assertEquals("si : " + si + " sj : " + sj, res1, res2)
        res2 = StringUtils.compareIgnoreCase(ci.toString, cj.toString) == 0
        assertEquals("si : " + si + " sj : " + sj, res1, res2)
        res2 = StringUtils.indexOfIgnoreCase(ci.toString, cj.toString) == 0
        assertEquals("si : " + si + " sj : " + sj, res1, res2)
        res2 = StringUtils.lastIndexOfIgnoreCase(ci.toString, cj.toString) == 0
        assertEquals("si : " + si + " sj : " + sj, res1, res2)
      }
    }
  }
}
