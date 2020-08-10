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
//
///**
//  * A functional interface like {@link java.util.function.BiConsumer} that declares a {@code java.lang.Throwable}.
//  *
//  * @since 3.11
//  */
//@FunctionalInterface object FailableBiConsumer {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableBiConsumer[_, _, _ <: Throwable] = (t: Any, u: Any) => {
//    def foo(t: Any, u: Any): Unit = {
//      /* NOP */
//    }
//
//    foo(t, u)
//  }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @param < T> Consumed type 1.
//    * @param < U> Consumed type 2.
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[T, U, E <: Throwable]: FailableBiConsumer[T, U, E] = NOP.asInstanceOf[FailableBiConsumer[T, U, E] ]
//}
//
//
///**
//  * Accepts the consumer.
//  *
//  * @tparam T Consumed type 1.
//  * @tparam U Consumed type 2.
//  * @tparam E Thrown exception.
//  * @throws E
//  */
//@FunctionalInterface trait FailableBiConsumer[T, U, E <: Throwable] {
//  /**
//    * Accepts the consumer.
//    *
//    * @param t  the first parameter for the consumable to accept
//    * @param u  the second parameter for the consumable to accept
//    * @throws E Thrown when the consumer fails.
//    */
//  def accept(t: T, u: U): Unit
//
//  /**
//    * Returns a composed {@code FailableBiConsumer} like {@link java.util.function.BiConsumer# andThen}.
//    *
//    * @param after the operation to perform after this one.
//    * @return a composed {@code FailableBiConsumer} like {@link java.util.function.BiConsumer#andThen}.
//    * @throws NullPointerException when {@code after} is null.
//    */
//  def andThen(after: FailableBiConsumer[_ >: T, _ >: U, E]): FailableBiConsumer[T, U, E] = {
//    Objects.requireNonNull(after)
//    (t: T, u: U) => {
//      def foo(t: T, u: U): Unit = {
//        accept(t, u)
//        after.accept(t, u)
//      }
//
//      foo(t, u)
//    }
//  }
//}
