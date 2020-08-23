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
//import org.junit.Assert.assertThrows
//import org.junit.Test
//
//
///**
//  * Tests for GmtTimeZone
//  */
//class GmtTimeZoneTest {
//  @Test def hoursOutOfRange() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(false, 24, 0))
//
//  @Test def hoursInRange() = assertEquals(23 * 60 * 60 * 1000, new Nothing(false, 23, 0).getRawOffset)
//
//  @Test def minutesOutOfRange() = assertThrows(classOf[IllegalArgumentException], () => new Nothing(false, 0, 60))
//
//  @Test def minutesInRange() = assertEquals(59 * 60 * 1000, new Nothing(false, 0, 59).getRawOffset)
//
//  @Test def getOffset() = assertEquals(0, new Nothing(false, 0, 0).getOffset(234304))
//
//  @Test def setRawOffset() = assertThrows(classOf[UnsupportedOperationException], () => new Nothing(false, 0, 0).setRawOffset(0))
//
//  @Test def getRawOffset() = assertEquals(0, new Nothing(false, 0, 0).getRawOffset)
//
//  @Test def getID() = {
//    assertEquals("GMT+00:00", new Nothing(false, 0, 0).getID)
//    assertEquals("GMT+01:02", new Nothing(false, 1, 2).getID)
//    assertEquals("GMT+11:22", new Nothing(false, 11, 22).getID)
//    assertEquals("GMT-01:02", new Nothing(true, 1, 2).getID)
//    assertEquals("GMT-11:22", new Nothing(true, 11, 22).getID)
//  }
//
//  @Test def useDaylightTime() = assertFalse(new Nothing(false, 0, 0).useDaylightTime)
//
//  @Test def inDaylightTime() = assertFalse(new Nothing(false, 0, 0).useDaylightTime)
//
//  @Test def testToString() = assertEquals("[GmtTimeZone id=\"GMT-12:00\",offset=-43200000]", new Nothing(true, 12, 0).toString)
//
//  @Test def testGetOffset() = assertEquals(-(6 * 60 + 30) * 60 * 1000, new Nothing(true, 6, 30).getOffset(1, 1, 1, 1, 1, 1))
//}
