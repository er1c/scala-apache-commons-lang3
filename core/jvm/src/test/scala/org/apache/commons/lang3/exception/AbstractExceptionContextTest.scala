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

package org.apache.commons.lang3.exception

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import java.io.Serializable
import java.util
import java.util.Collections
import java.util.Date
import org.apache.commons.lang3.SerializationUtils
import org.junit.Before
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Abstract test of an ExceptionContext implementation.
  */
object AbstractExceptionContextTest {
  protected[exception] val TEST_MESSAGE_2 = "This is monotonous"
  protected[exception] val TEST_MESSAGE = "Test Message"
  protected[exception] class ObjectWithFaultyToString { override def toString = throw new RuntimeException("Crap") }
}

abstract class AbstractExceptionContextTest[T <: ExceptionContext with Serializable] extends JUnitSuite {
  import AbstractExceptionContextTest._
  protected var exceptionContext: T = _

  @Before
  @throws[Exception]
  def setUp(): Unit = {
    exceptionContext
      .addContextValue("test1", null)
      .addContextValue("test2", "some value")
      .addContextValue("test Date", new Date)
      .addContextValue("test Nbr", Integer.valueOf(5))
      .addContextValue("test Poorly written obj", new AbstractExceptionContextTest.ObjectWithFaultyToString)

    ()
  }

  @Test def testAddContextValue(): Unit = {
    val message = exceptionContext.getFormattedExceptionMessage(TEST_MESSAGE)
    assertTrue(message.contains(TEST_MESSAGE))
    assertTrue(message.contains("test1"))
    assertTrue(message.contains("test2"))
    assertTrue(message.contains("test Date"))
    assertTrue(message.contains("test Nbr"))
    assertTrue(message.contains("some value"))
    assertTrue(message.contains("5"))
    assertNull(exceptionContext.getFirstContextValue("test1"))
    assertEquals("some value", exceptionContext.getFirstContextValue("test2"))
    assertEquals(5, exceptionContext.getContextLabels.size)
    assertTrue(exceptionContext.getContextLabels.contains("test1"))
    assertTrue(exceptionContext.getContextLabels.contains("test2"))
    assertTrue(exceptionContext.getContextLabels.contains("test Date"))
    assertTrue(exceptionContext.getContextLabels.contains("test Nbr"))
    exceptionContext.addContextValue("test2", "different value")
    assertEquals(5, exceptionContext.getContextLabels.size)
    assertTrue(exceptionContext.getContextLabels.contains("test2"))
    val contextMessage = exceptionContext.getFormattedExceptionMessage(null)
    assertFalse(contextMessage.contains(TEST_MESSAGE))
  }

  @Test def testSetContextValue(): Unit = {
    exceptionContext.addContextValue("test2", "different value")
    exceptionContext.setContextValue("test3", "3")
    val message = exceptionContext.getFormattedExceptionMessage(TEST_MESSAGE)
    assertTrue(message.contains(TEST_MESSAGE))
    assertTrue(message.contains("test Poorly written obj"))
    assertTrue(message.contains("Crap"))
    assertNull(exceptionContext.getFirstContextValue("crap"))
    assertTrue(
      exceptionContext
        .getFirstContextValue("test Poorly written obj")
        .isInstanceOf[AbstractExceptionContextTest.ObjectWithFaultyToString])
    assertEquals(7, exceptionContext.getContextEntries.size)
    assertEquals(6, exceptionContext.getContextLabels.size)
    assertTrue(exceptionContext.getContextLabels.contains("test Poorly written obj"))
    assertFalse(exceptionContext.getContextLabels.contains("crap"))
    exceptionContext.setContextValue("test Poorly written obj", "replacement")
    assertEquals(7, exceptionContext.getContextEntries.size)
    assertEquals(6, exceptionContext.getContextLabels.size)
    exceptionContext.setContextValue("test2", "another")
    assertEquals(6, exceptionContext.getContextEntries.size)
    assertEquals(6, exceptionContext.getContextLabels.size)
    val contextMessage = exceptionContext.getFormattedExceptionMessage(null)
    assertFalse(contextMessage.contains(TEST_MESSAGE))
  }

  @Test def testGetFirstContextValue(): Unit = {
    exceptionContext.addContextValue("test2", "different value")
    assertNull(exceptionContext.getFirstContextValue("test1"))
    assertEquals("some value", exceptionContext.getFirstContextValue("test2"))
    assertNull(exceptionContext.getFirstContextValue("crap"))
    exceptionContext.setContextValue("test2", "another")
    assertEquals("another", exceptionContext.getFirstContextValue("test2"))
  }

  @Test def testGetContextValues(): Unit = {
    exceptionContext.addContextValue("test2", "different value")
    assertEquals(exceptionContext.getContextValues("test1"), Collections.singletonList(null))
    assertEquals(exceptionContext.getContextValues("test2"), util.Arrays.asList("some value", "different value"))
    exceptionContext.setContextValue("test2", "another")
    assertEquals("another", exceptionContext.getFirstContextValue("test2"))
  }

  @Test def testGetContextLabels(): Unit = {
    assertEquals(5, exceptionContext.getContextEntries.size)
    exceptionContext.addContextValue("test2", "different value")
    val labels = exceptionContext.getContextLabels
    assertEquals(6, exceptionContext.getContextEntries.size)
    assertEquals(5, labels.size)
    assertTrue(labels.contains("test1"))
    assertTrue(labels.contains("test2"))
    assertTrue(labels.contains("test Date"))
    assertTrue(labels.contains("test Nbr"))
  }

  @Test def testGetContextEntries(): Unit = {
    assertEquals(5, exceptionContext.getContextEntries.size)
    exceptionContext.addContextValue("test2", "different value")
    val entries = exceptionContext.getContextEntries
    assertEquals(6, entries.size)
    assertEquals("test1", entries.get(0).getKey)
    assertEquals("test2", entries.get(1).getKey)
    assertEquals("test Date", entries.get(2).getKey)
    assertEquals("test Nbr", entries.get(3).getKey)
    assertEquals("test Poorly written obj", entries.get(4).getKey)
    assertEquals("test2", entries.get(5).getKey)
  }

  @Test def testJavaSerialization(): Unit = {
    exceptionContext.setContextValue("test Poorly written obj", "serializable replacement")
    val clone: T = SerializationUtils.deserialize[T](SerializationUtils.serialize(exceptionContext))
    assertEquals(exceptionContext.getFormattedExceptionMessage(null), clone.getFormattedExceptionMessage(null))
  }
}
