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

package org.apache.commons.lang3.math

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test
import org.scalatest.Assertions.assertThrows

object FractionTest {
  private val SKIP = 500 //53
}

/**
  * Test cases for the {@link Fraction} class
  */
class FractionTest {
  @Test def testConstants(): Unit = {
    assertEquals(0, Fraction.ZERO.getNumerator)
    assertEquals(1, Fraction.ZERO.getDenominator)
    assertEquals(1, Fraction.ONE.getNumerator)
    assertEquals(1, Fraction.ONE.getDenominator)
    assertEquals(1, Fraction.ONE_HALF.getNumerator)
    assertEquals(2, Fraction.ONE_HALF.getDenominator)
    assertEquals(1, Fraction.ONE_THIRD.getNumerator)
    assertEquals(3, Fraction.ONE_THIRD.getDenominator)
    assertEquals(2, Fraction.TWO_THIRDS.getNumerator)
    assertEquals(3, Fraction.TWO_THIRDS.getDenominator)
    assertEquals(1, Fraction.ONE_QUARTER.getNumerator)
    assertEquals(4, Fraction.ONE_QUARTER.getDenominator)
    assertEquals(2, Fraction.TWO_QUARTERS.getNumerator)
    assertEquals(4, Fraction.TWO_QUARTERS.getDenominator)
    assertEquals(3, Fraction.THREE_QUARTERS.getNumerator)
    assertEquals(4, Fraction.THREE_QUARTERS.getDenominator)
    assertEquals(1, Fraction.ONE_FIFTH.getNumerator)
    assertEquals(5, Fraction.ONE_FIFTH.getDenominator)
    assertEquals(2, Fraction.TWO_FIFTHS.getNumerator)
    assertEquals(5, Fraction.TWO_FIFTHS.getDenominator)
    assertEquals(3, Fraction.THREE_FIFTHS.getNumerator)
    assertEquals(5, Fraction.THREE_FIFTHS.getDenominator)
    assertEquals(4, Fraction.FOUR_FIFTHS.getNumerator)
    assertEquals(5, Fraction.FOUR_FIFTHS.getDenominator)
  }

  @Test def testFactory_int_int(): Unit = {
    var f: Fraction = null
    // zero
    f = Fraction.getFraction(0, 1)
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction(0, 2)
    assertEquals(0, f.getNumerator)
    assertEquals(2, f.getDenominator)
    // normal
    f = Fraction.getFraction(1, 1)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction(2, 1)
    assertEquals(2, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction(23, 345)
    assertEquals(23, f.getNumerator)
    assertEquals(345, f.getDenominator)
    // improper
    f = Fraction.getFraction(22, 7)
    assertEquals(22, f.getNumerator)
    assertEquals(7, f.getDenominator)
    // negatives
    f = Fraction.getFraction(-6, 10)
    assertEquals(-6, f.getNumerator)
    assertEquals(10, f.getDenominator)
    f = Fraction.getFraction(6, -10)
    assertEquals(-6, f.getNumerator)
    assertEquals(10, f.getDenominator)
    f = Fraction.getFraction(-6, -10)
    assertEquals(6, f.getNumerator)
    assertEquals(10, f.getDenominator)
    // zero denominator
    assertThrows[ArithmeticException](Fraction.getFraction(1, 0))
    assertThrows[ArithmeticException](Fraction.getFraction(2, 0))
    assertThrows[ArithmeticException](Fraction.getFraction(-3, 0))
    // very large: can't represent as unsimplified fraction, although
    assertThrows[ArithmeticException](Fraction.getFraction(4, Int.MinValue))
    assertThrows[ArithmeticException](Fraction.getFraction(1, Int.MinValue))

    ()
  }

  @Test def testFactory_int_int_int(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(0, 0, 2)
    assertEquals(0, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction(2, 0, 2)
    assertEquals(4, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction(0, 1, 2)
    assertEquals(1, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction(1, 1, 2)
    assertEquals(3, f.getNumerator)
    assertEquals(2, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getFraction(1, -6, -10))
    assertThrows[ArithmeticException](Fraction.getFraction(1, -6, -10))
    assertThrows[ArithmeticException](Fraction.getFraction(1, -6, -10))
    // negative whole
    f = Fraction.getFraction(-1, 6, 10)
    assertEquals(-16, f.getNumerator)
    assertEquals(10, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getFraction(-1, -6, 10))
    assertThrows[ArithmeticException](Fraction.getFraction(-1, 6, -10))
    assertThrows[ArithmeticException](Fraction.getFraction(-1, -6, -10))
    assertThrows[ArithmeticException](Fraction.getFraction(0, 1, 0))
    assertThrows[ArithmeticException](Fraction.getFraction(1, 2, 0))
    assertThrows[ArithmeticException](Fraction.getFraction(-1, -3, 0))
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MaxValue, 1, 2))
    assertThrows[ArithmeticException](Fraction.getFraction(-Int.MaxValue, 1, 2))
    // very large
    f = Fraction.getFraction(-1, 0, Int.MaxValue)
    assertEquals(-Int.MaxValue, f.getNumerator)
    assertEquals(Int.MaxValue, f.getDenominator)
    // negative denominators not allowed in this constructor.
    assertThrows[ArithmeticException](Fraction.getFraction(0, 4, Int.MinValue))
    assertThrows[ArithmeticException](Fraction.getFraction(1, 1, Int.MaxValue))
    assertThrows[ArithmeticException](Fraction.getFraction(-1, 2, Int.MaxValue))
    ()
  }

  @Test def testReducedFactory_int_int(): Unit = {
    var f: Fraction = null
    f = Fraction.getReducedFraction(0, 1)
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getReducedFraction(1, 1)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getReducedFraction(2, 1)
    assertEquals(2, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getReducedFraction(22, 7)
    assertEquals(22, f.getNumerator)
    assertEquals(7, f.getDenominator)
    f = Fraction.getReducedFraction(-6, 10)
    assertEquals(-3, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f = Fraction.getReducedFraction(6, -10)
    assertEquals(-3, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f = Fraction.getReducedFraction(-6, -10)
    assertEquals(3, f.getNumerator)
    assertEquals(5, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getReducedFraction(1, 0))
    assertThrows[ArithmeticException](Fraction.getReducedFraction(2, 0))
    assertThrows[ArithmeticException](Fraction.getReducedFraction(-3, 0))
    // reduced
    f = Fraction.getReducedFraction(0, 2)
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getReducedFraction(2, 2)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getReducedFraction(2, 4)
    assertEquals(1, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getReducedFraction(15, 10)
    assertEquals(3, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getReducedFraction(121, 22)
    assertEquals(11, f.getNumerator)
    assertEquals(2, f.getDenominator)
    // Extreme values
    // OK, can reduce before negating
    f = Fraction.getReducedFraction(-2, Int.MinValue)
    assertEquals(1, f.getNumerator)
    assertEquals(-(Int.MinValue / 2), f.getDenominator)
    // Can't reduce, negation will throw
    assertThrows[ArithmeticException](Fraction.getReducedFraction(-7, Int.MinValue))
    // LANG-662
    f = Fraction.getReducedFraction(Int.MinValue, 2)
    assertEquals(Int.MinValue / 2, f.getNumerator)
    assertEquals(1, f.getDenominator)
  }

  @Test def testFactory_double(): Unit = {
    assertThrows[ArithmeticException](Fraction.getFraction(Double.NaN))
    assertThrows[ArithmeticException](Fraction.getFraction(Double.PositiveInfinity))
    assertThrows[ArithmeticException](Fraction.getFraction(Double.NegativeInfinity))
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MaxValue.toDouble + 1))
    var f = Fraction.getFraction(0.0d)
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    // one
    f = Fraction.getFraction(1.0d)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    // one half
    f = Fraction.getFraction(0.5d)
    assertEquals(1, f.getNumerator)
    assertEquals(2, f.getDenominator)
    // negative
    f = Fraction.getFraction(-0.875d)
    assertEquals(-7, f.getNumerator)
    assertEquals(8, f.getDenominator)
    // over 1
    f = Fraction.getFraction(1.25d)
    assertEquals(5, f.getNumerator)
    assertEquals(4, f.getDenominator)
    // two thirds
    f = Fraction.getFraction(0.66666d)
    assertEquals(2, f.getNumerator)
    assertEquals(3, f.getDenominator)
    // small
    f = Fraction.getFraction(1.0d / 10001d)
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    var f2: Fraction = null
    for (i <- 1 to 100) { // denominator
      for (j <- 1 to i) { // numerator
        f = Fraction.getFraction(j.toDouble / i.toDouble)
        f2 = Fraction.getReducedFraction(j, i)
        assertEquals(f2.getNumerator, f.getNumerator)
        assertEquals(f2.getDenominator, f.getDenominator)
      }
    }
    // save time by skipping some tests!  (
    var i = 1001
    while ({
      i <= 10000
    }) {
      for (j <- 1 to i) {
        f = Fraction.getFraction(j.toDouble / i.toDouble)
        f2 = Fraction.getReducedFraction(j, i)
        assertEquals(f2.getNumerator, f.getNumerator)
        assertEquals(f2.getDenominator, f.getDenominator)
      }
      i += FractionTest.SKIP
    }
  }

  @Test def testFactory_String(): Unit = {
    assertThrows[NullPointerException](Fraction.getFraction(null))
    ()
  }

  @Test def testFactory_String_double(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction("0.0")
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction("0.2")
    assertEquals(1, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f = Fraction.getFraction("0.5")
    assertEquals(1, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction("0.66666")
    assertEquals(2, f.getNumerator)
    assertEquals(3, f.getDenominator)
    assertThrows[NumberFormatException](Fraction.getFraction("2.3R"))
    assertThrows[NumberFormatException](Fraction.getFraction("2147483648")) // too big
    assertThrows[NumberFormatException](Fraction.getFraction("."))
    ()
  }

  @Test def testFactory_String_proper(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction("0 0/1")
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction("1 1/5")
    assertEquals(6, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f = Fraction.getFraction("7 1/2")
    assertEquals(15, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction("1 2/4")
    assertEquals(6, f.getNumerator)
    assertEquals(4, f.getDenominator)
    f = Fraction.getFraction("-7 1/2")
    assertEquals(-15, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction("-1 2/4")
    assertEquals(-6, f.getNumerator)
    assertEquals(4, f.getDenominator)
    assertThrows[NumberFormatException](Fraction.getFraction("2 3"))
    assertThrows[NumberFormatException](Fraction.getFraction("a 3"))
    assertThrows[NumberFormatException](Fraction.getFraction("2 b/4"))
    assertThrows[NumberFormatException](Fraction.getFraction("2 "))
    assertThrows[NumberFormatException](Fraction.getFraction(" 3"))
    assertThrows[NumberFormatException](Fraction.getFraction(" "))
    ()
  }

  @Test def testFactory_String_improper(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction("0/1")
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction("1/5")
    assertEquals(1, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f = Fraction.getFraction("1/2")
    assertEquals(1, f.getNumerator)
    assertEquals(2, f.getDenominator)
    f = Fraction.getFraction("2/3")
    assertEquals(2, f.getNumerator)
    assertEquals(3, f.getDenominator)
    f = Fraction.getFraction("7/3")
    assertEquals(7, f.getNumerator)
    assertEquals(3, f.getDenominator)
    f = Fraction.getFraction("2/4")
    assertEquals(2, f.getNumerator)
    assertEquals(4, f.getDenominator)
    assertThrows[NumberFormatException](Fraction.getFraction("2/d"))
    assertThrows[NumberFormatException](Fraction.getFraction("2e/3"))
    assertThrows[NumberFormatException](Fraction.getFraction("2/"))
    assertThrows[NumberFormatException](Fraction.getFraction("/"))
    ()
  }

  @Test def testGets(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(3, 5, 6)
    assertEquals(23, f.getNumerator)
    assertEquals(3, f.getProperWhole)
    assertEquals(5, f.getProperNumerator)
    assertEquals(6, f.getDenominator)
    f = Fraction.getFraction(-3, 5, 6)
    assertEquals(-23, f.getNumerator)
    assertEquals(-3, f.getProperWhole)
    assertEquals(5, f.getProperNumerator)
    assertEquals(6, f.getDenominator)
    f = Fraction.getFraction(Int.MinValue, 0, 1)
    assertEquals(Int.MinValue, f.getNumerator)
    assertEquals(Int.MinValue, f.getProperWhole)
    assertEquals(0, f.getProperNumerator)
    assertEquals(1, f.getDenominator)
  }

  @Test def testConversions(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(3, 7, 8)
    assertEquals(3, f.intValue)
    assertEquals(3L, f.longValue)
    assertEquals(3.875f, f.floatValue, 0.00001f)
    assertEquals(3.875d, f.doubleValue, 0.00001d)
  }

  @Test def testReduce(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(50, 75)
    var result = f.reduce
    assertEquals(2, result.getNumerator)
    assertEquals(3, result.getDenominator)
    f = Fraction.getFraction(-2, -3)
    result = f.reduce
    assertEquals(2, result.getNumerator)
    assertEquals(3, result.getDenominator)
    f = Fraction.getFraction(2, -3)
    result = f.reduce
    assertEquals(-2, result.getNumerator)
    assertEquals(3, result.getDenominator)
    f = Fraction.getFraction(-2, 3)
    result = f.reduce
    assertEquals(-2, result.getNumerator)
    assertEquals(3, result.getDenominator)
    assertSame(f, result)
    f = Fraction.getFraction(2, 3)
    result = f.reduce
    assertEquals(2, result.getNumerator)
    assertEquals(3, result.getDenominator)
    assertSame(f, result)
    f = Fraction.getFraction(0, 1)
    result = f.reduce
    assertEquals(0, result.getNumerator)
    assertEquals(1, result.getDenominator)
    assertSame(f, result)
    f = Fraction.getFraction(0, 100)
    result = f.reduce
    assertEquals(0, result.getNumerator)
    assertEquals(1, result.getDenominator)
    assertSame(result, Fraction.ZERO)
    f = Fraction.getFraction(Int.MinValue, 2)
    result = f.reduce
    assertEquals(Int.MinValue / 2, result.getNumerator)
    assertEquals(1, result.getDenominator)
  }

  @Test def testInvert(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(50, 75)
    f = f.invert
    assertEquals(75, f.getNumerator)
    assertEquals(50, f.getDenominator)
    f = Fraction.getFraction(4, 3)
    f = f.invert
    assertEquals(3, f.getNumerator)
    assertEquals(4, f.getDenominator)
    f = Fraction.getFraction(-15, 47)
    f = f.invert
    assertEquals(-47, f.getNumerator)
    assertEquals(15, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getFraction(0, 3).invert)
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 1).invert)
    f = Fraction.getFraction(Int.MaxValue, 1)
    f = f.invert
    assertEquals(1, f.getNumerator)
    assertEquals(Int.MaxValue, f.getDenominator)
  }

  @Test def testNegate(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(50, 75)
    f = f.negate
    assertEquals(-50, f.getNumerator)
    assertEquals(75, f.getDenominator)
    f = Fraction.getFraction(-50, 75)
    f = f.negate
    assertEquals(50, f.getNumerator)
    assertEquals(75, f.getDenominator)
    // large values
    f = Fraction.getFraction(Int.MaxValue - 1, Int.MaxValue)
    f = f.negate
    assertEquals(Int.MinValue + 2, f.getNumerator)
    assertEquals(Int.MaxValue, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 1).negate)
    ()
  }

  @Test def testAbs(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(50, 75)
    f = f.abs
    assertEquals(50, f.getNumerator)
    assertEquals(75, f.getDenominator)
    f = Fraction.getFraction(-50, 75)
    f = f.abs
    assertEquals(50, f.getNumerator)
    assertEquals(75, f.getDenominator)
    f = Fraction.getFraction(Int.MaxValue, 1)
    f = f.abs
    assertEquals(Int.MaxValue, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f = Fraction.getFraction(Int.MaxValue, -1)
    f = f.abs
    assertEquals(Int.MaxValue, f.getNumerator)
    assertEquals(1, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 1).abs)
    ()
  }

  @Test def testPow(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(3, 5)
    assertEquals(Fraction.ONE, f.pow(0))
    f = Fraction.getFraction(3, 5)
    assertSame(f, f.pow(1))
    assertEquals(f, f.pow(1))
    f = Fraction.getFraction(3, 5)
    f = f.pow(2)
    assertEquals(9, f.getNumerator)
    assertEquals(25, f.getDenominator)
    f = Fraction.getFraction(3, 5)
    f = f.pow(3)
    assertEquals(27, f.getNumerator)
    assertEquals(125, f.getDenominator)
    f = Fraction.getFraction(3, 5)
    f = f.pow(-1)
    assertEquals(5, f.getNumerator)
    assertEquals(3, f.getDenominator)
    f = Fraction.getFraction(3, 5)
    f = f.pow(-2)
    assertEquals(25, f.getNumerator)
    assertEquals(9, f.getDenominator)
    // check unreduced fractions stay that way.
    f = Fraction.getFraction(6, 10)
    assertEquals(Fraction.ONE, f.pow(0))
    f = Fraction.getFraction(6, 10)
    assertEquals(f, f.pow(1))
    assertNotEquals(f.pow(1), Fraction.getFraction(3, 5))
    f = Fraction.getFraction(6, 10)
    f = f.pow(2)
    assertEquals(9, f.getNumerator)
    assertEquals(25, f.getDenominator)
    f = Fraction.getFraction(6, 10)
    f = f.pow(3)
    assertEquals(27, f.getNumerator)
    assertEquals(125, f.getDenominator)
    f = Fraction.getFraction(6, 10)
    f = f.pow(-1)
    assertEquals(10, f.getNumerator)
    assertEquals(6, f.getDenominator)
    f = Fraction.getFraction(6, 10)
    f = f.pow(-2)
    assertEquals(25, f.getNumerator)
    assertEquals(9, f.getDenominator)
    // zero to any positive power is still zero.
    f = Fraction.getFraction(0, 1231)
    f = f.pow(1)
    assertEquals(0, f.compareTo(Fraction.ZERO))
    assertEquals(0, f.getNumerator)
    assertEquals(1231, f.getDenominator)
    f = f.pow(2)
    assertEquals(0, f.compareTo(Fraction.ZERO))
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    // zero to negative powers should throw an exception
    val fr = f
    assertThrows[ArithmeticException](fr.pow(-1))
    assertThrows[ArithmeticException](fr.pow(Int.MinValue))
    // one to any power is still one.
    f = Fraction.getFraction(1, 1)
    f = f.pow(0)
    assertEquals(f, Fraction.ONE)
    f = f.pow(1)
    assertEquals(f, Fraction.ONE)
    f = f.pow(-1)
    assertEquals(f, Fraction.ONE)
    f = f.pow(Int.MaxValue)
    assertEquals(f, Fraction.ONE)
    f = f.pow(Int.MinValue)
    assertEquals(f, Fraction.ONE)
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MaxValue, 1).pow(2))
    // Numerator growing too negative during the pow operation.
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 1).pow(3))
    assertThrows[ArithmeticException](Fraction.getFraction(65536, 1).pow(2))
    ()
  }

  @Test def testAdd(): Unit = {
    var f: Fraction = null
    var f1: Fraction = null
    var f2: Fraction = null
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(1, 5)
    f = f1.add(f2)
    assertEquals(4, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(2, 5)
    f = f1.add(f2)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(3, 5)
    f = f1.add(f2)
    assertEquals(6, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(-4, 5)
    f = f1.add(f2)
    assertEquals(-1, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(Int.MaxValue - 1, 1)
    f2 = Fraction.ONE
    f = f1.add(f2)
    assertEquals(Int.MaxValue, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(1, 2)
    f = f1.add(f2)
    assertEquals(11, f.getNumerator)
    assertEquals(10, f.getDenominator)
    f1 = Fraction.getFraction(3, 8)
    f2 = Fraction.getFraction(1, 6)
    f = f1.add(f2)
    assertEquals(13, f.getNumerator)
    assertEquals(24, f.getDenominator)
    f1 = Fraction.getFraction(0, 5)
    f2 = Fraction.getFraction(1, 5)
    f = f1.add(f2)
    assertSame(f2, f)
    f = f2.add(f1)
    assertSame(f2, f)
    f1 = Fraction.getFraction(-1, 13 * 13 * 2 * 2)
    f2 = Fraction.getFraction(-2, 13 * 17 * 2)
    val fr = f1.add(f2)
    assertEquals(13 * 13 * 17 * 2 * 2, fr.getDenominator)
    assertEquals(-17 - 2 * 13 * 2, fr.getNumerator)
    assertThrows[NullPointerException](fr.add(null))
    // if this fraction is added naively, it will overflow.
    // check that it doesn't.
    f1 = Fraction.getFraction(1, 32768 * 3)
    f2 = Fraction.getFraction(1, 59049)
    f = f1.add(f2)
    assertEquals(52451, f.getNumerator)
    assertEquals(1934917632, f.getDenominator)
    f1 = Fraction.getFraction(Int.MinValue, 3)
    f2 = Fraction.ONE_THIRD
    f = f1.add(f2)
    assertEquals(Int.MinValue + 1, f.getNumerator)
    assertEquals(3, f.getDenominator)
    f1 = Fraction.getFraction(Int.MaxValue - 1, 1)
    f2 = Fraction.ONE
    f = f1.add(f2)
    assertEquals(Int.MaxValue, f.getNumerator)
    assertEquals(1, f.getDenominator)
    val overflower = f
    assertThrows[ArithmeticException](overflower.add(Fraction.ONE)) // should overflow
    // denominator should not be a multiple of 2 or 3 to trigger overflow
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 5).add(Fraction.getFraction(-1, 5)))
    val maxValue = Fraction.getFraction(-Int.MaxValue, 1)
    assertThrows[ArithmeticException](maxValue.add(maxValue))
    val negativeMaxValue = Fraction.getFraction(-Int.MaxValue, 1)
    assertThrows[ArithmeticException](negativeMaxValue.add(negativeMaxValue))
    val f3 = Fraction.getFraction(3, 327680)
    val f4 = Fraction.getFraction(2, 59049)
    assertThrows[ArithmeticException](f3.add(f4))
    ()
  }

  @Test def testSubtract(): Unit = {
    var f: Fraction = null
    var f1: Fraction = null
    var f2: Fraction = null
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(1, 5)
    f = f1.subtract(f2)
    assertEquals(2, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(7, 5)
    f2 = Fraction.getFraction(2, 5)
    f = f1.subtract(f2)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(3, 5)
    f = f1.subtract(f2)
    assertEquals(0, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(-4, 5)
    f = f1.subtract(f2)
    assertEquals(7, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(0, 5)
    f2 = Fraction.getFraction(4, 5)
    f = f1.subtract(f2)
    assertEquals(-4, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(0, 5)
    f2 = Fraction.getFraction(-4, 5)
    f = f1.subtract(f2)
    assertEquals(4, f.getNumerator)
    assertEquals(5, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(1, 2)
    f = f1.subtract(f2)
    assertEquals(1, f.getNumerator)
    assertEquals(10, f.getDenominator)
    f1 = Fraction.getFraction(0, 5)
    f2 = Fraction.getFraction(1, 5)
    f = f2.subtract(f1)
    assertSame(f2, f)
    val fr = f
    assertThrows[NullPointerException](fr.subtract(null))
    // if this fraction is subtracted naively, it will overflow.
    f1 = Fraction.getFraction(1, 32768 * 3)
    f2 = Fraction.getFraction(1, 59049)
    f = f1.subtract(f2)
    assertEquals(-13085, f.getNumerator)
    assertEquals(1934917632, f.getDenominator)
    f1 = Fraction.getFraction(Int.MinValue, 3)
    f2 = Fraction.ONE_THIRD.negate
    f = f1.subtract(f2)
    assertEquals(Int.MinValue + 1, f.getNumerator)
    assertEquals(3, f.getDenominator)
    f1 = Fraction.getFraction(Int.MaxValue, 1)
    f2 = Fraction.ONE
    f = f1.subtract(f2)
    assertEquals(Int.MaxValue - 1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    // Should overflow
    assertThrows[ArithmeticException](
      Fraction.getFraction(1, Int.MaxValue).subtract(Fraction.getFraction(1, Int.MaxValue - 1)))
    f = f1.subtract(f2)
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 5).subtract(Fraction.getFraction(1, 5)))
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MinValue, 1).subtract(Fraction.ONE))
    assertThrows[ArithmeticException](Fraction.getFraction(Int.MaxValue, 1).subtract(Fraction.ONE.negate))
    assertThrows[ArithmeticException](Fraction.getFraction(3, 327680).subtract(Fraction.getFraction(2, 59049)))
    ()
  }

  @Test def testMultiply(): Unit = {
    var f: Fraction = null
    var f1: Fraction = null
    var f2: Fraction = null
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(2, 5)
    f = f1.multiplyBy(f2)
    assertEquals(6, f.getNumerator)
    assertEquals(25, f.getDenominator)
    f1 = Fraction.getFraction(6, 10)
    f2 = Fraction.getFraction(6, 10)
    f = f1.multiplyBy(f2)
    assertEquals(9, f.getNumerator)
    assertEquals(25, f.getDenominator)
    f = f.multiplyBy(f2)
    assertEquals(27, f.getNumerator)
    assertEquals(125, f.getDenominator)
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(-2, 5)
    f = f1.multiplyBy(f2)
    assertEquals(-6, f.getNumerator)
    assertEquals(25, f.getDenominator)
    f1 = Fraction.getFraction(-3, 5)
    f2 = Fraction.getFraction(-2, 5)
    f = f1.multiplyBy(f2)
    assertEquals(6, f.getNumerator)
    assertEquals(25, f.getDenominator)
    f1 = Fraction.getFraction(0, 5)
    f2 = Fraction.getFraction(2, 7)
    f = f1.multiplyBy(f2)
    assertSame(Fraction.ZERO, f)
    f1 = Fraction.getFraction(2, 7)
    f2 = Fraction.ONE
    f = f1.multiplyBy(f2)
    assertEquals(2, f.getNumerator)
    assertEquals(7, f.getDenominator)
    f1 = Fraction.getFraction(Int.MaxValue, 1)
    f2 = Fraction.getFraction(Int.MinValue, Int.MaxValue)
    f = f1.multiplyBy(f2)
    assertEquals(Int.MinValue, f.getNumerator)
    assertEquals(1, f.getDenominator)
    val fr = f
    assertThrows[NullPointerException](fr.multiplyBy(null))
    val fr1 = Fraction.getFraction(1, Int.MaxValue)
    assertThrows[ArithmeticException](fr1.multiplyBy(fr1))
    val fr2 = Fraction.getFraction(1, -Int.MaxValue)
    assertThrows[ArithmeticException](fr2.multiplyBy(fr2))
    ()
  }

  @Test def testDivide(): Unit = {
    var f: Fraction = null
    var f1: Fraction = null
    var f2: Fraction = null
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(2, 5)
    f = f1.divideBy(f2)
    assertEquals(3, f.getNumerator)
    assertEquals(2, f.getDenominator)
    assertThrows[ArithmeticException](Fraction.getFraction(3, 5).divideBy(Fraction.ZERO))
    f1 = Fraction.getFraction(0, 5)
    f2 = Fraction.getFraction(2, 7)
    f = f1.divideBy(f2)
    assertSame(Fraction.ZERO, f)
    f1 = Fraction.getFraction(2, 7)
    f2 = Fraction.ONE
    f = f1.divideBy(f2)
    assertEquals(2, f.getNumerator)
    assertEquals(7, f.getDenominator)
    f1 = Fraction.getFraction(1, Int.MaxValue)
    f = f1.divideBy(f1)
    assertEquals(1, f.getNumerator)
    assertEquals(1, f.getDenominator)
    f1 = Fraction.getFraction(Int.MinValue, Int.MaxValue)
    f2 = Fraction.getFraction(1, Int.MaxValue)
    val fr = f1.divideBy(f2)
    assertEquals(Int.MinValue, fr.getNumerator)
    assertEquals(1, fr.getDenominator)
    assertThrows[NullPointerException](fr.divideBy(null))
    val smallest = Fraction.getFraction(1, Int.MaxValue)
    assertThrows[ArithmeticException](smallest.divideBy(smallest.invert))
    val negative = Fraction.getFraction(1, -Int.MaxValue)
    assertThrows[ArithmeticException](negative.divideBy(negative.invert))
    ()
  }

  @Test def testEquals(): Unit = {
    var f1: Fraction = null
    var f2: Fraction = null
    f1 = Fraction.getFraction(3, 5)
    assertNotEquals(null, f1)
    assertNotEquals(f1, new AnyRef)
    assertNotEquals(f1, Integer.valueOf(6))
    f1 = Fraction.getFraction(3, 5)
    f2 = Fraction.getFraction(2, 5)
    assertNotEquals(f1, f2)
    assertEquals(f1, f1)
    assertEquals(f2, f2)
    f2 = Fraction.getFraction(3, 5)
    assertEquals(f1, f2)
    f2 = Fraction.getFraction(6, 10)
    assertNotEquals(f1, f2)
  }

  @Test def testHashCode(): Unit = {
    val f1 = Fraction.getFraction(3, 5)
    var f2 = Fraction.getFraction(3, 5)
    assertEquals(f1.hashCode, f2.hashCode)
    f2 = Fraction.getFraction(2, 5)
    assertTrue(f1.hashCode != f2.hashCode)
    f2 = Fraction.getFraction(6, 10)
    assertTrue(f1.hashCode != f2.hashCode)
  }

  @Test def testCompareTo(): Unit = {
    var f1: Fraction = null
    var f2: Fraction = null
    f1 = Fraction.getFraction(3, 5)
    assertEquals(0, f1.compareTo(f1))
    val fr = f1
    assertThrows[NullPointerException](fr.compareTo(null))
    f2 = Fraction.getFraction(2, 5)
    assertTrue(f1.compareTo(f2) > 0)
    assertEquals(0, f2.compareTo(f2))
    f2 = Fraction.getFraction(4, 5)
    assertTrue(f1.compareTo(f2) < 0)
    assertEquals(0, f2.compareTo(f2))
    f2 = Fraction.getFraction(3, 5)
    assertEquals(0, f1.compareTo(f2))
    assertEquals(0, f2.compareTo(f2))
    f2 = Fraction.getFraction(6, 10)
    assertEquals(0, f1.compareTo(f2))
    assertEquals(0, f2.compareTo(f2))
    f2 = Fraction.getFraction(-1, 1, Int.MaxValue)
    assertTrue(f1.compareTo(f2) > 0)
    assertEquals(0, f2.compareTo(f2))
  }

  @Test def testToString(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(3, 5)
    val str = f.toString
    assertEquals("3/5", str)
    assertSame(str, f.toString)
    f = Fraction.getFraction(7, 5)
    assertEquals("7/5", f.toString)
    f = Fraction.getFraction(4, 2)
    assertEquals("4/2", f.toString)
    f = Fraction.getFraction(0, 2)
    assertEquals("0/2", f.toString)
    f = Fraction.getFraction(2, 2)
    assertEquals("2/2", f.toString)
    f = Fraction.getFraction(Int.MinValue, 0, 1)
    assertEquals("-2147483648/1", f.toString)
    f = Fraction.getFraction(-1, 1, Int.MaxValue)
    assertEquals("-2147483648/2147483647", f.toString)
  }

  @Test def testToProperString(): Unit = {
    var f: Fraction = null
    f = Fraction.getFraction(3, 5)
    val str = f.toProperString
    assertEquals("3/5", str)
    assertSame(str, f.toProperString)
    f = Fraction.getFraction(7, 5)
    assertEquals("1 2/5", f.toProperString)
    f = Fraction.getFraction(14, 10)
    assertEquals("1 4/10", f.toProperString)
    f = Fraction.getFraction(4, 2)
    assertEquals("2", f.toProperString)
    f = Fraction.getFraction(0, 2)
    assertEquals("0", f.toProperString)
    f = Fraction.getFraction(2, 2)
    assertEquals("1", f.toProperString)
    f = Fraction.getFraction(-7, 5)
    assertEquals("-1 2/5", f.toProperString)
    f = Fraction.getFraction(Int.MinValue, 0, 1)
    assertEquals("-2147483648", f.toProperString)
    f = Fraction.getFraction(-1, 1, Int.MaxValue)
    assertEquals("-1 1/2147483647", f.toProperString)
    assertEquals("-1", Fraction.getFraction(-1).toProperString)
  }
}
