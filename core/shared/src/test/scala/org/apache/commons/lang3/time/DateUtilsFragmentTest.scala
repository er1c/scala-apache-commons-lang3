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
//import org.junit.Assert.assertThrows
//import java.util.Calendar
//import java.util.Date
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
//object DateUtilsFragmentTest {
//  private val months = 7 // second final prime before 12
//  private val days = 23 // second final prime before 31 (and valid)
//  private val hours = 19 // second final prime before 24
//  private val minutes = 53 // second final prime before 60
//  private val seconds = 47 // third final prime before 60
//  private val millis = 991 // second final prime before 1000
//}
//
//class DateUtilsFragmentTest {
//  private var aDate = null
//  private var aCalendar = null
//
//  @BeforeEach def setUp() = {
//    aCalendar = Calendar.getInstance
//    aCalendar.set(2005, DateUtilsFragmentTest.months, DateUtilsFragmentTest.days, DateUtilsFragmentTest.hours, DateUtilsFragmentTest.minutes, DateUtilsFragmentTest.seconds)
//    aCalendar.set(Calendar.MILLISECOND, DateUtilsFragmentTest.millis)
//    aDate = aCalendar.getTime
//  }
//
//  @Test def testNullDate() = {
//    assertThrows(classOf[NullPointerException], () => DateUtils.getFragmentInMilliseconds(null.asInstanceOf[Date], Calendar.MILLISECOND))
//    assertThrows(classOf[NullPointerException], () => DateUtils.getFragmentInSeconds(null.asInstanceOf[Date], Calendar.MILLISECOND))
//    assertThrows(classOf[NullPointerException], () => DateUtils.getFragmentInMinutes(null.asInstanceOf[Date], Calendar.MILLISECOND))
//    assertThrows(classOf[NullPointerException], () => DateUtils.getFragmentInHours(null.asInstanceOf[Date], Calendar.MILLISECOND))
//    assertThrows(classOf[NullPointerException], () => DateUtils.getFragmentInDays(null.asInstanceOf[Date], Calendar.MILLISECOND))
//  }
//
//  @Test def testNullCalendar() = {
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInMilliseconds(null.asInstanceOf[Calendar], Calendar.MILLISECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInSeconds(null.asInstanceOf[Calendar], Calendar.MILLISECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInMinutes(null.asInstanceOf[Calendar], Calendar.MILLISECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInHours(null.asInstanceOf[Calendar], Calendar.MILLISECOND))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInDays(null.asInstanceOf[Calendar], Calendar.MILLISECOND))
//  }
//
//  @Test def testInvalidFragmentWithDate() = {
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInMilliseconds(aDate, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInSeconds(aDate, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInMinutes(aDate, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInHours(aDate, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInDays(aDate, 0))
//  }
//
//  @Test def testInvalidFragmentWithCalendar() = {
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInMilliseconds(aCalendar, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInSeconds(aCalendar, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInMinutes(aCalendar, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInHours(aCalendar, 0))
//    assertThrows(classOf[IllegalArgumentException], () => DateUtils.getFragmentInDays(aCalendar, 0))
//  }
//
//  @Test def testMillisecondFragmentInLargerUnitWithDate() = {
//    assertEquals(0, DateUtils.getFragmentInMilliseconds(aDate, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInSeconds(aDate, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInMinutes(aDate, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.MILLISECOND))
//  }
//
//  @Test def testMillisecondFragmentInLargerUnitWithCalendar() = {
//    assertEquals(0, DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInSeconds(aCalendar, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInMinutes(aCalendar, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.MILLISECOND))
//    assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.MILLISECOND))
//  }
//
//  @Test def testSecondFragmentInLargerUnitWithDate() = {
//    assertEquals(0, DateUtils.getFragmentInSeconds(aDate, Calendar.SECOND))
//    assertEquals(0, DateUtils.getFragmentInMinutes(aDate, Calendar.SECOND))
//    assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.SECOND))
//    assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.SECOND))
//  }
//
//  @Test def testSecondFragmentInLargerUnitWithCalendar() = {
//    assertEquals(0, DateUtils.getFragmentInSeconds(aCalendar, Calendar.SECOND))
//    assertEquals(0, DateUtils.getFragmentInMinutes(aCalendar, Calendar.SECOND))
//    assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.SECOND))
//    assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.SECOND))
//  }
//
//  @Test def testMinuteFragmentInLargerUnitWithDate() = {
//    assertEquals(0, DateUtils.getFragmentInMinutes(aDate, Calendar.MINUTE))
//    assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.MINUTE))
//    assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.MINUTE))
//  }
//
//  @Test def testMinuteFragmentInLargerUnitWithCalendar() = {
//    assertEquals(0, DateUtils.getFragmentInMinutes(aCalendar, Calendar.MINUTE))
//    assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.MINUTE))
//    assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.MINUTE))
//  }
//
//  @Test def testHourOfDayFragmentInLargerUnitWithDate() = {
//    assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.HOUR_OF_DAY))
//    assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.HOUR_OF_DAY))
//  }
//
//  @Test def testHourOfDayFragmentInLargerUnitWithCalendar() = {
//    assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.HOUR_OF_DAY))
//    assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.HOUR_OF_DAY))
//  }
//
//  @Test def testDayOfYearFragmentInLargerUnitWithDate() = assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.DAY_OF_YEAR))
//
//  @Test def testDayOfYearFragmentInLargerUnitWithCalendar() = assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.DAY_OF_YEAR))
//
//  @Test def testDateFragmentInLargerUnitWithDate() = assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.DATE))
//
//  @Test def testDateFragmentInLargerUnitWithCalendar() = assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.DATE))
//
//  //Calendar.SECOND as useful fragment
//  @Test def testMillisecondsOfSecondWithDate() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.SECOND)
//    assertEquals(DateUtilsFragmentTest.millis, testResult)
//  }
//
//  @Test def testMillisecondsOfSecondWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.SECOND)
//    assertEquals(DateUtilsFragmentTest.millis, testResult)
//    assertEquals(aCalendar.get(Calendar.MILLISECOND), testResult)
//  }
//
//  //Calendar.MINUTE as useful fragment
//  @Test def testMillisecondsOfMinuteWithDate() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.MINUTE)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND), testResult)
//  }
//
//  @Test def testMillisecondsOfMinuteWithCalender() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.MINUTE)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND), testResult)
//  }
//
//  @Test def testSecondsofMinuteWithDate() = {
//    val testResult = DateUtils.getFragmentInSeconds(aDate, Calendar.MINUTE)
//    assertEquals(DateUtilsFragmentTest.seconds, testResult)
//  }
//
//  @Test def testSecondsofMinuteWithCalendar() = {
//    val testResult = DateUtils.getFragmentInSeconds(aCalendar, Calendar.MINUTE)
//    assertEquals(DateUtilsFragmentTest.seconds, testResult)
//    assertEquals(aCalendar.get(Calendar.SECOND), testResult)
//  }
//
//  //Calendar.HOUR_OF_DAY as useful fragment
//  @Test def testMillisecondsOfHourWithDate() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.HOUR_OF_DAY)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE), testResult)
//  }
//
//  @Test def testMillisecondsOfHourWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.HOUR_OF_DAY)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE), testResult)
//  }
//
//  @Test def testSecondsofHourWithDate() = {
//    val testResult = DateUtils.getFragmentInSeconds(aDate, Calendar.HOUR_OF_DAY)
//    assertEquals(DateUtilsFragmentTest.seconds + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE / DateUtils.MILLIS_PER_SECOND), testResult)
//  }
//
//  @Test def testSecondsofHourWithCalendar() = {
//    val testResult = DateUtils.getFragmentInSeconds(aCalendar, Calendar.HOUR_OF_DAY)
//    assertEquals(DateUtilsFragmentTest.seconds + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE / DateUtils.MILLIS_PER_SECOND), testResult)
//  }
//
//  @Test def testMinutesOfHourWithDate() = {
//    val testResult = DateUtils.getFragmentInMinutes(aDate, Calendar.HOUR_OF_DAY)
//    assertEquals(DateUtilsFragmentTest.minutes, testResult)
//  }
//
//  @Test def testMinutesOfHourWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMinutes(aCalendar, Calendar.HOUR_OF_DAY)
//    assertEquals(DateUtilsFragmentTest.minutes, testResult)
//  }
//
//  //Calendar.DATE and Calendar.DAY_OF_YEAR as useful fragment
//  @Test def testMillisecondsOfDayWithDate() = {
//    var testresult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR)
//    assertEquals(expectedValue, testresult)
//    testresult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testresult)
//  }
//
//  @Test def testMillisecondsOfDayWithCalendar() = {
//    var testresult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR)
//    assertEquals(expectedValue, testresult)
//    testresult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testresult)
//  }
//
//  @Test def testSecondsOfDayWithDate() = {
//    var testresult = DateUtils.getFragmentInSeconds(aDate, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.seconds + ((DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR)) / DateUtils.MILLIS_PER_SECOND
//    assertEquals(expectedValue, testresult)
//    testresult = DateUtils.getFragmentInSeconds(aDate, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testresult)
//  }
//
//  @Test def testSecondsOfDayWithCalendar() = {
//    var testresult = DateUtils.getFragmentInSeconds(aCalendar, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.seconds + ((DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR)) / DateUtils.MILLIS_PER_SECOND
//    assertEquals(expectedValue, testresult)
//    testresult = DateUtils.getFragmentInSeconds(aCalendar, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testresult)
//  }
//
//  @Test def testMinutesOfDayWithDate() = {
//    var testResult = DateUtils.getFragmentInMinutes(aDate, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.minutes + DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR / DateUtils.MILLIS_PER_MINUTE
//    assertEquals(expectedValue, testResult)
//    testResult = DateUtils.getFragmentInMinutes(aDate, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testResult)
//  }
//
//  @Test def testMinutesOfDayWithCalendar() = {
//    var testResult = DateUtils.getFragmentInMinutes(aCalendar, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.minutes + DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR / DateUtils.MILLIS_PER_MINUTE
//    assertEquals(expectedValue, testResult)
//    testResult = DateUtils.getFragmentInMinutes(aCalendar, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testResult)
//  }
//
//  @Test def testHoursOfDayWithDate() = {
//    var testResult = DateUtils.getFragmentInHours(aDate, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.hours
//    assertEquals(expectedValue, testResult)
//    testResult = DateUtils.getFragmentInHours(aDate, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testResult)
//  }
//
//  @Test def testHoursOfDayWithCalendar() = {
//    var testResult = DateUtils.getFragmentInHours(aCalendar, Calendar.DATE)
//    val expectedValue = DateUtilsFragmentTest.hours
//    assertEquals(expectedValue, testResult)
//    testResult = DateUtils.getFragmentInHours(aCalendar, Calendar.DAY_OF_YEAR)
//    assertEquals(expectedValue, testResult)
//  }
//
//  //Calendar.MONTH as useful fragment
//  @Test def testMillisecondsOfMonthWithDate() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY), testResult)
//  }
//
//  @Test def testMillisecondsOfMonthWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY), testResult)
//  }
//
//  @Test def testSecondsOfMonthWithDate() = {
//    val testResult = DateUtils.getFragmentInSeconds(aDate, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.seconds + ((DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_SECOND, testResult)
//  }
//
//  @Test def testSecondsOfMonthWithCalendar() = {
//    val testResult = DateUtils.getFragmentInSeconds(aCalendar, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.seconds + ((DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_SECOND, testResult)
//  }
//
//  @Test def testMinutesOfMonthWithDate() = {
//    val testResult = DateUtils.getFragmentInMinutes(aDate, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.minutes + ((DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_MINUTE, testResult)
//  }
//
//  @Test def testMinutesOfMonthWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMinutes(aCalendar, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.minutes + ((DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_MINUTE, testResult)
//  }
//
//  @Test def testHoursOfMonthWithDate() = {
//    val testResult = DateUtils.getFragmentInHours(aDate, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.hours + (DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY / DateUtils.MILLIS_PER_HOUR, testResult)
//  }
//
//  @Test def testHoursOfMonthWithCalendar() = {
//    val testResult = DateUtils.getFragmentInHours(aCalendar, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.hours + (DateUtilsFragmentTest.days - 1) * DateUtils.MILLIS_PER_DAY / DateUtils.MILLIS_PER_HOUR, testResult)
//  }
//
//  //Calendar.YEAR as useful fragment
//  @Test def testMillisecondsOfYearWithDate() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aDate, Calendar.YEAR)
//    val cal = Calendar.getInstance
//    cal.setTime(aDate)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((cal.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY), testResult)
//  }
//
//  @Test def testMillisecondsOfYearWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.YEAR)
//    assertEquals(DateUtilsFragmentTest.millis + (DateUtilsFragmentTest.seconds * DateUtils.MILLIS_PER_SECOND) + (DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((aCalendar.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY), testResult)
//  }
//
//  @Test def testSecondsOfYearWithDate() = {
//    val testResult = DateUtils.getFragmentInSeconds(aDate, Calendar.YEAR)
//    val cal = Calendar.getInstance
//    cal.setTime(aDate)
//    assertEquals(DateUtilsFragmentTest.seconds + ((DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((cal.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_SECOND, testResult)
//  }
//
//  @Test def testSecondsOfYearWithCalendar() = {
//    val testResult = DateUtils.getFragmentInSeconds(aCalendar, Calendar.YEAR)
//    assertEquals(DateUtilsFragmentTest.seconds + ((DateUtilsFragmentTest.minutes * DateUtils.MILLIS_PER_MINUTE) + (DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((aCalendar.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_SECOND, testResult)
//  }
//
//  @Test def testMinutesOfYearWithDate() = {
//    val testResult = DateUtils.getFragmentInMinutes(aDate, Calendar.YEAR)
//    val cal = Calendar.getInstance
//    cal.setTime(aDate)
//    assertEquals(DateUtilsFragmentTest.minutes + ((DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((cal.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_MINUTE, testResult)
//  }
//
//  @Test def testMinutesOfYearWithCalendar() = {
//    val testResult = DateUtils.getFragmentInMinutes(aCalendar, Calendar.YEAR)
//    assertEquals(DateUtilsFragmentTest.minutes + ((DateUtilsFragmentTest.hours * DateUtils.MILLIS_PER_HOUR) + ((aCalendar.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY)) / DateUtils.MILLIS_PER_MINUTE, testResult)
//  }
//
//  @Test def testMinutesOfYearWithWrongOffsetBugWithCalendar() = {
//    val c = Calendar.getInstance
//    c.set(Calendar.MONTH, Calendar.JANUARY)
//    c.set(Calendar.DAY_OF_YEAR, 1)
//    c.set(Calendar.HOUR_OF_DAY, 0)
//    c.set(Calendar.MINUTE, 0)
//    c.set(Calendar.SECOND, 0)
//    c.set(Calendar.MILLISECOND, 0)
//    val testResult = DateUtils.getFragmentInMinutes(c, Calendar.YEAR)
//    assertEquals(0, testResult)
//  }
//
//  @Test def testHoursOfYearWithDate() = {
//    val testResult = DateUtils.getFragmentInHours(aDate, Calendar.YEAR)
//    val cal = Calendar.getInstance
//    cal.setTime(aDate)
//    assertEquals(DateUtilsFragmentTest.hours + (cal.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY / DateUtils.MILLIS_PER_HOUR, testResult)
//  }
//
//  @Test def testHoursOfYearWithCalendar() = {
//    val testResult = DateUtils.getFragmentInHours(aCalendar, Calendar.YEAR)
//    assertEquals(DateUtilsFragmentTest.hours + (aCalendar.get(Calendar.DAY_OF_YEAR) - 1) * DateUtils.MILLIS_PER_DAY / DateUtils.MILLIS_PER_HOUR, testResult)
//  }
//
//  @Test def testDaysOfMonthWithCalendar() = {
//    val testResult = DateUtils.getFragmentInDays(aCalendar, Calendar.MONTH)
//    assertEquals(DateUtilsFragmentTest.days, testResult)
//  }
//
//  @Test def testDaysOfMonthWithDate() = {
//    val testResult = DateUtils.getFragmentInDays(aDate, Calendar.MONTH)
//    val cal = Calendar.getInstance
//    cal.setTime(aDate)
//    assertEquals(cal.get(Calendar.DAY_OF_MONTH), testResult)
//  }
//
//  @Test def testDaysOfYearWithCalendar() = {
//    val testResult = DateUtils.getFragmentInDays(aCalendar, Calendar.YEAR)
//    assertEquals(aCalendar.get(Calendar.DAY_OF_YEAR), testResult)
//  }
//
//  @Test def testDaysOfYearWithDate() = {
//    val testResult = DateUtils.getFragmentInDays(aDate, Calendar.YEAR)
//    val cal = Calendar.getInstance
//    cal.setTime(aDate)
//    assertEquals(cal.get(Calendar.DAY_OF_YEAR), testResult)
//  }
//}
