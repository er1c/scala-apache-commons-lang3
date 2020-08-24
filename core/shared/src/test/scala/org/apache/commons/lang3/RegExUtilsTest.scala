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

import java.util.regex.{Pattern, PatternSyntaxException}
import org.junit.Assert.{assertEquals, assertNull}
import org.junit.Test
import org.scalatest.Assertions.assertThrows

/**
  * Unit tests for methods of {@link org.apache.commons.lang3.RegExUtils} which been moved to their own test classes.
  */
class RegExUtilsTest {
  @Test def testRemoveAll_StringPattern(): Unit = {
    assertNull(RegExUtils.removeAll(null, Pattern.compile("")))
    assertEquals("any", RegExUtils.removeAll("any", null.asInstanceOf[Pattern]))
    assertEquals("any", RegExUtils.removeAll("any", Pattern.compile("")))
    assertEquals("", RegExUtils.removeAll("any", Pattern.compile(".*")))
    assertEquals("", RegExUtils.removeAll("any", Pattern.compile(".+")))
    assertEquals("", RegExUtils.removeAll("any", Pattern.compile(".?")))
    assertEquals("A\nB", RegExUtils.removeAll("A<__>\n<__>B", Pattern.compile("<.*>")))
    assertEquals("AB", RegExUtils.removeAll("A<__>\n<__>B", Pattern.compile("(?s)<.*>")))
    assertEquals("ABC123", RegExUtils.removeAll("ABCabc123abc", Pattern.compile("[a-z]")))
    assertEquals("AB", RegExUtils.removeAll("A<__>\n<__>B", Pattern.compile("<.*>", Pattern.DOTALL)))
    assertEquals("AB", RegExUtils.removeAll("A<__>\\n<__>B", Pattern.compile("<.*>")))
    assertEquals("", RegExUtils.removeAll("<A>x\\ny</A>", Pattern.compile("<A>.*</A>")))
    assertEquals("", RegExUtils.removeAll("<A>\nxy\n</A>", Pattern.compile("<A>.*</A>", Pattern.DOTALL)))
  }

  @Test def testRemoveAll_StringString(): Unit = {
    assertNull(RegExUtils.removeAll(null, ""))
    assertEquals("any", RegExUtils.removeAll("any", null.asInstanceOf[String]))
    assertEquals("any", RegExUtils.removeAll("any", ""))
    assertEquals("", RegExUtils.removeAll("any", ".*"))
    assertEquals("", RegExUtils.removeAll("any", ".+"))
    assertEquals("", RegExUtils.removeAll("any", ".?"))
    assertEquals("A\nB", RegExUtils.removeAll("A<__>\n<__>B", "<.*>"))
    assertEquals("AB", RegExUtils.removeAll("A<__>\n<__>B", "(?s)<.*>"))
    assertEquals("ABC123", RegExUtils.removeAll("ABCabc123abc", "[a-z]"))
    assertThrows[PatternSyntaxException](
      RegExUtils.removeAll("any", "{badRegexSyntax}")
    ) //, "RegExUtils.removeAll expecting PatternSyntaxException")
    ()
  }

  @Test def testRemoveFirst_StringPattern(): Unit = {
    assertNull(RegExUtils.removeFirst(null, Pattern.compile("")))
    assertEquals("any", RegExUtils.removeFirst("any", null.asInstanceOf[Pattern]))
    assertEquals("any", RegExUtils.removeFirst("any", Pattern.compile("")))
    assertEquals("", RegExUtils.removeFirst("any", Pattern.compile(".*")))
    assertEquals("", RegExUtils.removeFirst("any", Pattern.compile(".+")))
    assertEquals("bc", RegExUtils.removeFirst("abc", Pattern.compile(".?")))
    assertEquals("A\n<__>B", RegExUtils.removeFirst("A<__>\n<__>B", Pattern.compile("<.*>")))
    assertEquals("AB", RegExUtils.removeFirst("A<__>\n<__>B", Pattern.compile("(?s)<.*>")))
    assertEquals("ABCbc123", RegExUtils.removeFirst("ABCabc123", Pattern.compile("[a-z]")))
    assertEquals("ABC123abc", RegExUtils.removeFirst("ABCabc123abc", Pattern.compile("[a-z]+")))
  }

  @Test def testRemoveFirst_StringString(): Unit = {
    assertNull(RegExUtils.removeFirst(null, ""))
    assertEquals("any", RegExUtils.removeFirst("any", null.asInstanceOf[String]))
    assertEquals("any", RegExUtils.removeFirst("any", ""))
    assertEquals("", RegExUtils.removeFirst("any", ".*"))
    assertEquals("", RegExUtils.removeFirst("any", ".+"))
    assertEquals("bc", RegExUtils.removeFirst("abc", ".?"))
    assertEquals("A\n<__>B", RegExUtils.removeFirst("A<__>\n<__>B", "<.*>"))
    assertEquals("AB", RegExUtils.removeFirst("A<__>\n<__>B", "(?s)<.*>"))
    assertEquals("ABCbc123", RegExUtils.removeFirst("ABCabc123", "[a-z]"))
    assertEquals("ABC123abc", RegExUtils.removeFirst("ABCabc123abc", "[a-z]+"))
    assertThrows[PatternSyntaxException](
      RegExUtils.removeFirst("any", "{badRegexSyntax}")
    ) //, "RegExUtils.removeFirst expecting PatternSyntaxException")
    ()
  }

  @Test def testRemovePattern_StringString(): Unit = {
    assertNull(RegExUtils.removePattern(null, ""))
    assertEquals("any", RegExUtils.removePattern("any", null.asInstanceOf[String]))
    assertEquals("", RegExUtils.removePattern("", ""))
    assertEquals("", RegExUtils.removePattern("", ".*"))
    assertEquals("", RegExUtils.removePattern("", ".+"))
    assertEquals("AB", RegExUtils.removePattern("A<__>\n<__>B", "<.*>"))
    assertEquals("AB", RegExUtils.removePattern("A<__>\\n<__>B", "<.*>"))
    assertEquals("", RegExUtils.removePattern("<A>x\\ny</A>", "<A>.*</A>"))
    assertEquals("", RegExUtils.removePattern("<A>\nxy\n</A>", "<A>.*</A>"))
    assertEquals("ABC123", RegExUtils.removePattern("ABCabc123", "[a-z]"))
  }

  @Test def testReplaceAll_StringPatternString(): Unit = {
    assertNull(RegExUtils.replaceAll(null, Pattern.compile(""), ""))
    assertEquals("any", RegExUtils.replaceAll("any", null.asInstanceOf[Pattern], ""))
    assertEquals("any", RegExUtils.replaceAll("any", Pattern.compile(""), null))
    assertEquals("zzz", RegExUtils.replaceAll("", Pattern.compile(""), "zzz"))
    assertEquals("zzz", RegExUtils.replaceAll("", Pattern.compile(".*"), "zzz"))
    assertEquals("", RegExUtils.replaceAll("", Pattern.compile(".+"), "zzz"))
    assertEquals("ZZaZZbZZcZZ", RegExUtils.replaceAll("abc", Pattern.compile(""), "ZZ"))
    assertEquals("z\nz", RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("<.*>"), "z"))
    assertEquals("z", RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("(?s)<.*>"), "z"))
    assertEquals("z", RegExUtils.replaceAll("<__>\n<__>", Pattern.compile("<.*>", Pattern.DOTALL), "z"))
    assertEquals("z", RegExUtils.replaceAll("<__>\\n<__>", Pattern.compile("<.*>"), "z"))
    assertEquals("X", RegExUtils.replaceAll("<A>\nxy\n</A>", Pattern.compile("<A>.*</A>", Pattern.DOTALL), "X"))
    assertEquals("ABC___123", RegExUtils.replaceAll("ABCabc123", Pattern.compile("[a-z]"), "_"))
    assertEquals("ABC_123", RegExUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), "_"))
    assertEquals("ABC123", RegExUtils.replaceAll("ABCabc123", Pattern.compile("[^A-Z0-9]+"), ""))
    assertEquals(
      "Lorem_ipsum_dolor_sit",
      RegExUtils.replaceAll("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2"))
  }

  @Test def testReplaceAll_StringStringString(): Unit = {
    assertNull(RegExUtils.replaceAll(null, "", ""))
    assertEquals("any", RegExUtils.replaceAll("any", null.asInstanceOf[String], ""))
    assertEquals("any", RegExUtils.replaceAll("any", "", null))
    assertEquals("zzz", RegExUtils.replaceAll("", "", "zzz"))
    assertEquals("zzz", RegExUtils.replaceAll("", ".*", "zzz"))
    assertEquals("", RegExUtils.replaceAll("", ".+", "zzz"))
    assertEquals("ZZaZZbZZcZZ", RegExUtils.replaceAll("abc", "", "ZZ"))
    assertEquals("z\nz", RegExUtils.replaceAll("<__>\n<__>", "<.*>", "z"))
    assertEquals("z", RegExUtils.replaceAll("<__>\n<__>", "(?s)<.*>", "z"))
    assertEquals("ABC___123", RegExUtils.replaceAll("ABCabc123", "[a-z]", "_"))
    assertEquals("ABC_123", RegExUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", "_"))
    assertEquals("ABC123", RegExUtils.replaceAll("ABCabc123", "[^A-Z0-9]+", ""))
    assertEquals("Lorem_ipsum_dolor_sit", RegExUtils.replaceAll("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"))
    assertThrows[PatternSyntaxException](
      RegExUtils.replaceAll("any", "{badRegexSyntax}", "")
    ) //, "RegExUtils.replaceAll expecting PatternSyntaxException")
    ()
  }

  @Test def testReplaceFirst_StringPatternString(): Unit = {
    assertNull(RegExUtils.replaceFirst(null, Pattern.compile(""), ""))
    assertEquals("any", RegExUtils.replaceFirst("any", null.asInstanceOf[Pattern], ""))
    assertEquals("any", RegExUtils.replaceFirst("any", Pattern.compile(""), null))
    assertEquals("zzz", RegExUtils.replaceFirst("", Pattern.compile(""), "zzz"))
    assertEquals("zzz", RegExUtils.replaceFirst("", Pattern.compile(".*"), "zzz"))
    assertEquals("", RegExUtils.replaceFirst("", Pattern.compile(".+"), "zzz"))
    assertEquals("ZZabc", RegExUtils.replaceFirst("abc", Pattern.compile(""), "ZZ"))
    assertEquals("z\n<__>", RegExUtils.replaceFirst("<__>\n<__>", Pattern.compile("<.*>"), "z"))
    assertEquals("z", RegExUtils.replaceFirst("<__>\n<__>", Pattern.compile("(?s)<.*>"), "z"))
    assertEquals("ABC_bc123", RegExUtils.replaceFirst("ABCabc123", Pattern.compile("[a-z]"), "_"))
    assertEquals("ABC_123abc", RegExUtils.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), "_"))
    assertEquals("ABC123abc", RegExUtils.replaceFirst("ABCabc123abc", Pattern.compile("[^A-Z0-9]+"), ""))
    assertEquals(
      "Lorem_ipsum  dolor   sit",
      RegExUtils.replaceFirst("Lorem ipsum  dolor   sit", Pattern.compile("( +)([a-z]+)"), "_$2"))
  }

  @Test def testReplaceFirst_StringStringString(): Unit = {
    assertNull(RegExUtils.replaceFirst(null, "", ""))
    assertEquals("any", RegExUtils.replaceFirst("any", null.asInstanceOf[String], ""))
    assertEquals("any", RegExUtils.replaceFirst("any", "", null))
    assertEquals("zzz", RegExUtils.replaceFirst("", "", "zzz"))
    assertEquals("zzz", RegExUtils.replaceFirst("", ".*", "zzz"))
    assertEquals("", RegExUtils.replaceFirst("", ".+", "zzz"))
    assertEquals("ZZabc", RegExUtils.replaceFirst("abc", "", "ZZ"))
    assertEquals("z\n<__>", RegExUtils.replaceFirst("<__>\n<__>", "<.*>", "z"))
    assertEquals("z", RegExUtils.replaceFirst("<__>\n<__>", "(?s)<.*>", "z"))
    assertEquals("ABC_bc123", RegExUtils.replaceFirst("ABCabc123", "[a-z]", "_"))
    assertEquals("ABC_123abc", RegExUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", "_"))
    assertEquals("ABC123abc", RegExUtils.replaceFirst("ABCabc123abc", "[^A-Z0-9]+", ""))
    assertEquals("Lorem_ipsum  dolor   sit", RegExUtils.replaceFirst("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"))
    assertThrows[PatternSyntaxException](
      RegExUtils.replaceFirst("any", "{badRegexSyntax}", "")
    ) //, "RegExUtils.replaceFirst expecting PatternSyntaxException")
    ()
  }

  @Test def testReplacePattern_StringStringString(): Unit = {
    assertNull(RegExUtils.replacePattern(null, "", ""))
    assertEquals("any", RegExUtils.replacePattern("any", null.asInstanceOf[String], ""))
    assertEquals("any", RegExUtils.replacePattern("any", "", null))
    assertEquals("zzz", RegExUtils.replacePattern("", "", "zzz"))
    assertEquals("zzz", RegExUtils.replacePattern("", ".*", "zzz"))
    assertEquals("", RegExUtils.replacePattern("", ".+", "zzz"))
    assertEquals("z", RegExUtils.replacePattern("<__>\n<__>", "<.*>", "z"))
    assertEquals("z", RegExUtils.replacePattern("<__>\\n<__>", "<.*>", "z"))
    assertEquals("X", RegExUtils.replacePattern("<A>\nxy\n</A>", "<A>.*</A>", "X"))
    assertEquals("ABC___123", RegExUtils.replacePattern("ABCabc123", "[a-z]", "_"))
    assertEquals("ABC_123", RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", "_"))
    assertEquals("ABC123", RegExUtils.replacePattern("ABCabc123", "[^A-Z0-9]+", ""))
    assertEquals("Lorem_ipsum_dolor_sit", RegExUtils.replacePattern("Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"))
  }
}
