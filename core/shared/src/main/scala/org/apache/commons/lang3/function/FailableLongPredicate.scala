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
//object FailableLongPredicate {
//  /** FALSE singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val FALSE: FailableLongPredicate[_ <: Throwable] = (t: Long) => { void(t); false }
//  /** TRUE singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val TRUE: FailableLongPredicate[_ <: Throwable] = (t: Long) => { void(t); true }
//
//  /**
//    * Returns The FALSE singleton.
//    *
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def falsePredicate[E <: Throwable]: FailableLongPredicate[E] = FALSE.asInstanceOf[FailableLongPredicate[E]]
//
//  /**
//    * Returns The FALSE TRUE.
//    *
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def truePredicate[E <: Throwable]: FailableLongPredicate[E] = TRUE.asInstanceOf[FailableLongPredicate[E]]
//}
//
//
///**
//  * A functional interface like {@link java.util.function.LongPredicate} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface
//trait FailableLongPredicate[E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableLongPredicate} like {@link java.util.function.LongPredicate#and}.
//    *
//    * @param other a predicate that will be logically-ANDed with this predicate.
//    * @return a composed {@code FailableLongPredicate} like {@link java.util.function.LongPredicate#and}.
//    * @throws java.lang.NullPointerException if other is null
//    */
//  def and(other: FailableLongPredicate[E]): FailableLongPredicate[E] = {
//    Objects.requireNonNull(other)
//    (t: Long) => test(t) && other.test(t)
//  }
//
//  /**
//    * Returns a predicate that negates this predicate.
//    *
//    * @return a predicate that negates this predicate.
//    */
//  def negate: FailableLongPredicate[E] = (t: Long) => !test(t)
//
//  /**
//    * Returns a composed {@code FailableLongPredicate} like {@link java.util.function.LongPredicate#and}.
//    *
//    * @param other a predicate that will be logically-ORed with this predicate.
//    * @return a composed {@code FailableLongPredicate} like {@link java.util.function.LongPredicate#and}.
//    * @throws java.lang.NullPointerException if other is null
//    */
//  def or(other: FailableLongPredicate[E]): FailableLongPredicate[E] = {
//    Objects.requireNonNull(other)
//    (t: Long) => test(t) || other.test(t)
//  }
//
//  // TODO:     * @throws E Thrown when the consumer fails.
//  /**
//    * Tests the predicate.
//    *
//    * @param value the parameter for the predicate to accept.
//    * @return {@code true} if the input argument matches the predicate, {@code false} otherwise.
//    */
//  @throws[E]
//  def test(value: Long): Boolean
//}
