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

import java.util

@SerialVersionUID(4954918890077093841L)
object MutablePair {
  /**
    * An empty array.
    * <p>
    * Consider using {@link #emptyArray ( )} to avoid generics warnings.
    * </p>
    *
    * @since 3.10.
    */
  val EMPTY_ARRAY = new Array[MutablePair[_, _]](0)

  /**
    * Returns the empty array singleton that can be assigned without compiler warning.
    *
    * @tparam L the left element type
    * @tparam R the right element type
    * @return the empty array singleton that can be assigned without compiler warning.
    * @since 3.10.
    */
  @SuppressWarnings(Array("unchecked")) def emptyArray[L, R]: Array[MutablePair[L, R]] =
    EMPTY_ARRAY.asInstanceOf[Array[MutablePair[L, R]]]

  /**
    * <p>Creates a mutable pair of two objects inferring the generic types.</p>
    *
    * <p>This factory allows the pair to be created using inference to
    * obtain the generic types.</p>
    *
    * @tparam L    the left element type
    * @tparam R    the right element type
    * @param left  the left element, may be null
    * @param right the right element, may be null
    * @return a pair formed from the two parameters, not null
    */
  def of[L, R](left: L, right: R) = new MutablePair[L, R](left, right)

  /**
    * <p>Creates a mutable pair from an existing pair.</p>
    *
    * <p>This factory allows the pair to be created using inference to
    * obtain the generic types.</p>
    *
    * @tparam L the left element type
    * @tparam R the right element type
    * @param pair the existing pair.
    * @return a pair formed from the two parameters, not null
    */
  def of[L, R](pair: util.Map.Entry[L, R]): MutablePair[L, R] = {
    var left: L = null.asInstanceOf[L]
    var right: R = null.asInstanceOf[R]

    if (pair != null) {
      left = pair.getKey
      right = pair.getValue
    }

    new MutablePair[L, R](left, right)
  }
}

/**
  * <p>A mutable pair consisting of two {@code Object} elements.</p>
  *
  * <p>Not #ThreadSafe#</p>
  *
  * @tparam L the left element type
  * @tparam R the right element type
  * @param left  the left value, may be null
  * @param right the right value, may be null
  * @since 3.0
  */
@SerialVersionUID(4954918890077093841L)
class MutablePair[L, R](var left: L, var right: R) extends Pair[L, R] {
  /**
    * Create a new pair instance of two nulls.
    */
  def this() {
    this(null.asInstanceOf[L], null.asInstanceOf[R])
  }

  /**
    * {@inheritDoc }
    */
  override def getLeft: L = this.left

  override def getRight: R = this.right

  /**
    * Sets the left element of the pair.
    *
    * @param left the new value of the left element, may be null
    */
  def setLeft(left: L): Unit = {
    this.left = left
  }

  /**
    * Sets the right element of the pair.
    *
    * @param right the new value of the right element, may be null
    */
  def setRight(right: R): Unit = {
    this.right = right
  }

  /**
    * Sets the {@code Map.Entry} value.
    * This sets the right element of the pair.
    *
    * @param value the right value to set, not null
    * @return the old value for the right element
    */
  override def setValue(value: R): R = {
    val result = getRight
    setRight(value)
    result
  }
}
