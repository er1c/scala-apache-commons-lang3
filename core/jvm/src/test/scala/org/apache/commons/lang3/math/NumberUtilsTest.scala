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

//package org.apache.commons.lang3.math
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import java.math.BigDecimal
//import java.math.BigInteger
//import java.math.RoundingMode
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link org.apache.commons.lang3.math.NumberUtils}.
//  */
//class NumberUtilsTest extends JUnitSuite {
//  //  @Test def testConstructor() = {
//  //    assertNotNull(new NumberUtils.type)
//  //    val cons = classOf[NumberUtils.type].getDeclaredConstructors
//  //    assertEquals(1, cons.length)
//  //    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//  //    assertTrue(Modifier.isPublic(classOf[NumberUtils.type].getModifiers))
//  //    assertFalse(Modifier.isFinal(classOf[NumberUtils.type].getModifiers))
//  //  }
//
//  /**
//    * Test for {@link NumberUtils# toInt ( String )}.
//    */
//  @Test def testToIntString() = {
//    assertEquals(12345, NumberUtils.toInt("12345"), "toInt(String) 1 failed")
//    assertEquals(0, NumberUtils.toInt("abc"), "toInt(String) 2 failed")
//    assertEquals(0, NumberUtils.toInt(""), "toInt(empty) failed")
//    assertEquals(0, NumberUtils.toInt(null), "toInt(null) failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toInt ( String, int)}.
//    */
//  @Test def testToIntStringI() = {
//    assertEquals(12345, NumberUtils.toInt("12345", 5), "toInt(String, int) 1 failed")
//    assertEquals(5, NumberUtils.toInt("1234.5", 5), "toInt(String, int) 2 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toLong ( String )}.
//    */
//  @Test def testToLongString() = {
//    assertEquals(12345L, NumberUtils.toLong("12345"), "toLong(String) 1 failed")
//    assertEquals(0L, NumberUtils.toLong("abc"), "toLong(String) 2 failed")
//    assertEquals(0L, NumberUtils.toLong("1L"), "toLong(String) 3 failed")
//    assertEquals(0L, NumberUtils.toLong("1l"), "toLong(String) 4 failed")
//    assertEquals(NumberUtils.toLong(Long.MAX_VALUE + ""), Long.MAX_VALUE, "toLong(Long.MAX_VALUE) failed")
//    assertEquals(NumberUtils.toLong(Long.MIN_VALUE + ""), Long.MIN_VALUE, "toLong(Long.MIN_VALUE) failed")
//    assertEquals(0L, NumberUtils.toLong(""), "toLong(empty) failed")
//    assertEquals(0L, NumberUtils.toLong(null), "toLong(null) failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toLong ( String, long)}.
//    */
//  @Test def testToLongStringL() = {
//    assertEquals(12345L, NumberUtils.toLong("12345", 5L), "toLong(String, long) 1 failed")
//    assertEquals(5L, NumberUtils.toLong("1234.5", 5L), "toLong(String, long) 2 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toFloat ( String )}.
//    */
//  @Test def testToFloatString() = {
//    assertEquals(NumberUtils.toFloat("-1.2345"), -1.2345f, "toFloat(String) 1 failed")
//    assertEquals(1.2345f, NumberUtils.toFloat("1.2345"), "toFloat(String) 2 failed")
//    assertEquals(0.0f, NumberUtils.toFloat("abc"), "toFloat(String) 3 failed")
//    // LANG-1060
//    assertEquals(NumberUtils.toFloat("-001.2345"), -1.2345f, "toFloat(String) 4 failed")
//    assertEquals(1.2345f, NumberUtils.toFloat("+001.2345"), "toFloat(String) 5 failed")
//    assertEquals(1.2345f, NumberUtils.toFloat("001.2345"), "toFloat(String) 6 failed")
//    assertEquals(0f, NumberUtils.toFloat("000.00"), "toFloat(String) 7 failed")
//    assertEquals(NumberUtils.toFloat(Float.MAX_VALUE + ""), Float.MAX_VALUE, "toFloat(Float.MAX_VALUE) failed")
//    assertEquals(NumberUtils.toFloat(Float.MIN_VALUE + ""), Float.MIN_VALUE, "toFloat(Float.MIN_VALUE) failed")
//    assertEquals(0.0f, NumberUtils.toFloat(""), "toFloat(empty) failed")
//    assertEquals(0.0f, NumberUtils.toFloat(null), "toFloat(null) failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toFloat ( String, float)}.
//    */
//  @Test def testToFloatStringF() = {
//    assertEquals(1.2345f, NumberUtils.toFloat("1.2345", 5.1f), "toFloat(String, int) 1 failed")
//    assertEquals(5.0f, NumberUtils.toFloat("a", 5.0f), "toFloat(String, int) 2 failed")
//    assertEquals(5.0f, NumberUtils.toFloat("-001Z.2345", 5.0f), "toFloat(String, int) 3 failed")
//    assertEquals(5.0f, NumberUtils.toFloat("+001AB.2345", 5.0f), "toFloat(String, int) 4 failed")
//    assertEquals(5.0f, NumberUtils.toFloat("001Z.2345", 5.0f), "toFloat(String, int) 5 failed")
//  }
//
//  /**
//    * Test for {(@link NumberUtils#createNumber(String)}
//    */
//  @Test def testStringCreateNumberEnsureNoPrecisionLoss() = {
//    val shouldBeFloat = "1.23"
//    val shouldBeDouble = "3.40282354e+38"
//    val shouldBeBigDecimal = "1.797693134862315759e+308"
//    assertTrue(NumberUtils.createNumber(shouldBeFloat).isInstanceOf[Float])
//    assertTrue(NumberUtils.createNumber(shouldBeDouble).isInstanceOf[Double])
//    assertTrue(NumberUtils.createNumber(shouldBeBigDecimal).isInstanceOf[BigDecimal])
//    assertTrue(NumberUtils.createNumber("001.12").isInstanceOf[Float])
//    assertTrue(NumberUtils.createNumber("-001.12").isInstanceOf[Float])
//    assertTrue(NumberUtils.createNumber("+001.12").isInstanceOf[Float])
//    assertTrue(NumberUtils.createNumber("003.40282354e+38").isInstanceOf[Double])
//    assertTrue(NumberUtils.createNumber("-003.40282354e+38").isInstanceOf[Double])
//    assertTrue(NumberUtils.createNumber("+003.40282354e+38").isInstanceOf[Double])
//    assertTrue(NumberUtils.createNumber("0001.797693134862315759e+308").isInstanceOf[BigDecimal])
//    assertTrue(NumberUtils.createNumber("-001.797693134862315759e+308").isInstanceOf[BigDecimal])
//    assertTrue(NumberUtils.createNumber("+001.797693134862315759e+308").isInstanceOf[BigDecimal])
//  }
//
//  /**
//    * Test for {@link NumberUtils# toDouble ( String )}.
//    */
//  @Test def testStringToDoubleString() = {
//    assertEquals(NumberUtils.toDouble("-1.2345"), -1.2345d, "toDouble(String) 1 failed")
//    assertEquals(1.2345d, NumberUtils.toDouble("1.2345"), "toDouble(String) 2 failed")
//    assertEquals(0.0d, NumberUtils.toDouble("abc"), "toDouble(String) 3 failed")
//    assertEquals(NumberUtils.toDouble("-001.2345"), -1.2345d, "toDouble(String) 4 failed")
//    assertEquals(1.2345d, NumberUtils.toDouble("+001.2345"), "toDouble(String) 5 failed")
//    assertEquals(1.2345d, NumberUtils.toDouble("001.2345"), "toDouble(String) 6 failed")
//    assertEquals(0d, NumberUtils.toDouble("000.00000"), "toDouble(String) 7 failed")
//    assertEquals(NumberUtils.toDouble(Double.MAX_VALUE + ""), Double.MAX_VALUE, "toDouble(Double.MAX_VALUE) failed")
//    assertEquals(NumberUtils.toDouble(Double.MIN_VALUE + ""), Double.MIN_VALUE, "toDouble(Double.MIN_VALUE) failed")
//    assertEquals(0.0d, NumberUtils.toDouble(""), "toDouble(empty) failed")
//    assertEquals(0.0d, NumberUtils.toDouble(null.asInstanceOf[String]), "toDouble(null) failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toDouble ( String, double)}.
//    */
//  @Test def testStringToDoubleStringD() = {
//    assertEquals(1.2345d, NumberUtils.toDouble("1.2345", 5.1d), "toDouble(String, int) 1 failed")
//    assertEquals(5.0d, NumberUtils.toDouble("a", 5.0d), "toDouble(String, int) 2 failed")
//    assertEquals(1.2345d, NumberUtils.toDouble("001.2345", 5.1d), "toDouble(String, int) 3 failed")
//    assertEquals(NumberUtils.toDouble("-001.2345", 5.1d), -1.2345d, "toDouble(String, int) 4 failed")
//    assertEquals(1.2345d, NumberUtils.toDouble("+001.2345", 5.1d), "toDouble(String, int) 5 failed")
//    assertEquals(0d, NumberUtils.toDouble("000.00", 5.1d), "toDouble(String, int) 7 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toDouble ( BigDecimal )}
//    */
//  @Test def testBigIntegerToDoubleBigInteger() = {
//    assertEquals(0.0d, NumberUtils.toDouble(null.asInstanceOf[BigDecimal]), "toDouble(BigInteger) 1 failed")
//    assertEquals(8.5d, NumberUtils.toDouble(BigDecimal.valueOf(8.5d)), "toDouble(BigInteger) 2 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toDouble ( BigDecimal, double)}
//    */
//  @Test def testBigIntegerToDoubleBigIntegerD() = {
//    assertEquals(1.1d, NumberUtils.toDouble(null.asInstanceOf[BigDecimal], 1.1d), "toDouble(BigInteger) 1 failed")
//    assertEquals(8.5d, NumberUtils.toDouble(BigDecimal.valueOf(8.5d), 1.1d), "toDouble(BigInteger) 2 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toByte ( String )}.
//    */
//  @Test def testToByteString() = {
//    assertEquals(123, NumberUtils.toByte("123"), "toByte(String) 1 failed")
//    assertEquals(0, NumberUtils.toByte("abc"), "toByte(String) 2 failed")
//    assertEquals(0, NumberUtils.toByte(""), "toByte(empty) failed")
//    assertEquals(0, NumberUtils.toByte(null), "toByte(null) failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toByte ( String, byte)}.
//    */
//  @Test def testToByteStringI() = {
//    assertEquals(123, NumberUtils.toByte("123", 5.toByte), "toByte(String, byte) 1 failed")
//    assertEquals(5, NumberUtils.toByte("12.3", 5.toByte), "toByte(String, byte) 2 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toShort ( String )}.
//    */
//  @Test def testToShortString() = {
//    assertEquals(12345, NumberUtils.toShort("12345"), "toShort(String) 1 failed")
//    assertEquals(0, NumberUtils.toShort("abc"), "toShort(String) 2 failed")
//    assertEquals(0, NumberUtils.toShort(""), "toShort(empty) failed")
//    assertEquals(0, NumberUtils.toShort(null), "toShort(null) failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toShort ( String, short)}.
//    */
//  @Test def testToShortStringI() = {
//    assertEquals(12345, NumberUtils.toShort("12345", 5.toShort), "toShort(String, short) 1 failed")
//    assertEquals(5, NumberUtils.toShort("1234.5", 5.toShort), "toShort(String, short) 2 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toScaledBigDecimal ( BigDecimal )}.
//    */
//  @Test def testToScaledBigDecimalBigDecimal() = {
//    assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(123.456)), BigDecimal.valueOf(123.46), "toScaledBigDecimal(BigDecimal) 1 failed")
//    // Test RoudingMode.HALF_EVEN default rounding.
//    assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.515)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(BigDecimal) 2 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(BigDecimal) 3 failed")
//    assertEquals("2352.00", NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525)).multiply(BigDecimal.valueOf(100)).toString, "toScaledBigDecimal(BigDecimal) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[BigDecimal]), BigDecimal.ZERO, "toScaledBigDecimal(BigDecimal) 5 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toScaledBigDecimal ( BigDecimal, int, RoundingMode)}.
//    */
//  @Test def testToScaledBigDecimalBigDecimalIRM() = {
//    assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(123.456), 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(BigDecimal, int, RoudingMode) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.5159), 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(BigDecimal, int, RoudingMode) 2 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.525), 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.53), "toScaledBigDecimal(BigDecimal, int, RoudingMode) 3 failed")
//    assertEquals("23521.0000", NumberUtils.toScaledBigDecimal(BigDecimal.valueOf(23.521), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString, "toScaledBigDecimal(BigDecimal, int, RoudingMode) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[BigDecimal], 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(BigDecimal, int, RoudingMode) 5 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toScaledBigDecimal ( Float )}.
//    */
//  @Test def testToScaledBigDecimalFloat() = {
//    assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(123.456f)), BigDecimal.valueOf(123.46), "toScaledBigDecimal(Float) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.515f)), BigDecimal.valueOf(23.51), "toScaledBigDecimal(Float) 2 failed")
//    // Note. NumberUtils.toScaledBigDecimal(Float.valueOf(23.515f)).equals(BigDecimal.valueOf(23.51))
//    // because of roundoff error. It is ok.
//    assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.525f)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Float) 3 failed")
//    assertEquals("2352.00", NumberUtils.toScaledBigDecimal(Float.valueOf(23.525f)).multiply(BigDecimal.valueOf(100)).toString, "toScaledBigDecimal(Float) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[Float]), BigDecimal.ZERO, "toScaledBigDecimal(Float) 5 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toScaledBigDecimal ( Float, int, RoundingMode)}.
//    */
//  @Test def testToScaledBigDecimalFloatIRM() = {
//    assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(123.456f), 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(Float, int, RoudingMode) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.5159f), 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(Float, int, RoudingMode) 2 failed")
//    // The following happens due to roundoff error. We're ok with this.
//    assertEquals(NumberUtils.toScaledBigDecimal(Float.valueOf(23.525f), 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Float, int, RoudingMode) 3 failed")
//    assertEquals("23521.0000", NumberUtils.toScaledBigDecimal(Float.valueOf(23.521f), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString, "toScaledBigDecimal(Float, int, RoudingMode) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[Float], 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(Float, int, RoudingMode) 5 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toScaledBigDecimal ( Double )}.
//    */
//  @Test def testToScaledBigDecimalDouble() = {
//    assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(123.456d)), BigDecimal.valueOf(123.46), "toScaledBigDecimal(Double) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.515d)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Double) 2 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.525d)), BigDecimal.valueOf(23.52), "toScaledBigDecimal(Double) 3 failed")
//    assertEquals("2352.00", NumberUtils.toScaledBigDecimal(Double.valueOf(23.525d)).multiply(BigDecimal.valueOf(100)).toString, "toScaledBigDecimal(Double) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[Double]), BigDecimal.ZERO, "toScaledBigDecimal(Double) 5 failed")
//  }
//
//  /**
//    * Test for {@link NumberUtils# toScaledBigDecimal ( Double, int, RoundingMode)}.
//    */
//  @Test def testToScaledBigDecimalDoubleIRM() = {
//    assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(123.456d), 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(Double, int, RoudingMode) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.5159d), 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(Double, int, RoudingMode) 2 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(Double.valueOf(23.525d), 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.53), "toScaledBigDecimal(Double, int, RoudingMode) 3 failed")
//    assertEquals("23521.0000", NumberUtils.toScaledBigDecimal(Double.valueOf(23.521d), 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString, "toScaledBigDecimal(Double, int, RoudingMode) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[Double], 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(Double, int, RoudingMode) 5 failed")
//  }
//
//  @Test def testToScaledBigDecimalString() = {
//    assertEquals(NumberUtils.toScaledBigDecimal("123.456"), BigDecimal.valueOf(123.46), "toScaledBigDecimal(String) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal("23.515"), BigDecimal.valueOf(23.52), "toScaledBigDecimal(String) 2 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal("23.525"), BigDecimal.valueOf(23.52), "toScaledBigDecimal(String) 3 failed")
//    assertEquals("2352.00", NumberUtils.toScaledBigDecimal("23.525").multiply(BigDecimal.valueOf(100)).toString, "toScaledBigDecimal(String) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[String]), BigDecimal.ZERO, "toScaledBigDecimal(String) 5 failed")
//  }
//
//  @Test def testToScaledBigDecimalStringIRM() = {
//    assertEquals(NumberUtils.toScaledBigDecimal("123.456", 1, RoundingMode.CEILING), BigDecimal.valueOf(123.5), "toScaledBigDecimal(String, int, RoudingMode) 1 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal("23.5159", 3, RoundingMode.FLOOR), BigDecimal.valueOf(23.515), "toScaledBigDecimal(String, int, RoudingMode) 2 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal("23.525", 2, RoundingMode.HALF_UP), BigDecimal.valueOf(23.53), "toScaledBigDecimal(String, int, RoudingMode) 3 failed")
//    assertEquals("23521.0000", NumberUtils.toScaledBigDecimal("23.521", 4, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(1000)).toString, "toScaledBigDecimal(String, int, RoudingMode) 4 failed")
//    assertEquals(NumberUtils.toScaledBigDecimal(null.asInstanceOf[String], 2, RoundingMode.HALF_UP), BigDecimal.ZERO, "toScaledBigDecimal(String, int, RoudingMode) 5 failed")
//  }
//
//  @Test def testCreateNumber() = { // a lot of things can go wrong
//    assertEquals(Float.valueOf("1234.5"), NumberUtils.createNumber("1234.5"), "createNumber(String) 1 failed")
//    assertEquals(Integer.valueOf("12345"), NumberUtils.createNumber("12345"), "createNumber(String) 2 failed")
//    assertEquals(Double.valueOf("1234.5"), NumberUtils.createNumber("1234.5D"), "createNumber(String) 3 failed")
//    assertEquals(Double.valueOf("1234.5"), NumberUtils.createNumber("1234.5d"), "createNumber(String) 3 failed")
//    assertEquals(Float.valueOf("1234.5"), NumberUtils.createNumber("1234.5F"), "createNumber(String) 4 failed")
//    assertEquals(Float.valueOf("1234.5"), NumberUtils.createNumber("1234.5f"), "createNumber(String) 4 failed")
//    assertEquals(Long.valueOf(Integer.MAX_VALUE + 1L), NumberUtils.createNumber("" + (Integer.MAX_VALUE + 1L)), "createNumber(String) 5 failed")
//    assertEquals(Long.valueOf(12345), NumberUtils.createNumber("12345L"), "createNumber(String) 6 failed")
//    assertEquals(Long.valueOf(12345), NumberUtils.createNumber("12345l"), "createNumber(String) 6 failed")
//    assertEquals(Float.valueOf("-1234.5"), NumberUtils.createNumber("-1234.5"), "createNumber(String) 7 failed")
//    assertEquals(Integer.valueOf("-12345"), NumberUtils.createNumber("-12345"), "createNumber(String) 8 failed")
//    assertEquals(0xFADE, NumberUtils.createNumber("0xFADE").intValue, "createNumber(String) 9a failed")
//    assertEquals(0xFADE, NumberUtils.createNumber("0Xfade").intValue, "createNumber(String) 9b failed")
//    assertEquals(-0xFADE, NumberUtils.createNumber("-0xFADE").intValue, "createNumber(String) 10a failed")
//    assertEquals(-0xFADE, NumberUtils.createNumber("-0Xfade").intValue, "createNumber(String) 10b failed")
//    assertEquals(Double.valueOf("1.1E200"), NumberUtils.createNumber("1.1E200"), "createNumber(String) 11 failed")
//    assertEquals(Float.valueOf("1.1E20"), NumberUtils.createNumber("1.1E20"), "createNumber(String) 12 failed")
//    assertEquals(Double.valueOf("-1.1E200"), NumberUtils.createNumber("-1.1E200"), "createNumber(String) 13 failed")
//    assertEquals(Double.valueOf("1.1E-200"), NumberUtils.createNumber("1.1E-200"), "createNumber(String) 14 failed")
//    assertNull(NumberUtils.createNumber(null), "createNumber(null) failed")
//    assertEquals(new BigInteger("12345678901234567890"), NumberUtils.createNumber("12345678901234567890L"), "createNumber(String) failed")
//    assertEquals(new BigDecimal("1.1E-700"), NumberUtils.createNumber("1.1E-700F"), "createNumber(String) 15 failed")
//    assertEquals(Long.valueOf("10" + Integer.MAX_VALUE), NumberUtils.createNumber("10" + Integer.MAX_VALUE + "L"), "createNumber(String) 16 failed")
//    assertEquals(Long.valueOf("10" + Integer.MAX_VALUE), NumberUtils.createNumber("10" + Integer.MAX_VALUE), "createNumber(String) 17 failed")
//    assertEquals(new BigInteger("10" + Long.MAX_VALUE), NumberUtils.createNumber("10" + Long.MAX_VALUE), "createNumber(String) 18 failed")
//    // LANG-521
//    assertEquals(Float.valueOf("2."), NumberUtils.createNumber("2."), "createNumber(String) LANG-521 failed")
//    // LANG-638
//    assertFalse(checkCreateNumber("1eE"), "createNumber(String) succeeded")
//    // LANG-693
//    assertEquals(Double.valueOf(Double.MAX_VALUE), NumberUtils.createNumber("" + Double.MAX_VALUE), "createNumber(String) LANG-693 failed")
//    // LANG-822
//    // ensure that the underlying negative number would create a BigDecimal
//    val bigNum = NumberUtils.createNumber("-1.1E-700F")
//    assertNotNull(bigNum)
//    assertEquals(classOf[BigDecimal], bigNum.getClass)
//    // LANG-1018
//    assertEquals(Double.valueOf("-160952.54"), NumberUtils.createNumber("-160952.54"), "createNumber(String) LANG-1018 failed")
//    // LANG-1187
//    assertEquals(Double.valueOf("6264583.33"), NumberUtils.createNumber("6264583.33"), "createNumber(String) LANG-1187 failed")
//    // LANG-1215
//    assertEquals(Double.valueOf("193343.82"), NumberUtils.createNumber("193343.82"), "createNumber(String) LANG-1215 failed")
//    assertEquals(Double.valueOf("001234.5678"), NumberUtils.createNumber("001234.5678"), "createNumber(String) LANG-1060a failed")
//    assertEquals(Double.valueOf("+001234.5678"), NumberUtils.createNumber("+001234.5678"), "createNumber(String) LANG-1060b failed")
//    assertEquals(Double.valueOf("-001234.5678"), NumberUtils.createNumber("-001234.5678"), "createNumber(String) LANG-1060c failed")
//    assertEquals(Double.valueOf("0000.00000"), NumberUtils.createNumber("0000.00000d"), "createNumber(String) LANG-1060d failed")
//    assertEquals(Float.valueOf("001234.56"), NumberUtils.createNumber("001234.56"), "createNumber(String) LANG-1060e failed")
//    assertEquals(Float.valueOf("+001234.56"), NumberUtils.createNumber("+001234.56"), "createNumber(String) LANG-1060f failed")
//    assertEquals(Float.valueOf("-001234.56"), NumberUtils.createNumber("-001234.56"), "createNumber(String) LANG-1060g failed")
//    assertEquals(Float.valueOf("0000.10"), NumberUtils.createNumber("0000.10"), "createNumber(String) LANG-1060h failed")
//    assertEquals(Float.valueOf("001.1E20"), NumberUtils.createNumber("001.1E20"), "createNumber(String) LANG-1060i failed")
//    assertEquals(Float.valueOf("+001.1E20"), NumberUtils.createNumber("+001.1E20"), "createNumber(String) LANG-1060j failed")
//    assertEquals(Float.valueOf("-001.1E20"), NumberUtils.createNumber("-001.1E20"), "createNumber(String) LANG-1060k failed")
//    assertEquals(Double.valueOf("001.1E200"), NumberUtils.createNumber("001.1E200"), "createNumber(String) LANG-1060l failed")
//    assertEquals(Double.valueOf("+001.1E200"), NumberUtils.createNumber("+001.1E200"), "createNumber(String) LANG-1060m failed")
//    assertEquals(Double.valueOf("-001.1E200"), NumberUtils.createNumber("-001.1E200"), "createNumber(String) LANG-1060n failed")
//  }
//
//  @Test def testLang1087() = { // no sign cases
//    assertEquals(classOf[Float], NumberUtils.createNumber("0.0").getClass)
//    assertEquals(Float.valueOf("0.0"), NumberUtils.createNumber("0.0"))
//    // explicit positive sign cases
//    assertEquals(classOf[Float], NumberUtils.createNumber("+0.0").getClass)
//    assertEquals(Float.valueOf("+0.0"), NumberUtils.createNumber("+0.0"))
//    // negative sign cases
//    assertEquals(classOf[Float], NumberUtils.createNumber("-0.0").getClass)
//    assertEquals(Float.valueOf("-0.0"), NumberUtils.createNumber("-0.0"))
//  }
//
//  @Test def TestLang747() = {
//    assertEquals(Integer.valueOf(0x8000), NumberUtils.createNumber("0x8000"))
//    assertEquals(Integer.valueOf(0x80000), NumberUtils.createNumber("0x80000"))
//    assertEquals(Integer.valueOf(0x800000), NumberUtils.createNumber("0x800000"))
//    assertEquals(Integer.valueOf(0x8000000), NumberUtils.createNumber("0x8000000"))
//    assertEquals(Integer.valueOf(0x7FFFFFFF), NumberUtils.createNumber("0x7FFFFFFF"))
//    assertEquals(Long.valueOf(0x80000000L), NumberUtils.createNumber("0x80000000"))
//    assertEquals(Long.valueOf(0xFFFFFFFFL), NumberUtils.createNumber("0xFFFFFFFF"))
//    // Leading zero tests
//    assertEquals(Integer.valueOf(0x8000000), NumberUtils.createNumber("0x08000000"))
//    assertEquals(Integer.valueOf(0x7FFFFFFF), NumberUtils.createNumber("0x007FFFFFFF"))
//    assertEquals(Long.valueOf(0x80000000L), NumberUtils.createNumber("0x080000000"))
//    assertEquals(Long.valueOf(0xFFFFFFFFL), NumberUtils.createNumber("0x00FFFFFFFF"))
//    assertEquals(Long.valueOf(0x800000000L), NumberUtils.createNumber("0x800000000"))
//    assertEquals(Long.valueOf(0x8000000000L), NumberUtils.createNumber("0x8000000000"))
//    assertEquals(Long.valueOf(0x80000000000L), NumberUtils.createNumber("0x80000000000"))
//    assertEquals(Long.valueOf(0x800000000000L), NumberUtils.createNumber("0x800000000000"))
//    assertEquals(Long.valueOf(0x8000000000000L), NumberUtils.createNumber("0x8000000000000"))
//    assertEquals(Long.valueOf(0x80000000000000L), NumberUtils.createNumber("0x80000000000000"))
//    assertEquals(Long.valueOf(0x800000000000000L), NumberUtils.createNumber("0x800000000000000"))
//    assertEquals(Long.valueOf(0x7FFFFFFFFFFFFFFFL), NumberUtils.createNumber("0x7FFFFFFFFFFFFFFF"))
//    // N.B. Cannot use a hex constant such as 0x8000000000000000L here as that is interpreted as a negative long
//    assertEquals(new BigInteger("8000000000000000", 16), NumberUtils.createNumber("0x8000000000000000"))
//    assertEquals(new BigInteger("FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("0xFFFFFFFFFFFFFFFF"))
//    assertEquals(Long.valueOf(0x80000000000000L), NumberUtils.createNumber("0x00080000000000000"))
//    assertEquals(Long.valueOf(0x800000000000000L), NumberUtils.createNumber("0x0800000000000000"))
//    assertEquals(Long.valueOf(0x7FFFFFFFFFFFFFFFL), NumberUtils.createNumber("0x07FFFFFFFFFFFFFFF"))
//    assertEquals(new BigInteger("8000000000000000", 16), NumberUtils.createNumber("0x00008000000000000000"))
//    assertEquals(new BigInteger("FFFFFFFFFFFFFFFF", 16), NumberUtils.createNumber("0x0FFFFFFFFFFFFFFFF"))
//  }
//
//  @Test // Check that the code fails to create a valid number when preceded by -- rather than -
//  def testCreateNumberFailure_1() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("--1.1E-700F"))
//
//  @Test // Check that the code fails to create a valid number when both e and E are present (with decimal)
//  def testCreateNumberFailure_2() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("-1.1E+0-7e00"))
//
//  @Test // Check that the code fails to create a valid number when both e and E are present (no decimal)
//  def testCreateNumberFailure_3() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("-11E+0-7e00"))
//
//  @Test def testCreateNumberFailure_4() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("1eE+00001"))
//
//  @Test // Check that the code fails to create a valid number when there are multiple trailing 'f' characters (LANG-1205)
//  def testCreateNumberFailure_5() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("1234.5ff"))
//
//  @Test // Check that the code fails to create a valid number when there are multiple trailing 'F' characters (LANG-1205)
//  def testCreateNumberFailure_6() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("1234.5FF"))
//
//  @Test // Check that the code fails to create a valid number when there are multiple trailing 'd' characters (LANG-1205)
//  def testCreateNumberFailure_7() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("1234.5dd"))
//
//  @Test // Check that the code fails to create a valid number when there are multiple trailing 'D' characters (LANG-1205)
//  def testCreateNumberFailure_8() = assertThrows(classOf[NumberFormatException], () => NumberUtils.createNumber("1234.5DD"))
//
//  // Tests to show when magnitude causes switch to next Number type
//  // Will probably need to be adjusted if code is changed to check precision (LANG-693)
//  @Test def testCreateNumberMagnitude() = { // Test Float.MAX_VALUE, and same with +1 in final digit to check conversion changes to next Number type
//    assertEquals(Float.valueOf(Float.MAX_VALUE), NumberUtils.createNumber("3.4028235e+38"))
//    assertEquals(Double.valueOf(3.4028236e+38), NumberUtils.createNumber("3.4028236e+38"))
//    // Test Double.MAX_VALUE
//    assertEquals(Double.valueOf(Double.MAX_VALUE), NumberUtils.createNumber("1.7976931348623157e+308"))
//    // Test with +2 in final digit (+1 does not cause roll-over to BigDecimal)
//    assertEquals(new BigDecimal("1.7976931348623159e+308"), NumberUtils.createNumber("1.7976931348623159e+308"))
//    assertEquals(Integer.valueOf(0x12345678), NumberUtils.createNumber("0x12345678"))
//    assertEquals(Long.valueOf(0x123456789L), NumberUtils.createNumber("0x123456789"))
//    assertEquals(Long.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("0x7fffffffffffffff"))
//    // Does not appear to be a way to create a literal BigInteger of this magnitude
//    assertEquals(new BigInteger("7fffffffffffffff0", 16), NumberUtils.createNumber("0x7fffffffffffffff0"))
//    assertEquals(Long.valueOf(0x7fffffffffffffffL), NumberUtils.createNumber("#7fffffffffffffff"))
//    assertEquals(new BigInteger("7fffffffffffffff0", 16), NumberUtils.createNumber("#7fffffffffffffff0"))
//    assertEquals(Integer.valueOf(017777777777), NumberUtils.createNumber("017777777777")) // 31 bits
//    assertEquals(Long.valueOf(037777777777L), NumberUtils.createNumber("037777777777")) // 32 bits
//    assertEquals(Long.valueOf(0777777777777777777777L), NumberUtils.createNumber("0777777777777777777777")) // 63 bits
//    assertEquals(new BigInteger("1777777777777777777777", 8), NumberUtils.createNumber("01777777777777777777777")) // 64 bits
//  }
//
//  @Test def testCreateFloat() = {
//    assertEquals(Float.valueOf("1234.5"), NumberUtils.createFloat("1234.5"), "createFloat(String) failed")
//    assertNull(NumberUtils.createFloat(null), "createFloat(null) failed")
//    this.testCreateFloatFailure("")
//    this.testCreateFloatFailure(" ")
//    this.testCreateFloatFailure("\b\t\n\f\r")
//    // Funky whitespaces
//    this.testCreateFloatFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
//  }
//
//  protected def testCreateFloatFailure(str: String) = assertThrows(classOf[NumberFormatException], () => NumberUtils.createFloat(str), "createFloat(\"" + str + "\") should have failed.")
//
//  @Test def testCreateDouble() = {
//    assertEquals(Double.valueOf("1234.5"), NumberUtils.createDouble("1234.5"), "createDouble(String) failed")
//    assertNull(NumberUtils.createDouble(null), "createDouble(null) failed")
//    this.testCreateDoubleFailure("")
//    this.testCreateDoubleFailure(" ")
//    this.testCreateDoubleFailure("\b\t\n\f\r")
//    this.testCreateDoubleFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
//  }
//
//  protected def testCreateDoubleFailure(str: String) = assertThrows(classOf[NumberFormatException], () => NumberUtils.createDouble(str), "createDouble(\"" + str + "\") should have failed.")
//
//  @Test def testCreateInteger() = {
//    assertEquals(Integer.valueOf("12345"), NumberUtils.createInteger("12345"), "createInteger(String) failed")
//    assertNull(NumberUtils.createInteger(null), "createInteger(null) failed")
//    this.testCreateIntegerFailure("")
//    this.testCreateIntegerFailure(" ")
//    this.testCreateIntegerFailure("\b\t\n\f\r")
//    this.testCreateIntegerFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
//  }
//
//  protected def testCreateIntegerFailure(str: String) = assertThrows(classOf[NumberFormatException], () => NumberUtils.createInteger(str), "createInteger(\"" + str + "\") should have failed.")
//
//  @Test def testCreateLong() = {
//    assertEquals(Long.valueOf("12345"), NumberUtils.createLong("12345"), "createLong(String) failed")
//    assertNull(NumberUtils.createLong(null), "createLong(null) failed")
//    this.testCreateLongFailure("")
//    this.testCreateLongFailure(" ")
//    this.testCreateLongFailure("\b\t\n\f\r")
//    this.testCreateLongFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
//  }
//
//  protected def testCreateLongFailure(str: String) = assertThrows(classOf[NumberFormatException], () => NumberUtils.createLong(str), "createLong(\"" + str + "\") should have failed.")
//
//  @Test def testCreateBigInteger() = {
//    assertEquals(new BigInteger("12345"), NumberUtils.createBigInteger("12345"), "createBigInteger(String) failed")
//    assertNull(NumberUtils.createBigInteger(null), "createBigInteger(null) failed")
//    this.testCreateBigIntegerFailure("")
//    this.testCreateBigIntegerFailure(" ")
//    this.testCreateBigIntegerFailure("\b\t\n\f\r")
//    this.testCreateBigIntegerFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
//    assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("0xff"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("0Xff"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("#ff"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("-255"), NumberUtils.createBigInteger("-0xff"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("255"), NumberUtils.createBigInteger("0377"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("-255"), NumberUtils.createBigInteger("-0377"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("-255"), NumberUtils.createBigInteger("-0377"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("-0"), NumberUtils.createBigInteger("-0"), "createBigInteger(String) failed")
//    assertEquals(new BigInteger("0"), NumberUtils.createBigInteger("0"), "createBigInteger(String) failed")
//    testCreateBigIntegerFailure("#")
//    testCreateBigIntegerFailure("-#")
//    testCreateBigIntegerFailure("0x")
//    testCreateBigIntegerFailure("-0x")
//  }
//
//  protected def testCreateBigIntegerFailure(str: String) = assertThrows(classOf[NumberFormatException], () => NumberUtils.createBigInteger(str), "createBigInteger(\"" + str + "\") should have failed.")
//
//  @Test def testCreateBigDecimal() = {
//    assertEquals(new BigDecimal("1234.5"), NumberUtils.createBigDecimal("1234.5"), "createBigDecimal(String) failed")
//    assertNull(NumberUtils.createBigDecimal(null), "createBigDecimal(null) failed")
//    this.testCreateBigDecimalFailure("")
//    this.testCreateBigDecimalFailure(" ")
//    this.testCreateBigDecimalFailure("\b\t\n\f\r")
//    this.testCreateBigDecimalFailure("\u00A0\uFEFF\u000B\u000C\u001C\u001D\u001E\u001F")
//    this.testCreateBigDecimalFailure("-") // sign alone not valid
//    this.testCreateBigDecimalFailure("--") // comment in NumberUtils suggests some implementations may incorrectly allow this
//    this.testCreateBigDecimalFailure("--0")
//    this.testCreateBigDecimalFailure("+")
//    this.testCreateBigDecimalFailure("++") // in case this was also allowed by some JVMs
//    this.testCreateBigDecimalFailure("++0")
//  }
//
//  protected def testCreateBigDecimalFailure(str: String) = assertThrows(classOf[NumberFormatException], () => NumberUtils.createBigDecimal(str), "createBigDecimal(\"" + str + "\") should have failed.")
//
//  // min/max tests
//  // ----------------------------------------------------------------------
//  @Test def testMinLong_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.min(null.asInstanceOf[Array[Long]]))
//
//  @Test def testMinLong_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.min)
//
//  @Test def testMinLong() = {
//    assertEquals(5, NumberUtils.min(5), "min(long[]) failed for array length 1")
//    assertEquals(6, NumberUtils.min(6, 9), "min(long[]) failed for array length 2")
//    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
//    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
//  }
//
//  @Test def testMinInt_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.min(null.asInstanceOf[Array[Int]]))
//
//  @Test def testMinInt_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.min)
//
//  @Test def testMinInt() = {
//    assertEquals(5, NumberUtils.min(5), "min(int[]) failed for array length 1")
//    assertEquals(6, NumberUtils.min(6, 9), "min(int[]) failed for array length 2")
//    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
//    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
//  }
//
//  @Test def testMinShort_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.min(null.asInstanceOf[Array[Short]]))
//
//  @Test def testMinShort_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.min)
//
//  @Test def testMinShort() = {
//    assertEquals(5, NumberUtils.min(5), "min(short[]) failed for array length 1")
//    assertEquals(6, NumberUtils.min(6, 9), "min(short[]) failed for array length 2")
//    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
//    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
//  }
//
//  @Test def testMinByte_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.min(null.asInstanceOf[Array[Byte]]))
//
//  @Test def testMinByte_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.min)
//
//  @Test def testMinByte() = {
//    assertEquals(5, NumberUtils.min(5), "min(byte[]) failed for array length 1")
//    assertEquals(6, NumberUtils.min(6, 9), "min(byte[]) failed for array length 2")
//    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10))
//    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10))
//  }
//
//  @Test def testMinDouble_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.min(null.asInstanceOf[Array[Double]]))
//
//  @Test def testMinDouble_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.min)
//
//  @Test def testMinDouble() = {
//    assertEquals(5.12, NumberUtils.min(5.12), "min(double[]) failed for array length 1")
//    assertEquals(6.23, NumberUtils.min(6.23, 9.34), "min(double[]) failed for array length 2")
//    assertEquals(-10.45, NumberUtils.min(-10.45, -5.56, 0, 5.67, 10.78), "min(double[]) failed for array length 5")
//    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10), 0.0001)
//    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10), 0.0001)
//  }
//
//  @Test def testMinFloat_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.min(null.asInstanceOf[Array[Float]]))
//
//  @Test def testMinFloat_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.min)
//
//  @Test def testMinFloat() = {
//    assertEquals(5.9f, NumberUtils.min(5.9f), "min(float[]) failed for array length 1")
//    assertEquals(6.8f, NumberUtils.min(6.8f, 9.7f), "min(float[]) failed for array length 2")
//    assertEquals(-10.6f, NumberUtils.min(-10.6f, -5.5f, 0, 5.4f, 10.3f), "min(float[]) failed for array length 5")
//    assertEquals(-10, NumberUtils.min(-10, -5, 0, 5, 10), 0.0001f)
//    assertEquals(-10, NumberUtils.min(-5, 0, -10, 5, 10), 0.0001f)
//  }
//
//  @Test def testMaxLong_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.max(null.asInstanceOf[Array[Long]]))
//
//  @Test def testMaxLong_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.max)
//
//  @Test def testMaxLong() = {
//    assertEquals(5, NumberUtils.max(5), "max(long[]) failed for array length 1")
//    assertEquals(9, NumberUtils.max(6, 9), "max(long[]) failed for array length 2")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), "max(long[]) failed for array length 5")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
//    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
//  }
//
//  @Test def testMaxInt_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.max(null.asInstanceOf[Array[Int]]))
//
//  @Test def testMaxInt_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.max)
//
//  @Test def testMaxInt() = {
//    assertEquals(5, NumberUtils.max(5), "max(int[]) failed for array length 1")
//    assertEquals(9, NumberUtils.max(6, 9), "max(int[]) failed for array length 2")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), "max(int[]) failed for array length 5")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
//    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
//  }
//
//  @Test def testMaxShort_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.max(null.asInstanceOf[Array[Short]]))
//
//  @Test def testMaxShort_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.max)
//
//  @Test def testMaxShort() = {
//    assertEquals(5, NumberUtils.max(5), "max(short[]) failed for array length 1")
//    assertEquals(9, NumberUtils.max(6, 9), "max(short[]) failed for array length 2")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), "max(short[]) failed for array length 5")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
//    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
//  }
//
//  @Test def testMaxByte_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.max(null.asInstanceOf[Array[Byte]]))
//
//  @Test def testMaxByte_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.max)
//
//  @Test def testMaxByte() = {
//    assertEquals(5, NumberUtils.max(5), "max(byte[]) failed for array length 1")
//    assertEquals(9, NumberUtils.max(6, 9), "max(byte[]) failed for array length 2")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), "max(byte[]) failed for array length 5")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10))
//    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10))
//  }
//
//  @Test def testMaxDouble_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.max(null.asInstanceOf[Array[Double]]))
//
//  @Test def testMaxDouble_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.max)
//
//  @Test def testMaxDouble() = {
//    val d = null
//    assertThrows(classOf[NullPointerException], () => NumberUtils.max(d), "No exception was thrown for null input.")
//    assertThrows(classOf[IllegalArgumentException], NumberUtils.max, "No exception was thrown for empty input.")
//    assertEquals(5.1f, NumberUtils.max(5.1f), "max(double[]) failed for array length 1")
//    assertEquals(9.2f, NumberUtils.max(6.3f, 9.2f), "max(double[]) failed for array length 2")
//    assertEquals(10.4f, NumberUtils.max(-10.5f, -5.6f, 0, 5.7f, 10.4f), "max(double[]) failed for float length 5")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), 0.0001)
//    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10), 0.0001)
//  }
//
//  @Test def testMaxFloat_nullArray() = assertThrows(classOf[NullPointerException], () => NumberUtils.max(null.asInstanceOf[Array[Float]]))
//
//  @Test def testMaxFloat_emptyArray() = assertThrows(classOf[IllegalArgumentException], NumberUtils.max)
//
//  @Test def testMaxFloat() = {
//    assertEquals(5.1f, NumberUtils.max(5.1f), "max(float[]) failed for array length 1")
//    assertEquals(9.2f, NumberUtils.max(6.3f, 9.2f), "max(float[]) failed for array length 2")
//    assertEquals(10.4f, NumberUtils.max(-10.5f, -5.6f, 0, 5.7f, 10.4f), "max(float[]) failed for float length 5")
//    assertEquals(10, NumberUtils.max(-10, -5, 0, 5, 10), 0.0001f)
//    assertEquals(10, NumberUtils.max(-5, 0, 10, 5, -10), 0.0001f)
//  }
//
//  @Test def testMinimumLong() = {
//    assertEquals(12345L, NumberUtils.min(12345L, 12345L + 1L, 12345L + 2L), "minimum(long, long, long) 1 failed")
//    assertEquals(12345L, NumberUtils.min(12345L + 1L, 12345L, 12345 + 2L), "minimum(long, long, long) 2 failed")
//    assertEquals(12345L, NumberUtils.min(12345L + 1L, 12345L + 2L, 12345L), "minimum(long, long, long) 3 failed")
//    assertEquals(12345L, NumberUtils.min(12345L + 1L, 12345L, 12345L), "minimum(long, long, long) 4 failed")
//    assertEquals(12345L, NumberUtils.min(12345L, 12345L, 12345L), "minimum(long, long, long) 5 failed")
//  }
//
//  @Test def testMinimumInt() = {
//    assertEquals(12345, NumberUtils.min(12345, 12345 + 1, 12345 + 2), "minimum(int, int, int) 1 failed")
//    assertEquals(12345, NumberUtils.min(12345 + 1, 12345, 12345 + 2), "minimum(int, int, int) 2 failed")
//    assertEquals(12345, NumberUtils.min(12345 + 1, 12345 + 2, 12345), "minimum(int, int, int) 3 failed")
//    assertEquals(12345, NumberUtils.min(12345 + 1, 12345, 12345), "minimum(int, int, int) 4 failed")
//    assertEquals(12345, NumberUtils.min(12345, 12345, 12345), "minimum(int, int, int) 5 failed")
//  }
//
//  @Test def testMinimumShort() = {
//    val low = 1234
//    val mid = 1234 + 1
//    val high = 1234 + 2
//    assertEquals(low, NumberUtils.min(low, mid, high), "minimum(short, short, short) 1 failed")
//    assertEquals(low, NumberUtils.min(mid, low, high), "minimum(short, short, short) 2 failed")
//    assertEquals(low, NumberUtils.min(mid, high, low), "minimum(short, short, short) 3 failed")
//    assertEquals(low, NumberUtils.min(low, mid, low), "minimum(short, short, short) 4 failed")
//  }
//
//  @Test def testMinimumByte() = {
//    val low = 123
//    val mid = 123 + 1
//    val high = 123 + 2
//    assertEquals(low, NumberUtils.min(low, mid, high), "minimum(byte, byte, byte) 1 failed")
//    assertEquals(low, NumberUtils.min(mid, low, high), "minimum(byte, byte, byte) 2 failed")
//    assertEquals(low, NumberUtils.min(mid, high, low), "minimum(byte, byte, byte) 3 failed")
//    assertEquals(low, NumberUtils.min(low, mid, low), "minimum(byte, byte, byte) 4 failed")
//  }
//
//  @Test def testMinimumDouble() = {
//    val low = 12.3
//    val mid = 12.3 + 1
//    val high = 12.3 + 2
//    assertEquals(low, NumberUtils.min(low, mid, high), 0.0001)
//    assertEquals(low, NumberUtils.min(mid, low, high), 0.0001)
//    assertEquals(low, NumberUtils.min(mid, high, low), 0.0001)
//    assertEquals(low, NumberUtils.min(low, mid, low), 0.0001)
//    assertEquals(mid, NumberUtils.min(high, mid, high), 0.0001)
//  }
//
//  @Test def testMinimumFloat() = {
//    val low = 12.3f
//    val mid = 12.3f + 1
//    val high = 12.3f + 2
//    assertEquals(low, NumberUtils.min(low, mid, high), 0.0001f)
//    assertEquals(low, NumberUtils.min(mid, low, high), 0.0001f)
//    assertEquals(low, NumberUtils.min(mid, high, low), 0.0001f)
//    assertEquals(low, NumberUtils.min(low, mid, low), 0.0001f)
//    assertEquals(mid, NumberUtils.min(high, mid, high), 0.0001f)
//  }
//
//  @Test def testMaximumLong() = {
//    assertEquals(12345L, NumberUtils.max(12345L, 12345L - 1L, 12345L - 2L), "maximum(long, long, long) 1 failed")
//    assertEquals(12345L, NumberUtils.max(12345L - 1L, 12345L, 12345L - 2L), "maximum(long, long, long) 2 failed")
//    assertEquals(12345L, NumberUtils.max(12345L - 1L, 12345L - 2L, 12345L), "maximum(long, long, long) 3 failed")
//    assertEquals(12345L, NumberUtils.max(12345L - 1L, 12345L, 12345L), "maximum(long, long, long) 4 failed")
//    assertEquals(12345L, NumberUtils.max(12345L, 12345L, 12345L), "maximum(long, long, long) 5 failed")
//  }
//
//  @Test def testMaximumInt() = {
//    assertEquals(12345, NumberUtils.max(12345, 12345 - 1, 12345 - 2), "maximum(int, int, int) 1 failed")
//    assertEquals(12345, NumberUtils.max(12345 - 1, 12345, 12345 - 2), "maximum(int, int, int) 2 failed")
//    assertEquals(12345, NumberUtils.max(12345 - 1, 12345 - 2, 12345), "maximum(int, int, int) 3 failed")
//    assertEquals(12345, NumberUtils.max(12345 - 1, 12345, 12345), "maximum(int, int, int) 4 failed")
//    assertEquals(12345, NumberUtils.max(12345, 12345, 12345), "maximum(int, int, int) 5 failed")
//  }
//
//  @Test def testMaximumShort() = {
//    val low = 1234
//    val mid = 1234 + 1
//    val high = 1234 + 2
//    assertEquals(high, NumberUtils.max(low, mid, high), "maximum(short, short, short) 1 failed")
//    assertEquals(high, NumberUtils.max(mid, low, high), "maximum(short, short, short) 2 failed")
//    assertEquals(high, NumberUtils.max(mid, high, low), "maximum(short, short, short) 3 failed")
//    assertEquals(high, NumberUtils.max(high, mid, high), "maximum(short, short, short) 4 failed")
//  }
//
//  @Test def testMaximumByte() = {
//    val low = 123
//    val mid = 123 + 1
//    val high = 123 + 2
//    assertEquals(high, NumberUtils.max(low, mid, high), "maximum(byte, byte, byte) 1 failed")
//    assertEquals(high, NumberUtils.max(mid, low, high), "maximum(byte, byte, byte) 2 failed")
//    assertEquals(high, NumberUtils.max(mid, high, low), "maximum(byte, byte, byte) 3 failed")
//    assertEquals(high, NumberUtils.max(high, mid, high), "maximum(byte, byte, byte) 4 failed")
//  }
//
//  @Test def testMaximumDouble() = {
//    val low = 12.3
//    val mid = 12.3 + 1
//    val high = 12.3 + 2
//    assertEquals(high, NumberUtils.max(low, mid, high), 0.0001)
//    assertEquals(high, NumberUtils.max(mid, low, high), 0.0001)
//    assertEquals(high, NumberUtils.max(mid, high, low), 0.0001)
//    assertEquals(mid, NumberUtils.max(low, mid, low), 0.0001)
//    assertEquals(high, NumberUtils.max(high, mid, high), 0.0001)
//  }
//
//  @Test def testMaximumFloat() = {
//    val low = 12.3f
//    val mid = 12.3f + 1
//    val high = 12.3f + 2
//    assertEquals(high, NumberUtils.max(low, mid, high), 0.0001f)
//    assertEquals(high, NumberUtils.max(mid, low, high), 0.0001f)
//    assertEquals(high, NumberUtils.max(mid, high, low), 0.0001f)
//    assertEquals(mid, NumberUtils.max(low, mid, low), 0.0001f)
//    assertEquals(high, NumberUtils.max(high, mid, high), 0.0001f)
//  }
//
//  // Testing JDK against old Lang functionality
//  @Test def testCompareDouble() = {
//    assertEquals(0, Double.compare(Double.NaN, Double.NaN))
//    assertEquals(Double.compare(Double.NaN, Double.POSITIVE_INFINITY), +1)
//    assertEquals(Double.compare(Double.NaN, Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(Double.NaN, 1.2d), +1)
//    assertEquals(Double.compare(Double.NaN, 0.0d), +1)
//    assertEquals(Double.compare(Double.NaN, -0.0d), +1)
//    assertEquals(Double.compare(Double.NaN, -1.2d), +1)
//    assertEquals(Double.compare(Double.NaN, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(Double.NaN, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, Double.NaN), -1)
//    assertEquals(0, Double.compare(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, 1.2d), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, 0.0d), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, -0.0d), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, -1.2d), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(Double.MAX_VALUE, Double.NaN), -1)
//    assertEquals(Double.compare(Double.MAX_VALUE, Double.POSITIVE_INFINITY), -1)
//    assertEquals(0, Double.compare(Double.MAX_VALUE, Double.MAX_VALUE))
//    assertEquals(Double.compare(Double.MAX_VALUE, 1.2d), +1)
//    assertEquals(Double.compare(Double.MAX_VALUE, 0.0d), +1)
//    assertEquals(Double.compare(Double.MAX_VALUE, -0.0d), +1)
//    assertEquals(Double.compare(Double.MAX_VALUE, -1.2d), +1)
//    assertEquals(Double.compare(Double.MAX_VALUE, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(Double.MAX_VALUE, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(1.2d, Double.NaN), -1)
//    assertEquals(Double.compare(1.2d, Double.POSITIVE_INFINITY), -1)
//    assertEquals(Double.compare(1.2d, Double.MAX_VALUE), -1)
//    assertEquals(0, Double.compare(1.2d, 1.2d))
//    assertEquals(Double.compare(1.2d, 0.0d), +1)
//    assertEquals(Double.compare(1.2d, -0.0d), +1)
//    assertEquals(Double.compare(1.2d, -1.2d), +1)
//    assertEquals(Double.compare(1.2d, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(1.2d, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(0.0d, Double.NaN), -1)
//    assertEquals(Double.compare(0.0d, Double.POSITIVE_INFINITY), -1)
//    assertEquals(Double.compare(0.0d, Double.MAX_VALUE), -1)
//    assertEquals(Double.compare(0.0d, 1.2d), -1)
//    assertEquals(0, Double.compare(0.0d, 0.0d))
//    assertEquals(Double.compare(0.0d, -0.0d), +1)
//    assertEquals(Double.compare(0.0d, -1.2d), +1)
//    assertEquals(Double.compare(0.0d, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(0.0d, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(-0.0d, Double.NaN), -1)
//    assertEquals(Double.compare(-0.0d, Double.POSITIVE_INFINITY), -1)
//    assertEquals(Double.compare(-0.0d, Double.MAX_VALUE), -1)
//    assertEquals(Double.compare(-0.0d, 1.2d), -1)
//    assertEquals(Double.compare(-0.0d, 0.0d), -1)
//    assertEquals(0, Double.compare(-0.0d, -0.0d))
//    assertEquals(Double.compare(-0.0d, -1.2d), +1)
//    assertEquals(Double.compare(-0.0d, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(-0.0d, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(-1.2d, Double.NaN), -1)
//    assertEquals(Double.compare(-1.2d, Double.POSITIVE_INFINITY), -1)
//    assertEquals(Double.compare(-1.2d, Double.MAX_VALUE), -1)
//    assertEquals(Double.compare(-1.2d, 1.2d), -1)
//    assertEquals(Double.compare(-1.2d, 0.0d), -1)
//    assertEquals(Double.compare(-1.2d, -0.0d), -1)
//    assertEquals(0, Double.compare(-1.2d, -1.2d))
//    assertEquals(Double.compare(-1.2d, -Double.MAX_VALUE), +1)
//    assertEquals(Double.compare(-1.2d, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, Double.NaN), -1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, Double.POSITIVE_INFINITY), -1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, Double.MAX_VALUE), -1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, 1.2d), -1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, 0.0d), -1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, -0.0d), -1)
//    assertEquals(Double.compare(-Double.MAX_VALUE, -1.2d), -1)
//    assertEquals(0, Double.compare(-Double.MAX_VALUE, -Double.MAX_VALUE))
//    assertEquals(Double.compare(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY), +1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, Double.NaN), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, Double.MAX_VALUE), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, 1.2d), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, 0.0d), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, -0.0d), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, -1.2d), -1)
//    assertEquals(Double.compare(Double.NEGATIVE_INFINITY, -Double.MAX_VALUE), -1)
//    assertEquals(0, Double.compare(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY))
//  }
//
//  @Test def testCompareFloat() = {
//    assertEquals(0, Float.compare(Float.NaN, Float.NaN))
//    assertEquals(Float.compare(Float.NaN, Float.POSITIVE_INFINITY), +1)
//    assertEquals(Float.compare(Float.NaN, Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(Float.NaN, 1.2f), +1)
//    assertEquals(Float.compare(Float.NaN, 0.0f), +1)
//    assertEquals(Float.compare(Float.NaN, -0.0f), +1)
//    assertEquals(Float.compare(Float.NaN, -1.2f), +1)
//    assertEquals(Float.compare(Float.NaN, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(Float.NaN, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, Float.NaN), -1)
//    assertEquals(0, Float.compare(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY))
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, 1.2f), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, 0.0f), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, -0.0f), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, -1.2f), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(Float.MAX_VALUE, Float.NaN), -1)
//    assertEquals(Float.compare(Float.MAX_VALUE, Float.POSITIVE_INFINITY), -1)
//    assertEquals(0, Float.compare(Float.MAX_VALUE, Float.MAX_VALUE))
//    assertEquals(Float.compare(Float.MAX_VALUE, 1.2f), +1)
//    assertEquals(Float.compare(Float.MAX_VALUE, 0.0f), +1)
//    assertEquals(Float.compare(Float.MAX_VALUE, -0.0f), +1)
//    assertEquals(Float.compare(Float.MAX_VALUE, -1.2f), +1)
//    assertEquals(Float.compare(Float.MAX_VALUE, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(Float.MAX_VALUE, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(1.2f, Float.NaN), -1)
//    assertEquals(Float.compare(1.2f, Float.POSITIVE_INFINITY), -1)
//    assertEquals(Float.compare(1.2f, Float.MAX_VALUE), -1)
//    assertEquals(0, Float.compare(1.2f, 1.2f))
//    assertEquals(Float.compare(1.2f, 0.0f), +1)
//    assertEquals(Float.compare(1.2f, -0.0f), +1)
//    assertEquals(Float.compare(1.2f, -1.2f), +1)
//    assertEquals(Float.compare(1.2f, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(1.2f, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(0.0f, Float.NaN), -1)
//    assertEquals(Float.compare(0.0f, Float.POSITIVE_INFINITY), -1)
//    assertEquals(Float.compare(0.0f, Float.MAX_VALUE), -1)
//    assertEquals(Float.compare(0.0f, 1.2f), -1)
//    assertEquals(0, Float.compare(0.0f, 0.0f))
//    assertEquals(Float.compare(0.0f, -0.0f), +1)
//    assertEquals(Float.compare(0.0f, -1.2f), +1)
//    assertEquals(Float.compare(0.0f, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(0.0f, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(-0.0f, Float.NaN), -1)
//    assertEquals(Float.compare(-0.0f, Float.POSITIVE_INFINITY), -1)
//    assertEquals(Float.compare(-0.0f, Float.MAX_VALUE), -1)
//    assertEquals(Float.compare(-0.0f, 1.2f), -1)
//    assertEquals(Float.compare(-0.0f, 0.0f), -1)
//    assertEquals(0, Float.compare(-0.0f, -0.0f))
//    assertEquals(Float.compare(-0.0f, -1.2f), +1)
//    assertEquals(Float.compare(-0.0f, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(-0.0f, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(-1.2f, Float.NaN), -1)
//    assertEquals(Float.compare(-1.2f, Float.POSITIVE_INFINITY), -1)
//    assertEquals(Float.compare(-1.2f, Float.MAX_VALUE), -1)
//    assertEquals(Float.compare(-1.2f, 1.2f), -1)
//    assertEquals(Float.compare(-1.2f, 0.0f), -1)
//    assertEquals(Float.compare(-1.2f, -0.0f), -1)
//    assertEquals(0, Float.compare(-1.2f, -1.2f))
//    assertEquals(Float.compare(-1.2f, -Float.MAX_VALUE), +1)
//    assertEquals(Float.compare(-1.2f, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, Float.NaN), -1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, Float.POSITIVE_INFINITY), -1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, Float.MAX_VALUE), -1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, 1.2f), -1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, 0.0f), -1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, -0.0f), -1)
//    assertEquals(Float.compare(-Float.MAX_VALUE, -1.2f), -1)
//    assertEquals(0, Float.compare(-Float.MAX_VALUE, -Float.MAX_VALUE))
//    assertEquals(Float.compare(-Float.MAX_VALUE, Float.NEGATIVE_INFINITY), +1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, Float.NaN), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, Float.MAX_VALUE), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, 1.2f), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, 0.0f), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, -0.0f), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, -1.2f), -1)
//    assertEquals(Float.compare(Float.NEGATIVE_INFINITY, -Float.MAX_VALUE), -1)
//    assertEquals(0, Float.compare(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY))
//  }
//
//  @Test def testIsDigits() = {
//    assertFalse(NumberUtils.isDigits(null), "isDigits(null) failed")
//    assertFalse(NumberUtils.isDigits(""), "isDigits('') failed")
//    assertTrue(NumberUtils.isDigits("12345"), "isDigits(String) failed")
//    assertFalse(NumberUtils.isDigits("1234.5"), "isDigits(String) neg 1 failed")
//    assertFalse(NumberUtils.isDigits("1ab"), "isDigits(String) neg 3 failed")
//    assertFalse(NumberUtils.isDigits("abc"), "isDigits(String) neg 4 failed")
//  }
//
//  /**
//    * Tests isCreatable(String) and tests that createNumber(String) returns
//    * a valid number iff isCreatable(String) returns false.
//    */
//  @Test def testIsCreatable() = {
//    compareIsCreatableWithCreateNumber("12345", true)
//    compareIsCreatableWithCreateNumber("1234.5", true)
//    compareIsCreatableWithCreateNumber(".12345", true)
//    compareIsCreatableWithCreateNumber("1234E5", true)
//    compareIsCreatableWithCreateNumber("1234E+5", true)
//    compareIsCreatableWithCreateNumber("1234E-5", true)
//    compareIsCreatableWithCreateNumber("123.4E5", true)
//    compareIsCreatableWithCreateNumber("-1234", true)
//    compareIsCreatableWithCreateNumber("-1234.5", true)
//    compareIsCreatableWithCreateNumber("-.12345", true)
//    compareIsCreatableWithCreateNumber("-1234E5", true)
//    compareIsCreatableWithCreateNumber("0", true)
//    compareIsCreatableWithCreateNumber("0.1", true) // LANG-1216
//    compareIsCreatableWithCreateNumber("-0", true)
//    compareIsCreatableWithCreateNumber("01234", true)
//    compareIsCreatableWithCreateNumber("-01234", true)
//    compareIsCreatableWithCreateNumber("-0xABC123", true)
//    compareIsCreatableWithCreateNumber("-0x0", true)
//    compareIsCreatableWithCreateNumber("123.4E21D", true)
//    compareIsCreatableWithCreateNumber("-221.23F", true)
//    compareIsCreatableWithCreateNumber("22338L", true)
//    compareIsCreatableWithCreateNumber(null, false)
//    compareIsCreatableWithCreateNumber("", false)
//    compareIsCreatableWithCreateNumber(" ", false)
//    compareIsCreatableWithCreateNumber("\r\n\t", false)
//    compareIsCreatableWithCreateNumber("--2.3", false)
//    compareIsCreatableWithCreateNumber(".12.3", false)
//    compareIsCreatableWithCreateNumber("-123E", false)
//    compareIsCreatableWithCreateNumber("-123E+-212", false)
//    compareIsCreatableWithCreateNumber("-123E2.12", false)
//    compareIsCreatableWithCreateNumber("0xGF", false)
//    compareIsCreatableWithCreateNumber("0xFAE-1", false)
//    compareIsCreatableWithCreateNumber(".", false)
//    compareIsCreatableWithCreateNumber("-0ABC123", false)
//    compareIsCreatableWithCreateNumber("123.4E-D", false)
//    compareIsCreatableWithCreateNumber("123.4ED", false)
//    compareIsCreatableWithCreateNumber("1234E5l", false)
//    compareIsCreatableWithCreateNumber("11a", false)
//    compareIsCreatableWithCreateNumber("1a", false)
//    compareIsCreatableWithCreateNumber("a", false)
//    compareIsCreatableWithCreateNumber("11g", false)
//    compareIsCreatableWithCreateNumber("11z", false)
//    compareIsCreatableWithCreateNumber("11def", false)
//    compareIsCreatableWithCreateNumber("11d11", false)
//    compareIsCreatableWithCreateNumber("11 11", false)
//    compareIsCreatableWithCreateNumber(" 1111", false)
//    compareIsCreatableWithCreateNumber("1111 ", false)
//    compareIsCreatableWithCreateNumber("2.", true)
//    compareIsCreatableWithCreateNumber("1.1L", false) // LANG-664
//  }
//
//  @Test def testLANG971() = {
//    compareIsCreatableWithCreateNumber("0085", false)
//    compareIsCreatableWithCreateNumber("085", false)
//    compareIsCreatableWithCreateNumber("08", false)
//    compareIsCreatableWithCreateNumber("07", true)
//    compareIsCreatableWithCreateNumber("00", true)
//  }
//
//  @Test def testLANG992() = {
//    compareIsCreatableWithCreateNumber("0.0", true)
//    compareIsCreatableWithCreateNumber("0.4790", true)
//  }
//
//  @Test def testLANG972() = {
//    compareIsCreatableWithCreateNumber("0xABCD", true)
//    compareIsCreatableWithCreateNumber("0XABCD", true)
//  }
//
//  @Test def testLANG1252() = {
//    compareIsCreatableWithCreateNumber("+2", true)
//    compareIsCreatableWithCreateNumber("+2.0", true)
//  }
//
//  private def compareIsCreatableWithCreateNumber(`val`: String, expected: Boolean) = {
//    val isValid = NumberUtils.isCreatable(`val`)
//    val canCreate = checkCreateNumber(`val`)
//    assertTrue(isValid == expected && canCreate == expected, "Expecting " + expected + " for isCreatable/createNumber using \"" + `val` + "\" but got " + isValid + " and " + canCreate)
//  }
//
//  @Test def testIsNumber() = {
//    compareIsNumberWithCreateNumber("12345", true)
//    compareIsNumberWithCreateNumber("1234.5", true)
//    compareIsNumberWithCreateNumber(".12345", true)
//    compareIsNumberWithCreateNumber("1234E5", true)
//    compareIsNumberWithCreateNumber("1234E+5", true)
//    compareIsNumberWithCreateNumber("1234E-5", true)
//    compareIsNumberWithCreateNumber("123.4E5", true)
//    compareIsNumberWithCreateNumber("-1234", true)
//    compareIsNumberWithCreateNumber("-1234.5", true)
//    compareIsNumberWithCreateNumber("-.12345", true)
//    compareIsNumberWithCreateNumber("-0001.12345", true)
//    compareIsNumberWithCreateNumber("-000.12345", true)
//    compareIsNumberWithCreateNumber("+00.12345", true)
//    compareIsNumberWithCreateNumber("+0002.12345", true)
//    compareIsNumberWithCreateNumber("-1234E5", true)
//    compareIsNumberWithCreateNumber("0", true)
//    compareIsNumberWithCreateNumber("-0", true)
//    compareIsNumberWithCreateNumber("01234", true)
//    compareIsNumberWithCreateNumber("-01234", true)
//    compareIsNumberWithCreateNumber("-0xABC123", true)
//    compareIsNumberWithCreateNumber("-0x0", true)
//    compareIsNumberWithCreateNumber("123.4E21D", true)
//    compareIsNumberWithCreateNumber("-221.23F", true)
//    compareIsNumberWithCreateNumber("22338L", true)
//    compareIsNumberWithCreateNumber(null, false)
//    compareIsNumberWithCreateNumber("", false)
//    compareIsNumberWithCreateNumber(" ", false)
//    compareIsNumberWithCreateNumber("\r\n\t", false)
//    compareIsNumberWithCreateNumber("--2.3", false)
//    compareIsNumberWithCreateNumber(".12.3", false)
//    compareIsNumberWithCreateNumber("-123E", false)
//    compareIsNumberWithCreateNumber("-123E+-212", false)
//    compareIsNumberWithCreateNumber("-123E2.12", false)
//    compareIsNumberWithCreateNumber("0xGF", false)
//    compareIsNumberWithCreateNumber("0xFAE-1", false)
//    compareIsNumberWithCreateNumber(".", false)
//    compareIsNumberWithCreateNumber("-0ABC123", false)
//    compareIsNumberWithCreateNumber("123.4E-D", false)
//    compareIsNumberWithCreateNumber("123.4ED", false)
//    compareIsNumberWithCreateNumber("+000E.12345", false)
//    compareIsNumberWithCreateNumber("-000E.12345", false)
//    compareIsNumberWithCreateNumber("1234E5l", false)
//    compareIsNumberWithCreateNumber("11a", false)
//    compareIsNumberWithCreateNumber("1a", false)
//    compareIsNumberWithCreateNumber("a", false)
//    compareIsNumberWithCreateNumber("11g", false)
//    compareIsNumberWithCreateNumber("11z", false)
//    compareIsNumberWithCreateNumber("11def", false)
//    compareIsNumberWithCreateNumber("11d11", false)
//    compareIsNumberWithCreateNumber("11 11", false)
//    compareIsNumberWithCreateNumber(" 1111", false)
//    compareIsNumberWithCreateNumber("1111 ", false)
//    compareIsNumberWithCreateNumber("2.", true)
//    compareIsNumberWithCreateNumber("1.1L", false)
//  }
//
//  @Test def testIsNumberLANG971() = {
//    compareIsNumberWithCreateNumber("0085", false)
//    compareIsNumberWithCreateNumber("085", false)
//    compareIsNumberWithCreateNumber("08", false)
//    compareIsNumberWithCreateNumber("07", true)
//    compareIsNumberWithCreateNumber("00", true)
//  }
//
//  @Test def testIsNumberLANG992() = {
//    compareIsNumberWithCreateNumber("0.0", true)
//    compareIsNumberWithCreateNumber("0.4790", true)
//  }
//
//  @Test def testIsNumberLANG972() = {
//    compareIsNumberWithCreateNumber("0xABCD", true)
//    compareIsNumberWithCreateNumber("0XABCD", true)
//  }
//
//  @Test def testIsNumberLANG1252() = {
//    compareIsNumberWithCreateNumber("+2", true)
//    compareIsNumberWithCreateNumber("+2.0", true)
//  }
//
//  @Test def testIsNumberLANG1385() = compareIsNumberWithCreateNumber("L", false)
//
//  private def compareIsNumberWithCreateNumber(`val`: String, expected: Boolean) = {
//    val isValid = NumberUtils.isCreatable(`val`)
//    val canCreate = checkCreateNumber(`val`)
//    assertTrue(isValid == expected && canCreate == expected, "Expecting " + expected + " for isCreatable/createNumber using \"" + `val` + "\" but got " + isValid + " and " + canCreate)
//  }
//
//  @Test def testIsParsable() = {
//    assertFalse(NumberUtils.isParsable(null))
//    assertFalse(NumberUtils.isParsable(""))
//    assertFalse(NumberUtils.isParsable("0xC1AB"))
//    assertFalse(NumberUtils.isParsable("65CBA2"))
//    assertFalse(NumberUtils.isParsable("pendro"))
//    assertFalse(NumberUtils.isParsable("64, 2"))
//    assertFalse(NumberUtils.isParsable("64.2.2"))
//    assertFalse(NumberUtils.isParsable("64."))
//    assertFalse(NumberUtils.isParsable("64L"))
//    assertFalse(NumberUtils.isParsable("-"))
//    assertFalse(NumberUtils.isParsable("--2"))
//    assertTrue(NumberUtils.isParsable("64.2"))
//    assertTrue(NumberUtils.isParsable("64"))
//    assertTrue(NumberUtils.isParsable("018"))
//    assertTrue(NumberUtils.isParsable(".18"))
//    assertTrue(NumberUtils.isParsable("-65"))
//    assertTrue(NumberUtils.isParsable("-018"))
//    assertTrue(NumberUtils.isParsable("-018.2"))
//    assertTrue(NumberUtils.isParsable("-.236"))
//  }
//
//  private def checkCreateNumber(`val`: String) = try {
//    val obj = NumberUtils.createNumber(`val`)
//    obj != null
//  } catch {
//    case e: NumberFormatException =>
//      false
//  }
//
//  @SuppressWarnings(Array("cast")) // suppress instanceof warning check
//  @Test def testConstants(): Unit =  {
//    assertTrue(NumberUtils.LONG_ZERO.isInstanceOf[Long])
//    assertTrue(NumberUtils.LONG_ONE.isInstanceOf[Long])
//    assertTrue(NumberUtils.LONG_MINUS_ONE.isInstanceOf[Long])
//    assertTrue(NumberUtils.INTEGER_ZERO.isInstanceOf[Integer])
//    assertTrue(NumberUtils.INTEGER_ONE.isInstanceOf[Integer])
//    assertTrue(NumberUtils.INTEGER_MINUS_ONE.isInstanceOf[Integer])
//    assertTrue(NumberUtils.SHORT_ZERO.isInstanceOf[Short])
//    assertTrue(NumberUtils.SHORT_ONE.isInstanceOf[Short])
//    assertTrue(NumberUtils.SHORT_MINUS_ONE.isInstanceOf[Short])
//    assertTrue(NumberUtils.BYTE_ZERO.isInstanceOf[Byte])
//    assertTrue(NumberUtils.BYTE_ONE.isInstanceOf[Byte])
//    assertTrue(NumberUtils.BYTE_MINUS_ONE.isInstanceOf[Byte])
//    assertTrue(NumberUtils.DOUBLE_ZERO.isInstanceOf[Double])
//    assertTrue(NumberUtils.DOUBLE_ONE.isInstanceOf[Double])
//    assertTrue(NumberUtils.DOUBLE_MINUS_ONE.isInstanceOf[Double])
//    assertTrue(NumberUtils.FLOAT_ZERO.isInstanceOf[Float])
//    assertTrue(NumberUtils.FLOAT_ONE.isInstanceOf[Float])
//    assertTrue(NumberUtils.FLOAT_MINUS_ONE.isInstanceOf[Float])
//    assertEquals(0, NumberUtils.LONG_ZERO.longValue)
//    assertEquals(1, NumberUtils.LONG_ONE.longValue)
//    assertEquals(NumberUtils.LONG_MINUS_ONE.longValue, -1)
//    assertEquals(0, NumberUtils.INTEGER_ZERO.intValue)
//    assertEquals(1, NumberUtils.INTEGER_ONE.intValue)
//    assertEquals(NumberUtils.INTEGER_MINUS_ONE.intValue, -1)
//    assertEquals(0, NumberUtils.SHORT_ZERO.shortValue)
//    assertEquals(1, NumberUtils.SHORT_ONE.shortValue)
//    assertEquals(NumberUtils.SHORT_MINUS_ONE.shortValue, -1)
//    assertEquals(0, NumberUtils.BYTE_ZERO.byteValue)
//    assertEquals(1, NumberUtils.BYTE_ONE.byteValue)
//    assertEquals(NumberUtils.BYTE_MINUS_ONE.byteValue, -1)
//    assertEquals(0.0d, NumberUtils.DOUBLE_ZERO.doubleValue)
//    assertEquals(1.0d, NumberUtils.DOUBLE_ONE.doubleValue)
//    assertEquals(NumberUtils.DOUBLE_MINUS_ONE.doubleValue, -1.0d)
//    assertEquals(0.0f, NumberUtils.FLOAT_ZERO.floatValue)
//    assertEquals(1.0f, NumberUtils.FLOAT_ONE.floatValue)
//    assertEquals(NumberUtils.FLOAT_MINUS_ONE.floatValue, -1.0f)
//  }
//
//  @Test def testLang300(): Unit = {
//    NumberUtils.createNumber ("-1l")
//    NumberUtils.createNumber ("01l")
//    NumberUtils.createNumber ("1l")
//  }
//
//  @Test def testLang381(): Unit = {
//    assertTrue(Double.isNaN (NumberUtils.min (1.2, 2.5, Double.NaN) ) )
//    assertTrue(Double.isNaN (NumberUtils.max (1.2, 2.5, Double.NaN) ) )
//    assertTrue(Float.isNaN (NumberUtils.min (1.2f, 2.5f, Float.NaN) ) )
//    assertTrue(Float.isNaN (NumberUtils.max (1.2f, 2.5f, Float.NaN) ) )
//    val a: Array[Double] = Array[Double] (1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN)
//    assertTrue(Double.isNaN (NumberUtils.max (a) ) )
//    assertTrue(Double.isNaN (NumberUtils.min (a) ) )
//    val b: Array[Double] = Array[Double] (Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN)
//    assertTrue(Double.isNaN (NumberUtils.max (b) ) )
//    assertTrue(Double.isNaN (NumberUtils.min (b) ) )
//    val aF: Array[Float] = Array[Float] (1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN)
//    assertTrue(Float.isNaN (NumberUtils.max (aF) ) )
//    val bF: Array[Float] = Array[Float] (Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN)
//    assertTrue(Float.isNaN (NumberUtils.max (bF) ) )
//  }
//
//  @Test def compareInt(): Unit = {
//    assertTrue(NumberUtils.compare (- (3), 0) < 0)
//    assertEquals(0, NumberUtils.compare (113, 113) )
//    assertTrue(NumberUtils.compare (213, 32) > 0)
//  }
//
//  @Test def compareLong(): Unit = {
//    assertTrue(NumberUtils.compare (- (3L), 0L) < 0)
//    assertEquals(0, NumberUtils.compare (113L, 113L) )
//    assertTrue(NumberUtils.compare (213L, 32L) > 0)
//  }
//
//  @Test def compareShort(): Unit = {
//    assertTrue(NumberUtils.compare (- (3).toShort, 0.toShort) < 0)
//    assertEquals(0, NumberUtils.compare (113.toShort, 113.toShort) )
//    assertTrue(NumberUtils.compare (213.toShort, 32.toShort) > 0)
//  }
//
//  @Test def compareByte(): Unit = {
//    assertTrue(NumberUtils.compare (- (3).toByte, 0.toByte) < 0)
//    assertEquals(0, NumberUtils.compare (113.toByte, 113.toByte) )
//    assertTrue(NumberUtils.compare (123.toByte, 32.toByte) > 0)
//  }
//}
