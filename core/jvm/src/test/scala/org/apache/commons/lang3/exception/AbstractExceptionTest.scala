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

import org.scalatestplus.junit.JUnitSuite

/**
  * Base class for testing {@link Exception} descendants
  */
object AbstractExceptionTest {
  protected[exception] val CAUSE_MESSAGE = "Cause message"
  protected[exception] val EXCEPTION_MESSAGE = "Exception message"
  protected[exception] val WRONG_EXCEPTION_MESSAGE = "Wrong exception message"
  protected[exception] val WRONG_CAUSE_MESSAGE = "Wrong cause message"
}

abstract class AbstractExceptionTest extends JUnitSuite {
  protected def generateCause = new Exception(AbstractExceptionTest.CAUSE_MESSAGE)
}
