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
//package org.apache.commons.lang3.reflect
//
//import java.lang.reflect.AccessibleObject
//import java.lang.reflect.Constructor
//import java.lang.reflect.Member
//import java.lang.reflect.Method
//import java.lang.reflect.Modifier
//import org.apache.commons.lang3.ClassUtils
//
///**
//  * Contains common code for working with {@link java.lang.reflect.Method Methods}/{@link java.lang.reflect.Constructor Constructors},
//  * extracted and refactored from {@link MethodUtils} when it was imported from Commons BeanUtils.
//  *
//  * @since 2.5
//  */
//object MemberUtils {
//  private val ACCESS_TEST = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE
//  /** Array of primitive number types ordered by "promotability" */
//  private val ORDERED_PRIMITIVE_TYPES = Array(Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE)
//
//  /**
//    * XXX Default access superclass workaround.
//    *
//    * When a {@code public} class has a default access superclass with {@code public} members,
//    * these members are accessible. Calling them from compiled code works fine.
//    * Unfortunately, on some JVMs, using reflection to invoke these members
//    * seems to (wrongly) prevent access even when the modifier is {@code public}.
//    * Calling {@code setAccessible(true)} solves the problem but will only work from
//    * sufficiently privileged code. Better workarounds would be gratefully
//    * accepted.
//    *
//    * @param o the AccessibleObject to set as accessible
//    * @return a boolean indicating whether the accessibility of the object was set to true.
//    */
//  private[reflect] def setAccessibleWorkaround(o: AccessibleObject): Boolean = {
//    if (o == null || o.isAccessible) return false
//    val m = o.asInstanceOf[Member]
//    if (!o.isAccessible && Modifier.isPublic(m.getModifiers) && isPackageAccess(m.getDeclaringClass.getModifiers)) try {
//      o.setAccessible(true)
//      return true
//    } catch {
//      case e: SecurityException =>
//
//      // NOPMD
//      // ignore in favor of subsequent IllegalAccessException
//    }
//    false
//  }
//
//  /**
//    * Returns whether a given set of modifiers implies package access.
//    *
//    * @param modifiers to test
//    * @return {@code true} unless {@code package}/{@code protected}/{@code private} modifier detected
//    */
//  private[reflect] def isPackageAccess(modifiers: Int) = (modifiers & ACCESS_TEST) == 0
//
//  /**
//    * Returns whether a {@link Member} is accessible.
//    *
//    * @param m Member to check
//    * @return {@code true} if {@code m} is accessible
//    */
//  private[reflect] def isAccessible(m: Member) = m != null && Modifier.isPublic(m.getModifiers) && !m.isSynthetic
//
//  /**
//    * Compares the relative fitness of two Constructors in terms of how well they
//    * match a set of runtime parameter types, such that a list ordered
//    * by the results of the comparison would return the best match first
//    * (least).
//    *
//    * @param left   the "left" Constructor
//    * @param right  the "right" Constructor
//    * @param actual the runtime parameter types to match against
//    *               {@code left}/{@code right}
//    * @return int consistent with {@code compare} semantics
//    * @since 3.5
//    */
//  private[reflect] def compareConstructorFit(left: Constructor[_], right: Constructor[_], actual: Array[Class[_]]) = compareParameterTypes(Executable.of(left), Executable.of(right), actual)
//
//  /**
//    * Compares the relative fitness of two Methods in terms of how well they
//    * match a set of runtime parameter types, such that a list ordered
//    * by the results of the comparison would return the best match first
//    * (least).
//    *
//    * @param left   the "left" Method
//    * @param right  the "right" Method
//    * @param actual the runtime parameter types to match against
//    *               {@code left}/{@code right}
//    * @return int consistent with {@code compare} semantics
//    * @since 3.5
//    */
//  private[reflect] def compareMethodFit(left: Method, right: Method, actual: Array[Class[_]]) = compareParameterTypes(Executable.of(left), Executable.of(right), actual)
//
//  /**
//    * Compares the relative fitness of two Executables in terms of how well they
//    * match a set of runtime parameter types, such that a list ordered
//    * by the results of the comparison would return the best match first
//    * (least).
//    *
//    * @param left   the "left" Executable
//    * @param right  the "right" Executable
//    * @param actual the runtime parameter types to match against
//    *               {@code left}/{@code right}
//    * @return int consistent with {@code compare} semantics
//    */
//  private def compareParameterTypes(left: MemberUtils.Executable, right: MemberUtils.Executable, actual: Array[Class[_]]) = {
//    val leftCost = getTotalTransformationCost(actual, left)
//    val rightCost = getTotalTransformationCost(actual, right)
//    Float.compare(leftCost, rightCost)
//  }
//
//  /**
//    * Returns the sum of the object transformation cost for each class in the
//    * source argument list.
//    *
//    * @param srcArgs    The source arguments
//    * @param executable The executable to calculate transformation costs for
//    * @return The total transformation cost
//    */
//  private def getTotalTransformationCost(srcArgs: Array[Class[_]], executable: MemberUtils.Executable): Float = {
//    val destArgs = executable.getParameterTypes
//    val isVarArgs = executable.isVarArgs
//    // "source" and "destination" are the actual and declared args respectively.
//    var totalCost = 0.0f
//    val normalArgsLen = if (isVarArgs) destArgs.length - 1
//    else destArgs.length
//    if (srcArgs.length < normalArgsLen) return Float.MAX_VALUE
//    var i = 0
//    while ( {
//      i < normalArgsLen
//    }) {
//      totalCost += getObjectTransformationCost(srcArgs(i), destArgs(i))
//
//      i += 1
//    }
//    if (isVarArgs) { // When isVarArgs is true, srcArgs and dstArgs may differ in length.
//      // There are two special cases to consider:
//      val noVarArgsPassed = srcArgs.length < destArgs.length
//      val explicitArrayForVarargs = srcArgs.length == destArgs.length && srcArgs(srcArgs.length - 1) != null && srcArgs(srcArgs.length - 1).isArray
//      val varArgsCost = 0.001f
//      val destClass = destArgs(destArgs.length - 1).getComponentType
//      if (noVarArgsPassed) { // When no varargs passed, the best match is the most generic matching type, not the most specific.
//        totalCost += getObjectTransformationCost(destClass, classOf[Any]) + varArgsCost
//      }
//      else if (explicitArrayForVarargs) {
//        val sourceClass = srcArgs(srcArgs.length - 1).getComponentType
//        totalCost += getObjectTransformationCost(sourceClass, destClass) + varArgsCost
//      }
//      else { // This is typical varargs case.
//        for (i <- destArgs.length - 1 until srcArgs.length) {
//          val srcClass = srcArgs(i)
//          totalCost += getObjectTransformationCost(srcClass, destClass) + varArgsCost
//        }
//      }
//    }
//    totalCost
//  }
//
//  /**
//    * Gets the number of steps required needed to turn the source class into
//    * the destination class. This represents the number of steps in the object
//    * hierarchy graph.
//    *
//    * @param srcClass  The source class
//    * @param destClass The destination class
//    * @return The cost of transforming an object
//    */
//  private def getObjectTransformationCost(srcClass: Class[_], destClass: Class[_]): Float = {
//    if (destClass.isPrimitive) return getPrimitivePromotionCost(srcClass, destClass)
//    var cost = 0.0f
//    while ( {
//      srcClass != null && !(destClass == srcClass)
//    }) {
//      if (destClass.isInterface && ClassUtils.isAssignable(srcClass, destClass)) { // slight penalty for interface match.
//        // we still want an exact match to override an interface match,
//        // but
//        // an interface match should override anything where we have to
//        // get a superclass.
//        cost += 0.25f
//        break //todo: break is not supported
//
//      }
//      cost += 1
//      srcClass = srcClass.getSuperclass
//    }
//    /*
//             * If the destination class is null, we've traveled all the way up to
//             * an Object match. We'll penalize this by adding 1.5 to the cost.
//             */ if (srcClass == null) cost += 1.5f
//    cost
//  }
//
//  /**
//    * Gets the number of steps required to promote a primitive number to another
//    * type.
//    *
//    * @param srcClass  the (primitive) source class
//    * @param destClass the (primitive) destination class
//    * @return The cost of promoting the primitive
//    */
//  private def getPrimitivePromotionCost(srcClass: Class[_], destClass: Class[_]): Float = {
//    if (srcClass == null) return 1.5f
//    var cost = 0.0f
//    var cls = srcClass
//    if (!cls.isPrimitive) { // slight unwrapping penalty
//      cost += 0.1f
//      cls = ClassUtils.wrapperToPrimitive(cls)
//    }
//    var i = 0
//    while ( {
//      (cls ne destClass) && i < ORDERED_PRIMITIVE_TYPES.length
//    }) {
//      if (cls eq ORDERED_PRIMITIVE_TYPES(i)) {
//        cost += 0.1f
//        if (i < ORDERED_PRIMITIVE_TYPES.length - 1) cls = ORDERED_PRIMITIVE_TYPES(i + 1)
//      }
//
//      i += 1
//    }
//    cost
//  }
//
//  private[reflect] def isMatchingMethod(method: Method, parameterTypes: Array[Class[_]]) = isMatchingExecutable(Executable.of(method), parameterTypes)
//
//  private[reflect] def isMatchingConstructor(method: Constructor[_], parameterTypes: Array[Class[_]]) = isMatchingExecutable(Executable.of(method), parameterTypes)
//
//  private def isMatchingExecutable(method: MemberUtils.Executable, parameterTypes: Array[Class[_]]): Boolean = {
//    val methodParameterTypes = method.getParameterTypes
//    if (ClassUtils.isAssignable(parameterTypes, methodParameterTypes, true)) return true
//    if (method.isVarArgs) {
//      var i = 0
//      i = 0
//      while ( {
//        i < methodParameterTypes.length - 1 && i < parameterTypes.length
//      }) {
//        if (!ClassUtils.isAssignable(parameterTypes(i), methodParameterTypes(i), true)) return false
//
//        i += 1
//      }
//      val varArgParameterType = methodParameterTypes(methodParameterTypes.length - 1).getComponentType
//
//      while ( {
//        i < parameterTypes.length
//      }) {
//        if (!ClassUtils.isAssignable(parameterTypes(i), varArgParameterType, true)) return false
//
//        i += 1
//      }
//      return true
//    }
//    false
//  }
//
//  /**
//    * <p> A class providing a subset of the API of java.lang.reflect.Executable in Java 1.8,
//    * providing a common representation for function signatures for Constructors and Methods.</p>
//    */
//  private object Executable {
//    private def of(method: Method) = new MemberUtils.Executable(method)
//
//    private def of(constructor: Constructor[_]) = new MemberUtils.Executable(constructor)
//  }
//
//  final private class Executable {
//    final private var parameterTypes = null
//    final private var isVarArgs = false
//
//    def this(method: Method) {
//      this()
//      parameterTypes = method.getParameterTypes
//      isVarArgs = method.isVarArgs
//    }
//
//    def this(constructor: Constructor[_]) {
//      this()
//      parameterTypes = constructor.getParameterTypes
//      isVarArgs = constructor.isVarArgs
//    }
//
//    def getParameterTypes: Array[Class[_]] = parameterTypes
//
//    def isVarArgs: Boolean = isVarArgs
//  }
//
//}