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

import java.lang.{Double => JavaDouble, Float => JavaFloat, Long => JavaLong}
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import org.apache.commons.lang3.{void, TestHelpers}
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * Unit tests {@link org.apache.commons.lang3.math.NumberUtils}.
  */
class NumberUtilsTest extends TestHelpers {
  //  @Test def testConstructor(): Unit = {
  //    assertNotNull(new NumberUtils.type)
  //    val cons = classOf[NumberUtils.type].getDeclaredConstructors
  //    assertEquals(1, cons.length)
  //    assertTrue(Modifier.isPublic(cons(0).getModifiers))
  //    assertTrue(Modifier.isPublic(classOf[NumberUtils.type].getModifiers))
  //    assertFalse(Modifier.isFinal(classOf[NumberUtils.type].getModifiers))
  //  }

  /**
    * Test for {@link NumberUtils# toInt ( String )}.
    */
  @Test def testToIntString(): Unit = {
    assertEquals("toInt(String) 1 failed", 12345, NumberUtils.toInt("12345"))
    assertEquals("toInt(String) 2 failed", 0, NumberUtils.toInt("abc"))
    assertEquals("toInt(empty) failed", 0, NumberUtils.toInt(""))
    assertEquals("toInt(null) failed", 0, NumberUtils.toInt(null))
  }

  /**
    * Test for {@link NumberUtils# toInt ( String, int)}.
    */
  @Test def testToIntStringI(): Unit = {
    assertEquals("toInt(String, int) 1 failed", 12345, NumberUtils.toInt("12345", 5))
    assertEquals("toInt(String, int) 2 failed", 5, NumberUtils.toInt("1234.5", 5))
  }

  /**
    * Test for {@link NumberUtils# toLong ( String )}.
    */
  @Test def testToLongString(): Unit = {
    assertEquals("toLong(String) 1 failed", 12345L, NumberUtils.toLong("12345"))
    assertEquals("toLong(String) 2 failed", 0L, NumberUtils.toLong("abc"))
    assertEquals("toLong(String) 3 failed", 0L, NumberUtils.toLong("1L"))
    assertEquals("toLong(String) 4 failed", 0L, NumberUtils.toLong("1l"))
    assertEquals("toLong(Long.MAX_VALUE) failed", NumberUtils.toLong(Long.MaxValue + ""), Long.MaxValue)
    assertEquals("toLong(Long.MIN_VALUE) failed", NumberUtils.toLong(Long.MinValue + ""), Long.MinValue)
    assertEquals("toLong(empty) failed", 0L, NumberUtils.toLong(""))
    assertEquals("toLong(null) failed", 0L, NumberUtils.toLong(null))
  }

  /**
    * Test for {@link NumberUtils# toLong ( String, long)}.
    */
  @Test def testToLongStringL(): Unit = {
    assertEquals("toLong(String, long) 1 failed", 12345L, NumberUtils.toLong("12345", 5L))
    assertEquals("toLong(String, long) 2 failed", 5L, NumberUtils.toLong("1234.5", 5L))
  }

  /**
    * Test for {@link NumberUtils# toFloat ( String )}.
    */
  @Test def testToFloatString(): Unit = {
    assertEquals("toFloat(String) 1 failed", NumberUtils.toFloat("-1.2345"), -1.2345f, FloatDelta)
    assertEquals("toFloat(String) 2 failed", 1.2345f, NumberUtils.toFloat("1.2345"), FloatDelta)
    assertEquals("toFloat(String) 3 failed", 0.0f, NumberUtils.toFloat("abc"), FloatDelta)
    // LANG-1060
    assertEquals("toFloat(String) 4 failed", NumberUtils.toFloat("-001.2345"), -1.2345f, FloatDelta)
    assertEquals("toFloat(String) 5 failed", 1.2345f, NumberUtils.toFloat("+001.2345"), FloatDelta)
    assertEquals("toFloat(String) 6 failed", 1.2345f, NumberUtils.toFloat("001.2345"), FloatDelta)
    assertEquals("toFloat(String) 7 failed", 0f, NumberUtils.toFloat("000.00"), FloatDelta)
    assertEquals("toFloat(Float.MaxValue) failed", NumberUtils.toFloat(Float.MaxValue + ""), Float.MaxValue, FloatDelta)
    assertEquals(
      "toFloat(Float.MIN_VALUE) failed",
      NumberUtils.toFloat(Float.MinValue + ""),
      Float.MinValue,
      FloatDelta)
    assertEquals("toFloat(empty) failed", 0.0f, NumberUtils.toFloat(""), FloatDelta)
    assertEquals("toFloat(null) failed", 0.0f, NumberUtils.toFloat(null), FloatDelta)
  }

  /**
    * Test for {@link NumberUtils# toFloat ( String, float)}.
    */
  @Test def testToFloatStringF(): Unit = {
    assertEquals("toFloat(String, int) 1 failed", 1.2345f, NumberUtils.toFloat("1.2345", 5.1f), FloatDelta)
    assertEquals("toFloat(String, int) 2 failed", 5.0f, NumberUtils.toFloat("a", 5.0f), FloatDelta)
    assertEquals("toFloat(String, int) 3 failed", 5.0f, NumberUtils.toFloat("-001Z.2345", 5.0f), FloatDelta)
    assertEquals("toFloat(String, int) 4 failed", 5.0f, NumberUtils.toFloat("+001AB.2345", 5.0f), FloatDelta)
    assertEquals("toFloat(String, int) 5 failed", 5.0f, NumberUtils.toFloat("001Z.2345", 5.0f), FloatDelta)
  }

  /**
    * Test for {(@link NumberUtils#createNumber(String)}
    */
  @Test def testStringCreateNumberEnsureNoPrecisionLoss(): Unit = {
    val shouldBeFloat = "1.23"
    val shouldBeDouble = "3.40282354e+38"
    val shouldBeBigDecimal = "1.797693134862315759e+308"
    assertTrue(NumberUtils.createNumber(shouldBeFloat).isInstanceOf[Float])
    assertTrue(NumberUtils.createNumber(shouldBeDouble).isInstanceOf[Double])
    assertTrue(NumberUtils.createNumber(shouldBeBigDecimal).isInstanceOf[BigDecimal])
    assertTrue(NumberUtils.createNumber("001.12").isInstanceOf[Float])
    assertTrue(NumberUtils.createNumber("-001.12").isInstanceOf[Float])
    assertTrue(NumberUtils.createNumber("+001.12").isInstanceOf[Float])
    assertTrue(NumberUtils.createNumber("003.40282354e+38").isInstanceOf[Double])
    assertTrue(NumberUtils.createNumber("-003.40282354e+38").isInstanceOf[Double])
    assertTrue(NumberUtils.createNumber("+003.40282354e+38").isInstanceOf[Double])
    assertTrue(NumberUtils.createNumber("0001.797693134862315759e+308").isInstanceOf[BigDecimal])
    assertTrue(NumberUtils.createNumber("-001.797693134862315759e+308").isInstanceOf[BigDecimal])
    assertTrue(NumberUtils.createNumber("+001.797693134862315759e+308").isInstanceOf[BigDecimal])
  }

  /**
    * Test for {@link NumberUtils# toDouble ( String )}.
    */
  @Test def testStringToDoubleString(): Unit = {
    assertEquals("toDouble(String) 1 failed", NumberUtils.toDouble("-1.2345"), -1.2345d, DoubleDelta)
    assertEquals("toDouble(String) 2 failed", 1.2345d, NumberUtils.toDouble("1.2345"), DoubleDelta)
    assertEquals("toDouble(String) 3 failed", 0.0d, NumberUtils.toDouble("abc"), DoubleDelta)
    assertEquals("toDouble(String) 4 failed", NumberUtils.toDouble("-001.2345"), -1.2345d, DoubleDelta)
    assertEquals("toDouble(String) 5 failed", 1.2345d, NumberUtils.toDouble("+001.2345"), DoubleDelta)
    assertEquals("toDouble(String) 6 failed", 1.2345d, NumberUtils.toDouble("001.2345"), DoubleDelta)
    assertEquals("toDouble(String) 7 failed", 0d, NumberUtils.toDouble("000.00000"), DoubleDelta)
    assertEquals(
      "toDouble(Double.MaxValue) failed",
      NumberUtils.toDouble(Double.MaxValue + ""),
      Double.MaxValue,
      DoubleDelta)
    assertEquals(
      "toDouble(Double.MIN_VALUE) failed",
      NumberUtils.toDouble(Double.MinValue + ""),
      Double.MinValue,
      DoubleDelta)
    assertEquals("toDouble(empty) failed", 0.0d, NumberUtils.toDouble(""), DoubleDelta)
    assertEquals("toDouble(null) failed", 0.0d, NumberUtils.toDouble(null.asInstanceOf[String]), DoubleDelta)
  }

  /**
    * Test for {@link NumberUtils# toDouble ( String, double)}.
    */
  @Test def testStringToDoubleStringD(): Unit = {
    assertEquals("toDouble(String, int) 1 failed", 1.2345d, NumberUtils.toDouble("1.2345", 5.1d), DoubleDelta)
    assertEquals("toDouble(String, int) 2 failed", 5.0d, NumberUtils.toDouble("a", 5.0d), DoubleDelta)
    assertEquals("toDouble(String, int) 3 failed", 1.2345d, NumberUtils.toDouble("001.2345", 5.1d), DoubleDelta)
    assertEquals("toDouble(String, int) 4 failed", NumberUtils.toDouble("-001.2345", 5.1d), -1.2345d, DoubleDelta)
    assertEquals("toDouble(String, int) 5 failed", 1.2345d, NumberUtils.toDouble("+001.2345", 5.1d), DoubleDelta)
    assertEquals("toDouble(String, int) 7 failed", 0d, NumberUtils.toDouble("000.00", 5.1d), DoubleDelta)
  }

  /**
    * Test for {@link NumberUtils# toDouble ( BigDecimal )}
    */
  @Test def testBigIntegerToDoubleBigInteger(): Unit = {
    assertEquals(
      "toDouble(BigInteger) 1 failed",
      0.0d,
      NumberUtils.toDouble(null.asInstanceOf[BigDecimal]),
      DoubleDelta)
    assertEquals("toDouble(BigInteger) 2 failed", 8.5d, NumberUtils.toDouble(BigDecimal.valueOf(8.5d)), DoubleDelta)
  }

  /**
    * Test for {@link NumberUtils# toDouble ( BigDecimal, double)}
    */
  @Test def testBigIntegerToDoubleBigIntegerD(): Unit = {
    assertEquals(
      "toDouble(BigInteger) 1 failed",
      1.1d,
      NumberUtils.toDouble(null.asInstanceOf[BigDecimal], 1.1d),
      DoubleDelta)
    assertEquals(
      "toDouble(BigInteger) 2 failed",
      8.5d,
      NumberUtils.toDouble(BigDecimal.valueOf(8.5d), 1.1d),
      DoubleDelta)
  }

  /**
    * Test for {@link NumberUtils# toByte ( String )}.
    */
  @Test def testToByteString(): Unit = {
    assertEquals("toByte(String) 1 failed", 123.toByte, NumberUtils.toByte("123"))
    assertEquals("toByte(String) 2 failed", 0.toByte, NumberUtils.toByte("abc"))
    assertEquals("toByte(empty) failed", 0.toByte, NumberUtils.toByte(""))
    assertEquals("toByte(null) failed", 0.toByte, NumberUtils.toByte(null))
  }

  /**
    * Test for {@link NumberUtils# toByte ( String, byte)}.
    */
  @Test def testToByteStringI(): Unit = {
    assertEquals("toByte(String, byte) 1 failed", 123.toByte, NumberUtils.toByte("123", 5.toByte))
    assertEquals("toByte(String, byte) 2 failed", 5.toByte, NumberUtils.toByte("12.3", 5.toByte))
  }

  /**
    * Test for {@link NumberUtils# toShort ( String )}.
    */
  @Test def testToShortString(): Unit = {
    assertEquals("toShort(String) 1 failed", 12345.toShort, NumberUtils.toShort("12345"))
    assertEquals("toShort(String) 2 failed", 0.toShort, NumberUtils.toShort("abc"))
    assertEquals("toShort(empty) failed", 0.toShort, NumberUtils.toShort(""))
    assertEquals("toShort(null) failed", 0.toShort, NumberUtils.toShort(null))
  }

  /**
    * Test for {@link NumberUtils# toShort ( String, short)}.
    */
  @Test def testToShortStringI(): Unit = {
    assertEquals("toShort(String, short) 1 failed", 12345.toShort, NumberUtils.toShort("12345", 5.toShort))
    assertEquals("toShort(String, short) 2 failed", 5.toShort, NumberUtils.toShort("1234.5", 5.toShort))
  }

  /**
    * Test for {@link NumberUtils# toScaledBigDecimal ( BigDecimal )}.
    */
  @Test def testToScaledBigDecimalBigDecimal(): Unit = {
    assertEquals(
      "toScaledBigDecimal(BigDecimal) 1 failed",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(123.456)),
      BigDecimal.valueOf(123.46))
    // Test RoudingMode.HALF_EVEN default rounding.
    assertEquals(
      "toScaledBigDecimal(BigDecimal) 2 failed",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.515)),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(BigDecimal) 3 failed",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525)),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(BigDecimal) 4 failed",
      "2352.00",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525)).multiply(BigDecimal.valueOf(100)).toString
    )
    assertEquals(
      "toScaledBigDecimal(BigDecimal) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[BigDecimal]),
      BigDecimal.ZERO)
  }

  /**
    * Test for {@link NumberUtils# toScaledBigDecimal ( BigDecimal, int, RoundingMode)}.
    */
  @Test def testToScaledBigDecimalBigDecimalIRM(): Unit = {
    assertEquals(
      "toScaledBigDecimal(BigDecimal, int, RoudingMode) 1 failed",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(123.456), 1, RoundingMode.CEILING),
      BigDecimal.valueOf(123.5)
    )
    assertEquals(
      "toScaledBigDecimal(BigDecimal, int, RoudingMode) 2 failed",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.5159), 3, RoundingMode.FLOOR),
      BigDecimal.valueOf(23.515)
    )
    assertEquals(
      "toScaledBigDecimal(BigDecimal, int, RoudingMode) 3 failed",
      NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525), 2, RoundingMode.HALF_UP),
      BigDecimal.valueOf(23.53)
    )
    assertEquals(
      "toScaledBigDecimal(BigDecimal, int, RoudingMode) 4 failed",
      "23521.0000",
      NumberUtils
        .toScaledBigDecimal(BigDecimal.valueOf(23.521), 4, RoundingMode.HALF_EVEN)
        .multiply(BigDecimal.valueOf(1000))
        .toString
    )
    assertEquals(
      "toScaledBigDecimal(BigDecimal, int, RoudingMode) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[BigDecimal], 2, RoundingMode.HALF_UP),
      BigDecimal.ZERO
    )
  }

  /**
    * Test for {@link NumberUtils# toScaledBigDecimal ( Float )}.
    */
  @Test def testToScaledBigDecimalFloat(): Unit = {
    assertEquals(
      "toScaledBigDecimal(Float) 1 failed",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(123.456f)),
      BigDecimal.valueOf(123.46))
    assertEquals(
      "toScaledBigDecimal(Float) 2 failed",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(23.515f)),
      BigDecimal.valueOf(23.51))
    // Note. NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(23.515f)).equals(BigDecimal.valueOf(23.51))
    // because of roundoff error. It is ok.
    assertEquals(
      "toScaledBigDecimal(Float) 3 failed",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(23.525f)),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(Float) 4 failed",
      "2352.00",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(23.525f)).multiply(BigDecimal.valueOf(100)).toString)
    assertEquals(
      "toScaledBigDecimal(Float) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[JavaFloat]),
      BigDecimal.ZERO)
  }

  /**
    * Test for {@link NumberUtils# toScaledBigDecimal ( Float, int, RoundingMode)}.
    */
  @Test def testToScaledBigDecimalFloatIRM(): Unit = {
    assertEquals(
      "toScaledBigDecimal(Float, int, RoudingMode) 1 failed",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(123.456f), 1, RoundingMode.CEILING),
      BigDecimal.valueOf(123.5)
    )
    assertEquals(
      "toScaledBigDecimal(Float, int, RoudingMode) 2 failed",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(23.5159f), 3, RoundingMode.FLOOR),
      BigDecimal.valueOf(23.515)
    )
    // The following happens due to roundoff error. We're ok with this.
    assertEquals(
      "toScaledBigDecimal(Float, int, RoudingMode) 3 failed",
      NumberUtils.toScaledBigDecimal(JavaFloat.valueOf(23.525f), 2, RoundingMode.HALF_UP),
      BigDecimal.valueOf(23.52)
    )
    assertEquals(
      "toScaledBigDecimal(Float, int, RoudingMode) 4 failed",
      "23521.0000",
      NumberUtils
        .toScaledBigDecimal(JavaFloat.valueOf(23.521f), 4, RoundingMode.HALF_EVEN)
        .multiply(BigDecimal.valueOf(1000))
        .toString
    )
    assertEquals(
      "toScaledBigDecimal(Float, int, RoudingMode) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[JavaFloat], 2, RoundingMode.HALF_UP),
      BigDecimal.ZERO
    )
  }

  /**
    * Test for {@link NumberUtils# toScaledBigDecimal ( Double )}.
    */
  @Test def testToScaledBigDecimalDouble(): Unit = {
    assertEquals(
      "toScaledBigDecimal(Double) 1 failed",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(123.456d)),
      BigDecimal.valueOf(123.46))
    assertEquals(
      "toScaledBigDecimal(Double) 2 failed",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(23.515d)),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(Double) 3 failed",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(23.525d)),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(Double) 4 failed",
      "2352.00",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(23.525d)).multiply(BigDecimal.valueOf(100)).toString
    )
    assertEquals(
      "toScaledBigDecimal(Double) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[JavaDouble]),
      BigDecimal.ZERO)
  }

  /**
    * Test for {@link NumberUtils# toScaledBigDecimal ( Double, int, RoundingMode)}.
    */
  @Test def testToScaledBigDecimalDoubleIRM(): Unit = {
    assertEquals(
      "toScaledBigDecimal(Double, int, RoudingMode) 1 failed",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(123.456d), 1, RoundingMode.CEILING),
      BigDecimal.valueOf(123.5)
    )
    assertEquals(
      "toScaledBigDecimal(Double, int, RoudingMode) 2 failed",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(23.5159d), 3, RoundingMode.FLOOR),
      BigDecimal.valueOf(23.515)
    )
    assertEquals(
      "toScaledBigDecimal(Double, int, RoudingMode) 3 failed",
      NumberUtils.toScaledBigDecimal(JavaDouble.valueOf(23.525d), 2, RoundingMode.HALF_UP),
      BigDecimal.valueOf(23.53)
    )
    assertEquals(
      "toScaledBigDecimal(Double, int, RoudingMode) 4 failed",
      "23521.0000",
      NumberUtils
        .toScaledBigDecimal(JavaDouble.valueOf(23.521d), 4, RoundingMode.HALF_EVEN)
        .multiply(BigDecimal.valueOf(1000))
        .toString
    )
    assertEquals(
      "toScaledBigDecimal(Double, int, RoudingMode) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[JavaDouble], 2, RoundingMode.HALF_UP),
      BigDecimal.ZERO
    )
  }

  @Test def testToScaledBigDecimalString(): Unit = {
    assertEquals(
      "toScaledBigDecimal(String) 1 failed",
      NumberUtils.toScaledBigDecimal("123.456"),
      BigDecimal.valueOf(123.46))
    assertEquals(
      "toScaledBigDecimal(String) 2 failed",
      NumberUtils.toScaledBigDecimal("23.515"),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(String) 3 failed",
      NumberUtils.toScaledBigDecimal("23.525"),
      BigDecimal.valueOf(23.52))
    assertEquals(
      "toScaledBigDecimal(String) 4 failed",
      "2352.00",
      NumberUtils.toScaledBigDecimal("23.525").multiply(BigDecimal.valueOf(100)).toString)
    assertEquals(
      "toScaledBigDecimal(String) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[String]),
      BigDecimal.ZERO)
  }

  @Test def testToScaledBigDecimalStringIRM(): Unit = {
    assertEquals(
      "toScaledBigDecimal(String, int, RoudingMode) 1 failed",
      NumberUtils.toScaledBigDecimal("123.456", 1, RoundingMode.CEILING),
      BigDecimal.valueOf(123.5))
    assertEquals(
      "toScaledBigDecimal(String, int, RoudingMode) 2 failed",
      NumberUtils.toScaledBigDecimal("23.5159", 3, RoundingMode.FLOOR),
      BigDecimal.valueOf(23.515))
    assertEquals(
      "toScaledBigDecimal(String, int, RoudingMode) 3 failed",
      NumberUtils.toScaledBigDecimal("23.525", 2, RoundingMode.HALF_UP),
      BigDecimal.valueOf(23.53))
    assertEquals(
      "toScaledBigDecimal(String, int, RoudingMode) 4 failed",
      "23521.0000",
      NumberUtils.toScaledBigDecimal("23.521", 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString
    )
    assertEquals(
      "toScaledBigDecimal(String, int, RoudingMode) 5 failed",
      NumberUtils.toScaledBigDecimal(null.asInstanceOf[String], 2, RoundingMode.HALF_UP),
      BigDecimal.ZERO
    )
  }

  @Test def testCreateNumber(): Unit = { // a lot of things can go wrong
    assertEquals("createNumber(String) 1 failed", JavaFloat.valueOf("1234.5"), NumberUtils.createNumber("1234.5"))
    assertEquals("createNumber(String) 2 failed", Integer.valueOf("12345"), NumberUtils.createNumber("12345"))
    assertEquals("createNumber(String) 3 failed", JavaDouble.valueOf("1234.5"), NumberUtils.createNumber("1234.5D"))
    assertEquals("createNumber(String) 3 failed", JavaDouble.valueOf("1234.5"), NumberUtils.createNumber("1234.5d"))
    assertEquals("createNumber(String) 4 failed", JavaFloat.valueOf("1234.5"), NumberUtils.createNumber("1234.5F"))
    assertEquals("createNumber(String) 4 failed", JavaFloat.valueOf("1234.5"), NumberUtils.createNumber("1234.5f"))
    assertEquals(
      "createNumber(String) 5 failed",
      JavaLong.valueOf(Int.MaxValue + 1L),
      NumberUtils.createNumber("" + (Int.MaxValue + 1L))
    )
    assertEquals("createNumber(String) 6 failed", JavaLong.valueOf(12345), NumberUtils.createNumber("12345L"))
    assertEquals("createNumber(String) 6 failed", JavaLong.valueOf(12345), NumberUtils.createNumber("12345l"))
    assertEquals("createNumber(String) 7 failed", JavaFloat.valueOf("-1234.5"), NumberUtils.createNumber("-1234.5"))
    assertEquals("createNumber(String) 8 failed", Integer.valueOf("-12345"), NumberUtils.createNumber("-12345"))
    assertEquals("createNumber(String) 9a failed", 0xfade, NumberUtils.createNumber("0xFADE").intValue)
    assertEquals("createNumber(String) 9b failed", 0xfade, NumberUtils.createNumber("0Xfade").intValue)
    assertEquals("createNumber(String) 10a failed", -0xfade, NumberUtils.createNumber("-0xFADE").intValue)
    assertEquals("createNumber(String) 10b failed", -0xfade, NumberUtils.createNumber("-0Xfade").intValue)
    assertEquals("createNumber(String) 11 failed", JavaDouble.valueOf("1.1E200"), NumberUtils.createNumber("1.1E200"))
    assertEquals("createNumber(String) 12 failed", JavaFloat.valueOf("1.1E20"), NumberUtils.createNumber("1.1E20"))
    assertEquals("createNumber(String) 13 failed", JavaDouble.valueOf("-1.1E200"), NumberUtils.createNumber("-1.1E200"))
    assertEquals("createNumber(String) 14 failed", JavaDouble.valueOf("1.1E-200"), NumberUtils.createNumber("1.1E-200"))
    assertNull("createNumber(null) failed", NumberUtils.createNumber(null))
    assertEquals(
      "createNumber(String) failed",
      new BigInteger("12345678901234567890"),
      NumberUtils.createNumber("12345678901234567890L")
    )

    assertEquals("createNumber(String) 15 failed", new BigDecimal("1.1E-700"), NumberUtils.createNumber("1.1E-700F"))
    assertEquals(
      "createNumber(String) 16 failed",
      JavaLong.valueOf("10" + Int.MaxValue),
      NumberUtils.createNumber("10" + Int.MaxValue + "L"))
    assertEquals(
      "createNumber(String) 17 failed",
      JavaLong.valueOf("10" + Int.MaxValue),
      NumberUtils.createNumber("10" + Int.MaxValue))
    assertEquals(
      "createNumber(String) 18 failed",
      new BigInteger("10" + Long.MaxValue),
      NumberUtils.createNumber("10" + Long.MaxValue))
    // LANG-521
    assertEquals("createNumber(String) LANG-521 failed", JavaFloat.valueOf("2."), NumberUtils.createNumber("2."))
    // LANG-638
    assertFalse("createNumber(String) succeeded", checkCreateNumber("1eE"))
    // LANG-693
    assertEquals(
      "createNumber(String) LANG-693 failed",
      JavaDouble.valueOf(Double.MaxValue),
      NumberUtils.createNumber("" + Double.MaxValue))
    // LANG-822
    // ensure that the underlying negative number would create a BigDecimal
    val bigNum = NumberUtils.createNumber("-1.1E-700F")
    assertNotNull(bigNum)
    assertEquals(classOf[BigDecimal], bigNum.getClass)
    // LANG-1018
    assertEquals(
      "createNumber(String) LANG-1018 failed",
      JavaDouble.valueOf("-160952.54"),
      NumberUtils.createNumber("-160952.54"))
    // LANG-1187
    assertEquals(
      "createNumber(String) LANG-1187 failed",
      JavaDouble.valueOf("6264583.33"),
      NumberUtils.createNumber("6264583.33"))
    // LANG-1215
    assertEquals(
      "createNumber(String) LANG-1215 failed",
      JavaDouble.valueOf("193343.82"),
      NumberUtils.createNumber("193343.82"))
    assertEquals(
      "createNumber(String) LANG-1060a failed",
      JavaDouble.valueOf("001234.5678"),
      NumberUtils.createNumber("001234.5678"))
    assertEquals(
      "createNumber(String) LANG-1060b failed",
      JavaDouble.valueOf("+001234.5678"),
      NumberUtils.createNumber("+001234.5678"))
    assertEquals(
      "createNumber(String) LANG-1060c failed",
      JavaDouble.valueOf("-001234.5678"),
      NumberUtils.createNumber("-001234.5678"))
    assertEquals(
      "createNumber(String) LANG-1060d failed",
      JavaDouble.valueOf("0000.00000"),
      NumberUtils.createNumber("0000.00000d"))
    assertEquals(
      "createNumber(String) LANG-1060e failed",
      JavaFloat.valueOf("001234.56"),
      NumberUtils.createNumber("001234.56"))
    assertEquals(
      "createNumber(String) LANG-1060f failed",
      JavaFloat.valueOf("+001234.56"),
      NumberUtils.createNumber("+001234.56"))
    assertEquals(
      "createNumber(String) LANG-1060g failed",
      JavaFloat.valueOf("-001234.56"),
      NumberUtils.createNumber("-001234.56"))
    assertEquals(
      "createNumber(String) LANG-1060h failed",
      JavaFloat.valueOf("0000.10"),
      NumberUtils.createNumber("0000.10"))
    assertEquals(
      "createNumber(String) LANG-1060i failed",
      JavaFloat.valueOf("001.1E20"),
      NumberUtils.createNumber("001.1E20"))
    assertEquals(
      "createNumber(String) LANG-1060j failed",
      JavaFloat.valueOf("+001.1E20"),
      NumberUtils.createNumber("+001.1E20"))
    assertEquals(
      "createNumber(String) LANG-1060k failed",
      JavaFloat.valueOf("-001.1E20"),
      NumberUtils.createNumber("-001.1E20"))
    assertEquals(
      "createNumber(String) LANG-1060l failed",
      JavaDouble.valueOf("001.1E200"),
      NumberUtils.createNumber("001.1E200"))
    assertEquals(
      "createNumber(String) LANG-1060m failed",
      JavaDouble.valueOf("+001.1E200"),
      NumberUtils.createNumber("+001.1E200"))
    assertEquals(
      "createNumber(String) LANG-1060n failed",
      JavaDouble.valueOf("-001.1E200"),
      NumberUtils.createNumber("-001.1E200"))
  }

  @Test def testLang1087(): Unit = { // no sign cases
    assertEquals(classOf[JavaFloat], NumberUtils.createNumber("0.0").getClass)
    assertEquals(JavaFloat.valueOf("0.0"), NumberUtils.createNumber("0.0"))
    // explicit positive sign cases
    assertEquals(classOf[JavaFloat], NumberUtils.createNumber("+0.0").getClass)
    assertEquals(JavaFloat.valueOf("+0.0"), NumberUtils.createNumber("+0.0"))
    // negative sign cases
    assertEquals(classOf[JavaFloat], NumberUtils.createNumber("-0.0").getClass)
    assertEquals(JavaFloat.valueOf("-0.0"), NumberUtils.createNumber("-0.0"))
  }

  @Test def TestLang747(): Unit = {
    assertEquals(Integer.valueOf(0x8000), NumberUtils.createNumber("0x8000"))
    assertEquals(Integer.valueOf(0x80000), NumberUtils.createNumber("0x80000"))
    assertEquals(Integer.valueOf(0x800000), NumberUtils.createNumber("0x800000"))
    assertEquals(Integer.valueOf(0x8000000), NumberUtils.createNumber("0x8000000"))
    assertEquals(Integer.valueOf(0x7fffffff), NumberUtils.createNumber("0x7FFFFFFF"))
    assertEquals(JavaLong.valueOf(0x80000000L), NumberUtils.createNumber("0x80000000"))
    assertEquals(JavaLong.valueOf(0xffffffffL), NumberUtils.createNumber("0xFFFFFFFF"))
    // Leading zero tests
    assertEquals(Integer.valueOf(0x8000000), NumberUtils.createNumber("0x08000000"))
    assertEquals(Integer.valueOf(0x7fffffff), NumberUtils.createNumber("0x007FFFFFFF"))
    assertEquals(JavaLong.valueOf(0x80000000L), NumberUtils.createNumber("0x080000000"))
    assertEquals(JavaLong.valueOf(0xffffffffL), NumberUtils.createNumber("0x00FFFFFFFF"))
    assertEquals(JavaLong.valueOf(0x800000000L), NumberUtils.createNumber("0x800000000"))
    assertEquals(JavaLong.valueOf(0x8000000000L), NumberUtils.createNumber("0x8000000000"))
    assertEquals(JavaLong.valueOf(0x80000000000L), NumberUtils.createNumber("0x80000000000"))
    assertEquals(JavaLong.valueOf(0x800000000000L), NumberUtils.createNumber("0x800000000000"))
    assertEquals(JavaLong.valueOf(0x8000000000000L), NumberUtils.createNumber("0x8000000000000"))
    assertEquals(JavaLong.valueOf(0x80000000000000L), NumberUtils.createNumber("0x80000000000000"))
    assertEquals(JavaLong.valueOf(0x800000000000000L), NumberUtils.createNumber("0x800000000000000"))
    assertEquals(JavaLong.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("0x7FFFFFFFFFFFFFFF"))
    // N.B. Cannot use a hex constant such as 0x8000000000000000L here as that is interpreted as a negative long
    assertEquals(new BigInteger("8000000000000000", 16), NumberUtils.createNumber("0x8000000000000000"))
    assertEquals(new BigInteger("FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("0xFFFFFFFFFFFFFFFF"))
    assertEquals(JavaLong.valueOf(0x80000000000000L), NumberUtils.createNumber("0x00080000000000000"))
    assertEquals(JavaLong.valueOf(0x800000000000000L), NumberUtils.createNumber("0x0800000000000000"))
    assertEquals(JavaLong.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("0x07FFFFFFFFFFFFFFF"))
    assertEquals(new BigInteger("8000000000000000", 16), NumberUtils.createNumber("0x00008000000000000000"))
    assertEquals(new BigInteger("FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("0x0FFFFFFFFFFFFFFFF"))
  }

  @Test // Check that the code fails to create a valid number when preceded by -- rather than -
  def testCreateNumberFailure_1(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("--1.1E-700F"))
    ()
  }

  @Test // Check that the code fails to create a valid number when both e and E are present (with decimal)
  def testCreateNumberFailure_2(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("-1.1E+0-7e00"))
    ()
  }

  @Test // Check that the code fails to create a valid number when both e and E are present (no decimal)
  def testCreateNumberFailure_3(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("-11E+0-7e00"))
    ()
  }

  @Test def testCreateNumberFailure_4(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("1eE+00001"))
    ()
  }

  @Test // Check that the code fails to create a valid number when there are multiple trailing 'f' characters (LANG-1205)
  def testCreateNumberFailure_5(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("1234.5ff"))
    ()
  }

  @Test // Check that the code fails to create a valid number when there are multiple trailing 'F' characters (LANG-1205)
  def testCreateNumberFailure_6(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("1234.5FF"))
    ()
  }

  @Test // Check that the code fails to create a valid number when there are multiple trailing 'd' characters (LANG-1205)
  def testCreateNumberFailure_7(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("1234.5dd"))
    ()
  }

  @Test // Check that the code fails to create a valid number when there are multiple trailing 'D' characters (LANG-1205)
  def testCreateNumberFailure_8(): Unit = {
    assertThrows[NumberFormatException](NumberUtils.createNumber("1234.5DD"))
    ()
  }

  // Tests to show when magnitude causes switch to next Number type
  // Will probably need to be adjusted if code is changed to check precision (LANG-693)
  @Test def testCreateNumberMagnitude()
    : Unit = { // Test Float.MaxValue, and same with +1 in final digit to check conversion changes to next Number type
    assertEquals(JavaFloat.valueOf(Float.MaxValue), NumberUtils.createNumber("3.4028235e+38"))
    assertEquals(JavaDouble.valueOf(3.4028236e+38), NumberUtils.createNumber("3.4028236e+38"))
    // Test Double.MaxValue
    assertEquals(JavaDouble.valueOf(Double.MaxValue), NumberUtils.createNumber("1.7976931348623157e+308"))
    // Test with +2 in final digit (+1 does not cause roll-over to BigDecimal)
    assertEquals(new BigDecimal("1.7976931348623159e+308"), NumberUtils.createNumber("1.7976931348623159e+308"))
    assertEquals(Integer.valueOf(0x12345678), NumberUtils.createNumber("0x12345678"))
    assertEquals(JavaLong.valueOf(0x123456789L), NumberUtils.createNumber("0x123456789"))
    assertEquals(JavaLong.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("0x7fffffffffffffff"))
    // Does not appear to be a way to create a literal BigInteger of this magnitude
    assertEquals(new BigInteger("7fffffffffffffff0", 16), NumberUtils.createNumber("0x7fffffffffffffff0"))
    assertEquals(JavaLong.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("#7fffffffffffffff"))
    assertEquals(new BigInteger("7fffffffffffffff0", 16), NumberUtils.createNumber("#7fffffffffffffff0"))
    //assertEquals(Integer.valueOf(017777777777), NumberUtils.createNumber("017777777777")) // 31 bits
    //assertEquals(JavaLong.valueOf(037777777777L), NumberUtils.createNumber("037777777777")) // 32 bits
    //assertEquals(JavaLong.valueOf(0777777777777777777777L), NumberUtils.createNumber("0777777777777777777777")) // 63 bits
    assertEquals(
      new BigInteger("1777777777777777777777", 8),
      NumberUtils.createNumber("01777777777777777777777")
    ) // 64 bits
  }

  @Test def testCreateFloat(): Unit = {
    assertEquals("createFloat(String) failed", JavaFloat.valueOf("1234.5"), NumberUtils.createFloat("1234.5"))
    assertNull("createFloat(null) failed", NumberUtils.createFloat(null))
    this.testCreateFloatFailure("")
    this.testCreateFloatFailure(" ")
    this.testCreateFloatFailure("\b\t\n\f\r")
    // Funky whitespaces
    this.testCreateFloatFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
  }

  protected def testCreateFloatFailure(str: String): Unit = {
    assertThrows[NumberFormatException](
      NumberUtils.createFloat(str)
    ) //, "createFloat(\"" + str + "\") should have failed.")
    ()
  }

  @Test def testCreateDouble(): Unit = {
    assertEquals("createDouble(String) failed", JavaDouble.valueOf("1234.5"), NumberUtils.createDouble("1234.5"))
    assertNull("createDouble(null) failed", NumberUtils.createDouble(null))
    this.testCreateDoubleFailure("")
    this.testCreateDoubleFailure(" ")
    this.testCreateDoubleFailure("\b\t\n\f\r")
    this.testCreateDoubleFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
  }

  protected def testCreateDoubleFailure(str: String): Unit = {
    assertThrows[NumberFormatException](
      NumberUtils.createDouble(str)
    ) //, "createDouble(\"" + str + "\") should have failed.")
    ()
  }

  @Test def testCreateInteger(): Unit = {
    assertEquals("createInteger(String) failed", Integer.valueOf("12345"), NumberUtils.createInteger("12345"))
    assertNull("createInteger(null) failed", NumberUtils.createInteger(null))
    this.testCreateIntegerFailure("")
    this.testCreateIntegerFailure(" ")
    this.testCreateIntegerFailure("\b\t\n\f\r")
    this.testCreateIntegerFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
  }

  protected def testCreateIntegerFailure(str: String): Unit = {
    assertThrows[NumberFormatException](
      NumberUtils.createInteger(str)
    ) //, "createInteger(\"" + str + "\") should have failed.")
    ()
  }

  @Test def testCreateLong(): Unit = {
    assertEquals("createLong(String) failed", JavaLong.valueOf("12345"), NumberUtils.createLong("12345"))
    assertNull("createLong(null) failed", NumberUtils.createLong(null))
    this.testCreateLongFailure("")
    this.testCreateLongFailure(" ")
    this.testCreateLongFailure("\b\t\n\f\r")
    this.testCreateLongFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
  }

  protected def testCreateLongFailure(str: String): Unit = {
    assertThrows[NumberFormatException](
      NumberUtils.createLong(str)
    ) //, "createLong(\"" + str + "\") should have failed.")
    ()
  }

  @Test def testCreateBigInteger(): Unit = {
    assertEquals("createBigInteger(String) failed", new BigInteger("12345"), NumberUtils.createBigInteger("12345"))
    assertNull("createBigInteger(null) failed", NumberUtils.createBigInteger(null))
    this.testCreateBigIntegerFailure("")
    this.testCreateBigIntegerFailure(" ")
    this.testCreateBigIntegerFailure("\b\t\n\f\r")
    this.testCreateBigIntegerFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
    assertEquals("createBigInteger(String) failed", new BigInteger("255"), NumberUtils.createBigInteger("0xff"))
    assertEquals("createBigInteger(String) failed", new BigInteger("255"), NumberUtils.createBigInteger("0Xff"))
    assertEquals("createBigInteger(String) failed", new BigInteger("255"), NumberUtils.createBigInteger("#ff"))
    assertEquals("createBigInteger(String) failed", new BigInteger("-255"), NumberUtils.createBigInteger("-0xff"))
    assertEquals("createBigInteger(String) failed", new BigInteger("255"), NumberUtils.createBigInteger("0377"))
    assertEquals("createBigInteger(String) failed", new BigInteger("-255"), NumberUtils.createBigInteger("-0377"))
    assertEquals("createBigInteger(String) failed", new BigInteger("-255"), NumberUtils.createBigInteger("-0377"))
    assertEquals("createBigInteger(String) failed", new BigInteger("-0"), NumberUtils.createBigInteger("-0"))
    assertEquals("createBigInteger(String) failed", new BigInteger("0"), NumberUtils.createBigInteger("0"))
    testCreateBigIntegerFailure("#")
    testCreateBigIntegerFailure("-#")
    testCreateBigIntegerFailure("0x")
    testCreateBigIntegerFailure("-0x")
  }

  protected def testCreateBigIntegerFailure(str: String): Unit = {
    assertThrows[NumberFormatException](
      NumberUtils.createBigInteger(str)
    ) //, "createBigInteger(\"" + str + "\") should have failed.")
    ()
  }

  @Test def testCreateBigDecimal(): Unit = {
    assertEquals("createBigDecimal(String) failed", new BigDecimal("1234.5"), NumberUtils.createBigDecimal("1234.5"))
    assertNull("createBigDecimal(null) failed", NumberUtils.createBigDecimal(null))
    this.testCreateBigDecimalFailure("")
    this.testCreateBigDecimalFailure(" ")
    this.testCreateBigDecimalFailure("\b\t\n\f\r")
    this.testCreateBigDecimalFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
    this.testCreateBigDecimalFailure("-") // sign alone not valid
    this.testCreateBigDecimalFailure(
      "--"
    ) // comment in NumberUtils suggests some implementations may incorrectly allow this
    this.testCreateBigDecimalFailure("--0")
    this.testCreateBigDecimalFailure("+")
    this.testCreateBigDecimalFailure("++") // in case this was also allowed by some JVMs
    this.testCreateBigDecimalFailure("++0")
  }

  protected def testCreateBigDecimalFailure(str: String): Unit = {
    assertThrows[NumberFormatException](
      NumberUtils.createBigDecimal(str)
    ) //, "createBigDecimal(\"" + str + "\") should have failed.")
    ()
  }

  // min/max tests
  // ----------------------------------------------------------------------
  @Test def testMinLong_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.min(null.asInstanceOf[Array[Long]]))
    ()
  }

  @Test def testMinLong_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.min())
    ()
  }

  @Test def testMinLong(): Unit = {
    assertEquals("min(long[]) failed for array length 1", 5, NumberUtils.min(5))
    assertEquals("min(long[]) failed for array length 2", 6, NumberUtils.min(6, 9))
    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
  }

  @Test def testMinInt_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.min(null.asInstanceOf[Array[Int]]))
    ()
  }

  @Test def testMinInt_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.min())
    ()
  }

  @Test def testMinInt(): Unit = {
    assertEquals("min(int[]) failed for array length 1", 5, NumberUtils.min(5))
    assertEquals("min(int[]) failed for array length 2", 6, NumberUtils.min(6, 9))
    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
  }

  @Test def testMinShort_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.min(null.asInstanceOf[Array[Short]]))
    ()
  }

  @Test def testMinShort_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.min())
    ()
  }

  @Test def testMinShort(): Unit = {
    assertEquals("min(short[]) failed for array length 1", 5, NumberUtils.min(5))
    assertEquals("min(short[]) failed for array length 2", 6, NumberUtils.min(6, 9))
    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
  }

  @Test def testMinByte_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.min(null.asInstanceOf[Array[Byte]]))
    ()
  }

  @Test def testMinByte_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.min())
    ()
  }

  @Test def testMinByte(): Unit = {
    assertEquals("min(byte[]) failed for array length 1", 5, NumberUtils.min(5))
    assertEquals("min(byte[]) failed for array length 2", 6, NumberUtils.min(6, 9))
    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
  }

  @Test def testMinDouble_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.min(null.asInstanceOf[Array[Double]]))
    ()
  }

  @Test def testMinDouble_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.min())
    ()
  }

  @Test def testMinDouble(): Unit = {
    assertEquals("min(double[]) failed for array length 1", 5.12, NumberUtils.min(5.12), DoubleDelta)
    assertEquals("min(double[]) failed for array length 2", 6.23, NumberUtils.min(6.23, 9.34), DoubleDelta)
    assertEquals(
      "min(double[]) failed for array length 5",
      -10.45,
      NumberUtils.min(-10.45, -5.56, 0, 5.67, 10.78),
      DoubleDelta)
    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10), 0.0001)
    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10), 0.0001)
  }

  @Test def testMinFloat_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.min(null.asInstanceOf[Array[Float]]))
    ()
  }

  @Test def testMinFloat_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.min())
    ()
  }

  @Test def testMinFloat(): Unit = {
    assertEquals("min(float[]) failed for array length 1", 5.9f, NumberUtils.min(5.9f), FloatDelta)
    assertEquals("min(float[]) failed for array length 2", 6.8f, NumberUtils.min(6.8f, 9.7f), FloatDelta)
    assertEquals(
      "min(float[]) failed for array length 5",
      -10.6f,
      NumberUtils.min(-10.6f, -5.5f, 0, 5.4f, 10.3f),
      FloatDelta)
    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10), 0.0001f)
    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10), 0.0001f)
  }

  @Test def testMaxLong_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.max(null.asInstanceOf[Array[Long]]))
    ()
  }

  @Test def testMaxLong_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.max())
    ()
  }

  @Test def testMaxLong(): Unit = {
    assertEquals("max(long[]) failed for array length 1", 5, NumberUtils.max(5))
    assertEquals("max(long[]) failed for array length 2", 9, NumberUtils.max(6, 9))
    assertEquals("max(long[]) failed for array length 5", 10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
  }

  @Test def testMaxInt_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.max(null.asInstanceOf[Array[Int]]))
    ()
  }

  @Test def testMaxInt_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.max())
    ()
  }

  @Test def testMaxInt(): Unit = {
    assertEquals("max(int[]) failed for array length 1", 5, NumberUtils.max(5))
    assertEquals("max(int[]) failed for array length 2", 9, NumberUtils.max(6, 9))
    assertEquals("max(int[]) failed for array length 5", 10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
  }

  @Test def testMaxShort_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.max(null.asInstanceOf[Array[Short]]))
    ()
  }

  @Test def testMaxShort_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.max())
    ()
  }

  @Test def testMaxShort(): Unit = {
    assertEquals("max(short[]) failed for array length 1", 5, NumberUtils.max(5))
    assertEquals("max(short[]) failed for array length 2", 9, NumberUtils.max(6, 9))
    assertEquals("max(short[]) failed for array length 5", 10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
  }

  @Test def testMaxByte_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.max(null.asInstanceOf[Array[Byte]]))
    ()
  }

  @Test def testMaxByte_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.max())
    ()
  }

  @Test def testMaxByte(): Unit = {
    assertEquals("max(byte[]) failed for array length 1", 5, NumberUtils.max(5))
    assertEquals("max(byte[]) failed for array length 2", 9, NumberUtils.max(6, 9))
    assertEquals("max(byte[]) failed for array length 5", 10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
  }

  @Test def testMaxDouble_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.max(null.asInstanceOf[Array[Double]]))
    ()
  }

  @Test def testMaxDouble_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.max())
    ()
  }

  @Test def testMaxDouble(): Unit = {
    assertThrows[NullPointerException](
      NumberUtils.max(null.asInstanceOf[Array[JavaDouble]])
    ) //, "No exception was thrown for null input.")
    assertThrows[IllegalArgumentException](NumberUtils.max()) //, "No exception was thrown for empty input.")
    assertEquals("max(double[]) failed for array length 1", 5.1f, NumberUtils.max(5.1f), DoubleDelta)
    assertEquals("max(double[]) failed for array length 2", 9.2f, NumberUtils.max(6.3f, 9.2f), DoubleDelta)
    assertEquals(
      "max(double[]) failed for float length 5",
      10.4f,
      NumberUtils.max(-10.5f, -5.6f, 0, 5.7f, 10.4f),
      DoubleDelta)
    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), 0.0001)
    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10), 0.0001)
  }

  @Test def testMaxFloat_nullArray(): Unit = {
    assertThrows[NullPointerException](NumberUtils.max(null.asInstanceOf[Array[Float]]))
    ()
  }

  @Test def testMaxFloat_emptyArray(): Unit = {
    assertThrows[IllegalArgumentException](NumberUtils.max())
    ()
  }

  @Test def testMaxFloat(): Unit = {
    assertEquals("max(float[]) failed for array length 1", 5.1f, NumberUtils.max(5.1f), FloatDelta)
    assertEquals("max(float[]) failed for array length 2", 9.2f, NumberUtils.max(6.3f, 9.2f), FloatDelta)
    assertEquals(
      "max(float[]) failed for float length 5",
      10.4f,
      NumberUtils.max(-10.5f, -5.6f, 0, 5.7f, 10.4f),
      FloatDelta)
    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), 0.0001f)
    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10), 0.0001f)
  }

  @Test def testMinimumLong(): Unit = {
    assertEquals("minimum(long, long, long) 1 failed", 12345L, NumberUtils.min(12345L, 12345L + 1L, 12345L + 2L))
    assertEquals("minimum(long, long, long) 2 failed", 12345L, NumberUtils.min(12345L + 1L, 12345L, 12345 + 2L))
    assertEquals("minimum(long, long, long) 3 failed", 12345L, NumberUtils.min(12345L + 1L, 12345L + 2L, 12345L))
    assertEquals("minimum(long, long, long) 4 failed", 12345L, NumberUtils.min(12345L + 1L, 12345L, 12345L))
    assertEquals("minimum(long, long, long) 5 failed", 12345L, NumberUtils.min(12345L, 12345L, 12345L))
  }

  @Test def testMinimumInt(): Unit = {
    assertEquals("minimum(int, int, int) 1 failed", 12345, NumberUtils.min(12345, 12345 + 1, 12345 + 2))
    assertEquals("minimum(int, int, int) 2 failed", 12345, NumberUtils.min(12345 + 1, 12345, 12345 + 2))
    assertEquals("minimum(int, int, int) 3 failed", 12345, NumberUtils.min(12345 + 1, 12345 + 2, 12345))
    assertEquals("minimum(int, int, int) 4 failed", 12345, NumberUtils.min(12345 + 1, 12345, 12345))
    assertEquals("minimum(int, int, int) 5 failed", 12345, NumberUtils.min(12345, 12345, 12345))
  }

  @Test def testMinimumShort(): Unit = {
    val low: Short = 1234
    val mid: Short = 1234 + 1
    val high: Short = 1234 + 2
    assertEquals("minimum(short, short, short) 1 failed", low, NumberUtils.min(low, mid, high))
    assertEquals("minimum(short, short, short) 2 failed", low, NumberUtils.min(mid, low, high))
    assertEquals("minimum(short, short, short) 3 failed", low, NumberUtils.min(mid, high, low))
    assertEquals("minimum(short, short, short) 4 failed", low, NumberUtils.min(low, mid, low))
  }

  @Test def testMinimumByte(): Unit = {
    val low: Byte = 123
    val mid: Byte = 123 + 1
    val high: Byte = 123 + 2
    assertEquals("minimum(byte, byte, byte) 1 failed", low, NumberUtils.min(low, mid, high))
    assertEquals("minimum(byte, byte, byte) 2 failed", low, NumberUtils.min(mid, low, high))
    assertEquals("minimum(byte, byte, byte) 3 failed", low, NumberUtils.min(mid, high, low))
    assertEquals("minimum(byte, byte, byte) 4 failed", low, NumberUtils.min(low, mid, low))
  }

  @Test def testMinimumDouble(): Unit = {
    val low: Double = 12.3
    val mid: Double = 12.3 + 1
    val high: Double = 12.3 + 2
    assertEquals(low, NumberUtils.min(low, mid, high), 0.0001)
    assertEquals(low, NumberUtils.min(mid, low, high), 0.0001)
    assertEquals(low, NumberUtils.min(mid, high, low), 0.0001)
    assertEquals(low, NumberUtils.min(low, mid, low), 0.0001)
    assertEquals(mid, NumberUtils.min(high, mid, high), 0.0001)
  }

  @Test def testMinimumFloat(): Unit = {
    val low: Float = 12.3f
    val mid: Float = 12.3f + 1
    val high: Float = 12.3f + 2
    assertEquals(low, NumberUtils.min(low, mid, high), 0.0001f)
    assertEquals(low, NumberUtils.min(mid, low, high), 0.0001f)
    assertEquals(low, NumberUtils.min(mid, high, low), 0.0001f)
    assertEquals(low, NumberUtils.min(low, mid, low), 0.0001f)
    assertEquals(mid, NumberUtils.min(high, mid, high), 0.0001f)
  }

  @Test def testMaximumLong(): Unit = {
    assertEquals("maximum(long, long, long) 1 failed", 12345L, NumberUtils.max(12345L, 12345L - 1L, 12345L - 2L))
    assertEquals("maximum(long, long, long) 2 failed", 12345L, NumberUtils.max(12345L - 1L, 12345L, 12345L - 2L))
    assertEquals("maximum(long, long, long) 3 failed", 12345L, NumberUtils.max(12345L - 1L, 12345L - 2L, 12345L))
    assertEquals("maximum(long, long, long) 4 failed", 12345L, NumberUtils.max(12345L - 1L, 12345L, 12345L))
    assertEquals("maximum(long, long, long) 5 failed", 12345L, NumberUtils.max(12345L, 12345L, 12345L))
  }

  @Test def testMaximumInt(): Unit = {
    assertEquals("maximum(int, int, int) 1 failed", 12345, NumberUtils.max(12345, 12345 - 1, 12345 - 2))
    assertEquals("maximum(int, int, int) 2 failed", 12345, NumberUtils.max(12345 - 1, 12345, 12345 - 2))
    assertEquals("maximum(int, int, int) 3 failed", 12345, NumberUtils.max(12345 - 1, 12345 - 2, 12345))
    assertEquals("maximum(int, int, int) 4 failed", 12345, NumberUtils.max(12345 - 1, 12345, 12345))
    assertEquals("maximum(int, int, int) 5 failed", 12345, NumberUtils.max(12345, 12345, 12345))
  }

  @Test def testMaximumShort(): Unit = {
    val low: Short = 1234
    val mid: Short = 1234 + 1
    val high: Short = 1234 + 2
    assertEquals("maximum(short, short, short) 1 failed", high, NumberUtils.max(low, mid, high))
    assertEquals("maximum(short, short, short) 2 failed", high, NumberUtils.max(mid, low, high))
    assertEquals("maximum(short, short, short) 3 failed", high, NumberUtils.max(mid, high, low))
    assertEquals("maximum(short, short, short) 4 failed", high, NumberUtils.max(high, mid, high))
  }

  @Test def testMaximumByte(): Unit = {
    val low: Byte = 123
    val mid: Byte = 123 + 1
    val high: Byte = 123 + 2
    assertEquals("maximum(byte, byte, byte) 1 failed", high, NumberUtils.max(low, mid, high))
    assertEquals("maximum(byte, byte, byte) 2 failed", high, NumberUtils.max(mid, low, high))
    assertEquals("maximum(byte, byte, byte) 3 failed", high, NumberUtils.max(mid, high, low))
    assertEquals("maximum(byte, byte, byte) 4 failed", high, NumberUtils.max(high, mid, high))
  }

  @Test def testMaximumDouble(): Unit = {
    val low = 12.3
    val mid = 12.3 + 1
    val high = 12.3 + 2
    assertEquals(high, NumberUtils.max(low, mid, high), 0.0001)
    assertEquals(high, NumberUtils.max(mid, low, high), 0.0001)
    assertEquals(high, NumberUtils.max(mid, high, low), 0.0001)
    assertEquals(mid, NumberUtils.max(low, mid, low), 0.0001)
    assertEquals(high, NumberUtils.max(high, mid, high), 0.0001)
  }

  @Test def testMaximumFloat(): Unit = {
    val low = 12.3f
    val mid = 12.3f + 1
    val high = 12.3f + 2
    assertEquals(high, NumberUtils.max(low, mid, high), 0.0001f)
    assertEquals(high, NumberUtils.max(mid, low, high), 0.0001f)
    assertEquals(high, NumberUtils.max(mid, high, low), 0.0001f)
    assertEquals(mid, NumberUtils.max(low, mid, low), 0.0001f)
    assertEquals(high, NumberUtils.max(high, mid, high), 0.0001f)
  }

  // Testing JDK against old Lang functionality
  @Test def testCompareDouble(): Unit = {
    assertEquals(0, JavaDouble.compare(Double.NaN, Double.NaN))
    assertEquals(JavaDouble.compare(Double.NaN, Double.PositiveInfinity), +1)
    assertEquals(JavaDouble.compare(Double.NaN, Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(Double.NaN, 1.2d), +1)
    assertEquals(JavaDouble.compare(Double.NaN, 0.0d), +1)
    assertEquals(JavaDouble.compare(Double.NaN, -0.0d), +1)
    assertEquals(JavaDouble.compare(Double.NaN, -1.2d), +1)
    assertEquals(JavaDouble.compare(Double.NaN, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(Double.NaN, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, Double.NaN), -1)
    assertEquals(0, JavaDouble.compare(Double.PositiveInfinity, Double.PositiveInfinity))
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, 1.2d), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, 0.0d), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, -0.0d), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, -1.2d), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(Double.PositiveInfinity, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(Double.MaxValue, Double.NaN), -1)
    assertEquals(JavaDouble.compare(Double.MaxValue, Double.PositiveInfinity), -1)
    assertEquals(0, JavaDouble.compare(Double.MaxValue, Double.MaxValue))
    assertEquals(JavaDouble.compare(Double.MaxValue, 1.2d), +1)
    assertEquals(JavaDouble.compare(Double.MaxValue, 0.0d), +1)
    assertEquals(JavaDouble.compare(Double.MaxValue, -0.0d), +1)
    assertEquals(JavaDouble.compare(Double.MaxValue, -1.2d), +1)
    assertEquals(JavaDouble.compare(Double.MaxValue, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(Double.MaxValue, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(1.2d, Double.NaN), -1)
    assertEquals(JavaDouble.compare(1.2d, Double.PositiveInfinity), -1)
    assertEquals(JavaDouble.compare(1.2d, Double.MaxValue), -1)
    assertEquals(0, JavaDouble.compare(1.2d, 1.2d))
    assertEquals(JavaDouble.compare(1.2d, 0.0d), +1)
    assertEquals(JavaDouble.compare(1.2d, -0.0d), +1)
    assertEquals(JavaDouble.compare(1.2d, -1.2d), +1)
    assertEquals(JavaDouble.compare(1.2d, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(1.2d, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(0.0d, Double.NaN), -1)
    assertEquals(JavaDouble.compare(0.0d, Double.PositiveInfinity), -1)
    assertEquals(JavaDouble.compare(0.0d, Double.MaxValue), -1)
    assertEquals(JavaDouble.compare(0.0d, 1.2d), -1)
    assertEquals(0, JavaDouble.compare(0.0d, 0.0d))
    assertEquals(JavaDouble.compare(0.0d, -0.0d), +1)
    assertEquals(JavaDouble.compare(0.0d, -1.2d), +1)
    assertEquals(JavaDouble.compare(0.0d, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(0.0d, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(-0.0d, Double.NaN), -1)
    assertEquals(JavaDouble.compare(-0.0d, Double.PositiveInfinity), -1)
    assertEquals(JavaDouble.compare(-0.0d, Double.MaxValue), -1)
    assertEquals(JavaDouble.compare(-0.0d, 1.2d), -1)
    assertEquals(JavaDouble.compare(-0.0d, 0.0d), -1)
    assertEquals(0, JavaDouble.compare(-0.0d, -0.0d))
    assertEquals(JavaDouble.compare(-0.0d, -1.2d), +1)
    assertEquals(JavaDouble.compare(-0.0d, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(-0.0d, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(-1.2d, Double.NaN), -1)
    assertEquals(JavaDouble.compare(-1.2d, Double.PositiveInfinity), -1)
    assertEquals(JavaDouble.compare(-1.2d, Double.MaxValue), -1)
    assertEquals(JavaDouble.compare(-1.2d, 1.2d), -1)
    assertEquals(JavaDouble.compare(-1.2d, 0.0d), -1)
    assertEquals(JavaDouble.compare(-1.2d, -0.0d), -1)
    assertEquals(0, JavaDouble.compare(-1.2d, -1.2d))
    assertEquals(JavaDouble.compare(-1.2d, -Double.MaxValue), +1)
    assertEquals(JavaDouble.compare(-1.2d, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, Double.NaN), -1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, Double.PositiveInfinity), -1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, Double.MaxValue), -1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, 1.2d), -1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, 0.0d), -1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, -0.0d), -1)
    assertEquals(JavaDouble.compare(-Double.MaxValue, -1.2d), -1)
    assertEquals(0, JavaDouble.compare(-Double.MaxValue, -Double.MaxValue))
    assertEquals(JavaDouble.compare(-Double.MaxValue, Double.NegativeInfinity), +1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, Double.NaN), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, Double.PositiveInfinity), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, Double.MaxValue), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, 1.2d), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, 0.0d), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, -0.0d), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, -1.2d), -1)
    assertEquals(JavaDouble.compare(Double.NegativeInfinity, -Double.MaxValue), -1)
    assertEquals(0, JavaDouble.compare(Double.NegativeInfinity, Double.NegativeInfinity))
  }

  @Test def testCompareFloat(): Unit = {
    assertEquals(0, JavaFloat.compare(Float.NaN, Float.NaN))
    assertEquals(JavaFloat.compare(Float.NaN, Float.PositiveInfinity), +1)
    assertEquals(JavaFloat.compare(Float.NaN, Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(Float.NaN, 1.2f), +1)
    assertEquals(JavaFloat.compare(Float.NaN, 0.0f), +1)
    assertEquals(JavaFloat.compare(Float.NaN, -0.0f), +1)
    assertEquals(JavaFloat.compare(Float.NaN, -1.2f), +1)
    assertEquals(JavaFloat.compare(Float.NaN, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(Float.NaN, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, Float.NaN), -1)
    assertEquals(0, JavaFloat.compare(Float.PositiveInfinity, Float.PositiveInfinity))
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, 1.2f), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, 0.0f), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, -0.0f), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, -1.2f), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(Float.PositiveInfinity, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(Float.MaxValue, Float.NaN), -1)
    assertEquals(JavaFloat.compare(Float.MaxValue, Float.PositiveInfinity), -1)
    assertEquals(0, JavaFloat.compare(Float.MaxValue, Float.MaxValue))
    assertEquals(JavaFloat.compare(Float.MaxValue, 1.2f), +1)
    assertEquals(JavaFloat.compare(Float.MaxValue, 0.0f), +1)
    assertEquals(JavaFloat.compare(Float.MaxValue, -0.0f), +1)
    assertEquals(JavaFloat.compare(Float.MaxValue, -1.2f), +1)
    assertEquals(JavaFloat.compare(Float.MaxValue, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(Float.MaxValue, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(1.2f, Float.NaN), -1)
    assertEquals(JavaFloat.compare(1.2f, Float.PositiveInfinity), -1)
    assertEquals(JavaFloat.compare(1.2f, Float.MaxValue), -1)
    assertEquals(0, JavaFloat.compare(1.2f, 1.2f))
    assertEquals(JavaFloat.compare(1.2f, 0.0f), +1)
    assertEquals(JavaFloat.compare(1.2f, -0.0f), +1)
    assertEquals(JavaFloat.compare(1.2f, -1.2f), +1)
    assertEquals(JavaFloat.compare(1.2f, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(1.2f, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(0.0f, Float.NaN), -1)
    assertEquals(JavaFloat.compare(0.0f, Float.PositiveInfinity), -1)
    assertEquals(JavaFloat.compare(0.0f, Float.MaxValue), -1)
    assertEquals(JavaFloat.compare(0.0f, 1.2f), -1)
    assertEquals(0, JavaFloat.compare(0.0f, 0.0f))
    assertEquals(JavaFloat.compare(0.0f, -0.0f), +1)
    assertEquals(JavaFloat.compare(0.0f, -1.2f), +1)
    assertEquals(JavaFloat.compare(0.0f, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(0.0f, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(-0.0f, Float.NaN), -1)
    assertEquals(JavaFloat.compare(-0.0f, Float.PositiveInfinity), -1)
    assertEquals(JavaFloat.compare(-0.0f, Float.MaxValue), -1)
    assertEquals(JavaFloat.compare(-0.0f, 1.2f), -1)
    assertEquals(JavaFloat.compare(-0.0f, 0.0f), -1)
    assertEquals(0, JavaFloat.compare(-0.0f, -0.0f))
    assertEquals(JavaFloat.compare(-0.0f, -1.2f), +1)
    assertEquals(JavaFloat.compare(-0.0f, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(-0.0f, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(-1.2f, Float.NaN), -1)
    assertEquals(JavaFloat.compare(-1.2f, Float.PositiveInfinity), -1)
    assertEquals(JavaFloat.compare(-1.2f, Float.MaxValue), -1)
    assertEquals(JavaFloat.compare(-1.2f, 1.2f), -1)
    assertEquals(JavaFloat.compare(-1.2f, 0.0f), -1)
    assertEquals(JavaFloat.compare(-1.2f, -0.0f), -1)
    assertEquals(0, JavaFloat.compare(-1.2f, -1.2f))
    assertEquals(JavaFloat.compare(-1.2f, -Float.MaxValue), +1)
    assertEquals(JavaFloat.compare(-1.2f, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, Float.NaN), -1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, Float.PositiveInfinity), -1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, Float.MaxValue), -1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, 1.2f), -1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, 0.0f), -1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, -0.0f), -1)
    assertEquals(JavaFloat.compare(-Float.MaxValue, -1.2f), -1)
    assertEquals(0, JavaFloat.compare(-Float.MaxValue, -Float.MaxValue))
    assertEquals(JavaFloat.compare(-Float.MaxValue, Float.NegativeInfinity), +1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, Float.NaN), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, Float.PositiveInfinity), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, Float.MaxValue), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, 1.2f), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, 0.0f), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, -0.0f), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, -1.2f), -1)
    assertEquals(JavaFloat.compare(Float.NegativeInfinity, -Float.MaxValue), -1)
    assertEquals(0, JavaFloat.compare(Float.NegativeInfinity, Float.NegativeInfinity))
  }

  @Test def testIsDigits(): Unit = {
    assertFalse("isDigits(null) failed", NumberUtils.isDigits(null))
    assertFalse("isDigits('') failed", NumberUtils.isDigits(""))
    assertTrue("isDigits(String) failed", NumberUtils.isDigits("12345"))
    assertFalse("isDigits(String) neg 1 failed", NumberUtils.isDigits("1234.5"))
    assertFalse("isDigits(String) neg 3 failed", NumberUtils.isDigits("1ab"))
    assertFalse("isDigits(String) neg 4 failed", NumberUtils.isDigits("abc"))
  }

  /**
    * Tests isCreatable(String) and tests that createNumber(String) returns
    * a valid number iff isCreatable(String) returns false.
    */
  @Test def testIsCreatable(): Unit = {
    compareIsCreatableWithCreateNumber("12345", true)
    compareIsCreatableWithCreateNumber("1234.5", true)
    compareIsCreatableWithCreateNumber(".12345", true)
    compareIsCreatableWithCreateNumber("1234E5", true)
    compareIsCreatableWithCreateNumber("1234E+5", true)
    compareIsCreatableWithCreateNumber("1234E-5", true)
    compareIsCreatableWithCreateNumber("123.4E5", true)
    compareIsCreatableWithCreateNumber("-1234", true)
    compareIsCreatableWithCreateNumber("-1234.5", true)
    compareIsCreatableWithCreateNumber("-.12345", true)
    compareIsCreatableWithCreateNumber("-1234E5", true)
    compareIsCreatableWithCreateNumber("0", true)
    compareIsCreatableWithCreateNumber("0.1", true) // LANG-1216
    compareIsCreatableWithCreateNumber("-0", true)
    compareIsCreatableWithCreateNumber("01234", true)
    compareIsCreatableWithCreateNumber("-01234", true)
    compareIsCreatableWithCreateNumber("-0xABC123", true)
    compareIsCreatableWithCreateNumber("-0x0", true)
    compareIsCreatableWithCreateNumber("123.4E21D", true)
    compareIsCreatableWithCreateNumber("-221.23F", true)
    compareIsCreatableWithCreateNumber("22338L", true)
    compareIsCreatableWithCreateNumber(null, false)
    compareIsCreatableWithCreateNumber("", false)
    compareIsCreatableWithCreateNumber(" ", false)
    compareIsCreatableWithCreateNumber("\r\n\t", false)
    compareIsCreatableWithCreateNumber("--2.3", false)
    compareIsCreatableWithCreateNumber(".12.3", false)
    compareIsCreatableWithCreateNumber("-123E", false)
    compareIsCreatableWithCreateNumber("-123E+-212", false)
    compareIsCreatableWithCreateNumber("-123E2.12", false)
    compareIsCreatableWithCreateNumber("0xGF", false)
    compareIsCreatableWithCreateNumber("0xFAE-1", false)
    compareIsCreatableWithCreateNumber(".", false)
    compareIsCreatableWithCreateNumber("-0ABC123", false)
    compareIsCreatableWithCreateNumber("123.4E-D", false)
    compareIsCreatableWithCreateNumber("123.4ED", false)
    compareIsCreatableWithCreateNumber("1234E5l", false)
    compareIsCreatableWithCreateNumber("11a", false)
    compareIsCreatableWithCreateNumber("1a", false)
    compareIsCreatableWithCreateNumber("a", false)
    compareIsCreatableWithCreateNumber("11g", false)
    compareIsCreatableWithCreateNumber("11z", false)
    compareIsCreatableWithCreateNumber("11def", false)
    compareIsCreatableWithCreateNumber("11d11", false)
    compareIsCreatableWithCreateNumber("11 11", false)
    compareIsCreatableWithCreateNumber(" 1111", false)
    compareIsCreatableWithCreateNumber("1111 ", false)
    compareIsCreatableWithCreateNumber("2.", true)
    compareIsCreatableWithCreateNumber("1.1L", false) // LANG-664
  }

  @Test def testLANG971(): Unit = {
    compareIsCreatableWithCreateNumber("0085", false)
    compareIsCreatableWithCreateNumber("085", false)
    compareIsCreatableWithCreateNumber("08", false)
    compareIsCreatableWithCreateNumber("07", true)
    compareIsCreatableWithCreateNumber("00", true)
  }

  @Test def testLANG992(): Unit = {
    compareIsCreatableWithCreateNumber("0.0", true)
    compareIsCreatableWithCreateNumber("0.4790", true)
  }

  @Test def testLANG972(): Unit = {
    compareIsCreatableWithCreateNumber("0xABCD", true)
    compareIsCreatableWithCreateNumber("0XABCD", true)
  }

  @Test def testLANG1252(): Unit = {
    compareIsCreatableWithCreateNumber("+2", true)
    compareIsCreatableWithCreateNumber("+2.0", true)
  }

  private def compareIsCreatableWithCreateNumber(`val`: String, expected: Boolean): Unit = {
    val isValid = NumberUtils.isCreatable(`val`)
    val canCreate = checkCreateNumber(`val`)
    assertTrue(
      "Expecting " + expected + " for isCreatable/createNumber using \"" + `val` + "\" but got " + isValid + " and " + canCreate,
      isValid == expected && canCreate == expected
    )
  }

  @Test def testIsNumber(): Unit = {
    compareIsNumberWithCreateNumber("12345", true)
    compareIsNumberWithCreateNumber("1234.5", true)
    compareIsNumberWithCreateNumber(".12345", true)
    compareIsNumberWithCreateNumber("1234E5", true)
    compareIsNumberWithCreateNumber("1234E+5", true)
    compareIsNumberWithCreateNumber("1234E-5", true)
    compareIsNumberWithCreateNumber("123.4E5", true)
    compareIsNumberWithCreateNumber("-1234", true)
    compareIsNumberWithCreateNumber("-1234.5", true)
    compareIsNumberWithCreateNumber("-.12345", true)
    compareIsNumberWithCreateNumber("-0001.12345", true)
    compareIsNumberWithCreateNumber("-000.12345", true)
    compareIsNumberWithCreateNumber("+00.12345", true)
    compareIsNumberWithCreateNumber("+0002.12345", true)
    compareIsNumberWithCreateNumber("-1234E5", true)
    compareIsNumberWithCreateNumber("0", true)
    compareIsNumberWithCreateNumber("-0", true)
    compareIsNumberWithCreateNumber("01234", true)
    compareIsNumberWithCreateNumber("-01234", true)
    compareIsNumberWithCreateNumber("-0xABC123", true)
    compareIsNumberWithCreateNumber("-0x0", true)
    compareIsNumberWithCreateNumber("123.4E21D", true)
    compareIsNumberWithCreateNumber("-221.23F", true)
    compareIsNumberWithCreateNumber("22338L", true)
    compareIsNumberWithCreateNumber(null, false)
    compareIsNumberWithCreateNumber("", false)
    compareIsNumberWithCreateNumber(" ", false)
    compareIsNumberWithCreateNumber("\r\n\t", false)
    compareIsNumberWithCreateNumber("--2.3", false)
    compareIsNumberWithCreateNumber(".12.3", false)
    compareIsNumberWithCreateNumber("-123E", false)
    compareIsNumberWithCreateNumber("-123E+-212", false)
    compareIsNumberWithCreateNumber("-123E2.12", false)
    compareIsNumberWithCreateNumber("0xGF", false)
    compareIsNumberWithCreateNumber("0xFAE-1", false)
    compareIsNumberWithCreateNumber(".", false)
    compareIsNumberWithCreateNumber("-0ABC123", false)
    compareIsNumberWithCreateNumber("123.4E-D", false)
    compareIsNumberWithCreateNumber("123.4ED", false)
    compareIsNumberWithCreateNumber("+000E.12345", false)
    compareIsNumberWithCreateNumber("-000E.12345", false)
    compareIsNumberWithCreateNumber("1234E5l", false)
    compareIsNumberWithCreateNumber("11a", false)
    compareIsNumberWithCreateNumber("1a", false)
    compareIsNumberWithCreateNumber("a", false)
    compareIsNumberWithCreateNumber("11g", false)
    compareIsNumberWithCreateNumber("11z", false)
    compareIsNumberWithCreateNumber("11def", false)
    compareIsNumberWithCreateNumber("11d11", false)
    compareIsNumberWithCreateNumber("11 11", false)
    compareIsNumberWithCreateNumber(" 1111", false)
    compareIsNumberWithCreateNumber("1111 ", false)
    compareIsNumberWithCreateNumber("2.", true)
    compareIsNumberWithCreateNumber("1.1L", false)
  }

  @Test def testIsNumberLANG971(): Unit = {
    compareIsNumberWithCreateNumber("0085", false)
    compareIsNumberWithCreateNumber("085", false)
    compareIsNumberWithCreateNumber("08", false)
    compareIsNumberWithCreateNumber("07", true)
    compareIsNumberWithCreateNumber("00", true)
  }

  @Test def testIsNumberLANG992(): Unit = {
    compareIsNumberWithCreateNumber("0.0", true)
    compareIsNumberWithCreateNumber("0.4790", true)
  }

  @Test def testIsNumberLANG972(): Unit = {
    compareIsNumberWithCreateNumber("0xABCD", true)
    compareIsNumberWithCreateNumber("0XABCD", true)
  }

  @Test def testIsNumberLANG1252(): Unit = {
    compareIsNumberWithCreateNumber("+2", true)
    compareIsNumberWithCreateNumber("+2.0", true)
  }

  @Test def testIsNumberLANG1385(): Unit = compareIsNumberWithCreateNumber("L", false)

  private def compareIsNumberWithCreateNumber(`val`: String, expected: Boolean): Unit = {
    val isValid = NumberUtils.isCreatable(`val`)
    val canCreate = checkCreateNumber(`val`)
    assertTrue(
      "Expecting " + expected + " for isCreatable/createNumber using \"" + `val` + "\" but got " + isValid + " and " + canCreate,
      isValid == expected && canCreate == expected
    )
  }

  @Test def testIsParsable(): Unit = {
    assertFalse(NumberUtils.isParsable(null))
    assertFalse(NumberUtils.isParsable(""))
    assertFalse(NumberUtils.isParsable("0xC1AB"))
    assertFalse(NumberUtils.isParsable("65CBA2"))
    assertFalse(NumberUtils.isParsable("pendro"))
    assertFalse(NumberUtils.isParsable("64, 2"))
    assertFalse(NumberUtils.isParsable("64.2.2"))
    assertFalse(NumberUtils.isParsable("64."))
    assertFalse(NumberUtils.isParsable("64L"))
    assertFalse(NumberUtils.isParsable("-"))
    assertFalse(NumberUtils.isParsable("--2"))
    assertTrue(NumberUtils.isParsable("64.2"))
    assertTrue(NumberUtils.isParsable("64"))
    assertTrue(NumberUtils.isParsable("018"))
    assertTrue(NumberUtils.isParsable(".18"))
    assertTrue(NumberUtils.isParsable("-65"))
    assertTrue(NumberUtils.isParsable("-018"))
    assertTrue(NumberUtils.isParsable("-018.2"))
    assertTrue(NumberUtils.isParsable("-.236"))
  }

  private def checkCreateNumber(`val`: String): Boolean =
    try {
      val obj = NumberUtils.createNumber(`val`)
      obj != null
    } catch {
      case _: NumberFormatException => false
    }

  @SuppressWarnings(Array("cast")) // suppress instanceof warning check
  @Test def testConstants(): Unit = {
    assertTrue(NumberUtils.LONG_ZERO.isInstanceOf[Long])
    assertTrue(NumberUtils.LONG_ONE.isInstanceOf[Long])
    assertTrue(NumberUtils.LONG_MINUS_ONE.isInstanceOf[Long])
    assertTrue(NumberUtils.INTEGER_ZERO.isInstanceOf[Integer])
    assertTrue(NumberUtils.INTEGER_ONE.isInstanceOf[Integer])
    assertTrue(NumberUtils.INTEGER_MINUS_ONE.isInstanceOf[Integer])
    assertTrue(NumberUtils.SHORT_ZERO.isInstanceOf[Short])
    assertTrue(NumberUtils.SHORT_ONE.isInstanceOf[Short])
    assertTrue(NumberUtils.SHORT_MINUS_ONE.isInstanceOf[Short])
    assertTrue(NumberUtils.BYTE_ZERO.isInstanceOf[Byte])
    assertTrue(NumberUtils.BYTE_ONE.isInstanceOf[Byte])
    assertTrue(NumberUtils.BYTE_MINUS_ONE.isInstanceOf[Byte])
    assertTrue(NumberUtils.DOUBLE_ZERO.isInstanceOf[Double])
    assertTrue(NumberUtils.DOUBLE_ONE.isInstanceOf[Double])
    assertTrue(NumberUtils.DOUBLE_MINUS_ONE.isInstanceOf[Double])
    assertTrue(NumberUtils.FLOAT_ZERO.isInstanceOf[Float])
    assertTrue(NumberUtils.FLOAT_ONE.isInstanceOf[Float])
    assertTrue(NumberUtils.FLOAT_MINUS_ONE.isInstanceOf[Float])
    assertEquals(0, NumberUtils.LONG_ZERO.longValue)
    assertEquals(1, NumberUtils.LONG_ONE.longValue)
    assertEquals(NumberUtils.LONG_MINUS_ONE.longValue, -1)
    assertEquals(0, NumberUtils.INTEGER_ZERO.intValue)
    assertEquals(1, NumberUtils.INTEGER_ONE.intValue)
    assertEquals(NumberUtils.INTEGER_MINUS_ONE.intValue, -1)
    assertEquals(0, NumberUtils.SHORT_ZERO.shortValue)
    assertEquals(1, NumberUtils.SHORT_ONE.shortValue)
    assertEquals(NumberUtils.SHORT_MINUS_ONE.shortValue, -1)
    assertEquals(0, NumberUtils.BYTE_ZERO.byteValue)
    assertEquals(1, NumberUtils.BYTE_ONE.byteValue)
    assertEquals(NumberUtils.BYTE_MINUS_ONE.byteValue, -1)
    assertEquals(0.0d, NumberUtils.DOUBLE_ZERO.doubleValue, DoubleDelta)
    assertEquals(1.0d, NumberUtils.DOUBLE_ONE.doubleValue, DoubleDelta)
    assertEquals(NumberUtils.DOUBLE_MINUS_ONE.doubleValue, -1.0d, DoubleDelta)
    assertEquals(0.0f, NumberUtils.FLOAT_ZERO.floatValue, FloatDelta)
    assertEquals(1.0f, NumberUtils.FLOAT_ONE.floatValue, FloatDelta)
    assertEquals(NumberUtils.FLOAT_MINUS_ONE.floatValue, -1.0f, FloatDelta)
  }

  @Test def testLang300(): Unit = {
    NumberUtils.createNumber("-1l")
    NumberUtils.createNumber("01l")
    NumberUtils.createNumber("1l")
    ()
  }

  @Test def testLang381(): Unit = {
    assertTrue(JavaDouble.isNaN(NumberUtils.min(1.2, 2.5, Double.NaN)))
    assertTrue(JavaDouble.isNaN(NumberUtils.max(1.2, 2.5, Double.NaN)))
    assertTrue(JavaFloat.isNaN(NumberUtils.min(1.2f, 2.5f, Float.NaN)))
    assertTrue(JavaFloat.isNaN(NumberUtils.max(1.2f, 2.5f, Float.NaN)))
    val a: Array[Double] = Array[Double](1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN)
    val foundMax = NumberUtils.max(a)
    void(foundMax)
    assertTrue(JavaDouble.isNaN(NumberUtils.max(a)))
    assertTrue(JavaDouble.isNaN(NumberUtils.min(a)))
    val b: Array[Double] = Array[Double](Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN)
    assertTrue(JavaDouble.isNaN(NumberUtils.max(b)))
    assertTrue(JavaDouble.isNaN(NumberUtils.min(b)))
    val aF: Array[Float] = Array[Float](1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN)
    assertTrue(JavaFloat.isNaN(NumberUtils.max(aF)))
    val bF: Array[Float] = Array[Float](Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN)
    assertTrue(JavaFloat.isNaN(NumberUtils.max(bF)))
  }

  @Test def compareInt(): Unit = {
    assertTrue(NumberUtils.compare(-(3), 0) < 0)
    assertEquals(0, NumberUtils.compare(113, 113))
    assertTrue(NumberUtils.compare(213, 32) > 0)
  }

  @Test def compareLong(): Unit = {
    assertTrue(NumberUtils.compare(-(3L), 0L) < 0)
    assertEquals(0, NumberUtils.compare(113L, 113L))
    assertTrue(NumberUtils.compare(213L, 32L) > 0)
  }

  @Test def compareShort(): Unit = {
    assertTrue(NumberUtils.compare(-(3).toShort, 0.toShort) < 0)
    assertEquals(0, NumberUtils.compare(113.toShort, 113.toShort))
    assertTrue(NumberUtils.compare(213.toShort, 32.toShort) > 0)
  }

  @Test def compareByte(): Unit = {
    assertTrue(NumberUtils.compare(-(3).toByte, 0.toByte) < 0)
    assertEquals(0, NumberUtils.compare(113.toByte, 113.toByte))
    assertTrue(NumberUtils.compare(123.toByte, 32.toByte) > 0)
  }
}
