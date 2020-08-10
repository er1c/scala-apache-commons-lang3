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
//object FailableIntUnaryOperator {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes")) val NOP: FailableIntUnaryOperator[_ <: Throwable] = (t: Int) => { void(t); 0 }
//
//  /**
//    * Returns a unary operator that always returns its input argument.
//    *
//    * @tparam E Thrown exception.
//    * @return a unary operator that always returns its input argument
//    */
//  def identity[E <: Throwable]: FailableIntUnaryOperator[E] = (t: Int) => t
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[E <: Throwable]: FailableIntUnaryOperator[E] = NOP.asInstanceOf[FailableIntUnaryOperator[E]]
//}
//
///**
//  * A functional interface like {@link java.util.function.IntUnaryOperator} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//trait FailableIntUnaryOperator[E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableDoubleUnaryOperator} like {@link java.util.function.IntUnaryOperator#andThen}.
//    *
//    * @param after the operator to apply after this one.
//    * @return a composed {@code FailableIntUnaryOperator} like {@link java.util.function.IntUnaryOperator#andThen}.
//    * @throws java.lang.NullPointerException if after is null.
//    * @see #compose
//    */
//  def andThen(after: FailableIntUnaryOperator[E]): FailableIntUnaryOperator[E] = {
//    Objects.requireNonNull(after)
//    (t: Int) => after.applyAsInt(applyAsInt(t))
//  }
//
//  // TODO:     * @throws E Thrown when a consumer fails.
//  /**
//    * Applies this operator to the given operand.
//    *
//    * @param operand the operand
//    * @return the operator result
//    */
//  @throws[E]
//  def applyAsInt(operand: Int): Int
//
//  /**
//    * Returns a composed {@code FailableIntUnaryOperator} like {@link java.util.function.IntUnaryOperator#compose}.
//    *
//    * @param before the operator to apply before this one.
//    * @return a composed {@code FailableIntUnaryOperator} like {@link java.util.function.IntUnaryOperator#compose}.
//    * @throws java.lang.NullPointerException if before is null.
//    * @see #andThen
//    */
//  def compose(before: FailableIntUnaryOperator[E]): FailableIntUnaryOperator[E] = {
//    Objects.requireNonNull(before)
//    (v: Int) => applyAsInt(before.applyAsInt(v))
//  }
//}
