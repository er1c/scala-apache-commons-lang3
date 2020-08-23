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

package org.apache.commons.lang3.builder

import java.util
import org.apache.commons.lang3.builder.ToStringStyleTest.Person
import org.junit.Assert._
import org.junit.Test
import org.junit.{After, Before}

/**
  * Unit tests {@link org.apache.commons.lang3.builder.SimpleToStringStyleTest}.
  */
class SimpleToStringStyleTest {
  final private val base = Integer.valueOf(5)

  @Before def setUp(): Unit = {
    ToStringBuilder.setDefaultStyle(ToStringStyle.SIMPLE_STYLE)
  }

  @After def tearDown(): Unit = {
    ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE)
  }

  @Test def testBlank(): Unit = {
    assertEquals("", new ToStringBuilder(base).toString)
  }

  @Test def testAppendSuper(): Unit = {
    assertEquals("", new ToStringBuilder(base).appendSuper("").toString)
    assertEquals("<null>", new ToStringBuilder(base).appendSuper("<null>").toString)
    assertEquals("hello", new ToStringBuilder(base).appendSuper("").append("a", "hello").toString)
    assertEquals("<null>,hello", new ToStringBuilder(base).appendSuper("<null>").append("a", "hello").toString)
    assertEquals("hello", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString)
  }

  @Test def testObject(): Unit = {
    val i3 = Integer.valueOf(3)
    val i4 = Integer.valueOf(4)
    assertEquals("<null>", new ToStringBuilder(base).append(null.asInstanceOf[Any]).toString)
    assertEquals("3", new ToStringBuilder(base).append(i3).toString)
    assertEquals("<null>", new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).toString)
    assertEquals("3", new ToStringBuilder(base).append("a", i3).toString)
    assertEquals("3,4", new ToStringBuilder(base).append("a", i3).append("b", i4).toString)
    assertEquals("<Integer>", new ToStringBuilder(base).append("a", i3, false).toString)
    assertEquals("<size=0>", new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], false).toString)
    assertEquals("[]", new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], true).toString)
    assertEquals("<size=0>", new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).toString)
    assertEquals("{}", new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).toString)
    assertEquals(
      "<size=0>",
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).toString)
    assertEquals("{}", new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).toString)
  }

  @Test def testPerson(): Unit = {
    val p = new Person
    p.name = "Jane Q. Public"
    p.age = 47
    p.smoker = false

    assertEquals(
      "Jane Q. Public,47,false",
      new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString)
  }

  @Test def testLong(): Unit = {
    assertEquals("3", new ToStringBuilder(base).append(3L).toString)
    assertEquals("3", new ToStringBuilder(base).append("a", 3L).toString)
    assertEquals("3,4", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString)
  }

  @Test def testObjectArray(): Unit = {
    var array = Array[AnyRef](null, base, Array[Int](3, 6))
    assertEquals("{<null>,5,{3,6}}", new ToStringBuilder(base).append(array).toString)
    assertEquals("{<null>,5,{3,6}}", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals("<null>", new ToStringBuilder(base).append(array).toString)
    assertEquals("<null>", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }

  @Test def testLongArray(): Unit = {
    var array = Array[Long](1, 2, -3, 4)
    assertEquals("{1,2,-3,4}", new ToStringBuilder(base).append(array).toString)
    assertEquals("{1,2,-3,4}", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals("<null>", new ToStringBuilder(base).append(array).toString)
    assertEquals("<null>", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }

  @Test def testLongArrayArray(): Unit = {
    var array = Array[Array[Long]](Array(1, 2), null, Array(5))
    assertEquals("{{1,2},<null>,{5}}", new ToStringBuilder(base).append(array).toString)
    assertEquals("{{1,2},<null>,{5}}", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals("<null>", new ToStringBuilder(base).append(array).toString)
    assertEquals("<null>", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
}
