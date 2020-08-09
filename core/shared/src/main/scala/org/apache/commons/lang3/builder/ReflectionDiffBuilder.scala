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
//import org.apache.commons.lang3.reflect.FieldUtils.readField
//import java.lang.reflect.Field
//import java.lang.reflect.Modifier
//import org.apache.commons.lang3.ClassUtils
//import org.apache.commons.lang3.reflect.FieldUtils
//
///**
//  * <p>
//  * Assists in implementing {@link Diffable# diff ( Object )} methods.
//  * </p>
//  * <p>
//  * All non-static, non-transient fields (including inherited fields)
//  * of the objects to diff are discovered using reflection and compared
//  * for differences.
//  * </p>
//  *
//  * <p>
//  * To use this class, write code as follows:
//  * </p>
//  *
//  * <pre>
//  * public class Person implements Diffable&lt;Person&gt; {
//  * String name;
//  * int age;
//  * boolean smoker;
//  * ...
//  *
//  * public DiffResult diff(Person obj) {
//  * // No need for null check, as NullPointerException correct if obj is null
//  * return new ReflectionDiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE)
//  * .build();
//  * }
//  * }
//  * </pre>
//  *
//  * <p>
//  * The {@code ToStringStyle} passed to the constructor is embedded in the
//  * returned {@code DiffResult} and influences the style of the
//  * {@code DiffResult.toString()} method. This style choice can be overridden by
//  * calling {@link DiffResult# toString ( ToStringStyle )}.
//  * </p>
//  *
//  * @param < T>
//  *          type of the left and right object to diff.
//  * @see Diffable
//  * @see Diff
//  * @see DiffResult
//  * @see ToStringStyle
//  * @since 3.6
//  */
//class ReflectionDiffBuilder[T](val left: Any, val right: Any, val style: ToStringStyle)
//
///**
//  * <p>
//  * Constructs a builder for the specified objects with the specified style.
//  * </p>
//  *
//  * <p>
//  * If {@code lhs == rhs} or {@code lhs.equals(rhs)} then the builder will
//  * not evaluate any calls to {@code append(...)} and will return an empty
//  * {@link DiffResult} when {@link #build ( )} is executed.
//  * </p>
//  *
//  * @param lhs
//  * {@code this} object
//  * @param rhs
//  * the object to diff against
//  * @param style
//  * the style will use when outputting the objects, {@code null}
//  * uses the default
//  * @throws java.lang.IllegalArgumentException
//  * if {@code lhs} or {@code rhs} is {@code null}
//  */
//  extends Builder[DiffResult] {
//  diffBuilder = new DiffBuilder(left, right, style)
//  final private var diffBuilder = null
//
//  override def build: DiffResult = {
//    if (left == right) return diffBuilder.build
//    appendFields(left.getClass)
//    diffBuilder.build
//  }
//
//  private def appendFields(clazz: Class[_]): Unit = {
//    for (field <- FieldUtils.getAllFields(clazz)) {
//      if (accept(field)) try diffBuilder.append(field.getName, readField(field, left, true), readField(field, right, true))
//      catch {
//        case ex: IllegalAccessException =>
//          //this can't happen. Would get a Security exception instead
//          //throw a runtime exception in case the impossible happens.
//          throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage)
//      }
//    }
//  }
//
//  private def accept(field: Field): Boolean = {
//    if (field.getName.indexOf(ClassUtils.INNER_CLASS_SEPARATOR_CHAR) != -1) return false
//    if (Modifier.isTransient(field.getModifiers)) return false
//    !Modifier.isStatic(field.getModifiers)
//  }
//}
