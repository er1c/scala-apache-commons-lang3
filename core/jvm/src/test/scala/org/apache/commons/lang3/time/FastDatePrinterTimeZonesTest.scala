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
//import java.text.SimpleDateFormat
//import java.util
//import java.util.Calendar
//import java.util.TimeZone
//import java.util.stream.Stream
//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.MethodSource
//import org.scalatestplus.junit.JUnitSuite
//
//object FastDatePrinterTimeZonesTest {
//  private val PATTERN = "h:mma z"
//
//  def data = util.Arrays.stream(TimeZone.getAvailableIDs).map(TimeZone.getTimeZone)
//}
//
//class FastDatePrinterTimeZonesTest extends JUnitSuite {
//  @ParameterizedTest
//  @MethodSource("data") def testCalendarTimezoneRespected(timeZone: TimeZone) = {
//    val cal = Calendar.getInstance(timeZone)
//    val sdf = new SimpleDateFormat(FastDatePrinterTimeZonesTest.PATTERN)
//    sdf.setTimeZone(timeZone)
//    val expectedValue = sdf.format(cal.getTime)
//    val actualValue = FastDateFormat.getInstance(FastDatePrinterTimeZonesTest.PATTERN, timeZone).format(cal)
//    assertEquals(expectedValue, actualValue)
//  }
//}
