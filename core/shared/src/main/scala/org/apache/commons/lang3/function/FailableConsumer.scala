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
//object FailableConsumer {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableConsumer[_, _ <: Throwable] = (t: Any) => {
//    def foo(t: Any): Unit = {
//      void(t) /* NOP */
//      ()
//    }
//
//    foo(t)
//  }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam T Consumed type 1.
//    * @tparam E Thrown exception.
//    * @return   The NOP singleton.
//    */
//  def nop[T, E <: Throwable]: FailableConsumer[T, E] = NOP.asInstanceOf[FailableConsumer[T,E]]
//}
//
///**
//  * A functional interface like {@link java.util.function.Consumer} that declares a {@code java.lang.Throwable}.
//  *
//  * @tparam T Consumed type 1.
//  * @tparam E Thrown exception.
//  * @since 3.11
//  */
//@FunctionalInterface
//trait FailableConsumer[T, E <: Throwable] {
//  /**
//    * Accepts the consumer.
//    *
//    * @param object the parameter for the consumable to accept
//    */
//  @throws[E]
//  def accept(`object`: T): Unit
//
//  /**
//    * Returns a composed {@code Consumer} like {@link java.util.function.Consumer#andThen}.
//    *
//    * @param after the operation to perform after this operation
//    * @return a composed {@code Consumer} like {@link java.util.function.Consumer#andThen}.
//    * @throws java.lang.NullPointerException when {@code after} is null
//    */
//  def andThen(after: FailableConsumer[_ >: T, E]): FailableConsumer[T, E] = {
//    Objects.requireNonNull(after)
//    (t: T) => {
//      def foo(t: T) = {
//        accept(t)
//        after.accept(t)
//      }
//
//      foo(t)
//    }
//  }
//}
