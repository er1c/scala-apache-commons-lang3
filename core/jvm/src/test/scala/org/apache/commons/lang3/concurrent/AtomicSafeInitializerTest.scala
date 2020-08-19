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
//import java.util.concurrent.atomic.AtomicInteger
//import org.junit.{Before, Test}
//
///**
//  * Test class for {@code AtomicSafeInitializer}.
//  */
//object AtomicSafeInitializerTest {
//
//  /**
//    * A concrete test implementation of {@code AtomicSafeInitializer}. This
//    * implementation also counts the number of invocations of the initialize()
//    * method.
//    */
//  private class AtomicSafeInitializerTestImpl extends Nothing {
//    /** A counter for initialize() invocations. */
//      final private[concurrent] val initCounter = new AtomicInteger
//
//    protected def initialize = {
//      initCounter.incrementAndGet
//      new Any
//    }
//  }
//
//}
//
//class AtomicSafeInitializerTest extends AbstractConcurrentInitializerTest {
//  /** The instance to be tested. */
//    private var initializer = null
//
//  @Before def setUp() = initializer = new AtomicSafeInitializerTest.AtomicSafeInitializerTestImpl
//
//  /**
//    * Returns the initializer to be tested.
//    *
//    * @return the {@code AtomicSafeInitializer} under test
//    */
//  override protected def createInitializer = initializer
//
//  /**
//    * Tests that initialize() is called only once.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException because {@link #testGetConcurrent ( )} may throw it
//    * @throws java.lang.InterruptedException                          because {@link #testGetConcurrent ( )} may throw it
//    */
//  @Test
//  @throws[ConcurrentException]
//  @throws[InterruptedException]
//  def testNumberOfInitializeInvocations() = {
//    testGetConcurrent()
//    assertEquals(1, initializer.initCounter.get, "Wrong number of invocations")
//  }
//}
