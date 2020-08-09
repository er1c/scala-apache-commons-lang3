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

import java.io.Serializable
import java.util.Objects
import org.apache.commons.lang3.builder.CompareToBuilder

@SerialVersionUID(1L)
object Triple {

  @SerialVersionUID(1L)
  final private class TripleAdapter[L, M, R] extends Triple[L, M, R] {
    override def getLeft: L = null.asInstanceOf[L]

    override def getMiddle: M = null.asInstanceOf[M]

    override def getRight: R = null.asInstanceOf[R]
  }

  /**
    * An empty array.
    * <p>
    * Consider using {@link #emptyArray} to avoid generics warnings.
    * </p>
    *
    * @since 3.10.
    */
  val EMPTY_ARRAY: Array[Triple[_, _, _]] = Array(new TripleAdapter[Any, Any, Any])

  /**
    * Returns the empty array singleton that can be assigned without compiler warning.
    *
    * @tparam L the left element type
    * @tparam M the middle element type
    * @tparam R the right element type
    * @return the empty array singleton that can be assigned without compiler warning.
    * @since 3.10.
    */
  @SuppressWarnings(Array("unchecked")) def emptyArray[L, M, R]: Array[Triple[L, M, R]] =
    EMPTY_ARRAY.asInstanceOf[Array[Triple[L, M, R]]]

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
  def of[L, M, R](left: L, middle: M, right: R) = new ImmutableTriple[L, M, R](left, middle, right)
}

/**
  * <p>A triple consisting of three elements.</p>
  *
  * <p>This class is an abstract implementation defining the basic API.
  * It refers to the elements as 'left', 'middle' and 'right'.</p>
  *
  * <p>Subclass implementations may be mutable or immutable.
  * However, there is no restriction on the type of the stored objects that may be stored.
  * If mutable objects are stored in the triple, then the triple itself effectively becomes mutable.</p>
  *
  * @tparam L the left element type
  * @tparam M the middle element type
  * @tparam R the right element type
  * @since 3.2
  */
@SerialVersionUID(1L)
abstract class Triple[L, M, R] extends Comparable[Triple[L, M, R]] with Serializable {
  /**
    * <p>Compares the triple based on the left element, followed by the middle element,
    * finally the right element.
    * The types must be {@code Comparable}.</p>
    *
    * @param other the other triple, not null
    * @return negative if this is less, zero if equal, positive if greater
    */
  override def compareTo(other: Triple[L, M, R]): Int =
    new CompareToBuilder()
      .append(getLeft, other.getLeft)
      .append(getMiddle, other.getMiddle)
      .append(getRight, other.getRight)
      .toComparison

  /**
    * <p>Compares this triple to another based on the three elements.</p>
    *
    * @param obj the object to compare to, null returns false
    * @return true if the elements of the triple are equal
    */
  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[AnyRef] && (this eq obj.asInstanceOf[AnyRef])) return true

    if (obj.isInstanceOf[Triple[_, _, _]]) {
      val other = obj.asInstanceOf[Triple[_, _, _]]
      return Objects.equals(getLeft, other.getLeft) && Objects.equals(getMiddle, other.getMiddle) && Objects
        .equals(getRight, other.getRight)
    }

    false
  }

  /**
    * <p>Gets the left element from this triple.</p>
    *
    * @return the left element, may be null
    */
  def getLeft: L

  /**
    * <p>Gets the middle element from this triple.</p>
    *
    * @return the middle element, may be null
    */
  def getMiddle: M

  /**
    * <p>Gets the right element from this triple.</p>
    *
    * @return the right element, may be null
    */
  def getRight: R

  /**
    * <p>Returns a suitable hash code.</p>
    *
    * @return the hash code
    */
  override def hashCode: Int = Objects.hashCode(getLeft) ^ Objects.hashCode(getMiddle) ^ Objects.hashCode(getRight)

  /**
    * <p>Returns a String representation of this triple using the format {@code (left,middle,right)}.</p>
    *
    * @return a string describing this object, not null
    */
  override def toString: String = "(" + getLeft + "," + getMiddle + "," + getRight + ")"

  /**
    * <p>Formats the receiver using the given format.</p>
    *
    * <p>This uses {@link java.util.Formattable} to perform the formatting. Three variables may
    * be used to embed the left and right elements. Use {@code %1s} for the left
    * element, {@code %2s} for the middle and {@code %3s} for the right element.
    * The default format used by {@code toString()} is {@code (%1s,%2s,%3s)}.</p>
    *
    * @param format the format string, optionally containing {@code %1s}, {@code %2s} and {@code %3s}, not null
    * @return the formatted string, not null
    */
  def toString(format: String): String = format.format(getLeft, getMiddle, getRight)
}
