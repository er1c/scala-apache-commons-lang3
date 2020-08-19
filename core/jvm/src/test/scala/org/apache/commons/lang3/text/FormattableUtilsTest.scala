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

//package org.apache.commons.lang3.text
//
//import java.util.FormattableFlags.LEFT_JUSTIFY
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertThrows
//import java.util.Formatter
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link FormattableUtils}.
//  */
//@deprecated class FormattableUtilsTest extends JUnitSuite {
//  @Test def testDefaultAppend() = {
//    assertEquals("foo", FormattableUtils.append("foo", new Formatter, 0, -1, -1).toString)
//    assertEquals("fo", FormattableUtils.append("foo", new Formatter, 0, -1, 2).toString)
//    assertEquals(" foo", FormattableUtils.append("foo", new Formatter, 0, 4, -1).toString)
//    assertEquals("   foo", FormattableUtils.append("foo", new Formatter, 0, 6, -1).toString)
//    assertEquals(" fo", FormattableUtils.append("foo", new Formatter, 0, 3, 2).toString)
//    assertEquals("   fo", FormattableUtils.append("foo", new Formatter, 0, 5, 2).toString)
//    assertEquals("foo ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 4, -1).toString)
//    assertEquals("foo   ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 6, -1).toString)
//    assertEquals("fo ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 3, 2).toString)
//    assertEquals("fo   ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 5, 2).toString)
//  }
//
//  @Test def testAlternatePadCharacter() = {
//    val pad = '_'
//    assertEquals("foo", FormattableUtils.append("foo", new Formatter, 0, -1, -1, pad).toString)
//    assertEquals("fo", FormattableUtils.append("foo", new Formatter, 0, -1, 2, pad).toString)
//    assertEquals("_foo", FormattableUtils.append("foo", new Formatter, 0, 4, -1, pad).toString)
//    assertEquals("___foo", FormattableUtils.append("foo", new Formatter, 0, 6, -1, pad).toString)
//    assertEquals("_fo", FormattableUtils.append("foo", new Formatter, 0, 3, 2, pad).toString)
//    assertEquals("___fo", FormattableUtils.append("foo", new Formatter, 0, 5, 2, pad).toString)
//    assertEquals("foo_", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 4, -1, pad).toString)
//    assertEquals("foo___", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 6, -1, pad).toString)
//    assertEquals("fo_", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 3, 2, pad).toString)
//    assertEquals("fo___", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 5, 2, pad).toString)
//  }
//
//  @Test def testEllipsis() = {
//    assertEquals("foo", FormattableUtils.append("foo", new Formatter, 0, -1, -1, "*").toString)
//    assertEquals("f*", FormattableUtils.append("foo", new Formatter, 0, -1, 2, "*").toString)
//    assertEquals(" foo", FormattableUtils.append("foo", new Formatter, 0, 4, -1, "*").toString)
//    assertEquals("   foo", FormattableUtils.append("foo", new Formatter, 0, 6, -1, "*").toString)
//    assertEquals(" f*", FormattableUtils.append("foo", new Formatter, 0, 3, 2, "*").toString)
//    assertEquals("   f*", FormattableUtils.append("foo", new Formatter, 0, 5, 2, "*").toString)
//    assertEquals("foo ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 4, -1, "*").toString)
//    assertEquals("foo   ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 6, -1, "*").toString)
//    assertEquals("f* ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 3, 2, "*").toString)
//    assertEquals("f*   ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 5, 2, "*").toString)
//    assertEquals("foo", FormattableUtils.append("foo", new Formatter, 0, -1, -1, "+*").toString)
//    assertEquals("+*", FormattableUtils.append("foo", new Formatter, 0, -1, 2, "+*").toString)
//    assertEquals(" foo", FormattableUtils.append("foo", new Formatter, 0, 4, -1, "+*").toString)
//    assertEquals("   foo", FormattableUtils.append("foo", new Formatter, 0, 6, -1, "+*").toString)
//    assertEquals(" +*", FormattableUtils.append("foo", new Formatter, 0, 3, 2, "+*").toString)
//    assertEquals("   +*", FormattableUtils.append("foo", new Formatter, 0, 5, 2, "+*").toString)
//    assertEquals("foo ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 4, -1, "+*").toString)
//    assertEquals("foo   ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 6, -1, "+*").toString)
//    assertEquals("+* ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 3, 2, "+*").toString)
//    assertEquals("+*   ", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 5, 2, "+*").toString)
//  }
//
//  @Test def testIllegalEllipsis() = assertThrows(classOf[IllegalArgumentException], () => FormattableUtils.append("foo", new Formatter, 0, -1, 1, "xx"))
//
//  @Test def testAlternatePadCharAndEllipsis() = {
//    assertEquals("foo", FormattableUtils.append("foo", new Formatter, 0, -1, -1, '_', "*").toString)
//    assertEquals("f*", FormattableUtils.append("foo", new Formatter, 0, -1, 2, '_', "*").toString)
//    assertEquals("_foo", FormattableUtils.append("foo", new Formatter, 0, 4, -1, '_', "*").toString)
//    assertEquals("___foo", FormattableUtils.append("foo", new Formatter, 0, 6, -1, '_', "*").toString)
//    assertEquals("_f*", FormattableUtils.append("foo", new Formatter, 0, 3, 2, '_', "*").toString)
//    assertEquals("___f*", FormattableUtils.append("foo", new Formatter, 0, 5, 2, '_', "*").toString)
//    assertEquals("foo_", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 4, -1, '_', "*").toString)
//    assertEquals("foo___", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 6, -1, '_', "*").toString)
//    assertEquals("f*_", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 3, 2, '_', "*").toString)
//    assertEquals("f*___", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 5, 2, '_', "*").toString)
//    assertEquals("foo", FormattableUtils.append("foo", new Formatter, 0, -1, -1, '_', "+*").toString)
//    assertEquals("+*", FormattableUtils.append("foo", new Formatter, 0, -1, 2, '_', "+*").toString)
//    assertEquals("_foo", FormattableUtils.append("foo", new Formatter, 0, 4, -1, '_', "+*").toString)
//    assertEquals("___foo", FormattableUtils.append("foo", new Formatter, 0, 6, -1, '_', "+*").toString)
//    assertEquals("_+*", FormattableUtils.append("foo", new Formatter, 0, 3, 2, '_', "+*").toString)
//    assertEquals("___+*", FormattableUtils.append("foo", new Formatter, 0, 5, 2, '_', "+*").toString)
//    assertEquals("foo_", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 4, -1, '_', "+*").toString)
//    assertEquals("foo___", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 6, -1, '_', "+*").toString)
//    assertEquals("+*_", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 3, 2, '_', "+*").toString)
//    assertEquals("+*___", FormattableUtils.append("foo", new Formatter, LEFT_JUSTIFY, 5, 2, '_', "+*").toString)
//  }
//}
