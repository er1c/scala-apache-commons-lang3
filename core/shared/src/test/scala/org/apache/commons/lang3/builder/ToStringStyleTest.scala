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

package org.apache.commons.lang3.builder

import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Test case for ToStringStyle.
  */
object ToStringStyleTest {

  @SerialVersionUID(1L)
  private class ToStringStyleImpl extends ToStringStyle {}

  /**
    * An object used to test {@link ToStringStyle}.
    *
    */
  private[builder] class Person {
    /**
      * Test String field.
      */
    private[builder] var name: String = null
    /**
      * Test integer field.
      */
    private[builder] var age: Int = 0
    /**
      * Test boolean field.
      */
    private[builder] var smoker: Boolean = false
  }

}

class ToStringStyleTest extends JUnitSuite {
  @Test def testSetArrayStart(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setArrayStart(null)
    assertEquals("", style.getArrayStart)
  }

  @Test def testSetArrayEnd(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setArrayEnd(null)
    assertEquals("", style.getArrayEnd)
  }

  @Test def testSetArraySeparator(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setArraySeparator(null)
    assertEquals("", style.getArraySeparator)
  }

  @Test def testSetContentStart(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setContentStart(null)
    assertEquals("", style.getContentStart)
  }

  @Test def testSetContentEnd(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setContentEnd(null)
    assertEquals("", style.getContentEnd)
  }

  @Test def testSetFieldNameValueSeparator(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setFieldNameValueSeparator(null)
    assertEquals("", style.getFieldNameValueSeparator)
  }

  @Test def testSetFieldSeparator(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setFieldSeparator(null)
    assertEquals("", style.getFieldSeparator)
  }

  @Test def testSetNullText(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setNullText(null)
    assertEquals("", style.getNullText)
  }

  @Test def testSetSizeStartText(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setSizeStartText(null)
    assertEquals("", style.getSizeStartText)
  }

  @Test def testSetSizeEndText(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setSizeEndText(null)
    assertEquals("", style.getSizeEndText)
  }

  @Test def testSetSummaryObjectStartText(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setSummaryObjectStartText(null)
    assertEquals("", style.getSummaryObjectStartText)
  }

  @Test def testSetSummaryObjectEndText(): Unit = {
    val style = new ToStringStyleTest.ToStringStyleImpl
    style.setSummaryObjectEndText(null)
    assertEquals("", style.getSummaryObjectEndText)
  }
}
