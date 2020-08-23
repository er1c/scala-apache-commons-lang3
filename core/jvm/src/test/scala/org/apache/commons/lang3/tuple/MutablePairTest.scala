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

package org.apache.commons.lang3.tuple

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.util
import org.junit.Assert.{assertEquals, assertNotEquals, assertNull}
import org.junit.Test

/**
  * Test the MutablePair class.
  */
class MutablePairTest {
  @Test def testEmptyArrayLength(): Unit = {
    @SuppressWarnings(Array("unchecked")) val empty = MutablePair.EMPTY_ARRAY.asInstanceOf[Array[Nothing]]
    assertEquals(0, empty.length)
  }

  @Test def testEmptyArrayGenerics(): Unit = {
    val empty = MutablePair.emptyArray
    assertEquals(0, empty.length)
  }

  @Test def testBasic(): Unit = {
    var oldPair: MutablePair[Integer, String] = new MutablePair(Integer.valueOf(0), "foo")
    var nowPair: MutablePair[Integer, String] = null

    for (_ <- 0 until 4) {
      nowPair = MutablePair.of(oldPair)
      assertEquals(0, nowPair.left.intValue)
      assertEquals(0, nowPair.getLeft.intValue)
      assertEquals("foo", nowPair.right)
      assertEquals("foo", nowPair.getRight)
      assertEquals(oldPair, nowPair)
      oldPair = nowPair
    }

    var oldPair2: MutablePair[_, String] = new MutablePair(null, "bar")
    var nowPair2: MutablePair[_, String] = null

    for (_ <- 0 until 4) {
      nowPair2 = MutablePair.of(oldPair2)
      assertNull(nowPair2.left)
      assertNull(nowPair2.getLeft)
      assertEquals("bar", nowPair2.right)
      assertEquals("bar", nowPair2.getRight)
      oldPair2 = nowPair2
    }
  }

  @Test def testDefault(): Unit = {
    val pair = new MutablePair[AnyRef, AnyRef]
    assertNull(pair.getLeft)
    assertNull(pair.getRight)
  }

  @Test def testEquals(): Unit = {
    assertEquals(MutablePair.of(null, "foo"), MutablePair.of(null, "foo"))
    assertNotEquals(MutablePair.of("foo", 0), MutablePair.of("foo", null))
    assertNotEquals(MutablePair.of("foo", "bar"), MutablePair.of("xyz", "bar"))
    val p = MutablePair.of("foo", "bar")
    assertEquals(p, p)
    assertNotEquals(p, new AnyRef)
  }

  @Test def testHashCode(): Unit =
    assertEquals(MutablePair.of(null, "foo").hashCode, MutablePair.of(null, "foo").hashCode)

  @Test def testMutate(): Unit = {
    val pair: MutablePair[Integer, String] = new MutablePair(0, "foo")
    pair.setLeft(42)
    pair.setRight("bar")
    assertEquals(42, pair.getLeft.intValue)
    assertEquals("bar", pair.getRight)
  }

  @Test def testPairOfMapEntry(): Unit = {
    val map = new util.HashMap[Integer, String]
    map.put(0, "foo")
    val entry = map.entrySet.iterator.next
    val pair = MutablePair.of(entry)
    assertEquals(entry.getKey, pair.getLeft)
    assertEquals(entry.getValue, pair.getRight)
  }

  @Test def testPairOfObjects(): Unit = {
    val pair = MutablePair.of(0, "foo")
    assertEquals(0, pair.getLeft.intValue)
    assertEquals("foo", pair.getRight)
    val pair2 = MutablePair.of(null, "bar")
    assertNull(pair2.getLeft)
    assertEquals("bar", pair2.getRight)
    val pair3 = MutablePair.of(null, null)
    assertNull(pair3.left)
    assertNull(pair3.right)
  }

  @Test
  @SuppressWarnings(Array("unchecked"))
  @throws[Exception]
  def testSerialization(): Unit = {
    val origPair: MutablePair[Integer, String] = MutablePair.of(0, "foo")
    val baos = new ByteArrayOutputStream
    val out = new ObjectOutputStream(baos)
    out.writeObject(origPair)

    val deserializedPair =
      new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray)).readObject
        .asInstanceOf[MutablePair[Integer, String]]

    assertEquals(origPair, deserializedPair)
    assertEquals(origPair.hashCode, deserializedPair.hashCode)
  }

  @Test def testToString(): Unit = {
    assertEquals("(null,null)", MutablePair.of(null, null).toString)
    assertEquals("(null,two)", MutablePair.of(null, "two").toString)
    assertEquals("(one,null)", MutablePair.of("one", null).toString)
    assertEquals("(one,two)", MutablePair.of("one", "two").toString)
  }
}
