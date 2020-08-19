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
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import org.junit.Assert.fail
//import java.io.Serializable
//import java.text.ParseException
//import java.text.ParsePosition
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.GregorianCalendar
//import java.util
//import java.util.Locale
//import java.util.TimeZone
//import org.apache.commons.lang3.LocaleUtils
//import org.apache.commons.lang3.SerializationUtils
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link org.apache.commons.lang3.time.FastDateParser}.
//  *
//  * @since 3.2
//  */
//object FastDateParserTest {
//  private val SHORT_FORMAT_NOERA = "y/M/d/h/a/m/s/E"
//  private val LONG_FORMAT_NOERA = "yyyy/MMMM/dddd/hhhh/mmmm/ss/aaaa/EEEE"
//  private val SHORT_FORMAT = "G/" + SHORT_FORMAT_NOERA
//  private val LONG_FORMAT = "GGGG/" + LONG_FORMAT_NOERA
//  private val yMdHmsSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS Z"
//  private val DMY_DOT = "dd.MM.yyyy"
//  private val YMD_SLASH = "yyyy/MM/dd"
//  private val MDY_DASH = "MM-DD-yyyy"
//  private val MDY_SLASH = "MM/DD/yyyy"
//  private val REYKJAVIK = TimeZone.getTimeZone("Atlantic/Reykjavik")
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
//    def this(zone: TimeZone, one: String, two: String, three: String, hasHalfHourOffset: Boolean) {
//      this()
//      this.zone = zone
//      this.one = one
//      this.two = two
//      this.three = three
//      this.offset = if (hasHalfHourOffset) 30 * 60 * 1000
//      else 0
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
//    = nullprivate[time]
//    var offset: Long
//    = 0L
//  }
//
//}
//
//class FastDateParserTest extends JUnitSuite {
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
//    * @return the DateParser instance to use for testing
//    */
//  protected def getInstance(format: String, timeZone: TimeZone, locale: Locale) = new Nothing(format, timeZone, locale, null)
//
//  @Test def test_Equality_Hash() = {
//    val parsers = Array(getInstance(FastDateParserTest.yMdHmsSZ, FastDateParserTest.NEW_YORK, Locale.US), getInstance(FastDateParserTest.DMY_DOT, FastDateParserTest.NEW_YORK, Locale.US), getInstance(FastDateParserTest.YMD_SLASH, FastDateParserTest.NEW_YORK, Locale.US), getInstance(FastDateParserTest.MDY_DASH, FastDateParserTest.NEW_YORK, Locale.US), getInstance(FastDateParserTest.MDY_SLASH, FastDateParserTest.NEW_YORK, Locale.US), getInstance(FastDateParserTest.MDY_SLASH, FastDateParserTest.REYKJAVIK, Locale.US), getInstance(FastDateParserTest.MDY_SLASH, FastDateParserTest.REYKJAVIK, FastDateParserTest.SWEDEN))
//    val map = new util.HashMap[Nothing, Integer]
//    var i = 0
//    for (parser <- parsers) {
//      map.put(parser, Integer.valueOf({
//        i += 1; i - 1
//      }))
//    }
//    i = 0
//    for (parser <- parsers) {
//      assertEquals({
//        i += 1; i - 1
//      }, map.get(parser).intValue)
//    }
//  }
//
//  @Test
//  @throws[ParseException]
//  def testParseZone() = {
//    val cal = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    cal.clear()
//    cal.set(2003, Calendar.JULY, 10, 16, 33, 20)
//    val fdf = getInstance(FastDateParserTest.yMdHmsSZ, FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(cal.getTime, fdf.parse("2003-07-10T15:33:20.000 -0500"))
//    assertEquals(cal.getTime, fdf.parse("2003-07-10T15:33:20.000 GMT-05:00"))
//    assertEquals(cal.getTime, fdf.parse("2003-07-10T16:33:20.000 Eastern Daylight Time"))
//    assertEquals(cal.getTime, fdf.parse("2003-07-10T16:33:20.000 EDT"))
//    cal.setTimeZone(TimeZone.getTimeZone("GMT-3"))
//    cal.set(2003, Calendar.FEBRUARY, 10, 9, 0, 0)
//    assertEquals(cal.getTime, fdf.parse("2003-02-10T09:00:00.000 -0300"))
//    cal.setTimeZone(TimeZone.getTimeZone("GMT+5"))
//    cal.set(2003, Calendar.FEBRUARY, 10, 15, 5, 6)
//    assertEquals(cal.getTime, fdf.parse("2003-02-10T15:05:06.000 +0500"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testParseLongShort() = {
//    val cal = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10, 15, 33, 20)
//    cal.set(Calendar.MILLISECOND, 989)
//    cal.setTimeZone(FastDateParserTest.NEW_YORK)
//    var fdf = getInstance("yyyy GGGG MMMM dddd aaaa EEEE HHHH mmmm ssss SSSS ZZZZ", FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(cal.getTime, fdf.parse("2003 AD February 0010 PM Monday 0015 0033 0020 0989 GMT-05:00"))
//    cal.set(Calendar.ERA, GregorianCalendar.BC)
//    val parse = fdf.parse("2003 BC February 0010 PM Saturday 0015 0033 0020 0989 GMT-05:00")
//    assertEquals(cal.getTime, parse)
//    fdf = getInstance("y G M d a E H m s S Z", FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(cal.getTime, fdf.parse("03 BC 2 10 PM Sat 15 33 20 989 -0500"))
//    cal.set(Calendar.ERA, GregorianCalendar.AD)
//    assertEquals(cal.getTime, fdf.parse("03 AD 2 10 PM Saturday 15 33 20 989 -0500"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testAmPm() = {
//    val cal = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    cal.clear()
//    val h = getInstance("yyyy-MM-dd hh a mm:ss", FastDateParserTest.NEW_YORK, Locale.US)
//    val K = getInstance("yyyy-MM-dd KK a mm:ss", FastDateParserTest.NEW_YORK, Locale.US)
//    val k = getInstance("yyyy-MM-dd kk:mm:ss", FastDateParserTest.NEW_YORK, Locale.US)
//    val H = getInstance("yyyy-MM-dd HH:mm:ss", FastDateParserTest.NEW_YORK, Locale.US)
//    cal.set(2010, Calendar.AUGUST, 1, 0, 33, 20)
//    assertEquals(cal.getTime, h.parse("2010-08-01 12 AM 33:20"))
//    assertEquals(cal.getTime, K.parse("2010-08-01 0 AM 33:20"))
//    assertEquals(cal.getTime, k.parse("2010-08-01 00:33:20"))
//    assertEquals(cal.getTime, H.parse("2010-08-01 00:33:20"))
//    cal.set(2010, Calendar.AUGUST, 1, 3, 33, 20)
//    assertEquals(cal.getTime, h.parse("2010-08-01 3 AM 33:20"))
//    assertEquals(cal.getTime, K.parse("2010-08-01 3 AM 33:20"))
//    assertEquals(cal.getTime, k.parse("2010-08-01 03:33:20"))
//    assertEquals(cal.getTime, H.parse("2010-08-01 03:33:20"))
//    cal.set(2010, Calendar.AUGUST, 1, 15, 33, 20)
//    assertEquals(cal.getTime, h.parse("2010-08-01 3 PM 33:20"))
//    assertEquals(cal.getTime, K.parse("2010-08-01 3 PM 33:20"))
//    assertEquals(cal.getTime, k.parse("2010-08-01 15:33:20"))
//    assertEquals(cal.getTime, H.parse("2010-08-01 15:33:20"))
//    cal.set(2010, Calendar.AUGUST, 1, 12, 33, 20)
//    assertEquals(cal.getTime, h.parse("2010-08-01 12 PM 33:20"))
//    assertEquals(cal.getTime, K.parse("2010-08-01 0 PM 33:20"))
//    assertEquals(cal.getTime, k.parse("2010-08-01 12:33:20"))
//    assertEquals(cal.getTime, H.parse("2010-08-01 12:33:20"))
//  }
//
//  private def getEraStart(year: Int, zone: TimeZone, locale: Locale) = {
//    val cal = Calendar.getInstance(zone, locale)
//    cal.clear()
//    // http://docs.oracle.com/javase/6/docs/technotes/guides/intl/calendar.doc.html
//    if (locale == FastDateParser.JAPANESE_IMPERIAL) if (year < 1868) {
//      cal.set(Calendar.ERA, 0)
//      cal.set(Calendar.YEAR, 1868 - year)
//    }
//    else {
//      if (year < 0) {
//        cal.set(Calendar.ERA, GregorianCalendar.BC)
//        year = -year
//      }
//      cal.set(Calendar.YEAR, year / 100 * 100)
//    }
//    cal
//  }
//
//  @throws[ParseException]
//  private def validateSdfFormatFdpParseEquality(format: String, locale: Locale, tz: TimeZone, fdp: Nothing, in: Date, year: Int, cs: Date) = {
//    val sdf = new SimpleDateFormat(format, locale)
//    sdf.setTimeZone(tz)
//    if (format == FastDateParserTest.SHORT_FORMAT) sdf.set2DigitYearStart(cs)
//    val fmt = sdf.format(in)
//    try {
//      val out = fdp.parse(fmt)
//      assertEquals(in, out, locale.toString + " " + in + " " + format + " " + tz.getID)
//    } catch {
//      case pe: ParseException =>
//        if (year >= 1868 || !(locale.getCountry == "JP")) { // LANG-978
//          throw pe
//        }
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  // Check that all Locales can parse the formats we use
//  def testParses() = for (format <- Array[String](FastDateParserTest.LONG_FORMAT, FastDateParserTest.SHORT_FORMAT)) {
//    for (locale <- Locale.getAvailableLocales) {
//      for (tz <- Array[TimeZone](FastDateParserTest.NEW_YORK, FastDateParserTest.REYKJAVIK, FastDateParserTest.GMT)) {
//        for (year <- Array[Int](2003, 1940, 1868, 1867, 1, -1, -1940)) {
//          val cal = getEraStart(year, tz, locale)
//          val centuryStart = cal.getTime
//          cal.set(Calendar.MONTH, 1)
//          cal.set(Calendar.DAY_OF_MONTH, 10)
//          val in = cal.getTime
//          val fdp = new Nothing(format, tz, locale, centuryStart)
//          validateSdfFormatFdpParseEquality(format, locale, tz, fdp, in, year, centuryStart)
//        }
//      }
//    }
//  }
//
//  // we cannot use historic dates to test timezone parsing, some timezones have second offsets
//  // as well as hours and minutes which makes the z formats a low fidelity round trip
//  @Test
//  @throws[Exception]
//  def testTzParses() = { // Check that all Locales can parse the time formats we use
//    for (locale <- Locale.getAvailableLocales) {
//      val fdp = new Nothing("yyyy/MM/dd z", TimeZone.getDefault, locale)
//      for (tz <- Array[TimeZone](FastDateParserTest.NEW_YORK, FastDateParserTest.REYKJAVIK, FastDateParserTest.GMT)) {
//        val cal = Calendar.getInstance(tz, locale)
//        cal.clear()
//        cal.set(Calendar.YEAR, 2000)
//        cal.set(Calendar.MONTH, 1)
//        cal.set(Calendar.DAY_OF_MONTH, 10)
//        val expected = cal.getTime
//        val actual = fdp.parse("2000/02/10 " + tz.getDisplayName(locale))
//        assertEquals(expected, actual, "tz:" + tz.getID + " locale:" + locale.getDisplayName)
//      }
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  def testLocales_Long_AD() = testLocales(FastDateParserTest.LONG_FORMAT, false)
//
//  @Test
//  @throws[Exception]
//  def testLocales_Long_BC() = testLocales(FastDateParserTest.LONG_FORMAT, true)
//
//  @Test
//  @throws[Exception]
//  def testLocales_Short_AD() = testLocales(FastDateParserTest.SHORT_FORMAT, false)
//
//  @Test
//  @throws[Exception]
//  def testLocales_Short_BC() = testLocales(FastDateParserTest.SHORT_FORMAT, true)
//
//  @Test
//  @throws[Exception]
//  def testLocales_LongNoEra_AD() = testLocales(FastDateParserTest.LONG_FORMAT_NOERA, false)
//
//  @Test
//  @throws[Exception]
//  def testLocales_LongNoEra_BC() = testLocales(FastDateParserTest.LONG_FORMAT_NOERA, true)
//
//  @Test
//  @throws[Exception]
//  def testLocales_ShortNoEra_AD() = testLocales(FastDateParserTest.SHORT_FORMAT_NOERA, false)
//
//  @Test
//  @throws[Exception]
//  def testLocales_ShortNoEra_BC() = testLocales(FastDateParserTest.SHORT_FORMAT_NOERA, true)
//
//  @throws[Exception]
//  private def testLocales(format: String, eraBC: Boolean) = {
//    val cal = Calendar.getInstance(FastDateParserTest.GMT)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10)
//    if (eraBC) cal.set(Calendar.ERA, GregorianCalendar.BC)
//    for (locale <- Locale.getAvailableLocales) { // ja_JP_JP cannot handle dates before 1868 properly
//      if (eraBC && locale == FastDateParser.JAPANESE_IMPERIAL) continue //todo: continue is not supported
//      val sdf = new SimpleDateFormat(format, locale)
//      val fdf = getInstance(format, locale)
//      // If parsing fails, a ParseException will be thrown and the test will fail
//      checkParse(locale, cal, sdf, fdf)
//    }
//  }
//
//  @Test
//  @throws[ParseException]
//  def testJpLocales() = {
//    val cal = Calendar.getInstance(FastDateParserTest.GMT)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10)
//    cal.set(Calendar.ERA, GregorianCalendar.BC)
//    val locale = LocaleUtils.toLocale("zh")
//    val sdf = new SimpleDateFormat(FastDateParserTest.LONG_FORMAT, locale)
//    val fdf = getInstance(FastDateParserTest.LONG_FORMAT, locale)
//    checkParse(locale, cal, sdf, fdf)
//  }
//
//  @throws[ParseException]
//  private def checkParse(locale: Locale, cal: Calendar, sdf: SimpleDateFormat, fdf: Nothing) = {
//    val formattedDate = sdf.format(cal.getTime)
//    checkParse(locale, sdf, fdf, formattedDate)
//    checkParse(locale, sdf, fdf, formattedDate.toLowerCase(locale))
//    checkParse(locale, sdf, fdf, formattedDate.toUpperCase(locale))
//  }
//
//  @throws[ParseException]
//  private def checkParse(locale: Locale, sdf: SimpleDateFormat, fdf: Nothing, formattedDate: String) = try {
//    val expectedTime = sdf.parse(formattedDate)
//    val actualTime = fdf.parse(formattedDate)
//    assertEquals(expectedTime, actualTime, "locale : " + locale + " formattedDate : " + formattedDate + "\n")
//  } catch {
//    case e: Exception =>
//      fail("locale : " + locale + " formattedDate : " + formattedDate + " error : " + e + "\n", e)
//  }
//
//  @Test
//  @throws[ParseException]
//  def testParseNumerics() = {
//    val cal = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10, 15, 33, 20)
//    cal.set(Calendar.MILLISECOND, 989)
//    val fdf = getInstance("yyyyMMddHHmmssSSS", FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(cal.getTime, fdf.parse("20030210153320989"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testQuotes() = {
//    val cal = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10, 15, 33, 20)
//    cal.set(Calendar.MILLISECOND, 989)
//    val fdf = getInstance("''yyyyMMdd'A''B'HHmmssSSS''", FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(cal.getTime, fdf.parse("'20030210A'B153320989'"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testSpecialCharacters() = {
//    testSdfAndFdp("q", "", true) // bad pattern character (at present)
//    testSdfAndFdp("Q", "", true) // bad pattern character
//    testSdfAndFdp("$", "$", false) // OK
//    testSdfAndFdp("?.d", "?.12", false)
//    testSdfAndFdp("''yyyyMMdd'A''B'HHmmssSSS''", "'20030210A'B153320989'", false)
//    testSdfAndFdp("''''yyyyMMdd'A''B'HHmmssSSS''", "''20030210A'B153320989'", false)
//    testSdfAndFdp("'$\\Ed'", "$\\Ed", false)
//    // quoted charaters are case sensitive
//    testSdfAndFdp("'QED'", "QED", false)
//    testSdfAndFdp("'QED'", "qed", true)
//    // case sensitive after insensitive Month field
//    testSdfAndFdp("yyyy-MM-dd 'QED'", "2003-02-10 QED", false)
//    testSdfAndFdp("yyyy-MM-dd 'QED'", "2003-02-10 qed", true)
//  }
//
//  @Test
//  @throws[Exception]
//  def testLANG_832() = {
//    testSdfAndFdp("'d'd", "d3", false)
//    testSdfAndFdp("'d'd'", "d3", true) // should fail (unterminated quote)
//  }
//
//  @Test
//  @throws[Exception]
//  def testLANG_831() = testSdfAndFdp("M E", "3  Tue", true)
//
//  @throws[Exception]
//  private def testSdfAndFdp(format: String, date: String, shouldFail: Boolean) = {
//    var dfdp = null
//    var dsdf = null
//    var f = null
//    var s = null
//    try {
//      val sdf = new SimpleDateFormat(format, Locale.US)
//      sdf.setTimeZone(FastDateParserTest.NEW_YORK)
//      dsdf = sdf.parse(date)
//      assertFalse(shouldFail, "Expected SDF failure, but got " + dsdf + " for [" + format + ", " + date + "]")
//    } catch {
//      case e: Exception =>
//        s = e
//        if (!shouldFail) throw e
//    }
//    try {
//      val fdp = getInstance(format, FastDateParserTest.NEW_YORK, Locale.US)
//      dfdp = fdp.parse(date)
//      assertFalse(shouldFail, "Expected FDF failure, but got " + dfdp + " for [" + format + ", " + date + "]")
//    } catch {
//      case e: Exception =>
//        f = e
//        if (!shouldFail) throw e
//    }
//    // SDF and FDF should produce equivalent results
//    assertEquals(f == null, s == null, "Should both or neither throw Exceptions")
//    assertEquals(dsdf, dfdp, "Parsed dates should be equal")
//  }
//
//  @Test
//  @throws[ParseException]
//  def testDayOf() = {
//    val cal = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10)
//    val fdf = getInstance("W w F D y", FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(cal.getTime, fdf.parse("3 7 2 41 03"))
//  }
//
//  /**
//    * Test case for {@link FastDateParser# FastDateParser ( String, TimeZone, Locale)}.
//    *
//    * @throws ParseException so we don't have to catch it
//    */
//  @Test
//  @throws[ParseException]
//  def testShortDateStyleWithLocales() = {
//    var fdf = getDateInstance(FastDateFormat.SHORT, Locale.US)
//    val cal = Calendar.getInstance
//    cal.clear()
//    cal.set(2004, Calendar.FEBRUARY, 3)
//    assertEquals(cal.getTime, fdf.parse("2/3/04"))
//    fdf = getDateInstance(FastDateFormat.SHORT, FastDateParserTest.SWEDEN)
//    assertEquals(cal.getTime, fdf.parse("2004-02-03"))
//  }
//
//  /**
//    * Tests that pre-1000AD years get padded with yyyy
//    *
//    * @throws ParseException so we don't have to catch it
//    */
//  @Test
//  @throws[ParseException]
//  def testLowYearPadding() = {
//    val parser = getInstance(FastDateParserTest.YMD_SLASH)
//    val cal = Calendar.getInstance
//    cal.clear()
//    cal.set(1, Calendar.JANUARY, 1)
//    assertEquals(cal.getTime, parser.parse("0001/01/01"))
//    cal.set(10, Calendar.JANUARY, 1)
//    assertEquals(cal.getTime, parser.parse("0010/01/01"))
//    cal.set(100, Calendar.JANUARY, 1)
//    assertEquals(cal.getTime, parser.parse("0100/01/01"))
//    cal.set(999, Calendar.JANUARY, 1)
//    assertEquals(cal.getTime, parser.parse("0999/01/01"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testMilleniumBug() = {
//    val parser = getInstance(FastDateParserTest.DMY_DOT)
//    val cal = Calendar.getInstance
//    cal.clear()
//    cal.set(1000, Calendar.JANUARY, 1)
//    assertEquals(cal.getTime, parser.parse("01.01.1000"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testLang303() = {
//    var parser = getInstance(FastDateParserTest.YMD_SLASH)
//    val cal = Calendar.getInstance
//    cal.set(2004, Calendar.DECEMBER, 31)
//    val date = parser.parse("2004/11/31")
//    parser = SerializationUtils.deserialize(SerializationUtils.serialize(parser.asInstanceOf[Serializable]))
//    assertEquals(date, parser.parse("2004/11/31"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testLang538() = {
//    val parser = getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", FastDateParserTest.GMT)
//    val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT-8"))
//    cal.clear()
//    cal.set(2009, Calendar.OCTOBER, 16, 8, 42, 16)
//    assertEquals(cal.getTime, parser.parse("2009-10-16T16:42:16.000Z"))
//  }
//
//  @Test def testEquals() = {
//    val parser1 = getInstance(FastDateParserTest.YMD_SLASH)
//    val parser2 = getInstance(FastDateParserTest.YMD_SLASH)
//    assertEquals(parser1, parser2)
//    assertEquals(parser1.hashCode, parser2.hashCode)
//    assertNotEquals(parser1, new Any)
//  }
//
//  @Test def testToStringContainsName() = {
//    val parser = getInstance(FastDateParserTest.YMD_SLASH)
//    assertTrue(parser.toString.startsWith("FastDate"))
//  }
//
//  @Test def testPatternMatches() = {
//    val parser = getInstance(FastDateParserTest.yMdHmsSZ)
//    assertEquals(FastDateParserTest.yMdHmsSZ, parser.getPattern)
//  }
//
//  @Test def testLocaleMatches() = {
//    val parser = getInstance(FastDateParserTest.yMdHmsSZ, FastDateParserTest.SWEDEN)
//    assertEquals(FastDateParserTest.SWEDEN, parser.getLocale)
//  }
//
//  @Test def testTimeZoneMatches() = {
//    val parser = getInstance(FastDateParserTest.yMdHmsSZ, FastDateParserTest.REYKJAVIK)
//    assertEquals(FastDateParserTest.REYKJAVIK, parser.getTimeZone)
//  }
//
//  @Test
//  @throws[ParseException]
//  def testLang996() = {
//    val expected = Calendar.getInstance(FastDateParserTest.NEW_YORK, Locale.US)
//    expected.clear()
//    expected.set(2014, Calendar.MAY, 14)
//    val fdp = getInstance("ddMMMyyyy", FastDateParserTest.NEW_YORK, Locale.US)
//    assertEquals(expected.getTime, fdp.parse("14may2014"))
//    assertEquals(expected.getTime, fdp.parse("14MAY2014"))
//    assertEquals(expected.getTime, fdp.parse("14May2014"))
//  }
//
//  @Test def test1806Argument() = assertThrows(classOf[IllegalArgumentException], () => getInstance("XXXX"))
//
//  @Test
//  @throws[ParseException]
//  def test1806() = {
//    val formatStub = "yyyy-MM-dd'T'HH:mm:ss.SSS"
//    val dateStub = "2001-02-04T12:08:56.235"
//    for (trial <- FastDateParserTest.Expected1806.values) {
//      val cal = FastDateParserTest.initializeCalendar(trial.zone)
//      val message = trial.zone.getDisplayName + ";"
//      var parser = getInstance(formatStub + "X", trial.zone)
//      assertEquals(cal.getTime.getTime, parser.parse(dateStub + trial.one).getTime - trial.offset, message + trial.one)
//      parser = getInstance(formatStub + "XX", trial.zone)
//      assertEquals(cal.getTime, parser.parse(dateStub + trial.two), message + trial.two)
//      parser = getInstance(formatStub + "XXX", trial.zone)
//      assertEquals(cal.getTime, parser.parse(dateStub + trial.three), message + trial.three)
//    }
//  }
//
//  @Test
//  @throws[ParseException]
//  def testLang1121() = {
//    val kst = TimeZone.getTimeZone("KST")
//    val fdp = getInstance("yyyyMMdd", kst, Locale.KOREA)
//    assertThrows(classOf[ParseException], () => fdp.parse("2015"))
//    // Wed Apr 29 00:00:00 KST 2015
//    var actual = fdp.parse("20150429")
//    val cal = Calendar.getInstance(kst, Locale.KOREA)
//    cal.clear()
//    cal.set(2015, 3, 29)
//    var expected = cal.getTime
//    assertEquals(expected, actual)
//    val df = new SimpleDateFormat("yyyyMMdd", Locale.KOREA)
//    df.setTimeZone(kst)
//    expected = df.parse("20150429113100")
//    // Thu Mar 16 00:00:00 KST 81724
//    actual = fdp.parse("20150429113100")
//    assertEquals(expected, actual)
//  }
//
//  @Test def testParseOffset() = {
//    val parser = getInstance(FastDateParserTest.YMD_SLASH)
//    val date = parser.parse("Today is 2015/07/04", new ParsePosition(9))
//    val cal = Calendar.getInstance
//    cal.clear()
//    cal.set(2015, Calendar.JULY, 4)
//    assertEquals(cal.getTime, date)
//  }
//
//  @Test
//  @throws[ParseException]
//  def testDayNumberOfWeek() = {
//    val parser = getInstance("u")
//    val calendar = Calendar.getInstance
//    calendar.setTime(parser.parse("1"))
//    assertEquals(Calendar.MONDAY, calendar.get(Calendar.DAY_OF_WEEK))
//    calendar.setTime(parser.parse("6"))
//    assertEquals(Calendar.SATURDAY, calendar.get(Calendar.DAY_OF_WEEK))
//    calendar.setTime(parser.parse("7"))
//    assertEquals(Calendar.SUNDAY, calendar.get(Calendar.DAY_OF_WEEK))
//  }
//
//  @Test
//  @throws[ParseException]
//  def testLang1380() = {
//    val expected = Calendar.getInstance(FastDateParserTest.GMT, Locale.FRANCE)
//    expected.clear()
//    expected.set(2014, Calendar.APRIL, 14)
//    val fdp = getInstance("dd MMM yyyy", FastDateParserTest.GMT, Locale.FRANCE)
//    assertEquals(expected.getTime, fdp.parse("14 avril 2014"))
//    assertEquals(expected.getTime, fdp.parse("14 avr. 2014"))
//    assertEquals(expected.getTime, fdp.parse("14 avr 2014"))
//  }
//
//  @Test
//  @throws[ParseException]
//  def java15BuggyLocaleTestAll() = for (locale <- Locale.getAvailableLocales) {
//    testSingleLocale(locale)
//  }
//
//  @Test
//  @throws[ParseException]
//  def java15BuggyLocaleTest(): Unit = {
//    val buggyLocaleName = "ff_LR_#Adlm"
//    var buggyLocale = null
//    for (locale <- Locale.getAvailableLocales) {
//      if (buggyLocaleName == locale.toString) {
//        buggyLocale = locale
//        break //todo: break is not supported
//      }
//    }
//    if (buggyLocale == null) return
//    testSingleLocale(buggyLocale)
//  }
//
//  @throws[ParseException]
//  private def testSingleLocale(locale: Locale) = {
//    val cal = Calendar.getInstance(FastDateParserTest.GMT)
//    cal.clear()
//    cal.set(2003, Calendar.FEBRUARY, 10)
//    val sdf = new SimpleDateFormat(FastDateParserTest.LONG_FORMAT, locale)
//    val formattedDate = sdf.format(cal.getTime)
//    sdf.parse(formattedDate)
//    sdf.parse(formattedDate.toUpperCase(locale))
//    sdf.parse(formattedDate.toLowerCase(locale))
//  }
//}
