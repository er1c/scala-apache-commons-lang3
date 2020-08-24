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

//package org.apache.commons.lang3.compare
//
//import org.apache.commons.lang3.compare.ComparableUtils.is
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertTrue
//import java.math.BigDecimal
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.DisplayNameGeneration
//import org.junit.jupiter.api.DisplayNameGenerator
//import org.junit.jupiter.api.Nested
//import org.junit.Test
//
//@DisplayNameGeneration(classOf[Nothing]) class ComparableUtilsTest {
//
//  @Nested private[compare] class A_is_1 {
//
//    @DisplayName("B is 0 (B < A)")
//    @Nested private[compare] class B_is_0 {
//
//      @DisplayName("C is 0 ([B=C] < A)")
//      @Nested private[compare] class C_is_0 {
//        private[compare] val c = BigDecimal.ZERO
//
//        @Test private[compare] def between_returns_false() = assertFalse(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      @DisplayName("C is 1 (B < A = C)")
//      @Nested private[compare] class C_is_1 {
//        private[compare] val c = BigDecimal.ONE
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      @DisplayName("C is 10 (B < A < C)")
//      @Nested private[compare] class C_is_10 {
//        private[compare] val c = BigDecimal.TEN
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_true() = assertTrue(is(a).betweenExclusive(b, c))
//      }
//
//      private[compare] val b = BigDecimal.ZERO
//
//      @Test private[compare] def equalTo_returns_false() = assertFalse(is(a).equalTo(b))
//
//      @Test private[compare] def greaterThan_returns_true() = assertTrue(is(a).greaterThan(b))
//
//      @Test private[compare] def greaterThanOrEqualTo_returns_true() = assertTrue(is(a).greaterThanOrEqualTo(b))
//
//      @Test private[compare] def lessThan_returns_false() = assertFalse(is(a).lessThan(b))
//
//      @Test private[compare] def lessThanOrEqualTo_returns_false() = assertFalse(is(a).lessThanOrEqualTo(b))
//    }
//
//    @DisplayName("B is 1 (B = A)")
//    @Nested private[compare] class B_is_1 {
//
//      @DisplayName("C is 0 (B = A > C)")
//      @Nested private[compare] class C_is_0 {
//        private[compare] val c = BigDecimal.ZERO
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      @DisplayName("C is 1 (B = A = C)")
//      @Nested private[compare] class C_is_1 {
//        private[compare] val c = BigDecimal.ONE
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      @DisplayName("C is 10 (B = A < C)")
//      @Nested private[compare] class C_is_10 {
//        private[compare] val c = BigDecimal.TEN
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      private[compare] val b = BigDecimal.ONE
//
//      @Test private[compare] def equalTo_returns_true() = assertTrue(is(a).equalTo(b))
//
//      @Test private[compare] def greaterThan_returns_false() = assertFalse(is(a).greaterThan(b))
//
//      @Test private[compare] def greaterThanOrEqualTo_returns_true() = assertTrue(is(a).greaterThanOrEqualTo(b))
//
//      @Test private[compare] def lessThan_returns_false() = assertFalse(is(a).lessThan(b))
//
//      @Test private[compare] def lessThanOrEqualTo_returns_true() = assertTrue(is(a).lessThanOrEqualTo(b))
//    }
//
//    @DisplayName("B is 10 (B > A)")
//    @Nested private[compare] class B_is_10 {
//
//      @DisplayName("C is 0 (B > A > C)")
//      @Nested private[compare] class C_is_0 {
//        private[compare] val c = BigDecimal.ZERO
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_true() = assertTrue(is(a).betweenExclusive(b, c))
//      }
//
//      @DisplayName("C is 1 (B > A = C)")
//      @Nested private[compare] class C_is_1 {
//        private[compare] val c = BigDecimal.ONE
//
//        @Test private[compare] def between_returns_true() = assertTrue(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      @DisplayName("C is 10 ([B,C] > A)")
//      @Nested private[compare] class C_is_10 {
//        private[compare] val c = BigDecimal.TEN
//
//        @Test private[compare] def between_returns_false() = assertFalse(is(a).between(b, c))
//
//        @Test private[compare] def betweenExclusive_returns_false() = assertFalse(is(a).betweenExclusive(b, c))
//      }
//
//      private[compare] val b = BigDecimal.TEN
//
//      @Test private[compare] def equalTo_returns_false() = assertFalse(is(a).equalTo(b))
//
//      @Test private[compare] def greaterThan_returns_false() = assertFalse(is(a).greaterThan(b))
//
//      @Test private[compare] def greaterThanOrEqualTo_returns_false() = assertFalse(is(a).greaterThanOrEqualTo(b))
//
//      @Test private[compare] def lessThan_returns_true() = assertTrue(is(a).lessThan(b))
//
//      @Test private[compare] def lessThanOrEqualTo_returns_true() = assertTrue(is(a).lessThanOrEqualTo(b))
//    }
//
//    private[compare] val a = BigDecimal.ONE
//  }
//
//}
