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

import org.junit.Assert.{assertEquals, assertNull, assertSame}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.StringUtils} - Substring methods
  */
object StringUtilsSubstringTest {
  private val FOO = "foo"
  private val BAR = "bar"
  private val BAZ = "baz"
  private val FOOBAR = "foobar"
  private val SENTENCE = "foo bar baz"
}

class StringUtilsSubstringTest extends JUnitSuite {
  @Test def testSubstring_StringInt(): Unit = {
    assertNull(StringUtils.substring(null, 0))
    assertEquals("", StringUtils.substring("", 0))
    assertEquals("", StringUtils.substring("", 2))
    assertEquals("", StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 80))
    assertEquals(StringUtilsSubstringTest.BAZ, StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 8))
    assertEquals(StringUtilsSubstringTest.BAZ, StringUtils.substring(StringUtilsSubstringTest.SENTENCE, -3))
    assertEquals(StringUtilsSubstringTest.SENTENCE, StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 0))
    assertEquals("abc", StringUtils.substring("abc", -4))
    assertEquals("abc", StringUtils.substring("abc", -3))
    assertEquals("bc", StringUtils.substring("abc", -2))
    assertEquals("c", StringUtils.substring("abc", -1))
    assertEquals("abc", StringUtils.substring("abc", 0))
    assertEquals("bc", StringUtils.substring("abc", 1))
    assertEquals("c", StringUtils.substring("abc", 2))
    assertEquals("", StringUtils.substring("abc", 3))
    assertEquals("", StringUtils.substring("abc", 4))
  }

  @Test def testSubstring_StringIntInt(): Unit = {
    assertNull(StringUtils.substring(null, 0, 0))
    assertNull(StringUtils.substring(null, 1, 2))
    assertEquals("", StringUtils.substring("", 0, 0))
    assertEquals("", StringUtils.substring("", 1, 2))
    assertEquals("", StringUtils.substring("", -2, -1))
    assertEquals("", StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 8, 6))
    assertEquals(StringUtilsSubstringTest.FOO, StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 0, 3))
    assertEquals("o", StringUtils.substring(StringUtilsSubstringTest.SENTENCE, -9, 3))
    assertEquals(StringUtilsSubstringTest.FOO, StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 0, -8))
    assertEquals("o", StringUtils.substring(StringUtilsSubstringTest.SENTENCE, -9, -8))
    assertEquals(StringUtilsSubstringTest.SENTENCE, StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 0, 80))
    assertEquals("", StringUtils.substring(StringUtilsSubstringTest.SENTENCE, 2, 2))
    assertEquals("b", StringUtils.substring("abc", -2, -1))
  }

  @Test def testLeft_String(): Unit = {
    assertSame(null, StringUtils.left(null, -1))
    assertSame(null, StringUtils.left(null, 0))
    assertSame(null, StringUtils.left(null, 2))
    assertEquals("", StringUtils.left("", -1))
    assertEquals("", StringUtils.left("", 0))
    assertEquals("", StringUtils.left("", 2))
    assertEquals("", StringUtils.left(StringUtilsSubstringTest.FOOBAR, -1))
    assertEquals("", StringUtils.left(StringUtilsSubstringTest.FOOBAR, 0))
    assertEquals(StringUtilsSubstringTest.FOO, StringUtils.left(StringUtilsSubstringTest.FOOBAR, 3))
    assertSame(StringUtilsSubstringTest.FOOBAR, StringUtils.left(StringUtilsSubstringTest.FOOBAR, 80))
  }

  @Test def testRight_String(): Unit = {
    assertSame(null, StringUtils.right(null, -1))
    assertSame(null, StringUtils.right(null, 0))
    assertSame(null, StringUtils.right(null, 2))
    assertEquals("", StringUtils.right("", -1))
    assertEquals("", StringUtils.right("", 0))
    assertEquals("", StringUtils.right("", 2))
    assertEquals("", StringUtils.right(StringUtilsSubstringTest.FOOBAR, -1))
    assertEquals("", StringUtils.right(StringUtilsSubstringTest.FOOBAR, 0))
    assertEquals(StringUtilsSubstringTest.BAR, StringUtils.right(StringUtilsSubstringTest.FOOBAR, 3))
    assertSame(StringUtilsSubstringTest.FOOBAR, StringUtils.right(StringUtilsSubstringTest.FOOBAR, 80))
  }

  @Test def testMid_String(): Unit = {
    assertSame(null, StringUtils.mid(null, -1, 0))
    assertSame(null, StringUtils.mid(null, 0, -1))
    assertSame(null, StringUtils.mid(null, 3, 0))
    assertSame(null, StringUtils.mid(null, 3, 2))
    assertEquals("", StringUtils.mid("", 0, -1))
    assertEquals("", StringUtils.mid("", 0, 0))
    assertEquals("", StringUtils.mid("", 0, 2))
    assertEquals("", StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 3, -1))
    assertEquals("", StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 3, 0))
    assertEquals("b", StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 3, 1))
    assertEquals(StringUtilsSubstringTest.FOO, StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 0, 3))
    assertEquals(StringUtilsSubstringTest.BAR, StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 3, 3))
    assertEquals(StringUtilsSubstringTest.FOOBAR, StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 0, 80))
    assertEquals(StringUtilsSubstringTest.BAR, StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 3, 80))
    assertEquals("", StringUtils.mid(StringUtilsSubstringTest.FOOBAR, 9, 3))
    assertEquals(StringUtilsSubstringTest.FOO, StringUtils.mid(StringUtilsSubstringTest.FOOBAR, -1, 3))
  }

  //-----------------------------------------------------------------------
  @Test def testSubstringBefore_StringString(): Unit = {
    assertEquals("foo", StringUtils.substringBefore("fooXXbarXXbaz", "XX"))
    assertNull(StringUtils.substringBefore(null, null))
    assertNull(StringUtils.substringBefore(null, ""))
    assertNull(StringUtils.substringBefore(null, "XX"))
    assertEquals("", StringUtils.substringBefore("", null))
    assertEquals("", StringUtils.substringBefore("", ""))
    assertEquals("", StringUtils.substringBefore("", "XX"))
    assertEquals("foo", StringUtils.substringBefore("foo", null))
    assertEquals("foo", StringUtils.substringBefore("foo", "b"))
    assertEquals("f", StringUtils.substringBefore("foot", "o"))
    assertEquals("", StringUtils.substringBefore("abc", "a"))
    assertEquals("a", StringUtils.substringBefore("abcba", "b"))
    assertEquals("ab", StringUtils.substringBefore("abc", "c"))
    assertEquals("", StringUtils.substringBefore("abc", ""))
  }

  @Test def testSubstringAfter_StringString(): Unit = {
    assertEquals("barXXbaz", StringUtils.substringAfter("fooXXbarXXbaz", "XX"))
    assertNull(StringUtils.substringAfter(null, null))
    assertNull(StringUtils.substringAfter(null, ""))
    assertNull(StringUtils.substringAfter(null, "XX"))
    assertEquals("", StringUtils.substringAfter("", null))
    assertEquals("", StringUtils.substringAfter("", ""))
    assertEquals("", StringUtils.substringAfter("", "XX"))
    assertEquals("", StringUtils.substringAfter("foo", null))
    assertEquals("ot", StringUtils.substringAfter("foot", "o"))
    assertEquals("bc", StringUtils.substringAfter("abc", "a"))
    assertEquals("cba", StringUtils.substringAfter("abcba", "b"))
    assertEquals("", StringUtils.substringAfter("abc", "c"))
    assertEquals("abc", StringUtils.substringAfter("abc", ""))
    assertEquals("", StringUtils.substringAfter("abc", "d"))
  }

  @Test def testSubstringAfter_StringInt(): Unit = {
    assertNull(StringUtils.substringAfter(null, 0))
    assertNull(StringUtils.substringAfter(null, 'X'))
    assertEquals("", StringUtils.substringAfter("", 0))
    assertEquals("", StringUtils.substringAfter("", 'X'))
    assertEquals("", StringUtils.substringAfter("foo", 0))
    assertEquals("ot", StringUtils.substringAfter("foot", 'o'))
    assertEquals("bc", StringUtils.substringAfter("abc", 'a'))
    assertEquals("cba", StringUtils.substringAfter("abcba", 'b'))
    assertEquals("", StringUtils.substringAfter("abc", 'c'))
    assertEquals("", StringUtils.substringAfter("abc", 'd'))
  }

  @Test def testSubstringBeforeLast_StringString(): Unit = {
    assertEquals("fooXXbar", StringUtils.substringBeforeLast("fooXXbarXXbaz", "XX"))
    assertNull(StringUtils.substringBeforeLast(null, null))
    assertNull(StringUtils.substringBeforeLast(null, ""))
    assertNull(StringUtils.substringBeforeLast(null, "XX"))
    assertEquals("", StringUtils.substringBeforeLast("", null))
    assertEquals("", StringUtils.substringBeforeLast("", ""))
    assertEquals("", StringUtils.substringBeforeLast("", "XX"))
    assertEquals("foo", StringUtils.substringBeforeLast("foo", null))
    assertEquals("foo", StringUtils.substringBeforeLast("foo", "b"))
    assertEquals("fo", StringUtils.substringBeforeLast("foo", "o"))
    assertEquals("abc\r\n", StringUtils.substringBeforeLast("abc\r\n", "d"))
    assertEquals("abc", StringUtils.substringBeforeLast("abcdabc", "d"))
    assertEquals("abcdabc", StringUtils.substringBeforeLast("abcdabcd", "d"))
    assertEquals("a", StringUtils.substringBeforeLast("abc", "b"))
    assertEquals("abc ", StringUtils.substringBeforeLast("abc \n", "\n"))
    assertEquals("a", StringUtils.substringBeforeLast("a", null))
    assertEquals("a", StringUtils.substringBeforeLast("a", ""))
    assertEquals("", StringUtils.substringBeforeLast("a", "a"))
  }

  @Test def testSubstringAfterLast_StringString(): Unit = {
    assertEquals("baz", StringUtils.substringAfterLast("fooXXbarXXbaz", "XX"))
    assertNull(StringUtils.substringAfterLast(null, null))
    assertNull(StringUtils.substringAfterLast(null, ""))
    assertNull(StringUtils.substringAfterLast(null, "XX"))
    assertEquals("", StringUtils.substringAfterLast("", null))
    assertEquals("", StringUtils.substringAfterLast("", ""))
    assertEquals("", StringUtils.substringAfterLast("", "a"))
    assertEquals("", StringUtils.substringAfterLast("foo", null))
    assertEquals("", StringUtils.substringAfterLast("foo", "b"))
    assertEquals("t", StringUtils.substringAfterLast("foot", "o"))
    assertEquals("bc", StringUtils.substringAfterLast("abc", "a"))
    assertEquals("a", StringUtils.substringAfterLast("abcba", "b"))
    assertEquals("", StringUtils.substringAfterLast("abc", "c"))
    assertEquals("", StringUtils.substringAfterLast("", "d"))
    assertEquals("", StringUtils.substringAfterLast("abc", ""))
  }

  @Test def testSubstringAfterLast_StringInt(): Unit = {
    assertNull(StringUtils.substringAfterLast(null, 0))
    assertNull(StringUtils.substringAfterLast(null, 'X'))
    assertEquals("", StringUtils.substringAfterLast("", 0))
    assertEquals("", StringUtils.substringAfterLast("", 'a'))
    assertEquals("", StringUtils.substringAfterLast("foo", 0))
    assertEquals("", StringUtils.substringAfterLast("foo", 'b'))
    assertEquals("t", StringUtils.substringAfterLast("foot", 'o'))
    assertEquals("bc", StringUtils.substringAfterLast("abc", 'a'))
    assertEquals("a", StringUtils.substringAfterLast("abcba", 'b'))
    assertEquals("", StringUtils.substringAfterLast("abc", 'c'))
    assertEquals("", StringUtils.substringAfterLast("", 'd'))
  }

  @Test def testSubstringBetween_StringString(): Unit = {
    assertNull(StringUtils.substringBetween(null, "tag"))
    assertEquals("", StringUtils.substringBetween("", ""))
    assertNull(StringUtils.substringBetween("", "abc"))
    assertEquals("", StringUtils.substringBetween("    ", " "))
    assertNull(StringUtils.substringBetween("abc", null))
    assertEquals("", StringUtils.substringBetween("abc", ""))
    assertNull(StringUtils.substringBetween("abc", "a"))
    assertEquals("bc", StringUtils.substringBetween("abca", "a"))
    assertEquals("bc", StringUtils.substringBetween("abcabca", "a"))
    assertEquals("bar", StringUtils.substringBetween("\nbar\n", "\n"))
  }

  @Test def testSubstringBetween_StringStringString(): Unit = {
    assertNull(StringUtils.substringBetween(null, "", ""))
    assertNull(StringUtils.substringBetween("", null, ""))
    assertNull(StringUtils.substringBetween("", "", null))
    assertEquals("", StringUtils.substringBetween("", "", ""))
    assertEquals("", StringUtils.substringBetween("foo", "", ""))
    assertNull(StringUtils.substringBetween("foo", "", "]"))
    assertNull(StringUtils.substringBetween("foo", "[", "]"))
    assertEquals("", StringUtils.substringBetween("    ", " ", "  "))
    assertEquals("bar", StringUtils.substringBetween("<foo>bar</foo>", "<foo>", "</foo>"))
  }

  /**
    * Tests the substringsBetween method that returns an String Array of substrings.
    */
  @Test def testSubstringsBetween_StringStringString(): Unit = {
    var results: Array[String] = StringUtils.substringsBetween("[one], [two], [three]", "[", "]")
    assertEquals(3, results.length)
    assertEquals("one", results(0))
    assertEquals("two", results(1))
    assertEquals("three", results(2))
    results = StringUtils.substringsBetween("[one], [two], three", "[", "]")
    assertEquals(2, results.length)
    assertEquals("one", results(0))
    assertEquals("two", results(1))
    results = StringUtils.substringsBetween("[one], [two], three]", "[", "]")
    assertEquals(2, results.length)
    assertEquals("one", results(0))
    assertEquals("two", results(1))
    results = StringUtils.substringsBetween("[one], two], three]", "[", "]")
    assertEquals(1, results.length)
    assertEquals("one", results(0))
    results = StringUtils.substringsBetween("one], two], [three]", "[", "]")
    assertEquals(1, results.length)
    assertEquals("three", results(0))
    // 'ab hello ba' will match, but 'ab non ba' won't
    // this is because the 'a' is shared between the two and can't be matched twice
    results = StringUtils.substringsBetween("aabhellobabnonba", "ab", "ba")
    assertEquals(1, results.length)
    assertEquals("hello", results(0))
    results = StringUtils.substringsBetween("one, two, three", "[", "]")
    assertNull(results)
    results = StringUtils.substringsBetween("[one, two, three", "[", "]")
    assertNull(results)
    results = StringUtils.substringsBetween("one, two, three]", "[", "]")
    assertNull(results)
    results = StringUtils.substringsBetween("[one], [two], [three]", "[", null)
    assertNull(results)
    results = StringUtils.substringsBetween("[one], [two], [three]", null, "]")
    assertNull(results)
    results = StringUtils.substringsBetween("[one], [two], [three]", "", "")
    assertNull(results)
    results = StringUtils.substringsBetween(null, "[", "]")
    assertNull(results)
    results = StringUtils.substringsBetween("", "[", "]")
    assertEquals(0, results.length)
  }

  @Test def testCountMatches_String(): Unit = {
    assertEquals(0, StringUtils.countMatches(null, null))
    assertEquals(0, StringUtils.countMatches("blah", null))
    assertEquals(0, StringUtils.countMatches(null, "DD"))
    assertEquals(0, StringUtils.countMatches("x", ""))
    assertEquals(0, StringUtils.countMatches("", ""))
    assertEquals(3, StringUtils.countMatches("one long someone sentence of one", "one"))
    assertEquals(0, StringUtils.countMatches("one long someone sentence of one", "two"))
    assertEquals(4, StringUtils.countMatches("oooooooooooo", "ooo"))
  }

  @Test def testCountMatches_char(): Unit = {
    assertEquals(0, StringUtils.countMatches(null, 'D'))
    assertEquals(5, StringUtils.countMatches("one long someone sentence of one", ' '))
    assertEquals(6, StringUtils.countMatches("one long someone sentence of one", 'o'))
    assertEquals(4, StringUtils.countMatches("oooooooooooo", "ooo"))
  }
}
