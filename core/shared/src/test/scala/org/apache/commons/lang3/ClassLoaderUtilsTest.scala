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

import java.io.IOException
import java.net.URL
import java.net.URLClassLoader
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Tests {@link ClassLoaderUtils}.
  */
class ClassLoaderUtilsTest extends JUnitSuite {
  @Test
  @throws[IOException]
  def testToString_ClassLoader(): Unit = {
    val url = new URL("http://localhost")
    val urlClassLoader = new URLClassLoader(Array[URL](url))
    try {
      @SuppressWarnings(Array("resource")) val classLoader = urlClassLoader
      assertEquals(String.format("%s[%s]", classLoader, url), ClassLoaderUtils.toString(classLoader))
    } finally {
      if (urlClassLoader != null) urlClassLoader.close()
    }
  }

  @Test
  @throws[IOException]
  def testToString_URLClassLoader(): Unit = {
    val url = new URL("http://localhost")

    val urlClassLoader = new URLClassLoader(Array[URL](url))
    try {
      assertEquals(String.format("%s[%s]", urlClassLoader, url), ClassLoaderUtils.toString(urlClassLoader))
    } finally {
      if (urlClassLoader != null) urlClassLoader.close()
    }
  }
}
