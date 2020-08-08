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
//import java.lang.reflect.Constructor
//import java.lang.reflect.InvocationTargetException
//import java.lang.reflect.Modifier
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.ClassUtils
//import org.apache.commons.lang3.Validate
//
///**
//  * <p> Utility reflection methods focused on constructors, modeled after
//  * {@link MethodUtils}. </p>
//  *
//  * <h2>Known Limitations</h2>
//  * <h3>Accessing Public Constructors In A Default Access Superclass</h3>
//  * <p>There is an issue when invoking {@code public} constructors
//  * contained in a default access superclass. Reflection correctly locates these
//  * constructors and assigns them as {@code public}. However, an
//  * {@link IllegalAccessException} is thrown if the constructor is
//  * invoked.</p>
//  *
//  * <p>{@link ConstructorUtils} contains a workaround for this situation: it
//  * will attempt to call {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} on this constructor. If this
//  * call succeeds, then the method can be invoked as normal. This call will only
//  * succeed when the application has sufficient security privileges. If this call
//  * fails then a warning will be logged and the method may fail.</p>
//  *
//  * @since 2.5
//  */
//object ConstructorUtils {
//  /**
//    * <p>Returns a new instance of the specified class inferring the right constructor
//    * from the types of the arguments.</p>
//    *
//    * <p>This locates and calls a constructor.
//    * The constructor signature must match the argument types by assignment compatibility.</p>
//    *
//    * @param <    T> the type to be constructed
//    * @param cls  the class to be constructed, not {@code null}
//    * @param args the array of arguments, {@code null} treated as empty
//    * @return new instance of {@code cls}, not {@code null}
//    * @throws NullPointerException      if {@code cls} is {@code null}
//    * @throws NoSuchMethodException     if a matching constructor cannot be found
//    * @throws IllegalAccessException    if invocation is not permitted by security
//    * @throws InvocationTargetException if an error occurs on invocation
//    * @throws InstantiationException    if an error occurs on instantiation
//    * @see #invokeConstructor(java.lang.Class, java.lang.Object[], java.lang.Class[])
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  @throws[InstantiationException]
//  def invokeConstructor[T](cls: Class[T], args: Any*): T = {
//    args = ArrayUtils.nullToEmpty(args)
//    val parameterTypes = ClassUtils.toClass(args)
//    invokeConstructor(cls, args, parameterTypes)
//  }
//
//  /**
//    * <p>Returns a new instance of the specified class choosing the right constructor
//    * from the list of parameter types.</p>
//    *
//    * <p>This locates and calls a constructor.
//    * The constructor signature must match the parameter types by assignment compatibility.</p>
//    *
//    * @param <              T> the type to be constructed
//    * @param cls            the class to be constructed, not {@code null}
//    * @param args           the array of arguments, {@code null} treated as empty
//    * @param parameterTypes the array of parameter types, {@code null} treated as empty
//    * @return new instance of {@code cls}, not {@code null}
//    * @throws NullPointerException      if {@code cls} is {@code null}
//    * @throws NoSuchMethodException     if a matching constructor cannot be found
//    * @throws IllegalAccessException    if invocation is not permitted by security
//    * @throws InvocationTargetException if an error occurs on invocation
//    * @throws InstantiationException    if an error occurs on instantiation
//    * @see Constructor#newInstance
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  @throws[InstantiationException]
//  def invokeConstructor[T](cls: Class[T], args: Array[AnyRef], parameterTypes: Array[Class[_]]): T = {
//    args = ArrayUtils.nullToEmpty(args:_*)
//    parameterTypes = ArrayUtils.nullToEmpty(parameterTypes)
//    val ctor = getMatchingAccessibleConstructor(cls, parameterTypes)
//    if (ctor == null) throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName)
//    if (ctor.isVarArgs) {
//      val methodParameterTypes = ctor.getParameterTypes
//      args = MethodUtils.getVarArgs(args, methodParameterTypes)
//    }
//    ctor.newInstance(args)
//  }
//
//  /**
//    * <p>Returns a new instance of the specified class inferring the right constructor
//    * from the types of the arguments.</p>
//    *
//    * <p>This locates and calls a constructor.
//    * The constructor signature must match the argument types exactly.</p>
//    *
//    * @param <    T> the type to be constructed
//    * @param cls  the class to be constructed, not {@code null}
//    * @param args the array of arguments, {@code null} treated as empty
//    * @return new instance of {@code cls}, not {@code null}
//    * @throws NullPointerException      if {@code cls} is {@code null}
//    * @throws NoSuchMethodException     if a matching constructor cannot be found
//    * @throws IllegalAccessException    if invocation is not permitted by security
//    * @throws InvocationTargetException if an error occurs on invocation
//    * @throws InstantiationException    if an error occurs on instantiation
//    * @see #invokeExactConstructor(java.lang.Class, java.lang.Object[], java.lang.Class[])
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  @throws[InstantiationException]
//  def invokeExactConstructor[T](cls: Class[T], args: Any*): T = {
//    args = ArrayUtils.nullToEmpty(args)
//    val parameterTypes = ClassUtils.toClass(args)
//    invokeExactConstructor(cls, args, parameterTypes)
//  }
//
//  /**
//    * <p>Returns a new instance of the specified class choosing the right constructor
//    * from the list of parameter types.</p>
//    *
//    * <p>This locates and calls a constructor.
//    * The constructor signature must match the parameter types exactly.</p>
//    *
//    * @param <              T> the type to be constructed
//    * @param cls            the class to be constructed, not {@code null}
//    * @param args           the array of arguments, {@code null} treated as empty
//    * @param parameterTypes the array of parameter types, {@code null} treated as empty
//    * @return new instance of {@code cls}, not {@code null}
//    * @throws NullPointerException      if {@code cls} is {@code null}
//    * @throws NoSuchMethodException     if a matching constructor cannot be found
//    * @throws IllegalAccessException    if invocation is not permitted by security
//    * @throws InvocationTargetException if an error occurs on invocation
//    * @throws InstantiationException    if an error occurs on instantiation
//    * @see Constructor#newInstance
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  @throws[InstantiationException]
//  def invokeExactConstructor[T](cls: Class[T], args: Array[AnyRef], parameterTypes: Array[Class[_]]): T = {
//    args = ArrayUtils.nullToEmpty(args)
//    parameterTypes = ArrayUtils.nullToEmpty(parameterTypes)
//    val ctor = getAccessibleConstructor(cls, parameterTypes)
//    if (ctor == null) throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName)
//    ctor.newInstance(args)
//  }
//
//  /**
//    * <p>Finds a constructor given a class and signature, checking accessibility.</p>
//    *
//    * <p>This finds the constructor and ensures that it is accessible.
//    * The constructor signature must match the parameter types exactly.</p>
//    *
//    * @param <              T> the constructor type
//    * @param cls            the class to find a constructor for, not {@code null}
//    * @param parameterTypes the array of parameter types, {@code null} treated as empty
//    * @return the constructor, {@code null} if no matching accessible constructor found
//    * @see Class#getConstructor
//    * @see #getAccessibleConstructor(java.lang.reflect.Constructor)
//    * @throws NullPointerException if {@code cls} is {@code null}
//    */
//  def getAccessibleConstructor[T](cls: Class[T], parameterTypes: Class[_]*): Constructor[T] = {
//    Validate.notNull(cls, "class cannot be null")
//    try getAccessibleConstructor(cls.getConstructor(parameterTypes))
//    catch {
//      case e: NoSuchMethodException =>
//        null
//    }
//  }
//
//  /**
//    * <p>Checks if the specified constructor is accessible.</p>
//    *
//    * <p>This simply ensures that the constructor is accessible.</p>
//    *
//    * @param <    T> the constructor type
//    * @param ctor the prototype constructor object, not {@code null}
//    * @return the constructor, {@code null} if no matching accessible constructor found
//    * @see java.lang.SecurityManager
//    * @throws NullPointerException if {@code ctor} is {@code null}
//    */
//  def getAccessibleConstructor[T](ctor: Constructor[T]): Constructor[T] = {
//    Validate.notNull(ctor, "constructor cannot be null")
//    if (MemberUtils.isAccessible(ctor) && isAccessible(ctor.getDeclaringClass)) ctor
//    else null
//  }
//
//  /**
//    * <p>Finds an accessible constructor with compatible parameters.</p>
//    *
//    * <p>This checks all the constructor and finds one with compatible parameters
//    * This requires that every parameter is assignable from the given parameter types.
//    * This is a more flexible search than the normal exact matching algorithm.</p>
//    *
//    * <p>First it checks if there is a constructor matching the exact signature.
//    * If not then all the constructors of the class are checked to see if their
//    * signatures are assignment-compatible with the parameter types.
//    * The first assignment-compatible matching constructor is returned.</p>
//    *
//    * @param <              T> the constructor type
//    * @param cls            the class to find a constructor for, not {@code null}
//    * @param parameterTypes find method with compatible parameters
//    * @return the constructor, null if no matching accessible constructor found
//    * @throws NullPointerException if {@code cls} is {@code null}
//    */
//  def getMatchingAccessibleConstructor[T](cls: Class[T], parameterTypes: Class[_]*): Constructor[T] = {
//    Validate.notNull(cls, "class cannot be null")
//    // see if we can find the constructor directly
//    // most of the time this works and it's much faster
//    try {
//      val ctor = cls.getConstructor(parameterTypes)
//      MemberUtils.setAccessibleWorkaround(ctor)
//      return ctor
//    } catch {
//      case e: NoSuchMethodException =>
//
//      // NOPMD - Swallow
//    }
//    var result = null
//    /*
//             * (1) Class.getConstructors() is documented to return Constructor<T> so as
//             * long as the array is not subsequently modified, everything's fine.
//             */ val ctors = cls.getConstructors
//    // return best match:
//    for (ctor <- ctors) { // compare parameters
//      if (MemberUtils.isMatchingConstructor(ctor, parameterTypes)) { // get accessible version of constructor
//        ctor = getAccessibleConstructor(ctor)
//        if (ctor != null) {
//          MemberUtils.setAccessibleWorkaround(ctor)
//          if (result == null || MemberUtils.compareConstructorFit(ctor, result, parameterTypes) < 0) { // temporary variable for annotation, see comment above (1)
//            @SuppressWarnings(Array("unchecked")) val constructor = ctor.asInstanceOf[Constructor[T]]
//            result = constructor
//          }
//        }
//      }
//    }
//    result
//  }
//
//  /**
//    * Learn whether the specified class is generally accessible, i.e. is
//    * declared in an entirely {@code public} manner.
//    *
//    * @param type to check
//    * @return {@code true} if {@code type} and any enclosing classes are
//    *         {@code public}.
//    */
//  private def isAccessible(`type`: Class[_]): Boolean = {
//    var cls = `type`
//    while ( {
//      cls != null
//    }) {
//      if (!Modifier.isPublic(cls.getModifiers)) return false
//      cls = cls.getEnclosingClass
//    }
//    true
//  }
//}