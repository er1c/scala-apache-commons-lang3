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
import org.scalatestplus.junit.JUnitSuite
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertNull
//import org.junit.jupiter.api.Test
import org.junit.Test

/**
  * Tests ArrayUtils remove and removeElement methods.
  */
class ArrayUtilsRemoveMultipleTest extends JUnitSuite {
  @Test def testRemoveAllBooleanArray(): Unit = {
    var array: Array[Boolean] = null
    array = ArrayUtils.removeAll(Array[Boolean](true), 0)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false), 0)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false), 1)
    assertArrayEquals(Array[Boolean](true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true), 1)
    assertArrayEquals(Array[Boolean](true, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, false), 0, 1)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, false), 0, 2)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, false), 1, 2)
    assertArrayEquals(Array[Boolean](true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true, false, true), 0, 2, 4)
    assertArrayEquals(Array[Boolean](false, false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true, false, true), 1, 3)
    assertArrayEquals(Array[Boolean](true, true, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true, false, true), 1, 3, 4)
    assertArrayEquals(Array[Boolean](true, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true, false, true, false, true), 0, 2, 4, 6)
    assertArrayEquals(Array[Boolean](false, false, false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true, false, true, false, true), 1, 3, 5)
    assertArrayEquals(Array[Boolean](true, true, true, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Boolean](true, false, true, false, true, false, true), 0, 1, 2)
    assertArrayEquals(Array[Boolean](false, true, false, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllBooleanArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Boolean](true, false), -1))
    ()
  }

  @Test def testRemoveAllBooleanArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Boolean](true, false), 2))
    ()
  }

  @Test def testRemoveAllBooleanArrayRemoveNone(): Unit = {
    val array1 = Array[Boolean](true, false)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Boolean], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllByteArray(): Unit = {
    var array: Array[Byte] = null
    array = ArrayUtils.removeAll(Array[Byte](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2), 0)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2), 1)
    assertArrayEquals(Array[Byte](1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 1), 1)
    assertArrayEquals(Array[Byte](1, 1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3), 0, 1)
    assertArrayEquals(Array[Byte](3), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3), 1, 2)
    assertArrayEquals(Array[Byte](1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3), 0, 2)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3, 4, 5), 1, 3)
    assertArrayEquals(Array[Byte](1, 3, 5), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3, 4, 5), 0, 2, 4)
    assertArrayEquals(Array[Byte](2, 4), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3, 4, 5, 6, 7), 1, 3, 5)
    assertArrayEquals(Array[Byte](1, 3, 5, 7), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Byte](1, 2, 3, 4, 5, 6, 7), 0, 2, 4, 6)
    assertArrayEquals(Array[Byte](2, 4, 6), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllByteArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Byte](1, 2), -1))
    ()
  }

  @Test def testRemoveAllByteArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Byte](1, 2), 2))
    ()
  }

  @Test def testRemoveAllByteArrayRemoveNone(): Unit = {
    val array1 = Array[Byte](1, 2)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Byte], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllCharArray(): Unit = {
    var array: Array[Char] = null
    array = ArrayUtils.removeAll(Array[Char]('a'), 0)
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b'), 0)
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b'), 1)
    assertArrayEquals(Array[Char]('a'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c'), 1)
    assertArrayEquals(Array[Char]('a', 'c'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b'), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c'), 0, 1)
    assertArrayEquals(Array[Char]('c'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c'), 1, 2)
    assertArrayEquals(Array[Char]('a'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c'), 0, 2)
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c', 'd', 'e'), 1, 3)
    assertArrayEquals(Array[Char]('a', 'c', 'e'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c', 'd', 'e'), 0, 2, 4)
    assertArrayEquals(Array[Char]('b', 'd'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c', 'd', 'e', 'f', 'g'), 1, 3, 5)
    assertArrayEquals(Array[Char]('a', 'c', 'e', 'g'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Char]('a', 'b', 'c', 'd', 'e', 'f', 'g'), 0, 2, 4, 6)
    assertArrayEquals(Array[Char]('b', 'd', 'f'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllCharArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Char]('a', 'b'), -1))
    ()
  }

  @Test def testRemoveAllCharArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Char]('a', 'b'), 2))
    ()
  }

  @Test def testRemoveAllCharArrayRemoveNone(): Unit = {
    val array1 = Array[Char]('a', 'b')
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Char], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllDoubleArray(): Unit = {
    var array: Array[Double] = null
    array = ArrayUtils.removeAll(Array[Double](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2), 0)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2), 1)
    assertArrayEquals(Array[Double](1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 1), 1)
    assertArrayEquals(Array[Double](1, 1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3), 0, 1)
    assertArrayEquals(Array[Double](3), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3), 1, 2)
    assertArrayEquals(Array[Double](1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3), 0, 2)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3, 4, 5), 1, 3)
    assertArrayEquals(Array[Double](1, 3, 5), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3, 4, 5), 0, 2, 4)
    assertArrayEquals(Array[Double](2, 4), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3, 4, 5, 6, 7), 1, 3, 5)
    assertArrayEquals(Array[Double](1, 3, 5, 7), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Double](1, 2, 3, 4, 5, 6, 7), 0, 2, 4, 6)
    assertArrayEquals(Array[Double](2, 4, 6), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllDoubleArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Double](1, 2), -1))
    ()
  }

  @Test def testRemoveAllDoubleArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Double](1, 2), 2))
    ()
  }

  @Test def testRemoveAllDoubleArrayRemoveNone(): Unit = {
    val array1 = Array[Double](1, 2)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Double], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllFloatArray(): Unit = {
    var array: Array[Float] = null
    array = ArrayUtils.removeAll(Array[Float](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2), 0)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2), 1)
    assertArrayEquals(Array[Float](1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 1), 1)
    assertArrayEquals(Array[Float](1, 1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3), 0, 1)
    assertArrayEquals(Array[Float](3), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3), 1, 2)
    assertArrayEquals(Array[Float](1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3), 0, 2)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3, 4, 5), 1, 3)
    assertArrayEquals(Array[Float](1, 3, 5), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3, 4, 5), 0, 2, 4)
    assertArrayEquals(Array[Float](2, 4), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3, 4, 5, 6, 7), 1, 3, 5)
    assertArrayEquals(Array[Float](1, 3, 5, 7), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Float](1, 2, 3, 4, 5, 6, 7), 0, 2, 4, 6)
    assertArrayEquals(Array[Float](2, 4, 6), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllFloatArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Float](1, 2), -1))
    ()
  }

  @Test def testRemoveAllFloatArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Float](1, 2), 2))
    ()
  }

  @Test def testRemoveAllFloatArrayRemoveNone(): Unit = {
    val array1 = Array[Float](1, 2)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Float], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllIntArray(): Unit = {
    var array: Array[Int] = null
    array = ArrayUtils.removeAll(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.EMPTY_INT_ARRAY)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    array = ArrayUtils.removeAll(Array[Int](1), ArrayUtils.EMPTY_INT_ARRAY)
    assertArrayEquals(Array[Int](1), array)
    array = ArrayUtils.removeAll(Array[Int](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2), 0)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2), 1)
    assertArrayEquals(Array[Int](1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 1), 1)
    assertArrayEquals(Array[Int](1, 1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3), 0, 1)
    assertArrayEquals(Array[Int](3), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3), 1, 2)
    assertArrayEquals(Array[Int](1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3), 0, 2)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3, 4, 5), 1, 3)
    assertArrayEquals(Array[Int](1, 3, 5), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3, 4, 5), 0, 2, 4)
    assertArrayEquals(Array[Int](2, 4), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3, 4, 5, 6, 7), 1, 3, 5)
    assertArrayEquals(Array[Int](1, 3, 5, 7), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Int](1, 2, 3, 4, 5, 6, 7), 0, 2, 4, 6)
    assertArrayEquals(Array[Int](2, 4, 6), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllIntArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Int](1, 2), -1))
    ()
  }

  @Test def testRemoveAllIntArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Int](1, 2), 2))
    ()
  }

  @Test def testRemoveAllIntArrayRemoveNone(): Unit = {
    val array1 = Array[Int](1, 2)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Int], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllLongArray(): Unit = {
    var array: Array[Long] = null
    array = ArrayUtils.removeAll(Array[Long](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2), 0)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2), 1)
    assertArrayEquals(Array[Long](1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 1), 1)
    assertArrayEquals(Array[Long](1, 1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3), 0, 1)
    assertArrayEquals(Array[Long](3), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3), 1, 2)
    assertArrayEquals(Array[Long](1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3), 0, 2)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3, 4, 5), 1, 3)
    assertArrayEquals(Array[Long](1, 3, 5), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3, 4, 5), 0, 2, 4)
    assertArrayEquals(Array[Long](2, 4), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3, 4, 5, 6, 7), 1, 3, 5)
    assertArrayEquals(Array[Long](1, 3, 5, 7), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Long](1, 2, 3, 4, 5, 6, 7), 0, 2, 4, 6)
    assertArrayEquals(Array[Long](2, 4, 6), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllLongArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Long](1, 2), -1))
    ()
  }

  @Test def testRemoveAllLongArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Long](1, 2), 2))
    ()
  }

  @Test def testRemoveAllLongArrayRemoveNone(): Unit = {
    val array1 = Array[Long](1, 2)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Long], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllNullBooleanArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Boolean]], 0))
    ()
  }

  @Test def testRemoveAllNullByteArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Byte]], 0))
    ()
  }

  @Test def testRemoveAllNullCharArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Char]], 0))
    ()
  }

  @Test def testRemoveAllNullDoubleArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Double]], 0))
    ()
  }

  @Test def testRemoveAllNullFloatArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Float]], 0))
    ()
  }

  @Test def testRemoveAllNullIntArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Int]], 0))
    ()
  }

  @Test def testRemoveAllNullLongArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Long]], 0))
    ()
  }

  @Test def testRemoveAllNullObjectArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.remove(null.asInstanceOf[Array[AnyRef]], 0))
    ()
  }

  @Test def testRemoveAllNullShortArray(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(null.asInstanceOf[Array[Short]], 0))
    ()
  }

//  @Test def testRemoveAllNumberArray(): Unit = {
//    val inarray: Array[Number] = Array(Integer.valueOf(1), JavaLong.valueOf(2L), JavaByte.valueOf(3.toByte))
//    assertEquals(3, inarray.length)
//    var outarray: Array[Number] = null
//    outarray = ArrayUtils.removeAll(inarray, 1)
//    assertArrayEquals(Array[Number](Integer.valueOf(1), JavaByte.valueOf(3.toByte)), outarray)
//    assertEquals(classOf[Number], outarray.getClass.getComponentType)
//    outarray = ArrayUtils.removeAll(outarray, 1)
//    assertArrayEquals(Array[Number](Integer.valueOf(1)), outarray)
//    assertEquals(classOf[Number], outarray.getClass.getComponentType)
//    outarray = ArrayUtils.removeAll(outarray, 0)
//    assertEquals(0, outarray.length)
//    assertEquals(classOf[Number], outarray.getClass.getComponentType)
//    outarray = ArrayUtils.removeAll(inarray, 0, 1)
//    assertArrayEquals(Array[Number](JavaByte.valueOf(3.toByte)), outarray)
//    assertEquals(classOf[Number], outarray.getClass.getComponentType)
//    outarray = ArrayUtils.removeAll(inarray, 0, 2)
//    assertArrayEquals(Array[Number](JavaLong.valueOf(2L)), outarray)
//    assertEquals(classOf[Number], outarray.getClass.getComponentType)
//    outarray = ArrayUtils.removeAll(inarray, 1, 2)
//    assertArrayEquals(Array[Number](Integer.valueOf(1)), outarray)
//    assertEquals(classOf[Number], outarray.getClass.getComponentType)
//  }

//  @Test def testRemoveAllObjectArray(): Unit = {
//    var array: Array[AnyRef] = null
//    array = ArrayUtils.removeAll(Array[AnyRef]("a"), 0)
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b"), 0, 1)
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c"), 1, 2)
//    assertArrayEquals(Array[AnyRef]("a"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d"), 1, 2)
//    assertArrayEquals(Array[AnyRef]("a", "d"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d"), 0, 3)
//    assertArrayEquals(Array[AnyRef]("b", "c"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d"), 0, 1, 3)
//    assertArrayEquals(Array[AnyRef]("c"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d", "e"), 0, 1, 3)
//    assertArrayEquals(Array[AnyRef]("c", "e"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d", "e"), 0, 2, 4)
//    assertArrayEquals(Array[AnyRef]("b", "d"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d"), 0, 1, 3, 0, 1, 3)
//    assertArrayEquals(Array[AnyRef]("c"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d"), 2, 1, 0, 3)
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeAll(Array[AnyRef]("a", "b", "c", "d"), 2, 0, 1, 3, 0, 2, 1, 3)
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//  }

  @Test def testRemoveAllObjectArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[AnyRef]("a", "b"), -1))
    ()
  }

  @Test def testRemoveAllObjectArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[AnyRef]("a", "b"), 2))
    ()
  }

  @Test def testRemoveAllObjectArrayRemoveNone(): Unit = {
    val array1 = Array[AnyRef]("foo", "bar", "baz")
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Any], array2.getClass.getComponentType)
  }

  @Test def testRemoveAllShortArray(): Unit = {
    var array: Array[Short] = null
    array = ArrayUtils.removeAll(Array[Short](1), 0)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2), 0)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2), 1)
    assertArrayEquals(Array[Short](1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 1), 1)
    assertArrayEquals(Array[Short](1, 1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2), 0, 1)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3), 0, 1)
    assertArrayEquals(Array[Short](3), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3), 1, 2)
    assertArrayEquals(Array[Short](1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3), 0, 2)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3, 4, 5), 1, 3)
    assertArrayEquals(Array[Short](1, 3, 5), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3, 4, 5), 0, 2, 4)
    assertArrayEquals(Array[Short](2, 4), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3, 4, 5, 6, 7), 1, 3, 5)
    assertArrayEquals(Array[Short](1, 3, 5, 7), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeAll(Array[Short](1, 2, 3, 4, 5, 6, 7), 0, 2, 4, 6)
    assertArrayEquals(Array[Short](2, 4, 6), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveAllShortArrayNegativeIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Short](1, 2), -1, 0))
    ()
  }

  @Test def testRemoveAllShortArrayOutOfBoundsIndex(): Unit = {
    assertThrows[IndexOutOfBoundsException](ArrayUtils.removeAll(Array[Short](1, 2), 2, 0))
    ()
  }

  @Test def testRemoveAllShortArrayRemoveNone(): Unit = {
    val array1 = Array[Short](1, 2)
    val array2 = ArrayUtils.removeAll(array1)
    assertNotSame(array1, array2)
    assertArrayEquals(array1, array2)
    assertEquals(classOf[Short], array2.getClass.getComponentType)
  }

  @Test def testRemoveElementBooleanArray(): Unit = {
    var array: Array[Boolean] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Boolean]], true)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_BOOLEAN_ARRAY, true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true), true)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false), true)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false, true), true)
    assertArrayEquals(Array[Boolean](false, true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Boolean]], true, false)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_BOOLEAN_ARRAY, true, false)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true), true, false)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false), true, false)
    assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false), true, true)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false, true), true, false)
    assertArrayEquals(Array[Boolean](true), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false, true), true, true)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Boolean](true, false, true), true, true, true, true)
    assertArrayEquals(Array[Boolean](false), array)
    assertEquals(JavaBoolean.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementByteArray(): Unit = {
    var array: Array[Byte] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Byte]], 1.toByte)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_BYTE_ARRAY, 1.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1), 1.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2), 1.toByte)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2, 1), 1.toByte)
    assertArrayEquals(Array[Byte](2, 1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Byte]], 1.toByte, 2.toByte)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_BYTE_ARRAY, 1.toByte, 2.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1), 1.toByte, 2.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2), 1.toByte, 2.toByte)
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_ARRAY, array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2), 1.toByte, 1.toByte)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2, 1), 1.toByte, 2.toByte)
    assertArrayEquals(Array[Byte](1), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2, 1), 1.toByte, 1.toByte)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Byte](1, 2, 1), 1.toByte, 1.toByte, 1.toByte, 1.toByte)
    assertArrayEquals(Array[Byte](2), array)
    assertEquals(JavaByte.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementCharArray(): Unit = {
    var array: Array[Char] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Char]], 'a')
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_CHAR_ARRAY, 'a')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a'), 'a')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b'), 'a')
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b', 'a'), 'a')
    assertArrayEquals(Array[Char]('b', 'a'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Char]], 'a', 'b')
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_CHAR_ARRAY, 'a', 'b')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a'), 'a', 'b')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b'), 'a', 'b')
    assertArrayEquals(ArrayUtils.EMPTY_CHAR_ARRAY, array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b'), 'a', 'a')
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b', 'a'), 'a', 'b')
    assertArrayEquals(Array[Char]('a'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b', 'a'), 'a', 'a')
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Char]('a', 'b', 'a'), 'a', 'a', 'a', 'a')
    assertArrayEquals(Array[Char]('b'), array)
    assertEquals(Character.TYPE, array.getClass.getComponentType)
  }

  @Test
  @SuppressWarnings(Array("cast")) def testRemoveElementDoubleArray(): Unit = {
    var array: Array[Double] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Double]], 1.toDouble)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_DOUBLE_ARRAY, 1.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1), 1.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2), 1.toDouble)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2, 1), 1.toDouble)
    assertArrayEquals(Array[Double](2, 1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Double]], 1.toDouble, 2.toDouble)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_DOUBLE_ARRAY, 1.toDouble, 2.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1), 1.toDouble, 2.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2), 1.toDouble, 2.toDouble)
    assertArrayEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2), 1.toDouble, 1.toDouble)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2, 1), 1.toDouble, 2.toDouble)
    assertArrayEquals(Array[Double](1), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2, 1), 1.toDouble, 1.toDouble)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Double](1, 2, 1), 1.toDouble, 1.toDouble, 1.toDouble, 1.toDouble)
    assertArrayEquals(Array[Double](2), array)
    assertEquals(JavaDouble.TYPE, array.getClass.getComponentType)
  }

  @Test
  @SuppressWarnings(Array("cast")) def testRemoveElementFloatArray(): Unit = {
    var array: Array[Float] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Float]], 1.toFloat)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_FLOAT_ARRAY, 1.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1), 1.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2), 1.toFloat)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2, 1), 1.toFloat)
    assertArrayEquals(Array[Float](2, 1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Float]], 1.toFloat, 1.toFloat)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_FLOAT_ARRAY, 1.toFloat, 1.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1), 1.toFloat, 1.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2), 1.toFloat, 2.toFloat)
    assertArrayEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2), 1.toFloat, 1.toFloat)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2, 1), 1.toFloat, 1.toFloat)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2, 1), 1.toFloat, 2.toFloat)
    assertArrayEquals(Array[Float](1), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Float](1, 2, 1), 1.toFloat, 1.toFloat, 1.toFloat, 1.toFloat)
    assertArrayEquals(Array[Float](2), array)
    assertEquals(JavaFloat.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementIntArray(): Unit = {
    var array: Array[Int] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Int]], 1)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_INT_ARRAY, 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1), 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2), 1)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2, 1), 1)
    assertArrayEquals(Array[Int](2, 1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Int]], 1)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_INT_ARRAY, 1, 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1), 1, 1)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2), 1, 2)
    assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2), 1, 1)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2, 1), 1, 2)
    assertArrayEquals(Array[Int](1), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2, 1), 1, 1)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Int](1, 2, 1), 1, 1, 1, 1)
    assertArrayEquals(Array[Int](2), array)
    assertEquals(Integer.TYPE, array.getClass.getComponentType)
  }

  @Test
  @SuppressWarnings(Array("cast")) def testRemoveElementLongArray(): Unit = {
    var array: Array[Long] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Long]], 1L)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_LONG_ARRAY, 1L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1), 1L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2), 1L)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2, 1), 1L)
    assertArrayEquals(Array[Long](2, 1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Long]], 1L, 1L)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_LONG_ARRAY, 1L, 1L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1), 1L, 1L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2), 1L, 2L)
    assertArrayEquals(ArrayUtils.EMPTY_LONG_ARRAY, array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2), 1L, 1L)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2, 1), 1L, 1L)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2, 1), 1L, 2L)
    assertArrayEquals(Array[Long](1), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Long](1, 2, 1), 1L, 1L, 1L, 1L)
    assertArrayEquals(Array[Long](2), array)
    assertEquals(JavaLong.TYPE, array.getClass.getComponentType)
  }

  @Test def testRemoveElementShortArray(): Unit = {
    var array: Array[Short] = null
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Short]], 1.toShort)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_SHORT_ARRAY, 1.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1), 1.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2), 1.toShort)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2, 1), 1.toShort)
    assertArrayEquals(Array[Short](2, 1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(null.asInstanceOf[Array[Short]], 1.toShort, 1.toShort)
    assertNull(array)
    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_SHORT_ARRAY, 1.toShort, 1.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1), 1.toShort, 1.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2), 1.toShort, 2.toShort)
    assertArrayEquals(ArrayUtils.EMPTY_SHORT_ARRAY, array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2), 1.toShort, 1.toShort)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2, 1), 1.toShort, 1.toShort)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2, 1), 1.toShort, 2.toShort)
    assertArrayEquals(Array[Short](1), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
    array = ArrayUtils.removeElements(Array[Short](1, 2, 1), 1.toShort, 1.toShort, 1.toShort, 1.toShort)
    assertArrayEquals(Array[Short](2), array)
    assertEquals(JavaShort.TYPE, array.getClass.getComponentType)
  }

//  @Test def testRemoveElementsObjectArray(): Unit = {
//    var array: Array[AnyRef] = null
//    array = ArrayUtils.removeElements(null.asInstanceOf[Array[AnyRef]], "a")
//    assertNull(array)
//    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_OBJECT_ARRAY, "a")
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a"), "a")
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b"), "a")
//    assertArrayEquals(Array[AnyRef]("b"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b", "a"), "a")
//    assertArrayEquals(Array[AnyRef]("b", "a"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(null.asInstanceOf[Array[AnyRef]], "a", "b")
//    assertNull(array)
//    array = ArrayUtils.removeElements(ArrayUtils.EMPTY_OBJECT_ARRAY, "a", "b")
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a"), "a", "b")
//    assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b"), "a", "c")
//    assertArrayEquals(Array[AnyRef]("b"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b", "a"), "a")
//    assertArrayEquals(Array[AnyRef]("b", "a"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b", "a"), "a", "b")
//    assertArrayEquals(Array[AnyRef]("a"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b", "a"), "a", "a")
//    assertArrayEquals(Array[AnyRef]("b"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//    array = ArrayUtils.removeElements(Array[AnyRef]("a", "b", "a"), "a", "a", "a", "a")
//    assertArrayEquals(Array[AnyRef]("b"), array)
//    assertEquals(classOf[Any], array.getClass.getComponentType)
//  }
}
