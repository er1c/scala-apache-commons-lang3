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
//import java.util
//import java.util.{ArrayList, HashMap}
//import org.apache.commons.lang3.builder.ToStringStyleTest.Person
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  * Unit tests {@link org.apache.commons.lang3.builder.MultiLineToStringStyleTest}.
//  */
//class MultiLineToStringStyleTest extends JUnitSuite {
//  final private val base = Integer.valueOf(5)
//  final private val baseStr = base.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(base))
//
//  @Before def setUp(): Unit = {
//    ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE)
//  }
//
//  @After def tearDown(): Unit = {
//    ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE)
//  }
//
//  @Test def testBlank(): Unit = {
//    assertEquals(baseStr + "[" + System.lineSeparator + "]", new ToStringBuilder(base).toString)
//  }
//
//  @Test def testAppendSuper(): Unit = {
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "]",
//      new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator + "]").toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base)
//        .appendSuper("Integer@8888[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]")
//        .toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=hello" + System.lineSeparator + "]",
//      new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator + "]").append("a", "hello").toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "  a=hello" + System.lineSeparator + "]",
//      new ToStringBuilder(base)
//        .appendSuper("Integer@8888[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]")
//        .append("a", "hello")
//        .toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=hello" + System.lineSeparator + "]",
//      new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString)
//  }
//
//  @Test def testObject(): Unit = {
//    val i3 = Integer.valueOf(3)
//    val i4 = Integer.valueOf(4)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(null.asInstanceOf[Any]).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  3" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(i3).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=<null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=3" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", i3).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=3" + System.lineSeparator + "  b=4" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", i3).append("b", i4).toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=<Integer>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", i3, false).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=<size=0>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], false).toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=[]" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], true).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=<size=0>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a={}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=<size=0>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).toString
//    )
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a={}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).toString
//    )
//  }
//
//  @Test def testPerson(): Unit = {
//    val p = new Person
//    p.name = "Jane Doe"
//    p.age = 25
//    p.smoker = true
//    val pBaseStr = p.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(p))
//    assertEquals(
//      pBaseStr + "[" + System.lineSeparator + "  name=Jane Doe" + System.lineSeparator + "  age=25" + System.lineSeparator + "  smoker=true" + System.lineSeparator + "]",
//      new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString
//    )
//  }
//
//  @Test def testLong(): Unit = {
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  3" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(3L).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=3" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", 3L).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  a=3" + System.lineSeparator + "  b=4" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString
//    )
//  }
//
//  @Test def testObjectArray(): Unit = {
//    var array = Array[AnyRef](null, base, Array[Int](3, 6))
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  {<null>,5,{3,6}}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  {<null>,5,{3,6}}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testLongArray(): Unit = {
//    var array = Array[Long](1, 2, -3, 4)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  {1,2,-3,4}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  {1,2,-3,4}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testLongArrayArray(): Unit = {
//    var array = Array[Array[Long]](Array(1, 2), null, Array(5))
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  {{1,2},<null>,{5}}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  {{1,2},<null>,{5}}" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array).toString)
//    assertEquals(
//      baseStr + "[" + System.lineSeparator + "  <null>" + System.lineSeparator + "]",
//      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//}
