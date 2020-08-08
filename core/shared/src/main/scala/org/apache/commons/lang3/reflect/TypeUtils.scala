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
//import java.lang.reflect.Array
//import java.lang.reflect.GenericArrayType
//import java.lang.reflect.GenericDeclaration
//import java.lang.reflect.ParameterizedType
//import java.lang.reflect.Type
//import java.lang.reflect.TypeVariable
//import java.lang.reflect.WildcardType
//import java.util
//import java.util.Collections
//import java.util.Objects
//import org.apache.commons.lang3.ArrayUtils
//import org.apache.commons.lang3.ClassUtils
//import org.apache.commons.lang3.ObjectUtils
//import org.apache.commons.lang3.Validate
//import org.apache.commons.lang3.builder.Builder
//
///**
//  * <p> Utility methods focusing on type inspection, particularly with regard to
//  * generics. </p>
//  *
//  * @since 3.0
//  */
//object TypeUtils {
//
//  /**
//    * {@link WildcardType} builder.
//    *
//    * @since 3.2
//    */
//  class WildcardTypeBuilder private()
//
//  /**
//    * Constructor
//    */
//    extends Builder[WildcardType] {
//    private var upperBounds = null
//    private var lowerBounds = null
//
//    /**
//      * Specify upper bounds of the wildcard type to build.
//      *
//      * @param bounds to set
//      * @return {@code this}
//      */
//    def withUpperBounds(bounds: Type*): TypeUtils.WildcardTypeBuilder = {
//      this.upperBounds = bounds
//      this
//    }
//
//    /**
//      * Specify lower bounds of the wildcard type to build.
//      *
//      * @param bounds to set
//      * @return {@code this}
//      */
//    def withLowerBounds(bounds: Type*): TypeUtils.WildcardTypeBuilder = {
//      this.lowerBounds = bounds
//      this
//    }
//
//    /**
//      * {@inheritDoc }
//      */
//    override def build = new TypeUtils.WildcardTypeImpl(upperBounds, lowerBounds)
//  }
//
//  /**
//    * GenericArrayType implementation class.
//    *
//    * @since 3.2
//    */
//  final private class GenericArrayTypeImpl private(val componentType: Type)
//
//  /**
//    * Constructor
//    *
//    * @param componentType of this array type
//    */
//    extends GenericArrayType {
//    override def getGenericComponentType: Type = componentType
//
//    override def toString: String = TypeUtils.toString(this)
//
//    override def equals(obj: Any): Boolean = (obj eq this) || obj.isInstanceOf[GenericArrayType] && TypeUtils.equals(this, obj.asInstanceOf[GenericArrayType])
//
//    override def hashCode: Int = {
//      var result = 67 << 4
//      result |= componentType.hashCode
//      result
//    }
//  }
//
//  /**
//    * ParameterizedType implementation class.
//    *
//    * @since 3.2
//    */
//  final private class ParameterizedTypeImpl private(val raw: Class[_], val useOwner: Type, val typeArguments: Array[Type])
//
//  /**
//    * Constructor
//    *
//    * @param rawClass      type
//    * @param useOwner      owner type to use, if any
//    * @param typeArguments formal type arguments
//    */
//    extends ParameterizedType {
//    this.typeArguments = Arrays.copyOf(typeArguments, typeArguments.length, classOf[Array[Type]])
//    final private var typeArguments = null
//
//    override def getRawType: Type = raw
//
//    override def getOwnerType: Type = useOwner
//
//    override def getActualTypeArguments: Array[Type] = typeArguments.clone
//
//    override def toString: String = TypeUtils.toString(this)
//
//    override def equals(obj: Any): Boolean = (obj eq this) || obj.isInstanceOf[ParameterizedType] && TypeUtils.equals(this, obj.asInstanceOf[ParameterizedType])
//
//    override def hashCode: Int = {
//      var result = 71 << 4
//      result |= raw.hashCode
//      result <<= 4
//      result |= Objects.hashCode(useOwner)
//      result <<= 8
//      result |= util.Arrays.hashCode(typeArguments)
//      result
//    }
//  }
//
//  /**
//    * WildcardType implementation class.
//    *
//    * @since 3.2
//    */
//  final private class WildcardTypeImpl private(val upperBounds: Array[Type], val lowerBounds: Array[Type])
//
//  /**
//    * Constructor
//    *
//    * @param upperBounds of this type
//    * @param lowerBounds of this type
//    */
//    extends WildcardType {
//    this.upperBounds = ObjectUtils.defaultIfNull(upperBounds, ArrayUtils.EMPTY_TYPE_ARRAY)
//    this.lowerBounds = ObjectUtils.defaultIfNull(lowerBounds, ArrayUtils.EMPTY_TYPE_ARRAY)
//    final private var upperBounds = null
//    final private var lowerBounds = null
//
//    override def getUpperBounds: Array[Type] = upperBounds.clone
//
//    override def getLowerBounds: Array[Type] = lowerBounds.clone
//
//    override def toString: String = TypeUtils.toString(this)
//
//    override def equals(obj: Any): Boolean = (obj eq this) || obj.isInstanceOf[WildcardType] && TypeUtils.equals(this, obj.asInstanceOf[WildcardType])
//
//    override def hashCode: Int = {
//      var result = 73 << 8
//      result |= util.Arrays.hashCode(upperBounds)
//      result <<= 8
//      result |= util.Arrays.hashCode(lowerBounds)
//      result
//    }
//  }
//
//  /**
//    * A wildcard instance matching {@code ?}.
//    *
//    * @since 3.2
//    */
//  val WILDCARD_ALL: WildcardType = wildcardType.withUpperBounds(classOf[Any]).build
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target type
//    * following the Java generics rules. If both types are {@link Class}
//    * objects, the method returns the result of
//    * {@link ClassUtils# isAssignable ( Class, Class)}.</p>
//    *
//    * @param type   the subject type to be assigned to the target type
//    * @param toType the target type
//    * @return {@code true} if {@code type} is assignable to {@code toType}.
//    */
//  def isAssignable(`type`: Type, toType: Type): Boolean = isAssignable(`type`, toType, null)
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target type
//    * following the Java generics rules.</p>
//    *
//    * @param type           the subject type to be assigned to the target type
//    * @param toType         the target type
//    * @param typeVarAssigns optional map of type variable assignments
//    * @return {@code true} if {@code type} is assignable to {@code toType}.
//    */
//  private def isAssignable(`type`: Type, toType: Type, typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Boolean = {
//    if (toType == null || toType.isInstanceOf[Class[_]]) return isAssignable(`type`, toType.asInstanceOf[Class[_]])
//    if (toType.isInstanceOf[ParameterizedType]) return isAssignable(`type`, toType.asInstanceOf[ParameterizedType], typeVarAssigns)
//    if (toType.isInstanceOf[GenericArrayType]) return isAssignable(`type`, toType.asInstanceOf[GenericArrayType], typeVarAssigns)
//    if (toType.isInstanceOf[WildcardType]) return isAssignable(`type`, toType.asInstanceOf[WildcardType], typeVarAssigns)
//    if (toType.isInstanceOf[TypeVariable[_]]) return isAssignable(`type`, toType.asInstanceOf[TypeVariable[_]], typeVarAssigns)
//    throw new IllegalStateException("found an unhandled type: " + toType)
//  }
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target class
//    * following the Java generics rules.</p>
//    *
//    * @param type    the subject type to be assigned to the target type
//    * @param toClass the target class
//    * @return {@code true} if {@code type} is assignable to {@code toClass}.
//    */
//  private def isAssignable(`type`: Type, toClass: Class[_]): Boolean = {
//    if (`type` == null) { // consistency with ClassUtils.isAssignable() behavior
//      return toClass == null || !toClass.isPrimitive
//    }
//    // only a null type can be assigned to null type which
//    // would have cause the previous to return true
//    if (toClass == null) return false
//    // all types are assignable to themselves
//    if (toClass == `type`) return true
//    if (`type`.isInstanceOf[Class[_]]) { // just comparing two classes
//      return ClassUtils.isAssignable(`type`.asInstanceOf[Class[_]], toClass)
//    }
//    if (`type`.isInstanceOf[ParameterizedType]) { // only have to compare the raw type to the class
//      return isAssignable(getRawType(`type`.asInstanceOf[ParameterizedType]), toClass)
//    }
//    // *
//    if (`type`.isInstanceOf[TypeVariable[_]]) { // if any of the bounds are assignable to the class, then the
//      // type is assignable to the class.
//      for (bound <- `type`.asInstanceOf[TypeVariable[_]].getBounds) {
//        if (isAssignable(bound, toClass)) return true
//      }
//      return false
//    }
//    // the only classes to which a generic array type can be assigned
//    // are class Object and array classes
//    if (`type`.isInstanceOf[GenericArrayType]) return toClass == classOf[Any] || toClass.isArray && isAssignable(`type`.asInstanceOf[GenericArrayType].getGenericComponentType, toClass.getComponentType)
//    // wildcard types are not assignable to a class (though one would think
//    // "? super Object" would be assignable to Object)
//    if (`type`.isInstanceOf[WildcardType]) return false
//    throw new IllegalStateException("found an unhandled type: " + `type`)
//  }
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target
//    * parameterized type following the Java generics rules.</p>
//    *
//    * @param type                the subject type to be assigned to the target type
//    * @param toParameterizedType the target parameterized type
//    * @param typeVarAssigns      a map with type variables
//    * @return {@code true} if {@code type} is assignable to {@code toType}.
//    */
//  private def isAssignable(`type`: Type, toParameterizedType: ParameterizedType, typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Boolean = {
//    if (`type` == null) return true
//    if (toParameterizedType == null) return false
//    if (toParameterizedType == `type`) return true
//    // get the target type's raw type
//    val toClass = getRawType(toParameterizedType)
//    // get the subject type's type arguments including owner type arguments
//    // and supertype arguments up to and including the target class.
//    val fromTypeVarAssigns = getTypeArguments(`type`, toClass, null)
//    // null means the two types are not compatible
//    if (fromTypeVarAssigns == null) return false
//    // compatible types, but there's no type arguments. this is equivalent
//    // to comparing Map< ?, ? > to Map, and raw types are always assignable
//    // to parameterized types.
//    if (fromTypeVarAssigns.isEmpty) return true
//    // get the target type's type arguments including owner type arguments
//    val toTypeVarAssigns = getTypeArguments(toParameterizedType, toClass, typeVarAssigns)
//    // now to check each type argument
//    import scala.collection.JavaConversions._
//    for (`var` <- toTypeVarAssigns.keySet) {
//      val toTypeArg = unrollVariableAssignments(`var`, toTypeVarAssigns)
//      val fromTypeArg = unrollVariableAssignments(`var`, fromTypeVarAssigns)
//      if (toTypeArg == null && fromTypeArg.isInstanceOf[Class[_]]) continue //todo: continue is not supported
//      // parameters must either be absent from the subject type, within
//      // the bounds of the wildcard type, or be an exact match to the
//      // parameters of the target type.
//      if (fromTypeArg != null && !(toTypeArg == fromTypeArg) && !(toTypeArg.isInstanceOf[WildcardType] && isAssignable(fromTypeArg, toTypeArg, typeVarAssigns))) return false
//    }
//    true
//  }
//
//  /**
//    * Look up {@code var} in {@code typeVarAssigns} <em>transitively</em>,
//    * i.e. keep looking until the value found is <em>not</em> a type variable.
//    *
//    * @param typeVariable   the type variable to look up
//    * @param typeVarAssigns the map used for the look up
//    * @return Type or {@code null} if some variable was not in the map
//    * @since 3.2
//    */
//  private def unrollVariableAssignments(typeVariable: TypeVariable[_], typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}) = {
//    var result = null
//    do {
//      result = typeVarAssigns.get(typeVariable)
//      if (result.isInstanceOf[TypeVariable[_]] && !(result == typeVariable)) {
//        typeVariable = result.asInstanceOf[TypeVariable[_]]
//        continue //todo: continue is not supported
//
//      }
//      break //todo: break is not supported
//
//    } while ( {
//      true
//    })
//    result
//  }
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target
//    * generic array type following the Java generics rules.</p>
//    *
//    * @param type               the subject type to be assigned to the target type
//    * @param toGenericArrayType the target generic array type
//    * @param typeVarAssigns     a map with type variables
//    * @return {@code true} if {@code type} is assignable to
//    *         {@code toGenericArrayType}.
//    */
//  private def isAssignable(`type`: Type, toGenericArrayType: GenericArrayType, typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Boolean = {
//    if (`type` == null) return true
//    if (toGenericArrayType == null) return false
//    if (toGenericArrayType == `type`) return true
//    val toComponentType = toGenericArrayType.getGenericComponentType
//    if (`type`.isInstanceOf[Class[_]]) {
//      val cls = `type`.asInstanceOf[Class[_]]
//      // compare the component types
//      return cls.isArray && isAssignable(cls.getComponentType, toComponentType, typeVarAssigns)
//    }
//    if (`type`.isInstanceOf[GenericArrayType]) return isAssignable(`type`.asInstanceOf[GenericArrayType].getGenericComponentType, toComponentType, typeVarAssigns)
//    if (`type`.isInstanceOf[WildcardType]) { // so long as one of the upper bounds is assignable, it's good
//      for (bound <- getImplicitUpperBounds(`type`.asInstanceOf[WildcardType])) {
//        if (isAssignable(bound, toGenericArrayType)) return true
//      }
//      return false
//    }
//    if (`type`.isInstanceOf[TypeVariable[_]]) { // probably should remove the following logic and just return false.
//      // type variables cannot specify arrays as bounds.
//      for (bound <- getImplicitBounds(`type`.asInstanceOf[TypeVariable[_]])) {
//        if (isAssignable(bound, toGenericArrayType)) return true
//      }
//      return false
//    }
//    if (`type`.isInstanceOf[ParameterizedType]) { // the raw type of a parameterized type is never an array or
//      // generic array, otherwise the declaration would look like this:
//      // Collection[]< ? extends String > collection;
//      return false
//    }
//    throw new IllegalStateException("found an unhandled type: " + `type`)
//  }
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target
//    * wildcard type following the Java generics rules.</p>
//    *
//    * @param type           the subject type to be assigned to the target type
//    * @param toWildcardType the target wildcard type
//    * @param typeVarAssigns a map with type variables
//    * @return {@code true} if {@code type} is assignable to
//    *         {@code toWildcardType}.
//    */
//  private def isAssignable(`type`: Type, toWildcardType: WildcardType, typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Boolean = {
//    if (`type` == null) return true
//    if (toWildcardType == null) return false
//    if (toWildcardType == `type`) return true
//    val toUpperBounds = getImplicitUpperBounds(toWildcardType)
//    val toLowerBounds = getImplicitLowerBounds(toWildcardType)
//    if (`type`.isInstanceOf[WildcardType]) {
//      val wildcardType = `type`.asInstanceOf[WildcardType]
//      val upperBounds = getImplicitUpperBounds(wildcardType)
//      val lowerBounds = getImplicitLowerBounds(wildcardType)
//      for (toBound <- toUpperBounds) { // if there are assignments for unresolved type variables,
//        // now's the time to substitute them.
//        toBound = substituteTypeVariables(toBound, typeVarAssigns)
//        // each upper bound of the subject type has to be assignable to
//        // each
//        // upper bound of the target type
//        for (bound <- upperBounds) {
//          if (!isAssignable(bound, toBound, typeVarAssigns)) return false
//        }
//      }
//      for (toBound <- toLowerBounds) {
//        toBound = substituteTypeVariables(toBound, typeVarAssigns)
//        // each lower bound of the target type has to be assignable to
//        // lower bound of the subject type
//        for (bound <- lowerBounds) {
//          if (!isAssignable(toBound, bound, typeVarAssigns)) return false
//        }
//      }
//      return true
//    }
//    for (toBound <- toUpperBounds) {
//      if (!isAssignable(`type`, substituteTypeVariables(toBound, typeVarAssigns), typeVarAssigns)) return false
//    }
//    for (toBound <- toLowerBounds) {
//      if (!isAssignable(substituteTypeVariables(toBound, typeVarAssigns), `type`, typeVarAssigns)) return false
//    }
//    true
//  }
//
//  /**
//    * <p>Checks if the subject type may be implicitly cast to the target type
//    * variable following the Java generics rules.</p>
//    *
//    * @param type           the subject type to be assigned to the target type
//    * @param toTypeVariable the target type variable
//    * @param typeVarAssigns a map with type variables
//    * @return {@code true} if {@code type} is assignable to
//    *         {@code toTypeVariable}.
//    */
//  private def isAssignable(`type`: Type, toTypeVariable: TypeVariable[_], typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Boolean = {
//    if (`type` == null) return true
//    if (toTypeVariable == null) return false
//    if (toTypeVariable == `type`) return true
//    if (`type`.isInstanceOf[TypeVariable[_]]) { // a type variable is assignable to another type variable, if
//      // and only if the former is the latter, extends the latter, or
//      // is otherwise a descendant of the latter.
//      val bounds = getImplicitBounds(`type`.asInstanceOf[TypeVariable[_]])
//      for (bound <- bounds) {
//        if (isAssignable(bound, toTypeVariable, typeVarAssigns)) return true
//      }
//    }
//    if (`type`.isInstanceOf[Class[_]] || `type`.isInstanceOf[ParameterizedType] || `type`.isInstanceOf[GenericArrayType] || `type`.isInstanceOf[WildcardType]) return false
//    throw new IllegalStateException("found an unhandled type: " + `type`)
//  }
//
//  /**
//    * <p>Find the mapping for {@code type} in {@code typeVarAssigns}.</p>
//    *
//    * @param type           the type to be replaced
//    * @param typeVarAssigns the map with type variables
//    * @return the replaced type
//    * @throws IllegalArgumentException if the type cannot be substituted
//    */
//  private def substituteTypeVariables(`type`: Type, typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Type = {
//    if (`type`.isInstanceOf[TypeVariable[_]] && typeVarAssigns != null) {
//      val replacementType = typeVarAssigns.get(`type`)
//      if (replacementType == null) throw new IllegalArgumentException("missing assignment type for type variable " + `type`)
//      return replacementType
//    }
//    `type`
//  }
//
//  /**
//    * <p>Retrieves all the type arguments for this parameterized type
//    * including owner hierarchy arguments such as
//    * {@code Outer<K, V>.Inner<T>.DeepInner<E>} .
//    * The arguments are returned in a
//    * {@link Map} specifying the argument type for each {@link TypeVariable}.
//    * </p>
//    *
//    * @param type specifies the subject parameterized type from which to
//    *             harvest the parameters.
//    * @return a {@code Map} of the type arguments to their respective type
//    *         variables.
//    */
//  def getTypeArguments(`type`: ParameterizedType): (util.Map[TypeVariable[_$1], Type]) forSome {type _$1} = getTypeArguments(`type`, getRawType(`type`), null)
//
//  /**
//    * <p>Gets the type arguments of a class/interface based on a subtype. For
//    * instance, this method will determine that both of the parameters for the
//    * interface {@link Map} are {@link Object} for the subtype
//    * {@link java.util.Properties Properties} even though the subtype does not
//    * directly implement the {@code Map} interface.</p>
//    * <p>This method returns {@code null} if {@code type} is not assignable to
//    * {@code toClass}. It returns an empty map if none of the classes or
//    * interfaces in its inheritance hierarchy specify any type arguments.</p>
//    * <p>A side effect of this method is that it also retrieves the type
//    * arguments for the classes and interfaces that are part of the hierarchy
//    * between {@code type} and {@code toClass}. So with the above
//    * example, this method will also determine that the type arguments for
//    * {@link java.util.Hashtable Hashtable} are also both {@code Object}.
//    * In cases where the interface specified by {@code toClass} is
//    * (indirectly) implemented more than once (e.g. where {@code toClass}
//    * specifies the interface {@link java.lang.Iterable Iterable} and
//    * {@code type} specifies a parameterized type that implements both
//    * {@link java.util.Set Set} and {@link java.util.Collection Collection}),
//    * this method will look at the inheritance hierarchy of only one of the
//    * implementations/subclasses; the first interface encountered that isn't a
//    * subinterface to one of the others in the {@code type} to
//    * {@code toClass} hierarchy.</p>
//    *
//    * @param type    the type from which to determine the type parameters of
//    *                {@code toClass}
//    * @param toClass the class whose type parameters are to be determined based
//    *                on the subtype {@code type}
//    * @return a {@code Map} of the type assignments for the type variables in
//    *         each type in the inheritance hierarchy from {@code type} to
//    *         {@code toClass} inclusive.
//    */
//  def getTypeArguments(`type`: Type, toClass: Class[_]): (util.Map[TypeVariable[_$1], Type]) forSome {type _$1} = getTypeArguments(`type`, toClass, null)
//
//  /**
//    * <p>Return a map of the type arguments of {@code type} in the context of {@code toClass}.</p>
//    *
//    * @param type              the type in question
//    * @param toClass           the class
//    * @param subtypeVarAssigns a map with type variables
//    * @return the {@code Map} with type arguments
//    */
//  private def getTypeArguments(`type`: Type, toClass: Class[_], subtypeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): (util.Map[TypeVariable[_$1], Type]) forSome {type _$1} = {
//    if (`type`.isInstanceOf[Class[_]]) return getTypeArguments(`type`.asInstanceOf[Class[_]], toClass, subtypeVarAssigns)
//    if (`type`.isInstanceOf[ParameterizedType]) return getTypeArguments(`type`.asInstanceOf[ParameterizedType], toClass, subtypeVarAssigns)
//    if (`type`.isInstanceOf[GenericArrayType]) return getTypeArguments(`type`.asInstanceOf[GenericArrayType].getGenericComponentType, if (toClass.isArray) toClass.getComponentType
//    else toClass, subtypeVarAssigns)
//    // since wildcard types are not assignable to classes, should this just
//    // return null?
//    if (`type`.isInstanceOf[WildcardType]) {
//      for (bound <- getImplicitUpperBounds(`type`.asInstanceOf[WildcardType])) { // find the first bound that is assignable to the target class
//        if (isAssignable(bound, toClass)) return getTypeArguments(bound, toClass, subtypeVarAssigns)
//      }
//      return null
//    }
//    if (`type`.isInstanceOf[TypeVariable[_]]) {
//      for (bound <- getImplicitBounds(`type`.asInstanceOf[TypeVariable[_]])) {
//        if (isAssignable(bound, toClass)) return getTypeArguments(bound, toClass, subtypeVarAssigns)
//      }
//      return null
//    }
//    throw new IllegalStateException("found an unhandled type: " + `type`)
//  }
//
//  /**
//    * <p>Return a map of the type arguments of a parameterized type in the context of {@code toClass}.</p>
//    *
//    * @param parameterizedType the parameterized type
//    * @param toClass           the class
//    * @param subtypeVarAssigns a map with type variables
//    * @return the {@code Map} with type arguments
//    */
//  private def getTypeArguments(parameterizedType: ParameterizedType, toClass: Class[_], subtypeVarAssigns: util.Map[TypeVariable[_], Type]): util.Map[TypeVariable[_], Type] = {
//    val cls = getRawType(parameterizedType)
//    // make sure they're assignable
//    if (!isAssignable(cls, toClass)) return null
//    val ownerType = parameterizedType.getOwnerType
//    var typeVarAssigns: util.Map[TypeVariable[_], Type] = null
//
//    if (ownerType.isInstanceOf[ParameterizedType]) { // get the owner type arguments first
//      val parameterizedOwnerType = ownerType.asInstanceOf[ParameterizedType]
//      typeVarAssigns = getTypeArguments(parameterizedOwnerType, getRawType(parameterizedOwnerType), subtypeVarAssigns)
//    }
//    else { // no owner, prep the type variable assignments map
//      typeVarAssigns =
//        if (subtypeVarAssigns == null) new util.HashMap[TypeVariable[_], Type]
//        else subtypeVarAssigns
//    }
//    // get the subject parameterized type's arguments
//    val typeArgs = parameterizedType.getActualTypeArguments
//    // and get the corresponding type variables from the raw class
//    val typeParams = cls.getTypeParameters
//    // map the arguments to their respective type variables
//    for (i <- 0 until typeParams.length) {
//      val typeArg = typeArgs(i)
//      typeVarAssigns.put(typeParams(i), typeVarAssigns.getOrDefault(typeArg, typeArg))
//    }
//    if (toClass == cls) { // target class has been reached. Done.
//      return typeVarAssigns
//    }
//    // walk the inheritance hierarchy until the target class is reached
//    getTypeArguments(getClosestParentType(cls, toClass), toClass, typeVarAssigns)
//  }
//
//  /**
//    * <p>Return a map of the type arguments of a class in the context of {@code toClass}.</p>
//    *
//    * @param cls               the class in question
//    * @param toClass           the context class
//    * @param subtypeVarAssigns a map with type variables
//    * @return the {@code Map} with type arguments
//    */
//  private def getTypeArguments(cls: Class[_], toClass: Class[_], subtypeVarAssigns: util.Map[TypeVariable[_], Type]): util.Map[TypeVariable[_], Type] = {
//    if (!isAssignable(cls, toClass)) return null
//
//    // can't work with primitives
//    if (cls.isPrimitive) { // both classes are primitives?
//      if (toClass.isPrimitive) { // dealing with widening here. No type arguments to be
//        // harvested with these two types.
//        return new util.HashMap[TypeVariable[_], Type]
//      }
//      // work with wrapper the wrapper class instead of the primitive
//      cls = ClassUtils.primitiveToWrapper(cls)
//    }
//
//    // create a copy of the incoming map, or an empty one if it's null
//    val typeVarAssigns: util.Map[TypeVariable[_], Type] =
//      if (subtypeVarAssigns == null) new util.HashMap[TypeVariable[_], Type]
//      else subtypeVarAssigns
//    }
//
//    // has target class been reached?
//    if (toClass == cls) return typeVarAssigns
//    getTypeArguments(getClosestParentType(cls, toClass), toClass, typeVarAssigns)
//  }
//
//  /**
//    * <p>Tries to determine the type arguments of a class/interface based on a
//    * super parameterized type's type arguments. This method is the inverse of
//    * {@link #getTypeArguments ( Type, Class)} which gets a class/interface's
//    * type arguments based on a subtype. It is far more limited in determining
//    * the type arguments for the subject class's type variables in that it can
//    * only determine those parameters that map from the subject {@link Class}
//    * object to the supertype.</p> <p>Example: {@link java.util.TreeSet
//    * TreeSet} sets its parameter as the parameter for
//    * {@link java.util.NavigableSet NavigableSet}, which in turn sets the
//    * parameter of {@link java.util.SortedSet}, which in turn sets the
//    * parameter of {@link Set}, which in turn sets the parameter of
//    * {@link java.util.Collection}, which in turn sets the parameter of
//    * {@link java.lang.Iterable}. Since {@code TreeSet}'s parameter maps
//    * (indirectly) to {@code Iterable}'s parameter, it will be able to
//    * determine that based on the super type {@code Iterable<? extends
//     * Map<Integer, ? extends Collection<?>>>}, the parameter of
//    * {@code TreeSet} is {@code ? extends Map<Integer, ? extends
//     * Collection<?>>}.</p>
//    *
//    * @param cls       the class whose type parameters are to be determined, not {@code null}
//    * @param superType the super type from which {@code cls}'s type
//    *                  arguments are to be determined, not {@code null}
//    * @return a {@code Map} of the type assignments that could be determined
//    *         for the type variables in each type in the inheritance hierarchy from
//    *         {@code type} to {@code toClass} inclusive.
//    */
//  def determineTypeArguments(cls: Class[_], superType: ParameterizedType): util.Map[TypeVariable[_], Type] = {
//    Validate.notNull(cls, "cls is null")
//    Validate.notNull(superType, "superType is null")
//    val superClass = getRawType(superType)
//    // compatibility check
//    if (!isAssignable(cls, superClass)) return null
//    if (cls == superClass) return getTypeArguments(superType, superClass, null)
//    // get the next class in the inheritance hierarchy
//    val midType = getClosestParentType(cls, superClass)
//    // can only be a class or a parameterized type
//    if (midType.isInstanceOf[Class[_]]) return determineTypeArguments(midType.asInstanceOf[Class[_]], superType)
//    val midParameterizedType = midType.asInstanceOf[ParameterizedType]
//    val midClass = getRawType(midParameterizedType)
//    // get the type variables of the mid class that map to the type
//    // arguments of the super class
//    val typeVarAssigns = determineTypeArguments(midClass, superType)
//    // map the arguments of the mid type to the class type variables
//    mapTypeVariablesToArguments(cls, midParameterizedType, typeVarAssigns)
//    typeVarAssigns
//  }
//
//  /**
//    * <p>Performs a mapping of type variables.</p>
//    *
//    * @tparam T                the generic type of the class in question
//    * @param cls               the class in question
//    * @param parameterizedType the parameterized type
//    * @param typeVarAssigns    the map to be filled
//    */
//  private def mapTypeVariablesToArguments[T](cls: Class[T], parameterizedType: ParameterizedType, typeVarAssigns: util.Map[TypeVariable[_], Type]): Unit = {
//    // capture the type variables from the owner type that have assignments
//    val ownerType = parameterizedType.getOwnerType
//    if (ownerType.isInstanceOf[ParameterizedType]) { // recursion to make sure the owner's owner type gets processed
//      mapTypeVariablesToArguments(cls, ownerType.asInstanceOf[ParameterizedType], typeVarAssigns)
//    }
//    // parameterizedType is a generic interface/class (or it's in the owner
//    // hierarchy of said interface/class) implemented/extended by the class
//    // cls. Find out which type variables of cls are type arguments of
//    // parameterizedType:
//    val typeArgs = parameterizedType.getActualTypeArguments
//    // of the cls's type variables that are arguments of parameterizedType,
//    // find out which ones can be determined from the super type's arguments
//    val typeVars = getRawType(parameterizedType).getTypeParameters
//    // use List view of type parameters of cls so the contains() method can be used:
//    val typeVarList = util.Arrays.asList(cls.getTypeParameters)
//    for (i <- 0 until typeArgs.length) {
//      val typeVar = typeVars(i)
//      val typeArg = typeArgs(i)
//      // argument of parameterizedType is a type variable of cls
//      if (typeVarList.contains(typeArg) && // type variable of parameterizedType has an assignment in
//        // the super type.
//        typeVarAssigns.containsKey(typeVar)) { // map the assignment to the cls's type variable
//        typeVarAssigns.put(typeArg.asInstanceOf[TypeVariable[_]], typeVarAssigns.get(typeVar))
//      }
//    }
//  }
//
//  /**
//    * <p>Get the closest parent type to the
//    * super class specified by {@code superClass}.</p>
//    *
//    * @param cls        the class in question
//    * @param superClass the super class
//    * @return the closes parent type
//    */
//  private def getClosestParentType(cls: Class[_], superClass: Class[_]): Type = { // only look at the interfaces if the super class is also an interface
//    if (superClass.isInterface) { // get the generic interfaces of the subject class
//      val interfaceTypes = cls.getGenericInterfaces
//      // will hold the best generic interface match found
//      var genericInterface = null
//      // find the interface closest to the super class
//      for (midType <- interfaceTypes) {
//        var midClass = null
//        if (midType.isInstanceOf[ParameterizedType]) midClass = getRawType(midType.asInstanceOf[ParameterizedType])
//        else if (midType.isInstanceOf[Class[_]]) midClass = midType.asInstanceOf[Class[_]]
//        else throw new IllegalStateException("Unexpected generic" + " interface type found: " + midType)
//        // check if this interface is further up the inheritance chain
//        // than the previously found match
//        if (isAssignable(midClass, superClass) && isAssignable(genericInterface, midClass.asInstanceOf[Type])) genericInterface = midType
//      }
//      // found a match?
//      if (genericInterface != null) return genericInterface
//    }
//    // none of the interfaces were descendants of the target class, so the
//    // super class has to be one, instead
//    cls.getGenericSuperclass
//  }
//
//  /**
//    * <p>Checks if the given value can be assigned to the target type
//    * following the Java generics rules.</p>
//    *
//    * @param value the value to be checked
//    * @param type  the target type
//    * @return {@code true} if {@code value} is an instance of {@code type}.
//    */
//  def isInstance(value: Any, `type`: Type): Boolean = {
//    if (`type` == null) return false
//    if (value == null) !`type`.isInstanceOf[Class[_]] || !(`type`.asInstanceOf[Class[_]]).isPrimitive
//    else isAssignable(value.getClass, `type`, null)
//  }
//
//  /**
//    * <p>This method strips out the redundant upper bound types in type
//    * variable types and wildcard types (or it would with wildcard types if
//    * multiple upper bounds were allowed).</p> <p>Example, with the variable
//    * type declaration:
//    *
//    * <pre>&lt;K extends java.util.Collection&lt;String&gt; &amp;
//    * java.util.List&lt;String&gt;&gt;</pre>
//    *
//    * <p>
//    * since {@code List} is a subinterface of {@code Collection},
//    * this method will return the bounds as if the declaration had been:
//    * </p>
//    *
//    * <pre>&lt;K extends java.util.List&lt;String&gt;&gt;</pre>
//    *
//    * @param bounds an array of types representing the upper bounds of either
//    *               {@link WildcardType} or {@link TypeVariable}, not {@code null}.
//    * @return an array containing the values from {@code bounds} minus the
//    *         redundant types.
//    */
//  def normalizeUpperBounds(bounds: Array[Type]): Array[Type] = {
//    Validate.notNull(bounds, "null value specified for bounds array")
//    // don't bother if there's only one (or none) type
//    if (bounds.length < 2) return bounds
//    val types = new util.HashSet[Type](bounds.length)
//    for (type1 <- bounds) {
//      var subtypeFound = false
//      for (type2 <- bounds) {
//        if ((type1 ne type2) && isAssignable(type2, type1, null)) {
//          subtypeFound = true
//          break //todo: break is not supported
//
//        }
//      }
//      if (!subtypeFound) types.add(type1)
//    }
//    types.toArray(ArrayUtils.EMPTY_TYPE_ARRAY)
//  }
//
//  /**
//    * <p>Returns an array containing the sole type of {@link Object} if
//    * {@link TypeVariable# getBounds ( )} returns an empty array. Otherwise, it
//    * returns the result of {@link TypeVariable# getBounds ( )} passed into
//    * {@link #normalizeUpperBounds}.</p>
//    *
//    * @param typeVariable the subject type variable, not {@code null}
//    * @return a non-empty array containing the bounds of the type variable.
//    */
//  def getImplicitBounds(typeVariable: TypeVariable[_]): Array[Type] = {
//    Validate.notNull(typeVariable, "typeVariable is null")
//    val bounds = typeVariable.getBounds
//    if (bounds.length == 0) Array[Type](classOf[Any])
//    else normalizeUpperBounds(bounds)
//  }
//
//  /**
//    * <p>Returns an array containing the sole value of {@link Object} if
//    * {@link WildcardType# getUpperBounds ( )} returns an empty array. Otherwise,
//    * it returns the result of {@link WildcardType# getUpperBounds ( )}
//    * passed into {@link #normalizeUpperBounds}.</p>
//    *
//    * @param wildcardType the subject wildcard type, not {@code null}
//    * @return a non-empty array containing the upper bounds of the wildcard
//    *         type.
//    */
//  def getImplicitUpperBounds(wildcardType: WildcardType): Array[Type] = {
//    Validate.notNull(wildcardType, "wildcardType is null")
//    val bounds = wildcardType.getUpperBounds
//    if (bounds.length == 0) Array[Type](classOf[Any])
//    else normalizeUpperBounds(bounds)
//  }
//
//  /**
//    * <p>Returns an array containing a single value of {@code null} if
//    * {@link WildcardType# getLowerBounds ( )} returns an empty array. Otherwise,
//    * it returns the result of {@link WildcardType# getLowerBounds ( )}.</p>
//    *
//    * @param wildcardType the subject wildcard type, not {@code null}
//    * @return a non-empty array containing the lower bounds of the wildcard
//    *         type.
//    */
//  def getImplicitLowerBounds(wildcardType: WildcardType): Array[Type] = {
//    Validate.notNull(wildcardType, "wildcardType is null")
//    val bounds = wildcardType.getLowerBounds
//    if (bounds.length == 0) Array[Type](null)
//    else bounds
//  }
//
//  /**
//    * <p>Determines whether or not specified types satisfy the bounds of their
//    * mapped type variables. When a type parameter extends another (such as
//    * {@code <T, S extends T>}), uses another as a type parameter (such as
//    * {@code <T, S extends Comparable>>}), or otherwise depends on
//    * another type variable to be specified, the dependencies must be included
//    * in {@code typeVarAssigns}.</p>
//    *
//    * @param typeVarAssigns specifies the potential types to be assigned to the
//    *                       type variables, not {@code null}.
//    * @return whether or not the types can be assigned to their respective type
//    *         variables.
//    */
//  def typesSatisfyVariables(typeVarAssigns: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): Boolean = {
//    Validate.notNull(typeVarAssigns, "typeVarAssigns is null")
//    // all types must be assignable to all the bounds of their mapped
//    // type variable.
//    import scala.collection.JavaConversions._
//    for (entry <- typeVarAssigns.entrySet) {
//      val typeVar = entry.getKey
//      val `type` = entry.getValue
//      for (bound <- getImplicitBounds(typeVar)) {
//        if (!isAssignable(`type`, substituteTypeVariables(bound, typeVarAssigns), typeVarAssigns)) return false
//      }
//    }
//    true
//  }
//
//  /**
//    * <p>Transforms the passed in type to a {@link Class} object. Type-checking method of convenience.</p>
//    *
//    * @param parameterizedType the type to be converted
//    * @return the corresponding {@code Class} object
//    * @throws IllegalStateException if the conversion fails
//    */
//  private def getRawType(parameterizedType: ParameterizedType) = {
//    val rawType = parameterizedType.getRawType
//    // check if raw type is a Class object
//    // not currently necessary, but since the return type is Type instead of
//    // Class, there's enough reason to believe that future versions of Java
//    // may return other Type implementations. And type-safety checking is
//    // rarely a bad idea.
//    if (!rawType.isInstanceOf[Class[_]]) throw new IllegalStateException("Wait... What!? Type of rawType: " + rawType)
//    rawType.asInstanceOf[Class[_]]
//  }
//
//  /**
//    * <p>Get the raw type of a Java type, given its context. Primarily for use
//    * with {@link TypeVariable}s and {@link GenericArrayType}s, or when you do
//    * not know the runtime type of {@code type}: if you know you have a
//    * {@link Class} instance, it is already raw; if you know you have a
//    * {@link ParameterizedType}, its raw type is only a method call away.</p>
//    *
//    * @param type          to resolve
//    * @param assigningType type to be resolved against
//    * @return the resolved {@link Class} object or {@code null} if
//    *         the type could not be resolved
//    */
//  def getRawType(`type`: Type, assigningType: Type): Class[_] = {
//    if (`type`.isInstanceOf[Class[_]]) { // it is raw, no problem
//      return `type`.asInstanceOf[Class[_]]
//    }
//    if (`type`.isInstanceOf[ParameterizedType]) { // simple enough to get the raw type of a ParameterizedType
//      return getRawType(`type`.asInstanceOf[ParameterizedType])
//    }
//    if (`type`.isInstanceOf[TypeVariable[_]]) {
//      if (assigningType == null) return null
//      // get the entity declaring this type variable
//      val genericDeclaration = `type`.asInstanceOf[TypeVariable[_]].getGenericDeclaration
//      // can't get the raw type of a method- or constructor-declared type
//      // variable
//      if (!genericDeclaration.isInstanceOf[Class[_]]) return null
//      // get the type arguments for the declaring class/interface based
//      // on the enclosing type
//      val typeVarAssigns = getTypeArguments(assigningType, genericDeclaration.asInstanceOf[Class[_]])
//      // enclosingType has to be a subclass (or subinterface) of the
//      // declaring type
//      if (typeVarAssigns == null) return null
//      // get the argument assigned to this type variable
//      val typeArgument = typeVarAssigns.get(`type`)
//      if (typeArgument == null) return null
//      // get the argument for this type variable
//      return getRawType(typeArgument, assigningType)
//    }
//    if (`type`.isInstanceOf[GenericArrayType]) { // get raw component type
//      val rawComponentType = getRawType(`type`.asInstanceOf[GenericArrayType].getGenericComponentType, assigningType)
//      // create array type from raw component type and return its class
//      return Array.newInstance(rawComponentType, 0).getClass
//    }
//    // (hand-waving) this is not the method you're looking for
//    if (`type`.isInstanceOf[WildcardType]) return null
//    throw new IllegalArgumentException("unknown type: " + `type`)
//  }
//
//  /**
//    * Learn whether the specified type denotes an array type.
//    *
//    * @param type the type to be checked
//    * @return {@code true} if {@code type} is an array class or a {@link GenericArrayType}.
//    */
//  def isArrayType(`type`: Type): Boolean = `type`.isInstanceOf[GenericArrayType] || `type`.isInstanceOf[Class[_]] && `type`.asInstanceOf[Class[_]].isArray
//
//  /**
//    * Gets the array component type of {@code type}.
//    *
//    * @param type the type to be checked
//    * @return component type or null if type is not an array type
//    */
//  def getArrayComponentType(`type`: Type): Type = {
//    if (`type`.isInstanceOf[Class[_]]) {
//      val cls = `type`.asInstanceOf[Class[_]]
//      return if (cls.isArray) cls.getComponentType
//      else null
//    }
//    if (`type`.isInstanceOf[GenericArrayType]) return `type`.asInstanceOf[GenericArrayType].getGenericComponentType
//    null
//  }
//
//  /**
//    * Gets a type representing {@code type} with variable assignments "unrolled."
//    *
//    * @param typeArguments as from {@link TypeUtils# getTypeArguments ( Type, Class)}
//    * @param type          the type to unroll variable assignments for
//    * @return Type
//    * @since 3.2
//    */
//  def unrollVariables(typeArguments: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}, `type`: Type): Type = {
//    if (typeArguments == null) typeArguments = Collections.emptyMap
//    if (containsTypeVariables(`type`)) {
//      if (`type`.isInstanceOf[TypeVariable[_]]) return unrollVariables(typeArguments, typeArguments.get(`type`))
//      if (`type`.isInstanceOf[ParameterizedType]) {
//        val p = `type`.asInstanceOf[ParameterizedType]
//        var parameterizedTypeArguments = null
//        if (p.getOwnerType == null) parameterizedTypeArguments = typeArguments
//        else {
//          parameterizedTypeArguments = new (util.HashMap[TypeVariable[_$1], Type])
//          forSome
//          {
//            type _$1
//          }
//          typeArguments
//          parameterizedTypeArguments.putAll(getTypeArguments(p))
//        }
//        val args = p.getActualTypeArguments
//        for (i <- 0 until args.length) {
//          val unrolled = unrollVariables(parameterizedTypeArguments, args(i))
//          if (unrolled != null) args(i) = unrolled
//        }
//        return parameterizeWithOwner(p.getOwnerType, p.getRawType.asInstanceOf[Class[_]], args)
//      }
//      if (`type`.isInstanceOf[WildcardType]) {
//        val wild = `type`.asInstanceOf[WildcardType]
//        return wildcardType.withUpperBounds(unrollBounds(typeArguments, wild.getUpperBounds)).withLowerBounds(unrollBounds(typeArguments, wild.getLowerBounds)).build
//      }
//    }
//    `type`
//  }
//
//  /**
//    * Local helper method to unroll variables in a type bounds array.
//    *
//    * @param typeArguments assignments {@link Map}
//    * @param bounds        in which to expand variables
//    * @return {@code bounds} with any variables reassigned
//    * @since 3.2
//    */
//  private def unrollBounds(typeArguments: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}, bounds: Array[Type]) = {
//    var result = bounds
//    var i = 0
//
//    while ( {
//      i < result.length
//    }) {
//      val unrolled = unrollVariables(typeArguments, result(i))
//      if (unrolled == null) result = ArrayUtils.remove(result, {
//        i -= 1; i + 1
//      })
//      else result(i) = unrolled
//
//      i += 1
//    }
//    result
//  }
//
//  /**
//    * Learn, recursively, whether any of the type parameters associated with {@code type} are bound to variables.
//    *
//    * @param type the type to check for type variables
//    * @return boolean
//    * @since 3.2
//    */
//  def containsTypeVariables(`type`: Type): Boolean = {
//    if (`type`.isInstanceOf[TypeVariable[_]]) return true
//    if (`type`.isInstanceOf[Class[_]]) return `type`.asInstanceOf[Class[_]].getTypeParameters.length > 0
//    if (`type`.isInstanceOf[ParameterizedType]) {
//      for (arg <- `type`.asInstanceOf[ParameterizedType].getActualTypeArguments) {
//        if (containsTypeVariables(arg)) return true
//      }
//      return false
//    }
//    if (`type`.isInstanceOf[WildcardType]) {
//      val wild = `type`.asInstanceOf[WildcardType]
//      return containsTypeVariables(getImplicitLowerBounds(wild)(0)) || containsTypeVariables(getImplicitUpperBounds(wild)(0))
//    }
//    false
//  }
//
//  /**
//    * Create a parameterized type instance.
//    *
//    * @param rawClass      the raw class to create a parameterized type instance for
//    * @param typeArguments the types used for parameterization
//    * @return {@link ParameterizedType}
//    * @since 3.2
//    */
//  def parameterize(rawClass: Class[_], typeArguments: Type*): ParameterizedType = parameterizeWithOwner(null, rawClass, typeArguments)
//
//  /**
//    * Create a parameterized type instance.
//    *
//    * @param rawClass        the raw class to create a parameterized type instance for
//    * @param typeArgMappings the mapping used for parameterization
//    * @return {@link ParameterizedType}
//    * @since 3.2
//    */
//  def parameterize(rawClass: Class[_], typeArgMappings: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): ParameterizedType = {
//    Validate.notNull(rawClass, "raw class is null")
//    Validate.notNull(typeArgMappings, "typeArgMappings is null")
//    parameterizeWithOwner(null, rawClass, extractTypeArgumentsFrom(typeArgMappings, rawClass.getTypeParameters))
//  }
//
//  /**
//    * Create a parameterized type instance.
//    *
//    * @param owner         the owning type
//    * @param rawClass      the raw class to create a parameterized type instance for
//    * @param typeArguments the types used for parameterization
//    * @return {@link ParameterizedType}
//    * @since 3.2
//    */
//  def parameterizeWithOwner(owner: Type, rawClass: Class[_], typeArguments: Type*): ParameterizedType = {
//    Validate.notNull(rawClass, "raw class is null")
//    var useOwner = null
//    if (rawClass.getEnclosingClass == null) {
//      Validate.isTrue(owner == null, "no owner allowed for top-level %s", rawClass)
//      useOwner = null
//    }
//    else if (owner == null) useOwner = rawClass.getEnclosingClass
//    else {
//      Validate.isTrue(isAssignable(owner, rawClass.getEnclosingClass), "%s is invalid owner type for parameterized %s", owner, rawClass)
//      useOwner = owner
//    }
//    Validate.noNullElements(typeArguments, "null type argument at index %s")
//    Validate.isTrue(rawClass.getTypeParameters.length == typeArguments.length, "invalid number of type parameters specified: expected %d, got %d", rawClass.getTypeParameters.length, typeArguments.length)
//    new TypeUtils.ParameterizedTypeImpl(rawClass, useOwner, typeArguments)
//  }
//
//  /**
//    * Create a parameterized type instance.
//    *
//    * @param owner           the owning type
//    * @param rawClass        the raw class to create a parameterized type instance for
//    * @param typeArgMappings the mapping used for parameterization
//    * @return {@link ParameterizedType}
//    * @since 3.2
//    */
//  def parameterizeWithOwner(owner: Type, rawClass: Class[_], typeArgMappings: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}): ParameterizedType = {
//    Validate.notNull(rawClass, "raw class is null")
//    Validate.notNull(typeArgMappings, "typeArgMappings is null")
//    parameterizeWithOwner(owner, rawClass, extractTypeArgumentsFrom(typeArgMappings, rawClass.getTypeParameters))
//  }
//
//  /**
//    * Helper method to establish the formal parameters for a parameterized type.
//    *
//    * @param mappings  map containing the assignments
//    * @param variables expected map keys
//    * @return array of map values corresponding to specified keys
//    */
//  private def extractTypeArgumentsFrom(mappings: (util.Map[TypeVariable[_$1], Type]) forSome {type _$1}, variables: Array[TypeVariable[_]]) = {
//    val result = new Array[Type](variables.length)
//    var index = 0
//    for (`var` <- variables) {
//      Validate.isTrue(mappings.containsKey(`var`), "missing argument mapping for %s", toString(`var`))
//      result({
//        index += 1; index - 1
//      }) = mappings.get(`var`)
//    }
//    result
//  }
//
//  /**
//    * Gets a {@link WildcardTypeBuilder}.
//    *
//    * @return {@link WildcardTypeBuilder}
//    * @since 3.2
//    */
//  def wildcardType = new TypeUtils.WildcardTypeBuilder
//
//  /**
//    * Create a generic array type instance.
//    *
//    * @param componentType the type of the elements of the array. For example the component type of {@code boolean[]}
//    *                      is {@code boolean}
//    * @return {@link GenericArrayType}
//    * @since 3.2
//    */
//  def genericArrayType(componentType: Type) = new TypeUtils.GenericArrayTypeImpl(Validate.notNull(componentType, "componentType is null"))
//
//  /**
//    * Check equality of types.
//    *
//    * @param type1 the first type
//    * @param type2 the second type
//    * @return boolean
//    * @since 3.2
//    */
//  def equals(type1: Type, type2: Type): Boolean = {
//    if (Objects.equals(type1, type2)) return true
//    if (type1.isInstanceOf[ParameterizedType]) return equals(type1.asInstanceOf[ParameterizedType], type2)
//    if (type1.isInstanceOf[GenericArrayType]) return equals(type1.asInstanceOf[GenericArrayType], type2)
//    if (type1.isInstanceOf[WildcardType]) return equals(type1.asInstanceOf[WildcardType], type2)
//    false
//  }
//
//  /**
//    * Learn whether {@code t} equals {@code p}.
//    *
//    * @param parameterizedType LHS
//    * @param type              RHS
//    * @return boolean
//    * @since 3.2
//    */
//  private def equals(parameterizedType: ParameterizedType, `type`: Type): Boolean = {
//    if (`type`.isInstanceOf[ParameterizedType]) {
//      val other = `type`.asInstanceOf[ParameterizedType]
//      if (equals(parameterizedType.getRawType, other.getRawType) && equals(parameterizedType.getOwnerType, other.getOwnerType)) return equals(parameterizedType.getActualTypeArguments, other.getActualTypeArguments)
//    }
//    false
//  }
//
//  /**
//    * Learn whether {@code t} equals {@code a}.
//    *
//    * @param genericArrayType LHS
//    * @param type             RHS
//    * @return boolean
//    * @since 3.2
//    */
//  private def equals(genericArrayType: GenericArrayType, `type`: Type) = `type`.isInstanceOf[GenericArrayType] && equals(genericArrayType.getGenericComponentType, `type`.asInstanceOf[GenericArrayType].getGenericComponentType)
//
//  /**
//    * Learn whether {@code t} equals {@code w}.
//    *
//    * @param wildcardType LHS
//    * @param type         RHS
//    * @return boolean
//    * @since 3.2
//    */
//  private def equals(wildcardType: WildcardType, `type`: Type): Boolean = {
//    if (`type`.isInstanceOf[WildcardType]) {
//      val other = `type`.asInstanceOf[WildcardType]
//      return equals(getImplicitLowerBounds(wildcardType), getImplicitLowerBounds(other)) && equals(getImplicitUpperBounds(wildcardType), getImplicitUpperBounds(other))
//    }
//    false
//  }
//
//  /**
//    * Learn whether {@code t1} equals {@code t2}.
//    *
//    * @param type1 LHS
//    * @param type2 RHS
//    * @return boolean
//    * @since 3.2
//    */
//  private def equals(type1: Array[Type], type2: Array[Type]): Boolean = {
//    if (type1.length == type2.length) {
//      for (i <- 0 until type1.length) {
//        if (!equals(type1(i), type2(i))) return false
//      }
//      return true
//    }
//    false
//  }
//
//  /**
//    * Present a given type as a Java-esque String.
//    *
//    * @param type the type to create a String representation for, not {@code null}
//    * @return String
//    * @since 3.2
//    */
//  def toString(`type`: Type): String = {
//    Validate.notNull(`type`)
//    if (`type`.isInstanceOf[Class[_]]) return classToString(`type`.asInstanceOf[Class[_]])
//    if (`type`.isInstanceOf[ParameterizedType]) return parameterizedTypeToString(`type`.asInstanceOf[ParameterizedType])
//    if (`type`.isInstanceOf[WildcardType]) return wildcardTypeToString(`type`.asInstanceOf[WildcardType])
//    if (`type`.isInstanceOf[TypeVariable[_]]) return typeVariableToString(`type`.asInstanceOf[TypeVariable[_]])
//    if (`type`.isInstanceOf[GenericArrayType]) return genericArrayTypeToString(`type`.asInstanceOf[GenericArrayType])
//    throw new IllegalArgumentException(ObjectUtils.identityToString(`type`))
//  }
//
//  /**
//    * Format a {@link TypeVariable} including its {@link GenericDeclaration}.
//    *
//    * @param var the type variable to create a String representation for, not {@code null}
//    * @return String
//    * @since 3.2
//    */
//  def toLongString(`var`: TypeVariable[_]): String = {
//    Validate.notNull(`var`, "var is null")
//    val buf = new StringBuilder
//    val d = `var`.getGenericDeclaration
//    if (d.isInstanceOf[Class[_]]) {
//      var c = d.asInstanceOf[Class[_]]
//      while ( {
//        true
//      }) {
//        if (c.getEnclosingClass == null) {
//          buf.insert(0, c.getName)
//          break //todo: break is not supported
//
//        }
//        buf.insert(0, c.getSimpleName).insert(0, '.')
//        c = c.getEnclosingClass
//      }
//    }
//    else if (d.isInstanceOf[Type]) { // not possible as of now
//      buf.append(toString(d.asInstanceOf[Type]))
//    }
//    else buf.append(d)
//    buf.append(':').append(typeVariableToString(`var`)).toString
//  }
//
//  /**
//    * Wrap the specified {@link Type} in a {@link Typed} wrapper.
//    *
//    * @param <    T> inferred generic type
//    * @param type to wrap
//    * @return Typed&lt;T&gt;
//    * @since 3.2
//    */
//  def wrap[T](`type`: Type): Typed[T] = () => `type`
//
//  /**
//    * Wrap the specified {@link Class} in a {@link Typed} wrapper.
//    *
//    * @param <    T> generic type
//    * @param type to wrap
//    * @return Typed&lt;T&gt;
//    * @since 3.2
//    */
//  def wrap[T](`type`: Class[T]): Typed[T] = wrap(`type`.asInstanceOf[Type])
//
//  /**
//    * Format a {@link Class} as a {@link String}.
//    *
//    * @param cls {@code Class} to format
//    * @return String
//    * @since 3.2
//    */
//  private def classToString(cls: Class[_]): String = {
//    if (cls.isArray) return toString(cls.getComponentType) + "[]"
//    val buf = new StringBuilder
//    if (cls.getEnclosingClass != null) buf.append(classToString(cls.getEnclosingClass)).append('.').append(cls.getSimpleName)
//    else buf.append(cls.getName)
//    if (cls.getTypeParameters.length > 0) {
//      buf.append('<')
//      appendAllTo(buf, ", ", cls.getTypeParameters)
//      buf.append('>')
//    }
//    buf.toString
//  }
//
//  /**
//    * Format a {@link TypeVariable} as a {@link String}.
//    *
//    * @param typeVariable {@code TypeVariable} to format
//    * @return String
//    * @since 3.2
//    */
//  private def typeVariableToString(typeVariable: TypeVariable[_]) = {
//    val buf = new StringBuilder(typeVariable.getName)
//    val bounds = typeVariable.getBounds
//    if (bounds.length > 0 && !(bounds.length == 1 && classOf[Any] == bounds(0))) {
//      buf.append(" extends ")
//      appendAllTo(buf, " & ", typeVariable.getBounds)
//    }
//    buf.toString
//  }
//
//  /**
//    * Format a {@link ParameterizedType} as a {@link String}.
//    *
//    * @param parameterizedType {@code ParameterizedType} to format
//    * @return String
//    * @since 3.2
//    */
//  private def parameterizedTypeToString(parameterizedType: ParameterizedType) = {
//    val builder = new StringBuilder
//    val useOwner = parameterizedType.getOwnerType
//    val raw = parameterizedType.getRawType.asInstanceOf[Class[_]]
//    if (useOwner == null) builder.append(raw.getName)
//    else {
//      if (useOwner.isInstanceOf[Class[_]]) builder.append(useOwner.asInstanceOf[Class[_]].getName)
//      else builder.append(useOwner.toString)
//      builder.append('.').append(raw.getSimpleName)
//    }
//    val recursiveTypeIndexes = findRecursiveTypes(parameterizedType)
//    if (recursiveTypeIndexes.length > 0) appendRecursiveTypes(builder, recursiveTypeIndexes, parameterizedType.getActualTypeArguments)
//    else appendAllTo(builder.append('<'), ", ", parameterizedType.getActualTypeArguments).append('>')
//    builder.toString
//  }
//
//  private def appendRecursiveTypes(builder: StringBuilder, recursiveTypeIndexes: Array[Int], argumentTypes: Array[Type]): Unit = {
//    for (i <- 0 until recursiveTypeIndexes.length) {
//      appendAllTo(builder.append('<'), ", ", argumentTypes(i).toString).append('>')
//    }
//    val argumentsFiltered = ArrayUtils.removeAll(argumentTypes, recursiveTypeIndexes)
//    if (argumentsFiltered.length > 0) appendAllTo(builder.append('<'), ", ", argumentsFiltered).append('>')
//  }
//
//  private def findRecursiveTypes(parameterizedType: ParameterizedType) = {
//    val filteredArgumentTypes = util.Arrays.copyOf(parameterizedType.getActualTypeArguments, parameterizedType.getActualTypeArguments.length)
//    var indexesToRemove = Array()
//    for (i <- 0 until filteredArgumentTypes.length) {
//      if (filteredArgumentTypes(i).isInstanceOf[TypeVariable[_]]) if (containsVariableTypeSameParametrizedTypeBound(filteredArgumentTypes(i).asInstanceOf[TypeVariable[_]], parameterizedType)) indexesToRemove = ArrayUtils.add(indexesToRemove, i)
//    }
//    indexesToRemove
//  }
//
//  private def containsVariableTypeSameParametrizedTypeBound(typeVariable: TypeVariable[_], parameterizedType: ParameterizedType) = ArrayUtils.contains(typeVariable.getBounds, parameterizedType)
//
//  /**
//    * Format a {@link WildcardType} as a {@link String}.
//    *
//    * @param wildcardType {@code WildcardType} to format
//    * @return String
//    * @since 3.2
//    */
//  private def wildcardTypeToString(wildcardType: WildcardType) = {
//    val buf = new StringBuilder().append('?')
//    val lowerBounds = wildcardType.getLowerBounds
//    val upperBounds = wildcardType.getUpperBounds
//    if (lowerBounds.length > 1 || lowerBounds.length == 1 && lowerBounds(0) != null) appendAllTo(buf.append(" super "), " & ", lowerBounds)
//    else if (upperBounds.length > 1 || upperBounds.length == 1 && !(classOf[Any] == upperBounds(0))) appendAllTo(buf.append(" extends "), " & ", upperBounds)
//    buf.toString
//  }
//
//  /**
//    * Format a {@link GenericArrayType} as a {@link String}.
//    *
//    * @param genericArrayType {@code GenericArrayType} to format
//    * @return String
//    * @since 3.2
//    */
//  private def genericArrayTypeToString(genericArrayType: GenericArrayType) = String.format("%s[]", toString(genericArrayType.getGenericComponentType))
//
//  /**
//    * Append {@code types} to {@code builder} with separator {@code sep}.
//    *
//    * @param builder destination
//    * @param sep     separator
//    * @param types   to append
//    * @return {@code builder}
//    * @since 3.2
//    */
//  private def appendAllTo[T](builder: StringBuilder, sep: String, @SuppressWarnings(Array("unchecked")) types: T*) = {
//    Validate.notEmpty(Validate.noNullElements(types))
//    if (types.length > 0) {
//      builder.append(toString(types(0)))
//      for (i <- 1 until types.length) {
//        builder.append(sep).append(toString(types(i)))
//      }
//    }
//    builder
//  }
//
//  private def toString[T](`object`: T) = if (`object`.isInstanceOf[Type]) toString(`object`.asInstanceOf[Type])
//  else `object`.toString
//}
