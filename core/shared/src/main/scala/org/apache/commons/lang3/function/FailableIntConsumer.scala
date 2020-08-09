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
//object FailableIntConsumer {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes")) val NOP: FailableIntConsumer[_ <: Throwable] = (t: Int) => {
//    def foo(t: Int) = {
//      void(t) /* NOP */
//    }
//
//    foo(t)
//  }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[E <: Throwable]: FailableIntConsumer[E] = NOP.asInstanceOf[FailableIntConsumer[E]]
//}
//
///**
//  * A functional interface like {@link java.util.function.IntConsumer} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface
//trait FailableIntConsumer[E <: Throwable] {
//  // TODO:     * @throws E Thrown when the consumer fails.
//  /**
//    * Accepts the consumer.
//    *
//    * @param value the parameter for the consumable to accept
//    */
//  @throws[E]
//  def accept(value: Int): Unit
//
//  /**
//    * Returns a composed {@code FailableIntConsumer} like {@link java.util.function.IntConsumer#andThen}.
//    *
//    * @param after the operation to perform after this one.
//    * @return a composed {@code FailableLongConsumer} like {@link java.util.function.IntConsumer#andThen}.
//    * @throws java.lang.NullPointerException if {@code after} is null
//    */
//  def andThen(after: FailableIntConsumer[E]): FailableIntConsumer[E] = {
//    Objects.requireNonNull(after)
//    (t: Int) => {
//      def foo(t: Int): Unit = {
//        accept(t)
//        after.accept(t)
//      }
//
//      foo(t)
//    }
//  }
//}
