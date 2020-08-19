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

package org.apache.commons.lang3.math

import java.lang.{Double => JavaDouble, Float => JavaFloat}
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.math.IEEE754rUtils}.
  */
class IEEE754rUtilsTest extends JUnitSuite {
  @Test def testLang381(): Unit = {
    assertEquals(1.2, IEEE754rUtils.min(1.2, 2.5, Double.NaN), 0.01)
    assertEquals(2.5, IEEE754rUtils.max(1.2, 2.5, Double.NaN), 0.01)
    assertTrue(JavaDouble.isNaN(IEEE754rUtils.max(Double.NaN, Double.NaN, Double.NaN)))
    assertEquals(1.2f, IEEE754rUtils.min(1.2f, 2.5f, Float.NaN), 0.01)
    assertEquals(2.5f, IEEE754rUtils.max(1.2f, 2.5f, Float.NaN), 0.01)
    assertTrue(JavaFloat.isNaN(IEEE754rUtils.max(Float.NaN, Float.NaN, Float.NaN)))
    val a: Array[Double] = Array[Double](1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN)
    assertEquals(42.0, IEEE754rUtils.max(a), 0.01)
    assertEquals(1.2, IEEE754rUtils.min(a), 0.01)
    val b = Array[Double](Double.NaN, 1.2, Double.NaN, 3.7, 27.0, 42.0, Double.NaN)
    assertEquals(42.0, IEEE754rUtils.max(b), 0.01)
    assertEquals(1.2, IEEE754rUtils.min(b), 0.01)
    val aF = Array[Float](1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN)
    assertEquals(1.2f, IEEE754rUtils.min(aF), 0.01)
    assertEquals(42.0f, IEEE754rUtils.max(aF), 0.01)
    val bF = Array[Float](Float.NaN, 1.2f, Float.NaN, 3.7f, 27.0f, 42.0f, Float.NaN)
    assertEquals(1.2f, IEEE754rUtils.min(bF), 0.01)
    assertEquals(42.0f, IEEE754rUtils.max(bF), 0.01)
  }

  @Test def testEnforceExceptions(): Unit = {
    assertThrows[NullPointerException](
      IEEE754rUtils.min(null.asInstanceOf[Array[Float]])
    ) //, "IllegalArgumentException expected for null input")
    assertThrows[IllegalArgumentException](IEEE754rUtils.min()) // "IllegalArgumentException expected for empty input")
    assertThrows[NullPointerException](
      IEEE754rUtils.max(null.asInstanceOf[Array[Float]])
    ) //, "IllegalArgumentException expected for null input")
    assertThrows[IllegalArgumentException](IEEE754rUtils.max()) //, "IllegalArgumentException expected for empty input")
    assertThrows[NullPointerException](
      IEEE754rUtils.min(null.asInstanceOf[Array[Double]])
    ) //, "IllegalArgumentException expected for null input")
    assertThrows[IllegalArgumentException](IEEE754rUtils.min()) //, "IllegalArgumentException expected for empty input")
    assertThrows[NullPointerException](
      IEEE754rUtils.max(null.asInstanceOf[Array[Double]])
    ) //, "IllegalArgumentException expected for null input")
    assertThrows[IllegalArgumentException](IEEE754rUtils.max()) //, "IllegalArgumentException expected for empty input")
    ()
  }

//  @Test def testConstructorExists() = new IEEE754rUtils.type
}
