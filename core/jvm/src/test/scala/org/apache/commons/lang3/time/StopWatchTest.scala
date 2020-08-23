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

//package org.apache.commons.lang3.time
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotEquals
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util.concurrent.TimeUnit
//import org.apache.commons.lang3.reflect.FieldUtils
//import org.junit.Test
//
//
///**
//  * TestCase for StopWatch.
//  */
//object StopWatchTest {
//  private val MESSAGE = "Baking cookies"
//  private val MIN_SLEEP_MILLISECONDS = 20
//  private val ZERO_HOURS_PREFIX = "00:"
//  private val ZERO_TIME_ELAPSED = "00:00:00.000"
//}
//
//class StopWatchTest {
//  /**
//    * <p>
//    * Creates a suspended StopWatch object which appears to have elapsed
//    * for the requested amount of time in nanoseconds.
//    * <p>
//    * <p>
//    * <pre>
//    * // Create a mock StopWatch with a time of 2:59:01.999
//    * final long nanos = TimeUnit.HOURS.toNanos(2)
//    * + TimeUnit.MINUTES.toNanos(59)
//    * + TimeUnit.SECONDS.toNanos(1)
//    * + TimeUnit.MILLISECONDS.toNanos(999);
//    * final StopWatch watch = createMockStopWatch(nanos);
//    * </pre>
//    *
//    * @param nanos Time in nanoseconds to have elapsed on the stop watch
//    * @return StopWatch in a suspended state with the elapsed time
//    */
//    private def createMockStopWatch(nanos: Long): Nothing = {
//      val watch = StopWatch.createStarted
//      watch.suspend
//      try {
//        val currentNanos = System.nanoTime
//        FieldUtils.writeField(watch, "startTime", currentNanos - nanos, true)
//        FieldUtils.writeField(watch, "stopTime", currentNanos, true)
//      } catch {
//        case e: IllegalAccessException =>
//          return null
//      }
//      watch
//    }
//
//  // test bad states
//  @Test def testBadStates() = {
//    val watch = new Nothing
//    assertThrows(classOf[IllegalStateException], watch.stop, "Calling stop on an unstarted StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.suspend, "Calling suspend on an unstarted StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.split, "Calling split on a non-running StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.unsplit, "Calling unsplit on an unsplit StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.resume, "Calling resume on an unsuspended StopWatch should throw an exception. ")
//    watch.start
//    assertThrows(classOf[IllegalStateException], watch.start, "Calling start on a started StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.unsplit, "Calling unsplit on an unsplit StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.getSplitTime, "Calling getSplitTime on an unsplit StopWatch should throw an exception. ")
//    assertThrows(classOf[IllegalStateException], watch.resume, "Calling resume on an unsuspended StopWatch should throw an exception. ")
//    watch.stop
//    assertThrows(classOf[IllegalStateException], watch.start, "Calling start on a stopped StopWatch should throw an exception as it needs to be reset. ")
//  }
//
//  @Test def testBooleanStates() = {
//    val watch = new Nothing
//    assertFalse(watch.isStarted)
//    assertFalse(watch.isSuspended)
//    assertTrue(watch.isStopped)
//    watch.start
//    assertTrue(watch.isStarted)
//    assertFalse(watch.isSuspended)
//    assertFalse(watch.isStopped)
//    watch.suspend
//    assertTrue(watch.isStarted)
//    assertTrue(watch.isSuspended)
//    assertFalse(watch.isStopped)
//    watch.stop
//    assertFalse(watch.isStarted)
//    assertFalse(watch.isSuspended)
//    assertTrue(watch.isStopped)
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testFormatSplitTime() = {
//    val watch = StopWatch.createStarted
//    Thread.sleep(StopWatchTest.MIN_SLEEP_MILLISECONDS)
//    watch.split
//    val formatSplitTime = watch.formatSplitTime
//    assertNotEquals(StopWatchTest.ZERO_TIME_ELAPSED, formatSplitTime)
//    assertTrue(formatSplitTime.startsWith(StopWatchTest.ZERO_HOURS_PREFIX))
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testFormatSplitTimeWithMessage() = {
//    val watch = new Nothing(StopWatchTest.MESSAGE)
//    watch.start
//    Thread.sleep(StopWatchTest.MIN_SLEEP_MILLISECONDS)
//    watch.split
//    val formatSplitTime = watch.formatSplitTime
//    assertFalse(formatSplitTime.startsWith(StopWatchTest.MESSAGE), formatSplitTime)
//    assertTrue(formatSplitTime.startsWith(StopWatchTest.ZERO_HOURS_PREFIX))
//  }
//
//  @Test def testFormatTime() = {
//    val watch = StopWatch.create
//    val formatTime = watch.formatTime
//    assertEquals(StopWatchTest.ZERO_TIME_ELAPSED, formatTime)
//    assertTrue(formatTime.startsWith(StopWatchTest.ZERO_HOURS_PREFIX))
//  }
//
//  @Test def testFormatTimeWithMessage() = {
//    val watch = new Nothing(StopWatchTest.MESSAGE)
//    val formatTime = watch.formatTime
//    assertFalse(formatTime.startsWith(StopWatchTest.MESSAGE), formatTime)
//  }
//
//  @Test def testGetStartTime() = {
//    val beforeStopWatch = System.currentTimeMillis
//    val watch = new Nothing
//    assertThrows(classOf[IllegalStateException], watch.getStartTime, "Calling getStartTime on an unstarted StopWatch should throw an exception")
//    watch.start
//    watch.getStartTime
//    assertTrue(watch.getStartTime >= beforeStopWatch)
//    watch.reset
//    assertThrows(classOf[IllegalStateException], watch.getStartTime, "Calling getStartTime on a reset, but unstarted StopWatch should throw an exception")
//  }
//
//  @Test def testLang315() = {
//    val watch = StopWatch.createStarted
//    try Thread.sleep(200)
//    catch {
//      case ex: InterruptedException =>
//
//      // ignore
//    }
//    watch.suspend
//    val suspendTime = watch.getTime
//    try Thread.sleep(200)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.stop
//    val totalTime = watch.getTime
//    assertEquals(suspendTime, totalTime)
//  }
//
//  @Test def testMessage() = {
//    assertNull(StopWatch.create.getMessage)
//    val stopWatch = new Nothing(StopWatchTest.MESSAGE)
//    assertEquals(StopWatchTest.MESSAGE, stopWatch.getMessage)
//    assertTrue(stopWatch.toString.startsWith(StopWatchTest.MESSAGE))
//    stopWatch.start
//    stopWatch.split
//    assertTrue(stopWatch.toSplitString.startsWith(StopWatchTest.MESSAGE))
//  }
//
//  @Test def testStopWatchGetWithTimeUnit() = { // Create a mock StopWatch with a time of 2:59:01.999
//    val watch = createMockStopWatch(TimeUnit.HOURS.toNanos(2) + TimeUnit.MINUTES.toNanos(59) + TimeUnit.SECONDS.toNanos(1) + TimeUnit.MILLISECONDS.toNanos(999))
//    assertEquals(2L, watch.getTime(TimeUnit.HOURS))
//    assertEquals(179L, watch.getTime(TimeUnit.MINUTES))
//    assertEquals(10741L, watch.getTime(TimeUnit.SECONDS))
//    assertEquals(10741999L, watch.getTime(TimeUnit.MILLISECONDS))
//  }
//
//  @Test def testStopWatchSimple() = {
//    val watch = StopWatch.createStarted
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.stop
//    val time = watch.getTime
//    assertEquals(time, watch.getTime)
//    assertTrue(time >= 500)
//    assertTrue(time < 700)
//    watch.reset
//    assertEquals(0, watch.getTime)
//  }
//
//  @Test def testStopWatchSimpleGet() = {
//    val watch = new Nothing
//    assertEquals(0, watch.getTime)
//    assertEquals(StopWatchTest.ZERO_TIME_ELAPSED, watch.toString)
//    watch.start
//    try Thread.sleep(500)
//    catch {
//      case ex: InterruptedException =>
//    }
//    assertTrue(watch.getTime < 2000)
//  }
//
//  @Test def testStopWatchSplit() = {
//    val watch = StopWatch.createStarted
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.split
//    val splitTime = watch.getSplitTime
//    val splitStr = watch.toSplitString
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.unsplit
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.stop
//    val totalTime = watch.getTime
//    assertEquals(splitStr.length, 12, "Formatted split string not the correct length")
//    assertTrue(splitTime >= 500)
//    assertTrue(splitTime < 700)
//    assertTrue(totalTime >= 1500)
//    assertTrue(totalTime < 1900)
//  }
//
//  @Test def testStopWatchStatic() = {
//    val watch = StopWatch.createStarted
//    assertTrue(watch.isStarted)
//  }
//
//  @Test def testStopWatchSuspend() = {
//    val watch = StopWatch.createStarted
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.suspend
//    val suspendTime = watch.getTime
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.resume
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.stop
//    val totalTime = watch.getTime
//    assertTrue(suspendTime >= 500)
//    assertTrue(suspendTime < 700)
//    assertTrue(totalTime >= 1000)
//    assertTrue(totalTime < 1300)
//  }
//
//  @Test def testToSplitString() = {
//    val watch = StopWatch.createStarted
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.split
//    val splitStr = watch.toSplitString
//    assertEquals(splitStr.length, 12, "Formatted split string not the correct length")
//  }
//
//  @Test def testToSplitStringWithMessage() = {
//    val watch = new Nothing(StopWatchTest.MESSAGE)
//    watch.start
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.split
//    val splitStr = watch.toSplitString
//    assertEquals(splitStr.length, 12 + StopWatchTest.MESSAGE.length + 1, "Formatted split string not the correct length")
//  }
//
//  @Test def testToString() = { //
//    val watch = StopWatch.createStarted
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.split
//    val splitStr = watch.toString
//    assertEquals(splitStr.length, 12, "Formatted split string not the correct length")
//  }
//
//  @Test def testToStringWithMessage() = {
//    assertTrue(new Nothing(StopWatchTest.MESSAGE).toString.startsWith(StopWatchTest.MESSAGE))
//    val watch = new Nothing(StopWatchTest.MESSAGE)
//    watch.start
//    try Thread.sleep(550)
//    catch {
//      case ex: InterruptedException =>
//    }
//    watch.split
//    val splitStr = watch.toString
//    assertEquals(splitStr.length, 12 + StopWatchTest.MESSAGE.length + 1, "Formatted split string not the correct length")
//  }
//}
