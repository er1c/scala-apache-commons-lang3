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
//import java.util
//import java.util.NoSuchElementException
//
///**
//  * <p>A contiguous range of characters, optionally negated.</p>
//  *
//  * <p>Instances are immutable.</p>
//  *
//  * <p>#ThreadSafe#</p>
//  *
//  * @since 1.0
//  */
//// TODO: This is no longer public and will be removed later as CharSet is moved
//// to depend on Range.
//@SerialVersionUID(8270183163158333422L)
//object CharRange {
//  /**
//    * <p>Constructs a {@code CharRange} over a single character.</p>
//    *
//    * @param ch only character in this range
//    * @return the new CharRange object
//    * @since 2.5
//    */
//  def is(ch: Char) = new CharRange(ch, ch, false)
//
//  /**
//    * <p>Constructs a negated {@code CharRange} over a single character.</p>
//    *
//    * <p>A negated range includes everything except that defined by the
//    * single character.</p>
//    *
//    * @param ch only character in this range
//    * @return the new CharRange object
//    * @since 2.5
//    */
//  def isNot(ch: Char) = new CharRange(ch, ch, true)
//
//  /**
//    * <p>Constructs a {@code CharRange} over a set of characters.</p>
//    *
//    * <p>If start and end are in the wrong order, they are reversed.
//    * Thus {@code a-e} is the same as {@code e-a}.</p>
//    *
//    * @param start first character, inclusive, in this range
//    * @param end   last character, inclusive, in this range
//    * @return the new CharRange object
//    * @since 2.5
//    */
//  def isIn(start: Char, `end`: Char) = new CharRange(start, `end`, false)
//
//  /**
//    * <p>Constructs a negated {@code CharRange} over a set of characters.</p>
//    *
//    * <p>A negated range includes everything except that defined by the
//    * start and end characters.</p>
//    *
//    * <p>If start and end are in the wrong order, they are reversed.
//    * Thus {@code a-e} is the same as {@code e-a}.</p>
//    *
//    * @param start first character, inclusive, in this range
//    * @param end   last character, inclusive, in this range
//    * @return the new CharRange object
//    * @since 2.5
//    */
//  def isNotIn(start: Char, `end`: Char) = new CharRange(start, `end`, true)
//
//  /**
//    * Character {@link Iterator}.
//    * <p>#NotThreadSafe#</p>
//    */
//  private class CharacterIterator private(val range: CharRange)
//
//  /**
//    * Constructs a new iterator for the character range.
//    *
//    * @param r The character range
//    */
//    extends util.Iterator[Character] {
//    hasNext = true
//    if (range.negated) if (range.start == 0) if (range.`end` == Character.MAX_VALUE) { // This range is an empty set
//      hasNext = false
//    }
//    else current = (range.`end` + 1).toChar
//    else current = 0
//    else current = range.start
//    /** The current character */
//    private var current = 0
//    private var hasNext = false
//
//    /**
//      * Prepares the next character in the range.
//      */
//    private def prepareNext(): Unit = {
//      if (range.negated) if (current == Character.MAX_VALUE) hasNext = false
//      else if (current + 1 == range.start) if (range.`end` == Character.MAX_VALUE) hasNext = false
//      else current = (range.`end` + 1).toChar
//      else current = (current + 1).toChar
//      else if (current < range.`end`) current = (current + 1).toChar
//      else hasNext = false
//    }
//
//    /**
//      * Has the iterator not reached the end character yet?
//      *
//      * @return {@code true} if the iterator has yet to reach the character date
//      */
//    override def hasNext: Boolean = hasNext
//
//    /**
//      * Returns the next character in the iteration
//      *
//      * @return {@code Character} for the next character
//      */
//    override def next: Character = {
//      if (!hasNext) throw new NoSuchElementException
//      val cur = current
//      prepareNext()
//      Character.valueOf(cur)
//    }
//
//    /**
//      * Always throws UnsupportedOperationException.
//      *
//      * @throws UnsupportedOperationException Always thrown.
//      * @see java.util.Iterator#remove()
//      */
//    override def remove(): Unit = {
//      throw new UnsupportedOperationException
//    }
//  }
//
//}
//
//@SerialVersionUID(8270183163158333422L)
//final class CharRange private(/** The first character, inclusive, in the range. */
//  val start: Char,
//
//  /** The last character, inclusive, in the range. */
//  val `end`: Char,
//
//  /** True if the range is everything except the characters specified. */
//  val negated: Boolean)
//
///**
//  * <p>Constructs a {@code CharRange} over a set of characters,
//  * optionally negating the range.</p>
//  *
//  * <p>A negated range includes everything except that defined by the
//  * start and end characters.</p>
//  *
//  * <p>If start and end are in the wrong order, they are reversed.
//  * Thus {@code a-e} is the same as {@code e-a}.</p>
//  *
//  * @param start   first character, inclusive, in this range
//  * @param end     last character, inclusive, in this range
//  * @param negated true to express everything except the range
//  */
//  extends Iterable[Character] with Serializable {
//  if (start > `end`) {
//    val temp = start
//    start = `end`
//    `end` = temp
//  }
//  /** Cached toString. */
//  private var iToString = null
//
//  /**
//    * <p>Gets the start character for this character range.</p>
//    *
//    * @return the start char (inclusive)
//    */
//  def getStart: Char = this.start
//
//  /**
//    * <p>Gets the end character for this character range.</p>
//    *
//    * @return the end char (inclusive)
//    */
//  def getEnd: Char = this.`end`
//
//  /**
//    * <p>Is this {@code CharRange} negated.</p>
//    *
//    * <p>A negated range includes everything except that defined by the
//    * start and end characters.</p>
//    *
//    * @return {@code true} if negated
//    */
//  def isNegated: Boolean = negated
//
//  /**
//    * <p>Is the character specified contained in this range.</p>
//    *
//    * @param ch the character to check
//    * @return {@code true} if this range contains the input character
//    */
//  def contains(ch: Char): Boolean = (ch >= start && ch <= `end`) != negated
//
//  /**
//    * <p>Are all the characters of the passed in range contained in
//    * this range.</p>
//    *
//    * @param range the range to check against
//    * @return {@code true} if this range entirely contains the input range
//    * @throws java.lang.IllegalArgumentException if {@code null} input
//    */
//  def contains(range: CharRange): Boolean = {
//    Validate.notNull(range, "The Range must not be null")
//    if (negated) {
//      if (range.negated) return start >= range.start && `end` <= range.`end`
//      return range.`end` < start || range.start > `end`
//    }
//    if (range.negated) return start == 0 && `end` == Character.MAX_VALUE
//    start <= range.start && `end` >= range.`end`
//  }
//
//  /**
//    * <p>Compares two CharRange objects, returning true if they represent
//    * exactly the same range of characters defined in the same way.</p>
//    *
//    * @param obj the object to compare to
//    * @return true if equal
//    */
//  override def equals(obj: Any): Boolean = {
//    if (obj eq this) return true
//    if (!obj.isInstanceOf[CharRange]) return false
//    val other = obj.asInstanceOf[CharRange]
//    start == other.start && `end` == other.`end` && negated == other.negated
//  }
//
//  /**
//    * <p>Gets a hashCode compatible with the equals method.</p>
//    *
//    * @return a suitable hashCode
//    */
//  override def hashCode: Int = 83 + start + 7 * `end` + (if (negated) 1
//  else 0)
//
//  /**
//    * <p>Gets a string representation of the character range.</p>
//    *
//    * @return string representation of this range
//    */
//  override def toString: String = {
//    if (iToString == null) {
//      val buf = new StringBuilder(4)
//      if (isNegated) buf.append('^')
//      buf.append(start)
//      if (start != `end`) {
//        buf.append('-')
//        buf.append(`end`)
//      }
//      iToString = buf.toString
//    }
//    iToString
//  }
//
//  /**
//    * <p>Returns an iterator which can be used to walk through the characters described by this range.</p>
//    *
//    * <p>#NotThreadSafe# the iterator is not thread-safe</p>
//    *
//    * @return an iterator to the chars represented by this range
//    * @since 2.5
//    */
//  override def iterator = new CharRange.CharacterIterator(this)
//}
