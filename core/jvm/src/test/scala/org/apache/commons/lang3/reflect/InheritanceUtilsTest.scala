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
//import org.junit.Assert.assertNotNull
//import org.apache.commons.lang3.reflect.testbed.AnotherChild
//import org.apache.commons.lang3.reflect.testbed.AnotherParent
//import org.apache.commons.lang3.reflect.testbed.Grandchild
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests InheritanceUtils
//  */
//class InheritanceUtilsTest extends JUnitSuite {
//  @Test
//  @throws[Exception]
//  def testConstructor() = assertNotNull(classOf[Nothing].newInstance)
//
//  @Test def testDistanceGreaterThanZero() = {
//    assertEquals(1, InheritanceUtils.distance(classOf[AnotherChild], classOf[AnotherParent]))
//    assertEquals(1, InheritanceUtils.distance(classOf[Grandchild], classOf[AnotherChild]))
//    assertEquals(2, InheritanceUtils.distance(classOf[Grandchild], classOf[AnotherParent]))
//    assertEquals(3, InheritanceUtils.distance(classOf[Grandchild], classOf[Any]))
//  }
//
//  @Test def testDistanceEqual() = assertEquals(0, InheritanceUtils.distance(classOf[AnotherChild], classOf[AnotherChild]))
//
//  @Test def testDistanceEqualObject() = assertEquals(0, InheritanceUtils.distance(classOf[Any], classOf[Any]))
//
//  @Test def testDistanceNullChild() = assertEquals(-1, InheritanceUtils.distance(null, classOf[Any]))
//
//  @Test def testDistanceNullParent() = assertEquals(-1, InheritanceUtils.distance(classOf[Any], null))
//
//  @Test def testDistanceNullParentNullChild() = assertEquals(-1, InheritanceUtils.distance(null, null))
//
//  @Test def testDistanceDisjoint() = assertEquals(-1, InheritanceUtils.distance(classOf[Boolean], classOf[String]))
//
//  @Test def testDistanceReverseParentChild() = assertEquals(-1, InheritanceUtils.distance(classOf[Any], classOf[Grandchild]))
//}
