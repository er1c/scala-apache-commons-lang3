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

import java.lang.{Byte => JavaByte}
import org.apache.commons.lang3.TestHelpers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * JUnit tests.
  *
  * @see MutableByte
  */
class MutableByteTest extends TestHelpers {
  @Test def testConstructors(): Unit = {
    assertEquals(0.toByte, new MutableByte().byteValue)
    assertEquals(1.toByte, new MutableByte(1.toByte).byteValue)
    assertEquals(2.toByte, new MutableByte(JavaByte.valueOf(2.toByte)).byteValue)
    assertEquals(3.toByte, new MutableByte(new MutableByte(3.toByte)).byteValue)
    assertEquals(2.toByte, new MutableByte("2").byteValue)
  }

  @Test def testConstructorNull(): Unit = {
    assertThrows[NullPointerException](new MutableByte(null.asInstanceOf[Number]))
    ()
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableByte(0.toByte)
    assertEquals(0.toByte, new MutableByte().byteValue)
    assertEquals(JavaByte.valueOf(0.toByte), new MutableByte().getValue)
    mutNum.setValue(1.toByte)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(JavaByte.valueOf(1.toByte), mutNum.getValue)
    mutNum.setValue(JavaByte.valueOf(2.toByte))
    assertEquals(2.toByte, mutNum.byteValue)
    assertEquals(JavaByte.valueOf(2.toByte), mutNum.getValue)
    mutNum.setValue(new MutableByte(3.toByte))
    assertEquals(3.toByte, mutNum.byteValue)
    assertEquals(JavaByte.valueOf(3.toByte), mutNum.getValue)
  }

  @Test def testSetNull(): Unit = {
    val mutNum = new MutableByte(0.toByte)
    assertThrows[NullPointerException](mutNum.setValue(null))
    ()
  }

  @Test def testEquals(): Unit = {
    val mutNumA = new MutableByte(0.toByte)
    val mutNumB = new MutableByte(0.toByte)
    val mutNumC = new MutableByte(1.toByte)
    assertEquals(mutNumA, mutNumA)
    assertEquals(mutNumA, mutNumB)
    assertEquals(mutNumB, mutNumA)
    assertEquals(mutNumB, mutNumB)
    assertNotEquals(mutNumA, mutNumC)
    assertNotEquals(mutNumB, mutNumC)
    assertEquals(mutNumC, mutNumC)
    assertNotEquals(null, mutNumA)
    assertNotEquals(mutNumA, JavaByte.valueOf(0.toByte))
    assertNotEquals("0", mutNumA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableByte(0.toByte)
    val mutNumB = new MutableByte(0.toByte)
    val mutNumC = new MutableByte(1.toByte)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertEquals(mutNumA.hashCode, JavaByte.valueOf(0.toByte).hashCode)
  }

  @Test def testCompareTo(): Unit = {
    val mutNum = new MutableByte(0.toByte)
    assertEquals(0.toByte, mutNum.compareTo(new MutableByte(0.toByte)))
    assertEquals(+1.toByte, mutNum.compareTo(new MutableByte(-1.toByte)))
    assertEquals(-1.toByte, mutNum.compareTo(new MutableByte(1.toByte)))
  }

  @Test def testCompareToNull(): Unit = {
    val mutNum = new MutableByte(0.toByte)
    assertThrows[NullPointerException](mutNum.compareTo(null))
    ()
  }

  @Test def testPrimitiveValues(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    assertEquals(1.0f, mutNum.floatValue, FloatDelta)
    assertEquals(1.0, mutNum.doubleValue, DoubleDelta)
    assertEquals(1.toByte, mutNum.byteValue)
    assertEquals(1.toShort, mutNum.shortValue)
    assertEquals(1, mutNum.intValue)
    assertEquals(1L, mutNum.longValue)
  }

  @Test def testToByte(): Unit = {
    assertEquals(JavaByte.valueOf(0.toByte), new MutableByte(0.toByte).toByte)
    assertEquals(JavaByte.valueOf(123.toByte), new MutableByte(123.toByte).toByte)
  }

  @Test def testIncrement(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    mutNum.increment()
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testIncrementAndGet(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    val result = mutNum.incrementAndGet
    assertEquals(2, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testGetAndIncrement(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    val result = mutNum.getAndIncrement
    assertEquals(1, result)
    assertEquals(2, mutNum.intValue)
    assertEquals(2L, mutNum.longValue)
  }

  @Test def testDecrement(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    mutNum.decrement()
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testDecrementAndGet(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    val result = mutNum.decrementAndGet
    assertEquals(0, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testGetAndDecrement(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    val result = mutNum.getAndDecrement
    assertEquals(1, result)
    assertEquals(0, mutNum.intValue)
    assertEquals(0L, mutNum.longValue)
  }

  @Test def testAddValuePrimitive(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    mutNum.add(1.toByte)
    assertEquals(2.toByte, mutNum.byteValue)
  }

  @Test def testAddValueObject(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    mutNum.add(Integer.valueOf(1))
    assertEquals(2.toByte, mutNum.byteValue)
  }

  @Test def testGetAndAddValuePrimitive(): Unit = {
    val mutableByte = new MutableByte(0.toByte)
    val result = mutableByte.getAndAdd(1.toByte)
    assertEquals(0.toByte, result)
    assertEquals(1.toByte, mutableByte.byteValue)
  }

  @Test def testGetAndAddValueObject(): Unit = {
    val mutableByte = new MutableByte(0.toByte)
    val result = mutableByte.getAndAdd(JavaByte.valueOf(1.toByte))
    assertEquals(0.toByte, result)
    assertEquals(1.toByte, mutableByte.byteValue)
  }

  @Test def testAddAndGetValuePrimitive(): Unit = {
    val mutableByte = new MutableByte(0.toByte)
    val result = mutableByte.addAndGet(1.toByte)
    assertEquals(1.toByte, result)
    assertEquals(1.toByte, mutableByte.byteValue)
  }

  @Test def testAddAndGetValueObject(): Unit = {
    val mutableByte = new MutableByte(0.toByte)
    val result = mutableByte.addAndGet(JavaByte.valueOf(1.toByte))
    assertEquals(1.toByte, result)
    assertEquals(1.toByte, mutableByte.byteValue)
  }

  @Test def testSubtractValuePrimitive(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    mutNum.subtract(1.toByte)
    assertEquals(0.toByte, mutNum.byteValue)
  }

  @Test def testSubtractValueObject(): Unit = {
    val mutNum = new MutableByte(1.toByte)
    mutNum.subtract(Integer.valueOf(1))
    assertEquals(0.toByte, mutNum.byteValue)
  }

  @Test def testToString(): Unit = {
    assertEquals("0", new MutableByte(0.toByte).toString)
    assertEquals("10", new MutableByte(10.toByte).toString)
    assertEquals("-123", new MutableByte(-123.toByte).toString)
  }
}
