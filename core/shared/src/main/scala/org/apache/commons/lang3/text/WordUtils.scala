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

import java.util.regex.Pattern
import org.apache.commons.lang3.StringUtils
import scala.util.control.Breaks

/**
  * <p>Operations on Strings that contain words.</p>
  *
  * <p>This class tries to handle {@code null} input gracefully.
  * An exception will not be thrown for a {@code null} input.
  * Each method documents its behavior in more detail.</p>
  *
  * @since 2.0
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/WordUtils.html">
  *             WordUtils</a> instead
  */
@deprecated object WordUtils {
  /**
    * Our own copy of breaks to avoid conflicts with any other breaks:
    * "Calls to break from one instantiation of Breaks will never target breakable objects of some other instantiation."
    */
  private val breaks: Breaks = new Breaks
  import breaks._

  /**
    * <p>Wraps a single line of text, identifying words by {@code ' '}.</p>
    *
    * <p>New lines will be separated by the system property line separator.
    * Very long words, such as URLs will <i>not</i> be wrapped.</p>
    *
    * <p>Leading spaces on a new line are stripped.
    * Trailing spaces are not stripped.</p>
    *
    * <table border="1">
    * <caption>Examples</caption>
    * <tr>
    * <th>input</th>
    * <th>wrapLength</th>
    * <th>result</th>
    * </tr>
    * <tr>
    * <td>null</td>
    * <td>*</td>
    * <td>null</td>
    * </tr>
    * <tr>
    * <td>""</td>
    * <td>*</td>
    * <td>""</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>"Here is one line of\ntext that is going\nto be wrapped after\n20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Click here to jump to the commons website - https://commons.apache.org"</td>
    * <td>20</td>
    * <td>"Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org"</td>
    * </tr>
    * <tr>
    * <td>"Click here, https://commons.apache.org, to jump to the commons website"</td>
    * <td>20</td>
    * <td>"Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website"</td>
    * </tr>
    * </table>
    *
    * (assuming that '\n' is the systems line separator)
    *
    * @param str        the String to be word wrapped, may be null
    * @param wrapLength the column to wrap the words at, less than 1 is treated as 1
    * @return a line with newlines inserted, {@code null} if null input
    */
  def wrap(str: String, wrapLength: Int): String =
    wrap(str, wrapLength, null, false)

  /**
    * <p>Wraps a single line of text, identifying words by {@code ' '}.</p>
    *
    * <p>Leading spaces on a new line are stripped.
    * Trailing spaces are not stripped.</p>
    *
    * <table border="1">
    * <caption>Examples</caption>
    * <tr>
    * <th>input</th>
    * <th>wrapLength</th>
    * <th>newLineString</th>
    * <th>wrapLongWords</th>
    * <th>result</th>
    * </tr>
    * <tr>
    * <td>null</td>
    * <td>*</td>
    * <td>*</td>
    * <td>true/false</td>
    * <td>null</td>
    * </tr>
    * <tr>
    * <td>""</td>
    * <td>*</td>
    * <td>*</td>
    * <td>true/false</td>
    * <td>""</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>true/false</td>
    * <td>"Here is one line of\ntext that is going\nto be wrapped after\n20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>"&lt;br /&gt;"</td>
    * <td>true/false</td>
    * <td>"Here is one line of&lt;br /&gt;text that is going&lt;br /&gt;to be wrapped after&lt;br /&gt;20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>null</td>
    * <td>true/false</td>
    * <td>"Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Click here to jump to the commons website - https://commons.apache.org"</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>false</td>
    * <td>"Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org"</td>
    * </tr>
    * <tr>
    * <td>"Click here to jump to the commons website - https://commons.apache.org"</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>true</td>
    * <td>"Click here to jump\nto the commons\nwebsite -\nhttp://commons.apach\ne.org"</td>
    * </tr>
    * </table>
    *
    * @param str           the String to be word wrapped, may be null
    * @param wrapLength    the column to wrap the words at, less than 1 is treated as 1
    * @param newLineStr    the string to insert for a new line,
    *                      {@code null} uses the system property line separator
    * @param wrapLongWords true if long words (such as URLs) should be wrapped
    * @return a line with newlines inserted, {@code null} if null input
    */
  def wrap(str: String, wrapLength: Int, newLineStr: String, wrapLongWords: Boolean): String =
    wrap(str, wrapLength, newLineStr, wrapLongWords, " ")

  /**
    * <p>Wraps a single line of text, identifying words by {@code wrapOn}.</p>
    *
    * <p>Leading spaces on a new line are stripped.
    * Trailing spaces are not stripped.</p>
    *
    * <table border="1">
    * <caption>Examples</caption>
    * <tr>
    * <th>input</th>
    * <th>wrapLength</th>
    * <th>newLineString</th>
    * <th>wrapLongWords</th>
    * <th>wrapOn</th>
    * <th>result</th>
    * </tr>
    * <tr>
    * <td>null</td>
    * <td>*</td>
    * <td>*</td>
    * <td>true/false</td>
    * <td>*</td>
    * <td>null</td>
    * </tr>
    * <tr>
    * <td>""</td>
    * <td>*</td>
    * <td>*</td>
    * <td>true/false</td>
    * <td>*</td>
    * <td>""</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>true/false</td>
    * <td>" "</td>
    * <td>"Here is one line of\ntext that is going\nto be wrapped after\n20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>"&lt;br /&gt;"</td>
    * <td>true/false</td>
    * <td>" "</td>
    * <td>"Here is one line of&lt;br /&gt;text that is going&lt;br /&gt;to be wrapped after&lt;br /&gt;20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Here is one line of text that is going to be wrapped after 20 columns."</td>
    * <td>20</td>
    * <td>null</td>
    * <td>true/false</td>
    * <td>" "</td>
    * <td>"Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns."</td>
    * </tr>
    * <tr>
    * <td>"Click here to jump to the commons website - https://commons.apache.org"</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>false</td>
    * <td>" "</td>
    * <td>"Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org"</td>
    * </tr>
    * <tr>
    * <td>"Click here to jump to the commons website - https://commons.apache.org"</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>true</td>
    * <td>" "</td>
    * <td>"Click here to jump\nto the commons\nwebsite -\nhttp://commons.apach\ne.org"</td>
    * </tr>
    * <tr>
    * <td>"flammable/inflammable"</td>
    * <td>20</td>
    * <td>"\n"</td>
    * <td>true</td>
    * <td>"/"</td>
    * <td>"flammable\ninflammable"</td>
    * </tr>
    * </table>
    *
    * @param str           the String to be word wrapped, may be null
    * @param wrapLength    the column to wrap the words at, less than 1 is treated as 1
    * @param newLineStr    the string to insert for a new line,
    *                      {@code null} uses the system property line separator
    * @param wrapLongWords true if long words (such as URLs) should be wrapped
    * @param wrapOn        regex expression to be used as a breakable characters,
    *                      if blank string is provided a space character will be used
    * @return a line with newlines inserted, {@code null} if null input
    */
  def wrap(str: String, wrapLength: Int, newLineStr: String, wrapLongWords: Boolean, wrapOn: String): String = {
    if (str == null) return null

    val updatedNewLineStr = if (newLineStr == null) System.lineSeparator else newLineStr
    val updatedWrapLength = if (wrapLength < 1) 1 else wrapLength
    val patternToWrapOn = Pattern.compile(if (StringUtils.isBlank(wrapOn)) " " else wrapOn)
    val inputLineLength = str.length
    var offset = 0
    val wrappedLine = new StringBuilder(inputLineLength + 32)

    breakable {
      while (offset < inputLineLength) {
        var spaceToWrapAt = -1
        var matcher = patternToWrapOn.matcher(
          str.substring(
            offset,
            Math.min(Math.min(Integer.MAX_VALUE, offset + updatedWrapLength + 1L).toInt, inputLineLength)))
        if (matcher.find) {
          if (matcher.start == 0) {
            offset += matcher.`end`
          } else {
            spaceToWrapAt = matcher.start + offset
          }
        }
        // only last line without leading spaces is left
        if (inputLineLength - offset <= updatedWrapLength) break()

        while (matcher.find) spaceToWrapAt = matcher.start + offset
        if (spaceToWrapAt >= offset) { // normal case
          wrappedLine.appendAll(str.toCharArray, offset, spaceToWrapAt)
          wrappedLine.append(updatedNewLineStr)
          offset = spaceToWrapAt + 1
        } else { // really long word or URL
          if (wrapLongWords) { // wrap really long word one line at a time
            wrappedLine.appendAll(str.toCharArray, offset, updatedWrapLength + offset)
            wrappedLine.append(updatedNewLineStr)
            offset += updatedWrapLength
          } else { // do not wrap really long word, just extend beyond limit
            matcher = patternToWrapOn.matcher(str.substring(offset + updatedWrapLength))
            if (matcher.find) spaceToWrapAt = matcher.start + offset + updatedWrapLength
            if (spaceToWrapAt >= 0) {
              wrappedLine.appendAll(str.toCharArray, offset, spaceToWrapAt)
              wrappedLine.append(updatedNewLineStr)
              offset = spaceToWrapAt + 1
            } else {
              wrappedLine.appendAll(str.toCharArray, offset, str.length)
              offset = inputLineLength
            }
          }
        }
      }
    }

    // Whatever is left in line is short enough to just pass through
    wrappedLine.appendAll(str.toCharArray, offset, str.length)
    wrappedLine.toString
  }

  /**
    * <p>Capitalizes all the delimiter separated words in a String.
    * Only the first character of each word is changed. To convert the
    * rest of each word to lowercase at the same time,
    * use {@link #capitalizeFully ( String, char[])}.</p>
    *
    * <p>The delimiters represent a set of characters understood to separate words.
    * The first string character and the first non-delimiter character after a
    * delimiter will be capitalized. </p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * Capitalization uses the Unicode title case, normally equivalent to
    * upper case.</p>
    *
    * <pre>
    * WordUtils.capitalize(null, *)            = null
    * WordUtils.capitalize("", *)              = ""
    * WordUtils.capitalize(*, new char[0])     = *
    * WordUtils.capitalize("i am fine", null)  = "I Am Fine"
    * WordUtils.capitalize("i aM.fine", {'.'}) = "I aM.Fine"
    * </pre>
    *
    * @param str        the String to capitalize, may be null
    * @param delimiters set of characters to determine capitalization, null means whitespace
    * @return capitalized String, {@code null} if null String input
    * @see #uncapitalize(String)
    * @see #capitalizeFully(String)
    * @since 2.1
    */
  def capitalize(str: String, delimiters: Char*): String = {
    val delimLen =
      if (delimiters == null) -1
      else delimiters.length
    if (StringUtils.isEmpty(str) || delimLen == 0) return str
    val buffer = str.toCharArray
    var capitalizeNext = true
    for (i <- 0 until buffer.length) {
      val ch = buffer(i)
      if (isDelimiter(ch, delimiters.toArray)) capitalizeNext = true
      else if (capitalizeNext) {
        buffer(i) = Character.toTitleCase(ch)
        capitalizeNext = false
      }
    }
    new String(buffer)
  }

  /**
    * <p>Converts all the delimiter separated words in a String into capitalized words,
    * that is each word is made up of a titlecase character and then a series of
    * lowercase characters. </p>
    *
    * <p>The delimiters represent a set of characters understood to separate words.
    * The first string character and the first non-delimiter character after a
    * delimiter will be capitalized. </p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * '
    * Capitalization uses the Unicode title case, normally equivalent to
    * upper case.</p>
    *
    * <pre>
    * WordUtils.capitalizeFully(null, *)            = null
    * WordUtils.capitalizeFully("", *)              = ""
    * WordUtils.capitalizeFully(*, null)            = *
    * WordUtils.capitalizeFully(*, new char[0])     = *
    * WordUtils.capitalizeFully("i aM.fine", {'.'}) = "I am.Fine"
    * </pre>
    *
    * @param str        the String to capitalize, may be null
    * @param delimiters set of characters to determine capitalization, null means whitespace
    * @return capitalized String, {@code null} if null String input
    * @since 2.1
    */
  def capitalizeFully(str: String, delimiters: Char*): String = {
    val delimLen =
      if (delimiters == null) -1
      else delimiters.length
    if (StringUtils.isEmpty(str) || delimLen == 0) return str
    capitalize(str.toLowerCase, delimiters: _*)
  }

  // TODO: @link to Character#isWhitespace
  /**
    * <p>Uncapitalizes all the whitespace separated words in a String.
    * Only the first character of each word is changed.</p>
    *
    * <p>The delimiters represent a set of characters understood to separate words.
    * The first string character and the first non-delimiter character after a
    * delimiter will be uncapitalized. </p>
    *
    * <p>Whitespace is defined by {@code java.lang.Character#isWhitespace}.
    * A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * WordUtils.uncapitalize(null, *)            = null
    * WordUtils.uncapitalize("", *)              = ""
    * WordUtils.uncapitalize(*, null)            = *
    * WordUtils.uncapitalize(*, new char[0])     = *
    * WordUtils.uncapitalize("I AM.FINE", {'.'}) = "i AM.fINE"
    * </pre>
    *
    * @param str        the String to uncapitalize, may be null
    * @param delimiters set of characters to determine uncapitalization, null means whitespace
    * @return uncapitalized String, {@code null} if null String input
    * @see #capitalize(String)
    * @since 2.1
    */
  def uncapitalize(str: String, delimiters: Char*): String = {
    val delimLen =
      if (delimiters == null) -1
      else delimiters.length
    if (StringUtils.isEmpty(str) || delimLen == 0) return str
    val buffer = str.toCharArray
    var uncapitalizeNext = true
    for (i <- 0 until buffer.length) {
      val ch = buffer(i)
      if (isDelimiter(ch, delimiters.toArray)) uncapitalizeNext = true
      else if (uncapitalizeNext) {
        buffer(i) = Character.toLowerCase(ch)
        uncapitalizeNext = false
      }
    }
    new String(buffer)
  }

  // TODO: @link to Character#isWhitespace
  /**
    * <p>Swaps the case of a String using a word based algorithm.</p>
    *
    * <ul>
    * <li>Upper case character converts to Lower case</li>
    * <li>Title case character converts to Lower case</li>
    * <li>Lower case character after Whitespace or at start converts to Title case</li>
    * <li>Other Lower case character converts to Upper case</li>
    * </ul>
    *
    * <p>Whitespace is defined by {@code java.lang.Character#isWhitespace}.
    * A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.swapCase(null)                 = null
    * StringUtils.swapCase("")                   = ""
    * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
    * </pre>
    *
    * @param str the String to swap case, may be null
    * @return the changed String, {@code null} if null String input
    */
  def swapCase(str: String): String = {
    if (StringUtils.isEmpty(str)) return str
    val buffer = str.toCharArray
    var whitespace = true
    for (i <- 0 until buffer.length) {
      val ch = buffer(i)
      if (Character.isUpperCase(ch) || Character.isTitleCase(ch)) {
        buffer(i) = Character.toLowerCase(ch)
        whitespace = false
      } else if (Character.isLowerCase(ch)) if (whitespace) {
        buffer(i) = Character.toTitleCase(ch)
        whitespace = false
      } else buffer(i) = Character.toUpperCase(ch)
      else whitespace = Character.isWhitespace(ch)
    }
    new String(buffer)
  }

  // TODO: @link to Character#isWhitespace
  /**
    * <p>Extracts the initial characters from each word in the String.</p>
    *
    * <p>All first characters after the defined delimiters are returned as a new string.
    * Their case is not changed.</p>
    *
    * <p>If the delimiters array is null, then Whitespace is used.
    * Whitespace is defined by {@code java.lang.Character#isWhitespace}.
    * A {@code null} input String returns {@code null}.
    * An empty delimiter array returns an empty String.</p>
    *
    * <pre>
    * WordUtils.initials(null, *)                = null
    * WordUtils.initials("", *)                  = ""
    * WordUtils.initials("Ben John Lee", null)   = "BJL"
    * WordUtils.initials("Ben J.Lee", null)      = "BJ"
    * WordUtils.initials("Ben J.Lee", [' ','.']) = "BJL"
    * WordUtils.initials(*, new char[0])         = ""
    * </pre>
    *
    * @param str        the String to get initials from, may be null
    * @param delimiters set of characters to determine words, null means whitespace
    * @return String of initial characters, {@code null} if null String input
    * @see #initials(String)
    * @since 2.2
    */
  def initials(str: String, delimiters: Char*): String = {
    if (StringUtils.isEmpty(str)) return str
    if (delimiters != null && delimiters.isEmpty) return StringUtils.EMPTY
    val strLen = str.length
    val buf = new Array[Char](strLen / 2 + 1)
    var count = 0
    var lastWasGap = true
    for (i <- 0 until strLen) {
      val ch = str.charAt(i)
      if (isDelimiter(ch, delimiters.toArray)) lastWasGap = true
      else if (lastWasGap) {
        buf({ count += 1; count - 1 }) = ch
        lastWasGap = false
      } else {
        // ignore ch
      }
    }
    new String(buf, 0, count)
  }

  /**
    * <p>Checks if the String contains all words in the given array.</p>
    *
    * <p>
    * A {@code null} String will return {@code false}. A {@code null}, zero
    * length search array or if one element of array is null will return {@code false}.
    * </p>
    *
    * <pre>
    * WordUtils.containsAllWords(null, *)            = false
    * WordUtils.containsAllWords("", *)              = false
    * WordUtils.containsAllWords(*, null)            = false
    * WordUtils.containsAllWords(*, [])              = false
    * WordUtils.containsAllWords("abcd", "ab", "cd") = false
    * WordUtils.containsAllWords("abc def", "def", "abc") = true
    * </pre>
    *
    * @param word  The CharSequence to check, may be null
    * @param words The array of String words to search for, may be null
    * @return {@code true} if all search words are found, {@code false} otherwise
    * @since 3.5
    */
  def containsAllWords(word: CharSequence, words: CharSequence*): Boolean = {
    if (StringUtils.isEmpty(word) || words.isEmpty) return false
    for (w <- words) {
      if (StringUtils.isBlank(w)) return false
      val p = Pattern.compile(".*\\b" + w + "\\b.*")
      if (!p.matcher(word).matches) return false
    }
    true
  }

  /**
    * Is the character a delimiter.
    *
    * @param ch         the character to check
    * @param delimiters the delimiters
    * @return true if it is a delimiter
    */
  private def isDelimiter(ch: Char, delimiters: Array[Char]): Boolean = {
    if (delimiters == null) return Character.isWhitespace(ch)
    for (delimiter <- delimiters) {
      if (ch == delimiter) return true
    }
    false
  }
}
