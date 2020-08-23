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

import java.util.FormattableFlags.LEFT_JUSTIFY
import java.util.Formattable
import java.util.Formatter
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.Validate

/**
  * <p>Provides utilities for working with the {@code Formattable} interface.</p>
  *
  * <p>The {@link java.util.Formattable} interface provides basic control over formatting
  * when using a {@code Formatter}. It is primarily concerned with numeric precision
  * and padding, and is not designed to allow generalised alternate formats.</p>
  *
  * @since 3.0
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/FormattableUtils.html">
  *             FormattableUtils</a> instead
  */
@deprecated object FormattableUtils {
  /**
    * A format that simply outputs the value as a string.
    */
  private val SIMPLEST_FORMAT = "%s"

  /**
    * Gets the default formatted representation of the specified
    * {@code Formattable}.
    *
    * @param formattable the instance to convert to a string, not null
    * @return the resulting string, not null
    */
  def toString(formattable: Formattable): String = String.format(SIMPLEST_FORMAT, formattable)

  /**
    * Handles the common {@code Formattable} operations of truncate-pad-append,
    * with no ellipsis on precision overflow, and padding width underflow with
    * spaces.
    *
    * @param seq       the string to handle, not null
    * @param formatter the destination formatter, not null
    * @param flags     the flags for formatting, see {@code Formattable}
    * @param width     the width of the output, see {@code Formattable}
    * @param precision the precision of the output, see {@code Formattable}
    * @return the {@code formatter} instance, not null
    */
  def append(seq: CharSequence, formatter: Formatter, flags: Int, width: Int, precision: Int): Formatter =
    append(seq, formatter, flags, width, precision, ' ', null)

  /**
    * Handles the common {@link java.util.Formattable} operations of truncate-pad-append,
    * with no ellipsis on precision overflow.
    *
    * @param seq       the string to handle, not null
    * @param formatter the destination formatter, not null
    * @param flags     the flags for formatting, see {@code Formattable}
    * @param width     the width of the output, see {@code Formattable}
    * @param precision the precision of the output, see {@code Formattable}
    * @param padChar   the pad character to use
    * @return the {@code formatter} instance, not null
    */
  def append(
    seq: CharSequence,
    formatter: Formatter,
    flags: Int,
    width: Int,
    precision: Int,
    padChar: Char): Formatter = append(seq, formatter, flags, width, precision, padChar, null)

  /**
    * Handles the common {@link java.util.Formattable} operations of truncate-pad-append,
    * padding width underflow with spaces.
    *
    * @param seq       the string to handle, not null
    * @param formatter the destination formatter, not null
    * @param flags     the flags for formatting, see {@code Formattable}
    * @param width     the width of the output, see {@code Formattable}
    * @param precision the precision of the output, see {@code Formattable}
    * @param ellipsis  the ellipsis to use when precision dictates truncation, null or
    *                  empty causes a hard truncation
    * @return the {@code formatter} instance, not null
    */
  def append(
    seq: CharSequence,
    formatter: Formatter,
    flags: Int,
    width: Int,
    precision: Int,
    ellipsis: CharSequence): Formatter = append(seq, formatter, flags, width, precision, ' ', ellipsis)

  /**
    * Handles the common {@link java.util.Formattable} operations of truncate-pad-append.
    *
    * @param seq       the string to handle, not null
    * @param formatter the destination formatter, not null
    * @param flags     the flags for formatting, see {@code Formattable}
    * @param width     the width of the output, see {@code Formattable}
    * @param precision the precision of the output, see {@code Formattable}
    * @param padChar   the pad character to use
    * @param ellipsis  the ellipsis to use when precision dictates truncation, null or
    *                  empty causes a hard truncation
    * @return the {@code formatter} instance, not null
    */
  def append(
    seq: CharSequence,
    formatter: Formatter,
    flags: Int,
    width: Int,
    precision: Int,
    padChar: Char,
    ellipsis: CharSequence): Formatter = {
    Validate.isTrue(
      ellipsis == null || precision < 0 || ellipsis.length <= precision,
      "Specified ellipsis '%1$s' exceeds precision of %2$s",
      ellipsis,
      Integer.valueOf(precision))
    val buf = new java.lang.StringBuilder(seq)
    if (precision >= 0 && precision < seq.length) {
      val _ellipsis = ObjectUtils.defaultIfNull(ellipsis, StringUtils.EMPTY)
      buf.replace(precision - _ellipsis.length, seq.length, _ellipsis.toString)
    }
    val leftJustify = (flags & LEFT_JUSTIFY) == LEFT_JUSTIFY
    for (i <- buf.length until width) {
      buf.insert(
        if (leftJustify) i
        else 0,
        padChar)
    }
    formatter.format(buf.toString)
    formatter
  }
}
