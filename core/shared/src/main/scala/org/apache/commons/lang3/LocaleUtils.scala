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
//import java.util
//import java.util.Collections
//import java.util.Locale
//import java.util.concurrent.ConcurrentHashMap
//import java.util.concurrent.ConcurrentMap
//
///**
//  * <p>Operations to assist when working with a {@link Locale}.</p>
//  *
//  * <p>This class tries to handle {@code null} input gracefully.
//  * An exception will not be thrown for a {@code null} input.
//  * Each method documents its behavior in more detail.</p>
//  *
//  * @since 2.2
//  */
//object LocaleUtils {
//  /** Concurrent map of language locales by country. */
//  private val cLanguagesByCountry = new ConcurrentHashMap[String, util.List[Locale]]
//  /** Concurrent map of country locales by language. */
//  private val cCountriesByLanguage = new ConcurrentHashMap[String, util.List[Locale]]
//
//  /**
//    * <p>Converts a String to a Locale.</p>
//    *
//    * <p>This method takes the string format of a locale and creates the
//    * locale object from it.</p>
//    *
//    * <pre>
//    * LocaleUtils.toLocale("")           = new Locale("", "")
//    * LocaleUtils.toLocale("en")         = new Locale("en", "")
//    * LocaleUtils.toLocale("en_GB")      = new Locale("en", "GB")
//    * LocaleUtils.toLocale("en_001")     = new Locale("en", "001")
//    * LocaleUtils.toLocale("en_GB_xxx")  = new Locale("en", "GB", "xxx")   (#)
//    * </pre>
//    *
//    * <p>(#) The behavior of the JDK variant constructor changed between JDK1.3 and JDK1.4.
//    * In JDK1.3, the constructor upper cases the variant, in JDK1.4, it doesn't.
//    * Thus, the result from getVariant() may vary depending on your JDK.</p>
//    *
//    * <p>This method validates the input strictly.
//    * The language code must be lowercase.
//    * The country code must be uppercase.
//    * The separator must be an underscore.
//    * The length must be correct.
//    * </p>
//    *
//    * @param str the locale String to convert, null returns null
//    * @return a Locale, null if null input
//    * @throws java.lang.IllegalArgumentException if the string is an invalid format
//    * @see Locale#forLanguageTag(String)
//    */
//  def toLocale(str: String): Locale = {
//    if (str == null) return null
//    if (str.isEmpty) { // LANG-941 - JDK 8 introduced an empty locale where all fields are blank
//      return new Locale(StringUtils.EMPTY, StringUtils.EMPTY)
//    }
//    if (str.contains("#")) { // LANG-879 - Cannot handle Java 7 script & extensions
//      throw new IllegalArgumentException("Invalid locale format: " + str)
//    }
//    val len = str.length
//    if (len < 2) throw new IllegalArgumentException("Invalid locale format: " + str)
//    val ch0 = str.charAt(0)
//    if (ch0 == '_') {
//      if (len < 3) throw new IllegalArgumentException("Invalid locale format: " + str)
//      val ch1 = str.charAt(1)
//      val ch2 = str.charAt(2)
//      if (!Character.isUpperCase(ch1) || !Character.isUpperCase(ch2)) throw new IllegalArgumentException("Invalid locale format: " + str)
//      if (len == 3) return new Locale(StringUtils.EMPTY, str.substring(1, 3))
//      if (len < 5) throw new IllegalArgumentException("Invalid locale format: " + str)
//      if (str.charAt(3) != '_') throw new IllegalArgumentException("Invalid locale format: " + str)
//      return new Locale(StringUtils.EMPTY, str.substring(1, 3), str.substring(4))
//    }
//    parseLocale(str)
//  }
//
//  /**
//    * Tries to parse a locale from the given String.
//    *
//    * @param str the String to parse a locale from.
//    * @return a Locale instance parsed from the given String.
//    * @throws java.lang.IllegalArgumentException if the given String can not be parsed.
//    */
//  private def parseLocale(str: String): Locale = {
//    if (isISO639LanguageCode(str)) return new Locale(str)
//    val segments = str.split("_", -1)
//    val language = segments(0)
//    if (segments.length == 2) {
//      val country = segments(1)
//      if (isISO639LanguageCode(language) && isISO3166CountryCode(country) || isNumericAreaCode(country)) return new Locale(language, country)
//    }
//    else if (segments.length == 3) {
//      val country = segments(1)
//      val variant = segments(2)
//      if (isISO639LanguageCode(language) && (country.isEmpty || isISO3166CountryCode(country) || isNumericAreaCode(country)) && !variant.isEmpty) return new Locale(language, country, variant)
//    }
//    throw new IllegalArgumentException("Invalid locale format: " + str)
//  }
//
//  /**
//    * Checks whether the given String is a ISO 639 compliant language code.
//    *
//    * @param str the String to check.
//    * @return true, if the given String is a ISO 639 compliant language code.
//    */
//  private def isISO639LanguageCode(str: String) = StringUtils.isAllLowerCase(str) && (str.length == 2 || str.length == 3)
//
//  /**
//    * Checks whether the given String is a ISO 3166 alpha-2 country code.
//    *
//    * @param str the String to check
//    * @return true, is the given String is a ISO 3166 compliant country code.
//    */
//  private def isISO3166CountryCode(str: String) = StringUtils.isAllUpperCase(str) && str.length == 2
//
//  /**
//    * Checks whether the given String is a UN M.49 numeric area code.
//    *
//    * @param str the String to check
//    * @return true, is the given String is a UN M.49 numeric area code.
//    */
//  private def isNumericAreaCode(str: String) = StringUtils.isNumeric(str) && str.length == 3
//
//  /**
//    * <p>Obtains the list of locales to search through when performing
//    * a locale search.</p>
//    *
//    * <pre>
//    * localeLookupList(Locale("fr", "CA", "xxx"))
//    * = [Locale("fr", "CA", "xxx"), Locale("fr", "CA"), Locale("fr")]
//    * </pre>
//    *
//    *
//    * @param locale the locale to start from
//    * @return the unmodifiable list of Locale objects, 0 being locale, not null
//    */
//  def localeLookupList(locale: Locale): util.List[Locale] = localeLookupList(locale, locale)
//
//  /**
//    * <p>Obtains the list of locales to search through when performing
//    * a locale search.</p>
//    *
//    * <pre>
//    * localeLookupList(Locale("fr", "CA", "xxx"), Locale("en"))
//    * = [Locale("fr", "CA", "xxx"), Locale("fr", "CA"), Locale("fr"), Locale("en"]
//    * </pre>
//    *
//    * <p>The result list begins with the most specific locale, then the
//    * next more general and so on, finishing with the default locale.
//    * The list will never contain the same locale twice.</p>
//    *
//    * @param locale        the locale to start from, null returns empty list
//    * @param defaultLocale the default locale to use if no other is found
//    * @return the unmodifiable list of Locale objects, 0 being locale, not null
//    */
//  def localeLookupList(locale: Locale, defaultLocale: Locale): util.List[Locale] = {
//    val list = new util.ArrayList[Locale](4)
//    if (locale != null) {
//      list.add(locale)
//      if (!locale.getVariant.isEmpty) list.add(new Locale(locale.getLanguage, locale.getCountry))
//      if (!locale.getCountry.isEmpty) list.add(new Locale(locale.getLanguage, StringUtils.EMPTY))
//      if (!list.contains(defaultLocale)) list.add(defaultLocale)
//    }
//    Collections.unmodifiableList(list)
//  }
//
//  /**
//    * <p>Obtains an unmodifiable list of installed locales.</p>
//    *
//    * <p>This method is a wrapper around {@link Locale# getAvailableLocales ( )}.
//    * It is more efficient, as the JDK method must create a new array each
//    * time it is called.</p>
//    *
//    * @return the unmodifiable list of available locales
//    */
//  def availableLocaleList: util.List[Locale] = SyncAvoid.AVAILABLE_LOCALE_LIST
//
//  /**
//    * <p>Obtains an unmodifiable set of installed locales.</p>
//    *
//    * <p>This method is a wrapper around {@link Locale# getAvailableLocales ( )}.
//    * It is more efficient, as the JDK method must create a new array each
//    * time it is called.</p>
//    *
//    * @return the unmodifiable set of available locales
//    */
//  def availableLocaleSet: util.Set[Locale] = SyncAvoid.AVAILABLE_LOCALE_SET
//
//  /**
//    * <p>Checks if the locale specified is in the list of available locales.</p>
//    *
//    * @param locale the Locale object to check if it is available
//    * @return true if the locale is a known locale
//    */
//  def isAvailableLocale(locale: Locale): Boolean = availableLocaleList.contains(locale)
//
//  /**
//    * <p>Obtains the list of languages supported for a given country.</p>
//    *
//    * <p>This method takes a country code and searches to find the
//    * languages available for that country. Variant locales are removed.</p>
//    *
//    * @param countryCode the 2 letter country code, null returns empty
//    * @return an unmodifiable List of Locale objects, not null
//    */
//  def languagesByCountry(countryCode: String): util.List[Locale] = {
//    if (countryCode == null) return Collections.emptyList
//    var langs = cLanguagesByCountry.get(countryCode)
//    if (langs == null) {
//      langs = new util.ArrayList[Locale]
//      val locales = availableLocaleList
//      import scala.collection.JavaConversions._
//      for (locale <- locales) {
//        if (countryCode == locale.getCountry && locale.getVariant.isEmpty) langs.add(locale)
//      }
//      langs = Collections.unmodifiableList(langs)
//      cLanguagesByCountry.putIfAbsent(countryCode, langs)
//      langs = cLanguagesByCountry.get(countryCode)
//    }
//    langs
//  }
//
//  /**
//    * <p>Obtains the list of countries supported for a given language.</p>
//    *
//    * <p>This method takes a language code and searches to find the
//    * countries available for that language. Variant locales are removed.</p>
//    *
//    * @param languageCode the 2 letter language code, null returns empty
//    * @return an unmodifiable List of Locale objects, not null
//    */
//  def countriesByLanguage(languageCode: String): util.List[Locale] = {
//    if (languageCode == null) return Collections.emptyList
//    var countries = cCountriesByLanguage.get(languageCode)
//    if (countries == null) {
//      countries = new util.ArrayList[Locale]
//      val locales = availableLocaleList
//      import scala.collection.JavaConversions._
//      for (locale <- locales) {
//        if (languageCode == locale.getLanguage && !locale.getCountry.isEmpty && locale.getVariant.isEmpty) countries.add(locale)
//      }
//      countries = Collections.unmodifiableList(countries)
//      cCountriesByLanguage.putIfAbsent(languageCode, countries)
//      countries = cCountriesByLanguage.get(languageCode)
//    }
//    countries
//  }
//
//  //-----------------------------------------------------------------------
//  // class to avoid synchronization (Init on demand)
//  private[lang3] object SyncAvoid {
//    /** Unmodifiable list of available locales. */
//    private var AVAILABLE_LOCALE_LIST = null
//    /** Unmodifiable set of available locales. */
//    private var AVAILABLE_LOCALE_SET = null
//
//    try
//    val list
//    = new util.ArrayList[Locale](util.Arrays.asList(Locale.getAvailableLocales)) // extra safe
//    AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(list)
//    AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new util.HashSet[Locale](list))
//
//  }
//
//}
