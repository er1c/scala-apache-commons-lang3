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

//package org.apache.commons.lang3.builder
//
//import org.scalatestplus.junit.JUnitSuite
//
//
//import org.junit.jupiter.api.Assertions.assertTrue
//import java.util
//import java.util.{Iterator, List}
//import org.junit.jupiter.api.Test
//
///**
//  * Unit tests {@link DiffResult}.
//  */
//object DiffResultTest {
//  private val SIMPLE_FALSE = new DiffResultTest.SimpleClass(false)
//  private val SIMPLE_TRUE = new DiffResultTest.SimpleClass(true)
//  private val SHORT_STYLE = ToStringStyle.SHORT_PREFIX_STYLE
//
//  private object SimpleClass {
//    private[builder] def getFieldName = "booleanField"
//  }
//
//  private class SimpleClass private[builder] (val booleanField: Boolean) extends Nothing {
//    def diff(obj: DiffResultTest.SimpleClass): Nothing =
//      new Nothing(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
//        .append(SimpleClass.getFieldName, booleanField, obj.booleanField)
//        .build
//  }
//
//  private class EmptyClass {}
//
//}
//
//class DiffResultTest extends JUnitSuite {
//  @Test def testListIsNonModifiable(): Unit = {
//    val lhs = new DiffResultTest.SimpleClass(true)
//    val rhs = new DiffResultTest.SimpleClass(false)
//    val diffs = lhs.diff(rhs).getDiffs
//    val list = new Nothing(lhs, rhs, diffs, DiffResultTest.SHORT_STYLE)
//    assertEquals(diffs, list.getDiffs)
//    assertEquals(1, list.getNumberOfDiffs)
//    assertThrows(classOf[UnsupportedOperationException], () => list.getDiffs.remove(0))
//  }
//
//  @Test def testIterator(): Unit = {
//    val lhs = new DiffResultTest.SimpleClass(true)
//    val rhs = new DiffResultTest.SimpleClass(false)
//    val diffs = lhs.diff(rhs).getDiffs
//    val expectedIterator = diffs.iterator
//    val list = new Nothing(lhs, rhs, diffs, DiffResultTest.SHORT_STYLE)
//    val iterator = list.iterator
//    while ({
//      iterator.hasNext
//    }) {
//      assertTrue(expectedIterator.hasNext)
//      assertEquals(expectedIterator.next, iterator.next)
//    }
//  }
//
//  @Test def testToStringOutput(): Unit = {
//    val list = new Nothing(
//      new DiffResultTest.EmptyClass,
//      new DiffResultTest.EmptyClass,
//      ToStringStyle.SHORT_PREFIX_STYLE).append("test", false, true).build
//    assertEquals(
//      "DiffResultTest.EmptyClass[test=false] differs from DiffResultTest.EmptyClass[test=true]",
//      list.toString)
//  }
//
//  @Test def testToStringSpecifyStyleOutput(): Unit = {
//    val list = DiffResultTest.SIMPLE_FALSE.diff(DiffResultTest.SIMPLE_TRUE)
//    assertEquals(list.getToStringStyle, DiffResultTest.SHORT_STYLE)
//    val lhsString = new ToStringBuilder(DiffResultTest.SIMPLE_FALSE, ToStringStyle.MULTI_LINE_STYLE)
//      .append(DiffResultTest.SimpleClass.getFieldName, DiffResultTest.SIMPLE_FALSE.booleanField)
//      .build
//    val rhsString = new ToStringBuilder(DiffResultTest.SIMPLE_TRUE, ToStringStyle.MULTI_LINE_STYLE)
//      .append(DiffResultTest.SimpleClass.getFieldName, DiffResultTest.SIMPLE_TRUE.booleanField)
//      .build
//    val expectedOutput = String.format("%s differs from %s", lhsString, rhsString)
//    assertEquals(expectedOutput, list.toString(ToStringStyle.MULTI_LINE_STYLE))
//  }
//
//  @Test def testNullLhs(): Unit = {
//    assertThrows[NullPointerException](
//      new Nothing(
//        null,
//        DiffResultTest.SIMPLE_FALSE,
//        DiffResultTest.SIMPLE_TRUE.diff(DiffResultTest.SIMPLE_FALSE).getDiffs,
//        DiffResultTest.SHORT_STYLE))
//  }
//
//  @Test def testNullRhs(): Unit = {
//    assertThrows[NullPointerException](
//      new Nothing(
//        DiffResultTest.SIMPLE_TRUE,
//        null,
//        DiffResultTest.SIMPLE_TRUE.diff(DiffResultTest.SIMPLE_FALSE).getDiffs,
//        DiffResultTest.SHORT_STYLE))
//  }
//
//  @Test def testNullList(): Unit = {
//    assertThrows[NullPointerException](
//      new Nothing(DiffResultTest.SIMPLE_TRUE, DiffResultTest.SIMPLE_FALSE, null, DiffResultTest.SHORT_STYLE))
//  }
//
//  @Test def testNullStyle(): Unit = {
//    val diffResult = new Nothing(
//      DiffResultTest.SIMPLE_TRUE,
//      DiffResultTest.SIMPLE_FALSE,
//      DiffResultTest.SIMPLE_TRUE.diff(DiffResultTest.SIMPLE_FALSE).getDiffs,
//      null)
//    assertEquals(ToStringStyle.DEFAULT_STYLE, diffResult.getToStringStyle)
//  }
//
//  @Test def testNoDifferencesString(): Unit = {
//    val diffResult =
//      new Nothing(DiffResultTest.SIMPLE_TRUE, DiffResultTest.SIMPLE_TRUE, DiffResultTest.SHORT_STYLE).build
//    assertEquals(DiffResult.OBJECTS_SAME_STRING, diffResult.toString)
//  }
//
//  @Test def testLeftAndRightGetters(): Unit = {
//    val left = new DiffResultTest.SimpleClass(true)
//    val right = new DiffResultTest.SimpleClass(false)
//    val diffs = left.diff(right).getDiffs
//    val diffResult = new Nothing(left, right, diffs, DiffResultTest.SHORT_STYLE)
//    assertEquals(left, diffResult.getLeft)
//    assertEquals(right, diffResult.getRight)
//  }
//}
