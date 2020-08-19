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
//import org.junit.Assert.assertNotEquals
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util.Comparator
//import org.junit.{Before, Test}
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * <p>
//  * Tests the methods in the {@link org.apache.commons.lang3.Range} class.
//  * </p>
//  */
//@SuppressWarnings(Array("boxing"))
//class RangeTest extends JUnitSuite {
//  private var byteRange = null
//  private var byteRange2 = null
//  private var byteRange3 = null
//  private var doubleRange = null
//  private var floatRange = null
//  private var intRange = null
//  private var longRange = null
//
//  @Before def setUp() = {
//    byteRange = Range.between(0.toByte, 5.toByte)
//    byteRange2 = Range.between(0.toByte, 5.toByte)
//    byteRange3 = Range.between(0.toByte, 10.toByte)
//    intRange = Range.between(10, 20)
//    longRange = Range.between(10L, 20L)
//    floatRange = Range.between(10.toFloat, 20.toFloat)
//    doubleRange = Range.between(10.toDouble, 20.toDouble)
//  }
//
//  @Test def testBetweenWithCompare() = { // all integers are equal
//    val c = (o1: Integer, o2: Integer) => 0
//    val lengthComp = Comparator.comparingInt(String.length)
//    var rb = Range.between(-10, 20)
//    assertFalse(rb.contains(null), "should not contain null")
//    assertTrue(rb.contains(10), "should contain 10")
//    assertTrue(rb.contains(-10), "should contain -10")
//    assertFalse(rb.contains(21), "should not contain 21")
//    assertFalse(rb.contains(-11), "should not contain -11")
//    rb = Range.between(-10, 20, c)
//    assertFalse(rb.contains(null), "should not contain null")
//    assertTrue(rb.contains(10), "should contain 10")
//    assertTrue(rb.contains(-10), "should contain -10")
//    assertTrue(rb.contains(21), "should contain 21")
//    assertTrue(rb.contains(-11), "should contain -11")
//    var rbstr = Range.between("house", "i")
//    assertFalse(rbstr.contains(null), "should not contain null")
//    assertTrue(rbstr.contains("house"), "should contain house")
//    assertTrue(rbstr.contains("i"), "should contain i")
//    assertFalse(rbstr.contains("hose"), "should not contain hose")
//    assertFalse(rbstr.contains("ice"), "should not contain ice")
//    rbstr = Range.between("house", "i", lengthComp)
//    assertFalse(rbstr.contains(null), "should not contain null")
//    assertTrue(rbstr.contains("house"), "should contain house")
//    assertTrue(rbstr.contains("i"), "should contain i")
//    assertFalse(rbstr.contains("houses"), "should not contain houses")
//    assertFalse(rbstr.contains(""), "should not contain ''")
//  }
//
//  // -----------------------------------------------------------------------
//  @SuppressWarnings(Array(Array("rawtypes", "unchecked")))
//  @Test def testComparableConstructors() = {
//    val c = (other: Any) => 1
//    val r1 = Range.is(c)
//    val r2 = Range.between(c, c)
//    assertTrue(r1.isNaturalOrdering)
//    assertTrue(r2.isNaturalOrdering)
//  }
//
//  @Test def testContains() = {
//    assertFalse(intRange.contains(null))
//    assertFalse(intRange.contains(5))
//    assertTrue(intRange.contains(10))
//    assertTrue(intRange.contains(15))
//    assertTrue(intRange.contains(20))
//    assertFalse(intRange.contains(25))
//  }
//
//  @Test def testContainsRange() = { // null handling
//    assertFalse(intRange.containsRange(null))
//    // easy inside range
//    assertTrue(intRange.containsRange(Range.between(12, 18)))
//    // outside range on each side
//    assertFalse(intRange.containsRange(Range.between(32, 45)))
//    assertFalse(intRange.containsRange(Range.between(2, 8)))
//    // equals range
//    assertTrue(intRange.containsRange(Range.between(10, 20)))
//    // overlaps
//    assertFalse(intRange.containsRange(Range.between(9, 14)))
//    assertFalse(intRange.containsRange(Range.between(16, 21)))
//    // touches lower boundary
//    assertTrue(intRange.containsRange(Range.between(10, 19)))
//    assertFalse(intRange.containsRange(Range.between(10, 21)))
//    // touches upper boundary
//    assertTrue(intRange.containsRange(Range.between(11, 20)))
//    assertFalse(intRange.containsRange(Range.between(9, 20)))
//    // negative
//    assertFalse(intRange.containsRange(Range.between(-11, -18)))
//  }
//
//  @Test def testElementCompareTo() = {
//    assertThrows(classOf[NullPointerException], () => intRange.elementCompareTo(null))
//    assertEquals(-1, intRange.elementCompareTo(5))
//    assertEquals(0, intRange.elementCompareTo(10))
//    assertEquals(0, intRange.elementCompareTo(15))
//    assertEquals(0, intRange.elementCompareTo(20))
//    assertEquals(1, intRange.elementCompareTo(25))
//  }
//
//  @Test def testEqualsObject() = {
//    assertEquals(byteRange, byteRange)
//    assertEquals(byteRange, byteRange2)
//    assertEquals(byteRange2, byteRange2)
//    assertEquals(byteRange, byteRange)
//    assertEquals(byteRange2, byteRange2)
//    assertEquals(byteRange3, byteRange3)
//    assertNotEquals(byteRange2, byteRange3)
//    assertNotEquals(null, byteRange2)
//    assertNotEquals("Ni!", byteRange2)
//  }
//
//  @Test def testFit() = {
//    assertEquals(intRange.getMinimum, intRange.fit(Integer.MIN_VALUE))
//    assertEquals(intRange.getMinimum, intRange.fit(intRange.getMinimum))
//    assertEquals(intRange.getMaximum, intRange.fit(Integer.MAX_VALUE))
//    assertEquals(intRange.getMaximum, intRange.fit(intRange.getMaximum))
//    assertEquals(15, intRange.fit(15))
//  }
//
//  @Test def testFitNull() = assertThrows(classOf[NullPointerException], () => {
//    def foo() =
//      intRange.fit(null)
//
//    foo()
//  })
//
//  @Test def testGetMaximum() = {
//    assertEquals(20, intRange.getMaximum.asInstanceOf[Int])
//    assertEquals(20L, longRange.getMaximum.asInstanceOf[Long])
//    assertEquals(20f, floatRange.getMaximum, 0.00001f)
//    assertEquals(20d, doubleRange.getMaximum, 0.00001d)
//  }
//
//  @Test def testGetMinimum() = {
//    assertEquals(10, intRange.getMinimum.asInstanceOf[Int])
//    assertEquals(10L, longRange.getMinimum.asInstanceOf[Long])
//    assertEquals(10f, floatRange.getMinimum, 0.00001f)
//    assertEquals(10d, doubleRange.getMinimum, 0.00001d)
//  }
//
//  @Test def testHashCode() = {
//    assertEquals(byteRange.hashCode, byteRange2.hashCode)
//    assertNotEquals(byteRange.hashCode, byteRange3.hashCode)
//    assertEquals(intRange.hashCode, intRange.hashCode)
//    assertTrue(intRange.hashCode ne 0)
//  }
//
//  @Test def testIntersectionWith() = {
//    assertSame(intRange, intRange.intersectionWith(intRange))
//    assertSame(byteRange, byteRange.intersectionWith(byteRange))
//    assertSame(longRange, longRange.intersectionWith(longRange))
//    assertSame(floatRange, floatRange.intersectionWith(floatRange))
//    assertSame(doubleRange, doubleRange.intersectionWith(doubleRange))
//    assertEquals(Range.between(10, 15), intRange.intersectionWith(Range.between(5, 15)))
//  }
//
//  @Test def testIntersectionWithNonOverlapping() = assertThrows(classOf[IllegalArgumentException], () => intRange.intersectionWith(Range.between(0, 9)))
//
//  @Test def testIntersectionWithNull() = assertThrows(classOf[IllegalArgumentException], () => intRange.intersectionWith(null))
//
//  @Test def testIsAfter() = {
//    assertFalse(intRange.isAfter(null))
//    assertTrue(intRange.isAfter(5))
//    assertFalse(intRange.isAfter(10))
//    assertFalse(intRange.isAfter(15))
//    assertFalse(intRange.isAfter(20))
//    assertFalse(intRange.isAfter(25))
//  }
//
//  @Test def testIsAfterRange() = {
//    assertFalse(intRange.isAfterRange(null))
//    assertTrue(intRange.isAfterRange(Range.between(5, 9)))
//    assertFalse(intRange.isAfterRange(Range.between(5, 10)))
//    assertFalse(intRange.isAfterRange(Range.between(5, 20)))
//    assertFalse(intRange.isAfterRange(Range.between(5, 25)))
//    assertFalse(intRange.isAfterRange(Range.between(15, 25)))
//    assertFalse(intRange.isAfterRange(Range.between(21, 25)))
//    assertFalse(intRange.isAfterRange(Range.between(10, 20)))
//  }
//
//  @Test def testIsBefore() = {
//    assertFalse(intRange.isBefore(null))
//    assertFalse(intRange.isBefore(5))
//    assertFalse(intRange.isBefore(10))
//    assertFalse(intRange.isBefore(15))
//    assertFalse(intRange.isBefore(20))
//    assertTrue(intRange.isBefore(25))
//  }
//
//  @Test def testIsBeforeRange() = {
//    assertFalse(intRange.isBeforeRange(null))
//    assertFalse(intRange.isBeforeRange(Range.between(5, 9)))
//    assertFalse(intRange.isBeforeRange(Range.between(5, 10)))
//    assertFalse(intRange.isBeforeRange(Range.between(5, 20)))
//    assertFalse(intRange.isBeforeRange(Range.between(5, 25)))
//    assertFalse(intRange.isBeforeRange(Range.between(15, 25)))
//    assertTrue(intRange.isBeforeRange(Range.between(21, 25)))
//    assertFalse(intRange.isBeforeRange(Range.between(10, 20)))
//  }
//
//  @Test def testIsEndedBy() = {
//    assertFalse(intRange.isEndedBy(null))
//    assertFalse(intRange.isEndedBy(5))
//    assertFalse(intRange.isEndedBy(10))
//    assertFalse(intRange.isEndedBy(15))
//    assertTrue(intRange.isEndedBy(20))
//    assertFalse(intRange.isEndedBy(25))
//  }
//
//  @Test def testIsOverlappedBy() = {
//    assertFalse(intRange.isOverlappedBy(null))
//    assertTrue(intRange.isOverlappedBy(Range.between(12, 18)))
//    assertFalse(intRange.isOverlappedBy(Range.between(32, 45)))
//    assertFalse(intRange.isOverlappedBy(Range.between(2, 8)))
//    assertTrue(intRange.isOverlappedBy(Range.between(10, 20)))
//    assertTrue(intRange.isOverlappedBy(Range.between(9, 14)))
//    assertTrue(intRange.isOverlappedBy(Range.between(16, 21)))
//    assertTrue(intRange.isOverlappedBy(Range.between(10, 19)))
//    assertTrue(intRange.isOverlappedBy(Range.between(10, 21)))
//    assertTrue(intRange.isOverlappedBy(Range.between(11, 20)))
//    assertTrue(intRange.isOverlappedBy(Range.between(9, 20)))
//    assertFalse(intRange.isOverlappedBy(Range.between(-11, -18)))
//  }
//
//  @Test def testIsStartedBy() = {
//    assertFalse(intRange.isStartedBy(null))
//    assertFalse(intRange.isStartedBy(5))
//    assertTrue(intRange.isStartedBy(10))
//    assertFalse(intRange.isStartedBy(15))
//    assertFalse(intRange.isStartedBy(20))
//    assertFalse(intRange.isStartedBy(25))
//  }
//
//  @Test def testIsWithCompare() = {
//    val c = (o1: Integer, o2: Integer) => 0
//    var ri = Range.is(10)
//    assertFalse(ri.contains(null), "should not contain null")
//    assertTrue(ri.contains(10), "should contain 10")
//    assertFalse(ri.contains(11), "should not contain 11")
//    ri = Range.is(10, c)
//    assertFalse(ri.contains(null), "should not contain null")
//    assertTrue(ri.contains(10), "should contain 10")
//    assertTrue(ri.contains(11), "should contain 11")
//  }
//
//  @Test def testRangeOfChars() = {
//    val chars = Range.between('a', 'z')
//    assertTrue(chars.contains('b'))
//    assertFalse(chars.contains('B'))
//  }
//
//  @Test def testSerializing() = SerializationUtils.clone(intRange)
//
//  @Test def testToString() = {
//    assertNotNull(byteRange.toString)
//    val str = intRange.toString
//    assertEquals("[10..20]", str)
//    assertEquals("[-20..-10]", Range.between(-20, -10).toString)
//  }
//
//  @Test def testToStringFormat() = {
//    val str = intRange.toString("From %1$s to %2$s")
//    assertEquals("From 10 to 20", str)
//  }
//}
