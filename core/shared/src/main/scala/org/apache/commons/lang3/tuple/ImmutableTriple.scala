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

package org.apache.commons.lang3.tuple

@SerialVersionUID(1L)
object ImmutableTriple {
  /**
    * An empty array.
    * <p>
    * Consider using {@link #emptyArray ( )} to avoid generics warnings.
    * </p>
    *
    * @since 3.10.
    */
  val EMPTY_ARRAY = new Array[ImmutableTriple[_, _, _]](0)
  /**
    * An immutable triple of nulls.
    */
  // This is not defined with generics to avoid warnings in call sites.
  @SuppressWarnings(Array("rawtypes")) private val NULL: ImmutableTriple[_, _, _] = of(null, null, null)

  /**
    * Returns the empty array singleton that can be assigned without compiler warning.
    *
    * @tparam L the left element type
    * @tparam M the middle element type
    * @tparam R the right element type
    * @return   the empty array singleton that can be assigned without compiler warning.
    * @since 3.10.
    */
  @SuppressWarnings(Array("unchecked")) def emptyArray[L, M, R]: Array[ImmutableTriple[L, M, R]] =
    EMPTY_ARRAY.asInstanceOf[Array[ImmutableTriple[L, M, R]]]

  /**
    * Returns an immutable triple of nulls.
    *
    * @tparam L the left element of this triple. Value is {@code null}.
    * @tparam M the middle element of this triple. Value is {@code null}.
    * @tparam R the right element of this triple. Value is {@code null}.
    * @return   an immutable triple of nulls.
    * @since 3.6
    */
  def nullTriple[L, M, R]: ImmutableTriple[L, M, R] =
    NULL.asInstanceOf[ImmutableTriple[L, M, R]]

  /**
    * <p>Obtains an immutable triple of three objects inferring the generic types.</p>
    *
    * <p>This factory allows the triple to be created using inference to
    * obtain the generic types.</p>
    *
    * @tparam L     the left element type
    * @tparam M     the middle element type
    * @tparam R     the right element type
    * @param left   the left element, may be null
    * @param middle the middle element, may be null
    * @param right  the right element, may be null
    * @return a triple formed from the three parameters, not null
    */
  def of[L, M, R](left: L, middle: M, right: R) =
    new ImmutableTriple[L, M, R](left, middle, right)
}

/**
  * <p>An immutable triple consisting of three {@code Object} elements.</p>
  *
  * <p>Although the implementation is immutable, there is no restriction on the objects
  * that may be stored. If mutable objects are stored in the triple, then the triple
  * itself effectively becomes mutable. The class is also {@code final}, so a subclass
  * can not add undesirable behavior.</p>
  *
  * <p>#ThreadSafe# if all three objects are thread-safe</p>
  *
  * @tparam L     the left element type
  * @tparam M     the middle element type
  * @tparam R     the right element type
  * @param left   Left object
  * @param middle Middle object
  * @param right  Right object
  * @since 3.2
  */
@SerialVersionUID(1L)
final class ImmutableTriple[L, M, R](val left: L, val middle: M, val right: R) extends Triple[L, M, R] {
  /**
    * {@inheritDoc }
    */
  override def getLeft: L = left

  override def getMiddle: M = middle

  override def getRight: R = right
}
