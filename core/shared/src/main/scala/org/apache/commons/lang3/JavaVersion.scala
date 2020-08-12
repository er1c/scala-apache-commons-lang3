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

import java.lang.{Float => JavaFloat}
import org.apache.commons.lang3.math.NumberUtils

/**
  * <p>An enum representing all the versions of the Java specification.
  * This is intended to mirror available values from the
  * <em>java.specification.version</em> System property. </p>
  *
  * @since 3.0
  */
object JavaVersion extends Enumeration {
  /**
    * @param value The float  value.
    * @param name  The standard name.
    */
  final class JavaVersion(protected val value: Float, name: String) extends super.Val(name) {

    /**
      * <p>The string value is overridden to return the standard name.</p>
      *
      * <p>For example, {@code "1.5"}.</p>
      *
      * @return the name, not null
      */
    override def toString: String = name

    /**
      * <p>Whether this version of Java is at least the version of Java passed in.</p>
      *
      * <p>For example:<br>
      * {@code myVersion.atLeast(JavaVersion.JAVA_1_4)}<p>
      *
      * @param requiredVersion the version to check against, not null
      * @return true if this version is equal to or greater than the specified version
      */
    def atLeast(requiredVersion: JavaVersion): Boolean = this.value >= requiredVersion.value

    /**
      * <p>Whether this version of Java is at most the version of Java passed in.</p>
      *
      * <p>For example:<br>
      * {@code myVersion.atMost(JavaVersion.JAVA_1_4)}<p>
      *
      * @param requiredVersion the version to check against, not null
      * @return true if this version is equal to or greater than the specified version
      * @since 3.9
      */
    def atMost(requiredVersion: JavaVersion): Boolean = this.value <= requiredVersion.value
  }

  /**
    * The Java version reported by Android. This is not an official Java version number.
    */
  val JAVA_0_9 = new JavaVersion(1.5f, "0.9")

  /**
    * Java 1.1.
    */
  val JAVA_1_1 = new JavaVersion(1.1f, "1.1")

  /**
    * Java 1.2.
    */
  val JAVA_1_2 = new JavaVersion(1.2f, "1.2")

  /**
    * Java 1.3.
    */
  val JAVA_1_3 = new JavaVersion(1.3f, "1.3")

  /**
    * Java 1.4.
    */
  val JAVA_1_4 = new JavaVersion(1.4f, "1.4")

  /**
    * Java 1.5.
    */
  val JAVA_1_5 = new JavaVersion(1.5f, "1.5")

  /**
    * Java 1.6.
    */
  val JAVA_1_6 = new JavaVersion(1.6f, "1.6")

  /**
    * Java 1.7.
    */
  val JAVA_1_7 = new JavaVersion(1.7f, "1.7")

  /**
    * Java 1.8.
    */
  val JAVA_1_8 = new JavaVersion(1.8f, "1.8")

  /**
    * Java 1.9.
    *
    * @deprecated As of release 3.5, replaced by {@link #JAVA_9}
    */
  @Deprecated
  val JAVA_1_9 = new JavaVersion(9.0f, "9")

  /**
    * Java 9
    *
    * @since 3.5
    */
  val JAVA_9 = new JavaVersion(9.0f, "9")

  /**
    * Java 10
    *
    * @since 3.7
    */
  val JAVA_10 = new JavaVersion(10.0f, "10")

  /**
    * Java 11
    *
    * @since 3.8
    */
  val JAVA_11 = new JavaVersion(11.0f, "11")

  /**
    * Java 12
    *
    * @since 3.9
    */
  val JAVA_12 = new JavaVersion(12.0f, "12")

  /**
    * Java 13
    *
    * @since 3.9
    */
  val JAVA_13 = new JavaVersion(13.0f, "13")

  /**
    * Java 14
    *
    * @since 3.11
    */
  val JAVA_14 = new JavaVersion(14.0f, "14")

  /**
    * Java 15
    *
    * @since 3.11
    */
  val JAVA_15 = new JavaVersion(15.0f, "15")

  /**
    * Java 16
    *
    * @since 3.11
    */
  val JAVA_16 = new JavaVersion(16.0f, "16")

  /**
    * The most recent java version. Mainly introduced to avoid to break when a new version of Java is used.
    */
  val JAVA_RECENT = new JavaVersion(maxVersion, JavaFloat.toString(maxVersion))

  /**
    * Transforms the given string with a Java version number to the
    * corresponding constant of this enumeration class. This method is used
    * internally.
    *
    * @param nom the Java version as string
    * @return the corresponding enumeration constant or <b>null</b> if the
    *         version is unknown
    */
  // helper for static importing
  private[lang3] def getJavaVersion(nom: String): JavaVersion = get(nom)

  private[lang3] def get(nom: String): JavaVersion = {
    if (nom == null) return null
    else if ("0.9" == nom) return JAVA_0_9
    else if ("1.1" == nom) return JAVA_1_1
    else if ("1.2" == nom) return JAVA_1_2
    else if ("1.3" == nom) return JAVA_1_3
    else if ("1.4" == nom) return JAVA_1_4
    else if ("1.5" == nom) return JAVA_1_5
    else if ("1.6" == nom) return JAVA_1_6
    else if ("1.7" == nom) return JAVA_1_7
    else if ("1.8" == nom) return JAVA_1_8
    else if ("9" == nom) return JAVA_9
    else if ("10" == nom) return JAVA_10
    else if ("11" == nom) return JAVA_11
    else if ("12" == nom) return JAVA_12
    else if ("13" == nom) return JAVA_13
    else if ("14" == nom) return JAVA_14
    else if ("15" == nom) return JAVA_15
    else if ("16" == nom) return JAVA_16

    val v = toFloatVersion(nom)

    if ((v - 1.0f) < 1.0f) { // then we need to check decimals > .9
      val firstComma = Math.max(nom.indexOf('.'), nom.indexOf(','))
      val `end` = Math.max(nom.length, nom.indexOf(',', firstComma))
      if (JavaFloat.parseFloat(nom.substring(firstComma + 1, `end`)) > .9f) return JAVA_RECENT
    } else if (v > 10) return JAVA_RECENT

    null
  }

  /**
    * Gets the Java Version from the system or 99.0 if the {@code java.specification.version} system property is not set.
    *
    * @return the value of {@code java.specification.version} system property or 99.0 if it is not set.
    */
  private def maxVersion: Float = {
    val v = toFloatVersion(System.getProperty("java.specification.version", "99.0"))
    if (v > 0) return v
    99f
  }

  /**
    * Parses a float value from a String.
    *
    * @param value the String to parse.
    * @return the float value represented by the string or -1 if the given String can not be parsed.
    */
  private def toFloatVersion(value: String): Float = {
    val defaultReturnValue: Float = -1f
    if (value.contains(".")) {
      val toParse = value.split("\\.")
      if (toParse.length >= 2) return NumberUtils.toFloat(toParse(0) + '.' + toParse(1), defaultReturnValue)
    } else return NumberUtils.toFloat(value, defaultReturnValue)
    defaultReturnValue
  }
}
