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
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util.concurrent.CountDownLatch
//import java.util.concurrent.ScheduledExecutorService
//import java.util.concurrent.ScheduledFuture
//import java.util.concurrent.ScheduledThreadPoolExecutor
//import java.util.concurrent.TimeUnit
//import org.easymock.EasyMock
//import org.junit.Test
//
///**
//  * Test class for TimedSemaphore.
//  */
//object TimedSemaphoreTest {
//  /** Constant for the time period. */
//    private val PERIOD = 500
//  /** Constant for the time unit. */
//  private val UNIT = TimeUnit.MILLISECONDS
//  /** Constant for the default limit. */
//  private val LIMIT = 10
//
//  /**
//    * A specialized implementation of {@code TimedSemaphore} that is easier to
//    * test.
//    */
//  private class TimedSemaphoreTestImpl extends Nothing {
//    /** A mock scheduled future. */
//      private[concurrent] val schedFuture = null
//    /** A latch for synchronizing with the main thread. */
//    private[concurrent] val latch = null
//    /** Counter for the endOfPeriod() invocations. */
//    private var periodEnds = 0
//
//    def this(timePeriod: Long, timeUnit: TimeUnit, limit: Int) {
//      this()
//      super (timePeriod, timeUnit, limit)
//    }
//
//    def this(service: ScheduledExecutorService, timePeriod: Long, timeUnit: TimeUnit, limit: Int) {
//      this()
//      super (service, timePeriod, timeUnit, limit)
//    }
//
//    /**
//      * Returns the number of invocations of the endOfPeriod() method.
//      *
//      * @return the endOfPeriod() invocations
//      */
//    private[concurrent] def getPeriodEnds = {
//      this synchronized periodEnds
//    }
//
//    /**
//      * Invokes the latch if one is set.
//      *
//      * @throws java.lang.InterruptedException because it is declared that way in TimedSemaphore
//      */
//    @throws[InterruptedException]
//    def acquire() = {
//      super.acquire
//      if (latch != null) latch.countDown()
//    }
//
//    /**
//      * Counts the number of invocations.
//      */
//    protected def endOfPeriod() = {
//      super.endOfPeriod
//      periodEnds += 1
//    }
//
//    /**
//      * Either returns the mock future or calls the super method.
//      */
//    protected def startTimer = if (schedFuture != null) schedFuture
//    else super.startTimer
//  }
//
//  /**
//    * A test thread class that will be used by tests for triggering the
//    * semaphore. The thread calls the semaphore a configurable number of times.
//    * When this is done, it can notify the main thread.
//    */
//  private class SemaphoreThread private[concurrent](/** The semaphore. */
//    val semaphore: Nothing,
//
//    /** A latch for communication with the main thread. */
//    val latch: CountDownLatch,
//
//    /** The number of acquire() calls. */
//    val count: Int,
//
//    /** The number of invocations of the latch. */
//    val latchCount: Int) extends Thread {
//    /**
//      * Calls acquire() on the semaphore for the specified number of times.
//      * Optionally the latch will also be triggered to synchronize with the
//      * main test thread.
//      */
//    override def run() = try for (i <- 0 until count) {
//      semaphore.acquire
//      if (i < latchCount) latch.countDown()
//    }
//    catch {
//      case iex: InterruptedException =>
//        Thread.currentThread.interrupt()
//    }
//  }
//
//  /**
//    * A test thread class which invokes {@code tryAcquire()} on the test semaphore and
//    * records the return value.
//    */
//  private class TryAcquireThread private[concurrent](val semaphore: Nothing, val latch: CountDownLatch) extends Thread {
//    /** Flag whether a permit could be acquired. */
//    private var acquired = false
//
//    override def run() = try if (latch.await(10, TimeUnit.SECONDS)) acquired = semaphore.tryAcquire
//    catch {
//      case iex: InterruptedException =>
//
//      // ignore
//    }
//  }
//
//}
//
//class TimedSemaphoreTest {
//  /**
//    * Tests creating a new instance.
//    */
//    @Test def testInit() = {
//      val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//      EasyMock.replay(service)
//      val semaphore = new Nothing(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//      EasyMock.verify(service)
//      assertEquals(service, semaphore.getExecutorService, "Wrong service")
//      assertEquals(TimedSemaphoreTest.PERIOD, semaphore.getPeriod, "Wrong period")
//      assertEquals(TimedSemaphoreTest.UNIT, semaphore.getUnit, "Wrong unit")
//      assertEquals(0, semaphore.getLastAcquiresPerPeriod, "Statistic available")
//      assertEquals(0.0, semaphore.getAverageCallsPerPeriod, .05, "Average available")
//      assertFalse(semaphore.isShutdown, "Already shutdown")
//      assertEquals(TimedSemaphoreTest.LIMIT, semaphore.getLimit, "Wrong limit")
//    }
//
//  /**
//    * Tries to create an instance with a negative period. This should cause an
//    * exception.
//    */
//  @Test def testInitInvalidPeriod() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(0L, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT))
//
//  /**
//    * Tests whether a default executor service is created if no service is
//    * provided.
//    */
//  @Test def testInitDefaultService() = {
//    val semaphore = new Nothing(TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    val exec = semaphore.getExecutorService.asInstanceOf[ScheduledThreadPoolExecutor]
//    assertFalse(exec.getContinueExistingPeriodicTasksAfterShutdownPolicy, "Wrong periodic task policy")
//    assertFalse(exec.getExecuteExistingDelayedTasksAfterShutdownPolicy, "Wrong delayed task policy")
//    assertFalse(exec.isShutdown, "Already shutdown")
//    semaphore.shutdown
//  }
//
//  /**
//    * Tests starting the timer.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testStartTimer() = {
//    val semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    val future = semaphore.startTimer
//    assertNotNull(future, "No future returned")
//    Thread.sleep(TimedSemaphoreTest.PERIOD)
//    val trials = 10
//    var count = 0
//    do {
//      Thread.sleep(TimedSemaphoreTest.PERIOD)
//      assertFalse({
//        count += 1; count - 1
//      } > trials, "endOfPeriod() not called!")
//    } while ( {
//      semaphore.getPeriodEnds <= 0
//    })
//    semaphore.shutdown
//  }
//
//  /**
//    * Tests the shutdown() method if the executor belongs to the semaphore. In
//    * this case it has to be shut down.
//    */
//  @Test def testShutdownOwnExecutor() = {
//    val semaphore = new Nothing(TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.shutdown
//    assertTrue(semaphore.isShutdown, "Not shutdown")
//    assertTrue(semaphore.getExecutorService.isShutdown, "Executor not shutdown")
//  }
//
//  /**
//    * Tests the shutdown() method for a shared executor service before a task
//    * was started. This should do pretty much nothing.
//    */
//  @Test def testShutdownSharedExecutorNoTask() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    EasyMock.replay(service)
//    val semaphore = new Nothing(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.shutdown
//    assertTrue(semaphore.isShutdown, "Not shutdown")
//    EasyMock.verify(service)
//  }
//
//  /**
//    * Prepares an executor service mock to expect the start of the timer.
//    *
//    * @param service the mock
//    * @param future  the future
//    */
//  private def prepareStartTimer(service: ScheduledExecutorService, future: ScheduledFuture[_]) = {
//    service.scheduleAtFixedRate(EasyMock.anyObject.asInstanceOf[Runnable], EasyMock.eq(TimedSemaphoreTest.PERIOD), EasyMock.eq(TimedSemaphoreTest.PERIOD), EasyMock.eq(TimedSemaphoreTest.UNIT))
//    EasyMock.expectLastCall.andReturn(future)
//  }
//
//  /**
//    * Tests the shutdown() method for a shared executor after the task was
//    * started. In this case the task must be canceled.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testShutdownSharedExecutorTask() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE)
//    EasyMock.replay(service, future)
//    val semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.acquire()
//    semaphore.shutdown
//    assertTrue(semaphore.isShutdown, "Not shutdown")
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tests multiple invocations of the shutdown() method.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testShutdownMultipleTimes() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.expect(Boolean.valueOf(future.cancel(false))).andReturn(Boolean.TRUE)
//    EasyMock.replay(service, future)
//    val semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.acquire()
//    for (i <- 0 until 10) {
//      semaphore.shutdown
//    }
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tests the acquire() method if a limit is set.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testAcquireLimit() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.replay(service, future)
//    val count = 10
//    val latch = new CountDownLatch(count - 1)
//    val semaphore = new Nothing(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, 1)
//    val t = new TimedSemaphoreTest.SemaphoreThread(semaphore, latch, count, count - 1)
//    semaphore.setLimit(count - 1)
//    // start a thread that calls the semaphore count times
//    t.start()
//    latch.await()
//    // now the semaphore's limit should be reached and the thread blocked
//    assertEquals(count - 1, semaphore.getAcquireCount, "Wrong semaphore count")
//    // this wakes up the thread, it should call the semaphore once more
//    semaphore.endOfPeriod
//    t.join()
//    assertEquals(1, semaphore.getAcquireCount, "Wrong semaphore count (2)")
//    assertEquals(count - 1, semaphore.getLastAcquiresPerPeriod, "Wrong acquire() count")
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tests the acquire() method if more threads are involved than the limit.
//    * This method starts a number of threads that all invoke the semaphore. The
//    * semaphore's limit is set to 1, so in each period only a single thread can
//    * acquire the semaphore.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testAcquireMultipleThreads() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.replay(service, future)
//    val semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, 1)
//    semaphore.latch = new CountDownLatch(1)
//    val count = 10
//    val threads = new Array[TimedSemaphoreTest.SemaphoreThread](count)
//    for (i <- 0 until count) {
//      threads(i) = new TimedSemaphoreTest.SemaphoreThread(semaphore, null, 1, 0)
//      threads(i).start()
//    }
//    for (i <- 0 until count) {
//      semaphore.latch.await()
//      assertEquals(1, semaphore.getAcquireCount, "Wrong count")
//      semaphore.latch = new CountDownLatch(1)
//      semaphore.endOfPeriod()
//      assertEquals(1, semaphore.getLastAcquiresPerPeriod, "Wrong acquire count")
//    }
//    for (i <- 0 until count) {
//      threads(i).join()
//    }
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tests the acquire() method if no limit is set. A test thread is started
//    * that calls the semaphore a large number of times. Even if the semaphore's
//    * period does not end, the thread should never block.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testAcquireNoLimit() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.replay(service, future)
//    val semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphore.NO_LIMIT)
//    val count = 1000
//    val latch = new CountDownLatch(count)
//    val t = new TimedSemaphoreTest.SemaphoreThread(semaphore, latch, count, count)
//    t.start()
//    latch.await()
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tries to call acquire() after shutdown(). This should cause an exception.
//    */
//  @Test def testPassAfterShutdown() = {
//    val semaphore = new Nothing(TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.shutdown
//    assertThrows(classOf[IllegalStateException], semaphore.acquire)
//  }
//
//  /**
//    * Tests a bigger number of invocations that span multiple periods. The
//    * period is set to a very short time. A background thread calls the
//    * semaphore a large number of times. While it runs at last one end of a
//    * period should be reached.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testAcquireMultiplePeriods() = {
//    val count = 1000
//    val semaphore = new TimedSemaphoreTest.TimedSemaphoreTestImpl(TimedSemaphoreTest.PERIOD / 10, TimeUnit.MILLISECONDS, 1)
//    semaphore.setLimit(count / 4)
//    val latch = new CountDownLatch(count)
//    val t = new TimedSemaphoreTest.SemaphoreThread(semaphore, latch, count, count)
//    t.start()
//    latch.await()
//    semaphore.shutdown
//    assertTrue(semaphore.getPeriodEnds > 0, "End of period not reached")
//  }
//
//  /**
//    * Tests the methods for statistics.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testGetAverageCallsPerPeriod() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.replay(service, future)
//    val semaphore = new Nothing(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.acquire
//    semaphore.endOfPeriod
//    assertEquals(1.0, semaphore.getAverageCallsPerPeriod, .005, "Wrong average (1)")
//    semaphore.acquire
//    semaphore.acquire
//    semaphore.endOfPeriod
//    assertEquals(1.5, semaphore.getAverageCallsPerPeriod, .005, "Wrong average (2)")
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tests whether the available non-blocking calls can be queried.
//    *
//    * @throws java.lang.InterruptedException so we don't have to catch it
//    */
//  @Test
//  @throws[InterruptedException]
//  def testGetAvailablePermits() = {
//    val service = EasyMock.createMock(classOf[ScheduledExecutorService])
//    val future = EasyMock.createMock(classOf[ScheduledFuture[_]])
//    prepareStartTimer(service, future)
//    EasyMock.replay(service, future)
//    val semaphore = new Nothing(service, TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    for (i <- 0 until TimedSemaphoreTest.LIMIT) {
//      assertEquals(TimedSemaphoreTest.LIMIT - i, semaphore.getAvailablePermits, "Wrong available count at " + i)
//      semaphore.acquire
//    }
//    semaphore.endOfPeriod
//    assertEquals(TimedSemaphoreTest.LIMIT, semaphore.getAvailablePermits, "Wrong available count in new period")
//    EasyMock.verify(service, future)
//  }
//
//  /**
//    * Tests the tryAcquire() method. It is checked whether the semaphore can be acquired
//    * by a bunch of threads the expected number of times and not more.
//    */
//  @Test
//  @throws[InterruptedException]
//  def testTryAcquire() = {
//    val semaphore = new Nothing(TimedSemaphoreTest.PERIOD, TimeUnit.SECONDS, TimedSemaphoreTest.LIMIT)
//    val threads = new Array[TimedSemaphoreTest.TryAcquireThread](3 * TimedSemaphoreTest.LIMIT)
//    val latch = new CountDownLatch(1)
//    for (i <- 0 until threads.length) {
//      threads(i) = new TimedSemaphoreTest.TryAcquireThread(semaphore, latch)
//      threads(i).start()
//    }
//    latch.countDown()
//    var permits = 0
//    for (t <- threads) {
//      t.join()
//      if (t.acquired) permits += 1
//    }
//    assertEquals(TimedSemaphoreTest.LIMIT, permits, "Wrong number of permits granted")
//  }
//
//  /**
//    * Tries to call tryAcquire() after shutdown(). This should cause an exception.
//    */
//  @Test def testTryAcquireAfterShutdown() = {
//    val semaphore = new Nothing(TimedSemaphoreTest.PERIOD, TimedSemaphoreTest.UNIT, TimedSemaphoreTest.LIMIT)
//    semaphore.shutdown
//    assertThrows(classOf[IllegalStateException], semaphore.tryAcquire)
//  }
//}
