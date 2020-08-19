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

package org.apache.commons.lang3.reflect.testbed

import org.apache.commons.lang3.void

/**
  */
object StaticContainer {
  val IMMUTABLE_PUBLIC: String = "public"
  protected val IMMUTABLE_PROTECTED: String = "protected"
  private[testbed] val IMMUTABLE_PACKAGE: String = ""
  @SuppressWarnings(Array("unused"))
  private val IMMUTABLE_PRIVATE = "private"
  void(IMMUTABLE_PRIVATE)
  /**
    * This final modifier of this field is meant to be removed by a test.
    * Using this field may produce unpredictable results.
    */
  @SuppressWarnings(Array("unused"))
  private val IMMUTABLE_PRIVATE_2 = "private"
  void(IMMUTABLE_PRIVATE_2)

  var mutablePublic: String = null
  protected var mutableProtected: String = null
  private[testbed] var mutablePackage: String = null
  private var mutablePrivate: String = null

  def reset() = {
    mutablePublic = null
    mutableProtected = null
    mutablePackage = null
    mutablePrivate = null
  }

  def getMutableProtected: String = mutableProtected

  def getMutablePackage: String = mutablePackage

  def getMutablePrivate: String = mutablePrivate
}
