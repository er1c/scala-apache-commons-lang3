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

import java.io.IOException
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import org.apache.commons.lang3.text.translate.NumericEntityEscaper
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

@deprecated object StringEscapeUtilsTest {
  private val FOO = "foo"

  private val HTML_ESCAPES = Array(
    Array("no escaping", "plain text", "plain text"),
    Array("no escaping", "plain text", "plain text"),
    Array("empty string", "", ""),
    Array("null", null, null),
    Array("ampersand", "bread &amp; butter", "bread & butter"),
    Array("quotes", "&quot;bread&quot; &amp; butter", "\"bread\" & butter"),
    Array("final character only", "greater than &gt;", "greater than >"),
    Array("first character only", "&lt; less than", "< less than"),
    Array("apostrophe", "Huntington's chorea", "Huntington's chorea"),
    Array(
      "languages",
      "English,Fran&ccedil;ais,\u65E5\u672C\u8A9E (nihongo)",
      "English,Fran\u00E7ais,\u65E5\u672C\u8A9E (nihongo)"),
    Array("8-bit ascii shouldn't number-escape", "\u0080\u009F", "\u0080\u009F")
  )
}

/**
  * Unit tests for {@link StringEscapeUtils}.
  */
@deprecated
class StringEscapeUtilsTest extends JUnitSuite {
//  @Test def testConstructor(): Unit = {
//    assertNotNull(new StringEscapeUtils.type)
//    val cons = classOf[StringEscapeUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[StringEscapeUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[StringEscapeUtils.type].getModifiers))
//  }

  @Test
  @throws[IOException]
  def testEscapeJava(): Unit = {
    assertNull(StringEscapeUtils.escapeJava(null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.ESCAPE_JAVA.translate(null, null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.ESCAPE_JAVA.translate("", null))
    assertEscapeJava("empty string", "", "")
    assertEscapeJava(StringEscapeUtilsTest.FOO, StringEscapeUtilsTest.FOO, StringEscapeUtilsTest.FOO)
    assertEscapeJava("tab", "\\t", "\t")
    assertEscapeJava("backslash", "\\\\", "\\")
    assertEscapeJava("single quote should not be escaped", "'", "'")
    assertEscapeJava("\\\\\\b\\t\\r", "\\\b\t\r")
    assertEscapeJava("\\u1234", "\u1234")
    assertEscapeJava("\\u0234", "\u0234")
    assertEscapeJava("\\u00EF", "\u00ef")
    assertEscapeJava("\\u0001", "\u0001")
    assertEscapeJava("Should use capitalized Unicode hex", "\\uABCD", "\uabcd")
    assertEscapeJava("He didn't say, \\\"stop!\\\"", "He didn't say, \"stop!\"")
    assertEscapeJava(
      "non-breaking space",
      "This space is non-breaking:" + "\\u00A0",
      "This space is non-breaking:\u00a0")
    assertEscapeJava("\\uABCD\\u1234\\u012C", "\uABCD\u1234\u012C")
  }

  /**
    * Tests https://issues.apache.org/jira/browse/LANG-421
    */
  @Test def testEscapeJavaWithSlash(): Unit = {
    val input = "String with a slash (/) in it"
    val expected = input
    val actual = StringEscapeUtils.escapeJava(input)
    /*
     * In 2.4 StringEscapeUtils.escapeJava(String) escapes '/' characters, which are not a valid character to escape
     * in a Java string.
     */
    assertEquals(expected, actual)
  }

  @throws[IOException]
  private def assertEscapeJava(escaped: String, original: String): Unit = {
    assertEscapeJava(null, escaped, original)
  }

  @throws[IOException]
  private def assertEscapeJava(message: String, expected: String, original: String): Unit = {
    val converted = StringEscapeUtils.escapeJava(original)

    val expectedMessage: String = "escapeJava(String) failed" +
      (if (message == null) "" else ": " + message)

    assertEquals(expectedMessage, expected, converted)
    val writer = new StringWriter
    StringEscapeUtils.ESCAPE_JAVA.translate(original, writer)
    assertEquals(expected, writer.toString)
  }

  @Test
  @throws[IOException]
  def testUnescapeJava(): Unit = {
    assertNull(StringEscapeUtils.unescapeJava(null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.UNESCAPE_JAVA.translate(null, null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.UNESCAPE_JAVA.translate("", null))
    assertThrows[RuntimeException](StringEscapeUtils.unescapeJava("\\u02-3"))
    assertUnescapeJava("", "")
    assertUnescapeJava("test", "test")
    assertUnescapeJava("\ntest\b", "\\ntest\\b")
    assertUnescapeJava("\u123425foo\ntest\b", "\\u123425foo\\ntest\\b")
    assertUnescapeJava("'\foo\teste\r", "\\'\\foo\\teste\\r")
    assertUnescapeJava("", "\\")
    //foo
    assertUnescapeJava("lowercase Unicode", "\uABCDx", "\\uabcdx")
    assertUnescapeJava("uppercase Unicode", "\uABCDx", "\\uABCDx")
    assertUnescapeJava("Unicode as final character", "\uABCD", "\\uabcd")
  }

  @throws[IOException]
  private def assertUnescapeJava(unescaped: String, original: String): Unit = {
    assertUnescapeJava(null, unescaped, original)
  }

  @throws[IOException]
  private def assertUnescapeJava(message: String, unescaped: String, original: String): Unit = {
    val expected = unescaped
    val actual = StringEscapeUtils.unescapeJava(original)
    val expectedMessage =
      "unescape(String) failed" +
        (if (message == null) "" else ": " + message) +
        ": expected '" + StringEscapeUtils.escapeJava(
        expected) + // we escape this so we can see it in the error message
        "' actual '" + StringEscapeUtils.escapeJava(actual) + "'"

    assertEquals(expectedMessage, expected, actual)
    val writer = new StringWriter
    StringEscapeUtils.UNESCAPE_JAVA.translate(original, writer)
    assertEquals(unescaped, writer.toString)
  }

  @Test def testEscapeEcmaScript(): Unit = {
    assertNull(StringEscapeUtils.escapeEcmaScript(null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.ESCAPE_ECMASCRIPT.translate(null, null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.ESCAPE_ECMASCRIPT.translate("", null))
    assertEquals("He didn\\'t say, \\\"stop!\\\"", StringEscapeUtils.escapeEcmaScript("He didn't say, \"stop!\""))
    assertEquals(
      "document.getElementById(\\\"test\\\").value = \\'<script>alert(\\'aaa\\');<\\/script>\\';",
      StringEscapeUtils.escapeEcmaScript("document.getElementById(\"test\").value = '<script>alert('aaa');</script>';")
    )
  }

  @Test def testUnescapeEcmaScript(): Unit = {
    assertNull(StringEscapeUtils.escapeEcmaScript(null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.UNESCAPE_ECMASCRIPT.translate(null, null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.UNESCAPE_ECMASCRIPT.translate("", null))
    assertEquals("He didn't say, \"stop!\"", StringEscapeUtils.unescapeEcmaScript("He didn\\'t say, \\\"stop!\\\""))
    assertEquals(
      "document.getElementById(\"test\").value = '<script>alert('aaa');</script>';",
      StringEscapeUtils.unescapeEcmaScript(
        "document.getElementById(\\\"test\\\").value = \\'<script>alert(\\'aaa\\');<\\/script>\\';")
    )
  }

  @Test
  @throws[IOException]
  def testEscapeHtml(): Unit = {
    for (element <- StringEscapeUtilsTest.HTML_ESCAPES) {
      val message = element(0)
      val expected = element(1)
      val original = element(2)
      assertEquals(message, expected, StringEscapeUtils.escapeHtml4(original))

      val sw = new StringWriter
      StringEscapeUtils.ESCAPE_HTML4.translate(original, sw)
      val actual =
        if (original == null) null
        else sw.toString

      assertEquals(message, expected, actual)
    }
  }

  @Test
  @throws[IOException]
  def testUnescapeHtml4(): Unit = {
    for (element <- StringEscapeUtilsTest.HTML_ESCAPES) {
      val message = element(0)
      val expected = element(2)
      val original = element(1)
      assertEquals(message, expected, StringEscapeUtils.unescapeHtml4(original))

      val sw = new StringWriter
      StringEscapeUtils.UNESCAPE_HTML4.translate(original, sw)

      val actual =
        if (original == null) null
        else sw.toString
      assertEquals(message, expected, actual)
    }
    // \u00E7 is a cedilla (c with wiggle under)
    // note that the test string must be 7-bit-clean (Unicode escaped) or else it will compile incorrectly
    // on some locales
    assertEquals("funny chars pass through OK", "Fran\u00E7ais", StringEscapeUtils.unescapeHtml4("Fran\u00E7ais"))
    assertEquals("Hello&;World", StringEscapeUtils.unescapeHtml4("Hello&;World"))
    assertEquals("Hello&#;World", StringEscapeUtils.unescapeHtml4("Hello&#;World"))
    assertEquals("Hello&# ;World", StringEscapeUtils.unescapeHtml4("Hello&# ;World"))
    assertEquals("Hello&##;World", StringEscapeUtils.unescapeHtml4("Hello&##;World"))
  }

  @Test def testUnescapeHexCharsHtml(): Unit = { // Simple easy to grok test
    assertEquals("hex number unescape", "\u0080\u009F", StringEscapeUtils.unescapeHtml4("&#x80;&#x9F;"))
    assertEquals("hex number unescape", "\u0080\u009F", StringEscapeUtils.unescapeHtml4("&#X80;&#X9F;"))

    // Test all Character values:
    var i: Int = Char.MinValue.toInt
    while (i < Char.MaxValue.toInt) {
      val c1 = Character.valueOf(i.toChar)
      val c2 = Character.valueOf((i + 1).toChar)
      val expected = c1.toString + c2.toString
      val escapedC1 = "&#x" + Integer.toHexString(c1.charValue) + ";"
      val escapedC2 = "&#x" + Integer.toHexString(c2.charValue) + ";"
      assertEquals("hex number unescape index " + i, expected, StringEscapeUtils.unescapeHtml4(escapedC1 + escapedC2))

      i += 1
    }
  }

  @Test def testUnescapeUnknownEntity(): Unit = {
    assertEquals("&zzzz;", StringEscapeUtils.unescapeHtml4("&zzzz;"))
  }

  @Test def testEscapeHtmlVersions(): Unit = {
    assertEquals("&Beta;", StringEscapeUtils.escapeHtml4("\u0392"))
    assertEquals("\u0392", StringEscapeUtils.unescapeHtml4("&Beta;"))
    // TODO: refine API for escaping/unescaping specific HTML versions
  }

  @Test
  @throws[Exception]
  def testEscapeXml(): Unit = {
    assertEquals("&lt;abc&gt;", StringEscapeUtils.escapeXml("<abc>"))
    assertEquals("<abc>", StringEscapeUtils.unescapeXml("&lt;abc&gt;"))
    assertEquals("XML should not escape >0x7f values", "\u00A1", StringEscapeUtils.escapeXml("\u00A1"))
    assertEquals("XML should be able to unescape >0x7f values", "\u00A0", StringEscapeUtils.unescapeXml("&#160;"))
    assertEquals(
      "XML should be able to unescape >0x7f values with one leading 0",
      "\u00A0",
      StringEscapeUtils.unescapeXml("&#0160;"))
    assertEquals(
      "XML should be able to unescape >0x7f values with two leading 0s",
      "\u00A0",
      StringEscapeUtils.unescapeXml("&#00160;"))
    assertEquals(
      "XML should be able to unescape >0x7f values with three leading 0s",
      "\u00A0",
      StringEscapeUtils.unescapeXml("&#000160;"))
    assertEquals("ain't", StringEscapeUtils.unescapeXml("ain&apos;t"))
    assertEquals("ain&apos;t", StringEscapeUtils.escapeXml("ain't"))
    assertEquals("", StringEscapeUtils.escapeXml(""))
    assertNull(StringEscapeUtils.escapeXml(null))
    assertNull(StringEscapeUtils.unescapeXml(null))

    var sw = new StringWriter
    StringEscapeUtils.ESCAPE_XML.translate("<abc>", sw)
    assertEquals("XML was escaped incorrectly", "&lt;abc&gt;", sw.toString)
    sw = new StringWriter
    StringEscapeUtils.UNESCAPE_XML.translate("&lt;abc&gt;", sw)
    assertEquals("XML was unescaped incorrectly", "<abc>", sw.toString)
  }

  @Test def testEscapeXml10(): Unit = {
    assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml10("a<b>c\"d'e&f"))
    assertEquals("XML 1.0 should not escape \t \n \r", "a\tb\rc\nd", StringEscapeUtils.escapeXml10("a\tb\rc\nd"))
    assertEquals(
      "XML 1.0 should omit most #x0-x8 | #xb | #xc | #xe-#x19",
      "ab",
      StringEscapeUtils.escapeXml10("a\u0000\u0001\u0008\u000b\u000c\u000e\u001fb"))
    assertEquals(
      "XML 1.0 should omit #xd800-#xdfff",
      "a\ud7ff  \ue000b",
      StringEscapeUtils.escapeXml10("a\ud7ff\ud800 \udfff \ue000b"))
    assertEquals(
      "XML 1.0 should omit #xfffe | #xffff",
      "a\ufffdb",
      StringEscapeUtils.escapeXml10("a\ufffd\ufffe\uffffb"))
    assertEquals(
      "XML 1.0 should escape #x7f-#x84 | #x86 - #x9f, for XML 1.1 compatibility",
      "a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b",
      StringEscapeUtils.escapeXml10("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b")
    )
  }

  @Test def testEscapeXml11(): Unit = {
    assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml11("a<b>c\"d'e&f"))
    assertEquals("XML 1.1 should not escape \t \n \r", "a\tb\rc\nd", StringEscapeUtils.escapeXml11("a\tb\rc\nd"))
    assertEquals("XML 1.1 should omit #x0", "ab", StringEscapeUtils.escapeXml11("a\u0000b"))
    assertEquals(
      "XML 1.1 should escape #x1-x8 | #xb | #xc | #xe-#x19",
      "a&#1;&#8;&#11;&#12;&#14;&#31;b",
      StringEscapeUtils.escapeXml11("a\u0001\u0008\u000b\u000c\u000e\u001fb")
    )
    assertEquals(
      "XML 1.1 should escape #x7F-#x84 | #x86-#x9F",
      "a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b",
      StringEscapeUtils.escapeXml11("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b")
    )
    assertEquals(
      "XML 1.1 should omit #xd800-#xdfff",
      "a\ud7ff  \ue000b",
      StringEscapeUtils.escapeXml11("a\ud7ff\ud800 \udfff \ue000b"))
    assertEquals(
      "XML 1.1 should omit #xfffe | #xffff",
      "a\ufffdb",
      StringEscapeUtils.escapeXml11("a\ufffd\ufffe\uffffb"))
  }

  /**
    * Tests Supplementary characters.
    * <p>
    * From http://www.w3.org/International/questions/qa-escapes
    * </p>
    * <blockquote>
    * Supplementary characters are those Unicode characters that have code points higher than the characters in
    * the Basic Multilingual Plane (BMP). In UTF-16 a supplementary character is encoded using two 16-bit surrogate code points from the
    * BMP. Because of this, some people think that supplementary characters need to be represented using two escapes, but this is incorrect
    * - you must use the single, code point value for that character. For example, use &amp;&#35;x233B4&#59; rather than
    * &amp;&#35;xD84C&#59;&amp;&#35;xDFB4&#59;.
    * </blockquote>
    *
    * @see <a href="http://www.w3.org/International/questions/qa-escapes">Using character escapes in markup and CSS</a>
    * @see <a href="https://issues.apache.org/jira/browse/LANG-728">LANG-728</a>
    */
  @Test def testEscapeXmlSupplementaryCharacters(): Unit = {
    val escapeXml = StringEscapeUtils.ESCAPE_XML.`with`(NumericEntityEscaper.between(0x7f, Integer.MAX_VALUE))
    assertEquals(
      "Supplementary character must be represented using a single escape",
      "&#144308;",
      escapeXml.translate("\uD84C\uDFB4"))
    assertEquals(
      "Supplementary characters mixed with basic characters should be encoded correctly",
      "a b c &#144308;",
      escapeXml.translate("a b c \uD84C\uDFB4"))
  }

  @Test def testEscapeXmlAllCharacters(): Unit = { // http://www.w3.org/TR/xml/#charsets says:
    // Char ::= #x9 | #xA | #xD | [#x20-#xD7FF] | [#xE000-#xFFFD] | [#x10000-#x10FFFF]
    // /* any Unicode character, excluding the surrogate blocks, FFFE, and FFFF. */
    val escapeXml =
      StringEscapeUtils.ESCAPE_XML
        .`with`(
          NumericEntityEscaper.below(9),
          NumericEntityEscaper.between(0xb, 0xc),
          NumericEntityEscaper.between(0xe, 0x19),
          NumericEntityEscaper.between(0xd800, 0xdfff),
          NumericEntityEscaper.between(0xfffe, 0xffff),
          NumericEntityEscaper.above(0x110000)
        )

    assertEquals(
      "&#0;&#1;&#2;&#3;&#4;&#5;&#6;&#7;&#8;",
      escapeXml.translate("\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\u0008"))
    assertEquals("\t", escapeXml.translate("\t")) // 0x9
    assertEquals("\n", escapeXml.translate("\n")) // 0xA

    assertEquals("&#11;&#12;", escapeXml.translate("\u000B\u000C"))
    assertEquals("\r", escapeXml.translate("\r")) // 0xD

    assertEquals("Hello World! Ain&apos;t this great?", escapeXml.translate("Hello World! Ain't this great?"))
    assertEquals("&#14;&#15;&#24;&#25;", escapeXml.translate("\u000E\u000F\u0018\u0019"))
  }

  /**
    * Reverse of the above.
    *
    * @see <a href="https://issues.apache.org/jira/browse/LANG-729">LANG-729</a>
    */
  @Test def testUnescapeXmlSupplementaryCharacters(): Unit = {
    assertEquals(
      "Supplementary character must be represented using a single escape",
      "\uD84C\uDFB4",
      StringEscapeUtils.unescapeXml("&#144308;"))
    assertEquals(
      "Supplementary characters mixed with basic characters should be decoded correctly",
      "a b c \uD84C\uDFB4",
      StringEscapeUtils.unescapeXml("a b c &#144308;")
    )
  }

  // Tests issue #38569
  // https://issues.apache.org/bugzilla/show_bug.cgi?id=38569
  @Test def testStandaloneAmphersand(): Unit = {
    assertEquals("<P&O>", StringEscapeUtils.unescapeHtml4("&lt;P&O&gt;"))
    assertEquals("test & <", StringEscapeUtils.unescapeHtml4("test & &lt;"))
    assertEquals("<P&O>", StringEscapeUtils.unescapeXml("&lt;P&O&gt;"))
    assertEquals("test & <", StringEscapeUtils.unescapeXml("test & &lt;"))
  }

  @Test def testLang313(): Unit = {
    assertEquals("& &", StringEscapeUtils.unescapeHtml4("& &amp;"))
  }

  @Test def testEscapeCsvString(): Unit = {
    assertEquals("foo.bar", StringEscapeUtils.escapeCsv("foo.bar"))
    assertEquals("\"foo,bar\"", StringEscapeUtils.escapeCsv("foo,bar"))
    assertEquals("\"foo\nbar\"", StringEscapeUtils.escapeCsv("foo\nbar"))
    assertEquals("\"foo\rbar\"", StringEscapeUtils.escapeCsv("foo\rbar"))
    assertEquals("\"foo\"\"bar\"", StringEscapeUtils.escapeCsv("foo\"bar"))
    assertEquals("foo\uD84C\uDFB4bar", StringEscapeUtils.escapeCsv("foo\uD84C\uDFB4bar"))
    assertEquals("", StringEscapeUtils.escapeCsv(""))
    assertNull(StringEscapeUtils.escapeCsv(null))
  }

  @Test
  @throws[Exception]
  def testEscapeCsvWriter(): Unit = {
    checkCsvEscapeWriter("foo.bar", "foo.bar")
    checkCsvEscapeWriter("\"foo,bar\"", "foo,bar")
    checkCsvEscapeWriter("\"foo\nbar\"", "foo\nbar")
    checkCsvEscapeWriter("\"foo\rbar\"", "foo\rbar")
    checkCsvEscapeWriter("\"foo\"\"bar\"", "foo\"bar")
    checkCsvEscapeWriter("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar")
    checkCsvEscapeWriter("", null)
    checkCsvEscapeWriter("", "")
  }

  @throws[IOException]
  private def checkCsvEscapeWriter(expected: String, value: String): Unit = {
    val writer = new StringWriter
    StringEscapeUtils.ESCAPE_CSV.translate(value, writer)
    assertEquals(expected, writer.toString)
  }

  @Test def testEscapeCsvIllegalStateException(): Unit = {
    val writer = new StringWriter
    assertThrows[IllegalStateException](StringEscapeUtils.ESCAPE_CSV.translate("foo", -1, writer))
    ()
  }

  @Test def testUnescapeCsvString(): Unit = {
    assertEquals("foo.bar", StringEscapeUtils.unescapeCsv("foo.bar"))
    assertEquals("foo,bar", StringEscapeUtils.unescapeCsv("\"foo,bar\""))
    assertEquals("foo\nbar", StringEscapeUtils.unescapeCsv("\"foo\nbar\""))
    assertEquals("foo\rbar", StringEscapeUtils.unescapeCsv("\"foo\rbar\""))
    assertEquals("foo\"bar", StringEscapeUtils.unescapeCsv("\"foo\"\"bar\""))
    assertEquals("foo\uD84C\uDFB4bar", StringEscapeUtils.unescapeCsv("foo\uD84C\uDFB4bar"))
    assertEquals("", StringEscapeUtils.unescapeCsv(""))
    assertNull(StringEscapeUtils.unescapeCsv(null))
    assertEquals("\"foo.bar\"", StringEscapeUtils.unescapeCsv("\"foo.bar\""))
  }

  @Test
  @throws[Exception]
  def testUnescapeCsvWriter(): Unit = {
    checkCsvUnescapeWriter("foo.bar", "foo.bar")
    checkCsvUnescapeWriter("foo,bar", "\"foo,bar\"")
    checkCsvUnescapeWriter("foo\nbar", "\"foo\nbar\"")
    checkCsvUnescapeWriter("foo\rbar", "\"foo\rbar\"")
    checkCsvUnescapeWriter("foo\"bar", "\"foo\"\"bar\"")
    checkCsvUnescapeWriter("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar")
    checkCsvUnescapeWriter("", null)
    checkCsvUnescapeWriter("", "")
    checkCsvUnescapeWriter("\"foo.bar\"", "\"foo.bar\"")
  }

  @throws[IOException]
  private def checkCsvUnescapeWriter(expected: String, value: String): Unit = {
    val writer = new StringWriter
    StringEscapeUtils.UNESCAPE_CSV.translate(value, writer)
    assertEquals(expected, writer.toString)
  }

  @Test def testUnescapeCsvIllegalStateException(): Unit = {
    val writer = new StringWriter
    assertThrows[IllegalStateException](StringEscapeUtils.UNESCAPE_CSV.translate("foo", -1, writer))
    ()
  }

  /**
    * @see <a href="https://issues.apache.org/jira/browse/LANG-480">LANG-480</a>
    */
  @Test def testEscapeHtmlHighUnicode(): Unit = { // this is the utf8 representation of the character:
    // COUNTING ROD UNIT DIGIT THREE
    // in Unicode
    // codepoint: U+1D362
    val data = Array[Byte](0xf0.toByte, 0x9d.toByte, 0x8d.toByte, 0xa2.toByte)
    val original = new String(data, StandardCharsets.UTF_8)
    val escaped = StringEscapeUtils.escapeHtml4(original)
    assertEquals("High Unicode should not have been escaped", original, escaped)
    val unescaped = StringEscapeUtils.unescapeHtml4(escaped)
    assertEquals("High Unicode should have been unchanged", original, unescaped)
    // TODO: I think this should hold, needs further investigation
    //        String unescapedFromEntity = StringEscapeUtils.unescapeHtml4( "&#119650;" );
    //        assertEquals( "High Unicode should have been unescaped", original, unescapedFromEntity);
  }

  /**
    * @see <a href="https://issues.apache.org/jira/browse/LANG-339">LANG-339</a>
    */
  @Test def testEscapeHiragana(): Unit = { // Some random Japanese Unicode characters
    val original = "\u304B\u304C\u3068"
    val escaped = StringEscapeUtils.escapeHtml4(original)
    assertEquals("Hiragana character Unicode behavior should not be being escaped by escapeHtml4", original, escaped)
    val unescaped = StringEscapeUtils.unescapeHtml4(escaped)
    assertEquals("Hiragana character Unicode behavior has changed - expected no unescaping", escaped, unescaped)
  }
//
//  /**
//    * @see <a href="https://issues.apache.org/jira/browse/LANG-708">LANG-708</a>
//    * @throws IOException if an I/O error occurs
//    */
//  @Test
//  @throws[IOException]
//  def testLang708(): Unit = {
//    val inputBytes = Files.readAllBytes(Paths.get("src/test/resources/lang-708-input.txt"))
//    val input = new String(inputBytes, StandardCharsets.UTF_8)
//    val escaped = StringEscapeUtils.escapeEcmaScript(input)
//    // just the end:
//    assertTrue(escaped, escaped.endsWith("}]"))
//    // a little more:
//    assertTrue(escaped, escaped.endsWith("\"valueCode\\\":\\\"\\\"}]"))
//  }

  /**
    * @see <a href="https://issues.apache.org/jira/browse/LANG-720">LANG-720</a>
    */
  @Test def testLang720(): Unit = {
    val input = "\ud842\udfb7" + "A"
    val escaped = StringEscapeUtils.escapeXml(input)
    assertEquals(input, escaped)
  }

  /**
    * @see <a href="https://issues.apache.org/jira/browse/LANG-911">LANG-911</a>
    */
  @Test def testLang911(): Unit = {
    val bellsTest = "\ud83d\udc80\ud83d\udd14"
    val value = StringEscapeUtils.escapeJava(bellsTest)
    val valueTest = StringEscapeUtils.unescapeJava(value)
    assertEquals(bellsTest, valueTest)
  }

  @Test def testEscapeJson(): Unit = {
    assertNull(StringEscapeUtils.escapeJson(null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.ESCAPE_JSON.translate(null, null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.ESCAPE_JSON.translate("", null))
    assertEquals("He didn't say, \\\"stop!\\\"", StringEscapeUtils.escapeJson("He didn't say, \"stop!\""))

    val expected = "\\\"foo\\\" isn't \\\"bar\\\". specials: \\b\\r\\n\\f\\t\\\\\\/"
    val input = "\"foo\" isn't \"bar\". specials: \b\r\n\f\t\\/"
    assertEquals(expected, StringEscapeUtils.escapeJson(input))
  }

  @Test def testUnescapeJson(): Unit = {
    assertNull(StringEscapeUtils.unescapeJson(null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.UNESCAPE_JSON.translate(null, null))
    assertThrows[IllegalArgumentException](StringEscapeUtils.UNESCAPE_JSON.translate("", null))
    assertEquals("He didn't say, \"stop!\"", StringEscapeUtils.unescapeJson("He didn't say, \\\"stop!\\\""))

    val expected = "\"foo\" isn't \"bar\". specials: \b\r\n\f\t\\/"
    val input = "\\\"foo\\\" isn't \\\"bar\\\". specials: \\b\\r\\n\\f\\t\\\\\\/"
    assertEquals(expected, StringEscapeUtils.unescapeJson(input))
  }
}
