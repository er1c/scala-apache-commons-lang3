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

//package org.apache.commons.lang3.text
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import java.util
//import java.util.Properties
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Test class for StrLookup.
//  */
//@deprecated class StrLookupTest extends JUnitSuite {
//  @Test def testNoneLookup() = {
//    assertNull(StrLookup.noneLookup.lookup(null))
//    assertNull(StrLookup.noneLookup.lookup(""))
//    assertNull(StrLookup.noneLookup.lookup("any"))
//  }
//
//  @Test def testSystemPropertiesLookup() = {
//    assertEquals(System.getProperty("os.name"), StrLookup.systemPropertiesLookup.lookup("os.name"))
//    assertNull(StrLookup.systemPropertiesLookup.lookup(""))
//    assertNull(StrLookup.systemPropertiesLookup.lookup("other"))
//    assertThrows(classOf[NullPointerException], () => StrLookup.systemPropertiesLookup.lookup(null))
//  }
//
//  /**
//    * Tests that a lookup object for system properties can deal with a full
//    * replacement of the system properties object. This test is related to
//    * LANG-1055.
//    */
//  @Test def testSystemPropertiesLookupReplacedProperties() = {
//    val oldProperties = System.getProperties
//    val osName = "os.name"
//    val newOsName = oldProperties.getProperty(osName) + "_changed"
//    val sysLookup = StrLookup.systemPropertiesLookup
//    val newProps = new Properties
//    newProps.setProperty(osName, newOsName)
//    System.setProperties(newProps)
//    try assertEquals(newOsName, sysLookup.lookup(osName), "Changed properties not detected")
//    finally System.setProperties(oldProperties)
//  }
//
//  /**
//    * Tests that a lookup object for system properties sees changes on system
//    * properties. This test is related to LANG-1141.
//    */
//  @Test def testSystemPropertiesLookupUpdatedProperty() = {
//    val osName = "os.name"
//    val oldOs = System.getProperty(osName)
//    val newOsName = oldOs + "_changed"
//    val sysLookup = StrLookup.systemPropertiesLookup
//    System.setProperty(osName, newOsName)
//    try assertEquals(newOsName, sysLookup.lookup(osName), "Changed properties not detected")
//    finally System.setProperty(osName, oldOs)
//  }
//
//  @Test def testMapLookup() = {
//    val map = new util.HashMap[String, AnyRef]
//    map.put("key", "value")
//    map.put("number", Integer.valueOf(2))
//    assertEquals("value", StrLookup.mapLookup(map).lookup("key"))
//    assertEquals("2", StrLookup.mapLookup(map).lookup("number"))
//    assertNull(StrLookup.mapLookup(map).lookup(null))
//    assertNull(StrLookup.mapLookup(map).lookup(""))
//    assertNull(StrLookup.mapLookup(map).lookup("other"))
//  }
//
//  @Test def testMapLookup_nullMap() = {
//    val map = null
//    assertNull(StrLookup.mapLookup(map).lookup(null))
//    assertNull(StrLookup.mapLookup(map).lookup(""))
//    assertNull(StrLookup.mapLookup(map).lookup("any"))
//  }
//}
