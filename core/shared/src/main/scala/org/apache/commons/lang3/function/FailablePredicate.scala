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
//object FailablePredicate {
//  /** FALSE singleton */
//  @SuppressWarnings(Array("rawtypes")) val FALSE: FailablePredicate[_, _ <: Throwable] = (t: Any) => { void(t); false }
//  /** TRUE singleton */
//  @SuppressWarnings(Array("rawtypes")) val TRUE: FailablePredicate[_, _ <: Throwable] = (t: Any) => { void(t); true }
//
//  /**
//    * Returns The FALSE singleton.
//    *
//    * @tparam T Predicate type.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def falsePredicate[T, E <: Throwable]: FailablePredicate[T, E] = FALSE.asInstanceOf[FailablePredicate[T, E]]
//
//  /**
//    * Returns The FALSE TRUE.
//    *
//    * @tparam T Predicate type.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def truePredicate[T, E <: Throwable]: FailablePredicate[T, E] = TRUE.asInstanceOf[FailablePredicate[T, E]]
//}
//
///**
//  * A functional interface like {@link java.util.function.Predicate} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam T Predicate type.
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface
//trait FailablePredicate[T, E <: Throwable] {
//  /**
//    * Returns a composed {@code FailablePredicate} like {@link java.util.function.Predicate#and}.
//    *
//    * @param other a predicate that will be logically-ANDed with this predicate.
//    * @return a composed {@code FailablePredicate} like {@link java.util.function.Predicate#and}.
//    * @throws java.lang.NullPointerException if other is null
//    */
//  def and(other: FailablePredicate[_ >: T, E]): FailablePredicate[T, E] = {
//    Objects.requireNonNull(other)
//    (t: T) => test(t) && other.test(t)
//  }
//
//  /**
//    * Returns a predicate that negates this predicate.
//    *
//    * @return a predicate that negates this predicate.
//    */
//  def negate: FailablePredicate[T, E] = (t: T) => !test(t)
//
//  /**
//    * Returns a composed {@code FailablePredicate} like {@link java.util.function.Predicate#and}.
//    *
//    * @param other a predicate that will be logically-ORed with this predicate.
//    * @return a composed {@code FailablePredicate} like {@link java.util.function.Predicate#and}.
//    * @throws java.lang.NullPointerException if other is null
//    */
//  def or(other: FailablePredicate[_ >: T, E]): FailablePredicate[T, E] = {
//    Objects.requireNonNull(other)
//    (t: T) => test(t) || other.test(t)
//  }
//
//  // TODO:     * @throws E if the predicate fails
//  /**
//    * Tests the predicate.
//    *
//    * @param object the object to test the predicate on
//    * @return the predicate's evaluation
//    */
//  @throws[E]
//  def test(`object`: T): Boolean
//}
