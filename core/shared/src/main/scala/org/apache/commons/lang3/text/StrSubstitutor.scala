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

import java.util
import java.util.Properties
import org.apache.commons.lang3.{void, StringUtils}
import scala.util.control.Breaks

@deprecated object StrSubstitutor {
  /**
    * Constant for the default escape character.
    */
  val DEFAULT_ESCAPE = '$'
  /**
    * Constant for the default variable prefix.
    */
  val DEFAULT_PREFIX: StrMatcher = StrMatcher.stringMatcher("${")
  /**
    * Constant for the default variable suffix.
    */
  val DEFAULT_SUFFIX: StrMatcher = StrMatcher.stringMatcher("}")
  /**
    * Constant for the default value delimiter of a variable.
    *
    * @since 3.2
    */
  val DEFAULT_VALUE_DELIMITER: StrMatcher = StrMatcher.stringMatcher(":-")

  /**
    * Replaces all the occurrences of variables in the given source object with
    * their matching values from the map.
    *
    * @tparam V       the type of the values in the map
    * @param source   the source text containing the variables to substitute, null returns null
    * @param valueMap the map with the values, may be null
    * @return the result of the replace operation
    */
  def replace[V](source: Any, valueMap: util.Map[String, V]): String = new StrSubstitutor(valueMap).replace(source)

  /**
    * Replaces all the occurrences of variables in the given source object with
    * their matching values from the map. This method allows to specify a
    * custom variable prefix and suffix
    *
    * @tparam V       the type of the values in the map
    * @param source   the source text containing the variables to substitute, null returns null
    * @param valueMap the map with the values, may be null
    * @param prefix   the prefix of variables, not null
    * @param suffix   the suffix of variables, not null
    * @return the result of the replace operation
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    */
  def replace[V](source: Any, valueMap: util.Map[String, V], prefix: String, suffix: String): String =
    new StrSubstitutor(valueMap, prefix, suffix).replace(source)

  /**
    * Replaces all the occurrences of variables in the given source object with their matching
    * values from the properties.
    *
    * @param source          the source text containing the variables to substitute, null returns null
    * @param valueProperties the properties with values, may be null
    * @return the result of the replace operation
    */
  def replace(source: Any, valueProperties: Properties): String = {
    if (valueProperties == null) return source.toString
    val valueMap = new util.HashMap[String, String]
    val propNames = valueProperties.propertyNames
    while ({
      propNames.hasMoreElements
    }) {
      val propName = propNames.nextElement.asInstanceOf[String]
      val propValue = valueProperties.getProperty(propName)
      valueMap.put(propName, propValue)
    }
    replace(source, valueMap)
  }

  /**
    * Replaces all the occurrences of variables in the given source object with
    * their matching values from the system properties.
    *
    * @param source the source text containing the variables to substitute, null returns null
    * @return the result of the replace operation
    */
  def replaceSystemProperties(source: Any): String =
    new StrSubstitutor(StrLookup.systemPropertiesLookup).replace(source)
}

// TODO: @link #setValueDelimiter(Char)
// TODO: @link #setValueDelimiter(String)
/**
  * Substitutes variables within a string by values.
  * <p>
  * This class takes a piece of text and substitutes all the variables within it.
  * The default definition of a variable is {@code variableName}.
  * The prefix and suffix can be changed via constructors and set methods.
  * <p>
  * Variable values are typically resolved from a map, but could also be resolved
  * from system properties, or by supplying a custom variable resolver.
  * <p>
  * The simplest example is to use this class to replace Java System properties. For example:
  * <pre>
  * StrSubstitutor.replaceSystemProperties(
  * "You are running with java.version = \${java.version} and os.name = \${os.name}.");
  * </pre>
  * <p>
  * Typical usage of this class follows the following pattern: First an instance is created
  * and initialized with the map that contains the values for the available variables.
  * If a prefix and/or suffix for variables should be used other than the default ones,
  * the appropriate settings can be performed. After that the {@code replace()}
  * method can be called passing in the source text for interpolation. In the returned
  * text all variable references (as long as their values are known) will be resolved.
  * The following example demonstrates this:
  * <pre>
  * Map valuesMap = HashMap();
  * valuesMap.put(&quot;animal&quot;, &quot;quick brown fox&quot;);
  * valuesMap.put(&quot;target&quot;, &quot;lazy dog&quot;);
  * String templateString = &quot;The \${animal} jumps over the \${target}.&quot;;
  * StrSubstitutor sub = new StrSubstitutor(valuesMap);
  * String resolvedString = sub.replace(templateString);
  * </pre>
  * yielding:
  * <pre>
  * The quick brown fox jumps over the lazy dog.
  * </pre>
  * <p>
  * Also, this class allows to set a default value for unresolved variables.
  * The default value for a variable can be appended to the variable name after the variable
  * default value delimiter. The default value of the variable default value delimiter is ':-',
  * as in bash and other *nix shells, as those are arguably where the default \${} delimiter set originated.
  * The variable default value delimiter can be manually set by calling {@link #setValueDelimiterMatcher},
  * {@code #setValueDelimiter(Char)} or {@code #setValueDelimiter(String)}.
  * The following shows an example with variable default value settings:
  * <pre>
  * Map valuesMap = HashMap();
  * valuesMap.put(&quot;animal&quot;, &quot;quick brown fox&quot;);
  * valuesMap.put(&quot;target&quot;, &quot;lazy dog&quot;);
  * String templateString = &quot;The \${animal} jumps over the \${target}. \${undefined.number:-1234567890}.&quot;;
  * StrSubstitutor sub = new StrSubstitutor(valuesMap);
  * String resolvedString = sub.replace(templateString);
  * </pre>
  * yielding:
  * <pre>
  * The quick brown fox jumps over the lazy dog. 1234567890.
  * </pre>
  * <p>
  * In addition to this usage pattern there are some static convenience methods that
  * cover the most common use cases. These methods can be used without the need of
  * manually creating an instance. However if multiple replace operations are to be
  * performed, creating and reusing an instance of this class will be more efficient.
  * <p>
  * Variable replacement works in a recursive way. Thus, if a variable value contains
  * a variable then that variable will also be replaced. Cyclic replacements are
  * detected and will cause an exception to be thrown.
  * <p>
  * Sometimes the interpolation's result must contain a variable prefix. As an example
  * take the following source text:
  * <pre>
  * The variable \${\${name}} must be used.
  * </pre>
  * Here only the variable's name referred to in the text should be replaced resulting
  * in the text (assuming that the value of the {@code name} variable is {@code x}):
  * <pre>
  * The variable \${x} must be used.
  * </pre>
  * To achieve this effect there are two possibilities: Either set a different prefix
  * and suffix for variables which do not conflict with the result text you want to
  * produce. The other possibility is to use the escape character, by default '\$'.
  * If this character is placed before a variable reference, this reference is ignored
  * and won't be replaced. For example:
  * <pre>
  * The variable \$\${\${name}} must be used.
  * </pre>
  * <p>
  * In some complex scenarios you might even want to perform substitution in the
  * names of variables, for instance
  * <pre>
  * \${jre-\${java.specification.version}}
  * </pre>
  * {@code StrSubstitutor} supports this recursive substitution in variable
  * names, but it has to be enabled explicitly by setting the
  * {@link #setEnableSubstitutionInVariables ( boolean ) enableSubstitutionInVariables}
  * property to <b>true</b>.
  * <p>This class is <b>not</b> thread safe.</p>
  *
  * @param variableResolver      the variable resolver, may be null
  * @param prefixMatcher         the prefix for variables, not null
  * @param suffixMatcher         the suffix for variables, not null
  * @param escape                the escape character
  * @param valueDelimiterMatcher the variable default value delimiter matcher, may be null
  * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
  * @since 2.2,3.2
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringSubstitutor.html">
  *             StringSubstitutor</a> instead
  */
@deprecated class StrSubstitutor(
  private var variableResolver: StrLookup[_],
  private var prefixMatcher: StrMatcher,
  private var suffixMatcher: StrMatcher,
  private var escape: Char,
  private var valueDelimiterMatcher: StrMatcher
) {
  /**
    * Our own copy of breaks to avoid conflicts with any other breaks:
    * "Calls to break from one instantiation of Breaks will never target breakable objects of some other instantiation."
    */
  private val breaks: Breaks = new Breaks
  import breaks._

  /**
    * The flag whether substitution in variable names is enabled.
    */
  private var enableSubstitutionInVariables: Boolean = false
  /**
    * Whether escapes should be preserved.  Default is false;
    */
  private var preserveEscapes: Boolean = false

  /**
    * Creates a new instance with defaults for variable prefix and suffix
    * and the escaping character.
    */
  def this() = {
    this(
      null.asInstanceOf[StrLookup[_]],
      StrSubstitutor.DEFAULT_PREFIX,
      StrSubstitutor.DEFAULT_SUFFIX,
      StrSubstitutor.DEFAULT_ESCAPE,
      StrSubstitutor.DEFAULT_VALUE_DELIMITER)
  }

  /**
    * Creates a new instance and initializes it.
    *
    * @param variableResolver the variable resolver, may be null
    * @param prefix           the prefix for variables, not null
    * @param suffix           the suffix for variables, not null
    * @param escape           the escape character
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    */
  def this(variableResolver: StrLookup[_], prefix: String, suffix: String, escape: Char) {
    this()
    this.setVariableResolver(variableResolver)
    this.setVariablePrefix(prefix)
    this.setVariableSuffix(suffix)
    this.setEscapeChar(escape)
    this.setValueDelimiterMatcher(StrSubstitutor.DEFAULT_VALUE_DELIMITER)
  }

  /**
    * Creates a new instance and initializes it.
    *
    * @param variableResolver the variable resolver, may be null
    * @param prefix           the prefix for variables, not null
    * @param suffix           the suffix for variables, not null
    * @param escape           the escape character
    * @param valueDelimiter   the variable default value delimiter string, may be null
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    * @since 3.2
    */
  def this(variableResolver: StrLookup[_], prefix: String, suffix: String, escape: Char, valueDelimiter: String) {
    this()
    this.setVariableResolver(variableResolver)
    this.setVariablePrefix(prefix)
    this.setVariableSuffix(suffix)
    this.setEscapeChar(escape)
    this.setValueDelimiter(valueDelimiter)
  }

  /**
    * Creates a new instance and initializes it.
    *
    */
//  def this(variableResolver: StrLookup[_], prefixMatcher: StrMatcher, suffixMatcher: StrMatcher, escape: Char, valueDelimiterMatcher: StrMatcher) {
//    this()
//    this.setVariableResolver(variableResolver)
//    this.setVariablePrefixMatcher(prefixMatcher)
//    this.setVariableSuffixMatcher(suffixMatcher)
//    this.setEscapeChar(escape)
//    this.setValueDelimiterMatcher(valueDelimiterMatcher)
//  }

  /**
    * Creates a new instance and initializes it. Uses defaults for variable
    * prefix and suffix and the escaping character.
    *
    * @param valueMap the map with the variables' values, may be null
    */
  def this(valueMap: util.Map[String, _]) {
    this(
      StrLookup.mapLookup(valueMap),
      StrSubstitutor.DEFAULT_PREFIX,
      StrSubstitutor.DEFAULT_SUFFIX,
      StrSubstitutor.DEFAULT_ESCAPE,
      StrSubstitutor.DEFAULT_VALUE_DELIMITER)
  }

  /**
    * Creates a new instance and initializes it. Uses a default escaping character.
    *
    * @param valueMap the map with the variables' values, may be null
    * @param prefix   the prefix for variables, not null
    * @param suffix   the suffix for variables, not null
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    */
  def this(valueMap: util.Map[String, _], prefix: String, suffix: String) {
    this(StrLookup.mapLookup(valueMap), prefix, suffix, StrSubstitutor.DEFAULT_ESCAPE)
  }

  /**
    * Creates a new instance and initializes it.
    *
    * @param valueMap the map with the variables' values, may be null
    * @param prefix   the prefix for variables, not null
    * @param suffix   the suffix for variables, not null
    * @param escape   the escape character
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    */
  def this(valueMap: util.Map[String, _], prefix: String, suffix: String, escape: Char) {
    this(StrLookup.mapLookup(valueMap), prefix, suffix, escape)
  }

  /**
    * Creates a new instance and initializes it.
    *
    * @param variableResolver the variable resolver, may be null
    * @param prefixMatcher    the prefix for variables, not null
    * @param suffixMatcher    the suffix for variables, not null
    * @param escape           the escape character
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    */
  def this(variableResolver: StrLookup[_], prefixMatcher: StrMatcher, suffixMatcher: StrMatcher, escape: Char) {
    this(variableResolver, prefixMatcher, suffixMatcher, escape, StrSubstitutor.DEFAULT_VALUE_DELIMITER)
  }

  /**
    * Creates a new instance and initializes it.
    *
    * @param valueMap       the map with the variables' values, may be null
    * @param prefix         the prefix for variables, not null
    * @param suffix         the suffix for variables, not null
    * @param escape         the escape character
    * @param valueDelimiter the variable default value delimiter, may be null
    * @throws java.lang.IllegalArgumentException if the prefix or suffix is null
    * @since 3.2
    */
  def this(valueMap: util.Map[String, _], prefix: String, suffix: String, escape: Char, valueDelimiter: String) {
    this(StrLookup.mapLookup(valueMap), prefix, suffix, escape, valueDelimiter)
  }

  /**
    * Creates a new instance and initializes it.
    *
    * @param variableResolver the variable resolver, may be null
    */
  def this(variableResolver: StrLookup[_]) {
    this(variableResolver, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, StrSubstitutor.DEFAULT_ESCAPE)
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source string as a template.
    *
    * @param source the string to replace in, null returns null
    * @return the result of the replace operation
    */
  def replace(source: String): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(source)

    if (substitute(buf, 0, source.length) == false) return source
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source string as a template.
    * <p>
    * Only the specified portion of the string will be processed.
    * The rest of the string is not processed, and is not returned.
    *
    * @param source the string to replace in, null returns null
    * @param offset the start offset within the array, must be valid
    * @param length the length within the array to be processed, must be valid
    * @return the result of the replace operation
    */
  def replace(source: String, offset: Int, length: Int): String = {
    if (source == null) return null

    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    if (substitute(buf, 0, length) == false) source.substring(offset, offset + length)
    else buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source array as a template.
    * The array is not altered by this method.
    *
    * @param source the character array to replace in, not altered, null returns null
    * @return the result of the replace operation
    */
  def replace(source: Array[Char]): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(source.length).append(source)
    substitute(buf, 0, source.length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source array as a template.
    * The array is not altered by this method.
    * <p>
    * Only the specified portion of the array will be processed.
    * The rest of the array is not processed, and is not returned.
    *
    * @param source the character array to replace in, not altered, null returns null
    * @param offset the start offset within the array, must be valid
    * @param length the length within the array to be processed, must be valid
    * @return the result of the replace operation
    */
  def replace(source: Array[Char], offset: Int, length: Int): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    substitute(buf, 0, length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source buffer as a template.
    * The buffer is not altered by this method.
    *
    * @param source the buffer to use as a template, not changed, null returns null
    * @return the result of the replace operation
    */
  def replace(source: StringBuffer): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(source.length).append(source)
    substitute(buf, 0, buf.length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source buffer as a template.
    * The buffer is not altered by this method.
    * <p>
    * Only the specified portion of the buffer will be processed.
    * The rest of the buffer is not processed, and is not returned.
    *
    * @param source the buffer to use as a template, not changed, null returns null
    * @param offset the start offset within the array, must be valid
    * @param length the length within the array to be processed, must be valid
    * @return the result of the replace operation
    */
  def replace(source: StringBuffer, offset: Int, length: Int): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    substitute(buf, 0, length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source as a template.
    * The source is not altered by this method.
    *
    * @param source the buffer to use as a template, not changed, null returns null
    * @return the result of the replace operation
    * @since 3.2
    */
  def replace(source: CharSequence): String = {
    if (source == null) return null
    replace(source, 0, source.length)
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source as a template.
    * The source is not altered by this method.
    * <p>
    * Only the specified portion of the buffer will be processed.
    * The rest of the buffer is not processed, and is not returned.
    *
    * @param source the buffer to use as a template, not changed, null returns null
    * @param offset the start offset within the array, must be valid
    * @param length the length within the array to be processed, must be valid
    * @return the result of the replace operation
    * @since 3.2
    */
  def replace(source: CharSequence, offset: Int, length: Int): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    substitute(buf, 0, length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source builder as a template.
    * The builder is not altered by this method.
    *
    * @param source the builder to use as a template, not changed, null returns null
    * @return the result of the replace operation
    */
  def replace(source: StrBuilder): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(source.length).append(source)
    substitute(buf, 0, buf.length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables with their matching values
    * from the resolver using the given source builder as a template.
    * The builder is not altered by this method.
    * <p>
    * Only the specified portion of the builder will be processed.
    * The rest of the builder is not processed, and is not returned.
    *
    * @param source the builder to use as a template, not changed, null returns null
    * @param offset the start offset within the array, must be valid
    * @param length the length within the array to be processed, must be valid
    * @return the result of the replace operation
    */
  def replace(source: StrBuilder, offset: Int, length: Int): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    substitute(buf, 0, length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables in the given source object with
    * their matching values from the resolver. The input source object is
    * converted to a string using {@code toString} and is not altered.
    *
    * @param source the source to replace in, null returns null
    * @return the result of the replace operation
    */
  def replace(source: Any): String = {
    if (source == null) return null
    val buf: StrBuilder = new StrBuilder().append(source)
    substitute(buf, 0, buf.length)
    buf.toString
  }

  /**
    * Replaces all the occurrences of variables within the given source buffer
    * with their matching values from the resolver.
    * The buffer is updated with the result.
    *
    * @param source the buffer to replace in, updated, null returns zero
    * @return true if altered
    */
  def replaceIn(source: StringBuffer): Boolean = {
    if (source == null) return false
    replaceIn(source, 0, source.length)
  }

  /**
    * Replaces all the occurrences of variables within the given source buffer
    * with their matching values from the resolver.
    * The buffer is updated with the result.
    * <p>
    * Only the specified portion of the buffer will be processed.
    * The rest of the buffer is not processed, but it is not deleted.
    *
    * @param source the buffer to replace in, updated, null returns zero
    * @param offset the start offset within the array, must be valid
    * @param length the length within the buffer to be processed, must be valid
    * @return true if altered
    */
  def replaceIn(source: StringBuffer, offset: Int, length: Int): Boolean = {
    if (source == null) return false
    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    if (substitute(buf, 0, length) == false) return false
    source.replace(offset, offset + length, buf.toString)
    true
  }

  /**
    * Replaces all the occurrences of variables within the given source buffer
    * with their matching values from the resolver.
    * The buffer is updated with the result.
    *
    * @param source the buffer to replace in, updated, null returns zero
    * @return true if altered
    * @since 3.2
    */
  def replaceIn(source: StringBuilder): Boolean = {
    if (source == null) return false
    replaceIn(source, 0, source.length)
  }

  /**
    * Replaces all the occurrences of variables within the given source builder
    * with their matching values from the resolver.
    * The builder is updated with the result.
    * <p>
    * Only the specified portion of the buffer will be processed.
    * The rest of the buffer is not processed, but it is not deleted.
    *
    * @param source the buffer to replace in, updated, null returns zero
    * @param offset the start offset within the array, must be valid
    * @param length the length within the buffer to be processed, must be valid
    * @return true if altered
    * @since 3.2
    */
  def replaceIn(source: StringBuilder, offset: Int, length: Int): Boolean = {
    if (source == null) return false
    val buf: StrBuilder = new StrBuilder(length).append(source, offset, length)
    if (substitute(buf, 0, length) == false) return false
    source.replace(offset, offset + length, buf.toString)
    true
  }

  /**
    * Replaces all the occurrences of variables within the given source
    * builder with their matching values from the resolver.
    *
    * @param source the builder to replace in, updated, null returns zero
    * @return true if altered
    */
  def replaceIn(source: StrBuilder): Boolean = {
    if (source == null) return false
    substitute(source, 0, source.length)
  }

  /**
    * Replaces all the occurrences of variables within the given source
    * builder with their matching values from the resolver.
    * <p>
    * Only the specified portion of the builder will be processed.
    * The rest of the builder is not processed, but it is not deleted.
    *
    * @param source the builder to replace in, null returns zero
    * @param offset the start offset within the array, must be valid
    * @param length the length within the builder to be processed, must be valid
    * @return true if altered
    */
  def replaceIn(source: StrBuilder, offset: Int, length: Int): Boolean = {
    if (source == null) return false
    substitute(source, offset, length)
  }

  /**
    * Internal method that substitutes the variables.
    * <p>
    * Most users of this class do not need to call this method. This method will
    * be called automatically by another (public) method.
    * <p>
    * Writers of subclasses can override this method if they need access to
    * the substitution process at the start or end.
    *
    * @param buf    the string builder to substitute into, not null
    * @param offset the start offset within the builder, must be valid
    * @param length the length within the builder to be processed, must be valid
    * @return true if altered
    */
  protected def substitute(buf: StrBuilder, offset: Int, length: Int): Boolean = {
    substitute(buf, offset, length, null) > 0
  }

  /**
    * Recursive handler for multiple levels of interpolation. This is the main
    * interpolation method, which resolves the values of all variable references
    * contained in the passed in text.
    *
    * @param buf            the string builder to substitute into, not null
    * @param offset         the start offset within the builder, must be valid
    * @param length         the length within the builder to be processed, must be valid
    * @param priorVariables the stack keeping track of the replaced variables, may be null
    * @return the length change that occurs, unless priorVariables is null when the int
    *         represents a boolean flag as to whether any change occurred.
    */
  private def substitute(buf: StrBuilder, offset: Int, length: Int, priorVariables: util.List[String]): Int = {
    var _priorVariables: util.List[String] = priorVariables

    val pfxMatcher: StrMatcher = getVariablePrefixMatcher
    val suffMatcher: StrMatcher = getVariableSuffixMatcher
    val escape: Char = getEscapeChar
    val valueDelimMatcher: StrMatcher = getValueDelimiterMatcher
    val substitutionInVariablesEnabled: Boolean = isEnableSubstitutionInVariables
    val top: Boolean = _priorVariables == null
    var altered: Boolean = false
    var lengthChange: Int = 0
    var chars: Array[Char] = buf.buffer
    var bufEnd: Int = offset + length
    var pos: Int = offset

    while (pos < bufEnd) {
      val startMatchLen: Int = pfxMatcher.isMatch(chars, pos, offset, bufEnd)

      if (startMatchLen == 0) {
        pos += 1
      } else {
        // found variable start marker
        if (pos > offset && chars(pos - 1) == escape) {
          // escaped
          if (preserveEscapes) {
            pos += 1
          } else {
            buf.deleteCharAt(pos - 1)
            chars = buf.buffer // in case buffer was altered

            lengthChange -= 1
            altered = true
            bufEnd -= 1
          }
        } else { // find suffix
          val startPos: Int = pos
          pos += startMatchLen
          var endMatchLen: Int = 0
          var nestedVarCount: Int = 0

          breakable {
            while (pos < bufEnd) {
              if (substitutionInVariablesEnabled && {
                  endMatchLen = pfxMatcher.isMatch(chars, pos, offset, bufEnd)
                  endMatchLen != 0
                }) {
                // found a nested variable start
                nestedVarCount += 1
                pos += endMatchLen
              } else {
                endMatchLen = suffMatcher.isMatch(chars, pos, offset, bufEnd)
                if (endMatchLen == 0) {
                  pos += 1
                } else {
                  // found variable end marker
                  if (nestedVarCount == 0) {
                    var varNameExpr: String =
                      new String(chars, startPos + startMatchLen, pos - startPos - startMatchLen)
                    if (substitutionInVariablesEnabled) {
                      val bufName: StrBuilder = new StrBuilder(varNameExpr)
                      substitute(bufName, 0, bufName.length)
                      varNameExpr = bufName.toString
                    }
                    pos += endMatchLen
                    val endPos: Int = pos
                    var varName: String = varNameExpr
                    var varDefaultValue: String = null
                    if (valueDelimMatcher != null) {
                      val varNameExprChars: Array[Char] = varNameExpr.toCharArray
                      var valueDelimiterMatchLen: Int = 0
                      for (i <- 0 until varNameExprChars.length) { // if there's any nested variable when nested variable substitution disabled, then stop resolving name and default value.
                        if (!substitutionInVariablesEnabled && pfxMatcher.isMatch(
                            varNameExprChars,
                            i,
                            i,
                            varNameExprChars.length) != 0) {
                          break()
                        }

                        if ({
                          valueDelimiterMatchLen = valueDelimMatcher.isMatch(varNameExprChars, i)
                          valueDelimiterMatchLen != 0
                        }) {
                          varName = varNameExpr.substring(0, i)
                          varDefaultValue = varNameExpr.substring(i + valueDelimiterMatchLen)
                          break()

                        }
                      }
                    }
                    // on the first call initialize priorVariables
                    if (_priorVariables == null) {
                      _priorVariables = new util.ArrayList[String]
                      _priorVariables.add(new String(chars, offset, length))
                    }
                    // handle cyclic substitution
                    checkCyclicSubstitution(varName, _priorVariables)
                    _priorVariables.add(varName)
                    // resolve the variable
                    var varValue: String = resolveVariable(varName, buf, startPos, endPos)
                    if (varValue == null) {
                      varValue = varDefaultValue
                    }
                    if (varValue != null) { // recursive replace
                      val varLen: Int = varValue.length
                      buf.replace(startPos, endPos, varValue)
                      altered = true
                      var change: Int = substitute(buf, startPos, varLen, _priorVariables)
                      change = change + varLen - (endPos - startPos)
                      pos += change
                      bufEnd += change
                      lengthChange += change
                      chars = buf.buffer // in case buffer was

                      // altered
                    }
                    // remove variable from the cyclic stack
                    _priorVariables.remove(_priorVariables.size - 1)
                    break()

                  }
                  nestedVarCount -= 1
                  pos += endMatchLen
                }
              }
            }
          }
        }
      }
    } // while loop

    if (top) {
      return {
        if (altered) 1
        else 0
      }
    }

    lengthChange
  }

  /**
    * Checks if the specified variable is already in the stack (list) of variables.
    *
    * @param varName        the variable name to check
    * @param priorVariables the list of prior variables
    */
  private def checkCyclicSubstitution(varName: String, priorVariables: util.List[String]): Unit = {
    if (priorVariables.contains(varName) == false) return

    val buf: StrBuilder = new StrBuilder(256)
    buf.append("Infinite loop in property interpolation of ")
    buf.append(priorVariables.remove(0))
    buf.append(": ")
    buf.appendWithSeparators(priorVariables, "->")
    throw new IllegalStateException(buf.toString)
  }

  /**
    * Internal method that resolves the value of a variable.
    * <p>
    * Most users of this class do not need to call this method. This method is
    * called automatically by the substitution process.
    * <p>
    * Writers of subclasses can override this method if they need to alter
    * how each substitution occurs. The method is passed the variable's name
    * and must return the corresponding value. This implementation uses the
    * {@link #getVariableResolver ( )} with the variable's name as the key.
    *
    * @param variableName the name of the variable, not null
    * @param buf          the buffer where the substitution is occurring, not null
    * @param startPos     the start position of the variable including the prefix, valid
    * @param endPos       the end position of the variable including the suffix, valid
    * @return the variable's value or <b>null</b> if the variable is unknown
    */
  protected def resolveVariable(variableName: String, buf: StrBuilder, startPos: Int, endPos: Int): String = {
    void(buf)
    void(startPos)
    void(endPos)
    val resolver: StrLookup[_] = getVariableResolver
    if (resolver == null) return null
    resolver.lookup(variableName)
  }

  /**
    * Returns the escape character.
    *
    * @return the character used for escaping variable references
    */
  def getEscapeChar: Char = this.escape

  /**
    * Sets the escape character.
    * If this character is placed before a variable reference in the source
    * text, this variable will be ignored.
    *
    * @param escapeCharacter the escape character (0 for disabling escaping)
    */
  def setEscapeChar(escapeCharacter: Char): Unit = {
    this.escape = escapeCharacter
  }

  /**
    * Gets the variable prefix matcher currently in use.
    * <p>
    * The variable prefix is the character or characters that identify the
    * start of a variable. This prefix is expressed in terms of a matcher
    * allowing advanced prefix matches.
    *
    * @return the prefix matcher in use
    */
  def getVariablePrefixMatcher: StrMatcher = prefixMatcher

  /**
    * Sets the variable prefix matcher currently in use.
    * <p>
    * The variable prefix is the character or characters that identify the
    * start of a variable. This prefix is expressed in terms of a matcher
    * allowing advanced prefix matches.
    *
    * @param prefixMatcher the prefix matcher to use, null ignored
    * @return this, to enable chaining
    * @throws java.lang.IllegalArgumentException if the prefix matcher is null
    */
  def setVariablePrefixMatcher(prefixMatcher: StrMatcher): StrSubstitutor = {
    if (prefixMatcher == null) throw new IllegalArgumentException("Variable prefix matcher must not be null.")
    this.prefixMatcher = prefixMatcher
    this
  }

  /**
    * Sets the variable prefix to use.
    * <p>
    * The variable prefix is the character or characters that identify the
    * start of a variable. This method allows a single character prefix to
    * be easily set.
    *
    * @param prefix the prefix character to use
    * @return this, to enable chaining
    */
  def setVariablePrefix(prefix: Char): StrSubstitutor =
    setVariablePrefixMatcher(StrMatcher.charMatcher(prefix))

  /**
    * Sets the variable prefix to use.
    * <p>
    * The variable prefix is the character or characters that identify the
    * start of a variable. This method allows a string prefix to be easily set.
    *
    * @param prefix the prefix for variables, not null
    * @return this, to enable chaining
    * @throws java.lang.IllegalArgumentException if the prefix is null
    */
  def setVariablePrefix(prefix: String): StrSubstitutor = {
    if (prefix == null) throw new IllegalArgumentException("Variable prefix must not be null.")
    setVariablePrefixMatcher(StrMatcher.stringMatcher(prefix))
  }

  /**
    * Gets the variable suffix matcher currently in use.
    * <p>
    * The variable suffix is the character or characters that identify the
    * end of a variable. This suffix is expressed in terms of a matcher
    * allowing advanced suffix matches.
    *
    * @return the suffix matcher in use
    */
  def getVariableSuffixMatcher: StrMatcher = suffixMatcher

  /**
    * Sets the variable suffix matcher currently in use.
    * <p>
    * The variable suffix is the character or characters that identify the
    * end of a variable. This suffix is expressed in terms of a matcher
    * allowing advanced suffix matches.
    *
    * @param suffixMatcher the suffix matcher to use, null ignored
    * @return this, to enable chaining
    * @throws java.lang.IllegalArgumentException if the suffix matcher is null
    */
  def setVariableSuffixMatcher(suffixMatcher: StrMatcher): StrSubstitutor = {
    if (suffixMatcher == null) throw new IllegalArgumentException("Variable suffix matcher must not be null.")
    this.suffixMatcher = suffixMatcher
    this
  }

  /**
    * Sets the variable suffix to use.
    * <p>
    * The variable suffix is the character or characters that identify the
    * end of a variable. This method allows a single character suffix to
    * be easily set.
    *
    * @param suffix the suffix character to use
    * @return this, to enable chaining
    */
  def setVariableSuffix(suffix: Char): StrSubstitutor = {
    setVariableSuffixMatcher(StrMatcher.charMatcher(suffix))
  }

  /**
    * Sets the variable suffix to use.
    * <p>
    * The variable suffix is the character or characters that identify the
    * end of a variable. This method allows a string suffix to be easily set.
    *
    * @param suffix the suffix for variables, not null
    * @return this, to enable chaining
    * @throws java.lang.IllegalArgumentException if the suffix is null
    */
  def setVariableSuffix(suffix: String): StrSubstitutor = {
    if (suffix == null) throw new IllegalArgumentException("Variable suffix must not be null.")
    setVariableSuffixMatcher(StrMatcher.stringMatcher(suffix))
  }

  /**
    * Gets the variable default value delimiter matcher currently in use.
    * <p>
    * The variable default value delimiter is the character or characters that delimit the
    * variable name and the variable default value. This delimiter is expressed in terms of a matcher
    * allowing advanced variable default value delimiter matches.
    * <p>
    * If it returns null, then the variable default value resolution is disabled.
    *
    * @return the variable default value delimiter matcher in use, may be null
    * @since 3.2
    */
  def getValueDelimiterMatcher: StrMatcher = valueDelimiterMatcher

  /**
    * Sets the variable default value delimiter matcher to use.
    * <p>
    * The variable default value delimiter is the character or characters that delimit the
    * variable name and the variable default value. This delimiter is expressed in terms of a matcher
    * allowing advanced variable default value delimiter matches.
    * <p>
    * If the {@code valueDelimiterMatcher} is null, then the variable default value resolution
    * becomes disabled.
    *
    * @param valueDelimiterMatcher variable default value delimiter matcher to use, may be null
    * @return this, to enable chaining
    * @since 3.2
    */
  def setValueDelimiterMatcher(valueDelimiterMatcher: StrMatcher): StrSubstitutor = {
    this.valueDelimiterMatcher = valueDelimiterMatcher
    this
  }

  /**
    * Sets the variable default value delimiter to use.
    * <p>
    * The variable default value delimiter is the character or characters that delimit the
    * variable name and the variable default value. This method allows a single character
    * variable default value delimiter to be easily set.
    *
    * @param valueDelimiter the variable default value delimiter character to use
    * @return this, to enable chaining
    * @since 3.2
    */
  def setValueDelimiter(valueDelimiter: Char): StrSubstitutor = {
    setValueDelimiterMatcher(StrMatcher.charMatcher(valueDelimiter))
  }

  /**
    * Sets the variable default value delimiter to use.
    * <p>
    * The variable default value delimiter is the character or characters that delimit the
    * variable name and the variable default value. This method allows a string
    * variable default value delimiter to be easily set.
    * <p>
    * If the {@code valueDelimiter} is null or empty string, then the variable default
    * value resolution becomes disabled.
    *
    * @param valueDelimiter the variable default value delimiter string to use, may be null or empty
    * @return this, to enable chaining
    * @since 3.2
    */
  def setValueDelimiter(valueDelimiter: String): StrSubstitutor = {
    if (StringUtils.isEmpty(valueDelimiter)) {
      setValueDelimiterMatcher(null)
      return this
    }

    setValueDelimiterMatcher(StrMatcher.stringMatcher(valueDelimiter))
  }

  /**
    * Gets the VariableResolver that is used to lookup variables.
    *
    * @return the VariableResolver
    */
  def getVariableResolver: StrLookup[_] = this.variableResolver

  /**
    * Sets the VariableResolver that is used to lookup variables.
    *
    * @param variableResolver the VariableResolver
    */
  def setVariableResolver(variableResolver: StrLookup[_]): Unit = {
    this.variableResolver = variableResolver
  }

  /**
    * Returns a flag whether substitution is done in variable names.
    *
    * @return the substitution in variable names flag
    * @since 3.0
    */
  def isEnableSubstitutionInVariables: Boolean = enableSubstitutionInVariables

  /**
    * Sets a flag whether substitution is done in variable names. If set to
    * <b>true</b>, the names of variables can contain other variables which are
    * processed first before the original variable is evaluated, e.g.
    * {@code {jre-{java.version}}}. The default value is <b>false</b>.
    *
    * @param enableSubstitutionInVariables the new value of the flag
    * @since 3.0
    */
  def setEnableSubstitutionInVariables(enableSubstitutionInVariables: Boolean): Unit = {
    this.enableSubstitutionInVariables = enableSubstitutionInVariables
  }

  /**
    * Returns the flag controlling whether escapes are preserved during
    * substitution.
    *
    * @return the preserve escape flag
    * @since 3.5
    */
  def isPreserveEscapes: Boolean = preserveEscapes

  /**
    * Sets a flag controlling whether escapes are preserved during
    * substitution.  If set to <b>true</b>, the escape character is retained
    * during substitution (e.g. {@code {this-is-escaped}} remains
    * {@code {this-is-escaped}}).  If set to <b>false</b>, the escape
    * character is removed during substitution (e.g.
    * {@code {this-is-escaped}} becomes
    * {@code {this-is-escaped}}).  The default value is <b>false</b>
    *
    * @param preserveEscapes true if escapes are to be preserved
    * @since 3.5
    */
  def setPreserveEscapes(preserveEscapes: Boolean): Unit = {
    this.preserveEscapes = preserveEscapes
  }
}
