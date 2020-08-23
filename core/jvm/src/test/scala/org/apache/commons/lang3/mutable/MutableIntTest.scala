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

import org.apache.commons.lang3.TestHelpers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * JUnit tests.
  *
  * @see MutableInt
  */
class MutableIntTest extends TestHelpers {
  @Test def testConstructors(): Unit = {
    assertEquals(0, new MutableInt().intValue)
    assertEquals(1, new MutableInt(1).intValue)
    assertEquals(2, new MutableInt(Integer.valueOf(2)).intValue)
    assertEquals(3, new MutableInt(new MutableLong(3)).intValue)
    assertEquals(2, new MutableInt("2").intValue)
  }

  @Test def testConstructorNull(): Unit = {
    assertThrows[NullPointerException](new MutableInt(null.asInstanceOf[Number]))
    ()
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableInt(0)
    assertEquals(0, new MutableInt().intValue)
    assertEquals(Integer.valueOf(0), new MutableInt().getValue)
    mutNum.setValue(1)
    assertEquals(1, mutNum.intValue)
    assertEquals(Integer.valueOf(1), mutNum.getValue)
    mutNum.setValue(Integer.valueOf(2))
    assertEquals(2, mutNum.intValue)
    assertEquals(Integer.valueOf(2), mutNum.getValue)
    mutNum.setValue(new MutableLong(3))
    assertEquals(3, mutNum.intValue)
    assertEquals(Integer.valueOf(3), mutNum.getValue)
  }

  @Test def testSetNull(): Unit = {
    val mutNum = new MutableInt(0)
    assertThrows[NullPointerException](mutNum.setValue(null))
    ()
  }

  @Test def testEquals(): Unit = {
    this.testEquals(new MutableInt(0), new MutableInt(0), new MutableInt(1))
    // Should Numbers be supported? GaryG July-21-2005.
    //this.testEquals(mutNumA, Integer.valueOf(0), mutNumC);
  }

  /**
    * @param numA must not be a 0 Integer; must not equal numC.
    * @param numB must equal numA; must not equal numC.
    * @param numC must not equal numA; must not equal numC.
    */
  private[mutable] def testEquals(numA: Number, numB: Number, numC: Number): Unit = {
    assertEquals(numA, numA)
    assertEquals(numA, numB)
    assertEquals(numB, numA)
    assertEquals(numB, numB)
    assertNotEquals(numA, numC)
    assertNotEquals(numB, numC)
    assertEquals(numC, numC)
    assertNotEquals(null, numA)
    assertNotEquals(numA, Integer.valueOf(0))
    assertNotEquals("0", numA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableInt(0)
    val mutNumB = new MutableInt(0)
    val mutNumC = new MutableInt(1)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertEquals(mutNumA.hashCode, Integer.valueOf(0).hashCode)
  }

  @Test def testCompareTo(): Unit = {
    val mutNum = new MutableInt(0)
    assertEquals(0, mutNum.compareTo(new MutableInt(0)))
    assertEquals(+1, mutNum.compareTo(new MutableInt(-1)))
    assertEquals(-1, mutNum.compareTo(new MutableInt(1)))
  }

  @Test def testCompareToNull(): Unit = {
    val mutNum = new MutableInt(0)
    assertThrows[NullPointerException](mutNum.compareTo(null))
    ()
  }

  @Test def testPrimitiveValues(): Unit = {
    val mutNum = new MutableInt(1)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(1.0f, mutNum.floatValue, FloatDelta)
    assertEquals(1.0, mutNum.doubleValue, DoubleDelta)
    assertEquals(1L, mutNum.longValue)
  }

  @Test def testToInteger(): Unit = {
    assertEquals(Integer.valueOf(0), new MutableInt(0).toInteger)
    assertEquals(Integer.valueOf(123), new MutableInt(123).toInteger)
  }

  @Test def testIncrement(): Unit = {
    val mutNum = new MutableInt(1)
    mutNum.increment()
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testIncrementAndGet(): Unit = {
    val mutNum = new MutableInt(1)
    val result = mutNum.incrementAndGet
    assertEquals(2, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndIncrement(): Unit = {
    val mutNum = new MutableInt(1)
    val result = mutNum.getAndIncrement
    assertEquals(1, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testDecrement(): Unit = {
    val mutNum = new MutableInt(1)
    mutNum.decrement()
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testDecrementAndGet(): Unit = {
    val mutNum = new MutableInt(1)
    val result = mutNum.decrementAndGet
    assertEquals(0, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testGetAndDecrement(): Unit = {
    val mutNum = new MutableInt(1)
    val result = mutNum.getAndDecrement
    assertEquals(1, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testAddValuePrimitive(): Unit = {
    val mutNum = new MutableInt(1)
    mutNum.add(1)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testAddValueObject(): Unit = {
    val mutNum = new MutableInt(1)
    mutNum.add(Integer.valueOf(1))
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndAddValuePrimitive(): Unit = {
    val mutableInteger = new MutableInt(0)
    val result = mutableInteger.getAndAdd(1)
    assertEquals(0, result)
    assertEquals(1, mutableInteger.intValue)
  }

  @Test def testGetAndAddValueObject(): Unit = {
    val mutableInteger = new MutableInt(0)
    val result = mutableInteger.getAndAdd(Integer.valueOf(1))
    assertEquals(0, result)
    assertEquals(1, mutableInteger.intValue)
  }

  @Test def testAddAndGetValuePrimitive(): Unit = {
    val mutableInteger = new MutableInt(0)
    val result = mutableInteger.addAndGet(1)
    assertEquals(1, result)
    assertEquals(1, mutableInteger.intValue)
  }

  @Test def testAddAndGetValueObject(): Unit = {
    val mutableInteger = new MutableInt(0)
    val result = mutableInteger.addAndGet(Integer.valueOf(1))
    assertEquals(1, result)
    assertEquals(1, mutableInteger.intValue)
  }

  @Test def testSubtractValuePrimitive(): Unit = {
    val mutNum = new MutableInt(1)
    mutNum.subtract(1)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testSubtractValueObject(): Unit = {
    val mutNum = new MutableInt(1)
    mutNum.subtract(Integer.valueOf(1))
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testToString(): Unit = {
    assertEquals("0", new MutableInt(0).toString)
    assertEquals("10", new MutableInt(10).toString)
    assertEquals("-123", new MutableInt(-123).toString)
  }
}
