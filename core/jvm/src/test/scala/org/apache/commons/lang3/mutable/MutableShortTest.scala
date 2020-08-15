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

import java.lang.{Short => JavaShort}
import org.apache.commons.lang3.TestHelpers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * JUnit tests.
  *
  * @see MutableShort
  */
class MutableShortTest extends JUnitSuite with TestHelpers {
  @Test def testConstructors(): Unit = {
    assertEquals(0.toShort, new MutableShort().shortValue)
    assertEquals(1.toShort, new MutableShort(1.toShort).shortValue)
    assertEquals(2.toShort, new MutableShort(JavaShort.valueOf(2.toShort)).shortValue)
    assertEquals(3.toShort, new MutableShort(new MutableShort(3.toShort)).shortValue)
    assertEquals(2.toShort, new MutableShort("2").shortValue)
    assertThrows[NullPointerException](new MutableShort(null.asInstanceOf[Number]))
    ()
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableShort(0.toShort)
    assertEquals(0.toShort, new MutableShort().shortValue)
    assertEquals(JavaShort.valueOf(0.toShort), new MutableShort().getValue)
    mutNum.setValue(1.toShort)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(JavaShort.valueOf(1.toShort), mutNum.getValue)
    mutNum.setValue(JavaShort.valueOf(2.toShort))
    assertEquals(2.toShort, mutNum.shortValue)
    assertEquals(JavaShort.valueOf(2.toShort), mutNum.getValue)
    mutNum.setValue(new MutableShort(3.toShort))
    assertEquals(3.toShort, mutNum.shortValue)
    assertEquals(JavaShort.valueOf(3.toShort), mutNum.getValue)
    assertThrows[NullPointerException](mutNum.setValue(null))
    ()
  }

  @Test def testEquals(): Unit = {
    val mutNumA = new MutableShort(0.toShort)
    val mutNumB = new MutableShort(0.toShort)
    val mutNumC = new MutableShort(1.toShort)
    assertEquals(mutNumA, mutNumA)
    assertEquals(mutNumA, mutNumB)
    assertEquals(mutNumB, mutNumA)
    assertEquals(mutNumB, mutNumB)
    assertNotEquals(mutNumA, mutNumC)
    assertNotEquals(mutNumB, mutNumC)
    assertEquals(mutNumC, mutNumC)
    assertNotEquals(null, mutNumA)
    assertNotEquals(mutNumA, JavaShort.valueOf(0.toShort))
    assertNotEquals("0", mutNumA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableShort(0.toShort)
    val mutNumB = new MutableShort(0.toShort)
    val mutNumC = new MutableShort(1.toShort)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertEquals(mutNumA.hashCode, JavaShort.valueOf(0.toShort).hashCode)
  }

  @Test def testCompareTo(): Unit = {
    val mutNum = new MutableShort(0.toShort)
    assertEquals(0.toShort, mutNum.compareTo(new MutableShort(0.toShort)))
    assertEquals(+1.toShort, mutNum.compareTo(new MutableShort(-1.toShort)))
    assertEquals(-1.toShort, mutNum.compareTo(new MutableShort(1.toShort)))
    assertThrows[NullPointerException](mutNum.compareTo(null))
    ()
  }

  @Test def testPrimitiveValues(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    assertEquals(1.0f, mutNum.floatValue, FloatDelta)
    assertEquals(1.0, mutNum.doubleValue, DoubleDelta)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(1, mutNum.intValue)
    assertEquals(1L, mutNum.longValue)
  }

  @Test def testToShort(): Unit = {
    assertEquals(JavaShort.valueOf(0.toShort), new MutableShort(0.toShort).toShort)
    assertEquals(JavaShort.valueOf(123.toShort), new MutableShort(123.toShort).toShort)
  }

  @Test def testIncrement(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    mutNum.increment()
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testIncrementAndGet(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    val result = mutNum.incrementAndGet
    assertEquals(2, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndIncrement(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    val result = mutNum.getAndIncrement
    assertEquals(1, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testDecrement(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    mutNum.decrement()
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testDecrementAndGet(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    val result = mutNum.decrementAndGet
    assertEquals(0, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testGetAndDecrement(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    val result = mutNum.getAndDecrement
    assertEquals(1, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testAddValuePrimitive(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    mutNum.add(1.toShort)
    assertEquals(2.toShort, mutNum.shortValue)
  }

  @Test def testAddValueObject(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    mutNum.add(JavaShort.valueOf(1.toShort))
    assertEquals(2.toShort, mutNum.shortValue)
  }

  @Test def testGetAndAddValuePrimitive(): Unit = {
    val mutableShort = new MutableShort(0.toShort)
    val result = mutableShort.getAndAdd(1.toShort)
    assertEquals(0.toShort, result)
    assertEquals(1.toShort, mutableShort.shortValue)
  }

  @Test def testGetAndAddValueObject(): Unit = {
    val mutableShort = new MutableShort(0.toShort)
    val result = mutableShort.getAndAdd(JavaShort.valueOf(1.toShort))
    assertEquals(0.toShort, result)
    assertEquals(1.toShort, mutableShort.shortValue)
  }

  @Test def testAddAndGetValuePrimitive(): Unit = {
    val mutableShort = new MutableShort(0.toShort)
    val result = mutableShort.addAndGet(1.toShort)
    assertEquals(1.toShort, result)
    assertEquals(1.toShort, mutableShort.shortValue)
  }

  @Test def testAddAndGetValueObject(): Unit = {
    val mutableShort = new MutableShort(0.toShort)
    val result = mutableShort.addAndGet(JavaShort.valueOf(1.toShort))
    assertEquals(1.toShort, result)
    assertEquals(1.toShort, mutableShort.shortValue)
  }

  @Test def testSubtractValuePrimitive(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    mutNum.subtract(1.toShort)
    assertEquals(0.toShort, mutNum.shortValue)
  }

  @Test def testSubtractValueObject(): Unit = {
    val mutNum = new MutableShort(1.toShort)
    mutNum.subtract(JavaShort.valueOf(1.toShort))
    assertEquals(0.toShort, mutNum.shortValue)
  }

  @Test def testToString(): Unit = {
    assertEquals("0", new MutableShort(0.toShort).toString)
    assertEquals("10", new MutableShort(10.toShort).toString)
    assertEquals("-123", new MutableShort(-123.toShort).toString)
  }
}
