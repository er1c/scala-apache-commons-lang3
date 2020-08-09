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

//package org.apache.commons.lang3
//
//import java.io.Serializable
//import java.util.Comparator
//
//@SerialVersionUID(1L)
//object Range { //-----------------------------------------------------------------------
//  @SuppressWarnings(Array(Array("rawtypes", "unchecked"))) final private class ComparableComparator extends Comparator[_] {}
//
//  /**
//    * <p>Obtains a range with the specified minimum and maximum values (both inclusive).</p>
//    *
//    * <p>The range uses the natural ordering of the elements to determine where
//    * values lie in the range.</p>
//    *
//    * <p>The arguments may be passed in the order (min,max) or (max,min).
//    * The getMinimum and getMaximum methods will return the correct values.</p>
//    *
//    * @param <             T> the type of the elements in this range
//    * @param fromInclusive the first value that defines the edge of the range, inclusive
//    * @param toInclusive   the second value that defines the edge of the range, inclusive
//    * @return the range object, not null
//    * @throws java.lang.IllegalArgumentException if either element is null
//    * @throws ClassCastException       if the elements are not {@code Comparable}
//    */
//  def between[T <: Comparable[T]](fromInclusive: T, toInclusive: T): Range[T] = between(fromInclusive, toInclusive, null)
//
//  /**
//    * <p>Obtains a range with the specified minimum and maximum values (both inclusive).</p>
//    *
//    * <p>The range uses the specified {@code Comparator} to determine where
//    * values lie in the range.</p>
//    *
//    * <p>The arguments may be passed in the order (min,max) or (max,min).
//    * The getMinimum and getMaximum methods will return the correct values.</p>
//    *
//    * @param <             T> the type of the elements in this range
//    * @param fromInclusive the first value that defines the edge of the range, inclusive
//    * @param toInclusive   the second value that defines the edge of the range, inclusive
//    * @param comparator    the comparator to be used, null for natural ordering
//    * @return the range object, not null
//    * @throws java.lang.IllegalArgumentException if either element is null
//    * @throws ClassCastException       if using natural ordering and the elements are not {@code Comparable}
//    */
//  def between[T](fromInclusive: T, toInclusive: T, comparator: Comparator[T]) = new Range[T](fromInclusive, toInclusive, comparator)
//
//  /**
//    * <p>Obtains a range using the specified element as both the minimum
//    * and maximum in this range.</p>
//    *
//    * <p>The range uses the natural ordering of the elements to determine where
//    * values lie in the range.</p>
//    *
//    * @param <       T> the type of the elements in this range
//    * @param element the value to use for this range, not null
//    * @return the range object, not null
//    * @throws java.lang.IllegalArgumentException if the element is null
//    * @throws ClassCastException       if the element is not {@code Comparable}
//    */
//  def is[T <: Comparable[T]](element: T): Range[T] = between(element, element, null)
//
//  /**
//    * <p>Obtains a range using the specified element as both the minimum
//    * and maximum in this range.</p>
//    *
//    * <p>The range uses the specified {@code Comparator} to determine where
//    * values lie in the range.</p>
//    *
//    * @param <          T> the type of the elements in this range
//    * @param element    the value to use for this range, must not be {@code null}
//    * @param comparator the comparator to be used, null for natural ordering
//    * @return the range object, not null
//    * @throws java.lang.IllegalArgumentException if the element is null
//    * @throws ClassCastException       if using natural ordering and the elements are not {@code Comparable}
//    */
//  def is[T](element: T, comparator: Comparator[T]): Range[T] = between(element, element, comparator)
//}
//
//
///**
//  * <p>An immutable range of objects from a minimum to maximum point inclusive.</p>
//  *
//  * <p>The objects need to either be implementations of {@code Comparable}
//  * or you need to supply a {@code Comparator}. </p>
//  *
//  * <p>#ThreadSafe# if the objects and comparator are thread-safe</p>
//  *
//  * @tparam T The type of range values.
//  * @since 3.0
//  */
//@SerialVersionUID(1L)
//final class Range[T] @SuppressWarnings(Array("unchecked")) private(val element1: T, val element2: T, val comp: Comparator[T])
//
///**
//  * Creates an instance.
//  *
//  * @param element1 the first element, not null
//  * @param element2 the second element, not null
//  * @param comp     the comparator to be used, null for natural ordering
//  */
//  extends Serializable {
//  if (element1 == null || element2 == null) throw new IllegalArgumentException("Elements in a range must not be null: element1=" + element1 + ", element2=" + element2)
//  if (comp == null) this.comparator = Range.ComparableComparator.INSTANCE
//  else this.comparator = comp
//  if (this.comparator.compare(element1, element2) < 1) {
//    this.minimum = element1
//    this.maximum = element2
//  }
//  else {
//    this.minimum = element2
//    this.maximum = element1
//  }
//  /**
//    * The ordering scheme used in this range.
//    */
//  final private var comparator = null
//  /**
//    * Cached output hashCode (class is immutable).
//    */
//  private var hashCode = 0
//  /**
//    * The maximum value in this range (inclusive).
//    */
//  final private var maximum = null
//  /**
//    * The minimum value in this range (inclusive).
//    */
//  final private var minimum = null
//  /**
//    * Cached output toString (class is immutable).
//    */
//  private var toString = null
//
//  /**
//    * <p>Checks whether the specified element occurs within this range.</p>
//    *
//    * @param element the element to check for, null returns false
//    * @return true if the specified element occurs within this range
//    */
//  def contains(element: T): Boolean = {
//    if (element == null) return false
//    comparator.compare(element, minimum) > -1 && comparator.compare(element, maximum) < 1
//  }
//
//  /**
//    * <p>Checks whether this range contains all the elements of the specified range.</p>
//    *
//    * <p>This method may fail if the ranges have two different comparators or element types.</p>
//    *
//    * @param otherRange the range to check, null returns false
//    * @return true if this range contains the specified range
//    * @throws RuntimeException if ranges cannot be compared
//    */
//  def containsRange(otherRange: Range[T]): Boolean = {
//    if (otherRange == null) return false
//    contains(otherRange.minimum) && contains(otherRange.maximum)
//  }
//
//  /**
//    * <p>Checks where the specified element occurs relative to this range.</p>
//    *
//    * <p>The API is reminiscent of the Comparable interface returning {@code -1} if
//    * the element is before the range, {@code 0} if contained within the range and
//    * {@code 1} if the element is after the range. </p>
//    *
//    * @param element the element to check for, not null
//    * @return -1, 0 or +1 depending on the element's location relative to the range
//    */
//  def elementCompareTo(element: T): Int = { // Comparable API says throw NPE on null
//    Validate.notNull(element, "Element is null")
//    if (isAfter(element)) -1
//    else if (isBefore(element)) 1
//    else 0
//  }
//
//  /**
//    * <p>Compares this range to another object to test if they are equal.</p>.
//    *
//    * <p>To be equal, the minimum and maximum values must be equal, which
//    * ignores any differences in the comparator.</p>
//    *
//    * @param obj the reference object with which to compare
//    * @return true if this object is equal
//    */
//  override def equals(obj: Any): Boolean = if (obj eq this) true
//  else if (obj == null || (obj.getClass ne getClass)) false
//  else {
//    @SuppressWarnings(Array("unchecked")) // OK because we checked the class above val range: Range[T] = obj.asInstanceOf[Range[T]]
//    minimum == range.minimum && maximum == range.maximum
//  }
//
//  /**
//    * <p>Gets the comparator being used to determine if objects are within the range.</p>
//    *
//    * <p>Natural ordering uses an internal comparator implementation, thus this
//    * method never returns null. See {@link #isNaturalOrdering ( )}.</p>
//    *
//    * @return the comparator being used, not null
//    */
//  def getComparator: Comparator[T] = comparator
//
//  /**
//    * <p>Gets the maximum value in this range.</p>
//    *
//    * @return the maximum value in this range, not null
//    */
//  def getMaximum: T = maximum
//
//  /**
//    * <p>Gets the minimum value in this range.</p>
//    *
//    * @return the minimum value in this range, not null
//    */
//  def getMinimum: T = minimum
//
//  /**
//    * <p>Gets a suitable hash code for the range.</p>
//    *
//    * @return a hash code value for this object
//    */
//  override def hashCode: Int = {
//    var result = hashCode
//    if (hashCode == 0) {
//      result = 17
//      result = 37 * result + getClass.hashCode
//      result = 37 * result + minimum.hashCode
//      result = 37 * result + maximum.hashCode
//      hashCode = result
//    }
//    result
//  }
//
//  /**
//    * Calculate the intersection of {@code this} and an overlapping Range.
//    *
//    * @param other overlapping Range
//    * @return range representing the intersection of {@code this} and {@code other} ({@code this} if equal)
//    * @throws java.lang.IllegalArgumentException if {@code other} does not overlap {@code this}
//    * @since 3.0.1
//    */
//  def intersectionWith(other: Range[T]): Range[T] = {
//    if (!this.isOverlappedBy(other)) throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", other))
//    if (this == other) return this
//    val min = if (getComparator.compare(minimum, other.minimum) < 0) other.minimum
//    else minimum
//    val max = if (getComparator.compare(maximum, other.maximum) < 0) maximum
//    else other.maximum
//    Range.between(min, max, getComparator)
//  }
//
//  /**
//    * <p>Checks whether this range is after the specified element.</p>
//    *
//    * @param element the element to check for, null returns false
//    * @return true if this range is entirely after the specified element
//    */
//  def isAfter(element: T): Boolean = {
//    if (element == null) return false
//    comparator.compare(element, minimum) < 0
//  }
//
//  /**
//    * <p>Checks whether this range is completely after the specified range.</p>
//    *
//    * <p>This method may fail if the ranges have two different comparators or element types.</p>
//    *
//    * @param otherRange the range to check, null returns false
//    * @return true if this range is completely after the specified range
//    * @throws RuntimeException if ranges cannot be compared
//    */
//  def isAfterRange(otherRange: Range[T]): Boolean = {
//    if (otherRange == null) return false
//    isAfter(otherRange.maximum)
//  }
//
//  /**
//    * <p>Checks whether this range is before the specified element.</p>
//    *
//    * @param element the element to check for, null returns false
//    * @return true if this range is entirely before the specified element
//    */
//  def isBefore(element: T): Boolean = {
//    if (element == null) return false
//    comparator.compare(element, maximum) > 0
//  }
//
//  /**
//    * <p>Checks whether this range is completely before the specified range.</p>
//    *
//    * <p>This method may fail if the ranges have two different comparators or element types.</p>
//    *
//    * @param otherRange the range to check, null returns false
//    * @return true if this range is completely before the specified range
//    * @throws RuntimeException if ranges cannot be compared
//    */
//  def isBeforeRange(otherRange: Range[T]): Boolean = {
//    if (otherRange == null) return false
//    isBefore(otherRange.minimum)
//  }
//
//  /**
//    * <p>Checks whether this range ends with the specified element.</p>
//    *
//    * @param element the element to check for, null returns false
//    * @return true if the specified element occurs within this range
//    */
//  def isEndedBy(element: T): Boolean = {
//    if (element == null) return false
//    comparator.compare(element, maximum) == 0
//  }
//
//  /**
//    * <p>Whether or not the Range is using the natural ordering of the elements.</p>
//    *
//    * <p>Natural ordering uses an internal comparator implementation, thus this
//    * method is the only way to check if a null comparator was specified.</p>
//    *
//    * @return true if using natural ordering
//    */
//  def isNaturalOrdering: Boolean = comparator eq Range.ComparableComparator.INSTANCE
//
//  /**
//    * <p>Checks whether this range is overlapped by the specified range.</p>
//    *
//    * <p>Two ranges overlap if there is at least one element in common.</p>
//    *
//    * <p>This method may fail if the ranges have two different comparators or element types.</p>
//    *
//    * @param otherRange the range to test, null returns false
//    * @return true if the specified range overlaps with this
//    *         range; otherwise, {@code false}
//    * @throws RuntimeException if ranges cannot be compared
//    */
//  def isOverlappedBy(otherRange: Range[T]): Boolean = {
//    if (otherRange == null) return false
//    otherRange.contains(minimum) || otherRange.contains(maximum) || contains(otherRange.minimum)
//  }
//
//  /**
//    * <p>Checks whether this range starts with the specified element.</p>
//    *
//    * @param element the element to check for, null returns false
//    * @return true if the specified element occurs within this range
//    */
//  def isStartedBy(element: T): Boolean = {
//    if (element == null) return false
//    comparator.compare(element, minimum) == 0
//  }
//
//  /**
//    * <p>
//    * Fits the given element into this range by returning the given element or, if out of bounds, the range minimum if
//    * below, or the range maximum if above.
//    * </p>
//    * <pre>
//    * Range&lt;Integer&gt; range = Range.between(16, 64);
//    * range.fit(-9) --&gt;  16
//    * range.fit(0)  --&gt;  16
//    * range.fit(15) --&gt;  16
//    * range.fit(16) --&gt;  16
//    * range.fit(17) --&gt;  17
//    * ...
//    * range.fit(63) --&gt;  63
//    * range.fit(64) --&gt;  64
//    * range.fit(99) --&gt;  64
//    * </pre>
//    *
//    * @param element the element to check for, not null
//    * @return the minimum, the element, or the maximum depending on the element's location relative to the range
//    * @since 3.10
//    */
//  def fit(element: T): T = {
//    Validate.notNull(element, "element")
//    if (isAfter(element)) minimum
//    else if (isBefore(element)) maximum
//    else element
//  }
//
//  /**
//    * <p>Gets the range as a {@code String}.</p>
//    *
//    * <p>The format of the String is '[<i>min</i>..<i>max</i>]'.</p>
//    *
//    * @return the {@code String} representation of this range
//    */
//  override def toString: String = {
//    if (toString == null) toString = "[" + minimum + ".." + maximum + "]"
//    toString
//  }
//
//  /**
//    * <p>Formats the receiver using the given format.</p>
//    *
//    * <p>This uses {@link java.util.Formattable} to perform the formatting. Three variables may
//    * be used to embed the minimum, maximum and comparator.
//    * Use {@code %1$s} for the minimum element, {@code %2$s} for the maximum element
//    * and {@code %3$s} for the comparator.
//    * The default format used by {@code toString()} is {@code [%1$s..%2$s]}.</p>
//    *
//    * @param format the format string, optionally containing {@code %1$s}, {@code %2$s} and  {@code %3$s}, not null
//    * @return the formatted string, not null
//    */
//  def toString(format: String): String = String.format(format, minimum, maximum, comparator)
//}
