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
//import org.junit.jupiter.api.Assertions.assertFalse
//import org.junit.jupiter.api.Assertions.assertTrue
//import java.util
//import java.util.{ArrayList, HashMap}
//import org.apache.commons.lang3.builder.ToStringStyleTest.Person
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  * Unit tests {@link org.apache.commons.lang3.builder.ToStringStyle}.
//  */
//object StandardToStringStyleTest {
//  private val STYLE = new StandardToStringStyle
//
//  try STYLE.setUseShortClassName(true)
//  STYLE.setUseIdentityHashCode(false)
//  STYLE.setArrayStart("[")
//  STYLE.setArraySeparator(", ")
//  STYLE.setArrayEnd("]")
//  STYLE.setNullText("%NULL%")
//  STYLE.setSizeStartText("%SIZE=")
//  STYLE.setSizeEndText("%")
//  STYLE.setSummaryObjectStartText("%")
//  STYLE.setSummaryObjectEndText("%")
//
//}
//
//class StandardToStringStyleTest extends JUnitSuite {
//  final private val base = Integer.valueOf(5)
//  final private val baseStr = "Integer"
//
//  @Before def setUp(): Unit = {
//    ToStringBuilder.setDefaultStyle(StandardToStringStyleTest.STYLE)
//  }
//
//  @After def tearDown(): Unit = {
//    ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE)
//  }
//
//  @Test def testBlank(): Unit = {
//    assertEquals(baseStr + "[]", new ToStringBuilder(base).toString)
//  }
//
//  @Test def testAppendSuper(): Unit = {
//    assertEquals(baseStr + "[]", new ToStringBuilder(base).appendSuper("Integer@8888[]").toString)
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).appendSuper("Integer@8888[%NULL%]").toString)
//    assertEquals(
//      baseStr + "[a=hello]",
//      new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a", "hello").toString)
//    assertEquals(
//      baseStr + "[%NULL%,a=hello]",
//      new ToStringBuilder(base).appendSuper("Integer@8888[%NULL%]").append("a", "hello").toString)
//    assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString)
//  }
//
//  @Test def testObject(): Unit = {
//    val i3 = Integer.valueOf(3)
//    val i4 = Integer.valueOf(4)
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(null.asInstanceOf[Any]).toString)
//    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).toString)
//    assertEquals(baseStr + "[a=%NULL%]", new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).toString)
//    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).toString)
//    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString)
//    assertEquals(baseStr + "[a=%Integer%]", new ToStringBuilder(base).append("a", i3, false).toString)
//    assertEquals(
//      baseStr + "[a=%SIZE=0%]",
//      new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], false).toString)
//    assertEquals(baseStr + "[a=[]]", new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], true).toString)
//    assertEquals(
//      baseStr + "[a=%SIZE=0%]",
//      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).toString)
//    assertEquals(
//      baseStr + "[a={}]",
//      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).toString)
//    assertEquals(
//      baseStr + "[a=%SIZE=0%]",
//      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).toString)
//    assertEquals(
//      baseStr + "[a=[]]",
//      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).toString)
//  }
//
//  @Test def testPerson(): Unit = {
//    val p = new Person
//    p.name = "Suzy Queue"
//    p.age = 19
//    p.smoker = false
//    val pBaseStr = "ToStringStyleTest.Person"
//    assertEquals(
//      pBaseStr + "[name=Suzy Queue,age=19,smoker=false]",
//      new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString)
//  }
//
//  @Test def testLong(): Unit = {
//    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3L).toString)
//    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3L).toString)
//    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString)
//  }
//
//  @Test def testObjectArray(): Unit = {
//    var array = Array[AnyRef](null, base, Array[Int](3, 6))
//    assertEquals(baseStr + "[[%NULL%, 5, [3, 6]]]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[[%NULL%, 5, [3, 6]]]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testLongArray(): Unit = {
//    var array = Array[Long](1, 2, -3, 4)
//    assertEquals(baseStr + "[[1, 2, -3, 4]]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[[1, 2, -3, 4]]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testLongArrayArray(): Unit = {
//    var array = Array[Array[Long]](Array(1, 2), null, Array(5))
//    assertEquals(baseStr + "[[[1, 2], %NULL%, [5]]]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[[[1, 2], %NULL%, [5]]]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testDefaultValueOfUseClassName(): Unit = {
//    assertTrue((new StandardToStringStyle).isUseClassName)
//  }
//
//  @Test def testDefaultValueOfUseFieldNames(): Unit = {
//    assertTrue((new StandardToStringStyle).isUseFieldNames)
//  }
//
//  @Test def testDefaultValueOfUseShortClassName(): Unit = {
//    assertFalse((new StandardToStringStyle).isUseShortClassName)
//  }
//
//  @Test def testDefaultValueOfUseIdentityHashCode(): Unit = {
//    assertTrue((new StandardToStringStyle).isUseIdentityHashCode)
//  }
//}
