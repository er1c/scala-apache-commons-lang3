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

//package org.apache.commons.lang3.builder
//
//import org.apache.commons.lang3.ClassUtils
//
///**
//  * <p>Works with {@link ToStringBuilder} to create a "deep" {@code toString}.
//  * But instead a single line like the {@link RecursiveToStringStyle} this creates a multiline String
//  * similar to the {@link ToStringStyle# MULTI_LINE_STYLE}.</p>
//  *
//  * <p>To use this class write code as follows:</p>
//  *
//  * <pre>
//  * public class Job {
//  * String title;
//  * ...
//  * }
//  *
//  * public class Person {
//  * String name;
//  * int age;
//  * boolean smoker;
//  * Job job;
//  *
//  * ...
//  *
//  * public String toString() {
//  * return new ReflectionToStringBuilder(this, new MultilineRecursiveToStringStyle()).toString();
//  * }
//  * }
//  * </pre>
//  *
//  * <p>
//  * This will produce a toString of the format:<br>
//  * <code>Person@7f54[ <br>
//  * &nbsp; name=Stephen, <br>
//  * &nbsp; age=29, <br>
//  * &nbsp; smoker=false, <br>
//  * &nbsp; job=Job@43cd2[ <br>
//  * &nbsp; &nbsp; title=Manager <br>
//  * &nbsp;  ] <br>
//  * ]
//  * </code>
//  * </p>
//  *
//  * @since 3.4
//  */
//@SerialVersionUID(1L)
//object MultilineRecursiveToStringStyle {
//  /** Indenting of inner lines. */
//  private val INDENT = 2
//}
//
//@SerialVersionUID(1L)
//class MultilineRecursiveToStringStyle()
//
///**
//  * Constructor.
//  */
//  extends RecursiveToStringStyle {
//  resetIndent()
//  /** Current indenting. */
//  private var spaces = 2
//
//  /**
//    * Resets the fields responsible for the line breaks and indenting.
//    * Must be invoked after changing the {@link #spaces} value.
//    */
//  private def resetIndent(): Unit = {
//    setArrayStart("{" + System.lineSeparator + spacer(spaces))
//    setArraySeparator("," + System.lineSeparator + spacer(spaces))
//    setArrayEnd(System.lineSeparator + spacer(spaces - MultilineRecursiveToStringStyle.INDENT) + "}")
//    setContentStart("[" + System.lineSeparator + spacer(spaces))
//    setFieldSeparator("," + System.lineSeparator + spacer(spaces))
//    setContentEnd(System.lineSeparator + spacer(spaces - MultilineRecursiveToStringStyle.INDENT) + "]")
//  }
//
//  /**
//    * Creates a StringBuilder responsible for the indenting.
//    *
//    * @param spaces how far to indent
//    * @return a StringBuilder with {spaces} leading space characters.
//    */
//  private def spacer(spaces: Int) = {
//    val sb = new StringBuilder
//    for (i <- 0 until spaces) {
//      sb.append(" ")
//    }
//    sb
//  }
//
//  override def appendDetail(buffer: StringBuffer, fieldName: String, value: Any): Unit = {
//    if (!ClassUtils.isPrimitiveWrapper(value.getClass) && !(classOf[String] == value.getClass) && accept(value.getClass)) {
//      spaces += MultilineRecursiveToStringStyle.INDENT
//      resetIndent()
//      buffer.append(ReflectionToStringBuilder.toString(value, this))
//      spaces -= MultilineRecursiveToStringStyle.INDENT
//      resetIndent()
//    }
//    else super.appendDetail(buffer, fieldName, value)
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[AnyRef]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def reflectionAppendArrayDetail(buffer: StringBuffer, fieldName: String, array: Any): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.reflectionAppendArrayDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Long]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Int]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Short]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Byte]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Char]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Double]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Float]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//
//  override protected def appendDetail(buffer: StringBuffer, fieldName: String, array: Array[Boolean]): Unit = {
//    spaces += MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//    super.appendDetail(buffer, fieldName, array)
//    spaces -= MultilineRecursiveToStringStyle.INDENT
//    resetIndent()
//  }
//}
