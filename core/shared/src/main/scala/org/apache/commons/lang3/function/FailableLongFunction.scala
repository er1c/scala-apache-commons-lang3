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

//object FailableLongFunction {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableLongFunction[_, _ <: Throwable] = (t: Long) => { void(t); null }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam R Return type.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[R, E <: Throwable]: FailableLongFunction[R, E] = NOP.asInstanceOf[FailableLongFunction[R, E] ]
//}
//
/**
  * A functional interface like {@link java.util.function.LongFunction} that declares a {@code java.lang.Throwable}.
  *
  * @tparam R Return type.
  * @tparam E Thrown exception.
  * @since 3.11
  */
@FunctionalInterface
trait FailableLongFunction[R, E <: Throwable] {
  // TODO:     * @throws E Thrown when the function fails.
  /**
    * Applies this function.
    *
    * @param input the input for the function
    * @return the result of the function
    */
  @throws[E]
  def apply(input: Long): R
}
