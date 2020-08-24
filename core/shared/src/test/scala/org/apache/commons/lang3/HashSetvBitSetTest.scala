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

//package org.apache.commons.lang3
//
//import java.util
//import java.util.concurrent.TimeUnit
//import org.openjdk.jmh.annotations.Benchmark
//import org.openjdk.jmh.annotations.BenchmarkMode
//import org.openjdk.jmh.annotations.Mode
//import org.openjdk.jmh.annotations.OutputTimeUnit
//import org.openjdk.jmh.annotations.Scope
//import org.openjdk.jmh.annotations.State
//
///**
//  * Test to show whether using BitSet for removeAll() methods is faster than using HashSet.
//  */
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
//@State(Scope.Thread) object HashSetvBitSetTest {
//  private val numberOfElementsToCompute = 10
//
//  // --- utility methods
//  private def extractIndices(coll: util.HashSet[Integer]) = {
//    val result = new Array[Int](coll.size)
//    var i = 0
//    import scala.collection.JavaConversions._
//    for (index <- coll) {
//      result({
//        i += 1; i - 1
//      }) = index.intValue
//    }
//    result
//  }
//
//  private def extractIndices(coll: util.BitSet) = {
//    val result = new Array[Int](coll.cardinality)
//    var i = 0
//    var j = 0
//    while ( {
//      (j = coll.nextSetBit(j)) != -1
//    }) result({
//      i += 1; i - 1
//    }) = {
//      j += 1; j - 1
//    }
//    result
//  }
//}
//
//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
//@State(Scope.Thread) class HashSetvBitSetTest {
//  @Benchmark def testHashSet = {
//    val toRemove = new util.HashSet[Integer]
//    var found = 0
//    for (i <- 0 until HashSetvBitSetTest.numberOfElementsToCompute) {
//      toRemove.add({
//        found += 1; found - 1
//      })
//    }
//    HashSetvBitSetTest.extractIndices(toRemove)
//  }
//
//  @Benchmark def testBitSet = {
//    val toRemove = new util.BitSet
//    var found = 0
//    for (i <- 0 until HashSetvBitSetTest.numberOfElementsToCompute) {
//      toRemove.set({
//        found += 1; found - 1
//      })
//    }
//    HashSetvBitSetTest.extractIndices(toRemove)
//  }
//
//  @Benchmark def timeBitSetRemoveAll = {
//    val toRemove = new util.BitSet
//    val array = new Array[Int](100)
//    toRemove.set(10, 20)
//    ArrayUtils.removeAll(array, toRemove).asInstanceOf[Array[Int]]
//  }
//
//  @Benchmark def timeExtractRemoveAll = {
//    val toRemove = new util.BitSet
//    val array = new Array[Int](100)
//    toRemove.set(10, 20)
//    val extractIndices = HashSetvBitSetTest.extractIndices(toRemove)
//    ArrayUtils.removeAll(array.asInstanceOf[Any], extractIndices).asInstanceOf[Array[Int]]
//  }
//}
