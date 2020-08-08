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
//import org.apache.commons.lang3.BooleanUtils
//
///**
//  * <p>Utility methods focusing on inheritance.</p>
//  *
//  * @since 3.2
//  */
//object InheritanceUtils {
//  /**
//    * <p>Returns the number of inheritance hops between two classes.</p>
//    *
//    * @param child  the child class, may be {@code null}
//    * @param parent the parent class, may be {@code null}
//    * @return the number of generations between the child and parent; 0 if the same class;
//    *         -1 if the classes are not related as child and parent (includes where either class is null)
//    * @since 3.2
//    */
//  def distance(child: Class[_], parent: Class[_]): Int = {
//    if (child == null || parent == null) return -1
//    if (child == parent) return 0
//    val cParent = child.getSuperclass
//    var d = BooleanUtils.toInteger(parent == cParent)
//    if (d == 1) return d
//    d += distance(cParent, parent)
//    if (d > 0) d + 1
//    else -1
//  }
//}
