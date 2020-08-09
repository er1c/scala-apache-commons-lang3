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

//package org.apache.commons.lang3.function
//
//import java.util.Objects
//import java.util.function.BiFunction
//
///**
//  * @since 3.11
//  */
//object FailableBiFunction {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableBiFunction[_, _, _, _ <: Throwable] = (t: Any, u: Any) => null
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam T Consumed type 1.
//    * @tparam U Consumed type 2.
//    * @tparam R Return type.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[T, U, R, E <: Throwable]: FailableBiFunction[T, U, R, E] = NOP.asInstanceOf[FailableBiFunction[T, U, R, E]]
//}
//
///**
//  * A functional interface like {@link java.util.function.BiFunction} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam T Input type 1.
//  * @tparam U Input type 2.
//  * @tparam R Return type.
//  * @tparam E Thrown exception.
//  */
//@FunctionalInterface
//trait FailableBiFunction[T, U, R, E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableBiFunction} that like {@link BiFunction# andThen ( Function )}.
//    *
//    * @tparam V    the output type of the {@code after} function, and of the composed function.
//    * @param after the operation to perform after this one.
//    * @return a composed {@code FailableBiFunction} that like {@link BiFunction# andThen ( Function )}.
//    * @throws NullPointerException when {@code after} is null.
//    */
//  def andThen[V](after: FailableFunction[_ <: R, _ <: V, E]): FailableBiFunction[T, U, V, E] = {
//    Objects.requireNonNull(after)
//    (t: T, u: U) => after.apply(apply(t, u))
//  }
//
//  /**
//    * Applies this function.
//    *
//    * @param input1 the first input for the function
//    * @param input2 the second input for the function
//    * @return the result of the function
//    * @throws E Thrown when the function fails.
//    */
//  @throws[E]
//  def apply(input1: T, input2: U): R
//}
