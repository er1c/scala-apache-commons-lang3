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
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util
//import java.util.NoSuchElementException
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.TimeUnit
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
///**
//  * Test class for {@link MultiBackgroundInitializer}.
//  */
//object MultiBackgroundInitializerTest {
//  /** Constant for the names of the child initializers. */
//    private val CHILD_INIT = "childInitializer"
//
//  /**
//    * A concrete implementation of {@code BackgroundInitializer} used for
//    * defining background tasks for {@code MultiBackgroundInitializer}.
//    */
//  private class ChildBackgroundInitializer extends Nothing {
//    /** Stores the current executor service. */
//      private[concurrent] var currentExecutor = null
//    /** A counter for the invocations of initialize(). */
//    private[concurrent] var initializeCalls = 0
//    /** An exception to be thrown by initialize(). */
//    private[concurrent] val ex = null
//
//    /**
//      * Records this invocation. Optionally throws an exception.
//      */
//    @throws[Exception]
//    protected def initialize = {
//      currentExecutor = getActiveExecutor
//      initializeCalls += 1
//      if (ex != null) throw ex
//      Integer.valueOf(initializeCalls)
//    }
//  }
//
//}
//
//class MultiBackgroundInitializerTest {
//  /** The initializer to be tested. */
//    private var initializer = null
//
//  @BeforeEach def setUp() = initializer = new Nothing
//
//  /**
//    * Tests whether a child initializer has been executed. Optionally the
//    * expected executor service can be checked, too.
//    *
//    * @param child   the child initializer
//    * @param expExec the expected executor service (null if the executor should
//    *                not be checked)
//    * @throws ConcurrentException if an error occurs
//    */
//  @throws[ConcurrentException]
//  private def checkChild(child: Nothing, expExec: ExecutorService) = {
//    val cinit = child.asInstanceOf[MultiBackgroundInitializerTest.ChildBackgroundInitializer]
//    val result = cinit.get
//    assertEquals(1, result.intValue, "Wrong result")
//    assertEquals(1, cinit.initializeCalls, "Wrong number of executions")
//    if (expExec != null) assertEquals(expExec, cinit.currentExecutor, "Wrong executor service")
//  }
//
//  /**
//    * Tests addInitializer() if a null name is passed in. This should cause an
//    * exception.
//    */
//  @Test def testAddInitializerNullName() = assertThrows(classOf[NullPointerException], () => initializer.addInitializer(null, new MultiBackgroundInitializerTest.ChildBackgroundInitializer))
//
//  /**
//    * Tests addInitializer() if a null initializer is passed in. This should
//    * cause an exception.
//    */
//  @Test def testAddInitializerNullInit() = assertThrows(classOf[NullPointerException], () => initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, null))
//
//  /**
//    * Tests the background processing if there are no child initializers.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeNoChildren() = {
//    assertTrue(initializer.start, "Wrong result of start()")
//    val res = initializer.get
//    assertTrue(res.initializerNames.isEmpty, "Got child initializers")
//    assertTrue(initializer.getActiveExecutor.isShutdown, "Executor not shutdown")
//  }
//
//  /**
//    * Helper method for testing the initialize() method. This method can
//    * operate with both an external and a temporary executor service.
//    *
//    * @return the result object produced by the initializer
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @throws[ConcurrentException]
//  private def checkInitialize = {
//    val count = 5
//    for (i <- 0 until count) {
//      initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT + i, new MultiBackgroundInitializerTest.ChildBackgroundInitializer)
//    }
//    initializer.start
//    val res = initializer.get
//    assertEquals(count, res.initializerNames.size, "Wrong number of child initializers")
//    for (i <- 0 until count) {
//      val key = MultiBackgroundInitializerTest.CHILD_INIT + i
//      assertTrue(res.initializerNames.contains(key), "Name not found: " + key)
//      assertEquals(Integer.valueOf(1), res.getResultObject(key), "Wrong result object")
//      assertFalse(res.isException(key), "Exception flag")
//      assertNull(res.getException(key), "Got an exception")
//      checkChild(res.getInitializer(key), initializer.getActiveExecutor)
//    }
//    res
//  }
//
//  /**
//    * Tests background processing if a temporary executor is used.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeTempExec() = {
//    checkInitialize
//    assertTrue(initializer.getActiveExecutor.isShutdown, "Executor not shutdown")
//  }
//
//  /**
//    * Tests background processing if an external executor service is provided.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  @throws[InterruptedException]
//  def testInitializeExternalExec() = {
//    val exec = Executors.newCachedThreadPool
//    try {
//      initializer = new Nothing(exec)
//      checkInitialize
//      assertEquals(exec, initializer.getActiveExecutor, "Wrong executor")
//      assertFalse(exec.isShutdown, "Executor was shutdown")
//    } finally {
//      exec.shutdown()
//      exec.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//
//  /**
//    * Tests the behavior of initialize() if a child initializer has a specific
//    * executor service. Then this service should not be overridden.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  @throws[InterruptedException]
//  def testInitializeChildWithExecutor() = {
//    val initExec = "childInitializerWithExecutor"
//    val exec = Executors.newSingleThreadExecutor
//    try {
//      val c1 = new MultiBackgroundInitializerTest.ChildBackgroundInitializer
//      val c2 = new MultiBackgroundInitializerTest.ChildBackgroundInitializer
//      c2.setExternalExecutor(exec)
//      initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, c1)
//      initializer.addInitializer(initExec, c2)
//      initializer.start
//      initializer.get
//      checkChild(c1, initializer.getActiveExecutor)
//      checkChild(c2, exec)
//    } finally {
//      exec.shutdown()
//      exec.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//
//  /**
//    * Tries to add another child initializer after the start() method has been
//    * called. This should not be allowed.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testAddInitializerAfterStart() = {
//    initializer.start
//    assertThrows(classOf[IllegalStateException], () => initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, new MultiBackgroundInitializerTest.ChildBackgroundInitializer), "Could add initializer after start()!")
//    initializer.get
//  }
//
//  /**
//    * Tries to query an unknown child initializer from the results object. This
//    * should cause an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testResultGetInitializerUnknown() = {
//    val res = checkInitialize
//    assertThrows(classOf[NoSuchElementException], () => res.getInitializer("unknown"))
//  }
//
//  /**
//    * Tries to query the results of an unknown child initializer from the
//    * results object. This should cause an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testResultGetResultObjectUnknown() = {
//    val res = checkInitialize
//    assertThrows(classOf[NoSuchElementException], () => res.getResultObject("unknown"))
//  }
//
//  /**
//    * Tries to query the exception of an unknown child initializer from the
//    * results object. This should cause an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testResultGetExceptionUnknown() = {
//    val res = checkInitialize
//    assertThrows(classOf[NoSuchElementException], () => res.getException("unknown"))
//  }
//
//  /**
//    * Tries to query the exception flag of an unknown child initializer from
//    * the results object. This should cause an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testResultIsExceptionUnknown() = {
//    val res = checkInitialize
//    assertThrows(classOf[NoSuchElementException], () => res.isException("unknown"))
//  }
//
//  /**
//    * Tests that the set with the names of the initializers cannot be modified.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testResultInitializerNamesModify() = {
//    checkInitialize
//    val res = initializer.get
//    val it = res.initializerNames.iterator
//    it.next
//    assertThrows(classOf[UnsupportedOperationException], it.remove)
//  }
//
//  /**
//    * Tests the behavior of the initializer if one of the child initializers
//    * throws a runtime exception.
//    */
//  @Test def testInitializeRuntimeEx() = {
//    val child = new MultiBackgroundInitializerTest.ChildBackgroundInitializer
//    child.ex = new RuntimeException
//    initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, child)
//    initializer.start
//    val ex = assertThrows(classOf[Exception], initializer.get)
//    assertEquals(child.ex, ex, "Wrong exception")
//  }
//
//  /**
//    * Tests the behavior of the initializer if one of the child initializers
//    * throws a checked exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeEx() = {
//    val child = new MultiBackgroundInitializerTest.ChildBackgroundInitializer
//    child.ex = new Exception
//    initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, child)
//    initializer.start
//    val res = initializer.get
//    assertTrue(res.isException(MultiBackgroundInitializerTest.CHILD_INIT), "No exception flag")
//    assertNull(res.getResultObject(MultiBackgroundInitializerTest.CHILD_INIT), "Got a results object")
//    val cex = res.getException(MultiBackgroundInitializerTest.CHILD_INIT)
//    assertEquals(child.ex, cex.getCause, "Wrong cause")
//  }
//
//  /**
//    * Tests the isSuccessful() method of the result object if no child
//    * initializer has thrown an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeResultsIsSuccessfulTrue() = {
//    val child = new MultiBackgroundInitializerTest.ChildBackgroundInitializer
//    initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, child)
//    initializer.start
//    val res = initializer.get
//    assertTrue(res.isSuccessful, "Wrong success flag")
//  }
//
//  /**
//    * Tests the isSuccessful() method of the result object if at least one
//    * child initializer has thrown an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeResultsIsSuccessfulFalse() = {
//    val child = new MultiBackgroundInitializerTest.ChildBackgroundInitializer
//    child.ex = new Exception
//    initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, child)
//    initializer.start
//    val res = initializer.get
//    assertFalse(res.isSuccessful, "Wrong success flag")
//  }
//
//  /**
//    * Tests whether MultiBackgroundInitializers can be combined in a nested
//    * way.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeNested() = {
//    val nameMulti = "multiChildInitializer"
//    initializer.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT, new MultiBackgroundInitializerTest.ChildBackgroundInitializer)
//    val mi2 = new Nothing
//    val count = 3
//    for (i <- 0 until count) {
//      mi2.addInitializer(MultiBackgroundInitializerTest.CHILD_INIT + i, new MultiBackgroundInitializerTest.ChildBackgroundInitializer)
//    }
//    initializer.addInitializer(nameMulti, mi2)
//    initializer.start
//    val res = initializer.get
//    val exec = initializer.getActiveExecutor
//    checkChild(res.getInitializer(MultiBackgroundInitializerTest.CHILD_INIT), exec)
//    val res2 = res.getResultObject(nameMulti).asInstanceOf[Nothing]
//    assertEquals(count, res2.initializerNames.size, "Wrong number of initializers")
//    for (i <- 0 until count) {
//      checkChild(res2.getInitializer(MultiBackgroundInitializerTest.CHILD_INIT + i), exec)
//    }
//    assertTrue(exec.isShutdown, "Executor not shutdown")
//  }
//}
