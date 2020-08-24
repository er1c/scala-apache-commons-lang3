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

package org.apache.commons.lang3

import java.lang.{Boolean => JavaBoolean}
import java.io.File
import JavaVersion.JavaVersion

/**
  * <p>
  * Helpers for {@code java.lang.System}.
  * </p>
  * <p>
  * If a system property cannot be read due to security restrictions, the corresponding field in this class will be set
  * to {@code null} and a message will be written to {@code System.err}.
  * </p>
  * <p>
  * #ThreadSafe#
  * </p>
  *
  * @since 1.0
  */
object SystemUtils {
  /**
    * The prefix String for all Windows OS.
    */
  private val OS_NAME_WINDOWS_PREFIX = "Windows"
  /**
    * The System property key for the user home directory.
    */
  private val USER_HOME_KEY = "user.home"
  /**
    * The System property key for the user name.
    */
  private val USER_NAME_KEY = "user.name"
  /**
    * The System property key for the user directory.
    */
  private val USER_DIR_KEY = "user.dir"
  /**
    * The System property key for the Java IO temporary directory.
    */
  private val JAVA_IO_TMPDIR_KEY = "java.io.tmpdir"
  /**
    * The System property key for the Java home directory.
    */
  private val JAVA_HOME_KEY = "java.home"
  /**
    * <p>
    * The {@code awt.toolkit} System Property.
    * </p>
    * <p>
    * Holds a class name, on Windows XP this is {@code sun.awt.windows.WToolkit}.
    * </p>
    * <p>
    * <b>On platforms without a GUI, this value is {@code null}.</b>
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.1
    */
  val AWT_TOOLKIT: String = getSystemProperty("awt.toolkit")
  /**
    * <p>
    * The {@code file.encoding} System Property.
    * </p>
    * <p>
    * File encoding, such as {@code Cp1252}.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.0
    *        Java 1.2
    */
  val FILE_ENCODING: String = getSystemProperty("file.encoding")
  /**
    * <p>
    * The {@code file.separator} System Property.
    * The file separator is:
    * </p>
    * <ul>
    * <li>{@code "/"} on UNIX</li>
    * <li>{@code "\"} on Windows.</li>
    * </ul>
    *
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @deprecated Use {@link java.io.File#separator}, since it is guaranteed to be a
    *             string containing a single character and it does not require a privilege check.
    * @since Java 1.1
    */
  @deprecated val FILE_SEPARATOR: String = getSystemProperty("file.separator")
  /**
    * <p>
    * The {@code java.awt.fonts} System Property.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.1
    */
  val JAVA_AWT_FONTS: String = getSystemProperty("java.awt.fonts")
  /**
    * <p>
    * The {@code java.awt.graphicsenv} System Property.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.1
    */
  val JAVA_AWT_GRAPHICSENV: String = getSystemProperty("java.awt.graphicsenv")
  /**
    * <p>
    * The {@code java.awt.headless} System Property. The value of this property is the String {@code "true"} or
    * {@code "false"}.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @see #isJavaAwtHeadless()
    * @since 2.1
    *        Java 1.4
    */
  val JAVA_AWT_HEADLESS: String = getSystemProperty("java.awt.headless")
  /**
    * <p>
    * The {@code java.awt.printerjob} System Property.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.1
    */
  val JAVA_AWT_PRINTERJOB: String = getSystemProperty("java.awt.printerjob")
  /**
    * <p>
    * The {@code java.class.path} System Property. Java class path.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val JAVA_CLASS_PATH: String = getSystemProperty("java.class.path")
  /**
    * <p>
    * The {@code java.class.version} System Property. Java class format version number.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val JAVA_CLASS_VERSION: String = getSystemProperty("java.class.version")
  /**
    * <p>
    * The {@code java.compiler} System Property. Name of JIT compiler to use. First in JDK version 1.2. Not used in Sun
    * JDKs after 1.2.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2. Not used in Sun versions after 1.2.
    */
  val JAVA_COMPILER: String = getSystemProperty("java.compiler")
  /**
    * <p>
    * The {@code java.endorsed.dirs} System Property. Path of endorsed directory or directories.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.4
    */
  val JAVA_ENDORSED_DIRS: String = getSystemProperty("java.endorsed.dirs")
  /**
    * <p>
    * The {@code java.ext.dirs} System Property. Path of extension directory or directories.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.3
    */
  val JAVA_EXT_DIRS: String = getSystemProperty("java.ext.dirs")
  /**
    * <p>
    * The {@code java.home} System Property. Java installation directory.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val JAVA_HOME: String = getSystemProperty(JAVA_HOME_KEY)
  /**
    * <p>
    * The {@code java.io.tmpdir} System Property. Default temp file path.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_IO_TMPDIR: String = getSystemProperty(JAVA_IO_TMPDIR_KEY)
  /**
    * <p>
    * The {@code java.library.path} System Property. List of paths to search when loading libraries.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_LIBRARY_PATH: String = getSystemProperty("java.library.path")
  /**
    * <p>
    * The {@code java.runtime.name} System Property. Java Runtime Environment name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.0
    *        Java 1.3
    */
  val JAVA_RUNTIME_NAME: String = getSystemProperty("java.runtime.name")
  /**
    * <p>
    * The {@code java.runtime.version} System Property. Java Runtime Environment version.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.0
    *        Java 1.3
    */
  val JAVA_RUNTIME_VERSION: String = getSystemProperty("java.runtime.version")
  /**
    * <p>
    * The {@code java.specification.name} System Property. Java Runtime Environment specification name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_SPECIFICATION_NAME: String = getSystemProperty("java.specification.name")
  /**
    * <p>
    * The {@code java.specification.vendor} System Property. Java Runtime Environment specification vendor.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_SPECIFICATION_VENDOR: String = getSystemProperty("java.specification.vendor")
  /**
    * <p>
    * The {@code java.specification.version} System Property. Java Runtime Environment specification version.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.3
    */
  val JAVA_SPECIFICATION_VERSION: String = getSystemProperty("java.specification.version")
  private val JAVA_SPECIFICATION_VERSION_AS_ENUM = JavaVersion.get(JAVA_SPECIFICATION_VERSION)
  /**
    * <p>
    * The {@code java.util.prefs.PreferencesFactory} System Property. A class name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.1
    *        Java 1.4
    */
  val JAVA_UTIL_PREFS_PREFERENCES_FACTORY: String = getSystemProperty("java.util.prefs.PreferencesFactory")
  /**
    * <p>
    * The {@code java.vendor} System Property. Java vendor-specific string.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val JAVA_VENDOR: String = getSystemProperty("java.vendor")
  /**
    * <p>
    * The {@code java.vendor.url} System Property. Java vendor URL.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val JAVA_VENDOR_URL: String = getSystemProperty("java.vendor.url")
  /**
    * <p>
    * The {@code java.version} System Property. Java version number.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val JAVA_VERSION: String = getSystemProperty("java.version")
  /**
    * <p>
    * The {@code java.vm.info} System Property. Java Virtual Machine implementation info.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.0
    *        Java 1.2
    */
  val JAVA_VM_INFO: String = getSystemProperty("java.vm.info")
  /**
    * <p>
    * The {@code java.vm.name} System Property. Java Virtual Machine implementation name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_VM_NAME: String = getSystemProperty("java.vm.name")
  /**
    * <p>
    * The {@code java.vm.specification.name} System Property. Java Virtual Machine specification name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_VM_SPECIFICATION_NAME: String = getSystemProperty("java.vm.specification.name")
  /**
    * <p>
    * The {@code java.vm.specification.vendor} System Property. Java Virtual Machine specification vendor.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_VM_SPECIFICATION_VENDOR: String = getSystemProperty("java.vm.specification.vendor")
  /**
    * <p>
    * The {@code java.vm.specification.version} System Property. Java Virtual Machine specification version.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_VM_SPECIFICATION_VERSION: String = getSystemProperty("java.vm.specification.version")
  /**
    * <p>
    * The {@code java.vm.vendor} System Property. Java Virtual Machine implementation vendor.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_VM_VENDOR: String = getSystemProperty("java.vm.vendor")
  /**
    * <p>
    * The {@code java.vm.version} System Property. Java Virtual Machine implementation version.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.2
    */
  val JAVA_VM_VERSION: String = getSystemProperty("java.vm.version")
  /**
    * <p>
    * The {@code line.separator} System Property. Line separator ({@code &quot;\n&quot;} on UNIX).
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @deprecated Use {@link java.lang.System#lineSeparator ( )} instead, since it does not require a privilege check.
    * @since Java 1.1
    */
  @deprecated val LINE_SEPARATOR: String = getSystemProperty("line.separator")
  /**
    * <p>
    * The {@code os.arch} System Property. Operating system architecture.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val OS_ARCH: String = getSystemProperty("os.arch")
  /**
    * <p>
    * The {@code os.name} System Property. Operating system name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val OS_NAME: String = getSystemProperty("os.name")
  /**
    * <p>
    * The {@code os.version} System Property. Operating system version.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val OS_VERSION: String = getSystemProperty("os.version")
  /**
    * <p>
    * The {@code path.separator} System Property. Path separator ({@code &quot;:&quot;} on UNIX).
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @deprecated Use {@link File# pathSeparator}, since it is guaranteed to be a
    *             string containing a single character and it does not require a privilege check.
    * @since Java 1.1
    */
  @deprecated val PATH_SEPARATOR: String = getSystemProperty("path.separator")
  /**
    * <p>
    * The {@code user.country} or {@code user.region} System Property. User's country code, such as {@code GB}. First
    * in Java version 1.2 as {@code user.region}. Renamed to {@code user.country} in 1.4
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.0
    *        Java 1.2
    */
  val USER_COUNTRY: String =
    if (getSystemProperty("user.country") == null) getSystemProperty("user.region")
    else getSystemProperty("user.country")
  /**
    * <p>
    * The {@code user.dir} System Property. User's current working directory.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val USER_DIR: String = getSystemProperty(USER_DIR_KEY)
  /**
    * <p>
    * The {@code user.home} System Property. User's home directory.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val USER_HOME: String = getSystemProperty(USER_HOME_KEY)
  /**
    * <p>
    * The {@code user.language} System Property. User's language code, such as {@code "en"}.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.0
    *        Java 1.2
    */
  val USER_LANGUAGE: String = getSystemProperty("user.language")
  /**
    * <p>
    * The {@code user.name} System Property. User's account name.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since Java 1.1
    */
  val USER_NAME: String = getSystemProperty(USER_NAME_KEY)
  /**
    * <p>
    * The {@code user.timezone} System Property. For example: {@code "America/Los_Angeles"}.
    * </p>
    * <p>
    * Defaults to {@code null} if the runtime does not have security access to read this property or the property does
    * not exist.
    * </p>
    * <p>
    * This value is initialized when the class is loaded. If {@link java.lang.System#setProperty} or
    * {@link java.lang.System#setProperties} is called after this class is loaded, the value will be out of
    * sync with that System property.
    * </p>
    *
    * @since 2.1
    */
  val USER_TIMEZONE: String = getSystemProperty("user.timezone")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.1 (also 1.1.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    */
  val IS_JAVA_1_1: Boolean = getJavaVersionMatches("1.1")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.2 (also 1.2.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    */
  val IS_JAVA_1_2: Boolean = getJavaVersionMatches("1.2")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.3 (also 1.3.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    */
  val IS_JAVA_1_3: Boolean = getJavaVersionMatches("1.3")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.4 (also 1.4.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    */
  val IS_JAVA_1_4: Boolean = getJavaVersionMatches("1.4")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.5 (also 1.5.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    */
  val IS_JAVA_1_5: Boolean = getJavaVersionMatches("1.5")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.6 (also 1.6.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    */
  val IS_JAVA_1_6: Boolean = getJavaVersionMatches("1.6")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.7 (also 1.7.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.0
    */
  val IS_JAVA_1_7: Boolean = getJavaVersionMatches("1.7")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.8 (also 1.8.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.3.2
    */
  val IS_JAVA_1_8: Boolean = getJavaVersionMatches("1.8")
  /**
    * <p>
    * Is {@code true} if this is Java version 1.9 (also 1.9.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.4
    * @deprecated As of release 3.5, replaced by {@link #IS_JAVA_9}
    */
  @deprecated val IS_JAVA_1_9: Boolean = getJavaVersionMatches("9")
  /**
    * <p>
    * Is {@code true} if this is Java version 9 (also 9.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.5
    */
  val IS_JAVA_9: Boolean = getJavaVersionMatches("9")
  /**
    * <p>
    * Is {@code true} if this is Java version 10 (also 10.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.7
    */
  val IS_JAVA_10: Boolean = getJavaVersionMatches("10")
  /**
    * <p>
    * Is {@code true} if this is Java version 11 (also 11.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.8
    */
  val IS_JAVA_11: Boolean = getJavaVersionMatches("11")
  /**
    * <p>
    * Is {@code true} if this is Java version 12 (also 12.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.9
    */
  val IS_JAVA_12: Boolean = getJavaVersionMatches("12")
  /**
    * <p>
    * Is {@code true} if this is Java version 13 (also 13.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.9
    */
  val IS_JAVA_13: Boolean = getJavaVersionMatches("13")
  /**
    * <p>
    * Is {@code true} if this is Java version 14 (also 14.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.10
    */
  val IS_JAVA_14: Boolean = getJavaVersionMatches("14")
  /**
    * <p>
    * Is {@code true} if this is Java version 15 (also 15.x versions).
    * </p>
    * <p>
    * The field will return {@code false} if {@link #JAVA_VERSION} is {@code null}.
    * </p>
    *
    * @since 3.10
    */
  val IS_JAVA_15: Boolean = getJavaVersionMatches("15")
  /**
    * <p>
    * Is {@code true} if this is AIX.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_AIX: Boolean = getOsMatchesName("AIX")
  /**
    * <p>
    * Is {@code true} if this is HP-UX.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_HP_UX: Boolean = getOsMatchesName("HP-UX")
  /**
    * <p>
    * Is {@code true} if this is IBM OS/400.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.3
    */
  val IS_OS_400: Boolean = getOsMatchesName("OS/400")
  /**
    * <p>
    * Is {@code true} if this is Irix.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_IRIX: Boolean = getOsMatchesName("Irix")
  /**
    * <p>
    * Is {@code true} if this is Linux.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_LINUX: Boolean = getOsMatchesName("Linux") || getOsMatchesName("LINUX")
  /**
    * <p>
    * Is {@code true} if this is Mac.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_MAC: Boolean = getOsMatchesName("Mac")
  val IS_OS_MAC_OSX: Boolean = getOsMatchesName("Mac OS X")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Cheetah.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_CHEETAH: Boolean = getOsMatches("Mac OS X", "10.0")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Puma.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_PUMA: Boolean = getOsMatches("Mac OS X", "10.1")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Jaguar.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_JAGUAR: Boolean = getOsMatches("Mac OS X", "10.2")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Panther.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_PANTHER: Boolean = getOsMatches("Mac OS X", "10.3")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Tiger.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_TIGER: Boolean = getOsMatches("Mac OS X", "10.4")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Leopard.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_LEOPARD: Boolean = getOsMatches("Mac OS X", "10.5")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Snow Leopard.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_SNOW_LEOPARD: Boolean = getOsMatches("Mac OS X", "10.6")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Lion.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_LION: Boolean = getOsMatches("Mac OS X", "10.7")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Mountain Lion.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_MOUNTAIN_LION: Boolean = getOsMatches("Mac OS X", "10.8")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Mavericks.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_MAVERICKS: Boolean = getOsMatches("Mac OS X", "10.9")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X Yosemite.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_MAC_OSX_YOSEMITE: Boolean = getOsMatches("Mac OS X", "10.10")
  /**
    * <p>
    * Is {@code true} if this is Mac OS X El Capitan.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.5
    */
  val IS_OS_MAC_OSX_EL_CAPITAN: Boolean = getOsMatches("Mac OS X", "10.11")
  /**
    * <p>
    * Is {@code true} if this is FreeBSD.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.1
    */
  val IS_OS_FREE_BSD: Boolean = getOsMatchesName("FreeBSD")
  /**
    * <p>
    * Is {@code true} if this is OpenBSD.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.1
    */
  val IS_OS_OPEN_BSD: Boolean = getOsMatchesName("OpenBSD")
  /**
    * <p>
    * Is {@code true} if this is NetBSD.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.1
    */
  val IS_OS_NET_BSD: Boolean = getOsMatchesName("NetBSD")
  /**
    * <p>
    * Is {@code true} if this is OS/2.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_OS2: Boolean = getOsMatchesName("OS/2")
  /**
    * <p>
    * Is {@code true} if this is Solaris.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_SOLARIS: Boolean = getOsMatchesName("Solaris")
  /**
    * <p>
    * Is {@code true} if this is SunOS.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_SUN_OS: Boolean = getOsMatchesName("SunOS")
  /**
    * <p>
    * Is {@code true} if this is a UNIX like system, as in any of AIX, HP-UX, Irix, Linux, MacOSX, Solaris or SUN OS.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.1
    */
  val IS_OS_UNIX: Boolean =
    IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS || IS_OS_SUN_OS || IS_OS_FREE_BSD || IS_OS_OPEN_BSD || IS_OS_NET_BSD
  /**
    * <p>
    * Is {@code true} if this is Windows.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX)
  /**
    * <p>
    * Is {@code true} if this is Windows 2000.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS_2000: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 2000")
  /**
    * <p>
    * Is {@code true} if this is Windows 2003.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.1
    */
  val IS_OS_WINDOWS_2003: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 2003")
  /**
    * <p>
    * Is {@code true} if this is Windows Server 2008.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.1
    */
  val IS_OS_WINDOWS_2008: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Server 2008")
  /**
    * <p>
    * Is {@code true} if this is Windows Server 2012.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.4
    */
  val IS_OS_WINDOWS_2012: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Server 2012")
  /**
    * <p>
    * Is {@code true} if this is Windows 95.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS_95: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 95")
  /**
    * <p>
    * Is {@code true} if this is Windows 98.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS_98: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 98")
  /**
    * <p>
    * Is {@code true} if this is Windows ME.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS_ME: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Me")
  /**
    * <p>
    * Is {@code true} if this is Windows NT.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS_NT: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " NT")
  /**
    * <p>
    * Is {@code true} if this is Windows XP.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.0
    */
  val IS_OS_WINDOWS_XP: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " XP")
  /**
    * <p>
    * Is {@code true} if this is Windows Vista.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 2.4
    */
  val IS_OS_WINDOWS_VISTA: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " Vista")
  /**
    * <p>
    * Is {@code true} if this is Windows 7.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.0
    */
  val IS_OS_WINDOWS_7: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 7")
  /**
    * <p>
    * Is {@code true} if this is Windows 8.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.2
    */
  val IS_OS_WINDOWS_8: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 8")
  /**
    * <p>
    * Is {@code true} if this is Windows 10.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.5
    */
  val IS_OS_WINDOWS_10: Boolean = getOsMatchesName(OS_NAME_WINDOWS_PREFIX + " 10")
  /**
    * <p>
    * Is {@code true} if this is z/OS.
    * </p>
    * <p>
    * The field will return {@code false} if {@code OS_NAME} is {@code null}.
    * </p>
    *
    * @since 3.5
    */
  // Values on a z/OS system I tested (Gary Gregory - 2016-03-12)
  // os.arch = s390x
  // os.encoding = ISO8859_1
  // os.name = z/OS
  // os.version = 02.02.00
  val IS_OS_ZOS: Boolean = getOsMatchesName("z/OS")

  /**
    * <p>
    * Gets the Java home directory as a {@code File}.
    * </p>
    *
    * @return a directory
    * @throws java.lang.SecurityException if a security manager exists and its {@code checkPropertyAccess} method doesn't allow
    *                           access to the specified system property.
    * @see System#getProperty(String)
    * @since 2.1
    */
  def getJavaHome = new File(System.getProperty(JAVA_HOME_KEY))

  /**
    * Gets the host name from an environment variable
    * (COMPUTERNAME on Windows, HOSTNAME elsewhere).
    *
    * <p>
    * If you want to know what the network stack says is the host name, you should use {@code InetAddress.getLocalHost().getHostName()}.
    * </p>
    *
    * @return the host name. Will be {@code null} if the environment variable is not defined.
    * @since 3.6
    */
  def getHostName: String =
    if (IS_OS_WINDOWS) System.getenv("COMPUTERNAME")
    else System.getenv("HOSTNAME")

  /**
    * <p>
    * Gets the Java IO temporary directory as a {@code File}.
    * </p>
    *
    * @return a directory
    * @throws java.lang.SecurityException if a security manager exists and its {@code checkPropertyAccess} method doesn't allow
    *                           access to the specified system property.
    * @see System#getProperty(String)
    * @since 2.1
    */
  def getJavaIoTmpDir = new File(System.getProperty(JAVA_IO_TMPDIR_KEY))

  /**
    * <p>
    * Decides if the Java version matches.
    * </p>
    *
    * @param versionPrefix the prefix for the java version
    * @return true if matches, or false if not or can't determine
    */
  private def getJavaVersionMatches(versionPrefix: String) =
    isJavaVersionMatch(JAVA_SPECIFICATION_VERSION, versionPrefix)

  /**
    * Decides if the operating system matches.
    *
    * @param osNamePrefix    the prefix for the OS name
    * @param osVersionPrefix the prefix for the version
    * @return true if matches, or false if not or can't determine
    */
  private def getOsMatches(osNamePrefix: String, osVersionPrefix: String) =
    isOSMatch(OS_NAME, OS_VERSION, osNamePrefix, osVersionPrefix)

  /**
    * Decides if the operating system matches.
    *
    * @param osNamePrefix the prefix for the OS name
    * @return true if matches, or false if not or can't determine
    */
  private def getOsMatchesName(osNamePrefix: String) = isOSNameMatch(OS_NAME, osNamePrefix)

  /**
    * <p>
    * Gets a System property, defaulting to {@code null} if the property cannot be read.
    * </p>
    * <p>
    * If a {@code SecurityException} is caught, the return value is {@code null} and a message is written to
    * {@code System.err}.
    * </p>
    *
    * @param property the system property name
    * @return the system property value or {@code null} if a security problem occurs
    */
  private def getSystemProperty(property: String) =
    try System.getProperty(property)
    catch {
      case _: SecurityException =>
        // we are not allowed to look at this property
        // System.err.println("Caught a SecurityException reading the system property '" + property
        // + "'; the SystemUtils property value will default to null.");
        null
    }

  /**
    * <p>
    * Gets an environment variable, defaulting to {@code defaultValue} if the variable cannot be read.
    * </p>
    * <p>
    * If a {@code SecurityException} is caught, the return value is {@code defaultValue} and a message is written to
    * {@code System.err}.
    * </p>
    *
    * @param name
    * the environment variable name
    * @param defaultValue
    * the default value
    * @return the environment variable value or {@code defaultValue} if a security problem occurs
    * @since 3.8
    */
  def getEnvironmentVariable(name: String, defaultValue: String): String =
    try {
      val value = System.getenv(name)
      if (value == null) defaultValue
      else value
    } catch {
      case _: SecurityException =>
        // System.err.println("Caught a SecurityException reading the environment variable '" + name + "'.");
        defaultValue
    }

  /**
    * <p>
    * Gets the user directory as a {@code File}.
    * </p>
    *
    * @return a directory
    * @throws java.lang.SecurityException if a security manager exists and its {@code checkPropertyAccess} method doesn't allow
    *                           access to the specified system property.
    * @see java.lang.System#getProperty(String)
    * @since 2.1
    */
  def getUserDir = new File(System.getProperty(USER_DIR_KEY))

  /**
    * <p>
    * Gets the user home directory as a {@code File}.
    * </p>
    *
    * @return a directory
    * @throws java.lang.SecurityException if a security manager exists and its {@code checkPropertyAccess} method doesn't allow
    *                           access to the specified system property.
    * @see java.lang.System#getProperty(String)
    * @since 2.1
    */
  def getUserHome = new File(System.getProperty(USER_HOME_KEY))

  /**
    * <p>
    * Gets the user name.
    * </p>
    *
    * @return a name
    * @throws java.lang.SecurityException if a security manager exists and its {@code checkPropertyAccess} method doesn't allow
    *                           access to the specified system property.
    * @see java.lang.System#getProperty(String)
    * @since 3.10
    */
  def getUserName: String = System.getProperty(USER_NAME_KEY)

  /**
    * <p>
    * Gets the user name.
    * </p>
    *
    * @param defaultValue A default value.
    * @return a name
    * @throws java.lang.SecurityException if a security manager exists and its {@code checkPropertyAccess} method doesn't allow
    *                           access to the specified system property.
    * @see java.lang.System#getProperty(String)
    * @since 3.10
    */
  def getUserName(defaultValue: String): String = System.getProperty(USER_NAME_KEY, defaultValue)

  /**
    * Returns whether the {@link #JAVA_AWT_HEADLESS} value is {@code true}.
    *
    * @return {@code true} if {@code JAVA_AWT_HEADLESS} is {@code "true"}, {@code false} otherwise.
    * @see #JAVA_AWT_HEADLESS
    * @since 2.1
    *        Java 1.4
    */
  def isJavaAwtHeadless: Boolean = JavaBoolean.TRUE.toString == JAVA_AWT_HEADLESS

  /**
    * <p>
    * Is the Java version at least the requested version.
    * </p>
    * <p>
    *
    * @param requiredVersion the required version, for example 1.31f
    * @return {@code true} if the actual version is equal or greater than the required version
    */
  def isJavaVersionAtLeast(requiredVersion: JavaVersion): Boolean =
    JAVA_SPECIFICATION_VERSION_AS_ENUM.atLeast(requiredVersion)

  /**
    * <p>
    * Is the Java version at most the requested version.
    * </p>
    * <p>
    * Example input:
    * </p>
    *
    * @param requiredVersion the required version, for example 1.31f
    * @return {@code true} if the actual version is equal or greater than the required version
    * @since 3.9
    */
  def isJavaVersionAtMost(requiredVersion: JavaVersion): Boolean =
    JAVA_SPECIFICATION_VERSION_AS_ENUM.atMost(requiredVersion)

  /**
    * <p>
    * Decides if the Java version matches.
    * </p>
    * <p>
    * This method is package private instead of private to support unit test invocation.
    * </p>
    *
    * @param version       the actual Java version
    * @param versionPrefix the prefix for the expected Java version
    * @return true if matches, or false if not or can't determine
    */
  private[lang3] def isJavaVersionMatch(version: String, versionPrefix: String): Boolean = {
    if (version == null) return false
    version.startsWith(versionPrefix)
  }

  /**
    * Decides if the operating system matches.
    * <p>
    * This method is package private instead of private to support unit test invocation.
    * </p>
    *
    * @param osName          the actual OS name
    * @param osVersion       the actual OS version
    * @param osNamePrefix    the prefix for the expected OS name
    * @param osVersionPrefix the prefix for the expected OS version
    * @return true if matches, or false if not or can't determine
    */
  private[lang3] def isOSMatch(
    osName: String,
    osVersion: String,
    osNamePrefix: String,
    osVersionPrefix: String): Boolean = {
    if (osName == null || osVersion == null) return false
    isOSNameMatch(osName, osNamePrefix) && isOSVersionMatch(osVersion, osVersionPrefix)
  }

  /**
    * Decides if the operating system matches.
    * <p>
    * This method is package private instead of private to support unit test invocation.
    * </p>
    *
    * @param osName       the actual OS name
    * @param osNamePrefix the prefix for the expected OS name
    * @return true if matches, or false if not or can't determine
    */
  private[lang3] def isOSNameMatch(osName: String, osNamePrefix: String): Boolean = {
    if (osName == null) return false
    osName.startsWith(osNamePrefix)
  }

  /**
    * Decides if the operating system version matches.
    * <p>
    * This method is package private instead of private to support unit test invocation.
    * </p>
    *
    * @param osVersion       the actual OS version
    * @param osVersionPrefix the prefix for the expected OS version
    * @return true if matches, or false if not or can't determine
    */
  private[lang3] def isOSVersionMatch(osVersion: String, osVersionPrefix: String): Boolean = {
    if (StringUtils.isEmpty(osVersion)) return false
    // Compare parts of the version string instead of using String.startsWith(String) because otherwise
    // osVersionPrefix 10.1 would also match osVersion 10.10
    val versionPrefixParts = osVersionPrefix.split("\\.")
    val versionParts = osVersion.split("\\.")
    for (i <- 0 until Math.min(versionPrefixParts.length, versionParts.length)) {
      if (!(versionPrefixParts(i) == versionParts(i))) return false
    }
    true
  }
}
