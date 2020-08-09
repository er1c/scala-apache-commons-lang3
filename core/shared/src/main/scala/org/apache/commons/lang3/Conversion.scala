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

import java.util.UUID

/**
  * <p>
  * Static methods to convert a type into another, with endianness and bit ordering awareness.
  * </p>
  * <p>
  * The methods names follow a naming rule:<br>
  * {@code <source type>[source endianness][source bit ordering]To<destination type>[destination endianness][destination bit ordering]}
  * </p>
  * <p>
  * Source/destination type fields is one of the following:
  * </p>
  * <ul>
  * <li>binary: an array of booleans</li>
  * <li>byte or byteArray</li>
  * <li>int or intArray</li>
  * <li>long or longArray</li>
  * <li>hex: a String containing hexadecimal digits (lowercase in destination)</li>
  * <li>hexDigit: a Char containing a hexadecimal digit (lowercase in destination)</li>
  * <li>uuid</li>
  * </ul>
  * <p>
  * Endianness field: little endian is the default, in this case the field is absent. In case of
  * big endian, the field is "Be".<br> Bit ordering: Lsb0 is the default, in this case the field
  * is absent. In case of Msb0, the field is "Msb0".
  * </p>
  * <p>
  * Example: intBeMsb0ToHex convert an int with big endian byte order and Msb0 bit order into its
  * hexadecimal string representation
  * </p>
  * <p>
  * Most of the methods provide only default encoding for destination, this limits the number of
  * ways to do one thing. Unless you are dealing with data from/to outside of the JVM platform,
  * you should not need to use "Be" and "Msb0" methods.
  * </p>
  * <p>
  * Development status: work on going, only a part of the little endian, Lsb0 methods implemented
  * so far.
  * </p>
  *
  * @since 3.2
  */
object Conversion {
  private val TTTT = Array(true, true, true, true)
  private val FTTT = Array(false, true, true, true)
  private val TFTT = Array(true, false, true, true)
  private val FFTT = Array(false, false, true, true)
  private val TTFT = Array(true, true, false, true)
  private val FTFT = Array(false, true, false, true)
  private val TFFT = Array(true, false, false, true)
  private val FFFT = Array(false, false, false, true)
  private val TTTF = Array(true, true, true, false)
  private val FTTF = Array(false, true, true, false)
  private val TFTF = Array(true, false, true, false)
  private val FFTF = Array(false, false, true, false)
  private val TTFF = Array(true, true, false, false)
  private val FTFF = Array(false, true, false, false)
  private val TFFF = Array(true, false, false, false)
  private val FFFF = Array(false, false, false, false)

  /**
    * <p>
    * Converts a hexadecimal digit into an int using the default (Lsb0) bit ordering.
    * </p>
    * <p>
    * '1' is converted to 1
    * </p>
    *
    * @param hexDigit the hexadecimal digit to convert
    * @return an int equals to {@code hexDigit}
    * @throws java.lang.IllegalArgumentException if {@code hexDigit} is not a hexadecimal digit
    */
  def hexDigitToInt(hexDigit: Char): Int = {
    val digit = Character.digit(hexDigit, 16)
    if (digit < 0) throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit")
    digit
  }

  /**
    * <p>
    * Converts a hexadecimal digit into an int using the Msb0 bit ordering.
    * </p>
    * <p>
    * '1' is converted to 8
    * </p>
    *
    * @param hexDigit the hexadecimal digit to convert
    * @return an int equals to {@code hexDigit}
    * @throws java.lang.IllegalArgumentException if {@code hexDigit} is not a hexadecimal digit
    */
  def hexDigitMsb0ToInt(hexDigit: Char): Int =
    hexDigit match {
      case '0' =>
        0x0
      case '1' =>
        0x8
      case '2' =>
        0x4
      case '3' =>
        0xc
      case '4' =>
        0x2
      case '5' =>
        0xa
      case '6' =>
        0x6
      case '7' =>
        0xe
      case '8' =>
        0x1
      case '9' =>
        0x9
      case 'a' | 'A' =>
        0x5
      case 'b' | 'B' =>
        0xd
      case 'c' | 'C' =>
        0x3
      case 'd' | 'D' =>
        0xb
      case 'e' | 'E' =>
        0x7
      case 'f' | 'F' =>
        0xf
      case _ =>
        throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit")
    }

  /**
    * <p>
    * Converts a hexadecimal digit into binary (represented as boolean array) using the default
    * (Lsb0) bit ordering.
    * </p>
    * <p>
    * '1' is converted as follow: (1, 0, 0, 0)
    * </p>
    *
    * @param hexDigit the hexadecimal digit to convert
    * @return a boolean array with the binary representation of {@code hexDigit}
    * @throws java.lang.IllegalArgumentException if {@code hexDigit} is not a hexadecimal digit
    */
  def hexDigitToBinary(hexDigit: Char): Array[Boolean] =
    hexDigit match {
      case '0' =>
        FFFF.clone
      case '1' =>
        TFFF.clone
      case '2' =>
        FTFF.clone
      case '3' =>
        TTFF.clone
      case '4' =>
        FFTF.clone
      case '5' =>
        TFTF.clone
      case '6' =>
        FTTF.clone
      case '7' =>
        TTTF.clone
      case '8' =>
        FFFT.clone
      case '9' =>
        TFFT.clone
      case 'a' | 'A' =>
        FTFT.clone
      case 'b' | 'B' =>
        TTFT.clone
      case 'c' | 'C' =>
        FFTT.clone
      case 'd' | 'D' =>
        TFTT.clone
      case 'e' | 'E' =>
        FTTT.clone
      case 'f' | 'F' =>
        TTTT.clone
      case _ =>
        throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit")
    }

  /**
    * <p>
    * Converts a hexadecimal digit into binary (represented as boolean array) using the Msb0
    * bit ordering.
    * </p>
    * <p>
    * '1' is converted as follow: (0, 0, 0, 1)
    * </p>
    *
    * @param hexDigit the hexadecimal digit to convert
    * @return a boolean array with the binary representation of {@code hexDigit}
    * @throws java.lang.IllegalArgumentException if {@code hexDigit} is not a hexadecimal digit
    */
  def hexDigitMsb0ToBinary(hexDigit: Char): Array[Boolean] =
    hexDigit match {
      case '0' =>
        FFFF.clone
      case '1' =>
        FFFT.clone
      case '2' =>
        FFTF.clone
      case '3' =>
        FFTT.clone
      case '4' =>
        FTFF.clone
      case '5' =>
        FTFT.clone
      case '6' =>
        FTTF.clone
      case '7' =>
        FTTT.clone
      case '8' =>
        TFFF.clone
      case '9' =>
        TFFT.clone
      case 'a' | 'A' =>
        TFTF.clone
      case 'b' | 'B' =>
        TFTT.clone
      case 'c' | 'C' =>
        TTFF.clone
      case 'd' | 'D' =>
        TTFT.clone
      case 'e' | 'E' =>
        TTTF.clone
      case 'f' | 'F' =>
        TTTT.clone
      case _ =>
        throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit")
    }

  /**
    * <p>
    * Converts binary (represented as boolean array) to a hexadecimal digit using the default
    * (Lsb0) bit ordering.
    * </p>
    * <p>
    * (1, 0, 0, 0) is converted as follow: '1'
    * </p>
    *
    * @param src the binary to convert
    * @return a hexadecimal digit representing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code src} is empty
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    */
  def binaryToHexDigit(src: Array[Boolean]): Char = binaryToHexDigit(src, 0)

  /**
    * <p>
    * Converts binary (represented as boolean array) to a hexadecimal digit using the default
    * (Lsb0) bit ordering.
    * </p>
    * <p>
    * (1, 0, 0, 0) is converted as follow: '1'
    * </p>
    *
    * @param src    the binary to convert
    * @param srcPos the position of the lsb to start the conversion
    * @return a hexadecimal digit representing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code src} is empty
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    */
  def binaryToHexDigit(src: Array[Boolean], srcPos: Int): Char = {
    if (src.length == 0) throw new IllegalArgumentException("Cannot convert an empty array.")
    if (src.length > srcPos + 3 && src(srcPos + 3)) {
      if (src(srcPos + 2)) {
        if (src(srcPos + 1)) return {
          if (src(srcPos)) 'f'
          else 'e'
        }

        return {
          if (src(srcPos)) 'd'
          else 'c'
        }
      }

      if (src(srcPos + 1)) return {
        if (src(srcPos)) 'b'
        else 'a'
      }

      return {
        if (src(srcPos)) '9'
        else '8'
      }
    }
    if (src.length > srcPos + 2 && src(srcPos + 2)) {
      if (src(srcPos + 1)) return {
        if (src(srcPos)) '7'
        else '6'
      }

      return {
        if (src(srcPos)) '5'
        else '4'
      }
    }
    if (src.length > srcPos + 1 && src(srcPos + 1)) return {
      if (src(srcPos)) '3'
      else '2'
    }

    if (src(srcPos)) '1'
    else '0'
  }

  /**
    * <p>
    * Converts binary (represented as boolean array) to a hexadecimal digit using the Msb0 bit
    * ordering.
    * </p>
    * <p>
    * (1, 0, 0, 0) is converted as follow: '8'
    * </p>
    *
    * @param src the binary to convert
    * @return a hexadecimal digit representing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code src} is empty, {@code src.length < 4} or
    *                                  {@code src.length > 8}
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    */
  def binaryToHexDigitMsb0_4bits(src: Array[Boolean]): Char = binaryToHexDigitMsb0_4bits(src, 0)

  /**
    * <p>
    * Converts binary (represented as boolean array) to a hexadecimal digit using the Msb0 bit
    * ordering.
    * </p>
    * <p>
    * (1, 0, 0, 0) is converted as follow: '8' (1, 0, 0, 1, 1, 0, 1, 0) with srcPos = 3 is converted
    * to 'D'
    * </p>
    *
    * @param src    the binary to convert
    * @param srcPos the position of the lsb to start the conversion
    * @return a hexadecimal digit representing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code src} is empty, {@code src.length > 8} or
    *                                  {@code src.length - srcPos < 4}
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    */
  def binaryToHexDigitMsb0_4bits(src: Array[Boolean], srcPos: Int): Char = {
    if (src.length > 8) throw new IllegalArgumentException("src.length>8: src.length=" + src.length)
    if (src.length - srcPos < 4)
      throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + src.length + ", srcPos=" + srcPos)
    if (src(srcPos + 3)) {
      if (src(srcPos + 2)) {
        if (src(srcPos + 1)) return {
          if (src(srcPos)) 'f'
          else '7'
        }
        return {
          if (src(srcPos)) 'b'
          else '3'
        }
      }

      if (src(srcPos + 1)) return {
        if (src(srcPos)) 'd'
        else '5'
      }

      return {
        if (src(srcPos)) '9'
        else '1'
      }
    }

    if (src(srcPos + 2)) {
      if (src(srcPos + 1)) return {
        if (src(srcPos)) 'e'
        else '6'
      }

      return {
        if (src(srcPos)) 'a'
        else '2'
      }
    }

    if (src(srcPos + 1)) return {
      if (src(srcPos)) 'c'
      else '4'
    }

    if (src(srcPos)) '8'
    else '0'
  }

  /**
    * <p>
    * Converts the first 4 bits of a binary (represented as boolean array) in big endian Msb0
    * bit ordering to a hexadecimal digit.
    * </p>
    * <p>
    * (1, 0, 0, 0) is converted as follow: '8' (1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0) is converted
    * to '4'
    * </p>
    *
    * @param src the binary to convert
    * @return a hexadecimal digit representing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code src} is empty
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    */
  def binaryBeMsb0ToHexDigit(src: Array[Boolean]): Char = binaryBeMsb0ToHexDigit(src, 0)

  /**
    * <p>
    * Converts a binary (represented as boolean array) in big endian Msb0 bit ordering to a
    * hexadecimal digit.
    * </p>
    * <p>
    * (1, 0, 0, 0) with srcPos = 0 is converted as follow: '8' (1, 0, 0, 0, 0, 0, 0, 0,
    * 0, 0, 0, 1, 0, 1, 0, 0) with srcPos = 2 is converted to '5'
    * </p>
    *
    * @param src    the binary to convert
    * @param srcPos the position of the lsb to start the conversion
    * @return a hexadecimal digit representing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code src} is empty
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    */
  def binaryBeMsb0ToHexDigit(src: Array[Boolean], srcPos: Int): Char = {
    if (src.length == 0) throw new IllegalArgumentException("Cannot convert an empty array.")
    val beSrcPos = src.length - 1 - srcPos
    val srcLen = Math.min(4, beSrcPos + 1)
    val paddedSrc = new Array[Boolean](4)
    System.arraycopy(src, beSrcPos + 1 - srcLen, paddedSrc, 4 - srcLen, srcLen)

    val newSrc = paddedSrc
    val newSrcPos = 0
    if (newSrc(newSrcPos)) {
      if (newSrc.length > newSrcPos + 1 && newSrc(newSrcPos + 1)) {
        if (newSrc.length > newSrcPos + 2 && newSrc(newSrcPos + 2)) {
          return {
            if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) 'f'
            else 'e'
          }
        }

        return {
          if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) 'd'
          else 'c'
        }
      }
      if (newSrc.length > newSrcPos + 2 && newSrc(newSrcPos + 2)) {
        return if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) 'b'
        else 'a'
      }
      return {
        if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) '9'
        else '8'
      }
    }
    if (newSrc.length > newSrcPos + 1 && newSrc(newSrcPos + 1)) {
      if (newSrc.length > newSrcPos + 2 && newSrc(newSrcPos + 2)) {
        return {
          if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) '7'
          else '6'
        }
      }
      return {
        if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) '5'
        else '4'
      }
    }
    if (newSrc.length > newSrcPos + 2 && newSrc(newSrcPos + 2)) {
      return {
        if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) '3'
        else '2'
      }
    }

    if (newSrc.length > newSrcPos + 3 && newSrc(newSrcPos + 3)) '1'
    else '0'
  }

  /**
    * <p>
    * Converts the 4 lsb of an int to a hexadecimal digit.
    * </p>
    * <p>
    * 0 returns '0'
    * </p>
    * <p>
    * 1 returns '1'
    * </p>
    * <p>
    * 10 returns 'A' and so on...
    * </p>
    *
    * @param nibble the 4 bits to convert
    * @return a hexadecimal digit representing the 4 lsb of {@code nibble}
    * @throws java.lang.IllegalArgumentException if {@code nibble < 0} or {@code nibble > 15}
    */
  def intToHexDigit(nibble: Int): Char = {
    val c = Character.forDigit(nibble, 16)
    if (c == Character.MIN_VALUE) throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble)
    c
  }

  /**
    * <p>
    * Converts the 4 lsb of an int to a hexadecimal digit encoded using the Msb0 bit ordering.
    * </p>
    * <p>
    * 0 returns '0'
    * </p>
    * <p>
    * 1 returns '8'
    * </p>
    * <p>
    * 10 returns '5' and so on...
    * </p>
    *
    * @param nibble the 4 bits to convert
    * @return a hexadecimal digit representing the 4 lsb of {@code nibble}
    * @throws java.lang.IllegalArgumentException if {@code nibble < 0} or {@code nibble > 15}
    */
  def intToHexDigitMsb0(nibble: Int): Char =
    nibble match {
      case 0x0 =>
        '0'
      case 0x1 =>
        '8'
      case 0x2 =>
        '4'
      case 0x3 =>
        'c'
      case 0x4 =>
        '2'
      case 0x5 =>
        'a'
      case 0x6 =>
        '6'
      case 0x7 =>
        'e'
      case 0x8 =>
        '1'
      case 0x9 =>
        '9'
      case 0xa =>
        '5'
      case 0xb =>
        'd'
      case 0xc =>
        '3'
      case 0xd =>
        'b'
      case 0xe =>
        '7'
      case 0xf =>
        'f'
      case _ =>
        throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble)
    }

  /**
    * <p>
    * Converts an array of int into a long using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src     the int array to convert
    * @param srcPos  the position in {@code src}, in int unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination long
    * @param dstPos  the position of the lsb, in bits, in the result long
    * @param nInts   the number of ints to convert
    * @return a long containing the selected bits
    * @throws java.lang.IllegalArgumentException       if {@code (nInts-1)*32+dstPos >= 64}
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nInts > src.length}
    */
  def intArrayToLong(src: Array[Int], srcPos: Int, dstInit: Long, dstPos: Int, nInts: Int): Long = {
    if (src.length == 0 && srcPos == 0 || 0 == nInts) return dstInit
    if ((nInts - 1) * 32 + dstPos >= 64)
      throw new IllegalArgumentException("(nInts-1)*32+dstPos is greater or equal to than 64")
    var out = dstInit
    for (i <- 0 until nInts) {
      val shift = i * 32 + dstPos
      val bits = (0xffffffffL & src(i + srcPos)) << shift
      val mask = 0xffffffffL << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of short into a long using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the short array to convert
    * @param srcPos  the position in {@code src}, in short unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination long
    * @param dstPos  the position of the lsb, in bits, in the result long
    * @param nShorts the number of shorts to convert
    * @return a long containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nShorts-1)*16+dstPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nShorts > src.length}
    */
  def shortArrayToLong(src: Array[Short], srcPos: Int, dstInit: Long, dstPos: Int, nShorts: Int): Long = {
    if (src.length == 0 && srcPos == 0 || 0 == nShorts) return dstInit
    if ((nShorts - 1) * 16 + dstPos >= 64)
      throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greater or equal to than 64")
    var out = dstInit
    for (i <- 0 until nShorts) {
      val shift = i * 16 + dstPos
      val bits = (0xffffL & src(i + srcPos)) << shift
      val mask = 0xffffL << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of short into an int using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the short array to convert
    * @param srcPos  the position in {@code src}, in short unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination int
    * @param dstPos  the position of the lsb, in bits, in the result int
    * @param nShorts the number of shorts to convert
    * @return an int containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nShorts-1)*16+dstPos >= 32}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nShorts > src.length}
    */
  def shortArrayToInt(src: Array[Short], srcPos: Int, dstInit: Int, dstPos: Int, nShorts: Int): Int = {
    if (src.length == 0 && srcPos == 0 || 0 == nShorts) return dstInit
    if ((nShorts - 1) * 16 + dstPos >= 32)
      throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greater or equal to than 32")
    var out = dstInit
    for (i <- 0 until nShorts) {
      val shift = i * 16 + dstPos
      val bits = (0xffff & src(i + srcPos)) << shift
      val mask = 0xffff << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of byte into a long using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the byte array to convert
    * @param srcPos  the position in {@code src}, in byte unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination long
    * @param dstPos  the position of the lsb, in bits, in the result long
    * @param nBytes  the number of bytes to convert
    * @return a long containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nBytes-1)*8+dstPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBytes > src.length}
    */
  def byteArrayToLong(src: Array[Byte], srcPos: Int, dstInit: Long, dstPos: Int, nBytes: Int): Long = {
    if (src.length == 0 && srcPos == 0 || 0 == nBytes) return dstInit
    if ((nBytes - 1) * 8 + dstPos >= 64)
      throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greater or equal to than 64")
    var out = dstInit
    for (i <- 0 until nBytes) {
      val shift = i * 8 + dstPos
      val bits = (0xffL & src(i + srcPos)) << shift
      val mask = 0xffL << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of byte into an int using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src     the byte array to convert
    * @param srcPos  the position in {@code src}, in byte unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination int
    * @param dstPos  the position of the lsb, in bits, in the result int
    * @param nBytes  the number of bytes to convert
    * @return an int containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nBytes-1)*8+dstPos >= 32}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBytes > src.length}
    */
  def byteArrayToInt(src: Array[Byte], srcPos: Int, dstInit: Int, dstPos: Int, nBytes: Int): Int = {
    if (src.length == 0 && srcPos == 0 || 0 == nBytes) return dstInit
    if ((nBytes - 1) * 8 + dstPos >= 32)
      throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greater or equal to than 32")
    var out = dstInit
    for (i <- 0 until nBytes) {
      val shift = i * 8 + dstPos
      val bits = (0xff & src(i + srcPos)) << shift
      val mask = 0xff << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of byte into a short using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the byte array to convert
    * @param srcPos  the position in {@code src}, in byte unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination short
    * @param dstPos  the position of the lsb, in bits, in the result short
    * @param nBytes  the number of bytes to convert
    * @return a short containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nBytes-1)*8+dstPos >= 16}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBytes > src.length}
    */
  def byteArrayToShort(src: Array[Byte], srcPos: Int, dstInit: Short, dstPos: Int, nBytes: Int): Short = {
    if (src.length == 0 && srcPos == 0 || 0 == nBytes) return dstInit
    if ((nBytes - 1) * 8 + dstPos >= 16)
      throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greater or equal to than 16")
    var out = dstInit
    for (i <- 0 until nBytes) {
      val shift = i * 8 + dstPos
      val bits = (0xff & src(i + srcPos)) << shift
      val mask = 0xff << shift
      out = ((out & ~mask) | bits).toShort
    }
    out
  }

  /**
    * <p>
    * Converts an array of Char into a long using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the hex string to convert
    * @param srcPos  the position in {@code src}, in Char unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination long
    * @param dstPos  the position of the lsb, in bits, in the result long
    * @param nHex    the number of Chars to convert
    * @return a long containing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code (nHexs-1)*4+dstPos >= 64}
    */
  def hexToLong(src: String, srcPos: Int, dstInit: Long, dstPos: Int, nHex: Int): Long = {
    if (0 == nHex) return dstInit
    if ((nHex - 1) * 4 + dstPos >= 64)
      throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 64")
    var out = dstInit
    for (i <- 0 until nHex) {
      val shift = i * 4 + dstPos
      val bits = (0xfL & hexDigitToInt(src.charAt(i + srcPos))) << shift
      val mask = 0xfL << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of Char into an int using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src     the hex string to convert
    * @param srcPos  the position in {@code src}, in Char unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination int
    * @param dstPos  the position of the lsb, in bits, in the result int
    * @param nHex    the number of Chars to convert
    * @return an int containing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code (nHexs-1)*4+dstPos >= 32}
    */
  def hexToInt(src: String, srcPos: Int, dstInit: Int, dstPos: Int, nHex: Int): Int = {
    if (0 == nHex) return dstInit
    if ((nHex - 1) * 4 + dstPos >= 32)
      throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 32")
    var out = dstInit
    for (i <- 0 until nHex) {
      val shift = i * 4 + dstPos
      val bits = (0xf & hexDigitToInt(src.charAt(i + srcPos))) << shift
      val mask = 0xf << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts an array of Char into a short using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the hex string to convert
    * @param srcPos  the position in {@code src}, in Char unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination short
    * @param dstPos  the position of the lsb, in bits, in the result short
    * @param nHex    the number of Chars to convert
    * @return a short containing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code (nHexs-1)*4+dstPos >= 16}
    */
  def hexToShort(src: String, srcPos: Int, dstInit: Short, dstPos: Int, nHex: Int): Short = {
    if (0 == nHex) return dstInit
    if ((nHex - 1) * 4 + dstPos >= 16)
      throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 16")
    var out = dstInit
    for (i <- 0 until nHex) {
      val shift = i * 4 + dstPos
      val bits = (0xf & hexDigitToInt(src.charAt(i + srcPos))) << shift
      val mask = 0xf << shift
      out = ((out & ~mask) | bits).toShort
    }
    out
  }

  /**
    * <p>
    * Converts an array of Char into a byte using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the hex string to convert
    * @param srcPos  the position in {@code src}, in Char unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination byte
    * @param dstPos  the position of the lsb, in bits, in the result byte
    * @param nHex    the number of Chars to convert
    * @return a byte containing the selected bits
    * @throws java.lang.IllegalArgumentException if {@code (nHexs-1)*4+dstPos >= 8}
    */
  def hexToByte(src: String, srcPos: Int, dstInit: Byte, dstPos: Int, nHex: Int): Byte = {
    if (0 == nHex) return dstInit
    if ((nHex - 1) * 4 + dstPos >= 8)
      throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greater or equal to than 8")
    var out = dstInit
    for (i <- 0 until nHex) {
      val shift = i * 4 + dstPos
      val bits = (0xf & hexDigitToInt(src.charAt(i + srcPos))) << shift
      val mask = 0xf << shift
      out = ((out & ~mask) | bits).toByte
    }
    out
  }

  /**
    * <p>
    * Converts binary (represented as boolean array) into a long using the default (little
    * endian, Lsb0) byte and bit ordering.
    * </p>
    *
    * @param src     the binary to convert
    * @param srcPos  the position in {@code src}, in boolean unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination long
    * @param dstPos  the position of the lsb, in bits, in the result long
    * @param nBools  the number of booleans to convert
    * @return a long containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+dstPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBools > src.length}
    */
  def binaryToLong(src: Array[Boolean], srcPos: Int, dstInit: Long, dstPos: Int, nBools: Int): Long = {
    if (src.length == 0 && srcPos == 0 || 0 == nBools) return dstInit
    if (nBools - 1 + dstPos >= 64) throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 64")
    var out = dstInit
    for (i <- 0 until nBools) {
      val shift = i + dstPos
      val bits = (if (src(i + srcPos)) 1L
                  else 0) << shift
      val mask = 0x1L << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts binary (represented as boolean array) into an int using the default (little
    * endian, Lsb0) byte and bit ordering.
    * </p>
    *
    * @param src     the binary to convert
    * @param srcPos  the position in {@code src}, in boolean unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination int
    * @param dstPos  the position of the lsb, in bits, in the result int
    * @param nBools  the number of booleans to convert
    * @return an int containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+dstPos >= 32}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBools > src.length}
    */
  def binaryToInt(src: Array[Boolean], srcPos: Int, dstInit: Int, dstPos: Int, nBools: Int): Int = {
    if (src.length == 0 && srcPos == 0 || 0 == nBools) return dstInit
    if (nBools - 1 + dstPos >= 32) throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 32")
    var out = dstInit
    for (i <- 0 until nBools) {
      val shift = i + dstPos
      val bits = (if (src(i + srcPos)) 1
                  else 0) << shift
      val mask = 0x1 << shift
      out = (out & ~mask) | bits
    }
    out
  }

  /**
    * <p>
    * Converts binary (represented as boolean array) into a short using the default (little
    * endian, Lsb0) byte and bit ordering.
    * </p>
    *
    * @param src     the binary to convert
    * @param srcPos  the position in {@code src}, in boolean unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination short
    * @param dstPos  the position of the lsb, in bits, in the result short
    * @param nBools  the number of booleans to convert
    * @return a short containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+dstPos >= 16}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBools > src.length}
    */
  def binaryToShort(src: Array[Boolean], srcPos: Int, dstInit: Short, dstPos: Int, nBools: Int): Short = {
    if (src.length == 0 && srcPos == 0 || 0 == nBools) return dstInit
    if (nBools - 1 + dstPos >= 16) throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 16")
    var out = dstInit
    for (i <- 0 until nBools) {
      val shift = i + dstPos
      val bits = (if (src(i + srcPos)) 1
                  else 0) << shift
      val mask = 0x1 << shift
      out = ((out & ~mask) | bits).toShort
    }
    out
  }

  /**
    * <p>
    * Converts binary (represented as boolean array) into a byte using the default (little
    * endian, Lsb0) byte and bit ordering.
    * </p>
    *
    * @param src     the binary to convert
    * @param srcPos  the position in {@code src}, in boolean unit, from where to start the
    *                conversion
    * @param dstInit initial value of the destination byte
    * @param dstPos  the position of the lsb, in bits, in the result byte
    * @param nBools  the number of booleans to convert
    * @return a byte containing the selected bits
    * @throws java.lang.NullPointerException           if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+dstPos >= 8}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code srcPos + nBools > src.length}
    */
  def binaryToByte(src: Array[Boolean], srcPos: Int, dstInit: Byte, dstPos: Int, nBools: Int): Byte = {
    if (src.length == 0 && srcPos == 0 || 0 == nBools) return dstInit
    if (nBools - 1 + dstPos >= 8) throw new IllegalArgumentException("nBools-1+dstPos is greater or equal to than 8")
    var out = dstInit
    for (i <- 0 until nBools) {
      val shift = i + dstPos
      val bits = (if (src(i + srcPos)) 1
                  else 0) << shift
      val mask = 0x1 << shift
      out = ((out & ~mask) | bits).toByte
    }
    out
  }

  /**
    * <p>
    * Converts a long into an array of int using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src    the long to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nInts  the number of ints to copy to {@code dst}, must be smaller or equal to the
    *               width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null} and {@code nInts > 0}
    * @throws java.lang.IllegalArgumentException       if {@code (nInts-1)*32+srcPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nInts > dst.length}
    */
  def longToIntArray(src: Long, srcPos: Int, dst: Array[Int], dstPos: Int, nInts: Int): Array[Int] = {
    if (0 == nInts) return dst
    if ((nInts - 1) * 32 + srcPos >= 64)
      throw new IllegalArgumentException("(nInts-1)*32+srcPos is greater or equal to than 64")
    for (i <- 0 until nInts) {
      val shift = i * 32 + srcPos
      dst(dstPos + i) = (0xffffffff & (src >> shift)).toInt
    }
    dst
  }

  /**
    * <p>
    * Converts a long into an array of short using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the long to convert
    * @param srcPos  the position in {@code src}, in bits, from where to start the conversion
    * @param dst     the destination array
    * @param dstPos  the position in {@code dst} where to copy the result
    * @param nShorts the number of shorts to copy to {@code dst}, must be smaller or equal to
    *                the width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nShorts-1)*16+srcPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nShorts > dst.length}
    */
  def longToShortArray(src: Long, srcPos: Int, dst: Array[Short], dstPos: Int, nShorts: Int): Array[Short] = {
    if (0 == nShorts) return dst
    if ((nShorts - 1) * 16 + srcPos >= 64)
      throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greater or equal to than 64")
    for (i <- 0 until nShorts) {
      val shift = i * 16 + srcPos
      dst(dstPos + i) = (0xffff & (src >> shift)).toShort
    }
    dst
  }

  /**
    * <p>
    * Converts an int into an array of short using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the int to convert
    * @param srcPos  the position in {@code src}, in bits, from where to start the conversion
    * @param dst     the destination array
    * @param dstPos  the position in {@code dst} where to copy the result
    * @param nShorts the number of shorts to copy to {@code dst}, must be smaller or equal to
    *                the width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nShorts-1)*16+srcPos >= 32}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nShorts > dst.length}
    */
  def intToShortArray(src: Int, srcPos: Int, dst: Array[Short], dstPos: Int, nShorts: Int): Array[Short] = {
    if (0 == nShorts) return dst
    if ((nShorts - 1) * 16 + srcPos >= 32)
      throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greater or equal to than 32")
    for (i <- 0 until nShorts) {
      val shift = i * 16 + srcPos
      dst(dstPos + i) = (0xffff & (src >> shift)).toShort
    }
    dst
  }

  /**
    * <p>
    * Converts a long into an array of byte using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src    the long to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the
    *               width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nBytes-1)*8+srcPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}
    */
  def longToByteArray(src: Long, srcPos: Int, dst: Array[Byte], dstPos: Int, nBytes: Int): Array[Byte] = {
    if (0 == nBytes) return dst
    if ((nBytes - 1) * 8 + srcPos >= 64)
      throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greater or equal to than 64")
    for (i <- 0 until nBytes) {
      val shift = i * 8 + srcPos
      dst(dstPos + i) = (0xff & (src >> shift)).toByte
    }
    dst
  }

  /**
    * <p>
    * Converts an int into an array of byte using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src    the int to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the
    *               width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nBytes-1)*8+srcPos >= 32}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}
    */
  def intToByteArray(src: Int, srcPos: Int, dst: Array[Byte], dstPos: Int, nBytes: Int): Array[Byte] = {
    if (0 == nBytes) return dst
    if ((nBytes - 1) * 8 + srcPos >= 32)
      throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greater or equal to than 32")
    for (i <- 0 until nBytes) {
      val shift = i * 8 + srcPos
      dst(dstPos + i) = (0xff & (src >> shift)).toByte
    }
    dst
  }

  /**
    * <p>
    * Converts a short into an array of byte using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src    the short to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the
    *               width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code (nBytes-1)*8+srcPos >= 16}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}
    */
  def shortToByteArray(src: Short, srcPos: Int, dst: Array[Byte], dstPos: Int, nBytes: Int): Array[Byte] = {
    if (0 == nBytes) return dst
    if ((nBytes - 1) * 8 + srcPos >= 16)
      throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greater or equal to than 16")
    for (i <- 0 until nBytes) {
      val shift = i * 8 + srcPos
      dst(dstPos + i) = (0xff & (src >> shift)).toByte
    }
    dst
  }

  /**
    * <p>
    * Converts a long into an array of Char using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the long to convert
    * @param srcPos  the position in {@code src}, in bits, from where to start the conversion
    * @param dstInit the initial value for the result String
    * @param dstPos  the position in {@code dst} where to copy the result
    * @param nHexs   the number of Chars to copy to {@code dst}, must be smaller or equal to the
    *                width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.IllegalArgumentException        if {@code (nHexs-1)*4+srcPos >= 64}
    * @throws java.lang.StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}
    */
  def longToHex(src: Long, srcPos: Int, dstInit: String, dstPos: Int, nHexs: Int): String = {
    if (0 == nHexs) return dstInit
    if ((nHexs - 1) * 4 + srcPos >= 64)
      throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 64")
    val sb = new StringBuilder(dstInit)
    var append = sb.length
    for (i <- 0 until nHexs) {
      val shift = i * 4 + srcPos
      val bits = (0xf & (src >> shift)).toInt
      if (dstPos + i == append) {
        append += 1
        sb.append(intToHexDigit(bits))
      } else sb.setCharAt(dstPos + i, intToHexDigit(bits))
    }
    sb.toString
  }

  /**
    * <p>
    * Converts an int into an array of Char using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src     the int to convert
    * @param srcPos  the position in {@code src}, in bits, from where to start the conversion
    * @param dstInit the initial value for the result String
    * @param dstPos  the position in {@code dst} where to copy the result
    * @param nHexs   the number of Chars to copy to {@code dst}, must be smaller or equal to the
    *                width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.IllegalArgumentException        if {@code (nHexs-1)*4+srcPos >= 32}
    * @throws java.lang.StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}
    */
  def intToHex(src: Int, srcPos: Int, dstInit: String, dstPos: Int, nHexs: Int): String = {
    if (0 == nHexs) return dstInit
    if ((nHexs - 1) * 4 + srcPos >= 32)
      throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 32")
    val sb = new StringBuilder(dstInit)
    var append = sb.length
    for (i <- 0 until nHexs) {
      val shift = i * 4 + srcPos
      val bits = 0xf & (src >> shift)
      if (dstPos + i == append) {
        append += 1
        sb.append(intToHexDigit(bits))
      } else sb.setCharAt(dstPos + i, intToHexDigit(bits))
    }
    sb.toString
  }

  /**
    * <p>
    * Converts a short into an array of Char using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the short to convert
    * @param srcPos  the position in {@code src}, in bits, from where to start the conversion
    * @param dstInit the initial value for the result String
    * @param dstPos  the position in {@code dst} where to copy the result
    * @param nHexs   the number of Chars to copy to {@code dst}, must be smaller or equal to the
    *                width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.IllegalArgumentException        if {@code (nHexs-1)*4+srcPos >= 16}
    * @throws java.lang.StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}
    */
  def shortToHex(src: Short, srcPos: Int, dstInit: String, dstPos: Int, nHexs: Int): String = {
    if (0 == nHexs) return dstInit
    if ((nHexs - 1) * 4 + srcPos >= 16)
      throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 16")
    val sb = new StringBuilder(dstInit)
    var append = sb.length
    for (i <- 0 until nHexs) {
      val shift = i * 4 + srcPos
      val bits = 0xf & (src >> shift)
      if (dstPos + i == append) {
        append += 1
        sb.append(intToHexDigit(bits))
      } else sb.setCharAt(dstPos + i, intToHexDigit(bits))
    }
    sb.toString
  }

  /**
    * <p>
    * Converts a byte into an array of Char using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src     the byte to convert
    * @param srcPos  the position in {@code src}, in bits, from where to start the conversion
    * @param dstInit the initial value for the result String
    * @param dstPos  the position in {@code dst} where to copy the result
    * @param nHexs   the number of Chars to copy to {@code dst}, must be smaller or equal to the
    *                width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.IllegalArgumentException        if {@code (nHexs-1)*4+srcPos >= 8}
    * @throws java.lang.StringIndexOutOfBoundsException if {@code dst.init.length() < dstPos}
    */
  def byteToHex(src: Byte, srcPos: Int, dstInit: String, dstPos: Int, nHexs: Int): String = {
    if (0 == nHexs) return dstInit
    if ((nHexs - 1) * 4 + srcPos >= 8)
      throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greater or equal to than 8")
    val sb = new StringBuilder(dstInit)
    var append = sb.length
    for (i <- 0 until nHexs) {
      val shift = i * 4 + srcPos
      val bits = 0xf & (src >> shift)
      if (dstPos + i == append) {
        append += 1
        sb.append(intToHexDigit(bits))
      } else sb.setCharAt(dstPos + i, intToHexDigit(bits))
    }
    sb.toString
  }

  /**
    * <p>
    * Converts a long into an array of boolean using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src    the long to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to
    *               the width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+srcPos >= 64}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}
    */
  def longToBinary(src: Long, srcPos: Int, dst: Array[Boolean], dstPos: Int, nBools: Int): Array[Boolean] = {
    if (0 == nBools) return dst
    if (nBools - 1 + srcPos >= 64) throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 64")
    for (i <- 0 until nBools) {
      val shift = i + srcPos
      dst(dstPos + i) = (0x1 & (src >> shift)) != 0
    }
    dst
  }

  /**
    * <p>
    * Converts an int into an array of boolean using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src    the int to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to
    *               the width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+srcPos >= 32}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}
    */
  def intToBinary(src: Int, srcPos: Int, dst: Array[Boolean], dstPos: Int, nBools: Int): Array[Boolean] = {
    if (0 == nBools) return dst
    if (nBools - 1 + srcPos >= 32) throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 32")
    for (i <- 0 until nBools) {
      val shift = i + srcPos
      dst(dstPos + i) = (0x1 & (src >> shift)) != 0
    }
    dst
  }

  /**
    * <p>
    * Converts a short into an array of boolean using the default (little endian, Lsb0) byte
    * and bit ordering.
    * </p>
    *
    * @param src    the short to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to
    *               the width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+srcPos >= 16}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}
    */
  def shortToBinary(src: Short, srcPos: Int, dst: Array[Boolean], dstPos: Int, nBools: Int): Array[Boolean] = {
    if (0 == nBools) return dst
    if (nBools - 1 + srcPos >= 16) throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 16")
    assert((nBools - 1) < 16 - srcPos)
    for (i <- 0 until nBools) {
      val shift = i + srcPos
      dst(dstPos + i) = (0x1 & (src >> shift)) != 0
    }
    dst
  }

  /**
    * <p>
    * Converts a byte into an array of boolean using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src    the byte to convert
    * @param srcPos the position in {@code src}, in bits, from where to start the conversion
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBools the number of booleans to copy to {@code dst}, must be smaller or equal to
    *               the width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBools-1+srcPos >= 8}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBools > dst.length}
    */
  def byteToBinary(src: Byte, srcPos: Int, dst: Array[Boolean], dstPos: Int, nBools: Int): Array[Boolean] = {
    if (0 == nBools) return dst
    if (nBools - 1 + srcPos >= 8) throw new IllegalArgumentException("nBools-1+srcPos is greater or equal to than 8")
    for (i <- 0 until nBools) {
      val shift = i + srcPos
      dst(dstPos + i) = (0x1 & (src >> shift)) != 0
    }
    dst
  }

  /**
    * <p>
    * Converts UUID into an array of byte using the default (little endian, Lsb0) byte and bit
    * ordering.
    * </p>
    *
    * @param src    the UUID to convert
    * @param dst    the destination array
    * @param dstPos the position in {@code dst} where to copy the result
    * @param nBytes the number of bytes to copy to {@code dst}, must be smaller or equal to the
    *               width of the input (from srcPos to msb)
    * @return {@code dst}
    * @throws java.lang.NullPointerException           if {@code dst} is {@code null}
    * @throws java.lang.IllegalArgumentException       if {@code nBytes > 16}
    * @throws java.lang.ArrayIndexOutOfBoundsException if {@code dstPos + nBytes > dst.length}
    */
  def uuidToByteArray(src: UUID, dst: Array[Byte], dstPos: Int, nBytes: Int): Array[Byte] = {
    if (0 == nBytes) return dst
    if (nBytes > 16) throw new IllegalArgumentException("nBytes is greater than 16")
    longToByteArray(src.getMostSignificantBits, 0, dst, dstPos, Math.min(nBytes, 8))
    if (nBytes >= 8) longToByteArray(src.getLeastSignificantBits, 0, dst, dstPos + 8, nBytes - 8)
    dst
  }

  /**
    * <p>
    * Converts bytes from an array into a UUID using the default (little endian, Lsb0) byte and
    * bit ordering.
    * </p>
    *
    * @param src    the byte array to convert
    * @param srcPos the position in {@code src} where to copy the result from
    * @return a UUID
    * @throws java.lang.NullPointerException     if {@code src} is {@code null}
    * @throws java.lang.IllegalArgumentException if array does not contain at least 16 bytes beginning
    *                                  with {@code srcPos}
    */
  def byteArrayToUuid(src: Array[Byte], srcPos: Int): UUID = {
    if (src.length - srcPos < 16) throw new IllegalArgumentException("Need at least 16 bytes for UUID")
    new UUID(byteArrayToLong(src, srcPos, 0, 0, 8), byteArrayToLong(src, srcPos + 8, 0, 0, 8))
  }
}
