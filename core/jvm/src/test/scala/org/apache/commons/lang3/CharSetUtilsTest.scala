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

//package org.apache.commons.lang3
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link org.apache.commons.lang3.CharSetUtils}.
//  */
//class CharSetUtilsTest extends JUnitSuite {
//  //-----------------------------------------------------------------------
//  @Test def testConstructor() = {
//    assertNotNull(new Nothing)
//    val cons = classOf[Nothing].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test def testSqueeze_StringString() = {
//    assertNull(CharSetUtils.squeeze(null, null.asInstanceOf[String]))
//    assertNull(CharSetUtils.squeeze(null, ""))
//    assertEquals("", CharSetUtils.squeeze("", null.asInstanceOf[String]))
//    assertEquals("", CharSetUtils.squeeze("", ""))
//    assertEquals("", CharSetUtils.squeeze("", "a-e"))
//    assertEquals("hello", CharSetUtils.squeeze("hello", null.asInstanceOf[String]))
//    assertEquals("hello", CharSetUtils.squeeze("hello", ""))
//    assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"))
//    assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"))
//    assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"))
//    assertEquals("hello", CharSetUtils.squeeze("helloo", "^l"))
//  }
//
//  @Test def testSqueeze_StringStringarray() = {
//    assertNull(CharSetUtils.squeeze(null, null.asInstanceOf[Array[String]]))
//    assertNull(CharSetUtils.squeeze(null))
//    assertNull(CharSetUtils.squeeze(null, null))
//    assertNull(CharSetUtils.squeeze(null, "el"))
//    assertEquals("", CharSetUtils.squeeze("", null.asInstanceOf[Array[String]]))
//    assertEquals("", CharSetUtils.squeeze(""))
//    assertEquals("", CharSetUtils.squeeze("", null))
//    assertEquals("", CharSetUtils.squeeze("", "a-e"))
//    assertEquals("hello", CharSetUtils.squeeze("hello", null.asInstanceOf[Array[String]]))
//    assertEquals("hello", CharSetUtils.squeeze("hello"))
//    assertEquals("hello", CharSetUtils.squeeze("hello", null))
//    assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"))
//    assertEquals("helo", CharSetUtils.squeeze("hello", "el"))
//    assertEquals("hello", CharSetUtils.squeeze("hello", "e"))
//    assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of"))
//    assertEquals("fof", CharSetUtils.squeeze("fooooff", "fo"))
//  }
//
//  @Test def testContainsAny_StringString() = {
//    assertFalse(CharSetUtils.containsAny(null, null.asInstanceOf[String]))
//    assertFalse(CharSetUtils.containsAny(null, ""))
//    assertFalse(CharSetUtils.containsAny("", null.asInstanceOf[String]))
//    assertFalse(CharSetUtils.containsAny("", ""))
//    assertFalse(CharSetUtils.containsAny("", "a-e"))
//    assertFalse(CharSetUtils.containsAny("hello", null.asInstanceOf[String]))
//    assertFalse(CharSetUtils.containsAny("hello", ""))
//    assertTrue(CharSetUtils.containsAny("hello", "a-e"))
//    assertTrue(CharSetUtils.containsAny("hello", "l-p"))
//  }
//
//  @Test def testContainsAny_StringStringarray() = {
//    assertFalse(CharSetUtils.containsAny(null, null.asInstanceOf[Array[String]]))
//    assertFalse(CharSetUtils.containsAny(null))
//    assertFalse(CharSetUtils.containsAny(null, null))
//    assertFalse(CharSetUtils.containsAny(null, "a-e"))
//    assertFalse(CharSetUtils.containsAny("", null.asInstanceOf[Array[String]]))
//    assertFalse(CharSetUtils.containsAny(""))
//    assertFalse(CharSetUtils.containsAny("", null))
//    assertFalse(CharSetUtils.containsAny("", "a-e"))
//    assertFalse(CharSetUtils.containsAny("hello", null.asInstanceOf[Array[String]]))
//    assertFalse(CharSetUtils.containsAny("hello"))
//    assertFalse(CharSetUtils.containsAny("hello", null))
//    assertTrue(CharSetUtils.containsAny("hello", "a-e"))
//    assertTrue(CharSetUtils.containsAny("hello", "el"))
//    assertFalse(CharSetUtils.containsAny("hello", "x"))
//    assertTrue(CharSetUtils.containsAny("hello", "e-i"))
//    assertTrue(CharSetUtils.containsAny("hello", "a-z"))
//    assertFalse(CharSetUtils.containsAny("hello", ""))
//  }
//
//  @Test def testCount_StringString() = {
//    assertEquals(0, CharSetUtils.count(null, null.asInstanceOf[String]))
//    assertEquals(0, CharSetUtils.count(null, ""))
//    assertEquals(0, CharSetUtils.count("", null.asInstanceOf[String]))
//    assertEquals(0, CharSetUtils.count("", ""))
//    assertEquals(0, CharSetUtils.count("", "a-e"))
//    assertEquals(0, CharSetUtils.count("hello", null.asInstanceOf[String]))
//    assertEquals(0, CharSetUtils.count("hello", ""))
//    assertEquals(1, CharSetUtils.count("hello", "a-e"))
//    assertEquals(3, CharSetUtils.count("hello", "l-p"))
//  }
//
//  @Test def testCount_StringStringarray() = {
//    assertEquals(0, CharSetUtils.count(null, null.asInstanceOf[Array[String]]))
//    assertEquals(0, CharSetUtils.count(null))
//    assertEquals(0, CharSetUtils.count(null, null))
//    assertEquals(0, CharSetUtils.count(null, "a-e"))
//    assertEquals(0, CharSetUtils.count("", null.asInstanceOf[Array[String]]))
//    assertEquals(0, CharSetUtils.count(""))
//    assertEquals(0, CharSetUtils.count("", null))
//    assertEquals(0, CharSetUtils.count("", "a-e"))
//    assertEquals(0, CharSetUtils.count("hello", null.asInstanceOf[Array[String]]))
//    assertEquals(0, CharSetUtils.count("hello"))
//    assertEquals(0, CharSetUtils.count("hello", null))
//    assertEquals(1, CharSetUtils.count("hello", "a-e"))
//    assertEquals(3, CharSetUtils.count("hello", "el"))
//    assertEquals(0, CharSetUtils.count("hello", "x"))
//    assertEquals(2, CharSetUtils.count("hello", "e-i"))
//    assertEquals(5, CharSetUtils.count("hello", "a-z"))
//    assertEquals(0, CharSetUtils.count("hello", ""))
//  }
//
//  @Test def testKeep_StringString() = {
//    assertNull(CharSetUtils.keep(null, null.asInstanceOf[String]))
//    assertNull(CharSetUtils.keep(null, ""))
//    assertEquals("", CharSetUtils.keep("", null.asInstanceOf[String]))
//    assertEquals("", CharSetUtils.keep("", ""))
//    assertEquals("", CharSetUtils.keep("", "a-e"))
//    assertEquals("", CharSetUtils.keep("hello", null.asInstanceOf[String]))
//    assertEquals("", CharSetUtils.keep("hello", ""))
//    assertEquals("", CharSetUtils.keep("hello", "xyz"))
//    assertEquals("hello", CharSetUtils.keep("hello", "a-z"))
//    assertEquals("hello", CharSetUtils.keep("hello", "oleh"))
//    assertEquals("ell", CharSetUtils.keep("hello", "el"))
//  }
//
//  @Test def testKeep_StringStringarray() = {
//    assertNull(CharSetUtils.keep(null, null.asInstanceOf[Array[String]]))
//    assertNull(CharSetUtils.keep(null))
//    assertNull(CharSetUtils.keep(null, null))
//    assertNull(CharSetUtils.keep(null, "a-e"))
//    assertEquals("", CharSetUtils.keep("", null.asInstanceOf[Array[String]]))
//    assertEquals("", CharSetUtils.keep(""))
//    assertEquals("", CharSetUtils.keep("", null))
//    assertEquals("", CharSetUtils.keep("", "a-e"))
//    assertEquals("", CharSetUtils.keep("hello", null.asInstanceOf[Array[String]]))
//    assertEquals("", CharSetUtils.keep("hello"))
//    assertEquals("", CharSetUtils.keep("hello", null))
//    assertEquals("e", CharSetUtils.keep("hello", "a-e"))
//    assertEquals("e", CharSetUtils.keep("hello", "a-e"))
//    assertEquals("ell", CharSetUtils.keep("hello", "el"))
//    assertEquals("hello", CharSetUtils.keep("hello", "elho"))
//    assertEquals("hello", CharSetUtils.keep("hello", "a-z"))
//    assertEquals("----", CharSetUtils.keep("----", "-"))
//    assertEquals("ll", CharSetUtils.keep("hello", "l"))
//  }
//
//  @Test def testDelete_StringString() = {
//    assertNull(CharSetUtils.delete(null, null.asInstanceOf[String]))
//    assertNull(CharSetUtils.delete(null, ""))
//    assertEquals("", CharSetUtils.delete("", null.asInstanceOf[String]))
//    assertEquals("", CharSetUtils.delete("", ""))
//    assertEquals("", CharSetUtils.delete("", "a-e"))
//    assertEquals("hello", CharSetUtils.delete("hello", null.asInstanceOf[String]))
//    assertEquals("hello", CharSetUtils.delete("hello", ""))
//    assertEquals("hllo", CharSetUtils.delete("hello", "a-e"))
//    assertEquals("he", CharSetUtils.delete("hello", "l-p"))
//    assertEquals("hello", CharSetUtils.delete("hello", "z"))
//  }
//
//  @Test def testDelete_StringStringarray() = {
//    assertNull(CharSetUtils.delete(null, null.asInstanceOf[Array[String]]))
//    assertNull(CharSetUtils.delete(null))
//    assertNull(CharSetUtils.delete(null, null))
//    assertNull(CharSetUtils.delete(null, "el"))
//    assertEquals("", CharSetUtils.delete("", null.asInstanceOf[Array[String]]))
//    assertEquals("", CharSetUtils.delete(""))
//    assertEquals("", CharSetUtils.delete("", null))
//    assertEquals("", CharSetUtils.delete("", "a-e"))
//    assertEquals("hello", CharSetUtils.delete("hello", null.asInstanceOf[Array[String]]))
//    assertEquals("hello", CharSetUtils.delete("hello"))
//    assertEquals("hello", CharSetUtils.delete("hello", null))
//    assertEquals("hello", CharSetUtils.delete("hello", "xyz"))
//    assertEquals("ho", CharSetUtils.delete("hello", "el"))
//    assertEquals("", CharSetUtils.delete("hello", "elho"))
//    assertEquals("hello", CharSetUtils.delete("hello", ""))
//    assertEquals("hello", CharSetUtils.delete("hello", ""))
//    assertEquals("", CharSetUtils.delete("hello", "a-z"))
//    assertEquals("", CharSetUtils.delete("----", "-"))
//    assertEquals("heo", CharSetUtils.delete("hello", "l"))
//  }
//}
