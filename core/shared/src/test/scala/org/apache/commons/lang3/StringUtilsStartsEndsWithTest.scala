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

import org.junit.Assert.{assertFalse, assertTrue}
import org.junit.Test

/**
  * Unit tests {@link org.apache.commons.lang3.StringUtils} - StartsWith/EndsWith methods
  */
object StringUtilsStartsEndsWithTest {
  private val foo = "foo"
  private val bar = "bar"
  private val foobar = "foobar"
  private val FOO = "FOO"
  private val BAR = "BAR"
  private val FOOBAR = "FOOBAR"
}

class StringUtilsStartsEndsWithTest {
  /**
    * Test StringUtils.startsWith()
    */
  @Test def testStartsWith(): Unit = {
    assertTrue("startsWith(null, null)", StringUtils.startsWith(null, null))
    assertFalse("startsWith(FOOBAR, null)", StringUtils.startsWith(StringUtilsStartsEndsWithTest.FOOBAR, null))
    assertFalse("startsWith(null, FOO)", StringUtils.startsWith(null, StringUtilsStartsEndsWithTest.FOO))
    assertTrue("startsWith(FOOBAR, \"\")", StringUtils.startsWith(StringUtilsStartsEndsWithTest.FOOBAR, ""))
    assertTrue(
      "startsWith(foobar, foo)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.foo))
    assertTrue(
      "startsWith(FOOBAR, FOO)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.FOO))
    assertFalse(
      "startsWith(foobar, FOO)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.FOO))
    assertFalse(
      "startsWith(FOOBAR, foo)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.foo))
    assertFalse(
      "startsWith(foo, foobar)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.foo, StringUtilsStartsEndsWithTest.foobar))
    assertFalse(
      "startsWith(foo, foobar)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.bar, StringUtilsStartsEndsWithTest.foobar))
    assertFalse(
      "startsWith(foobar, bar)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.bar))
    assertFalse(
      "startsWith(FOOBAR, BAR)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.BAR))
    assertFalse(
      "startsWith(foobar, BAR)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.BAR))
    assertFalse(
      "startsWith(FOOBAR, bar)",
      StringUtils.startsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.bar))
  }

  /**
    * Test StringUtils.testStartsWithIgnoreCase()
    */
  @Test def testStartsWithIgnoreCase(): Unit = {
    assertTrue("startsWithIgnoreCase(null, null)", StringUtils.startsWithIgnoreCase(null, null))
    assertFalse(
      "startsWithIgnoreCase(FOOBAR, null)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, null))
    assertFalse(
      "startsWithIgnoreCase(null, FOO)",
      StringUtils.startsWithIgnoreCase(null, StringUtilsStartsEndsWithTest.FOO))
    assertTrue(
      "startsWithIgnoreCase(FOOBAR, \"\")",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, ""))
    assertTrue(
      "startsWithIgnoreCase(foobar, foo)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.foo))
    assertTrue(
      "startsWithIgnoreCase(FOOBAR, FOO)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.FOO))
    assertTrue(
      "startsWithIgnoreCase(foobar, FOO)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.FOO))
    assertTrue(
      "startsWithIgnoreCase(FOOBAR, foo)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.foo))
    assertFalse(
      "startsWithIgnoreCase(foo, foobar)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.foo, StringUtilsStartsEndsWithTest.foobar))
    assertFalse(
      "startsWithIgnoreCase(foo, foobar)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.bar, StringUtilsStartsEndsWithTest.foobar))
    assertFalse(
      "startsWithIgnoreCase(foobar, bar)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.bar))
    assertFalse(
      "startsWithIgnoreCase(FOOBAR, BAR)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.BAR))
    assertFalse(
      "startsWithIgnoreCase(foobar, BAR)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.BAR))
    assertFalse(
      "startsWithIgnoreCase(FOOBAR, bar)",
      StringUtils.startsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.bar))
  }

  @Test def testStartsWithAny(): Unit = {
    assertFalse(StringUtils.startsWithAny(null, null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.startsWithAny(null, "abc"))
    assertFalse(StringUtils.startsWithAny("abcxyz", null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.startsWithAny("abcxyz"))
    assertTrue(StringUtils.startsWithAny("abcxyz", "abc"))
    assertTrue(StringUtils.startsWithAny("abcxyz", null, "xyz", "abc"))
    assertFalse(StringUtils.startsWithAny("abcxyz", null, "xyz", "abcd"))
    assertTrue(StringUtils.startsWithAny("abcxyz", ""))
    assertFalse(StringUtils.startsWithAny("abcxyz", null, "xyz", "ABCX"))
    assertFalse(StringUtils.startsWithAny("ABCXYZ", null, "xyz", "abc"))
    assertTrue(
      "StringUtils.startsWithAny(abcxyz, StringBuilder(xyz), StringBuffer(abc))",
      StringUtils.startsWithAny("abcxyz", new StringBuilder("xyz"), new StringBuffer("abc"))
    )
    assertTrue(
      "StringUtils.startsWithAny(StringBuffer(abcxyz), StringBuilder(xyz), StringBuffer(abc))",
      StringUtils.startsWithAny(new StringBuffer("abcxyz"), new StringBuilder("xyz"), new StringBuffer("abc"))
    )
  }

  /**
    * Test StringUtils.endsWith()
    */
  @Test def testEndsWith(): Unit = {
    assertTrue("endsWith(null, null)", StringUtils.endsWith(null, null))
    assertFalse("endsWith(FOOBAR, null)", StringUtils.endsWith(StringUtilsStartsEndsWithTest.FOOBAR, null))
    assertFalse("endsWith(null, FOO)", StringUtils.endsWith(null, StringUtilsStartsEndsWithTest.FOO))
    assertTrue("endsWith(FOOBAR, \"\")", StringUtils.endsWith(StringUtilsStartsEndsWithTest.FOOBAR, ""))
    assertFalse(
      "endsWith(foobar, foo)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.foo))
    assertFalse(
      "endsWith(FOOBAR, FOO)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.FOO))
    assertFalse(
      "endsWith(foobar, FOO)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.FOO))
    assertFalse(
      "endsWith(FOOBAR, foo)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.foo))
    assertFalse(
      "endsWith(foo, foobar)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.foo, StringUtilsStartsEndsWithTest.foobar))
    assertFalse(
      "endsWith(foo, foobar)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.bar, StringUtilsStartsEndsWithTest.foobar))
    assertTrue(
      "endsWith(foobar, bar)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.bar))
    assertTrue(
      "endsWith(FOOBAR, BAR)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.BAR))
    assertFalse(
      "endsWith(foobar, BAR)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.BAR))
    assertFalse(
      "endsWith(FOOBAR, bar)",
      StringUtils.endsWith(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.bar))
    // "alpha, beta, gamma, delta".endsWith("delta")
    assertTrue("endsWith(\u03B1\u03B2\u03B3\u03B4, \u03B4)", StringUtils.endsWith("\u03B1\u03B2\u03B3\u03B4", "\u03B4"))
    // "alpha, beta, gamma, delta".endsWith("gamma, DELTA")
    assertFalse(
      "endsWith(\u03B1\u03B2\u03B3\u03B4, \u03B3\u0394)",
      StringUtils.endsWith("\u03B1\u03B2\u03B3\u03B4", "\u03B3\u0394"))
  }

  /**
    * Test StringUtils.endsWithIgnoreCase()
    */
  @Test def testEndsWithIgnoreCase(): Unit = {
    assertTrue("endsWithIgnoreCase(null, null)", StringUtils.endsWithIgnoreCase(null, null))
    assertFalse(
      "endsWithIgnoreCase(FOOBAR, null)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, null))
    assertFalse(
      "endsWithIgnoreCase(null, FOO)",
      StringUtils.endsWithIgnoreCase(null, StringUtilsStartsEndsWithTest.FOO))
    assertTrue(
      "endsWithIgnoreCase(FOOBAR, \"\")",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, ""))
    assertFalse(
      "endsWithIgnoreCase(foobar, foo)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.foo))
    assertFalse(
      "endsWithIgnoreCase(FOOBAR, FOO)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.FOO))
    assertFalse(
      "endsWithIgnoreCase(foobar, FOO)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.FOO))
    assertFalse(
      "endsWithIgnoreCase(FOOBAR, foo)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.foo))
    assertFalse(
      "endsWithIgnoreCase(foo, foobar)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.foo, StringUtilsStartsEndsWithTest.foobar))
    assertFalse(
      "endsWithIgnoreCase(foo, foobar)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.bar, StringUtilsStartsEndsWithTest.foobar))
    assertTrue(
      "endsWithIgnoreCase(foobar, bar)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.bar))
    assertTrue(
      "endsWithIgnoreCase(FOOBAR, BAR)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.BAR))
    assertTrue(
      "endsWithIgnoreCase(foobar, BAR)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.foobar, StringUtilsStartsEndsWithTest.BAR))
    assertTrue(
      "endsWithIgnoreCase(FOOBAR, bar)",
      StringUtils.endsWithIgnoreCase(StringUtilsStartsEndsWithTest.FOOBAR, StringUtilsStartsEndsWithTest.bar))
    // javadoc
    assertTrue(StringUtils.endsWithIgnoreCase("abcdef", "def"))
    assertTrue(StringUtils.endsWithIgnoreCase("ABCDEF", "def"))
    assertFalse(StringUtils.endsWithIgnoreCase("ABCDEF", "cde"))
    // "alpha, beta, gamma, delta".endsWith("DELTA")
    assertTrue(
      "endsWith(\u03B1\u03B2\u03B3\u03B4, \u0394)",
      StringUtils.endsWithIgnoreCase("\u03B1\u03B2\u03B3\u03B4", "\u0394"))
    // "alpha, beta, gamma, delta".endsWith("GAMMA")
    assertFalse(
      "endsWith(\u03B1\u03B2\u03B3\u03B4, \u0393)",
      StringUtils.endsWithIgnoreCase("\u03B1\u03B2\u03B3\u03B4", "\u0393"))
  }

  @Test def testEndsWithAny(): Unit = {
    assertFalse("StringUtils.endsWithAny(null, null)", StringUtils.endsWithAny(null, null.asInstanceOf[String]))
    assertFalse("StringUtils.endsWithAny(null, new String[] {abc})", StringUtils.endsWithAny(null, "abc"))
    assertFalse("StringUtils.endsWithAny(abcxyz, null)", StringUtils.endsWithAny("abcxyz", null.asInstanceOf[String]))
    assertTrue("StringUtils.endsWithAny(abcxyz, new String[] {\"\"})", StringUtils.endsWithAny("abcxyz", ""))
    assertTrue("StringUtils.endsWithAny(abcxyz, new String[] {xyz})", StringUtils.endsWithAny("abcxyz", "xyz"))
    assertTrue(
      "StringUtils.endsWithAny(abcxyz, new String[] {null, xyz, abc})",
      StringUtils.endsWithAny("abcxyz", null, "xyz", "abc"))
    assertFalse(
      "StringUtils.endsWithAny(defg, new String[] {null, xyz, abc})",
      StringUtils.endsWithAny("defg", null, "xyz", "abc"))
    assertTrue(StringUtils.endsWithAny("abcXYZ", "def", "XYZ"))
    assertFalse(StringUtils.endsWithAny("abcXYZ", "def", "xyz"))
    assertTrue(StringUtils.endsWithAny("abcXYZ", "def", "YZ"))
    /*
     * Type null of the last argument to method endsWithAny(CharSequence, CharSequence...)
     * doesn't exactly match the vararg parameter type.
     * Cast to CharSequence[] to confirm the non-varargs invocation,
     * or pass individual arguments of type CharSequence for a varargs invocation.
     *
     * assertFalse(StringUtils.endsWithAny("abcXYZ", null)); // replace with specific types to avoid warning
     */
    assertFalse(StringUtils.endsWithAny("abcXYZ", null.asInstanceOf[CharSequence]))
    assertFalse(StringUtils.endsWithAny("abcXYZ", null.asInstanceOf[Array[CharSequence]]))
    assertTrue(StringUtils.endsWithAny("abcXYZ", ""))
    assertTrue(
      "StringUtils.endsWithAny(abcxyz, StringBuilder(abc), StringBuffer(xyz))",
      StringUtils.endsWithAny("abcxyz", new StringBuilder("abc"), new StringBuffer("xyz"))
    )
    assertTrue(
      "StringUtils.endsWithAny(StringBuffer(abcxyz), StringBuilder(abc), StringBuffer(xyz))",
      StringUtils.endsWithAny(new StringBuffer("abcxyz"), new StringBuilder("abc"), new StringBuffer("xyz"))
    )
  }
}
