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
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  * Unit tests {@link Diff}.
//  */
//object DiffTest {
//  private val FIELD_NAME = "field"
//  private val booleanDiff = new DiffTest.BooleanDiff(FIELD_NAME)
//
//  @SerialVersionUID(1L)
//  private class BooleanDiff protected (val fieldName: String) extends Nothing(fieldName) {
//    def getLeft: Boolean = Boolean.TRUE
//
//    def getRight: Boolean = Boolean.FALSE
//  }
//
//}
//
//class DiffTest extends JUnitSuite {
//  @Test def testCannotModify(): Unit = {
//    assertThrows(classOf[UnsupportedOperationException], () => DiffTest.booleanDiff.setValue(Boolean.FALSE))
//  }
//
//  @Test def testGetFieldName(): Unit = {
//    assertEquals(DiffTest.FIELD_NAME, DiffTest.booleanDiff.getFieldName)
//  }
//
//  @Test def testGetType(): Unit = {
//    assertEquals(classOf[Boolean], DiffTest.booleanDiff.getType)
//  }
//
//  @Test def testToString(): Unit = {
//    assertEquals(
//      String.format("[%s: %s, %s]", DiffTest.FIELD_NAME, DiffTest.booleanDiff.getLeft, DiffTest.booleanDiff.getRight),
//      DiffTest.booleanDiff.toString)
//  }
//}
