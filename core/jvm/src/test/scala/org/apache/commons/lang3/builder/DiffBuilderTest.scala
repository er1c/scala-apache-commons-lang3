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
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.core.IsEqual.equalTo
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//import org.apache.commons.lang3.ArrayUtils
//import org.hamcrest.Matcher
//
///**
//  * Unit tests {@link DiffBuilder}.
//  */
//object DiffBuilderTest {
//
//  private class TypeTestClass extends Nothing {
//    private val style = SHORT_STYLE
//    private val booleanField = true
//    private val booleanArrayField = Array(true)
//    private val byteField = 0xff.toByte
//    private val byteArrayField = Array(0xff.toByte)
//    private val charField = 'a'
//    private val charArrayField = Array('a')
//    private val doubleField = 1.0
//    private val doubleArrayField = Array(1.0)
//    private val floatField = 1.0f
//    private val floatArrayField = Array(1.0f)
//    private val intField = 1
//    private val intArrayField = Array(1)
//    private val longField = 1L
//    private val longArrayField = Array(1L)
//    private val shortField = 1
//    private val shortArrayField = Array(1)
//    private val objectField = null
//    private val objectArrayField = Array(null)
//
//    def diff(obj: DiffBuilderTest.TypeTestClass): Nothing =
//      new Nothing(this, obj, style)
//        .append("boolean", booleanField, obj.booleanField)
//        .append("booleanArray", booleanArrayField, obj.booleanArrayField)
//        .append("byte", byteField, obj.byteField)
//        .append("byteArray", byteArrayField, obj.byteArrayField)
//        .append("char", charField, obj.charField)
//        .append("charArray", charArrayField, obj.charArrayField)
//        .append("double", doubleField, obj.doubleField)
//        .append("doubleArray", doubleArrayField, obj.doubleArrayField)
//        .append("float", floatField, obj.floatField)
//        .append("floatArray", floatArrayField, obj.floatArrayField)
//        .append("int", intField, obj.intField)
//        .append("intArray", intArrayField, obj.intArrayField)
//        .append("long", longField, obj.longField)
//        .append("longArray", longArrayField, obj.longArrayField)
//        .append("short", shortField, obj.shortField)
//        .append("shortArray", shortArrayField, obj.shortArrayField)
//        .append("objectField", objectField, obj.objectField)
//        .append("objectArrayField", objectArrayField, obj.objectArrayField)
//        .build
//
//    override def equals(obj: Any): Boolean = EqualsBuilder.reflectionEquals(this, obj, false)
//
//    override def hashCode: Int = HashCodeBuilder.reflectionHashCode(this, false)
//  }
//
//  private val SHORT_STYLE = ToStringStyle.SHORT_PREFIX_STYLE
//}
//
//class DiffBuilderTest extends JUnitSuite {
//  @Test def testBoolean(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.booleanField = false
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(classOf[Boolean], diff.getType)
//    assertEquals(Boolean.TRUE, diff.getLeft)
//    assertEquals(Boolean.FALSE, diff.getRight)
//  }
//
//  @Test def testBooleanArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.booleanArrayField = Array[Boolean](false, false)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.booleanArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.booleanArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testByte(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.byteField = 0x01
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Byte.valueOf(class1.byteField), diff.getLeft)
//    assertEquals(Byte.valueOf(class2.byteField), diff.getRight)
//  }
//
//  @Test def testByteArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.byteArrayField = Array[Byte](0x01, 0x02)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.byteArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.byteArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testByteArrayEqualAsObject(): Unit = {
//    val list = new Nothing("String1", "String2", DiffBuilderTest.SHORT_STYLE)
//      .append("foo", Array[Boolean](false), Array[Boolean](false))
//      .append("foo", Array[Byte](0x01), Array[Byte](0x01))
//      .append("foo", Array[Char]('a'), Array[Char]('a'))
//      .append("foo", Array[Double](1.0), Array[Double](1.0))
//      .append("foo", Array[Float](1.0f), Array[Float](1.0f))
//      .append("foo", Array[Int](1), Array[Int](1))
//      .append("foo", Array[Long](1L), Array[Long](1L))
//      .append("foo", Array[Short](1), Array[Short](1))
//      .append("foo", Array[AnyRef](1, "two"), Array[AnyRef](1, "two"))
//      .build
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def testChar(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.charField = 'z'
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Character.valueOf(class1.charField), diff.getLeft)
//    assertEquals(Character.valueOf(class2.charField), diff.getRight)
//  }
//
//  @Test def testCharArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.charArrayField = Array[Char]('f', 'o', 'o')
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.charArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.charArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testDiffResult(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.intField = 2
//    val list = new Nothing(class1, class2, DiffBuilderTest.SHORT_STYLE).append("prop1", class1.diff(class2)).build
//    assertEquals(1, list.getNumberOfDiffs)
//    assertEquals("prop1.int", list.getDiffs.get(0).getFieldName)
//  }
//
//  @Test def testDouble(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.doubleField = 99.99
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Double.valueOf(class1.doubleField), diff.getLeft)
//    assertEquals(Double.valueOf(class2.doubleField), diff.getRight)
//  }
//
//  @Test def testDoubleArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.doubleArrayField = Array[Double](3.0, 2.9, 2.8)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.doubleArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.doubleArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testFloat(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.floatField = 99.99f
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Float.valueOf(class1.floatField), diff.getLeft)
//    assertEquals(Float.valueOf(class2.floatField), diff.getRight)
//  }
//
//  @Test def testFloatArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.floatArrayField = Array[Float](3.0f, 2.9f, 2.8f)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.floatArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.floatArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testInt(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.intField = 42
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Integer.valueOf(class1.intField), diff.getLeft)
//    assertEquals(Integer.valueOf(class2.intField), diff.getRight)
//  }
//
//  @Test def testIntArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.intArrayField = Array[Int](3, 2, 1)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.intArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.intArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testLong(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.longField = 42L
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Long.valueOf(class1.longField), diff.getLeft)
//    assertEquals(Long.valueOf(class2.longField), diff.getRight)
//  }
//
//  @Test def testLongArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.longArrayField = Array[Long](3L, 2L, 1L)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.longArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.longArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testNullLhs(): Unit = {
//    assertThrows[NullPointerException](new Nothing(null, this, ToStringStyle.DEFAULT_STYLE))
//  }
//
//  @Test def testNullRhs(): Unit = {
//    assertThrows[NullPointerException](new Nothing(this, null, ToStringStyle.DEFAULT_STYLE))
//  }
//
//  @Test def testObject(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.objectField = "Some string"
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(class1.objectField, diff.getLeft)
//    assertEquals(class2.objectField, diff.getRight)
//  }
//
//  @Test def testObjectArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.objectArrayField = Array[AnyRef]("string", 1, 2)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(class1.objectArrayField, diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(class2.objectArrayField, diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testObjectArrayEqual(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class1.objectArrayField = Array[AnyRef]("string", 1, 2)
//    class2.objectArrayField = Array[AnyRef]("string", 1, 2)
//    val list = class1.diff(class2)
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  /**
//    * Test that "left" and "right" are the same instance but are equal.
//    */
//  @Test def testObjectsNotSameButEqual(): Unit = {
//    val left = new DiffBuilderTest.TypeTestClass
//    left.objectField = new Integer(1)
//    val right = new DiffBuilderTest.TypeTestClass
//    right.objectField = new Integer(1)
//    assertNotSame(left.objectField, right.objectField)
//    assertEquals(left.objectField, right.objectField)
//    val list = left.diff(right)
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  /**
//    * Test that "left" and "right" are not the same instance and are not equal.
//    */
//  @Test def testObjectsNotSameNorEqual(): Unit = {
//    val left = new DiffBuilderTest.TypeTestClass
//    left.objectField = 4
//    val right = new DiffBuilderTest.TypeTestClass
//    right.objectField = 100
//    assertNotSame(left.objectField, right.objectField)
//    assertNotEquals(left.objectField, right.objectField)
//    val list = left.diff(right)
//    assertEquals(1, list.getNumberOfDiffs)
//  }
//
//  /**
//    * Test that "left" and "right" are the same instance and are equal.
//    */
//  @Test def testObjectsSameAndEqual(): Unit = {
//    val sameObject = 1
//    val left = new DiffBuilderTest.TypeTestClass
//    left.objectField = sameObject
//    val right = new DiffBuilderTest.TypeTestClass
//    right.objectField = sameObject
//    assertSame(left.objectField, right.objectField)
//    assertEquals(left.objectField, right.objectField)
//    val list = left.diff(right)
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def testSameObjectIgnoresAppends(): Unit = {
//    val testClass = new DiffBuilderTest.TypeTestClass
//    val list = new Nothing(testClass, testClass, DiffBuilderTest.SHORT_STYLE).append("ignored", false, true).build
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def testShort(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.shortField = 42
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertEquals(Short.valueOf(class1.shortField), diff.getLeft)
//    assertEquals(Short.valueOf(class2.shortField), diff.getRight)
//  }
//
//  @Test def testShortArray(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    val class2 = new DiffBuilderTest.TypeTestClass
//    class2.shortArrayField = Array[Short](3, 2, 1)
//    val list = class1.diff(class2)
//    assertEquals(1, list.getNumberOfDiffs)
//    val diff = list.getDiffs.get(0)
//    assertArrayEquals(ArrayUtils.toObject(class1.shortArrayField), diff.getLeft.asInstanceOf[Array[AnyRef]])
//    assertArrayEquals(ArrayUtils.toObject(class2.shortArrayField), diff.getRight.asInstanceOf[Array[AnyRef]])
//  }
//
//  @Test def testSimilarObjectIgnoresAppends(): Unit = {
//    val testClass1 = new DiffBuilderTest.TypeTestClass
//    val testClass2 = new DiffBuilderTest.TypeTestClass
//    val list = new Nothing(testClass1, testClass2, DiffBuilderTest.SHORT_STYLE).append("ignored", false, true).build
//    assertEquals(0, list.getNumberOfDiffs)
//  }
//
//  @Test def testStylePassedToDiffResult(): Unit = {
//    val class1 = new DiffBuilderTest.TypeTestClass
//    var list = class1.diff(class1)
//    assertEquals(DiffBuilderTest.SHORT_STYLE, list.getToStringStyle)
//    class1.style = ToStringStyle.MULTI_LINE_STYLE
//    list = class1.diff(class1)
//    assertEquals(ToStringStyle.MULTI_LINE_STYLE, list.getToStringStyle)
//  }
//
//  @Test def testTriviallyEqualTestDisabled(): Unit = {
//    val equalToOne = equalTo(1)
//    // Constructor's arguments are not trivially equal, but not testing for that.
//    val explicitTestAndNotEqual1 = new Nothing(1, 2, null, false)
//    explicitTestAndNotEqual1.append("letter", "X", "Y")
//    assertThat(explicitTestAndNotEqual1.build.getNumberOfDiffs, equalToOne)
//    // Constructor's arguments are trivially equal, but not testing for that.
//    val explicitTestAndNotEqual2 = new Nothing(1, 1, null, false)
//    // This append(f, l, r) will not abort early.
//    explicitTestAndNotEqual2.append("letter", "X", "Y")
//    assertThat(explicitTestAndNotEqual2.build.getNumberOfDiffs, equalToOne)
//  }
//
//  @Test def testTriviallyEqualTestEnabled(): Unit = {
//    val equalToZero = equalTo(0)
//    val equalToOne = equalTo(1)
//    // The option to test if trivially equal is enabled by default.
//    val implicitTestAndEqual = new Nothing(1, 1, null)
//    // This append(f, l, r) will abort without creating a Diff for letter.
//    implicitTestAndEqual.append("letter", "X", "Y")
//    assertThat(implicitTestAndEqual.build.getNumberOfDiffs, equalToZero)
//    val implicitTestAndNotEqual = new Nothing(1, 2, null)
//    // This append(f, l, r) will not abort early
//    // because the constructor's arguments were not trivially equal.
//    implicitTestAndNotEqual.append("letter", "X", "Y")
//    assertThat(implicitTestAndNotEqual.build.getNumberOfDiffs, equalToOne)
//    // This is explicitly enabling the trivially equal test.
//    val explicitTestAndEqual = new Nothing(1, 1, null, true)
//    explicitTestAndEqual.append("letter", "X", "Y")
//    assertThat(explicitTestAndEqual.build.getNumberOfDiffs, equalToZero)
//  }
//}
