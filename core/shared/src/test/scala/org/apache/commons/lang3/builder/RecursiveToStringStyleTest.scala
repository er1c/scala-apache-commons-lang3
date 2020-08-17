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
//import java.util
//import java.util.{ArrayList, HashMap}
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  * Unit tests {@link org.apache.commons.lang3.builder.RecursiveToStringStyleTest}.
//  */
//object RecursiveToStringStyleTest {
//
//  private[builder] class Person {
//    /**
//      * Test String field.
//      */
//    private[builder] var name: String = null
//    /**
//      * Test integer field.
//      */
//    private[builder] var age: Int = 0
//    /**
//      * Test boolean field.
//      */
//    private[builder] var smoker: Boolean = false
//    /**
//      * Test Object field.
//      */
//    private[builder] var job: Job = null
//  }
//
//  private[builder] class Job {
//    private[builder] var title: String = null
//  }
//
//}
//
//class RecursiveToStringStyleTest extends JUnitSuite {
//  final private val base = Integer.valueOf(5)
//  final private val baseStr = base.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(base))
//
//  @Before def setUp(): Unit = {
//    ToStringBuilder.setDefaultStyle(new RecursiveToStringStyle)
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
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").toString)
//    assertEquals(
//      baseStr + "[a=hello]",
//      new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a", "hello").toString)
//    assertEquals(
//      baseStr + "[<null>,a=hello]",
//      new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").append("a", "hello").toString)
//    assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString)
//  }
//
//  @Test def testObject(): Unit = {
//    val i3 = Integer.valueOf(3)
//    val i4 = Integer.valueOf(4)
//    val emptyList = new util.ArrayList[AnyRef]
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(null.asInstanceOf[Any]).toString)
//    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).toString)
//    assertEquals(baseStr + "[a=<null>]", new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).toString)
//    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).toString)
//    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString)
//    assertEquals(baseStr + "[a=<Integer>]", new ToStringBuilder(base).append("a", i3, false).toString)
//    assertEquals(baseStr + "[a=<size=0>]", new ToStringBuilder(base).append("a", emptyList, false).toString)
//    assertEquals(
//      baseStr + "[a=java.util.ArrayList@" + Integer.toHexString(System.identityHashCode(emptyList)) + "{}]",
//      new ToStringBuilder(base).append("a", emptyList, true).toString
//    )
//    assertEquals(
//      baseStr + "[a=<size=0>]",
//      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).toString)
//    assertEquals(
//      baseStr + "[a={}]",
//      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).toString)
//    assertEquals(
//      baseStr + "[a=<size=0>]",
//      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).toString)
//    assertEquals(
//      baseStr + "[a={}]",
//      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).toString)
//  }
//
//  @Test def testPerson(): Unit = {
//    val p = new RecursiveToStringStyleTest.Person
//    p.name = "John Doe"
//    p.age = 33
//    p.smoker = false
//    p.job = new RecursiveToStringStyleTest.Job
//    p.job.title = "Manager"
//    val pBaseStr = p.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(p))
//    val pJobStr = p.job.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(p.job))
//    assertEquals(
//      pBaseStr + "[age=33,job=" + pJobStr + "[title=Manager],name=John Doe,smoker=false]",
//      new ReflectionToStringBuilder[RecursiveToStringStyleTest.Person](p, new RecursiveToStringStyle).toString)
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
//    assertEquals(baseStr + "[{<null>,5,{3,6}}]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[{<null>,5,{3,6}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testLongArray(): Unit = {
//    var array = Array[Long](1, 2, -3, 4)
//    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//
//  @Test def testLongArrayArray(): Unit = {
//    var array = Array[Array[Long]](Array(1, 2), null, Array(5))
//    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//    array = null
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
//    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
//  }
//}
