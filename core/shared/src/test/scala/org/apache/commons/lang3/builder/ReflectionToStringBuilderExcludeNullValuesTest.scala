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
//
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
//object ReflectionToStringBuilderExcludeNullValuesTest {
//
//  private[builder] class TestFixture private[builder] (val testIntegerField: Integer, val testStringField: String) {}
//
//  private val INTEGER_FIELD_NAME = "testIntegerField"
//  private val STRING_FIELD_NAME = "testStringField"
//}
//
//class ReflectionToStringBuilderExcludeNullValuesTest {
//  final private val BOTH_NON_NULL = new ReflectionToStringBuilderExcludeNullValuesTest.TestFixture(0, "str")
//  final private val FIRST_NULL = new ReflectionToStringBuilderExcludeNullValuesTest.TestFixture(null, "str")
//  final private val SECOND_NULL = new ReflectionToStringBuilderExcludeNullValuesTest.TestFixture(0, null)
//  final private val BOTH_NULL = new ReflectionToStringBuilderExcludeNullValuesTest.TestFixture(null, null)
//
//  @Test def test_NonExclude(): Unit = { //normal case=
//    var toString = ReflectionToStringBuilder.toString(BOTH_NON_NULL, null, false, false, false, null)
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    //make one null
//    toString = ReflectionToStringBuilder.toString(FIRST_NULL, null, false, false, false, null)
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    //other one null
//    toString = ReflectionToStringBuilder.toString(SECOND_NULL, null, false, false, false, null)
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    //make the both null
//    toString = ReflectionToStringBuilder.toString(BOTH_NULL, null, false, false, false, null)
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//  }
//
//  @Test def test_excludeNull(): Unit = { //test normal case
//    var toString = ReflectionToStringBuilder.toString(BOTH_NON_NULL, null, false, false, true, null)
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    toString = ReflectionToStringBuilder.toString(FIRST_NULL, null, false, false, true, null)
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    toString = ReflectionToStringBuilder.toString(SECOND_NULL, null, false, false, true, null)
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    //both null
//    toString = ReflectionToStringBuilder.toString(BOTH_NULL, null, false, false, true, null)
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//  }
//
//  @Test def test_ConstructorOption(): Unit = {
//    var builder = new ReflectionToStringBuilder[_](BOTH_NON_NULL, null, null, null, false, false, true)
//    assertTrue(builder.isExcludeNullValues)
//    var toString = builder.toString
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    builder = new ReflectionToStringBuilder[_](FIRST_NULL, null, null, null, false, false, true)
//    toString = builder.toString
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    builder = new ReflectionToStringBuilder[_](SECOND_NULL, null, null, null, false, false, true)
//    toString = builder.toString
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    builder = new ReflectionToStringBuilder[_](BOTH_NULL, null, null, null, false, false, true)
//    toString = builder.toString
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//  }
//
//  @Test def test_ConstructorOptionNormal(): Unit = {
//    val builder = new ReflectionToStringBuilder[_](BOTH_NULL, null, null, null, false, false, false)
//    assertFalse(builder.isExcludeNullValues)
//    var toString = builder.toString
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    //regression test older constructors
//    var oldBuilder = new ReflectionToStringBuilder[_](BOTH_NULL)
//    toString = oldBuilder.toString
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    oldBuilder = new ReflectionToStringBuilder[_](BOTH_NULL, null, null, null, false, false)
//    toString = oldBuilder.toString
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    oldBuilder = new ReflectionToStringBuilder[_](BOTH_NULL, null, null)
//    toString = oldBuilder.toString
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//  }
//
//  @Test def test_ConstructorOption_ExcludeNull(): Unit = {
//    var builder = new ReflectionToStringBuilder[_](BOTH_NULL, null, null, null, false, false, false)
//    builder.setExcludeNullValues(true)
//    assertTrue(builder.isExcludeNullValues)
//    var toString = builder.toString
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    builder = new ReflectionToStringBuilder[_](BOTH_NULL, null, null, null, false, false, true)
//    toString = builder.toString
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//    val oldBuilder = new ReflectionToStringBuilder[_](BOTH_NULL)
//    oldBuilder.setExcludeNullValues(true)
//    assertTrue(oldBuilder.isExcludeNullValues)
//    toString = oldBuilder.toString
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.STRING_FIELD_NAME))
//    assertFalse(toString.contains(ReflectionToStringBuilderExcludeNullValuesTest.INTEGER_FIELD_NAME))
//  }
//}
