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

import org.junit.Assert._
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.nio.CharBuffer
import org.apache.commons.lang3.ArrayUtils
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

@deprecated
object StrBuilderTest {

  private class MockReadable private[text] extends Readable {
    final private var src: CharBuffer = null

    def this(src: String) = {
      this()
      this.src = CharBuffer.wrap(src)
    }

    @throws[IOException]
    override def read(cb: CharBuffer) = src.read(cb)
  }

  private[text] val A_NUMBER_MATCHER = new StrMatcher() {
    override def isMatch(buffer: Array[Char], pos: Int, bufferStart: Int, bufferEnd: Int): Int = {
      if (buffer(pos) == 'A') {
        val nextPos = pos + 1
        if (nextPos < bufferEnd && buffer(nextPos) >= '0' && buffer(nextPos) <= '9') return 2
      }
      0
    }
  }
}

/**
  * Unit tests for {@link org.apache.commons.lang3.text.StrBuilder}.
  */
@deprecated
class StrBuilderTest extends JUnitSuite {
//  @Test def testConstructors(): Unit = {
//    val sb0 = new StrBuilder
//    assertEquals(32, sb0.capacity)
//    assertEquals(0, sb0.length)
//    assertEquals(0, sb0.size)
//    val sb1 = new StrBuilder(32)
//    assertEquals(32, sb1.capacity)
//    assertEquals(0, sb1.length)
//    assertEquals(0, sb1.size)
//    val sb2 = new StrBuilder(0)
//    assertEquals(32, sb2.capacity)
//    assertEquals(0, sb2.length)
//    assertEquals(0, sb2.size)
//    val sb3 = new StrBuilder(-1)
//    assertEquals(32, sb3.capacity)
//    assertEquals(0, sb3.length)
//    assertEquals(0, sb3.size)
//    val sb4 = new StrBuilder(1)
//    assertEquals(1, sb4.capacity)
//    assertEquals(0, sb4.length)
//    assertEquals(0, sb4.size)
//    val sb5 = new StrBuilder(null)
//    assertEquals(32, sb5.capacity)
//    assertEquals(0, sb5.length)
//    assertEquals(0, sb5.size)
//    val sb6 = new StrBuilder("")
//    assertEquals(32, sb6.capacity)
//    assertEquals(0, sb6.length)
//    assertEquals(0, sb6.size)
//    val sb7 = new StrBuilder("foo")
//    assertEquals(35, sb7.capacity)
//    assertEquals(3, sb7.length)
//    assertEquals(3, sb7.size)
//  }

  @Test def testChaining(): Unit = {
    val sb = new StrBuilder
    assertSame(sb, sb.setNewLineText(null))
    assertSame(sb, sb.setNullText(null))
    assertSame(sb, sb.setLength(1))
    assertSame(sb, sb.setCharAt(0, 'a'))
    assertSame(sb, sb.ensureCapacity(0))
    assertSame(sb, sb.minimizeCapacity)
    assertSame(sb, sb.clear)
    assertSame(sb, sb.reverse)
    assertSame(sb, sb.trim)
  }

  @Test
  @throws[Exception]
  def testReadFromReader(): Unit = {
    var s = ""
    for (i <- 0 until 100) {
      val sb = new StrBuilder
      val len = sb.readFrom(new StringReader(s))
      assertEquals(s.length, len)
      assertEquals(s, sb.toString)
      s += Integer.toString(i)
    }
  }

  @Test
  @throws[Exception]
  def testReadFromReaderAppendsToEnd(): Unit = {
    val sb = new StrBuilder("Test")
    sb.readFrom(new StringReader(" 123"))
    assertEquals("Test 123", sb.toString)
  }

  @Test
  @throws[Exception]
  def testReadFromCharBuffer(): Unit = {
    var s = ""
    for (i <- 0 until 100) {
      val sb = new StrBuilder
      val len = sb.readFrom(CharBuffer.wrap(s))
      assertEquals(s.length, len)
      assertEquals(s, sb.toString)
      s += Integer.toString(i)
    }
  }

  @Test
  @throws[Exception]
  def testReadFromCharBufferAppendsToEnd(): Unit = {
    val sb = new StrBuilder("Test")
    sb.readFrom(CharBuffer.wrap(" 123"))
    assertEquals("Test 123", sb.toString)
  }

  @Test
  @throws[Exception]
  def testReadFromReadable(): Unit = {
    var s = ""
    for (i <- 0 until 100) {
      val sb = new StrBuilder
      val len = sb.readFrom(new StrBuilderTest.MockReadable(s))
      assertEquals(s.length, len)
      assertEquals(s, sb.toString)
      s += Integer.toString(i)
    }
  }

  @Test
  @throws[Exception]
  def testReadFromReadableAppendsToEnd(): Unit = {
    val sb = new StrBuilder("Test")
    sb.readFrom(new StrBuilderTest.MockReadable(" 123"))
    assertEquals("Test 123", sb.toString)
  }

  @Test def testGetSetNewLineText(): Unit = {
    val sb = new StrBuilder
    assertNull(sb.getNewLineText)
    sb.setNewLineText("#")
    assertEquals("#", sb.getNewLineText)
    sb.setNewLineText("")
    assertEquals("", sb.getNewLineText)
    sb.setNewLineText(null)
    assertNull(sb.getNewLineText)
  }

  @Test def testGetSetNullText(): Unit = {
    val sb = new StrBuilder
    assertNull(sb.getNullText)
    sb.setNullText("null")
    assertEquals("null", sb.getNullText)
    sb.setNullText("")
    assertNull(sb.getNullText)
    sb.setNullText("NULL")
    assertEquals("NULL", sb.getNullText)
    sb.setNullText(null)
    assertNull(sb.getNullText)
  }

  @Test def testCapacityAndLength(): Unit = {
    val sb = new StrBuilder
    assertEquals(32, sb.capacity)
    assertEquals(0, sb.length)
    assertEquals(0, sb.size)
    assertTrue(sb.isEmpty)
    sb.minimizeCapacity
    assertEquals(0, sb.capacity)
    assertEquals(0, sb.length)
    assertEquals(0, sb.size)
    assertTrue(sb.isEmpty)
    sb.ensureCapacity(32)
    assertTrue(sb.capacity >= 32)
    assertEquals(0, sb.length)
    assertEquals(0, sb.size)
    assertTrue(sb.isEmpty)
    sb.append("foo")
    assertTrue(sb.capacity >= 32)
    assertEquals(3, sb.length)
    assertEquals(3, sb.size)
    assertFalse(sb.isEmpty)
    sb.clear
    assertTrue(sb.capacity >= 32)
    assertEquals(0, sb.length)
    assertEquals(0, sb.size)
    assertTrue(sb.isEmpty)
    sb.append("123456789012345678901234567890123")
    assertTrue(sb.capacity > 32)
    assertEquals(33, sb.length)
    assertEquals(33, sb.size)
    assertFalse(sb.isEmpty)
    sb.ensureCapacity(16)
    assertTrue(sb.capacity > 16)
    assertEquals(33, sb.length)
    assertEquals(33, sb.size)
    assertFalse(sb.isEmpty)
    sb.minimizeCapacity
    assertEquals(33, sb.capacity)
    assertEquals(33, sb.length)
    assertEquals(33, sb.size)
    assertFalse(sb.isEmpty)
    assertThrows[IndexOutOfBoundsException](
      sb.setLength(-1)
    ) //, "setLength(-1) expected StringIndexOutOfBoundsException")
    sb.setLength(33)
    assertEquals(33, sb.capacity)
    assertEquals(33, sb.length)
    assertEquals(33, sb.size)
    assertFalse(sb.isEmpty)
    sb.setLength(16)
    assertTrue(sb.capacity >= 16)
    assertEquals(16, sb.length)
    assertEquals(16, sb.size)
    assertEquals("1234567890123456", sb.toString)
    assertFalse(sb.isEmpty)
    sb.setLength(32)
    assertTrue(sb.capacity >= 32)
    assertEquals(32, sb.length)
    assertEquals(32, sb.size)
    assertEquals(
      "1234567890123456\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000",
      sb.toString)
    assertFalse(sb.isEmpty)
    sb.setLength(0)
    assertTrue(sb.capacity >= 32)
    assertEquals(0, sb.length)
    assertEquals(0, sb.size)
    assertTrue(sb.isEmpty)
  }

  @Test def testLength(): Unit = {
    val sb = new StrBuilder
    assertEquals(0, sb.length)
    sb.append("Hello")
    assertEquals(5, sb.length)
  }

  @Test def testSetLength(): Unit = {
    val sb = new StrBuilder
    sb.append("Hello")
    sb.setLength(2) // shorten
    assertEquals("He", sb.toString)
    sb.setLength(2) // no change
    assertEquals("He", sb.toString)
    sb.setLength(3) // lengthen
    assertEquals("He\u0000", sb.toString)
    assertThrows[IndexOutOfBoundsException](
      sb.setLength(-1)
    ) //, "setLength(-1) expected StringIndexOutOfBoundsException")
    ()
  }

  @Test def testCapacity(): Unit = {
    val sb = new StrBuilder
    assertEquals(sb.buffer.length, sb.capacity)
    sb.append("HelloWorldHelloWorldHelloWorldHelloWorld")
    assertEquals(sb.buffer.length, sb.capacity)
  }

  @Test def testEnsureCapacity(): Unit = {
    val sb = new StrBuilder
    sb.ensureCapacity(2)
    assertTrue(sb.capacity >= 2)
    sb.ensureCapacity(-1)
    assertTrue(sb.capacity >= 0)
    sb.append("HelloWorld")
    sb.ensureCapacity(40)
    assertTrue(sb.capacity >= 40)
  }

  @Test def testMinimizeCapacity(): Unit = {
    val sb = new StrBuilder
    sb.minimizeCapacity
    assertEquals(0, sb.capacity)
    sb.append("HelloWorld")
    sb.minimizeCapacity
    assertEquals(10, sb.capacity)
  }

  @Test def testSize(): Unit = {
    val sb = new StrBuilder
    assertEquals(0, sb.size)
    sb.append("Hello")
    assertEquals(5, sb.size)
  }

  @Test def testIsEmpty(): Unit = {
    val sb = new StrBuilder
    assertTrue(sb.isEmpty)
    sb.append("Hello")
    assertFalse(sb.isEmpty)
    sb.clear
    assertTrue(sb.isEmpty)
  }

  @Test def testClear(): Unit = {
    val sb = new StrBuilder
    sb.append("Hello")
    sb.clear
    assertEquals(0, sb.length)
    assertTrue(sb.buffer.length >= 5)
  }

  @Test def testCharAt(): Unit = {
    val sb = new StrBuilder
    assertThrows[IndexOutOfBoundsException](sb.charAt(0)) //, "charAt(0) expected IndexOutOfBoundsException")
    assertThrows[IndexOutOfBoundsException](sb.charAt(-1)) //, "charAt(-1) expected IndexOutOfBoundsException")
    sb.append("foo")
    assertEquals('f', sb.charAt(0))
    assertEquals('o', sb.charAt(1))
    assertEquals('o', sb.charAt(2))
    assertThrows[IndexOutOfBoundsException](sb.charAt(-1)) //, "charAt(-1) expected IndexOutOfBoundsException")
    assertThrows[IndexOutOfBoundsException](sb.charAt(3)) //, "charAt(3) expected IndexOutOfBoundsException")
    ()
  }

  @Test def testSetCharAt(): Unit = {
    val sb = new StrBuilder
    assertThrows[IndexOutOfBoundsException](
      sb.setCharAt(0, 'f')
    ) //, "setCharAt(0, ) expected IndexOutOfBoundsException")
    assertThrows[IndexOutOfBoundsException](
      sb.setCharAt(-1, 'f')
    ) //, "setCharAt(-1, ) expected IndexOutOfBoundsException")
    sb.append("foo")
    sb.setCharAt(0, 'b')
    sb.setCharAt(1, 'a')
    sb.setCharAt(2, 'r')
    assertThrows[IndexOutOfBoundsException](
      sb.setCharAt(3, '!')
    ) //, "setCharAt(3, ) expected IndexOutOfBoundsException")
    assertEquals("bar", sb.toString)
  }

  @Test def testDeleteCharAt(): Unit = {
    val sb = new StrBuilder("abc")
    sb.deleteCharAt(0)
    assertEquals("bc", sb.toString)
    assertThrows[IndexOutOfBoundsException](sb.deleteCharAt(1000))
    ()
  }

  @Test def testToCharArray(): Unit = {
    val sb = new StrBuilder
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, sb.toCharArray)
    var a = sb.toCharArray
    assertNotNull("toCharArray() result is null", a)
    assertEquals("toCharArray() result is too large", 0, a.length)
    sb.append("junit")
    a = sb.toCharArray
    assertEquals("toCharArray() result incorrect length", 5, a.length)
    assertArrayEquals("toCharArray() result does not match", "junit".toCharArray, a)
  }

  @Test def testToCharArrayIntInt(): Unit = {
    val sb = new StrBuilder
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, sb.toCharArray(0, 0))
    sb.append("junit")
    var a = sb.toCharArray(0, 20) // too large test
    assertEquals("toCharArray(int, int) result incorrect length", 5, a.length)
    assertArrayEquals("toCharArray(int, int) result does not match", "junit".toCharArray, a)
    a = sb.toCharArray(0, 4)
    assertEquals("toCharArray(int, int) result incorrect length", 4, a.length)
    assertArrayEquals("toCharArray(int, int) result does not match", "juni".toCharArray, a)
    a = sb.toCharArray(0, 4)
    assertEquals("toCharArray(int, int) result incorrect length", 4, a.length)
    assertArrayEquals("toCharArray(int, int) result does not match", "juni".toCharArray, a)
    a = sb.toCharArray(0, 1)
    assertNotNull("toCharArray(int, int) result is null", a)
    assertThrows[IndexOutOfBoundsException](sb.toCharArray(-1, 5)) //, "no string index out of bound on -1")
    assertThrows[IndexOutOfBoundsException](sb.toCharArray(6, 5)) //, "no string index out of bound on -1")
    ()
  }

  @Test def testGetChars(): Unit = {
    val sb = new StrBuilder
    var input = new Array[Char](10)
    var a = sb.getChars(input)
    assertSame(input, a)
    assertArrayEquals(new Array[Char](10), a)
    sb.append("junit")
    a = sb.getChars(input)
    assertSame(input, a)
    assertArrayEquals(Array[Char]('j', 'u', 'n', 'i', 't', 0, 0, 0, 0, 0), a)
    a = sb.getChars(null)
    assertNotSame(input, a)
    assertEquals(5, a.length)
    assertArrayEquals("junit".toCharArray, a)
    input = new Array[Char](5)
    a = sb.getChars(input)
    assertSame(input, a)
    input = new Array[Char](4)
    a = sb.getChars(input)
    assertNotSame(input, a)
  }

  @Test def testGetCharsIntIntCharArrayInt(): Unit = {
    val sb = new StrBuilder
    sb.append("junit")
    val a = new Array[Char](5)
    sb.getChars(0, 5, a, 0)
    assertArrayEquals(Array[Char]('j', 'u', 'n', 'i', 't'), a)
    val b = new Array[Char](5)
    sb.getChars(0, 2, b, 3)
    assertArrayEquals(Array[Char](0, 0, 0, 'j', 'u'), b)
    assertThrows[IndexOutOfBoundsException](sb.getChars(-1, 0, b, 0))
    assertThrows[IndexOutOfBoundsException](sb.getChars(0, -1, b, 0))
    assertThrows[IndexOutOfBoundsException](sb.getChars(0, 20, b, 0))
    assertThrows[IndexOutOfBoundsException](sb.getChars(4, 2, b, 0))
    ()
  }

  @Test def testDeleteIntInt(): Unit = {
    val sb = new StrBuilder("abc")
    sb.delete(0, 1)
    assertEquals("bc", sb.toString)
    sb.delete(1, 2)
    assertEquals("b", sb.toString)
    sb.delete(0, 1)
    assertEquals("", sb.toString)
    sb.delete(0, 1000)
    assertEquals("", sb.toString)
    assertThrows[IndexOutOfBoundsException](sb.delete(1, 2))
    assertThrows[IndexOutOfBoundsException](sb.delete(-1, 1))
    assertThrows[IndexOutOfBoundsException](new StrBuilder("anything").delete(2, 1))
    ()
  }

  @Test def testDeleteAll_char(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.deleteAll('X')
    assertEquals("abcbccba", sb.toString)
    sb.deleteAll('a')
    assertEquals("bcbccb", sb.toString)
    sb.deleteAll('c')
    assertEquals("bbb", sb.toString)
    sb.deleteAll('b')
    assertEquals("", sb.toString)
    sb = new StrBuilder("")
    sb.deleteAll('b')
    assertEquals("", sb.toString)
  }

  @Test def testDeleteFirst_char(): Unit = {
    var sb = new StrBuilder("abcba")
    sb.deleteFirst('X')
    assertEquals("abcba", sb.toString)
    sb.deleteFirst('a')
    assertEquals("bcba", sb.toString)
    sb.deleteFirst('c')
    assertEquals("bba", sb.toString)
    sb.deleteFirst('b')
    assertEquals("ba", sb.toString)
    sb = new StrBuilder("")
    sb.deleteFirst('b')
    assertEquals("", sb.toString)
  }

  // -----------------------------------------------------------------------
  @Test def testDeleteAll_String(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.deleteAll(null.asInstanceOf[String])
    assertEquals("abcbccba", sb.toString)
    sb.deleteAll("")
    assertEquals("abcbccba", sb.toString)
    sb.deleteAll("X")
    assertEquals("abcbccba", sb.toString)
    sb.deleteAll("a")
    assertEquals("bcbccb", sb.toString)
    sb.deleteAll("c")
    assertEquals("bbb", sb.toString)
    sb.deleteAll("b")
    assertEquals("", sb.toString)
    sb = new StrBuilder("abcbccba")
    sb.deleteAll("bc")
    assertEquals("acba", sb.toString)
    sb = new StrBuilder("")
    sb.deleteAll("bc")
    assertEquals("", sb.toString)
  }

  @Test def testDeleteFirst_String(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.deleteFirst(null.asInstanceOf[String])
    assertEquals("abcbccba", sb.toString)
    sb.deleteFirst("")
    assertEquals("abcbccba", sb.toString)
    sb.deleteFirst("X")
    assertEquals("abcbccba", sb.toString)
    sb.deleteFirst("a")
    assertEquals("bcbccba", sb.toString)
    sb.deleteFirst("c")
    assertEquals("bbccba", sb.toString)
    sb.deleteFirst("b")
    assertEquals("bccba", sb.toString)
    sb = new StrBuilder("abcbccba")
    sb.deleteFirst("bc")
    assertEquals("abccba", sb.toString)
    sb = new StrBuilder("")
    sb.deleteFirst("bc")
    assertEquals("", sb.toString)
  }

  @Test def testDeleteAll_StrMatcher(): Unit = {
    var sb = new StrBuilder("A0xA1A2yA3")
    sb.deleteAll(null.asInstanceOf[StrMatcher])
    assertEquals("A0xA1A2yA3", sb.toString)
    sb.deleteAll(StrBuilderTest.A_NUMBER_MATCHER)
    assertEquals("xy", sb.toString)
    sb = new StrBuilder("Ax1")
    sb.deleteAll(StrBuilderTest.A_NUMBER_MATCHER)
    assertEquals("Ax1", sb.toString)
    sb = new StrBuilder("")
    sb.deleteAll(StrBuilderTest.A_NUMBER_MATCHER)
    assertEquals("", sb.toString)
  }

  @Test def testDeleteFirst_StrMatcher(): Unit = {
    var sb = new StrBuilder("A0xA1A2yA3")
    sb.deleteFirst(null.asInstanceOf[StrMatcher])
    assertEquals("A0xA1A2yA3", sb.toString)
    sb.deleteFirst(StrBuilderTest.A_NUMBER_MATCHER)
    assertEquals("xA1A2yA3", sb.toString)
    sb = new StrBuilder("Ax1")
    sb.deleteFirst(StrBuilderTest.A_NUMBER_MATCHER)
    assertEquals("Ax1", sb.toString)
    sb = new StrBuilder("")
    sb.deleteFirst(StrBuilderTest.A_NUMBER_MATCHER)
    assertEquals("", sb.toString)
  }

  @Test def testReplace_int_int_String(): Unit = {
    val sb = new StrBuilder("abc")
    sb.replace(0, 1, "d")
    assertEquals("dbc", sb.toString)
    sb.replace(0, 1, "aaa")
    assertEquals("aaabc", sb.toString)
    sb.replace(0, 3, "")
    assertEquals("bc", sb.toString)
    sb.replace(1, 2, null)
    assertEquals("b", sb.toString)
    sb.replace(1, 1000, "text")
    assertEquals("btext", sb.toString)
    sb.replace(0, 1000, "text")
    assertEquals("text", sb.toString)
    val sb1 = new StrBuilder("atext")
    sb1.replace(1, 1, "ny")
    assertEquals("anytext", sb1.toString)
    assertThrows[IndexOutOfBoundsException](sb1.replace(2, 1, "anything"))
    val sb2 = new StrBuilder
    assertThrows[IndexOutOfBoundsException](sb2.replace(1, 2, "anything"))
    assertThrows[IndexOutOfBoundsException](sb2.replace(-1, 1, "anything"))
    ()
  }

  @Test def testReplaceAll_char_char(): Unit = {
    val sb = new StrBuilder("abcbccba")
    sb.replaceAll('x', 'y')
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll('a', 'd')
    assertEquals("dbcbccbd", sb.toString)
    sb.replaceAll('b', 'e')
    assertEquals("dececced", sb.toString)
    sb.replaceAll('c', 'f')
    assertEquals("defeffed", sb.toString)
    sb.replaceAll('d', 'd')
    assertEquals("defeffed", sb.toString)
  }

  @Test def testReplaceFirst_char_char(): Unit = {
    val sb = new StrBuilder("abcbccba")
    sb.replaceFirst('x', 'y')
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst('a', 'd')
    assertEquals("dbcbccba", sb.toString)
    sb.replaceFirst('b', 'e')
    assertEquals("decbccba", sb.toString)
    sb.replaceFirst('c', 'f')
    assertEquals("defbccba", sb.toString)
    sb.replaceFirst('d', 'd')
    assertEquals("defbccba", sb.toString)
  }

  @Test def testReplaceAll_String_String(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.replaceAll(null.asInstanceOf[String], null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll(null.asInstanceOf[String], "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll("", null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll("", "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll("x", "y")
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll("a", "d")
    assertEquals("dbcbccbd", sb.toString)
    sb.replaceAll("d", null)
    assertEquals("bcbccb", sb.toString)
    sb.replaceAll("cb", "-")
    assertEquals("b-c-", sb.toString)
    sb = new StrBuilder("abcba")
    sb.replaceAll("b", "xbx")
    assertEquals("axbxcxbxa", sb.toString)
    sb = new StrBuilder("bb")
    sb.replaceAll("b", "xbx")
    assertEquals("xbxxbx", sb.toString)
  }

  @Test def testReplaceFirst_String_String(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.replaceFirst(null.asInstanceOf[String], null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst(null.asInstanceOf[String], "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst("", null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst("", "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst("x", "y")
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst("a", "d")
    assertEquals("dbcbccba", sb.toString)
    sb.replaceFirst("d", null)
    assertEquals("bcbccba", sb.toString)
    sb.replaceFirst("cb", "-")
    assertEquals("b-ccba", sb.toString)
    sb = new StrBuilder("abcba")
    sb.replaceFirst("b", "xbx")
    assertEquals("axbxcba", sb.toString)
    sb = new StrBuilder("bb")
    sb.replaceFirst("b", "xbx")
    assertEquals("xbxb", sb.toString)
  }

  @Test def testReplaceAll_StrMatcher_String(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.replaceAll(null.asInstanceOf[StrMatcher], null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll(null.asInstanceOf[StrMatcher], "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll(StrMatcher.noneMatcher, null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll(StrMatcher.noneMatcher, "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll(StrMatcher.charMatcher('x'), "y")
    assertEquals("abcbccba", sb.toString)
    sb.replaceAll(StrMatcher.charMatcher('a'), "d")
    assertEquals("dbcbccbd", sb.toString)
    sb.replaceAll(StrMatcher.charMatcher('d'), null)
    assertEquals("bcbccb", sb.toString)
    sb.replaceAll(StrMatcher.stringMatcher("cb"), "-")
    assertEquals("b-c-", sb.toString)
    sb = new StrBuilder("abcba")
    sb.replaceAll(StrMatcher.charMatcher('b'), "xbx")
    assertEquals("axbxcxbxa", sb.toString)
    sb = new StrBuilder("bb")
    sb.replaceAll(StrMatcher.charMatcher('b'), "xbx")
    assertEquals("xbxxbx", sb.toString)
    sb = new StrBuilder("A1-A2A3-A4")
    sb.replaceAll(StrBuilderTest.A_NUMBER_MATCHER, "***")
    assertEquals("***-******-***", sb.toString)
    sb = new StrBuilder("Dear X, hello X.")
    sb.replaceAll(StrMatcher.stringMatcher("X"), "012345678901234567")
    assertEquals("Dear 012345678901234567, hello 012345678901234567.", sb.toString)
  }

  @Test def testReplaceFirst_StrMatcher_String(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.replaceFirst(null.asInstanceOf[StrMatcher], null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst(null.asInstanceOf[StrMatcher], "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst(StrMatcher.noneMatcher, null)
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst(StrMatcher.noneMatcher, "anything")
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst(StrMatcher.charMatcher('x'), "y")
    assertEquals("abcbccba", sb.toString)
    sb.replaceFirst(StrMatcher.charMatcher('a'), "d")
    assertEquals("dbcbccba", sb.toString)
    sb.replaceFirst(StrMatcher.charMatcher('d'), null)
    assertEquals("bcbccba", sb.toString)
    sb.replaceFirst(StrMatcher.stringMatcher("cb"), "-")
    assertEquals("b-ccba", sb.toString)
    sb = new StrBuilder("abcba")
    sb.replaceFirst(StrMatcher.charMatcher('b'), "xbx")
    assertEquals("axbxcba", sb.toString)
    sb = new StrBuilder("bb")
    sb.replaceFirst(StrMatcher.charMatcher('b'), "xbx")
    assertEquals("xbxb", sb.toString)
    sb = new StrBuilder("A1-A2A3-A4")
    sb.replaceFirst(StrBuilderTest.A_NUMBER_MATCHER, "***")
    assertEquals("***-A2A3-A4", sb.toString)
  }

  @Test def testReplace_StrMatcher_String_int_int_int_VaryMatcher(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.replace(null, "x", 0, sb.length, -1)
    assertEquals("abcbccba", sb.toString)
    sb.replace(StrMatcher.charMatcher('a'), "x", 0, sb.length, -1)
    assertEquals("xbcbccbx", sb.toString)
    sb.replace(StrMatcher.stringMatcher("cb"), "x", 0, sb.length, -1)
    assertEquals("xbxcxx", sb.toString)
    sb = new StrBuilder("A1-A2A3-A4")
    sb.replace(StrBuilderTest.A_NUMBER_MATCHER, "***", 0, sb.length, -1)
    assertEquals("***-******-***", sb.toString)
    sb = new StrBuilder
    sb.replace(StrBuilderTest.A_NUMBER_MATCHER, "***", 0, sb.length, -1)
    assertEquals("", sb.toString)
  }

  @Test def testReplace_StrMatcher_String_int_int_int_VaryReplace(): Unit = {
    var sb = new StrBuilder("abcbccba")
    sb.replace(StrMatcher.stringMatcher("cb"), "cb", 0, sb.length, -1)
    assertEquals("abcbccba", sb.toString)
    sb = new StrBuilder("abcbccba")
    sb.replace(StrMatcher.stringMatcher("cb"), "-", 0, sb.length, -1)
    assertEquals("ab-c-a", sb.toString)
    sb = new StrBuilder("abcbccba")
    sb.replace(StrMatcher.stringMatcher("cb"), "+++", 0, sb.length, -1)
    assertEquals("ab+++c+++a", sb.toString)
    sb = new StrBuilder("abcbccba")
    sb.replace(StrMatcher.stringMatcher("cb"), "", 0, sb.length, -1)
    assertEquals("abca", sb.toString)
    sb = new StrBuilder("abcbccba")
    sb.replace(StrMatcher.stringMatcher("cb"), null, 0, sb.length, -1)
    assertEquals("abca", sb.toString)
  }

  @Test def testReplace_StrMatcher_String_int_int_int_VaryStartIndex(): Unit = {
    var sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, sb.length, -1)
    assertEquals("-x--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 1, sb.length, -1)
    assertEquals("aax--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 2, sb.length, -1)
    assertEquals("aax--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 3, sb.length, -1)
    assertEquals("aax--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 4, sb.length, -1)
    assertEquals("aaxa-ay-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 5, sb.length, -1)
    assertEquals("aaxaa-y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 6, sb.length, -1)
    assertEquals("aaxaaaay-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 7, sb.length, -1)
    assertEquals("aaxaaaay-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 8, sb.length, -1)
    assertEquals("aaxaaaay-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 9, sb.length, -1)
    assertEquals("aaxaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 10, sb.length, -1)
    assertEquals("aaxaaaayaa", sb.toString)
    val sb1 = new StrBuilder("aaxaaaayaa")
    assertThrows[IndexOutOfBoundsException](sb1.replace(StrMatcher.stringMatcher("aa"), "-", 11, sb1.length, -1))
    assertEquals("aaxaaaayaa", sb1.toString)
    val sb2 = new StrBuilder("aaxaaaayaa")
    assertThrows[IndexOutOfBoundsException](sb2.replace(StrMatcher.stringMatcher("aa"), "-", -1, sb2.length, -1))
    assertEquals("aaxaaaayaa", sb2.toString)
  }

  @Test def testReplace_StrMatcher_String_int_int_int_VaryEndIndex(): Unit = {
    var sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 0, -1)
    assertEquals("aaxaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 2, -1)
    assertEquals("-xaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 3, -1)
    assertEquals("-xaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 4, -1)
    assertEquals("-xaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 5, -1)
    assertEquals("-x-aayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 6, -1)
    assertEquals("-x-aayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 7, -1)
    assertEquals("-x--yaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 8, -1)
    assertEquals("-x--yaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 9, -1)
    assertEquals("-x--yaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, -1)
    assertEquals("-x--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 1000, -1)
    assertEquals("-x--y-", sb.toString)
    val sb1 = new StrBuilder("aaxaaaayaa")
    assertThrows[IndexOutOfBoundsException](sb1.replace(StrMatcher.stringMatcher("aa"), "-", 2, 1, -1))
    assertEquals("aaxaaaayaa", sb1.toString)
  }

  @Test def testReplace_StrMatcher_String_int_int_int_VaryCount(): Unit = {
    var sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, -1)
    assertEquals("-x--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, 0)
    assertEquals("aaxaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, 1)
    assertEquals("-xaaaayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, 2)
    assertEquals("-x-aayaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, 3)
    assertEquals("-x--yaa", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, 4)
    assertEquals("-x--y-", sb.toString)
    sb = new StrBuilder("aaxaaaayaa")
    sb.replace(StrMatcher.stringMatcher("aa"), "-", 0, 10, 5)
    assertEquals("-x--y-", sb.toString)
  }

  @Test def testReverse(): Unit = {
    val sb = new StrBuilder
    assertEquals("", sb.reverse.toString)
    sb.clear.append(true)
    assertEquals("eurt", sb.reverse.toString)
    assertEquals("true", sb.reverse.toString)
  }

  @Test def testTrim(): Unit = {
    val sb = new StrBuilder
    assertEquals("", sb.reverse.toString)
    sb.clear.append(" \u0000 ")
    assertEquals("", sb.trim.toString)
    sb.clear.append(" \u0000 a b c")
    assertEquals("a b c", sb.trim.toString)
    sb.clear.append("a b c \u0000 ")
    assertEquals("a b c", sb.trim.toString)
    sb.clear.append(" \u0000 a b c \u0000 ")
    assertEquals("a b c", sb.trim.toString)
    sb.clear.append("a b c")
    assertEquals("a b c", sb.trim.toString)
  }

  @Test def testStartsWith(): Unit = {
    val sb = new StrBuilder
    assertFalse(sb.startsWith("a"))
    assertFalse(sb.startsWith(null))
    assertTrue(sb.startsWith(""))
    sb.append("abc")
    assertTrue(sb.startsWith("a"))
    assertTrue(sb.startsWith("ab"))
    assertTrue(sb.startsWith("abc"))
    assertFalse(sb.startsWith("cba"))
  }

  @Test def testEndsWith(): Unit = {
    val sb = new StrBuilder
    assertFalse(sb.endsWith("a"))
    assertFalse(sb.endsWith("c"))
    assertTrue(sb.endsWith(""))
    assertFalse(sb.endsWith(null))
    sb.append("abc")
    assertTrue(sb.endsWith("c"))
    assertTrue(sb.endsWith("bc"))
    assertTrue(sb.endsWith("abc"))
    assertFalse(sb.endsWith("cba"))
    assertFalse(sb.endsWith("abcd"))
    assertFalse(sb.endsWith(" abc"))
    assertFalse(sb.endsWith("abc "))
  }

  @Test def testSubSequenceIntInt(): Unit = {
    val sb = new StrBuilder("hello goodbye")
    // Start index is negative
    assertThrows[IndexOutOfBoundsException](sb.subSequence(-1, 5))
    // End index is negative
    assertThrows[IndexOutOfBoundsException](sb.subSequence(2, -1))
    // End index greater than length()
    assertThrows[IndexOutOfBoundsException](sb.subSequence(2, sb.length + 1))
    // Start index greater then end index
    assertThrows[IndexOutOfBoundsException](sb.subSequence(3, 2))
    // Normal cases
    assertEquals("hello", sb.subSequence(0, 5))
    assertEquals("hello goodbye".subSequence(0, 6), sb.subSequence(0, 6))
    assertEquals("goodbye", sb.subSequence(6, 13))
    assertEquals("hello goodbye".subSequence(6, 13), sb.subSequence(6, 13))
  }

  @Test def testSubstringInt(): Unit = {
    val sb = new StrBuilder("hello goodbye")
    assertEquals("goodbye", sb.substring(6))
    assertEquals("hello goodbye".substring(6), sb.substring(6))
    assertEquals("hello goodbye", sb.substring(0))
    assertEquals("hello goodbye".substring(0), sb.substring(0))
    assertThrows[IndexOutOfBoundsException](sb.substring(-1))
    assertThrows[IndexOutOfBoundsException](sb.substring(15))
    ()
  }

  @Test def testSubstringIntInt(): Unit = {
    val sb = new StrBuilder("hello goodbye")
    assertEquals("hello", sb.substring(0, 5))
    assertEquals("hello goodbye".substring(0, 6), sb.substring(0, 6))
    assertEquals("goodbye", sb.substring(6, 13))
    assertEquals("hello goodbye".substring(6, 13), sb.substring(6, 13))
    assertEquals("goodbye", sb.substring(6, 20))
    assertThrows[IndexOutOfBoundsException](sb.substring(-1, 5))
    assertThrows[IndexOutOfBoundsException](sb.substring(15, 20))
    ()
  }

  @Test def testMidString(): Unit = {
    val sb = new StrBuilder("hello goodbye hello")
    assertEquals("goodbye", sb.midString(6, 7))
    assertEquals("hello", sb.midString(0, 5))
    assertEquals("hello", sb.midString(-5, 5))
    assertEquals("", sb.midString(0, -1))
    assertEquals("", sb.midString(20, 2))
    assertEquals("hello", sb.midString(14, 22))
  }

  @Test def testRightString(): Unit = {
    val sb = new StrBuilder("left right")
    assertEquals("right", sb.rightString(5))
    assertEquals("", sb.rightString(0))
    assertEquals("", sb.rightString(-5))
    assertEquals("left right", sb.rightString(15))
  }

  @Test def testLeftString(): Unit = {
    val sb = new StrBuilder("left right")
    assertEquals("left", sb.leftString(4))
    assertEquals("", sb.leftString(0))
    assertEquals("", sb.leftString(-5))
    assertEquals("left right", sb.leftString(15))
  }

  @Test def testContains_char(): Unit = {
    val sb = new StrBuilder("abcdefghijklmnopqrstuvwxyz")
    assertTrue(sb.contains('a'))
    assertTrue(sb.contains('o'))
    assertTrue(sb.contains('z'))
    assertFalse(sb.contains('1'))
  }

  @Test def testContains_String(): Unit = {
    val sb = new StrBuilder("abcdefghijklmnopqrstuvwxyz")
    assertTrue(sb.contains("a"))
    assertTrue(sb.contains("pq"))
    assertTrue(sb.contains("z"))
    assertFalse(sb.contains("zyx"))
    assertFalse(sb.contains(null.asInstanceOf[String]))
  }

  @Test def testContains_StrMatcher(): Unit = {
    var sb = new StrBuilder("abcdefghijklmnopqrstuvwxyz")
    assertTrue(sb.contains(StrMatcher.charMatcher('a')))
    assertTrue(sb.contains(StrMatcher.stringMatcher("pq")))
    assertTrue(sb.contains(StrMatcher.charMatcher('z')))
    assertFalse(sb.contains(StrMatcher.stringMatcher("zy")))
    assertFalse(sb.contains(null.asInstanceOf[StrMatcher]))
    sb = new StrBuilder
    assertFalse(sb.contains(StrBuilderTest.A_NUMBER_MATCHER))
    sb.append("B A1 C")
    assertTrue(sb.contains(StrBuilderTest.A_NUMBER_MATCHER))
  }

  @Test def testIndexOf_char(): Unit = {
    val sb = new StrBuilder("abab")
    assertEquals(0, sb.indexOf('a'))
    // should work like String#indexOf
    assertEquals("abab".indexOf('a'), sb.indexOf('a'))
    assertEquals(1, sb.indexOf('b'))
    assertEquals("abab".indexOf('b'), sb.indexOf('b'))
    assertEquals(-1, sb.indexOf('z'))
  }

  @Test def testIndexOf_char_int(): Unit = {
    var sb = new StrBuilder("abab")
    assertEquals(0, sb.indexOf('a', -1))
    assertEquals(0, sb.indexOf('a', 0))
    assertEquals(2, sb.indexOf('a', 1))
    assertEquals(-1, sb.indexOf('a', 4))
    assertEquals(-1, sb.indexOf('a', 5))
    assertEquals("abab".indexOf('a', 1), sb.indexOf('a', 1))
    assertEquals(3, sb.indexOf('b', 2))
    assertEquals("abab".indexOf('b', 2), sb.indexOf('b', 2))
    assertEquals(-1, sb.indexOf('z', 2))
    sb = new StrBuilder("xyzabc")
    assertEquals(2, sb.indexOf('z', 0))
    assertEquals(-1, sb.indexOf('z', 3))
  }

  @Test def testLastIndexOf_char(): Unit = {
    val sb = new StrBuilder("abab")
    assertEquals(2, sb.lastIndexOf('a'))
    //should work like String#lastIndexOf
    assertEquals("abab".lastIndexOf('a'), sb.lastIndexOf('a'))
    assertEquals(3, sb.lastIndexOf('b'))
    assertEquals("abab".lastIndexOf('b'), sb.lastIndexOf('b'))
    assertEquals(-1, sb.lastIndexOf('z'))
  }

  @Test def testLastIndexOf_char_int(): Unit = {
    var sb = new StrBuilder("abab")
    assertEquals(-1, sb.lastIndexOf('a', -1))
    assertEquals(0, sb.lastIndexOf('a', 0))
    assertEquals(0, sb.lastIndexOf('a', 1))
    // should work like String#lastIndexOf
    assertEquals("abab".lastIndexOf('a', 1), sb.lastIndexOf('a', 1))
    assertEquals(1, sb.lastIndexOf('b', 2))
    assertEquals("abab".lastIndexOf('b', 2), sb.lastIndexOf('b', 2))
    assertEquals(-1, sb.lastIndexOf('z', 2))
    sb = new StrBuilder("xyzabc")
    assertEquals(2, sb.lastIndexOf('z', sb.length))
    assertEquals(-1, sb.lastIndexOf('z', 1))
  }

  @Test def testIndexOf_String(): Unit = {
    val sb = new StrBuilder("abab")
    assertEquals(0, sb.indexOf("a"))
    //should work like String#indexOf
    assertEquals("abab".indexOf("a"), sb.indexOf("a"))
    assertEquals(0, sb.indexOf("ab"))
    assertEquals("abab".indexOf("ab"), sb.indexOf("ab"))
    assertEquals(1, sb.indexOf("b"))
    assertEquals("abab".indexOf("b"), sb.indexOf("b"))
    assertEquals(1, sb.indexOf("ba"))
    assertEquals("abab".indexOf("ba"), sb.indexOf("ba"))
    assertEquals(-1, sb.indexOf("z"))
    assertEquals(-1, sb.indexOf(null.asInstanceOf[String]))
  }

  @Test def testIndexOf_String_int(): Unit = {
    var sb = new StrBuilder("abab")
    assertEquals(0, sb.indexOf("a", -1))
    assertEquals(0, sb.indexOf("a", 0))
    assertEquals(2, sb.indexOf("a", 1))
    assertEquals(2, sb.indexOf("a", 2))
    assertEquals(-1, sb.indexOf("a", 3))
    assertEquals(-1, sb.indexOf("a", 4))
    assertEquals(-1, sb.indexOf("a", 5))
    assertEquals(-1, sb.indexOf("abcdef", 0))
    assertEquals(0, sb.indexOf("", 0))
    assertEquals(1, sb.indexOf("", 1))
    assertEquals("abab".indexOf("a", 1), sb.indexOf("a", 1))
    assertEquals(2, sb.indexOf("ab", 1))
    assertEquals("abab".indexOf("ab", 1), sb.indexOf("ab", 1))
    assertEquals(3, sb.indexOf("b", 2))
    assertEquals("abab".indexOf("b", 2), sb.indexOf("b", 2))
    assertEquals(1, sb.indexOf("ba", 1))
    assertEquals("abab".indexOf("ba", 2), sb.indexOf("ba", 2))
    assertEquals(-1, sb.indexOf("z", 2))
    sb = new StrBuilder("xyzabc")
    assertEquals(2, sb.indexOf("za", 0))
    assertEquals(-1, sb.indexOf("za", 3))
    assertEquals(-1, sb.indexOf(null.asInstanceOf[String], 2))
  }

  @Test def testLastIndexOf_String(): Unit = {
    val sb = new StrBuilder("abab")
    assertEquals(2, sb.lastIndexOf("a"))
    assertEquals("abab".lastIndexOf("a"), sb.lastIndexOf("a"))
    assertEquals(2, sb.lastIndexOf("ab"))
    assertEquals("abab".lastIndexOf("ab"), sb.lastIndexOf("ab"))
    assertEquals(3, sb.lastIndexOf("b"))
    assertEquals("abab".lastIndexOf("b"), sb.lastIndexOf("b"))
    assertEquals(1, sb.lastIndexOf("ba"))
    assertEquals("abab".lastIndexOf("ba"), sb.lastIndexOf("ba"))
    assertEquals(-1, sb.lastIndexOf("z"))
    assertEquals(-1, sb.lastIndexOf(null.asInstanceOf[String]))
  }

  @Test def testLastIndexOf_String_int(): Unit = {
    var sb = new StrBuilder("abab")
    assertEquals(-1, sb.lastIndexOf("a", -1))
    assertEquals(0, sb.lastIndexOf("a", 0))
    assertEquals(0, sb.lastIndexOf("a", 1))
    assertEquals(2, sb.lastIndexOf("a", 2))
    assertEquals(2, sb.lastIndexOf("a", 3))
    assertEquals(2, sb.lastIndexOf("a", 4))
    assertEquals(2, sb.lastIndexOf("a", 5))
    assertEquals(-1, sb.lastIndexOf("abcdef", 3))
    assertEquals("abab".lastIndexOf("", 3), sb.lastIndexOf("", 3))
    assertEquals("abab".lastIndexOf("", 1), sb.lastIndexOf("", 1))
    assertEquals("abab".lastIndexOf("a", 1), sb.lastIndexOf("a", 1))
    assertEquals(0, sb.lastIndexOf("ab", 1))
    assertEquals("abab".lastIndexOf("ab", 1), sb.lastIndexOf("ab", 1))
    assertEquals(1, sb.lastIndexOf("b", 2))
    assertEquals("abab".lastIndexOf("b", 2), sb.lastIndexOf("b", 2))
    assertEquals(1, sb.lastIndexOf("ba", 2))
    assertEquals("abab".lastIndexOf("ba", 2), sb.lastIndexOf("ba", 2))
    assertEquals(-1, sb.lastIndexOf("z", 2))
    sb = new StrBuilder("xyzabc")
    assertEquals(2, sb.lastIndexOf("za", sb.length))
    assertEquals(-1, sb.lastIndexOf("za", 1))
    assertEquals(-1, sb.lastIndexOf(null.asInstanceOf[String], 2))
  }

  @Test def testIndexOf_StrMatcher(): Unit = {
    val sb = new StrBuilder
    assertEquals(-1, sb.indexOf(null.asInstanceOf[StrMatcher]))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('a')))
    sb.append("ab bd")
    assertEquals(0, sb.indexOf(StrMatcher.charMatcher('a')))
    assertEquals(1, sb.indexOf(StrMatcher.charMatcher('b')))
    assertEquals(2, sb.indexOf(StrMatcher.spaceMatcher))
    assertEquals(4, sb.indexOf(StrMatcher.charMatcher('d')))
    assertEquals(-1, sb.indexOf(StrMatcher.noneMatcher))
    assertEquals(-1, sb.indexOf(null.asInstanceOf[StrMatcher]))
    sb.append(" A1 junction")
    assertEquals(6, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER))
  }

  @Test def testIndexOf_StrMatcher_int(): Unit = {
    val sb = new StrBuilder
    assertEquals(-1, sb.indexOf(null.asInstanceOf[StrMatcher], 2))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('a'), 2))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('a'), 0))
    sb.append("ab bd")
    assertEquals(0, sb.indexOf(StrMatcher.charMatcher('a'), -2))
    assertEquals(0, sb.indexOf(StrMatcher.charMatcher('a'), 0))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('a'), 2))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('a'), 20))
    assertEquals(1, sb.indexOf(StrMatcher.charMatcher('b'), -1))
    assertEquals(1, sb.indexOf(StrMatcher.charMatcher('b'), 0))
    assertEquals(1, sb.indexOf(StrMatcher.charMatcher('b'), 1))
    assertEquals(3, sb.indexOf(StrMatcher.charMatcher('b'), 2))
    assertEquals(3, sb.indexOf(StrMatcher.charMatcher('b'), 3))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('b'), 4))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('b'), 5))
    assertEquals(-1, sb.indexOf(StrMatcher.charMatcher('b'), 6))
    assertEquals(2, sb.indexOf(StrMatcher.spaceMatcher, -2))
    assertEquals(2, sb.indexOf(StrMatcher.spaceMatcher, 0))
    assertEquals(2, sb.indexOf(StrMatcher.spaceMatcher, 2))
    assertEquals(-1, sb.indexOf(StrMatcher.spaceMatcher, 4))
    assertEquals(-1, sb.indexOf(StrMatcher.spaceMatcher, 20))
    assertEquals(-1, sb.indexOf(StrMatcher.noneMatcher, 0))
    assertEquals(-1, sb.indexOf(null.asInstanceOf[StrMatcher], 0))
    sb.append(" A1 junction with A2")
    assertEquals(6, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER, 5))
    assertEquals(6, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER, 6))
    assertEquals(23, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER, 7))
    assertEquals(23, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER, 22))
    assertEquals(23, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER, 23))
    assertEquals(-1, sb.indexOf(StrBuilderTest.A_NUMBER_MATCHER, 24))
  }

  @Test def testLastIndexOf_StrMatcher(): Unit = {
    val sb = new StrBuilder
    assertEquals(-1, sb.lastIndexOf(null.asInstanceOf[StrMatcher]))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('a')))
    sb.append("ab bd")
    assertEquals(0, sb.lastIndexOf(StrMatcher.charMatcher('a')))
    assertEquals(3, sb.lastIndexOf(StrMatcher.charMatcher('b')))
    assertEquals(2, sb.lastIndexOf(StrMatcher.spaceMatcher))
    assertEquals(4, sb.lastIndexOf(StrMatcher.charMatcher('d')))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.noneMatcher))
    assertEquals(-1, sb.lastIndexOf(null.asInstanceOf[StrMatcher]))
    sb.append(" A1 junction")
    assertEquals(6, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER))
  }

  @Test def testLastIndexOf_StrMatcher_int(): Unit = {
    val sb = new StrBuilder
    assertEquals(-1, sb.lastIndexOf(null.asInstanceOf[StrMatcher], 2))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('a'), 2))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('a'), 0))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('a'), -1))
    sb.append("ab bd")
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('a'), -2))
    assertEquals(0, sb.lastIndexOf(StrMatcher.charMatcher('a'), 0))
    assertEquals(0, sb.lastIndexOf(StrMatcher.charMatcher('a'), 2))
    assertEquals(0, sb.lastIndexOf(StrMatcher.charMatcher('a'), 20))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('b'), -1))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.charMatcher('b'), 0))
    assertEquals(1, sb.lastIndexOf(StrMatcher.charMatcher('b'), 1))
    assertEquals(1, sb.lastIndexOf(StrMatcher.charMatcher('b'), 2))
    assertEquals(3, sb.lastIndexOf(StrMatcher.charMatcher('b'), 3))
    assertEquals(3, sb.lastIndexOf(StrMatcher.charMatcher('b'), 4))
    assertEquals(3, sb.lastIndexOf(StrMatcher.charMatcher('b'), 5))
    assertEquals(3, sb.lastIndexOf(StrMatcher.charMatcher('b'), 6))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.spaceMatcher, -2))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.spaceMatcher, 0))
    assertEquals(2, sb.lastIndexOf(StrMatcher.spaceMatcher, 2))
    assertEquals(2, sb.lastIndexOf(StrMatcher.spaceMatcher, 4))
    assertEquals(2, sb.lastIndexOf(StrMatcher.spaceMatcher, 20))
    assertEquals(-1, sb.lastIndexOf(StrMatcher.noneMatcher, 0))
    assertEquals(-1, sb.lastIndexOf(null.asInstanceOf[StrMatcher], 0))
    sb.append(" A1 junction with A2")
    assertEquals(-1, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER, 5))
    assertEquals(-1, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER, 6)) // A matches, 1 is outside bounds
    assertEquals(6, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER, 7))
    assertEquals(6, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER, 22))
    assertEquals(6, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER, 23)) // A matches, 2 is outside bounds
    assertEquals(23, sb.lastIndexOf(StrBuilderTest.A_NUMBER_MATCHER, 24))
  }

  @Test def testAsTokenizer(): Unit = {
    // from Javadoc
    val b = new StrBuilder
    b.append("a b ")
    val t = b.asTokenizer
    val tokens1 = t.getTokenArray
    assertEquals(2, tokens1.length)
    assertEquals("a", tokens1(0))
    assertEquals("b", tokens1(1))
    assertEquals(2, t.size)
    b.append("c d ")
    val tokens2 = t.getTokenArray
    assertEquals(2, tokens2.length)
    assertEquals("a", tokens2(0))
    assertEquals("b", tokens2(1))
    assertEquals(2, t.size)
    assertEquals("a", t.next)
    assertEquals("b", t.next)
    t.reset
    val tokens3 = t.getTokenArray
    assertEquals(4, tokens3.length)
    assertEquals("a", tokens3(0))
    assertEquals("b", tokens3(1))
    assertEquals("c", tokens3(2))
    assertEquals("d", tokens3(3))
    assertEquals(4, t.size)
    assertEquals("a", t.next)
    assertEquals("b", t.next)
    assertEquals("c", t.next)
    assertEquals("d", t.next)
    assertEquals("a b c d ", t.getContent)
  }

  @Test
  @throws[Exception]
  def testAsReader(): Unit = {
    val sb = new StrBuilder("some text")
    var reader = sb.asReader
    assertTrue(reader.ready)
    val buf = new Array[Char](40)
    assertEquals(9, reader.read(buf))
    assertEquals("some text", new String(buf, 0, 9))
    assertEquals(-1, reader.read)
    assertFalse(reader.ready)
    assertEquals(0, reader.skip(2))
    assertEquals(0, reader.skip(-1))
    assertTrue(reader.markSupported)
    reader = sb.asReader
    assertEquals('s', reader.read)
    reader.mark(-1)
    var array = new Array[Char](3)
    assertEquals(3, reader.read(array, 0, 3))
    assertEquals('o', array(0))
    assertEquals('m', array(1))
    assertEquals('e', array(2))
    reader.reset()
    assertEquals(1, reader.read(array, 1, 1))
    assertEquals('o', array(0))
    assertEquals('o', array(1))
    assertEquals('e', array(2))
    assertEquals(2, reader.skip(2))
    assertEquals(' ', reader.read)
    assertTrue(reader.ready)
    reader.close()
    assertTrue(reader.ready)
    val r = sb.asReader
    val arr = new Array[Char](3)
    assertThrows[IndexOutOfBoundsException](r.read(arr, -1, 0))
    assertThrows[IndexOutOfBoundsException](r.read(arr, 0, -1))
    assertThrows[IndexOutOfBoundsException](r.read(arr, 100, 1))
    assertThrows[IndexOutOfBoundsException](r.read(arr, 0, 100))
    assertThrows[IndexOutOfBoundsException](r.read(arr, Integer.MAX_VALUE, Integer.MAX_VALUE))
    assertEquals(0, r.read(arr, 0, 0))
    assertEquals(0, arr(0))
    assertEquals(0, arr(1))
    assertEquals(0, arr(2))
    r.skip(9)
    assertEquals(-1, r.read(arr, 0, 1))
    r.reset()
    array = new Array[Char](30)
    assertEquals(9, r.read(array, 0, 30))
  }

  @Test
  @throws[Exception]
  def testAsWriter(): Unit = {
    val sb = new StrBuilder("base")
    val writer = sb.asWriter
    writer.write('l')
    assertEquals("basel", sb.toString)
    writer.write(Array[Char]('i', 'n'))
    assertEquals("baselin", sb.toString)
    writer.write(Array[Char]('n', 'e', 'r'), 1, 2)
    assertEquals("baseliner", sb.toString)
    writer.write(" rout")
    assertEquals("baseliner rout", sb.toString)
    writer.write("ping that server", 1, 3)
    assertEquals("baseliner routing", sb.toString)
    writer.flush() // no effect
    assertEquals("baseliner routing", sb.toString)
    writer.close()
    assertEquals("baseliner routing", sb.toString)
    writer.write(" hi") // works after close
    assertEquals("baseliner routing hi", sb.toString)
    sb.setLength(4) // mix and match
    writer.write('d')
    assertEquals("based", sb.toString)
  }

  @Test def testEqualsIgnoreCase(): Unit = {
    val sb1 = new StrBuilder
    val sb2 = new StrBuilder
    assertTrue(sb1.equalsIgnoreCase(sb1))
    assertTrue(sb1.equalsIgnoreCase(sb2))
    assertTrue(sb2.equalsIgnoreCase(sb2))
    sb1.append("abc")
    assertFalse(sb1.equalsIgnoreCase(sb2))
    sb2.append("ABC")
    assertTrue(sb1.equalsIgnoreCase(sb2))
    sb2.clear.append("abc")
    assertTrue(sb1.equalsIgnoreCase(sb2))
    assertTrue(sb1.equalsIgnoreCase(sb1))
    assertTrue(sb2.equalsIgnoreCase(sb2))
    sb2.clear.append("aBc")
    assertTrue(sb1.equalsIgnoreCase(sb2))
  }

  @Test def testEquals(): Unit = {
    val sb1 = new StrBuilder
    val sb2 = new StrBuilder
    assertTrue(sb1 == sb2)
    assertTrue(sb1 == sb1)
    assertTrue(sb2 == sb2)
    assertEquals(sb1, sb2.asInstanceOf[Any])
    sb1.append("abc")
    assertFalse(sb1 == sb2)
    assertNotEquals(sb1, sb2.asInstanceOf[Any])
    sb2.append("ABC")
    assertFalse(sb1 == sb2)
    assertNotEquals(sb1, sb2.asInstanceOf[Any])
    sb2.clear.append("abc")
    assertTrue(sb1 == sb2)
    assertEquals(sb1, sb2.asInstanceOf[Any])
    assertNotEquals(sb1, Integer.valueOf(1))
    assertNotEquals("abc", sb1)
  }

  @Test def test_LANG_1131_EqualsWithNullStrBuilder(): Unit = {
    val sb = new StrBuilder
    val other = null
    assertFalse(sb == other)
  }

  @Test def testHashCode(): Unit = {
    val sb = new StrBuilder
    val hc1a = sb.hashCode
    val hc1b = sb.hashCode
    assertEquals(0, hc1a)
    assertEquals(hc1a, hc1b)
    sb.append("abc")
    val hc2a = sb.hashCode
    val hc2b = sb.hashCode
    assertTrue(hc2a != 0)
    assertEquals(hc2a, hc2b)
  }

  @Test def testToString(): Unit = {
    val sb = new StrBuilder("abc")
    assertEquals("abc", sb.toString)
  }

  @Test def testToStringBuffer(): Unit = {
    val sb = new StrBuilder
    assertEquals(new StringBuffer().toString, sb.toStringBuffer.toString)
    sb.append("junit")
    assertEquals(new StringBuffer("junit").toString, sb.toStringBuffer.toString)
  }

  @Test def testToStringBuilder(): Unit = {
    val sb = new StrBuilder
    assertEquals(new StringBuilder().toString, sb.toStringBuilder.toString)
    sb.append("junit")
    assertEquals(new StringBuilder("junit").toString, sb.toStringBuilder.toString)
  }

  @Test def testLang294(): Unit = {
    val sb = new StrBuilder("\n%BLAH%\nDo more stuff\neven more stuff\n%BLAH%\n")
    sb.deleteAll("\n%BLAH%")
    assertEquals("\nDo more stuff\neven more stuff\n", sb.toString)
  }

  @Test def testIndexOfLang294(): Unit = {
    val sb = new StrBuilder("onetwothree")
    sb.deleteFirst("three")
    assertEquals(-1, sb.indexOf("three"))
  }

  @Test def testLang295(): Unit = {
    val sb = new StrBuilder("onetwothree")
    sb.deleteFirst("three")
    assertFalse("The contains(char) method is looking beyond the end of the string", sb.contains('h'))
    assertEquals("The indexOf(char) method is looking beyond the end of the string", -1, sb.indexOf('h'))
  }

  @Test def testLang412Right(): Unit = {
    val sb = new StrBuilder
    sb.appendFixedWidthPadRight(null, 10, '*')
    assertEquals("Failed to invoke appendFixedWidthPadRight correctly", "**********", sb.toString)
  }

  @Test def testLang412Left(): Unit = {
    val sb = new StrBuilder
    sb.appendFixedWidthPadLeft(null, 10, '*')
    assertEquals("Failed to invoke appendFixedWidthPadLeft correctly", "**********", sb.toString)
  }

  @Test def testAsBuilder(): Unit = {
    val sb = new StrBuilder().appendAll("Lorem", " ", "ipsum", " ", "dolor")
    assertEquals(sb.toString, sb.build)
  }

  @Test def testAppendCharBuffer(): Unit = {
    val sb1 = new StrBuilder
    val buf = CharBuffer.allocate(10)
    buf.append("0123456789")
    buf.flip
    sb1.append(buf)
    assertEquals("0123456789", sb1.toString)
    val sb2 = new StrBuilder
    sb2.append(buf, 1, 8)
    assertEquals("12345678", sb2.toString)
  }

  @Test
  @throws[Exception]
  def testAppendToWriter(): Unit = {
    val sb = new StrBuilder("1234567890")
    val writer = new StringWriter
    writer.append("Test ")
    sb.appendTo(writer)
    assertEquals("Test 1234567890", writer.toString)
  }

  @Test
  @throws[Exception]
  def testAppendToStringBuilder(): Unit = {
    val sb = new StrBuilder("1234567890")
    val builder = new java.lang.StringBuilder("Test ")
    sb.appendTo(builder)
    assertEquals("Test 1234567890", builder.toString)
  }

  @Test
  @throws[Exception]
  def testAppendToStringBuffer(): Unit = {
    val sb = new StrBuilder("1234567890")
    val buffer = new java.lang.StringBuffer("Test ")
    sb.appendTo(buffer)
    assertEquals("Test 1234567890", buffer.toString)
  }

  @Test
  @throws[Exception]
  def testAppendToCharBuffer(): Unit = {
    val sb = new StrBuilder("1234567890")
    val text = "Test "
    val buffer = CharBuffer.allocate(sb.size + text.length)
    buffer.put(text)
    sb.appendTo(buffer)
    buffer.flip
    assertEquals("Test 1234567890", buffer.toString)
  }
}
