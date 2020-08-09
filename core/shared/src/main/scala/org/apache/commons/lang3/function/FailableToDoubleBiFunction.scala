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

//import org.apache.commons.lang3.void
//
//object FailableToDoubleBiFunction {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableToDoubleBiFunction[_, _, _ <: Throwable] =
//    (t: Any, u: Any) => { void(t); void(u); 0d }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam T the type of the first argument to the function
//    * @tparam U the type of the second argument to the function
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[T, U, E <: Throwable]: FailableToDoubleBiFunction[T, U, E] = NOP.asInstanceOf[FailableToDoubleBiFunction[T, U, E]]
//}

/**
  * A functional interface like {@link java.util.function.ToDoubleBiFunction} that declares a {@code java.lang.Throwable}.
  *
  * @tparam T the type of the first argument to the function
  * @tparam U the type of the second argument to the function
  * @tparam E Thrown exception.
  * @since 3.11
  */
@FunctionalInterface
trait FailableToDoubleBiFunction[T, U, E <: Throwable] {
  // TODO:     * @throws E Thrown when the function fails.
  /**
    * Applies this function to the given arguments.
    *
    * @param t the first function argument
    * @param u the second function argument
    * @return the function result
    */
  @throws[E]
  def applyAsDouble(t: T, u: U): Double
}
