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
//import org.apache.commons.lang3.JavaVersion.JAVA_1_4
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import java.util
//import java.util.Locale
//import org.junit.{Before, Test}
//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.MethodSource
//
///**
//  * Unit tests for {@link LocaleUtils}.
//  */
//object LocaleUtilsTest {
//  private val LOCALE_EN = new Locale("en", "")
//  private val LOCALE_EN_US = new Locale("en", "US")
//  private val LOCALE_EN_US_ZZZZ = new Locale("en", "US", "ZZZZ")
//  private val LOCALE_FR = new Locale("fr", "")
//  private val LOCALE_FR_CA = new Locale("fr", "CA")
//  private val LOCALE_QQ = new Locale("qq", "")
//  private val LOCALE_QQ_ZZ = new Locale("qq", "ZZ")
//
//  /**
//    * Pass in a valid language, test toLocale.
//    *
//    * @param language the language string
//    */
//  private def assertValidToLocale(language: String) = {
//    val locale = LocaleUtils.toLocale(language)
//    assertNotNull(locale, "valid locale")
//    assertEquals(language, locale.getLanguage)
//    //country and variant are empty
//    assertTrue(locale.getCountry == null || locale.getCountry.isEmpty)
//    assertTrue(locale.getVariant == null || locale.getVariant.isEmpty)
//  }
//
//  /**
//    * Pass in a valid language, test toLocale.
//    *
//    * @param localeString to pass to toLocale()
//    * @param language     of the resulting Locale
//    * @param country      of the resulting Locale
//    */
//  private def assertValidToLocale(localeString: String, language: String, country: String) = {
//    val locale = LocaleUtils.toLocale(localeString)
//    assertNotNull(locale, "valid locale")
//    assertEquals(language, locale.getLanguage)
//    assertEquals(country, locale.getCountry)
//    //variant is empty
//    assertTrue(locale.getVariant == null || locale.getVariant.isEmpty)
//  }
//
//  /**
//    * Pass in a valid language, test toLocale.
//    *
//    * @param localeString to pass to toLocale()
//    * @param language     of the resulting Locale
//    * @param country      of the resulting Locale
//    * @param variant      of the resulting Locale
//    */
//  private def assertValidToLocale(localeString: String, language: String, country: String, variant: String) = {
//    val locale = LocaleUtils.toLocale(localeString)
//    assertNotNull(locale, "valid locale")
//    assertEquals(language, locale.getLanguage)
//    assertEquals(country, locale.getCountry)
//    assertEquals(variant, locale.getVariant)
//  }
//
//  /**
//    * Helper method for local lookups.
//    *
//    * @param locale        the input locale
//    * @param defaultLocale the input default locale
//    * @param expected      expected results
//    */
//  private def assertLocaleLookupList(locale: Locale, defaultLocale: Locale, expected: Array[Locale]) = {
//    val localeList = if (defaultLocale == null) LocaleUtils.localeLookupList(locale)
//    else LocaleUtils.localeLookupList(locale, defaultLocale)
//    assertEquals(expected.length, localeList.size)
//    assertEquals(util.Arrays.asList(expected), localeList)
//    assertUnmodifiableCollection(localeList)
//  }
//
//  /**
//    * Make sure the language by country is correct. It checks that
//    * the LocaleUtils.languagesByCountry(country) call contains the
//    * array of languages passed in. It may contain more due to JVM
//    * variations.
//    *
//    * @param country
//    * @param languages array of languages that should be returned
//    */
//  private def assertLanguageByCountry(country: String, languages: Array[String]) = {
//    val list = LocaleUtils.languagesByCountry(country)
//    val list2 = LocaleUtils.languagesByCountry(country)
//    assertNotNull(list)
//    assertSame(list, list2)
//    //search through languages
//    for (language <- languages) {
//      val iterator = list.iterator
//      var found = false
//      // see if it was returned by the set
//      while ( {
//        iterator.hasNext
//      }) {
//        val locale = iterator.next
//        // should have an en empty variant
//        assertTrue(locale.getVariant == null || locale.getVariant.isEmpty)
//        assertEquals(country, locale.getCountry)
//        if (language == locale.getLanguage) {
//          found = true
//          break //todo: break is not supported
//        }
//      }
//      assertTrue(found, "Could not find language: " + language + " for country: " + country)
//    }
//    assertUnmodifiableCollection(list)
//  }
//
//  /**
//    * Make sure the country by language is correct. It checks that
//    * the LocaleUtils.countryByLanguage(language) call contains the
//    * array of countries passed in. It may contain more due to JVM
//    * variations.
//    *
//    * @param language
//    * @param countries array of countries that should be returned
//    */
//  private def assertCountriesByLanguage(language: String, countries: Array[String]) = {
//    val list = LocaleUtils.countriesByLanguage(language)
//    val list2 = LocaleUtils.countriesByLanguage(language)
//    assertNotNull(list)
//    assertSame(list, list2)
//    for (country <- countries) {
//      val iterator = list.iterator
//      var found = false
//      while ( {
//        iterator.hasNext
//      }) {
//        val locale = iterator.next
//        assertTrue(locale.getVariant == null || locale.getVariant.isEmpty)
//        assertEquals(language, locale.getLanguage)
//        if (country == locale.getCountry) {
//          found = true
//          break //todo: break is not supported
//        }
//      }
//      assertTrue(found, "Could not find language: " + country + " for country: " + language)
//    }
//    assertUnmodifiableCollection(list)
//  }
//
//  /**
//    * @param coll the collection to check
//    */
//  private def assertUnmodifiableCollection(coll: util.Collection[_]) = assertThrows(classOf[UnsupportedOperationException], () => coll.add(null))
//}
//
//class LocaleUtilsTest {
//  @Before def setUp() = { // Testing #LANG-304. Must be called before availableLocaleSet is called.
//    LocaleUtils.isAvailableLocale(Locale.getDefault)
//  }
//
//  //  /**
//  //    * Test that constructors are public, and work, etc.
//  //    */
//  //  @Test def testConstructor() = {
//  //    assertNotNull(new Nothing)
//  //    val cons = classOf[Nothing].getDeclaredConstructors
//  //    assertEquals(1, cons.length)
//  //    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//  //    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//  //    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  //  }
//
//  /**
//    * Test toLocale() method.
//    */
//  @Test def testToLocale_1Part() = {
//    assertNull(LocaleUtils.toLocale(null))
//    LocaleUtilsTest.assertValidToLocale("us")
//    LocaleUtilsTest.assertValidToLocale("fr")
//    LocaleUtilsTest.assertValidToLocale("de")
//    LocaleUtilsTest.assertValidToLocale("zh")
//    // Valid format but lang doesn't exist, should make instance anyway
//    LocaleUtilsTest.assertValidToLocale("qq")
//    // LANG-941: JDK 8 introduced the empty locale as one of the default locales
//    LocaleUtilsTest.assertValidToLocale("")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("Us"), "Should fail if not lowercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("uS"), "Should fail if not lowercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("u#"), "Should fail if not lowercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("u"), "Must be 2 chars if less than 5")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("uu_U"), "Must be 2 chars if less than 5")
//  }
//
//  @Test def testToLocale_2Part() = {
//    LocaleUtilsTest.assertValidToLocale("us_EN", "us", "EN")
//    //valid though doesn't exist
//    LocaleUtilsTest.assertValidToLocale("us_ZH", "us", "ZH")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("us-EN"), "Should fail as not underscore")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("us_En"), "Should fail second part not uppercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("us_en"), "Should fail second part not uppercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("us_eN"), "Should fail second part not uppercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("uS_EN"), "Should fail first part not lowercase")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("us_E3"), "Should fail second part not uppercase")
//  }
//
//  @Test def testToLocale_3Part() = {
//    LocaleUtilsTest.assertValidToLocale("us_EN_A", "us", "EN", "A")
//    // this isn't pretty, but was caused by a jdk bug it seems
//    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4210525
//    if (SystemUtils.isJavaVersionAtLeast(JAVA_1_4)) {
//      LocaleUtilsTest.assertValidToLocale("us_EN_a", "us", "EN", "a")
//      LocaleUtilsTest.assertValidToLocale("us_EN_SFsafdFDsdfF", "us", "EN", "SFsafdFDsdfF")
//    }
//    else {
//      LocaleUtilsTest.assertValidToLocale("us_EN_a", "us", "EN", "A")
//      LocaleUtilsTest.assertValidToLocale("us_EN_SFsafdFDsdfF", "us", "EN", "SFSAFDFDSDFF")
//    }
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("us_EN-a"), "Should fail as not underscore")
//    assertThrows(classOf[IllegalArgumentException], () => LocaleUtils.toLocale("uu_UU_"), "Must be 3, 5 or 7+ in length")
//  }
//
//  /**
//    * Test localeLookupList() method.
//    */
//  @Test def testLocaleLookupList_Locale() = {
//    LocaleUtilsTest.assertLocaleLookupList(null, null, new Array[Locale](0))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_QQ, null, Array[Locale](LocaleUtilsTest.LOCALE_QQ))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN, null, Array[Locale](LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN, null, Array[Locale](LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US, null, Array[Locale](LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US_ZZZZ, null, Array[Locale](LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN))
//  }
//
//  @Test def testLocaleLookupList_LocaleLocale() = {
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_QQ, LocaleUtilsTest.LOCALE_QQ, Array[Locale](LocaleUtilsTest.LOCALE_QQ))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN, LocaleUtilsTest.LOCALE_EN, Array[Locale](LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN_US, Array[Locale](LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_QQ, Array[Locale](LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN, LocaleUtilsTest.LOCALE_QQ))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_QQ_ZZ, Array[Locale](LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN, LocaleUtilsTest.LOCALE_QQ_ZZ))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US_ZZZZ, null, Array[Locale](LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_EN_US_ZZZZ, Array[Locale](LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_QQ, Array[Locale](LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN, LocaleUtilsTest.LOCALE_QQ))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_QQ_ZZ, Array[Locale](LocaleUtilsTest.LOCALE_EN_US_ZZZZ, LocaleUtilsTest.LOCALE_EN_US, LocaleUtilsTest.LOCALE_EN, LocaleUtilsTest.LOCALE_QQ_ZZ))
//    LocaleUtilsTest.assertLocaleLookupList(LocaleUtilsTest.LOCALE_FR_CA, LocaleUtilsTest.LOCALE_EN, Array[Locale](LocaleUtilsTest.LOCALE_FR_CA, LocaleUtilsTest.LOCALE_FR, LocaleUtilsTest.LOCALE_EN))
//  }
//
//  /**
//    * Test availableLocaleList() method.
//    */
//  @Test def testAvailableLocaleList() = {
//    val list = LocaleUtils.availableLocaleList
//    val list2 = LocaleUtils.availableLocaleList
//    assertNotNull(list)
//    assertSame(list, list2)
//    LocaleUtilsTest.assertUnmodifiableCollection(list)
//    val jdkLocaleArray = Locale.getAvailableLocales
//    val jdkLocaleList = util.Arrays.asList(jdkLocaleArray)
//    assertEquals(jdkLocaleList, list)
//  }
//
//  /**
//    * Test availableLocaleSet() method.
//    */
//  @Test def testAvailableLocaleSet() = {
//    val set = LocaleUtils.availableLocaleSet
//    val set2 = LocaleUtils.availableLocaleSet
//    assertNotNull(set)
//    assertSame(set, set2)
//    LocaleUtilsTest.assertUnmodifiableCollection(set)
//    val jdkLocaleArray = Locale.getAvailableLocales
//    val jdkLocaleList = util.Arrays.asList(jdkLocaleArray)
//    val jdkLocaleSet = new util.HashSet[Locale](jdkLocaleList)
//    assertEquals(jdkLocaleSet, set)
//  }
//
//  @SuppressWarnings(Array("boxing")) // JUnit4 does not support primitive equality testing apart from long
//  @Test  def testIsAvailableLocale(): Unit =  {
//    val set: util.Set[Locale] = LocaleUtils.availableLocaleSet
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_EN), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_EN))
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_EN_US), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_EN_US))
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_EN_US_ZZZZ), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_EN_US_ZZZZ))
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_FR), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_FR))
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_FR_CA), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_FR_CA))
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_QQ), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_QQ))
//    assertEquals(set.contains(LocaleUtilsTest.LOCALE_QQ_ZZ), LocaleUtils.isAvailableLocale(LocaleUtilsTest.LOCALE_QQ_ZZ))
//  }
//
//  /**
//    * Test for 3-chars locale, further details at LANG-915
//    *
//    */
//  @Test def testThreeCharsLocale (): Unit = {
//
//    import scala.collection.JavaConversions._
//
//    for (str <- util.Arrays.asList ("udm", "tet") ) {
//      val locale: Locale = LocaleUtils.toLocale (str)
//      assertNotNull (locale)
//      assertEquals (str, locale.getLanguage)
//      assertTrue (StringUtils.isBlank (locale.getCountry) )
//      assertEquals (new Locale (str), locale)
//    }
//  }
//
//  /**
//    * Test languagesByCountry() method.
//    */
//  @Test def testLanguagesByCountry (): Unit = {
//    LocaleUtilsTest.assertLanguageByCountry (null, new Array[String] (0) )
//    LocaleUtilsTest.assertLanguageByCountry ("GB", Array[String] ("en") )
//    LocaleUtilsTest.assertLanguageByCountry ("ZZ", new Array[String] (0) )
//    LocaleUtilsTest.assertLanguageByCountry ("CH", Array[String] ("fr", "de", "it") )
//  }
//
//  /**
//    * Test countriesByLanguage() method.
//    */
//  @Test def testCountriesByLanguage (): Unit = {
//    LocaleUtilsTest.assertCountriesByLanguage (null, new Array[String] (0) )
//    LocaleUtilsTest.assertCountriesByLanguage ("de", Array[String] ("DE", "CH", "AT", "LU") )
//    LocaleUtilsTest.assertCountriesByLanguage ("zz", new Array[String] (0) )
//    LocaleUtilsTest.assertCountriesByLanguage ("it", Array[String] ("IT", "CH") )
//  }
//
//  /**
//    * Tests #LANG-328 - only language+variant
//    */
//  @Test def testLang328 (): Unit = {
//    LocaleUtilsTest.assertValidToLocale ("fr__P", "fr", "", "P")
//    LocaleUtilsTest.assertValidToLocale ("fr__POSIX", "fr", "", "POSIX")
//  }
//  @Test def testLanguageAndUNM49Numeric3AreaCodeLang1312 (): Unit = {
//    LocaleUtilsTest.assertValidToLocale ("en_001", "en", "001")
//    LocaleUtilsTest.assertValidToLocale ("en_150", "en", "150")
//    LocaleUtilsTest.assertValidToLocale ("ar_001", "ar", "001")
//    // LANG-1312
//    LocaleUtilsTest.assertValidToLocale ("en_001_GB", "en", "001", "GB")
//    LocaleUtilsTest.assertValidToLocale ("en_150_US", "en", "150", "US")
//  }
//
//  /**
//    * Tests #LANG-865, strings starting with an underscore.
//    */
//  @Test def testLang865 (): Unit = {
//    LocaleUtilsTest.assertValidToLocale ("_GB", "", "GB", "")
//    LocaleUtilsTest.assertValidToLocale ("_GB_P", "", "GB", "P")
//    LocaleUtilsTest.assertValidToLocale ("_GB_POSIX", "", "GB", "POSIX")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_G"), "Must be at least 3 chars if starts with underscore")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_Gb"), "Must be uppercase if starts with underscore")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_gB"), "Must be uppercase if starts with underscore")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_1B"), "Must be letter if starts with underscore")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_G1"), "Must be letter if starts with underscore")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_GB_"), "Must be at least 5 chars if starts with underscore")
//    assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale ("_GBAP"), "Must have underscore after the country if starts with underscore and is at least 5 chars")
//  }
//
//  @ParameterizedTest
//  @MethodSource ("java.util.Locale#getAvailableLocales")
//  def testParseAllLocales (l: Locale): Unit = { // Check if it's possible to recreate the Locale using just the standard constructor
//    val locale: Locale = new Locale (l.getLanguage, l.getCountry, l.getVariant)
//    if (l == locale) { // it is possible for LocaleUtils.toLocale to handle these Locales
//      val str: String = l.toString
//      // Look for the script/extension suffix
//      var suff: Int = str.indexOf ("_#")
//      if (suff == - (1) ) {
//        suff = str.indexOf ("#")
//      }
//      var localeStr: String = str
//      if (suff >= 0) { // we have a suffix
//        assertThrows (classOf[IllegalArgumentException], () => LocaleUtils.toLocale (str) )
//        // try without suffix
//        localeStr = str.substring (0, suff)
//      }
//      val loc: Locale = LocaleUtils.toLocale (localeStr)
//      assertEquals (l, loc)
//    }
//  }
//}
