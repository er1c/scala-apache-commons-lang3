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

import java.util
import org.junit.Assert._
import org.junit.Test

/**
  * Unit tests for {@link org.apache.commons.lang3.text.translate.EntityArrays}.
  */
@deprecated
class EntityArraysTest {
//  @Test def testConstructorExists(): Unit = {
//    new EntityArrays.type
//  }

  // LANG-659 - check arrays for duplicate entries
  @Test def testHTML40_EXTENDED_ESCAPE(): Unit = {
    val col0 = new util.HashSet[CharSequence]
    val col1 = new util.HashSet[CharSequence]
    val sa = EntityArrays.HTML40_EXTENDED_ESCAPE

    for (i <- 0 until sa.length) {
      assertTrue(
        "Already added entry 0: " + i + " " + sa(i)(0),
        col0.add(sa(i)(0))
      )
      assertTrue(
        "Already added entry 1: " + i + " " + sa(i)(1),
        col1.add(sa(i)(1))
      )
    }
  }

  // LANG-658 - check arrays for duplicate entries
  @Test def testISO8859_1_ESCAPE(): Unit = {
    val col0 = new util.HashSet[CharSequence]
    val col1 = new util.HashSet[CharSequence]
    val sa = EntityArrays.ISO8859_1_ESCAPE
    var success = true

    for (i <- 0 until sa.length) {
      val add0 = col0.add(sa(i)(0))
      val add1 = col1.add(sa(i)(1))
      if (!add0) {
        success = false
        System.out.println("Already added entry 0: " + i + " " + sa(i)(0) + " " + sa(i)(1))
      }
      if (!add1) {
        success = false
        System.out.println("Already added entry 1: " + i + " " + sa(i)(0) + " " + sa(i)(1))
      }
    }
    assertTrue("One or more errors detected", success)
  }
}
