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

//package org.apache.commons.lang3.concurrent
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertNotSame
//import org.junit.Assert.assertNull
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.util.concurrent.ThreadFactory
//import org.easymock.EasyMock
//import org.junit.jupiter.api.BeforeEach
//import org.junit.Test
//
///**
//  * Test class for {@code BasicThreadFactory}.
//  */
//object BasicThreadFactoryTest {
//  /** Constant for the test naming pattern. */
//    private val PATTERN = "testThread-%d"
//}
//
//class BasicThreadFactoryTest {
//  /** The builder for creating a thread factory. */
//    private var builder = null
//
//  @BeforeEach def setUp() = builder = new Nothing
//
//  /**
//    * Tests the default options of a thread factory.
//    *
//    * @param factory the factory to be checked
//    */
//  private def checkFactoryDefaults(factory: Nothing) = {
//    assertNull(factory.getNamingPattern, "Got a naming pattern")
//    assertNull(factory.getUncaughtExceptionHandler, "Got an exception handler")
//    assertNull(factory.getPriority, "Got a priority")
//    assertNull(factory.getDaemonFlag, "Got a daemon flag")
//    assertNotNull(factory.getWrappedFactory, "No wrapped factory")
//  }
//
//  /**
//    * Tests the default values used by the builder.
//    */
//  @Test def testBuildDefaults() = {
//    val factory = builder.build
//    checkFactoryDefaults(factory)
//  }
//
//  /**
//    * Tries to set a null naming pattern.
//    */
//  @Test def testBuildNamingPatternNull() = assertThrows(classOf[NullPointerException], () => builder.namingPattern(null))
//
//  /**
//    * Tries to set a null wrapped factory.
//    */
//  @Test def testBuildWrappedFactoryNull() = assertThrows(classOf[NullPointerException], () => builder.wrappedFactory(null))
//
//  /**
//    * Tries to set a null exception handler.
//    */
//  @Test def testBuildUncaughtExceptionHandlerNull() = assertThrows(classOf[NullPointerException], () => builder.uncaughtExceptionHandler(null))
//
//  /**
//    * Tests the reset() method of the builder.
//    */
//  @Test def testBuilderReset() = {
//    val wrappedFactory = EasyMock.createMock(classOf[ThreadFactory])
//    val exHandler = EasyMock.createMock(classOf[Thread.UncaughtExceptionHandler])
//    EasyMock.replay(wrappedFactory, exHandler)
//    builder.namingPattern(BasicThreadFactoryTest.PATTERN).daemon(true).priority(Thread.MAX_PRIORITY).uncaughtExceptionHandler(exHandler).wrappedFactory(wrappedFactory)
//    builder.reset
//    val factory = builder.build
//    checkFactoryDefaults(factory)
//    assertNotSame(wrappedFactory, factory.getWrappedFactory, "Wrapped factory not reset")
//    EasyMock.verify(wrappedFactory, exHandler)
//  }
//
//  /**
//    * Tests whether reset() is automatically called after build().
//    */
//  @Test def testBuilderResetAfterBuild() = {
//    builder.wrappedFactory(EasyMock.createNiceMock(classOf[ThreadFactory])).namingPattern(BasicThreadFactoryTest.PATTERN).daemon(true).build
//    checkFactoryDefaults(builder.build)
//  }
//
//  /**
//    * Tests whether the naming pattern is applied to new threads.
//    */
//  @Test def testNewThreadNamingPattern() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val count = 12
//    for (i <- 0 until count) {
//      EasyMock.expect(wrapped.newThread(r)).andReturn(new Thread)
//    }
//    EasyMock.replay(wrapped, r)
//    val factory = builder.wrappedFactory(wrapped).namingPattern(BasicThreadFactoryTest.PATTERN).build
//    for (i <- 0 until count) {
//      val t = factory.newThread(r)
//      assertEquals(String.format(BasicThreadFactoryTest.PATTERN, Long.valueOf(i + 1)), t.getName, "Wrong thread name")
//      assertEquals(i + 1, factory.getThreadCount, "Wrong thread count")
//    }
//    EasyMock.verify(wrapped, r)
//  }
//
//  /**
//    * Tests whether the thread name is not modified if no naming pattern is
//    * set.
//    */
//  @Test def testNewThreadNoNamingPattern() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val name = "unchangedThreadName"
//    val t = new Thread(name)
//    EasyMock.expect(wrapped.newThread(r)).andReturn(t)
//    EasyMock.replay(wrapped, r)
//    val factory = builder.wrappedFactory(wrapped).build
//    assertSame(t, factory.newThread(r), "Wrong thread")
//    assertEquals(name, t.getName, "Name was changed")
//    EasyMock.verify(wrapped, r)
//  }
//
//  /**
//    * Helper method for testing whether the daemon flag is taken into account.
//    *
//    * @param flag the value of the flag
//    */
//  private def checkDaemonFlag(flag: Boolean) = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val t = new Thread
//    EasyMock.expect(wrapped.newThread(r)).andReturn(t)
//    EasyMock.replay(wrapped, r)
//    val factory = builder.wrappedFactory(wrapped).daemon(flag).build
//    assertSame(t, factory.newThread(r), "Wrong thread")
//    assertEquals(flag, t.isDaemon, "Wrong daemon flag")
//    EasyMock.verify(wrapped, r)
//  }
//
//  /**
//    * Tests whether daemon threads can be created.
//    */
//  @Test def testNewThreadDaemonTrue() = checkDaemonFlag(true)
//
//  /**
//    * Tests whether the daemon status of new threads can be turned off.
//    */
//  @Test def testNewThreadDaemonFalse() = checkDaemonFlag(false)
//
//  /**
//    * Tests whether the daemon flag is not touched on newly created threads if
//    * it is not specified.
//    */
//  @Test def testNewThreadNoDaemonFlag() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r1 = EasyMock.createMock(classOf[Runnable])
//    val r2 = EasyMock.createMock(classOf[Runnable])
//    val t1 = new Thread
//    val t2 = new Thread
//    t1.setDaemon(true)
//    EasyMock.expect(wrapped.newThread(r1)).andReturn(t1)
//    EasyMock.expect(wrapped.newThread(r2)).andReturn(t2)
//    EasyMock.replay(wrapped, r1, r2)
//    val factory = builder.wrappedFactory(wrapped).build
//    assertSame(t1, factory.newThread(r1), "Wrong thread 1")
//    assertTrue(t1.isDaemon, "No daemon thread")
//    assertSame(t2, factory.newThread(r2), "Wrong thread 2")
//    assertFalse(t2.isDaemon, "A daemon thread")
//    EasyMock.verify(wrapped, r1, r2)
//  }
//
//  /**
//    * Tests whether the priority is set on newly created threads.
//    */
//  @Test def testNewThreadPriority() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val t = new Thread
//    EasyMock.expect(wrapped.newThread(r)).andReturn(t)
//    EasyMock.replay(wrapped, r)
//    val priority = Thread.NORM_PRIORITY + 1
//    val factory = builder.wrappedFactory(wrapped).priority(priority).build
//    assertSame(t, factory.newThread(r), "Wrong thread")
//    assertEquals(priority, t.getPriority, "Wrong priority")
//    EasyMock.verify(wrapped, r)
//  }
//
//  /**
//    * Tests whether the original priority is not changed if no priority is
//    * specified.
//    */
//  @Test def testNewThreadNoPriority() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val orgPriority = Thread.NORM_PRIORITY + 1
//    val t = new Thread
//    t.setPriority(orgPriority)
//    EasyMock.expect(wrapped.newThread(r)).andReturn(t)
//    EasyMock.replay(wrapped, r)
//    val factory = builder.wrappedFactory(wrapped).build
//    assertSame(t, factory.newThread(r), "Wrong thread")
//    assertEquals(orgPriority, t.getPriority, "Wrong priority")
//    EasyMock.verify(wrapped, r)
//  }
//
//  /**
//    * Tests whether the exception handler is set if one is provided.
//    */
//  @Test def testNewThreadExHandler() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val handler = EasyMock.createMock(classOf[Thread.UncaughtExceptionHandler])
//    val t = new Thread
//    EasyMock.expect(wrapped.newThread(r)).andReturn(t)
//    EasyMock.replay(wrapped, r, handler)
//    val factory = builder.wrappedFactory(wrapped).uncaughtExceptionHandler(handler).build
//    assertSame(t, factory.newThread(r), "Wrong thread")
//    assertEquals(handler, t.getUncaughtExceptionHandler, "Wrong exception handler")
//    EasyMock.verify(wrapped, r, handler)
//  }
//
//  /**
//    * Tests whether the original exception handler is not touched if none is
//    * specified.
//    */
//  @Test def testNewThreadNoExHandler() = {
//    val wrapped = EasyMock.createMock(classOf[ThreadFactory])
//    val r = EasyMock.createMock(classOf[Runnable])
//    val handler = EasyMock.createMock(classOf[Thread.UncaughtExceptionHandler])
//    val t = new Thread
//    t.setUncaughtExceptionHandler(handler)
//    EasyMock.expect(wrapped.newThread(r)).andReturn(t)
//    EasyMock.replay(wrapped, r, handler)
//    val factory = builder.wrappedFactory(wrapped).build
//    assertSame(t, factory.newThread(r), "Wrong thread")
//    assertEquals(handler, t.getUncaughtExceptionHandler, "Wrong exception handler")
//    EasyMock.verify(wrapped, r, handler)
//  }
//}
