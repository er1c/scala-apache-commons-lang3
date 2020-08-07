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

package org.apache.commons.lang3.builder

/**
  * <p>Works with {@link ToStringBuilder} to create a {@code toString}.</p>
  *
  * <p>This class is intended to be used as a singleton.
  * There is no need to instantiate a new style each time.
  * Simply instantiate the class once, customize the values as required, and
  * store the result in a public static final variable for the rest of the
  * program to access.</p>
  *
  * @since 1.0
  */
@SerialVersionUID(1L)
class StandardToStringStyle()

/**
  * <p>Constructor.</p>
  */
  extends ToStringStyle {
  /**
    * <p>Gets whether to use the class name.</p>
    *
    * @return the current useClassName flag
    */
  override def isUseClassName: Boolean = { // NOPMD as this is implementing the abstract class
    super.isUseClassName
  }

  /**
    * <p>Sets whether to use the class name.</p>
    *
    * @param useClassName the new useClassName flag
    */
  override def setUseClassName(useClassName: Boolean): Unit = {
    super.setUseClassName(useClassName)
  }

  /**
    * <p>Gets whether to output short or long class names.</p>
    *
    * @return the current useShortClassName flag
    * @since 2.0
    */
  override def isUseShortClassName: Boolean = super.isUseShortClassName

  /**
    * <p>Sets whether to output short or long class names.</p>
    *
    * @param useShortClassName the new useShortClassName flag
    * @since 2.0
    */
  override def setUseShortClassName(useShortClassName: Boolean): Unit = {
    super.setUseShortClassName(useShortClassName)
  }

  /**
    * <p>Gets whether to use the identity hash code.</p>
    *
    * @return the current useIdentityHashCode flag
    */
  override def isUseIdentityHashCode: Boolean = super.isUseIdentityHashCode

  /**
    * <p>Sets whether to use the identity hash code.</p>
    *
    * @param useIdentityHashCode the new useIdentityHashCode flag
    */
  override def setUseIdentityHashCode(useIdentityHashCode: Boolean): Unit = {
    super.setUseIdentityHashCode(useIdentityHashCode)
  }

  /**
    * <p>Gets whether to use the field names passed in.</p>
    *
    * @return the current useFieldNames flag
    */
  override def isUseFieldNames: Boolean = super.isUseFieldNames

  /**
    * <p>Sets whether to use the field names passed in.</p>
    *
    * @param useFieldNames the new useFieldNames flag
    */
  override def setUseFieldNames(useFieldNames: Boolean): Unit = {
    super.setUseFieldNames(useFieldNames)
  }

  /**
    * <p>Gets whether to use full detail when the caller doesn't
    * specify.</p>
    *
    * @return the current defaultFullDetail flag
    */
  override def isDefaultFullDetail: Boolean = super.isDefaultFullDetail

  /**
    * <p>Sets whether to use full detail when the caller doesn't
    * specify.</p>
    *
    * @param defaultFullDetail the new defaultFullDetail flag
    */
  override def setDefaultFullDetail(defaultFullDetail: Boolean): Unit = {
    super.setDefaultFullDetail(defaultFullDetail)
  }

  /**
    * <p>Gets whether to output array content detail.</p>
    *
    * @return the current array content detail setting
    */
  override def isArrayContentDetail: Boolean = super.isArrayContentDetail

  /**
    * <p>Sets whether to output array content detail.</p>
    *
    * @param arrayContentDetail the new arrayContentDetail flag
    */
  override def setArrayContentDetail(arrayContentDetail: Boolean): Unit = {
    super.setArrayContentDetail(arrayContentDetail)
  }

  /**
    * <p>Gets the array start text.</p>
    *
    * @return the current array start text
    */
  override def getArrayStart: String = super.getArrayStart

  /**
    * <p>Sets the array start text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param arrayStart the new array start text
    */
  override def setArrayStart(arrayStart: String): Unit = {
    super.setArrayStart(arrayStart)
  }

  /**
    * <p>Gets the array end text.</p>
    *
    * @return the current array end text
    */
  override def getArrayEnd: String = super.getArrayEnd

  /**
    * <p>Sets the array end text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param arrayEnd the new array end text
    */
  override def setArrayEnd(arrayEnd: String): Unit = {
    super.setArrayEnd(arrayEnd)
  }

  /**
    * <p>Gets the array separator text.</p>
    *
    * @return the current array separator text
    */
  override def getArraySeparator: String = super.getArraySeparator

  /**
    * <p>Sets the array separator text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param arraySeparator the new array separator text
    */
  override def setArraySeparator(arraySeparator: String): Unit = {
    super.setArraySeparator(arraySeparator)
  }

  /**
    * <p>Gets the content start text.</p>
    *
    * @return the current content start text
    */
  override def getContentStart: String = super.getContentStart

  /**
    * <p>Sets the content start text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param contentStart the new content start text
    */
  override def setContentStart(contentStart: String): Unit = {
    super.setContentStart(contentStart)
  }

  /**
    * <p>Gets the content end text.</p>
    *
    * @return the current content end text
    */
  override def getContentEnd: String = super.getContentEnd

  /**
    * <p>Sets the content end text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param contentEnd the new content end text
    */
  override def setContentEnd(contentEnd: String): Unit = {
    super.setContentEnd(contentEnd)
  }

  /**
    * <p>Gets the field name value separator text.</p>
    *
    * @return the current field name value separator text
    */
  override def getFieldNameValueSeparator: String = super.getFieldNameValueSeparator

  /**
    * <p>Sets the field name value separator text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param fieldNameValueSeparator the new field name value separator text
    */
  override def setFieldNameValueSeparator(fieldNameValueSeparator: String): Unit = {
    super.setFieldNameValueSeparator(fieldNameValueSeparator)
  }

  /**
    * <p>Gets the field separator text.</p>
    *
    * @return the current field separator text
    */
  override def getFieldSeparator: String = super.getFieldSeparator

  /**
    * <p>Sets the field separator text.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param fieldSeparator the new field separator text
    */
  override def setFieldSeparator(fieldSeparator: String): Unit = {
    super.setFieldSeparator(fieldSeparator)
  }

  /**
    * <p>Gets whether the field separator should be added at the start
    * of each buffer.</p>
    *
    * @return the fieldSeparatorAtStart flag
    * @since 2.0
    */
  override def isFieldSeparatorAtStart: Boolean = super.isFieldSeparatorAtStart

  /**
    * <p>Sets whether the field separator should be added at the start
    * of each buffer.</p>
    *
    * @param fieldSeparatorAtStart the fieldSeparatorAtStart flag
    * @since 2.0
    */
  override def setFieldSeparatorAtStart(fieldSeparatorAtStart: Boolean): Unit = {
    super.setFieldSeparatorAtStart(fieldSeparatorAtStart)
  }

  /**
    * <p>Gets whether the field separator should be added at the end
    * of each buffer.</p>
    *
    * @return fieldSeparatorAtEnd flag
    * @since 2.0
    */
  override def isFieldSeparatorAtEnd: Boolean = super.isFieldSeparatorAtEnd

  /**
    * <p>Sets whether the field separator should be added at the end
    * of each buffer.</p>
    *
    * @param fieldSeparatorAtEnd the fieldSeparatorAtEnd flag
    * @since 2.0
    */
  override def setFieldSeparatorAtEnd(fieldSeparatorAtEnd: Boolean): Unit = {
    super.setFieldSeparatorAtEnd(fieldSeparatorAtEnd)
  }

  /**
    * <p>Gets the text to output when {@code null} found.</p>
    *
    * @return the current text to output when {@code null} found
    */
  override def getNullText: String = super.getNullText

  /**
    * <p>Sets the text to output when {@code null} found.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param nullText the new text to output when {@code null} found
    */
  override def setNullText(nullText: String): Unit = {
    super.setNullText(nullText)
  }

  /**
    * <p>Gets the text to output when a {@code Collection},
    * {@code Map} or {@code Array} size is output.</p>
    *
    * <p>This is output before the size value.</p>
    *
    * @return the current start of size text
    */
  override def getSizeStartText: String = super.getSizeStartText

  /**
    * <p>Sets the start text to output when a {@code Collection},
    * {@code Map} or {@code Array} size is output.</p>
    *
    * <p>This is output before the size value.</p>
    *
    * <p>{@code null} is accepted, but will be converted to
    * an empty String.</p>
    *
    * @param sizeStartText the new start of size text
    */
  override def setSizeStartText(sizeStartText: String): Unit = {
    super.setSizeStartText(sizeStartText)
  }

  /**
    * <p>Gets the end text to output when a {@code Collection},
    * {@code Map} or {@code Array} size is output.</p>
    *
    * <p>This is output after the size value.</p>
    *
    * @return the current end of size text
    */
  override def getSizeEndText: String = super.getSizeEndText

  /**
    * <p>Sets the end text to output when a {@code Collection},
    * {@code Map} or {@code Array} size is output.</p>
    *
    * <p>This is output after the size value.</p>
    *
    * <p>{@code null} is accepted, but will be converted
    * to an empty String.</p>
    *
    * @param sizeEndText the new end of size text
    */
  override def setSizeEndText(sizeEndText: String): Unit = {
    super.setSizeEndText(sizeEndText)
  }

  /**
    * <p>Gets the start text to output when an {@code Object} is
    * output in summary mode.</p>
    *
    * <P>This is output before the size value.</p>
    *
    * @return the current start of summary text
    */
  override def getSummaryObjectStartText: String = super.getSummaryObjectStartText

  /**
    * <p>Sets the start text to output when an {@code Object} is
    * output in summary mode.</p>
    *
    * <p>This is output before the size value.</p>
    *
    * <p>{@code null} is accepted, but will be converted to
    * an empty String.</p>
    *
    * @param summaryObjectStartText the new start of summary text
    */
  override def setSummaryObjectStartText(summaryObjectStartText: String): Unit = {
    super.setSummaryObjectStartText(summaryObjectStartText)
  }

  /**
    * <p>Gets the end text to output when an {@code Object} is
    * output in summary mode.</p>
    *
    * <p>This is output after the size value.</p>
    *
    * @return the current end of summary text
    */
  override def getSummaryObjectEndText: String = super.getSummaryObjectEndText

  /**
    * <p>Sets the end text to output when an {@code Object} is
    * output in summary mode.</p>
    *
    * <p>This is output after the size value.</p>
    *
    * <p>{@code null} is accepted, but will be converted to
    * an empty String.</p>
    *
    * @param summaryObjectEndText the new end of summary text
    */
  override def setSummaryObjectEndText(summaryObjectEndText: String): Unit = {
    super.setSummaryObjectEndText(summaryObjectEndText)
  }

  //---------------------------------------------------------------------
}