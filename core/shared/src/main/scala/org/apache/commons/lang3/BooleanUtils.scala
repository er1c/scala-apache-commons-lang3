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

import org.apache.commons.lang3.math.NumberUtils
import java.lang.{Boolean => JavaBoolean}

/**
  * <p>Operations on boolean primitives and Boolean objects.</p>
  *
  * <p>This class tries to handle {@code null} input gracefully.
  * An exception will not be thrown for a {@code null} input.
  * Each method documents its behavior in more detail.</p>
  *
  * <p>#ThreadSafe#</p>
  *
  * @since 2.0
  */
object BooleanUtils {
  /**
    * <p>Negates the specified boolean.</p>
    *
    * <p>If {@code null} is passed in, {@code null} will be returned.</p>
    *
    * <p>NOTE: This returns {@code null} and will throw a {@code NullPointerException}
    * if unboxed to a boolean. </p>
    *
    * <pre>
    * BooleanUtils.negate(Boolean.TRUE)  = Boolean.FALSE;
    * BooleanUtils.negate(Boolean.FALSE) = Boolean.TRUE;
    * BooleanUtils.negate(null)          = null;
    * </pre>
    *
    * @param bool the Boolean to negate, may be null
    * @return the negated Boolean, or {@code null} if {@code null} input
    */
  def negate(bool: JavaBoolean): JavaBoolean = {
    if (bool == null) return null
    if (bool.booleanValue) JavaBoolean.FALSE
    else JavaBoolean.TRUE
  }

  /**
    * <p>Checks if a {@code Boolean} value is {@code true},
    * handling {@code null} by returning {@code false}.</p>
    *
    * <pre>
    * BooleanUtils.isTrue(Boolean.TRUE)  = true
    * BooleanUtils.isTrue(Boolean.FALSE) = false
    * BooleanUtils.isTrue(null)          = false
    * </pre>
    *
    * @param bool the boolean to check, {@code null} returns {@code false}
    * @return {@code true} only if the input is non-null and true
    * @since 2.1
    */
  def isTrue(bool: JavaBoolean): JavaBoolean = JavaBoolean.TRUE == bool

  /**
    * <p>Checks if a {@code Boolean} value is <i>not</i> {@code true},
    * handling {@code null} by returning {@code true}.</p>
    *
    * <pre>
    * BooleanUtils.isNotTrue(Boolean.TRUE)  = false
    * BooleanUtils.isNotTrue(Boolean.FALSE) = true
    * BooleanUtils.isNotTrue(null)          = true
    * </pre>
    *
    * @param bool the boolean to check, null returns {@code true}
    * @return {@code true} if the input is null or false
    * @since 2.3
    */
  def isNotTrue(bool: JavaBoolean): JavaBoolean = !isTrue(bool)

  /**
    * <p>Checks if a {@code Boolean} value is {@code false},
    * handling {@code null} by returning {@code false}.</p>
    *
    * <pre>
    * BooleanUtils.isFalse(Boolean.TRUE)  = false
    * BooleanUtils.isFalse(Boolean.FALSE) = true
    * BooleanUtils.isFalse(null)          = false
    * </pre>
    *
    * @param bool the boolean to check, null returns {@code false}
    * @return {@code true} only if the input is non-{@code null} and {@code false}
    * @since 2.1
    */
  def isFalse(bool: JavaBoolean): JavaBoolean = JavaBoolean.FALSE == bool

  /**
    * <p>Checks if a {@code Boolean} value is <i>not</i> {@code false},
    * handling {@code null} by returning {@code true}.</p>
    *
    * <pre>
    * BooleanUtils.isNotFalse(Boolean.TRUE)  = true
    * BooleanUtils.isNotFalse(Boolean.FALSE) = false
    * BooleanUtils.isNotFalse(null)          = true
    * </pre>
    *
    * @param bool the boolean to check, null returns {@code true}
    * @return {@code true} if the input is {@code null} or {@code true}
    * @since 2.3
    */
  def isNotFalse(bool: JavaBoolean): JavaBoolean = !isFalse(bool)

  /**
    * <p>Converts a JavaBoolean to a boolean handling {@code null}
    * by returning {@code false}.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean(Boolean.TRUE)  = true
    * BooleanUtils.toBoolean(Boolean.FALSE) = false
    * BooleanUtils.toBoolean(null)          = false
    * </pre>
    *
    * @param bool the boolean to convert
    * @return {@code true} or {@code false}, {@code null} returns {@code false}
    */
  def toBoolean(bool: JavaBoolean): Boolean = bool != null && bool.booleanValue

  /**
    * <p>Converts a Boolean to a boolean handling {@code null}.</p>
    *
    * <pre>
    * BooleanUtils.toBooleanDefaultIfNull(Boolean.TRUE, false)  = true
    * BooleanUtils.toBooleanDefaultIfNull(Boolean.TRUE, true)   = true
    * BooleanUtils.toBooleanDefaultIfNull(Boolean.FALSE, true)  = false
    * BooleanUtils.toBooleanDefaultIfNull(Boolean.FALSE, false) = false
    * BooleanUtils.toBooleanDefaultIfNull(null, true)           = true
    * BooleanUtils.toBooleanDefaultIfNull(null, false)          = false
    * </pre>
    *
    * @param bool        the boolean object to convert to primitive
    * @param valueIfNull the boolean value to return if the parameter {@code bool} is {@code null}
    * @return {@code true} or {@code false}
    */
  def toBooleanDefaultIfNull(bool: JavaBoolean, valueIfNull: JavaBoolean): JavaBoolean = {
    if (bool == null) return valueIfNull
    bool.booleanValue
  }

  /**
    * <p>Converts an int to a boolean using the convention that {@code zero}
    * is {@code false}, everything else is {@code true}.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean(0) = false
    * BooleanUtils.toBoolean(1) = true
    * BooleanUtils.toBoolean(2) = true
    * </pre>
    *
    * @param value the int to convert
    * @return {@code true} if non-zero, {@code false}
    *         if zero
    */
  def toBoolean(value: Int): Boolean = value != 0

  /**
    * <p>Converts an int to a Boolean using the convention that {@code zero}
    * is {@code false}, everything else is {@code true}.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean(0) = Boolean.FALSE
    * BooleanUtils.toBoolean(1) = Boolean.TRUE
    * BooleanUtils.toBoolean(2) = Boolean.TRUE
    * </pre>
    *
    * @param value the int to convert
    * @return Boolean.TRUE if non-zero, Boolean.FALSE if zero,
    *         {@code null} if {@code null}
    */
  def toBooleanObject(value: Int): JavaBoolean =
    if (value == 0) JavaBoolean.FALSE
    else JavaBoolean.TRUE

  /**
    * <p>Converts an Integer to a Boolean using the convention that {@code zero}
    * is {@code false}, every other numeric value is {@code true}.</p>
    *
    * <p>{@code null} will be converted to {@code null}.</p>
    *
    * <p>NOTE: This method may return {@code null} and may throw a {@code NullPointerException}
    * if unboxed to a {@code boolean}.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean(Integer.valueOf(0))    = Boolean.FALSE
    * BooleanUtils.toBoolean(Integer.valueOf(1))    = Boolean.TRUE
    * BooleanUtils.toBoolean(Integer.valueOf(null)) = null
    * </pre>
    *
    * @param value the Integer to convert
    * @return Boolean.TRUE if non-zero, Boolean.FALSE if zero,
    *         {@code null} if {@code null} input
    */
  def toBooleanObject(value: Integer): JavaBoolean = {
    if (value == null) return null
    if (value.intValue == 0) JavaBoolean.FALSE
    else JavaBoolean.TRUE
  }

  /**
    * <p>Converts an int to a boolean specifying the conversion values.</p>
    *
    * <p>If the {@code trueValue} and {@code falseValue} are the same number then
    * the return value will be {@code true} in case {@code value} matches it.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean(0, 1, 0) = false
    * BooleanUtils.toBoolean(1, 1, 0) = true
    * BooleanUtils.toBoolean(1, 1, 1) = true
    * BooleanUtils.toBoolean(2, 1, 2) = false
    * BooleanUtils.toBoolean(2, 2, 0) = true
    * </pre>
    *
    * @param value      the {@code Integer} to convert
    * @param trueValue  the value to match for {@code true}
    * @param falseValue the value to match for {@code false}
    * @return {@code true} or {@code false}
    * @throws java.lang.IllegalArgumentException if {@code value} does not match neither
    *                                            {@code trueValue} no {@code falseValue}
    */
  def toBoolean(value: Int, trueValue: Int, falseValue: Int): JavaBoolean = {
    if (value == trueValue) return true
    if (value == falseValue) return false
    throw new IllegalArgumentException("The Integer did not match either specified value")
  }

  /**
    * <p>Converts an Integer to a boolean specifying the conversion values.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0)) = false
    * BooleanUtils.toBoolean(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(0)) = true
    * BooleanUtils.toBoolean(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(2)) = false
    * BooleanUtils.toBoolean(Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(0)) = true
    * BooleanUtils.toBoolean(null, null, Integer.valueOf(0))                     = true
    * </pre>
    *
    * @param value      the Integer to convert
    * @param trueValue  the value to match for {@code true}, may be {@code null}
    * @param falseValue the value to match for {@code false}, may be {@code null}
    * @return {@code true} or {@code false}
    * @throws java.lang.IllegalArgumentException if no match
    */
  def toBoolean(value: Integer, trueValue: Integer, falseValue: Integer): JavaBoolean = {
    if (value == null) {
      if (trueValue == null) return true
      if (falseValue == null) return false
    } else if (value == trueValue) return true
    else if (value == falseValue) return false
    throw new IllegalArgumentException("The Integer did not match either specified value")
  }

  /**
    * <p>Converts an int to a Boolean specifying the conversion values.</p>
    *
    * <p>NOTE: This method may return {@code null} and may throw a {@code NullPointerException}
    * if unboxed to a {@code boolean}.</p>
    *
    * <p>The checks are done first for the {@code trueValue}, then for the {@code falseValue} and
    * finally for the {@code nullValue}.</p>
    *
    * <pre>
    * BooleanUtils.toBooleanObject(0, 0, 2, 3) = Boolean.TRUE
    * BooleanUtils.toBooleanObject(0, 0, 0, 3) = Boolean.TRUE
    * BooleanUtils.toBooleanObject(0, 0, 0, 0) = Boolean.TRUE
    * BooleanUtils.toBooleanObject(2, 1, 2, 3) = Boolean.FALSE
    * BooleanUtils.toBooleanObject(2, 1, 2, 2) = Boolean.FALSE
    * BooleanUtils.toBooleanObject(3, 1, 2, 3) = null
    * </pre>
    *
    * @param value      the Integer to convert
    * @param trueValue  the value to match for {@code true}
    * @param falseValue the value to match for {@code false}
    * @param nullValue  the value to to match for {@code null}
    * @return Boolean.TRUE, Boolean.FALSE, or {@code null}
    * @throws java.lang.IllegalArgumentException if no match
    */
  def toBooleanObject(value: Int, trueValue: Int, falseValue: Int, nullValue: Int): JavaBoolean = {
    if (value == trueValue) return JavaBoolean.TRUE
    if (value == falseValue) return JavaBoolean.FALSE
    if (value == nullValue) return null
    throw new IllegalArgumentException("The Integer did not match any specified value")
  }

  /**
    * <p>Converts an Integer to a Boolean specifying the conversion values.</p>
    *
    * <p>NOTE: This method may return {@code null} and may throw a {@code NullPointerException}
    * if unboxed to a {@code boolean}.</p>
    *
    * <p>The checks are done first for the {@code trueValue}, then for the {@code falseValue} and
    * finally for the {@code nullValue}.</p>
    * *
    * <pre>
    * BooleanUtils.toBooleanObject(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(3)) = JavaBoolean.TRUE
    * BooleanUtils.toBooleanObject(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(3)) = JavaBoolean.TRUE
    * BooleanUtils.toBooleanObject(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)) = JavaBoolean.TRUE
    * BooleanUtils.toBooleanObject(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)) = JavaBoolean.FALSE
    * BooleanUtils.toBooleanObject(Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(2)) = JavaBoolean.FALSE
    * BooleanUtils.toBooleanObject(Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)) = null
    * </pre>
    *
    * @param value      the Integer to convert
    * @param trueValue  the value to match for {@code true}, may be {@code null}
    * @param falseValue the value to match for {@code false}, may be {@code null}
    * @param nullValue  the value to to match for {@code null}, may be {@code null}
    * @return Boolean.TRUE, Boolean.FALSE, or {@code null}
    * @throws java.lang.IllegalArgumentException if no match
    */
  def toBooleanObject(value: Integer, trueValue: Integer, falseValue: Integer, nullValue: Integer): JavaBoolean = {
    if (value == null) {
      if (trueValue == null) return JavaBoolean.TRUE
      if (falseValue == null) return JavaBoolean.FALSE
      if (nullValue == null) return null
    } else if (value == trueValue) return JavaBoolean.TRUE
    else if (value == falseValue) return JavaBoolean.FALSE
    else if (value == nullValue) return null
    throw new IllegalArgumentException("The Integer did not match any specified value")
  }

  /**
    * <p>Converts a boolean to an int using the convention that
    * {@code true} is {@code 1} and {@code false} is {@code 0}.</p>
    *
    * <pre>
    * BooleanUtils.toInteger(true)  = 1
    * BooleanUtils.toInteger(false) = 0
    * </pre>
    *
    * @param bool the boolean to convert
    * @return one if {@code true}, zero if {@code false}
    */
  def toInteger(bool: JavaBoolean): Int =
    if (bool) 1
    else 0

  /**
    * <p>Converts a boolean to an Integer using the convention that
    * {@code true} is {@code 1} and {@code false} is {@code 0}.</p>
    *
    * <pre>
    * BooleanUtils.toIntegerObject(true)  = Integer.valueOf(1)
    * BooleanUtils.toIntegerObject(false) = Integer.valueOf(0)
    * </pre>
    *
    * @param bool the boolean to convert
    * @return one if {@code true}, zero if {@code false}
    */
  def toIntegerObject(bool: Boolean): Integer =
    if (bool) NumberUtils.INTEGER_ONE
    else NumberUtils.INTEGER_ZERO

  /**
    * <p>Converts a JavaBoolean to a Integer using the convention that
    * {@code zero} is {@code false}.</p>
    *
    * <p>{@code null} will be converted to {@code null}.</p>
    *
    * <pre>
    * BooleanUtils.toIntegerObject(JavaBoolean.TRUE)  = Integer.valueOf(1)
    * BooleanUtils.toIntegerObject(JavaBoolean.FALSE) = Integer.valueOf(0)
    * </pre>
    *
    * @param bool the JavaBoolean to convert
    * @return one if JavaBoolean.TRUE, zero if JavaBoolean.FALSE, {@code null} if {@code null}
    */
  def toIntegerObject(bool: JavaBoolean): Integer = {
    if (bool == null) return null
    if (bool.booleanValue) NumberUtils.INTEGER_ONE
    else NumberUtils.INTEGER_ZERO
  }

  /**
    * <p>Converts a boolean to an int specifying the conversion values.</p>
    *
    * <pre>
    * BooleanUtils.toInteger(true, 1, 0)  = 1
    * BooleanUtils.toInteger(false, 1, 0) = 0
    * </pre>
    *
    * @param bool       the to convert
    * @param trueValue  the value to return if {@code true}
    * @param falseValue the value to return if {@code false}
    * @return the appropriate value
    */
  def toInteger(bool: JavaBoolean, trueValue: Int, falseValue: Int): Int =
    if (bool) trueValue
    else falseValue

  /**
    * <p>Converts a JavaBoolean to an int specifying the conversion values.</p>
    *
    * <pre>
    * BooleanUtils.toInteger(JavaBoolean.TRUE, 1, 0, 2)  = 1
    * BooleanUtils.toInteger(JavaBoolean.FALSE, 1, 0, 2) = 0
    * BooleanUtils.toInteger(null, 1, 0, 2)          = 2
    * </pre>
    *
    * @param bool       the Boolean to convert
    * @param trueValue  the value to return if {@code true}
    * @param falseValue the value to return if {@code false}
    * @param nullValue  the value to return if {@code null}
    * @return the appropriate value
    */
  def toInteger(bool: JavaBoolean, trueValue: Int, falseValue: Int, nullValue: Int): Int = {
    if (bool == null) return nullValue
    if (bool.booleanValue) trueValue
    else falseValue
  }

  /**
    * <p>Converts a boolean to an Integer specifying the conversion values.</p>
    *
    * <pre>
    * BooleanUtils.toIntegerObject(true, Integer.valueOf(1), Integer.valueOf(0))  = Integer.valueOf(1)
    * BooleanUtils.toIntegerObject(false, Integer.valueOf(1), Integer.valueOf(0)) = Integer.valueOf(0)
    * </pre>
    *
    * @param bool       the to convert
    * @param trueValue  the value to return if {@code true}, may be {@code null}
    * @param falseValue the value to return if {@code false}, may be {@code null}
    * @return the appropriate value
    */
  def toIntegerObject(bool: Boolean, trueValue: Integer, falseValue: Integer): Integer =
    if (bool) trueValue
    else falseValue

  /**
    * <p>Converts a JavaBoolean to an Integer specifying the conversion values.</p>
    *
    * <pre>
    * BooleanUtils.toIntegerObject(JavaBoolean.TRUE, Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(2))  = Integer.valueOf(1)
    * BooleanUtils.toIntegerObject(JavaBoolean.FALSE, Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(2)) = Integer.valueOf(0)
    * BooleanUtils.toIntegerObject(null, Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(2))          = Integer.valueOf(2)
    * </pre>
    *
    * @param bool       the Boolean to convert
    * @param trueValue  the value to return if {@code true}, may be {@code null}
    * @param falseValue the value to return if {@code false}, may be {@code null}
    * @param nullValue  the value to return if {@code null}, may be {@code null}
    * @return the appropriate value
    */
  def toIntegerObject(bool: JavaBoolean, trueValue: Integer, falseValue: Integer, nullValue: Integer): Integer = {
    if (bool == null) return nullValue
    if (bool.booleanValue) trueValue
    else falseValue
  }

  /**
    * <p>Converts a String to a Boolean.</p>
    *
    * <p>{@code 'true'}, {@code 'on'}, {@code 'y'}, {@code 't'}, {@code 'yes'}
    * or {@code '1'} (case insensitive) will return {@code true}.
    * {@code 'false'}, {@code 'off'}, {@code 'n'}, {@code 'f'}, {@code 'no'}
    * or {@code '0'} (case insensitive) will return {@code false}.
    * Otherwise, {@code null} is returned.</p>
    *
    * <p>NOTE: This method may return {@code null} and may throw a {@code NullPointerException}
    * if unboxed to a {@code boolean}.</p>
    *
    * <pre>
    * // N.B. case is not significant
    * BooleanUtils.toBooleanObject(null)    = null
    * BooleanUtils.toBooleanObject("true")  = Boolean.TRUE
    * BooleanUtils.toBooleanObject("T")     = Boolean.TRUE // i.e. T[RUE]
    * BooleanUtils.toBooleanObject("false") = Boolean.FALSE
    * BooleanUtils.toBooleanObject("f")     = Boolean.FALSE // i.e. f[alse]
    * BooleanUtils.toBooleanObject("No")    = Boolean.FALSE
    * BooleanUtils.toBooleanObject("n")     = Boolean.FALSE // i.e. n[o]
    * BooleanUtils.toBooleanObject("on")    = Boolean.TRUE
    * BooleanUtils.toBooleanObject("ON")    = Boolean.TRUE
    * BooleanUtils.toBooleanObject("off")   = Boolean.FALSE
    * BooleanUtils.toBooleanObject("oFf")   = Boolean.FALSE
    * BooleanUtils.toBooleanObject("yes")   = Boolean.TRUE
    * BooleanUtils.toBooleanObject("Y")     = Boolean.TRUE // i.e. Y[ES]
    * BooleanUtils.toBooleanObject("1")     = Boolean.TRUE
    * BooleanUtils.toBooleanObject("0")     = Boolean.FALSE
    * BooleanUtils.toBooleanObject("blue")  = null
    * BooleanUtils.toBooleanObject("true ") = null // trailing space (too long)
    * BooleanUtils.toBooleanObject("ono")   = null // does not match on or no
    * </pre>
    *
    * @param str the String to check; upper and lower case are treated as the same
    * @return the JavaBoolean value of the string, {@code null} if no match or {@code null} input
    */
  def toBooleanObject(str: String): JavaBoolean = { // Previously used equalsIgnoreCase, which was fast for interned 'true'.
    // Non interned 'true' matched 15 times slower.
    //
    // Optimisation provides same performance as before for interned 'true'.
    // Similar performance for null, 'false', and other strings not length 2/3/4.
    // 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower.
    if (str eq "true") return JavaBoolean.TRUE
    if (str == null) return null
    str.length match {
      case 1 =>
        val ch0 = str.charAt(0)
        if (ch0 == 'y' || ch0 == 'Y' || ch0 == 't' || ch0 == 'T' || ch0 == '1') return JavaBoolean.TRUE
        if (ch0 == 'n' || ch0 == 'N' || ch0 == 'f' || ch0 == 'F' || ch0 == '0') return JavaBoolean.FALSE

      case 2 =>
        val ch0 = str.charAt(0)
        val ch1 = str.charAt(1)
        if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N')) return JavaBoolean.TRUE
        if ((ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O')) return JavaBoolean.FALSE

      case 3 =>
        val ch0 = str.charAt(0)
        val ch1 = str.charAt(1)
        val ch2 = str.charAt(2)
        if ((ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S'))
          return JavaBoolean.TRUE
        if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F'))
          return JavaBoolean.FALSE

      case 4 =>
        val ch0 = str.charAt(0)
        val ch1 = str.charAt(1)
        val ch2 = str.charAt(2)
        val ch3 = str.charAt(3)
        if ((ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R') && (ch2 == 'u' || ch2 == 'U') && (ch3 == 'e' || ch3 == 'E'))
          return JavaBoolean.TRUE

      case 5 =>
        val ch0 = str.charAt(0)
        val ch1 = str.charAt(1)
        val ch2 = str.charAt(2)
        val ch3 = str.charAt(3)
        val ch4 = str.charAt(4)
        if ((ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A') && (ch2 == 'l' || ch2 == 'L') && (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E'))
          return JavaBoolean.FALSE

      case _ =>
    }
    null
  }

  /**
    * <p>Converts a String to a Boolean throwing an exception if no match.</p>
    *
    * <p>NOTE: This method may return {@code null} and may throw a {@code NullPointerException}
    * if unboxed to a {@code boolean}.</p>
    *
    * <pre>
    * BooleanUtils.toBooleanObject("true", "true", "false", "null")   = Boolean.TRUE
    * BooleanUtils.toBooleanObject(null, null, "false", "null")       = Boolean.TRUE
    * BooleanUtils.toBooleanObject(null, null, null, "null")          = Boolean.TRUE
    * BooleanUtils.toBooleanObject(null, null, null, null)            = Boolean.TRUE
    * BooleanUtils.toBooleanObject("false", "true", "false", "null")  = Boolean.FALSE
    * BooleanUtils.toBooleanObject("false", "true", "false", "false") = Boolean.FALSE
    * BooleanUtils.toBooleanObject(null, "true", null, "false")       = Boolean.FALSE
    * BooleanUtils.toBooleanObject(null, "true", null, null)          = Boolean.FALSE
    * BooleanUtils.toBooleanObject("null", "true", "false", "null")   = null
    * </pre>
    *
    * @param str         the String to check
    * @param trueString  the String to match for {@code true} (case sensitive), may be {@code null}
    * @param falseString the String to match for {@code false} (case sensitive), may be {@code null}
    * @param nullString  the String to match for {@code null} (case sensitive), may be {@code null}
    * @return the Boolean value of the string, {@code null} if either the String matches {@code nullString}
    *         or if {@code null} input and {@code nullString} is {@code null}
    * @throws java.lang.IllegalArgumentException if the String doesn't match
    */
  def toBooleanObject(str: String, trueString: String, falseString: String, nullString: String): JavaBoolean = {
    if (str == null) {
      if (trueString == null) return JavaBoolean.TRUE
      if (falseString == null) return JavaBoolean.FALSE
      if (nullString == null) return null
    } else if (str == trueString) return JavaBoolean.TRUE
    else if (str == falseString) return JavaBoolean.FALSE
    else if (str == nullString) return null
    // no match
    throw new IllegalArgumentException("The String did not match any specified value")
  }

  /**
    * <p>Converts a String to a boolean (optimised for performance).</p>
    *
    * <p>{@code 'true'}, {@code 'on'}, {@code 'y'}, {@code 't'} or {@code 'yes'}
    * (case insensitive) will return {@code true}. Otherwise,
    * {@code false} is returned.</p>
    *
    * <p>This method performs 4 times faster (JDK1.4) than
    * {@code Boolean.valueOf(String)}. However, this method accepts
    * 'on' and 'yes', 't', 'y' as true values.
    *
    * <pre>
    * BooleanUtils.toBoolean(null)    = false
    * BooleanUtils.toBoolean("true")  = true
    * BooleanUtils.toBoolean("TRUE")  = true
    * BooleanUtils.toBoolean("tRUe")  = true
    * BooleanUtils.toBoolean("on")    = true
    * BooleanUtils.toBoolean("yes")   = true
    * BooleanUtils.toBoolean("false") = false
    * BooleanUtils.toBoolean("x gti") = false
    * BooleanUtils.toBooleanObject("y") = true
    * BooleanUtils.toBooleanObject("n") = false
    * BooleanUtils.toBooleanObject("t") = true
    * BooleanUtils.toBooleanObject("f") = false
    * </pre>
    *
    * @param str the String to check
    * @return the boolean value of the string, {@code false} if no match or the String is null
    */
  def toBoolean(str: String): JavaBoolean = toBooleanObject(str) eq JavaBoolean.TRUE

  /**
    * <p>Converts a String to a JavaBoolean throwing an exception if no match found.</p>
    *
    * <pre>
    * BooleanUtils.toBoolean("true", "true", "false")  = true
    * BooleanUtils.toBoolean("false", "true", "false") = false
    * </pre>
    *
    * @param str         the String to check
    * @param trueString  the String to match for {@code true} (case sensitive), may be {@code null}
    * @param falseString the String to match for {@code false} (case sensitive), may be {@code null}
    * @return the boolean value of the string
    * @throws java.lang.IllegalArgumentException if the String doesn't match
    */
  def toBoolean(str: String, trueString: String, falseString: String): JavaBoolean = {
    if (str eq trueString) return true
    else if (str eq falseString) return false
    else if (str != null)
      if (str == trueString) return true
      else if (str == falseString) return false
    throw new IllegalArgumentException("The String did not match either specified value")
  }

  /**
    * <p>Converts a Boolean to a String returning {@code 'true'},
    * {@code 'false'}, or {@code null}.</p>
    *
    * <pre>
    * BooleanUtils.toStringTrueFalse(Boolean.TRUE)  = "true"
    * BooleanUtils.toStringTrueFalse(Boolean.FALSE) = "false"
    * BooleanUtils.toStringTrueFalse(null)          = null;
    * </pre>
    *
    * @param bool the JavaBoolean to check
    * @return {@code 'true'}, {@code 'false'}, or {@code null}
    */
  def toStringTrueFalse(bool: JavaBoolean): String = toString(bool, "true", "false", null)

  /**
    * <p>Converts a Boolean to a String returning {@code 'on'},
    * {@code 'off'}, or {@code null}.</p>
    *
    * <pre>
    * BooleanUtils.toStringOnOff(Boolean.TRUE)  = "on"
    * BooleanUtils.toStringOnOff(Boolean.FALSE) = "off"
    * BooleanUtils.toStringOnOff(null)          = null;
    * </pre>
    *
    * @param bool the Boolean to check
    * @return {@code 'on'}, {@code 'off'}, or {@code null}
    */
  def toStringOnOff(bool: JavaBoolean): String = toString(bool, "on", "off", null)

  /**
    * <p>Converts a Boolean to a String returning {@code 'yes'},
    * {@code 'no'}, or {@code null}.</p>
    *
    * <pre>
    * BooleanUtils.toStringYesNo(Boolean.TRUE)  = "yes"
    * BooleanUtils.toStringYesNo(Boolean.FALSE) = "no"
    * BooleanUtils.toStringYesNo(null)          = null;
    * </pre>
    *
    * @param bool the Boolean to check
    * @return {@code 'yes'}, {@code 'no'}, or {@code null}
    */
  def toStringYesNo(bool: JavaBoolean): String = toString(bool, "yes", "no", null)

  /**
    * <p>Converts a Boolean to a String returning one of the input Strings.</p>
    *
    * <pre>
    * BooleanUtils.toString(Boolean.TRUE, "true", "false", null)   = "true"
    * BooleanUtils.toString(Boolean.FALSE, "true", "false", null)  = "false"
    * BooleanUtils.toString(null, "true", "false", null)           = null;
    * </pre>
    *
    * @param bool        the Boolean to check
    * @param trueString  the String to return if {@code true}, may be {@code null}
    * @param falseString the String to return if {@code false}, may be {@code null}
    * @param nullString  the String to return if {@code null}, may be {@code null}
    * @return one of the three input Strings
    */
  def toString(bool: JavaBoolean, trueString: String, falseString: String, nullString: String): String = {
    if (bool == null) return nullString
    if (bool.booleanValue) trueString
    else falseString
  }

  /**
    * <p>Converts a boolean to a String returning {@code 'true'}
    * or {@code 'false'}.</p>
    *
    * <pre>
    * BooleanUtils.toStringTrueFalse(true)   = "true"
    * BooleanUtils.toStringTrueFalse(false)  = "false"
    * </pre>
    *
    * @param bool the Boolean to check
    * @return {@code 'true'}, {@code 'false'}, or {@code null}
    */
  def toStringTrueFalse(bool: Boolean): String = toString(bool, "true", "false")

  /**
    * <p>Converts a boolean to a String returning {@code 'on'}
    * or {@code 'off'}.</p>
    *
    * <pre>
    * BooleanUtils.toStringOnOff(true)   = "on"
    * BooleanUtils.toStringOnOff(false)  = "off"
    * </pre>
    *
    * @param bool the Boolean to check
    * @return {@code 'on'}, {@code 'off'}, or {@code null}
    */
  def toStringOnOff(bool: Boolean): String = toString(bool, "on", "off")

  /**
    * <p>Converts a boolean to a String returning {@code 'yes'}
    * or {@code 'no'}.</p>
    *
    * <pre>
    * BooleanUtils.toStringYesNo(true)   = "yes"
    * BooleanUtils.toStringYesNo(false)  = "no"
    * </pre>
    *
    * @param bool the Boolean to check
    * @return {@code 'yes'}, {@code 'no'}, or {@code null}
    */
  def toStringYesNo(bool: Boolean): String = toString(bool, "yes", "no")

  /**
    * <p>Converts a boolean to a String returning one of the input Strings.</p>
    *
    * <pre>
    * BooleanUtils.toString(true, "true", "false")   = "true"
    * BooleanUtils.toString(false, "true", "false")  = "false"
    * </pre>
    *
    * @param bool        the Boolean to check
    * @param trueString  the String to return if {@code true}, may be {@code null}
    * @param falseString the String to return if {@code false}, may be {@code null}
    * @return one of the two input Strings
    */
  def toString(bool: JavaBoolean, trueString: String, falseString: String): String =
    if (bool) trueString
    else falseString

  /**
    * <p>Performs an 'and' operation on a set of booleans.</p>
    *
    * <pre>
    * BooleanUtils.and(true, true)         = true
    * BooleanUtils.and(false, false)       = false
    * BooleanUtils.and(true, false)        = false
    * BooleanUtils.and(true, true, false)  = false
    * BooleanUtils.and(true, true, true)   = true
    * </pre>
    *
    * @param array an array of {@code boolean}s
    * @return the result of the logical 'and' operation. That is {@code false}
    *         if any of the parameters is {@code false} and {@code true} otherwise.
    * @throws java.lang.IllegalArgumentException if {@code array} is {@code null}
    *                                            if {@code array} is empty.
    * @since 3.0.1
    */
  def and(array: Boolean*): Boolean = and(array.toArray)

  def and(array: Array[Boolean]): Boolean = {
    // Validates input
    if (array == null) throw new IllegalArgumentException("The Array must not be null")
    if (array.length == 0) throw new IllegalArgumentException("Array is empty")

    for (element <- array) {
      if (!element) return false
    }

    true
  }

  /**
    * <p>Performs an 'and' operation on an array of Booleans.</p>
    *
    * <pre>
    * BooleanUtils.and(Boolean.TRUE, Boolean.TRUE)                 = Boolean.TRUE
    * BooleanUtils.and(Boolean.FALSE, Boolean.FALSE)               = Boolean.FALSE
    * BooleanUtils.and(Boolean.TRUE, Boolean.FALSE)                = Boolean.FALSE
    * BooleanUtils.and(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)   = Boolean.TRUE
    * BooleanUtils.and(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE) = Boolean.FALSE
    * BooleanUtils.and(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)  = Boolean.FALSE
    * </pre>
    *
    * @param array an array of {@code Boolean}s
    * @return the result of the logical 'and' operation. That is {@code false}
    *         if any of the parameters is {@code false} and {@code true} otherwise.
    * @throws java.lang.IllegalArgumentException if {@code array} is {@code null}
    *                                            if {@code array} is empty.
    *                                            if {@code array} contains a {@code null}
    * @since 3.0.1
    */
  def and(array: JavaBoolean*): JavaBoolean = and(array.toArray)

  def and(array: Array[JavaBoolean]): JavaBoolean = {
    if (array == null) throw new IllegalArgumentException("The Array must not be null")
    if (array.length == 0) throw new IllegalArgumentException("Array is empty")

    try {
      val primitive = ArrayUtils.toPrimitive(array.toArray)
      if (and(primitive: _*)) JavaBoolean.TRUE
      else JavaBoolean.FALSE
    } catch {
      case _: NullPointerException =>
        throw new IllegalArgumentException("The array must not contain any null elements")
    }
  }

  /**
    * <p>Performs an 'or' operation on a set of booleans.</p>
    *
    * <pre>
    * BooleanUtils.or(true, true)          = true
    * BooleanUtils.or(false, false)        = false
    * BooleanUtils.or(true, false)         = true
    * BooleanUtils.or(true, true, false)   = true
    * BooleanUtils.or(true, true, true)    = true
    * BooleanUtils.or(false, false, false) = false
    * </pre>
    *
    * @param array an array of {@code boolean}s
    * @return {@code true} if any of the arguments is {@code true}, and it returns {@code false} otherwise.
    * @throws java.lang.IllegalArgumentException if {@code array} is {@code null}
    *                                            if {@code array} is empty.
    * @since 3.0.1
    */
  def or(array: Boolean*): Boolean = or(array.toArray)

  def or(array: Array[Boolean]): Boolean = {
    if (array == null) throw new IllegalArgumentException("The Array must not be null")
    if (array.length == 0) throw new IllegalArgumentException("Array is empty")

    for (element <- array) {
      if (element) return true
    }

    false
  }

  /**
    * <p>Performs an 'or' operation on an array of JavaBooleans.</p>
    *
    * <pre>
    * BooleanUtils.or(Boolean.TRUE, Boolean.TRUE)                  = Boolean.TRUE
    * BooleanUtils.or(Boolean.FALSE, Boolean.FALSE)                = Boolean.FALSE
    * BooleanUtils.or(Boolean.TRUE, Boolean.FALSE)                 = Boolean.TRUE
    * BooleanUtils.or(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)    = Boolean.TRUE
    * BooleanUtils.or(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE)  = Boolean.TRUE
    * BooleanUtils.or(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)   = Boolean.TRUE
    * BooleanUtils.or(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE) = Boolean.FALSE
    * </pre>
    *
    * @param array an array of {@code Boolean}s
    * @return {@code true} if any of the arguments is {@code true}, and it returns {@code false} otherwise.
    * @throws java.lang.IllegalArgumentException if {@code array} is {@code null}
    *                                            if {@code array} is empty.
    *                                            if {@code array} contains a {@code null}
    * @since 3.0.1
    */
  def or(array: JavaBoolean*): JavaBoolean = or(array.toArray)

  def or(array: Array[JavaBoolean]): JavaBoolean = {
    if (array == null) throw new IllegalArgumentException("The Array must not be null")
    if (array.length == 0) throw new IllegalArgumentException("Array is empty")

    try {
      val primitive = ArrayUtils.toPrimitive(array.toArray)
      if (or(primitive: _*)) JavaBoolean.TRUE
      else JavaBoolean.FALSE
    } catch {
      case _: NullPointerException =>
        throw new IllegalArgumentException("The array must not contain any null elements")
    }
  }

  /**
    * <p>Performs an xor on a set of booleans.</p>
    *
    * <pre>
    * BooleanUtils.xor(true, true)   = false
    * BooleanUtils.xor(false, false) = false
    * BooleanUtils.xor(true, false)  = true
    * </pre>
    *
    * @param array an array of {@code boolean}s
    * @return the result of the xor operations
    * @throws java.lang.IllegalArgumentException if {@code array} is {@code null}
    *                                            if {@code array} is empty.
    */
  def xor(array: Boolean*): Boolean = xor(array.toArray)

  def xor(array: Array[Boolean]): Boolean = {
    if (array == null) throw new IllegalArgumentException("The Array must not be null")
    if (array.length == 0) throw new IllegalArgumentException("Array is empty")

    // false if the neutral element of the xor operator
    var result = false
    for (element <- array) {
      result ^= element
    }

    result
  }

  /**
    * <p>Performs an xor on an array of JavaBooleans.</p>
    *
    * <pre>
    * BooleanUtils.xor(new Boolean[] { Boolean.TRUE, Boolean.TRUE })   = Boolean.FALSE
    * BooleanUtils.xor(new Boolean[] { Boolean.FALSE, Boolean.FALSE }) = Boolean.FALSE
    * BooleanUtils.xor(new Boolean[] { Boolean.TRUE, Boolean.FALSE })  = Boolean.TRUE
    * BooleanUtils.xor(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE)     = Boolean.TRUE
    * </pre>
    *
    * @param array an array of {@code Boolean}s
    * @return the result of the xor operations
    * @throws java.lang.IllegalArgumentException if {@code array} is {@code null}
    *                                            if {@code array} is empty.
    *                                            if {@code array} contains a {@code null}
    */
  def xor(array: JavaBoolean*): JavaBoolean = xor(array.toArray)

  def xor(array: Array[JavaBoolean]): JavaBoolean = {
    if (array == null) throw new IllegalArgumentException("The Array must not be null")
    if (array.length == 0) throw new IllegalArgumentException("Array is empty")

    try {
      val primitive = ArrayUtils.toPrimitive(array.toArray)
      if (xor(primitive: _*)) JavaBoolean.TRUE
      else JavaBoolean.FALSE
    } catch {
      case _: NullPointerException =>
        throw new IllegalArgumentException("The array must not contain any null elements")
    }
  }

  /**
    * <p>Compares two {@code boolean} values. This is the same functionality as provided in Java 7.</p>
    *
    * @param x the first {@code boolean} to compare
    * @param y the second {@code boolean} to compare
    * @return the value {@code 0} if {@code x == y};
    *         a value less than {@code 0} if {@code !x && y}; and
    *         a value greater than {@code 0} if {@code x && !y}
    * @since 3.4
    */
  def compare(x: JavaBoolean, y: JavaBoolean): Int = {
    if (x == y) return 0
    if (x) 1
    else -1
  }
}
