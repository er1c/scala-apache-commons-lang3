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

import java.lang.{StringBuilder => JStringBuilder}
import java.io.IOException
import java.io.Reader
import java.io.Serializable
import java.io.Writer
import java.nio.CharBuffer
import java.util
import java.util.Objects
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.CharUtils
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.builder.Builder

/**
  * Builds a string from constituent parts providing a more flexible and powerful API
  * than StringBuffer.
  * <p>
  * The main differences from StringBuffer/StringBuilder are:
  * </p>
  * <ul>
  * <li>Not synchronized</li>
  * <li>Not final</li>
  * <li>Subclasses have direct access to character array</li>
  * <li>Additional methods
  * <ul>
  * <li>appendWithSeparators - adds an array of values, with a separator</li>
  * <li>appendPadding - adds a length padding characters</li>
  * <li>appendFixedLength - adds a fixed width field to the builder</li>
  * <li>toCharArray/getChars - simpler ways to get a range of the character array</li>
  * <li>delete - delete char or string</li>
  * <li>replace - search and replace for a char or string</li>
  * <li>leftString/rightString/midString - substring without exceptions</li>
  * <li>contains - whether the builder contains a char or string</li>
  * <li>size/clear/isEmpty - collections style API methods</li>
  * </ul>
  * </li>
  * <li>Views
  * <ul>
  * <li>asTokenizer - uses the internal buffer as the source of a StrTokenizer</li>
  * <li>asReader - uses the internal buffer as the source of a Reader</li>
  * <li>asWriter - allows a Writer to write directly to the internal buffer</li>
  * </ul>
  * </li>
  * </ul>
  * <p>
  * The aim has been to provide an API that mimics very closely what StringBuffer
  * provides, but with additional methods. It should be noted that some edge cases,
  * with invalid indices or null input, have been altered - see individual methods.
  * The biggest of these changes is that by default, null will not output the text
  * 'null'. This can be controlled by a property, {@link StrBuilder#setNullText}.
  * <p>
  * Prior to 3.0, this class implemented Cloneable but did not implement the
  * clone method so could not be used. From 3.0 onwards it no longer implements
  * the interface.
  *
  * @since 2.2
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/TextStringBuilder.html">
  *             TextStringBuilder</a> instead
  */
@deprecated
@SerialVersionUID(7628716375283629643L)
object StrBuilder {
  /**
    * The extra capacity for new builders.
    */
  private[text] val CAPACITY = 32
}

/**
  * Constructor that creates an empty builder initial capacity 32 characters.
  */
@deprecated
@SerialVersionUID(7628716375283629643L)
class StrBuilder() extends CharSequence with Appendable with Serializable with Builder[String] { self =>
  /** Internal data storage. */
  protected[text] var buffer: Array[Char] = new Array[Char](StrBuilder.CAPACITY) // TODO make private?

  /** Current size of the buffer. */
  protected var bufferSize = 0
  /** The new line. */
  private var newLine: String = null
  /** The null text. */
  private var nullText: String = null

  /**
    * Constructor that creates an empty builder the specified initial capacity.
    *
    * @param initialCapacity the initial capacity, zero or less will be converted to 32
    */
  def this(initialCapacity: Int) {
    this()
    @inline def capacity: Int = if (initialCapacity <= 0) StrBuilder.CAPACITY else initialCapacity
    this.buffer = new Array[Char](capacity)
  }

  /**
    * Constructor that creates a builder from the string, allocating
    * 32 extra characters for growth.
    *
    * @param str the string to copy, null treated as blank string
    */
  def this(str: String) {
    this()
    if (str == null) this.buffer = new Array[Char](StrBuilder.CAPACITY)
    else {
      this.buffer = new Array[Char](str.length + StrBuilder.CAPACITY)
      append(str)
    }
  }

  /**
    * Gets the text to be appended when a new line is added.
    *
    * @return the new line text, null means use system default
    */
  def getNewLineText: String = newLine

  /**
    * Sets the text to be appended when a new line is added.
    *
    * @param newLine the new line text, null means use system default
    * @return this, to enable chaining
    */
  def setNewLineText(newLine: String): StrBuilder = {
    this.newLine = newLine
    this
  }

  /**
    * Gets the text to be appended when null is added.
    *
    * @return the null text, null means no append
    */
  def getNullText: String = nullText

  /**
    * Sets the text to be appended when null is added.
    *
    * @param nullText the null text, null means no append
    * @return this, to enable chaining
    */
  def setNullText(nullText: String): StrBuilder = {
    if (nullText != null && nullText.isEmpty) this.nullText = null
    else this.nullText = nullText
    this
  }

  /**
    * Gets the length of the string builder.
    *
    * @return the length
    */
  override def length: Int = size

  /**
    * Updates the length of the builder by either dropping the last characters
    * or adding filler of Unicode zero.
    *
    * @param length the length to set to, must be zero or positive
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the length is negative
    */
  def setLength(length: Int): StrBuilder = {
    if (length < 0) throw new StringIndexOutOfBoundsException(length)
    if (length < size) bufferSize = length
    else if (length > size) {
      ensureCapacity(length)
      val oldEnd = size
      val newEnd = length
      bufferSize = length
      for (i <- oldEnd until newEnd) {
        buffer(i) = CharUtils.NUL
      }
    }
    this
  }

  /**
    * Gets the current size of the internal character array buffer.
    *
    * @return the capacity
    */
  def capacity: Int = buffer.length

  /**
    * Checks the capacity and ensures that it is at least the size specified.
    *
    * @param capacity the capacity to ensure
    * @return this, to enable chaining
    */
  def ensureCapacity(capacity: Int): StrBuilder = {
    if (capacity > buffer.length) {
      val old = buffer
      buffer = new Array[Char](capacity * 2)
      Array.copy(old, 0, buffer, 0, size)
    }
    this
  }

  /**
    * Minimizes the capacity to the actual length of the string.
    *
    * @return this, to enable chaining
    */
  def minimizeCapacity: StrBuilder = {
    if (buffer.length > length) {
      val old = buffer
      buffer = new Array[Char](length)
      Array.copy(old, 0, buffer, 0, size)
    }
    this
  }

  /**
    * Gets the length of the string builder.
    * <p>
    * This method is the same as {@link #length} and is provided to match the
    * API of Collections.
    *
    * @return the length
    */
  def size: Int = bufferSize

  /**
    * Checks is the string builder is empty (convenience Collections API style method).
    * <p>
    * This method is the same as checking {@link #length} and is provided to match the
    * API of Collections.
    *
    * @return {@code true} if the size is {@code 0}.
    */
  def isEmpty: Boolean = size == 0

  /**
    * Clears the string builder (convenience Collections API style method).
    * <p>
    * This method does not reduce the size of the internal character buffer.
    * To do that, call {@code clear()} followed by {@link #minimizeCapacity}.
    * <p>
    * This method is the same as {@link #setLength} called with zero
    * and is provided to match the API of Collections.
    *
    * @return this, to enable chaining
    */
  def clear: StrBuilder = {
    bufferSize = 0
    this
  }

  /**
    * Gets the character at the specified index.
    *
    * @see #setCharAt(int, char)
    * @see #deleteCharAt(int)
    * @param index the index to retrieve, must be valid
    * @return the character at the index
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  override def charAt(index: Int): Char = {
    if (index < 0 || index >= length) throw new StringIndexOutOfBoundsException(index)
    buffer(index)
  }

  /**
    * Sets the character at the specified index.
    *
    * @see #charAt(int)
    * @see #deleteCharAt(int)
    * @param index the index to set
    * @param ch    the new character
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def setCharAt(index: Int, ch: Char): StrBuilder = {
    if (index < 0 || index >= length) throw new StringIndexOutOfBoundsException(index)
    buffer(index) = ch
    this
  }

  /**
    * Deletes the character at the specified index.
    *
    * @see #charAt(int)
    * @see #setCharAt(int, char)
    * @param index the index to delete
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def deleteCharAt(index: Int): StrBuilder = {
    if (index < 0 || index >= size) throw new StringIndexOutOfBoundsException(index)
    deleteImpl(index, index + 1, 1)
    this
  }

  /**
    * Copies the builder's character array into a new character array.
    *
    * @return a new array that represents the contents of the builder
    */
  def toCharArray: Array[Char] = {
    if (size == 0) return ArrayUtils.EMPTY_CHAR_ARRAY
    val chars = new Array[Char](size)
    Array.copy(buffer, 0, chars, 0, size)
    chars
  }

  /**
    * Copies part of the builder's character array into a new character array.
    *
    * @param startIndex the start index, inclusive, must be valid
    * @param endIndex   the end index, exclusive, must be valid except that
    *                   if too large it is treated as end of string
    * @return a new array that holds part of the contents of the builder
    * @throws java.lang.IndexOutOfBoundsException if startIndex is invalid,
    *                                   or if endIndex is invalid (but endIndex greater than size is valid)
    */
  def toCharArray(startIndex: Int, endIndex: Int): Array[Char] = {
    val newEndIndex = validateRange(startIndex, endIndex)
    val len = newEndIndex - startIndex
    if (len == 0) return ArrayUtils.EMPTY_CHAR_ARRAY
    val chars = new Array[Char](len)
    Array.copy(buffer, startIndex, chars, 0, len)
    chars
  }

  /**
    * Copies the character array into the specified array.
    *
    * @param destination the destination array, null will cause an array to be created
    * @return the input array, unless that was null or too small
    */
  def getChars(destination: Array[Char]): Array[Char] = {
    val len = self.length
    var dest = destination
    if (dest == null || dest.length < len) dest = new Array[Char](len)

    Array.copy(buffer, 0, dest, 0, len)
    dest
  }

  /**
    * Copies the character array into the specified array.
    *
    * @param startIndex       first index to copy, inclusive, must be valid
    * @param endIndex         last index, exclusive, must be valid
    * @param destination      the destination array, must not be null or too small
    * @param destinationIndex the index to start copying in destination
    * @throws java.lang.NullPointerException      if the array is null
    * @throws java.lang.IndexOutOfBoundsException if any index is invalid
    */
  def getChars(startIndex: Int, endIndex: Int, destination: Array[Char], destinationIndex: Int): Unit = {
    if (startIndex < 0) throw new StringIndexOutOfBoundsException(startIndex)
    if (endIndex < 0 || endIndex > self.length) throw new StringIndexOutOfBoundsException(endIndex)
    if (startIndex > endIndex) throw new StringIndexOutOfBoundsException("end < start")
    Array.copy(buffer, startIndex, destination, destinationIndex, endIndex - startIndex)
  }

  /**
    * If possible, reads chars from the provided {@link java.lang.Readable} directly into underlying
    * character buffer without making extra copies.
    *
    * @param readable object to read from
    * @return the number of characters read
    * @throws java.io.IOException if an I/O error occurs
    * @since 3.4
    * @see #appendTo(Appendable)
    */
  @throws[IOException]
  def readFrom(readable: Readable): Int = {
    val oldSize: Int = size

    if (readable.isInstanceOf[Reader]) {
      val r = readable.asInstanceOf[Reader]
      ensureCapacity(size + 1)
      var read: Int = 0
      while ({ read = r.read(buffer, size, buffer.length - size); read } != -1) {
        bufferSize += read
        ensureCapacity(size + 1)
      }
    } else if (readable.isInstanceOf[CharBuffer]) {
      val cb = readable.asInstanceOf[CharBuffer]
      val remaining = cb.remaining
      ensureCapacity(size + remaining)
      cb.get(buffer, size, remaining)
      bufferSize += remaining
    } else {
      var read: Int = 0
      while (read >= 0) {
        bufferSize += read
        ensureCapacity(size + 1)
        val buf = CharBuffer.wrap(buffer, size, buffer.length - size)
        read = readable.read(buf)
      }
    }

    size - oldSize
  }

  /**
    * Appends the new line string to this string builder.
    * <p>
    * The new line string can be altered using {@link #setNewLineText}.
    * This might be used to force the output to always use Unix line endings
    * even when on Windows.
    *
    * @return this, to enable chaining
    */
  def appendNewLine: StrBuilder = {
    if (newLine == null) {
      append(System.lineSeparator)
      return this
    }
    append(newLine)
  }

  /**
    * Appends the text representing {@code null} to this string builder.
    *
    * @return this, to enable chaining
    */
  def appendNull: StrBuilder = {
    if (nullText == null) return this
    append(nullText)
  }

  /**
    * Appends an object to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param obj the object to append
    * @return this, to enable chaining
    */
  def append(obj: Any): StrBuilder = {
    if (obj == null) return appendNull
    if (obj.isInstanceOf[CharSequence]) return append(obj.asInstanceOf[CharSequence])
    append(obj.toString)
  }

  /**
    * Appends a CharSequence to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param seq the CharSequence to append
    * @return this, to enable chaining
    * @since 3.0
    */
  override def append(seq: CharSequence): StrBuilder = {
    if (seq == null) return appendNull
    if (seq.isInstanceOf[StrBuilder]) return append(seq.asInstanceOf[StrBuilder])
    if (seq.isInstanceOf[JStringBuilder]) return append(seq.asInstanceOf[JStringBuilder])
    if (seq.isInstanceOf[StringBuffer]) return append(seq.asInstanceOf[StringBuffer])
    if (seq.isInstanceOf[CharBuffer]) return append(seq.asInstanceOf[CharBuffer])
    append(seq.toString)
  }

  /**
    * Appends part of a CharSequence to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param seq        the CharSequence to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 3.0
    */
  override def append(seq: CharSequence, startIndex: Int, length: Int): StrBuilder = {
    if (seq == null) return appendNull
    append(seq.toString, startIndex, length)
  }

  /**
    * Appends a string to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string to append
    * @return this, to enable chaining
    */
  def append(str: String): StrBuilder = {
    if (str == null) return appendNull
    val strLen = str.length
    if (strLen > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + strLen)
      str.getChars(0, strLen, buffer, currentLength)
      bufferSize += strLen
    }

    this
  }

  /**
    * Appends part of a string to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    */
  def append(str: String, startIndex: Int, length: Int): StrBuilder = {
    if (str == null) return appendNull

    if (startIndex < 0 || startIndex > str.length) throw new StringIndexOutOfBoundsException("startIndex must be valid")
    if (length < 0 || (startIndex + length) > str.length)
      throw new StringIndexOutOfBoundsException("length must be valid")
    if (length > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + length)
      str.getChars(startIndex, startIndex + length, buffer, currentLength)
      bufferSize += length
    }

    this
  }

  // TODO: @link java.lang.String#format
  /**
    * Calls {@code java.lang.String#format} and appends the result.
    *
    * @param format the format string
    * @param objs   the objects to use in the format string
    * @return {@code this} to enable chaining
    * @see String#format(String, Object...)
    * @since 3.2
    */
  def append(format: String, objs: Any*): StrBuilder = append(String.format(format, objs))

  /**
    * Appends the contents of a char buffer to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param buf the char buffer to append
    * @return this, to enable chaining
    * @since 3.4
    */
  def append(buf: CharBuffer): StrBuilder = {
    if (buf == null) return appendNull
    if (buf.hasArray) {
      val length: Int = buf.remaining
      val currentLength: Int = self.length
      ensureCapacity(currentLength + length)
      Array.copy(buf.array(), (buf.arrayOffset + buf.position()), buffer, currentLength, length)
      bufferSize += length
    } else append(buf.toString)
    this
  }

  /**
    * Appends the contents of a char buffer to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param buf        the char buffer to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 3.4
    */
  def append(buf: CharBuffer, startIndex: Int, length: Int): StrBuilder = {
    if (buf == null) return appendNull
    if (buf.hasArray) {
      val totalLength = buf.remaining
      if (startIndex < 0 || startIndex > totalLength)
        throw new StringIndexOutOfBoundsException("startIndex must be valid")
      if (length < 0 || (startIndex + length) > totalLength)
        throw new StringIndexOutOfBoundsException("length must be valid")

      val currentLength = self.length
      ensureCapacity(currentLength + length)

      System.arraycopy(buf.array, buf.arrayOffset + buf.position() + startIndex, buffer, currentLength, length)
      bufferSize += length
    } else append(buf.toString, startIndex, length)
    this
  }

  /**
    * Appends a string buffer to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string buffer to append
    * @return this, to enable chaining
    */
  def append(str: StringBuffer): StrBuilder = {
    if (str == null) return appendNull
    val strLen = str.length
    if (strLen > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + strLen)
      str.getChars(0, strLen, buffer, currentLength)
      bufferSize += strLen
    }
    this
  }

  /**
    * Appends part of a string buffer to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    */
  def append(str: StringBuffer, startIndex: Int, length: Int): StrBuilder = {
    if (str == null) return appendNull
    if (startIndex < 0 || startIndex > str.length) throw new StringIndexOutOfBoundsException("startIndex must be valid")
    if (length < 0 || (startIndex + length) > str.length)
      throw new StringIndexOutOfBoundsException("length must be valid")
    if (length > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + length)
      str.getChars(startIndex, startIndex + length, buffer, currentLength)
      bufferSize += length
    }
    this
  }

  /**
    * Appends a StringBuilder to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the StringBuilder to append
    * @return this, to enable chaining
    * @since 3.2
    */
  def append(str: JStringBuilder): StrBuilder = {
    if (str == null) return appendNull
    val strLen = str.length
    if (strLen > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + strLen)
      str.getChars(0, strLen, buffer, currentLength)
      bufferSize += strLen
    }
    this
  }

  /**
    * Appends part of a StringBuilder to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the StringBuilder to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 3.2
    */
  def append(str: JStringBuilder, startIndex: Int, length: Int): StrBuilder = {
    if (str == null) return appendNull
    if (startIndex < 0 || startIndex > str.length) throw new StringIndexOutOfBoundsException("startIndex must be valid")
    if (length < 0 || (startIndex + length) > str.length)
      throw new StringIndexOutOfBoundsException("length must be valid")
    if (length > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + length)
      str.getChars(startIndex, startIndex + length, buffer, currentLength)
      bufferSize += length
    }

    this
  }

  /**
    * Appends another string builder to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string builder to append
    * @return this, to enable chaining
    */
  def append(str: StrBuilder): StrBuilder = {
    if (str == null) return appendNull
    val strLen = str.length
    if (strLen > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + strLen)
      Array.copy(str.buffer, 0, buffer, currentLength, strLen)
      bufferSize += strLen
    }

    this
  }

  /**
    * Appends part of a string builder to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    */
  def append(str: StrBuilder, startIndex: Int, length: Int): StrBuilder = {
    if (str == null) return appendNull
    if (startIndex < 0 || startIndex > str.length) throw new StringIndexOutOfBoundsException("startIndex must be valid")
    if (length < 0 || (startIndex + length) > str.length)
      throw new StringIndexOutOfBoundsException("length must be valid")
    if (length > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + length)
      str.getChars(startIndex, startIndex + length, buffer, currentLength)
      bufferSize += length
    }

    this
  }

  /**
    * Appends a char array to the string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param chars the char array to append
    * @return this, to enable chaining
    */
  def append(chars: Array[Char]): StrBuilder = {
    if (chars == null) return appendNull
    val strLen = chars.length
    if (strLen > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + strLen)
      Array.copy(chars, 0, buffer, currentLength, strLen)
      bufferSize += strLen
    }
    this
  }

  /**
    * Appends a char array to the string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param chars      the char array to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    */
  def append(chars: Array[Char], startIndex: Int, length: Int): StrBuilder = {
    if (chars == null) return appendNull

    if (startIndex < 0 || startIndex > chars.length)
      throw new StringIndexOutOfBoundsException("Invalid startIndex: " + length)
    if (length < 0 || (startIndex + length) > chars.length)
      throw new StringIndexOutOfBoundsException("Invalid length: " + length)

    if (length > 0) {
      val currentLength = self.length
      ensureCapacity(currentLength + length)
      Array.copy(chars, startIndex, buffer, currentLength, length)
      bufferSize += length
    }

    this
  }

  /**
    * Appends a boolean value to the string builder.
    *
    * @param value the value to append
    * @return this, to enable chaining
    */
  def append(value: Boolean): StrBuilder = {
    if (value) {
      ensureCapacity(size + 4)
      buffer({ bufferSize += 1; size - 1 }) = 't'
      buffer({ bufferSize += 1; size - 1 }) = 'r'
      buffer({ bufferSize += 1; size - 1 }) = 'u'
      buffer({ bufferSize += 1; size - 1 }) = 'e'
    } else {
      ensureCapacity(size + 5)
      buffer({ bufferSize += 1; size - 1 }) = 'f'
      buffer({ bufferSize += 1; size - 1 }) = 'a'
      buffer({ bufferSize += 1; size - 1 }) = 'l'
      buffer({ bufferSize += 1; size - 1 }) = 's'
      buffer({ bufferSize += 1; size - 1 }) = 'e'
    }

    this
  }

  /**
    * Appends a char value to the string builder.
    *
    * @param ch the value to append
    * @return this, to enable chaining
    * @since 3.0
    */
  override def append(ch: Char): StrBuilder = {
    val len = self.length
    ensureCapacity(len + 1)
    buffer({ bufferSize += 1; size - 1 }) = ch
    this
  }

  /**
    * Appends an int value to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    */
  def append(value: Int): StrBuilder = append(String.valueOf(value))

  /**
    * Appends a long value to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    */
  def append(value: Long): StrBuilder = append(String.valueOf(value))

  /**
    * Appends a float value to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    */
  def append(value: Float): StrBuilder = append(String.valueOf(value))

  /**
    * Appends a double value to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    */
  def append(value: Double): StrBuilder = append(String.valueOf(value))

  /**
    * Appends an object followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param obj the object to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(obj: Any): StrBuilder = append(obj).appendNewLine

  /**
    * Appends a string followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(str: String): StrBuilder = append(str).appendNewLine

  /**
    * Appends part of a string followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(str: String, startIndex: Int, length: Int): StrBuilder = append(str, startIndex, length).appendNewLine

  def appendln(format: String, objs: Any*): StrBuilder = append(format, objs).appendNewLine

  /**
    * Appends a string buffer followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string buffer to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(str: StringBuffer): StrBuilder = append(str).appendNewLine

  /**
    * Appends a string builder followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string builder to append
    * @return this, to enable chaining
    * @since 3.2
    */
  def appendln(str: JStringBuilder): StrBuilder = append(str).appendNewLine

  /**
    * Appends part of a string builder followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string builder to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 3.2
    */
  def appendln(str: JStringBuilder, startIndex: Int, length: Int): StrBuilder =
    append(str, startIndex, length).appendNewLine

  /**
    * Appends part of a string buffer followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(str: StringBuffer, startIndex: Int, length: Int): StrBuilder =
    append(str, startIndex, length).appendNewLine

  /**
    * Appends another string builder followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str the string builder to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(str: StrBuilder): StrBuilder = append(str).appendNewLine

  /**
    * Appends part of a string builder followed by a new line to this string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param str        the string to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(str: StrBuilder, startIndex: Int, length: Int): StrBuilder =
    append(str, startIndex, length).appendNewLine

  /**
    * Appends a char array followed by a new line to the string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param chars the char array to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(chars: Array[Char]): StrBuilder = append(chars).appendNewLine

  /**
    * Appends a char array followed by a new line to the string builder.
    * Appending null will call {@link #appendNull}.
    *
    * @param chars      the char array to append
    * @param startIndex the start index, inclusive, must be valid
    * @param length     the length to append, must be valid
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(chars: Array[Char], startIndex: Int, length: Int): StrBuilder =
    append(chars, startIndex, length).appendNewLine

  /**
    * Appends a boolean value followed by a new line to the string builder.
    *
    * @param value the value to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(value: Boolean): StrBuilder = append(value).appendNewLine

  /**
    * Appends a char value followed by a new line to the string builder.
    *
    * @param ch the value to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(ch: Char): StrBuilder = append(ch).appendNewLine

  /**
    * Appends an int value followed by a new line to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(value: Int): StrBuilder = append(value).appendNewLine

  /**
    * Appends a long value followed by a new line to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(value: Long): StrBuilder = append(value).appendNewLine

  /**
    * Appends a float value followed by a new line to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(value: Float): StrBuilder = append(value).appendNewLine

  /**
    * Appends a double value followed by a new line to the string builder using {@code String.valueOf}.
    *
    * @param value the value to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendln(value: Double): StrBuilder = append(value).appendNewLine

  /**
    * Appends each item in an array to the builder without any separators.
    * Appending a null array will have no effect.
    * Each object is appended using {@link #append(obj:Any)*}.
    *
    * @tparam T    the element type
    * @param array the array to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendAll[T](@SuppressWarnings(Array("unchecked")) array: T*): StrBuilder = {
    /*
     * @SuppressWarnings used to hide warning about vararg usage. We cannot
     * use @SafeVarargs, since this method is not final. Using @SuppressWarnings
     * is fine, because it isn't inherited by subclasses, so each subclass must
     * vouch for itself whether its use of 'array' is safe.
     */
    if (array.nonEmpty) for (element <- array) {
      append(element)
    }

    this
  }

  /**
    * Appends each item in an iterable to the builder without any separators.
    * Appending a null iterable will have no effect.
    * Each object is appended using {@link #append(obj:Any)*}.
    *
    * @param iterable the iterable to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendAll(iterable: Iterable[_]): StrBuilder = {
    if (iterable != null) {
      for (o <- iterable) append(o)
    }
    this
  }

  /**
    * Appends each item in an iterator to the builder without any separators.
    * Appending a null iterator will have no effect.
    * Each object is appended using {@link #append(obj:Any)*}.
    *
    * @param it the iterator to append
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendAll(it: util.Iterator[_]): StrBuilder = {
    if (it != null) while ({
      it.hasNext
    }) append(it.next)
    this
  }

  /**
    * Appends an array placing separators between each value, but
    * not before the first or after the last.
    * Appending a null array will have no effect.
    * Each object is appended using {@link #append(obj:Any)*}.
    *
    * @param array     the array to append
    * @param separator the separator to use, null means no separator
    * @return this, to enable chaining
    */
  def appendWithSeparators(array: Array[AnyRef], separator: String): StrBuilder = {
    if (array != null && array.length > 0) {
      val sep = Objects.toString(separator, "")
      append(array(0))
      for (i <- 1 until array.length) {
        append(sep)
        append(array(i))
      }
    }
    this
  }

  /**
    * Appends an iterable placing separators between each value, but
    * not before the first or after the last.
    * Appending a null iterable will have no effect.
    * Each object is appended using {@link #append(obj:Any)*}.
    *
    * @param iterable  the iterable to append
    * @param separator the separator to use, null means no separator
    * @return this, to enable chaining
    */
  def appendWithSeparators(iterable: Iterable[_], separator: String): StrBuilder = {
    if (iterable != null) {
      val sep = Objects.toString(separator, "")
      val it = iterable.iterator
      while ({
        it.hasNext
      }) {
        append(it.next)
        if (it.hasNext) append(sep)
      }
    }
    this
  }

  /**
    * Appends an iterator placing separators between each value, but
    * not before the first or after the last.
    * Appending a null iterator will have no effect.
    * Each object is appended using {@link #append(obj:Any)*}.
    *
    * @param it        the iterator to append
    * @param separator the separator to use, null means no separator
    * @return this, to enable chaining
    */
  def appendWithSeparators(it: util.Iterator[_], separator: String): StrBuilder = {
    if (it != null) {
      val sep = Objects.toString(separator, "")
      while ({
        it.hasNext
      }) {
        append(it.next)
        if (it.hasNext) append(sep)
      }
    }
    this
  }

  /**
    * Appends a separator if the builder is currently non-empty.
    * Appending a null separator will have no effect.
    * The separator is appended using {@link #append(str:String)*}.
    * <p>
    * This method is useful for adding a separator each time around the
    * loop except the first.
    * <pre>
    * for (Iterator it = list.iterator(); it.hasNext(); ) {
    * appendSeparator(",");
    * append(it.next());
    * }
    * </pre>
    * Note that for this simple example, you should use
    * {@link #appendWithSeparators(iterable:Iterable[_],separator:String)*}.
    *
    * @param separator the separator to use, null means no separator
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendSeparator(separator: String): StrBuilder = appendSeparator(separator, null)

  /**
    * Appends one of both separators to the StrBuilder.
    * If the builder is currently empty it will append the defaultIfEmpty-separator
    * Otherwise it will append the standard-separator
    *
    * Appending a null separator will have no effect.
    * The separator is appended using {@link #append(str:String)*}.
    * <p>
    * This method is for example useful for constructing queries
    * <pre>
    * StrBuilder whereClause = new StrBuilder();
    * if (searchCommand.getPriority() != null) {
    * whereClause.appendSeparator(" and", " where");
    * whereClause.append(" priority = ?")
    * }
    * if (searchCommand.getComponent() != null) {
    * whereClause.appendSeparator(" and", " where");
    * whereClause.append(" component = ?")
    * }
    * selectClause.append(whereClause)
    * </pre>
    *
    * @param standard       the separator if builder is not empty, null means no separator
    * @param defaultIfEmpty the separator if builder is empty, null means no separator
    * @return this, to enable chaining
    * @since 2.5
    */
  def appendSeparator(standard: String, defaultIfEmpty: String): StrBuilder = {
    val str =
      if (isEmpty) defaultIfEmpty
      else standard
    if (str != null) append(str)
    this
  }

  /**
    * Appends a separator if the builder is currently non-empty.
    * The separator is appended using {@link #append(ch:Char)*}.
    * <p>
    * This method is useful for adding a separator each time around the
    * loop except the first.
    * <pre>
    * for (Iterator it = list.iterator(); it.hasNext(); ) {
    * appendSeparator(',');
    * append(it.next());
    * }
    * </pre>
    * Note that for this simple example, you should use
    * {@link #appendWithSeparators(iterable:Iterable[_],separator:String)*}.
    *
    * @param separator the separator to use
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendSeparator(separator: Char): StrBuilder = {
    if (size > 0) append(separator)
    this
  }

  /**
    * Append one of both separators to the builder
    * If the builder is currently empty it will append the defaultIfEmpty-separator
    * Otherwise it will append the standard-separator
    *
    * The separator is appended using {@link #append(ch:Char)*}.
    *
    * @param standard       the separator if builder is not empty
    * @param defaultIfEmpty the separator if builder is empty
    * @return this, to enable chaining
    * @since 2.5
    */
  def appendSeparator(standard: Char, defaultIfEmpty: Char): StrBuilder = {
    if (size > 0)
      append(standard)
    else append(defaultIfEmpty)
    this
  }

  /**
    * Appends a separator to the builder if the loop index is greater than zero.
    * Appending a null separator will have no effect.
    * The separator is appended using {@link #append(str:String)*}.
    * <p>
    * This method is useful for adding a separator each time around the
    * loop except the first.
    * </p>
    * <pre>
    * for (int i = 0; i &lt; list.size(); i++) {
    * appendSeparator(",", i);
    * append(list.get(i));
    * }
    * </pre>
    * Note that for this simple example, you should use
    * {@link #appendWithSeparators(iterable:Iterable[_],separator:String)*}.
    *
    * @param separator the separator to use, null means no separator
    * @param loopIndex the loop index
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendSeparator(separator: String, loopIndex: Int): StrBuilder = {
    if (separator != null && loopIndex > 0) append(separator)
    this
  }

  /**
    * Appends a separator to the builder if the loop index is greater than zero.
    * The separator is appended using {@link #append(ch:Char)*}.
    * <p>
    * This method is useful for adding a separator each time around the
    * loop except the first.
    * </p>
    * <pre>
    * for (int i = 0; i &lt; list.size(); i++) {
    * appendSeparator(",", i);
    * append(list.get(i));
    * }
    * </pre>
    * Note that for this simple example, you should use
    * {@link #appendWithSeparators(iterable:Iterable[_],separator:String)*}.
    *
    * @param separator the separator to use
    * @param loopIndex the loop index
    * @return this, to enable chaining
    * @since 2.3
    */
  def appendSeparator(separator: Char, loopIndex: Int): StrBuilder = {
    if (loopIndex > 0) append(separator)
    this
  }

  /**
    * Appends the pad character to the builder the specified number of times.
    *
    * @param length  the length to append, negative means no append
    * @param padChar the character to append
    * @return this, to enable chaining
    */
  def appendPadding(length: Int, padChar: Char): StrBuilder = {
    if (length >= 0) {
      ensureCapacity(size + length)
      for (_ <- 0 until length) {
        buffer({ bufferSize += 1; size - 1 }) = padChar
      }
    }
    this
  }

  /**
    * Appends an object to the builder padding on the left to a fixed width.
    * The {@code toString} of the object is used.
    * If the object is larger than the length, the left hand side is lost.
    * If the object is null, the null text value is used.
    *
    * @param obj     the object to append, null uses null text
    * @param width   the fixed field width, zero or negative has no effect
    * @param padChar the pad character to use
    * @return this, to enable chaining
    */
  def appendFixedWidthPadLeft(obj: Any, width: Int, padChar: Char): StrBuilder = {
    if (width > 0) {
      ensureCapacity(size + width)
      var str =
        if (obj == null) getNullText
        else obj.toString
      if (str == null) str = StringUtils.EMPTY
      val strLen = str.length
      if (strLen >= width) str.getChars(strLen - width, strLen, buffer, size)
      else {
        val padLen = width - strLen
        for (i <- 0 until padLen) {
          buffer(size + i) = padChar
        }
        str.getChars(0, strLen, buffer, size + padLen)
      }
      bufferSize += width
    }
    this
  }

  /**
    * Appends an object to the builder padding on the left to a fixed width.
    * The {@code String.valueOf} of the {@code int} value is used.
    * If the formatted value is larger than the length, the left hand side is lost.
    *
    * @param value   the value to append
    * @param width   the fixed field width, zero or negative has no effect
    * @param padChar the pad character to use
    * @return this, to enable chaining
    */
  def appendFixedWidthPadLeft(value: Int, width: Int, padChar: Char): StrBuilder =
    appendFixedWidthPadLeft(String.valueOf(value), width, padChar)

  /**
    * Appends an object to the builder padding on the right to a fixed length.
    * The {@code toString} of the object is used.
    * If the object is larger than the length, the right hand side is lost.
    * If the object is null, null text value is used.
    *
    * @param obj     the object to append, null uses null text
    * @param width   the fixed field width, zero or negative has no effect
    * @param padChar the pad character to use
    * @return this, to enable chaining
    */
  def appendFixedWidthPadRight(obj: Any, width: Int, padChar: Char): StrBuilder = {
    if (width > 0) {
      ensureCapacity(size + width)
      var str =
        if (obj == null) getNullText
        else obj.toString
      if (str == null) str = StringUtils.EMPTY
      val strLen = str.length
      if (strLen >= width) str.getChars(0, width, buffer, size)
      else {
        val padLen = width - strLen
        str.getChars(0, strLen, buffer, size)
        for (i <- 0 until padLen) {
          buffer(size + strLen + i) = padChar
        }
      }
      bufferSize += width
    }
    this
  }

  /**
    * Appends an object to the builder padding on the right to a fixed length.
    * The {@code String.valueOf} of the {@code int} value is used.
    * If the object is larger than the length, the right hand side is lost.
    *
    * @param value   the value to append
    * @param width   the fixed field width, zero or negative has no effect
    * @param padChar the pad character to use
    * @return this, to enable chaining
    */
  def appendFixedWidthPadRight(value: Int, width: Int, padChar: Char): StrBuilder =
    appendFixedWidthPadRight(String.valueOf(value), width, padChar)

  /**
    * Inserts the string representation of an object into this builder.
    * Inserting null will use the stored null text value.
    *
    * @param index the index to add at, must be valid
    * @param obj   the object to insert
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def insert(index: Int, obj: Any): StrBuilder = {
    if (obj == null) return insert(index, nullText)
    insert(index, obj.toString)
  }

  /**
    * Inserts the string into this builder.
    * Inserting null will use the stored null text value.
    *
    * @param index the index to add at, must be valid
    * @param str   the string to insert
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def insert(index: Int, str: String): StrBuilder = {
    validateIndex(index)
    val newStr = if (str == null) nullText else str

    if (newStr != null) {
      val strLen = newStr.length
      if (strLen > 0) {
        val newSize = size + strLen
        ensureCapacity(newSize)
        Array.copy(buffer, index, buffer, index + strLen, size - index)
        bufferSize = newSize
        newStr.getChars(0, strLen, buffer, index)
      }
    }

    this
  }

  /**
    * Inserts the character array into this builder.
    * Inserting null will use the stored null text value.
    *
    * @param index the index to add at, must be valid
    * @param chars the char array to insert
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def insert(index: Int, chars: Array[Char]): StrBuilder = {
    validateIndex(index)
    if (chars == null) return insert(index, nullText)
    val len = chars.length

    if (len > 0) {
      ensureCapacity(size + len)
      Array.copy(buffer, index, buffer, index + len, size - index)
      Array.copy(chars, 0, buffer, index, len)
      bufferSize += len
    }

    this
  }

  /**
    * Inserts part of the character array into this builder.
    * Inserting null will use the stored null text value.
    *
    * @param index  the index to add at, must be valid
    * @param chars  the char array to insert
    * @param offset the offset into the character array to start at, must be valid
    * @param length the length of the character array part to copy, must be positive
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if any index is invalid
    */
  def insert(index: Int, chars: Array[Char], offset: Int, length: Int): StrBuilder = {
    validateIndex(index)
    if (chars == null) return insert(index, nullText)
    if (offset < 0 || offset > chars.length) throw new StringIndexOutOfBoundsException("Invalid offset: " + offset)
    if (length < 0 || offset + length > chars.length)
      throw new StringIndexOutOfBoundsException("Invalid length: " + length)
    if (length > 0) {
      ensureCapacity(size + length)
      Array.copy(buffer, index, buffer, index + length, size - index)
      Array.copy(chars, offset, buffer, index, length)
      bufferSize += length
    }
    this
  }

  /**
    * Inserts the value into this builder.
    *
    * @param index the index to add at, must be valid
    * @param value the value to insert
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def insert(index: Int, value: Boolean): StrBuilder = {
    validateIndex(index)
    var idx: Int = index

    if (value) {
      ensureCapacity(size + 4)
      Array.copy(buffer, idx, buffer, idx + 4, size - idx)
      buffer({ idx += 1; idx - 1 }) = 't'
      buffer({ idx += 1; idx - 1 }) = 'r'
      buffer({ idx += 1; idx - 1 }) = 'u'
      buffer(idx) = 'e'
      bufferSize += 4
    } else {
      ensureCapacity(size + 5)
      Array.copy(buffer, idx, buffer, idx + 5, size - idx)
      buffer({ idx += 1; idx - 1 }) = 'f'
      buffer({ idx += 1; idx - 1 }) = 'a'
      buffer({ idx += 1; idx - 1 }) = 'l'
      buffer({ idx += 1; idx - 1 }) = 's'
      buffer(idx) = 'e'
      bufferSize += 5
    }
    this
  }

  def insert(index: Int, value: Char): StrBuilder = {
    validateIndex(index)
    ensureCapacity(size + 1)
    Array.copy(buffer, index, buffer, index + 1, size - index)
    buffer(index) = value
    bufferSize += 1
    this
  }

  def insert(index: Int, value: Int): StrBuilder = insert(index, String.valueOf(value))

  def insert(index: Int, value: Long): StrBuilder = insert(index, String.valueOf(value))

  def insert(index: Int, value: Float): StrBuilder = insert(index, String.valueOf(value))

  def insert(index: Int, value: Double): StrBuilder = insert(index, String.valueOf(value))

  /**
    * Internal method to delete a range without validation.
    *
    * @param startIndex the start index, must be valid
    * @param endIndex   the end index (exclusive), must be valid
    * @param len        the length, must be valid
    * @throws java.lang.IndexOutOfBoundsException if any index is invalid
    */
  private def deleteImpl(startIndex: Int, endIndex: Int, len: Int): Unit = {
    Array.copy(buffer, endIndex, buffer, startIndex, size - endIndex)
    bufferSize -= len
  }

  /**
    * Deletes the characters between the two specified indices.
    *
    * @param startIndex the start index, inclusive, must be valid
    * @param endIndex   the end index, exclusive, must be valid except
    *                   that if too large it is treated as end of string
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def delete(startIndex: Int, endIndex: Int): StrBuilder = {
    val newEndIndex = validateRange(startIndex, endIndex)
    val len = newEndIndex - startIndex
    if (len > 0) deleteImpl(startIndex, newEndIndex, len)
    this
  }

  /**
    * Deletes the character wherever it occurs in the builder.
    *
    * @param ch the character to delete
    * @return this, to enable chaining
    */
  def deleteAll(ch: Char): StrBuilder = {
    var i = 0
    while (i < size) {
      if (buffer(i) == ch) {
        val start = i
        while ({ i += 1; i } < size && buffer(i) == ch) {}
        val len = i - start
        deleteImpl(start, i, len)
        i -= len
      }

      i += 1
    }
    this
  }

  def deleteFirst(ch: Char): StrBuilder = {
    for (i <- 0 until size) {
      if (buffer(i) == ch) {
        deleteImpl(i, i + 1, 1)
        return this
      }
    }
    this
  }

  /**
    * Deletes the string wherever it occurs in the builder.
    *
    * @param str the string to delete, null causes no action
    * @return this, to enable chaining
    */
  def deleteAll(str: String): StrBuilder = {
    val len =
      if (str == null) 0
      else str.length

    if (len > 0) {
      var index = indexOf(str, 0)
      while (index >= 0) {
        deleteImpl(index, index + len, len)
        index = indexOf(str, index)
      }
    }
    this
  }

  def deleteFirst(str: String): StrBuilder = {
    val len =
      if (str == null) 0
      else str.length

    if (len > 0) {
      val index = indexOf(str, 0)
      if (index >= 0) deleteImpl(index, index + len, len)
    }
    this
  }

  /**
    * Deletes all parts of the builder that the matcher matches.
    * <p>
    * Matchers can be used to perform advanced deletion behavior.
    * For example you could write a matcher to delete all occurrences
    * where the character 'a' is followed by a number.
    *
    * @param matcher the matcher to use to find the deletion, null causes no action
    * @return this, to enable chaining
    */
  def deleteAll(matcher: StrMatcher): StrBuilder = replace(matcher, null, 0, size, -1)

  /**
    * Deletes the first match within the builder using the specified matcher.
    * <p>
    * Matchers can be used to perform advanced deletion behavior.
    * For example you could write a matcher to delete
    * where the character 'a' is followed by a number.
    *
    * @param matcher the matcher to use to find the deletion, null causes no action
    * @return this, to enable chaining
    */
  def deleteFirst(matcher: StrMatcher): StrBuilder = replace(matcher, null, 0, size, 1)

  /**
    * Internal method to delete a range without validation.
    *
    * @param startIndex the start index, must be valid
    * @param endIndex   the end index (exclusive), must be valid
    * @param removeLen  the length to remove (endIndex - startIndex), must be valid
    * @param insertStr  the string to replace with, null means delete range
    * @param insertLen  the length of the insert string, must be valid
    * @throws java.lang.IndexOutOfBoundsException if any index is invalid
    */
  private def replaceImpl(startIndex: Int, endIndex: Int, removeLen: Int, insertStr: String, insertLen: Int): Unit = {
    val newSize = size - removeLen + insertLen
    if (insertLen != removeLen) {
      ensureCapacity(newSize)
      Array.copy(buffer, endIndex, buffer, startIndex + insertLen, size - endIndex)
      bufferSize = newSize
    }
    if (insertLen > 0) insertStr.getChars(0, insertLen, buffer, startIndex)
  }

  /**
    * Replaces a portion of the string builder with another string.
    * The length of the inserted string does not have to match the removed length.
    *
    * @param startIndex the start index, inclusive, must be valid
    * @param endIndex   the end index, exclusive, must be valid except
    *                   that if too large it is treated as end of string
    * @param replaceStr the string to replace with, null means delete range
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def replace(startIndex: Int, endIndex: Int, replaceStr: String): StrBuilder = {
    val newEndIndex = validateRange(startIndex, endIndex)
    val insertLen =
      if (replaceStr == null) 0
      else replaceStr.length
    replaceImpl(startIndex, newEndIndex, newEndIndex - startIndex, replaceStr, insertLen)
    this
  }

  /**
    * Replaces the search character with the replace character
    * throughout the builder.
    *
    * @param search  the search character
    * @param replace the replace character
    * @return this, to enable chaining
    */
  def replaceAll(search: Char, replace: Char): StrBuilder = {
    if (search != replace) for (i <- 0 until size) {
      if (buffer(i) == search) buffer(i) = replace
    }
    this
  }

  /**
    * Replaces the first instance of the search character with the
    * replace character in the builder.
    *
    * @param search  the search character
    * @param replace the replace character
    * @return this, to enable chaining
    */
  def replaceFirst(search: Char, replace: Char): StrBuilder = {
    if (search != replace) for (i <- 0 until size) {
      if (buffer(i) == search) {
        buffer(i) = replace
        return this
      }
    }
    this
  }

  /**
    * Replaces the search string with the replace string throughout the builder.
    *
    * @param searchStr  the search string, null causes no action to occur
    * @param replaceStr the replace string, null is equivalent to an empty string
    * @return this, to enable chaining
    */
  def replaceAll(searchStr: String, replaceStr: String): StrBuilder = {
    val searchLen =
      if (searchStr == null) 0
      else searchStr.length
    if (searchLen > 0) {
      val replaceLen =
        if (replaceStr == null) 0
        else replaceStr.length
      var index = indexOf(searchStr, 0)
      while (index >= 0) {
        replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen)
        index = indexOf(searchStr, index + replaceLen)
      }
    }
    this
  }

  /**
    * Replaces the first instance of the search string with the replace string.
    *
    * @param searchStr  the search string, null causes no action to occur
    * @param replaceStr the replace string, null is equivalent to an empty string
    * @return this, to enable chaining
    */
  def replaceFirst(searchStr: String, replaceStr: String): StrBuilder = {
    val searchLen =
      if (searchStr == null) 0
      else searchStr.length

    if (searchLen > 0) {
      val index = indexOf(searchStr, 0)
      if (index >= 0) {
        val replaceLen =
          if (replaceStr == null) 0
          else replaceStr.length

        replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen)
      }
    }
    this
  }

  /**
    * Replaces all matches within the builder with the replace string.
    * <p>
    * Matchers can be used to perform advanced replace behavior.
    * For example you could write a matcher to replace all occurrences
    * where the character 'a' is followed by a number.
    *
    * @param matcher    the matcher to use to find the deletion, null causes no action
    * @param replaceStr the replace string, null is equivalent to an empty string
    * @return this, to enable chaining
    */
  def replaceAll(matcher: StrMatcher, replaceStr: String): StrBuilder = replace(matcher, replaceStr, 0, size, -1)

  /**
    * Replaces the first match within the builder with the replace string.
    * <p>
    * Matchers can be used to perform advanced replace behavior.
    * For example you could write a matcher to replace
    * where the character 'a' is followed by a number.
    *
    * @param matcher    the matcher to use to find the deletion, null causes no action
    * @param replaceStr the replace string, null is equivalent to an empty string
    * @return this, to enable chaining
    */
  def replaceFirst(matcher: StrMatcher, replaceStr: String): StrBuilder = replace(matcher, replaceStr, 0, size, 1)

  /**
    * Advanced search and replaces within the builder using a matcher.
    * <p>
    * Matchers can be used to perform advanced behavior.
    * For example you could write a matcher to delete all occurrences
    * where the character 'a' is followed by a number.
    *
    * @param matcher      the matcher to use to find the deletion, null causes no action
    * @param replaceStr   the string to replace the match with, null is a delete
    * @param startIndex   the start index, inclusive, must be valid
    * @param endIndex     the end index, exclusive, must be valid except
    *                     that if too large it is treated as end of string
    * @param replaceCount the number of times to replace, -1 for replace all
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if start index is invalid
    */
  def replace(
    matcher: StrMatcher,
    replaceStr: String,
    startIndex: Int,
    endIndex: Int,
    replaceCount: Int): StrBuilder = {
    val newEndIndex = validateRange(startIndex, endIndex)
    replaceImpl(matcher, replaceStr, startIndex, newEndIndex, replaceCount)
  }

  /**
    * Replaces within the builder using a matcher.
    * <p>
    * Matchers can be used to perform advanced behavior.
    * For example you could write a matcher to delete all occurrences
    * where the character 'a' is followed by a number.
    *
    * @param matcher      the matcher to use to find the deletion, null causes no action
    * @param replaceStr   the string to replace the match with, null is a delete
    * @param from         the start index, must be valid
    * @param to           the end index (exclusive), must be valid
    * @param replaceCount the number of times to replace, -1 for replace all
    * @return this, to enable chaining
    * @throws java.lang.IndexOutOfBoundsException if any index is invalid
    */
  private def replaceImpl(
    matcher: StrMatcher,
    replaceStr: String,
    from: Int,
    to: Int,
    replaceCount: Int): StrBuilder = {
    if (matcher == null || size == 0) return this
    val replaceLen =
      if (replaceStr == null) 0
      else replaceStr.length

    var i = from
    var currentTo = to
    var currentReplaceCount = replaceCount

    while (i < currentTo && currentReplaceCount != 0) {
      val buf = buffer
      val removeLen = matcher.isMatch(buf, i, from, currentTo)
      if (removeLen > 0) {
        replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen)
        currentTo = currentTo - removeLen + replaceLen
        i = i + replaceLen - 1
        if (currentReplaceCount > 0) currentReplaceCount -= 1
      }

      i += 1
    }

    this
  }

  /**
    * Reverses the string builder placing each character in the opposite index.
    *
    * @return this, to enable chaining
    */
  def reverse: StrBuilder = {
    if (size == 0) return this
    val half = size / 2
    val buf = buffer
    var leftIdx = 0
    var rightIdx = size - 1
    while (leftIdx < half) {
      val swap = buf(leftIdx)
      buf(leftIdx) = buf(rightIdx)
      buf(rightIdx) = swap

      leftIdx += 1
      rightIdx -= 1
    }
    this
  }

  /**
    * Trims the builder by removing characters less than or equal to a space
    * from the beginning and end.
    *
    * @return this, to enable chaining
    */
  def trim: StrBuilder = {
    if (size == 0) return this
    var len = size
    val buf = buffer
    var pos = 0

    while (pos < len && buf(pos) <= ' ') pos += 1
    while (pos < len && buf(len - 1) <= ' ') len -= 1

    if (len < size) delete(len, size)
    if (pos > 0) delete(0, pos)
    this
  }

  /**
    * Checks whether this builder starts with the specified string.
    * <p>
    * Note that this method handles null input quietly, unlike String.
    *
    * @param str the string to search for, null returns false
    * @return true if the builder starts with the string
    */
  def startsWith(str: String): Boolean = {
    if (str == null) return false
    val len = str.length
    if (len == 0) return true
    if (len > size) return false
    for (i <- 0 until len) {
      if (buffer(i) != str.charAt(i)) return false
    }
    true
  }

  /**
    * Checks whether this builder ends with the specified string.
    * <p>
    * Note that this method handles null input quietly, unlike String.
    *
    * @param str the string to search for, null returns false
    * @return true if the builder ends with the string
    */
  def endsWith(str: String): Boolean = {
    if (str == null) return false
    val len = str.length
    if (len == 0) return true
    if (len > size) return false
    var pos = size - len
    var i = 0
    while ({
      i < len
    }) {
      if (buffer(pos) != str.charAt(i)) return false

      i += 1
      pos += 1
    }
    true
  }

  override def subSequence(startIndex: Int, endIndex: Int): CharSequence = {
    if (startIndex < 0) throw new StringIndexOutOfBoundsException(startIndex)
    if (endIndex > size) throw new StringIndexOutOfBoundsException(endIndex)
    if (startIndex > endIndex) throw new StringIndexOutOfBoundsException(endIndex - startIndex)
    substring(startIndex, endIndex)
  }

  /**
    * Extracts a portion of this string builder as a string.
    *
    * @param start the start index, inclusive, must be valid
    * @return the new string
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def substring(start: Int): String = substring(start, size)

  /**
    * Extracts a portion of this string builder as a string.
    * <p>
    * Note: This method treats an endIndex greater than the length of the
    * builder as equal to the length of the builder, and continues
    * without error, unlike StringBuffer or String.
    *
    * @param startIndex the start index, inclusive, must be valid
    * @param endIndex   the end index, exclusive, must be valid except
    *                   that if too large it is treated as end of string
    * @return the new string
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  def substring(startIndex: Int, endIndex: Int): String = {
    new String(buffer, startIndex, validateRange(startIndex, endIndex) - startIndex)
  }

  /**
    * Extracts the leftmost characters from the string builder without
    * throwing an exception.
    * <p>
    * This method extracts the left {@code length} characters from
    * the builder. If this many characters are not available, the whole
    * builder is returned. Thus the returned string may be shorter than the
    * length requested.
    *
    * @param length the number of characters to extract, negative returns empty string
    * @return the new string
    */
  def leftString(length: Int): String =
    if (length <= 0) StringUtils.EMPTY
    else if (length >= size) new String(buffer, 0, size)
    else new String(buffer, 0, length)

  /**
    * Extracts the rightmost characters from the string builder without
    * throwing an exception.
    * <p>
    * This method extracts the right {@code length} characters from
    * the builder. If this many characters are not available, the whole
    * builder is returned. Thus the returned string may be shorter than the
    * length requested.
    *
    * @param length the number of characters to extract, negative returns empty string
    * @return the new string
    */
  def rightString(length: Int): String =
    if (length <= 0) StringUtils.EMPTY
    else if (length >= size) new String(buffer, 0, size)
    else new String(buffer, size - length, length)

  /**
    * Extracts some characters from the middle of the string builder without
    * throwing an exception.
    * <p>
    * This method extracts {@code length} characters from the builder
    * at the specified index.
    * If the index is negative it is treated as zero.
    * If the index is greater than the builder size, it is treated as the builder size.
    * If the length is negative, the empty string is returned.
    * If insufficient characters are available in the builder, as much as possible is returned.
    * Thus the returned string may be shorter than the length requested.
    *
    * @param index  the index to start at, negative means zero
    * @param length the number of characters to extract, negative returns empty string
    * @return the new string
    */
  def midString(index: Int, length: Int): String = {
    val idx: Int = if (index < 0) 0 else index

    if (length <= 0 || idx >= size) StringUtils.EMPTY
    else if (size <= idx + length) new String(buffer, idx, size - idx)
    else new String(buffer, idx, length)
  }

  /**
    * Checks if the string builder contains the specified char.
    *
    * @param ch the character to find
    * @return true if the builder contains the character
    */
  def contains(ch: Char): Boolean = {
    val thisBuf = buffer
    for (i <- 0 until this.size) {
      if (thisBuf(i) == ch) return true
    }
    false
  }

  /**
    * Checks if the string builder contains the specified string.
    *
    * @param str the string to find
    * @return true if the builder contains the string
    */
  def contains(str: String): Boolean = indexOf(str, 0) >= 0

  /**
    * Checks if the string builder contains a string matched using the
    * specified matcher.
    * <p>
    * Matchers can be used to perform advanced searching behavior.
    * For example you could write a matcher to search for the character
    * 'a' followed by a number.
    *
    * @param matcher the matcher to use, null returns -1
    * @return true if the matcher finds a match in the builder
    */
  def contains(matcher: StrMatcher): Boolean = indexOf(matcher, 0) >= 0

  /**
    * Searches the string builder to find the first reference to the specified char.
    *
    * @param ch the character to find
    * @return the first index of the character, or -1 if not found
    */
  def indexOf(ch: Char): Int = indexOf(ch, 0)

  /**
    * Searches the string builder to find the first reference to the specified char.
    *
    * @param ch         the character to find
    * @param startIndex the index to start at, invalid index rounded to edge
    * @return the first index of the character, or -1 if not found
    */
  def indexOf(ch: Char, startIndex: Int): Int = {
    val newStartIndex = Math.max(startIndex, 0)
    if (newStartIndex >= size) return -1
    val thisBuf = buffer

    for (i <- newStartIndex until size) {
      if (thisBuf(i) == ch) return i
    }

    -1
  }

  /**
    * Searches the string builder to find the first reference to the specified string.
    * <p>
    * Note that a null input string will return -1, whereas the JDK throws an exception.
    *
    * @param str the string to find, null returns -1
    * @return the first index of the string, or -1 if not found
    */
  def indexOf(str: String): Int = indexOf(str, 0)

  /**
    * Searches the string builder to find the first reference to the specified
    * string starting searching from the given index.
    * <p>
    * Note that a null input string will return -1, whereas the JDK throws an exception.
    *
    * @param str        the string to find, null returns -1
    * @param startIndex the index to start at, invalid index rounded to edge
    * @return the first index of the string, or -1 if not found
    */
  def indexOf(str: String, startIndex: Int): Int = {
    val newStartIndex = Math.max(startIndex, 0)

    if (str == null || startIndex >= size) return -1
    val strLen = str.length
    if (strLen == 1) return indexOf(str.charAt(0), newStartIndex)
    if (strLen == 0) return newStartIndex
    if (strLen > size) return -1
    val thisBuf = buffer
    val len = size - strLen + 1

    for (i <- newStartIndex until len) {
      val isMatch: Boolean = (0 until strLen).forall { j =>
        str.charAt(j) == thisBuf(i + j)
      }
      if (isMatch) return i
    }
    -1
  }

  /**
    * Searches the string builder using the matcher to find the first match.
    * <p>
    * Matchers can be used to perform advanced searching behavior.
    * For example you could write a matcher to find the character 'a'
    * followed by a number.
    *
    * @param matcher the matcher to use, null returns -1
    * @return the first index matched, or -1 if not found
    */
  def indexOf(matcher: StrMatcher): Int = indexOf(matcher, 0)

  /**
    * Searches the string builder using the matcher to find the first
    * match searching from the given index.
    * <p>
    * Matchers can be used to perform advanced searching behavior.
    * For example you could write a matcher to find the character 'a'
    * followed by a number.
    *
    * @param matcher    the matcher to use, null returns -1
    * @param startIndex the index to start at, invalid index rounded to edge
    * @return the first index matched, or -1 if not found
    */
  def indexOf(matcher: StrMatcher, startIndex: Int): Int = {
    val newStartIndex = Math.max(startIndex, 0)
    if (matcher == null || newStartIndex >= size) return -1
    val len = size
    val buf = buffer
    for (i <- newStartIndex until len) {
      if (matcher.isMatch(buf, i, newStartIndex, len) > 0) return i
    }
    -1
  }

  /**
    * Searches the string builder to find the last reference to the specified char.
    *
    * @param ch the character to find
    * @return the last index of the character, or -1 if not found
    */
  def lastIndexOf(ch: Char): Int = lastIndexOf(ch, size - 1)

  /**
    * Searches the string builder to find the last reference to the specified char.
    *
    * @param ch         the character to find
    * @param startIndex the index to start at, invalid index rounded to edge
    * @return the last index of the character, or -1 if not found
    */
  def lastIndexOf(ch: Char, startIndex: Int): Int = {
    val newStartIndex =
      if (startIndex >= size) size - 1
      else startIndex

    if (newStartIndex < 0) return -1
    for (i <- newStartIndex to 0 by -1) {
      if (buffer(i) == ch) return i
    }
    -1
  }

  /**
    * Searches the string builder to find the last reference to the specified string.
    * <p>
    * Note that a null input string will return -1, whereas the JDK throws an exception.
    *
    * @param str the string to find, null returns -1
    * @return the last index of the string, or -1 if not found
    */
  def lastIndexOf(str: String): Int = lastIndexOf(str, size - 1)

  /**
    * Searches the string builder to find the last reference to the specified
    * string starting searching from the given index.
    * <p>
    * Note that a null input string will return -1, whereas the JDK throws an exception.
    *
    * @param str        the string to find, null returns -1
    * @param startIndex the index to start at, invalid index rounded to edge
    * @return the last index of the string, or -1 if not found
    */
  def lastIndexOf(str: String, startIndex: Int): Int = {
    val newStartIndex =
      if (startIndex >= size) size - 1
      else startIndex

    if (str == null || newStartIndex < 0) return -1
    val strLen = str.length
    if (strLen > 0 && strLen <= size) {
      if (strLen == 1) return lastIndexOf(str.charAt(0), newStartIndex)

      for (i <- newStartIndex - strLen + 1 to 0 by -1) {
        val isMatch: Boolean = (0 until strLen).forall { j =>
          str.charAt(j) == buffer(i + j)
        }

        if (isMatch) return i
      }
    } else if (strLen == 0) return newStartIndex

    -1
  }

  /**
    * Searches the string builder using the matcher to find the last match.
    * <p>
    * Matchers can be used to perform advanced searching behavior.
    * For example you could write a matcher to find the character 'a'
    * followed by a number.
    *
    * @param matcher the matcher to use, null returns -1
    * @return the last index matched, or -1 if not found
    */
  def lastIndexOf(matcher: StrMatcher): Int = lastIndexOf(matcher, size)

  /**
    * Searches the string builder using the matcher to find the last
    * match searching from the given index.
    * <p>
    * Matchers can be used to perform advanced searching behavior.
    * For example you could write a matcher to find the character 'a'
    * followed by a number.
    *
    * @param matcher    the matcher to use, null returns -1
    * @param startIndex the index to start at, invalid index rounded to edge
    * @return the last index matched, or -1 if not found
    */
  def lastIndexOf(matcher: StrMatcher, startIndex: Int): Int = {
    val newStartIndex =
      if (startIndex >= size) size - 1
      else startIndex

    if (matcher == null || newStartIndex < 0) return -1
    val buf = buffer
    val endIndex = newStartIndex + 1
    for (i <- newStartIndex to 0 by -1) {
      if (matcher.isMatch(buf, i, 0, endIndex) > 0) return i
    }

    -1
  }

  /**
    * Creates a tokenizer that can tokenize the contents of this builder.
    * <p>
    * This method allows the contents of this builder to be tokenized.
    * The tokenizer will be setup by default to tokenize on space, tab,
    * newline and formfeed (as per StringTokenizer). These values can be
    * changed on the tokenizer class, before retrieving the tokens.
    * <p>
    * The returned tokenizer is linked to this builder. You may intermix
    * calls to the builder and tokenizer within certain limits, however
    * there is no synchronization. Once the tokenizer has been used once,
    * it must be {@link StrTokenizer# reset ( ) reset} to pickup the latest
    * changes in the builder. For example:
    * <pre>
    * StrBuilder b = new StrBuilder();
    * b.append("a b ");
    * StrTokenizer t = b.asTokenizer();
    * String[] tokens1 = t.getTokenArray();  // returns a,b
    * b.append("c d ");
    * String[] tokens2 = t.getTokenArray();  // returns a,b (c and d ignored)
    * t.reset();              // reset causes builder changes to be picked up
    * String[] tokens3 = t.getTokenArray();  // returns a,b,c,d
    * </pre>
    * In addition to simply intermixing appends and tokenization, you can also
    * call the set methods on the tokenizer to alter how it tokenizes. Just
    * remember to call reset when you want to pickup builder changes.
    * <p>
    * Calling {@link StrTokenizer# reset ( String )} or {@link StrTokenizer# reset ( char[ ] )}
    * with a non-null value will break the link with the builder.
    *
    * @return a tokenizer that is linked to this builder
    */
  def asTokenizer = new StrBuilderTokenizer

  /**
    * Gets the contents of this builder as a Reader.
    * <p>
    * This method allows the contents of the builder to be read
    * using any standard method that expects a Reader.
    * <p>
    * To use, simply create a {@code StrBuilder}, populate it with
    * data, call {@code asReader}, and then read away.
    * <p>
    * The internal character array is shared between the builder and the reader.
    * This allows you to append to the builder after creating the reader,
    * and the changes will be picked up.
    * Note however, that no synchronization occurs, so you must perform
    * all operations with the builder and the reader in one thread.
    * <p>
    * The returned reader supports marking, and ignores the flush method.
    *
    * @return a reader that reads from this builder
    */
  def asReader = new StrBuilderReader()

  /**
    * Gets this builder as a Writer that can be written to.
    * <p>
    * This method allows you to populate the contents of the builder
    * using any standard method that takes a Writer.
    * <p>
    * To use, simply create a {@code StrBuilder},
    * call {@code asWriter}, and populate away. The data is available
    * at any time using the methods of the {@code StrBuilder}.
    * <p>
    * The internal character array is shared between the builder and the writer.
    * This allows you to intermix calls that append to the builder and
    * write using the writer and the changes will be occur correctly.
    * Note however, that no synchronization occurs, so you must perform
    * all operations with the builder and the writer in one thread.
    * <p>
    * The returned writer ignores the close and flush methods.
    *
    * @return a writer that populates this builder
    */
  def asWriter = new StrBuilderWriter

  /**
    * Appends current contents of this {@code StrBuilder} to the
    * provided {@link java.lang.Appendable}.
    * <p>
    * This method tries to avoid doing any extra copies of contents.
    *
    * @param appendable the appendable to append data to
    * @throws java.io.IOException if an I/O error occurs
    * @since 3.4
    * @see #readFrom(Readable)
    */
  @throws[IOException]
  def appendTo(appendable: Appendable): Unit = {
    if (appendable.isInstanceOf[Writer]) appendable.asInstanceOf[Writer].write(buffer, 0, size)
    else if (appendable.isInstanceOf[JStringBuilder]) appendable.asInstanceOf[JStringBuilder].append(buffer, 0, size)
    else if (appendable.isInstanceOf[StringBuffer]) appendable.asInstanceOf[StringBuffer].append(buffer, 0, size)
    else if (appendable.isInstanceOf[CharBuffer]) appendable.asInstanceOf[CharBuffer].put(buffer, 0, size)
    else appendable.append(this)
    ()
  }

  /**
    * Checks the contents of this builder against another to see if they
    * contain the same character content ignoring case.
    *
    * @param other the object to check, null returns false
    * @return true if the builders contain the same characters in the same order
    */
  def equalsIgnoreCase(other: StrBuilder): Boolean = {
    if (this eq other) return true
    if (this.size != other.size) return false
    val thisBuf = this.buffer
    val otherBuf = other.buffer
    for (i <- size - 1 to 0 by -1) {
      val c1 = thisBuf(i)
      val c2 = otherBuf(i)
      if (c1 != c2 && Character.toUpperCase(c1) != Character.toUpperCase(c2)) return false
    }
    true
  }

  /**
    * Checks the contents of this builder against another to see if they
    * contain the same character content.
    *
    * @param other the object to check, null returns false
    * @return true if the builders contain the same characters in the same order
    */
  def equals(other: StrBuilder): Boolean = {
    if (this eq other) return true
    if (other == null) return false
    if (this.size != other.size) return false
    val thisBuf = this.buffer
    val otherBuf = other.buffer
    for (i <- size - 1 to 0 by -1) {
      if (thisBuf(i) != otherBuf(i)) return false
    }
    true
  }

  /**
    * Checks the contents of this builder against another to see if they
    * contain the same character content.
    *
    * @param obj the object to check, null returns false
    * @return true if the builders contain the same characters in the same order
    */
  override def equals(obj: Any): Boolean = {
    obj match {
      case b: StrBuilder => equals(b)
      case _ => false
    }
  }

  /**
    * Gets a suitable hash code for this builder.
    *
    * @return a hash code
    */
  override def hashCode: Int = {
    val buf = buffer
    var hash = 0
    for (i <- size - 1 to 0 by -1) {
      hash = 31 * hash + buf(i)
    }
    hash
  }

  /**
    * Gets a String version of the string builder, creating a new instance
    * each time the method is called.
    * <p>
    * Note that unlike StringBuffer, the string version returned is
    * independent of the string builder.
    *
    * @return the builder as a String
    */
  override def toString = new String(buffer, 0, size)

  /**
    * Gets a StringBuffer version of the string builder, creating a
    * new instance each time the method is called.
    *
    * @return the builder as a StringBuffer
    */
  def toStringBuffer: StringBuffer = new StringBuffer(size).append(buffer, 0, size)

  /**
    * Gets a StringBuilder version of the string builder, creating a
    * new instance each time the method is called.
    *
    * @return the builder as a StringBuilder
    * @since 3.2
    */
  def toStringBuilder: JStringBuilder = new JStringBuilder(size).append(buffer, 0, size)

  /**
    * Implement the {@link org.apache.commons.lang3.builder.Builder} interface.
    *
    * @return the builder as a String
    * @since 3.2
    * @see #toString()
    */
  override def build: String = toString

  /**
    * Validates parameters defining a range of the builder.
    *
    * @param startIndex the start index, inclusive, must be valid
    * @param endIndex   the end index, exclusive, must be valid except
    *                   that if too large it is treated as end of string
    * @return the new string
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  protected def validateRange(startIndex: Int, endIndex: Int): Int = {
    if (startIndex < 0) throw new StringIndexOutOfBoundsException(startIndex)
    val newEndIndex = if (endIndex > size) size else endIndex

    if (startIndex > newEndIndex) throw new StringIndexOutOfBoundsException("end < start")
    newEndIndex
  }

  /**
    * Validates parameters defining a single index in the builder.
    *
    * @param index the index, must be valid
    * @throws java.lang.IndexOutOfBoundsException if the index is invalid
    */
  protected def validateIndex(index: Int): Unit = {
    if (index < 0 || index > size) throw new StringIndexOutOfBoundsException(index)
  }

  /**
    * Inner class to allow StrBuilder to operate as a tokenizer.
    */
  private[text] class StrBuilderTokenizer private[text] () extends StrTokenizer {
    /** {@inheritDoc} */
    override protected def tokenize(chars: Array[Char], offset: Int, count: Int): util.List[String] = {
      if (chars == null) super.tokenize(buffer, 0, self.size)
      else super.tokenize(chars, offset, count)
    }

    /** {@inheritDoc} */
    override def getContent: String = {
      val str = super.getContent

      if (str == null) self.toString
      else str
    }
  }

  /**
    * Inner class to allow StrBuilder to operate as a reader.
    */
  private[text] class StrBuilderReader private[text] () extends Reader {
    /** The current stream position. */
    private var pos: Int = 0
    /** The last mark position. */
    private var mark = 0

    override def close(): Unit = {
      // do nothing
    }

    override def read: Int = {
      if (ready == false) return -1
      charAt({ pos += 1; pos - 1 }).toInt
    }

    override def read(b: Array[Char], off: Int, len: Int): Int = {
      if (off < 0 || len < 0 || off > b.length || (off + len) > b.length || (off + len) < 0)
        throw new IndexOutOfBoundsException
      if (len == 0) return 0
      if (pos >= size) return -1

      val updatedLen: Int = if (pos + len > size) size - pos else len
      getChars(pos, pos + updatedLen, b, off)
      pos += updatedLen
      updatedLen
    }

    override def skip(n: Long): Long = {
      val updatedN: Int = if (pos + n > size) size - pos else n.toInt

      if (updatedN < 0) return 0
      pos += updatedN

      updatedN.toLong
    }

    override def ready: Boolean = pos < size

    override def markSupported = true

    override def mark(readAheadLimit: Int): Unit = {
      mark = pos
    }

    override def reset(): Unit = {
      pos = mark
    }
  }

  /**
    * Inner class to allow StrBuilder to operate as a writer.
    */
  private[text] class StrBuilderWriter private[text] () extends Writer {
    override def close(): Unit = {}

    override def flush(): Unit = {}

    override def write(c: Int): Unit = {
      self.append(c.toChar)
      ()
    }

    override def write(cbuf: Array[Char]): Unit = {
      self.append(cbuf)
      ()
    }

    override def write(cbuf: Array[Char], off: Int, len: Int): Unit = {
      self.append(cbuf, off, len)
      ()
    }

    override def write(str: String): Unit = {
      self.append(str)
      ()
    }

    override def write(str: String, off: Int, len: Int): Unit = {
      self.append(str, off, len)
      ()
    }
  }

}
