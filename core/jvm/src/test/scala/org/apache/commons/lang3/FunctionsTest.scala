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
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.io.IOException
//import java.io.UncheckedIOException
//import java.lang.reflect.UndeclaredThrowableException
//import java.util.concurrent.Callable
//import java.util.function.BiConsumer
//import java.util.function.BiFunction
//import java.util.function.BiPredicate
//import java.util.function.Consumer
//import java.util.function.Function
//import java.util.function.Predicate
//import java.util.function.Supplier
//import org.apache.commons.lang3.Functions.FailableBiConsumer
//import org.apache.commons.lang3.Functions.FailableBiFunction
//import org.apache.commons.lang3.Functions.FailableCallable
//import org.apache.commons.lang3.Functions.FailableConsumer
//import org.apache.commons.lang3.Functions.FailableFunction
//import org.apache.commons.lang3.Functions.FailableSupplier
//import org.junit.jupiter.api.DisplayName
//import org.junit.Test
//
//
//object FunctionsTest {
//
//  class CloseableObject {
//    private var closed = false
//
//    def close() = closed = true
//
//    def isClosed = closed
//
//    def reset() = closed = false
//
//    @throws[Throwable]
//    def run(pTh: Throwable) = if (pTh != null) throw pTh
//  }
//
//  object FailureOnOddInvocations {
//    private var invocations = 0
//
//    @throws[SomeException]
//    private[lang3] def failingBool = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private[lang3] def testDouble(value: Double) = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private[lang3] def testInt(value: Int) = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private[lang3] def testLong(value: Long) = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private def throwOnOdd() = {
//      val i = {
//        invocations += 1; invocations
//      }
//      if (i % 2 == 1) throw new FunctionsTest.SomeException("Odd Invocation: " + i)
//    }
//  }
//
//  class FailureOnOddInvocations @throws[SomeException]
//
//  private[lang3
//  ] () {
//    FailureOnOddInvocations.throwOnOdd()
//
//    @throws[SomeException]
//    private[lang3] def getAsBoolean = {
//      FailureOnOddInvocations.throwOnOdd()
//      true
//    }
//  }
//
//  @SerialVersionUID(-4965704778119283411L)
//  class SomeException private[lang3](val message: String) extends Exception(message) {
//    private var t = null
//
//    def setThrowable(throwable: Throwable) = t = throwable
//
//    @throws[Throwable]
//    def test() = if (t != null) throw t
//  }
//
//  class Testable[T, P] private[lang3](var throwable: Throwable) {
//    private var acceptedObject = null
//    private var acceptedPrimitiveObject1 = null
//    private var acceptedPrimitiveObject2 = null
//
//    def getAcceptedObject = acceptedObject
//
//    def getAcceptedPrimitiveObject1 = acceptedPrimitiveObject1
//
//    def getAcceptedPrimitiveObject2 = acceptedPrimitiveObject2
//
//    def setThrowable(throwable: Throwable) = this.throwable = throwable
//
//    @throws[Throwable]
//    def test() = test(throwable)
//
//    @throws[Throwable]
//    def test(input1: Any, input2: Any) = {
//      test(throwable)
//      acceptedObject
//    }
//
//    @throws[Throwable]
//    def test(throwable: Throwable) = if (throwable != null) throw throwable
//
//    @throws[Throwable]
//    def testAsBooleanPrimitive = testAsBooleanPrimitive(throwable)
//
//    @throws[Throwable]
//    def testAsBooleanPrimitive(throwable: Throwable) = {
//      if (throwable != null) throw throwable
//      false
//    }
//
//    @throws[Throwable]
//    def testAsDoublePrimitive = testAsDoublePrimitive(throwable)
//
//    @throws[Throwable]
//    def testAsDoublePrimitive(throwable: Throwable) = {
//      if (throwable != null) throw throwable
//      0
//    }
//
//    @throws[Throwable]
//    def testAsInteger = testAsInteger(throwable)
//
//    @throws[Throwable]
//    def testAsInteger(throwable: Throwable) = {
//      if (throwable != null) throw throwable
//      0
//    }
//
//    @throws[Throwable]
//    def testAsIntPrimitive = testAsIntPrimitive(throwable)
//
//    @throws[Throwable]
//    def testAsIntPrimitive(throwable: Throwable) = {
//      if (throwable != null) throw throwable
//      0
//    }
//
//    @throws[Throwable]
//    def testAsLongPrimitive = testAsLongPrimitive(throwable)
//
//    @throws[Throwable]
//    def testAsLongPrimitive(throwable: Throwable) = {
//      if (throwable != null) throw throwable
//      0
//    }
//
//    @throws[Throwable]
//    def testDouble(i: Double) = {
//      test(throwable)
//      acceptedPrimitiveObject1 = i.asInstanceOf[Double].asInstanceOf[P]
//    }
//
//    @throws[Throwable]
//    def testDoubleDouble(i: Double, j: Double) = {
//      test(throwable)
//      acceptedPrimitiveObject1 = i.asInstanceOf[Double].asInstanceOf[P]
//      acceptedPrimitiveObject2 = j.asInstanceOf[Double].asInstanceOf[P]
//      3d
//    }
//
//    @throws[Throwable]
//    def testInt(i: Int) = {
//      test(throwable)
//      acceptedPrimitiveObject1 = i.asInstanceOf[Integer].asInstanceOf[P]
//    }
//
//    @throws[Throwable]
//    def testLong(i: Long) = {
//      test(throwable)
//      acceptedPrimitiveObject1 = i.asInstanceOf[Long].asInstanceOf[P]
//    }
//
//    @throws[Throwable]
//    def testObjDouble(`object`: T, i: Double) = {
//      test(throwable)
//      acceptedObject = `object`
//      acceptedPrimitiveObject1 = i.asInstanceOf[Double].asInstanceOf[P]
//    }
//
//    @throws[Throwable]
//    def testObjInt(`object`: T, i: Int) = {
//      test(throwable)
//      acceptedObject = `object`
//      acceptedPrimitiveObject1 = i.asInstanceOf[Integer].asInstanceOf[P]
//    }
//
//    @throws[Throwable]
//    def testObjLong(`object`: T, i: Long) = {
//      test(throwable)
//      acceptedObject = `object`
//      acceptedPrimitiveObject1 = i.asInstanceOf[Long].asInstanceOf[P]
//    }
//  }
//
//}
//
//class FunctionsTest {
//  @Test def testAcceptBiConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](null)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(FunctionsTest.Testable.test, testable, ise))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(FunctionsTest.Testable.test, testable, error))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(FunctionsTest.Testable.test, testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    Functions.accept(FunctionsTest.Testable.test, testable, null.asInstanceOf[Throwable])
//  }
//
//  @Test def testAcceptConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(FunctionsTest.Testable.test, testable))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(FunctionsTest.Testable.test, testable))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(FunctionsTest.Testable.test, testable))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    Functions.accept(FunctionsTest.Testable.test, testable)
//  }
//
//  @Test def testAcceptDoubleConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, Double](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(testable.testDouble, 1d))
//    assertSame(ise, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(testable.testDouble, 1d))
//    assertSame(error, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(testable.testDouble, 1d))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Functions.accept(testable.testDouble, 1d)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptIntConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, Integer](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(testable.testInt, 1))
//    assertSame(ise, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(testable.testInt, 1))
//    assertSame(error, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(testable.testInt, 1))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Functions.accept(testable.testInt, 1)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptLongConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, Long](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(testable.testLong, 1L))
//    assertSame(ise, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(testable.testLong, 1L))
//    assertSame(error, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(testable.testLong, 1L))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Functions.accept(testable.testLong, 1L)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptObjDoubleConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[String, Double](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(testable.testObjDouble, "X", 1d))
//    assertSame(ise, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(testable.testObjDouble, "X", 1d))
//    assertSame(error, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(testable.testObjDouble, "X", 1d))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Functions.accept(testable.testObjDouble, "X", 1d)
//    assertEquals("X", testable.getAcceptedObject)
//    assertEquals(1d, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptObjIntConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[String, Integer](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(testable.testObjInt, "X", 1))
//    assertSame(ise, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(testable.testObjInt, "X", 1))
//    assertSame(error, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(testable.testObjInt, "X", 1))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Functions.accept(testable.testObjInt, "X", 1)
//    assertEquals("X", testable.getAcceptedObject)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptObjLongConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[String, Long](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.accept(testable.testObjLong, "X", 1L))
//    assertSame(ise, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.accept(testable.testObjLong, "X", 1L))
//    assertSame(error, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.accept(testable.testObjLong, "X", 1L))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Functions.accept(testable.testObjLong, "X", 1L)
//    assertEquals("X", testable.getAcceptedObject)
//    assertEquals(1L, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testApplyBiFunction() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](null)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.apply(FunctionsTest.Testable.testAsInteger, testable, ise))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.apply(FunctionsTest.Testable.testAsInteger, testable, error))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.apply(FunctionsTest.Testable.testAsInteger, testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    val i = Functions.apply(FunctionsTest.Testable.testAsInteger, testable, null.asInstanceOf[Throwable])
//    assertNotNull(i)
//    assertEquals(0, i.intValue)
//  }
//
//  @Test def testApplyFunction() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.apply(FunctionsTest.Testable.testAsInteger, testable))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.apply(FunctionsTest.Testable.testAsInteger, testable))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.apply(FunctionsTest.Testable.testAsInteger, testable))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    val i = Functions.apply(FunctionsTest.Testable.testAsInteger, testable)
//    assertNotNull(i)
//    assertEquals(0, i.intValue)
//  }
//
//  @Test def testAsCallable() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failableCallable = FunctionsTest.FailureOnOddInvocations.`new`
//    val callable = Functions.asCallable(failableCallable)
//    val e = assertThrows(classOf[UndeclaredThrowableException], callable.call)
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = null
//    try instance = callable.call
//    catch {
//      case ex: Exception =>
//        throw Functions.rethrow(ex)
//    }
//    assertNotNull(instance)
//  }
//
//  @Test def testAsConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](ise)
//    val consumer = Functions.asConsumer(FunctionsTest.Testable.test)
//    var e = assertThrows(classOf[IllegalStateException], () => consumer.accept(testable))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => consumer.accept(testable))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => consumer.accept(testable))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    Functions.accept(FunctionsTest.Testable.test, testable)
//  }
//
//  @Test def testAsRunnable() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val runnable = Functions.asRunnable(FunctionsTest.FailureOnOddInvocations.`new`)
//    val e = assertThrows(classOf[UndeclaredThrowableException], runnable.run)
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    // Even invocations, should not throw an exception
//    runnable.run()
//  }
//
//  @Test def testAsSupplier() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failableSupplier = FunctionsTest.FailureOnOddInvocations.`new`
//    val supplier = Functions.asSupplier(failableSupplier)
//    val e = assertThrows(classOf[UndeclaredThrowableException], supplier.get)
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    assertNotNull(supplier.get)
//  }
//
//  @Test def testBiConsumer() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failableBiConsumer = (t, th) => {
//      def foo(t, th) = {
//        t.setThrowable(th)
//        t.test
//      }
//
//      foo(t, th)
//    }
//    val consumer = Functions.asBiConsumer(failableBiConsumer)
//    var e = assertThrows(classOf[IllegalStateException], () => consumer.accept(testable, ise))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    e = assertThrows(classOf[OutOfMemoryError], () => consumer.accept(testable, error))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => consumer.accept(testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    consumer.accept(testable, null)
//  }
//
//  @Test def testBiFunction() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](ise)
//    val failableBiFunction = (t, th) => {
//      def foo(t, th) = {
//        t.setThrowable(th)
//        Integer.valueOf(t.testAsInteger)
//      }
//
//      foo(t, th)
//    }
//    val biFunction = Functions.asBiFunction(failableBiFunction)
//    var e = assertThrows(classOf[IllegalStateException], () => biFunction.apply(testable, ise))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => biFunction.apply(testable, error))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => biFunction.apply(testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertEquals(0, biFunction.apply(testable, null).intValue)
//  }
//
//  @Test
//  @DisplayName("Test that asPredicate(FailableBiPredicate) is converted to -> BiPredicate ") def testBiPredicate() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failableBiPredicate = (t1, t2) => FunctionsTest.FailureOnOddInvocations.failingBool
//    val predicate = Functions.asBiPredicate(failableBiPredicate)
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => predicate.test(null, null))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = predicate.test(null, null)
//    assertNotNull(instance)
//  }
//
//  @Test def testCallable() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => Functions.run(FunctionsTest.FailureOnOddInvocations.`new`))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = Functions.call(FunctionsTest.FailureOnOddInvocations.`new`)
//    assertNotNull(instance)
//  }
//
//  @Test def testConstructor() = { // We allow this, which must have been an omission to make the ctor private.
//    // We could make the ctor private in 4.0.
//    new Nothing
//  }
//
//  @Test def testFunction() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](ise)
//    val failableFunction = (th) => {
//      def foo(th) = {
//        testable.setThrowable(th)
//        Integer.valueOf(testable.testAsInteger)
//      }
//
//      foo(th)
//    }
//    val function = Functions.asFunction(failableFunction)
//    var e = assertThrows(classOf[IllegalStateException], () => function.apply(ise))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => function.apply(error))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => function.apply(ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertEquals(0, function.apply(null).intValue)
//  }
//
//  @Test def testGetFromSupplier() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => Functions.run(FunctionsTest.FailureOnOddInvocations.`new`))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = Functions.call(FunctionsTest.FailureOnOddInvocations.`new`)
//    assertNotNull(instance)
//  }
//
//  @Test def testGetSupplier() = {
//    val ise = new IllegalStateException
//    val testable = new FunctionsTest.Testable[AnyRef, AnyRef](ise)
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.get(testable.testAsInteger))
//    assertSame(ise, e)
//    val error = new OutOfMemoryError
//    testable.setThrowable(error)
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.get(testable.testAsInteger))
//    assertSame(error, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Functions.get(testable.testAsInteger))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    val i = Functions.apply(FunctionsTest.Testable.testAsInteger, testable)
//    assertNotNull(i)
//    assertEquals(0, i.intValue)
//  }
//
//  @Test
//  @DisplayName("Test that asPredicate(FailablePredicate) is converted to -> Predicate ") def testPredicate() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failablePredicate = (t) => FunctionsTest.FailureOnOddInvocations.failingBool
//    val predicate = Functions.asPredicate(failablePredicate)
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => predicate.test(null))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = predicate.test(null)
//    assertNotNull(instance)
//  }
//
//  @Test def testRunnable() = {
//    FunctionsTest.FailureOnOddInvocations.invocations = 0
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => Functions.run(FunctionsTest.FailureOnOddInvocations.`new`))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    Functions.run(FunctionsTest.FailureOnOddInvocations.`new`)
//  }
//
//  /**
//    * Tests that our failable interface is properly defined to throw any exception. using the top level generic types
//    * Object and Throwable.
//    */
//  @Test def testThrows_FailableBiConsumer_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def accept(object1: Any, object2: Any) = throw new IOException("test")
//  }
//
//  /**
//    * Tests that our failable interface is properly defined to throw any exception using String and IOExceptions as
//    * generic test types.
//    */
//  @Test def testThrows_FailableBiConsumer_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def accept(object1: String, object2: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableBiFunction_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def apply(input1: Any, input2: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableBiFunction_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def apply(input1: String, input2: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableBiPredicate_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def test(object1: Any, object2: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableBiPredicate_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def test(object1: String, object2: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableCallable_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def call = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableCallable_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def call = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableConsumer_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def accept(`object`: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableConsumer_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def accept(`object`: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableFunction_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def apply(input: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableFunction_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def apply(input: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailablePredicate_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def test(`object`: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailablePredicate_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def test(`object`: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableRunnable_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def run() = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableRunnable_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def run() = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableSupplier_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def get = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableSupplier_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def get = throw new IOException("test")
//  }
//
//  @Test def testTryWithResources() = {
//    val co = new FunctionsTest.CloseableObject
//    val consumer = co.run
//    val ise = new IllegalStateException
//    var e = assertThrows(classOf[IllegalStateException], () => Functions.tryWithResources(() => consumer.accept(ise), co.close))
//    assertSame(ise, e)
//    assertTrue(co.isClosed)
//    co.reset()
//    val error = new OutOfMemoryError
//    e = assertThrows(classOf[OutOfMemoryError], () => Functions.tryWithResources(() => consumer.accept(error), co.close))
//    assertSame(error, e)
//    assertTrue(co.isClosed)
//    co.reset()
//    val ioe = new IOException("Unknown I/O error")
//    val uioe = assertThrows(classOf[UncheckedIOException], () => Functions.tryWithResources(() => consumer.accept(ioe), co.close))
//    val cause = uioe.getCause
//    assertSame(ioe, cause)
//    assertTrue(co.isClosed)
//    co.reset()
//    Functions.tryWithResources(() => consumer.accept(null), co.close)
//    assertTrue(co.isClosed)
//  }
//}
