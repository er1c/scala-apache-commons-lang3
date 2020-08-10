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

package org.apache.commons.lang3.function

//object FailableDoubleFunction {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableDoubleFunction[_, _ <: Throwable] = (t: Double) => null
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam R Return type.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[R, E <: Throwable]: FailableDoubleFunction[R, E] = NOP
//}

/**
  * A functional interface like {@link java.util.function.DoubleFunction} that declares a {@code java.lang.Throwable}.
  *
  * @tparam R Return type.
  * @tparam E Thrown exception.
  * @since 3.11
  */
@FunctionalInterface
trait FailableDoubleFunction[R, E <: Throwable] {
  // TODO:     * @throws E Thrown when the function fails.
  /**
    * Applies this function.
    *
    * @param input the input for the function
    * @return the result of the function
    */
  @throws[E]
  def apply(input: Double): R
}
