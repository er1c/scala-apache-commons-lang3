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

package org.apache.commons.lang3.mutable

import java.lang.{Double => JavaDouble}
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test

/**
  * JUnit tests.
  *
  * @see MutableShort
  */
class MutableObjectTest {
  @Test def testConstructors(): Unit = {
    assertNull(new MutableObject[String]().getValue)
    val i = Integer.valueOf(6)
    assertSame(i, new MutableObject[Integer](i).getValue)
    assertSame("HI", new MutableObject[String]("HI").getValue)
    assertSame(null, new MutableObject[AnyRef](null).getValue)
  }

  @Test def testGetSet(): Unit = {
    val mutNum = new MutableObject[String]
    assertNull(new MutableObject[AnyRef]().getValue)
    mutNum.setValue("HELLO")
    assertSame("HELLO", mutNum.getValue)
    mutNum.setValue(null)
    assertSame(null, mutNum.getValue)
  }

  @Test def testEquals(): Unit = {
    val mutNumA = new MutableObject[String]("ALPHA")
    val mutNumB = new MutableObject[String]("ALPHA")
    val mutNumC = new MutableObject[String]("BETA")
    val mutNumD = new MutableObject[String](null)
    assertEquals(mutNumA, mutNumA)
    assertEquals(mutNumA, mutNumB)
    assertEquals(mutNumB, mutNumA)
    assertEquals(mutNumB, mutNumB)
    assertNotEquals(mutNumA, mutNumC)
    assertNotEquals(mutNumB, mutNumC)
    assertEquals(mutNumC, mutNumC)
    assertNotEquals(mutNumA, mutNumD)
    assertEquals(mutNumD, mutNumD)
    assertNotEquals(null, mutNumA)
    assertNotEquals(mutNumA, new AnyRef)
    assertNotEquals("0", mutNumA)
  }

  @Test def testHashCode(): Unit = {
    val mutNumA = new MutableObject[String]("ALPHA")
    val mutNumB = new MutableObject[String]("ALPHA")
    val mutNumC = new MutableObject[String]("BETA")
    val mutNumD = new MutableObject[String](null)
    assertEquals(mutNumA.hashCode, mutNumA.hashCode)
    assertEquals(mutNumA.hashCode, mutNumB.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumC.hashCode)
    assertNotEquals(mutNumA.hashCode, mutNumD.hashCode)
    assertEquals(mutNumA.hashCode, "ALPHA".hashCode)
    assertEquals(0, mutNumD.hashCode)
  }

  @Test def testToString(): Unit = {
    assertEquals("HI", new MutableObject[String]("HI").toString)
    assertEquals("10.0", new MutableObject[JavaDouble](JavaDouble.valueOf(10)).toString)
    assertEquals("null", new MutableObject[AnyRef](null).toString)
  }
}
