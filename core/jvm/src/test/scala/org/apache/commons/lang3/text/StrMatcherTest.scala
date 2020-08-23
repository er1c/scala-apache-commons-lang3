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

package org.apache.commons.lang3.text

import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

/**
  * Unit tests for {@link org.apache.commons.lang3.text.StrMatcher}.
  */
@deprecated object StrMatcherTest {
  private val BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray
  private val BUFFER2 = "abcdef".toCharArray
}

@deprecated class StrMatcherTest {
  @Test def testCommaMatcher() = {
    val matcher = StrMatcher.commaMatcher
    assertSame(matcher, StrMatcher.commaMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 0))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 1))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 2))
  }

  @Test def testTabMatcher() = {
    val matcher = StrMatcher.tabMatcher
    assertSame(matcher, StrMatcher.tabMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 2))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 4))
  }

  @Test def testSpaceMatcher() = {
    val matcher = StrMatcher.spaceMatcher
    assertSame(matcher, StrMatcher.spaceMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 4))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 5))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 6))
  }

  @Test def testSplitMatcher() = {
    val matcher = StrMatcher.splitMatcher
    assertSame(matcher, StrMatcher.splitMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 2))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 4))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 5))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 6))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 7))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 8))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 9))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 10))
  }

  @Test def testTrimMatcher() = {
    val matcher = StrMatcher.trimMatcher
    assertSame(matcher, StrMatcher.trimMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 2))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 4))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 5))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 6))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 7))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 8))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 9))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 10))
  }

  @Test def testSingleQuoteMatcher() = {
    val matcher = StrMatcher.singleQuoteMatcher
    assertSame(matcher, StrMatcher.singleQuoteMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 10))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 11))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 12))
  }

  @Test def testDoubleQuoteMatcher() = {
    val matcher = StrMatcher.doubleQuoteMatcher
    assertSame(matcher, StrMatcher.doubleQuoteMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 11))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 12))
  }

  @Test def testQuoteMatcher() = {
    val matcher = StrMatcher.quoteMatcher
    assertSame(matcher, StrMatcher.quoteMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 10))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 11))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER1, 12))
  }

  @Test def testNoneMatcher() = {
    val matcher = StrMatcher.noneMatcher
    assertSame(matcher, StrMatcher.noneMatcher)
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 0))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 1))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 2))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 4))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 5))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 6))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 7))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 8))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 9))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 10))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 11))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER1, 12))
  }

  @Test def testCharMatcher_char() = {
    val matcher = StrMatcher.charMatcher('c')
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 0))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 1))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 2))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 4))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 5))
  }

  @Test def testCharSetMatcher_String() = {
    val matcher = StrMatcher.charSetMatcher("ace")
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 0))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 1))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 2))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 3))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 4))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 5))
    assertSame(StrMatcher.noneMatcher, StrMatcher.charSetMatcher(""))
    assertSame(StrMatcher.noneMatcher, StrMatcher.charSetMatcher(null.asInstanceOf[String]))
    assertTrue(StrMatcher.charSetMatcher("a").isInstanceOf[StrMatcher.CharMatcher])
  }

  @Test def testCharSetMatcher_charArray() = {
    val matcher = StrMatcher.charSetMatcher("ace".toCharArray)
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 0))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 1))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 2))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 3))
    assertEquals(1, matcher.isMatch(StrMatcherTest.BUFFER2, 4))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 5))
    assertSame(StrMatcher.noneMatcher, StrMatcher.charSetMatcher())
    assertSame(StrMatcher.noneMatcher, StrMatcher.charSetMatcher(null.asInstanceOf[Array[Char]]))
    assertTrue(StrMatcher.charSetMatcher("a".toCharArray).isInstanceOf[StrMatcher.CharMatcher])
  }

  @Test def testStringMatcher_String() = {
    val matcher = StrMatcher.stringMatcher("bc")
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 0))
    assertEquals(2, matcher.isMatch(StrMatcherTest.BUFFER2, 1))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 2))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 4))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 5))
    assertSame(StrMatcher.noneMatcher, StrMatcher.stringMatcher(""))
    assertSame(StrMatcher.noneMatcher, StrMatcher.stringMatcher(null))
  }

  @Test def testMatcherIndices() = { // remember that the API contract is tight for the isMatch() method
    // all the onus is on the caller, so invalid inputs are not
    // the concern of StrMatcher, and are not bugs
    val matcher = StrMatcher.stringMatcher("bc")
    assertEquals(2, matcher.isMatch(StrMatcherTest.BUFFER2, 1, 1, StrMatcherTest.BUFFER2.length))
    assertEquals(2, matcher.isMatch(StrMatcherTest.BUFFER2, 1, 0, 3))
    assertEquals(0, matcher.isMatch(StrMatcherTest.BUFFER2, 1, 0, 2))
  }
}
