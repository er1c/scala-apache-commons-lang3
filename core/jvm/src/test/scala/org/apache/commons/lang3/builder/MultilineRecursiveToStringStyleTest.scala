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
//import java.util.{ArrayList, List}
//import org.junit.Assert._
//import org.junit.Test
//import org.junit.{Before, After}
//
///**
//  */
//object MultilineRecursiveToStringStyleTest {
//
//  private[builder] class WithArrays {
//    private[builder] val boolArray = null
//    private[builder] val charArray = null
//    private[builder] val doubleArray = null
//    private[builder] val intArray = null
//    private[builder] val longArray = null
//    private[builder] val stringArray = null
//  }
//
//  private[builder] class Bank private[builder] (var name: String) {}
//
//  private[builder] class Customer private[builder] (var name: String) {
//    private[builder] val bank = null
//    private[builder] val accounts = null
//  }
//
//  private[builder] class Account {
//    private[builder] val owner = null
//    private[builder] val transactions = new util.ArrayList[MultilineRecursiveToStringStyleTest.Transaction]
//
//    def getBalance: Double = {
//      var balance = 0
//      import scala.collection.JavaConversions._
//      for (tx <- transactions) {
//        balance += tx.amount
//      }
//      balance
//    }
//  }
//
//  private[builder] class Transaction private[builder] (var date: String, var amount: Double) {}
//
//}
//
//class MultilineRecursiveToStringStyleTest extends JUnitSuite {
//  final private val BR = System.lineSeparator
//
//  @Test def simpleObject(): Unit = {
//    val tx = new MultilineRecursiveToStringStyleTest.Transaction("2014.10.15", 100)
//    val expected = getClassPrefix(tx) + "[" + BR + "  amount=100.0," + BR + "  date=2014.10.15" + BR + "]"
//    assertEquals(expected, toString(tx))
//  }
//
//  @Test def nestedElements(): Unit = {
//    val customer = new MultilineRecursiveToStringStyleTest.Customer("Douglas Adams")
//    val bank = new MultilineRecursiveToStringStyleTest.Bank("ASF Bank")
//    customer.bank = bank
//    val exp = getClassPrefix(customer) + "[" + BR + "  accounts=<null>," + BR + "  bank=" + getClassPrefix(
//      bank) + "[" + BR + "    name=ASF Bank" + BR + "  ]," + BR + "  name=Douglas Adams" + BR + "]"
//    assertEquals(exp, toString(customer))
//  }
//
//  @Test def nestedAndArray(): Unit = {
//    val acc = new MultilineRecursiveToStringStyleTest.Account
//    val tx1 = new MultilineRecursiveToStringStyleTest.Transaction("2014.10.14", 100)
//    val tx2 = new MultilineRecursiveToStringStyleTest.Transaction("2014.10.15", 50)
//    acc.transactions.add(tx1)
//    acc.transactions.add(tx2)
//    val expected = getClassPrefix(acc) + "[" + BR + "  owner=<null>," + BR + "  transactions=" + getClassPrefix(
//      acc.transactions) + "{" + BR + "    " + getClassPrefix(
//      tx1) + "[" + BR + "      amount=100.0," + BR + "      date=2014.10.14" + BR + "    ]," + BR + "    " + getClassPrefix(
//      tx2) + "[" + BR + "      amount=50.0," + BR + "      date=2014.10.15" + BR + "    ]" + BR + "  }" + BR + "]"
//    assertEquals(expected, toString(acc))
//  }
//
//  @Test def noArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray=<null>," + BR + "  charArray=<null>," + BR + "  doubleArray=<null>," + BR + "  intArray=<null>," + BR + "  longArray=<null>," + BR + "  stringArray=<null>" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def boolArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    wa.boolArray = Array[Boolean](true, false, true)
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray={" + BR + "    true," + BR + "    false," + BR + "    true" + BR + "  }," + BR + "  charArray=<null>," + BR + "  doubleArray=<null>," + BR + "  intArray=<null>," + BR + "  longArray=<null>," + BR + "  stringArray=<null>" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def charArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    wa.charArray = Array[Char]('a', 'A')
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray=<null>," + BR + "  charArray={" + BR + "    a," + BR + "    A" + BR + "  }," + BR + "  doubleArray=<null>," + BR + "  intArray=<null>," + BR + "  longArray=<null>," + BR + "  stringArray=<null>" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def intArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    wa.intArray = Array[Int](1, 2)
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray=<null>," + BR + "  charArray=<null>," + BR + "  doubleArray=<null>," + BR + "  intArray={" + BR + "    1," + BR + "    2" + BR + "  }," + BR + "  longArray=<null>," + BR + "  stringArray=<null>" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def doubleArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    wa.doubleArray = Array[Double](1, 2)
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray=<null>," + BR + "  charArray=<null>," + BR + "  doubleArray={" + BR + "    1.0," + BR + "    2.0" + BR + "  }," + BR + "  intArray=<null>," + BR + "  longArray=<null>," + BR + "  stringArray=<null>" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def longArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    wa.longArray = Array[Long](1L, 2L)
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray=<null>," + BR + "  charArray=<null>," + BR + "  doubleArray=<null>," + BR + "  intArray=<null>," + BR + "  longArray={" + BR + "    1," + BR + "    2" + BR + "  }," + BR + "  stringArray=<null>" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def stringArray(): Unit = {
//    val wa = new MultilineRecursiveToStringStyleTest.WithArrays
//    wa.stringArray = Array[String]("a", "A")
//    val exp = getClassPrefix(
//      wa) + "[" + BR + "  boolArray=<null>," + BR + "  charArray=<null>," + BR + "  doubleArray=<null>," + BR + "  intArray=<null>," + BR + "  longArray=<null>," + BR + "  stringArray={" + BR + "    a," + BR + "    A" + BR + "  }" + BR + "]"
//    assertEquals(exp, toString(wa))
//  }
//
//  @Test def testLANG1319(): Unit = {
//    val stringArray = Array("1", "2")
//    val exp = getClassPrefix(stringArray) + "[" + BR + "  {" + BR + "    1," + BR + "    2" + BR + "  }" + BR + "]"
//    assertEquals(exp, toString(stringArray))
//  }
//
//  private def getClassPrefix(`object`: Any) =
//    `object`.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(`object`))
//
//  private def toString(`object`: Any) = new ReflectionToStringBuilder[_](`object`, new Nothing).toString
//}
