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

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.ObjectStreamClass
import java.io.OutputStream
import java.io.Serializable

/**
  * <p>Assists with the serialization process and performs additional functionality based
  * on serialization.</p>
  *
  * <ul>
  * <li>Deep clone using serialization
  * <li>Serialize managing finally and IOException
  * <li>Deserialize managing finally and IOException
  * </ul>
  *
  * <p>This class throws exceptions for invalid {@code null} inputs.
  * Each method documents its behavior in more detail.</p>
  *
  * <p>#ThreadSafe#</p>
  *
  * @since 1.0
  */
object SerializationUtils {
  /**
    * <p>Deep clone an {@code Object} using serialization.</p>
    *
    * <p>This is many times slower than writing clone methods by hand
    * on all objects in your object graph. However, for complex object
    * graphs, or for those that don't support deep cloning this can
    * be a simple alternative implementation. Of course all the objects
    * must be {@code Serializable}.</p>
    *
    * @tparam T     the type of the object involved
    * @param object the {@code Serializable} object to clone
    * @return the cloned object
    * @throws SerializationException (runtime) if the serialization fails
    */
  def clone[T <: Serializable](`object`: T): T = {
    if (`object` == null) return null.asInstanceOf[T]
    val objectData = serialize(`object`)
    val bais = new ByteArrayInputStream(objectData)
    val in = new SerializationUtils.ClassLoaderAwareObjectInputStream(bais, `object`.getClass.getClassLoader)
    try {
      /*
       * when we serialize and deserialize an object,
       * it is reasonable to assume the deserialized object
       * is of the same type as the original serialized object
       */
      @SuppressWarnings(Array("unchecked")) // see above
      val readObject: T = in.readObject.asInstanceOf[T]
      readObject
    } catch {
      case ex: ClassNotFoundException =>
        throw new SerializationException("ClassNotFoundException while reading cloned object data", ex)
      case ex: IOException =>
        throw new SerializationException("IOException while reading or closing cloned object data", ex)
    } finally if (in != null) in.close()
  }

  /**
    * Performs a serialization roundtrip. Serializes and deserializes the given object, great for testing objects that
    * implement {@link java.io.Serializable}.
    *
    * @tparam T
    *          the type of the object involved
    * @param msg
    *          the object to roundtrip
    * @return the serialized and deserialized object
    * @since 3.3
    */
  @SuppressWarnings(Array("unchecked")) // OK, because we serialized a type `T`
  def roundtrip[T <: Serializable](msg: T): T = deserialize(serialize(msg))

  /**
    * <p>Serializes an {@code Object} to the specified stream.</p>
    *
    * <p>The stream will be closed once the object is written.
    * This avoids the need for a finally clause, and maybe also exception
    * handling, in the application code.</p>
    *
    * <p>The stream passed in is not buffered internally within this method.
    * This is the responsibility of your application if desired.</p>
    *
    * @param obj          the object to serialize to bytes, may be null
    * @param outputStream the stream to write to, must not be null
    * @throws java.lang.NullPointerException   if {@code outputStream} is {@code null}
    * @throws SerializationException (runtime) if the serialization fails
    */
  def serialize(obj: Serializable, outputStream: OutputStream): Unit = {
    Validate.notNull(outputStream, "The OutputStream must not be null")
    var out: ObjectOutputStream = null
    try {
      out = new ObjectOutputStream(outputStream)
      out.writeObject(obj)
    } catch {
      case ex: IOException => throw new SerializationException(ex)
    } finally {
      if (out != null) out.close()
    }
  }

  /**
    * <p>Serializes an {@code Object} to a byte array for
    * storage/serialization.</p>
    *
    * @param obj the object to serialize to bytes
    * @return a byte[] with the converted Serializable
    * @throws SerializationException (runtime) if the serialization fails
    */
  def serialize(obj: Serializable): Array[Byte] = {
    val baos: ByteArrayOutputStream = new ByteArrayOutputStream(512)
    serialize(obj, baos)
    baos.toByteArray
  }

  /**
    * <p>
    * Deserializes an {@code Object} from the specified stream.
    * </p>
    *
    * <p>
    * The stream will be closed once the object is written. This avoids the need for a finally clause, and maybe also
    * exception handling, in the application code.
    * </p>
    *
    * <p>
    * The stream passed in is not buffered internally within this method. This is the responsibility of your
    * application if desired.
    * </p>
    *
    * <p>
    * If the call site incorrectly types the return value, a {@link java.lang.ClassCastException} is thrown from the call site.
    * Without Generics in this declaration, the call site must type cast and can cause the same ClassCastException.
    * Note that in both cases, the ClassCastException is in the call site, not in this method.
    * </p>
    *
    * @tparam T the object type to be deserialized
    * @param inputStream
    *           the serialized object input stream, must not be null
    * @return the deserialized object
    * @throws java.lang.NullPointerException   if {@code inputStream} is {@code null}
    * @throws SerializationException (runtime) if the serialization fails
    */
  def deserialize[T](inputStream: InputStream): T = {
    Validate.notNull(inputStream, "The InputStream must not be null")
    var in: ObjectInputStream = null
    try {
      in = new ObjectInputStream(inputStream)
      @SuppressWarnings(Array("unchecked")) val obj: T = in.readObject.asInstanceOf[T]
      return obj
    } catch {
      case ex @ (_: ClassNotFoundException | _: IOException) =>
        throw new SerializationException(ex)
    } finally {
      if (in != null) in.close()
    }
  }

  /**
    * <p>
    * Deserializes a single {@code Object} from an array of bytes.
    * </p>
    *
    * <p>
    * If the call site incorrectly types the return value, a {@link java.lang.ClassCastException} is thrown from the call site.
    * Without Generics in this declaration, the call site must type cast and can cause the same ClassCastException.
    * Note that in both cases, the ClassCastException is in the call site, not in this method.
    * </p>
    *
    * @tparam T the object type to be deserialized
    * @param objectData
    *           the serialized object, must not be null
    * @return   the deserialized object
    * @throws java.lang.NullPointerException   if {@code objectData} is {@code null}
    * @throws SerializationException (runtime) if the serialization fails
    */
  def deserialize[T](objectData: Array[Byte]): T = {
    Validate.notNull(objectData, "The byte[] must not be null")
    deserialize(new ByteArrayInputStream(objectData))
  }

  /**
    * <p>Custom specialization of the standard JDK {@link java.io.ObjectInputStream}
    * that uses a custom  {@code ClassLoader} to resolve a class.
    * If the specified {@code ClassLoader} is not able to resolve the class,
    * the context classloader of the current thread will be used.
    * This way, the standard deserialization work also in web-application
    * containers and application servers, no matter in which of the
    * {@code ClassLoader} the particular class that encapsulates
    * serialization/deserialization lives. </p>
    *
    * <p>For more in-depth information about the problem for which this
    * class here is a workaround, see the JIRA issue LANG-626. </p>
    */
  private[lang3] object ClassLoaderAwareObjectInputStream {
    private val primitiveTypes: Map[String, Class[_]] = Map(
      "byte" -> classOf[Byte],
      "short" -> classOf[Short],
      "int" -> classOf[Int],
      "long" -> classOf[Long],
      "float" -> classOf[Float],
      "double" -> classOf[Double],
      "boolean" -> classOf[Boolean],
      "char" -> classOf[Char],
      "void" -> classOf[Unit]
    )
  }

  /**
    * Constructor.
    *
    * @param in          The {@code InputStream}.
    * @param classLoader classloader to use
    * @throws IOException if an I/O error occurs while reading stream header.
    * @see java.io.ObjectInputStream
    */
  private[lang3] class ClassLoaderAwareObjectInputStream(in: InputStream, classLoader: ClassLoader)
    extends ObjectInputStream(in) {
    /**
      * Overridden version that uses the parameterized {@code ClassLoader} or the {@code ClassLoader}
      * of the current {@code Thread} to resolve the class.
      *
      * @param desc An instance of class {@code ObjectStreamClass}.
      * @return A {@code Class} object corresponding to {@code desc}.
      * @throws IOException            Any of the usual Input/Output exceptions.
      * @throws ClassNotFoundException If class of a serialized object cannot be found.
      */
    @throws[IOException]
    @throws[ClassNotFoundException]
    override protected def resolveClass(desc: ObjectStreamClass): Class[_] = {
      val name: String = desc.getName
      try {
        Class.forName(name, false, classLoader)
      } catch {
        case _: ClassNotFoundException =>
          try {
            Class.forName(name, false, Thread.currentThread.getContextClassLoader)
          } catch {
            case cnfe: ClassNotFoundException =>
              val cls: Class[_] = ClassLoaderAwareObjectInputStream.primitiveTypes.getOrElse(name, null)

              if (cls != null) cls
              else throw cnfe
          }
      }
    }
  }
}
