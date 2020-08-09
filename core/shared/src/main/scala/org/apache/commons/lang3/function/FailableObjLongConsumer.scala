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

package org.apache.commons.lang3.function

//object FailableObjLongConsumer {
//  /** NOP singleton */
//  @SuppressWarnings(Array("rawtypes"))
//  val NOP: FailableObjLongConsumer[_, _ <: Throwable] = (t: Any, u: Long) => {
//    def foo(t: Any, u: Long): Unit = {
//      void(t); void(u); ()/* NOP */
//    }
//
//    foo(t, u)
//  }
//
//  /**
//    * Returns The NOP singleton.
//    *
//    * @tparam T the type of the object argument to the operation.
//    * @tparam E Thrown exception.
//    * @return The NOP singleton.
//    */
//  def nop[T, E <: Throwable]: FailableObjLongConsumer[T, E] = NOP.asInstanceOf[FailableObjLongConsumer[T, E]]
//}
//
/**
  * A functional interface like {@link java.util.function.ObjLongConsumer} that declares a {@code java.lang.Throwable}.
  *
  * @tparam T the type of the object argument to the operation.
  * @tparam E Thrown exception.
  * @since 3.11
  */
@FunctionalInterface
trait FailableObjLongConsumer[T, E <: Throwable] {
  // TODO:     * @throws E Thrown when the consumer fails.
  /**
    * Accepts the consumer.
    *
    * @param object the object parameter for the consumable to accept.
    * @param value  the long parameter for the consumable to accept.
    */
  @throws[E]
  def accept(`object`: T, value: Long): Unit
}
