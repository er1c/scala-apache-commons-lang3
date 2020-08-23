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

import java.lang.{Boolean => JavaBoolean}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.util
import org.junit.Assert._
import org.junit.Test

import scala.collection.JavaConverters._

/**
  * Test the Triple class.
  */
class ImmutableTripleTest {
  @Test def testEmptyArrayLength(): Unit = {
    @SuppressWarnings(Array("unchecked")) val empty =
      ImmutableTriple.EMPTY_ARRAY.asInstanceOf[Array[ImmutableTriple[Integer, String, Boolean]]]
    assertEquals(0, empty.length)
  }

  @Test def testEmptyArrayGenerics(): Unit = {
    val empty = ImmutableTriple.emptyArray
    assertEquals(0, empty.length)
  }

  @Test def testBasic(): Unit = {
    val triple = new ImmutableTriple[Integer, String, Boolean](0, "foo", JavaBoolean.TRUE)
    assertEquals(0, triple.left.intValue)
    assertEquals(0, triple.getLeft.intValue)
    assertEquals("foo", triple.middle)
    assertEquals("foo", triple.getMiddle)
    assertEquals(JavaBoolean.TRUE, triple.right)
    assertEquals(JavaBoolean.TRUE, triple.getRight)
    val triple2 = new ImmutableTriple[AnyRef, String, Integer](null, "bar", 42)
    assertNull(triple2.left)
    assertNull(triple2.getLeft)
    assertEquals("bar", triple2.middle)
    assertEquals("bar", triple2.getMiddle)
    assertEquals(new Integer(42), triple2.right)
    assertEquals(new Integer(42), triple2.getRight)
  }

  @Test def testEquals(): Unit = {
    assertEquals(ImmutableTriple.of(null, "foo", 42), ImmutableTriple.of(null, "foo", 42))
    assertNotEquals(ImmutableTriple.of("foo", 0, JavaBoolean.TRUE), ImmutableTriple.of("foo", null, null))
    assertNotEquals(ImmutableTriple.of("foo", "bar", "baz"), ImmutableTriple.of("xyz", "bar", "blo"))
    val p = ImmutableTriple.of("foo", "bar", "baz")
    assertEquals(p, p)
    assertNotEquals(p, new AnyRef)
  }

  @Test def testHashCode(): Unit =
    assertEquals(
      ImmutableTriple.of(null, "foo", JavaBoolean.TRUE).hashCode,
      ImmutableTriple.of(null, "foo", JavaBoolean.TRUE).hashCode)

  @Test def testNullTripleEquals(): Unit = assertEquals(ImmutableTriple.nullTriple, ImmutableTriple.nullTriple)

  @Test def testNullTripleLeft(): Unit = assertNull(ImmutableTriple.nullTriple[Any, Any, Any].getLeft)

  @Test def testNullTripleMiddle(): Unit = assertNull(ImmutableTriple.nullTriple[Any, Any, Any].getMiddle)

  @Test def testNullTripleRight(): Unit = assertNull(ImmutableTriple.nullTriple[Any, Any, Any].getRight)

  @Test def testNullTripleSame(): Unit = assertSame(ImmutableTriple.nullTriple, ImmutableTriple.nullTriple)

  @Test def testNullTripleTyped(): Unit = { // No compiler warnings
    // How do we assert that?
    val triple = ImmutableTriple.nullTriple
    assertNotNull(triple)
  }

  @Test
  @SuppressWarnings(Array("unchecked"))
  @throws[Exception]
  def testSerialization(): Unit = {
    val origTriple = ImmutableTriple.of(0, "foo", JavaBoolean.TRUE)
    val baos = new ByteArrayOutputStream
    val out = new ObjectOutputStream(baos)
    out.writeObject(origTriple)
    val deserializedTriple = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray)).readObject
      .asInstanceOf[ImmutableTriple[Integer, String, Boolean]]
    assertEquals(origTriple, deserializedTriple)
    assertEquals(origTriple.hashCode, deserializedTriple.hashCode)
  }

  @Test def testToString(): Unit = {
    assertEquals("(null,null,null)", ImmutableTriple.of(null, null, null).toString)
    assertEquals("(null,two,null)", ImmutableTriple.of(null, "two", null).toString)
    assertEquals("(one,null,null)", ImmutableTriple.of("one", null, null).toString)
    assertEquals("(one,two,null)", ImmutableTriple.of("one", "two", null).toString)
    assertEquals("(null,two,three)", ImmutableTriple.of(null, "two", "three").toString)
    assertEquals("(one,null,three)", ImmutableTriple.of("one", null, "three").toString)
    assertEquals("(one,two,three)", MutableTriple.of("one", "two", "three").toString)
  }

  @Test def testTripleOf(): Unit = {
    val triple = ImmutableTriple.of(0, "foo", JavaBoolean.FALSE)
    assertEquals(0, triple.left.intValue)
    assertEquals(0, triple.getLeft.intValue)
    assertEquals("foo", triple.middle)
    assertEquals("foo", triple.getMiddle)
    assertEquals(JavaBoolean.FALSE, triple.right)
    assertEquals(JavaBoolean.FALSE, triple.getRight)
    val triple2 = ImmutableTriple.of(null, "bar", JavaBoolean.TRUE)
    assertNull(triple2.left)
    assertNull(triple2.getLeft)
    assertEquals("bar", triple2.middle)
    assertEquals("bar", triple2.getMiddle)
    assertEquals(JavaBoolean.TRUE, triple2.right)
    assertEquals(JavaBoolean.TRUE, triple2.getRight)
  }

  @Test def testUseAsKeyOfHashMap(): Unit = {
    val map = new util.HashMap[ImmutableTriple[AnyRef, AnyRef, AnyRef], String]
    val o1 = new AnyRef
    val o2 = new AnyRef
    val o3 = new AnyRef
    val key1 = ImmutableTriple.of(o1, o2, o3)
    val value1 = "a1"
    map.put(key1, value1)
    assertEquals(value1, map.get(key1))
    assertEquals(value1, map.get(ImmutableTriple.of(o1, o2, o3)))
  }

  @Test def testUseAsKeyOfTreeMap(): Unit = {
    val map = new util.TreeMap[ImmutableTriple[Integer, Integer, Integer], String]
    map.put(ImmutableTriple.of(0, 1, 2), "012")
    map.put(ImmutableTriple.of(0, 1, 1), "011")
    map.put(ImmutableTriple.of(0, 0, 1), "001")
    val expected = new util.ArrayList[ImmutableTriple[Integer, Integer, Integer]]
    expected.add(ImmutableTriple.of(0, 0, 1))
    expected.add(ImmutableTriple.of(0, 1, 1))
    expected.add(ImmutableTriple.of(0, 1, 2))
    val it = map.entrySet.iterator
    for (item <- expected.asScala) {
      val entry = it.next
      assertEquals(item, entry.getKey)
      assertEquals(item.getLeft + "" + item.getMiddle + "" + item.getRight, entry.getValue)
    }
  }
}
