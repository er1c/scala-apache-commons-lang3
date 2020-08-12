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
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import java.util
import java.util.{Collections, Comparator, Date, Random}
import scala.reflect.ClassTag
//import org.junit.jupiter.api.Test
import org.junit.Test

/**
  * Unit tests {@link org.apache.commons.lang3.ArrayUtils}.
  */
@SuppressWarnings(Array("deprecation")) // deliberate use of deprecated code
object ArrayUtilsTest {
  /** A predefined seed used to initialize {@link java.util.Random} in order to get predictable results */
  private val SEED: Long = 16111981L
  @SafeVarargs private def toArrayPropagatingType[T: ClassTag](items: T*): Array[T] = {
    ArrayUtils.toArray(items: _*)
  }

  private def assertArrayEqualsToObject[A <: Any, B <: Any](expected: Array[A], actual: Array[B]): Unit =
    assertArrayEquals(expected.asInstanceOf[Array[Object]], actual.asInstanceOf[Array[Object]])
}

@SuppressWarnings(Array("deprecation"))
class ArrayUtilsTest extends JUnitSuite {
  import ArrayUtilsTest._

  private class TestClass {}

  private def assertIsEquals(array1: Any, array2: Any, array3: Any): Unit = {
    assertTrue(ArrayUtils.isEquals(array1, array1))
    assertTrue(ArrayUtils.isEquals(array2, array2))
    assertTrue(ArrayUtils.isEquals(array3, array3))
    assertFalse(ArrayUtils.isEquals(array1, array2))
    assertFalse(ArrayUtils.isEquals(array2, array1))
    assertFalse(ArrayUtils.isEquals(array1, array3))
    assertFalse(ArrayUtils.isEquals(array3, array1))
    assertFalse(ArrayUtils.isEquals(array1, array2))
    assertFalse(ArrayUtils.isEquals(array2, array1))
  }

  /**
    * Tests generic array creation with parameters of same type.
    */
  @Test def testArrayCreation(): Unit = {
    val array = ArrayUtils.toArray("foo", "bar")
    assertEquals(2, array.length)
    assertEquals("foo", array(0))
    assertEquals("bar", array(1))
  }

  /**
    * Tests generic array creation with parameters of common base type.
    */
  @Test def testArrayCreationWithDifferentTypes(): Unit = {
    val array = ArrayUtils.toArray[Number](Integer.valueOf(42), JavaDouble.valueOf(Math.PI))
    assertEquals(2, array.length)
    assertEquals(Integer.valueOf(42), array(0))
    assertEquals(JavaDouble.valueOf(Math.PI), array(1))
  }

  /**
    * Tests generic array creation with general return type.
    */
  @Test def testArrayCreationWithGeneralReturnType(): Unit = {
    val obj = ArrayUtils.toArray("foo", "bar")
    assertTrue(obj.isInstanceOf[Array[String]])
  }

  @Test def testClone(): Unit = {
    assertArrayEquals(null, ArrayUtils.clone(null.asInstanceOf[Array[AnyRef]]))
    var original1 = new Array[AnyRef](0)
    var cloned1 = ArrayUtils.clone(original1)
    assertArrayEquals(original1, cloned1)
    assertNotSame(original1, cloned1)
    val builder = new StringBuilder("pick")
    original1 = Array[AnyRef](builder, "a", Array[String]("stick"))
    cloned1 = ArrayUtils.clone(original1)
    assertArrayEquals(original1, cloned1)
    assertNotSame(original1, cloned1)
    assertSame(original1(0), cloned1(0))
    assertSame(original1(1), cloned1(1))
    assertSame(original1(2), cloned1(2))
  }

  @Test def testCloneBoolean(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Boolean]]))
    val original = Array[Boolean](true, false)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneByte(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Byte]]))
    val original = Array[Byte](1, 6)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneChar(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Char]]))
    val original = Array[Char]('a', '4')
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneDouble(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Double]]))
    val original = Array[Double](2.4d, 5.7d)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneFloat(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Float]]))
    val original = Array[Float](2.6f, 6.4f)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneInt(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Int]]))
    val original = Array[Int](5, 8)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneLong(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Long]]))
    val original = Array[Long](0L, 1L)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

  @Test def testCloneShort(): Unit = {
    assertNull(ArrayUtils.clone(null.asInstanceOf[Array[Short]]))
    val original = Array[Short](1, 4)
    val cloned = ArrayUtils.clone(original)
    assertArrayEquals(original, cloned)
    assertNotSame(original, cloned)
  }

//  @Test def testConstructor(): Unit = {
//    assertNotNull(new ArrayUtils)
//    val cons = classOf[ArrayUtils].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[ArrayUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[ArrayUtils.type].getModifiers))
//  }

  @Test def testContains(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", null, "0")
    assertFalse(ArrayUtils.contains(null, null))
    assertFalse(ArrayUtils.contains(null, "1"))
    assertTrue(ArrayUtils.contains(array, "0"))
    assertTrue(ArrayUtils.contains(array, "1"))
    assertTrue(ArrayUtils.contains(array, "2"))
    assertTrue(ArrayUtils.contains(array, "3"))
    assertTrue(ArrayUtils.contains(array, null))
    assertFalse(ArrayUtils.contains(array, "notInArray"))
  }

  @Test def testContains_LANG_1261(): Unit = {
    class LANG1261ParentObject {
      override def equals(o: Any) = true
    }
    class LANG1261ChildObject extends LANG1261ParentObject {}
    val array = Array[LANG1261ChildObject](new LANG1261ChildObject)
    assertTrue(ArrayUtils.contains(array, new LANG1261ParentObject))
  }

  @Test def testContainsBoolean(): Unit = {
    var array: Array[Boolean] = null
    assertFalse(ArrayUtils.contains(array, true))
    array = Array[Boolean](true, false, true)
    assertTrue(ArrayUtils.contains(array, true))
    assertTrue(ArrayUtils.contains(array, false))
    array = Array[Boolean](true, true)
    assertTrue(ArrayUtils.contains(array, true))
    assertFalse(ArrayUtils.contains(array, false))
  }

  @Test def testContainsByte(): Unit = {
    var array: Array[Byte] = null
    assertFalse(ArrayUtils.contains(array, 1.toByte))
    array = Array[Byte](0, 1, 2, 3, 0)
    assertTrue(ArrayUtils.contains(array, 0.toByte))
    assertTrue(ArrayUtils.contains(array, 1.toByte))
    assertTrue(ArrayUtils.contains(array, 2.toByte))
    assertTrue(ArrayUtils.contains(array, 3.toByte))
    assertFalse(ArrayUtils.contains(array, 99.toByte))
  }

  @Test def testContainsChar(): Unit = {
    var array: Array[Char] = null
    assertFalse(ArrayUtils.contains(array, 'b'))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    assertTrue(ArrayUtils.contains(array, 'a'))
    assertTrue(ArrayUtils.contains(array, 'b'))
    assertTrue(ArrayUtils.contains(array, 'c'))
    assertTrue(ArrayUtils.contains(array, 'd'))
    assertFalse(ArrayUtils.contains(array, 'e'))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testContainsDouble(): Unit = {
    var array: Array[Double] = null
    assertFalse(ArrayUtils.contains(array, 1.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertTrue(ArrayUtils.contains(array, 0.toDouble))
    assertTrue(ArrayUtils.contains(array, 1.toDouble))
    assertTrue(ArrayUtils.contains(array, 2.toDouble))
    assertTrue(ArrayUtils.contains(array, 3.toDouble))
    assertFalse(ArrayUtils.contains(array, 99.toDouble))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testContainsDoubleTolerance(): Unit = {
    var array: Array[Double] = null
    assertFalse(ArrayUtils.contains(array, 1.toDouble, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertFalse(ArrayUtils.contains(array, 4.0, 0.33))
    assertFalse(ArrayUtils.contains(array, 2.5, 0.49))
    assertTrue(ArrayUtils.contains(array, 2.5, 0.50))
    assertTrue(ArrayUtils.contains(array, 2.5, 0.51))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testContainsFloat(): Unit = {
    var array: Array[Float] = null
    assertFalse(ArrayUtils.contains(array, 1.toFloat))
    array = Array[Float](0, 1, 2, 3, 0)
    assertTrue(ArrayUtils.contains(array, 0.toFloat))
    assertTrue(ArrayUtils.contains(array, 1.toFloat))
    assertTrue(ArrayUtils.contains(array, 2.toFloat))
    assertTrue(ArrayUtils.contains(array, 3.toFloat))
    assertFalse(ArrayUtils.contains(array, 99.toFloat))
  }

  @Test def testContainsInt(): Unit = {
    var array: Array[Int] = null
    assertFalse(ArrayUtils.contains(array, 1))
    array = Array[Int](0, 1, 2, 3, 0)
    assertTrue(ArrayUtils.contains(array, 0))
    assertTrue(ArrayUtils.contains(array, 1))
    assertTrue(ArrayUtils.contains(array, 2))
    assertTrue(ArrayUtils.contains(array, 3))
    assertFalse(ArrayUtils.contains(array, 99))
  }

  @Test def testContainsLong(): Unit = {
    var array: Array[Long] = null
    assertFalse(ArrayUtils.contains(array, 1))
    array = Array[Long](0, 1, 2, 3, 0)
    assertTrue(ArrayUtils.contains(array, 0))
    assertTrue(ArrayUtils.contains(array, 1))
    assertTrue(ArrayUtils.contains(array, 2))
    assertTrue(ArrayUtils.contains(array, 3))
    assertFalse(ArrayUtils.contains(array, 99))
  }

  @Test def testContainsShort(): Unit = {
    var array: Array[Short] = null
    assertFalse(ArrayUtils.contains(array, 1.toShort))
    array = Array[Short](0, 1, 2, 3, 0)
    assertTrue(ArrayUtils.contains(array, 0.toShort))
    assertTrue(ArrayUtils.contains(array, 1.toShort))
    assertTrue(ArrayUtils.contains(array, 2.toShort))
    assertTrue(ArrayUtils.contains(array, 3.toShort))
    assertFalse(ArrayUtils.contains(array, 99.toShort))
  }

//  @Test def testCreatePrimitiveArray(): Unit = {
//    assertNull(ArrayUtils.toPrimitive(null.asInstanceOf[Array[AnyRef]]))
//    assertArrayEquals(new Array[Int], ArrayUtils.toPrimitive(new Array[Integer]))
//    assertArrayEquals(Array[Short](2), ArrayUtils.toPrimitive(Array[Short](2)))
//    assertArrayEquals(Array[Long](2, 3), ArrayUtils.toPrimitive(Array[Long](2L, 3L)))
//    assertArrayEquals(Array[Float](3.14f), ArrayUtils.toPrimitive(Array[Float](3.14f)), 0.1f)
//    assertArrayEquals(Array[Double](2.718), ArrayUtils.toPrimitive(Array[Double](2.718)), 0.1)
//  }

  /**
    * Tests generic empty array creation with generic type.
    */
  @Test def testEmptyArrayCreation(): Unit = {
    val array: Array[String] = ArrayUtils.toArray[String]()
    assertEquals(0, array.length)
  }

  @Test def testGet(): Unit = {
    assertNull(ArrayUtils.get(null.asInstanceOf[Array[Any]], -1))
    assertNull(ArrayUtils.get(null.asInstanceOf[Array[Any]], 0))
    assertNull(ArrayUtils.get(null.asInstanceOf[Array[Any]], 1))
    val array0 = Array[Any]()
    assertNull(ArrayUtils.get(array0, -1))
    assertNull(ArrayUtils.get(array0, 0))
    assertNull(ArrayUtils.get(array0, 1))
    val array1 = Array(StringUtils.EMPTY)
    assertEquals(null, ArrayUtils.get(array1, -1))
    assertEquals(StringUtils.EMPTY, ArrayUtils.get(array1, 0))
    assertEquals(null, ArrayUtils.get(array1, 1))
  }

  @Test def testGetDefault(): Unit = {
    // null default
    assertNull(ArrayUtils.get(null, -1, null))
    assertNull(ArrayUtils.get(null, 0, null))
    assertNull(ArrayUtils.get(null, 1, null))
    val array0 = Array[Any]()
    assertNull(ArrayUtils.get(array0, -1, null))
    assertNull(ArrayUtils.get(array0, 0, null))
    assertNull(ArrayUtils.get(array0, 1, null))
    val array1: Array[String] = Array(StringUtils.EMPTY)
    assertEquals(null, ArrayUtils.get(array1, -1, null))
    assertEquals(StringUtils.EMPTY, ArrayUtils.get(array1, 0, null))
    assertEquals(null, ArrayUtils.get(array1, 1, null))

    // non-null default
    val defaultValue = "defaultValue"
    //val array1 = Array(StringUtils.EMPTY)
    assertEquals(defaultValue, ArrayUtils.get(array1, -1, defaultValue))
    assertEquals(StringUtils.EMPTY, ArrayUtils.get(array1, 0, defaultValue))
    assertEquals(defaultValue, ArrayUtils.get(array1, 1, defaultValue))

  }

  @Test def testGetLength(): Unit = {
    assertEquals(0, ArrayUtils.getLength(null))
    val emptyObjectArray = Array[AnyRef]()
    val notEmptyObjectArray = Array[AnyRef]("aValue")
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyObjectArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyObjectArray))
    val emptyIntArray: Array[Int] = Array[Int]()
    val notEmptyIntArray = Array[Int](1)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyIntArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyIntArray))
    val emptyShortArray = Array[Short]()
    val notEmptyShortArray = Array[Short](1)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyShortArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyShortArray))
    val emptyCharArray = Array[Char]()
    val notEmptyCharArray = Array[Char](1)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyCharArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyCharArray))
    val emptyByteArray = Array[Byte]()
    val notEmptyByteArray = Array[Byte](1)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyByteArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyByteArray))
    val emptyDoubleArray = Array[Double]()
    val notEmptyDoubleArray = Array[Double](1.0)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyDoubleArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyDoubleArray))
    val emptyFloatArray = Array[Float]()
    val notEmptyFloatArray = Array[Float](1.0f)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyFloatArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyFloatArray))
    val emptyBooleanArray = Array[Boolean]()
    val notEmptyBooleanArray = Array[Boolean](true)
    assertEquals(0, ArrayUtils.getLength(null))
    assertEquals(0, ArrayUtils.getLength(emptyBooleanArray))
    assertEquals(1, ArrayUtils.getLength(notEmptyBooleanArray))
    assertThrows[IllegalArgumentException](ArrayUtils.getLength("notAnArray"))
    ()
  }

  @Test def testHashCode(): Unit = {
    val array1 = Array[Array[Long]](Array(2, 5), Array(4, 5))
    val array2 = Array[Array[Long]](Array(2, 5), Array(4, 6))
    assertEquals(ArrayUtils.hashCode(array1), ArrayUtils.hashCode(array1))
    assertNotEquals(ArrayUtils.hashCode(array1), ArrayUtils.hashCode(array2))
    val array3 = Array[AnyRef](new String(Array[Char]('A', 'B')))
    val array4 = Array[AnyRef]("AB")
    assertEquals(ArrayUtils.hashCode(array3), ArrayUtils.hashCode(array3))
    assertEquals(ArrayUtils.hashCode(array3), ArrayUtils.hashCode(array4))
    val arrayA = Array[AnyRef](Array[Boolean](true, false), Array[Int](6, 7))
    val arrayB = Array[AnyRef](Array[Boolean](true, false), Array[Int](6, 7))
    assertEquals(ArrayUtils.hashCode(arrayB), ArrayUtils.hashCode(arrayA))
  }

  @Test def testIndexesOf(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", null, "0")
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(null.asInstanceOf[Array[AnyRef]], null))
    assertEquals(emptySet, ArrayUtils.indexesOf(new Array[AnyRef](0), "0"))
    testSet.set(5)
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "0"))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "2"))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "3"))
    testSet.clear()
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, null))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, "notInArray"))
  }

  @Test def testIndexesOfBoolean(): Unit = {
    var array: Array[Boolean] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, true))
    array = new Array[Boolean](0)
    assertEquals(emptySet, ArrayUtils.indexesOf(array, true))
    array = Array[Boolean](true, false, true)
    testSet.set(0)
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, true))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, false))
    array = Array[Boolean](true, true)
    assertEquals(emptySet, ArrayUtils.indexesOf(array, false))
  }

  @Test def testIndexesOfBooleanWithStartIndex(): Unit = {
    var array: Array[Boolean] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, true, 0))
    array = new Array[Boolean](0)
    assertEquals(emptySet, ArrayUtils.indexesOf(array, true, 0))
    array = Array[Boolean](true, false, true)
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, true, 1))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, true, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, false, 1))
    array = Array[Boolean](true, true)
    assertEquals(emptySet, ArrayUtils.indexesOf(array, false, 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, false, -1))
  }

  @Test def testIndexesOfByte(): Unit = {
    var array: Array[Byte] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toByte))
    array = Array[Byte](0, 1, 2, 3, 0)
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toByte))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1.toByte))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2.toByte))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3.toByte))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99.toByte))
  }

  @Test def testIndexesOfByteWithStartIndex(): Unit = {
    var array: Array[Byte] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toByte, 2))
    array = Array[Byte](0, 1, 2, 3, 0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toByte, 2))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toByte, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1.toByte, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2.toByte, 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3.toByte, 0))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3.toByte, -1))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99.toByte, 0))
  }

  @Test def testIndexesOfChar(): Unit = {
    var array: Array[Char] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 'a'))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'a'))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'b'))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'c'))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'd'))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 'e'))
  }

  @Test def testIndexesOfCharWithStartIndex(): Unit = {
    var array: Array[Char] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 'a', 0))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'a', 2))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'a', 0))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'a', -1))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'b', 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'c', 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 'd', 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 'd', 5))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 'e', 0))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexesOfDouble(): Unit = {
    var array: Array[Double] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0))
    array = Array[Double](0, 1, 2, 3, 0)
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexesOfDoubleTolerance(): Unit = {
    var array: Array[Double] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toDouble, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toDouble, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toDouble, 0.3))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 4.15, 2.0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1.00001324, 0.0001))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexesOfDoubleWithStartIndex(): Unit = {
    var array: Array[Double] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0, 2))
    array = Array[Double](0, 1, 2, 3, 0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 2))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, 0))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, -1))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99, 0))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexesOfDoubleWithStartIndexTolerance(): Unit = {
    var array: Array[Double] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toDouble, 0, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toDouble, 0, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toDouble, 1, 0.3))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toDouble, 0, 0.3))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, 0, 0.35))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, 2, 0.35))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, -1, 0.35))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 2, 3, 0.35))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 4.15, 0, 2.0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1.00001324, 0, 0.0001))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexesOfFloat(): Unit = {
    var array: Array[Float] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0))
    array = Array[Float](0, 1, 2, 3, 0)
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexesOfFloatWithStartIndex(): Unit = {
    var array: Array[Float] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0, 2))
    array = Array[Float](0f, 1f, 2f, 3f, 0f)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 2))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, 0))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, -1))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99, 0))
  }

  @Test def testIndexesOfIntWithStartIndex(): Unit = {
    var array: Array[Int] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0, 2))
    array = Array[Int](0, 1, 2, 3, 0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 2))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, 0))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, -1))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99, 0))
  }

  @Test def testIndexesOfLong(): Unit = {
    val array = Array[Long](0, 1, 2, 3)
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(null.asInstanceOf[Array[Long]], 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 4))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3))
  }

  @Test def testIndexesOfLongWithStartIndex(): Unit = {
    val array = Array[Long](0, 1, 2, 3, 2, 1, 0, 1)
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(null.asInstanceOf[Array[Long]], 0, 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 4, 0))
    testSet.set(6)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 1))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0, 0))
    testSet.clear()
    testSet.set(1)
    testSet.set(5)
    testSet.set(7)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1, 0))
    testSet.clear()
    testSet.set(2)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2, 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3, 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 3, 8))
  }

  @Test def testIndexesOfShort(): Unit = {
    var array: Array[Short] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toShort))
    array = Array[Short](0, 1, 2, 3, 0)
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toShort))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1.toShort))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2.toShort))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3.toShort))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99.toShort))
  }

  @Test def testIndexesOfShortWithStartIndex(): Unit = {
    var array: Array[Short] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0.toShort, 2))
    array = Array[Short](0, 1, 2, 3, 0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toShort, 2))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0.toShort, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1.toShort, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2.toShort, 0))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3.toShort, 0))
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3.toShort, -1))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99.toShort, 0))
  }

  @Test def testIndexesOfWithStartIndex(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", "2", "3", "1", null, "0")
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(null, null, 2))
    assertEquals(emptySet, ArrayUtils.indexesOf(new Array[AnyRef](0), "0", 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(null, "0", 2))
    testSet.set(8)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "0", 8))
    testSet.set(0)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "0", 0))
    testSet.clear()
    testSet.set(6)
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "1", 0))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, "1", 9))
    testSet.clear()
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "2", 3))
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "2", 0))
    testSet.clear()
    testSet.set(3)
    testSet.set(5)
    assertEquals(testSet, ArrayUtils.indexesOf(array, "3", 0))
    testSet.clear()
    testSet.set(7)
    assertEquals(testSet, ArrayUtils.indexesOf(array, null, 0))
  }

  @Test def testIndexOf(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", null, "0")
    assertEquals(-1, ArrayUtils.indexOf(null, null))
    assertEquals(-1, ArrayUtils.indexOf(null, "0"))
    assertEquals(-1, ArrayUtils.indexOf(new Array[AnyRef](0), "0"))
    assertEquals(0, ArrayUtils.indexOf(array, "0"))
    assertEquals(1, ArrayUtils.indexOf(array, "1"))
    assertEquals(2, ArrayUtils.indexOf(array, "2"))
    assertEquals(3, ArrayUtils.indexOf(array, "3"))
    assertEquals(4, ArrayUtils.indexOf(array, null))
    assertEquals(-1, ArrayUtils.indexOf(array, "notInArray"))
  }

  @Test def testIndexOfBoolean(): Unit = {
    var array: Array[Boolean] = null
    assertEquals(-1, ArrayUtils.indexOf(array, true))
    array = new Array[Boolean](0)
    assertEquals(-1, ArrayUtils.indexOf(array, true))
    array = Array[Boolean](true, false, true)
    assertEquals(0, ArrayUtils.indexOf(array, true))
    assertEquals(1, ArrayUtils.indexOf(array, false))
    array = Array[Boolean](true, true)
    assertEquals(-1, ArrayUtils.indexOf(array, false))
  }

  @Test def testIndexOfBooleanWithStartIndex(): Unit = {
    var array: Array[Boolean] = null
    assertEquals(-1, ArrayUtils.indexOf(array, true, 2))
    array = new Array[Boolean](0)
    assertEquals(-1, ArrayUtils.indexOf(array, true, 2))
    array = Array[Boolean](true, false, true)
    assertEquals(2, ArrayUtils.indexOf(array, true, 1))
    assertEquals(-1, ArrayUtils.indexOf(array, false, 2))
    assertEquals(1, ArrayUtils.indexOf(array, false, 0))
    assertEquals(1, ArrayUtils.indexOf(array, false, -1))
    array = Array[Boolean](true, true)
    assertEquals(-1, ArrayUtils.indexOf(array, false, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, false, -1))
  }

  @Test def testIndexOfByte(): Unit = {
    var array: Array[Byte] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toByte))
    array = Array[Byte](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0.toByte))
    assertEquals(1, ArrayUtils.indexOf(array, 1.toByte))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toByte))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toByte))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toByte))
  }

  @Test def testIndexOfByteWithStartIndex(): Unit = {
    var array: Array[Byte] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toByte, 2))
    array = Array[Byte](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.indexOf(array, 0.toByte, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 1.toByte, 2))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toByte, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toByte, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toByte, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toByte, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toByte, 6))
  }

  @Test def testIndexOfChar(): Unit = {
    var array: Array[Char] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 'a'))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    assertEquals(0, ArrayUtils.indexOf(array, 'a'))
    assertEquals(1, ArrayUtils.indexOf(array, 'b'))
    assertEquals(2, ArrayUtils.indexOf(array, 'c'))
    assertEquals(3, ArrayUtils.indexOf(array, 'd'))
    assertEquals(-1, ArrayUtils.indexOf(array, 'e'))
  }

  @Test def testIndexOfCharWithStartIndex(): Unit = {
    var array: Array[Char] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 'a', 2))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    assertEquals(4, ArrayUtils.indexOf(array, 'a', 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 'b', 2))
    assertEquals(2, ArrayUtils.indexOf(array, 'c', 2))
    assertEquals(3, ArrayUtils.indexOf(array, 'd', 2))
    assertEquals(3, ArrayUtils.indexOf(array, 'd', -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 'e', 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 'a', 6))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexOfDouble(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0.toDouble))
    assertEquals(1, ArrayUtils.indexOf(array, 1.toDouble))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toDouble))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toDouble))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toDouble, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toDouble))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexOfDoubleTolerance(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0.toDouble, 0.3))
    assertEquals(2, ArrayUtils.indexOf(array, 2.2, 0.35))
    assertEquals(3, ArrayUtils.indexOf(array, 4.15, 2.0))
    assertEquals(1, ArrayUtils.indexOf(array, 1.00001324, 0.0001))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexOfDoubleWithStartIndex(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 2))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 2))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.indexOf(array, 0.toDouble, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 1.toDouble, 2))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toDouble, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toDouble, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toDouble, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 6))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexOfDoubleWithStartIndexTolerance(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 2, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 2, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toDouble, 99, 0.3))
    assertEquals(0, ArrayUtils.indexOf(array, 0.toDouble, 0, 0.3))
    assertEquals(4, ArrayUtils.indexOf(array, 0.toDouble, 3, 0.3))
    assertEquals(2, ArrayUtils.indexOf(array, 2.2, 0, 0.35))
    assertEquals(3, ArrayUtils.indexOf(array, 4.15, 0, 2.0))
    assertEquals(1, ArrayUtils.indexOf(array, 1.00001324, 0, 0.0001))
    assertEquals(3, ArrayUtils.indexOf(array, 4.15, -1, 2.0))
    assertEquals(1, ArrayUtils.indexOf(array, 1.00001324, -300, 0.0001))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexOfFloat(): Unit = {
    var array: Array[Float] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toFloat))
    array = new Array[Float](0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toFloat))
    array = Array[Float](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0.toFloat))
    assertEquals(1, ArrayUtils.indexOf(array, 1.toFloat))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toFloat))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toFloat))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toFloat))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testIndexOfFloatWithStartIndex(): Unit = {
    var array: Array[Float] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toFloat, 2))
    array = new Array[Float](0)
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toFloat, 2))
    array = Array[Float](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.indexOf(array, 0.toFloat, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 1.toFloat, 2))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toFloat, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toFloat, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toFloat, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toFloat, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toFloat, 6))
  }

  @Test def testIndexOfInt(): Unit = {
    var array: Array[Int] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0))
    array = Array[Int](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0))
    assertEquals(1, ArrayUtils.indexOf(array, 1))
    assertEquals(2, ArrayUtils.indexOf(array, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3))
    assertEquals(-1, ArrayUtils.indexOf(array, 99))
  }

  @Test def testIndexOfIntWithStartIndex(): Unit = {
    var array: Array[Int] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 2))
    array = Array[Int](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.indexOf(array, 0, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 1, 2))
    assertEquals(2, ArrayUtils.indexOf(array, 2, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 99, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 6))
  }

  @Test def testIndexOfLong(): Unit = {
    var array: Array[Long] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0))
    array = Array[Long](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0))
    assertEquals(1, ArrayUtils.indexOf(array, 1))
    assertEquals(2, ArrayUtils.indexOf(array, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3))
    assertEquals(-1, ArrayUtils.indexOf(array, 99))
  }

  @Test def testIndexOfLongWithStartIndex(): Unit = {
    var array: Array[Long] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 2))
    array = Array[Long](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.indexOf(array, 0, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 1, 2))
    assertEquals(2, ArrayUtils.indexOf(array, 2, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 99, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 0, 6))
  }

  @Test def testIndexOfShort(): Unit = {
    var array: Array[Short] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toShort))
    array = Array[Short](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.indexOf(array, 0.toShort))
    assertEquals(1, ArrayUtils.indexOf(array, 1.toShort))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toShort))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toShort))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toShort))
  }

  @Test def testIndexOfShortWithStartIndex(): Unit = {
    var array: Array[Short] = null
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toShort, 2))
    array = Array[Short](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.indexOf(array, 0.toShort, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, 1.toShort, 2))
    assertEquals(2, ArrayUtils.indexOf(array, 2.toShort, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toShort, 2))
    assertEquals(3, ArrayUtils.indexOf(array, 3.toShort, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, 99.toShort, 0))
    assertEquals(-1, ArrayUtils.indexOf(array, 0.toShort, 6))
  }

  @Test def testIndexOfWithStartIndex(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", null, "0")
    assertEquals(-1, ArrayUtils.indexOf(null, null, 2))
    assertEquals(-1, ArrayUtils.indexOf(new Array[AnyRef](0), "0", 0))
    assertEquals(-1, ArrayUtils.indexOf(null, "0", 2))
    assertEquals(5, ArrayUtils.indexOf(array, "0", 2))
    assertEquals(-1, ArrayUtils.indexOf(array, "1", 2))
    assertEquals(2, ArrayUtils.indexOf(array, "2", 2))
    assertEquals(3, ArrayUtils.indexOf(array, "3", 2))
    assertEquals(4, ArrayUtils.indexOf(array, null, 2))
    assertEquals(-1, ArrayUtils.indexOf(array, "notInArray", 2))
    assertEquals(4, ArrayUtils.indexOf(array, null, -1))
    assertEquals(-1, ArrayUtils.indexOf(array, null, 8))
    assertEquals(-1, ArrayUtils.indexOf(array, "0", 8))
  }

  /**
    * Tests generic array creation with generic type.
    */
  @Test def testIndirectArrayCreation(): Unit = {
    val array = ArrayUtilsTest.toArrayPropagatingType("foo", "bar")
    assertEquals(2, array.length)
    assertEquals("foo", array(0))
    assertEquals("bar", array(1))
  }

  /**
    * Tests indirect generic empty array creation with generic type.
    */
  @Test def testIndirectEmptyArrayCreation(): Unit = {
    val array = ArrayUtilsTest.toArrayPropagatingType[String]()
    assertEquals(0, array.length)
  }

  @Test def testIsArrayIndexValid(): Unit = {
    assertFalse(ArrayUtils.isArrayIndexValid(null, 0))
    val array = new Array[String](1)
    //too big
    assertFalse(ArrayUtils.isArrayIndexValid(array, 1))
    //negative index
    assertFalse(ArrayUtils.isArrayIndexValid(array, -1))
    //good to go
    assertTrue(ArrayUtils.isArrayIndexValid(array, 0))
  }

  /**
    * Test for {@link ArrayUtils# isEmpty ( java.lang.Object[ ] )}.
    */
  @Test def testIsEmptyObject(): Unit = {
    val emptyArray = Array[AnyRef]()
    val notEmptyArray = Array[AnyRef](new String("Value"))
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[AnyRef]]))
    assertTrue(ArrayUtils.isEmpty(emptyArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyArray))
  }

  /**
    * Tests for {@link ArrayUtils# isEmpty ( long[ ] )},
    * {@link ArrayUtils# isEmpty ( int[ ] )},
    * {@link ArrayUtils# isEmpty ( short[ ] )},
    * {@link ArrayUtils# isEmpty ( char[ ] )},
    * {@link ArrayUtils# isEmpty ( byte[ ] )},
    * {@link ArrayUtils# isEmpty ( double[ ] )},
    * {@link ArrayUtils# isEmpty ( float[ ] )} and
    * {@link ArrayUtils# isEmpty ( boolean[ ] )}.
    */
  @Test def testIsEmptyPrimitives(): Unit = {
    val emptyLongArray = Array[Long]()
    val notEmptyLongArray = Array[Long](1L)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Long]]))
    assertTrue(ArrayUtils.isEmpty(emptyLongArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyLongArray))
    val emptyIntArray = Array[Int]()
    val notEmptyIntArray = Array[Int](1)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Int]]))
    assertTrue(ArrayUtils.isEmpty(emptyIntArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyIntArray))
    val emptyShortArray = Array[Short]()
    val notEmptyShortArray = Array[Short](1)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Short]]))
    assertTrue(ArrayUtils.isEmpty(emptyShortArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyShortArray))
    val emptyCharArray = Array[Char]()
    val notEmptyCharArray = Array[Char](1)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Char]]))
    assertTrue(ArrayUtils.isEmpty(emptyCharArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyCharArray))
    val emptyByteArray = Array[Byte]()
    val notEmptyByteArray = Array[Byte](1)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Byte]]))
    assertTrue(ArrayUtils.isEmpty(emptyByteArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyByteArray))
    val emptyDoubleArray = Array[Double]()
    val notEmptyDoubleArray = Array[Double](1.0)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Double]]))
    assertTrue(ArrayUtils.isEmpty(emptyDoubleArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyDoubleArray))
    val emptyFloatArray = Array[Float]()
    val notEmptyFloatArray = Array[Float](1.0f)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Float]]))
    assertTrue(ArrayUtils.isEmpty(emptyFloatArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyFloatArray))
    val emptyBooleanArray = Array[Boolean]()
    val notEmptyBooleanArray = Array[Boolean](true)
    assertTrue(ArrayUtils.isEmpty(null.asInstanceOf[Array[Boolean]]))
    assertTrue(ArrayUtils.isEmpty(emptyBooleanArray))
    assertFalse(ArrayUtils.isEmpty(notEmptyBooleanArray))
  }

  @Test def testIsEquals(): Unit = {
    val larray1 = Array[Array[Long]](Array(2, 5), Array(4, 5))
    val larray2 = Array[Array[Long]](Array(2, 5), Array(4, 6))
    val larray3 = Array[Long](2, 5)
    this.assertIsEquals(larray1, larray2, larray3)
    val iarray1 = Array[Array[Int]](Array(2, 5), Array(4, 5))
    val iarray2 = Array[Array[Int]](Array(2, 5), Array(4, 6))
    val iarray3 = Array[Int](2, 5)
    this.assertIsEquals(iarray1, iarray2, iarray3)
    val sarray1 = Array[Array[Short]](Array(2, 5), Array(4, 5))
    val sarray2 = Array[Array[Short]](Array(2, 5), Array(4, 6))
    val sarray3 = Array[Short](2, 5)
    this.assertIsEquals(sarray1, sarray2, sarray3)
    val farray1 = Array[Array[Float]](Array(2, 5), Array(4, 5))
    val farray2 = Array[Array[Float]](Array(2, 5), Array(4, 6))
    val farray3 = Array[Float](2, 5)
    this.assertIsEquals(farray1, farray2, farray3)
    val darray1 = Array[Array[Double]](Array(2, 5), Array(4, 5))
    val darray2 = Array[Array[Double]](Array(2, 5), Array(4, 6))
    val darray3 = Array[Double](2, 5)
    this.assertIsEquals(darray1, darray2, darray3)
    val byteArray1 = Array[Array[Byte]](Array(2, 5), Array(4, 5))
    val byteArray2 = Array[Array[Byte]](Array(2, 5), Array(4, 6))
    val byteArray3 = Array[Byte](2, 5)
    this.assertIsEquals(byteArray1, byteArray2, byteArray3)
    val charArray1 = Array[Array[Char]](Array(2, 5), Array(4, 5))
    val charArray2 = Array[Array[Char]](Array(2, 5), Array(4, 6))
    val charArray3 = Array[Char](2, 5)
    this.assertIsEquals(charArray1, charArray2, charArray3)
    val barray1 = Array[Array[Boolean]](Array(true, false), Array(true, true))
    val barray2 = Array[Array[Boolean]](Array(true, false), Array(true, false))
    val barray3 = Array[Boolean](false, true)
    this.assertIsEquals(barray1, barray2, barray3)
    val array3 = Array[AnyRef](new String(Array[Char]('A', 'B')))
    val array4 = Array[AnyRef]("AB")
    assertTrue(ArrayUtils.isEquals(array3, array3))
    assertTrue(ArrayUtils.isEquals(array3, array4))
    assertTrue(ArrayUtils.isEquals(null, null))
    assertFalse(ArrayUtils.isEquals(null, array4))
  }

  /**
    * Test for {@link ArrayUtils# isNotEmpty ( java.lang.Object[ ] )}.
    */
  @Test def testIsNotEmptyObject(): Unit = {
    val emptyArray = Array[AnyRef]()
    val notEmptyArray = Array[AnyRef](new String("Value"))
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[AnyRef]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyArray))
  }

  /**
    * Tests for {@link ArrayUtils# isNotEmpty ( long[ ] )},
    * {@link ArrayUtils# isNotEmpty ( int[ ] )},
    * {@link ArrayUtils# isNotEmpty ( short[ ] )},
    * {@link ArrayUtils# isNotEmpty ( char[ ] )},
    * {@link ArrayUtils# isNotEmpty ( byte[ ] )},
    * {@link ArrayUtils# isNotEmpty ( double[ ] )},
    * {@link ArrayUtils# isNotEmpty ( float[ ] )} and
    * {@link ArrayUtils# isNotEmpty ( boolean[ ] )}.
    */
  @Test def testIsNotEmptyPrimitives(): Unit = {
    val emptyLongArray = Array[Long]()
    val notEmptyLongArray = Array[Long](1L)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Long]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyLongArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyLongArray))
    val emptyIntArray = Array[Int]()
    val notEmptyIntArray = Array[Int](1)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Int]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyIntArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyIntArray))
    val emptyShortArray = Array[Short]()
    val notEmptyShortArray = Array[Short](1)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Short]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyShortArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyShortArray))
    val emptyCharArray = Array[Char]()
    val notEmptyCharArray = Array[Char](1)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Char]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyCharArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyCharArray))
    val emptyByteArray = Array[Byte]()
    val notEmptyByteArray = Array[Byte](1)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Byte]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyByteArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyByteArray))
    val emptyDoubleArray = Array[Double]()
    val notEmptyDoubleArray = Array[Double](1.0)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Double]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyDoubleArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyDoubleArray))
    val emptyFloatArray = Array[Float]()
    val notEmptyFloatArray = Array[Float](1.0f)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Float]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyFloatArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyFloatArray))
    val emptyBooleanArray = Array[Boolean]()
    val notEmptyBooleanArray = Array[Boolean](true)
    assertFalse(ArrayUtils.isNotEmpty(null.asInstanceOf[Array[Boolean]]))
    assertFalse(ArrayUtils.isNotEmpty(emptyBooleanArray))
    assertTrue(ArrayUtils.isNotEmpty(notEmptyBooleanArray))
  }

  @Test def testIsSorted(): Unit = {
    var array: Array[Integer] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Integer](1)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Integer](1, 2, 3)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Integer](1, 3, 2)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedBool(): Unit = {
    var array: Array[Boolean] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Boolean](true)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Boolean](false, true)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Boolean](true, false)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedByte(): Unit = {
    var array: Array[Byte] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Byte](0x10)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Byte](0x10, 0x20, 0x30)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Byte](0x10, 0x30, 0x20)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedChar(): Unit = {
    var array: Array[Char] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Char]('a')
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Char]('a', 'b', 'c')
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Char]('a', 'c', 'b')
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedComparator(): Unit = {
    val c: Comparator[Integer] = implicitly[Ordering[Integer]].reversed
    var array: Array[Integer] = null
    assertTrue(ArrayUtils.isSorted(array, c))
    array = Array[Integer](1)
    assertTrue(ArrayUtils.isSorted(array, c))
    array = Array[Integer](3, 2, 1)
    assertTrue(ArrayUtils.isSorted(array, c))
    array = Array[Integer](1, 3, 2)
    assertFalse(ArrayUtils.isSorted(array, c))
  }

  @Test def testIsSortedDouble(): Unit = {
    var array: Array[Double] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Double](0.0)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Double](-1.0, 0.0, 0.1, 0.2)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Double](-1.0, 0.2, 0.1, 0.0)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedFloat(): Unit = {
    var array: Array[Float] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Float](0f)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Float](-1f, 0f, 0.1f, 0.2f)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Float](-1f, 0.2f, 0.1f, 0f)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedInt(): Unit = {
    var array: Array[Int] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Int](1)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Int](1, 2, 3)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Int](1, 3, 2)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedLong(): Unit = {
    var array: Array[Long] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Long](0L)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Long](-1L, 0L, 1L)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Long](-1L, 1L, 0L)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testIsSortedNullComparator(): Unit = {
    assertThrows[IllegalArgumentException](ArrayUtils.isSorted(null, null))
    ()
  }

  @Test def testIsSortedShort(): Unit = {
    var array: Array[Short] = null
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Short](0)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Short](-1, 0, 1)
    assertTrue(ArrayUtils.isSorted(array))
    array = Array[Short](-1, 1, 0)
    assertFalse(ArrayUtils.isSorted(array))
  }

  @Test def testLastIndexOf(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", null, "0")
    assertEquals(-1, ArrayUtils.lastIndexOf(null, null))
    assertEquals(-1, ArrayUtils.lastIndexOf(null, "0"))
    assertEquals(5, ArrayUtils.lastIndexOf(array, "0"))
    assertEquals(1, ArrayUtils.lastIndexOf(array, "1"))
    assertEquals(2, ArrayUtils.lastIndexOf(array, "2"))
    assertEquals(3, ArrayUtils.lastIndexOf(array, "3"))
    assertEquals(4, ArrayUtils.lastIndexOf(array, null))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "notInArray"))
  }

  @Test def testLastIndexOfBoolean(): Unit = {
    var array: Array[Boolean] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true))
    array = new Array[Boolean](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true))
    array = Array[Boolean](true, false, true)
    assertEquals(2, ArrayUtils.lastIndexOf(array, true))
    assertEquals(1, ArrayUtils.lastIndexOf(array, false))
    array = Array[Boolean](true, true)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, false))
  }

  @Test def testLastIndexOfBooleanWithStartIndex(): Unit = {
    var array: Array[Boolean] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, 2))
    array = new Array[Boolean](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, 2))
    array = Array[Boolean](true, false, true)
    assertEquals(2, ArrayUtils.lastIndexOf(array, true, 2))
    assertEquals(0, ArrayUtils.lastIndexOf(array, true, 1))
    assertEquals(1, ArrayUtils.lastIndexOf(array, false, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, -1))
    array = Array[Boolean](true, true)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, false, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, true, -1))
  }

  @Test def testLastIndexOfByte(): Unit = {
    var array: Array[Byte] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toByte))
    array = Array[Byte](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toByte))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toByte))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toByte))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3.toByte))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toByte))
  }

  @Test def testLastIndexOfByteWithStartIndex(): Unit = {
    var array: Array[Byte] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toByte, 2))
    array = Array[Byte](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0.toByte, 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toByte, 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toByte, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toByte, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toByte, -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toByte))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toByte, 88))
  }

  @Test def testLastIndexOfChar(): Unit = {
    var array: Array[Char] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'a'))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    assertEquals(4, ArrayUtils.lastIndexOf(array, 'a'))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 'b'))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 'c'))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 'd'))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'e'))
  }

  @Test def testLastIndexOfCharWithStartIndex(): Unit = {
    var array: Array[Char] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'a', 2))
    array = Array[Char]('a', 'b', 'c', 'd', 'a')
    assertEquals(0, ArrayUtils.lastIndexOf(array, 'a', 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 'b', 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 'c', 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'd', 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'd', -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 'e'))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 'a', 88))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testLastIndexOfDouble(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toDouble))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toDouble))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toDouble))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3.toDouble))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toDouble))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testLastIndexOfDoubleTolerance(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toDouble, 0.3))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.2, 0.35))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 4.15, 2.0))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.00001324, 0.0001))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testLastIndexOfDoubleWithStartIndex(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble, 2))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble, 2))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0.toDouble, 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toDouble, 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toDouble, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toDouble, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toDouble, -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toDouble))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toDouble, 88))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testLastIndexOfDoubleWithStartIndexTolerance(): Unit = {
    var array: Array[Double] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble, 2, 0.toDouble))
    array = new Array[Double](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toDouble, 2, 0.toDouble))
    array = Array[Double](3.toDouble)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 1.toDouble, 0, 0.toDouble))
    array = Array[Double](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toDouble, 99, 0.3))
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0.toDouble, 3, 0.3))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.2, 3, 0.35))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 4.15, array.length, 2.0))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.00001324, array.length, 0.0001))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 4.15, -200, 2.0))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testLastIndexOfFloat(): Unit = {
    var array: Array[Float] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toFloat))
    array = new Array[Float](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toFloat))
    array = Array[Float](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toFloat))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toFloat))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toFloat))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3.toFloat))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toFloat))
  }

  @SuppressWarnings(Array("cast"))
  @Test def testLastIndexOfFloatWithStartIndex(): Unit = {
    var array: Array[Float] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toFloat, 2))
    array = new Array[Float](0)
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toFloat, 2))
    array = Array[Float](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0.toFloat, 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toFloat, 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toFloat, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toFloat, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toFloat, -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toFloat))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toFloat, 88))
  }

  @Test def testLastIndexOfInt(): Unit = {
    var array: Array[Int] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0))
    array = Array[Int](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99))
  }

  @Test def testLastIndexOfIntWithStartIndex(): Unit = {
    var array: Array[Int] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0, 2))
    array = Array[Int](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0, 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1, 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0, 88))
  }

  @Test def testLastIndexOfLong(): Unit = {
    var array: Array[Long] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0))
    array = Array[Long](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99))
  }

  @Test def testLastIndexOfLongWithStartIndex(): Unit = {
    var array: Array[Long] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0, 2))
    array = Array[Long](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0, 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1, 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3, -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99, 4))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0, 88))
  }

  @Test def testLastIndexOfShort(): Unit = {
    var array: Array[Short] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toShort))
    array = Array[Short](0, 1, 2, 3, 0)
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toShort))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toShort))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toShort))
    assertEquals(3, ArrayUtils.lastIndexOf(array, 3.toShort))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toShort))
  }

  @Test def testLastIndexOfShortWithStartIndex(): Unit = {
    var array: Array[Short] = null
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 0.toShort, 2))
    array = Array[Short](0, 1, 2, 3, 0)
    assertEquals(0, ArrayUtils.lastIndexOf(array, 0.toShort, 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, 1.toShort, 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, 2.toShort, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toShort, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 3.toShort, -1))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, 99.toShort))
    assertEquals(4, ArrayUtils.lastIndexOf(array, 0.toShort, 88))
  }

  @Test def testLastIndexOfWithStartIndex(): Unit = {
    val array = Array[AnyRef]("0", "1", "2", "3", null, "0")
    assertEquals(-1, ArrayUtils.lastIndexOf(null, null, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(null, "0", 2))
    assertEquals(0, ArrayUtils.lastIndexOf(array, "0", 2))
    assertEquals(1, ArrayUtils.lastIndexOf(array, "1", 2))
    assertEquals(2, ArrayUtils.lastIndexOf(array, "2", 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "3", 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "3", -1))
    assertEquals(4, ArrayUtils.lastIndexOf(array, null, 5))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, null, 2))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, "notInArray", 5))
    assertEquals(-1, ArrayUtils.lastIndexOf(array, null, -1))
    assertEquals(5, ArrayUtils.lastIndexOf(array, "0", 88))
  }

  @Test def testNullToEmptyBoolean(): Unit = {
    val original = Array[Boolean](true, false)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyBooleanEmptyArray(): Unit = {
    val empty = Array[Boolean]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyBooleanNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Boolean]]))
  }

  @Test def testNullToEmptyBooleanObject(): Unit = {
    val original = Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE)
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyBooleanObjectEmptyArray(): Unit = {
    val empty = Array[JavaBoolean]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyBooleanObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[JavaBoolean]]))
  }

  @Test def testNullToEmptyByte(): Unit = {
    val original = Array[Byte](0x0f, 0x0e)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyByteEmptyArray(): Unit = {
    val empty = Array[Byte]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyByteNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Byte]]))
  }

  @Test def testNullToEmptyByteObject(): Unit = {
    val original = Array[JavaByte](JavaByte.valueOf(0x0f.toByte), JavaByte.valueOf(0x0e.toByte))
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyByteObjectEmptyArray(): Unit = {
    val empty: Array[JavaByte] = Array[JavaByte]()
    val result = ArrayUtils.nullToEmpty(empty).asInstanceOf[Array[Object]]
    assertArrayEquals(ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY.asInstanceOf[Array[Object]], result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyByteObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[JavaByte]]))
  }

  @Test def testNullToEmptyChar(): Unit = {
    val original = Array[Char]('a', 'b')
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyCharEmptyArray(): Unit = {
    val empty = Array[Char]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyCharNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Char]]))
  }

  @Test def testNullToEmptyCharObject(): Unit = {
    val original = Array[Character]('a', 'b')
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyCharObjectEmptyArray(): Unit = {
    val empty = Array[Character]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNUllToEmptyCharObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Character]]))
  }

  @Test def testNullToEmptyClass(): Unit = {
    val original: Array[Class[_]] = Array(classOf[Any], classOf[String])
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyClassEmptyArray(): Unit = {
    val empty = Array[Class[_]]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_CLASS_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyClassNull(): Unit = {
    assertArrayEqualsToObject(ArrayUtils.EMPTY_CLASS_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Class[_]]]))
  }

  @Test def testNullToEmptyDouble(): Unit = {
    val original = Array[Double](1L, 2L)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyDoubleEmptyArray(): Unit = {
    val empty = Array[Double]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyDoubleNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Double]]))
  }

  @Test def testNullToEmptyDoubleObject(): Unit = {
    val original = Array[JavaDouble](JavaDouble.valueOf(1d), JavaDouble.valueOf(2d))
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyDoubleObjectEmptyArray(): Unit = {
    val empty = Array[JavaDouble]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyDoubleObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[JavaDouble]]))
  }

  @Test def testNullToEmptyFloat(): Unit = {
    val original = Array[Float](2.6f, 3.8f)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyFloatEmptyArray(): Unit = {
    val empty = Array[Float]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyFloatNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Float]]))
  }

  @Test def testNullToEmptyFloatObject(): Unit = {
    val original = Array[JavaFloat](JavaFloat.valueOf(2.6f), JavaFloat.valueOf(3.8f))
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyFloatObjectEmptyArray(): Unit = {
    val empty = Array[JavaFloat]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyFloatObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[JavaFloat]]))
  }

  @Test def testNullToEmptyGeneric(): Unit = {
    val input = Array[TestClass](new TestClass, new TestClass)
    val output = ArrayUtils.nullToEmpty(input, classOf[Array[TestClass]])
    assertSame(input, output)
  }

  @Test def testNullToEmptyGenericEmpty(): Unit = {
    val input = Array[TestClass]()
    val output = ArrayUtils.nullToEmpty(input, classOf[Array[TestClass]])
    assertSame(input, output)
  }

  @Test def testNullToEmptyGenericNull(): Unit = {
    val output = ArrayUtils.nullToEmpty(null, classOf[Array[TestClass]])
    assertNotNull(output)
    assertEquals(0, output.length)
  }

  @Test def testNullToEmptyGenericNullType(): Unit = {
    val input = Array[TestClass]()
    assertThrows[IllegalArgumentException](ArrayUtils.nullToEmpty(input, null))
    ()
  }

  @Test def testNullToEmptyInt(): Unit = {
    val original = Array[Int](1, 2)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyIntEmptyArray(): Unit = {
    val empty = Array[Int]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_INT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyIntNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Int]]))
  }

  @Test def testNullToEmptyIntObject(): Unit = {
    val original = Array[Integer](Integer.valueOf(1), Integer.valueOf(2))
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyIntObjectEmptyArray(): Unit = {
    val empty = Array[Integer]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyIntObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Integer]]))
  }

  @Test def testNullToEmptyLong(): Unit = {
    val original = Array[Long](1L, 2L)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyLongEmptyArray(): Unit = {
    val empty = Array[Long]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_LONG_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyLongNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Long]]))
  }

  @Test def testNullToEmptyLongObject(): Unit = {
    val original = Array[JavaLong](JavaLong.valueOf(1L), JavaLong.valueOf(2L))
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyLongObjectEmptyArray(): Unit = {
    val empty = Array[JavaLong]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_LONG_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyLongObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_LONG_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[JavaLong]]))
  }

  @Test def testNullToEmptyObject(): Unit = {
    val original = Array[Any](JavaBoolean.TRUE, JavaBoolean.FALSE)
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyObjectEmptyArray(): Unit = {
    val empty: Array[Any] = Array[Any]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyObjectNull(): Unit = {
    assertArrayEqualsToObject(ArrayUtils.EMPTY_OBJECT_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Any]]))
  }

  @Test def testNullToEmptyShort(): Unit = {
    val original = Array[Short](1, 2)
    assertEquals(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyShortEmptyArray(): Unit = {
    val empty = Array[Short]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertEquals(ArrayUtils.EMPTY_SHORT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyShortNull(): Unit = {
    assertEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[Short]]))
  }

  @Test def testNullToEmptyShortObject(): Unit = {
    val original = Array[JavaShort](JavaShort.valueOf(1.toShort), JavaShort.valueOf(2.toShort))
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyShortObjectEmptyArray(): Unit = {
    val empty = Array[JavaShort]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyShortObjectNull(): Unit = {
    assertArrayEqualsToObject(
      ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY,
      ArrayUtils.nullToEmpty(null.asInstanceOf[Array[JavaShort]]))
  }

  @Test def testNullToEmptyString(): Unit = {
    val original = Array[String]("abc", "def")
    assertArrayEqualsToObject(original, ArrayUtils.nullToEmpty(original))
  }

  @Test def testNullToEmptyStringEmptyArray(): Unit = {
    val empty = Array[String]()
    val result = ArrayUtils.nullToEmpty(empty)
    assertArrayEqualsToObject(ArrayUtils.EMPTY_STRING_ARRAY, result)
    assertNotSame(empty, result)
  }

  @Test def testNullToEmptyStringNull(): Unit = {
    assertArrayEqualsToObject(ArrayUtils.EMPTY_STRING_ARRAY, ArrayUtils.nullToEmpty(null.asInstanceOf[Array[String]]))
  }

  @Test def testReverse(): Unit = {
    val str1 = new StringBuffer("pick")
    val str2 = "a"
    val str3 = Array[String]("stick")
    val str4 = "up"
    var array = Array[AnyRef](str1, str2, str3)
    ArrayUtils.reverse(array)
    assertEquals(array(0), str3)
    assertEquals(array(1), str2)
    assertEquals(array(2), str1)
    array = Array[AnyRef](str1, str2, str3, str4)
    ArrayUtils.reverse(array)
    assertEquals(array(0), str4)
    assertEquals(array(1), str3)
    assertEquals(array(2), str2)
    assertEquals(array(3), str1)
    array = null
    ArrayUtils.reverse(array)
    assertArrayEquals(null, array)
  }

  @Test def testReverseBoolean(): Unit = {
    var array = Array[Boolean](false, false, true)
    ArrayUtils.reverse(array)
    assertTrue(array(0))
    assertFalse(array(1))
    assertFalse(array(2))
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseBooleanRange(): Unit = {
    var array = Array[Boolean](false, false, true)
    // The whole array
    ArrayUtils.reverse(array, 0, 3)
    assertTrue(array(0))
    assertFalse(array(1))
    assertFalse(array(2))
    // a range
    array = Array[Boolean](false, false, true)
    ArrayUtils.reverse(array, 0, 2)
    assertFalse(array(0))
    assertFalse(array(1))
    assertTrue(array(2))
    // a range with a negative start
    array = Array[Boolean](false, false, true)
    ArrayUtils.reverse(array, -1, 3)
    assertTrue(array(0))
    assertFalse(array(1))
    assertFalse(array(2))
    // a range with a large stop index
    array = Array[Boolean](false, false, true)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertTrue(array(0))
    assertFalse(array(1))
    assertFalse(array(2))
    // null
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseByte(): Unit = {
    var array = Array[Byte](2.toByte, 3.toByte, 4.toByte)
    ArrayUtils.reverse(array)
    assertEquals(array(0), 4.toByte)
    assertEquals(array(1), 3.toByte)
    assertEquals(array(2), 2.toByte)
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseByteRange(): Unit = {
    var array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(1.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2.toByte, array(0))
    assertEquals(1.toByte, array(1))
    assertEquals(3.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(1.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(1.toByte, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseChar(): Unit = {
    var array = Array[Char]('a', 'f', 'C')
    ArrayUtils.reverse(array)
    assertEquals(array(0), 'C')
    assertEquals(array(1), 'f')
    assertEquals(array(2), 'a')
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseCharRange(): Unit = {
    var array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(1.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2.toChar, array(0))
    assertEquals(1.toChar, array(1))
    assertEquals(3.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(1.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(1.toChar, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseDouble(): Unit = {
    var array = Array[Double](0.3d, 0.4d, 0.5d)
    ArrayUtils.reverse(array)
    assertEquals(0.5d, array(0))
    assertEquals(0.4d, array(1))
    assertEquals(0.3d, array(2))
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseDoubleRange(): Unit = {
    var array = Array[Double](1d, 2d, 3d)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3d, array(0))
    assertEquals(2d, array(1))
    assertEquals(1d, array(2))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2d, array(0))
    assertEquals(1d, array(1))
    assertEquals(3d, array(2))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3d, array(0))
    assertEquals(2d, array(1))
    assertEquals(1d, array(2))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3d, array(0))
    assertEquals(2d, array(1))
    assertEquals(1d, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseFloat(): Unit = {
    var array = Array[Float](0.3f, 0.4f, 0.5f)
    ArrayUtils.reverse(array)
    assertEquals(0.5f, array(0))
    assertEquals(0.4f, array(1))
    assertEquals(0.3f, array(2))
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseFloatRange(): Unit = {
    var array = Array[Float](1f, 2f, 3f)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3f, array(0))
    assertEquals(2f, array(1))
    assertEquals(1f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2f, array(0))
    assertEquals(1f, array(1))
    assertEquals(3f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3f, array(0))
    assertEquals(2f, array(1))
    assertEquals(1f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3f, array(0))
    assertEquals(2f, array(1))
    assertEquals(1f, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseInt(): Unit = {
    var array = Array[Int](1, 2, 3)
    ArrayUtils.reverse(array)
    assertEquals(array(0), 3)
    assertEquals(array(1), 2)
    assertEquals(array(2), 1)
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseIntRange(): Unit = {
    var array = Array[Int](1, 2, 3)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2, array(0))
    assertEquals(1, array(1))
    assertEquals(3, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseLong(): Unit = {
    var array = Array[Long](1L, 2L, 3L)
    ArrayUtils.reverse(array)
    assertEquals(array(0), 3L)
    assertEquals(array(1), 2L)
    assertEquals(array(2), 1L)
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseLongRange(): Unit = {
    var array = Array[Long](1L, 2L, 3L)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3L, array(0))
    assertEquals(2L, array(1))
    assertEquals(1L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2L, array(0))
    assertEquals(1L, array(1))
    assertEquals(3L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3L, array(0))
    assertEquals(2L, array(1))
    assertEquals(1L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3L, array(0))
    assertEquals(2L, array(1))
    assertEquals(1L, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseObjectRange(): Unit = {
    var array = Array[String]("1", "2", "3")
    ArrayUtils.reverse(array, 0, 3)
    assertEquals("3", array(0))
    assertEquals("2", array(1))
    assertEquals("1", array(2))
    array = Array[String]("1", "2", "3")
    ArrayUtils.reverse(array, 0, 2)
    assertEquals("2", array(0))
    assertEquals("1", array(1))
    assertEquals("3", array(2))
    array = Array[String]("1", "2", "3")
    ArrayUtils.reverse(array, -1, 3)
    assertEquals("3", array(0))
    assertEquals("2", array(1))
    assertEquals("1", array(2))
    array = Array[String]("1", "2", "3")
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals("3", array(0))
    assertEquals("2", array(1))
    assertEquals("1", array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testReverseShort(): Unit = {
    var array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.reverse(array)
    assertEquals(array(0), 3.toShort)
    assertEquals(array(1), 2.toShort)
    assertEquals(array(2), 1.toShort)
    array = null
    ArrayUtils.reverse(array)
    assertNull(array)
  }

  @Test def testReverseShortRange(): Unit = {
    var array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.reverse(array, 0, 3)
    assertEquals(3.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(1.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.reverse(array, 0, 2)
    assertEquals(2.toShort, array(0))
    assertEquals(1.toShort, array(1))
    assertEquals(3.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.reverse(array, -1, 3)
    assertEquals(3.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(1.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.reverse(array, -1, array.length + 1000)
    assertEquals(3.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(1.toShort, array(2))
    array = null
    ArrayUtils.reverse(array, 0, 3)
    assertNull(array)
  }

  @Test def testSameLength(): Unit = {
    val nullArray: Array[AnyRef] = null
    val emptyArray = new Array[AnyRef](0)
    val oneArray = Array[AnyRef]("pick")
    val twoArray = Array[AnyRef]("pick", "stick")
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthAll(): Unit = {
    val nullArrayObject: Array[AnyRef] = null
    val emptyArrayObject = new Array[AnyRef](0)
    val oneArrayObject = Array[AnyRef]("pick")
    val twoArrayObject = Array[AnyRef]("pick", "stick")
    val nullArrayBoolean: Array[Boolean] = null
    val emptyArrayBoolean = new Array[Boolean](0)
    val oneArrayBoolean = Array[Boolean](true)
    val twoArrayBoolean = Array[Boolean](true, false)
    val nullArrayLong: Array[Long] = null
    val emptyArrayLong = new Array[Long](0)
    val oneArrayLong = Array[Long](0L)
    val twoArrayLong = Array[Long](0L, 76L)
    val nullArrayInt: Array[Int] = null
    val emptyArrayInt = new Array[Int](0)
    val oneArrayInt = Array[Int](4)
    val twoArrayInt = Array[Int](5, 7)
    val nullArrayShort: Array[Short] = null
    val emptyArrayShort = new Array[Short](0)
    val oneArrayShort = Array[Short](4.toShort)
    val twoArrayShort = Array[Short](6.toShort, 8.toShort)
    val nullArrayChar: Array[Char] = null
    val emptyArrayChar = new Array[Char](0)
    val oneArrayChar = Array[Char]('f')
    val twoArrayChar = Array[Char]('d', 't')
    val nullArrayByte: Array[Byte] = null
    val emptyArrayByte = new Array[Byte](0)
    val oneArrayByte = Array[Byte](3.toByte)
    val twoArrayByte = Array[Byte](4.toByte, 6.toByte)
    val nullArrayDouble: Array[Double] = null
    val emptyArrayDouble = new Array[Double](0)
    val oneArrayDouble = Array[Double](1.3d)
    val twoArrayDouble = Array[Double](4.5d, 6.3d)
    val nullArrayFloat: Array[Float] = null
    val emptyArrayFloat = new Array[Float](0)
    val oneArrayFloat = Array[Float](2.5f)
    val twoArrayFloat = Array[Float](6.4f, 5.8f)
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayObject, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayBoolean, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayLong, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayInt, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayShort, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayChar, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayByte, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayDouble, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(nullArrayFloat, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayObject, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayBoolean, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayLong, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayInt, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayShort, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayChar, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayByte, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayDouble, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(nullArrayFloat, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, nullArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayObject, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayBoolean, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayLong, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayInt, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayShort, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayChar, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayByte, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayDouble, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayObject))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayLong))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayInt))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayShort))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayChar))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayByte))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayDouble))
    assertTrue(ArrayUtils.isSameLength(emptyArrayFloat, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayObject, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayBoolean, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayLong, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayInt, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayShort, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayChar, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayByte, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayDouble, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(emptyArrayFloat, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, emptyArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayObject, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayBoolean, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayLong, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayInt, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayShort, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayChar, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayByte, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayDouble, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayObject))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayLong))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayInt))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayShort))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayChar))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayByte))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayDouble))
    assertTrue(ArrayUtils.isSameLength(oneArrayFloat, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayObject, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayBoolean, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayLong, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayInt, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayShort, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayChar, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayByte, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayDouble, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayObject))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayLong))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayInt))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayShort))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayChar))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayByte))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayDouble))
    assertFalse(ArrayUtils.isSameLength(oneArrayFloat, twoArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, nullArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, emptyArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayObject, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayBoolean, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayLong, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayInt, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayShort, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayChar, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayByte, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayDouble, oneArrayFloat))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayObject))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayBoolean))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayLong))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayInt))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayShort))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayChar))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayByte))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayDouble))
    assertFalse(ArrayUtils.isSameLength(twoArrayFloat, oneArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayObject, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayBoolean, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayLong, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayInt, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayShort, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayChar, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayByte, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayDouble, twoArrayFloat))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayObject))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayBoolean))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayLong))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayInt))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayShort))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayChar))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayByte))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayDouble))
    assertTrue(ArrayUtils.isSameLength(twoArrayFloat, twoArrayFloat))
  }

  @Test def testSameLengthBoolean(): Unit = {
    val nullArray: Array[Boolean] = null
    val emptyArray = new Array[Boolean](0)
    val oneArray = Array[Boolean](true)
    val twoArray = Array[Boolean](true, false)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthByte(): Unit = {
    val nullArray: Array[Byte] = null
    val emptyArray = new Array[Byte](0)
    val oneArray = Array[Byte](3)
    val twoArray = Array[Byte](4, 6)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthChar(): Unit = {
    val nullArray: Array[Char] = null
    val emptyArray = new Array[Char](0)
    val oneArray = Array[Char]('f')
    val twoArray = Array[Char]('d', 't')
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthDouble(): Unit = {
    val nullArray: Array[Double] = null
    val emptyArray = new Array[Double](0)
    val oneArray = Array[Double](1.3d)
    val twoArray = Array[Double](4.5d, 6.3d)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthFloat(): Unit = {
    val nullArray: Array[Float] = null
    val emptyArray = new Array[Float](0)
    val oneArray = Array[Float](2.5f)
    val twoArray = Array[Float](6.4f, 5.8f)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthInt(): Unit = {
    val nullArray: Array[Int] = null
    val emptyArray = new Array[Int](0)
    val oneArray = Array[Int](4)
    val twoArray = Array[Int](5, 7)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthLong(): Unit = {
    val nullArray: Array[Long] = null
    val emptyArray = new Array[Long](0)
    val oneArray = Array[Long](0L)
    val twoArray = Array[Long](0L, 76L)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameLengthShort(): Unit = {
    val nullArray: Array[Short] = null
    val emptyArray = new Array[Short](0)
    val oneArray = Array[Short](4)
    val twoArray = Array[Short](6, 8)
    assertTrue(ArrayUtils.isSameLength(nullArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(nullArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(nullArray, twoArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, nullArray))
    assertTrue(ArrayUtils.isSameLength(emptyArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(emptyArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, emptyArray))
    assertTrue(ArrayUtils.isSameLength(oneArray, oneArray))
    assertFalse(ArrayUtils.isSameLength(oneArray, twoArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, nullArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, emptyArray))
    assertFalse(ArrayUtils.isSameLength(twoArray, oneArray))
    assertTrue(ArrayUtils.isSameLength(twoArray, twoArray))
  }

  @Test def testSameType(): Unit = {
    assertThrows[IllegalArgumentException](ArrayUtils.isSameType(null, null))
    assertThrows[IllegalArgumentException](ArrayUtils.isSameType(null, new Array[AnyRef](0)))
    assertThrows[IllegalArgumentException](ArrayUtils.isSameType(new Array[AnyRef](0), null))
    assertTrue(ArrayUtils.isSameType(new Array[AnyRef](0), new Array[AnyRef](0)))
    assertFalse(ArrayUtils.isSameType(new Array[String](0), new Array[AnyRef](0)))
    assertTrue(ArrayUtils.isSameType(Array.ofDim[String](0, 0), Array.ofDim[String](0, 0)))
    assertFalse(ArrayUtils.isSameType(new Array[String](0), Array.ofDim[String](0, 0)))
    assertFalse(ArrayUtils.isSameType(Array.ofDim[String](0, 0), new Array[String](0)))
  }

  @Test def testShiftAllByte(): Unit = {
    val array = Array[Byte](1.toByte, 2.toByte, 3.toByte, 4.toByte)
    ArrayUtils.shift(array, 4)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
    assertEquals(4.toByte, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
    assertEquals(4.toByte, array(3))
  }

  @Test def testShiftAllChar(): Unit = {
    val array = Array[Char](1.toChar, 2.toChar, 3.toChar, 4.toChar)
    ArrayUtils.shift(array, 4)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
    assertEquals(4.toChar, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
    assertEquals(4.toChar, array(3))
  }

  @Test def testShiftAllDouble(): Unit = {
    val array = Array[Double](1d, 2d, 3d, 4d)
    ArrayUtils.shift(array, 4)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
    assertEquals(4d, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
    assertEquals(4d, array(3))
  }

  @Test def testShiftAllFloat(): Unit = {
    val array = Array[Float](1f, 2f, 3f, 4f)
    ArrayUtils.shift(array, 4)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
    assertEquals(4f, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
    assertEquals(4f, array(3))
  }

  @Test def testShiftAllInt(): Unit = {
    val array = Array[Int](1, 2, 3, 4)
    ArrayUtils.shift(array, 4)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
    assertEquals(4, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
    assertEquals(4, array(3))
  }

  @Test def testShiftAllLong(): Unit = {
    val array = Array[Long](1L, 2L, 3L, 4L)
    ArrayUtils.shift(array, 4)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
    assertEquals(4L, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
    assertEquals(4L, array(3))
  }

  @Test def testShiftAllObject(): Unit = {
    val array = Array[String]("1", "2", "3", "4")
    ArrayUtils.shift(array, 4)
    assertEquals("1", array(0))
    assertEquals("2", array(1))
    assertEquals("3", array(2))
    assertEquals("4", array(3))
    ArrayUtils.shift(array, -4)
    assertEquals("1", array(0))
    assertEquals("2", array(1))
    assertEquals("3", array(2))
    assertEquals("4", array(3))
  }

  @Test def testShiftAllShort(): Unit = {
    val array = Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort)
    ArrayUtils.shift(array, 4)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
    assertEquals(4.toShort, array(3))
    ArrayUtils.shift(array, -4)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
    assertEquals(4.toShort, array(3))
  }

  @Test def testShiftBoolean(): Unit = {
    val array = Array[Boolean](true, true, false, false)
    ArrayUtils.shift(array, 1)
    assertFalse(array(0))
    assertTrue(array(1))
    assertTrue(array(2))
    assertFalse(array(3))
    ArrayUtils.shift(array, -1)
    assertTrue(array(0))
    assertTrue(array(1))
    assertFalse(array(2))
    assertFalse(array(3))
    ArrayUtils.shift(array, 5)
    assertFalse(array(0))
    assertTrue(array(1))
    assertTrue(array(2))
    assertFalse(array(3))
    ArrayUtils.shift(array, -3)
    assertFalse(array(0))
    assertFalse(array(1))
    assertTrue(array(2))
    assertTrue(array(3))
  }

  @Test def testShiftByte(): Unit = {
    val array = Array[Byte](1.toByte, 2.toByte, 3.toByte, 4.toByte)
    ArrayUtils.shift(array, 1)
    assertEquals(4.toByte, array(0))
    assertEquals(1.toByte, array(1))
    assertEquals(2.toByte, array(2))
    assertEquals(3.toByte, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
    assertEquals(4.toByte, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4.toByte, array(0))
    assertEquals(1.toByte, array(1))
    assertEquals(2.toByte, array(2))
    assertEquals(3.toByte, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3.toByte, array(0))
    assertEquals(4.toByte, array(1))
    assertEquals(1.toByte, array(2))
    assertEquals(2.toByte, array(3))
  }

  @Test def testShiftChar(): Unit = {
    val array = Array[Char](1.toChar, 2.toChar, 3.toChar, 4.toChar)
    ArrayUtils.shift(array, 1)
    assertEquals(4.toChar, array(0))
    assertEquals(1.toChar, array(1))
    assertEquals(2.toChar, array(2))
    assertEquals(3.toChar, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
    assertEquals(4.toChar, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4.toChar, array(0))
    assertEquals(1.toChar, array(1))
    assertEquals(2.toChar, array(2))
    assertEquals(3.toChar, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3.toChar, array(0))
    assertEquals(4.toChar, array(1))
    assertEquals(1.toChar, array(2))
    assertEquals(2.toChar, array(3))
  }

  @Test def testShiftDouble(): Unit = {
    val array = Array[Double](1d, 2d, 3d, 4d)
    ArrayUtils.shift(array, 1)
    assertEquals(4d, array(0))
    assertEquals(1d, array(1))
    assertEquals(2d, array(2))
    assertEquals(3d, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
    assertEquals(4d, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4d, array(0))
    assertEquals(1d, array(1))
    assertEquals(2d, array(2))
    assertEquals(3d, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3d, array(0))
    assertEquals(4d, array(1))
    assertEquals(1d, array(2))
    assertEquals(2d, array(3))
  }

  @Test def testShiftFloat(): Unit = {
    val array = Array[Float](1f, 2f, 3f, 4f)
    ArrayUtils.shift(array, 1)
    assertEquals(4f, array(0))
    assertEquals(1f, array(1))
    assertEquals(2f, array(2))
    assertEquals(3f, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
    assertEquals(4f, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4f, array(0))
    assertEquals(1f, array(1))
    assertEquals(2f, array(2))
    assertEquals(3f, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3f, array(0))
    assertEquals(4f, array(1))
    assertEquals(1f, array(2))
    assertEquals(2f, array(3))
  }

  @Test def testShiftInt(): Unit = {
    val array = Array[Int](1, 2, 3, 4)
    ArrayUtils.shift(array, 1)
    assertEquals(4, array(0))
    assertEquals(1, array(1))
    assertEquals(2, array(2))
    assertEquals(3, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
    assertEquals(4, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4, array(0))
    assertEquals(1, array(1))
    assertEquals(2, array(2))
    assertEquals(3, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3, array(0))
    assertEquals(4, array(1))
    assertEquals(1, array(2))
    assertEquals(2, array(3))
  }

  @Test def testShiftLong(): Unit = {
    val array = Array[Long](1L, 2L, 3L, 4L)
    ArrayUtils.shift(array, 1)
    assertEquals(4L, array(0))
    assertEquals(1L, array(1))
    assertEquals(2L, array(2))
    assertEquals(3L, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
    assertEquals(4L, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4L, array(0))
    assertEquals(1L, array(1))
    assertEquals(2L, array(2))
    assertEquals(3L, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3L, array(0))
    assertEquals(4L, array(1))
    assertEquals(1L, array(2))
    assertEquals(2L, array(3))
  }

  @Test def testShiftNullBoolean(): Unit = {
    val array: Array[Boolean] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftNullDouble(): Unit = {
    val array: Array[Double] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftNullFloat(): Unit = {
    val array: Array[Float] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftNullInt(): Unit = {
    val array: Array[Int] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftNullLong(): Unit = {
    val array: Array[Long] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftNullObject(): Unit = {
    val array: Array[Any] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftNullShort(): Unit = {
    val array: Array[Short] = null
    ArrayUtils.shift(array, 1)
    assertNull(array)
  }

  @Test def testShiftObject(): Unit = {
    val array = Array[String]("1", "2", "3", "4")
    ArrayUtils.shift(array, 1)
    assertEquals("4", array(0))
    assertEquals("1", array(1))
    assertEquals("2", array(2))
    assertEquals("3", array(3))
    ArrayUtils.shift(array, -1)
    assertEquals("1", array(0))
    assertEquals("2", array(1))
    assertEquals("3", array(2))
    assertEquals("4", array(3))
    ArrayUtils.shift(array, 5)
    assertEquals("4", array(0))
    assertEquals("1", array(1))
    assertEquals("2", array(2))
    assertEquals("3", array(3))
    ArrayUtils.shift(array, -3)
    assertEquals("3", array(0))
    assertEquals("4", array(1))
    assertEquals("1", array(2))
    assertEquals("2", array(3))
  }

  @Test def testShiftRangeByte(): Unit = {
    val array = Array[Byte](1.toByte, 2.toByte, 3.toByte, 4.toByte, 5.toByte)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1.toByte, array(0))
    assertEquals(3.toByte, array(1))
    assertEquals(2.toByte, array(2))
    assertEquals(4.toByte, array(3))
    assertEquals(5.toByte, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(4.toByte, array(2))
    assertEquals(3.toByte, array(3))
    assertEquals(5.toByte, array(4))
  }

  @Test def testShiftRangeChar(): Unit = {
    val array = Array[Char](1.toChar, 2.toChar, 3.toChar, 4.toChar, 5.toChar)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1.toChar, array(0))
    assertEquals(3.toChar, array(1))
    assertEquals(2.toChar, array(2))
    assertEquals(4.toChar, array(3))
    assertEquals(5.toChar, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(4.toChar, array(2))
    assertEquals(3.toChar, array(3))
    assertEquals(5.toChar, array(4))
  }

  @Test def testShiftRangeDouble(): Unit = {
    val array = Array[Double](1d, 2d, 3d, 4d, 5d)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1d, array(0))
    assertEquals(3d, array(1))
    assertEquals(2d, array(2))
    assertEquals(4d, array(3))
    assertEquals(5d, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(4d, array(2))
    assertEquals(3d, array(3))
    assertEquals(5d, array(4))
  }

  @Test def testShiftRangeFloat(): Unit = {
    val array = Array[Float](1f, 2f, 3f, 4f, 5f)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1f, array(0))
    assertEquals(3f, array(1))
    assertEquals(2f, array(2))
    assertEquals(4f, array(3))
    assertEquals(5f, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(4f, array(2))
    assertEquals(3f, array(3))
    assertEquals(5f, array(4))
  }

  @Test def testShiftRangeInt(): Unit = {
    val array = Array[Int](1, 2, 3, 4, 5)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1, array(0))
    assertEquals(3, array(1))
    assertEquals(2, array(2))
    assertEquals(4, array(3))
    assertEquals(5, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(4, array(2))
    assertEquals(3, array(3))
    assertEquals(5, array(4))
  }

  @Test def testShiftRangeLong(): Unit = {
    val array = Array[Long](1L, 2L, 3L, 4L, 5L)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1L, array(0))
    assertEquals(3L, array(1))
    assertEquals(2L, array(2))
    assertEquals(4L, array(3))
    assertEquals(5L, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(4L, array(2))
    assertEquals(3L, array(3))
    assertEquals(5L, array(4))
  }

  @Test def testShiftRangeNoElemByte(): Unit = {
    val array = Array[Byte](1.toByte, 2.toByte, 3.toByte, 4.toByte)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
    assertEquals(4.toByte, array(3))
  }

  @Test def testShiftRangeNoElemChar(): Unit = {
    val array = Array[Char](1.toChar, 2.toChar, 3.toChar, 4.toChar)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
    assertEquals(4.toChar, array(3))
  }

  @Test def testShiftRangeNoElemDouble(): Unit = {
    val array = Array[Double](1d, 2d, 3d, 4d)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
    assertEquals(4d, array(3))
  }

  @Test def testShiftRangeNoElemFloat(): Unit = {
    val array = Array[Float](1f, 2f, 3f, 4f)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
    assertEquals(4f, array(3))
  }

  @Test def testShiftRangeNoElemInt(): Unit = {
    val array = Array[Int](1, 2, 3, 4)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
    assertEquals(4, array(3))
  }

  @Test def testShiftRangeNoElemLong(): Unit = {
    val array = Array[Long](1L, 2L, 3L, 4L)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
    assertEquals(4L, array(3))
  }

  @Test def testShiftRangeNoElemObject(): Unit = {
    val array = Array[String]("1", "2", "3", "4")
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals("1", array(0))
    assertEquals("2", array(1))
    assertEquals("3", array(2))
    assertEquals("4", array(3))
  }

  @Test def testShiftRangeNoElemShort(): Unit = {
    val array = Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort)
    ArrayUtils.shift(array, 1, 1, 1)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
    assertEquals(4.toShort, array(3))
  }

  @Test def testShiftRangeNullByte(): Unit = {
    val array: Array[Byte] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullChar(): Unit = {
    val array: Array[Char] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullDouble(): Unit = {
    val array: Array[Double] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullFloat(): Unit = {
    val array: Array[Float] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullInt(): Unit = {
    val array: Array[Int] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullLong(): Unit = {
    val array: Array[Long] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullObject(): Unit = {
    val array: Array[Any] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeNullShort(): Unit = {
    val array: Array[Short] = null
    ArrayUtils.shift(array, 1, 1, 1)
    assertNull(array)
  }

  @Test def testShiftRangeObject(): Unit = {
    val array = Array[String]("1", "2", "3", "4", "5")
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals("1", array(0))
    assertEquals("3", array(1))
    assertEquals("2", array(2))
    assertEquals("4", array(3))
    assertEquals("5", array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals("1", array(0))
    assertEquals("2", array(1))
    assertEquals("4", array(2))
    assertEquals("3", array(3))
    assertEquals("5", array(4))
  }

  @Test def testShiftRangeShort(): Unit = {
    val array = Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort, 5.toShort)
    ArrayUtils.shift(array, 1, 3, 1)
    assertEquals(1.toShort, array(0))
    assertEquals(3.toShort, array(1))
    assertEquals(2.toShort, array(2))
    assertEquals(4.toShort, array(3))
    assertEquals(5.toShort, array(4))
    ArrayUtils.shift(array, 1, 4, 2)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(4.toShort, array(2))
    assertEquals(3.toShort, array(3))
    assertEquals(5.toShort, array(4))
  }

  @Test def testShiftShort(): Unit = {
    var array = Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort)
    ArrayUtils.shift(array, 1)
    assertEquals(4.toShort, array(0))
    assertEquals(1.toShort, array(1))
    assertEquals(2.toShort, array(2))
    assertEquals(3.toShort, array(3))
    ArrayUtils.shift(array, -1)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
    assertEquals(4.toShort, array(3))
    ArrayUtils.shift(array, 5)
    assertEquals(4.toShort, array(0))
    assertEquals(1.toShort, array(1))
    assertEquals(2.toShort, array(2))
    assertEquals(3.toShort, array(3))
    ArrayUtils.shift(array, -3)
    assertEquals(3.toShort, array(0))
    assertEquals(4.toShort, array(1))
    assertEquals(1.toShort, array(2))
    assertEquals(2.toShort, array(3))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort, 5.toShort)
    ArrayUtils.shift(array, 2)
    assertEquals(4.toShort, array(0))
    assertEquals(5.toShort, array(1))
    assertEquals(1.toShort, array(2))
    assertEquals(2.toShort, array(3))
    assertEquals(3.toShort, array(4))
  }

  @Test def testShuffle(): Unit = {
    val array1 = Array[String]("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1.asInstanceOf[Array[Object]], array2.asInstanceOf[Array[Object]]))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleBoolean(): Unit = {
    val array1 = Array[Boolean](true, false, true, true, false, false, true, false, false, true)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    assertEquals(5, ArrayUtils.removeAllOccurrences(array1, true).length)
  }

  @Test def testShuffleByte(): Unit = {
    val array1 =
      Array[Byte](1.toByte, 2.toByte, 3.toByte, 4.toByte, 5.toByte, 6.toByte, 7.toByte, 8.toByte, 9.toByte, 10.toByte)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleChar(): Unit = {
    val array1 =
      Array[Char](1.toChar, 2.toChar, 3.toChar, 4.toChar, 5.toChar, 6.toChar, 7.toChar, 8.toChar, 9.toChar, 10.toChar)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleDouble(): Unit = {
    val array1 = Array[Double](1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleFloat(): Unit = {
    val array1 = Array[Float](1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleInt(): Unit = {
    val array1 = Array[Int](1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleLong(): Unit = {
    val array1 = Array[Long](1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testShuffleShort(): Unit = {
    val array1 = Array[Short](
      1.toShort,
      2.toShort,
      3.toShort,
      4.toShort,
      5.toShort,
      6.toShort,
      7.toShort,
      8.toShort,
      9.toShort,
      10.toShort)
    val array2 = ArrayUtils.clone(array1)
    ArrayUtils.shuffle(array1, new Random(ArrayUtilsTest.SEED))
    assertFalse(util.Arrays.equals(array1, array2))
    for (element <- array2) {
      assertTrue(ArrayUtils.contains(array1, element), "Element " + element + " not found")
    }
  }

  @Test def testSubarrayBoolean(): Unit = {
    val nullArray: Array[Boolean] = null
    val array = Array(true, true, false, true, false, true)
    val leftSubarray = Array(true, true, false, true)
    val midSubarray = Array(true, false, true, false)
    val rightSubarray = Array(false, true, false, true)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(
      ArrayUtils.EMPTY_BOOLEAN_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_BOOLEAN_ARRAY, 1, 2),
      "empty array")
    assertEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    // empty-return tests
    assertSame(
      ArrayUtils.EMPTY_BOOLEAN_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_BOOLEAN_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_BOOLEAN_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    // array type tests
    assertSame(classOf[Boolean], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "boolean type")
  }

  @Test def testSubarrayByte(): Unit = {
    val nullArray: Array[Byte] = null
    val array = Array(10.toByte, 11.toByte, 12.toByte, 13.toByte, 14.toByte, 15.toByte)
    val leftSubarray = Array(10.toByte, 11.toByte, 12.toByte, 13.toByte)
    val midSubarray = Array(11.toByte, 12.toByte, 13.toByte, 14.toByte)
    val rightSubarray = Array(12.toByte, 13.toByte, 14.toByte, 15.toByte)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_BYTE_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_BYTE_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_BYTE_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_BYTE_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    assertSame(classOf[Byte], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "byte type")
  }

  @Test def testSubarrayDouble(): Unit = {
    val nullArray: Array[Double] = null
    val array = Array(10.123d, 11.234d, 12.345d, 13.456d, 14.567d, 15.678d)
    val leftSubarray = Array(10.123d, 11.234d, 12.345d, 13.456d)
    val midSubarray = Array(11.234d, 12.345d, 13.456d, 14.567d)
    val rightSubarray = Array(12.345d, 13.456d, 14.567d, 15.678d)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_DOUBLE_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_DOUBLE_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_DOUBLE_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_DOUBLE_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    assertSame(classOf[Double], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "double type")
  }

  @Test def testSubarrayFloat(): Unit = {
    val nullArray: Array[Float] = null
    val array = Array(10f, 11f, 12f, 13f, 14f, 15f)
    val leftSubarray = Array(10f, 11f, 12f, 13f)
    val midSubarray = Array(11f, 12, 13f, 14f)
    val rightSubarray = Array(12f, 13, 14f, 15f)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_FLOAT_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_FLOAT_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_FLOAT_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_FLOAT_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    assertSame(classOf[Float], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "float type")
  }

  @Test def testSubarrayInt(): Unit = {
    val nullArray: Array[Int] = null
    val array = Array(10, 11, 12, 13, 14, 15)
    val leftSubarray = Array(10, 11, 12, 13)
    val midSubarray = Array(11, 12, 13, 14)
    val rightSubarray = Array(12, 13, 14, 15)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_INT_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_INT_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_INT_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.subarray(array, 8733, 4), "start overshoot, any end, object test")
    assertSame(classOf[Int], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "int type")
  }

  @Test def testSubarrayLong(): Unit = {
    val nullArray: Array[Long] = null
    val array = Array(999910L, 999911L, 999912L, 999913L, 999914L, 999915L)
    val leftSubarray = Array(999910L, 999911L, 999912L, 999913L)
    val midSubarray = Array(999911L, 999912L, 999913L, 999914L)
    val rightSubarray = Array(999912L, 999913L, 999914L, 999915L)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_LONG_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_LONG_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_LONG_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_LONG_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    assertSame(classOf[Long], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "long type")
  }

  @Test def testSubarrayObject(): Unit = {
    val nullArray: Array[Any] = null
    val objectArray: Array[Object] = Array("a", "b", "c", "d", "e", "f")

    assertEquals("abcd", StringUtils.join(ArrayUtils.subarray(objectArray, 0, 4)), "0 start, mid end")
    assertEquals(
      "abcdef",
      StringUtils.join(ArrayUtils.subarray(objectArray, 0, objectArray.length)),
      "0 start, length end")
    assertEquals("bcd", StringUtils.join(ArrayUtils.subarray(objectArray, 1, 4)), "mid start, mid end")
    assertEquals(
      "bcdef",
      StringUtils.join(ArrayUtils.subarray(objectArray, 1, objectArray.length)),
      "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals("", StringUtils.join(ArrayUtils.subarray(ArrayUtils.EMPTY_OBJECT_ARRAY, 1, 2)), "empty array")
    assertEquals("", StringUtils.join(ArrayUtils.subarray(objectArray, 4, 2)), "start > end")
    assertEquals("", StringUtils.join(ArrayUtils.subarray(objectArray, 3, 3)), "start == end")
    assertEquals("abcd", StringUtils.join(ArrayUtils.subarray(objectArray, -2, 4)), "start undershoot, normal end")
    assertEquals("", StringUtils.join(ArrayUtils.subarray(objectArray, 33, 4)), "start overshoot, any end")
    assertEquals("cdef", StringUtils.join(ArrayUtils.subarray(objectArray, 2, 33)), "normal start, end overshoot")
    assertEquals(
      "abcdef",
      StringUtils.join(ArrayUtils.subarray(objectArray, -2, 12)),
      "start undershoot, end overshoot")
    val dateArray = Array(new Date(new Date().getTime), new Date, new Date, new Date, new Date)
    assertSame(classOf[AnyRef], ArrayUtils.subarray(objectArray, 2, 4).getClass.getComponentType, "Object type")
    assertSame(
      classOf[java.util.Date],
      ArrayUtils.subarray(dateArray, 1, 4).getClass.getComponentType,
      "java.util.Date type")
    assertNotSame(
      classOf[java.sql.Date],
      ArrayUtils.subarray(dateArray, 1, 4).getClass.getComponentType,
      "java.sql.Date type")
    assertThrows[ClassCastException](
      classOf[Array[java.sql.Date]].cast(ArrayUtils.subarray(dateArray, 1, 3))
    ) //, "Invalid downcast")
    ()
  }

  @Test def testSubarrayShort(): Unit = {
    val nullArray: Array[Short] = null
    val array = Array(10.toShort, 11.toShort, 12.toShort, 13.toShort, 14.toShort, 15.toShort)
    val leftSubarray = Array(10.toShort, 11.toShort, 12.toShort, 13.toShort)
    val midSubarray = Array(11.toShort, 12.toShort, 13.toShort, 14.toShort)
    val rightSubarray = Array(12.toShort, 13.toShort, 14.toShort, 15.toShort)
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_SHORT_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_SHORT_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_SHORT_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_SHORT_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    assertSame(classOf[Short], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "short type")
  }

  @Test def testSubarrChar(): Unit = {
    val nullArray: Array[Char] = null
    val array = Array('a', 'b', 'c', 'd', 'e', 'f')
    val leftSubarray = Array('a', 'b', 'c', 'd')
    val midSubarray = Array('b', 'c', 'd', 'e')
    val rightSubarray = Array('c', 'd', 'e', 'f')
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, 0, 4)), "0 start, mid end")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, 0, array.length)), "0 start, length end")
    assertTrue(ArrayUtils.isEquals(midSubarray, ArrayUtils.subarray(array, 1, 5)), "mid start, mid end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, array.length)), "mid start, length end")
    assertNull(ArrayUtils.subarray(nullArray, 0, 3), "null input")
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.subarray(ArrayUtils.EMPTY_CHAR_ARRAY, 1, 2), "empty array")
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.subarray(array, 4, 2), "start > end")
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end")
    assertTrue(ArrayUtils.isEquals(leftSubarray, ArrayUtils.subarray(array, -2, 4)), "start undershoot, normal end")
    assertEquals(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.subarray(array, 33, 4), "start overshoot, any end")
    assertTrue(ArrayUtils.isEquals(rightSubarray, ArrayUtils.subarray(array, 2, 33)), "normal start, end overshoot")
    assertTrue(ArrayUtils.isEquals(array, ArrayUtils.subarray(array, -2, 12)), "start undershoot, end overshoot")
    assertSame(
      ArrayUtils.EMPTY_CHAR_ARRAY,
      ArrayUtils.subarray(ArrayUtils.EMPTY_CHAR_ARRAY, 1, 2),
      "empty array, object test")
    assertSame(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.subarray(array, 4, 1), "start > end, object test")
    assertSame(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.subarray(array, 3, 3), "start == end, object test")
    assertSame(
      ArrayUtils.EMPTY_CHAR_ARRAY,
      ArrayUtils.subarray(array, 8733, 4),
      "start overshoot, any end, object test")
    assertSame(classOf[Char], ArrayUtils.subarray(array, 2, 4).getClass.getComponentType, "char type")
  }

  @Test def testSwapBoolean(): Unit = {
    val array = Array[Boolean](true, false, false)
    ArrayUtils.swap(array, 0, 2)
    assertFalse(array(0))
    assertFalse(array(1))
    assertTrue(array(2))
  }

  @Test def testSwapBooleanRange(): Unit = {
    var array = Array[Boolean](false, false, true, true)
    ArrayUtils.swap(array, 0, 2, 2)
    assertTrue(array(0))
    assertTrue(array(1))
    assertFalse(array(2))
    assertFalse(array(3))
    array = Array[Boolean](false, true, false)
    ArrayUtils.swap(array, 0, 3)
    assertFalse(array(0))
    assertTrue(array(1))
    assertFalse(array(2))
    array = Array[Boolean](true, true, false)
    ArrayUtils.swap(array, 0, 2, 2)
    assertFalse(array(0))
    assertTrue(array(1))
    assertTrue(array(2))
    array = Array[Boolean](true, true, false)
    ArrayUtils.swap(array, -1, 2, 2)
    assertFalse(array(0))
    assertTrue(array(1))
    assertTrue(array(2))
    array = Array[Boolean](true, true, false)
    ArrayUtils.swap(array, 0, -1, 2)
    assertTrue(array(0))
    assertTrue(array(1))
    assertFalse(array(2))
    array = Array[Boolean](true, true, false)
    ArrayUtils.swap(array, -1, -1, 2)
    assertTrue(array(0))
    assertTrue(array(1))
    assertFalse(array(2))
  }

  @Test def testSwapByte(): Unit = {
    val array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(3.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(1.toByte, array(2))
  }

  @Test def testSwapByteRange(): Unit = {
    var array = Array[Byte](1.toByte, 2.toByte, 3.toByte, 4.toByte)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3.toByte, array(0))
    assertEquals(4.toByte, array(1))
    assertEquals(1.toByte, array(2))
    assertEquals(2.toByte, array(3))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.swap(array, 0, 3)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(1.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(1.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
    array = Array[Byte](1.toByte, 2.toByte, 3.toByte)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1.toByte, array(0))
    assertEquals(2.toByte, array(1))
    assertEquals(3.toByte, array(2))
  }

  @Test def testSwapChar(): Unit = {
    var array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, 0, 2)
    assertArrayEquals(Array[Char](3.toChar, 2.toChar, 1.toChar), array)
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, 0, 0)
    assertArrayEquals(Array[Char](1.toChar, 2.toChar, 3.toChar), array)
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, 1, 0)
    assertArrayEquals(Array[Char](2.toChar, 1.toChar, 3.toChar), array)
  }

  @Test def testSwapCharRange(): Unit = {
    var array = Array[Char](1.toChar, 2.toChar, 3.toChar, 4.toChar)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3.toChar, array(0))
    assertEquals(4.toChar, array(1))
    assertEquals(1.toChar, array(2))
    assertEquals(2.toChar, array(3))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, 0, 3)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(1.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(1.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
    array = Array[Char](1.toChar, 2.toChar, 3.toChar)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1.toChar, array(0))
    assertEquals(2.toChar, array(1))
    assertEquals(3.toChar, array(2))
  }

  @Test def testSwapDouble(): Unit = {
    val array = Array[Double](1d, 2d, 3d)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(3d, array(0))
    assertEquals(2d, array(1))
    assertEquals(1d, array(2))
  }

  @Test def testSwapDoubleRange(): Unit = {
    var array = Array[Double](1d, 2d, 3d, 4d)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3d, array(0))
    assertEquals(4d, array(1))
    assertEquals(1d, array(2))
    assertEquals(2d, array(3))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.swap(array, 0, 3)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3d, array(0))
    assertEquals(2d, array(1))
    assertEquals(1d, array(2))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3d, array(0))
    assertEquals(2d, array(1))
    assertEquals(1d, array(2))
    array = Array[Double](1d, 2d, 3d)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
    array = Array[Double](1, 2, 3)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1d, array(0))
    assertEquals(2d, array(1))
    assertEquals(3d, array(2))
  }

  @Test def testSwapEmptyBooleanArray(): Unit = {
    val array = new Array[Boolean](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyByteArray(): Unit = {
    val array = new Array[Byte](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyCharArray(): Unit = {
    val array = new Array[Char](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyDoubleArray(): Unit = {
    val array = new Array[Double](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyFloatArray(): Unit = {
    val array = new Array[Float](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyIntArray(): Unit = {
    val array = new Array[Int](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyLongArray(): Unit = {
    val array = new Array[Long](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyObjectArray(): Unit = {
    val array = new Array[String](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapEmptyShortArray(): Unit = {
    val array = new Array[Short](0)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(0, array.length)
  }

  @Test def testSwapFloat(): Unit = {
    val array = Array[Float](1, 2, 3)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
  }

  @Test def testSwapFloatRange(): Unit = {
    var array = Array[Float](1f, 2f, 3f, 4f)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3f, array(0))
    assertEquals(4f, array(1))
    assertEquals(1f, array(2))
    assertEquals(2f, array(3))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.swap(array, 0, 3)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3f, array(0))
    assertEquals(2f, array(1))
    assertEquals(1f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3f, array(0))
    assertEquals(2f, array(1))
    assertEquals(1f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
    array = Array[Float](1f, 2f, 3f)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1f, array(0))
    assertEquals(2f, array(1))
    assertEquals(3f, array(2))
  }

  @Test def testSwapInt(): Unit = {
    val array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
  }

  @Test def testSwapIntExchangedOffsets(): Unit = {
    var array: Array[Int] = null
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, 0, 1, 2)
    assertArrayEquals(Array[Int](2, 3, 1), array)
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, 1, 0, 2)
    assertArrayEquals(Array[Int](2, 3, 1), array)
  }

  @Test def testSwapIntRange(): Unit = {
    var array = Array[Int](1, 2, 3, 4)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3, array(0))
    assertEquals(4, array(1))
    assertEquals(1, array(2))
    assertEquals(2, array(3))
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, 3, 0)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3, array(0))
    assertEquals(2, array(1))
    assertEquals(1, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
    array = Array[Int](1, 2, 3)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1, array(0))
    assertEquals(2, array(1))
    assertEquals(3, array(2))
  }

  @Test def testSwapLong(): Unit = {
    val array = Array[Long](1L, 2L, 3L)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(3L, array(0))
    assertEquals(2L, array(1))
    assertEquals(1L, array(2))
  }

  @Test def testSwapLongRange(): Unit = {
    var array = Array[Long](1L, 2L, 3L, 4L)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3L, array(0))
    assertEquals(4L, array(1))
    assertEquals(1L, array(2))
    assertEquals(2L, array(3))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.swap(array, 0, 3)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3L, array(0))
    assertEquals(2L, array(1))
    assertEquals(1L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3L, array(0))
    assertEquals(2L, array(1))
    assertEquals(1L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
    array = Array[Long](1L, 2L, 3L)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1L, array(0))
    assertEquals(2L, array(1))
    assertEquals(3L, array(2))
  }

  @Test def testSwapNullBooleanArray(): Unit = {
    val array: Array[Boolean] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullByteArray(): Unit = {
    val array: Array[Byte] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullCharArray(): Unit = {
    val array: Array[Char] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullDoubleArray(): Unit = {
    val array: Array[Double] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullFloatArray(): Unit = {
    val array: Array[Float] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullIntArray(): Unit = {
    val array: Array[Int] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullLongArray(): Unit = {
    val array: Array[Long] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullObjectArray(): Unit = {
    val array: Array[AnyRef] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapNullShortArray(): Unit = {
    val array: Array[Short] = null
    ArrayUtils.swap(array, 0, 2)
    assertNull(array)
  }

  @Test def testSwapObject(): Unit = {
    val array: Array[String] = Array[String]("1", "2", "3")
    ArrayUtils.swap(array, 0, 2)
    assertEquals("3", array(0))
    assertEquals("2", array(1))
    assertEquals("1", array(2))
  }

  @Test def testSwapObjectRange(): Unit = {
    var array: Array[String] = Array[String]("1", "2", "3", "4")
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals("3", array(0))
    assertEquals("4", array(1))
    assertEquals("1", array(2))
    assertEquals("2", array(3))
    array = Array[String]("1", "2", "3", "4")
    ArrayUtils.swap(array, -1, 2, 3)
    assertEquals("3", array(0))
    assertEquals("4", array(1))
    assertEquals("1", array(2))
    assertEquals("2", array(3))
    array = Array[String]("1", "2", "3", "4", "5")
    ArrayUtils.swap(array, -3, 2, 3)
    assertEquals("3", array(0))
    assertEquals("4", array(1))
    assertEquals("5", array(2))
    assertEquals("2", array(3))
    assertEquals("1", array(4))
    array = Array[String]("1", "2", "3", "4", "5")
    ArrayUtils.swap(array, 2, -2, 3)
    assertEquals("3", array(0))
    assertEquals("4", array(1))
    assertEquals("5", array(2))
    assertEquals("2", array(3))
    assertEquals("1", array(4))
    array = new Array[String](0)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(0, array.length)
    array = null
    ArrayUtils.swap(array, 0, 2, 2)
    assertNull(array)
  }

  @Test def testSwapShort(): Unit = {
    val array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.swap(array, 0, 2)
    assertEquals(3.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(1.toShort, array(2))
  }

  @Test def testSwapShortRange(): Unit = {
    var array = Array[Short](1.toShort, 2.toShort, 3.toShort, 4.toShort)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3.toShort, array(0))
    assertEquals(4.toShort, array(1))
    assertEquals(1.toShort, array(2))
    assertEquals(2.toShort, array(3))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.swap(array, 3, 0)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.swap(array, 0, 2, 2)
    assertEquals(3.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(1.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.swap(array, -1, 2, 2)
    assertEquals(3.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(1.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.swap(array, 0, -1, 2)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
    array = Array[Short](1.toShort, 2.toShort, 3.toShort)
    ArrayUtils.swap(array, -1, -1, 2)
    assertEquals(1.toShort, array(0))
    assertEquals(2.toShort, array(1))
    assertEquals(3.toShort, array(2))
  }

  @Test def testToMap(): Unit = {
    var map = ArrayUtils.toMap(Array(Array("foo", "bar"), Array("hello", "world")))
    assertEquals("bar", map.get("foo"))
    assertEquals("world", map.get("hello"))
    assertNull(ArrayUtils.toMap(null))
    assertThrows[IllegalArgumentException](ArrayUtils.toMap(Array(Array("foo", "bar"), Array("short"))))
    assertThrows[IllegalArgumentException](ArrayUtils.toMap(Array(Array[AnyRef]("foo", "bar"), "illegal type")))
    assertThrows[IllegalArgumentException](ArrayUtils.toMap(Array(Array[AnyRef]("foo", "bar"), null)))
    map = ArrayUtils.toMap(Array[Any](new util.Map.Entry[Any, Any]() {
      override def equals(o: Any) = throw new UnsupportedOperationException

      override def getKey = "foo"

      override def getValue = "bar"

      override def hashCode = throw new UnsupportedOperationException

      override def setValue(value: Any) = throw new UnsupportedOperationException
    }))
    assertEquals("bar", map.get("foo"))
    // Return empty map when got input array with length = 0
    assertEquals(Collections.emptyMap, ArrayUtils.toMap(new Array[Any](0)))
    // Test all null values
    map = ArrayUtils.toMap(Array(Array(null, null), Array(null, null)))
    assertEquals(Collections.singletonMap(null, null), map)
    // Test duplicate keys
    map = ArrayUtils.toMap(Array(Array("key", "value2"), Array("key", "value1")))
    assertEquals(Collections.singletonMap("key", "value1"), map)
  }

  @Test def testToObject_boolean(): Unit = {
    val b: Array[Boolean] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Boolean](0)))
    assertArrayEqualsToObject(
      Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.TRUE),
      ArrayUtils.toObject(Array[Boolean](true, false, true)))
  }

  @Test def testToObject_byte(): Unit = {
    val b: Array[Byte] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_BYTE_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Byte](0)))
    assertArrayEqualsToObject(
      Array[JavaByte](
        JavaByte.valueOf(Byte.MinValue),
        JavaByte.valueOf(Byte.MaxValue),
        JavaByte.valueOf(9999999.toByte)),
      ArrayUtils.toObject(Array[Byte](Byte.MinValue, Byte.MaxValue, 9999999.toByte))
    )
  }

  @Test def testToObject_char(): Unit = {
    val b: Array[Char] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Char](0)))
    assertArrayEqualsToObject(
      Array[Character](Character.valueOf(Char.MinValue), Character.valueOf(Char.MaxValue), Character.valueOf('0')),
      ArrayUtils.toObject(Array[Char](Char.MinValue, Char.MaxValue, '0'))
    )
  }

  @Test def testToObject_double(): Unit = {
    val b: Array[Double] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_DOUBLE_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Double](0)))
    assertArrayEqualsToObject(
      Array[JavaDouble](
        JavaDouble.valueOf(Double.MinValue),
        JavaDouble.valueOf(Double.MaxValue),
        JavaDouble.valueOf(9999999)
      ),
      ArrayUtils.toObject(Array[Double](Double.MinValue, Double.MaxValue, 9999999d))
    )
  }

  @Test def testToObject_float(): Unit = {
    val b: Array[Float] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_FLOAT_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Float](0)))
    assertArrayEqualsToObject(
      Array[JavaFloat](
        JavaFloat.valueOf(Float.MinValue),
        JavaFloat.valueOf(Float.MaxValue),
        JavaFloat.valueOf(9999999)),
      ArrayUtils.toObject(Array[Float](Float.MinValue, Float.MaxValue, 9999999f))
    )
  }

  @Test def testToObject_int(): Unit = {
    val b: Array[Int] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Int](0)))
    assertArrayEqualsToObject(
      Array[Integer](Integer.valueOf(Int.MinValue), Integer.valueOf(Int.MaxValue), Integer.valueOf(9999999)),
      ArrayUtils.toObject(Array[Int](Int.MinValue, Int.MaxValue, 9999999))
    )
  }

  @Test def testToObject_long(): Unit = {
    val b: Array[Long] = null
    assertArrayEqualsToObject(null, ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_LONG_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Long](0)))
    assertArrayEqualsToObject(
      Array[JavaLong](JavaLong.valueOf(Long.MinValue), JavaLong.valueOf(Long.MaxValue), JavaLong.valueOf(9999999)),
      ArrayUtils.toObject(Array[Long](Long.MinValue, Long.MaxValue, 9999999L))
    )
  }

  @Test def testToObject_short(): Unit = {
    val b: Array[Short] = null
    assertArrayEqualsToObject(null.asInstanceOf[Array[JavaShort]], ArrayUtils.toObject(b))
    assertSame(ArrayUtils.EMPTY_SHORT_OBJECT_ARRAY, ArrayUtils.toObject(new Array[Short](0)))
    assertArrayEqualsToObject(
      Array[JavaShort](
        JavaShort.valueOf(Short.MinValue),
        JavaShort.valueOf(Short.MaxValue),
        JavaShort.valueOf(9999999.toShort)),
      ArrayUtils.toObject(Array[Short](Short.MinValue, Short.MaxValue, 9999999.toShort))
    )
  }

  // testToPrimitive/Object for boolean
  //  -----------------------------------------------------------------------
  @Test def testToPrimitive_boolean(): Unit = {
    val b: Array[JavaBoolean] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.toPrimitive(new Array[JavaBoolean](0)))
    assertArrayEquals(
      Array[Boolean](true, false, true),
      ArrayUtils.toPrimitive(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.TRUE)))
    assertThrows[NullPointerException](ArrayUtils.toPrimitive(Array[JavaBoolean](JavaBoolean.TRUE, null)))
    ()
  }

  @Test def testToPrimitive_boolean_boolean(): Unit = {
    assertNull(ArrayUtils.toPrimitive(null, false))
    assertSame(ArrayUtils.EMPTY_BOOLEAN_ARRAY, ArrayUtils.toPrimitive(new Array[JavaBoolean](0), false))
    assertArrayEquals(
      Array[Boolean](true, false, true),
      ArrayUtils.toPrimitive(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.TRUE), false))
    assertArrayEquals(
      Array[Boolean](true, false, false),
      ArrayUtils.toPrimitive(Array[JavaBoolean](JavaBoolean.TRUE, null, JavaBoolean.FALSE), false))
    assertArrayEquals(
      Array[Boolean](true, true, false),
      ArrayUtils.toPrimitive(Array[JavaBoolean](JavaBoolean.TRUE, null, JavaBoolean.FALSE), true))
  }

  // testToPrimitive/Object for byte
  @Test def testToPrimitive_byte(): Unit = {
    val b: Array[JavaByte] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.toPrimitive(new Array[JavaByte](0)))
    assertArrayEquals(
      Array[Byte](Byte.MinValue, Byte.MaxValue, 9999999.toByte),
      ArrayUtils.toPrimitive(
        Array[JavaByte](
          JavaByte.valueOf(Byte.MinValue),
          JavaByte.valueOf(Byte.MaxValue),
          JavaByte.valueOf(9999999.toByte)))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(
        Array[JavaByte](JavaByte.valueOf(Byte.MinValue), null.asInstanceOf[JavaByte])
      ))

    ()
  }

  @Test def testToPrimitive_byte_byte(): Unit = {
    val b: Array[JavaByte] = null
    assertNull(ArrayUtils.toPrimitive(b, Byte.MinValue))
    assertSame(ArrayUtils.EMPTY_BYTE_ARRAY, ArrayUtils.toPrimitive(new Array[JavaByte](0), 1.toByte))
    assertArrayEquals(
      Array[Byte](Byte.MinValue, Byte.MaxValue, 9999999.toByte),
      ArrayUtils.toPrimitive(
        Array[JavaByte](
          JavaByte.valueOf(Byte.MinValue),
          JavaByte.valueOf(Byte.MaxValue),
          JavaByte.valueOf(9999999.toByte)),
        Byte.MinValue)
    )
    assertArrayEquals(
      Array[Byte](Byte.MinValue, Byte.MaxValue, 9999999.toByte),
      ArrayUtils.toPrimitive(
        Array[JavaByte](JavaByte.valueOf(Byte.MinValue), null.asInstanceOf[JavaByte], JavaByte.valueOf(9999999.toByte)),
        Byte.MaxValue)
    )
  }

  @Test def testToPrimitive_char(): Unit = {
    val b: Array[Character] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.toPrimitive(new Array[Character](0)))
    assertArrayEquals(
      Array[Char](Char.MinValue, Char.MaxValue, '0'),
      ArrayUtils.toPrimitive(
        Array[Character](Character.valueOf(Char.MinValue), Character.valueOf(Char.MaxValue), Character.valueOf('0')))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(Array[Character](Character.valueOf(Char.MinValue), null.asInstanceOf[Character])))
    ()
  }

  @Test def testToPrimitive_char_char(): Unit = {
    val b: Array[Character] = null
    assertNull(ArrayUtils.toPrimitive(b, Char.MinValue))
    assertSame(ArrayUtils.EMPTY_CHAR_ARRAY, ArrayUtils.toPrimitive(new Array[Character](0), 0.toChar))
    assertArrayEquals(
      Array[Char](Char.MinValue, Char.MaxValue, '0'),
      ArrayUtils.toPrimitive(
        Array[Character](Character.valueOf(Char.MinValue), Character.valueOf(Char.MaxValue), Character.valueOf('0')),
        Char.MinValue)
    )
    assertArrayEquals(
      Array[Char](Char.MinValue, Char.MaxValue, '0'),
      ArrayUtils.toPrimitive(
        Array[Character](Character.valueOf(Char.MinValue), null.asInstanceOf[Character], Character.valueOf('0')),
        Char.MaxValue)
    )
  }

  //  testToPrimitive/Object for double
  @Test def testToPrimitive_double(): Unit = {
    val b: Array[JavaDouble] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.toPrimitive(new Array[JavaDouble](0)))
    assertArrayEquals(
      Array[Double](Double.MinValue, Double.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaDouble](
          JavaDouble.valueOf(Double.MinValue),
          JavaDouble.valueOf(Double.MaxValue),
          JavaDouble.valueOf(9999999)))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(Array[JavaFloat](JavaFloat.valueOf(Float.MinValue), null.asInstanceOf[JavaFloat])))
    ()
  }

  @Test def testToPrimitive_double_double(): Unit = {
    val l: Array[JavaDouble] = null
    assertNull(ArrayUtils.toPrimitive(l, Double.MinValue))
    assertSame(ArrayUtils.EMPTY_DOUBLE_ARRAY, ArrayUtils.toPrimitive(new Array[JavaDouble](0), 1))
    assertArrayEquals(
      Array[Double](Double.MinValue, Double.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaDouble](
          JavaDouble.valueOf(Double.MinValue),
          JavaDouble.valueOf(Double.MaxValue),
          JavaDouble.valueOf(9999999)),
        1)
    )
    assertArrayEquals(
      Array[Double](Double.MinValue, Double.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaDouble](
          JavaDouble.valueOf(Double.MinValue),
          null.asInstanceOf[JavaDouble],
          JavaDouble.valueOf(9999999)),
        Double.MaxValue)
    )
  }

  //  testToPrimitive/Object for float
  @Test def testToPrimitive_float(): Unit = {
    val b: Array[JavaFloat] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.toPrimitive(new Array[JavaFloat](0)))
    assertArrayEquals(
      Array[Float](Float.MinValue, Float.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaFloat](
          JavaFloat.valueOf(Float.MinValue),
          JavaFloat.valueOf(Float.MaxValue),
          JavaFloat.valueOf(9999999)))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(Array[JavaFloat](JavaFloat.valueOf(Float.MinValue), null.asInstanceOf[JavaFloat])))
    ()
  }

  @Test def testToPrimitive_float_float(): Unit = {
    val l: Array[JavaFloat] = null
    assertNull(ArrayUtils.toPrimitive(l, Float.MinValue))
    assertSame(ArrayUtils.EMPTY_FLOAT_ARRAY, ArrayUtils.toPrimitive(new Array[JavaFloat](0), 1))
    assertArrayEquals(
      Array[Float](Float.MinValue, Float.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaFloat](
          JavaFloat.valueOf(Float.MinValue),
          JavaFloat.valueOf(Float.MaxValue),
          JavaFloat.valueOf(9999999)),
        1)
    )
    assertArrayEquals(
      Array[Float](Float.MinValue, Float.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaFloat](JavaFloat.valueOf(Float.MinValue), null.asInstanceOf[JavaFloat], JavaFloat.valueOf(9999999)),
        Float.MaxValue)
    )
  }

  //  testToPrimitive/Object for int
  @Test def testToPrimitive_int(): Unit = {
    val b: Array[Integer] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.toPrimitive(new Array[Integer](0)))
    assertArrayEquals(
      Array[Int](Int.MinValue, Int.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[Integer](Integer.valueOf(Int.MinValue), Integer.valueOf(Int.MaxValue), Integer.valueOf(9999999)))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(Array[Integer](Integer.valueOf(Int.MinValue), null.asInstanceOf[Integer])))
    ()
  }

  @Test def testToPrimitive_int_int(): Unit = {
    val l: Array[Integer] = null
    assertNull(ArrayUtils.toPrimitive(l, Int.MinValue))
    assertSame(ArrayUtils.EMPTY_INT_ARRAY, ArrayUtils.toPrimitive(new Array[Integer](0), 1))
    assertArrayEquals(
      Array[Int](Int.MinValue, Int.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[Integer](Integer.valueOf(Int.MinValue), Integer.valueOf(Int.MaxValue), Integer.valueOf(9999999)),
        1)
    )
    assertArrayEquals(
      Array[Int](Int.MinValue, Int.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[Integer](Integer.valueOf(Int.MinValue), null.asInstanceOf[Integer], Integer.valueOf(9999999)),
        Int.MaxValue)
    )
  }

  @Test def testToPrimitive_intNull(): Unit = {
    val iArray: Array[Integer] = null
    assertNull(ArrayUtils.toPrimitive(iArray, Int.MinValue))
  }

  //  testToPrimitive/Object for long
  @Test def testToPrimitive_long(): Unit = {
    val b: Array[JavaLong] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.toPrimitive(new Array[JavaLong](0)))
    assertArrayEquals(
      Array[Long](Long.MinValue, Long.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaLong](JavaLong.valueOf(Long.MinValue), JavaLong.valueOf(Long.MaxValue), JavaLong.valueOf(9999999)))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(Array[JavaLong](JavaLong.valueOf(Long.MinValue), null.asInstanceOf[JavaLong])))
    ()
  }

  @Test def testToPrimitive_long_long(): Unit = {
    val l: Array[JavaLong] = null
    assertNull(ArrayUtils.toPrimitive(l, Long.MinValue))
    assertSame(ArrayUtils.EMPTY_LONG_ARRAY, ArrayUtils.toPrimitive(new Array[JavaLong](0), 1))
    assertArrayEquals(
      Array[Long](Long.MinValue, Long.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaLong](JavaLong.valueOf(Long.MinValue), JavaLong.valueOf(Long.MaxValue), JavaLong.valueOf(9999999)),
        1)
    )
    assertArrayEquals(
      Array[Long](Long.MinValue, Long.MaxValue, 9999999),
      ArrayUtils.toPrimitive(
        Array[JavaLong](JavaLong.valueOf(Long.MinValue), null.asInstanceOf[JavaLong], JavaLong.valueOf(9999999)),
        Long.MaxValue)
    )
  }

  // testToPrimitive/Object for short
  @Test def testToPrimitive_short(): Unit = {
    val b: Array[JavaShort] = null
    assertNull(ArrayUtils.toPrimitive(b))
    assertSame(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.toPrimitive(new Array[JavaShort](0)))
    assertArrayEquals(
      Array[Short](Short.MinValue, Short.MaxValue, 9999999.toShort),
      ArrayUtils.toPrimitive(
        Array[JavaShort](
          JavaShort.valueOf(Short.MinValue),
          JavaShort.valueOf(Short.MaxValue),
          JavaShort.valueOf(9999999.toShort)))
    )
    assertThrows[NullPointerException](
      ArrayUtils.toPrimitive(Array[JavaShort](JavaShort.valueOf(Short.MinValue), null.asInstanceOf[JavaShort])))
    ()
  }

  @Test def testToPrimitive_short_short(): Unit = {
    val s: Array[JavaShort] = null
    assertNull(ArrayUtils.toPrimitive(s, Short.MinValue))
    assertSame(ArrayUtils.EMPTY_SHORT_ARRAY, ArrayUtils.toPrimitive(new Array[JavaShort](0), Short.MinValue))
    assertArrayEquals(
      Array[Short](Short.MinValue, Short.MaxValue, 9999999.toShort),
      ArrayUtils.toPrimitive(
        Array[JavaShort](
          JavaShort.valueOf(Short.MinValue),
          JavaShort.valueOf(Short.MaxValue),
          JavaShort.valueOf(9999999.toShort)),
        Short.MinValue)
    )
    assertArrayEquals(
      Array[Short](Short.MinValue, Short.MaxValue, 9999999.toShort),
      ArrayUtils.toPrimitive(
        Array[JavaShort](
          JavaShort.valueOf(Short.MinValue),
          null.asInstanceOf[JavaShort],
          JavaShort.valueOf(9999999.toShort)),
        Short.MaxValue)
    )
  }

  @Test def testToString(): Unit = {
    assertEquals("{}", ArrayUtils.toString(null))
    assertEquals("{}", ArrayUtils.toString(new Array[AnyRef](0)))
    assertEquals("{}", ArrayUtils.toString(new Array[String](0)))
    assertEquals("{<null>}", ArrayUtils.toString(Array[String](null)))
    assertEquals("{pink,blue}", ArrayUtils.toString(Array[String]("pink", "blue")))
    assertEquals("<empty>", ArrayUtils.toString(null, "<empty>"))
    assertEquals("{}", ArrayUtils.toString(new Array[AnyRef](0), "<empty>"))
    assertEquals("{}", ArrayUtils.toString(new Array[String](0), "<empty>"))
    assertEquals("{<null>}", ArrayUtils.toString(Array[String](null), "<empty>"))
    assertEquals("{pink,blue}", ArrayUtils.toString(Array[String]("pink", "blue"), "<empty>"))
  }

  @Test def testToStringArray_array(): Unit = {
    assertNull(ArrayUtils.toStringArray(null))
    assertArrayEqualsToObject(new Array[String](0), ArrayUtils.toStringArray(new Array[AnyRef](0)))
    val array: Array[Any] = Array[Any](1, 2, 3, "array", "test")
    assertArrayEqualsToObject(Array[String]("1", "2", "3", "array", "test"), ArrayUtils.toStringArray(array))
    assertThrows[NullPointerException](ArrayUtils.toStringArray(Array[AnyRef](null)))
    ()
  }

  @Test def testToStringArray_array_string(): Unit = {
    assertNull(ArrayUtils.toStringArray(null, ""))
    assertArrayEqualsToObject(new Array[String](0), ArrayUtils.toStringArray(new Array[AnyRef](0), ""))
    val array: Array[Any] = Array[Any](1, null, "test")
    assertArrayEqualsToObject(
      Array[String]("1", "valueForNullElements", "test"),
      ArrayUtils.toStringArray(array, "valueForNullElements"))
  }

  @Test def textIndexesOfInt(): Unit = {
    var array: Array[Int] = null
    val emptySet = new util.BitSet
    val testSet = new util.BitSet
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 0))
    array = Array[Int](0, 1, 2, 3, 0)
    testSet.set(0)
    testSet.set(4)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 0))
    testSet.clear()
    testSet.set(1)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 1))
    testSet.clear()
    testSet.set(2)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 2))
    testSet.clear()
    testSet.set(3)
    assertEquals(testSet, ArrayUtils.indexesOf(array, 3))
    assertEquals(emptySet, ArrayUtils.indexesOf(array, 99))
  }
}
