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
//import java.util.function.BiPredicate
//
///**
//  * A functional interface like {@link BiPredicate} that declares a {@code Throwable}.
//  *
//  * @param < T> Predicate type 1.
//  * @param < U> Predicate type 2.
//  * @param < E> Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface object FailableBiPredicate {
//  /** FALSE singleton */
//  @SuppressWarnings(Array("rawtypes")) val FALSE: FailableBiPredicate[_, _, _ <: Throwable] = (t: Any, u: Any) => false
//  /** TRUE singleton */
//  @SuppressWarnings(Array("rawtypes")) val TRUE: FailableBiPredicate[_, _, _ <: Throwable] = (t: Any, u: Any) => true
//
//  /**
//    * Returns The FALSE singleton.
//    *
//    * @param < T> Consumed type 1.
//    * @param < U> Consumed type 2.
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def falsePredicate[T, U, E <: Throwable]: FailableBiPredicate[T, U, E] = FALSE
//
//  /**
//    * Returns The FALSE TRUE.
//    *
//    * @param < T> Consumed type 1.
//    * @param < U> Consumed type 2.
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def truePredicate[T, U, E <: Throwable]: FailableBiPredicate[T, U, E] = TRUE
//}
//
//@FunctionalInterface trait FailableBiPredicate[T, U, E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableBiPredicate} like {@link BiPredicate# and ( BiPredicate )}.
//    *
//    * @param other a predicate that will be logically-ANDed with this predicate.
//    * @return a composed {@code FailableBiPredicate} like {@link BiPredicate# and ( BiPredicate )}.
//    * @throws NullPointerException if other is null
//    */
//  def and(other: FailableBiPredicate[_ >: T, _ >: U, E]): FailableBiPredicate[T, U, E] = {
//    Objects.requireNonNull(other)
//    (t: T, u: U) => test(t, u) && other.test(t, u)
//  }
//
//  /**
//    * Returns a predicate that negates this predicate.
//    *
//    * @return a predicate that negates this predicate.
//    */
//  def negate: FailableBiPredicate[T, U, E] = (t: T, u: U) => !test(t, u)
//
//  /**
//    * Returns a composed {@code FailableBiPredicate} like {@link BiPredicate# and ( BiPredicate )}.
//    *
//    * @param other a predicate that will be logically-ORed with this predicate.
//    * @return a composed {@code FailableBiPredicate} like {@link BiPredicate# and ( BiPredicate )}.
//    * @throws NullPointerException if other is null
//    */
//  def or(other: FailableBiPredicate[_ >: T, _ >: U, E]): FailableBiPredicate[T, U, E] = {
//    Objects.requireNonNull(other)
//    (t: T, u: U) => test(t, u) || other.test(t, u)
//  }
//
//  /**
//    * Tests the predicate.
//    *
//    * @param object1 the first object to test the predicate on
//    * @param object2 the second object to test the predicate on
//    * @return the predicate's evaluation
//    * @throws E Thrown when this predicate fails.
//    */
//  @throws[E]
//  def test(object1: T, object2: U): Boolean
//}
