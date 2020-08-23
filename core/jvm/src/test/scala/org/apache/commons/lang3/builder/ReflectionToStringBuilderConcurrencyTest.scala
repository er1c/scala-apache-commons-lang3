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
//
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
//  * Tests concurrent access for {@link ReflectionToStringBuilder}.
//  * <p>
//  * The {@link ToStringStyle} class includes a registry to avoid infinite loops for objects with circular references. We
//  * want to make sure that we do not get concurrency exceptions accessing this registry.
//  * </p>
//  * <p>
//  * The tests on the non-thread-safe collections do not pass.
//  * </p>
//  *
//  * @see <a href="https://issues.apache.org/jira/browse/LANG-762">[LANG-762] Handle or document ReflectionToStringBuilder
//  *      and ToStringBuilder for collections that are not thread safe</a>
//  * @since 3.1
//  */
//object ReflectionToStringBuilderConcurrencyTest {
//
//  private[builder] class CollectionHolder[T <: util.Collection[Any]] private[builder] (var collection: T) {}
//
//  private val DATA_SIZE = 100000
//  private val REPEAT = 100
//}
//
//class ReflectionToStringBuilderConcurrencyTest {
//  @Test
//  @Disabled
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  def testLinkedList(): Unit = {
//    this.testConcurrency(
//      new ReflectionToStringBuilderConcurrencyTest.CollectionHolder[util.List[Integer]](new util.LinkedList[Integer]))
//  }
//
//  @Test
//  @Disabled
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  def testArrayList(): Unit = {
//    this.testConcurrency(
//      new ReflectionToStringBuilderConcurrencyTest.CollectionHolder[util.List[Integer]](new util.ArrayList[Integer]))
//  }
//
//  @Test
//  @Disabled
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  def testCopyOnWriteArrayList(): Unit = {
//    this.testConcurrency(
//      new ReflectionToStringBuilderConcurrencyTest.CollectionHolder[util.List[Integer]](
//        new CopyOnWriteArrayList[Integer]))
//  }
//
//  @throws[InterruptedException]
//  @throws[ExecutionException]
//  private def testConcurrency(
//    holder: ReflectionToStringBuilderConcurrencyTest.CollectionHolder[util.List[Integer]]): Unit = {
//    val list = holder.collection
//    // make a big array that takes a long time to toString()
//    for (i <- 0 until ReflectionToStringBuilderConcurrencyTest.DATA_SIZE) {
//      list.add(Integer.valueOf(i))
//    }
//    // Create a thread pool with two threads to cause the most contention on the underlying resource.
//    val threadPool = Executors.newFixedThreadPool(2)
//    try { // Consumes toStrings
//      val consumer = () => {
//        def foo(): Unit = {
//          for (i <- 0 until ReflectionToStringBuilderConcurrencyTest.REPEAT) {
//            val s = ReflectionToStringBuilder.toString(holder)
//            assertNotNull(s)
//          }
//          Integer.valueOf(ReflectionToStringBuilderConcurrencyTest.REPEAT)
//        }
//
//        foo()
//      }
//      // Produces changes in the list
//      val producer = () => {
//        def foo(): Unit = {
//          for (i <- 0 until ReflectionToStringBuilderConcurrencyTest.DATA_SIZE) {
//            list.remove(list.get(0))
//          }
//          Integer.valueOf(ReflectionToStringBuilderConcurrencyTest.REPEAT)
//        }
//
//        foo()
//      }
//      val tasks = new util.ArrayList[Callable[Integer]]
//      tasks.add(consumer)
//      tasks.add(producer)
//      val futures = threadPool.invokeAll(tasks)
//      import scala.collection.JavaConversions._
//      for (future <- futures) {
//        assertEquals(ReflectionToStringBuilderConcurrencyTest.REPEAT, future.get.intValue)
//      }
//    } finally {
//      threadPool.shutdown()
//      threadPool.awaitTermination(1, TimeUnit.SECONDS)
//    }
//  }
//}
