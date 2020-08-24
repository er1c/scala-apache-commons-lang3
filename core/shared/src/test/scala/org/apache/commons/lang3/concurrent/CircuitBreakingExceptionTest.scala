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
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.apache.commons.lang3.exception.AbstractExceptionTest
//import org.junit.Test
//
///**
//  * JUnit tests for {@link CircuitBreakingException}.
//  */
//class CircuitBreakingExceptionTest extends AbstractExceptionTest {
//  @Test def testThrowingInformativeException() = assertThrows(classOf[Nothing], () => {
//    def foo() =
//      throw new Nothing(EXCEPTION_MESSAGE, generateCause)
//
//    foo()
//  })
//
//  @Test def testThrowingExceptionWithMessage() = assertThrows(classOf[Nothing], () => {
//    def foo() =
//      throw new Nothing(EXCEPTION_MESSAGE)
//
//    foo()
//  })
//
//  @Test def testThrowingExceptionWithCause() = assertThrows(classOf[Nothing], () => {
//    def foo() =
//      throw new Nothing(generateCause)
//
//    foo()
//  })
//
//  @Test def testThrowingEmptyException() = assertThrows(classOf[Nothing], () => {
//    def foo() =
//      throw new Nothing
//
//    foo()
//  })
//
//  @Test def testWithCauseAndMessage() = {
//    val exception = new Nothing(EXCEPTION_MESSAGE, generateCause)
//    assertNotNull(exception)
//    assertEquals(EXCEPTION_MESSAGE, exception.getMessage, WRONG_EXCEPTION_MESSAGE)
//    val cause = exception.getCause
//    assertNotNull(cause)
//    assertEquals(CAUSE_MESSAGE, cause.getMessage, WRONG_CAUSE_MESSAGE)
//  }
//
//  @Test def testWithoutCause() = {
//    val exception = new Nothing(EXCEPTION_MESSAGE)
//    assertNotNull(exception)
//    assertEquals(EXCEPTION_MESSAGE, exception.getMessage, WRONG_EXCEPTION_MESSAGE)
//    val cause = exception.getCause
//    assertNull(cause)
//  }
//
//  @Test def testWithoutMessage() = {
//    val exception = new Nothing(generateCause)
//    assertNotNull(exception)
//    assertNotNull(exception.getMessage)
//    val cause = exception.getCause
//    assertNotNull(cause)
//    assertEquals(CAUSE_MESSAGE, cause.getMessage, WRONG_CAUSE_MESSAGE)
//  }
//}
