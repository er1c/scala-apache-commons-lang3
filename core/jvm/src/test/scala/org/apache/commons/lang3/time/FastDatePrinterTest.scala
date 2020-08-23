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
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.io.Serializable
//import java.text.FieldPosition
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.GregorianCalendar
//import java.util.Locale
//import java.util.TimeZone
//import org.apache.commons.lang3.SerializationUtils
//import org.junit.Test
//import org.junitpioneer.jupiter.DefaultLocale
//import org.junitpioneer.jupiter.DefaultTimeZone
//
//
///**
//  * Unit tests {@link org.apache.commons.lang3.time.FastDatePrinter}.
//  *
//  * @since 3.0
//  */
//object FastDatePrinterTest {
//  private val YYYY_MM_DD = "yyyy/MM/dd"
//  private val NEW_YORK = TimeZone.getTimeZone("America/New_York")
//  private val GMT = TimeZone.getTimeZone("GMT")
//  private val INDIA = TimeZone.getTimeZone("Asia/Calcutta")
//  private val SWEDEN = new Locale("sv", "SE")
//
//  private def initializeCalendar(tz: TimeZone) = {
//    val cal = Calendar.getInstance(tz)
//    cal.set(Calendar.YEAR, 2001)
//    cal.set(Calendar.MONTH, 1) // not daylight savings
//    cal.set(Calendar.DAY_OF_MONTH, 4)
//    cal.set(Calendar.HOUR_OF_DAY, 12)
//    cal.set(Calendar.MINUTE, 8)
//    cal.set(Calendar.SECOND, 56)
//    cal.set(Calendar.MILLISECOND, 235)
//    cal
//  }
//
//  private object Expected1806 extends Enumeration {
//    type Expected1806 = Value
//    val India, Greenwich, NewYork = Value
//
//    def this(zone: TimeZone, one: String, two: String, three: String) {
//      this()
//      this.zone = zone
//      this.one = one
//      this.two = two
//      this.three = three
//    }
//
//    private[time
//    ]
//    val zone = nullprivate[time]
//    val one: String
//    = nullprivate[time]
//    val two: String
//    = nullprivate[time]
//    val three: String
//    = null
//  }
//
//}
//
//class FastDatePrinterTest {
//  private[time] def getInstance(format: String) = getInstance(format, TimeZone.getDefault, Locale.getDefault)
//
//  private def getDateInstance(dateStyle: Int, locale: Locale) = getInstance(FormatCache.getPatternForStyle(Integer.valueOf(dateStyle), null, locale), TimeZone.getDefault, Locale.getDefault)
//
//  private def getInstance(format: String, locale: Locale) = getInstance(format, TimeZone.getDefault, locale)
//
//  private def getInstance(format: String, timeZone: TimeZone) = getInstance(format, timeZone, Locale.getDefault)
//
//  /**
//    * Override this method in derived tests to change the construction of instances
//    *
//    * @param format   the format string to use
//    * @param timeZone the time zone to use
//    * @param locale   the locale to use
//    * @return the DatePrinter to use for testing
//    */
//  protected def getInstance(format: String, timeZone: TimeZone, locale: Locale) = new Nothing(format, timeZone, locale)
//
//  @DefaultLocale(language = "en", country = "US")
//  @DefaultTimeZone("America/New_York")
//  @Test def testFormat() = {
//    val cal1 = new GregorianCalendar(2003, 0, 10, 15, 33, 20)
//    val cal2 = new GregorianCalendar(2003, 6, 10, 9, 0, 0)
//    val date1 = cal1.getTime
//    val date2 = cal2.getTime
//    val millis1 = date1.getTime
//    val millis2 = date2.getTime
//    var fdf = getInstance("yyyy-MM-dd'T'HH:mm:ss")
//    var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//    assertEquals(sdf.format(date1), fdf.format(date1))
//    assertEquals("2003-01-10T15:33:20", fdf.format(date1))
//    assertEquals("2003-01-10T15:33:20", fdf.format(cal1))
//    assertEquals("2003-01-10T15:33:20", fdf.format(millis1))
//    assertEquals("2003-07-10T09:00:00", fdf.format(date2))
//    assertEquals("2003-07-10T09:00:00", fdf.format(cal2))
//    assertEquals("2003-07-10T09:00:00", fdf.format(millis2))
//    fdf = getInstance("Z")
//    assertEquals("-0500", fdf.format(date1))
//    assertEquals("-0500", fdf.format(cal1))
//    assertEquals("-0500", fdf.format(millis1))
//    assertEquals("-0400", fdf.format(date2))
//    assertEquals("-0400", fdf.format(cal2))
//    assertEquals("-0400", fdf.format(millis2))
//    fdf = getInstance("ZZ")
//    assertEquals("-05:00", fdf.format(date1))
//    assertEquals("-05:00", fdf.format(cal1))
//    assertEquals("-05:00", fdf.format(millis1))
//    assertEquals("-04:00", fdf.format(date2))
//    assertEquals("-04:00", fdf.format(cal2))
//    assertEquals("-04:00", fdf.format(millis2))
//    val pattern = "GGGG GGG GG G yyyy yyy yy y MMMM MMM MM M" + " dddd ddd dd d DDDD DDD DD D EEEE EEE EE E aaaa aaa aa a zzzz zzz zz z"
//    fdf = getInstance(pattern)
//    sdf = new SimpleDateFormat(pattern)
//    // SDF bug fix starting with Java 7
//    assertEquals(sdf.format(date1).replaceAll("2003 03 03 03", "2003 2003 03 2003"), fdf.format(date1))
//    assertEquals(sdf.format(date2).replaceAll("2003 03 03 03", "2003 2003 03 2003"), fdf.format(date2))
//  }
//
//  /**
//    * Test case for {@link FastDateParser# FastDateParser ( String, TimeZone, Locale)}.
//    */
//  @Test def testShortDateStyleWithLocales() = {
//    val usLocale = Locale.US
//    val swedishLocale = new Locale("sv", "SE")
//    val cal = Calendar.getInstance
//    cal.set(2004, Calendar.FEBRUARY, 3)
//    var fdf = getDateInstance(FastDateFormat.SHORT, usLocale)
//    assertEquals("2/3/04", fdf.format(cal))
//    fdf = getDateInstance(FastDateFormat.SHORT, swedishLocale)
//    assertEquals("2004-02-03", fdf.format(cal))
//  }
//
//  /**
//    * Tests that pre-1000AD years get padded with yyyy
//    */
//  @Test def testLowYearPadding() = {
//    val cal = Calendar.getInstance
//    val format = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    cal.set(1, Calendar.JANUARY, 1)
//    assertEquals("0001/01/01", format.format(cal))
//    cal.set(10, Calendar.JANUARY, 1)
//    assertEquals("0010/01/01", format.format(cal))
//    cal.set(100, Calendar.JANUARY, 1)
//    assertEquals("0100/01/01", format.format(cal))
//    cal.set(999, Calendar.JANUARY, 1)
//    assertEquals("0999/01/01", format.format(cal))
//  }
//
//  /**
//    * Show Bug #39410 is solved
//    */
//  @Test def testMilleniumBug() = {
//    val cal = Calendar.getInstance
//    val format = getInstance("dd.MM.yyyy")
//    cal.set(1000, Calendar.JANUARY, 1)
//    assertEquals("01.01.1000", format.format(cal))
//  }
//
//  /**
//    * testLowYearPadding showed that the date was buggy
//    * This test confirms it, getting 366 back as a date
//    */
//  @Test def testSimpleDate() = {
//    val cal = Calendar.getInstance
//    val format = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    cal.set(2004, Calendar.DECEMBER, 31)
//    assertEquals("2004/12/31", format.format(cal))
//    cal.set(999, Calendar.DECEMBER, 31)
//    assertEquals("0999/12/31", format.format(cal))
//    cal.set(1, Calendar.MARCH, 2)
//    assertEquals("0001/03/02", format.format(cal))
//  }
//
//  @Test def testLang303() = {
//    val cal = Calendar.getInstance
//    cal.set(2004, Calendar.DECEMBER, 31)
//    var format = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    val output = format.format(cal)
//    format = SerializationUtils.deserialize(SerializationUtils.serialize(format.asInstanceOf[Serializable]))
//    assertEquals(output, format.format(cal))
//  }
//
//  @Test def testLang538() = { // more commonly constructed with: cal = new GregorianCalendar(2009, 9, 16, 8, 42, 16)
//    // for the unit test to work in any time zone, constructing with GMT-8 rather than default locale time zone
//    val cal = new GregorianCalendar(TimeZone.getTimeZone("GMT-8"))
//    cal.clear()
//    cal.set(2009, Calendar.OCTOBER, 16, 8, 42, 16)
//    val format = getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone("GMT"))
//    assertEquals("2009-10-16T16:42:16.000Z", format.format(cal.getTime), "dateTime")
//    assertEquals("2009-10-16T16:42:16.000Z", format.format(cal), "dateTime")
//  }
//
//  @Test def testLang645() = {
//    val locale = new Locale("sv", "SE")
//    val cal = Calendar.getInstance
//    cal.set(2010, Calendar.JANUARY, 1, 12, 0, 0)
//    val d = cal.getTime
//    val fdf = getInstance("EEEE', week 'ww", locale)
//    assertEquals("fredag, week 53", fdf.format(d))
//  }
//
//  @Test def testEquals() = {
//    val printer1 = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    val printer2 = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    assertEquals(printer1, printer2)
//    assertEquals(printer1.hashCode, printer2.hashCode)
//    assertNotEquals(printer1, new Any)
//  }
//
//  @Test def testToStringContainsName() = {
//    val printer = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    assertTrue(printer.toString.startsWith("FastDate"))
//  }
//
//  @Test def testPatternMatches() = {
//    val printer = getInstance(FastDatePrinterTest.YYYY_MM_DD)
//    assertEquals(FastDatePrinterTest.YYYY_MM_DD, printer.getPattern)
//  }
//
//  @Test def testLocaleMatches() = {
//    val printer = getInstance(FastDatePrinterTest.YYYY_MM_DD, FastDatePrinterTest.SWEDEN)
//    assertEquals(FastDatePrinterTest.SWEDEN, printer.getLocale)
//  }
//
//  @Test def testTimeZoneMatches() = {
//    val printer = getInstance(FastDatePrinterTest.YYYY_MM_DD, FastDatePrinterTest.NEW_YORK)
//    assertEquals(FastDatePrinterTest.NEW_YORK, printer.getTimeZone)
//  }
//
//  @DefaultTimeZone("UTC")
//  @Test def testTimeZoneAsZ() = {
//    val c = Calendar.getInstance(FastTimeZone.getGmtTimeZone)
//    val noColonFormat = FastDateFormat.getInstance("Z")
//    assertEquals("+0000", noColonFormat.format(c))
//    val isoFormat = FastDateFormat.getInstance("ZZ")
//    assertEquals("Z", isoFormat.format(c))
//    val colonFormat = FastDateFormat.getInstance("ZZZ")
//    assertEquals("+00:00", colonFormat.format(c))
//  }
//
//  @Test def test1806Argument() = assertThrows(classOf[IllegalArgumentException], () => getInstance("XXXX"))
//
//  @Test def test1806() = for (trial <- FastDatePrinterTest.Expected1806.values) {
//    val cal = FastDatePrinterTest.initializeCalendar(trial.zone)
//    var printer = getInstance("X", trial.zone)
//    assertEquals(trial.one, printer.format(cal))
//    printer = getInstance("XX", trial.zone)
//    assertEquals(trial.two, printer.format(cal))
//    printer = getInstance("XXX", trial.zone)
//    assertEquals(trial.three, printer.format(cal))
//  }
//
//  @Test def testLang1103() = {
//    val cal = Calendar.getInstance(FastDatePrinterTest.SWEDEN)
//    cal.set(Calendar.DAY_OF_MONTH, 2)
//    assertEquals("2", getInstance("d", FastDatePrinterTest.SWEDEN).format(cal))
//    assertEquals("02", getInstance("dd", FastDatePrinterTest.SWEDEN).format(cal))
//    assertEquals("002", getInstance("ddd", FastDatePrinterTest.SWEDEN).format(cal))
//    assertEquals("0002", getInstance("dddd", FastDatePrinterTest.SWEDEN).format(cal))
//    assertEquals("00002", getInstance("ddddd", FastDatePrinterTest.SWEDEN).format(cal))
//  }
//
//  /**
//    * According to LANG-916 (https://issues.apache.org/jira/browse/LANG-916),
//    * the format method did contain a bug: it did not use the TimeZone data.
//    *
//    * This method test that the bug is fixed.
//    */
//  @Test def testLang916() = {
//    val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
//    cal.clear()
//    cal.set(2009, 9, 16, 8, 42, 16)
//    // calendar fast.
//    val value = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss Z", TimeZone.getTimeZone("Europe/Paris")).format(cal)
//    assertEquals("2009-10-16T08:42:16 +0200", value, "calendar")
//    val value = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss Z", TimeZone.getTimeZone("Asia/Kolkata")).format(cal)
//    assertEquals("2009-10-16T12:12:16 +0530", value, "calendar")
//    val value = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss Z", TimeZone.getTimeZone("Europe/London")).format(cal)
//    assertEquals("2009-10-16T07:42:16 +0100", value, "calendar")
//  }
//
//  @Test def testHourFormats() = {
//    val calendar = Calendar.getInstance
//    calendar.clear()
//    val printer = getInstance("K k H h")
//    calendar.set(Calendar.HOUR_OF_DAY, 0)
//    assertEquals("0 24 0 12", printer.format(calendar))
//    calendar.set(Calendar.HOUR_OF_DAY, 12)
//    assertEquals("0 12 12 12", printer.format(calendar))
//    calendar.set(Calendar.HOUR_OF_DAY, 23)
//    assertEquals("11 23 23 11", printer.format(calendar))
//  }
//
//  @SuppressWarnings(Array("deprecation"))
//  @Test def testStringBufferOptions() = {
//    val format = getInstance("yyyy-MM-dd HH:mm:ss.SSS Z", TimeZone.getTimeZone("GMT"))
//    val calendar = Calendar.getInstance
//    val sb = new StringBuffer
//    val expected = format.format(calendar, sb, new FieldPosition(0)).toString
//    sb.setLength(0)
//    assertEquals(expected, format.format(calendar, sb).toString)
//    sb.setLength(0)
//    val date = calendar.getTime
//    assertEquals(expected, format.format(date, sb, new FieldPosition(0)).toString)
//    sb.setLength(0)
//    assertEquals(expected, format.format(date, sb).toString)
//    sb.setLength(0)
//    val epoch = date.getTime
//    assertEquals(expected, format.format(epoch, sb, new FieldPosition(0)).toString)
//    sb.setLength(0)
//    assertEquals(expected, format.format(epoch, sb).toString)
//  }
//
//  @Test def testAppendableOptions() = {
//    val format = getInstance("yyyy-MM-dd HH:mm:ss.SSS Z", TimeZone.getTimeZone("GMT"))
//    val calendar = Calendar.getInstance
//    val sb = new StringBuilder
//    val expected = format.format(calendar, sb).toString
//    sb.setLength(0)
//    val date = calendar.getTime
//    assertEquals(expected, format.format(date, sb).toString)
//    sb.setLength(0)
//    val epoch = date.getTime
//    assertEquals(expected, format.format(epoch, sb).toString)
//  }
//
//  @Test def testDayNumberOfWeek() = {
//    val printer = getInstance("u")
//    val calendar = Calendar.getInstance
//    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
//    assertEquals("1", printer.format(calendar.getTime))
//    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
//    assertEquals("6", printer.format(calendar.getTime))
//    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
//    assertEquals("7", printer.format(calendar.getTime))
//  }
//}
