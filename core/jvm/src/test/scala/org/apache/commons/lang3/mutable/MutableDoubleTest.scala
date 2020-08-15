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

package org.apache.commons.lang3.mutable

import java.lang.{Double => JavaDouble}
import org.apache.commons.lang3.TestHelpers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * JUnit tests.
  *
  * @see MutableDouble
  */
class MutableDoubleTest extends JUnitSuite with TestHelpers {
  @Test def testConstructors(): Unit = {
    assertEquals(0d, new MutableDouble().doubleValue, 0.0001d)
    assertEquals(1d, new MutableDouble(1d).doubleValue, 0.0001d)
    assertEquals(2d, new MutableDouble(JavaDouble.valueOf(2d)).doubleValue, 0.0001d)
    assertEquals(3d, new MutableDouble(new MutableDouble(3d)).doubleValue, 0.0001d)
    assertEquals(2d, new MutableDouble("2.0").doubleValue, 0.0001d)
  }

  @Test def testConstructorNull(): Unit = {
    assertThrows[NullPointerException](new MutableDouble(null.asInstanceOf[Number]))
    ()
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableDouble(0d)
    assertEquals(0d, new MutableDouble().doubleValue, 0.0001d)
    assertEquals(JavaDouble.valueOf(0), new MutableDouble().getValue)
    mutNum.setValue(1)
    assertEquals(1d, mutNum.doubleValue, 0.0001d)
    assertEquals(JavaDouble.valueOf(1d), mutNum.getValue)
    mutNum.setValue(JavaDouble.valueOf(2d))
    assertEquals(2d, mutNum.doubleValue, 0.0001d)
    assertEquals(JavaDouble.valueOf(2d), mutNum.getValue)
    mutNum.setValue(new MutableDouble(3d))
    assertEquals(3d, mutNum.doubleValue, 0.0001d)
    assertEquals(JavaDouble.valueOf(3d), mutNum.getValue)
  }

  @Test def testSetNull(): Unit = {
    val mutNum = new MutableDouble(0d)
    assertThrows[NullPointerException](mutNum.setValue(null))
    ()
  }

  @Test def testNanInfinite(): Unit = {
    var mutNum = new MutableDouble(Double.NaN)
    assertTrue(mutNum.isNaN)
    mutNum = new MutableDouble(Double.PositiveInfinity)
    assertTrue(mutNum.isInfinite)
    mutNum = new MutableDouble(Double.NegativeInfinity)
    assertTrue(mutNum.isInfinite)
  }

  @Test def testEquals(): Unit = {
    val mutNumA = new MutableDouble(0d)
    val mutNumB = new MutableDouble(0d)
    val mutNumC = new MutableDouble(1d)
    assertEquals(mutNumA, mutNumA)
    assertEquals(mutNumA, mutNumB)
    assertEquals(mutNumB, mutNumA)
    assertEquals(mutNumB, mutNumB)
    assertNotEquals(mutNumA, mutNumC)
    assertNotEquals(mutNumB, mutNumC)
    assertEquals(mutNumC, mutNumC)
    assertNotEquals(null, mutNumA)
    assertNotEquals(mutNumA, JavaDouble.valueOf(0d))
    assertNotEquals("0", mutNumA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableDouble(0d)
    val mutNumB = new MutableDouble(0d)
    val mutNumC = new MutableDouble(1d)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertEquals(mutNumA.hashCode, JavaDouble.valueOf(0d).hashCode)
  }

  @Test def testCompareTo(): Unit = {
    val mutNum = new MutableDouble(0d)
    assertEquals(0, mutNum.compareTo(new MutableDouble(0d)))
    assertEquals(+1, mutNum.compareTo(new MutableDouble(-1d)))
    assertEquals(-1, mutNum.compareTo(new MutableDouble(1d)))
  }

  @Test def testCompareToNull(): Unit = {
    val mutNum = new MutableDouble(0d)
    assertThrows[NullPointerException](mutNum.compareTo(null))
    ()
  }

  @Test def testPrimitiveValues(): Unit = {
    val mutNum = new MutableDouble(1.7d)
    assertEquals(1.7f, mutNum.floatValue, FloatDelta)
    assertEquals(1.7d, mutNum.doubleValue, DoubleDelta)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(1, mutNum.intValue)
    assertEquals(1L, mutNum.longValue)
  }

  @Test def testToDouble(): Unit = {
    assertEquals(JavaDouble.valueOf(0d), new MutableDouble(0d).toDouble)
    assertEquals(JavaDouble.valueOf(12.3d), new MutableDouble(12.3d).toDouble)
  }

  @Test def testIncrement(): Unit = {
    val mutNum = new MutableDouble(1)
    mutNum.increment()
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testIncrementAndGet(): Unit = {
    val mutNum = new MutableDouble(1d)
    val result = mutNum.incrementAndGet
    assertEquals(2d, result, 0.01d)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndIncrement(): Unit = {
    val mutNum = new MutableDouble(1d)
    val result = mutNum.getAndIncrement
    assertEquals(1d, result, 0.01d)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testDecrement(): Unit = {
    val mutNum = new MutableDouble(1)
    mutNum.decrement()
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testDecrementAndGet(): Unit = {
    val mutNum = new MutableDouble(1d)
    val result = mutNum.decrementAndGet
    assertEquals(0d, result, 0.01d)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testGetAndDecrement(): Unit = {
    val mutNum = new MutableDouble(1d)
    val result = mutNum.getAndDecrement
    assertEquals(1d, result, 0.01d)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testAddValuePrimitive(): Unit = {
    val mutNum = new MutableDouble(1)
    mutNum.add(1.1d)
    assertEquals(2.1d, mutNum.doubleValue, 0.01d)
  }

  @Test def testAddValueObject(): Unit = {
    val mutNum = new MutableDouble(1)
    mutNum.add(JavaDouble.valueOf(1.1d))
    assertEquals(2.1d, mutNum.doubleValue, 0.01d)
  }

  @Test def testGetAndAddValuePrimitive(): Unit = {
    val mutableDouble = new MutableDouble(0.5d)
    val result = mutableDouble.getAndAdd(1d)
    assertEquals(0.5d, result, 0.01d)
    assertEquals(1.5d, mutableDouble.doubleValue, 0.01d)
  }

  @Test def testGetAndAddValueObject(): Unit = {
    val mutableDouble = new MutableDouble(0.5d)
    val result = mutableDouble.getAndAdd(JavaDouble.valueOf(2d))
    assertEquals(0.5d, result, 0.01d)
    assertEquals(2.5d, mutableDouble.doubleValue, 0.01d)
  }

  @Test def testAddAndGetValuePrimitive(): Unit = {
    val mutableDouble = new MutableDouble(10.5d)
    val result = mutableDouble.addAndGet(-0.5d)
    assertEquals(10d, result, 0.01d)
    assertEquals(10d, mutableDouble.doubleValue, 0.01d)
  }

  @Test def testAddAndGetValueObject(): Unit = {
    val mutableDouble = new MutableDouble(7.5d)
    val result = mutableDouble.addAndGet(JavaDouble.valueOf(-2.5d))
    assertEquals(5d, result, 0.01d)
    assertEquals(5d, mutableDouble.doubleValue, 0.01d)
  }

  @Test def testSubtractValuePrimitive(): Unit = {
    val mutNum = new MutableDouble(1)
    mutNum.subtract(0.9d)
    assertEquals(0.1d, mutNum.doubleValue, 0.01d)
  }

  @Test def testSubtractValueObject(): Unit = {
    val mutNum = new MutableDouble(1)
    mutNum.subtract(JavaDouble.valueOf(0.9d))
    assertEquals(0.1d, mutNum.doubleValue, 0.01d)
  }

  @Test def testToString(): Unit = {
    assertEquals("0.0", new MutableDouble(0d).toString)
    assertEquals("10.0", new MutableDouble(10d).toString)
    assertEquals("-123.0", new MutableDouble(-123d).toString)
  }
}
