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

package org.apache.commons.lang3.compare

import java.util.function.{Predicate => JPredicate}

/**
  * <p>Utility library to provide helper methods for translating {@link java.lang.Comparable#compareTo} result into a boolean.</p>
  *
  * <p>Example: {@code boolean x = is(myComparable).lessThanOrEqualTo(otherComparable)}</p>
  *
  * <p>#ThreadSafe#</p>
  *
  * @since 3.10
  */
object ComparableUtils {

  /**
    * Provides access to the available methods
    *
    * @param < A> the type of objects that this object may be compared against.
    */
  class ComparableCheckBuilder[A <: Comparable[A]] private[ComparableUtils](val a: A) {
    /**
      * Checks if {@code [b <= a <= c]} or {@code [b >= a >= c]} where the {@code a} is object passed to {@link #is}.
      *
      * @param b the object to compare to the base object
      * @param c the object to compare to the base object
      * @return true if the base object is between b and c
      */
    def between(b: A, c: A): Boolean = betweenOrdered(b, c) || betweenOrdered(c, b)

    /**
      * Checks if {@code (b < a < c)} or {@code (b > a > c)} where the {@code a} is object passed to {@link #is}.
      *
      * @param b the object to compare to the base object
      * @param c the object to compare to the base object
      * @return true if the base object is between b and c and not equal to those
      */
    def betweenExclusive(b: A, c: A): Boolean = betweenOrderedExclusive(b, c) || betweenOrderedExclusive(c, b)

    private def betweenOrdered(b: A, c: A): Boolean = greaterThanOrEqualTo(b) && lessThanOrEqualTo(c)

    private def betweenOrderedExclusive(b: A, c: A): Boolean = greaterThan(b) && lessThan(c)

    /**
      * Checks if the object passed to {@link #is} is equal to {@code b}
      *
      * @param b the object to compare to the base object
      * @return true if the value returned by {@link java.lang.Comparable#compareTo} is equal to {@code 0}
      */
    def equalTo(b: A): Boolean = a.compareTo(b) == 0

    /**
      * Checks if the object passed to {@link #is} is greater than {@code b}
      *
      * @param b the object to compare to the base object
      * @return true if the value returned by {@link java.lang.Comparable#compareTo} is greater than {@code 0}
      */
    def greaterThan(b: A): Boolean = a.compareTo(b) > 0

    /**
      * Checks if the object passed to {@link #is} is greater than or equal to {@code b}
      *
      * @param b the object to compare to the base object
      * @return true if the value returned by {@link java.lang.Comparable#compareTo} is greater than or equal to {@code 0}
      */
    def greaterThanOrEqualTo(b: A): Boolean = a.compareTo(b) >= 0

    /**
      * Checks if the object passed to {@link #is} is less than {@code b}
      *
      * @param b the object to compare to the base object
      * @return true if the value returned by {@link java.lang.Comparable#compareTo} is less than {@code 0}
      */
    def lessThan(b: A): Boolean = a.compareTo(b) < 0

    /**
      * Checks if the object passed to {@link #is} is less than or equal to {@code b}
      *
      * @param b the object to compare to the base object
      * @return true if the value returned by {@link java.lang.Comparable#compareTo} is less than or equal to {@code 0}
      */
    def lessThanOrEqualTo(b: A): Boolean = a.compareTo(b) <= 0
  }

  /**
    * Checks if {@code [b <= a <= c]} or {@code [b >= a >= c]} where the {@code a} is the tested object.
    *
    * @tparam A type of the test object
    * @param b  the object to compare to the tested object
    * @param c  the object to compare to the tested object
    * @return a predicate for true if the tested object is between b and c
    */
  def between[A <: Comparable[A]](b: A, c: A): JPredicate[A] =
    new JPredicate[A] {
      override def test(a: A): Boolean = is(a).between(b, c)
    }

  /**
    * Checks if {@code (b < a < c)} or {@code (b > a > c)} where the {@code a} is the tested object.
    *
    * @tparam A type of the test object
    * @param b  the object to compare to the tested object
    * @param c  the object to compare to the tested object
    * @return   a predicate for true if the tested object is between b and c and not equal to those
    */
  def betweenExclusive[A <: Comparable[A]](b: A, c: A): JPredicate[A] =
    new JPredicate[A] {
      override def test(a: A): Boolean = is(a).betweenExclusive(b, c)
    }

  /**
    * Checks if the tested object is greater than or equal to {@code b}
    *
    * @tparam A type of the test object
    * @param b  the object to compare to the tested object
    * @return a predicate for true if the value returned by {@link java.lang.Comparable#compareTo}
    *         is greater than or equal to {@code 0}
    */
  def ge[A <: Comparable[A]](b: A): JPredicate[A] =
    new JPredicate[A] {
      override def test(a: A): Boolean = is(a).greaterThanOrEqualTo(b)
    }

  /**
    * Checks if the tested object is greater than {@code b}
    *
    * @tparam A type of the test object
    * @param b  the object to compare to the tested object
    * @return a predicate for true if the value returned by {@link java.lang.Comparable#compareTo} is greater than {@code 0}
    */
  def gt[A <: Comparable[A]](b: A): JPredicate[A] =
    new JPredicate[A] {
      override def test(a: A): Boolean = is(a).greaterThan(b)
    }

  /**
    * Provides access to the available methods
    *
    * @tparam A type of the base object
    * @param a  base object in the further comparison
    * @return a builder object with further methods
    */
  def is[A <: Comparable[A]](a: A) = new ComparableCheckBuilder[A](a)

  /**
    * Checks if the tested object is less than or equal to {@code b}
    *
    * @tparam A type of the test object
    * @param b  the object to compare to the tested object
    * @return a predicate for true if the value returned by {@link java.lang.Comparable#compareTo}
    *         is less than or equal to {@code 0}
    */
  def le[A <: Comparable[A]](b: A): JPredicate[A] =
    new JPredicate[A] {
      override def test(a: A): Boolean = is(a).lessThanOrEqualTo(b)
    }

  /**
    * Checks if the tested object is less than {@code b}
    *
    * @tparam A type of the test object
    * @param b  the object to compare to the tested object
    * @return   a predicate for true if the value returned by {@link java.lang.Comparable#compareTo} is less than {@code 0}
    */
  def lt[A <: Comparable[A]](b: A): JPredicate[A] =
    new JPredicate[A] {
      override def test(a: A): Boolean = is(a).lessThan(b)
    }
}