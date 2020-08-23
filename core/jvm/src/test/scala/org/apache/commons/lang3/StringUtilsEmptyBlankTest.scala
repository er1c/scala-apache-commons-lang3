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

import org.junit.Assert.{assertEquals, assertFalse, assertNull, assertTrue}
import org.junit.Test

/**
  * Unit tests {@link org.apache.commons.lang3.StringUtils} - Empty/Blank methods
  */
class StringUtilsEmptyBlankTest {
  @Test def testIsEmpty(): Unit = {
    assertTrue(StringUtils.isEmpty(null))
    assertTrue(StringUtils.isEmpty(""))
    assertFalse(StringUtils.isEmpty(" "))
    assertFalse(StringUtils.isEmpty("foo"))
    assertFalse(StringUtils.isEmpty("  foo  "))
  }

  @Test def testIsNotEmpty(): Unit = {
    assertFalse(StringUtils.isNotEmpty(null))
    assertFalse(StringUtils.isNotEmpty(""))
    assertTrue(StringUtils.isNotEmpty(" "))
    assertTrue(StringUtils.isNotEmpty("foo"))
    assertTrue(StringUtils.isNotEmpty("  foo  "))
  }

  @Test def testIsAnyEmpty(): Unit = {
    assertTrue(StringUtils.isAnyEmpty(null.asInstanceOf[String]))
    assertFalse(StringUtils.isAnyEmpty(null.asInstanceOf[Array[String]]))
    assertTrue(StringUtils.isAnyEmpty(null, "foo"))
    assertTrue(StringUtils.isAnyEmpty("", "bar"))
    assertTrue(StringUtils.isAnyEmpty("bob", ""))
    assertTrue(StringUtils.isAnyEmpty("  bob  ", null))
    assertFalse(StringUtils.isAnyEmpty(" ", "bar"))
    assertFalse(StringUtils.isAnyEmpty("foo", "bar"))
  }

  @Test def testIsNoneEmpty(): Unit = {
    assertFalse(StringUtils.isNoneEmpty(null.asInstanceOf[String]))
    assertTrue(StringUtils.isNoneEmpty(null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.isNoneEmpty(null, "foo"))
    assertFalse(StringUtils.isNoneEmpty("", "bar"))
    assertFalse(StringUtils.isNoneEmpty("bob", ""))
    assertFalse(StringUtils.isNoneEmpty("  bob  ", null))
    assertTrue(StringUtils.isNoneEmpty(" ", "bar"))
    assertTrue(StringUtils.isNoneEmpty("foo", "bar"))
  }

  @Test def testIsAllEmpty(): Unit = {
    assertTrue(StringUtils.isAllEmpty())
    //assertTrue(StringUtils.isAllEmpty)
    assertTrue(StringUtils.isAllEmpty(null.asInstanceOf[String]))
    assertTrue(StringUtils.isAllEmpty(null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.isAllEmpty(null, "foo"))
    assertFalse(StringUtils.isAllEmpty("", "bar"))
    assertFalse(StringUtils.isAllEmpty("bob", ""))
    assertFalse(StringUtils.isAllEmpty("  bob  ", null))
    assertFalse(StringUtils.isAllEmpty(" ", "bar"))
    assertFalse(StringUtils.isAllEmpty("foo", "bar"))
    assertTrue(StringUtils.isAllEmpty("", null))
  }

  @Test def testIsBlank(): Unit = {
    assertTrue(StringUtils.isBlank(null))
    assertTrue(StringUtils.isBlank(""))
    assertTrue(StringUtils.isBlank(StringUtilsTest.WHITESPACE))
    assertFalse(StringUtils.isBlank("foo"))
    assertFalse(StringUtils.isBlank("  foo  "))
  }

  @Test def testIsNotBlank(): Unit = {
    assertFalse(StringUtils.isNotBlank(null))
    assertFalse(StringUtils.isNotBlank(""))
    assertFalse(StringUtils.isNotBlank(StringUtilsTest.WHITESPACE))
    assertTrue(StringUtils.isNotBlank("foo"))
    assertTrue(StringUtils.isNotBlank("  foo  "))
  }

  @Test def testIsAnyBlank(): Unit = {
    assertTrue(StringUtils.isAnyBlank(null.asInstanceOf[String]))
    assertFalse(StringUtils.isAnyBlank(null.asInstanceOf[Array[String]]))
    assertTrue(StringUtils.isAnyBlank(null, "foo"))
    assertTrue(StringUtils.isAnyBlank(null, null))
    assertTrue(StringUtils.isAnyBlank("", "bar"))
    assertTrue(StringUtils.isAnyBlank("bob", ""))
    assertTrue(StringUtils.isAnyBlank("  bob  ", null))
    assertTrue(StringUtils.isAnyBlank(" ", "bar"))
    assertFalse(StringUtils.isAnyBlank("foo", "bar"))
  }

  @Test def testIsNoneBlank(): Unit = {
    assertFalse(StringUtils.isNoneBlank(null.asInstanceOf[String]))
    assertTrue(StringUtils.isNoneBlank(null.asInstanceOf[Array[String]]))
    assertFalse(StringUtils.isNoneBlank(null, "foo"))
    assertFalse(StringUtils.isNoneBlank(null, null))
    assertFalse(StringUtils.isNoneBlank("", "bar"))
    assertFalse(StringUtils.isNoneBlank("bob", ""))
    assertFalse(StringUtils.isNoneBlank("  bob  ", null))
    assertFalse(StringUtils.isNoneBlank(" ", "bar"))
    assertTrue(StringUtils.isNoneBlank("foo", "bar"))
  }

  @Test def testIsAllBlank(): Unit = {
    assertTrue(StringUtils.isAllBlank(null.asInstanceOf[String]))
    assertTrue(StringUtils.isAllBlank(null.asInstanceOf[Array[String]]))
    assertTrue(StringUtils.isAllBlank(null, null))
    assertTrue(StringUtils.isAllBlank(null, " "))
    assertFalse(StringUtils.isAllBlank(null, "foo"))
    assertFalse(StringUtils.isAllBlank("", "bar"))
    assertFalse(StringUtils.isAllBlank("bob", ""))
    assertFalse(StringUtils.isAllBlank("  bob  ", null))
    assertFalse(StringUtils.isAllBlank(" ", "bar"))
    assertFalse(StringUtils.isAllBlank("foo", "bar"))
  }

  @Test def testFirstNonBlank(): Unit = {
    //assertNull(StringUtils.firstNonBlank())
    assertNull(StringUtils.firstNonBlank(null.asInstanceOf[Array[String]]))
    assertNull(StringUtils.firstNonBlank(null, null, null))
    assertNull(StringUtils.firstNonBlank(null, "", " "))
    assertNull(StringUtils.firstNonBlank(null, null, " "))
    assertEquals("zz", StringUtils.firstNonBlank(null, "zz"))
    assertEquals("abc", StringUtils.firstNonBlank("abc"))
    assertEquals("xyz", StringUtils.firstNonBlank(null, "xyz"))
    assertEquals("xyz", StringUtils.firstNonBlank(null, "xyz", "abc"))
  }

  @Test def testFirstNonEmpty(): Unit = {
    //assertNull(StringUtils.firstNonEmpty())
    assertNull(StringUtils.firstNonEmpty(null.asInstanceOf[Array[String]]))
    assertNull(StringUtils.firstNonEmpty(null, null, null))
    assertEquals(" ", StringUtils.firstNonEmpty(null, "", " "))
    assertNull(StringUtils.firstNonEmpty(null, null, ""))
    assertEquals("zz", StringUtils.firstNonEmpty(null, "zz"))
    assertEquals("abc", StringUtils.firstNonEmpty("abc"))
    assertEquals("xyz", StringUtils.firstNonEmpty(null, "xyz"))
    assertEquals("xyz", StringUtils.firstNonEmpty(null, "xyz", "abc"))
  }
}
