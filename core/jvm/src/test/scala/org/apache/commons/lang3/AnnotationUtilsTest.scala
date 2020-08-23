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

//package org.apache.commons.lang3
//
//import java.lang.annotation.ElementType.FIELD
//import java.lang.annotation.RetentionPolicy.RUNTIME
//import org.apache.commons.lang3.AnnotationUtilsTest.Stooge.CURLY
//import org.apache.commons.lang3.AnnotationUtilsTest.Stooge.LARRY
//import org.apache.commons.lang3.AnnotationUtilsTest.Stooge.MOE
//import org.apache.commons.lang3.AnnotationUtilsTest.Stooge.SHEMP
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotEquals
//import org.junit.Assert.assertTimeoutPreemptively
//import org.junit.Assert.assertTrue
//import java.lang.annotation.ElementType
//import java.lang.annotation.Retention
//import java.lang.annotation.RetentionPolicy
//import java.lang.annotation.Target
//import java.lang.reflect.Array
//import java.lang.reflect.Field
//import java.lang.reflect.InvocationHandler
//import java.lang.reflect.Proxy
//import java.time.Duration
//import java.util
//import org.junit.{Before, Test}
//
//
///**
//  */
//object AnnotationUtilsTest {
//
//  @Target(Array(FIELD))
//  @Retention(RUNTIME) trait TestAnnotation {
//    def string
//
//    def strings
//
//    def `type`
//
//    def types
//
//    def byteValue
//
//    def byteValues
//
//    def shortValue
//
//    def shortValues
//
//    def intValue
//
//    def intValues
//
//    def charValue
//
//    def charValues
//
//    def longValue
//
//    def longValues
//
//    def floatValue
//
//    def floatValues
//
//    def doubleValue
//
//    def doubleValues
//
//    def booleanValue
//
//    def booleanValues
//
//    def stooge
//
//    def stooges
//
//    def nest
//
//    def nests
//  }
//
//  @Retention(RUNTIME) trait NestAnnotation {
//    def string
//
//    def strings
//
//    def `type`
//
//    def types
//
//    def byteValue
//
//    def byteValues
//
//    def shortValue
//
//    def shortValues
//
//    def intValue
//
//    def intValues
//
//    def charValue
//
//    def charValues
//
//    def longValue
//
//    def longValues
//
//    def floatValue
//
//    def floatValues
//
//    def doubleValue
//
//    def doubleValues
//
//    def booleanValue
//
//    def booleanValues
//
//    def stooge
//
//    def stooges
//  }
//
//  @Retention(RetentionPolicy.RUNTIME)
//  @Target(Array(Array(ElementType.METHOD))) object TestMethodAnnotation {
//
//    class None extends Throwable {}
//
//  }
//
//  @Retention(RetentionPolicy.RUNTIME)
//  @Target(Array(Array(ElementType.METHOD))) trait TestMethodAnnotation {
//    def expected
//
//    def timeout
//  }
//
//  object Stooge extends Enumeration {
//    type Stooge = Value
//    val MOE, LARRY, CURLY, JOE, SHEMP = Value
//  }
//
//}
//
//class AnnotationUtilsTest {
//  @TestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), nest = new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Any], types = Array(Array(classOf[Any]))), nests = Array(Array(new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Array[AnyRef]], types = Array(Array(classOf[Array[AnyRef]]))))), shortValue = 0, shortValues = Array(Array(0)), stooge = SHEMP, stooges = Array(Array(MOE, LARRY, CURLY)), string = "", strings = Array(Array("")), `type` = classOf[Any], types = Array(Array(classOf[Any]))) var dummy1 = null
//  @TestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), nest = new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Any], types = Array(Array(classOf[Any]))), nests = Array(Array(new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Array[AnyRef]], types = Array(Array(classOf[Array[AnyRef]]))))), shortValue = 0, shortValues = Array(Array(0)), stooge = SHEMP, stooges = Array(Array(MOE, LARRY, CURLY)), string = "", strings = Array(Array("")), `type` = classOf[Any], types = Array(Array(classOf[Any]))) var dummy2 = null
//  @TestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), nest = new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Any], types = Array(Array(classOf[Any]))), nests = Array(Array(new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Array[AnyRef]], types = Array(Array(classOf[Array[AnyRef]]))), //add a second NestAnnotation to break equality:
//    new NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Array[AnyRef]], types = Array(Array(classOf[Array[AnyRef]]))))), shortValue = 0, shortValues = Array(Array(0)), stooge = SHEMP, stooges = Array(Array(MOE, LARRY, CURLY)), string = "", strings = Array(Array("")), `type` = classOf[Any], types = Array(Array(classOf[Any]))) var dummy3 = null
//  @NestAnnotation(booleanValue = false, booleanValues = Array(Array(false)), byteValue = 0, byteValues = Array(Array(0)), charValue = 0, charValues = Array(Array(0)), doubleValue = 0, doubleValues = Array(Array(0)), floatValue = 0, floatValues = Array(Array(0)), intValue = 0, intValues = Array(Array(0)), longValue = 0, longValues = Array(Array(0)), shortValue = 0, shortValues = Array(Array(0)), stooge = CURLY, stooges = Array(Array(MOE, LARRY, SHEMP)), string = "", strings = Array(Array("")), `type` = classOf[Array[AnyRef]], types = Array(Array(classOf[Array[AnyRef]]))) var dummy4 = null
//  private var field1 = null
//  private var field2 = null
//  private var field3 = null
//  private var field4 = null
//
//  @Before
//  @throws[Exception]
//  def setup() = {
//    field1 = getClass.getDeclaredField("dummy1")
//    field2 = getClass.getDeclaredField("dummy2")
//    field3 = getClass.getDeclaredField("dummy3")
//    field4 = getClass.getDeclaredField("dummy4")
//  }
//
//  @Test def testEquivalence() = {
//    assertTrue(AnnotationUtils.equals(field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), field2.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//    assertTrue(AnnotationUtils.equals(field2.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//  }
//
//  @Test def testSameInstance() = assertTrue(AnnotationUtils.equals(field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//
//  @Test def testNonEquivalentAnnotationsOfSameType() = {
//    assertFalse(AnnotationUtils.equals(field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), field3.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//    assertFalse(AnnotationUtils.equals(field3.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//  }
//
//  @Test def testAnnotationsOfDifferingTypes() = {
//    assertFalse(AnnotationUtils.equals(field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), field4.getAnnotation(classOf[AnnotationUtilsTest.NestAnnotation])))
//    assertFalse(AnnotationUtils.equals(field4.getAnnotation(classOf[AnnotationUtilsTest.NestAnnotation]), field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//  }
//
//  @Test def testOneArgNull() = {
//    assertFalse(AnnotationUtils.equals(field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation]), null))
//    assertFalse(AnnotationUtils.equals(null, field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])))
//  }
//
//  @Test def testBothArgsNull() = assertTrue(AnnotationUtils.equals(null, null))
//
//  @Test def testIsValidAnnotationMemberType() = {
//    for (`type` <- Array[Class[_]](classOf[Byte], classOf[Short], classOf[Int], classOf[Char], classOf[Long], classOf[Float], classOf[Double], classOf[Boolean], classOf[String], classOf[Class[_]], classOf[AnnotationUtilsTest.NestAnnotation], classOf[AnnotationUtilsTest.TestAnnotation], classOf[AnnotationUtilsTest.Stooge], classOf[ElementType])) {
//      assertTrue(AnnotationUtils.isValidAnnotationMemberType(`type`))
//      assertTrue(AnnotationUtils.isValidAnnotationMemberType(Array.newInstance(`type`, 0).getClass))
//    }
//    for (`type` <- Array[Class[_]](classOf[Any], classOf[util.Map[_, _]], classOf[util.Collection[_]])) {
//      assertFalse(AnnotationUtils.isValidAnnotationMemberType(`type`))
//      assertFalse(AnnotationUtils.isValidAnnotationMemberType(Array.newInstance(`type`, 0).getClass))
//    }
//  }
//
//  @Test def testGeneratedAnnotationEquivalentToRealAnnotation() = assertTimeoutPreemptively(Duration.ofSeconds(666L), () => {
//    def foo() = {
//      val real = getClass.getDeclaredMethod("testGeneratedAnnotationEquivalentToRealAnnotation").getAnnotation(classOf[Test])
//      val generatedTestInvocationHandler = (proxy: Any, method: Method, args: Array[AnyRef]) => {
//        def foo(proxy: Any, method: Method, args: Array[AnyRef]) = {
//          if ("equals" == method.getName && method.getParameterTypes.length == 1) return Boolean.valueOf(proxy eq args(0))
//          if ("hashCode" == method.getName && method.getParameterTypes.length == 0) return Integer.valueOf(System.identityHashCode(proxy))
//          if ("toString" == method.getName && method.getParameterTypes.length == 0) return "Test proxy"
//          method.invoke(real, args)
//        }
//
//        foo(proxy, method, args)
//      }
//      val generated = Proxy.newProxyInstance(Thread.currentThread.getContextClassLoader, Array[Class[_]](classOf[Test]), generatedTestInvocationHandler).asInstanceOf[Test]
//      assertEquals(real, generated)
//      assertNotEquals(generated, real)
//      assertTrue(AnnotationUtils.equals(generated, real))
//      assertTrue(AnnotationUtils.equals(real, generated))
//      val generated2 = Proxy.newProxyInstance(Thread.currentThread.getContextClassLoader, Array[Class[_]](classOf[Test]), generatedTestInvocationHandler).asInstanceOf[Test]
//      assertNotEquals(generated, generated2)
//      assertNotEquals(generated2, generated)
//      assertTrue(AnnotationUtils.equals(generated, generated2))
//      assertTrue(AnnotationUtils.equals(generated2, generated))
//    }
//
//    foo()
//  })
//
//  @Test def testHashCode() = assertTimeoutPreemptively(Duration.ofSeconds(666L), () => {
//    def foo() = {
//      val test = getClass.getDeclaredMethod("testHashCode").getAnnotation(classOf[Test])
//      assertEquals(test.hashCode, AnnotationUtils.hashCode(test))
//      val testAnnotation1 = field1.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])
//      assertEquals(testAnnotation1.hashCode, AnnotationUtils.hashCode(testAnnotation1))
//      val testAnnotation3 = field3.getAnnotation(classOf[AnnotationUtilsTest.TestAnnotation])
//      assertEquals(testAnnotation3.hashCode, AnnotationUtils.hashCode(testAnnotation3))
//    }
//
//    foo()
//  })
//
//  @Test
//  @TestMethodAnnotation(timeout = 666000) def testToString() = assertTimeoutPreemptively(Duration.ofSeconds(666L), () => {
//    def foo() = {
//      val testAnnotation = getClass.getDeclaredMethod("testToString").getAnnotation(classOf[AnnotationUtilsTest.TestMethodAnnotation])
//      val annotationString = AnnotationUtils.toString(testAnnotation)
//      assertTrue(annotationString.startsWith("@org.apache.commons.lang3.AnnotationUtilsTest$TestMethodAnnotation("))
//      assertTrue(annotationString.endsWith(")"))
//      assertTrue(annotationString.contains("expected=class org.apache.commons.lang3.AnnotationUtilsTest$TestMethodAnnotation$None"))
//      assertTrue(annotationString.contains("timeout=666000"))
//      assertTrue(annotationString.contains(", "))
//    }
//
//    foo()
//  })
//}
