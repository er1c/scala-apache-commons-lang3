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
//import org.junit.Assert.assertNotEquals
//import java.text.DateFormatSymbols
//import java.text.ParseException
//import java.util.Date
//import java.util.Locale
//import java.util.TimeZone
//import org.junit.Test
//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.MethodSource
//
//class FastDateParser_TimeZoneStrategyTest {
//  @ParameterizedTest
//  @MethodSource("java.util.Locale#getAvailableLocales")
//  @throws[ParseException]
//  private[time] def testTimeZoneStrategyPattern(locale: Locale) = {
//    val parser = new Nothing("z", TimeZone.getDefault, locale)
//    val zones = DateFormatSymbols.getInstance(locale).getZoneStrings
//    for (zone <- zones) {
//      for (t <- 1 until zone.length) {
//        val tzDisplay = zone(t)
//        if (tzDisplay == null) break //todo: break is not supported
//        // An exception will be thrown and the test will fail if parsing isn't successful
//        parser.parse(tzDisplay)
//      }
//    }
//  }
//
//  @Test
//  @throws[ParseException]
//  private[time] def testLang1219() = {
//    val parser = new Nothing("dd.MM.yyyy HH:mm:ss z", TimeZone.getDefault, Locale.GERMAN)
//    val summer = parser.parse("26.10.2014 02:00:00 MESZ")
//    val standard = parser.parse("26.10.2014 02:00:00 MEZ")
//    assertNotEquals(summer.getTime, standard.getTime)
//  }
//}
