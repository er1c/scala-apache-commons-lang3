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
//import java.util
//import java.util.Properties
//import org.apache.commons.lang3.mutable.MutableObject
//import org.junit.{After, Before, Test}
//import org.junit.Assert._
//
//
///**
//  * Test class for StrSubstitutor.
//  */
//@deprecated class StrSubstitutorTest {
//  private var values: util.Map[String, String] = null
//
//  @Before def setUp(): Unit = {
//    values = new util.HashMap[String, String]
//    values.put("animal", "quick brown fox")
//    values.put("target", "lazy dog")
//    ()
//  }
//
//  @After def tearDown(): Unit = values = null
//
//  /**
//    * Tests simple key replace.
//    */
//  @Test def testReplaceSimple(): Unit =
//    doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true)
//
//  @Test def testReplaceSolo(): Unit = doTestReplace("quick brown fox", "${animal}", false)
//
//  /**
//    * Tests replace with no variables.
//    */
//  @Test def testReplaceNoVariables(): Unit = doTestNoReplace("The balloon arrived.")
//
//  /**
//    * Tests replace with null.
//    */
//  @Test def testReplaceNull(): Unit = doTestNoReplace(null)
//
//  @Test def testReplaceEmpty(): Unit = doTestNoReplace("")
//
//  /**
//    * Tests key replace changing map after initialization (not recommended).
//    */
//  @Test def testReplaceChangedMap(): Unit = {
//    values.put("target", "moon")
//    val sub = new StrSubstitutor(values)
//    assertEquals("The quick brown fox jumps over the moon.", sub.replace("The ${animal} jumps over the ${target}."))
//  }
//
//  /**
//    * Tests unknown key replace.
//    */
//  @Test def testReplaceUnknownKey(): Unit = {
//    doTestReplace(f"The $${person} jumps over the lazy dog.", f"The $${person} jumps over the $${target}.", true)
//    doTestReplace(
//      f"The $${person} jumps over the lazy dog. 1234567890.",
//      f"The $${person} jumps over the $${target}. $${undefined.number:-1234567890}.",
//      true)
//  }
//
//  /**
//    * Tests adjacent keys.
//    */
//  @Test def testReplaceAdjacentAtStart(): Unit = {
//    values.put("code", "GBP")
//    values.put("amount", "12.50")
//    val sub = new StrSubstitutor(values)
//    assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"))
//  }
//
//  @Test def testReplaceAdjacentAtEnd(): Unit = {
//    values.put("code", "GBP")
//    values.put("amount", "12.50")
//    val sub = new StrSubstitutor(values)
//    assertEquals("Amount is GBP12.50", sub.replace("Amount is ${code}${amount}"))
//  }
//
//  /**
//    * Tests simple recursive replace.
//    */
//  @Test def testReplaceRecursive(): Unit = {
//    values.put("animal", "${critter}")
//    values.put("target", "${pet}")
//    values.put("pet", "${petCharacteristic} dog")
//    values.put("petCharacteristic", "lazy")
//    values.put("critter", "${critterSpeed} ${critterColor} ${critterType}")
//    values.put("critterSpeed", "quick")
//    values.put("critterColor", "brown")
//    values.put("critterType", "fox")
//    doTestReplace("The quick brown fox jumps over the lazy dog.", f"The $${animal} jumps over the $${target}.", true)
//    values.put("pet", f"$${petCharacteristicUnknown:-lazy} dog")
//    doTestReplace("The quick brown fox jumps over the lazy dog.", f"The $${animal} jumps over the $${target}.", true)
//  }
//
//  /**
//    * Tests escaping.
//    */
//  @Test def testReplaceEscaping(): Unit =
//    doTestReplace(f"The $${animal} jumps over the lazy dog.", f"The $$$${animal} jumps over the $${target}.", true)
//
//  @Test def testReplaceSoloEscaping(): Unit = doTestReplace(f"$${animal}", f"$$$${animal}", false)
//
//  /**
//    * Tests complex escaping.
//    */
//  @Test def testReplaceComplexEscaping(): Unit = {
//    doTestReplace(
//      f"The $${quick brown fox} jumps over the lazy dog.",
//      f"The $$$${$${animal}} jumps over the $${target}.",
//      true)
//    doTestReplace(
//      f"The $${quick brown fox} jumps over the lazy dog. $${1234567890}.",
//      f"The $$$${$${animal}} jumps over the $${target}. $$$${$${undefined.number:-1234567890}}.",
//      true
//    )
//  }
//
//  /**
//    * Tests when no prefix or suffix.
//    */
//  @Test def testReplaceNoPrefixNoSuffix(): Unit =
//    doTestReplace("The animal jumps over the lazy dog.", f"The animal jumps over the $${target}.", true)
//
//  /**
//    * Tests when no incomplete prefix.
//    */
//  @Test def testReplaceIncompletePrefix(): Unit =
//    doTestReplace("The {animal} jumps over the lazy dog.", f"The {animal} jumps over the $${target}.", true)
//
//  /**
//    * Tests when prefix but no suffix.
//    */
//  @Test def testReplacePrefixNoSuffix(): Unit =
//    doTestReplace(
//      f"The $${animal jumps over the $${target} lazy dog.",
//      f"The $${animal jumps over the $${target} $${target}.",
//      true)
//
//  /**
//    * Tests when suffix but no prefix.
//    */
//  @Test def testReplaceNoPrefixSuffix(): Unit =
//    doTestReplace("The animal} jumps over the lazy dog.", f"The animal} jumps over the $${target}.", true)
//
//  /**
//    * Tests when no variable name.
//    */
//  @Test def testReplaceEmptyKeys(): Unit = {
//    doTestReplace(f"The $${} jumps over the lazy dog.", f"The $${} jumps over the $${target}.", true)
//    doTestReplace("The animal jumps over the lazy dog.", f"The $${:-animal} jumps over the $${target}.", true)
//  }
//
//  /**
//    * Tests replace creates output same as input.
//    */
//  @Test def testReplaceToIdentical(): Unit = {
//    values.put("animal", f"$$$${$${thing}}")
//    values.put("thing", "animal")
//    doTestReplace(f"The $${animal} jumps.", f"The $${animal} jumps.", true)
//  }
//
//  /**
//    * Tests a cyclic replace operation.
//    * The cycle should be detected and cause an exception to be thrown.
//    */
//  @Test def testCyclicReplacement(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("animal", f"$${critter}")
//    map.put("target", f"$${pet}")
//    map.put("pet", f"$${petCharacteristic} dog")
//    map.put("petCharacteristic", "lazy")
//    map.put("critter", f"$${critterSpeed} $${critterColor} $${critterType}")
//    map.put("critterSpeed", "quick")
//    map.put("critterColor", "brown")
//    map.put("critterType", f"$${animal}")
//    val sub = new StrSubstitutor(map)
//    assertThrows[IllegalStateException](
//      sub.replace(f"The $${animal} jumps over the $${target}.")
//    ) //, "Cyclic replacement was not detected!")
//
//    // also check even when default value is set.
//    map.put("critterType", f"$${animal:-fox}")
//    val sub2 = new StrSubstitutor(map)
//    assertThrows[IllegalStateException](
//      sub2.replace(f"The $${animal} jumps over the $${target}.")
//    ) //, "Cyclic replacement was not detected!")
//    ()
//  }
//
//  /**
//    * Tests interpolation with weird boundary patterns.
//    */
//  @Test def testReplaceWeirdPattens(): Unit = {
//    doTestNoReplace("")
//    doTestNoReplace(f"$${}")
//    doTestNoReplace(f"$${ }")
//    doTestNoReplace(f"$${\t}")
//    doTestNoReplace(f"$${\n}")
//    doTestNoReplace(f"$${\b}")
//    doTestNoReplace(f"$${")
//    doTestNoReplace(f"$$}")
//    doTestNoReplace("}")
//    doTestNoReplace(f"$${}$$")
//    doTestNoReplace(f"$${$${")
//    doTestNoReplace(f"$${$${}}")
//    doTestNoReplace(f"$${$$$${}}")
//    doTestNoReplace(f"$${$$$$$${}}")
//    doTestNoReplace(f"$${$$$$$${$$}}")
//    doTestNoReplace(f"$${$${}}")
//    doTestNoReplace(f"$${$${ }}")
//  }
//
//  @Test def testReplacePartialString_noReplace(): Unit = {
//    val sub = new StrSubstitutor
//    assertEquals("${animal} jumps", sub.replace("The ${animal} jumps over the ${target}.", 4, 15))
//  }
//
//  /**
//    * Tests whether a variable can be replaced in a variable name.
//    */
//  @Test def testReplaceInVariable(): Unit = {
//    values.put("animal.1", "fox")
//    values.put("animal.2", "mouse")
//    values.put("species", "2")
//    var sub = new StrSubstitutor(values)
//    sub.setEnableSubstitutionInVariables(true)
//    assertEquals(
//      "Wrong result (1)",
//      "The mouse jumps over the lazy dog.",
//      sub.replace(f"The $${animal.$${species}} jumps over the $${target}.")
//    )
//    values.put("species", "1")
//    sub = new StrSubstitutor(values)
//    assertEquals(
//      "Wrong result (2)",
//      "The fox jumps over the lazy dog.",
//      sub.replace(f"The $${animal.$${species}} jumps over the $${target}.")
//    )
//    assertEquals(
//      "Wrong result (3)",
//      "The fox jumps over the lazy dog.",
//      sub.replace(f"The $${unknown.animal.$${unknown.species:-1}:-fox} jumps over the $${unknown.target:-lazy dog}.")
//    )
//  }
//
//  /**
//    * Tests whether substitution in variable names is disabled per default.
//    */
//  @Test def testReplaceInVariableDisabled(): Unit = {
//    values.put("animal.1", "fox")
//    values.put("animal.2", "mouse")
//    values.put("species", "2")
//    val sub = new StrSubstitutor(values)
//    assertEquals(
//      "Wrong result (1)",
//      f"The $${animal.$${species}} jumps over the lazy dog.",
//      sub.replace(f"The $${animal.$${species}} jumps over the $${target}.")
//    )
//    assertEquals(
//      "Wrong result (2)",
//      f"The $${animal.$${species:-1}} jumps over the lazy dog.",
//      sub.replace(f"The $${animal.$${species:-1}} jumps over the $${target}.")
//    )
//  }
//
//  /**
//    * Tests complex and recursive substitution in variable names.
//    */
//  @Test def testReplaceInVariableRecursive(): Unit = {
//    values.put("animal.2", "brown fox")
//    values.put("animal.1", "white mouse")
//    values.put("color", "white")
//    values.put("species.white", "1")
//    values.put("species.brown", "2")
//    val sub = new StrSubstitutor(values)
//    sub.setEnableSubstitutionInVariables(true)
//    assertEquals(
//      "Wrong result (1)",
//      "The white mouse jumps over the lazy dog.",
//      sub.replace(f"The $${animal.$${species.$${color}}} jumps over the $${target}.")
//    )
//    assertEquals(
//      "Wrong result (2)",
//      "The brown fox jumps over the lazy dog.",
//      sub.replace(f"The $${animal.$${species.$${unknownColor:-brown}}} jumps over the $${target}.")
//    )
//  }
//
//  @Test def testDefaultValueDelimiters(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("animal", "fox")
//    map.put("target", "dog")
//    var sub = new StrSubstitutor(map, "${", "}", '$')
//    assertEquals(
//      "The fox jumps over the lazy dog. 1234567890.",
//      sub.replace(f"The $${animal} jumps over the lazy $${target}. $${undefined.number:-1234567890}."))
//    sub = new StrSubstitutor(map, "${", "}", '$', "?:")
//    assertEquals(
//      "The fox jumps over the lazy dog. 1234567890.",
//      sub.replace(f"The $${animal} jumps over the lazy $${target}. $${undefined.number?:1234567890}."))
//    sub = new StrSubstitutor(map, "${", "}", '$', "||")
//    assertEquals(
//      "The fox jumps over the lazy dog. 1234567890.",
//      sub.replace(f"The $${animal} jumps over the lazy $${target}. $${undefined.number||1234567890}."))
//    sub = new StrSubstitutor(map, "${", "}", '$', "!")
//    assertEquals(
//      "The fox jumps over the lazy dog. 1234567890.",
//      sub.replace(f"The $${animal} jumps over the lazy $${target}. $${undefined.number!1234567890}."))
//    sub = new StrSubstitutor(map, "${", "}", '$', "")
//    sub.setValueDelimiterMatcher(null)
//    assertEquals(
//      f"The fox jumps over the lazy dog. $${undefined.number!1234567890}.",
//      sub.replace(f"The $${animal} jumps over the lazy $${target}. $${undefined.number!1234567890}.")
//    )
//    sub = new StrSubstitutor(map, "${", "}", '$')
//    sub.setValueDelimiterMatcher(null)
//    assertEquals(
//      f"The fox jumps over the lazy dog. $${undefined.number!1234567890}.",
//      sub.replace(f"The $${animal} jumps over the lazy $${target}. $${undefined.number!1234567890}.")
//    )
//  }
//
//  /**
//    * Tests protected.
//    */
//  @Test def testResolveVariable(): Unit = {
//    val builder = new StrBuilder("Hi ${name}!")
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    val sub = new StrSubstitutor(map) {
//      override protected def resolveVariable(
//        variableName: String,
//        buf: StrBuilder,
//        startPos: Int,
//        endPos: Int): String = {
//        assertEquals("name", variableName)
//        assertSame(builder, buf)
//        assertEquals(3, startPos)
//        assertEquals(10, endPos)
//        "jakarta"
//      }
//    }
//    sub.replaceIn(builder)
//    assertEquals("Hi jakarta!", builder.toString)
//  }
//
//  /**
//    * Tests constructor.
//    */
//  @Test def testConstructorNoArgs(): Unit = {
//    val sub = new StrSubstitutor
//    assertEquals("Hi ${name}", sub.replace("Hi ${name}"))
//  }
//
//  @Test def testConstructorMapPrefixSuffix(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    val sub = new StrSubstitutor(map, "<", ">")
//    assertEquals("Hi < commons", sub.replace("Hi $< <name>"))
//  }
//
//  @Test def testConstructorMapFull(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    var sub = new StrSubstitutor(map, "<", ">", '!')
//    assertEquals("Hi < commons", sub.replace("Hi !< <name>"))
//    sub = new StrSubstitutor(map, "<", ">", '!', "||")
//    assertEquals("Hi < commons", sub.replace("Hi !< <name2||commons>"))
//  }
//
//  /**
//    * Tests get set.
//    */
//  @Test def testGetSetEscape(): Unit = {
//    val sub = new StrSubstitutor
//    assertEquals('$', sub.getEscapeChar)
//    sub.setEscapeChar('<')
//    assertEquals('<', sub.getEscapeChar)
//  }
//
//  @Test def testGetSetPrefix(): Unit = {
//    val sub = new StrSubstitutor
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    sub.setVariablePrefix('<')
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.CharMatcher])
//    sub.setVariablePrefix("<<")
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    assertThrows[IllegalArgumentException](sub.setVariablePrefix(null))
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    val matcher = StrMatcher.commaMatcher
//    sub.setVariablePrefixMatcher(matcher)
//    assertSame(matcher, sub.getVariablePrefixMatcher)
//    assertThrows[IllegalArgumentException](sub.setVariablePrefixMatcher(null))
//    assertSame(matcher, sub.getVariablePrefixMatcher)
//  }
//
//  @Test def testGetSetSuffix(): Unit = {
//    val sub = new StrSubstitutor
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    sub.setVariableSuffix('<')
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.CharMatcher])
//    sub.setVariableSuffix("<<")
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    assertThrows[IllegalArgumentException](sub.setVariableSuffix(null))
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    val matcher = StrMatcher.commaMatcher
//    sub.setVariableSuffixMatcher(matcher)
//    assertSame(matcher, sub.getVariableSuffixMatcher)
//    assertThrows[IllegalArgumentException](sub.setVariableSuffixMatcher(null))
//    assertSame(matcher, sub.getVariableSuffixMatcher)
//  }
//
//  @Test def testGetSetValueDelimiter(): Unit = {
//    val sub = new StrSubstitutor
//    assertTrue(sub.getValueDelimiterMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    sub.setValueDelimiter(':')
//    assertTrue(sub.getValueDelimiterMatcher.isInstanceOf[StrMatcher.CharMatcher])
//    sub.setValueDelimiter("||")
//    assertTrue(sub.getValueDelimiterMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    sub.setValueDelimiter(null)
//    assertNull(sub.getValueDelimiterMatcher)
//    val matcher = StrMatcher.commaMatcher
//    sub.setValueDelimiterMatcher(matcher)
//    assertSame(matcher, sub.getValueDelimiterMatcher)
//    sub.setValueDelimiterMatcher(null)
//    assertNull(sub.getValueDelimiterMatcher)
//  }
//
//  /**
//    * Tests static.
//    */
//  @Test def testStaticReplace(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    assertEquals("Hi commons!", StrSubstitutor.replace("Hi ${name}!", map))
//  }
//
//  @Test def testStaticReplacePrefixSuffix(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"))
//  }
//
//  /**
//    * Tests interpolation with system properties.
//    */
//  @Test def testStaticReplaceSystemProperties(): Unit = {
//    val buf = new StrBuilder
//    buf.append("Hi ").append(System.getProperty("user.name"))
//    buf.append(", you are working with ")
//    buf.append(System.getProperty("os.name"))
//    buf.append(", your home directory is ")
//    buf.append(System.getProperty("user.home")).append('.')
//    assertEquals(
//      buf.toString,
//      StrSubstitutor.replaceSystemProperties(
//        f"Hi $${user.name}, you are " + f"working with $${os.name}, your home " + f"directory is $${user.home}.")
//    )
//  }
//
//  /**
//    * Test for LANG-1055: StrSubstitutor.replaceSystemProperties does not work consistently
//    */
//  @Test def testLANG1055(): Unit = {
//    System.setProperty("test_key", "test_value")
//    val expected = StrSubstitutor.replace("test_key=${test_key}", System.getProperties)
//    val actual = StrSubstitutor.replaceSystemProperties("test_key=${test_key}")
//    assertEquals(expected, actual)
//  }
//
//  /**
//    * Test the replace of a properties object
//    */
//  @Test def testSubstituteDefaultProperties(): Unit = {
//    val org = "${doesnotwork}"
//    System.setProperty("doesnotwork", "It works!")
//    // create a new Properties object with the System.getProperties as default
//    val props = new Properties(System.getProperties)
//    assertEquals("It works!", StrSubstitutor.replace(org, props))
//  }
//
//  @Test def testSamePrefixAndSuffix(): Unit = {
//    val map = new util.HashMap[String, String]
//    map.put("greeting", "Hello")
//    map.put(" there ", "XXX")
//    map.put("name", "commons")
//    assertEquals("Hi commons!", StrSubstitutor.replace("Hi @name@!", map, "@", "@"))
//    assertEquals("Hello there commons!", StrSubstitutor.replace("@greeting@ there @name@!", map, "@", "@"))
//  }
//
//  @Test def testSubstitutePreserveEscape(): Unit = {
//    val org = f"$${not-escaped} $$$${escaped}"
//    val map = new util.HashMap[String, String]
//    map.put("not-escaped", "value")
//    val sub = new StrSubstitutor(map, "${", "}", '$')
//    assertFalse(sub.isPreserveEscapes)
//    assertEquals(f"value $${escaped}", sub.replace(org))
//    sub.setPreserveEscapes(true)
//    assertTrue(sub.isPreserveEscapes)
//    assertEquals(f"value $$$${escaped}", sub.replace(org))
//  }
//
//  //-----------------------------------------------------------------------
//  private def doTestReplace(expectedResult: String, replaceTemplate: String, substring: Boolean): Unit = {
//    val expectedShortResult = expectedResult.substring(1, expectedResult.length - 1)
//    val sub = new StrSubstitutor(values)
//    // replace using String
//    assertEquals(expectedResult, sub.replace(replaceTemplate))
//    if (substring) assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length - 2))
//    // replace using char[]
//    val chars = replaceTemplate.toCharArray
//    assertEquals(expectedResult, sub.replace(chars))
//    if (substring) assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2))
//    // replace using StringBuffer
//    var buf = new StringBuffer(replaceTemplate)
//    assertEquals(expectedResult, sub.replace(buf))
//    if (substring) assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length - 2))
//    // replace using StringBuilder
//    var builder = new StringBuilder(replaceTemplate)
//    assertEquals(expectedResult, sub.replace(builder))
//    if (substring) assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length - 2))
//    // replace using StrBuilder
//    var bld = new StrBuilder(replaceTemplate)
//    assertEquals(expectedResult, sub.replace(bld))
//    if (substring) assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length - 2))
//    // replace using object
//    val obj = new MutableObject[String](replaceTemplate) // toString returns template
//    assertEquals(expectedResult, sub.replace(obj))
//    // replace in StringBuffer
//    buf = new StringBuffer(replaceTemplate)
//    assertTrue(sub.replaceIn(buf))
//    assertEquals(expectedResult, buf.toString)
//    if (substring) {
//      buf = new StringBuffer(replaceTemplate)
//      assertTrue(sub.replaceIn(buf, 1, buf.length - 2))
//      assertEquals(expectedResult, buf.toString) // expect full result as remainder is untouched
//    }
//    // replace in StringBuilder
//    builder = new StringBuilder(replaceTemplate)
//    assertTrue(sub.replaceIn(builder))
//    assertEquals(expectedResult, builder.toString)
//    if (substring) {
//      builder = new StringBuilder(replaceTemplate)
//      assertTrue(sub.replaceIn(builder, 1, builder.length - 2))
//      assertEquals(expectedResult, builder.toString)
//    }
//    // replace in StrBuilder
//    bld = new StrBuilder(replaceTemplate)
//    assertTrue(sub.replaceIn(bld))
//    assertEquals(expectedResult, bld.toString)
//    if (substring) {
//      bld = new StrBuilder(replaceTemplate)
//      assertTrue(sub.replaceIn(bld, 1, bld.length - 2))
//      assertEquals(expectedResult, bld.toString)
//    }
//  }
//
//  private def doTestNoReplace(replaceTemplate: String): Unit = {
//    val sub = new StrSubstitutor(values)
//    if (replaceTemplate == null) {
//      assertNull(sub.replace(null.asInstanceOf[String]))
//      assertNull(sub.replace(null.asInstanceOf[String], 0, 100))
//      assertNull(sub.replace(null.asInstanceOf[Array[Char]]))
//      assertNull(sub.replace(null.asInstanceOf[Array[Char]], 0, 100))
//      assertNull(sub.replace(null.asInstanceOf[StringBuffer]))
//      assertNull(sub.replace(null.asInstanceOf[StringBuffer], 0, 100))
//      assertNull(sub.replace(null.asInstanceOf[StrBuilder]))
//      assertNull(sub.replace(null.asInstanceOf[StrBuilder], 0, 100))
//      assertNull(sub.replace(null.asInstanceOf[Any]))
//      assertFalse(sub.replaceIn(null.asInstanceOf[StringBuffer]))
//      assertFalse(sub.replaceIn(null.asInstanceOf[StringBuffer], 0, 100))
//      assertFalse(sub.replaceIn(null.asInstanceOf[StrBuilder]))
//      assertFalse(sub.replaceIn(null.asInstanceOf[StrBuilder], 0, 100))
//    } else {
//      assertEquals(replaceTemplate, sub.replace(replaceTemplate))
//      val bld = new StrBuilder(replaceTemplate)
//      assertFalse(sub.replaceIn(bld))
//      assertEquals(replaceTemplate, bld.toString)
//    }
//  }
//}
