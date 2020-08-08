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
object MutableTriple {
  /**
    * The empty array singleton.
    * <p>
    * Consider using {@link #emptyArray ( )} to avoid generics warnings.
    * </p>
    *
    * @since 3.10.
    */
  val EMPTY_ARRAY = new Array[MutableTriple[_, _, _]](0)

  /**
    * Returns the empty array singleton that can be assigned without compiler warning.
    *
    * @tparam L the left element type
    * @tparam M the middle element type
    * @tparam R the right element type
    * @return   the empty array singleton that can be assigned without compiler warning.
    * @since 3.10.
    */
  @SuppressWarnings(Array("unchecked")) def emptyArray[L, M, R]: Array[MutableTriple[L, M, R]] = EMPTY_ARRAY.asInstanceOf[Array[MutableTriple[L, M, R]]]

  /**
    * <p>Obtains a mutable triple of three objects inferring the generic types.</p>
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
  def of[L, M, R](left: L, middle: M, right: R) = new MutableTriple[L, M, R](left, middle, right)
}


/**
  * <p>A mutable triple consisting of three {@code Object} elements.</p>
  *
  * <p>Not #ThreadSafe#</p>
  *
  * @tparam L the left element type
  * @tparam M the middle element type
  * @tparam R the right element type
  * @param left   the left value, may be null
  * @param middle the middle value, may be null
  * @param right  the right value, may be null
  * @since 3.2
  */
@SerialVersionUID(1L)
class MutableTriple[L, M, R](var left: L, var middle: M, var right: R) extends Triple[L, M, R] {
  /**
    * Create a new triple instance of three nulls.
    */
  def this() = {
    this(null.asInstanceOf[L], null.asInstanceOf[M], null.asInstanceOf[R])
  }

  /**
    * {@inheritDoc }
    */
  override def getLeft: L = left

  override def getMiddle: M = middle

  override def getRight: R = right

  /**
    * Sets the left element of the triple.
    *
    * @param left the new value of the left element, may be null
    */
  def setLeft(left: L): Unit = {
    this.left = left
  }

  /**
    * Sets the middle element of the triple.
    *
    * @param middle the new value of the middle element, may be null
    */
  def setMiddle(middle: M): Unit = {
    this.middle = middle
  }

  /**
    * Sets the right element of the triple.
    *
    * @param right the new value of the right element, may be null
    */
  def setRight(right: R): Unit = {
    this.right = right
  }
}
