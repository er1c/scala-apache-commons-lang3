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

//package org.apache.commons.lang3.function
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
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
//import org.junit.jupiter.api.DisplayName
//import org.junit.Test
//
///**
//  * Tests "failable" interfaces defined in this package.
//  */
//object FailableFunctionsTest {
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
//    private[function] def failingBool = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private[function] def testDouble(value: Double) = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private[function] def testInt(value: Int) = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private[function] def testLong(value: Long) = {
//      throwOnOdd()
//      true
//    }
//
//    @throws[SomeException]
//    private def throwOnOdd() = {
//      val i = {
//        invocations += 1; invocations
//      }
//      if (i % 2 == 1) throw new FailableFunctionsTest.SomeException("Odd Invocation: " + i)
//    }
//  }
//
//  class FailureOnOddInvocations @throws[SomeException]
//
//  private[function
//  ] () {
//    FailureOnOddInvocations.throwOnOdd()
//
//    @throws[SomeException]
//    private[function] def getAsBoolean = {
//      FailureOnOddInvocations.throwOnOdd()
//      true
//    }
//  }
//
//  @SerialVersionUID(-4965704778119283411L)
//  class SomeException private[function](val message: String) extends Exception(message) {
//    private var t = null
//
//    def setThrowable(throwable: Throwable) = t = throwable
//
//    @throws[Throwable]
//    def test() = if (t != null) throw t
//  }
//
//  class Testable[T, P] private[function](var throwable: Throwable) {
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
//  private val ERROR = new OutOfMemoryError
//  private val ILLEGAL_STATE_EXCEPTION = new IllegalStateException
//}
//
//class FailableFunctionsTest {
//  @Test def testAcceptBiConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(FailableFunctionsTest.Testable.test, testable, FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(FailableFunctionsTest.Testable.test, testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(FailableFunctionsTest.Testable.test, testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    Failable.accept(FailableFunctionsTest.Testable.test, testable, null.asInstanceOf[Throwable])
//  }
//
//  @Test def testAcceptConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(FailableFunctionsTest.Testable.test, testable))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(FailableFunctionsTest.Testable.test, testable))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(FailableFunctionsTest.Testable.test, testable))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    Failable.accept(FailableFunctionsTest.Testable.test, testable)
//  }
//
//  @Test def testAcceptDoubleConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, Double](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(testable.testDouble, 1d))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(testable.testDouble, 1d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(testable.testDouble, 1d))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Failable.accept(testable.testDouble, 1d)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptIntConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, Integer](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(testable.testInt, 1))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(testable.testInt, 1))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(testable.testInt, 1))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Failable.accept(testable.testInt, 1)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptLongConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, Long](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(testable.testLong, 1L))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(testable.testLong, 1L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(testable.testLong, 1L))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Failable.accept(testable.testLong, 1L)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptObjDoubleConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[String, Double](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(testable.testObjDouble, "X", 1d))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(testable.testObjDouble, "X", 1d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(testable.testObjDouble, "X", 1d))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Failable.accept(testable.testObjDouble, "X", 1d)
//    assertEquals("X", testable.getAcceptedObject)
//    assertEquals(1d, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptObjIntConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[String, Integer](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(testable.testObjInt, "X", 1))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(testable.testObjInt, "X", 1))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(testable.testObjInt, "X", 1))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Failable.accept(testable.testObjInt, "X", 1)
//    assertEquals("X", testable.getAcceptedObject)
//    assertEquals(1, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testAcceptObjLongConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[String, Long](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.accept(testable.testObjLong, "X", 1L))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.accept(testable.testObjLong, "X", 1L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.accept(testable.testObjLong, "X", 1L))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertNull(testable.getAcceptedObject)
//    assertNull(testable.getAcceptedPrimitiveObject1)
//    testable.setThrowable(null)
//    Failable.accept(testable.testObjLong, "X", 1L)
//    assertEquals("X", testable.getAcceptedObject)
//    assertEquals(1L, testable.getAcceptedPrimitiveObject1)
//  }
//
//  @Test def testApplyBiFunction() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable, FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    val i = Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable, null.asInstanceOf[Throwable])
//    assertNotNull(i)
//    assertEquals(0, i.intValue)
//  }
//
//  @Test def testApplyDoubleBinaryOperator() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, Double](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    val e = assertThrows(classOf[IllegalStateException], () => Failable.applyAsDouble(testable.testDoubleDouble, 1d, 2d))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    val testable2 = new FailableFunctionsTest.Testable[AnyRef, Double](null)
//    val i = Failable.applyAsDouble(testable2.testDoubleDouble, 1d, 2d)
//    assertEquals(3d, i)
//  }
//
//  @Test def testApplyFunction() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    val i = Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable)
//    assertNotNull(i)
//    assertEquals(0, i.intValue)
//  }
//
//  @Test def testAsCallable() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failableCallable = FailableFunctionsTest.FailureOnOddInvocations.`new`
//    val callable = Failable.asCallable(failableCallable)
//    val e = assertThrows(classOf[UndeclaredThrowableException], callable.call)
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = null
//    try instance = callable.call
//    catch {
//      case ex: Exception =>
//        throw Failable.rethrow(ex)
//    }
//    assertNotNull(instance)
//  }
//
//  @Test def testAsConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    val consumer = Failable.asConsumer(FailableFunctionsTest.Testable.test)
//    var e = assertThrows(classOf[IllegalStateException], () => consumer.accept(testable))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => consumer.accept(testable))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => consumer.accept(testable))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    Failable.accept(FailableFunctionsTest.Testable.test, testable)
//  }
//
//  @Test def testAsRunnable() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val runnable = Failable.asRunnable(FailableFunctionsTest.FailureOnOddInvocations.`new`)
//    val e = assertThrows(classOf[UndeclaredThrowableException], runnable.run)
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    // Even invocations, should not throw an exception
//    runnable.run()
//  }
//
//  @Test def testAsSupplier() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failableSupplier = FailableFunctionsTest.FailureOnOddInvocations.`new`
//    val supplier = Failable.asSupplier(failableSupplier)
//    val e = assertThrows(classOf[UndeclaredThrowableException], supplier.get)
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    assertNotNull(supplier.get)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testBiConsumer() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failableBiConsumer = (t, th) => {
//      def foo(t, th) = {
//        t.setThrowable(th)
//        t.test
//      }
//
//      foo(t, th)
//    }
//    val consumer = Failable.asBiConsumer(failableBiConsumer)
//    var e = assertThrows(classOf[IllegalStateException], () => consumer.accept(testable, FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => consumer.accept(testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failableBiConsumer.accept(testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => consumer.accept(testable, ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    consumer.accept(testable, null)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testBiConsumerAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t, th) => {
//      def foo(t, th) = {
//        t.setThrowable(th)
//        t.test
//      }
//
//      foo(t, th)
//    }
//    val nop = FailableBiConsumer.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).accept(testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).accept(testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    // Does not throw
//    nop.andThen(nop)
//    // Documented in Javadoc edge-case.
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test def testBiFunction() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    val failableBiFunction = (t, th) => {
//      def foo(t, th) = {
//        t.setThrowable(th)
//        t.testAsInteger
//      }
//
//      foo(t, th)
//    }
//    val biFunction = Failable.asBiFunction(failableBiFunction)
//    var e = assertThrows(classOf[IllegalStateException], () => biFunction.apply(testable, FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => biFunction.apply(testable, FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
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
//  @throws[IOException]
//  def testBiFunctionAndThen() = { // Unchecked usage pattern in JRE
//    val nopBiFunction = (t: Any, u: Integer) => null
//    val nopFunction = (t: Any) => null
//    nopBiFunction.andThen(nopFunction)
//    // Checked usage pattern
//    val failingBiFunctionTest = (t, u) => {
//      def foo(t, u) =
//        throw new IOException
//
//      foo(t, u)
//    }
//    val failingFunction = (t) => {
//      def foo(t) =
//        throw new IOException
//
//      foo(t)
//    }
//    val nopFailableBiFunction = FailableBiFunction.nop
//    val nopFailableFunction = FailableFunction.nop
//    //
//    assertThrows(classOf[IOException], () => failingBiFunctionTest.andThen(failingFunction).apply(null, null))
//    assertThrows(classOf[IOException], () => failingBiFunctionTest.andThen(nopFailableFunction).apply(null, null))
//    assertThrows(classOf[IOException], () => nopFailableBiFunction.andThen(failingFunction).apply(null, null))
//    nopFailableBiFunction.andThen(nopFailableFunction).apply(null, null)
//    assertThrows(classOf[NullPointerException], () => failingBiFunctionTest.andThen(null))
//  }
//
//  @Test
//  @DisplayName("Test that asPredicate(FailableBiPredicate) is converted to -> BiPredicate ") def testBiPredicate() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failableBiPredicate = (t1, t2) => FailableFunctionsTest.FailureOnOddInvocations.failingBool
//    val predicate = Failable.asBiPredicate(failableBiPredicate)
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => predicate.test(null, null))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    assertTrue(predicate.test(null, null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testBiPredicateAnd() = {
//    assertTrue(FailableBiPredicate.TRUE.and(FailableBiPredicate.TRUE).test(null, null))
//    assertFalse(FailableBiPredicate.TRUE.and(FailableBiPredicate.FALSE).test(null, null))
//    assertFalse(FailableBiPredicate.FALSE.and(FailableBiPredicate.TRUE).test(null, null))
//    assertFalse(FailableBiPredicate.FALSE.and(FailableBiPredicate.FALSE).test(null, null))
//    // null tests
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableBiPredicate.falsePredicate.and(null).test(null, null)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableBiPredicate.truePredicate.and(null).test(null, null)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testBiPredicateNegate() = {
//    assertFalse(FailableBiPredicate.TRUE.negate.test(null, null))
//    assertFalse(FailableBiPredicate.truePredicate.negate.test(null, null))
//    assertTrue(FailableBiPredicate.FALSE.negate.test(null, null))
//    assertTrue(FailableBiPredicate.falsePredicate.negate.test(null, null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testBiPredicateOr() = {
//    assertTrue(FailableBiPredicate.TRUE.or(FailableBiPredicate.TRUE).test(null, null))
//    assertTrue(FailableBiPredicate.TRUE.or(FailableBiPredicate.FALSE).test(null, null))
//    assertTrue(FailableBiPredicate.FALSE.or(FailableBiPredicate.TRUE).test(null, null))
//    assertFalse(FailableBiPredicate.FALSE.or(FailableBiPredicate.FALSE).test(null, null))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableBiPredicate.falsePredicate.or(null).test(null, null)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableBiPredicate.truePredicate.or(null).test(null, null)))
//  }
//
//  @Test def testCallable() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => Failable.run(FailableFunctionsTest.FailureOnOddInvocations.`new`))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = Failable.call(FailableFunctionsTest.FailureOnOddInvocations.`new`)
//    assertNotNull(instance)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testConsumerAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failableConsumer = (th) => {
//      def foo(th) = {
//        testable.setThrowable(th)
//        testable.test()
//      }
//
//      foo(th)
//    }
//    val nop = FailableConsumer.nop
//    val e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failableConsumer).accept(FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failableConsumer.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoubleConsumerAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//      }
//
//      foo(t)
//    }
//    val nop = FailableDoubleConsumer.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).accept(0d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).accept(0d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoublePredicate() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failablePredicate = FailableFunctionsTest.FailureOnOddInvocations.testDouble
//    assertThrows(classOf[FailableFunctionsTest.SomeException], () => failablePredicate.test(1d))
//    failablePredicate.test(1d)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoublePredicateAnd() = {
//    assertTrue(FailableDoublePredicate.TRUE.and(FailableDoublePredicate.TRUE).test(0))
//    assertFalse(FailableDoublePredicate.TRUE.and(FailableDoublePredicate.FALSE).test(0))
//    assertFalse(FailableDoublePredicate.FALSE.and(FailableDoublePredicate.TRUE).test(0))
//    assertFalse(FailableDoublePredicate.FALSE.and(FailableDoublePredicate.FALSE).test(0))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableDoublePredicate.falsePredicate.and(null).test(0)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableDoublePredicate.truePredicate.and(null).test(0)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoublePredicateNegate() = {
//    assertFalse(FailableDoublePredicate.TRUE.negate.test(0d))
//    assertFalse(FailableDoublePredicate.truePredicate.negate.test(0d))
//    assertTrue(FailableDoublePredicate.FALSE.negate.test(0d))
//    assertTrue(FailableDoublePredicate.falsePredicate.negate.test(0d))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoublePredicateOr() = {
//    assertTrue(FailableDoublePredicate.TRUE.or(FailableDoublePredicate.TRUE).test(0))
//    assertTrue(FailableDoublePredicate.TRUE.or(FailableDoublePredicate.FALSE).test(0))
//    assertTrue(FailableDoublePredicate.FALSE.or(FailableDoublePredicate.TRUE).test(0))
//    assertFalse(FailableDoublePredicate.FALSE.or(FailableDoublePredicate.FALSE).test(0))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableDoublePredicate.falsePredicate.or(null).test(0)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableDoublePredicate.truePredicate.or(null).test(0)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoubleUnaryOperatorAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0d
//      }
//
//      foo(t)
//    }
//    val nop = FailableDoubleUnaryOperator.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).applyAsDouble(0d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).applyAsDouble(0d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoubleUnaryOperatorCompose() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0d
//      }
//
//      foo(t)
//    }
//    val nop = FailableDoubleUnaryOperator.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.compose(failing).applyAsDouble(0d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.compose(nop).applyAsDouble(0d))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => failing.compose(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testDoubleUnaryOperatorIdentity() = {
//    val nop = FailableDoubleUnaryOperator.identity
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => nop.compose(null))
//  }
//
//  @Test def testFunction() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    val failableFunction = (th) => {
//      def foo(th) = {
//        testable.setThrowable(th)
//        testable.testAsInteger
//      }
//
//      foo(th)
//    }
//    val function = Failable.asFunction(failableFunction)
//    var e = assertThrows(classOf[IllegalStateException], () => function.apply(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => function.apply(FailableFunctionsTest.ERROR))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => function.apply(ioe))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    assertEquals(0, function.apply(null).intValue)
//  }
//
//  @Test
//  @throws[IOException]
//  def testFunctionAndThen() = {
//    val nopFunction = (t: Any) => null
//    nopFunction.andThen(nopFunction)
//    val failingFunction = (t) => {
//      def foo(t) =
//        throw new IOException
//
//      foo(t)
//    }
//    val nopFailableFunction = FailableFunction.nop
//    assertThrows(classOf[IOException], () => failingFunction.andThen(failingFunction).apply(null))
//    assertThrows(classOf[IOException], () => failingFunction.andThen(nopFailableFunction).apply(null))
//    assertThrows(classOf[IOException], () => nopFailableFunction.andThen(failingFunction).apply(null))
//    nopFailableFunction.andThen(nopFailableFunction).apply(null)
//    assertThrows(classOf[NullPointerException], () => failingFunction.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testFunctionCompose() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0
//      }
//
//      foo(t)
//    }
//    val nop = FailableFunction.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.compose(failing).apply(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.compose(nop).apply(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => failing.compose(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testFunctionIdentity() = {
//    val nop = FailableFunction.identity
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => nop.compose(null))
//  }
//
//  @Test def testGetAsBooleanSupplier() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.getAsBoolean(testable.testAsBooleanPrimitive))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.getAsBoolean(testable.testAsBooleanPrimitive))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.getAsBoolean(testable.testAsBooleanPrimitive))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    assertFalse(Failable.getAsBoolean(testable.testAsBooleanPrimitive))
//  }
//
//  @Test def testGetAsDoubleSupplier() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.getAsDouble(testable.testAsDoublePrimitive))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.getAsDouble(testable.testAsDoublePrimitive))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.getAsDouble(testable.testAsDoublePrimitive))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    assertEquals(0, Failable.getAsDouble(testable.testAsDoublePrimitive))
//  }
//
//  @Test def testGetAsIntSupplier() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.getAsInt(testable.testAsIntPrimitive))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.getAsInt(testable.testAsIntPrimitive))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.getAsInt(testable.testAsIntPrimitive))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    val i = Failable.getAsInt(testable.testAsInteger)
//    assertEquals(0, i)
//  }
//
//  @Test def testGetAsLongSupplier() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.getAsLong(testable.testAsLongPrimitive))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.getAsLong(testable.testAsLongPrimitive))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.getAsLong(testable.testAsLongPrimitive))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    val i = Failable.getAsLong(testable.testAsLongPrimitive)
//    assertEquals(0, i)
//  }
//
//  @Test def testGetFromSupplier() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => Failable.run(FailableFunctionsTest.FailureOnOddInvocations.`new`))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = Failable.call(FailableFunctionsTest.FailureOnOddInvocations.`new`)
//    assertNotNull(instance)
//  }
//
//  @Test def testGetSupplier() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION)
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.get(testable.testAsInteger))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    testable.setThrowable(FailableFunctionsTest.ERROR)
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.get(testable.testAsInteger))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    val ioe = new IOException("Unknown I/O error")
//    testable.setThrowable(ioe)
//    e = assertThrows(classOf[UncheckedIOException], () => Failable.get(testable.testAsInteger))
//    val t = e.getCause
//    assertNotNull(t)
//    assertSame(ioe, t)
//    testable.setThrowable(null)
//    val i = Failable.apply(FailableFunctionsTest.Testable.testAsInteger, testable)
//    assertNotNull(i)
//    assertEquals(0, i.intValue)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntConsumerAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//      }
//
//      foo(t)
//    }
//    val nop = FailableIntConsumer.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).accept(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).accept(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntPredicate() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failablePredicate = FailableFunctionsTest.FailureOnOddInvocations.testInt
//    assertThrows(classOf[FailableFunctionsTest.SomeException], () => failablePredicate.test(1))
//    failablePredicate.test(1)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntPredicateAnd() = {
//    assertTrue(FailableIntPredicate.TRUE.and(FailableIntPredicate.TRUE).test(0))
//    assertFalse(FailableIntPredicate.TRUE.and(FailableIntPredicate.FALSE).test(0))
//    assertFalse(FailableIntPredicate.FALSE.and(FailableIntPredicate.TRUE).test(0))
//    assertFalse(FailableIntPredicate.FALSE.and(FailableIntPredicate.FALSE).test(0))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableIntPredicate.falsePredicate.and(null).test(0)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableIntPredicate.truePredicate.and(null).test(0)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntPredicateNegate() = {
//    assertFalse(FailableIntPredicate.TRUE.negate.test(0))
//    assertFalse(FailableIntPredicate.truePredicate.negate.test(0))
//    assertTrue(FailableIntPredicate.FALSE.negate.test(0))
//    assertTrue(FailableIntPredicate.falsePredicate.negate.test(0))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntPredicateOr() = {
//    assertTrue(FailableIntPredicate.TRUE.or(FailableIntPredicate.TRUE).test(0))
//    assertTrue(FailableIntPredicate.TRUE.or(FailableIntPredicate.FALSE).test(0))
//    assertTrue(FailableIntPredicate.FALSE.or(FailableIntPredicate.TRUE).test(0))
//    assertFalse(FailableIntPredicate.FALSE.or(FailableIntPredicate.FALSE).test(0))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableIntPredicate.falsePredicate.or(null).test(0)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableIntPredicate.truePredicate.or(null).test(0)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntUnaryOperatorAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0
//      }
//
//      foo(t)
//    }
//    val nop = FailableIntUnaryOperator.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).applyAsInt(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).applyAsInt(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntUnaryOperatorCompose() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0
//      }
//
//      foo(t)
//    }
//    val nop = FailableIntUnaryOperator.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.compose(failing).applyAsInt(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.compose(nop).applyAsInt(0))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => failing.compose(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testIntUnaryOperatorIdentity() = {
//    val nop = FailableIntUnaryOperator.identity
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => nop.compose(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongConsumerAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//      }
//
//      foo(t)
//    }
//    val nop = FailableLongConsumer.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).accept(0L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).accept(0L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongPredicate() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failablePredicate = FailableFunctionsTest.FailureOnOddInvocations.testLong
//    assertThrows(classOf[FailableFunctionsTest.SomeException], () => failablePredicate.test(1L))
//    failablePredicate.test(1L)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongPredicateAnd() = {
//    assertTrue(FailableLongPredicate.TRUE.and(FailableLongPredicate.TRUE).test(0))
//    assertFalse(FailableLongPredicate.TRUE.and(FailableLongPredicate.FALSE).test(0))
//    assertFalse(FailableLongPredicate.FALSE.and(FailableLongPredicate.TRUE).test(0))
//    assertFalse(FailableLongPredicate.FALSE.and(FailableLongPredicate.FALSE).test(0))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableLongPredicate.falsePredicate.and(null).test(0)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableLongPredicate.truePredicate.and(null).test(0)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongPredicateNegate() = {
//    assertFalse(FailableLongPredicate.TRUE.negate.test(0L))
//    assertFalse(FailableLongPredicate.truePredicate.negate.test(0L))
//    assertTrue(FailableLongPredicate.FALSE.negate.test(0L))
//    assertTrue(FailableLongPredicate.falsePredicate.negate.test(0L))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongPredicateOr() = {
//    assertTrue(FailableLongPredicate.TRUE.or(FailableLongPredicate.TRUE).test(0))
//    assertTrue(FailableLongPredicate.TRUE.or(FailableLongPredicate.FALSE).test(0))
//    assertTrue(FailableLongPredicate.FALSE.or(FailableLongPredicate.TRUE).test(0))
//    assertFalse(FailableLongPredicate.FALSE.or(FailableLongPredicate.FALSE).test(0))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailableLongPredicate.falsePredicate.or(null).test(0)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailableLongPredicate.truePredicate.or(null).test(0)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongUnaryOperatorAndThen() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0L
//      }
//
//      foo(t)
//    }
//    val nop = FailableLongUnaryOperator.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.andThen(failing).applyAsLong(0L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.andThen(nop).applyAsLong(0L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.andThen(nop)
//    assertThrows(classOf[NullPointerException], () => failing.andThen(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongUnaryOperatorCompose() = {
//    val testable = new FailableFunctionsTest.Testable[AnyRef, AnyRef](null)
//    val failing = (t) => {
//      def foo(t) = {
//        testable.setThrowable(FailableFunctionsTest.ERROR)
//        testable.test()
//        0L
//      }
//
//      foo(t)
//    }
//    val nop = FailableLongUnaryOperator.nop
//    var e = assertThrows(classOf[OutOfMemoryError], () => nop.compose(failing).applyAsLong(0L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    e = assertThrows(classOf[OutOfMemoryError], () => failing.compose(nop).applyAsLong(0L))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => failing.compose(null))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testLongUnaryOperatorIdentity() = {
//    val nop = FailableLongUnaryOperator.identity
//    nop.compose(nop)
//    assertThrows(classOf[NullPointerException], () => nop.compose(null))
//  }
//
//  @Test
//  @DisplayName("Test that asPredicate(FailablePredicate) is converted to -> Predicate ") def testPredicate() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val failablePredicate = (t) => FailableFunctionsTest.FailureOnOddInvocations.failingBool
//    val predicate = Failable.asPredicate(failablePredicate)
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => predicate.test(null))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    val instance = predicate.test(null)
//    assertNotNull(instance)
//  }
//
//  @Test
//  @throws[Throwable]
//  def testPredicateAnd() = {
//    assertTrue(FailablePredicate.TRUE.and(FailablePredicate.TRUE).test(null))
//    assertFalse(FailablePredicate.TRUE.and(FailablePredicate.FALSE).test(null))
//    assertFalse(FailablePredicate.FALSE.and(FailablePredicate.TRUE).test(null))
//    assertFalse(FailablePredicate.FALSE.and(FailablePredicate.FALSE).test(null))
//    assertThrows(classOf[NullPointerException], () => assertFalse(FailablePredicate.FALSE.and(null).test(null)))
//    assertThrows(classOf[NullPointerException], () => assertTrue(FailablePredicate.TRUE.and(null).test(null)))
//  }
//
//  @Test
//  @throws[Throwable]
//  def testPredicateNegate() = {
//    assertFalse(FailablePredicate.TRUE.negate.test(null))
//    assertFalse(FailablePredicate.truePredicate.negate.test(null))
//    assertTrue(FailablePredicate.FALSE.negate.test(null))
//    assertTrue(FailablePredicate.falsePredicate.negate.test(null))
//  }
//
//  @Test def testRunnable() = {
//    FailableFunctionsTest.FailureOnOddInvocations.invocations = 0
//    val e = assertThrows(classOf[UndeclaredThrowableException], () => Failable.run(FailableFunctionsTest.FailureOnOddInvocations.`new`))
//    val cause = e.getCause
//    assertNotNull(cause)
//    assertTrue(cause.isInstanceOf[FailableFunctionsTest.SomeException])
//    assertEquals("Odd Invocation: 1", cause.getMessage)
//    Failable.run(FailableFunctionsTest.FailureOnOddInvocations.`new`)
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
//  @Test def testThrows_FailableBooleanSupplier_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def getAsBoolean = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableBooleanSupplier_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def getAsBoolean = throw new IOException("test")
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
//  @Test def testThrows_FailableDoubleBinaryOperator_Object_Throwable() = new FailableDoubleBinaryOperator[Throwable]() {
//    @throws[Throwable]
//    override def applyAsDouble(left: Double, right: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleBinaryOperator_String_IOException() = new FailableDoubleBinaryOperator[IOException]() {
//    @throws[IOException]
//    override def applyAsDouble(left: Double, right: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleConsumer_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def accept(value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleConsumer_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def accept(value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleFunction_Object_Throwable() = new FailableDoubleFunction[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def apply(input: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleFunction_String_IOException() = new FailableDoubleFunction[String, IOException]() {
//    @throws[IOException]
//    override def apply(input: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleSupplier_Object_Throwable() = new FailableDoubleSupplier[Throwable]() {
//    @throws[Throwable]
//    override def getAsDouble = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleSupplier_String_IOException() = new FailableDoubleSupplier[IOException]() {
//    @throws[IOException]
//    override def getAsDouble = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleToIntFunction_Object_Throwable() = new FailableDoubleToIntFunction[Throwable]() {
//    @throws[Throwable]
//    override def applyAsInt(value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleToIntFunction_String_IOException() = new FailableDoubleToIntFunction[IOException]() {
//    @throws[IOException]
//    override def applyAsInt(value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleToLongFunction_Object_Throwable() = new FailableDoubleToLongFunction[Throwable]() {
//    @throws[Throwable]
//    override def applyAsLong(value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableDoubleToLongFunction_String_IOException() = new FailableDoubleToLongFunction[IOException]() {
//    @throws[IOException]
//    override def applyAsLong(value: Double) = throw new IOException("test")
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
//  @Test def testThrows_FailableIntBinaryOperator_Object_Throwable() = new FailableIntBinaryOperator[Throwable]() {
//    @throws[Throwable]
//    override def applyAsInt(left: Int, right: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntBinaryOperator_String_IOException() = new FailableIntBinaryOperator[IOException]() {
//    @throws[IOException]
//    override def applyAsInt(left: Int, right: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntConsumer_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def accept(value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntConsumer_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def accept(value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntFunction_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def apply(input: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntFunction_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def apply(input: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntSupplier_Object_Throwable() = new FailableIntSupplier[Throwable]() {
//    @throws[Throwable]
//    override def getAsInt = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntSupplier_String_IOException() = new FailableIntSupplier[IOException]() {
//    @throws[IOException]
//    override def getAsInt = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntToDoubleFunction_Object_Throwable() = new FailableIntToDoubleFunction[Throwable]() {
//    @throws[Throwable]
//    override def applyAsDouble(value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntToDoubleFunction_String_IOException() = new FailableIntToDoubleFunction[IOException]() {
//    @throws[IOException]
//    override def applyAsDouble(value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntToLongFunction_Object_Throwable() = new FailableIntToLongFunction[Throwable]() {
//    @throws[Throwable]
//    override def applyAsLong(value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableIntToLongFunction_String_IOException() = new FailableIntToLongFunction[IOException]() {
//    @throws[IOException]
//    override def applyAsLong(value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongBinaryOperator_Object_Throwable() = new FailableLongBinaryOperator[Throwable]() {
//    @throws[Throwable]
//    override def applyAsLong(left: Long, right: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongBinaryOperator_String_IOException() = new FailableLongBinaryOperator[IOException]() {
//    @throws[IOException]
//    override def applyAsLong(left: Long, right: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongConsumer_Object_Throwable() = new Nothing() {
//    @throws[Throwable]
//    def accept(`object`: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongConsumer_String_IOException() = new Nothing() {
//    @throws[IOException]
//    def accept(`object`: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongFunction_Object_Throwable() = new FailableLongFunction[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def apply(input: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongFunction_String_IOException() = new FailableLongFunction[String, IOException]() {
//    @throws[IOException]
//    override def apply(input: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongSupplier_Object_Throwable() = new FailableLongSupplier[Throwable]() {
//    @throws[Throwable]
//    override def getAsLong = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongSupplier_String_IOException() = new FailableLongSupplier[IOException]() {
//    @throws[IOException]
//    override def getAsLong = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongToDoubleFunction_Object_Throwable() = new FailableLongToDoubleFunction[Throwable]() {
//    @throws[Throwable]
//    override def applyAsDouble(value: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongToDoubleFunction_String_IOException() = new FailableLongToDoubleFunction[IOException]() {
//    @throws[IOException]
//    override def applyAsDouble(value: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongToIntFunction_Object_Throwable() = new FailableLongToIntFunction[Throwable]() {
//    @throws[Throwable]
//    override def applyAsInt(value: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableLongToIntFunction_String_IOException() = new FailableLongToIntFunction[IOException]() {
//    @throws[IOException]
//    override def applyAsInt(value: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableObjDoubleConsumer_Object_Throwable() = new FailableObjDoubleConsumer[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def accept(`object`: Any, value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableObjDoubleConsumer_String_IOException() = new FailableObjDoubleConsumer[String, IOException]() {
//    @throws[IOException]
//    override def accept(`object`: String, value: Double) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableObjIntConsumer_Object_Throwable() = new FailableObjIntConsumer[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def accept(`object`: Any, value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableObjIntConsumer_String_IOException() = new FailableObjIntConsumer[String, IOException]() {
//    @throws[IOException]
//    override def accept(`object`: String, value: Int) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableObjLongConsumer_Object_Throwable() = new FailableObjLongConsumer[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def accept(`object`: Any, value: Long) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableObjLongConsumer_String_IOException() = new FailableObjLongConsumer[String, IOException]() {
//    @throws[IOException]
//    override def accept(`object`: String, value: Long) = throw new IOException("test")
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
//  @Test def testThrows_FailableRunnable_Object_Throwable() = new FailableRunnable[Throwable]() {
//    @throws[Throwable]
//    override def run() = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableRunnable_String_IOException() = new FailableRunnable[IOException]() {
//    @throws[IOException]
//    override def run() = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableSupplier_Object_Throwable() = new FailableSupplier[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def get = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableSupplier_String_IOException() = new FailableSupplier[String, IOException]() {
//    @throws[IOException]
//    override def get = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToDoubleBiFunction_Object_Throwable() = new FailableToDoubleBiFunction[AnyRef, AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def applyAsDouble(t: Any, u: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToDoubleBiFunction_String_IOException() = new FailableToDoubleBiFunction[String, String, IOException]() {
//    @throws[IOException]
//    override def applyAsDouble(t: String, u: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToDoubleFunction_Object_Throwable() = new FailableToDoubleFunction[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def applyAsDouble(t: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToDoubleFunction_String_IOException() = new FailableToDoubleFunction[String, IOException]() {
//    @throws[IOException]
//    override def applyAsDouble(t: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToIntBiFunction_Object_Throwable() = new FailableToIntBiFunction[AnyRef, AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def applyAsInt(t: Any, u: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToIntBiFunction_String_IOException() = new FailableToIntBiFunction[String, String, IOException]() {
//    @throws[IOException]
//    override def applyAsInt(t: String, u: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToIntFunction_Object_Throwable() = new FailableToIntFunction[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def applyAsInt(t: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToIntFunction_String_IOException() = new FailableToIntFunction[String, IOException]() {
//    @throws[IOException]
//    override def applyAsInt(t: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToLongBiFunction_Object_Throwable() = new FailableToLongBiFunction[AnyRef, AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def applyAsLong(t: Any, u: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToLongBiFunction_String_IOException() = new FailableToLongBiFunction[String, String, IOException]() {
//    @throws[IOException]
//    override def applyAsLong(t: String, u: String) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToLongFunction_Object_Throwable() = new FailableToLongFunction[AnyRef, Throwable]() {
//    @throws[Throwable]
//    override def applyAsLong(t: Any) = throw new IOException("test")
//  }
//
//  @Test def testThrows_FailableToLongFunction_String_IOException() = new FailableToLongFunction[String, IOException]() {
//    @throws[IOException]
//    override def applyAsLong(t: String) = throw new IOException("test")
//  }
//
//  @Test def testTryWithResources() = {
//    val closeable = new FailableFunctionsTest.CloseableObject
//    val consumer = closeable.run
//    var e = assertThrows(classOf[IllegalStateException], () => Failable.tryWithResources(() => consumer.accept(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION), closeable.close))
//    assertSame(FailableFunctionsTest.ILLEGAL_STATE_EXCEPTION, e)
//    assertTrue(closeable.isClosed)
//    closeable.reset()
//    e = assertThrows(classOf[OutOfMemoryError], () => Failable.tryWithResources(() => consumer.accept(FailableFunctionsTest.ERROR), closeable.close))
//    assertSame(FailableFunctionsTest.ERROR, e)
//    assertTrue(closeable.isClosed)
//    closeable.reset()
//    val ioe = new IOException("Unknown I/O error")
//    val uioe = assertThrows(classOf[UncheckedIOException], () => Failable.tryWithResources(() => consumer.accept(ioe), closeable.close))
//    val cause = uioe.getCause
//    assertSame(ioe, cause)
//    assertTrue(closeable.isClosed)
//    closeable.reset()
//    Failable.tryWithResources(() => consumer.accept(null), closeable.close)
//    assertTrue(closeable.isClosed)
//  }
//}
