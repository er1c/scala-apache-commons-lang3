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
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Modifier
//import java.util
//import java.util.NoSuchElementException
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link org.apache.commons.lang3.CharRange}.
//  */
//class CharRangeTest extends JUnitSuite {
//  //-----------------------------------------------------------------------
//  @Test def testClass() = { // class changed to non-public in 3.0
//    assertFalse(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertTrue(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test def testConstructorAccessors_is() = {
//    val rangea = CharRange.is('a')
//    assertEquals('a', rangea.getStart)
//    assertEquals('a', rangea.getEnd)
//    assertFalse(rangea.isNegated)
//    assertEquals("a", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isNot() = {
//    val rangea = CharRange.isNot('a')
//    assertEquals('a', rangea.getStart)
//    assertEquals('a', rangea.getEnd)
//    assertTrue(rangea.isNegated)
//    assertEquals("^a", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isIn_Same() = {
//    val rangea = CharRange.isIn('a', 'a')
//    assertEquals('a', rangea.getStart)
//    assertEquals('a', rangea.getEnd)
//    assertFalse(rangea.isNegated)
//    assertEquals("a", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isIn_Normal() = {
//    val rangea = CharRange.isIn('a', 'e')
//    assertEquals('a', rangea.getStart)
//    assertEquals('e', rangea.getEnd)
//    assertFalse(rangea.isNegated)
//    assertEquals("a-e", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isIn_Reversed() = {
//    val rangea = CharRange.isIn('e', 'a')
//    assertEquals('a', rangea.getStart)
//    assertEquals('e', rangea.getEnd)
//    assertFalse(rangea.isNegated)
//    assertEquals("a-e", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isNotIn_Same() = {
//    val rangea = CharRange.isNotIn('a', 'a')
//    assertEquals('a', rangea.getStart)
//    assertEquals('a', rangea.getEnd)
//    assertTrue(rangea.isNegated)
//    assertEquals("^a", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isNotIn_Normal() = {
//    val rangea = CharRange.isNotIn('a', 'e')
//    assertEquals('a', rangea.getStart)
//    assertEquals('e', rangea.getEnd)
//    assertTrue(rangea.isNegated)
//    assertEquals("^a-e", rangea.toString)
//  }
//
//  @Test def testConstructorAccessors_isNotIn_Reversed() = {
//    val rangea = CharRange.isNotIn('e', 'a')
//    assertEquals('a', rangea.getStart)
//    assertEquals('e', rangea.getEnd)
//    assertTrue(rangea.isNegated)
//    assertEquals("^a-e", rangea.toString)
//  }
//
//  @Test def testEquals_Object() = {
//    val rangea = CharRange.is('a')
//    val rangeae = CharRange.isIn('a', 'e')
//    val rangenotbf = CharRange.isIn('b', 'f')
//    assertNotEquals(null, rangea)
//    assertEquals(rangea, rangea)
//    assertEquals(rangea, CharRange.is('a'))
//    assertEquals(rangeae, rangeae)
//    assertEquals(rangeae, CharRange.isIn('a', 'e'))
//    assertEquals(rangenotbf, rangenotbf)
//    assertEquals(rangenotbf, CharRange.isIn('b', 'f'))
//    assertNotEquals(rangea, rangeae)
//    assertNotEquals(rangea, rangenotbf)
//    assertNotEquals(rangeae, rangea)
//    assertNotEquals(rangeae, rangenotbf)
//    assertNotEquals(rangenotbf, rangea)
//    assertNotEquals(rangenotbf, rangeae)
//  }
//
//  @Test def testHashCode() = {
//    val rangea = CharRange.is('a')
//    val rangeae = CharRange.isIn('a', 'e')
//    val rangenotbf = CharRange.isIn('b', 'f')
//    assertEquals(rangea.hashCode, rangea.hashCode)
//    assertEquals(rangea.hashCode, CharRange.is('a').hashCode)
//    assertEquals(rangeae.hashCode, rangeae.hashCode)
//    assertEquals(rangeae.hashCode, CharRange.isIn('a', 'e').hashCode)
//    assertEquals(rangenotbf.hashCode, rangenotbf.hashCode)
//    assertEquals(rangenotbf.hashCode, CharRange.isIn('b', 'f').hashCode)
//    assertNotEquals(rangea.hashCode, rangeae.hashCode)
//    assertNotEquals(rangea.hashCode, rangenotbf.hashCode)
//    assertNotEquals(rangeae.hashCode, rangea.hashCode)
//    assertNotEquals(rangeae.hashCode, rangenotbf.hashCode)
//    assertNotEquals(rangenotbf.hashCode, rangea.hashCode)
//    assertNotEquals(rangenotbf.hashCode, rangeae.hashCode)
//  }
//
//  @Test def testContains_Char() = {
//    var range = CharRange.is('c')
//    assertFalse(range.contains('b'))
//    assertTrue(range.contains('c'))
//    assertFalse(range.contains('d'))
//    assertFalse(range.contains('e'))
//    range = CharRange.isIn('c', 'd')
//    assertFalse(range.contains('b'))
//    assertTrue(range.contains('c'))
//    assertTrue(range.contains('d'))
//    assertFalse(range.contains('e'))
//    range = CharRange.isIn('d', 'c')
//    assertFalse(range.contains('b'))
//    assertTrue(range.contains('c'))
//    assertTrue(range.contains('d'))
//    assertFalse(range.contains('e'))
//    range = CharRange.isNotIn('c', 'd')
//    assertTrue(range.contains('b'))
//    assertFalse(range.contains('c'))
//    assertFalse(range.contains('d'))
//    assertTrue(range.contains('e'))
//    assertTrue(range.contains(0.toChar))
//    assertTrue(range.contains(Character.MAX_VALUE))
//  }
//
//  @Test def testContains_Charrange() = {
//    val a = CharRange.is('a')
//    val b = CharRange.is('b')
//    val c = CharRange.is('c')
//    val c2 = CharRange.is('c')
//    val d = CharRange.is('d')
//    val e = CharRange.is('e')
//    val cd = CharRange.isIn('c', 'd')
//    val bd = CharRange.isIn('b', 'd')
//    val bc = CharRange.isIn('b', 'c')
//    val ab = CharRange.isIn('a', 'b')
//    val de = CharRange.isIn('d', 'e')
//    val ef = CharRange.isIn('e', 'f')
//    val ae = CharRange.isIn('a', 'e')
//    // normal/normal
//    assertFalse(c.contains(b))
//    assertTrue(c.contains(c))
//    assertTrue(c.contains(c2))
//    assertFalse(c.contains(d))
//    assertFalse(c.contains(cd))
//    assertFalse(c.contains(bd))
//    assertFalse(c.contains(bc))
//    assertFalse(c.contains(ab))
//    assertFalse(c.contains(de))
//    assertTrue(cd.contains(c))
//    assertTrue(bd.contains(c))
//    assertTrue(bc.contains(c))
//    assertFalse(ab.contains(c))
//    assertFalse(de.contains(c))
//    assertTrue(ae.contains(b))
//    assertTrue(ae.contains(ab))
//    assertTrue(ae.contains(bc))
//    assertTrue(ae.contains(cd))
//    assertTrue(ae.contains(de))
//    val notb = CharRange.isNot('b')
//    val notc = CharRange.isNot('c')
//    val notd = CharRange.isNot('d')
//    val notab = CharRange.isNotIn('a', 'b')
//    val notbc = CharRange.isNotIn('b', 'c')
//    val notbd = CharRange.isNotIn('b', 'd')
//    val notcd = CharRange.isNotIn('c', 'd')
//    val notde = CharRange.isNotIn('d', 'e')
//    val notae = CharRange.isNotIn('a', 'e')
//    val all = CharRange.isIn(0.toChar, Character.MAX_VALUE)
//    val allbutfirst = CharRange.isIn(1.toChar, Character.MAX_VALUE)
//    // normal/negated
//    assertFalse(c.contains(notc))
//    assertFalse(c.contains(notbd))
//    assertTrue(all.contains(notc))
//    assertTrue(all.contains(notbd))
//    assertFalse(allbutfirst.contains(notc))
//    assertFalse(allbutfirst.contains(notbd))
//    // negated/normal
//    assertTrue(notc.contains(a))
//    assertTrue(notc.contains(b))
//    assertFalse(notc.contains(c))
//    assertTrue(notc.contains(d))
//    assertTrue(notc.contains(e))
//    assertTrue(notc.contains(ab))
//    assertFalse(notc.contains(bc))
//    assertFalse(notc.contains(bd))
//    assertFalse(notc.contains(cd))
//    assertTrue(notc.contains(de))
//    assertFalse(notc.contains(ae))
//    assertFalse(notc.contains(all))
//    assertFalse(notc.contains(allbutfirst))
//    assertTrue(notbd.contains(a))
//    assertFalse(notbd.contains(b))
//    assertFalse(notbd.contains(c))
//    assertFalse(notbd.contains(d))
//    assertTrue(notbd.contains(e))
//    assertTrue(notcd.contains(ab))
//    assertFalse(notcd.contains(bc))
//    assertFalse(notcd.contains(bd))
//    assertFalse(notcd.contains(cd))
//    assertFalse(notcd.contains(de))
//    assertFalse(notcd.contains(ae))
//    assertTrue(notcd.contains(ef))
//    assertFalse(notcd.contains(all))
//    assertFalse(notcd.contains(allbutfirst))
//    // negated/negated
//    assertFalse(notc.contains(notb))
//    assertTrue(notc.contains(notc))
//    assertFalse(notc.contains(notd))
//    assertFalse(notc.contains(notab))
//    assertTrue(notc.contains(notbc))
//    assertTrue(notc.contains(notbd))
//    assertTrue(notc.contains(notcd))
//    assertFalse(notc.contains(notde))
//    assertFalse(notbd.contains(notb))
//    assertFalse(notbd.contains(notc))
//    assertFalse(notbd.contains(notd))
//    assertFalse(notbd.contains(notab))
//    assertFalse(notbd.contains(notbc))
//    assertTrue(notbd.contains(notbd))
//    assertFalse(notbd.contains(notcd))
//    assertFalse(notbd.contains(notde))
//    assertTrue(notbd.contains(notae))
//  }
//
//  @Test def testContainsNullArg() = {
//    val range = CharRange.is('a')
//    val e = assertThrows(classOf[NullPointerException], () => range.contains(null))
//    assertEquals("The Range must not be null", e.getMessage)
//  }
//
//  @Test def testIterator() = {
//    val a = CharRange.is('a')
//    val ad = CharRange.isIn('a', 'd')
//    val nota = CharRange.isNot('a')
//    val emptySet = CharRange.isNotIn(0.toChar, Character.MAX_VALUE)
//    val notFirst = CharRange.isNotIn(1.toChar, Character.MAX_VALUE)
//    val notLast = CharRange.isNotIn(0.toChar, (Character.MAX_VALUE - 1).toChar)
//    val aIt = a.iterator
//    assertNotNull(aIt)
//    assertTrue(aIt.hasNext)
//    assertEquals(Character.valueOf('a'), aIt.next)
//    assertFalse(aIt.hasNext)
//    val adIt = ad.iterator
//    assertNotNull(adIt)
//    assertTrue(adIt.hasNext)
//    assertEquals(Character.valueOf('a'), adIt.next)
//    assertEquals(Character.valueOf('b'), adIt.next)
//    assertEquals(Character.valueOf('c'), adIt.next)
//    assertEquals(Character.valueOf('d'), adIt.next)
//    assertFalse(adIt.hasNext)
//    val notaIt = nota.iterator
//    assertNotNull(notaIt)
//    assertTrue(notaIt.hasNext)
//    while ( {
//      notaIt.hasNext
//    }) {
//      val c = notaIt.next
//      assertNotEquals('a', c.charValue)
//    }
//    val emptySetIt = emptySet.iterator
//    assertNotNull(emptySetIt)
//    assertFalse(emptySetIt.hasNext)
//    assertThrows(classOf[NoSuchElementException], emptySetIt.next)
//    val notFirstIt = notFirst.iterator
//    assertNotNull(notFirstIt)
//    assertTrue(notFirstIt.hasNext)
//    assertEquals(Character.valueOf(0.toChar), notFirstIt.next)
//    assertFalse(notFirstIt.hasNext)
//    assertThrows(classOf[NoSuchElementException], notFirstIt.next)
//    val notLastIt = notLast.iterator
//    assertNotNull(notLastIt)
//    assertTrue(notLastIt.hasNext)
//    assertEquals(Character.valueOf(Character.MAX_VALUE), notLastIt.next)
//    assertFalse(notLastIt.hasNext)
//    assertThrows(classOf[NoSuchElementException], notLastIt.next)
//  }
//
//  @Test def testSerialization() = {
//    var range = CharRange.is('a')
//    assertEquals(range, SerializationUtils.clone(range))
//    range = CharRange.isIn('a', 'e')
//    assertEquals(range, SerializationUtils.clone(range))
//    range = CharRange.isNotIn('a', 'e')
//    assertEquals(range, SerializationUtils.clone(range))
//  }
//
//  @Test def testIteratorRemove() = {
//    val a = CharRange.is('a')
//    val aIt = a.iterator
//    assertThrows(classOf[UnsupportedOperationException], aIt.remove)
//  }
//}
