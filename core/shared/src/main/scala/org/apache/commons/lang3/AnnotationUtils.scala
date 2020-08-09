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

///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package org.apache.commons.lang3
//
//import java.lang.{Boolean => JavaBoolean, Byte => JavaByte, Double => JavaDouble, Float => JavaFloat, Long => JavaLong, Short => JavaShort}
//import java.lang.annotation.Annotation
//import java.lang.reflect.InvocationTargetException
//import java.lang.reflect.Method
//import java.util
//import org.apache.commons.lang3.builder.ToStringBuilder
//import org.apache.commons.lang3.builder.ToStringStyle
//
///**
//  * <p>Helper methods for working with {@link Annotation} instances.</p>
//  *
//  * <p>This class contains various utility methods that make working with
//  * annotations simpler.</p>
//  *
//  * <p>{@link Annotation} instances are always proxy objects; unfortunately
//  * dynamic proxies cannot be depended upon to know how to implement certain
//  * methods in the same manner as would be done by "natural" {@link Annotation}s.
//  * The methods presented in this class can be used to avoid that possibility. It
//  * is of course also possible for dynamic proxies to actually delegate their
//  * e.g. {@link Annotation# equals ( Object )}/{@link Annotation# hashCode ( )}/
//  * {@link Annotation# toString ( )} implementations to {@link AnnotationUtils}.</p>
//  *
//  * <p>#ThreadSafe#</p>
//  *
//  * @since 3.0
//  */
//object AnnotationUtils {
//  /**
//    * A style that prints annotations as recommended.
//    */
//  private val TO_STRING_STYLE = new ToStringStyle() {
//    /**
//      * {@inheritDoc }
//      */
//    override protected def getShortClassName(cls: Class[_]): String = {
//      import scala.collection.JavaConversions._
//      for (iface <- ClassUtils.getAllInterfaces(cls)) {
//        if (classOf[Annotation].isAssignableFrom(iface)) return "@" + iface.getName
//      }
//      StringUtils.EMPTY
//    }
//
//    override
//
//    protected def appendDetail(buffer: StringBuffer, fieldName: String, value: Any): Unit = {
//      if (value.isInstanceOf[Annotation]) value = AnnotationUtils.toString(value.asInstanceOf[Annotation])
//      super.appendDetail(buffer, fieldName, value)
//    }
//  }
//
//  /**
//    * <p>Checks if two annotations are equal using the criteria for equality
//    * presented in the {@link Annotation# equals ( Object )} API docs.</p>
//    *
//    * @param a1 the first Annotation to compare, {@code null} returns
//    *           {@code false} unless both are {@code null}
//    * @param a2 the second Annotation to compare, {@code null} returns
//    *           {@code false} unless both are {@code null}
//    * @return {@code true} if the two annotations are {@code equal} or both
//    *         {@code null}
//    */
//  def equals(a1: Annotation, a2: Annotation): Boolean = {
//    if (a1 eq a2) return true
//    if (a1 == null || a2 == null) return false
//    val type1 = a1.annotationType
//    val type2 = a2.annotationType
//    Validate.notNull(type1, "Annotation %s with null annotationType()", a1)
//    Validate.notNull(type2, "Annotation %s with null annotationType()", a2)
//    if (!(type1 == type2)) return false
//    try for (m <- type1.getDeclaredMethods) {
//      if (m.getParameterTypes.length == 0 && isValidAnnotationMemberType(m.getReturnType)) {
//        val v1 = m.invoke(a1)
//        val v2 = m.invoke(a2)
//        if (!memberEquals(m.getReturnType, v1, v2)) return false
//      }
//    }
//    catch {
//      case ex@(_: IllegalAccessException | _: InvocationTargetException) =>
//        return false
//    }
//    true
//  }
//
//  /**
//    * <p>Generate a hash code for the given annotation using the algorithm
//    * presented in the {@link Annotation# hashCode ( )} API docs.</p>
//    *
//    * @param a the Annotation for a hash code calculation is desired, not
//    *          {@code null}
//    * @return the calculated hash code
//    * @throws RuntimeException      if an {@code Exception} is encountered during
//    *                               annotation member access
//    * @throws IllegalStateException if an annotation method invocation returns
//    *                               {@code null}
//    */
//  def hashCode(a: Annotation): Int = {
//    var result = 0
//    val `type` = a.annotationType
//    for (m <- `type`.getDeclaredMethods) {
//      try {
//        val value = m.invoke(a)
//        if (value == null) throw new IllegalStateException(String.format("Annotation method %s returned null", m))
//        result += hashMember(m.getName, value)
//      } catch {
//        case ex: RuntimeException =>
//          throw ex
//        case ex: Exception =>
//          throw new RuntimeException(ex)
//      }
//    }
//    result
//  }
//
//  /**
//    * <p>Generate a string representation of an Annotation, as suggested by
//    * {@link Annotation# toString ( )}.</p>
//    *
//    * @param a the annotation of which a string representation is desired
//    * @return the standard string representation of an annotation, not
//    *         {@code null}
//    */
//  def toString(a: Annotation): String = {
//    val builder = new ToStringBuilder(a, TO_STRING_STYLE)
//    for (m <- a.annotationType.getDeclaredMethods) {
//      if (m.getParameterTypes.length > 0) {
//        continue //todo: continue is not supported
//        //wtf?
//
//      }
//      try builder.append(m.getName, m.invoke(a))
//      catch {
//        case ex: RuntimeException =>
//          throw ex
//        case ex: Exception =>
//          throw new RuntimeException(ex)
//      }
//    }
//    builder.build
//  }
//
//  /**
//    * <p>Checks if the specified type is permitted as an annotation member.</p>
//    *
//    * <p>The Java language specification only permits certain types to be used
//    * in annotations. These include {@link String}, {@link Class}, primitive
//    * types, {@link Annotation}, {@link Enum}, and single-dimensional arrays of
//    * these types.</p>
//    *
//    * @param type the type to check, {@code null}
//    * @return {@code true} if the type is a valid type to use in an annotation
//    */
//  def isValidAnnotationMemberType(`type`: Class[_]): Boolean = {
//    if (`type` == null) return false
//    if (`type`.isArray) `type` = `type`.getComponentType
//    `type`.isPrimitive || `type`.isEnum || `type`.isAnnotation || classOf[String] == `type` || classOf[Class[_]] == `type`
//  }
//
//  /**
//    * Helper method for generating a hash code for a member of an annotation.
//    *
//    * @param name  the name of the member
//    * @param value the value of the member
//    * @return a hash code for this member
//    */
//  private def hashMember(name: String, value: Any): Int = {
//    val part1 = name.hashCode * 127
//    if (value.getClass.isArray) return part1 ^ arrayMemberHash(value.getClass.getComponentType, value)
//    if (value.isInstanceOf[Annotation]) return part1 ^ hashCode(value.asInstanceOf[Annotation])
//    part1 ^ value.hashCode
//  }
//
//  /**
//    * Helper method for checking whether two objects of the given type are
//    * equal. This method is used to compare the parameters of two annotation
//    * instances.
//    *
//    * @param type the type of the objects to be compared
//    * @param o1   the first object
//    * @param o2   the second object
//    * @return a flag whether these objects are equal
//    */
//  private def memberEquals(`type`: Class[_], o1: Any, o2: Any): Boolean = {
//    if (o1 eq o2) return true
//    if (o1 == null || o2 == null) return false
//    if (`type`.isArray) return arrayMemberEquals(`type`.getComponentType, o1, o2)
//    if (`type`.isAnnotation) return equals(o1.asInstanceOf[Annotation], o2.asInstanceOf[Annotation])
//    o1 == o2
//  }
//
//  /**
//    * Helper method for comparing two objects of an array type.
//    *
//    * @param componentType the component type of the array
//    * @param o1            the first object
//    * @param o2            the second object
//    * @return a flag whether these objects are equal
//    */
//  private def arrayMemberEquals(componentType: Class[_], o1: Any, o2: Any): Boolean = {
//    if (componentType.isAnnotation) return annotationArrayMemberEquals(o1.asInstanceOf[Array[Annotation]], o2.asInstanceOf[Array[Annotation]])
//    if (componentType == Byte.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Byte]], o2.asInstanceOf[Array[Byte]])
//    if (componentType == Short.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Short]], o2.asInstanceOf[Array[Short]])
//    if (componentType == Integer.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Int]], o2.asInstanceOf[Array[Int]])
//    if (componentType == Character.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Char]], o2.asInstanceOf[Array[Char]])
//    if (componentType == Long.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Long]], o2.asInstanceOf[Array[Long]])
//    if (componentType == Float.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Float]], o2.asInstanceOf[Array[Float]])
//    if (componentType == Double.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Double]], o2.asInstanceOf[Array[Double]])
//    if (componentType == Boolean.TYPE) return util.Arrays.equals(o1.asInstanceOf[Array[Boolean]], o2.asInstanceOf[Array[Boolean]])
//    util.Arrays.equals(o1.asInstanceOf[Array[AnyRef]], o2.asInstanceOf[Array[AnyRef]])
//  }
//
//  /**
//    * Helper method for comparing two arrays of annotations.
//    *
//    * @param a1 the first array
//    * @param a2 the second array
//    * @return a flag whether these arrays are equal
//    */
//  private def annotationArrayMemberEquals(a1: Array[Annotation], a2: Array[Annotation]): Boolean = {
//    if (a1.length != a2.length) return false
//    for (i <- 0 until a1.length) {
//      if (!equals(a1(i), a2(i))) return false
//    }
//    true
//  }
//
//  /**
//    * Helper method for generating a hash code for an array.
//    *
//    * @param componentType the component type of the array
//    * @param o             the array
//    * @return a hash code for the specified array
//    */
//  private def arrayMemberHash(componentType: Class[_], o: Any): Int = {
//    if (componentType == JavaByte.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Byte]])
//    if (componentType == JavaShort.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Short]])
//    if (componentType == Integer.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Int]])
//    if (componentType == Character.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Char]])
//    if (componentType == JavaLong.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Long]])
//    if (componentType == JavaFloat.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Float]])
//    if (componentType == JavaDouble.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Double]])
//    if (componentType == JavaBoolean.TYPE) return util.Arrays.hashCode(o.asInstanceOf[Array[Boolean]])
//    util.Arrays.hashCode(o.asInstanceOf[Array[AnyRef]])
//  }
//}
