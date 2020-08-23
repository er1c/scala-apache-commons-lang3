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

package org.apache.commons.lang3.text

import org.junit.Assert.assertEquals
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Locale
import org.junit.Test

/**
  * Unit tests for {@link org.apache.commons.lang3.text.CompositeFormat}.
  */
@deprecated class CompositeFormatTest {
  /**
    * Ensures that the parse/format separation is correctly maintained.
    */
  @Test def testCompositeFormat(): Unit = {
    val parser = new Format() {
      override def format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition) =
        throw new UnsupportedOperationException("Not implemented")

      override def parseObject(source: String, pos: ParsePosition) = null // do nothing
    }
    val formatter = new Format() {
      override def format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition) = null

      override def parseObject(source: String, pos: ParsePosition) =
        throw new UnsupportedOperationException("Not implemented")
    }
    val composite = new CompositeFormat(parser, formatter)
    composite.parseObject("", null)
    composite.format(new AnyRef, new StringBuffer, null)
    assertEquals("Parser get method incorrectly implemented", parser, composite.getParser)
    assertEquals("Formatter get method incorrectly implemented", formatter, composite.getFormatter)
  }

  @Test
  @throws[Exception]
  def testUsage(): Unit = {
    val f1 = new SimpleDateFormat("MMddyyyy", Locale.ENGLISH)
    val f2 = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
    val c = new CompositeFormat(f1, f2)
    val testString = "January 3, 2005"
    assertEquals(testString, c.format(c.parseObject("01032005")))
    assertEquals(testString, c.reformat("01032005"))
  }
}
