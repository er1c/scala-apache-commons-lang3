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

import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.Test

/**
  * Class to test BitField functionality
  */
object BitFieldTest {
  private val bf_multi = new BitField(0x3f80)
  private val bf_single = new BitField(0x4000)
  private val bf_zero = new BitField(0)
}

class BitFieldTest {
  /**
    * test the getValue() method
    */
  @Test def testGetValue() = {
    assertEquals(BitFieldTest.bf_multi.getValue(-1), 127)
    assertEquals(BitFieldTest.bf_multi.getValue(0), 0)
    assertEquals(BitFieldTest.bf_single.getValue(-1), 1)
    assertEquals(BitFieldTest.bf_single.getValue(0), 0)
    assertEquals(BitFieldTest.bf_zero.getValue(-1), 0)
    assertEquals(BitFieldTest.bf_zero.getValue(0), 0)
  }

  /**
    * test the getShortValue() method
    */
  @Test def testGetShortValue() = {
    assertEquals(BitFieldTest.bf_multi.getShortValue(-1.toShort), 127.toShort)
    assertEquals(BitFieldTest.bf_multi.getShortValue(0.toShort), 0.toShort)
    assertEquals(BitFieldTest.bf_single.getShortValue(-1.toShort), 1.toShort)
    assertEquals(BitFieldTest.bf_single.getShortValue(0.toShort), 0.toShort)
    assertEquals(BitFieldTest.bf_zero.getShortValue(-1.toShort), 0.toShort)
    assertEquals(BitFieldTest.bf_zero.getShortValue(0.toShort), 0.toShort)
  }

  /**
    * test the getRawValue() method
    */
  @Test def testGetRawValue() = {
    assertEquals(BitFieldTest.bf_multi.getRawValue(-1), 0x3f80)
    assertEquals(BitFieldTest.bf_multi.getRawValue(0), 0)
    assertEquals(BitFieldTest.bf_single.getRawValue(-1), 0x4000)
    assertEquals(BitFieldTest.bf_single.getRawValue(0), 0)
    assertEquals(BitFieldTest.bf_zero.getRawValue(-1), 0)
    assertEquals(BitFieldTest.bf_zero.getRawValue(0), 0)
  }

  /**
    * test the getShortRawValue() method
    */
  @Test def testGetShortRawValue() = {
    assertEquals(BitFieldTest.bf_multi.getShortRawValue(-1.toShort), 0x3f80.toShort)
    assertEquals(BitFieldTest.bf_multi.getShortRawValue(0.toShort), 0.toShort)
    assertEquals(BitFieldTest.bf_single.getShortRawValue(-1.toShort), 0x4000.toShort)
    assertEquals(BitFieldTest.bf_single.getShortRawValue(0.toShort), 0.toShort)
    assertEquals(BitFieldTest.bf_zero.getShortRawValue(-1.toShort), 0.toShort)
    assertEquals(BitFieldTest.bf_zero.getShortRawValue(0.toShort), 0.toShort)
  }

  /**
    * test the isSet() method
    */
  @Test def testIsSet() = {
    assertFalse(BitFieldTest.bf_multi.isSet(0))
    assertFalse(BitFieldTest.bf_zero.isSet(0))
    var j = 0x80
    while (j <= 0x3f80) {
      assertTrue(BitFieldTest.bf_multi.isSet(j))
      j += 0x80
    }

    j = 0x80
    while (j <= 0x3f80) {
      assertFalse(BitFieldTest.bf_zero.isSet(j))
      j += 0x80
    }
    assertFalse(BitFieldTest.bf_single.isSet(0))
    assertTrue(BitFieldTest.bf_single.isSet(0x4000))
  }

  /**
    * test the isAllSet() method
    */
  @Test def testIsAllSet() = {
    var j = 0
    while (j < 0x3f80) {
      assertFalse(BitFieldTest.bf_multi.isAllSet(j))
      assertTrue(BitFieldTest.bf_zero.isAllSet(j))
      j += 0x80
    }

    assertTrue(BitFieldTest.bf_multi.isAllSet(0x3f80))
    assertFalse(BitFieldTest.bf_single.isAllSet(0))
    assertTrue(BitFieldTest.bf_single.isAllSet(0x4000))
  }

  /**
    * test the setValue() method
    */
  @Test def testSetValue() = {
    for (j <- 0 until 128) {
      assertEquals(BitFieldTest.bf_multi.getValue(BitFieldTest.bf_multi.setValue(0, j)), j)
      assertEquals(BitFieldTest.bf_multi.setValue(0, j), j << 7)
    }
    for (j <- 0 until 128) {
      assertEquals(BitFieldTest.bf_zero.getValue(BitFieldTest.bf_zero.setValue(0, j)), 0)
      assertEquals(BitFieldTest.bf_zero.setValue(0, j), 0)
    }
    // verify that excess bits are stripped off
    assertEquals(BitFieldTest.bf_multi.setValue(0x3f80, 128), 0)
    for (j <- 0 until 2) {
      assertEquals(BitFieldTest.bf_single.getValue(BitFieldTest.bf_single.setValue(0, j)), j)
      assertEquals(BitFieldTest.bf_single.setValue(0, j), j << 14)
    }
    assertEquals(BitFieldTest.bf_single.setValue(0x4000, 2), 0)
  }

  /**
    * test the setShortValue() method
    */
  @Test def testSetShortValue() = {
    for (j <- 0 until 128) {
      assertEquals(
        BitFieldTest.bf_multi.getShortValue(BitFieldTest.bf_multi.setShortValue(0.toShort, j.toShort)),
        j.toShort)
      assertEquals(BitFieldTest.bf_multi.setShortValue(0.toShort, j.toShort), (j << 7).toShort)
    }
    for (j <- 0 until 128) {
      assertEquals(
        BitFieldTest.bf_zero.getShortValue(BitFieldTest.bf_zero.setShortValue(0.toShort, j.toShort)),
        0.toShort)
      assertEquals(BitFieldTest.bf_zero.setShortValue(0.toShort, j.toShort), 0.toShort)
    }
    assertEquals(BitFieldTest.bf_multi.setShortValue(0x3f80.toShort, 128.toShort), 0.toShort)
    for (j <- 0 until 2) {
      assertEquals(
        BitFieldTest.bf_single.getShortValue(BitFieldTest.bf_single.setShortValue(0.toShort, j.toShort)),
        j.toShort)
      assertEquals(BitFieldTest.bf_single.setShortValue(0.toShort, j.toShort), (j << 14).toShort)
    }
    assertEquals(BitFieldTest.bf_single.setShortValue(0x4000.toShort, 2.toShort), 0.toShort)
  }

  @Test def testByte() = {
    assertEquals(0, new BitField(0).setByteBoolean(0.toByte, true))
    assertEquals(1, new BitField(1).setByteBoolean(0.toByte, true))
    assertEquals(2, new BitField(2).setByteBoolean(0.toByte, true))
    assertEquals(4, new BitField(4).setByteBoolean(0.toByte, true))
    assertEquals(8, new BitField(8).setByteBoolean(0.toByte, true))
    assertEquals(16, new BitField(16).setByteBoolean(0.toByte, true))
    assertEquals(32, new BitField(32).setByteBoolean(0.toByte, true))
    assertEquals(64, new BitField(64).setByteBoolean(0.toByte, true))
    assertEquals(-128, new BitField(128).setByteBoolean(0.toByte, true))
    assertEquals(1, new BitField(0).setByteBoolean(1.toByte, false))
    assertEquals(0, new BitField(1).setByteBoolean(1.toByte, false))
    assertEquals(0, new BitField(2).setByteBoolean(2.toByte, false))
    assertEquals(0, new BitField(4).setByteBoolean(4.toByte, false))
    assertEquals(0, new BitField(8).setByteBoolean(8.toByte, false))
    assertEquals(0, new BitField(16).setByteBoolean(16.toByte, false))
    assertEquals(0, new BitField(32).setByteBoolean(32.toByte, false))
    assertEquals(0, new BitField(64).setByteBoolean(64.toByte, false))
    assertEquals(0, new BitField(128).setByteBoolean(128.toByte, false))
    assertEquals(-2, new BitField(1).setByteBoolean(255.toByte, false))
    val clearedBit = new BitField(0x40).setByteBoolean(-63.toByte, false)
    assertFalse(new BitField(0x40).isSet(clearedBit))
  }

  /**
    * test the clear() method
    */
  @Test def testClear() = {
    assertEquals(BitFieldTest.bf_multi.clear(-1), 0xffffc07f)
    assertEquals(BitFieldTest.bf_single.clear(-1), 0xffffbfff)
    assertEquals(BitFieldTest.bf_zero.clear(-1), 0xffffffff)
  }

  /**
    * test the clearShort() method
    */
  @Test def testClearShort() = {
    assertEquals(BitFieldTest.bf_multi.clearShort(-1.toShort), 0xc07f.toShort)
    assertEquals(BitFieldTest.bf_single.clearShort(-1.toShort), 0xbfff.toShort)
    assertEquals(BitFieldTest.bf_zero.clearShort(-1.toShort), 0xffff.toShort)
  }

  /**
    * test the set() method
    */
  @Test def testSet() = {
    assertEquals(BitFieldTest.bf_multi.set(0), 0x3f80)
    assertEquals(BitFieldTest.bf_single.set(0), 0x4000)
    assertEquals(BitFieldTest.bf_zero.set(0), 0)
  }

  /**
    * test the setShort() method
    */
  @Test def testSetShort() = {
    assertEquals(BitFieldTest.bf_multi.setShort(0.toShort), 0x3f80.toShort)
    assertEquals(BitFieldTest.bf_single.setShort(0.toShort), 0x4000.toShort)
    assertEquals(BitFieldTest.bf_zero.setShort(0.toShort), 0.toShort)
  }

  /**
    * test the setBoolean() method
    */
  @Test def testSetBoolean() = {
    assertEquals(BitFieldTest.bf_multi.set(0), BitFieldTest.bf_multi.setBoolean(0, true))
    assertEquals(BitFieldTest.bf_single.set(0), BitFieldTest.bf_single.setBoolean(0, true))
    assertEquals(BitFieldTest.bf_zero.set(0), BitFieldTest.bf_zero.setBoolean(0, true))
    assertEquals(BitFieldTest.bf_multi.clear(-1), BitFieldTest.bf_multi.setBoolean(-1, false))
    assertEquals(BitFieldTest.bf_single.clear(-1), BitFieldTest.bf_single.setBoolean(-1, false))
    assertEquals(BitFieldTest.bf_zero.clear(-1), BitFieldTest.bf_zero.setBoolean(-1, false))
  }

  /**
    * test the setShortBoolean() method
    */
  @Test def testSetShortBoolean() = {
    assertEquals(BitFieldTest.bf_multi.setShort(0.toShort), BitFieldTest.bf_multi.setShortBoolean(0.toShort, true))
    assertEquals(BitFieldTest.bf_single.setShort(0.toShort), BitFieldTest.bf_single.setShortBoolean(0.toShort, true))
    assertEquals(BitFieldTest.bf_zero.setShort(0.toShort), BitFieldTest.bf_zero.setShortBoolean(0.toShort, true))
    assertEquals(BitFieldTest.bf_multi.clearShort(-1.toShort), BitFieldTest.bf_multi.setShortBoolean(-1.toShort, false))
    assertEquals(
      BitFieldTest.bf_single.clearShort(-1.toShort),
      BitFieldTest.bf_single.setShortBoolean(-1.toShort, false))
    assertEquals(BitFieldTest.bf_zero.clearShort(-1.toShort), BitFieldTest.bf_zero.setShortBoolean(-1.toShort, false))
  }
}
