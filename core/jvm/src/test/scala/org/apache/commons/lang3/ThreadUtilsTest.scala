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
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import java.util
//import java.util.concurrent.CountDownLatch
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link org.apache.commons.lang3.ThreadUtils}.
//  */
//object ThreadUtilsTest {
//
//  private class TestThread extends Thread {
//    final private val latch = new CountDownLatch(1)
//
//    def this(name: String) {
//      this()
//      super (name)
//    }
//
//    def this(group: ThreadGroup, name: String) {
//      this()
//      super (group, name)
//    }
//
//    override def start() = {
//      super.start()
//      try latch.await()
//      catch {
//        case e: InterruptedException =>
//          Thread.currentThread.interrupt()
//      }
//    }
//
//    override def run() = {
//      latch.countDown()
//      try this synchronized this.wait()
//      catch {
//        case e: InterruptedException =>
//          Thread.currentThread.interrupt()
//      }
//    }
//  }
//
//}
//
//class ThreadUtilsTest extends JUnitSuite {
//  @Test def testNullThreadName() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadsByName(null))
//
//  @Test def testNullThreadGroupName() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadGroupsByName(null))
//
//  @Test def testNullThreadThreadGroupName1() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadsByName(null, "tgname"))
//
//  @Test def testNullThreadThreadGroupName2() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadsByName("tname", null.asInstanceOf[String]))
//
//  @Test def testNullThreadThreadGroupName3() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadsByName(null, null.asInstanceOf[String]))
//
//  @Test def testNullThreadThreadGroup1() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadsByName("tname", null.asInstanceOf[ThreadGroup]))
//
//  @Test def testNullThreadThreadGroup2() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadById(1L, null.asInstanceOf[ThreadGroup]))
//
//  @Test def testNullThreadThreadGroup3() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadsByName(null, null.asInstanceOf[ThreadGroup]))
//
//  @Test def testInvalidThreadId() = assertThrows(classOf[IllegalArgumentException], () => ThreadUtils.findThreadById(-5L))
//
//  @Test def testThreadGroupsByIdFail() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadById(Thread.currentThread.getId, null.asInstanceOf[String]))
//
//  @Test def testThreadgroupsNullParent() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadGroups(null, true, ThreadUtils.ALWAYS_TRUE_PREDICATE))
//
//  @Test def testThreadgroupsNullPredicate() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreadGroups(null))
//
//  @Test def testThreadsNullPredicate() = assertThrows(classOf[NullPointerException], () => ThreadUtils.findThreads(null))
//
//  @Test def testNoThread() = assertEquals(0, ThreadUtils.findThreadsByName("some_thread_which_does_not_exist_18762ZucTT").size)
//
//  @Test def testNoThreadGroup() = assertEquals(0, ThreadUtils.findThreadGroupsByName("some_thread_group_which_does_not_exist_18762ZucTTII").size)
//
//  @Test def testSystemThreadGroupExists() = {
//    val systemThreadGroup = ThreadUtils.getSystemThreadGroup
//    assertNotNull(systemThreadGroup)
//    assertNull(systemThreadGroup.getParent)
//    assertEquals("system", systemThreadGroup.getName)
//  }
//
//  @Test def testAtLeastOneThreadExists() = assertTrue(ThreadUtils.getAllThreads.size > 0)
//
//  @Test def testAtLeastOneThreadGroupsExists() = assertTrue(ThreadUtils.getAllThreadGroups.size > 0)
//
//  @Test
//  @throws[InterruptedException]
//  def testThreadsSameName() = {
//    val t1 = new ThreadUtilsTest.TestThread("thread1_XXOOLL__")
//    val alsot1 = new ThreadUtilsTest.TestThread("thread1_XXOOLL__")
//    try {
//      t1.start()
//      alsot1.start()
//      assertEquals(2, ThreadUtils.findThreadsByName("thread1_XXOOLL__").size)
//    } finally {
//      t1.interrupt()
//      alsot1.interrupt()
//      t1.join()
//      alsot1.join()
//    }
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testThreads() = {
//    val t1 = new ThreadUtilsTest.TestThread("thread1_XXOOLL__")
//    val t2 = new ThreadUtilsTest.TestThread("thread2_XXOOLL__")
//    try {
//      t1.start()
//      t2.start()
//      assertEquals(1, ThreadUtils.findThreadsByName("thread2_XXOOLL__").size)
//    } finally {
//      t1.interrupt()
//      t2.interrupt()
//      t1.join()
//      t2.join()
//    }
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testThreadsById() = {
//    val t1 = new ThreadUtilsTest.TestThread("thread1_XXOOLL__")
//    val t2 = new ThreadUtilsTest.TestThread("thread2_XXOOLL__")
//    try {
//      t1.start()
//      t2.start()
//      assertSame(t1, ThreadUtils.findThreadById(t1.getId))
//      assertSame(t2, ThreadUtils.findThreadById(t2.getId))
//    } finally {
//      t1.interrupt()
//      t2.interrupt()
//      t1.join()
//      t2.join()
//    }
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testThreadsByIdWrongGroup() = {
//    val t1 = new ThreadUtilsTest.TestThread("thread1_XXOOLL__")
//    val tg = new ThreadGroup("tg__HHEE22")
//    try {
//      t1.start()
//      assertNull(ThreadUtils.findThreadById(t1.getId, tg))
//    } finally {
//      t1.interrupt()
//      t1.join()
//      tg.destroy()
//    }
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testThreadGroups() = {
//    val threadGroup = new ThreadGroup("thread_group_DDZZ99__")
//    val t1 = new ThreadUtilsTest.TestThread(threadGroup, "thread1_XXOOPP__")
//    val t2 = new ThreadUtilsTest.TestThread(threadGroup, "thread2_XXOOPP__")
//    try {
//      t1.start()
//      t2.start()
//      assertEquals(1, ThreadUtils.findThreadsByName("thread1_XXOOPP__").size)
//      assertEquals(1, ThreadUtils.findThreadsByName("thread1_XXOOPP__", "thread_group_DDZZ99__").size)
//      assertEquals(1, ThreadUtils.findThreadsByName("thread2_XXOOPP__", "thread_group_DDZZ99__").size)
//      assertEquals(0, ThreadUtils.findThreadsByName("thread1_XXOOPP__", "non_existent_thread_group_JJHHZZ__").size)
//      assertEquals(0, ThreadUtils.findThreadsByName("non_existent_thread_BBDDWW__", "thread_group_DDZZ99__").size)
//      assertEquals(1, ThreadUtils.findThreadGroupsByName("thread_group_DDZZ99__").size)
//      assertEquals(0, ThreadUtils.findThreadGroupsByName("non_existent_thread_group_JJHHZZ__").size)
//      assertNotNull(ThreadUtils.findThreadById(t1.getId, threadGroup))
//    } finally {
//      t1.interrupt()
//      t2.interrupt()
//      t1.join()
//      t2.join()
//      threadGroup.destroy()
//    }
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testThreadGroupsRef() = {
//    val threadGroup = new ThreadGroup("thread_group_DDZZ99__")
//    val deadThreadGroup = new ThreadGroup("dead_thread_group_MMQQSS__")
//    deadThreadGroup.destroy()
//    val t1 = new ThreadUtilsTest.TestThread(threadGroup, "thread1_XXOOPP__")
//    val t2 = new ThreadUtilsTest.TestThread(threadGroup, "thread2_XXOOPP__")
//    try {
//      t1.start()
//      t2.start()
//      assertEquals(1, ThreadUtils.findThreadsByName("thread1_XXOOPP__").size)
//      assertEquals(1, ThreadUtils.findThreadsByName("thread1_XXOOPP__", threadGroup).size)
//      assertEquals(1, ThreadUtils.findThreadsByName("thread2_XXOOPP__", threadGroup).size)
//      assertEquals(0, ThreadUtils.findThreadsByName("thread1_XXOOPP__", deadThreadGroup).size)
//    } finally {
//      t1.interrupt()
//      t2.interrupt()
//      t1.join()
//      t2.join()
//      threadGroup.destroy()
//      assertEquals(0, ThreadUtils.findThreadsByName("thread2_XXOOPP__", threadGroup).size)
//    }
//  }
//
//  @Test
//  @throws[InterruptedException]
//  def testThreadGroupsById() = {
//    val threadGroup = new ThreadGroup("thread_group_DDZZ99__")
//    val t1 = new ThreadUtilsTest.TestThread(threadGroup, "thread1_XXOOPP__")
//    val t2 = new ThreadUtilsTest.TestThread(threadGroup, "thread2_XXOOPP__")
//    val nonExistingId = t1.getId + t2.getId
//    try {
//      t1.start()
//      t2.start()
//      assertSame(t1, ThreadUtils.findThreadById(t1.getId, "thread_group_DDZZ99__"))
//      assertSame(t2, ThreadUtils.findThreadById(t2.getId, "thread_group_DDZZ99__"))
//      assertNull(ThreadUtils.findThreadById(nonExistingId, "non_existent_thread_group_JJHHZZ__"))
//      assertNull(ThreadUtils.findThreadById(nonExistingId, "thread_group_DDZZ99__"))
//    } finally {
//      t1.interrupt()
//      t2.interrupt()
//      t1.join()
//      t2.join()
//      threadGroup.destroy()
//    }
//  }
//
//  @Test def testConstructor() = {
//    assertNotNull(new Nothing)
//    val cons = classOf[Nothing].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test
//  @throws[Exception]
//  def testComplexThreadGroups() = {
//    val threadGroup1 = new ThreadGroup("thread_group_1__")
//    val threadGroup2 = new ThreadGroup("thread_group_2__")
//    val threadGroup3 = new ThreadGroup(threadGroup2, "thread_group_3__")
//    val threadGroup4 = new ThreadGroup(threadGroup2, "thread_group_4__")
//    val threadGroup5 = new ThreadGroup(threadGroup1, "thread_group_5__")
//    val threadGroup6 = new ThreadGroup(threadGroup4, "thread_group_6__")
//    val threadGroup7 = new ThreadGroup(threadGroup4, "thread_group_7__")
//    val threadGroup7Doubled = new ThreadGroup(threadGroup4, "thread_group_7__")
//    val threadGroups = util.Arrays.asList(threadGroup1, threadGroup2, threadGroup3, threadGroup4, threadGroup5, threadGroup6, threadGroup7, threadGroup7Doubled)
//    val t1 = new ThreadUtilsTest.TestThread("thread1_X__")
//    val t2 = new ThreadUtilsTest.TestThread(threadGroup1, "thread2_X__")
//    val t3 = new ThreadUtilsTest.TestThread(threadGroup2, "thread3_X__")
//    val t4 = new ThreadUtilsTest.TestThread(threadGroup3, "thread4_X__")
//    val t5 = new ThreadUtilsTest.TestThread(threadGroup4, "thread5_X__")
//    val t6 = new ThreadUtilsTest.TestThread(threadGroup5, "thread6_X__")
//    val t7 = new ThreadUtilsTest.TestThread(threadGroup6, "thread7_X__")
//    val t8 = new ThreadUtilsTest.TestThread(threadGroup4, "thread8_X__")
//    val t9 = new ThreadUtilsTest.TestThread(threadGroup6, "thread9_X__")
//    val t10 = new ThreadUtilsTest.TestThread(threadGroup3, "thread10_X__")
//    val t11 = new ThreadUtilsTest.TestThread(threadGroup7, "thread11_X__")
//    val t11Doubled = new ThreadUtilsTest.TestThread(threadGroup7Doubled, "thread11_X__")
//    val threads = util.Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t11Doubled)
//    try {
//      import scala.collection.JavaConversions._
//      for (thread <- threads) {
//        thread.start()
//      }
//      assertTrue(ThreadUtils.getAllThreadGroups.size >= 7)
//      assertTrue(ThreadUtils.getAllThreads.size >= 11)
//      assertTrue(ThreadUtils.findThreads(ThreadUtils.ALWAYS_TRUE_PREDICATE).size >= 11)
//      assertEquals(1, ThreadUtils.findThreadsByName(t4.getName, threadGroup3.getName).size)
//      assertEquals(0, ThreadUtils.findThreadsByName(t4.getName, threadGroup2.getName).size)
//      assertEquals(2, ThreadUtils.findThreadsByName(t11.getName, threadGroup7.getName).size)
//    } finally {
//      import scala.collection.JavaConversions._
//      for (thread <- threads) {
//        thread.interrupt()
//        thread.join()
//      }
//      import scala.collection.JavaConversions._
//      for (threadGroup <- threadGroups) {
//        if (!threadGroup.isDestroyed) threadGroup.destroy()
//      }
//    }
//  }
//}
