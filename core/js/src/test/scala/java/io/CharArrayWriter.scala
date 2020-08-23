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

package java.io

import scala.collection.JavaConverters._
import scala.collection.mutable

class CharArrayWriter extends Writer {
  private implicit def toCharIterable(cs: CharSequence): Iterable[Char] =
    cs.chars.mapToObj(i => i.toChar).iterator.asScala.toIterable

  private[this] var buf = new mutable.ArrayBuffer[Char]

  def this(initialSize: Int) = {
    this()
    buf = new mutable.ArrayBuffer[Char](initialSize)
  }

  override def append(c: Char): CharArrayWriter = {
    buf += c
    this
  }

  override def append(csq: CharSequence): CharArrayWriter = {
    buf ++= csq
    this
  }

  override def append(csq: CharSequence, start: Int, end: Int): CharArrayWriter = {
    buf ++= csq.subSequence(start, end)
    this
  }

  override def close(): Unit = ()

  override def flush(): Unit = ()

  def reset(): Unit = buf.clear()

  def size(): Int = buf.size

  def toCharArray(): Array[Char] = buf.toArray

  override def toString(): String = buf.toString

  override def write(c: Int): Unit = {
    buf.append(c.toChar)
    ()
  }

  def write(cbuf: Array[Char], off: Int, len: Int): Unit = {
    buf.insertAll(off, cbuf.take(len))
  }

  override def write(str: String): Unit = {
    buf.appendAll(str.toCharArray)
    ()
  }

  override def write(str: String, off: Int, len: Int): Unit =
    buf.insertAll(off, str.toCharArray.take(len))
}