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
//object FailableLongUnaryOperator {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes")) val NOP: FailableLongUnaryOperator[_ <: Throwable] = (t: Long) => { void(t); 0L }
//
//  /**
//    * Returns a unary operator that always returns its input argument.
//    *
//    * @tparam E Thrown exception.
//    * @return a unary operator that always returns its input argument
//    */
//  def identity[E <: Throwable]: FailableLongUnaryOperator[E] = (t: Long) => t
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[E <: Throwable]: FailableLongUnaryOperator[E] = NOP.asInstanceOf[FailableLongUnaryOperator[E]]
//}
//
///**
//  * A functional interface like {@link java.util.function.LongUnaryOperator} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//trait FailableLongUnaryOperator[E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableDoubleUnaryOperator} like {@link java.util.function.LongUnaryOperator#andThen}.
//    *
//    * @param after the operator to apply after this one.
//    * @return a composed {@code FailableLongUnaryOperator} like {@link java.util.function.LongUnaryOperator#andThen}.
//    * @throws java.lang.NullPointerException if after is null.
//    * @see #compose(FailableLongUnaryOperator)
//    */
//  def andThen(after: FailableLongUnaryOperator[E]): FailableLongUnaryOperator[E] = {
//    Objects.requireNonNull(after)
//    (t: Long) => after.applyAsLong(applyAsLong(t))
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
//  def applyAsLong(operand: Long): Long
//
//  /**
//    * Returns a composed {@code FailableLongUnaryOperator} like {@link java.util.function.LongUnaryOperator#compose}.
//    *
//    * @param before the operator to apply before this one.
//    * @return a composed {@code FailableLongUnaryOperator} like {@link java.util.function.LongUnaryOperator#compose}.
//    * @throws java.lang.NullPointerException if before is null.
//    * @see #andThen(FailableLongUnaryOperator)
//    */
//  def compose(before: FailableLongUnaryOperator[E]): FailableLongUnaryOperator[E] = {
//    Objects.requireNonNull(before)
//    (v: Long) => applyAsLong(before.applyAsLong(v))
//  }
//}
