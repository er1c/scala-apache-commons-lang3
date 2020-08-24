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

//package org.apache.commons.lang3.time
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotEquals
//import org.junit.Assert.assertTrue
//import java.text.ParseException
//import java.text.ParsePosition
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//import java.util.TimeZone
//import java.util.stream.Stream
//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.Arguments
//import org.junit.jupiter.params.provider.MethodSource
//
//
///**
//  * Compare FastDateParser with SimpleDateFormat
//  */
//object FastDateParserSDFTest {
//  def data = Stream.of( // General Time zone tests
//    Arguments.of("z yyyy", "GMT 2010", Locale.UK, true), // no offset specified, but this is allowed as a TimeZone name
//    Arguments.of("z yyyy", "GMT-123 2010", Locale.UK, false), Arguments.of("z yyyy", "GMT-1234 2010", Locale.UK, false), Arguments.of("z yyyy", "GMT-12:34 2010", Locale.UK, true), Arguments.of("z yyyy", "GMT-1:23 2010", Locale.UK, true), // RFC 822 tests
//    Arguments.of("z yyyy", "-1234 2010", Locale.UK, true), Arguments.of("z yyyy", "-12:34 2010", Locale.UK, false), Arguments.of("z yyyy", "-123 2010", Locale.UK, false), // year tests
//    Arguments.of("MM/dd/yyyy", "01/11/12", Locale.UK, true), Arguments.of("MM/dd/yy", "01/11/12", Locale.UK, true), // LANG-1089
//    Arguments.of("HH", "00", Locale.UK, true), // Hour in day (0-23)
//    Arguments.of("KK", "00", Locale.UK, true), // Hour in am/pm (0-11)
//    Arguments.of("hh", "00", Locale.UK, true), // Hour in am/pm (1-12), i.e. midday/midnight is 12, not 0
//    Arguments.of("kk", "00", Locale.UK, true), // Hour in day (1-24), i.e. midnight is 24, not 0
//    Arguments.of("HH", "01", Locale.UK, true), Arguments.of("KK", "01", Locale.UK, true), Arguments.of("hh", "01", Locale.UK, true), Arguments.of("kk", "01", Locale.UK, true), Arguments.of("HH", "11", Locale.UK, true), Arguments.of("KK", "11", Locale.UK, true), Arguments.of("hh", "11", Locale.UK, true), Arguments.of("kk", "11", Locale.UK, true), Arguments.of("HH", "12", Locale.UK, true), Arguments.of("KK", "12", Locale.UK, true), Arguments.of("hh", "12", Locale.UK, true), Arguments.of("kk", "12", Locale.UK, true), Arguments.of("HH", "13", Locale.UK, true), Arguments.of("KK", "13", Locale.UK, true), Arguments.of("hh", "13", Locale.UK, true), Arguments.of("kk", "13", Locale.UK, true), Arguments.of("HH", "23", Locale.UK, true), Arguments.of("KK", "23", Locale.UK, true), Arguments.of("hh", "23", Locale.UK, true), Arguments.of("kk", "23", Locale.UK, true), Arguments.of("HH", "24", Locale.UK, true), Arguments.of("KK", "24", Locale.UK, true), Arguments.of("hh", "24", Locale.UK, true), Arguments.of("kk", "24", Locale.UK, true), Arguments.of("HH", "25", Locale.UK, true), Arguments.of("KK", "25", Locale.UK, true), Arguments.of("hh", "25", Locale.UK, true), Arguments.of("kk", "25", Locale.UK, true), Arguments.of("HH", "48", Locale.UK, true), Arguments.of("KK", "48", Locale.UK, true), Arguments.of("hh", "48", Locale.UK, true), Arguments.of("kk", "48", Locale.UK, true) // Hour in day (1-24), i.e. midnight is 24, not 0)
//  private val timeZone = TimeZone.getDefault
//}
//
//class FastDateParserSDFTest {
//  @ParameterizedTest
//  @MethodSource("data") def testOriginal(format: String, input: String, locale: Locale, valid: Boolean) = checkParse(input, format, locale, valid)
//
//  @ParameterizedTest
//  @MethodSource("data") def testOriginalPP(format: String, input: String, locale: Locale, valid: Boolean) = checkParsePosition(input, format, locale, valid)
//
//  @ParameterizedTest
//  @MethodSource("data") def testUpperCase(format: String, input: String, locale: Locale, valid: Boolean) = checkParse(input.toUpperCase(locale), format, locale, valid)
//
//  @ParameterizedTest
//  @MethodSource("data") def testUpperCasePP(format: String, input: String, locale: Locale, valid: Boolean) = checkParsePosition(input.toUpperCase(locale), format, locale, valid)
//
//  @ParameterizedTest
//  @MethodSource("data") def testLowerCase(format: String, input: String, locale: Locale, valid: Boolean) = checkParse(input.toLowerCase(locale), format, locale, valid)
//
//  @ParameterizedTest
//  @MethodSource("data") def testLowerCasePP(format: String, input: String, locale: Locale, valid: Boolean) = checkParsePosition(input.toLowerCase(locale), format, locale, valid)
//
//  private def checkParse(formattedDate: String, format: String, locale: Locale, valid: Boolean) = {
//    val sdf = new SimpleDateFormat(format, locale)
//    sdf.setTimeZone(FastDateParserSDFTest.timeZone)
//    val fdf = new Nothing(format, FastDateParserSDFTest.timeZone, locale)
//    var expectedTime = null
//    var sdfE = null
//    try {
//      expectedTime = sdf.parse(formattedDate)
//      if (!valid) { // Error in test data
//        throw new RuntimeException("Test data error: expected SDF parse to fail, but got " + expectedTime)
//      }
//    } catch {
//      case e: ParseException =>
//        if (valid) throw new RuntimeException("Test data error: expected SDF parse to succeed, but got " + e)
//        sdfE = e.getClass
//    }
//    var actualTime = null
//    var fdfE = null
//    try {
//      actualTime = fdf.parse(formattedDate)
//      // failure in test
//      assertTrue(valid, "Expected FDP parse to fail, but got " + actualTime)
//    } catch {
//      case e: ParseException =>
//        assertFalse(valid, "Expected FDP parse to succeed, but got " + e)
//        fdfE = e.getClass
//    }
//    if (valid) assertEquals(expectedTime, actualTime, locale.toString + " " + formattedDate + "\n")
//    else assertEquals(sdfE, fdfE, locale.toString + " " + formattedDate + " expected same Exception ")
//  }
//
//  private def checkParsePosition(formattedDate: String, format: String, locale: Locale, valid: Boolean) = {
//    val sdf = new SimpleDateFormat(format, locale)
//    sdf.setTimeZone(FastDateParserSDFTest.timeZone)
//    val fdf = new Nothing(format, FastDateParserSDFTest.timeZone, locale)
//    val sdfP = new ParsePosition(0)
//    val expectedTime = sdf.parse(formattedDate, sdfP)
//    val sdferrorIndex = sdfP.getErrorIndex
//    if (valid) {
//      assertEquals(-1, sdferrorIndex, "Expected SDF error index -1 ")
//      val endIndex = sdfP.getIndex
//      val length = formattedDate.length
//      if (endIndex != length) throw new RuntimeException("Test data error: expected SDF parse to consume entire string; endindex " + endIndex + " != " + length)
//    }
//    else {
//      val errorIndex = sdfP.getErrorIndex
//      if (errorIndex == -1) throw new RuntimeException("Test data error: expected SDF parse to fail, but got " + expectedTime)
//    }
//    val fdfP = new ParsePosition(0)
//    val actualTime = fdf.parse(formattedDate, fdfP)
//    val fdferrorIndex = fdfP.getErrorIndex
//    if (valid) {
//      assertEquals(-1, fdferrorIndex, "Expected FDF error index -1 ")
//      val endIndex = fdfP.getIndex
//      val length = formattedDate.length
//      assertEquals(length, endIndex, "Expected FDF to parse full string " + fdfP)
//      assertEquals(expectedTime, actualTime, locale.toString + " " + formattedDate + "\n")
//    }
//    else {
//      assertNotEquals(-1, fdferrorIndex, "Test data error: expected FDF parse to fail, but got " + actualTime)
//      assertTrue(sdferrorIndex - fdferrorIndex <= 4, "FDF error index (" + fdferrorIndex + ") should approximate SDF index (" + sdferrorIndex + ")")
//    }
//  }
//}
