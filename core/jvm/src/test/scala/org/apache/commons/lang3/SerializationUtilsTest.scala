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

import java.io._
import java.util
import org.junit.Assert._
import org.junit.{Before, Test}
import org.scalatest.Assertions.{assertThrows, intercept}

/**
  * Unit tests {@link org.apache.commons.lang3.SerializationUtils}.
  */
object SerializationUtilsTest {
  private[lang3] val CLASS_NOT_FOUND_MESSAGE = "ClassNotFoundSerialization.readObject fake exception"
  protected val SERIALIZE_IO_EXCEPTION_MESSAGE = "Anonymous OutputStream I/O exception"
}

class SerializationUtilsTest {
  private var iString: String = null
  private var iInteger: Integer = null
  private var iMap: util.HashMap[AnyRef, AnyRef] = null

  @Before def setUp(): Unit = {
    iString = "foo"
    iInteger = Integer.valueOf(7)
    iMap = new util.HashMap[AnyRef, AnyRef]
    iMap.put("FOO", iString)
    iMap.put("BAR", iInteger)

    ()
  }

//  @Test def testConstructor(): Unit = {
//    assertNotNull(new SerializationUtils.type)
//    val cons = classOf[SerializationUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[SerializationUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[SerializationUtils.type].getModifiers))
//  }

  @Test def testException(): Unit = {
    var serEx: SerializationException = null
    val ex = new Exception

    serEx = new SerializationException
    assertSame(null, serEx.getMessage)
    assertSame(null, serEx.getCause)
    serEx = new SerializationException("Message")
    assertSame("Message", serEx.getMessage)
    assertSame(null, serEx.getCause)
    serEx = new SerializationException(ex)
    assertEquals("java.lang.Exception", serEx.getMessage)
    assertSame(ex, serEx.getCause)
    serEx = new SerializationException("Message", ex)
    assertSame("Message", serEx.getMessage)
    assertSame(ex, serEx.getCause)
  }

  @Test
  @throws[Exception]
  def testSerializeStream(): Unit = {
    val streamTest = new ByteArrayOutputStream
    SerializationUtils.serialize(iMap, streamTest)
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(iMap)
    oos.flush()
    oos.close()
    val testBytes = streamTest.toByteArray
    val realBytes = streamReal.toByteArray
    assertEquals(testBytes.length, realBytes.length)
    assertArrayEquals(realBytes, testBytes)
  }

  @Test def testSerializeStreamUnserializable(): Unit = {
    val streamTest = new ByteArrayOutputStream
    iMap.put(new AnyRef, new AnyRef)
    assertThrows[SerializationException](SerializationUtils.serialize(iMap, streamTest))
    ()
  }

  @Test
  @throws[Exception]
  def testSerializeStreamNullObj(): Unit = {
    val streamTest = new ByteArrayOutputStream
    SerializationUtils.serialize(null, streamTest)
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(null)
    oos.flush()
    oos.close()
    val testBytes = streamTest.toByteArray
    val realBytes = streamReal.toByteArray
    assertEquals(testBytes.length, realBytes.length)
    assertArrayEquals(realBytes, testBytes)
  }

  @Test def testSerializeStreamObjNull(): Unit = {
    assertThrows[NullPointerException](SerializationUtils.serialize(iMap, null))
    ()
  }

  @Test def testSerializeStreamNullNull(): Unit = {
    assertThrows[NullPointerException](SerializationUtils.serialize(null, null))
    ()
  }

  @Test def testSerializeIOException()
    : Unit = { // forces an IOException when the ObjectOutputStream is created, to test not closing the stream
    // in the finally block
    val streamTest = new OutputStream() {
      @throws[IOException]
      override def write(arg0: Int): Unit = throw new IOException(SerializationUtilsTest.SERIALIZE_IO_EXCEPTION_MESSAGE)
    }

    val e = intercept[SerializationException](SerializationUtils.serialize(iMap, streamTest))
    assertEquals("java.io.IOException: " + SerializationUtilsTest.SERIALIZE_IO_EXCEPTION_MESSAGE, e.getMessage)
  }

  @Test
  @throws[Exception]
  def testDeserializeStream(): Unit = {
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(iMap)
    oos.flush()
    oos.close()
    val inTest = new ByteArrayInputStream(streamReal.toByteArray)
    val test = SerializationUtils.deserialize[AnyRef](inTest)
    assertNotNull(test)
    assertTrue(test.isInstanceOf[util.HashMap[_, _]])
    assertNotSame(test, iMap)
    val testMap = test.asInstanceOf[util.HashMap[_, _]]
    assertEquals(iString, testMap.get("FOO"))
    assertNotSame(iString, testMap.get("FOO"))
    assertEquals(iInteger, testMap.get("BAR"))
    assertNotSame(iInteger, testMap.get("BAR"))
    assertEquals(iMap, testMap)
  }

  @Test def testDeserializeClassCastException(): Unit = {
    val value = "Hello"
    val serialized = SerializationUtils.serialize(value)
    assertEquals(value, SerializationUtils.deserialize[String](serialized))
    assertThrows[ClassCastException](SerializationUtils.deserialize[Integer](serialized))
    ()
  }

  @Test
  @throws[Exception]
  def testDeserializeStreamOfNull(): Unit = {
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(null)
    oos.flush()
    oos.close()
    val inTest = new ByteArrayInputStream(streamReal.toByteArray)
    val test = SerializationUtils.deserialize[AnyRef](inTest)
    assertNull(test)
  }

  @Test def testDeserializeStreamNull(): Unit = {
    assertThrows[NullPointerException](SerializationUtils.deserialize(null.asInstanceOf[InputStream]))
    ()
  }

  @Test def testDeserializeStreamBadStream(): Unit = {
    assertThrows[SerializationException](SerializationUtils.deserialize(new ByteArrayInputStream(new Array[Byte](0))))
    ()
  }

  @Test
  @throws[Exception]
  def testDeserializeStreamClassNotFound(): Unit = {
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(new ClassNotFoundSerialization)
    oos.flush()
    oos.close()
    val inTest = new ByteArrayInputStream(streamReal.toByteArray)

    val se = intercept[SerializationException](SerializationUtils.deserialize[ClassNotFoundSerialization](inTest))
    assertEquals("java.lang.ClassNotFoundException: " + SerializationUtilsTest.CLASS_NOT_FOUND_MESSAGE, se.getMessage)
  }

  @Test def testRoundtrip(): Unit = {
    val newMap = SerializationUtils.roundtrip(iMap)
    assertEquals(iMap, newMap)
  }

  @Test
  @throws[Exception]
  def testSerializeBytes(): Unit = {
    val testBytes = SerializationUtils.serialize(iMap)
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(iMap)
    oos.flush()
    oos.close()
    val realBytes = streamReal.toByteArray
    assertEquals(testBytes.length, realBytes.length)
    assertArrayEquals(realBytes, testBytes)
  }

  @Test def testSerializeBytesUnserializable(): Unit = {
    iMap.put(new AnyRef, new AnyRef)
    assertThrows[SerializationException](SerializationUtils.serialize(iMap))
    ()
  }

  @Test
  @throws[Exception]
  def testSerializeBytesNull(): Unit = {
    val testBytes = SerializationUtils.serialize(null)
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(null)
    oos.flush()
    oos.close()
    val realBytes = streamReal.toByteArray
    assertEquals(testBytes.length, realBytes.length)
    assertArrayEquals(realBytes, testBytes)
  }

  @Test
  @throws[Exception]
  def testDeserializeBytes(): Unit = {
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(iMap)
    oos.flush()
    oos.close()
    val test = SerializationUtils.deserialize[AnyRef](streamReal.toByteArray)
    assertNotNull(test)
    assertTrue(test.isInstanceOf[util.HashMap[_, _]])
    assertNotSame(test, iMap)
    val testMap = test.asInstanceOf[util.HashMap[_, _]]
    assertEquals(iString, testMap.get("FOO"))
    assertNotSame(iString, testMap.get("FOO"))
    assertEquals(iInteger, testMap.get("BAR"))
    assertNotSame(iInteger, testMap.get("BAR"))
    assertEquals(iMap, testMap)
  }

  @Test
  @throws[Exception]
  def testDeserializeBytesOfNull(): Unit = {
    val streamReal = new ByteArrayOutputStream
    val oos = new ObjectOutputStream(streamReal)
    oos.writeObject(null)
    oos.flush()
    oos.close()
    val test = SerializationUtils.deserialize[AnyRef](streamReal.toByteArray)
    assertNull(test)
  }

  @Test def testDeserializeBytesNull(): Unit = {
    assertThrows[NullPointerException](SerializationUtils.deserialize(null.asInstanceOf[Array[Byte]]))
    ()
  }

  @Test def testDeserializeBytesBadStream(): Unit = {
    assertThrows[SerializationException](SerializationUtils.deserialize(new Array[Byte](0)))
    ()
  }

  @Test def testClone(): Unit = {
    val test = SerializationUtils.clone(iMap)
    assertNotNull(test)
    assertTrue(test.isInstanceOf[util.HashMap[_, _]])
    assertNotSame(test, iMap)
    val testMap = test.asInstanceOf[util.HashMap[_, _]]
    assertEquals(iString, testMap.get("FOO"))
    assertNotSame(iString, testMap.get("FOO"))
    assertEquals(iInteger, testMap.get("BAR"))
    assertNotSame(iInteger, testMap.get("BAR"))
    assertEquals(iMap, testMap)
  }

  @Test def testCloneNull(): Unit = {
    val test = SerializationUtils.clone(null)
    assertNull(test)
  }

  @Test def testCloneUnserializable(): Unit = {
    iMap.put(new AnyRef, new AnyRef)
    assertThrows[SerializationException](SerializationUtils.clone(iMap))
    ()
  }

  @Test def testPrimitiveTypeClassSerialization(): Unit = {
    val primitiveTypes = Array(
      classOf[Byte],
      classOf[Short],
      classOf[Int],
      classOf[Long],
      classOf[Float],
      classOf[Double],
      classOf[Boolean],
      classOf[Char],
      classOf[Unit])
    for (primitiveType <- primitiveTypes) {
      val clone = SerializationUtils.clone(primitiveType)
      assertEquals(primitiveType, clone)
    }
  }
}

@SerialVersionUID(1L)
class ClassNotFoundSerialization extends Serializable {
  @throws[ClassNotFoundException]
  private def readObject(in: ObjectInputStream): Unit = {
    void(in)
    throw new ClassNotFoundException(SerializationUtilsTest.CLASS_NOT_FOUND_MESSAGE)
  }
}
