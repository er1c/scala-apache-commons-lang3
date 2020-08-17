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

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import java.io.ByteArrayOutputStream
//import java.io.IOException
import java.io.PrintStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util
//import org.apache.commons.lang3.test.NotVisibleExceptionFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
//import org.junit.function.ThrowingRunnable
import org.scalatestplus.junit.JUnitSuite
import scala.util.control.Breaks

/**
  * Tests {@link org.apache.commons.lang3.exception.ExceptionUtils}.
  *
  * @since 1.0
  */
object ExceptionUtilsTest {

  /**
    * Our own copy of breaks to avoid conflicts with any other breaks:
    * "Calls to break from one instantiation of Breaks will never target breakable objects of some other instantiation."
    */
  private val breaks: Breaks = new Breaks

  /**
    * Provides a method with a well known chained/nested exception
    * name which matches the full signature (e.g. has a return value
    * of {@code Throwable}.
    */
  @SerialVersionUID(1L)
  private class ExceptionWithCause(str: String, private var cause: Throwable) extends Exception(str, cause) {
    def this(cause: Throwable) = {
      this(null, cause)
    }

    override def getCause: Throwable = this.cause
    def setCauseImpl(cause: Throwable): Unit = this.cause = cause
  }

  /**
    * Provides a method with a well known chained/nested exception
    * name which does not match the full signature (e.g. lacks a
    * return value of {@code Throwable}.
    */
  @SerialVersionUID(1L)
  private class ExceptionWithoutCause extends Exception {
    @SuppressWarnings(Array("unused"))
    def getTargetException(): Unit = {
      // noop
    }
  }

  // Temporary classes to allow the nested exception code to be removed
  // prior to a rewrite of this test class.
  @SerialVersionUID(1L)
  private class NestableException(t: Throwable) extends Exception(t) {
    //@SuppressWarnings(Array("unused"))
    //private[exception] def this() = this(null)
  }

  @SerialVersionUID(1L)
  class TestThrowable extends Throwable {}

//  private def redeclareCheckedException = throwsCheckedException
//
//  private def throwsCheckedException =
//    try {
//      throw new IOException
//    } catch {
//      case e: Exception => ExceptionUtils.rethrow[Integer](e)
//    }
}

class ExceptionUtilsTest extends JUnitSuite {
  import ExceptionUtilsTest._
  import ExceptionUtilsTest.breaks._

  private var nested: NestableException = null
  private var withCause: Throwable = null
  private var withoutCause: Throwable = null
  private var jdkNoCause: Throwable = null
  private var cyclicCause: ExceptionWithCause = null
  private var notVisibleException: Throwable = null

  private def createExceptionWithCause: Throwable =
    try {
      try {
        throw new ExceptionWithCause(createExceptionWithoutCause)
      } catch {
        case t: Throwable => throw new ExceptionWithCause(t)
      }
    } catch {
      case t: Throwable => t
    }

  private def createExceptionWithoutCause: Throwable =
    try {
      throw new ExceptionWithoutCause
    } catch {
      case t: Throwable => t
    }

  @Before
  def setUp(): Unit = {
    withoutCause = createExceptionWithoutCause
    nested = new NestableException(withoutCause)
    withCause = new ExceptionWithCause(nested)
    jdkNoCause = new NullPointerException
    val a: ExceptionWithCause = new ExceptionWithCause(null)
    val b: ExceptionWithCause = new ExceptionWithCause(a)
    a.setCauseImpl(b)
    cyclicCause = new ExceptionWithCause(a)
//    notVisibleException = NotVisibleExceptionFactory.createException(withoutCause)
  }

  @After
  def tearDown(): Unit = {
    withoutCause = null
    nested = null
    withCause = null
    jdkNoCause = null
    cyclicCause = null
    notVisibleException = null
  }

  @Test
  def test_getMessage_Throwable(): Unit = {
    var th: Throwable = null
    assertEquals("", ExceptionUtils.getMessage(th))
    th = new IllegalArgumentException("Base")
    assertEquals("IllegalArgumentException: Base", ExceptionUtils.getMessage(th))
    th = new ExceptionWithCause("Wrapper", th)
    assertEquals("ExceptionUtilsTest.ExceptionWithCause: Wrapper", ExceptionUtils.getMessage(th))
  }

  @Test def test_getRootCauseMessage_Throwable(): Unit = {
    var th: Throwable = null
    assertEquals("", ExceptionUtils.getRootCauseMessage(th))
    th = new IllegalArgumentException("Base")
    assertEquals("IllegalArgumentException: Base", ExceptionUtils.getRootCauseMessage(th))
    th = new ExceptionWithCause("Wrapper", th)
    assertEquals("IllegalArgumentException: Base", ExceptionUtils.getRootCauseMessage(th))
  }

//  @Test def testCatchTechniques(): Unit = {
//    var ioe = org.junit.Assert.assertThrows(
//      classOf[IOException],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          throwsCheckedException
//          ()
//        }
//      })
//    assertEquals(1, ExceptionUtils.getThrowableCount(ioe))
//
//    ioe = org.junit.Assert.assertThrows(
//      classOf[IOException],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          redeclareCheckedException
//          ()
//        }
//      })
//    assertEquals(1, ExceptionUtils.getThrowableCount(ioe))
//  }

  @SuppressWarnings(Array("deprecation")) // Specifically tests the deprecated methods
  @Test def testGetCause_Throwable(): Unit = {
    assertSame(null, ExceptionUtils.getCause(null))
    assertSame(null, ExceptionUtils.getCause(withoutCause))
    assertSame(withoutCause, ExceptionUtils.getCause(nested))
    assertSame(nested, ExceptionUtils.getCause(withCause))
    assertSame(null, ExceptionUtils.getCause(jdkNoCause))
    assertSame(cyclicCause.getCause, ExceptionUtils.getCause(cyclicCause))
    assertSame(cyclicCause.getCause.getCause, ExceptionUtils.getCause(cyclicCause.getCause))
    assertSame(cyclicCause.getCause, ExceptionUtils.getCause(cyclicCause.getCause.getCause))
    assertSame(withoutCause, ExceptionUtils.getCause(notVisibleException))
  }

  @SuppressWarnings(Array("deprecation"))
  @Test
  def testGetCause_ThrowableArray(): Unit = {
    assertSame(null, ExceptionUtils.getCause(null, null))
    assertSame(null, ExceptionUtils.getCause(null, new Array[String](0)))
    // not known type, so match on supplied method names
    assertSame(nested, ExceptionUtils.getCause(withCause, null)) // default names

    assertSame(null, ExceptionUtils.getCause(withCause, new Array[String](0)))
    assertSame(null, ExceptionUtils.getCause(withCause, Array[String](null)))
    assertSame(nested, ExceptionUtils.getCause(withCause, Array[String]("getCause")))
    assertSame(null, ExceptionUtils.getCause(withoutCause, null))
    assertSame(null, ExceptionUtils.getCause(withoutCause, new Array[String](0)))
    assertSame(null, ExceptionUtils.getCause(withoutCause, Array[String](null)))
    assertSame(null, ExceptionUtils.getCause(withoutCause, Array[String]("getCause")))
    assertSame(null, ExceptionUtils.getCause(withoutCause, Array[String]("getTargetException")))
  }

  @Test def testGetRootCause_Throwable(): Unit = {
    assertSame(null, ExceptionUtils.getRootCause(null))
    assertSame(withoutCause, ExceptionUtils.getRootCause(withoutCause))
    assertSame(withoutCause, ExceptionUtils.getRootCause(nested))
    assertSame(withoutCause, ExceptionUtils.getRootCause(withCause))
    assertSame(jdkNoCause, ExceptionUtils.getRootCause(jdkNoCause))
    assertSame(cyclicCause.getCause.getCause, ExceptionUtils.getRootCause(cyclicCause))
  }

  @Test def testGetRootCauseStackTrace_Throwable(): Unit = {
    assertEquals(0, ExceptionUtils.getRootCauseStackTrace(null).length)
    val cause: Throwable = createExceptionWithCause
    var stackTrace: Array[String] = ExceptionUtils.getRootCauseStackTrace(cause)
    var `match`: Boolean = false

    breakable {
      for (element <- stackTrace) {
        if (element.startsWith(ExceptionUtils.WRAPPED_MARKER)) {
          `match` = true
          break()
        }
      }
    }

    assertTrue(`match`)
    stackTrace = ExceptionUtils.getRootCauseStackTrace(withoutCause)
    `match` = false

    breakable {
      for (element <- stackTrace) {
        if (element.startsWith(ExceptionUtils.WRAPPED_MARKER)) {
          `match` = true
          break()
        }
      }
    }

    assertFalse(`match`)
  }

  @Test def testGetThrowableCount_Throwable(): Unit = {
    assertEquals(0, ExceptionUtils.getThrowableCount(null))
    assertEquals(1, ExceptionUtils.getThrowableCount(withoutCause))
    assertEquals(2, ExceptionUtils.getThrowableCount(nested))
    assertEquals(3, ExceptionUtils.getThrowableCount(withCause))
    assertEquals(1, ExceptionUtils.getThrowableCount(jdkNoCause))
    assertEquals(3, ExceptionUtils.getThrowableCount(cyclicCause))
  }

  @Test def testGetThrowableList_Throwable_jdkNoCause(): Unit = {
    val throwables: util.List[_] = ExceptionUtils.getThrowableList(jdkNoCause)
    assertEquals(1, throwables.size)
    assertSame(jdkNoCause, throwables.get(0))
  }

  @Test def testGetThrowableList_Throwable_nested(): Unit = {
    val throwables: util.List[_] = ExceptionUtils.getThrowableList(nested)
    assertEquals(2, throwables.size)
    assertSame(nested, throwables.get(0))
    assertSame(withoutCause, throwables.get(1))
  }

  @Test def testGetThrowableList_Throwable_null(): Unit = {
    val throwables: util.List[_] = ExceptionUtils.getThrowableList(null)
    assertEquals(0, throwables.size)
  }

  @Test def testGetThrowableList_Throwable_recursiveCause(): Unit = {
    val throwables: util.List[_] = ExceptionUtils.getThrowableList(cyclicCause)
    assertEquals(3, throwables.size)
    assertSame(cyclicCause, throwables.get(0))
    assertSame(cyclicCause.getCause, throwables.get(1))
    assertSame(cyclicCause.getCause.getCause, throwables.get(2))
  }

  @Test def testGetThrowableList_Throwable_withCause(): Unit = {
    val throwables: util.List[_] = ExceptionUtils.getThrowableList(withCause)
    assertEquals(3, throwables.size)
    assertSame(withCause, throwables.get(0))
    assertSame(nested, throwables.get(1))
    assertSame(withoutCause, throwables.get(2))
  }

  @Test def testGetThrowableList_Throwable_withoutCause(): Unit = {
    val throwables: util.List[_] = ExceptionUtils.getThrowableList(withoutCause)
    assertEquals(1, throwables.size)
    assertSame(withoutCause, throwables.get(0))
  }

  @Test def testGetThrowables_Throwable_jdkNoCause(): Unit = {
    val throwables: Array[Throwable] = ExceptionUtils.getThrowables(jdkNoCause)
    assertEquals(1, throwables.length)
    assertSame(jdkNoCause, throwables(0))
  }

  @Test def testGetThrowables_Throwable_nested(): Unit = {
    val throwables: Array[Throwable] = ExceptionUtils.getThrowables(nested)
    assertEquals(2, throwables.length)
    assertSame(nested, throwables(0))
    assertSame(withoutCause, throwables(1))
  }

  @Test def testGetThrowables_Throwable_null(): Unit = {
    assertEquals(0, ExceptionUtils.getThrowables(null).length)
  }

  @Test def testGetThrowables_Throwable_recursiveCause(): Unit = {
    val throwables: Array[Throwable] = ExceptionUtils.getThrowables(cyclicCause)
    assertEquals(3, throwables.length)
    assertSame(cyclicCause, throwables(0))
    assertSame(cyclicCause.getCause, throwables(1))
    assertSame(cyclicCause.getCause.getCause, throwables(2))
  }

  @Test def testGetThrowables_Throwable_withCause(): Unit = {
    val throwables: Array[Throwable] = ExceptionUtils.getThrowables(withCause)
    assertEquals(3, throwables.length)
    assertSame(withCause, throwables(0))
    assertSame(nested, throwables(1))
    assertSame(withoutCause, throwables(2))
  }

  @Test def testGetThrowables_Throwable_withoutCause(): Unit = {
    val throwables: Array[Throwable] = ExceptionUtils.getThrowables(withoutCause)
    assertEquals(1, throwables.length)
    assertSame(withoutCause, throwables(0))
  }

  @Test def testIndexOf_ThrowableClass(): Unit = {
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(null, null))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(null, classOf[NestableException]))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withoutCause, null))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withoutCause, classOf[ExceptionWithCause]))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withoutCause, classOf[NestableException]))
    assertEquals(0, ExceptionUtils.indexOfThrowable(withoutCause, classOf[ExceptionWithoutCause]))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(nested, null))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(nested, classOf[ExceptionWithCause]))
    assertEquals(0, ExceptionUtils.indexOfThrowable(nested, classOf[NestableException]))
    assertEquals(1, ExceptionUtils.indexOfThrowable(nested, classOf[ExceptionWithoutCause]))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, null))
    assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithCause]))
    assertEquals(1, ExceptionUtils.indexOfThrowable(withCause, classOf[NestableException]))
    assertEquals(2, ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithoutCause]))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, classOf[Exception]))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, classOf[Throwable]))
  }

  @Test def testIndexOf_ThrowableClassInt(): Unit = {
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(null, null, 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(null, classOf[NestableException], 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withoutCause, null))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withoutCause, classOf[ExceptionWithCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withoutCause, classOf[NestableException], 0))
    assertEquals(0, ExceptionUtils.indexOfThrowable(withoutCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(nested, null, 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(nested, classOf[ExceptionWithCause], 0))
    assertEquals(0, ExceptionUtils.indexOfThrowable(nested, classOf[NestableException], 0))
    assertEquals(1, ExceptionUtils.indexOfThrowable(nested, classOf[ExceptionWithoutCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, null))
    assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(1, ExceptionUtils.indexOfThrowable(withCause, classOf[NestableException], 0))
    assertEquals(2, ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithCause], -(1)))
    assertEquals(0, ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithCause], 1))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, classOf[ExceptionWithCause], 9))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, classOf[Exception], 0))
    assertEquals(-(1), ExceptionUtils.indexOfThrowable(withCause, classOf[Throwable], 0))
  }

  @Test def testIndexOfType_ThrowableClass(): Unit = {
    assertEquals(-(1), ExceptionUtils.indexOfType(null, null))
    assertEquals(-(1), ExceptionUtils.indexOfType(null, classOf[NestableException]))
    assertEquals(-(1), ExceptionUtils.indexOfType(withoutCause, null))
    assertEquals(-(1), ExceptionUtils.indexOfType(withoutCause, classOf[ExceptionWithCause]))
    assertEquals(-(1), ExceptionUtils.indexOfType(withoutCause, classOf[NestableException]))
    assertEquals(0, ExceptionUtils.indexOfType(withoutCause, classOf[ExceptionWithoutCause]))
    assertEquals(-(1), ExceptionUtils.indexOfType(nested, null))
    assertEquals(-(1), ExceptionUtils.indexOfType(nested, classOf[ExceptionWithCause]))
    assertEquals(0, ExceptionUtils.indexOfType(nested, classOf[NestableException]))
    assertEquals(1, ExceptionUtils.indexOfType(nested, classOf[ExceptionWithoutCause]))
    assertEquals(-(1), ExceptionUtils.indexOfType(withCause, null))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithCause]))
    assertEquals(1, ExceptionUtils.indexOfType(withCause, classOf[NestableException]))
    assertEquals(2, ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithoutCause]))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[Exception]))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[Throwable]))
  }

  @Test def testIndexOfType_ThrowableClassInt(): Unit = {
    assertEquals(-(1), ExceptionUtils.indexOfType(null, null, 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(null, classOf[NestableException], 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(withoutCause, null))
    assertEquals(-(1), ExceptionUtils.indexOfType(withoutCause, classOf[ExceptionWithCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(withoutCause, classOf[NestableException], 0))
    assertEquals(0, ExceptionUtils.indexOfType(withoutCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(nested, null, 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(nested, classOf[ExceptionWithCause], 0))
    assertEquals(0, ExceptionUtils.indexOfType(nested, classOf[NestableException], 0))
    assertEquals(1, ExceptionUtils.indexOfType(nested, classOf[ExceptionWithoutCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(withCause, null))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(1, ExceptionUtils.indexOfType(withCause, classOf[NestableException], 0))
    assertEquals(2, ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithCause], -(1)))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(-(1), ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithCause], 1))
    assertEquals(-(1), ExceptionUtils.indexOfType(withCause, classOf[ExceptionWithCause], 9))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[Exception], 0))
    assertEquals(0, ExceptionUtils.indexOfType(withCause, classOf[Throwable], 0))
  }

  @Test def testPrintRootCauseStackTrace_Throwable(): Unit = {
    ExceptionUtils.printRootCauseStackTrace(null)
    // could pipe system.err to a known stream, but not much point as
    // internally this method calls stream method anyway
  }

  @Test def testPrintRootCauseStackTrace_ThrowableStream(): Unit = {
    var out: ByteArrayOutputStream = new ByteArrayOutputStream(1024)
    ExceptionUtils.printRootCauseStackTrace(null, null.asInstanceOf[PrintStream])
    ExceptionUtils.printRootCauseStackTrace(null, new PrintStream(out))
    assertEquals(0, out.toString.length)
    out = new ByteArrayOutputStream(1024)
    assertThrows[NullPointerException](
      ExceptionUtils.printRootCauseStackTrace(withCause, null.asInstanceOf[PrintStream]))
    out = new ByteArrayOutputStream(1024)
    val cause: Throwable = createExceptionWithCause
    ExceptionUtils.printRootCauseStackTrace(cause, new PrintStream(out))
    var stackTrace: String = out.toString
    assertTrue(stackTrace.contains(ExceptionUtils.WRAPPED_MARKER))
    out = new ByteArrayOutputStream(1024)
    ExceptionUtils.printRootCauseStackTrace(withoutCause, new PrintStream(out))
    stackTrace = out.toString
    assertFalse(stackTrace.contains(ExceptionUtils.WRAPPED_MARKER))
  }

  @Test def testPrintRootCauseStackTrace_ThrowableWriter(): Unit = {
    var writer: StringWriter = new StringWriter(1024)
    ExceptionUtils.printRootCauseStackTrace(null, null.asInstanceOf[PrintWriter])
    ExceptionUtils.printRootCauseStackTrace(null, new PrintWriter(writer))
    assertEquals(0, writer.getBuffer.length)
    writer = new StringWriter(1024)
    assertThrows[NullPointerException](
      ExceptionUtils.printRootCauseStackTrace(withCause, null.asInstanceOf[PrintWriter]))

    writer = new StringWriter(1024)
    val cause: Throwable = createExceptionWithCause
    ExceptionUtils.printRootCauseStackTrace(cause, new PrintWriter(writer))
    var stackTrace: String = writer.toString
    assertTrue(stackTrace.contains(ExceptionUtils.WRAPPED_MARKER))
    writer = new StringWriter(1024)
    ExceptionUtils.printRootCauseStackTrace(withoutCause, new PrintWriter(writer))
    stackTrace = writer.toString
    assertFalse(stackTrace.contains(ExceptionUtils.WRAPPED_MARKER))
  }

  @Test def testRemoveCommonFrames_ListList(): Unit = {
    assertThrows[IllegalArgumentException](ExceptionUtils.removeCommonFrames(null, null))
    ()
  }

//  @Test def testThrow(): Unit = {
//    val expected: Exception = new InterruptedException
//    val actual: Exception = org.junit.Assert.assertThrows(
//      classOf[Exception],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ExceptionUtils.rethrow(expected)
//        }
//      })
//
//    assertSame(expected, actual)
//  }

  @Test def testThrowableOf_ThrowableClass(): Unit = {
    assertEquals(null, ExceptionUtils.throwableOfThrowable[Throwable](null, null))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[NestableException](null, classOf[NestableException]))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[Throwable](withoutCause, null))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withoutCause, classOf[ExceptionWithCause]))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withoutCause, classOf[NestableException]))
    assertEquals(withoutCause, ExceptionUtils.throwableOfThrowable(withoutCause, classOf[ExceptionWithoutCause]))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[NestableException](nested, null))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(nested, classOf[ExceptionWithCause]))
    assertEquals(nested, ExceptionUtils.throwableOfThrowable(nested, classOf[NestableException]))
    assertEquals(nested.getCause, ExceptionUtils.throwableOfThrowable(nested, classOf[ExceptionWithoutCause]))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[ExceptionWithCause](withCause, null))
    assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithCause]))
    assertEquals(withCause.getCause, ExceptionUtils.throwableOfThrowable(withCause, classOf[NestableException]))
    assertEquals(
      withCause.getCause.getCause,
      ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithoutCause]))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withCause, classOf[Exception]))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withCause, classOf[Throwable]))
  }

  @Test def testThrowableOf_ThrowableClassInt(): Unit = {
    assertEquals(null, ExceptionUtils.throwableOfThrowable[Throwable](null, null, 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(null, classOf[NestableException], 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[ExceptionWithoutCause](withoutCause, null))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withoutCause, classOf[ExceptionWithCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withoutCause, classOf[NestableException], 0))
    assertEquals(withoutCause, ExceptionUtils.throwableOfThrowable(withoutCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[NestableException](nested, null, 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(nested, classOf[ExceptionWithCause], 0))
    assertEquals(nested, ExceptionUtils.throwableOfThrowable(nested, classOf[NestableException], 0))
    assertEquals(nested.getCause, ExceptionUtils.throwableOfThrowable(nested, classOf[ExceptionWithoutCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable[ExceptionWithCause](withCause, null))
    assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(withCause.getCause, ExceptionUtils.throwableOfThrowable(withCause, classOf[NestableException], 0))
    assertEquals(
      withCause.getCause.getCause,
      ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithCause], -(1)))
    assertEquals(withCause, ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithCause], 1))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withCause, classOf[ExceptionWithCause], 9))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withCause, classOf[Exception], 0))
    assertEquals(null, ExceptionUtils.throwableOfThrowable(withCause, classOf[Throwable], 0))
  }

  @Test def testThrowableOfType_ThrowableClass(): Unit = {
    assertEquals(null, ExceptionUtils.throwableOfType[Throwable](null, null))
    assertEquals(null, ExceptionUtils.throwableOfType(null, classOf[NestableException]))
    assertEquals(null, ExceptionUtils.throwableOfType[ExceptionWithoutCause](withoutCause, null))
    assertEquals(null, ExceptionUtils.throwableOfType(withoutCause, classOf[ExceptionWithCause]))
    assertEquals(null, ExceptionUtils.throwableOfType(withoutCause, classOf[NestableException]))
    assertEquals(withoutCause, ExceptionUtils.throwableOfType(withoutCause, classOf[ExceptionWithoutCause]))
    assertEquals(null, ExceptionUtils.throwableOfType[NestableException](nested, null))
    assertEquals(null, ExceptionUtils.throwableOfType(nested, classOf[ExceptionWithCause]))
    assertEquals(nested, ExceptionUtils.throwableOfType(nested, classOf[NestableException]))
    assertEquals(nested.getCause, ExceptionUtils.throwableOfType(nested, classOf[ExceptionWithoutCause]))
    assertEquals(null, ExceptionUtils.throwableOfType[ExceptionWithCause](withCause, null))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithCause]))
    assertEquals(withCause.getCause, ExceptionUtils.throwableOfType(withCause, classOf[NestableException]))
    assertEquals(withCause.getCause.getCause, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithoutCause]))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[Exception]))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[Throwable]))
  }

  @Test def testThrowableOfType_ThrowableClassInt(): Unit = {
    assertEquals(null, ExceptionUtils.throwableOfType[Throwable](null, null, 0))
    assertEquals(null, ExceptionUtils.throwableOfType(null, classOf[NestableException], 0))
    assertEquals(null, ExceptionUtils.throwableOfType[ExceptionWithoutCause](withoutCause, null))
    assertEquals(null, ExceptionUtils.throwableOfType(withoutCause, classOf[ExceptionWithCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfType(withoutCause, classOf[NestableException], 0))
    assertEquals(withoutCause, ExceptionUtils.throwableOfType(withoutCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfType[NestableException](nested, null, 0))
    assertEquals(null, ExceptionUtils.throwableOfType(nested, classOf[ExceptionWithCause], 0))
    assertEquals(nested, ExceptionUtils.throwableOfType(nested, classOf[NestableException], 0))
    assertEquals(nested.getCause, ExceptionUtils.throwableOfType(nested, classOf[ExceptionWithoutCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfType[ExceptionWithCause](withCause, null))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(withCause.getCause, ExceptionUtils.throwableOfType(withCause, classOf[NestableException], 0))
    assertEquals(
      withCause.getCause.getCause,
      ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithoutCause], 0))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithCause], -(1)))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithCause], 0))
    assertEquals(null, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithCause], 1))
    assertEquals(null, ExceptionUtils.throwableOfType(withCause, classOf[ExceptionWithCause], 9))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[Exception], 0))
    assertEquals(withCause, ExceptionUtils.throwableOfType(withCause, classOf[Throwable], 0))
  }

//  @Test def testWrapAndUnwrapCheckedException(): Unit = {
//    val t: Throwable = org.junit.Assert.assertThrows(
//      classOf[Throwable],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ExceptionUtils.wrapAndThrow(new IOException)
//        }
//      })
//
//    assertTrue(ExceptionUtils.hasCause(t, classOf[IOException]))
//  }

//  @Test def testWrapAndUnwrapError(): Unit = {
//    val t: Throwable = org.junit.Assert.assertThrows(
//      classOf[Throwable],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ExceptionUtils.wrapAndThrow(new OutOfMemoryError)
//        }
//      })
//
//    assertTrue(ExceptionUtils.hasCause(t, classOf[Error]))
//  }

//  @Test def testWrapAndUnwrapRuntimeException(): Unit = {
//    val t: Throwable = org.junit.Assert.assertThrows(
//      classOf[Throwable],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ExceptionUtils.wrapAndThrow(new IllegalArgumentException)
//        }
//      })
//
//    assertTrue(ExceptionUtils.hasCause(t, classOf[RuntimeException]))
//  }

//  @Test def testWrapAndUnwrapThrowable(): Unit = {
//    val t: Throwable = org.junit.Assert.assertThrows(
//      classOf[Throwable],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ExceptionUtils.wrapAndThrow(new TestThrowable)
//        }
//      })
//
//    assertTrue(ExceptionUtils.hasCause(t, classOf[TestThrowable]))
//  }

  @Test
  //@DisplayName("getStackFrames returns the string array of the stack frames when there is a real exception")
  def testgetStackFramesNullArg(): Unit = {
    val actual: Array[String] = ExceptionUtils.getStackFrames(null.asInstanceOf[Throwable])
    assertEquals(0, actual.length)
  }

  @Test
  //@DisplayName("getStackFrames returns empty string array when the argument is null")
  def testgetStackFramesHappyPath(): Unit = {
    val actual: Array[String] =
      ExceptionUtils.getStackFrames(new Throwable() { // provide static stack trace to make test stable
        override def printStackTrace(s: PrintWriter): Unit = {
          s.write(
            "org.apache.commons.lang3.exception.ExceptionUtilsTest$1\n" +
              "\tat org.apache.commons.lang3.exception.testgetStackFramesGappyPath(java:706)\n" +
              "\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
              "\tat com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)\n" +
              "\tat com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)\n"
          )
        }
      })

    assertArrayEquals(
      Array[String](
        "org.apache.commons.lang3.exception.ExceptionUtilsTest$1",
        "\tat org.apache.commons.lang3.exception.testgetStackFramesGappyPath(java:706)",
        "\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)",
        "\tat com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)",
        "\tat com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)"
      ).asInstanceOf[Array[Object]],
      actual.asInstanceOf[Array[Object]]
    )
  }
}
