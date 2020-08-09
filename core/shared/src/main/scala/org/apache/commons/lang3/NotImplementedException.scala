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

package org.apache.commons.lang3

/**
  * <p>Thrown to indicate that a block of code has not been implemented.
  * This exception supplements {@code UnsupportedOperationException}
  * by providing a more semantically rich description of the problem.</p>
  *
  * <p>{@code NotImplementedException} represents the case where the
  * author has yet to implement the logic at this point in the program.
  * This can act as an exception based TODO tag. </p>
  *
  * <pre>
  * public void foo() {
  *   try {
  *     // do something that throws an Exception
  *   } catch (Exception ex) {
  *     // don't know what to do here yet
  *     throw new NotImplementedException("TODO", ex);
  *   }
  * }
  * </pre>
  *
  * This class was originally added in Lang 2.0, but removed in 3.0.
  *
  * @param message description of the exception
  * @param cause   cause of the exception
  * @param code    code indicating a resource for more information regarding the lack of implementation
  * @since 3.2
  */
@SerialVersionUID(20131021L)
class NotImplementedException(message: String, cause: Throwable, code: String)
  extends UnsupportedOperationException(message, cause) {
  /**
    * Constructs a NotImplementedException.
    *
    * @param message description of the exception
    * @since 3.2
    */
  def this(message: String) {
    this(message, null, null)
  }

  /**
    * Constructs a NotImplementedException.
    *
    * @param cause cause of the exception
    * @since 3.2
    */
  def this(cause: Throwable) {
    this(null, cause, null)
  }

  /**
    * Constructs a NotImplementedException.
    *
    * @param message description of the exception
    * @param cause   cause of the exception
    * @since 3.2
    */
  def this(message: String, cause: Throwable) {
    this(message, cause, null)
  }

  /**
    * Constructs a NotImplementedException.
    *
    * @param message description of the exception
    * @param code    code indicating a resource for more information regarding the lack of implementation
    * @since 3.2
    */
  def this(message: String, code: String) {
    this(message, null, code)
  }

  /**
    * Constructs a NotImplementedException.
    *
    * @param cause cause of the exception
    * @param code  code indicating a resource for more information regarding the lack of implementation
    * @since 3.2
    */
  def this(cause: Throwable, code: String) {
    this(null, cause, code)
  }

  /**
    * Obtain the not implemented code. This is an unformatted piece of text intended to point to
    * further information regarding the lack of implementation. It might, for example, be an issue
    * tracker ID or a URL.
    *
    * @return a code indicating a resource for more information regarding the lack of implementation
    */
  def getCode: String = this.code
}
