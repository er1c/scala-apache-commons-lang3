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

import java.text.Format
import java.text.MessageFormat
import java.text.ParsePosition
import java.util
import java.util.Locale
import java.util.Objects
import org.apache.commons.lang3.ObjectUtils
import org.apache.commons.lang3.Validate
import scala.collection.JavaConverters._
import scala.collection.{immutable, mutable}

@deprecated
@SerialVersionUID(-2362048321261811743L)
object ExtendedMessageFormat {
  private val HASH_SEED = 31
  private val DUMMY_PATTERN = ""
  private val START_FMT = ','
  private val END_FE = '}'
  private val START_FE = '{'
  private val QUOTE = '\''
}

/**
  * Extends {@code java.text.MessageFormat} to allow pluggable/additional formatting
  * options for embedded format elements.  Client code should specify a registry
  * of {@code FormatFactory} instances associated with {@code String}
  * format names.  This registry will be consulted when the format elements are
  * parsed from the message pattern.  In this way custom patterns can be specified,
  * and the formats supported by {@code java.text.MessageFormat} can be overridden
  * at the format and/or format style level (see MessageFormat).  A "format element"
  * embedded in the message pattern is specified (<b>()?</b> signifies optionality):<br>
  * {@code {}<i>argument-number</i><b>(</b>{@code ,}<i>format-name</i><b>
  * (</b>{@code ,}<i>format-style</i><b>)?)?</b>{@code }}
  *
  * <p>
  * <i>format-name</i> and <i>format-style</i> values are trimmed of surrounding whitespace
  * in the manner of {@code java.text.MessageFormat}.  If <i>format-name</i> denotes
  * {@code FormatFactory formatFactoryInstance} in {@code registry}, a {@code Format}
  * matching <i>format-name</i> and <i>format-style</i> is requested from
  * {@code formatFactoryInstance}.  If this is successful, the {@code Format}
  * found is used for this format element.
  * </p>
  *
  * <p><b>NOTICE:</b> The various subformat mutator methods are considered unnecessary; they exist on the parent
  * class to allow the type of customization which it is the job of this class to provide in
  * a configurable fashion.  These methods have thus been disabled and will throw
  * {@code UnsupportedOperationException} if called.
  * </p>
  *
  * <p>Limitations inherited from {@code java.text.MessageFormat}:</p>
  * <ul>
  * <li>When using "choice" subformats, support for nested formatting instructions is limited
  * to that provided by the base class.</li>
  * <li>Thread-safety of {@code Format}s, including {@code MessageFormat} and thus
  * {@code ExtendedMessageFormat}, is not guaranteed.</li>
  * </ul>
  *
  * Create a new ExtendedMessageFormat.
  *
  * @param pattern  the pattern to use, not null
  * @param locale   the locale to use, not null
  * @param registry the registry of format factories, may be null
  * @throws java.lang.IllegalArgumentException in case of a bad pattern.
  * @since 2.4
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/ExtendedMessageFormat.html">
  *             ExtendedMessageFormat</a> instead
  */
@deprecated
@SerialVersionUID(-2362048321261811743L)
class ExtendedMessageFormat(
  private val pattern: String,
  private val locale: Locale,
  private val registry: mutable.Map[String, _ <: FormatFactory]
) extends MessageFormat(ExtendedMessageFormat.DUMMY_PATTERN) {
  private var _toPattern: String = null

  setLocale(locale)
  applyPattern(pattern)

  def this(pattern: String, locale: Locale, registry: immutable.Map[String, _ <: FormatFactory]) {
    this(pattern, locale, mutable.Map(registry.toSeq: _*))
  }

  def this(pattern: String, locale: Locale, registry: java.util.Map[String, _ <: FormatFactory]) {
    this(pattern, locale, registry.asScala)
  }

  /**
    * Create a new ExtendedMessageFormat.
    *
    * @param pattern the pattern to use, not null
    * @param locale  the locale to use, not null
    * @throws java.lang.IllegalArgumentException in case of a bad pattern.
    */
  def this(pattern: String, locale: Locale) {
    this(pattern, locale, null.asInstanceOf[mutable.Map[String, _ <: FormatFactory]])
  }

  /**
    * Create a new ExtendedMessageFormat for the default locale.
    *
    * @param pattern the pattern to use, not null
    * @throws java.lang.IllegalArgumentException in case of a bad pattern.
    */
  def this(pattern: String) {
    this(pattern, Locale.getDefault)
  }

  /**
    * Create a new ExtendedMessageFormat for the default locale.
    *
    * @param pattern  the pattern to use, not null
    * @param registry the registry of format factories, may be null
    * @throws java.lang.IllegalArgumentException in case of a bad pattern.
    */
  def this(pattern: String, registry: util.Map[String, _ <: FormatFactory]) {
    this(pattern, Locale.getDefault, registry)
  }

  override def toPattern: String = _toPattern

  /**
    * Apply the specified pattern.
    *
    * @param pattern String
    */
  override final def applyPattern(pattern: String): Unit = {
    if (registry == null) {
      super.applyPattern(pattern)
      this._toPattern = super.toPattern
      return
    }
    val foundFormats = new util.ArrayList[Format]
    val foundDescriptions = new util.ArrayList[String]
    val stripCustom = new java.lang.StringBuilder(pattern.length)
    val pos = new ParsePosition(0)
    val c = pattern.toCharArray
    var fmtCount = 0
    while (pos.getIndex < pattern.length) {
      c(pos.getIndex) match {
        case ExtendedMessageFormat.QUOTE =>
          appendQuotedString(pattern, pos, stripCustom)

        case ExtendedMessageFormat.START_FE =>
          fmtCount += 1
          seekNonWs(pattern, pos)
          val start = pos.getIndex
          val index = readArgumentIndex(pattern, next(pos))
          stripCustom.append(ExtendedMessageFormat.START_FE).append(index)
          seekNonWs(pattern, pos)
          var format: Format = null
          var formatDescription: String = null
          if (c(pos.getIndex) == ExtendedMessageFormat.START_FMT) {
            formatDescription = parseFormatDescription(pattern, next(pos))
            format = getFormat(formatDescription)
            if (format == null) stripCustom.append(ExtendedMessageFormat.START_FMT).append(formatDescription)
          }
          foundFormats.add(format)
          foundDescriptions.add({
            if (format == null) null
            else formatDescription
          })
          Validate.isTrue(foundFormats.size == fmtCount)
          Validate.isTrue(foundDescriptions.size == fmtCount)
          if (c(pos.getIndex) != ExtendedMessageFormat.END_FE)
            throw new IllegalArgumentException("Unreadable format element at position " + start)
        //$FALL-THROUGH$
        case _ =>
      }

      stripCustom.append(c(pos.getIndex))
      next(pos)
    }

    super.applyPattern(stripCustom.toString)
    this._toPattern = insertFormats(super.toPattern, foundDescriptions)
    if (containsElements(foundFormats)) {
      val origFormats = getFormats
      // only loop over what we know we have, as MessageFormat on Java 1.3
      // seems to provide an extra format element:
      var i = 0
      val it = foundFormats.iterator
      while ({
        it.hasNext
      }) {
        val f = it.next
        if (f != null) origFormats(i) = f

        i += 1
      }
      super.setFormats(origFormats)
    }
  }

  /**
    * Throws UnsupportedOperationException - see class Javadoc for details.
    *
    * @param formatElementIndex format element index
    * @param newFormat          the new format
    * @throws java.lang.UnsupportedOperationException always thrown since this isn't supported by ExtendMessageFormat
    */
  override def setFormat(formatElementIndex: Int, newFormat: Format): Unit = {
    throw new UnsupportedOperationException
  }

  /**
    * Throws UnsupportedOperationException - see class Javadoc for details.
    *
    * @param argumentIndex argument index
    * @param newFormat     the new format
    * @throws java.lang.UnsupportedOperationException always thrown since this isn't supported by ExtendMessageFormat
    */
  override def setFormatByArgumentIndex(argumentIndex: Int, newFormat: Format): Unit = {
    throw new UnsupportedOperationException
  }

  /**
    * Throws UnsupportedOperationException - see class Javadoc for details.
    *
    * @param newFormats new formats
    * @throws java.lang.UnsupportedOperationException always thrown since this isn't supported by ExtendMessageFormat
    */
  override def setFormats(newFormats: Array[Format]): Unit = {
    throw new UnsupportedOperationException
  }

  override def setFormatsByArgumentIndex(newFormats: Array[Format]): Unit = {
    throw new UnsupportedOperationException
  }

  /**
    * Check if this extended message format is equal to another object.
    *
    * @param obj the object to compare to
    * @return true if this object equals the other, otherwise false
    */
  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[AnyRef] && (obj.asInstanceOf[AnyRef] eq this)) return true
    if (obj == null) return false
    if (!super.equals(obj)) return false
    if (ObjectUtils.notEqual(getClass, obj.getClass)) return false
    val rhs = obj.asInstanceOf[ExtendedMessageFormat]
    if (ObjectUtils.notEqual(toPattern, rhs.toPattern)) return false
    !ObjectUtils.notEqual(registry, rhs.registry)
  }

  override def hashCode: Int = {
    var result = super.hashCode
    result = ExtendedMessageFormat.HASH_SEED * result + Objects.hashCode(registry)
    result = ExtendedMessageFormat.HASH_SEED * result + Objects.hashCode(toPattern)
    result
  }

  /**
    * Gets a custom format from a format description.
    *
    * @param desc String
    * @return Format
    */
  private def getFormat(desc: String): Format = {
    if (registry != null) {
      var name = desc
      var args: String = null
      val i = desc.indexOf(ExtendedMessageFormat.START_FMT)
      if (i > 0) {
        name = desc.substring(0, i).trim
        args = desc.substring(i + 1).trim
      }
      val factory: FormatFactory = registry.getOrElse(name, null: FormatFactory)
      if (factory != null) return factory.getFormat(name, args, getLocale)
    }
    null
  }

  /**
    * Read the argument index from the current format element
    *
    * @param pattern pattern to parse
    * @param pos     current parse position
    * @return argument index
    */
  private def readArgumentIndex(pattern: String, pos: ParsePosition): Int = {
    val start = pos.getIndex
    seekNonWs(pattern, pos)
    val result = new StringBuilder
    var error = false

    while (!error && pos.getIndex < pattern.length) {
      var c = pattern.charAt(pos.getIndex)
      var _cont: Boolean = false

      if (Character.isWhitespace(c)) {
        seekNonWs(pattern, pos)
        c = pattern.charAt(pos.getIndex)
        if (c != ExtendedMessageFormat.START_FMT && c != ExtendedMessageFormat.END_FE) {
          error = true
          _cont = true
        }
      }

      if (!_cont) {
        if ((c == ExtendedMessageFormat.START_FMT || c == ExtendedMessageFormat.END_FE) && result.length > 0) {
          try {
            return result.toString.toInt
          } catch {
            case _: NumberFormatException =>
            // NOPMD
            // we've already ensured only digits, so unless something
            // outlandishly large was specified we should be okay.
          }
        }

        error = !Character.isDigit(c)
        result.append(c)

        next(pos)
      }
    }

    if (error)
      throw new IllegalArgumentException(
        "Invalid format argument index at position " + start + ": " + pattern.substring(start, pos.getIndex))
    throw new IllegalArgumentException("Unterminated format element at position " + start)
  }

  /**
    * Parse the format component of a format element.
    *
    * @param pattern string to parse
    * @param pos     current parse position
    * @return Format description String
    */
  private def parseFormatDescription(pattern: String, pos: ParsePosition): String = {
    val start = pos.getIndex
    seekNonWs(pattern, pos)
    val text = pos.getIndex
    var depth = 1

    while ({
      pos.getIndex < pattern.length
    }) {
      pattern.charAt(pos.getIndex) match {
        case ExtendedMessageFormat.START_FE =>
          depth += 1

        case ExtendedMessageFormat.END_FE =>
          depth -= 1
          if (depth == 0) return pattern.substring(text, pos.getIndex)

        case ExtendedMessageFormat.QUOTE =>
          getQuotedString(pattern, pos)

        case _ =>
      }

      next(pos)
    }
    throw new IllegalArgumentException("Unterminated format element at position " + start)
  }

  /**
    * Insert formats back into the pattern for toPattern() support.
    *
    * @param pattern        source
    * @param customPatterns The custom patterns to re-insert, if any
    * @return full pattern
    */
  private def insertFormats(pattern: String, customPatterns: util.ArrayList[String]): String = {
    if (!containsElements(customPatterns)) return pattern
    val sb = new java.lang.StringBuilder(pattern.length * 2)
    val pos = new ParsePosition(0)
    var fe = -1
    var depth = 0
    while (pos.getIndex < pattern.length) {
      val c = pattern.charAt(pos.getIndex)
      c match {
        case ExtendedMessageFormat.QUOTE =>
          appendQuotedString(pattern, pos, sb)

        case ExtendedMessageFormat.START_FE =>
          depth += 1
          sb.append(ExtendedMessageFormat.START_FE).append(readArgumentIndex(pattern, next(pos)))
          // do not look for custom patterns when they are embedded, e.g. in a choice
          if (depth == 1) {
            fe += 1
            val customPattern = customPatterns.get(fe)
            if (customPattern != null) sb.append(ExtendedMessageFormat.START_FMT).append(customPattern)
          }

        case ExtendedMessageFormat.END_FE =>
          depth -= 1
        case _ =>
          sb.append(c)
          next(pos)
      }
    }
    sb.toString
  }

  /**
    * Consume whitespace from the current parse position.
    *
    * @param pattern String to read
    * @param pos     current position
    */
  private def seekNonWs(pattern: String, pos: ParsePosition): Unit = {
    var len = 0
    val buffer = pattern.toCharArray
    do {
      len = StrMatcher.splitMatcher.isMatch(buffer, pos.getIndex)
      pos.setIndex(pos.getIndex + len)
    } while ({
      len > 0 && pos.getIndex < pattern.length
    })
  }

  /**
    * Convenience method to advance parse position by 1
    *
    * @param pos ParsePosition
    * @return {@code pos}
    */
  private def next(pos: ParsePosition) = {
    pos.setIndex(pos.getIndex + 1)
    pos
  }

  /**
    * Consume a quoted string, adding it to {@code appendTo} if
    * specified.
    *
    * @param pattern  pattern to parse
    * @param pos      current parse position
    * @param appendTo optional StringBuilder to append
    * @return {@code appendTo}
    */
  private def appendQuotedString(
    pattern: String,
    pos: ParsePosition,
    appendTo: java.lang.StringBuilder): java.lang.StringBuilder = {
    assert(
      pattern.toCharArray()(pos.getIndex) == ExtendedMessageFormat.QUOTE,
      "Quoted string must start with quote character")
    // handle quote character at the beginning of the string
    if (appendTo != null) appendTo.append(ExtendedMessageFormat.QUOTE)
    next(pos)
    val start = pos.getIndex
    val c = pattern.toCharArray
    val lastHold = start
    for (_ <- pos.getIndex until pattern.length) {
      if (c(pos.getIndex) == ExtendedMessageFormat.QUOTE) {
        next(pos)
        return if (appendTo == null) null
        else appendTo.append(c, lastHold, pos.getIndex - lastHold)
      }
      next(pos)
    }
    throw new IllegalArgumentException("Unterminated quoted string at position " + start)
  }

  /**
    * Consume quoted string only
    *
    * @param pattern pattern to parse
    * @param pos     current parse position
    */
  private def getQuotedString(pattern: String, pos: ParsePosition): Unit = {
    appendQuotedString(pattern, pos, null)
    ()
  }

  /**
    * Learn whether the specified Collection contains non-null elements.
    *
    * @param coll to check
    * @return {@code true} if some Object was found, {@code false} otherwise.
    */
  private def containsElements(coll: util.Collection[_]): Boolean = {
    if (coll == null || coll.isEmpty) return false
    for (name <- coll.asScala) {
      if (name != null) return true
    }
    false
  }
}
