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
//import java.util.Collections
//import java.util
//
///**
//  * <p>A set of characters.</p>
//  *
//  * <p>Instances are immutable, but instances of subclasses may not be.</p>
//  *
//  * <p>#ThreadSafe#</p>
//  *
//  * @since 1.0
//  */
//@SerialVersionUID(5947847346149275958L)
//object CharSet {
//  /**
//    * A CharSet defining no characters.
//    *
//    * @since 2.0
//    */
//  val EMPTY = new CharSet(null.asInstanceOf[String])
//  /**
//    * A CharSet defining ASCII alphabetic characters "a-zA-Z".
//    *
//    * @since 2.0
//    */
//  val ASCII_ALPHA = new CharSet("a-zA-Z")
//  /**
//    * A CharSet defining ASCII alphabetic characters "a-z".
//    *
//    * @since 2.0
//    */
//  val ASCII_ALPHA_LOWER = new CharSet("a-z")
//  /**
//    * A CharSet defining ASCII alphabetic characters "A-Z".
//    *
//    * @since 2.0
//    */
//  val ASCII_ALPHA_UPPER = new CharSet("A-Z")
//  /**
//    * A CharSet defining ASCII alphabetic characters "0-9".
//    *
//    * @since 2.0
//    */
//  val ASCII_NUMERIC = new CharSet("0-9")
//  /**
//    * A Map of the common cases used in the factory.
//    * Subclasses can add more common patterns if desired
//    *
//    * @since 2.0
//    */
//  protected val COMMON: util.Map[String, CharSet] = Collections.synchronizedMap(new util.HashMap[String, CharSet])
//
//  /**
//    * <p>Factory method to create a new CharSet using a special syntax.</p>
//    *
//    * <ul>
//    * <li>{@code null} or empty string ("")
//    * - set containing no characters</li>
//    * <li>Single character, such as "a"
//    *  - set containing just that character</li>
//    * <li>Multi character, such as "a-e"
//    *  - set containing characters from one character to the other</li>
//    * <li>Negated, such as "^a" or "^a-e"
//    *  - set containing all characters except those defined</li>
//    * <li>Combinations, such as "abe-g"
//    *  - set containing all the characters from the individual sets</li>
//    * </ul>
//    *
//    * <p>The matching order is:</p>
//    * <ol>
//    * <li>Negated multi character range, such as "^a-e"
//    * <li>Ordinary multi character range, such as "a-e"
//    * <li>Negated single character, such as "^a"
//    * <li>Ordinary single character, such as "a"
//    * </ol>
//    *
//    * <p>Matching works left to right. Once a match is found the
//    * search starts again from the next character.</p>
//    *
//    * <p>If the same range is defined twice using the same syntax, only
//    * one range will be kept.
//    * Thus, "a-ca-c" creates only one range of "a-c".</p>
//    *
//    * <p>If the start and end of a range are in the wrong order,
//    * they are reversed. Thus "a-e" is the same as "e-a".
//    * As a result, "a-ee-a" would create only one range,
//    * as the "a-e" and "e-a" are the same.</p>
//    *
//    * <p>The set of characters represented is the union of the specified ranges.</p>
//    *
//    * <p>There are two ways to add a literal negation character ({@code ^}):</p>
//    * <ul>
//    * <li>As the last character in a string, e.g. {@code CharSet.getInstance("a-z^")}</li>
//    * <li>As a separate element, e.g. {@code CharSet.getInstance("^", "a-z")}</li>
//    * </ul>
//    *
//    * <p>Examples using the negation character:</p>
//    * <pre>
//    * CharSet.getInstance("^a-c").contains('a') = false
//    * CharSet.getInstance("^a-c").contains('d') = true
//    * CharSet.getInstance("^^a-c").contains('a') = true // (only '^' is negated)
//    * CharSet.getInstance("^^a-c").contains('^') = false
//    * CharSet.getInstance("^a-cd-f").contains('d') = true
//    * CharSet.getInstance("a-c^").contains('^') = true
//    * CharSet.getInstance("^", "a-c").contains('^') = true
//    * </pre>
//    *
//    * <p>All CharSet objects returned by this method will be immutable.</p>
//    *
//    * @param setStrs Strings to merge into the set, may be null
//    * @return a CharSet instance
//    * @since 2.4
//    */
//  def getInstance(setStrs: String*): CharSet = {
//    if (setStrs == null) return null
//    if (setStrs.length == 1) {
//      val common = COMMON.get(setStrs(0))
//      if (common != null) return common
//    }
//    new CharSet(setStrs)
//  }
//
//  try COMMON.put(null, EMPTY)
//  COMMON.put(StringUtils.EMPTY, EMPTY)
//  COMMON.put("a-zA-Z", ASCII_ALPHA)
//  COMMON.put("A-Za-z", ASCII_ALPHA)
//  COMMON.put("a-z", ASCII_ALPHA_LOWER)
//  COMMON.put("A-Z", ASCII_ALPHA_UPPER)
//  COMMON.put("0-9", ASCII_NUMERIC)
//
//}
//
//@SerialVersionUID(5947847346149275958L)
//class CharSet protected(val set: String*)
//
///**
//  * <p>Constructs a new CharSet using the set syntax.
//  * Each string is merged in with the set.</p>
//  *
//  * @param set Strings to merge into the initial set
//  * @throws java.lang.NullPointerException if set is {@code null}
//  */
//  extends Serializable {
//  for (s <- set) {
//    add(s)
//  }
//  /** The set of CharRange objects. */
//  final private val set = Collections.synchronizedSet(new util.HashSet[CharRange])
//
//  /**
//    * <p>Add a set definition string to the {@code CharSet}.</p>
//    *
//    * @param str set definition string
//    */
//  protected def add(str: String): Unit = {
//    if (str == null) return
//    val len = str.length
//    var pos = 0
//    while ( {
//      pos < len
//    }) {
//      val remainder = len - pos
//      if (remainder >= 4 && str.charAt(pos) == '^' && str.charAt(pos + 2) == '-') { // negated range
//        set.add(CharRange.isNotIn(str.charAt(pos + 1), str.charAt(pos + 3)))
//        pos += 4
//      }
//      else if (remainder >= 3 && str.charAt(pos + 1) == '-') { // range
//        set.add(CharRange.isIn(str.charAt(pos), str.charAt(pos + 2)))
//        pos += 3
//      }
//      else if (remainder >= 2 && str.charAt(pos) == '^') { // negated char
//        set.add(CharRange.isNot(str.charAt(pos + 1)))
//        pos += 2
//      }
//      else { // char
//        set.add(CharRange.is(str.charAt(pos)))
//        pos += 1
//      }
//    }
//  }
//
//  /**
//    * <p>Gets the internal set as an array of CharRange objects.</p>
//    *
//    * @return an array of immutable CharRange objects
//    * @since 2.0
//    */
//  // NOTE: This is no longer public as CharRange is no longer a public class.
//  //       It may be replaced when CharSet moves to Range.
//  /*public*/ private[lang3] def getCharRanges = set.toArray(new Array[CharRange](0))
//
//  /**
//    * <p>Does the {@code CharSet} contain the specified
//    * character {@code ch}.</p>
//    *
//    * @param ch the character to check for
//    * @return {@code true} if the set contains the characters
//    */
//  def contains(ch: Char): Boolean = {
//    set synchronized
//    import scala.collection.JavaConversions._
//    for (range <- set) {
//      if (range.contains(ch)) return true
//    }
//
//    false
//  }
//
//  /**
//    * <p>Compares two {@code CharSet} objects, returning true if they represent
//    * exactly the same set of characters defined in the same way.</p>
//    *
//    * <p>The two sets {@code abc} and {@code a-c} are <i>not</i>
//    * equal according to this method.</p>
//    *
//    * @param obj the object to compare to
//    * @return true if equal
//    * @since 2.0
//    */
//  override def equals(obj: Any): Boolean = {
//    if (obj eq this) return true
//    if (!obj.isInstanceOf[CharSet]) return false
//    val other = obj.asInstanceOf[CharSet]
//    set == other.set
//  }
//
//  /**
//    * <p>Gets a hash code compatible with the equals method.</p>
//    *
//    * @return a suitable hash code
//    * @since 2.0
//    */
//  override def hashCode: Int = 89 + set.hashCode
//
//  /**
//    * <p>Gets a string representation of the set.</p>
//    *
//    * @return string representation of the set
//    */
//  override def toString: String = set.toString
//}
