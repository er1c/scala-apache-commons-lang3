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

import java.lang.{
  Boolean => JavaBoolean,
  Byte => JavaByte,
  Double => JavaDouble,
  Float => JavaFloat,
  Long => JavaLong,
  Short => JavaShort
}
import java.lang.reflect.Modifier
import java.util
import java.util.Collections
import org.apache.commons.lang3.ClassUtils.Interfaces
import org.apache.commons.lang3.reflect.testbed.{GenericConsumer, GenericParent, StringParameterizedChild}
import org.junit.Assert._
import org.junit.Test
import org.scalatest.Assertions.assertThrows
import scala.reflect.ClassTag

// JUnit4 does not support primitive equality testing apart from long
object ClassUtilsTest {
  private[ClassUtilsTest] class CX extends ClassUtilsTest.IB with ClassUtilsTest.IA with ClassUtilsTest.IE {}

  private[ClassUtilsTest] class CY extends ClassUtilsTest.CX with ClassUtilsTest.IB with ClassUtilsTest.IC {}

  private[ClassUtilsTest] trait IA {}

  private[ClassUtilsTest] trait IB {}

  private[ClassUtilsTest] trait IC extends ClassUtilsTest.ID with ClassUtilsTest.IE {}

  private[ClassUtilsTest] trait ID {}

  private[ClassUtilsTest] trait IE extends ClassUtilsTest.IF {}

  private[ClassUtilsTest] trait IF {}

  private[ClassUtilsTest] class Inner {

    private[ClassUtilsTest] class DeeplyNested {}

  }

}

/**
  * Unit tests {@link org.apache.commons.lang3.ClassUtils}.
  */
@SuppressWarnings(Array("boxing")) class ClassUtilsTest {
  @throws[Exception]
  private def assertGetClassReturnsClass(c: Class[_]): Unit = assertEquals(c, ClassUtils.getClass(c.getName))

  private def assertGetClassThrowsClassNotFound(className: String): Unit = {
    assertGetClassThrowsException(className, classOf[ClassNotFoundException])
  }

  private def assertGetClassThrowsException[E <: Exception: ClassTag](
    className: String,
    exceptionType: Class[E]): Unit = {
    void(exceptionType)
    assertThrows[E](
      ClassUtils.getClass(className)
    ) //, "ClassUtils.getClass() should fail with an exception of type " + exceptionType.getName + " when given class name \"" + className + "\".")
    ()
  }

  private def assertGetClassThrowsNullPointerException(className: String): Unit = {
    assertGetClassThrowsException(className, classOf[NullPointerException])
    ()
  }

  @Test def test_convertClassesToClassNames_List(): Unit = {
    val list: util.List[Class[_]] = new util.ArrayList[Class[_]]
    var result = ClassUtils.convertClassesToClassNames(list)
    assertEquals(0, result.size)
    list.add(classOf[String])
    list.add(null)
    list.add(classOf[Any])
    result = ClassUtils.convertClassesToClassNames(list)
    assertEquals(3, result.size)
    assertEquals("java.lang.String", result.get(0))
    assertNull(result.get(1))
    assertEquals("java.lang.Object", result.get(2))
    @SuppressWarnings(Array("unchecked")) // test what happens when non-generic code adds wrong type of element
    val olist: util.List[AnyRef] = list.asInstanceOf[util.List[_]].asInstanceOf[util.List[AnyRef]]
    olist.add(new AnyRef)
    assertThrows[ClassCastException](
      ClassUtils.convertClassesToClassNames(list)
    ) //, "Should not have been able to convert list")
    assertNull(ClassUtils.convertClassesToClassNames(null))
  }

  // -------------------------------------------------------------------------
  @Test def test_convertClassNamesToClasses_List(): Unit = {
    val list = new util.ArrayList[String]
    var result = ClassUtils.convertClassNamesToClasses(list)
    assertEquals(0, result.size)
    list.add("java.lang.String")
    list.add("java.lang.xxx")
    list.add("java.lang.Object")
    result = ClassUtils.convertClassNamesToClasses(list)
    assertEquals(3, result.size)
    assertEquals(classOf[String], result.get(0))
    assertNull(result.get(1))
    assertEquals(classOf[Any], result.get(2))
    @SuppressWarnings(Array("unchecked")) val olist = list.asInstanceOf[util.List[_]].asInstanceOf[util.List[AnyRef]]
    olist.add(new AnyRef)
    assertThrows[ClassCastException](
      ClassUtils.convertClassNamesToClasses(list)
    ) //, "Should not have been able to convert list")
    assertNull(ClassUtils.convertClassNamesToClasses(null))
  }

  @Test def test_getAbbreviatedName_Class(): Unit = {
    assertEquals("", ClassUtils.getAbbreviatedName(null.asInstanceOf[Class[_]], 1))
    assertEquals("j.l.String", ClassUtils.getAbbreviatedName(classOf[String], 1))
    assertEquals("j.l.String", ClassUtils.getAbbreviatedName(classOf[String], 5))
    assertEquals("o.a.c.l.ClassUtils$", ClassUtils.getAbbreviatedName(ClassUtils.getClass(), 18))
    assertEquals("j.lang.String", ClassUtils.getAbbreviatedName(classOf[String], 13))
    assertEquals("j.lang.String", ClassUtils.getAbbreviatedName(classOf[String], 15))
    assertEquals("java.lang.String", ClassUtils.getAbbreviatedName(classOf[String], 20))
  }

  /**
    * Test that in case the required length is larger than the name and thus there is no need for any shortening
    * then the returned string object is the same as the one passed as argument. Note, however, that this is
    * tested as an internal implementation detail, but it is not a guaranteed feature of the implementation.
    */
  @Test
  //@DisplayName("When the length hint is longer than the actual length then the same String object is returned")
  def test_getAbbreviatedName_TooLongHint(): Unit = {
    val className = "java.lang.String"
    assertSame(className, ClassUtils.getAbbreviatedName(className, className.length + 1))
    assertSame(className, ClassUtils.getAbbreviatedName(className, className.length))
  }

  @Test
  //@DisplayName("When the desired length is negative then exception is thrown")
  def test_getAbbreviatedName_Class_NegativeLen(): Unit = {
    assertThrows[IllegalArgumentException](ClassUtils.getAbbreviatedName(classOf[String], -10))
    ()
  }

  @Test
  //@DisplayName("When the desired length is zero then exception is thrown")
  def test_getAbbreviatedName_Class_ZeroLen(): Unit = {
    assertThrows[IllegalArgumentException](ClassUtils.getAbbreviatedName(classOf[String], 0))
    ()
  }

  @Test def test_getAbbreviatedName_String(): Unit = {
    assertEquals("", ClassUtils.getAbbreviatedName(null.asInstanceOf[String], 1))
    assertEquals("", ClassUtils.getAbbreviatedName("", 1))
    assertEquals("WithoutPackage", ClassUtils.getAbbreviatedName("WithoutPackage", 1))
    assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", 1))
    assertEquals("o.a.c.l.ClassUtils", ClassUtils.getAbbreviatedName("org.apache.commons.lang3.ClassUtils", 18))
    assertEquals(
      "org.apache.commons.lang3.ClassUtils",
      ClassUtils.getAbbreviatedName("org.apache.commons.lang3.ClassUtils", "org.apache.commons.lang3.ClassUtils".length)
    )
    assertEquals("o.a.c.l.ClassUtils", ClassUtils.getAbbreviatedName("o.a.c.l.ClassUtils", 18))
    assertEquals("o..c.l.ClassUtils", ClassUtils.getAbbreviatedName("o..c.l.ClassUtils", 18))
    assertEquals(".", ClassUtils.getAbbreviatedName(".", 18))
    assertEquals(".", ClassUtils.getAbbreviatedName(".", 1))
    assertEquals("..", ClassUtils.getAbbreviatedName("..", 1))
    assertEquals("...", ClassUtils.getAbbreviatedName("...", 2))
    assertEquals("...", ClassUtils.getAbbreviatedName("...", 3))
    assertEquals("java.lang.String", ClassUtils.getAbbreviatedName("java.lang.String", Integer.MAX_VALUE))
    assertEquals("j.lang.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.lang.String".length))
    assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.lang.String".length - 1))
    assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.l.String".length))
    assertEquals("j.l.String", ClassUtils.getAbbreviatedName("java.lang.String", "j.l.String".length - 1))
  }

  @Test def test_getAllInterfaces_Class(): Unit = {
    val list = ClassUtils.getAllInterfaces(classOf[ClassUtilsTest.CY])
    assertEquals(6, list.size)
    assertEquals(classOf[ClassUtilsTest.IC], list.get(0))
    assertEquals(classOf[ClassUtilsTest.ID], list.get(1))
    assertEquals(classOf[ClassUtilsTest.IE], list.get(2))
    assertEquals(classOf[ClassUtilsTest.IF], list.get(3))
    assertEquals(classOf[ClassUtilsTest.IB], list.get(4))
    assertEquals(classOf[ClassUtilsTest.IA], list.get(5))
    assertNull(ClassUtils.getAllInterfaces(null))
  }

  @Test def test_getAllSuperclasses_Class(): Unit = {
    val list = ClassUtils.getAllSuperclasses(classOf[ClassUtilsTest.CY])
    assertEquals(2, list.size)
    assertEquals(classOf[ClassUtilsTest.CX], list.get(0))
    assertEquals(classOf[Any], list.get(1))
    assertNull(ClassUtils.getAllSuperclasses(null))
  }

  @Test def test_getCanonicalName_Class(): Unit = {
    assertEquals("org.apache.commons.lang3.ClassUtils$", ClassUtils.getCanonicalName(ClassUtils.getClass()))
    assertEquals("java.util.Map.Entry", ClassUtils.getCanonicalName(classOf[util.Map.Entry[_, _]]))
    assertEquals("", ClassUtils.getCanonicalName(null.asInstanceOf[Class[_]]))
    assertEquals("java.lang.String[]", ClassUtils.getCanonicalName(classOf[Array[String]]))
    assertEquals("java.util.Map.Entry[]", ClassUtils.getCanonicalName(classOf[Array[util.Map.Entry[_, _]]]))
    // Primitives
    assertEquals("boolean", ClassUtils.getCanonicalName(classOf[Boolean]))
    assertEquals("byte", ClassUtils.getCanonicalName(classOf[Byte]))
    assertEquals("char", ClassUtils.getCanonicalName(classOf[Char]))
    assertEquals("short", ClassUtils.getCanonicalName(classOf[Short]))
    assertEquals("int", ClassUtils.getCanonicalName(classOf[Int]))
    assertEquals("long", ClassUtils.getCanonicalName(classOf[Long]))
    assertEquals("float", ClassUtils.getCanonicalName(classOf[Float]))
    assertEquals("double", ClassUtils.getCanonicalName(classOf[Double]))
    // Primitive Arrays
    assertEquals("boolean[]", ClassUtils.getCanonicalName(classOf[Array[Boolean]]))
    assertEquals("byte[]", ClassUtils.getCanonicalName(classOf[Array[Byte]]))
    assertEquals("char[]", ClassUtils.getCanonicalName(classOf[Array[Char]]))
    assertEquals("short[]", ClassUtils.getCanonicalName(classOf[Array[Short]]))
    assertEquals("int[]", ClassUtils.getCanonicalName(classOf[Array[Int]]))
    assertEquals("long[]", ClassUtils.getCanonicalName(classOf[Array[Long]]))
    assertEquals("float[]", ClassUtils.getCanonicalName(classOf[Array[Float]]))
    assertEquals("double[]", ClassUtils.getCanonicalName(classOf[Array[Double]]))
    // Arrays of arrays of ...
    assertEquals("java.lang.String[][]", ClassUtils.getCanonicalName(classOf[Array[Array[String]]]))
    assertEquals("java.lang.String[][][]", ClassUtils.getCanonicalName(classOf[Array[Array[Array[String]]]]))
    assertEquals("java.lang.String[][][][]", ClassUtils.getCanonicalName(classOf[Array[Array[Array[Array[String]]]]]))
    // Inner types
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals(StringUtils.EMPTY, ClassUtils.getCanonicalName(new AnyRef() {}.getClass))
//    assertEquals(StringUtils.EMPTY, ClassUtils.getCanonicalName(classOf[Named]))
    assertEquals(
      "org.apache.commons.lang3.ClassUtilsTest.Inner",
      ClassUtils.getCanonicalName(classOf[ClassUtilsTest.Inner]))
  }

  @Test def test_getCanonicalName_Class_String(): Unit = {
    assertEquals("org.apache.commons.lang3.ClassUtils$", ClassUtils.getCanonicalName(ClassUtils.getClass(), "X"))
    assertEquals("java.util.Map.Entry", ClassUtils.getCanonicalName(classOf[util.Map.Entry[_, _]], "X"))
    assertEquals("X", ClassUtils.getCanonicalName(null.asInstanceOf[Class[_]], "X"))
    assertEquals("java.lang.String[]", ClassUtils.getCanonicalName(classOf[Array[String]], "X"))
    assertEquals("java.util.Map.Entry[]", ClassUtils.getCanonicalName(classOf[Array[util.Map.Entry[_, _]]], "X"))
    assertEquals("boolean", ClassUtils.getCanonicalName(classOf[Boolean], "X"))
    assertEquals("byte", ClassUtils.getCanonicalName(classOf[Byte], "X"))
    assertEquals("char", ClassUtils.getCanonicalName(classOf[Char], "X"))
    assertEquals("short", ClassUtils.getCanonicalName(classOf[Short], "X"))
    assertEquals("int", ClassUtils.getCanonicalName(classOf[Int], "X"))
    assertEquals("long", ClassUtils.getCanonicalName(classOf[Long], "X"))
    assertEquals("float", ClassUtils.getCanonicalName(classOf[Float], "X"))
    assertEquals("double", ClassUtils.getCanonicalName(classOf[Double], "X"))
    assertEquals("boolean[]", ClassUtils.getCanonicalName(classOf[Array[Boolean]], "X"))
    assertEquals("byte[]", ClassUtils.getCanonicalName(classOf[Array[Byte]], "X"))
    assertEquals("char[]", ClassUtils.getCanonicalName(classOf[Array[Char]], "X"))
    assertEquals("short[]", ClassUtils.getCanonicalName(classOf[Array[Short]], "X"))
    assertEquals("int[]", ClassUtils.getCanonicalName(classOf[Array[Int]], "X"))
    assertEquals("long[]", ClassUtils.getCanonicalName(classOf[Array[Long]], "X"))
    assertEquals("float[]", ClassUtils.getCanonicalName(classOf[Array[Float]], "X"))
    assertEquals("double[]", ClassUtils.getCanonicalName(classOf[Array[Double]], "X"))
    assertEquals("java.lang.String[][]", ClassUtils.getCanonicalName(classOf[Array[Array[String]]], "X"))
    assertEquals("java.lang.String[][][]", ClassUtils.getCanonicalName(classOf[Array[Array[Array[String]]]], "X"))
    assertEquals(
      "java.lang.String[][][][]",
      ClassUtils.getCanonicalName(classOf[Array[Array[Array[Array[String]]]]], "X"))
    class Named {}
    assertEquals("X", ClassUtils.getCanonicalName(new AnyRef() {}.getClass, "X"))
    assertEquals("X", ClassUtils.getCanonicalName(classOf[Named], "X"))
    assertEquals(
      "org.apache.commons.lang3.ClassUtilsTest.Inner",
      ClassUtils.getCanonicalName(classOf[ClassUtilsTest.Inner], "X"))
  }

  @Test def test_getName_Class(): Unit = {
    assertEquals("org.apache.commons.lang3.ClassUtils$", ClassUtils.getName(ClassUtils.getClass()))
    assertEquals("java.util.Map$Entry", ClassUtils.getName(classOf[util.Map.Entry[_, _]]))
    assertEquals("", ClassUtils.getName(null.asInstanceOf[Class[_]]))
    assertEquals("[Ljava.lang.String;", ClassUtils.getName(classOf[Array[String]]))
    assertEquals("[Ljava.util.Map$Entry;", ClassUtils.getName(classOf[Array[util.Map.Entry[_, _]]]))
    assertEquals("boolean", ClassUtils.getName(classOf[Boolean]))
    assertEquals("byte", ClassUtils.getName(classOf[Byte]))
    assertEquals("char", ClassUtils.getName(classOf[Char]))
    assertEquals("short", ClassUtils.getName(classOf[Short]))
    assertEquals("int", ClassUtils.getName(classOf[Int]))
    assertEquals("long", ClassUtils.getName(classOf[Long]))
    assertEquals("float", ClassUtils.getName(classOf[Float]))
    assertEquals("double", ClassUtils.getName(classOf[Double]))
    assertEquals("[Z", ClassUtils.getName(classOf[Array[Boolean]]))
    assertEquals("[B", ClassUtils.getName(classOf[Array[Byte]]))
    assertEquals("[C", ClassUtils.getName(classOf[Array[Char]]))
    assertEquals("[S", ClassUtils.getName(classOf[Array[Short]]))
    assertEquals("[I", ClassUtils.getName(classOf[Array[Int]]))
    assertEquals("[J", ClassUtils.getName(classOf[Array[Long]]))
    assertEquals("[F", ClassUtils.getName(classOf[Array[Float]]))
    assertEquals("[D", ClassUtils.getName(classOf[Array[Double]]))
    assertEquals("[[Ljava.lang.String;", ClassUtils.getName(classOf[Array[Array[String]]]))
    assertEquals("[[[Ljava.lang.String;", ClassUtils.getName(classOf[Array[Array[Array[String]]]]))
    assertEquals("[[[[Ljava.lang.String;", ClassUtils.getName(classOf[Array[Array[Array[Array[String]]]]]))
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("org.apache.commons.lang3.ClassUtilsTest$$anon$3", ClassUtils.getName(new AnyRef() {}.getClass))
//    assertEquals("org.apache.commons.lang3.ClassUtilsTest$Named$3", ClassUtils.getName(classOf[Named]))
    assertEquals("org.apache.commons.lang3.ClassUtilsTest$Inner", ClassUtils.getName(classOf[ClassUtilsTest.Inner]))
  }

  @Test def test_getName_Object(): Unit = {
    assertEquals("org.apache.commons.lang3.ClassUtils$", ClassUtils.getName(ClassUtils, "<null>"))
    assertEquals(
      "org.apache.commons.lang3.ClassUtilsTest$Inner",
      ClassUtils.getName(new ClassUtilsTest.Inner, "<null>"))
    assertEquals("java.lang.String", ClassUtils.getName("hello", "<null>"))
    assertEquals("<null>", ClassUtils.getName(null, "<null>"))
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("org.apache.commons.lang3.ClassUtilsTest$$anon$4", ClassUtils.getName(new AnyRef() {}, "<null>"))
//    assertEquals("org.apache.commons.lang3.ClassUtilsTest$Named$4", ClassUtils.getName(new Named, "<null>"))
    assertEquals(
      "org.apache.commons.lang3.ClassUtilsTest$Inner",
      ClassUtils.getName(new ClassUtilsTest.Inner, "<null>"))
  }

  @Test def test_getPackageCanonicalName_Class(): Unit = {
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(ClassUtils.getClass()))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(classOf[Array[ClassUtils.type]]))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(classOf[Array[Array[ClassUtils.type]]]))
    assertEquals("", ClassUtils.getPackageCanonicalName(classOf[Array[Int]]))
    assertEquals("", ClassUtils.getPackageCanonicalName(classOf[Array[Array[Int]]]))
    class Named {}
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(new AnyRef() {}.getClass))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(classOf[Named]))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(classOf[ClassUtilsTest.Inner]))
  }

  @Test def test_getPackageCanonicalName_Object(): Unit = {
    assertEquals("<null>", ClassUtils.getPackageCanonicalName(null, "<null>"))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(ClassUtils, "<null>"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName(new Array[ClassUtils.type](0), "<null>"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName(Array.ofDim[ClassUtils.type](0, 0), "<null>"))
    assertEquals("", ClassUtils.getPackageCanonicalName(new Array[Int](0), "<null>"))
    assertEquals("", ClassUtils.getPackageCanonicalName(Array.ofDim[Int](0, 0), "<null>"))
    class Named {}
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(new AnyRef() {}, "<null>"))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(new Named, "<null>"))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName(new ClassUtilsTest.Inner, "<null>"))
  }

  @Test def test_getPackageCanonicalName_String(): Unit = {
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtils"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("[Lorg.apache.commons.lang3.ClassUtils;"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("[[Lorg.apache.commons.lang3.ClassUtils;"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtils[]"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtils[][]"))
    assertEquals("", ClassUtils.getPackageCanonicalName("[I"))
    assertEquals("", ClassUtils.getPackageCanonicalName("[[I"))
    assertEquals("", ClassUtils.getPackageCanonicalName("int[]"))
    assertEquals("", ClassUtils.getPackageCanonicalName("int[][]"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtilsTest$6"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtilsTest$5Named"))
    assertEquals(
      "org.apache.commons.lang3",
      ClassUtils.getPackageCanonicalName("org.apache.commons.lang3.ClassUtilsTest$Inner"))
  }

  @Test def test_getPackageName_Class(): Unit = {
    assertEquals("java.lang", ClassUtils.getPackageName(classOf[String]))
    assertEquals("java.util", ClassUtils.getPackageName(classOf[util.Map.Entry[_, _]]))
    assertEquals("", ClassUtils.getPackageName(null.asInstanceOf[Class[_]]))
    // LANG-535
    assertEquals("java.lang", ClassUtils.getPackageName(classOf[Array[String]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Boolean]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Byte]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Char]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Short]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Int]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Long]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Float]]))
    assertEquals("", ClassUtils.getPackageName(classOf[Array[Double]]))
    assertEquals("java.lang", ClassUtils.getPackageName(classOf[Array[Array[String]]]))
    assertEquals("java.lang", ClassUtils.getPackageName(classOf[Array[Array[Array[String]]]]))
    assertEquals("java.lang", ClassUtils.getPackageName(classOf[Array[Array[Array[Array[String]]]]]))
    // On-the-fly types
    class Named {}
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(new AnyRef() {}.getClass))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(classOf[Named]))
  }

  @Test def test_getPackageName_Object(): Unit = {
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(ClassUtils, "<null>"))
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(new ClassUtilsTest.Inner, "<null>"))
    assertEquals("<null>", ClassUtils.getPackageName(null, "<null>"))
  }

  @Test def test_getPackageName_String() = {
    assertEquals("org.apache.commons.lang3", ClassUtils.getPackageName(ClassUtils.getClass().getName))
    assertEquals("java.util", ClassUtils.getPackageName(classOf[util.Map.Entry[_, _]].getName))
    assertEquals("", ClassUtils.getPackageName(null.asInstanceOf[String]))
    assertEquals("", ClassUtils.getPackageName(""))
  }

  @Test def test_getShortCanonicalName_Class(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getShortCanonicalName(ClassUtils.getClass()))
    assertEquals("ClassUtils$[]", ClassUtils.getShortCanonicalName(classOf[Array[ClassUtils.type]]))
    assertEquals("ClassUtils$[][]", ClassUtils.getShortCanonicalName(classOf[Array[Array[ClassUtils.type]]]))
    assertEquals("int[]", ClassUtils.getShortCanonicalName(classOf[Array[Int]]))
    assertEquals("int[][]", ClassUtils.getShortCanonicalName(classOf[Array[Array[Int]]]))
    assertEquals("ClassUtilsTest", ClassUtils.getShortCanonicalName(classOf[ClassUtilsTest]))
// WARNING: this is fragile, implementation may change, naming is not guaranteed
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("ClassUtilsTest.$anon.8", ClassUtils.getShortCanonicalName(new AnyRef() {}.getClass))
//    assertEquals("ClassUtilsTest.Named.8", ClassUtils.getShortCanonicalName(classOf[Named]))
    assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortCanonicalName(classOf[ClassUtilsTest.Inner]))
  }

  @Test def test_getShortCanonicalName_Object(): Unit = {
    assertEquals("<null>", ClassUtils.getShortCanonicalName(null, "<null>"))
    assertEquals("ClassUtils$", ClassUtils.getShortCanonicalName(ClassUtils, "<null>"))
    assertEquals("ClassUtils$[]", ClassUtils.getShortCanonicalName(new Array[ClassUtils.type](0), "<null>"))
    assertEquals("ClassUtils$[][]", ClassUtils.getShortCanonicalName(Array.ofDim[ClassUtils.type](0, 0), "<null>"))
    assertEquals("int[]", ClassUtils.getShortCanonicalName(new Array[Int](0), "<null>"))
    assertEquals("int[][]", ClassUtils.getShortCanonicalName(Array.ofDim[Int](0, 0), "<null>"))
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("ClassUtilsTest.$anon.9", ClassUtils.getShortCanonicalName(new AnyRef() {}, "<null>"))
//    assertEquals("ClassUtilsTest.Named.9", ClassUtils.getShortCanonicalName(new Named, "<null>"))
    assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortCanonicalName(new ClassUtilsTest.Inner, "<null>"))
  }

  @Test def test_getShortCanonicalName_String(): Unit = {
    assertEquals("", ClassUtils.getShortCanonicalName(null.asInstanceOf[String]))
    assertEquals("Map.Entry", ClassUtils.getShortCanonicalName(classOf[util.Map.Entry[_, _]].getName))
    assertEquals("Entry", ClassUtils.getShortCanonicalName(classOf[util.Map.Entry[_, _]].getCanonicalName))
    assertEquals("ClassUtils", ClassUtils.getShortCanonicalName("org.apache.commons.lang3.ClassUtils"))
    assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName("[Lorg.apache.commons.lang3.ClassUtils;"))
    assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName("[[Lorg.apache.commons.lang3.ClassUtils;"))
    assertEquals("ClassUtils[]", ClassUtils.getShortCanonicalName("org.apache.commons.lang3.ClassUtils[]"))
    assertEquals("ClassUtils[][]", ClassUtils.getShortCanonicalName("org.apache.commons.lang3.ClassUtils[][]"))
    assertEquals("int[]", ClassUtils.getShortCanonicalName("[I"))
    assertEquals("int[]", ClassUtils.getShortCanonicalName(classOf[Array[Int]].getCanonicalName))
    assertEquals("int[]", ClassUtils.getShortCanonicalName(classOf[Array[Int]].getName))
    assertEquals("int[][]", ClassUtils.getShortCanonicalName("[[I"))
    assertEquals("int[]", ClassUtils.getShortCanonicalName("int[]"))
    assertEquals("int[][]", ClassUtils.getShortCanonicalName("int[][]"))
    // this is to demonstrate that the documentation and the naming of the methods
    // uses the class name and canonical name totally mixed up, which cannot be
    // fixed without backward compatibility break
    assertEquals("int[]", classOf[Array[Int]].getCanonicalName)
    assertEquals("[I", classOf[Array[Int]].getName)
    // Inner types... the problem is that these are not canonical names, classes with this name do not even have canonical name
    assertEquals("ClassUtilsTest.6", ClassUtils.getShortCanonicalName("org.apache.commons.lang3.ClassUtilsTest$6"))
    assertEquals(
      "ClassUtilsTest.5Named",
      ClassUtils.getShortCanonicalName("org.apache.commons.lang3.ClassUtilsTest$5Named"))
    assertEquals(
      "ClassUtilsTest.Inner",
      ClassUtils.getShortCanonicalName("org.apache.commons.lang3.ClassUtilsTest$Inner"))
    // demonstrating what a canonical name is... it is a bigger issue to clean this up
// TODO: Scala 2.11 has different results than 2.12/2.13
//    assertEquals("org.apache.commons.lang3.ClassUtilsTest$$anon$10", new ClassUtilsTest() {}.getClass.getName)
    assertNull(new ClassUtilsTest() {}.getClass.getCanonicalName)
  }

  @Test def test_getShortClassName_Class(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getShortClassName(ClassUtils.getClass()))
    assertEquals("Map.Entry", ClassUtils.getShortClassName(classOf[util.Map.Entry[_, _]]))
    assertEquals("", ClassUtils.getShortClassName(null.asInstanceOf[Class[_]]))
    assertEquals("String[]", ClassUtils.getShortClassName(classOf[Array[String]]))
    assertEquals("Map.Entry[]", ClassUtils.getShortClassName(classOf[Array[util.Map.Entry[_, _]]]))
    assertEquals("boolean", ClassUtils.getShortClassName(classOf[Boolean]))
    assertEquals("byte", ClassUtils.getShortClassName(classOf[Byte]))
    assertEquals("char", ClassUtils.getShortClassName(classOf[Char]))
    assertEquals("short", ClassUtils.getShortClassName(classOf[Short]))
    assertEquals("int", ClassUtils.getShortClassName(classOf[Int]))
    assertEquals("long", ClassUtils.getShortClassName(classOf[Long]))
    assertEquals("float", ClassUtils.getShortClassName(classOf[Float]))
    assertEquals("double", ClassUtils.getShortClassName(classOf[Double]))
    assertEquals("boolean[]", ClassUtils.getShortClassName(classOf[Array[Boolean]]))
    assertEquals("byte[]", ClassUtils.getShortClassName(classOf[Array[Byte]]))
    assertEquals("char[]", ClassUtils.getShortClassName(classOf[Array[Char]]))
    assertEquals("short[]", ClassUtils.getShortClassName(classOf[Array[Short]]))
    assertEquals("int[]", ClassUtils.getShortClassName(classOf[Array[Int]]))
    assertEquals("long[]", ClassUtils.getShortClassName(classOf[Array[Long]]))
    assertEquals("float[]", ClassUtils.getShortClassName(classOf[Array[Float]]))
    assertEquals("double[]", ClassUtils.getShortClassName(classOf[Array[Double]]))
    assertEquals("String[][]", ClassUtils.getShortClassName(classOf[Array[Array[String]]]))
    assertEquals("String[][][]", ClassUtils.getShortClassName(classOf[Array[Array[Array[String]]]]))
    assertEquals("String[][][][]", ClassUtils.getShortClassName(classOf[Array[Array[Array[Array[String]]]]]))

// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("ClassUtilsTest.$anon.12", ClassUtils.getShortClassName(new AnyRef() {}.getClass))
//    assertEquals("ClassUtilsTest.Named.10", ClassUtils.getShortClassName(classOf[Named]))
    assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(classOf[ClassUtilsTest.Inner]))
  }

  @Test def test_getShortClassName_Object(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getShortClassName(ClassUtils, "<null>"))
    assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(new ClassUtilsTest.Inner, "<null>"))
    assertEquals("String", ClassUtils.getShortClassName("hello", "<null>"))
    assertEquals("<null>", ClassUtils.getShortClassName(null, "<null>"))
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("ClassUtilsTest.$anon.13", ClassUtils.getShortClassName(new AnyRef() {}, "<null>"))
//    assertEquals("ClassUtilsTest.Named.11", ClassUtils.getShortClassName(new Named, "<null>"))
    assertEquals("ClassUtilsTest.Inner", ClassUtils.getShortClassName(new ClassUtilsTest.Inner, "<null>"))
  }

  @Test def test_getShortClassName_String(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getShortClassName(ClassUtils.getClass().getName))
    assertEquals("Map.Entry", ClassUtils.getShortClassName(classOf[util.Map.Entry[_, _]].getName))
    assertEquals("", ClassUtils.getShortClassName(null.asInstanceOf[String]))
    assertEquals("", ClassUtils.getShortClassName(""))
  }

  @Test def test_getSimpleName_Class(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getSimpleName(ClassUtils.getClass()))
    assertEquals("Entry", ClassUtils.getSimpleName(classOf[util.Map.Entry[_, _]]))
    assertEquals("", ClassUtils.getSimpleName(null))
    assertEquals("String[]", ClassUtils.getSimpleName(classOf[Array[String]]))
    assertEquals("Entry[]", ClassUtils.getSimpleName(classOf[Array[util.Map.Entry[_, _]]]))
    assertEquals("boolean", ClassUtils.getSimpleName(classOf[Boolean]))
    assertEquals("byte", ClassUtils.getSimpleName(classOf[Byte]))
    assertEquals("char", ClassUtils.getSimpleName(classOf[Char]))
    assertEquals("short", ClassUtils.getSimpleName(classOf[Short]))
    assertEquals("int", ClassUtils.getSimpleName(classOf[Int]))
    assertEquals("long", ClassUtils.getSimpleName(classOf[Long]))
    assertEquals("float", ClassUtils.getSimpleName(classOf[Float]))
    assertEquals("double", ClassUtils.getSimpleName(classOf[Double]))
    assertEquals("boolean[]", ClassUtils.getSimpleName(classOf[Array[Boolean]]))
    assertEquals("byte[]", ClassUtils.getSimpleName(classOf[Array[Byte]]))
    assertEquals("char[]", ClassUtils.getSimpleName(classOf[Array[Char]]))
    assertEquals("short[]", ClassUtils.getSimpleName(classOf[Array[Short]]))
    assertEquals("int[]", ClassUtils.getSimpleName(classOf[Array[Int]]))
    assertEquals("long[]", ClassUtils.getSimpleName(classOf[Array[Long]]))
    assertEquals("float[]", ClassUtils.getSimpleName(classOf[Array[Float]]))
    assertEquals("double[]", ClassUtils.getSimpleName(classOf[Array[Double]]))
    assertEquals("String[][]", ClassUtils.getSimpleName(classOf[Array[Array[String]]]))
    assertEquals("String[][][]", ClassUtils.getSimpleName(classOf[Array[Array[Array[String]]]]))
    assertEquals("String[][][][]", ClassUtils.getSimpleName(classOf[Array[Array[Array[Array[String]]]]]))
// TODO: Scala 2.11 has different results than 2.12/2.13
//    class Named {}
//    assertEquals("", ClassUtils.getSimpleName(new AnyRef() {}.getClass))
//    assertEquals("Named$12", ClassUtils.getSimpleName(classOf[Named]))
  }

  @Test def test_getSimpleName_Object(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getSimpleName(ClassUtils))
    assertEquals("Inner", ClassUtils.getSimpleName(new ClassUtilsTest.Inner))
    assertEquals("String", ClassUtils.getSimpleName("hello"))
    assertEquals(StringUtils.EMPTY, ClassUtils.getSimpleName(null))
    assertEquals(StringUtils.EMPTY, ClassUtils.getSimpleName(null))
  }

  @Test def test_getSimpleName_Object_String(): Unit = {
    assertEquals("ClassUtils$", ClassUtils.getSimpleName(ClassUtils, "<null>"))
    assertEquals("Inner", ClassUtils.getSimpleName(new ClassUtilsTest.Inner, "<null>"))
    assertEquals("String", ClassUtils.getSimpleName("hello", "<null>"))
    assertEquals("<null>", ClassUtils.getSimpleName(null, "<null>"))
    assertNull(ClassUtils.getSimpleName(null, null))
  }

  @Test def test_isAssignable(): Unit = {
    assertFalse(ClassUtils.isAssignable(null.asInstanceOf[Class[_]], null))
    assertFalse(ClassUtils.isAssignable(classOf[String], null))
    assertTrue(ClassUtils.isAssignable(null.asInstanceOf[Class[_]], classOf[Any]))
    assertTrue(ClassUtils.isAssignable(null.asInstanceOf[Class[_]], classOf[Integer]))
    assertFalse(ClassUtils.isAssignable(null.asInstanceOf[Class[_]], Integer.TYPE))
    assertTrue(ClassUtils.isAssignable(classOf[String], classOf[Any]))
    assertTrue(ClassUtils.isAssignable(classOf[String], classOf[String]))
    assertFalse(ClassUtils.isAssignable(classOf[Any], classOf[String]))

    assertTrue(ClassUtils.isAssignable(Integer.TYPE, classOf[Int]))
    //assertTrue(ClassUtils.isAssignable(Integer.TYPE, classOf[AnyRef]))
    assertTrue(ClassUtils.isAssignable(classOf[Int], Integer.TYPE))
    assertTrue(ClassUtils.isAssignable(classOf[Integer], classOf[Any]))
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE))
    assertTrue(ClassUtils.isAssignable(classOf[Integer], classOf[Integer]))

    assertTrue(ClassUtils.isAssignable(JavaBoolean.TYPE, classOf[Boolean]))
    //assertTrue(ClassUtils.isAssignable(JavaBoolean.TYPE, classOf[java.lang.Object]))
    assertTrue(ClassUtils.isAssignable(classOf[Boolean], JavaBoolean.TYPE))
    assertTrue(ClassUtils.isAssignable(classOf[JavaBoolean], classOf[Any]))
    assertTrue(ClassUtils.isAssignable(JavaBoolean.TYPE, JavaBoolean.TYPE))
    assertTrue(ClassUtils.isAssignable(classOf[Boolean], classOf[Boolean]))
  }

  @Test def test_isAssignable_Autoboxing(): Unit = {
    assertFalse(ClassUtils.isAssignable(null.asInstanceOf[Class[_]], null, true))
    assertFalse(ClassUtils.isAssignable(classOf[String], null, true))
    assertTrue(ClassUtils.isAssignable(null, classOf[Any], true))
    assertTrue(ClassUtils.isAssignable(null, classOf[Integer], true))
    assertFalse(ClassUtils.isAssignable(null, Integer.TYPE, true))
    assertTrue(ClassUtils.isAssignable(classOf[String], classOf[Any], true))
    assertTrue(ClassUtils.isAssignable(classOf[String], classOf[String], true))
    assertFalse(ClassUtils.isAssignable(classOf[Any], classOf[String], true))
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, classOf[Integer], true))
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, classOf[Any], true))
    assertTrue(ClassUtils.isAssignable(classOf[Integer], Integer.TYPE, true))
    assertTrue(ClassUtils.isAssignable(classOf[Integer], classOf[Any], true))
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE, true))
    assertTrue(ClassUtils.isAssignable(classOf[Integer], classOf[Integer], true))
    assertTrue(ClassUtils.isAssignable(JavaBoolean.TYPE, classOf[Boolean], true))
    assertTrue(ClassUtils.isAssignable(classOf[Boolean], JavaBoolean.TYPE, true))
    assertTrue(ClassUtils.isAssignable(classOf[Boolean], classOf[Any], true))
    assertTrue(ClassUtils.isAssignable(JavaBoolean.TYPE, JavaBoolean.TYPE, true))
    assertTrue(ClassUtils.isAssignable(classOf[Boolean], classOf[Boolean], true))
  }

  @Test def test_isAssignable_ClassArray_ClassArray(): Unit = {
    val array2 = Array[Class[_]](classOf[Any], classOf[Any])
    val array1 = Array[Class[_]](classOf[Any])
    val array1s = Array[Class[_]](classOf[String])
    val array0 = new Array[Class[_]](0)
    val arrayPrimitives: Array[Class[_]] = Array(Integer.TYPE, JavaBoolean.TYPE)
    val arrayWrappers: Array[Class[_]] = Array(classOf[Integer], classOf[Boolean])

    assertFalse(ClassUtils.isAssignable(array1, array2))
    assertFalse(ClassUtils.isAssignable(null, array2))
    assertTrue(ClassUtils.isAssignable(null, array0))
    assertTrue(ClassUtils.isAssignable(array0, array0))
    assertTrue(ClassUtils.isAssignable(array0, null.asInstanceOf[Array[Class[_]]])) // explicit cast to avoid warning
    assertTrue(ClassUtils.isAssignable(null, null.asInstanceOf[Array[Class[_]]]))
    assertFalse(ClassUtils.isAssignable(array1, array1s))
    assertTrue(ClassUtils.isAssignable(array1s, array1s))
    assertTrue(ClassUtils.isAssignable(array1s, array1))
    assertTrue(ClassUtils.isAssignable(arrayPrimitives, arrayWrappers))
    assertTrue(ClassUtils.isAssignable(arrayWrappers, arrayPrimitives))
    assertFalse(ClassUtils.isAssignable(arrayPrimitives, array1))
    assertFalse(ClassUtils.isAssignable(arrayWrappers, array1))
    assertTrue(ClassUtils.isAssignable(arrayPrimitives, array2))
    assertTrue(ClassUtils.isAssignable(arrayWrappers, array2))
  }

  @Test def test_isAssignable_ClassArray_ClassArray_Autoboxing(): Unit = {
    val array2 = Array[Class[_]](classOf[Any], classOf[Any])
    val array1 = Array[Class[_]](classOf[Any])
    val array1s = Array[Class[_]](classOf[String])
    val array0 = new Array[Class[_]](0)
    val arrayPrimitives: Array[Class[_]] = Array(Integer.TYPE, JavaBoolean.TYPE)
    val arrayWrappers: Array[Class[_]] = Array(classOf[Integer], classOf[Boolean])

    assertFalse(ClassUtils.isAssignable(array1, array2, true))
    assertFalse(ClassUtils.isAssignable(null, array2, true))
    assertTrue(ClassUtils.isAssignable(null, array0, true))
    assertTrue(ClassUtils.isAssignable(array0, array0, true))
    assertTrue(ClassUtils.isAssignable(array0, null, true))
    assertTrue(ClassUtils.isAssignable(null.asInstanceOf[Array[Class[_]]], null, true))
    assertFalse(ClassUtils.isAssignable(array1, array1s, true))
    assertTrue(ClassUtils.isAssignable(array1s, array1s, true))
    assertTrue(ClassUtils.isAssignable(array1s, array1, true))
    assertTrue(ClassUtils.isAssignable(arrayPrimitives, arrayWrappers, true))
    assertTrue(ClassUtils.isAssignable(arrayWrappers, arrayPrimitives, true))
    assertFalse(ClassUtils.isAssignable(arrayPrimitives, array1, true))
    assertFalse(ClassUtils.isAssignable(arrayWrappers, array1, true))
    assertTrue(ClassUtils.isAssignable(arrayPrimitives, array2, true))
    assertTrue(ClassUtils.isAssignable(arrayWrappers, array2, true))
  }

  @Test def test_isAssignable_ClassArray_ClassArray_NoAutoboxing(): Unit = {
    val array2 = Array[Class[_]](classOf[Any], classOf[Any])
    val array1 = Array[Class[_]](classOf[Any])
    val array1s = Array[Class[_]](classOf[String])
    val array0 = new Array[Class[_]](0)
    val arrayPrimitives: Array[Class[_]] = Array(Integer.TYPE, JavaBoolean.TYPE)
    val arrayWrappers: Array[Class[_]] = Array(classOf[Integer], classOf[Boolean])

    assertFalse(ClassUtils.isAssignable(array1, array2, false))
    assertFalse(ClassUtils.isAssignable(null, array2, false))
    assertTrue(ClassUtils.isAssignable(null, array0, false))
    assertTrue(ClassUtils.isAssignable(array0, array0, false))
    assertTrue(ClassUtils.isAssignable(array0, null, false))
    assertTrue(ClassUtils.isAssignable(null.asInstanceOf[Array[Class[_]]], null, false))
    assertFalse(ClassUtils.isAssignable(array1, array1s, false))
    assertTrue(ClassUtils.isAssignable(array1s, array1s, false))
    assertTrue(ClassUtils.isAssignable(array1s, array1, false))
    assertFalse(ClassUtils.isAssignable(arrayPrimitives, arrayWrappers, false))
    assertFalse(ClassUtils.isAssignable(arrayWrappers, arrayPrimitives, false))
    assertFalse(ClassUtils.isAssignable(arrayPrimitives, array1, false))
    assertFalse(ClassUtils.isAssignable(arrayWrappers, array1, false))
    //assertTrue(ClassUtils.isAssignable(arrayWrappers, array2, false))
    assertFalse(ClassUtils.isAssignable(arrayPrimitives, array2, false))
  }

  @Test def test_isAssignable_DefaultUnboxing_Widening(): Unit = { // test byte conversions
    assertFalse("byte -> char", ClassUtils.isAssignable(classOf[JavaByte], Character.TYPE))
    assertTrue("byte -> byte", ClassUtils.isAssignable(classOf[JavaByte], JavaByte.TYPE))
    assertTrue("byte -> short", ClassUtils.isAssignable(classOf[JavaByte], JavaShort.TYPE))
    assertTrue("byte -> int", ClassUtils.isAssignable(classOf[JavaByte], Integer.TYPE))
    assertTrue("byte -> long", ClassUtils.isAssignable(classOf[JavaByte], JavaLong.TYPE))
    assertTrue("byte -> float", ClassUtils.isAssignable(classOf[JavaByte], JavaFloat.TYPE))
    assertTrue("byte -> double", ClassUtils.isAssignable(classOf[JavaByte], JavaDouble.TYPE))
    assertFalse("byte -> boolean", ClassUtils.isAssignable(classOf[JavaByte], JavaBoolean.TYPE))
    // test short conversions
    assertFalse("short -> char", ClassUtils.isAssignable(classOf[JavaShort], Character.TYPE))
    assertFalse("short -> byte", ClassUtils.isAssignable(classOf[JavaShort], JavaByte.TYPE))
    assertTrue("short -> short", ClassUtils.isAssignable(classOf[JavaShort], JavaShort.TYPE))
    assertTrue("short -> int", ClassUtils.isAssignable(classOf[JavaShort], Integer.TYPE))
    assertTrue("short -> long", ClassUtils.isAssignable(classOf[JavaShort], JavaLong.TYPE))
    assertTrue("short -> float", ClassUtils.isAssignable(classOf[JavaShort], JavaFloat.TYPE))
    assertTrue("short -> double", ClassUtils.isAssignable(classOf[JavaShort], JavaDouble.TYPE))
    assertFalse("short -> boolean", ClassUtils.isAssignable(classOf[JavaShort], JavaBoolean.TYPE))
    // test char conversions
    assertTrue("char -> char", ClassUtils.isAssignable(classOf[Character], Character.TYPE))
    assertFalse("char -> byte", ClassUtils.isAssignable(classOf[Character], JavaByte.TYPE))
    assertFalse("char -> short", ClassUtils.isAssignable(classOf[Character], JavaShort.TYPE))
    assertTrue("char -> int", ClassUtils.isAssignable(classOf[Character], Integer.TYPE))
    assertTrue("char -> long", ClassUtils.isAssignable(classOf[Character], JavaLong.TYPE))
    assertTrue("char -> float", ClassUtils.isAssignable(classOf[Character], JavaFloat.TYPE))
    assertTrue("char -> double", ClassUtils.isAssignable(classOf[Character], JavaDouble.TYPE))
    assertFalse("char -> boolean", ClassUtils.isAssignable(classOf[Character], JavaBoolean.TYPE))
    // test int conversions
    assertFalse("int -> char", ClassUtils.isAssignable(classOf[Integer], Character.TYPE))
    assertFalse("int -> byte", ClassUtils.isAssignable(classOf[Integer], JavaByte.TYPE))
    assertFalse("int -> short", ClassUtils.isAssignable(classOf[Integer], JavaShort.TYPE))
    assertTrue("int -> int", ClassUtils.isAssignable(classOf[Integer], Integer.TYPE))
    assertTrue("int -> long", ClassUtils.isAssignable(classOf[Integer], JavaLong.TYPE))
    assertTrue("int -> float", ClassUtils.isAssignable(classOf[Integer], JavaFloat.TYPE))
    assertTrue("int -> double", ClassUtils.isAssignable(classOf[Integer], JavaDouble.TYPE))
    assertFalse("int -> boolean", ClassUtils.isAssignable(classOf[Integer], JavaBoolean.TYPE))
    // test long conversions
    assertFalse("long -> char", ClassUtils.isAssignable(classOf[JavaLong], Character.TYPE))
    assertFalse("long -> byte", ClassUtils.isAssignable(classOf[JavaLong], JavaByte.TYPE))
    assertFalse("long -> short", ClassUtils.isAssignable(classOf[JavaLong], JavaShort.TYPE))
    assertFalse("long -> int", ClassUtils.isAssignable(classOf[JavaLong], Integer.TYPE))
    assertTrue("long -> long", ClassUtils.isAssignable(classOf[JavaLong], JavaLong.TYPE))
    assertTrue("long -> float", ClassUtils.isAssignable(classOf[JavaLong], JavaFloat.TYPE))
    assertTrue("long -> double", ClassUtils.isAssignable(classOf[JavaLong], JavaDouble.TYPE))
    assertFalse("long -> boolean", ClassUtils.isAssignable(classOf[JavaLong], JavaBoolean.TYPE))
    // test float conversions
    assertFalse("float -> char", ClassUtils.isAssignable(classOf[JavaFloat], Character.TYPE))
    assertFalse("float -> byte", ClassUtils.isAssignable(classOf[JavaFloat], JavaByte.TYPE))
    assertFalse("float -> short", ClassUtils.isAssignable(classOf[JavaFloat], JavaShort.TYPE))
    assertFalse("float -> int", ClassUtils.isAssignable(classOf[JavaFloat], Integer.TYPE))
    assertFalse("float -> long", ClassUtils.isAssignable(classOf[JavaFloat], JavaLong.TYPE))
    assertTrue("float -> float", ClassUtils.isAssignable(classOf[JavaFloat], JavaFloat.TYPE))
    assertTrue("float -> double", ClassUtils.isAssignable(classOf[JavaFloat], JavaDouble.TYPE))
    assertFalse("float -> boolean", ClassUtils.isAssignable(classOf[JavaFloat], JavaBoolean.TYPE))
    // test double conversions
    assertFalse("double -> char", ClassUtils.isAssignable(classOf[JavaDouble], Character.TYPE))
    assertFalse("double -> byte", ClassUtils.isAssignable(classOf[JavaDouble], JavaByte.TYPE))
    assertFalse("double -> short", ClassUtils.isAssignable(classOf[JavaDouble], JavaShort.TYPE))
    assertFalse("double -> int", ClassUtils.isAssignable(classOf[JavaDouble], Integer.TYPE))
    assertFalse("double -> long", ClassUtils.isAssignable(classOf[JavaDouble], JavaLong.TYPE))
    assertFalse("double -> float", ClassUtils.isAssignable(classOf[JavaDouble], JavaFloat.TYPE))
    assertTrue("double -> double", ClassUtils.isAssignable(classOf[JavaDouble], JavaDouble.TYPE))
    assertFalse("double -> boolean", ClassUtils.isAssignable(classOf[JavaDouble], JavaBoolean.TYPE))
    // test boolean conversions
    assertFalse("boolean -> char", ClassUtils.isAssignable(classOf[JavaBoolean], Character.TYPE))
    assertFalse("boolean -> byte", ClassUtils.isAssignable(classOf[JavaBoolean], JavaByte.TYPE))
    assertFalse("boolean -> short", ClassUtils.isAssignable(classOf[JavaBoolean], JavaShort.TYPE))
    assertFalse("boolean -> int", ClassUtils.isAssignable(classOf[JavaBoolean], Integer.TYPE))
    assertFalse("boolean -> long", ClassUtils.isAssignable(classOf[JavaBoolean], JavaLong.TYPE))
    assertFalse("boolean -> float", ClassUtils.isAssignable(classOf[JavaBoolean], JavaFloat.TYPE))
    assertFalse("boolean -> double", ClassUtils.isAssignable(classOf[JavaBoolean], JavaDouble.TYPE))
    assertTrue("boolean -> boolean", ClassUtils.isAssignable(classOf[JavaBoolean], JavaBoolean.TYPE))
  }

  @Test def test_isAssignable_NoAutoboxing(): Unit = {
    assertFalse(ClassUtils.isAssignable(null.asInstanceOf[Class[_]], null, false))
    assertFalse(ClassUtils.isAssignable(classOf[String], null, false))
    assertTrue(ClassUtils.isAssignable(null, classOf[Any], false))
    assertTrue(ClassUtils.isAssignable(null, classOf[Integer], false))
    assertFalse(ClassUtils.isAssignable(null, Integer.TYPE, false))
    assertTrue(ClassUtils.isAssignable(classOf[String], classOf[Any], false))
    assertTrue(ClassUtils.isAssignable(classOf[String], classOf[String], false))
    assertFalse(ClassUtils.isAssignable(classOf[Any], classOf[String], false))
    assertFalse(ClassUtils.isAssignable(Integer.TYPE, classOf[Integer], false))
    assertFalse(ClassUtils.isAssignable(Integer.TYPE, classOf[Any], false))
    assertFalse(ClassUtils.isAssignable(classOf[Integer], Integer.TYPE, false))
    assertTrue(ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE, false))
    assertTrue(ClassUtils.isAssignable(classOf[Integer], classOf[Integer], false))
    assertFalse(ClassUtils.isAssignable(JavaBoolean.TYPE, classOf[JavaBoolean], false))
    assertFalse(ClassUtils.isAssignable(JavaBoolean.TYPE, classOf[Any], false))
    assertFalse(ClassUtils.isAssignable(classOf[JavaBoolean], JavaBoolean.TYPE, false))
    assertTrue(ClassUtils.isAssignable(classOf[JavaBoolean], classOf[AnyRef], false))
    assertTrue(ClassUtils.isAssignable(JavaBoolean.TYPE, JavaBoolean.TYPE, false))
    assertTrue(ClassUtils.isAssignable(classOf[Boolean], classOf[Boolean], false))
  }

  @Test def test_isAssignable_Unboxing_Widening(): Unit = {
    assertFalse("byte -> char", ClassUtils.isAssignable(classOf[Byte], Character.TYPE, true))
    assertTrue("byte -> byte", ClassUtils.isAssignable(classOf[Byte], JavaByte.TYPE, true))
    assertTrue("byte -> short", ClassUtils.isAssignable(classOf[Byte], JavaShort.TYPE, true))
    assertTrue("byte -> int", ClassUtils.isAssignable(classOf[Byte], Integer.TYPE, true))
    assertTrue("byte -> long", ClassUtils.isAssignable(classOf[Byte], JavaLong.TYPE, true))
    assertTrue("byte -> float", ClassUtils.isAssignable(classOf[Byte], JavaFloat.TYPE, true))
    assertTrue("byte -> double", ClassUtils.isAssignable(classOf[Byte], JavaDouble.TYPE, true))
    assertFalse("byte -> boolean", ClassUtils.isAssignable(classOf[Byte], JavaBoolean.TYPE, true))
    assertFalse("short -> char", ClassUtils.isAssignable(classOf[Short], Character.TYPE, true))
    assertFalse("short -> byte", ClassUtils.isAssignable(classOf[Short], JavaByte.TYPE, true))
    assertTrue("short -> short", ClassUtils.isAssignable(classOf[Short], JavaShort.TYPE, true))
    assertTrue("short -> int", ClassUtils.isAssignable(classOf[Short], Integer.TYPE, true))
    assertTrue("short -> long", ClassUtils.isAssignable(classOf[Short], JavaLong.TYPE, true))
    assertTrue("short -> float", ClassUtils.isAssignable(classOf[Short], JavaFloat.TYPE, true))
    assertTrue("short -> double", ClassUtils.isAssignable(classOf[Short], JavaDouble.TYPE, true))
    assertFalse("short -> boolean", ClassUtils.isAssignable(classOf[Short], JavaBoolean.TYPE, true))
    assertTrue("char -> char", ClassUtils.isAssignable(classOf[Character], Character.TYPE, true))
    assertFalse("char -> byte", ClassUtils.isAssignable(classOf[Character], JavaByte.TYPE, true))
    assertFalse("char -> short", ClassUtils.isAssignable(classOf[Character], JavaShort.TYPE, true))
    assertTrue("char -> int", ClassUtils.isAssignable(classOf[Character], Integer.TYPE, true))
    assertTrue("char -> long", ClassUtils.isAssignable(classOf[Character], JavaLong.TYPE, true))
    assertTrue("char -> float", ClassUtils.isAssignable(classOf[Character], JavaFloat.TYPE, true))
    assertTrue("char -> double", ClassUtils.isAssignable(classOf[Character], JavaDouble.TYPE, true))
    assertFalse("char -> boolean", ClassUtils.isAssignable(classOf[Character], JavaBoolean.TYPE, true))
    assertFalse("int -> char", ClassUtils.isAssignable(classOf[Integer], Character.TYPE, true))
    assertFalse("int -> byte", ClassUtils.isAssignable(classOf[Integer], JavaByte.TYPE, true))
    assertFalse("int -> short", ClassUtils.isAssignable(classOf[Integer], JavaShort.TYPE, true))
    assertTrue("int -> int", ClassUtils.isAssignable(classOf[Integer], Integer.TYPE, true))
    assertTrue("int -> long", ClassUtils.isAssignable(classOf[Integer], JavaLong.TYPE, true))
    assertTrue("int -> float", ClassUtils.isAssignable(classOf[Integer], JavaFloat.TYPE, true))
    assertTrue("int -> double", ClassUtils.isAssignable(classOf[Integer], JavaDouble.TYPE, true))
    assertFalse("int -> boolean", ClassUtils.isAssignable(classOf[Integer], JavaBoolean.TYPE, true))
    assertFalse("long -> char", ClassUtils.isAssignable(classOf[Long], Character.TYPE, true))
    assertFalse("long -> byte", ClassUtils.isAssignable(classOf[Long], JavaByte.TYPE, true))
    assertFalse("long -> short", ClassUtils.isAssignable(classOf[Long], JavaShort.TYPE, true))
    assertFalse("long -> int", ClassUtils.isAssignable(classOf[Long], Integer.TYPE, true))
    assertTrue("long -> long", ClassUtils.isAssignable(classOf[Long], JavaLong.TYPE, true))
    assertTrue("long -> float", ClassUtils.isAssignable(classOf[Long], JavaFloat.TYPE, true))
    assertTrue("long -> double", ClassUtils.isAssignable(classOf[Long], JavaDouble.TYPE, true))
    assertFalse("long -> boolean", ClassUtils.isAssignable(classOf[Long], JavaBoolean.TYPE, true))
    assertFalse("float -> char", ClassUtils.isAssignable(classOf[Float], Character.TYPE, true))
    assertFalse("float -> byte", ClassUtils.isAssignable(classOf[Float], JavaByte.TYPE, true))
    assertFalse("float -> short", ClassUtils.isAssignable(classOf[Float], JavaShort.TYPE, true))
    assertFalse("float -> int", ClassUtils.isAssignable(classOf[Float], Integer.TYPE, true))
    assertFalse("float -> long", ClassUtils.isAssignable(classOf[Float], JavaLong.TYPE, true))
    assertTrue("float -> float", ClassUtils.isAssignable(classOf[Float], JavaFloat.TYPE, true))
    assertTrue("float -> double", ClassUtils.isAssignable(classOf[Float], JavaDouble.TYPE, true))
    assertFalse("float -> boolean", ClassUtils.isAssignable(classOf[Float], JavaBoolean.TYPE, true))
    assertFalse("double -> char", ClassUtils.isAssignable(classOf[Double], Character.TYPE, true))
    assertFalse("double -> byte", ClassUtils.isAssignable(classOf[Double], JavaByte.TYPE, true))
    assertFalse("double -> short", ClassUtils.isAssignable(classOf[Double], JavaShort.TYPE, true))
    assertFalse("double -> int", ClassUtils.isAssignable(classOf[Double], Integer.TYPE, true))
    assertFalse("double -> long", ClassUtils.isAssignable(classOf[Double], JavaLong.TYPE, true))
    assertFalse("double -> float", ClassUtils.isAssignable(classOf[Double], JavaFloat.TYPE, true))
    assertTrue("double -> double", ClassUtils.isAssignable(classOf[Double], JavaDouble.TYPE, true))
    assertFalse("double -> boolean", ClassUtils.isAssignable(classOf[Double], JavaBoolean.TYPE, true))
    assertFalse("boolean -> char", ClassUtils.isAssignable(classOf[Boolean], Character.TYPE, true))
    assertFalse("boolean -> byte", ClassUtils.isAssignable(classOf[Boolean], JavaByte.TYPE, true))
    assertFalse("boolean -> short", ClassUtils.isAssignable(classOf[Boolean], JavaShort.TYPE, true))
    assertFalse("boolean -> int", ClassUtils.isAssignable(classOf[Boolean], Integer.TYPE, true))
    assertFalse("boolean -> long", ClassUtils.isAssignable(classOf[Boolean], JavaLong.TYPE, true))
    assertFalse("boolean -> float", ClassUtils.isAssignable(classOf[Boolean], JavaFloat.TYPE, true))
    assertFalse("boolean -> double", ClassUtils.isAssignable(classOf[Boolean], JavaDouble.TYPE, true))
    assertTrue("boolean -> boolean", ClassUtils.isAssignable(classOf[Boolean], JavaBoolean.TYPE, true))
  }

  @Test def test_isAssignable_Widening(): Unit = {
    assertFalse("byte -> char", ClassUtils.isAssignable(JavaByte.TYPE, Character.TYPE))
    assertTrue("byte -> byte", ClassUtils.isAssignable(JavaByte.TYPE, JavaByte.TYPE))
    assertTrue("byte -> short", ClassUtils.isAssignable(JavaByte.TYPE, JavaShort.TYPE))
    assertTrue("byte -> int", ClassUtils.isAssignable(JavaByte.TYPE, Integer.TYPE))
    assertTrue("byte -> long", ClassUtils.isAssignable(JavaByte.TYPE, JavaLong.TYPE))
    assertTrue("byte -> float", ClassUtils.isAssignable(JavaByte.TYPE, JavaFloat.TYPE))
    assertTrue("byte -> double", ClassUtils.isAssignable(JavaByte.TYPE, JavaDouble.TYPE))
    assertFalse("byte -> boolean", ClassUtils.isAssignable(JavaByte.TYPE, JavaBoolean.TYPE))
    assertFalse("short -> char", ClassUtils.isAssignable(JavaShort.TYPE, Character.TYPE))
    assertFalse("short -> byte", ClassUtils.isAssignable(JavaShort.TYPE, JavaByte.TYPE))
    assertTrue("short -> short", ClassUtils.isAssignable(JavaShort.TYPE, JavaShort.TYPE))
    assertTrue("short -> int", ClassUtils.isAssignable(JavaShort.TYPE, Integer.TYPE))
    assertTrue("short -> long", ClassUtils.isAssignable(JavaShort.TYPE, JavaLong.TYPE))
    assertTrue("short -> float", ClassUtils.isAssignable(JavaShort.TYPE, JavaFloat.TYPE))
    assertTrue("short -> double", ClassUtils.isAssignable(JavaShort.TYPE, JavaDouble.TYPE))
    assertFalse("short -> boolean", ClassUtils.isAssignable(JavaShort.TYPE, JavaBoolean.TYPE))
    assertTrue("char -> char", ClassUtils.isAssignable(Character.TYPE, Character.TYPE))
    assertFalse("char -> byte", ClassUtils.isAssignable(Character.TYPE, JavaByte.TYPE))
    assertFalse("char -> short", ClassUtils.isAssignable(Character.TYPE, JavaShort.TYPE))
    assertTrue("char -> int", ClassUtils.isAssignable(Character.TYPE, Integer.TYPE))
    assertTrue("char -> long", ClassUtils.isAssignable(Character.TYPE, JavaLong.TYPE))
    assertTrue("char -> float", ClassUtils.isAssignable(Character.TYPE, JavaFloat.TYPE))
    assertTrue("char -> double", ClassUtils.isAssignable(Character.TYPE, JavaDouble.TYPE))
    assertFalse("char -> boolean", ClassUtils.isAssignable(Character.TYPE, JavaBoolean.TYPE))
    assertFalse("int -> char", ClassUtils.isAssignable(Integer.TYPE, Character.TYPE))
    assertFalse("int -> byte", ClassUtils.isAssignable(Integer.TYPE, JavaByte.TYPE))
    assertFalse("int -> short", ClassUtils.isAssignable(Integer.TYPE, JavaShort.TYPE))
    assertTrue("int -> int", ClassUtils.isAssignable(Integer.TYPE, Integer.TYPE))
    assertTrue("int -> long", ClassUtils.isAssignable(Integer.TYPE, JavaLong.TYPE))
    assertTrue("int -> float", ClassUtils.isAssignable(Integer.TYPE, JavaFloat.TYPE))
    assertTrue("int -> double", ClassUtils.isAssignable(Integer.TYPE, JavaDouble.TYPE))
    assertFalse("int -> boolean", ClassUtils.isAssignable(Integer.TYPE, JavaBoolean.TYPE))
    assertFalse("long -> char", ClassUtils.isAssignable(JavaLong.TYPE, Character.TYPE))
    assertFalse("long -> byte", ClassUtils.isAssignable(JavaLong.TYPE, JavaByte.TYPE))
    assertFalse("long -> short", ClassUtils.isAssignable(JavaLong.TYPE, JavaShort.TYPE))
    assertFalse("long -> int", ClassUtils.isAssignable(JavaLong.TYPE, Integer.TYPE))
    assertTrue("long -> long", ClassUtils.isAssignable(JavaLong.TYPE, JavaLong.TYPE))
    assertTrue("long -> float", ClassUtils.isAssignable(JavaLong.TYPE, JavaFloat.TYPE))
    assertTrue("long -> double", ClassUtils.isAssignable(JavaLong.TYPE, JavaDouble.TYPE))
    assertFalse("long -> boolean", ClassUtils.isAssignable(JavaLong.TYPE, JavaBoolean.TYPE))
    assertFalse("float -> char", ClassUtils.isAssignable(JavaFloat.TYPE, Character.TYPE))
    assertFalse("float -> byte", ClassUtils.isAssignable(JavaFloat.TYPE, JavaByte.TYPE))
    assertFalse("float -> short", ClassUtils.isAssignable(JavaFloat.TYPE, JavaShort.TYPE))
    assertFalse("float -> int", ClassUtils.isAssignable(JavaFloat.TYPE, Integer.TYPE))
    assertFalse("float -> long", ClassUtils.isAssignable(JavaFloat.TYPE, JavaLong.TYPE))
    assertTrue("float -> float", ClassUtils.isAssignable(JavaFloat.TYPE, JavaFloat.TYPE))
    assertTrue("float -> double", ClassUtils.isAssignable(JavaFloat.TYPE, JavaDouble.TYPE))
    assertFalse("float -> boolean", ClassUtils.isAssignable(JavaFloat.TYPE, JavaBoolean.TYPE))
    assertFalse("double -> char", ClassUtils.isAssignable(JavaDouble.TYPE, Character.TYPE))
    assertFalse("double -> byte", ClassUtils.isAssignable(JavaDouble.TYPE, JavaByte.TYPE))
    assertFalse("double -> short", ClassUtils.isAssignable(JavaDouble.TYPE, JavaShort.TYPE))
    assertFalse("double -> int", ClassUtils.isAssignable(JavaDouble.TYPE, Integer.TYPE))
    assertFalse("double -> long", ClassUtils.isAssignable(JavaDouble.TYPE, JavaLong.TYPE))
    assertFalse("double -> float", ClassUtils.isAssignable(JavaDouble.TYPE, JavaFloat.TYPE))
    assertTrue("double -> double", ClassUtils.isAssignable(JavaDouble.TYPE, JavaDouble.TYPE))
    assertFalse("double -> boolean", ClassUtils.isAssignable(JavaDouble.TYPE, JavaBoolean.TYPE))
    assertFalse("boolean -> char", ClassUtils.isAssignable(JavaBoolean.TYPE, Character.TYPE))
    assertFalse("boolean -> byte", ClassUtils.isAssignable(JavaBoolean.TYPE, JavaByte.TYPE))
    assertFalse("boolean -> short", ClassUtils.isAssignable(JavaBoolean.TYPE, JavaShort.TYPE))
    assertFalse("boolean -> int", ClassUtils.isAssignable(JavaBoolean.TYPE, Integer.TYPE))
    assertFalse("boolean -> long", ClassUtils.isAssignable(JavaBoolean.TYPE, JavaLong.TYPE))
    assertFalse("boolean -> float", ClassUtils.isAssignable(JavaBoolean.TYPE, JavaFloat.TYPE))
    assertFalse("boolean -> double", ClassUtils.isAssignable(JavaBoolean.TYPE, JavaDouble.TYPE))
    assertTrue("boolean -> boolean", ClassUtils.isAssignable(JavaBoolean.TYPE, JavaBoolean.TYPE))
  }

  @Test def test_isInnerClass_Class(): Unit = {
    assertTrue(ClassUtils.isInnerClass(classOf[ClassUtilsTest.Inner]))
    assertTrue(ClassUtils.isInnerClass(classOf[util.Map.Entry[_, _]]))
    assertTrue(ClassUtils.isInnerClass(new Cloneable() {}.getClass))
    assertFalse(ClassUtils.isInnerClass(this.getClass))
    assertFalse(ClassUtils.isInnerClass(classOf[String]))
    assertFalse(ClassUtils.isInnerClass(null))
  }

//  @Test def testConstructor(): Unit = {
//    assertNotNull(new ClassUtils.type)
//    val cons = classOf[ClassUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[ClassUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[ClassUtils.type].getModifiers))
//  }

  @Test
  @throws[ClassNotFoundException]
  def testGetClassByNormalNameArrays(): Unit = {
    assertEquals(classOf[Array[Int]], ClassUtils.getClass("int[]"))
    assertEquals(classOf[Array[Long]], ClassUtils.getClass("long[]"))
    assertEquals(classOf[Array[Short]], ClassUtils.getClass("short[]"))
    assertEquals(classOf[Array[Byte]], ClassUtils.getClass("byte[]"))
    assertEquals(classOf[Array[Char]], ClassUtils.getClass("char[]"))
    assertEquals(classOf[Array[Float]], ClassUtils.getClass("float[]"))
    assertEquals(classOf[Array[Double]], ClassUtils.getClass("double[]"))
    assertEquals(classOf[Array[Boolean]], ClassUtils.getClass("boolean[]"))
    assertEquals(classOf[Array[String]], ClassUtils.getClass("java.lang.String[]"))
    assertEquals(classOf[Array[util.Map.Entry[_, _]]], ClassUtils.getClass("java.util.Map.Entry[]"))
    assertEquals(classOf[Array[util.Map.Entry[_, _]]], ClassUtils.getClass("java.util.Map$Entry[]"))
    assertEquals(classOf[Array[util.Map.Entry[_, _]]], ClassUtils.getClass("[Ljava.util.Map.Entry;"))
    assertEquals(classOf[Array[util.Map.Entry[_, _]]], ClassUtils.getClass("[Ljava.util.Map$Entry;"))
  }

  @Test
  @throws[ClassNotFoundException]
  def testGetClassByNormalNameArrays2D(): Unit = {
    assertEquals(classOf[Array[Array[Int]]], ClassUtils.getClass("int[][]"))
    assertEquals(classOf[Array[Array[Long]]], ClassUtils.getClass("long[][]"))
    assertEquals(classOf[Array[Array[Short]]], ClassUtils.getClass("short[][]"))
    assertEquals(classOf[Array[Array[Byte]]], ClassUtils.getClass("byte[][]"))
    assertEquals(classOf[Array[Array[Char]]], ClassUtils.getClass("char[][]"))
    assertEquals(classOf[Array[Array[Float]]], ClassUtils.getClass("float[][]"))
    assertEquals(classOf[Array[Array[Double]]], ClassUtils.getClass("double[][]"))
    assertEquals(classOf[Array[Array[Boolean]]], ClassUtils.getClass("boolean[][]"))
    assertEquals(classOf[Array[Array[String]]], ClassUtils.getClass("java.lang.String[][]"))
  }

  @Test
  @throws[Exception]
  def testGetClassClassNotFound(): Unit = {
    assertGetClassThrowsClassNotFound("bool")
    assertGetClassThrowsClassNotFound("bool[]")
    assertGetClassThrowsClassNotFound("integer[]")
  }

  @Test
  @throws[Exception]
  def testGetClassInvalidArguments(): Unit = {
    assertGetClassThrowsNullPointerException(null)
    assertGetClassThrowsClassNotFound("[][][]")
    assertGetClassThrowsClassNotFound("[[]")
    assertGetClassThrowsClassNotFound("[")
    assertGetClassThrowsClassNotFound("java.lang.String][")
    assertGetClassThrowsClassNotFound(".hello.world")
    assertGetClassThrowsClassNotFound("hello..world")
  }

  @Test
  @throws[ClassNotFoundException]
  def testGetClassRawPrimitives(): Unit = {
    assertEquals(classOf[Int], ClassUtils.getClass("int"))
    assertEquals(classOf[Long], ClassUtils.getClass("long"))
    assertEquals(classOf[Short], ClassUtils.getClass("short"))
    assertEquals(classOf[Byte], ClassUtils.getClass("byte"))
    assertEquals(classOf[Char], ClassUtils.getClass("char"))
    assertEquals(classOf[Float], ClassUtils.getClass("float"))
    assertEquals(classOf[Double], ClassUtils.getClass("double"))
    assertEquals(classOf[Boolean], ClassUtils.getClass("boolean"))
    assertEquals(classOf[Unit], ClassUtils.getClass("void"))
  }

  @Test
  @throws[Exception]
  def testGetClassWithArrayClasses(): Unit = {
    assertGetClassReturnsClass(classOf[Array[String]])
    assertGetClassReturnsClass(classOf[Array[Int]])
    assertGetClassReturnsClass(classOf[Array[Long]])
    assertGetClassReturnsClass(classOf[Array[Short]])
    assertGetClassReturnsClass(classOf[Array[Byte]])
    assertGetClassReturnsClass(classOf[Array[Char]])
    assertGetClassReturnsClass(classOf[Array[Float]])
    assertGetClassReturnsClass(classOf[Array[Double]])
    assertGetClassReturnsClass(classOf[Array[Boolean]])
  }

  @Test
  @throws[Exception]
  def testGetClassWithArrayClasses2D(): Unit = {
    assertGetClassReturnsClass(classOf[Array[Array[String]]])
    assertGetClassReturnsClass(classOf[Array[Array[Int]]])
    assertGetClassReturnsClass(classOf[Array[Array[Long]]])
    assertGetClassReturnsClass(classOf[Array[Array[Short]]])
    assertGetClassReturnsClass(classOf[Array[Array[Byte]]])
    assertGetClassReturnsClass(classOf[Array[Array[Char]]])
    assertGetClassReturnsClass(classOf[Array[Array[Float]]])
    assertGetClassReturnsClass(classOf[Array[Array[Double]]])
    assertGetClassReturnsClass(classOf[Array[Array[Boolean]]])
  }

  @Test
  @throws[ClassNotFoundException]
  def testGetInnerClass(): Unit = {
    assertEquals(
      classOf[ClassUtilsTest.Inner#DeeplyNested],
      ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest.Inner.DeeplyNested"))
    assertEquals(
      classOf[ClassUtilsTest.Inner#DeeplyNested],
      ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest.Inner$DeeplyNested"))
    assertEquals(
      classOf[ClassUtilsTest.Inner#DeeplyNested],
      ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest$Inner$DeeplyNested"))
    assertEquals(
      classOf[ClassUtilsTest.Inner#DeeplyNested],
      ClassUtils.getClass("org.apache.commons.lang3.ClassUtilsTest$Inner.DeeplyNested"))
  }

  @Test
  @throws[Exception]
  def testGetPublicMethod(): Unit = { // Tests with Collections$UnmodifiableSet
    val set = Collections.unmodifiableSet(new util.HashSet[AnyRef])
    val isEmptyMethod = ClassUtils.getPublicMethod(set.getClass, "isEmpty")
    assertTrue(Modifier.isPublic(isEmptyMethod.getDeclaringClass.getModifiers))
    assertTrue(isEmptyMethod.invoke(set).asInstanceOf[Boolean])
    // Tests with a public Class
    val toStringMethod = ClassUtils.getPublicMethod(classOf[Any], "toString")
    assertEquals(classOf[Any].getMethod("toString"), toStringMethod)
  }

  @Test def testHierarchyExcludingInterfaces(): Unit = {
    val iter = ClassUtils.hierarchy(classOf[StringParameterizedChild]).iterator
    assertEquals(classOf[StringParameterizedChild], iter.next)
    assertEquals(classOf[GenericParent[_]], iter.next)
    assertEquals(classOf[Any], iter.next)
    assertFalse(iter.hasNext)
  }

  @Test def testHierarchyIncludingInterfaces(): Unit = {
    val iter = ClassUtils.hierarchy(classOf[StringParameterizedChild], Interfaces.INCLUDE).iterator
    assertEquals(classOf[StringParameterizedChild], iter.next)
    assertEquals(classOf[GenericParent[_]], iter.next)
    assertEquals(classOf[GenericConsumer[_]], iter.next)
    assertEquals(classOf[Any], iter.next)
    assertFalse(iter.hasNext)
  }

  @Test def testIsPrimitiveOrWrapper(): Unit = { // test primitive wrapper classes
    assertTrue("Boolean.class", ClassUtils.isPrimitiveOrWrapper(classOf[Boolean]))
    assertTrue("Byte.class", ClassUtils.isPrimitiveOrWrapper(classOf[Byte]))
    assertTrue("Character.class", ClassUtils.isPrimitiveOrWrapper(classOf[Char]))
    assertTrue("Short.class", ClassUtils.isPrimitiveOrWrapper(classOf[Short]))
    assertTrue("Integer.class", ClassUtils.isPrimitiveOrWrapper(classOf[Int]))
    assertTrue("Long.class", ClassUtils.isPrimitiveOrWrapper(classOf[Long]))
    assertTrue("Double.class", ClassUtils.isPrimitiveOrWrapper(classOf[Double]))
    assertTrue("Float.class", ClassUtils.isPrimitiveOrWrapper(classOf[Float]))
    // test primitive classes
    assertTrue("boolean", ClassUtils.isPrimitiveOrWrapper(JavaBoolean.TYPE))
    assertTrue("byte", ClassUtils.isPrimitiveOrWrapper(JavaByte.TYPE))
    assertTrue("char", ClassUtils.isPrimitiveOrWrapper(Character.TYPE))
    assertTrue("short", ClassUtils.isPrimitiveOrWrapper(JavaShort.TYPE))
    assertTrue("int", ClassUtils.isPrimitiveOrWrapper(Integer.TYPE))
    assertTrue("long", ClassUtils.isPrimitiveOrWrapper(JavaLong.TYPE))
    assertTrue("double", ClassUtils.isPrimitiveOrWrapper(JavaDouble.TYPE))
    assertTrue("float", ClassUtils.isPrimitiveOrWrapper(JavaFloat.TYPE))
    assertTrue("Void.TYPE", ClassUtils.isPrimitiveOrWrapper(Void.TYPE))
    // others
    assertFalse("null", ClassUtils.isPrimitiveOrWrapper(null))
    //assertFalse("Void.class", ClassUtils.isPrimitiveOrWrapper(classOf[Void]))
    assertFalse("String.class", ClassUtils.isPrimitiveOrWrapper(classOf[String]))
    assertFalse("this.getClass()", ClassUtils.isPrimitiveOrWrapper(this.getClass))
  }

  @Test def testIsPrimitiveWrapper(): Unit = {
    assertTrue("Boolean.class", ClassUtils.isPrimitiveWrapper(classOf[JavaBoolean]))
    assertTrue("Byte.class", ClassUtils.isPrimitiveWrapper(classOf[JavaByte]))
    assertTrue("Character.class", ClassUtils.isPrimitiveWrapper(classOf[Character]))
    assertTrue("Short.class", ClassUtils.isPrimitiveWrapper(classOf[JavaShort]))
    assertTrue("Integer.class", ClassUtils.isPrimitiveWrapper(classOf[Integer]))
    assertTrue("Long.class", ClassUtils.isPrimitiveWrapper(classOf[JavaLong]))
    assertTrue("Double.class", ClassUtils.isPrimitiveWrapper(classOf[JavaDouble]))
    assertTrue("Float.class", ClassUtils.isPrimitiveWrapper(classOf[JavaFloat]))

    assertFalse("boolean", ClassUtils.isPrimitiveWrapper(JavaBoolean.TYPE))
    assertFalse("byte", ClassUtils.isPrimitiveWrapper(JavaByte.TYPE))
    assertFalse("char", ClassUtils.isPrimitiveWrapper(Character.TYPE))
    assertFalse("short", ClassUtils.isPrimitiveWrapper(JavaShort.TYPE))
    assertFalse("int", ClassUtils.isPrimitiveWrapper(Integer.TYPE))
    assertFalse("long", ClassUtils.isPrimitiveWrapper(JavaLong.TYPE))
    assertFalse("double", ClassUtils.isPrimitiveWrapper(JavaDouble.TYPE))
    assertFalse("float", ClassUtils.isPrimitiveWrapper(JavaFloat.TYPE))
    assertFalse("null", ClassUtils.isPrimitiveWrapper(null))
    //assertFalse("Void.class", ClassUtils.isPrimitiveWrapper(classOf[java.lang.Void]))
    assertFalse("Void.TYPE", ClassUtils.isPrimitiveWrapper(Void.TYPE))
    assertFalse("String.class", ClassUtils.isPrimitiveWrapper(classOf[String]))
    assertFalse("this.getClass()", ClassUtils.isPrimitiveWrapper(this.getClass))
  }

  @Test def testPrimitivesToWrappers(): Unit = { // test null
    //        assertNull("null -> null", ClassUtils.primitivesToWrappers(null)); // generates warning
    assertNull(
      "null -> null",
      ClassUtils.primitivesToWrappers(null.asInstanceOf[Array[Class[_]]])
    ) // equivalent cast to avoid warning
    // Other possible casts for null
    assertArrayEquals(
      "empty -> empty",
      ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[Object]],
      ClassUtils.primitivesToWrappers().asInstanceOf[Array[Object]])
    val castNull = ClassUtils.primitivesToWrappers(null.asInstanceOf[Class[_]]) // == new Class<?>[]{null}
    assertArrayEquals(
      "(Class<?>) null -> [null]",
      Array[Class[_]](null).asInstanceOf[Array[Object]],
      castNull.asInstanceOf[Array[Object]])
    // test empty array is returned unchanged
    assertArrayEquals(
      "empty -> empty",
      ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[Object]],
      ClassUtils.primitivesToWrappers(ArrayUtils.EMPTY_CLASS_ARRAY).asInstanceOf[Array[Object]]
    )
    // test an array of various classes
    val primitives = Array[Class[_]](
      JavaBoolean.TYPE,
      JavaByte.TYPE,
      Character.TYPE,
      JavaShort.TYPE,
      Integer.TYPE,
      JavaLong.TYPE,
      JavaDouble.TYPE,
      JavaFloat.TYPE,
      classOf[String],
      ClassUtils.getClass()
    )
    val wrappers = ClassUtils.primitivesToWrappers(primitives)
    for (i <- 0 until primitives.length) { // test each returned wrapper
      val primitive = primitives(i)
      val expectedWrapper = ClassUtils.primitiveToWrapper(primitive)
      assertEquals(primitive + " -> " + expectedWrapper, expectedWrapper, wrappers(i))
    }
    // test an array of no primitive classes
    val noPrimitives = Array[Class[_]](classOf[String], ClassUtils.getClass(), Void.TYPE)
    // This used to return the exact same array, but no longer does.
    assertNotSame("unmodified", noPrimitives, ClassUtils.primitivesToWrappers(noPrimitives))
  }

  @Test def testPrimitiveToWrapper(): Unit = {
    assertEquals("boolean -> Boolean.class", classOf[JavaBoolean], ClassUtils.primitiveToWrapper(JavaBoolean.TYPE))
    assertEquals("byte -> Byte.class", classOf[JavaByte], ClassUtils.primitiveToWrapper(JavaByte.TYPE))
    assertEquals("char -> Character.class", classOf[Character], ClassUtils.primitiveToWrapper(Character.TYPE))
    assertEquals("short -> Short.class", classOf[JavaShort], ClassUtils.primitiveToWrapper(JavaShort.TYPE))
    assertEquals("int -> Integer.class", classOf[Integer], ClassUtils.primitiveToWrapper(Integer.TYPE))
    assertEquals("long -> Long.class", classOf[JavaLong], ClassUtils.primitiveToWrapper(JavaLong.TYPE))
    assertEquals("double -> Double.class", classOf[JavaDouble], ClassUtils.primitiveToWrapper(JavaDouble.TYPE))
    assertEquals("float -> Float.class", classOf[JavaFloat], ClassUtils.primitiveToWrapper(JavaFloat.TYPE))
    // test a few other classes
    assertEquals("String.class -> String.class", classOf[String], ClassUtils.primitiveToWrapper(classOf[String]))
    assertEquals(
      "ClassUtils.class -> ClassUtils.class",
      ClassUtils.getClass(),
      ClassUtils.primitiveToWrapper(ClassUtils.getClass()))
    assertEquals("Void.TYPE -> Void.class", classOf[java.lang.Void], ClassUtils.primitiveToWrapper(Void.TYPE))
    assertNull("null -> null", ClassUtils.primitiveToWrapper(null))
  }

  // Show the Java bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957
  // We may have to delete this if a JDK fixes the bug.
  @Test
  @throws[Exception]
  def testShowJavaBug(): Unit = {
    val set = Collections.unmodifiableSet(new util.HashSet[AnyRef])
    val isEmptyMethod = set.getClass.getMethod("isEmpty")
    assertThrows[IllegalAccessException](isEmptyMethod.invoke(set))
    ()
  }

  @Test def testToClass_object(): Unit = {
    //assertNull(ClassUtils.toClass(null)); // generates warning
    assertNull(ClassUtils.toClass(null.asInstanceOf[Array[Any]])) // equivalent explicit cast
    // Additional varargs tests
    assertArrayEquals(
      "empty -> empty",
      ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[Object]],
      ClassUtils.toClass().asInstanceOf[Array[Object]])
    val castNull = ClassUtils.toClass(null.asInstanceOf[Any]) // == new Object[]{null}
    assertArrayEquals(
      "(Object) null -> [null]",
      Array[AnyRef](null).asInstanceOf[Array[Object]],
      castNull.asInstanceOf[Array[Object]])
    assertSame(ArrayUtils.EMPTY_CLASS_ARRAY, ClassUtils.toClass(ArrayUtils.EMPTY_OBJECT_ARRAY))
    assertArrayEquals(
      Array[Class[_]](classOf[String], classOf[Integer], classOf[JavaDouble]).asInstanceOf[Array[Object]],
      ClassUtils.toClass("Test", Integer.valueOf(1), JavaDouble.valueOf(99d)).asInstanceOf[Array[Object]]
    )
    assertArrayEquals(
      Array[Class[_]](classOf[String], null, classOf[JavaDouble]).asInstanceOf[Array[Object]],
      ClassUtils.toClass("Test", null, JavaDouble.valueOf(99d)).asInstanceOf[Array[Object]]
    )
  }

  @Test
  @throws[ClassNotFoundException]
  def testWithInterleavingWhitespace(): Unit = {
    assertEquals(classOf[Array[Int]], ClassUtils.getClass(" int [ ] "))
    assertEquals(classOf[Array[Long]], ClassUtils.getClass("\rlong\t[\n]\r"))
    assertEquals(classOf[Array[Short]], ClassUtils.getClass("\tshort                \t\t[]"))
    assertEquals(classOf[Array[Byte]], ClassUtils.getClass("byte[\t\t\n\r]   "))
  }

  @Test def testWrappersToPrimitives(): Unit = { // an array with classes to test
    val classes: Array[Class[_]] = Array(
      classOf[Boolean],
      classOf[Byte],
      classOf[Character],
      classOf[Short],
      classOf[Integer],
      classOf[Long],
      classOf[Float],
      classOf[Double],
      classOf[String],
      ClassUtils.getClass(),
      null
    )
    val primitives = ClassUtils.wrappersToPrimitives(classes)
    // now test the result
    assertEquals("Wrong length of result array", classes.length, primitives.length)
    for (i <- 0 until classes.length) {
      val expectedPrimitive = ClassUtils.wrapperToPrimitive(classes(i))
      assertEquals(classes(i) + " -> " + expectedPrimitive, expectedPrimitive, primitives(i))
    }
  }

  @Test def testWrappersToPrimitivesEmpty(): Unit = {
    val empty = new Array[Class[_]](0)
    assertArrayEquals(
      "Wrong result for empty input",
      empty.asInstanceOf[Array[Object]],
      ClassUtils.wrappersToPrimitives(empty).asInstanceOf[Array[Object]])
  }

  @Test def testWrappersToPrimitivesNull()
    : Unit = { //        assertNull("Wrong result for null input", ClassUtils.wrappersToPrimitives(null)); // generates warning
    assertNull(
      "Wrong result for null input",
      ClassUtils.wrappersToPrimitives(null.asInstanceOf[Array[Class[_]]])
    ) // equivalent cast
    assertArrayEquals(
      "empty -> empty",
      ArrayUtils.EMPTY_CLASS_ARRAY.asInstanceOf[Array[Object]],
      ClassUtils.wrappersToPrimitives().asInstanceOf[Array[Object]])
    val castNull = ClassUtils.wrappersToPrimitives(null.asInstanceOf[Class[_]])
    assertArrayEquals(
      "(Class<?>) null -> [null]",
      Array[Class[_]](null).asInstanceOf[Array[Object]],
      castNull.asInstanceOf[Array[Object]])
  }

  @Test def testWrapperToPrimitive(): Unit = { // an array with classes to convert
    // Don't test VOID since it points back to itself
    val primitives: Array[Class[_]] = Array(
      classOf[Boolean],
      classOf[Byte],
      classOf[Char],
      classOf[Short],
      classOf[Int],
      classOf[Long],
      classOf[Float],
      classOf[Double])

    for (primitive <- primitives) {
      val wrapperCls = ClassUtils.primitiveToWrapper(primitive)
      assertFalse("Still primitive", wrapperCls.isPrimitive)
      assertEquals(wrapperCls + " -> " + primitive, primitive, ClassUtils.wrapperToPrimitive(wrapperCls))
    }
  }

  @Test def testWrapperToPrimitiveNoWrapper(): Unit = {
    assertNull("Wrong result for non wrapper class", ClassUtils.wrapperToPrimitive(classOf[String]))
  }

  @Test def testWrapperToPrimitiveNull(): Unit = {
    assertNull("Wrong result for null class", ClassUtils.wrapperToPrimitive(null))
  }
}
