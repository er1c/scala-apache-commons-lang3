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

//package org.apache.commons.lang3.concurrent;
//
//class EventCountCircuitBreakerTest {
//  /**
//    * Tests that time units are correctly taken into account by constructors.
//    */
//    @Test def testIntervalCalculation() = {
//      val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 2, TimeUnit.MILLISECONDS)
//      assertEquals(EventCountCircuitBreakerTest.NANO_FACTOR, breaker.getOpeningInterval, "Wrong opening interval")
//      assertEquals(2 * EventCountCircuitBreakerTest.NANO_FACTOR / 1000, breaker.getClosingInterval, "Wrong closing interval")
//    }
//
//  /**
//    * Tests that the closing interval is the same as the opening interval if it is not
//    * specified.
//    */
//  @Test def testDefaultClosingInterval() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD)
//    assertEquals(EventCountCircuitBreakerTest.NANO_FACTOR, breaker.getClosingInterval, "Wrong closing interval")
//  }
//
//  /**
//    * Tests that the closing threshold is the same as the opening threshold if not
//    * specified otherwise.
//    */
//  @Test def testDefaultClosingThreshold() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS)
//    assertEquals(EventCountCircuitBreakerTest.NANO_FACTOR, breaker.getClosingInterval, "Wrong closing interval")
//    assertEquals(EventCountCircuitBreakerTest.OPENING_THRESHOLD, breaker.getClosingThreshold, "Wrong closing threshold")
//  }
//
//  /**
//    * Tests that a circuit breaker is closed after its creation.
//    */
//  @Test def testInitiallyClosed() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS)
//    assertFalse(breaker.isOpen, "Open")
//    assertTrue(breaker.isClosed, "Not closed")
//  }
//
//  /**
//    * Tests whether the current time is correctly determined.
//    */
//  @Test def testNow() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS)
//    val nowNanos = breaker.now
//    val deltaNanos = Math.abs(System.nanoTime - nowNanos)
//    assertTrue(deltaNanos < 100_000, String.format("Delta %,d ns to current time too large", deltaNanos))
//  }
//
//  /**
//    * Tests that the circuit breaker stays closed if the number of received events stays
//    * below the threshold.
//    */
//  @Test def testNotOpeningUnderThreshold() = {
//    var startTime = 1000
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    for (i <- 0 until EventCountCircuitBreakerTest.OPENING_THRESHOLD - 1) {
//      assertTrue(breaker.at(startTime).incrementAndCheckState, "In open state")
//      startTime += 1
//    }
//    assertTrue(breaker.isClosed, "Not closed")
//  }
//
//  /**
//    * Tests that the circuit breaker stays closed if there are a number of received
//    * events, but not in a single check interval.
//    */
//  @Test def testNotOpeningCheckIntervalExceeded() = {
//    var startTime = 0L
//    val timeIncrement = 3 * EventCountCircuitBreakerTest.NANO_FACTOR / (2 * EventCountCircuitBreakerTest.OPENING_THRESHOLD)
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    for (i <- 0 until 5 * EventCountCircuitBreakerTest.OPENING_THRESHOLD) {
//      assertTrue(breaker.at(startTime).incrementAndCheckState, "In open state")
//      startTime += timeIncrement
//    }
//    assertTrue(breaker.isClosed, "Not closed")
//  }
//
//  /**
//    * Tests that the circuit breaker opens if all conditions are met.
//    */
//  @Test def testOpeningWhenThresholdReached() = {
//    var startTime = 0
//    val timeIncrement = EventCountCircuitBreakerTest.NANO_FACTOR / EventCountCircuitBreakerTest.OPENING_THRESHOLD - 1
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    var open = false
//    for (i <- 0 until EventCountCircuitBreakerTest.OPENING_THRESHOLD + 1) {
//      open = !breaker.at(startTime).incrementAndCheckState
//      startTime += timeIncrement
//    }
//    assertTrue(open, "Not open")
//    assertFalse(breaker.isClosed, "Closed")
//  }
//
//  /**
//    * Tests that the circuit breaker opens if all conditions are met when using
//    * {@link EventCountCircuitBreaker# incrementAndCheckState ( Integer increment)}.
//    */
//  @Test def testOpeningWhenThresholdReachedThroughBatch() = {
//    val timeIncrement = EventCountCircuitBreakerTest.NANO_FACTOR / EventCountCircuitBreakerTest.OPENING_THRESHOLD - 1
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    val startTime = timeIncrement * (EventCountCircuitBreakerTest.OPENING_THRESHOLD + 1)
//    val open = !breaker.at(startTime).incrementAndCheckState(EventCountCircuitBreakerTest.OPENING_THRESHOLD + 1)
//    assertTrue(open, "Not open")
//    assertFalse(breaker.isClosed, "Closed")
//  }
//
//  /**
//    * Tests that an open circuit breaker does not close itself when the number of events
//    * received is over the threshold.
//    */
//  @Test def testNotClosingOverThreshold() = {
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 10, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    var startTime = 0
//    breaker.open
//    for (i <- 0 to EventCountCircuitBreakerTest.CLOSING_THRESHOLD) {
//      assertFalse(breaker.at(startTime).incrementAndCheckState, "Not open")
//      startTime += 1000
//    }
//    assertFalse(breaker.at(startTime + EventCountCircuitBreakerTest.NANO_FACTOR).incrementAndCheckState, "Closed in new interval")
//    assertTrue(breaker.isOpen, "Not open at end")
//  }
//
//  /**
//    * Tests that the circuit breaker closes automatically if the number of events
//    * received goes under the closing threshold.
//    */
//  @Test def testClosingWhenThresholdReached() = {
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 10, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    breaker.open
//    breaker.at(1000).incrementAndCheckState
//    assertFalse(breaker.at(2000).checkState, "Already closed")
//    assertFalse(breaker.at(EventCountCircuitBreakerTest.NANO_FACTOR).checkState, "Closed at interval end")
//    assertTrue(breaker.at(EventCountCircuitBreakerTest.NANO_FACTOR + 1).checkState, "Not closed after interval end")
//    assertTrue(breaker.isClosed, "Not closed at end")
//  }
//
//  /**
//    * Tests whether an explicit open operation fully initializes the internal check data
//    * object. Otherwise, the circuit breaker may close itself directly afterwards.
//    */
//  @Test def testOpenStartsNewCheckInterval() = {
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 2, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    breaker.at(EventCountCircuitBreakerTest.NANO_FACTOR - 1000).open
//    assertTrue(breaker.isOpen, "Not open")
//    assertFalse(breaker.at(EventCountCircuitBreakerTest.NANO_FACTOR + 100).checkState, "Already closed")
//  }
//
//  /**
//    * Tests whether a new check interval is started if the circuit breaker has a
//    * transition to open state.
//    */
//  @Test def testAutomaticOpenStartsNewCheckInterval() = {
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 2, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    var time = 10 * EventCountCircuitBreakerTest.NANO_FACTOR
//    for (i <- 0 to EventCountCircuitBreakerTest.OPENING_THRESHOLD) {
//      breaker.at({
//        time += 1; time - 1
//      }).incrementAndCheckState
//    }
//    assertTrue(breaker.isOpen, "Not open")
//    time += EventCountCircuitBreakerTest.NANO_FACTOR - 1000
//    assertFalse(breaker.at(time).incrementAndCheckState, "Already closed")
//    time += 1001
//    assertTrue(breaker.at(time).checkState, "Not closed in time interval")
//  }
//
//  /**
//    * Tests whether the circuit breaker can be closed explicitly.
//    */
//  @Test def testClose() = {
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 2, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    var time = 0
//    var i = 0
//    while ( {
//      i <= EventCountCircuitBreakerTest.OPENING_THRESHOLD
//    }) {
//      breaker.at(time).incrementAndCheckState
//      i += 1
//      time += 1000
//    }
//    assertTrue(breaker.isOpen, "Not open")
//    breaker.close
//    assertTrue(breaker.isClosed, "Not closed")
//    assertTrue(breaker.at(time + 1000).incrementAndCheckState, "Open again")
//  }
//
//  /**
//    * Tests whether events are generated when the state is changed.
//    */
//  @Test def testChangeEvents() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS)
//    val listener = new EventCountCircuitBreakerTest.ChangeListener(breaker)
//    breaker.addChangeListener(listener)
//    breaker.open
//    breaker.close
//    listener.verify(Boolean.TRUE, Boolean.FALSE)
//  }
//
//  /**
//    * Tests whether a change listener can be removed.
//    */
//  @Test def testRemoveChangeListener() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS)
//    val listener = new EventCountCircuitBreakerTest.ChangeListener(breaker)
//    breaker.addChangeListener(listener)
//    breaker.open
//    breaker.removeChangeListener(listener)
//    breaker.close
//    listener.verify(Boolean.TRUE)
//  }
//
//  /**
//    * Tests that a state transition triggered by multiple threads is handled correctly.
//    * Only the first transition should cause an event to be sent.
//    */
//  @Test
//  @throws[InterruptedException]
//  def testStateTransitionGuarded() = {
//    val breaker = new Nothing(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 1, TimeUnit.SECONDS)
//    val listener = new EventCountCircuitBreakerTest.ChangeListener(breaker)
//    breaker.addChangeListener(listener)
//    val threadCount = 128
//    val latch = new CountDownLatch(1)
//    val threads = new Array[Thread](threadCount)
//    for (i <- 0 until threadCount) {
//      threads(i) = new Thread() {
//        override def run() = {
//          try latch.await()
//          catch {
//            case iex: InterruptedException =>
//
//            // ignore
//          }
//          breaker.open
//        }
//      }
//      threads(i).start()
//    }
//    latch.countDown()
//    for (thread <- threads) {
//      thread.join()
//    }
//    listener.verify(Boolean.TRUE)
//  }
//
//  /**
//    * Tests that automatic state transitions generate change events as well.
//    */
//  @Test def testChangeEventsGeneratedByAutomaticTransitions() = {
//    val breaker = new EventCountCircuitBreakerTest.EventCountCircuitBreakerTestImpl(EventCountCircuitBreakerTest.OPENING_THRESHOLD, 2, TimeUnit.SECONDS, EventCountCircuitBreakerTest.CLOSING_THRESHOLD, 1, TimeUnit.SECONDS)
//    val listener = new EventCountCircuitBreakerTest.ChangeListener(breaker)
//    breaker.addChangeListener(listener)
//    var time = 0
//    var i = 0
//    while ( {
//      i <= EventCountCircuitBreakerTest.OPENING_THRESHOLD
//    }) {
//      breaker.at(time).incrementAndCheckState
//      i += 1
//      time += 1000
//    }
//    breaker.at(EventCountCircuitBreakerTest.NANO_FACTOR + 1).checkState
//    breaker.at(3 * EventCountCircuitBreakerTest.NANO_FACTOR).checkState
//    listener.verify(Boolean.TRUE, Boolean.FALSE)
//  }
//}
