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
import org.junit.Assert.{assertEquals, assertNotEquals, assertNull}
import org.junit.Test

/**
  * Test the MutableTriple class.
  */
class MutableTripleTest {
  @Test def testEmptyArrayLength(): Unit = {
    @SuppressWarnings(Array("unchecked")) val empty =
      MutableTriple.EMPTY_ARRAY.asInstanceOf[Array[MutableTriple[Integer, String, Boolean]]]
    assertEquals(0, empty.length)
  }

  @Test def testEmptyArrayGenerics(): Unit = {
    val empty = MutableTriple.emptyArray
    assertEquals(0, empty.length)
  }

  @Test def testBasic(): Unit = {
    val triple = new MutableTriple[Integer, String, Boolean](0, "foo", JavaBoolean.FALSE)
    assertEquals(0, triple.getLeft.intValue)
    assertEquals("foo", triple.getMiddle)
    assertEquals(JavaBoolean.FALSE, triple.getRight)
    val triple2 = new MutableTriple[AnyRef, String, String](null, "bar", "hello")
    assertNull(triple2.getLeft)
    assertEquals("bar", triple2.getMiddle)
    assertEquals("hello", triple2.getRight)
  }

  @Test def testDefault(): Unit = {
    val triple = new MutableTriple[Integer, String, Boolean]
    assertNull(triple.getLeft)
    assertNull(triple.getMiddle)
    assertNull(triple.getRight)
  }

  @Test def testEquals(): Unit = {
    assertEquals(MutableTriple.of(null, "foo", "baz"), MutableTriple.of(null, "foo", "baz"))
    assertNotEquals(MutableTriple.of("foo", 0, JavaBoolean.TRUE), MutableTriple.of("foo", null, JavaBoolean.TRUE))
    assertNotEquals(MutableTriple.of("foo", "bar", "baz"), MutableTriple.of("xyz", "bar", "baz"))
    assertNotEquals(MutableTriple.of("foo", "bar", "baz"), MutableTriple.of("foo", "bar", "blo"))
    val p = MutableTriple.of("foo", "bar", "baz")
    assertEquals(p, p)
    assertNotEquals(p, new AnyRef)
  }

  @Test def testHashCode(): Unit =
    assertEquals(MutableTriple.of(null, "foo", "baz").hashCode, MutableTriple.of(null, "foo", "baz").hashCode)

  @Test def testMutate(): Unit = {
    val triple = new MutableTriple[Integer, String, Boolean](0, "foo", JavaBoolean.TRUE)
    triple.setLeft(42)
    triple.setMiddle("bar")
    triple.setRight(JavaBoolean.FALSE)
    assertEquals(42, triple.getLeft.intValue)
    assertEquals("bar", triple.getMiddle)
    assertEquals(JavaBoolean.FALSE, triple.getRight)
  }

  @Test
  @SuppressWarnings(Array("unchecked"))
  @throws[Exception]
  def testSerialization(): Unit = {
    val origTriple = MutableTriple.of(0, "foo", JavaBoolean.TRUE)
    val baos = new ByteArrayOutputStream
    val out = new ObjectOutputStream(baos)
    out.writeObject(origTriple)
    val deserializedTriple = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray)).readObject
      .asInstanceOf[MutableTriple[Integer, String, Boolean]]
    assertEquals(origTriple, deserializedTriple)
    assertEquals(origTriple.hashCode, deserializedTriple.hashCode)
  }

  @Test def testToString(): Unit = {
    assertEquals("(null,null,null)", MutableTriple.of(null, null, null).toString)
    assertEquals("(null,two,null)", MutableTriple.of(null, "two", null).toString)
    assertEquals("(one,null,null)", MutableTriple.of("one", null, null).toString)
    assertEquals("(one,two,null)", MutableTriple.of("one", "two", null).toString)
    assertEquals("(null,two,three)", MutableTriple.of(null, "two", "three").toString)
    assertEquals("(one,null,three)", MutableTriple.of("one", null, "three").toString)
    assertEquals("(one,two,three)", MutableTriple.of("one", "two", "three").toString)
  }

  @Test def testTripleOf(): Unit = {
    val triple = MutableTriple.of(0, "foo", JavaBoolean.TRUE)
    assertEquals(0, triple.getLeft.intValue)
    assertEquals("foo", triple.getMiddle)
    assertEquals(JavaBoolean.TRUE, triple.getRight)
    val triple2 = MutableTriple.of(null, "bar", "hello")
    assertNull(triple2.getLeft)
    assertEquals("bar", triple2.getMiddle)
    assertEquals("hello", triple2.getRight)
  }
}
