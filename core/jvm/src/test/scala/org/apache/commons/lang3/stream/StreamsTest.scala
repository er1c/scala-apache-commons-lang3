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

//package org.apache.commons.lang3.stream
//
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.core.Is.is
//import org.hamcrest.core.IsEqual.equalTo
//import org.hamcrest.core.IsNull.nullValue
//import org.junit.Assert.assertAll
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertThrows
//import org.junit.jupiter.api.DynamicTest.dynamicTest
//import java.lang.reflect.UndeclaredThrowableException
//import java.util
//import java.util.stream.Collectors
//import java.util.stream.Stream
//import org.apache.commons.lang3.function.Failable
//import org.apache.commons.lang3.function.FailableConsumer
//import org.apache.commons.lang3.function.FailablePredicate
//import org.junit.jupiter.api.DynamicTest
//import org.junit.Test
//import org.junit.TestFactory
//import org.junit.jupiter.api.function.Executable
//import org.xml.sax.SAXException
//
//class StreamsTest {
//  protected def asIntConsumer[T <: Throwable](pThrowable: T) = (s) => {
//    def foo(s) = {
//      val i = Integer.valueOf(s)
//      if (i.intValue == 4) throw pThrowable
//    }
//
//    foo(s)
//  }
//
//  protected def asIntPredicate[T <: Throwable](pThrowable: T) = (i) => {
//    def foo(i) = {
//      if (i.intValue eq 5) if (pThrowable != null) throw pThrowable
//      i % 2 == 0
//    }
//
//    foo(i)
//  }
//
//  private def assertEvenNumbers(output: util.List[Integer]) = {
//    assertEquals(3, output.size)
//    for (i <- 0 until 3) {
//      assertEquals((i + 1) * 2, output.get(i).intValue)
//    }
//  }
//
//  @TestFactory def simpleStreamFilterFailing = {
//    val input = util.Arrays.asList("1", "2", "3", "4", "5", "6")
//    val output = Failable.stream(input).map(Integer.valueOf).filter(asIntPredicate(null)).collect(Collectors.toList)
//    assertEvenNumbers(output)
//    Stream.of(dynamicTest("IllegalArgumentException", () => {
//      def foo() = {
//        val iae = new IllegalArgumentException("Invalid argument: " + 5)
//        val testMethod = () => Failable.stream(input).map(Integer.valueOf).filter(asIntPredicate(iae)).collect(Collectors.toList)
//        val thrown = assertThrows(classOf[IllegalArgumentException], testMethod)
//        assertThat(thrown.getMessage, is(equalTo("Invalid argument: " + 5)))
//      }
//
//      foo()
//    }), dynamicTest("OutOfMemoryError", () => {
//      def foo() = {
//        val oome = new OutOfMemoryError
//        val testMethod = () => Failable.stream(input).map(Integer.valueOf).filter(asIntPredicate(oome)).collect(Collectors.toList)
//        val thrown = assertThrows(classOf[OutOfMemoryError], testMethod)
//        assertThat(thrown.getMessage, is(nullValue))
//      }
//
//      foo()
//    }), dynamicTest("SAXException", () => {
//      def foo() = {
//        val se = new SAXException
//        val testMethod = () => Failable.stream(input).map(Integer.valueOf).filter(asIntPredicate(se)).collect(Collectors.toList)
//        val thrown = assertThrows(classOf[UndeclaredThrowableException], testMethod)
//        assertAll(() => assertThat(thrown.getMessage, is(nullValue)), () => assertThat(thrown.getCause, is(equalTo(se))))
//      }
//
//      foo()
//    }))
//  }
//
//  @TestFactory def simpleStreamForEachFailing = {
//    val input = util.Arrays.asList("1", "2", "3", "4", "5", "6")
//    Stream.of(dynamicTest("IllegalArgumentException", () => {
//      def foo() = {
//        val ise = new IllegalArgumentException
//        val testMethod = () => Failable.stream(input).forEach(asIntConsumer(ise))
//        val thrown = assertThrows(classOf[IllegalArgumentException], testMethod)
//        assertThat(thrown.getMessage, is(nullValue))
//      }
//
//      foo()
//    }), dynamicTest("OutOfMemoryError", () => {
//      def foo() = {
//        val oome = new OutOfMemoryError
//        val oomeTestMethod = () => Failable.stream(input).forEach(asIntConsumer(oome))
//        val oomeThrown = assertThrows(classOf[OutOfMemoryError], oomeTestMethod)
//        assertThat(oomeThrown.getMessage, is(nullValue))
//      }
//
//      foo()
//    }), dynamicTest("SAXException", () => {
//      def foo() = {
//        val se = new SAXException
//        val seTestMethod = () => Failable.stream(input).forEach(asIntConsumer(se))
//        val seThrown = assertThrows(classOf[UndeclaredThrowableException], seTestMethod)
//        assertAll(() => assertThat(seThrown.getMessage, is(nullValue)), () => assertThat(seThrown.getCause, is(equalTo(se))))
//      }
//
//      foo()
//    }))
//  }
//
//  @Test def testSimpleStreamFilter() = {
//    val input = util.Arrays.asList("1", "2", "3", "4", "5", "6")
//    val output = Failable.stream(input).map(Integer.valueOf).filter((i) => i.intValue % 2 eq 0).collect(Collectors.toList)
//    assertEvenNumbers(output)
//  }
//
//  @Test def testSimpleStreamForEach() = {
//    val input = util.Arrays.asList("1", "2", "3", "4", "5", "6")
//    val output = new util.ArrayList[Integer]
//    Failable.stream(input).forEach((s) => output.add(Integer.valueOf(s)))
//    assertEquals(6, output.size)
//    for (i <- 0 until 6) {
//      assertEquals(i + 1, output.get(i).intValue)
//    }
//  }
//
//  @Test def testSimpleStreamMap() = {
//    val input = util.Arrays.asList("1", "2", "3", "4", "5", "6")
//    val output = Failable.stream(input).map(Integer.valueOf).collect(Collectors.toList)
//    assertEquals(6, output.size)
//    for (i <- 0 until 6) {
//      assertEquals(i + 1, output.get(i).intValue)
//    }
//  }
//
//  @Test def testSimpleStreamMapFailing() = {
//    val input = util.Arrays.asList("1", "2", "3", "4 ", "5", "6")
//    val testMethod = () => Failable.stream(input).map(Integer.valueOf).collect(Collectors.toList)
//    val thrown = assertThrows(classOf[NumberFormatException], testMethod)
//    assertEquals("For input string: \"4 \"", thrown.getMessage)
//  }
//
//  @Test def testToArray() = {
//    val array = util.Arrays.asList("2", "3", "1").stream.collect(Streams.toArray(classOf[String]))
//    assertNotNull(array)
//    assertEquals(3, array.length)
//    assertEquals("2", array(0))
//    assertEquals("3", array(1))
//    assertEquals("1", array(2))
//  }
//}
