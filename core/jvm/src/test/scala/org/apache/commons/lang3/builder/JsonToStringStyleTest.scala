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

import org.scalatestplus.junit.JUnitSuite
import java.util
import java.util.{Collections, Date}
import org.apache.commons.lang3.builder.ToStringStyleTest.Person
import org.junit.Assert._
import org.junit.Test
import org.junit.{After, Before}

/**
  * Unit tests {@link org.apache.commons.lang3.builder.JsonToStringStyleTest}.
  */
object JsonToStringStyleTest {

  /**
    * An object with nested object structures used to test {@code ToStringStyle.JsonToStringStyle}.
    */
  private[builder] class NestingPerson {
    /**
      * Test String field.
      */
    private[builder] var pid: String = null
    /**
      * Test nested object field.
      */
    private[builder] var person: Person = null
  }

  type Hobby = Hobby.Value
  object Hobby extends Enumeration {
    val SPORT, BOOK, MUSIC = Value
  }

  final private[builder] class EmptyEnum {}

  private[builder] class Student {
    private[builder] var hobbies: util.List[Hobby] = null

    def getHobbies: util.List[JsonToStringStyleTest.Hobby] = hobbies

    def setHobbies(hobbies: util.List[JsonToStringStyleTest.Hobby]): Unit = {
      this.hobbies = hobbies
    }

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private[builder] class Teacher {
    private[builder] var hobbies: Array[Hobby] = null

    def getHobbies: Array[JsonToStringStyleTest.Hobby] = hobbies

    def setHobbies(hobbies: Array[JsonToStringStyleTest.Hobby]): Unit = {
      this.hobbies = hobbies
    }

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private[builder] class AcademyClass {
    private[builder] var teacher: Teacher = null
    private[builder] var students: util.List[Student] = null

    def setTeacher(teacher: JsonToStringStyleTest.Teacher): Unit = {
      this.teacher = teacher
    }

    def setStudents(students: util.List[JsonToStringStyleTest.Student]): Unit = {
      this.students = students
    }

    def getTeacher: JsonToStringStyleTest.Teacher = teacher

    def getStudents: util.List[JsonToStringStyleTest.Student] = students

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  /**
    * An object with a Map field used to test {@code ToStringStyle.JsonToStringStyle}.
    */
  private[builder] class InnerMapObject {
    private[builder] var pid: String = null
    /**
      * Test inner map field.
      */
    private[builder] var map: util.Map[String, AnyRef] = null
  }

}

class JsonToStringStyleTest extends JUnitSuite {
  final private val base = Integer.valueOf(5)

  @Before def setUp(): Unit = {
    ToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE)
  }

  @After def tearDown(): Unit = {
    ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE)
  }

  @Test def testNull(): Unit = {
    assertEquals("null", new ToStringBuilder(null).toString)
  }

  @Test def testBlank(): Unit = {
    assertEquals("{}", new ToStringBuilder(base).toString)
  }

  @Test def testAppendSuper(): Unit = {
    assertEquals("{}", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator + "]").toString)
    assertEquals(
      "{}",
      new ToStringBuilder(base)
        .appendSuper("Integer@8888[" + System.lineSeparator + "  null" + System.lineSeparator + "]")
        .toString)
    assertEquals(
      "{\"a\":\"hello\"}",
      new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator + "]").append("a", "hello").toString)
    assertEquals(
      "{\"a\":\"hello\"}",
      new ToStringBuilder(base)
        .appendSuper("Integer@8888[" + System.lineSeparator + "  null" + System.lineSeparator + "]")
        .append("a", "hello")
        .toString
    )
    assertEquals("{\"a\":\"hello\"}", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString)
    assertEquals(
      "{\"a\":\"hello\",\"b\":\"world\"}",
      new ToStringBuilder(base).appendSuper("{\"a\":\"hello\"}").append("b", "world").toString)
  }

  @Test def testChar(): Unit = {
    assertThrows[UnsupportedOperationException](new ToStringBuilder(base).append('A').toString)
    assertEquals("{\"a\":\"A\"}", new ToStringBuilder(base).append("a", 'A').toString)
    assertEquals("{\"a\":\"A\",\"b\":\"B\"}", new ToStringBuilder(base).append("a", 'A').append("b", 'B').toString)
  }

  @Test def testDate(): Unit = {
    val now = new Date
    val afterNow = new Date(System.currentTimeMillis + 1)
    assertThrows[UnsupportedOperationException](new ToStringBuilder(base).append(now).toString)
    assertEquals("{\"now\":\"" + now.toString + "\"}", new ToStringBuilder(base).append("now", now).toString)
    assertEquals(
      "{\"now\":\"" + now.toString + "\",\"after\":\"" + afterNow.toString + "\"}",
      new ToStringBuilder(base).append("now", now).append("after", afterNow).toString)
  }

  @Test def testObject(): Unit = {
    val i3 = Integer.valueOf(3)
    val i4 = Integer.valueOf(4)
    assertThrows[UnsupportedOperationException](new ToStringBuilder(base).append(null.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](new ToStringBuilder(base).append(i3).toString)
    assertEquals("{\"a\":null}", new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).toString)
    assertEquals("{\"a\":3}", new ToStringBuilder(base).append("a", i3).toString)
    assertEquals("{\"a\":3,\"b\":4}", new ToStringBuilder(base).append("a", i3).append("b", i4).toString)
    assertThrows[UnsupportedOperationException](new ToStringBuilder(base).append("a", i3, false).toString)
    assertThrows[UnsupportedOperationException](
      new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], false).toString)
    assertEquals("{\"a\":[]}", new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], true).toString)
    assertThrows[UnsupportedOperationException](
      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).toString)
    assertEquals("{\"a\":{}}", new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).toString)
    assertThrows[UnsupportedOperationException](
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).toString)
    assertEquals(
      "{\"a\":[]}",
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).toString)
    assertThrows[UnsupportedOperationException](
      new ToStringBuilder(base).append("a", Array[Int](1, 2, 3).asInstanceOf[Any], false).toString)
    assertEquals(
      "{\"a\":[1,2,3]}",
      new ToStringBuilder(base).append("a", Array[Int](1, 2, 3).asInstanceOf[Any], true).toString)
    assertThrows[UnsupportedOperationException](
      new ToStringBuilder(base).append("a", Array[String]("v", "x", "y", "z").asInstanceOf[Any], false).toString)
    assertEquals(
      "{\"a\":[\"v\",\"x\",\"y\",\"z\"]}",
      new ToStringBuilder(base).append("a", Array[String]("v", "x", "y", "z").asInstanceOf[Any], true).toString)
  }

  @Test def testList(): Unit = {
    val student = new JsonToStringStyleTest.Student
    val objects = new util.ArrayList[JsonToStringStyleTest.Hobby]
    objects.add(JsonToStringStyleTest.Hobby.BOOK)
    objects.add(JsonToStringStyleTest.Hobby.SPORT)
    objects.add(JsonToStringStyleTest.Hobby.MUSIC)
    student.setHobbies(objects)
    assertEquals(student.toString, "{\"hobbies\":[\"BOOK\",\"SPORT\",\"MUSIC\"]}")
    student.setHobbies(new util.ArrayList[JsonToStringStyleTest.Hobby])
    assertEquals(student.toString, "{\"hobbies\":[]}")
    student.setHobbies(null)
    assertEquals(student.toString, "{\"hobbies\":null}")
  }

  @Test def testArrayEnum(): Unit = {
    val teacher = new JsonToStringStyleTest.Teacher
    val hobbies = new Array[JsonToStringStyleTest.Hobby](3)
    hobbies(0) = JsonToStringStyleTest.Hobby.BOOK
    hobbies(1) = JsonToStringStyleTest.Hobby.SPORT
    hobbies(2) = JsonToStringStyleTest.Hobby.MUSIC
    teacher.setHobbies(hobbies)
    assertEquals(teacher.toString, "{\"hobbies\":[\"BOOK\",\"SPORT\",\"MUSIC\"]}")
    teacher.setHobbies(new Array[JsonToStringStyleTest.Hobby](0))
    assertEquals(teacher.toString, "{\"hobbies\":[]}")
    teacher.setHobbies(null)
    assertEquals(teacher.toString, "{\"hobbies\":null}")
  }

  @Test def testCombineListAndEnum(): Unit = {
    val teacher = new JsonToStringStyleTest.Teacher
    val teacherHobbies = new Array[JsonToStringStyleTest.Hobby](3)
    teacherHobbies(0) = JsonToStringStyleTest.Hobby.BOOK
    teacherHobbies(1) = JsonToStringStyleTest.Hobby.SPORT
    teacherHobbies(2) = JsonToStringStyleTest.Hobby.MUSIC
    teacher.setHobbies(teacherHobbies)
    val john = new JsonToStringStyleTest.Student
    john.setHobbies(util.Arrays.asList(JsonToStringStyleTest.Hobby.BOOK, JsonToStringStyleTest.Hobby.MUSIC))
    val alice = new JsonToStringStyleTest.Student
    alice.setHobbies(new util.ArrayList[JsonToStringStyleTest.Hobby])
    val bob = new JsonToStringStyleTest.Student
    bob.setHobbies(Collections.singletonList(JsonToStringStyleTest.Hobby.BOOK))
    val students = new util.ArrayList[JsonToStringStyleTest.Student]
    students.add(john)
    students.add(alice)
    students.add(bob)
    val academyClass = new JsonToStringStyleTest.AcademyClass
    academyClass.setStudents(students)
    academyClass.setTeacher(teacher)
    assertEquals(
      academyClass.toString,
      "{\"students\":[{\"hobbies\":[\"BOOK\",\"MUSIC\"]},{\"hobbies\":[]},{\"hobbies\":[\"BOOK\"]}],\"teacher\":{\"hobbies\":[\"BOOK\",\"SPORT\",\"MUSIC\"]}}"
    )
  }

  @Test def testPerson(): Unit = {
    val p = new Person
    p.name = "Jane Doe"
    p.age = 25
    p.smoker = true
    assertEquals(
      "{\"name\":\"Jane Doe\",\"age\":25,\"smoker\":true}",
      new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString
    )
  }

  @Test def testNestingPerson(): Unit = {
    val p = new Person() {
      override def toString: String =
        new ToStringBuilder(this)
          .append("name", this.name)
          .append("age", this.age)
          .append("smoker", this.smoker)
          .toString
    }
    p.name = "Jane Doe"
    p.age = 25
    p.smoker = true

    val nestP = new JsonToStringStyleTest.NestingPerson
    nestP.pid = "#1@Jane"
    nestP.person = p

    assertEquals(
      "{\"pid\":\"#1@Jane\",\"person\":{\"name\":\"Jane Doe\",\"age\":25,\"smoker\":true}}",
      new ToStringBuilder(nestP).append("pid", nestP.pid).append("person", nestP.person).toString
    )
  }

  @Test def testLong(): Unit = {
    assertThrows[UnsupportedOperationException](new ToStringBuilder(base).append(3L).toString)
    assertEquals("{\"a\":3}", new ToStringBuilder(base).append("a", 3L).toString)
    assertEquals("{\"a\":3,\"b\":4}", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString)
  }

  @Test def testObjectArray(): Unit = {
    val array = Array[AnyRef](null, base, Array[Int](3, 6))
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"objectArray\":[null,5,[3,6]]}", toStringBuilder.append("objectArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[AnyRef]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testLongArray(): Unit = {
    val array = Array[Long](1, 2, -3, 4)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"longArray\":[1,2,-3,4]}", toStringBuilder.append("longArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testIntArray(): Unit = {
    val array = Array[Int](1, 2, -3, 4)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"intArray\":[1,2,-3,4]}", toStringBuilder.append("intArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testByteArray(): Unit = {
    val array = Array[Byte](1, 2, -3, 4)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"byteArray\":[1,2,-3,4]}", toStringBuilder.append("byteArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testShortArray(): Unit = {
    val array = Array[Short](1, 2, -3, 4)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"shortArray\":[1,2,-3,4]}", toStringBuilder.append("shortArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testDoubleArray(): Unit = {
    val array = Array[Double](1, 2, -3, 4)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"doubleArray\":[1.0,2.0,-3.0,4.0]}", toStringBuilder.append("doubleArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testFloatArray(): Unit = {
    val array = Array[Float](1, 2, -3, 4)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"floatArray\":[1.0,2.0,-3.0,4.0]}", toStringBuilder.append("floatArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testCharArray(): Unit = {
    val array = Array[Char]('1', '2', '3', '4')
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"charArray\":[\"1\",\"2\",\"3\",\"4\"]}", toStringBuilder.append("charArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testBooleanArray(): Unit = {
    val array = Array[Boolean](true, false)
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertEquals("{\"booleanArray\":[true,false]}", toStringBuilder.append("booleanArray", array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Long]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testLongArrayArray(): Unit = {
    val array = Array[Array[Long]](Array(1, 2), null, Array(5))
    val toStringBuilder = new ToStringBuilder(base)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(null.asInstanceOf[Array[Array[Long]]]).toString)
    assertThrows[UnsupportedOperationException](toStringBuilder.append(array.asInstanceOf[Any]).toString)
    ()
  }

  @Test def testArray(): Unit = {
    val p = new Person
    p.name = "Jane Doe"
    p.age = 25
    p.smoker = true

    assertEquals(
      "{\"name\":\"Jane Doe\",\"age\":25,\"smoker\":true,\"groups\":['admin', 'manager', 'user']}",
      new ToStringBuilder(p)
        .append("name", p.name)
        .append("age", p.age)
        .append("smoker", p.smoker)
        .append(
          "groups",
          new AnyRef() {
            override def toString = "['admin', 'manager', 'user']"
          })
        .toString
    )
  }

  @Test def testLANG1395(): Unit = {
    assertEquals("{\"name\":\"value\"}", new ToStringBuilder(base).append("name", "value").toString)
    assertEquals("{\"name\":\"\"}", new ToStringBuilder(base).append("name", "").toString)
    assertEquals("{\"name\":\"\\\"\"}", new ToStringBuilder(base).append("name", '"').toString)
    assertEquals("{\"name\":\"\\\\\"}", new ToStringBuilder(base).append("name", '\\').toString)
    assertEquals(
      "{\"name\":\"Let's \\\"quote\\\" this\"}",
      new ToStringBuilder(base).append("name", "Let's \"quote\" this").toString)
  }

  @Test def testLANG1396(): Unit = {
    assertEquals(
      "{\"Let's \\\"quote\\\" this\":\"value\"}",
      new ToStringBuilder(base).append("Let's \"quote\" this", "value").toString)
  }

  @Test def testRootMap(): Unit = {
    val map = new util.LinkedHashMap[String, AnyRef]
    map.put("k1", "v1")
    map.put("k2", Integer.valueOf(2))
    assertEquals("{\"map\":{\"k1\":\"v1\",\"k2\":2}}", new ToStringBuilder(base).append("map", map).toString)
  }

  @Test def testObjectWithInnerMap(): Unit = {
    val map = new util.LinkedHashMap[String, AnyRef]
    map.put("k1", "value1")
    map.put("k2", Integer.valueOf(2))

    val `object` = new JsonToStringStyleTest.InnerMapObject() {
      override def toString: String =
        new ToStringBuilder(this).append("pid", this.pid).append("map", this.map).toString
    }
    `object`.pid = "dummy-text"
    `object`.map = map
    assertEquals(
      "{\"object\":{\"pid\":\"dummy-text\",\"map\":{\"k1\":\"value1\",\"k2\":2}}}",
      new ToStringBuilder(base).append("object", `object`).toString)
  }

  @Test def testNestedMaps(): Unit = {
    val innerMap = new util.LinkedHashMap[String, AnyRef]
    innerMap.put("k2.1", "v2.1")
    innerMap.put("k2.2", "v2.2")
    val baseMap = new util.LinkedHashMap[String, AnyRef]
    baseMap.put("k1", "v1")
    baseMap.put("k2", innerMap)
    val `object` = new JsonToStringStyleTest.InnerMapObject() {
      override def toString: String = new ToStringBuilder(this).append("pid", this.pid).append("map", this.map).toString
    }
    `object`.pid = "dummy-text"
    `object`.map = baseMap
    assertEquals(
      "{\"object\":{\"pid\":\"dummy-text\",\"map\":{\"k1\":\"v1\"," + "\"k2\":{\"k2.1\":\"v2.1\",\"k2.2\":\"v2.2\"}}}}",
      new ToStringBuilder(base).append("object", `object`).toString
    )
  }

  @Test def testMapSkipNullKey(): Unit = {
    val map = new util.LinkedHashMap[String, AnyRef]
    map.put("k1", "v1")
    map.put(null, "v2")
    assertEquals("{\"map\":{\"k1\":\"v1\"}}", new ToStringBuilder(base).append("map", map).toString)
  }
}
