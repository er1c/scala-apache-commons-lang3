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

package org.apache.commons.lang3

import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.text.Normalizer
import java.util
import java.util.Locale
import scala.util.control.Breaks
import java.util.Objects
import java.util.function.Supplier
import java.util.regex.Pattern
import scala.collection.JavaConverters._

// TODO: @link java.lang.Character#isWhitespace(Char)
/**
  * <p>Operations on {@link java.lang.String} that are
  * {@code null} safe.</p>
  *
  * <ul>
  * <li><b>IsEmpty/IsBlank</b>
  *      - checks if a String contains text</li>
  * <li><b>Trim/Strip</b>
  *      - removes leading and trailing whitespace</li>
  * <li><b>Equals/Compare</b>
  *      - compares two strings in a null-safe manner</li>
  * <li><b>startsWith</b>
  *      - check if a String starts with a prefix in a null-safe manner</li>
  * <li><b>endsWith</b>
  *      - check if a String ends with a suffix in a null-safe manner</li>
  * <li><b>IndexOf/LastIndexOf/Contains</b>
  *      - null-safe index-of checks
  * <li><b>IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut</b>
  *      - index-of any of a set of Strings</li>
  * <li><b>ContainsOnly/ContainsNone/ContainsAny</b>
  *      - checks if String contains only/none/any of these characters</li>
  * <li><b>Substring/Left/Right/Mid</b>
  *      - null-safe substring extractions</li>
  * <li><b>SubstringBefore/SubstringAfter/SubstringBetween</b>
  *      - substring extraction relative to other strings</li>
  * <li><b>Split/Join</b>
  *      - splits a String into an array of substrings and vice versa</li>
  * <li><b>Remove/Delete</b>
  *      - removes part of a String</li>
  * <li><b>Replace/Overlay</b>
  *      - Searches a String and replaces one String with another</li>
  * <li><b>Chomp/Chop</b>
  *      - removes the last part of a String</li>
  * <li><b>AppendIfMissing</b>
  *      - appends a suffix to the end of the String if not present</li>
  * <li><b>PrependIfMissing</b>
  *      - prepends a prefix to the start of the String if not present</li>
  * <li><b>LeftPad/RightPad/Center/Repeat</b>
  *      - pads a String</li>
  * <li><b>UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize</b>
  *      - changes the case of a String</li>
  * <li><b>CountMatches</b>
  *      - counts the number of occurrences of one String in another</li>
  * <li><b>IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable</b>
  *      - checks the characters in a String</li>
  * <li><b>DefaultString</b>
  *      - protects against a null input String</li>
  * <li><b>Rotate</b>
  *      - rotate (circular shift) a String</li>
  * <li><b>Reverse/ReverseDelimited</b>
  *      - reverses a String</li>
  * <li><b>Abbreviate</b>
  *      - abbreviates a string using ellipses or another given String</li>
  * <li><b>Difference</b>
  *      - compares Strings and reports on their differences</li>
  * <li><b>LevenshteinDistance</b>
  *      - the number of changes needed to change one String into another</li>
  * </ul>
  *
  * <p>The {@code StringUtils} class defines certain words related to
  * String handling.</p>
  *
  * <ul>
  * <li>null - {@code null}</li>
  * <li>empty - a zero-length string ({@code ""})</li>
  * <li>space - the space character ({@code ' '}, char 32)</li>
  * <li>whitespace - the characters defined by {@code java.lang.Character#isWhitespace(Char)}</li>
  * <li>trim - the characters &lt;= 32 as in {@link java.lang.String#trim}</li>
  * </ul>
  *
  * <p>{@code StringUtils} handles {@code null} input Strings quietly.
  * That is to say that a {@code null} input will return {@code null}.
  * Where a {@code boolean} or {@code int} is being returned
  * details vary by method.</p>
  *
  * <p>A side effect of the {@code null} handling is that a
  * {@code NullPointerException} should be considered a bug in
  * {@code StringUtils}.</p>
  *
  * <p>Methods in this class include sample code in their Javadoc comments to explain their operation.
  * The symbol {@code *} is used to indicate any input including {@code null}.</p>
  *
  * <p>#ThreadSafe#</p>
  *
  * @see java.lang.String
  * @since 1.0
  */
//@Immutable
object StringUtils {
  /**
    * Our own copy of breaks to avoid conflicts with any other breaks:
    * "Calls to break from one instantiation of Breaks will never target breakable objects of some other instantiation."
    */
  private val breaks: Breaks = new Breaks
  import breaks._

  private val STRING_BUILDER_SIZE = 256
  /**
    * A String for a space character.
    *
    * @since 3.2
    */
  val SPACE = " "
  /**
    * The empty String {@code ""}.
    *
    * @since 2.0
    */
  val EMPTY = ""
  /**
    * A String for linefeed LF ("\n").
    *
    * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences
    *      for Character and String Literals</a>
    * @since 3.2
    */
  val LF = "\n"
  /**
    * A String for carriage return CR ("\r").
    *
    * @see <a href="http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF: Escape Sequences
    *      for Character and String Literals</a>
    * @since 3.2
    */
  val CR = "\r"
  /**
    * Represents a failed index search.
    *
    * @since 2.1
    */
  val INDEX_NOT_FOUND: Int = -1
  /**
    * <p>The maximum size to which the padding constant(s) can expand.</p>
    */
  private val PAD_LIMIT = 8192
  /**
    * Pattern used in {@link #stripAccents}.
    */
  private val STRIP_ACCENTS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+") //$NON-NLS-1$

  /**
    * <p>Abbreviates a String using ellipses. This will turn
    * "Now is the time for all good men" into "Now is the time for..."</p>
    *
    * <p>Specifically:</p>
    * <ul>
    * <li>If the number of characters in {@code str} is less than or equal to
    * {@code maxWidth}, return {@code str}.</li>
    * <li>Else abbreviate it to {@code (substring(str, 0, max-3) + "...")}.</li>
    * <li>If {@code maxWidth} is less than {@code 4}, throw an
    * {@code IllegalArgumentException}.</li>
    * <li>In no case will it return a String of length greater than
    * {@code maxWidth}.</li>
    * </ul>
    *
    * <pre>
    * StringUtils.abbreviate(null, *)      = null
    * StringUtils.abbreviate("", 4)        = ""
    * StringUtils.abbreviate("abcdefg", 6) = "abc..."
    * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
    * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
    * StringUtils.abbreviate("abcdefg", 4) = "a..."
    * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
    * </pre>
    *
    * @param str      the String to check, may be null
    * @param maxWidth maximum length of result String, must be at least 4
    * @return abbreviated String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException if the width is too small
    * @since 2.0
    */
  def abbreviate(str: String, maxWidth: Int): String = abbreviate(str, "...", 0, maxWidth)

  /**
    * <p>Abbreviates a String using ellipses. This will turn
    * "Now is the time for all good men" into "...is the time for..."</p>
    *
    * <p>Works like {@code abbreviate(String, int)}, but allows you to specify
    * a "left edge" offset.  Note that this left edge is not necessarily going to
    * be the leftmost character in the result, or the first character following the
    * ellipses, but it will appear somewhere in the result.
    *
    * <p>In no case will it return a String of length greater than
    * {@code maxWidth}.</p>
    *
    * <pre>
    * StringUtils.abbreviate(null, *, *)                = null
    * StringUtils.abbreviate("", 0, 4)                  = ""
    * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
    * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
    * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
    * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
    * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
    * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
    * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
    * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
    * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
    * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
    * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
    * </pre>
    *
    * @param str      the String to check, may be null
    * @param offset   left edge of source String
    * @param maxWidth maximum length of result String, must be at least 4
    * @return abbreviated String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException if the width is too small
    * @since 2.0
    */
  def abbreviate(str: String, offset: Int, maxWidth: Int): String = abbreviate(str, "...", offset, maxWidth)

  /**
    * <p>Abbreviates a String using another given String as replacement marker. This will turn
    * "Now is the time for all good men" into "Now is the time for..." if "..." was defined
    * as the replacement marker.</p>
    *
    * <p>Specifically:</p>
    * <ul>
    * <li>If the number of characters in {@code str} is less than or equal to
    * {@code maxWidth}, return {@code str}.</li>
    * <li>Else abbreviate it to {@code (substring(str, 0, max-abbrevMarker.length) + abbrevMarker)}.</li>
    * <li>If {@code maxWidth} is less than {@code abbrevMarker.length + 1}, throw an
    * {@code IllegalArgumentException}.</li>
    * <li>In no case will it return a String of length greater than
    * {@code maxWidth}.</li>
    * </ul>
    *
    * <pre>
    * StringUtils.abbreviate(null, "...", *)      = null
    * StringUtils.abbreviate("abcdefg", null, *)  = "abcdefg"
    * StringUtils.abbreviate("", "...", 4)        = ""
    * StringUtils.abbreviate("abcdefg", ".", 5)   = "abcd."
    * StringUtils.abbreviate("abcdefg", ".", 7)   = "abcdefg"
    * StringUtils.abbreviate("abcdefg", ".", 8)   = "abcdefg"
    * StringUtils.abbreviate("abcdefg", "..", 4)  = "ab.."
    * StringUtils.abbreviate("abcdefg", "..", 3)  = "a.."
    * StringUtils.abbreviate("abcdefg", "..", 2)  = IllegalArgumentException
    * StringUtils.abbreviate("abcdefg", "...", 3) = IllegalArgumentException
    * </pre>
    *
    * @param str          the String to check, may be null
    * @param abbrevMarker the String used as replacement marker
    * @param maxWidth     maximum length of result String, must be at least {@code abbrevMarker.length + 1}
    * @return abbreviated String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException if the width is too small
    * @since 3.6
    */
  def abbreviate(str: String, abbrevMarker: String, maxWidth: Int): String = abbreviate(str, abbrevMarker, 0, maxWidth)

  /**
    * <p>Abbreviates a String using a given replacement marker. This will turn
    * "Now is the time for all good men" into "...is the time for..." if "..." was defined
    * as the replacement marker.</p>
    *
    * <p>Works like {@code abbreviate(String, String, int)}, but allows you to specify
    * a "left edge" offset.  Note that this left edge is not necessarily going to
    * be the leftmost character in the result, or the first character following the
    * replacement marker, but it will appear somewhere in the result.
    *
    * <p>In no case will it return a String of length greater than {@code maxWidth}.</p>
    *
    * <pre>
    * StringUtils.abbreviate(null, null, *, *)                 = null
    * StringUtils.abbreviate("abcdefghijklmno", null, *, *)    = "abcdefghijklmno"
    * StringUtils.abbreviate("", "...", 0, 4)                  = ""
    * StringUtils.abbreviate("abcdefghijklmno", "---", -1, 10) = "abcdefg---"
    * StringUtils.abbreviate("abcdefghijklmno", ",", 0, 10)    = "abcdefghi,"
    * StringUtils.abbreviate("abcdefghijklmno", ",", 1, 10)    = "abcdefghi,"
    * StringUtils.abbreviate("abcdefghijklmno", ",", 2, 10)    = "abcdefghi,"
    * StringUtils.abbreviate("abcdefghijklmno", "::", 4, 10)   = "::efghij::"
    * StringUtils.abbreviate("abcdefghijklmno", "...", 6, 10)  = "...ghij..."
    * StringUtils.abbreviate("abcdefghijklmno", "*", 9, 10)    = "*ghijklmno"
    * StringUtils.abbreviate("abcdefghijklmno", "'", 10, 10)   = "'ghijklmno"
    * StringUtils.abbreviate("abcdefghijklmno", "!", 12, 10)   = "!ghijklmno"
    * StringUtils.abbreviate("abcdefghij", "abra", 0, 4)       = IllegalArgumentException
    * StringUtils.abbreviate("abcdefghij", "...", 5, 6)        = IllegalArgumentException
    * </pre>
    *
    * @param str          the String to check, may be null
    * @param abbrevMarker the String used as replacement marker
    * @param offset       left edge of source String
    * @param maxWidth     maximum length of result String, must be at least 4
    * @return abbreviated String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException if the width is too small
    * @since 3.6
    */
  def abbreviate(str: String, abbrevMarker: String, offset: Int, maxWidth: Int): String = {
    if (isNotEmpty(str) && EMPTY == abbrevMarker && maxWidth > 0) return substring(str, 0, maxWidth)
    if (isAnyEmpty(str, abbrevMarker)) return str

    val abbrevMarkerLength = abbrevMarker.length
    val minAbbrevWidth = abbrevMarkerLength + 1
    val minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1

    if (maxWidth < minAbbrevWidth)
      throw new IllegalArgumentException("Minimum abbreviation width is %d".format(minAbbrevWidth))
    if (str.length <= maxWidth) return str

    var _offset = if (offset > str.length) str.length else offset

    if (str.length - _offset < maxWidth - abbrevMarkerLength) _offset = str.length - (maxWidth - abbrevMarkerLength)
    if (_offset <= abbrevMarkerLength + 1) return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker
    if (maxWidth < minAbbrevWidthOffset)
      throw new IllegalArgumentException("Minimum abbreviation width with offset is %d".format(minAbbrevWidthOffset))
    if (_offset + maxWidth - abbrevMarkerLength < str.length)
      return abbrevMarker + abbreviate(str.substring(_offset), abbrevMarker, maxWidth - abbrevMarkerLength)
    abbrevMarker + str.substring(str.length - (maxWidth - abbrevMarkerLength))
  }

  /**
    * <p>Abbreviates a String to the length passed, replacing the middle characters with the supplied
    * replacement String.</p>
    *
    * <p>This abbreviation only occurs if the following criteria is met:</p>
    * <ul>
    * <li>Neither the String for abbreviation nor the replacement String are null or empty </li>
    * <li>The length to truncate to is less than the length of the supplied String</li>
    * <li>The length to truncate to is greater than 0</li>
    * <li>The abbreviated String will have enough room for the length supplied replacement String
    * and the first and last characters of the supplied String for abbreviation</li>
    * </ul>
    * <p>Otherwise, the returned String will be the same as the supplied String for abbreviation.
    * </p>
    *
    * <pre>
    * StringUtils.abbreviateMiddle(null, null, 0)      = null
    * StringUtils.abbreviateMiddle("abc", null, 0)      = "abc"
    * StringUtils.abbreviateMiddle("abc", ".", 0)      = "abc"
    * StringUtils.abbreviateMiddle("abc", ".", 3)      = "abc"
    * StringUtils.abbreviateMiddle("abcdef", ".", 4)     = "ab.f"
    * </pre>
    *
    * @param str    the String to abbreviate, may be null
    * @param middle the String to replace the middle characters with, may be null
    * @param length the length to abbreviate {@code str} to.
    * @return the abbreviated String if the above criteria is met, or the original String supplied for abbreviation.
    * @since 2.5
    */
  def abbreviateMiddle(str: String, middle: String, length: Int): String = {
    if (isAnyEmpty(str, middle) || length >= str.length || length < middle.length + 2) return str
    val targetSting = length - middle.length
    val startOffset = targetSting / 2 + targetSting % 2
    val endOffset = str.length - targetSting / 2

    str.substring(0, startOffset) + middle + str.substring(endOffset)
  }

  /**
    * Appends the suffix to the end of the string if the string does not
    * already end with the suffix.
    *
    * @param str        The string.
    * @param suffix     The suffix to append to the end of the string.
    * @param ignoreCase Indicates whether the compare should ignore case.
    * @param suffixes   Additional suffixes that are valid terminators (optional).
    * @return A new String if suffix was appended, the same string otherwise.
    */
  private def appendIfMissing(
    str: String,
    suffix: CharSequence,
    ignoreCase: Boolean,
    suffixes: Array[_ <: CharSequence]
  ): String = {
    if (str == null || isEmpty(suffix) || endsWith(str, suffix, ignoreCase)) return str
    if (ArrayUtils.isNotEmpty(suffixes)) for (s <- suffixes) {
      if (endsWith(str, s, ignoreCase)) return str
    }
    str + suffix.toString
  }

  /**
    * Appends the suffix to the end of the string if the string does not
    * already end with any of the suffixes.
    *
    * <pre>
    * StringUtils.appendIfMissing(null, null) = null
    * StringUtils.appendIfMissing("abc", null) = "abc"
    * StringUtils.appendIfMissing("", "xyz") = "xyz"
    * StringUtils.appendIfMissing("abc", "xyz") = "abcxyz"
    * StringUtils.appendIfMissing("abcxyz", "xyz") = "abcxyz"
    * StringUtils.appendIfMissing("abcXYZ", "xyz") = "abcXYZxyz"
    * </pre>
    * <p>With additional suffixes,</p>
    * <pre>
    * StringUtils.appendIfMissing(null, null, null) = null
    * StringUtils.appendIfMissing("abc", null, null) = "abc"
    * StringUtils.appendIfMissing("", "xyz", null) = "xyz"
    * StringUtils.appendIfMissing("abc", "xyz", new CharSequence[]{null}) = "abcxyz"
    * StringUtils.appendIfMissing("abc", "xyz", "") = "abc"
    * StringUtils.appendIfMissing("abc", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissing("abcxyz", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissing("abcmno", "xyz", "mno") = "abcmno"
    * StringUtils.appendIfMissing("abcXYZ", "xyz", "mno") = "abcXYZxyz"
    * StringUtils.appendIfMissing("abcMNO", "xyz", "mno") = "abcMNOxyz"
    * </pre>
    *
    * @param str      The string.
    * @param suffix   The suffix to append to the end of the string.
    * @param suffixes Additional suffixes that are valid terminators.
    * @return A new String if suffix was appended, the same string otherwise.
    * @since 3.2
    */
  def appendIfMissing(str: String, suffix: CharSequence, suffixes: CharSequence*): String =
    appendIfMissing(str, suffix, false, suffixes.toArray)

  /**
    * Appends the suffix to the end of the string if the string does not
    * already end with any of the suffixes.
    *
    * <pre>
    * StringUtils.appendIfMissing(null, null) = null
    * StringUtils.appendIfMissing("abc", null) = "abc"
    * StringUtils.appendIfMissing("", "xyz") = "xyz"
    * StringUtils.appendIfMissing("abc", "xyz") = "abcxyz"
    * StringUtils.appendIfMissing("abcxyz", "xyz") = "abcxyz"
    * StringUtils.appendIfMissing("abcXYZ", "xyz") = "abcXYZxyz"
    * </pre>
    * <p>With additional suffixes,</p>
    * <pre>
    * StringUtils.appendIfMissing(null, null, null) = null
    * StringUtils.appendIfMissing("abc", null, null) = "abc"
    * StringUtils.appendIfMissing("", "xyz", null) = "xyz"
    * StringUtils.appendIfMissing("abc", "xyz", new CharSequence[]{null}) = "abcxyz"
    * StringUtils.appendIfMissing("abc", "xyz", "") = "abc"
    * StringUtils.appendIfMissing("abc", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissing("abcxyz", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissing("abcmno", "xyz", "mno") = "abcmno"
    * StringUtils.appendIfMissing("abcXYZ", "xyz", "mno") = "abcXYZxyz"
    * StringUtils.appendIfMissing("abcMNO", "xyz", "mno") = "abcMNOxyz"
    * </pre>
    *
    * @param str      The string.
    * @param suffix   The suffix to append to the end of the string.
    * @param suffixes Additional suffixes that are valid terminators.
    * @return A new String if suffix was appended, the same string otherwise.
    * @since 3.2
    */
  def appendIfMissing(str: String, suffix: CharSequence, suffixes: Array[_ <: CharSequence]): String =
    appendIfMissing(str, suffix, false, suffixes)

  /**
    * Appends the suffix to the end of the string if the string does not
    * already end, case insensitive, with any of the suffixes.
    *
    * <pre>
    * StringUtils.appendIfMissingIgnoreCase(null, null) = null
    * StringUtils.appendIfMissingIgnoreCase("abc", null) = "abc"
    * StringUtils.appendIfMissingIgnoreCase("", "xyz") = "xyz"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz") = "abcXYZ"
    * </pre>
    * <p>With additional suffixes,</p>
    * <pre>
    * StringUtils.appendIfMissingIgnoreCase(null, null, null) = null
    * StringUtils.appendIfMissingIgnoreCase("abc", null, null) = "abc"
    * StringUtils.appendIfMissingIgnoreCase("", "xyz", null) = "xyz"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", new CharSequence[]{null}) = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "") = "abc"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcmno", "xyz", "mno") = "abcmno"
    * StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz", "mno") = "abcXYZ"
    * StringUtils.appendIfMissingIgnoreCase("abcMNO", "xyz", "mno") = "abcMNO"
    * </pre>
    *
    * @param str      The string.
    * @param suffix   The suffix to append to the end of the string.
    * @param suffixes Additional suffixes that are valid terminators.
    * @return A new String if suffix was appended, the same string otherwise.
    * @since 3.2
    */
  def appendIfMissingIgnoreCase(str: String, suffix: CharSequence, suffixes: CharSequence*): String =
    appendIfMissing(str, suffix, true, suffixes.toArray)

  /**
    * Appends the suffix to the end of the string if the string does not
    * already end, case insensitive, with any of the suffixes.
    *
    * <pre>
    * StringUtils.appendIfMissingIgnoreCase(null, null) = null
    * StringUtils.appendIfMissingIgnoreCase("abc", null) = "abc"
    * StringUtils.appendIfMissingIgnoreCase("", "xyz") = "xyz"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz") = "abcXYZ"
    * </pre>
    * <p>With additional suffixes,</p>
    * <pre>
    * StringUtils.appendIfMissingIgnoreCase(null, null, null) = null
    * StringUtils.appendIfMissingIgnoreCase("abc", null, null) = "abc"
    * StringUtils.appendIfMissingIgnoreCase("", "xyz", null) = "xyz"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", new CharSequence[]{null}) = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "") = "abc"
    * StringUtils.appendIfMissingIgnoreCase("abc", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcxyz", "xyz", "mno") = "abcxyz"
    * StringUtils.appendIfMissingIgnoreCase("abcmno", "xyz", "mno") = "abcmno"
    * StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz", "mno") = "abcXYZ"
    * StringUtils.appendIfMissingIgnoreCase("abcMNO", "xyz", "mno") = "abcMNO"
    * </pre>
    *
    * @param str      The string.
    * @param suffix   The suffix to append to the end of the string.
    * @param suffixes Additional suffixes that are valid terminators.
    * @return A new String if suffix was appended, the same string otherwise.
    * @since 3.2
    */
  def appendIfMissingIgnoreCase(str: String, suffix: CharSequence, suffixes: Array[_ <: CharSequence]): String =
    appendIfMissing(str, suffix, true, suffixes)

  // TODO: @link java.lang.Character#toTitleCase
  // TODO: @link org.apache.commons.lang3.text.WordUtils#capitalize
  /**
    * <p>Capitalizes a String changing the first character to title case as
    * per {@code java.lang.Character#toTitleCase}. No other characters are changed.</p>
    *
    * <p>For a word based algorithm, see {@code org.apache.commons.lang3.text.WordUtils#capitalize}.
    * A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.capitalize(null)  = null
    * StringUtils.capitalize("")    = ""
    * StringUtils.capitalize("cat") = "Cat"
    * StringUtils.capitalize("cAt") = "CAt"
    * StringUtils.capitalize("'cat'") = "'cat'"
    * </pre>
    *
    * @param str the String to capitalize, may be null
    * @return the capitalized String, {@code null} if null String input
    * @see org.apache.commons.lang3.text.WordUtils#capitalize
    * @see #uncapitalize(String)
    * @since 2.0
    */
  def capitalize(str: String): String = {
    val strLen = length(str)
    if (strLen == 0) return str

    val firstCodepoint = str.codePointAt(0)
    val newCodePoint = Character.toTitleCase(firstCodepoint)
    if (firstCodepoint == newCodePoint) return str // already capitalized

    val newCodePoints = new Array[Int](strLen) // cannot be longer than the char array
    var outOffset = 1

    newCodePoints(0) = newCodePoint // copy the first codepoint

    var inOffset = Character.charCount(firstCodepoint)
    while (inOffset < strLen) {
      val codepoint = str.codePointAt(inOffset)
      newCodePoints(outOffset) = codepoint // copy the remaining ones
      outOffset += 1

      inOffset += Character.charCount(codepoint)
    }

    new String(newCodePoints, 0, outOffset)
  }

  /**
    * <p>Centers a String in a larger String of size {@code size}
    * using the space character (' ').</p>
    *
    * <p>If the size is less than the String length, the original String is returned.
    * A {@code null} String returns {@code null}.
    * A negative size is treated as zero.</p>
    *
    * <p>Equivalent to {@code center(str, size, " ")}.</p>
    *
    * <pre>
    * StringUtils.center(null, *)   = null
    * StringUtils.center("", 4)     = "    "
    * StringUtils.center("ab", -1)  = "ab"
    * StringUtils.center("ab", 4)   = " ab "
    * StringUtils.center("abcd", 2) = "abcd"
    * StringUtils.center("a", 4)    = " a  "
    * </pre>
    *
    * @param str  the String to center, may be null
    * @param size the int size of new String, negative treated as zero
    * @return centered String, {@code null} if null String input
    */
  def center(str: String, size: Int): String = center(str, size, ' ')

  /**
    * <p>Centers a String in a larger String of size {@code size}.
    * Uses a supplied character as the value to pad the String with.</p>
    *
    * <p>If the size is less than the String length, the String is returned.
    * A {@code null} String returns {@code null}.
    * A negative size is treated as zero.</p>
    *
    * <pre>
    * StringUtils.center(null, *, *)     = null
    * StringUtils.center("", 4, ' ')     = "    "
    * StringUtils.center("ab", -1, ' ')  = "ab"
    * StringUtils.center("ab", 4, ' ')   = " ab "
    * StringUtils.center("abcd", 2, ' ') = "abcd"
    * StringUtils.center("a", 4, ' ')    = " a  "
    * StringUtils.center("a", 4, 'y')    = "yayy"
    * </pre>
    *
    * @param str     the String to center, may be null
    * @param size    the int size of new String, negative treated as zero
    * @param padChar the character to pad the new String with
    * @return centered String, {@code null} if null String input
    * @since 2.0
    */
  def center(str: String, size: Int, padChar: Char): String = {
    if (str == null || size <= 0) return str
    val strLen = str.length
    val pads = size - strLen
    if (pads <= 0) return str

    val left = leftPad(str, strLen + pads / 2, padChar)
    rightPad(left, size, padChar)
  }

  /**
    * <p>Centers a String in a larger String of size {@code size}.
    * Uses a supplied String as the value to pad the String with.</p>
    *
    * <p>If the size is less than the String length, the String is returned.
    * A {@code null} String returns {@code null}.
    * A negative size is treated as zero.</p>
    *
    * <pre>
    * StringUtils.center(null, *, *)     = null
    * StringUtils.center("", 4, " ")     = "    "
    * StringUtils.center("ab", -1, " ")  = "ab"
    * StringUtils.center("ab", 4, " ")   = " ab "
    * StringUtils.center("abcd", 2, " ") = "abcd"
    * StringUtils.center("a", 4, " ")    = " a  "
    * StringUtils.center("a", 4, "yz")   = "yayz"
    * StringUtils.center("abc", 7, null) = "  abc  "
    * StringUtils.center("abc", 7, "")   = "  abc  "
    * </pre>
    *
    * @param str    the String to center, may be null
    * @param size   the int size of new String, negative treated as zero
    * @param padStr the String to pad the new String with, must not be null or empty
    * @return centered String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException if padStr is {@code null} or empty
    */
  def center(str: String, size: Int, padStr: String): String = {
    if (str == null || size <= 0) return str

    val newPadStr: String = if (isEmpty(padStr)) SPACE else padStr

    val strLen = str.length
    val pads = size - strLen
    if (pads <= 0) return str

    val left = leftPad(str, strLen + pads / 2, newPadStr)
    rightPad(left, size, newPadStr)
  }

  /**
    * <p>Removes one newline from end of a String if it's there,
    * otherwise leave it alone.  A newline is &quot;{@code \n}&quot;,
    * &quot;{@code \r}&quot;, or &quot;{@code \r\n}&quot;.</p>
    *
    * <p>NOTE: This method changed in 2.0.
    * It now more closely matches Perl chomp.</p>
    *
    * <pre>
    * StringUtils.chomp(null)          = null
    * StringUtils.chomp("")            = ""
    * StringUtils.chomp("abc \r")      = "abc "
    * StringUtils.chomp("abc\n")       = "abc"
    * StringUtils.chomp("abc\r\n")     = "abc"
    * StringUtils.chomp("abc\r\n\r\n") = "abc\r\n"
    * StringUtils.chomp("abc\n\r")     = "abc\n"
    * StringUtils.chomp("abc\n\rabc")  = "abc\n\rabc"
    * StringUtils.chomp("\r")          = ""
    * StringUtils.chomp("\n")          = ""
    * StringUtils.chomp("\r\n")        = ""
    * </pre>
    *
    * @param str the String to chomp a newline from, may be null
    * @return String without newline, {@code null} if null String input
    */
  def chomp(str: String): String = {
    if (isEmpty(str)) return str

    if (str.length == 1) {
      val ch = str.charAt(0)
      if (ch == CharUtils.CR || ch == CharUtils.LF) return EMPTY
      return str
    }

    var lastIdx = str.length - 1
    val last = str.charAt(lastIdx)
    if (last == CharUtils.LF) {
      if (str.charAt(lastIdx - 1) == CharUtils.CR) lastIdx -= 1
    } else if (last != CharUtils.CR) lastIdx += 1

    str.substring(0, lastIdx)
  }

  /**
    * <p>Removes {@code separator} from the end of
    * {@code str} if it's there, otherwise leave it alone.</p>
    *
    * <p>NOTE: This method changed in version 2.0.
    * It now more closely matches Perl chomp.
    * For the previous behavior, use {@link #substringBeforeLast ( String, String)}.
    * This method uses {@link java.lang.String# endsWith ( String )}.</p>
    *
    * <pre>
    * StringUtils.chomp(null, *)         = null
    * StringUtils.chomp("", *)           = ""
    * StringUtils.chomp("foobar", "bar") = "foo"
    * StringUtils.chomp("foobar", "baz") = "foobar"
    * StringUtils.chomp("foo", "foo")    = ""
    * StringUtils.chomp("foo ", "foo")   = "foo "
    * StringUtils.chomp(" foo", "foo")   = " "
    * StringUtils.chomp("foo", "foooo")  = "foo"
    * StringUtils.chomp("foo", "")       = "foo"
    * StringUtils.chomp("foo", null)     = "foo"
    * </pre>
    *
    * @param str       the String to chomp from, may be null
    * @param separator separator String, may be null
    * @return String without trailing separator, {@code null} if null String input
    * @deprecated This feature will be removed in Lang 4.0, use {@link StringUtils# removeEnd ( String, String)} instead
    */
  @deprecated def chomp(str: String, separator: String): String = removeEnd(str, separator)

  /**
    * <p>Remove the last character from a String.</p>
    *
    * <p>If the String ends in {@code \r\n}, then remove both
    * of them.</p>
    *
    * <pre>
    * StringUtils.chop(null)          = null
    * StringUtils.chop("")            = ""
    * StringUtils.chop("abc \r")      = "abc "
    * StringUtils.chop("abc\n")       = "abc"
    * StringUtils.chop("abc\r\n")     = "abc"
    * StringUtils.chop("abc")         = "ab"
    * StringUtils.chop("abc\nabc")    = "abc\nab"
    * StringUtils.chop("a")           = ""
    * StringUtils.chop("\r")          = ""
    * StringUtils.chop("\n")          = ""
    * StringUtils.chop("\r\n")        = ""
    * </pre>
    *
    * @param str the String to chop last character from, may be null
    * @return String without last character, {@code null} if null String input
    */
  def chop(str: String): String = {
    if (str == null) return null
    val strLen = str.length

    if (strLen < 2) return EMPTY

    val lastIdx = strLen - 1
    val ret = str.substring(0, lastIdx)
    val last = str.charAt(lastIdx)

    if (last == CharUtils.LF && ret.charAt(lastIdx - 1) == CharUtils.CR) ret.substring(0, lastIdx - 1)
    else ret
  }

  /**
    * <p>Compare two Strings lexicographically, as per {@link java.lang.String# compareTo ( String )}, returning :</p>
    * <ul>
    * <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
    * <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
    * <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
    * </ul>
    *
    * <p>This is a {@code null} safe version of :</p>
    * <blockquote><pre>str1.compareTo(str2)</pre></blockquote>
    *
    * <p>{@code null} value is considered less than non-{@code null} value.
    * Two {@code null} references are considered equal.</p>
    *
    * <pre>
    * StringUtils.compare(null, null)   = 0
    * StringUtils.compare(null , "a")   &lt; 0
    * StringUtils.compare("a", null)    &gt; 0
    * StringUtils.compare("abc", "abc") = 0
    * StringUtils.compare("a", "b")     &lt; 0
    * StringUtils.compare("b", "a")     &gt; 0
    * StringUtils.compare("a", "B")     &gt; 0
    * StringUtils.compare("ab", "abc")  &lt; 0
    * </pre>
    *
    * @see #compare(String, String, boolean)
    * @see String#compareTo(String)
    * @param str1 the String to compare from
    * @param str2 the String to compare to
    * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal or greater than {@code str2}
    * @since 3.5
    */
  def compare(str1: String, str2: String): Int = compare(str1, str2, true)

  /**
    * <p>Compare two Strings lexicographically, as per {@link java.lang.String# compareTo ( String )}, returning :</p>
    * <ul>
    * <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
    * <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
    * <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
    * </ul>
    *
    * <p>This is a {@code null} safe version of :</p>
    * <blockquote><pre>str1.compareTo(str2)</pre></blockquote>
    *
    * <p>{@code null} inputs are handled according to the {@code nullIsLess} parameter.
    * Two {@code null} references are considered equal.</p>
    *
    * <pre>
    * StringUtils.compare(null, null, *)     = 0
    * StringUtils.compare(null , "a", true)  &lt; 0
    * StringUtils.compare(null , "a", false) &gt; 0
    * StringUtils.compare("a", null, true)   &gt; 0
    * StringUtils.compare("a", null, false)  &lt; 0
    * StringUtils.compare("abc", "abc", *)   = 0
    * StringUtils.compare("a", "b", *)       &lt; 0
    * StringUtils.compare("b", "a", *)       &gt; 0
    * StringUtils.compare("a", "B", *)       &gt; 0
    * StringUtils.compare("ab", "abc", *)    &lt; 0
    * </pre>
    *
    * @see String#compareTo(String)
    * @param str1       the String to compare from
    * @param str2       the String to compare to
    * @param nullIsLess whether consider {@code null} value less than non-{@code null} value
    * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2}
    * @since 3.5
    */
  def compare(str1: String, str2: String, nullIsLess: Boolean): Int = {
    if (str1 eq str2) return 0

    if (str1 == null) return {
      if (nullIsLess) -1
      else 1
    }

    if (str2 == null) return {
      if (nullIsLess) 1
      else -1
    }

    str1.compareTo(str2)
  }

  /**
    * <p>Compare two Strings lexicographically, ignoring case differences,
    * as per {@link java.lang.String# compareToIgnoreCase ( String )}, returning :</p>
    * <ul>
    * <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
    * <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
    * <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
    * </ul>
    *
    * <p>This is a {@code null} safe version of :</p>
    * <blockquote><pre>str1.compareToIgnoreCase(str2)</pre></blockquote>
    *
    * <p>{@code null} value is considered less than non-{@code null} value.
    * Two {@code null} references are considered equal.
    * Comparison is case insensitive.</p>
    *
    * <pre>
    * StringUtils.compareIgnoreCase(null, null)   = 0
    * StringUtils.compareIgnoreCase(null , "a")   &lt; 0
    * StringUtils.compareIgnoreCase("a", null)    &gt; 0
    * StringUtils.compareIgnoreCase("abc", "abc") = 0
    * StringUtils.compareIgnoreCase("abc", "ABC") = 0
    * StringUtils.compareIgnoreCase("a", "b")     &lt; 0
    * StringUtils.compareIgnoreCase("b", "a")     &gt; 0
    * StringUtils.compareIgnoreCase("a", "B")     &lt; 0
    * StringUtils.compareIgnoreCase("A", "b")     &lt; 0
    * StringUtils.compareIgnoreCase("ab", "ABC")  &lt; 0
    * </pre>
    *
    * @see #compareIgnoreCase(String, String, boolean)
    * @see String#compareToIgnoreCase(String)
    * @param str1 the String to compare from
    * @param str2 the String to compare to
    * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2},
    *         ignoring case differences.
    * @since 3.5
    */
  def compareIgnoreCase(str1: String, str2: String): Int = compareIgnoreCase(str1, str2, true)

  /**
    * <p>Compare two Strings lexicographically, ignoring case differences,
    * as per {@link java.lang.String# compareToIgnoreCase ( String )}, returning :</p>
    * <ul>
    * <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
    * <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
    * <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
    * </ul>
    *
    * <p>This is a {@code null} safe version of :</p>
    * <blockquote><pre>str1.compareToIgnoreCase(str2)</pre></blockquote>
    *
    * <p>{@code null} inputs are handled according to the {@code nullIsLess} parameter.
    * Two {@code null} references are considered equal.
    * Comparison is case insensitive.</p>
    *
    * <pre>
    * StringUtils.compareIgnoreCase(null, null, *)     = 0
    * StringUtils.compareIgnoreCase(null , "a", true)  &lt; 0
    * StringUtils.compareIgnoreCase(null , "a", false) &gt; 0
    * StringUtils.compareIgnoreCase("a", null, true)   &gt; 0
    * StringUtils.compareIgnoreCase("a", null, false)  &lt; 0
    * StringUtils.compareIgnoreCase("abc", "abc", *)   = 0
    * StringUtils.compareIgnoreCase("abc", "ABC", *)   = 0
    * StringUtils.compareIgnoreCase("a", "b", *)       &lt; 0
    * StringUtils.compareIgnoreCase("b", "a", *)       &gt; 0
    * StringUtils.compareIgnoreCase("a", "B", *)       &lt; 0
    * StringUtils.compareIgnoreCase("A", "b", *)       &lt; 0
    * StringUtils.compareIgnoreCase("ab", "abc", *)    &lt; 0
    * </pre>
    *
    * @see String#compareToIgnoreCase(String)
    * @param str1       the String to compare from
    * @param str2       the String to compare to
    * @param nullIsLess whether consider {@code null} value less than non-{@code null} value
    * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2},
    *         ignoring case differences.
    * @since 3.5
    */
  def compareIgnoreCase(str1: String, str2: String, nullIsLess: Boolean): Int = {
    if (str1 eq str2) return 0
    if (str1 == null)
      return if (nullIsLess) -1
      else 1
    if (str2 == null)
      return if (nullIsLess) 1
      else -1
    str1.compareToIgnoreCase(str2)
  }

  /**
    * <p>Checks if CharSequence contains a search CharSequence, handling {@code null}.
    * This method uses {@link java.lang.String# indexOf ( String )} if possible.</p>
    *
    * <p>A {@code null} CharSequence will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.contains(null, *)     = false
    * StringUtils.contains(*, null)     = false
    * StringUtils.contains("", "")      = true
    * StringUtils.contains("abc", "")   = true
    * StringUtils.contains("abc", "a")  = true
    * StringUtils.contains("abc", "z")  = false
    * </pre>
    *
    * @param seq       the CharSequence to check, may be null
    * @param searchSeq the CharSequence to find, may be null
    * @return true if the CharSequence contains the search CharSequence,
    *         false if not or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from contains(String, String) to contains(CharSequence, CharSequence)
    */
  def contains(seq: CharSequence, searchSeq: CharSequence): Boolean =
    if (seq == null || searchSeq == null) false
    else CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0

  /**
    * <p>Checks if CharSequence contains a search character, handling {@code null}.
    * This method uses {@link java.lang.String# indexOf ( int )} if possible.</p>
    *
    * <p>A {@code null} or empty ("") CharSequence will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.contains(null, *)    = false
    * StringUtils.contains("", *)      = false
    * StringUtils.contains("abc", 'a') = true
    * StringUtils.contains("abc", 'z') = false
    * </pre>
    *
    * @param seq        the CharSequence to check, may be null
    * @param searchChar the character to find
    * @return true if the CharSequence contains the search character,
    *         false if not or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from contains(String, int) to contains(CharSequence, int)
    */
  def contains(seq: CharSequence, searchChar: Int): Boolean =
    if (isEmpty(seq)) false
    else CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0

  /**
    * <p>Checks if the CharSequence contains any character in the given
    * set of characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code false}.
    * A {@code null} or zero length search array will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.containsAny(null, *)                  = false
    * StringUtils.containsAny("", *)                    = false
    * StringUtils.containsAny(*, null)                  = false
    * StringUtils.containsAny(*, [])                    = false
    * StringUtils.containsAny("zzabyycdxx", ['z', 'a']) = true
    * StringUtils.containsAny("zzabyycdxx", ['b', 'y']) = true
    * StringUtils.containsAny("zzabyycdxx", ['z', 'y']) = true
    * StringUtils.containsAny("aba", ['z'])             = false
    * </pre>
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars the chars to search for, may be null
    * @return the {@code true} if any of the chars are found,
    *         {@code false} if no match or null input
    * @since 2.4
    *        3.0 Changed signature from containsAny(String, char[]) to containsAny(CharSequence, char...)
    */
  def containsAny(cs: CharSequence, searchChars: Char*): Boolean = {
    if (isEmpty(cs) || searchChars.isEmpty) return false
    val csLength = cs.length
    val searchLength = searchChars.length
    val csLast = csLength - 1
    val searchLast = searchLength - 1
    for (i <- 0 until csLength) {
      val ch = cs.charAt(i)
      for (j <- 0 until searchLength) {
        if (searchChars(j) == ch) if (Character.isHighSurrogate(ch)) {
          if (j == searchLast) { // missing low surrogate, fine, like String.indexOf(String)
            return true
          }
          if (i < csLast && searchChars(j + 1) == cs.charAt(i + 1)) return true
        } else { // ch is in the Basic Multilingual Plane
          return true
        }
      }
    }
    false
  }

  /**
    * <p>
    * Checks if the CharSequence contains any character in the given set of characters.
    * </p>
    *
    * <p>
    * A {@code null} CharSequence will return {@code false}. A {@code null} search CharSequence will return
    * {@code false}.
    * </p>
    *
    * <pre>
    * StringUtils.containsAny(null, *)               = false
    * StringUtils.containsAny("", *)                 = false
    * StringUtils.containsAny(*, null)               = false
    * StringUtils.containsAny(*, "")                 = false
    * StringUtils.containsAny("zzabyycdxx", "za")    = true
    * StringUtils.containsAny("zzabyycdxx", "by")    = true
    * StringUtils.containsAny("zzabyycdxx", "zy")    = true
    * StringUtils.containsAny("zzabyycdxx", "\tx")   = true
    * StringUtils.containsAny("zzabyycdxx", "$.#yF") = true
    * StringUtils.containsAny("aba", "z")            = false
    * </pre>
    *
    * @param cs
    * the CharSequence to check, may be null
    * @param searchChars
    * the chars to search for, may be null
    * @return the {@code true} if any of the chars are found, {@code false} if no match or null input
    * @since 2.4
    *        3.0 Changed signature from containsAny(String, String) to containsAny(CharSequence, CharSequence)
    */
  def containsAny(cs: CharSequence, searchChars: Array[Char]): Boolean =
    if (searchChars == null) false
    else containsAny(cs, searchChars: _*)

  /**
    * <p>
    * Checks if the CharSequence contains any character in the given set of characters.
    * </p>
    *
    * <p>
    * A {@code null} CharSequence will return {@code false}. A {@code null} search CharSequence will return
    * {@code false}.
    * </p>
    *
    * <pre>
    * StringUtils.containsAny(null, *)               = false
    * StringUtils.containsAny("", *)                 = false
    * StringUtils.containsAny(*, null)               = false
    * StringUtils.containsAny(*, "")                 = false
    * StringUtils.containsAny("zzabyycdxx", "za")    = true
    * StringUtils.containsAny("zzabyycdxx", "by")    = true
    * StringUtils.containsAny("zzabyycdxx", "zy")    = true
    * StringUtils.containsAny("zzabyycdxx", "\tx")   = true
    * StringUtils.containsAny("zzabyycdxx", "$.#yF") = true
    * StringUtils.containsAny("aba", "z")            = false
    * </pre>
    *
    * @param cs
    * the CharSequence to check, may be null
    * @param searchChars
    * the chars to search for, may be null
    * @return the {@code true} if any of the chars are found, {@code false} if no match or null input
    * @since 2.4
    *        3.0 Changed signature from containsAny(String, String) to containsAny(CharSequence, CharSequence)
    */
  def containsAny(cs: CharSequence, searchChars: CharSequence): Boolean =
    if (searchChars == null) false
    else containsAny(cs, CharSequenceUtils.toCharArray(searchChars): _*)

  /**
    * <p>Checks if the CharSequence contains any of the CharSequences in the given array.</p>
    *
    * <p>
    * A {@code null} {@code cs} CharSequence will return {@code false}. A {@code null} or zero
    * length search array will return {@code false}.
    * </p>
    *
    * <pre>
    * StringUtils.containsAny(null, *)            = false
    * StringUtils.containsAny("", *)              = false
    * StringUtils.containsAny(*, null)            = false
    * StringUtils.containsAny(*, [])              = false
    * StringUtils.containsAny("abcd", "ab", null) = true
    * StringUtils.containsAny("abcd", "ab", "cd") = true
    * StringUtils.containsAny("abc", "d", "abc")  = true
    * </pre>
    *
    * @param cs                  The CharSequence to check, may be null
    * @param searchCharSequences The array of CharSequences to search for, may be null.
    *                            Individual CharSequences may be null as well.
    * @return {@code true} if any of the search CharSequences are found, {@code false} otherwise
    * @since 3.4
    */
  def containsAny[T <: CharSequence](cs: CharSequence, searchCharSequences: T*)(implicit d: DummyImplicit): Boolean = {
    if (isEmpty(cs) || searchCharSequences.isEmpty) return false
    for (searchCharSequence <- searchCharSequences) {
      if (contains(cs, searchCharSequence)) return true
    }
    false
  }

  /**
    * <p>Checks if the CharSequence contains any of the CharSequences in the given array.</p>
    *
    * <p>
    * A {@code null} {@code cs} CharSequence will return {@code false}. A {@code null} or zero
    * length search array will return {@code false}.
    * </p>
    *
    * <pre>
    * StringUtils.containsAny(null, *)            = false
    * StringUtils.containsAny("", *)              = false
    * StringUtils.containsAny(*, null)            = false
    * StringUtils.containsAny(*, [])              = false
    * StringUtils.containsAny("abcd", "ab", null) = true
    * StringUtils.containsAny("abcd", "ab", "cd") = true
    * StringUtils.containsAny("abc", "d", "abc")  = true
    * </pre>
    *
    * @param cs                  The CharSequence to check, may be null
    * @param searchCharSequences The array of CharSequences to search for, may be null.
    *                            Individual CharSequences may be null as well.
    * @return {@code true} if any of the search CharSequences are found, {@code false} otherwise
    * @since 3.4
    */
  def containsAny(cs: CharSequence, searchCharSequences: Array[_ <: CharSequence])(implicit
    d: DummyImplicit): Boolean = {
    if (isEmpty(cs) || ArrayUtils.isEmpty(searchCharSequences)) return false
    for (searchCharSequence <- searchCharSequences) {
      if (contains(cs, searchCharSequence)) return true
    }
    false
  }

  /**
    * <p>Checks if CharSequence contains a search CharSequence irrespective of case,
    * handling {@code null}. Case-insensitivity is defined as by
    * {@link java.lang.String# equalsIgnoreCase ( String )}.
    *
    * <p>A {@code null} CharSequence will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.containsIgnoreCase(null, *) = false
    * StringUtils.containsIgnoreCase(*, null) = false
    * StringUtils.containsIgnoreCase("", "") = true
    * StringUtils.containsIgnoreCase("abc", "") = true
    * StringUtils.containsIgnoreCase("abc", "a") = true
    * StringUtils.containsIgnoreCase("abc", "z") = false
    * StringUtils.containsIgnoreCase("abc", "A") = true
    * StringUtils.containsIgnoreCase("abc", "Z") = false
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @return true if the CharSequence contains the search CharSequence irrespective of
    *         case or false if not or {@code null} string input
    * @since 3.0 Changed signature from containsIgnoreCase(String, String) to containsIgnoreCase(CharSequence, CharSequence)
    */
  def containsIgnoreCase(str: CharSequence, searchStr: CharSequence): Boolean = {
    if (str == null || searchStr == null) return false
    val len = searchStr.length
    val max = str.length - len
    for (i <- 0 to max) {
      if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) return true
    }
    false
  }

  /**
    * <p>Checks that the CharSequence does not contain certain characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code true}.
    * A {@code null} invalid character array will return {@code true}.
    * An empty CharSequence (length()=0) always returns true.</p>
    *
    * <pre>
    * StringUtils.containsNone(null, *)       = true
    * StringUtils.containsNone(*, null)       = true
    * StringUtils.containsNone("", *)         = true
    * StringUtils.containsNone("ab", '')      = true
    * StringUtils.containsNone("abab", 'xyz') = true
    * StringUtils.containsNone("ab1", 'xyz')  = true
    * StringUtils.containsNone("abz", 'xyz')  = false
    * </pre>
    *
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars an array of invalid chars, may be null
    * @return true if it contains none of the invalid chars, or is null
    * @since 2.0
    *        3.0 Changed signature from containsNone(String, char[]) to containsNone(CharSequence, char...)
    */
  def containsNone(cs: CharSequence, searchChars: Char*): Boolean = {
    if (cs == null || searchChars == null || searchChars.isEmpty) return true
    val csLen = cs.length
    val csLast = csLen - 1
    val searchLen = searchChars.length
    val searchLast = searchLen - 1
    for (i <- 0 until csLen) {
      val ch = cs.charAt(i)
      for (j <- 0 until searchLen) {
        if (searchChars(j) == ch) if (Character.isHighSurrogate(ch)) {
          if (j == searchLast) return false
          if (i < csLast && searchChars(j + 1) == cs.charAt(i + 1)) return false
        } else return false
      }
    }
    true
  }

  /**
    * <p>Checks that the CharSequence does not contain certain characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code true}.
    * A {@code null} invalid character array will return {@code true}.
    * An empty CharSequence (length()=0) always returns true.</p>
    *
    * <pre>
    * StringUtils.containsNone(null, *)       = true
    * StringUtils.containsNone(*, null)       = true
    * StringUtils.containsNone("", *)         = true
    * StringUtils.containsNone("ab", '')      = true
    * StringUtils.containsNone("abab", 'xyz') = true
    * StringUtils.containsNone("ab1", 'xyz')  = true
    * StringUtils.containsNone("abz", 'xyz')  = false
    * </pre>
    *
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars an array of invalid chars, may be null
    * @return true if it contains none of the invalid chars, or is null
    * @since 2.0
    *        3.0 Changed signature from containsNone(String, char[]) to containsNone(CharSequence, char...)
    */
  def containsNone(cs: CharSequence, searchChars: Array[Char]): Boolean = {
    if (cs == null || ArrayUtils.isEmpty(searchChars)) return true
    val csLen = cs.length
    val csLast = csLen - 1
    val searchLen = searchChars.length
    val searchLast = searchLen - 1
    for (i <- 0 until csLen) {
      val ch = cs.charAt(i)
      for (j <- 0 until searchLen) {
        if (searchChars(j) == ch) if (Character.isHighSurrogate(ch)) {
          if (j == searchLast) return false
          if (i < csLast && searchChars(j + 1) == cs.charAt(i + 1)) return false
        } else return false
      }
    }
    true
  }

  /**
    * <p>Checks that the CharSequence does not contain certain characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code true}.
    * A {@code null} invalid character array will return {@code true}.
    * An empty String ("") always returns true.</p>
    *
    * <pre>
    * StringUtils.containsNone(null, *)       = true
    * StringUtils.containsNone(*, null)       = true
    * StringUtils.containsNone("", *)         = true
    * StringUtils.containsNone("ab", "")      = true
    * StringUtils.containsNone("abab", "xyz") = true
    * StringUtils.containsNone("ab1", "xyz")  = true
    * StringUtils.containsNone("abz", "xyz")  = false
    * </pre>
    *
    * @param cs           the CharSequence to check, may be null
    * @param invalidChars a String of invalid chars, may be null
    * @return true if it contains none of the invalid chars, or is null
    * @since 2.0
    *        3.0 Changed signature from containsNone(String, String) to containsNone(CharSequence, String)
    */
  def containsNone(cs: CharSequence, invalidChars: String): Boolean = {
    if (cs == null || invalidChars == null) return true
    containsNone(cs, invalidChars.toCharArray: _*)
  }

  /**
    * <p>Checks if the CharSequence contains only certain characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code false}.
    * A {@code null} valid character array will return {@code false}.
    * An empty CharSequence (length()=0) always returns {@code true}.</p>
    *
    * <pre>
    * StringUtils.containsOnly(null, *)       = false
    * StringUtils.containsOnly(*, null)       = false
    * StringUtils.containsOnly("", *)         = true
    * StringUtils.containsOnly("ab", '')      = false
    * StringUtils.containsOnly("abab", 'abc') = true
    * StringUtils.containsOnly("ab1", 'abc')  = false
    * StringUtils.containsOnly("abz", 'abc')  = false
    * </pre>
    *
    *
    * @param cs    the String to check, may be null
    * @param valid an array of valid chars, may be null
    * @return true if it only contains valid chars and is non-null
    * @since 3.0 Changed signature from containsOnly(String, char[]) to containsOnly(CharSequence, char...)
    */
  def containsOnly(cs: CharSequence, valid: Char*): Boolean = { // All these pre-checks are to maintain API with an older version
    if (valid == null || cs == null) return false
    if (cs.length == 0) return true
    if (valid.length == 0) return false
    indexOfAnyBut(cs, valid: _*) == INDEX_NOT_FOUND
  }

  /**
    * <p>Checks if the CharSequence contains only certain characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code false}.
    * A {@code null} valid character array will return {@code false}.
    * An empty CharSequence (length()=0) always returns {@code true}.</p>
    *
    * <pre>
    * StringUtils.containsOnly(null, *)       = false
    * StringUtils.containsOnly(*, null)       = false
    * StringUtils.containsOnly("", *)         = true
    * StringUtils.containsOnly("ab", '')      = false
    * StringUtils.containsOnly("abab", 'abc') = true
    * StringUtils.containsOnly("ab1", 'abc')  = false
    * StringUtils.containsOnly("abz", 'abc')  = false
    * </pre>
    *
    *
    * @param cs    the String to check, may be null
    * @param valid an array of valid chars, may be null
    * @return true if it only contains valid chars and is non-null
    * @since 3.0 Changed signature from containsOnly(String, char[]) to containsOnly(CharSequence, char...)
    */
  def containsOnly(cs: CharSequence, valid: Array[Char]): Boolean = { // All these pre-checks are to maintain API with an older version
    if (valid == null || cs == null) return false
    if (cs.length == 0) return true
    if (valid.length == 0) return false
    indexOfAnyBut(cs, valid: _*) == INDEX_NOT_FOUND
  }

  /**
    * <p>Checks if the CharSequence contains only certain characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code false}.
    * A {@code null} valid character String will return {@code false}.
    * An empty String (length()=0) always returns {@code true}.</p>
    *
    * <pre>
    * StringUtils.containsOnly(null, *)       = false
    * StringUtils.containsOnly(*, null)       = false
    * StringUtils.containsOnly("", *)         = true
    * StringUtils.containsOnly("ab", "")      = false
    * StringUtils.containsOnly("abab", "abc") = true
    * StringUtils.containsOnly("ab1", "abc")  = false
    * StringUtils.containsOnly("abz", "abc")  = false
    * </pre>
    *
    * @param cs         the CharSequence to check, may be null
    * @param validChars a String of valid chars, may be null
    * @return true if it only contains valid chars and is non-null
    * @since 2.0
    *        3.0 Changed signature from containsOnly(String, String) to containsOnly(CharSequence, String)
    */
  def containsOnly(cs: CharSequence, validChars: String): Boolean =
    if (cs == null || validChars == null) false
    else containsOnly(cs, validChars.toCharArray: _*)

  /**
    * <p>Check whether the given CharSequence contains any whitespace characters.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * @param seq the CharSequence to check (may be {@code null})
    * @return {@code true} if the CharSequence is not empty and
    *         contains at least 1 (breaking) whitespace character
    * @since 3.0
    */
  // From org.springframework.util.StringUtils, under Apache License 2.0
  def containsWhitespace(seq: CharSequence): Boolean = {
    if (isEmpty(seq)) return false
    val strLen = seq.length
    for (i <- 0 until strLen) {
      if (Character.isWhitespace(seq.charAt(i))) return true
    }
    false
  }

  private def convertRemainingAccentCharacters(decomposed: StringBuilder): Unit = {
    for (i <- 0 until decomposed.length) {
      if (decomposed.charAt(i) == '\u0141') decomposed.setCharAt(i, 'L')
      else if (decomposed.charAt(i) == '\u0142') decomposed.setCharAt(i, 'l')
    }
  }

  /**
    * <p>Counts how many times the char appears in the given string.</p>
    *
    * <p>A {@code null} or empty ("") String input returns {@code 0}.</p>
    *
    * <pre>
    * StringUtils.countMatches(null, *)       = 0
    * StringUtils.countMatches("", *)         = 0
    * StringUtils.countMatches("abba", 0)  = 0
    * StringUtils.countMatches("abba", 'a')   = 2
    * StringUtils.countMatches("abba", 'b')  = 2
    * StringUtils.countMatches("abba", 'x') = 0
    * </pre>
    *
    * @param str the CharSequence to check, may be null
    * @param ch  the char to count
    * @return the number of occurrences, 0 if the CharSequence is {@code null}
    * @since 3.4
    */
  def countMatches(str: CharSequence, ch: Char): Int = {
    if (isEmpty(str)) return 0
    var count = 0
    // We could also call str.toCharArray() for faster look ups but that would generate more garbage.
    for (i <- 0 until str.length) {
      if (ch == str.charAt(i)) count += 1
    }
    count
  }

  /**
    * <p>Counts how many times the substring appears in the larger string.</p>
    *
    * <p>A {@code null} or empty ("") String input returns {@code 0}.</p>
    *
    * <pre>
    * StringUtils.countMatches(null, *)       = 0
    * StringUtils.countMatches("", *)         = 0
    * StringUtils.countMatches("abba", null)  = 0
    * StringUtils.countMatches("abba", "")    = 0
    * StringUtils.countMatches("abba", "a")   = 2
    * StringUtils.countMatches("abba", "ab")  = 1
    * StringUtils.countMatches("abba", "xxx") = 0
    * </pre>
    *
    * @param str the CharSequence to check, may be null
    * @param sub the substring to count, may be null
    * @return the number of occurrences, 0 if either CharSequence is {@code null}
    * @since 3.0 Changed signature from countMatches(String, String) to countMatches(CharSequence, CharSequence)
    */
  def countMatches(str: CharSequence, sub: CharSequence): Int = {
    if (isEmpty(str) || isEmpty(sub)) return 0
    var count = 0
    var idx = 0
    while ({ idx = CharSequenceUtils.indexOf(str, sub, idx); idx } != INDEX_NOT_FOUND) {
      count += 1
      idx += sub.length
    }
    count
  }

  /**
    * <p>Returns either the passed in CharSequence, or if the CharSequence is
    * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
    * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
    * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
    * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
    * StringUtils.defaultIfBlank("", null)      = null
    * </pre>
    *
    * @tparam T         the specific kind of CharSequence
    * @param str        the CharSequence to check, may be null
    * @param defaultStr the default CharSequence to return
    *                   if the input is whitespace, empty ("") or {@code null}, may be null
    * @return the passed in CharSequence, or the default
    * @see StringUtils#defaultString(String, String)
    */
  def defaultIfBlank[T <: CharSequence](str: T, defaultStr: T): T =
    if (isBlank(str)) defaultStr
    else str

  /**
    * <p>Returns either the passed in CharSequence, or if the CharSequence is
    * empty or {@code null}, the value of {@code defaultStr}.</p>
    *
    * <pre>
    * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
    * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
    * StringUtils.defaultIfEmpty(" ", "NULL")   = " "
    * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
    * StringUtils.defaultIfEmpty("", null)      = null
    * </pre>
    *
    * @tparam T         the specific kind of CharSequence
    * @param str        the CharSequence to check, may be null
    * @param defaultStr the default CharSequence to return
    *                   if the input is empty ("") or {@code null}, may be null
    * @return the passed in CharSequence, or the default
    * @see StringUtils#defaultString(String, String)
    */
  def defaultIfEmpty[T <: CharSequence](str: T, defaultStr: T): T =
    if (isEmpty(str)) defaultStr
    else str

  /**
    * <p>Returns either the passed in String,
    * or if the String is {@code null}, an empty String ("").</p>
    *
    * <pre>
    * StringUtils.defaultString(null)  = ""
    * StringUtils.defaultString("")    = ""
    * StringUtils.defaultString("bat") = "bat"
    * </pre>
    *
    * @see ObjectUtils#toString(Object)
    * @see String#valueOf(Object)
    * @param str the String to check, may be null
    * @return the passed in String, or the empty String if it
    *         was {@code null}
    */
  def defaultString(str: String): String = defaultString(str, EMPTY)

  /**
    * <p>Returns either the passed in String, or if the String is
    * {@code null}, the value of {@code defaultStr}.</p>
    *
    * <pre>
    * StringUtils.defaultString(null, "NULL")  = "NULL"
    * StringUtils.defaultString("", "NULL")    = ""
    * StringUtils.defaultString("bat", "NULL") = "bat"
    * </pre>
    *
    * @see ObjectUtils#toString(Object,String)
    * @see String#valueOf(Object)
    * @param str        the String to check, may be null
    * @param defaultStr the default String to return
    *                   if the input is {@code null}, may be null
    * @return the passed in String, or the default if it was {@code null}
    */
  def defaultString(str: String, defaultStr: String): String =
    if (str == null) defaultStr
    else str

  /**
    * <p>Deletes all whitespaces from a String as defined by
    * {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.deleteWhitespace(null)         = null
    * StringUtils.deleteWhitespace("")           = ""
    * StringUtils.deleteWhitespace("abc")        = "abc"
    * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
    * </pre>
    *
    * @param str the String to delete whitespace from, may be null
    * @return the String without whitespaces, {@code null} if null String input
    */
  def deleteWhitespace(str: String): String = {
    if (isEmpty(str)) return str
    val sz = str.length
    val chs = new Array[Char](sz)
    var count = 0
    for (i <- 0 until sz) {
      if (!Character.isWhitespace(str.charAt(i)))
        chs({ count += 1; count - 1 }) = str.charAt(i)
    }

    if (count == sz) str
    else new String(chs, 0, count)
  }

  /**
    * <p>Compares two Strings, and returns the portion where they differ.
    * More precisely, return the remainder of the second String,
    * starting from where it's different from the first. This means that
    * the difference between "abc" and "ab" is the empty String and not "c". </p>
    *
    * <p>For example,
    * {@code difference("i am a machine", "i am a robot") -> "robot"}.</p>
    *
    * <pre>
    * StringUtils.difference(null, null) = null
    * StringUtils.difference("", "") = ""
    * StringUtils.difference("", "abc") = "abc"
    * StringUtils.difference("abc", "") = ""
    * StringUtils.difference("abc", "abc") = ""
    * StringUtils.difference("abc", "ab") = ""
    * StringUtils.difference("ab", "abxyz") = "xyz"
    * StringUtils.difference("abcde", "abxyz") = "xyz"
    * StringUtils.difference("abcde", "xyz") = "xyz"
    * </pre>
    *
    * @param str1 the first String, may be null
    * @param str2 the second String, may be null
    * @return the portion of str2 where it differs from str1; returns the
    *         empty String if they are equal
    * @see #indexOfDifference(CharSequence,CharSequence)
    * @since 2.0
    */
  def difference(str1: String, str2: String): String = {
    if (str1 == null) return str2
    if (str2 == null) return str1
    val at = indexOfDifference(str1, str2)
    if (at == INDEX_NOT_FOUND) return EMPTY
    str2.substring(at)
  }

  /**
    * <p>Check if a CharSequence ends with a specified suffix.</p>
    *
    * <p>{@code null}s are handled without exceptions. Two {@code null}
    * references are considered to be equal. The comparison is case sensitive.</p>
    *
    * <pre>
    * StringUtils.endsWith(null, null)      = true
    * StringUtils.endsWith(null, "def")     = false
    * StringUtils.endsWith("abcdef", null)  = false
    * StringUtils.endsWith("abcdef", "def") = true
    * StringUtils.endsWith("ABCDEF", "def") = false
    * StringUtils.endsWith("ABCDEF", "cde") = false
    * StringUtils.endsWith("ABCDEF", "")    = true
    * </pre>
    *
    * @see java.lang.String#endsWith(String)
    * @param str    the CharSequence to check, may be null
    * @param suffix the suffix to find, may be null
    * @return {@code true} if the CharSequence ends with the suffix, case sensitive, or
    *         both {@code null}
    * @since 2.4
    *        3.0 Changed signature from endsWith(String, String) to endsWith(CharSequence, CharSequence)
    */
  def endsWith(str: CharSequence, suffix: CharSequence): Boolean = endsWith(str, suffix, false)

  /**
    * <p>Check if a CharSequence ends with a specified suffix (optionally case insensitive).</p>
    *
    * @see java.lang.String#endsWith(String)
    * @param str        the CharSequence to check, may be null
    * @param suffix     the suffix to find, may be null
    * @param ignoreCase indicates whether the compare should ignore case
    *                   (case insensitive) or not.
    * @return {@code true} if the CharSequence starts with the prefix or
    *         both {@code null}
    */
  private def endsWith(str: CharSequence, suffix: CharSequence, ignoreCase: Boolean): Boolean = {
    if (str == null || suffix == null) return str eq suffix
    if (suffix.length > str.length) return false
    val strOffset = str.length - suffix.length
    CharSequenceUtils.regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length)
  }

  /**
    * <p>Check if a CharSequence ends with any of the provided case-sensitive suffixes.</p>
    *
    * <pre>
    * StringUtils.endsWithAny(null, null)      = false
    * StringUtils.endsWithAny(null, new String[] {"abc"})  = false
    * StringUtils.endsWithAny("abcxyz", null)     = false
    * StringUtils.endsWithAny("abcxyz", new String[] {""}) = true
    * StringUtils.endsWithAny("abcxyz", new String[] {"xyz"}) = true
    * StringUtils.endsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
    * StringUtils.endsWithAny("abcXYZ", "def", "XYZ") = true
    * StringUtils.endsWithAny("abcXYZ", "def", "xyz") = false
    * </pre>
    *
    * @param sequence      the CharSequence to check, may be null
    * @param searchStrings the case-sensitive CharSequences to find, may be empty or contain {@code null}
    * @see StringUtils#endsWith(CharSequence, CharSequence)
    * @return {@code true} if the input {@code sequence} is {@code null} AND no {@code searchStrings} are provided, or
    *         the input {@code sequence} ends in any of the provided case-sensitive {@code searchStrings}.
    * @since 3.0
    */
  def endsWithAny(sequence: CharSequence, searchStrings: CharSequence*): Boolean =
    endsWithAny(sequence, searchStrings.toArray)

  /**
    * <p>Check if a CharSequence ends with any of the provided case-sensitive suffixes.</p>
    *
    * <pre>
    * StringUtils.endsWithAny(null, null)      = false
    * StringUtils.endsWithAny(null, new String[] {"abc"})  = false
    * StringUtils.endsWithAny("abcxyz", null)     = false
    * StringUtils.endsWithAny("abcxyz", new String[] {""}) = true
    * StringUtils.endsWithAny("abcxyz", new String[] {"xyz"}) = true
    * StringUtils.endsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
    * StringUtils.endsWithAny("abcXYZ", "def", "XYZ") = true
    * StringUtils.endsWithAny("abcXYZ", "def", "xyz") = false
    * </pre>
    *
    * @param sequence      the CharSequence to check, may be null
    * @param searchStrings the case-sensitive CharSequences to find, may be empty or contain {@code null}
    * @see StringUtils#endsWith(CharSequence, CharSequence)
    * @return {@code true} if the input {@code sequence} is {@code null} AND no {@code searchStrings} are provided, or
    *         the input {@code sequence} ends in any of the provided case-sensitive {@code searchStrings}.
    * @since 3.0
    */
  def endsWithAny(sequence: CharSequence, searchStrings: Array[_ <: CharSequence]): Boolean = {
    if (isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) return false
    for (searchString <- searchStrings) {
      if (endsWith(sequence, searchString)) return true
    }
    false
  }

  /**
    * <p>Case insensitive check if a CharSequence ends with a specified suffix.</p>
    *
    * <p>{@code null}s are handled without exceptions. Two {@code null}
    * references are considered to be equal. The comparison is case insensitive.</p>
    *
    * <pre>
    * StringUtils.endsWithIgnoreCase(null, null)      = true
    * StringUtils.endsWithIgnoreCase(null, "def")     = false
    * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
    * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
    * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
    * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
    * </pre>
    *
    * @see java.lang.String#endsWith(String)
    * @param str    the CharSequence to check, may be null
    * @param suffix the suffix to find, may be null
    * @return {@code true} if the CharSequence ends with the suffix, case insensitive, or
    *         both {@code null}
    * @since 2.4
    *        3.0 Changed signature from endsWithIgnoreCase(String, String) to endsWithIgnoreCase(CharSequence, CharSequence)
    */
  def endsWithIgnoreCase(str: CharSequence, suffix: CharSequence): Boolean =
    endsWith(str, suffix, true)

  /**
    * <p>Compares two CharSequences, returning {@code true} if they represent
    * equal sequences of characters.</p>
    *
    * <p>{@code null}s are handled without exceptions. Two {@code null}
    * references are considered to be equal. The comparison is <strong>case sensitive</strong>.</p>
    *
    * <pre>
    * StringUtils.equals(null, null)   = true
    * StringUtils.equals(null, "abc")  = false
    * StringUtils.equals("abc", null)  = false
    * StringUtils.equals("abc", "abc") = true
    * StringUtils.equals("abc", "ABC") = false
    * </pre>
    *
    * @param cs1 the first CharSequence, may be {@code null}
    * @param cs2 the second CharSequence, may be {@code null}
    * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}
    * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
    * @see Object#equals(Object)
    * @see #equalsIgnoreCase(CharSequence, CharSequence)
    */
  def equals(cs1: CharSequence, cs2: CharSequence): Boolean = {
    if (cs1 eq cs2) return true
    if (cs1 == null || cs2 == null) return false
    if (cs1.length != cs2.length) return false
    if (cs1.isInstanceOf[String] && cs2.isInstanceOf[String]) return cs1 == cs2
    // Step-wise comparison
    val length = cs1.length
    for (i <- 0 until length) {
      if (cs1.charAt(i) != cs2.charAt(i)) return false
    }
    true
  }

  /**
    * <p>Compares given {@code string} to a CharSequences vararg of {@code searchStrings},
    * returning {@code true} if the {@code string} is equal to any of the {@code searchStrings}.</p>
    *
    * <pre>
    * StringUtils.equalsAny(null, (CharSequence[]) null) = false
    * StringUtils.equalsAny(null, null, null)    = true
    * StringUtils.equalsAny(null, "abc", "def")  = false
    * StringUtils.equalsAny("abc", null, "def")  = false
    * StringUtils.equalsAny("abc", "abc", "def") = true
    * StringUtils.equalsAny("abc", "ABC", "DEF") = false
    * </pre>
    *
    * @param string        to compare, may be {@code null}.
    * @param searchStrings a vararg of strings, may be {@code null}.
    * @return {@code true} if the string is equal (case-sensitive) to any other element of {@code searchStrings};
    *         {@code false} if {@code searchStrings} is null or contains no matches.
    * @since 3.5
    */
  def equalsAny(string: CharSequence, searchStrings: CharSequence*): Boolean =
    equalsAny(string, searchStrings.toArray)

  /**
    * <p>Compares given {@code string} to a CharSequences vararg of {@code searchStrings},
    * returning {@code true} if the {@code string} is equal to any of the {@code searchStrings}.</p>
    *
    * <pre>
    * StringUtils.equalsAny(null, (CharSequence[]) null) = false
    * StringUtils.equalsAny(null, null, null)    = true
    * StringUtils.equalsAny(null, "abc", "def")  = false
    * StringUtils.equalsAny("abc", null, "def")  = false
    * StringUtils.equalsAny("abc", "abc", "def") = true
    * StringUtils.equalsAny("abc", "ABC", "DEF") = false
    * </pre>
    *
    * @param string        to compare, may be {@code null}.
    * @param searchStrings a vararg of strings, may be {@code null}.
    * @return {@code true} if the string is equal (case-sensitive) to any other element of {@code searchStrings};
    *         {@code false} if {@code searchStrings} is null or contains no matches.
    * @since 3.5
    */
  def equalsAny(string: CharSequence, searchStrings: Array[_ <: CharSequence]): Boolean = {
    if (ArrayUtils.isNotEmpty(searchStrings)) for (next <- searchStrings) {
      if (equals(string, next)) return true
    }
    false
  }

  /**
    * <p>Compares given {@code string} to a CharSequences vararg of {@code searchStrings},
    * returning {@code true} if the {@code string} is equal to any of the {@code searchStrings}, ignoring case.</p>
    *
    * <pre>
    * StringUtils.equalsAnyIgnoreCase(null, (CharSequence[]) null) = false
    * StringUtils.equalsAnyIgnoreCase(null, null, null)    = true
    * StringUtils.equalsAnyIgnoreCase(null, "abc", "def")  = false
    * StringUtils.equalsAnyIgnoreCase("abc", null, "def")  = false
    * StringUtils.equalsAnyIgnoreCase("abc", "abc", "def") = true
    * StringUtils.equalsAnyIgnoreCase("abc", "ABC", "DEF") = true
    * </pre>
    *
    * @param string        to compare, may be {@code null}.
    * @param searchStrings a vararg of strings, may be {@code null}.
    * @return {@code true} if the string is equal (case-insensitive) to any other element of {@code searchStrings};
    *         {@code false} if {@code searchStrings} is null or contains no matches.
    * @since 3.5
    */
  def equalsAnyIgnoreCase(string: CharSequence, searchStrings: CharSequence*): Boolean =
    equalsAnyIgnoreCase(string, searchStrings.toArray)

  /**
    * <p>Compares given {@code string} to a CharSequences vararg of {@code searchStrings},
    * returning {@code true} if the {@code string} is equal to any of the {@code searchStrings}, ignoring case.</p>
    *
    * <pre>
    * StringUtils.equalsAnyIgnoreCase(null, (CharSequence[]) null) = false
    * StringUtils.equalsAnyIgnoreCase(null, null, null)    = true
    * StringUtils.equalsAnyIgnoreCase(null, "abc", "def")  = false
    * StringUtils.equalsAnyIgnoreCase("abc", null, "def")  = false
    * StringUtils.equalsAnyIgnoreCase("abc", "abc", "def") = true
    * StringUtils.equalsAnyIgnoreCase("abc", "ABC", "DEF") = true
    * </pre>
    *
    * @param string        to compare, may be {@code null}.
    * @param searchStrings a vararg of strings, may be {@code null}.
    * @return {@code true} if the string is equal (case-insensitive) to any other element of {@code searchStrings};
    *         {@code false} if {@code searchStrings} is null or contains no matches.
    * @since 3.5
    */
  def equalsAnyIgnoreCase(string: CharSequence, searchStrings: Array[_ <: CharSequence]): Boolean = {
    if (searchStrings != null && ArrayUtils.isNotEmpty(searchStrings)) {
      for {
        next <- searchStrings
        //if isNotEmpty(next)
      } {
        if (equalsIgnoreCase(string, next)) return true
      }
    }
    false
  }

  /**
    * <p>Compares two CharSequences, returning {@code true} if they represent
    * equal sequences of characters, ignoring case.</p>
    *
    * <p>{@code null}s are handled without exceptions. Two {@code null}
    * references are considered equal. The comparison is <strong>case insensitive</strong>.</p>
    *
    * <pre>
    * StringUtils.equalsIgnoreCase(null, null)   = true
    * StringUtils.equalsIgnoreCase(null, "abc")  = false
    * StringUtils.equalsIgnoreCase("abc", null)  = false
    * StringUtils.equalsIgnoreCase("abc", "abc") = true
    * StringUtils.equalsIgnoreCase("abc", "ABC") = true
    * </pre>
    *
    * @param cs1 the first CharSequence, may be {@code null}
    * @param cs2 the second CharSequence, may be {@code null}
    * @return {@code true} if the CharSequences are equal (case-insensitive), or both {@code null}
    * @since 3.0 Changed signature from equalsIgnoreCase(String, String) to equalsIgnoreCase(CharSequence, CharSequence)
    * @see #equals(CharSequence, CharSequence)
    */
  def equalsIgnoreCase(cs1: CharSequence, cs2: CharSequence): Boolean = {
    if (cs1 eq cs2) return true
    if (cs1 == null || cs2 == null) return false
    if (cs1.length != cs2.length) return false
    CharSequenceUtils.regionMatches(cs1, true, 0, cs2, 0, cs1.length)
  }

  /**
    * <p>Returns the first value in the array which is not empty (""),
    * {@code null} or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>If all values are blank or the array is {@code null}
    * or empty then {@code null} is returned.</p>
    *
    * <pre>
    * StringUtils.firstNonBlank(null, null, null)     = null
    * StringUtils.firstNonBlank(null, "", " ")        = null
    * StringUtils.firstNonBlank("abc")                = "abc"
    * StringUtils.firstNonBlank(null, "xyz")          = "xyz"
    * StringUtils.firstNonBlank(null, "", " ", "xyz") = "xyz"
    * StringUtils.firstNonBlank(null, "xyz", "abc")   = "xyz"
    * StringUtils.firstNonBlank()                     = null
    * </pre>
    *
    * @tparam T     the specific kind of CharSequence
    * @param values the values to test, may be {@code null} or empty
    * @return the first value from {@code values} which is not blank,
    *         or {@code null} if there are no non-blank values
    * @since 3.8
    */
  @SafeVarargs def firstNonBlank[T <: CharSequence](values: T*): T = {
    if (values != null) for (v <- values) {
      if (isNotBlank(v)) return v
    }

    null.asInstanceOf[T]
  }

  /**
    * <p>Returns the first value in the array which is not empty (""),
    * {@code null} or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>If all values are blank or the array is {@code null}
    * or empty then {@code null} is returned.</p>
    *
    * <pre>
    * StringUtils.firstNonBlank(null, null, null)     = null
    * StringUtils.firstNonBlank(null, "", " ")        = null
    * StringUtils.firstNonBlank("abc")                = "abc"
    * StringUtils.firstNonBlank(null, "xyz")          = "xyz"
    * StringUtils.firstNonBlank(null, "", " ", "xyz") = "xyz"
    * StringUtils.firstNonBlank(null, "xyz", "abc")   = "xyz"
    * StringUtils.firstNonBlank()                     = null
    * </pre>
    *
    * @tparam T     the specific kind of CharSequence
    * @param values the values to test, may be {@code null} or empty
    * @return the first value from {@code values} which is not blank,
    *         or {@code null} if there are no non-blank values
    * @since 3.8
    */
  def firstNonBlank[T <: CharSequence](values: Array[T]): T = {
    if (ArrayUtils.isNotEmpty(values)) for (v <- values) {
      if (isNotBlank(v)) return v
    }

    null.asInstanceOf[T]
  }

  /**
    * <p>Returns the first value in the array which is not empty.</p>
    *
    * <p>If all values are empty or the array is {@code null}
    * or empty then {@code null} is returned.</p>
    *
    * <pre>
    * StringUtils.firstNonEmpty(null, null, null)   = null
    * StringUtils.firstNonEmpty(null, null, "")     = null
    * StringUtils.firstNonEmpty(null, "", " ")      = " "
    * StringUtils.firstNonEmpty("abc")              = "abc"
    * StringUtils.firstNonEmpty(null, "xyz")        = "xyz"
    * StringUtils.firstNonEmpty("", "xyz")          = "xyz"
    * StringUtils.firstNonEmpty(null, "xyz", "abc") = "xyz"
    * StringUtils.firstNonEmpty()                   = null
    * </pre>
    *
    * @tparam T     the specific kind of CharSequence
    * @param values the values to test, may be {@code null} or empty
    * @return the first value from {@code values} which is not empty,
    *         or {@code null} if there are no non-empty values
    * @since 3.8
    */
  @SafeVarargs def firstNonEmpty[T <: CharSequence](values: T*): T = {
    if (values != null) for (v <- values) {
      if (isNotEmpty(v)) return v
    }
    null.asInstanceOf[T]
  }

  /**
    * <p>Returns the first value in the array which is not empty.</p>
    *
    * <p>If all values are empty or the array is {@code null}
    * or empty then {@code null} is returned.</p>
    *
    * <pre>
    * StringUtils.firstNonEmpty(null, null, null)   = null
    * StringUtils.firstNonEmpty(null, null, "")     = null
    * StringUtils.firstNonEmpty(null, "", " ")      = " "
    * StringUtils.firstNonEmpty("abc")              = "abc"
    * StringUtils.firstNonEmpty(null, "xyz")        = "xyz"
    * StringUtils.firstNonEmpty("", "xyz")          = "xyz"
    * StringUtils.firstNonEmpty(null, "xyz", "abc") = "xyz"
    * StringUtils.firstNonEmpty()                   = null
    * </pre>
    *
    * @tparam T     the specific kind of CharSequence
    * @param values the values to test, may be {@code null} or empty
    * @return the first value from {@code values} which is not empty,
    *         or {@code null} if there are no non-empty values
    * @since 3.8
    */
  def firstNonEmpty[T <: CharSequence](values: Array[T]): T = {
    if (values != null) for (v <- values) {
      if (isNotEmpty(v)) return v
    }
    null.asInstanceOf[T]
  }

  // TODO: @link java.lang.String#getBytes(Charset)
  /**
    * Calls {@code java.lang.String#getBytes(Charset)} in a null-safe manner.
    *
    * @param string  input string
    * @param charset The {@link java.nio.charset.Charset} to encode the {@code String}. If null, then use the default Charset.
    * @return The empty byte[] if {@code string} is null, the result of {@code java.lang.String#getBytes(Charset)} otherwise.
    * @see String#getBytes(Charset)
    * @since 3.10
    */
  def getBytes(string: String, charset: Charset): Array[Byte] =
    if (string == null) ArrayUtils.EMPTY_BYTE_ARRAY
    else string.getBytes(Charsets.toCharset(charset))

  // TODO: @link java.lang.String#getBytes(String)
  /**
    * Calls {@code java.lang.String#getBytes(String)} in a null-safe manner.
    *
    * @param string  input string
    * @param charset The {@link java.nio.charset.Charset} name to encode the {@code String}. If null, then use the default Charset.
    * @return The empty byte[] if {@code string} is null, the result of {@code java.lang.String#getBytes(String)} otherwise.
    * @throws java.io.UnsupportedEncodingException Thrown when the named charset is not supported.
    * @see String#getBytes(String)
    * @since 3.10
    */
  @throws[UnsupportedEncodingException]
  def getBytes(string: String, charset: String): Array[Byte] =
    if (string == null) ArrayUtils.EMPTY_BYTE_ARRAY
    else string.getBytes(Charsets.toCharsetName(charset))

  /**
    * <p>Compares all Strings in an array and returns the initial sequence of
    * characters that is common to all of them.</p>
    *
    * <p>For example,
    * {@code getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -&gt; "i am a "}</p>
    *
    * <pre>
    * StringUtils.getCommonPrefix(null) = ""
    * StringUtils.getCommonPrefix(new String[] {}) = ""
    * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
    * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
    * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
    * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
    * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
    * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
    * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
    * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
    * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
    * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
    * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
    * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
    * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
    * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
    * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
    * </pre>
    *
    * @param strs array of String objects, entries may be null
    * @return the initial sequence of characters that are common to all Strings
    *         in the array; empty String if the array is null, the elements are all null
    *         or if there is no common prefix.
    * @since 2.4
    */
  def getCommonPrefix(strs: String*): String = {
    if (strs.isEmpty) return EMPTY
    val smallestIndexOfDiff: Int = indexOfDifference(strs: _*)

    if (smallestIndexOfDiff == INDEX_NOT_FOUND) { // all strings were identical
      if (strs(0) == null) return EMPTY
      strs(0)
    } else if (smallestIndexOfDiff == 0) { // there were no common initial characters
      EMPTY
    } else { // we found a common initial character sequence
      strs(0).substring(0, smallestIndexOfDiff)
    }
  }

  /**
    * <p>Checks if a String {@code str} contains Unicode digits,
    * if yes then concatenate all the digits in {@code str} and return it as a String.</p>
    *
    * <p>An empty ("") String will be returned if no digits found in {@code str}.</p>
    *
    * <pre>
    * StringUtils.getDigits(null)  = null
    * StringUtils.getDigits("")    = ""
    * StringUtils.getDigits("abc") = ""
    * StringUtils.getDigits("1000$") = "1000"
    * StringUtils.getDigits("1123~45") = "112345"
    * StringUtils.getDigits("(541) 754-3010") = "5417543010"
    * StringUtils.getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
    * </pre>
    *
    * @param str the String to extract digits from, may be null
    * @return String with only digits,
    *         or an empty ("") String if no digits found,
    *         or {@code null} String if {@code str} is null
    * @since 3.6
    */
  def getDigits(str: String): String = {
    if (isEmpty(str)) return str
    val sz = str.length
    val strDigits = new StringBuilder(sz)
    for (i <- 0 until sz) {
      val tempChar = str.charAt(i)
      if (Character.isDigit(tempChar)) strDigits.append(tempChar)
    }
    strDigits.toString
  }

//  /**
//    * <p>Find the Fuzzy Distance which indicates the similarity score between two Strings.</p>
//    *
//    * <p>This string matching algorithm is similar to the algorithms of editors such as Sublime Text,
//    * TextMate, Atom and others. One point is given for every matched character. Subsequent
//    * matches yield two bonus points. A higher score indicates a higher similarity.</p>
//    *
//    * <pre>
//    * StringUtils.getFuzzyDistance(null, null, null)                                    = IllegalArgumentException
//    * StringUtils.getFuzzyDistance("", "", Locale.ENGLISH)                              = 0
//    * StringUtils.getFuzzyDistance("Workshop", "b", Locale.ENGLISH)                     = 0
//    * StringUtils.getFuzzyDistance("Room", "o", Locale.ENGLISH)                         = 1
//    * StringUtils.getFuzzyDistance("Workshop", "w", Locale.ENGLISH)                     = 1
//    * StringUtils.getFuzzyDistance("Workshop", "ws", Locale.ENGLISH)                    = 2
//    * StringUtils.getFuzzyDistance("Workshop", "wo", Locale.ENGLISH)                    = 4
//    * StringUtils.getFuzzyDistance("Apache Software Foundation", "asf", Locale.ENGLISH) = 3
//    * </pre>
//    *
//    * @param term   a full term that should be matched against, must not be null
//    * @param query  the query that will be matched against a term, must not be null
//    * @param locale This string matching logic is case insensitive. A locale is necessary to normalize
//    *               both Strings to lower case.
//    * @return result score
//    * @throws java.lang.IllegalArgumentException if either String input {@code null} or Locale input {@code null}
//    * @since 3.4
//    * @deprecated as of 3.6, use commons-text
//    *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/FuzzyScore.html">
//    *             FuzzyScore</a> instead
//    */
//  @deprecated def getFuzzyDistance(term: CharSequence, query: CharSequence, locale: Locale): Int = {
//    if (term == null || query == null) throw new IllegalArgumentException("Strings must not be null")
//    else if (locale == null) throw new IllegalArgumentException("Locale must not be null")
//    // fuzzy logic is case insensitive. We normalize the Strings to lower
//    // case right from the start. Turning characters to lower case
//    // via Character.toLowerCase(char) is unfortunately insufficient
//    // as it does not accept a locale.
//    val termLowerCase = term.toString.toLowerCase(locale)
//    val queryLowerCase = query.toString.toLowerCase(locale)
//    // the resulting score
//    var score = 0
//    // the position in the term which will be scanned next for potential
//    // query character matches
//    var termIndex = 0
//    // index of the previously matched character in the term
//    var previousMatchingCharacterIndex = Integer.MIN_VALUE
//    for (queryIndex <- 0 until queryLowerCase.length) {
//      val queryChar = queryLowerCase.charAt(queryIndex)
//      var termCharacterMatchFound = false
//
//      while ( {
//        termIndex < termLowerCase.length && !termCharacterMatchFound
//      }) {
//        val termChar = termLowerCase.charAt(termIndex)
//        if (queryChar == termChar) { // simple character matches result in one point
//          score += 1
//          // subsequent character matches further improve
//          // the score.
//          if (previousMatchingCharacterIndex + 1 == termIndex) score += 2
//          previousMatchingCharacterIndex = termIndex
//          // we can leave the nested loop. Every character in the
//          // query can match at most one character in the term.
//          termCharacterMatchFound = true
//        }
//
//        termIndex += 1
//      }
//    }
//    score
//  }
//
  /**
    * <p>Returns either the passed in CharSequence, or if the CharSequence is
    * whitespace, empty ("") or {@code null}, the value supplied by {@code defaultStrSupplier}.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>Caller responsible for thread-safety and exception handling of default value supplier</p>
    *
    * <pre>
    * {@code
    * StringUtils.getIfBlank(null, () -> "NULL")   = "NULL"
    * StringUtils.getIfBlank("", () -> "NULL")     = "NULL"
    * StringUtils.getIfBlank(" ", () -> "NULL")    = "NULL"
    * StringUtils.getIfBlank("bat", () -> "NULL")  = "bat"
    * StringUtils.getIfBlank("", () -> null)       = null
    * StringUtils.getIfBlank("", null)             = null
    * }</pre>
    * @tparam T  the specific kind of CharSequence
    * @param str the CharSequence to check, may be null
    * @param defaultSupplier the supplier of default CharSequence to return
    * if the input is whitespace, empty ("") or {@code null}, may be null
    *
    * @return the passed in CharSequence, or the default
    * @see StringUtils#defaultString(String, String)
    * @since 3.10
    */
  def getIfBlank[T <: CharSequence](str: T, defaultSupplier: Supplier[T]): T =
    if (isBlank(str))
      if (defaultSupplier == null) null.asInstanceOf[T]
      else defaultSupplier.get
    else str

  /**
    * <p>Returns either the passed in CharSequence, or if the CharSequence is
    * empty or {@code null}, the value supplied by {@code defaultStrSupplier}.</p>
    *
    * <p>Caller responsible for thread-safety and exception handling of default value supplier</p>
    *
    * <pre>
    * {@code
    * StringUtils.getIfEmpty(null, () -> "NULL")    = "NULL"
    * StringUtils.getIfEmpty("", () -> "NULL")      = "NULL"
    * StringUtils.getIfEmpty(" ", () -> "NULL")     = " "
    * StringUtils.getIfEmpty("bat", () -> "NULL")   = "bat"
    * StringUtils.getIfEmpty("", () -> null)        = null
    * StringUtils.getIfEmpty("", null)              = null
    * }
    * </pre>
    * @tparam T   the specific kind of CharSequence
    * @param str  the CharSequence to check, may be null
    * @param defaultSupplier  the supplier of default CharSequence to return
    * if the input is empty ("") or {@code null}, may be null
    *
    * @return the passed in CharSequence, or the default
    * @see StringUtils#defaultString(String, String)
    * @since 3.10
    */
  def getIfEmpty[T <: CharSequence](str: T, defaultSupplier: Supplier[T]): T =
    if (isEmpty(str)) {
      if (defaultSupplier == null) null.asInstanceOf[T]
      else defaultSupplier.get
    } else str

//  /**
//    * <p>Find the Jaro Winkler Distance which indicates the similarity score between two Strings.</p>
//    *
//    * <p>The Jaro measure is the weighted sum of percentage of matched characters from each file and transposed characters.
//    * Winkler increased this measure for matching initial characters.</p>
//    *
//    * <p>This implementation is based on the Jaro Winkler similarity algorithm
//    * from <a href="http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance">http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance</a>.</p>
//    *
//    * <pre>
//    * StringUtils.getJaroWinklerDistance(null, null)          = IllegalArgumentException
//    * StringUtils.getJaroWinklerDistance("", "")              = 0.0
//    * StringUtils.getJaroWinklerDistance("", "a")             = 0.0
//    * StringUtils.getJaroWinklerDistance("aaapppp", "")       = 0.0
//    * StringUtils.getJaroWinklerDistance("frog", "fog")       = 0.93
//    * StringUtils.getJaroWinklerDistance("fly", "ant")        = 0.0
//    * StringUtils.getJaroWinklerDistance("elephant", "hippo") = 0.44
//    * StringUtils.getJaroWinklerDistance("hippo", "elephant") = 0.44
//    * StringUtils.getJaroWinklerDistance("hippo", "zzzzzzzz") = 0.0
//    * StringUtils.getJaroWinklerDistance("hello", "hallo")    = 0.88
//    * StringUtils.getJaroWinklerDistance("ABC Corporation", "ABC Corp") = 0.93
//    * StringUtils.getJaroWinklerDistance("D N H Enterprises Inc", "D &amp; H Enterprises, Inc.") = 0.95
//    * StringUtils.getJaroWinklerDistance("My Gym Children's Fitness Center", "My Gym. Childrens Fitness") = 0.92
//    * StringUtils.getJaroWinklerDistance("PENNSYLVANIA", "PENNCISYLVNIA") = 0.88
//    * </pre>
//    *
//    * @param first  the first String, must not be null
//    * @param second the second String, must not be null
//    * @return result distance
//    * @throws java.lang.IllegalArgumentException if either String input {@code null}
//    * @since 3.3
//    * @deprecated as of 3.6, use commons-text
//    *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/JaroWinklerDistance.html">
//    *             JaroWinklerDistance</a> instead
//    */
//  @deprecated def getJaroWinklerDistance(first: CharSequence, second: CharSequence): Double = {
//    val DEFAULT_SCALING_FACTOR = 0.1
//    if (first == null || second == null) throw new IllegalArgumentException("Strings must not be null")
//    val mtp = matches(first, second)
//    val m = mtp(0)
//    if (m == 0) return 0D
//    val j = (m / first.length + m / second.length + (m - mtp(1)) / m) / 3
//    val jw = if (j < 0.7D) j
//    else j + Math.min(DEFAULT_SCALING_FACTOR, 1D / mtp(3)) * mtp(2) * (1D - j)
//    jw * 100.0D.round / 100.0D
//  }
//
//  /**
//    * <p>Find the Levenshtein distance between two Strings.</p>
//    *
//    * <p>This is the number of changes needed to change one String into
//    * another, where each change is a single character modification (deletion,
//    * insertion or substitution).</p>
//    *
//    * <p>The implementation uses a single-dimensional array of length s.length() + 1. See
//    * <a href="http://blog.softwx.net/2014/12/optimizing-levenshtein-algorithm-in-c.html">
//    * http://blog.softwx.net/2014/12/optimizing-levenshtein-algorithm-in-c.html</a> for details.</p>
//    *
//    * <pre>
//    * StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
//    * StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
//    * StringUtils.getLevenshteinDistance("", "")              = 0
//    * StringUtils.getLevenshteinDistance("", "a")             = 1
//    * StringUtils.getLevenshteinDistance("aaapppp", "")       = 7
//    * StringUtils.getLevenshteinDistance("frog", "fog")       = 1
//    * StringUtils.getLevenshteinDistance("fly", "ant")        = 3
//    * StringUtils.getLevenshteinDistance("elephant", "hippo") = 7
//    * StringUtils.getLevenshteinDistance("hippo", "elephant") = 7
//    * StringUtils.getLevenshteinDistance("hippo", "zzzzzzzz") = 8
//    * StringUtils.getLevenshteinDistance("hello", "hallo")    = 1
//    * </pre>
//    *
//    * @param s the first String, must not be null
//    * @param t the second String, must not be null
//    * @return result distance
//    * @throws java.lang.IllegalArgumentException if either String input {@code null}
//    * @since 3.0 Changed signature from getLevenshteinDistance(String, String) to
//    *        getLevenshteinDistance(CharSequence, CharSequence)
//    * @deprecated as of 3.6, use commons-text
//    *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html">
//    *             LevenshteinDistance</a> instead
//    */
//  @deprecated def getLevenshteinDistance(s: CharSequence, t: CharSequence): Int = {
//    if (s == null || t == null) throw new IllegalArgumentException("Strings must not be null")
//    var n = s.length
//    var m = t.length
//    if (n == 0) return m
//    else if (m == 0) return n
//    if (n > m) { // swap the input strings to consume less memory
//      val tmp = s
//      s = t
//      t = tmp
//      n = m
//      m = t.length
//    }
//    val p = new Array[Int](n + 1)
//    // indexes into strings s and t
//    var i = 0 // iterates through s
//    var j = 0 // iterates through t
//    var upper_left = 0
//    var upper = 0
//    var t_j = 0 // jth character of t
//    var cost = 0
//    i = 0
//    while ( {
//      i <= n
//    }) {
//      p(i) = i
//
//      i += 1
//    }
//    j = 1
//    while ( {
//      j <= m
//    }) {
//      upper_left = p(0)
//      t_j = t.charAt(j - 1)
//      p(0) = j
//      i = 1
//      while ( {
//        i <= n
//      }) {
//        upper = p(i)
//        cost = if (s.charAt(i - 1) == t_j) 0
//        else 1
//        // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
//        p(i) = Math.min(Math.min(p(i - 1) + 1, p(i) + 1), upper_left + cost)
//        upper_left = upper
//
//        i += 1
//      }
//
//      j += 1
//    }
//    p(n)
//  }
//
//  /**
//    * <p>Find the Levenshtein distance between two Strings if it's less than or equal to a given
//    * threshold.</p>
//    *
//    * <p>This is the number of changes needed to change one String into
//    * another, where each change is a single character modification (deletion,
//    * insertion or substitution).</p>
//    *
//    * <p>This implementation follows from Algorithms on Strings, Trees and Sequences by Dan Gusfield
//    * and Chas Emerick's implementation of the Levenshtein distance algorithm from
//    * <a href="http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm</a></p>
//    *
//    * <pre>
//    * StringUtils.getLevenshteinDistance(null, *, *)             = IllegalArgumentException
//    * StringUtils.getLevenshteinDistance(*, null, *)             = IllegalArgumentException
//    * StringUtils.getLevenshteinDistance(*, *, -1)               = IllegalArgumentException
//    * StringUtils.getLevenshteinDistance("", "", 0)              = 0
//    * StringUtils.getLevenshteinDistance("aaapppp", "", 8)       = 7
//    * StringUtils.getLevenshteinDistance("aaapppp", "", 7)       = 7
//    * StringUtils.getLevenshteinDistance("aaapppp", "", 6))      = -1
//    * StringUtils.getLevenshteinDistance("elephant", "hippo", 7) = 7
//    * StringUtils.getLevenshteinDistance("elephant", "hippo", 6) = -1
//    * StringUtils.getLevenshteinDistance("hippo", "elephant", 7) = 7
//    * StringUtils.getLevenshteinDistance("hippo", "elephant", 6) = -1
//    * </pre>
//    *
//    * @param s         the first String, must not be null
//    * @param t         the second String, must not be null
//    * @param threshold the target threshold, must not be negative
//    * @return result distance, or {@code -1} if the distance would be greater than the threshold
//    * @throws java.lang.IllegalArgumentException if either String input {@code null} or negative threshold
//    * @deprecated as of 3.6, use commons-text
//    *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/similarity/LevenshteinDistance.html">
//    *             LevenshteinDistance</a> instead
//    */
//  @deprecated def getLevenshteinDistance(s: CharSequence, t: CharSequence, threshold: Int): Int = {
//    if (s == null || t == null) throw new IllegalArgumentException("Strings must not be null")
//    if (threshold < 0) throw new IllegalArgumentException("Threshold must not be negative")
//    /*
//        This implementation only computes the distance if it's less than or equal to the
//        threshold value, returning -1 if it's greater.  The advantage is performance: unbounded
//        distance is O(nm), but a bound of k allows us to reduce it to O(km) time by only
//        computing a diagonal stripe of width 2k + 1 of the cost table.
//        It is also possible to use this to compute the unbounded Levenshtein distance by starting
//        the threshold at 1 and doubling each time until the distance is found; this is O(dm), where
//        d is the distance.
//
//        One subtlety comes from needing to ignore entries on the border of our stripe
//        eg.
//        p[] = |#|#|#|*
//        d[] =  *|#|#|#|
//        We must ignore the entry to the left of the leftmost member
//        We must ignore the entry above the rightmost member
//
//        Another subtlety comes from our stripe running off the matrix if the strings aren't
//        of the same size.  Since string s is always swapped to be the shorter of the two,
//        the stripe will always run off to the upper right instead of the lower left of the matrix.
//
//        As a concrete example, suppose s is of length 5, t is of length 7, and our threshold is 1.
//        In this case we're going to walk a stripe of length 3.  The matrix would look like so:
//
//           1 2 3 4 5
//        1 |#|#| | | |
//        2 |#|#|#| | |
//        3 | |#|#|#| |
//        4 | | |#|#|#|
//        5 | | | |#|#|
//        6 | | | | |#|
//        7 | | | | | |
//
//        Note how the stripe leads off the table as there is no possible way to turn a string of length 5
//        into one of length 7 in edit distance of 1.
//
//        Additionally, this implementation decreases memory usage by using two
//        single-dimensional arrays and swapping them back and forth instead of allocating
//        an entire n by m matrix.  This requires a few minor changes, such as immediately returning
//        when it's detected that the stripe has run off the matrix and initially filling the arrays with
//        large values so that entries we don't compute are ignored.
//
//        See Algorithms on Strings, Trees and Sequences by Dan Gusfield for some discussion.
//         */ var n = s.length // length of s
//    var m = t.length // length of t
//    // if one string is empty, the edit distance is necessarily the length of the other
//    if (n == 0) return if (m <= threshold) m
//    else -1
//    else if (m == 0) return if (n <= threshold) n
//    else -1
//    else if (Math.abs(n - m) > threshold) { // no need to calculate the distance if the length difference is greater than the threshold
//      return -1
//    }
//    if (n > m) { // swap the two strings to consume less memory
//      val tmp = s
//      s = t
//      t = tmp
//      n = m
//      m = t.length
//    }
//    var p = new Array[Int](n + 1) // 'previous' cost array, horizontally
//    var d = new Array[Int](n + 1) // cost array, horizontally
//    var _d = null // placeholder to assist in swapping p and d
//    // fill in starting table values
//    val boundary = Math.min(n, threshold) + 1
//    for (i <- 0 until boundary) {
//      p(i) = i
//    }
//    // these fills ensure that the value above the rightmost entry of our
//    // stripe will be ignored in following loop iterations
//    util.Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE)
//    util.Arrays.fill(d, Integer.MAX_VALUE)
//    // iterates through t
//    for (j <- 1 to m) {
//      val t_j = t.charAt(j - 1)
//      d(0) = j
//      // compute stripe indices, constrain to array size
//      val min = Math.max(1, j - threshold)
//      val max = if (j > Integer.MAX_VALUE - threshold) n
//      else Math.min(n, j + threshold)
//      // the stripe may lead off of the table if s and t are of different sizes
//      if (min > max) return -1
//      // ignore entry left of leftmost
//      if (min > 1) d(min - 1) = Integer.MAX_VALUE
//      // iterates through [min, max] in s
//      for (i <- min to max) {
//        if (s.charAt(i - 1) == t_j) { // diagonally left and up
//          d(i) = p(i - 1)
//        }
//        else { // 1 + minimum of cell to the left, to the top, diagonally left and up
//          d(i) = 1 + Math.min(Math.min(d(i - 1), p(i)), p(i - 1))
//        }
//      }
//      // copy current distance counts to 'previous row' distance counts
//      _d = p
//      p = d
//      d = _d
//    }
//    // if p[n] is greater than the threshold, there's no guarantee on it being the correct
//    // distance
//    if (p(n) <= threshold) return p(n)
//    -1
//  }
//
  /**
    * <p>Finds the first index within a CharSequence, handling {@code null}.
    * This method uses {@link java.lang.String# indexOf ( String, int)} if possible.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.indexOf(null, *)          = -1
    * StringUtils.indexOf(*, null)          = -1
    * StringUtils.indexOf("", "")           = 0
    * StringUtils.indexOf("", *)            = -1 (except when * = "")
    * StringUtils.indexOf("aabaabaa", "a")  = 0
    * StringUtils.indexOf("aabaabaa", "b")  = 2
    * StringUtils.indexOf("aabaabaa", "ab") = 1
    * StringUtils.indexOf("aabaabaa", "")   = 0
    * </pre>
    *
    * @param seq       the CharSequence to check, may be null
    * @param searchSeq the CharSequence to find, may be null
    * @return the first index of the search CharSequence,
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from indexOf(String, String) to indexOf(CharSequence, CharSequence)
    */
  def indexOf(seq: CharSequence, searchSeq: CharSequence): Int = {
    if (seq == null || searchSeq == null) return INDEX_NOT_FOUND
    CharSequenceUtils.indexOf(seq, searchSeq, 0)
  }

  /**
    * <p>Finds the first index within a CharSequence, handling {@code null}.
    * This method uses {@link java.lang.String# indexOf ( String, int)} if possible.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A negative start position is treated as zero.
    * An empty ("") search CharSequence always matches.
    * A start position greater than the string length only matches
    * an empty search CharSequence.</p>
    *
    * <pre>
    * StringUtils.indexOf(null, *, *)          = -1
    * StringUtils.indexOf(*, null, *)          = -1
    * StringUtils.indexOf("", "", 0)           = 0
    * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
    * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
    * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
    * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
    * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
    * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
    * StringUtils.indexOf("aabaabaa", "b", -1) = 2
    * StringUtils.indexOf("aabaabaa", "", 2)   = 2
    * StringUtils.indexOf("abc", "", 9)        = 3
    * </pre>
    *
    * @param seq       the CharSequence to check, may be null
    * @param searchSeq the CharSequence to find, may be null
    * @param startPos  the start position, negative treated as zero
    * @return the first index of the search CharSequence (always &ge; startPos),
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from indexOf(String, String, int) to indexOf(CharSequence, CharSequence, int)
    */
  def indexOf(seq: CharSequence, searchSeq: CharSequence, startPos: Int): Int = {
    if (seq == null || searchSeq == null) return INDEX_NOT_FOUND
    CharSequenceUtils.indexOf(seq, searchSeq, startPos)
  }

  /**
    * Returns the index within {@code seq} of the first occurrence of
    * the specified character. If a character with value
    * {@code searchChar} occurs in the character sequence represented by
    * {@code seq} {@code CharSequence} object, then the index (in Unicode
    * code units) of the first such occurrence is returned. For
    * values of {@code searchChar} in the range from 0 to 0xFFFF
    * (inclusive), this is the smallest value <i>k</i> such that:
    * <blockquote><pre>
    * this.charAt(<i>k</i>) == searchChar
    * </pre></blockquote>
    * is true. For other values of {@code searchChar}, it is the
    * smallest value <i>k</i> such that:
    * <blockquote><pre>
    * this.codePointAt(<i>k</i>) == searchChar
    * </pre></blockquote>
    * is true. In either case, if no such character occurs in {@code seq},
    * then {@code INDEX_NOT_FOUND (-1)} is returned.
    *
    * <p>Furthermore, a {@code null} or empty ("") CharSequence will
    * return {@code INDEX_NOT_FOUND (-1)}.</p>
    *
    * <pre>
    * StringUtils.indexOf(null, *)         = -1
    * StringUtils.indexOf("", *)           = -1
    * StringUtils.indexOf("aabaabaa", 'a') = 0
    * StringUtils.indexOf("aabaabaa", 'b') = 2
    * </pre>
    *
    * @param seq        the CharSequence to check, may be null
    * @param searchChar the character to find
    * @return the first index of the search character,
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from indexOf(String, int) to indexOf(CharSequence, int)
    *        3.6 Updated {@link CharSequenceUtils} call to behave more like {@code String}
    */
  def indexOf(seq: CharSequence, searchChar: Int): Int = {
    if (isEmpty(seq)) return INDEX_NOT_FOUND
    CharSequenceUtils.indexOf(seq, searchChar, 0)
  }

  /**
    *
    * Returns the index within {@code seq} of the first occurrence of the
    * specified character, starting the search at the specified index.
    * <p>
    * If a character with value {@code searchChar} occurs in the
    * character sequence represented by the {@code seq} {@code CharSequence}
    * object at an index no smaller than {@code startPos}, then
    * the index of the first such occurrence is returned. For values
    * of {@code searchChar} in the range from 0 to 0xFFFF (inclusive),
    * this is the smallest value <i>k</i> such that:
    * <blockquote><pre>
    * (this.charAt(<i>k</i>) == searchChar) &amp;&amp; (<i>k</i> &gt;= startPos)
    * </pre></blockquote>
    * is true. For other values of {@code searchChar}, it is the
    * smallest value <i>k</i> such that:
    * <blockquote><pre>
    * (this.codePointAt(<i>k</i>) == searchChar) &amp;&amp; (<i>k</i> &gt;= startPos)
    * </pre></blockquote>
    * is true. In either case, if no such character occurs in {@code seq}
    * at or after position {@code startPos}, then
    * {@code -1} is returned.
    *
    * <p>
    * There is no restriction on the value of {@code startPos}. If it
    * is negative, it has the same effect as if it were zero: this entire
    * string may be searched. If it is greater than the length of this
    * string, it has the same effect as if it were equal to the length of
    * this string: {@code (INDEX_NOT_FOUND) -1} is returned. Furthermore, a
    * {@code null} or empty ("") CharSequence will
    * return {@code (INDEX_NOT_FOUND) -1}.
    *
    * <p>All indices are specified in {@code char} values
    * (Unicode code units).
    *
    * <pre>
    * StringUtils.indexOf(null, *, *)          = -1
    * StringUtils.indexOf("", *, *)            = -1
    * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
    * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
    * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
    * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
    * </pre>
    *
    * @param seq        the CharSequence to check, may be null
    * @param searchChar the character to find
    * @param startPos   the start position, negative treated as zero
    * @return the first index of the search character (always &ge; startPos),
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from indexOf(String, int, int) to indexOf(CharSequence, int, int)
    *        3.6 Updated {@link CharSequenceUtils} call to behave more like {@code String}
    */
  def indexOf(seq: CharSequence, searchChar: Int, startPos: Int): Int = {
    if (isEmpty(seq)) return INDEX_NOT_FOUND
    CharSequenceUtils.indexOf(seq, searchChar, startPos)
  }

  /**
    * <p>Search a CharSequence to find the first index of any
    * character in the given set of characters.</p>
    *
    * <p>A {@code null} String will return {@code -1}.
    * A {@code null} or zero length search array will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.indexOfAny(null, *)                  = -1
    * StringUtils.indexOfAny("", *)                    = -1
    * StringUtils.indexOfAny(*, null)                  = -1
    * StringUtils.indexOfAny(*, [])                    = -1
    * StringUtils.indexOfAny("zzabyycdxx", ['z', 'a']) = 0
    * StringUtils.indexOfAny("zzabyycdxx", ['b', 'y']) = 3
    * StringUtils.indexOfAny("aba", ['z'])             = -1
    * </pre>
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars the chars to search for, may be null
    * @return the index of any of the chars, -1 if no match or null input
    * @since 2.0
    *        3.0 Changed signature from indexOfAny(String, char[]) to indexOfAny(CharSequence, char...)
    */
  def indexOfAny(cs: CharSequence, searchChars: Char*): Int = {
    if (isEmpty(cs) || searchChars.isEmpty) return INDEX_NOT_FOUND
    indexOfAny(cs, searchChars.toArray)
  }

  def indexOfAny(cs: CharSequence, searchChars: Array[Char]): Int = {
    if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) return INDEX_NOT_FOUND
    val csLen = cs.length
    val csLast = csLen - 1
    val searchLen = searchChars.length
    val searchLast = searchLen - 1

    for (i <- 0 until csLen) {
      val ch = cs.charAt(i)
      for (j <- 0 until searchLen) {
        if (searchChars(j) == ch)
          if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) { // ch is a supplementary character
            if (searchChars(j + 1) == cs.charAt(i + 1)) return i
          } else return i
      }
    }

    INDEX_NOT_FOUND
  }

  // IndexOfAny strings

  /**
    * <p>Find the first index of any of a set of potential substrings.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A {@code null} or zero length search array will return {@code -1}.
    * A {@code null} search array entry will be ignored, but a search
    * array containing "" will return {@code 0} if {@code str} is not
    * null. This method uses {@link java.lang.String#indexOf} if possible.</p>
    *
    * <pre>
    * StringUtils.indexOfAny(null, *)                      = -1
    * StringUtils.indexOfAny(*, null)                      = -1
    * StringUtils.indexOfAny(*, [])                        = -1
    * StringUtils.indexOfAny("zzabyycdxx", ["ab", "cd"])   = 2
    * StringUtils.indexOfAny("zzabyycdxx", ["cd", "ab"])   = 2
    * StringUtils.indexOfAny("zzabyycdxx", ["mn", "op"])   = -1
    * StringUtils.indexOfAny("zzabyycdxx", ["zab", "aby"]) = 1
    * StringUtils.indexOfAny("zzabyycdxx", [""])           = 0
    * StringUtils.indexOfAny("", [""])                     = 0
    * StringUtils.indexOfAny("", ["a"])                    = -1
    * </pre>
    *
    * @param str        the CharSequence to check, may be null
    * @param searchStrs the CharSequences to search for, may be null
    * @return the first index of any of the searchStrs in str, -1 if no match
    * @since 3.0 Changed signature from indexOfAny(String, String[]) to indexOfAny(CharSequence, CharSequence...)
    */
  def indexOfAny(str: CharSequence, searchStrs: CharSequence*)(implicit d: DummyImplicit): Int = {
    if (searchStrs.isEmpty) return INDEX_NOT_FOUND
    indexOfAny(str, searchStrs.toArray)
  }

  def indexOfAny(str: CharSequence, searchStrs: Array[_ <: CharSequence]): Int = {
    if (str == null || searchStrs == null) return INDEX_NOT_FOUND
    // String's can't have a MAX_VALUEth index.
    var ret = Int.MaxValue
    var tmp = 0
    for (search <- searchStrs) {
      if (search != null) {
        tmp = CharSequenceUtils.indexOf(str, search, 0)
        if (tmp != INDEX_NOT_FOUND && tmp < ret) ret = tmp
      }
    }

    if (ret == Int.MaxValue) INDEX_NOT_FOUND
    else ret
  }

  /**
    * <p>Search a CharSequence to find the first index of any
    * character in the given set of characters.</p>
    *
    * <p>A {@code null} String will return {@code -1}.
    * A {@code null} search string will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.indexOfAny(null, *)            = -1
    * StringUtils.indexOfAny("", *)              = -1
    * StringUtils.indexOfAny(*, null)            = -1
    * StringUtils.indexOfAny(*, "")              = -1
    * StringUtils.indexOfAny("zzabyycdxx", "za") = 0
    * StringUtils.indexOfAny("zzabyycdxx", "by") = 3
    * StringUtils.indexOfAny("aba", "z")         = -1
    * </pre>
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars the chars to search for, may be null
    * @return the index of any of the chars, -1 if no match or null input
    * @since 2.0
    *        3.0 Changed signature from indexOfAny(String, String) to indexOfAny(CharSequence, String)
    */
  def indexOfAny(cs: CharSequence, searchChars: String): Int = {
    if (isEmpty(cs) || isEmpty(searchChars)) return INDEX_NOT_FOUND
    indexOfAny(cs, searchChars.toCharArray)
  }

  /**
    * <p>Searches a CharSequence to find the first index of any
    * character not in the given set of characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A {@code null} or zero length search array will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.indexOfAnyBut(null, *)                              = -1
    * StringUtils.indexOfAnyBut("", *)                                = -1
    * StringUtils.indexOfAnyBut(*, null)                              = -1
    * StringUtils.indexOfAnyBut(*, [])                                = -1
    * StringUtils.indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
    * StringUtils.indexOfAnyBut("aba", new char[] {'z'} )             = 0
    * StringUtils.indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
    *
    * </pre>
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars the chars to search for, may be null
    * @return the index of any of the chars, -1 if no match or null input
    * @since 2.0
    *        3.0 Changed signature from indexOfAnyBut(String, char[]) to indexOfAnyBut(CharSequence, char...)
    */
  def indexOfAnyBut(cs: CharSequence, searchChars: Char*): Int =
    indexOfAnyBut(cs, searchChars.toArray)

  /**
    * <p>Searches a CharSequence to find the first index of any
    * character not in the given set of characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A {@code null} or zero length search array will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.indexOfAnyBut(null, *)                              = -1
    * StringUtils.indexOfAnyBut("", *)                                = -1
    * StringUtils.indexOfAnyBut(*, null)                              = -1
    * StringUtils.indexOfAnyBut(*, [])                                = -1
    * StringUtils.indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
    * StringUtils.indexOfAnyBut("aba", new char[] {'z'} )             = 0
    * StringUtils.indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
    *
    * </pre>
    *
    * @param cs          the CharSequence to check, may be null
    * @param searchChars the chars to search for, may be null
    * @return the index of any of the chars, -1 if no match or null input
    * @since 2.0
    *        3.0 Changed signature from indexOfAnyBut(String, char[]) to indexOfAnyBut(CharSequence, char...)
    */
  def indexOfAnyBut(cs: CharSequence, searchChars: Array[Char]): Int = {
    if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) return INDEX_NOT_FOUND
    val csLen = cs.length
    val csLast = csLen - 1
    val searchLen = searchChars.length
    val searchLast = searchLen - 1

    for (i <- 0 until csLen) {
      val ch = cs.charAt(i)
      breakable {
        for (j <- 0 until searchLen) {
          if (searchChars(j) == ch)
            if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) {
              if (searchChars(j + 1) == cs.charAt(i + 1)) break()
            } else break()
        }
        return i
      }
    }

    INDEX_NOT_FOUND
  }

  /**
    * <p>Search a CharSequence to find the first index of any
    * character not in the given set of characters.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A {@code null} or empty search string will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.indexOfAnyBut(null, *)            = -1
    * StringUtils.indexOfAnyBut("", *)              = -1
    * StringUtils.indexOfAnyBut(*, null)            = -1
    * StringUtils.indexOfAnyBut(*, "")              = -1
    * StringUtils.indexOfAnyBut("zzabyycdxx", "za") = 3
    * StringUtils.indexOfAnyBut("zzabyycdxx", "")   = -1
    * StringUtils.indexOfAnyBut("aba", "ab")        = -1
    * </pre>
    *
    * @param seq         the CharSequence to check, may be null
    * @param searchChars the chars to search for, may be null
    * @return the index of any of the chars, -1 if no match or null input
    * @since 2.0
    *        3.0 Changed signature from indexOfAnyBut(String, String) to indexOfAnyBut(CharSequence, CharSequence)
    */
  def indexOfAnyBut(seq: CharSequence, searchChars: CharSequence): Int = {
    if (isEmpty(seq) || isEmpty(searchChars)) return INDEX_NOT_FOUND
    val strLen = seq.length
    for (i <- 0 until strLen) {
      val ch = seq.charAt(i)
      val chFound = CharSequenceUtils.indexOf(searchChars, ch.toInt, 0) >= 0
      if (i + 1 < strLen && Character.isHighSurrogate(ch)) {
        val ch2 = seq.charAt(i + 1)
        if (chFound && CharSequenceUtils.indexOf(searchChars, ch2.toInt, 0) < 0) return i
      } else if (!chFound) return i
    }
    INDEX_NOT_FOUND
  }

  /**
    * <p>Compares all CharSequences in an array and returns the index at which the
    * CharSequences begin to differ.</p>
    *
    * <p>For example,
    * {@code indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -> 7}</p>
    *
    * <pre>
    * StringUtils.indexOfDifference(null) = -1
    * StringUtils.indexOfDifference(new String[] {}) = -1
    * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
    * StringUtils.indexOfDifference(new String[] {null, null}) = -1
    * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
    * StringUtils.indexOfDifference(new String[] {"", null}) = 0
    * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
    * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
    * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
    * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
    * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
    * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
    * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
    * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
    * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
    * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
    * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
    * </pre>
    *
    * @param css array of CharSequences, entries may be null
    * @return the index where the strings begin to differ; -1 if they are all equal
    * @since 2.4
    *        3.0 Changed signature from indexOfDifference(String...) to indexOfDifference(CharSequence...)
    */
  def indexOfDifference(css: CharSequence*): Int = {
    if (css == null || css.isEmpty) return INDEX_NOT_FOUND

    var anyStringNull = false
    var allStringsNull = true
    val arrayLen = css.length
    var shortestStrLen: Int = Int.MaxValue
    var longestStrLen = 0

    // find the min and max string lengths; this avoids checking to make
    // sure we are not exceeding the length of the string each time through
    // the bottom loop.
    for (cs <- css) {
      if (cs == null) {
        anyStringNull = true
        shortestStrLen = 0
      } else {
        allStringsNull = false
        shortestStrLen = Math.min(cs.length, shortestStrLen)
        longestStrLen = Math.max(cs.length, longestStrLen)
      }
    }
    // handle lists containing all nulls or all empty strings
    if (allStringsNull || longestStrLen == 0 && !anyStringNull) return INDEX_NOT_FOUND
    // handle lists containing some nulls or some empty strings
    if (shortestStrLen == 0) return 0
    // find the position with the first difference across all strings
    var firstDiff = -1
    var stringPos: Int = 0

    breakable {
      while (stringPos < shortestStrLen) {
        val comparisonChar = css(0).charAt(stringPos)
        var arrayPos: Int = 1

        while (arrayPos < arrayLen) {
          if (css(arrayPos).charAt(stringPos) != comparisonChar) {
            firstDiff = stringPos
            arrayPos = arrayLen // end loop
          } else arrayPos += 1
        }

        if (firstDiff != -1) break()
        else stringPos += 1
      }

    }

    if (firstDiff == -1 && shortestStrLen != longestStrLen) { // we compared all of the characters up to the length of the
      // shortest string and didn't find a match, but the string lengths
      // vary, so return the length of the shortest string.
      return shortestStrLen
    }

    firstDiff
  }

  /**
    * <p>Compares two CharSequences, and returns the index at which the
    * CharSequences begin to differ.</p>
    *
    * <p>For example,
    * {@code indexOfDifference("i am a machine", "i am a robot") -> 7}</p>
    *
    * <pre>
    * StringUtils.indexOfDifference(null, null) = -1
    * StringUtils.indexOfDifference("", "") = -1
    * StringUtils.indexOfDifference("", "abc") = 0
    * StringUtils.indexOfDifference("abc", "") = 0
    * StringUtils.indexOfDifference("abc", "abc") = -1
    * StringUtils.indexOfDifference("ab", "abxyz") = 2
    * StringUtils.indexOfDifference("abcde", "abxyz") = 2
    * StringUtils.indexOfDifference("abcde", "xyz") = 0
    * </pre>
    *
    * @param cs1 the first CharSequence, may be null
    * @param cs2 the second CharSequence, may be null
    * @return the index where cs1 and cs2 begin to differ; -1 if they are equal
    * @since 2.0
    *        3.0 Changed signature from indexOfDifference(String, String) to
    *        indexOfDifference(CharSequence, CharSequence)
    */
  def indexOfDifference(cs1: CharSequence, cs2: CharSequence): Int = {
    if (cs1 eq cs2) return INDEX_NOT_FOUND
    if (cs1 == null || cs2 == null) return 0

    var i = 0
    var done = false
    while (i < cs1.length && i < cs2.length && !done) {
      if (cs1.charAt(i) != cs2.charAt(i)) done = true //todo: break is not supported
      else i += 1
    }

    if (i < cs2.length || i < cs1.length) return i
    INDEX_NOT_FOUND
  }

  /**
    * <p>Case in-sensitive find of the first index within a CharSequence.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A negative start position is treated as zero.
    * An empty ("") search CharSequence always matches.
    * A start position greater than the string length only matches
    * an empty search CharSequence.</p>
    *
    * <pre>
    * StringUtils.indexOfIgnoreCase(null, *)          = -1
    * StringUtils.indexOfIgnoreCase(*, null)          = -1
    * StringUtils.indexOfIgnoreCase("", "")           = 0
    * StringUtils.indexOfIgnoreCase("aabaabaa", "a")  = 0
    * StringUtils.indexOfIgnoreCase("aabaabaa", "b")  = 2
    * StringUtils.indexOfIgnoreCase("aabaabaa", "ab") = 1
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @return the first index of the search CharSequence,
    *         -1 if no match or {@code null} string input
    * @since 2.5
    *        3.0 Changed signature from indexOfIgnoreCase(String, String) to indexOfIgnoreCase(CharSequence, CharSequence)
    */
  def indexOfIgnoreCase(str: CharSequence, searchStr: CharSequence): Int = indexOfIgnoreCase(str, searchStr, 0)

  /**
    * <p>Case in-sensitive find of the first index within a CharSequence
    * from the specified position.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A negative start position is treated as zero.
    * An empty ("") search CharSequence always matches.
    * A start position greater than the string length only matches
    * an empty search CharSequence.</p>
    *
    * <pre>
    * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
    * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
    * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
    * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
    * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
    * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
    * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
    * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
    * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
    * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
    * StringUtils.indexOfIgnoreCase("abc", "", 9)        = -1
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @param startPos  the start position, negative treated as zero
    * @return the first index of the search CharSequence (always &ge; startPos),
    *         -1 if no match or {@code null} string input
    * @since 2.5
    *        3.0 Changed signature from indexOfIgnoreCase(String, String, int) to indexOfIgnoreCase(CharSequence, CharSequence, int)
    */
  def indexOfIgnoreCase(str: CharSequence, searchStr: CharSequence, startPos: Int): Int = {
    if (str == null || searchStr == null) return INDEX_NOT_FOUND
    val startIndex: Int = if (startPos < 0) 0 else startPos

    val endLimit = str.length - searchStr.length + 1
    if (startIndex > endLimit) return INDEX_NOT_FOUND
    if (searchStr.length == 0) return startIndex
    for (i <- startIndex until endLimit) {
      if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length)) return i
    }
    INDEX_NOT_FOUND
  }

  /**
    * <p>Checks if all of the CharSequences are empty (""), null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isAllBlank(null)             = true
    * StringUtils.isAllBlank(null, "foo")      = false
    * StringUtils.isAllBlank(null, null)       = true
    * StringUtils.isAllBlank("", "bar")        = false
    * StringUtils.isAllBlank("bob", "")        = false
    * StringUtils.isAllBlank("  bob  ", null)  = false
    * StringUtils.isAllBlank(" ", "bar")       = false
    * StringUtils.isAllBlank("foo", "bar")     = false
    * StringUtils.isAllBlank(new String[] {})  = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if all of the CharSequences are empty or null or whitespace only
    * @since 3.6
    */
  def isAllBlank(css: CharSequence*): Boolean = {
    if (css == null || css.isEmpty) return true
    for (cs <- css) {
      if (isNotBlank(cs)) return false
    }
    true
  }

  /**
    * <p>Checks if all of the CharSequences are empty (""), null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isAllBlank(null)             = true
    * StringUtils.isAllBlank(null, "foo")      = false
    * StringUtils.isAllBlank(null, null)       = true
    * StringUtils.isAllBlank("", "bar")        = false
    * StringUtils.isAllBlank("bob", "")        = false
    * StringUtils.isAllBlank("  bob  ", null)  = false
    * StringUtils.isAllBlank(" ", "bar")       = false
    * StringUtils.isAllBlank("foo", "bar")     = false
    * StringUtils.isAllBlank(new String[] {})  = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if all of the CharSequences are empty or null or whitespace only
    * @since 3.6
    */
  def isAllBlank(css: Array[_ <: CharSequence]): Boolean = {
    if (ArrayUtils.isEmpty(css)) return true
    for (cs <- css) {
      if (isNotBlank(cs)) return false
    }
    true
  }

  /**
    * <p>Checks if all of the CharSequences are empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isAllEmpty(null)             = true
    * StringUtils.isAllEmpty(null, "")         = true
    * StringUtils.isAllEmpty(new String[] {})  = true
    * StringUtils.isAllEmpty(null, "foo")      = false
    * StringUtils.isAllEmpty("", "bar")        = false
    * StringUtils.isAllEmpty("bob", "")        = false
    * StringUtils.isAllEmpty("  bob  ", null)  = false
    * StringUtils.isAllEmpty(" ", "bar")       = false
    * StringUtils.isAllEmpty("foo", "bar")     = false
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if all of the CharSequences are empty or null
    * @since 3.6
    */
  def isAllEmpty(css: CharSequence*): Boolean = {
    if (css == null || css.isEmpty) return true
    for (cs <- css) {
      if (isNotEmpty(cs)) return false
    }
    true
  }

  /**
    * <p>Checks if all of the CharSequences are empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isAllEmpty(null)             = true
    * StringUtils.isAllEmpty(null, "")         = true
    * StringUtils.isAllEmpty(new String[] {})  = true
    * StringUtils.isAllEmpty(null, "foo")      = false
    * StringUtils.isAllEmpty("", "bar")        = false
    * StringUtils.isAllEmpty("bob", "")        = false
    * StringUtils.isAllEmpty("  bob  ", null)  = false
    * StringUtils.isAllEmpty(" ", "bar")       = false
    * StringUtils.isAllEmpty("foo", "bar")     = false
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if all of the CharSequences are empty or null
    * @since 3.6
    */
  def isAllEmpty(css: Array[_ <: CharSequence]): Boolean = {
    if (ArrayUtils.isEmpty(css)) return true
    for (cs <- css) {
      if (isNotEmpty(cs)) return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only lowercase characters.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.isAllLowerCase(null)   = false
    * StringUtils.isAllLowerCase("")     = false
    * StringUtils.isAllLowerCase("  ")   = false
    * StringUtils.isAllLowerCase("abc")  = true
    * StringUtils.isAllLowerCase("abC")  = false
    * StringUtils.isAllLowerCase("ab c") = false
    * StringUtils.isAllLowerCase("ab1c") = false
    * StringUtils.isAllLowerCase("ab/c") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains lowercase characters, and is non-null
    * @since 2.5
    *        3.0 Changed signature from isAllLowerCase(String) to isAllLowerCase(CharSequence)
    */
  def isAllLowerCase(cs: CharSequence): Boolean = {
    if (isEmpty(cs)) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isLowerCase(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only uppercase characters.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty String (length()=0) will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.isAllUpperCase(null)   = false
    * StringUtils.isAllUpperCase("")     = false
    * StringUtils.isAllUpperCase("  ")   = false
    * StringUtils.isAllUpperCase("ABC")  = true
    * StringUtils.isAllUpperCase("aBC")  = false
    * StringUtils.isAllUpperCase("A C")  = false
    * StringUtils.isAllUpperCase("A1C")  = false
    * StringUtils.isAllUpperCase("A/C")  = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains uppercase characters, and is non-null
    * @since 2.5
    *        3.0 Changed signature from isAllUpperCase(String) to isAllUpperCase(CharSequence)
    */
  def isAllUpperCase(cs: CharSequence): Boolean = {
    if (isEmpty(cs)) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isUpperCase(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only Unicode letters.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.isAlpha(null)   = false
    * StringUtils.isAlpha("")     = false
    * StringUtils.isAlpha("  ")   = false
    * StringUtils.isAlpha("abc")  = true
    * StringUtils.isAlpha("ab2c") = false
    * StringUtils.isAlpha("ab-c") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains letters, and is non-null
    * @since 3.0 Changed signature from isAlpha(String) to isAlpha(CharSequence)
    *        3.0 Changed "" to return false and not true
    */
  def isAlpha(cs: CharSequence): Boolean = {
    if (isEmpty(cs)) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isLetter(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only Unicode letters or digits.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code false}.</p>
    *
    * <pre>
    * StringUtils.isAlphanumeric(null)   = false
    * StringUtils.isAlphanumeric("")     = false
    * StringUtils.isAlphanumeric("  ")   = false
    * StringUtils.isAlphanumeric("abc")  = true
    * StringUtils.isAlphanumeric("ab c") = false
    * StringUtils.isAlphanumeric("ab2c") = true
    * StringUtils.isAlphanumeric("ab-c") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains letters or digits,
    *         and is non-null
    * @since 3.0 Changed signature from isAlphanumeric(String) to isAlphanumeric(CharSequence)
    *        3.0 Changed "" to return false and not true
    */
  def isAlphanumeric(cs: CharSequence): Boolean = {
    if (isEmpty(cs)) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isLetterOrDigit(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only Unicode letters, digits
    * or space ({@code ' '}).</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code true}.</p>
    *
    * <pre>
    * StringUtils.isAlphanumericSpace(null)   = false
    * StringUtils.isAlphanumericSpace("")     = true
    * StringUtils.isAlphanumericSpace("  ")   = true
    * StringUtils.isAlphanumericSpace("abc")  = true
    * StringUtils.isAlphanumericSpace("ab c") = true
    * StringUtils.isAlphanumericSpace("ab2c") = true
    * StringUtils.isAlphanumericSpace("ab-c") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains letters, digits or space,
    *         and is non-null
    * @since 3.0 Changed signature from isAlphanumericSpace(String) to isAlphanumericSpace(CharSequence)
    */
  def isAlphanumericSpace(cs: CharSequence): Boolean = {
    if (cs == null) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only Unicode letters and
    * space (' ').</p>
    *
    * <p>{@code null} will return {@code false}
    * An empty CharSequence (length()=0) will return {@code true}.</p>
    *
    * <pre>
    * StringUtils.isAlphaSpace(null)   = false
    * StringUtils.isAlphaSpace("")     = true
    * StringUtils.isAlphaSpace("  ")   = true
    * StringUtils.isAlphaSpace("abc")  = true
    * StringUtils.isAlphaSpace("ab c") = true
    * StringUtils.isAlphaSpace("ab2c") = false
    * StringUtils.isAlphaSpace("ab-c") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains letters and space,
    *         and is non-null
    * @since 3.0 Changed signature from isAlphaSpace(String) to isAlphaSpace(CharSequence)
    */
  def isAlphaSpace(cs: CharSequence): Boolean = {
    if (cs == null) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') return false
    }
    true
  }

  /**
    * <p>Checks if any of the CharSequences are empty ("") or null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isAnyBlank((String) null)    = true
    * StringUtils.isAnyBlank((String[]) null)  = false
    * StringUtils.isAnyBlank(null, "foo")      = true
    * StringUtils.isAnyBlank(null, null)       = true
    * StringUtils.isAnyBlank("", "bar")        = true
    * StringUtils.isAnyBlank("bob", "")        = true
    * StringUtils.isAnyBlank("  bob  ", null)  = true
    * StringUtils.isAnyBlank(" ", "bar")       = true
    * StringUtils.isAnyBlank(new String[] {})  = false
    * StringUtils.isAnyBlank(new String[]{""}) = true
    * StringUtils.isAnyBlank("foo", "bar")     = false
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if any of the CharSequences are empty or null or whitespace only
    * @since 3.2
    */
  def isAnyBlank(css: CharSequence*): Boolean = {
    if (css == null || css.isEmpty) return false
    for (cs <- css) {
      if (isBlank(cs)) return true
    }
    false
  }

  /**
    * <p>Checks if any of the CharSequences are empty ("") or null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isAnyBlank((String) null)    = true
    * StringUtils.isAnyBlank((String[]) null)  = false
    * StringUtils.isAnyBlank(null, "foo")      = true
    * StringUtils.isAnyBlank(null, null)       = true
    * StringUtils.isAnyBlank("", "bar")        = true
    * StringUtils.isAnyBlank("bob", "")        = true
    * StringUtils.isAnyBlank("  bob  ", null)  = true
    * StringUtils.isAnyBlank(" ", "bar")       = true
    * StringUtils.isAnyBlank(new String[] {})  = false
    * StringUtils.isAnyBlank(new String[]{""}) = true
    * StringUtils.isAnyBlank("foo", "bar")     = false
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if any of the CharSequences are empty or null or whitespace only
    * @since 3.2
    */
  def isAnyBlank(css: Array[_ <: CharSequence]): Boolean = {
    if (ArrayUtils.isEmpty(css)) return false
    for (cs <- css) {
      if (isBlank(cs)) return true
    }
    false
  }

  /**
    * <p>Checks if any of the CharSequences are empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isAnyEmpty((String) null)    = true
    * StringUtils.isAnyEmpty((String[]) null)  = false
    * StringUtils.isAnyEmpty(null, "foo")      = true
    * StringUtils.isAnyEmpty("", "bar")        = true
    * StringUtils.isAnyEmpty("bob", "")        = true
    * StringUtils.isAnyEmpty("  bob  ", null)  = true
    * StringUtils.isAnyEmpty(" ", "bar")       = false
    * StringUtils.isAnyEmpty("foo", "bar")     = false
    * StringUtils.isAnyEmpty(new String[]{})   = false
    * StringUtils.isAnyEmpty(new String[]{""}) = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if any of the CharSequences are empty or null
    * @since 3.2
    */
  def isAnyEmpty(css: CharSequence*): Boolean = {
    if (css == null || css.isEmpty) return false
    for (cs <- css) {
      if (isEmpty(cs)) return true
    }
    false
  }

  /**
    * <p>Checks if any of the CharSequences are empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isAnyEmpty((String) null)    = true
    * StringUtils.isAnyEmpty((String[]) null)  = false
    * StringUtils.isAnyEmpty(null, "foo")      = true
    * StringUtils.isAnyEmpty("", "bar")        = true
    * StringUtils.isAnyEmpty("bob", "")        = true
    * StringUtils.isAnyEmpty("  bob  ", null)  = true
    * StringUtils.isAnyEmpty(" ", "bar")       = false
    * StringUtils.isAnyEmpty("foo", "bar")     = false
    * StringUtils.isAnyEmpty(new String[]{})   = false
    * StringUtils.isAnyEmpty(new String[]{""}) = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if any of the CharSequences are empty or null
    * @since 3.2
    */
  def isAnyEmpty(css: Array[_ <: CharSequence]): Boolean = {
    if (ArrayUtils.isEmpty(css)) return false
    for (cs <- css) {
      if (isEmpty(cs)) return true
    }
    false
  }

  /**
    * <p>Checks if the CharSequence contains only ASCII printable characters.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code true}.</p>
    *
    * <pre>
    * StringUtils.isAsciiPrintable(null)     = false
    * StringUtils.isAsciiPrintable("")       = true
    * StringUtils.isAsciiPrintable(" ")      = true
    * StringUtils.isAsciiPrintable("Ceki")   = true
    * StringUtils.isAsciiPrintable("ab2c")   = true
    * StringUtils.isAsciiPrintable("!ab-c~") = true
    * StringUtils.isAsciiPrintable("\u0020") = true
    * StringUtils.isAsciiPrintable("\u0021") = true
    * StringUtils.isAsciiPrintable("\u007e") = true
    * StringUtils.isAsciiPrintable("\u007f") = false
    * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if every character is in the range
    *         32 thru 126
    * @since 2.1
    *        3.0 Changed signature from isAsciiPrintable(String) to isAsciiPrintable(CharSequence)
    */
  def isAsciiPrintable(cs: CharSequence): Boolean = {
    if (cs == null) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!CharUtils.isAsciiPrintable(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isBlank(null)      = true
    * StringUtils.isBlank("")        = true
    * StringUtils.isBlank(" ")       = true
    * StringUtils.isBlank("bob")     = false
    * StringUtils.isBlank("  bob  ") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if the CharSequence is null, empty or whitespace only
    * @since 2.0
    *        3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
    */
  def isBlank(cs: CharSequence): Boolean = {
    val strLen = length(cs)
    if (strLen == 0) return true
    for (i <- 0 until strLen) {
      if (!Character.isWhitespace(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if a CharSequence is empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isEmpty(null)      = true
    * StringUtils.isEmpty("")        = true
    * StringUtils.isEmpty(" ")       = false
    * StringUtils.isEmpty("bob")     = false
    * StringUtils.isEmpty("  bob  ") = false
    * </pre>
    *
    * <p>NOTE: This method changed in Lang version 2.0.
    * It no longer trims the CharSequence.
    * That functionality is available in isBlank().</p>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if the CharSequence is empty or null
    * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
    */
  def isEmpty(cs: CharSequence): Boolean = cs == null || cs.length == 0

  /**
    * <p>Checks if the CharSequence contains mixed casing of both uppercase and lowercase characters.</p>
    *
    * <p>{@code null} will return {@code false}. An empty CharSequence ({@code length()=0}) will return
    * {@code false}.</p>
    *
    * <pre>
    * StringUtils.isMixedCase(null)    = false
    * StringUtils.isMixedCase("")      = false
    * StringUtils.isMixedCase("ABC")   = false
    * StringUtils.isMixedCase("abc")   = false
    * StringUtils.isMixedCase("aBc")   = true
    * StringUtils.isMixedCase("A c")   = true
    * StringUtils.isMixedCase("A1c")   = true
    * StringUtils.isMixedCase("a/C")   = true
    * StringUtils.isMixedCase("aC\t")  = true
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if the CharSequence contains both uppercase and lowercase characters
    * @since 3.5
    */
  def isMixedCase(cs: CharSequence): Boolean = {
    if (isEmpty(cs) || cs.length == 1) return false
    var containsUppercase = false
    var containsLowercase = false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (containsUppercase && containsLowercase) return true
      else if (Character.isUpperCase(cs.charAt(i))) containsUppercase = true
      else if (Character.isLowerCase(cs.charAt(i))) containsLowercase = true
    }
    containsUppercase && containsLowercase
  }

  /**
    * <p>Checks if none of the CharSequences are empty (""), null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isNoneBlank((String) null)    = false
    * StringUtils.isNoneBlank((String[]) null)  = true
    * StringUtils.isNoneBlank(null, "foo")      = false
    * StringUtils.isNoneBlank(null, null)       = false
    * StringUtils.isNoneBlank("", "bar")        = false
    * StringUtils.isNoneBlank("bob", "")        = false
    * StringUtils.isNoneBlank("  bob  ", null)  = false
    * StringUtils.isNoneBlank(" ", "bar")       = false
    * StringUtils.isNoneBlank(new String[] {})  = true
    * StringUtils.isNoneBlank(new String[]{""}) = false
    * StringUtils.isNoneBlank("foo", "bar")     = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if none of the CharSequences are empty or null or whitespace only
    * @since 3.2
    */
  def isNoneBlank(css: CharSequence*): Boolean = !isAnyBlank(css: _*)

  /**
    * <p>Checks if none of the CharSequences are empty (""), null or whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isNoneBlank((String) null)    = false
    * StringUtils.isNoneBlank((String[]) null)  = true
    * StringUtils.isNoneBlank(null, "foo")      = false
    * StringUtils.isNoneBlank(null, null)       = false
    * StringUtils.isNoneBlank("", "bar")        = false
    * StringUtils.isNoneBlank("bob", "")        = false
    * StringUtils.isNoneBlank("  bob  ", null)  = false
    * StringUtils.isNoneBlank(" ", "bar")       = false
    * StringUtils.isNoneBlank(new String[] {})  = true
    * StringUtils.isNoneBlank(new String[]{""}) = false
    * StringUtils.isNoneBlank("foo", "bar")     = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if none of the CharSequences are empty or null or whitespace only
    * @since 3.2
    */
  def isNoneBlank(css: Array[_ <: CharSequence]): Boolean = !isAnyBlank(css)

  /**
    * <p>Checks if none of the CharSequences are empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isNoneEmpty((String) null)    = false
    * StringUtils.isNoneEmpty((String[]) null)  = true
    * StringUtils.isNoneEmpty(null, "foo")      = false
    * StringUtils.isNoneEmpty("", "bar")        = false
    * StringUtils.isNoneEmpty("bob", "")        = false
    * StringUtils.isNoneEmpty("  bob  ", null)  = false
    * StringUtils.isNoneEmpty(new String[] {})  = true
    * StringUtils.isNoneEmpty(new String[]{""}) = false
    * StringUtils.isNoneEmpty(" ", "bar")       = true
    * StringUtils.isNoneEmpty("foo", "bar")     = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if none of the CharSequences are empty or null
    * @since 3.2
    */
  def isNoneEmpty(css: CharSequence*): Boolean = !isAnyEmpty(css: _*)

  /**
    * <p>Checks if none of the CharSequences are empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isNoneEmpty((String) null)    = false
    * StringUtils.isNoneEmpty((String[]) null)  = true
    * StringUtils.isNoneEmpty(null, "foo")      = false
    * StringUtils.isNoneEmpty("", "bar")        = false
    * StringUtils.isNoneEmpty("bob", "")        = false
    * StringUtils.isNoneEmpty("  bob  ", null)  = false
    * StringUtils.isNoneEmpty(new String[] {})  = true
    * StringUtils.isNoneEmpty(new String[]{""}) = false
    * StringUtils.isNoneEmpty(" ", "bar")       = true
    * StringUtils.isNoneEmpty("foo", "bar")     = true
    * </pre>
    *
    * @param css the CharSequences to check, may be null or empty
    * @return {@code true} if none of the CharSequences are empty or null
    * @since 3.2
    */
  def isNoneEmpty(css: Array[_ <: CharSequence]): Boolean = !isAnyEmpty(css)

  /**
    * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.isNotBlank(null)      = false
    * StringUtils.isNotBlank("")        = false
    * StringUtils.isNotBlank(" ")       = false
    * StringUtils.isNotBlank("bob")     = true
    * StringUtils.isNotBlank("  bob  ") = true
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if the CharSequence is
    *         not empty and not null and not whitespace only
    * @since 2.0
    *        3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
    */
  def isNotBlank(cs: CharSequence): Boolean = !isBlank(cs)

  /**
    * <p>Checks if a CharSequence is not empty ("") and not null.</p>
    *
    * <pre>
    * StringUtils.isNotEmpty(null)      = false
    * StringUtils.isNotEmpty("")        = false
    * StringUtils.isNotEmpty(" ")       = true
    * StringUtils.isNotEmpty("bob")     = true
    * StringUtils.isNotEmpty("  bob  ") = true
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if the CharSequence is not empty and not null
    * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
    */
  def isNotEmpty(cs: CharSequence): Boolean = !isEmpty(cs)

  /**
    * <p>Checks if the CharSequence contains only Unicode digits.
    * A decimal point is not a Unicode digit and returns false.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code false}.</p>
    *
    * <p>Note that the method does not allow for a leading sign, either positive or negative.
    * Also, if a String passes the numeric test, it may still generate a NumberFormatException
    * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
    * for int or long respectively.</p>
    *
    * <pre>
    * StringUtils.isNumeric(null)   = false
    * StringUtils.isNumeric("")     = false
    * StringUtils.isNumeric("  ")   = false
    * StringUtils.isNumeric("123")  = true
    * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
    * StringUtils.isNumeric("12 3") = false
    * StringUtils.isNumeric("ab2c") = false
    * StringUtils.isNumeric("12-3") = false
    * StringUtils.isNumeric("12.3") = false
    * StringUtils.isNumeric("-123") = false
    * StringUtils.isNumeric("+123") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains digits, and is non-null
    * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
    *        3.0 Changed "" to return false and not true
    */
  def isNumeric(cs: CharSequence): Boolean = {
    if (isEmpty(cs)) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isDigit(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only Unicode digits or space
    * ({@code ' '}).
    * A decimal point is not a Unicode digit and returns false.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code true}.</p>
    *
    * <pre>
    * StringUtils.isNumericSpace(null)   = false
    * StringUtils.isNumericSpace("")     = true
    * StringUtils.isNumericSpace("  ")   = true
    * StringUtils.isNumericSpace("123")  = true
    * StringUtils.isNumericSpace("12 3") = true
    * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
    * StringUtils.isNumeric("\u0967\u0968 \u0969")  = true
    * StringUtils.isNumericSpace("ab2c") = false
    * StringUtils.isNumericSpace("12-3") = false
    * StringUtils.isNumericSpace("12.3") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains digits or space,
    *         and is non-null
    * @since 3.0 Changed signature from isNumericSpace(String) to isNumericSpace(CharSequence)
    */
  def isNumericSpace(cs: CharSequence): Boolean = {
    if (cs == null) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') return false
    }
    true
  }

  /**
    * <p>Checks if the CharSequence contains only whitespace.</p>
    *
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>{@code null} will return {@code false}.
    * An empty CharSequence (length()=0) will return {@code true}.</p>
    *
    * <pre>
    * StringUtils.isWhitespace(null)   = false
    * StringUtils.isWhitespace("")     = true
    * StringUtils.isWhitespace("  ")   = true
    * StringUtils.isWhitespace("abc")  = false
    * StringUtils.isWhitespace("ab2c") = false
    * StringUtils.isWhitespace("ab-c") = false
    * </pre>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if only contains whitespace, and is non-null
    * @since 2.0
    *        3.0 Changed signature from isWhitespace(String) to isWhitespace(CharSequence)
    */
  def isWhitespace(cs: CharSequence): Boolean = {
    if (cs == null) return false
    val sz = cs.length
    for (i <- 0 until sz) {
      if (!Character.isWhitespace(cs.charAt(i))) return false
    }
    true
  }

  /**
    * <p>
    * Joins the elements of the provided array into a single String containing the provided list of elements.
    * </p>
    *
    * <p>
    * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
    * by empty strings.
    * </p>
    *
    * <pre>
    * StringUtils.join(null, *)               = null
    * StringUtils.join([], *)                 = ""
    * StringUtils.join([null], *)             = ""
    * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
    * StringUtils.join([1, 2, 3], null) = "123"
    * </pre>
    *
    * @param array
    * the array of values to join together, may be null
    * @param separator
    * the separator character to use
    * @return the joined String, {@code null} if null array input
    * @since 3.2
    */
  def join(array: Array[Byte], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  /**
    * <p>
    * Joins the elements of the provided array into a single String containing the provided list of elements.
    * </p>
    *
    * <p>
    * No delimiter is added before or after the list. Null objects or empty strings within the array are represented
    * by empty strings.
    * </p>
    *
    * <pre>
    * StringUtils.join(null, *)               = null
    * StringUtils.join([], *)                 = ""
    * StringUtils.join([null], *)             = ""
    * StringUtils.join([1, 2, 3], ';')  = "1;2;3"
    * StringUtils.join([1, 2, 3], null) = "123"
    * </pre>
    *
    * @param array
    * the array of values to join together, may be null
    * @param separator
    * the separator character to use
    * @param startIndex
    * the first index to start joining from. It is an error to pass in a start index past the end of the
    * array
    * @param endIndex
    * the index to stop joining from (exclusive). It is an error to pass in an end index past the end of
    * the array
    * @return the joined String, {@code null} if null array input
    * @since 3.2
    */
  def join(array: Array[Byte], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }
    buf.toString
  }

  def join(array: Array[Char], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  def join(array: Array[Char], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }
    buf.toString
  }

  def join(array: Array[Double], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  def join(array: Array[Double], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }
    buf.toString
  }

  def join(array: Array[Float], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  def join(array: Array[Float], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }
    buf.toString
  }

  def join(array: Array[Int], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  def join(array: Array[Int], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }
    buf.toString
  }

  /**
    * <p>Joins the elements of the provided {@code Iterable} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list. Null objects or empty
    * strings within the iteration are represented by empty strings.</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:Char)*}. </p>
    *
    * @param iterable  the {@code Iterable} providing the values to join together, may be null
    * @param separator the separator character to use
    * @return the joined String, {@code null} if null iterator input
    * @since 2.3
    */
  def join(iterable: Iterable[_], separator: Char): String = {
    if (iterable == null) return null
    join(iterable.iterator, separator)
  }

  /**
    * <p>Joins the elements of the provided {@code Iterable} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list. Null objects or empty
    * strings within the iteration are represented by empty strings.</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:Char)*}. </p>
    *
    * @param iterable  the {@code Iterable} providing the values to join together, may be null
    * @param separator the separator character to use
    * @return the joined String, {@code null} if null iterator input
    * @since 2.3
    */
  def join(iterable: java.lang.Iterable[_], separator: Char): String = {
    if (iterable == null) return null
    join(iterable.iterator, separator)
  }

  /**
    * <p>Joins the elements of the provided {@code Iterable} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * A {@code null} separator is the same as an empty String ("").</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:String)*}. </p>
    *
    * @param iterable  the {@code Iterable} providing the values to join together, may be null
    * @param separator the separator character to use, null treated as ""
    * @return the joined String, {@code null} if null iterator input
    * @since 2.3
    */
  def join(iterable: Iterable[_], separator: String): String = {
    if (iterable == null) return null
    join(iterable.iterator, separator)
  }

  /**
    * <p>Joins the elements of the provided {@code Iterable} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * A {@code null} separator is the same as an empty String ("").</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:String)*}. </p>
    *
    * @param iterable  the {@code Iterable} providing the values to join together, may be null
    * @param separator the separator character to use, null treated as ""
    * @return the joined String, {@code null} if null iterator input
    * @since 2.3
    */
  def join(iterable: java.lang.Iterable[_], separator: String): String = {
    if (iterable == null) return null
    join(iterable.iterator, separator)
  }

  /**
    * <p>Joins the elements of the provided {@code Iterator} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list. Null objects or empty
    * strings within the iteration are represented by empty strings.</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:Char)*}. </p>
    *
    * @param iterator  the {@code Iterator} of values to join together, may be null
    * @param separator the separator character to use
    * @return the joined String, {@code null} if null iterator input
    * @since 2.0
    */
  def join(iterator: Iterator[_], separator: Char): String = {
    // handle null, zero and one elements before building a buffer
    if (iterator == null) return null
    if (!iterator.hasNext) return EMPTY
    val first = iterator.next
    if (!iterator.hasNext) return Objects.toString(first, EMPTY)
    // two or more elements
    val buf = new StringBuilder(STRING_BUILDER_SIZE) // Java default is 16, probably too small
    if (first != null) buf.append(first)
    while ({
      iterator.hasNext
    }) {
      buf.append(separator)
      val obj = iterator.next
      if (obj != null) buf.append(obj)
    }
    buf.toString
  }

  /**
    * <p>Joins the elements of the provided {@code Iterator} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list. Null objects or empty
    * strings within the iteration are represented by empty strings.</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:Char)*}. </p>
    *
    * @param iterator  the {@code Iterator} of values to join together, may be null
    * @param separator the separator character to use
    * @return the joined String, {@code null} if null iterator input
    * @since 2.0
    */
  def join(iterator: java.util.Iterator[_], separator: Char): String = {
    // handle null, zero and one elements before building a buffer
    if (iterator == null) return null
    if (!iterator.hasNext) return EMPTY
    val first = iterator.next
    if (!iterator.hasNext) return Objects.toString(first, EMPTY)
    // two or more elements
    val buf = new StringBuilder(STRING_BUILDER_SIZE) // Java default is 16, probably too small
    if (first != null) buf.append(first)
    while ({
      iterator.hasNext
    }) {
      buf.append(separator)
      val obj = iterator.next
      if (obj != null) buf.append(obj)
    }
    buf.toString
  }

  /**
    * <p>Joins the elements of the provided {@code Iterator} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * A {@code null} separator is the same as an empty String ("").</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:String)*}. </p>
    *
    * @param iterator  the {@code Iterator} of values to join together, may be null
    * @param separator the separator character to use, null treated as ""
    * @return the joined String, {@code null} if null iterator input
    */
  def join(iterator: Iterator[_], separator: String): String = {
    if (iterator == null) return null
    if (!iterator.hasNext) return EMPTY

    val first = iterator.next
    if (!iterator.hasNext) return Objects.toString(first, "")
    val buf = new StringBuilder(STRING_BUILDER_SIZE)
    if (first != null) buf.append(first)

    while (iterator.hasNext) {
      if (separator != null) buf.append(separator)
      val obj = iterator.next
      if (obj != null) buf.append(obj)
    }

    buf.toString
  }

  /**
    * <p>Joins the elements of the provided {@code Iterator} into
    * a single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * A {@code null} separator is the same as an empty String ("").</p>
    *
    * <p>See the examples here: {@link #join(array:Array[_],separator:String)*}. </p>
    *
    * @param iterator  the {@code Iterator} of values to join together, may be null
    * @param separator the separator character to use, null treated as ""
    * @return the joined String, {@code null} if null iterator input
    */
  def join(iterator: java.util.Iterator[_], separator: String): String = {
    if (iterator == null) return null
    if (!iterator.hasNext) return EMPTY

    val first = iterator.next
    if (!iterator.hasNext) return Objects.toString(first, "")
    val buf = new StringBuilder(STRING_BUILDER_SIZE)
    if (first != null) buf.append(first)

    while (iterator.hasNext) {
      if (separator != null) buf.append(separator)
      val obj = iterator.next
      if (obj != null) buf.append(obj)
    }

    buf.toString
  }

  /**
    * <p>Joins the elements of the provided {@code List} into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null, *)               = null
    * StringUtils.join([], *)                 = ""
    * StringUtils.join([null], *)             = ""
    * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
    * StringUtils.join(["a", "b", "c"], null) = "abc"
    * StringUtils.join([null, "", "a"], ';')  = ";;a"
    * </pre>
    *
    * @param list       the {@code List} of values to join together, may be null
    * @param separator  the separator character to use
    * @param startIndex the first index to start joining from.  It is
    *                   an error to pass in a start index past the end of the list
    * @param endIndex   the index to stop joining from (exclusive). It is
    *                   an error to pass in an end index past the end of the list
    * @return the joined String, {@code null} if null list input
    * @since 3.8
    */
  def join(list: util.List[_], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (list == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val subList = list.subList(startIndex, endIndex)
    join(subList.iterator, separator)
  }

  def join(list: util.List[_], separator: String, startIndex: Int, endIndex: Int): String = {
    if (list == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val subList = list.subList(startIndex, endIndex)
    join(subList.iterator, separator)
  }

  /**
    * <p>Joins the elements of the provided {@code List} into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null, *)               = null
    * StringUtils.join([], *)                 = ""
    * StringUtils.join([null], *)             = ""
    * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
    * StringUtils.join(["a", "b", "c"], null) = "abc"
    * StringUtils.join([null, "", "a"], ';')  = ";;a"
    * </pre>
    *
    * @param list       the {@code List} of values to join together, may be null
    * @param separator  the separator character to use
    * @param startIndex the first index to start joining from.  It is
    *                   an error to pass in a start index past the end of the list
    * @param endIndex   the index to stop joining from (exclusive). It is
    *                   an error to pass in an end index past the end of the list
    * @return the joined String, {@code null} if null list input
    * @since 3.8
    */
  def join(list: List[_], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (list == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val subList = list.slice(startIndex, endIndex)
    join(subList.iterator, separator)
  }

  def join(list: List[_], separator: String, startIndex: Int, endIndex: Int): String = {
    if (list == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val subList = list.slice(startIndex, endIndex)
    join(subList.iterator, separator)
  }

  def join(array: Array[Long], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  def join(array: Array[Long], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }
    buf.toString
  }

  /**
    * <p>Joins the elements of the provided array into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null, *)               = null
    * StringUtils.join([], *)                 = ""
    * StringUtils.join([null], *)             = ""
    * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
    * StringUtils.join(["a", "b", "c"], null) = "abc"
    * StringUtils.join([null, "", "a"], ';')  = ";;a"
    * </pre>
    *
    * @param array     the array of values to join together, may be null
    * @param separator the separator character to use
    * @return the joined String, {@code null} if null array input
    * @since 2.0
    */
  def join(array: Array[_], separator: Char): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  /**
    * <p>Joins the elements of the provided array into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null, *)               = null
    * StringUtils.join([], *)                 = ""
    * StringUtils.join([null], *)             = ""
    * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
    * StringUtils.join(["a", "b", "c"], null) = "abc"
    * StringUtils.join([null, "", "a"], ';')  = ";;a"
    * </pre>
    *
    * @param array      the array of values to join together, may be null
    * @param separator  the separator character to use
    * @param startIndex the first index to start joining from.  It is
    *                   an error to pass in a start index past the end of the array
    * @param endIndex   the index to stop joining from (exclusive). It is
    *                   an error to pass in an end index past the end of the array
    * @return the joined String, {@code null} if null array input
    * @since 2.0
    */
  def join[T](array: Array[T], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY
    val buf = newStringBuilder(noOfItems)
    if (array(startIndex) != null) buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      if (array(i) != null) buf.append(array(i))
    }
    buf.toString
  }

  /**
    * <p>Joins the elements of the provided array into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * A {@code null} separator is the same as an empty String ("").
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null, *)                = null
    * StringUtils.join([], *)                  = ""
    * StringUtils.join([null], *)              = ""
    * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
    * StringUtils.join(["a", "b", "c"], null)  = "abc"
    * StringUtils.join(["a", "b", "c"], "")    = "abc"
    * StringUtils.join([null, "", "a"], ',')   = ",,a"
    * </pre>
    *
    *
    * @param array     the array of values to join together, may be null
    * @param separator the separator character to use, null treated as ""
    * @return the joined String, {@code null} if null array input
    */
  def join(array: Array[_], separator: String): String = {
    if (array == null) return null
    join(array, separator, 0, array.length)
  }

  /**
    * <p>Joins the elements of the provided array into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * A {@code null} separator is the same as an empty String ("").
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null, *, *, *)                = null
    * StringUtils.join([], *, *, *)                  = ""
    * StringUtils.join([null], *, *, *)              = ""
    * StringUtils.join(["a", "b", "c"], "--", 0, 3)  = "a--b--c"
    * StringUtils.join(["a", "b", "c"], "--", 1, 3)  = "b--c"
    * StringUtils.join(["a", "b", "c"], "--", 2, 3)  = "c"
    * StringUtils.join(["a", "b", "c"], "--", 2, 2)  = ""
    * StringUtils.join(["a", "b", "c"], null, 0, 3)  = "abc"
    * StringUtils.join(["a", "b", "c"], "", 0, 3)    = "abc"
    * StringUtils.join([null, "", "a"], ',', 0, 3)   = ",,a"
    * </pre>
    *
    *
    * @param array      the array of values to join together, may be null
    * @param separator  the separator character to use, null treated as ""
    * @param startIndex the first index to start joining from.
    * @param endIndex   the index to stop joining from (exclusive).
    * @return the joined String, {@code null} if null array input; or the empty string
    *         if {@code endIndex - startIndex <= 0}. The number of joined entries is given by
    *         {@code endIndex - startIndex}
    * @throws java.lang.ArrayIndexOutOfBoundsException ife<br>
    *                                        {@code startIndex < 0} or <br>
    *                                        {@code startIndex >= array.length()} or <br>
    *                                        {@code endIndex < 0} or <br>
    *                                        {@code endIndex > array.length()}
    */
  def join[T](array: Array[T], separator: String, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null

    val newSeparator: String = if (separator == null) EMPTY else separator

    // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(newSeparator))
    //           (Assuming that all Strings are roughly equally long)
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY

    val buf = newStringBuilder(noOfItems)
    if (array(startIndex) != null) buf.append(array(startIndex))

    for (i <- startIndex + 1 until endIndex) {
      buf.append(newSeparator)
      if (array(i) != null) buf.append(array(i))
    }

    buf.toString
  }

  def join(array: Array[Short], separator: Char): String =
    if (array == null) null
    else join(array, separator, 0, array.length)

  def join(array: Array[Short], separator: Char, startIndex: Int, endIndex: Int): String = {
    if (array == null) return null
    val noOfItems = endIndex - startIndex
    if (noOfItems <= 0) return EMPTY

    val buf = newStringBuilder(noOfItems)
    buf.append(array(startIndex))
    for (i <- startIndex + 1 until endIndex) {
      buf.append(separator)
      buf.append(array(i))
    }

    buf.toString
  }

  /**
    * <p>Joins the elements of the provided array into a single String
    * containing the provided list of elements.</p>
    *
    * <p>No separator is added to the joined String.
    * Null objects or empty strings within the array are represented by
    * empty strings.</p>
    *
    * <pre>
    * StringUtils.join(null)            = null
    * StringUtils.join([])              = ""
    * StringUtils.join([null])          = ""
    * StringUtils.join(["a", "b", "c"]) = "abc"
    * StringUtils.join([null, "", "a"]) = "a"
    * </pre>
    *
    * @tparam T       the specific type of values to join together
    * @param elements the values to join together, may be null
    * @return the joined String, {@code null} if null array input
    * @since 2.0
    *        3.0 Changed signature to use varargs
    */
  @SafeVarargs
  def join[T](elements: T*): String = join(elements, null)

  def join[T](elements: Array[T]): String = join(elements, null)

  /**
    * <p>Joins the elements of the provided varargs into a
    * single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * {@code null} elements and separator are treated as empty Strings ("").</p>
    *
    * <pre>
    * StringUtils.joinWith(",", {"a", "b"})        = "a,b"
    * StringUtils.joinWith(",", {"a", "b",""})     = "a,b,"
    * StringUtils.joinWith(",", {"a", null, "b"})  = "a,,b"
    * StringUtils.joinWith(null, {"a", "b"})       = "ab"
    * </pre>
    *
    *
    * @param separator the separator character to use, null treated as ""
    * @param objects   the varargs providing the values to join together. {@code null} elements are treated as ""
    * @return the joined String.
    * @throws java.lang.IllegalArgumentException if a null varargs is provided
    * @since 3.5
    */
  def joinWith[T](separator: String, objects: Array[T]): String = {
    if (objects == null) throw new IllegalArgumentException("Object varargs must not be null")
    if (ArrayUtils.isEmpty(objects)) return ""

    val sanitizedSeparator = defaultString(separator)
    val result = new StringBuilder
    val iterator = objects.iterator

    while (iterator.hasNext) {
      val value = Objects.toString(iterator.next, "")
      result.append(value)
      if (iterator.hasNext) result.append(sanitizedSeparator)
    }
    result.toString
  }

  /**
    * <p>Joins the elements of the provided varargs into a
    * single String containing the provided elements.</p>
    *
    * <p>No delimiter is added before or after the list.
    * {@code null} elements and separator are treated as empty Strings ("").</p>
    *
    * <pre>
    * StringUtils.joinWith(",", {"a", "b"})        = "a,b"
    * StringUtils.joinWith(",", {"a", "b",""})     = "a,b,"
    * StringUtils.joinWith(",", {"a", null, "b"})  = "a,,b"
    * StringUtils.joinWith(null, {"a", "b"})       = "ab"
    * </pre>
    *
    *
    * @param separator the separator character to use, null treated as ""
    * @param objects   the varargs providing the values to join together. {@code null} elements are treated as ""
    * @return the joined String.
    * @throws java.lang.IllegalArgumentException if a null varargs is provided
    * @since 3.5
    */
  def joinWith[T](separator: String, objects: T*): String = {
    if (objects == null) throw new IllegalArgumentException("Object varargs must not be null")
    if (objects.isEmpty) return ""

    val sanitizedSeparator = defaultString(separator)
    val result = new StringBuilder
    val iterator =
      if (objects.size == 1 && objects.head.getClass.isArray) util.Arrays.asList(objects).iterator.asScala
      else objects.iterator

    while (iterator.hasNext) {
      val value = Objects.toString(iterator.next, "")
      result.append(value)
      if (iterator.hasNext) result.append(sanitizedSeparator)
    }
    result.toString
  }

  /**
    * <p>Finds the last index within a CharSequence, handling {@code null}.
    * This method uses {@link java.lang.String# lastIndexOf ( String )} if possible.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.lastIndexOf(null, *)          = -1
    * StringUtils.lastIndexOf(*, null)          = -1
    * StringUtils.lastIndexOf("", "")           = 0
    * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
    * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
    * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
    * StringUtils.lastIndexOf("aabaabaa", "")   = 8
    * </pre>
    *
    * @param seq       the CharSequence to check, may be null
    * @param searchSeq the CharSequence to find, may be null
    * @return the last index of the search String,
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from lastIndexOf(String, String) to lastIndexOf(CharSequence, CharSequence)
    */
  def lastIndexOf(seq: CharSequence, searchSeq: CharSequence): Int = {
    if (seq == null || searchSeq == null) INDEX_NOT_FOUND
    else CharSequenceUtils.lastIndexOf(seq, searchSeq, seq.length)
  }

  /**
    * <p>Finds the last index within a CharSequence, handling {@code null}.
    * This method uses {@link java.lang.String# lastIndexOf ( String, int)} if possible.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A negative start position returns {@code -1}.
    * An empty ("") search CharSequence always matches unless the start position is negative.
    * A start position greater than the string length searches the whole string.
    * The search starts at the startPos and works backwards; matches starting after the start
    * position are ignored.
    * </p>
    *
    * <pre>
    * StringUtils.lastIndexOf(null, *, *)          = -1
    * StringUtils.lastIndexOf(*, null, *)          = -1
    * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
    * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
    * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
    * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
    * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
    * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
    * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
    * StringUtils.lastIndexOf("aabaabaa", "b", 1)  = -1
    * StringUtils.lastIndexOf("aabaabaa", "b", 2)  = 2
    * StringUtils.lastIndexOf("aabaabaa", "ba", 2)  = 2
    * </pre>
    *
    * @param seq       the CharSequence to check, may be null
    * @param searchSeq the CharSequence to find, may be null
    * @param startPos  the start position, negative treated as zero
    * @return the last index of the search CharSequence (always &le; startPos),
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from lastIndexOf(String, String, int) to lastIndexOf(CharSequence, CharSequence, int)
    */
  def lastIndexOf(seq: CharSequence, searchSeq: CharSequence, startPos: Int): Int = {
    if (seq == null || searchSeq == null) INDEX_NOT_FOUND
    else CharSequenceUtils.lastIndexOf(seq, searchSeq, startPos)
  }

  /**
    * Returns the index within {@code seq} of the last occurrence of
    * the specified character. For values of {@code searchChar} in the
    * range from 0 to 0xFFFF (inclusive), the index (in Unicode code
    * units) returned is the largest value <i>k</i> such that:
    * <blockquote><pre>
    * this.charAt(<i>k</i>) == searchChar
    * </pre></blockquote>
    * is true. For other values of {@code searchChar}, it is the
    * largest value <i>k</i> such that:
    * <blockquote><pre>
    * this.codePointAt(<i>k</i>) == searchChar
    * </pre></blockquote>
    * is true.  In either case, if no such character occurs in this
    * string, then {@code -1} is returned. Furthermore, a {@code null} or empty ("")
    * {@code CharSequence} will return {@code -1}. The
    * {@code seq} {@code CharSequence} object is searched backwards
    * starting at the last character.
    *
    * <pre>
    * StringUtils.lastIndexOf(null, *)         = -1
    * StringUtils.lastIndexOf("", *)           = -1
    * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
    * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
    * </pre>
    *
    * @param seq        the {@code CharSequence} to check, may be null
    * @param searchChar the character to find
    * @return the last index of the search character,
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from lastIndexOf(String, int) to lastIndexOf(CharSequence, int)
    *        3.6 Updated {@link CharSequenceUtils} call to behave more like {@code String}
    */
  def lastIndexOf(seq: CharSequence, searchChar: Int): Int = {
    if (isEmpty(seq)) INDEX_NOT_FOUND
    else CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length)
  }

  /**
    * Returns the index within {@code seq} of the last occurrence of
    * the specified character, searching backward starting at the
    * specified index. For values of {@code searchChar} in the range
    * from 0 to 0xFFFF (inclusive), the index returned is the largest
    * value <i>k</i> such that:
    * <blockquote><pre>
    * (this.charAt(<i>k</i>) == searchChar) &amp;&amp; (<i>k</i> &lt;= startPos)
    * </pre></blockquote>
    * is true. For other values of {@code searchChar}, it is the
    * largest value <i>k</i> such that:
    * <blockquote><pre>
    * (this.codePointAt(<i>k</i>) == searchChar) &amp;&amp; (<i>k</i> &lt;= startPos)
    * </pre></blockquote>
    * is true. In either case, if no such character occurs in {@code seq}
    * at or before position {@code startPos}, then
    * {@code -1} is returned. Furthermore, a {@code null} or empty ("")
    * {@code CharSequence} will return {@code -1}. A start position greater
    * than the string length searches the whole string.
    * The search starts at the {@code startPos} and works backwards;
    * matches starting after the start position are ignored.
    *
    * <p>All indices are specified in {@code char} values
    * (Unicode code units).
    *
    * <pre>
    * StringUtils.lastIndexOf(null, *, *)          = -1
    * StringUtils.lastIndexOf("", *,  *)           = -1
    * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
    * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
    * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
    * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
    * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
    * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
    * </pre>
    *
    * @param seq        the CharSequence to check, may be null
    * @param searchChar the character to find
    * @param startPos   the start position
    * @return the last index of the search character (always &le; startPos),
    *         -1 if no match or {@code null} string input
    * @since 2.0
    *        3.0 Changed signature from lastIndexOf(String, int, int) to lastIndexOf(CharSequence, int, int)
    */
  def lastIndexOf(seq: CharSequence, searchChar: Int, startPos: Int): Int = {
    if (isEmpty(seq)) INDEX_NOT_FOUND
    else CharSequenceUtils.lastIndexOf(seq, searchChar, startPos)
  }

  /**
    * <p>Find the latest index of any substring in a set of potential substrings.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A {@code null} search array will return {@code -1}.
    * A {@code null} or zero length search array entry will be ignored,
    * but a search array containing "" will return the length of {@code str}
    * if {@code str} is not null. This method uses {@link java.lang.String# indexOf ( String )} if possible</p>
    *
    * <pre>
    * StringUtils.lastIndexOfAny(null, *)                    = -1
    * StringUtils.lastIndexOfAny(*, null)                    = -1
    * StringUtils.lastIndexOfAny(*, [])                      = -1
    * StringUtils.lastIndexOfAny(*, [null])                  = -1
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab", "cd"]) = 6
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd", "ab"]) = 6
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", ""])   = 10
    * </pre>
    *
    * @param str        the CharSequence to check, may be null
    * @param searchStrs the CharSequences to search for, may be null
    * @return the last index of any of the CharSequences, -1 if no match
    * @since 3.0 Changed signature from lastIndexOfAny(String, String[]) to lastIndexOfAny(CharSequence, CharSequence)
    */
  def lastIndexOfAny(str: CharSequence, searchStrs: CharSequence*): Int =
    lastIndexOfAny(str, searchStrs.toArray)

  /**
    * <p>Find the latest index of any substring in a set of potential substrings.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A {@code null} search array will return {@code -1}.
    * A {@code null} or zero length search array entry will be ignored,
    * but a search array containing "" will return the length of {@code str}
    * if {@code str} is not null. This method uses {@link java.lang.String# indexOf ( String )} if possible</p>
    *
    * <pre>
    * StringUtils.lastIndexOfAny(null, *)                    = -1
    * StringUtils.lastIndexOfAny(*, null)                    = -1
    * StringUtils.lastIndexOfAny(*, [])                      = -1
    * StringUtils.lastIndexOfAny(*, [null])                  = -1
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab", "cd"]) = 6
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd", "ab"]) = 6
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", "op"]) = -1
    * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn", ""])   = 10
    * </pre>
    *
    * @param str        the CharSequence to check, may be null
    * @param searchStrs the CharSequences to search for, may be null
    * @return the last index of any of the CharSequences, -1 if no match
    * @since 3.0 Changed signature from lastIndexOfAny(String, String[]) to lastIndexOfAny(CharSequence, CharSequence)
    */
  def lastIndexOfAny(str: CharSequence, searchStrs: Array[_ <: CharSequence]): Int = {
    if (str == null || ArrayUtils.isEmpty(searchStrs)) return INDEX_NOT_FOUND

    var ret = INDEX_NOT_FOUND
    var tmp = 0

    for (search <- searchStrs) {
      if (search != null) {
        tmp = CharSequenceUtils.lastIndexOf(str, search, str.length)
        if (tmp > ret) ret = tmp
      }
    }

    ret
  }

  /**
    * <p>Case in-sensitive find of the last index within a CharSequence.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A negative start position returns {@code -1}.
    * An empty ("") search CharSequence always matches unless the start position is negative.
    * A start position greater than the string length searches the whole string.</p>
    *
    * <pre>
    * StringUtils.lastIndexOfIgnoreCase(null, *)          = -1
    * StringUtils.lastIndexOfIgnoreCase(*, null)          = -1
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @return the first index of the search CharSequence,
    *         -1 if no match or {@code null} string input
    * @since 2.5
    *        3.0 Changed signature from lastIndexOfIgnoreCase(String, String) to lastIndexOfIgnoreCase(CharSequence, CharSequence)
    */
  def lastIndexOfIgnoreCase(str: CharSequence, searchStr: CharSequence): Int = {
    if (str == null || searchStr == null) INDEX_NOT_FOUND
    else lastIndexOfIgnoreCase(str, searchStr, str.length)
  }

  /**
    * <p>Case in-sensitive find of the last index within a CharSequence
    * from the specified position.</p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.
    * A negative start position returns {@code -1}.
    * An empty ("") search CharSequence always matches unless the start position is negative.
    * A start position greater than the string length searches the whole string.
    * The search starts at the startPos and works backwards; matches starting after the start
    * position are ignored.
    * </p>
    *
    * <pre>
    * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
    * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
    * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @param startPos  the start position
    * @return the last index of the search CharSequence (always &le; startPos),
    *         -1 if no match or {@code null} input
    * @since 2.5
    *        3.0 Changed signature from lastIndexOfIgnoreCase(String, String, int) to lastIndexOfIgnoreCase(CharSequence, CharSequence, int)
    */
  def lastIndexOfIgnoreCase(str: CharSequence, searchStr: CharSequence, startPos: Int): Int = {
    if (str == null || searchStr == null) return INDEX_NOT_FOUND

    val newStartPos: Int = if (startPos > str.length - searchStr.length) str.length - searchStr.length else startPos

    if (newStartPos < 0) return INDEX_NOT_FOUND
    if (searchStr.length == 0) return newStartPos

    for (i <- newStartPos to 0 by -1) {
      if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length)) return i
    }

    INDEX_NOT_FOUND
  }

  /**
    * <p>Finds the n-th last index within a String, handling {@code null}.
    * This method uses {@link java.lang.String# lastIndexOf ( String )}.</p>
    *
    * <p>A {@code null} String will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
    * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
    * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
    * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
    * </pre>
    *
    * <p>Note that 'tail(CharSequence str, int n)' may be implemented as: </p>
    *
    * <pre>
    * str.substring(lastOrdinalIndexOf(str, "\n", n) + 1)
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @param ordinal   the n-th last {@code searchStr} to find
    * @return the n-th last index of the search CharSequence,
    *         {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
    * @since 2.5
    *        3.0 Changed signature from lastOrdinalIndexOf(String, String, int) to lastOrdinalIndexOf(CharSequence, CharSequence, int)
    */
  def lastOrdinalIndexOf(str: CharSequence, searchStr: CharSequence, ordinal: Int): Int =
    ordinalIndexOf(str, searchStr, ordinal, true)

  /**
    * <p>Gets the leftmost {@code len} characters of a String.</p>
    *
    * <p>If {@code len} characters are not available, or the
    * String is {@code null}, the String will be returned without
    * an exception. An empty String is returned if len is negative.</p>
    *
    * <pre>
    * StringUtils.left(null, *)    = null
    * StringUtils.left(*, -ve)     = ""
    * StringUtils.left("", *)      = ""
    * StringUtils.left("abc", 0)   = ""
    * StringUtils.left("abc", 2)   = "ab"
    * StringUtils.left("abc", 4)   = "abc"
    * </pre>
    *
    * @param str the String to get the leftmost characters from, may be null
    * @param len the length of the required String
    * @return the leftmost characters, {@code null} if null String input
    */
  def left(str: String, len: Int): String = {
    if (str == null) return null
    if (len < 0) return EMPTY
    if (str.length <= len) return str
    str.substring(0, len)
  }

  /**
    * <p>Left pad a String with spaces (' ').</p>
    *
    * <p>The String is padded to the size of {@code size}.</p>
    *
    * <pre>
    * StringUtils.leftPad(null, *)   = null
    * StringUtils.leftPad("", 3)     = "   "
    * StringUtils.leftPad("bat", 3)  = "bat"
    * StringUtils.leftPad("bat", 5)  = "  bat"
    * StringUtils.leftPad("bat", 1)  = "bat"
    * StringUtils.leftPad("bat", -1) = "bat"
    * </pre>
    *
    * @param str  the String to pad out, may be null
    * @param size the size to pad to
    * @return left padded String or original String if no padding is necessary,
    *         {@code null} if null String input
    */
  def leftPad(str: String, size: Int): String = leftPad(str, size, ' ')

  /**
    * <p>Left pad a String with a specified character.</p>
    *
    * <p>Pad to a size of {@code size}.</p>
    *
    * <pre>
    * StringUtils.leftPad(null, *, *)     = null
    * StringUtils.leftPad("", 3, 'z')     = "zzz"
    * StringUtils.leftPad("bat", 3, 'z')  = "bat"
    * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
    * StringUtils.leftPad("bat", 1, 'z')  = "bat"
    * StringUtils.leftPad("bat", -1, 'z') = "bat"
    * </pre>
    *
    * @param str     the String to pad out, may be null
    * @param size    the size to pad to
    * @param padChar the character to pad with
    * @return left padded String or original String if no padding is necessary,
    *         {@code null} if null String input
    * @since 2.0
    */
  def leftPad(str: String, size: Int, padChar: Char): String = {
    if (str == null) return null
    val pads = size - str.length
    if (pads <= 0) return str // returns original String when possible
    if (pads > PAD_LIMIT) return leftPad(str, size, String.valueOf(padChar))
    repeat(padChar, pads).concat(str)
  }

  /**
    * <p>Left pad a String with a specified String.</p>
    *
    * <p>Pad to a size of {@code size}.</p>
    *
    * <pre>
    * StringUtils.leftPad(null, *, *)      = null
    * StringUtils.leftPad("", 3, "z")      = "zzz"
    * StringUtils.leftPad("bat", 3, "yz")  = "bat"
    * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
    * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
    * StringUtils.leftPad("bat", 1, "yz")  = "bat"
    * StringUtils.leftPad("bat", -1, "yz") = "bat"
    * StringUtils.leftPad("bat", 5, null)  = "  bat"
    * StringUtils.leftPad("bat", 5, "")    = "  bat"
    * </pre>
    *
    * @param str    the String to pad out, may be null
    * @param size   the size to pad to
    * @param padStr the String to pad with, null or empty treated as single space
    * @return left padded String or original String if no padding is necessary,
    *         {@code null} if null String input
    */
  def leftPad(str: String, size: Int, padStr: String): String = {
    if (str == null) return null

    val newPadStr: String = if (isEmpty(padStr)) SPACE else padStr

    val padLen = newPadStr.length
    val strLen = str.length
    val pads = size - strLen

    if (pads <= 0) return str
    if (padLen == 1 && pads <= PAD_LIMIT) return leftPad(str, size, newPadStr.charAt(0))
    if (pads == padLen) newPadStr.concat(str)
    else if (pads < padLen) newPadStr.substring(0, pads).concat(str)
    else {
      val padding = new Array[Char](pads)
      val padChars = newPadStr.toCharArray
      for (i <- 0 until pads) {
        padding(i) = padChars(i % padLen)
      }
      new String(padding).concat(str)
    }
  }

  /**
    * Gets a CharSequence length or {@code 0} if the CharSequence is
    * {@code null}.
    *
    * @param cs
    * a CharSequence or {@code null}
    * @return CharSequence length or {@code 0} if the CharSequence is
    *         {@code null}.
    * @since 2.4
    *        3.0 Changed signature from length(String) to length(CharSequence)
    */
  def length(cs: CharSequence): Int =
    if (cs == null) 0
    else cs.length

  // TODO: @link java.lang.String#toLowerCase
  // TODO: @link #lowerCase(String,Locale)
  /**
    * <p>Converts a String to lower case as per {@code java.lang.String#toLowerCase()}.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.lowerCase(null)  = null
    * StringUtils.lowerCase("")    = ""
    * StringUtils.lowerCase("aBc") = "abc"
    * </pre>
    *
    * <p><strong>Note:</strong> As described in the documentation for {@code java.lang.String#toLowerCase()},
    * the result of this method is affected by the current locale.
    * For platform-independent case transformations, the method {@code #lowerCase(String,Locale)*}
    * should be used with a specific locale (e.g. {@link java.util.Locale#ENGLISH}).</p>
    *
    * @param str the String to lower case, may be null
    * @return the lower cased String, {@code null} if null String input
    */
  def lowerCase(str: String): String =
    if (str == null) null
    else str.toLowerCase

  // TODO: @link java.lang.String#toLowerCase(Locale)
  /**
    * <p>Converts a String to lower case as per {@code java.lang.String#toLowerCase(Locale)}.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.lowerCase(null, Locale.ENGLISH)  = null
    * StringUtils.lowerCase("", Locale.ENGLISH)    = ""
    * StringUtils.lowerCase("aBc", Locale.ENGLISH) = "abc"
    * </pre>
    *
    * @param str    the String to lower case, may be null
    * @param locale the locale that defines the case transformation rules, must not be null
    * @return the lower cased String, {@code null} if null String input
    * @since 2.5
    */
  def lowerCase(str: String, locale: Locale): String =
    if (str == null) null
    else str.toLowerCase(locale)

//  private def matches(first: CharSequence, second: CharSequence): Array[Int] = {
//    var max: CharSequence = null
//    var min: CharSequence = null
//
//    if (first.length > second.length) {
//      max = first
//      min = second
//    } else {
//      max = second
//      min = first
//    }
//
//    val range = Math.max(max.length / 2 - 1, 0)
//    val matchIndexes = new Array[Int](min.length)
//    util.Arrays.fill(matchIndexes, -1)
//    val matchFlags = new Array[Boolean](max.length)
//    var matches = 0
//
//    for (mi <- 0 until min.length) {
//      val c1 = min.charAt(mi)
//      var xi = Math.max(mi - range, 0)
//      val xn = Math.min(mi + range + 1, max.length)
//      breakable {
//        while (xi < xn) {
//          if (!matchFlags(xi) && c1 == max.charAt(xi)) {
//            matchIndexes(mi) = xi
//            matchFlags(xi) = true
//            matches += 1
//            break()
//          }
//
//          xi += 1
//        }
//      }
//    }
//
//    val ms1 = new Array[Char](matches)
//    val ms2 = new Array[Char](matches)
//
//    var i: Int = 0
//    var si: Int = 0
//
//    while (i < min.length) {
//      if (matchIndexes(i) != -1) {
//        ms1(si) = min.charAt(i)
//        si += 1
//      }
//
//      i += 1
//    }
//
//    i = 0
//    si = 0
//
//    while (i < max.length) {
//      if (matchFlags(i)) {
//        ms2(si) = max.charAt(i)
//        si += 1
//      }
//
//      i += 1
//    }
//
//    var transpositions = 0
//    for (mi <- 0 until ms1.length) {
//      if (ms1(mi) != ms2(mi)) transpositions += 1
//    }
//
//    var prefix = 0
//
//    breakable {
//      for (mi <- 0 until min.length) {
//        if (first.charAt(mi) == second.charAt(mi)) prefix += 1
//        else break()
//      }
//    }
//
//    Array[Int](matches, transpositions / 2, prefix, max.length)
//  }

  /**
    * <p>Gets {@code len} characters from the middle of a String.</p>
    *
    * <p>If {@code len} characters are not available, the remainder
    * of the String will be returned without an exception. If the
    * String is {@code null}, {@code null} will be returned.
    * An empty String is returned if len is negative or exceeds the
    * length of {@code str}.</p>
    *
    * <pre>
    * StringUtils.mid(null, *, *)    = null
    * StringUtils.mid(*, *, -ve)     = ""
    * StringUtils.mid("", 0, *)      = ""
    * StringUtils.mid("abc", 0, 2)   = "ab"
    * StringUtils.mid("abc", 0, 4)   = "abc"
    * StringUtils.mid("abc", 2, 4)   = "c"
    * StringUtils.mid("abc", 4, 2)   = ""
    * StringUtils.mid("abc", -2, 2)  = "ab"
    * </pre>
    *
    * @param str the String to get the characters from, may be null
    * @param pos the position to start from, negative treated as zero
    * @param len the length of the required String
    * @return the middle characters, {@code null} if null String input
    */
  def mid(str: String, pos: Int, len: Int): String = {
    if (str == null) return null
    if (len < 0 || pos > str.length) return EMPTY

    val startIndex: Int = if (pos < 0) 0 else pos

    if (str.length <= startIndex + len) return str.substring(startIndex)
    str.substring(startIndex, startIndex + len)
  }

  private def newStringBuilder(noOfItems: Int) = new StringBuilder(noOfItems * 16)

  /**
    * <p>
    * Similar to <a
    * href="http://www.w3.org/TR/xpath/#function-normalize-space">http://www.w3.org/TR/xpath/#function-normalize
    * -space</a>
    * </p>
    * <p>
    * The function returns the argument string with whitespace normalized by using
    * {@code {@link #trim(String)}} to remove leading and trailing whitespace
    * and then replacing sequences of whitespace characters by a single space.
    * </p>
    * In XML Whitespace characters are the same as those allowed by the <a
    * href="http://www.w3.org/TR/REC-xml/#NT-S">S</a> production, which is S ::= (#x20 | #x9 | #xD | #xA)+
    * <p>
    * Java's regexp pattern \s defines whitespace as [ \t\n\x0B\f\r]
    *
    * <p>For reference:</p>
    * <ul>
    * <li>\x0B = vertical tab</li>
    * <li>\f = #xC = form feed</li>
    * <li>#x20 = space</li>
    * <li>#x9 = \t</li>
    * <li>#xA = \n</li>
    * <li>#xD = \r</li>
    * </ul>
    *
    * <p>
    * The difference is that Java's whitespace includes vertical tab and form feed, which this functional will also
    * normalize. Additionally {@code {@link #trim(String)}} removes control characters (char &lt;= 32) from both
    * ends of this String.
    * </p>
    *
    * @see Pattern
    * @see #trim(String)
    * @see <a
    *      href="http://www.w3.org/TR/xpath/#function-normalize-space">http://www.w3.org/TR/xpath/#function-normalize-space</a>
    * @param str the source String to normalize whitespaces from, may be null
    * @return the modified string with whitespace normalized, {@code null} if null String input
    * @since 3.0
    */
  def normalizeSpace(str: String): String = { // LANG-1020: Improved performance significantly by normalizing manually instead of using regex
    // See https://github.com/librucha/commons-lang-normalizespaces-benchmark for performance test
    if (isEmpty(str)) return str
    val size = str.length
    val newChars = new Array[Char](size)
    var count = 0
    var whitespacesCount = 0
    var startWhitespaces = true
    for (i <- 0 until size) {
      val actualChar = str.charAt(i)
      val isWhitespace = Character.isWhitespace(actualChar)
      if (isWhitespace) {
        if (whitespacesCount == 0 && !startWhitespaces) newChars({
          count += 1; count - 1
        }) = SPACE.charAt(0)
        whitespacesCount += 1
      } else {
        startWhitespaces = false
        newChars({
          count += 1; count - 1
        }) =
          if (actualChar == 160) 32
          else actualChar
        whitespacesCount = 0
      }
    }
    if (startWhitespaces) return EMPTY
    new String(
      newChars,
      0,
      count - (if (whitespacesCount > 0) 1
               else 0)).trim
  }

  // TODO: @lang java.lang.String#indexOf(String)
  /**
    * <p>Finds the n-th index within a CharSequence, handling {@code null}.
    * This method uses {@code java.lang.String#indexOf(String)} if possible.</p>
    * <p><b>Note:</b> The code starts looking for a match at the start of the target,
    * incrementing the starting index by one after each successful match
    * (unless {@code searchStr} is an empty string in which case the position
    * is never incremented and {@code 0} is returned immediately).
    * This means that matches may overlap.</p>
    * <p>A {@code null} CharSequence will return {@code -1}.</p>
    *
    * <pre>
    * StringUtils.ordinalIndexOf(null, *, *)          = -1
    * StringUtils.ordinalIndexOf(*, null, *)          = -1
    * StringUtils.ordinalIndexOf("", "", *)           = 0
    * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
    * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
    * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
    * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
    * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
    * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
    * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
    * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
    * </pre>
    *
    * <p>Matches may overlap:</p>
    * <pre>
    * StringUtils.ordinalIndexOf("ababab", "aba", 1)   = 0
    * StringUtils.ordinalIndexOf("ababab", "aba", 2)   = 2
    * StringUtils.ordinalIndexOf("ababab", "aba", 3)   = -1
    *
    * StringUtils.ordinalIndexOf("abababab", "abab", 1) = 0
    * StringUtils.ordinalIndexOf("abababab", "abab", 2) = 2
    * StringUtils.ordinalIndexOf("abababab", "abab", 3) = 4
    * StringUtils.ordinalIndexOf("abababab", "abab", 4) = -1
    * </pre>
    *
    * <p>Note that 'head(CharSequence str, int n)' may be implemented as: </p>
    *
    * <pre>
    * str.substring(0, lastOrdinalIndexOf(str, "\n", n))
    * </pre>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @param ordinal   the n-th {@code searchStr} to find
    * @return the n-th index of the search CharSequence,
    *         {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
    * @since 2.1
    *        3.0 Changed signature from ordinalIndexOf(String, String, int) to ordinalIndexOf(CharSequence, CharSequence, int)
    */
  def ordinalIndexOf(str: CharSequence, searchStr: CharSequence, ordinal: Int): Int =
    ordinalIndexOf(str, searchStr, ordinal, false)

  /**
    * <p>Finds the n-th index within a String, handling {@code null}.
    * This method uses {@link java.lang.String#indexOf(String)} if possible.</p>
    * <p>Note that matches may overlap<p>
    *
    * <p>A {@code null} CharSequence will return {@code -1}.</p>
    *
    * @param str       the CharSequence to check, may be null
    * @param searchStr the CharSequence to find, may be null
    * @param ordinal   the n-th {@code searchStr} to find, overlapping matches are allowed.
    * @param lastIndex true if lastOrdinalIndexOf() otherwise false if ordinalIndexOf()
    * @return the n-th index of the search CharSequence,
    *         {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
    */
  // Shared code between ordinalIndexOf(String, String, int) and lastOrdinalIndexOf(String, String, int)
  private def ordinalIndexOf(str: CharSequence, searchStr: CharSequence, ordinal: Int, lastIndex: Boolean): Int = {
    if (str == null || searchStr == null || ordinal <= 0) return INDEX_NOT_FOUND
    if (searchStr.length == 0)
      return if (lastIndex) str.length
      else 0
    var found = 0
    // set the initial index beyond the end of the string
    // this is to allow for the initial index decrement/increment
    var index =
      if (lastIndex) str.length
      else INDEX_NOT_FOUND
    do {
      if (lastIndex) index = CharSequenceUtils.lastIndexOf(str, searchStr, index - 1) // step backwards thru string
      else index = CharSequenceUtils.indexOf(str, searchStr, index + 1) // step forwards through string
      if (index < 0) return index
      found += 1
    } while ({
      found < ordinal
    })
    index
  }

  /**
    * <p>Overlays part of a String with another String.</p>
    *
    * <p>A {@code null} string input returns {@code null}.
    * A negative index is treated as zero.
    * An index greater than the string length is treated as the string length.
    * The start index is always the smaller of the two indices.</p>
    *
    * <pre>
    * StringUtils.overlay(null, *, *, *)            = null
    * StringUtils.overlay("", "abc", 0, 0)          = "abc"
    * StringUtils.overlay("abcdef", null, 2, 4)     = "abef"
    * StringUtils.overlay("abcdef", "", 2, 4)       = "abef"
    * StringUtils.overlay("abcdef", "", 4, 2)       = "abef"
    * StringUtils.overlay("abcdef", "zzzz", 2, 4)   = "abzzzzef"
    * StringUtils.overlay("abcdef", "zzzz", 4, 2)   = "abzzzzef"
    * StringUtils.overlay("abcdef", "zzzz", -1, 4)  = "zzzzef"
    * StringUtils.overlay("abcdef", "zzzz", 2, 8)   = "abzzzz"
    * StringUtils.overlay("abcdef", "zzzz", -2, -3) = "zzzzabcdef"
    * StringUtils.overlay("abcdef", "zzzz", 8, 10)  = "abcdefzzzz"
    * </pre>
    *
    * @param str     the String to do overlaying in, may be null
    * @param overlay the String to overlay, may be null
    * @param start   the position to start overlaying at
    * @param end     the position to stop overlaying before
    * @return overlayed String, {@code null} if null String input
    * @since 2.0
    */
  def overlay(str: String, overlay: String, start: Int, `end`: Int): String = {
    if (str == null) return null

    val len = str.length
    var startIndex: Int = start
    var endIndex: Int = `end`

    if (startIndex < 0) startIndex = 0
    if (startIndex > len) startIndex = len

    if (endIndex < 0) endIndex = 0
    if (endIndex > len) endIndex = len

    if (startIndex > endIndex) {
      val temp: Int = startIndex
      startIndex = endIndex
      endIndex = temp
    }

    str.substring(0, startIndex) +
      (if (overlay == null) EMPTY else overlay) +
      str.substring(endIndex)
  }

  /**
    * Prepends the prefix to the start of the string if the string does not
    * already start with any of the prefixes.
    *
    * @param str        The string.
    * @param prefix     The prefix to prepend to the start of the string.
    * @param ignoreCase Indicates whether the compare should ignore case.
    * @param prefixes   Additional prefixes that are valid (optional).
    * @return A new String if prefix was prepended, the same string otherwise.
    */
  private def prependIfMissing(
    str: String,
    prefix: CharSequence,
    ignoreCase: Boolean,
    prefixes: CharSequence*): String = {
    if (str == null || isEmpty(prefix) || startsWith(str, prefix, ignoreCase)) return str

    if (prefixes.nonEmpty) for (p <- prefixes) {
      if (startsWith(str, p, ignoreCase)) return str
    }
    prefix.toString + str
  }

  /**
    * Prepends the prefix to the start of the string if the string does not
    * already start with any of the prefixes.
    *
    * <pre>
    * StringUtils.prependIfMissing(null, null) = null
    * StringUtils.prependIfMissing("abc", null) = "abc"
    * StringUtils.prependIfMissing("", "xyz") = "xyz"
    * StringUtils.prependIfMissing("abc", "xyz") = "xyzabc"
    * StringUtils.prependIfMissing("xyzabc", "xyz") = "xyzabc"
    * StringUtils.prependIfMissing("XYZabc", "xyz") = "xyzXYZabc"
    * </pre>
    * <p>With additional prefixes,</p>
    * <pre>
    * StringUtils.prependIfMissing(null, null, null) = null
    * StringUtils.prependIfMissing("abc", null, null) = "abc"
    * StringUtils.prependIfMissing("", "xyz", null) = "xyz"
    * StringUtils.prependIfMissing("abc", "xyz", new CharSequence[]{null}) = "xyzabc"
    * StringUtils.prependIfMissing("abc", "xyz", "") = "abc"
    * StringUtils.prependIfMissing("abc", "xyz", "mno") = "xyzabc"
    * StringUtils.prependIfMissing("xyzabc", "xyz", "mno") = "xyzabc"
    * StringUtils.prependIfMissing("mnoabc", "xyz", "mno") = "mnoabc"
    * StringUtils.prependIfMissing("XYZabc", "xyz", "mno") = "xyzXYZabc"
    * StringUtils.prependIfMissing("MNOabc", "xyz", "mno") = "xyzMNOabc"
    * </pre>
    *
    * @param str      The string.
    * @param prefix   The prefix to prepend to the start of the string.
    * @param prefixes Additional prefixes that are valid.
    * @return A new String if prefix was prepended, the same string otherwise.
    * @since 3.2
    */
  def prependIfMissing(str: String, prefix: CharSequence, prefixes: CharSequence*): String =
    prependIfMissing(str, prefix, false, prefixes: _*)

  /**
    * Prepends the prefix to the start of the string if the string does not
    * already start, case insensitive, with any of the prefixes.
    *
    * <pre>
    * StringUtils.prependIfMissingIgnoreCase(null, null) = null
    * StringUtils.prependIfMissingIgnoreCase("abc", null) = "abc"
    * StringUtils.prependIfMissingIgnoreCase("", "xyz") = "xyz"
    * StringUtils.prependIfMissingIgnoreCase("abc", "xyz") = "xyzabc"
    * StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz") = "xyzabc"
    * StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz") = "XYZabc"
    * </pre>
    * <p>With additional prefixes,</p>
    * <pre>
    * StringUtils.prependIfMissingIgnoreCase(null, null, null) = null
    * StringUtils.prependIfMissingIgnoreCase("abc", null, null) = "abc"
    * StringUtils.prependIfMissingIgnoreCase("", "xyz", null) = "xyz"
    * StringUtils.prependIfMissingIgnoreCase("abc", "xyz", new CharSequence[]{null}) = "xyzabc"
    * StringUtils.prependIfMissingIgnoreCase("abc", "xyz", "") = "abc"
    * StringUtils.prependIfMissingIgnoreCase("abc", "xyz", "mno") = "xyzabc"
    * StringUtils.prependIfMissingIgnoreCase("xyzabc", "xyz", "mno") = "xyzabc"
    * StringUtils.prependIfMissingIgnoreCase("mnoabc", "xyz", "mno") = "mnoabc"
    * StringUtils.prependIfMissingIgnoreCase("XYZabc", "xyz", "mno") = "XYZabc"
    * StringUtils.prependIfMissingIgnoreCase("MNOabc", "xyz", "mno") = "MNOabc"
    * </pre>
    *
    * @param str      The string.
    * @param prefix   The prefix to prepend to the start of the string.
    * @param prefixes Additional prefixes that are valid (optional).
    * @return A new String if prefix was prepended, the same string otherwise.
    * @since 3.2
    */
  def prependIfMissingIgnoreCase(str: String, prefix: CharSequence, prefixes: CharSequence*): String =
    prependIfMissing(str, prefix, true, prefixes: _*)

  /**
    * <p>Removes all occurrences of a character from within the source string.</p>
    *
    * <p>A {@code null} source string will return {@code null}.
    * An empty ("") source string will return the empty string.</p>
    *
    * <pre>
    * StringUtils.remove(null, *)       = null
    * StringUtils.remove("", *)         = ""
    * StringUtils.remove("queued", 'u') = "qeed"
    * StringUtils.remove("queued", 'z') = "queued"
    * </pre>
    *
    * @param str    the source String to search, may be null
    * @param remove the char to search for and remove, may be null
    * @return the substring with the char removed if found,
    *         {@code null} if null String input
    * @since 2.1
    */
  def remove(str: String, remove: Char): String = {
    if (isEmpty(str) || str.indexOf(remove.toInt) == INDEX_NOT_FOUND) return str
    val chars = str.toCharArray
    var pos = 0
    for (i <- 0 until chars.length) {
      if (chars(i) != remove) chars({
        pos += 1; pos - 1
      }) = chars(i)
    }
    new String(chars, 0, pos)
  }

  /**
    * <p>Removes all occurrences of a substring from within the source string.</p>
    *
    * <p>A {@code null} source string will return {@code null}.
    * An empty ("") source string will return the empty string.
    * A {@code null} remove string will return the source string.
    * An empty ("") remove string will return the source string.</p>
    *
    * <pre>
    * StringUtils.remove(null, *)        = null
    * StringUtils.remove("", *)          = ""
    * StringUtils.remove(*, null)        = *
    * StringUtils.remove(*, "")          = *
    * StringUtils.remove("queued", "ue") = "qd"
    * StringUtils.remove("queued", "zz") = "queued"
    * </pre>
    *
    * @param str    the source String to search, may be null
    * @param remove the String to search for and remove, may be null
    * @return the substring with the string removed if found,
    *         {@code null} if null String input
    * @since 2.1
    */
  def remove(str: String, remove: String): String = {
    if (isEmpty(str) || isEmpty(remove)) str
    else replace(str, remove, EMPTY, -1)
  }

  /**
    * <p>Removes each substring of the text String that matches the given regular expression.</p>
    *
    * This method is a {@code null} safe equivalent to:
    * <ul>
    * <li>{@code text.replaceAll(regex, StringUtils.EMPTY)}</li>
    * <li>{@code Pattern.compile(regex).matcher(text).replaceAll(StringUtils.EMPTY)}</li>
    * </ul>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <p>Unlike in the {@link #removePattern(source:String,regex:String)*} method, the {@link java.util.regex.Pattern#DOTALL} option
    * is NOT automatically added.
    * To use the DOTALL option prepend {@code "(?s)"} to the regex.
    * DOTALL is also known as single-line mode in Perl.</p>
    *
    * <pre>
    * StringUtils.removeAll(null, *)      = null
    * StringUtils.removeAll("any", (String) null)  = "any"
    * StringUtils.removeAll("any", "")    = "any"
    * StringUtils.removeAll("any", ".*")  = ""
    * StringUtils.removeAll("any", ".+")  = ""
    * StringUtils.removeAll("abc", ".?")  = ""
    * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\nB"
    * StringUtils.removeAll("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
    * StringUtils.removeAll("ABCabc123abc", "[a-z]")     = "ABC123"
    * </pre>
    *
    * @param text  text to remove from, may be null
    * @param regex the regular expression to which this string is to be matched
    * @return the text with any removes processed,
    *         {@code null} if null String input
    * @throws java.util.regex.PatternSyntaxException
    * if the regular expression's syntax is invalid
    * @see #replaceAll(String, String, String)
    * @see #removePattern(String, String)
    * @see String#replaceAll(String, String)
    * @see java.util.regex.Pattern
    * @see java.util.regex.Pattern#DOTALL
    * @since 3.5
    * @deprecated Moved to RegExUtils.
    */
  @deprecated def removeAll(text: String, regex: String): String =
    RegExUtils.removeAll(text, regex)

  /**
    * <p>Removes a substring only if it is at the end of a source string,
    * otherwise returns the source string.</p>
    *
    * <p>A {@code null} source string will return {@code null}.
    * An empty ("") source string will return the empty string.
    * A {@code null} search string will return the source string.</p>
    *
    * <pre>
    * StringUtils.removeEnd(null, *)      = null
    * StringUtils.removeEnd("", *)        = ""
    * StringUtils.removeEnd(*, null)      = *
    * StringUtils.removeEnd("www.domain.com", ".com.")  = "www.domain.com"
    * StringUtils.removeEnd("www.domain.com", ".com")   = "www.domain"
    * StringUtils.removeEnd("www.domain.com", "domain") = "www.domain.com"
    * StringUtils.removeEnd("abc", "")    = "abc"
    * </pre>
    *
    * @param str    the source String to search, may be null
    * @param remove the String to search for and remove, may be null
    * @return the substring with the string removed if found,
    *         {@code null} if null String input
    * @since 2.1
    */
  def removeEnd(str: String, remove: String): String = {
    if (isEmpty(str) || isEmpty(remove)) return str
    if (str.endsWith(remove)) return str.substring(0, str.length - remove.length)
    str
  }

  /**
    * <p>Case insensitive removal of a substring if it is at the end of a source string,
    * otherwise returns the source string.</p>
    *
    * <p>A {@code null} source string will return {@code null}.
    * An empty ("") source string will return the empty string.
    * A {@code null} search string will return the source string.</p>
    *
    * <pre>
    * StringUtils.removeEndIgnoreCase(null, *)      = null
    * StringUtils.removeEndIgnoreCase("", *)        = ""
    * StringUtils.removeEndIgnoreCase(*, null)      = *
    * StringUtils.removeEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
    * StringUtils.removeEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
    * StringUtils.removeEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
    * StringUtils.removeEndIgnoreCase("abc", "")    = "abc"
    * StringUtils.removeEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
    * StringUtils.removeEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
    * </pre>
    *
    * @param str    the source String to search, may be null
    * @param remove the String to search for (case insensitive) and remove, may be null
    * @return the substring with the string removed if found,
    *         {@code null} if null String input
    * @since 2.4
    */
  def removeEndIgnoreCase(str: String, remove: String): String =
    if (isEmpty(str) || isEmpty(remove)) str
    else if (endsWithIgnoreCase(str, remove)) str.substring(0, str.length - remove.length)
    else str

  /**
    * <p>Removes the first substring of the text string that matches the given regular expression.</p>
    *
    * This method is a {@code null} safe equivalent to:
    * <ul>
    * <li>{@code text.replaceFirst(regex, StringUtils.EMPTY)}</li>
    * <li>{@code Pattern.compile(regex).matcher(text).replaceFirst(StringUtils.EMPTY)}</li>
    * </ul>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <p>The {@link java.util.regex.Pattern#DOTALL} option is NOT automatically added.
    * To use the DOTALL option prepend {@code "(?s)"} to the regex.
    * DOTALL is also known as single-line mode in Perl.</p>
    *
    * <pre>
    * StringUtils.removeFirst(null, *)      = null
    * StringUtils.removeFirst("any", (String) null)  = "any"
    * StringUtils.removeFirst("any", "")    = "any"
    * StringUtils.removeFirst("any", ".*")  = ""
    * StringUtils.removeFirst("any", ".+")  = ""
    * StringUtils.removeFirst("abc", ".?")  = "bc"
    * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")      = "A\n&lt;__&gt;B"
    * StringUtils.removeFirst("A&lt;__&gt;\n&lt;__&gt;B", "(?s)&lt;.*&gt;")  = "AB"
    * StringUtils.removeFirst("ABCabc123", "[a-z]")          = "ABCbc123"
    * StringUtils.removeFirst("ABCabc123abc", "[a-z]+")      = "ABC123abc"
    * </pre>
    *
    *
    * @param text  text to remove from, may be null
    * @param regex the regular expression to which this string is to be matched
    * @return the text with the first replacement processed,
    *         {@code null} if null String input
    * @throws java.util.regex.PatternSyntaxException
    * if the regular expression's syntax is invalid
    * @see #replaceFirst(String, String, String)
    * @see String#replaceFirst(String, String)
    * @see java.util.regex.Pattern
    * @see java.util.regex.Pattern#DOTALL
    * @since 3.5
    * @deprecated Moved to RegExUtils.
    */
  @deprecated def removeFirst(text: String, regex: String): String =
    replaceFirst(text, regex, EMPTY)

  /**
    * <p>
    * Case insensitive removal of all occurrences of a substring from within
    * the source string.
    * </p>
    *
    * <p>
    * A {@code null} source string will return {@code null}. An empty ("")
    * source string will return the empty string. A {@code null} remove string
    * will return the source string. An empty ("") remove string will return
    * the source string.
    * </p>
    *
    * <pre>
    * StringUtils.removeIgnoreCase(null, *)        = null
    * StringUtils.removeIgnoreCase("", *)          = ""
    * StringUtils.removeIgnoreCase(*, null)        = *
    * StringUtils.removeIgnoreCase(*, "")          = *
    * StringUtils.removeIgnoreCase("queued", "ue") = "qd"
    * StringUtils.removeIgnoreCase("queued", "zz") = "queued"
    * StringUtils.removeIgnoreCase("quEUed", "UE") = "qd"
    * StringUtils.removeIgnoreCase("queued", "zZ") = "queued"
    * </pre>
    *
    * @param str
    * the source String to search, may be null
    * @param remove
    * the String to search for (case insensitive) and remove, may be
    * null
    * @return the substring with the string removed if found, {@code null} if
    *         null String input
    * @since 3.5
    */
  def removeIgnoreCase(str: String, remove: String): String = {
    if (isEmpty(str) || isEmpty(remove)) return str
    replaceIgnoreCase(str, remove, EMPTY, -1)
  }

  /**
    * <p>Removes each substring of the source String that matches the given regular expression using the DOTALL option.
    * </p>
    *
    * This call is a {@code null} safe equivalent to:
    * <ul>
    * <li>{@code source.replaceAll(&quot;(?s)&quot; + regex, StringUtils.EMPTY)}</li>
    * <li>{@code Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(StringUtils.EMPTY)}</li>
    * </ul>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.removePattern(null, *)       = null
    * StringUtils.removePattern("any", (String) null)   = "any"
    * StringUtils.removePattern("A&lt;__&gt;\n&lt;__&gt;B", "&lt;.*&gt;")  = "AB"
    * StringUtils.removePattern("ABCabc123", "[a-z]")    = "ABC123"
    * </pre>
    *
    * @param source
    * the source string
    * @param regex
    * the regular expression to which this string is to be matched
    * @return The resulting {@code String}
    * @see #replacePattern(source:String,regex:String,replacement:String)
    * @see java.lang.String#replaceAll
    *      // java.lang.String#replaceAll(String,String)
    * @see Pattern#DOTALL
    * @since 3.2
    *        3.5 Changed {@code null} reference passed to this method is a no-op.
    * @deprecated Moved to RegExUtils.
    */
  @deprecated def removePattern(source: String, regex: String): String =
    RegExUtils.removePattern(source, regex)

  /**
    * <p>Removes a substring only if it is at the beginning of a source string,
    * otherwise returns the source string.</p>
    *
    * <p>A {@code null} source string will return {@code null}.
    * An empty ("") source string will return the empty string.
    * A {@code null} search string will return the source string.</p>
    *
    * <pre>
    * StringUtils.removeStart(null, *)      = null
    * StringUtils.removeStart("", *)        = ""
    * StringUtils.removeStart(*, null)      = *
    * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
    * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
    * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
    * StringUtils.removeStart("abc", "")    = "abc"
    * </pre>
    *
    * @param str    the source String to search, may be null
    * @param remove the String to search for and remove, may be null
    * @return the substring with the string removed if found,
    *         {@code null} if null String input
    * @since 2.1
    */
  def removeStart(str: String, remove: String): String = {
    if (isEmpty(str) || isEmpty(remove)) return str
    if (str.startsWith(remove)) return str.substring(remove.length)
    str
  }

  /**
    * <p>Case insensitive removal of a substring if it is at the beginning of a source string,
    * otherwise returns the source string.</p>
    *
    * <p>A {@code null} source string will return {@code null}.
    * An empty ("") source string will return the empty string.
    * A {@code null} search string will return the source string.</p>
    *
    * <pre>
    * StringUtils.removeStartIgnoreCase(null, *)      = null
    * StringUtils.removeStartIgnoreCase("", *)        = ""
    * StringUtils.removeStartIgnoreCase(*, null)      = *
    * StringUtils.removeStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
    * StringUtils.removeStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
    * StringUtils.removeStartIgnoreCase("domain.com", "www.")       = "domain.com"
    * StringUtils.removeStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
    * StringUtils.removeStartIgnoreCase("abc", "")    = "abc"
    * </pre>
    *
    * @param str    the source String to search, may be null
    * @param remove the String to search for (case insensitive) and remove, may be null
    * @return the substring with the string removed if found,
    *         {@code null} if null String input
    * @since 2.4
    */
  def removeStartIgnoreCase(str: String, remove: String): String = {
    if (isEmpty(str) || isEmpty(remove)) return str
    if (startsWithIgnoreCase(str, remove)) return str.substring(remove.length)
    str
  }

  /**
    * <p>Returns padding using the specified delimiter repeated
    * to a given length.</p>
    *
    * <pre>
    * StringUtils.repeat('e', 0)  = ""
    * StringUtils.repeat('e', 3)  = "eee"
    * StringUtils.repeat('e', -2) = ""
    * </pre>
    *
    * <p>Note: this method does not support padding with
    * <a href="http://www.unicode.org/glossary/#supplementary_character">Unicode Supplementary Characters</a>
    * as they require a pair of {@code char}s to be represented.
    * If you are needing to support full I18N of your applications
    * consider using {@link #repeat(str:String,repeat:Int)*} instead.
    * </p>
    *
    * @param ch     character to repeat
    * @param repeat number of times to repeat char, negative treated as zero
    * @return String with repeated character
    * @see #repeat(String, int)
    */
  def repeat(ch: Char, repeat: Int): String = {
    if (repeat <= 0) return EMPTY
    val buf = new Array[Char](repeat)
    for (i <- repeat - 1 to 0 by -1) {
      buf(i) = ch
    }
    new String(buf)
  }

  /**
    * <p>Repeat a String {@code repeat} times to form a
    * new String.</p>
    *
    * <pre>
    * StringUtils.repeat(null, 2) = null
    * StringUtils.repeat("", 0)   = ""
    * StringUtils.repeat("", 2)   = ""
    * StringUtils.repeat("a", 3)  = "aaa"
    * StringUtils.repeat("ab", 2) = "abab"
    * StringUtils.repeat("a", -2) = ""
    * </pre>
    *
    * @param str    the String to repeat, may be null
    * @param repeat number of times to repeat str, negative treated as zero
    * @return a new String consisting of the original String repeated,
    *         {@code null} if null String input
    */
  def repeat(str: String, repeat: Int): String = { // Performance tuned for 2.0 (JDK1.4)
    if (str == null) return null
    if (repeat <= 0) return EMPTY
    val inputLength = str.length
    if (repeat == 1 || inputLength == 0) return str
    if (inputLength == 1 && repeat <= PAD_LIMIT) return this.repeat(str.charAt(0), repeat)

    val outputLength = inputLength * repeat
    inputLength match {
      case 1 =>
        this.repeat(str.charAt(0), repeat)
      case 2 =>
        val ch0 = str.charAt(0)
        val ch1 = str.charAt(1)
        val output2 = new Array[Char](outputLength)
        var i = repeat * 2 - 2
        while (i >= 0) {
          output2(i) = ch0
          output2(i + 1) = ch1

          i -= 1
          i -= 1
        }
        new String(output2)
      case _ =>
        val buf = new StringBuilder(outputLength)
        for (_ <- 0 until repeat) buf.append(str)
        buf.toString
    }
  }

  /**
    * <p>Repeat a String {@code repeat} times to form a
    * new String, with a String separator injected each time. </p>
    *
    * <pre>
    * StringUtils.repeat(null, null, 2) = null
    * StringUtils.repeat(null, "x", 2)  = null
    * StringUtils.repeat("", null, 0)   = ""
    * StringUtils.repeat("", "", 2)     = ""
    * StringUtils.repeat("", "x", 3)    = "xxx"
    * StringUtils.repeat("?", ", ", 3)  = "?, ?, ?"
    * </pre>
    *
    * @param str       the String to repeat, may be null
    * @param separator the String to inject, may be null
    * @param repeat    number of times to repeat str, negative treated as zero
    * @return a new String consisting of the original String repeated,
    *         {@code null} if null String input
    * @since 2.5
    */
  def repeat(str: String, separator: String, repeat: Int): String = {
    if (str == null || separator == null) return this.repeat(str, repeat)
    // given that repeat(String, int) is quite optimized, better to rely on it than try and splice this into it
    val result = this.repeat(str + separator, repeat)
    removeEnd(result, separator)
  }

  /**
    * <p>Replaces all occurrences of a String within another String.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *)        = null
    * StringUtils.replace("", *, *)          = ""
    * StringUtils.replace("any", null, *)    = "any"
    * StringUtils.replace("any", *, null)    = "any"
    * StringUtils.replace("any", "", *)      = "any"
    * StringUtils.replace("aba", "a", null)  = "aba"
    * StringUtils.replace("aba", "a", "")    = "b"
    * StringUtils.replace("aba", "a", "z")   = "zbz"
    * </pre>
    *
    * @see #replace(String text, String searchString, String replacement, int max)
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for, may be null
    * @param replacement  the String to replace it with, may be null
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    */
  def replace(text: String, searchString: String, replacement: String): String =
    replace(text, searchString, replacement, -1)

  /**
    * <p>Replaces a String with another String inside a larger String,
    * for the first {@code max} values of the search String.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *, *)         = null
    * StringUtils.replace("", *, *, *)           = ""
    * StringUtils.replace("any", null, *, *)     = "any"
    * StringUtils.replace("any", *, null, *)     = "any"
    * StringUtils.replace("any", "", *, *)       = "any"
    * StringUtils.replace("any", *, *, 0)        = "any"
    * StringUtils.replace("abaa", "a", null, -1) = "abaa"
    * StringUtils.replace("abaa", "a", "", -1)   = "b"
    * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
    * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
    * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
    * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
    * </pre>
    *
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for, may be null
    * @param replacement  the String to replace it with, may be null
    * @param max          maximum number of values to replace, or {@code -1} if no maximum
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    */
  def replace(text: String, searchString: String, replacement: String, max: Int): String =
    replace(text, searchString, replacement, max, false)

  /**
    * <p>Replaces a String with another String inside a larger String,
    * for the first {@code max} values of the search String,
    * case sensitively/insensitively based on {@code ignoreCase} value.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *, *, false)         = null
    * StringUtils.replace("", *, *, *, false)           = ""
    * StringUtils.replace("any", null, *, *, false)     = "any"
    * StringUtils.replace("any", *, null, *, false)     = "any"
    * StringUtils.replace("any", "", *, *, false)       = "any"
    * StringUtils.replace("any", *, *, 0, false)        = "any"
    * StringUtils.replace("abaa", "a", null, -1, false) = "abaa"
    * StringUtils.replace("abaa", "a", "", -1, false)   = "b"
    * StringUtils.replace("abaa", "a", "z", 0, false)   = "abaa"
    * StringUtils.replace("abaa", "A", "z", 1, false)   = "abaa"
    * StringUtils.replace("abaa", "A", "z", 1, true)   = "zbaa"
    * StringUtils.replace("abAa", "a", "z", 2, true)   = "zbza"
    * StringUtils.replace("abAa", "a", "z", -1, true)  = "zbzz"
    * </pre>
    *
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for (case insensitive), may be null
    * @param replacement  the String to replace it with, may be null
    * @param max          maximum number of values to replace, or {@code -1} if no maximum
    * @param ignoreCase   if true replace is case insensitive, otherwise case sensitive
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    */
  private def replace(
    text: String,
    searchString: String,
    replacement: String,
    max: Int,
    ignoreCase: Boolean
  ): String = {
    if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) return text
    val needle: String = if (ignoreCase) searchString.toLowerCase else searchString

    var start = 0
    var `end` =
      if (ignoreCase) indexOfIgnoreCase(text, needle, start)
      else indexOf(text, needle, start)

    if (`end` == INDEX_NOT_FOUND) return text

    val replLength = needle.length
    var increase = Math.max(replacement.length - replLength, 0)

    increase *= (
      if (max < 0) 16
      else Math.min(max, 64)
    )

    var remainingReplaces: Int = max
    val buf = new java.lang.StringBuilder(text.length + increase)

    breakable {
      while (`end` != INDEX_NOT_FOUND) {
        buf
          .append(text, start, `end`)
          .append(replacement)
        start = `end` + replLength

        remainingReplaces -= 1
        if (remainingReplaces == 0) break()

        `end` =
          if (ignoreCase) indexOfIgnoreCase(text, needle, start)
          else indexOf(text, needle, start)
      }
    }

    buf.append(text, start, text.length)
    buf.toString
  }

  /**
    * <p>Replaces each substring of the text String that matches the given regular expression
    * with the given replacement.</p>
    *
    * This method is a {@code null} safe equivalent to:
    * <ul>
    * <li>{@code text.replaceAll(regex, replacement)}</li>
    * <li>{@code Pattern.compile(regex).matcher(text).replaceAll(replacement)}</li>
    * </ul>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <p>Unlike in the {@link #replacePattern(source:String,regex:String,replacement:String)*} method, the {@link java.util.regex.Pattern#DOTALL} option
    * is NOT automatically added.
    * To use the DOTALL option prepend {@code "(?s)"} to the regex.
    * DOTALL is also known as single-line mode in Perl.</p>
    *
    * <pre>
    * StringUtils.replaceAll(null, *, *)       = null
    * StringUtils.replaceAll("any", (String) null, *)   = "any"
    * StringUtils.replaceAll("any", *, null)   = "any"
    * StringUtils.replaceAll("", "", "zzz")    = "zzz"
    * StringUtils.replaceAll("", ".*", "zzz")  = "zzz"
    * StringUtils.replaceAll("", ".+", "zzz")  = ""
    * StringUtils.replaceAll("abc", "", "ZZ")  = "ZZaZZbZZcZZ"
    * StringUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\nz"
    * StringUtils.replaceAll("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
    * StringUtils.replaceAll("ABCabc123", "[a-z]", "_")       = "ABC___123"
    * StringUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
    * StringUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
    * StringUtils.replaceAll("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_\$2")  = "Lorem_ipsum_dolor_sit"
    * </pre>
    *
    *
    * @param text        text to search and replace in, may be null
    * @param regex       the regular expression to which this string is to be matched
    * @param replacement the string to be substituted for each match
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    * @throws java.util.regex.PatternSyntaxException
    * if the regular expression's syntax is invalid
    * @see #replacePattern(String, String, String)
    * @see String#replaceAll(String, String)
    * @see java.util.regex.Pattern
    * @see java.util.regex.Pattern#DOTALL
    * @since 3.5
    * @deprecated Moved to RegExUtils.
    */
  @deprecated def replaceAll(text: String, regex: String, replacement: String): String =
    RegExUtils.replaceAll(text, regex, replacement)

  // TODO: @link java.lang.String#replace(Char,Char)
  /**
    * <p>Replaces all occurrences of a character in a String with another.
    * This is a null-safe version of {@code java.lang.String#replace(Char,Char)}.</p>
    *
    * <p>A {@code null} string input returns {@code null}.
    * An empty ("") string input returns an empty string.</p>
    *
    * <pre>
    * StringUtils.replaceChars(null, *, *)        = null
    * StringUtils.replaceChars("", *, *)          = ""
    * StringUtils.replaceChars("abcba", 'b', 'y') = "aycya"
    * StringUtils.replaceChars("abcba", 'z', 'y') = "abcba"
    * </pre>
    *
    * @param str         String to replace characters in, may be null
    * @param searchChar  the character to search for, may be null
    * @param replaceChar the character to replace, may be null
    * @return modified String, {@code null} if null string input
    * @since 2.0
    */
  def replaceChars(str: String, searchChar: Char, replaceChar: Char): String = {
    if (str == null) return null
    str.replace(searchChar, replaceChar)
  }

  /**
    * <p>Replaces multiple characters in a String in one go.
    * This method can also be used to delete characters.</p>
    *
    * <p>For example:<br>
    * {@code replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly}.</p>
    *
    * <p>A {@code null} string input returns {@code null}.
    * An empty ("") string input returns an empty string.
    * A null or empty set of search characters returns the input string.</p>
    *
    * <p>The length of the search characters should normally equal the length
    * of the replace characters.
    * If the search characters is longer, then the extra search characters
    * are deleted.
    * If the search characters is shorter, then the extra replace characters
    * are ignored.</p>
    *
    * <pre>
    * StringUtils.replaceChars(null, *, *)           = null
    * StringUtils.replaceChars("", *, *)             = ""
    * StringUtils.replaceChars("abc", null, *)       = "abc"
    * StringUtils.replaceChars("abc", "", *)         = "abc"
    * StringUtils.replaceChars("abc", "b", null)     = "ac"
    * StringUtils.replaceChars("abc", "b", "")       = "ac"
    * StringUtils.replaceChars("abcba", "bc", "yz")  = "ayzya"
    * StringUtils.replaceChars("abcba", "bc", "y")   = "ayya"
    * StringUtils.replaceChars("abcba", "bc", "yzx") = "ayzya"
    * </pre>
    *
    * @param str          String to replace characters in, may be null
    * @param searchChars  a set of characters to search for, may be null
    * @param replaceChars a set of characters to replace, may be null
    * @return modified String, {@code null} if null string input
    * @since 2.0
    */
  def replaceChars(str: String, searchChars: String, replaceChars: String): String = {
    if (isEmpty(str) || isEmpty(searchChars)) return str

    val updatedReplaceChars: String =
      if (replaceChars == null) EMPTY
      else replaceChars

    var modified = false
    val replaceCharsLength = updatedReplaceChars.length
    val strLength = str.length
    val buf = new StringBuilder(strLength)

    for (i <- 0 until strLength) {
      val ch = str.charAt(i)
      val index = searchChars.indexOf(ch.toInt)

      if (index >= 0) {
        modified = true
        if (index < replaceCharsLength) buf.append(updatedReplaceChars.charAt(index))
      } else buf.append(ch)
    }

    if (modified) buf.toString
    else str
  }

  /**
    * <p>
    * Replaces all occurrences of Strings within another String.
    * </p>
    *
    * <p>
    * A {@code null} reference passed to this method is a no-op, or if
    * any "search string" or "string to replace" is null, that replace will be
    * ignored. This will not repeat. For repeating replaces, call the
    * overloaded method.
    * </p>
    *
    * <pre>
    * StringUtils.replaceEach(null, *, *)        = null
    * StringUtils.replaceEach("", *, *)          = ""
    * StringUtils.replaceEach("aba", null, null) = "aba"
    * StringUtils.replaceEach("aba", new String[0], null) = "aba"
    * StringUtils.replaceEach("aba", null, new String[0]) = "aba"
    * StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
    * StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
    * StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
    * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
    * (example of how it does not repeat)
    * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
    * </pre>
    *
    * @param text
    * text to search and replace in, no-op if null
    * @param searchList
    * the Strings to search for, no-op if null
    * @param replacementList
    * the Strings to replace them with, no-op if null
    * @return the text with any replacements processed, {@code null} if
    *         null String input
    * @throws java.lang.IllegalArgumentException
    * if the lengths of the arrays are not the same (null is ok,
    * and/or size 0)
    * @since 2.4
    */
  def replaceEach(text: String, searchList: Array[String], replacementList: Array[String]): String =
    replaceEach(text, searchList, replacementList, false, 0)

  /**
    * <p>
    * Replace all occurrences of Strings within another String.
    * This is a private recursive helper method for {@link #replaceEachRepeatedly ( String, String[], String[])} and
    * {@link #replaceEach ( String, String[], String[])}
    * </p>
    *
    * <p>
    * A {@code null} reference passed to this method is a no-op, or if
    * any "search string" or "string to replace" is null, that replace will be
    * ignored.
    * </p>
    *
    * <pre>
    * StringUtils.replaceEach(null, *, *, *, *) = null
    * StringUtils.replaceEach("", *, *, *, *) = ""
    * StringUtils.replaceEach("aba", null, null, *, *) = "aba"
    * StringUtils.replaceEach("aba", new String[0], null, *, *) = "aba"
    * StringUtils.replaceEach("aba", null, new String[0], *, *) = "aba"
    * StringUtils.replaceEach("aba", new String[]{"a"}, null, *, *) = "aba"
    * StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, *, >=0) = "b"
    * StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, *, >=0) = "aba"
    * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *, >=0) = "wcte"
    * (example of how it repeats)
    * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false, >=0) = "dcte"
    * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true, >=2) = "tcte"
    * StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, *, *) = IllegalStateException
    * </pre>
    *
    * @param text
    *               text to search and replace in, no-op if null
    * @param searchList
    *               the Strings to search for, no-op if null
    * @param replacementList
    *               the Strings to replace them with, no-op if null
    * @param repeat if true, then replace repeatedly
    *               until there are no more possible replacements or timeToLive < 0
    * @param timeToLive
    *               if less than 0 then there is a circular reference and endless
    *               loop
    * @return the text with any replacements processed, {@code null} if
    *         null String input
    * @throws java.lang.IllegalStateException
    * if the search is repeating and there is an endless loop due
    * to outputs of one being inputs to another
    * @throws java.lang.IllegalArgumentException
    * if the lengths of the arrays are not the same (null is ok,
    * and/or size 0)
    * @since 2.4
    */
  private def replaceEach(
    text: String,
    searchList: Array[String],
    replacementList: Array[String],
    repeat: Boolean,
    timeToLive: Int): String = { // mchyzer Performance note: This creates very few new objects (one major goal)
    // let me know if there are performance requests, we can create a harness to measure
    // if recursing, this shouldn't be less than 0
    if (timeToLive < 0) {
      val searchSet = new util.HashSet[String](searchList.toList.asJava)
      val replacementSet = new util.HashSet[String](replacementList.toList.asJava)

      searchSet.retainAll(replacementSet)
      if (searchSet.size > 0)
        throw new IllegalStateException(
          "Aborting to protect against StackOverflowError - " + "output of one loop is the input of another")
    }

    if (isEmpty(text) ||
      ArrayUtils.isEmpty(searchList) ||
      ArrayUtils.isEmpty(replacementList) ||
      (ArrayUtils.isNotEmpty(searchList) && timeToLive == -1)) return text

    val searchLength = searchList.length
    val replacementLength = replacementList.length
    // make sure lengths are ok, these need to be equal
    if (searchLength != replacementLength)
      throw new IllegalArgumentException(
        "Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength)
    // keep track of which still have matches
    val noMoreMatchesForReplIndex = new Array[Boolean](searchLength)
    // index on index that the match was found
    var textIndex = -1
    var replaceIndex = -1
    var tempIndex = -1
    // index of replace array that will replace the search string found
    // NOTE: logic duplicated below START
    for {
      i <- 0 until searchLength
      if !(noMoreMatchesForReplIndex(i) || isEmpty(searchList(i)) || replacementList(i) == null)
    } {
      tempIndex = text.indexOf(searchList(i))
      // see if we need to keep searching for this
      if (tempIndex == -1) noMoreMatchesForReplIndex(i) = true
      else if (textIndex == -1 || tempIndex < textIndex) {
        textIndex = tempIndex
        replaceIndex = i
      }
    }

    // NOTE: logic mostly below END
    // no search strings found, we are done
    if (textIndex == -1) return text
    var start = 0
    // get a good guess on the size of the result buffer so it doesn't have to double if it goes over a bit
    var increase = 0
    // count the replacement text elements that are larger than their corresponding text being replaced
    for {
      i <- 0 until searchList.length
      if !(searchList(i) == null || replacementList(i) == null)
    } {
      val greater = replacementList(i).length - searchList(i).length
      if (greater > 0) increase += 3 * greater // assume 3 matches
    }

    // have upper-bound at 20% increase, then let Java take over
    increase = Math.min(increase, text.length / 5)
    val buf = new StringBuilder(text.length + increase)

    while (textIndex != -1) {
      for (i <- start until textIndex) {
        buf.append(text.charAt(i))
      }
      buf.append(replacementList(replaceIndex))
      start = textIndex + searchList(replaceIndex).length
      textIndex = -1
      replaceIndex = -1
      tempIndex = -1
      // find the next earliest match
      // NOTE: logic mostly duplicated above START
      for {
        i <- 0 until searchLength
        if !(noMoreMatchesForReplIndex(i) || searchList(i) == null || searchList(i).isEmpty || replacementList(
          i) == null)
      } {
        tempIndex = text.indexOf(searchList(i), start)
        if (tempIndex == -1) noMoreMatchesForReplIndex(i) = true
        else if (textIndex == -1 || tempIndex < textIndex) {
          textIndex = tempIndex
          replaceIndex = i
        }
      }
      // NOTE: logic duplicated above END
    }

    val textLength = text.length
    for (i <- start until textLength) {
      buf.append(text.charAt(i))
    }

    val result = buf.toString

    if (!repeat) result
    else replaceEach(result, searchList, replacementList, repeat, timeToLive - 1)
  }

  /**
    * <p>
    * Replaces all occurrences of Strings within another String.
    * </p>
    *
    * <p>
    * A {@code null} reference passed to this method is a no-op, or if
    * any "search string" or "string to replace" is null, that replace will be
    * ignored.
    * </p>
    *
    * <pre>
    * StringUtils.replaceEachRepeatedly(null, *, *) = null
    * StringUtils.replaceEachRepeatedly("", *, *) = ""
    * StringUtils.replaceEachRepeatedly("aba", null, null) = "aba"
    * StringUtils.replaceEachRepeatedly("aba", new String[0], null) = "aba"
    * StringUtils.replaceEachRepeatedly("aba", null, new String[0]) = "aba"
    * StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, null) = "aba"
    * StringUtils.replaceEachRepeatedly("aba", new String[]{"a"}, new String[]{""}) = "b"
    * StringUtils.replaceEachRepeatedly("aba", new String[]{null}, new String[]{"a"}) = "aba"
    * StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}) = "wcte"
    * (example of how it repeats)
    * StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}) = "tcte"
    * StringUtils.replaceEachRepeatedly("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}) = IllegalStateException
    * </pre>
    *
    * @param text
    * text to search and replace in, no-op if null
    * @param searchList
    * the Strings to search for, no-op if null
    * @param replacementList
    * the Strings to replace them with, no-op if null
    * @return the text with any replacements processed, {@code null} if
    *         null String input
    * @throws java.lang.IllegalStateException
    * if the search is repeating and there is an endless loop due
    * to outputs of one being inputs to another
    * @throws java.lang.IllegalArgumentException
    * if the lengths of the arrays are not the same (null is ok,
    * and/or size 0)
    * @since 2.4
    */
  def replaceEachRepeatedly(text: String, searchList: Array[String], replacementList: Array[String]): String = { // timeToLive should be 0 if not used or nothing to replace, else it's
    // the length of the replace array
    val timeToLive =
      if (searchList == null) 0
      else searchList.length
    replaceEach(text, searchList, replacementList, true, timeToLive)
  }

  /**
    * <p>Replaces the first substring of the text string that matches the given regular expression
    * with the given replacement.</p>
    *
    * This method is a {@code null} safe equivalent to:
    * <ul>
    * <li>{@code text.replaceFirst(regex, replacement)}</li>
    * <li>{@code Pattern.compile(regex).matcher(text).replaceFirst(replacement)}</li>
    * </ul>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <p>The {@link java.util.regex.Pattern#DOTALL} option is NOT automatically added.
    * To use the DOTALL option prepend {@code "(?s)"} to the regex.
    * DOTALL is also known as single-line mode in Perl.</p>
    *
    * <pre>
    * StringUtils.replaceFirst(null, *, *)       = null
    * StringUtils.replaceFirst("any", (String) null, *)   = "any"
    * StringUtils.replaceFirst("any", *, null)   = "any"
    * StringUtils.replaceFirst("", "", "zzz")    = "zzz"
    * StringUtils.replaceFirst("", ".*", "zzz")  = "zzz"
    * StringUtils.replaceFirst("", ".+", "zzz")  = ""
    * StringUtils.replaceFirst("abc", "", "ZZ")  = "ZZabc"
    * StringUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")      = "z\n&lt;__&gt;"
    * StringUtils.replaceFirst("&lt;__&gt;\n&lt;__&gt;", "(?s)&lt;.*&gt;", "z")  = "z"
    * StringUtils.replaceFirst("ABCabc123", "[a-z]", "_")          = "ABC_bc123"
    * StringUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_")  = "ABC_123abc"
    * StringUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "")   = "ABC123abc"
    * StringUtils.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_\$2")  = "Lorem_ipsum  dolor   sit"
    * </pre>
    *
    *
    * @param text        text to search and replace in, may be null
    * @param regex       the regular expression to which this string is to be matched
    * @param replacement the string to be substituted for the first match
    * @return the text with the first replacement processed,
    *         {@code null} if null String input
    * @throws java.util.regex.PatternSyntaxException
    * if the regular expression's syntax is invalid
    * @see String#replaceFirst(String, String)
    * @see java.util.regex.Pattern
    * @see java.util.regex.Pattern#DOTALL
    * @since 3.5
    * @deprecated Moved to RegExUtils.
    */
  @deprecated def replaceFirst(text: String, regex: String, replacement: String): String =
    RegExUtils.replaceFirst(text, regex, replacement)

  /**
    * <p>Case insensitively replaces all occurrences of a String within another String.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replaceIgnoreCase(null, *, *)        = null
    * StringUtils.replaceIgnoreCase("", *, *)          = ""
    * StringUtils.replaceIgnoreCase("any", null, *)    = "any"
    * StringUtils.replaceIgnoreCase("any", *, null)    = "any"
    * StringUtils.replaceIgnoreCase("any", "", *)      = "any"
    * StringUtils.replaceIgnoreCase("aba", "a", null)  = "aba"
    * StringUtils.replaceIgnoreCase("abA", "A", "")    = "b"
    * StringUtils.replaceIgnoreCase("aba", "A", "z")   = "zbz"
    * </pre>
    *
    * @see #replaceIgnoreCase(String text, String searchString, String replacement, int max)
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for (case insensitive), may be null
    * @param replacement  the String to replace it with, may be null
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    * @since 3.5
    */
  def replaceIgnoreCase(text: String, searchString: String, replacement: String): String =
    replaceIgnoreCase(text, searchString, replacement, -1)

  /**
    * <p>Case insensitively replaces a String with another String inside a larger String,
    * for the first {@code max} values of the search String.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replaceIgnoreCase(null, *, *, *)         = null
    * StringUtils.replaceIgnoreCase("", *, *, *)           = ""
    * StringUtils.replaceIgnoreCase("any", null, *, *)     = "any"
    * StringUtils.replaceIgnoreCase("any", *, null, *)     = "any"
    * StringUtils.replaceIgnoreCase("any", "", *, *)       = "any"
    * StringUtils.replaceIgnoreCase("any", *, *, 0)        = "any"
    * StringUtils.replaceIgnoreCase("abaa", "a", null, -1) = "abaa"
    * StringUtils.replaceIgnoreCase("abaa", "a", "", -1)   = "b"
    * StringUtils.replaceIgnoreCase("abaa", "a", "z", 0)   = "abaa"
    * StringUtils.replaceIgnoreCase("abaa", "A", "z", 1)   = "zbaa"
    * StringUtils.replaceIgnoreCase("abAa", "a", "z", 2)   = "zbza"
    * StringUtils.replaceIgnoreCase("abAa", "a", "z", -1)  = "zbzz"
    * </pre>
    *
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for (case insensitive), may be null
    * @param replacement  the String to replace it with, may be null
    * @param max          maximum number of values to replace, or {@code -1} if no maximum
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    * @since 3.5
    */
  def replaceIgnoreCase(text: String, searchString: String, replacement: String, max: Int): String =
    replace(text, searchString, replacement, max, true)

  /**
    * <p>Replaces a String with another String inside a larger String, once.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replaceOnce(null, *, *)        = null
    * StringUtils.replaceOnce("", *, *)          = ""
    * StringUtils.replaceOnce("any", null, *)    = "any"
    * StringUtils.replaceOnce("any", *, null)    = "any"
    * StringUtils.replaceOnce("any", "", *)      = "any"
    * StringUtils.replaceOnce("aba", "a", null)  = "aba"
    * StringUtils.replaceOnce("aba", "a", "")    = "ba"
    * StringUtils.replaceOnce("aba", "a", "z")   = "zba"
    * </pre>
    *
    * @see #replace(String text, String searchString, String replacement, int max)
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for, may be null
    * @param replacement  the String to replace with, may be null
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    */
  def replaceOnce(text: String, searchString: String, replacement: String): String =
    replace(text, searchString, replacement, 1)

  /**
    * <p>Case insensitively replaces a String with another String inside a larger String, once.</p>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replaceOnceIgnoreCase(null, *, *)        = null
    * StringUtils.replaceOnceIgnoreCase("", *, *)          = ""
    * StringUtils.replaceOnceIgnoreCase("any", null, *)    = "any"
    * StringUtils.replaceOnceIgnoreCase("any", *, null)    = "any"
    * StringUtils.replaceOnceIgnoreCase("any", "", *)      = "any"
    * StringUtils.replaceOnceIgnoreCase("aba", "a", null)  = "aba"
    * StringUtils.replaceOnceIgnoreCase("aba", "a", "")    = "ba"
    * StringUtils.replaceOnceIgnoreCase("aba", "a", "z")   = "zba"
    * StringUtils.replaceOnceIgnoreCase("FoOFoofoo", "foo", "") = "Foofoo"
    * </pre>
    *
    * @see #replaceIgnoreCase(text:String,searchString:String,replacement:String,max:Int)
    * @param text         text to search and replace in, may be null
    * @param searchString the String to search for (case insensitive), may be null
    * @param replacement  the String to replace with, may be null
    * @return the text with any replacements processed,
    *         {@code null} if null String input
    * @since 3.5
    */
  def replaceOnceIgnoreCase(text: String, searchString: String, replacement: String): String =
    replaceIgnoreCase(text, searchString, replacement, 1)

  /**
    * <p>Replaces each substring of the source String that matches the given regular expression with the given
    * replacement using the {@link java.util.regex.Pattern#DOTALL} option. DOTALL is also known as single-line mode in Perl.</p>
    *
    * This call is a {@code null} safe equivalent to:
    * <ul>
    * <li>{@code source.replaceAll(&quot;(?s)&quot; + regex, replacement)}</li>
    * <li>{@code Pattern.compile(regex, Pattern.DOTALL).matcher(source).replaceAll(replacement)}</li>
    * </ul>
    *
    * <p>A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replacePattern(null, *, *)       = null
    * StringUtils.replacePattern("any", (String) null, *)   = "any"
    * StringUtils.replacePattern("any", *, null)   = "any"
    * StringUtils.replacePattern("", "", "zzz")    = "zzz"
    * StringUtils.replacePattern("", ".*", "zzz")  = "zzz"
    * StringUtils.replacePattern("", ".+", "zzz")  = ""
    * StringUtils.replacePattern("&lt;__&gt;\n&lt;__&gt;", "&lt;.*&gt;", "z")       = "z"
    * StringUtils.replacePattern("ABCabc123", "[a-z]", "_")       = "ABC___123"
    * StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_")  = "ABC_123"
    * StringUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "")   = "ABC123"
    * StringUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_\$2")  = "Lorem_ipsum_dolor_sit"
    * </pre>
    *
    *
    * @param source
    * the source string
    * @param regex
    * the regular expression to which this string is to be matched
    * @param replacement
    * the string to be substituted for each match
    * @return The resulting {@code String}
    * @see #replaceAll(String, String, String)
    * @see String#replaceAll(String, String)
    * @see Pattern#DOTALL
    * @since 3.2
    *        3.5 Changed {@code null} reference passed to this method is a no-op.
    * @deprecated Moved to RegExUtils.
    */
  @deprecated def replacePattern(source: String, regex: String, replacement: String): String =
    RegExUtils.replacePattern(source, regex, replacement)

  /**
    * <p>Reverses a String as per {@link java.lang.StringBuilder#reverse}.</p>
    *
    * <p>A {@code null} String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.reverse(null)  = null
    * StringUtils.reverse("")    = ""
    * StringUtils.reverse("bat") = "tab"
    * </pre>
    *
    * @param str the String to reverse, may be null
    * @return the reversed String, {@code null} if null String input
    */
  def reverse(str: String): String = {
    if (str == null) return null
    new StringBuilder(str).reverse.toString
  }

  /**
    * <p>Reverses a String that is delimited by a specific character.</p>
    *
    * <p>The Strings between the delimiters are not reversed.
    * Thus java.lang.String becomes String.lang.java (if the delimiter
    * is {@code '.'}).</p>
    *
    * <pre>
    * StringUtils.reverseDelimited(null, *)      = null
    * StringUtils.reverseDelimited("", *)        = ""
    * StringUtils.reverseDelimited("a.b.c", 'x') = "a.b.c"
    * StringUtils.reverseDelimited("a.b.c", ".") = "c.b.a"
    * </pre>
    *
    * @param str           the String to reverse, may be null
    * @param separatorChar the separator character to use
    * @return the reversed String, {@code null} if null String input
    * @since 2.0
    */
  def reverseDelimited(str: String, separatorChar: Char): String = {
    if (str == null) return null
    // could implement manually, but simple way is to reuse other,
    // probably slower, methods.
    val strs: Array[String] = split(str, separatorChar)
    ArrayUtils.reverse(strs)
    join(strs, separatorChar)
  }

  /**
    * <p>Gets the rightmost {@code len} characters of a String.</p>
    *
    * <p>If {@code len} characters are not available, or the String
    * is {@code null}, the String will be returned without an
    * an exception. An empty String is returned if len is negative.</p>
    *
    * <pre>
    * StringUtils.right(null, *)    = null
    * StringUtils.right(*, -ve)     = ""
    * StringUtils.right("", *)      = ""
    * StringUtils.right("abc", 0)   = ""
    * StringUtils.right("abc", 2)   = "bc"
    * StringUtils.right("abc", 4)   = "abc"
    * </pre>
    *
    * @param str the String to get the rightmost characters from, may be null
    * @param len the length of the required String
    * @return the rightmost characters, {@code null} if null String input
    */
  def right(str: String, len: Int): String = {
    if (str == null) return null
    if (len < 0) return EMPTY
    if (str.length <= len) return str
    str.substring(str.length - len)
  }

  /**
    * <p>Right pad a String with spaces (' ').</p>
    *
    * <p>The String is padded to the size of {@code size}.</p>
    *
    * <pre>
    * StringUtils.rightPad(null, *)   = null
    * StringUtils.rightPad("", 3)     = "   "
    * StringUtils.rightPad("bat", 3)  = "bat"
    * StringUtils.rightPad("bat", 5)  = "bat  "
    * StringUtils.rightPad("bat", 1)  = "bat"
    * StringUtils.rightPad("bat", -1) = "bat"
    * </pre>
    *
    * @param str  the String to pad out, may be null
    * @param size the size to pad to
    * @return right padded String or original String if no padding is necessary,
    *         {@code null} if null String input
    */
  def rightPad(str: String, size: Int): String = rightPad(str, size, ' ')

  /**
    * <p>Right pad a String with a specified character.</p>
    *
    * <p>The String is padded to the size of {@code size}.</p>
    *
    * <pre>
    * StringUtils.rightPad(null, *, *)     = null
    * StringUtils.rightPad("", 3, 'z')     = "zzz"
    * StringUtils.rightPad("bat", 3, 'z')  = "bat"
    * StringUtils.rightPad("bat", 5, 'z')  = "batzz"
    * StringUtils.rightPad("bat", 1, 'z')  = "bat"
    * StringUtils.rightPad("bat", -1, 'z') = "bat"
    * </pre>
    *
    * @param str     the String to pad out, may be null
    * @param size    the size to pad to
    * @param padChar the character to pad with
    * @return right padded String or original String if no padding is necessary,
    *         {@code null} if null String input
    * @since 2.0
    */
  def rightPad(str: String, size: Int, padChar: Char): String = {
    if (str == null) return null
    val pads = size - str.length
    if (pads <= 0) return str
    if (pads > PAD_LIMIT) return rightPad(str, size, String.valueOf(padChar))
    str.concat(repeat(padChar, pads))
  }

  /**
    * <p>Right pad a String with a specified String.</p>
    *
    * <p>The String is padded to the size of {@code size}.</p>
    *
    * <pre>
    * StringUtils.rightPad(null, *, *)      = null
    * StringUtils.rightPad("", 3, "z")      = "zzz"
    * StringUtils.rightPad("bat", 3, "yz")  = "bat"
    * StringUtils.rightPad("bat", 5, "yz")  = "batyz"
    * StringUtils.rightPad("bat", 8, "yz")  = "batyzyzy"
    * StringUtils.rightPad("bat", 1, "yz")  = "bat"
    * StringUtils.rightPad("bat", -1, "yz") = "bat"
    * StringUtils.rightPad("bat", 5, null)  = "bat  "
    * StringUtils.rightPad("bat", 5, "")    = "bat  "
    * </pre>
    *
    * @param str    the String to pad out, may be null
    * @param size   the size to pad to
    * @param padStr the String to pad with, null or empty treated as single space
    * @return right padded String or original String if no padding is necessary,
    *         {@code null} if null String input
    */
  def rightPad(str: String, size: Int, padStr: String): String = {
    if (str == null) return null

    val newPadStr: String =
      if (isEmpty(padStr)) SPACE
      else padStr

    val padLen = newPadStr.length
    val strLen = str.length
    val pads = size - strLen
    if (pads <= 0) return str
    if (padLen == 1 && pads <= PAD_LIMIT) return rightPad(str, size, newPadStr.charAt(0))
    if (pads == padLen) str.concat(newPadStr)
    else if (pads < padLen) str.concat(newPadStr.substring(0, pads))
    else {
      val padding = new Array[Char](pads)
      val padChars = newPadStr.toCharArray
      for (i <- 0 until pads) {
        padding(i) = padChars(i % padLen)
      }
      str.concat(new String(padding))
    }
  }

  /**
    * <p>Rotate (circular shift) a String of {@code shift} characters.</p>
    * <ul>
    * <li>If {@code shift > 0}, right circular shift (ex : ABCDEF =&gt; FABCDE)</li>
    * <li>If {@code shift < 0}, left circular shift (ex : ABCDEF =&gt; BCDEFA)</li>
    * </ul>
    *
    * <pre>
    * StringUtils.rotate(null, *)        = null
    * StringUtils.rotate("", *)          = ""
    * StringUtils.rotate("abcdefg", 0)   = "abcdefg"
    * StringUtils.rotate("abcdefg", 2)   = "fgabcde"
    * StringUtils.rotate("abcdefg", -2)  = "cdefgab"
    * StringUtils.rotate("abcdefg", 7)   = "abcdefg"
    * StringUtils.rotate("abcdefg", -7)  = "abcdefg"
    * StringUtils.rotate("abcdefg", 9)   = "fgabcde"
    * StringUtils.rotate("abcdefg", -9)  = "cdefgab"
    * </pre>
    *
    * @param str   the String to rotate, may be null
    * @param shift number of time to shift (positive : right shift, negative : left shift)
    * @return the rotated String,
    *         or the original String if {@code shift == 0},
    *         or {@code null} if null String input
    * @since 3.5
    */
  def rotate(str: String, shift: Int): String = {
    if (str == null) return null
    val strLen = str.length
    if (shift == 0 || strLen == 0 || shift % strLen == 0) return str
    val builder = new StringBuilder(strLen)
    val offset = -(shift % strLen)
    builder.append(substring(str, offset))
    builder.append(substring(str, 0, offset))
    builder.toString
  }

  // TODO: @link java.lang.Character#isWhitespace(Char)
  /**
    * <p>Splits the provided text into an array, using whitespace as the
    * separator.
    * Whitespace is defined by {@code java.lang.Character#isWhitespace(Char)}.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as one separator.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.split(null)       = null
    * StringUtils.split("")         = []
    * StringUtils.split("abc def")  = ["abc", "def"]
    * StringUtils.split("abc  def") = ["abc", "def"]
    * StringUtils.split(" abc ")    = ["abc"]
    * </pre>
    *
    * @param str the String to parse, may be null
    * @return an array of parsed Strings, {@code null} if null String input
    */
  def split(str: String): Array[String] = split(str, null, -1)

  /**
    * <p>Splits the provided text into an array, separator specified.
    * This is an alternative to using StringTokenizer.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as one separator.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.split(null, *)         = null
    * StringUtils.split("", *)           = []
    * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
    * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
    * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
    * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
    * </pre>
    *
    * @param str           the String to parse, may be null
    * @param separatorChar the character used as the delimiter
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.0
    */
  def split(str: String, separatorChar: Char): Array[String] = splitWorker(str, separatorChar, false)

  /**
    * <p>Splits the provided text into an array, separators specified.
    * This is an alternative to using StringTokenizer.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as one separator.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separatorChars splits on whitespace.</p>
    *
    * <pre>
    * StringUtils.split(null, *)         = null
    * StringUtils.split("", *)           = []
    * StringUtils.split("abc def", null) = ["abc", "def"]
    * StringUtils.split("abc def", " ")  = ["abc", "def"]
    * StringUtils.split("abc  def", " ") = ["abc", "def"]
    * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
    * </pre>
    *
    * @param str            the String to parse, may be null
    * @param separatorChars the characters used as the delimiters,
    *                       {@code null} splits on whitespace
    * @return an array of parsed Strings, {@code null} if null String input
    */
  def split(str: String, separatorChars: String): Array[String] = splitWorker(str, separatorChars, -1, false)

  /**
    * <p>Splits the provided text into an array with a maximum length,
    * separators specified.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as one separator.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separatorChars splits on whitespace.</p>
    *
    * <p>If more than {@code max} delimited substrings are found, the last
    * returned string includes all characters after the first {@code max - 1}
    * returned strings (including separator characters).</p>
    *
    * <pre>
    * StringUtils.split(null, *, *)            = null
    * StringUtils.split("", *, *)              = []
    * StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
    * StringUtils.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
    * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
    * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
    * </pre>
    *
    * @param str            the String to parse, may be null
    * @param separatorChars the characters used as the delimiters,
    *                       {@code null} splits on whitespace
    * @param max            the maximum number of elements to include in the
    *                       array. A zero or negative value implies no limit
    * @return an array of parsed Strings, {@code null} if null String input
    */
  def split(str: String, separatorChars: String, max: Int): Array[String] = splitWorker(str, separatorChars, max, false)

  /**
    * <p>Splits a String by Character type as returned by
    * {@code java.lang.Character.getType(char)}. Groups of contiguous
    * characters of the same type are returned as complete tokens.
    * <pre>
    * StringUtils.splitByCharacterType(null)         = null
    * StringUtils.splitByCharacterType("")           = []
    * StringUtils.splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
    * StringUtils.splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
    * StringUtils.splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
    * StringUtils.splitByCharacterType("number5")    = ["number", "5"]
    * StringUtils.splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
    * StringUtils.splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
    * StringUtils.splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
    * </pre>
    *
    * @param str the String to split, may be {@code null}
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.4
    */
  def splitByCharacterType(str: String): Array[String] = splitByCharacterType(str, false)

  /**
    * <p>Splits a String by Character type as returned by
    * {@code java.lang.Character.getType(char)}. Groups of contiguous
    * characters of the same type are returned as complete tokens, with the
    * following exception: if {@code camelCase} is {@code true},
    * the character of type {@code Character.UPPERCASE_LETTER}, if any,
    * immediately preceding a token of type {@code Character.LOWERCASE_LETTER}
    * will belong to the following token rather than to the preceding, if any,
    * {@code Character.UPPERCASE_LETTER} token.
    *
    * @param str       the String to split, may be {@code null}
    * @param camelCase whether to use so-called "camel-case" for letter types
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.4
    */
  private def splitByCharacterType(str: String, camelCase: Boolean): Array[String] = {
    if (str == null) return null
    if (str.isEmpty) return ArrayUtils.EMPTY_STRING_ARRAY
    val c = str.toCharArray
    val list = new util.ArrayList[String]
    var tokenStart = 0
    var currentType = Character.getType(c(tokenStart))
    for (pos <- tokenStart + 1 until c.length) {
      val `type` = Character.getType(c(pos))

      if (`type` != currentType) {
        if (camelCase && `type` == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
          val newTokenStart = pos - 1
          if (newTokenStart != tokenStart) {
            list.add(new String(c, tokenStart, newTokenStart - tokenStart))
            tokenStart = newTokenStart
          }
        } else {
          list.add(new String(c, tokenStart, pos - tokenStart))
          tokenStart = pos
        }
        currentType = `type`
      }
    }
    list.add(new String(c, tokenStart, c.length - tokenStart))
    list.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
  }

  /**
    * <p>Splits a String by Character type as returned by
    * {@code java.lang.Character.getType(char)}. Groups of contiguous
    * characters of the same type are returned as complete tokens, with the
    * following exception: the character of type
    * {@code Character.UPPERCASE_LETTER}, if any, immediately
    * preceding a token of type {@code Character.LOWERCASE_LETTER}
    * will belong to the following token rather than to the preceding, if any,
    * {@code Character.UPPERCASE_LETTER} token.
    * <pre>
    * StringUtils.splitByCharacterTypeCamelCase(null)         = null
    * StringUtils.splitByCharacterTypeCamelCase("")           = []
    * StringUtils.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
    * StringUtils.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
    * StringUtils.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
    * StringUtils.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
    * StringUtils.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
    * StringUtils.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
    * StringUtils.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
    * </pre>
    *
    * @param str the String to split, may be {@code null}
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.4
    */
  def splitByCharacterTypeCamelCase(str: String): Array[String] = splitByCharacterType(str, true)

  /**
    * <p>Splits the provided text into an array, separator string specified.</p>
    *
    * <p>The separator(s) will not be included in the returned String array.
    * Adjacent separators are treated as one separator.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separator splits on whitespace.</p>
    *
    * <pre>
    * StringUtils.splitByWholeSeparator(null, *)               = null
    * StringUtils.splitByWholeSeparator("", *)                 = []
    * StringUtils.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
    * StringUtils.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
    * StringUtils.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
    * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
    * </pre>
    *
    * @param str       the String to parse, may be null
    * @param separator String containing the String to be used as a delimiter,
    *                  {@code null} splits on whitespace
    * @return an array of parsed Strings, {@code null} if null String was input
    */
  def splitByWholeSeparator(str: String, separator: String): Array[String] =
    splitByWholeSeparatorWorker(str, separator, -1, false)

  /**
    * <p>Splits the provided text into an array, separator string specified.
    * Returns a maximum of {@code max} substrings.</p>
    *
    * <p>The separator(s) will not be included in the returned String array.
    * Adjacent separators are treated as one separator.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separator splits on whitespace.</p>
    *
    * <pre>
    * StringUtils.splitByWholeSeparator(null, *, *)               = null
    * StringUtils.splitByWholeSeparator("", *, *)                 = []
    * StringUtils.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
    * StringUtils.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
    * StringUtils.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
    * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
    * StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
    * </pre>
    *
    * @param str       the String to parse, may be null
    * @param separator String containing the String to be used as a delimiter,
    *                  {@code null} splits on whitespace
    * @param max       the maximum number of elements to include in the returned
    *                  array. A zero or negative value implies no limit.
    * @return an array of parsed Strings, {@code null} if null String was input
    */
  def splitByWholeSeparator(str: String, separator: String, max: Int): Array[String] =
    splitByWholeSeparatorWorker(str, separator, max, false)

  /**
    * <p>Splits the provided text into an array, separator string specified. </p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as separators for empty tokens.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separator splits on whitespace.</p>
    *
    * <pre>
    * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *)               = null
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *)                 = []
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
    * </pre>
    *
    * @param str       the String to parse, may be null
    * @param separator String containing the String to be used as a delimiter,
    *                  {@code null} splits on whitespace
    * @return an array of parsed Strings, {@code null} if null String was input
    * @since 2.4
    */
  def splitByWholeSeparatorPreserveAllTokens(str: String, separator: String): Array[String] =
    splitByWholeSeparatorWorker(str, separator, -1, true)

  /**
    * <p>Splits the provided text into an array, separator string specified.
    * Returns a maximum of {@code max} substrings.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as separators for empty tokens.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separator splits on whitespace.</p>
    *
    * <pre>
    * StringUtils.splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
    * StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
    * </pre>
    *
    * @param str       the String to parse, may be null
    * @param separator String containing the String to be used as a delimiter,
    *                  {@code null} splits on whitespace
    * @param max       the maximum number of elements to include in the returned
    *                  array. A zero or negative value implies no limit.
    * @return an array of parsed Strings, {@code null} if null String was input
    * @since 2.4
    */
  def splitByWholeSeparatorPreserveAllTokens(str: String, separator: String, max: Int): Array[String] =
    splitByWholeSeparatorWorker(str, separator, max, true)

  /**
    * Performs the logic for the {@code splitByWholeSeparatorPreserveAllTokens} methods.
    *
    * @param str               the String to parse, may be {@code null}
    * @param separator         String containing the String to be used as a delimiter,
    *                          {@code null} splits on whitespace
    * @param max               the maximum number of elements to include in the returned
    *                          array. A zero or negative value implies no limit.
    * @param preserveAllTokens if {@code true}, adjacent separators are
    *                          treated as empty token separators; if {@code false}, adjacent
    *                          separators are treated as one separator.
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.4
    */
  private def splitByWholeSeparatorWorker(
    str: String,
    separator: String,
    max: Int,
    preserveAllTokens: Boolean): Array[String] = {
    if (str == null) return null
    val len = str.length
    if (len == 0) return ArrayUtils.EMPTY_STRING_ARRAY
    if (separator == null || EMPTY == separator) { // Split on whitespace.
      return splitWorker(str, null, max, preserveAllTokens)
    }
    val separatorLength = separator.length
    val substrings = new util.ArrayList[String]
    var numberOfSubstrings = 0
    var beg = 0
    var `end` = 0
    while ({
      `end` < len
    }) {
      `end` = str.indexOf(separator, beg)
      if (`end` > -1) if (`end` > beg) {
        numberOfSubstrings += 1
        if (numberOfSubstrings == max) {
          `end` = len
          substrings.add(str.substring(beg))
        } else { // The following is OK, because String.substring( beg, end ) excludes
          // the character at the position 'end'.
          substrings.add(str.substring(beg, `end`))
          // Set the starting point for the next search.
          // The following is equivalent to beg = end + (separatorLength - 1) + 1,
          // which is the right calculation:
          beg = `end` + separatorLength
        }
      } else { // We found a consecutive occurrence of the separator, so skip it.
        if (preserveAllTokens) {
          numberOfSubstrings += 1
          if (numberOfSubstrings == max) {
            `end` = len
            substrings.add(str.substring(beg))
          } else substrings.add(EMPTY)
        }
        beg = `end` + separatorLength
      }
      else { // String.substring( beg ) goes from 'beg' to the end of the String.
        substrings.add(str.substring(beg))
        `end` = len
      }
    }
    substrings.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
  }

  /**
    * <p>Splits the provided text into an array, using whitespace as the
    * separator, preserving all tokens, including empty tokens created by
    * adjacent separators. This is an alternative to using StringTokenizer.
    * Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as separators for empty tokens.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.splitPreserveAllTokens(null)       = null
    * StringUtils.splitPreserveAllTokens("")         = []
    * StringUtils.splitPreserveAllTokens("abc def")  = ["abc", "def"]
    * StringUtils.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
    * StringUtils.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
    * </pre>
    *
    * @param str the String to parse, may be {@code null}
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.1
    */
  def splitPreserveAllTokens(str: String): Array[String] = splitWorker(str, null, -1, true)

  /**
    * <p>Splits the provided text into an array, separator specified,
    * preserving all tokens, including empty tokens created by adjacent
    * separators. This is an alternative to using StringTokenizer.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as separators for empty tokens.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.splitPreserveAllTokens(null, *)         = null
    * StringUtils.splitPreserveAllTokens("", *)           = []
    * StringUtils.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
    * StringUtils.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
    * StringUtils.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
    * StringUtils.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
    * StringUtils.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
    * StringUtils.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
    * StringUtils.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
    * StringUtils.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
    * StringUtils.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
    * StringUtils.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
    * </pre>
    *
    * @param str           the String to parse, may be {@code null}
    * @param separatorChar the character used as the delimiter,
    *                      {@code null} splits on whitespace
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.1
    */
  def splitPreserveAllTokens(str: String, separatorChar: Char): Array[String] = splitWorker(str, separatorChar, true)

  /**
    * <p>Splits the provided text into an array, separators specified,
    * preserving all tokens, including empty tokens created by adjacent
    * separators. This is an alternative to using StringTokenizer.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as separators for empty tokens.
    * For more control over the split use the StrTokenizer class.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separatorChars splits on whitespace.</p>
    *
    * <pre>
    * StringUtils.splitPreserveAllTokens(null, *)           = null
    * StringUtils.splitPreserveAllTokens("", *)             = []
    * StringUtils.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
    * StringUtils.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
    * StringUtils.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
    * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
    * StringUtils.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
    * StringUtils.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
    * StringUtils.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
    * StringUtils.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
    * StringUtils.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
    * StringUtils.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
    * </pre>
    *
    * @param str            the String to parse, may be {@code null}
    * @param separatorChars the characters used as the delimiters,
    *                       {@code null} splits on whitespace
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.1
    */
  def splitPreserveAllTokens(str: String, separatorChars: String): Array[String] =
    splitWorker(str, separatorChars, -1, true)

  /**
    * <p>Splits the provided text into an array with a maximum length,
    * separators specified, preserving all tokens, including empty tokens
    * created by adjacent separators.</p>
    *
    * <p>The separator is not included in the returned String array.
    * Adjacent separators are treated as separators for empty tokens.
    * Adjacent separators are treated as one separator.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} separatorChars splits on whitespace.</p>
    *
    * <p>If more than {@code max} delimited substrings are found, the last
    * returned string includes all characters after the first {@code max - 1}
    * returned strings (including separator characters).</p>
    *
    * <pre>
    * StringUtils.splitPreserveAllTokens(null, *, *)            = null
    * StringUtils.splitPreserveAllTokens("", *, *)              = []
    * StringUtils.splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "de", "fg"]
    * StringUtils.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "", "", "de", "fg"]
    * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
    * StringUtils.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
    * StringUtils.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
    * StringUtils.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
    * StringUtils.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
    * </pre>
    *
    * @param str            the String to parse, may be {@code null}
    * @param separatorChars the characters used as the delimiters,
    *                       {@code null} splits on whitespace
    * @param max            the maximum number of elements to include in the
    *                       array. A zero or negative value implies no limit
    * @return an array of parsed Strings, {@code null} if null String input
    * @since 2.1
    */
  def splitPreserveAllTokens(str: String, separatorChars: String, max: Int): Array[String] =
    splitWorker(str, separatorChars, max, true)

  /**
    * Performs the logic for the {@code split} and
    * {@code splitPreserveAllTokens} methods that do not return a
    * maximum array length.
    *
    * @param str               the String to parse, may be {@code null}
    * @param separatorChar     the separate character
    * @param preserveAllTokens if {@code true}, adjacent separators are
    *                          treated as empty token separators; if {@code false}, adjacent
    *                          separators are treated as one separator.
    * @return an array of parsed Strings, {@code null} if null String input
    */
  private def splitWorker(str: String, separatorChar: Char, preserveAllTokens: Boolean): Array[String] = {
    if (str == null) return null
    val len = str.length
    if (len == 0) return ArrayUtils.EMPTY_STRING_ARRAY
    val list = new util.ArrayList[String]
    var i = 0
    var start = 0
    var `match` = false
    var lastMatch = false
    while (i < len) {
      if (str.charAt(i) == separatorChar) {
        if (`match` || preserveAllTokens) {
          list.add(str.substring(start, i))
          `match` = false
          lastMatch = true
        }

        i += 1
        start = i
      } else {
        lastMatch = false
        `match` = true
        i += 1
      }
    }

    if (`match` || preserveAllTokens && lastMatch) list.add(str.substring(start, i))

    list.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
  }

  /**
    * Performs the logic for the {@code split} and
    * {@code splitPreserveAllTokens} methods that return a maximum array
    * length.
    *
    * @param str               the String to parse, may be {@code null}
    * @param separatorChars    the separate character
    * @param max               the maximum number of elements to include in the
    *                          array. A zero or negative value implies no limit.
    * @param preserveAllTokens if {@code true}, adjacent separators are
    *                          treated as empty token separators; if {@code false}, adjacent
    *                          separators are treated as one separator.
    * @return an array of parsed Strings, {@code null} if null String input
    */
  private def splitWorker(str: String, separatorChars: String, max: Int, preserveAllTokens: Boolean): Array[String] = { // Direct code is quicker than StringTokenizer.
    // Also, StringTokenizer uses isSpace() not isWhitespace()
    if (str == null) return null
    val len = str.length
    if (len == 0) return ArrayUtils.EMPTY_STRING_ARRAY
    val list = new util.ArrayList[String]
    var sizePlus1 = 1
    var i = 0
    var start = 0
    var `match` = false
    var lastMatch = false
    if (separatorChars == null) { // Null separator means use whitespace
      while ({
        i < len
      }) {
        if (Character.isWhitespace(str.charAt(i))) {
          if (`match` || preserveAllTokens) {
            lastMatch = true
            if (sizePlus1 == max) {
              i = len
              lastMatch = false
            }
            sizePlus1 += 1
            list.add(str.substring(start, i))
            `match` = false
          }
          i += 1
          start = i
        } else {
          lastMatch = false
          `match` = true
          i += 1
        }
      }
    } else if (separatorChars.length == 1) { // Optimise 1 character case
      val sep = separatorChars.charAt(0)
      while ({
        i < len
      }) {
        if (str.charAt(i) == sep) {
          if (`match` || preserveAllTokens) {
            lastMatch = true
            if (sizePlus1 == max) {
              i = len
              lastMatch = false
            }
            sizePlus1 += 1
            list.add(str.substring(start, i))
            `match` = false
          }
          i += 1
          start = i
        } else {
          lastMatch = false
          `match` = true
          i += 1
        }
      }
    } else { // standard case
      while (i < len) {
        if (separatorChars.indexOf(str.charAt(i).toInt) >= 0) {
          if (`match` || preserveAllTokens) {
            lastMatch = true
            if (sizePlus1 == max) {
              i = len
              lastMatch = false
            }
            sizePlus1 += 1
            list.add(str.substring(start, i))
            `match` = false
          }
          i += 1
          start = i
        } else {
          lastMatch = false
          `match` = true
          i += 1
        }
      }
    }

    if (`match` || preserveAllTokens && lastMatch) list.add(str.substring(start, i))

    list.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
  }

  /**
    * <p>Check if a CharSequence starts with a specified prefix.</p>
    *
    * <p>{@code null}s are handled without exceptions. Two {@code null}
    * references are considered to be equal. The comparison is case sensitive.</p>
    *
    * <pre>
    * StringUtils.startsWith(null, null)      = true
    * StringUtils.startsWith(null, "abc")     = false
    * StringUtils.startsWith("abcdef", null)  = false
    * StringUtils.startsWith("abcdef", "abc") = true
    * StringUtils.startsWith("ABCDEF", "abc") = false
    * </pre>
    *
    * @see java.lang.String#startsWith(String)
    * @param str    the CharSequence to check, may be null
    * @param prefix the prefix to find, may be null
    * @return {@code true} if the CharSequence starts with the prefix, case sensitive, or
    *         both {@code null}
    * @since 2.4
    *        3.0 Changed signature from startsWith(String, String) to startsWith(CharSequence, CharSequence)
    */
  def startsWith(str: CharSequence, prefix: CharSequence): Boolean = startsWith(str, prefix, false)

  /**
    * <p>Check if a CharSequence starts with a specified prefix (optionally case insensitive).</p>
    *
    * @see java.lang.String#startsWith(String)
    * @param str        the CharSequence to check, may be null
    * @param prefix     the prefix to find, may be null
    * @param ignoreCase indicates whether the compare should ignore case
    *                   (case insensitive) or not.
    * @return {@code true} if the CharSequence starts with the prefix or
    *         both {@code null}
    */
  private def startsWith(str: CharSequence, prefix: CharSequence, ignoreCase: Boolean): Boolean = {
    if (str == null || prefix == null) return str eq prefix
    if (prefix.length > str.length) return false
    CharSequenceUtils.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length)
  }

  /**
    * <p>Check if a CharSequence starts with any of the provided case-sensitive prefixes.</p>
    *
    * <pre>
    * StringUtils.startsWithAny(null, null)      = false
    * StringUtils.startsWithAny(null, new String[] {"abc"})  = false
    * StringUtils.startsWithAny("abcxyz", null)     = false
    * StringUtils.startsWithAny("abcxyz", new String[] {""}) = true
    * StringUtils.startsWithAny("abcxyz", new String[] {"abc"}) = true
    * StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
    * StringUtils.startsWithAny("abcxyz", null, "xyz", "ABCX") = false
    * StringUtils.startsWithAny("ABCXYZ", null, "xyz", "abc") = false
    * </pre>
    *
    * @param sequence      the CharSequence to check, may be null
    * @param searchStrings the case-sensitive CharSequence prefixes, may be empty or contain {@code null}
    * @see StringUtils#startsWith(CharSequence, CharSequence)
    * @return {@code true} if the input {@code sequence} is {@code null} AND no {@code searchStrings} are provided, or
    *         the input {@code sequence} begins with any of the provided case-sensitive {@code searchStrings}.
    * @since 2.5
    *        3.0 Changed signature from startsWithAny(String, String[]) to startsWithAny(CharSequence, CharSequence...)
    */
  def startsWithAny(sequence: CharSequence, searchStrings: CharSequence*): Boolean =
    startsWithAny(sequence, searchStrings.toArray)

  /**
    * <p>Check if a CharSequence starts with any of the provided case-sensitive prefixes.</p>
    *
    * <pre>
    * StringUtils.startsWithAny(null, null)      = false
    * StringUtils.startsWithAny(null, new String[] {"abc"})  = false
    * StringUtils.startsWithAny("abcxyz", null)     = false
    * StringUtils.startsWithAny("abcxyz", new String[] {""}) = true
    * StringUtils.startsWithAny("abcxyz", new String[] {"abc"}) = true
    * StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
    * StringUtils.startsWithAny("abcxyz", null, "xyz", "ABCX") = false
    * StringUtils.startsWithAny("ABCXYZ", null, "xyz", "abc") = false
    * </pre>
    *
    * @param sequence      the CharSequence to check, may be null
    * @param searchStrings the case-sensitive CharSequence prefixes, may be empty or contain {@code null}
    * @see StringUtils#startsWith(CharSequence, CharSequence)
    * @return {@code true} if the input {@code sequence} is {@code null} AND no {@code searchStrings} are provided, or
    *         the input {@code sequence} begins with any of the provided case-sensitive {@code searchStrings}.
    * @since 2.5
    *        3.0 Changed signature from startsWithAny(String, String[]) to startsWithAny(CharSequence, CharSequence...)
    */
  def startsWithAny(sequence: CharSequence, searchStrings: Array[_ <: CharSequence]): Boolean = {
    if (isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) return false
    for (searchString <- searchStrings) {
      if (startsWith(sequence, searchString)) return true
    }
    false
  }

  /**
    * <p>Case insensitive check if a CharSequence starts with a specified prefix.</p>
    *
    * <p>{@code null}s are handled without exceptions. Two {@code null}
    * references are considered to be equal. The comparison is case insensitive.</p>
    *
    * <pre>
    * StringUtils.startsWithIgnoreCase(null, null)      = true
    * StringUtils.startsWithIgnoreCase(null, "abc")     = false
    * StringUtils.startsWithIgnoreCase("abcdef", null)  = false
    * StringUtils.startsWithIgnoreCase("abcdef", "abc") = true
    * StringUtils.startsWithIgnoreCase("ABCDEF", "abc") = true
    * </pre>
    *
    * @see java.lang.String#startsWith(String)
    * @param str    the CharSequence to check, may be null
    * @param prefix the prefix to find, may be null
    * @return {@code true} if the CharSequence starts with the prefix, case insensitive, or
    *         both {@code null}
    * @since 2.4
    *        3.0 Changed signature from startsWithIgnoreCase(String, String) to startsWithIgnoreCase(CharSequence, CharSequence)
    */
  def startsWithIgnoreCase(str: CharSequence, prefix: CharSequence): Boolean = startsWith(str, prefix, true)

  // TODO: @link java.lang.Character#isWhitespace(Char)
  /**
    * <p>Strips whitespace from the start and end of a String.</p>
    *
    * <p>This is similar to {@link #trim(str:String)*} but removes whitespace.
    * Whitespace is defined by {@code java.lang.Character#isWhitespace(Char)}.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.strip(null)     = null
    * StringUtils.strip("")       = ""
    * StringUtils.strip("   ")    = ""
    * StringUtils.strip("abc")    = "abc"
    * StringUtils.strip("  abc")  = "abc"
    * StringUtils.strip("abc  ")  = "abc"
    * StringUtils.strip(" abc ")  = "abc"
    * StringUtils.strip(" ab c ") = "ab c"
    * </pre>
    *
    * @param str the String to remove whitespace from, may be null
    * @return the stripped String, {@code null} if null String input
    */
  def strip(str: String): String = strip(str, null)

  // TODO: @link java.lang.Character#isWhitespace(Char)
  /**
    * <p>Strips any of a set of characters from the start and end of a String.
    * This is similar to {@link java.lang.String#trim} but allows the characters
    * to be stripped to be controlled.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * An empty string ("") input returns the empty string.</p>
    *
    * <p>If the stripChars String is {@code null}, whitespace is
    * stripped as defined by {@code java.lang.Character#isWhitespace(Char)}.
    * Alternatively use {@link #strip(str:String)*}.</p>
    *
    * <pre>
    * StringUtils.strip(null, *)          = null
    * StringUtils.strip("", *)            = ""
    * StringUtils.strip("abc", null)      = "abc"
    * StringUtils.strip("  abc", null)    = "abc"
    * StringUtils.strip("abc  ", null)    = "abc"
    * StringUtils.strip(" abc ", null)    = "abc"
    * StringUtils.strip("  abcyx", "xyz") = "  abc"
    * </pre>
    *
    * @param str        the String to remove characters from, may be null
    * @param stripChars the characters to remove, null treated as whitespace
    * @return the stripped String, {@code null} if null String input
    */
  def strip(str: String, stripChars: String): String = {
    if (isEmpty(str)) return str

    val start = stripStart(str, stripChars)
    stripEnd(start, stripChars)
  }

  /**
    * <p>Removes diacritics (~= accents) from a string. The case will not be altered.</p>
    * <p>For instance, '&agrave;' will be replaced by 'a'.</p>
    * <p>Note that ligatures will be left as is.</p>
    *
    * <pre>
    * StringUtils.stripAccents(null)                = null
    * StringUtils.stripAccents("")                  = ""
    * StringUtils.stripAccents("control")           = "control"
    * StringUtils.stripAccents("&eacute;clair")     = "eclair"
    * </pre>
    *
    * @param input String to be stripped
    * @return input text with diacritics removed
    * @since 3.0
    */
  // See also Lucene's ASCIIFoldingFilter (Lucene 2.9) that replaces accented characters by their unaccented equivalent (and uncommitted bug fix: https://issues.apache.org/jira/browse/LUCENE-1343?focusedCommentId=12858907&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#action_12858907).
  def stripAccents(input: String): String = {
    if (input == null) return null
    val decomposed = new StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFD))
    convertRemainingAccentCharacters(decomposed)
    // Note that this doesn't correctly remove ligatures...
    STRIP_ACCENTS_PATTERN.matcher(decomposed).replaceAll(EMPTY)
  }

  /**
    * <p>Strips whitespace from the start and end of every String in an array.
    * Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>A new array is returned each time, except for length zero.
    * A {@code null} array will return {@code null}.
    * An empty array will return itself.
    * A {@code null} array entry will be ignored.</p>
    *
    * <pre>
    * StringUtils.stripAll(null)             = null
    * StringUtils.stripAll([])               = []
    * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
    * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
    * </pre>
    *
    * @param strs the array to remove whitespace from, may be null
    * @return the stripped Strings, {@code null} if null array input
    */
  def stripAll(strs: String*): Array[String] = stripAll(strs.toArray, null)

  /**
    * <p>Strips whitespace from the start and end of every String in an array.
    * Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>A new array is returned each time, except for length zero.
    * A {@code null} array will return {@code null}.
    * An empty array will return itself.
    * A {@code null} array entry will be ignored.</p>
    *
    * <pre>
    * StringUtils.stripAll(null)             = null
    * StringUtils.stripAll([])               = []
    * StringUtils.stripAll(["abc", "  abc"]) = ["abc", "abc"]
    * StringUtils.stripAll(["abc  ", null])  = ["abc", null]
    * </pre>
    *
    * @param strs the array to remove whitespace from, may be null
    * @return the stripped Strings, {@code null} if null array input
    */
  def stripAll(strs: Array[String]): Array[String] = stripAll(strs, null)

  /**
    * <p>Strips any of a set of characters from the start and end of every
    * String in an array.</p>
    * <p>Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <p>A new array is returned each time, except for length zero.
    * A {@code null} array will return {@code null}.
    * An empty array will return itself.
    * A {@code null} array entry will be ignored.
    * A {@code null} stripChars will strip whitespace as defined by
    * {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.stripAll(null, *)                = null
    * StringUtils.stripAll([], *)                  = []
    * StringUtils.stripAll(["abc", "  abc"], null) = ["abc", "abc"]
    * StringUtils.stripAll(["abc  ", null], null)  = ["abc", null]
    * StringUtils.stripAll(["abc  ", null], "yz")  = ["abc  ", null]
    * StringUtils.stripAll(["yabcz", null], "yz")  = ["abc", null]
    * </pre>
    *
    * @param strs       the array to remove characters from, may be null
    * @param stripChars the characters to remove, null treated as whitespace
    * @return the stripped Strings, {@code null} if null array input
    */
  def stripAll(strs: Array[String], stripChars: String): Array[String] = {
    val strsLen = ArrayUtils.getLength(strs)
    if (strsLen == 0) return strs
    val newArr = new Array[String](strsLen)
    for (i <- 0 until strsLen) {
      newArr(i) = strip(strs(i), stripChars)
    }
    newArr
  }

  /**
    * <p>Strips any of a set of characters from the end of a String.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * An empty string ("") input returns the empty string.</p>
    *
    * <p>If the stripChars String is {@code null}, whitespace is
    * stripped as defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.stripEnd(null, *)          = null
    * StringUtils.stripEnd("", *)            = ""
    * StringUtils.stripEnd("abc", "")        = "abc"
    * StringUtils.stripEnd("abc", null)      = "abc"
    * StringUtils.stripEnd("  abc", null)    = "  abc"
    * StringUtils.stripEnd("abc  ", null)    = "abc"
    * StringUtils.stripEnd(" abc ", null)    = " abc"
    * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
    * StringUtils.stripEnd("120.00", ".0")   = "12"
    * </pre>
    *
    * @param str        the String to remove characters from, may be null
    * @param stripChars the set of characters to remove, null treated as whitespace
    * @return the stripped String, {@code null} if null String input
    */
  def stripEnd(str: String, stripChars: String): String = {
    var `end` = length(str)
    if (`end` == 0) return str
    if (stripChars == null) while ({
      `end` != 0 && Character.isWhitespace(str.charAt(`end` - 1))
    }) `end` -= 1
    else if (stripChars.isEmpty) return str
    else
      while ({
        `end` != 0 && stripChars.indexOf(str.charAt(`end` - 1).toInt) != INDEX_NOT_FOUND
      }) `end` -= 1
    str.substring(0, `end`)
  }

  /**
    * <p>Strips any of a set of characters from the start of a String.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * An empty string ("") input returns the empty string.</p>
    *
    * <p>If the stripChars String is {@code null}, whitespace is
    * stripped as defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.stripStart(null, *)          = null
    * StringUtils.stripStart("", *)            = ""
    * StringUtils.stripStart("abc", "")        = "abc"
    * StringUtils.stripStart("abc", null)      = "abc"
    * StringUtils.stripStart("  abc", null)    = "abc"
    * StringUtils.stripStart("abc  ", null)    = "abc  "
    * StringUtils.stripStart(" abc ", null)    = "abc "
    * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
    * </pre>
    *
    * @param str        the String to remove characters from, may be null
    * @param stripChars the characters to remove, null treated as whitespace
    * @return the stripped String, {@code null} if null String input
    */
  def stripStart(str: String, stripChars: String): String = {
    val strLen = length(str)
    if (strLen == 0) return str
    var start = 0
    if (stripChars == null) while ({
      start != strLen && Character.isWhitespace(str.charAt(start))
    }) start += 1
    else if (stripChars.isEmpty) return str
    else
      while ({
        start != strLen && stripChars.indexOf(str.charAt(start).toInt) != INDEX_NOT_FOUND
      }) start += 1
    str.substring(start)
  }

  /**
    * <p>Strips whitespace from the start and end of a String  returning
    * an empty String if {@code null} input.</p>
    *
    * <p>This is similar to {@link #trimToEmpty ( String )} but removes whitespace.
    * Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.stripToEmpty(null)     = ""
    * StringUtils.stripToEmpty("")       = ""
    * StringUtils.stripToEmpty("   ")    = ""
    * StringUtils.stripToEmpty("abc")    = "abc"
    * StringUtils.stripToEmpty("  abc")  = "abc"
    * StringUtils.stripToEmpty("abc  ")  = "abc"
    * StringUtils.stripToEmpty(" abc ")  = "abc"
    * StringUtils.stripToEmpty(" ab c ") = "ab c"
    * </pre>
    *
    * @param str the String to be stripped, may be null
    * @return the trimmed String, or an empty String if {@code null} input
    * @since 2.0
    */
  def stripToEmpty(str: String): String =
    if (str == null) EMPTY
    else strip(str, null)

  /**
    * <p>Strips whitespace from the start and end of a String  returning
    * {@code null} if the String is empty ("") after the strip.</p>
    *
    * <p>This is similar to {@link #trimToNull ( String )} but removes whitespace.
    * Whitespace is defined by {@link java.lang.Character# isWhitespace ( char )}.</p>
    *
    * <pre>
    * StringUtils.stripToNull(null)     = null
    * StringUtils.stripToNull("")       = null
    * StringUtils.stripToNull("   ")    = null
    * StringUtils.stripToNull("abc")    = "abc"
    * StringUtils.stripToNull("  abc")  = "abc"
    * StringUtils.stripToNull("abc  ")  = "abc"
    * StringUtils.stripToNull(" abc ")  = "abc"
    * StringUtils.stripToNull(" ab c ") = "ab c"
    * </pre>
    *
    * @param str the String to be stripped, may be null
    * @return the stripped String,
    *         {@code null} if whitespace, empty or null String input
    * @since 2.0
    */
  def stripToNull(str: String): String = {
    if (str == null) return null
    val stripped = strip(str, null)

    if (stripped.isEmpty) null
    else stripped
  }

  /**
    * <p>Gets a substring from the specified String avoiding exceptions.</p>
    *
    * <p>A negative start position can be used to start {@code n}
    * characters from the end of the String.</p>
    *
    * <p>A {@code null} String will return {@code null}.
    * An empty ("") String will return "".</p>
    *
    * <pre>
    * StringUtils.substring(null, *)   = null
    * StringUtils.substring("", *)     = ""
    * StringUtils.substring("abc", 0)  = "abc"
    * StringUtils.substring("abc", 2)  = "c"
    * StringUtils.substring("abc", 4)  = ""
    * StringUtils.substring("abc", -2) = "bc"
    * StringUtils.substring("abc", -4) = "abc"
    * </pre>
    *
    * @param str   the String to get the substring from, may be null
    * @param start the position to start from, negative means
    *              count back from the end of the String by this many characters
    * @return substring from start position, {@code null} if null String input
    */
  def substring(str: String, start: Int): String = {
    if (str == null) return null

    // handle negatives, which means last n characters
    var startIndex: Int = start
    if (startIndex < 0) startIndex = str.length + start // remember start is negative
    if (startIndex < 0) startIndex = 0
    if (startIndex > str.length) return EMPTY

    str.substring(startIndex)
  }

  /**
    * <p>Gets a substring from the specified String avoiding exceptions.</p>
    *
    * <p>A negative start position can be used to start/end {@code n}
    * characters from the end of the String.</p>
    *
    * <p>The returned substring starts with the character in the {@code start}
    * position and ends before the {@code end} position. All position counting is
    * zero-based -- i.e., to start at the beginning of the string use
    * {@code start = 0}. Negative start and end positions can be used to
    * specify offsets relative to the end of the String.</p>
    *
    * <p>If {@code start} is not strictly to the left of {@code end}, ""
    * is returned.</p>
    *
    * <pre>
    * StringUtils.substring(null, *, *)    = null
    * StringUtils.substring("", * ,  *)    = "";
    * StringUtils.substring("abc", 0, 2)   = "ab"
    * StringUtils.substring("abc", 2, 0)   = ""
    * StringUtils.substring("abc", 2, 4)   = "c"
    * StringUtils.substring("abc", 4, 6)   = ""
    * StringUtils.substring("abc", 2, 2)   = ""
    * StringUtils.substring("abc", -2, -1) = "b"
    * StringUtils.substring("abc", -4, 2)  = "ab"
    * </pre>
    *
    * @param str   the String to get the substring from, may be null
    * @param start the position to start from, negative means
    *              count back from the end of the String by this many characters
    * @param end   the position to end at (exclusive), negative means
    *              count back from the end of the String by this many characters
    * @return substring from start position to end position,
    *         {@code null} if null String input
    */
  def substring(str: String, start: Int, `end`: Int): String = {
    if (str == null) return null

    var startPos: Int = start
    var endPos: Int = `end`

    // handle negatives
    if (endPos < 0) endPos = str.length + `end` // remember end is negative
    if (startPos < 0) startPos = str.length + start

    // check length next
    if (endPos > str.length) endPos = str.length
    // if start is greater than end, return ""
    if (startPos > endPos) return EMPTY

    if (startPos < 0) startPos = 0
    if (endPos < 0) endPos = 0

    str.substring(startPos, endPos)
  }

  /**
    * <p>Gets the substring after the first occurrence of a separator.
    * The separator is not returned.</p>
    *
    * <p>A {@code null} string input will return {@code null}.
    * An empty ("") string input will return the empty string.
    *
    * <p>If nothing is found, the empty string is returned.</p>
    *
    * <pre>
    * StringUtils.substringAfter(null, *)      = null
    * StringUtils.substringAfter("", *)        = ""
    * StringUtils.substringAfter("abc", 'a')   = "bc"
    * StringUtils.substringAfter("abcba", 'b') = "cba"
    * StringUtils.substringAfter("abc", 'c')   = ""
    * StringUtils.substringAfter("abc", 'd')   = ""
    * StringUtils.substringAfter(" abc", 32)   = "abc"
    * </pre>
    *
    * @param str       the String to get a substring from, may be null
    * @param separator the character to search.
    * @return the substring after the first occurrence of the separator,
    *         {@code null} if null String input
    * @since 3.11
    */
  def substringAfter(str: String, separator: Int): String = {
    if (isEmpty(str)) return str
    val pos = str.indexOf(separator)

    if (pos == INDEX_NOT_FOUND) EMPTY
    else str.substring(pos + 1)
  }

  /**
    * <p>Gets the substring after the first occurrence of a separator.
    * The separator is not returned.</p>
    *
    * <p>A {@code null} string input will return {@code null}.
    * An empty ("") string input will return the empty string.
    * A {@code null} separator will return the empty string if the
    * input string is not {@code null}.</p>
    *
    * <p>If nothing is found, the empty string is returned.</p>
    *
    * <pre>
    * StringUtils.substringAfter(null, *)      = null
    * StringUtils.substringAfter("", *)        = ""
    * StringUtils.substringAfter(*, null)      = ""
    * StringUtils.substringAfter("abc", "a")   = "bc"
    * StringUtils.substringAfter("abcba", "b") = "cba"
    * StringUtils.substringAfter("abc", "c")   = ""
    * StringUtils.substringAfter("abc", "d")   = ""
    * StringUtils.substringAfter("abc", "")    = "abc"
    * </pre>
    *
    * @param str       the String to get a substring from, may be null
    * @param separator the String to search for, may be null
    * @return the substring after the first occurrence of the separator,
    *         {@code null} if null String input
    * @since 2.0
    */
  def substringAfter(str: String, separator: String): String = {
    if (isEmpty(str)) return str
    if (separator == null) return EMPTY
    val pos = str.indexOf(separator)

    if (pos == INDEX_NOT_FOUND) EMPTY
    else str.substring(pos + separator.length)
  }

  /**
    * <p>Gets the substring after the last occurrence of a separator.
    * The separator is not returned.</p>
    *
    * <p>A {@code null} string input will return {@code null}.
    * An empty ("") string input will return the empty string.
    *
    * <p>If nothing is found, the empty string is returned.</p>
    *
    * <pre>
    * StringUtils.substringAfterLast(null, *)      = null
    * StringUtils.substringAfterLast("", *)        = ""
    * StringUtils.substringAfterLast("abc", 'a')   = "bc"
    * StringUtils.substringAfterLast(" bc", 32)    = "bc"
    * StringUtils.substringAfterLast("abcba", 'b') = "a"
    * StringUtils.substringAfterLast("abc", 'c')   = ""
    * StringUtils.substringAfterLast("a", 'a')     = ""
    * StringUtils.substringAfterLast("a", 'z')     = ""
    * </pre>
    *
    * @param str       the String to get a substring from, may be null
    * @param separator the String to search for, may be null
    * @return the substring after the last occurrence of the separator,
    *         {@code null} if null String input
    * @since 3.11
    */
  def substringAfterLast(str: String, separator: Int): String = {
    if (isEmpty(str)) return str
    val pos = str.lastIndexOf(separator)

    if (pos == INDEX_NOT_FOUND || pos == str.length - 1) EMPTY
    else str.substring(pos + 1)
  }

  /**
    * <p>Gets the substring after the last occurrence of a separator.
    * The separator is not returned.</p>
    *
    * <p>A {@code null} string input will return {@code null}.
    * An empty ("") string input will return the empty string.
    * An empty or {@code null} separator will return the empty string if
    * the input string is not {@code null}.</p>
    *
    * <p>If nothing is found, the empty string is returned.</p>
    *
    * <pre>
    * StringUtils.substringAfterLast(null, *)      = null
    * StringUtils.substringAfterLast("", *)        = ""
    * StringUtils.substringAfterLast(*, "")        = ""
    * StringUtils.substringAfterLast(*, null)      = ""
    * StringUtils.substringAfterLast("abc", "a")   = "bc"
    * StringUtils.substringAfterLast("abcba", "b") = "a"
    * StringUtils.substringAfterLast("abc", "c")   = ""
    * StringUtils.substringAfterLast("a", "a")     = ""
    * StringUtils.substringAfterLast("a", "z")     = ""
    * </pre>
    *
    * @param str       the String to get a substring from, may be null
    * @param separator the String to search for, may be null
    * @return the substring after the last occurrence of the separator,
    *         {@code null} if null String input
    * @since 2.0
    */
  def substringAfterLast(str: String, separator: String): String = {
    if (isEmpty(str)) str
    else if (isEmpty(separator)) EMPTY
    else {
      val pos = str.lastIndexOf(separator)

      if (pos == INDEX_NOT_FOUND || pos == str.length - separator.length) EMPTY
      else str.substring(pos + separator.length)
    }
  }

  /**
    * <p>Gets the substring before the first occurrence of a separator.
    * The separator is not returned.</p>
    *
    * <p>A {@code null} string input will return {@code null}.
    * An empty ("") string input will return the empty string.
    * A {@code null} separator will return the input string.</p>
    *
    * <p>If nothing is found, the string input is returned.</p>
    *
    * <pre>
    * StringUtils.substringBefore(null, *)      = null
    * StringUtils.substringBefore("", *)        = ""
    * StringUtils.substringBefore("abc", "a")   = ""
    * StringUtils.substringBefore("abcba", "b") = "a"
    * StringUtils.substringBefore("abc", "c")   = "ab"
    * StringUtils.substringBefore("abc", "d")   = "abc"
    * StringUtils.substringBefore("abc", "")    = ""
    * StringUtils.substringBefore("abc", null)  = "abc"
    * </pre>
    *
    * @param str       the String to get a substring from, may be null
    * @param separator the String to search for, may be null
    * @return the substring before the first occurrence of the separator,
    *         {@code null} if null String input
    * @since 2.0
    */
  def substringBefore(str: String, separator: String): String = {
    if (isEmpty(str) || separator == null) str
    else if (isEmpty(separator)) EMPTY
    else {
      val pos = str.indexOf(separator)
      if (pos == INDEX_NOT_FOUND) return str
      str.substring(0, pos)
    }
  }

  /**
    * <p>Gets the substring before the last occurrence of a separator.
    * The separator is not returned.</p>
    *
    * <p>A {@code null} string input will return {@code null}.
    * An empty ("") string input will return the empty string.
    * An empty or {@code null} separator will return the input string.</p>
    *
    * <p>If nothing is found, the string input is returned.</p>
    *
    * <pre>
    * StringUtils.substringBeforeLast(null, *)      = null
    * StringUtils.substringBeforeLast("", *)        = ""
    * StringUtils.substringBeforeLast("abcba", "b") = "abc"
    * StringUtils.substringBeforeLast("abc", "c")   = "ab"
    * StringUtils.substringBeforeLast("a", "a")     = ""
    * StringUtils.substringBeforeLast("a", "z")     = "a"
    * StringUtils.substringBeforeLast("a", null)    = "a"
    * StringUtils.substringBeforeLast("a", "")      = "a"
    * </pre>
    *
    * @param str       the String to get a substring from, may be null
    * @param separator the String to search for, may be null
    * @return the substring before the last occurrence of the separator,
    *         {@code null} if null String input
    * @since 2.0
    */
  def substringBeforeLast(str: String, separator: String): String = {
    if (isEmpty(str) || isEmpty(separator)) str
    else {
      val pos = str.lastIndexOf(separator)
      if (pos == INDEX_NOT_FOUND) return str
      str.substring(0, pos)
    }
  }

  /**
    * <p>Gets the String that is nested in between two instances of the
    * same String.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} tag returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.substringBetween(null, *)            = null
    * StringUtils.substringBetween("", "")             = ""
    * StringUtils.substringBetween("", "tag")          = null
    * StringUtils.substringBetween("tagabctag", null)  = null
    * StringUtils.substringBetween("tagabctag", "")    = ""
    * StringUtils.substringBetween("tagabctag", "tag") = "abc"
    * </pre>
    *
    * @param str the String containing the substring, may be null
    * @param tag the String before and after the substring, may be null
    * @return the substring, {@code null} if no match
    * @since 2.0
    */
  def substringBetween(str: String, tag: String): String =
    substringBetween(str, tag, tag)

  /**
    * <p>Gets the String that is nested in between two Strings.
    * Only the first match is returned.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} open/close returns {@code null} (no match).
    * An empty ("") open and close returns an empty string.</p>
    *
    * <pre>
    * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
    * StringUtils.substringBetween(null, *, *)          = null
    * StringUtils.substringBetween(*, null, *)          = null
    * StringUtils.substringBetween(*, *, null)          = null
    * StringUtils.substringBetween("", "", "")          = ""
    * StringUtils.substringBetween("", "", "]")         = null
    * StringUtils.substringBetween("", "[", "]")        = null
    * StringUtils.substringBetween("yabcz", "", "")     = ""
    * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
    * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
    * </pre>
    *
    * @param str   the String containing the substring, may be null
    * @param open  the String before the substring, may be null
    * @param close the String after the substring, may be null
    * @return the substring, {@code null} if no match
    * @since 2.0
    */
  def substringBetween(str: String, open: String, close: String): String = {
    if (!ObjectUtils.allNotNull(str, open, close)) null
    else {
      val start = str.indexOf(open)
      if (start != INDEX_NOT_FOUND) {
        val `end` = str.indexOf(close, start + open.length)
        if (`end` != INDEX_NOT_FOUND)
          str.substring(start + open.length, `end`)
        else
          null
      } else {
        null
      }
    }
  }

  /**
    * <p>Searches a String for substrings delimited by a start and end tag,
    * returning all matching substrings in an array.</p>
    *
    * <p>A {@code null} input String returns {@code null}.
    * A {@code null} open/close returns {@code null} (no match).
    * An empty ("") open/close returns {@code null} (no match).</p>
    *
    * <pre>
    * StringUtils.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
    * StringUtils.substringsBetween(null, *, *)            = null
    * StringUtils.substringsBetween(*, null, *)            = null
    * StringUtils.substringsBetween(*, *, null)            = null
    * StringUtils.substringsBetween("", "[", "]")          = []
    * </pre>
    *
    * @param str   the String containing the substrings, null returns null, empty returns empty
    * @param open  the String identifying the start of the substring, empty returns null
    * @param close the String identifying the end of the substring, empty returns null
    * @return a String Array of substrings, or {@code null} if no match
    * @since 2.3
    */
  def substringsBetween(str: String, open: String, close: String): Array[String] = {
    if (str == null || isEmpty(open) || isEmpty(close)) return null
    val strLen = str.length
    if (strLen == 0) return ArrayUtils.EMPTY_STRING_ARRAY
    val closeLen = close.length
    val openLen = open.length
    val list = new util.ArrayList[String]
    var pos = 0

    breakable {
      while (pos < strLen - closeLen) {
        var start = str.indexOf(open, pos)
        if (start < 0) break()
        start += openLen
        val `end` = str.indexOf(close, start)
        if (`end` < 0) break()
        list.add(str.substring(start, `end`))
        pos = `end` + closeLen
      }
    }

    if (list.isEmpty) return null
    list.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
  }

  /**
    * <p>Swaps the case of a String changing upper and title case to
    * lower case, and lower case to upper case.</p>
    *
    * <ul>
    * <li>Upper case character converts to Lower case</li>
    * <li>Title case character converts to Lower case</li>
    * <li>Lower case character converts to Upper case</li>
    * </ul>
    *
    * <p>For a word based algorithm, see {@link org.apache.commons.lang3.text.WordUtils# swapCase ( String )}.
    * A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.swapCase(null)                 = null
    * StringUtils.swapCase("")                   = ""
    * StringUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
    * </pre>
    *
    * <p>NOTE: This method changed in Lang version 2.0.
    * It no longer performs a word based algorithm.
    * If you only use ASCII, you will notice no change.
    * That functionality is available in org.apache.commons.lang3.text.WordUtils.</p>
    *
    * @param str the String to swap case, may be null
    * @return the changed String, {@code null} if null String input
    */
  def swapCase(str: String): String = {
    if (isEmpty(str)) return str
    val strLen = str.length
    val newCodePoints = new Array[Int](strLen)
    var outOffset = 0
    var i = 0
    while ({
      i < strLen
    }) {
      val oldCodepoint = str.codePointAt(i)
      var newCodePoint = 0
      if (Character.isUpperCase(oldCodepoint) || Character.isTitleCase(oldCodepoint))
        newCodePoint = Character.toLowerCase(oldCodepoint)
      else if (Character.isLowerCase(oldCodepoint)) newCodePoint = Character.toUpperCase(oldCodepoint)
      else newCodePoint = oldCodepoint
      newCodePoints({
        outOffset += 1; outOffset - 1
      }) = newCodePoint
      i += Character.charCount(newCodePoint)
    }
    new String(newCodePoints, 0, outOffset)
  }

  /**
    * <p>Converts a {@code CharSequence} into an array of code points.</p>
    *
    * <p>Valid pairs of surrogate code units will be converted into a single supplementary
    * code point. Isolated surrogate code units (i.e. a high surrogate not followed by a low surrogate or
    * a low surrogate not preceded by a high surrogate) will be returned as-is.</p>
    *
    * <pre>
    * StringUtils.toCodePoints(null)   =  null
    * StringUtils.toCodePoints("")     =  []  // empty array
    * </pre>
    *
    * @param str the character sequence to convert
    * @return an array of code points
    * @since 3.6
    */
  def toCodePoints(str: CharSequence): Array[Int] = {
    if (str == null) return null
    if (str.length == 0) return ArrayUtils.EMPTY_INT_ARRAY
    val s = str.toString
    val result = new Array[Int](s.codePointCount(0, s.length))
    var index = 0
    for (i <- 0 until result.length) {
      result(i) = s.codePointAt(index)
      index += Character.charCount(result(i))
    }
    result
  }

  /**
    * Converts a {@code byte[]} to a String using the specified character encoding.
    *
    * @param bytes
    * the byte array to read from
    * @param charset
    * the encoding to use, if null then use the platform default
    * @return a new String
    * @throws java.lang.NullPointerException
    * if {@code bytes} is null
    * @since 3.2
    *        3.3 No longer throws {@link java.io.UnsupportedEncodingException}.
    */
  def toEncodedString(bytes: Array[Byte], charset: Charset) = new String(bytes, Charsets.toCharset(charset))

  /**
    * Converts the given source String as a lower-case using the {@link java.util.Locale# ROOT} locale in a null-safe manner.
    *
    * @param source A source String or null.
    * @return the given source String as a lower-case using the {@link java.util.Locale# ROOT} locale or null.
    * @since 3.10
    */
  def toRootLowerCase(source: String): String =
    if (source == null) null
    else source.toLowerCase(Locale.ROOT)

  /**
    * Converts the given source String as a upper-case using the {@link java.util.Locale# ROOT} locale in a null-safe manner.
    *
    * @param source A source String or null.
    * @return the given source String as a upper-case using the {@link java.util.Locale# ROOT} locale or null.
    * @since 3.10
    */
  def toRootUpperCase(source: String): String =
    if (source == null) null
    else source.toUpperCase(Locale.ROOT)

  /**
    * Converts a {@code byte[]} to a String using the specified character encoding.
    *
    * @param bytes
    * the byte array to read from
    * @param charsetName
    * the encoding to use, if null then use the platform default
    * @return a new String
    * @throws java.io.UnsupportedEncodingException
    * If the named charset is not supported
    * @throws java.lang.NullPointerException
    * if the input is null
    * @deprecated use {@link StringUtils#toEncodedString(bytes:Array[Byte],charset:Charset)*} instead of String constants in your code
    * @since 3.1
    */
  @deprecated
  @throws[UnsupportedEncodingException]
  def toString(bytes: Array[Byte], charsetName: String): String =
    if (charsetName != null) new String(bytes, charsetName)
    else new String(bytes, Charset.defaultCharset)

  /**
    * <p>Removes control characters (char &lt;= 32) from both
    * ends of this String, handling {@code null} by returning
    * {@code null}.</p>
    *
    * <p>The String is trimmed using {@link java.lang.String#trim}.
    * Trim removes start and end characters &lt;= 32.
    * To strip whitespace use {@link #strip(str:String)*}.</p>
    *
    * <p>To trim your choice of characters, use the
    * {@link #strip(str:String,stripChars:String)*} methods.</p>
    *
    * <pre>
    * StringUtils.trim(null)          = null
    * StringUtils.trim("")            = ""
    * StringUtils.trim("     ")       = ""
    * StringUtils.trim("abc")         = "abc"
    * StringUtils.trim("    abc    ") = "abc"
    * </pre>
    *
    * @param str the String to be trimmed, may be null
    * @return the trimmed string, {@code null} if null String input
    */
  def trim(str: String): String =
    if (str == null) null
    else str.trim

  /**
    * <p>Removes control characters (char &lt;= 32) from both
    * ends of this String returning an empty String ("") if the String
    * is empty ("") after the trim or if it is {@code null}.
    *
    * <p>The String is trimmed using {@link java.lang.String#trim}.
    * Trim removes start and end characters &lt;= 32.
    * To strip whitespace use {@link #stripToEmpty}.</p>
    *
    * <pre>
    * StringUtils.trimToEmpty(null)          = ""
    * StringUtils.trimToEmpty("")            = ""
    * StringUtils.trimToEmpty("     ")       = ""
    * StringUtils.trimToEmpty("abc")         = "abc"
    * StringUtils.trimToEmpty("    abc    ") = "abc"
    * </pre>
    *
    * @param str the String to be trimmed, may be null
    * @return the trimmed String, or an empty String if {@code null} input
    * @since 2.0
    */
  def trimToEmpty(str: String): String =
    if (str == null) EMPTY
    else str.trim

  /**
    * <p>Removes control characters (char &lt;= 32) from both
    * ends of this String returning {@code null} if the String is
    * empty ("") after the trim or if it is {@code null}.
    *
    * <p>The String is trimmed using {@link java.lang.String#trim}.
    * Trim removes start and end characters &lt;= 32.
    * To strip whitespace use {@link #stripToNull}.</p>
    *
    * <pre>
    * StringUtils.trimToNull(null)          = null
    * StringUtils.trimToNull("")            = null
    * StringUtils.trimToNull("     ")       = null
    * StringUtils.trimToNull("abc")         = "abc"
    * StringUtils.trimToNull("    abc    ") = "abc"
    * </pre>
    *
    * @param str the String to be trimmed, may be null
    * @return the trimmed String,
    *         {@code null} if only chars &lt;= 32, empty or null String input
    * @since 2.0
    */
  def trimToNull(str: String): String = {
    val ts = trim(str)
    if (isEmpty(ts)) null
    else ts
  }

  /**
    * <p>Truncates a String. This will turn
    * "Now is the time for all good men" into "Now is the time for".</p>
    *
    * <p>Specifically:</p>
    * <ul>
    * <li>If {@code str} is less than {@code maxWidth} characters
    * long, return it.</li>
    * <li>Else truncate it to {@code substring(str, 0, maxWidth)}.</li>
    * <li>If {@code maxWidth} is less than {@code 0}, throw an
    * {@code IllegalArgumentException}.</li>
    * <li>In no case will it return a String of length greater than
    * {@code maxWidth}.</li>
    * </ul>
    *
    * <pre>
    * StringUtils.truncate(null, 0)       = null
    * StringUtils.truncate(null, 2)       = null
    * StringUtils.truncate("", 4)         = ""
    * StringUtils.truncate("abcdefg", 4)  = "abcd"
    * StringUtils.truncate("abcdefg", 6)  = "abcdef"
    * StringUtils.truncate("abcdefg", 7)  = "abcdefg"
    * StringUtils.truncate("abcdefg", 8)  = "abcdefg"
    * StringUtils.truncate("abcdefg", -1) = throws an IllegalArgumentException
    * </pre>
    *
    * @param str      the String to truncate, may be null
    * @param maxWidth maximum length of result String, must be positive
    * @return truncated String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException If {@code maxWidth} is less than {@code 0}
    * @since 3.5
    */
  def truncate(str: String, maxWidth: Int): String = truncate(str, 0, maxWidth)

  /**
    * <p>Truncates a String. This will turn
    * "Now is the time for all good men" into "is the time for all".</p>
    *
    * <p>Works like {@code truncate(String, int)}, but allows you to specify
    * a "left edge" offset.
    *
    * <p>Specifically:</p>
    * <ul>
    * <li>If {@code str} is less than {@code maxWidth} characters
    * long, return it.</li>
    * <li>Else truncate it to {@code substring(str, offset, maxWidth)}.</li>
    * <li>If {@code maxWidth} is less than {@code 0}, throw an
    * {@code IllegalArgumentException}.</li>
    * <li>If {@code offset} is less than {@code 0}, throw an
    * {@code IllegalArgumentException}.</li>
    * <li>In no case will it return a String of length greater than
    * {@code maxWidth}.</li>
    * </ul>
    *
    * <pre>
    * StringUtils.truncate(null, 0, 0) = null
    * StringUtils.truncate(null, 2, 4) = null
    * StringUtils.truncate("", 0, 10) = ""
    * StringUtils.truncate("", 2, 10) = ""
    * StringUtils.truncate("abcdefghij", 0, 3) = "abc"
    * StringUtils.truncate("abcdefghij", 5, 6) = "fghij"
    * StringUtils.truncate("raspberry peach", 10, 15) = "peach"
    * StringUtils.truncate("abcdefghijklmno", 0, 10) = "abcdefghij"
    * StringUtils.truncate("abcdefghijklmno", -1, 10) = throws an IllegalArgumentException
    * StringUtils.truncate("abcdefghijklmno", Integer.MIN_VALUE, 10) = throws an IllegalArgumentException
    * StringUtils.truncate("abcdefghijklmno", Integer.MIN_VALUE, Integer.MAX_VALUE) = throws an IllegalArgumentException
    * StringUtils.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE) = "abcdefghijklmno"
    * StringUtils.truncate("abcdefghijklmno", 1, 10) = "bcdefghijk"
    * StringUtils.truncate("abcdefghijklmno", 2, 10) = "cdefghijkl"
    * StringUtils.truncate("abcdefghijklmno", 3, 10) = "defghijklm"
    * StringUtils.truncate("abcdefghijklmno", 4, 10) = "efghijklmn"
    * StringUtils.truncate("abcdefghijklmno", 5, 10) = "fghijklmno"
    * StringUtils.truncate("abcdefghijklmno", 5, 5) = "fghij"
    * StringUtils.truncate("abcdefghijklmno", 5, 3) = "fgh"
    * StringUtils.truncate("abcdefghijklmno", 10, 3) = "klm"
    * StringUtils.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE) = "klmno"
    * StringUtils.truncate("abcdefghijklmno", 13, 1) = "n"
    * StringUtils.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE) = "no"
    * StringUtils.truncate("abcdefghijklmno", 14, 1) = "o"
    * StringUtils.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE) = "o"
    * StringUtils.truncate("abcdefghijklmno", 15, 1) = ""
    * StringUtils.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE) = ""
    * StringUtils.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE) = ""
    * StringUtils.truncate("abcdefghij", 3, -1) = throws an IllegalArgumentException
    * StringUtils.truncate("abcdefghij", -2, 4) = throws an IllegalArgumentException
    * </pre>
    *
    * @param str      the String to truncate, may be null
    * @param offset   left edge of source String
    * @param maxWidth maximum length of result String, must be positive
    * @return truncated String, {@code null} if null String input
    * @throws java.lang.IllegalArgumentException If {@code offset} or {@code maxWidth} is less than {@code 0}
    * @since 3.5
    */
  def truncate(str: String, offset: Int, maxWidth: Int): String = {
    if (offset < 0) throw new IllegalArgumentException("offset cannot be negative")
    if (maxWidth < 0) throw new IllegalArgumentException("maxWith cannot be negative")
    if (str == null) return null
    if (offset > str.length) return EMPTY
    if (str.length > maxWidth) {
      val ix = Math.min(offset + maxWidth, str.length)
      return str.substring(offset, ix)
    }
    str.substring(offset)
  }

  /**
    * <p>Uncapitalizes a String, changing the first character to lower case as
    * per {@link java.lang.Character# toLowerCase ( int )}. No other characters are changed.</p>
    *
    * <p>For a word based algorithm, see {@link org.apache.commons.lang3.text.WordUtils# uncapitalize ( String )}.
    * A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.uncapitalize(null)  = null
    * StringUtils.uncapitalize("")    = ""
    * StringUtils.uncapitalize("cat") = "cat"
    * StringUtils.uncapitalize("Cat") = "cat"
    * StringUtils.uncapitalize("CAT") = "cAT"
    * </pre>
    *
    * @param str the String to uncapitalize, may be null
    * @return the uncapitalized String, {@code null} if null String input
    * @see org.apache.commons.lang3.text.WordUtils#uncapitalize(String)
    * @see #capitalize(String)
    * @since 2.0
    */
  def uncapitalize(str: String): String = {
    val strLen = length(str)
    if (strLen == 0) return str
    val firstCodepoint = str.codePointAt(0)
    val newCodePoint = Character.toLowerCase(firstCodepoint)
    if (firstCodepoint == newCodePoint) return str
    val newCodePoints = new Array[Int](strLen)
    var outOffset = 0
    newCodePoints({
      outOffset += 1; outOffset - 1
    }) = newCodePoint
    var inOffset = Character.charCount(firstCodepoint)
    while ({
      inOffset < strLen
    }) {
      val codepoint = str.codePointAt(inOffset)
      newCodePoints({
        outOffset += 1; outOffset - 1
      }) = codepoint
      inOffset += Character.charCount(codepoint)
    }
    new String(newCodePoints, 0, outOffset)
  }

  /**
    * <p>
    * Unwraps a given string from a character.
    * </p>
    *
    * <pre>
    * StringUtils.unwrap(null, null)         = null
    * StringUtils.unwrap(null, '\0')         = null
    * StringUtils.unwrap(null, '1')          = null
    * StringUtils.unwrap("a", 'a')           = "a"
    * StringUtils.unwrap("aa", 'a')           = ""
    * StringUtils.unwrap("\'abc\'", '\'')    = "abc"
    * StringUtils.unwrap("AABabcBAA", 'A')   = "ABabcBA"
    * StringUtils.unwrap("A", '#')           = "A"
    * StringUtils.unwrap("#A", '#')          = "#A"
    * StringUtils.unwrap("A#", '#')          = "A#"
    * </pre>
    *
    *
    * @param str
    * the String to be unwrapped, can be null
    * @param wrapChar
    * the character used to unwrap
    * @return unwrapped String or the original string
    *         if it is not quoted properly with the wrapChar
    * @since 3.6
    */
  def unwrap(str: String, wrapChar: Char): String = {
    if (isEmpty(str) || wrapChar == CharUtils.NUL || str.length == 1) return str
    if (str.charAt(0) == wrapChar && str.charAt(str.length - 1) == wrapChar) {
      val startIndex = 0
      val endIndex = str.length - 1
      return str.substring(startIndex + 1, endIndex)
    }
    str
  }

  /**
    * <p>
    * Unwraps a given string from anther string.
    * </p>
    *
    * <pre>
    * StringUtils.unwrap(null, null)         = null
    * StringUtils.unwrap(null, "")           = null
    * StringUtils.unwrap(null, "1")          = null
    * StringUtils.unwrap("a", "a")           = "a"
    * StringUtils.unwrap("aa", "a")          = ""
    * StringUtils.unwrap("\'abc\'", "\'")    = "abc"
    * StringUtils.unwrap("\"abc\"", "\"")    = "abc"
    * StringUtils.unwrap("AABabcBAA", "AA")  = "BabcB"
    * StringUtils.unwrap("A", "#")           = "A"
    * StringUtils.unwrap("#A", "#")          = "#A"
    * StringUtils.unwrap("A#", "#")          = "A#"
    * </pre>
    *
    * @param str
    * the String to be unwrapped, can be null
    * @param wrapToken
    * the String used to unwrap
    * @return unwrapped String or the original string
    *         if it is not quoted properly with the wrapToken
    * @since 3.6
    */
  def unwrap(str: String, wrapToken: String): String = {
    if (isEmpty(str) || isEmpty(wrapToken) || str.length == 1) return str
    if (startsWith(str, wrapToken) && endsWith(str, wrapToken)) {
      val startIndex = str.indexOf(wrapToken)
      val endIndex = str.lastIndexOf(wrapToken)
      val wrapLength = wrapToken.length
      if (startIndex != -1 && endIndex != -1) return str.substring(startIndex + wrapLength, endIndex)
    }
    str
  }

  // TODO: @link java.lang.String#toUpperCase()
  // TODO: @link #lowerCase(str:String,locale:Locale)
  /**
    * <p>Converts a String to upper case as per {@code java.lang.String#toUpperCase()}.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.upperCase(null)  = null
    * StringUtils.upperCase("")    = ""
    * StringUtils.upperCase("aBc") = "ABC"
    * </pre>
    *
    * <p><strong>Note:</strong> As described in the documentation for {@code java.lang.String#toUpperCase()},
    * the result of this method is affected by the current locale.
    * For platform-independent case transformations, the method {@code #lowerCase(str:String,locale:Locale)*}
    * should be used with a specific locale (e.g. {@link java.util.Locale#ENGLISH}).</p>
    *
    * @param str the String to upper case, may be null
    * @return the upper cased String, {@code null} if null String input
    */
  def upperCase(str: String): String = {
    if (str == null) return null
    str.toUpperCase
  }

  // TODO: @link java.lang.String#toUpperCase(Locale)
  /**
    * <p>Converts a String to upper case as per {@code java.lang.String#toUpperCase(Locale)}.</p>
    *
    * <p>A {@code null} input String returns {@code null}.</p>
    *
    * <pre>
    * StringUtils.upperCase(null, Locale.ENGLISH)  = null
    * StringUtils.upperCase("", Locale.ENGLISH)    = ""
    * StringUtils.upperCase("aBc", Locale.ENGLISH) = "ABC"
    * </pre>
    *
    * @param str    the String to upper case, may be null
    * @param locale the locale that defines the case transformation rules, must not be null
    * @return the upper cased String, {@code null} if null String input
    * @since 2.5
    */
  def upperCase(str: String, locale: Locale): String = {
    if (str == null) return null
    str.toUpperCase(locale)
  }

  /**
    * Returns the string representation of the {@code char} array or null.
    *
    * @param value the character array.
    * @return a String or null
    * @see String#valueOf(char[])
    * @since 3.9
    */
  def valueOf(value: Array[Char]): String =
    if (value == null) null
    else String.valueOf(value)

  /**
    * <p>
    * Wraps a string with a char.
    * </p>
    *
    * <pre>
    * StringUtils.wrap(null, *)        = null
    * StringUtils.wrap("", *)          = ""
    * StringUtils.wrap("ab", '\0')     = "ab"
    * StringUtils.wrap("ab", 'x')      = "xabx"
    * StringUtils.wrap("ab", '\'')     = "'ab'"
    * StringUtils.wrap("\"ab\"", '\"') = "\"\"ab\"\""
    * </pre>
    *
    *
    * @param str
    * the string to be wrapped, may be {@code null}
    * @param wrapWith
    * the char that will wrap {@code str}
    * @return the wrapped string, or {@code null} if {@code str==null}
    * @since 3.4
    */
  def wrap(str: String, wrapWith: Char): String = {
    if (isEmpty(str) || wrapWith == CharUtils.NUL) return str
    wrapWith + str + wrapWith
  }

  /**
    * <p>
    * Wraps a String with another String.
    * </p>
    *
    * <p>
    * A {@code null} input String returns {@code null}.
    * </p>
    *
    * <pre>
    * StringUtils.wrap(null, *)         = null
    * StringUtils.wrap("", *)           = ""
    * StringUtils.wrap("ab", null)      = "ab"
    * StringUtils.wrap("ab", "x")       = "xabx"
    * StringUtils.wrap("ab", "\"")      = "\"ab\""
    * StringUtils.wrap("\"ab\"", "\"")  = "\"\"ab\"\""
    * StringUtils.wrap("ab", "'")       = "'ab'"
    * StringUtils.wrap("'abcd'", "'")   = "''abcd''"
    * StringUtils.wrap("\"abcd\"", "'") = "'\"abcd\"'"
    * StringUtils.wrap("'abcd'", "\"")  = "\"'abcd'\""
    * </pre>
    *
    * @param str
    * the String to be wrapper, may be null
    * @param wrapWith
    * the String that will wrap str
    * @return wrapped String, {@code null} if null String input
    * @since 3.4
    */
  def wrap(str: String, wrapWith: String): String = {
    if (isEmpty(str) || isEmpty(wrapWith)) return str
    wrapWith.concat(str).concat(wrapWith)
  }

  /**
    * <p>
    * Wraps a string with a char if that char is missing from the start or end of the given string.
    * </p>
    *
    * <p>A new {@code String} will not be created if {@code str} is already wrapped.</p>
    *
    * <pre>
    * StringUtils.wrapIfMissing(null, *)        = null
    * StringUtils.wrapIfMissing("", *)          = ""
    * StringUtils.wrapIfMissing("ab", '\0')     = "ab"
    * StringUtils.wrapIfMissing("ab", 'x')      = "xabx"
    * StringUtils.wrapIfMissing("ab", '\'')     = "'ab'"
    * StringUtils.wrapIfMissing("\"ab\"", '\"') = "\"ab\""
    * StringUtils.wrapIfMissing("/", '/')  = "/"
    * StringUtils.wrapIfMissing("a/b/c", '/')  = "/a/b/c/"
    * StringUtils.wrapIfMissing("/a/b/c", '/')  = "/a/b/c/"
    * StringUtils.wrapIfMissing("a/b/c/", '/')  = "/a/b/c/"
    * </pre>
    *
    *
    * @param str
    * the string to be wrapped, may be {@code null}
    * @param wrapWith
    * the char that will wrap {@code str}
    * @return the wrapped string, or {@code null} if {@code str==null}
    * @since 3.5
    */
  def wrapIfMissing(str: String, wrapWith: Char): String = {
    if (isEmpty(str) || wrapWith == CharUtils.NUL) return str
    val wrapStart = str.charAt(0) != wrapWith
    val wrapEnd = str.charAt(str.length - 1) != wrapWith
    if (!wrapStart && !wrapEnd) return str
    val builder = new StringBuilder(str.length + 2)
    if (wrapStart) builder.append(wrapWith)
    builder.append(str)
    if (wrapEnd) builder.append(wrapWith)
    builder.toString
  }

  /**
    * <p>
    * Wraps a string with a string if that string is missing from the start or end of the given string.
    * </p>
    *
    * <p>A new {@code String} will not be created if {@code str} is already wrapped.</p>
    *
    * <pre>
    * StringUtils.wrapIfMissing(null, *)         = null
    * StringUtils.wrapIfMissing("", *)           = ""
    * StringUtils.wrapIfMissing("ab", null)      = "ab"
    * StringUtils.wrapIfMissing("ab", "x")       = "xabx"
    * StringUtils.wrapIfMissing("ab", "\"")      = "\"ab\""
    * StringUtils.wrapIfMissing("\"ab\"", "\"")  = "\"ab\""
    * StringUtils.wrapIfMissing("ab", "'")       = "'ab'"
    * StringUtils.wrapIfMissing("'abcd'", "'")   = "'abcd'"
    * StringUtils.wrapIfMissing("\"abcd\"", "'") = "'\"abcd\"'"
    * StringUtils.wrapIfMissing("'abcd'", "\"")  = "\"'abcd'\""
    * StringUtils.wrapIfMissing("/", "/")  = "/"
    * StringUtils.wrapIfMissing("a/b/c", "/")  = "/a/b/c/"
    * StringUtils.wrapIfMissing("/a/b/c", "/")  = "/a/b/c/"
    * StringUtils.wrapIfMissing("a/b/c/", "/")  = "/a/b/c/"
    * </pre>
    *
    * @param str
    * the string to be wrapped, may be {@code null}
    * @param wrapWith
    * the string that will wrap {@code str}
    * @return the wrapped string, or {@code null} if {@code str==null}
    * @since 3.5
    */
  def wrapIfMissing(str: String, wrapWith: String): String = {
    if (isEmpty(str) || isEmpty(wrapWith)) return str
    val wrapStart = !str.startsWith(wrapWith)
    val wrapEnd = !str.endsWith(wrapWith)
    if (!wrapStart && !wrapEnd) return str
    val builder = new StringBuilder(str.length + wrapWith.length + wrapWith.length)
    if (wrapStart) builder.append(wrapWith)
    builder.append(str)
    if (wrapEnd) builder.append(wrapWith)
    builder.toString
  }
}
