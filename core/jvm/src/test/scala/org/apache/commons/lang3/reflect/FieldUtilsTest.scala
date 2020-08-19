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
//import org.junit.Assert.assertArrayEquals
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import org.junit.Assert.fail
//import org.junit.jupiter.api.Assumptions.assumeTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Field
//import java.lang.reflect.Modifier
//import java.util
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.JavaVersion
//import org.apache.commons.lang3.SystemUtils
//import org.apache.commons.lang3.reflect.testbed.Ambig
//import org.apache.commons.lang3.reflect.testbed.Annotated
//import org.apache.commons.lang3.reflect.testbed.Foo
//import org.apache.commons.lang3.reflect.testbed.PrivatelyShadowedChild
//import org.apache.commons.lang3.reflect.testbed.PublicChild
//import org.apache.commons.lang3.reflect.testbed.PubliclyShadowedChild
//import org.apache.commons.lang3.reflect.testbed.StaticContainer
//import org.apache.commons.lang3.reflect.testbed.StaticContainerChild
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
///**
//  * Unit tests FieldUtils
//  */
//object FieldUtilsTest {
//  private val JACOCO_DATA_FIELD_NAME = "$jacocoData"
//  private[reflect] val I0 = Integer.valueOf(0)
//  private[reflect] val I1 = Integer.valueOf(1)
//  private[reflect] val D0 = Double.valueOf(0.0)
//  private[reflect] val D1 = Double.valueOf(1.0)
//}
//
//class FieldUtilsTest {
//  @Annotated private var publicChild = null
//  private var publiclyShadowedChild = null
//  @Annotated private var privatelyShadowedChild = null
//  final private val parentClass = classOf[PublicChild].getSuperclass
//
//  @BeforeEach def setUp() = {
//    StaticContainer.reset()
//    publicChild = new PublicChild
//    publiclyShadowedChild = new PubliclyShadowedChild
//    privatelyShadowedChild = new PrivatelyShadowedChild
//  }
//
//  @Test def testConstructor() = {
//    assertNotNull(new Nothing)
//    val cons = classOf[Nothing].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test def testGetField() = {
//    assertEquals(classOf[Foo], FieldUtils.getField(classOf[PublicChild], "VALUE").getDeclaringClass)
//    assertEquals(parentClass, FieldUtils.getField(classOf[PublicChild], "s").getDeclaringClass)
//    assertNull(FieldUtils.getField(classOf[PublicChild], "b"))
//    assertNull(FieldUtils.getField(classOf[PublicChild], "i"))
//    assertNull(FieldUtils.getField(classOf[PublicChild], "d"))
//    assertEquals(classOf[Foo], FieldUtils.getField(classOf[PubliclyShadowedChild], "VALUE").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "s").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "b").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "i").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "d").getDeclaringClass)
//    assertEquals(classOf[Foo], FieldUtils.getField(classOf[PrivatelyShadowedChild], "VALUE").getDeclaringClass)
//    assertEquals(parentClass, FieldUtils.getField(classOf[PrivatelyShadowedChild], "s").getDeclaringClass)
//    assertNull(FieldUtils.getField(classOf[PrivatelyShadowedChild], "b"))
//    assertNull(FieldUtils.getField(classOf[PrivatelyShadowedChild], "i"))
//    assertNull(FieldUtils.getField(classOf[PrivatelyShadowedChild], "d"))
//  }
//
//  @Test def testGetFieldIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.getField(null, "none"))
//
//  @Test def testGetFieldIllegalArgumentException2() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[PublicChild], null))
//
//  @Test def testGetFieldIllegalArgumentException3() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[PublicChild], ""))
//
//  @Test def testGetFieldIllegalArgumentException4() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[PublicChild], " "))
//
//  @Test def testGetFieldForceAccess() = {
//    assertEquals(classOf[PublicChild], FieldUtils.getField(classOf[PublicChild], "VALUE", true).getDeclaringClass)
//    assertEquals(parentClass, FieldUtils.getField(classOf[PublicChild], "s", true).getDeclaringClass)
//    assertEquals(parentClass, FieldUtils.getField(classOf[PublicChild], "b", true).getDeclaringClass)
//    assertEquals(parentClass, FieldUtils.getField(classOf[PublicChild], "i", true).getDeclaringClass)
//    assertEquals(parentClass, FieldUtils.getField(classOf[PublicChild], "d", true).getDeclaringClass)
//    assertEquals(classOf[Foo], FieldUtils.getField(classOf[PubliclyShadowedChild], "VALUE", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "s", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "b", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "i", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getField(classOf[PubliclyShadowedChild], "d", true).getDeclaringClass)
//    assertEquals(classOf[Foo], FieldUtils.getField(classOf[PrivatelyShadowedChild], "VALUE", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getField(classOf[PrivatelyShadowedChild], "s", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getField(classOf[PrivatelyShadowedChild], "b", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getField(classOf[PrivatelyShadowedChild], "i", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getField(classOf[PrivatelyShadowedChild], "d", true).getDeclaringClass)
//  }
//
//  @Test def testGetFieldForceAccessIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.getField(null, "none", true))
//
//  @Test def testGetFieldForceAccessIllegalArgumentException2() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[PublicChild], null, true))
//
//  @Test def testGetFieldForceAccessIllegalArgumentException3() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[PublicChild], "", true))
//
//  @Test def testGetFieldForceAccessIllegalArgumentException4() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[PublicChild], " ", true))
//
//  @Test def testGetAllFields() = {
//    assertArrayEquals(new Array[Field](0), FieldUtils.getAllFields(classOf[Any]))
//    val fieldsNumber = classOf[Number].getDeclaredFields
//    assertArrayEquals(fieldsNumber, FieldUtils.getAllFields(classOf[Number]))
//    val fieldsInteger = classOf[Integer].getDeclaredFields
//    assertArrayEquals(ArrayUtils.addAll(fieldsInteger, fieldsNumber), FieldUtils.getAllFields(classOf[Integer]))
//    val allFields = FieldUtils.getAllFields(classOf[PublicChild])
//    // Under Jacoco,0.8.1 and Java 10, the field count is 7.
//    var expected = 5
//    for (field <- allFields) {
//      if (field.getName == FieldUtilsTest.JACOCO_DATA_FIELD_NAME) expected += 1
//    }
//    assertEquals(expected, allFields.length, util.Arrays.toString(allFields))
//  }
//
//  @Test def testGetAllFieldsList() = {
//    assertEquals(0, FieldUtils.getAllFieldsList(classOf[Any]).size)
//    val fieldsNumber = util.Arrays.asList(classOf[Number].getDeclaredFields)
//    assertEquals(fieldsNumber, FieldUtils.getAllFieldsList(classOf[Number]))
//    val fieldsInteger = util.Arrays.asList(classOf[Integer].getDeclaredFields)
//    val allFieldsInteger = new util.ArrayList[Field](fieldsInteger)
//    allFieldsInteger.addAll(fieldsNumber)
//    assertEquals(new util.HashSet[_](allFieldsInteger), new util.HashSet[_](FieldUtils.getAllFieldsList(classOf[Integer])))
//    val allFields = FieldUtils.getAllFieldsList(classOf[PublicChild])
//    var expected = 5
//    import scala.collection.JavaConversions._
//    for (field <- allFields) {
//      if (field.getName == FieldUtilsTest.JACOCO_DATA_FIELD_NAME) expected += 1
//    }
//    assertEquals(expected, allFields.size, allFields.toString)
//  }
//
//  @Test
//  @throws[NoSuchFieldException]
//  def testGetFieldsWithAnnotation() = {
//    assertArrayEquals(new Array[Field](0), FieldUtils.getFieldsWithAnnotation(classOf[Any], classOf[Annotated]))
//    val annotatedFields = Array[Field](classOf[FieldUtilsTest].getDeclaredField("publicChild"), classOf[FieldUtilsTest].getDeclaredField("privatelyShadowedChild"))
//    assertArrayEquals(annotatedFields, FieldUtils.getFieldsWithAnnotation(classOf[FieldUtilsTest], classOf[Annotated]))
//  }
//
//  @Test def testGetFieldsWithAnnotationIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.getFieldsWithAnnotation(classOf[FieldUtilsTest], null))
//
//  @Test def testGetFieldsWithAnnotationIllegalArgumentException2() = assertThrows(classOf[NullPointerException], () => FieldUtils.getFieldsWithAnnotation(null, classOf[Annotated]))
//
//  @Test def testGetFieldsWithAnnotationIllegalArgumentException3() = assertThrows(classOf[NullPointerException], () => FieldUtils.getFieldsWithAnnotation(null, null))
//
//  @Test
//  @throws[NoSuchFieldException]
//  def testGetFieldsListWithAnnotation() = {
//    assertEquals(0, FieldUtils.getFieldsListWithAnnotation(classOf[Any], classOf[Annotated]).size)
//    val annotatedFields = util.Arrays.asList(classOf[FieldUtilsTest].getDeclaredField("publicChild"), classOf[FieldUtilsTest].getDeclaredField("privatelyShadowedChild"))
//    val fieldUtilsTestAnnotatedFields = FieldUtils.getFieldsListWithAnnotation(classOf[FieldUtilsTest], classOf[Annotated])
//    assertEquals(annotatedFields.size, fieldUtilsTestAnnotatedFields.size)
//    assertTrue(fieldUtilsTestAnnotatedFields.contains(annotatedFields.get(0)))
//    assertTrue(fieldUtilsTestAnnotatedFields.contains(annotatedFields.get(1)))
//  }
//
//  @Test def testGetFieldsListWithAnnotationIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.getFieldsListWithAnnotation(classOf[FieldUtilsTest], null))
//
//  @Test def testGetFieldsListWithAnnotationIllegalArgumentException2() = assertThrows(classOf[NullPointerException], () => FieldUtils.getFieldsListWithAnnotation(null, classOf[Annotated]))
//
//  @Test def testGetFieldsListWithAnnotationIllegalArgumentException3() = assertThrows(classOf[NullPointerException], () => FieldUtils.getFieldsListWithAnnotation(null, null))
//
//  @Test def testGetDeclaredField() = {
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "VALUE"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "s"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "b"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "i"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "d"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "VALUE"))
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "s").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "b").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "i").getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "d").getDeclaringClass)
//    assertNull(FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "VALUE"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "s"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "b"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "i"))
//    assertNull(FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "d"))
//  }
//
//  @Test def testGetDeclaredFieldAccessIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.getDeclaredField(null, "none"))
//
//  @Test def testGetDeclaredFieldAccessIllegalArgumentException2() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getDeclaredField(classOf[PublicChild], null))
//
//  @Test def testGetDeclaredFieldAccessIllegalArgumentException3() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getDeclaredField(classOf[PublicChild], ""))
//
//  @Test def testGetDeclaredFieldAccessIllegalArgumentException4() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getDeclaredField(classOf[PublicChild], " "))
//
//  @Test def testGetDeclaredFieldForceAccess() = {
//    assertEquals(classOf[PublicChild], FieldUtils.getDeclaredField(classOf[PublicChild], "VALUE", true).getDeclaringClass)
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "s", true))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "b", true))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "i", true))
//    assertNull(FieldUtils.getDeclaredField(classOf[PublicChild], "d", true))
//    assertNull(FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "VALUE", true))
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "s", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "b", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "i", true).getDeclaringClass)
//    assertEquals(classOf[PubliclyShadowedChild], FieldUtils.getDeclaredField(classOf[PubliclyShadowedChild], "d", true).getDeclaringClass)
//    assertNull(FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "VALUE", true))
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "s", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "b", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "i", true).getDeclaringClass)
//    assertEquals(classOf[PrivatelyShadowedChild], FieldUtils.getDeclaredField(classOf[PrivatelyShadowedChild], "d", true).getDeclaringClass)
//  }
//
//  @Test def testGetDeclaredFieldForceAccessIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.getDeclaredField(null, "none", true))
//
//  @Test def testGetDeclaredFieldForceAccessIllegalArgumentException2() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getDeclaredField(classOf[PublicChild], null, true))
//
//  @Test def testGetDeclaredFieldForceAccessIllegalArgumentException3() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getDeclaredField(classOf[PublicChild], "", true))
//
//  @Test def testGetDeclaredFieldForceAccessIllegalArgumentException4() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getDeclaredField(classOf[PublicChild], " ", true))
//
//  @Test
//  @throws[Exception]
//  def testReadStaticField() = assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(classOf[Foo], "VALUE")))
//
//  @Test def testReadStaticFieldIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.readStaticField(null))
//
//  @Test
//  @throws[Exception]
//  def testReadStaticFieldIllegalArgumentException2() = {
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(classOf[Foo], "VALUE")))
//    val nonStaticField = FieldUtils.getField(classOf[PublicChild], "s")
//    assumeTrue(nonStaticField != null)
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(nonStaticField))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadStaticFieldForceAccess() = {
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(classOf[Foo], "VALUE")))
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(FieldUtils.getField(classOf[PublicChild], "VALUE")))
//  }
//
//  @Test def testReadStaticFieldForceAccessIllegalArgumentException1() = assertThrows(classOf[NullPointerException], () => FieldUtils.readStaticField(null, true))
//
//  @Test def testReadStaticFieldForceAccessIllegalArgumentException2() = {
//    val nonStaticField = FieldUtils.getField(classOf[PublicChild], "s", true)
//    assumeTrue(nonStaticField != null)
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(nonStaticField))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadNamedStaticField() = {
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[Foo], "VALUE"))
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[PubliclyShadowedChild], "VALUE"))
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[PrivatelyShadowedChild], "VALUE"))
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[PublicChild], "VALUE"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readStaticField(null, "none"), "null class should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[Foo], null), "null field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[Foo], ""), "empty field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[Foo], " "), "blank field name should cause an IllegalArgumentException")
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readStaticField(classOf[Foo], "does_not_exist"), "a field that doesn't exist should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[PublicChild], "s"), "non-static field should cause an IllegalArgumentException")
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadNamedStaticFieldForceAccess() = {
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[Foo], "VALUE", true))
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[PubliclyShadowedChild], "VALUE", true))
//    assertEquals(Foo.VALUE, FieldUtils.readStaticField(classOf[PrivatelyShadowedChild], "VALUE", true))
//    assertEquals("child", FieldUtils.readStaticField(classOf[PublicChild], "VALUE", true))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readStaticField(null, "none", true), "null class should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[Foo], null, true), "null field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[Foo], "", true), "empty field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[Foo], " ", true), "blank field name should cause an IllegalArgumentException")
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readStaticField(classOf[Foo], "does_not_exist", true), "a field that doesn't exist should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readStaticField(classOf[PublicChild], "s", false), "non-static field should cause an IllegalArgumentException")
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadDeclaredNamedStaticField() = {
//    assertEquals(Foo.VALUE, FieldUtils.readDeclaredStaticField(classOf[Foo], "VALUE"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredStaticField(classOf[PublicChild], "VALUE"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredStaticField(classOf[PubliclyShadowedChild], "VALUE"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredStaticField(classOf[PrivatelyShadowedChild], "VALUE"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadDeclaredNamedStaticFieldForceAccess() = {
//    assertEquals(Foo.VALUE, FieldUtils.readDeclaredStaticField(classOf[Foo], "VALUE", true))
//    assertEquals("child", FieldUtils.readDeclaredStaticField(classOf[PublicChild], "VALUE", true))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredStaticField(classOf[PubliclyShadowedChild], "VALUE", true))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredStaticField(classOf[PrivatelyShadowedChild], "VALUE", true))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadField() = {
//    val parentS = FieldUtils.getDeclaredField(parentClass, "s")
//    assertEquals("s", FieldUtils.readField(parentS, publicChild))
//    assertEquals("s", FieldUtils.readField(parentS, publiclyShadowedChild))
//    assertEquals("s", FieldUtils.readField(parentS, privatelyShadowedChild))
//    val parentB = FieldUtils.getDeclaredField(parentClass, "b", true)
//    assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, publicChild))
//    assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, publiclyShadowedChild))
//    assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, privatelyShadowedChild))
//    val parentI = FieldUtils.getDeclaredField(parentClass, "i", true)
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(parentI, publicChild))
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(parentI, publiclyShadowedChild))
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(parentI, privatelyShadowedChild))
//    val parentD = FieldUtils.getDeclaredField(parentClass, "d", true)
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(parentD, publicChild))
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(parentD, publiclyShadowedChild))
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(parentD, privatelyShadowedChild))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readField(null, publicChild), "a null field should cause an IllegalArgumentException")
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadFieldForceAccess() = {
//    val parentS = FieldUtils.getDeclaredField(parentClass, "s")
//    parentS.setAccessible(false)
//    assertEquals("s", FieldUtils.readField(parentS, publicChild, true))
//    assertEquals("s", FieldUtils.readField(parentS, publiclyShadowedChild, true))
//    assertEquals("s", FieldUtils.readField(parentS, privatelyShadowedChild, true))
//    val parentB = FieldUtils.getDeclaredField(parentClass, "b", true)
//    parentB.setAccessible(false)
//    assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, publicChild, true))
//    assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, publiclyShadowedChild, true))
//    assertEquals(Boolean.FALSE, FieldUtils.readField(parentB, privatelyShadowedChild, true))
//    val parentI = FieldUtils.getDeclaredField(parentClass, "i", true)
//    parentI.setAccessible(false)
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(parentI, publicChild, true))
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(parentI, publiclyShadowedChild, true))
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(parentI, privatelyShadowedChild, true))
//    val parentD = FieldUtils.getDeclaredField(parentClass, "d", true)
//    parentD.setAccessible(false)
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(parentD, publicChild, true))
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(parentD, publiclyShadowedChild, true))
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(parentD, privatelyShadowedChild, true))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readField(null, publicChild, true), "a null field should cause an IllegalArgumentException")
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadNamedField() = {
//    assertEquals("s", FieldUtils.readField(publicChild, "s"))
//    assertEquals("ss", FieldUtils.readField(publiclyShadowedChild, "s"))
//    assertEquals("s", FieldUtils.readField(privatelyShadowedChild, "s"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, null), "a null field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, ""), "an empty field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, " "), "a blank field name should cause an IllegalArgumentException")
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readField(null.asInstanceOf[Any], "none"), "a null target should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, "b"))
//    assertEquals(Boolean.TRUE, FieldUtils.readField(publiclyShadowedChild, "b"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(privatelyShadowedChild, "b"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, "i"))
//    assertEquals(FieldUtilsTest.I1, FieldUtils.readField(publiclyShadowedChild, "i"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(privatelyShadowedChild, "i"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, "d"))
//    assertEquals(FieldUtilsTest.D1, FieldUtils.readField(publiclyShadowedChild, "d"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(privatelyShadowedChild, "d"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadNamedFieldForceAccess() = {
//    assertEquals("s", FieldUtils.readField(publicChild, "s", true))
//    assertEquals("ss", FieldUtils.readField(publiclyShadowedChild, "s", true))
//    assertEquals("ss", FieldUtils.readField(privatelyShadowedChild, "s", true))
//    assertEquals(Boolean.FALSE, FieldUtils.readField(publicChild, "b", true))
//    assertEquals(Boolean.TRUE, FieldUtils.readField(publiclyShadowedChild, "b", true))
//    assertEquals(Boolean.TRUE, FieldUtils.readField(privatelyShadowedChild, "b", true))
//    assertEquals(FieldUtilsTest.I0, FieldUtils.readField(publicChild, "i", true))
//    assertEquals(FieldUtilsTest.I1, FieldUtils.readField(publiclyShadowedChild, "i", true))
//    assertEquals(FieldUtilsTest.I1, FieldUtils.readField(privatelyShadowedChild, "i", true))
//    assertEquals(FieldUtilsTest.D0, FieldUtils.readField(publicChild, "d", true))
//    assertEquals(FieldUtilsTest.D1, FieldUtils.readField(publiclyShadowedChild, "d", true))
//    assertEquals(FieldUtilsTest.D1, FieldUtils.readField(privatelyShadowedChild, "d", true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, null, true), "a null field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, "", true), "an empty field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readField(publicChild, " ", true), "a blank field name should cause an IllegalArgumentException")
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readField(null.asInstanceOf[Any], "none", true), "a null target should cause an IllegalArgumentException")
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadDeclaredNamedField() = {
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, null), "a null field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, ""), "an empty field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, " "), "a blank field name should cause an IllegalArgumentException")
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredField(null, "none"), "a null target should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "s"))
//    assertEquals("ss", FieldUtils.readDeclaredField(publiclyShadowedChild, "s"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(privatelyShadowedChild, "s"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "b"))
//    assertEquals(Boolean.TRUE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(privatelyShadowedChild, "b"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "i"))
//    assertEquals(FieldUtilsTest.I1, FieldUtils.readDeclaredField(publiclyShadowedChild, "i"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(privatelyShadowedChild, "i"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "d"))
//    assertEquals(FieldUtilsTest.D1, FieldUtils.readDeclaredField(publiclyShadowedChild, "d"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(privatelyShadowedChild, "d"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testReadDeclaredNamedFieldForceAccess() = {
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, null, true), "a null field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "", true), "an empty field name should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, " ", true), "a blank field name should cause an IllegalArgumentException")
//    assertThrows(classOf[NullPointerException], () => FieldUtils.readDeclaredField(null, "none", true), "a null target should cause an IllegalArgumentException")
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "s", true))
//    assertEquals("ss", FieldUtils.readDeclaredField(publiclyShadowedChild, "s", true))
//    assertEquals("ss", FieldUtils.readDeclaredField(privatelyShadowedChild, "s", true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "b", true))
//    assertEquals(Boolean.TRUE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b", true))
//    assertEquals(Boolean.TRUE, FieldUtils.readDeclaredField(privatelyShadowedChild, "b", true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "i", true))
//    assertEquals(FieldUtilsTest.I1, FieldUtils.readDeclaredField(publiclyShadowedChild, "i", true))
//    assertEquals(FieldUtilsTest.I1, FieldUtils.readDeclaredField(privatelyShadowedChild, "i", true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.readDeclaredField(publicChild, "d", true))
//    assertEquals(FieldUtilsTest.D1, FieldUtils.readDeclaredField(publiclyShadowedChild, "d", true))
//    assertEquals(FieldUtilsTest.D1, FieldUtils.readDeclaredField(privatelyShadowedChild, "d", true))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteStaticField() = {
//    val field = classOf[StaticContainer].getDeclaredField("mutablePublic")
//    FieldUtils.writeStaticField(field, "new")
//    assertEquals("new", StaticContainer.mutablePublic)
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("mutableProtected"), "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("mutablePackage"), "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("mutablePrivate"), "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PUBLIC"), "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PROTECTED"), "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PACKAGE"), "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PRIVATE"), "new"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteStaticFieldForceAccess() = {
//    var field = classOf[StaticContainer].getDeclaredField("mutablePublic")
//    FieldUtils.writeStaticField(field, "new", true)
//    assertEquals("new", StaticContainer.mutablePublic)
//    field = classOf[StaticContainer].getDeclaredField("mutableProtected")
//    FieldUtils.writeStaticField(field, "new", true)
//    assertEquals("new", StaticContainer.getMutableProtected)
//    field = classOf[StaticContainer].getDeclaredField("mutablePackage")
//    FieldUtils.writeStaticField(field, "new", true)
//    assertEquals("new", StaticContainer.getMutablePackage)
//    field = classOf[StaticContainer].getDeclaredField("mutablePrivate")
//    FieldUtils.writeStaticField(field, "new", true)
//    assertEquals("new", StaticContainer.getMutablePrivate)
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PUBLIC"), "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PROTECTED"), "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PACKAGE"), "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainer].getDeclaredField("IMMUTABLE_PRIVATE"), "new", true))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteNamedStaticField() = {
//    FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutablePublic", "new")
//    assertEquals("new", StaticContainer.mutablePublic)
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutableProtected", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutablePackage", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutablePrivate", "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PUBLIC", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PROTECTED", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PACKAGE", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PRIVATE", "new"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteNamedStaticFieldForceAccess() = {
//    FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutablePublic", "new", true)
//    assertEquals("new", StaticContainer.mutablePublic)
//    FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutableProtected", "new", true)
//    assertEquals("new", StaticContainer.getMutableProtected)
//    FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutablePackage", "new", true)
//    assertEquals("new", StaticContainer.getMutablePackage)
//    FieldUtils.writeStaticField(classOf[StaticContainerChild], "mutablePrivate", "new", true)
//    assertEquals("new", StaticContainer.getMutablePrivate)
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PUBLIC", "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PROTECTED", "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PACKAGE", "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeStaticField(classOf[StaticContainerChild], "IMMUTABLE_PRIVATE", "new", true))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteDeclaredNamedStaticField() = {
//    FieldUtils.writeStaticField(classOf[StaticContainer], "mutablePublic", "new")
//    assertEquals("new", StaticContainer.mutablePublic)
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutableProtected", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutablePackage", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutablePrivate", "new"))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PUBLIC", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PROTECTED", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PACKAGE", "new"))
//    assertThrows(classOf[NullPointerException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PRIVATE", "new"))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteDeclaredNamedStaticFieldForceAccess() = {
//    FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutablePublic", "new", true)
//    assertEquals("new", StaticContainer.mutablePublic)
//    FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutableProtected", "new", true)
//    assertEquals("new", StaticContainer.getMutableProtected)
//    FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutablePackage", "new", true)
//    assertEquals("new", StaticContainer.getMutablePackage)
//    FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "mutablePrivate", "new", true)
//    assertEquals("new", StaticContainer.getMutablePrivate)
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PUBLIC", "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PROTECTED", "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PACKAGE", "new", true))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeDeclaredStaticField(classOf[StaticContainer], "IMMUTABLE_PRIVATE", "new", true))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteField() = {
//    val field = parentClass.getDeclaredField("s")
//    FieldUtils.writeField(field, publicChild, "S")
//    assertEquals("S", field.get(publicChild))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeField(parentClass.getDeclaredField("b"), publicChild, Boolean.TRUE))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeField(parentClass.getDeclaredField("i"), publicChild, Integer.valueOf(Integer.MAX_VALUE)))
//    assertThrows(classOf[IllegalAccessException], () => FieldUtils.writeField(parentClass.getDeclaredField("d"), publicChild, Double.valueOf(Double.MAX_VALUE)))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteFieldForceAccess() = {
//    var field = parentClass.getDeclaredField("s")
//    FieldUtils.writeField(field, publicChild, "S", true)
//    assertEquals("S", field.get(publicChild))
//    field = parentClass.getDeclaredField("b")
//    FieldUtils.writeField(field, publicChild, Boolean.TRUE, true)
//    assertEquals(Boolean.TRUE, field.get(publicChild))
//    field = parentClass.getDeclaredField("i")
//    FieldUtils.writeField(field, publicChild, Integer.valueOf(Integer.MAX_VALUE), true)
//    assertEquals(Integer.valueOf(Integer.MAX_VALUE), field.get(publicChild))
//    field = parentClass.getDeclaredField("d")
//    FieldUtils.writeField(field, publicChild, Double.valueOf(Double.MAX_VALUE), true)
//    assertEquals(Double.valueOf(Double.MAX_VALUE), field.get(publicChild))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteNamedField() = {
//    FieldUtils.writeField(publicChild, "s", "S")
//    assertEquals("S", FieldUtils.readField(publicChild, "s"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeField(publicChild, "b", Boolean.TRUE))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeField(publicChild, "i", Integer.valueOf(1)))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeField(publicChild, "d", Double.valueOf(1.0)))
//    FieldUtils.writeField(publiclyShadowedChild, "s", "S")
//    assertEquals("S", FieldUtils.readField(publiclyShadowedChild, "s"))
//    FieldUtils.writeField(publiclyShadowedChild, "b", Boolean.FALSE)
//    assertEquals(Boolean.FALSE, FieldUtils.readField(publiclyShadowedChild, "b"))
//    FieldUtils.writeField(publiclyShadowedChild, "i", Integer.valueOf(0))
//    assertEquals(Integer.valueOf(0), FieldUtils.readField(publiclyShadowedChild, "i"))
//    FieldUtils.writeField(publiclyShadowedChild, "d", Double.valueOf(0.0))
//    assertEquals(Double.valueOf(0.0), FieldUtils.readField(publiclyShadowedChild, "d"))
//    FieldUtils.writeField(privatelyShadowedChild, "s", "S")
//    assertEquals("S", FieldUtils.readField(privatelyShadowedChild, "s"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeField(privatelyShadowedChild, "b", Boolean.TRUE))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeField(privatelyShadowedChild, "i", Integer.valueOf(1)))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeField(privatelyShadowedChild, "d", Double.valueOf(1.0)))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteNamedFieldForceAccess() = {
//    FieldUtils.writeField(publicChild, "s", "S", true)
//    assertEquals("S", FieldUtils.readField(publicChild, "s", true))
//    FieldUtils.writeField(publicChild, "b", Boolean.TRUE, true)
//    assertEquals(Boolean.TRUE, FieldUtils.readField(publicChild, "b", true))
//    FieldUtils.writeField(publicChild, "i", Integer.valueOf(1), true)
//    assertEquals(Integer.valueOf(1), FieldUtils.readField(publicChild, "i", true))
//    FieldUtils.writeField(publicChild, "d", Double.valueOf(1.0), true)
//    assertEquals(Double.valueOf(1.0), FieldUtils.readField(publicChild, "d", true))
//    FieldUtils.writeField(publiclyShadowedChild, "s", "S", true)
//    assertEquals("S", FieldUtils.readField(publiclyShadowedChild, "s", true))
//    FieldUtils.writeField(publiclyShadowedChild, "b", Boolean.FALSE, true)
//    assertEquals(Boolean.FALSE, FieldUtils.readField(publiclyShadowedChild, "b", true))
//    FieldUtils.writeField(publiclyShadowedChild, "i", Integer.valueOf(0), true)
//    assertEquals(Integer.valueOf(0), FieldUtils.readField(publiclyShadowedChild, "i", true))
//    FieldUtils.writeField(publiclyShadowedChild, "d", Double.valueOf(0.0), true)
//    assertEquals(Double.valueOf(0.0), FieldUtils.readField(publiclyShadowedChild, "d", true))
//    FieldUtils.writeField(privatelyShadowedChild, "s", "S", true)
//    assertEquals("S", FieldUtils.readField(privatelyShadowedChild, "s", true))
//    FieldUtils.writeField(privatelyShadowedChild, "b", Boolean.FALSE, true)
//    assertEquals(Boolean.FALSE, FieldUtils.readField(privatelyShadowedChild, "b", true))
//    FieldUtils.writeField(privatelyShadowedChild, "i", Integer.valueOf(0), true)
//    assertEquals(Integer.valueOf(0), FieldUtils.readField(privatelyShadowedChild, "i", true))
//    FieldUtils.writeField(privatelyShadowedChild, "d", Double.valueOf(0.0), true)
//    assertEquals(Double.valueOf(0.0), FieldUtils.readField(privatelyShadowedChild, "d", true))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteDeclaredNamedField() = {
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "s", "S"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "b", Boolean.TRUE))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "i", Integer.valueOf(1)))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "d", Double.valueOf(1.0)))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "s", "S")
//    assertEquals("S", FieldUtils.readDeclaredField(publiclyShadowedChild, "s"))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "b", Boolean.FALSE)
//    assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b"))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "i", Integer.valueOf(0))
//    assertEquals(Integer.valueOf(0), FieldUtils.readDeclaredField(publiclyShadowedChild, "i"))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "d", Double.valueOf(0.0))
//    assertEquals(Double.valueOf(0.0), FieldUtils.readDeclaredField(publiclyShadowedChild, "d"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(privatelyShadowedChild, "s", "S"))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(privatelyShadowedChild, "b", Boolean.TRUE))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(privatelyShadowedChild, "i", Integer.valueOf(1)))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(privatelyShadowedChild, "d", Double.valueOf(1.0)))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWriteDeclaredNamedFieldForceAccess() = {
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "s", "S", true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "b", Boolean.TRUE, true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "i", Integer.valueOf(1), true))
//    assertThrows(classOf[IllegalArgumentException], () => FieldUtils.writeDeclaredField(publicChild, "d", Double.valueOf(1.0), true))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "s", "S", true)
//    assertEquals("S", FieldUtils.readDeclaredField(publiclyShadowedChild, "s", true))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "b", Boolean.FALSE, true)
//    assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(publiclyShadowedChild, "b", true))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "i", Integer.valueOf(0), true)
//    assertEquals(Integer.valueOf(0), FieldUtils.readDeclaredField(publiclyShadowedChild, "i", true))
//    FieldUtils.writeDeclaredField(publiclyShadowedChild, "d", Double.valueOf(0.0), true)
//    assertEquals(Double.valueOf(0.0), FieldUtils.readDeclaredField(publiclyShadowedChild, "d", true))
//    FieldUtils.writeDeclaredField(privatelyShadowedChild, "s", "S", true)
//    assertEquals("S", FieldUtils.readDeclaredField(privatelyShadowedChild, "s", true))
//    FieldUtils.writeDeclaredField(privatelyShadowedChild, "b", Boolean.FALSE, true)
//    assertEquals(Boolean.FALSE, FieldUtils.readDeclaredField(privatelyShadowedChild, "b", true))
//    FieldUtils.writeDeclaredField(privatelyShadowedChild, "i", Integer.valueOf(0), true)
//    assertEquals(Integer.valueOf(0), FieldUtils.readDeclaredField(privatelyShadowedChild, "i", true))
//    FieldUtils.writeDeclaredField(privatelyShadowedChild, "d", Double.valueOf(0.0), true)
//    assertEquals(Double.valueOf(0.0), FieldUtils.readDeclaredField(privatelyShadowedChild, "d", true))
//  }
//
//  @Test def testAmbig() = assertThrows(classOf[IllegalArgumentException], () => FieldUtils.getField(classOf[Ambig], "VALUE"))
//
//  @Test
//  @throws[Exception]
//  def testRemoveFinalModifier() = {
//    val field = classOf[StaticContainer].getDeclaredField("IMMUTABLE_PRIVATE_2")
//    assertFalse(field.isAccessible)
//    assertTrue(Modifier.isFinal(field.getModifiers))
//    callRemoveFinalModifierCheckForException(field, true)
//    if (SystemUtils.isJavaVersionAtMost(JavaVersion.JAVA_11)) {
//      assertFalse(Modifier.isFinal(field.getModifiers))
//      assertFalse(field.isAccessible)
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  def testRemoveFinalModifierWithAccess() = {
//    val field = classOf[StaticContainer].getDeclaredField("IMMUTABLE_PRIVATE_2")
//    assertFalse(field.isAccessible)
//    assertTrue(Modifier.isFinal(field.getModifiers))
//    callRemoveFinalModifierCheckForException(field, true)
//    if (SystemUtils.isJavaVersionAtMost(JavaVersion.JAVA_11)) {
//      assertFalse(Modifier.isFinal(field.getModifiers))
//      assertFalse(field.isAccessible)
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  def testRemoveFinalModifierWithoutAccess() = {
//    val field = classOf[StaticContainer].getDeclaredField("IMMUTABLE_PRIVATE_2")
//    assertFalse(field.isAccessible)
//    assertTrue(Modifier.isFinal(field.getModifiers))
//    callRemoveFinalModifierCheckForException(field, false)
//    if (SystemUtils.isJavaVersionAtMost(JavaVersion.JAVA_11)) {
//      assertTrue(Modifier.isFinal(field.getModifiers))
//      assertFalse(field.isAccessible)
//    }
//  }
//
//  @Test
//  @throws[Exception]
//  def testRemoveFinalModifierAccessNotNeeded() = {
//    val field = classOf[StaticContainer].getDeclaredField("IMMUTABLE_PACKAGE")
//    assertFalse(field.isAccessible)
//    assertTrue(Modifier.isFinal(field.getModifiers))
//    callRemoveFinalModifierCheckForException(field, false)
//    if (SystemUtils.isJavaVersionAtMost(JavaVersion.JAVA_11)) {
//      assertTrue(Modifier.isFinal(field.getModifiers))
//      assertFalse(field.isAccessible)
//    }
//  }
//
//  /**
//    * Read the {@code @deprecated} notice on
//    * {@link FieldUtils# removeFinalModifier ( Field, boolean)}.
//    *
//    * @param field       {@link Field} to be curried into
//    *                    {@link FieldUtils# removeFinalModifier ( Field, boolean)}.
//    * @param forceAccess {@link Boolean} to be curried into
//    *                    {@link FieldUtils# removeFinalModifier ( Field, boolean)}.
//    */
//  private def callRemoveFinalModifierCheckForException(field: Field, forceAccess: Boolean) = try FieldUtils.removeFinalModifier(field, forceAccess)
//  catch {
//    case exception: UnsupportedOperationException =>
//      if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_12)) assertTrue(exception.getCause.isInstanceOf[NoSuchFieldException])
//      else fail("No exception should be thrown for java prior to 12.0")
//  }
//}
