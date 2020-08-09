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
//import java.util.Collections
//
///**
//  * <p>
//  * Helpers for {@code java.lang.Thread} and {@code java.lang.ThreadGroup}.
//  * </p>
//  * <p>
//  * #ThreadSafe#
//  * </p>
//  *
//  * @see java.lang.Thread
//  * @see java.lang.ThreadGroup
//  * @since 3.5
//  */
//object ThreadUtils {
//  /**
//    * Finds the active thread with the specified id if it belongs to the specified thread group.
//    *
//    * @param threadId    The thread id
//    * @param threadGroup The thread group
//    * @return The thread which belongs to a specified thread group and the thread's id match the specified id.
//    *         {@code null} is returned if no such thread exists
//    * @throws IllegalArgumentException if the specified id is zero or negative or the group is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadById(threadId: Long, threadGroup: ThreadGroup): Thread = {
//    Validate.notNull(threadGroup, "The thread group must not be null")
//    val thread = findThreadById(threadId)
//    if (thread != null && threadGroup == thread.getThreadGroup) return thread
//    null
//  }
//
//  /**
//    * Finds the active thread with the specified id if it belongs to a thread group with the specified group name.
//    *
//    * @param threadId        The thread id
//    * @param threadGroupName The thread group name
//    * @return The threads which belongs to a thread group with the specified group name and the thread's id match the specified id.
//    *         {@code null} is returned if no such thread exists
//    * @throws IllegalArgumentException if the specified id is zero or negative or the group name is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadById(threadId: Long, threadGroupName: String): Thread = {
//    Validate.notNull(threadGroupName, "The thread group name must not be null")
//    val thread = findThreadById(threadId)
//    if (thread != null && thread.getThreadGroup != null && thread.getThreadGroup.getName == threadGroupName) return thread
//    null
//  }
//
//  /**
//    * Finds active threads with the specified name if they belong to a specified thread group.
//    *
//    * @param threadName  The thread name
//    * @param threadGroup The thread group
//    * @return The threads which belongs to a thread group and the thread's name match the specified name,
//    *         An empty collection is returned if no such thread exists. The collection returned is always unmodifiable.
//    * @throws IllegalArgumentException if the specified thread name or group is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadsByName(threadName: String, threadGroup: ThreadGroup): util.Collection[Thread] = findThreads(threadGroup, false, new ThreadUtils.NamePredicate(threadName))
//
//  /**
//    * Finds active threads with the specified name if they belong to a thread group with the specified group name.
//    *
//    * @param threadName      The thread name
//    * @param threadGroupName The thread group name
//    * @return The threads which belongs to a thread group with the specified group name and the thread's name match the specified name,
//    *         An empty collection is returned if no such thread exists. The collection returned is always unmodifiable.
//    * @throws IllegalArgumentException if the specified thread name or group name is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadsByName(threadName: String, threadGroupName: String): util.Collection[Thread] = {
//    Validate.notNull(threadName, "The thread name must not be null")
//    Validate.notNull(threadGroupName, "The thread group name must not be null")
//    val threadGroups = findThreadGroups(new ThreadUtils.NamePredicate(threadGroupName))
//    if (threadGroups.isEmpty) return Collections.emptyList
//    val result = new util.ArrayList[Thread]
//    val threadNamePredicate = new ThreadUtils.NamePredicate(threadName)
//    import scala.collection.JavaConversions._
//    for (group <- threadGroups) {
//      result.addAll(findThreads(group, false, threadNamePredicate))
//    }
//    Collections.unmodifiableCollection(result)
//  }
//
//  /**
//    * Finds active thread groups with the specified group name.
//    *
//    * @param threadGroupName The thread group name
//    * @return the thread groups with the specified group name or an empty collection if no such thread group exists. The collection returned is always unmodifiable.
//    * @throws IllegalArgumentException if group name is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadGroupsByName(threadGroupName: String): util.Collection[ThreadGroup] = findThreadGroups(new ThreadUtils.NamePredicate(threadGroupName))
//
//  /**
//    * Gets all active thread groups excluding the system thread group (A thread group is active if it has been not destroyed).
//    *
//    * @return all thread groups excluding the system thread group. The collection returned is always unmodifiable.
//    * @throws java.lang.SecurityException
//    *                           if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException if the current thread cannot modify
//    *                           thread groups from this thread's thread group up to the system thread group
//    */
//  def getAllThreadGroups: util.Collection[ThreadGroup] = findThreadGroups(ALWAYS_TRUE_PREDICATE)
//
//  /**
//    * Gets the system thread group (sometimes also referred as "root thread group").
//    *
//    * @return the system thread group
//    * @throws java.lang.SecurityException if the current thread cannot modify
//    *                           thread groups from this thread's thread group up to the system thread group
//    */
//  def getSystemThreadGroup: ThreadGroup = {
//    var threadGroup = Thread.currentThread.getThreadGroup
//    while ( {
//      threadGroup.getParent != null
//    }) threadGroup = threadGroup.getParent
//    threadGroup
//  }
//
//  /**
//    * Gets all active threads (A thread is active if it has been started and has not yet died).
//    *
//    * @return all active threads. The collection returned is always unmodifiable.
//    * @throws java.lang.SecurityException
//    *                           if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException if the current thread cannot modify
//    *                           thread groups from this thread's thread group up to the system thread group
//    */
//  def getAllThreads: util.Collection[Thread] = findThreads(ALWAYS_TRUE_PREDICATE)
//
//  /**
//    * Finds active threads with the specified name.
//    *
//    * @param threadName The thread name
//    * @return The threads with the specified name or an empty collection if no such thread exists. The collection returned is always unmodifiable.
//    * @throws IllegalArgumentException if the specified name is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadsByName(threadName: String): util.Collection[Thread] = findThreads(new ThreadUtils.NamePredicate(threadName))
//
//  /**
//    * Finds the active thread with the specified id.
//    *
//    * @param threadId The thread id
//    * @return The thread with the specified id or {@code null} if no such thread exists
//    * @throws IllegalArgumentException if the specified id is zero or negative
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadById(threadId: Long): Thread = {
//    val result = findThreads(new ThreadUtils.ThreadIdPredicate(threadId))
//    if (result.isEmpty) null
//    else result.iterator.next
//  }
//
//  /**
//    * A predicate for selecting threads.
//    */
//  // When breaking BC, replace this with Predicate<Thread>
//  @FunctionalInterface trait ThreadPredicate {
//    /**
//      * Evaluates this predicate on the given thread.
//      *
//      * @param thread the thread
//      * @return {@code true} if the thread matches the predicate, otherwise {@code false}
//      */
//    def test(thread: Thread): Boolean
//  }
//
//  /**
//    * A predicate for selecting threadgroups.
//    */
//  // When breaking BC, replace this with Predicate<ThreadGroup>
//  @FunctionalInterface trait ThreadGroupPredicate {
//    /**
//      * Evaluates this predicate on the given threadgroup.
//      *
//      * @param threadGroup the threadgroup
//      * @return {@code true} if the threadGroup matches the predicate, otherwise {@code false}
//      */
//    def test(threadGroup: ThreadGroup): Boolean
//  }
//
//  /**
//    * Predicate which always returns true.
//    */
//  val ALWAYS_TRUE_PREDICATE = new ThreadUtils.AlwaysTruePredicate
//
//  /**
//    * A predicate implementation which always returns true.
//    */
//  final private class AlwaysTruePredicate private() extends ThreadUtils.ThreadPredicate with ThreadUtils.ThreadGroupPredicate {
//    override def test(threadGroup: ThreadGroup) = true
//
//    override def test(thread: Thread) = true
//  }
//
//  /**
//    * A predicate implementation which matches a thread or threadgroup name.
//    */
//  class NamePredicate(val name: String)
//
//  /**
//    * Predicate constructor
//    *
//    * @param name thread or threadgroup name
//    * @throws IllegalArgumentException if the name is {@code null}
//    */
//    extends ThreadUtils.ThreadPredicate with ThreadUtils.ThreadGroupPredicate {
//    Validate.notNull(name, "The name must not be null")
//
//    override def test(threadGroup: ThreadGroup): Boolean = threadGroup != null && threadGroup.getName == name
//
//    override def test(thread: Thread): Boolean = thread != null && thread.getName == name
//  }
//
//  /**
//    * A predicate implementation which matches a thread id.
//    */
//  class ThreadIdPredicate(val threadId: Long)
//
//  /**
//    * Predicate constructor
//    *
//    * @param threadId the threadId to match
//    * @throws IllegalArgumentException if the threadId is zero or negative
//    */
//    extends ThreadUtils.ThreadPredicate {
//    if (threadId <= 0) throw new IllegalArgumentException("The thread id must be greater than zero")
//
//    override def test(thread: Thread): Boolean = thread != null && thread.getId == threadId
//  }
//
//  /**
//    * Select all active threads which match the given predicate.
//    *
//    * @param predicate the predicate
//    * @return An unmodifiable {@code Collection} of active threads matching the given predicate
//    * @throws IllegalArgumentException if the predicate is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreads(predicate: ThreadUtils.ThreadPredicate): util.Collection[Thread] =
//    findThreads(getSystemThreadGroup, true, predicate)
//
//  /**
//    * Select all active threadgroups which match the given predicate.
//    *
//    * @param predicate the predicate
//    * @return An unmodifiable {@code Collection} of active threadgroups matching the given predicate
//    * @throws IllegalArgumentException if the predicate is null
//    * @throws java.lang.SecurityException
//    *                                  if the current thread cannot access the system thread group
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadGroups(predicate: ThreadUtils.ThreadGroupPredicate): util.Collection[ThreadGroup] = findThreadGroups(getSystemThreadGroup, true, predicate)
//
//  /**
//    * Select all active threads which match the given predicate and which belongs to the given thread group (or one of its subgroups).
//    *
//    * @param group     the thread group
//    * @param recurse   if {@code true} then evaluate the predicate recursively on all threads in all subgroups of the given group
//    * @param predicate the predicate
//    * @return An unmodifiable {@code Collection} of active threads which match the given predicate and which belongs to the given thread group
//    * @throws IllegalArgumentException if the given group or predicate is null
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreads(group: ThreadGroup, recurse: Boolean, predicate: ThreadUtils.ThreadPredicate): util.Collection[Thread] = {
//    Validate.notNull(group, "The group must not be null")
//    Validate.notNull(predicate, "The predicate must not be null")
//    var count = group.activeCount
//    var threads: Array[Thread] = null
//    do {
//      threads = new Array[Thread](count + (count / 2) + 1) //slightly grow the array size
//
//      count = group.enumerate(threads, recurse)
//      //return value of enumerate() must be strictly less than the array size according to javadoc
//    } while ( {
//      count >= threads.length
//    })
//    val result = new util.ArrayList[Thread](count)
//    for (i <- 0 until count) {
//      if (predicate.test(threads(i))) result.add(threads(i))
//    }
//    Collections.unmodifiableCollection(result)
//  }
//
//  /**
//    * Select all active threadgroups which match the given predicate and which is a subgroup of the given thread group (or one of its subgroups).
//    *
//    * @param group     the thread group
//    * @param recurse   if {@code true} then evaluate the predicate recursively on all threadgroups in all subgroups of the given group
//    * @param predicate the predicate
//    * @return An unmodifiable {@code Collection} of active threadgroups which match the given predicate and which is a subgroup of the given thread group
//    * @throws IllegalArgumentException if the given group or predicate is null
//    * @throws java.lang.SecurityException        if the current thread cannot modify
//    *                                  thread groups from this thread's thread group up to the system thread group
//    */
//  def findThreadGroups(group: ThreadGroup, recurse: Boolean, predicate: ThreadUtils.ThreadGroupPredicate): util.Collection[ThreadGroup] = {
//    Validate.notNull(group, "The group must not be null")
//    Validate.notNull(predicate, "The predicate must not be null")
//    var count = group.activeGroupCount
//    var threadGroups: Array[ThreadGroup] = null
//    do {
//      threadGroups = new Array[ThreadGroup](count + (count / 2) + 1)
//      count = group.enumerate(threadGroups, recurse)
//    } while ( {
//      count >= threadGroups.length
//    })
//    val result = new util.ArrayList[ThreadGroup](count)
//    for (i <- 0 until count) {
//      if (predicate.test(threadGroups(i))) result.add(threadGroups(i))
//    }
//    Collections.unmodifiableCollection(result)
//  }
//}
