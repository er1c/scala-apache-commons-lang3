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

//package org.apache.commons.lang3.concurrent
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertTrue
//import java.util.regex.Pattern
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
///**
//  * Test class for {@code ConstantInitializer}.
//  */
//object ConstantInitializerTest {
//  /** Constant for the object managed by the initializer. */
//    private val VALUE = 42
//}
//
//class ConstantInitializerTest {
//  /** The initializer to be tested. */
//    private var init = null
//
//  @BeforeEach def setUp() = init = new Nothing(ConstantInitializerTest.VALUE)
//
//  /**
//    * Helper method for testing equals() and hashCode().
//    *
//    * @param obj      the object to compare with the test instance
//    * @param expected the expected result
//    */
//  private def checkEquals(obj: Any, expected: Boolean) = {
//    assertEquals(expected, init.equals(obj), "Wrong result of equals")
//    if (obj != null) {
//      assertEquals(expected, obj == init, "Not symmetric")
//      if (expected) assertEquals(init.hashCode, obj.hashCode, "Different hash codes")
//    }
//  }
//
//  /**
//    * Tests whether the correct object is returned.
//    */
//  @Test def testGetObject() = assertEquals(ConstantInitializerTest.VALUE, init.getObject, "Wrong object")
//
//  /**
//    * Tests whether get() returns the correct object.
//    *
//    * @throws org.apache.commons.lang3.concurrent.ConcurrentException so we don't have to catch it
//    */
//  @Test
//  @throws[ConcurrentException]
//  def testGet() = assertEquals(ConstantInitializerTest.VALUE, init.get, "Wrong object")
//
//  /**
//    * Tests equals() if the expected result is true.
//    */
//  @Test def testEqualsTrue() = {
//    checkEquals(init, true)
//    var init2 = new Nothing(Integer.valueOf(ConstantInitializerTest.VALUE.intValue))
//    checkEquals(init2, true)
//    init = new Nothing(null)
//    init2 = new Nothing(null)
//    checkEquals(init2, true)
//  }
//
//  /**
//    * Tests equals() if the expected result is false.
//    */
//  @Test def testEqualsFalse() = {
//    var init2 = new Nothing(null)
//    checkEquals(init2, false)
//    init2 = new Nothing(ConstantInitializerTest.VALUE + 1)
//    checkEquals(init2, false)
//  }
//
//  /**
//    * Tests equals() with objects of other classes.
//    */
//  @Test def testEqualsWithOtherObjects() = {
//    checkEquals(null, false)
//    checkEquals(this, false)
//    checkEquals(new Nothing("Test"), false)
//  }
//
//  /**
//    * Tests the string representation.
//    */
//  @Test def testToString() = {
//    val s = init.toString
//    val pattern = Pattern.compile("ConstantInitializer@-?\\d+ \\[ object = " + ConstantInitializerTest.VALUE + " \\]")
//    assertTrue(pattern.matcher(s).matches, "Wrong string: " + s)
//  }
//
//  /**
//    * Tests the string representation if the managed object is null.
//    */
//  @Test def testToStringNull() = {
//    val s = new Nothing(null).toString
//    assertTrue(s.indexOf("object = null") > 0, "Object not found: " + s)
//  }
//}
