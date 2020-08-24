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
//import org.junit.Assert.assertNotNull
//import java.util.concurrent.CountDownLatch
//import org.junit.Test
//
///**
//  * <p>
//  * An abstract base class for tests of concrete {@code ConcurrentInitializer}
//  * implementations.
//  * </p>
//  * <p>
//  * This class provides some basic tests for initializer implementations. Derived
//  * class have to create a {@link ConcurrentInitializer} object on which the
//  * tests are executed.
//  * </p>
//  */
//abstract class AbstractConcurrentInitializerTest {
//  /**
//    * Tests a simple invocation of the get() method.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException because the object under test may throw it
//    */
//    @Test
//    @throws[ConcurrentException]
//    def testGet() = assertNotNull(createInitializer.get, "No managed object")
//
//  /**
//    * Tests whether sequential get() invocations always return the same
//    * instance.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException because the object under test may throw it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testGetMultipleTimes() = {
//    val initializer = createInitializer
//    val obj = initializer.get
//    for (i <- 0 until 10) {
//      assertEquals(obj, initializer.get, "Got different object at " + i)
//    }
//  }
//
//  /**
//    * Tests whether get() can be invoked from multiple threads concurrently.
//    * Always the same object should be returned.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException because the object under test may throw it
//    * @throws java.lang.InterruptedException                          because the threading API my throw it
//    */
//  @Test
//  @throws[ConcurrentException]
//  @throws[InterruptedException]
//  def testGetConcurrent() = {
//    val initializer = createInitializer
//    val threadCount = 20
//    val startLatch = new CountDownLatch(1)
//    class GetThread extends Thread {
//      private[concurrent] var `object` = null
//
//      override def run() = try { // wait until all threads are ready for maximum parallelism
//        startLatch.await()
//        // access the initializer
//        `object` = initializer.get
//      } catch {
//        case iex: InterruptedException =>
//
//        // ignore
//        case cex: Nothing =>
//          `object` = cex
//      }
//    }
//    val threads = new Array[GetThread](threadCount)
//    for (i <- 0 until threadCount) {
//      threads(i) = new GetThread
//      threads(i).start()
//    }
//    // fire all threads and wait until they are ready
//    startLatch.countDown()
//    for (t <- threads) {
//      t.join()
//    }
//    // check results
//    val managedObject = initializer.get
//    for (t <- threads) {
//      assertEquals(managedObject, t.`object`, "Wrong object")
//    }
//  }
//
//  /**
//    * Creates the {@link ConcurrentInitializer} object to be tested. This
//    * method is called whenever the test fixture needs to be obtained.
//    *
//    * @return the initializer object to be tested
//    */
//  protected def createInitializer
//}
