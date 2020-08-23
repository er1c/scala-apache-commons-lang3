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

//package org.apache.commons.lang3.builder
//
//
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//import java.util
//import java.util.{ArrayList, Collection, List}
//import org.apache.commons.lang3.ArrayUtils
//
///**
//  */
//object ReflectionToStringBuilderExcludeTest {
//  private val NOT_SECRET_FIELD = "showField"
//  private val NOT_SECRET_VALUE = "Hello World!"
//  private val SECRET_FIELD = "secretField"
//  private val SECRET_VALUE = "secret value"
//}
//
//class ReflectionToStringBuilderExcludeTest {
//
//  private[builder] class TestFixture {
//    @SuppressWarnings(Array("unused")) final private val secretField = ReflectionToStringBuilderExcludeTest.SECRET_VALUE
//    @SuppressWarnings(Array("unused")) final private val showField =
//      ReflectionToStringBuilderExcludeTest.NOT_SECRET_VALUE
//  }
//
//  @Test def test_toStringExclude(): Unit = {
//    val toString = ReflectionToStringBuilder.toStringExclude(
//      new ReflectionToStringBuilderExcludeTest#TestFixture,
//      ReflectionToStringBuilderExcludeTest.SECRET_FIELD)
//    this.validateSecretFieldAbsent(toString)
//  }
//
//  @Test def test_toStringExcludeArray(): Unit = {
//    val toString = ReflectionToStringBuilder.toStringExclude(
//      new ReflectionToStringBuilderExcludeTest#TestFixture,
//      ReflectionToStringBuilderExcludeTest.SECRET_FIELD)
//    this.validateSecretFieldAbsent(toString)
//  }
//
//  @Test def test_toStringExcludeArrayWithNull(): Unit = {
//    val toString = ReflectionToStringBuilder
//      .toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, Array[String](null))
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeArrayWithNulls(): Unit = {
//    val toString =
//      ReflectionToStringBuilder.toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, null, null)
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeCollection(): Unit = {
//    val excludeList = new util.ArrayList[String]
//    excludeList.add(ReflectionToStringBuilderExcludeTest.SECRET_FIELD)
//    val toString =
//      ReflectionToStringBuilder.toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, excludeList)
//    this.validateSecretFieldAbsent(toString)
//  }
//
//  @Test def test_toStringExcludeCollectionWithNull(): Unit = {
//    val excludeList = new util.ArrayList[String]
//    excludeList.add(null)
//    val toString =
//      ReflectionToStringBuilder.toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, excludeList)
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeCollectionWithNulls(): Unit = {
//    val excludeList = new util.ArrayList[String]
//    excludeList.add(null)
//    excludeList.add(null)
//    val toString =
//      ReflectionToStringBuilder.toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, excludeList)
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeEmptyArray(): Unit = {
//    val toString = ReflectionToStringBuilder
//      .toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, ArrayUtils.EMPTY_STRING_ARRAY)
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeEmptyCollection(): Unit = {
//    val toString = ReflectionToStringBuilder
//      .toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, new util.ArrayList[String])
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeNullArray(): Unit = {
//    val toString = ReflectionToStringBuilder
//      .toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, null.asInstanceOf[Array[String]])
//    this.validateSecretFieldPresent(toString)
//  }
//
//  @Test def test_toStringExcludeNullCollection(): Unit = {
//    val toString = ReflectionToStringBuilder
//      .toStringExclude(new ReflectionToStringBuilderExcludeTest#TestFixture, null.asInstanceOf[util.Collection[String]])
//    this.validateSecretFieldPresent(toString)
//  }
//
//  private def validateNonSecretField(toString: String): Unit = {
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeTest.NOT_SECRET_FIELD))
//    assertTrue(toString.contains(ReflectionToStringBuilderExcludeTest.NOT_SECRET_VALUE))
//  }
//
//  private def validateSecretFieldAbsent(toString: String): Unit = {
//    assertEquals(ArrayUtils.INDEX_NOT_FOUND, toString.indexOf(ReflectionToStringBuilderExcludeTest.SECRET_VALUE))
//    this.validateNonSecretField(toString)
//  }
//
//  private def validateSecretFieldPresent(toString: String): Unit = {
//    assertTrue(toString.indexOf(ReflectionToStringBuilderExcludeTest.SECRET_VALUE) > 0)
//    this.validateNonSecretField(toString)
//  }
//}
