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

import java.text.FieldPosition
import java.text.Format
import java.text.ParseException
import java.text.ParsePosition

/**
  * Formats using one formatter and parses using a different formatter. An
  * example of use for this would be a webapp where data is taken in one way and
  * stored in a database another way.
  *
  * Create a format that points its parseObject method to one implementation
  * and its format method to another.
  *
  * @param parser    The parser to use
  * @param formatter The formatter to use
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/CompositeFormat.html">
  *             CompositeFormat</a> instead
  */
@deprecated
@SerialVersionUID(-4329119827877627683L)
class CompositeFormat(parser: Format, formatter: Format) extends Format {
  /**
    * Uses the formatter Format instance.
    *
    * @param obj        the object to format
    * @param toAppendTo the {@link java.lang.StringBuffer} to append to
    * @param pos        the FieldPosition to use (or ignore).
    * @return {@code toAppendTo}
    * @see Format#format(Object, StringBuffer, FieldPosition)
    */
  override // Therefore has to use StringBuffer
  def format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition): StringBuffer =
    formatter.format(obj, toAppendTo, pos)

  /**
    * Uses the parser Format instance.
    *
    * @param source the String source
    * @param pos    the ParsePosition containing the position to parse from, will
    *               be updated according to parsing success (index) or failure
    *               (error index)
    * @return the parsed Object
    * @see Format#parseObject(String, ParsePosition)
    */
  override def parseObject(source: String, pos: ParsePosition): AnyRef = parser.parseObject(source, pos)

  /**
    * Provides access to the parser Format implementation.
    *
    * @return parser Format implementation
    */
  def getParser: Format = this.parser

  /**
    * Provides access to the parser Format implementation.
    *
    * @return formatter Format implementation
    */
  def getFormatter: Format = this.formatter

  /**
    * Utility method to parse and then reformat a String.
    *
    * @param input String to reformat
    * @return A reformatted String
    * @throws java.text.ParseException thrown by parseObject(String) call
    */
  @throws[ParseException]
  def reformat(input: String): String = format(parseObject(input))
}
