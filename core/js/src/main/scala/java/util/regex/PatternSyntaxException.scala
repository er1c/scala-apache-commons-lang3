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

package java.util.regex


/**
  * Unchecked exception thrown to indicate a syntax error in a
  * regular-expression pattern.
  *
  * Constructs a new instance of this class.
  *
  * @param desc
  * A description of the error
  * @param pattern
  * The erroneous pattern
  * @param index
  * The approximate index in the pattern of the error,
  * or {@code -1} if the index is not known
  */
@SerialVersionUID(-3864639126226059218L)
class PatternSyntaxException(val desc: String, val pattern: String, val index: Int) extends IllegalArgumentException {
  /**
    * Retrieves the error index.
    *
    * @return The approximate index in the pattern of the error,
    *         or {@code -1} if the index is not known
    */
  def getIndex: Int = index

  /**
    * Retrieves the description of the error.
    *
    * @return The description of the error
    */
  def getDescription: String = desc

  /**
    * Retrieves the erroneous regular-expression pattern.
    *
    * @return The erroneous pattern
    */
  def getPattern: String = pattern

  /**
    * Returns a multi-line string containing the description of the syntax
    * error and its index, the erroneous regular-expression pattern, and a
    * visual indication of the error index within the pattern.
    *
    * @return The full detail message
    */
  override def getMessage: String = {
    val sb = new StringBuilder
    sb.append(desc)
    if (index >= 0) {
      sb.append(" near index ")
      sb.append(index)
    }
    sb.append(System.lineSeparator)
    sb.append(pattern)
    if (index >= 0 && pattern != null && index < pattern.length) {
      sb.append(System.lineSeparator)
      for (_ <- 0 until index) sb.append(' ')
      sb.append('^')
    }
    sb.toString
  }
}
