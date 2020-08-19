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

//package org.apache.commons.lang3
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  *
//  */
//class EnumUtilsTest extends JUnitSuite {
//  @Test def testConstructable() = { // enforce public constructor
//    new Nothing
//  }
//
//  @Test def test_getEnumMap() = {
//    val test = EnumUtils.getEnumMap(classOf[Traffic])
//    assertEquals("{RED=RED, AMBER=AMBER, GREEN=GREEN}", test.toString, "getEnumMap not created correctly")
//    assertEquals(3, test.size)
//    assertTrue(test.containsKey("RED"))
//    assertEquals(Traffic.RED, test.get("RED"))
//    assertTrue(test.containsKey("AMBER"))
//    assertEquals(Traffic.AMBER, test.get("AMBER"))
//    assertTrue(test.containsKey("GREEN"))
//    assertEquals(Traffic.GREEN, test.get("GREEN"))
//    assertFalse(test.containsKey("PURPLE"))
//  }
//
//  @Test def test_getEnumList() = {
//    val test = EnumUtils.getEnumList(classOf[Traffic])
//    assertEquals(3, test.size)
//    assertEquals(Traffic.RED, test.get(0))
//    assertEquals(Traffic.AMBER, test.get(1))
//    assertEquals(Traffic.GREEN, test.get(2))
//  }
//
//  @Test def test_isValidEnum() = {
//    assertTrue(EnumUtils.isValidEnum(classOf[Traffic], "RED"))
//    assertTrue(EnumUtils.isValidEnum(classOf[Traffic], "AMBER"))
//    assertTrue(EnumUtils.isValidEnum(classOf[Traffic], "GREEN"))
//    assertFalse(EnumUtils.isValidEnum(classOf[Traffic], "PURPLE"))
//    assertFalse(EnumUtils.isValidEnum(classOf[Traffic], null))
//  }
//
//  @Test def test_isValidEnum_nullClass() = assertThrows(classOf[NullPointerException], () => EnumUtils.isValidEnum(null, "PURPLE"))
//
//  @Test def test_isValidEnumIgnoreCase() = {
//    assertTrue(EnumUtils.isValidEnumIgnoreCase(classOf[Traffic], "red"))
//    assertTrue(EnumUtils.isValidEnumIgnoreCase(classOf[Traffic], "Amber"))
//    assertTrue(EnumUtils.isValidEnumIgnoreCase(classOf[Traffic], "grEEn"))
//    assertFalse(EnumUtils.isValidEnumIgnoreCase(classOf[Traffic], "purple"))
//    assertFalse(EnumUtils.isValidEnumIgnoreCase(classOf[Traffic], null))
//  }
//
//  @Test def test_isValidEnumIgnoreCase_nullClass() = assertThrows(classOf[NullPointerException], () => EnumUtils.isValidEnumIgnoreCase(null, "PURPLE"))
//
//  @Test def test_getEnum() = {
//    assertEquals(Traffic.RED, EnumUtils.getEnum(classOf[Traffic], "RED"))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnum(classOf[Traffic], "AMBER"))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnum(classOf[Traffic], "GREEN"))
//    assertNull(EnumUtils.getEnum(classOf[Traffic], "PURPLE"))
//    assertNull(EnumUtils.getEnum(classOf[Traffic], null))
//  }
//
//  @Test def test_getEnum_nonEnumClass() = {
//    val rawType = classOf[Any]
//    assertNull(EnumUtils.getEnum(rawType, "rawType"))
//  }
//
//  @Test def test_getEnum_nullClass() = assertThrows(classOf[NullPointerException], () => EnumUtils.getEnum(null.asInstanceOf[Class[Traffic]], "PURPLE"))
//
//  @Test def test_getEnum_defaultEnum() = {
//    assertEquals(Traffic.RED, EnumUtils.getEnum(classOf[Traffic], "RED", Traffic.AMBER))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnum(classOf[Traffic], "AMBER", Traffic.GREEN))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnum(classOf[Traffic], "GREEN", Traffic.RED))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnum(classOf[Traffic], "PURPLE", Traffic.AMBER))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnum(classOf[Traffic], "PURPLE", Traffic.GREEN))
//    assertEquals(Traffic.RED, EnumUtils.getEnum(classOf[Traffic], "PURPLE", Traffic.RED))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnum(classOf[Traffic], null, Traffic.AMBER))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnum(classOf[Traffic], null, Traffic.GREEN))
//    assertEquals(Traffic.RED, EnumUtils.getEnum(classOf[Traffic], null, Traffic.RED))
//    assertNull(EnumUtils.getEnum(classOf[Traffic], "PURPLE", null))
//  }
//
//  @Test def test_getEnumIgnoreCase() = {
//    assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "red"))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "Amber"))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "grEEn"))
//    assertNull(EnumUtils.getEnumIgnoreCase(classOf[Traffic], "purple"))
//    assertNull(EnumUtils.getEnumIgnoreCase(classOf[Traffic], null))
//  }
//
//  @Test def test_getEnumIgnoreCase_nonEnumClass() = {
//    val rawType = classOf[Any]
//    assertNull(EnumUtils.getEnumIgnoreCase(rawType, "rawType"))
//  }
//
//  @Test def test_getEnumIgnoreCase_nullClass() = assertThrows(classOf[NullPointerException], () => EnumUtils.getEnumIgnoreCase(null.asInstanceOf[Class[Traffic]], "PURPLE"))
//
//  @Test def test_getEnumIgnoreCase_defaultEnum() = {
//    assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "red", Traffic.AMBER))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "Amber", Traffic.GREEN))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "grEEn", Traffic.RED))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "PURPLE", Traffic.AMBER))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "purple", Traffic.GREEN))
//    assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(classOf[Traffic], "pUrPlE", Traffic.RED))
//    assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(classOf[Traffic], null, Traffic.AMBER))
//    assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(classOf[Traffic], null, Traffic.GREEN))
//    assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(classOf[Traffic], null, Traffic.RED))
//    assertNull(EnumUtils.getEnumIgnoreCase(classOf[Traffic], "PURPLE", null))
//  }
//
//  @Test def test_generateBitVector_nullClass() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVector(null, util.EnumSet.of(Traffic.RED)))
//
//  @Test def test_generateBitVectors_nullClass() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVectors(null, util.EnumSet.of(Traffic.RED)))
//
//  @Test def test_generateBitVector_nullIterable() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVector(classOf[Traffic], null.asInstanceOf[Iterable[Traffic]]))
//
//  @Test def test_generateBitVectors_nullIterable() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVectors(null, null.asInstanceOf[Iterable[Traffic]]))
//
//  @Test def test_generateBitVector_nullElement() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVector(classOf[Traffic], util.Arrays.asList(Traffic.RED, null)))
//
//  @Test def test_generateBitVectors_nullElement() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVectors(classOf[Traffic], util.Arrays.asList(Traffic.RED, null)))
//
//  @Test def test_generateBitVector_nullClassWithArray() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVector(null, Traffic.RED))
//
//  @Test def test_generateBitVectors_nullClassWithArray() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVectors(null, Traffic.RED))
//
//  @Test def test_generateBitVector_nullArray() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVector(classOf[Traffic], null.asInstanceOf[Array[Traffic]]))
//
//  @Test def test_generateBitVectors_nullArray() = assertThrows(classOf[NullPointerException], () => EnumUtils.generateBitVectors(classOf[Traffic], null.asInstanceOf[Array[Traffic]]))
//
//  @Test def test_generateBitVector_nullArrayElement() = assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVector(classOf[Traffic], Traffic.RED, null))
//
//  @Test def test_generateBitVectors_nullArrayElement() = assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVectors(classOf[Traffic], Traffic.RED, null))
//
//  @Test def test_generateBitVector_longClass() = assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVector(classOf[TooMany], util.EnumSet.of(TooMany.A1)))
//
//  @Test def test_generateBitVector_longClassWithArray() = assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVector(classOf[TooMany], TooMany.A1))
//
//  @SuppressWarnings(Array("unchecked"))
//  @Test def test_generateBitVector_nonEnumClass() = {
//    @SuppressWarnings(Array("rawtypes")) val rawType = classOf[Any]
//    @SuppressWarnings(Array("rawtypes")) val rawList = new util.ArrayList[_]
//    assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVector(rawType, rawList))
//  }
//
//  @SuppressWarnings(Array("unchecked"))
//  @Test def test_generateBitVectors_nonEnumClass() = {
//    @SuppressWarnings(Array("rawtypes")) val rawType = classOf[Any]
//    @SuppressWarnings(Array("rawtypes")) val rawList = new util.ArrayList[_]
//    assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVectors(rawType, rawList))
//  }
//
//  @SuppressWarnings(Array("unchecked"))
//  @Test def test_generateBitVector_nonEnumClassWithArray() = {
//    @SuppressWarnings(Array("rawtypes")) val rawType = classOf[Any]
//    assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVector(rawType))
//  }
//
//  @SuppressWarnings(Array("unchecked"))
//  @Test def test_generateBitVectors_nonEnumClassWithArray() = {
//    @SuppressWarnings(Array("rawtypes")) val rawType = classOf[Any]
//    assertThrows(classOf[IllegalArgumentException], () => EnumUtils.generateBitVectors(rawType))
//  }
//
//  @Test def test_generateBitVector() = {
//    assertEquals(0L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.noneOf(classOf[Traffic])))
//    assertEquals(1L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.RED)))
//    assertEquals(2L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.AMBER)))
//    assertEquals(4L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.GREEN)))
//    assertEquals(3L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.RED, Traffic.AMBER)))
//    assertEquals(5L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.RED, Traffic.GREEN)))
//    assertEquals(6L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.AMBER, Traffic.GREEN)))
//    assertEquals(7L, EnumUtils.generateBitVector(classOf[Traffic], util.EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)))
//    // 64 values Enum (to test whether no int<->long jdk conversion issue exists)
//    assertEquals(1L << 31, EnumUtils.generateBitVector(classOf[Enum64], util.EnumSet.of(Enum64.A31)))
//    assertEquals(1L << 32, EnumUtils.generateBitVector(classOf[Enum64], util.EnumSet.of(Enum64.A32)))
//    assertEquals(1L << 63, EnumUtils.generateBitVector(classOf[Enum64], util.EnumSet.of(Enum64.A63)))
//    assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(classOf[Enum64], util.EnumSet.of(Enum64.A63)))
//  }
//
//  @Test def test_generateBitVectors() = {
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.noneOf(classOf[Traffic])), 0L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.RED)), 1L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.AMBER)), 2L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.GREEN)), 4L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.RED, Traffic.AMBER)), 3L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.RED, Traffic.GREEN)), 5L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.AMBER, Traffic.GREEN)), 6L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], util.EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)), 7L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], util.EnumSet.of(Enum64.A31)), 1L << 31)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], util.EnumSet.of(Enum64.A32)), 1L << 32)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], util.EnumSet.of(Enum64.A63)), 1L << 63)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], util.EnumSet.of(Enum64.A63)), Long.MIN_VALUE)
//    // More than 64 values Enum
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[TooMany], util.EnumSet.of(TooMany.M2)), 1L, 0L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[TooMany], util.EnumSet.of(TooMany.L2, TooMany.M2)), 1L, 1L << 63)
//  }
//
//  @Test def test_generateBitVectorFromArray() = {
//    assertEquals(0L, EnumUtils.generateBitVector(classOf[Traffic]))
//    assertEquals(1L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.RED))
//    assertEquals(2L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.AMBER))
//    assertEquals(4L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.GREEN))
//    assertEquals(3L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.RED, Traffic.AMBER))
//    assertEquals(5L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.RED, Traffic.GREEN))
//    assertEquals(6L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.AMBER, Traffic.GREEN))
//    assertEquals(7L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.RED, Traffic.AMBER, Traffic.GREEN))
//    //gracefully handles duplicates:
//    assertEquals(7L, EnumUtils.generateBitVector(classOf[Traffic], Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN))
//    assertEquals(1L << 31, EnumUtils.generateBitVector(classOf[Enum64], Enum64.A31))
//    assertEquals(1L << 32, EnumUtils.generateBitVector(classOf[Enum64], Enum64.A32))
//    assertEquals(1L << 63, EnumUtils.generateBitVector(classOf[Enum64], Enum64.A63))
//    assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(classOf[Enum64], Enum64.A63))
//  }
//
//  @Test def test_generateBitVectorsFromArray() = {
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic]), 0L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.RED), 1L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.AMBER), 2L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.GREEN), 4L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.RED, Traffic.AMBER), 3L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.RED, Traffic.GREEN), 5L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.AMBER, Traffic.GREEN), 6L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Traffic], Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN), 7L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], Enum64.A31), 1L << 31)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], Enum64.A32), 1L << 32)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], Enum64.A63), 1L << 63)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[Enum64], Enum64.A63), Long.MIN_VALUE)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[TooMany], TooMany.M2), 1L, 0L)
//    assertArrayEquals(EnumUtils.generateBitVectors(classOf[TooMany], TooMany.L2, TooMany.M2), 1L, 1L << 63)
//  }
//
//  private def assertArrayEquals(actual: Array[Long], expected: Long*) = Assertions.assertArrayEquals(expected, actual)
//
//  @Test def test_processBitVector_nullClass() = {
//    val empty = null
//    assertThrows(classOf[NullPointerException], () => EnumUtils.processBitVector(empty, 0L))
//  }
//
//  @Test def test_processBitVectors_nullClass() = {
//    val empty = null
//    assertThrows(classOf[NullPointerException], () => EnumUtils.processBitVectors(empty, 0L))
//  }
//
//  @Test def test_processBitVector() = {
//    assertEquals(util.EnumSet.noneOf(classOf[Traffic]), EnumUtils.processBitVector(classOf[Traffic], 0L))
//    assertEquals(util.EnumSet.of(Traffic.RED), EnumUtils.processBitVector(classOf[Traffic], 1L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER), EnumUtils.processBitVector(classOf[Traffic], 2L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVector(classOf[Traffic], 3L))
//    assertEquals(util.EnumSet.of(Traffic.GREEN), EnumUtils.processBitVector(classOf[Traffic], 4L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVector(classOf[Traffic], 5L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(classOf[Traffic], 6L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(classOf[Traffic], 7L))
//    assertEquals(util.EnumSet.of(Enum64.A31), EnumUtils.processBitVector(classOf[Enum64], 1L << 31))
//    assertEquals(util.EnumSet.of(Enum64.A32), EnumUtils.processBitVector(classOf[Enum64], 1L << 32))
//    assertEquals(util.EnumSet.of(Enum64.A63), EnumUtils.processBitVector(classOf[Enum64], 1L << 63))
//    assertEquals(util.EnumSet.of(Enum64.A63), EnumUtils.processBitVector(classOf[Enum64], Long.MIN_VALUE))
//  }
//
//  @Test def test_processBitVectors() = {
//    assertEquals(util.EnumSet.noneOf(classOf[Traffic]), EnumUtils.processBitVectors(classOf[Traffic], 0L))
//    assertEquals(util.EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(classOf[Traffic], 1L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(classOf[Traffic], 2L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(classOf[Traffic], 3L))
//    assertEquals(util.EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 4L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 5L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 6L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 7L))
//    assertEquals(util.EnumSet.noneOf(classOf[Traffic]), EnumUtils.processBitVectors(classOf[Traffic], 0L, 0L))
//    assertEquals(util.EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(classOf[Traffic], 0L, 1L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(classOf[Traffic], 0L, 2L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(classOf[Traffic], 0L, 3L))
//    assertEquals(util.EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 0L, 4L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 0L, 5L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 0L, 6L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 0L, 7L))
//    // demonstrate tolerance of irrelevant high-order digits:
//    assertEquals(util.EnumSet.noneOf(classOf[Traffic]), EnumUtils.processBitVectors(classOf[Traffic], 666L, 0L))
//    assertEquals(util.EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(classOf[Traffic], 666L, 1L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(classOf[Traffic], 666L, 2L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(classOf[Traffic], 666L, 3L))
//    assertEquals(util.EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 666L, 4L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 666L, 5L))
//    assertEquals(util.EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 666L, 6L))
//    assertEquals(util.EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(classOf[Traffic], 666L, 7L))
//    assertEquals(util.EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(classOf[Enum64], 1L << 31))
//    assertEquals(util.EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(classOf[Enum64], 1L << 32))
//    assertEquals(util.EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(classOf[Enum64], 1L << 63))
//    assertEquals(util.EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(classOf[Enum64], Long.MIN_VALUE))
//  }
//
//  @Test def test_processBitVector_longClass() = assertThrows(classOf[IllegalArgumentException], () => EnumUtils.processBitVector(classOf[TooMany], 0L))
//
//  @Test def test_processBitVectors_longClass() = {
//    assertEquals(util.EnumSet.noneOf(classOf[TooMany]), EnumUtils.processBitVectors(classOf[TooMany], 0L))
//    assertEquals(util.EnumSet.of(TooMany.A), EnumUtils.processBitVectors(classOf[TooMany], 1L))
//    assertEquals(util.EnumSet.of(TooMany.B), EnumUtils.processBitVectors(classOf[TooMany], 2L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(classOf[TooMany], 3L))
//    assertEquals(util.EnumSet.of(TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 4L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 5L))
//    assertEquals(util.EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 6L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 7L))
//    assertEquals(util.EnumSet.noneOf(classOf[TooMany]), EnumUtils.processBitVectors(classOf[TooMany], 0L, 0L))
//    assertEquals(util.EnumSet.of(TooMany.A), EnumUtils.processBitVectors(classOf[TooMany], 0L, 1L))
//    assertEquals(util.EnumSet.of(TooMany.B), EnumUtils.processBitVectors(classOf[TooMany], 0L, 2L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(classOf[TooMany], 0L, 3L))
//    assertEquals(util.EnumSet.of(TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 0L, 4L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 0L, 5L))
//    assertEquals(util.EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 0L, 6L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 0L, 7L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(classOf[TooMany], 0L, 7L))
//    assertEquals(util.EnumSet.of(TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 0L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 1L))
//    assertEquals(util.EnumSet.of(TooMany.B, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 2L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 3L))
//    assertEquals(util.EnumSet.of(TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 4L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 5L))
//    assertEquals(util.EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 6L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 7L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 1L, 7L))
//    assertEquals(util.EnumSet.of(TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 0L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 1L))
//    assertEquals(util.EnumSet.of(TooMany.B, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 2L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 3L))
//    assertEquals(util.EnumSet.of(TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 4L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 5L))
//    assertEquals(util.EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 6L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 7L))
//    assertEquals(util.EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(classOf[TooMany], 9L, 7L))
//  }
//}
//
//object Traffic extends Enumeration {
//  type Traffic = Value
//  val RED, AMBER, GREEN = Value
//}
//
//object Enum64 extends Enumeration {
//  type Enum64 = Value
//  val A00, A01, A02, A03, A04, A05, A06, A07, A08, A09, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, A23, A24, A25, A26, A27, A28, A29, A30, A31, A32, A33, A34, A35, A36, A37, A38, A39, A40, A41, A42, A43, A44, A45, A46, A47, A48, A49, A50, A51, A52, A53, A54, A55, A56, A57, A58, A59, A60, A61, A62, A63 = Value
//}
//
//object TooMany extends Enumeration {
//  type TooMany = Value
//  val A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, A1, B1, C1, D1, E1, F1, G1, H1, I1, J1, K1, L1, M1, N1, O1, P1, Q1, R1, S1, T1, U1, V1, W1, X1, Y1, Z1, A2, B2, C2, D2, E2, F2, G2, H2, I2, J2, K2, L2, M2 = Value
//}
