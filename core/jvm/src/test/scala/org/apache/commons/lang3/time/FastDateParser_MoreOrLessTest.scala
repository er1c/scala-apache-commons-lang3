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
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import java.text.ParsePosition
//import java.util.Calendar
//import java.util.Date
//import java.util.Locale
//import java.util.TimeZone
//import org.junit.Test
//
//object FastDateParser_MoreOrLessTest {
//  private val NEW_YORK = TimeZone.getTimeZone("America/New_York")
//}
//
//class FastDateParser_MoreOrLessTest {
//  @Test def testInputHasPrecedingCharacters() = {
//    val parser = new Nothing("MM/dd", TimeZone.getDefault, Locale.getDefault)
//    val parsePosition = new ParsePosition(0)
//    val date = parser.parse("A 3/23/61", parsePosition)
//    assertNull(date)
//    assertEquals(0, parsePosition.getIndex)
//    assertEquals(0, parsePosition.getErrorIndex)
//  }
//
//  @Test def testInputHasWhitespace() = {
//    val parser = new Nothing("M/d/y", TimeZone.getDefault, Locale.getDefault)
//    //SimpleDateFormat parser = new SimpleDateFormat("M/d/y");
//    val parsePosition = new ParsePosition(0)
//    val date = parser.parse(" 3/ 23/ 1961", parsePosition)
//    assertEquals(12, parsePosition.getIndex)
//    val calendar = Calendar.getInstance
//    calendar.setTime(date)
//    assertEquals(1961, calendar.get(Calendar.YEAR))
//    assertEquals(2, calendar.get(Calendar.MONTH))
//    assertEquals(23, calendar.get(Calendar.DATE))
//  }
//
//  @Test def testInputHasMoreCharacters() = {
//    val parser = new Nothing("MM/dd", TimeZone.getDefault, Locale.getDefault)
//    val parsePosition = new ParsePosition(0)
//    val date = parser.parse("3/23/61", parsePosition)
//    assertEquals(4, parsePosition.getIndex)
//    val calendar = Calendar.getInstance
//    calendar.setTime(date)
//    assertEquals(2, calendar.get(Calendar.MONTH))
//    assertEquals(23, calendar.get(Calendar.DATE))
//  }
//
//  @Test def testInputHasWrongCharacters() = {
//    val parser = new Nothing("MM-dd-yyy", TimeZone.getDefault, Locale.getDefault)
//    val parsePosition = new ParsePosition(0)
//    assertNull(parser.parse("03/23/1961", parsePosition))
//    assertEquals(2, parsePosition.getErrorIndex)
//  }
//
//  @Test def testInputHasLessCharacters() = {
//    val parser = new Nothing("MM/dd/yyy", TimeZone.getDefault, Locale.getDefault)
//    val parsePosition = new ParsePosition(0)
//    assertNull(parser.parse("03/23", parsePosition))
//    assertEquals(5, parsePosition.getErrorIndex)
//  }
//
//  @Test def testInputHasWrongTimeZone() = {
//    val parser = new Nothing("mm:ss z", FastDateParser_MoreOrLessTest.NEW_YORK, Locale.US)
//    val input = "11:23 Pacific Standard Time"
//    val parsePosition = new ParsePosition(0)
//    assertNotNull(parser.parse(input, parsePosition))
//    assertEquals(input.length, parsePosition.getIndex)
//    parsePosition.setIndex(0)
//    assertNull(parser.parse("11:23 Pacific Standard ", parsePosition))
//    assertEquals(6, parsePosition.getErrorIndex)
//  }
//
//  @Test def testInputHasWrongDay() = {
//    val parser = new Nothing("EEEE, MM/dd/yyy", FastDateParser_MoreOrLessTest.NEW_YORK, Locale.US)
//    val input = "Thursday, 03/23/61"
//    val parsePosition = new ParsePosition(0)
//    assertNotNull(parser.parse(input, parsePosition))
//    assertEquals(input.length, parsePosition.getIndex)
//    parsePosition.setIndex(0)
//    assertNull(parser.parse("Thorsday, 03/23/61", parsePosition))
//    assertEquals(0, parsePosition.getErrorIndex)
//  }
//}
