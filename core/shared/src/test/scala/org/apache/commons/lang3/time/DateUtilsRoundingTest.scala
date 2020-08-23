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
//import org.junit.Assert.assertNotEquals
//import java.text.DateFormat
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.Locale
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
///**
//  * These Unit-tests will check all possible extremes when using some rounding-methods of DateUtils.
//  * The extremes are tested at the switch-point in milliseconds
//  *
//  * According to the implementation SEMI_MONTH will either round/truncate to the 1st or 16th
//  * When rounding Calendar.MONTH it depends on the number of days within that month.
//  * A month with 28 days will be rounded up from the 15th
//  * A month with 29 or 30 days will be rounded up from the 16th
//  * A month with 31 days will be rounded up from the 17th
//  *
//  * @since 3.0
//  */
//class DateUtilsRoundingTest {
//  private[time] var dateTimeParser = null
//  private[time] var januaryOneDate = null
//  private[time] var targetYearDate = null
//  //No targetMonths, these must be tested for every type of month(28-31 days)
//  private[time] var targetDateDate = null
//  private[time] var targetDayOfMonthDate = null
//  private[time] var targetAmDate = null
//  private[time] var targetPmDate = null
//  private[time] var targetHourOfDayDate = null
//  private[time] var targetHourDate = null
//  private[time] var targetMinuteDate = null
//  private[time] var targetSecondDate = null
//  private[time] var targetMilliSecondDate = null
//  private[time] var januaryOneCalendar = null
//  @SuppressWarnings(Array("deprecation")) private[time] val fdf = DateFormatUtils.ISO_DATETIME_FORMAT
//
//  @BeforeEach
//  @throws[Exception]
//  def setUp() = {
//    dateTimeParser = new SimpleDateFormat("MMM dd, yyyy H:mm:ss.SSS", Locale.ENGLISH)
//    targetYearDate = dateTimeParser.parse("January 1, 2007 0:00:00.000")
//    targetDateDate = targetDayOfMonthDate = dateTimeParser.parse("June 1, 2008 0:00:00.000")
//    targetAmDate = dateTimeParser.parse("June 1, 2008 0:00:00.000")
//    targetPmDate = dateTimeParser.parse("June 1, 2008 12:00:00.000")
//    targetHourDate = dateTimeParser.parse("June 1, 2008 8:00:00.000")
//    targetHourOfDayDate = dateTimeParser.parse("June 1, 2008 8:00:00.000")
//    targetMinuteDate = dateTimeParser.parse("June 1, 2008 8:15:00.000")
//    targetSecondDate = dateTimeParser.parse("June 1, 2008 8:15:14.000")
//    targetMilliSecondDate = dateTimeParser.parse("June 1, 2008 8:15:14.231")
//    januaryOneDate = dateTimeParser.parse("January 1, 2008 0:00:00.000")
//    januaryOneCalendar = Calendar.getInstance
//    januaryOneCalendar.setTime(januaryOneDate)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.Year
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundYear() = {
//    val calendarField = Calendar.YEAR
//    val roundedUpDate = dateTimeParser.parse("January 1, 2008 0:00:00.000")
//    val roundedDownDate = targetYearDate
//    val lastRoundedDownDate = dateTimeParser.parse("June 30, 2007 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.MONTH
//    * Includes rounding months with 28, 29, 30 and 31 days
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundMonth() = {
//    val calendarField = Calendar.MONTH
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    //month with 28 days
//    roundedUpDate = dateTimeParser.parse("March 1, 2007 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("February 1, 2007 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("February 14, 2007 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 29 days
//    roundedUpDate = dateTimeParser.parse("March 1, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("February 1, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("February 15, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 30 days
//    roundedUpDate = dateTimeParser.parse("May 1, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("April 1, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("April 15, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 31 days
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("May 1, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("May 16, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //round to January 1
//    minDate = dateTimeParser.parse("December 17, 2007 00:00:00.000")
//    maxDate = dateTimeParser.parse("January 16, 2008 23:59:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with DateUtils.SEMI_MONTH
//    * Includes rounding months with 28, 29, 30 and 31 days, each with first and second half
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundSemiMonth() = {
//    val calendarField = DateUtils.SEMI_MONTH
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    //month with 28 days (1)
//    roundedUpDate = dateTimeParser.parse("February 16, 2007 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("February 1, 2007 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("February 8, 2007 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 28 days (2)
//    roundedUpDate = dateTimeParser.parse("March 1, 2007 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("February 16, 2007 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("February 23, 2007 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 29 days (1)
//    roundedUpDate = dateTimeParser.parse("February 16, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("February 1, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("February 8, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 29 days (2)
//    roundedUpDate = dateTimeParser.parse("March 1, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("February 16, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("February 23, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 30 days (1)
//    roundedUpDate = dateTimeParser.parse("April 16, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("April 1, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("April 8, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 30 days (2)
//    roundedUpDate = dateTimeParser.parse("May 1, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("April 16, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("April 23, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 31 days (1)
//    roundedUpDate = dateTimeParser.parse("May 16, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("May 1, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("May 8, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //month with 31 days (2)
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 0:00:00.000")
//    roundedDownDate = dateTimeParser.parse("May 16, 2008 0:00:00.000")
//    lastRoundedDownDate = dateTimeParser.parse("May 23, 2008 23:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 24, 2007 00:00:00.000")
//    maxDate = dateTimeParser.parse("January 8, 2008 23:59:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.DATE
//    * Includes rounding the extremes of one day
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundDate() = {
//    val calendarField = Calendar.DATE
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedUpDate = dateTimeParser.parse("June 2, 2008 0:00:00.000")
//    roundedDownDate = targetDateDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 11:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 12:00:00.000")
//    maxDate = dateTimeParser.parse("January 1, 2008 11:59:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.DAY_OF_MONTH
//    * Includes rounding the extremes of one day
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundDayOfMonth() = {
//    val calendarField = Calendar.DAY_OF_MONTH
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedUpDate = dateTimeParser.parse("June 2, 2008 0:00:00.000")
//    roundedDownDate = targetDayOfMonthDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 11:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 12:00:00.000")
//    maxDate = dateTimeParser.parse("January 1, 2008 11:59:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.AM_PM
//    * Includes rounding the extremes of both AM and PM of one day
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundAmPm() = {
//    val calendarField = Calendar.AM_PM
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    //AM
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 12:00:00.000")
//    roundedDownDate = targetAmDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 5:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    //PM
//    roundedUpDate = dateTimeParser.parse("June 2, 2008 0:00:00.000")
//    roundedDownDate = targetPmDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 17:59:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 18:00:00.000")
//    maxDate = dateTimeParser.parse("January 1, 2008 5:59:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.HOUR_OF_DAY
//    * Includes rounding the extremes of one hour
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundHourOfDay() = {
//    val calendarField = Calendar.HOUR_OF_DAY
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 9:00:00.000")
//    roundedDownDate = targetHourOfDayDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 8:29:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 23:30:00.000")
//    maxDate = dateTimeParser.parse("January 1, 2008 0:29:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.HOUR
//    * Includes rounding the extremes of one hour
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundHour() = {
//    val calendarField = Calendar.HOUR
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 9:00:00.000")
//    roundedDownDate = targetHourDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 8:29:59.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 23:30:00.000")
//    maxDate = dateTimeParser.parse("January 1, 2008 0:29:59.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.MINUTE
//    * Includes rounding the extremes of one minute
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundMinute() = {
//    val calendarField = Calendar.MINUTE
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 8:16:00.000")
//    roundedDownDate = targetMinuteDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 8:15:29.999")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 23:59:30.000")
//    maxDate = dateTimeParser.parse("January 1, 2008 0:00:29.999")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.SECOND
//    * Includes rounding the extremes of one second
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundSecond() = {
//    val calendarField = Calendar.SECOND
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 8:15:15.000")
//    roundedDownDate = targetSecondDate
//    lastRoundedDownDate = dateTimeParser.parse("June 1, 2008 8:15:14.499")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = dateTimeParser.parse("December 31, 2007 23:59:59.500")
//    maxDate = dateTimeParser.parse("January 1, 2008 0:00:00.499")
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Tests DateUtils.round()-method with Calendar.MILLISECOND
//    * Includes rounding the extremes of one second
//    * Includes rounding to January 1
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testRoundMilliSecond() = {
//    val calendarField = Calendar.MILLISECOND
//    var roundedUpDate = null
//    var roundedDownDate = null
//    var lastRoundedDownDate = null
//    var minDate = null
//    var maxDate = null
//    roundedDownDate = lastRoundedDownDate = targetMilliSecondDate
//    roundedUpDate = dateTimeParser.parse("June 1, 2008 8:15:14.232")
//    baseRoundTest(roundedUpDate, roundedDownDate, lastRoundedDownDate, calendarField)
//    minDate = maxDate = januaryOneDate
//    roundToJanuaryFirst(minDate, maxDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.YEAR
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateYear() = {
//    val calendarField = Calendar.YEAR
//    val lastTruncateDate = dateTimeParser.parse("December 31, 2007 23:59:59.999")
//    baseTruncateTest(targetYearDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.MONTH
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateMonth() = {
//    val calendarField = Calendar.MONTH
//    val truncatedDate = dateTimeParser.parse("March 1, 2008 0:00:00.000")
//    val lastTruncateDate = dateTimeParser.parse("March 31, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with DateUtils.SEMI_MONTH
//    * Includes truncating months with 28, 29, 30 and 31 days, each with first and second half
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateSemiMonth() = {
//    val calendarField = DateUtils.SEMI_MONTH
//    var truncatedDate = null
//    var lastTruncateDate = null
//    truncatedDate = dateTimeParser.parse("February 1, 2007 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("February 15, 2007 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("February 16, 2007 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("February 28, 2007 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("February 1, 2008 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("February 15, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("February 16, 2008 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("February 29, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("April 1, 2008 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("April 15, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("April 16, 2008 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("April 30, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("March 1, 2008 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("March 15, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//    truncatedDate = dateTimeParser.parse("March 16, 2008 0:00:00.000")
//    lastTruncateDate = dateTimeParser.parse("March 31, 2008 23:59:59.999")
//    baseTruncateTest(truncatedDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.DATE
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateDate() = {
//    val calendarField = Calendar.DATE
//    val lastTruncateDate = dateTimeParser.parse("June 1, 2008 23:59:59.999")
//    baseTruncateTest(targetDateDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.DAY_OF_MONTH
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateDayOfMonth() = {
//    val calendarField = Calendar.DAY_OF_MONTH
//    val lastTruncateDate = dateTimeParser.parse("June 1, 2008 23:59:59.999")
//    baseTruncateTest(targetDayOfMonthDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.AM_PM
//    * Includes truncating the extremes of both AM and PM of one day
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateAmPm() = {
//    val calendarField = Calendar.AM_PM
//    var lastTruncateDate = dateTimeParser.parse("June 1, 2008 11:59:59.999")
//    baseTruncateTest(targetAmDate, lastTruncateDate, calendarField)
//    lastTruncateDate = dateTimeParser.parse("June 1, 2008 23:59:59.999")
//    baseTruncateTest(targetPmDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.HOUR
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateHour() = {
//    val calendarField = Calendar.HOUR
//    val lastTruncateDate = dateTimeParser.parse("June 1, 2008 8:59:59.999")
//    baseTruncateTest(targetHourDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.HOUR_OF_DAY
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateHourOfDay() = {
//    val calendarField = Calendar.HOUR_OF_DAY
//    val lastTruncateDate = dateTimeParser.parse("June 1, 2008 8:59:59.999")
//    baseTruncateTest(targetHourOfDayDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.MINUTE
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateMinute() = {
//    val calendarField = Calendar.MINUTE
//    val lastTruncateDate = dateTimeParser.parse("June 1, 2008 8:15:59.999")
//    baseTruncateTest(targetMinuteDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.SECOND
//    *
//    * @throws Exception so we don't have to catch it
//    * @since 3.0
//    */
//  @Test
//  @throws[Exception]
//  def testTruncateSecond() = {
//    val calendarField = Calendar.SECOND
//    val lastTruncateDate = dateTimeParser.parse("June 1, 2008 8:15:14.999")
//    baseTruncateTest(targetSecondDate, lastTruncateDate, calendarField)
//  }
//
//  /**
//    * Test DateUtils.truncate()-method with Calendar.SECOND
//    *
//    * @since 3.0
//    */
//  @Test def testTruncateMilliSecond() = {
//    val calendarField = Calendar.MILLISECOND
//    baseTruncateTest(targetMilliSecondDate, targetMilliSecondDate, calendarField)
//  }
//
//  /**
//    * When using this basetest all extremes are tested.<br>
//    * It will test the Date, Calendar and Object-implementation<br>
//    * lastRoundDownDate should round down to roundedDownDate<br>
//    * lastRoundDownDate + 1 millisecond should round up to roundedUpDate
//    *
//    * @param roundedUpDate     the next rounded date after <strong>roundedDownDate</strong> when using <strong>calendarField</strong>
//    * @param roundedDownDate   the result if <strong>lastRoundDownDate</strong> was rounded with <strong>calendarField</strong>
//    * @param lastRoundDownDate rounding this value with <strong>calendarField</strong> will result in <strong>roundedDownDate</strong>
//    * @param calendarField     a Calendar.field value
//    * @since 3.0
//    */
//  protected def baseRoundTest(roundedUpDate: Date, roundedDownDate: Date, lastRoundDownDate: Date, calendarField: Int) = {
//    val firstRoundUpDate = DateUtils.addMilliseconds(lastRoundDownDate, 1)
//    //Date-comparison
//    assertEquals(roundedDownDate, DateUtils.round(roundedDownDate, calendarField))
//    assertEquals(roundedUpDate, DateUtils.round(roundedUpDate, calendarField))
//    assertEquals(roundedDownDate, DateUtils.round(lastRoundDownDate, calendarField))
//    assertEquals(roundedUpDate, DateUtils.round(firstRoundUpDate, calendarField))
//    //Calendar-initiations
//    var roundedUpCalendar = null
//    var roundedDownCalendar = null
//    var lastRoundDownCalendar = null
//    var firstRoundUpCalendar = null
//    roundedDownCalendar = Calendar.getInstance
//    roundedUpCalendar = Calendar.getInstance
//    lastRoundDownCalendar = Calendar.getInstance
//    firstRoundUpCalendar = Calendar.getInstance
//    roundedDownCalendar.setTime(roundedDownDate)
//    roundedUpCalendar.setTime(roundedUpDate)
//    lastRoundDownCalendar.setTime(lastRoundDownDate)
//    firstRoundUpCalendar.setTime(firstRoundUpDate)
//    //Calendar-comparison
//    assertEquals(roundedDownCalendar, DateUtils.round(roundedDownCalendar, calendarField))
//    assertEquals(roundedUpCalendar, DateUtils.round(roundedUpCalendar, calendarField))
//    assertEquals(roundedDownCalendar, DateUtils.round(lastRoundDownCalendar, calendarField))
//    assertEquals(roundedUpCalendar, DateUtils.round(firstRoundUpCalendar, calendarField))
//    //Object-comparison
//    assertEquals(roundedDownDate, DateUtils.round(roundedDownDate.asInstanceOf[Any], calendarField))
//    assertEquals(roundedUpDate, DateUtils.round(roundedUpDate.asInstanceOf[Any], calendarField))
//    assertEquals(roundedDownDate, DateUtils.round(lastRoundDownDate.asInstanceOf[Any], calendarField))
//    assertEquals(roundedUpDate, DateUtils.round(firstRoundUpDate.asInstanceOf[Any], calendarField))
//    assertEquals(roundedDownDate, DateUtils.round(roundedDownCalendar.asInstanceOf[Any], calendarField))
//    assertEquals(roundedUpDate, DateUtils.round(roundedUpCalendar.asInstanceOf[Any], calendarField))
//    assertEquals(roundedDownDate, DateUtils.round(lastRoundDownDate.asInstanceOf[Any], calendarField))
//    assertEquals(roundedUpDate, DateUtils.round(firstRoundUpDate.asInstanceOf[Any], calendarField))
//  }
//
//  /**
//    * When using this basetest all extremes are tested.<br>
//    * It will test the Date, Calendar and Object-implementation<br>
//    * lastTruncateDate should round down to truncatedDate<br>
//    * lastTruncateDate + 1 millisecond should never round down to truncatedDate
//    *
//    * @param truncatedDate    expected Date when <strong>lastTruncateDate</strong> is truncated with <strong>calendarField</strong>
//    * @param lastTruncateDate the last possible Date which will truncate to <strong>truncatedDate</strong> with <strong>calendarField</strong>
//    * @param calendarField    a Calendar.field value
//    * @since 3.0
//    */
//  protected def baseTruncateTest(truncatedDate: Date, lastTruncateDate: Date, calendarField: Int) = {
//    val nextTruncateDate = DateUtils.addMilliseconds(lastTruncateDate, 1)
//    assertEquals(truncatedDate, DateUtils.truncate(truncatedDate, calendarField), "Truncating " + fdf.format(truncatedDate) + " as Date with CalendarField-value " + calendarField + " must return itself")
//    assertEquals(truncatedDate, DateUtils.truncate(lastTruncateDate, calendarField))
//    assertNotEquals(truncatedDate, DateUtils.truncate(nextTruncateDate, calendarField), fdf.format(lastTruncateDate) + " is not an extreme when truncating as Date with CalendarField-value " + calendarField)
//    var truncatedCalendar = null
//    var lastTruncateCalendar = null
//    var nextTruncateCalendar = null
//    truncatedCalendar = Calendar.getInstance
//    lastTruncateCalendar = Calendar.getInstance
//    nextTruncateCalendar = Calendar.getInstance
//    truncatedCalendar.setTime(truncatedDate)
//    lastTruncateCalendar.setTime(lastTruncateDate)
//    nextTruncateCalendar.setTime(nextTruncateDate)
//    assertEquals(truncatedCalendar, DateUtils.truncate(truncatedCalendar, calendarField), "Truncating " + fdf.format(truncatedCalendar) + " as Calendar with CalendarField-value " + calendarField + " must return itself")
//    assertEquals(truncatedCalendar, DateUtils.truncate(lastTruncateCalendar, calendarField))
//    assertNotEquals(truncatedCalendar, DateUtils.truncate(nextTruncateCalendar, calendarField), fdf.format(lastTruncateCalendar) + " is not an extreme when truncating as Calendar with CalendarField-value " + calendarField)
//    assertEquals(truncatedDate, DateUtils.truncate(truncatedDate.asInstanceOf[Any], calendarField), "Truncating " + fdf.format(truncatedDate) + " as Date cast to Object with CalendarField-value " + calendarField + " must return itself as Date")
//    assertEquals(truncatedDate, DateUtils.truncate(lastTruncateDate.asInstanceOf[Any], calendarField))
//    assertNotEquals(truncatedDate, DateUtils.truncate(nextTruncateDate.asInstanceOf[Any], calendarField), fdf.format(lastTruncateDate) + " is not an extreme when truncating as Date cast to Object with CalendarField-value " + calendarField)
//    assertEquals(truncatedDate, DateUtils.truncate(truncatedCalendar.asInstanceOf[Any], calendarField), "Truncating " + fdf.format(truncatedCalendar) + " as Calendar cast to Object with CalendarField-value " + calendarField + " must return itself as Date")
//    assertEquals(truncatedDate, DateUtils.truncate(lastTruncateCalendar.asInstanceOf[Any], calendarField))
//    assertNotEquals(truncatedDate, DateUtils.truncate(nextTruncateCalendar.asInstanceOf[Any], calendarField), fdf.format(lastTruncateCalendar) + " is not an extreme when truncating as Calendar cast to Object with CalendarField-value " + calendarField)
//  }
//
//  /**
//    *
//    * Any January 1 could be considered as the ultimate extreme.
//    * Instead of comparing the results if the input has a difference of 1 millisecond we check the output to be exactly January first.
//    *
//    * @param minDate       the lower bound
//    * @param maxDate       the upper bound
//    * @param calendarField a Calendar.field value
//    * @since 3.0
//    */
//  protected def roundToJanuaryFirst(minDate: Date, maxDate: Date, calendarField: Int) = {
//    assertEquals(januaryOneDate, DateUtils.round(januaryOneDate, calendarField), "Rounding " + fdf.format(januaryOneDate) + " as Date with CalendarField-value " + calendarField + " must return itself")
//    assertEquals(januaryOneDate, DateUtils.round(minDate, calendarField))
//    assertEquals(januaryOneDate, DateUtils.round(maxDate, calendarField))
//    val minCalendar = Calendar.getInstance
//    minCalendar.setTime(minDate)
//    val maxCalendar = Calendar.getInstance
//    maxCalendar.setTime(maxDate)
//    assertEquals(januaryOneCalendar, DateUtils.round(januaryOneCalendar, calendarField), "Rounding " + fdf.format(januaryOneCalendar) + " as Date with CalendarField-value " + calendarField + " must return itself")
//    assertEquals(januaryOneCalendar, DateUtils.round(minCalendar, calendarField))
//    assertEquals(januaryOneCalendar, DateUtils.round(maxCalendar, calendarField))
//    val toPrevRoundDate = DateUtils.addMilliseconds(minDate, -1)
//    val toNextRoundDate = DateUtils.addMilliseconds(maxDate, 1)
//    assertNotEquals(januaryOneDate, DateUtils.round(toPrevRoundDate, calendarField), fdf.format(minDate) + " is not an lower-extreme when rounding as Date with CalendarField-value " + calendarField)
//    assertNotEquals(januaryOneDate, DateUtils.round(toNextRoundDate, calendarField), fdf.format(maxDate) + " is not an upper-extreme when rounding as Date with CalendarField-value " + calendarField)
//    val toPrevRoundCalendar = Calendar.getInstance
//    toPrevRoundCalendar.setTime(toPrevRoundDate)
//    val toNextRoundCalendar = Calendar.getInstance
//    toNextRoundCalendar.setTime(toNextRoundDate)
//    assertNotEquals(januaryOneDate, DateUtils.round(toPrevRoundDate, calendarField), fdf.format(minCalendar) + " is not an lower-extreme when rounding as Date with CalendarField-value " + calendarField)
//    assertNotEquals(januaryOneDate, DateUtils.round(toNextRoundDate, calendarField), fdf.format(maxCalendar) + " is not an upper-extreme when rounding as Date with CalendarField-value " + calendarField)
//  }
//}
