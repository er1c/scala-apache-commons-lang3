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
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.Matchers.hasItemInArray
//import org.hamcrest.Matchers.hasItems
//import org.junit.Assert.assertArrayEquals
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNotSame
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Method
//import java.lang.reflect.Type
//import java.util
//import java.util.Date
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.ClassUtils
//import org.apache.commons.lang3.ClassUtils.Interfaces
//import org.apache.commons.lang3.math.NumberUtils
//import org.apache.commons.lang3.mutable.Mutable
//import org.apache.commons.lang3.mutable.MutableObject
//import org.apache.commons.lang3.reflect.testbed.Annotated
//import org.apache.commons.lang3.reflect.testbed.GenericConsumer
//import org.apache.commons.lang3.reflect.testbed.GenericParent
//import org.apache.commons.lang3.reflect.testbed.PublicChild
//import org.apache.commons.lang3.reflect.testbed.StringParameterizedChild
//import org.apache.commons.lang3.tuple.ImmutablePair
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests MethodUtils
//  */
//object MethodUtilsTest {
//
//  private trait PrivateInterface {}
//
//  private[reflect] class TestBeanWithInterfaces extends MethodUtilsTest.PrivateInterface {
//    def foo = "foo()"
//  }
//
//  object TestBean {
//    def bar = "bar()"
//
//    def bar(i: Int) = "bar(int)"
//
//    def bar(i: Integer) = "bar(Integer)"
//
//    def bar(d: Double) = "bar(double)"
//
//    def bar(s: String) = "bar(String)"
//
//    def bar(o: Any) = "bar(Object)"
//
//    def bar(s: String*) = "bar(String...)"
//
//    def bar(s: Long*) = "bar(long...)"
//
//    def bar(i: Integer, s: String*) = "bar(int, String...)"
//
//    def oneParameterStatic(s: String) = {
//      // empty
//    }
//
//    // This method is overloaded for the wrapper class for every primitive type, plus the common supertypes
//    // Number and Object. This is an acid test since it easily leads to ambiguous methods.
//    def varOverload(args: Byte*) = "Byte..."
//
//    def varOverload(args: Character*) = "Character..."
//
//    def varOverload(args: Short*) = "Short..."
//
//    def varOverload(args: Boolean*) = "Boolean..."
//
//    def varOverload(args: Float*) = "Float..."
//
//    def varOverload(args: Double*) = "Double..."
//
//    def varOverload(args: Integer*) = "Integer..."
//
//    def varOverload(args: Long*) = "Long..."
//
//    def varOverload(args: Number*) = "Number..."
//
//    def varOverload(args: Any*) = "Object..."
//
//    def varOverload(args: String*) = "String..."
//
//    // This method is overloaded for the wrapper class for every numeric primitive type, plus the common
//    // supertype Number
//    def numOverload(args: Byte*) = "Byte..."
//
//    def numOverload(args: Short*) = "Short..."
//
//    def numOverload(args: Float*) = "Float..."
//
//    def numOverload(args: Double*) = "Double..."
//
//    def numOverload(args: Integer*) = "Integer..."
//
//    def numOverload(args: Long*) = "Long..."
//
//    def numOverload(args: Number*) = "Number..."
//
//    def varOverloadEchoStatic(args: String*) = new ImmutablePair[String, Array[AnyRef]]("String...", args)
//
//    def varOverloadEchoStatic(args: Number*) = new ImmutablePair[String, Array[AnyRef]]("Number...", args)
//
//    private[reflect] def verify(a: ImmutablePair[String, Array[AnyRef]], b: ImmutablePair[String, Array[AnyRef]]) = {
//      assertEquals(a.getLeft, b.getLeft)
//      assertArrayEquals(a.getRight, b.getRight)
//    }
//
//    private[reflect] def verify(a: ImmutablePair[String, Array[AnyRef]], _b: Any) = {
//      @SuppressWarnings(Array("unchecked")) val b = _b.asInstanceOf[ImmutablePair[String, Array[AnyRef]]]
//      verify(a, b)
//    }
//  }
//
//  class TestBean {
//    @SuppressWarnings(Array("unused")) private def privateStuff() = {
//    }
//
//    @SuppressWarnings(Array("unused")) private def privateStringStuff = "privateStringStuff()"
//
//    @SuppressWarnings(Array("unused")) private def privateStringStuff(i: Int) = "privateStringStuff(int)"
//
//    @SuppressWarnings(Array("unused")) private def privateStringStuff(i: Integer) = "privateStringStuff(Integer)"
//
//    @SuppressWarnings(Array("unused")) private def privateStringStuff(d: Double) = "privateStringStuff(double)"
//
//    @SuppressWarnings(Array("unused")) private def privateStringStuff(s: String) = "privateStringStuff(String)"
//
//    @SuppressWarnings(Array("unused")) private def privateStringStuff(s: Any) = "privateStringStuff(Object)"
//
//    def foo = "foo()"
//
//    def foo(i: Int) = "foo(int)"
//
//    def foo(i: Integer) = "foo(Integer)"
//
//    def foo(d: Double) = "foo(double)"
//
//    def foo(l: Long) = "foo(long)"
//
//    def foo(s: String) = "foo(String)"
//
//    def foo(o: Any) = "foo(Object)"
//
//    def foo(s: String*) = "foo(String...)"
//
//    def foo(l: Long*) = "foo(long...)"
//
//    def foo(i: Integer, s: String*) = "foo(int, String...)"
//
//    def oneParameter(s: String) = {
//    }
//
//    def foo(s: Any*) = "foo(Object...)"
//
//    def unboxing(values: Int*) = values
//
//    // These varOverloadEcho and varOverloadEchoStatic methods are designed to verify that
//    // not only is the correct overloaded variant invoked, but that the varags arguments
//    // are also delivered correctly to the method.
//    def varOverloadEcho(args: String*) = new ImmutablePair[String, Array[AnyRef]]("String...", args)
//
//    def varOverloadEcho(args: Number*) = new ImmutablePair[String, Array[AnyRef]]("Number...", args)
//  }
//
//  private class TestMutable extends Mutable[AnyRef] {
//    override def getValue = null
//
//    override def setValue(value: Any) = {
//    }
//  }
//
//  class InheritanceBean {
//    def testOne(obj: Any) = {
//    }
//
//    def testOne(obj: MethodUtilsTest.GrandParentObject) = {
//    }
//
//    def testOne(obj: MethodUtilsTest.ParentObject) = {
//    }
//
//    def testTwo(obj: Any) = {
//    }
//
//    def testTwo(obj: MethodUtilsTest.GrandParentObject) = {
//    }
//
//    def testTwo(obj: MethodUtilsTest.ChildInterface) = {
//    }
//  }
//
//  private[reflect] trait ChildInterface {}
//
//  class GrandParentObject {}
//
//  class ParentObject extends MethodUtilsTest.GrandParentObject {}
//
//  class ChildObject extends MethodUtilsTest.ParentObject with MethodUtilsTest.ChildInterface {}
//
//  private class MethodDescriptor private[reflect](val declaringClass: Class[_], val name: String, val parameterTypes: Array[Type]) {
//  }
//
//}
//
//class MethodUtilsTest extends JUnitSuite {
//  private var testBean = null
//  final private val classCache = new (util.HashMap[Class[_$1], Array[Class[_]]])
//  forSome
//  {
//    type _$1
//  }
//
//  @Before def setUp() = {
//    testBean = new MethodUtilsTest.TestBean
//    classCache.clear()
//  }
//
//  @Test
//  @throws[Exception]
//  def testConstructor() = assertNotNull(classOf[Nothing].newInstance)
//
//  @Test def verifyJavaVarargsOverloadingResolution() = { // This code is not a test of MethodUtils.
//    // Rather it makes explicit the behavior of the Java specification for
//    // various cases of overload resolution.
//    assertEquals("Byte...", MethodUtilsTest.TestBean.varOverload(1.toByte, 2.toByte))
//    assertEquals("Short...", MethodUtilsTest.TestBean.varOverload(1.toShort, 2.toShort))
//    assertEquals("Integer...", MethodUtilsTest.TestBean.varOverload(1, 2))
//    assertEquals("Long...", MethodUtilsTest.TestBean.varOverload(1L, 2L))
//    assertEquals("Float...", MethodUtilsTest.TestBean.varOverload(1f, 2f))
//    assertEquals("Double...", MethodUtilsTest.TestBean.varOverload(1d, 2d))
//    assertEquals("Character...", MethodUtilsTest.TestBean.varOverload('a', 'b'))
//    assertEquals("String...", MethodUtilsTest.TestBean.varOverload("a", "b"))
//    assertEquals("Boolean...", MethodUtilsTest.TestBean.varOverload(true, false))
//    assertEquals("Object...", MethodUtilsTest.TestBean.varOverload(1, "s"))
//    assertEquals("Object...", MethodUtilsTest.TestBean.varOverload(1, true))
//    assertEquals("Object...", MethodUtilsTest.TestBean.varOverload(1.1, true))
//    assertEquals("Object...", MethodUtilsTest.TestBean.varOverload('c', true))
//    assertEquals("Number...", MethodUtilsTest.TestBean.varOverload(1, 1.1))
//    assertEquals("Number...", MethodUtilsTest.TestBean.varOverload(1, 1L))
//    assertEquals("Number...", MethodUtilsTest.TestBean.varOverload(1d, 1f))
//    assertEquals("Number...", MethodUtilsTest.TestBean.varOverload(1.toShort, 1.toByte))
//    assertEquals("Object...", MethodUtilsTest.TestBean.varOverload(1, 'c'))
//    assertEquals("Object...", MethodUtilsTest.TestBean.varOverload('c', "s"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeJavaVarargsOverloadingResolution() = {
//    assertEquals("Byte...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1.toByte, 2.toByte))
//    assertEquals("Short...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1.toShort, 2.toShort))
//    assertEquals("Integer...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1, 2))
//    assertEquals("Long...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1L, 2L))
//    assertEquals("Float...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1f, 2f))
//    assertEquals("Double...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1d, 2d))
//    assertEquals("Character...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 'a', 'b'))
//    assertEquals("String...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", "a", "b"))
//    assertEquals("Boolean...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", true, false))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1, "s"))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1, true))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1.1, true))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 'c', true))
//    assertEquals("Number...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1, 1.1))
//    assertEquals("Number...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1, 1L))
//    assertEquals("Number...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1d, 1f))
//    assertEquals("Number...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1.toShort, 1.toByte))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 1, 'c'))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", 'c', "s"))
//    assertEquals("Object...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverload", ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]))
//    assertEquals("Number...", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "numOverload", ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeMethod() = {
//    assertEquals("foo()", MethodUtils.invokeMethod(testBean, "foo", ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]))
//    assertEquals("foo()", MethodUtils.invokeMethod(testBean, "foo"))
//    assertEquals("foo()", MethodUtils.invokeMethod(testBean, "foo", null.asInstanceOf[Array[AnyRef]]))
//    assertEquals("foo()", MethodUtils.invokeMethod(testBean, "foo", null, null))
//    assertEquals("foo(String)", MethodUtils.invokeMethod(testBean, "foo", ""))
//    assertEquals("foo(Object)", MethodUtils.invokeMethod(testBean, "foo", new Any))
//    assertEquals("foo(Object)", MethodUtils.invokeMethod(testBean, "foo", Boolean.TRUE))
//    assertEquals("foo(Integer)", MethodUtils.invokeMethod(testBean, "foo", NumberUtils.INTEGER_ONE))
//    assertEquals("foo(int)", MethodUtils.invokeMethod(testBean, "foo", NumberUtils.BYTE_ONE))
//    assertEquals("foo(long)", MethodUtils.invokeMethod(testBean, "foo", NumberUtils.LONG_ONE))
//    assertEquals("foo(double)", MethodUtils.invokeMethod(testBean, "foo", NumberUtils.DOUBLE_ONE))
//    assertEquals("foo(String...)", MethodUtils.invokeMethod(testBean, "foo", "a", "b", "c"))
//    assertEquals("foo(String...)", MethodUtils.invokeMethod(testBean, "foo", "a", "b", "c"))
//    assertEquals("foo(int, String...)", MethodUtils.invokeMethod(testBean, "foo", 5, "a", "b", "c"))
//    assertEquals("foo(long...)", MethodUtils.invokeMethod(testBean, "foo", 1L, 2L))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeMethod(testBean, "foo", 1, 2))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("String...", Array[String]("x", "y")), MethodUtils.invokeMethod(testBean, "varOverloadEcho", "x", "y"))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("Number...", Array[Number](17, 23, 42)), MethodUtils.invokeMethod(testBean, "varOverloadEcho", 17, 23, 42))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("String...", Array[String]("x", "y")), MethodUtils.invokeMethod(testBean, "varOverloadEcho", "x", "y"))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("Number...", Array[Number](17, 23, 42)), MethodUtils.invokeMethod(testBean, "varOverloadEcho", 17, 23, 42))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeMethod_VarArgsWithNullValues() = {
//    assertEquals("String...", MethodUtils.invokeMethod(testBean, "varOverload", "a", null, "c"))
//    assertEquals("String...", MethodUtils.invokeMethod(testBean, "varOverload", "a", "b", null))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeMethod_VarArgsNotUniqueResolvable() = {
//    assertEquals("Boolean...", MethodUtils.invokeMethod(testBean, "varOverload", Array[AnyRef](null)))
//    assertEquals("Object...", MethodUtils.invokeMethod(testBean, "varOverload", null.asInstanceOf[Array[AnyRef]]))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeExactMethod() = {
//    assertEquals("foo()", MethodUtils.invokeExactMethod(testBean, "foo", ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]))
//    assertEquals("foo()", MethodUtils.invokeExactMethod(testBean, "foo"))
//    assertEquals("foo()", MethodUtils.invokeExactMethod(testBean, "foo", null.asInstanceOf[Array[AnyRef]]))
//    assertEquals("foo()", MethodUtils.invokeExactMethod(testBean, "foo", null, null))
//    assertEquals("foo(String)", MethodUtils.invokeExactMethod(testBean, "foo", ""))
//    assertEquals("foo(Object)", MethodUtils.invokeExactMethod(testBean, "foo", new Any))
//    assertEquals("foo(Integer)", MethodUtils.invokeExactMethod(testBean, "foo", NumberUtils.INTEGER_ONE))
//    assertEquals("foo(double)", MethodUtils.invokeExactMethod(testBean, "foo", Array[AnyRef](NumberUtils.DOUBLE_ONE), Array[Class[_]](Double.TYPE)))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeExactMethod(testBean, "foo", NumberUtils.BYTE_ONE))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeExactMethod(testBean, "foo", NumberUtils.LONG_ONE))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeExactMethod(testBean, "foo", Boolean.TRUE))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeStaticMethod() = {
//    assertEquals("bar()", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]))
//    assertEquals("bar()", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", null.asInstanceOf[Array[AnyRef]]))
//    assertEquals("bar()", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", null, null))
//    assertEquals("bar(String)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", ""))
//    assertEquals("bar(Object)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", new Any))
//    assertEquals("bar(Object)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", Boolean.TRUE))
//    assertEquals("bar(Integer)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.INTEGER_ONE))
//    assertEquals("bar(int)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.BYTE_ONE))
//    assertEquals("bar(double)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.DOUBLE_ONE))
//    assertEquals("bar(String...)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", "a", "b"))
//    assertEquals("bar(long...)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", 1L, 2L))
//    assertEquals("bar(int, String...)", MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.INTEGER_ONE, "a", "b"))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("String...", Array[String]("x", "y")), MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverloadEchoStatic", "x", "y"))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("Number...", Array[Number](17, 23, 42)), MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverloadEchoStatic", 17, 23, 42))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("String...", Array[String]("x", "y")), MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverloadEchoStatic", "x", "y"))
//    MethodUtilsTest.TestBean.verify(new ImmutablePair[L, R]("Number...", Array[Number](17, 23, 42)), MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "varOverloadEchoStatic", 17, 23, 42))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeStaticMethod(classOf[MethodUtilsTest.TestBean], "does_not_exist"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeExactStaticMethod() = {
//    assertEquals("bar()", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[AnyRef]]))
//    assertEquals("bar()", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", null.asInstanceOf[Array[AnyRef]]))
//    assertEquals("bar()", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", null, null))
//    assertEquals("bar(String)", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", ""))
//    assertEquals("bar(Object)", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", new Any))
//    assertEquals("bar(Integer)", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.INTEGER_ONE))
//    assertEquals("bar(double)", MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", Array[AnyRef](NumberUtils.DOUBLE_ONE), Array[Class[_]](Double.TYPE)))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.BYTE_ONE))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", NumberUtils.LONG_ONE))
//    assertThrows(classOf[NoSuchMethodException], () => MethodUtils.invokeExactStaticMethod(classOf[MethodUtilsTest.TestBean], "bar", Boolean.TRUE))
//  }
//
//  @Test
//  @throws[Exception]
//  def testGetAccessibleInterfaceMethod() = {
//    val p = Array(ArrayUtils.EMPTY_CLASS_ARRAY, null)
//    for (element <- p) {
//      val method = classOf[MethodUtilsTest.TestMutable].getMethod("getValue", element)
//      val accessibleMethod = MethodUtils.getAccessibleMethod(method)
//      assertNotSame(accessibleMethod, method)
//      assertSame(classOf[Mutable[_]], accessibleMethod.getDeclaringClass)
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  def testGetAccessibleMethodPrivateInterface() = {
//    val expected = classOf[MethodUtilsTest.TestBeanWithInterfaces].getMethod("foo")
//    assertNotNull(expected)
//    val actual = MethodUtils.getAccessibleMethod(classOf[MethodUtilsTest.TestBeanWithInterfaces], "foo")
//    assertNull(actual)
//  }
//
//  @Test def testGetAccessibleInterfaceMethodFromDescription() = {
//    val p = Array(ArrayUtils.EMPTY_CLASS_ARRAY, null)
//    for (element <- p) {
//      val accessibleMethod = MethodUtils.getAccessibleMethod(classOf[MethodUtilsTest.TestMutable], "getValue", element)
//      assertSame(classOf[Mutable[_]], accessibleMethod.getDeclaringClass)
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  def testGetAccessiblePublicMethod() = assertSame(classOf[MutableObject[_]], MethodUtils.getAccessibleMethod(classOf[MutableObject[_]].getMethod("getValue", ArrayUtils.EMPTY_CLASS_ARRAY)).getDeclaringClass)
//
//  @Test def testGetAccessiblePublicMethodFromDescription() = assertSame(classOf[MutableObject[_]], MethodUtils.getAccessibleMethod(classOf[MutableObject[_]], "getValue", ArrayUtils.EMPTY_CLASS_ARRAY).getDeclaringClass)
//
//  @Test
//  @throws[Exception]
//  def testGetAccessibleMethodInaccessible() = {
//    val expected = classOf[MethodUtilsTest.TestBean].getDeclaredMethod("privateStuff")
//    val actual = MethodUtils.getAccessibleMethod(expected)
//    assertNull(actual)
//  }
//
//  @Test def testGetMatchingAccessibleMethod() = {
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", ArrayUtils.EMPTY_CLASS_ARRAY, ArrayUtils.EMPTY_CLASS_ARRAY)
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", null, ArrayUtils.EMPTY_CLASS_ARRAY)
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[String]), singletonArray(classOf[String]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Any]), singletonArray(classOf[Any]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Boolean]), singletonArray(classOf[Any]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Byte]), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Byte.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Short]), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Short.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Character]), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Character.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Integer]), singletonArray(classOf[Integer]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Integer.TYPE), singletonArray(Integer.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Long]), singletonArray(Long.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Long.TYPE), singletonArray(Long.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Float]), singletonArray(Double.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Float.TYPE), singletonArray(Double.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(classOf[Double]), singletonArray(Double.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Double.TYPE), singletonArray(Double.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", singletonArray(Double.TYPE), singletonArray(Double.TYPE))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", Array[Class[_]](classOf[String], classOf[String]), Array[Class[_]](classOf[Array[String]]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "foo", Array[Class[_]](Integer.TYPE, classOf[String], classOf[String]), Array[Class[_]](classOf[Integer], classOf[Array[String]]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.InheritanceBean], "testOne", singletonArray(classOf[MethodUtilsTest.ParentObject]), singletonArray(classOf[MethodUtilsTest.ParentObject]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.InheritanceBean], "testOne", singletonArray(classOf[MethodUtilsTest.ChildObject]), singletonArray(classOf[MethodUtilsTest.ParentObject]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.InheritanceBean], "testTwo", singletonArray(classOf[MethodUtilsTest.ParentObject]), singletonArray(classOf[MethodUtilsTest.GrandParentObject]))
//    expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.InheritanceBean], "testTwo", singletonArray(classOf[MethodUtilsTest.ChildObject]), singletonArray(classOf[MethodUtilsTest.ChildInterface]))
//  }
//
//  @Test def testNullArgument() = expectMatchingAccessibleMethodParameterTypes(classOf[MethodUtilsTest.TestBean], "oneParameter", singletonArray(null), singletonArray(classOf[String]))
//
//  @Test def testGetOverrideHierarchyIncludingInterfaces() = {
//    val method = MethodUtils.getAccessibleMethod(classOf[StringParameterizedChild], "consume", classOf[String])
//    val expected = util.Arrays.asList(new MethodUtilsTest.MethodDescriptor(classOf[StringParameterizedChild], "consume", classOf[String]), new MethodUtilsTest.MethodDescriptor(classOf[GenericParent[_]], "consume", classOf[GenericParent[_]].getTypeParameters(0)), new MethodUtilsTest.MethodDescriptor(classOf[GenericConsumer[_]], "consume", classOf[GenericConsumer[_]].getTypeParameters(0))).iterator
//    import scala.collection.JavaConversions._
//    for (m <- MethodUtils.getOverrideHierarchy(method, Interfaces.INCLUDE)) {
//      assertTrue(expected.hasNext)
//      val md = expected.next
//      assertEquals(md.declaringClass, m.getDeclaringClass)
//      assertEquals(md.name, m.getName)
//      assertEquals(md.parameterTypes.length, m.getParameterTypes.length)
//      for (i <- 0 until md.parameterTypes.length) {
//        assertTrue(TypeUtils.equals(md.parameterTypes(i), m.getGenericParameterTypes(i)))
//      }
//    }
//    assertFalse(expected.hasNext)
//  }
//
//  @Test def testGetOverrideHierarchyExcludingInterfaces() = {
//    val method = MethodUtils.getAccessibleMethod(classOf[StringParameterizedChild], "consume", classOf[String])
//    val expected = util.Arrays.asList(new MethodUtilsTest.MethodDescriptor(classOf[StringParameterizedChild], "consume", classOf[String]), new MethodUtilsTest.MethodDescriptor(classOf[GenericParent[_]], "consume", classOf[GenericParent[_]].getTypeParameters(0))).iterator
//    import scala.collection.JavaConversions._
//    for (m <- MethodUtils.getOverrideHierarchy(method, Interfaces.EXCLUDE)) {
//      assertTrue(expected.hasNext)
//      val md = expected.next
//      assertEquals(md.declaringClass, m.getDeclaringClass)
//      assertEquals(md.name, m.getName)
//      assertEquals(md.parameterTypes.length, m.getParameterTypes.length)
//      for (i <- 0 until md.parameterTypes.length) {
//        assertTrue(TypeUtils.equals(md.parameterTypes(i), m.getGenericParameterTypes(i)))
//      }
//    }
//    assertFalse(expected.hasNext)
//  }
//
//  @Test
//  @Annotated
//  @throws[NoSuchMethodException]
//  def testGetMethodsWithAnnotation() = {
//    assertArrayEquals(new Array[Method](0), MethodUtils.getMethodsWithAnnotation(classOf[Any], classOf[Annotated]))
//    val methodsWithAnnotation = MethodUtils.getMethodsWithAnnotation(classOf[MethodUtilsTest], classOf[Annotated])
//    assertEquals(2, methodsWithAnnotation.length)
//    assertThat(methodsWithAnnotation, hasItemInArray(classOf[MethodUtilsTest].getMethod("testGetMethodsWithAnnotation")))
//    assertThat(methodsWithAnnotation, hasItemInArray(classOf[MethodUtilsTest].getMethod("testGetMethodsListWithAnnotation")))
//  }
//
//  @Test def testGetMethodsWithAnnotationSearchSupersAndIgnoreAccess() = {
//    assertArrayEquals(new Array[Method](0), MethodUtils.getMethodsWithAnnotation(classOf[Any], classOf[Annotated], true, true))
//    val methodsWithAnnotation = MethodUtils.getMethodsWithAnnotation(classOf[PublicChild], classOf[Annotated], true, true)
//    assertEquals(4, methodsWithAnnotation.length)
//    assertEquals("PublicChild", methodsWithAnnotation(0).getDeclaringClass.getSimpleName)
//    assertEquals("PublicChild", methodsWithAnnotation(1).getDeclaringClass.getSimpleName)
//    assertTrue(methodsWithAnnotation(0).getName.endsWith("AnnotatedMethod"))
//    assertTrue(methodsWithAnnotation(1).getName.endsWith("AnnotatedMethod"))
//    assertEquals("Foo.doIt", methodsWithAnnotation(2).getDeclaringClass.getSimpleName + '.' + methodsWithAnnotation(2).getName)
//    assertEquals("Parent.parentProtectedAnnotatedMethod", methodsWithAnnotation(3).getDeclaringClass.getSimpleName + '.' + methodsWithAnnotation(3).getName)
//  }
//
//  @Test def testGetMethodsWithAnnotationNotSearchSupersButIgnoreAccess() = {
//    assertArrayEquals(new Array[Method](0), MethodUtils.getMethodsWithAnnotation(classOf[Any], classOf[Annotated], false, true))
//    val methodsWithAnnotation = MethodUtils.getMethodsWithAnnotation(classOf[PublicChild], classOf[Annotated], false, true)
//    assertEquals(2, methodsWithAnnotation.length)
//    assertEquals("PublicChild", methodsWithAnnotation(0).getDeclaringClass.getSimpleName)
//    assertEquals("PublicChild", methodsWithAnnotation(1).getDeclaringClass.getSimpleName)
//    assertTrue(methodsWithAnnotation(0).getName.endsWith("AnnotatedMethod"))
//    assertTrue(methodsWithAnnotation(1).getName.endsWith("AnnotatedMethod"))
//  }
//
//  @Test def testGetMethodsWithAnnotationSearchSupersButNotIgnoreAccess() = {
//    assertArrayEquals(new Array[Method](0), MethodUtils.getMethodsWithAnnotation(classOf[Any], classOf[Annotated], true, false))
//    val methodsWithAnnotation = MethodUtils.getMethodsWithAnnotation(classOf[PublicChild], classOf[Annotated], true, false)
//    assertEquals(2, methodsWithAnnotation.length)
//    assertEquals("PublicChild.publicAnnotatedMethod", methodsWithAnnotation(0).getDeclaringClass.getSimpleName + '.' + methodsWithAnnotation(0).getName)
//    assertEquals("Foo.doIt", methodsWithAnnotation(1).getDeclaringClass.getSimpleName + '.' + methodsWithAnnotation(1).getName)
//  }
//
//  @Test def testGetMethodsWithAnnotationNotSearchSupersAndNotIgnoreAccess() = {
//    assertArrayEquals(new Array[Method](0), MethodUtils.getMethodsWithAnnotation(classOf[Any], classOf[Annotated], false, false))
//    val methodsWithAnnotation = MethodUtils.getMethodsWithAnnotation(classOf[PublicChild], classOf[Annotated], false, false)
//    assertEquals(1, methodsWithAnnotation.length)
//    assertEquals("PublicChild.publicAnnotatedMethod", methodsWithAnnotation(0).getDeclaringClass.getSimpleName + '.' + methodsWithAnnotation(0).getName)
//  }
//
//  @Test
//  @throws[NoSuchMethodException]
//  def testGetAnnotationSearchSupersAndIgnoreAccess() = {
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentNotAnnotatedMethod"), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("doIt"), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentProtectedAnnotatedMethod"), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getDeclaredMethod("privateAnnotatedMethod"), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("publicAnnotatedMethod"), classOf[Annotated], true, true))
//    assertNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getMethod("parentNotAnnotatedMethod", classOf[String]), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getMethod("parentProtectedAnnotatedMethod", classOf[String]), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getDeclaredMethod("privateAnnotatedMethod", classOf[String]), classOf[Annotated], true, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getMethod("publicAnnotatedMethod", classOf[String]), classOf[Annotated], true, true))
//  }
//
//  @Test
//  @throws[NoSuchMethodException]
//  def testGetAnnotationNotSearchSupersButIgnoreAccess() = {
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentNotAnnotatedMethod"), classOf[Annotated], false, true))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("doIt"), classOf[Annotated], false, true))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentProtectedAnnotatedMethod"), classOf[Annotated], false, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getDeclaredMethod("privateAnnotatedMethod"), classOf[Annotated], false, true))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("publicAnnotatedMethod"), classOf[Annotated], false, true))
//  }
//
//  @Test
//  @throws[NoSuchMethodException]
//  def testGetAnnotationSearchSupersButNotIgnoreAccess() = {
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentNotAnnotatedMethod"), classOf[Annotated], true, false))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("doIt"), classOf[Annotated], true, false))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentProtectedAnnotatedMethod"), classOf[Annotated], true, false))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getDeclaredMethod("privateAnnotatedMethod"), classOf[Annotated], true, false))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("publicAnnotatedMethod"), classOf[Annotated], true, false))
//    assertNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getMethod("parentNotAnnotatedMethod", classOf[String]), classOf[Annotated], true, false))
//    assertNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getMethod("parentProtectedAnnotatedMethod", classOf[String]), classOf[Annotated], true, false))
//    assertNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getDeclaredMethod("privateAnnotatedMethod", classOf[String]), classOf[Annotated], true, false))
//    assertNotNull(MethodUtils.getAnnotation(classOf[StringParameterizedChild].getMethod("publicAnnotatedMethod", classOf[String]), classOf[Annotated], true, false))
//  }
//
//  @Test
//  @throws[NoSuchMethodException]
//  def testGetAnnotationNotSearchSupersAndNotIgnoreAccess() = {
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentNotAnnotatedMethod"), classOf[Annotated], false, false))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("doIt"), classOf[Annotated], false, false))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("parentProtectedAnnotatedMethod"), classOf[Annotated], false, false))
//    assertNull(MethodUtils.getAnnotation(classOf[PublicChild].getDeclaredMethod("privateAnnotatedMethod"), classOf[Annotated], false, false))
//    assertNotNull(MethodUtils.getAnnotation(classOf[PublicChild].getMethod("publicAnnotatedMethod"), classOf[Annotated], false, false))
//  }
//
//  @Test def testGetMethodsWithAnnotationIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => MethodUtils.getMethodsWithAnnotation(classOf[FieldUtilsTest], null))
//
//  @Test def testGetMethodsWithAnnotationIllegalArgumentException2() = assertThrows(classOf[NullPointerException], () => MethodUtils.getMethodsWithAnnotation(null, classOf[Annotated]))
//
//  @Test def testGetMethodsWithAnnotationIllegalArgumentException3() = assertThrows(classOf[NullPointerException], () => MethodUtils.getMethodsWithAnnotation(null, null))
//
//  @Test
//  @Annotated
//  @throws[NoSuchMethodException]
//  def testGetMethodsListWithAnnotation() = {
//    assertEquals(0, MethodUtils.getMethodsListWithAnnotation(classOf[Any], classOf[Annotated]).size)
//    val methodWithAnnotation = MethodUtils.getMethodsListWithAnnotation(classOf[MethodUtilsTest], classOf[Annotated])
//    assertEquals(2, methodWithAnnotation.size)
//    assertThat(methodWithAnnotation, hasItems(classOf[MethodUtilsTest].getMethod("testGetMethodsWithAnnotation"), classOf[MethodUtilsTest].getMethod("testGetMethodsListWithAnnotation")))
//  }
//
//  @Test def testGetMethodsListWithAnnotationIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => MethodUtils.getMethodsListWithAnnotation(classOf[FieldUtilsTest], null))
//
//  @Test def testGetMethodsListWithAnnotationIllegalArgumentException2() = assertThrows(classOf[NullPointerException], () => MethodUtils.getMethodsListWithAnnotation(null, classOf[Annotated]))
//
//  @Test def testGetMethodsListWithAnnotationIllegalArgumentException3() = assertThrows(classOf[NullPointerException], () => MethodUtils.getMethodsListWithAnnotation(null, null))
//
//  @Test def testGetAnnotationIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => MethodUtils.getAnnotation(classOf[FieldUtilsTest].getDeclaredMethods(0), null, true, true))
//
//  @Test def testGetAnnotationIllegalArgumentException2() = assertThrows(classOf[NullPointerException], () => MethodUtils.getAnnotation(null, classOf[Annotated], true, true))
//
//  @Test def testGetAnnotationIllegalArgumentException3() = assertThrows(classOf[NullPointerException], () => MethodUtils.getAnnotation(null, null, true, true))
//
//  private def expectMatchingAccessibleMethodParameterTypes(cls: Class[_], methodName: String, requestTypes: Array[Class[_]], actualTypes: Array[Class[_]]) = {
//    val m = MethodUtils.getMatchingAccessibleMethod(cls, methodName, requestTypes)
//    assertNotNull(m, "could not find any matches for " + methodName + " (" + (if (requestTypes == null) null
//    else toString(requestTypes)) + ")")
//    assertArrayEquals(actualTypes, m.getParameterTypes, toString(m.getParameterTypes) + " not equals " + toString(actualTypes))
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
//    val testBean = new MethodUtilsTest.TestBean
//    val actual = MethodUtils.invokeMethod(testBean, "unboxing", Integer.valueOf(1), Integer.valueOf(2)).asInstanceOf[Array[Int]]
//    assertArrayEquals(Array[Int](1, 2), actual)
//  }
//
//  @Test
//  @throws[Exception]
//  def testInvokeMethodForceAccessNoArgs() = assertEquals("privateStringStuff()", MethodUtils.invokeMethod(testBean, true, "privateStringStuff"))
//
//  @Test
//  @throws[Exception]
//  def testInvokeMethodForceAccessWithArgs() = {
//    assertEquals("privateStringStuff(Integer)", MethodUtils.invokeMethod(testBean, true, "privateStringStuff", 5))
//    assertEquals("privateStringStuff(double)", MethodUtils.invokeMethod(testBean, true, "privateStringStuff", 5.0d))
//    assertEquals("privateStringStuff(String)", MethodUtils.invokeMethod(testBean, true, "privateStringStuff", "Hi There"))
//    assertEquals("privateStringStuff(Object)", MethodUtils.invokeMethod(testBean, true, "privateStringStuff", new Date))
//  }
//
//  @Test
//  @throws[Exception]
//  def testDistance() = {
//    val distanceMethod = MethodUtils.getMatchingMethod(classOf[Nothing], "distance", classOf[Array[Class[_]]], classOf[Array[Class[_]]])
//    distanceMethod.setAccessible(true)
//    assertEquals(-1, distanceMethod.invoke(null, Array[Class[_]](classOf[String]), Array[Class[_]](classOf[Date])))
//    assertEquals(0, distanceMethod.invoke(null, Array[Class[_]](classOf[Date]), Array[Class[_]](classOf[Date])))
//    assertEquals(1, distanceMethod.invoke(null, Array[Class[_]](classOf[Integer]), Array[Class[_]](ClassUtils.wrapperToPrimitive(classOf[Integer]))))
//    assertEquals(2, distanceMethod.invoke(null, Array[Class[_]](classOf[Integer]), Array[Class[_]](classOf[Any])))
//    distanceMethod.setAccessible(false)
//  }
//}
