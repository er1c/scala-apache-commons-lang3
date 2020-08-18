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

import org.scalatestplus.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
/**
  */
class ClassPathUtilsTest extends JUnitSuite {
//  @Test def testConstructor(): Unit = {
//    assertNotNull(new ClassPathUtils.type)
//    val cons = classOf[ClassPathUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[ClassPathUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[ClassPathUtils.type].getModifiers))
//  }

  @Test def testToFullyQualifiedNameNullClassString(): Unit = {
    assertThrows[NullPointerException](
      ClassPathUtils.toFullyQualifiedName(null.asInstanceOf[Class[_]], "Test.properties"))
    ()
  }

  @Test def testToFullyQualifiedNameClassNull(): Unit = {
    assertThrows[NullPointerException](ClassPathUtils.toFullyQualifiedName(ClassPathUtils.getClass, null))
    ()
  }

  @Test def testToFullyQualifiedNameClassString(): Unit = {
    val expected = "org.apache.commons.lang3.Test.properties"
    val actual = ClassPathUtils.toFullyQualifiedName(ClassPathUtils.getClass, "Test.properties")
    assertEquals(expected, actual)
  }

  @Test def testToFullyQualifiedNameNullPackageString(): Unit = {
    assertThrows[NullPointerException](
      ClassPathUtils.toFullyQualifiedName(null.asInstanceOf[Package], "Test.properties"))
    ()
  }

  @Test def testToFullyQualifiedNamePackageNull(): Unit = {
    assertThrows[NullPointerException](ClassPathUtils.toFullyQualifiedName(ClassPathUtils.getClass.getPackage, null))
    ()
  }

  @Test def testToFullyQualifiedNamePackageString(): Unit = {
    val expected = "org.apache.commons.lang3.Test.properties"
    val actual = ClassPathUtils.toFullyQualifiedName(ClassPathUtils.getClass.getPackage, "Test.properties")
    assertEquals(expected, actual)
  }

  @Test def testToFullyQualifiedPathClassNullString(): Unit = {
    assertThrows[NullPointerException](
      ClassPathUtils.toFullyQualifiedPath(null.asInstanceOf[Class[_]], "Test.properties"))
    ()
  }

  @Test def testToFullyQualifiedPathClassNull(): Unit = {
    assertThrows[NullPointerException](ClassPathUtils.toFullyQualifiedPath(ClassPathUtils.getClass, null))
    ()
  }

  @Test def testToFullyQualifiedPathClass(): Unit = {
    val expected = "org/apache/commons/lang3/Test.properties"
    val actual = ClassPathUtils.toFullyQualifiedPath(ClassPathUtils.getClass, "Test.properties")
    assertEquals(expected, actual)
  }

  @Test def testToFullyQualifiedPathPackageNullString(): Unit = {
    assertThrows[NullPointerException](
      ClassPathUtils.toFullyQualifiedPath(null.asInstanceOf[Package], "Test.properties"))
    ()
  }

  @Test def testToFullyQualifiedPathPackageNull(): Unit = {
    assertThrows[NullPointerException](ClassPathUtils.toFullyQualifiedPath(ClassPathUtils.getClass.getPackage, null))
    ()
  }

  @Test def testToFullyQualifiedPathPackage(): Unit = {
    val expected = "org/apache/commons/lang3/Test.properties"
    val actual = ClassPathUtils.toFullyQualifiedPath(ClassPathUtils.getClass.getPackage, "Test.properties")
    assertEquals(expected, actual)
  }
}
