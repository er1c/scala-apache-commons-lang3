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

package org.apache.commons.lang3

import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * Tests for {@link RandomUtils}
  */
object RandomUtilsTest {
  /**
    * For comparing doubles and floats
    */
  private val DELTA = 1e-5
}

class RandomUtilsTest {
//  @Test def testConstructor(): Unit = {
//    assertNotNull(new RandomUtils.type)
//    val cons = classOf[RandomUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[RandomUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[RandomUtils.type].getModifiers))
//  }

  @Test def testNextBytesNegative(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextBytes(-1))
    ()
  }

  @Test def testNextIntNegative(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextInt(-1, 1))
    ()
  }

  @Test def testNextLongNegative(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextLong(-1, 1))
    ()
  }

  @Test def testNextDoubleNegative(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextDouble(-1, 1))
    ()
  }

  @Test def testNextFloatNegative(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextFloat(-1, 1))
    ()
  }

  @Test def testNextIntLowerGreaterUpper(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextInt(2, 1))
    ()
  }

  @Test def testNextLongLowerGreaterUpper(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextLong(2, 1))
    ()
  }

  @Test def testNextDoubleLowerGreaterUpper(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextDouble(2, 1))
    ()
  }

  @Test def testNextFloatLowerGreaterUpper(): Unit = {
    assertThrows[IllegalArgumentException](RandomUtils.nextFloat(2, 1))
    ()
  }

  /**
    * Tests next boolean
    */
  @Test def testBoolean(): Unit = {
    val result = RandomUtils.nextBoolean
    assertTrue(result == true || result == false)
  }

  /**
    * Tests a zero byte array length.
    */
  @Test def testZeroLengthNextBytes(): Unit = assertArrayEquals(new Array[Byte](0), RandomUtils.nextBytes(0))

  /**
    * Tests random byte array.
    */
  @Test def testNextBytes(): Unit = {
    val result = RandomUtils.nextBytes(20)
    assertEquals(20, result.length)
  }

  /**
    * Test next int range with minimal range.
    */
  @Test def testNextIntMinimalRange() = assertEquals(42, RandomUtils.nextInt(42, 42))

  /**
    * Tests next int range.
    */
  @Test def testNextInt(): Unit = {
    val result = RandomUtils.nextInt(33, 42)
    assertTrue(result >= 33 && result < 42)
  }

  /**
    * Tests next int range, random result.
    */
  @Test def testNextIntRandomResult(): Unit = {
    val randomResult = RandomUtils.nextInt
    assertTrue(randomResult > 0)
    assertTrue(randomResult < Integer.MAX_VALUE)
  }

  /**
    * Test next double range with minimal range.
    */
  @Test def testNextDoubleMinimalRange(): Unit =
    assertEquals(42.1, RandomUtils.nextDouble(42.1, 42.1), RandomUtilsTest.DELTA)

  /**
    * Test next float range with minimal range.
    */
  @Test def testNextFloatMinimalRange(): Unit =
    assertEquals(42.1f, RandomUtils.nextFloat(42.1f, 42.1f), RandomUtilsTest.DELTA)

  /**
    * Tests next double range.
    */
  @Test def testNextDouble(): Unit = {
    val result = RandomUtils.nextDouble(33d, 42d)
    assertTrue(result >= 33d && result <= 42d)
  }

  /**
    * Tests next double range, random result.
    */
  @Test def testNextDoubleRandomResult(): Unit = {
    val randomResult = RandomUtils.nextDouble
    assertTrue(randomResult > 0)
    assertTrue(randomResult < Double.MaxValue)
  }

  /**
    * Tests next float range.
    */
  @Test def testNextFloat(): Unit = {
    val result = RandomUtils.nextFloat(33f, 42f)
    assertTrue(result >= 33f && result <= 42f)
  }

  /**
    * Tests next float range, random result.
    */
  @Test def testNextFloatRandomResult(): Unit = {
    val randomResult = RandomUtils.nextFloat
    assertTrue(randomResult > 0)
    assertTrue(randomResult < Float.MaxValue)
  }

  /**
    * Test next long range with minimal range.
    */
  @Test def testNextLongMinimalRange(): Unit = assertEquals(42L, RandomUtils.nextLong(42L, 42L))

  /**
    * Tests next long range.
    */
  @Test def testNextLong(): Unit = {
    val result = RandomUtils.nextLong(33L, 42L)
    assertTrue(result >= 33L && result < 42L)
  }

  /**
    * Tests next long range, random result.
    */
  @Test def testNextLongRandomResult(): Unit = {
    val randomResult = RandomUtils.nextLong
    assertTrue(randomResult > 0)
    assertTrue(randomResult < Long.MaxValue)
  }

  /**
    * Tests extreme range.
    */
  @Test def testExtremeRangeInt(): Unit = {
    val result = RandomUtils.nextInt(0, Integer.MAX_VALUE)
    assertTrue(result >= 0 && result < Integer.MAX_VALUE)
  }

  @Test def testExtremeRangeLong(): Unit = {
    val result = RandomUtils.nextLong(0, Long.MaxValue)
    assertTrue(result >= 0 && result < Long.MaxValue)
  }

  @Test def testExtremeRangeFloat(): Unit = {
    val result = RandomUtils.nextFloat(0, Float.MaxValue)
    assertTrue(result >= 0f && result <= Float.MaxValue)
  }

  @Test def testExtremeRangeDouble(): Unit = {
    val result = RandomUtils.nextDouble(0, Double.MaxValue)
    assertTrue(result >= 0 && result <= Double.MaxValue)
  }

  /**
    * Test a large value for long. A previous implementation using
    * {@link RandomUtils# nextDouble ( double, double)} could generate a value equal
    * to the upper limit.
    *
    * <pre>
    * return (long) nextDouble(startInclusive, endExclusive);
    * </pre>
    *
    * <p>See LANG-1592.</p>
    */
  @Test def testLargeValueRangeLong(): Unit = {
    val startInclusive = 12900000000001L
    val endExclusive = 12900000000016L
    // Note: The method using 'return (long) nextDouble(startInclusive, endExclusive)'
    // takes thousands of calls to generate an error. This size loop fails most
    // of the time with the previous method.
    val n = (endExclusive - startInclusive).toInt * 1000
    for (_ <- 0 until n) {
      assertNotEquals(endExclusive, RandomUtils.nextLong(startInclusive, endExclusive))
    }
  }
}
