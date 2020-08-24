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

import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows

object CharUtilsTest {
  private val CHAR_COPY = '\u00a9'
  private val CHARACTER_A: Char = 'A'
  private val CHARACTER_B: Char = 'B'
}

/**
  * Unit tests {@link org.apache.commons.lang3.CharUtils}.
  */
class CharUtilsTest {
  @Test def testCompare(): Unit = {
    assertTrue(CharUtils.compare('a', 'b') < 0)
    assertEquals(0, CharUtils.compare('c', 'c'))
    assertTrue(CharUtils.compare('c', 'a') > 0)
  }

//  @Test def testConstructor(): Unit = {
//    assertNotNull(new CharUtils.type)
//    val cons = classOf[CharUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[CharUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[CharUtils.type].getModifiers))
//  }

  @Test def testIsAscii_char(): Unit = {
    assertTrue(CharUtils.isAscii('a'))
    assertTrue(CharUtils.isAscii('A'))
    assertTrue(CharUtils.isAscii('3'))
    assertTrue(CharUtils.isAscii('-'))
    assertTrue(CharUtils.isAscii('\n'))
    assertFalse(CharUtils.isAscii(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 255) {
      assertEquals(i < 128, CharUtils.isAscii(i.toChar))
    }
  }

  @Test def testIsAsciiAlpha_char(): Unit = {
    assertTrue(CharUtils.isAsciiAlpha('a'))
    assertTrue(CharUtils.isAsciiAlpha('A'))
    assertFalse(CharUtils.isAsciiAlpha('3'))
    assertFalse(CharUtils.isAsciiAlpha('-'))
    assertFalse(CharUtils.isAsciiAlpha('\n'))
    assertFalse(CharUtils.isAsciiAlpha(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if ((i >= 'A' && i <= 'Z') || (i >= 'a' && i <= 'z')) assertTrue(CharUtils.isAsciiAlpha(i.toChar))
      else assertFalse(CharUtils.isAsciiAlpha(i.toChar))
    }
  }

  @Test def testIsAsciiAlphaLower_char(): Unit = {
    assertTrue(CharUtils.isAsciiAlphaLower('a'))
    assertFalse(CharUtils.isAsciiAlphaLower('A'))
    assertFalse(CharUtils.isAsciiAlphaLower('3'))
    assertFalse(CharUtils.isAsciiAlphaLower('-'))
    assertFalse(CharUtils.isAsciiAlphaLower('\n'))
    assertFalse(CharUtils.isAsciiAlphaLower(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if (i >= 'a' && i <= 'z') assertTrue(CharUtils.isAsciiAlphaLower(i.toChar))
      else assertFalse(CharUtils.isAsciiAlphaLower(i.toChar))
    }
  }

  @Test def testIsAsciiAlphanumeric_char(): Unit = {
    assertTrue(CharUtils.isAsciiAlphanumeric('a'))
    assertTrue(CharUtils.isAsciiAlphanumeric('A'))
    assertTrue(CharUtils.isAsciiAlphanumeric('3'))
    assertFalse(CharUtils.isAsciiAlphanumeric('-'))
    assertFalse(CharUtils.isAsciiAlphanumeric('\n'))
    assertFalse(CharUtils.isAsciiAlphanumeric(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if ((i >= 'A' && i <= 'Z') || (i >= 'a' && i <= 'z') || (i >= '0' && i <= '9'))
        assertTrue(CharUtils.isAsciiAlphanumeric(i.toChar))
      else assertFalse(CharUtils.isAsciiAlphanumeric(i.toChar))
    }
  }

  @Test def testIsAsciiAlphaUpper_char(): Unit = {
    assertFalse(CharUtils.isAsciiAlphaUpper('a'))
    assertTrue(CharUtils.isAsciiAlphaUpper('A'))
    assertFalse(CharUtils.isAsciiAlphaUpper('3'))
    assertFalse(CharUtils.isAsciiAlphaUpper('-'))
    assertFalse(CharUtils.isAsciiAlphaUpper('\n'))
    assertFalse(CharUtils.isAsciiAlphaUpper(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if (i >= 'A' && i <= 'Z') assertTrue(CharUtils.isAsciiAlphaUpper(i.toChar))
      else assertFalse(CharUtils.isAsciiAlphaUpper(i.toChar))
    }
  }

  @Test def testIsAsciiControl_char(): Unit = {
    assertFalse(CharUtils.isAsciiControl('a'))
    assertFalse(CharUtils.isAsciiControl('A'))
    assertFalse(CharUtils.isAsciiControl('3'))
    assertFalse(CharUtils.isAsciiControl('-'))
    assertTrue(CharUtils.isAsciiControl('\n'))
    assertFalse(CharUtils.isAsciiControl(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if (i < 32 || i == 127) assertTrue(CharUtils.isAsciiControl(i.toChar))
      else assertFalse(CharUtils.isAsciiControl(i.toChar))
    }
  }

  @Test def testIsAsciiNumeric_char(): Unit = {
    assertFalse(CharUtils.isAsciiNumeric('a'))
    assertFalse(CharUtils.isAsciiNumeric('A'))
    assertTrue(CharUtils.isAsciiNumeric('3'))
    assertFalse(CharUtils.isAsciiNumeric('-'))
    assertFalse(CharUtils.isAsciiNumeric('\n'))
    assertFalse(CharUtils.isAsciiNumeric(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if (i >= '0' && i <= '9') assertTrue(CharUtils.isAsciiNumeric(i.toChar))
      else assertFalse(CharUtils.isAsciiNumeric(i.toChar))
    }
  }

  @Test def testIsAsciiPrintable_char(): Unit = {
    assertTrue(CharUtils.isAsciiPrintable('a'))
    assertTrue(CharUtils.isAsciiPrintable('A'))
    assertTrue(CharUtils.isAsciiPrintable('3'))
    assertTrue(CharUtils.isAsciiPrintable('-'))
    assertFalse(CharUtils.isAsciiPrintable('\n'))
    assertFalse(CharUtils.isAsciiPrintable(CharUtilsTest.CHAR_COPY))
    for (i <- 0 until 196) {
      if (i >= 32 && i <= 126) assertTrue(CharUtils.isAsciiPrintable(i.toChar))
      else assertFalse(CharUtils.isAsciiPrintable(i.toChar))
    }
  }

  @Test def testToChar_Character(): Unit = {
    assertEquals('A', CharUtils.toChar(CharUtilsTest.CHARACTER_A))
    assertEquals('B', CharUtils.toChar(CharUtilsTest.CHARACTER_B))
    assertThrows[NullPointerException](CharUtils.toChar(null.asInstanceOf[Character]))
    ()
  }

  @Test def testToChar_Character_char(): Unit = {
    assertEquals('A', CharUtils.toChar(CharUtilsTest.CHARACTER_A, 'X'))
    assertEquals('B', CharUtils.toChar(CharUtilsTest.CHARACTER_B, 'X'))
    assertEquals('X', CharUtils.toChar(null.asInstanceOf[Character], 'X'))
  }

  @Test def testToChar_String(): Unit = {
    assertEquals('A', CharUtils.toChar("A"))
    assertEquals('B', CharUtils.toChar("BA"))
    assertThrows[NullPointerException](CharUtils.toChar(null.asInstanceOf[String]))
    assertThrows[IllegalArgumentException](CharUtils.toChar(""))
    ()
  }

  @Test def testToChar_String_char(): Unit = {
    assertEquals('A', CharUtils.toChar("A", 'X'))
    assertEquals('B', CharUtils.toChar("BA", 'X'))
    assertEquals('X', CharUtils.toChar("", 'X'))
    assertEquals('X', CharUtils.toChar(null.asInstanceOf[String], 'X'))
  }

  @SuppressWarnings(Array("deprecation"))
  // intentional test of deprecated method
  @Test def testToCharacterObject_char(): Unit = {
    assertEquals(Character.valueOf('a'), CharUtils.toCharacterObject('a'))
    assertSame(CharUtils.toCharacterObject('a'), CharUtils.toCharacterObject('a'))

    for (i <- 0 until 128) {
      val ch = CharUtils.toCharacterObject(i.toChar)
      val ch2 = CharUtils.toCharacterObject(i.toChar)
      assertSame(ch, ch2)
      assertEquals(i, ch.charValue)
    }

    for (i <- 128 until 196) {
      val ch = CharUtils.toCharacterObject(i.toChar)
      val ch2 = CharUtils.toCharacterObject(i.toChar)
      assertEquals(ch, ch2)
      assertNotSame(ch, ch2)
      assertEquals(i, ch.charValue)
      assertEquals(i, ch2.charValue)
    }

    assertSame(CharUtils.toCharacterObject("a"), CharUtils.toCharacterObject('a'))
  }

  @Test def testToCharacterObject_String(): Unit = {
    assertNull(CharUtils.toCharacterObject(null))
    assertNull(CharUtils.toCharacterObject(""))
    assertEquals(Character.valueOf('a'), CharUtils.toCharacterObject("a"))
    assertEquals(Character.valueOf('a'), CharUtils.toCharacterObject("abc"))
    assertSame(CharUtils.toCharacterObject("a"), CharUtils.toCharacterObject("a"))
  }

  @Test def testToIntValue_char(): Unit = {
    assertEquals(0, CharUtils.toIntValue('0'))
    assertEquals(1, CharUtils.toIntValue('1'))
    assertEquals(2, CharUtils.toIntValue('2'))
    assertEquals(3, CharUtils.toIntValue('3'))
    assertEquals(4, CharUtils.toIntValue('4'))
    assertEquals(5, CharUtils.toIntValue('5'))
    assertEquals(6, CharUtils.toIntValue('6'))
    assertEquals(7, CharUtils.toIntValue('7'))
    assertEquals(8, CharUtils.toIntValue('8'))
    assertEquals(9, CharUtils.toIntValue('9'))
    assertThrows[IllegalArgumentException](CharUtils.toIntValue('a'))
    ()
  }

  @Test def testToIntValue_char_int(): Unit = {
    assertEquals(0, CharUtils.toIntValue('0', -1))
    assertEquals(3, CharUtils.toIntValue('3', -1))
    assertEquals(-1, CharUtils.toIntValue('a', -1))
  }

  @Test def testToIntValue_Character(): Unit = {
    assertEquals(0, CharUtils.toIntValue(Character.valueOf('0')))
    assertEquals(3, CharUtils.toIntValue(Character.valueOf('3')))
    assertThrows[NullPointerException](CharUtils.toIntValue(null))
    assertThrows[IllegalArgumentException](CharUtils.toIntValue(CharUtilsTest.CHARACTER_A))
    ()
  }

  @Test def testToIntValue_Character_int(): Unit = {
    assertEquals(0, CharUtils.toIntValue(Character.valueOf('0'), -1))
    assertEquals(3, CharUtils.toIntValue(Character.valueOf('3'), -1))
    assertEquals(-1, CharUtils.toIntValue(Character.valueOf('A'), -1))
    assertEquals(-1, CharUtils.toIntValue(null, -1))
  }
  @Test def testToString_char(): Unit = {
    assertEquals("a", CharUtils.toString('a'))
    assertSame(CharUtils.toString('a'), CharUtils.toString('a'))
    for (i <- 0 until 128) {
      val str: String = CharUtils.toString(i.toChar)
      val str2: String = CharUtils.toString(i.toChar)
      assertSame(str, str2)
      assertEquals(1, str.length)
      assertEquals(i, str.charAt(0))
    }
    for (i <- 128 until 196) {
      val str: String = CharUtils.toString(i.toChar)
      val str2: String = CharUtils.toString(i.toChar)
      assertEquals(str, str2)
      assertNotSame(str, str2)
      assertEquals(1, str.length)
      assertEquals(i, str.charAt(0))
      assertEquals(1, str2.length)
      assertEquals(i, str2.charAt(0))
    }
  }

  @Test def testToString_Character(): Unit = {
    assertNull(CharUtils.toString(null))
    assertEquals("A", CharUtils.toString(CharUtilsTest.CHARACTER_A))
    assertSame(CharUtils.toString(CharUtilsTest.CHARACTER_A), CharUtils.toString(CharUtilsTest.CHARACTER_A))
  }

  @Test def testToUnicodeEscaped_char(): Unit = {
    assertEquals("\\u0041", CharUtils.unicodeEscaped('A'))
    assertEquals("\\u004c", CharUtils.unicodeEscaped('L'))
    for (i <- 0 until 196) {
      val str: String = CharUtils.unicodeEscaped(i.toChar)
      assertEquals(6, str.length)
      val `val`: Int = Integer.parseInt(str.substring(2), 16)
      assertEquals(i, `val`)
    }
    assertEquals("\\u0999", CharUtils.unicodeEscaped(0x999.toChar))
    assertEquals("\\u1001", CharUtils.unicodeEscaped(0x1001.toChar))
  }

  @Test def testToUnicodeEscaped_Character(): Unit = {
    assertNull(CharUtils.unicodeEscaped(null))
    assertEquals("\\u0041", CharUtils.unicodeEscaped(CharUtilsTest.CHARACTER_A))
  }
}
