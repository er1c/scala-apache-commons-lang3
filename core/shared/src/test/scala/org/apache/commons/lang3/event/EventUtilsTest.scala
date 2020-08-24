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

//package org.apache.commons.lang3.event
//
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertNotNull
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.beans.PropertyChangeEvent
//import java.beans.PropertyChangeListener
//import java.beans.VetoableChangeListener
//import java.lang.reflect.Constructor
//import java.lang.reflect.InvocationHandler
//import java.lang.reflect.Method
//import java.lang.reflect.Modifier
//import java.lang.reflect.Proxy
//import java.util.Date
//import java.util
//import javax.naming.event.ObjectChangeListener
//import org.junit.Test
//
///**
//  * @since 3.0
//  */
//object EventUtilsTest {
//
//  trait MultipleEventListener {
//    def event1(e: PropertyChangeEvent)
//
//    def event2(e: PropertyChangeEvent)
//  }
//
//  class EventCounter {
//    private var count = 0
//
//    def eventOccurred() = count += 1
//
//    def getCount = count
//  }
//
//  class EventCounterWithEvent {
//    private var count = 0
//
//    def eventOccurred(e: PropertyChangeEvent) = count += 1
//
//    def getCount = count
//  }
//
//  private class EventCountingInvocationHandler extends InvocationHandler {
//    final private val eventCounts = new util.TreeMap[String, Integer]
//
//    def createListener[L](listenerType: Class[L]) = listenerType.cast(Proxy.newProxyInstance(Thread.currentThread.getContextClassLoader, Array[Class[_]](listenerType), this))
//
//    def getEventCount(eventName: String) = {
//      val count = eventCounts.get(eventName)
//      if (count == null) 0
//      else count.intValue
//    }
//
//    override def invoke(proxy: Any, method: Method, args: Array[AnyRef]) = {
//      val count = eventCounts.get(method.getName)
//      if (count == null) eventCounts.put(method.getName, Integer.valueOf(1))
//      else eventCounts.put(method.getName, Integer.valueOf(count.intValue + 1))
//      null
//    }
//  }
//
//  class MultipleEventSource {
//    final private val listeners = EventListenerSupport.create(classOf[EventUtilsTest.MultipleEventListener])
//
//    def addMultipleEventListener(listener: EventUtilsTest.MultipleEventListener) = listeners.addListener(listener)
//  }
//
//  class ExceptionEventSource {
//    def addPropertyChangeListener(listener: PropertyChangeListener) = throw new RuntimeException
//  }
//
//  class PropertyChangeSource {
//    final private val listeners = EventListenerSupport.create(classOf[PropertyChangeListener])
//    private var property = null
//
//    def setProperty(property: String) = {
//      val oldValue = this.property
//      this.property = property
//      listeners.fire.propertyChange(new PropertyChangeEvent(this, "property", oldValue, property))
//    }
//
//    protected def addVetoableChangeListener(listener: VetoableChangeListener) = {
//      // Do nothing!
//    }
//
//    def addPropertyChangeListener(listener: PropertyChangeListener) = listeners.addListener(listener)
//
//    def removePropertyChangeListener(listener: PropertyChangeListener) = listeners.removeListener(listener)
//  }
//
//}
//
//class EventUtilsTest {
//  @Test def testConstructor() = {
//    assertNotNull(new Nothing)
//    val cons = classOf[Nothing].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[Nothing].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[Nothing].getModifiers))
//  }
//
//  @Test def testAddEventListener() = {
//    val src = new EventUtilsTest.PropertyChangeSource
//    val handler = new EventUtilsTest.EventCountingInvocationHandler
//    val listener = handler.createListener(classOf[PropertyChangeListener])
//    assertEquals(0, handler.getEventCount("propertyChange"))
//    EventUtils.addEventListener(src, classOf[PropertyChangeListener], listener)
//    assertEquals(0, handler.getEventCount("propertyChange"))
//    src.setProperty("newValue")
//    assertEquals(1, handler.getEventCount("propertyChange"))
//  }
//
//  @Test def testAddEventListenerWithNoAddMethod() = {
//    val src = new EventUtilsTest.PropertyChangeSource
//    val handler = new EventUtilsTest.EventCountingInvocationHandler
//    val listener = handler.createListener(classOf[ObjectChangeListener])
//    val e = assertThrows(classOf[IllegalArgumentException], () => EventUtils.addEventListener(src, classOf[ObjectChangeListener], listener))
//    assertEquals("Class " + src.getClass.getName + " does not have a public add" + classOf[ObjectChangeListener].getSimpleName + " method which takes a parameter of type " + classOf[ObjectChangeListener].getName + ".", e.getMessage)
//  }
//
//  @Test def testAddEventListenerThrowsException() = {
//    val src = new EventUtilsTest.ExceptionEventSource
//    assertThrows(classOf[RuntimeException], () => EventUtils.addEventListener(src, classOf[PropertyChangeListener], (e) => {
//      def foo(e) = {
//      }
//
//      foo(e)
//    }))
//  }
//
//  @Test def testAddEventListenerWithPrivateAddMethod() = {
//    val src = new EventUtilsTest.PropertyChangeSource
//    val handler = new EventUtilsTest.EventCountingInvocationHandler
//    val listener = handler.createListener(classOf[VetoableChangeListener])
//    val e = assertThrows(classOf[IllegalArgumentException], () => EventUtils.addEventListener(src, classOf[VetoableChangeListener], listener))
//    assertEquals("Class " + src.getClass.getName + " does not have a public add" + classOf[VetoableChangeListener].getSimpleName + " method which takes a parameter of type " + classOf[VetoableChangeListener].getName + ".", e.getMessage)
//  }
//
//  @Test def testBindEventsToMethod() = {
//    val src = new EventUtilsTest.PropertyChangeSource
//    val counter = new EventUtilsTest.EventCounter
//    EventUtils.bindEventsToMethod(counter, "eventOccurred", src, classOf[PropertyChangeListener])
//    assertEquals(0, counter.getCount)
//    src.setProperty("newValue")
//    assertEquals(1, counter.getCount)
//  }
//
//  @Test def testBindEventsToMethodWithEvent() = {
//    val src = new EventUtilsTest.PropertyChangeSource
//    val counter = new EventUtilsTest.EventCounterWithEvent
//    EventUtils.bindEventsToMethod(counter, "eventOccurred", src, classOf[PropertyChangeListener])
//    assertEquals(0, counter.getCount)
//    src.setProperty("newValue")
//    assertEquals(1, counter.getCount)
//  }
//
//  @Test def testBindFilteredEventsToMethod() = {
//    val src = new EventUtilsTest.MultipleEventSource
//    val counter = new EventUtilsTest.EventCounter
//    EventUtils.bindEventsToMethod(counter, "eventOccurred", src, classOf[EventUtilsTest.MultipleEventListener], "event1")
//    assertEquals(0, counter.getCount)
//    src.listeners.fire.event1(new PropertyChangeEvent(new Date, "Day", Integer.valueOf(0), Integer.valueOf(1)))
//    assertEquals(1, counter.getCount)
//    src.listeners.fire.event2(new PropertyChangeEvent(new Date, "Day", Integer.valueOf(1), Integer.valueOf(2)))
//    assertEquals(1, counter.getCount)
//  }
//}
