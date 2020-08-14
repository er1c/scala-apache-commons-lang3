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

import java.lang.{Long => JavaLong}
import org.apache.commons.lang3.TestHelpers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * JUnit tests.
  *
  * @see MutableLong
  */
class MutableLongTest extends JUnitSuite with TestHelpers {
  @Test def testConstructors(): Unit = {
    assertEquals(0, new MutableLong().longValue)
    assertEquals(1, new MutableLong(1).longValue)
    assertEquals(2, new MutableLong(JavaLong.valueOf(2)).longValue)
    assertEquals(3, new MutableLong(new MutableLong(3)).longValue)
    assertEquals(2, new MutableLong("2").longValue)
  }

  @Test def testConstructorNull(): Unit = {
    assertThrows[NullPointerException](new MutableLong(null.asInstanceOf[Number]))
    ()
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableLong(0)
    assertEquals(0, new MutableLong().longValue)
    assertEquals(JavaLong.valueOf(0), new MutableLong().getValue)
    mutNum.setValue(1)
    assertEquals(1, mutNum.longValue)
    assertEquals(JavaLong.valueOf(1), mutNum.getValue)
    mutNum.setValue(JavaLong.valueOf(2))
    assertEquals(2, mutNum.longValue)
    assertEquals(JavaLong.valueOf(2), mutNum.getValue)
    mutNum.setValue(new MutableLong(3))
    assertEquals(3, mutNum.longValue)
    assertEquals(JavaLong.valueOf(3), mutNum.getValue)
  }

  @Test def testSetNull(): Unit = {
    val mutNum = new MutableLong(0)
    assertThrows[NullPointerException](mutNum.setValue(null))
    ()
  }

  @Test def testEquals(): Unit = {
    val mutNumA = new MutableLong(0)
    val mutNumB = new MutableLong(0)
    val mutNumC = new MutableLong(1)
    assertEquals(mutNumA, mutNumA)
    assertEquals(mutNumA, mutNumB)
    assertEquals(mutNumB, mutNumA)
    assertEquals(mutNumB, mutNumB)
    assertNotEquals(mutNumA, mutNumC)
    assertNotEquals(mutNumB, mutNumC)
    assertEquals(mutNumC, mutNumC)
    assertNotEquals(null, mutNumA)
    assertNotEquals(mutNumA, JavaLong.valueOf(0))
    assertNotEquals("0", mutNumA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableLong(0)
    val mutNumB = new MutableLong(0)
    val mutNumC = new MutableLong(1)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertEquals(mutNumA.hashCode, JavaLong.valueOf(0).hashCode)
  }

  @Test def testCompareTo(): Unit = {
    val mutNum = new MutableLong(0)
    assertEquals(0, mutNum.compareTo(new MutableLong(0)))
    assertEquals(+1, mutNum.compareTo(new MutableLong(-1)))
    assertEquals(-1, mutNum.compareTo(new MutableLong(1)))
  }

  @Test def testCompareToNull(): Unit = {
    val mutNum = new MutableLong(0)
    assertThrows[NullPointerException](mutNum.compareTo(null))
    ()
  }

  @Test def testPrimitiveValues(): Unit = {
    val mutNum = new MutableLong(1L)
    assertEquals(1.0f, mutNum.floatValue, FloatDelta)
    assertEquals(1.0, mutNum.doubleValue, DoubleDelta)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(1, mutNum.intValue)
    assertEquals(1L, mutNum.longValue)
  }

  @Test def testToLong(): Unit = {
    assertEquals(JavaLong.valueOf(0L), new MutableLong(0L).toLong)
    assertEquals(JavaLong.valueOf(123L), new MutableLong(123L).toLong)
  }

  @Test def testIncrement(): Unit = {
    val mutNum = new MutableLong(1)
    mutNum.increment()
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testIncrementAndGet(): Unit = {
    val mutNum = new MutableLong(1L)
    val result = mutNum.incrementAndGet
    assertEquals(2, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndIncrement(): Unit = {
    val mutNum = new MutableLong(1L)
    val result = mutNum.getAndIncrement
    assertEquals(1, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testDecrement(): Unit = {
    val mutNum = new MutableLong(1)
    mutNum.decrement()
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testDecrementAndGet(): Unit = {
    val mutNum = new MutableLong(1L)
    val result = mutNum.decrementAndGet
    assertEquals(0, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testGetAndDecrement(): Unit = {
    val mutNum = new MutableLong(1L)
    val result = mutNum.getAndDecrement
    assertEquals(1, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testAddValuePrimitive(): Unit = {
    val mutNum = new MutableLong(1)
    mutNum.add(1)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testAddValueObject(): Unit = {
    val mutNum = new MutableLong(1)
    mutNum.add(JavaLong.valueOf(1))
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndAddValuePrimitive(): Unit = {
    val mutableLong = new MutableLong(0L)
    val result = mutableLong.getAndAdd(1L)
    assertEquals(0L, result)
    assertEquals(1L, mutableLong.longValue)
  }

  @Test def testGetAndAddValueObject(): Unit = {
    val mutableLong = new MutableLong(0L)
    val result = mutableLong.getAndAdd(JavaLong.valueOf(1L))
    assertEquals(0L, result)
    assertEquals(1L, mutableLong.longValue)
  }

  @Test def testAddAndGetValuePrimitive(): Unit = {
    val mutableLong = new MutableLong(0L)
    val result = mutableLong.addAndGet(1L)
    assertEquals(1L, result)
    assertEquals(1L, mutableLong.longValue)
  }

  @Test def testAddAndGetValueObject(): Unit = {
    val mutableLong = new MutableLong(0L)
    val result = mutableLong.addAndGet(JavaLong.valueOf(1L))
    assertEquals(1L, result)
    assertEquals(1L, mutableLong.longValue)
  }

  @Test def testSubtractValuePrimitive(): Unit = {
    val mutNum = new MutableLong(1)
    mutNum.subtract(1)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testSubtractValueObject(): Unit = {
    val mutNum = new MutableLong(1)
    mutNum.subtract(JavaLong.valueOf(1))
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testToString(): Unit = {
    assertEquals("0", new MutableLong(0).toString)
    assertEquals("10", new MutableLong(10).toString)
    assertEquals("-123", new MutableLong(-123).toString)
  }
}
