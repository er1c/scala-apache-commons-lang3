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
//import java.util.concurrent.CountDownLatch
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.TimeUnit
//import java.util.concurrent.atomic.AtomicReference
//import org.junit.Test
//
//object BackgroundInitializerTest {
//
//  /**
//    * A concrete implementation of BackgroundInitializer. It also overloads
//    * some methods that simplify testing.
//    */
//  private class BackgroundInitializerTestImpl private[concurrent]() extends Nothing {
//    /** An exception to be thrown by initialize(). */
//    private[concurrent] val ex = null
//    /** A flag whether the background task should sleep a while. */
//    private[concurrent] val shouldSleep = false
//    /** The number of invocations of initialize(). */
//    private[concurrent] var initializeCalls = 0
//
//    def this(exec: ExecutorService) {
//      this()
//      super (exec)
//    }
//
//    /**
//      * Records this invocation. Optionally throws an exception or sleeps a
//      * while.
//      *
//      * @throws Exception in case of an error
//      */
//    @throws[Exception]
//    protected def initialize = {
//      if (ex != null) throw ex
//      if (shouldSleep) Thread.sleep(60000L)
//      Integer.valueOf({
//        initializeCalls += 1; initializeCalls
//      })
//    }
//  }
//
//}
//
//class BackgroundInitializerTest {
//  /**
//    * Helper method for checking whether the initialize() method was correctly
//    * called. start() must already have been invoked.
//    *
//    * @param init the initializer to test
//    */
//    @throws[ConcurrentException]
//    private def checkInitialize(init: BackgroundInitializerTest.BackgroundInitializerTestImpl) = {
//      val result = init.get
//      assertEquals(1, result.intValue, "Wrong result")
//      assertEquals(1, init.initializeCalls, "Wrong number of invocations")
//      assertNotNull(init.getFuture, "No future")
//    }
//
//  /**
//    * Tests whether initialize() is invoked.
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitialize() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    init.start
//    checkInitialize(init)
//  }
//
//  /**
//    * Tries to obtain the executor before start(). It should not have been
//    * initialized yet.
//    */
//  @Test def testGetActiveExecutorBeforeStart() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    assertNull(init.getActiveExecutor, "Got an executor")
//  }
//
//  /**
//    * Tests whether an external executor is correctly detected.
//    */
//  @Test
//  @throws[InterruptedException]
//  @throws[ConcurrentException]
//  def testGetActiveExecutorExternal() = {
//    val exec = Executors.newSingleThreadExecutor
//    try {
//      val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl(exec)
//      init.start
//      assertSame(exec, init.getActiveExecutor, "Wrong executor")
//      checkInitialize(init)
//    } finally {
//      exec.shutdown()
//      exec.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//
//  /**
//    * Tests getActiveExecutor() for a temporary executor.
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testGetActiveExecutorTemp() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    init.start
//    assertNotNull(init.getActiveExecutor, "No active executor")
//    checkInitialize(init)
//  }
//
//  /**
//    * Tests the execution of the background task if a temporary executor has to
//    * be created.
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testInitializeTempExecutor() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    assertTrue(init.start, "Wrong result of start()")
//    checkInitialize(init)
//    assertTrue(init.getActiveExecutor.isShutdown, "Executor not shutdown")
//  }
//
//  /**
//    * Tests whether an external executor can be set using the
//    * setExternalExecutor() method.
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testSetExternalExecutor() = {
//    val exec = Executors.newCachedThreadPool
//    try {
//      val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//      init.setExternalExecutor(exec)
//      assertEquals(exec, init.getExternalExecutor, "Wrong executor service")
//      assertTrue(init.start, "Wrong result of start()")
//      assertSame(exec, init.getActiveExecutor, "Wrong active executor")
//      checkInitialize(init)
//      assertFalse(exec.isShutdown, "Executor was shutdown")
//    } finally exec.shutdown()
//  }
//
//  /**
//    * Tests that setting an executor after start() causes an exception.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException because the test implementation may throw it
//    */
//  @Test
//  @throws[ConcurrentException]
//  @throws[InterruptedException]
//  def testSetExternalExecutorAfterStart() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    init.start
//    val exec = Executors.newSingleThreadExecutor
//    try {
//      assertThrows(classOf[IllegalStateException], () => init.setExternalExecutor(exec))
//      init.get
//    } finally {
//      exec.shutdown()
//      exec.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//
//  /**
//    * Tests invoking start() multiple times. Only the first invocation should
//    * have an effect.
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testStartMultipleTimes() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    assertTrue(init.start, "Wrong result for start()")
//    for (i <- 0 until 10) {
//      assertFalse(init.start, "Could start again")
//    }
//    checkInitialize(init)
//  }
//
//  /**
//    * Tests calling get() before start(). This should cause an exception.
//    */
//  @Test def testGetBeforeStart() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    assertThrows(classOf[IllegalStateException], init.get)
//  }
//
//  /**
//    * Tests the get() method if background processing causes a runtime
//    * exception.
//    */
//  @Test def testGetRuntimeException() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    val rex = new RuntimeException
//    init.ex = rex
//    init.start
//    val ex = assertThrows(classOf[Exception], init.get)
//    assertEquals(rex, ex, "Runtime exception not thrown")
//  }
//
//  /**
//    * Tests the get() method if background processing causes a checked
//    * exception.
//    */
//  @Test def testGetCheckedException() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    val ex = new Exception
//    init.ex = ex
//    init.start
//    val cex = assertThrows(classOf[Nothing], init.get)
//    assertEquals(ex, cex.getCause, "Exception not thrown")
//  }
//
//  /**
//    * Tests the get() method if waiting for the initialization is interrupted.
//    *
//    * @throws java.lang.InterruptedException because we're making use of Java's concurrent API
//    */
//  @Test
//  @throws[InterruptedException]
//  def testGetInterruptedException() = {
//    val exec = Executors.newSingleThreadExecutor
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl(exec)
//    val latch1 = new CountDownLatch(1)
//    init.shouldSleep = true
//    init.start
//    val iex = new AtomicReference[InterruptedException]
//    val getThread = new Thread() {
//      override def run() = try init.get
//      catch {
//        case cex: Nothing =>
//          if (cex.getCause.isInstanceOf[InterruptedException]) iex.set(cex.getCause.asInstanceOf[InterruptedException])
//      } finally {
//        assertTrue(isInterrupted, "Thread not interrupted")
//        latch1.countDown()
//      }
//    }
//    getThread.start()
//    getThread.interrupt()
//    latch1.await()
//    exec.shutdownNow
//    exec.awaitTermination(1, TimeUnit.SECONDS)
//    assertNotNull(iex.get, "No interrupted exception")
//  }
//
//  /**
//    * Tests isStarted() before start() was called.
//    */
//  @Test def testIsStartedFalse() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    assertFalse(init.isStarted, "Already started")
//  }
//
//  /**
//    * Tests isStarted() after start().
//    */
//  @Test def testIsStartedTrue() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    init.start
//    assertTrue(init.isStarted, "Not started")
//  }
//
//  /**
//    * Tests isStarted() after the background task has finished.
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testIsStartedAfterGet() = {
//    val init = new BackgroundInitializerTest.BackgroundInitializerTestImpl
//    init.start
//    checkInitialize(init)
//    assertTrue(init.isStarted, "Not started")
//  }
//}
