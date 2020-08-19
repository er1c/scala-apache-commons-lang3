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
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import java.beans.PropertyChangeEvent
//import java.beans.PropertyVetoException
//import java.beans.VetoableChangeListener
//import java.io.ByteArrayInputStream
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.io.ObjectInputStream
//import java.io.ObjectOutputStream
//import java.lang.reflect.Method
//import java.util
//import java.util.Date
//import org.easymock.EasyMock
//import org.junit.Test
//
///**
//  * @since 3.0
//  */
//class EventListenerSupportTest {
//  @Test def testAddListenerNoDuplicates() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    val listeners = listenerSupport.getListeners
//    assertEquals(0, listeners.length)
//    assertEquals(classOf[VetoableChangeListener], listeners.getClass.getComponentType)
//    val empty = listeners
//    //for fun, show that the same empty instance is used
//    assertSame(empty, listenerSupport.getListeners)
//    val listener1 = EasyMock.createNiceMock(classOf[VetoableChangeListener])
//    listenerSupport.addListener(listener1)
//    assertEquals(1, listenerSupport.getListeners.length)
//    listenerSupport.addListener(listener1, false)
//    assertEquals(1, listenerSupport.getListeners.length)
//    listenerSupport.removeListener(listener1)
//    assertSame(empty, listenerSupport.getListeners)
//  }
//
//  @Test def testAddNullListener() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    assertThrows(classOf[NullPointerException], () => listenerSupport.addListener(null))
//  }
//
//  @Test def testRemoveNullListener() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    assertThrows(classOf[NullPointerException], () => listenerSupport.removeListener(null))
//  }
//
//  @Test
//  @throws[PropertyVetoException]
//  def testEventDispatchOrder() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    val calledListeners = new util.ArrayList[VetoableChangeListener]
//    val listener1 = createListener(calledListeners)
//    val listener2 = createListener(calledListeners)
//    listenerSupport.addListener(listener1)
//    listenerSupport.addListener(listener2)
//    listenerSupport.fire.vetoableChange(new PropertyChangeEvent(new Date, "Day", 4, 5))
//    assertEquals(calledListeners.size, 2)
//    assertSame(calledListeners.get(0), listener1)
//    assertSame(calledListeners.get(1), listener2)
//  }
//
//  @Test def testCreateWithNonInterfaceParameter() = assertThrows(classOf[IllegalArgumentException], () => EventListenerSupport.create(classOf[String]))
//
//  @Test def testCreateWithNullParameter() = assertThrows(classOf[NullPointerException], () => EventListenerSupport.create(null))
//
//  @Test
//  @throws[PropertyVetoException]
//  def testRemoveListenerDuringEvent() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    for (i <- 0 until 10) {
//      addDeregisterListener(listenerSupport)
//    }
//    assertEquals(listenerSupport.getListenerCount, 10)
//    listenerSupport.fire.vetoableChange(new PropertyChangeEvent(new Date, "Day", 4, 5))
//    assertEquals(listenerSupport.getListenerCount, 0)
//  }
//
//  @Test def testGetListeners() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    val listeners = listenerSupport.getListeners
//    assertEquals(0, listeners.length)
//    assertEquals(classOf[VetoableChangeListener], listeners.getClass.getComponentType)
//    val empty = listeners
//    assertSame(empty, listenerSupport.getListeners)
//    val listener1 = EasyMock.createNiceMock(classOf[VetoableChangeListener])
//    listenerSupport.addListener(listener1)
//    assertEquals(1, listenerSupport.getListeners.length)
//    val listener2 = EasyMock.createNiceMock(classOf[VetoableChangeListener])
//    listenerSupport.addListener(listener2)
//    assertEquals(2, listenerSupport.getListeners.length)
//    listenerSupport.removeListener(listener1)
//    assertEquals(1, listenerSupport.getListeners.length)
//    listenerSupport.removeListener(listener2)
//    assertSame(empty, listenerSupport.getListeners)
//  }
//
//  @Test
//  @throws[IOException]
//  @throws[ClassNotFoundException]
//  @throws[PropertyVetoException]
//  def testSerialization() = {
//    val listenerSupport = EventListenerSupport.create(classOf[VetoableChangeListener])
//    listenerSupport.addListener((e) => {
//      def foo(e) = {
//      }
//
//      foo(e)
//    })
//    listenerSupport.addListener(EasyMock.createNiceMock(classOf[VetoableChangeListener]))
//    //serialize:
//    val outputStream = new ByteArrayOutputStream
//    val objectOutputStream = new ObjectOutputStream(outputStream)
//    objectOutputStream.writeObject(listenerSupport)
//    objectOutputStream.close()
//    //deserialize:
//    @SuppressWarnings(Array("unchecked")) val deserializedListenerSupport = new ObjectInputStream(new ByteArrayInputStream(outputStream.toByteArray)).readObject.asInstanceOf[Nothing]
//    //make sure we get a listener array back, of the correct component type, and that it contains only the serializable mock
//    val listeners = deserializedListenerSupport.getListeners
//    assertEquals(classOf[VetoableChangeListener], listeners.getClass.getComponentType)
//    assertEquals(1, listeners.length)
//    //now verify that the mock still receives events; we can infer that the proxy was correctly reconstituted
//    val listener = listeners(0)
//    val evt = new PropertyChangeEvent(new Date, "Day", 7, 9)
//    listener.vetoableChange(evt)
//    EasyMock.replay(listener)
//    deserializedListenerSupport.fire.vetoableChange(evt)
//    EasyMock.verify(listener)
//    //remove listener and verify we get an empty array of listeners
//    deserializedListenerSupport.removeListener(listener)
//    assertEquals(0, deserializedListenerSupport.getListeners.length)
//  }
//
//  @Test
//  @throws[PropertyVetoException]
//  def testSubclassInvocationHandling() = {
//    val eventListenerSupport = new Nothing(classOf[VetoableChangeListener]) {
//      protected def createInvocationHandler = new Nothing() {
//        /**
//          * {@inheritDoc }
//          */
//          @throws[Throwable]
//          def invoke(proxy: Any, method: Method, args: Array[AnyRef]): Any = return if ("vetoableChange" == method.getName && "Hour" == args(0).asInstanceOf[PropertyChangeEvent].getPropertyName) null
//          else super.invoke(proxy, method, args)
//      }
//    }
//    val listener = EasyMock.createNiceMock(classOf[VetoableChangeListener])
//    eventListenerSupport.addListener(listener)
//    val source = new Date
//    val ignore = new PropertyChangeEvent(source, "Hour", 5, 6)
//    val respond = new PropertyChangeEvent(source, "Day", 6, 7)
//    listener.vetoableChange(respond)
//    EasyMock.replay(listener)
//    eventListenerSupport.fire.vetoableChange(ignore)
//    eventListenerSupport.fire.vetoableChange(respond)
//    EasyMock.verify(listener)
//  }
//
//  private def addDeregisterListener(listenerSupport: Nothing) = listenerSupport.addListener(new VetoableChangeListener() {
//    override def vetoableChange(e: PropertyChangeEvent) = listenerSupport.removeListener(this)
//  })
//
//  private def createListener(calledListeners: util.List[VetoableChangeListener]) = new VetoableChangeListener() {
//    override def vetoableChange(e: PropertyChangeEvent) = calledListeners.add(this)
//  }
//}
