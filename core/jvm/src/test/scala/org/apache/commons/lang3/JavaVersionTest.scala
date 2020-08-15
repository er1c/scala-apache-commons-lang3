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

import org.apache.commons.lang3.JavaVersion._
import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.JavaVersion}.
  */
class JavaVersionTest extends JUnitSuite {
  @Test def testGetJavaVersion(): Unit = {
    assertEquals("0.9 failed", JAVA_0_9, get("0.9"))
    assertEquals("1.1 failed", JAVA_1_1, get("1.1"))
    assertEquals("1.2 failed", JAVA_1_2, get("1.2"))
    assertEquals("1.3 failed", JAVA_1_3, get("1.3"))
    assertEquals("1.4 failed", JAVA_1_4, get("1.4"))
    assertEquals("1.5 failed", JAVA_1_5, get("1.5"))
    assertEquals("1.6 failed", JAVA_1_6, get("1.6"))
    assertEquals("1.7 failed", JAVA_1_7, get("1.7"))
    assertEquals("1.8 failed", JAVA_1_8, get("1.8"))
    assertEquals("9 failed", JAVA_9, get("9"))
    assertEquals("10 failed", JAVA_10, get("10"))
    assertEquals("10 failed", JAVA_11, get("11"))
    assertEquals("10 failed", JAVA_12, get("12"))
    assertEquals("10 failed", JAVA_13, get("13"))
    assertEquals("10 failed", JAVA_14, get("14"))
    assertEquals("10 failed", JAVA_15, get("15"))
    assertEquals("10 failed", JAVA_16, get("16"))
    assertEquals("1.10 failed", JAVA_RECENT, get("1.10"))
    // assertNull(get("2.10"), "2.10 unexpectedly worked");
    assertEquals("Wrapper method failed", get("1.5"), getJavaVersion("1.5"))
    assertEquals("Unhandled", JAVA_RECENT, get("17")) // LANG-1384
  }

  @Test def testAtLeast(): Unit = {
    assertFalse("1.2 at least 1.5 passed", JAVA_1_2.atLeast(JAVA_1_5))
    assertTrue("1.5 at least 1.2 failed", JAVA_1_5.atLeast(JAVA_1_2))
    assertFalse("1.6 at least 1.7 passed", JAVA_1_6.atLeast(JAVA_1_7))
    assertTrue("0.9 at least 1.5 failed", JAVA_0_9.atLeast(JAVA_1_5))
    assertFalse("0.9 at least 1.6 passed", JAVA_0_9.atLeast(JAVA_1_6))
  }

  @Test def testToString(): Unit = assertEquals("1.2", JAVA_1_2.toString)
}
