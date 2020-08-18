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

import java.math.BigInteger
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.builder.CompareToBuilder}.
  */
object CompareToBuilderTest {

  private[builder] class TestObject private[builder] (var a: Int) extends Comparable[CompareToBuilderTest.TestObject] {
    override def equals(o: Any): Boolean = {
      //if (o eq this) return true
      if (o == this) return true
      if (!o.isInstanceOf[CompareToBuilderTest.TestObject]) return false
      val rhs = o.asInstanceOf[CompareToBuilderTest.TestObject]
      a == rhs.a
    }

    override def hashCode: Int = a

    def setA(a: Int): Unit = {
      this.a = a
    }

    def getA: Int = a

    override def compareTo(rhs: CompareToBuilderTest.TestObject): Int = Integer.compare(a, rhs.a)
  }

  private[builder] class TestSubObject private[builder] (a: Int) extends CompareToBuilderTest.TestObject(a) {
    private var b = 0

    def this() {
      this(0)
    }

    def this(a: Int, b: Int) {
      this(a)
      this.b = b
    }

    override def equals(o: Any): Boolean = {
      if (o == this) return true
      if (!o.isInstanceOf[CompareToBuilderTest.TestSubObject]) return false
      val rhs = o.asInstanceOf[CompareToBuilderTest.TestSubObject]
      super.equals(o) && b == rhs.b
    }
  }

  private[builder] class TestTransientSubObject private[builder] (a: Int, var t: Int)
    extends CompareToBuilderTest.TestObject(a) {}

}

class CompareToBuilderTest extends JUnitSuite {
  @Test def testReflectionCompare(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    val o2 = new CompareToBuilderTest.TestObject(4)
    assertEquals(0, CompareToBuilder.reflectionCompare(o1, o1))
    assertEquals(0, CompareToBuilder.reflectionCompare(o1, o2))
    o2.setA(5)
    assertTrue(CompareToBuilder.reflectionCompare(o1, o2) < 0)
    assertTrue(CompareToBuilder.reflectionCompare(o2, o1) > 0)
  }

  @Test def testReflectionCompareEx1(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    assertThrows[NullPointerException](CompareToBuilder.reflectionCompare(o1, null))
    ()
  }

  @Test def testReflectionCompareEx2(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    val o2 = new AnyRef
    assertThrows[ClassCastException](CompareToBuilder.reflectionCompare(o1, o2))
    ()
  }

  @Test def testReflectionHierarchyCompare(): Unit = {
    testReflectionHierarchyCompare(false, null)
  }

  @Test def testReflectionHierarchyCompareExcludeFields(): Unit = {
    val excludeFields = Array[String]("b")
    testReflectionHierarchyCompare(true, excludeFields)
    var x: CompareToBuilderTest.TestSubObject = null
    var y: CompareToBuilderTest.TestSubObject = null
    var z: CompareToBuilderTest.TestSubObject = null
    x = new CompareToBuilderTest.TestSubObject(1, 1)
    y = new CompareToBuilderTest.TestSubObject(2, 1)
    z = new CompareToBuilderTest.TestSubObject(3, 1)
    assertXYZCompareOrder(x, y, z, true, excludeFields)
    x = new CompareToBuilderTest.TestSubObject(1, 3)
    y = new CompareToBuilderTest.TestSubObject(2, 2)
    z = new CompareToBuilderTest.TestSubObject(3, 1)
    assertXYZCompareOrder(x, y, z, true, excludeFields)
  }

  @Test def testReflectionHierarchyCompareTransients(): Unit = {
    testReflectionHierarchyCompare(true, null)
    var x: CompareToBuilderTest.TestTransientSubObject = null
    var y: CompareToBuilderTest.TestTransientSubObject = null
    var z: CompareToBuilderTest.TestTransientSubObject = null
    x = new CompareToBuilderTest.TestTransientSubObject(1, 1)
    y = new CompareToBuilderTest.TestTransientSubObject(2, 2)
    z = new CompareToBuilderTest.TestTransientSubObject(3, 3)
    assertXYZCompareOrder(x, y, z, true, null)
    x = new CompareToBuilderTest.TestTransientSubObject(1, 1)
    y = new CompareToBuilderTest.TestTransientSubObject(1, 2)
    z = new CompareToBuilderTest.TestTransientSubObject(1, 3)
    assertXYZCompareOrder(x, y, z, true, null)
  }

  private def assertXYZCompareOrder(
    x: Any,
    y: Any,
    z: Any,
    testTransients: Boolean,
    excludeFields: Array[String]
  ): Unit = {
    assertEquals(
      0,
      CompareToBuilder.reflectionCompare(x, x, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertEquals(
      0,
      CompareToBuilder.reflectionCompare(y, y, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertEquals(
      0,
      CompareToBuilder.reflectionCompare(z, z, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertTrue(0 > CompareToBuilder.reflectionCompare(x, y, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertTrue(0 > CompareToBuilder.reflectionCompare(x, z, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertTrue(0 > CompareToBuilder.reflectionCompare(y, z, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertTrue(0 < CompareToBuilder.reflectionCompare(y, x, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertTrue(0 < CompareToBuilder.reflectionCompare(z, x, testTransients, null.asInstanceOf[Class[_]], excludeFields))
    assertTrue(0 < CompareToBuilder.reflectionCompare(z, y, testTransients, null.asInstanceOf[Class[_]], excludeFields))
  }

  private def testReflectionHierarchyCompare(testTransients: Boolean, excludeFields: Array[String]): Unit = {
    val to1 = new CompareToBuilderTest.TestObject(1)
    val to2 = new CompareToBuilderTest.TestObject(2)
    val to3 = new CompareToBuilderTest.TestObject(3)
    val tso1 = new CompareToBuilderTest.TestSubObject(1, 1)
    val tso2 = new CompareToBuilderTest.TestSubObject(2, 2)
    val tso3 = new CompareToBuilderTest.TestSubObject(3, 3)
    assertReflectionCompareContract(to1, to1, to1, false, excludeFields)
    assertReflectionCompareContract(to1, to2, to3, false, excludeFields)
    assertReflectionCompareContract(tso1, tso1, tso1, false, excludeFields)
    assertReflectionCompareContract(tso1, tso2, tso3, false, excludeFields)
    assertReflectionCompareContract("1", "2", "3", false, excludeFields)
    assertTrue(
      0 != CompareToBuilder.reflectionCompare(tso1, new CompareToBuilderTest.TestSubObject(1, 0), testTransients))
    assertTrue(
      0 != CompareToBuilder.reflectionCompare(tso1, new CompareToBuilderTest.TestSubObject(0, 1), testTransients))
    // root class
    assertXYZCompareOrder(to1, to2, to3, true, null)
    // subclass
    assertXYZCompareOrder(tso1, tso2, tso3, true, null)
  }

  /**
    * See "Effective Java" under "Consider Implementing Comparable".
    *
    * @param x              an object to compare
    * @param y              an object to compare
    * @param z              an object to compare
    * @param testTransients Whether to include transients in the comparison
    * @param excludeFields  fields to exclude
    */
  private def assertReflectionCompareContract(
    x: Any,
    y: Any,
    z: Any,
    testTransients: Boolean,
    excludeFields: Array[String]
  ): Unit = { // signum
    assertEquals(
      reflectionCompareSignum(x, y, testTransients, excludeFields),
      -reflectionCompareSignum(y, x, testTransients, excludeFields)
    )
    // transitive
    if (CompareToBuilder.reflectionCompare(x, y, testTransients, null, excludeFields) > 0 &&
      CompareToBuilder.reflectionCompare(y, z, testTransients, null, excludeFields: _*) > 0)
      assertTrue(CompareToBuilder.reflectionCompare(x, z, testTransients, null, excludeFields) > 0)
    // un-named
    if (CompareToBuilder.reflectionCompare(x, y, testTransients, null, excludeFields) == 0)
      assertEquals(
        reflectionCompareSignum(x, z, testTransients, excludeFields),
        -reflectionCompareSignum(y, z, testTransients, excludeFields))
    // strongly recommended but not strictly required
    assertTrue(
      CompareToBuilder.reflectionCompare(x, y, testTransients) == 0 == EqualsBuilder
        .reflectionEquals(x, y, testTransients)
    )
  }

  /**
    * Returns the signum of the result of comparing x and y with
    * {@code CompareToBuilder.reflectionCompare}
    *
    * @param lhs            The "left-hand-side" of the comparison.
    * @param rhs            The "right-hand-side" of the comparison.
    * @param testTransients Whether to include transients in the comparison
    * @param excludeFields  fields to exclude
    * @return int The signum
    */
  private def reflectionCompareSignum(lhs: Any, rhs: Any, testTransients: Boolean, excludeFields: Array[String]) =
    BigInteger.valueOf(CompareToBuilder.reflectionCompare(lhs, rhs, testTransients, null, excludeFields)).signum

  @Test def testAppendSuper(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    val o2 = new CompareToBuilderTest.TestObject(5)
    assertEquals(0, new CompareToBuilder().appendSuper(0).append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().appendSuper(0).append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().appendSuper(0).append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().appendSuper(-1).append(o1, o1).toComparison < 0)
    assertTrue(new CompareToBuilder().appendSuper(-1).append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().appendSuper(1).append(o1, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().appendSuper(1).append(o1, o2).toComparison > 0)
  }

  @Test def testObject(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    val o2 = new CompareToBuilderTest.TestObject(4)
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertEquals(0, new CompareToBuilder().append(o1, o2).toComparison)
    o2.setA(5)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Any], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, o1).toComparison < 0)
  }

  @Test def testObjectBuild(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    val o2 = new CompareToBuilderTest.TestObject(4)
    assertEquals(Integer.valueOf(0), new CompareToBuilder().append(o1, o1).build)
    assertEquals(Integer.valueOf(0), new CompareToBuilder().append(o1, o2).build)
    o2.setA(5)
    assertTrue(new CompareToBuilder().append(o1, o2).build.intValue < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).build.intValue > 0)
    assertTrue(new CompareToBuilder().append(o1, null).build.intValue > 0)
    assertEquals(
      Integer.valueOf(0),
      new CompareToBuilder().append(null.asInstanceOf[Any], null.asInstanceOf[Any]).build)
    assertTrue(new CompareToBuilder().append(null, o1).build.intValue < 0)
  }

  @Test def testObjectEx2(): Unit = {
    val o1 = new CompareToBuilderTest.TestObject(4)
    val o2 = new AnyRef
    assertThrows[ClassCastException](new CompareToBuilder().append(o1, o2))
    ()
  }

  @Test def testObjectComparator(): Unit = {
    val o1 = "Fred"
    var o2 = "Fred"
    assertEquals(0, new CompareToBuilder().append(o1, o1, String.CASE_INSENSITIVE_ORDER).toComparison)
    assertEquals(0, new CompareToBuilder().append(o1, o2, String.CASE_INSENSITIVE_ORDER).toComparison)
    o2 = "FRED"
    assertEquals(0, new CompareToBuilder().append(o1, o2, String.CASE_INSENSITIVE_ORDER).toComparison)
    assertEquals(0, new CompareToBuilder().append(o2, o1, String.CASE_INSENSITIVE_ORDER).toComparison)
    o2 = "FREDA"
    assertTrue(new CompareToBuilder().append(o1, o2, String.CASE_INSENSITIVE_ORDER).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1, String.CASE_INSENSITIVE_ORDER).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, null, String.CASE_INSENSITIVE_ORDER).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null, null, String.CASE_INSENSITIVE_ORDER).toComparison)
    assertTrue(new CompareToBuilder().append(null, o1, String.CASE_INSENSITIVE_ORDER).toComparison < 0)
  }

  @Test def testObjectComparatorNull(): Unit = {
    val o1 = "Fred"
    var o2 = "Fred"
    assertEquals(0, new CompareToBuilder().append(o1, o1, null).toComparison)
    assertEquals(0, new CompareToBuilder().append(o1, o2, null).toComparison)
    o2 = "Zebra"
    assertTrue(new CompareToBuilder().append(o1, o2, null).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1, null).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, null, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null, null, null).toComparison)
    assertTrue(new CompareToBuilder().append(null, o1, null).toComparison < 0)
  }

  @Test def testLong(): Unit = {
    val o1 = 1L
    val o2 = 2L
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Long.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Long.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Long.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Long.MinValue, o1).toComparison < 0)
  }

  @Test def testInt(): Unit = {
    val o1 = 1
    val o2 = 2
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Int.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Int.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Int.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Int.MinValue, o1).toComparison < 0)
  }

  @Test def testShort(): Unit = {
    val o1 = 1
    val o2 = 2
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Short.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Short.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Short.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Short.MinValue, o1).toComparison < 0)
  }

  @Test def testChar(): Unit = {
    val o1 = 1
    val o2 = 2
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Char.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Char.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Char.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Char.MinValue, o1).toComparison < 0)
  }

  @Test def testByte(): Unit = {
    val o1 = 1
    val o2 = 2
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Byte.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Byte.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Byte.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Byte.MinValue, o1).toComparison < 0)
  }

  @Test def testDouble(): Unit = {
    val o1 = 1
    val o2 = 2
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Double.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Double.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Double.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Double.MinValue, o1).toComparison < 0)
    assertEquals(0, new CompareToBuilder().append(Double.NaN, Double.NaN).toComparison)
    assertTrue(new CompareToBuilder().append(Double.NaN, Double.MaxValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Double.PositiveInfinity, Double.MaxValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Double.NegativeInfinity, Double.MinValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o1, Double.NaN).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Double.NaN, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(-0.0, 0.0).toComparison < 0)
    assertTrue(new CompareToBuilder().append(0.0, -0.0).toComparison > 0)
  }

  @Test def testFloat(): Unit = {
    val o1 = 1
    val o2 = 2
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Float.MaxValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Float.MaxValue, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o1, Float.MinValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Float.MinValue, o1).toComparison < 0)
    assertEquals(0, new CompareToBuilder().append(Float.NaN, Float.NaN).toComparison)
    assertTrue(new CompareToBuilder().append(Float.NaN, Float.MaxValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Float.PositiveInfinity, Float.MaxValue).toComparison > 0)
    assertTrue(new CompareToBuilder().append(Float.NegativeInfinity, Float.MinValue).toComparison < 0)
    assertTrue(new CompareToBuilder().append(o1, Float.NaN).toComparison < 0)
    assertTrue(new CompareToBuilder().append(Float.NaN, o1).toComparison > 0)
    assertTrue(new CompareToBuilder().append(-0.0, 0.0).toComparison < 0)
    assertTrue(new CompareToBuilder().append(0.0, -0.0).toComparison > 0)
  }

  @Test def testBoolean(): Unit = {
    val o1 = true
    val o2 = false
    assertEquals(0, new CompareToBuilder().append(o1, o1).toComparison)
    assertEquals(0, new CompareToBuilder().append(o2, o2).toComparison)
    assertTrue(new CompareToBuilder().append(o1, o2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(o2, o1).toComparison < 0)
  }

  @Test def testObjectArray(): Unit = {
    val obj1 = new Array[CompareToBuilderTest.TestObject](2)
    obj1(0) = new CompareToBuilderTest.TestObject(4)
    obj1(1) = new CompareToBuilderTest.TestObject(5)
    val obj2 = new Array[CompareToBuilderTest.TestObject](2)
    obj2(0) = new CompareToBuilderTest.TestObject(4)
    obj2(1) = new CompareToBuilderTest.TestObject(5)
    val obj3 = new Array[CompareToBuilderTest.TestObject](3)
    obj3(0) = new CompareToBuilderTest.TestObject(4)
    obj3(1) = new CompareToBuilderTest.TestObject(5)
    obj3(2) = new CompareToBuilderTest.TestObject(6)
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = new CompareToBuilderTest.TestObject(7)
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[AnyRef]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testLongArray(): Unit = {
    val obj1 = new Array[Long](2)
    obj1(0) = 5L
    obj1(1) = 6L
    val obj2 = new Array[Long](2)
    obj2(0) = 5L
    obj2(1) = 6L
    val obj3 = new Array[Long](3)
    obj3(0) = 5L
    obj3(1) = 6L
    obj3(2) = 7L
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Long]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testIntArray(): Unit = {
    val obj1 = new Array[Int](2)
    obj1(0) = 5
    obj1(1) = 6
    val obj2 = new Array[Int](2)
    obj2(0) = 5
    obj2(1) = 6
    val obj3 = new Array[Int](3)
    obj3(0) = 5
    obj3(1) = 6
    obj3(2) = 7
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Int]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testShortArray(): Unit = {
    val obj1 = new Array[Short](2)
    obj1(0) = 5
    obj1(1) = 6
    val obj2 = new Array[Short](2)
    obj2(0) = 5
    obj2(1) = 6
    val obj3 = new Array[Short](3)
    obj3(0) = 5
    obj3(1) = 6
    obj3(2) = 7
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Short]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testCharArray(): Unit = {
    val obj1 = new Array[Char](2)
    obj1(0) = 5
    obj1(1) = 6
    val obj2 = new Array[Char](2)
    obj2(0) = 5
    obj2(1) = 6
    val obj3 = new Array[Char](3)
    obj3(0) = 5
    obj3(1) = 6
    obj3(2) = 7
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Char]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testByteArray(): Unit = {
    val obj1 = new Array[Byte](2)
    obj1(0) = 5
    obj1(1) = 6
    val obj2 = new Array[Byte](2)
    obj2(0) = 5
    obj2(1) = 6
    val obj3 = new Array[Byte](3)
    obj3(0) = 5
    obj3(1) = 6
    obj3(2) = 7
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Byte]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testDoubleArray(): Unit = {
    val obj1 = new Array[Double](2)
    obj1(0) = 5
    obj1(1) = 6
    val obj2 = new Array[Double](2)
    obj2(0) = 5
    obj2(1) = 6
    val obj3 = new Array[Double](3)
    obj3(0) = 5
    obj3(1) = 6
    obj3(2) = 7
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Double]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testFloatArray(): Unit = {
    val obj1 = new Array[Float](2)
    obj1(0) = 5
    obj1(1) = 6
    val obj2 = new Array[Float](2)
    obj2(0) = 5
    obj2(1) = 6
    val obj3 = new Array[Float](3)
    obj3(0) = 5
    obj3(1) = 6
    obj3(2) = 7
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Float]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testBooleanArray(): Unit = {
    val obj1 = new Array[Boolean](2)
    obj1(0) = true
    obj1(1) = false
    val obj2 = new Array[Boolean](2)
    obj2(0) = true
    obj2(1) = false
    val obj3 = new Array[Boolean](3)
    obj3(0) = true
    obj3(1) = false
    obj3(2) = true
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    obj1(1) = true
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj1, null).toComparison > 0)
    assertEquals(0, new CompareToBuilder().append(null.asInstanceOf[Array[Boolean]], null).toComparison)
    assertTrue(new CompareToBuilder().append(null, obj1).toComparison < 0)
  }

  @Test def testMultiLongArray(): Unit = {
    val array1 = Array.ofDim[Long](2, 2)
    val array2 = Array.ofDim[Long](2, 2)
    val array3 = Array.ofDim[Long](2, 3)
    for (i <- 0 until array1.length) {
      for (j <- 0 until array1(0).length) {
        array1(i)(j) = (i + 1) * (j + 1)
        array2(i)(j) = (i + 1) * (j + 1)
        array3(i)(j) = (i + 1) * (j + 1)
      }
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 200
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiIntArray(): Unit = {
    val array1 = Array.ofDim[Int](2, 2)
    val array2 = Array.ofDim[Int](2, 2)
    val array3 = Array.ofDim[Int](2, 3)
    for (i <- 0 until array1.length) {
      for (j <- 0 until array1(0).length) {
        array1(i)(j) = (i + 1) * (j + 1)
        array2(i)(j) = (i + 1) * (j + 1)
        array3(i)(j) = (i + 1) * (j + 1)
      }
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 200
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiShortArray(): Unit = {
    val array1 = Array.ofDim[Short](2, 2)
    val array2 = Array.ofDim[Short](2, 2)
    val array3 = Array.ofDim[Short](2, 3)
    var i = 0
    while ({
      i < array1.length
    }) {
      var j = 0
      while ({
        j < array1(0).length
      }) {
        array1(i)(j) = ((i + 1) * (j + 1)).toShort
        array2(i)(j) = ((i + 1) * (j + 1)).toShort
        array3(i)(j) = ((i + 1) * (j + 1)).toShort

        j += 1
      }

      i += 1
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 200
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiCharArray(): Unit = {
    val array1 = Array.ofDim[Char](2, 2)
    val array2 = Array.ofDim[Char](2, 2)
    val array3 = Array.ofDim[Char](2, 3)
    var i = 0
    while ({
      i < array1.length
    }) {
      var j = 0
      while ({
        j < array1(0).length
      }) {
        array1(i)(j) = ((i + 1) * (j + 1)).toChar
        array2(i)(j) = ((i + 1) * (j + 1)).toChar
        array3(i)(j) = ((i + 1) * (j + 1)).toChar

        j += 1
      }

      i += 1
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 200
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiByteArray(): Unit = {
    val array1 = Array.ofDim[Byte](2, 2)
    val array2 = Array.ofDim[Byte](2, 2)
    val array3 = Array.ofDim[Byte](2, 3)
    var i = 0
    while ({
      i < array1.length
    }) {
      var j = 0
      while ({
        j < array1(0).length
      }) {
        array1(i)(j) = ((i + 1) * (j + 1)).toByte
        array2(i)(j) = ((i + 1) * (j + 1)).toByte
        array3(i)(j) = ((i + 1) * (j + 1)).toByte

        j += 1
      }

      i += 1
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 127
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiFloatArray(): Unit = {
    val array1 = Array.ofDim[Float](2, 2)
    val array2 = Array.ofDim[Float](2, 2)
    val array3 = Array.ofDim[Float](2, 3)
    for (i <- 0 until array1.length) {
      for (j <- 0 until array1(0).length) {
        array1(i)(j) = (i + 1) * (j + 1)
        array2(i)(j) = (i + 1) * (j + 1)
        array3(i)(j) = (i + 1) * (j + 1)
      }
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 127
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiDoubleArray(): Unit = {
    val array1 = Array.ofDim[Double](2, 2)
    val array2 = Array.ofDim[Double](2, 2)
    val array3 = Array.ofDim[Double](2, 3)
    for (i <- 0 until array1.length) {
      for (j <- 0 until array1(0).length) {
        array1(i)(j) = (i + 1) * (j + 1)
        array2(i)(j) = (i + 1) * (j + 1)
        array3(i)(j) = (i + 1) * (j + 1)
      }
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 127
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMultiBooleanArray(): Unit = {
    val array1 = Array.ofDim[Boolean](2, 2)
    val array2 = Array.ofDim[Boolean](2, 2)
    val array3 = Array.ofDim[Boolean](2, 3)
    for (i <- 0 until array1.length) {
      for (j <- 0 until array1(0).length) {
        array1(i)(j) = i == 1 ^ j == 1
        array2(i)(j) = i == 1 ^ j == 1
        array3(i)(j) = i == 1 ^ j == 1
      }
    }
    array3(1)(2) = false
    array3(1)(2) = false
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = true
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testRaggedArray(): Unit = {
    val array1 = new Array[Array[Long]](2)
    val array2 = new Array[Array[Long]](2)
    val array3 = new Array[Array[Long]](3)
    for (i <- 0 until array1.length) {
      array1(i) = new Array[Long](2)
      array2(i) = new Array[Long](2)
      array3(i) = new Array[Long](3)
      for (j <- 0 until array1(i).length) {
        array1(i)(j) = (i + 1) * (j + 1)
        array2(i)(j) = (i + 1) * (j + 1)
        array3(i)(j) = (i + 1) * (j + 1)
      }
    }
    array3(1)(2) = 100
    array3(1)(2) = 100
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1)(1) = 200
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testMixedArray(): Unit = {
    val array1 = new Array[AnyRef](2)
    val array2 = new Array[AnyRef](2)
    val array3 = new Array[AnyRef](2)
    for (i <- 0 until array1.length) {
      array1(i) = new Array[Long](2)
      array2(i) = new Array[Long](2)
      array3(i) = new Array[Long](3)
      for (j <- 0 until 2) {
        array1(i).asInstanceOf[Array[Long]](j) = (i + 1) * (j + 1)
        array2(i).asInstanceOf[Array[Long]](j) = (i + 1) * (j + 1)
        array3(i).asInstanceOf[Array[Long]](j) = (i + 1) * (j + 1)
      }
    }
    array3(0).asInstanceOf[Array[Long]](2) = 1
    array3(1).asInstanceOf[Array[Long]](2) = 1
    assertEquals(0, new CompareToBuilder().append(array1, array1).toComparison)
    assertEquals(0, new CompareToBuilder().append(array1, array2).toComparison)
    assertTrue(new CompareToBuilder().append(array1, array3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(array3, array1).toComparison > 0)
    array1(1).asInstanceOf[Array[Long]](1) = 200
    assertTrue(new CompareToBuilder().append(array1, array2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(array2, array1).toComparison < 0)
  }

  @Test def testObjectArrayHiddenByObject(): Unit = {
    val array1 = new Array[CompareToBuilderTest.TestObject](2)
    array1(0) = new CompareToBuilderTest.TestObject(4)
    array1(1) = new CompareToBuilderTest.TestObject(5)
    val array2 = new Array[CompareToBuilderTest.TestObject](2)
    array2(0) = new CompareToBuilderTest.TestObject(4)
    array2(1) = new CompareToBuilderTest.TestObject(5)
    val array3 = new Array[CompareToBuilderTest.TestObject](3)
    array3(0) = new CompareToBuilderTest.TestObject(4)
    array3(1) = new CompareToBuilderTest.TestObject(5)
    array3(2) = new CompareToBuilderTest.TestObject(6)
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = new CompareToBuilderTest.TestObject(7)
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testLongArrayHiddenByObject(): Unit = {
    val array1 = new Array[Long](2)
    array1(0) = 5L
    array1(1) = 6L
    val array2 = new Array[Long](2)
    array2(0) = 5L
    array2(1) = 6L
    val array3 = new Array[Long](3)
    array3(0) = 5L
    array3(1) = 6L
    array3(2) = 7L
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testIntArrayHiddenByObject(): Unit = {
    val array1 = new Array[Int](2)
    array1(0) = 5
    array1(1) = 6
    val array2 = new Array[Int](2)
    array2(0) = 5
    array2(1) = 6
    val array3 = new Array[Int](3)
    array3(0) = 5
    array3(1) = 6
    array3(2) = 7
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testShortArrayHiddenByObject(): Unit = {
    val array1 = new Array[Short](2)
    array1(0) = 5
    array1(1) = 6
    val array2 = new Array[Short](2)
    array2(0) = 5
    array2(1) = 6
    val array3 = new Array[Short](3)
    array3(0) = 5
    array3(1) = 6
    array3(2) = 7
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testCharArrayHiddenByObject(): Unit = {
    val array1 = new Array[Char](2)
    array1(0) = 5
    array1(1) = 6
    val array2 = new Array[Char](2)
    array2(0) = 5
    array2(1) = 6
    val array3 = new Array[Char](3)
    array3(0) = 5
    array3(1) = 6
    array3(2) = 7
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testByteArrayHiddenByObject(): Unit = {
    val array1 = new Array[Byte](2)
    array1(0) = 5
    array1(1) = 6
    val array2 = new Array[Byte](2)
    array2(0) = 5
    array2(1) = 6
    val array3 = new Array[Byte](3)
    array3(0) = 5
    array3(1) = 6
    array3(2) = 7
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testDoubleArrayHiddenByObject(): Unit = {
    val array1 = new Array[Double](2)
    array1(0) = 5
    array1(1) = 6
    val array2 = new Array[Double](2)
    array2(0) = 5
    array2(1) = 6
    val array3 = new Array[Double](3)
    array3(0) = 5
    array3(1) = 6
    array3(2) = 7
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testFloatArrayHiddenByObject(): Unit = {
    val array1 = new Array[Float](2)
    array1(0) = 5
    array1(1) = 6
    val array2 = new Array[Float](2)
    array2(0) = 5
    array2(1) = 6
    val array3 = new Array[Float](3)
    array3(0) = 5
    array3(1) = 6
    array3(2) = 7
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = 7
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }

  @Test def testBooleanArrayHiddenByObject(): Unit = {
    val array1 = new Array[Boolean](2)
    array1(0) = true
    array1(1) = false
    val array2 = new Array[Boolean](2)
    array2(0) = true
    array2(1) = false
    val array3 = new Array[Boolean](3)
    array3(0) = true
    array3(1) = false
    array3(2) = true
    val obj1 = array1
    val obj2 = array2
    val obj3 = array3
    assertEquals(0, new CompareToBuilder().append(obj1, obj1).toComparison)
    assertEquals(0, new CompareToBuilder().append(obj1, obj2).toComparison)
    assertTrue(new CompareToBuilder().append(obj1, obj3).toComparison < 0)
    assertTrue(new CompareToBuilder().append(obj3, obj1).toComparison > 0)
    array1(1) = true
    assertTrue(new CompareToBuilder().append(obj1, obj2).toComparison > 0)
    assertTrue(new CompareToBuilder().append(obj2, obj1).toComparison < 0)
  }
}
