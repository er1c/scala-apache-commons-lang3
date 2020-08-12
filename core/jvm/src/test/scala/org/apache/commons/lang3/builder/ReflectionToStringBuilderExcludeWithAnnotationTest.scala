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
//import org.scalatestplus.junit.JUnitSuite
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.Matchers.containsString
//import org.hamcrest.Matchers.not
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  * Test class for ToStringExclude annotation
//  */
//object ReflectionToStringBuilderExcludeWithAnnotationTest {
//  private val INCLUDED_FIELD_NAME = "includedField"
//  private val INCLUDED_FIELD_VALUE = "Hello World!"
//  private val EXCLUDED_FIELD_NAME = "excludedField"
//  private val EXCLUDED_FIELD_VALUE = "excluded field value"
//}
//
//class ReflectionToStringBuilderExcludeWithAnnotationTest extends JUnitSuite {
//
//  private[builder] class TestFixture {
//    @ToStringExclude final private val excludedField =
//      ReflectionToStringBuilderExcludeWithAnnotationTest.EXCLUDED_FIELD_VALUE
//    @SuppressWarnings(Array("unused")) final private val includedField =
//      ReflectionToStringBuilderExcludeWithAnnotationTest.INCLUDED_FIELD_VALUE
//  }
//
//  @Test def test_toStringExclude(): Unit = {
//    val toString =
//      ReflectionToStringBuilder.toString(new ReflectionToStringBuilderExcludeWithAnnotationTest#TestFixture)
//    assertThat(toString, not(containsString(ReflectionToStringBuilderExcludeWithAnnotationTest.EXCLUDED_FIELD_NAME)))
//    assertThat(toString, not(containsString(ReflectionToStringBuilderExcludeWithAnnotationTest.EXCLUDED_FIELD_VALUE)))
//    assertThat(toString, containsString(ReflectionToStringBuilderExcludeWithAnnotationTest.INCLUDED_FIELD_NAME))
//    assertThat(toString, containsString(ReflectionToStringBuilderExcludeWithAnnotationTest.INCLUDED_FIELD_VALUE))
//  }
//}
