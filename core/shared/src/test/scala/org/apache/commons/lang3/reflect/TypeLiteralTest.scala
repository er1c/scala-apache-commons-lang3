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

//package org.apache.commons.lang3.reflect
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotEquals
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util
//import org.junit.Test
//
//class TypeLiteralTest {
//  @Test def testBasic() = {
//    assertTrue(TypeUtils.equals(classOf[String], new Nothing() {}.value))
//    assertTrue(TypeUtils.equals(TypeUtils.parameterize(classOf[util.List[_]], classOf[String]), new Nothing() {}.value))
//  }
//
//  @Test def testTyped() = {
//    val stringType = new Nothing() {}
//    assertTrue(TypeUtils.equals(classOf[String], stringType.getType))
//    val listOfStringType = new Nothing() {}
//    assertTrue(TypeUtils.equals(TypeUtils.parameterize(classOf[util.List[_]], classOf[String]), listOfStringType.getType))
//  }
//
//  @Test def testEquals() = {
//    assertEquals(new Nothing() {}, new Nothing() {})
//    assertEquals(new Nothing() {}, new Nothing() {})
//    assertNotEquals(new Nothing() {}, new Nothing() {})
//  }
//
//  @SuppressWarnings(Array("rawtypes"))
//  @Test def testRaw() = assertThrows(classOf[NullPointerException], () => new Nothing() {})
//}
