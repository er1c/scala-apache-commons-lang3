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

import java.util.Random

/**
  * <p>Utility library that supplements the standard {@link java.util.Random} class.</p>
  *
  * <p>Caveat: Instances of {@link java.util.Random} are not cryptographically secure.</p>
  *
  * <p>Please note that the Apache Commons project provides a component
  * dedicated to pseudo-random number generation, namely
  * <a href="https://commons.apache.org/rng">Commons RNG</a>, that may be
  * a better choice for applications with more stringent requirements
  * (performance and/or correctness).</p>
  *
  * @since 3.3
  */
object RandomUtils {
  /**
    * Random object used by random method. This has to be not local to the
    * random method so as to not return the same value in the same millisecond.
    */
  private val RANDOM = new Random

  /**
    * <p>
    * Returns a random boolean value
    * </p>
    *
    * @return the random boolean
    * @since 3.5
    */
  def nextBoolean: Boolean = RANDOM.nextBoolean

  /**
    * <p>
    * Creates an array of random bytes.
    * </p>
    *
    * @param count
    * the size of the returned array
    * @return the random byte array
    * @throws java.lang.IllegalArgumentException if {@code count} is negative
    */
  def nextBytes(count: Int): Array[Byte] = {
    Validate.isTrue(count >= 0, "Count cannot be negative.")
    val result = new Array[Byte](count)
    RANDOM.nextBytes(result)
    result
  }

  /**
    * <p>
    * Returns a random integer within the specified range.
    * </p>
    *
    * @param startInclusive
    * the smallest value that can be returned, must be non-negative
    * @param endExclusive
    * the upper bound (not included)
    * @throws java.lang.IllegalArgumentException
    * if {@code startInclusive > endExclusive} or if
    * {@code startInclusive} is negative
    * @return the random integer
    */
  def nextInt(startInclusive: Int, endExclusive: Int): Int = {
    Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.")
    Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.")
    if (startInclusive == endExclusive) return startInclusive
    startInclusive + RANDOM.nextInt(endExclusive - startInclusive)
  }

  /**
    * <p> Returns a random int within 0 - Integer.MAX_VALUE </p>
    *
    * @return the random integer
    * @see #nextInt(int, int)
    * @since 3.5
    */
  def nextInt: Int = nextInt(0, Int.MaxValue)

  /**
    * <p>
    * Returns a random long within the specified range.
    * </p>
    *
    * @param startInclusive
    * the smallest value that can be returned, must be non-negative
    * @param endExclusive
    * the upper bound (not included)
    * @throws java.lang.IllegalArgumentException
    * if {@code startInclusive > endExclusive} or if
    * {@code startInclusive} is negative
    * @return the random long
    */
  def nextLong(startInclusive: Long, endExclusive: Long): Long = {
    Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.")
    Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.")
    if (startInclusive == endExclusive) return startInclusive
    startInclusive + nextLong(endExclusive - startInclusive)
  }

  /**
    * <p> Returns a random long within 0 - Long.MAX_VALUE </p>
    *
    * @return the random long
    * @see #nextLong(long, long)
    * @since 3.5
    */
  def nextLong: Long = nextLong(Long.MaxValue)

  /**
    * Generates a {@code long} value between 0 (inclusive) and the specified
    * value (exclusive).
    *
    * @param n Bound on the random number to be returned.  Must be positive.
    * @return a random {@code long} value between 0 (inclusive) and {@code n}
    *         (exclusive).
    */
  private def nextLong(n: Long) = { // Extracted from o.a.c.rng.core.BaseProvider.nextLong(long)
    var bits = 0L
    var `val` = 0L
    do {
      bits = RANDOM.nextLong >>> 1
      `val` = bits % n
    } while ({
      bits - `val` + (n - 1) < 0
    })
    `val`
  }

  /**
    * <p>
    * Returns a random double within the specified range.
    * </p>
    *
    * @param startInclusive
    * the smallest value that can be returned, must be non-negative
    * @param endExclusive
    * the upper bound (not included)
    * @throws java.lang.IllegalArgumentException
    * if {@code startInclusive > endExclusive} or if
    * {@code startInclusive} is negative
    * @return the random double
    */
  def nextDouble(startInclusive: Double, endExclusive: Double): Double = {
    Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.")
    Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.")
    if (startInclusive == endExclusive) return startInclusive
    startInclusive + ((endExclusive - startInclusive) * RANDOM.nextDouble)
  }

  /**
    * <p> Returns a random double within 0 - Double.MAX_VALUE </p>
    *
    * @return the random double
    * @see #nextDouble(double, double)
    * @since 3.5
    */
  def nextDouble: Double = nextDouble(0, Double.MaxValue)

  /**
    * <p>
    * Returns a random float within the specified range.
    * </p>
    *
    * @param startInclusive
    * the smallest value that can be returned, must be non-negative
    * @param endExclusive
    * the upper bound (not included)
    * @throws java.lang.IllegalArgumentException
    * if {@code startInclusive > endExclusive} or if
    * {@code startInclusive} is negative
    * @return the random float
    */
  def nextFloat(startInclusive: Float, endExclusive: Float): Float = {
    Validate.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.")
    Validate.isTrue(startInclusive >= 0, "Both range values must be non-negative.")
    if (startInclusive == endExclusive) return startInclusive
    startInclusive + ((endExclusive - startInclusive) * RANDOM.nextFloat)
  }

  /**
    * <p> Returns a random float within 0 - Float.MAX_VALUE </p>
    *
    * @return the random float
    * @see #nextFloat(float, float)
    * @since 3.5
    */
  def nextFloat: Float = nextFloat(0, Float.MaxValue)
}
