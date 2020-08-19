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
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotSame
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util
//import java.util.Collections
//import java.util.NoSuchElementException
//import org.apache.commons.lang3.ArrayUtils
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit test for Tokenizer.
//  */
//@deprecated object StrTokenizerTest {
//  private val CSV_SIMPLE_FIXTURE = "A,b,c"
//  private val TSV_SIMPLE_FIXTURE = "A\tb\tc"
//}
//
//@deprecated class StrTokenizerTest extends JUnitSuite {
//  private def checkClone(tokenizer: StrTokenizer) = {
//    assertNotSame(StrTokenizer.getCSVInstance, tokenizer)
//    assertNotSame(StrTokenizer.getTSVInstance, tokenizer)
//  }
//
//  // -----------------------------------------------------------------------
//  @Test def test1() = {
//    val input = "a;b;c;\"d;\"\"e\";f; ; ;  "
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterChar(';')
//    tok.setQuoteChar('"')
//    tok.setIgnoredMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", "c", "d;\"e", "f", "", "", "")
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def test2() = {
//    val input = "a;b;c ;\"d;\"\"e\";f; ; ;"
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterChar(';')
//    tok.setQuoteChar('"')
//    tok.setIgnoredMatcher(StrMatcher.noneMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", "c ", "d;\"e", "f", " ", " ", "")
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def test3() = {
//    val input = "a;b; c;\"d;\"\"e\";f; ; ;"
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterChar(';')
//    tok.setQuoteChar('"')
//    tok.setIgnoredMatcher(StrMatcher.noneMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", " c", "d;\"e", "f", " ", " ", "")
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def test4() = {
//    val input = "a;b; c;\"d;\"\"e\";f; ; ;"
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterChar(';')
//    tok.setQuoteChar('"')
//    tok.setIgnoredMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(true)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", "c", "d;\"e", "f")
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def test5() = {
//    val input = "a;b; c;\"d;\"\"e\";f; ; ;"
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterChar(';')
//    tok.setQuoteChar('"')
//    tok.setIgnoredMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", "c", "d;\"e", "f", null, null, null)
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def test6() = {
//    val input = "a;b; c;\"d;\"\"e\";f; ; ;"
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterChar(';')
//    tok.setQuoteChar('"')
//    tok.setIgnoredMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    // tok.setTreatingEmptyAsNull(true);
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", " c", "d;\"e", "f", null, null, null)
//    var nextCount = 0
//    while ( {
//      tok.hasNext
//    }) {
//      tok.next
//      nextCount += 1
//    }
//    var prevCount = 0
//    while ( {
//      tok.hasPrevious
//    }) {
//      tok.previous
//      prevCount += 1
//    }
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    assertEquals(nextCount, expected.length, "could not cycle through entire token list" + " using the 'hasNext' and 'next' methods")
//    assertEquals(prevCount, expected.length, "could not cycle through entire token list" + " using the 'hasPrevious' and 'previous' methods")
//  }
//
//  @Test def test7() = {
//    val input = "a   b c \"d e\" f "
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterMatcher(StrMatcher.spaceMatcher)
//    tok.setQuoteMatcher(StrMatcher.doubleQuoteMatcher)
//    tok.setIgnoredMatcher(StrMatcher.noneMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "", "", "b", "c", "d e", "f", "")
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def test8() = {
//    val input = "a   b c \"d e\" f "
//    val tok = new StrTokenizer(input)
//    tok.setDelimiterMatcher(StrMatcher.spaceMatcher)
//    tok.setQuoteMatcher(StrMatcher.doubleQuoteMatcher)
//    tok.setIgnoredMatcher(StrMatcher.noneMatcher)
//    tok.setIgnoreEmptyTokens(true)
//    val tokens = tok.getTokenArray
//    val expected = Array[String]("a", "b", "c", "d e", "f")
//    assertEquals(expected.length, tokens.length, ArrayUtils.toString(tokens))
//    for (i <- 0 until expected.length) {
//      assertEquals(expected(i), tokens(i), "token[" + i + "] was '" + tokens(i) + "' but was expected to be '" + expected(i) + "'")
//    }
//  }
//
//  @Test def testBasic1() = {
//    val input = "a  b c"
//    val tok = new StrTokenizer(input)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasic2() = {
//    val input = "a \nb\fc"
//    val tok = new StrTokenizer(input)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasic3() = {
//    val input = "a \nb\u0001\fc"
//    val tok = new StrTokenizer(input)
//    assertEquals("a", tok.next)
//    assertEquals("b\u0001", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasic4() = {
//    val input = "a \"b\" c"
//    val tok = new StrTokenizer(input)
//    assertEquals("a", tok.next)
//    assertEquals("\"b\"", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasic5() = {
//    val input = "a:b':c"
//    val tok = new StrTokenizer(input, ':', '\'')
//    assertEquals("a", tok.next)
//    assertEquals("b'", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicDelim1() = {
//    val input = "a:b:c"
//    val tok = new StrTokenizer(input, ':')
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicDelim2() = {
//    val input = "a:b:c"
//    val tok = new StrTokenizer(input, ',')
//    assertEquals("a:b:c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicEmpty1() = {
//    val input = "a  b c"
//    val tok = new StrTokenizer(input)
//    tok.setIgnoreEmptyTokens(false)
//    assertEquals("a", tok.next)
//    assertEquals("", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicEmpty2() = {
//    val input = "a  b c"
//    val tok = new StrTokenizer(input)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertNull(tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted1() = {
//    val input = "a 'b' c"
//    val tok = new StrTokenizer(input, ' ', '\'')
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted2() = {
//    val input = "a:'b':"
//    val tok = new StrTokenizer(input, ':', '\'')
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted3() = {
//    val input = "a:'b''c'"
//    val tok = new StrTokenizer(input, ':', '\'')
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("b'c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted4() = {
//    val input = "a: 'b' 'c' :d"
//    val tok = new StrTokenizer(input, ':', '\'')
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("b c", tok.next)
//    assertEquals("d", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted5() = {
//    val input = "a: 'b'x'c' :d"
//    val tok = new StrTokenizer(input, ':', '\'')
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("bxc", tok.next)
//    assertEquals("d", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted6() = {
//    val input = "a:'b'\"c':d"
//    val tok = new StrTokenizer(input, ':')
//    tok.setQuoteMatcher(StrMatcher.quoteMatcher)
//    assertEquals("a", tok.next)
//    assertEquals("b\"c:d", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuoted7() = {
//    val input = "a:\"There's a reason here\":b"
//    val tok = new StrTokenizer(input, ':')
//    tok.setQuoteMatcher(StrMatcher.quoteMatcher)
//    assertEquals("a", tok.next)
//    assertEquals("There's a reason here", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicQuotedTrimmed1() = {
//    val input = "a: 'b' :"
//    val tok = new StrTokenizer(input, ':', '\'')
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicTrimmed1() = {
//    val input = "a: b :  "
//    val tok = new StrTokenizer(input, ':')
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicTrimmed2() = {
//    val input = "a:  b  :"
//    val tok = new StrTokenizer(input, ':')
//    tok.setTrimmerMatcher(StrMatcher.stringMatcher("  "))
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicIgnoreTrimmed1() = {
//    val input = "a: bIGNOREc : "
//    val tok = new StrTokenizer(input, ':')
//    tok.setIgnoredMatcher(StrMatcher.stringMatcher("IGNORE"))
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("bc", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicIgnoreTrimmed2() = {
//    val input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE "
//    val tok = new StrTokenizer(input, ':')
//    tok.setIgnoredMatcher(StrMatcher.stringMatcher("IGNORE"))
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("bc", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicIgnoreTrimmed3() = {
//    val input = "IGNOREaIGNORE: IGNORE bIGNOREc IGNORE : IGNORE "
//    val tok = new StrTokenizer(input, ':')
//    tok.setIgnoredMatcher(StrMatcher.stringMatcher("IGNORE"))
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("  bc  ", tok.next)
//    assertEquals("  ", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testBasicIgnoreTrimmed4() = {
//    val input = "IGNOREaIGNORE: IGNORE 'bIGNOREc'IGNORE'd' IGNORE : IGNORE "
//    val tok = new StrTokenizer(input, ':', '\'')
//    tok.setIgnoredMatcher(StrMatcher.stringMatcher("IGNORE"))
//    tok.setTrimmerMatcher(StrMatcher.trimMatcher)
//    tok.setIgnoreEmptyTokens(false)
//    tok.setEmptyTokenAsNull(true)
//    assertEquals("a", tok.next)
//    assertEquals("bIGNOREcd", tok.next)
//    assertNull(tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  //-----------------------------------------------------------------------
//  @Test def testListArray() = {
//    val input = "a  b c"
//    val tok = new StrTokenizer(input)
//    val array = tok.getTokenArray
//    val list = tok.getTokenList
//    assertEquals(util.Arrays.asList(array), list)
//    assertEquals(3, list.size)
//  }
//
//  private def testCSV(data: String) = {
//    this.testXSVAbc(StrTokenizer.getCSVInstance(data))
//    this.testXSVAbc(StrTokenizer.getCSVInstance(data.toCharArray))
//  }
//
//  @Test def testCSVEmpty() = {
//    this.testEmpty(StrTokenizer.getCSVInstance)
//    this.testEmpty(StrTokenizer.getCSVInstance(""))
//  }
//
//  @Test def testCSVSimple() = this.testCSV(StrTokenizerTest.CSV_SIMPLE_FIXTURE)
//
//  @Test def testCSVSimpleNeedsTrim() = {
//    this.testCSV("   " + StrTokenizerTest.CSV_SIMPLE_FIXTURE)
//    this.testCSV("   \n\t  " + StrTokenizerTest.CSV_SIMPLE_FIXTURE)
//    this.testCSV("   \n  " + StrTokenizerTest.CSV_SIMPLE_FIXTURE + "\n\n\r")
//  }
//
//  private[text] def testEmpty(tokenizer: StrTokenizer) = {
//    this.checkClone(tokenizer)
//    assertFalse(tokenizer.hasNext)
//    assertFalse(tokenizer.hasPrevious)
//    assertNull(tokenizer.nextToken)
//    assertEquals(0, tokenizer.size)
//    assertThrows(classOf[NoSuchElementException], tokenizer.next)
//  }
//
//  @Test def testGetContent() = {
//    val input = "a   b c \"d e\" f "
//    var tok = new StrTokenizer(input)
//    assertEquals(input, tok.getContent)
//    tok = new StrTokenizer(input.toCharArray)
//    assertEquals(input, tok.getContent)
//    tok = new StrTokenizer
//    assertNull(tok.getContent)
//  }
//
//  @Test def testChaining() = {
//    val tok = new StrTokenizer
//    assertEquals(tok, tok.reset)
//    assertEquals(tok, tok.reset(""))
//    assertEquals(tok, tok.reset(new Array[Char](0)))
//    assertEquals(tok, tok.setDelimiterChar(' '))
//    assertEquals(tok, tok.setDelimiterString(" "))
//    assertEquals(tok, tok.setDelimiterMatcher(null))
//    assertEquals(tok, tok.setQuoteChar(' '))
//    assertEquals(tok, tok.setQuoteMatcher(null))
//    assertEquals(tok, tok.setIgnoredChar(' '))
//    assertEquals(tok, tok.setIgnoredMatcher(null))
//    assertEquals(tok, tok.setTrimmerMatcher(null))
//    assertEquals(tok, tok.setEmptyTokenAsNull(false))
//    assertEquals(tok, tok.setIgnoreEmptyTokens(false))
//  }
//
//  /**
//    * Tests that the {@link StrTokenizer# clone ( )} clone method catches {@link CloneNotSupportedException} and returns
//    * {@code null}.
//    */
//  @Test def testCloneNotSupportedException() = {
//    val notCloned = new StrTokenizer() {
//      @throws[CloneNotSupportedException]
//      override private[text] def cloneReset = throw new CloneNotSupportedException("test")
//    }.clone
//    assertNull(notCloned)
//  }
//
//  @Test def testCloneNull() = {
//    val tokenizer = new StrTokenizer(null.asInstanceOf[Array[Char]])
//    // Start sanity check
//    assertNull(tokenizer.nextToken)
//    tokenizer.reset
//    assertNull(tokenizer.nextToken)
//    // End sanity check
//    val clonedTokenizer = tokenizer.clone.asInstanceOf[StrTokenizer]
//    tokenizer.reset
//    assertNull(tokenizer.nextToken)
//    assertNull(clonedTokenizer.nextToken)
//  }
//
//  @Test def testCloneReset() = {
//    val input = Array[Char]('a')
//    val tokenizer = new StrTokenizer(input)
//    assertEquals("a", tokenizer.nextToken)
//    tokenizer.reset(input)
//    assertEquals("a", tokenizer.nextToken)
//    val clonedTokenizer = tokenizer.clone.asInstanceOf[StrTokenizer]
//    input(0) = 'b'
//    tokenizer.reset(input)
//    assertEquals("b", tokenizer.nextToken)
//    assertEquals("a", clonedTokenizer.nextToken)
//  }
//
//  @Test def testConstructor_String() = {
//    var tok = new StrTokenizer("a b")
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer("")
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(null.asInstanceOf[String])
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testConstructor_String_char() = {
//    var tok = new StrTokenizer("a b", ' ')
//    assertEquals(1, tok.getDelimiterMatcher.isMatch(" ".toCharArray, 0, 0, 1))
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer("", ' ')
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(null.asInstanceOf[String], ' ')
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testConstructor_String_char_char() = {
//    var tok = new StrTokenizer("a b", ' ', '"')
//    assertEquals(1, tok.getDelimiterMatcher.isMatch(" ".toCharArray, 0, 0, 1))
//    assertEquals(1, tok.getQuoteMatcher.isMatch("\"".toCharArray, 0, 0, 1))
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer("", ' ', '"')
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(null.asInstanceOf[String], ' ', '"')
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testConstructor_charArray() = {
//    var tok = new StrTokenizer("a b".toCharArray)
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(new Array[Char](0))
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(null.asInstanceOf[Array[Char]])
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testConstructor_charArray_char() = {
//    var tok = new StrTokenizer("a b".toCharArray, ' ')
//    assertEquals(1, tok.getDelimiterMatcher.isMatch(" ".toCharArray, 0, 0, 1))
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(new Array[Char](0), ' ')
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(null.asInstanceOf[Array[Char]], ' ')
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testConstructor_charArray_char_char() = {
//    var tok = new StrTokenizer("a b".toCharArray, ' ', '"')
//    assertEquals(1, tok.getDelimiterMatcher.isMatch(" ".toCharArray, 0, 0, 1))
//    assertEquals(1, tok.getQuoteMatcher.isMatch("\"".toCharArray, 0, 0, 1))
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(new Array[Char](0), ' ', '"')
//    assertFalse(tok.hasNext)
//    tok = new StrTokenizer(null.asInstanceOf[Array[Char]], ' ', '"')
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testReset() = {
//    val tok = new StrTokenizer("a b c")
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//    tok.reset
//    assertEquals("a", tok.next)
//    assertEquals("b", tok.next)
//    assertEquals("c", tok.next)
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testReset_String() = {
//    val tok = new StrTokenizer("x x x")
//    tok.reset("d e")
//    assertEquals("d", tok.next)
//    assertEquals("e", tok.next)
//    assertFalse(tok.hasNext)
//    tok.reset(null.asInstanceOf[String])
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testReset_charArray() = {
//    val tok = new StrTokenizer("x x x")
//    val array = Array[Char]('a', 'b', 'c')
//    tok.reset(array)
//    assertEquals("abc", tok.next)
//    assertFalse(tok.hasNext)
//    tok.reset(null.asInstanceOf[Array[Char]])
//    assertFalse(tok.hasNext)
//  }
//
//  @Test def testTSV() = {
//    this.testXSVAbc(StrTokenizer.getTSVInstance(StrTokenizerTest.TSV_SIMPLE_FIXTURE))
//    this.testXSVAbc(StrTokenizer.getTSVInstance(StrTokenizerTest.TSV_SIMPLE_FIXTURE.toCharArray))
//  }
//
//  @Test def testTSVEmpty() = {
//    this.testEmpty(StrTokenizer.getTSVInstance)
//    this.testEmpty(StrTokenizer.getTSVInstance(""))
//  }
//
//  private[text] def testXSVAbc(tokenizer: StrTokenizer) = {
//    this.checkClone(tokenizer)
//    assertEquals(-1, tokenizer.previousIndex)
//    assertEquals(0, tokenizer.nextIndex)
//    assertNull(tokenizer.previousToken)
//    assertEquals("A", tokenizer.nextToken)
//    assertEquals(1, tokenizer.nextIndex)
//    assertEquals("b", tokenizer.nextToken)
//    assertEquals(2, tokenizer.nextIndex)
//    assertEquals("c", tokenizer.nextToken)
//    assertEquals(3, tokenizer.nextIndex)
//    assertNull(tokenizer.nextToken)
//    assertEquals(3, tokenizer.nextIndex)
//    assertEquals("c", tokenizer.previousToken)
//    assertEquals(2, tokenizer.nextIndex)
//    assertEquals("b", tokenizer.previousToken)
//    assertEquals(1, tokenizer.nextIndex)
//    assertEquals("A", tokenizer.previousToken)
//    assertEquals(0, tokenizer.nextIndex)
//    assertNull(tokenizer.previousToken)
//    assertEquals(0, tokenizer.nextIndex)
//    assertEquals(-1, tokenizer.previousIndex)
//    assertEquals(3, tokenizer.size)
//  }
//
//  @Test def testIteration() = {
//    val tkn = new StrTokenizer("a b c")
//    assertFalse(tkn.hasPrevious)
//    assertThrows(classOf[NoSuchElementException], tkn.previous)
//    assertTrue(tkn.hasNext)
//    assertEquals("a", tkn.next)
//    assertThrows(classOf[UnsupportedOperationException], tkn.remove)
//    assertThrows(classOf[UnsupportedOperationException], () => tkn.set("x"))
//    assertThrows(classOf[UnsupportedOperationException], () => tkn.add("y"))
//    assertTrue(tkn.hasPrevious)
//    assertTrue(tkn.hasNext)
//    assertEquals("b", tkn.next)
//    assertTrue(tkn.hasPrevious)
//    assertTrue(tkn.hasNext)
//    assertEquals("c", tkn.next)
//    assertTrue(tkn.hasPrevious)
//    assertFalse(tkn.hasNext)
//    assertThrows(classOf[NoSuchElementException], tkn.next)
//    assertTrue(tkn.hasPrevious)
//    assertFalse(tkn.hasNext)
//  }
//
//  @Test def testTokenizeSubclassInputChange() = {
//    val tkn = new StrTokenizer("a b c d e") {
//      override protected def tokenize(chars: Array[Char], offset: Int, count: Int) = super.tokenize("w x y z".toCharArray, 2, 5)
//    }
//    assertEquals("x", tkn.next)
//    assertEquals("y", tkn.next)
//  }
//
//  @Test def testTokenizeSubclassOutputChange() = {
//    val tkn = new StrTokenizer("a b c") {
//      override protected def tokenize(chars: Array[Char], offset: Int, count: Int) = {
//        val list = super.tokenize(chars, offset, count)
//        Collections.reverse(list)
//        list
//      }
//    }
//    assertEquals("c", tkn.next)
//    assertEquals("b", tkn.next)
//    assertEquals("a", tkn.next)
//  }
//
//  @Test def testToString() = {
//    val tkn = new StrTokenizer("a b c d e")
//    assertEquals("StrTokenizer[not tokenized yet]", tkn.toString)
//    tkn.next
//    assertEquals("StrTokenizer[a, b, c, d, e]", tkn.toString)
//  }
//}
