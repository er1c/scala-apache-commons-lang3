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
import org.junit.Assert.{assertArrayEquals, assertEquals}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link Conversion}.
  */
object ConversionTest {
  private[lang3] def dbgPrint(src: Array[Boolean]): Unit = {
    val sb = new StringBuilder
    for (e <- src) {
      if (e) sb.append("1, ")
      else sb.append("0, ")
    }
    val out = sb.toString
    out.substring(0, out.length - 1)
    ()
  }
}

class ConversionTest extends JUnitSuite {
  /**
    * Tests {@link Conversion#hexDigitToInt}.
    */
  @Test def testHexDigitToInt(): Unit = {
    assertEquals(0, Conversion.hexDigitToInt('0'))
    assertEquals(1, Conversion.hexDigitToInt('1'))
    assertEquals(2, Conversion.hexDigitToInt('2'))
    assertEquals(3, Conversion.hexDigitToInt('3'))
    assertEquals(4, Conversion.hexDigitToInt('4'))
    assertEquals(5, Conversion.hexDigitToInt('5'))
    assertEquals(6, Conversion.hexDigitToInt('6'))
    assertEquals(7, Conversion.hexDigitToInt('7'))
    assertEquals(8, Conversion.hexDigitToInt('8'))
    assertEquals(9, Conversion.hexDigitToInt('9'))
    assertEquals(10, Conversion.hexDigitToInt('A'))
    assertEquals(10, Conversion.hexDigitToInt('a'))
    assertEquals(11, Conversion.hexDigitToInt('B'))
    assertEquals(11, Conversion.hexDigitToInt('b'))
    assertEquals(12, Conversion.hexDigitToInt('C'))
    assertEquals(12, Conversion.hexDigitToInt('c'))
    assertEquals(13, Conversion.hexDigitToInt('D'))
    assertEquals(13, Conversion.hexDigitToInt('d'))
    assertEquals(14, Conversion.hexDigitToInt('E'))
    assertEquals(14, Conversion.hexDigitToInt('e'))
    assertEquals(15, Conversion.hexDigitToInt('F'))
    assertEquals(15, Conversion.hexDigitToInt('f'))
    assertThrows[IllegalArgumentException](Conversion.hexDigitToInt('G'))
    ()
  }

  /**
    * Tests {@link Conversion# hexDigitMsb0ToInt ( char )}.
    */
  @Test def testHexDigitMsb0ToInt(): Unit = {
    assertEquals(0x0, Conversion.hexDigitMsb0ToInt('0'))
    assertEquals(0x8, Conversion.hexDigitMsb0ToInt('1'))
    assertEquals(0x4, Conversion.hexDigitMsb0ToInt('2'))
    assertEquals(0xc, Conversion.hexDigitMsb0ToInt('3'))
    assertEquals(0x2, Conversion.hexDigitMsb0ToInt('4'))
    assertEquals(0xa, Conversion.hexDigitMsb0ToInt('5'))
    assertEquals(0x6, Conversion.hexDigitMsb0ToInt('6'))
    assertEquals(0xe, Conversion.hexDigitMsb0ToInt('7'))
    assertEquals(0x1, Conversion.hexDigitMsb0ToInt('8'))
    assertEquals(0x9, Conversion.hexDigitMsb0ToInt('9'))
    assertEquals(0x5, Conversion.hexDigitMsb0ToInt('A'))
    assertEquals(0x5, Conversion.hexDigitMsb0ToInt('a'))
    assertEquals(0xd, Conversion.hexDigitMsb0ToInt('B'))
    assertEquals(0xd, Conversion.hexDigitMsb0ToInt('b'))
    assertEquals(0x3, Conversion.hexDigitMsb0ToInt('C'))
    assertEquals(0x3, Conversion.hexDigitMsb0ToInt('c'))
    assertEquals(0xb, Conversion.hexDigitMsb0ToInt('D'))
    assertEquals(0xb, Conversion.hexDigitMsb0ToInt('d'))
    assertEquals(0x7, Conversion.hexDigitMsb0ToInt('E'))
    assertEquals(0x7, Conversion.hexDigitMsb0ToInt('e'))
    assertEquals(0xf, Conversion.hexDigitMsb0ToInt('F'))
    assertEquals(0xf, Conversion.hexDigitMsb0ToInt('f'))
    assertThrows[IllegalArgumentException](Conversion.hexDigitMsb0ToInt('G'))
    ()
  }

  /**
    * Tests {@link Conversion# hexDigitToBinary ( char )}.
    */
  @Test def testHexDigitToBinary(): Unit = {
    assertArrayEquals(Array[Boolean](false, false, false, false), Conversion.hexDigitToBinary('0'))
    assertArrayEquals(Array[Boolean](true, false, false, false), Conversion.hexDigitToBinary('1'))
    assertArrayEquals(Array[Boolean](false, true, false, false), Conversion.hexDigitToBinary('2'))
    assertArrayEquals(Array[Boolean](true, true, false, false), Conversion.hexDigitToBinary('3'))
    assertArrayEquals(Array[Boolean](false, false, true, false), Conversion.hexDigitToBinary('4'))
    assertArrayEquals(Array[Boolean](true, false, true, false), Conversion.hexDigitToBinary('5'))
    assertArrayEquals(Array[Boolean](false, true, true, false), Conversion.hexDigitToBinary('6'))
    assertArrayEquals(Array[Boolean](true, true, true, false), Conversion.hexDigitToBinary('7'))
    assertArrayEquals(Array[Boolean](false, false, false, true), Conversion.hexDigitToBinary('8'))
    assertArrayEquals(Array[Boolean](true, false, false, true), Conversion.hexDigitToBinary('9'))
    assertArrayEquals(Array[Boolean](false, true, false, true), Conversion.hexDigitToBinary('A'))
    assertArrayEquals(Array[Boolean](false, true, false, true), Conversion.hexDigitToBinary('a'))
    assertArrayEquals(Array[Boolean](true, true, false, true), Conversion.hexDigitToBinary('B'))
    assertArrayEquals(Array[Boolean](true, true, false, true), Conversion.hexDigitToBinary('b'))
    assertArrayEquals(Array[Boolean](false, false, true, true), Conversion.hexDigitToBinary('C'))
    assertArrayEquals(Array[Boolean](false, false, true, true), Conversion.hexDigitToBinary('c'))
    assertArrayEquals(Array[Boolean](true, false, true, true), Conversion.hexDigitToBinary('D'))
    assertArrayEquals(Array[Boolean](true, false, true, true), Conversion.hexDigitToBinary('d'))
    assertArrayEquals(Array[Boolean](false, true, true, true), Conversion.hexDigitToBinary('E'))
    assertArrayEquals(Array[Boolean](false, true, true, true), Conversion.hexDigitToBinary('e'))
    assertArrayEquals(Array[Boolean](true, true, true, true), Conversion.hexDigitToBinary('F'))
    assertArrayEquals(Array[Boolean](true, true, true, true), Conversion.hexDigitToBinary('f'))
    assertThrows[IllegalArgumentException](Conversion.hexDigitToBinary('G'))
    ()
  }

  /**
    * Tests {@link Conversion# hexDigitMsb0ToBinary ( char )}.
    */
  @Test def testHexDigitMsb0ToBinary(): Unit = {
    assertArrayEquals(Array[Boolean](false, false, false, false), Conversion.hexDigitMsb0ToBinary('0'))
    assertArrayEquals(Array[Boolean](false, false, false, true), Conversion.hexDigitMsb0ToBinary('1'))
    assertArrayEquals(Array[Boolean](false, false, true, false), Conversion.hexDigitMsb0ToBinary('2'))
    assertArrayEquals(Array[Boolean](false, false, true, true), Conversion.hexDigitMsb0ToBinary('3'))
    assertArrayEquals(Array[Boolean](false, true, false, false), Conversion.hexDigitMsb0ToBinary('4'))
    assertArrayEquals(Array[Boolean](false, true, false, true), Conversion.hexDigitMsb0ToBinary('5'))
    assertArrayEquals(Array[Boolean](false, true, true, false), Conversion.hexDigitMsb0ToBinary('6'))
    assertArrayEquals(Array[Boolean](false, true, true, true), Conversion.hexDigitMsb0ToBinary('7'))
    assertArrayEquals(Array[Boolean](true, false, false, false), Conversion.hexDigitMsb0ToBinary('8'))
    assertArrayEquals(Array[Boolean](true, false, false, true), Conversion.hexDigitMsb0ToBinary('9'))
    assertArrayEquals(Array[Boolean](true, false, true, false), Conversion.hexDigitMsb0ToBinary('A'))
    assertArrayEquals(Array[Boolean](true, false, true, false), Conversion.hexDigitMsb0ToBinary('a'))
    assertArrayEquals(Array[Boolean](true, false, true, true), Conversion.hexDigitMsb0ToBinary('B'))
    assertArrayEquals(Array[Boolean](true, false, true, true), Conversion.hexDigitMsb0ToBinary('b'))
    assertArrayEquals(Array[Boolean](true, true, false, false), Conversion.hexDigitMsb0ToBinary('C'))
    assertArrayEquals(Array[Boolean](true, true, false, false), Conversion.hexDigitMsb0ToBinary('c'))
    assertArrayEquals(Array[Boolean](true, true, false, true), Conversion.hexDigitMsb0ToBinary('D'))
    assertArrayEquals(Array[Boolean](true, true, false, true), Conversion.hexDigitMsb0ToBinary('d'))
    assertArrayEquals(Array[Boolean](true, true, true, false), Conversion.hexDigitMsb0ToBinary('E'))
    assertArrayEquals(Array[Boolean](true, true, true, false), Conversion.hexDigitMsb0ToBinary('e'))
    assertArrayEquals(Array[Boolean](true, true, true, true), Conversion.hexDigitMsb0ToBinary('F'))
    assertArrayEquals(Array[Boolean](true, true, true, true), Conversion.hexDigitMsb0ToBinary('f'))
    assertThrows[IllegalArgumentException](Conversion.hexDigitMsb0ToBinary('G'))
    ()
  }

  /**
    * Tests {@link Conversion# binaryToHexDigit ( boolean[ ] )}.
    */
  @Test def testBinaryToHexDigit(): Unit = {
    assertEquals('0', Conversion.binaryToHexDigit(Array[Boolean](false, false, false, false)))
    assertEquals('1', Conversion.binaryToHexDigit(Array[Boolean](true, false, false, false)))
    assertEquals('2', Conversion.binaryToHexDigit(Array[Boolean](false, true, false, false)))
    assertEquals('3', Conversion.binaryToHexDigit(Array[Boolean](true, true, false, false)))
    assertEquals('4', Conversion.binaryToHexDigit(Array[Boolean](false, false, true, false)))
    assertEquals('5', Conversion.binaryToHexDigit(Array[Boolean](true, false, true, false)))
    assertEquals('6', Conversion.binaryToHexDigit(Array[Boolean](false, true, true, false)))
    assertEquals('7', Conversion.binaryToHexDigit(Array[Boolean](true, true, true, false)))
    assertEquals('8', Conversion.binaryToHexDigit(Array[Boolean](false, false, false, true)))
    assertEquals('9', Conversion.binaryToHexDigit(Array[Boolean](true, false, false, true)))
    assertEquals('a', Conversion.binaryToHexDigit(Array[Boolean](false, true, false, true)))
    assertEquals('b', Conversion.binaryToHexDigit(Array[Boolean](true, true, false, true)))
    assertEquals('c', Conversion.binaryToHexDigit(Array[Boolean](false, false, true, true)))
    assertEquals('d', Conversion.binaryToHexDigit(Array[Boolean](true, false, true, true)))
    assertEquals('e', Conversion.binaryToHexDigit(Array[Boolean](false, true, true, true)))
    assertEquals('f', Conversion.binaryToHexDigit(Array[Boolean](true, true, true, true)))
    assertEquals('1', Conversion.binaryToHexDigit(Array[Boolean](true)))
    assertEquals('f', Conversion.binaryToHexDigit(Array[Boolean](true, true, true, true, true)))
    assertThrows[IllegalArgumentException](Conversion.binaryToHexDigit(Array[Boolean]()))
    ()
  }

  /**
    * Tests {@link Conversion# binaryBeMsb0ToHexDigit ( boolean[ ], int)}.
    */
  @Test def testBinaryToHexDigit_2args(): Unit = {
    val shortArray = Array[Boolean](false, true, true)
    assertEquals('6', Conversion.binaryToHexDigit(shortArray, 0))
    assertEquals('3', Conversion.binaryToHexDigit(shortArray, 1))
    assertEquals('1', Conversion.binaryToHexDigit(shortArray, 2))
    val longArray = Array[Boolean](true, false, true, false, false, true, true)
    assertEquals('5', Conversion.binaryToHexDigit(longArray, 0))
    assertEquals('2', Conversion.binaryToHexDigit(longArray, 1))
    assertEquals('9', Conversion.binaryToHexDigit(longArray, 2))
    assertEquals('c', Conversion.binaryToHexDigit(longArray, 3))
    assertEquals('6', Conversion.binaryToHexDigit(longArray, 4))
    assertEquals('3', Conversion.binaryToHexDigit(longArray, 5))
    assertEquals('1', Conversion.binaryToHexDigit(longArray, 6))
  }

  /**
    * Tests {@link Conversion# binaryToHexDigitMsb0_4bits ( boolean[ ] )}.
    */
  @Test def testBinaryToHexDigitMsb0_bits(): Unit = {
    assertEquals('0', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, false, false, false)))
    assertEquals('1', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, false, false, true)))
    assertEquals('2', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, false, true, false)))
    assertEquals('3', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, false, true, true)))
    assertEquals('4', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, true, false, false)))
    assertEquals('5', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, true, false, true)))
    assertEquals('6', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, true, true, false)))
    assertEquals('7', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](false, true, true, true)))
    assertEquals('8', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, false, false, false)))
    assertEquals('9', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, false, false, true)))
    assertEquals('a', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, false, true, false)))
    assertEquals('b', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, false, true, true)))
    assertEquals('c', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, true, false, false)))
    assertEquals('d', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, true, false, true)))
    assertEquals('e', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, true, true, false)))
    assertEquals('f', Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean](true, true, true, true)))
    assertThrows[IllegalArgumentException](Conversion.binaryToHexDigitMsb0_4bits(Array[Boolean]()))
    ()
  }

  /**
    * Tests {@link Conversion# binaryToHexDigitMsb0_4bits ( boolean[ ], int)}.
    */
  @Test def testBinaryToHexDigitMsb0_4bits_2args(): Unit = { // boolean[] shortArray = new boolean[]{true, true, false};
    // assertEquals('6', Conversion.BinaryToHexDigitMsb0(shortArray, 0));
    // assertEquals('3', Conversion.BinaryToHexDigitMsb0(shortArray, 1));
    // assertEquals('1', Conversion.BinaryToHexDigitMsb0(shortArray, 2));
    val shortArray = Array[Boolean](true, true, false, true)
    assertEquals('d', Conversion.binaryToHexDigitMsb0_4bits(shortArray, 0))
    val longArray = Array[Boolean](true, false, true, false, false, true, true)
    assertEquals('a', Conversion.binaryToHexDigitMsb0_4bits(longArray, 0))
    assertEquals('4', Conversion.binaryToHexDigitMsb0_4bits(longArray, 1))
    assertEquals('9', Conversion.binaryToHexDigitMsb0_4bits(longArray, 2))
    assertEquals('3', Conversion.binaryToHexDigitMsb0_4bits(longArray, 3))
    // assertEquals('6', Conversion.BinaryToHexDigitMsb0(longArray, 4));
    // assertEquals('3', Conversion.BinaryToHexDigitMsb0(longArray, 5));
    // assertEquals('1', Conversion.BinaryToHexDigitMsb0(longArray, 6));
    val maxLengthArray = Array[Boolean](true, false, true, false, false, true, true, true)
    assertEquals('a', Conversion.binaryToHexDigitMsb0_4bits(maxLengthArray, 0))
    assertEquals('4', Conversion.binaryToHexDigitMsb0_4bits(maxLengthArray, 1))
    assertEquals('9', Conversion.binaryToHexDigitMsb0_4bits(maxLengthArray, 2))
    assertEquals('3', Conversion.binaryToHexDigitMsb0_4bits(maxLengthArray, 3))
    assertEquals('7', Conversion.binaryToHexDigitMsb0_4bits(maxLengthArray, 4))
    // assertEquals('7', Conversion.BinaryToHexDigitMsb0(longArray, 5));
    // assertEquals('3', Conversion.BinaryToHexDigitMsb0(longArray, 6));
    // assertEquals('1', Conversion.BinaryToHexDigitMsb0(longArray, 7));
    val javaDocCheck = Array[Boolean](true, false, false, true, true, false, true, false)
    assertEquals('d', Conversion.binaryToHexDigitMsb0_4bits(javaDocCheck, 3))
  }

  @Test def testBinaryBeMsb0ToHexDigit(): Unit = {
    assertEquals('0', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, false, false, false)))
    assertEquals('1', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, false, false, true)))
    assertEquals('2', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, false, true, false)))
    assertEquals('3', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, false, true, true)))
    assertEquals('4', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, true, false, false)))
    assertEquals('5', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, true, false, true)))
    assertEquals('6', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, true, true, false)))
    assertEquals('7', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](false, true, true, true)))
    assertEquals('8', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, false, false, false)))
    assertEquals('9', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, false, false, true)))
    assertEquals('a', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, false, true, false)))
    assertEquals('b', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, false, true, true)))
    assertEquals('c', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, true, false, false)))
    assertEquals('d', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, true, false, true)))
    assertEquals('e', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, true, true, false)))
    assertEquals('f', Conversion.binaryBeMsb0ToHexDigit(Array[Boolean](true, true, true, true)))
    assertEquals(
      '4',
      Conversion.binaryBeMsb0ToHexDigit(
        Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, false, false, true,
          false, false)))
    assertThrows[IllegalArgumentException](Conversion.binaryBeMsb0ToHexDigit(Array[Boolean]()))
    ()
  }

  /**
    * Tests {@link Conversion# binaryToHexDigit ( boolean[ ], int)}.
    */
  @Test def testBinaryBeMsb0ToHexDigit_2args(): Unit = {
    assertEquals(
      '5',
      Conversion.binaryBeMsb0ToHexDigit(
        Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, true, false, true,
          false, false),
        2))
    val shortArray = Array[Boolean](true, true, false)
    assertEquals('6', Conversion.binaryBeMsb0ToHexDigit(shortArray, 0))
    assertEquals('3', Conversion.binaryBeMsb0ToHexDigit(shortArray, 1))
    assertEquals('1', Conversion.binaryBeMsb0ToHexDigit(shortArray, 2))
    val shortArray2 = Array[Boolean](true, true, true, false, false, true, false, true)
    assertEquals('5', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 0))
    assertEquals('2', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 1))
    assertEquals('9', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 2))
    assertEquals('c', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 3))
    assertEquals('e', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 4))
    assertEquals('7', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 5))
    assertEquals('3', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 6))
    assertEquals('1', Conversion.binaryBeMsb0ToHexDigit(shortArray2, 7))
    val multiBytesArray = Array[Boolean](true, true, false, false, true, false, true, false, true, true, true, false,
      false, true, false, true)
    assertEquals('5', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 0))
    assertEquals('2', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 1))
    assertEquals('9', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 2))
    assertEquals('c', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 3))
    assertEquals('e', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 4))
    assertEquals('7', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 5))
    assertEquals('b', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 6))
    assertEquals('5', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 7))
    assertEquals('a', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 8))
    assertEquals('5', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 9))
    assertEquals('2', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 10))
    assertEquals('9', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 11))
    assertEquals('c', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 12))
    assertEquals('6', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 13))
    assertEquals('3', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 14))
    assertEquals('1', Conversion.binaryBeMsb0ToHexDigit(multiBytesArray, 15))
  }

  /**
    * Tests {@link Conversion# intToHexDigit ( int )}.
    */
  @Test def testIntToHexDigit(): Unit = {
    assertEquals('0', Conversion.intToHexDigit(0))
    assertEquals('1', Conversion.intToHexDigit(1))
    assertEquals('2', Conversion.intToHexDigit(2))
    assertEquals('3', Conversion.intToHexDigit(3))
    assertEquals('4', Conversion.intToHexDigit(4))
    assertEquals('5', Conversion.intToHexDigit(5))
    assertEquals('6', Conversion.intToHexDigit(6))
    assertEquals('7', Conversion.intToHexDigit(7))
    assertEquals('8', Conversion.intToHexDigit(8))
    assertEquals('9', Conversion.intToHexDigit(9))
    assertEquals('a', Conversion.intToHexDigit(10))
    assertEquals('b', Conversion.intToHexDigit(11))
    assertEquals('c', Conversion.intToHexDigit(12))
    assertEquals('d', Conversion.intToHexDigit(13))
    assertEquals('e', Conversion.intToHexDigit(14))
    assertEquals('f', Conversion.intToHexDigit(15))
    assertThrows[IllegalArgumentException](Conversion.intToHexDigit(16))
    ()
  }

  /**
    * Tests {@link Conversion# intToHexDigitMsb0 ( int )}.
    */
  @Test def testIntToHexDigitMsb0(): Unit = {
    assertEquals('0', Conversion.intToHexDigitMsb0(0))
    assertEquals('8', Conversion.intToHexDigitMsb0(1))
    assertEquals('4', Conversion.intToHexDigitMsb0(2))
    assertEquals('c', Conversion.intToHexDigitMsb0(3))
    assertEquals('2', Conversion.intToHexDigitMsb0(4))
    assertEquals('a', Conversion.intToHexDigitMsb0(5))
    assertEquals('6', Conversion.intToHexDigitMsb0(6))
    assertEquals('e', Conversion.intToHexDigitMsb0(7))
    assertEquals('1', Conversion.intToHexDigitMsb0(8))
    assertEquals('9', Conversion.intToHexDigitMsb0(9))
    assertEquals('5', Conversion.intToHexDigitMsb0(10))
    assertEquals('d', Conversion.intToHexDigitMsb0(11))
    assertEquals('3', Conversion.intToHexDigitMsb0(12))
    assertEquals('b', Conversion.intToHexDigitMsb0(13))
    assertEquals('7', Conversion.intToHexDigitMsb0(14))
    assertEquals('f', Conversion.intToHexDigitMsb0(15))
    assertThrows[IllegalArgumentException](Conversion.intToHexDigitMsb0(16))
    ()
  }

  /**
    * Tests {@link Conversion# intArrayToLong ( int[ ], int, long, int, int)}.
    */
  @Test def testIntArrayToLong(): Unit = {
    val src = Array[Int](0xcdf1f0c1, 0x0f123456, 0x78000000)
    assertEquals(0x0000000000000000L, Conversion.intArrayToLong(src, 0, 0L, 0, 0))
    assertEquals(0x0000000000000000L, Conversion.intArrayToLong(src, 1, 0L, 0, 0))
    assertEquals(0x00000000cdf1f0c1L, Conversion.intArrayToLong(src, 0, 0L, 0, 1))
    assertEquals(0x0f123456cdf1f0c1L, Conversion.intArrayToLong(src, 0, 0L, 0, 2))
    assertEquals(0x000000000f123456L, Conversion.intArrayToLong(src, 1, 0L, 0, 1))
    assertEquals(0x123456789abcdef0L, Conversion.intArrayToLong(src, 0, 0x123456789abcdef0L, 0, 0))
    assertEquals(0x1234567878000000L, Conversion.intArrayToLong(src, 2, 0x123456789abcdef0L, 0, 1))
    // assertEquals(0x0F12345678000000L, Conversion.intsToLong(src, 1, 0x123456789ABCDEF0L, 32, 2));
  }

  /**
    * Tests {@link Conversion# shortArrayToLong ( short[ ], int, long, int, int)}.
    */
  @Test def testShortArrayToLong(): Unit = {
    val src = Array[Short](0xcdf1.toShort, 0xf0c1.toShort, 0x0f12.toShort, 0x3456.toShort, 0x7800.toShort)
    assertEquals(0x0000000000000000L, Conversion.shortArrayToLong(src, 0, 0L, 0, 0))
    assertEquals(0x000000000000cdf1L, Conversion.shortArrayToLong(src, 0, 0L, 0, 1))
    assertEquals(0x00000000f0c1cdf1L, Conversion.shortArrayToLong(src, 0, 0L, 0, 2))
    assertEquals(0x780034560f12f0c1L, Conversion.shortArrayToLong(src, 1, 0L, 0, 4))
    assertEquals(0x123456789abcdef0L, Conversion.shortArrayToLong(src, 0, 0x123456789abcdef0L, 0, 0))
    assertEquals(0x123456cdf1bcdef0L, Conversion.shortArrayToLong(src, 0, 0x123456789abcdef0L, 24, 1))
    assertEquals(0x123478003456def0L, Conversion.shortArrayToLong(src, 3, 0x123456789abcdef0L, 16, 2))
  }

  /**
    * Tests {@link Conversion# byteArrayToLong ( byte[ ], int, long, int, int)}.
    */
  @Test def testByteArrayToLong(): Unit = {
    val src = Array[Byte](
      0xcd.toByte,
      0xf1.toByte,
      0xf0.toByte,
      0xc1.toByte,
      0x0f.toByte,
      0x12.toByte,
      0x34.toByte,
      0x56.toByte,
      0x78.toByte)
    assertEquals(0x0000000000000000L, Conversion.byteArrayToLong(src, 0, 0L, 0, 0))
    assertEquals(0x00000000000000cdL, Conversion.byteArrayToLong(src, 0, 0L, 0, 1))
    assertEquals(0x00000000c1f0f1cdL, Conversion.byteArrayToLong(src, 0, 0L, 0, 4))
    assertEquals(0x000000000fc1f0f1L, Conversion.byteArrayToLong(src, 1, 0L, 0, 4))
    assertEquals(0x123456789abcdef0L, Conversion.byteArrayToLong(src, 0, 0x123456789abcdef0L, 0, 0))
    assertEquals(0x12345678cdbcdef0L, Conversion.byteArrayToLong(src, 0, 0x123456789abcdef0L, 24, 1))
    assertEquals(0x123456789a7856f0L, Conversion.byteArrayToLong(src, 7, 0x123456789abcdef0L, 8, 2))
  }

  /**
    * Tests {@link Conversion# shortArrayToInt ( short[ ], int, int, int, int)}.
    */
  @Test def testShortArrayToInt(): Unit = {
    val src = Array[Short](0xcdf1.toShort, 0xf0c1.toShort, 0x0f12.toShort, 0x3456.toShort, 0x7800.toShort)
    assertEquals(0x00000000, Conversion.shortArrayToInt(src, 0, 0, 0, 0))
    assertEquals(0x0000cdf1, Conversion.shortArrayToInt(src, 0, 0, 0, 1))
    assertEquals(0xf0c1cdf1, Conversion.shortArrayToInt(src, 0, 0, 0, 2))
    assertEquals(0x0f12f0c1, Conversion.shortArrayToInt(src, 1, 0, 0, 2))
    assertEquals(0x12345678, Conversion.shortArrayToInt(src, 0, 0x12345678, 0, 0))
    assertEquals(0xcdf15678, Conversion.shortArrayToInt(src, 0, 0x12345678, 16, 1))
    // assertEquals(0x34567800, Conversion.ShortArrayToInt(src, 3, 0x12345678, 16, 2));
  }

  /**
    * Tests {@link Conversion# byteArrayToInt ( byte[ ], int, int, int, int)}.
    */
  @Test def testByteArrayToInt(): Unit = {
    val src = Array[Byte](
      0xcd.toByte,
      0xf1.toByte,
      0xf0.toByte,
      0xc1.toByte,
      0x0f.toByte,
      0x12.toByte,
      0x34.toByte,
      0x56.toByte,
      0x78.toByte)
    assertEquals(0x00000000, Conversion.byteArrayToInt(src, 0, 0, 0, 0))
    assertEquals(0x000000cd, Conversion.byteArrayToInt(src, 0, 0, 0, 1))
    assertEquals(0xc1f0f1cd, Conversion.byteArrayToInt(src, 0, 0, 0, 4))
    assertEquals(0x0fc1f0f1, Conversion.byteArrayToInt(src, 1, 0, 0, 4))
    assertEquals(0x12345678, Conversion.byteArrayToInt(src, 0, 0x12345678, 0, 0))
    assertEquals(0xcd345678, Conversion.byteArrayToInt(src, 0, 0x12345678, 24, 1))
    // assertEquals(0x56341278, Conversion.ByteArrayToInt(src, 5, 0x01234567, 8, 4));
  }

  /**
    * Tests {@link Conversion# byteArrayToShort ( byte[ ], int, short, int, int)}.
    */
  @Test def testByteArrayToShort(): Unit = {
    val src = Array[Byte](
      0xcd.toByte,
      0xf1.toByte,
      0xf0.toByte,
      0xc1.toByte,
      0x0f.toByte,
      0x12.toByte,
      0x34.toByte,
      0x56.toByte,
      0x78.toByte)
    assertEquals(0x0000.toShort, Conversion.byteArrayToShort(src, 0, 0.toShort, 0, 0))
    assertEquals(0x00cd.toShort, Conversion.byteArrayToShort(src, 0, 0.toShort, 0, 1))
    assertEquals(0xf1cd.toShort, Conversion.byteArrayToShort(src, 0, 0.toShort, 0, 2))
    assertEquals(0xf0f1.toShort, Conversion.byteArrayToShort(src, 1, 0.toShort, 0, 2))
    assertEquals(0x1234.toShort, Conversion.byteArrayToShort(src, 0, 0x1234.toShort, 0, 0))
    assertEquals(0xcd34.toShort, Conversion.byteArrayToShort(src, 0, 0x1234.toShort, 8, 1))
    // assertEquals((short) 0x5678, Conversion.ByteArrayToShort(src, 7, (short) 0x0123, 8,
    // 2));
  }

  /**
    * Tests {@link Conversion# hexToLong ( String, int, long, int, int)}.
    */
  @Test def testHexToLong(): Unit = {
    val src = "CDF1F0C10F12345678"
    assertEquals(0x0000000000000000L, Conversion.hexToLong(src, 0, 0L, 0, 0))
    assertEquals(0x000000000000000cL, Conversion.hexToLong(src, 0, 0L, 0, 1))
    assertEquals(0x000000001c0f1fdcL, Conversion.hexToLong(src, 0, 0L, 0, 8))
    assertEquals(0x0000000001c0f1fdL, Conversion.hexToLong(src, 1, 0L, 0, 8))
    assertEquals(0x123456798abcdef0L, Conversion.hexToLong(src, 0, 0x123456798abcdef0L, 0, 0))
    assertEquals(0x1234567876bcdef0L, Conversion.hexToLong(src, 15, 0x123456798abcdef0L, 24, 3))
  }

  /**
    * Tests {@link Conversion# hexToInt ( String, int, int, int, int)}.
    */
  @Test def testHexToInt(): Unit = {
    val src = "CDF1F0C10F12345678"
    assertEquals(0x00000000, Conversion.hexToInt(src, 0, 0, 0, 0))
    assertEquals(0x0000000c, Conversion.hexToInt(src, 0, 0, 0, 1))
    assertEquals(0x1c0f1fdc, Conversion.hexToInt(src, 0, 0, 0, 8))
    assertEquals(0x01c0f1fd, Conversion.hexToInt(src, 1, 0, 0, 8))
    assertEquals(0x12345679, Conversion.hexToInt(src, 0, 0x12345679, 0, 0))
    assertEquals(0x87645679, Conversion.hexToInt(src, 15, 0x12345679, 20, 3))
  }

  /**
    * Tests {@link Conversion# hexToShort ( String, int, short, int, int)}.
    */
  @Test def testHexToShort(): Unit = {
    val src = "CDF1F0C10F12345678"
    assertEquals(0x0000.toShort, Conversion.hexToShort(src, 0, 0.toShort, 0, 0))
    assertEquals(0x000c.toShort, Conversion.hexToShort(src, 0, 0.toShort, 0, 1))
    assertEquals(0x1fdc.toShort, Conversion.hexToShort(src, 0, 0.toShort, 0, 4))
    assertEquals(0xf1fd.toShort, Conversion.hexToShort(src, 1, 0.toShort, 0, 4))
    assertEquals(0x1234.toShort, Conversion.hexToShort(src, 0, 0x1234.toShort, 0, 0))
    assertEquals(0x8764.toShort, Conversion.hexToShort(src, 15, 0x1234.toShort, 4, 3))
  }

  /**
    * Tests {@link Conversion# hexToByte ( String, int, byte, int, int)}.
    */
  @Test def testHexToByte(): Unit = {
    val src = "CDF1F0C10F12345678"
    assertEquals(0x00.toByte, Conversion.hexToByte(src, 0, 0.toByte, 0, 0))
    assertEquals(0x0c.toByte, Conversion.hexToByte(src, 0, 0.toByte, 0, 1))
    assertEquals(0xdc.toByte, Conversion.hexToByte(src, 0, 0.toByte, 0, 2))
    assertEquals(0xfd.toByte, Conversion.hexToByte(src, 1, 0.toByte, 0, 2))
    assertEquals(0x34.toByte, Conversion.hexToByte(src, 0, 0x34.toByte, 0, 0))
    assertEquals(0x84.toByte, Conversion.hexToByte(src, 17, 0x34.toByte, 4, 1))
  }

  /**
    * Tests {@link Conversion# binaryToLong ( boolean[ ], int, long, int, int)}.
    */
  @Test def testBinaryToLong(): Unit = {
    val src = Array[Boolean](false, false, true, true, true, false, true, true, true, true, true, true, true, false,
      false, false, true, true, true, true, false, false, false, false, false, false, true, true, true, false, false,
      false, false, false, false, false, true, true, true, true, true, false, false, false, false, true, false, false,
      true, true, false, false, false, false, true, false, true, false, true, false, false, true, true, false, true,
      true, true, false, false, false, false, true)
    // conversion of "CDF1F0C10F12345678" by HexToBinary
    assertEquals(0x0000000000000000L, Conversion.binaryToLong(src, 0, 0L, 0, 0))
    assertEquals(0x000000000000000cL, Conversion.binaryToLong(src, 0, 0L, 0, 1 * 4))
    assertEquals(0x000000001c0f1fdcL, Conversion.binaryToLong(src, 0, 0L, 0, 8 * 4))
    assertEquals(0x0000000001c0f1fdL, Conversion.binaryToLong(src, 1 * 4, 0L, 0, 8 * 4))
    assertEquals(0x123456798abcdef0L, Conversion.binaryToLong(src, 0, 0x123456798abcdef0L, 0, 0))
    assertEquals(0x1234567876bcdef0L, Conversion.binaryToLong(src, 15 * 4, 0x123456798abcdef0L, 24, 3 * 4))
  }

  /**
    * Tests {@link Conversion# binaryToInt ( boolean[ ], int, int, int, int)}.
    */
  @Test def testBinaryToInt(): Unit = {
    val src = Array[Boolean](false, false, true, true, true, false, true, true, true, true, true, true, true, false,
      false, false, true, true, true, true, false, false, false, false, false, false, true, true, true, false, false,
      false, false, false, false, false, true, true, true, true, true, false, false, false, false, true, false, false,
      true, true, false, false, false, false, true, false, true, false, true, false, false, true, true, false, true,
      true, true, false, false, false, false, true)
    assertEquals(0x00000000, Conversion.binaryToInt(src, 0 * 4, 0, 0, 0 * 4))
    assertEquals(0x0000000c, Conversion.binaryToInt(src, 0 * 4, 0, 0, 1 * 4))
    assertEquals(0x1c0f1fdc, Conversion.binaryToInt(src, 0 * 4, 0, 0, 8 * 4))
    assertEquals(0x01c0f1fd, Conversion.binaryToInt(src, 1 * 4, 0, 0, 8 * 4))
    assertEquals(0x12345679, Conversion.binaryToInt(src, 0 * 4, 0x12345679, 0, 0 * 4))
    assertEquals(0x87645679, Conversion.binaryToInt(src, 15 * 4, 0x12345679, 20, 3 * 4))
  }

  /**
    * Tests {@link Conversion# binaryToShort ( boolean[ ], int, short, int, int)}.
    */
  @Test def testBinaryToShort(): Unit = {
    val src = Array[Boolean](false, false, true, true, true, false, true, true, true, true, true, true, true, false,
      false, false, true, true, true, true, false, false, false, false, false, false, true, true, true, false, false,
      false, false, false, false, false, true, true, true, true, true, false, false, false, false, true, false, false,
      true, true, false, false, false, false, true, false, true, false, true, false, false, true, true, false, true,
      true, true, false, false, false, false, true)
    assertEquals(0x0000.toShort, Conversion.binaryToShort(src, 0 * 4, 0.toShort, 0, 0 * 4))
    assertEquals(0x000c.toShort, Conversion.binaryToShort(src, 0 * 4, 0.toShort, 0, 1 * 4))
    assertEquals(0x1fdc.toShort, Conversion.binaryToShort(src, 0 * 4, 0.toShort, 0, 4 * 4))
    assertEquals(0xf1fd.toShort, Conversion.binaryToShort(src, 1 * 4, 0.toShort, 0, 4 * 4))
    assertEquals(0x1234.toShort, Conversion.binaryToShort(src, 0 * 4, 0x1234.toShort, 0, 0 * 4))
    assertEquals(0x8764.toShort, Conversion.binaryToShort(src, 15 * 4, 0x1234.toShort, 4, 3 * 4))
  }

  /**
    * Tests {@link Conversion# binaryToByte ( boolean[ ], int, byte, int, int)}.
    */
  @Test def testBinaryToByte(): Unit = {
    val src = Array[Boolean](false, false, true, true, true, false, true, true, true, true, true, true, true, false,
      false, false, true, true, true, true, false, false, false, false, false, false, true, true, true, false, false,
      false, false, false, false, false, true, true, true, true, true, false, false, false, false, true, false, false,
      true, true, false, false, false, false, true, false, true, false, true, false, false, true, true, false, true,
      true, true, false, false, false, false, true)
    assertEquals(0x00.toByte, Conversion.binaryToByte(src, 0 * 4, 0.toByte, 0, 0 * 4))
    assertEquals(0x0c.toByte, Conversion.binaryToByte(src, 0 * 4, 0.toByte, 0, 1 * 4))
    assertEquals(0xdc.toByte, Conversion.binaryToByte(src, 0 * 4, 0.toByte, 0, 2 * 4))
    assertEquals(0xfd.toByte, Conversion.binaryToByte(src, 1 * 4, 0.toByte, 0, 2 * 4))
    assertEquals(0x34.toByte, Conversion.binaryToByte(src, 0 * 4, 0x34.toByte, 0, 0 * 4))
    assertEquals(0x84.toByte, Conversion.binaryToByte(src, 17 * 4, 0x34.toByte, 4, 1 * 4))
  }

  /**
    * Tests {@link Conversion# longToIntArray ( long, int, int[], int, int)}.
    */
  @Test def testLongToIntArray(): Unit = {
    assertArrayEquals(new Array[Int](0), Conversion.longToIntArray(0x0000000000000000L, 0, new Array[Int](0), 0, 0))
    assertArrayEquals(new Array[Int](0), Conversion.longToIntArray(0x0000000000000000L, 100, new Array[Int](0), 0, 0))
    assertArrayEquals(new Array[Int](0), Conversion.longToIntArray(0x0000000000000000L, 0, new Array[Int](0), 100, 0))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 0, Array[Int](-1, -1, -1, -1), 0, 0))
    assertArrayEquals(
      Array[Int](0x90abcdef, 0xffffffff, 0xffffffff, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 0, Array[Int](-1, -1, -1, -1), 0, 1))
    assertArrayEquals(
      Array[Int](0x90abcdef, 0x12345678, 0xffffffff, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 0, Array[Int](-1, -1, -1, -1), 0, 2))
    // assertArrayEquals(new
    // int[]{0x90ABCDEF, 0x12345678, 0x90ABCDEF, 0x12345678}, Conversion.longToIntArray(0x1234567890ABCDEFL,
    // 0, new int[]{-1, -1, -1, -1}, 0, 4));//rejected by assertion
    // int[]{0xFFFFFFFF, 0x90ABCDEF, 0x12345678, 0x90ABCDEF}, Conversion.longToIntArray(0x1234567890ABCDEFL,
    // 0, new int[]{-1, -1, -1, -1}, 1, 3));
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0x90abcdef, 0x12345678),
      Conversion.longToIntArray(0x1234567890abcdefL, 0, Array[Int](-1, -1, -1, -1), 2, 2))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0x90abcdef, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 0, Array[Int](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0xffffffff, 0x90abcdef),
      Conversion.longToIntArray(0x1234567890abcdefL, 0, Array[Int](-1, -1, -1, -1), 3, 1))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0x4855e6f7, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 1, Array[Int](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0x242af37b, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 2, Array[Int](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0x121579bd, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 3, Array[Int](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Int](0xffffffff, 0xffffffff, 0x890abcde, 0xffffffff),
      Conversion.longToIntArray(0x1234567890abcdefL, 4, Array[Int](-1, -1, -1, -1), 2, 1))
    // int[]{0x4855E6F7, 0x091A2B3C, 0x4855E6F7, 0x091A2B3C}, Conversion.longToIntArray(0x1234567890ABCDEFL,
    // 1, new int[]{-1, -1, -1, -1}, 0, 4));//rejected by assertion
    assertArrayEquals(Array[Int](0x091a2b3c), Conversion.longToIntArray(0x1234567890abcdefL, 33, Array[Int](0), 0, 1))
  }

  /**
    * Tests {@link Conversion# longToShortArray ( long, int, short[], int, int)}.
    */
  @Test def testLongToShortArray(): Unit = {
    assertArrayEquals(
      new Array[Short](0),
      Conversion.longToShortArray(0x0000000000000000L, 0, new Array[Short](0), 0, 0))
    assertArrayEquals(
      new Array[Short](0),
      Conversion.longToShortArray(0x0000000000000000L, 100, new Array[Short](0), 0, 0))
    assertArrayEquals(
      new Array[Short](0),
      Conversion.longToShortArray(0x0000000000000000L, 0, new Array[Short](0), 100, 0))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xffff.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 0, 0)
    )
    assertArrayEquals(
      Array[Short](0xcdef.toShort, 0xffff.toShort, 0xffff.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Short](0xcdef.toShort, 0x90ab.toShort, 0xffff.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 0, 2)
    )
    assertArrayEquals(
      Array[Short](0xcdef.toShort, 0x90ab.toShort, 0x5678.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 0, 3)
    )
    assertArrayEquals(
      Array[Short](0xcdef.toShort, 0x90ab.toShort, 0x5678.toShort, 0x1234.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 0, 4)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xcdef.toShort, 0x90ab.toShort, 0x5678.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 1, 3)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xcdef.toShort, 0x90ab.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 2, 2)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xcdef.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 2, 1)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xffff.toShort, 0xcdef.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 0, Array[Short](-1, -1, -1, -1), 3, 1)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xe6f7.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 1, Array[Short](-1, -1, -1, -1), 2, 1)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xf37b.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 2, Array[Short](-1, -1, -1, -1), 2, 1)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x79bd.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 3, Array[Short](-1, -1, -1, -1), 2, 1)
    )
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xbcde.toShort, 0xffff.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 4, Array[Short](-1, -1, -1, -1), 2, 1)
    )
    assertArrayEquals(
      Array[Short](0xe6f7.toShort, 0x4855.toShort, 0x2b3c.toShort, 0x091a.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 1, Array[Short](-1, -1, -1, -1), 0, 4)
    )
    assertArrayEquals(
      Array[Short](0x2b3c.toShort),
      Conversion.longToShortArray(0x1234567890abcdefL, 33, Array[Short](0), 0, 1))
  }

  /**
    * Tests {@link Conversion# intToShortArray ( int, int, short[], int, int)}.
    */
  @Test def testIntToShortArray(): Unit = {
    assertArrayEquals(new Array[Short](0), Conversion.intToShortArray(0x00000000, 0, new Array[Short](0), 0, 0))
    assertArrayEquals(new Array[Short](0), Conversion.intToShortArray(0x00000000, 100, new Array[Short](0), 0, 0))
    assertArrayEquals(new Array[Short](0), Conversion.intToShortArray(0x00000000, 0, new Array[Short](0), 100, 0))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xffff.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 0, Array[Short](-1, -1, -1, -1), 0, 0))
    assertArrayEquals(
      Array[Short](0x5678.toShort, 0xffff.toShort, 0xffff.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 0, Array[Short](-1, -1, -1, -1), 0, 1))
    assertArrayEquals(
      Array[Short](0x5678.toShort, 0x1234.toShort, 0xffff.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 0, Array[Short](-1, -1, -1, -1), 0, 2))
    // short[]{(short) 0x5678, (short) 0x1234, (short) 0x5678, (short) 0xFFFF}, Conversion.intToShortArray(0x12345678,
    // 0, new short[]{-1, -1, -1, -1}, 0, 3));//rejected by assertion
    // short[]{(short) 0x5678, (short) 0x1234, (short) 0x5678, (short) 0x1234}, Conversion.intToShortArray(0x12345678,
    // 0, new short[]{-1, -1, -1, -1}, 0, 4));
    // short[]{(short) 0xFFFF, (short) 0x5678, (short) 0x1234, (short) 0x5678}, Conversion.intToShortArray(0x12345678,
    // 0, new short[]{-1, -1, -1, -1}, 1, 3));
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x5678.toShort, 0x1234.toShort),
      Conversion.intToShortArray(0x12345678, 0, Array[Short](-1, -1, -1, -1), 2, 2))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x5678.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 0, Array[Short](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0xffff.toShort, 0x5678.toShort),
      Conversion.intToShortArray(0x12345678, 0, Array[Short](-1, -1, -1, -1), 3, 1))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x2b3c.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 1, Array[Short](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x159e.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 2, Array[Short](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x8acf.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 3, Array[Short](-1, -1, -1, -1), 2, 1))
    assertArrayEquals(
      Array[Short](0xffff.toShort, 0xffff.toShort, 0x4567.toShort, 0xffff.toShort),
      Conversion.intToShortArray(0x12345678, 4, Array[Short](-1, -1, -1, -1), 2, 1))
    // short[]{(short) 0xE6F7, (short) 0x4855, (short) 0x2B3C, (short) 0x091A}, Conversion.intToShortArray(0x12345678,
    // 1, new short[]{-1, -1, -1, -1}, 0, 4));//rejected by assertion
    // short[]{(short) 0x2B3C}, Conversion.intToShortArray(0x12345678, 33, new
    // short[]{0}, 0, 1));//rejected by assertion
    assertArrayEquals(Array[Short](0x091a.toShort), Conversion.intToShortArray(0x12345678, 17, Array[Short](0), 0, 1))
  }

  /**
    * Tests {@link Conversion# longToByteArray ( long, int, byte[], int, int)}.
    */
  @Test def testLongToByteArray(): Unit = {
    assertArrayEquals(new Array[Byte](0), Conversion.longToByteArray(0x0000000000000000L, 0, new Array[Byte](0), 0, 0))
    assertArrayEquals(
      new Array[Byte](0),
      Conversion.longToByteArray(0x0000000000000000L, 100, new Array[Byte](0), 0, 0))
    assertArrayEquals(
      new Array[Byte](0),
      Conversion.longToByteArray(0x0000000000000000L, 0, new Array[Byte](0), 100, 0))
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 0)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xcd.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 2)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 4)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0x78.toByte,
        0x56.toByte,
        0x34.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 7)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0x78.toByte,
        0x56.toByte,
        0x34.toByte,
        0x12.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 8)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xcd.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 2)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 4)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0x78.toByte,
        0x56.toByte,
        0x34.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 7)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0x78.toByte,
        0x56.toByte,
        0x34.toByte,
        0x12.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 8)
    )
    assertArrayEquals(
      Array[Byte](
        0xf7.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 1, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0x7b.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 2, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0x00.toByte,
        0xff.toByte,
        0x6f.toByte,
        0x5e.toByte,
        0x85.toByte,
        0xc4.toByte,
        0xb3.toByte,
        0xa2.toByte,
        0x91.toByte,
        0x00.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 5, Array[Byte](-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 8)
    )
    // byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x5E, (byte) 0x85, (byte) 0xC4, (byte) 0xB3, (byte) 0xA2, (byte) 0x91, (byte) 0x00, (byte) 0x00}, Conversion.longToByteArray(0x1234567890ABCDEFL, 13, new
    // byte[]{-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 3, 8));//rejected by assertion
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0x00.toByte,
        0xff.toByte,
        0x5e.toByte,
        0x85.toByte,
        0xc4.toByte,
        0xb3.toByte,
        0xa2.toByte,
        0x91.toByte,
        0x00.toByte,
        0xff.toByte),
      Conversion.longToByteArray(0x1234567890abcdefL, 13, Array[Byte](-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 7)
    )
  }

  /**
    * Tests {@link Conversion# intToByteArray ( int, int, byte[], int, int)}.
    */
  @Test def testIntToByteArray(): Unit = {
    assertArrayEquals(new Array[Byte](0), Conversion.intToByteArray(0x00000000, 0, new Array[Byte](0), 0, 0))
    assertArrayEquals(new Array[Byte](0), Conversion.intToByteArray(0x00000000, 100, new Array[Byte](0), 0, 0))
    assertArrayEquals(new Array[Byte](0), Conversion.intToByteArray(0x00000000, 0, new Array[Byte](0), 100, 0))
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 0)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xcd.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 2)
    )
    assertArrayEquals(
      Array[Byte](
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 4)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xcd.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 2)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xef.toByte,
        0xcd.toByte,
        0xab.toByte,
        0x90.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 4)
    )
    assertArrayEquals(
      Array[Byte](
        0xf7.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 1, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0x7b.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 2, Array[Byte](-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0x00.toByte,
        0xff.toByte,
        0x6f.toByte,
        0x5e.toByte,
        0x85.toByte,
        0xfc.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 5, Array[Byte](-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 4)
    )
    // byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x5E, (byte) 0x85, (byte) 0xFC, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, Conversion.intToByteArray(0x90ABCDEF, 13, new
    // byte[]{-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1}, 3, 4));//rejected by assertion
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0x00.toByte,
        0xff.toByte,
        0x5e.toByte,
        0x85.toByte,
        0xfc.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte),
      Conversion.intToByteArray(0x90abcdef, 13, Array[Byte](-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1), 3, 3)
    )
  }

  /**
    * Tests {@link Conversion# shortToByteArray ( short, int, byte[], int, int)}.
    */
  @Test def testShortToByteArray(): Unit = {
    assertArrayEquals(new Array[Byte](0), Conversion.shortToByteArray(0x0000.toShort, 0, new Array[Byte](0), 0, 0))
    assertArrayEquals(new Array[Byte](0), Conversion.shortToByteArray(0x0000.toShort, 100, new Array[Byte](0), 0, 0))
    assertArrayEquals(new Array[Byte](0), Conversion.shortToByteArray(0x0000.toShort, 0, new Array[Byte](0), 100, 0))
    assertArrayEquals(
      Array[Byte](0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 0, 0)
    )
    assertArrayEquals(
      Array[Byte](0xef.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](0xef.toByte, 0xcd.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 0, 2)
    )
    assertArrayEquals(
      Array[Byte](0xff.toByte, 0xff.toByte, 0xff.toByte, 0xef.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 3, 1)
    )
    assertArrayEquals(
      Array[Byte](0xff.toByte, 0xff.toByte, 0xff.toByte, 0xef.toByte, 0xcd.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 0, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 3, 2)
    )
    assertArrayEquals(
      Array[Byte](0xf7.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 1, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](0x7b.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 2, Array[Byte](-1, -1, -1, -1, -1, -1, -1), 0, 1)
    )
    assertArrayEquals(
      Array[Byte](0xff.toByte, 0x00.toByte, 0xff.toByte, 0x6f.toByte, 0xfe.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 5, Array[Byte](-1, 0, -1, -1, -1, -1, -1), 3, 2)
    )
    // byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x5E, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, Conversion.shortToByteArray((short) 0xCDEF, 13, new
    // byte[]{-1, 0, -1, -1, -1, -1, -1}, 3, 2));//rejected by assertion
    assertArrayEquals(
      Array[Byte](0xff.toByte, 0x00.toByte, 0xff.toByte, 0xfe.toByte, 0xff.toByte, 0xff.toByte, 0xff.toByte),
      Conversion.shortToByteArray(0xcdef.toShort, 13, Array[Byte](-1, 0, -1, -1, -1, -1, -1), 3, 1)
    )
  }

  /**
    * Tests {@link Conversion# longToHex ( long, int, String, int, int)}.
    */
  @Test def testLongToHex(): Unit = {
    assertEquals("", Conversion.longToHex(0x0000000000000000L, 0, "", 0, 0))
    assertEquals("", Conversion.longToHex(0x0000000000000000L, 100, "", 0, 0))
    assertEquals("", Conversion.longToHex(0x0000000000000000L, 0, "", 100, 0))
    assertEquals(
      "ffffffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 0, 0))
    assertEquals(
      "3fffffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcde3L, 0, "ffffffffffffffffffffffff", 0, 1))
    assertEquals(
      "feffffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 0, 2))
    assertEquals(
      "fedcffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 0, 4))
    assertEquals(
      "fedcba098765432fffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 0, 15))
    assertEquals(
      "fedcba0987654321ffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 0, 16))
    assertEquals(
      "fff3ffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcde3L, 0, "ffffffffffffffffffffffff", 3, 1))
    assertEquals(
      "ffffefffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 3, 2))
    assertEquals(
      "ffffedcfffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 3, 4))
    assertEquals(
      "ffffedcba098765432ffffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 3, 15))
    assertEquals(
      "ffffedcba0987654321fffff",
      Conversion.longToHex(0x1234567890abcdefL, 0, "ffffffffffffffffffffffff", 3, 16))
    assertEquals(
      "7fffffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 1, "ffffffffffffffffffffffff", 0, 1))
    assertEquals(
      "bfffffffffffffffffffffff",
      Conversion.longToHex(0x1234567890abcdefL, 2, "ffffffffffffffffffffffff", 0, 1))
    assertEquals(
      "fffdb975121fca86420fffff",
      Conversion.longToHex(0x1234567890abcdefL, 3, "ffffffffffffffffffffffff", 3, 16))
    // assertEquals("ffffffffffffffffffffffff", Conversion.longToHex(0x1234567890ABCDEFL, 4, "ffffffffffffffffffffffff", 3, 16));//rejected
    // by assertion
    assertEquals(
      "fffedcba0987654321ffffff",
      Conversion.longToHex(0x1234567890abcdefL, 4, "ffffffffffffffffffffffff", 3, 15))
    assertEquals("fedcba0987654321", Conversion.longToHex(0x1234567890abcdefL, 0, "", 0, 16))
    assertThrows[StringIndexOutOfBoundsException](Conversion.longToHex(0x1234567890abcdefL, 0, "", 1, 8))
    ()
  }

  /**
    * Tests {@link Conversion# intToHex ( int, int, String, int, int)}.
    */
  @Test def testIntToHex(): Unit = {
    assertEquals("", Conversion.intToHex(0x00000000, 0, "", 0, 0))
    assertEquals("", Conversion.intToHex(0x00000000, 100, "", 0, 0))
    assertEquals("", Conversion.intToHex(0x00000000, 0, "", 100, 0))
    assertEquals("ffffffffffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 0, 0))
    assertEquals("3fffffffffffffffffffffff", Conversion.intToHex(0x90abcde3, 0, "ffffffffffffffffffffffff", 0, 1))
    assertEquals("feffffffffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 0, 2))
    assertEquals("fedcffffffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 0, 4))
    assertEquals("fedcba0fffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 0, 7))
    assertEquals("fedcba09ffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 0, 8))
    assertEquals("fff3ffffffffffffffffffff", Conversion.intToHex(0x90abcde3, 0, "ffffffffffffffffffffffff", 3, 1))
    assertEquals("ffffefffffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 3, 2))
    assertEquals("ffffedcfffffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 3, 4))
    assertEquals("ffffedcba0ffffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 3, 7))
    assertEquals("ffffedcba09fffffffffffff", Conversion.intToHex(0x90abcdef, 0, "ffffffffffffffffffffffff", 3, 8))
    assertEquals("7fffffffffffffffffffffff", Conversion.intToHex(0x90abcdef, 1, "ffffffffffffffffffffffff", 0, 1))
    assertEquals("bfffffffffffffffffffffff", Conversion.intToHex(0x90abcdef, 2, "ffffffffffffffffffffffff", 0, 1))
    assertEquals("fffdb97512ffffffffffffff", Conversion.intToHex(0x90abcdef, 3, "ffffffffffffffffffffffff", 3, 8))
    // assertEquals("ffffffffffffffffffffffff", Conversion.intToHex(0x90ABCDEF,
    // 4, "ffffffffffffffffffffffff", 3, 8));//rejected by assertion
    assertEquals("fffedcba09ffffffffffffff", Conversion.intToHex(0x90abcdef, 4, "ffffffffffffffffffffffff", 3, 7))
    assertEquals("fedcba09", Conversion.intToHex(0x90abcdef, 0, "", 0, 8))
    assertThrows[StringIndexOutOfBoundsException](Conversion.intToHex(0x90abcdef, 0, "", 1, 8))
    ()
  }

  /**
    * Tests {@link Conversion# shortToHex ( short, int, String, int, int)}.
    */
  @Test def testShortToHex(): Unit = {
    assertEquals("", Conversion.shortToHex(0x0000.toShort, 0, "", 0, 0))
    assertEquals("", Conversion.shortToHex(0x0000.toShort, 100, "", 0, 0))
    assertEquals("", Conversion.shortToHex(0x0000.toShort, 0, "", 100, 0))
    assertEquals("ffffffffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 0, "ffffffffffffffffffffffff", 0, 0))
    assertEquals("3fffffffffffffffffffffff", Conversion.shortToHex(0xcde3.toShort, 0, "ffffffffffffffffffffffff", 0, 1))
    assertEquals("feffffffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 0, "ffffffffffffffffffffffff", 0, 2))
    assertEquals("fedfffffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 0, "ffffffffffffffffffffffff", 0, 3))
    assertEquals("fedcffffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 0, "ffffffffffffffffffffffff", 0, 4))
    assertEquals("fff3ffffffffffffffffffff", Conversion.shortToHex(0xcde3.toShort, 0, "ffffffffffffffffffffffff", 3, 1))
    assertEquals("ffffefffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 0, "ffffffffffffffffffffffff", 3, 2))
    assertEquals("7fffffffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 1, "ffffffffffffffffffffffff", 0, 1))
    assertEquals("bfffffffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 2, "ffffffffffffffffffffffff", 0, 1))
    assertEquals("fffdb9ffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 3, "ffffffffffffffffffffffff", 3, 4))
    // assertEquals("ffffffffffffffffffffffff", Conversion.shortToHex((short) 0xCDEF,
    // 4, "ffffffffffffffffffffffff", 3, 4));//rejected by assertion
    assertEquals("fffedcffffffffffffffffff", Conversion.shortToHex(0xcdef.toShort, 4, "ffffffffffffffffffffffff", 3, 3))
    assertEquals("fedc", Conversion.shortToHex(0xcdef.toShort, 0, "", 0, 4))
    assertThrows[StringIndexOutOfBoundsException](Conversion.shortToHex(0xcdef.toShort, 0, "", 1, 4))
    ()
  }

  /**
    * Tests {@link Conversion# byteToHex ( byte, int, String, int, int)}.
    */
  @Test def testByteToHex(): Unit = {
    assertEquals("", Conversion.byteToHex(0x00.toByte, 0, "", 0, 0))
    assertEquals("", Conversion.byteToHex(0x00.toByte, 100, "", 0, 0))
    assertEquals("", Conversion.byteToHex(0x00.toByte, 0, "", 100, 0))
    assertEquals("00000", Conversion.byteToHex(0xef.toByte, 0, "00000", 0, 0))
    assertEquals("f0000", Conversion.byteToHex(0xef.toByte, 0, "00000", 0, 1))
    assertEquals("fe000", Conversion.byteToHex(0xef.toByte, 0, "00000", 0, 2))
    assertEquals("000f0", Conversion.byteToHex(0xef.toByte, 0, "00000", 3, 1))
    assertEquals("000fe", Conversion.byteToHex(0xef.toByte, 0, "00000", 3, 2))
    assertEquals("70000", Conversion.byteToHex(0xef.toByte, 1, "00000", 0, 1))
    assertEquals("b0000", Conversion.byteToHex(0xef.toByte, 2, "00000", 0, 1))
    assertEquals("000df", Conversion.byteToHex(0xef.toByte, 3, "00000", 3, 2))
    // assertEquals("00000", Conversion.byteToHex((byte) 0xEF, 4, "00000", 3, 2));//rejected by
    // assertion
    assertEquals("000e0", Conversion.byteToHex(0xef.toByte, 4, "00000", 3, 1))
    assertEquals("fe", Conversion.byteToHex(0xef.toByte, 0, "", 0, 2))
    assertThrows[StringIndexOutOfBoundsException](Conversion.byteToHex(0xef.toByte, 0, "", 1, 2))
    ()
  }

  /**
    * Tests {@link Conversion# longToBinary ( long, int, boolean[], int, int)}.
    */
  @Test def testLongToBinary(): Unit = {
    assertArrayEquals(
      new Array[Boolean](0),
      Conversion.longToBinary(0x0000000000000000L, 0, new Array[Boolean](0), 0, 0))
    assertArrayEquals(
      new Array[Boolean](0),
      Conversion.longToBinary(0x0000000000000000L, 100, new Array[Boolean](0), 0, 0))
    assertArrayEquals(
      new Array[Boolean](0),
      Conversion.longToBinary(0x0000000000000000L, 0, new Array[Boolean](0), 100, 0))
    assertArrayEquals(
      new Array[Boolean](69),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 0, 0))
    assertArrayEquals(
      Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 0, 1)
    )
    assertArrayEquals(
      Array[Boolean](true, true, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 0, 2)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 0, 3)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true,
        true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false,
        false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false,
        true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false,
        false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 0, 63)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true,
        true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false,
        false, false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false,
        true, false, true, true, false, false, false, true, false, false, true, false, false, false, false, false,
        false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 0, 64)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 2, 1)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, true, true, true, false, true, true, true, true, false, true, true, false,
        false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false,
        false, true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true,
        false, false, false, true, false, true, true, false, false, false, true, false, false, true, false, false,
        false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 0, new Array[Boolean](69), 2, 64)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true,
        true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false,
        false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true,
        false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false,
        false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 1, new Array[Boolean](69), 0, 63)
    )
    assertArrayEquals(
      Array[Boolean](true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true,
        false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false,
        false, true, true, true, true, false, false, true, true, false, true, false, true, false, false, false, true,
        false, true, true, false, false, false, true, false, false, true, false, false, false, false, false, false,
        false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 2, new Array[Boolean](69), 0, 62)
    )
    // assertArrayEquals(new boolean[]{false, false, false, true, true, false, true, true,
    // true, true, false, true, true, false, false, true, true, true, true, false, true,
    // false, true, false, true, false, false, false, false, true, false, false, true,
    // false, false, false, true, true, true, true, false, false, true, true, false, true,
    // false, true, false, false, false, true, false, true, true, false, false, false, true,
    // false, false, true, false, false, false
    // , false, false, false, false}, Conversion.longToBinary(0x1234567890ABCDEFL, 2, new
    // boolean[69], 3, 63));//rejected by assertion
    assertArrayEquals(
      Array[Boolean](false, false, false, true, true, false, true, true, true, true, false, true, true, false, false,
        true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false,
        true, false, false, false, true, true, true, true, false, false, true, true, false, true, false, true, false,
        false, false, true, false, true, true, false, false, false, true, false, false, true, false, false, false,
        false, false, false, false),
      Conversion.longToBinary(0x1234567890abcdefL, 2, new Array[Boolean](69), 3, 62)
    )
  }

  /**
    * Tests {@link Conversion# intToBinary ( int, int, boolean[], int, int)}.
    */
  @Test def testIntToBinary(): Unit = {
    assertArrayEquals(new Array[Boolean](0), Conversion.intToBinary(0x00000000, 0, new Array[Boolean](0), 0, 0))
    assertArrayEquals(new Array[Boolean](0), Conversion.intToBinary(0x00000000, 100, new Array[Boolean](0), 0, 0))
    assertArrayEquals(new Array[Boolean](0), Conversion.intToBinary(0x00000000, 0, new Array[Boolean](0), 100, 0))
    assertArrayEquals(new Array[Boolean](69), Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](69), 0, 0))
    assertArrayEquals(
      Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 0, 1)
    )
    assertArrayEquals(
      Array[Boolean](true, true, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 0, 2)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 0, 3)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true,
        true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, false, false,
        false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 0, 31)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true,
        true, true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false,
        false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 0, 32)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 2, 1)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, true, true, true, false, true, true, true, true, false, true, true, false,
        false, true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false,
        false, true, false, false, false),
      Conversion.intToBinary(0x90abcdef, 0, new Array[Boolean](37), 2, 32)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, false, true, true, true, true, false, true, true, false, false, true, true, true,
        true, false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false,
        false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 1, new Array[Boolean](37), 0, 31)
    )
    assertArrayEquals(
      Array[Boolean](true, true, false, true, true, true, true, false, true, true, false, false, true, true, true, true,
        false, true, false, true, false, true, false, false, false, false, true, false, false, true, false, false,
        false, false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 2, new Array[Boolean](37), 0, 30)
    )
    // assertArrayEquals(new boolean[]{false, false, false, true, true, false, true,
    // true,
    // false, true, false, true, false, false, false, false, true, false, false, false,
    // false, false, false, false}, Conversion.intToBinary(0x90ABCDEF, 2, new boolean[37],
    // 3, 31));//rejected by assertion
    assertArrayEquals(
      Array[Boolean](false, false, false, true, true, false, true, true, true, true, false, true, true, false, false,
        true, true, true, true, false, true, false, true, false, true, false, false, false, false, true, false, false,
        true, false, false, false, false),
      Conversion.intToBinary(0x90abcdef, 2, new Array[Boolean](37), 3, 30)
    )
  }

  /**
    * Tests {@link Conversion# shortToBinary ( short, int, boolean[], int, int)}.
    */
  @Test def testShortToBinary(): Unit = {
    assertArrayEquals(new Array[Boolean](0), Conversion.shortToBinary(0x0000.toShort, 0, new Array[Boolean](0), 0, 0))
    assertArrayEquals(new Array[Boolean](0), Conversion.shortToBinary(0x0000.toShort, 100, new Array[Boolean](0), 0, 0))
    assertArrayEquals(new Array[Boolean](0), Conversion.shortToBinary(0x0000.toShort, 0, new Array[Boolean](0), 100, 0))
    assertArrayEquals(new Array[Boolean](69), Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](69), 0, 0))
    assertArrayEquals(
      Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 0, 1)
    )
    assertArrayEquals(
      Array[Boolean](true, true, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 0, 2)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 0, 3)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, true, false, true, true, true, true, false, true, true, false, false, true,
        false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 0, 15)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, true, false, true, true, true, true, false, true, true, false, false, true, true,
        false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 0, 16)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 2, 1)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, true, true, true, false, true, true, true, true, false, true, true, false,
        false, true, true, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 0, new Array[Boolean](21), 2, 16)
    )
    assertArrayEquals(
      Array[Boolean](true, true, true, false, true, true, true, true, false, true, true, false, false, true, true,
        false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 1, new Array[Boolean](21), 0, 15)
    )
    assertArrayEquals(
      Array[Boolean](true, true, false, true, true, true, true, false, true, true, false, false, true, true, false,
        false, false, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 2, new Array[Boolean](21), 0, 14)
    )
    // true, true, false, true, true, false, false, true, false, false, false, false,
    // false}, Conversion.shortToBinary((short) 0xCDEF, 2, new boolean[21],
    // 3, 15));//rejected by
    assertArrayEquals(
      Array[Boolean](false, false, false, true, true, false, true, true, true, true, false, true, true, false, false,
        true, true, false, false, false, false),
      Conversion.shortToBinary(0xcdef.toShort, 2, new Array[Boolean](21), 3, 14)
    )
  }

  /**
    * Tests {@link Conversion# byteToBinary ( byte, int, boolean[], int, int)}.
    */
  @Test def testByteToBinary(): Unit = {
    assertArrayEquals(new Array[Boolean](0), Conversion.byteToBinary(0x00.toByte, 0, new Array[Boolean](0), 0, 0))
    assertArrayEquals(new Array[Boolean](0), Conversion.byteToBinary(0x00.toByte, 100, new Array[Boolean](0), 0, 0))
    assertArrayEquals(new Array[Boolean](0), Conversion.byteToBinary(0x00.toByte, 0, new Array[Boolean](0), 100, 0))
    assertArrayEquals(new Array[Boolean](69), Conversion.byteToBinary(0xef.toByte, 0, new Array[Boolean](69), 0, 0))
    assertArrayEquals(
      Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 0, 1)
    )
    assertArrayEquals(
      Array[Boolean](true, false, false, false, false, false, false, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 0, 2)
    )
    assertArrayEquals(
      Array[Boolean](true, false, true, false, false, false, false, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 0, 3)
    )
    assertArrayEquals(
      Array[Boolean](true, false, true, false, true, false, false, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 0, 7)
    )
    assertArrayEquals(
      Array[Boolean](true, false, true, false, true, false, false, true, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 0, 8)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, false, false, false, false, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 2, 1)
    )
    assertArrayEquals(
      Array[Boolean](false, false, true, false, true, false, true, false, false, true, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 0, new Array[Boolean](13), 2, 8)
    )
    assertArrayEquals(
      Array[Boolean](false, true, false, true, false, false, true, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 1, new Array[Boolean](13), 0, 7)
    )
    assertArrayEquals(
      Array[Boolean](true, false, true, false, false, true, false, false, false, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 2, new Array[Boolean](13), 0, 6)
    )
    // false, false, false, false, false}, Conversion.byteToBinary((byte) 0x95, 2, new
    // boolean[13], 3, 7));//rejected by assertion
    assertArrayEquals(
      Array[Boolean](false, false, false, true, false, true, false, false, true, false, false, false, false),
      Conversion.byteToBinary(0x95.toByte, 2, new Array[Boolean](13), 3, 6)
    )
  }

  /**
    * Tests {@link Conversion# uuidToByteArray ( UUID, byte[], int, int)}.
    */
  @Test def testUuidToByteArray(): Unit = {
    assertArrayEquals(
      Array[Byte](
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte,
        0xff.toByte
      ),
      Conversion.uuidToByteArray(new UUID(0xffffffffffffffffL, 0xffffffffffffffffL), new Array[Byte](16), 0, 16)
    )
    assertArrayEquals(
      Array[Byte](
        0x88.toByte,
        0x99.toByte,
        0xaa.toByte,
        0xbb.toByte,
        0xcc.toByte,
        0xdd.toByte,
        0xee.toByte,
        0xff.toByte,
        0x00.toByte,
        0x11.toByte,
        0x22.toByte,
        0x33.toByte,
        0x44.toByte,
        0x55.toByte,
        0x66.toByte,
        0x77.toByte
      ),
      Conversion.uuidToByteArray(new UUID(0xffeeddccbbaa9988L, 0x7766554433221100L), new Array[Byte](16), 0, 16)
    )
    assertArrayEquals(
      Array[Byte](
        0x00.toByte,
        0x00.toByte,
        0x00.toByte,
        0x00.toByte,
        0x88.toByte,
        0x99.toByte,
        0xaa.toByte,
        0xbb.toByte,
        0xcc.toByte,
        0xdd.toByte,
        0xee.toByte,
        0xff.toByte,
        0x00.toByte,
        0x00.toByte,
        0x00.toByte,
        0x00.toByte
      ),
      Conversion.uuidToByteArray(new UUID(0xffeeddccbbaa9988L, 0x7766554433221100L), new Array[Byte](16), 4, 8)
    )
    assertArrayEquals(
      Array[Byte](
        0x00.toByte,
        0x00.toByte,
        0x88.toByte,
        0x99.toByte,
        0xaa.toByte,
        0xbb.toByte,
        0xcc.toByte,
        0xdd.toByte,
        0xee.toByte,
        0xff.toByte,
        0x00.toByte,
        0x11.toByte,
        0x22.toByte,
        0x33.toByte,
        0x00.toByte,
        0x00.toByte
      ),
      Conversion.uuidToByteArray(new UUID(0xffeeddccbbaa9988L, 0x7766554433221100L), new Array[Byte](16), 2, 12)
    )
  }

  /**
    * Tests {@link Conversion# byteArrayToUuid ( byte[ ], int)}.
    */
  @Test def testByteArrayToUuid(): Unit = {
    assertEquals(
      new UUID(0xffffffffffffffffL, 0xffffffffffffffffL),
      Conversion.byteArrayToUuid(
        Array[Byte](
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte,
          0xff.toByte
        ),
        0
      )
    )
    assertEquals(
      new UUID(0xffeeddccbbaa9988L, 0x7766554433221100L),
      Conversion.byteArrayToUuid(
        Array[Byte](
          0x88.toByte,
          0x99.toByte,
          0xaa.toByte,
          0xbb.toByte,
          0xcc.toByte,
          0xdd.toByte,
          0xee.toByte,
          0xff.toByte,
          0x00.toByte,
          0x11.toByte,
          0x22.toByte,
          0x33.toByte,
          0x44.toByte,
          0x55.toByte,
          0x66.toByte,
          0x77.toByte
        ),
        0
      )
    )
    assertEquals(
      new UUID(0xffeeddccbbaa9988L, 0x7766554433221100L),
      Conversion.byteArrayToUuid(
        Array[Byte](
          0,
          0,
          0x88.toByte,
          0x99.toByte,
          0xaa.toByte,
          0xbb.toByte,
          0xcc.toByte,
          0xdd.toByte,
          0xee.toByte,
          0xff.toByte,
          0x00.toByte,
          0x11.toByte,
          0x22.toByte,
          0x33.toByte,
          0x44.toByte,
          0x55.toByte,
          0x66.toByte,
          0x77.toByte
        ),
        2
      )
    )
  }
}
