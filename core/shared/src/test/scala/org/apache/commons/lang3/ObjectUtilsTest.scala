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

package org.apache.commons.lang3

import java.io.IOException
import java.lang.{Boolean => JavaBoolean}
import java.util
import java.util.{Calendar, Collections, Comparator, Date}
import java.util.function.Supplier
//import org.apache.commons.lang3.exception.CloneFailedException
import org.apache.commons.lang3.mutable.MutableInt
import org.apache.commons.lang3.mutable.MutableObject
import org.apache.commons.lang3.text.StrBuilder
import org.junit.Assert._
import org.junit.Test
//import org.junit.function.ThrowingRunnable
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.ObjectUtils}.
  */
@SuppressWarnings(Array("deprecation")) // deliberate use of deprecated code
object ObjectUtilsTest {
  private val FOO: String = "foo"
  private val BAR: String = "bar"
  private val NON_EMPTY_ARRAY: Array[String] = Array[String](FOO, BAR)
  private val NON_EMPTY_LIST: List[String] = List(NON_EMPTY_ARRAY: _*)
  private val NON_EMPTY_SET: Set[String] = Set(NON_EMPTY_LIST: _*)
  private val NON_EMPTY_MAP: Map[String, String] = Map(FOO -> BAR)

  /**
    * String that is cloneable.
    */
  @SerialVersionUID(1L)
  final private[lang3] class CloneableString private[lang3] (val s: String)
    extends MutableObject[String](s) with Cloneable {
    @throws[CloneNotSupportedException]
    override def clone: ObjectUtilsTest.CloneableString = super.clone.asInstanceOf[ObjectUtilsTest.CloneableString]
  }

  /**
    * String that is not cloneable.
    */
  @SerialVersionUID(1L)
  final private[lang3] class UncloneableString private[lang3] (val s: String)
    extends MutableObject[String](s) with Cloneable {}

  final private[lang3] class NonComparableCharSequence private[lang3] (val value: String)

  /**
    * Create a new NonComparableCharSequence instance.
    *
    * @param value the CharSequence value
    */
    extends CharSequence {
    Validate.notNull(value)

    override def charAt(arg0: Int): Char = value.charAt(arg0)

    override def length: Int = value.length

    override def subSequence(arg0: Int, arg1: Int): CharSequence = value.subSequence(arg0, arg1)

    override def toString: String = value
  }

  final private[lang3] class CharSequenceComparator extends Comparator[CharSequence] {
    override def compare(o1: CharSequence, o2: CharSequence): Int = o1.toString.compareTo(o2.toString)
  }
}

@SuppressWarnings(Array("deprecation"))
class ObjectUtilsTest extends JUnitSuite with TestHelpers {
//  @Test def testConstructor(): Unit = {
//    assertNotNull(new ObjectUtils.type)
//    val cons = classOf[ObjectUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[ObjectUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[ObjectUtils.type].getModifiers))
//  }

  @Test def testIsEmpty(): Unit = {
    assertTrue(ObjectUtils.isEmpty(null))
    assertTrue(ObjectUtils.isEmpty(""))
    assertTrue(ObjectUtils.isEmpty(Array[Int]()))
    assertTrue(ObjectUtils.isEmpty(Collections.emptyList))
    assertTrue(ObjectUtils.isEmpty(Collections.emptySet))
    assertTrue(ObjectUtils.isEmpty(Collections.emptyMap))
    assertFalse(ObjectUtils.isEmpty("  "))
    assertFalse(ObjectUtils.isEmpty("ab"))
    assertFalse(ObjectUtils.isEmpty(ObjectUtilsTest.NON_EMPTY_ARRAY))
    assertFalse(ObjectUtils.isEmpty(ObjectUtilsTest.NON_EMPTY_LIST))
    assertFalse(ObjectUtils.isEmpty(ObjectUtilsTest.NON_EMPTY_SET))
    assertFalse(ObjectUtils.isEmpty(ObjectUtilsTest.NON_EMPTY_MAP))
  }

  @Test def testIsNotEmpty(): Unit = {
    assertFalse(ObjectUtils.isNotEmpty(null))
    assertFalse(ObjectUtils.isNotEmpty(""))
    assertFalse(ObjectUtils.isNotEmpty(Array[Int]()))
    assertFalse(ObjectUtils.isNotEmpty(Collections.emptyList))
    assertFalse(ObjectUtils.isNotEmpty(Collections.emptySet))
    assertFalse(ObjectUtils.isNotEmpty(Collections.emptyMap))
    assertTrue(ObjectUtils.isNotEmpty("  "))
    assertTrue(ObjectUtils.isNotEmpty("ab"))
    assertTrue(ObjectUtils.isNotEmpty(ObjectUtilsTest.NON_EMPTY_ARRAY))
    assertTrue(ObjectUtils.isNotEmpty(ObjectUtilsTest.NON_EMPTY_LIST))
    assertTrue(ObjectUtils.isNotEmpty(ObjectUtilsTest.NON_EMPTY_SET))
    assertTrue(ObjectUtils.isNotEmpty(ObjectUtilsTest.NON_EMPTY_MAP))
  }

  @Test def testDefaultIfNull(): Unit = {
    val o: String = ObjectUtilsTest.FOO
    val dflt: String = ObjectUtilsTest.BAR

    assertSame("dflt was not returned when o was null", dflt, ObjectUtils.defaultIfNull(null, dflt))
    assertSame("dflt was returned when o was not null", o, ObjectUtils.defaultIfNull(o, dflt))

    assertSame(
      "dflt was not returned when o was null",
      dflt,
      ObjectUtils.getIfNull(
        null,
        new Supplier[String] {
          override def get(): String = {
            dflt
          }
        })
    )

    assertSame(
      "dflt was returned when o was not null",
      o,
      ObjectUtils.getIfNull(
        o,
        new Supplier[String] {
          override def get(): String = {
            dflt
          }
        }
      )
    )

    assertSame(
      "dflt was returned when o was not null",
      o,
      ObjectUtils.getIfNull(
        ObjectUtilsTest.FOO,
        new Supplier[String] {
          override def get(): String = dflt
        }
      )
    )

    assertSame(
      "dflt was returned when o was not null",
      o,
      ObjectUtils.getIfNull(
        "foo",
        new Supplier[String] {
          override def get(): String = dflt
        }
      )
    )

    val callsCounter = new MutableInt(0)

    val countingDefaultSupplier: Supplier[Object] = new Supplier[Object] {
      override def get(): Object = {
        callsCounter.increment()
        dflt
      }
    }

    ObjectUtils.getIfNull(o, countingDefaultSupplier)
    assertEquals(0, callsCounter.getValue)
    ObjectUtils.getIfNull(null, countingDefaultSupplier)
    assertEquals(1, callsCounter.getValue)
  }

  @Test def testFirstNonNull(): Unit = {
    assertEquals("", ObjectUtils.firstNonNull(null, ""))
    val firstNonNullGenerics = ObjectUtils.firstNonNull(null, null, "123", "456")
    assertEquals("123", firstNonNullGenerics)
    assertEquals("123", ObjectUtils.firstNonNull("123", null, "456", null))
    assertSame(JavaBoolean.TRUE, ObjectUtils.firstNonNull(JavaBoolean.TRUE))
    // Explicitly pass in an empty array of Object type to ensure compiler doesn't complain of unchecked generic array creation
    // TODO: "dead code following this construct?
    //assertNull(ObjectUtils.firstNonNull())
    // Cast to Object in line below ensures compiler doesn't complain of unchecked generic array creation
    assertNull(ObjectUtils.firstNonNull(null, null))
    assertNull(ObjectUtils.firstNonNull(null.asInstanceOf[Any]))
    assertNull(ObjectUtils.firstNonNull(null.asInstanceOf[Array[AnyRef]]))
  }

  @Test def testGetFirstNonNull(): Unit = { // first non null
    assertEquals(
      "",
      ObjectUtils.getFirstNonNull(
        Array[Supplier[String]](
          new Supplier[String] {
            override def get(): String = null
          },
          new Supplier[String] {
            override def get(): String = ""
          }
        ): _*)
    )
    // first encountered value is used
    assertEquals(
      "1",
      ObjectUtils.getFirstNonNull(
        Array[Supplier[String]](
          new Supplier[String] {
            override def get(): String = null
          },
          new Supplier[String] {
            override def get(): String = "1"
          },
          new Supplier[String] {
            override def get(): String = "2"
          },
          new Supplier[String] {
            override def get(): String = null
          }
        ): _*)
    )

    assertEquals(
      "123",
      ObjectUtils.getFirstNonNull(
        Array[Supplier[String]](
          new Supplier[String] {
            override def get(): String = {
              "123"
            }
          },
          new Supplier[String] {
            override def get(): String = {
              "456"
            }
          }
        ): _*)
    )
    // don't evaluate suppliers after first value is found
    assertEquals(
      "123",
      ObjectUtils.getFirstNonNull(
        Array[Supplier[String]](
          new Supplier[String] {
            override def get(): String = null
          },
          new Supplier[String] {
            override def get(): String = "123"
          },
          new Supplier[String] {
            override def get(): String = fail("Supplier after first non-null value should not be evaluated")
          }
        ): _*
      )
    )
    // supplier returning null and null supplier both result in null
    assertNull(
      ObjectUtils.getFirstNonNull(
        null,
        new Supplier[String] {
          override def get(): String = null
        }
      ))
    // TODO: "dead code following this construct?
    //assertNull(ObjectUtils.getFirstNonNull())
    // supplier is null
    assertNull(ObjectUtils.getFirstNonNull(null.asInstanceOf[Supplier[AnyRef]]))
    // varargs array itself is null
    // TODO: Scala equivalent?
    //assertNull(ObjectUtils.getFirstNonNull(null.asInstanceOf[Array[Supplier[AnyRef]]]))
    // test different types
    assertEquals(
      Integer.valueOf(1),
      ObjectUtils.getFirstNonNull(
        Array[Supplier[Integer]](
          new Supplier[Integer] {
            override def get(): Integer = {
              null
            }
          },
          new Supplier[Integer] {
            override def get(): Integer = {
              1
            }
          }
        ): _*)
    )
    assertEquals(
      JavaBoolean.TRUE,
      ObjectUtils.getFirstNonNull(
        Array[Supplier[JavaBoolean]](
          new Supplier[JavaBoolean] {
            override def get(): JavaBoolean = null
          },
          new Supplier[JavaBoolean] {
            override def get(): JavaBoolean = JavaBoolean.TRUE
          }
        ): _*)
    )
  }

  /**
    * Tests {@link ObjectUtils#anyNull}.
    */
  @Test def testAnyNull(): Unit = {
    assertTrue(ObjectUtils.anyNull(null.asInstanceOf[Any]))
    assertTrue(ObjectUtils.anyNull(null, null, null))
    assertTrue(ObjectUtils.anyNull(null, ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
    assertTrue(ObjectUtils.anyNull(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR, null))
    assertTrue(
      ObjectUtils.anyNull(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR, null, ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
    assertFalse(ObjectUtils.anyNull())
    assertFalse(ObjectUtils.anyNull(ObjectUtilsTest.FOO))
    assertFalse(
      ObjectUtils.anyNull(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR, 1, JavaBoolean.TRUE, new AnyRef, Array[AnyRef]()))
  }

  /**
    * Tests {@link ObjectUtils#anyNotNull}.
    */
  @Test def testAnyNotNull(): Unit = {
    assertFalse(ObjectUtils.anyNotNull())
    assertFalse(ObjectUtils.anyNotNull(null.asInstanceOf[Any]))
    assertFalse(ObjectUtils.anyNotNull(null.asInstanceOf[Array[AnyRef]]))
    assertFalse(ObjectUtils.anyNotNull(null, null, null))
    assertTrue(ObjectUtils.anyNotNull(ObjectUtilsTest.FOO))
    assertTrue(ObjectUtils.anyNotNull(null, ObjectUtilsTest.FOO, null))
    assertTrue(ObjectUtils.anyNotNull(null, null, null, null, ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
  }

  /**
    * Tests {@link ObjectUtils#allNull}.
    */
  @Test def testAllNull(): Unit = {
    assertTrue(ObjectUtils.allNull())
    assertTrue(ObjectUtils.allNull(null.asInstanceOf[Any]))
    assertTrue(ObjectUtils.allNull(null.asInstanceOf[Array[AnyRef]]))
    assertTrue(ObjectUtils.allNull(null, null, null))
    assertFalse(ObjectUtils.allNull(ObjectUtilsTest.FOO))
    assertFalse(ObjectUtils.allNull(null, ObjectUtilsTest.FOO, null))
    assertFalse(ObjectUtils.allNull(null, null, null, null, ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
  }

  /**
    * Tests {@link ObjectUtils#allNotNull}.
    */
  @Test def testAllNotNull(): Unit = {
    assertFalse(ObjectUtils.allNotNull(null.asInstanceOf[Any]))
    assertFalse(ObjectUtils.allNotNull(null.asInstanceOf[Array[AnyRef]]))
    assertFalse(ObjectUtils.allNotNull(null, null, null))
    assertFalse(ObjectUtils.allNotNull(null, ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
    assertFalse(ObjectUtils.allNotNull(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR, null))
    assertFalse(
      ObjectUtils.allNotNull(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR, null, ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
    assertTrue(ObjectUtils.allNotNull())
    assertTrue(ObjectUtils.allNotNull(ObjectUtilsTest.FOO))
    assertTrue(
      ObjectUtils
        .allNotNull(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR, 1, JavaBoolean.TRUE, new AnyRef, Array[AnyRef]()))
  }

  @Test def testEquals(): Unit = {
    assertTrue("ObjectUtils.equals(null, null) returned false", ObjectUtils.equals(null, null))
    assertTrue("ObjectUtils.equals(\"foo\", null) returned true", !ObjectUtils.equals(ObjectUtilsTest.FOO, null))
    assertTrue("ObjectUtils.equals(null, \"bar\") returned true", !ObjectUtils.equals(null, ObjectUtilsTest.BAR))
    assertTrue(
      "ObjectUtils.equals(\"foo\", \"bar\") returned true",
      !ObjectUtils.equals(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
    assertTrue(
      "ObjectUtils.equals(\"foo\", \"foo\") returned false",
      ObjectUtils.equals(ObjectUtilsTest.FOO, ObjectUtilsTest.FOO))
  }

  @Test def testNotEqual(): Unit = {
    assertFalse("ObjectUtils.notEqual(null, null) returned false", ObjectUtils.notEqual(null, null))
    assertTrue("ObjectUtils.notEqual(\"foo\", null) returned true", ObjectUtils.notEqual(ObjectUtilsTest.FOO, null))
    assertTrue("ObjectUtils.notEqual(null, \"bar\") returned true", ObjectUtils.notEqual(null, ObjectUtilsTest.BAR))
    assertTrue(
      "ObjectUtils.notEqual(\"foo\", \"bar\") returned true",
      ObjectUtils.notEqual(ObjectUtilsTest.FOO, ObjectUtilsTest.BAR))
    assertFalse(
      "ObjectUtils.notEqual(\"foo\", \"foo\") returned false",
      ObjectUtils.notEqual(ObjectUtilsTest.FOO, ObjectUtilsTest.FOO))
  }

  @Test def testHashCode(): Unit = {
    assertEquals(0, ObjectUtils.hashCode(null))
    assertEquals("a".hashCode, ObjectUtils.hashCode("a"))
  }

  @Test def testHashCodeMulti_multiple_emptyArray(): Unit = {
    val array = new Array[AnyRef](0)
    assertEquals(1, ObjectUtils.hashCodeMulti(array))
  }

  @Test def testHashCodeMulti_multiple_nullArray(): Unit = {
    val array: Array[Any] = null
    assertEquals(1, ObjectUtils.hashCodeMulti(array))
  }

  @Test def testHashCodeMulti_multiple_likeList(): Unit = {
    val list0 = new util.ArrayList[AnyRef](Collections.emptyList)
    assertEquals(list0.hashCode, ObjectUtils.hashCodeMulti())
    val list1 = new util.ArrayList[AnyRef](Collections.singletonList("a"))
    assertEquals(list1.hashCode, ObjectUtils.hashCodeMulti("a"))
    val list2 = new util.ArrayList[AnyRef](util.Arrays.asList("a", "b"))
    assertEquals(list2.hashCode, ObjectUtils.hashCodeMulti("a", "b"))
    val list3 = new util.ArrayList[AnyRef](util.Arrays.asList("a", "b", "c"))
    assertEquals(list3.hashCode, ObjectUtils.hashCodeMulti("a", "b", "c"))
  }

  @Test def testIdentityToStringStringBuffer(): Unit = {
    val i = Integer.valueOf(45)
    val expected = "java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i))
    val buffer = new StringBuffer
    ObjectUtils.identityToString(buffer, i)
    assertEquals(expected, buffer.toString)
    assertThrows[NullPointerException](ObjectUtils.identityToString(null.asInstanceOf[StringBuffer], "tmp"))
    assertThrows[NullPointerException](ObjectUtils.identityToString(new StringBuffer, null))
    ()
  }

  @Test def testIdentityToStringObjectNull(): Unit = {
    assertNull(ObjectUtils.identityToString(null))
  }

  @Test def testIdentityToStringInteger(): Unit = {
    val i = Integer.valueOf(90)
    val expected = "java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i))
    assertEquals(expected, ObjectUtils.identityToString(i))
  }

  @Test def testIdentityToStringString(): Unit = {
    assertEquals(
      "java.lang.String@" + Integer.toHexString(System.identityHashCode(ObjectUtilsTest.FOO)),
      ObjectUtils.identityToString(ObjectUtilsTest.FOO)
    )
  }

  @Test def testIdentityToStringStringBuilder(): Unit = {
    val i = Integer.valueOf(90)
    val expected = "java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i))
    val builder = new StringBuilder
    ObjectUtils.identityToString(builder, i)
    assertEquals(expected, builder.toString)
  }

  @Test def testIdentityToStringStringBuilderInUse(): Unit = {
    val i = Integer.valueOf(90)
    val expected = "ABC = java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i))
    val builder = new StringBuilder("ABC = ")
    ObjectUtils.identityToString(builder, i)
    assertEquals(expected, builder.toString)
  }

  @Test def testIdentityToStringStringBuilderNullValue(): Unit = {
    assertThrows[NullPointerException](ObjectUtils.identityToString(new StringBuilder, null))
    ()
  }

  @Test def testIdentityToStringStringBuilderNullStringBuilder(): Unit = {
    assertThrows[NullPointerException](ObjectUtils.identityToString(null.asInstanceOf[StringBuilder], "tmp"))
    ()
  }

  @Test def testIdentityToStringStrBuilder(): Unit = {
    val i = Integer.valueOf(102)
    val expected = "java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i))
    val builder = new StrBuilder
    ObjectUtils.identityToString(builder, i)
    assertEquals(expected, builder.toString)
    assertThrows[NullPointerException](ObjectUtils.identityToString(null.asInstanceOf[StrBuilder], "tmp"))
    assertThrows[NullPointerException](ObjectUtils.identityToString(new StrBuilder, null))
    ()
  }

  @Test
  @throws[IOException]
  def testIdentityToStringAppendable(): Unit = {
    val i = Integer.valueOf(121)
    val expected = "java.lang.Integer@" + Integer.toHexString(System.identityHashCode(i))
    val appendable = new StringBuilder
    ObjectUtils.identityToString(appendable, i)
    assertEquals(expected, appendable.toString)
    assertThrows[NullPointerException](ObjectUtils.identityToString(null.asInstanceOf[Appendable], "tmp"))
    assertThrows[NullPointerException](
      ObjectUtils.identityToString((new java.lang.StringBuilder).asInstanceOf[Appendable], null))
    ()
  }

  @Test def testToString_Object(): Unit = {
    assertEquals("", ObjectUtils.toString(null))
    assertEquals(JavaBoolean.TRUE.toString, ObjectUtils.toString(JavaBoolean.TRUE))
  }

  @Test def testToString_ObjectString(): Unit = {
    assertEquals(ObjectUtilsTest.BAR, ObjectUtils.toString(null, ObjectUtilsTest.BAR))
    assertEquals(JavaBoolean.TRUE.toString, ObjectUtils.toString(JavaBoolean.TRUE, ObjectUtilsTest.BAR))
  }

  @Test def testToString_SupplierString(): Unit = {
    assertEquals(null, ObjectUtils.toString(null, null.asInstanceOf[Supplier[String]]))
    assertEquals(
      null,
      ObjectUtils.toString(
        null,
        new Supplier[String] {
          override def get(): String = null
        }
      ))
    // Pretend computing BAR is expensive.
    assertEquals(
      ObjectUtilsTest.BAR,
      ObjectUtils.toString(
        null,
        new Supplier[String] {
          override def get(): String = ObjectUtilsTest.BAR
        }
      ))
    assertEquals(
      JavaBoolean.TRUE.toString,
      ObjectUtils.toString(
        JavaBoolean.TRUE,
        new Supplier[String] {
          override def get(): String = ObjectUtilsTest.BAR
        }
      ))
  }

  @SuppressWarnings(Array("cast")) // 1 OK, because we are checking for code change
  @Test def testNull(): Unit = {
    assertNotNull(ObjectUtils.NULL)
    // 1 Check that NULL really is a Null i.e. the definition has not been changed
    assertTrue(ObjectUtils.NULL.isInstanceOf[ObjectUtils.Null])
    assertSame(ObjectUtils.NULL, SerializationUtils.clone(ObjectUtils.NULL))
  }

  @Test def testMax(): Unit = {
    val calendar: Calendar = Calendar.getInstance
    val nonNullComparable1: Date = calendar.getTime
    val nonNullComparable2: Date = calendar.getTime
    val nullArray: Array[String] = null
    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
    val minComparable: Date = calendar.getTime

    assertNotSame(nonNullComparable1, nonNullComparable2)
    assertNull(ObjectUtils.max(null.asInstanceOf[String]))
    assertNull(ObjectUtils.max(nullArray))
    assertSame(nonNullComparable1, ObjectUtils.max(null, nonNullComparable1))
    assertSame(nonNullComparable1, ObjectUtils.max(nonNullComparable1, null))
    assertSame(nonNullComparable1, ObjectUtils.max(null, nonNullComparable1, null))
    assertSame(nonNullComparable1, ObjectUtils.max(nonNullComparable1, nonNullComparable2))
    assertSame(nonNullComparable2, ObjectUtils.max(nonNullComparable2, nonNullComparable1))
    assertSame(nonNullComparable1, ObjectUtils.max(nonNullComparable1, minComparable))
    assertSame(nonNullComparable1, ObjectUtils.max(minComparable, nonNullComparable1))
    assertSame(nonNullComparable1, ObjectUtils.max(null, minComparable, null, nonNullComparable1))
    assertNull(ObjectUtils.max(null, null))
  }

  @Test def testMin(): Unit = {
    val calendar: Calendar = Calendar.getInstance
    val nonNullComparable1: Date = calendar.getTime
    val nonNullComparable2: Date = calendar.getTime
    val nullArray: Array[String] = null
    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1)
    val minComparable: Date = calendar.getTime
    assertNotSame(nonNullComparable1, nonNullComparable2)
    assertNull(ObjectUtils.min(null.asInstanceOf[String]))
    assertNull(ObjectUtils.min(nullArray))
    assertSame(nonNullComparable1, ObjectUtils.min(null, nonNullComparable1))
    assertSame(nonNullComparable1, ObjectUtils.min(nonNullComparable1, null))
    assertSame(nonNullComparable1, ObjectUtils.min(null, nonNullComparable1, null))
    assertSame(nonNullComparable1, ObjectUtils.min(nonNullComparable1, nonNullComparable2))
    assertSame(nonNullComparable2, ObjectUtils.min(nonNullComparable2, nonNullComparable1))
    assertSame(minComparable, ObjectUtils.min(nonNullComparable1, minComparable))
    assertSame(minComparable, ObjectUtils.min(minComparable, nonNullComparable1))
    assertSame(minComparable, ObjectUtils.min(null, nonNullComparable1, null, minComparable))
    assertNull(ObjectUtils.min(null, null))
  }

  /**
    * Tests {@link ObjectUtils#compare}.
    */
  @Test def testCompare(): Unit = {
    val one: Integer = Integer.valueOf(1)
    val two: Integer = Integer.valueOf(2)
    val nullValue: Integer = null

    assertEquals("Null Null false", 0, ObjectUtils.compare(nullValue, nullValue))
    assertEquals("Null Null true", 0, ObjectUtils.compare(nullValue, nullValue, true))
    assertEquals("Null one false", -1, ObjectUtils.compare(nullValue, one))
    assertEquals("Null one true", 1, ObjectUtils.compare(nullValue, one, true))
    assertEquals("one Null false", 1, ObjectUtils.compare(one, nullValue))
    assertEquals("one Null true", -1, ObjectUtils.compare(one, nullValue, true))
    assertEquals("one two false", -1, ObjectUtils.compare(one, two))
    assertEquals("one two true", -1, ObjectUtils.compare(one, two, true))
  }

  @Test def testMedian(): Unit = {
    assertEquals("foo", ObjectUtils.median("foo"))
    assertEquals("bar", ObjectUtils.median("foo", "bar"))
    assertEquals("baz", ObjectUtils.median("foo", "bar", "baz"))
    assertEquals("baz", ObjectUtils.median("foo", "bar", "baz", "blah"))
    assertEquals("blah", ObjectUtils.median("foo", "bar", "baz", "blah", "wah"))
    assertEquals(Integer.valueOf(5), ObjectUtils.median(Integer.valueOf(1), Integer.valueOf(5), Integer.valueOf(10)))
    assertEquals(
      Integer.valueOf(7),
      ObjectUtils
        .median(Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9))
    )
    assertEquals(
      Integer.valueOf(6),
      ObjectUtils.median(Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8))
    )
  }

  @Test def testMedian_nullItems(): Unit = {
    assertThrows[NullPointerException](ObjectUtils.median(null.asInstanceOf[Array[String]]))
    ()
  }

  @Test def testMedian_emptyItems(): Unit = {
    assertThrows[IllegalArgumentException](ObjectUtils.median[String]())
    ()
  }

  @Test def testComparatorMedian(): Unit = {
    val cmp: ObjectUtilsTest.CharSequenceComparator = new ObjectUtilsTest.CharSequenceComparator
    val foo: ObjectUtilsTest.NonComparableCharSequence = new ObjectUtilsTest.NonComparableCharSequence("foo")
    val bar: ObjectUtilsTest.NonComparableCharSequence = new ObjectUtilsTest.NonComparableCharSequence("bar")
    val baz: ObjectUtilsTest.NonComparableCharSequence = new ObjectUtilsTest.NonComparableCharSequence("baz")
    val blah: ObjectUtilsTest.NonComparableCharSequence = new ObjectUtilsTest.NonComparableCharSequence("blah")
    val wah: ObjectUtilsTest.NonComparableCharSequence = new ObjectUtilsTest.NonComparableCharSequence("wah")
    assertSame(foo, ObjectUtils.median(cmp, foo))
    assertSame(bar, ObjectUtils.median(cmp, foo, bar))
    assertSame(baz, ObjectUtils.median(cmp, foo, bar, baz))
    assertSame(baz, ObjectUtils.median(cmp, foo, bar, baz, blah))
    assertSame(blah, ObjectUtils.median(cmp, foo, bar, baz, blah, wah))
  }

  @Test def testComparatorMedian_nullComparator(): Unit = {
    assertThrows[NullPointerException](
      ObjectUtils
        .median(null.asInstanceOf[Comparator[CharSequence]], new ObjectUtilsTest.NonComparableCharSequence("foo")))
    ()
  }

  @Test def testComparatorMedian_nullItems(): Unit = {
    assertThrows[NullPointerException](
      ObjectUtils.median(new ObjectUtilsTest.CharSequenceComparator, null.asInstanceOf[Array[CharSequence]]))
    ()
  }

  @Test def testComparatorMedian_emptyItems(): Unit = {
    assertThrows[IllegalArgumentException](ObjectUtils.median(new ObjectUtilsTest.CharSequenceComparator))
    ()
  }

  @Test def testMode(): Unit = {
    assertNull(ObjectUtils.mode(null.asInstanceOf[Array[AnyRef]]))
    // TODO: "dead code following this construct?
    //assertNull(ObjectUtils.mode())
    assertNull(ObjectUtils.mode("foo", "bar", "baz"))
    assertNull(ObjectUtils.mode("foo", "bar", "baz", "foo", "bar"))
    assertEquals("foo", ObjectUtils.mode("foo", "bar", "baz", "foo"))
    assertEquals(
      Integer.valueOf(9),
      ObjectUtils.mode("foo", "bar", "baz", Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(9)))
  }

  /**
    * Tests {@link ObjectUtils#clone} with a cloneable object.
    */
  @Test def testCloneOfCloneable(): Unit = {
    val string: ObjectUtilsTest.CloneableString = new ObjectUtilsTest.CloneableString("apache")
    val stringClone: ObjectUtilsTest.CloneableString = ObjectUtils.clone(string)
    assertEquals("apache", stringClone.getValue)
  }

  /**
    * Tests {@link ObjectUtils#clone} with a not cloneable object.
    */
  @Test def testCloneOfNotCloneable(): Unit = {
    val string: String = new String("apache")
    assertNull(ObjectUtils.clone(string))
  }

//  /**
//    * Tests {@link ObjectUtils#clone} with an uncloneable object.
//    */
//  @Test def testCloneOfUncloneable(): Unit = {
//    val string: ObjectUtilsTest.UncloneableString = new ObjectUtilsTest.UncloneableString("apache")
//    val e: CloneFailedException = org.junit.Assert.assertThrows(
//      classOf[CloneFailedException],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ObjectUtils.clone(string)
//          ()
//        }
//      }
//    )
//
//    assertEquals(classOf[NoSuchMethodException], e.getCause.getClass)
//  }

  /**
    * Tests {@link ObjectUtils#clone} with an object array.
    */
  @Test def testCloneOfStringArray(): Unit = {
    assertTrue(
      util.Arrays.deepEquals(
        Array[String]("string").asInstanceOf[Array[Object]],
        ObjectUtils.clone(Array[String]("string")).asInstanceOf[Array[Object]]
      ))
  }

  /**
    * Tests {@link ObjectUtils#clone} with an array of primitives.
    */
  @Test def testCloneOfPrimitiveArray(): Unit = {
    assertArrayEquals(Array[Int](1), ObjectUtils.clone(Array[Int](1)))
  }

  /**
    * Tests {@link ObjectUtils#cloneIfPossible} with a cloneable object.
    */
  @Test def testPossibleCloneOfCloneable(): Unit = {
    val string: ObjectUtilsTest.CloneableString = new ObjectUtilsTest.CloneableString("apache")
    val stringClone: ObjectUtilsTest.CloneableString = ObjectUtils.cloneIfPossible(string)
    assertEquals("apache", stringClone.getValue)
  }

  /**
    * Tests {@link ObjectUtils#cloneIfPossible} with a not cloneable object.
    */
  @Test def testPossibleCloneOfNotCloneable(): Unit = {
    val string: String = new String("apache")
    assertSame(string, ObjectUtils.cloneIfPossible(string))
  }

//  /**
//    * Tests {@link ObjectUtils#cloneIfPossible} with an uncloneable object.
//    */
//  @Test def testPossibleCloneOfUncloneable(): Unit = {
//    val string: ObjectUtilsTest.UncloneableString = new ObjectUtilsTest.UncloneableString("apache")
//    val e: CloneFailedException = org.junit.Assert.assertThrows(
//      classOf[CloneFailedException],
//      new ThrowingRunnable() {
//        @throws[Throwable]
//        override def run(): Unit = {
//          ObjectUtils.cloneIfPossible(string)
//          ()
//        }
//      })
//
//    assertEquals(classOf[NoSuchMethodException], e.getCause.getClass)
//  }

  @Test def testConstMethods(): Unit = { // To truly test the CONST() method, we'd want to look in the
    // bytecode to see if the literals were folded into the
    // class, or if the bytecode kept the method call.
    assertTrue("CONST(boolean)", ObjectUtils.CONST(true))
    assertEquals("CONST(byte)", 3.toByte, ObjectUtils.CONST(3.toByte))
    assertEquals("CONST(char)", 3.toChar, ObjectUtils.CONST(3.toChar))
    assertEquals("CONST(short)", 3.toShort, ObjectUtils.CONST(3.toShort))
    assertEquals("CONST(int)", 3, ObjectUtils.CONST(3))
    assertEquals("CONST(long)", 3L, ObjectUtils.CONST(3L))
    assertEquals("CONST(float)", 3f, ObjectUtils.CONST(3f), FloatDelta)
    assertEquals("CONST(double)", 3.0d, ObjectUtils.CONST(3.0d), DoubleDelta)
    assertEquals("CONST(Object)", "abc", ObjectUtils.CONST("abc"))

    // Make sure documentation examples from Javadoc all work
    // (this fixed a lot of my bugs when I these!)
    //
    // My bugs should be in a software engineering textbook
    // for "Can you screw this up?"  The answer is, yes,
    // you can even screw this up.  (When you == Julius)
    // .
    val MAGIC_FLAG: Boolean = ObjectUtils.CONST(true)
    val MAGIC_BYTE1: Byte = ObjectUtils.CONST(127.toByte)
    val MAGIC_BYTE2: Byte = ObjectUtils.CONST_BYTE(127)
    val MAGIC_CHAR: Char = ObjectUtils.CONST('a')
    val MAGIC_SHORT1: Short = ObjectUtils.CONST(123.toShort)
    val MAGIC_SHORT2: Short = ObjectUtils.CONST_SHORT(127)
    val MAGIC_INT: Int = ObjectUtils.CONST(123)
    val MAGIC_LONG1: Long = ObjectUtils.CONST(123L)
    val MAGIC_LONG2: Long = ObjectUtils.CONST(3)
    val MAGIC_FLOAT: Float = ObjectUtils.CONST(1.0f)
    val MAGIC_DOUBLE: Double = ObjectUtils.CONST(1.0d)
    val MAGIC_STRING: String = ObjectUtils.CONST("abc")

    assertTrue(MAGIC_FLAG)

    assertEquals(127, MAGIC_BYTE1)
    assertEquals(127, MAGIC_BYTE2)
    assertEquals('a', MAGIC_CHAR)
    assertEquals(123, MAGIC_SHORT1)
    assertEquals(127, MAGIC_SHORT2)
    assertEquals(123, MAGIC_INT)
    assertEquals(123, MAGIC_LONG1)
    assertEquals(3, MAGIC_LONG2)
    assertEquals(1.0f, MAGIC_FLOAT, FloatDelta)
    assertEquals(1.0d, MAGIC_DOUBLE, DoubleDelta)
    assertEquals("abc", MAGIC_STRING)

    assertThrows[IllegalArgumentException](ObjectUtils.CONST_BYTE(-129))
    //, "CONST_BYTE(-129): IllegalArgumentException should have been thrown.")
    assertThrows[IllegalArgumentException](ObjectUtils.CONST_BYTE(128))
    //, "CONST_BYTE(128): IllegalArgumentException should have been thrown.")
    assertThrows[IllegalArgumentException](ObjectUtils.CONST_SHORT(-32769))
    //, "CONST_SHORT(-32769): IllegalArgumentException should have been thrown.")
    assertThrows[IllegalArgumentException](ObjectUtils.CONST_BYTE(32768))
    //, "CONST_SHORT(32768): IllegalArgumentException should have been thrown.")
    ()
  }
}
