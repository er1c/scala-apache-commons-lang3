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
//import java.lang.annotation.Annotation
//import java.lang.reflect.Array
//import java.lang.reflect.InvocationTargetException
//import java.lang.reflect.Method
//import java.lang.reflect.Modifier
//import java.lang.reflect.Type
//import java.lang.reflect.TypeVariable
//import java.util
//import java.util.Comparator
//import java.util.Objects
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.ClassUtils
//import org.apache.commons.lang3.ClassUtils.Interfaces
//import org.apache.commons.lang3.Validate
//import scala.collection.JavaConverters._
//import scala.util.control.Breaks
//
///**
//  * <p>Utility reflection methods focused on {@link Method}s, originally from Commons BeanUtils.
//  * Differences from the BeanUtils version may be noted, especially where similar functionality
//  * already existed within Lang.
//  * </p>
//  *
//  * <h2>Known Limitations</h2>
//  * <h3>Accessing Public Methods In A Default Access Superclass</h3>
//  * <p>There is an issue when invoking {@code public} methods contained in a default access superclass on JREs prior to 1.4.
//  * Reflection locates these methods fine and correctly assigns them as {@code public}.
//  * However, an {@link IllegalAccessException} is thrown if the method is invoked.</p>
//  *
//  * <p>{@link MethodUtils} contains a workaround for this situation.
//  * It will attempt to call {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} on this method.
//  * If this call succeeds, then the method can be invoked as normal.
//  * This call will only succeed when the application has sufficient security privileges.
//  * If this call fails then the method may fail.</p>
//  *
//  * @since 2.5
//  */
//object MethodUtils {
//
//  /**
//    * Our own copy of breaks to avoid conflicts with any other breaks:
//    * "Calls to break from one instantiation of Breaks will never target breakable objects of some other instantiation."
//    */
//  private val breaks: Breaks = new Breaks
//  import breaks._
//
//  private val METHOD_BY_SIGNATURE = Comparator.comparing(Method.toString)
//
//  /**
//    * <p>Invokes a named method without parameters.</p>
//    *
//    * <p>This method delegates the method search to {@link #getMatchingAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * <p>This is a convenient wrapper for
//    * {@link #invokeMethod ( Object object, String methodName, Object[] args, Class[] parameterTypes)}.
//    * </p>
//    *
//    * @param object     invoke method on this object
//    * @param methodName get method with this name
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible via reflection
//    * @since 3.4
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeMethod(`object`: Any, methodName: String): Any = invokeMethod(`object`, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null)
//
//  /**
//    * <p>Invokes a named method without parameters.</p>
//    *
//    * <p>This is a convenient wrapper for
//    * {@link #invokeMethod ( Object object, boolean forceAccess, String methodName, Object[] args, Class[] parameterTypes)}.
//    * </p>
//    *
//    * @param object      invoke method on this object
//    * @param forceAccess force access to invoke method even if it's not accessible
//    * @param methodName  get method with this name
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible via reflection
//    * @since 3.5
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeMethod(`object`: Any, forceAccess: Boolean, methodName: String): Any = invokeMethod(`object`, forceAccess, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null)
//
//  /**
//    * <p>Invokes a named method whose parameter type matches the object type.</p>
//    *
//    * <p>This method delegates the method search to {@link #getMatchingAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * <p>This method supports calls to methods taking primitive parameters
//    * via passing in wrapping classes. So, for example, a {@code Boolean} object
//    * would match a {@code boolean} primitive.</p>
//    *
//    * <p>This is a convenient wrapper for
//    * {@link #invokeMethod ( Object object, String methodName, Object[] args, Class[] parameterTypes)}.
//    * </p>
//    *
//    * @param object     invoke method on this object
//    * @param methodName get method with this name
//    * @param args       use these arguments - treat null as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeMethod(`object`: Any, methodName: String, args: Any*): Any = {
//    val parameterTypes = ClassUtils.toClass(args:_*)
//    invokeMethod(`object`, methodName, args, parameterTypes)
//  }
//
//  /**
//    * <p>Invokes a named method whose parameter type matches the object type.</p>
//    *
//    * <p>This method supports calls to methods taking primitive parameters
//    * via passing in wrapping classes. So, for example, a {@code Boolean} object
//    * would match a {@code boolean} primitive.</p>
//    *
//    * <p>This is a convenient wrapper for
//    * {@link #invokeMethod ( Object object, boolean forceAccess, String methodName, Object[] args, Class[] parameterTypes)}.
//    * </p>
//    *
//    * @param object      invoke method on this object
//    * @param forceAccess force access to invoke method even if it's not accessible
//    * @param methodName  get method with this name
//    * @param args        use these arguments - treat null as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible via reflection
//    * @since 3.5
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeMethod(`object`: Any, forceAccess: Boolean, methodName: String, args: Any*): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    val parameterTypes = ClassUtils.toClass(args)
//    invokeMethod(`object`, forceAccess, methodName, args, parameterTypes)
//  }
//
//  /**
//    * <p>Invokes a named method whose parameter type matches the object type.</p>
//    *
//    * <p>This method supports calls to methods taking primitive parameters
//    * via passing in wrapping classes. So, for example, a {@code Boolean} object
//    * would match a {@code boolean} primitive.</p>
//    *
//    * @param object         invoke method on this object
//    * @param forceAccess    force access to invoke method even if it's not accessible
//    * @param methodName     get method with this name
//    * @param args           use these arguments - treat null as empty array
//    * @param parameterTypes match these parameters - treat null as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible via reflection
//    * @since 3.5
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeMethod(`object`: Any, forceAccess: Boolean, methodName: String, args: Array[AnyRef], parameterTypes: Array[Class[_]]): Any = {
//    parameterTypes = ArrayUtils.nullToEmpty(parameterTypes)
//    args = ArrayUtils.nullToEmpty(args)
//    var messagePrefix = null
//    var method = null
//    if (forceAccess) {
//      messagePrefix = "No such method: "
//      method = getMatchingMethod(`object`.getClass, methodName, parameterTypes)
//      if (method != null && !method.isAccessible) method.setAccessible(true)
//    }
//    else {
//      messagePrefix = "No such accessible method: "
//      method = getMatchingAccessibleMethod(`object`.getClass, methodName, parameterTypes)
//    }
//    if (method == null) throw new NoSuchMethodException(messagePrefix + methodName + "() on object: " + `object`.getClass.getName)
//    args = toVarArgs(method, args)
//    method.invoke(`object`, args)
//  }
//
//  /**
//    * <p>Invokes a named method whose parameter type matches the object type.</p>
//    *
//    * <p>This method delegates the method search to {@link #getMatchingAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * <p>This method supports calls to methods taking primitive parameters
//    * via passing in wrapping classes. So, for example, a {@code Boolean} object
//    * would match a {@code boolean} primitive.</p>
//    *
//    * @param object         invoke method on this object
//    * @param methodName     get method with this name
//    * @param args           use these arguments - treat null as empty array
//    * @param parameterTypes match these parameters - treat null as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeMethod(`object`: Any, methodName: String, args: Array[AnyRef], parameterTypes: Array[Class[_]]): Any = invokeMethod(`object`, false, methodName, args, parameterTypes)
//
//  /**
//    * <p>Invokes a method whose parameter types match exactly the object
//    * types.</p>
//    *
//    * <p>This uses reflection to invoke the method obtained from a call to
//    * {@link #getAccessibleMethod}(Class, String, Class[])}.</p>
//    *
//    * @param object     invoke method on this object
//    * @param methodName get method with this name
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    * @since 3.4
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeExactMethod(`object`: Any, methodName: String): Any = invokeExactMethod(`object`, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null)
//
//  /**
//    * <p>Invokes a method with no parameters.</p>
//    *
//    * <p>This uses reflection to invoke the method obtained from a call to
//    * {@link #getAccessibleMethod}(Class, String, Class[])}.</p>
//    *
//    * @param object     invoke method on this object
//    * @param methodName get method with this name
//    * @param args       use these arguments - treat null as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeExactMethod(`object`: Any, methodName: String, args: Any*): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    val parameterTypes = ClassUtils.toClass(args)
//    invokeExactMethod(`object`, methodName, args, parameterTypes)
//  }
//
//  /**
//    * <p>Invokes a method whose parameter types match exactly the parameter
//    * types given.</p>
//    *
//    * <p>This uses reflection to invoke the method obtained from a call to
//    * {@link #getAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * @param object         invoke method on this object
//    * @param methodName     get method with this name
//    * @param args           use these arguments - treat null as empty array
//    * @param parameterTypes match these parameters - treat {@code null} as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeExactMethod(`object`: Any, methodName: String, args: Array[AnyRef], parameterTypes: Array[Class[_]]): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    parameterTypes = ArrayUtils.nullToEmpty(parameterTypes)
//    val method = getAccessibleMethod(`object`.getClass, methodName, parameterTypes)
//    if (method == null) throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + `object`.getClass.getName)
//    method.invoke(`object`, args)
//  }
//
//  /**
//    * <p>Invokes a {@code static} method whose parameter types match exactly the parameter
//    * types given.</p>
//    *
//    * <p>This uses reflection to invoke the method obtained from a call to
//    * {@link #getAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * @param cls            invoke static method on this class
//    * @param methodName     get method with this name
//    * @param args           use these arguments - treat {@code null} as empty array
//    * @param parameterTypes match these parameters - treat {@code null} as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeExactStaticMethod(cls: Class[_], methodName: String, args: Array[AnyRef], parameterTypes: Array[Class[_]]): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    parameterTypes = ArrayUtils.nullToEmpty(parameterTypes)
//    val method = getAccessibleMethod(cls, methodName, parameterTypes)
//    if (method == null) throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName)
//    method.invoke(null, args)
//  }
//
//  /**
//    * <p>Invokes a named {@code static} method whose parameter type matches the object type.</p>
//    *
//    * <p>This method delegates the method search to {@link #getMatchingAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * <p>This method supports calls to methods taking primitive parameters
//    * via passing in wrapping classes. So, for example, a {@code Boolean} class
//    * would match a {@code boolean} primitive.</p>
//    *
//    * <p>This is a convenient wrapper for
//    * {@link #invokeStaticMethod ( Class, String, Object[], Class[])}.
//    * </p>
//    *
//    * @param cls        invoke static method on this class
//    * @param methodName get method with this name
//    * @param args       use these arguments - treat {@code null} as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeStaticMethod(cls: Class[_], methodName: String, args: Any*): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    val parameterTypes = ClassUtils.toClass(args)
//    invokeStaticMethod(cls, methodName, args, parameterTypes)
//  }
//
//  /**
//    * <p>Invokes a named {@code static} method whose parameter type matches the object type.</p>
//    *
//    * <p>This method delegates the method search to {@link #getMatchingAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * <p>This method supports calls to methods taking primitive parameters
//    * via passing in wrapping classes. So, for example, a {@code Boolean} class
//    * would match a {@code boolean} primitive.</p>
//    *
//    * @param cls            invoke static method on this class
//    * @param methodName     get method with this name
//    * @param args           use these arguments - treat {@code null} as empty array
//    * @param parameterTypes match these parameters - treat {@code null} as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeStaticMethod(cls: Class[_], methodName: String, args: Array[AnyRef], parameterTypes: Array[Class[_]]): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    parameterTypes = ArrayUtils.nullToEmpty(parameterTypes)
//    val method = getMatchingAccessibleMethod(cls, methodName, parameterTypes)
//    if (method == null) throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName)
//    args = toVarArgs(method, args)
//    method.invoke(null, args)
//  }
//
//  private def toVarArgs(method: Method, args: Array[AnyRef]) = {
//    if (method.isVarArgs) {
//      val methodParameterTypes = method.getParameterTypes
//      args = getVarArgs(args, methodParameterTypes)
//    }
//    args
//  }
//
//  /**
//    * <p>Given an arguments array passed to a varargs method, return an array of arguments in the canonical form,
//    * i.e. an array with the declared number of parameters, and whose last parameter is an array of the varargs type.
//    * </p>
//    *
//    * @param args                 the array of arguments passed to the varags method
//    * @param methodParameterTypes the declared array of method parameter types
//    * @return an array of the variadic arguments passed to the method
//    * @since 3.5
//    */
//  private[reflect] def getVarArgs(args: Array[AnyRef], methodParameterTypes: Array[Class[_]]): Array[AnyRef] = {
//    if (args.length == methodParameterTypes.length && (args(args.length - 1) == null || args(args.length - 1).getClass == methodParameterTypes(methodParameterTypes.length - 1))) { // The args array is already in the canonical form for the method.
//      return args
//    }
//    // Construct a new array matching the method's declared parameter types.
//    val newArgs = new Array[AnyRef](methodParameterTypes.length)
//    // Copy the normal (non-varargs) parameters
//    System.arraycopy(args, 0, newArgs, 0, methodParameterTypes.length - 1)
//    // Construct a new array for the variadic parameters
//    val varArgComponentType = methodParameterTypes(methodParameterTypes.length - 1).getComponentType
//    val varArgLength = args.length - methodParameterTypes.length + 1
//    var varArgsArray = Array.newInstance(ClassUtils.primitiveToWrapper(varArgComponentType), varArgLength)
//    // Copy the variadic arguments into the varargs array.
//    System.arraycopy(args, methodParameterTypes.length - 1, varArgsArray, 0, varArgLength)
//    if (varArgComponentType.isPrimitive) { // unbox from wrapper type to primitive type
//      varArgsArray = ArrayUtils.toPrimitive(varArgsArray)
//    }
//    // Store the varargs array in the last position of the array to return
//    newArgs(methodParameterTypes.length - 1) = varArgsArray
//    // Return the canonical varargs array.
//    newArgs
//  }
//
//  /**
//    * <p>Invokes a {@code static} method whose parameter types match exactly the object
//    * types.</p>
//    *
//    * <p>This uses reflection to invoke the method obtained from a call to
//    * {@link #getAccessibleMethod ( Class, String, Class[])}.</p>
//    *
//    * @param cls        invoke static method on this class
//    * @param methodName get method with this name
//    * @param args       use these arguments - treat {@code null} as empty array
//    * @return The value returned by the invoked method
//    * @throws NoSuchMethodException     if there is no such accessible method
//    * @throws InvocationTargetException wraps an exception thrown by the
//    *                                   method invoked
//    * @throws IllegalAccessException    if the requested method is not accessible
//    *                                   via reflection
//    */
//  @throws[NoSuchMethodException]
//  @throws[IllegalAccessException]
//  @throws[InvocationTargetException]
//  def invokeExactStaticMethod(cls: Class[_], methodName: String, args: Any*): Any = {
//    args = ArrayUtils.nullToEmpty(args)
//    val parameterTypes = ClassUtils.toClass(args)
//    invokeExactStaticMethod(cls, methodName, args, parameterTypes)
//  }
//
//  /**
//    * <p>Returns an accessible method (that is, one that can be invoked via
//    * reflection) with given name and parameters. If no such method
//    * can be found, return {@code null}.
//    * This is just a convenience wrapper for
//    * {@link #getAccessibleMethod ( Method )}.</p>
//    *
//    * @param cls            get method from this class
//    * @param methodName     get method with this name
//    * @param parameterTypes with these parameters types
//    * @return The accessible method
//    */
//  def getAccessibleMethod(cls: Class[_], methodName: String, parameterTypes: Class[_]*): Method = try getAccessibleMethod(cls.getMethod(methodName, parameterTypes))
//  catch {
//    case e: NoSuchMethodException =>
//      null
//  }
//
//  /**
//    * <p>Returns an accessible method (that is, one that can be invoked via
//    * reflection) that implements the specified Method. If no such method
//    * can be found, return {@code null}.</p>
//    *
//    * @param method The method that we wish to call
//    * @return The accessible method
//    */
//  def getAccessibleMethod(method: Method): Method = {
//    if (!MemberUtils.isAccessible(method)) return null
//    // If the declaring class is public, we are done
//    val cls = method.getDeclaringClass
//    if (Modifier.isPublic(cls.getModifiers)) return method
//    val methodName = method.getName
//    val parameterTypes = method.getParameterTypes
//    // Check the implemented interfaces and subinterfaces
//    method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes)
//    // Check the superclass chain
//    if (method == null) method = getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes)
//    method
//  }
//
//  /**
//    * <p>Returns an accessible method (that is, one that can be invoked via
//    * reflection) by scanning through the superclasses. If no such method
//    * can be found, return {@code null}.</p>
//    *
//    * @param cls            Class to be checked
//    * @param methodName     Method name of the method we wish to call
//    * @param parameterTypes The parameter type signatures
//    * @return the accessible method or {@code null} if not found
//    */
//  private def getAccessibleMethodFromSuperclass(cls: Class[_], methodName: String, parameterTypes: Class[_]*): Method = {
//    var parentClass = cls.getSuperclass
//    while ( {
//      parentClass != null
//    }) {
//      if (Modifier.isPublic(parentClass.getModifiers)) try return parentClass.getMethod(methodName, parameterTypes)
//      catch {
//        case e: NoSuchMethodException =>
//          return null
//      }
//      parentClass = parentClass.getSuperclass
//    }
//    null
//  }
//
//  /**
//    * <p>Returns an accessible method (that is, one that can be invoked via
//    * reflection) that implements the specified method, by scanning through
//    * all implemented interfaces and subinterfaces. If no such method
//    * can be found, return {@code null}.</p>
//    *
//    * <p>There isn't any good reason why this method must be {@code private}.
//    * It is because there doesn't seem any reason why other classes should
//    * call this rather than the higher level methods.</p>
//    *
//    * @param cls            Parent class for the interfaces to be checked
//    * @param methodName     Method name of the method we wish to call
//    * @param parameterTypes The parameter type signatures
//    * @return the accessible method or {@code null} if not found
//    */
//  private def getAccessibleMethodFromInterfaceNest(cls: Class[_], methodName: String, parameterTypes: Class[_]*): Method = { // Search up the superclass chain
//
//    while ( {
//      cls != null
//    }) { // Check the implemented interfaces of the parent class
//      val interfaces = cls.getInterfaces
//      for (anInterface <- interfaces) { // Is this interface public?
//        if (!Modifier.isPublic(anInterface.getModifiers)) continue //todo: continue is not supported
//        // Does the method exist on this interface?
//        try return anInterface.getDeclaredMethod(methodName, parameterTypes)
//        catch {
//          case e: NoSuchMethodException =>
//
//          // NOPMD
//          /*
//                               * Swallow, if no method is found after the loop then this
//                               * method returns null.
//                               */
//        }
//        // Recursively check our parent interfaces
//        val method = getAccessibleMethodFromInterfaceNest(anInterface, methodName, parameterTypes)
//        if (method != null) return method
//      }
//
//      cls = cls.getSuperclass
//    }
//    null
//  }
//
//  /**
//    * <p>Finds an accessible method that matches the given name and has compatible parameters.
//    * Compatible parameters mean that every method parameter is assignable from
//    * the given parameters.
//    * In other words, it finds a method with the given name
//    * that will take the parameters given.</p>
//    *
//    * <p>This method is used by
//    * {@link
//  * #invokeMethod(Object object, String methodName, Object[] args, Class[] parameterTypes)}.
//    * </p>
//    *
//    * <p>This method can match primitive parameter by passing in wrapper classes.
//    * For example, a {@code Boolean} will match a primitive {@code boolean}
//    * parameter.
//    * </p>
//    *
//    * @param cls            find method in this class
//    * @param methodName     find method with this name
//    * @param parameterTypes find method with most compatible parameters
//    * @return The accessible method
//    */
//  def getMatchingAccessibleMethod(cls: Class[_], methodName: String, parameterTypes: Class[_]*): Method = {
//    try {
//      val method = cls.getMethod(methodName, parameterTypes)
//      MemberUtils.setAccessibleWorkaround(method)
//      return method
//    } catch {
//      case e: NoSuchMethodException =>
//
//      // NOPMD - Swallow the exception
//    }
//    // search through all methods
//    val methods = cls.getMethods
//    val matchingMethods = new util.ArrayList[Method]
//    for (method <- methods) { // compare name and parameters
//      if (method.getName == methodName && MemberUtils.isMatchingMethod(method, parameterTypes)) matchingMethods.add(method)
//    }
//    // Sort methods by signature to force deterministic result
//    matchingMethods.sort(METHOD_BY_SIGNATURE)
//    var bestMatch = null
//    import scala.collection.JavaConversions._
//    for (method <- matchingMethods) { // get accessible version of method
//      val accessibleMethod = getAccessibleMethod(method)
//      if (accessibleMethod != null && (bestMatch == null || MemberUtils.compareMethodFit(accessibleMethod, bestMatch, parameterTypes) < 0)) bestMatch = accessibleMethod
//    }
//    if (bestMatch != null) MemberUtils.setAccessibleWorkaround(bestMatch)
//    if (bestMatch != null && bestMatch.isVarArgs && bestMatch.getParameterTypes.length > 0 && parameterTypes.length > 0) {
//      val methodParameterTypes = bestMatch.getParameterTypes
//      val methodParameterComponentType = methodParameterTypes(methodParameterTypes.length - 1).getComponentType
//      val methodParameterComponentTypeName = ClassUtils.primitiveToWrapper(methodParameterComponentType).getName
//      val lastParameterType = parameterTypes(parameterTypes.length - 1)
//      val parameterTypeName = if (lastParameterType == null) null
//      else lastParameterType.getName
//      val parameterTypeSuperClassName = if (lastParameterType == null) null
//      else lastParameterType.getSuperclass.getName
//      if (parameterTypeName != null && parameterTypeSuperClassName != null && !(methodParameterComponentTypeName == parameterTypeName) && !(methodParameterComponentTypeName == parameterTypeSuperClassName)) return null
//    }
//    bestMatch
//  }
//
//  /**
//    * <p>Retrieves a method whether or not it's accessible. If no such method
//    * can be found, return {@code null}.</p>
//    *
//    * @param cls            The class that will be subjected to the method search
//    * @param methodName     The method that we wish to call
//    * @param parameterTypes Argument class types
//    * @return The method
//    * @since 3.5
//    */
//  def getMatchingMethod(cls: Class[_], methodName: String, parameterTypes: Class[_]*): Method = {
//    Validate.notNull(cls, "Null class not allowed.")
//    Validate.notEmpty(methodName, "Null or blank methodName not allowed.")
//    // Address methods in superclasses
//    var methodArray = cls.getDeclaredMethods
//    val superclassList = ClassUtils.getAllSuperclasses(cls)
//    import scala.collection.JavaConversions._
//    for (klass <- superclassList) {
//      methodArray = ArrayUtils.addAll(methodArray, klass.getDeclaredMethods)
//    }
//    var inexactMatch = null
//    for (method <- methodArray) {
//      if (methodName == method.getName && Objects.deepEquals(parameterTypes, method.getParameterTypes)) return method
//      else if (methodName == method.getName && ClassUtils.isAssignable(parameterTypes, method.getParameterTypes, true)) if ((inexactMatch == null) || (distance(parameterTypes, method.getParameterTypes) < distance(parameterTypes, inexactMatch.getParameterTypes))) inexactMatch = method
//    }
//    inexactMatch
//  }
//
//  /**
//    * <p>Returns the aggregate number of inheritance hops between assignable argument class types.  Returns -1
//    * if the arguments aren't assignable.  Fills a specific purpose for getMatchingMethod and is not generalized.</p>
//    *
//    * @param classArray
//    * @param toClassArray
//    * @return the aggregate number of inheritance hops between assignable argument class types.
//    */
//  private def distance(classArray: Array[Class[_]], toClassArray: Array[Class[_]]): Int = {
//    var answer = 0
//    if (!ClassUtils.isAssignable(classArray, toClassArray, true)) return -1
//    for (offset <- 0 until classArray.length) { // Note InheritanceUtils.distance() uses different scoring system.
//      if (classArray(offset) == toClassArray(offset)) continue //todo: continue is not supported
//      else if (ClassUtils.isAssignable(classArray(offset), toClassArray(offset), true) && !ClassUtils.isAssignable(classArray(offset), toClassArray(offset), false)) answer += 1
//      else answer = answer + 2
//    }
//    answer
//  }
//
//  /**
//    * Gets the hierarchy of overridden methods down to {@code result} respecting generics.
//    *
//    * @param method             lowest to consider
//    * @param interfacesBehavior whether to search interfaces, {@code null} {@code implies} false
//    * @return Set&lt;Method&gt; in ascending order from sub- to superclass
//    * @throws NullPointerException if the specified method is {@code null}
//    * @since 3.2
//    */
//  def getOverrideHierarchy(method: Method, interfacesBehavior: ClassUtils.Interfaces): util.Set[Method] = {
//    Validate.notNull(method)
//    val result = new util.LinkedHashSet[Method]
//    result.add(method)
//    val parameterTypes = method.getParameterTypes
//    val declaringClass = method.getDeclaringClass
//    val hierarchy = ClassUtils.hierarchy(declaringClass, interfacesBehavior).iterator
//    //skip the declaring class :P
//    hierarchy.next
//    hierarchyTraversal //todo: labels are not supported
//    while ( {
//      hierarchy.hasNext
//    }) {
//      val c = hierarchy.next
//      val m = getMatchingAccessibleMethod(c, method.getName, parameterTypes)
//      if (m == null) continue //todo: continue is not supported
//      if (util.Arrays.equals(m.getParameterTypes, parameterTypes)) { // matches without generics
//        result.add(m)
//        continue //todo: continue is not supported
//
//      }
//      // necessary to get arguments every time in the case that we are including interfaces
//      val typeArguments = TypeUtils.getTypeArguments(declaringClass, m.getDeclaringClass)
//      for (i <- 0 until parameterTypes.length) {
//        val childType = TypeUtils.unrollVariables(typeArguments, method.getGenericParameterTypes(i))
//        val parentType = TypeUtils.unrollVariables(typeArguments, m.getGenericParameterTypes(i))
//        if (!TypeUtils.equals(childType, parentType)) continue hierarchyTraversal //todo: continue is not supported
//      }
//      result.add(m)
//    }
//    result
//  }
//
//  /**
//    * Gets all class level public methods of the given class that are annotated with the given annotation.
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @param annotationCls
//    * the {@link java.lang.annotation.Annotation} that must be present on a method to be matched
//    * @return an array of Methods (possibly empty).
//    * @throws NullPointerException if the class or annotation are {@code null}
//    * @since 3.4
//    */
//  def getMethodsWithAnnotation(cls: Class[_], annotationCls: Class[_ <: Annotation]): Array[Method] = getMethodsWithAnnotation(cls, annotationCls, false, false)
//
//  /**
//    * Gets all class level public methods of the given class that are annotated with the given annotation.
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @param annotationCls
//    * the {@link Annotation} that must be present on a method to be matched
//    * @return a list of Methods (possibly empty).
//    * @throws IllegalArgumentException
//    * if the class or annotation are {@code null}
//    * @since 3.4
//    */
//  def getMethodsListWithAnnotation(cls: Class[_], annotationCls: Class[_ <: Annotation]): util.List[Method] = getMethodsListWithAnnotation(cls, annotationCls, false, false)
//
//  /**
//    * Gets all methods of the given class that are annotated with the given annotation.
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @param annotationCls
//    * the {@link java.lang.annotation.Annotation} that must be present on a method to be matched
//    * @param searchSupers
//    * determines if a lookup in the entire inheritance hierarchy of the given class should be performed
//    * @param ignoreAccess
//    * determines if non public methods should be considered
//    * @return an array of Methods (possibly empty).
//    * @throws NullPointerException if the class or annotation are {@code null}
//    * @since 3.6
//    */
//  def getMethodsWithAnnotation(cls: Class[_], annotationCls: Class[_ <: Annotation], searchSupers: Boolean, ignoreAccess: Boolean): Array[Method] = {
//    val annotatedMethodsList = getMethodsListWithAnnotation(cls, annotationCls, searchSupers, ignoreAccess)
//    annotatedMethodsList.toArray(ArrayUtils.EMPTY_METHOD_ARRAY)
//  }
//
//  /**
//    * Gets all methods of the given class that are annotated with the given annotation.
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @param annotationCls
//    * the {@link Annotation} that must be present on a method to be matched
//    * @param searchSupers
//    * determines if a lookup in the entire inheritance hierarchy of the given class should be performed
//    * @param ignoreAccess
//    * determines if non public methods should be considered
//    * @return a list of Methods (possibly empty).
//    * @throws NullPointerException if either the class or annotation class is {@code null}
//    * @since 3.6
//    */
//  def getMethodsListWithAnnotation(cls: Class[_], annotationCls: Class[_ <: Annotation], searchSupers: Boolean, ignoreAccess: Boolean): util.List[Method] = {
//    Validate.notNull(cls, "The class must not be null")
//    Validate.notNull(annotationCls, "The annotation class must not be null")
//
//    val classes =
//      if (searchSupers) getAllSuperclassesAndInterfaces(cls)
//      else new util.ArrayList[Class[_]]()
//
//    classes.add(0, cls)
//    val annotatedMethods = new util.ArrayList[Method]
//    for (acls <- classes.asScala) {
//      val methods = if (ignoreAccess) acls.getDeclaredMethods
//      else acls.getMethods
//      for (method <- methods) {
//        if (method.getAnnotation(annotationCls) != null) annotatedMethods.add(method)
//      }
//    }
//
//    annotatedMethods
//  }
//
//  /**
//    * <p>Gets the annotation object with the given annotation type that is present on the given method
//    * or optionally on any equivalent method in super classes and interfaces. Returns null if the annotation
//    * type was not present.</p>
//    *
//    * <p>Stops searching for an annotation once the first annotation of the specified type has been
//    * found. Additional annotations of the specified type will be silently ignored.</p>
//    *
//    * @tparam A
//    *          the annotation type
//    * @param method
//    *          the {@link Method} to query
//    * @param annotationCls
//    *          the {@link Annotation} to check if is present on the method
//    * @param searchSupers
//    *          determines if a lookup in the entire inheritance hierarchy of the given class is performed
//    *          if the annotation was not directly present
//    * @param ignoreAccess
//    *          determines if underlying method has to be accessible
//    * @return the first matching annotation, or {@code null} if not found
//    * @throws NullPointerException if either the method or annotation class is {@code null}
//    * @since 3.6
//    */
//  def getAnnotation[A <: Annotation](method: Method, annotationCls: Class[A], searchSupers: Boolean, ignoreAccess: Boolean): A = {
//    Validate.notNull(method, "The method must not be null")
//    Validate.notNull(annotationCls, "The annotation class must not be null")
//    if (!ignoreAccess && !MemberUtils.isAccessible(method)) return null.asInstanceOf[A]
//
//    var annotation = method.getAnnotation(annotationCls)
//    if (annotation == null && searchSupers) {
//      val mcls = method.getDeclaringClass
//      val classes = getAllSuperclassesAndInterfaces(mcls)
//
//      breakable {
//        for (acls <- classes.asScala) {
//          val equivalentMethod =
//            if (ignoreAccess) MethodUtils.getMatchingMethod(acls, method.getName, method.getParameterTypes:_*)
//            else MethodUtils.getMatchingAccessibleMethod(acls, method.getName, method.getParameterTypes:_*)
//          if (equivalentMethod != null) {
//            annotation = equivalentMethod.getAnnotation(annotationCls)
//            if (annotation != null) break()
//          }
//        }
//      }
//    }
//    annotation
//  }
//
//  /**
//    * <p>Gets a combination of {@link ClassUtils# getAllSuperclasses ( Class )} and
//    * {@link ClassUtils# getAllInterfaces ( Class )}, one from superclasses, one
//    * from interfaces, and so on in a breadth first way.</p>
//    *
//    * @param cls the class to look up, may be {@code null}
//    * @return the combined {@code List} of superclasses and interfaces in order
//    *         going up from this one
//    *         {@code null} if null input
//    */
//  private def getAllSuperclassesAndInterfaces(cls: Class[_]): util.List[Class[_]] = {
//    if (cls == null) return null
//    val allSuperClassesAndInterfaces = new util.ArrayList[Class[_]]()
//    val allSuperclasses = ClassUtils.getAllSuperclasses(cls)
//    var superClassIndex = 0
//    val allInterfaces = ClassUtils.getAllInterfaces(cls)
//    var interfaceIndex = 0
//
//    while (interfaceIndex < allInterfaces.size || superClassIndex < allSuperclasses.size) {
//      var acls: Class[_] = null
//      if (interfaceIndex >= allInterfaces.size) acls = allSuperclasses.get({
//        superClassIndex += 1; superClassIndex - 1
//      })
//      else if ((superClassIndex >= allSuperclasses.size) || (interfaceIndex < superClassIndex) || !(superClassIndex < interfaceIndex)) acls = allInterfaces.get({
//        interfaceIndex += 1; interfaceIndex - 1
//      })
//      else acls = allSuperclasses.get({
//        superClassIndex += 1; superClassIndex - 1
//      })
//      allSuperClassesAndInterfaces.add(acls)
//    }
//    allSuperClassesAndInterfaces
//  }
//}
