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

import java.lang.{Boolean => JavaBoolean}
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * JUnit tests.
  *
  * @since 2.2
  * @see MutableBoolean
  */
class MutableBooleanTest extends JUnitSuite {
  @Test def testCompareTo(): Unit = {
    val mutBool = new MutableBoolean(false)
    assertEquals(0, mutBool.compareTo(new MutableBoolean(false)))
    assertEquals(-1, mutBool.compareTo(new MutableBoolean(true)))
    mutBool.setValue(true)
    assertEquals(+1, mutBool.compareTo(new MutableBoolean(false)))
    assertEquals(0, mutBool.compareTo(new MutableBoolean(true)))
  }

  @Test def testCompareToNull(): Unit = {
    val mutBool = new MutableBoolean(false)
    assertThrows[NullPointerException](mutBool.compareTo(null))
    ()
  }

  @Test def testConstructors(): Unit = {
    assertFalse(new MutableBoolean().booleanValue)
    assertTrue(new MutableBoolean(true).booleanValue)
    assertFalse(new MutableBoolean(false).booleanValue)
    assertTrue(new MutableBoolean(JavaBoolean.TRUE).booleanValue)
    assertFalse(new MutableBoolean(JavaBoolean.FALSE).booleanValue)
  }

  @Test def testConstructorNull(): Unit = {
    assertThrows[NullPointerException](new MutableBoolean(null))
    ()
  }

  @Test def testEquals(): Unit = {
    val mutBoolA = new MutableBoolean(false)
    val mutBoolB = new MutableBoolean(false)
    val mutBoolC = new MutableBoolean(true)
    assertEquals(mutBoolA, mutBoolA)
    assertEquals(mutBoolA, mutBoolB)
    assertEquals(mutBoolB, mutBoolA)
    assertEquals(mutBoolB, mutBoolB)
    assertNotEquals(mutBoolA, mutBoolC)
    assertNotEquals(mutBoolB, mutBoolC)
    assertEquals(mutBoolC, mutBoolC)
    assertNotEquals(null, mutBoolA)
    assertNotEquals(mutBoolA, JavaBoolean.FALSE)
    assertNotEquals("false", mutBoolA)
  }

  @Test def testGetSet(): Unit = {
    assertFalse(new MutableBoolean().booleanValue)
    assertEquals(JavaBoolean.FALSE, new MutableBoolean().getValue)
    val mutBool = new MutableBoolean(false)
    assertEquals(JavaBoolean.FALSE, mutBool.toBoolean)
    assertFalse(mutBool.booleanValue)
    assertTrue(mutBool.isFalse)
    assertFalse(mutBool.isTrue)
    mutBool.setValue(JavaBoolean.TRUE)
    assertEquals(JavaBoolean.TRUE, mutBool.toBoolean)
    assertTrue(mutBool.booleanValue)
    assertFalse(mutBool.isFalse)
    assertTrue(mutBool.isTrue)
    mutBool.setValue(false)
    assertFalse(mutBool.booleanValue)
    mutBool.setValue(true)
    assertTrue(mutBool.booleanValue)
    mutBool.setFalse()
    assertFalse(mutBool.booleanValue)
    mutBool.setTrue()
    assertTrue(mutBool.booleanValue)
  }

  @Test def testSetNull(): Unit = {
    val mutBool = new MutableBoolean(false)
    assertThrows[NullPointerException](mutBool.setValue(null))
    ()
  }

  @Test def testHashCode(): Unit = {
    val mutBoolA = new MutableBoolean(false)
    val mutBoolB = new MutableBoolean(false)
    val mutBoolC = new MutableBoolean(true)
    assertEquals(mutBoolA.hashCode, mutBoolA.hashCode)
    assertEquals(mutBoolA.hashCode, mutBoolB.hashCode)
    assertNotEquals(mutBoolA.hashCode, mutBoolC.hashCode)
    assertEquals(mutBoolA.hashCode, JavaBoolean.FALSE.hashCode)
    assertEquals(mutBoolC.hashCode, JavaBoolean.TRUE.hashCode)
  }

  @Test def testToString(): Unit = {
    assertEquals(JavaBoolean.FALSE.toString, new MutableBoolean(false).toString)
    assertEquals(JavaBoolean.TRUE.toString, new MutableBoolean(true).toString)
  }
}
