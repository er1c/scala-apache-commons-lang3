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

import java.lang.{Boolean => JavaBoolean, Long => JavaLong}
import java.util
import java.util.Calendar
import org.junit.Assert.{assertEquals, assertNull, assertTrue}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Test the Triple class.
  */
class TripleTest extends JUnitSuite {
  @Test def testEmptyArrayLength() = {
    @SuppressWarnings(Array("unchecked")) val empty =
      Triple.EMPTY_ARRAY.asInstanceOf[Array[Triple[Integer, String, Boolean]]]
    assertEquals(0, empty.length)
  }

  @Test def testEmptyArrayGenerics() = {
    val empty = Triple.emptyArray
    assertEquals(0, empty.length)
  }

  @Test def testComparable1() = {
    val triple1 = Triple.of("A", "D", "A")
    val triple2 = Triple.of("B", "C", "A")
    assertEquals(0, triple1.compareTo(triple1))
    assertTrue(triple1.compareTo(triple2) < 0)
    assertEquals(0, triple2.compareTo(triple2))
    assertTrue(triple2.compareTo(triple1) > 0)
  }

  @Test def testComparable2() = {
    val triple1 = Triple.of("A", "C", "B")
    val triple2 = Triple.of("A", "D", "B")
    assertEquals(0, triple1.compareTo(triple1))
    assertTrue(triple1.compareTo(triple2) < 0)
    assertEquals(0, triple2.compareTo(triple2))
    assertTrue(triple2.compareTo(triple1) > 0)
  }

  @Test def testComparable3() = {
    val triple1 = Triple.of("A", "A", "D")
    val triple2 = Triple.of("A", "B", "C")
    assertEquals(0, triple1.compareTo(triple1))
    assertTrue(triple1.compareTo(triple2) < 0)
    assertEquals(0, triple2.compareTo(triple2))
    assertTrue(triple2.compareTo(triple1) > 0)
  }

  @Test def testComparable4() = {
    val triple1 = Triple.of("B", "A", "C")
    val triple2 = Triple.of("B", "A", "D")
    assertEquals(0, triple1.compareTo(triple1))
    assertTrue(triple1.compareTo(triple2) < 0)
    assertEquals(0, triple2.compareTo(triple2))
    assertTrue(triple2.compareTo(triple1) > 0)
  }

  @Test def testCompatibilityBetweenTriples(): Unit = {
    val triple: ImmutableTriple[Integer, String, JavaBoolean] = ImmutableTriple.of(0, "foo", JavaBoolean.TRUE)
    val triple2: MutableTriple[Integer, String, JavaBoolean] = MutableTriple.of(0, "foo", JavaBoolean.TRUE)
    assertEquals(triple, triple2)
    assertEquals(triple.hashCode, triple2.hashCode)
    val set = new util.HashSet[Triple[Integer, String, JavaBoolean]]
    set.add(triple)
    assertTrue(set.contains(triple2))
  }

  @Test def testFormattable_padded(): Unit = {
    val triple = Triple.of("Key", "Something", "Value")
    assertEquals("         (Key,Something,Value)", String.format("%1$30s", triple))
  }

  @Test def testFormattable_simple() = {
    val triple = Triple.of("Key", "Something", "Value")
    assertEquals("(Key,Something,Value)", String.format("%1$s", triple))
  }

  @Test def testToString() = {
    val triple = Triple.of("Key", "Something", "Value")
    assertEquals("(Key,Something,Value)", triple.toString)
  }

  @Test def testToStringCustom() = {
    val date = Calendar.getInstance
    date.set(2011, Calendar.APRIL, 25)
    val triple = Triple.of("DOB", "string", date)
    assertEquals("Test created on " + "04-25-2011", triple.toString("Test created on %3$tm-%3$td-%3$tY"))
  }

  @Test def testTripleOf() = {
    val triple = Triple.of(0, "foo", JavaBoolean.TRUE)
    assertTrue(triple.isInstanceOf[ImmutableTriple[_, _, _]])
    assertEquals(0, triple.asInstanceOf[ImmutableTriple[Integer, String, Boolean]].left.intValue)
    assertEquals("foo", triple.asInstanceOf[ImmutableTriple[Integer, String, Boolean]].middle)
    assertEquals(JavaBoolean.TRUE, triple.asInstanceOf[ImmutableTriple[Integer, String, Boolean]].right)
    val triple2 = Triple.of(null, "bar", JavaLong.valueOf(200L))
    assertTrue(triple2.isInstanceOf[ImmutableTriple[_, _, _]])
    assertNull(triple2.asInstanceOf[ImmutableTriple[AnyRef, String, Long]].left)
    assertEquals("bar", triple2.asInstanceOf[ImmutableTriple[AnyRef, String, Long]].middle)
    assertEquals(JavaLong.valueOf(200L), triple2.asInstanceOf[ImmutableTriple[AnyRef, String, Long]].right)
  }
}
