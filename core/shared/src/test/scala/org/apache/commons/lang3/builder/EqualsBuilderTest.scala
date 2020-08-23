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
//import java.lang.reflect.Method
//import org.apache.commons.lang3.reflect.MethodUtils
//
///**
//  * Unit tests {@link org.apache.commons.lang3.builder.EqualsBuilder}.
//  */
//object EqualsBuilderTest {
//
//  private[builder] class TestObject private[builder] () {
//    private var a = 0
//
//    def this(a: Int) {
//      this()
//      this.a = a
//    }
//
//    override def equals(o: Any): Boolean = {
//      if (o == null) return false
//      if (o eq this) return true
//      if (o.getClass ne getClass) return false
//      val rhs = o.asInstanceOf[EqualsBuilderTest.TestObject]
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
//  private[builder] class TestSubObject private[builder] (a: Int) extends EqualsBuilderTest.TestObject(a) {
//    private var b = 0
//
//    def this(a: Int, b: Int) {
//      this(a)
//      this.b = b
//    }
//
//    override def equals(o: Any): Boolean = {
//      if (o == null) return false
//      if (o eq this) return true
//      if (o.getClass ne getClass) return false
//      val rhs = o.asInstanceOf[EqualsBuilderTest.TestSubObject]
//      super.equals(o) && b == rhs.b
//    }
//
//    override def hashCode: Int = b * 17 + super.hashCode
//
//    def setB(b: Int): Unit = {
//      this.b = b
//    }
//
//    def getB: Int = b
//  }
//
//  private[builder] class TestEmptySubObject private[builder] (val a: Int) extends EqualsBuilderTest.TestObject(a) {}
//
//  private[builder] class TestTSubObject private[builder] (val a: Int, var t: Int)
//    extends EqualsBuilderTest.TestObject(a) {}
//
//  private[builder] class TestTTSubObject private[builder] (val a: Int, val t: Int, var tt: Int)
//    extends EqualsBuilderTest.TestTSubObject(a, t) {}
//
//  private[builder] class TestTTLeafObject private[builder] (val a: Int, val t: Int, val tt: Int, val leafValue: Int)
//    extends EqualsBuilderTest.TestTTSubObject(a, t, tt) {}
//
//  private[builder] class TestTSubObject2 private[builder] (val a: Int, val t: Int)
//    extends EqualsBuilderTest.TestObject(a) {
//    private var t = 0
//
//    def getT: Int = t
//
//    def setT(t: Int): Unit = {
//      this.t = t
//    }
//  }
//
//  private[builder] class TestRecursiveGenericObject[T] private[builder] (val a: T) {
//    def getA: T = a
//  }
//
//  private[builder] class TestRecursiveObject private[builder] (
//    val a: EqualsBuilderTest.TestRecursiveInnerObject,
//    val b: EqualsBuilderTest.TestRecursiveInnerObject,
//    val z: Int) {
//    private val z = 0
//
//    def getA: EqualsBuilderTest.TestRecursiveInnerObject = a
//
//    def getB: EqualsBuilderTest.TestRecursiveInnerObject = b
//
//    def getZ: Int = z
//  }
//
//  private[builder] class TestRecursiveInnerObject private[builder] (val n: Int) {
//    def getN: Int = n
//  }
//
//  private[builder] class TestRecursiveCycleObject {
//    private var cycle = null
//    final private var n = 0
//
//    def this(n: Int) {
//      this()
//      this.n = n
//      this.cycle = this
//    }
//
//    def this(cycle: EqualsBuilderTest.TestRecursiveCycleObject, n: Int) {
//      this()
//      this.n = n
//      this.cycle = cycle
//    }
//
//    def getN: Int = n
//
//    def getCycle: EqualsBuilderTest.TestRecursiveCycleObject = cycle
//
//    def setCycle(cycle: EqualsBuilderTest.TestRecursiveCycleObject): Unit = {
//      this.cycle = cycle
//    }
//  }
//
//  class TestACanEqualB(val a: Int) {
//    override def equals(o: Any): Boolean = {
//      if (o eq this) return true
//      if (o.isInstanceOf[EqualsBuilderTest.TestACanEqualB])
//        return this.a == o.asInstanceOf[EqualsBuilderTest.TestACanEqualB].getA
//      if (o.isInstanceOf[EqualsBuilderTest.TestBCanEqualA])
//        return this.a == o.asInstanceOf[EqualsBuilderTest.TestBCanEqualA].getB
//      false
//    }
//
//    override def hashCode: Int = a
//
//    def getA: Int = this.a
//  }
//
//  class TestBCanEqualA(val b: Int) {
//    override def equals(o: Any): Boolean = {
//      if (o eq this) return true
//      if (o.isInstanceOf[EqualsBuilderTest.TestACanEqualB])
//        return this.b == o.asInstanceOf[EqualsBuilderTest.TestACanEqualB].getA
//      if (o.isInstanceOf[EqualsBuilderTest.TestBCanEqualA])
//        return this.b == o.asInstanceOf[EqualsBuilderTest.TestBCanEqualA].getB
//      false
//    }
//
//    override def hashCode: Int = b
//
//    def getB: Int = this.b
//  }
//
//  private[builder] class TestObjectWithMultipleFields private[builder] (val one: Int, val two: Int, val three: Int) {
//    this.one = new EqualsBuilderTest.TestObject(one)
//    this.two = new EqualsBuilderTest.TestObject(two)
//    this.three = new EqualsBuilderTest.TestObject(three)
//    @SuppressWarnings(Array("unused")) final private var one = null
//    @SuppressWarnings(Array("unused")) final private var two = null
//    @SuppressWarnings(Array("unused")) final private var three = null
//  }
//
//  private[builder] class TestObjectReference private[builder] (val one: Int) {
//    this.one = new EqualsBuilderTest.TestObject(one)
//    @SuppressWarnings(Array("unused")) private var reference = null
//    @SuppressWarnings(Array("unused")) final private var one = null
//
//    def setObjectReference(reference: EqualsBuilderTest.TestObjectReference): Unit = {
//      this.reference = reference
//    }
//
//    override def equals(obj: Any): Boolean = EqualsBuilder.reflectionEquals(this, obj)
//  }
//
//  private[builder] class TestObjectEqualsExclude private[builder] (val a: Int, val b: Int) {
//    def getA: Int = a
//
//    def getB: Int = b
//  }
//
//}
//
//class EqualsBuilderTest {
//  @Test def testReflectionEquals(): Unit = {
//    val o1 = new EqualsBuilderTest.TestObject(4)
//    val o2 = new EqualsBuilderTest.TestObject(5)
//    assertTrue(EqualsBuilder.reflectionEquals(o1, o1))
//    assertFalse(EqualsBuilder.reflectionEquals(o1, o2))
//    o2.setA(4)
//    assertTrue(EqualsBuilder.reflectionEquals(o1, o2))
//    assertFalse(EqualsBuilder.reflectionEquals(o1, this))
//    assertFalse(EqualsBuilder.reflectionEquals(o1, null))
//    assertFalse(EqualsBuilder.reflectionEquals(null, o2))
//    assertTrue(EqualsBuilder.reflectionEquals(null, null))
//  }
//
//  @Test def testReflectionHierarchyEquals(): Unit = {
//    testReflectionHierarchyEquals(false)
//    testReflectionHierarchyEquals(true)
//    // Transients
//    assertTrue(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        true))
//    assertTrue(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        false))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestTTLeafObject(1, 0, 0, 4),
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        true))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 0),
//        true))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestTTLeafObject(0, 2, 3, 4),
//        new EqualsBuilderTest.TestTTLeafObject(1, 2, 3, 4),
//        true))
//  }
//
//  private def testReflectionHierarchyEquals(testTransients: Boolean): Unit = {
//    val to1 = new EqualsBuilderTest.TestObject(4)
//    val to1Bis = new EqualsBuilderTest.TestObject(4)
//    val to1Ter = new EqualsBuilderTest.TestObject(4)
//    val to2 = new EqualsBuilderTest.TestObject(5)
//    val teso = new EqualsBuilderTest.TestEmptySubObject(4)
//    val ttso = new EqualsBuilderTest.TestTSubObject(4, 1)
//    val tttso = new EqualsBuilderTest.TestTTSubObject(4, 1, 2)
//    val ttlo = new EqualsBuilderTest.TestTTLeafObject(4, 1, 2, 3)
//    val tso1 = new EqualsBuilderTest.TestSubObject(1, 4)
//    val tso1bis = new EqualsBuilderTest.TestSubObject(1, 4)
//    val tso1ter = new EqualsBuilderTest.TestSubObject(1, 4)
//    val tso2 = new EqualsBuilderTest.TestSubObject(2, 5)
//    testReflectionEqualsEquivalenceRelationship(
//      to1,
//      to1Bis,
//      to1Ter,
//      to2,
//      new EqualsBuilderTest.TestObject,
//      testTransients)
//    testReflectionEqualsEquivalenceRelationship(
//      tso1,
//      tso1bis,
//      tso1ter,
//      tso2,
//      new EqualsBuilderTest.TestSubObject,
//      testTransients)
//    // More sanity checks:
//    // same values
//    assertTrue(EqualsBuilder.reflectionEquals(ttlo, ttlo, testTransients))
//    assertTrue(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestSubObject(1, 10),
//        new EqualsBuilderTest.TestSubObject(1, 10),
//        testTransients))
//    // same super values, diff sub values
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestSubObject(1, 10),
//        new EqualsBuilderTest.TestSubObject(1, 11),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestSubObject(1, 11),
//        new EqualsBuilderTest.TestSubObject(1, 10),
//        testTransients))
//    // diff super values, same sub values
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestSubObject(0, 10),
//        new EqualsBuilderTest.TestSubObject(1, 10),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestSubObject(1, 10),
//        new EqualsBuilderTest.TestSubObject(0, 10),
//        testTransients))
//    // mix super and sub types: equals
//    assertTrue(EqualsBuilder.reflectionEquals(to1, teso, testTransients))
//    assertTrue(EqualsBuilder.reflectionEquals(teso, to1, testTransients))
//    assertTrue(EqualsBuilder.reflectionEquals(to1, ttso, false)) // Force testTransients = false for this assert
//
//    assertTrue(EqualsBuilder.reflectionEquals(ttso, to1, false))
//    assertTrue(EqualsBuilder.reflectionEquals(to1, tttso, false))
//    assertTrue(EqualsBuilder.reflectionEquals(tttso, to1, false))
//    assertTrue(EqualsBuilder.reflectionEquals(ttso, tttso, false))
//    assertTrue(EqualsBuilder.reflectionEquals(tttso, ttso, false))
//    // mix super and sub types: NOT equals
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestObject(0),
//        new EqualsBuilderTest.TestEmptySubObject(1),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestEmptySubObject(1),
//        new EqualsBuilderTest.TestObject(0),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestObject(0),
//        new EqualsBuilderTest.TestTSubObject(1, 1),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestTSubObject(1, 1),
//        new EqualsBuilderTest.TestObject(0),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestObject(1),
//        new EqualsBuilderTest.TestSubObject(0, 10),
//        testTransients))
//    assertFalse(
//      EqualsBuilder.reflectionEquals(
//        new EqualsBuilderTest.TestSubObject(0, 10),
//        new EqualsBuilderTest.TestObject(1),
//        testTransients))
//    assertFalse(EqualsBuilder.reflectionEquals(to1, ttlo))
//    assertFalse(EqualsBuilder.reflectionEquals(tso1, this))
//  }
//
//  /**
//    * Equivalence relationship tests inspired by "Effective Java":
//    * <ul>
//    * <li>reflection</li>
//    * <li>symmetry</li>
//    * <li>transitive</li>
//    * <li>consistency</li>
//    * <li>non-null reference</li>
//    * </ul>
//    *
//    * @param to             a TestObject
//    * @param toBis          a TestObject, equal to to and toTer
//    * @param toTer          Left hand side, equal to to and toBis
//    * @param to2            a different TestObject
//    * @param oToChange      a TestObject that will be changed
//    * @param testTransients whether to test transient instance variables
//    */
//  private def testReflectionEqualsEquivalenceRelationship(
//    to: EqualsBuilderTest.TestObject,
//    toBis: EqualsBuilderTest.TestObject,
//    toTer: EqualsBuilderTest.TestObject,
//    to2: EqualsBuilderTest.TestObject,
//    oToChange: EqualsBuilderTest.TestObject,
//    testTransients: Boolean): Unit = { // reflection test
//    assertTrue(EqualsBuilder.reflectionEquals(to, to, testTransients))
//    assertTrue(EqualsBuilder.reflectionEquals(to2, to2, testTransients))
//    // symmetry test
//    assertTrue(
//      EqualsBuilder.reflectionEquals(to, toBis, testTransients) && EqualsBuilder
//        .reflectionEquals(toBis, to, testTransients))
//    // transitive test
//    assertTrue(
//      EqualsBuilder.reflectionEquals(to, toBis, testTransients) && EqualsBuilder
//        .reflectionEquals(toBis, toTer, testTransients) && EqualsBuilder.reflectionEquals(to, toTer, testTransients))
//    // consistency test
//    oToChange.setA(to.getA)
//    if (oToChange.isInstanceOf[EqualsBuilderTest.TestSubObject])
//      oToChange
//        .asInstanceOf[EqualsBuilderTest.TestSubObject]
//        .setB(to.asInstanceOf[EqualsBuilderTest.TestSubObject].getB)
//    assertTrue(EqualsBuilder.reflectionEquals(oToChange, to, testTransients))
//    assertTrue(EqualsBuilder.reflectionEquals(oToChange, to, testTransients))
//    oToChange.setA(to.getA + 1)
//    if (oToChange.isInstanceOf[EqualsBuilderTest.TestSubObject])
//      oToChange
//        .asInstanceOf[EqualsBuilderTest.TestSubObject]
//        .setB(to.asInstanceOf[EqualsBuilderTest.TestSubObject].getB + 1)
//    assertFalse(EqualsBuilder.reflectionEquals(oToChange, to, testTransients))
//    assertFalse(EqualsBuilder.reflectionEquals(oToChange, to, testTransients))
//    // non-null reference test
//    assertFalse(EqualsBuilder.reflectionEquals(to, null, testTransients))
//    assertFalse(EqualsBuilder.reflectionEquals(to2, null, testTransients))
//    assertFalse(EqualsBuilder.reflectionEquals(null, to, testTransients))
//    assertFalse(EqualsBuilder.reflectionEquals(null, to2, testTransients))
//    assertTrue(EqualsBuilder.reflectionEquals(null, null, testTransients))
//  }
//
//  @Test def testSuper(): Unit = {
//    val o1 = new EqualsBuilderTest.TestObject(4)
//    val o2 = new EqualsBuilderTest.TestObject(5)
//    assertTrue(new EqualsBuilder().appendSuper(true).append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().appendSuper(false).append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().appendSuper(true).append(o1, o2).isEquals)
//    assertFalse(new EqualsBuilder().appendSuper(false).append(o1, o2).isEquals)
//  }
//
//  @Test def testObject(): Unit = {
//    val o1 = new EqualsBuilderTest.TestObject(4)
//    val o2 = new EqualsBuilderTest.TestObject(5)
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//    o2.setA(4)
//    assertTrue(new EqualsBuilder().append(o1, o2).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, this).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, null).isEquals)
//    assertFalse(new EqualsBuilder().append(null, o2).isEquals)
//    assertTrue(new EqualsBuilder().append(null.asInstanceOf[Any], null).isEquals)
//  }
//
//  @Test def testObjectBuild(): Unit = {
//    val o1 = new EqualsBuilderTest.TestObject(4)
//    val o2 = new EqualsBuilderTest.TestObject(5)
//    assertEquals(Boolean.TRUE, new EqualsBuilder().append(o1, o1).build)
//    assertEquals(Boolean.FALSE, new EqualsBuilder().append(o1, o2).build)
//    o2.setA(4)
//    assertEquals(Boolean.TRUE, new EqualsBuilder().append(o1, o2).build)
//    assertEquals(Boolean.FALSE, new EqualsBuilder().append(o1, this).build)
//    assertEquals(Boolean.FALSE, new EqualsBuilder().append(o1, null).build)
//    assertEquals(Boolean.FALSE, new EqualsBuilder().append(null, o2).build)
//    assertEquals(Boolean.TRUE, new EqualsBuilder().append(null.asInstanceOf[Any], null).build)
//  }
//
//  @Test def testObjectRecursiveGenericInteger(): Unit = {
//    val o1_a = new EqualsBuilderTest.TestRecursiveGenericObject[Integer](1)
//    val o1_b = new EqualsBuilderTest.TestRecursiveGenericObject[Integer](1)
//    val o2 = new EqualsBuilderTest.TestRecursiveGenericObject[Integer](2)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_b).isEquals)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_b, o1_a).isEquals)
//    assertFalse(new EqualsBuilder().setTestRecursive(true).append(o1_b, o2).isEquals)
//  }
//
//  @Test def testObjectRecursiveGenericString()
//    : Unit = { // Note: Do not use literals, because string literals are always mapped by same object (internal() of String))!
//    val s1_a = String.valueOf(1)
//    val o1_a = new EqualsBuilderTest.TestRecursiveGenericObject[String](s1_a)
//    val o1_b = new EqualsBuilderTest.TestRecursiveGenericObject[String](String.valueOf(1))
//    val o2 = new EqualsBuilderTest.TestRecursiveGenericObject[String](String.valueOf(2))
//    // To trigger bug reported in LANG-1356, call hashCode only on string in instance o1_a
//    s1_a.hashCode
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_b).isEquals)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_b, o1_a).isEquals)
//    assertFalse(new EqualsBuilder().setTestRecursive(true).append(o1_b, o2).isEquals)
//  }
//
//  @Test def testObjectRecursive(): Unit = {
//    val i1_1 = new EqualsBuilderTest.TestRecursiveInnerObject(1)
//    val i1_2 = new EqualsBuilderTest.TestRecursiveInnerObject(1)
//    val i2_1 = new EqualsBuilderTest.TestRecursiveInnerObject(2)
//    val i2_2 = new EqualsBuilderTest.TestRecursiveInnerObject(2)
//    val i3 = new EqualsBuilderTest.TestRecursiveInnerObject(3)
//    val i4 = new EqualsBuilderTest.TestRecursiveInnerObject(4)
//    val o1_a = new EqualsBuilderTest.TestRecursiveObject(i1_1, i2_1, 1)
//    val o1_b = new EqualsBuilderTest.TestRecursiveObject(i1_2, i2_2, 1)
//    val o2 = new EqualsBuilderTest.TestRecursiveObject(i3, i4, 2)
//    val oNull = new EqualsBuilderTest.TestRecursiveObject(null, null, 2)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_a).isEquals)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_b).isEquals)
//    assertFalse(new EqualsBuilder().setTestRecursive(true).append(o1_a, o2).isEquals)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(oNull, oNull).isEquals)
//    assertFalse(new EqualsBuilder().setTestRecursive(true).append(o1_a, oNull).isEquals)
//  }
//
//  @Test def testObjectRecursiveCycleSelfreference(): Unit = {
//    val o1_a = new EqualsBuilderTest.TestRecursiveCycleObject(1)
//    val o1_b = new EqualsBuilderTest.TestRecursiveCycleObject(1)
//    val o2 = new EqualsBuilderTest.TestRecursiveCycleObject(2)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_a).isEquals)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_b).isEquals)
//    assertFalse(new EqualsBuilder().setTestRecursive(true).append(o1_a, o2).isEquals)
//  }
//
//  @Test def testObjectRecursiveCycle(): Unit = {
//    val o1_a = new EqualsBuilderTest.TestRecursiveCycleObject(1)
//    val i1_a = new EqualsBuilderTest.TestRecursiveCycleObject(o1_a, 100)
//    o1_a.setCycle(i1_a)
//    val o1_b = new EqualsBuilderTest.TestRecursiveCycleObject(1)
//    val i1_b = new EqualsBuilderTest.TestRecursiveCycleObject(o1_b, 100)
//    o1_b.setCycle(i1_b)
//    val o2 = new EqualsBuilderTest.TestRecursiveCycleObject(2)
//    val i2 = new EqualsBuilderTest.TestRecursiveCycleObject(o1_b, 200)
//    o2.setCycle(i2)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_a).isEquals)
//    assertTrue(new EqualsBuilder().setTestRecursive(true).append(o1_a, o1_b).isEquals)
//    assertFalse(new EqualsBuilder().setTestRecursive(true).append(o1_a, o2).isEquals)
//    assertTrue(EqualsBuilder.reflectionEquals(o1_a, o1_b, false, null, true))
//    assertFalse(EqualsBuilder.reflectionEquals(o1_a, o2, false, null, true))
//  }
//
//  @Test def testLong(): Unit = {
//    val o1 = 1L
//    val o2 = 2L
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//  }
//
//  @Test def testInt(): Unit = {
//    val o1 = 1
//    val o2 = 2
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//  }
//
//  @Test def testShort(): Unit = {
//    val o1 = 1
//    val o2 = 2
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//  }
//
//  @Test def testChar(): Unit = {
//    val o1 = 1
//    val o2 = 2
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//  }
//
//  @Test def testByte(): Unit = {
//    val o1 = 1
//    val o2 = 2
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//  }
//
//  @Test def testDouble(): Unit = {
//    val o1 = 1
//    val o2 = 2
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, Double.NaN).isEquals)
//    assertTrue(new EqualsBuilder().append(Double.NaN, Double.NaN).isEquals)
//    assertTrue(new EqualsBuilder().append(Double.PositiveInfinity, Double.PositiveInfinity).isEquals)
//  }
//
//  @Test def testFloat(): Unit = {
//    val o1 = 1
//    val o2 = 2
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, Float.NaN).isEquals)
//    assertTrue(new EqualsBuilder().append(Float.NaN, Float.NaN).isEquals)
//    assertTrue(new EqualsBuilder().append(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY).isEquals)
//  }
//
//  @Test def testAccessors(): Unit = {
//    val equalsBuilder = new EqualsBuilder
//    assertTrue(equalsBuilder.isEquals)
//    equalsBuilder.setEquals(true)
//    assertTrue(equalsBuilder.isEquals)
//    equalsBuilder.setEquals(false)
//    assertFalse(equalsBuilder.isEquals)
//  }
//
//  @Test def testReset(): Unit = {
//    val equalsBuilder = new EqualsBuilder
//    assertTrue(equalsBuilder.isEquals)
//    equalsBuilder.setEquals(false)
//    assertFalse(equalsBuilder.isEquals)
//    equalsBuilder.reset()
//    assertTrue(equalsBuilder.isEquals)
//  }
//
//  @Test def testBoolean(): Unit = {
//    val o1 = true
//    val o2 = false
//    assertTrue(new EqualsBuilder().append(o1, o1).isEquals)
//    assertFalse(new EqualsBuilder().append(o1, o2).isEquals)
//  }
//
//  @Test def testObjectArray(): Unit = {
//    var obj1 = new Array[EqualsBuilderTest.TestObject](3)
//    obj1(0) = new EqualsBuilderTest.TestObject(4)
//    obj1(1) = new EqualsBuilderTest.TestObject(5)
//    obj1(2) = null
//    var obj2 = new Array[EqualsBuilderTest.TestObject](3)
//    obj2(0) = new EqualsBuilderTest.TestObject(4)
//    obj2(1) = new EqualsBuilderTest.TestObject(5)
//    obj2(2) = null
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj2, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1).setA(6)
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1).setA(5)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(2) = obj1(1)
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(2) = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testLongArray(): Unit = {
//    var obj1 = new Array[Long](2)
//    obj1(0) = 5L
//    obj1(1) = 6L
//    var obj2 = new Array[Long](2)
//    obj2(0) = 5L
//    obj2(1) = 6L
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testIntArray(): Unit = {
//    var obj1 = new Array[Int](2)
//    obj1(0) = 5
//    obj1(1) = 6
//    var obj2 = new Array[Int](2)
//    obj2(0) = 5
//    obj2(1) = 6
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testShortArray(): Unit = {
//    var obj1 = new Array[Short](2)
//    obj1(0) = 5
//    obj1(1) = 6
//    var obj2 = new Array[Short](2)
//    obj2(0) = 5
//    obj2(1) = 6
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testCharArray(): Unit = {
//    var obj1 = new Array[Char](2)
//    obj1(0) = 5
//    obj1(1) = 6
//    var obj2 = new Array[Char](2)
//    obj2(0) = 5
//    obj2(1) = 6
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testByteArray(): Unit = {
//    var obj1 = new Array[Byte](2)
//    obj1(0) = 5
//    obj1(1) = 6
//    var obj2 = new Array[Byte](2)
//    obj2(0) = 5
//    obj2(1) = 6
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testDoubleArray(): Unit = {
//    var obj1 = new Array[Double](2)
//    obj1(0) = 5
//    obj1(1) = 6
//    var obj2 = new Array[Double](2)
//    obj2(0) = 5
//    obj2(1) = 6
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testFloatArray(): Unit = {
//    var obj1 = new Array[Float](2)
//    obj1(0) = 5
//    obj1(1) = 6
//    var obj2 = new Array[Float](2)
//    obj2(0) = 5
//    obj2(1) = 6
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testBooleanArray(): Unit = {
//    var obj1 = new Array[Boolean](2)
//    obj1(0) = true
//    obj1(1) = false
//    var obj2 = new Array[Boolean](2)
//    obj2(0) = true
//    obj2(1) = false
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1(1) = true
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj2 = null
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//    obj1 = null
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testMultiLongArray(): Unit = {
//    val array1 = new Array[Array[Long]](2, 2)
//    val array2 = new Array[Array[Long]](2, 2)
//    for (i <- 0 until array1.length) {
//      for (j <- 0 until array1(0).length) {
//        array1(i)(j) = (i + 1) * (j + 1)
//        array2(i)(j) = (i + 1) * (j + 1)
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiIntArray(): Unit = {
//    val array1 = new Array[Array[Int]](2, 2)
//    val array2 = new Array[Array[Int]](2, 2)
//    for (i <- 0 until array1.length) {
//      for (j <- 0 until array1(0).length) {
//        array1(i)(j) = (i + 1) * (j + 1)
//        array2(i)(j) = (i + 1) * (j + 1)
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiShortArray(): Unit = {
//    val array1 = new Array[Array[Short]](2, 2)
//    val array2 = new Array[Array[Short]](2, 2)
//    var i = 0
//    while ({
//      i < array1.length
//    }) {
//      var j = 0
//      while ({
//        j < array1(0).length
//      }) {
//        array1(i)(j) = i
//        array2(i)(j) = i
//
//        j += 1
//      }
//
//      i += 1
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiCharArray(): Unit = {
//    val array1 = new Array[Array[Char]](2, 2)
//    val array2 = new Array[Array[Char]](2, 2)
//    var i = 0
//    while ({
//      i < array1.length
//    }) {
//      var j = 0
//      while ({
//        j < array1(0).length
//      }) {
//        array1(i)(j) = i
//        array2(i)(j) = i
//
//        j += 1
//      }
//
//      i += 1
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiByteArray(): Unit = {
//    val array1 = new Array[Array[Byte]](2, 2)
//    val array2 = new Array[Array[Byte]](2, 2)
//    var i = 0
//    while ({
//      i < array1.length
//    }) {
//      var j = 0
//      while ({
//        j < array1(0).length
//      }) {
//        array1(i)(j) = i
//        array2(i)(j) = i
//
//        j += 1
//      }
//
//      i += 1
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiFloatArray(): Unit = {
//    val array1 = new Array[Array[Float]](2, 2)
//    val array2 = new Array[Array[Float]](2, 2)
//    for (i <- 0 until array1.length) {
//      for (j <- 0 until array1(0).length) {
//        array1(i)(j) = (i + 1) * (j + 1)
//        array2(i)(j) = (i + 1) * (j + 1)
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiDoubleArray(): Unit = {
//    val array1 = new Array[Array[Double]](2, 2)
//    val array2 = new Array[Array[Double]](2, 2)
//    for (i <- 0 until array1.length) {
//      for (j <- 0 until array1(0).length) {
//        array1(i)(j) = (i + 1) * (j + 1)
//        array2(i)(j) = (i + 1) * (j + 1)
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMultiBooleanArray(): Unit = {
//    val array1 = new Array[Array[Boolean]](2, 2)
//    val array2 = new Array[Array[Boolean]](2, 2)
//    for (i <- 0 until array1.length) {
//      for (j <- 0 until array1(0).length) {
//        array1(i)(j) = i == 1 || j == 1
//        array2(i)(j) = i == 1 || j == 1
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = false
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//    // compare 1 dim to 2.
//    val array3 = Array[Boolean](true, true)
//    assertFalse(new EqualsBuilder().append(array1, array3).isEquals)
//    assertFalse(new EqualsBuilder().append(array3, array1).isEquals)
//    assertFalse(new EqualsBuilder().append(array2, array3).isEquals)
//    assertFalse(new EqualsBuilder().append(array3, array2).isEquals)
//  }
//
//  @Test def testRaggedArray(): Unit = {
//    val array1 = new Array[Array[Long]](2)
//    val array2 = new Array[Array[Long]](2)
//    for (i <- 0 until array1.length) {
//      array1(i) = new Array[Long](2)
//      array2(i) = new Array[Long](2)
//      for (j <- 0 until array1(i).length) {
//        array1(i)(j) = (i + 1) * (j + 1)
//        array2(i)(j) = (i + 1) * (j + 1)
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1)(1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testMixedArray(): Unit = {
//    val array1 = new Array[AnyRef](2)
//    val array2 = new Array[AnyRef](2)
//    for (i <- 0 until array1.length) {
//      array1(i) = new Array[Long](2)
//      array2(i) = new Array[Long](2)
//      for (j <- 0 until 2) {
//        array1(i).asInstanceOf[Array[Long]](j) = (i + 1) * (j + 1)
//        array2(i).asInstanceOf[Array[Long]](j) = (i + 1) * (j + 1)
//      }
//    }
//    assertTrue(new EqualsBuilder().append(array1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(array1, array2).isEquals)
//    array1(1).asInstanceOf[Array[Long]](1) = 0
//    assertFalse(new EqualsBuilder().append(array1, array2).isEquals)
//  }
//
//  @Test def testObjectArrayHiddenByObject(): Unit = {
//    val array1 = new Array[EqualsBuilderTest.TestObject](2)
//    array1(0) = new EqualsBuilderTest.TestObject(4)
//    array1(1) = new EqualsBuilderTest.TestObject(5)
//    val array2 = new Array[EqualsBuilderTest.TestObject](2)
//    array2(0) = new EqualsBuilderTest.TestObject(4)
//    array2(1) = new EqualsBuilderTest.TestObject(5)
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1).setA(6)
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testLongArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Long](2)
//    array1(0) = 5L
//    array1(1) = 6L
//    val array2 = new Array[Long](2)
//    array2(0) = 5L
//    array2(1) = 6L
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testIntArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Int](2)
//    array1(0) = 5
//    array1(1) = 6
//    val array2 = new Array[Int](2)
//    array2(0) = 5
//    array2(1) = 6
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testShortArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Short](2)
//    array1(0) = 5
//    array1(1) = 6
//    val array2 = new Array[Short](2)
//    array2(0) = 5
//    array2(1) = 6
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testCharArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Char](2)
//    array1(0) = 5
//    array1(1) = 6
//    val array2 = new Array[Char](2)
//    array2(0) = 5
//    array2(1) = 6
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testByteArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Byte](2)
//    array1(0) = 5
//    array1(1) = 6
//    val array2 = new Array[Byte](2)
//    array2(0) = 5
//    array2(1) = 6
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testDoubleArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Double](2)
//    array1(0) = 5
//    array1(1) = 6
//    val array2 = new Array[Double](2)
//    array2(0) = 5
//    array2(1) = 6
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testFloatArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Float](2)
//    array1(0) = 5
//    array1(1) = 6
//    val array2 = new Array[Float](2)
//    array2(0) = 5
//    array2(1) = 6
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = 7
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  @Test def testBooleanArrayHiddenByObject(): Unit = {
//    val array1 = new Array[Boolean](2)
//    array1(0) = true
//    array1(1) = false
//    val array2 = new Array[Boolean](2)
//    array2(0) = true
//    array2(1) = false
//    val obj1 = array1
//    val obj2 = array2
//    assertTrue(new EqualsBuilder().append(obj1, obj1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array1).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, obj2).isEquals)
//    assertTrue(new EqualsBuilder().append(obj1, array2).isEquals)
//    array1(1) = true
//    assertFalse(new EqualsBuilder().append(obj1, obj2).isEquals)
//  }
//
//  /**
//    * Tests two instances of classes that can be equal and that are not "related". The two classes are not subclasses
//    * of each other and do not share a parent aside from Object.
//    * See https://issues.apache.org/bugzilla/show_bug.cgi?id=33069
//    */
//  @Test def testUnrelatedClasses(): Unit = {
//    val x = Array[AnyRef](new EqualsBuilderTest.TestACanEqualB(1))
//    val y = Array[AnyRef](new EqualsBuilderTest.TestBCanEqualA(1))
//    // sanity checks:
//    assertArrayEquals(x, x)
//    assertArrayEquals(y, y)
//    assertArrayEquals(x, y)
//    assertArrayEquals(y, x)
//    // real tests:
//    assertEquals(x(0), x(0))
//    assertEquals(y(0), y(0))
//    assertEquals(x(0), y(0))
//    assertEquals(y(0), x(0))
//    assertTrue(new EqualsBuilder().append(x, x).isEquals)
//    assertTrue(new EqualsBuilder().append(y, y).isEquals)
//    assertTrue(new EqualsBuilder().append(x, y).isEquals)
//    assertTrue(new EqualsBuilder().append(y, x).isEquals)
//  }
//
//  /**
//    * Test from https://issues.apache.org/bugzilla/show_bug.cgi?id=33067
//    */
//  @Test def testNpeForNullElement(): Unit = {
//    val x1 = Array[AnyRef](Integer.valueOf(1), null, Integer.valueOf(3))
//    val x2 = Array[AnyRef](Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))
//    // causes an NPE in 2.0 according to:
//    // https://issues.apache.org/bugzilla/show_bug.cgi?id=33067
//    new EqualsBuilder().append(x1, x2)
//  }
//
//  @Test def testReflectionEqualsExcludeFields(): Unit = {
//    val x1 = new EqualsBuilderTest.TestObjectWithMultipleFields(1, 2, 3)
//    val x2 = new EqualsBuilderTest.TestObjectWithMultipleFields(1, 3, 4)
//    // not equal when including all fields
//    assertFalse(EqualsBuilder.reflectionEquals(x1, x2))
//    // doesn't barf on null, empty array, or non-existent field, but still tests as not equal
//    assertFalse(EqualsBuilder.reflectionEquals(x1, x2, null.asInstanceOf[Array[String]]))
//    assertFalse(EqualsBuilder.reflectionEquals(x1, x2))
//    assertFalse(EqualsBuilder.reflectionEquals(x1, x2, "xxx"))
//    // not equal if only one of the differing fields excluded
//    assertFalse(EqualsBuilder.reflectionEquals(x1, x2, "two"))
//    assertFalse(EqualsBuilder.reflectionEquals(x1, x2, "three"))
//    // equal if both differing fields excluded
//    assertTrue(EqualsBuilder.reflectionEquals(x1, x2, "two", "three"))
//    // still equal as long as both differing fields are among excluded
//    assertTrue(EqualsBuilder.reflectionEquals(x1, x2, "one", "two", "three"))
//    assertTrue(EqualsBuilder.reflectionEquals(x1, x2, "one", "two", "three", "xxx"))
//  }
//
//  /**
//    * Test cyclical object references which cause a StackOverflowException if
//    * not handled properly. s. LANG-606
//    */
//  @Test def testCyclicalObjectReferences(): Unit = {
//    val refX1 = new EqualsBuilderTest.TestObjectReference(1)
//    val x1 = new EqualsBuilderTest.TestObjectReference(1)
//    x1.setObjectReference(refX1)
//    refX1.setObjectReference(x1)
//    val refX2 = new EqualsBuilderTest.TestObjectReference(1)
//    val x2 = new EqualsBuilderTest.TestObjectReference(1)
//    x2.setObjectReference(refX2)
//    refX2.setObjectReference(x2)
//    val refX3 = new EqualsBuilderTest.TestObjectReference(2)
//    val x3 = new EqualsBuilderTest.TestObjectReference(2)
//    x3.setObjectReference(refX3)
//    refX3.setObjectReference(x3)
//    assertEquals(x1, x2)
//    assertNull(EqualsBuilder.getRegistry)
//    assertNotEquals(x1, x3)
//    assertNull(EqualsBuilder.getRegistry)
//    assertNotEquals(x2, x3)
//    assertNull(EqualsBuilder.getRegistry)
//  }
//
//  @Test def testReflectionArrays(): Unit = {
//    val one = new EqualsBuilderTest.TestObject(1)
//    val two = new EqualsBuilderTest.TestObject(2)
//    val o1 = Array[AnyRef](one)
//    val o2 = Array[AnyRef](two)
//    val o3 = Array[AnyRef](one)
//    assertFalse(EqualsBuilder.reflectionEquals(o1, o2))
//    assertTrue(EqualsBuilder.reflectionEquals(o1, o1))
//    assertTrue(EqualsBuilder.reflectionEquals(o1, o3))
//    val d1 = Array(0, 1)
//    val d2 = Array(2, 3)
//    val d3 = Array(0, 1)
//    assertFalse(EqualsBuilder.reflectionEquals(d1, d2))
//    assertTrue(EqualsBuilder.reflectionEquals(d1, d1))
//    assertTrue(EqualsBuilder.reflectionEquals(d1, d3))
//  }
//
//  @Test def testToEqualsExclude(): Unit = {
//    var one = new EqualsBuilderTest.TestObjectEqualsExclude(1, 2)
//    var two = new EqualsBuilderTest.TestObjectEqualsExclude(1, 3)
//    assertFalse(EqualsBuilder.reflectionEquals(one, two))
//    one = new EqualsBuilderTest.TestObjectEqualsExclude(1, 2)
//    two = new EqualsBuilderTest.TestObjectEqualsExclude(2, 2)
//    assertTrue(EqualsBuilder.reflectionEquals(one, two))
//  }
//
//  @Test def testReflectionAppend(): Unit = {
//    assertTrue(EqualsBuilder.reflectionEquals(null, null))
//    val o1 = new EqualsBuilderTest.TestObject(4)
//    val o2 = new EqualsBuilderTest.TestObject(5)
//    assertTrue(new EqualsBuilder().reflectionAppend(o1, o1).build)
//    assertFalse(new EqualsBuilder().reflectionAppend(o1, o2).build)
//    o2.setA(4)
//    assertTrue(new EqualsBuilder().reflectionAppend(o1, o2).build)
//    assertFalse(new EqualsBuilder().reflectionAppend(o1, this).build)
//    assertFalse(new EqualsBuilder().reflectionAppend(o1, null).build)
//    assertFalse(new EqualsBuilder().reflectionAppend(null, o2).build)
//  }
//
//  @Test
//  @throws[Exception]
//  def testIsRegistered(): Unit = {
//    val firstObject = new Any
//    val secondObject = new Any
//    try {
//      val registerMethod = MethodUtils.getMatchingMethod(classOf[EqualsBuilder], "register", classOf[Any], classOf[Any])
//      registerMethod.setAccessible(true)
//      registerMethod.invoke(null, firstObject, secondObject)
//      assertTrue(EqualsBuilder.isRegistered(firstObject, secondObject))
//      assertTrue(EqualsBuilder.isRegistered(secondObject, firstObject)) // LANG-1349
//    } finally {
//      val unregisterMethod =
//        MethodUtils.getMatchingMethod(classOf[EqualsBuilder], "unregister", classOf[Any], classOf[Any])
//      unregisterMethod.setAccessible(true)
//      unregisterMethod.invoke(null, firstObject, secondObject)
//    }
//  }
//}
