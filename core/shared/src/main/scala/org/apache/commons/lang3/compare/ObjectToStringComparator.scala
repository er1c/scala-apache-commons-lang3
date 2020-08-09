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

package org.apache.commons.lang3.compare

import java.io.Serializable
import java.util.Comparator

/**
  * Compares Object's {@link java.lang.Object#toString} values.
  *
  * This class is stateless.
  *
  * @since 3.10
  */
@SerialVersionUID(1L)
object ObjectToStringComparator {
  /**
    * Singleton instance.
    *
    * This class is stateless.
    */
  val INSTANCE = new ObjectToStringComparator
}

@SerialVersionUID(1L)
final class ObjectToStringComparator extends Comparator[AnyRef] with Serializable {
  override def compare(o1: AnyRef, o2: AnyRef): Int = {
    if (o1 == null && o2 == null) return 0
    if (o1 == null) return 1
    if (o2 == null) return -1
    val string1 = o1.toString
    val string2 = o2.toString
    // No guarantee that toString() returns a non-null value, despite what Spotbugs thinks.
    if (string1 == null && string2 == null) return 0
    if (string1 == null) return 1
    if (string2 == null) return -1
    string1.compareTo(string2)
  }
}