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

//package org.apache.commons.lang3.text
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertNotEquals
//import java.text.DateFormat
//import java.text.FieldPosition
//import java.text.Format
//import java.text.MessageFormat
//import java.text.NumberFormat
//import java.text.ParsePosition
//import java.util
//import java.util.Calendar
//import java.util.Collections
//import java.util.Locale
//import org.junit.Before
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Test case for {@link ExtendedMessageFormat}.
//  *
//  * @since 2.4
//  */
//@deprecated object ExtendedMessageFormatTest {
//
//  /**
//    * {@link Format} implementation which converts to lower case.
//    */
//  @SerialVersionUID(1L)
//  private class LowerCaseFormat extends Format {
//    override def format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition) = toAppendTo.append(obj.asInstanceOf[String].toLowerCase(Locale.ROOT))
//
//    override def parseObject(source: String, pos: ParsePosition) = throw new UnsupportedOperationException
//  }
//
//  /**
//    * {@link Format} implementation which converts to upper case.
//    */
//  @SerialVersionUID(1L)
//  private class UpperCaseFormat extends Format {
//    override def format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition) = toAppendTo.append(obj.asInstanceOf[String].toUpperCase(Locale.ROOT))
//
//    override def parseObject(source: String, pos: ParsePosition) = throw new UnsupportedOperationException
//  }
//
//  /**
//    * {@link FormatFactory} implementation for lower case format.
//    */
//  private object LowerCaseFormatFactory {
//    private val LOWER_INSTANCE = new ExtendedMessageFormatTest.LowerCaseFormat
//  }
//
//  private class LowerCaseFormatFactory extends Nothing {
//    def getFormat(name: String, arguments: String, locale: Locale) = LowerCaseFormatFactory.LOWER_INSTANCE
//  }
//
//  /**
//    * {@link FormatFactory} implementation for upper case format.
//    */
//  private object UpperCaseFormatFactory {
//    private val UPPER_INSTANCE = new ExtendedMessageFormatTest.UpperCaseFormat
//  }
//
//  private class UpperCaseFormatFactory extends Nothing {
//    def getFormat(name: String, arguments: String, locale: Locale) = UpperCaseFormatFactory.UPPER_INSTANCE
//  }
//
//  /**
//    * {@link FormatFactory} implementation to override date format "short" to "default".
//    */
//  private class OverrideShortDateFormatFactory extends Nothing {
//    def getFormat(name: String, arguments: String, locale: Locale) = if (!("short" == arguments)) null
//    else if (locale == null) DateFormat.getDateInstance(DateFormat.DEFAULT)
//    else DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
//  }
//
//  /**
//    * Alternative ExtendedMessageFormat impl.
//    */
//  @SerialVersionUID(1L)
//  private class OtherExtendedMessageFormat private[text](val pattern: String, val locale: Locale, val registry: Map[String, _ <: Nothing]) extends Nothing(pattern, locale, registry) {
//  }
//
//}
//
//@deprecated class ExtendedMessageFormatTest extends JUnitSuite {
//  final private val registry = new util.HashMap[String, Nothing]
//
//  @Before def setUp() = {
//    registry.put("lower", new ExtendedMessageFormatTest.LowerCaseFormatFactory)
//    registry.put("upper", new ExtendedMessageFormatTest.UpperCaseFormatFactory)
//  }
//
//  /**
//    * Test extended formats.
//    */
//  @Test def testExtendedFormats() = {
//    val pattern = "Lower: {0,lower} Upper: {1,upper}"
//    val emf = new Nothing(pattern, registry)
//    assertEquals(pattern, emf.toPattern, "TOPATTERN")
//    assertEquals(emf.format(Array[AnyRef]("foo", "bar")), "Lower: foo Upper: BAR")
//    assertEquals(emf.format(Array[AnyRef]("Foo", "Bar")), "Lower: foo Upper: BAR")
//    assertEquals(emf.format(Array[AnyRef]("FOO", "BAR")), "Lower: foo Upper: BAR")
//    assertEquals(emf.format(Array[AnyRef]("FOO", "bar")), "Lower: foo Upper: BAR")
//    assertEquals(emf.format(Array[AnyRef]("foo", "BAR")), "Lower: foo Upper: BAR")
//  }
//
//  /**
//    * Test Bug LANG-477 - out of memory error with escaped quote
//    */
//  @Test def testEscapedQuote_LANG_477() = {
//    val pattern = "it''s a {0,lower} 'test'!"
//    val emf = new Nothing(pattern, registry)
//    assertEquals("it's a dummy test!", emf.format(Array[AnyRef]("DUMMY")))
//  }
//
//  /**
//    * Test Bug LANG-917 - IndexOutOfBoundsException and/or infinite loop when using a choice pattern
//    */
//  @Test def testEmbeddedPatternInChoice() = {
//    val pattern = "Hi {0,lower}, got {1,choice,0#none|1#one|1<{1,number}}, {2,upper}!"
//    val emf = new Nothing(pattern, registry)
//    assertEquals(emf.format(Array[AnyRef]("there", 3, "great")), "Hi there, got 3, GREAT!")
//  }
//
//  /**
//    * Test Bug LANG-948 - Exception while using ExtendedMessageFormat and escaping braces
//    */
//  @Test def testEscapedBraces_LANG_948() = { // message without placeholder because braces are escaped by quotes
//    val pattern = "Message without placeholders '{}'"
//    val emf = new Nothing(pattern, registry)
//    assertEquals("Message without placeholders {}", emf.format(Array[AnyRef]("DUMMY")))
//    // message with placeholder because quotes are escaped by quotes
//    val pattern2 = "Message with placeholder ''{0}''"
//    val emf2 = new Nothing(pattern2, registry)
//    assertEquals("Message with placeholder 'DUMMY'", emf2.format(Array[AnyRef]("DUMMY")))
//  }
//
//  /**
//    * Test extended and built in formats.
//    */
//  @Test def testExtendedAndBuiltInFormats() = {
//    val cal = Calendar.getInstance
//    cal.set(2007, Calendar.JANUARY, 23, 18, 33, 5)
//    val args = Array[AnyRef]("John Doe", cal.getTime, Double.valueOf("12345.67"))
//    val builtinsPattern = "DOB: {1,date,short} Salary: {2,number,currency}"
//    val extendedPattern = "Name: {0,upper} "
//    val pattern = extendedPattern + builtinsPattern
//    val testLocales = new util.HashSet[Locale](util.Arrays.asList(DateFormat.getAvailableLocales))
//    testLocales.retainAll(util.Arrays.asList(NumberFormat.getAvailableLocales))
//    testLocales.add(null)
//    import scala.collection.JavaConversions._
//    for (locale <- testLocales) {
//      val builtins = createMessageFormat(builtinsPattern, locale)
//      val expectedPattern = extendedPattern + builtins.toPattern
//      var df = null
//      var nf = null
//      var emf = null
//      if (locale == null) {
//        df = DateFormat.getDateInstance(DateFormat.SHORT)
//        nf = NumberFormat.getCurrencyInstance
//        emf = new Nothing(pattern, registry)
//      }
//      else {
//        df = DateFormat.getDateInstance(DateFormat.SHORT, locale)
//        nf = NumberFormat.getCurrencyInstance(locale)
//        emf = new Nothing(pattern, locale, registry)
//      }
//      val expected = new StringBuilder
//      expected.append("Name: ")
//      expected.append(args(0).toString.toUpperCase(Locale.ROOT))
//      expected.append(" DOB: ")
//      expected.append(df.format(args(1)))
//      expected.append(" Salary: ")
//      expected.append(nf.format(args(2)))
//      assertEquals(expectedPattern, emf.toPattern, "pattern comparison for locale " + locale)
//      assertEquals(expected.toString, emf.format(args), String.valueOf(locale))
//    }
//  }
//
//  /**
//    * Test the built in choice format.
//    */
//  @Test def testBuiltInChoiceFormat() = {
//    val values = Array[Number](Integer.valueOf(1), Double.valueOf("2.2"), Double.valueOf("1234.5"))
//    var choicePattern = null
//    val availableLocales = NumberFormat.getAvailableLocales
//    choicePattern = "{0,choice,1#One|2#Two|3#Many {0,number}}"
//    for (value <- values) {
//      checkBuiltInFormat(value + ": " + choicePattern, Array[AnyRef](value), availableLocales)
//    }
//    choicePattern = "{0,choice,1#''One''|2#\"Two\"|3#''{Many}'' {0,number}}"
//    for (value <- values) {
//      checkBuiltInFormat(value + ": " + choicePattern, Array[AnyRef](value), availableLocales)
//    }
//  }
//
//  /**
//    * Test the built in date/time formats
//    */
//  @Test def testBuiltInDateTimeFormat() = {
//    val cal = Calendar.getInstance
//    cal.set(2007, Calendar.JANUARY, 23, 18, 33, 5)
//    val args = Array[AnyRef](cal.getTime)
//    val availableLocales = DateFormat.getAvailableLocales
//    checkBuiltInFormat("1: {0,date,short}", args, availableLocales)
//    checkBuiltInFormat("2: {0,date,medium}", args, availableLocales)
//    checkBuiltInFormat("3: {0,date,long}", args, availableLocales)
//    checkBuiltInFormat("4: {0,date,full}", args, availableLocales)
//    checkBuiltInFormat("5: {0,date,d MMM yy}", args, availableLocales)
//    checkBuiltInFormat("6: {0,time,short}", args, availableLocales)
//    checkBuiltInFormat("7: {0,time,medium}", args, availableLocales)
//    checkBuiltInFormat("8: {0,time,long}", args, availableLocales)
//    checkBuiltInFormat("9: {0,time,full}", args, availableLocales)
//    checkBuiltInFormat("10: {0,time,HH:mm}", args, availableLocales)
//    checkBuiltInFormat("11: {0,date}", args, availableLocales)
//    checkBuiltInFormat("12: {0,time}", args, availableLocales)
//  }
//
//  @Test def testOverriddenBuiltinFormat() = {
//    val cal = Calendar.getInstance
//    cal.set(2007, Calendar.JANUARY, 23)
//    val args = Array[AnyRef](cal.getTime)
//    val availableLocales = DateFormat.getAvailableLocales
//    val dateRegistry = Collections.singletonMap("date", new ExtendedMessageFormatTest.OverrideShortDateFormatFactory)
//    //check the non-overridden builtins:
//    checkBuiltInFormat("1: {0,date}", dateRegistry, args, availableLocales)
//    checkBuiltInFormat("2: {0,date,medium}", dateRegistry, args, availableLocales)
//    checkBuiltInFormat("3: {0,date,long}", dateRegistry, args, availableLocales)
//    checkBuiltInFormat("4: {0,date,full}", dateRegistry, args, availableLocales)
//    checkBuiltInFormat("5: {0,date,d MMM yy}", dateRegistry, args, availableLocales)
//    //check the overridden format:
//    for (i <- -1 until availableLocales.length) {
//      val locale = if (i < 0) null
//      else availableLocales(i)
//      val dateDefault = createMessageFormat("{0,date}", locale)
//      val pattern = "{0,date,short}"
//      val dateShort = new Nothing(pattern, locale, dateRegistry)
//      assertEquals(dateDefault.format(args), dateShort.format(args), "overridden date,short format")
//      assertEquals(pattern, dateShort.toPattern, "overridden date,short pattern")
//    }
//  }
//
//  /**
//    * Test the built in number formats.
//    */
//  @Test def testBuiltInNumberFormat() = {
//    val args = Array[AnyRef](Double.valueOf("6543.21"))
//    val availableLocales = NumberFormat.getAvailableLocales
//    checkBuiltInFormat("1: {0,number}", args, availableLocales)
//    checkBuiltInFormat("2: {0,number,integer}", args, availableLocales)
//    checkBuiltInFormat("3: {0,number,currency}", args, availableLocales)
//    checkBuiltInFormat("4: {0,number,percent}", args, availableLocales)
//    checkBuiltInFormat("5: {0,number,00000.000}", args, availableLocales)
//  }
//
//  /**
//    * Test equals() and hashcode.
//    */
//  @Test def testEqualsHashcode() = {
//    val fmtRegistry = Collections.singletonMap("testfmt", new ExtendedMessageFormatTest.LowerCaseFormatFactory)
//    val otherRegistry = Collections.singletonMap("testfmt", new ExtendedMessageFormatTest.UpperCaseFormatFactory)
//    val pattern = "Pattern: {0,testfmt}"
//    val emf = new Nothing(pattern, Locale.US, fmtRegistry)
//    var other = null
//    // Same object
//    assertEquals(emf, emf, "same, equals()")
//    assertEquals(emf.hashCode, emf.hashCode, "same, hashcode()")
//    // Equal Object
//    other = new Nothing(pattern, Locale.US, fmtRegistry)
//    assertEquals(emf, other, "equal, equals()")
//    assertEquals(emf.hashCode, other.hashCode, "equal, hashcode()")
//    // Different Class
//    other = new ExtendedMessageFormatTest.OtherExtendedMessageFormat(pattern, Locale.US, fmtRegistry)
//    assertNotEquals(emf, other, "class, equals()")
//    assertEquals(emf.hashCode, other.hashCode, "class, hashcode()") // same hashcode
//    // Different pattern
//    other = new Nothing("X" + pattern, Locale.US, fmtRegistry)
//    assertNotEquals(emf, other, "pattern, equals()")
//    assertNotEquals(emf.hashCode, other.hashCode, "pattern, hashcode()")
//    // Different registry
//    other = new Nothing(pattern, Locale.US, otherRegistry)
//    assertNotEquals(emf, other, "registry, equals()")
//    assertNotEquals(emf.hashCode, other.hashCode, "registry, hashcode()")
//    // Different Locale
//    other = new Nothing(pattern, Locale.FRANCE, fmtRegistry)
//    assertNotEquals(emf, other, "locale, equals()")
//    assertEquals(emf.hashCode, other.hashCode, "locale, hashcode()")
//  }
//
//  /**
//    * Test a built in format for the specified Locales, plus {@code null} Locale.
//    *
//    * @param pattern MessageFormat pattern
//    * @param args    MessageFormat arguments
//    * @param locales to test
//    */
//  private def checkBuiltInFormat(pattern: String, args: Array[AnyRef], locales: Array[Locale]) = checkBuiltInFormat(pattern, null, args, locales)
//
//  /**
//    * Test a built in format for the specified Locales, plus {@code null} Locale.
//    *
//    * @param pattern     MessageFormat pattern
//    * @param fmtRegistry FormatFactory registry to use
//    * @param args        MessageFormat arguments
//    * @param locales     to test
//    */
//  private def checkBuiltInFormat(pattern: String, fmtRegistry: util.Map[String, _], args: Array[AnyRef], locales: Array[Locale]) = {
//    checkBuiltInFormat(pattern, fmtRegistry, args, null.asInstanceOf[Locale])
//    for (locale <- locales) {
//      checkBuiltInFormat(pattern, fmtRegistry, args, locale)
//    }
//  }
//
//  /**
//    * Create an ExtendedMessageFormat for the specified pattern and locale and check the
//    * formatted output matches the expected result for the parameters.
//    *
//    * @param pattern        string
//    * @param registryUnused map (currently unused)
//    * @param args           Object[]
//    * @param locale         Locale
//    */
//  private def checkBuiltInFormat(pattern: String, registryUnused: util.Map[String, _], args: Array[AnyRef], locale: Locale) = {
//    val buffer = new StringBuilder
//    buffer.append("Pattern=[")
//    buffer.append(pattern)
//    buffer.append("], locale=[")
//    buffer.append(locale)
//    buffer.append("]")
//    val mf = createMessageFormat(pattern, locale)
//    var emf = null
//    if (locale == null) emf = new Nothing(pattern)
//    else emf = new Nothing(pattern, locale)
//    assertEquals(mf.format(args), emf.format(args), "format " + buffer.toString)
//    assertEquals(mf.toPattern, emf.toPattern, "toPattern " + buffer.toString)
//  }
//
//  /**
//    * Replace MessageFormat(String, Locale) constructor (not available until JDK 1.4).
//    *
//    * @param pattern string
//    * @param locale  Locale
//    * @return MessageFormat
//    */
//  private def createMessageFormat(pattern: String, locale: Locale) = {
//    val result = new MessageFormat(pattern)
//    if (locale != null) {
//      result.setLocale(locale)
//      result.applyPattern(pattern)
//    }
//    result
//  }
//}
