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

package lang3

import org.scalatestplus.junit.JUnitSuite
import org.apache.commons.lang3.ArchUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.apache.commons.lang3.arch.Processor
import org.apache.commons.lang3.arch.Processor.Arch
import org.junit.Test

/**
  * Test class for {@link org.apache.commons.lang3.ArchUtils}.
  */
object ArchUtilsTest {
  private val IA64 = "ia64"
  private val IA64_32 = "ia64_32"
  private val PPC = "ppc"
  private val PPC64 = "ppc64"
  private val X86 = "x86"
  private val X86_64 = "x86_64"
}

class ArchUtilsTest extends JUnitSuite {
  private def assertEqualsArchNotNull(arch: Processor.Arch, processor: Processor): Unit = {
    assertNotNull(arch)
    assertNotNull(processor)
    assertEquals(arch, processor.getArch)
  }

  private def assertEqualsTypeNotNull(`type`: Processor.Type, processor: Processor): Unit = {
    assertNotNull(`type`)
    assertNotNull(processor)
    assertEquals(`type`, processor.getType)
  }

  private def assertNotEqualsArchNotNull(arch: Processor.Arch, processor: Processor): Unit = {
    assertNotNull(arch)
    assertNotNull(processor)
    assertNotEquals(arch, processor.getArch)
  }

  private def assertNotEqualsTypeNotNull(`type`: Processor.Type, processor: Processor): Unit = {
    assertNotNull(`type`)
    assertNotNull(processor)
    assertNotEquals(`type`, processor.getType)
  }

  @Test def testArch(): Unit = {
    var processor = ArchUtils.getProcessor(ArchUtilsTest.X86)
    assertEqualsTypeNotNull(Processor.Type.X86, processor)
    assertTrue(processor.isX86)
    assertNotEqualsTypeNotNull(Processor.Type.PPC, processor)
    assertFalse(processor.isPPC)
    processor = ArchUtils.getProcessor(ArchUtilsTest.X86_64)
    assertEqualsTypeNotNull(Processor.Type.X86, processor)
    assertTrue(processor.isX86)
    processor = ArchUtils.getProcessor(ArchUtilsTest.IA64_32)
    assertEqualsTypeNotNull(Processor.Type.IA_64, processor)
    assertTrue(processor.isIA64)
    processor = ArchUtils.getProcessor(ArchUtilsTest.IA64)
    assertEqualsTypeNotNull(Processor.Type.IA_64, processor)
    assertTrue(processor.isIA64)
    assertNotEqualsTypeNotNull(Processor.Type.X86, processor)
    assertFalse(processor.isX86)
    processor = ArchUtils.getProcessor(ArchUtilsTest.PPC)
    assertEqualsTypeNotNull(Processor.Type.PPC, processor)
    assertTrue(processor.isPPC)
    assertNotEqualsTypeNotNull(Processor.Type.IA_64, processor)
    assertFalse(processor.isIA64)
    processor = ArchUtils.getProcessor(ArchUtilsTest.PPC64)
    assertEqualsTypeNotNull(Processor.Type.PPC, processor)
    assertTrue(processor.isPPC)
  }

  @Test def testArchLabels(): Unit = {
    for (arch: Processor.Arch <- Arch.values) { // Only test label presence.
      assertFalse(arch.getLabel.isEmpty)
    }
  }

  @Test def testGetProcessor(): Unit = {
    assertNotNull(ArchUtils.getProcessor(ArchUtilsTest.X86))
    assertNull(ArchUtils.getProcessor("NA"))
  }

  @Test def testIs32BitJVM(): Unit = {
    var processor = ArchUtils.getProcessor(ArchUtilsTest.X86)
    assertEqualsArchNotNull(Processor.Arch.BIT_32, processor)
    assertTrue(processor.is32Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.IA64_32)
    assertEqualsArchNotNull(Processor.Arch.BIT_32, processor)
    assertTrue(processor.is32Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.PPC)
    assertEqualsArchNotNull(Processor.Arch.BIT_32, processor)
    processor.is32Bit
    processor = ArchUtils.getProcessor(ArchUtilsTest.X86_64)
    assertNotEqualsArchNotNull(Processor.Arch.BIT_32, processor)
    assertFalse(processor.is32Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.PPC64)
    assertNotEqualsArchNotNull(Processor.Arch.BIT_32, processor)
    assertFalse(processor.is32Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.IA64)
    assertNotEqualsArchNotNull(Processor.Arch.BIT_32, processor)
    assertFalse(processor.is32Bit)
  }

  @Test def testIs64BitJVM(): Unit = {
    var processor = ArchUtils.getProcessor(ArchUtilsTest.X86_64)
    assertEqualsArchNotNull(Processor.Arch.BIT_64, processor)
    assertTrue(processor.is64Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.PPC64)
    assertEqualsArchNotNull(Processor.Arch.BIT_64, processor)
    assertTrue(processor.is64Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.IA64)
    assertEqualsArchNotNull(Processor.Arch.BIT_64, processor)
    assertTrue(processor.is64Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.X86)
    assertNotEqualsArchNotNull(Processor.Arch.BIT_64, processor)
    assertFalse(processor.is64Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.PPC)
    assertNotEqualsArchNotNull(Processor.Arch.BIT_64, processor)
    assertFalse(processor.is64Bit)
    processor = ArchUtils.getProcessor(ArchUtilsTest.IA64_32)
    assertNotEqualsArchNotNull(Processor.Arch.BIT_64, processor)
    assertFalse(processor.is64Bit)
  }
}
