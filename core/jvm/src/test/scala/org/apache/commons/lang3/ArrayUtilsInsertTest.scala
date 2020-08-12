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

//import java.lang.{Boolean => JavaBoolean, Byte => JavaByte, Double => JavaDouble, Float => JavaFloat, Long => JavaLong, Short => JavaShort}
import org.scalatestplus.junit.JUnitSuite
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertNull
//import org.junit.jupiter.api.Test
import org.junit.Test

/**
  * Tests ArrayUtils insert methods.
  */
class ArrayUtilsInsertTest extends JUnitSuite {
  @Test def testInsertBooleans(): Unit = {
    val array: Array[Boolean] = Array(true, false, true)
    val values: Array[Boolean] = Array(false, true, false)
    val result: Array[Boolean] = ArrayUtils.insert(42, array, null.asInstanceOf[Array[Boolean]])
    assertArrayEquals(array, result)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Boolean]], array))
    assertArrayEquals(new Array[Boolean](0), ArrayUtils.insert(0, new Array[Boolean](0), null))
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Boolean]], null))
    assertThrows[IndexOutOfBoundsException] { ArrayUtils.insert(-1, array, array) }
    assertThrows[IndexOutOfBoundsException] { ArrayUtils.insert(array.length + 1, array, array) }
    assertArrayEquals(Array[Boolean](false, true, false, true), ArrayUtils.insert(0, array, false))
    assertArrayEquals(Array[Boolean](true, false, false, true), ArrayUtils.insert(1, array, false))
    assertArrayEquals(Array[Boolean](true, false, true, false), ArrayUtils.insert(array.length, array, false))
    assertArrayEquals(Array[Boolean](false, true, false, true, false, true), ArrayUtils.insert(0, array, values))
    assertArrayEquals(Array[Boolean](true, false, true, false, false, true), ArrayUtils.insert(1, array, values))
    assertArrayEquals(
      Array[Boolean](true, false, true, false, true, false),
      ArrayUtils.insert(array.length, array, values))
  }

  @Test def testInsertBytes(): Unit = {
    val array: Array[Byte] = Array(1, 2, 3)
    val values: Array[Byte] = Array(4, 5, 6)
    val result: Array[Byte] = ArrayUtils.insert(42, array, null.asInstanceOf[Array[Byte]])
    assertArrayEquals(array, result)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Byte](0), ArrayUtils.insert(0, new Array[Byte](0), null))
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Byte]], null))
    assertThrows[IndexOutOfBoundsException] { ArrayUtils.insert(-1, array, array) }
    assertThrows[IndexOutOfBoundsException] { ArrayUtils.insert(array.length + 1, array, array) }
    assertArrayEquals(Array[Byte](0, 1, 2, 3), ArrayUtils.insert(0, array, 0.toByte))
    assertArrayEquals(Array[Byte](1, 0, 2, 3), ArrayUtils.insert(1, array, 0.toByte))
    assertArrayEquals(Array[Byte](1, 2, 3, 0), ArrayUtils.insert(array.length, array, 0.toByte))
    assertArrayEquals(Array[Byte](4, 5, 6, 1, 2, 3), ArrayUtils.insert(0, array, values))
    assertArrayEquals(Array[Byte](1, 4, 5, 6, 2, 3), ArrayUtils.insert(1, array, values))
    assertArrayEquals(Array[Byte](1, 2, 3, 4, 5, 6), ArrayUtils.insert(array.length, array, values))
  }

  @Test def testInsertChars(): Unit = {
    val array: Array[Char] = Array('a', 'b', 'c')
    val values: Array[Char] = Array('d', 'e', 'f')
    val result: Array[Char] = ArrayUtils.insert(42, array, null)
    assertArrayEquals(array, result)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Char](0), ArrayUtils.insert(0, new Array[Char](0), null))
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Char]], null))
    assertThrows[IndexOutOfBoundsException] { ArrayUtils.insert(-1, array, array) }
    assertThrows[IndexOutOfBoundsException] { ArrayUtils.insert(array.length + 1, array, array) }
    assertArrayEquals(Array[Char]('z', 'a', 'b', 'c'), ArrayUtils.insert(0, array, 'z'))
    assertArrayEquals(Array[Char]('a', 'z', 'b', 'c'), ArrayUtils.insert(1, array, 'z'))
    assertArrayEquals(Array[Char]('a', 'b', 'c', 'z'), ArrayUtils.insert(array.length, array, 'z'))
    assertArrayEquals(Array[Char]('d', 'e', 'f', 'a', 'b', 'c'), ArrayUtils.insert(0, array, values))
    assertArrayEquals(Array[Char]('a', 'd', 'e', 'f', 'b', 'c'), ArrayUtils.insert(1, array, values))
    assertArrayEquals(Array[Char]('a', 'b', 'c', 'd', 'e', 'f'), ArrayUtils.insert(array.length, array, values))
  }

  @Test def testInsertDoubles(): Unit = {
    val array: Array[Double] = Array(1d, 2d, 3d)
    val values: Array[Double] = Array(4d, 5d, 6d)
    val delta = 0.000001d
    val result: Array[Double] = ArrayUtils.insert(42, array, null)
    assertArrayEquals(array, result, delta)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Double](0), ArrayUtils.insert(0, new Array[Double](0), null), delta)
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Double]], null))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(-1, array, array))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(array.length + 1, array, array))
    assertArrayEquals(Array[Double](0, 1, 2, 3), ArrayUtils.insert(0, array, 0), delta)
    assertArrayEquals(Array[Double](1, 0, 2, 3), ArrayUtils.insert(1, array, 0), delta)
    assertArrayEquals(Array[Double](1, 2, 3, 0), ArrayUtils.insert(array.length, array, 0), delta)
    assertArrayEquals(Array[Double](4, 5, 6, 1, 2, 3), ArrayUtils.insert(0, array, values), delta)
    assertArrayEquals(Array[Double](1, 4, 5, 6, 2, 3), ArrayUtils.insert(1, array, values), delta)
    assertArrayEquals(Array[Double](1, 2, 3, 4, 5, 6), ArrayUtils.insert(array.length, array, values), delta)
  }

  @Test def testInsertFloats(): Unit = {
    val array: Array[Float] = Array(1f, 2f, 3f)
    val values: Array[Float] = Array(4f, 5f, 6f)
    val delta = 0.000001f
    val result: Array[Float] = ArrayUtils.insert(42, array, null)
    assertArrayEquals(array, result, delta)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Float](0), ArrayUtils.insert(0, new Array[Float](0), null), delta)
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Float]], null))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(-1, array, array))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(array.length + 1, array, array))
    assertArrayEquals(Array[Float](0, 1, 2, 3), ArrayUtils.insert(0, array, 0), delta)
    assertArrayEquals(Array[Float](1, 0, 2, 3), ArrayUtils.insert(1, array, 0), delta)
    assertArrayEquals(Array[Float](1, 2, 3, 0), ArrayUtils.insert(array.length, array, 0), delta)
    assertArrayEquals(Array[Float](4, 5, 6, 1, 2, 3), ArrayUtils.insert(0, array, values), delta)
    assertArrayEquals(Array[Float](1, 4, 5, 6, 2, 3), ArrayUtils.insert(1, array, values), delta)
    assertArrayEquals(Array[Float](1, 2, 3, 4, 5, 6), ArrayUtils.insert(array.length, array, values), delta)
  }

//  @Test def testInsertGenericArray(): Unit = {
//    val array: Array[String] = Array("a", "b", "c")
//    val values: Array[String] = Array("d", "e", "f")
//    val result: Array[String] = ArrayUtils.insert(42, array)
//    assertArrayEquals(array, result)
//    assertNotSame(array, result)
//    assertNull(ArrayUtils.insert(42, null, array))
//    assertArrayEquals(new Array[String](0), ArrayUtils.insert(0, new Array[String](0), null.asInstanceOf[Array[String]]))
//    assertNull(ArrayUtils.insert(42, null, null.asInstanceOf[Array[String]]))
//    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(-1, array, array))
//    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(array.length + 1, array, array))
//    assertArrayEquals(Array[String]("z", "a", "b", "c"), ArrayUtils.insert(0, array, "z"))
//    assertArrayEquals(Array[String]("a", "z", "b", "c"), ArrayUtils.insert(1, array, "z"))
//    assertArrayEquals(Array[String]("a", "b", "c", "z"), ArrayUtils.insert(array.length, array, "z"))
//    assertArrayEquals(Array[String]("d", "e", "f", "a", "b", "c"), ArrayUtils.insert(0, array, values))
//    assertArrayEquals(Array[String]("a", "d", "e", "f", "b", "c"), ArrayUtils.insert(1, array, values))
//    assertArrayEquals(Array[String]("a", "b", "c", "d", "e", "f"), ArrayUtils.insert(array.length, array, values))
//  }

  @Test def testInsertInts(): Unit = {
    val array: Array[Int] = Array(1, 2, 3)
    val values: Array[Int] = Array(4, 5, 6)
    val result: Array[Int] = ArrayUtils.insert(42, array, null)
    assertArrayEquals(array, result)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Int](0), ArrayUtils.insert(0, new Array[Int](0), null))
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Int]], null))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(-1, array, array))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(array.length + 1, array, array))
    assertArrayEquals(Array[Int](0, 1, 2, 3), ArrayUtils.insert(0, array, 0))
    assertArrayEquals(Array[Int](1, 0, 2, 3), ArrayUtils.insert(1, array, 0))
    assertArrayEquals(Array[Int](1, 2, 3, 0), ArrayUtils.insert(array.length, array, 0))
    assertArrayEquals(Array[Int](4, 5, 6, 1, 2, 3), ArrayUtils.insert(0, array, values))
    assertArrayEquals(Array[Int](1, 4, 5, 6, 2, 3), ArrayUtils.insert(1, array, values))
    assertArrayEquals(Array[Int](1, 2, 3, 4, 5, 6), ArrayUtils.insert(array.length, array, values))
  }

  @Test def testInsertLongs(): Unit = {
    val array: Array[Long] = Array(1L, 2L, 3L)
    val values: Array[Long] = Array(4L, 5L, 6L)
    val result: Array[Long] = ArrayUtils.insert(42, array, null)
    assertArrayEquals(array, result)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Long](0), ArrayUtils.insert(0, new Array[Long](0), null))
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Long]], null))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(-1, array, array))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(array.length + 1, array, array))
    assertArrayEquals(Array[Long](0L, 1L, 2L, 3L), ArrayUtils.insert(0, array, 0))
    assertArrayEquals(Array[Long](1L, 0L, 2L, 3L), ArrayUtils.insert(1, array, 0))
    assertArrayEquals(Array[Long](1L, 2L, 3L, 0L), ArrayUtils.insert(array.length, array, 0))
    assertArrayEquals(Array[Long](4L, 5L, 6L, 1L, 2L, 3L), ArrayUtils.insert(0, array, values))
    assertArrayEquals(Array[Long](1L, 4L, 5L, 6L, 2L, 3L), ArrayUtils.insert(1, array, values))
    assertArrayEquals(Array[Long](1L, 2L, 3L, 4L, 5L, 6L), ArrayUtils.insert(array.length, array, values))
  }

  @Test def testInsertShorts(): Unit = {
    val array: Array[Short] = Array(1.toShort, 2.toShort, 3.toShort)
    val values: Array[Short] = Array(4.toShort, 5.toShort, 6.toShort)
    val result: Array[Short] = ArrayUtils.insert(42, array, null)
    assertArrayEquals(array, result)
    assertNotSame(array, result)
    assertNull(ArrayUtils.insert(42, null, array))
    assertArrayEquals(new Array[Short](0), ArrayUtils.insert(0, new Array[Short](0), null))
    assertNull(ArrayUtils.insert(42, null.asInstanceOf[Array[Short]], null))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(-1, array, array))
    assertThrows[IndexOutOfBoundsException](ArrayUtils.insert(array.length + 1, array, array))
    assertArrayEquals(Array[Short](0.toShort, 1.toShort, 2.toShort, 3.toShort), ArrayUtils.insert(0, array, 0.toShort))
    assertArrayEquals(Array[Short](1.toShort, 0.toShort, 2.toShort, 3.toShort), ArrayUtils.insert(1, array, 0.toShort))
    assertArrayEquals(
      Array[Short](1.toShort, 2.toShort, 3.toShort, 0.toShort),
      ArrayUtils.insert(array.length, array, 0.toShort))
    assertArrayEquals(
      Array[Short](4.toShort, 5.toShort, 6.toShort, 1.toShort, 2.toShort, 3.toShort),
      ArrayUtils.insert(0, array, values))
    assertArrayEquals(
      Array[Short](1.toShort, 4.toShort, 5.toShort, 6.toShort, 2.toShort, 3.toShort),
      ArrayUtils.insert(1, array, values))
    assertArrayEquals(
      Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort, 5.toShort, 6.toShort),
      ArrayUtils.insert(array.length, array, values))
  }
}
