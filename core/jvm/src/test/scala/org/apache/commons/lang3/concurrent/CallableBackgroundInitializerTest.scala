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
//import org.junit.Assert.assertThrows
//import java.util.concurrent.Callable
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.TimeUnit
//import org.junit.Test
//
///**
//  * Test class for {@code CallableBackgroundInitializer}
//  */
//object CallableBackgroundInitializerTest {
//  /** Constant for the result of the call() invocation. */
//    private val RESULT = Integer.valueOf(42)
//
//  /**
//    * A test Callable implementation for checking the initializer's
//    * implementation of the initialize() method.
//    */
//  private class TestCallable extends Callable[Integer] {
//    /** A counter for the number of call() invocations. */
//      private[concurrent] var callCount = 0
//
//    /**
//      * Records this invocation and returns the test result.
//      */
//    override def call = {
//      callCount += 1
//      RESULT
//    }
//  }
//
//}
//
//class CallableBackgroundInitializerTest {
//  /**
//    * Tries to create an instance without a Callable. This should cause an
//    * exception.
//    */
//    @Test def testInitNullCallable() = assertThrows(classOf[NullPointerException], () => new Nothing(null))
//
//  /**
//    * Tests whether the executor service is correctly passed to the super
//    * class.
//    */
//  @Test
//  @throws[InterruptedException]
//  def testInitExecutor() = {
//    val exec = Executors.newSingleThreadExecutor
//    val init = new Nothing(new CallableBackgroundInitializerTest.TestCallable, exec)
//    assertEquals(exec, init.getExternalExecutor, "Executor not set")
//    exec.shutdown()
//    exec.awaitTermination(1, TimeUnit.SECONDS)
//  }
//
//  /**
//    * Tries to pass a null Callable to the constructor that takes an executor.
//    * This should cause an exception.
//    */
//  @Test
//  @throws[InterruptedException]
//  def testInitExecutorNullCallable() = {
//    val exec = Executors.newSingleThreadExecutor
//    try assertThrows(classOf[NullPointerException], () => new Nothing(null, exec))
//    finally {
//      exec.shutdown()
//      exec.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//
//  /**
//    * Tests the implementation of initialize().
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testInitialize() = {
//    val call = new CallableBackgroundInitializerTest.TestCallable
//    val init = new Nothing(call)
//    assertEquals(CallableBackgroundInitializerTest.RESULT, init.initialize, "Wrong result")
//    assertEquals(1, call.callCount, "Wrong number of invocations")
//  }
//}
