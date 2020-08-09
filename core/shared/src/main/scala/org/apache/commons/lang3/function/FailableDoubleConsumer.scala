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
//import java.util.function.DoubleConsumer
//
///**
//  * A functional interface like {@link DoubleConsumer} that declares a {@code Throwable}.
//  *
//  * @param < E> Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface object FailableDoubleConsumer {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes")) val NOP: FailableDoubleConsumer[_ <: Throwable] = (t: Double) => {
//    def foo(t: Double) = {
//      /* NOP */
//    }
//
//    foo(t)
//  }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @param < E> Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[E <: Throwable]: FailableDoubleConsumer[E] = NOP
//}
//
//@FunctionalInterface trait FailableDoubleConsumer[E <: Throwable] {
//  /**
//    * Accepts the consumer.
//    *
//    * @param value the parameter for the consumable to accept
//    * @throws E Thrown when the consumer fails.
//    */
//  @throws[E]
//  def accept(value: Double): Unit
//
//  /**
//    * Returns a composed {@code FailableDoubleConsumer} like {@link DoubleConsumer# andThen ( DoubleConsumer )}.
//    *
//    * @param after the operation to perform after this one.
//    * @return a composed {@code FailableDoubleConsumer} like {@link DoubleConsumer# andThen ( DoubleConsumer )}.
//    * @throws NullPointerException when {@code after} is null.
//    */
//  def andThen(after: FailableDoubleConsumer[E]): FailableDoubleConsumer[E] = {
//    Objects.requireNonNull(after)
//    (t: Double) => {
//      def foo(t: Double) = {
//        accept(t)
//        after.accept(t)
//      }
//
//      foo(t)
//    }
//  }
//}
