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

//package org.apache.commons.lang3.concurrent.locks
//
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNotSame
//import org.junit.Assert.assertTrue
//import java.util.function.LongConsumer
//import org.apache.commons.lang3.concurrent.locks.LockingVisitors.LockVisitor
//import org.apache.commons.lang3.concurrent.locks.LockingVisitors.StampedLockVisitor
//import org.apache.commons.lang3.function.FailableConsumer
//import org.junit.Test
//
//object LockingVisitorsTest {
//  private val DELAY_MILLIS = 1500
//  private val NUMBER_OF_THREADS = 10
//  private val TOTAL_DELAY_MILLIS = NUMBER_OF_THREADS * DELAY_MILLIS
//}
//
//class LockingVisitorsTest {
//  protected def containsTrue(booleanArray: Array[Boolean]): Boolean = {
//    booleanArray synchronized
//    for (element <- booleanArray) {
//      if (element) return true
//    }
//    false
//  }
//
//  @throws[InterruptedException]
//  private def runTest(delayMillis: Long, exclusiveLock: Boolean, runTimeCheck: LongConsumer, booleanValues: Array[Boolean], visitor: Nothing) = {
//    val runningValues = new Array[Boolean](10)
//    val startTime = System.currentTimeMillis
//    for (i <- 0 until booleanValues.length) {
//      val index = i
//      val consumer = (b) => {
//        def foo(b) = {
//          b(index) = false
//          Thread.sleep(delayMillis)
//          b(index) = true
//          set(runningValues, index, false)
//        }
//
//        foo(b)
//      }
//      val t = new Thread(() => {
//        def foo() =
//          if (exclusiveLock) visitor.acceptWriteLocked(consumer)
//          else visitor.acceptReadLocked(consumer)
//
//        foo()
//      })
//      set(runningValues, i, true)
//      t.start()
//    }
//    while ( {
//      containsTrue(runningValues)
//    }) Thread.sleep(100)
//    val endTime = System.currentTimeMillis
//    for (booleanValue <- booleanValues) {
//      assertTrue(booleanValue)
//    }
//    // WRONG assumption
//    // runTimeCheck.accept(endTime - startTime);
//  }
//
//  protected def set(booleanArray: Array[Boolean], offset: Int, value: Boolean) = booleanArray synchronized booleanArray(offset) = value
//
//  @Test
//  @throws[Exception]
//  def testReentrantReadWriteLockExclusive() = {
//    /*
//            * If our threads are running concurrently, then we expect to be no faster than running one after the other.
//            */ val booleanValues = new Array[Boolean](10)
//    runTest(LockingVisitorsTest.DELAY_MILLIS, true, (l: Long) => assertTrue(l >= LockingVisitorsTest.TOTAL_DELAY_MILLIS), booleanValues, LockingVisitors.reentrantReadWriteLockVisitor(booleanValues))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReentrantReadWriteLockNotExclusive() = {
//    /*
//            * If our threads are running concurrently, then we expect to be faster than running one after the other.
//            */ val booleanValues = new Array[Boolean](10)
//    runTest(LockingVisitorsTest.DELAY_MILLIS, false, (l: Long) => assertTrue(l < LockingVisitorsTest.TOTAL_DELAY_MILLIS), booleanValues, LockingVisitors.reentrantReadWriteLockVisitor(booleanValues))
//  }
//
//  @Test def testResultValidation() = {
//    val hidden = new Any
//    val lock = LockingVisitors.stampedLockVisitor(hidden)
//    val o1 = lock.applyReadLocked((h) => {
//      def foo(h) =
//        new Any
//
//      foo(h)
//    })
//    assertNotNull(o1)
//    assertNotSame(hidden, o1)
//    val o2 = lock.applyWriteLocked((h) => {
//      def foo(h) =
//        new Any
//
//      foo(h)
//    })
//    assertNotNull(o2)
//    assertNotSame(hidden, o2)
//  }
//
//  @Test
//  @throws[Exception]
//  def testStampedLockExclusive() = {
//    val booleanValues = new Array[Boolean](10)
//    runTest(LockingVisitorsTest.DELAY_MILLIS, true, (l: Long) => assertTrue(l >= LockingVisitorsTest.TOTAL_DELAY_MILLIS), booleanValues, LockingVisitors.stampedLockVisitor(booleanValues))
//  }
//
//  @Test
//  @throws[Exception]
//  def testStampedLockNotExclusive() = {
//    val booleanValues = new Array[Boolean](10)
//    runTest(LockingVisitorsTest.DELAY_MILLIS, false, (l: Long) => assertTrue(l < LockingVisitorsTest.TOTAL_DELAY_MILLIS), booleanValues, LockingVisitors.stampedLockVisitor(booleanValues))
//  }
//}
