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

package org.apache.commons.lang3.text

import java.util
import org.apache.commons.lang3.StringUtils

/**
  * A matcher class that can be queried to determine if a character array
  * portion matches.
  * <p>
  * This class comes complete with various factory methods.
  * If these do not suffice, you can subclass and implement your own matcher.
  *
  * @since 2.2
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringMatcherFactory.html">
  *             StringMatcherFactory</a> instead
  */
@deprecated object StrMatcher {
  /**
    * Matches the comma character.
    */
  private val COMMA_MATCHER = new StrMatcher.CharMatcher(',')
  /**
    * Matches the tab character.
    */
  private val TAB_MATCHER = new StrMatcher.CharMatcher('\t')
  /**
    * Matches the space character.
    */
  private val SPACE_MATCHER = new StrMatcher.CharMatcher(' ')
  /**
    * Matches the same characters as StringTokenizer,
    * namely space, tab, newline, formfeed.
    */
  private val SPLIT_MATCHER = new StrMatcher.CharSetMatcher(" \t\n\r\f".toCharArray)
  /**
    * Matches the String trim() whitespace characters.
    */
  private val TRIM_MATCHER = new StrMatcher.TrimMatcher
  /**
    * Matches the double quote character.
    */
  private val SINGLE_QUOTE_MATCHER = new StrMatcher.CharMatcher('\'')
  private val DOUBLE_QUOTE_MATCHER = new StrMatcher.CharMatcher('"')
  /**
    * Matches the single or double quote character.
    */
  private val QUOTE_MATCHER = new StrMatcher.CharSetMatcher("'\"".toCharArray)
  /**
    * Matches no characters.
    */
  private val NONE_MATCHER = new StrMatcher.NoMatcher

  /**
    * Returns a matcher which matches the comma character.
    *
    * @return a matcher for a comma
    */
  def commaMatcher: StrMatcher = COMMA_MATCHER

  /**
    * Returns a matcher which matches the tab character.
    *
    * @return a matcher for a tab
    */
  def tabMatcher: StrMatcher = TAB_MATCHER

  /**
    * Returns a matcher which matches the space character.
    *
    * @return a matcher for a space
    */
  def spaceMatcher: StrMatcher = SPACE_MATCHER

  /**
    * Matches the same characters as StringTokenizer,
    * namely space, tab, newline and formfeed.
    *
    * @return the split matcher
    */
  def splitMatcher: StrMatcher = SPLIT_MATCHER

  /**
    * Matches the String trim() whitespace characters.
    *
    * @return the trim matcher
    */
  def trimMatcher: StrMatcher = TRIM_MATCHER

  /**
    * Returns a matcher which matches the single quote character.
    *
    * @return a matcher for a single quote
    */
  def singleQuoteMatcher: StrMatcher = SINGLE_QUOTE_MATCHER

  /**
    * Returns a matcher which matches the double quote character.
    *
    * @return a matcher for a double quote
    */
  def doubleQuoteMatcher: StrMatcher = DOUBLE_QUOTE_MATCHER

  /**
    * Returns a matcher which matches the single or double quote character.
    *
    * @return a matcher for a single or double quote
    */
  def quoteMatcher: StrMatcher = QUOTE_MATCHER

  /**
    * Matches no characters.
    *
    * @return a matcher that matches nothing
    */
  def noneMatcher: StrMatcher = NONE_MATCHER

  /**
    * Constructor that creates a matcher from a character.
    *
    * @param ch the character to match, must not be null
    * @return a new Matcher for the given char
    */
  def charMatcher(ch: Char) = new StrMatcher.CharMatcher(ch)

  /**
    * Constructor that creates a matcher from a set of characters.
    *
    * @param chars the characters to match, null or empty matches nothing
    * @return a new matcher for the given char[]
    */
  def charSetMatcher(chars: Char*): StrMatcher = {
    if (chars == null || chars.length == 0) return NONE_MATCHER
    if (chars.length == 1) return new StrMatcher.CharMatcher(chars(0))
    new StrMatcher.CharSetMatcher(chars.toArray)
  }

  /**
    * Constructor that creates a matcher from a string representing a set of characters.
    *
    * @param chars the characters to match, null or empty matches nothing
    * @return a new Matcher for the given characters
    */
  def charSetMatcher(chars: String): StrMatcher = {
    if (StringUtils.isEmpty(chars)) return NONE_MATCHER
    if (chars.length == 1) return new StrMatcher.CharMatcher(chars.charAt(0))
    new StrMatcher.CharSetMatcher(chars.toCharArray)
  }

  /**
    * Constructor that creates a matcher from a string.
    *
    * @param str the string to match, null or empty matches nothing
    * @return a new Matcher for the given String
    */
  def stringMatcher(str: String): StrMatcher = {
    if (StringUtils.isEmpty(str)) return NONE_MATCHER
    new StrMatcher.StringMatcher(str)
  }

  /**
    * Class used to define a set of characters for matching purposes.
    */
  final private[text] class CharSetMatcher private[text]() extends StrMatcher {

    /**
      * Constructor that creates a matcher from a character array.
      *
      * @param chars the characters to match, must not be null
      */
    def this(chars: Array[Char]) = {
      this()
      this.chars = chars.clone
      util.Arrays.sort(this.chars)
    }

    /** The set of characters to match. */
    final private var chars: Array[Char] = null

    /**
      * Returns whether or not the given character matches.
      *
      * @param buffer      the text content to match against, do not change
      * @param pos         the starting position for the match, valid for buffer
      * @param bufferStart the first active index in the buffer, valid for buffer
      * @param bufferEnd   the end index of the active buffer, valid for buffer
      * @return the number of matching characters, zero for no match
      */
    override def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int): Int =
      if (util.Arrays.binarySearch(chars, buffer(pos)) >= 0) 1
      else 0
  }

  /**
    * Class used to define a character for matching purposes.
    *
    * @param ch the character to match
    */
  final private[text] class CharMatcher private[text](ch: Char) extends StrMatcher {
    override def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int): Int =
      if (ch == buffer(pos)) 1
      else 0
  }


  final private[text] class StringMatcher private[text]() extends StrMatcher {
    /** The string to match, as a character array. */
    private var chars: Array[Char] = null

    /**
      * Constructor that creates a matcher from a String.
      *
      * @param str the string to match, must not be null
      */
    def this(str: String) = {
      this()
      this.chars = str.toCharArray
    }

    /**
      * Returns whether or not the given text matches the stored string.
      *
      * @param buffer      the text content to match against, do not change
      * @param pos         the starting position for the match, valid for buffer
      * @param bufferStart the first active index in the buffer, valid for buffer
      * @param bufferEnd   the end index of the active buffer, valid for buffer
      * @return the number of matching characters, zero for no match
      */
    override def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int): Int = {
      val len: Int = chars.length
      if (pos + len > bufferEnd) return 0

      var p: Int = pos
      var i: Int = 0

      while (i < chars.length) {
        if (chars(i) != buffer(p)) return 0

        i += 1
        p += 1
      }
      len
    }

    override def toString: String = super.toString + ' ' + util.Arrays.toString(chars)
  }

  /**
    * Class used to match no characters.
    *
    */
  final private[text] class NoMatcher private[text]() extends StrMatcher {
    /**
      * Always returns {@code false}.
      *
      * @param buffer      the text content to match against, do not change
      * @param pos         the starting position for the match, valid for buffer
      * @param bufferStart the first active index in the buffer, valid for buffer
      * @param bufferEnd   the end index of the active buffer, valid for buffer
      * @return the number of matching characters, zero for no match
      */
    override def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int) = 0
  }

  /**
    * Class used to match whitespace as per trim().
    */
  final private[text] class TrimMatcher private[text]()

  /**
    * Constructs a new instance of {@code TrimMatcher}.
    */
    extends StrMatcher {
    override def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int): Int = if (buffer(pos) <= 32) 1
    else 0
  }

}

/**
  * Constructor.
  */
@deprecated abstract class StrMatcher protected() {
  /**
    * Returns the number of matching characters, zero for no match.
    * <p>
    * This method is called to check for a match.
    * The parameter {@code pos} represents the current position to be
    * checked in the string {@code buffer} (a character array which must
    * not be changed).
    * The API guarantees that {@code pos} is a valid index for {@code buffer}.
    * <p>
    * The character array may be larger than the active area to be matched.
    * Only values in the buffer between the specified indices may be accessed.
    * <p>
    * The matching code may check one character or many.
    * It may check characters preceding {@code pos} as well as those
    * after, so long as no checks exceed the bounds specified.
    * <p>
    * It must return zero for no match, or a positive number if a match was found.
    * The number indicates the number of characters that matched.
    *
    * @param buffer      the text content to match against, do not change
    * @param pos         the starting position for the match, valid for buffer
    * @param bufferStart the first active index in the buffer, valid for buffer
    * @param bufferEnd   the end index (exclusive) of the active buffer, valid for buffer
    * @return the number of matching characters, zero for no match
    */
  def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int): Int

  /**
    * Returns the number of matching characters, zero for no match.
    * <p>
    * This method is called to check for a match.
    * The parameter {@code pos} represents the current position to be
    * checked in the string {@code buffer} (a character array which must
    * not be changed).
    * The API guarantees that {@code pos} is a valid index for {@code buffer}.
    * <p>
    * The matching code may check one character or many.
    * It may check characters preceding {@code pos} as well as those after.
    * <p>
    * It must return zero for no match, or a positive number if a match was found.
    * The number indicates the number of characters that matched.
    *
    * @param buffer the text content to match against, do not change
    * @param pos    the starting position for the match, valid for buffer
    * @return the number of matching characters, zero for no match
    * @since 2.4
    */
  def isMatch(buffer: Array[Char], pos: Int): Int = isMatch(buffer, pos, 0, buffer.length)
}