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

package org.apache.commons.lang3.math

import java.lang.{Double => JavaDouble, Float => JavaFloat}
import org.apache.commons.lang3.Validate

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
  * <p>Provides IEEE-754r variants of NumberUtils methods. </p>
  *
  * <p>See: <a href="http://en.wikipedia.org/wiki/IEEE_754r">http://en.wikipedia.org/wiki/IEEE_754r</a></p>
  *
  * @since 2.4
  */
object IEEE754rUtils {
  /**
    * <p>Returns the minimum value in an array.</p>
    *
    * @param array an array, must not be null or empty
    * @return the minimum value in the array
    * @throws java.lang.NullPointerException     if {@code array} is {@code null}
    * @throws java.lang.IllegalArgumentException if {@code array} is empty
    * @since 3.4 Changed signature from min(double[]) to min(double...)
    */
  def min(array: Double*): Double = min(array.toArray)

  def min(array: Array[Double]): Double = {
    Validate.notNull(array, "The Array must not be null")
    Validate.isTrue(array.length != 0, "Array cannot be empty.")
    // Finds and returns min
    var currMin = array(0)
    for (i <- 1 until array.length) {
      currMin = min(array(i), currMin)
    }
    currMin
  }

  /**
    * <p>Returns the minimum value in an array.</p>
    *
    * @param array an array, must not be null or empty
    * @return the minimum value in the array
    * @throws java.lang.NullPointerException     if {@code array} is {@code null}
    * @throws java.lang.IllegalArgumentException if {@code array} is empty
    * @since 3.4 Changed signature from min(float[]) to min(float...)
    */
  def min(array: Float*): Float = min(array.toArray)

  def min(array: Array[Float]): Float = {
    Validate.notNull(array, "The Array must not be null")
    Validate.isTrue(array.length != 0, "Array cannot be empty.")
    var currMin = array(0)
    for (i <- 1 until array.length) {
      currMin = min(array(i), currMin)
    }
    currMin
  }

  /**
    * <p>Gets the minimum of three {@code double} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @param c value 3
    * @return the smallest of the values
    */
  def min(a: Double, b: Double, c: Double): Double = min(min(a, b), c)

  /**
    * <p>Gets the minimum of two {@code double} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @return the smallest of the values
    */
  def min(a: Double, b: Double): Double =
    if (JavaDouble.isNaN(a)) b
    else if (JavaDouble.isNaN(b)) a
    else Math.min(a, b)

  /**
    * <p>Gets the minimum of three {@code float} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @param c value 3
    * @return the smallest of the values
    */
  def min(a: Float, b: Float, c: Float): Float = min(min(a, b), c)

  /**
    * <p>Gets the minimum of two {@code float} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @return the smallest of the values
    */
  def min(a: Float, b: Float): Float =
    if (JavaFloat.isNaN(a)) b
    else if (JavaFloat.isNaN(b)) a
    else Math.min(a, b)

  /**
    * <p>Returns the maximum value in an array.</p>
    *
    * @param array an array, must not be null or empty
    * @return the minimum value in the array
    * @throws java.lang.NullPointerException     if {@code array} is {@code null}
    * @throws java.lang.IllegalArgumentException if {@code array} is empty
    * @since 3.4 Changed signature from max(double[]) to max(double...)
    */
  def max(array: Double*): Double = max(array.toArray)

  def max(array: Array[Double]): Double = {
    Validate.notNull(array, "The Array must not be null")
    Validate.isTrue(array.length != 0, "Array cannot be empty.")
    // Finds and returns max
    var currMax: Double = array(0)
    for (j <- 1 until array.length) {
      currMax = max(array(j), currMax)
    }

    currMax
  }

  /**
    * <p>Returns the maximum value in an array.</p>
    *
    * @param array an array, must not be null or empty
    * @return the minimum value in the array
    * @throws java.lang.NullPointerException     if {@code array} is {@code null}
    * @throws java.lang.IllegalArgumentException if {@code array} is empty
    * @since 3.4 Changed signature from max(float[]) to max(float...)
    */
  def max(array: Float*): Float = max(array.toArray)

  def max(array: Array[Float]): Float = {
    Validate.notNull(array, "The Array must not be null")
    Validate.isTrue(array.length != 0, "Array cannot be empty.")
    var currMax = array(0)
    for (j <- 1 until array.length) {
      currMax = max(array(j), currMax)
    }
    currMax
  }

  /**
    * <p>Gets the maximum of three {@code double} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @param c value 3
    * @return the largest of the values
    */
  def max(a: Double, b: Double, c: Double): Double = max(max(a, b), c)

  /**
    * <p>Gets the maximum of two {@code double} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @return the largest of the values
    */
  def max(a: Double, b: Double): Double =
    if (JavaDouble.isNaN(a)) b
    else if (JavaDouble.isNaN(b)) a
    else Math.max(a, b)

  /**
    * <p>Gets the maximum of three {@code float} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @param c value 3
    * @return the largest of the values
    */
  def max(a: Float, b: Float, c: Float): Float = max(max(a, b), c)

  /**
    * <p>Gets the maximum of two {@code float} values.</p>
    *
    * <p>NaN is only returned if all numbers are NaN as per IEEE-754r. </p>
    *
    * @param a value 1
    * @param b value 2
    * @return the largest of the values
    */
  def max(a: Float, b: Float): Float =
    if (JavaFloat.isNaN(a)) b
    else if (JavaFloat.isNaN(b)) a
    else Math.max(a, b)
}
