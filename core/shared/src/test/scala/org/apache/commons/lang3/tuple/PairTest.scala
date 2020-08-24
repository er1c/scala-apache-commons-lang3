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

import java.util
//import java.util.Calendar
import org.junit.Assert.{assertEquals, assertNotEquals, assertNull, assertTrue}
import org.junit.Test

/**
  * Test the Pair class.
  */
class PairTest {
  @Test def testEmptyArrayLength(): Unit = {
    @SuppressWarnings(Array("unchecked")) val empty = Pair.EMPTY_ARRAY.asInstanceOf[Array[Pair[Integer, String]]]
    assertEquals(0, empty.length)
  }

  @Test def testEmptyArrayGenerics(): Unit = {
    val empty = Pair.emptyArray
    assertEquals(0, empty.length)
  }

  @Test def testComparable1(): Unit = {
    val pair1 = Pair.of("A", "D")
    val pair2 = Pair.of("B", "C")
    assertEquals(0, pair1.compareTo(pair1))
    assertTrue(pair1.compareTo(pair2) < 0)
    assertEquals(0, pair2.compareTo(pair2))
    assertTrue(pair2.compareTo(pair1) > 0)
  }

  @Test def testComparable2(): Unit = {
    val pair1 = Pair.of("A", "C")
    val pair2 = Pair.of("A", "D")
    assertEquals(0, pair1.compareTo(pair1))
    assertTrue(pair1.compareTo(pair2) < 0)
    assertEquals(0, pair2.compareTo(pair2))
    assertTrue(pair2.compareTo(pair1) > 0)
  }

  @Test def testCompatibilityBetweenPairs(): Unit = {
    val pair: ImmutablePair[Integer, String] = ImmutablePair.of(0, "foo")
    val pair2: MutablePair[Integer, String] = MutablePair.of(0, "foo")
    assertEquals(pair, pair2)
    assertEquals(pair.hashCode, pair2.hashCode)
    val set = new util.HashSet[Pair[Integer, String]]
    set.add(pair)
    assertTrue(set.contains(pair2))
    pair2.setValue("bar")
    assertNotEquals(pair, pair2)
    assertNotEquals(pair.hashCode, pair2.hashCode)
  }

  @Test def testFormattable_padded(): Unit = {
    val pair = Pair.of("Key", "Value")
    assertEquals("         (Key,Value)", String.format("%1$20s", pair))
  }

  @Test def testFormattable_simple(): Unit = {
    val pair = Pair.of("Key", "Value")
    assertEquals("(Key,Value)", String.format("%1$s", pair))
  }

  @Test def testMapEntry(): Unit = {
    val pair = ImmutablePair.of(0, "foo")
    val map = new util.HashMap[Integer, String]
    map.put(0, "foo")
    val entry = map.entrySet.iterator.next
    assertEquals(pair, entry)
    assertEquals(pair.hashCode, entry.hashCode)
  }

  @Test def testPairOfMapEntry(): Unit = {
    val map = new util.HashMap[Integer, String]
    map.put(0, "foo")
    val entry = map.entrySet.iterator.next
    val pair = Pair.of(entry)
    assertEquals(entry.getKey, pair.getLeft)
    assertEquals(entry.getValue, pair.getRight)
  }

  @Test def testPairOfObjects(): Unit = {
    val pair = Pair.of(0, "foo")
    assertTrue(pair.isInstanceOf[ImmutablePair[_, _]])
    assertEquals(0, pair.asInstanceOf[ImmutablePair[Integer, String]].left.intValue)
    assertEquals("foo", pair.asInstanceOf[ImmutablePair[Integer, String]].right)
    val pair2 = Pair.of(null, "bar")
    assertTrue(pair2.isInstanceOf[ImmutablePair[_, _]])
    assertNull(pair2.asInstanceOf[ImmutablePair[AnyRef, String]].left)
    assertEquals("bar", pair2.asInstanceOf[ImmutablePair[AnyRef, String]].right)
    val pair3 = Pair.of(null, null)
    assertNull(pair3.getLeft)
    assertNull(pair3.getRight)
  }

  @Test def testToString(): Unit = {
    val pair = Pair.of("Key", "Value")
    assertEquals("(Key,Value)", pair.toString)
  }

//  TODO: scala.js calendar
//  @Test def testToStringCustom(): Unit = {
//    val date = Calendar.getInstance
//    date.set(2011, Calendar.APRIL, 25)
//    val pair = Pair.of("DOB", date)
//    assertEquals("Test created on " + "04-25-2011", pair.toString("Test created on %2$tm-%2$td-%2$tY"))
//  }
}
