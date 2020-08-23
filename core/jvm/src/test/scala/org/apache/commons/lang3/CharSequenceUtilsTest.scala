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

import java.util.Random
import java.util.stream.IntStream
import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * Tests CharSequenceUtils
  */
object CharSequenceUtilsTest {

  private[lang3] class TestData {
    final private[lang3] var source: String = null
    final private[lang3] var ignoreCase: Boolean = false
    final private[lang3] var toffset: Int = 0
    final private[lang3] var other: String = null
    final private[lang3] var ooffset: Int = 0
    final private[lang3] var len: Int = 0
    final private[lang3] var expected: Boolean = false
    final private[lang3] var throwable: Class[_ <: Throwable] = null

    def this(
      source: String,
      ignoreCase: Boolean,
      toffset: Int,
      other: String,
      ooffset: Int,
      len: Int,
      expected: Boolean) {
      this()
      this.source = source
      this.ignoreCase = ignoreCase
      this.toffset = toffset
      this.other = other
      this.ooffset = ooffset
      this.len = len
      this.expected = expected
      this.throwable = null
    }

    def this(
      source: String,
      ignoreCase: Boolean,
      toffset: Int,
      other: String,
      ooffset: Int,
      len: Int,
      throwable: Class[_ <: Throwable]) {
      this()
      this.source = source
      this.ignoreCase = ignoreCase
      this.toffset = toffset
      this.other = other
      this.ooffset = ooffset
      this.len = len
      this.expected = false
      this.throwable = throwable
    }

    override def toString: String = {
      val sb = new StringBuilder
      sb.append(source).append("[").append(toffset).append("]")
      sb.append(
        if (ignoreCase) " caseblind "
        else " samecase ")
      sb.append(other).append("[").append(ooffset).append("]")
      sb.append(" ").append(len).append(" => ")
      if (throwable != null) sb.append(throwable)
      else sb.append(expected)
      sb.toString
    }
  }

  private val TEST_DATA = Array(
    new CharSequenceUtilsTest.TestData("", true, -1, "", -1, -1, false),
    new CharSequenceUtilsTest.TestData("", true, 0, "", 0, 1, false),
    new CharSequenceUtilsTest.TestData("a", true, 0, "abc", 0, 0, true),
    new CharSequenceUtilsTest.TestData("a", true, 0, "abc", 0, 1, true),
    new CharSequenceUtilsTest.TestData("a", true, 0, null, 0, 0, classOf[NullPointerException]),
    new CharSequenceUtilsTest.TestData(null, true, 0, null, 0, 0, classOf[NullPointerException]),
    new CharSequenceUtilsTest.TestData(null, true, 0, "", 0, 0, classOf[NullPointerException]),
    new CharSequenceUtilsTest.TestData("Abc", true, 0, "abc", 0, 3, true),
    new CharSequenceUtilsTest.TestData("Abc", false, 0, "abc", 0, 3, false),
    new CharSequenceUtilsTest.TestData("Abc", true, 1, "abc", 1, 2, true),
    new CharSequenceUtilsTest.TestData("Abc", false, 1, "abc", 1, 2, true),
    new CharSequenceUtilsTest.TestData("Abcd", true, 1, "abcD", 1, 2, true),
    new CharSequenceUtilsTest.TestData("Abcd", false, 1, "abcD", 1, 2, true)
  )

  abstract private class RunTest { self =>
    private[lang3] def invoke: Any

    private[lang3] def run(data: CharSequenceUtilsTest.TestData, id: String): Unit = {
      if (data.throwable != null) {
        //assertThrows(self.invoke)

//        import org.junit.function.ThrowingRunnable
//        // , id + " Expected " + data.throwable)
//        org.junit.Assert.assertThrows(
//          data.throwable,
//          new ThrowingRunnable() {
//            @throws[Throwable]
//            override def run(): Unit = {
//              self.invoke
//              ()
//            }
//          })
      } else {
        val stringCheck = self.invoke
        assertEquals(id + " Failed test " + data, data.expected, stringCheck)
      }
      ()
    }
  }

  private[lang3] class WrapperString private[lang3] (val inner: CharSequence) extends CharSequence {
    override def length: Int = inner.length

    override def charAt(index: Int): Char = inner.charAt(index)

    override def subSequence(start: Int, `end`: Int): CharSequence = inner.subSequence(start, `end`)

    override def toString: String = inner.toString

    override def chars: IntStream = inner.chars

    override def codePoints: IntStream = inner.codePoints
  }

}

class CharSequenceUtilsTest {
//  @Test def testConstructor(): Unit = {
//    assertNotNull(new CharSequenceUtils.type)
//    val cons = classOf[CharSequenceUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[CharSequenceUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[CharSequenceUtils.type].getModifiers))
//  }

  @Test def testSubSequence(): Unit = { //
    // null input
    assertNull(CharSequenceUtils.subSequence(null, -1))
    assertNull(CharSequenceUtils.subSequence(null, 0))
    assertNull(CharSequenceUtils.subSequence(null, 1))
    // non-null input
    assertEquals(StringUtils.EMPTY, CharSequenceUtils.subSequence(StringUtils.EMPTY, 0))
    assertEquals("012", CharSequenceUtils.subSequence("012", 0))
    assertEquals("12", CharSequenceUtils.subSequence("012", 1))
    assertEquals("2", CharSequenceUtils.subSequence("012", 2))
    assertEquals(StringUtils.EMPTY, CharSequenceUtils.subSequence("012", 3))
  }

  @Test def testSubSequenceNegativeStart(): Unit = {
    assertThrows[IndexOutOfBoundsException](CharSequenceUtils.subSequence(StringUtils.EMPTY, -1))
    ()
  }

  @Test def testSubSequenceTooLong(): Unit = {
    assertThrows[IndexOutOfBoundsException](CharSequenceUtils.subSequence(StringUtils.EMPTY, 1))
    ()
  }

  @Test def testRegionMatches(): Unit = {
    for (data <- CharSequenceUtilsTest.TEST_DATA) {
      new CharSequenceUtilsTest.RunTest() {
        override private[lang3] def invoke = {
          CharSequenceUtils.regionMatches(
            data.source,
            data.ignoreCase,
            data.toffset,
            data.other,
            data.ooffset,
            data.len)
        }
      }.run(data, "String")

      new CharSequenceUtilsTest.RunTest() {
        override private[lang3] def invoke = {
          CharSequenceUtils.regionMatches(
            data.source,
            data.ignoreCase,
            data.toffset,
            data.other,
            data.ooffset,
            data.len)
        }
      }.run(data, "CSString")

      new CharSequenceUtilsTest.RunTest() {
        override private[lang3] def invoke = {
          CharSequenceUtils.regionMatches(
            new StringBuilder(data.source),
            data.ignoreCase,
            data.toffset,
            data.other,
            data.ooffset,
            data.len)
        }
      }.run(data, "CSNonString")
    }
  }

  @Test def testToCharArray(): Unit = {
    val builder = new StringBuilder("abcdefg")
    val expected = builder.toString.toCharArray
    assertArrayEquals(expected, CharSequenceUtils.toCharArray(builder))
    assertArrayEquals(expected, CharSequenceUtils.toCharArray(builder.toString))
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, CharSequenceUtils.toCharArray(null))
  }

  @Test def testNewLastIndexOf(): Unit = {
    testNewLastIndexOfSingle("808087847-1321060740-635567660180086727-925755305", "-1321060740-635567660", 21)
    testNewLastIndexOfSingle("", "")
    testNewLastIndexOfSingle("1", "")
    testNewLastIndexOfSingle("", "1")
    testNewLastIndexOfSingle("1", "1")
    testNewLastIndexOfSingle("11", "1")
    testNewLastIndexOfSingle("1", "11")
    testNewLastIndexOfSingle("apache", "a")
    testNewLastIndexOfSingle("apache", "p")
    testNewLastIndexOfSingle("apache", "e")
    testNewLastIndexOfSingle("apache", "x")
    testNewLastIndexOfSingle("oraoraoraora", "r")
    testNewLastIndexOfSingle("mudamudamudamuda", "d")
    val random = new Random
    val seg: java.lang.StringBuilder = new java.lang.StringBuilder
    while (seg.length <= CharSequenceUtils.TO_STRING_LIMIT) seg.append(random.nextInt)
    var original = new java.lang.StringBuilder(seg)
    testNewLastIndexOfSingle(original, seg)
    for (_ <- 0 until 100) {
      if (random.nextDouble < 0.5) original.append(random.nextInt % 10)
      else original = new java.lang.StringBuilder().append(String.valueOf(random.nextInt % 100)).append(original)

      testNewLastIndexOfSingle(original, seg)
    }
  }

  private def testNewLastIndexOfSingle(a: CharSequence, b: CharSequence): Unit = {
    val maxa = Math.max(a.length, b.length)
    for (i <- -maxa - 10 to maxa + 10) {
      testNewLastIndexOfSingle(a, b, i)
    }
    testNewLastIndexOfSingle(a, b, Integer.MIN_VALUE)
    testNewLastIndexOfSingle(a, b, Integer.MAX_VALUE)
  }

  private def testNewLastIndexOfSingle(a: CharSequence, b: CharSequence, start: Int): Unit = {
    testNewLastIndexOfSingleSingle(a, b, start)
    testNewLastIndexOfSingleSingle(b, a, start)
  }

  private def testNewLastIndexOfSingleSingle(a: CharSequence, b: CharSequence, start: Int): Unit = {
    assertEquals(
      "testNewLastIndexOf fails! original : " + a + " seg : " + b + " start : " + start,
      a.toString.lastIndexOf(b.toString, start),
      CharSequenceUtils.lastIndexOf(
        new CharSequenceUtilsTest.WrapperString(a.toString),
        new CharSequenceUtilsTest.WrapperString(b.toString),
        start
      )
    )
  }
}
