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

import org.scalatestplus.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test

/**
  * Tests {@link org.apache.commons.lang3.builder.HashCodeBuilder} and
  * {@link org.apache.commons.lang3.builder.EqualsBuilderTest} to insure that equal
  * objects must have equal hash codes.
  */
object HashCodeBuilderAndEqualsBuilderTest {

  private[builder] class TestFixture private[builder] (var i: Int, var c: Char, var string: String, var s: Short) {}

  private[builder] class SubTestFixture private[builder] (
    i: Int,
    c: Char,
    string: String,
    s: Short,
    val tString: String)
    extends HashCodeBuilderAndEqualsBuilderTest.TestFixture(i, c, string, s) {}

  private[builder] class AllTransientFixture private[builder] (
    var i: Int,
    var c: Char,
    var string: String,
    var s: Short) {}

  private[builder] class SubAllTransientFixture private[builder] (
    i: Int,
    c: Char,
    string: String,
    s: Short,
    override val toString: String)
    extends HashCodeBuilderAndEqualsBuilderTest.AllTransientFixture(i, c, string, s) {}

}

class HashCodeBuilderAndEqualsBuilderTest extends JUnitSuite {
  private def testInteger(testTransients: Boolean): Unit = {
    val i1 = Integer.valueOf(12345)
    val i2 = Integer.valueOf(12345)
    assertEqualsAndHashCodeContract(i1, i2, testTransients)
  }

  @Test def testInteger(): Unit = {
    testInteger(false)
  }

  @Test def testIntegerWithTransients(): Unit = {
    testInteger(true)
  }

  @Test def testFixture(): Unit = {
    testFixture(false)
  }

  @Test def testFixtureWithTransients(): Unit = {
    testFixture(true)
  }

  private def testFixture(testTransients: Boolean): Unit = {
    assertEqualsAndHashCodeContract(
      new HashCodeBuilderAndEqualsBuilderTest.TestFixture(2, 'c', "Test", 2.toShort),
      new HashCodeBuilderAndEqualsBuilderTest.TestFixture(2, 'c', "Test", 2.toShort),
      testTransients
    )
    assertEqualsAndHashCodeContract(
      new HashCodeBuilderAndEqualsBuilderTest.AllTransientFixture(2, 'c', "Test", 2.toShort),
      new HashCodeBuilderAndEqualsBuilderTest.AllTransientFixture(2, 'c', "Test", 2.toShort),
      testTransients
    )
    assertEqualsAndHashCodeContract(
      new HashCodeBuilderAndEqualsBuilderTest.SubTestFixture(2, 'c', "Test", 2.toShort, "Same"),
      new HashCodeBuilderAndEqualsBuilderTest.SubTestFixture(2, 'c', "Test", 2.toShort, "Same"),
      testTransients
    )
    assertEqualsAndHashCodeContract(
      new HashCodeBuilderAndEqualsBuilderTest.SubAllTransientFixture(2, 'c', "Test", 2.toShort, "Same"),
      new HashCodeBuilderAndEqualsBuilderTest.SubAllTransientFixture(2, 'c', "Test", 2.toShort, "Same"),
      testTransients
    )
  }

  /**
    * Asserts that if {@code lhs} equals {@code rhs}
    * then their hash codes MUST be identical.
    *
    * @param lhs            The Left-Hand-Side of the equals test
    * @param rhs            The Right-Hand-Side of the equals test
    * @param testTransients whether to test transient fields
    */
  private def assertEqualsAndHashCodeContract(lhs: Any, rhs: Any, testTransients: Boolean): Unit = {
    if (EqualsBuilder.reflectionEquals(lhs, rhs, testTransients)) { // test a couple of times for consistency.
      assertEquals(
        HashCodeBuilder.reflectionHashCode(lhs, testTransients),
        HashCodeBuilder.reflectionHashCode(rhs, testTransients))
      assertEquals(
        HashCodeBuilder.reflectionHashCode(lhs, testTransients),
        HashCodeBuilder.reflectionHashCode(rhs, testTransients))
      assertEquals(
        HashCodeBuilder.reflectionHashCode(lhs, testTransients),
        HashCodeBuilder.reflectionHashCode(rhs, testTransients))
    }
  }
}
