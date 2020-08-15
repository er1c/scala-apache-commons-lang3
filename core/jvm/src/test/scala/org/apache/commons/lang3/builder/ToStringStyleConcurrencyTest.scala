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
//import java.util.{ArrayList, Collection, LinkedList, List}
//import java.util.concurrent.Callable
//import java.util.concurrent.CopyOnWriteArrayList
//import java.util.concurrent.ExecutionException
//import java.util.concurrent.ExecutorService
//import java.util.concurrent.Executors
//import java.util.concurrent.Future
//import java.util.concurrent.TimeUnit
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  * Tests concurrent access for the default {@link ToStringStyle}.
//  * <p>
//  * The {@link ToStringStyle} class includes a registry to avoid infinite loops for objects with circular references. We
//  * want to make sure that we do not get concurrency exceptions accessing this registry.
//  * </p>
//  * <p>
//  * This test passes but only tests one aspect of the issue.
//  * </p>
//  *
//  * @see <a href="https://issues.apache.org/jira/browse/LANG-762">[LANG-762] Handle or document ReflectionToStringBuilder
//  *      and ToStringBuilder for collections that are not thread safe</a>
//  * @since 3.1
//  */
//object ToStringStyleConcurrencyTest {
//
//  private[builder] class CollectionHolder[T <: util.Collection[Any]] private[builder] (var collection: T) {}
//
//  private var LIST = null
//  private val LIST_SIZE = 100000
//  private val REPEAT = 100
//
//  try LIST = new util.ArrayList[Integer](LIST_SIZE)
//  for (i <- 0 until LIST_SIZE) {
//    LIST.add(Integer.valueOf(i))
//  }
//
//}
//
//class ToStringStyleConcurrencyTest extends JUnitSuite {
//  @Test
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  def testLinkedList(): Unit = {
//    this.testConcurrency(
//      new ToStringStyleConcurrencyTest.CollectionHolder[util.List[Integer]](new util.LinkedList[Integer]))
//  }
//
//  @Test
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  def testArrayList(): Unit = {
//    this.testConcurrency(
//      new ToStringStyleConcurrencyTest.CollectionHolder[util.List[Integer]](new util.ArrayList[Integer]))
//  }
//
//  @Test
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  def testCopyOnWriteArrayList(): Unit = {
//    this.testConcurrency(
//      new ToStringStyleConcurrencyTest.CollectionHolder[util.List[Integer]](new CopyOnWriteArrayList[Integer]))
//  }
//
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  private def testConcurrency(holder: ToStringStyleConcurrencyTest.CollectionHolder[util.List[Integer]]): Unit = {
//    val list = holder.collection
//    // make a big array that takes a long time to toString()
//    list.addAll(ToStringStyleConcurrencyTest.LIST)
//    // Create a thread pool with two threads to cause the most contention on the underlying resource.
//    val threadPool = Executors.newFixedThreadPool(2)
//    try { // Consumes toStrings
//      val consumer = () => {
//        def foo(): Unit = {
//          for (i <- 0 until ToStringStyleConcurrencyTest.REPEAT) { // Calls ToStringStyle
//            new ToStringBuilder(holder).append(holder.collection)
//          }
//          Integer.valueOf(ToStringStyleConcurrencyTest.REPEAT)
//        }
//
//        foo()
//      }
//      val tasks = new util.ArrayList[Callable[Integer]]
//      tasks.add(consumer)
//      tasks.add(consumer)
//      val futures = threadPool.invokeAll(tasks)
//      import scala.collection.JavaConversions._
//      for (future <- futures) {
//        future.get
//      }
//    } finally {
//      threadPool.shutdown()
//      threadPool.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//}
