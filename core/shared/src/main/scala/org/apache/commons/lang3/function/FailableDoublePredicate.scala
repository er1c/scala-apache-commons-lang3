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
//import java.util.function.DoublePredicate
//
//@FunctionalInterface object FailableDoublePredicate {
//  /** FALSE singleton */
//  @SuppressWarnings(Array("rawtypes")) val FALSE: FailableDoublePredicate[_ <: Throwable] = (t: Double) => false
//  /** TRUE singleton */
//  @SuppressWarnings(Array("rawtypes")) val TRUE: FailableDoublePredicate[_ <: Throwable] = (t: Double) => true
//
//  /**
//    * Returns The FALSE singleton.
//    *
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def falsePredicate[E <: Throwable]: FailableDoublePredicate[E] = FALSE
//
//  /**
//    * Returns The FALSE TRUE.
//    *
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def truePredicate[E <: Throwable]: FailableDoublePredicate[E] = TRUE
//}
//
///**
//  * A functional interface like {@link java.util.function.DoublePredicate} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface
//trait FailableDoublePredicate[E <: Throwable] {
//  /**
//    * Returns a composed {@code FailableDoublePredicate} like {@link java.util.function.DoublePredicate#and}.
//    *
//    * @param other a predicate that will be logically-ANDed with this predicate.
//    * @return a composed {@code FailableDoublePredicate} like {@link java.util.function.DoublePredicate#and}.
//    * @throws NullPointerException if other is null
//    */
//  def and(other: FailableDoublePredicate[E]): FailableDoublePredicate[E] = {
//    Objects.requireNonNull(other)
//    (t: Double) => test(t) && other.test(t)
//  }
//
//  /**
//    * Returns a predicate that negates this predicate.
//    *
//    * @return a predicate that negates this predicate.
//    */
//  def negate: FailableDoublePredicate[E] = (t: Double) => !test(t)
//
//  /**
//    * Returns a composed {@code FailableDoublePredicate} like {@link java.util.function.DoublePredicate#and}.
//    *
//    * @param other a predicate that will be logically-ORed with this predicate.
//    * @return a composed {@code FailableDoublePredicate} like {@link java.util.function.DoublePredicate#and}.
//    * @throws java.lang.NullPointerException if other is null
//    */
//  def or(other: FailableDoublePredicate[E]): FailableDoublePredicate[E] = {
//    Objects.requireNonNull(other)
//    (t: Double) => test(t) || other.test(t)
//  }
//
//  /**
//    * Tests the predicate.
//    *
//    * @param value the parameter for the predicate to accept.
//    * @return {@code true} if the input argument matches the predicate, {@code false} otherwise.
//    * @throws E Thrown when the consumer fails.
//    */
//  @throws[E]
//  def test(value: Double): Boolean
//}
