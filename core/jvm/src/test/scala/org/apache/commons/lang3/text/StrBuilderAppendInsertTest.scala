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
//import org.junit.Assert.assertThrows
//import java.text.DecimalFormatSymbols
//import java.util
//import java.util.Collections
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests for {@link org.apache.commons.lang3.text.StrBuilder}.
//  */
//@deprecated object StrBuilderAppendInsertTest {
//  /** The system line separator. */
//    private val SEP = System.lineSeparator
//  /** Test subclass of Object, with a toString method. */
//  private val FOO = new Any() {
//    override def toString = "foo"
//  }
//}
//
//@deprecated class StrBuilderAppendInsertTest extends JUnitSuite { //-----------------------------------------------------------------------
//  @Test def testAppendNewLine() = {
//    var sb = new StrBuilder("---")
//    sb.appendNewLine.append("+++")
//    assertEquals("---" + StrBuilderAppendInsertTest.SEP + "+++", sb.toString)
//    sb = new StrBuilder("---")
//    sb.setNewLineText("#").appendNewLine.setNewLineText(null).appendNewLine
//    assertEquals("---#" + StrBuilderAppendInsertTest.SEP, sb.toString)
//  }
//
//  @Test def testAppendWithNullText() = {
//    val sb = new StrBuilder
//    sb.setNullText("NULL")
//    assertEquals("", sb.toString)
//    sb.appendNull
//    assertEquals("NULL", sb.toString)
//    sb.append(null.asInstanceOf[Any])
//    assertEquals("NULLNULL", sb.toString)
//    sb.append(StrBuilderAppendInsertTest.FOO)
//    assertEquals("NULLNULLfoo", sb.toString)
//    sb.append(null.asInstanceOf[String])
//    assertEquals("NULLNULLfooNULL", sb.toString)
//    sb.append("")
//    assertEquals("NULLNULLfooNULL", sb.toString)
//    sb.append("bar")
//    assertEquals("NULLNULLfooNULLbar", sb.toString)
//    sb.append(null.asInstanceOf[StringBuffer])
//    assertEquals("NULLNULLfooNULLbarNULL", sb.toString)
//    sb.append(new StringBuffer("baz"))
//    assertEquals("NULLNULLfooNULLbarNULLbaz", sb.toString)
//  }
//
//  @Test def testAppend_Object() = {
//    val sb = new StrBuilder
//    sb.appendNull
//    assertEquals("", sb.toString)
//    sb.append(null.asInstanceOf[Any])
//    assertEquals("", sb.toString)
//    sb.append(StrBuilderAppendInsertTest.FOO)
//    assertEquals("foo", sb.toString)
//    sb.append(null.asInstanceOf[StringBuffer])
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuffer("baz"))
//    assertEquals("foobaz", sb.toString)
//    sb.append(new StrBuilder("yes"))
//    assertEquals("foobazyes", sb.toString)
//    sb.append("Seq".asInstanceOf[CharSequence])
//    assertEquals("foobazyesSeq", sb.toString)
//    sb.append(new StringBuilder("bld")) // Check it supports StringBuilder
//    assertEquals("foobazyesSeqbld", sb.toString)
//  }
//
//  @Test def testAppend_StringBuilder() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[String])
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new StringBuilder("foo"))
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuilder(""))
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuilder("bar"))
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_String() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[String])
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append("foo")
//    assertEquals("foo", sb.toString)
//    sb.append("")
//    assertEquals("foo", sb.toString)
//    sb.append("bar")
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_String_int_int() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[String], 0, 1)
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append("foo", 0, 3)
//    assertEquals("foo", sb.toString)
//    val sb1 = sb
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append("bar", -1, 1), "append(char[], -1,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append("bar", 3, 1), "append(char[], 3,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append("bar", 1, -1), "append(char[],, -1) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append("bar", 1, 3), "append(char[], 1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append("bar", -1, 3), "append(char[], -1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append("bar", 4, 0), "append(char[], 4, 0) expected IndexOutOfBoundsException")
//    sb.append("bar", 3, 0)
//    assertEquals("foo", sb.toString)
//    sb.append("abcbardef", 3, 3)
//    assertEquals("foobar", sb.toString)
//    sb.append("abcbardef".asInstanceOf[CharSequence], 4, 3)
//    assertEquals("foobarard", sb.toString)
//  }
//
//  @Test def testAppend_StringBuilder_int_int() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[String], 0, 1)
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new StringBuilder("foo"), 0, 3)
//    assertEquals("foo", sb.toString)
//    val sb1 = sb
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuilder("bar"), -1, 1), "append(StringBuilder, -1,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuilder("bar"), 3, 1), "append(StringBuilder, 3,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuilder("bar"), 1, -1), "append(StringBuilder,, -1) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuilder("bar"), 1, 3), "append(StringBuilder, 1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuilder("bar"), -1, 3), "append(StringBuilder, -1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuilder("bar"), 4, 0), "append(StringBuilder, 4, 0) expected IndexOutOfBoundsException")
//    sb.append(new StringBuilder("bar"), 3, 0)
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuilder("abcbardef"), 3, 3)
//    assertEquals("foobar", sb.toString)
//    sb.append(new StringBuilder("abcbardef"), 4, 3)
//    assertEquals("foobarard", sb.toString)
//  }
//
//  @Test def testAppend_StringBuffer() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[StringBuffer])
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new StringBuffer("foo"))
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuffer(""))
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuffer("bar"))
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_StringBuffer_int_int() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[StringBuffer], 0, 1)
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new StringBuffer("foo"), 0, 3)
//    assertEquals("foo", sb.toString)
//    val sb1 = sb
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuffer("bar"), -1, 1), "append(char[], -1,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuffer("bar"), 3, 1), "append(char[], 3,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuffer("bar"), 1, -1), "append(char[],, -1) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuffer("bar"), 1, 3), "append(char[], 1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuffer("bar"), -1, 3), "append(char[], -1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StringBuffer("bar"), 4, 0), "append(char[], 4, 0) expected IndexOutOfBoundsException")
//    sb.append(new StringBuffer("bar"), 3, 0)
//    assertEquals("foo", sb.toString)
//    sb.append(new StringBuffer("abcbardef"), 3, 3)
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_StrBuilder() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[StrBuilder])
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new StrBuilder("foo"))
//    assertEquals("foo", sb.toString)
//    sb.append(new StrBuilder(""))
//    assertEquals("foo", sb.toString)
//    sb.append(new StrBuilder("bar"))
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_StrBuilder_int_int() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[StrBuilder], 0, 1)
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new StrBuilder("foo"), 0, 3)
//    assertEquals("foo", sb.toString)
//    val sb1 = sb
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StrBuilder("bar"), -1, 1), "append(char[], -1,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StrBuilder("bar"), 3, 1), "append(char[], 3,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StrBuilder("bar"), 1, -1), "append(char[],, -1) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StrBuilder("bar"), 1, 3), "append(char[], 1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StrBuilder("bar"), -1, 3), "append(char[], -1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(new StrBuilder("bar"), 4, 0), "append(char[], 4, 0) expected IndexOutOfBoundsException")
//    sb.append(new StrBuilder("bar"), 3, 0)
//    assertEquals("foo", sb.toString)
//    sb.append(new StrBuilder("abcbardef"), 3, 3)
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_CharArray() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[Array[Char]])
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(new Array[Char](0))
//    assertEquals("", sb.toString)
//    sb.append(Array[Char]('f', 'o', 'o'))
//    assertEquals("foo", sb.toString)
//  }
//
//  @Test def testAppend_CharArray_int_int() = {
//    var sb = new StrBuilder
//    sb.setNullText("NULL").append(null.asInstanceOf[Array[Char]], 0, 1)
//    assertEquals("NULL", sb.toString)
//    sb = new StrBuilder
//    sb.append(Array[Char]('f', 'o', 'o'), 0, 3)
//    assertEquals("foo", sb.toString)
//    val sb1 = sb
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(Array[Char]('b', 'a', 'r'), -1, 1), "append(char[], -1,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(Array[Char]('b', 'a', 'r'), 3, 1), "append(char[], 3,) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(Array[Char]('b', 'a', 'r'), 1, -1), "append(char[],, -1) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(Array[Char]('b', 'a', 'r'), 1, 3), "append(char[], 1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(Array[Char]('b', 'a', 'r'), -1, 3), "append(char[], -1, 3) expected IndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb1.append(Array[Char]('b', 'a', 'r'), 4, 0), "append(char[], 4, 0) expected IndexOutOfBoundsException")
//    sb.append(Array[Char]('b', 'a', 'r'), 3, 0)
//    assertEquals("foo", sb.toString)
//    sb.append(Array[Char]('a', 'b', 'c', 'b', 'a', 'r', 'd', 'e', 'f'), 3, 3)
//    assertEquals("foobar", sb.toString)
//  }
//
//  @Test def testAppend_Boolean() = {
//    val sb = new StrBuilder
//    sb.append(true)
//    assertEquals("true", sb.toString)
//    sb.append(false)
//    assertEquals("truefalse", sb.toString)
//    sb.append('!')
//    assertEquals("truefalse!", sb.toString)
//  }
//
//  @Test def testAppend_PrimitiveNumber() = {
//    val sb = new StrBuilder
//    sb.append(0)
//    assertEquals("0", sb.toString)
//    sb.append(1L)
//    assertEquals("01", sb.toString)
//    sb.append(2.3f)
//    assertEquals("012.3", sb.toString)
//    sb.append(4.5d)
//    assertEquals("012.34.5", sb.toString)
//  }
//
//  @Test def testAppendln_FormattedString() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: String) = {
//        count(0) += 1
//        super.append(str)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln("Hello %s", "Alice")
//    assertEquals("Hello Alice" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(2, count(0)) // appendNewLine() calls append(String)
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_Object() = {
//    val sb = new StrBuilder
//    sb.appendln(null.asInstanceOf[Any])
//    assertEquals("" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    sb.appendln(StrBuilderAppendInsertTest.FOO)
//    assertEquals(StrBuilderAppendInsertTest.SEP + "foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    sb.appendln(Integer.valueOf(6))
//    assertEquals(StrBuilderAppendInsertTest.SEP + "foo" + StrBuilderAppendInsertTest.SEP + "6" + StrBuilderAppendInsertTest.SEP, sb.toString)
//  }
//
//  @Test def testAppendln_String() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: String) = {
//        count(0) += 1
//        super.append(str)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln("foo")
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(2, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_String_int_int() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: String, startIndex: Int, length: Int) = {
//        count(0) += 1
//        super.append(str, startIndex, length)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln("foo", 0, 3)
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_StringBuffer() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: StringBuffer) = {
//        count(0) += 1
//        super.append(str)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln(new StringBuffer("foo"))
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_StringBuilder() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: StringBuilder) = {
//        count(0) += 1
//        super.append(str)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln(new StringBuilder("foo"))
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_StringBuffer_int_int() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: StringBuffer, startIndex: Int, length: Int) = {
//        count(0) += 1
//        super.append(str, startIndex, length)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln(new StringBuffer("foo"), 0, 3)
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_StringBuilder_int_int() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: StringBuilder, startIndex: Int, length: Int) = {
//        count(0) += 1
//        super.append(str, startIndex, length)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln(new StringBuilder("foo"), 0, 3)
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_StrBuilder() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: StrBuilder) = {
//        count(0) += 1
//        super.append(str)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln(new StrBuilder("foo"))
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_StrBuilder_int_int() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: StrBuilder, startIndex: Int, length: Int) = {
//        count(0) += 1
//        super.append(str, startIndex, length)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln(new StrBuilder("foo"), 0, 3)
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_CharArray() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: Array[Char]) = {
//        count(0) += 1
//        super.append(str)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln("foo".toCharArray)
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_CharArray_int_int() = {
//    val count = new Array[Int](2)
//    val sb = new StrBuilder() {
//      override def append(str: Array[Char], startIndex: Int, length: Int) = {
//        count(0) += 1
//        super.append(str, startIndex, length)
//      }
//
//      override
//
//      def appendNewLine = {
//        count(1) += 1
//        super.appendNewLine
//      }
//    }
//    sb.appendln("foo".toCharArray, 0, 3)
//    assertEquals("foo" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    assertEquals(1, count(0))
//    assertEquals(1, count(1))
//  }
//
//  @Test def testAppendln_Boolean() = {
//    val sb = new StrBuilder
//    sb.appendln(true)
//    assertEquals("true" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    sb.clear
//    sb.appendln(false)
//    assertEquals("false" + StrBuilderAppendInsertTest.SEP, sb.toString)
//  }
//
//  @Test def testAppendln_PrimitiveNumber() = {
//    val sb = new StrBuilder
//    sb.appendln(0)
//    assertEquals("0" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    sb.clear
//    sb.appendln(1L)
//    assertEquals("1" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    sb.clear
//    sb.appendln(2.3f)
//    assertEquals("2.3" + StrBuilderAppendInsertTest.SEP, sb.toString)
//    sb.clear
//    sb.appendln(4.5d)
//    assertEquals("4.5" + StrBuilderAppendInsertTest.SEP, sb.toString)
//  }
//
//  @Test def testAppendPadding() = {
//    val sb = new StrBuilder
//    sb.append("foo")
//    assertEquals("foo", sb.toString)
//    sb.appendPadding(-1, '-')
//    assertEquals("foo", sb.toString)
//    sb.appendPadding(0, '-')
//    assertEquals("foo", sb.toString)
//    sb.appendPadding(1, '-')
//    assertEquals("foo-", sb.toString)
//    sb.appendPadding(16, '-')
//    assertEquals(20, sb.length)
//    //            12345678901234567890
//    assertEquals("foo-----------------", sb.toString)
//  }
//
//  @Test def testAppendFixedWidthPadLeft() = {
//    val sb = new StrBuilder
//    sb.appendFixedWidthPadLeft("foo", -1, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft("foo", 0, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft("foo", 1, '-')
//    assertEquals("o", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft("foo", 2, '-')
//    assertEquals("oo", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft("foo", 3, '-')
//    assertEquals("foo", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft("foo", 4, '-')
//    assertEquals("-foo", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft("foo", 10, '-')
//    assertEquals(10, sb.length)
//    //            1234567890
//    assertEquals("-------foo", sb.toString)
//    sb.clear
//    sb.setNullText("null")
//    sb.appendFixedWidthPadLeft(null, 5, '-')
//    assertEquals("-null", sb.toString)
//  }
//
//  @Test def testAppendFixedWidthPadLeft_int() = {
//    val sb = new StrBuilder
//    sb.appendFixedWidthPadLeft(123, -1, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft(123, 0, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft(123, 1, '-')
//    assertEquals("3", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft(123, 2, '-')
//    assertEquals("23", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft(123, 3, '-')
//    assertEquals("123", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft(123, 4, '-')
//    assertEquals("-123", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadLeft(123, 10, '-')
//    assertEquals(10, sb.length)
//    assertEquals("-------123", sb.toString)
//  }
//
//  @Test def testAppendFixedWidthPadRight() = {
//    val sb = new StrBuilder
//    sb.appendFixedWidthPadRight("foo", -1, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight("foo", 0, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight("foo", 1, '-')
//    assertEquals("f", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight("foo", 2, '-')
//    assertEquals("fo", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight("foo", 3, '-')
//    assertEquals("foo", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight("foo", 4, '-')
//    assertEquals("foo-", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight("foo", 10, '-')
//    assertEquals(10, sb.length)
//    assertEquals("foo-------", sb.toString)
//    sb.clear
//    sb.setNullText("null")
//    sb.appendFixedWidthPadRight(null, 5, '-')
//    assertEquals("null-", sb.toString)
//  }
//
//  // See: https://issues.apache.org/jira/browse/LANG-299
//  @Test def testLang299() = {
//    val sb = new StrBuilder(1)
//    sb.appendFixedWidthPadRight("foo", 1, '-')
//    assertEquals("f", sb.toString)
//  }
//
//  @Test def testAppendFixedWidthPadRight_int() = {
//    val sb = new StrBuilder
//    sb.appendFixedWidthPadRight(123, -1, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight(123, 0, '-')
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight(123, 1, '-')
//    assertEquals("1", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight(123, 2, '-')
//    assertEquals("12", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight(123, 3, '-')
//    assertEquals("123", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight(123, 4, '-')
//    assertEquals("123-", sb.toString)
//    sb.clear
//    sb.appendFixedWidthPadRight(123, 10, '-')
//    assertEquals(10, sb.length)
//    assertEquals("123-------", sb.toString)
//  }
//
//  @Test def testAppend_FormattedString() = {
//    var sb = null
//    sb = new StrBuilder
//    sb.append("Hi", null.asInstanceOf[Array[AnyRef]])
//    assertEquals("Hi", sb.toString)
//    sb = new StrBuilder
//    sb.append("Hi", "Alice")
//    assertEquals("Hi", sb.toString)
//    sb = new StrBuilder
//    sb.append("Hi %s", "Alice")
//    assertEquals("Hi Alice", sb.toString)
//    sb = new StrBuilder
//    sb.append("Hi %s %,d", "Alice", 5000)
//    // group separator depends on system locale
//    val groupingSeparator = DecimalFormatSymbols.getInstance.getGroupingSeparator
//    val expected = "Hi Alice 5" + groupingSeparator + "000"
//    assertEquals(expected, sb.toString)
//  }
//
//  @Test def testAppendAll_Array() = {
//    val sb = new StrBuilder
//    sb.appendAll(null.asInstanceOf[Array[AnyRef]])
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendAll
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendAll("foo", "bar", "baz")
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.appendAll("foo", "bar", "baz")
//    assertEquals("foobarbaz", sb.toString)
//  }
//
//  @Test def testAppendAll_Collection() = {
//    val sb = new StrBuilder
//    sb.appendAll(null.asInstanceOf[util.Collection[_]])
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendAll(Collections.EMPTY_LIST)
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendAll(util.Arrays.asList("foo", "bar", "baz"))
//    assertEquals("foobarbaz", sb.toString)
//  }
//
//  @Test def testAppendAll_Iterator() = {
//    val sb = new StrBuilder
//    sb.appendAll(null.asInstanceOf[util.Iterator[_]])
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendAll(Collections.EMPTY_LIST.iterator)
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendAll(util.Arrays.asList("foo", "bar", "baz").iterator)
//    assertEquals("foobarbaz", sb.toString)
//  }
//
//  @Test def testAppendWithSeparators_Array() = {
//    val sb = new StrBuilder
//    sb.appendWithSeparators(null.asInstanceOf[Array[AnyRef]], ",")
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(new Array[AnyRef](0), ",")
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(Array[AnyRef]("foo", "bar", "baz"), ",")
//    assertEquals("foo,bar,baz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(Array[AnyRef]("foo", "bar", "baz"), null)
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(Array[AnyRef]("foo", null, "baz"), ",")
//    assertEquals("foo,,baz", sb.toString)
//  }
//
//  @Test def testAppendWithSeparators_Collection() = {
//    val sb = new StrBuilder
//    sb.appendWithSeparators(null.asInstanceOf[util.Collection[_]], ",")
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(Collections.EMPTY_LIST, ",")
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", "bar", "baz"), ",")
//    assertEquals("foo,bar,baz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", "bar", "baz"), null)
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", null, "baz"), ",")
//    assertEquals("foo,,baz", sb.toString)
//  }
//
//  @Test def testAppendWithSeparators_Iterator() = {
//    val sb = new StrBuilder
//    sb.appendWithSeparators(null.asInstanceOf[util.Iterator[_]], ",")
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(Collections.EMPTY_LIST.iterator, ",")
//    assertEquals("", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", "bar", "baz").iterator, ",")
//    assertEquals("foo,bar,baz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", "bar", "baz").iterator, null)
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", null, "baz").iterator, ",")
//    assertEquals("foo,,baz", sb.toString)
//  }
//
//  @Test def testAppendWithSeparatorsWithNullText() = {
//    val sb = new StrBuilder
//    sb.setNullText("null")
//    sb.appendWithSeparators(Array[AnyRef]("foo", null, "baz"), ",")
//    assertEquals("foo,null,baz", sb.toString)
//    sb.clear
//    sb.appendWithSeparators(util.Arrays.asList("foo", null, "baz"), ",")
//    assertEquals("foo,null,baz", sb.toString)
//  }
//
//  @Test def testAppendSeparator_String() = {
//    val sb = new StrBuilder
//    sb.appendSeparator(",") // no effect
//    assertEquals("", sb.toString)
//    sb.append("foo")
//    assertEquals("foo", sb.toString)
//    sb.appendSeparator(",")
//    assertEquals("foo,", sb.toString)
//  }
//
//  @Test def testAppendSeparator_String_String() = {
//    val sb = new StrBuilder
//    val startSeparator = "order by "
//    val standardSeparator = ","
//    val foo = "foo"
//    sb.appendSeparator(null, null)
//    assertEquals("", sb.toString)
//    sb.appendSeparator(standardSeparator, null)
//    assertEquals("", sb.toString)
//    sb.appendSeparator(standardSeparator, startSeparator)
//    assertEquals(startSeparator, sb.toString)
//    sb.appendSeparator(null, null)
//    assertEquals(startSeparator, sb.toString)
//    sb.appendSeparator(null, startSeparator)
//    assertEquals(startSeparator, sb.toString)
//    sb.append(foo)
//    assertEquals(startSeparator + foo, sb.toString)
//    sb.appendSeparator(standardSeparator, startSeparator)
//    assertEquals(startSeparator + foo + standardSeparator, sb.toString)
//  }
//
//  @Test def testAppendSeparator_char() = {
//    val sb = new StrBuilder
//    sb.appendSeparator(',')
//    assertEquals("", sb.toString)
//    sb.append("foo")
//    assertEquals("foo", sb.toString)
//    sb.appendSeparator(',')
//    assertEquals("foo,", sb.toString)
//  }
//
//  @Test def testAppendSeparator_char_char() = {
//    val sb = new StrBuilder
//    val startSeparator = ':'
//    val standardSeparator = ','
//    val foo = "foo"
//    sb.appendSeparator(standardSeparator, startSeparator)
//    assertEquals(String.valueOf(startSeparator), sb.toString)
//    sb.append(foo)
//    assertEquals(String.valueOf(startSeparator) + foo, sb.toString)
//    sb.appendSeparator(standardSeparator, startSeparator)
//    assertEquals(String.valueOf(startSeparator) + foo + standardSeparator, sb.toString)
//  }
//
//  @Test def testAppendSeparator_String_int() = {
//    val sb = new StrBuilder
//    sb.appendSeparator(",", 0)
//    assertEquals("", sb.toString)
//    sb.append("foo")
//    assertEquals("foo", sb.toString)
//    sb.appendSeparator(",", 1)
//    assertEquals("foo,", sb.toString)
//    sb.appendSeparator(",", -1)
//    assertEquals("foo,", sb.toString)
//  }
//
//  @Test def testAppendSeparator_char_int() = {
//    val sb = new StrBuilder
//    sb.appendSeparator(',', 0)
//    assertEquals("", sb.toString)
//    sb.append("foo")
//    assertEquals("foo", sb.toString)
//    sb.appendSeparator(',', 1)
//    assertEquals("foo,", sb.toString)
//    sb.appendSeparator(',', -1)
//    assertEquals("foo,", sb.toString)
//  }
//
//  @Test def testInsert() = {
//    val sb = new StrBuilder
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, StrBuilderAppendInsertTest.FOO), "insert(-1, Object) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, StrBuilderAppendInsertTest.FOO), "insert(7, Object) expected StringIndexOutOfBoundsException")
//    sb.insert(0, null.asInstanceOf[Any])
//    assertEquals("barbaz", sb.toString)
//    sb.insert(0, StrBuilderAppendInsertTest.FOO)
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, "foo"), "insert(-1, String) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, "foo"), "insert(7, String) expected StringIndexOutOfBoundsException")
//    sb.insert(0, null.asInstanceOf[String])
//    assertEquals("barbaz", sb.toString)
//    sb.insert(0, "foo")
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, Array[Char]('f', 'o', 'o')), "insert(-1, char[]) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, Array[Char]('f', 'o', 'o')), "insert(7, char[]) expected StringIndexOutOfBoundsException")
//    sb.insert(0, null.asInstanceOf[Array[Char]])
//    assertEquals("barbaz", sb.toString)
//    sb.insert(0, new Array[Char](0))
//    assertEquals("barbaz", sb.toString)
//    sb.insert(0, Array[Char]('f', 'o', 'o'))
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 3, 3), "insert(-1, char[], 3, 3) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 3, 3), "insert(7, char[], 3, 3) expected StringIndexOutOfBoundsException")
//    sb.insert(0, null, 0, 0)
//    assertEquals("barbaz", sb.toString)
//    sb.insert(0, new Array[Char](0), 0, 0)
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(0, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), -1, 3), "insert(0, char[], -1, 3) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(0, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 10, 3), "insert(0, char[], 10, 3) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(0, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 0, -1), "insert(0, char[], 0, -1) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(0, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 0, 10), "insert(0, char[], 0, 10) expected StringIndexOutOfBoundsException")
//    sb.insert(0, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 0, 0)
//    assertEquals("barbaz", sb.toString)
//    sb.insert(0, Array[Char]('a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'), 3, 3)
//    assertEquals("foobarbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, true), "insert(-1, boolean) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, true), "insert(7, boolean) expected StringIndexOutOfBoundsException")
//    sb.insert(0, true)
//    assertEquals("truebarbaz", sb.toString)
//    sb.insert(0, false)
//    assertEquals("falsetruebarbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, '!'), "insert(-1, char) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, '!'), "insert(7, char) expected StringIndexOutOfBoundsException")
//    sb.insert(0, '!')
//    assertEquals("!barbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, 0), "insert(-1, int) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, 0), "insert(7, int) expected StringIndexOutOfBoundsException")
//    sb.insert(0, '0')
//    assertEquals("0barbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, 1L), "insert(-1, long) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, 1L), "insert(7, long) expected StringIndexOutOfBoundsException")
//    sb.insert(0, 1L)
//    assertEquals("1barbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, 2.3F), "insert(-1, float) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, 2.3F), "insert(7, float) expected StringIndexOutOfBoundsException")
//    sb.insert(0, 2.3F)
//    assertEquals("2.3barbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, 4.5D), "insert(-1, double) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, 4.5D), "insert(7, double) expected StringIndexOutOfBoundsException")
//    sb.insert(0, 4.5D)
//    assertEquals("4.5barbaz", sb.toString)
//  }
//
//  @Test def testInsertWithNullText() = {
//    val sb = new StrBuilder
//    sb.setNullText("null")
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, StrBuilderAppendInsertTest.FOO), "insert(-1, Object) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, StrBuilderAppendInsertTest.FOO), "insert(7, Object) expected StringIndexOutOfBoundsException")
//    sb.insert(0, null.asInstanceOf[Any])
//    assertEquals("nullbarbaz", sb.toString)
//    sb.insert(0, StrBuilderAppendInsertTest.FOO)
//    assertEquals("foonullbarbaz", sb.toString)
//    sb.clear
//    sb.append("barbaz")
//    assertEquals("barbaz", sb.toString)
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(-1, "foo"), "insert(-1, String) expected StringIndexOutOfBoundsException")
//    assertThrows(classOf[IndexOutOfBoundsException], () => sb.insert(7, "foo"), "insert(7, String) expected StringIndexOutOfBoundsException")
//    sb.insert(0, null.asInstanceOf[String])
//    assertEquals("nullbarbaz", sb.toString)
//    sb.insert(0, "foo")
//    assertEquals("foonullbarbaz", sb.toString)
//    sb.insert(0, null.asInstanceOf[Array[Char]])
//    assertEquals("nullfoonullbarbaz", sb.toString)
//    sb.insert(0, null, 0, 0)
//    assertEquals("nullnullfoonullbarbaz", sb.toString)
//  }
//}
