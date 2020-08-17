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

//package org.apache.commons.lang3.builder
//
//import org.scalatestplus.junit.JUnitSuite
//import java.util
//import java.util.{LinkedList, Random}
//import org.junit.jupiter.api.Disabled
//import org.junit.jupiter.api.Test
//
//
///**
//  * Tests concurrent access for {@link ReflectionToStringBuilder}.
//  * <p>
//  * The {@link ToStringStyle} class includes a registry to avoid infinite loops for objects with circular references. We
//  * want to make sure that we do not get concurrency exceptions accessing this registry.
//  * </p>
//  *
//  * @see <a href="https://issues.apache.org/jira/browse/LANG-762">[LANG-762] Handle or document ReflectionToStringBuilder
//  *      and ToStringBuilder for collections that are not thread safe</a>
//  * @since 3.1
//  */
//class ReflectionToStringBuilderMutateInspectConcurrencyTest extends JUnitSuite {
//
//  private[builder] class TestFixture private[builder]() {
//    this synchronized
//    for (i <- 0 until N) {
//      listField.add(Integer.valueOf(i))
//    }
//
//    final private val listField = new util.LinkedList[Integer]
//    final private val random = new Random
//    final private val N = 100
//
//    def add(): Unit = {
//      listField.add(Integer.valueOf(random.nextInt(N)))
//    }
//
//    def delete(): Unit = {
//      listField.remove(Integer.valueOf(random.nextInt(N)))
//    }
//  }
//
//  private[builder] class MutatingClient private[builder](val testFixture: ReflectionToStringBuilderMutateInspectConcurrencyTest#TestFixture) extends Runnable {
//    final private val random = new Random
//
//    override def run(): Unit = {
//      if (random.nextBoolean) testFixture.add()
//      else testFixture.delete()
//    }
//  }
//
//  private[builder] class InspectingClient private[builder](val testFixture: ReflectionToStringBuilderMutateInspectConcurrencyTest#TestFixture) extends Runnable {
//    override def run(): Unit = {
//      ReflectionToStringBuilder.toString(testFixture)
//    }
//  }
//
//  @Test
//  @Disabled def testConcurrency(): Unit = {
//    val testFixture = new ReflectionToStringBuilderMutateInspectConcurrencyTest#TestFixture
//    val numMutators = 10
//    val numIterations = 10
//    for (i <- 0 until numIterations) {
//      for (j <- 0 until numMutators) {
//        val t = new Thread(new ReflectionToStringBuilderMutateInspectConcurrencyTest#MutatingClient(testFixture))
//        t.start()
//        val s = new Thread(new ReflectionToStringBuilderMutateInspectConcurrencyTest#InspectingClient(testFixture))
//        s.start()
//      }
//    }
//  }
//}
