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

package org.apache.commons.lang3.text

import java.util
import scala.collection.JavaConverters._

@deprecated object StrLookup {
  /**
    * Lookup that always returns null.
    */
  private val NONE_LOOKUP = new StrLookup.MapStrLookup[String](null)
  /**
    * Lookup based on system properties.
    */
  private val SYSTEM_PROPERTIES_LOOKUP = new StrLookup.SystemPropertiesStrLookup

  /**
    * Returns a lookup which always returns null.
    *
    * @return a lookup that always returns null, not null
    */
  def noneLookup: StrLookup[_] = NONE_LOOKUP

  /**
    * Returns a new lookup which uses a copy of the current
    * {@link java.lang.System#getProperties}.
    * <p>
    * If a security manager blocked access to system properties, then null will
    * be returned from every lookup.
    * <p>
    * If a null key is used, this lookup will throw a NullPointerException.
    *
    * @return a lookup using system properties, not null
    */
  def systemPropertiesLookup: StrLookup[String] = SYSTEM_PROPERTIES_LOOKUP

  /**
    * Returns a lookup which looks up values using a map.
    * <p>
    * If the map is null, then null will be returned from every lookup.
    * The map result object is converted to a string using toString().
    *
    * @tparam V the type of the values supported by the lookup
    * @param map the map of keys to values, may be null
    * @return a lookup using the map, not null
    */
  def mapLookup[V](map: util.Map[String, V]) = {
    if (map == null) new StrLookup.MapStrLookup[V](null.asInstanceOf[Map[String, V]])
    else new StrLookup.MapStrLookup[V](map.asScala.toMap)
  }

  def mapLookup[V](map: Map[String, V]) = new StrLookup.MapStrLookup[V](map)

  /**
    * Lookup implementation that uses a Map.
    *
    * @param map the map of keys to values, may be null
    */
  private[text] class MapStrLookup[V] private[text] (map: Map[String, V]) extends StrLookup[V] {
    /**
      * Looks up a String key to a String value using the map.
      * <p>
      * If the map is null, then null is returned.
      * The map result object is converted to a string using toString().
      *
      * @param key the key to be looked up, may be null
      * @return the matching value, null if no match
      */
    override def lookup(key: String): String = {
      if (map == null) return null
      val obj = map.getOrElse(key, null: Any)
      if (obj == null) return null
      obj.toString
    }
  }

  /**
    * Lookup implementation based on system properties.
    */
  private class SystemPropertiesStrLookup extends StrLookup[String] {
    /**
      * {@inheritDoc } This implementation directly accesses system properties.
      */
    override def lookup(key: String): String = {
      if (!key.isEmpty)
        try return System.getProperty(key)
        catch {
          case _: SecurityException =>
          // Squelched. All lookup(String) will return null.
        }
      null
    }
  }

}

/**
  * Lookup a String key to a String value.
  * <p>
  * This class represents the simplest form of a string to string map.
  * It has a benefit over a map in that it can create the result on
  * demand based on the key.
  * <p>
  * This class comes complete with various factory methods.
  * If these do not suffice, you can subclass and implement your own matcher.
  * <p>
  * For example, it would be possible to implement a lookup that used the
  * key as a primary key, and looked up the value on demand from the database.
  *
  * @tparam V Unused.
  * @since 2.2
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringLookupFactory.html">
  *             StringLookupFactory</a> instead
  */
@deprecated
abstract class StrLookup[V] protected {
  /**
    * Looks up a String key to a String value.
    * <p>
    * The internal implementation may use any mechanism to return the value.
    * The simplest implementation is to use a Map. However, virtually any
    * implementation is possible.
    * <p>
    * For example, it would be possible to implement a lookup that used the
    * key as a primary key, and looked up the value on demand from the database
    * Or, a numeric based implementation could be created that treats the key
    * as an integer, increments the value and return the result as a string -
    * converting 1 to 2, 15 to 16 etc.
    * <p>
    * The {@link #lookup ( String )} method always returns a String, regardless of
    * the underlying data, by converting it as necessary. For example:
    * <pre>
    * Map&lt;String, Object&gt; map = new HashMap&lt;String, Object&gt;();
    * map.put("number", Integer.valueOf(2));
    * assertEquals("2", StrLookup.mapLookup(map).lookup("number"));
    * </pre>
    *
    * @param key the key to be looked up, may be null
    * @return the matching value, null if no match
    */
  def lookup(key: String): String
}
