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

import java.lang.{Float => JavaFloat}
import org.apache.commons.lang3.TestHelpers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * JUnit tests.
  *
  * @see MutableFloat
  */
class MutableFloatTest extends JUnitSuite with TestHelpers {
  @Test def testConstructors(): Unit = {
    assertEquals(0f, new MutableFloat().floatValue, 0.0001f)
    assertEquals(1f, new MutableFloat(1f).floatValue, 0.0001f)
    assertEquals(2f, new MutableFloat(JavaFloat.valueOf(2f)).floatValue, 0.0001f)
    assertEquals(3f, new MutableFloat(new MutableFloat(3f)).floatValue, 0.0001f)
    assertEquals(2f, new MutableFloat("2.0").floatValue, 0.0001f)
  }

  @Test def testConstructorNull(): Unit = {
    assertThrows[NullPointerException](new MutableFloat(null.asInstanceOf[Number]))
    ()
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableFloat(0f)
    assertEquals(0f, new MutableFloat().floatValue, 0.0001f)
    assertEquals(JavaFloat.valueOf(0), new MutableFloat().getValue)
    mutNum.setValue(1)
    assertEquals(1f, mutNum.floatValue, 0.0001f)
    assertEquals(JavaFloat.valueOf(1f), mutNum.getValue)
    mutNum.setValue(JavaFloat.valueOf(2f))
    assertEquals(2f, mutNum.floatValue, 0.0001f)
    assertEquals(JavaFloat.valueOf(2f), mutNum.getValue)
    mutNum.setValue(new MutableFloat(3f))
    assertEquals(3f, mutNum.floatValue, 0.0001f)
    assertEquals(JavaFloat.valueOf(3f), mutNum.getValue)
  }

  @Test def testSetNull(): Unit = {
    val mutNum = new MutableFloat(0f)
    assertThrows[NullPointerException](mutNum.setValue(null))
    ()
  }

  @Test def testNanInfinite(): Unit = {
    var mutNum = new MutableFloat(Float.NaN)
    assertTrue(mutNum.isNaN)
    mutNum = new MutableFloat(Float.PositiveInfinity)
    assertTrue(mutNum.isInfinite)
    mutNum = new MutableFloat(Float.NegativeInfinity)
    assertTrue(mutNum.isInfinite)
  }

  @Test def testEquals(): Unit = {
    val mutNumA = new MutableFloat(0f)
    val mutNumB = new MutableFloat(0f)
    val mutNumC = new MutableFloat(1f)
    assertEquals(mutNumA, mutNumA)
    assertEquals(mutNumA, mutNumB)
    assertEquals(mutNumB, mutNumA)
    assertEquals(mutNumB, mutNumB)
    assertNotEquals(mutNumA, mutNumC)
    assertNotEquals(mutNumB, mutNumC)
    assertEquals(mutNumC, mutNumC)
    assertNotEquals(null, mutNumA)
    assertNotEquals(mutNumA, JavaFloat.valueOf(0f))
    assertNotEquals("0", mutNumA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableFloat(0f)
    val mutNumB = new MutableFloat(0f)
    val mutNumC = new MutableFloat(1f)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertEquals(mutNumA.hashCode, JavaFloat.valueOf(0f).hashCode)
  }

  @Test def testCompareTo(): Unit = {
    val mutNum = new MutableFloat(0f)
    assertEquals(0, mutNum.compareTo(new MutableFloat(0f)))
    assertEquals(+1, mutNum.compareTo(new MutableFloat(-1f)))
    assertEquals(-1, mutNum.compareTo(new MutableFloat(1f)))
  }

  @Test def testCompareToNull(): Unit = {
    val mutNum = new MutableFloat(0f)
    assertThrows[NullPointerException](mutNum.compareTo(null))
    ()
  }

  @Test def testPrimitiveValues(): Unit = {
    val mutNum = new MutableFloat(1.7f)
    assertEquals(1, mutNum.intValue)
    assertEquals(1.7, mutNum.doubleValue, 0.00001)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(1, mutNum.intValue)
    assertEquals(1L, mutNum.longValue)
  }

  @Test def testToFloat(): Unit = {
    assertEquals(JavaFloat.valueOf(0f), new MutableFloat(0f).toFloat)
    assertEquals(JavaFloat.valueOf(12.3f), new MutableFloat(12.3f).toFloat)
  }

  @Test def testIncrement(): Unit = {
    val mutNum = new MutableFloat(1)
    mutNum.increment()
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testIncrementAndGet(): Unit = {
    val mutNum = new MutableFloat(1f)
    val result = mutNum.incrementAndGet
    assertEquals(2f, result, 0.01f)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndIncrement(): Unit = {
    val mutNum = new MutableFloat(1f)
    val result = mutNum.getAndIncrement
    assertEquals(1f, result, 0.01f)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testDecrement(): Unit = {
    val mutNum = new MutableFloat(1)
    mutNum.decrement()
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testDecrementAndGet(): Unit = {
    val mutNum = new MutableFloat(1f)
    val result = mutNum.decrementAndGet
    assertEquals(0f, result, 0.01f)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testGetAndDecrement(): Unit = {
    val mutNum = new MutableFloat(1f)
    val result = mutNum.getAndDecrement
    assertEquals(1f, result, 0.01f)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testAddValuePrimitive(): Unit = {
    val mutNum = new MutableFloat(1)
    mutNum.add(1.1f)
    assertEquals(2.1f, mutNum.floatValue, 0.01f)
  }

  @Test def testAddValueObject(): Unit = {
    val mutNum = new MutableFloat(1)
    mutNum.add(JavaFloat.valueOf(1.1f))
    assertEquals(2.1f, mutNum.floatValue, 0.01f)
  }

  @Test def testGetAndAddValuePrimitive(): Unit = {
    val mutableFloat = new MutableFloat(1.25f)
    val result = mutableFloat.getAndAdd(0.75f)
    assertEquals(1.25f, result, 0.01f)
    assertEquals(2f, mutableFloat.floatValue, 0.01f)
  }

  @Test def testGetAndAddValueObject(): Unit = {
    val mutableFloat = new MutableFloat(7.75f)
    val result = mutableFloat.getAndAdd(JavaFloat.valueOf(2.25f))
    assertEquals(7.75f, result, 0.01f)
    assertEquals(10f, mutableFloat.floatValue, 0.01f)
  }

  @Test def testAddAndGetValuePrimitive(): Unit = {
    val mutableFloat = new MutableFloat(0.5f)
    val result = mutableFloat.addAndGet(1f)
    assertEquals(1.5f, result, 0.01f)
    assertEquals(1.5f, mutableFloat.floatValue, 0.01f)
  }

  @Test def testAddAndGetValueObject(): Unit = {
    val mutableFloat = new MutableFloat(5f)
    val result = mutableFloat.addAndGet(JavaFloat.valueOf(2.5f))
    assertEquals(7.5f, result, 0.01f)
    assertEquals(7.5f, mutableFloat.floatValue, 0.01f)
  }

  @Test def testSubtractValuePrimitive(): Unit = {
    val mutNum = new MutableFloat(1)
    mutNum.subtract(0.9f)
    assertEquals(0.1f, mutNum.floatValue, 0.01f)
  }

  @Test def testSubtractValueObject(): Unit = {
    val mutNum = new MutableFloat(1)
    mutNum.subtract(JavaFloat.valueOf(0.9f))
    assertEquals(0.1f, mutNum.floatValue, 0.01f)
  }

  @Test def testToString(): Unit = {
    assertEquals("0.0", new MutableFloat(0f).toString)
    assertEquals("10.0", new MutableFloat(10f).toString)
    assertEquals("-123.0", new MutableFloat(-123f).toString)
  }
}
