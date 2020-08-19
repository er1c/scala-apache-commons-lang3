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
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertTrue
//import java.io.Serializable
//import java.lang.reflect.Field
//import java.lang.reflect.GenericArrayType
//import java.lang.reflect.Method
//import java.lang.reflect.ParameterizedType
//import java.lang.reflect.Type
//import java.lang.reflect.TypeVariable
//import java.lang.reflect.WildcardType
//import java.net.URI
//import java.util
//import java.util.Collections
//import org.apache.commons.lang3.reflect.testbed.Foo
//import org.apache.commons.lang3.reflect.testbed.GenericParent
//import org.apache.commons.lang3.reflect.testbed.GenericTypeHolder
//import org.apache.commons.lang3.reflect.testbed.StringParameterizedChild
//import org.junit.Test
//
///**
//  * Test TypeUtils
//  */
//@SuppressWarnings(Array(Array("unchecked", "unused", "rawtypes"))) //raw types, where used, are used purposely
//object TypeUtilsTest {
//
//  trait This[K, V] {}
//
//  trait And[K, V] extends TypeUtilsTest.This[Number, Number] {}
//
//  var stringComparable = null
//  var uriComparable = null
//  var intComparable = null
//  var longComparable = null
//  var wildcardComparable = null
//  var uri = null
//  var stringListArray = null
//
//  @SerialVersionUID(1L)
//  object ClassWithSuperClassWithGenericType {
//    def methodWithGenericReturnType[U] = null
//  }
//
//  @SerialVersionUID(1L)
//  class ClassWithSuperClassWithGenericType extends util.ArrayList[AnyRef] {}
//
//  def stub[G <: Comparable[G]] = null
//
//  def stub2[G <: Comparable[Any]] = null
//
//  def stub3[T <: Comparable[T]] = null
//}
//
//@SuppressWarnings(Array(Array("unchecked", "unused", "rawtypes"))) class TypeUtilsTest[B] {
//
//  class That[K, V] extends TypeUtilsTest.This[K, V] {}
//
//  class The[K, V] extends TypeUtilsTest[B]#That[Number, Number] with TypeUtilsTest.And[String, String] {}
//
//  class Other[T] extends TypeUtilsTest.This[String, T] {}
//
//  class Thing[Q] extends TypeUtilsTest[B]#Other[B] {}
//
//  class Tester extends TypeUtilsTest.This[String, B] {}
//
//  var dis = null
//  var dat = null
//  var da = null
//  var uhder = null
//  var ding = null
//  var tester = null
//  var tester2 = null
//  var dat2 = null
//  var dat3 = null
//  var intWildcardComparable = null
//
//  def dummyMethod(list0: util.List[_], list1: util.List[AnyRef], list2: util.List[_], list3: util.List[_ >: Any], list4: util.List[String], list5: util.List[_ <: String], list6: util.List[_ >: String], list7: Array[util.List[_]], list8: Array[util.List[AnyRef]], list9: Array[util.List[_]], list10: Array[util.List[_ >: Any]], list11: Array[util.List[String]], list12: Array[util.List[_ <: String]], list13: Array[util.List[_ >: String]]) = {
//  }
//
//  @SuppressWarnings(Array("boxing")) // deliberately used here @Test  @throws[SecurityException]
//  @throws[NoSuchMethodException]
//  @throws[NoSuchFieldException]
//  def testIsAssignable() = {
//    var list0 = null
//    var list1 = null
//    var list2 = null
//    var list3 = null
//    var list4 = null
//    var list5 = null
//    var list6 = null
//    var list7 = null
//    var list8 = null
//    var list9 = null
//    var list10 = null
//    var list11 = null
//    var list12 = null
//    var list13 = null
//    val clazz = getClass
//    val method = clazz.getMethod("dummyMethod", classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]])
//    val types = method.getGenericParameterTypes
//    //        list0 = list0;
//    delegateBooleanAssertion(types, 0, 0, true)
//    list1 = list0
//    delegateBooleanAssertion(types, 0, 1, true)
//    list0 = list1
//    delegateBooleanAssertion(types, 1, 0, true)
//    list2 = list0
//    delegateBooleanAssertion(types, 0, 2, true)
//    list0 = list2
//    delegateBooleanAssertion(types, 2, 0, true)
//    list3 = list0
//    delegateBooleanAssertion(types, 0, 3, true)
//    list0 = list3
//    delegateBooleanAssertion(types, 3, 0, true)
//    list4 = list0
//    delegateBooleanAssertion(types, 0, 4, true)
//    list0 = list4
//    delegateBooleanAssertion(types, 4, 0, true)
//    list5 = list0
//    delegateBooleanAssertion(types, 0, 5, true)
//    list0 = list5
//    delegateBooleanAssertion(types, 5, 0, true)
//    list6 = list0
//    delegateBooleanAssertion(types, 0, 6, true)
//    list0 = list6
//    delegateBooleanAssertion(types, 6, 0, true)
//    //        list1 = list1;
//    delegateBooleanAssertion(types, 1, 1, true)
//    list2 = list1
//    delegateBooleanAssertion(types, 1, 2, true)
//    list1 = list2.asInstanceOf[util.List[AnyRef]]
//    delegateBooleanAssertion(types, 2, 1, false)
//    list3 = list1
//    delegateBooleanAssertion(types, 1, 3, true)
//    list1 = list3.asInstanceOf[util.List[AnyRef]]
//    delegateBooleanAssertion(types, 3, 1, false)
//    // list4 = list1;
//    delegateBooleanAssertion(types, 1, 4, false)
//    // list1 = list4;
//    delegateBooleanAssertion(types, 4, 1, false)
//    // list5 = list1;
//    delegateBooleanAssertion(types, 1, 5, false)
//    // list1 = list5;
//    delegateBooleanAssertion(types, 5, 1, false)
//    list6 = list1
//    delegateBooleanAssertion(types, 1, 6, true)
//    list1 = list6.asInstanceOf[util.List[AnyRef]]
//    delegateBooleanAssertion(types, 6, 1, false)
//    //        list2 = list2;
//    delegateBooleanAssertion(types, 2, 2, true)
//    list2 = list3
//    delegateBooleanAssertion(types, 2, 3, false)
//    list2 = list4
//    delegateBooleanAssertion(types, 3, 2, true)
//    list3 = list2.asInstanceOf[util.List[_ >: Any]]
//    delegateBooleanAssertion(types, 2, 4, false)
//    list2 = list5
//    delegateBooleanAssertion(types, 4, 2, true)
//    list4 = list2.asInstanceOf[util.List[String]]
//    delegateBooleanAssertion(types, 2, 5, false)
//    list2 = list6
//    delegateBooleanAssertion(types, 5, 2, true)
//    list5 = list2.asInstanceOf[util.List[_ <: String]]
//    delegateBooleanAssertion(types, 2, 6, false)
//    //        list3 = list3;
//    delegateBooleanAssertion(types, 6, 2, true)
//    list6 = list2.asInstanceOf[util.List[_ >: String]]
//    delegateBooleanAssertion(types, 3, 3, true)
//    // list4 = list3;
//    delegateBooleanAssertion(types, 3, 4, false)
//    // list3 = list4;
//    delegateBooleanAssertion(types, 4, 3, false)
//    // list5 = list3;
//    delegateBooleanAssertion(types, 3, 5, false)
//    // list3 = list5;
//    delegateBooleanAssertion(types, 5, 3, false)
//    list6 = list3
//    delegateBooleanAssertion(types, 3, 6, true)
//    list3 = list6.asInstanceOf[util.List[_ >: Any]]
//    delegateBooleanAssertion(types, 6, 3, false)
//    //        list4 = list4;
//    delegateBooleanAssertion(types, 4, 4, true)
//    list5 = list4
//    delegateBooleanAssertion(types, 4, 5, true)
//    list4 = list5.asInstanceOf[util.List[String]]
//    delegateBooleanAssertion(types, 5, 4, false)
//    list6 = list4
//    delegateBooleanAssertion(types, 4, 6, true)
//    list4 = list6.asInstanceOf[util.List[String]]
//    delegateBooleanAssertion(types, 6, 4, false)
//    //        list5 = list5;
//    delegateBooleanAssertion(types, 5, 5, true)
//    list6 = list5.asInstanceOf[util.List[_ >: String]]
//    delegateBooleanAssertion(types, 5, 6, false)
//    list5 = list6.asInstanceOf[util.List[_ <: String]]
//    delegateBooleanAssertion(types, 6, 5, false)
//    //        list6 = list6;
//    delegateBooleanAssertion(types, 6, 6, true)
//    //        list7 = list7;
//    delegateBooleanAssertion(types, 7, 7, true)
//    list8 = list7
//    delegateBooleanAssertion(types, 7, 8, true)
//    list7 = list8
//    delegateBooleanAssertion(types, 8, 7, true)
//    list9 = list7
//    delegateBooleanAssertion(types, 7, 9, true)
//    list7 = list9
//    delegateBooleanAssertion(types, 9, 7, true)
//    list10 = list7
//    delegateBooleanAssertion(types, 7, 10, true)
//    list7 = list10
//    delegateBooleanAssertion(types, 10, 7, true)
//    list11 = list7
//    delegateBooleanAssertion(types, 7, 11, true)
//    list7 = list11
//    delegateBooleanAssertion(types, 11, 7, true)
//    list12 = list7
//    delegateBooleanAssertion(types, 7, 12, true)
//    list7 = list12
//    delegateBooleanAssertion(types, 12, 7, true)
//    list13 = list7
//    delegateBooleanAssertion(types, 7, 13, true)
//    list7 = list13
//    delegateBooleanAssertion(types, 13, 7, true)
//    //        list8 = list8;
//    delegateBooleanAssertion(types, 8, 8, true)
//    list9 = list8
//    delegateBooleanAssertion(types, 8, 9, true)
//    list8 = list9.asInstanceOf[Array[util.List[AnyRef]]]
//    delegateBooleanAssertion(types, 9, 8, false)
//    list10 = list8
//    delegateBooleanAssertion(types, 8, 10, true)
//    list8 = list10.asInstanceOf[Array[util.List[AnyRef]]] // NOTE cast is required by Sun Java, but not by Eclipse
//    delegateBooleanAssertion(types, 10, 8, false)
//    // list11 = list8;
//    delegateBooleanAssertion(types, 8, 11, false)
//    // list8 = list11;
//    delegateBooleanAssertion(types, 11, 8, false)
//    // list12 = list8;
//    delegateBooleanAssertion(types, 8, 12, false)
//    // list8 = list12;
//    delegateBooleanAssertion(types, 12, 8, false)
//    list13 = list8
//    delegateBooleanAssertion(types, 8, 13, true)
//    list8 = list13.asInstanceOf[Array[util.List[AnyRef]]]
//    delegateBooleanAssertion(types, 13, 8, false)
//    //        list9 = list9;
//    delegateBooleanAssertion(types, 9, 9, true)
//    list10 = list9.asInstanceOf[Array[util.List[_ >: Any]]]
//    delegateBooleanAssertion(types, 9, 10, false)
//    list9 = list10
//    delegateBooleanAssertion(types, 10, 9, true)
//    list11 = list9.asInstanceOf[Array[util.List[String]]]
//    delegateBooleanAssertion(types, 9, 11, false)
//    list9 = list11
//    delegateBooleanAssertion(types, 11, 9, true)
//    list12 = list9.asInstanceOf[Array[util.List[_ <: String]]]
//    delegateBooleanAssertion(types, 9, 12, false)
//    list9 = list12
//    delegateBooleanAssertion(types, 12, 9, true)
//    list13 = list9.asInstanceOf[Array[util.List[_ >: String]]]
//    delegateBooleanAssertion(types, 9, 13, false)
//    list9 = list13
//    delegateBooleanAssertion(types, 13, 9, true)
//    //        list10 = list10;
//    delegateBooleanAssertion(types, 10, 10, true)
//    // list11 = list10;
//    delegateBooleanAssertion(types, 10, 11, false)
//    // list10 = list11;
//    delegateBooleanAssertion(types, 11, 10, false)
//    // list12 = list10;
//    delegateBooleanAssertion(types, 10, 12, false)
//    // list10 = list12;
//    delegateBooleanAssertion(types, 12, 10, false)
//    list13 = list10
//    delegateBooleanAssertion(types, 10, 13, true)
//    list10 = list13.asInstanceOf[Array[util.List[_ >: Any]]]
//    delegateBooleanAssertion(types, 13, 10, false)
//    //        list11 = list11;
//    delegateBooleanAssertion(types, 11, 11, true)
//    list12 = list11
//    delegateBooleanAssertion(types, 11, 12, true)
//    list11 = list12.asInstanceOf[Array[util.List[String]]]
//    delegateBooleanAssertion(types, 12, 11, false)
//    list13 = list11
//    delegateBooleanAssertion(types, 11, 13, true)
//    list11 = list13.asInstanceOf[Array[util.List[String]]]
//    delegateBooleanAssertion(types, 13, 11, false)
//    //        list12 = list12;
//    delegateBooleanAssertion(types, 12, 12, true)
//    list13 = list12.asInstanceOf[Array[util.List[_ >: String]]]
//    delegateBooleanAssertion(types, 12, 13, false)
//    list12 = list13.asInstanceOf[Array[util.List[_ <: String]]]
//    delegateBooleanAssertion(types, 13, 12, false)
//    //        list13 = list13;
//    delegateBooleanAssertion(types, 13, 13, true)
//    val disType = getClass.getField("dis").getGenericType
//    // Reporter.log( ( ( ParameterizedType ) disType
//    // ).getOwnerType().getClass().toString() );
//    val datType = getClass.getField("dat").getGenericType
//    val daType = getClass.getField("da").getGenericType
//    val uhderType = getClass.getField("uhder").getGenericType
//    val dingType = getClass.getField("ding").getGenericType
//    val testerType = getClass.getField("tester").getGenericType
//    val tester2Type = getClass.getField("tester2").getGenericType
//    val dat2Type = getClass.getField("dat2").getGenericType
//    val dat3Type = getClass.getField("dat3").getGenericType
//    dis = dat
//    assertTrue(TypeUtils.isAssignable(datType, disType))
//    // dis = da;
//    assertFalse(TypeUtils.isAssignable(daType, disType))
//    dis = uhder
//    assertTrue(TypeUtils.isAssignable(uhderType, disType))
//    dis = ding
//    assertFalse(TypeUtils.isAssignable(dingType, disType), String.format("type %s not assignable to %s!", dingType, disType))
//    dis = tester
//    assertTrue(TypeUtils.isAssignable(testerType, disType))
//    // dis = tester2;
//    assertFalse(TypeUtils.isAssignable(tester2Type, disType))
//    // dat = dat2;
//    assertFalse(TypeUtils.isAssignable(dat2Type, datType))
//    // dat2 = dat;
//    assertFalse(TypeUtils.isAssignable(datType, dat2Type))
//    // dat = dat3;
//    assertFalse(TypeUtils.isAssignable(dat3Type, datType))
//    val ch = 0
//    val bo = false
//    val by = 0
//    val sh = 0
//    var in = 0
//    var lo = 0
//    val fl = 0
//    var du = 0
//    du = ch
//    assertTrue(TypeUtils.isAssignable(classOf[Char], classOf[Double]))
//    du = by
//    assertTrue(TypeUtils.isAssignable(classOf[Byte], classOf[Double]))
//    du = sh
//    assertTrue(TypeUtils.isAssignable(classOf[Short], classOf[Double]))
//    du = in
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Double]))
//    du = lo
//    assertTrue(TypeUtils.isAssignable(classOf[Long], classOf[Double]))
//    du = fl
//    assertTrue(TypeUtils.isAssignable(classOf[Float], classOf[Double]))
//    lo = in
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Long]))
//    lo = Integer.valueOf(0)
//    assertTrue(TypeUtils.isAssignable(classOf[Integer], classOf[Long]))
//    // Long lngW = 1;
//    assertFalse(TypeUtils.isAssignable(classOf[Int], classOf[Long]))
//    // lngW = Integer.valueOf( 0 );
//    assertFalse(TypeUtils.isAssignable(classOf[Integer], classOf[Long]))
//    in = Integer.valueOf(0)
//    assertTrue(TypeUtils.isAssignable(classOf[Integer], classOf[Int]))
//    val inte = in
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Integer]))
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Number]))
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Any]))
//    val intComparableType = getClass.getField("intComparable").getGenericType
//    TypeUtilsTest.intComparable = 1
//    assertTrue(TypeUtils.isAssignable(classOf[Int], intComparableType))
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Comparable[_]]))
//    val ser = 1
//    assertTrue(TypeUtils.isAssignable(classOf[Int], classOf[Serializable]))
//    val longComparableType = getClass.getField("longComparable").getGenericType
//    // longComparable = 1;
//    assertFalse(TypeUtils.isAssignable(classOf[Int], longComparableType))
//    // longComparable = Integer.valueOf( 0 );
//    assertFalse(TypeUtils.isAssignable(classOf[Integer], longComparableType))
//    // int[] ia;
//    // long[] la = ia;
//    assertFalse(TypeUtils.isAssignable(classOf[Array[Int]], classOf[Array[Long]]))
//    val ia = null
//    val caType = getClass.getField("intWildcardComparable").getGenericType
//    intWildcardComparable = ia
//    assertTrue(TypeUtils.isAssignable(classOf[Array[Integer]], caType))
//    // int[] ina = ia;
//    assertFalse(TypeUtils.isAssignable(classOf[Array[Integer]], classOf[Array[Int]]))
//    val ina = null
//    var oa = null
//    // oa = ina;
//    assertFalse(TypeUtils.isAssignable(classOf[Array[Int]], classOf[Array[AnyRef]]))
//    oa = new Array[Integer](0)
//    assertTrue(TypeUtils.isAssignable(classOf[Array[Integer]], classOf[Array[AnyRef]]))
//    val bClassType = classOf[AClass].getField("bClass").getGenericType
//    val cClassType = classOf[AClass].getField("cClass").getGenericType
//    val dClassType = classOf[AClass].getField("dClass").getGenericType
//    val eClassType = classOf[AClass].getField("eClass").getGenericType
//    val fClassType = classOf[AClass].getField("fClass").getGenericType
//    val aClass = new AClass(new AAClass[String])
//    aClass.bClass = aClass.cClass
//    assertTrue(TypeUtils.isAssignable(cClassType, bClassType))
//    aClass.bClass = aClass.dClass
//    assertTrue(TypeUtils.isAssignable(dClassType, bClassType))
//    aClass.bClass = aClass.eClass
//    assertTrue(TypeUtils.isAssignable(eClassType, bClassType))
//    aClass.bClass = aClass.fClass
//    assertTrue(TypeUtils.isAssignable(fClassType, bClassType))
//    aClass.cClass = aClass.dClass
//    assertTrue(TypeUtils.isAssignable(dClassType, cClassType))
//    aClass.cClass = aClass.eClass
//    assertTrue(TypeUtils.isAssignable(eClassType, cClassType))
//    aClass.cClass = aClass.fClass
//    assertTrue(TypeUtils.isAssignable(fClassType, cClassType))
//    aClass.dClass = aClass.eClass
//    assertTrue(TypeUtils.isAssignable(eClassType, dClassType))
//    aClass.dClass = aClass.fClass
//    assertTrue(TypeUtils.isAssignable(fClassType, dClassType))
//    aClass.eClass = aClass.fClass
//    assertTrue(TypeUtils.isAssignable(fClassType, eClassType))
//  }
//
//  def delegateBooleanAssertion(types: Array[Type], i2: Int, i1: Int, expected: Boolean) = {
//    val type1 = types(i1)
//    val type2 = types(i2)
//    val isAssignable = TypeUtils.isAssignable(type2, type1)
//    if (expected) assertTrue(isAssignable, "[" + i1 + ", " + i2 + "]: From " + String.valueOf(type2) + " to " + String.valueOf(type1))
//    else assertFalse(isAssignable, "[" + i1 + ", " + i2 + "]: From " + String.valueOf(type2) + " to " + String.valueOf(type1))
//  }
//
//  @SuppressWarnings(Array("boxing")) // boxing is deliberate here @Test  @throws[SecurityException]
//  @throws[NoSuchFieldException]
//  def testIsInstance() = {
//    val intComparableType = getClass.getField("intComparable").getGenericType
//    val uriComparableType = getClass.getField("uriComparable").getGenericType
//    TypeUtilsTest.intComparable = 1
//    assertTrue(TypeUtils.isInstance(1, intComparableType))
//    // uriComparable = 1;
//    assertFalse(TypeUtils.isInstance(1, uriComparableType))
//  }
//
//  @Test def testGetTypeArguments() = {
//    var typeVarAssigns = null
//    var treeSetTypeVar = null
//    var typeArg = null
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[Integer], classOf[Comparable[_]])
//    treeSetTypeVar = classOf[Comparable[_]].getTypeParameters(0)
//    assertTrue(typeVarAssigns.containsKey(treeSetTypeVar), "Type var assigns for Comparable from Integer: " + typeVarAssigns)
//    typeArg = typeVarAssigns.get(treeSetTypeVar)
//    assertEquals(classOf[Integer], typeVarAssigns.get(treeSetTypeVar), "Type argument of Comparable from Integer: " + typeArg)
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[Int], classOf[Comparable[_]])
//    treeSetTypeVar = classOf[Comparable[_]].getTypeParameters(0)
//    assertTrue(typeVarAssigns.containsKey(treeSetTypeVar), "Type var assigns for Comparable from int: " + typeVarAssigns)
//    typeArg = typeVarAssigns.get(treeSetTypeVar)
//    assertEquals(classOf[Integer], typeVarAssigns.get(treeSetTypeVar), "Type argument of Comparable from int: " + typeArg)
//    val col = Collections.emptyList
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[util.List[_]], classOf[util.Collection[_]])
//    treeSetTypeVar = classOf[Comparable[_]].getTypeParameters(0)
//    assertFalse(typeVarAssigns.containsKey(treeSetTypeVar), "Type var assigns for Collection from List: " + typeVarAssigns)
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[AAAClass#BBBClass], classOf[AAClass[T]#BBClass[_]])
//    assertEquals(2, typeVarAssigns.size)
//    assertEquals(classOf[String], typeVarAssigns.get(classOf[AAClass[_]].getTypeParameters(0)))
//    assertEquals(classOf[String], typeVarAssigns.get(classOf[AAClass[T]#BBClass[_]].getTypeParameters(0)))
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[TypeUtilsTest[B]#Other[_]], classOf[TypeUtilsTest.This[_, _]])
//    assertEquals(2, typeVarAssigns.size)
//    assertEquals(classOf[String], typeVarAssigns.get(classOf[TypeUtilsTest.This[_, _]].getTypeParameters(0)))
//    assertEquals(classOf[TypeUtilsTest[B]#Other[_]].getTypeParameters(0), typeVarAssigns.get(classOf[TypeUtilsTest.This[_, _]].getTypeParameters(1)))
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[TypeUtilsTest.And[_, _]], classOf[TypeUtilsTest.This[_, _]])
//    assertEquals(2, typeVarAssigns.size)
//    assertEquals(classOf[Number], typeVarAssigns.get(classOf[TypeUtilsTest.This[_, _]].getTypeParameters(0)))
//    assertEquals(classOf[Number], typeVarAssigns.get(classOf[TypeUtilsTest.This[_, _]].getTypeParameters(1)))
//    typeVarAssigns = TypeUtils.getTypeArguments(classOf[TypeUtilsTest[B]#Thing[_]], classOf[TypeUtilsTest[B]#Other[_]])
//    assertEquals(2, typeVarAssigns.size)
//    assertEquals(getClass.getTypeParameters(0), typeVarAssigns.get(getClass.getTypeParameters(0)))
//    assertEquals(getClass.getTypeParameters(0), typeVarAssigns.get(classOf[TypeUtilsTest[B]#Other[_]].getTypeParameters(0)))
//  }
//
//  @Test
//  @throws[SecurityException]
//  @throws[NoSuchMethodException]
//  def testTypesSatisfyVariables() = {
//    val typeVarAssigns = new (util.HashMap[TypeVariable[_$1], Type])
//    forSome
//    {
//      type _$1
//    }
//    val max = TypeUtilsTest.stub[Integer]
//    typeVarAssigns.put(getClass.getMethod("stub").getTypeParameters(0), classOf[Integer])
//    assertTrue(TypeUtils.typesSatisfyVariables(typeVarAssigns))
//    typeVarAssigns.clear()
//    typeVarAssigns.put(getClass.getMethod("stub2").getTypeParameters(0), classOf[Integer])
//    assertTrue(TypeUtils.typesSatisfyVariables(typeVarAssigns))
//    typeVarAssigns.clear()
//    typeVarAssigns.put(getClass.getMethod("stub3").getTypeParameters(0), classOf[Integer])
//    assertTrue(TypeUtils.typesSatisfyVariables(typeVarAssigns))
//  }
//
//  @Test
//  @throws[SecurityException]
//  @throws[NoSuchFieldException]
//  def testDetermineTypeVariableAssignments() = {
//    val iterableType = getClass.getField("iterable").getGenericType.asInstanceOf[ParameterizedType]
//    val typeVarAssigns = TypeUtils.determineTypeArguments(classOf[util.TreeSet[_]], iterableType)
//    val treeSetTypeVar = classOf[util.TreeSet[_]].getTypeParameters(0)
//    assertTrue(typeVarAssigns.containsKey(treeSetTypeVar))
//    assertEquals(iterableType.getActualTypeArguments(0), typeVarAssigns.get(treeSetTypeVar))
//  }
//
//  @Test
//  @throws[SecurityException]
//  @throws[NoSuchFieldException]
//  def testGetRawType() = {
//    val stringParentFieldType = classOf[GenericTypeHolder].getDeclaredField("stringParent").getGenericType
//    val integerParentFieldType = classOf[GenericTypeHolder].getDeclaredField("integerParent").getGenericType
//    val foosFieldType = classOf[GenericTypeHolder].getDeclaredField("foos").getGenericType
//    val genericParentT = classOf[GenericParent[_]].getTypeParameters(0)
//    assertEquals(classOf[GenericParent[_]], TypeUtils.getRawType(stringParentFieldType, null))
//    assertEquals(classOf[GenericParent[_]], TypeUtils.getRawType(integerParentFieldType, null))
//    assertEquals(classOf[util.List[_]], TypeUtils.getRawType(foosFieldType, null))
//    assertEquals(classOf[String], TypeUtils.getRawType(genericParentT, classOf[StringParameterizedChild]))
//    assertEquals(classOf[String], TypeUtils.getRawType(genericParentT, stringParentFieldType))
//    assertEquals(classOf[Foo], TypeUtils.getRawType(classOf[Iterable[_]].getTypeParameters(0), foosFieldType))
//    assertEquals(classOf[Foo], TypeUtils.getRawType(classOf[util.List[_]].getTypeParameters(0), foosFieldType))
//    assertNull(TypeUtils.getRawType(genericParentT, classOf[GenericParent[_]]))
//    assertEquals(classOf[Array[GenericParent[_]]], TypeUtils.getRawType(classOf[GenericTypeHolder].getDeclaredField("barParents").getGenericType, null))
//  }
//
//  @Test def testIsArrayTypeClasses() = {
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Boolean]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Byte]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Short]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Int]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Char]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Long]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Float]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[Double]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[AnyRef]]))
//    assertTrue(TypeUtils.isArrayType(classOf[Array[String]]))
//    assertFalse(TypeUtils.isArrayType(classOf[Boolean]))
//    assertFalse(TypeUtils.isArrayType(classOf[Byte]))
//    assertFalse(TypeUtils.isArrayType(classOf[Short]))
//    assertFalse(TypeUtils.isArrayType(classOf[Int]))
//    assertFalse(TypeUtils.isArrayType(classOf[Char]))
//    assertFalse(TypeUtils.isArrayType(classOf[Long]))
//    assertFalse(TypeUtils.isArrayType(classOf[Float]))
//    assertFalse(TypeUtils.isArrayType(classOf[Double]))
//    assertFalse(TypeUtils.isArrayType(classOf[Any]))
//    assertFalse(TypeUtils.isArrayType(classOf[String]))
//  }
//
//  @Test
//  @throws[Exception]
//  def testIsArrayGenericTypes() = {
//    val method = getClass.getMethod("dummyMethod", classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]])
//    val types = method.getGenericParameterTypes
//    assertFalse(TypeUtils.isArrayType(types(0)))
//    assertFalse(TypeUtils.isArrayType(types(1)))
//    assertFalse(TypeUtils.isArrayType(types(2)))
//    assertFalse(TypeUtils.isArrayType(types(3)))
//    assertFalse(TypeUtils.isArrayType(types(4)))
//    assertFalse(TypeUtils.isArrayType(types(5)))
//    assertFalse(TypeUtils.isArrayType(types(6)))
//    assertTrue(TypeUtils.isArrayType(types(7)))
//    assertTrue(TypeUtils.isArrayType(types(8)))
//    assertTrue(TypeUtils.isArrayType(types(9)))
//    assertTrue(TypeUtils.isArrayType(types(10)))
//    assertTrue(TypeUtils.isArrayType(types(11)))
//    assertTrue(TypeUtils.isArrayType(types(12)))
//    assertTrue(TypeUtils.isArrayType(types(13)))
//  }
//
//  @Test def testGetPrimitiveArrayComponentType() = {
//    assertEquals(classOf[Boolean], TypeUtils.getArrayComponentType(classOf[Array[Boolean]]))
//    assertEquals(classOf[Byte], TypeUtils.getArrayComponentType(classOf[Array[Byte]]))
//    assertEquals(classOf[Short], TypeUtils.getArrayComponentType(classOf[Array[Short]]))
//    assertEquals(classOf[Int], TypeUtils.getArrayComponentType(classOf[Array[Int]]))
//    assertEquals(classOf[Char], TypeUtils.getArrayComponentType(classOf[Array[Char]]))
//    assertEquals(classOf[Long], TypeUtils.getArrayComponentType(classOf[Array[Long]]))
//    assertEquals(classOf[Float], TypeUtils.getArrayComponentType(classOf[Array[Float]]))
//    assertEquals(classOf[Double], TypeUtils.getArrayComponentType(classOf[Array[Double]]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Boolean]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Byte]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Short]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Int]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Char]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Long]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Float]))
//    assertNull(TypeUtils.getArrayComponentType(classOf[Double]))
//  }
//
//  @Test
//  @throws[Exception]
//  def testGetArrayComponentType() = {
//    val method = getClass.getMethod("dummyMethod", classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[util.List[_]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]], classOf[Array[util.List[_]]])
//    val types = method.getGenericParameterTypes
//    assertNull(TypeUtils.getArrayComponentType(types(0)))
//    assertNull(TypeUtils.getArrayComponentType(types(1)))
//    assertNull(TypeUtils.getArrayComponentType(types(2)))
//    assertNull(TypeUtils.getArrayComponentType(types(3)))
//    assertNull(TypeUtils.getArrayComponentType(types(4)))
//    assertNull(TypeUtils.getArrayComponentType(types(5)))
//    assertNull(TypeUtils.getArrayComponentType(types(6)))
//    assertEquals(types(0), TypeUtils.getArrayComponentType(types(7)))
//    assertEquals(types(1), TypeUtils.getArrayComponentType(types(8)))
//    assertEquals(types(2), TypeUtils.getArrayComponentType(types(9)))
//    assertEquals(types(3), TypeUtils.getArrayComponentType(types(10)))
//    assertEquals(types(4), TypeUtils.getArrayComponentType(types(11)))
//    assertEquals(types(5), TypeUtils.getArrayComponentType(types(12)))
//    assertEquals(types(6), TypeUtils.getArrayComponentType(types(13)))
//  }
//
//  @Test def testLang820() = {
//    val typeArray = Array(classOf[String], classOf[String])
//    val expectedArray = Array(classOf[String])
//    assertArrayEquals(expectedArray, TypeUtils.normalizeUpperBounds(typeArray))
//  }
//
//  @Test
//  @throws[Exception]
//  def testParameterize() = {
//    val stringComparableType = TypeUtils.parameterize(classOf[Comparable[_]], classOf[String])
//    assertTrue(TypeUtils.equals(getClass.getField("stringComparable").getGenericType, stringComparableType))
//    assertEquals("java.lang.Comparable<java.lang.String>", stringComparableType.toString)
//  }
//
//  @Test def testParameterizeNarrowerTypeArray() = {
//    val variables = classOf[util.ArrayList[_]].getTypeParameters
//    val parameterizedType = TypeUtils.parameterize(classOf[util.ArrayList[_]], variables)
//    val mapping = Collections.singletonMap[TypeVariable[_], Type](variables(0), classOf[String])
//    val unrolled = TypeUtils.unrollVariables(mapping, parameterizedType)
//    assertEquals(TypeUtils.parameterize(classOf[util.ArrayList[_]], classOf[String]), unrolled)
//  }
//
//  @Test
//  @throws[Exception]
//  def testParameterizeWithOwner() = {
//    val owner = TypeUtils.parameterize(classOf[TypeUtilsTest[_]], classOf[String])
//    val dat2Type = TypeUtils.parameterizeWithOwner(owner, classOf[TypeUtilsTest[B]#That[_, _]], classOf[String], classOf[String])
//    assertTrue(TypeUtils.equals(getClass.getField("dat2").getGenericType, dat2Type))
//  }
//
//  @Test
//  @throws[Exception]
//  def testWildcardType() = {
//    val simpleWildcard = TypeUtils.wildcardType.withUpperBounds(classOf[String]).build
//    val cClass = classOf[AClass].getField("cClass")
//    assertTrue(TypeUtils.equals(cClass.getGenericType.asInstanceOf[ParameterizedType].getActualTypeArguments(0), simpleWildcard))
//    assertEquals(String.format("? extends %s", classOf[String].getName), TypeUtils.toString(simpleWildcard))
//    assertEquals(String.format("? extends %s", classOf[String].getName), simpleWildcard.toString)
//  }
//
//  @Test def testUnboundedWildcardType() = {
//    val unbounded = TypeUtils.wildcardType.withLowerBounds(null.asInstanceOf[Type]).withUpperBounds.build
//    assertTrue(TypeUtils.equals(TypeUtils.WILDCARD_ALL, unbounded))
//    assertArrayEquals(Array[Type](classOf[Any]), TypeUtils.getImplicitUpperBounds(unbounded))
//    assertArrayEquals(Array[Type](null), TypeUtils.getImplicitLowerBounds(unbounded))
//    assertEquals("?", TypeUtils.toString(unbounded))
//    assertEquals("?", unbounded.toString)
//  }
//
//  @Test def testLowerBoundedWildcardType() = {
//    val lowerBounded = TypeUtils.wildcardType.withLowerBounds(classOf[Date]).build
//    assertEquals(String.format("? super %s", classOf[Date].getName), TypeUtils.toString(lowerBounded))
//    assertEquals(String.format("? super %s", classOf[Date].getName), lowerBounded.toString)
//    val iterableT0 = classOf[Iterable[_]].getTypeParameters(0)
//    val lowerTypeVariable = TypeUtils.wildcardType.withLowerBounds(iterableT0).build
//    assertEquals(String.format("? super %s", iterableT0.getName), TypeUtils.toString(lowerTypeVariable))
//    assertEquals(String.format("? super %s", iterableT0.getName), lowerTypeVariable.toString)
//  }
//
//  @Test
//  @throws[Exception]
//  def testLang1114() = {
//    val nonWildcardType = getClass.getDeclaredField("wildcardComparable").getGenericType
//    val wildcardType = nonWildcardType.asInstanceOf[ParameterizedType].getActualTypeArguments(0)
//    assertFalse(TypeUtils.equals(wildcardType, nonWildcardType))
//    assertFalse(TypeUtils.equals(nonWildcardType, wildcardType))
//  }
//
//  @Test
//  @throws[Exception]
//  def testGenericArrayType() = {
//    val expected = getClass.getField("intWildcardComparable").getGenericType
//    val actual = TypeUtils.genericArrayType(TypeUtils.parameterize(classOf[Comparable[_]], TypeUtils.wildcardType.withUpperBounds(classOf[Integer]).build))
//    assertTrue(TypeUtils.equals(expected, actual))
//    assertEquals("java.lang.Comparable<? extends java.lang.Integer>[]", actual.toString)
//  }
//
//  @Test def testToStringLang1311() = {
//    assertEquals("int[]", TypeUtils.toString(classOf[Array[Int]]))
//    assertEquals("java.lang.Integer[]", TypeUtils.toString(classOf[Array[Integer]]))
//    val stringListField = FieldUtils.getDeclaredField(getClass, "stringListArray")
//    assertEquals("java.util.List<java.lang.String>[]", TypeUtils.toString(stringListField.getGenericType))
//  }
//
//  @Test def testToLongString() = assertEquals(getClass.getName + ":B", TypeUtils.toLongString(getClass.getTypeParameters(0)))
//
//  @Test def testWrap() = {
//    val t = getClass.getTypeParameters(0)
//    assertTrue(TypeUtils.equals(t, TypeUtils.wrap(t).getType))
//    assertEquals(classOf[String], TypeUtils.wrap(classOf[String]).getType)
//  }
//
//  @Test
//  @throws[Exception]
//  def testLANG1190() = {
//    val fromType = classOf[TypeUtilsTest.ClassWithSuperClassWithGenericType].getDeclaredMethod("methodWithGenericReturnType").getGenericReturnType
//    val failingToType = TypeUtils.wildcardType.withLowerBounds(classOf[TypeUtilsTest.ClassWithSuperClassWithGenericType]).build
//    assertTrue(TypeUtils.isAssignable(fromType, failingToType))
//  }
//
//  @Test
//  @throws[Exception]
//  def testLANG1348() = {
//    val method = classOf[(Enum[E]) forSome {type E <: Enum[E]}].getMethod("valueOf", classOf[Class[_]], classOf[String])
//    assertEquals("T extends java.lang.Enum<T>", TypeUtils.toString(method.getGenericReturnType))
//  }
//
//  var iterable = null
//}
//
//class AAClass[T] {
//
//  class BBClass[S] {}
//
//}
//
//class AAAClass extends AAClass[String] {
//
//  class BBBClass extends AAClass[String]#BBClass[String] {}
//
//}
//
//@SuppressWarnings(Array("rawtypes")) object AClass {
//
//  trait AInterface[T] {}
//
//}
//
//@SuppressWarnings(Array("rawtypes")) class AClass private[reflect](val enclosingInstance: AAClass[String]) extends AAClass[String]#BBClass[Number] {
//  enclosingInstance.super
//
//  class BClass[T] {}
//
//  class CClass[T] extends AClass#BClass[_] {}
//
//  class DClass[T] extends AClass#CClass[T] {}
//
//  class EClass[T] extends AClass#DClass[_] {}
//
//  class FClass extends AClass#EClass[String] {}
//
//  class GClass[T <: BClass[T] with AInterface[AClass.AInterface[_ >: T]]] {}
//
//  var bClass = null
//  var cClass = null
//  var dClass = null
//  var eClass = null
//  var fClass = null
//  var gClass = null
//}
