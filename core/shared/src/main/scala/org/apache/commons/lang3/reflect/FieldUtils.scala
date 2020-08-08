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
//import java.lang.reflect.Field
//import java.lang.reflect.Modifier
//import java.util
//import java.util.Collections
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.ClassUtils
//import org.apache.commons.lang3.JavaVersion
//import org.apache.commons.lang3.StringUtils
//import org.apache.commons.lang3.SystemUtils
//import org.apache.commons.lang3.Validate
//
///**
//  * Utilities for working with {@link Field}s by reflection. Adapted and refactored from the dormant [reflect] Commons
//  * sandbox component.
//  * <p>
//  * The ability is provided to break the scoping restrictions coded by the programmer. This can allow fields to be
//  * changed that shouldn't be. This facility should be used with care.
//  *
//  * @since 2.5
//  */
//object FieldUtils {
//  /**
//    * Gets an accessible {@link Field} by name respecting scope. Superclasses/interfaces will be considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @return the Field object
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty
//    */
//  def getField(cls: Class[_], fieldName: String): Field = {
//    val field = getField(cls, fieldName, false)
//    MemberUtils.setAccessibleWorkaround(field)
//    field
//  }
//
//  /**
//    * Gets an accessible {@link Field} by name, breaking scope if requested. Superclasses/interfaces will be
//    * considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @return the Field object
//    * @throws NullPointerException     if the class is {@code null}
//    * @throws IllegalArgumentException if the field name is blank or empty or is matched at multiple places
//    *                                  in the inheritance hierarchy
//    */
//  def getField(cls: Class[_], fieldName: String, forceAccess: Boolean): Field = {
//    Validate.notNull(cls, "The class must not be null")
//    Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty")
//    // FIXME is this workaround still needed? lang requires Java 6
//    // Sun Java 1.3 has a bugged implementation of getField hence we write the
//    // code ourselves
//    // getField() will return the Field object with the declaring class
//    // set correctly to the class that declares the field. Thus requesting the
//    // field on a subclass will return the field from the superclass.
//    //
//    // priority order for lookup:
//    // searchclass private/protected/package/public
//    // superclass protected/package/public
//    // private/different package blocks access to further superclasses
//    // implementedinterface public
//    // check up the superclass hierarchy
//    var acls = cls
//    while ( {
//      acls != null
//    }) {
//      try {
//        val field = acls.getDeclaredField(fieldName)
//        // getDeclaredField checks for non-public scopes as well
//        // and it returns accurate results
//        if (!Modifier.isPublic(field.getModifiers)) if (forceAccess) field.setAccessible(true)
//        else continue //todo: continue is not supported
//        return field
//      } catch {
//        case ex: NoSuchFieldException =>
//
//        // NOPMD
//        // ignore
//      }
//
//      acls = acls.getSuperclass
//    }
//    // check the public interface case. This must be manually searched for
//    // incase there is a public supersuperclass field hidden by a private/package
//    // superclass field.
//    var `match` = null
//    import scala.collection.JavaConversions._
//    for (class1 <- ClassUtils.getAllInterfaces(cls)) {
//      try {
//        val test = class1.getField(fieldName)
//        Validate.isTrue(`match` == null, "Reference to field %s is ambiguous relative to %s" + "; a matching field exists on two or more implemented interfaces.", fieldName, cls)
//        `match` = test
//      } catch {
//        case ex: NoSuchFieldException =>
//
//      }
//    }
//    `match`
//  }
//
//  /**
//    * Gets an accessible {@link Field} by name respecting scope. Only the specified class will be considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @return the Field object
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty
//    */
//  def getDeclaredField(cls: Class[_], fieldName: String): Field = getDeclaredField(cls, fieldName, false)
//
//  /**
//    * Gets an accessible {@link Field} by name, breaking scope if requested. Only the specified class will be
//    * considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @return the Field object
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty
//    */
//  def getDeclaredField(cls: Class[_], fieldName: String, forceAccess: Boolean): Field = {
//    Validate.notNull(cls, "The class must not be null")
//    Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty")
//    try { // only consider the specified class by using getDeclaredField()
//      val field = cls.getDeclaredField(fieldName)
//      if (!MemberUtils.isAccessible(field)) if (forceAccess) field.setAccessible(true)
//      else return null
//      return field
//    } catch {
//      case e: NoSuchFieldException =>
//
//    }
//    null
//  }
//
//  /**
//    * Gets all fields of the given class and its parents (if any).
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @return an array of Fields (possibly empty).
//    * @throws IllegalArgumentException
//    * if the class is {@code null}
//    * @since 3.2
//    */
//  def getAllFields(cls: Class[_]): Array[Field] = {
//    val allFieldsList = getAllFieldsList(cls)
//    allFieldsList.toArray(ArrayUtils.EMPTY_FIELD_ARRAY)
//  }
//
//  def getAllFieldsList(cls: Class[_]): util.List[Field] = {
//    Validate.notNull(cls, "The class must not be null")
//    val allFields = new util.ArrayList[Field]
//    var currentClass = cls
//    while ( {
//      currentClass != null
//    }) {
//      val declaredFields = currentClass.getDeclaredFields
//      Collections.addAll(allFields, declaredFields)
//      currentClass = currentClass.getSuperclass
//    }
//    allFields
//  }
//
//  /**
//    * Gets all fields of the given class and its parents (if any) that are annotated with the given annotation.
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @param annotationCls
//    * the {@link Annotation} that must be present on a field to be matched
//    * @return an array of Fields (possibly empty).
//    * @throws IllegalArgumentException
//    * if the class or annotation are {@code null}
//    * @since 3.4
//    */
//  def getFieldsWithAnnotation(cls: Class[_], annotationCls: Class[_ <: Annotation]): Array[Field] = {
//    val annotatedFieldsList = getFieldsListWithAnnotation(cls, annotationCls)
//    annotatedFieldsList.toArray(ArrayUtils.EMPTY_FIELD_ARRAY)
//  }
//
//  /**
//    * Gets all fields of the given class and its parents (if any) that are annotated with the given annotation.
//    *
//    * @param cls
//    * the {@link Class} to query
//    * @param annotationCls
//    * the {@link Annotation} that must be present on a field to be matched
//    * @return a list of Fields (possibly empty).
//    * @throws IllegalArgumentException
//    * if the class or annotation are {@code null}
//    * @since 3.4
//    */
//  def getFieldsListWithAnnotation(cls: Class[_], annotationCls: Class[_ <: Annotation]): util.List[Field] = {
//    Validate.notNull(annotationCls, "The annotation class must not be null")
//    val allFields = getAllFieldsList(cls)
//    val annotatedFields = new util.ArrayList[Field]
//    import scala.collection.JavaConversions._
//    for (field <- allFields) {
//      if (field.getAnnotation(annotationCls) != null) annotatedFields.add(field)
//    }
//    annotatedFields
//  }
//
//  /**
//    * Reads an accessible {@code static} {@link Field}.
//    *
//    * @param field
//    * to read
//    * @return the field value
//    * @throws IllegalArgumentException
//    * if the field is {@code null}, or not {@code static}
//    * @throws IllegalAccessException
//    * if the field is not accessible
//    */
//  @throws[IllegalAccessException]
//  def readStaticField(field: Field): Any = readStaticField(field, false)
//
//  /**
//    * Reads a static {@link Field}.
//    *
//    * @param field
//    * to read
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method.
//    * @return the field value
//    * @throws IllegalArgumentException
//    * if the field is {@code null} or not {@code static}
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def readStaticField(field: Field, forceAccess: Boolean): Any = {
//    Validate.notNull(field, "The field must not be null")
//    Validate.isTrue(Modifier.isStatic(field.getModifiers), "The field '%s' is not static", field.getName)
//    readField(field, null.asInstanceOf[Any], forceAccess)
//  }
//
//  /**
//    * Reads the named {@code public static} {@link Field}. Superclasses will be considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @return the value of the field
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty, is not {@code static}, or could
//    * not be found
//    * @throws IllegalAccessException
//    * if the field is not accessible
//    */
//  @throws[IllegalAccessException]
//  def readStaticField(cls: Class[_], fieldName: String): Any = readStaticField(cls, fieldName, false)
//
//  /**
//    * Reads the named {@code static} {@link Field}. Superclasses will be considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @return the Field object
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty, is not {@code static}, or could
//    * not be found
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def readStaticField(cls: Class[_], fieldName: String, forceAccess: Boolean): Any = {
//    val field = getField(cls, fieldName, forceAccess)
//    Validate.notNull(field, "Cannot locate field '%s' on %s", fieldName, cls)
//    // already forced access above, don't repeat it here:
//    readStaticField(field, false)
//  }
//
//  /**
//    * Gets the value of a {@code static} {@link Field} by name. The field must be {@code public}. Only the specified
//    * class will be considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @return the value of the field
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty, is not {@code static}, or could
//    * not be found
//    * @throws IllegalAccessException
//    * if the field is not accessible
//    */
//  @throws[IllegalAccessException]
//  def readDeclaredStaticField(cls: Class[_], fieldName: String): Any = readDeclaredStaticField(cls, fieldName, false)
//
//  /**
//    * Gets the value of a {@code static} {@link Field} by name. Only the specified class will be considered.
//    *
//    * @param cls
//    * the {@link Class} to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @return the Field object
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty, is not {@code static}, or could
//    * not be found
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def readDeclaredStaticField(cls: Class[_], fieldName: String, forceAccess: Boolean): Any = {
//    val field = getDeclaredField(cls, fieldName, forceAccess)
//    Validate.notNull(field, "Cannot locate declared field %s.%s", cls.getName, fieldName)
//    readStaticField(field, false)
//  }
//
//  /**
//    * Reads an accessible {@link Field}.
//    *
//    * @param field
//    * the field to use
//    * @param target
//    * the object to call on, may be {@code null} for {@code static} fields
//    * @return the field value
//    * @throws IllegalArgumentException
//    * if the field is {@code null}
//    * @throws IllegalAccessException
//    * if the field is not accessible
//    */
//  @throws[IllegalAccessException]
//  def readField(field: Field, target: Any): Any = readField(field, target, false)
//
//  /**
//    * Reads a {@link Field}.
//    *
//    * @param field
//    * the field to use
//    * @param target
//    * the object to call on, may be {@code null} for {@code static} fields
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method.
//    * @return the field value
//    * @throws IllegalArgumentException
//    * if the field is {@code null}
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def readField(field: Field, target: Any, forceAccess: Boolean): Any = {
//    Validate.notNull(field, "The field must not be null")
//    if (forceAccess && !field.isAccessible) field.setAccessible(true)
//    else MemberUtils.setAccessibleWorkaround(field)
//    field.get(target)
//  }
//
//  /**
//    * Reads the named {@code public} {@link Field}. Superclasses will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @return the value of the field
//    * @throws IllegalArgumentException
//    * if the class is {@code null}, or the field name is blank or empty or could not be found
//    * @throws IllegalAccessException
//    * if the named field is not {@code public}
//    */
//  @throws[IllegalAccessException]
//  def readField(target: Any, fieldName: String): Any = readField(target, fieldName, false)
//
//  /**
//    * Reads the named {@link Field}. Superclasses will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @return the field value
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, or the field name is blank or empty or could not be found
//    * @throws IllegalAccessException
//    * if the named field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def readField(target: Any, fieldName: String, forceAccess: Boolean): Any = {
//    Validate.notNull(target, "target object must not be null")
//    val cls = target.getClass
//    val field = getField(cls, fieldName, forceAccess)
//    Validate.isTrue(field != null, "Cannot locate field %s on %s", fieldName, cls)
//    readField(field, target, false)
//  }
//
//  /**
//    * Reads the named {@code public} {@link Field}. Only the class of the specified object will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @return the value of the field
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, or the field name is blank or empty or could not be found
//    * @throws IllegalAccessException
//    * if the named field is not {@code public}
//    */
//  @throws[IllegalAccessException]
//  def readDeclaredField(target: Any, fieldName: String): Any = readDeclaredField(target, fieldName, false)
//
//  /**
//    * Gets a {@link Field} value by name. Only the class of the specified object will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match public fields.
//    * @return the Field object
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, or the field name is blank or empty or could not be found
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def readDeclaredField(target: Any, fieldName: String, forceAccess: Boolean): Any = {
//    Validate.notNull(target, "target object must not be null")
//    val cls = target.getClass
//    val field = getDeclaredField(cls, fieldName, forceAccess)
//    Validate.isTrue(field != null, "Cannot locate declared field %s.%s", cls, fieldName)
//    readField(field, target, false)
//  }
//
//  /**
//    * Writes a {@code public static} {@link Field}.
//    *
//    * @param field
//    * to write
//    * @param value
//    * to set
//    * @throws IllegalArgumentException
//    * if the field is {@code null} or not {@code static}, or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not {@code public} or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeStaticField(field: Field, value: Any): Unit = {
//    writeStaticField(field, value, false)
//  }
//
//  /**
//    * Writes a static {@link Field}.
//    *
//    * @param field
//    * to write
//    * @param value
//    * to set
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if the field is {@code null} or not {@code static}, or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeStaticField(field: Field, value: Any, forceAccess: Boolean): Unit = {
//    Validate.notNull(field, "The field must not be null")
//    Validate.isTrue(Modifier.isStatic(field.getModifiers), "The field %s.%s is not static", field.getDeclaringClass.getName, field.getName)
//    writeField(field, null.asInstanceOf[Any], value, forceAccess)
//  }
//
//  /**
//    * Writes a named {@code public static} {@link Field}. Superclasses will be considered.
//    *
//    * @param cls
//    * {@link Class} on which the field is to be found
//    * @param fieldName
//    * to write
//    * @param value
//    * to set
//    * @throws IllegalArgumentException
//    * if {@code cls} is {@code null}, the field name is blank or empty, the field cannot be located or is
//    * not {@code static}, or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not {@code public} or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeStaticField(cls: Class[_], fieldName: String, value: Any): Unit = {
//    writeStaticField(cls, fieldName, value, false)
//  }
//
//  /**
//    * Writes a named {@code static} {@link Field}. Superclasses will be considered.
//    *
//    * @param cls
//    * {@link Class} on which the field is to be found
//    * @param fieldName
//    * to write
//    * @param value
//    * to set
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if {@code cls} is {@code null}, the field name is blank or empty, the field cannot be located or is
//    * not {@code static}, or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeStaticField(cls: Class[_], fieldName: String, value: Any, forceAccess: Boolean): Unit = {
//    val field = getField(cls, fieldName, forceAccess)
//    Validate.notNull(field, "Cannot locate field %s on %s", fieldName, cls)
//    writeStaticField(field, value, false)
//  }
//
//  /**
//    * Writes a named {@code public static} {@link Field}. Only the specified class will be considered.
//    *
//    * @param cls
//    * {@link Class} on which the field is to be found
//    * @param fieldName
//    * to write
//    * @param value
//    * to set
//    * @throws IllegalArgumentException
//    * if {@code cls} is {@code null}, the field name is blank or empty, the field cannot be located or is
//    * not {@code static}, or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not {@code public} or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeDeclaredStaticField(cls: Class[_], fieldName: String, value: Any): Unit = {
//    writeDeclaredStaticField(cls, fieldName, value, false)
//  }
//
//  /**
//    * Writes a named {@code static} {@link Field}. Only the specified class will be considered.
//    *
//    * @param cls
//    * {@link Class} on which the field is to be found
//    * @param fieldName
//    * to write
//    * @param value
//    * to set
//    * @param forceAccess
//    * whether to break scope restrictions using the {@code AccessibleObject#setAccessible(boolean)} method.
//    * {@code false} will only match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if {@code cls} is {@code null}, the field name is blank or empty, the field cannot be located or is
//    * not {@code static}, or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeDeclaredStaticField(cls: Class[_], fieldName: String, value: Any, forceAccess: Boolean): Unit = {
//    val field = getDeclaredField(cls, fieldName, forceAccess)
//    Validate.notNull(field, "Cannot locate declared field %s.%s", cls.getName, fieldName)
//    writeField(field, null.asInstanceOf[Any], value, false)
//  }
//
//  /**
//    * Writes an accessible {@link Field}.
//    *
//    * @param field
//    * to write
//    * @param target
//    * the object to call on, may be {@code null} for {@code static} fields
//    * @param value
//    * to set
//    * @throws IllegalAccessException
//    * if the field or target is {@code null}, the field is not accessible or is {@code final}, or
//    * {@code value} is not assignable
//    */
//  @throws[IllegalAccessException]
//  def writeField(field: Field, target: Any, value: Any): Unit = {
//    writeField(field, target, value, false)
//  }
//
//  /**
//    * Writes a {@link Field}.
//    *
//    * @param field
//    * to write
//    * @param target
//    * the object to call on, may be {@code null} for {@code static} fields
//    * @param value
//    * to set
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if the field is {@code null} or {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible or is {@code final}
//    */
//  @throws[IllegalAccessException]
//  def writeField(field: Field, target: Any, value: Any, forceAccess: Boolean): Unit = {
//    Validate.notNull(field, "The field must not be null")
//    if (forceAccess && !field.isAccessible) field.setAccessible(true)
//    else MemberUtils.setAccessibleWorkaround(field)
//    field.set(target, value)
//  }
//
//  /**
//    * Removes the final modifier from a {@link Field}.
//    *
//    * @param field
//    * to remove the final modifier
//    * @throws IllegalArgumentException
//    * if the field is {@code null}
//    * @since 3.2
//    */
//  def removeFinalModifier(field: Field): Unit = {
//    removeFinalModifier(field, true)
//  }
//
//  /**
//    * Removes the final modifier from a {@link Field}.
//    *
//    * @param field
//    * to remove the final modifier
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if the field is {@code null}
//    * @deprecated As of Java 12, we can no longer drop the {@code final} modifier, thus
//    *             rendering this method obsolete. The JDK discussion about this change can be found
//    *             here: http://mail.openjdk.java.net/pipermail/core-libs-dev/2018-November/056486.html
//    * @since 3.3
//    */
//  @deprecated def removeFinalModifier(field: Field, forceAccess: Boolean): Unit = {
//    Validate.notNull(field, "The field must not be null")
//    try if (Modifier.isFinal(field.getModifiers)) { // Do all JREs implement Field with a private ivar called "modifiers"?
//      val modifiersField = classOf[Field].getDeclaredField("modifiers")
//      val doForceAccess = forceAccess && !modifiersField.isAccessible
//      if (doForceAccess) modifiersField.setAccessible(true)
//      try modifiersField.setInt(field, field.getModifiers & ~Modifier.FINAL)
//      finally if (doForceAccess) modifiersField.setAccessible(false)
//    }
//    catch {
//      case e@(_: NoSuchFieldException | _: IllegalAccessException) =>
//        if (SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_12)) throw new UnsupportedOperationException("In java 12+ final cannot be removed.", e)
//      // else no exception is thrown because we can modify final.
//    }
//  }
//
//  /**
//    * Writes a {@code public} {@link Field}. Superclasses will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param value
//    * to set
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, {@code fieldName} is blank or empty or could not be found, or
//    * {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not accessible
//    */
//  @throws[IllegalAccessException]
//  def writeField(target: Any, fieldName: String, value: Any): Unit = {
//    writeField(target, fieldName, value, false)
//  }
//
//  /**
//    * Writes a {@link Field}. Superclasses will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param value
//    * to set
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, {@code fieldName} is blank or empty or could not be found, or
//    * {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def writeField(target: Any, fieldName: String, value: Any, forceAccess: Boolean): Unit = {
//    Validate.notNull(target, "target object must not be null")
//    val cls = target.getClass
//    val field = getField(cls, fieldName, forceAccess)
//    Validate.isTrue(field != null, "Cannot locate declared field %s.%s", cls.getName, fieldName)
//    writeField(field, target, value, false)
//  }
//
//  /**
//    * Writes a {@code public} {@link Field}. Only the specified class will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param value
//    * to set
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, {@code fieldName} is blank or empty or could not be found, or
//    * {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def writeDeclaredField(target: Any, fieldName: String, value: Any): Unit = {
//    writeDeclaredField(target, fieldName, value, false)
//  }
//
//  /**
//    * Writes a {@code public} {@link Field}. Only the specified class will be considered.
//    *
//    * @param target
//    * the object to reflect, must not be {@code null}
//    * @param fieldName
//    * the field name to obtain
//    * @param value
//    * to set
//    * @param forceAccess
//    * whether to break scope restrictions using the
//    * {@link java.lang.reflect.AccessibleObject# setAccessible ( boolean )} method. {@code false} will only
//    * match {@code public} fields.
//    * @throws IllegalArgumentException
//    * if {@code target} is {@code null}, {@code fieldName} is blank or empty or could not be found, or
//    * {@code value} is not assignable
//    * @throws IllegalAccessException
//    * if the field is not made accessible
//    */
//  @throws[IllegalAccessException]
//  def writeDeclaredField(target: Any, fieldName: String, value: Any, forceAccess: Boolean): Unit = {
//    Validate.notNull(target, "target object must not be null")
//    val cls = target.getClass
//    val field = getDeclaredField(cls, fieldName, forceAccess)
//    Validate.isTrue(field != null, "Cannot locate declared field %s.%s", cls.getName, fieldName)
//    writeField(field, target, value, false)
//  }
//}