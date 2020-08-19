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
  * <p>Exception thrown when the Serialization process fails.</p>
  *
  * <p>The original error is wrapped within this one.</p>
  *
  * <p>#NotThreadSafe# because Throwable is not thread-safe</p>
  *
  * @param msg   The error message.
  * @param cause The {@code Exception} or {@code Error}
  *              that caused this exception to be thrown.
  * @since 1.0
  */
@SerialVersionUID(4029025366392702726L)
class SerializationException(msg: String, cause: Throwable) extends RuntimeException(msg, cause) {

  /**
    * <p>Constructs a new {@code SerializationException} without specified
    * detail message.</p>
    */
  def this() {
    this(null, null)
  }

  /**
    * <p>Constructs a new {@code SerializationException} with specified
    * detail message.</p>
    *
    * @param msg The error message.
    */
  def this(msg: String) {
    this(msg, null)
  }

  /**
    * <p>Constructs a new {@code SerializationException} with specified
    * nested {@code Throwable}.</p>
    *
    * @param cause The {@code Exception} or {@code Error}
    *              that caused this exception to be thrown.
    */
  def this(cause: Throwable) {
    this(cause.toString, cause)
  }
}
