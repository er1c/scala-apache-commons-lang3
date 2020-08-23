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

import java.lang.{Boolean => JavaBoolean}
import java.util.Locale
import org.apache.commons.lang3.JavaVersion._
import org.junit.Assert._
import org.junit.Test

/**
  * Unit tests {@link org.apache.commons.lang3.SystemUtils}.
  *
  * Only limited testing can be performed.
  */
class SystemUtilsTest {
//  @Test def testConstructor(): Unit = {
//    assertNotNull(new SystemUtils.type)
//    val cons = classOf[SystemUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[SystemUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[SystemUtils.type].getModifiers))
//  }

  @Test def testGetEnvironmentVariableAbsent(): Unit = {
    val name = "THIS_ENV_VAR_SHOULD_NOT_EXIST_FOR_THIS_TEST_TO_PASS"
    val expected = System.getenv(name)
    assertNull(expected)
    val value = SystemUtils.getEnvironmentVariable(name, "DEFAULT")
    assertEquals("DEFAULT", value)
  }

  @Test def testGetEnvironmentVariablePresent(): Unit = {
    val name = "PATH"
    val expected = System.getenv(name)
    val value = SystemUtils.getEnvironmentVariable(name, null)
    assertEquals(expected, value)
  }

  @Test def testGetHostName(): Unit = {
    val hostName = SystemUtils.getHostName
    val expected =
      if (SystemUtils.IS_OS_WINDOWS) System.getenv("COMPUTERNAME")
      else System.getenv("HOSTNAME")
    assertEquals(expected, hostName)
  }

  /**
    * Assumes no security manager exists.
    */
  @Test def testGetJavaHome(): Unit = {
    val dir = SystemUtils.getJavaHome
    assertNotNull(dir)
    assertTrue(dir.exists)
  }

  @Test def testGetJavaIoTmpDir(): Unit = {
    val dir = SystemUtils.getJavaIoTmpDir
    assertNotNull(dir)
    assertTrue(dir.exists)
  }

  @Test def testGetUserDir(): Unit = {
    val dir = SystemUtils.getUserDir
    assertNotNull(dir)
    assertTrue(dir.exists)
  }

  @Test def testGetUserHome(): Unit = {
    val dir = SystemUtils.getUserHome
    assertNotNull(dir)
    assertTrue(dir.exists)
  }

  @Test def testGetUserName(): Unit = {
    assertEquals(System.getProperty("user.name"), SystemUtils.getUserName)
    // Don't overwrite the system property in this test in case something goes awfully wrong.
    assertEquals(System.getProperty("user.name", "foo"), SystemUtils.getUserName("foo"))
  }

  @Test
  @SuppressWarnings(Array("deprecation")) def testIS_JAVA(): Unit = {
    val javaVersion = SystemUtils.JAVA_VERSION
    if (javaVersion == null) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("1.8")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertTrue(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("9")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertTrue(SystemUtils.IS_JAVA_1_9)
      assertTrue(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("10")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertTrue(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("11")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertTrue(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("12")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertTrue(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
    } else if (javaVersion.startsWith("13")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertTrue(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("14")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertTrue(SystemUtils.IS_JAVA_14)
      assertFalse(SystemUtils.IS_JAVA_15)
    } else if (javaVersion.startsWith("15")) {
      assertFalse(SystemUtils.IS_JAVA_1_1)
      assertFalse(SystemUtils.IS_JAVA_1_2)
      assertFalse(SystemUtils.IS_JAVA_1_3)
      assertFalse(SystemUtils.IS_JAVA_1_4)
      assertFalse(SystemUtils.IS_JAVA_1_5)
      assertFalse(SystemUtils.IS_JAVA_1_6)
      assertFalse(SystemUtils.IS_JAVA_1_7)
      assertFalse(SystemUtils.IS_JAVA_1_8)
      assertFalse(SystemUtils.IS_JAVA_1_9)
      assertFalse(SystemUtils.IS_JAVA_9)
      assertFalse(SystemUtils.IS_JAVA_10)
      assertFalse(SystemUtils.IS_JAVA_11)
      assertFalse(SystemUtils.IS_JAVA_12)
      assertFalse(SystemUtils.IS_JAVA_13)
      assertFalse(SystemUtils.IS_JAVA_14)
      assertTrue(SystemUtils.IS_JAVA_15)
    } else System.out.println("Can't test IS_JAVA value: " + javaVersion)
  }

  @Test def testIS_OS(): Unit = {
    val osName = System.getProperty("os.name")
    if (osName == null) {
      assertFalse(SystemUtils.IS_OS_WINDOWS)
      assertFalse(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_SOLARIS)
      assertFalse(SystemUtils.IS_OS_LINUX)
      assertFalse(SystemUtils.IS_OS_MAC_OSX)
    } else if (osName.startsWith("Windows")) {
      assertFalse(SystemUtils.IS_OS_UNIX)
      assertTrue(SystemUtils.IS_OS_WINDOWS)
    } else if (osName.startsWith("Solaris")) {
      assertTrue(SystemUtils.IS_OS_SOLARIS)
      assertTrue(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_WINDOWS)
    } else if (osName.toLowerCase(Locale.ENGLISH).startsWith("linux")) {
      assertTrue(SystemUtils.IS_OS_LINUX)
      assertTrue(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_WINDOWS)
    } else if (osName.startsWith("Mac OS X")) {
      assertTrue(SystemUtils.IS_OS_MAC_OSX)
      assertTrue(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_WINDOWS)
    } else if (osName.startsWith("OS/2")) {
      assertTrue(SystemUtils.IS_OS_OS2)
      assertFalse(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_WINDOWS)
    } else if (osName.startsWith("SunOS")) {
      assertTrue(SystemUtils.IS_OS_SUN_OS)
      assertTrue(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_WINDOWS)
    } else if (osName.startsWith("FreeBSD")) {
      assertTrue(SystemUtils.IS_OS_FREE_BSD)
      assertTrue(SystemUtils.IS_OS_UNIX)
      assertFalse(SystemUtils.IS_OS_WINDOWS)
    } else System.out.println("Can't test IS_OS value: " + osName)
  }

  @Test def testIS_zOS(): Unit = {
    val osName = System.getProperty("os.name")
    if (osName == null) assertFalse(SystemUtils.IS_OS_ZOS)
    else if (osName.contains("z/OS")) {
      assertFalse(SystemUtils.IS_OS_WINDOWS)
      assertTrue(SystemUtils.IS_OS_ZOS)
    }
  }

  @Test def testJavaVersionMatches(): Unit = {
    var javaVersion: String = null
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = ""
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.0"
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.1"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.2"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.3.0"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.3.1"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.4.0"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.4.1"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.4.2"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.5.0"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.6.0"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.7.0"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "1.8.0"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
    javaVersion = "9"
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.0"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.1"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.2"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.3"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.4"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.5"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.6"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.7"))
    assertFalse(SystemUtils.isJavaVersionMatch(javaVersion, "1.8"))
    assertTrue(SystemUtils.isJavaVersionMatch(javaVersion, "9"))
  }

  @Test def testIsJavaVersionAtLeast() =
    if (SystemUtils.IS_JAVA_1_8) {
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_1))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_2))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_3))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_4))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_5))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_6))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_8))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_9))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_10))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_11))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_12))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_13))
    } else if (SystemUtils.IS_JAVA_9) {
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_1))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_2))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_3))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_4))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_5))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_6))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_9))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_10))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_11))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_12))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_13))
    } else if (SystemUtils.IS_JAVA_10) {
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_1))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_2))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_3))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_4))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_5))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_6))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_10))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_11))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_12))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_13))
    } else if (SystemUtils.IS_JAVA_11) {
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_1))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_2))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_3))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_4))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_5))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_6))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_11))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_12))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_13))
    } else if (SystemUtils.IS_JAVA_12) {
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_1))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_2))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_3))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_4))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_5))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_6))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_12))
      assertFalse(SystemUtils.isJavaVersionAtLeast(JAVA_13))
    } else if (SystemUtils.IS_JAVA_13) {
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_1))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_2))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_3))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_4))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_5))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_6))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtLeast(JAVA_13))
    }

  @Test def testIsJavaVersionAtMost() =
    if (SystemUtils.IS_JAVA_1_8) {
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_1))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_2))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_3))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_4))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_5))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_6))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_7))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_13))
    } else if (SystemUtils.IS_JAVA_9) {
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_1))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_2))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_3))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_4))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_5))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_6))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_7))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_8))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_13))
    } else if (SystemUtils.IS_JAVA_10) {
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_1))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_2))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_3))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_4))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_5))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_6))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_7))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_8))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_9))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_13))
    } else if (SystemUtils.IS_JAVA_11) {
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_1))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_2))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_3))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_4))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_5))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_6))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_7))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_8))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_9))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_10))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_13))
    } else if (SystemUtils.IS_JAVA_12) {
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_1))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_2))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_3))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_4))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_5))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_6))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_7))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_8))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_9))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_10))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_11))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_13))
    } else if (SystemUtils.IS_JAVA_13) {
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_1))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_2))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_3))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_4))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_5))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_6))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_7))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_1_8))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_9))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_10))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_11))
      assertFalse(SystemUtils.isJavaVersionAtMost(JAVA_12))
      assertTrue(SystemUtils.isJavaVersionAtMost(JAVA_13))
    }

  @Test def testOSMatchesName(): Unit = {
    var osName: String = null
    assertFalse(SystemUtils.isOSNameMatch(osName, "Windows"))
    osName = ""
    assertFalse(SystemUtils.isOSNameMatch(osName, "Windows"))
    osName = "Windows 95"
    assertTrue(SystemUtils.isOSNameMatch(osName, "Windows"))
    osName = "Windows NT"
    assertTrue(SystemUtils.isOSNameMatch(osName, "Windows"))
    osName = "OS/2"
    assertFalse(SystemUtils.isOSNameMatch(osName, "Windows"))
  }

  @Test def testOSMatchesNameAndVersion(): Unit = {
    var osName: String = null
    var osVersion: String = null
    assertFalse(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
    osName = ""
    osVersion = ""
    assertFalse(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
    osName = "Windows 95"
    osVersion = "4.0"
    assertFalse(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
    osName = "Windows 95"
    osVersion = "4.1"
    assertTrue(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
    osName = "Windows 98"
    osVersion = "4.1"
    assertTrue(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
    osName = "Windows NT"
    osVersion = "4.0"
    assertFalse(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
    osName = "OS/2"
    osVersion = "4.0"
    assertFalse(SystemUtils.isOSMatch(osName, osVersion, "Windows 9", "4.1"))
  }

  @Test def testOsVersionMatches(): Unit = {
    var osVersion: String = null
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    osVersion = ""
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    osVersion = "10"
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.1.1"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.10"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.10.1"))
    osVersion = "10.1"
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.1.1"))
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.10"))
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.10.1"))
    osVersion = "10.1.1"
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.1.1"))
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.10"))
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.10.1"))
    osVersion = "10.10"
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.1.1"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.10"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.10.1"))
    osVersion = "10.10.1"
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.1"))
    assertFalse(SystemUtils.isOSVersionMatch(osVersion, "10.1.1"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.10"))
    assertTrue(SystemUtils.isOSVersionMatch(osVersion, "10.10.1"))
  }

  @Test def testJavaAwtHeadless(): Unit = {
    val expectedStringValue = System.getProperty("java.awt.headless")
    val expectedStringValueWithDefault = System.getProperty("java.awt.headless", "false")
    assertNotNull(expectedStringValueWithDefault)
    val expectedValue = JavaBoolean.valueOf(expectedStringValue).booleanValue
    if (expectedStringValue != null) assertEquals(expectedStringValue, SystemUtils.JAVA_AWT_HEADLESS)
    assertEquals(expectedValue, SystemUtils.isJavaAwtHeadless)
    assertEquals(expectedStringValueWithDefault, "" + SystemUtils.isJavaAwtHeadless)
  }
}
