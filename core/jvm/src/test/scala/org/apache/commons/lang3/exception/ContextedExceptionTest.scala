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

package org.apache.commons.lang3.exception

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import java.util.Date
import org.apache.commons.lang3.StringUtils
import org.junit.Before
import org.junit.Test

/**
  * JUnit tests for ContextedException.
  */
class ContextedExceptionTest extends AbstractExceptionContextTest[ContextedException] {
  import AbstractExceptionContextTest._

  @Before
  @throws[Exception]
  override def setUp(): Unit = {
    exceptionContext = new ContextedException(new Exception(TEST_MESSAGE))
    super.setUp()
  }

  @Test def testContextedException() = {
    exceptionContext = new ContextedException
    val message = exceptionContext.getMessage
    val trace = ExceptionUtils.getStackTrace(exceptionContext)
    assertTrue(trace.contains("ContextedException"))
    assertTrue(StringUtils.isEmpty(message))
  }

  @Test def testContextedExceptionString() = {
    exceptionContext = new ContextedException(TEST_MESSAGE)
    assertEquals(TEST_MESSAGE, exceptionContext.getMessage)
    val trace = ExceptionUtils.getStackTrace(exceptionContext)
    assertTrue(trace.contains(TEST_MESSAGE))
  }

  @Test def testContextedExceptionThrowable() = {
    exceptionContext = new ContextedException(new Exception(TEST_MESSAGE))
    val message = exceptionContext.getMessage
    val trace = ExceptionUtils.getStackTrace(exceptionContext)
    assertTrue(trace.contains("ContextedException"))
    assertTrue(trace.contains(TEST_MESSAGE))
    assertTrue(message.contains(TEST_MESSAGE))
  }

  @Test def testContextedExceptionStringThrowable() = {
    exceptionContext = new ContextedException(TEST_MESSAGE_2, new Exception(TEST_MESSAGE))
    val message = exceptionContext.getMessage
    val trace = ExceptionUtils.getStackTrace(exceptionContext)
    assertTrue(trace.contains("ContextedException"))
    assertTrue(trace.contains(TEST_MESSAGE))
    assertTrue(trace.contains(TEST_MESSAGE_2))
    assertTrue(message.contains(TEST_MESSAGE_2))
  }

  @Test def testContextedExceptionStringThrowableContext() = {
    exceptionContext = new ContextedException(TEST_MESSAGE_2, new Exception(TEST_MESSAGE), new DefaultExceptionContext)
    val message = exceptionContext.getMessage
    val trace = ExceptionUtils.getStackTrace(exceptionContext)
    assertTrue(trace.contains("ContextedException"))
    assertTrue(trace.contains(TEST_MESSAGE))
    assertTrue(trace.contains(TEST_MESSAGE_2))
    assertTrue(message.contains(TEST_MESSAGE_2))
  }

  @Test def testNullExceptionPassing() = {
    exceptionContext = new ContextedException(TEST_MESSAGE_2, new Exception(TEST_MESSAGE), null)
      .addContextValue("test1", null)
      .addContextValue("test2", "some value")
      .addContextValue("test Date", new Date)
      .addContextValue("test Nbr", Integer.valueOf(5))
      .addContextValue("test Poorly written obj", new AbstractExceptionContextTest.ObjectWithFaultyToString)
    val message = exceptionContext.getMessage
    assertNotNull(message)
  }

  @Test def testRawMessage() = {
    assertEquals(classOf[Exception].getName + ": " + TEST_MESSAGE, exceptionContext.getRawMessage)
    exceptionContext = new ContextedException(TEST_MESSAGE_2, new Exception(TEST_MESSAGE), new DefaultExceptionContext)
    assertEquals(TEST_MESSAGE_2, exceptionContext.getRawMessage)
    exceptionContext = new ContextedException(null, new Exception(TEST_MESSAGE), new DefaultExceptionContext)
    assertNull(exceptionContext.getRawMessage)
  }
}
