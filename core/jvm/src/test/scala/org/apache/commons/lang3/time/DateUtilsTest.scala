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
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNotSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import java.text.DateFormat
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.GregorianCalendar
//import java.util
//import java.util.Locale
//import java.util.NoSuchElementException
//import java.util.TimeZone
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//import org.junitpioneer.jupiter.DefaultLocale
//
///**
//  * Unit tests {@link org.apache.commons.lang3.time.DateUtils}.
//  */
//object DateUtilsTest {
//  private var BASE_DATE = null
//
//  @BeforeAll def classSetup() = {
//    val cal = new GregorianCalendar(2000, 6, 5, 4, 3, 2)
//    cal.set(Calendar.MILLISECOND, 1)
//    BASE_DATE = cal.getTime
//  }
//
//  /**
//    * This checks that this is a 7 element iterator of Calendar objects
//    * that are dates (no time), and exactly 1 day spaced after each other.
//    */
//  private def assertWeekIterator(it: util.Iterator[_], start: Calendar) = {
//    val `end` = start.clone.asInstanceOf[Calendar]
//    `end`.add(Calendar.DATE, 6)
//    assertWeekIterator(it, start, `end`)
//  }
//
//  /**
//    * Convenience method for when working with Date objects
//    */
//  private def assertWeekIterator(it: util.Iterator[_], start: Date, `end`: Date) = {
//    val calStart = Calendar.getInstance
//    calStart.setTime(start)
//    val calEnd = Calendar.getInstance
//    calEnd.setTime(`end`)
//    assertWeekIterator(it, calStart, calEnd)
//  }
//
//  /**
//    * This checks that this is a 7 divisble iterator of Calendar objects
//    * that are dates (no time), and exactly 1 day spaced after each other
//    * (in addition to the proper start and stop dates)
//    */
//  private def assertWeekIterator(it: util.Iterator[_], start: Calendar, `end`: Calendar) = {
//    var cal = it.next.asInstanceOf[Calendar]
//    assertCalendarsEquals("", start, cal, 0)
//    var last = null
//    var count = 1
//    while ( {
//      it.hasNext
//    }) { //Check this is just a date (no time component)
//      assertCalendarsEquals("", cal, DateUtils.truncate(cal, Calendar.DATE), 0)
//      last = cal
//      cal = it.next.asInstanceOf[Calendar]
//      count += 1
//      //Check that this is one day more than the last date
//      last.add(Calendar.DATE, 1)
//      assertCalendarsEquals("", last, cal, 0)
//    }
//    assertFalse(count % 7 != 0, "There were " + count + " days in this iterator")
//    assertCalendarsEquals("", `end`, cal, 0)
//  }
//
//  /**
//    * Used to check that Calendar objects are close enough
//    * delta is in milliseconds
//    */
//  private def assertCalendarsEquals(message: String, cal1: Calendar, cal2: Calendar, delta: Long) = assertFalse(Math.abs(cal1.getTime.getTime - cal2.getTime.getTime) > delta, message + " expected " + cal1.getTime + " but got " + cal2.getTime)
//}
//
//class DateUtilsTest {
//  private var dateParser = null
//  private var dateTimeParser = null
//  private var dateAmPm1 = null
//  private var dateAmPm2 = null
//  private var dateAmPm3 = null
//  private var dateAmPm4 = null
//  private var date0 = null
//  private var date1 = null
//  private var date2 = null
//  private var date3 = null
//  private var date4 = null
//  private var date5 = null
//  private var date6 = null
//  private var date7 = null
//  private var date8 = null
//  private var calAmPm1 = null
//  private var calAmPm2 = null
//  private var calAmPm3 = null
//  private var calAmPm4 = null
//  private var cal1 = null
//  private var cal2 = null
//  private var cal3 = null
//  private var cal4 = null
//  private var cal5 = null
//  private var cal6 = null
//  private var cal7 = null
//  private var cal8 = null
//  private var zone = null
//  private var defaultZone = null
//
//  @BeforeEach
//  @throws[Exception]
//  def setUp() = {
//    dateParser = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
//    dateTimeParser = new SimpleDateFormat("MMM dd, yyyy H:mm:ss.SSS", Locale.ENGLISH)
//    dateAmPm1 = dateTimeParser.parse("February 3, 2002 01:10:00.000")
//    dateAmPm2 = dateTimeParser.parse("February 3, 2002 11:10:00.000")
//    dateAmPm3 = dateTimeParser.parse("February 3, 2002 13:10:00.000")
//    dateAmPm4 = dateTimeParser.parse("February 3, 2002 19:10:00.000")
//    date0 = dateTimeParser.parse("February 3, 2002 12:34:56.789")
//    date1 = dateTimeParser.parse("February 12, 2002 12:34:56.789")
//    date2 = dateTimeParser.parse("November 18, 2001 1:23:11.321")
//    defaultZone = TimeZone.getDefault
//    zone = TimeZone.getTimeZone("MET")
//    try {
//      TimeZone.setDefault(zone)
//      dateTimeParser.setTimeZone(zone)
//      date3 = dateTimeParser.parse("March 30, 2003 05:30:45.000")
//      date4 = dateTimeParser.parse("March 30, 2003 01:10:00.000")
//      date5 = dateTimeParser.parse("March 30, 2003 01:40:00.000")
//      date6 = dateTimeParser.parse("March 30, 2003 02:10:00.000")
//      date7 = dateTimeParser.parse("March 30, 2003 02:40:00.000")
//      date8 = dateTimeParser.parse("October 26, 2003 05:30:45.000")
//    } finally {
//      dateTimeParser.setTimeZone(defaultZone)
//      TimeZone.setDefault(defaultZone)
//    }
//    calAmPm1 = Calendar.getInstance
//    calAmPm1.setTime(dateAmPm1)
//    calAmPm2 = Calendar.getInstance
//    calAmPm2.setTime(dateAmPm2)
//    calAmPm3 = Calendar.getInstance
//    calAmPm3.setTime(dateAmPm3)
//    calAmPm4 = Calendar.getInstance
//    calAmPm4.setTime(dateAmPm4)
//    cal1 = Calendar.getInstance
//    cal1.setTime(date1)
//    cal2 = Calendar.getInstance
//    cal2.setTime(date2)
//    try {
//      TimeZone.setDefault(zone)
//      cal3 = Calendar.getInstance
//      cal3.setTime(date3)
//      cal4 = Calendar.getInstance
//      cal4.setTime(date4)
//      cal5 = Calendar.getInstance
//      cal5.setTime(date5)
//      cal6 = Calendar.getInstance
//      cal6.setTime(date6)
//      cal7 = Calendar.getInstance
//      cal7.setTime(date7)
//      cal8 = Calendar.getInstance
//      cal8.setTime(date8)
//    } finally TimeZone.setDefault(defaultZone)
//  }
//
//  //-----------------------------------------------------------------------
//  @Test def testConstructor() = {
//    assertNotNull(new Nothing)
//    val cons = classOf[Nothing].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test def testIsSameDay_Date() = {
//    var datea = new GregorianCalendar(2004, 6, 9, 13, 45).getTime
//    var dateb = new GregorianCalendar(2004, 6, 9, 13, 45).getTime
//    assertTrue(DateUtils.isSameDay(datea, dateb))
//    dateb = new GregorianCalendar(2004, 6, 10, 13, 45).getTime
//    assertFalse(DateUtils.isSameDay(datea, dateb))
//    datea = new GregorianCalendar(2004, 6, 10, 13, 45).getTime
//    assertTrue(DateUtils.isSameDay(datea, dateb))
//    dateb = new GregorianCalendar(2005, 6, 10, 13, 45).getTime
//    assertFalse(DateUtils.isSameDay(datea, dateb))
//  }
//
//  @Test def testIsSameDay_DateNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameDay(null.asInstanceOf[Date], null))
//
//  @Test def testIsSameDay_DateNullNotNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameDay(null, new Date))
//
//  @Test def testIsSameDay_DateNotNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameDay(new Date, null))
//
//  @Test def testIsSameDay_Cal() = {
//    val cala = new GregorianCalendar(2004, 6, 9, 13, 45)
//    val calb = new GregorianCalendar(2004, 6, 9, 13, 45)
//    assertTrue(DateUtils.isSameDay(cala, calb))
//    calb.add(Calendar.DAY_OF_YEAR, 1)
//    assertFalse(DateUtils.isSameDay(cala, calb))
//    cala.add(Calendar.DAY_OF_YEAR, 1)
//    assertTrue(DateUtils.isSameDay(cala, calb))
//    calb.add(Calendar.YEAR, 1)
//    assertFalse(DateUtils.isSameDay(cala, calb))
//  }
//
//  @Test def testIsSameDay_CalNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameDay(null.asInstanceOf[Calendar], null))
//
//  @Test def testIsSameDay_CalNullNotNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameDay(null, Calendar.getInstance))
//
//  @Test def testIsSameDay_CalNotNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameDay(Calendar.getInstance, null))
//
//  @Test def testIsSameInstant_Date() = {
//    var datea = new GregorianCalendar(2004, 6, 9, 13, 45).getTime
//    var dateb = new GregorianCalendar(2004, 6, 9, 13, 45).getTime
//    assertTrue(DateUtils.isSameInstant(datea, dateb))
//    dateb = new GregorianCalendar(2004, 6, 10, 13, 45).getTime
//    assertFalse(DateUtils.isSameInstant(datea, dateb))
//    datea = new GregorianCalendar(2004, 6, 10, 13, 45).getTime
//    assertTrue(DateUtils.isSameInstant(datea, dateb))
//    dateb = new GregorianCalendar(2005, 6, 10, 13, 45).getTime
//    assertFalse(DateUtils.isSameInstant(datea, dateb))
//  }
//
//  @Test def testIsSameInstant_DateNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameInstant(null.asInstanceOf[Date], null))
//
//  @Test def testIsSameInstant_DateNullNotNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameInstant(null, new Date))
//
//  @Test def testIsSameInstant_DateNotNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameInstant(new Date, null))
//
//  @Test def testIsSameInstant_Cal() = {
//    val cala = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"))
//    val calb = new GregorianCalendar(TimeZone.getTimeZone("GMT-1"))
//    cala.set(2004, Calendar.JULY, 9, 13, 45, 0)
//    cala.set(Calendar.MILLISECOND, 0)
//    calb.set(2004, Calendar.JULY, 9, 13, 45, 0)
//    calb.set(Calendar.MILLISECOND, 0)
//    assertFalse(DateUtils.isSameInstant(cala, calb))
//    calb.set(2004, Calendar.JULY, 9, 11, 45, 0)
//    assertTrue(DateUtils.isSameInstant(cala, calb))
//  }
//
//  @Test def testIsSameInstant_CalNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameInstant(null.asInstanceOf[Calendar], null))
//
//  @Test def testIsSameInstant_CalNullNotNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameInstant(null, Calendar.getInstance))
//
//  @Test def testIsSameInstant_CalNotNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameInstant(Calendar.getInstance, null))
//
//  @Test def testIsSameLocalTime_Cal() = {
//    val cala = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"))
//    val calb = new GregorianCalendar(TimeZone.getTimeZone("GMT-1"))
//    cala.set(2004, Calendar.JULY, 9, 13, 45, 0)
//    cala.set(Calendar.MILLISECOND, 0)
//    calb.set(2004, Calendar.JULY, 9, 13, 45, 0)
//    calb.set(Calendar.MILLISECOND, 0)
//    assertTrue(DateUtils.isSameLocalTime(cala, calb))
//    val calc = Calendar.getInstance
//    val cald = Calendar.getInstance
//    calc.set(2004, Calendar.JULY, 9, 4, 0, 0)
//    cald.set(2004, Calendar.JULY, 9, 16, 0, 0)
//    calc.set(Calendar.MILLISECOND, 0)
//    cald.set(Calendar.MILLISECOND, 0)
//    assertFalse(DateUtils.isSameLocalTime(calc, cald), "LANG-677")
//    calb.set(2004, Calendar.JULY, 9, 11, 45, 0)
//    assertFalse(DateUtils.isSameLocalTime(cala, calb))
//  }
//
//  @Test def testIsSameLocalTime_CalNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameLocalTime(null, null))
//
//  @Test def testIsSameLocalTime_CalNullNotNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameLocalTime(null, Calendar.getInstance))
//
//  @Test def testIsSameLocalTime_CalNotNullNull() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.isSameLocalTime(Calendar.getInstance, null))
//
//  @Test
//  @throws[Exception]
//  def testParseDate() = {
//    val cal = new GregorianCalendar(1972, 11, 3)
//    var dateStr = "1972-12-03"
//    val parsers = Array[String]("yyyy'-'DDD", "yyyy'-'MM'-'dd", "yyyyMMdd")
//    var date = DateUtils.parseDate(dateStr, parsers)
//    assertEquals(cal.getTime, date)
//    dateStr = "1972-338"
//    date = DateUtils.parseDate(dateStr, parsers)
//    assertEquals(cal.getTime, date)
//    dateStr = "19721203"
//    date = DateUtils.parseDate(dateStr, parsers)
//    assertEquals(cal.getTime, date)
//  }
//
//  @Test def testParseDate_NoDateString() = {
//    val parsers = Array[String]("yyyy'-'DDD", "yyyy'-'MM'-'dd", "yyyyMMdd")
//    assertThrows(classOf[ParseException], () => DateUtils.parseDate("PURPLE", parsers))
//  }
//
//  @Test def testParseDate_InvalidDateString() = {
//    val parsers = Array[String]("yyyy'-'DDD", "yyyy'-'MM'-'dd", "yyyyMMdd")
//    assertThrows(classOf[ParseException], () => DateUtils.parseDate("197212AB", parsers))
//  }
//
//  @Test def testParseDate_Null() = {
//    val parsers = Array[String]("yyyy'-'DDD", "yyyy'-'MM'-'dd", "yyyyMMdd")
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.parseDate(null, parsers))
//  }
//
//  @Test def testParse_NullParsers() = assertThrows(classOf[IllegalArgumentException], () => DateUtils.parseDate("19721203", null.asInstanceOf[Array[String]]))
//
//  @Test def testParse_EmptyParsers() = assertThrows(classOf[ParseException], () => DateUtils.parseDate("19721203"))
//
//  // LANG-486
//  @Test
//  @throws[Exception]
//  def testParseDateWithLeniency() = {
//    val cal = new GregorianCalendar(1998, 6, 30)
//    val dateStr = "02 942, 1996"
//    val parsers = Array[String]("MM DDD, yyyy")
//    val date = DateUtils.parseDate(dateStr, parsers)
//    assertEquals(cal.getTime, date)
//    assertThrows(classOf[ParseException], () => DateUtils.parseDateStrictly(dateStr, parsers))
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddYears() = {
//    var result = DateUtils.addYears(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addYears(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2001, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addYears(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 1999, 6, 5, 4, 3, 2, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddMonths() = {
//    var result = DateUtils.addMonths(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addMonths(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 7, 5, 4, 3, 2, 1)
//    result = DateUtils.addMonths(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 5, 5, 4, 3, 2, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddWeeks() = {
//    var result = DateUtils.addWeeks(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addWeeks(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 12, 4, 3, 2, 1)
//    result = DateUtils.addWeeks(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1) // july
//    assertDate(result, 2000, 5, 28, 4, 3, 2, 1) // june
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddDays() = {
//    var result = DateUtils.addDays(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addDays(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 6, 4, 3, 2, 1)
//    result = DateUtils.addDays(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 4, 4, 3, 2, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddHours() = {
//    var result = DateUtils.addHours(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addHours(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 5, 3, 2, 1)
//    result = DateUtils.addHours(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 3, 3, 2, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddMinutes() = {
//    var result = DateUtils.addMinutes(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addMinutes(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 4, 2, 1)
//    result = DateUtils.addMinutes(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 2, 2, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddSeconds() = {
//    var result = DateUtils.addSeconds(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addSeconds(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 3, 1)
//    result = DateUtils.addSeconds(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 1, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testAddMilliseconds() = {
//    var result = DateUtils.addMilliseconds(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.addMilliseconds(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 2)
//    result = DateUtils.addMilliseconds(DateUtilsTest.BASE_DATE, -1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 0)
//  }
//
//  // -----------------------------------------------------------------------
//  @Test
//  @throws[Exception]
//  def testSetYears() = {
//    var result = DateUtils.setYears(DateUtilsTest.BASE_DATE, 2000)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.setYears(DateUtilsTest.BASE_DATE, 2008)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2008, 6, 5, 4, 3, 2, 1)
//    result = DateUtils.setYears(DateUtilsTest.BASE_DATE, 2005)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2005, 6, 5, 4, 3, 2, 1)
//  }
//
//  @Test
//  @throws[Exception]
//  def testSetMonths() = {
//    var result = DateUtils.setMonths(DateUtilsTest.BASE_DATE, 5)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 5, 5, 4, 3, 2, 1)
//    result = DateUtils.setMonths(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 1, 5, 4, 3, 2, 1)
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.setMonths(DateUtilsTest.BASE_DATE, 12), "DateUtils.setMonths did not throw an expected IllegalArgumentException.")
//  }
//
//  @Test
//  @throws[Exception]
//  def testSetDays() = {
//    var result = DateUtils.setDays(DateUtilsTest.BASE_DATE, 1)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 1, 4, 3, 2, 1)
//    result = DateUtils.setDays(DateUtilsTest.BASE_DATE, 29)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 29, 4, 3, 2, 1)
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.setDays(DateUtilsTest.BASE_DATE, 32), "DateUtils.setDays did not throw an expected IllegalArgumentException.")
//  }
//
//  @Test
//  @throws[Exception]
//  def testSetHours() = {
//    var result = DateUtils.setHours(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 0, 3, 2, 1)
//    result = DateUtils.setHours(DateUtilsTest.BASE_DATE, 23)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 23, 3, 2, 1)
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.setHours(DateUtilsTest.BASE_DATE, 24), "DateUtils.setHours did not throw an expected IllegalArgumentException.")
//  }
//
//  @Test
//  @throws[Exception]
//  def testSetMinutes() = {
//    var result = DateUtils.setMinutes(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 0, 2, 1)
//    result = DateUtils.setMinutes(DateUtilsTest.BASE_DATE, 59)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 59, 2, 1)
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.setMinutes(DateUtilsTest.BASE_DATE, 60), "DateUtils.setMinutes did not throw an expected IllegalArgumentException.")
//  }
//
//  @Test
//  @throws[Exception]
//  def testSetSeconds() = {
//    var result = DateUtils.setSeconds(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 0, 1)
//    result = DateUtils.setSeconds(DateUtilsTest.BASE_DATE, 59)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 59, 1)
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.setSeconds(DateUtilsTest.BASE_DATE, 60), "DateUtils.setSeconds did not throw an expected IllegalArgumentException.")
//  }
//
//  @Test
//  @throws[Exception]
//  def testSetMilliseconds() = {
//    var result = DateUtils.setMilliseconds(DateUtilsTest.BASE_DATE, 0)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 0)
//    result = DateUtils.setMilliseconds(DateUtilsTest.BASE_DATE, 999)
//    assertNotSame(DateUtilsTest.BASE_DATE, result)
//    assertDate(DateUtilsTest.BASE_DATE, 2000, 6, 5, 4, 3, 2, 1)
//    assertDate(result, 2000, 6, 5, 4, 3, 2, 999)
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.setMilliseconds(DateUtilsTest.BASE_DATE, 1000), "DateUtils.setMilliseconds did not throw an expected IllegalArgumentException.")
//  }
//
//  private def assertDate(date: Date, year: Int, month: Int, day: Int, hour: Int, min: Int, sec: Int, mil: Int) = {
//    val cal = new GregorianCalendar
//    cal.setTime(date)
//    assertEquals(year, cal.get(Calendar.YEAR))
//    assertEquals(month, cal.get(Calendar.MONTH))
//    assertEquals(day, cal.get(Calendar.DAY_OF_MONTH))
//    assertEquals(hour, cal.get(Calendar.HOUR_OF_DAY))
//    assertEquals(min, cal.get(Calendar.MINUTE))
//    assertEquals(sec, cal.get(Calendar.SECOND))
//    assertEquals(mil, cal.get(Calendar.MILLISECOND))
//  }
//
//  @Test def testToCalendar() = {
//    assertEquals(date1, DateUtils.toCalendar(date1).getTime, "Failed to convert to a Calendar and back")
//    assertThrows(classOf[NullPointerException], () => DateUtils.toCalendar(null))
//  }
//
//  @Test def testToCalendarWithDateNull() = assertThrows(classOf[NullPointerException], () => DateUtils.toCalendar(null, zone))
//
//  @Test def testToCalendarWithTimeZoneNull() = assertThrows(classOf[NullPointerException], () => DateUtils.toCalendar(date1, null))
//
//  @Test def testToCalendarWithDateAndTimeZoneNotNull() = {
//    val c = DateUtils.toCalendar(date2, defaultZone)
//    assertEquals(date2, c.getTime, "Convert Date and TimeZone to a Calendar, but failed to get the Date back")
//    assertEquals(defaultZone, c.getTimeZone, "Convert Date and TimeZone to a Calendar, but failed to get the TimeZone back")
//  }
//
//  @Test def testToCalendarWithDateAndTimeZoneNull() = assertThrows(classOf[NullPointerException], () => DateUtils.toCalendar(null, null))
//
//  /**
//    * Tests various values with the round method
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testRound() = { // tests for public static Date round(Date date, int field)
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.round(date1, Calendar.YEAR), "round year-1 failed")
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.round(date2, Calendar.YEAR), "round year-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.round(date1, Calendar.MONTH), "round month-1 failed")
//    assertEquals(dateParser.parse("December 1, 2001"), DateUtils.round(date2, Calendar.MONTH), "round month-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.round(date0, DateUtils.SEMI_MONTH), "round semimonth-0 failed")
//    assertEquals(dateParser.parse("February 16, 2002"), DateUtils.round(date1, DateUtils.SEMI_MONTH), "round semimonth-1 failed")
//    assertEquals(dateParser.parse("November 16, 2001"), DateUtils.round(date2, DateUtils.SEMI_MONTH), "round semimonth-2 failed")
//    assertEquals(dateParser.parse("February 13, 2002"), DateUtils.round(date1, Calendar.DATE), "round date-1 failed")
//    assertEquals(dateParser.parse("November 18, 2001"), DateUtils.round(date2, Calendar.DATE), "round date-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 13:00:00.000"), DateUtils.round(date1, Calendar.HOUR), "round hour-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:00:00.000"), DateUtils.round(date2, Calendar.HOUR), "round hour-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:35:00.000"), DateUtils.round(date1, Calendar.MINUTE), "round minute-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:00.000"), DateUtils.round(date2, Calendar.MINUTE), "round minute-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:57.000"), DateUtils.round(date1, Calendar.SECOND), "round second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:11.000"), DateUtils.round(date2, Calendar.SECOND), "round second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.round(dateAmPm1, Calendar.AM_PM), "round ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.round(dateAmPm2, Calendar.AM_PM), "round ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.round(dateAmPm3, Calendar.AM_PM), "round ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.round(dateAmPm4, Calendar.AM_PM), "round ampm-4 failed")
//    // tests for public static Date round(Object date, int field)
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.round(date1.asInstanceOf[Any], Calendar.YEAR), "round year-1 failed")
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.round(date2.asInstanceOf[Any], Calendar.YEAR), "round year-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.round(date1.asInstanceOf[Any], Calendar.MONTH), "round month-1 failed")
//    assertEquals(dateParser.parse("December 1, 2001"), DateUtils.round(date2.asInstanceOf[Any], Calendar.MONTH), "round month-2 failed")
//    assertEquals(dateParser.parse("February 16, 2002"), DateUtils.round(date1.asInstanceOf[Any], DateUtils.SEMI_MONTH), "round semimonth-1 failed")
//    assertEquals(dateParser.parse("November 16, 2001"), DateUtils.round(date2.asInstanceOf[Any], DateUtils.SEMI_MONTH), "round semimonth-2 failed")
//    assertEquals(dateParser.parse("February 13, 2002"), DateUtils.round(date1.asInstanceOf[Any], Calendar.DATE), "round date-1 failed")
//    assertEquals(dateParser.parse("November 18, 2001"), DateUtils.round(date2.asInstanceOf[Any], Calendar.DATE), "round date-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 13:00:00.000"), DateUtils.round(date1.asInstanceOf[Any], Calendar.HOUR), "round hour-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:00:00.000"), DateUtils.round(date2.asInstanceOf[Any], Calendar.HOUR), "round hour-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:35:00.000"), DateUtils.round(date1.asInstanceOf[Any], Calendar.MINUTE), "round minute-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:00.000"), DateUtils.round(date2.asInstanceOf[Any], Calendar.MINUTE), "round minute-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:57.000"), DateUtils.round(date1.asInstanceOf[Any], Calendar.SECOND), "round second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:11.000"), DateUtils.round(date2.asInstanceOf[Any], Calendar.SECOND), "round second-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:57.000"), DateUtils.round(cal1.asInstanceOf[Any], Calendar.SECOND), "round calendar second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:11.000"), DateUtils.round(cal2.asInstanceOf[Any], Calendar.SECOND), "round calendar second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.round(dateAmPm1.asInstanceOf[Any], Calendar.AM_PM), "round ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.round(dateAmPm2.asInstanceOf[Any], Calendar.AM_PM), "round ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.round(dateAmPm3.asInstanceOf[Any], Calendar.AM_PM), "round ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.round(dateAmPm4.asInstanceOf[Any], Calendar.AM_PM), "round ampm-4 failed")
//    assertThrows(classOf[NullPointerException], () => DateUtils.round(null.asInstanceOf[Date], Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.round(null.asInstanceOf[Calendar], Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.round(null.asInstanceOf[Any], Calendar.SECOND))
//    assertThrows(classOf[ClassCastException], () => DateUtils.round("", Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.round(date1, -9999))
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.round(calAmPm1.asInstanceOf[Any], Calendar.AM_PM), "round ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.round(calAmPm2.asInstanceOf[Any], Calendar.AM_PM), "round ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.round(calAmPm3.asInstanceOf[Any], Calendar.AM_PM), "round ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.round(calAmPm4.asInstanceOf[Any], Calendar.AM_PM), "round ampm-4 failed")
//    // Fix for https://issues.apache.org/bugzilla/show_bug.cgi?id=25560 / LANG-13
//    // Test rounding across the beginning of daylight saving time
//    try {
//      TimeZone.setDefault(zone)
//      dateTimeParser.setTimeZone(zone)
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(date4, Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(cal4.asInstanceOf[Any], Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(date5, Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(cal5.asInstanceOf[Any], Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(date6, Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(cal6.asInstanceOf[Any], Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(date7, Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.round(cal7.asInstanceOf[Any], Calendar.DATE), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 01:00:00.000"), DateUtils.round(date4, Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 01:00:00.000"), DateUtils.round(cal4.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.round(date5, Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.round(cal5.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.round(date6, Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.round(cal6.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 04:00:00.000"), DateUtils.round(date7, Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 04:00:00.000"), DateUtils.round(cal7.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "round MET date across DST change-over")
//    } finally {
//      TimeZone.setDefault(defaultZone)
//      dateTimeParser.setTimeZone(defaultZone)
//    }
//  }
//
//  /**
//    * Tests the Changes Made by LANG-346 to the DateUtils.modify() private method invoked
//    * by DateUtils.round().
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testRoundLang346() = {
//    val testCalendar = Calendar.getInstance
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 50)
//    var date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:09:00.000"), DateUtils.round(date, Calendar.MINUTE), "Minute Round Up Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 20)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:08:00.000"), DateUtils.round(date, Calendar.MINUTE), "Minute No Round Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 50)
//    testCalendar.set(Calendar.MILLISECOND, 600)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:08:51.000"), DateUtils.round(date, Calendar.SECOND), "Second Round Up with 600 Milli Seconds Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 50)
//    testCalendar.set(Calendar.MILLISECOND, 200)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:08:50.000"), DateUtils.round(date, Calendar.SECOND), "Second Round Down with 200 Milli Seconds Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 20)
//    testCalendar.set(Calendar.MILLISECOND, 600)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:08:21.000"), DateUtils.round(date, Calendar.SECOND), "Second Round Up with 200 Milli Seconds Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 20)
//    testCalendar.set(Calendar.MILLISECOND, 200)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:08:20.000"), DateUtils.round(date, Calendar.SECOND), "Second Round Down with 200 Milli Seconds Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 8, 50)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 08:00:00.000"), DateUtils.round(date, Calendar.HOUR), "Hour Round Down Failed")
//    testCalendar.set(2007, Calendar.JULY, 2, 8, 31, 50)
//    date = testCalendar.getTime
//    assertEquals(dateTimeParser.parse("July 2, 2007 09:00:00.000"), DateUtils.round(date, Calendar.HOUR), "Hour Round Up Failed")
//  }
//
//  /**
//    * Tests various values with the trunc method
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testTruncate() = { // tests public static Date truncate(Date date, int field)
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.truncate(date1, Calendar.YEAR), "truncate year-1 failed")
//    assertEquals(dateParser.parse("January 1, 2001"), DateUtils.truncate(date2, Calendar.YEAR), "truncate year-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.truncate(date1, Calendar.MONTH), "truncate month-1 failed")
//    assertEquals(dateParser.parse("November 1, 2001"), DateUtils.truncate(date2, Calendar.MONTH), "truncate month-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.truncate(date1, DateUtils.SEMI_MONTH), "truncate semimonth-1 failed")
//    assertEquals(dateParser.parse("November 16, 2001"), DateUtils.truncate(date2, DateUtils.SEMI_MONTH), "truncate semimonth-2 failed")
//    assertEquals(dateParser.parse("February 12, 2002"), DateUtils.truncate(date1, Calendar.DATE), "truncate date-1 failed")
//    assertEquals(dateParser.parse("November 18, 2001"), DateUtils.truncate(date2, Calendar.DATE), "truncate date-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:00:00.000"), DateUtils.truncate(date1, Calendar.HOUR), "truncate hour-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:00:00.000"), DateUtils.truncate(date2, Calendar.HOUR), "truncate hour-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:00.000"), DateUtils.truncate(date1, Calendar.MINUTE), "truncate minute-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:00.000"), DateUtils.truncate(date2, Calendar.MINUTE), "truncate minute-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:56.000"), DateUtils.truncate(date1, Calendar.SECOND), "truncate second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:11.000"), DateUtils.truncate(date2, Calendar.SECOND), "truncate second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.truncate(dateAmPm1, Calendar.AM_PM), "truncate ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.truncate(dateAmPm2, Calendar.AM_PM), "truncate ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.truncate(dateAmPm3, Calendar.AM_PM), "truncate ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.truncate(dateAmPm4, Calendar.AM_PM), "truncate ampm-4 failed")
//    // tests public static Date truncate(Object date, int field)
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.truncate(date1.asInstanceOf[Any], Calendar.YEAR), "truncate year-1 failed")
//    assertEquals(dateParser.parse("January 1, 2001"), DateUtils.truncate(date2.asInstanceOf[Any], Calendar.YEAR), "truncate year-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.truncate(date1.asInstanceOf[Any], Calendar.MONTH), "truncate month-1 failed")
//    assertEquals(dateParser.parse("November 1, 2001"), DateUtils.truncate(date2.asInstanceOf[Any], Calendar.MONTH), "truncate month-2 failed")
//    assertEquals(dateParser.parse("February 1, 2002"), DateUtils.truncate(date1.asInstanceOf[Any], DateUtils.SEMI_MONTH), "truncate semimonth-1 failed")
//    assertEquals(dateParser.parse("November 16, 2001"), DateUtils.truncate(date2.asInstanceOf[Any], DateUtils.SEMI_MONTH), "truncate semimonth-2 failed")
//    assertEquals(dateParser.parse("February 12, 2002"), DateUtils.truncate(date1.asInstanceOf[Any], Calendar.DATE), "truncate date-1 failed")
//    assertEquals(dateParser.parse("November 18, 2001"), DateUtils.truncate(date2.asInstanceOf[Any], Calendar.DATE), "truncate date-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:00:00.000"), DateUtils.truncate(date1.asInstanceOf[Any], Calendar.HOUR), "truncate hour-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:00:00.000"), DateUtils.truncate(date2.asInstanceOf[Any], Calendar.HOUR), "truncate hour-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:00.000"), DateUtils.truncate(date1.asInstanceOf[Any], Calendar.MINUTE), "truncate minute-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:00.000"), DateUtils.truncate(date2.asInstanceOf[Any], Calendar.MINUTE), "truncate minute-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:56.000"), DateUtils.truncate(date1.asInstanceOf[Any], Calendar.SECOND), "truncate second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:11.000"), DateUtils.truncate(date2.asInstanceOf[Any], Calendar.SECOND), "truncate second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.truncate(dateAmPm1.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.truncate(dateAmPm2.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.truncate(dateAmPm3.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.truncate(dateAmPm4.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-4 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:56.000"), DateUtils.truncate(cal1.asInstanceOf[Any], Calendar.SECOND), "truncate calendar second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:11.000"), DateUtils.truncate(cal2.asInstanceOf[Any], Calendar.SECOND), "truncate calendar second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.truncate(calAmPm1.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 00:00:00.000"), DateUtils.truncate(calAmPm2.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.truncate(calAmPm3.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.truncate(calAmPm4.asInstanceOf[Any], Calendar.AM_PM), "truncate ampm-4 failed")
//    assertThrows(classOf[NullPointerException], () => DateUtils.truncate(null.asInstanceOf[Date], Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.truncate(null.asInstanceOf[Calendar], Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.truncate(null.asInstanceOf[Any], Calendar.SECOND))
//    assertThrows(classOf[ClassCastException], () => DateUtils.truncate("", Calendar.SECOND))
//    // Fix for https://issues.apache.org/bugzilla/show_bug.cgi?id=25560
//    // Test truncate across beginning of daylight saving time
//    try {
//      TimeZone.setDefault(zone)
//      dateTimeParser.setTimeZone(zone)
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.truncate(date3, Calendar.DATE), "truncate MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 00:00:00.000"), DateUtils.truncate(cal3.asInstanceOf[Any], Calendar.DATE), "truncate MET date across DST change-over")
//      // Test truncate across end of daylight saving time
//      assertEquals(dateTimeParser.parse("October 26, 2003 00:00:00.000"), DateUtils.truncate(date8, Calendar.DATE), "truncate MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("October 26, 2003 00:00:00.000"), DateUtils.truncate(cal8.asInstanceOf[Any], Calendar.DATE), "truncate MET date across DST change-over")
//    } finally {
//      TimeZone.setDefault(defaultZone)
//      dateTimeParser.setTimeZone(defaultZone)
//    }
//    // Bug 31395, large dates
//    val endOfTime = new Date(Long.MAX_VALUE) // fyi: Sun Aug 17 07:12:55 CET 292278994 -- 807 millis
//    val endCal = new GregorianCalendar
//    endCal.setTime(endOfTime)
//    assertThrows(classOf[ArithmeticException], () => DateUtils.truncate(endCal, Calendar.DATE))
//    endCal.set(Calendar.YEAR, 280000001)
//    assertThrows(classOf[ArithmeticException], () => DateUtils.truncate(endCal, Calendar.DATE))
//    endCal.set(Calendar.YEAR, 280000000)
//    val cal = DateUtils.truncate(endCal, Calendar.DATE)
//    assertEquals(0, cal.get(Calendar.HOUR))
//  }
//
//  /**
//    * Tests for LANG-59
//    *
//    * see https://issues.apache.org/jira/browse/LANG-59
//    */
//  @Test def testTruncateLang59() = try { // Set TimeZone to Mountain Time
//    val denverZone = TimeZone.getTimeZone("America/Denver")
//    TimeZone.setDefault(denverZone)
//    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS XXX")
//    format.setTimeZone(denverZone)
//    val oct31_01MDT = new Date(1099206000000L)
//    val oct31MDT = new Date(oct31_01MDT.getTime - 3600000L) // - 1 hour
//    val oct31_01_02MDT = new Date(oct31_01MDT.getTime + 120000L) // + 2 minutes
//    val oct31_01_02_03MDT = new Date(oct31_01_02MDT.getTime + 3000L) // + 3 seconds
//    val oct31_01_02_03_04MDT = new Date(oct31_01_02_03MDT.getTime + 4L) // + 4 milliseconds
//    assertEquals("2004-10-31 00:00:00.000 -06:00", format.format(oct31MDT), "Check 00:00:00.000")
//    assertEquals("2004-10-31 01:00:00.000 -06:00", format.format(oct31_01MDT), "Check 01:00:00.000")
//    assertEquals("2004-10-31 01:02:00.000 -06:00", format.format(oct31_01_02MDT), "Check 01:02:00.000")
//    assertEquals("2004-10-31 01:02:03.000 -06:00", format.format(oct31_01_02_03MDT), "Check 01:02:03.000")
//    assertEquals("2004-10-31 01:02:03.004 -06:00", format.format(oct31_01_02_03_04MDT), "Check 01:02:03.004")
//    // ------- Demonstrate Problem -------
//    val gval = Calendar.getInstance
//    gval.setTime(new Date(oct31_01MDT.getTime))
//    gval.set(Calendar.MINUTE, gval.get(Calendar.MINUTE)) // set minutes to the same value
//    assertEquals(gval.getTime.getTime, oct31_01MDT.getTime + 3600000L, "Demonstrate Problem")
//    // ---------- Test Truncate ----------
//    assertEquals(oct31_01_02_03_04MDT, DateUtils.truncate(oct31_01_02_03_04MDT, Calendar.MILLISECOND), "Truncate Calendar.MILLISECOND")
//    assertEquals(oct31_01_02_03MDT, DateUtils.truncate(oct31_01_02_03_04MDT, Calendar.SECOND), "Truncate Calendar.SECOND")
//    assertEquals(oct31_01_02MDT, DateUtils.truncate(oct31_01_02_03_04MDT, Calendar.MINUTE), "Truncate Calendar.MINUTE")
//    assertEquals(oct31_01MDT, DateUtils.truncate(oct31_01_02_03_04MDT, Calendar.HOUR_OF_DAY), "Truncate Calendar.HOUR_OF_DAY")
//    assertEquals(oct31_01MDT, DateUtils.truncate(oct31_01_02_03_04MDT, Calendar.HOUR), "Truncate Calendar.HOUR")
//    assertEquals(oct31MDT, DateUtils.truncate(oct31_01_02_03_04MDT, Calendar.DATE), "Truncate Calendar.DATE")
//    // ---------- Test Round (down) ----------
//    assertEquals(oct31_01_02_03_04MDT, DateUtils.round(oct31_01_02_03_04MDT, Calendar.MILLISECOND), "Round Calendar.MILLISECOND")
//    assertEquals(oct31_01_02_03MDT, DateUtils.round(oct31_01_02_03_04MDT, Calendar.SECOND), "Round Calendar.SECOND")
//    assertEquals(oct31_01_02MDT, DateUtils.round(oct31_01_02_03_04MDT, Calendar.MINUTE), "Round Calendar.MINUTE")
//    assertEquals(oct31_01MDT, DateUtils.round(oct31_01_02_03_04MDT, Calendar.HOUR_OF_DAY), "Round Calendar.HOUR_OF_DAY")
//    assertEquals(oct31_01MDT, DateUtils.round(oct31_01_02_03_04MDT, Calendar.HOUR), "Round Calendar.HOUR")
//    assertEquals(oct31MDT, DateUtils.round(oct31_01_02_03_04MDT, Calendar.DATE), "Round Calendar.DATE")
//  } finally {
//    // restore default time zone
//    TimeZone.setDefault(defaultZone)
//  }
//
//  // https://issues.apache.org/jira/browse/LANG-530
//  @SuppressWarnings(Array("deprecation"))
//  @Test
//  @throws[ParseException]
//  def testLang530() = {
//    val d = new Date
//    val isoDateStr = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(d)
//    val d2 = DateUtils.parseDate(isoDateStr, DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern)
//    // the format loses milliseconds so have to reintroduce them
//    assertEquals(d.getTime, d2.getTime + d.getTime % 1000, "Date not equal to itself ISO formatted and parsed")
//  }
//
//  /**
//    * Tests various values with the ceiling method
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testCeil() = { // test javadoc
//    assertEquals(dateTimeParser.parse("March 28, 2002 14:00:00.000"), DateUtils.ceiling(dateTimeParser.parse("March 28, 2002 13:45:01.231"), Calendar.HOUR), "ceiling javadoc-1 failed")
//    assertEquals(dateTimeParser.parse("April 1, 2002 00:00:00.000"), DateUtils.ceiling(dateTimeParser.parse("March 28, 2002 13:45:01.231"), Calendar.MONTH), "ceiling javadoc-2 failed")
//    // tests public static Date ceiling(Date date, int field)
//    assertEquals(dateParser.parse("January 1, 2003"), DateUtils.ceiling(date1, Calendar.YEAR), "ceiling year-1 failed")
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.ceiling(date2, Calendar.YEAR), "ceiling year-2 failed")
//    assertEquals(dateParser.parse("March 1, 2002"), DateUtils.ceiling(date1, Calendar.MONTH), "ceiling month-1 failed")
//    assertEquals(dateParser.parse("December 1, 2001"), DateUtils.ceiling(date2, Calendar.MONTH), "ceiling month-2 failed")
//    assertEquals(dateParser.parse("February 16, 2002"), DateUtils.ceiling(date1, DateUtils.SEMI_MONTH), "ceiling semimonth-1 failed")
//    assertEquals(dateParser.parse("December 1, 2001"), DateUtils.ceiling(date2, DateUtils.SEMI_MONTH), "ceiling semimonth-2 failed")
//    assertEquals(dateParser.parse("February 13, 2002"), DateUtils.ceiling(date1, Calendar.DATE), "ceiling date-1 failed")
//    assertEquals(dateParser.parse("November 19, 2001"), DateUtils.ceiling(date2, Calendar.DATE), "ceiling date-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 13:00:00.000"), DateUtils.ceiling(date1, Calendar.HOUR), "ceiling hour-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 2:00:00.000"), DateUtils.ceiling(date2, Calendar.HOUR), "ceiling hour-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:35:00.000"), DateUtils.ceiling(date1, Calendar.MINUTE), "ceiling minute-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:24:00.000"), DateUtils.ceiling(date2, Calendar.MINUTE), "ceiling minute-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:57.000"), DateUtils.ceiling(date1, Calendar.SECOND), "ceiling second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:12.000"), DateUtils.ceiling(date2, Calendar.SECOND), "ceiling second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.ceiling(dateAmPm1, Calendar.AM_PM), "ceiling ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.ceiling(dateAmPm2, Calendar.AM_PM), "ceiling ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.ceiling(dateAmPm3, Calendar.AM_PM), "ceiling ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.ceiling(dateAmPm4, Calendar.AM_PM), "ceiling ampm-4 failed")
//    // tests public static Date ceiling(Object date, int field)
//    assertEquals(dateParser.parse("January 1, 2003"), DateUtils.ceiling(date1.asInstanceOf[Any], Calendar.YEAR), "ceiling year-1 failed")
//    assertEquals(dateParser.parse("January 1, 2002"), DateUtils.ceiling(date2.asInstanceOf[Any], Calendar.YEAR), "ceiling year-2 failed")
//    assertEquals(dateParser.parse("March 1, 2002"), DateUtils.ceiling(date1.asInstanceOf[Any], Calendar.MONTH), "ceiling month-1 failed")
//    assertEquals(dateParser.parse("December 1, 2001"), DateUtils.ceiling(date2.asInstanceOf[Any], Calendar.MONTH), "ceiling month-2 failed")
//    assertEquals(dateParser.parse("February 16, 2002"), DateUtils.ceiling(date1.asInstanceOf[Any], DateUtils.SEMI_MONTH), "ceiling semimonth-1 failed")
//    assertEquals(dateParser.parse("December 1, 2001"), DateUtils.ceiling(date2.asInstanceOf[Any], DateUtils.SEMI_MONTH), "ceiling semimonth-2 failed")
//    assertEquals(dateParser.parse("February 13, 2002"), DateUtils.ceiling(date1.asInstanceOf[Any], Calendar.DATE), "ceiling date-1 failed")
//    assertEquals(dateParser.parse("November 19, 2001"), DateUtils.ceiling(date2.asInstanceOf[Any], Calendar.DATE), "ceiling date-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 13:00:00.000"), DateUtils.ceiling(date1.asInstanceOf[Any], Calendar.HOUR), "ceiling hour-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 2:00:00.000"), DateUtils.ceiling(date2.asInstanceOf[Any], Calendar.HOUR), "ceiling hour-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:35:00.000"), DateUtils.ceiling(date1.asInstanceOf[Any], Calendar.MINUTE), "ceiling minute-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:24:00.000"), DateUtils.ceiling(date2.asInstanceOf[Any], Calendar.MINUTE), "ceiling minute-2 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:57.000"), DateUtils.ceiling(date1.asInstanceOf[Any], Calendar.SECOND), "ceiling second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:12.000"), DateUtils.ceiling(date2.asInstanceOf[Any], Calendar.SECOND), "ceiling second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.ceiling(dateAmPm1.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.ceiling(dateAmPm2.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.ceiling(dateAmPm3.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.ceiling(dateAmPm4.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-4 failed")
//    assertEquals(dateTimeParser.parse("February 12, 2002 12:34:57.000"), DateUtils.ceiling(cal1.asInstanceOf[Any], Calendar.SECOND), "ceiling calendar second-1 failed")
//    assertEquals(dateTimeParser.parse("November 18, 2001 1:23:12.000"), DateUtils.ceiling(cal2.asInstanceOf[Any], Calendar.SECOND), "ceiling calendar second-2 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.ceiling(calAmPm1.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-1 failed")
//    assertEquals(dateTimeParser.parse("February 3, 2002 12:00:00.000"), DateUtils.ceiling(calAmPm2.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-2 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.ceiling(calAmPm3.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-3 failed")
//    assertEquals(dateTimeParser.parse("February 4, 2002 00:00:00.000"), DateUtils.ceiling(calAmPm4.asInstanceOf[Any], Calendar.AM_PM), "ceiling ampm-4 failed")
//    assertThrows(classOf[NullPointerException], () => DateUtils.ceiling(null.asInstanceOf[Date], Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.ceiling(null.asInstanceOf[Calendar], Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.ceiling(null.asInstanceOf[Any], Calendar.SECOND))
//    assertThrows(classOf[ClassCastException], () => DateUtils.ceiling("", Calendar.SECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.ceiling(date1, -9999))
//    // Test ceiling across the beginning of daylight saving time
//    try {
//      TimeZone.setDefault(zone)
//      dateTimeParser.setTimeZone(zone)
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(date4, Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(cal4.asInstanceOf[Any], Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(date5, Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(cal5.asInstanceOf[Any], Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(date6, Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(cal6.asInstanceOf[Any], Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(date7, Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 31, 2003 00:00:00.000"), DateUtils.ceiling(cal7.asInstanceOf[Any], Calendar.DATE), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.ceiling(date4, Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.ceiling(cal4.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.ceiling(date5, Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 03:00:00.000"), DateUtils.ceiling(cal5.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 04:00:00.000"), DateUtils.ceiling(date6, Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 04:00:00.000"), DateUtils.ceiling(cal6.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 04:00:00.000"), DateUtils.ceiling(date7, Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//      assertEquals(dateTimeParser.parse("March 30, 2003 04:00:00.000"), DateUtils.ceiling(cal7.asInstanceOf[Any], Calendar.HOUR_OF_DAY), "ceiling MET date across DST change-over")
//    } finally {
//      TimeZone.setDefault(defaultZone)
//      dateTimeParser.setTimeZone(defaultZone)
//    }
//    val endOfTime = new Date(Long.MAX_VALUE)
//    val endCal = new GregorianCalendar
//    endCal.setTime(endOfTime)
//    assertThrows(classOf[ArithmeticException], () => DateUtils.ceiling(endCal, Calendar.DATE))
//    endCal.set(Calendar.YEAR, 280000001)
//    assertThrows(classOf[ArithmeticException], () => DateUtils.ceiling(endCal, Calendar.DATE))
//    endCal.set(Calendar.YEAR, 280000000)
//    val cal = DateUtils.ceiling(endCal, Calendar.DATE)
//    assertEquals(0, cal.get(Calendar.HOUR))
//  }
//
//  /**
//    * Tests the iterator exceptions
//    */
//  @Test def testIteratorEx() = {
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.iterator(Calendar.getInstance, -9999))
//    assertThrows(classOf[NullPointerException], () => DateUtils.iterator(null.asInstanceOf[Date], DateUtils.RANGE_WEEK_CENTER))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.iterator(null.asInstanceOf[Calendar], DateUtils.RANGE_WEEK_CENTER))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.iterator(null.asInstanceOf[Any], DateUtils.RANGE_WEEK_CENTER))
//    assertThrows(classOf[ClassCastException], () => DateUtils.iterator("", DateUtils.RANGE_WEEK_CENTER))
//  }
//
//  /**
//    * Tests the calendar iterator for week ranges
//    */
//  @Test def testWeekIterator() = {
//    val now = Calendar.getInstance
//    for (i <- 0 until 7) {
//      val today = DateUtils.truncate(now, Calendar.DATE)
//      val sunday = DateUtils.truncate(now, Calendar.DATE)
//      sunday.add(Calendar.DATE, 1 - sunday.get(Calendar.DAY_OF_WEEK))
//      val monday = DateUtils.truncate(now, Calendar.DATE)
//      if (monday.get(Calendar.DAY_OF_WEEK) == 1) { //This is sunday... roll back 6 days
//        monday.add(Calendar.DATE, -6)
//      }
//      else monday.add(Calendar.DATE, 2 - monday.get(Calendar.DAY_OF_WEEK))
//      val centered = DateUtils.truncate(now, Calendar.DATE)
//      centered.add(Calendar.DATE, -3)
//      var it = DateUtils.iterator(now, DateUtils.RANGE_WEEK_SUNDAY)
//      DateUtilsTest.assertWeekIterator(it, sunday)
//      it = DateUtils.iterator(now, DateUtils.RANGE_WEEK_MONDAY)
//      DateUtilsTest.assertWeekIterator(it, monday)
//      it = DateUtils.iterator(now, DateUtils.RANGE_WEEK_RELATIVE)
//      DateUtilsTest.assertWeekIterator(it, today)
//      it = DateUtils.iterator(now, DateUtils.RANGE_WEEK_CENTER)
//      DateUtilsTest.assertWeekIterator(it, centered)
//      it = DateUtils.iterator(now.asInstanceOf[Any], DateUtils.RANGE_WEEK_CENTER)
//      DateUtilsTest.assertWeekIterator(it, centered)
//      val it2 = DateUtils.iterator(now.getTime.asInstanceOf[Any], DateUtils.RANGE_WEEK_CENTER)
//      DateUtilsTest.assertWeekIterator(it2, centered)
//      assertThrows(classOf[NoSuchElementException], it2.next)
//      val it3 = DateUtils.iterator(now, DateUtils.RANGE_WEEK_CENTER)
//      it3.next
//      assertThrows(classOf[UnsupportedOperationException], it3.remove)
//      now.add(Calendar.DATE, 1)
//    }
//  }
//
//  /**
//    * Tests the calendar iterator for month-based ranges
//    *
//    * @throws java.lang.Exception so we don't have to catch it
//    */
//  @Test
//  @throws[Exception]
//  def testMonthIterator() = {
//    var it = DateUtils.iterator(date1, DateUtils.RANGE_MONTH_SUNDAY)
//    DateUtilsTest.assertWeekIterator(it, dateParser.parse("January 27, 2002"), dateParser.parse("March 2, 2002"))
//    it = DateUtils.iterator(date1, DateUtils.RANGE_MONTH_MONDAY)
//    DateUtilsTest.assertWeekIterator(it, dateParser.parse("January 28, 2002"), dateParser.parse("March 3, 2002"))
//    it = DateUtils.iterator(date2, DateUtils.RANGE_MONTH_SUNDAY)
//    DateUtilsTest.assertWeekIterator(it, dateParser.parse("October 28, 2001"), dateParser.parse("December 1, 2001"))
//    it = DateUtils.iterator(date2, DateUtils.RANGE_MONTH_MONDAY)
//    DateUtilsTest.assertWeekIterator(it, dateParser.parse("October 29, 2001"), dateParser.parse("December 2, 2001"))
//  }
//
//  @DefaultLocale(language = "en")
//  @Test
//  @throws[ParseException]
//  def testLANG799_EN_OK() = {
//    DateUtils.parseDate("Wed, 09 Apr 2008 23:55:38 GMT", "EEE, dd MMM yyyy HH:mm:ss zzz")
//    DateUtils.parseDateStrictly("Wed, 09 Apr 2008 23:55:38 GMT", "EEE, dd MMM yyyy HH:mm:ss zzz")
//  }
//
//  // Parse German date with English Locale
//  @DefaultLocale(language = "en")
//  @Test def testLANG799_EN_FAIL() = assertThrows(classOf[ParseException], () => DateUtils.parseDate("Mi, 09 Apr 2008 23:55:38 GMT", "EEE, dd MMM yyyy HH:mm:ss zzz"))
//
//  @DefaultLocale(language = "de")
//  @Test
//  @throws[ParseException]
//  def testLANG799_DE_OK() = {
//    DateUtils.parseDate("Mi, 09 Apr 2008 23:55:38 GMT", "EEE, dd MMM yyyy HH:mm:ss zzz")
//    DateUtils.parseDateStrictly("Mi, 09 Apr 2008 23:55:38 GMT", "EEE, dd MMM yyyy HH:mm:ss zzz")
//  }
//
//  // Parse English date with German Locale
//  @DefaultLocale(language = "de")
//  @Test def testLANG799_DE_FAIL() = assertThrows(classOf[ParseException], () => DateUtils.parseDate("Wed, 09 Apr 2008 23:55:38 GMT", "EEE, dd MMM yyyy HH:mm:ss zzz"))
//
//  // Parse German date with English Locale, specifying German Locale override
//  @DefaultLocale(language = "en")
//  @Test
//  @throws[ParseException]
//  def testLANG799_EN_WITH_DE_LOCALE() = DateUtils.parseDate("Mi, 09 Apr 2008 23:55:38 GMT", Locale.GERMAN, "EEE, dd MMM yyyy HH:mm:ss zzz")
//
//  @Test
//  @throws[ParseException]
//  def testLANG799() = DateUtils.parseDateStrictly("09 abril 2008 23:55:38 GMT", new Locale("es"), "dd MMM yyyy HH:mm:ss zzz")
//}
