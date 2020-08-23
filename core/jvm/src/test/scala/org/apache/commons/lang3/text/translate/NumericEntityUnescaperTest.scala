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

package org.apache.commons.lang3.text.translate

import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * Unit tests for {@link org.apache.commons.lang3.text.translate.NumericEntityUnescaper}.
  */
@deprecated
class NumericEntityUnescaperTest {
  @Test def testSupplementaryUnescaping(): Unit = {
    val neu = new NumericEntityUnescaper
    val input = "&#68642;"
    val expected = "\uD803\uDC22"
    val result = neu.translate(input)
    assertEquals("Failed to unescape numeric entities supplementary characters", expected, result)
  }

  @Test def testOutOfBounds(): Unit = {
    val neu = new NumericEntityUnescaper
    assertEquals("Failed to ignore when last character is &", "Test &", neu.translate("Test &"))
    assertEquals("Failed to ignore when last character is &", "Test &#", neu.translate("Test &#"))
    assertEquals("Failed to ignore when last character is &", "Test &#x", neu.translate("Test &#x"))
    assertEquals("Failed to ignore when last character is &", "Test &#X", neu.translate("Test &#X"))
  }

  @Test def testUnfinishedEntity(): Unit = { // parse it
    var neu = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional)
    var input = "Test &#x30 not test"
    var expected = "Test \u0030 not test"
    var result = neu.translate(input)
    assertEquals("Failed to support unfinished entities (i.e. missing semi-colon)", expected, result)

    // ignore it
    neu = new NumericEntityUnescaper
    input = "Test &#x30 not test"
    expected = input
    result = neu.translate(input)
    assertEquals("Failed to ignore unfinished entities (i.e. missing semi-colon)", expected, result)
    // fail it
    val failingNeu = new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon)
    val failingInput = "Test &#x30 not test"

    assertThrows[IllegalArgumentException](failingNeu.translate(failingInput))
    ()
  }
}
