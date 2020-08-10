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

//object FailableDoubleToLongFunction {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes")) val NOP: FailableDoubleToLongFunction[_ <: Throwable] = (t: Double) => 0
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[E <: Throwable]: FailableDoubleToLongFunction[E] = NOP
//}

/**
  * A functional interface like {@link java.util.function.DoubleToLongFunction} that declares a {@code java.lang.Throwable}.
  *
  * @tparam E Thrown exception.
  * @since 3.11
  */
@FunctionalInterface
trait FailableDoubleToLongFunction[E <: Throwable] {
  // TODO:     * @throws E if the operation fails
  /**
    * Applies this function to the given argument.
    *
    * @param value the function argument
    * @return the function result
    */
  @throws[E]
  def applyAsLong(value: Double): Int
}
