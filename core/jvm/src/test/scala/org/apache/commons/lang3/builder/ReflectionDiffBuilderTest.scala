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
//object ReflectionDiffBuilderTest {
//  private val SHORT_STYLE = ToStringStyle.SHORT_PREFIX_STYLE
//
//  @SuppressWarnings(Array("unused")) private object TypeTestClass {
//    private val staticField = 0
//  }
//
//  @SuppressWarnings(Array("unused")) private class TypeTestClass extends Diffable[TypeTestClass] {
//    final private val style = SHORT_STYLE
//    final private val booleanField = true
//    final private val booleanArrayField = Array(true)
//    final private val byteField = 0xff.toByte
//    final private val byteArrayField = Array(0xff.toByte)
//    private val charField = 'a'
//    private val charArrayField = Array('a')
//    final private val doubleField = 1.0
//    final private val doubleArrayField = Array(1.0)
//    final private val floatField = 1.0f
//    final private val floatArrayField = Array(1.0f)
//    private[builder] val intField = 1
//    final private val intArrayField = Array(1)
//    final private val longField = 1L
//    final private val longArrayField = Array(1L)
//    final private val shortField = 1
//    final private val shortArrayField = Array(1)
//    final private val objectField = null
//    final private val objectArrayField = Array(null)
//    private val transientField = null
//
//    def diff(obj: ReflectionDiffBuilderTest.TypeTestClass): Nothing = new Nothing(this, obj, style).build
//
//    override def hashCode: Int = HashCodeBuilder.reflectionHashCode(this, false)
//
//    override def equals(obj: Any): Boolean = EqualsBuilder.reflectionEquals(this, obj, false)
//  }
//
//  @SuppressWarnings(Array("unused")) private class TypeTestChildClass extends ReflectionDiffBuilderTest.TypeTestClass {
//    private[builder] val field = "a"
//  }
//
//}
//
//class ReflectionDiffBuilderTest extends JUnitSuite {
//  @Test def test_no_differences(): Unit = {
//    val firstObject = new ReflectionDiffBuilderTest.TypeTestClass
//    val secondObject = new ReflectionDiffBuilderTest.TypeTestClass
//    val list = firstObject.diff(secondObject)
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def test_primitive_difference(): Unit = {
//    val firstObject = new ReflectionDiffBuilderTest.TypeTestClass
//    firstObject.charField = 'c'
//    val secondObject = new ReflectionDiffBuilderTest.TypeTestClass
//    val list = firstObject.diff(secondObject)
//    assertEquals(1, list.getNumberOfDiffs)
//  }
//
//  @Test def test_array_difference(): Unit = {
//    val firstObject = new ReflectionDiffBuilderTest.TypeTestClass
//    firstObject.charArrayField = Array[Char]('c')
//    val secondObject = new ReflectionDiffBuilderTest.TypeTestClass
//    val list = firstObject.diff(secondObject)
//    assertEquals(1, list.getNumberOfDiffs)
//  }
//
//  @Test def test_transient_field_difference(): Unit = {
//    val firstObject = new ReflectionDiffBuilderTest.TypeTestClass
//    firstObject.transientField = "a"
//    val secondObject = new ReflectionDiffBuilderTest.TypeTestClass
//    firstObject.transientField = "b"
//    val list = firstObject.diff(secondObject)
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def test_no_differences_inheritance(): Unit = {
//    val firstObject = new ReflectionDiffBuilderTest.TypeTestChildClass
//    val secondObject = new ReflectionDiffBuilderTest.TypeTestChildClass
//    val list = firstObject.diff(secondObject)
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def test_difference_in_inherited_field(): Unit = {
//    val firstObject = new ReflectionDiffBuilderTest.TypeTestChildClass
//    firstObject.intField = 99
//    val secondObject = new ReflectionDiffBuilderTest.TypeTestChildClass
//    val list = firstObject.diff(secondObject)
//    assertEquals(1, list.getNumberOfDiffs)
//  }
//}
