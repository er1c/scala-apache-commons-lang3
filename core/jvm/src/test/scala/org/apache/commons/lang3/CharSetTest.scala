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
//import org.junit.Assert.assertNotEquals
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Modifier
//import org.junit.Test
//
//
///**
//  * Unit tests {@link org.apache.commons.lang3.CharSet}.
//  */
//class CharSetTest {
//  //-----------------------------------------------------------------------
//  @Test def testClass() = {
//    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test def testGetInstance() = {
//    assertSame(CharSet.EMPTY, CharSet.getInstance(null.asInstanceOf[String]))
//    assertSame(CharSet.EMPTY, CharSet.getInstance(""))
//    assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"))
//    assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"))
//    assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"))
//    assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"))
//    assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"))
//  }
//
//  @Test def testGetInstance_Stringarray() = {
//    assertNull(CharSet.getInstance(null.asInstanceOf[Array[String]]))
//    assertEquals("[]", CharSet.getInstance(new Array[String](0)).toString)
//    assertEquals("[]", CharSet.getInstance(Array[String](null)).toString)
//    assertEquals("[a-e]", CharSet.getInstance(Array[String]("a-e")).toString)
//  }
//
//  @Test def testConstructor_String_simple() = {
//    var set = null
//    var array = null
//    set = CharSet.getInstance(null.asInstanceOf[String])
//    array = set.getCharRanges
//    assertEquals("[]", set.toString)
//    assertEquals(0, array.length)
//    set = CharSet.getInstance("")
//    array = set.getCharRanges
//    assertEquals("[]", set.toString)
//    assertEquals(0, array.length)
//    set = CharSet.getInstance("a")
//    array = set.getCharRanges
//    assertEquals("[a]", set.toString)
//    assertEquals(1, array.length)
//    assertEquals("a", array(0).toString)
//    set = CharSet.getInstance("^a")
//    array = set.getCharRanges
//    assertEquals("[^a]", set.toString)
//    assertEquals(1, array.length)
//    assertEquals("^a", array(0).toString)
//    set = CharSet.getInstance("a-e")
//    array = set.getCharRanges
//    assertEquals("[a-e]", set.toString)
//    assertEquals(1, array.length)
//    assertEquals("a-e", array(0).toString)
//    set = CharSet.getInstance("^a-e")
//    array = set.getCharRanges
//    assertEquals("[^a-e]", set.toString)
//    assertEquals(1, array.length)
//    assertEquals("^a-e", array(0).toString)
//  }
//
//  @Test def testConstructor_String_combo() = {
//    var set = null
//    var array = null
//    set = CharSet.getInstance("abc")
//    array = set.getCharRanges
//    assertEquals(3, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('b')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('c')))
//    set = CharSet.getInstance("a-ce-f")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('a', 'c')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('e', 'f')))
//    set = CharSet.getInstance("ae-f")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('e', 'f')))
//    set = CharSet.getInstance("e-fa")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('e', 'f')))
//    set = CharSet.getInstance("ae-fm-pz")
//    array = set.getCharRanges
//    assertEquals(4, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('e', 'f')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('m', 'p')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('z')))
//  }
//
//  @Test def testConstructor_String_comboNegated() = {
//    var set = null
//    var array = null
//    set = CharSet.getInstance("^abc")
//    array = set.getCharRanges
//    assertEquals(3, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('b')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('c')))
//    set = CharSet.getInstance("b^ac")
//    array = set.getCharRanges
//    assertEquals(3, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('b')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('c')))
//    set = CharSet.getInstance("db^ac")
//    array = set.getCharRanges
//    assertEquals(4, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('d')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('b')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('c')))
//    set = CharSet.getInstance("^b^a")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('b')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('a')))
//    set = CharSet.getInstance("b^a-c^z")
//    array = set.getCharRanges
//    assertEquals(3, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNotIn('a', 'c')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('z')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('b')))
//  }
//
//  @Test def testConstructor_String_oddDash() = {
//    var set = null
//    var array = null
//    set = CharSet.getInstance("-")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//    set = CharSet.getInstance("--")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//    set = CharSet.getInstance("---")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//    set = CharSet.getInstance("----")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//    set = CharSet.getInstance("-a")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a')))
//    set = CharSet.getInstance("a-")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//    set = CharSet.getInstance("a--")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('a', '-')))
//    set = CharSet.getInstance("--a")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('-', 'a')))
//  }
//
//  @Test def testConstructor_String_oddNegate() = {
//    var set = null
//    var array = null
//    set = CharSet.getInstance("^")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('^'))) // "^"
//    set = CharSet.getInstance("^^")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('^'))) // "^^"
//    set = CharSet.getInstance("^^^")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('^')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('^')))
//    set = CharSet.getInstance("^^^^")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('^'))) // "^^" x2
//    set = CharSet.getInstance("a^")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.is('a'))) // "a"
//    assertTrue(ArrayUtils.contains(array, CharRange.is('^')))
//    set = CharSet.getInstance("^a-")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('a'))) // "^a"
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-'))) // "-"
//    set = CharSet.getInstance("^^-c")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNotIn('^', 'c'))) // "^^-c"
//    set = CharSet.getInstance("^c-^")
//    array = set.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNotIn('c', '^'))) // "^c-^"
//    set = CharSet.getInstance("^c-^d")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNotIn('c', '^')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('d'))) // "d"
//    set = CharSet.getInstance("^^-")
//    array = set.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isNot('^')))
//    assertTrue(ArrayUtils.contains(array, CharRange.is('-')))
//  }
//
//  @Test def testConstructor_String_oddCombinations() = {
//    var set = null
//    var array = null
//    set = CharSet.getInstance("a-^c")
//    array = set.getCharRanges
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('a', '^'))) // "a-^"
//    assertTrue(ArrayUtils.contains(array, CharRange.is('c'))) // "c"
//    assertFalse(set.contains('b'))
//    assertTrue(set.contains('^'))
//    assertTrue(set.contains('_')) // between ^ and a
//    assertTrue(set.contains('c'))
//    set = CharSet.getInstance("^a-^c")
//    array = set.getCharRanges
//    assertTrue(ArrayUtils.contains(array, CharRange.isNotIn('a', '^'))) // "^a-^"
//    assertTrue(ArrayUtils.contains(array, CharRange.is('c')))
//    assertTrue(set.contains('b'))
//    assertFalse(set.contains('^'))
//    assertFalse(set.contains('_'))
//    set = CharSet.getInstance("a- ^-- ") //contains everything
//    array = set.getCharRanges
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('a', ' '))) // "a- "
//    assertTrue(ArrayUtils.contains(array, CharRange.isNotIn('-', ' '))) // "^-- "
//    assertTrue(set.contains('#'))
//    assertTrue(set.contains('^'))
//    assertTrue(set.contains('a'))
//    assertTrue(set.contains('*'))
//    assertTrue(set.contains('A'))
//    set = CharSet.getInstance("^-b")
//    array = set.getCharRanges
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('^', 'b'))) // "^-b"
//    assertTrue(set.contains('b'))
//    assertTrue(set.contains('_'))
//    assertFalse(set.contains('A'))
//    assertTrue(set.contains('^'))
//    set = CharSet.getInstance("b-^")
//    array = set.getCharRanges
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('^', 'b'))) // "b-^"
//    assertTrue(set.contains('b'))
//    assertTrue(set.contains('^'))
//    assertTrue(set.contains('a')) // between ^ and b
//    assertFalse(set.contains('c'))
//  }
//
//  @Test def testEquals_Object() = {
//    val abc = CharSet.getInstance("abc")
//    val abc2 = CharSet.getInstance("abc")
//    val atoc = CharSet.getInstance("a-c")
//    val atoc2 = CharSet.getInstance("a-c")
//    val notatoc = CharSet.getInstance("^a-c")
//    val notatoc2 = CharSet.getInstance("^a-c")
//    assertNotEquals(null, abc)
//    assertEquals(abc, abc)
//    assertEquals(abc, abc2)
//    assertNotEquals(abc, atoc)
//    assertNotEquals(abc, notatoc)
//    assertNotEquals(atoc, abc)
//    assertEquals(atoc, atoc)
//    assertEquals(atoc, atoc2)
//    assertNotEquals(atoc, notatoc)
//    assertNotEquals(notatoc, abc)
//    assertNotEquals(notatoc, atoc)
//    assertEquals(notatoc, notatoc)
//    assertEquals(notatoc, notatoc2)
//  }
//
//  @Test def testHashCode() = {
//    val abc = CharSet.getInstance("abc")
//    val abc2 = CharSet.getInstance("abc")
//    val atoc = CharSet.getInstance("a-c")
//    val atoc2 = CharSet.getInstance("a-c")
//    val notatoc = CharSet.getInstance("^a-c")
//    val notatoc2 = CharSet.getInstance("^a-c")
//    assertEquals(abc.hashCode, abc.hashCode)
//    assertEquals(abc.hashCode, abc2.hashCode)
//    assertEquals(atoc.hashCode, atoc.hashCode)
//    assertEquals(atoc.hashCode, atoc2.hashCode)
//    assertEquals(notatoc.hashCode, notatoc.hashCode)
//    assertEquals(notatoc.hashCode, notatoc2.hashCode)
//  }
//
//  @Test def testContains_Char() = {
//    val btod = CharSet.getInstance("b-d")
//    val dtob = CharSet.getInstance("d-b")
//    val bcd = CharSet.getInstance("bcd")
//    val bd = CharSet.getInstance("bd")
//    val notbtod = CharSet.getInstance("^b-d")
//    assertFalse(btod.contains('a'))
//    assertTrue(btod.contains('b'))
//    assertTrue(btod.contains('c'))
//    assertTrue(btod.contains('d'))
//    assertFalse(btod.contains('e'))
//    assertFalse(bcd.contains('a'))
//    assertTrue(bcd.contains('b'))
//    assertTrue(bcd.contains('c'))
//    assertTrue(bcd.contains('d'))
//    assertFalse(bcd.contains('e'))
//    assertFalse(bd.contains('a'))
//    assertTrue(bd.contains('b'))
//    assertFalse(bd.contains('c'))
//    assertTrue(bd.contains('d'))
//    assertFalse(bd.contains('e'))
//    assertTrue(notbtod.contains('a'))
//    assertFalse(notbtod.contains('b'))
//    assertFalse(notbtod.contains('c'))
//    assertFalse(notbtod.contains('d'))
//    assertTrue(notbtod.contains('e'))
//    assertFalse(dtob.contains('a'))
//    assertTrue(dtob.contains('b'))
//    assertTrue(dtob.contains('c'))
//    assertTrue(dtob.contains('d'))
//    assertFalse(dtob.contains('e'))
//    val array = dtob.getCharRanges
//    assertEquals("[b-d]", dtob.toString)
//    assertEquals(1, array.length)
//  }
//
//  @Test def testSerialization() = {
//    var set = CharSet.getInstance("a")
//    assertEquals(set, SerializationUtils.clone(set))
//    set = CharSet.getInstance("a-e")
//    assertEquals(set, SerializationUtils.clone(set))
//    set = CharSet.getInstance("be-f^a-z")
//    assertEquals(set, SerializationUtils.clone(set))
//  }
//
//  @Test def testStatics() = {
//    var array = null
//    array = CharSet.EMPTY.getCharRanges
//    assertEquals(0, array.length)
//    array = CharSet.ASCII_ALPHA.getCharRanges
//    assertEquals(2, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('a', 'z')))
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('A', 'Z')))
//    array = CharSet.ASCII_ALPHA_LOWER.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('a', 'z')))
//    array = CharSet.ASCII_ALPHA_UPPER.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('A', 'Z')))
//    array = CharSet.ASCII_NUMERIC.getCharRanges
//    assertEquals(1, array.length)
//    assertTrue(ArrayUtils.contains(array, CharRange.isIn('0', '9')))
//  }
//
//  @Test def testJavadocExamples() = {
//    assertFalse(CharSet.getInstance("^a-c").contains('a'))
//    assertTrue(CharSet.getInstance("^a-c").contains('d'))
//    assertTrue(CharSet.getInstance("^^a-c").contains('a'))
//    assertFalse(CharSet.getInstance("^^a-c").contains('^'))
//    assertTrue(CharSet.getInstance("^a-cd-f").contains('d'))
//    assertTrue(CharSet.getInstance("a-c^").contains('^'))
//    assertTrue(CharSet.getInstance("^", "a-c").contains('^'))
//  }
//}
