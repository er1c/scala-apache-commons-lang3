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
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite
import scala.collection.JavaConverters._

/**
  * Test the Pair class.
  */
class ImmutablePairTest extends JUnitSuite {
  @Test def testEmptyArrayLength(): Unit = {
    @SuppressWarnings(Array("unchecked")) val empty =
      ImmutablePair.EMPTY_ARRAY.asInstanceOf[Array[ImmutablePair[Integer, String]]]
    assertEquals(0, empty.length)
  }

  @Test def testEmptyArrayGenerics(): Unit = {
    val empty = ImmutablePair.emptyArray
    assertEquals(0, empty.length)
  }

  @Test def testBasic(): Unit = {
    var oldPair = new ImmutablePair[Integer, String](0, "foo")
    var nowPair: ImmutablePair[Integer, String] = null
    for (_ <- 0 until 4) {
      nowPair = ImmutablePair.of(oldPair)
      assertEquals(0, nowPair.left.intValue)
      assertEquals(0, nowPair.getLeft.intValue)
      assertEquals("foo", nowPair.right)
      assertEquals("foo", nowPair.getRight)
      assertEquals(oldPair, nowPair)
      oldPair = nowPair
    }

    var oldPair2 = new ImmutablePair[AnyRef, String](null, "bar")
    var nowPair2: ImmutablePair[AnyRef, String] = null
    for (_ <- 0 until 4) {
      nowPair2 = ImmutablePair.of(oldPair2)
      assertNull(nowPair2.left)
      assertNull(nowPair2.getLeft)
      assertEquals("bar", nowPair2.right)
      assertEquals("bar", nowPair2.getRight)
      oldPair2 = nowPair2
    }
  }

  @Test def testEquals(): Unit = {
    assertEquals(ImmutablePair.of(null, "foo"), ImmutablePair.of(null, "foo"))
    assertNotEquals(ImmutablePair.of("foo", 0), ImmutablePair.of("foo", null))
    assertNotEquals(ImmutablePair.of("foo", "bar"), ImmutablePair.of("xyz", "bar"))
    val p = ImmutablePair.of("foo", "bar")
    assertEquals(p, p)
    assertNotEquals(p, new AnyRef)
  }

  @Test def testHashCode(): Unit =
    assertEquals(ImmutablePair.of(null, "foo").hashCode, ImmutablePair.of(null, "foo").hashCode)

  @Test def testNullPairEquals(): Unit = assertEquals(ImmutablePair.nullPair, ImmutablePair.nullPair)

  @Test def testNullPairKey(): Unit = assertNull(ImmutablePair.nullPair[Any, Any].getKey)

  @Test def testNullPairLeft(): Unit = assertNull(ImmutablePair.nullPair[Any, Any].getLeft)

  @Test def testNullPairRight(): Unit = assertNull(ImmutablePair.nullPair[Any, Any].getRight)

  @Test def testNullPairSame(): Unit = assertSame(ImmutablePair.nullPair, ImmutablePair.nullPair)

  @Test def testNullPairTyped(): Unit = {
    // No compiler warnings
    // How do we assert that?
    val pair = ImmutablePair.nullPair[Any, Any]
    assertNotNull(pair)
  }

  @Test def testNullPairValue(): Unit = assertNull(ImmutablePair.nullPair[Any, Any].getValue)

  @Test def testPairOfMapEntry(): Unit = {
    val map = new util.HashMap[Integer, String]
    map.put(0, "foo")
    val entry = map.entrySet.iterator.next
    val pair = ImmutablePair.of(entry)
    assertEquals(entry.getKey, pair.getLeft)
    assertEquals(entry.getValue, pair.getRight)
  }

  @Test def testPairOfObjects(): Unit = {
    val pair = ImmutablePair.of(0, "foo")
    assertEquals(0, pair.left.intValue)
    assertEquals(0, pair.getLeft.intValue)
    assertEquals("foo", pair.right)
    assertEquals("foo", pair.getRight)
    val pair2 = ImmutablePair.of(null, "bar")
    assertNull(pair2.left)
    assertNull(pair2.getLeft)
    assertEquals("bar", pair2.right)
    assertEquals("bar", pair2.getRight)
    val pair3 = ImmutablePair.of(null, null)
    assertNull(pair3.left)
    assertNull(pair3.right)
  }

  @Test
  @SuppressWarnings(Array("unchecked"))
  @throws[Exception]
  def testSerialization(): Unit = {
    val origPair = ImmutablePair.of(0, "foo")
    val baos = new ByteArrayOutputStream
    val out = new ObjectOutputStream(baos)
    out.writeObject(origPair)
    val deserializedPair = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray)).readObject
      .asInstanceOf[ImmutablePair[Integer, String]]
    assertEquals(origPair, deserializedPair)
    assertEquals(origPair.hashCode, deserializedPair.hashCode)
  }

  @Test def testToString(): Unit = {
    assertEquals("(null,null)", ImmutablePair.of(null, null).toString)
    assertEquals("(null,two)", ImmutablePair.of(null, "two").toString)
    assertEquals("(one,null)", ImmutablePair.of("one", null).toString)
    assertEquals("(one,two)", ImmutablePair.of("one", "two").toString)
  }

  @Test def testUseAsKeyOfHashMap(): Unit = {
    val map = new util.HashMap[ImmutablePair[AnyRef, AnyRef], String]
    val o1 = new AnyRef
    val o2 = new AnyRef
    val key1 = ImmutablePair.of(o1, o2)
    val value1 = "a1"
    map.put(key1, value1)
    assertEquals(value1, map.get(key1))
    assertEquals(value1, map.get(ImmutablePair.of(o1, o2)))
  }

  @Test def testUseAsKeyOfTreeMap(): Unit = {
    val map = new util.TreeMap[ImmutablePair[Integer, Integer], String]
    map.put(ImmutablePair.of(1, 2), "12")
    map.put(ImmutablePair.of(1, 1), "11")
    map.put(ImmutablePair.of(0, 1), "01")
    val expected = new util.ArrayList[ImmutablePair[Integer, Integer]]
    expected.add(ImmutablePair.of(0, 1))
    expected.add(ImmutablePair.of(1, 1))
    expected.add(ImmutablePair.of(1, 2))
    val it = map.entrySet.iterator
    for (item <- expected.asScala) {
      val entry = it.next
      assertEquals(item, entry.getKey)
      assertEquals(item.getLeft + "" + item.getRight, entry.getValue)
    }
  }

  @Test def testComparableLeftOnly(): Unit = {
    val pair1 = ImmutablePair.left("A")
    val pair2 = ImmutablePair.left("B")
    assertEquals("A", pair1.getLeft)
    assertEquals("B", pair2.getLeft)
    assertEquals(0, pair1.compareTo(pair1))
    assertTrue(pair1.compareTo(pair2) < 0)
    assertEquals(0, pair2.compareTo(pair2))
    assertTrue(pair2.compareTo(pair1) > 0)
  }

  @Test def testComparableRightOnly(): Unit = {
    val pair1 = ImmutablePair.right("A")
    val pair2 = ImmutablePair.right("B")
    assertEquals("A", pair1.getRight)
    assertEquals("B", pair2.getRight)
    assertEquals(0, pair1.compareTo(pair1))
    assertTrue(pair1.compareTo(pair2) < 0)
    assertEquals(0, pair2.compareTo(pair2))
    assertTrue(pair2.compareTo(pair1) > 0)
  }

  @Test def testToStringLeft(): Unit = {
    val pair = ImmutablePair.left("Key")
    assertEquals("(Key,null)", pair.toString)
  }

  @Test def testToStringRight(): Unit = {
    val pair = ImmutablePair.right("Value")
    assertEquals("(null,Value)", pair.toString)
  }
}
