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

//package org.apache.commons.lang3.reflect
//
//import org.junit.Assert.assertArrayEquals
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import java.lang.reflect.Constructor
//import java.util
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.math.NumberUtils
//import org.apache.commons.lang3.mutable.MutableObject
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
///**
//  * Unit tests ConstructorUtils
//  */
//object ConstructorUtilsTest {
//
//  class TestBean() {
//    toString = "()"
//    varArgs = null
//    final private var toString = null
//    final private[reflect] var varArgs = null
//
//    def this(i: Int) {
//      this()
//      toString = "(int)"
//      varArgs = null
//    }
//
//    def this(i: Integer) {
//      this()
//      toString = "(Integer)"
//      varArgs = null
//    }
//
//    def this(d: Double) {
//      this()
//      toString = "(double)"
//      varArgs = null
//    }
//
//    def this(s: String) {
//      this()
//      toString = "(String)"
//      varArgs = null
//    }
//
//    def this(o: Any) {
//      this()
//      toString = "(Object)"
//      varArgs = null
//    }
//
//    def this(s: String*) {
//      this()
//      toString = "(String...)"
//      varArgs = s
//    }
//
//    def this(bc: ConstructorUtilsTest.BaseClass, s: String*) {
//      this()
//      toString = "(BaseClass, String...)"
//      varArgs = s
//    }
//
//    def this(i: Integer, s: String*) {
//      this()
//      toString = "(Integer, String...)"
//      varArgs = s
//    }
//
//    def this(first: Integer, args: Int*) {
//      this()
//      toString = "(Integer, String...)"
//      varArgs = new Array[String](args.length)
//      for (i <- 0 until args.length) {
//        varArgs(i) = Integer.toString(args(i))
//      }
//    }
//
//    override def toString = toString
//
//    private[reflect] def verify(str: String, args: Array[String]) = {
//      assertEquals(str, toString)
//      assertArrayEquals(args, varArgs)
//    }
//  }
//
//  private class BaseClass {}
//
//  private class SubClass extends ConstructorUtilsTest.BaseClass {}
//
//  private[reflect] object PrivateClass {
//
//    @SuppressWarnings(Array("unused")) class PublicInnerClass() {
//    }
//
//  }
//
//  private[reflect] class PrivateClass @SuppressWarnings(Array("unused"))() {
//  }
//
//}
//
//class ConstructorUtilsTest() {
//  classCache = new (HashMap[Class[_$1], Array[Class[_]]])
//  forSome
//  {
//    type _$1
//  }
//  final private var classCache = null
//
//  @BeforeEach def setUp() = classCache.clear()
//
//  @Test
//  @throws[Exception]
//  def testConstructor() = assertNotNull(classOf[Nothing].newInstance)
//
//  @Test
//  @throws[Exception]
//  def testInvokeConstructor() = {
//    assertEquals("()", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]).toString)
//    assertEquals("()", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], null.asInstanceOf[Array[AnyRef]]).toString)
//    assertEquals("()", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean]).toString)
//    assertEquals("(String)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], "").toString)
//    assertEquals("(Object)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], new Any).toString)
//    assertEquals("(Object)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], Boolean.TRUE).toString)
//    assertEquals("(Integer)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.INTEGER_ONE).toString)
//    assertEquals("(int)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.BYTE_ONE).toString)
//    assertEquals("(double)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.LONG_ONE).toString)
//    assertEquals("(double)", ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.DOUBLE_ONE).toString)
//    ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.INTEGER_ONE).verify("(Integer)", null)
//    ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], "a", "b").verify("(String...)", Array[String]("a", "b"))
//    ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.INTEGER_ONE, "a", "b").verify("(Integer, String...)", Array[String]("a", "b"))
//    ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], new ConstructorUtilsTest.SubClass, Array[String]("a", "b")).verify("(BaseClass, String...)", Array[String]("a", "b"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeExactConstructor() = {
//    assertEquals("()", ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]).toString)
//    assertEquals("()", ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], null.asInstanceOf[Array[AnyRef]]).toString)
//    assertEquals("(String)", ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], "").toString)
//    assertEquals("(Object)", ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], new Any).toString)
//    assertEquals("(Integer)", ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.INTEGER_ONE).toString)
//    assertEquals("(double)", ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], Array[AnyRef](NumberUtils.DOUBLE_ONE), Array[Class[_]](Double.TYPE)).toString)
//    assertThrows(classOf[NoSuchMethodException], () => ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.BYTE_ONE))
//    assertThrows(classOf[NoSuchMethodException], () => ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], NumberUtils.LONG_ONE))
//    assertThrows(classOf[NoSuchMethodException], () => ConstructorUtils.invokeExactConstructor(classOf[ConstructorUtilsTest.TestBean], Boolean.TRUE))
//  }
//
//  @Test
//  @throws[Exception]
//  def testGetAccessibleConstructor() = {
//    assertNotNull(ConstructorUtils.getAccessibleConstructor(classOf[Any].getConstructor(ArrayUtils.EMPTY_CLASS_ARRAY)))
//    assertNull(ConstructorUtils.getAccessibleConstructor(classOf[ConstructorUtilsTest.PrivateClass].getConstructor(ArrayUtils.EMPTY_CLASS_ARRAY)))
//    assertNull(ConstructorUtils.getAccessibleConstructor(classOf[PrivateClass.PublicInnerClass]))
//  }
//
//  @Test def testGetAccessibleConstructorFromDescription() = {
//    assertNotNull(ConstructorUtils.getAccessibleConstructor(classOf[Any], ArrayUtils.EMPTY_CLASS_ARRAY))
//    assertNull(ConstructorUtils.getAccessibleConstructor(classOf[ConstructorUtilsTest.PrivateClass], ArrayUtils.EMPTY_CLASS_ARRAY))
//  }
//
//  @Test def testGetMatchingAccessibleMethod() = {
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], ArrayUtils.EMPTY_CLASS_ARRAY, ArrayUtils.EMPTY_CLASS_ARRAY)
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], null, ArrayUtils.EMPTY_CLASS_ARRAY)
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[String]), singletonArray(classOf[String]))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Any]), singletonArray(classOf[Any]))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Boolean]), singletonArray(classOf[Any]))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Byte]), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Byte.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Short]), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Short.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Character]), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Character.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Integer]), singletonArray(classOf[Integer]))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Integer.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Long]), singletonArray(Double.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Long.TYPE), singletonArray(Double.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Float]), singletonArray(Double.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Float.TYPE), singletonArray(Double.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(classOf[Double]), singletonArray(Double.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], singletonArray(Double.TYPE), singletonArray(Double.TYPE))
//    expectMatchingAccessibleConstructorParameterTypes(classOf[ConstructorUtilsTest.TestBean], Array[Class[_]](classOf[ConstructorUtilsTest.SubClass], classOf[Array[String]]), Array[Class[_]](classOf[ConstructorUtilsTest.BaseClass], classOf[Array[String]]))
//  }
//
//  @Test def testNullArgument() = expectMatchingAccessibleConstructorParameterTypes(classOf[MutableObject[_]], singletonArray(null), singletonArray(classOf[Any]))
//
//  private def expectMatchingAccessibleConstructorParameterTypes(cls: Class[_], requestTypes: Array[Class[_]], actualTypes: Array[Class[_]]) = {
//    val c = ConstructorUtils.getMatchingAccessibleConstructor(cls, requestTypes)
//    assertArrayEquals(actualTypes, c.getParameterTypes, toString(c.getParameterTypes) + " not equals " + toString(actualTypes))
//  }
//
//  private def toString(c: Array[Class[_]]) = util.Arrays.asList(c).toString
//
//  private def singletonArray(c: Class[_]) = {
//    var result = classCache.get(c)
//    if (result == null) {
//      result = Array[Class[_]](c)
//      classCache.put(c, result)
//    }
//    result
//  }
//
//  @Test
//  @throws[Exception]
//  def testVarArgsUnboxing() = {
//    val testBean = ConstructorUtils.invokeConstructor(classOf[ConstructorUtilsTest.TestBean], Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3))
//    assertArrayEquals(Array[String]("2", "3"), testBean.varArgs)
//  }
//}
