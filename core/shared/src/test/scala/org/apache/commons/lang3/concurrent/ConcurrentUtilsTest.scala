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

//package org.apache.commons.lang3.concurrent
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util.concurrent.ConcurrentHashMap
//import java.util.concurrent.ConcurrentMap
//import java.util.concurrent.ExecutionException
//import java.util.concurrent.Future
//import java.util.concurrent.TimeUnit
//import org.easymock.EasyMock
//import org.junit.Test
//
///**
//  * Test class for {@link ConcurrentUtils}.
//  */
//class ConcurrentUtilsTest {
//  /**
//    * Tests creating a ConcurrentException with a runtime exception as cause.
//    */
//    @Test def testConcurrentExceptionCauseUnchecked() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(new RuntimeException))
//
//  /**
//    * Tests creating a ConcurrentException with an error as cause.
//    */
//  @Test def testConcurrentExceptionCauseError() = assertThrows(classOf[IllegalArgumentException], () => new Nothing("An error", new Error))
//
//  /**
//    * Tests creating a ConcurrentException with null as cause.
//    */
//  @Test def testConcurrentExceptionCauseNull() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(null))
//
//  /**
//    * Tries to create a ConcurrentRuntimeException with a runtime as cause.
//    */
//  @Test def testConcurrentRuntimeExceptionCauseUnchecked() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(new RuntimeException))
//
//  /**
//    * Tries to create a ConcurrentRuntimeException with an error as cause.
//    */
//  @Test def testConcurrentRuntimeExceptionCauseError() = assertThrows(classOf[IllegalArgumentException], () => new Nothing("An error", new Error))
//
//  /**
//    * Tries to create a ConcurrentRuntimeException with null as cause.
//    */
//  @Test def testConcurrentRuntimeExceptionCauseNull() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(null))
//
//  /**
//    * Tests extractCause() for a null exception.
//    */
//  @Test def testExtractCauseNull() = assertNull(ConcurrentUtils.extractCause(null), "Non null result")
//
//  /**
//    * Tests extractCause() if the cause of the passed in exception is null.
//    */
//  @Test def testExtractCauseNullCause() = assertNull(ConcurrentUtils.extractCause(new ExecutionException("Test", null)), "Non null result")
//
//  /**
//    * Tests extractCause() if the cause is an error.
//    */
//  @Test def testExtractCauseError() = {
//    val err = new AssertionError("Test")
//    val e = assertThrows(classOf[AssertionError], () => ConcurrentUtils.extractCause(new ExecutionException(err)))
//    assertEquals(err, e, "Wrong error")
//  }
//
//  /**
//    * Tests extractCause() if the cause is an unchecked exception.
//    */
//  @Test def testExtractCauseUncheckedException() = {
//    val rex = new RuntimeException("Test")
//    assertThrows(classOf[RuntimeException], () => ConcurrentUtils.extractCause(new ExecutionException(rex)))
//  }
//
//  /**
//    * Tests extractCause() if the cause is a checked exception.
//    */
//  @Test def testExtractCauseChecked() = {
//    val ex = new Exception("Test")
//    val cex = ConcurrentUtils.extractCause(new ExecutionException(ex))
//    assertSame(ex, cex.getCause, "Wrong cause")
//  }
//
//  /**
//    * Tests extractCauseUnchecked() for a null exception.
//    */
//  @Test def testExtractCauseUncheckedNull() = assertNull(ConcurrentUtils.extractCauseUnchecked(null), "Non null result")
//
//  /**
//    * Tests extractCauseUnchecked() if the cause of the passed in exception is null.
//    */
//  @Test def testExtractCauseUncheckedNullCause() = assertNull(ConcurrentUtils.extractCauseUnchecked(new ExecutionException("Test", null)), "Non null result")
//
//  /**
//    * Tests extractCauseUnchecked() if the cause is an error.
//    */
//  @Test def testExtractCauseUncheckedError() = {
//    val err = new AssertionError("Test")
//    val e = assertThrows(classOf[Error], () => ConcurrentUtils.extractCauseUnchecked(new ExecutionException(err)))
//    assertEquals(err, e, "Wrong error")
//  }
//
//  /**
//    * Tests extractCauseUnchecked() if the cause is an unchecked exception.
//    */
//  @Test def testExtractCauseUncheckedUncheckedException() = {
//    val rex = new RuntimeException("Test")
//    val r = assertThrows(classOf[RuntimeException], () => ConcurrentUtils.extractCauseUnchecked(new ExecutionException(rex)))
//    assertEquals(rex, r, "Wrong exception")
//  }
//
//  /**
//    * Tests extractCauseUnchecked() if the cause is a checked exception.
//    */
//  @Test def testExtractCauseUncheckedChecked() = {
//    val ex = new Exception("Test")
//    val cex = ConcurrentUtils.extractCauseUnchecked(new ExecutionException(ex))
//    assertSame(ex, cex.getCause, "Wrong cause")
//  }
//
//  /**
//    * Tests handleCause() if the cause is an error.
//    */
//  @Test def testHandleCauseError() = {
//    val err = new AssertionError("Test")
//    val e = assertThrows(classOf[Error], () => ConcurrentUtils.handleCause(new ExecutionException(err)))
//    assertEquals(err, e, "Wrong error")
//  }
//
//  /**
//    * Tests handleCause() if the cause is an unchecked exception.
//    */
//  @Test def testHandleCauseUncheckedException() = {
//    val rex = new RuntimeException("Test")
//    val r = assertThrows(classOf[RuntimeException], () => ConcurrentUtils.handleCause(new ExecutionException(rex)))
//    assertEquals(rex, r, "Wrong exception")
//  }
//
//  /**
//    * Tests handleCause() if the cause is a checked exception.
//    */
//  @Test def testHandleCauseChecked() = {
//    val ex = new Exception("Test")
//    val cex = assertThrows(classOf[Nothing], () => ConcurrentUtils.handleCause(new ExecutionException(ex)))
//    assertEquals(ex, cex.getCause, "Wrong cause")
//  }
//
//  /**
//    * Tests handleCause() for a null parameter or a null cause. In this case
//    * the method should do nothing. We can only test that no exception is
//    * thrown.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testHandleCauseNull() = {
//    ConcurrentUtils.handleCause(null)
//    ConcurrentUtils.handleCause(new ExecutionException("Test", null))
//  }
//
//  /**
//    * Tests handleCauseUnchecked() if the cause is an error.
//    */
//  @Test def testHandleCauseUncheckedError() = {
//    val err = new AssertionError("Test")
//    val e = assertThrows(classOf[Error], () => ConcurrentUtils.handleCauseUnchecked(new ExecutionException(err)))
//    assertEquals(err, e, "Wrong error")
//  }
//
//  /**
//    * Tests handleCauseUnchecked() if the cause is an unchecked exception.
//    */
//  @Test def testHandleCauseUncheckedUncheckedException() = {
//    val rex = new RuntimeException("Test")
//    val r = assertThrows(classOf[RuntimeException], () => ConcurrentUtils.handleCauseUnchecked(new ExecutionException(rex)))
//    assertEquals(rex, r, "Wrong exception")
//  }
//
//  /**
//    * Tests handleCauseUnchecked() if the cause is a checked exception.
//    */
//  @Test def testHandleCauseUncheckedChecked() = {
//    val ex = new Exception("Test")
//    val crex = assertThrows(classOf[Nothing], () => ConcurrentUtils.handleCauseUnchecked(new ExecutionException(ex)))
//    assertEquals(ex, crex.getCause, "Wrong cause")
//  }
//
//  /**
//    * Tests handleCauseUnchecked() for a null parameter or a null cause. In
//    * this case the method should do nothing. We can only test that no
//    * exception is thrown.
//    */
//  @Test def testHandleCauseUncheckedNull() = {
//    ConcurrentUtils.handleCauseUnchecked(null)
//    ConcurrentUtils.handleCauseUnchecked(new ExecutionException("Test", null))
//  }
//
//  /**
//    * Tests initialize() for a null argument.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeNull() = assertNull(ConcurrentUtils.initialize(null), "Got a result")
//
//  /**
//    * Tests a successful initialize() operation.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitialize() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    val result = new Any
//    EasyMock.expect(init.get).andReturn(result)
//    EasyMock.replay(init)
//    assertSame(result, ConcurrentUtils.initialize(init), "Wrong result object")
//    EasyMock.verify(init)
//  }
//
//  /**
//    * Tests initializeUnchecked() for a null argument.
//    */
//  @Test def testInitializeUncheckedNull() = assertNull(ConcurrentUtils.initializeUnchecked(null), "Got a result")
//
//  /**
//    * Tests creating ConcurrentRuntimeException with no arguments.
//    */
//  @Test def testUninitializedConcurrentRuntimeException() = assertNotNull(new Nothing, "Error creating empty ConcurrentRuntimeException")
//
//  /**
//    * Tests a successful initializeUnchecked() operation.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeUnchecked() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    val result = new Any
//    EasyMock.expect(init.get).andReturn(result)
//    EasyMock.replay(init)
//    assertSame(result, ConcurrentUtils.initializeUnchecked(init), "Wrong result object")
//    EasyMock.verify(init)
//  }
//
//  /**
//    * Tests whether exceptions are correctly handled by initializeUnchecked().
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeUncheckedEx() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    val cause = new Exception
//    EasyMock.expect(init.get).andThrow(new Nothing(cause))
//    EasyMock.replay(init)
//    val crex = assertThrows(classOf[Nothing], () => ConcurrentUtils.initializeUnchecked(init))
//    assertSame(cause, crex.getCause, "Wrong cause")
//    EasyMock.verify(init)
//  }
//
//  /**
//    * Tests constant future.
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testConstantFuture_Integer() = {
//    val value = Integer.valueOf(5)
//    val test = ConcurrentUtils.constantFuture(value)
//    assertTrue(test.isDone)
//    assertSame(value, test.get)
//    assertSame(value, test.get(1000, TimeUnit.SECONDS))
//    assertSame(value, test.get(1000, null))
//    assertFalse(test.isCancelled)
//    assertFalse(test.cancel(true))
//    assertFalse(test.cancel(false))
//  }
//
//  @Test
//  @throws[Exception]
//  def testConstantFuture_null() = {
//    val value = null
//    val test = ConcurrentUtils.constantFuture(value)
//    assertTrue(test.isDone)
//    assertSame(value, test.get)
//    assertSame(value, test.get(1000, TimeUnit.SECONDS))
//    assertSame(value, test.get(1000, null))
//    assertFalse(test.isCancelled)
//    assertFalse(test.cancel(true))
//    assertFalse(test.cancel(false))
//  }
//
//  /**
//    * Tests putIfAbsent() if the map contains the key in question.
//    */
//  @Test def testPutIfAbsentKeyPresent() = {
//    val key = "testKey"
//    val value = 42
//    val map = new ConcurrentHashMap[String, Integer]
//    map.put(key, value)
//    assertEquals(value, ConcurrentUtils.putIfAbsent(map, key, 0), "Wrong result")
//    assertEquals(value, map.get(key), "Wrong value in map")
//  }
//
//  /**
//    * Tests putIfAbsent() if the map does not contain the key in question.
//    */
//  @Test def testPutIfAbsentKeyNotPresent() = {
//    val key = "testKey"
//    val value = 42
//    val map = new ConcurrentHashMap[String, Integer]
//    assertEquals(value, ConcurrentUtils.putIfAbsent(map, key, value), "Wrong result")
//    assertEquals(value, map.get(key), "Wrong value in map")
//  }
//
//  /**
//    * Tests putIfAbsent() if a null map is passed in.
//    */
//  @Test def testPutIfAbsentNullMap() = assertNull(ConcurrentUtils.putIfAbsent(null, "test", 100), "Wrong result")
//
//  /**
//    * Tests createIfAbsent() if the key is found in the map.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testCreateIfAbsentKeyPresent() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    EasyMock.replay(init)
//    val key = "testKey"
//    val value = 42
//    val map = new ConcurrentHashMap[String, Integer]
//    map.put(key, value)
//    assertEquals(value, ConcurrentUtils.createIfAbsent(map, key, init), "Wrong result")
//    assertEquals(value, map.get(key), "Wrong value in map")
//    EasyMock.verify(init)
//  }
//
//  /**
//    * Tests createIfAbsent() if the map does not contain the key in question.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testCreateIfAbsentKeyNotPresent() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    val key = "testKey"
//    val value = 42
//    EasyMock.expect(init.get).andReturn(value)
//    EasyMock.replay(init)
//    val map = new ConcurrentHashMap[String, Integer]
//    assertEquals(value, ConcurrentUtils.createIfAbsent(map, key, init), "Wrong result")
//    assertEquals(value, map.get(key), "Wrong value in map")
//    EasyMock.verify(init)
//  }
//
//  /**
//    * Tests createIfAbsent() if a null map is passed in.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testCreateIfAbsentNullMap() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    EasyMock.replay(init)
//    assertNull(ConcurrentUtils.createIfAbsent(null, "test", init), "Wrong result")
//    EasyMock.verify(init)
//  }
//
//  /**
//    * Tests createIfAbsent() if a null initializer is passed in.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testCreateIfAbsentNullInit() = {
//    val map = new ConcurrentHashMap[String, Integer]
//    val key = "testKey"
//    val value = 42
//    map.put(key, value)
//    assertNull(ConcurrentUtils.createIfAbsent(map, key, null), "Wrong result")
//    assertEquals(value, map.get(key), "Map was changed")
//  }
//
//  /**
//    * Tests createIfAbsentUnchecked() if no exception is thrown.
//    */
//  @Test def testCreateIfAbsentUncheckedSuccess() = {
//    val key = "testKey"
//    val value = 42
//    val map = new ConcurrentHashMap[String, Integer]
//    assertEquals(value, ConcurrentUtils.createIfAbsentUnchecked(map, key, new Nothing(value)), "Wrong result")
//    assertEquals(value, map.get(key), "Wrong value in map")
//  }
//
//  /**
//    * Tests createIfAbsentUnchecked() if an exception is thrown.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testCreateIfAbsentUncheckedException() = {
//    @SuppressWarnings(Array("unchecked")) val init = EasyMock.createMock(classOf[Nothing])
//    val ex = new Exception
//    EasyMock.expect(init.get).andThrow(new Nothing(ex))
//    EasyMock.replay(init)
//    val crex = assertThrows(classOf[Nothing], () => ConcurrentUtils.createIfAbsentUnchecked(new ConcurrentHashMap[K, V], "test", init))
//    assertEquals(ex, crex.getCause, "Wrong cause")
//    EasyMock.verify(init)
//  }
//}
