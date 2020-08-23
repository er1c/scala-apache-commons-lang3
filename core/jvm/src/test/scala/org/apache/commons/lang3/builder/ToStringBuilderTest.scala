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

import org.junit.Assert._
import org.junit.Test
import org.junit.After
import java.lang.{Boolean => JavaBoolean}
import java.util
import org.apache.commons.lang3.void
import org.scalatest.Assertions.assertThrows

/**
  * Unit tests for {@link org.apache.commons.lang3.builder.ToStringBuilder}.
  */
object ToStringBuilderTest {
  // See LANG-1337 for more.
  private val ARRAYLIST_INITIAL_CAPACITY: Int = 10

  private[builder] class ReflectionTestFixtureA {
    @SuppressWarnings(Array("unused"))
    final private val a: Char = 'a'
    @SuppressWarnings(Array("unused"))
    @transient
    final private val transientA: Char = 't'

    void(a)
    void(transientA)
  }

  private[builder] class ReflectionTestFixtureB extends ToStringBuilderTest.ReflectionTestFixtureA {
    @SuppressWarnings(Array("unused"))
    private val b: Char = 'b'

    @SuppressWarnings(Array("unused"))
    @transient
    private val transientB = 't'

    void(b)
    void(transientB)
  }

  private[builder] class Outer {
    private[builder] val inner: Inner = new Inner

    private[builder] class Inner {
      override def toString: String = ToStringBuilder.reflectionToString(this)
    }

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  /**
    * A reflection test fixture.
    */
  private[builder] class ReflectionTestCycleA {
    private[builder] var b: ReflectionTestCycleB = null

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private[builder] class ReflectionTestCycleB {
    private[builder] var a: ReflectionTestCycleA = null

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private[builder] class SimpleReflectionTestFixture private[builder] () {
    private[builder] var o: Any = null

    private[builder] def this(o: Any) {
      this()
      this.o = o
    }

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private class SelfInstanceVarReflectionTestFixture private[builder] () {
    @SuppressWarnings(Array("unused"))
    final private val typeIsSelf: SelfInstanceVarReflectionTestFixture = this
    void(typeIsSelf)

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private class SelfInstanceTwoVarsReflectionTestFixture private[builder] () {
    @SuppressWarnings(Array("unused"))
    final private val typeIsSelf: SelfInstanceTwoVarsReflectionTestFixture = this
    void(typeIsSelf)

    final private val otherType: String = "The Other Type"

    def getOtherType: String = this.otherType

    override def toString: String = ToStringBuilder.reflectionToString(this)
  }

  private[builder] class ObjectCycle {
    private[builder] var obj: AnyRef = null

    override def toString: String = new ToStringBuilder(this).append(obj).toString
  }

}

class ToStringBuilderTest {
  final private val base: Integer = Integer.valueOf(5)
  final private val baseStr: String = base.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(base))

  /*
   * All tests should leave the registry empty.
   */
  @After def after(): Unit = {
    validateNullToStringStyleRegistry()
  }

  @Test def testConstructorEx1(): Unit = {
    assertEquals("<null>", new ToStringBuilder(null).toString)
  }

  @Test def testConstructorEx2(): Unit = {
    assertEquals("<null>", new ToStringBuilder(null, null).toString)
    new ToStringBuilder(this.base, null).toString
    ()
  }

  @Test def testConstructorEx3(): Unit = {
    assertEquals("<null>", new ToStringBuilder(null, null, null).toString)
    new ToStringBuilder(this.base, null, null).toString
    new ToStringBuilder(this.base, ToStringStyle.DEFAULT_STYLE, null).toString
    ()
  }

  @Test def testGetSetDefault(): Unit = {
    try {
      ToStringBuilder.setDefaultStyle(ToStringStyle.NO_FIELD_NAMES_STYLE)
      assertSame(ToStringStyle.NO_FIELD_NAMES_STYLE, ToStringBuilder.getDefaultStyle)
    } finally {
      // reset for other tests
      ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE)
    }
  }

  @Test def testSetDefaultEx(): Unit = {
    assertThrows[NullPointerException](ToStringBuilder.setDefaultStyle(null))
    ()
  }

  @Test def testBlank(): Unit = {
    assertEquals(baseStr + "[]", new ToStringBuilder(base).toString)
  }

  /**
    * Test wrapper for int primitive.
    */
  @Test def testReflectionInteger(): Unit = {
    assertEquals(baseStr + "[value=5]", ToStringBuilder.reflectionToString(base))
  }

  /**
    * Test wrapper for char primitive.
    */
  @Test def testReflectionCharacter(): Unit = {
    val c = 'A'
    assertEquals(this.toBaseString(c) + "[value=A]", ToStringBuilder.reflectionToString(c))
  }

  /**
    * Test wrapper for char boolean.
    */
  @Test def testReflectionBoolean(): Unit = {
    var b = java.lang.Boolean.TRUE
    assertEquals(this.toBaseString(b) + "[value=true]", ToStringBuilder.reflectionToString(b))
    b = java.lang.Boolean.FALSE
    assertEquals(this.toBaseString(b) + "[value=false]", ToStringBuilder.reflectionToString(b))
  }

  /**
    * Create the same toString() as Object.toString().
    *
    * @param o the object to create the string for.
    * @return a String in the Object.toString format.
    */
  private def toBaseString(o: Any) = o.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(o))

  def assertReflectionArray(expected: String, actual: Any): Unit = {
    if (actual == null) { // Until ToStringBuilder supports null objects.
      return
    }
    assertEquals(expected, ToStringBuilder.reflectionToString(actual))
    assertEquals(expected, ToStringBuilder.reflectionToString(actual, null))
    assertEquals(expected, ToStringBuilder.reflectionToString(actual, null, true))
    assertEquals(expected, ToStringBuilder.reflectionToString(actual, null, false))
  }

  @Test def testReflectionObjectArray(): Unit = {
    var array = Array[AnyRef](null, base, Array[Int](3, 6))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{<null>,5,{3,6}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionLongArray(): Unit = {
    var array = Array[Long](1, 2, -3, 4)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionIntArray(): Unit = {
    var array = Array[Int](1, 2, -3, 4)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionShortArray(): Unit = {
    var array = Array[Short](1, 2, -3, 4)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionyteArray(): Unit = {
    var array = Array[Byte](1, 2, -3, 4)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{1,2,-3,4}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionCharArray(): Unit = {
    var array = Array[Char]('A', '2', '_', 'D')
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{A,2,_,D}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionDoubleArray(): Unit = {
    var array = Array[Double](1.0, 2.9876, -3.00001, 4.3)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{1.0,2.9876,-3.00001,4.3}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionFloatArray(): Unit = {
    var array = Array[Float](1.0f, 2.9876f, -3.00001f, 4.3f)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{1.0,2.9876,-3.00001,4.3}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionBooleanArray(): Unit = {
    var array = Array[Boolean](true, false, false)
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{true,false,false}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionFloatArrayArray(): Unit = {
    var array = Array[Array[Float]](Array(1.0f, 2.29686f), null, Array(Float.NaN))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{1.0,2.29686},<null>,{NaN}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionLongArrayArray(): Unit = {
    var array = Array[Array[Long]](Array(1, 2), null, Array(5))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{1,2},<null>,{5}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionIntArrayArray(): Unit = {
    var array = Array[Array[Int]](Array(1, 2), null, Array(5))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{1,2},<null>,{5}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionhortArrayArray(): Unit = {
    var array = Array[Array[Short]](Array(1, 2), null, Array(5))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{1,2},<null>,{5}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionByteArrayArray(): Unit = {
    var array = Array[Array[Byte]](Array(1, 2), null, Array(5))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{1,2},<null>,{5}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionCharArrayArray(): Unit = {
    var array = Array[Array[Char]](Array('A', 'B'), null, Array('p'))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{A,B},<null>,{p}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionDoubleArrayArray(): Unit = {
    var array = Array[Array[Double]](Array(1.0, 2.29686), null, Array(Double.NaN))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{1.0,2.29686},<null>,{NaN}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  @Test def testReflectionBooleanArrayArray(): Unit = {
    var array = Array[Array[Boolean]](Array(true, false), null, Array(false))
    val baseString = this.toBaseString(array)
    assertEquals(baseString + "[{{true,false},<null>,{false}}]", ToStringBuilder.reflectionToString(array))
    assertEquals(baseString + "[{{true,false},<null>,{false}}]", ToStringBuilder.reflectionToString(array))
    array = null
    assertReflectionArray("<null>", array)
  }

  // Reflection hierarchy tests
  @Test def testReflectionHierarchyArrayList()
    : Unit = { // LANG-1337 without this, the generated string can differ depending on the JVM version/vendor
    val list = new util.ArrayList[AnyRef](ToStringBuilderTest.ARRAYLIST_INITIAL_CAPACITY)
    val baseString = this.toBaseString(list)
    val expectedWithTransients =
      baseString + "[elementData={<null>,<null>,<null>,<null>,<null>,<null>,<null>,<null>,<null>,<null>},size=0,modCount=0]"
    val toStringWithTransients = ToStringBuilder.reflectionToString(list, null, true)
    if (!(expectedWithTransients == toStringWithTransients))
      assertEquals(expectedWithTransients, toStringWithTransients)
    val expectedWithoutTransients = baseString + "[size=0]"
    val toStringWithoutTransients = ToStringBuilder.reflectionToString(list, null, false)
    if (!(expectedWithoutTransients == toStringWithoutTransients))
      assertEquals(expectedWithoutTransients, toStringWithoutTransients)
  }

  @Test def testReflectionHierarchy(): Unit = {
    val baseA = new ToStringBuilderTest.ReflectionTestFixtureA
    var baseString = this.toBaseString(baseA)
    assertEquals(baseString + "[a=a]", ToStringBuilder.reflectionToString(baseA))
    assertEquals(baseString + "[a=a]", ToStringBuilder.reflectionToString(baseA, null))
    assertEquals(baseString + "[a=a]", ToStringBuilder.reflectionToString(baseA, null, false))
    assertEquals(baseString + "[a=a,transientA=t]", ToStringBuilder.reflectionToString(baseA, null, true))
    assertEquals(baseString + "[a=a]", ToStringBuilder.reflectionToString(baseA, null, false, null))
    assertEquals(baseString + "[a=a]", ToStringBuilder.reflectionToString(baseA, null, false, classOf[Any]))
    assertEquals(
      baseString + "[a=a]",
      ToStringBuilder.reflectionToString(baseA, null, false, classOf[ToStringBuilderTest.ReflectionTestFixtureA]))
    val baseB = new ToStringBuilderTest.ReflectionTestFixtureB
    baseString = this.toBaseString(baseB)
    assertEquals(baseString + "[b=b,a=a]", ToStringBuilder.reflectionToString(baseB))
    assertEquals(baseString + "[b=b,a=a]", ToStringBuilder.reflectionToString(baseB))
    assertEquals(baseString + "[b=b,a=a]", ToStringBuilder.reflectionToString(baseB, null))
    assertEquals(baseString + "[b=b,a=a]", ToStringBuilder.reflectionToString(baseB, null, false))
    assertEquals(
      baseString + "[b=b,transientB=t,a=a,transientA=t]",
      ToStringBuilder.reflectionToString(baseB, null, true))
    assertEquals(baseString + "[b=b,a=a]", ToStringBuilder.reflectionToString(baseB, null, false, null))
    assertEquals(baseString + "[b=b,a=a]", ToStringBuilder.reflectionToString(baseB, null, false, classOf[Any]))
    assertEquals(
      baseString + "[b=b,a=a]",
      ToStringBuilder.reflectionToString(baseB, null, false, classOf[ToStringBuilderTest.ReflectionTestFixtureA]))
    assertEquals(
      baseString + "[b=b]",
      ToStringBuilder.reflectionToString(baseB, null, false, classOf[ToStringBuilderTest.ReflectionTestFixtureB]))
  }

  @Test def testInnerClassReflection(): Unit = {
    val outer = new ToStringBuilderTest.Outer
    assertEquals(toBaseString(outer) + "[inner=" + toBaseString(outer.inner) + "[]]", outer.toString)
  }

  /**
    * Test an array element pointing to its container.
    */
  @Test def testReflectionArrayCycle(): Unit = {
    val objects = new Array[AnyRef](1)
    objects(0) = objects
    assertEquals(
      this.toBaseString(objects) + "[{" + this.toBaseString(objects) + "}]",
      ToStringBuilder.reflectionToString(objects))
  }

  @Test def testReflectionArrayCycleLevel2(): Unit = {
    val objects = new Array[AnyRef](1)
    val objectsLevel2 = new Array[AnyRef](1)
    objects(0) = objectsLevel2
    objectsLevel2(0) = objects
    assertEquals(
      this.toBaseString(objects) + "[{{" + this.toBaseString(objects) + "}}]",
      ToStringBuilder.reflectionToString(objects))
    assertEquals(
      this.toBaseString(objectsLevel2) + "[{{" + this.toBaseString(objectsLevel2) + "}}]",
      ToStringBuilder.reflectionToString(objectsLevel2))
  }

  @Test def testReflectionArrayArrayCycle(): Unit = {
    val objects = Array.ofDim[AnyRef](2, 2)
    objects(0)(0) = objects
    objects(0)(1) = objects
    objects(1)(0) = objects
    objects(1)(1) = objects
    val basicToString = this.toBaseString(objects)
    assertEquals(
      basicToString + "[{{" + basicToString + "," + basicToString + "},{" + basicToString + "," + basicToString + "}}]",
      ToStringBuilder.reflectionToString(objects))
  }

  /**
    * Test an Object pointing to itself, the simplest test.
    */
  @Test def testSimpleReflectionObjectCycle(): Unit = {
    val simple = new ToStringBuilderTest.SimpleReflectionTestFixture
    simple.o = simple
    assertEquals(this.toBaseString(simple) + "[o=" + this.toBaseString(simple) + "]", simple.toString)
  }

  /**
    * Test a class that defines an ivar pointing to itself.
    */
  @Test def testSelfInstanceVarReflectionObjectCycle(): Unit = {
    val test = new ToStringBuilderTest.SelfInstanceVarReflectionTestFixture
    assertEquals(this.toBaseString(test) + "[typeIsSelf=" + this.toBaseString(test) + "]", test.toString)
  }

  /**
    * Test a class that defines an ivar pointing to itself.  This test was
    * created to show that handling cyclical object resulted in a missing endFieldSeparator call.
    */
  @Test def testSelfInstanceTwoVarsReflectionObjectCycle(): Unit = {
    val test = new ToStringBuilderTest.SelfInstanceTwoVarsReflectionTestFixture
    assertEquals(
      this.toBaseString(test) + "[otherType=" + test.getOtherType.toString + ",typeIsSelf=" + this.toBaseString(
        test) + "]",
      test.toString)
  }

  /**
    * Test Objects pointing to each other.
    */
  @Test def testReflectionObjectCycle(): Unit = {
    val a: ToStringBuilderTest.ReflectionTestCycleA = new ToStringBuilderTest.ReflectionTestCycleA
    val b: ToStringBuilderTest.ReflectionTestCycleB = new ToStringBuilderTest.ReflectionTestCycleB
    a.b = b
    b.a = a
    assertEquals(this.toBaseString(a) + "[b=" + this.toBaseString(b) + "[a=" + this.toBaseString(a) + "]]", a.toString)
  }

  /**
    * Test a nasty combination of arrays and Objects pointing to each other.
    * objects[0] -&gt; SimpleReflectionTestFixture[ o -&gt; objects ]
    */
  @Test def testReflectionArrayAndObjectCycle(): Unit = {
    val objects = new Array[AnyRef](1)
    val simple = new ToStringBuilderTest.SimpleReflectionTestFixture(objects)
    objects(0) = simple
    assertEquals(
      this.toBaseString(objects) + "[{" + this.toBaseString(simple) + "[o=" + this.toBaseString(objects) + "]" + "}]",
      ToStringBuilder.reflectionToString(objects))
    assertEquals(
      this.toBaseString(simple) + "[o={" + this.toBaseString(simple) + "}]",
      ToStringBuilder.reflectionToString(simple))
  }

  private[builder] def validateNullToStringStyleRegistry(): Unit = {
    val registry = ToStringStyle.getRegistry
    assertNull("Expected null, actual: " + registry, registry)
  }

  @Test def testAppendSuper(): Unit = {
    assertEquals(baseStr + "[]", new ToStringBuilder(base).appendSuper("Integer@8888[]").toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").toString)
    assertEquals(
      baseStr + "[a=hello]",
      new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a", "hello").toString)
    assertEquals(
      baseStr + "[<null>,a=hello]",
      new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").append("a", "hello").toString)
    assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString)
  }

  @Test def testAppendToString(): Unit = {
    assertEquals(baseStr + "[]", new ToStringBuilder(base).appendToString("Integer@8888[]").toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).appendToString("Integer@8888[<null>]").toString)
    assertEquals(
      baseStr + "[a=hello]",
      new ToStringBuilder(base).appendToString("Integer@8888[]").append("a", "hello").toString)
    assertEquals(
      baseStr + "[<null>,a=hello]",
      new ToStringBuilder(base).appendToString("Integer@8888[<null>]").append("a", "hello").toString)
    assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendToString(null).append("a", "hello").toString)
  }

  @Test def testAppendAsObjectToString(): Unit = {
    val objectToAppend1 = ""
    val objectToAppend2 = JavaBoolean.TRUE
    val objectToAppend3 = new AnyRef
    assertEquals(
      baseStr + "[" + toBaseString(objectToAppend1) + "]",
      new ToStringBuilder(base).appendAsObjectToString(objectToAppend1).toString)
    assertEquals(
      baseStr + "[" + toBaseString(objectToAppend2) + "]",
      new ToStringBuilder(base).appendAsObjectToString(objectToAppend2).toString)
    assertEquals(
      baseStr + "[" + toBaseString(objectToAppend3) + "]",
      new ToStringBuilder(base).appendAsObjectToString(objectToAppend3).toString)
  }

  @Test def testAppendBooleanArrayWithFieldName(): Unit = {
    val array = Array[Boolean](true, false, false)
    assertEquals(baseStr + "[flags={true,false,false}]", new ToStringBuilder(base).append("flags", array).toString)
    assertEquals(
      baseStr + "[flags=<null>]",
      new ToStringBuilder(base).append("flags", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{true,false,false}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendBooleanArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Boolean](true, false, false)
    assertEquals(
      baseStr + "[flags={true,false,false}]",
      new ToStringBuilder(base).append("flags", array, true).toString)
    assertEquals(baseStr + "[length=<size=3>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[flags=<null>]",
      new ToStringBuilder(base).append("flags", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=3>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendCharArrayWithFieldName(): Unit = {
    val array = Array[Char]('A', '2', '_', 'D')
    assertEquals(baseStr + "[chars={A,2,_,D}]", new ToStringBuilder(base).append("chars", array).toString)
    assertEquals(baseStr + "[letters={A,2,_,D}]", new ToStringBuilder(base).append("letters", array).toString)
    assertEquals(
      baseStr + "[flags=<null>]",
      new ToStringBuilder(base).append("flags", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{A,2,_,D}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendCharArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Char]('A', '2', '_', 'D')
    assertEquals(baseStr + "[chars={A,2,_,D}]", new ToStringBuilder(base).append("chars", array, true).toString)
    assertEquals(baseStr + "[letters=<size=4>]", new ToStringBuilder(base).append("letters", array, false).toString)
    assertEquals(
      baseStr + "[flags=<null>]",
      new ToStringBuilder(base).append("flags", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendDoubleArrayWithFieldName(): Unit = {
    val array = Array[Double](1.0, 2.9876, -3.00001, 4.3)
    assertEquals(
      baseStr + "[values={1.0,2.9876,-3.00001,4.3}]",
      new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendDoubleArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Double](1.0, 2.9876, -3.00001, 4.3)
    assertEquals(
      baseStr + "[values={1.0,2.9876,-3.00001,4.3}]",
      new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=4>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendObjectArrayWithFieldName(): Unit = {
    val array = Array[AnyRef](null, base, Array[Int](3, 6))
    assertEquals(baseStr + "[values={<null>,5,{3,6}}]", new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{<null>,5,{3,6}}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendObjectArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[AnyRef](null, base, Array[Int](3, 6))
    assertEquals(
      baseStr + "[values={<null>,5,{3,6}}]",
      new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=3>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=3>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendLongArrayWithFieldName(): Unit = {
    val array = Array[Long](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendLongArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Long](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=4>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendIntArrayWithFieldName(): Unit = {
    val array = Array[Int](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendIntArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Int](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=4>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendShortArrayWithFieldName(): Unit = {
    val array = Array[Short](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendShortArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Short](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=4>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendByteArrayWithFieldName(): Unit = {
    val array = Array[Byte](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendByteArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Byte](1, 2, -3, 4)
    assertEquals(baseStr + "[values={1,2,-3,4}]", new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=4>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testAppendFloatArrayWithFieldName(): Unit = {
    val array = Array[Float](1.0f, 2.9876f, -3.00001f, 4.3f)
    assertEquals(
      baseStr + "[values={1.0,2.9876,-3.00001,4.3}]",
      new ToStringBuilder(base).append("values", array).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]]).toString)
    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(base).append(null, array).toString)
  }

  @Test def testAppendFloatArrayWithFieldNameAndFullDetatil(): Unit = {
    val array = Array[Float](1.0f, 2.9876f, -3.00001f, 4.3f)
    assertEquals(
      baseStr + "[values={1.0,2.9876,-3.00001,4.3}]",
      new ToStringBuilder(base).append("values", array, true).toString)
    assertEquals(baseStr + "[length=<size=4>]", new ToStringBuilder(base).append("length", array, false).toString)
    assertEquals(
      baseStr + "[values=<null>]",
      new ToStringBuilder(base).append("values", null.asInstanceOf[Array[Boolean]], true).toString)
    assertEquals(
      baseStr + "[<null>]",
      new ToStringBuilder(base).append(null, null.asInstanceOf[Array[Boolean]], false).toString)
    assertEquals(baseStr + "[<size=4>]", new ToStringBuilder(base).append(null, array, false).toString)
  }

  @Test def testConstructToStringBuilder(): Unit = {
    val stringBuilder1 = new ToStringBuilder(base, null, null)
    val stringBuilder2 = new ToStringBuilder(base, ToStringStyle.DEFAULT_STYLE, new StringBuffer(1024))
    assertEquals(ToStringStyle.DEFAULT_STYLE, stringBuilder1.getStyle)
    assertNotNull(stringBuilder1.getStringBuffer)
    assertNotNull(stringBuilder1.toString)
    assertEquals(ToStringStyle.DEFAULT_STYLE, stringBuilder2.getStyle)
    assertNotNull(stringBuilder2.getStringBuffer)
    assertNotNull(stringBuilder2.toString)
  }

  @Test def testObject(): Unit = {
    val i3 = Integer.valueOf(3)
    val i4 = Integer.valueOf(4)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(null.asInstanceOf[Any]).toString)
    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).toString)
    assertEquals(baseStr + "[a=<null>]", new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).toString)
    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).toString)
    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString)
    assertEquals(baseStr + "[a=<Integer>]", new ToStringBuilder(base).append("a", i3, false).toString)
    assertEquals(
      baseStr + "[a=<size=0>]",
      new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], false).toString)
    assertEquals(baseStr + "[a=[]]", new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], true).toString)
    assertEquals(
      baseStr + "[a=<size=0>]",
      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).toString)
    assertEquals(
      baseStr + "[a={}]",
      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).toString)
    assertEquals(
      baseStr + "[a=<size=0>]",
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).toString)
    assertEquals(
      baseStr + "[a={}]",
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).toString)
  }

  @Test def testObjectBuild(): Unit = {
    val i3 = Integer.valueOf(3)
    val i4 = Integer.valueOf(4)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(null.asInstanceOf[Any]).build)
    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).build)
    assertEquals(baseStr + "[a=<null>]", new ToStringBuilder(base).append("a", null.asInstanceOf[Any]).build)
    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).build)
    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).build)
    assertEquals(baseStr + "[a=<Integer>]", new ToStringBuilder(base).append("a", i3, false).build)
    assertEquals(
      baseStr + "[a=<size=0>]",
      new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], false).build)
    assertEquals(baseStr + "[a=[]]", new ToStringBuilder(base).append("a", new util.ArrayList[AnyRef], true).build)
    assertEquals(
      baseStr + "[a=<size=0>]",
      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], false).build)
    assertEquals(
      baseStr + "[a={}]",
      new ToStringBuilder(base).append("a", new util.HashMap[AnyRef, AnyRef], true).build)
    assertEquals(
      baseStr + "[a=<size=0>]",
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], false).build)
    assertEquals(
      baseStr + "[a={}]",
      new ToStringBuilder(base).append("a", new Array[String](0).asInstanceOf[Any], true).build)
  }

  @Test def testLong(): Unit = {
    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3L).toString)
    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3L).toString)
    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString)
  }

  @SuppressWarnings(Array("cast")) // cast is not really needed, keep for consistency
  @Test def testInt(): Unit = {
    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3).toString)
    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3).toString)
    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3).append("b", 4).toString)
  }

  @Test def testShort(): Unit = {
    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3.toShort).toString)
    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3.toShort).toString)
    assertEquals(
      baseStr + "[a=3,b=4]",
      new ToStringBuilder(base).append("a", 3.toShort).append("b", 4.toShort).toString)
  }
  @Test def testChar(): Unit = {
    assertEquals(baseStr + "[A]", new ToStringBuilder(base).append(65.toChar).toString)
    assertEquals(baseStr + "[a=A]", new ToStringBuilder(base).append("a", 65.toChar).toString)
    assertEquals(
      baseStr + "[a=A,b=B]",
      new ToStringBuilder(base).append("a", 65.toChar).append("b", 66.toChar).toString)
  }
  @Test def testByte(): Unit = {
    assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3.toByte).toString)
    assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3.toByte).toString)
    assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3.toByte).append("b", 4.toByte).toString)
  }
  @SuppressWarnings(Array("cast")) @Test def testDouble(): Unit = {
    assertEquals(baseStr + "[3.2]", new ToStringBuilder(base).append(3.2).toString)
    assertEquals(baseStr + "[a=3.2]", new ToStringBuilder(base).append("a", 3.2).toString)
    assertEquals(baseStr + "[a=3.2,b=4.3]", new ToStringBuilder(base).append("a", 3.2).append("b", 4.3).toString)
  }
  @Test def testFloat(): Unit = {
    assertEquals(baseStr + "[3.2]", new ToStringBuilder(base).append(3.2.toFloat).toString)
    assertEquals(baseStr + "[a=3.2]", new ToStringBuilder(base).append("a", 3.2.toFloat).toString)
    assertEquals(
      baseStr + "[a=3.2,b=4.3]",
      new ToStringBuilder(base).append("a", 3.2.toFloat).append("b", 4.3.toFloat).toString)
  }
  @Test def testBoolean(): Unit = {
    assertEquals(baseStr + "[true]", new ToStringBuilder(base).append(true).toString)
    assertEquals(baseStr + "[a=true]", new ToStringBuilder(base).append("a", true).toString)
    assertEquals(baseStr + "[a=true,b=false]", new ToStringBuilder(base).append("a", true).append("b", false).toString)
  }
  @Test def testObjectArray(): Unit = {
    var array: Array[AnyRef] = Array[AnyRef](null, base, Array[Int](3, 6))
    assertEquals(baseStr + "[{<null>,5,{3,6}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{<null>,5,{3,6}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testLongArray(): Unit = {
    var array: Array[Long] = Array[Long](1, 2, -(3), 4)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testIntArray(): Unit = {
    var array: Array[Int] = Array[Int](1, 2, -(3), 4)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testShortArray(): Unit = {
    var array: Array[Short] = Array[Short](1, 2, -(3), 4)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testByteArray(): Unit = {
    var array: Array[Byte] = Array[Byte](1, 2, -(3), 4)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{1,2,-3,4}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testCharArray(): Unit = {
    var array: Array[Char] = Array[Char]('A', '2', '_', 'D')
    assertEquals(baseStr + "[{A,2,_,D}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{A,2,_,D}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testDoubleArray(): Unit = {
    var array: Array[Double] = Array[Double](1.0, 2.9876, -(3.00001), 4.3)
    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(
      baseStr + "[{1.0,2.9876,-3.00001,4.3}]",
      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testFloatArray(): Unit = {
    var array: Array[Float] = Array[Float](1.0f, 2.9876f, -(3.00001f), 4.3f)
    assertEquals(baseStr + "[{1.0,2.9876,-3.00001,4.3}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(
      baseStr + "[{1.0,2.9876,-3.00001,4.3}]",
      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testBooleanArray(): Unit = {
    var array: Array[Boolean] = Array[Boolean](true, false, false)
    assertEquals(baseStr + "[{true,false,false}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{true,false,false}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testLongArrayArray(): Unit = {
    var array: Array[Array[Long]] = Array[Array[Long]](Array(1, 2), null, Array(5))
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testIntArrayArray(): Unit = {
    var array: Array[Array[Int]] = Array[Array[Int]](Array(1, 2), null, Array(5))
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testShortArrayArray(): Unit = {
    var array: Array[Array[Short]] = Array[Array[Short]](Array(1, 2), null, Array(5))
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testByteArrayArray(): Unit = {
    var array: Array[Array[Byte]] = Array[Array[Byte]](Array(1, 2), null, Array(5))
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{{1,2},<null>,{5}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testCharArrayArray(): Unit = {
    var array: Array[Array[Char]] = Array[Array[Char]](Array('A', 'B'), null, Array('p'))
    assertEquals(baseStr + "[{{A,B},<null>,{p}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[{{A,B},<null>,{p}}]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testDoubleArrayArray(): Unit = {
    var array: Array[Array[Double]] = Array[Array[Double]](Array(1.0, 2.29686), null, Array(Double.NaN))
    assertEquals(baseStr + "[{{1.0,2.29686},<null>,{NaN}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(
      baseStr + "[{{1.0,2.29686},<null>,{NaN}}]",
      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testFloatArrayArray(): Unit = {
    var array: Array[Array[Float]] = Array[Array[Float]](Array(1.0f, 2.29686f), null, Array(Float.NaN))
    assertEquals(baseStr + "[{{1.0,2.29686},<null>,{NaN}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(
      baseStr + "[{{1.0,2.29686},<null>,{NaN}}]",
      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testBooleanArrayArray(): Unit = {
    var array: Array[Array[Boolean]] = Array[Array[Boolean]](Array(true, false), null, Array(false))
    assertEquals(baseStr + "[{{true,false},<null>,{false}}]", new ToStringBuilder(base).append(array).toString)
    assertEquals(
      baseStr + "[{{true,false},<null>,{false}}]",
      new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
    array = null
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array).toString)
    assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append(array.asInstanceOf[Any]).toString)
  }
  @Test def testObjectCycle(): Unit = {
    val a: ToStringBuilderTest.ObjectCycle = new ToStringBuilderTest.ObjectCycle
    val b: ToStringBuilderTest.ObjectCycle = new ToStringBuilderTest.ObjectCycle
    a.obj = b
    b.obj = a
    val expected: String = toBaseString(a) + "[" + toBaseString(b) + "[" + toBaseString(a) + "]]"
    assertEquals(expected, a.toString)
  }
  @Test def testSimpleReflectionStatics(): Unit = {
    val instance1: SimpleReflectionStaticFieldsFixture =
      new SimpleReflectionStaticFieldsFixture
    assertEquals(
      this.toBaseString(instance1) + "[staticInt=12345,staticString=staticString]",
      ReflectionToStringBuilder
        .toString(instance1, null, false, true, classOf[SimpleReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(instance1) + "[staticInt=12345,staticString=staticString]",
      ReflectionToStringBuilder
        .toString(instance1, null, true, true, classOf[SimpleReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(instance1) + "[staticInt=12345,staticString=staticString]",
      this.toStringWithStatics(instance1, null, classOf[SimpleReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(instance1) + "[staticInt=12345,staticString=staticString]",
      this.toStringWithStatics(instance1, null, classOf[SimpleReflectionStaticFieldsFixture])
    )
  }

  /**
    * Tests ReflectionToStringBuilder.toString() for statics.
    */
  @Test def testReflectionStatics(): Unit = {
    val instance1: ReflectionStaticFieldsFixture = new ReflectionStaticFieldsFixture
    assertEquals(
      this.toBaseString(
        instance1) + "[instanceInt=67890,instanceString=instanceString,staticInt=12345,staticString=staticString]",
      ReflectionToStringBuilder
        .toString(instance1, null, false, true, classOf[ReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(
        instance1) + "[instanceInt=67890,instanceString=instanceString,transientInt=98765,transientString=transientString,staticInt=12345,staticString=staticString,staticTransientInt=54321,staticTransientString=staticTransientString]",
      ReflectionToStringBuilder
        .toString(instance1, null, true, true, classOf[ReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(
        instance1) + "[instanceInt=67890,instanceString=instanceString,staticInt=12345,staticString=staticString]",
      this.toStringWithStatics(instance1, null, classOf[ReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(
        instance1) + "[instanceInt=67890,instanceString=instanceString,staticInt=12345,staticString=staticString]",
      this.toStringWithStatics(instance1, null, classOf[ReflectionStaticFieldsFixture])
    )
  }
  @Test def testInheritedReflectionStatics(): Unit = {
    val instance1: InheritedReflectionStaticFieldsFixture =
      new InheritedReflectionStaticFieldsFixture
    assertEquals(
      this.toBaseString(instance1) + "[staticInt2=67890,staticString2=staticString2]",
      ReflectionToStringBuilder
        .toString(instance1, null, false, true, classOf[InheritedReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(
        instance1) + "[staticInt2=67890,staticString2=staticString2,staticInt=12345,staticString=staticString]",
      ReflectionToStringBuilder
        .toString(instance1, null, false, true, classOf[SimpleReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(
        instance1) + "[staticInt2=67890,staticString2=staticString2,staticInt=12345,staticString=staticString]",
      this.toStringWithStatics(instance1, null, classOf[SimpleReflectionStaticFieldsFixture])
    )
    assertEquals(
      this.toBaseString(
        instance1) + "[staticInt2=67890,staticString2=staticString2,staticInt=12345,staticString=staticString]",
      this.toStringWithStatics(instance1, null, classOf[SimpleReflectionStaticFieldsFixture])
    )
  }

  /**
    * <p>This method uses reflection to build a suitable
    * {@code toString} value which includes static fields.</p>
    *
    * <p>It uses {@code AccessibleObject.setAccessible} to gain access to private
    * fields. This means that it will throw a security exception if run
    * under a security manager, if the permissions are not set up correctly.
    * It is also not as efficient as testing explicitly. </p>
    *
    * <p>Transient fields are not output.</p>
    *
    * <p>Superclass fields will be appended up to and including the specified superclass.
    * A null superclass is treated as {@code java.lang.Object}.</p>
    *
    * <p>If the style is {@code null}, the default
    * {@code ToStringStyle} is used.</p>
    *
    * @param <                T> the type of the output object
    * @param object           the Object to be output
    * @param style            the style of the {@code toString} to create,
    *                         may be {@code null}
    * @param reflectUpToClass the superclass to reflect up to (inclusive),
    *                         may be {@code null}
    * @return the String result
    * @throws IllegalArgumentException if the Object is {@code null}
    */
  def toStringWithStatics[T](`object`: T, style: ToStringStyle, reflectUpToClass: Class[_ >: T]): String = {
    return ReflectionToStringBuilder.toString(`object`, style, false, true, reflectUpToClass)
  }

  /**
    * Tests ReflectionToStringBuilder setUpToClass().
    */
  @Test def test_setUpToClass_valid(): Unit = {
    val `val`: Integer = Integer.valueOf(5)
    val test: ReflectionToStringBuilder[Integer] = new ReflectionToStringBuilder[Integer](`val`)
    test.setUpToClass(classOf[Number])
    test.toString
    ()
  }

  @Test def test_setUpToClass_invalid(): Unit = {
    val `val`: Integer = Integer.valueOf(5)
    val test: ReflectionToStringBuilder[Integer] = new ReflectionToStringBuilder[Integer](`val`)
    assertThrows[IllegalArgumentException](test.setUpToClass(classOf[String]))
    test.toString
    ()
  }

  private[builder] object ReflectionStaticFieldsFixture {
    private[builder] val staticString: String = "staticString"
    private[builder] val staticInt: Int = 12345
    @transient private[builder] val staticTransientString: String = "staticTransientString"
    @transient private[builder] val staticTransientInt: Int = 54321
  }

  private[builder] class ReflectionStaticFieldsFixture {
    private[builder] val instanceString: String = "instanceString"
    private[builder] val instanceInt: Int = 67890
    @transient private[builder] val transientString: String = "transientString"
    @transient private[builder] val transientInt: Int = 98765
  }

  /**
    * Test fixture for ReflectionToStringBuilder.toString() for statics.
    */
  private[builder] object SimpleReflectionStaticFieldsFixture {
    private[builder] val staticString: String = "staticString"
    private[builder] val staticInt: Int = 12345
  }

  private[builder] class SimpleReflectionStaticFieldsFixture {}

  @SuppressWarnings(Array("unused"))
  private[builder] object InheritedReflectionStaticFieldsFixture {
    private[builder] val staticString2: String = "staticString2"
    private[builder] val staticInt2: Int = 67890
  }

  @SuppressWarnings(Array("unused"))
  private[builder] class InheritedReflectionStaticFieldsFixture extends SimpleReflectionStaticFieldsFixture {}

  @Test def testReflectionNull(): Unit = {
    assertThrows[NullPointerException](ReflectionToStringBuilder.toString(null))
    ()
  }

  /**
    * Points out failure to print anything from appendToString methods using MULTI_LINE_STYLE.
    * See issue LANG-372.
    */
  private[builder] class MultiLineTestObject {
    private[builder] val i = Integer.valueOf(31337)

    override def toString: String = new ToStringBuilder(this).append("testInt", i).toString
  }

  @Test def testAppendToStringUsingMultiLineStyle(): Unit = {
    val obj: MultiLineTestObject = new MultiLineTestObject
    val testBuilder: ToStringBuilder =
      new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).appendToString(obj.toString)
    assertEquals(-(1), testBuilder.toString.indexOf("testInt=31337"))
  }
}
