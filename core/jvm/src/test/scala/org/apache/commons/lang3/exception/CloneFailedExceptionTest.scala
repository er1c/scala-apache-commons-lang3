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
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * JUnit tests for {@link CloneFailedExceptionTest}.
  */
class CloneFailedExceptionTest extends AbstractExceptionTest {
  @Test def testThrowingInformativeException(): Unit = {
    assertThrows[CloneFailedException](
      throw new CloneFailedException(AbstractExceptionTest.EXCEPTION_MESSAGE, generateCause))
    ()
  }

  @Test def testThrowingExceptionWithMessage(): Unit = {
    assertThrows[CloneFailedException](throw new CloneFailedException(AbstractExceptionTest.EXCEPTION_MESSAGE))
    ()
  }

  @Test def testThrowingExceptionWithCause(): Unit = {
    assertThrows[CloneFailedException](throw new CloneFailedException(generateCause))
    ()
  }

  @Test def testWithCauseAndMessage(): Unit = {
    val exception = new CloneFailedException(AbstractExceptionTest.EXCEPTION_MESSAGE, generateCause)
    assertNotNull(exception)
    assertEquals(
      AbstractExceptionTest.WRONG_EXCEPTION_MESSAGE,
      AbstractExceptionTest.EXCEPTION_MESSAGE,
      exception.getMessage)
    val cause = exception.getCause
    assertNotNull(cause)
    assertEquals(AbstractExceptionTest.WRONG_CAUSE_MESSAGE, AbstractExceptionTest.CAUSE_MESSAGE, cause.getMessage)
  }

  @Test def testWithoutCause(): Unit = {
    val exception = new CloneFailedException(AbstractExceptionTest.EXCEPTION_MESSAGE)
    assertNotNull(exception)
    assertEquals(
      AbstractExceptionTest.WRONG_EXCEPTION_MESSAGE,
      AbstractExceptionTest.EXCEPTION_MESSAGE,
      exception.getMessage)
    val cause = exception.getCause
    assertNull(cause)
  }

  @Test def testWithoutMessage(): Unit = {
    val exception = new CloneFailedException(generateCause)
    assertNotNull(exception)
    assertNotNull(exception.getMessage)
    val cause = exception.getCause
    assertNotNull(cause)
    assertEquals(AbstractExceptionTest.WRONG_CAUSE_MESSAGE, AbstractExceptionTest.CAUSE_MESSAGE, cause.getMessage)
  }
}
