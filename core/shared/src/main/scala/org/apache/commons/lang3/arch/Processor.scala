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

package org.apache.commons.lang3.arch

/**
  * The {@link Processor} represents a microprocessor and defines
  * some properties like architecture and type of the microprocessor.
  *
  * @since 3.6
  */
object Processor {

  type Arch = Arch.Value

  /**
    * The {@link Arch} enum defines the architecture of
    * a microprocessor. The architecture represents the bit value
    * of the microprocessor.
    * The following architectures are defined:
    * <ul>
    * <li>32-bit</li>
    * <li>64-bit</li>
    * <li>Unknown</li>
    * </ul>
    */
  object Arch extends Enumeration {
    final class Arch(label: String) extends super.Val(label) {
      /**
        * Gets the label suitable for display.
        *
        * @return the label.
        */
      def getLabel: String = label
    }

    /**
      * A 32-bit processor architecture.
      */
    val BIT_32 = new Arch("32-bit")

    /**
      * A 64-bit processor architecture.
      */
    val BIT_64 = new Arch("64-bit")

    /**
      * An unknown-bit processor architecture.
      */
    val UNKNOWN = new Arch("Unknown")

  }

  type Type = Type.Value

  /**
    * The {@link Processor.Type} enum defines types of a microprocessor.
    * The following types are defined:
    * <ul>
    * <li>x86</li>
    * <li>ia64</li>
    * <li>PPC</li>
    * <li>Unknown</li>
    * </ul>
    */
  object Type extends Enumeration {
    /**
      * Intel x86 series of instruction set architectures.
      */
    val X86 = Value

    /**
      * Intel Itanium  64-bit architecture.
      */
    val IA_64 = Value

    /**
      * Apple–IBM–Motorola PowerPC architecture.
      */
    val PPC = Value

    /**
      * Unknown architecture.
      */
    val UNKNOWN = Value
  }
}

/**
  * Constructs a {@link Processor} object with the given
  * parameters.
  *
  * @param arch The processor architecture.
  * @param type The processor type.
  */
class Processor(val arch: Processor.Arch, val `type`: Processor.Type) {
  /**
    * Returns the processor architecture as an {@link Processor.Arch} enum.
    * The processor architecture defines, if the processor has
    * a 32 or 64 bit architecture.
    *
    * @return A {@link Processor.Arch} enum.
    */
  def getArch: Processor.Arch = arch

  /**
    * Returns the processor type as {@link Processor.Type} enum.
    * The processor type defines, if the processor is for example
    * a x86 or PPA.
    *
    * @return A {@link Processor#Type} enum.
    */
  def getType: Processor.Type = `type`

  /**
    * Checks if {@link Processor} is 32 bit.
    *
    * @return {@code true}, if {@link Processor} is {@link Processor.Arch#BIT_32}, else {@code false}.
    */
  def is32Bit: Boolean = Processor.Arch.BIT_32 == arch

  /**
    * Checks if {@link Processor} is 64 bit.
    *
    * @return {@code true}, if {@link Processor} is {@link Processor.Arch#BIT_64}, else {@code false}.
    */
  def is64Bit: Boolean = Processor.Arch.BIT_64 == arch

  /**
    * Checks if {@link Processor} is type of x86.
    *
    * @return {@code true}, if {@link Processor} is {@link Processor.Type#X86}, else {@code false}.
    */
  def isX86: Boolean = Processor.Type.X86 == `type`

  /**
    * Checks if {@link Processor} is type of Intel Itanium.
    *
    * @return {@code true}. if {@link Processor} is {@link Processor.Type#IA_64}, else {@code false}.
    */
  def isIA64: Boolean = Processor.Type.IA_64 == `type`

  /**
    * Checks if {@link Processor} is type of Power PC.
    *
    * @return {@code true}. if {@link Processor} is {@link Processor.Type#PPC}, else {@code false}.
    */
  def isPPC: Boolean = Processor.Type.PPC == `type`
}
