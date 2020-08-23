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

object TestHelpers {
  val DoubleDelta: Double = 1e-9d
  val FloatDelta: Float = 1e-9f
}

trait TestHelpers {
  protected def DoubleDelta: Double = TestHelpers.DoubleDelta
  protected def FloatDelta: Float = TestHelpers.FloatDelta

//  implicit def toArrayObject(arr: Array[Boolean]): Array[Object] =
//    ArrayUtils.toObject(arr).asInstanceOf[Array[Object]]

  def assertArrayEquals(expecteds: Array[Boolean], actuals: Array[Boolean]) =
    org.junit.Assert.assertArrayEquals(
      ArrayUtils.toObject(expecteds).asInstanceOf[Array[Object]],
      ArrayUtils.toObject(actuals).asInstanceOf[Array[Object]]
    )

  def assertArrayEquals(expecteds: Array[Byte], actuals: Array[Byte]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals)

  def assertArrayEquals(expecteds: Array[Char], actuals: Array[Char]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals)

  def assertArrayEquals(expecteds: Array[Double], actuals: Array[Double]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals, DoubleDelta)

  def assertArrayEquals(expecteds: Array[Float], actuals: Array[Float]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals, FloatDelta)

  def assertArrayEquals(expecteds: Array[Int], actuals: Array[Int]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals)

  def assertArrayEquals(expecteds: Array[Long], actuals: Array[Long]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals)

  def assertArrayEquals(expecteds: Array[Short], actuals: Array[Short]) =
    org.junit.Assert.assertArrayEquals(expecteds, actuals)

  def assertArrayEquals[T <: AnyRef](expecteds: Array[T], actuals: Array[T]) =
    org.junit.Assert.assertArrayEquals(expecteds.asInstanceOf[Array[Object]], actuals.asInstanceOf[Array[Object]])

//  def assertArrayEquals(expecteds: Array[Boolean], actuals: Array[Boolean]) =
//    org.junit.Assert.assertArrayEquals(expecteds, actuals)
}
