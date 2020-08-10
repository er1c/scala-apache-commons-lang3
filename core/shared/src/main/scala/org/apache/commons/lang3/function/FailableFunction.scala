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
//import org.apache.commons.lang3.void
//
//object FailableFunction {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes")) val NOP: FailableFunction[_, _, _ <: Throwable] = (t: Any) => { void(t); null }
//
//  /**
//    * Returns a function that always returns its input argument.
//    *
//    * @tparam T the type of the input and output objects to the function
//    * @tparam E Thrown exception.
//    * @return a function that always returns its input argument
//    */
//  def identity[T, E <: Throwable]: FailableFunction[T, T, E] = (t: T) => t
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam T Consumed type 1.
//    * @tparam R Return type.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[T, R, E <: Throwable]: FailableFunction[T, R, E] = NOP.asInstanceOf[FailableFunction[T, R, E]]
//}
//
//
///**
//  * A functional interface like {@link java.util.function.Function} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam T Input type 1.
//  * @tparam R Return type.
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface
//trait FailableFunction[T, R, E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableFunction} like {@link java.util.function.Function#andThen}.
//    *
//    * @tparam V the output type of the {@code after} function, and of the composed function.
//    * @return a composed {@code FailableFunction} like {@link java.util.function.Function#andThen}.
//    * @param after the operation to perform after this one.
//    * @throws java.lang.NullPointerException when {@code after} is null.
//    */
//  def andThen[V](after: FailableFunction[_ >: R, _ <: V, E]): FailableFunction[T, V, E] = {
//    Objects.requireNonNull(after)
//    (t: T) => after.apply(apply(t))
//  }
//
//  // TODO:     * @throws E Thrown when the function fails.
//  /**
//    * Applies this function.
//    *
//    * @param input the input for the function
//    * @return the result of the function
//    */
//  @throws[E]
//  def apply(input: T): R
//
//  /**
//    * Returns a composed {@code FailableFunction} like {@link java.util.function.Function#compose}.
//    *
//    * @tparam V      the input type to the {@code before} function, and to the composed function.
//    * @param before the operator to apply before this one.
//    * @return a a composed {@code FailableFunction} like {@link java.util.function.Function#compose}.
//    * @throws java.lang.NullPointerException if before is null.
//    * @see #andThen(FailableFunction)
//    */
//  def compose[V](before: FailableFunction[_ >: V, _ <: T, E]): FailableFunction[V, R, E] = {
//    Objects.requireNonNull(before)
//    (v: V) => apply(before.apply(v))
//  }
//}
