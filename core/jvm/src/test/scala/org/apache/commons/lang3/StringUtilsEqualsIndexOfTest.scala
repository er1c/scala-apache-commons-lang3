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
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNot
import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.StringUtils} - Equals/IndexOf methods
  */
object StringUtilsEqualsIndexOfTest {
  private val BAR = "bar"
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
  private val FOO = "foo"
  private val FOOBAR = "foobar"
  private val FOOBAR_SUB_ARRAY = Array[String]("ob", "ba")

  // The purpose of this class is to test StringUtils#equals(CharSequence, CharSequence)
  // with a CharSequence implementation whose equals(Object) override requires that the
  // other object be an instance of CustomCharSequence, even though, as char sequences,
  // `seq` may equal the other object.
  private class CustomCharSequence private[lang3] (val seq: CharSequence) extends CharSequence {
    override def charAt(index: Int) = seq.charAt(index)

    override def length = seq.length

    override def subSequence(start: Int, `end`: Int) =
      new StringUtilsEqualsIndexOfTest.CustomCharSequence(seq.subSequence(start, `end`))

    override def equals(obj: Any): Boolean = {
      if (!obj.isInstanceOf[StringUtilsEqualsIndexOfTest.CustomCharSequence]) return false
      val other = obj.asInstanceOf[StringUtilsEqualsIndexOfTest.CustomCharSequence]
      seq == other.seq
    }

    override def hashCode: Int = seq.hashCode

    override def toString = seq.toString
  }

}

class StringUtilsEqualsIndexOfTest extends JUnitSuite {
  @Test def testCustomCharSequence(): Unit = {
    assertThat(
      new StringUtilsEqualsIndexOfTest.CustomCharSequence(StringUtilsEqualsIndexOfTest.FOO),
      IsNot.not[CharSequence](StringUtilsEqualsIndexOfTest.FOO))
    assertThat(
      StringUtilsEqualsIndexOfTest.FOO,
      IsNot.not[CharSequence](new StringUtilsEqualsIndexOfTest.CustomCharSequence(StringUtilsEqualsIndexOfTest.FOO)))
    assertEquals(
      new StringUtilsEqualsIndexOfTest.CustomCharSequence(StringUtilsEqualsIndexOfTest.FOO),
      new StringUtilsEqualsIndexOfTest.CustomCharSequence(StringUtilsEqualsIndexOfTest.FOO)
    )
  }

  @Test def testEquals(): Unit = {
    val fooCs = new StringBuilder(StringUtilsEqualsIndexOfTest.FOO)
    val barCs = new StringBuilder(StringUtilsEqualsIndexOfTest.BAR)
    val foobarCs = new StringBuilder(StringUtilsEqualsIndexOfTest.FOOBAR)
    assertTrue(StringUtils.equals(null, null))
    assertTrue(StringUtils.equals(fooCs, fooCs))
    assertTrue(StringUtils.equals(fooCs, new StringBuilder(StringUtilsEqualsIndexOfTest.FOO)))
    assertTrue(StringUtils.equals(fooCs, new String(Array[Char]('f', 'o', 'o'))))
    assertTrue(
      StringUtils.equals(fooCs, new StringUtilsEqualsIndexOfTest.CustomCharSequence(StringUtilsEqualsIndexOfTest.FOO)))
    assertTrue(
      StringUtils.equals(new StringUtilsEqualsIndexOfTest.CustomCharSequence(StringUtilsEqualsIndexOfTest.FOO), fooCs))
    assertFalse(StringUtils.equals(fooCs, new String(Array[Char]('f', 'O', 'O'))))
    assertFalse(StringUtils.equals(fooCs, barCs))
    assertFalse(StringUtils.equals(fooCs, null))
    assertFalse(StringUtils.equals(null, fooCs))
    assertFalse(StringUtils.equals(fooCs, foobarCs))
    assertFalse(StringUtils.equals(foobarCs, fooCs))
  }

  @Test def testEqualsOnStrings(): Unit = {
    assertTrue(StringUtils.equals(null, null))
    assertTrue(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOO, new String(Array[Char]('f', 'o', 'o'))))
    assertFalse(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOO, new String(Array[Char]('f', 'O', 'O'))))
    assertFalse(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.BAR))
    assertFalse(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOO, null))
    assertFalse(StringUtils.equals(null, StringUtilsEqualsIndexOfTest.FOO))
    assertFalse(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOOBAR))
    assertFalse(StringUtils.equals(StringUtilsEqualsIndexOfTest.FOOBAR, StringUtilsEqualsIndexOfTest.FOO))
  }

  @Test def testEqualsIgnoreCase(): Unit = {
    assertTrue(StringUtils.equalsIgnoreCase(null, null))
    assertTrue(StringUtils.equalsIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(StringUtils.equalsIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, new String(Array[Char]('f', 'o', 'o'))))
    assertTrue(StringUtils.equalsIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, new String(Array[Char]('f', 'O', 'O'))))
    assertFalse(StringUtils.equalsIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.BAR))
    assertFalse(StringUtils.equalsIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, null))
    assertFalse(StringUtils.equalsIgnoreCase(null, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(StringUtils.equalsIgnoreCase("", ""))
    assertFalse(StringUtils.equalsIgnoreCase("abcd", "abcd "))
  }

  @Test def testEqualsAny(): Unit = {
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, Array[String]()))
    assertTrue(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(
      StringUtils.equalsAny(
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        new String(Array[Char]('f', 'o', 'o'))))
    assertFalse(
      StringUtils.equalsAny(
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        new String(Array[Char]('f', 'O', 'O'))))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.BAR))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.BAR, null))
    assertFalse(StringUtils.equalsAny(null, StringUtilsEqualsIndexOfTest.FOO))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOOBAR))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOOBAR, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(StringUtils.equalsAny(null, null, null))
    assertFalse(
      StringUtils.equalsAny(
        null,
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        StringUtilsEqualsIndexOfTest.FOOBAR))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, null, StringUtilsEqualsIndexOfTest.BAR))
    assertTrue(
      StringUtils.equalsAny(
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        null,
        "",
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR))
    assertFalse(
      StringUtils
        .equalsAny(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO.toUpperCase(Locale.ROOT)))
    assertFalse(StringUtils.equalsAny(null, null.asInstanceOf[Array[CharSequence]]))
    assertTrue(
      StringUtils
        .equalsAny(StringUtilsEqualsIndexOfTest.FOO, new StringUtilsEqualsIndexOfTest.CustomCharSequence("foo")))
    assertTrue(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, new StringBuilder("foo")))
    assertFalse(
      StringUtils
        .equalsAny(StringUtilsEqualsIndexOfTest.FOO, new StringUtilsEqualsIndexOfTest.CustomCharSequence("fOo")))
    assertFalse(StringUtils.equalsAny(StringUtilsEqualsIndexOfTest.FOO, new StringBuilder("fOo")))
  }

  @Test def testEqualsAnyIgnoreCase(): Unit = {
    assertFalse(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO))
    assertFalse(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, Array[String]()))
    assertTrue(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(StringUtils
      .equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO.toUpperCase(Locale.ROOT)))
    assertTrue(
      StringUtils.equalsAnyIgnoreCase(
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.FOO,
        new String(Array[Char]('f', 'o', 'o'))))
    assertTrue(
      StringUtils.equalsAnyIgnoreCase(
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        new String(Array[Char]('f', 'O', 'O'))))
    assertFalse(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.BAR))
    assertFalse(
      StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.BAR, null))
    assertFalse(StringUtils.equalsAnyIgnoreCase(null, StringUtilsEqualsIndexOfTest.FOO))
    assertFalse(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOOBAR))
    assertFalse(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOOBAR, StringUtilsEqualsIndexOfTest.FOO))
    assertTrue(StringUtils.equalsAnyIgnoreCase(null, null, null))
    assertFalse(
      StringUtils.equalsAnyIgnoreCase(
        null,
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        StringUtilsEqualsIndexOfTest.FOOBAR))
    assertFalse(
      StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, null, StringUtilsEqualsIndexOfTest.BAR))
    assertTrue(
      StringUtils.equalsAnyIgnoreCase(
        StringUtilsEqualsIndexOfTest.FOO,
        StringUtilsEqualsIndexOfTest.BAR,
        null,
        "",
        StringUtilsEqualsIndexOfTest.FOO.toUpperCase(Locale.ROOT),
        StringUtilsEqualsIndexOfTest.BAR
      ))
    assertTrue(StringUtils
      .equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, StringUtilsEqualsIndexOfTest.FOO.toUpperCase(Locale.ROOT)))
    assertFalse(StringUtils.equalsAnyIgnoreCase(null, null.asInstanceOf[Array[CharSequence]]))
    assertTrue(
      StringUtils.equalsAnyIgnoreCase(
        StringUtilsEqualsIndexOfTest.FOO,
        new StringUtilsEqualsIndexOfTest.CustomCharSequence("fOo")))
    assertTrue(StringUtils.equalsAnyIgnoreCase(StringUtilsEqualsIndexOfTest.FOO, new StringBuilder("fOo")))
  }

  //-----------------------------------------------------------------------
  @Test def testCompare_StringString(): Unit = {
    assertEquals(0, StringUtils.compare(null, null))
    assertTrue(StringUtils.compare(null, "a") < 0)
    assertTrue(StringUtils.compare("a", null) > 0)
    assertEquals(0, StringUtils.compare("abc", "abc"))
    assertTrue(StringUtils.compare("a", "b") < 0)
    assertTrue(StringUtils.compare("b", "a") > 0)
    assertTrue(StringUtils.compare("a", "B") > 0)
    assertTrue(StringUtils.compare("abc", "abd") < 0)
    assertTrue(StringUtils.compare("ab", "abc") < 0)
    assertTrue(StringUtils.compare("ab", "ab ") < 0)
    assertTrue(StringUtils.compare("abc", "ab ") > 0)
  }

  @Test def testCompare_StringStringBoolean(): Unit = {
    assertEquals(0, StringUtils.compare(null, null, false))
    assertTrue(StringUtils.compare(null, "a", true) < 0)
    assertTrue(StringUtils.compare(null, "a", false) > 0)
    assertTrue(StringUtils.compare("a", null, true) > 0)
    assertTrue(StringUtils.compare("a", null, false) < 0)
    assertEquals(0, StringUtils.compare("abc", "abc", false))
    assertTrue(StringUtils.compare("a", "b", false) < 0)
    assertTrue(StringUtils.compare("b", "a", false) > 0)
    assertTrue(StringUtils.compare("a", "B", false) > 0)
    assertTrue(StringUtils.compare("abc", "abd", false) < 0)
    assertTrue(StringUtils.compare("ab", "abc", false) < 0)
    assertTrue(StringUtils.compare("ab", "ab ", false) < 0)
    assertTrue(StringUtils.compare("abc", "ab ", false) > 0)
  }

  @Test def testCompareIgnoreCase_StringString(): Unit = {
    assertEquals(0, StringUtils.compareIgnoreCase(null, null))
    assertTrue(StringUtils.compareIgnoreCase(null, "a") < 0)
    assertTrue(StringUtils.compareIgnoreCase("a", null) > 0)
    assertEquals(0, StringUtils.compareIgnoreCase("abc", "abc"))
    assertEquals(0, StringUtils.compareIgnoreCase("abc", "ABC"))
    assertTrue(StringUtils.compareIgnoreCase("a", "b") < 0)
    assertTrue(StringUtils.compareIgnoreCase("b", "a") > 0)
    assertTrue(StringUtils.compareIgnoreCase("a", "B") < 0)
    assertTrue(StringUtils.compareIgnoreCase("A", "b") < 0)
    assertTrue(StringUtils.compareIgnoreCase("abc", "ABD") < 0)
    assertTrue(StringUtils.compareIgnoreCase("ab", "ABC") < 0)
    assertTrue(StringUtils.compareIgnoreCase("ab", "AB ") < 0)
    assertTrue(StringUtils.compareIgnoreCase("abc", "AB ") > 0)
  }

  @Test def testCompareIgnoreCase_StringStringBoolean(): Unit = {
    assertEquals(0, StringUtils.compareIgnoreCase(null, null, false))
    assertTrue(StringUtils.compareIgnoreCase(null, "a", true) < 0)
    assertTrue(StringUtils.compareIgnoreCase(null, "a", false) > 0)
    assertTrue(StringUtils.compareIgnoreCase("a", null, true) > 0)
    assertTrue(StringUtils.compareIgnoreCase("a", null, false) < 0)
    assertEquals(0, StringUtils.compareIgnoreCase("abc", "abc", false))
    assertEquals(0, StringUtils.compareIgnoreCase("abc", "ABC", false))
    assertTrue(StringUtils.compareIgnoreCase("a", "b", false) < 0)
    assertTrue(StringUtils.compareIgnoreCase("b", "a", false) > 0)
    assertTrue(StringUtils.compareIgnoreCase("a", "B", false) < 0)
    assertTrue(StringUtils.compareIgnoreCase("A", "b", false) < 0)
    assertTrue(StringUtils.compareIgnoreCase("abc", "ABD", false) < 0)
    assertTrue(StringUtils.compareIgnoreCase("ab", "ABC", false) < 0)
    assertTrue(StringUtils.compareIgnoreCase("ab", "AB ", false) < 0)
    assertTrue(StringUtils.compareIgnoreCase("abc", "AB ", false) > 0)
  }

  @Test def testIndexOf_char(): Unit = {
    assertEquals(-1, StringUtils.indexOf(null, ' '))
    assertEquals(-1, StringUtils.indexOf("", ' '))
    assertEquals(0, StringUtils.indexOf("aabaabaa", 'a'))
    assertEquals(2, StringUtils.indexOf("aabaabaa", 'b'))
    assertEquals(2, StringUtils.indexOf(new StringBuilder("aabaabaa"), 'b'))
  }

  @Test def testIndexOf_charInt(): Unit = {
    assertEquals(-1, StringUtils.indexOf(null, ' ', 0))
    assertEquals(-1, StringUtils.indexOf(null, ' ', -1))
    assertEquals(-1, StringUtils.indexOf("", ' ', 0))
    assertEquals(-1, StringUtils.indexOf("", ' ', -1))
    assertEquals(0, StringUtils.indexOf("aabaabaa", 'a', 0))
    assertEquals(2, StringUtils.indexOf("aabaabaa", 'b', 0))
    assertEquals(5, StringUtils.indexOf("aabaabaa", 'b', 3))
    assertEquals(-1, StringUtils.indexOf("aabaabaa", 'b', 9))
    assertEquals(2, StringUtils.indexOf("aabaabaa", 'b', -1))
    assertEquals(5, StringUtils.indexOf(new StringBuilder("aabaabaa"), 'b', 3))
    //LANG-1300 tests go here
    val CODE_POINT = 0x2070e
    var builder = new java.lang.StringBuilder
    builder.appendCodePoint(CODE_POINT)
    assertEquals(0, StringUtils.indexOf(builder, CODE_POINT, 0))
    assertEquals(0, StringUtils.indexOf(builder.toString, CODE_POINT, 0))
    builder.appendCodePoint(CODE_POINT)
    assertEquals(2, StringUtils.indexOf(builder, CODE_POINT, 1))
    assertEquals(2, StringUtils.indexOf(builder.toString, CODE_POINT, 1))
    // inner branch on the supplementary character block
    val tmp = Array(55361.toChar)
    builder = new java.lang.StringBuilder
    builder.append(tmp)
    assertEquals(-1, StringUtils.indexOf(builder, CODE_POINT, 0))
    assertEquals(-1, StringUtils.indexOf(builder.toString, CODE_POINT, 0))
    builder.appendCodePoint(CODE_POINT)
    assertEquals(1, StringUtils.indexOf(builder, CODE_POINT, 0))
    assertEquals(1, StringUtils.indexOf(builder.toString, CODE_POINT, 0))
    assertEquals(-1, StringUtils.indexOf(builder, CODE_POINT, 2))
    assertEquals(-1, StringUtils.indexOf(builder.toString, CODE_POINT, 2))
  }

  @Test def testIndexOf_String(): Unit = {
    assertEquals(-1, StringUtils.indexOf(null, null))
    assertEquals(-1, StringUtils.indexOf("", null))
    assertEquals(0, StringUtils.indexOf("", ""))
    assertEquals(0, StringUtils.indexOf("aabaabaa", "a"))
    assertEquals(2, StringUtils.indexOf("aabaabaa", "b"))
    assertEquals(1, StringUtils.indexOf("aabaabaa", "ab"))
    assertEquals(0, StringUtils.indexOf("aabaabaa", ""))
    assertEquals(2, StringUtils.indexOf(new StringBuilder("aabaabaa"), "b"))
  }

  @Test def testIndexOf_StringInt(): Unit = {
    assertEquals(-1, StringUtils.indexOf(null, null, 0))
    assertEquals(-1, StringUtils.indexOf(null, null, -1))
    assertEquals(-1, StringUtils.indexOf(null, "", 0))
    assertEquals(-1, StringUtils.indexOf(null, "", -1))
    assertEquals(-1, StringUtils.indexOf("", null, 0))
    assertEquals(-1, StringUtils.indexOf("", null, -1))
    assertEquals(0, StringUtils.indexOf("", "", 0))
    assertEquals(0, StringUtils.indexOf("", "", -1))
    assertEquals(0, StringUtils.indexOf("", "", 9))
    assertEquals(0, StringUtils.indexOf("abc", "", 0))
    assertEquals(0, StringUtils.indexOf("abc", "", -1))
    assertEquals(3, StringUtils.indexOf("abc", "", 9))
    assertEquals(3, StringUtils.indexOf("abc", "", 3))
    assertEquals(0, StringUtils.indexOf("aabaabaa", "a", 0))
    assertEquals(2, StringUtils.indexOf("aabaabaa", "b", 0))
    assertEquals(1, StringUtils.indexOf("aabaabaa", "ab", 0))
    assertEquals(5, StringUtils.indexOf("aabaabaa", "b", 3))
    assertEquals(-1, StringUtils.indexOf("aabaabaa", "b", 9))
    assertEquals(2, StringUtils.indexOf("aabaabaa", "b", -1))
    assertEquals(2, StringUtils.indexOf("aabaabaa", "", 2))
    // Test that startIndex works correctly, i.e. cannot match before startIndex
    assertEquals(7, StringUtils.indexOf("12345678", "8", 5))
    assertEquals(7, StringUtils.indexOf("12345678", "8", 6))
    assertEquals(7, StringUtils.indexOf("12345678", "8", 7)) // 7 is last index
    assertEquals(-1, StringUtils.indexOf("12345678", "8", 8))
    assertEquals(5, StringUtils.indexOf(new StringBuilder("aabaabaa"), "b", 3))
  }

  @Test def testIndexOfAny_StringCharArray(): Unit = {
    assertEquals(-1, StringUtils.indexOfAny(null, null.asInstanceOf[Array[Char]]))
    assertEquals(-1, StringUtils.indexOfAny(null, new Array[Char](0)))
    assertEquals(-1, StringUtils.indexOfAny(null, 'a', 'b'))
    assertEquals(-1, StringUtils.indexOfAny("", null.asInstanceOf[Array[Char]]))
    assertEquals(-1, StringUtils.indexOfAny("", new Array[Char](0)))
    assertEquals(-1, StringUtils.indexOfAny("", 'a', 'b'))
    assertEquals(-1, StringUtils.indexOfAny("zzabyycdxx", null.asInstanceOf[Array[Char]]))
    assertEquals(-1, StringUtils.indexOfAny("zzabyycdxx", new Array[Char](0)))
    assertEquals(0, StringUtils.indexOfAny("zzabyycdxx", 'z', 'a'))
    assertEquals(3, StringUtils.indexOfAny("zzabyycdxx", 'b', 'y'))
    assertEquals(-1, StringUtils.indexOfAny("ab", 'z'))
  }

  /**
    * See http://www.oracle.com/technetwork/articles/javase/supplementary-142654.html
    */
  @Test def testIndexOfAny_StringCharArrayWithSupplementaryChars(): Unit = {
    assertEquals(
      0,
      StringUtils.indexOfAny(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20000.toCharArray)
    )
    assertEquals(
      2,
      StringUtils.indexOfAny(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20001.toCharArray)
    )
    assertEquals(
      0,
      StringUtils
        .indexOfAny(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20000.toCharArray))
    assertEquals(
      -1,
      StringUtils
        .indexOfAny(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20001.toCharArray))
  }

  @Test def testIndexOfAny_StringString(): Unit = {
    assertEquals(-1, StringUtils.indexOfAny(null, null.asInstanceOf[String]))
    assertEquals(-1, StringUtils.indexOfAny(null, ""))
    assertEquals(-1, StringUtils.indexOfAny(null, "ab"))
    assertEquals(-1, StringUtils.indexOfAny("", null.asInstanceOf[String]))
    assertEquals(-1, StringUtils.indexOfAny("", ""))
    assertEquals(-1, StringUtils.indexOfAny("", "ab"))
    assertEquals(-1, StringUtils.indexOfAny("zzabyycdxx", null.asInstanceOf[String]))
    assertEquals(-1, StringUtils.indexOfAny("zzabyycdxx", ""))
    assertEquals(0, StringUtils.indexOfAny("zzabyycdxx", "za"))
    assertEquals(3, StringUtils.indexOfAny("zzabyycdxx", "by"))
    assertEquals(-1, StringUtils.indexOfAny("ab", "z"))
  }

  @Test def testIndexOfAny_StringStringArray(): Unit = {
    assertEquals(-1, StringUtils.indexOfAny(null, null.asInstanceOf[Array[String]]))
    assertEquals(-1, StringUtils.indexOfAny(null, StringUtilsEqualsIndexOfTest.FOOBAR_SUB_ARRAY))
    assertEquals(-1, StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, null.asInstanceOf[Array[String]]))
    assertEquals(
      2,
      StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, StringUtilsEqualsIndexOfTest.FOOBAR_SUB_ARRAY))
    assertEquals(-1, StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, new Array[String](0)))
    assertEquals(-1, StringUtils.indexOfAny(null, new Array[String](0)))
    assertEquals(-1, StringUtils.indexOfAny("", new Array[String](0)))
    assertEquals(-1, StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, Array[String]("llll")))
    assertEquals(0, StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, Array[String]("")))
    assertEquals(0, StringUtils.indexOfAny("", Array[String]("")))
    assertEquals(-1, StringUtils.indexOfAny("", Array[String]("a")))
    assertEquals(-1, StringUtils.indexOfAny("", Array[String](null)))
    assertEquals(-1, StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, Array[String](null)))
    assertEquals(-1, StringUtils.indexOfAny(null, Array[String](null)))
  }

  @Test def testIndexOfAny_StringStringWithSupplementaryChars(): Unit = {
    assertEquals(
      0,
      StringUtils.indexOfAny(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20000))
    assertEquals(
      2,
      StringUtils.indexOfAny(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20001))
    assertEquals(
      0,
      StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20000))
    assertEquals(
      -1,
      StringUtils.indexOfAny(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20001))
  }

  @Test def testIndexOfAnyBut_StringCharArray(): Unit = {
    assertEquals(-1, StringUtils.indexOfAnyBut(null, null.asInstanceOf[Array[Char]]))
    assertEquals(-1, StringUtils.indexOfAnyBut(null))
    assertEquals(-1, StringUtils.indexOfAnyBut(null, 'a', 'b'))
    assertEquals(-1, StringUtils.indexOfAnyBut("", null.asInstanceOf[Array[Char]]))
    assertEquals(-1, StringUtils.indexOfAnyBut(""))
    assertEquals(-1, StringUtils.indexOfAnyBut("", 'a', 'b'))
    assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx", null.asInstanceOf[Array[Char]]))
    assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx"))
    assertEquals(3, StringUtils.indexOfAnyBut("zzabyycdxx", 'z', 'a'))
    assertEquals(0, StringUtils.indexOfAnyBut("zzabyycdxx", 'b', 'y'))
    assertEquals(-1, StringUtils.indexOfAnyBut("aba", 'a', 'b'))
    assertEquals(0, StringUtils.indexOfAnyBut("aba", 'z'))
  }

  @Test def testIndexOfAnyBut_StringCharArrayWithSupplementaryChars(): Unit = {
    assertEquals(
      2,
      StringUtils.indexOfAnyBut(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20000.toCharArray)
    )
    assertEquals(
      0,
      StringUtils.indexOfAnyBut(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20001.toCharArray)
    )
    assertEquals(
      -1,
      StringUtils
        .indexOfAnyBut(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20000.toCharArray))
    assertEquals(
      0,
      StringUtils
        .indexOfAnyBut(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20001.toCharArray))
  }

  @Test def testIndexOfAnyBut_StringString(): Unit = {
    assertEquals(-1, StringUtils.indexOfAnyBut(null, null.asInstanceOf[String]))
    assertEquals(-1, StringUtils.indexOfAnyBut(null, ""))
    assertEquals(-1, StringUtils.indexOfAnyBut(null, "ab"))
    assertEquals(-1, StringUtils.indexOfAnyBut("", null.asInstanceOf[String]))
    assertEquals(-1, StringUtils.indexOfAnyBut("", ""))
    assertEquals(-1, StringUtils.indexOfAnyBut("", "ab"))
    assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx", null.asInstanceOf[String]))
    assertEquals(-1, StringUtils.indexOfAnyBut("zzabyycdxx", ""))
    assertEquals(3, StringUtils.indexOfAnyBut("zzabyycdxx", "za"))
    assertEquals(0, StringUtils.indexOfAnyBut("zzabyycdxx", "by"))
    assertEquals(0, StringUtils.indexOfAnyBut("ab", "z"))
  }

  @Test def testIndexOfAnyBut_StringStringWithSupplementaryChars(): Unit = {
    assertEquals(
      2,
      StringUtils.indexOfAnyBut(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20000))
    assertEquals(
      0,
      StringUtils.indexOfAnyBut(
        StringUtilsEqualsIndexOfTest.CharU20000 + StringUtilsEqualsIndexOfTest.CharU20001,
        StringUtilsEqualsIndexOfTest.CharU20001))
    assertEquals(
      -1,
      StringUtils.indexOfAnyBut(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20000))
    assertEquals(
      0,
      StringUtils.indexOfAnyBut(StringUtilsEqualsIndexOfTest.CharU20000, StringUtilsEqualsIndexOfTest.CharU20001))
  }

  @Test def testIndexOfIgnoreCase_String(): Unit = {
    assertEquals(-1, StringUtils.indexOfIgnoreCase(null, null))
    assertEquals(-1, StringUtils.indexOfIgnoreCase(null, ""))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("", null))
    assertEquals(0, StringUtils.indexOfIgnoreCase("", ""))
    assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", "a"))
    assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", "A"))
    assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "b"))
    assertEquals(2, StringUtils.indexOfIgnoreCase("aabaabaa", "B"))
    assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "ab"))
    assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB"))
    assertEquals(0, StringUtils.indexOfIgnoreCase("aabaabaa", ""))
  }

  @Test def testIndexOfIgnoreCase_StringInt(): Unit = {
    assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", -1))
    assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0))
    assertEquals(1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 1))
    assertEquals(4, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 2))
    assertEquals(4, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 3))
    assertEquals(4, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 4))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 5))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 6))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 7))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 8))
    assertEquals(1, StringUtils.indexOfIgnoreCase("aab", "AB", 1))
    assertEquals(5, StringUtils.indexOfIgnoreCase("aabaabaa", "", 5))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("ab", "AAB", 0))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("aab", "AAB", 1))
    assertEquals(-1, StringUtils.indexOfIgnoreCase("abc", "", 9))
  }

  @Test def testLastIndexOf_char(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOf(null, ' '))
    assertEquals(-1, StringUtils.lastIndexOf("", ' '))
    assertEquals(7, StringUtils.lastIndexOf("aabaabaa", 'a'))
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", 'b'))
    assertEquals(5, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), 'b'))
  }

  @Test def testLastIndexOf_charInt(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOf(null, ' ', 0))
    assertEquals(-1, StringUtils.lastIndexOf(null, ' ', -1))
    assertEquals(-1, StringUtils.lastIndexOf("", ' ', 0))
    assertEquals(-1, StringUtils.lastIndexOf("", ' ', -1))
    assertEquals(7, StringUtils.lastIndexOf("aabaabaa", 'a', 8))
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", 'b', 8))
    assertEquals(2, StringUtils.lastIndexOf("aabaabaa", 'b', 3))
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", 'b', 9))
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", 'b', -1))
    assertEquals(0, StringUtils.lastIndexOf("aabaabaa", 'a', 0))
    assertEquals(2, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), 'b', 2))
    //LANG-1300 addition test
    val CODE_POINT = 0x2070e
    var builder = new java.lang.StringBuilder
    builder.appendCodePoint(CODE_POINT)
    assertEquals(0, StringUtils.lastIndexOf(builder, CODE_POINT, 0))
    builder.appendCodePoint(CODE_POINT)
    assertEquals(0, StringUtils.lastIndexOf(builder, CODE_POINT, 0))
    assertEquals(0, StringUtils.lastIndexOf(builder, CODE_POINT, 1))
    assertEquals(2, StringUtils.lastIndexOf(builder, CODE_POINT, 2))
    builder.append("aaaaa")
    assertEquals(2, StringUtils.lastIndexOf(builder, CODE_POINT, 4))
    val tmp = Array(55361.toChar)
    builder = new java.lang.StringBuilder
    builder.append(tmp)
    assertEquals(-1, StringUtils.lastIndexOf(builder, CODE_POINT, 0))
    builder.appendCodePoint(CODE_POINT)
    assertEquals(-1, StringUtils.lastIndexOf(builder, CODE_POINT, 0))
    assertEquals(1, StringUtils.lastIndexOf(builder, CODE_POINT, 1))
    assertEquals(-1, StringUtils.lastIndexOf(builder.toString, CODE_POINT, 0))
    assertEquals(1, StringUtils.lastIndexOf(builder.toString, CODE_POINT, 1))
  }

  @Test def testLastIndexOf_String(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOf(null, null))
    assertEquals(-1, StringUtils.lastIndexOf("", null))
    assertEquals(-1, StringUtils.lastIndexOf("", "a"))
    assertEquals(0, StringUtils.lastIndexOf("", ""))
    assertEquals(8, StringUtils.lastIndexOf("aabaabaa", ""))
    assertEquals(7, StringUtils.lastIndexOf("aabaabaa", "a"))
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b"))
    assertEquals(4, StringUtils.lastIndexOf("aabaabaa", "ab"))
    assertEquals(4, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), "ab"))
  }

  @Test def testLastIndexOf_StringInt(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOf(null, null, 0))
    assertEquals(-1, StringUtils.lastIndexOf(null, null, -1))
    assertEquals(-1, StringUtils.lastIndexOf(null, "", 0))
    assertEquals(-1, StringUtils.lastIndexOf(null, "", -1))
    assertEquals(-1, StringUtils.lastIndexOf("", null, 0))
    assertEquals(-1, StringUtils.lastIndexOf("", null, -1))
    assertEquals(0, StringUtils.lastIndexOf("", "", 0))
    assertEquals(-1, StringUtils.lastIndexOf("", "", -1))
    assertEquals(0, StringUtils.lastIndexOf("", "", 9))
    assertEquals(0, StringUtils.lastIndexOf("abc", "", 0))
    assertEquals(-1, StringUtils.lastIndexOf("abc", "", -1))
    assertEquals(3, StringUtils.lastIndexOf("abc", "", 9))
    assertEquals(7, StringUtils.lastIndexOf("aabaabaa", "a", 8))
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b", 8))
    assertEquals(4, StringUtils.lastIndexOf("aabaabaa", "ab", 8))
    assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "b", 3))
    assertEquals(5, StringUtils.lastIndexOf("aabaabaa", "b", 9))
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", -1))
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", 0))
    assertEquals(0, StringUtils.lastIndexOf("aabaabaa", "a", 0))
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "a", -1))
    // Test that fromIndex works correctly, i.e. cannot match after fromIndex
    assertEquals(7, StringUtils.lastIndexOf("12345678", "8", 9))
    assertEquals(7, StringUtils.lastIndexOf("12345678", "8", 8))
    assertEquals(7, StringUtils.lastIndexOf("12345678", "8", 7))
    assertEquals(-1, StringUtils.lastIndexOf("12345678", "8", 6))
    assertEquals(-1, StringUtils.lastIndexOf("aabaabaa", "b", 1))
    assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "b", 2))
    assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "ba", 2))
    assertEquals(2, StringUtils.lastIndexOf("aabaabaa", "ba", 3))
    assertEquals(2, StringUtils.lastIndexOf(new StringBuilder("aabaabaa"), "b", 3))
  }

  @Test def testLastIndexOfAny_StringStringArray(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOfAny(null, null.asInstanceOf[CharSequence])) // test both types of ...
    assertEquals(-1, StringUtils.lastIndexOfAny(null, null.asInstanceOf[Array[CharSequence]])) // ... varargs invocation
    assertEquals(-1, StringUtils.lastIndexOfAny(null)) // Missing varag
    assertEquals(-1, StringUtils.lastIndexOfAny(null, StringUtilsEqualsIndexOfTest.FOOBAR_SUB_ARRAY))
    assertEquals(-1, StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, null.asInstanceOf[CharSequence]))
    assertEquals(
      -1,
      StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, null.asInstanceOf[Array[CharSequence]]))
    assertEquals(-1, StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR)) // Missing vararg
    assertEquals(
      3,
      StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, StringUtilsEqualsIndexOfTest.FOOBAR_SUB_ARRAY))
    assertEquals(-1, StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, new Array[String](0)))
    assertEquals(-1, StringUtils.lastIndexOfAny(null, new Array[String](0)))
    assertEquals(-1, StringUtils.lastIndexOfAny("", new Array[String](0)))
    assertEquals(-1, StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, Array[String]("llll")))
    assertEquals(6, StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, Array[String]("")))
    assertEquals(0, StringUtils.lastIndexOfAny("", Array[String]("")))
    assertEquals(-1, StringUtils.lastIndexOfAny("", Array[String]("a")))
    assertEquals(-1, StringUtils.lastIndexOfAny("", Array[String](null)))
    assertEquals(-1, StringUtils.lastIndexOfAny(StringUtilsEqualsIndexOfTest.FOOBAR, Array[String](null)))
    assertEquals(-1, StringUtils.lastIndexOfAny(null, Array[String](null)))
  }

  @Test def testLastIndexOfIgnoreCase_String(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, null))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", null))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, ""))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", "a"))
    assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", ""))
    assertEquals(8, StringUtils.lastIndexOfIgnoreCase("aabaabaa", ""))
    assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "a"))
    assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A"))
    assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "b"))
    assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B"))
    assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "ab"))
    assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB"))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("ab", "AAB"))
    assertEquals(0, StringUtils.lastIndexOfIgnoreCase("aab", "AAB"))
  }

  @Test def testLastIndexOfIgnoreCase_StringInt(): Unit = {
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, null, 0))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, null, -1))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, "", 0))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase(null, "", -1))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", null, 0))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", null, -1))
    assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", "", 0))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("", "", -1))
    assertEquals(0, StringUtils.lastIndexOfIgnoreCase("", "", 9))
    assertEquals(0, StringUtils.lastIndexOfIgnoreCase("abc", "", 0))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("abc", "", -1))
    assertEquals(3, StringUtils.lastIndexOfIgnoreCase("abc", "", 9))
    assertEquals(7, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8))
    assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8))
    assertEquals(4, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8))
    assertEquals(2, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 3))
    assertEquals(5, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1))
    assertEquals(-1, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0))
    assertEquals(0, StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0))
    assertEquals(1, StringUtils.lastIndexOfIgnoreCase("aab", "AB", 1))
  }

  @Test def testLastOrdinalIndexOf(): Unit = {
    assertEquals(-1, StringUtils.lastOrdinalIndexOf(null, "*", 42))
    assertEquals(-1, StringUtils.lastOrdinalIndexOf("*", null, 42))
    assertEquals(0, StringUtils.lastOrdinalIndexOf("", "", 42))
    assertEquals(7, StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1))
    assertEquals(6, StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2))
    assertEquals(5, StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1))
    assertEquals(2, StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2))
    assertEquals(4, StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1))
    assertEquals(1, StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2))
    assertEquals(8, StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1))
    assertEquals(8, StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2))
  }

  @Test def testOrdinalIndexOf(): Unit = {
    assertEquals(-1, StringUtils.ordinalIndexOf(null, null, Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("", null, Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("", "", Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "", Integer.MIN_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf(null, null, -1))
    assertEquals(-1, StringUtils.ordinalIndexOf("", null, -1))
    assertEquals(-1, StringUtils.ordinalIndexOf("", "", -1))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", -1))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", -1))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", -1))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "", -1))
    assertEquals(-1, StringUtils.ordinalIndexOf(null, null, 0))
    assertEquals(-1, StringUtils.ordinalIndexOf("", null, 0))
    assertEquals(-1, StringUtils.ordinalIndexOf("", "", 0))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", 0))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", 0))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", 0))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "", 0))
    assertEquals(-1, StringUtils.ordinalIndexOf(null, null, 1))
    assertEquals(-1, StringUtils.ordinalIndexOf("", null, 1))
    assertEquals(0, StringUtils.ordinalIndexOf("", "", 1))
    assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "a", 1))
    assertEquals(2, StringUtils.ordinalIndexOf("aabaabaa", "b", 1))
    assertEquals(1, StringUtils.ordinalIndexOf("aabaabaa", "ab", 1))
    assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "", 1))
    assertEquals(-1, StringUtils.ordinalIndexOf(null, null, 2))
    assertEquals(-1, StringUtils.ordinalIndexOf("", null, 2))
    assertEquals(0, StringUtils.ordinalIndexOf("", "", 2))
    assertEquals(1, StringUtils.ordinalIndexOf("aabaabaa", "a", 2))
    assertEquals(5, StringUtils.ordinalIndexOf("aabaabaa", "b", 2))
    assertEquals(4, StringUtils.ordinalIndexOf("aabaabaa", "ab", 2))
    assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "", 2))
    assertEquals(-1, StringUtils.ordinalIndexOf(null, null, Integer.MAX_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("", null, Integer.MAX_VALUE))
    assertEquals(0, StringUtils.ordinalIndexOf("", "", Integer.MAX_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "a", Integer.MAX_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "b", Integer.MAX_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aabaabaa", "ab", Integer.MAX_VALUE))
    assertEquals(0, StringUtils.ordinalIndexOf("aabaabaa", "", Integer.MAX_VALUE))
    assertEquals(-1, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 0))
    assertEquals(0, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 1))
    assertEquals(1, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 2))
    assertEquals(2, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 3))
    assertEquals(3, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 4))
    assertEquals(4, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 5))
    assertEquals(5, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 6))
    assertEquals(6, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 7))
    assertEquals(7, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 8))
    assertEquals(8, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 9))
    assertEquals(-1, StringUtils.ordinalIndexOf("aaaaaaaaa", "a", 10))
    // match at each possible position
    assertEquals(0, StringUtils.ordinalIndexOf("aaaaaa", "aa", 1))
    assertEquals(1, StringUtils.ordinalIndexOf("aaaaaa", "aa", 2))
    assertEquals(2, StringUtils.ordinalIndexOf("aaaaaa", "aa", 3))
    assertEquals(3, StringUtils.ordinalIndexOf("aaaaaa", "aa", 4))
    assertEquals(4, StringUtils.ordinalIndexOf("aaaaaa", "aa", 5))
    assertEquals(-1, StringUtils.ordinalIndexOf("aaaaaa", "aa", 6))
    assertEquals(0, StringUtils.ordinalIndexOf("ababab", "aba", 1))
    assertEquals(2, StringUtils.ordinalIndexOf("ababab", "aba", 2))
    assertEquals(-1, StringUtils.ordinalIndexOf("ababab", "aba", 3))
    assertEquals(0, StringUtils.ordinalIndexOf("abababab", "abab", 1))
    assertEquals(2, StringUtils.ordinalIndexOf("abababab", "abab", 2))
    assertEquals(4, StringUtils.ordinalIndexOf("abababab", "abab", 3))
    assertEquals(-1, StringUtils.ordinalIndexOf("abababab", "abab", 4))
  }

  @Test def testLANG1193() = assertEquals(0, StringUtils.ordinalIndexOf("abc", "ab", 1))

  @Test // Non-overlapping test
  def testLANG1241_1(): Unit = { //                                          0  3  6
    assertEquals(0, StringUtils.ordinalIndexOf("abaabaab", "ab", 1))
    assertEquals(3, StringUtils.ordinalIndexOf("abaabaab", "ab", 2))
    assertEquals(6, StringUtils.ordinalIndexOf("abaabaab", "ab", 3))
  }

  @Test // Overlapping matching test
  def testLANG1241_2(): Unit = { //                                          0 2 4
    assertEquals(0, StringUtils.ordinalIndexOf("abababa", "aba", 1))
    assertEquals(2, StringUtils.ordinalIndexOf("abababa", "aba", 2))
    assertEquals(4, StringUtils.ordinalIndexOf("abababa", "aba", 3))
    assertEquals(0, StringUtils.ordinalIndexOf("abababab", "abab", 1))
    assertEquals(2, StringUtils.ordinalIndexOf("abababab", "abab", 2))
    assertEquals(4, StringUtils.ordinalIndexOf("abababab", "abab", 3))
  }
}
