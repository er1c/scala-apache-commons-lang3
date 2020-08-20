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

package org.apache.commons.lang3.builder

import java.util
import org.apache.commons.lang3.{void, ClassUtils}

/**
  * <p>Works with {@link ToStringBuilder} to create a "deep" {@code toString}.</p>
  *
  * <p>To use this class write code as follows:</p>
  *
  * <pre>
  * public class Job {
  *   String title;
  *   ...
  * }
  *
  * public class Person {
  *   String name;
  *   int age;
  *   boolean smoker;
  *   Job job;
  *
  * ...
  *
  *   public String toString() {
  *     return new ReflectionToStringBuilder(this, new RecursiveToStringStyle()).toString();
  *   }
  * }
  * </pre>
  *
  * <p>This will produce a toString of the format:
  * {@code Person@7f54[name=Stephen,age=29,smoker=false,job=Job@43cd2[title=Manager]]}</p>
  *
  * @since 3.2
  */
@SerialVersionUID(1L)
class RecursiveToStringStyle()

/**
  * <p>Constructor.</p>
  */
  extends ToStringStyle {
  override def appendDetail(buffer: StringBuffer, fieldName: String, value: AnyRef): Unit = {
    if (!ClassUtils.isPrimitiveWrapper(value.getClass) &&
      !(classOf[String] == value.getClass) &&
      accept(value.getClass)) buffer.append(ReflectionToStringBuilder.toString(value, this))
    else super.appendDetail(buffer, fieldName, value)

    ()
  }

  override protected def appendDetail(buffer: StringBuffer, fieldName: String, coll: util.Collection[_]): Unit = {
    appendClassName(buffer, coll)
    appendIdentityHashCode(buffer, coll)
    super.appendDetail(buffer, fieldName, coll)
  }

  /**
    * Returns whether or not to recursively format the given {@code Class}.
    * By default, this method always returns {@code true}, but may be overwritten by
    * sub-classes to filter specific classes.
    *
    * @param clazz
    * The class to test.
    * @return Whether or not to recursively format the given {@code Class}.
    */
  protected def accept(clazz: Class[_]): Boolean = {
    void(clazz)
    true
  }
}
