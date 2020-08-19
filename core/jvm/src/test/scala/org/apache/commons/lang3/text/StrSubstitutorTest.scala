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
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util
//import java.util.Properties
//import org.apache.commons.lang3.mutable.MutableObject
//import org.junit.{After, Before, Test}
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Test class for StrSubstitutor.
//  */
//@deprecated class StrSubstitutorTest extends JUnitSuite {
//  private var values = null
//
//  @Before def setUp() = {
//    values = new util.HashMap[String, String]
//    values.put("animal", "quick brown fox")
//    values.put("target", "lazy dog")
//  }
//
//  @After def tearDown() = values = null
//
//  /**
//    * Tests simple key replace.
//    */
//  @Test def testReplaceSimple() = doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true)
//
//  @Test def testReplaceSolo() = doTestReplace("quick brown fox", "${animal}", false)
//
//  /**
//    * Tests replace with no variables.
//    */
//  @Test def testReplaceNoVariables() = doTestNoReplace("The balloon arrived.")
//
//  /**
//    * Tests replace with null.
//    */
//  @Test def testReplaceNull() = doTestNoReplace(null)
//
//  @Test def testReplaceEmpty() = doTestNoReplace("")
//
//  /**
//    * Tests key replace changing map after initialization (not recommended).
//    */
//  @Test def testReplaceChangedMap() = {
//    val sub = new Nothing(values)
//    values.put("target", "moon")
//    assertEquals("The quick brown fox jumps over the moon.", sub.replace("The ${animal} jumps over the ${target}."))
//  }
//
//  /**
//    * Tests unknown key replace.
//    */
//  @Test def testReplaceUnknownKey() = {
//    doTestReplace("The ${person} jumps over the lazy dog.", "The ${person} jumps over the ${target}.", true)
//    doTestReplace("The ${person} jumps over the lazy dog. 1234567890.", "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.", true)
//  }
//
//  /**
//    * Tests adjacent keys.
//    */
//  @Test def testReplaceAdjacentAtStart() = {
//    values.put("code", "GBP")
//    values.put("amount", "12.50")
//    val sub = new Nothing(values)
//    assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"))
//  }
//
//  @Test def testReplaceAdjacentAtEnd() = {
//    values.put("code", "GBP")
//    values.put("amount", "12.50")
//    val sub = new Nothing(values)
//    assertEquals("Amount is GBP12.50", sub.replace("Amount is ${code}${amount}"))
//  }
//
//  /**
//    * Tests simple recursive replace.
//    */
//  @Test def testReplaceRecursive() = {
//    values.put("animal", "${critter}")
//    values.put("target", "${pet}")
//    values.put("pet", "${petCharacteristic} dog")
//    values.put("petCharacteristic", "lazy")
//    values.put("critter", "${critterSpeed} ${critterColor} ${critterType}")
//    values.put("critterSpeed", "quick")
//    values.put("critterColor", "brown")
//    values.put("critterType", "fox")
//    doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true)
//    values.put("pet", "${petCharacteristicUnknown:-lazy} dog")
//    doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true)
//  }
//
//  /**
//    * Tests escaping.
//    */
//  @Test def testReplaceEscaping() = doTestReplace("The ${animal} jumps over the lazy dog.", "The $${animal} jumps over the ${target}.", true)
//
//  @Test def testReplaceSoloEscaping() = doTestReplace("${animal}", "$${animal}", false)
//
//  /**
//    * Tests complex escaping.
//    */
//  @Test def testReplaceComplexEscaping() = {
//    doTestReplace("The ${quick brown fox} jumps over the lazy dog.", "The $${${animal}} jumps over the ${target}.", true)
//    doTestReplace("The ${quick brown fox} jumps over the lazy dog. ${1234567890}.", "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.", true)
//  }
//
//  /**
//    * Tests when no prefix or suffix.
//    */
//  @Test def testReplaceNoPrefixNoSuffix() = doTestReplace("The animal jumps over the lazy dog.", "The animal jumps over the ${target}.", true)
//
//  /**
//    * Tests when no incomplete prefix.
//    */
//  @Test def testReplaceIncompletePrefix() = doTestReplace("The {animal} jumps over the lazy dog.", "The {animal} jumps over the ${target}.", true)
//
//  /**
//    * Tests when prefix but no suffix.
//    */
//  @Test def testReplacePrefixNoSuffix() = doTestReplace("The ${animal jumps over the ${target} lazy dog.", "The ${animal jumps over the ${target} ${target}.", true)
//
//  /**
//    * Tests when suffix but no prefix.
//    */
//  @Test def testReplaceNoPrefixSuffix() = doTestReplace("The animal} jumps over the lazy dog.", "The animal} jumps over the ${target}.", true)
//
//  /**
//    * Tests when no variable name.
//    */
//  @Test def testReplaceEmptyKeys() = {
//    doTestReplace("The ${} jumps over the lazy dog.", "The ${} jumps over the ${target}.", true)
//    doTestReplace("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.", true)
//  }
//
//  /**
//    * Tests replace creates output same as input.
//    */
//  @Test def testReplaceToIdentical() = {
//    values.put("animal", "$${${thing}}")
//    values.put("thing", "animal")
//    doTestReplace("The ${animal} jumps.", "The ${animal} jumps.", true)
//  }
//
//  /**
//    * Tests a cyclic replace operation.
//    * The cycle should be detected and cause an exception to be thrown.
//    */
//  @Test def testCyclicReplacement() = {
//    val map = new util.HashMap[String, String]
//    map.put("animal", "${critter}")
//    map.put("target", "${pet}")
//    map.put("pet", "${petCharacteristic} dog")
//    map.put("petCharacteristic", "lazy")
//    map.put("critter", "${critterSpeed} ${critterColor} ${critterType}")
//    map.put("critterSpeed", "quick")
//    map.put("critterColor", "brown")
//    map.put("critterType", "${animal}")
//    val sub = new Nothing(map)
//    assertThrows(classOf[IllegalStateException], () => sub.replace("The ${animal} jumps over the ${target}."), "Cyclic replacement was not detected!")
//    // also check even when default value is set.
//    map.put("critterType", "${animal:-fox}")
//    val sub2 = new Nothing(map)
//    assertThrows(classOf[IllegalStateException], () => sub2.replace("The ${animal} jumps over the ${target}."), "Cyclic replacement was not detected!")
//  }
//
//  /**
//    * Tests interpolation with weird boundary patterns.
//    */
//  @Test def testReplaceWeirdPattens() = {
//    doTestNoReplace("")
//    doTestNoReplace("${}")
//    doTestNoReplace("${ }")
//    doTestNoReplace("${\t}")
//    doTestNoReplace("${\n}")
//    doTestNoReplace("${\b}")
//    doTestNoReplace("${")
//    doTestNoReplace("$}")
//    doTestNoReplace("}")
//    doTestNoReplace("${}$")
//    doTestNoReplace("${${")
//    doTestNoReplace("${${}}")
//    doTestNoReplace("${$${}}")
//    doTestNoReplace("${$$${}}")
//    doTestNoReplace("${$$${$}}")
//    doTestNoReplace("${${}}")
//    doTestNoReplace("${${ }}")
//  }
//
//  @Test def testReplacePartialString_noReplace() = {
//    val sub = new Nothing
//    assertEquals("${animal} jumps", sub.replace("The ${animal} jumps over the ${target}.", 4, 15))
//  }
//
//  /**
//    * Tests whether a variable can be replaced in a variable name.
//    */
//  @Test def testReplaceInVariable() = {
//    values.put("animal.1", "fox")
//    values.put("animal.2", "mouse")
//    values.put("species", "2")
//    val sub = new Nothing(values)
//    sub.setEnableSubstitutionInVariables(true)
//    assertEquals("The mouse jumps over the lazy dog.", sub.replace("The ${animal.${species}} jumps over the ${target}."), "Wrong result (1)")
//    values.put("species", "1")
//    assertEquals("The fox jumps over the lazy dog.", sub.replace("The ${animal.${species}} jumps over the ${target}."), "Wrong result (2)")
//    assertEquals("The fox jumps over the lazy dog.", sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknown.target:-lazy dog}."), "Wrong result (3)")
//  }
//
//  /**
//    * Tests whether substitution in variable names is disabled per default.
//    */
//  @Test def testReplaceInVariableDisabled() = {
//    values.put("animal.1", "fox")
//    values.put("animal.2", "mouse")
//    values.put("species", "2")
//    val sub = new Nothing(values)
//    assertEquals("The ${animal.${species}} jumps over the lazy dog.", sub.replace("The ${animal.${species}} jumps over the ${target}."), "Wrong result (1)")
//    assertEquals("The ${animal.${species:-1}} jumps over the lazy dog.", sub.replace("The ${animal.${species:-1}} jumps over the ${target}."), "Wrong result (2)")
//  }
//
//  /**
//    * Tests complex and recursive substitution in variable names.
//    */
//  @Test def testReplaceInVariableRecursive() = {
//    values.put("animal.2", "brown fox")
//    values.put("animal.1", "white mouse")
//    values.put("color", "white")
//    values.put("species.white", "1")
//    values.put("species.brown", "2")
//    val sub = new Nothing(values)
//    sub.setEnableSubstitutionInVariables(true)
//    assertEquals("The white mouse jumps over the lazy dog.", sub.replace("The ${animal.${species.${color}}} jumps over the ${target}."), "Wrong result (1)")
//    assertEquals("The brown fox jumps over the lazy dog.", sub.replace("The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."), "Wrong result (2)")
//  }
//
//  @Test def testDefaultValueDelimiters() = {
//    val map = new util.HashMap[String, String]
//    map.put("animal", "fox")
//    map.put("target", "dog")
//    var sub = new Nothing(map, "${", "}", '$')
//    assertEquals("The fox jumps over the lazy dog. 1234567890.", sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number:-1234567890}."))
//    sub = new Nothing(map, "${", "}", '$', "?:")
//    assertEquals("The fox jumps over the lazy dog. 1234567890.", sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number?:1234567890}."))
//    sub = new Nothing(map, "${", "}", '$', "||")
//    assertEquals("The fox jumps over the lazy dog. 1234567890.", sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number||1234567890}."))
//    sub = new Nothing(map, "${", "}", '$', "!")
//    assertEquals("The fox jumps over the lazy dog. 1234567890.", sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."))
//    sub = new Nothing(map, "${", "}", '$', "")
//    sub.setValueDelimiterMatcher(null)
//    assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.", sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."))
//    sub = new Nothing(map, "${", "}", '$')
//    sub.setValueDelimiterMatcher(null)
//    assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.", sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."))
//  }
//
//  /**
//    * Tests protected.
//    */
//  @Test def testResolveVariable() = {
//    val builder = new StrBuilder("Hi ${name}!")
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    val sub = new Nothing(map) {
//      protected def resolveVariable(variableName: String, buf: StrBuilder, startPos: Int, endPos: Int) = {
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
//  @Test def testConstructorNoArgs() = {
//    val sub = new Nothing
//    assertEquals("Hi ${name}", sub.replace("Hi ${name}"))
//  }
//
//  @Test def testConstructorMapPrefixSuffix() = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    val sub = new Nothing(map, "<", ">")
//    assertEquals("Hi < commons", sub.replace("Hi $< <name>"))
//  }
//
//  @Test def testConstructorMapFull() = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    var sub = new Nothing(map, "<", ">", '!')
//    assertEquals("Hi < commons", sub.replace("Hi !< <name>"))
//    sub = new Nothing(map, "<", ">", '!', "||")
//    assertEquals("Hi < commons", sub.replace("Hi !< <name2||commons>"))
//  }
//
//  /**
//    * Tests get set.
//    */
//  @Test def testGetSetEscape() = {
//    val sub = new Nothing
//    assertEquals('$', sub.getEscapeChar)
//    sub.setEscapeChar('<')
//    assertEquals('<', sub.getEscapeChar)
//  }
//
//  @Test def testGetSetPrefix() = {
//    val sub = new Nothing
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    sub.setVariablePrefix('<')
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.CharMatcher])
//    sub.setVariablePrefix("<<")
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    assertThrows(classOf[IllegalArgumentException], () => sub.setVariablePrefix(null))
//    assertTrue(sub.getVariablePrefixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    val matcher = StrMatcher.commaMatcher
//    sub.setVariablePrefixMatcher(matcher)
//    assertSame(matcher, sub.getVariablePrefixMatcher)
//    assertThrows(classOf[IllegalArgumentException], () => sub.setVariablePrefixMatcher(null))
//    assertSame(matcher, sub.getVariablePrefixMatcher)
//  }
//
//  @Test def testGetSetSuffix() = {
//    val sub = new Nothing
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    sub.setVariableSuffix('<')
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.CharMatcher])
//    sub.setVariableSuffix("<<")
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    assertThrows(classOf[IllegalArgumentException], () => sub.setVariableSuffix(null))
//    assertTrue(sub.getVariableSuffixMatcher.isInstanceOf[StrMatcher.StringMatcher])
//    val matcher = StrMatcher.commaMatcher
//    sub.setVariableSuffixMatcher(matcher)
//    assertSame(matcher, sub.getVariableSuffixMatcher)
//    assertThrows(classOf[IllegalArgumentException], () => sub.setVariableSuffixMatcher(null))
//    assertSame(matcher, sub.getVariableSuffixMatcher)
//  }
//
//  @Test def testGetSetValueDelimiter() = {
//    val sub = new Nothing
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
//  @Test def testStaticReplace() = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    assertEquals("Hi commons!", StrSubstitutor.replace("Hi ${name}!", map))
//  }
//
//  @Test def testStaticReplacePrefixSuffix() = {
//    val map = new util.HashMap[String, String]
//    map.put("name", "commons")
//    assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"))
//  }
//
//  /**
//    * Tests interpolation with system properties.
//    */
//  @Test def testStaticReplaceSystemProperties() = {
//    val buf = new StrBuilder
//    buf.append("Hi ").append(System.getProperty("user.name"))
//    buf.append(", you are working with ")
//    buf.append(System.getProperty("os.name"))
//    buf.append(", your home directory is ")
//    buf.append(System.getProperty("user.home")).append('.')
//    assertEquals(buf.toString, StrSubstitutor.replaceSystemProperties("Hi ${user.name}, you are " + "working with ${os.name}, your home " + "directory is ${user.home}."))
//  }
//
//  /**
//    * Test for LANG-1055: StrSubstitutor.replaceSystemProperties does not work consistently
//    */
//  @Test def testLANG1055() = {
//    System.setProperty("test_key", "test_value")
//    val expected = StrSubstitutor.replace("test_key=${test_key}", System.getProperties)
//    val actual = StrSubstitutor.replaceSystemProperties("test_key=${test_key}")
//    assertEquals(expected, actual)
//  }
//
//  /**
//    * Test the replace of a properties object
//    */
//  @Test def testSubstituteDefaultProperties() = {
//    val org = "${doesnotwork}"
//    System.setProperty("doesnotwork", "It works!")
//    // create a new Properties object with the System.getProperties as default
//    val props = new Properties(System.getProperties)
//    assertEquals("It works!", StrSubstitutor.replace(org, props))
//  }
//
//  @Test def testSamePrefixAndSuffix() = {
//    val map = new util.HashMap[String, String]
//    map.put("greeting", "Hello")
//    map.put(" there ", "XXX")
//    map.put("name", "commons")
//    assertEquals("Hi commons!", StrSubstitutor.replace("Hi @name@!", map, "@", "@"))
//    assertEquals("Hello there commons!", StrSubstitutor.replace("@greeting@ there @name@!", map, "@", "@"))
//  }
//
//  @Test def testSubstitutePreserveEscape() = {
//    val org = "${not-escaped} $${escaped}"
//    val map = new util.HashMap[String, String]
//    map.put("not-escaped", "value")
//    val sub = new Nothing(map, "${", "}", '$')
//    assertFalse(sub.isPreserveEscapes)
//    assertEquals("value ${escaped}", sub.replace(org))
//    sub.setPreserveEscapes(true)
//    assertTrue(sub.isPreserveEscapes)
//    assertEquals("value $${escaped}", sub.replace(org))
//  }
//
//  //-----------------------------------------------------------------------
//  private def doTestReplace(expectedResult: String, replaceTemplate: String, substring: Boolean) = {
//    val expectedShortResult = expectedResult.substring(1, expectedResult.length - 1)
//    val sub = new Nothing(values)
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
//  private def doTestNoReplace(replaceTemplate: String) = {
//    val sub = new Nothing(values)
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
//    }
//    else {
//      assertEquals(replaceTemplate, sub.replace(replaceTemplate))
//      val bld = new StrBuilder(replaceTemplate)
//      assertFalse(sub.replaceIn(bld))
//      assertEquals(replaceTemplate, bld.toString)
//    }
//  }
//}
