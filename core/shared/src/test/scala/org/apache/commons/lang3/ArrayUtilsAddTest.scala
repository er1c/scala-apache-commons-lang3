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
import org.junit.Assert._
import org.junit.Test

object ArrayUtilsAddTest {
  private val DoubleDelta: Double = 1e-9d
  private val FloatDelta: Float = 1e-9f
}

/**
  * Tests ArrayUtils add methods.
  */
class ArrayUtilsAddTest extends JUnitSuite {
  import ArrayUtilsAddTest._

  @Test def testAddFirstBoolean(): Unit = {
    var newArray: Array[Boolean] = null
    newArray = ArrayUtils.addFirst(null, false)
    assertArrayEquals(Array[Boolean](false), newArray)
    assertEquals(JavaBoolean.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null, true)
    assertArrayEquals(Array[Boolean](true), newArray)
    assertEquals(JavaBoolean.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Boolean](true, false, true)
    newArray = ArrayUtils.addFirst(array1, false)
    assertArrayEquals(Array[Boolean](false, true, false, true), newArray)
    assertEquals(JavaBoolean.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddFirstByte(): Unit = {
    var newArray: Array[Byte] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Byte]], 0.toByte)
    assertArrayEquals(Array[Byte](0), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Byte]], 1.toByte)
    assertArrayEquals(Array[Byte](1), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Byte](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0.toByte)
    assertArrayEquals(Array[Byte](0, 1, 2, 3), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4.toByte)
    assertArrayEquals(Array[Byte](4, 1, 2, 3), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddFirstChar(): Unit = {
    var newArray: Array[Char] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Char]], 0.toChar)
    assertArrayEquals(Array[Char](0), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Char]], 1.toChar)
    assertArrayEquals(Array[Char](1), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Char](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0.toChar)
    assertArrayEquals(Array[Char](0, 1, 2, 3), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4.toChar)
    assertArrayEquals(Array[Char](4, 1, 2, 3), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddFirstDouble(): Unit = {
    var newArray: Array[Double] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Double]], 0)
    assertArrayEquals(Array[Double](0), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Double]], 1)
    assertArrayEquals(Array[Double](1), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Double](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0)
    assertArrayEquals(Array[Double](0, 1, 2, 3), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4)
    assertArrayEquals(Array[Double](4, 1, 2, 3), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddFirstFloat(): Unit = {
    var newArray: Array[Float] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Float]], 0)
    assertArrayEquals(Array[Float](0), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Float]], 1)
    assertArrayEquals(Array[Float](1), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Float](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0)
    assertArrayEquals(Array[Float](0, 1, 2, 3), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4)
    assertArrayEquals(Array[Float](4, 1, 2, 3), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddFirstInt(): Unit = {
    var newArray: Array[Int] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Int]], 0)
    assertArrayEquals(Array[Int](0), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Int]], 1)
    assertArrayEquals(Array[Int](1), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Int](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0)
    assertArrayEquals(Array[Int](0, 1, 2, 3), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4)
    assertArrayEquals(Array[Int](4, 1, 2, 3), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddFirstLong(): Unit = {
    var newArray: Array[Long] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Long]], 0)
    assertArrayEquals(Array[Long](0), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Long]], 1)
    assertArrayEquals(Array[Long](1), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Long](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0)
    assertArrayEquals(Array[Long](0, 1, 2, 3), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4)
    assertArrayEquals(Array[Long](4, 1, 2, 3), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
  }

//  @Test def testAddFirstObject(): Unit = {
//    var newArray: Array[Any] = null
//    //show that not casting is okay
//    newArray = ArrayUtils.add(null.asInstanceOf[Array[Any]], "a")
//    assertArrayEquals(Array[String]("a"), newArray)
//    assertArrayEquals(Array[AnyRef]("a"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    //show that not casting to Object[] is okay and will assume String based on "a"
//    val newStringArray = ArrayUtils.add(null, "a")
//    assertArrayEquals(Array[String]("a"), newStringArray)
//    assertArrayEquals(Array[AnyRef]("a"), newStringArray)
//    assertEquals(classOf[String], newStringArray.getClass.getComponentType)
//    val stringArray1 = Array[String]("a", "b", "c")
//    newArray = ArrayUtils.addFirst(stringArray1, null)
//    assertArrayEquals(Array[String](null, "a", "b", "c"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.addFirst(stringArray1, "d")
//    assertArrayEquals(Array[String]("d", "a", "b", "c"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    var numberArray1 = Array[Number](Integer.valueOf(1), JavaDouble.valueOf(2))
//    newArray = ArrayUtils.addFirst(numberArray1, JavaFloat.valueOf(3))
//    assertArrayEquals(Array[Number](JavaFloat.valueOf(3), Integer.valueOf(1), JavaDouble.valueOf(2)), newArray)
//    assertEquals(classOf[Number], newArray.getClass.getComponentType)
//    numberArray1 = null
//    newArray = ArrayUtils.addFirst(numberArray1, JavaFloat.valueOf(3))
//    assertArrayEquals(Array[Float](JavaFloat.valueOf(3)), newArray)
//    assertEquals(classOf[Float], newArray.getClass.getComponentType)
//  }

  @Test def testAddFirstShort(): Unit = {
    var newArray: Array[Short] = null
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Short]], 0.toShort)
    assertArrayEquals(Array[Short](0), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(null.asInstanceOf[Array[Short]], 1.toShort)
    assertArrayEquals(Array[Short](1), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Short](1, 2, 3)
    newArray = ArrayUtils.addFirst(array1, 0.toShort)
    assertArrayEquals(Array[Short](0, 1, 2, 3), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.addFirst(array1, 4.toShort)
    assertArrayEquals(Array[Short](4, 1, 2, 3), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayBoolean(): Unit = {
    var newArray: Array[Boolean] = null
    newArray = ArrayUtils.add(null, false)
    assertArrayEquals(Array[Boolean](false), newArray)
    assertEquals(JavaBoolean.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null, true)
    assertArrayEquals(Array[Boolean](true), newArray)
    assertEquals(JavaBoolean.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Boolean](true, false, true)
    newArray = ArrayUtils.add(array1, false)
    assertArrayEquals(Array[Boolean](true, false, true, false), newArray)
    assertEquals(JavaBoolean.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayByte(): Unit = {
    var newArray: Array[Byte] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Byte]], 0.toByte)
    assertArrayEquals(Array[Byte](0), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Byte]], 1.toByte)
    assertArrayEquals(Array[Byte](1), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Byte](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0.toByte)
    assertArrayEquals(Array[Byte](1, 2, 3, 0), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4.toByte)
    assertArrayEquals(Array[Byte](1, 2, 3, 4), newArray)
    assertEquals(JavaByte.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayChar(): Unit = {
    var newArray: Array[Char] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Char]], 0.toChar)
    assertArrayEquals(Array[Char](0), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Char]], 1.toChar)
    assertArrayEquals(Array[Char](1), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Char](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0.toChar)
    assertArrayEquals(Array[Char](1, 2, 3, 0), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4.toChar)
    assertArrayEquals(Array[Char](1, 2, 3, 4), newArray)
    assertEquals(Character.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayDouble(): Unit = {
    var newArray: Array[Double] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Double]], 0)
    assertArrayEquals(Array[Double](0), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Double]], 1)
    assertArrayEquals(Array[Double](1), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Double](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0)
    assertArrayEquals(Array[Double](1, 2, 3, 0), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4)
    assertArrayEquals(Array[Double](1, 2, 3, 4), newArray, DoubleDelta)
    assertEquals(JavaDouble.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayFloat(): Unit = {
    var newArray: Array[Float] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Float]], 0)
    assertArrayEquals(Array[Float](0), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Float]], 1)
    assertArrayEquals(Array[Float](1), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Float](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0)
    assertArrayEquals(Array[Float](1, 2, 3, 0), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4)
    assertArrayEquals(Array[Float](1, 2, 3, 4), newArray, FloatDelta)
    assertEquals(JavaFloat.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayInt(): Unit = {
    var newArray: Array[Int] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Int]], 0)
    assertArrayEquals(Array[Int](0), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Int]], 1)
    assertArrayEquals(Array[Int](1), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Int](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0)
    assertArrayEquals(Array[Int](1, 2, 3, 0), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4)
    assertArrayEquals(Array[Int](1, 2, 3, 4), newArray)
    assertEquals(Integer.TYPE, newArray.getClass.getComponentType)
  }

  @Test def testAddObjectArrayLong(): Unit = {
    var newArray: Array[Long] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Long]], 0)
    assertArrayEquals(Array[Long](0), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Long]], 1)
    assertArrayEquals(Array[Long](1), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Long](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0)
    assertArrayEquals(Array[Long](1, 2, 3, 0), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4)
    assertArrayEquals(Array[Long](1, 2, 3, 4), newArray)
    assertEquals(JavaLong.TYPE, newArray.getClass.getComponentType)
  }

//  @Test def testAddObjectArrayObject(): Unit = {
//    var newArray: Array[Any] = null
//    newArray = ArrayUtils.add(null.asInstanceOf[Array[Any]], "a")
//    assertArrayEquals(Array[String]("a"), newArray)
//    assertArrayEquals(Array[AnyRef]("a"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    val newStringArray = ArrayUtils.add(null, "a")
//    assertArrayEquals(Array[String]("a"), newStringArray)
//    assertArrayEquals(Array[AnyRef]("a"), newStringArray)
//    assertEquals(classOf[String], newStringArray.getClass.getComponentType)
//    val stringArray1 = Array[String]("a", "b", "c")
//    newArray = ArrayUtils.add(stringArray1, null)
//    assertArrayEquals(Array[String]("a", "b", "c", null), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.add(stringArray1, "d")
//    assertArrayEquals(Array[String]("a", "b", "c", "d"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    var numberArray1 = Array[Number](Integer.valueOf(1), JavaDouble.valueOf(2))
//    newArray = ArrayUtils.add(numberArray1, JavaFloat.valueOf(3))
//    assertArrayEquals(Array[Number](Integer.valueOf(1), JavaDouble.valueOf(2), JavaFloat.valueOf(3)), newArray)
//    assertEquals(classOf[Number], newArray.getClass.getComponentType)
//    numberArray1 = null
//    newArray = ArrayUtils.add(numberArray1, JavaFloat.valueOf(3))
//    assertArrayEquals(Array[Float](JavaFloat.valueOf(3)), newArray)
//    assertEquals(classOf[Float], newArray.getClass.getComponentType)
//  }

  @Test def testAddObjectArrayShort(): Unit = {
    var newArray: Array[Short] = null
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Short]], 0.toShort)
    assertArrayEquals(Array[Short](0), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(null.asInstanceOf[Array[Short]], 1.toShort)
    assertArrayEquals(Array[Short](1), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
    val array1 = Array[Short](1, 2, 3)
    newArray = ArrayUtils.add(array1, 0.toShort)
    assertArrayEquals(Array[Short](1, 2, 3, 0), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
    newArray = ArrayUtils.add(array1, 4.toShort)
    assertArrayEquals(Array[Short](1, 2, 3, 4), newArray)
    assertEquals(JavaShort.TYPE, newArray.getClass.getComponentType)
  }

//  @Test def testAddObjectArrayToObjectArray(): Unit = {
//    assertNull(ArrayUtils.addAll(null, null.asInstanceOf[Array[AnyRef]]))
//    var newArray: Array[Any] = null
//    val stringArray1 = Array[String]("a", "b", "c")
//    val stringArray2 = Array[String]("1", "2", "3")
//    newArray = ArrayUtils.addAll(stringArray1, null.asInstanceOf[Array[String]])
//    assertNotSame(stringArray1, newArray)
//    assertArrayEquals(stringArray1, newArray)
//    assertArrayEquals(Array[String]("a", "b", "c"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.addAll(null, stringArray2)
//    assertNotSame(stringArray2, newArray)
//    assertArrayEquals(stringArray2, newArray)
//    assertArrayEquals(Array[String]("1", "2", "3"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.addAll(stringArray1, stringArray2)
//    assertArrayEquals(Array[String]("a", "b", "c", "1", "2", "3"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.addAll(ArrayUtils.EMPTY_STRING_ARRAY, null.asInstanceOf[Array[String]])
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, newArray)
//    assertArrayEquals(new Array[String], newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.addAll(null, ArrayUtils.EMPTY_STRING_ARRAY)
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, newArray)
//    assertArrayEquals(new Array[String], newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.addAll(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.EMPTY_STRING_ARRAY)
//    assertArrayEquals(ArrayUtils.EMPTY_STRING_ARRAY, newArray)
//    assertArrayEquals(new Array[String], newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    val stringArrayNull = Array[String](null)
//    newArray = ArrayUtils.addAll(stringArrayNull, stringArrayNull)
//    assertArrayEquals(Array[String](null, null), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    // boolean
//    assertArrayEquals(Array[Boolean](true, false, false, true), ArrayUtils.addAll(Array[Boolean](true, false), false, true))
//    assertArrayEquals(Array[Boolean](false, true), ArrayUtils.addAll(null, Array[Boolean](false, true)))
//    assertArrayEquals(Array[Boolean](true, false), ArrayUtils.addAll(Array[Boolean](true, false), null))
//    // char
//    assertArrayEquals(Array[Char]('a', 'b', 'c', 'd'), ArrayUtils.addAll(Array[Char]('a', 'b'), 'c', 'd'))
//    assertArrayEquals(Array[Char]('c', 'd'), ArrayUtils.addAll(null, Array[Char]('c', 'd')))
//    assertArrayEquals(Array[Char]('a', 'b'), ArrayUtils.addAll(Array[Char]('a', 'b'), null))
//    // byte
//    assertArrayEquals(Array[Byte](0.toByte, 1.toByte, 2.toByte, 3.toByte), ArrayUtils.addAll(Array[Byte](0.toByte, 1.toByte), 2.toByte, 3.toByte))
//    assertArrayEquals(Array[Byte](2.toByte, 3.toByte), ArrayUtils.addAll(null, Array[Byte](2.toByte, 3.toByte)))
//    assertArrayEquals(Array[Byte](0.toByte, 1.toByte), ArrayUtils.addAll(Array[Byte](0.toByte, 1.toByte), null))
//    // short
//    assertArrayEquals(Array[Short](10.toShort, 20.toShort, 30.toShort, 40.toShort), ArrayUtils.addAll(Array[Short](10.toShort, 20.toShort), 30.toShort, 40.toShort))
//    assertArrayEquals(Array[Short](30.toShort, 40.toShort), ArrayUtils.addAll(null, Array[Short](30.toShort, 40.toShort)))
//    assertArrayEquals(Array[Short](10.toShort, 20.toShort), ArrayUtils.addAll(Array[Short](10.toShort, 20.toShort), null))
//    // int
//    assertArrayEquals(Array[Int](1, 1000, -1000, -1), ArrayUtils.addAll(Array[Int](1, 1000), -1000, -1))
//    assertArrayEquals(Array[Int](-1000, -1), ArrayUtils.addAll(null, Array[Int](-1000, -1)))
//    assertArrayEquals(Array[Int](1, 1000), ArrayUtils.addAll(Array[Int](1, 1000), null))
//    // long
//    assertArrayEquals(Array[Long](1L, -1L, 1000L, -1000L), ArrayUtils.addAll(Array[Long](1L, -1L), 1000L, -1000L))
//    assertArrayEquals(Array[Long](1000L, -1000L), ArrayUtils.addAll(null, Array[Long](1000L, -1000L)))
//    assertArrayEquals(Array[Long](1L, -1L), ArrayUtils.addAll(Array[Long](1L, -1L), null))
//    // float
//    assertArrayEquals(Array[Float](10.5f, 10.1f, 1.6f, 0.01f), ArrayUtils.addAll(Array[Float](10.5f, 10.1f), 1.6f, 0.01f))
//    assertArrayEquals(Array[Float](1.6f, 0.01f), ArrayUtils.addAll(null, Array[Float](1.6f, 0.01f)))
//    assertArrayEquals(Array[Float](10.5f, 10.1f), ArrayUtils.addAll(Array[Float](10.5f, 10.1f), null))
//    // double
//    assertArrayEquals(Array[Double](Math.PI, -Math.PI, 0, 9.99), ArrayUtils.addAll(Array[Double](Math.PI, -Math.PI), 0, 9.99))
//    assertArrayEquals(Array[Double](0, 9.99), ArrayUtils.addAll(null, Array[Double](0, 9.99)))
//    assertArrayEquals(Array[Double](Math.PI, -Math.PI), ArrayUtils.addAll(Array[Double](Math.PI, -Math.PI), null))
//  }

//  @SuppressWarnings(Array("deprecation"))
//  @Test def testAddObjectAtIndex(): Unit = {
//    var newArray: Array[Any] = null
//    newArray = ArrayUtils.add(null.asInstanceOf[Array[AnyRef]], 0, "a")
//    assertArrayEquals(Array[String]("a"), newArray)
//    assertArrayEquals(Array[AnyRef]("a"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    val stringArray1 = Array[String]("a", "b", "c")
//    newArray = ArrayUtils.add(stringArray1, 0, null)
//    assertArrayEquals(Array[String](null, "a", "b", "c"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.add(stringArray1, 1, null)
//    assertArrayEquals(Array[String]("a", null, "b", "c"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.add(stringArray1, 3, null)
//    assertArrayEquals(Array[String]("a", "b", "c", null), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    newArray = ArrayUtils.add(stringArray1, 3, "d")
//    assertArrayEquals(Array[String]("a", "b", "c", "d"), newArray)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    assertEquals(classOf[String], newArray.getClass.getComponentType)
//    val o = Array[AnyRef]("1", "2", "4")
//    val result = ArrayUtils.add(o, 2, "3")
//    val result2 = ArrayUtils.add(o, 3, "5")
//    assertNotNull(result)
//    assertEquals(4, result.length)
//    assertEquals("1", result(0))
//    assertEquals("2", result(1))
//    assertEquals("3", result(2))
//    assertEquals("4", result(3))
//    assertNotNull(result2)
//    assertEquals(4, result2.length)
//    assertEquals("1", result2(0))
//    assertEquals("2", result2(1))
//    assertEquals("4", result2(2))
//    assertEquals("5", result2(3))
//    // boolean tests
//    var booleanArray = ArrayUtils.add(null, 0, true)
//    assertArrayEquals(Array[Boolean](true), booleanArray)
//    var e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null, -1, true))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    booleanArray = ArrayUtils.add(Array[Boolean](true), 0, false)
//    assertArrayEquals(Array[Boolean](false, true), booleanArray)
//    booleanArray = ArrayUtils.add(Array[Boolean](false), 1, true)
//    assertArrayEquals(Array[Boolean](false, true), booleanArray)
//    booleanArray = ArrayUtils.add(Array[Boolean](true, false), 1, true)
//    assertArrayEquals(Array[Boolean](true, true, false), booleanArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Boolean](true, false), 4, true))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Boolean](true, false), -1, true))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // char tests
//    var charArray = ArrayUtils.add(null.asInstanceOf[Array[Char]], 0, 'a')
//    assertArrayEquals(Array[Char]('a'), charArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null.asInstanceOf[Array[Char]], -1, 'a'))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    charArray = ArrayUtils.add(Array[Char]('a'), 0, 'b')
//    assertArrayEquals(Array[Char]('b', 'a'), charArray)
//    charArray = ArrayUtils.add(Array[Char]('a', 'b'), 0, 'c')
//    assertArrayEquals(Array[Char]('c', 'a', 'b'), charArray)
//    charArray = ArrayUtils.add(Array[Char]('a', 'b'), 1, 'k')
//    assertArrayEquals(Array[Char]('a', 'k', 'b'), charArray)
//    charArray = ArrayUtils.add(Array[Char]('a', 'b', 'c'), 1, 't')
//    assertArrayEquals(Array[Char]('a', 't', 'b', 'c'), charArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Char]('a', 'b'), 4, 'c'))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Char]('a', 'b'), -1, 'c'))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // short tests
//    var shortArray = ArrayUtils.add(Array[Short](1), 0, 2.toShort)
//    assertArrayEquals(Array[Short](2, 1), shortArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null.asInstanceOf[Array[Short]], -1, 2.toShort))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    shortArray = ArrayUtils.add(Array[Short](2, 6), 2, 10.toShort)
//    assertArrayEquals(Array[Short](2, 6, 10), shortArray)
//    shortArray = ArrayUtils.add(Array[Short](2, 6), 0, -4.toShort)
//    assertArrayEquals(Array[Short](-4, 2, 6), shortArray)
//    shortArray = ArrayUtils.add(Array[Short](2, 6, 3), 2, 1.toShort)
//    assertArrayEquals(Array[Short](2, 6, 1, 3), shortArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Short](2, 6), 4, 10.toShort))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Short](2, 6), -1, 10.toShort))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // byte tests
//    var byteArray = ArrayUtils.add(Array[Byte](1), 0, 2.toByte)
//    assertArrayEquals(Array[Byte](2, 1), byteArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null.asInstanceOf[Array[Byte]], -1, 2.toByte))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    byteArray = ArrayUtils.add(Array[Byte](2, 6), 2, 3.toByte)
//    assertArrayEquals(Array[Byte](2, 6, 3), byteArray)
//    byteArray = ArrayUtils.add(Array[Byte](2, 6), 0, 1.toByte)
//    assertArrayEquals(Array[Byte](1, 2, 6), byteArray)
//    byteArray = ArrayUtils.add(Array[Byte](2, 6, 3), 2, 1.toByte)
//    assertArrayEquals(Array[Byte](2, 6, 1, 3), byteArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Byte](2, 6), 4, 3.toByte))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Byte](2, 6), -1, 3.toByte))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // int tests
//    var intArray = ArrayUtils.add(Array[Int](1), 0, 2)
//    assertArrayEquals(Array[Int](2, 1), intArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null.asInstanceOf[Array[Int]], -1, 2))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    intArray = ArrayUtils.add(Array[Int](2, 6), 2, 10)
//    assertArrayEquals(Array[Int](2, 6, 10), intArray)
//    intArray = ArrayUtils.add(Array[Int](2, 6), 0, -4)
//    assertArrayEquals(Array[Int](-4, 2, 6), intArray)
//    intArray = ArrayUtils.add(Array[Int](2, 6, 3), 2, 1)
//    assertArrayEquals(Array[Int](2, 6, 1, 3), intArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Int](2, 6), 4, 10))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Int](2, 6), -1, 10))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // long tests
//    var longArray = ArrayUtils.add(Array[Long](1L), 0, 2L)
//    assertArrayEquals(Array[Long](2L, 1L), longArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null.asInstanceOf[Array[Long]], -1, 2L))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    longArray = ArrayUtils.add(Array[Long](2L, 6L), 2, 10L)
//    assertArrayEquals(Array[Long](2L, 6L, 10L), longArray)
//    longArray = ArrayUtils.add(Array[Long](2L, 6L), 0, -4L)
//    assertArrayEquals(Array[Long](-4L, 2L, 6L), longArray)
//    longArray = ArrayUtils.add(Array[Long](2L, 6L, 3L), 2, 1L)
//    assertArrayEquals(Array[Long](2L, 6L, 1L, 3L), longArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Long](2L, 6L), 4, 10L))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Long](2L, 6L), -1, 10L))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // float tests
//    var floatArray = ArrayUtils.add(Array[Float](1.1f), 0, 2.2f)
//    assertArrayEquals(Array[Float](2.2f, 1.1f), floatArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null.asInstanceOf[Array[Float]], -1, 2.2f))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    floatArray = ArrayUtils.add(Array[Float](2.3f, 6.4f), 2, 10.5f)
//    assertArrayEquals(Array[Float](2.3f, 6.4f, 10.5f), floatArray)
//    floatArray = ArrayUtils.add(Array[Float](2.6f, 6.7f), 0, -4.8f)
//    assertArrayEquals(Array[Float](-4.8f, 2.6f, 6.7f), floatArray)
//    floatArray = ArrayUtils.add(Array[Float](2.9f, 6.0f, 0.3f), 2, 1.0f)
//    assertArrayEquals(Array[Float](2.9f, 6.0f, 1.0f, 0.3f), floatArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Float](2.3f, 6.4f), 4, 10.5f))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Float](2.3f, 6.4f), -1, 10.5f))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//    // double tests
//    var doubleArray = ArrayUtils.add(Array[Double](1.1), 0, 2.2)
//    assertArrayEquals(Array[Double](2.2, 1.1), doubleArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(null, -1, 2.2))
//    assertEquals("Index: -1, Length: 0", e.getMessage)
//    doubleArray = ArrayUtils.add(Array[Double](2.3, 6.4), 2, 10.5)
//    assertArrayEquals(Array[Double](2.3, 6.4, 10.5), doubleArray)
//    doubleArray = ArrayUtils.add(Array[Double](2.6, 6.7), 0, -4.8)
//    assertArrayEquals(Array[Double](-4.8, 2.6, 6.7), doubleArray)
//    doubleArray = ArrayUtils.add(Array[Double](2.9, 6.0, 0.3), 2, 1.0)
//    assertArrayEquals(Array[Double](2.9, 6.0, 1.0, 0.3), doubleArray)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Double](2.3, 6.4), 4, 10.5))
//    assertEquals("Index: 4, Length: 2", e.getMessage)
//    e = assertThrows[IndexOutOfBoundsException](ArrayUtils.add(Array[Double](2.3, 6.4), -1, 10.5))
//    assertEquals("Index: -1, Length: 2", e.getMessage)
//  }

//  @Test def testJira567(): Unit = {
//    var n: Array[Number] = null
//    // Valid array construction
//    n = ArrayUtils.addAll(Array[Number](Integer.valueOf(1)), Array[Long](JavaLong.valueOf(2)))
//    assertEquals(2, n.length)
//    assertEquals(classOf[Number], n.getClass.getComponentType)
//    // Invalid - can't store Long in Integer array
//    assertThrows[IllegalArgumentException](ArrayUtils.addAll(Array[Integer](Integer.valueOf(1)), Array[Long](JavaLong.valueOf(2))))
//  }

  @Test
  @SuppressWarnings(Array("deprecation")) def testLANG571(): Unit = {
    //import  org.junit.jupiter.api.Assertions.{assertThrows => jAssertThrows}

    val stringArray: Array[String] = null
    val aString: String = null
    assertThrows[IllegalArgumentException] { ArrayUtils.add(stringArray, aString) }
    assertThrows[IllegalArgumentException] { ArrayUtils.add(stringArray, 0, aString) }
    ()
  }
}
