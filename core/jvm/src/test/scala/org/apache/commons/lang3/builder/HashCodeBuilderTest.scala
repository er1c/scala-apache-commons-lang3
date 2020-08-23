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
//import java.lang.{Double => JavaDouble, Float => JavaFloat}
//import org.apache.commons.lang3.void
//
//import org.junit.Assert._
//import org.junit.Test
//
///**
//  * Unit tests {@link org.apache.commons.lang3.builder.HashCodeBuilder}.
//  */
//object HashCodeBuilderTest {
//
//  /**
//    * A reflection test fixture.
//    */
//  private[builder] class ReflectionTestCycleA {
//    private[builder] var b: ReflectionTestCycleB = null
//
//    override def hashCode: Int = HashCodeBuilder.reflectionHashCode(this)
//  }
//
//  private[builder] class ReflectionTestCycleB {
//    private[builder] var a: ReflectionTestCycleA = null
//
//    override def hashCode: Int = HashCodeBuilder.reflectionHashCode(this)
//  }
//
//  private[builder] class TestObject private[builder] (var a: Int) {
//    override def equals(o: Any): Boolean = {
//      if (o.isInstanceOf[AnyRef] && (o.asInstanceOf[AnyRef] eq this)) return true
//      if (!o.isInstanceOf[HashCodeBuilderTest.TestObject]) return false
//      val rhs = o.asInstanceOf[HashCodeBuilderTest.TestObject]
//      a == rhs.a
//    }
//
//    override def hashCode: Int = a
//
//    def setA(a: Int): Unit = {
//      this.a = a
//    }
//
//    def getA: Int = a
//  }
//
//  private[builder] class TestSubObject private[builder] (a: Int) extends HashCodeBuilderTest.TestObject(a) {
//    private var b = 0
//    @SuppressWarnings(Array("unused")) private var t = 0
//    void(t)
//
//    def this(a: Int, b: Int, t: Int) {
//      this(a)
//      this.b = b
//      this.t = t
//    }
//
//    override def equals(o: Any): Boolean = {
//      if (o.isInstanceOf[AnyRef] && (o.asInstanceOf[AnyRef] eq this)) return true
//      if (!o.isInstanceOf[HashCodeBuilderTest.TestSubObject]) return false
//      val rhs = o.asInstanceOf[HashCodeBuilderTest.TestSubObject]
//      super.equals(o) && b == rhs.b
//    }
//
//    override def hashCode: Int = b * 17 + super.hashCode
//  }
//
//  private[builder] class TestObjectWithMultipleFields {
//    @SuppressWarnings(Array("unused")) private var one = 0
//    @SuppressWarnings(Array("unused")) private var two = 0
//    @SuppressWarnings(Array("unused")) private var three = 0
//
//    void(one)
//    void(two)
//    void(three)
//
//    def this(one: Int, two: Int, three: Int) = {
//      this()
//      this.one = one
//      this.two = two
//      this.three = three
//    }
//  }
//
//  private[builder] class TestObjectHashCodeExclude private[builder] (a: Int, b: Int) {
//    def getA: Int = a
//
//    def getB: Int = b
//  }
//
//  private[builder] class TestObjectHashCodeExclude2 private[builder] (a: Int, b: Int) {
//    def getA: Int = a
//
//    def getB: Int = b
//  }
//
//}
//
//class HashCodeBuilderTest {
//  @Test def testConstructorExZero(): Unit = {
//    assertThrows[IllegalArgumentException](new HashCodeBuilder(0, 0))
//    ()
//  }
//
//  @Test def testConstructorExEvenFirst(): Unit = {
//    assertThrows[IllegalArgumentException](new HashCodeBuilder(2, 3))
//    ()
//  }
//
//  @Test def testConstructorExEvenSecond(): Unit = {
//    assertThrows[IllegalArgumentException](new HashCodeBuilder(3, 2))
//    ()
//  }
//
//  @Test def testConstructorExEvenNegative(): Unit = {
//    assertThrows[IllegalArgumentException](new HashCodeBuilder(-2, -2))
//    ()
//  }
//
//  @Test def testReflectionHashCode(): Unit = {
//    assertEquals(17 * 37, HashCodeBuilder.reflectionHashCode(new HashCodeBuilderTest.TestObject(0)))
//    assertEquals(17 * 37 + 123456, HashCodeBuilder.reflectionHashCode(new HashCodeBuilderTest.TestObject(123456)))
//  }
//
//  @Test def testReflectionHierarchyHashCode(): Unit = {
//    assertEquals(17 * 37 * 37, HashCodeBuilder.reflectionHashCode(new HashCodeBuilderTest.TestSubObject(0, 0, 0)))
//    assertEquals(
//      17 * 37 * 37 * 37,
//      HashCodeBuilder.reflectionHashCode(new HashCodeBuilderTest.TestSubObject(0, 0, 0), true))
//    assertEquals(
//      (17 * 37 + 7890) * 37 + 123456,
//      HashCodeBuilder.reflectionHashCode(new HashCodeBuilderTest.TestSubObject(123456, 7890, 0)))
//    assertEquals(
//      ((17 * 37 + 7890) * 37 + 0) * 37 + 123456,
//      HashCodeBuilder.reflectionHashCode(new HashCodeBuilderTest.TestSubObject(123456, 7890, 0), true))
//  }
//
//  @Test def testReflectionHierarchyHashCodeEx1(): Unit = {
//    assertThrows[IllegalArgumentException](
//      HashCodeBuilder.reflectionHashCode(0, 0, new HashCodeBuilderTest.TestSubObject(0, 0, 0), true))
//    ()
//  }
//
//  @Test def testReflectionHierarchyHashCodeEx2(): Unit = {
//    assertThrows[IllegalArgumentException](
//      HashCodeBuilder.reflectionHashCode(2, 2, new HashCodeBuilderTest.TestSubObject(0, 0, 0), true))
//    ()
//  }
//
//  @Test def testReflectionHashCodeEx1(): Unit = {
//    assertThrows[IllegalArgumentException](
//      HashCodeBuilder.reflectionHashCode(0, 0, new HashCodeBuilderTest.TestObject(0), true))
//    ()
//  }
//
//  @Test def testReflectionHashCodeEx2(): Unit = {
//    assertThrows[IllegalArgumentException](
//      HashCodeBuilder.reflectionHashCode(2, 2, new HashCodeBuilderTest.TestObject(0), true))
//    ()
//  }
//
//  @Test def testReflectionHashCodeEx3(): Unit = {
//    assertThrows[NullPointerException](HashCodeBuilder.reflectionHashCode(13, 19, null, true))
//    ()
//  }
//
//  @Test def testSuper(): Unit = {
//    val obj = new AnyRef
//    assertEquals(
//      17 * 37 + 19 * 41 + obj.hashCode,
//      new HashCodeBuilder(17, 37).appendSuper(new HashCodeBuilder(19, 41).append(obj).toHashCode).toHashCode)
//  }
//
//  @Test def testObject(): Unit = {
//    var obj: AnyRef = null
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj = new AnyRef
//    assertEquals(17 * 37 + obj.hashCode, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//
//  @Test def testObjectBuild(): Unit = {
//    var obj: AnyRef = null
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(obj).build.intValue)
//    obj = new AnyRef
//    assertEquals(17 * 37 + obj.hashCode, new HashCodeBuilder(17, 37).append(obj).build.intValue)
//  }
//
//  @Test
//  @SuppressWarnings(Array("cast")) // cast is not really needed, keep for consistency
//  def testLong(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0L).toHashCode)
//    assertEquals(
//      17 * 37 + (123456789L ^ 123456789L >> 32).toInt,
//      new HashCodeBuilder(17, 37).append(123456789L).toHashCode)
//  }
//
//  @Test @SuppressWarnings(Array("cast")) def testInt(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0).toHashCode)
//    assertEquals(17 * 37 + 123456, new HashCodeBuilder(17, 37).append(123456).toHashCode)
//  }
//  @Test def testShort(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0.toShort).toHashCode)
//    assertEquals(17 * 37 + 12345, new HashCodeBuilder(17, 37).append(12345.toShort).toHashCode)
//  }
//  @Test def testChar(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0.toChar).toHashCode)
//    assertEquals(17 * 37 + 1234, new HashCodeBuilder(17, 37).append(1234.toChar).toHashCode)
//  }
//  @Test def testByte(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0.toByte).toHashCode)
//    assertEquals(17 * 37 + 123, new HashCodeBuilder(17, 37).append(123.toByte).toHashCode)
//  }
//  @Test @SuppressWarnings(Array("cast")) def testDouble(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0d).toHashCode)
//    val d: Double = 1234567.89
//    val l: Long = JavaDouble.doubleToLongBits(d)
//    assertEquals(17 * 37 + (l ^ l >> 32).toInt, new HashCodeBuilder(17, 37).append(d).toHashCode)
//  }
//  @Test @SuppressWarnings(Array("cast")) def testFloat(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(0f).toHashCode)
//    val f: Float = 1234.89f
//    val i: Int = JavaFloat.floatToIntBits(f)
//    assertEquals(17 * 37 + i, new HashCodeBuilder(17, 37).append(f).toHashCode)
//  }
//  @Test def testBoolean(): Unit = {
//    assertEquals(17 * 37 + 0, new HashCodeBuilder(17, 37).append(true).toHashCode)
//    assertEquals(17 * 37 + 1, new HashCodeBuilder(17, 37).append(false).toHashCode)
//  }
//  @Test def testObjectArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[AnyRef]]).toHashCode)
//    val obj: Array[AnyRef] = new Array[AnyRef](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = new AnyRef
//    assertEquals((17 * 37 + obj(0).hashCode) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = new AnyRef
//    assertEquals((17 * 37 + obj(0).hashCode) * 37 + obj(1).hashCode, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testObjectArrayAsObject(): Unit = {
//    val obj: Array[AnyRef] = new Array[AnyRef](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = new AnyRef
//    assertEquals((17 * 37 + obj(0).hashCode) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = new AnyRef
//    assertEquals(
//      (17 * 37 + obj(0).hashCode) * 37 + obj(1).hashCode,
//      new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testLongArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Long]]).toHashCode)
//    val obj: Array[Long] = new Array[Long](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5L
//    val h1: Int = (5L ^ 5L >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6L
//    val h2: Int = (6L ^ 6L >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37 + h2, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testLongArrayAsObject(): Unit = {
//    val obj: Array[Long] = new Array[Long](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5L
//    val h1: Int = (5L ^ 5L >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6L
//    val h2: Int = (6L ^ 6L >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37 + h2, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testIntArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Int]]).toHashCode)
//    val obj: Array[Int] = new Array[Int](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testIntArrayAsObject(): Unit = {
//    val obj: Array[Int] = new Array[Int](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testShortArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Short]]).toHashCode)
//    val obj: Array[Short] = new Array[Short](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5.toShort
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6.toShort
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testShortArrayAsObject(): Unit = {
//    val obj: Array[Short] = new Array[Short](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5.toShort
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6.toShort
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testCharArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Char]]).toHashCode)
//    val obj: Array[Char] = new Array[Char](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5.toChar
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6.toChar
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testCharArrayAsObject(): Unit = {
//    val obj: Array[Char] = new Array[Char](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5.toChar
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6.toChar
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testByteArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Byte]]).toHashCode)
//    val obj: Array[Byte] = new Array[Byte](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5.toByte
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6.toByte
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testByteArrayAsObject(): Unit = {
//    val obj: Array[Byte] = new Array[Byte](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5.toByte
//    assertEquals((17 * 37 + 5) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6.toByte
//    assertEquals((17 * 37 + 5) * 37 + 6, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testDoubleArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Double]]).toHashCode)
//    val obj: Array[Double] = new Array[Double](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5.4d
//    val l1: Long = JavaDouble.doubleToLongBits(5.4d)
//    val h1: Int = (l1 ^ l1 >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6.3d
//    val l2: Long = JavaDouble.doubleToLongBits(6.3d)
//    val h2: Int = (l2 ^ l2 >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37 + h2, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testDoubleArrayAsObject(): Unit = {
//    val obj: Array[Double] = new Array[Double](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5.4d
//    val l1: Long = JavaDouble.doubleToLongBits(5.4d)
//    val h1: Int = (l1 ^ l1 >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6.3d
//    val l2: Long = JavaDouble.doubleToLongBits(6.3d)
//    val h2: Int = (l2 ^ l2 >> 32).toInt
//    assertEquals((17 * 37 + h1) * 37 + h2, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testFloatArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Float]]).toHashCode)
//    val obj: Array[Float] = new Array[Float](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = 5.4f
//    val h1: Int = JavaFloat.floatToIntBits(5.4f)
//    assertEquals((17 * 37 + h1) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = 6.3f
//    val h2: Int = JavaFloat.floatToIntBits(6.3f)
//    assertEquals((17 * 37 + h1) * 37 + h2, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testFloatArrayAsObject(): Unit = {
//    val obj: Array[Float] = new Array[Float](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = 5.4f
//    val h1: Int = JavaFloat.floatToIntBits(5.4f)
//    assertEquals((17 * 37 + h1) * 37, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = 6.3f
//    val h2: Int = JavaFloat.floatToIntBits(6.3f)
//    assertEquals((17 * 37 + h1) * 37 + h2, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testBooleanArray(): Unit = {
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(null.asInstanceOf[Array[Boolean]]).toHashCode)
//    val obj: Array[Boolean] = new Array[Boolean](2)
//    assertEquals((17 * 37 + 1) * 37 + 1, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = true
//    assertEquals((17 * 37 + 0) * 37 + 1, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = false
//    assertEquals((17 * 37 + 0) * 37 + 1, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//  @Test def testBooleanArrayAsObject(): Unit = {
//    val obj: Array[Boolean] = new Array[Boolean](2)
//    assertEquals((17 * 37 + 1) * 37 + 1, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(0) = true
//    assertEquals((17 * 37 + 0) * 37 + 1, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//    obj(1) = false
//    assertEquals((17 * 37 + 0) * 37 + 1, new HashCodeBuilder(17, 37).append(obj.asInstanceOf[Any]).toHashCode)
//  }
//  @Test def testBooleanMultiArray(): Unit = {
//    val obj: Array[Array[Boolean]] = new Array[Array[Boolean]](2)
//    assertEquals(17 * 37 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = new Array[Boolean](0)
//    assertEquals(17 * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = new Array[Boolean](1)
//    assertEquals((17 * 37 + 1) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0) = new Array[Boolean](2)
//    assertEquals(((17 * 37 + 1) * 37 + 1) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(0)(0) = true
//    assertEquals(((17 * 37 + 0) * 37 + 1) * 37, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//    obj(1) = new Array[Boolean](1)
//    assertEquals(((17 * 37 + 0) * 37 + 1) * 37 + 1, new HashCodeBuilder(17, 37).append(obj).toHashCode)
//  }
//
////  @Test def testReflectionHashCodeExcludeFields(): Unit = {
////    val x: HashCodeBuilderTest.TestObjectWithMultipleFields =
////      new HashCodeBuilderTest.TestObjectWithMultipleFields(1, 2, 3)
////    assertEquals(((17 * 37 + 1) * 37 + 3) * 37 + 2, HashCodeBuilder.reflectionHashCode(x))
////    assertEquals(
////      ((17 * 37 + 1) * 37 + 3) * 37 + 2,
////      HashCodeBuilder.reflectionHashCode(x, null.asInstanceOf[Array[String]]))
////    assertEquals(((17 * 37 + 1) * 37 + 3) * 37 + 2, HashCodeBuilder.reflectionHashCode(x))
////    assertEquals(((17 * 37 + 1) * 37 + 3) * 37 + 2, HashCodeBuilder.reflectionHashCode(x, "xxx"))
////    assertEquals((17 * 37 + 1) * 37 + 3, HashCodeBuilder.reflectionHashCode(x, "two"))
////    assertEquals((17 * 37 + 1) * 37 + 2, HashCodeBuilder.reflectionHashCode(x, "three"))
////    assertEquals(17 * 37 + 1, HashCodeBuilder.reflectionHashCode(x, "two", "three"))
////    assertEquals(17, HashCodeBuilder.reflectionHashCode(x, "one", "two", "three"))
////    assertEquals(17, HashCodeBuilder.reflectionHashCode(x, "one", "two", "three", "xxx"))
////  }
//
//  /**
//    * Test Objects pointing to each other.
//    */
//  @Test def testReflectionObjectCycle(): Unit = {
//    val a: HashCodeBuilderTest.ReflectionTestCycleA = new HashCodeBuilderTest.ReflectionTestCycleA
//    val b: HashCodeBuilderTest.ReflectionTestCycleB = new HashCodeBuilderTest.ReflectionTestCycleB
//    a.b = b
//    b.a = a
//    // Used to caused:
//    // java.lang.StackOverflowError
//    // at java.lang.ClassLoader.getCallerClassLoader(Native Method)
//    // at java.lang.Class.getDeclaredFields(Class.java:992)
//    // at org.apache.commons.lang.builder.HashCodeBuilder.reflectionAppend(HashCodeBuilder.java:373)
//    // at org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode(HashCodeBuilder.java:349)
//    // at org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode(HashCodeBuilder.java:155)
//    // at
//    // org.apache.commons.lang.builder.HashCodeBuilderTest$ReflectionTestCycleB.hashCode(HashCodeBuilderTest.java:53)
//    // at org.apache.commons.lang.builder.HashCodeBuilder.append(HashCodeBuilder.java:422)
//    // at org.apache.commons.lang.builder.HashCodeBuilder.reflectionAppend(HashCodeBuilder.java:383)
//    // org.apache.commons.lang.builder.HashCodeBuilderTest$ReflectionTestCycleA.hashCode(HashCodeBuilderTest.java:42)
//    a.hashCode
//    assertNull(HashCodeBuilder.getRegistry)
//    b.hashCode
//    assertNull(HashCodeBuilder.getRegistry)
//  }
//
//  /**
//    * Ensures LANG-520 remains true
//    */
//  @Test def testToHashCodeEqualsHashCode(): Unit = {
//    val hcb: HashCodeBuilder = new HashCodeBuilder(17, 37).append(new AnyRef).append('a')
//    assertEquals(
//      "hashCode() is no longer returning the same value as toHashCode() - see LANG-520",
//      hcb.toHashCode,
//      hcb.hashCode
//    )
//  }
//
////  @Test def testToHashCodeExclude(): Unit = {
////    val one: HashCodeBuilderTest.TestObjectHashCodeExclude = new HashCodeBuilderTest.TestObjectHashCodeExclude(1, 2)
////    val two: HashCodeBuilderTest.TestObjectHashCodeExclude2 = new HashCodeBuilderTest.TestObjectHashCodeExclude2(1, 2)
////    assertEquals(17 * 37 + 2, HashCodeBuilder.reflectionHashCode(one))
////    assertEquals(17, HashCodeBuilder.reflectionHashCode(two))
////  }
//}
