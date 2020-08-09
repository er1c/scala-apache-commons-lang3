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

//package org.apache.commons.lang3
//
//import java.util
//import java.util.Collections
//
///**
//  * <p>Utility library to provide helper methods for Java enums.</p>
//  *
//  * <p>#ThreadSafe#</p>
//  *
//  * @since 3.0
//  */
//object EnumUtils {
//  private val NULL_ELEMENTS_NOT_PERMITTED = "null elements not permitted"
//  private val CANNOT_STORE_S_S_VALUES_IN_S_BITS = "Cannot store %s %s values in %s bits"
//  private val S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE = "%s does not seem to be an Enum type"
//  private val ENUM_CLASS_MUST_BE_DEFINED = "EnumClass must be defined."
//
//  /**
//    * Validate {@code enumClass}.
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass to check
//    * @return {@code enumClass}
//    * @throws java.lang.NullPointerException     if {@code enumClass} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class
//    * @since 3.2
//    */
//  private def asEnum[E <: Enum[E]](enumClass: Class[E]) = {
//    Validate.notNull(enumClass, ENUM_CLASS_MUST_BE_DEFINED)
//    Validate.isTrue(enumClass.isEnum, S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE, enumClass)
//    enumClass
//  }
//
//  /**
//    * Validate that {@code enumClass} is compatible with representation in a {@code long}.
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass to check
//    * @return {@code enumClass}
//    * @throws java.lang.NullPointerException     if {@code enumClass} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class or has more than 64 values
//    * @since 3.0.1
//    */
//  private def checkBitVectorable[E <: Enum[E]](enumClass: Class[E]) = {
//    val constants = asEnum(enumClass).getEnumConstants
//    Validate.isTrue(constants.length <= Long.SIZE, CANNOT_STORE_S_S_VALUES_IN_S_BITS, Integer.valueOf(constants.length), enumClass.getSimpleName, Integer.valueOf(Long.SIZE))
//    enumClass
//  }
//
//  /**
//    * <p>Creates a long bit vector representation of the given array of Enum values.</p>
//    *
//    * <p>This generates a value that is usable by {@link EnumUtils# processBitVector}.</p>
//    *
//    * <p>Do not use this method if you have more than 64 values in your Enum, as this
//    * would create a value greater than a long can hold.</p>
//    *
//    * @param enumClass the class of the enum we are working with, not {@code null}
//    * @param values    the values we want to convert, not {@code null}
//    * @param <         E>       the type of the enumeration
//    * @return a long whose value provides a binary representation of the given set of enum values.
//    * @throws java.lang.NullPointerException     if {@code enumClass} or {@code values} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class or has more than 64 values
//    * @since 3.0.1
//    * @see #generateBitVectors(Class, Iterable)
//    */
//  @SafeVarargs def generateBitVector[E <: Enum[E]](enumClass: Class[E], values: E*): Long = {
//    Validate.noNullElements(values)
//    generateBitVector(enumClass, util.Arrays.asList(values))
//  }
//
//  /**
//    * <p>Creates a long bit vector representation of the given subset of an Enum.</p>
//    *
//    * <p>This generates a value that is usable by {@link EnumUtils# processBitVector}.</p>
//    *
//    * <p>Do not use this method if you have more than 64 values in your Enum, as this
//    * would create a value greater than a long can hold.</p>
//    *
//    * @param enumClass the class of the enum we are working with, not {@code null}
//    * @param values    the values we want to convert, not {@code null}, neither containing {@code null}
//    * @param <         E>       the type of the enumeration
//    * @return a long whose value provides a binary representation of the given set of enum values.
//    * @throws java.lang.NullPointerException     if {@code enumClass} or {@code values} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class or has more than 64 values,
//    *                                  or if any {@code values} {@code null}
//    * @since 3.0.1
//    * @see #generateBitVectors(Class, Iterable)
//    */
//  def generateBitVector[E <: Enum[E]](enumClass: Class[E], values: Iterable[_ <: E]): Long = {
//    checkBitVectorable(enumClass)
//    Validate.notNull(values)
//    var total = 0
//    import scala.collection.JavaConversions._
//    for (constant <- values) {
//      Validate.notNull(constant, NULL_ELEMENTS_NOT_PERMITTED)
//      total |= 1L << constant.ordinal
//    }
//    total
//  }
//
//  /**
//    * <p>Creates a bit vector representation of the given subset of an Enum using as many {@code long}s as needed.</p>
//    *
//    * <p>This generates a value that is usable by {@link EnumUtils# processBitVectors}.</p>
//    *
//    * <p>Use this method if you have more than 64 values in your Enum.</p>
//    *
//    * @param enumClass the class of the enum we are working with, not {@code null}
//    * @param values    the values we want to convert, not {@code null}, neither containing {@code null}
//    * @param <         E>       the type of the enumeration
//    * @return a long[] whose values provide a binary representation of the given set of enum values
//    *         with least significant digits rightmost.
//    * @throws java.lang.NullPointerException     if {@code enumClass} or {@code values} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class, or if any {@code values} {@code null}
//    * @since 3.2
//    */
//  @SafeVarargs def generateBitVectors[E <: Enum[E]](enumClass: Class[E], values: E*): Array[Long] = {
//    asEnum(enumClass)
//    Validate.noNullElements(values)
//    val condensed = util.EnumSet.noneOf(enumClass)
//    Collections.addAll(condensed, values)
//    val result = new Array[Long]((enumClass.getEnumConstants.length - 1) / Long.SIZE + 1)
//    import scala.collection.JavaConversions._
//    for (value <- condensed) {
//      result(value.ordinal / Long.SIZE) |= 1L << (value.ordinal % Long.SIZE)
//    }
//    ArrayUtils.reverse(result)
//    result
//  }
//
//  def generateBitVectors[E <: Enum[E]](enumClass: Class[E], values: Iterable[_ <: E]): Array[Long] = {
//    asEnum(enumClass)
//    Validate.notNull(values)
//    val condensed = util.EnumSet.noneOf(enumClass)
//    import scala.collection.JavaConversions._
//    for (constant <- values) {
//      Validate.notNull(constant, NULL_ELEMENTS_NOT_PERMITTED)
//      condensed.add(constant)
//    }
//    val result = new Array[Long]((enumClass.getEnumConstants.length - 1) / Long.SIZE + 1)
//    import scala.collection.JavaConversions._
//    for (value <- condensed) {
//      result(value.ordinal / Long.SIZE) |= 1L << (value.ordinal % Long.SIZE)
//    }
//    ArrayUtils.reverse(result)
//    result
//  }
//
//  /**
//    * <p>Gets the enum for the class, returning {@code null} if not found.</p>
//    *
//    * <p>This method differs from {@link Enum# valueOf} in that it does not throw an exception
//    * for an invalid enum name.</p>
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass the class of the enum to query, not null
//    * @param enumName  the enum name, null returns null
//    * @return the enum, null if not found
//    */
//  def getEnum[E <: Enum[E]](enumClass: Class[E], enumName: String): E = getEnum(enumClass, enumName, null)
//
//  /**
//    * <p>Gets the enum for the class, returning {@code defaultEnum} if not found.</p>
//    *
//    * <p>This method differs from {@link Enum# valueOf} in that it does not throw an exception
//    * for an invalid enum name.</p>
//    *
//    * @param <           E> the type of the enumeration
//    * @param enumClass   the class of the enum to query, not null
//    * @param enumName    the enum name, null returns default enum
//    * @param defaultEnum the default enum
//    * @return the enum, default enum if not found
//    * @since 3.10
//    */
//  def getEnum[E <: Enum[E]](enumClass: Class[E], enumName: String, defaultEnum: E): E = {
//    if (enumName == null) return defaultEnum
//    try Enum.valueOf(enumClass, enumName)
//    catch {
//      case ex: IllegalArgumentException =>
//        defaultEnum
//    }
//  }
//
//  /**
//    * <p>Gets the enum for the class, returning {@code null} if not found.</p>
//    *
//    * <p>This method differs from {@link Enum# valueOf} in that it does not throw an exception
//    * for an invalid enum name and performs case insensitive matching of the name.</p>
//    *
//    * @param <         E>         the type of the enumeration
//    * @param enumClass the class of the enum to query, not null
//    * @param enumName  the enum name, null returns null
//    * @return the enum, null if not found
//    * @since 3.8
//    */
//  def getEnumIgnoreCase[E <: Enum[E]](enumClass: Class[E], enumName: String): E = getEnumIgnoreCase(enumClass, enumName, null)
//
//  /**
//    * <p>Gets the enum for the class, returning {@code defaultEnum} if not found.</p>
//    *
//    * <p>This method differs from {@link Enum# valueOf} in that it does not throw an exception
//    * for an invalid enum name and performs case insensitive matching of the name.</p>
//    *
//    * @param <           E>         the type of the enumeration
//    * @param enumClass   the class of the enum to query, not null
//    * @param enumName    the enum name, null returns default enum
//    * @param defaultEnum the default enum
//    * @return the enum, default enum if not found
//    * @since 3.10
//    */
//  def getEnumIgnoreCase[E <: Enum[E]](enumClass: Class[E], enumName: String, defaultEnum: E): E = {
//    if (enumName == null || !enumClass.isEnum) return defaultEnum
//    for (each <- enumClass.getEnumConstants) {
//      if (each.name.equalsIgnoreCase(enumName)) return each
//    }
//    defaultEnum
//  }
//
//  /**
//    * <p>Gets the {@code List} of enums.</p>
//    *
//    * <p>This method is useful when you need a list of enums rather than an array.</p>
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass the class of the enum to query, not null
//    * @return the modifiable list of enums, never null
//    */
//  def getEnumList[E <: Enum[E]](enumClass: Class[E]) = new util.ArrayList[E](util.Arrays.asList(enumClass.getEnumConstants))
//
//  /**
//    * <p>Gets the {@code Map} of enums by name.</p>
//    *
//    * <p>This method is useful when you need a map of enums by name.</p>
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass the class of the enum to query, not null
//    * @return the modifiable map of enum names to enums, never null
//    */
//  def getEnumMap[E <: Enum[E]](enumClass: Class[E]): util.Map[String, E] = {
//    val map = new util.LinkedHashMap[String, E]
//    for (e <- enumClass.getEnumConstants) {
//      map.put(e.name, e)
//    }
//    map
//  }
//
//  /**
//    * <p>Checks if the specified name is a valid enum for the class.</p>
//    *
//    * <p>This method differs from {@link Enum# valueOf} in that checks if the name is
//    * a valid enum without needing to catch the exception.</p>
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass the class of the enum to query, not null
//    * @param enumName  the enum name, null returns false
//    * @return true if the enum name is valid, otherwise false
//    */
//  def isValidEnum[E <: Enum[E]](enumClass: Class[E], enumName: String): Boolean = getEnum(enumClass, enumName) != null
//
//  /**
//    * <p>Checks if the specified name is a valid enum for the class.</p>
//    *
//    * <p>This method differs from {@link Enum# valueOf} in that checks if the name is
//    * a valid enum without needing to catch the exception
//    * and performs case insensitive matching of the name.</p>
//    *
//    * @param <         E> the type of the enumeration
//    * @param enumClass the class of the enum to query, not null
//    * @param enumName  the enum name, null returns false
//    * @return true if the enum name is valid, otherwise false
//    * @since 3.8
//    */
//  def isValidEnumIgnoreCase[E <: Enum[E]](enumClass: Class[E], enumName: String): Boolean = getEnumIgnoreCase(enumClass, enumName) != null
//
//  /**
//    * <p>Convert a long value created by {@link EnumUtils# generateBitVector} into the set of
//    * enum values that it represents.</p>
//    *
//    * <p>If you store this value, beware any changes to the enum that would affect ordinal values.</p>
//    *
//    * @param enumClass the class of the enum we are working with, not {@code null}
//    * @param value     the long value representation of a set of enum values
//    * @param <         E>       the type of the enumeration
//    * @return a set of enum values
//    * @throws java.lang.NullPointerException     if {@code enumClass} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class or has more than 64 values
//    * @since 3.0.1
//    */
//  def processBitVector[E <: Enum[E]](enumClass: Class[E], value: Long): util.EnumSet[E] = {
//    checkBitVectorable(enumClass).getEnumConstants
//    processBitVectors(enumClass, value)
//  }
//
//  /**
//    * <p>Convert a {@code long[]} created by {@link EnumUtils# generateBitVectors} into the set of
//    * enum values that it represents.</p>
//    *
//    * <p>If you store this value, beware any changes to the enum that would affect ordinal values.</p>
//    *
//    * @param enumClass the class of the enum we are working with, not {@code null}
//    * @param values    the long[] bearing the representation of a set of enum values, least significant digits rightmost, not {@code null}
//    * @param <         E>       the type of the enumeration
//    * @return a set of enum values
//    * @throws java.lang.NullPointerException     if {@code enumClass} is {@code null}
//    * @throws java.lang.IllegalArgumentException if {@code enumClass} is not an enum class
//    * @since 3.2
//    */
//  def processBitVectors[E <: Enum[E]](enumClass: Class[E], values: Long*): util.EnumSet[E] = {
//    val results = util.EnumSet.noneOf(asEnum(enumClass))
//    val lvalues = ArrayUtils.clone(Validate.notNull(values))
//    ArrayUtils.reverse(lvalues)
//    for (constant <- enumClass.getEnumConstants) {
//      val block = constant.ordinal / Long.SIZE
//      if (block < lvalues.length && (lvalues(block) & 1L << (constant.ordinal % Long.SIZE)) != 0) results.add(constant)
//    }
//    results
//  }
//}
