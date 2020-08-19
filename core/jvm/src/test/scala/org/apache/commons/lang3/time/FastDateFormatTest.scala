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
//import org.junit.Assert.assertNotSame
//import org.junit.Assert.assertSame
//import org.junit.Assert.fail
//import java.text.FieldPosition
//import java.text.Format
//import java.text.ParsePosition
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//import java.util.TimeZone
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.TimeUnit
//import java.util.concurrent.atomic.AtomicInteger
//import java.util.concurrent.atomic.AtomicLongArray
//import org.junit.Test
//import org.junitpioneer.jupiter.DefaultLocale
//import org.junitpioneer.jupiter.DefaultTimeZone
//
///**
//  * Unit tests {@link org.apache.commons.lang3.time.FastDateFormat}.
//  *
//  * @since 2.0
//  */
//object FastDateFormatTest {
//  private val NTHREADS = 10
//  private val NROUNDS = 10000
//}
//
//class FastDateFormatTest {
//  /*
//      * Only the cache methods need to be tested here.
//      * The print methods are tested by {@link FastDateFormat_PrinterTest}
//      * and the parse methods are tested by {@link FastDateFormat_ParserTest}
//      */ @Test def test_getInstance() = {
//    val format1 = FastDateFormat.getInstance
//    val format2 = FastDateFormat.getInstance
//    assertSame(format1, format2)
//  }
//
//  @Test def test_getInstance_String() = {
//    val format1 = FastDateFormat.getInstance("MM/DD/yyyy")
//    val format2 = FastDateFormat.getInstance("MM-DD-yyyy")
//    val format3 = FastDateFormat.getInstance("MM-DD-yyyy")
//    assertNotSame(format1, format2)
//    assertSame(format2, format3)
//    assertEquals("MM/DD/yyyy", format1.getPattern)
//    assertEquals(TimeZone.getDefault, format1.getTimeZone)
//    assertEquals(TimeZone.getDefault, format2.getTimeZone)
//  }
//
//  @DefaultLocale(language = "en", country = "US")
//  @DefaultTimeZone("America/New_York")
//  @Test def test_getInstance_String_TimeZone() = {
//    val format1 = FastDateFormat.getInstance("MM/DD/yyyy", TimeZone.getTimeZone("Atlantic/Reykjavik"))
//    val format2 = FastDateFormat.getInstance("MM/DD/yyyy")
//    val format3 = FastDateFormat.getInstance("MM/DD/yyyy", TimeZone.getDefault)
//    val format4 = FastDateFormat.getInstance("MM/DD/yyyy", TimeZone.getDefault)
//    val format5 = FastDateFormat.getInstance("MM-DD-yyyy", TimeZone.getDefault)
//    val format6 = FastDateFormat.getInstance("MM-DD-yyyy")
//    assertNotSame(format1, format2)
//    assertEquals(TimeZone.getTimeZone("Atlantic/Reykjavik"), format1.getTimeZone)
//    assertEquals(TimeZone.getDefault, format2.getTimeZone)
//    assertSame(format3, format4)
//    assertNotSame(format3, format5)
//    assertNotSame(format4, format6)
//  }
//
//  @DefaultLocale(language = "en", country = "US")
//  @Test def test_getInstance_String_Locale() = {
//    val format1 = FastDateFormat.getInstance("MM/DD/yyyy", Locale.GERMANY)
//    val format2 = FastDateFormat.getInstance("MM/DD/yyyy")
//    val format3 = FastDateFormat.getInstance("MM/DD/yyyy", Locale.GERMANY)
//    assertNotSame(format1, format2)
//    assertSame(format1, format3)
//    assertEquals(Locale.GERMANY, format1.getLocale)
//  }
//
//  @DefaultLocale(language = "en", country = "US")
//  @Test def test_changeDefault_Locale_DateInstance() = {
//    val format1 = FastDateFormat.getDateInstance(FastDateFormat.FULL, Locale.GERMANY)
//    val format2 = FastDateFormat.getDateInstance(FastDateFormat.FULL)
//    Locale.setDefault(Locale.GERMANY)
//    val format3 = FastDateFormat.getDateInstance(FastDateFormat.FULL)
//    assertSame(Locale.GERMANY, format1.getLocale)
//    assertEquals(Locale.US, format2.getLocale)
//    assertSame(Locale.GERMANY, format3.getLocale)
//    assertNotSame(format1, format2)
//    assertNotSame(format2, format3)
//  }
//
//  @DefaultLocale(language = "en", country = "US")
//  @Test def test_changeDefault_Locale_DateTimeInstance() = {
//    val format1 = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL, Locale.GERMANY)
//    val format2 = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL)
//    Locale.setDefault(Locale.GERMANY)
//    val format3 = FastDateFormat.getDateTimeInstance(FastDateFormat.FULL, FastDateFormat.FULL)
//    assertSame(Locale.GERMANY, format1.getLocale)
//    assertEquals(Locale.US, format2.getLocale)
//    assertSame(Locale.GERMANY, format3.getLocale)
//    assertNotSame(format1, format2)
//    assertNotSame(format2, format3)
//  }
//
//  @DefaultLocale(language = "en", country = "US")
//  @DefaultTimeZone("America/New_York")
//  @Test def test_getInstance_String_TimeZone_Locale() = {
//    val format1 = FastDateFormat.getInstance("MM/DD/yyyy", TimeZone.getTimeZone("Atlantic/Reykjavik"), Locale.GERMANY)
//    val format2 = FastDateFormat.getInstance("MM/DD/yyyy", Locale.GERMANY)
//    val format3 = FastDateFormat.getInstance("MM/DD/yyyy", TimeZone.getDefault, Locale.GERMANY)
//    assertNotSame(format1, format2)
//    assertEquals(TimeZone.getTimeZone("Atlantic/Reykjavik"), format1.getTimeZone)
//    assertEquals(TimeZone.getDefault, format2.getTimeZone)
//    assertEquals(TimeZone.getDefault, format3.getTimeZone)
//    assertEquals(Locale.GERMANY, format1.getLocale)
//    assertEquals(Locale.GERMANY, format2.getLocale)
//    assertEquals(Locale.GERMANY, format3.getLocale)
//  }
//
//  @Test def testCheckDefaults() = {
//    val format = FastDateFormat.getInstance
//    val medium = FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT, FastDateFormat.SHORT)
//    assertEquals(medium, format)
//    val sdf = new SimpleDateFormat
//    assertEquals(sdf.toPattern, format.getPattern)
//    assertEquals(Locale.getDefault, format.getLocale)
//    assertEquals(TimeZone.getDefault, format.getTimeZone)
//  }
//
//  @Test def testCheckDifferingStyles() = {
//    val shortShort = FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT, FastDateFormat.SHORT, Locale.US)
//    val shortLong = FastDateFormat.getDateTimeInstance(FastDateFormat.SHORT, FastDateFormat.LONG, Locale.US)
//    val longShort = FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.SHORT, Locale.US)
//    val longLong = FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.LONG, Locale.US)
//    assertNotEquals(shortShort, shortLong)
//    assertNotEquals(shortShort, longShort)
//    assertNotEquals(shortShort, longLong)
//    assertNotEquals(shortLong, longShort)
//    assertNotEquals(shortLong, longLong)
//    assertNotEquals(longShort, longLong)
//  }
//
//  @Test def testDateDefaults() = {
//    assertEquals(FastDateFormat.getDateInstance(FastDateFormat.LONG, Locale.CANADA), FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getDefault, Locale.CANADA))
//    assertEquals(FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York")), FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York"), Locale.getDefault))
//    assertEquals(FastDateFormat.getDateInstance(FastDateFormat.LONG), FastDateFormat.getDateInstance(FastDateFormat.LONG, TimeZone.getDefault, Locale.getDefault))
//  }
//
//  @Test def testTimeDefaults() = {
//    assertEquals(FastDateFormat.getTimeInstance(FastDateFormat.LONG, Locale.CANADA), FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getDefault, Locale.CANADA))
//    assertEquals(FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York")), FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getTimeZone("America/New_York"), Locale.getDefault))
//    assertEquals(FastDateFormat.getTimeInstance(FastDateFormat.LONG), FastDateFormat.getTimeInstance(FastDateFormat.LONG, TimeZone.getDefault, Locale.getDefault))
//  }
//
//  @Test def testTimeDateDefaults() = {
//    assertEquals(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, Locale.CANADA), FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getDefault, Locale.CANADA))
//    assertEquals(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getTimeZone("America/New_York")), FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getTimeZone("America/New_York"), Locale.getDefault))
//    assertEquals(FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM), FastDateFormat.getDateTimeInstance(FastDateFormat.LONG, FastDateFormat.MEDIUM, TimeZone.getDefault, Locale.getDefault))
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testParseSync() = {
//    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS"
//    val inner = new SimpleDateFormat(pattern)
//    val sdf = new Format() {
//      override def format(obj: Any, toAppendTo: StringBuffer, fieldPosition: FieldPosition) = {
//        this synchronized inner.format(obj, toAppendTo, fieldPosition)
//      }
//
//      override
//
//      def parseObject(source: String, pos: ParsePosition) = {
//        this synchronized inner.parseObject(source, pos)
//      }
//    }
//    val sdfTime = measureTime(sdf, sdf)
//    val fdf = FastDateFormat.getInstance(pattern)
//    val fdfTime = measureTime(fdf, fdf)
//    //System.out.println(">>FastDateFormatTest: FastDatePrinter:"+fdfTime.get(0)+"  SimpleDateFormat:"+sdfTime.get(0));
//    //System.out.println(">>FastDateFormatTest: FastDateParser:"+fdfTime.get(1)+"  SimpleDateFormat:"+sdfTime.get(1));
//  }
//
//  @throws[InterruptedException]
//  private def measureTime(printer: Format, parser: Format) = {
//    val pool = Executors.newFixedThreadPool(FastDateFormatTest.NTHREADS)
//    val failures = new AtomicInteger(0)
//    val totalElapsed = new AtomicLongArray(2)
//    try for (i <- 0 until FastDateFormatTest.NTHREADS) {
//      pool.submit(() => {
//        def foo() =
//          for (j <- 0 until FastDateFormatTest.NROUNDS) {
//            try {
//              val date = new Date
//              val t0 = System.currentTimeMillis
//              val formattedDate = printer.format(date)
//              totalElapsed.addAndGet(0, System.currentTimeMillis - t0)
//              val t1 = System.currentTimeMillis
//              val pd = parser.parseObject(formattedDate)
//              totalElapsed.addAndGet(1, System.currentTimeMillis - t1)
//              if (!(date == pd)) failures.incrementAndGet
//            } catch {
//              case e: Exception =>
//                failures.incrementAndGet
//                e.printStackTrace()
//            }
//          }
//
//        foo()
//      })
//    }
//    finally {
//      pool.shutdown()
//      // depending on the performance of the machine used to run the parsing,
//      // the tests can run for a while. It should however complete within
//      // 30 seconds. Might need increase on very slow machines.
//      if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
//        pool.shutdownNow
//        fail("did not complete tasks")
//      }
//    }
//    assertEquals(0, failures.get)
//    totalElapsed
//  }
//
//  /**
//    * According to LANG-954 (https://issues.apache.org/jira/browse/LANG-954) this is broken in Android 2.1.
//    */
//  @Test def testLANG_954() = {
//    val pattern = "yyyy-MM-dd'T'"
//    FastDateFormat.getInstance(pattern)
//  }
//
//  @Test def testLANG_1152() = {
//    val utc = FastTimeZone.getGmtTimeZone
//    val date = new Date(Long.MAX_VALUE)
//    var dateAsString = FastDateFormat.getInstance("yyyy-MM-dd", utc, Locale.US).format(date)
//    assertEquals("292278994-08-17", dateAsString)
//    dateAsString = FastDateFormat.getInstance("dd/MM/yyyy", utc, Locale.US).format(date)
//    assertEquals("17/08/292278994", dateAsString)
//  }
//
//  @Test def testLANG_1267() = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//}
