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
//import java.util
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.Validate
//
///**
//  * <p>
//  * Assists in implementing {@link Diffable# diff ( Object )} methods.
//  * </p>
//  *
//  * <p>
//  * To use this class, write code as follows:
//  * </p>
//  *
//  * <pre>
//  * public class Person implements Diffable&lt;Person&gt; {
//  * String name;
//  * int age;
//  * boolean smoker;
//  *
//  * ...
//  *
//  * public DiffResult diff(Person obj) {
//  * // No need for null check, as NullPointerException correct if obj is null
//  * return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
//  * .append("name", this.name, obj.name)
//  * .append("age", this.age, obj.age)
//  * .append("smoker", this.smoker, obj.smoker)
//  * .build();
//  * }
//  * }
//  * </pre>
//  *
//  * <p>
//  * The {@code ToStringStyle} passed to the constructor is embedded in the
//  * returned {@code DiffResult} and influences the style of the
//  * {@code DiffResult.toString()} method. This style choice can be overridden by
//  * calling {@link DiffResult# toString ( ToStringStyle )}.
//  * </p>
//  *
//  * @param < T> type of the left and right object.
//  * @see Diffable
//  * @see Diff
//  * @see DiffResult
//  * @see ToStringStyle
//  * @since 3.3
//  */
//class DiffBuilder[T](val left: T, val right: T, val style: ToStringStyle, val testTriviallyEqual: Boolean)
//
///**
//  * <p>
//  * Constructs a builder for the specified objects with the specified style.
//  * </p>
//  *
//  * <p>
//  * If {@code lhs == rhs} or {@code lhs.equals(rhs)} then the builder will
//  * not evaluate any calls to {@code append(...)} and will return an empty
//  * {@link DiffResult} when {@link #build ( )} is executed.
//  * </p>
//  *
//  * @param lhs
//  * {@code this} object
//  * @param rhs
//  * the object to diff against
//  * @param style
//  * the style will use when outputting the objects, {@code null}
//  * uses the default
//  * @param testTriviallyEqual
//  * If true, this will test if lhs and rhs are the same or equal.
//  * All of the append(fieldName, lhs, rhs) methods will abort
//  * without creating a field {@link Diff} if the trivially equal
//  * test is enabled and returns true.  The result of this test
//  * is never changed throughout the life of this {@link DiffBuilder}.
//  * @throws IllegalArgumentException
//  * if {@code lhs} or {@code rhs} is {@code null}
//  * @since 3.4
//  */
//  extends Builder[DiffResult] {
//  Validate.notNull(left, "lhs cannot be null")
//  Validate.notNull(right, "rhs cannot be null")
//  this.diffs = new (ArrayList[Diff[_$1]])
//  forSome
//  {
//    type _$1
//  }
//  // Don't compare any fields if objects equal
//  this.objectsTriviallyEqual = testTriviallyEqual && ((left eq right) || left == right)
//  final private var diffs = null
//  final private var objectsTriviallyEqual = false
//
//  /**
//    * <p>
//    * Constructs a builder for the specified objects with the specified style.
//    * </p>
//    *
//    * <p>
//    * If {@code lhs == rhs} or {@code lhs.equals(rhs)} then the builder will
//    * not evaluate any calls to {@code append(...)} and will return an empty
//    * {@link DiffResult} when {@link #build ( )} is executed.
//    * </p>
//    *
//    * <p>
//    * This delegates to {@link #DiffBuilder ( Object, Object, ToStringStyle, boolean)}
//    * with the testTriviallyEqual flag enabled.
//    * </p>
//    *
//    * @param lhs
//    * {@code this} object
//    * @param rhs
//    * the object to diff against
//    * @param style
//    * the style will use when outputting the objects, {@code null}
//    * uses the default
//    * @throws IllegalArgumentException
//    * if {@code lhs} or {@code rhs} is {@code null}
//    */
//  def this(lhs: T, rhs: T, style: ToStringStyle) {
//    this(lhs, rhs, style, true)
//  }
//
//  /**
//    * <p>
//    * Test if two {@code boolean}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code boolean}
//    * @param rhs
//    * the right hand {@code boolean}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Boolean, rhs: Boolean): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs != rhs) diffs.add(new Diff[Boolean](fieldName) {
//      override def getLeft: Boolean = Boolean.valueOf(lhs)
//
//      override
//
//      def getRight: Boolean = Boolean.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code boolean[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code boolean[]}
//    * @param rhs
//    * the right hand {@code boolean[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Boolean], rhs: Array[Boolean]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Boolean]](fieldName) {
//      override def getLeft: Array[Boolean] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Boolean] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code byte}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code byte}
//    * @param rhs
//    * the right hand {@code byte}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Byte, rhs: Byte): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs != rhs) diffs.add(new Diff[Byte](fieldName) {
//      override def getLeft: Byte = Byte.valueOf(lhs)
//
//      override
//
//      def getRight: Byte = Byte.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code byte[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code byte[]}
//    * @param rhs
//    * the right hand {@code byte[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Byte], rhs: Array[Byte]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Byte]](fieldName) {
//      override def getLeft: Array[Byte] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Byte] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code char}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code char}
//    * @param rhs
//    * the right hand {@code char}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Char, rhs: Char): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs != rhs) diffs.add(new Diff[Character](fieldName) {
//      override def getLeft: Character = Character.valueOf(lhs)
//
//      override
//
//      def getRight: Character = Character.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code char[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code char[]}
//    * @param rhs
//    * the right hand {@code char[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Char], rhs: Array[Char]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Character]](fieldName) {
//      override def getLeft: Array[Character] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Character] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code double}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code double}
//    * @param rhs
//    * the right hand {@code double}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Double, rhs: Double): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (Double.doubleToLongBits(lhs) != Double.doubleToLongBits(rhs)) diffs.add(new Diff[Double](fieldName) {
//      override def getLeft: Double = Double.valueOf(lhs)
//
//      override
//
//      def getRight: Double = Double.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code double[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code double[]}
//    * @param rhs
//    * the right hand {@code double[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Double], rhs: Array[Double]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Double]](fieldName) {
//      override def getLeft: Array[Double] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Double] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code float}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code float}
//    * @param rhs
//    * the right hand {@code float}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Float, rhs: Float): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (Float.floatToIntBits(lhs) != Float.floatToIntBits(rhs)) diffs.add(new Diff[Float](fieldName) {
//      override def getLeft: Float = Float.valueOf(lhs)
//
//      override
//
//      def getRight: Float = Float.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code float[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code float[]}
//    * @param rhs
//    * the right hand {@code float[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Float], rhs: Array[Float]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Float]](fieldName) {
//      override def getLeft: Array[Float] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Float] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code int}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code int}
//    * @param rhs
//    * the right hand {@code int}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Int, rhs: Int): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs != rhs) diffs.add(new Diff[Integer](fieldName) {
//      override def getLeft: Integer = Integer.valueOf(lhs)
//
//      override
//
//      def getRight: Integer = Integer.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code int[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code int[]}
//    * @param rhs
//    * the right hand {@code int[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Int], rhs: Array[Int]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Integer]](fieldName) {
//      override def getLeft: Array[Integer] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Integer] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code long}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code long}
//    * @param rhs
//    * the right hand {@code long}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Long, rhs: Long): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs != rhs) diffs.add(new Diff[Long](fieldName) {
//      override def getLeft: Long = Long.valueOf(lhs)
//
//      override
//
//      def getRight: Long = Long.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code long[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code long[]}
//    * @param rhs
//    * the right hand {@code long[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Long], rhs: Array[Long]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Long]](fieldName) {
//      override def getLeft: Array[Long] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Long] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code short}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code short}
//    * @param rhs
//    * the right hand {@code short}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Short, rhs: Short): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs != rhs) diffs.add(new Diff[Short](fieldName) {
//      override def getLeft: Short = Short.valueOf(lhs)
//
//      override
//
//      def getRight: Short = Short.valueOf(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code short[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code short[]}
//    * @param rhs
//    * the right hand {@code short[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[Short], rhs: Array[Short]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[Short]](fieldName) {
//      override def getLeft: Array[Short] = ArrayUtils.toObject(lhs)
//
//      override
//
//      def getRight: Array[Short] = ArrayUtils.toObject(rhs)
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code Objects}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code Object}
//    * @param rhs
//    * the right hand {@code Object}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Any, rhs: Any): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (lhs eq rhs) return this
//    var objectToTest = null
//    if (lhs != null) objectToTest = lhs
//    else { // rhs cannot be null, as lhs != rhs
//      objectToTest = rhs
//    }
//    if (objectToTest.getClass.isArray) {
//      if (objectToTest.isInstanceOf[Array[Boolean]]) return append(fieldName, lhs.asInstanceOf[Array[Boolean]], rhs.asInstanceOf[Array[Boolean]])
//      if (objectToTest.isInstanceOf[Array[Byte]]) return append(fieldName, lhs.asInstanceOf[Array[Byte]], rhs.asInstanceOf[Array[Byte]])
//      if (objectToTest.isInstanceOf[Array[Char]]) return append(fieldName, lhs.asInstanceOf[Array[Char]], rhs.asInstanceOf[Array[Char]])
//      if (objectToTest.isInstanceOf[Array[Double]]) return append(fieldName, lhs.asInstanceOf[Array[Double]], rhs.asInstanceOf[Array[Double]])
//      if (objectToTest.isInstanceOf[Array[Float]]) return append(fieldName, lhs.asInstanceOf[Array[Float]], rhs.asInstanceOf[Array[Float]])
//      if (objectToTest.isInstanceOf[Array[Int]]) return append(fieldName, lhs.asInstanceOf[Array[Int]], rhs.asInstanceOf[Array[Int]])
//      if (objectToTest.isInstanceOf[Array[Long]]) return append(fieldName, lhs.asInstanceOf[Array[Long]], rhs.asInstanceOf[Array[Long]])
//      if (objectToTest.isInstanceOf[Array[Short]]) return append(fieldName, lhs.asInstanceOf[Array[Short]], rhs.asInstanceOf[Array[Short]])
//      return append(fieldName, lhs.asInstanceOf[Array[AnyRef]], rhs.asInstanceOf[Array[AnyRef]])
//    }
//    // Not array type
//    if (lhs != null && lhs == rhs) return this
//    diffs.add(new Diff[AnyRef](fieldName) {
//      override def getLeft: Any = lhs
//
//      override
//
//      def getRight: Any = rhs
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Test if two {@code Object[]}s are equal.
//    * </p>
//    *
//    * @param fieldName
//    * the field name
//    * @param lhs
//    * the left hand {@code Object[]}
//    * @param rhs
//    * the right hand {@code Object[]}
//    * @return this
//    * @throws IllegalArgumentException
//    * if field name is {@code null}
//    */
//  def append(fieldName: String, lhs: Array[AnyRef], rhs: Array[AnyRef]): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    if (objectsTriviallyEqual) return this
//    if (!util.Arrays.equals(lhs, rhs)) diffs.add(new Diff[Array[AnyRef]](fieldName) {
//      override def getLeft: Array[AnyRef] = lhs
//
//      override
//
//      def getRight: Array[AnyRef] = rhs
//    })
//    this
//  }
//
//  /**
//    * <p>
//    * Append diffs from another {@code DiffResult}.
//    * </p>
//    *
//    * <p>
//    * This method is useful if you want to compare properties which are
//    * themselves Diffable and would like to know which specific part of
//    * it is different.
//    * </p>
//    *
//    * <pre>
//    * public class Person implements Diffable&lt;Person&gt; {
//    * String name;
//    * Address address; // implements Diffable&lt;Address&gt;
//    *
//    * ...
//    *
//    * public DiffResult diff(Person obj) {
//    * return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
//    * .append("name", this.name, obj.name)
//    * .append("address", this.address.diff(obj.address))
//    * .build();
//    * }
//    * }
//    * </pre>
//    *
//    * @param fieldName
//    * the field name
//    * @param diffResult
//    * the {@code DiffResult} to append
//    * @return this
//    * @throws NullPointerException if field name is {@code null}
//    * @since 3.5
//    */
//  def append(fieldName: String, diffResult: DiffResult): DiffBuilder[T] = {
//    validateFieldNameNotNull(fieldName)
//    Validate.notNull(diffResult, "Diff result cannot be null")
//    if (objectsTriviallyEqual) return this
//    import scala.collection.JavaConversions._
//    for (diff <- diffResult.getDiffs) {
//      append(fieldName + "." + diff.getFieldName, diff.getLeft, diff.getRight)
//    }
//    this
//  }
//
//  /**
//    * <p>
//    * Builds a {@link DiffResult} based on the differences appended to this
//    * builder.
//    * </p>
//    *
//    * @return a {@code DiffResult} containing the differences between the two
//    *         objects.
//    */
//  override def build = new DiffResult(left, right, diffs, style)
//
//  private def validateFieldNameNotNull(fieldName: String): Unit = {
//    Validate.notNull(fieldName, "Field name cannot be null")
//  }
//}
