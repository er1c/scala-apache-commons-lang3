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

import java.lang.{Double => JavaDouble, Long => JavaLong}
import java.math.BigInteger
import org.apache.commons.lang3.Validate

@SerialVersionUID(65382027393090L)
object Fraction {
  /**
    * {@code Fraction} representation of 0.
    */
  val ZERO = new Fraction(0, 1)
  /**
    * {@code Fraction} representation of 1.
    */
  val ONE = new Fraction(1, 1)
  /**
    * {@code Fraction} representation of 1/2.
    */
  val ONE_HALF = new Fraction(1, 2)
  /**
    * {@code Fraction} representation of 1/3.
    */
  val ONE_THIRD = new Fraction(1, 3)
  /**
    * {@code Fraction} representation of 2/3.
    */
  val TWO_THIRDS = new Fraction(2, 3)
  /**
    * {@code Fraction} representation of 1/4.
    */
  val ONE_QUARTER = new Fraction(1, 4)
  /**
    * {@code Fraction} representation of 2/4.
    */
  val TWO_QUARTERS = new Fraction(2, 4)
  /**
    * {@code Fraction} representation of 3/4.
    */
  val THREE_QUARTERS = new Fraction(3, 4)
  /**
    * {@code Fraction} representation of 1/5.
    */
  val ONE_FIFTH = new Fraction(1, 5)
  /**
    * {@code Fraction} representation of 2/5.
    */
  val TWO_FIFTHS = new Fraction(2, 5)
  /**
    * {@code Fraction} representation of 3/5.
    */
  val THREE_FIFTHS = new Fraction(3, 5)
  /**
    * {@code Fraction} representation of 4/5.
    */
  val FOUR_FIFTHS = new Fraction(4, 5)

  /**
    * <p>Creates a {@code Fraction} instance with the 2 parts
    * of a fraction Y/Z.</p>
    *
    * <p>Any negative signs are resolved to be on the numerator.</p>
    *
    * @param numerator   the numerator, for example the three in 'three sevenths'
    * @param denominator the denominator, for example the seven in 'three sevenths'
    * @return a new fraction instance
    * @throws java.lang.ArithmeticException if the denominator is {@code zero}
    *                             or the denominator is {@code negative} and the numerator is {@code Integer#MIN_VALUE}
    */
  def getFraction(numerator: Int, denominator: Int): Fraction = {
    if (denominator == 0) throw new ArithmeticException("The denominator must not be zero")

    if (denominator < 0) {
      if (numerator == Int.MinValue || denominator == Int.MinValue)
        throw new ArithmeticException("overflow: can't negate")
      new Fraction(-numerator, -denominator)
    } else {
      new Fraction(numerator, denominator)
    }
  }

  /**
    * <p>Creates a {@code Fraction} instance with the 3 parts
    * of a fraction X Y/Z.</p>
    *
    * <p>The negative sign must be passed in on the whole number part.</p>
    *
    * @param whole       the whole number, for example the one in 'one and three sevenths'
    * @param numerator   the numerator, for example the three in 'one and three sevenths'
    * @param denominator the denominator, for example the seven in 'one and three sevenths'
    * @return a new fraction instance
    * @throws java.lang.ArithmeticException if the denominator is {@code zero}
    *                                       if the denominator is negative
    *                                       if the numerator is negative
    *                                       if the resulting numerator exceeds {@code Integer.MAX_VALUE}
    */
  def getFraction(whole: Int, numerator: Int, denominator: Int): Fraction = {
    if (denominator == 0) throw new ArithmeticException("The denominator must not be zero")
    if (denominator < 0) throw new ArithmeticException("The denominator must not be negative")
    if (numerator < 0) throw new ArithmeticException("The numerator must not be negative")

    var numeratorValue = 0L
    if (whole < 0) numeratorValue = whole * denominator.toLong - numerator
    else numeratorValue = whole * denominator.toLong + numerator

    if (numeratorValue < Int.MinValue || numeratorValue > Int.MaxValue)
      throw new ArithmeticException("Numerator too large to represent as an Integer.")

    new Fraction(numeratorValue.toInt, denominator)
  }

  /**
    * <p>Creates a reduced {@code Fraction} instance with the 2 parts
    * of a fraction Y/Z.</p>
    *
    * <p>For example, if the input parameters represent 2/4, then the created
    * fraction will be 1/2.</p>
    *
    * <p>Any negative signs are resolved to be on the numerator.</p>
    *
    * @param numerator   the numerator, for example the three in 'three sevenths'
    * @param denominator the denominator, for example the seven in 'three sevenths'
    * @return a new fraction instance, with the numerator and denominator reduced
    * @throws java.lang.ArithmeticException if the denominator is {@code zero}
    */
  def getReducedFraction(numerator: Int, denominator: Int): Fraction = {
    if (denominator == 0) throw new ArithmeticException("The denominator must not be zero")
    if (numerator == 0) return ZERO // normalize zero.

    var _numerator: Int = numerator
    var _denominator: Int = denominator

    // allow 2^k/-2^31 as a valid fraction (where k>0)
    if (_denominator == Int.MinValue && (_numerator & 1) == 0) {
      _numerator /= 2
      _denominator /= 2
    }
    if (_denominator < 0) {
      if (_numerator == Int.MinValue || _denominator == Int.MinValue)
        throw new ArithmeticException("overflow: can't negate")

      _numerator = -_numerator
      _denominator = -_denominator
    }
    // simplify fraction.
    val gcd = greatestCommonDivisor(_numerator, _denominator)
    _numerator /= gcd
    _denominator /= gcd

    new Fraction(_numerator, _denominator)
  }

  /**
    * <p>Creates a {@code Fraction} instance from a {@code double} value.</p>
    *
    * <p>This method uses the <a href="http://archives.math.utk.edu/articles/atuyl/confrac/">
    * continued fraction algorithm</a>, computing a maximum of
    * 25 convergents and bounding the denominator by 10,000.</p>
    *
    * @param value the double value to convert
    * @return a new fraction instance that is close to the value
    * @throws java.lang.ArithmeticException if {@code |value| &gt; Integer.MAX_VALUE} or {@code value = NaN}
    *                                       if the calculated denominator is {@code zero}
    *                                       if the algorithm does not converge
    */
  def getFraction(value: Double): Fraction = {
    var _value: Double = value
    val sign =
      if (_value < 0) -1
      else 1

    _value = Math.abs(_value)
    if (_value > Int.MaxValue || JavaDouble.isNaN(_value))
      throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN")

    val wholeNumber = _value.toInt
    _value -= wholeNumber

    var numer0: Int = 0 // the pre-previous
    var denom0: Int = 1
    var numer1: Int = 1 // the previous
    var denom1: Int = 0
    var numer2: Int = 0 // the current, setup in calculation
    var denom2: Int = 0
    var a1: Int = _value.toInt
    var a2: Int = 0
    var x1: Double = 1d
    var x2: Double = 0d
    var y1: Double = _value - a1
    var y2: Double = 0d
    var delta1: Double = .0d
    var delta2: Double = Double.MaxValue
    var fraction: Double = .0d
    var i = 1

    do {
      delta1 = delta2
      a2 = (x1 / y1).toInt
      x2 = y1
      y2 = x1 - a2 * y1
      numer2 = a1 * numer1 + numer0
      denom2 = a1 * denom1 + denom0
      fraction = numer2.toDouble / denom2.toDouble
      delta2 = Math.abs(_value - fraction)
      a1 = a2
      x1 = x2
      y1 = y2
      numer0 = numer1
      denom0 = denom1
      numer1 = numer2
      denom1 = denom2
      i += 1
    } while (delta1 > delta2 && denom2 <= 10000 && denom2 > 0 && i < 25)

    if (i == 25) throw new ArithmeticException("Unable to convert double to fraction")
    getReducedFraction((numer0 + wholeNumber * denom0) * sign, denom0)
  }

  /**
    * <p>Creates a Fraction from a {@code String}.</p>
    *
    * <p>The formats accepted are:</p>
    *
    * <ol>
    * <li>{@code double} String containing a dot</li>
    * <li>'X Y/Z'</li>
    * <li>'Y/Z'</li>
    * <li>'X' (a simple whole number)</li>
    * </ol>
    * <p>and a .</p>
    *
    * @param str the string to parse, must not be {@code null}
    * @return the new {@code Fraction} instance
    * @throws java.lang.NullPointerException  if the string is {@code null}
    * @throws java.lang.NumberFormatException if the number format is invalid
    */
  def getFraction(str: String): Fraction = {
    Validate.notNull(str, "The string must not be null")

    var _str = str

    // parse double format
    var pos = _str.indexOf('.')
    if (pos >= 0) return getFraction(_str.toDouble)
    // parse X Y/Z format
    pos = _str.indexOf(' ')
    if (pos > 0) {
      val whole = _str.substring(0, pos).toInt
      _str = _str.substring(pos + 1)
      pos = _str.indexOf('/')
      if (pos < 0) throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z")
      val numer = _str.substring(0, pos).toInt
      val denom = _str.substring(pos + 1).toInt
      return getFraction(whole, numer, denom)
    }
    // parse Y/Z format
    pos = _str.indexOf('/')
    if (pos < 0) { // simple whole number
      return getFraction(_str.toInt, 1)
    }

    val numer = _str.substring(0, pos).toInt
    val denom = _str.substring(pos + 1).toInt
    getFraction(numer, denom)
  }

  /**
    * <p>Gets the greatest common divisor of the absolute value of
    * two numbers, using the "binary gcd" method which avoids
    * division and modulo operations.  See Knuth 4.5.2 algorithm B.
    * This algorithm is due to Josef Stein (1961).</p>
    *
    * @param u a non-zero number
    * @param v a non-zero number
    * @return the greatest common divisor, never zero
    */
  private def greatestCommonDivisor(u: Int, v: Int): Int = {

    // From Commons Math:
    if (u == 0 || v == 0) {
      if (u == Int.MinValue || v == Int.MinValue)
        throw new ArithmeticException("overflow: gcd is 2^31")
      return Math.abs(u) + Math.abs(v)
    }

    // if either operand is abs 1, return 1:
    if (Math.abs(u) == 1 || Math.abs(v) == 1) return 1

    var _u = u
    var _v = v
    // keep u and v negative, as negative integers range down to
    // -2^31, while positive numbers can only be as large as 2^31-1
    // (i.e. we can't necessarily negate a negative number without
    // overflow)

    if (_u > 0) _u = -_u // make u negative}
    if (_v > 0) _v = -_v // make v negative}

    // B1. [Find power of 2]
    var k = 0
    while ((_u & 1) == 0 && (_v & 1) == 0 && k < 31) {
      // while u and v are both even...
      _u /= 2
      _v /= 2
      k += 1 // cast out twos.
    }

    if (k == 31) throw new ArithmeticException("overflow: gcd is 2^31")

    // B2. Initialize: u and v have been divided by 2^k and at least
    // one is odd.
    var t =
      if ((_u & 1) == 1) _v
      else -(_u / 2) /* B3 */

    // t negative: u was odd, v may be even (t replaces v)
    // t positive: u was even, v is odd (t replaces u)
    do {
      /* assert u<0 && v<0; */
      // B4/B3: cast out twos from t.
      // while t is even..
      while ((t & 1) == 0) {
        t /= 2 // cast out twos
      }

      // B5 [reset max(u,v)]
      if (t > 0) _u = -t
      else _v = t
      // B6/B3. at this point both u and v should be odd.
      t = (_v - _u) / 2
      // |u| larger: t positive (replace u)
      // |v| larger: t negative (replace v)
    } while (t != 0)

    -_u * (1 << k) // gcd is u*2^k
  }

  /**
    * Multiply two integers, checking for overflow.
    *
    * @param x a factor
    * @param y a factor
    * @return the product {@code x*y}
    * @throws java.lang.ArithmeticException if the result can not be represented as
    *                             an int
    */
  private def mulAndCheck(x: Int, y: Int) = {
    val m = x.toLong * y.toLong
    if (m < Integer.MIN_VALUE || m > Integer.MAX_VALUE) throw new ArithmeticException("overflow: mul")
    m.toInt
  }

  /**
    * Multiply two non-negative integers, checking for overflow.
    *
    * @param x a non-negative factor
    * @param y a non-negative factor
    * @return the product {@code x*y}
    * @throws java.lang.ArithmeticException if the result can not be represented as
    *                             an int
    */
  private def mulPosAndCheck(x: Int, y: Int) = {
    /* assert x>=0 && y>=0; */
    val m = x.toLong * y.toLong
    if (m > Integer.MAX_VALUE) throw new ArithmeticException("overflow: mulPos")
    m.toInt
  }

  /**
    * Add two integers, checking for overflow.
    *
    * @param x an addend
    * @param y an addend
    * @return the sum {@code x+y}
    * @throws java.lang.ArithmeticException if the result can not be represented as
    *                             an int
    */
  private def addAndCheck(x: Int, y: Int) = {
    val s = x.toLong + y.toLong
    if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) throw new ArithmeticException("overflow: add")
    s.toInt
  }

  /**
    * Subtract two integers, checking for overflow.
    *
    * @param x the minuend
    * @param y the subtrahend
    * @return the difference {@code x-y}
    * @throws java.lang.ArithmeticException if the result can not be represented as
    *                             an int
    */
  private def subAndCheck(x: Int, y: Int) = {
    val s = x.toLong - y.toLong
    if (s < Integer.MIN_VALUE || s > Integer.MAX_VALUE) throw new ArithmeticException("overflow: add")
    s.toInt
  }
}

/**
  * <p>{@code Fraction} is a {@code Number} implementation that
  * stores fractions accurately.</p>
  *
  * <p>This class is immutable, and interoperable with most methods that accept
  * a {@code Number}.</p>
  *
  * <p>Note that this class is intended for common use cases, it is <i>int</i>
  * based and thus suffers from various overflow issues. For a BigInteger based
  * equivalent, please see the Commons Math BigFraction class. </p>
  *
  * @param numerator   the numerator, for example the three in 'three sevenths'
  * @param denominator the denominator, for example the seven in 'three sevenths'
  * @since 2.0
  */
@SerialVersionUID(65382027393090L)
final class Fraction private (private[Fraction] val numerator: Int, private[Fraction] val denominator: Int)
  extends Number with Comparable[Fraction] {
  /**
    * Cached output hashCode (class is immutable).
    */
  private var _hashCode: Int = 0
  /**
    * Cached output toString (class is immutable).
    */
  private var _toString: String = null
  /**
    * Cached output toProperString (class is immutable).
    */
  private var _toProperString: String = null

  /**
    * <p>Gets the numerator part of the fraction.</p>
    *
    * <p>This method may return a value greater than the denominator, an
    * improper fraction, such as the seven in 7/4.</p>
    *
    * @return the numerator fraction part
    */
  def getNumerator: Int = numerator

  /**
    * <p>Gets the denominator part of the fraction.</p>
    *
    * @return the denominator fraction part
    */
  def getDenominator: Int = denominator

  /**
    * <p>Gets the proper numerator, always positive.</p>
    *
    * <p>An improper fraction 7/4 can be resolved into a proper one, 1 3/4.
    * This method returns the 3 from the proper fraction.</p>
    *
    * <p>If the fraction is negative such as -7/4, it can be resolved into
    * -1 3/4, so this method returns the positive proper numerator, 3.</p>
    *
    * @return the numerator fraction part of a proper fraction, always positive
    */
  def getProperNumerator: Int = Math.abs(numerator % denominator)

  /**
    * <p>Gets the proper whole part of the fraction.</p>
    *
    * <p>An improper fraction 7/4 can be resolved into a proper one, 1 3/4.
    * This method returns the 1 from the proper fraction.</p>
    *
    * <p>If the fraction is negative such as -7/4, it can be resolved into
    * -1 3/4, so this method returns the positive whole part -1.</p>
    *
    * @return the whole fraction part of a proper fraction, that includes the sign
    */
  def getProperWhole: Int = numerator / denominator

  /**
    * <p>Gets the fraction as an {@code int}. This returns the whole number
    * part of the fraction.</p>
    *
    * @return the whole number fraction part
    */
  override def intValue: Int = numerator / denominator

  /**
    * <p>Gets the fraction as a {@code long}. This returns the whole number
    * part of the fraction.</p>
    *
    * @return the whole number fraction part
    */
  override def longValue: Long = numerator.toLong / denominator

  /**
    * <p>Gets the fraction as a {@code float}. This calculates the fraction
    * as the numerator divided by denominator.</p>
    *
    * @return the fraction as a {@code float}
    */
  override def floatValue: Float = numerator.toFloat / denominator.toFloat

  /**
    * <p>Gets the fraction as a {@code double}. This calculates the fraction
    * as the numerator divided by denominator.</p>
    *
    * @return the fraction as a {@code double}
    */
  override def doubleValue: Double = numerator.toDouble / denominator.toDouble

  /**
    * <p>Reduce the fraction to the smallest values for the numerator and
    * denominator, returning the result.</p>
    *
    * <p>For example, if this fraction represents 2/4, then the result
    * will be 1/2.</p>
    *
    * @return a new reduced fraction instance, or this if no simplification possible
    */
  def reduce: Fraction = {
    if (numerator == 0)
      return if (this == Fraction.ZERO) this
      else Fraction.ZERO
    val gcd = Fraction.greatestCommonDivisor(Math.abs(numerator), denominator)
    if (gcd == 1) return this
    Fraction.getFraction(numerator / gcd, denominator / gcd)
  }

  /**
    * <p>Gets a fraction that is the inverse (1/fraction) of this one.</p>
    *
    * <p>The returned fraction is not reduced.</p>
    *
    * @return a new fraction instance with the numerator and denominator
    *         inverted.
    * @throws java.lang.ArithmeticException if the fraction represents zero.
    */
  def invert: Fraction = {
    if (numerator == 0) throw new ArithmeticException("Unable to invert zero.")
    if (numerator == Integer.MIN_VALUE) throw new ArithmeticException("overflow: can't negate numerator")
    if (numerator < 0) return new Fraction(-denominator, -numerator)
    new Fraction(denominator, numerator)
  }

  /**
    * <p>Gets a fraction that is the negative (-fraction) of this one.</p>
    *
    * <p>The returned fraction is not reduced.</p>
    *
    * @return a new fraction instance with the opposite signed numerator
    */
  def negate: Fraction = { // the positive range is one smaller than the negative range of an int.
    if (numerator == Integer.MIN_VALUE) throw new ArithmeticException("overflow: too large to negate")
    new Fraction(-numerator, denominator)
  }

  /**
    * <p>Gets a fraction that is the positive equivalent of this one.</p>
    * <p>More precisely: {@code (fraction &gt;= 0 ? this : -fraction)}</p>
    *
    * <p>The returned fraction is not reduced.</p>
    *
    * @return {@code this} if it is positive, or a new positive fraction
    *         instance with the opposite signed numerator
    */
  def abs: Fraction = {
    if (numerator >= 0) return this
    negate
  }

  /**
    * <p>Gets a fraction that is raised to the passed in power.</p>
    *
    * <p>The returned fraction is in reduced form.</p>
    *
    * @param power the power to raise the fraction to
    * @return {@code this}
    *         if the power is one, {@code ONE} if the power
    *         is zero (even if the fraction equals ZERO) or a new fraction instance
    *         raised to the appropriate power
    * @throws java.lang.ArithmeticException
    *         if the resulting numerator or denominator exceeds {@code Integer.MAX_VALUE}
    */
  def pow(power: Int): Fraction =
    if (power == 1) this
    else if (power == 0) Fraction.ONE
    else if (power < 0) {
      if (power == Integer.MIN_VALUE) { // MIN_VALUE can't be negated.
        return this.invert.pow(2).pow(-(power / 2))
      }
      this.invert.pow(-power)
    } else {
      val f = this.multiplyBy(this)
      if (power % 2 == 0) { // if even...
        return f.pow(power / 2)
      }
      f.pow(power / 2).multiplyBy(this)
    }

  /**
    * <p>Adds the value of this fraction to another, returning the result in reduced form.
    * The algorithm follows Knuth, 4.5.1.</p>
    *
    * @param fraction the fraction to add, must not be {@code null}
    * @return a {@code Fraction} instance with the resulting values
    * @throws java.lang.IllegalArgumentException if the fraction is {@code null}
    * @throws java.lang.ArithmeticException      if the resulting numerator or denominator exceeds {@code Integer.MAX_VALUE}
    */
  def add(fraction: Fraction): Fraction = addSub(fraction, true /* add */ )

  /**
    * <p>Subtracts the value of another fraction from the value of this one,
    * returning the result in reduced form.</p>
    *
    * @param fraction the fraction to subtract, must not be {@code null}
    * @return a {@code Fraction} instance with the resulting values
    * @throws java.lang.IllegalArgumentException if the fraction is {@code null}
    * @throws java.lang.ArithmeticException      if the resulting numerator or denominator cannot be represented in an {@code int}.
    */
  def subtract(fraction: Fraction): Fraction = addSub(fraction, false /* subtract */ )

  /**
    * Implement add and subtract using algorithm described in Knuth 4.5.1.
    *
    * @param fraction the fraction to subtract, must not be {@code null}
    * @param isAdd    true to add, false to subtract
    * @return a {@code Fraction} instance with the resulting values
    * @throws java.lang.IllegalArgumentException if the fraction is {@code null}
    * @throws java.lang.ArithmeticException      if the resulting numerator or denominator cannot be represented in an {@code int}.
    */
  private def addSub(fraction: Fraction, isAdd: Boolean): Fraction = {
    Validate.notNull(fraction, "The fraction must not be null")
    // zero is identity for addition.
    if (numerator == 0)
      return {
        if (isAdd) fraction
        else fraction.negate
      }

    if (fraction.numerator == 0) return this
    // if denominators are randomly distributed, d1 will be 1 about 61%
    // of the time.
    val d1 = Fraction.greatestCommonDivisor(denominator, fraction.denominator)
    if (d1 == 1) { // result is ( (u*v' +/- u'v) / u'v')
      val uvp = Fraction.mulAndCheck(numerator, fraction.denominator)
      val upv = Fraction.mulAndCheck(fraction.numerator, denominator)
      return new Fraction(
        if (isAdd) Fraction.addAndCheck(uvp, upv)
        else Fraction.subAndCheck(uvp, upv),
        Fraction.mulPosAndCheck(denominator, fraction.denominator))
    }
    // the quantity 't' requires 65 bits of precision; see knuth 4.5.1
    // exercise 7. we're going to use a BigInteger.
    // t = u(v'/d1) +/- v(u'/d1)
    val uvp = BigInteger.valueOf(numerator).multiply(BigInteger.valueOf(fraction.denominator / d1))
    val upv = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(denominator / d1))
    val t =
      if (isAdd) uvp.add(upv)
      else uvp.subtract(upv)
    // but d2 doesn't need extra precision because
    // d2 = gcd(t,d1) = gcd(t mod d1, d1)
    val tmodd1 = t.mod(BigInteger.valueOf(d1)).intValue
    val d2 =
      if (tmodd1 == 0) d1
      else Fraction.greatestCommonDivisor(tmodd1, d1)
    // result is (t/d2) / (u'/d1)(v'/d2)
    val w = t.divide(BigInteger.valueOf(d2))
    if (w.bitLength > 31) throw new ArithmeticException("overflow: numerator too large after multiply")
    new Fraction(w.intValue, Fraction.mulPosAndCheck(denominator / d1, fraction.denominator / d2))
  }

  /**
    * <p>Multiplies the value of this fraction by another, returning the
    * result in reduced form.</p>
    *
    * @param fraction the fraction to multiply by, must not be {@code null}
    * @return a {@code Fraction} instance with the resulting values
    * @throws java.lang.NullPointerException if the fraction is {@code null}
    * @throws java.lang.ArithmeticException  if the resulting numerator or denominator exceeds {@code Integer.MAX_VALUE}
    */
  def multiplyBy(fraction: Fraction): Fraction = {
    Validate.notNull(fraction, "The fraction must not be null")
    if (numerator == 0 || fraction.numerator == 0) return Fraction.ZERO
    // knuth 4.5.1
    // make sure we don't overflow unless the result *must* overflow.
    val d1 = Fraction.greatestCommonDivisor(numerator, fraction.denominator)
    val d2 = Fraction.greatestCommonDivisor(fraction.numerator, denominator)
    Fraction.getReducedFraction(
      Fraction.mulAndCheck(numerator / d1, fraction.numerator / d2),
      Fraction.mulPosAndCheck(denominator / d2, fraction.denominator / d1))
  }

  /**
    * <p>Divide the value of this fraction by another.</p>
    *
    * @param fraction the fraction to divide by, must not be {@code null}
    * @return a {@code Fraction} instance with the resulting values
    * @throws java.lang.NullPointerException if the fraction is {@code null}
    * @throws java.lang.ArithmeticException  if the fraction to divide by is zero
    *                                        if the resulting numerator or denominator exceeds {@code Integer.MAX_VALUE}
    */
  def divideBy(fraction: Fraction): Fraction = {
    Validate.notNull(fraction, "The fraction must not be null")
    if (fraction.numerator == 0) throw new ArithmeticException("The fraction to divide by must not be zero")
    multiplyBy(fraction.invert)
  }

  /**
    * <p>Compares this fraction to another object to test if they are equal.</p>.
    *
    * <p>To be equal, both values must be equal. Thus 2/4 is not equal to 1/2.</p>
    *
    * @param obj the reference object with which to compare
    * @return {@code true} if this object is equal
    */
  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[AnyRef] && (this eq obj.asInstanceOf[AnyRef])) return true
    if (!obj.isInstanceOf[Fraction]) return false
    val other = obj.asInstanceOf[Fraction]
    getNumerator == other.getNumerator && getDenominator == other.getDenominator
  }

  /**
    * <p>Gets a hashCode for the fraction.</p>
    *
    * @return a hash code value for this object
    */
  override def hashCode: Int = {
    if (_hashCode == 0) { // hash code update should be atomic.
      _hashCode = 37 * (37 * 17 + getNumerator) + getDenominator
    }

    _hashCode
  }

  /**
    * <p>Compares this object to another based on size.</p>
    *
    * <p>Note: this class has a natural ordering that is inconsistent
    * with equals, because, for example, equals treats 1/2 and 2/4 as
    * different, whereas compareTo treats them as equal.
    *
    * @param other the object to compare to
    * @return -1 if this is less, 0 if equal, +1 if greater
    * @throws java.lang.ClassCastException   if the object is not a {@code Fraction}
    * @throws java.lang.NullPointerException if the object is {@code null}
    */
  override def compareTo(other: Fraction): Int = {
    if (this eq other) return 0
    if (numerator == other.numerator && denominator == other.denominator) return 0
    // otherwise see which is less
    val first = numerator.toLong * other.denominator.toLong
    val second = other.numerator.toLong * denominator.toLong

    JavaLong.compare(first, second)
  }

  /**
    * <p>Gets the fraction as a {@code String}.</p>
    *
    * <p>The format used is '<i>numerator</i>/<i>denominator</i>' always.
    *
    * @return a {@code String} form of the fraction
    */
  override def toString: String = {
    if (_toString == null) _toString = getNumerator + "/" + getDenominator
    _toString
  }

  /**
    * <p>Gets the fraction as a proper {@code String} in the format X Y/Z.</p>
    *
    * <p>The format used in '<i>wholeNumber</i> <i>numerator</i>/<i>denominator</i>'.
    * If the whole number is zero it will be omitted. If the numerator is zero,
    * only the whole number is returned.</p>
    *
    * @return a {@code String} form of the fraction
    */
  def toProperString: String = {
    if (_toProperString == null)
      if (numerator == 0) _toProperString = "0"
      else if (numerator == denominator) _toProperString = "1"
      else if (numerator == -1 * denominator) _toProperString = "-1"
      else if ((if (numerator > 0) -numerator
                else numerator) < -denominator) { // note that we do the magnitude comparison test above with
        // NEGATIVE (not positive) numbers, since negative numbers
        // have a larger range. otherwise numerator==Integer.MIN_VALUE
        // is handled incorrectly.
        val properNumerator = getProperNumerator
        if (properNumerator == 0) _toProperString = Integer.toString(getProperWhole)
        else _toProperString = getProperWhole + " " + properNumerator + "/" + getDenominator
      } else _toProperString = getNumerator + "/" + getDenominator

    _toProperString
  }
}
