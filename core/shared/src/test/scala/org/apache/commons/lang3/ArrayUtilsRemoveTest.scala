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

import java.lang.{
  Boolean => JavaBoolean,
  Byte => JavaByte,
  Double => JavaDouble,
  Float => JavaFloat,
  Long => JavaLong,
  Short => JavaShort
}
import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * Tests ArrayUtils remove and removeElement methods.
  */
class ArrayUtilsRemoveTest extends TestHelpers {
  @Test def testRemoveAllBooleanOccurences(): Unit = {
    var a: Array[Boolean] = null
    assertNull(ArrayUtils.removeAllOccurences(a, true))
    a = new Array[Boolean](0)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.removeAllOccurences(a, true))
    a = Array[Boolean](true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.removeAllOccurences(a, true))
    a = Array[Boolean](true, true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.removeAllOccurences(a, true))
    a = Array[Boolean](false, true, true, false, true)
    assertArrayEquals(Array[Boolean](false, false), ArrayUtils.removeAllOccurences(a, true))
    a = Array[Boolean](false, true, true, false, true)
    assertArrayEquals(Array[Boolean](true, true, true), ArrayUtils.removeAllOccurences(a, false))
  }

  @Test def testRemoveAllBooleanOccurrences(): Unit = {
    var a: Array[Boolean] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, true))
    a = new Array[Boolean](0)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.removeAllOccurrences(a, true))
    a = Array[Boolean](true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.removeAllOccurrences(a, true))
    a = Array[Boolean](true, true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.removeAllOccurrences(a, true))
    a = Array[Boolean](false, true, true, false, true)
    assertArrayEquals(Array[Boolean](false, false), ArrayUtils.removeAllOccurrences(a, true))
    a = Array[Boolean](false, true, true, false, true)
    assertArrayEquals(Array[Boolean](true, true, true), ArrayUtils.removeAllOccurrences(a, false))
  }

  @Test def testRemoveAllByteOccurences(): Unit = {
    var a: Array[Byte] = null
    assertNull(ArrayUtils.removeAllOccurences(a, 2.toByte))
    a = new Array[Byte](0)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.removeAllOccurences(a, 2.toByte))
    a = Array[Byte](2)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.removeAllOccurences(a, 2.toByte))
    a = Array[Byte](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.removeAllOccurences(a, 2.toByte))
    a = Array[Byte](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Byte](1, 3), ArrayUtils.removeAllOccurences(a, 2.toByte))
    a = Array[Byte](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Byte](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurences(a, 4.toByte))
  }

  @Test def testRemoveAllByteOccurrences(): Unit = {
    var a: Array[Byte] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, 2.toByte))
    a = new Array[Byte](0)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.removeAllOccurrences(a, 2.toByte))
    a = Array[Byte](2)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.removeAllOccurrences(a, 2.toByte))
    a = Array[Byte](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.removeAllOccurrences(a, 2.toByte))
    a = Array[Byte](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Byte](1, 3), ArrayUtils.removeAllOccurrences(a, 2.toByte))
    a = Array[Byte](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Byte](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurrences(a, 4.toByte))
  }

  @Test def testRemoveAllCharOccurences(): Unit = {
    var a: Array[Char] = null
    assertNull(ArrayUtils.removeAllOccurences(a, '2'))
    a = new Array[Char](0)
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.removeAllOccurences(a, '2'))
    a = Array[Char]('2')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.removeAllOccurences(a, '2'))
    a = Array[Char]('2', '2')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.removeAllOccurences(a, '2'))
    a = Array[Char]('1', '2', '2', '3', '2')
    assertArrayEquals(Array[Char]('1', '3'), ArrayUtils.removeAllOccurences(a, '2'))
    a = Array[Char]('1', '2', '2', '3', '2')
    assertArrayEquals(Array[Char]('1', '2', '2', '3', '2'), ArrayUtils.removeAllOccurences(a, '4'))
  }

  @Test def testRemoveAllCharOccurrences(): Unit = {
    var a: Array[Char] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, '2'))
    a = new Array[Char](0)
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.removeAllOccurrences(a, '2'))
    a = Array[Char]('2')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.removeAllOccurrences(a, '2'))
    a = Array[Char]('2', '2')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.removeAllOccurrences(a, '2'))
    a = Array[Char]('1', '2', '2', '3', '2')
    assertArrayEquals(Array[Char]('1', '3'), ArrayUtils.removeAllOccurrences(a, '2'))
    a = Array[Char]('1', '2', '2', '3', '2')
    assertArrayEquals(Array[Char]('1', '2', '2', '3', '2'), ArrayUtils.removeAllOccurrences(a, '4'))
  }

  @Test def testRemoveAllDoubleOccurences(): Unit = {
    var a: Array[Double] = null
    assertNull(ArrayUtils.removeAllOccurences(a, 2))
    a = new Array[Double](0)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Double](2)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Double](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Double](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Double](1, 3), ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Double](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Double](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurences(a, 4))
  }

  @Test def testRemoveAllDoubleOccurrences(): Unit = {
    var a: Array[Double] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, 2))
    a = new Array[Double](0)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Double](2)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Double](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Double](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Double](1, 3), ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Double](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Double](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurrences(a, 4))
  }

  @Test def testRemoveAllFloatOccurences(): Unit = {
    var a: Array[Float] = null
    assertNull(ArrayUtils.removeAllOccurences(a, 2))
    a = new Array[Float](0)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Float](2)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Float](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Float](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Float](1, 3), ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Float](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Float](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurences(a, 4))
  }

  @Test def testRemoveAllFloatOccurrences(): Unit = {
    var a: Array[Float] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, 2))
    a = new Array[Float](0)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Float](2)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Float](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Float](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Float](1, 3), ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Float](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Float](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurrences(a, 4))
  }

  @Test def testRemoveAllIntOccurences(): Unit = {
    var a: Array[Int] = null
    assertNull(ArrayUtils.removeAllOccurences(a, 2))
    a = new Array[Int](0)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Int](2)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Int](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Int](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Int](1, 3), ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Int](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Int](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurences(a, 4))
  }

  @Test def testRemoveAllIntOccurrences(): Unit = {
    var a: Array[Int] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, 2))
    a = new Array[Int](0)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Int](2)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Int](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Int](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Int](1, 3), ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Int](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Int](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurrences(a, 4))
  }

  @Test def testRemoveAllLongOccurences(): Unit = {
    var a: Array[Long] = null
    assertNull(ArrayUtils.removeAllOccurences(a, 2))
    a = new Array[Long](0)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Long](2)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Long](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Long](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Long](1, 3), ArrayUtils.removeAllOccurences(a, 2))
    a = Array[Long](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Long](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurences(a, 4))
  }

  @Test def testRemoveAllLongOccurrences(): Unit = {
    var a: Array[Long] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, 2))
    a = new Array[Long](0)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Long](2)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Long](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Long](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Long](1, 3), ArrayUtils.removeAllOccurrences(a, 2))
    a = Array[Long](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Long](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurrences(a, 4))
  }

//  @Test def testRemoveAllObjectOccurences(): Unit = {
//    var a: Array[String] = null
//    assertNull(ArrayUtils.removeAllOccurences(a, "2"))
//    a = new Array[String](0)
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.removeAllOccurences(a, "2"))
//    a = Array[String]("2")
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.removeAllOccurences(a, "2"))
//    a = Array[String]("2", "2")
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.removeAllOccurences(a, "2"))
//    a = Array[String]("1", "2", "2", "3", "2")
//    assertArrayEquals(Array[String]("1", "3"), ArrayUtils.removeAllOccurences(a, "2"))
//    a = Array[String]("1", "2", "2", "3", "2")
//    assertArrayEquals(Array[String]("1", "2", "2", "3", "2"), ArrayUtils.removeAllOccurences(a, "4"))
//  }

//  @Test def testRemoveAllObjectOccurrences(): Unit = {
//    var a: Array[String] = null
//    assertNull(ArrayUtils.removeAllOccurrences(a, "2"))
//    a = new Array[String](0)
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.removeAllOccurrences(a, "2"))
//    a = Array[String]("2")
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.removeAllOccurrences(a, "2"))
//    a = Array[String]("2", "2")
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.removeAllOccurrences(a, "2"))
//    a = Array[String]("1", "2", "2", "3", "2")
//    assertArrayEquals(Array[String]("1", "3"), ArrayUtils.removeAllOccurrences(a, "2"))
//    a = Array[String]("1", "2", "2", "3", "2")
//    assertArrayEquals(Array[String]("1", "2", "2", "3", "2"), ArrayUtils.removeAllOccurrences(a, "4"))
//  }

  @Test def testRemoveAllShortOccurences(): Unit = {
    var a: Array[Short] = null
    assertNull(ArrayUtils.removeAllOccurences(a, 2.toShort))
    a = new Array[Short](0)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.removeAllOccurences(a, 2.toShort))
    a = Array[Short](2)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.removeAllOccurences(a, 2.toShort))
    a = Array[Short](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.removeAllOccurences(a, 2.toShort))
    a = Array[Short](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Short](1, 3), ArrayUtils.removeAllOccurences(a, 2.toShort))
    a = Array[Short](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Short](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurences(a, 4.toShort))
  }

  @Test def testRemoveAllShortOccurrences(): Unit = {
    var a: Array[Short] = null
    assertNull(ArrayUtils.removeAllOccurrences(a, 2.toShort))
    a = new Array[Short](0)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2.toShort))
    a = Array[Short](2)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2.toShort))
    a = Array[Short](2, 2)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.removeAllOccurrences(a, 2.toShort))
    a = Array[Short](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Short](1, 3), ArrayUtils.removeAllOccurrences(a, 2.toShort))
    a = Array[Short](1, 2, 2, 3, 2)
    assertArrayEquals(Array[Short](1, 2, 2, 3, 2), ArrayUtils.removeAllOccurrences(a, 4.toShort))
  }

  @Test def testRemoveBooleanArray(): Unit = {
    var array: Array[Boolean] = null
    array = ArrayUtils.remove(Array[Boolean](true), 0)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Boolean](true, false), 0)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Boolean](true, false), 1)
    assertArrayEquals(Array[Boolean](true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Boolean](true, false, true), 1)
    assertArrayEquals(Array[Boolean](true, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Boolean](true, false), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Boolean](true, false), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Boolean]], 0))
    ()
  }

  @Test def testRemoveByteArray(): Unit = {
    var array: Array[Byte] = null
    array = ArrayUtils.remove(Array[Byte](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Byte](1, 2), 0)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Byte](1, 2), 1)
    assertArrayEquals(Array[Byte](1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Byte](1, 2, 1), 1)
    assertArrayEquals(Array[Byte](1, 1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Byte](1, 2), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Byte](1, 2), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Byte]], 0))
    ()
  }

  @Test def testRemoveCharArray(): Unit = {
    var array: Array[Char] = null
    array = ArrayUtils.remove(Array[Char]('a'), 0)
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Char]('a', 'b'), 0)
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Char]('a', 'b'), 1)
    assertArrayEquals(Array[Char]('a'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Char]('a', 'b', 'c'), 1)
    assertArrayEquals(Array[Char]('a', 'c'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Char]('a', 'b'), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Char]('a', 'b'), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Char]], 0))
    ()
  }

  @Test def testRemoveDoubleArray(): Unit = {
    var array: Array[Double] = null
    array = ArrayUtils.remove(Array[Double](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Double](1, 2), 0)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Double](1, 2), 1)
    assertArrayEquals(Array[Double](1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Double](1, 2, 1), 1)
    assertArrayEquals(Array[Double](1, 1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Double](1, 2), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Double](1, 2), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Double]], 0))
    ()
  }

  @Test def testRemoveElementBooleanArray(): Unit = {
    var array: Array[Boolean] = null
    array = ArrayUtils.removeElement(null, true)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_BOOLEAN_ARRAY, true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Boolean](true), true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Boolean](true, false), true)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Boolean](true, false, true), true)
    assertArrayEquals(Array[Boolean](false, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementByteArray(): Unit = {
    var array: Array[Byte] = null
    array = ArrayUtils.removeElement(null.asInstanceOf[Array[Byte]], 1.toByte)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_BYTE_ARRAY, 1.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Byte](1), 1.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Byte](1, 2), 1.toByte)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Byte](1, 2, 1), 1.toByte)
    assertArrayEquals(Array[Byte](2, 1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementCharArray(): Unit = {
    var array: Array[Char] = null
    array = ArrayUtils.removeElement(null.asInstanceOf[Array[Char]], 'a')
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_CHAR_ARRAY, 'a')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Char]('a'), 'a')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Char]('a', 'b'), 'a')
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Char]('a', 'b', 'a'), 'a')
    assertArrayEquals(Array[Char]('b', 'a'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
  }

  @Test
  @SuppressWarnings(Array("cast")) def testRemoveElementDoubleArray(): Unit = {
    var array: Array[Double] = null
    array = ArrayUtils.removeElement(null, 1.toDouble)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_DOUBLE_ARRAY, 1.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Double](1), 1.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Double](1, 2), 1.toDouble)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Double](1, 2, 1), 1.toDouble)
    assertArrayEquals(Array[Double](2, 1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
  }

  @Test
  @SuppressWarnings(Array("cast")) def testRemoveElementFloatArray(): Unit = {
    var array: Array[Float] = null
    array = ArrayUtils.removeElement(null.asInstanceOf[Array[Float]], 1.toFloat)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_FLOAT_ARRAY, 1.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Float](1), 1.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Float](1, 2), 1.toFloat)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Float](1, 2, 1), 1.toFloat)
    assertArrayEquals(Array[Float](2, 1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementIntArray(): Unit = {
    var array: Array[Int] = null
    array = ArrayUtils.removeElement(null.asInstanceOf[Array[Int]], 1)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_INT_ARRAY, 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Int](1), 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Int](1, 2), 1)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Int](1, 2, 1), 1)
    assertArrayEquals(Array[Int](2, 1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
  }

  @Test
  @SuppressWarnings(Array("cast")) def testRemoveElementLongArray(): Unit = {
    var array: Array[Long] = null
    array = ArrayUtils.removeElement(null.asInstanceOf[Array[Long]], 1L)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_LONG_ARRAY, 1L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Long](1), 1L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Long](1, 2), 1L)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Long](1, 2, 1), 1L)
    assertArrayEquals(Array[Long](2, 1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
  }

//  @Test def testRemoveElementObjectArray(): Unit = {
//    var array: Array[AnyRef] = null
//    array = ArrayUtils.removeElement(null, "a")
//    assertNull(array)
//    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_OBJECT_ARRAY, "a")
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElement(Array[AnyRef]("a"), "a")
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElement(Array[AnyRef]("a", "b"), "a")
//    assertArrayEquals(Array[AnyRef]("b"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElement(Array[AnyRef]("a", "b", "a"), "a")
//    assertArrayEquals(Array[AnyRef]("b", "a"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//  }

  @Test def testRemoveElementShortArray(): Unit = {
    var array: Array[Short] = null
    array = ArrayUtils.removeElement(null.asInstanceOf[Array[Short]], 1.toShort)
    assertNull(array)
    array = ArrayUtils.removeElement(ArrayUtils.EMPTY_SHORT_ARRAY, 1.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Short](1), 1.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Short](1, 2), 1.toShort)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElement(Array[Short](1, 2, 1), 1.toShort)
    assertArrayEquals(Array[Short](2, 1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveFloatArray(): Unit = {
    var array: Array[Float] = null
    array = ArrayUtils.remove(Array[Float](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Float](1, 2), 0)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Float](1, 2), 1)
    assertArrayEquals(Array[Float](1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Float](1, 2, 1), 1)
    assertArrayEquals(Array[Float](1, 1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Float](1, 2), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Float](1, 2), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Float]], 0))
    ()
  }

  @Test def testRemoveIntArray(): Unit = {
    var array: Array[Int] = null
    array = ArrayUtils.remove(Array[Int](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Int](1, 2), 0)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Int](1, 2), 1)
    assertArrayEquals(Array[Int](1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Int](1, 2, 1), 1)
    assertArrayEquals(Array[Int](1, 1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Int](1, 2), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Int](1, 2), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Int]], 0))
    ()
  }

  @Test def testRemoveLongArray(): Unit = {
    var array: Array[Long] = null
    array = ArrayUtils.remove(Array[Long](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Long](1, 2), 0)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Long](1, 2), 1)
    assertArrayEquals(Array[Long](1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Long](1, 2, 1), 1)
    assertArrayEquals(Array[Long](1, 1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Long](1, 2), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Long](1, 2), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Long]], 0))
    ()
  }

  @Test def testRemoveNumberArray(): Unit = {
    val inarray = Array[Number](Integer.valueOf(1), JavaLong.valueOf(2), JavaByte.valueOf(3.toByte))
    assertEquals(3, inarray.length)
    var outarray: Array[Number] = null
    outarray = ArrayUtils.remove(inarray, 1)
    assertEquals(2, outarray.length)
    assertEquals(classOf[Number], outarray.getClass.getComponentType)
    outarray = ArrayUtils.remove(outarray, 1)
    assertEquals(1, outarray.length)
    assertEquals(classOf[Number], outarray.getClass.getComponentType)
    outarray = ArrayUtils.remove(outarray, 0)
    assertEquals(0, outarray.length)
    assertEquals(classOf[Number], outarray.getClass.getComponentType)
  }

//  @Test def testRemoveObjectArray(): Unit = {
//    var array: Array[AnyRef] = null
//    array = ArrayUtils.remove(Array[AnyRef]("a"), 0)
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.remove(Array[AnyRef]("a", "b"), 0)
//    assertArrayEquals(Array[AnyRef]("b"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.remove(Array[AnyRef]("a", "b"), 1)
//    assertArrayEquals(Array[AnyRef]("a"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.remove(Array[AnyRef]("a", "b", "c"), 1)
//    assertArrayEquals(Array[AnyRef]("a", "c"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[AnyRef]("a", "b"), -1))
//    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[AnyRef]("a", "b"), 2))
//    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[AnyRef]], 0))
//  }

  @Test def testRemoveShortArray(): Unit = {
    var array: Array[Short] = null
    array = ArrayUtils.remove(Array[Short](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Short](1, 2), 0)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Short](1, 2), 1)
    assertArrayEquals(Array[Short](1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.remove(Array[Short](1, 2, 1), 1)
    assertArrayEquals(Array[Short](1, 1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Short](1, 2), -1))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(Array[Short](1, 2), 2))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[Short]], 0))
    ()
  }
}
