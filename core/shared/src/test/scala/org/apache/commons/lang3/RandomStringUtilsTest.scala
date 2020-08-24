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
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.Matchers.allOf
//import org.hamcrest.Matchers.greaterThanOrEqualTo
//import org.hamcrest.Matchers.is
//import org.hamcrest.Matchers.lessThanOrEqualTo
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import org.junit.Assert.fail
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import java.nio.charset.Charset
//import java.nio.charset.StandardCharsets
//import java.util.Random
//import org.junit.Test
//
//
///**
//  * Unit tests {@link org.apache.commons.lang3.RandomStringUtils}.
//  */
//class RandomStringUtilsTest {
////  @Test def testConstructor() = {
////    assertNotNull(new Nothing)
////    val cons = classOf[Nothing].getDeclaredConstructors
////    assertEquals(1, cons.length)
////    assertTrue(Modifier.isPublic(cons(0).getModifiers))
////    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
////    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
////  }
//
//  /**
//    * Test the implementation
//    */
//  @Test def testRandomStringUtils() = {
//    var r1 = RandomStringUtils.random(50)
//    assertEquals(50, r1.length, "random(50) length")
//    var r2 = RandomStringUtils.random(50)
//    assertEquals(50, r2.length, "random(50) length")
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.randomAscii(50)
//    assertEquals(50, r1.length, "randomAscii(50) length")
//    for (i <- 0 until r1.length) {
//      assertTrue(r1.charAt(i) >= 32 && r1.charAt(i) <= 127, "char between 32 and 127")
//    }
//    r2 = RandomStringUtils.randomAscii(50)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.randomAlphabetic(50)
//    assertEquals(50, r1.length, "randomAlphabetic(50)")
//    for (i <- 0 until r1.length) {
//      assertTrue(Character.isLetter(r1.charAt(i)) && !Character.isDigit(r1.charAt(i)), "r1 contains alphabetic")
//    }
//    r2 = RandomStringUtils.randomAlphabetic(50)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.randomAlphanumeric(50)
//    assertEquals(50, r1.length, "randomAlphanumeric(50)")
//    for (i <- 0 until r1.length) {
//      assertTrue(Character.isLetterOrDigit(r1.charAt(i)), "r1 contains alphanumeric")
//    }
//    r2 = RandomStringUtils.randomAlphabetic(50)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.randomGraph(50)
//    assertEquals(50, r1.length, "randomGraph(50) length")
//    for (i <- 0 until r1.length) {
//      assertTrue(r1.charAt(i) >= 33 && r1.charAt(i) <= 126, "char between 33 and 126")
//    }
//    r2 = RandomStringUtils.randomGraph(50)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.randomNumeric(50)
//    assertEquals(50, r1.length, "randomNumeric(50)")
//    for (i <- 0 until r1.length) {
//      assertTrue(Character.isDigit(r1.charAt(i)) && !Character.isLetter(r1.charAt(i)), "r1 contains numeric")
//    }
//    r2 = RandomStringUtils.randomNumeric(50)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.randomPrint(50)
//    assertEquals(50, r1.length, "randomPrint(50) length")
//    for (i <- 0 until r1.length) {
//      assertTrue(r1.charAt(i) >= 32 && r1.charAt(i) <= 126, "char between 32 and 126")
//    }
//    r2 = RandomStringUtils.randomPrint(50)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    var set = "abcdefg"
//    r1 = RandomStringUtils.random(50, set)
//    assertEquals(50, r1.length, "random(50, \"abcdefg\")")
//    for (i <- 0 until r1.length) {
//      assertTrue(set.indexOf(r1.charAt(i)) > -1, "random char in set")
//    }
//    r2 = RandomStringUtils.random(50, set)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.random(50, null.asInstanceOf[String])
//    assertEquals(50, r1.length, "random(50) length")
//    r2 = RandomStringUtils.random(50, null.asInstanceOf[String])
//    assertEquals(50, r2.length, "random(50) length")
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    set = "stuvwxyz"
//    r1 = RandomStringUtils.random(50, set.toCharArray)
//    assertEquals(50, r1.length, "random(50, \"stuvwxyz\")")
//    for (i <- 0 until r1.length) {
//      assertTrue(set.indexOf(r1.charAt(i)) > -1, "random char in set")
//    }
//    r2 = RandomStringUtils.random(50, set)
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    r1 = RandomStringUtils.random(50, null.asInstanceOf[Array[Char]])
//    assertEquals(50, r1.length, "random(50) length")
//    r2 = RandomStringUtils.random(50, null.asInstanceOf[Array[Char]])
//    assertEquals(50, r2.length, "random(50) length")
//    assertTrue(!(r1 == r2), "!r1.equals(r2)")
//    val seed = System.currentTimeMillis
//    r1 = RandomStringUtils.random(50, 0, 0, true, true, null, new Random(seed))
//    r2 = RandomStringUtils.random(50, 0, 0, true, true, null, new Random(seed))
//    assertEquals(r1, r2, "r1.equals(r2)")
//    r1 = RandomStringUtils.random(0)
//    assertEquals("", r1, "random(0).equals(\"\")")
//  }
//
//  @Test def testLANG805() = {
//    val seed = System.currentTimeMillis
//    assertEquals("aaa", RandomStringUtils.random(3, 0, 0, false, false, Array[Char]('a'), new Random(seed)))
//  }
//
//  @Test def testLANG807() = {
//    val ex = assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(3, 5, 5, false, false))
//    val msg = ex.getMessage
//    assertTrue(msg.contains("start"), "Message (" + msg + ") must contain 'start'")
//    assertTrue(msg.contains("end"), "Message (" + msg + ") must contain 'end'")
//  }
//
//  @Test def testExceptions() = {
//    val DUMMY = Array[Char]('a') // valid char array
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, true, true))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, DUMMY))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(1, new Array[Char](0)))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, ""))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, null.asInstanceOf[String]))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, 'a', 'z', false, false))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, 'a', 'z', false, false, DUMMY))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(-1, 'a', 'z', false, false, DUMMY, new Random))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(8, 32, 48, false, true))
//    assertThrows(classOf[IllegalArgumentException], () => RandomStringUtils.random(8, 32, 65, true, false))
//  }
//
//  /**
//    * Make sure boundary alphanumeric characters are generated by randomAlphaNumeric
//    * This test will fail randomly with probability = 6 * (61/62)**1000 ~ 5.2E-7
//    */
//  @Test def testRandomAlphaNumeric() = {
//    val testChars = Array('a', 'z', 'A', 'Z', '0', '9')
//    val found = Array(false, false, false, false, false, false)
//    for (i <- 0 until 100) {
//      val randString = RandomStringUtils.randomAlphanumeric(10)
//      for (j <- 0 until testChars.length) {
//        if (randString.indexOf(testChars(j)) > 0) found(j) = true
//      }
//    }
//    for (i <- 0 until testChars.length) {
//      assertTrue(found(i), "alphanumeric character not generated in 1000 attempts: " + testChars(i) + " -- repeated failures indicate a problem ")
//    }
//  }
//
//  /**
//    * Make sure '0' and '9' are generated by randomNumeric
//    * This test will fail randomly with probability = 2 * (9/10)**1000 ~ 3.5E-46
//    */
//  @Test def testRandomNumeric() = {
//    val testChars = Array('0', '9')
//    val found = Array(false, false)
//    for (i <- 0 until 100) {
//      val randString = RandomStringUtils.randomNumeric(10)
//      for (j <- 0 until testChars.length) {
//        if (randString.indexOf(testChars(j)) > 0) found(j) = true
//      }
//    }
//    for (i <- 0 until testChars.length) {
//      assertTrue(found(i), "digit not generated in 1000 attempts: " + testChars(i) + " -- repeated failures indicate a problem ")
//    }
//  }
//
//  /**
//    * Make sure boundary alpha characters are generated by randomAlphabetic
//    * This test will fail randomly with probability = 4 * (51/52)**1000 ~ 1.58E-8
//    */
//  @Test def testRandomAlphabetic() = {
//    val testChars = Array('a', 'z', 'A', 'Z')
//    val found = Array(false, false, false, false)
//    for (i <- 0 until 100) {
//      val randString = RandomStringUtils.randomAlphabetic(10)
//      for (j <- 0 until testChars.length) {
//        if (randString.indexOf(testChars(j)) > 0) found(j) = true
//      }
//    }
//    for (i <- 0 until testChars.length) {
//      assertTrue(found(i), "alphanumeric character not generated in 1000 attempts: " + testChars(i) + " -- repeated failures indicate a problem ")
//    }
//  }
//
//  /**
//    * Make sure 32 and 127 are generated by randomNumeric
//    * This test will fail randomly with probability = 2*(95/96)**1000 ~ 5.7E-5
//    */
//  @Test def testRandomAscii() = {
//    val testChars = Array(32.toChar, 126.toChar)
//    val found = Array(false, false)
//    for (i <- 0 until 100) {
//      val randString = RandomStringUtils.randomAscii(10)
//      for (j <- 0 until testChars.length) {
//        if (randString.indexOf(testChars(j)) > 0) found(j) = true
//      }
//    }
//    for (i <- 0 until testChars.length) {
//      assertTrue(found(i), "ascii character not generated in 1000 attempts: " + testChars(i).toInt + " -- repeated failures indicate a problem")
//    }
//  }
//
//  @Test def testRandomAsciiRange() = {
//    val expectedMinLengthInclusive = 1
//    val expectedMaxLengthExclusive = 11
//    val pattern = "^\\p{ASCII}{" + expectedMinLengthInclusive + ',' + expectedMaxLengthExclusive + "}$"
//    var maxCreatedLength = expectedMinLengthInclusive
//    var minCreatedLength = expectedMaxLengthExclusive - 1
//    for (i <- 0 until 1000) {
//      val s = RandomStringUtils.randomAscii(expectedMinLengthInclusive, expectedMaxLengthExclusive)
//      assertThat("within range", s.length, allOf(greaterThanOrEqualTo(expectedMinLengthInclusive), lessThanOrEqualTo(expectedMaxLengthExclusive - 1)))
//      assertTrue(s.matches(pattern), s)
//      if (s.length < minCreatedLength) minCreatedLength = s.length
//      if (s.length > maxCreatedLength) maxCreatedLength = s.length
//    }
//    assertThat("min generated, may fail randomly rarely", minCreatedLength, is(expectedMinLengthInclusive))
//    assertThat("max generated, may fail randomly rarely", maxCreatedLength, is(expectedMaxLengthExclusive - 1))
//  }
//
//  @Test def testRandomAlphabeticRange() = {
//    val expectedMinLengthInclusive = 1
//    val expectedMaxLengthExclusive = 11
//    val pattern = "^\\p{Alpha}{" + expectedMinLengthInclusive + ',' + expectedMaxLengthExclusive + "}$"
//    var maxCreatedLength = expectedMinLengthInclusive
//    var minCreatedLength = expectedMaxLengthExclusive - 1
//    for (i <- 0 until 1000) {
//      val s = RandomStringUtils.randomAlphabetic(expectedMinLengthInclusive, expectedMaxLengthExclusive)
//      assertThat("within range", s.length, allOf(greaterThanOrEqualTo(expectedMinLengthInclusive), lessThanOrEqualTo(expectedMaxLengthExclusive - 1)))
//      assertTrue(s.matches(pattern), s)
//      if (s.length < minCreatedLength) minCreatedLength = s.length
//      if (s.length > maxCreatedLength) maxCreatedLength = s.length
//    }
//    assertThat("min generated, may fail randomly rarely", minCreatedLength, is(expectedMinLengthInclusive))
//    assertThat("max generated, may fail randomly rarely", maxCreatedLength, is(expectedMaxLengthExclusive - 1))
//  }
//
//  @Test def testRandomAlphanumericRange() = {
//    val expectedMinLengthInclusive = 1
//    val expectedMaxLengthExclusive = 11
//    val pattern = "^\\p{Alnum}{" + expectedMinLengthInclusive + ',' + expectedMaxLengthExclusive + "}$"
//    var maxCreatedLength = expectedMinLengthInclusive
//    var minCreatedLength = expectedMaxLengthExclusive - 1
//    for (i <- 0 until 1000) {
//      val s = RandomStringUtils.randomAlphanumeric(expectedMinLengthInclusive, expectedMaxLengthExclusive)
//      assertThat("within range", s.length, allOf(greaterThanOrEqualTo(expectedMinLengthInclusive), lessThanOrEqualTo(expectedMaxLengthExclusive - 1)))
//      assertTrue(s.matches(pattern), s)
//      if (s.length < minCreatedLength) minCreatedLength = s.length
//      if (s.length > maxCreatedLength) maxCreatedLength = s.length
//    }
//    assertThat("min generated, may fail randomly rarely", minCreatedLength, is(expectedMinLengthInclusive))
//    assertThat("max generated, may fail randomly rarely", maxCreatedLength, is(expectedMaxLengthExclusive - 1))
//  }
//
//  @Test def testRandomGraphRange() = {
//    val expectedMinLengthInclusive = 1
//    val expectedMaxLengthExclusive = 11
//    val pattern = "^\\p{Graph}{" + expectedMinLengthInclusive + ',' + expectedMaxLengthExclusive + "}$"
//    var maxCreatedLength = expectedMinLengthInclusive
//    var minCreatedLength = expectedMaxLengthExclusive - 1
//    for (i <- 0 until 1000) {
//      val s = RandomStringUtils.randomGraph(expectedMinLengthInclusive, expectedMaxLengthExclusive)
//      assertThat("within range", s.length, allOf(greaterThanOrEqualTo(expectedMinLengthInclusive), lessThanOrEqualTo(expectedMaxLengthExclusive - 1)))
//      assertTrue(s.matches(pattern), s)
//      if (s.length < minCreatedLength) minCreatedLength = s.length
//      if (s.length > maxCreatedLength) maxCreatedLength = s.length
//    }
//    assertThat("min generated, may fail randomly rarely", minCreatedLength, is(expectedMinLengthInclusive))
//    assertThat("max generated, may fail randomly rarely", maxCreatedLength, is(expectedMaxLengthExclusive - 1))
//  }
//
//  @Test def testRandomNumericRange() = {
//    val expectedMinLengthInclusive = 1
//    val expectedMaxLengthExclusive = 11
//    val pattern = "^\\p{Digit}{" + expectedMinLengthInclusive + ',' + expectedMaxLengthExclusive + "}$"
//    var maxCreatedLength = expectedMinLengthInclusive
//    var minCreatedLength = expectedMaxLengthExclusive - 1
//    for (i <- 0 until 1000) {
//      val s = RandomStringUtils.randomNumeric(expectedMinLengthInclusive, expectedMaxLengthExclusive)
//      assertThat("within range", s.length, allOf(greaterThanOrEqualTo(expectedMinLengthInclusive), lessThanOrEqualTo(expectedMaxLengthExclusive - 1)))
//      assertTrue(s.matches(pattern), s)
//      if (s.length < minCreatedLength) minCreatedLength = s.length
//      if (s.length > maxCreatedLength) maxCreatedLength = s.length
//    }
//    assertThat("min generated, may fail randomly rarely", minCreatedLength, is(expectedMinLengthInclusive))
//    assertThat("max generated, may fail randomly rarely", maxCreatedLength, is(expectedMaxLengthExclusive - 1))
//  }
//
//  @Test def testRandomPrintRange() = {
//    val expectedMinLengthInclusive = 1
//    val expectedMaxLengthExclusive = 11
//    val pattern = "^\\p{Print}{" + expectedMinLengthInclusive + ',' + expectedMaxLengthExclusive + "}$"
//    var maxCreatedLength = expectedMinLengthInclusive
//    var minCreatedLength = expectedMaxLengthExclusive - 1
//    for (i <- 0 until 1000) {
//      val s = RandomStringUtils.randomPrint(expectedMinLengthInclusive, expectedMaxLengthExclusive)
//      assertThat("within range", s.length, allOf(greaterThanOrEqualTo(expectedMinLengthInclusive), lessThanOrEqualTo(expectedMaxLengthExclusive - 1)))
//      assertTrue(s.matches(pattern), s)
//      if (s.length < minCreatedLength) minCreatedLength = s.length
//      if (s.length > maxCreatedLength) maxCreatedLength = s.length
//    }
//    assertThat("min generated, may fail randomly rarely", minCreatedLength, is(expectedMinLengthInclusive))
//    assertThat("max generated, may fail randomly rarely", maxCreatedLength, is(expectedMaxLengthExclusive - 1))
//  }
//
//  /**
//    * Test homogeneity of random strings generated --
//    * i.e., test that characters show up with expected frequencies
//    * in generated strings.  Will fail randomly about 1 in 1000 times.
//    * Repeated failures indicate a problem.
//    */
//  @Test def testRandomStringUtilsHomog() = {
//    val set = "abc"
//    val chars = set.toCharArray
//    var gen = ""
//    val counts = Array(0, 0, 0)
//    val expected = Array(200, 200, 200)
//    for (i <- 0 until 100) {
//      gen = RandomStringUtils.random(6, chars)
//      for (j <- 0 until 6) {
//        gen.charAt(j) match {
//          case 'a' =>
//            counts(0) += 1
//          case 'b' =>
//            counts(1) += 1
//          case 'c' =>
//            counts(2) += 1
//          case _ =>
//            fail("generated character not in set")
//        }
//      }
//    }
//    // Perform chi-square test with df = 3-1 = 2, testing at .001 level
//    assertTrue(chiSquare(expected, counts) < 13.82, "test homogeneity -- will fail about 1 in 1000 times")
//  }
//
//  /**
//    * Computes Chi-Square statistic given observed and expected counts
//    *
//    * @param observed array of observed frequency counts
//    * @param expected array of expected frequency counts
//    */
//  private def chiSquare(expected: Array[Int], observed: Array[Int]) = {
//    var sumSq = 0.0d
//    var dev = 0.0d
//    for (i <- 0 until observed.length) {
//      dev = observed(i) - expected(i)
//      sumSq += dev * dev / expected(i)
//    }
//    sumSq
//  }
//
//  /**
//    * Checks if the string got by {@link RandomStringUtils# random ( int )}
//    * can be converted to UTF-8 and back without loss.
//    *
//    * @see <a href="https://issues.apache.org/jira/browse/LANG-100">LANG-100</a>
//    */
//  @Test def testLang100() = {
//    val size = 5000
//    val charset = StandardCharsets.UTF_8
//    val orig = RandomStringUtils.random(size)
//    val bytes = orig.getBytes(charset)
//    val copy = new String(bytes, charset)
//    // for a verbose compare:
//    var i = 0
//    while ( {
//      i < orig.length && i < copy.length
//    }) {
//      val o = orig.charAt(i)
//      val c = copy.charAt(i)
//      assertEquals(o, c, "differs at " + i + "(" + Integer.toHexString(Character.valueOf(o).hashCode) + "," + Integer.toHexString(Character.valueOf(c).hashCode) + ")")
//      i += 1
//    }
//    // compare length also
//    assertEquals(orig.length, copy.length)
//    // just to be complete
//    assertEquals(orig, copy)
//  }
//
//  /**
//    * Test for LANG-1286. Creates situation where old code would
//    * overflow a char and result in a code point outside the specified
//    * range.
//    */
//  @Test def testCharOverflow() = {
//    val start = Character.MAX_VALUE
//    val `end` = Integer.MAX_VALUE
//    @SuppressWarnings(Array("serial")) val fixedRandom = new Random() {
//      override def nextInt(n: Int) = { // Prevents selection of 'start' as the character
//        super.nextInt(n - 1) + 1
//      }
//    }
//    val result = RandomStringUtils.random(2, start, `end`, false, false, null, fixedRandom)
//    val c = result.codePointAt(0)
//    assertTrue(c >= start && c < `end`, String.format("Character '%d' not in range [%d,%d).", c, start, `end`))
//  }
//}
