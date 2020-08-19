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
//import java.util.TimeZone
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Tests for FastTimeZone
//  */
//object FastTimeZoneTest {
//  private val HOURS_23 = 23 * 60 * 60 * 1000
//  private val HOURS_2 = 2 * 60 * 60 * 1000
//  private val MINUTES_59 = 59 * 60 * 1000
//  private val MINUTES_5 = 5 * 60 * 1000
//}
//
//class FastTimeZoneTest extends JUnitSuite {
//  @Test def testGetGmtTimeZone() = assertEquals(0, FastTimeZone.getGmtTimeZone.getRawOffset)
//
//  @Test def testBareGmt() = assertEquals(FastTimeZone.getGmtTimeZone, FastTimeZone.getTimeZone("GMT"))
//
//  @Test def testZ() = assertEquals(FastTimeZone.getGmtTimeZone, FastTimeZone.getTimeZone("Z"))
//
//  @Test def testUTC() = assertEquals(FastTimeZone.getGmtTimeZone, FastTimeZone.getTimeZone("UTC"))
//
//  @Test def testZeroOffsetsReturnSingleton() = {
//    assertEquals(FastTimeZone.getGmtTimeZone, FastTimeZone.getTimeZone("+0"))
//    assertEquals(FastTimeZone.getGmtTimeZone, FastTimeZone.getTimeZone("-0"))
//  }
//
//  @Test def testOlson() = assertEquals(TimeZone.getTimeZone("America/New_York"), FastTimeZone.getTimeZone("America/New_York"))
//
//  @Test def testGmtPrefix() = {
//    assertEquals(FastTimeZoneTest.HOURS_23, FastTimeZone.getGmtTimeZone("GMT+23:00").getRawOffset)
//    assertEquals(-FastTimeZoneTest.HOURS_23, FastTimeZone.getGmtTimeZone("GMT-23:00").getRawOffset)
//  }
//
//  @Test def testSign() = {
//    assertEquals(FastTimeZoneTest.HOURS_23, FastTimeZone.getGmtTimeZone("+23:00").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_2, FastTimeZone.getGmtTimeZone("+2:00").getRawOffset)
//    assertEquals(-FastTimeZoneTest.HOURS_23, FastTimeZone.getGmtTimeZone("-23:00").getRawOffset)
//    assertEquals(-FastTimeZoneTest.HOURS_2, FastTimeZone.getGmtTimeZone("-2:00").getRawOffset)
//  }
//
//  @Test def testHoursColonMinutes() = {
//    assertEquals(FastTimeZoneTest.HOURS_23, FastTimeZone.getGmtTimeZone("23:00").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_2, FastTimeZone.getGmtTimeZone("2:00").getRawOffset)
//    assertEquals(FastTimeZoneTest.MINUTES_59, FastTimeZone.getGmtTimeZone("00:59").getRawOffset)
//    assertEquals(FastTimeZoneTest.MINUTES_5, FastTimeZone.getGmtTimeZone("00:5").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_23 + FastTimeZoneTest.MINUTES_59, FastTimeZone.getGmtTimeZone("23:59").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_2 + FastTimeZoneTest.MINUTES_5, FastTimeZone.getGmtTimeZone("2:5").getRawOffset)
//  }
//
//  @Test def testHoursMinutes() = {
//    assertEquals(FastTimeZoneTest.HOURS_23, FastTimeZone.getGmtTimeZone("2300").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_2, FastTimeZone.getGmtTimeZone("0200").getRawOffset)
//    assertEquals(FastTimeZoneTest.MINUTES_59, FastTimeZone.getGmtTimeZone("0059").getRawOffset)
//    assertEquals(FastTimeZoneTest.MINUTES_5, FastTimeZone.getGmtTimeZone("0005").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_23 + FastTimeZoneTest.MINUTES_59, FastTimeZone.getGmtTimeZone("2359").getRawOffset)
//    assertEquals(FastTimeZoneTest.HOURS_2 + FastTimeZoneTest.MINUTES_5, FastTimeZone.getGmtTimeZone("0205").getRawOffset)
//  }
//}
