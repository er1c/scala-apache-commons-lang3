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
//import org.easymock.EasyMock.expect
//import org.easymock.EasyMock.replay
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertThrows
//import org.easymock.EasyMock
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
//class MemoizerTest {
//  private var computable = null
//
//  @BeforeEach def setUpComputableMock() = computable = EasyMock.mock(classOf[Nothing])
//
//  @Test
//  @throws[Exception]
//  def testOnlyCallComputableOnceIfDoesNotThrowException() = {
//    val input = 1
//    val memoizer = new Nothing(computable)
//    expect(computable.compute(input)).andReturn(input)
//    replay(computable)
//    assertEquals(input, memoizer.compute(input), "Should call computable first time")
//    assertEquals(input, memoizer.compute(input), "Should not call the computable the second time")
//  }
//
//  @Test
//  @throws[Exception]
//  def testDefaultBehaviourNotToRecalculateExecutionExceptions() = {
//    val input = 1
//    val memoizer = new Nothing(computable)
//    val interruptedException = new InterruptedException
//    expect(computable.compute(input)).andThrow(interruptedException)
//    replay(computable)
//    assertThrows(classOf[Throwable], () => memoizer.compute(input))
//    assertThrows(classOf[IllegalStateException], () => memoizer.compute(input))
//  }
//
//  @Test
//  @throws[Exception]
//  def testDoesNotRecalculateWhenSetToFalse() = {
//    val input = 1
//    val memoizer = new Nothing(computable, false)
//    val interruptedException = new InterruptedException
//    expect(computable.compute(input)).andThrow(interruptedException)
//    replay(computable)
//    assertThrows(classOf[Throwable], () => memoizer.compute(input))
//    assertThrows(classOf[IllegalStateException], () => memoizer.compute(input))
//  }
//
//  @Test
//  @throws[Exception]
//  def testDoesRecalculateWhenSetToTrue() = {
//    val input = 1
//    val answer = 3
//    val memoizer = new Nothing(computable, true)
//    val interruptedException = new InterruptedException
//    expect(computable.compute(input)).andThrow(interruptedException).andReturn(answer)
//    replay(computable)
//    assertThrows(classOf[Throwable], () => memoizer.compute(input))
//    assertEquals(answer, memoizer.compute(input))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWhenComputableThrowsRuntimeException() = {
//    val input = 1
//    val memoizer = new Nothing(computable)
//    val runtimeException = new RuntimeException("Some runtime exception")
//    expect(computable.compute(input)).andThrow(runtimeException)
//    replay(computable)
//    assertThrows(classOf[RuntimeException], () => memoizer.compute(input))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWhenComputableThrowsError() = {
//    val input = 1
//    val memoizer = new Nothing(computable)
//    val error = new Error
//    expect(computable.compute(input)).andThrow(error)
//    replay(computable)
//    assertThrows(classOf[Error], () => memoizer.compute(input))
//  }
//}
