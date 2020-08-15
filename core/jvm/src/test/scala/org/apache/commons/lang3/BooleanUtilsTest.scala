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

import java.lang.{Boolean => JavaBoolean}
import org.junit.Assert._
import org.junit.Test
import org.scalatestplus.junit.JUnitSuite

/**
  * Unit tests {@link org.apache.commons.lang3.BooleanUtils}.
  */
class BooleanUtilsTest extends JUnitSuite {

//  @Test def testConstructor(): Unit = {
//    assertNotNull(new BooleanUtils.type)
//    val cons = classOf[BooleanUtils.type].getDeclaredConstructors
//    assertEquals(1, cons.length)
//    assertTrue(Modifier.isPublic(cons(0).getModifiers))
//    assertTrue(Modifier.isPublic(classOf[BooleanUtils.type].getModifiers))
//    assertFalse(Modifier.isFinal(classOf[BooleanUtils.type].getModifiers))
//  }

  @Test def test_negate_Boolean(): Unit = {
    assertSame(null, BooleanUtils.negate(null))
    assertSame(JavaBoolean.TRUE, BooleanUtils.negate(JavaBoolean.FALSE))
    assertSame(JavaBoolean.FALSE, BooleanUtils.negate(JavaBoolean.TRUE))
  }

  @Test def test_isTrue_Boolean(): Unit = {
    assertTrue(BooleanUtils.isTrue(JavaBoolean.TRUE))
    assertFalse(BooleanUtils.isTrue(JavaBoolean.FALSE))
    assertFalse(BooleanUtils.isTrue(null))
  }

  @Test def test_isNotTrue_Boolean(): Unit = {
    assertFalse(BooleanUtils.isNotTrue(JavaBoolean.TRUE))
    assertTrue(BooleanUtils.isNotTrue(JavaBoolean.FALSE))
    assertTrue(BooleanUtils.isNotTrue(null))
  }

  @Test def test_isFalse_Boolean(): Unit = {
    assertFalse(BooleanUtils.isFalse(JavaBoolean.TRUE))
    assertTrue(BooleanUtils.isFalse(JavaBoolean.FALSE))
    assertFalse(BooleanUtils.isFalse(null))
  }

  @Test def test_isNotFalse_Boolean(): Unit = {
    assertTrue(BooleanUtils.isNotFalse(JavaBoolean.TRUE))
    assertFalse(BooleanUtils.isNotFalse(JavaBoolean.FALSE))
    assertTrue(BooleanUtils.isNotFalse(null))
  }

  @Test def test_toBoolean_Boolean(): Unit = {
    assertTrue(BooleanUtils.toBoolean(JavaBoolean.TRUE))
    assertFalse(BooleanUtils.toBoolean(JavaBoolean.FALSE))
    assertFalse(BooleanUtils.toBoolean(null.asInstanceOf[Boolean]))
  }

  @Test def test_toBooleanDefaultIfNull_Boolean_boolean(): Unit = {
    assertTrue(BooleanUtils.toBooleanDefaultIfNull(JavaBoolean.TRUE, true))
    assertTrue(BooleanUtils.toBooleanDefaultIfNull(JavaBoolean.TRUE, false))
    assertFalse(BooleanUtils.toBooleanDefaultIfNull(JavaBoolean.FALSE, true))
    assertFalse(BooleanUtils.toBooleanDefaultIfNull(JavaBoolean.FALSE, false))
    assertTrue(BooleanUtils.toBooleanDefaultIfNull(null, true))
    assertFalse(BooleanUtils.toBooleanDefaultIfNull(null, false))
  }

  @Test def test_toBoolean_int(): Unit = {
    assertTrue(BooleanUtils.toBoolean(1))
    assertTrue(BooleanUtils.toBoolean(-1))
    assertFalse(BooleanUtils.toBoolean(0))
  }

  @Test def test_toBooleanObject_int(): Unit = {
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(1))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(-1))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject(0))
  }

  @Test def test_toBooleanObject_Integer(): Unit = {
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(Integer.valueOf(1)))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(Integer.valueOf(-1)))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject(Integer.valueOf(0)))
    assertNull(BooleanUtils.toBooleanObject(null.asInstanceOf[Integer]))
  }

  @Test def test_toBoolean_int_int_int(): Unit = {
    assertTrue(BooleanUtils.toBoolean(6, 6, 7))
    assertFalse(BooleanUtils.toBoolean(7, 6, 7))
  }

  @Test def test_toBoolean_int_int_int_noMatch(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBoolean(8, 6, 7))
    ()
  }

  @Test def test_toBoolean_Integer_Integer_Integer(): Unit = {
    val six = Integer.valueOf(6)
    val seven = Integer.valueOf(7)
    assertTrue(BooleanUtils.toBoolean(null, null, seven))
    assertFalse(BooleanUtils.toBoolean(null, six, null))
    assertTrue(BooleanUtils.toBoolean(Integer.valueOf(6), six, seven))
    assertFalse(BooleanUtils.toBoolean(Integer.valueOf(7), six, seven))
  }

  @Test def test_toBoolean_Integer_Integer_Integer_nullValue(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBoolean(null, Integer.valueOf(6), Integer.valueOf(7)))
    ()
  }

  @Test def test_toBoolean_Integer_Integer_Integer_noMatch(): Unit = {
    assertThrows[IllegalArgumentException](
      BooleanUtils.toBoolean(Integer.valueOf(8), Integer.valueOf(6), Integer.valueOf(7)))
    ()
  }

  @Test def test_toBooleanObject_int_int_int(): Unit = {
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(6, 6, 7, 8))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject(7, 6, 7, 8))
    assertNull(BooleanUtils.toBooleanObject(8, 6, 7, 8))
  }

  @Test def test_toBooleanObject_int_int_int_noMatch(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBooleanObject(9, 6, 7, 8))
    ()
  }

  @Test def test_toBooleanObject_Integer_Integer_Integer_Integer(): Unit = {
    val six = Integer.valueOf(6)
    val seven = Integer.valueOf(7)
    val eight = Integer.valueOf(8)
    assertSame(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(null, null, seven, eight))
    assertSame(JavaBoolean.FALSE, BooleanUtils.toBooleanObject(null, six, null, eight))
    assertSame(null, BooleanUtils.toBooleanObject(null, six, seven, null))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(Integer.valueOf(6), six, seven, eight))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject(Integer.valueOf(7), six, seven, eight))
    assertNull(BooleanUtils.toBooleanObject(Integer.valueOf(8), six, seven, eight))
  }

  @Test def test_toBooleanObject_Integer_Integer_Integer_Integer_nullValue(): Unit = {
    assertThrows[IllegalArgumentException](
      BooleanUtils.toBooleanObject(null, Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8)))
    ()
  }

  @Test def test_toBooleanObject_Integer_Integer_Integer_Integer_noMatch(): Unit = {
    assertThrows[IllegalArgumentException](
      BooleanUtils.toBooleanObject(Integer.valueOf(9), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8)))
    ()
  }

  @Test def test_toInteger_boolean(): Unit = {
    assertEquals(1, BooleanUtils.toInteger(true))
    assertEquals(0, BooleanUtils.toInteger(false))
  }

  @Test def test_toIntegerObject_boolean(): Unit = {
    assertEquals(Integer.valueOf(1), BooleanUtils.toIntegerObject(true))
    assertEquals(Integer.valueOf(0), BooleanUtils.toIntegerObject(false))
  }

  @Test def test_toIntegerObject_Boolean(): Unit = {
    assertEquals(Integer.valueOf(1), BooleanUtils.toIntegerObject(JavaBoolean.TRUE))
    assertEquals(Integer.valueOf(0), BooleanUtils.toIntegerObject(JavaBoolean.FALSE))
    assertNull(BooleanUtils.toIntegerObject(null))
  }

  @Test def test_toInteger_boolean_int_int(): Unit = {
    assertEquals(6, BooleanUtils.toInteger(true, 6, 7))
    assertEquals(7, BooleanUtils.toInteger(false, 6, 7))
  }

  @Test def test_toInteger_Boolean_int_int_int(): Unit = {
    assertEquals(6, BooleanUtils.toInteger(JavaBoolean.TRUE, 6, 7, 8))
    assertEquals(7, BooleanUtils.toInteger(JavaBoolean.FALSE, 6, 7, 8))
    assertEquals(8, BooleanUtils.toInteger(null, 6, 7, 8))
  }

  @Test def test_toIntegerObject_boolean_Integer_Integer(): Unit = {
    val six = Integer.valueOf(6)
    val seven = Integer.valueOf(7)
    assertEquals(six, BooleanUtils.toIntegerObject(true, six, seven))
    assertEquals(seven, BooleanUtils.toIntegerObject(false, six, seven))
  }

  @Test def test_toIntegerObject_Boolean_Integer_Integer_Integer(): Unit = {
    val six = Integer.valueOf(6)
    val seven = Integer.valueOf(7)
    val eight = Integer.valueOf(8)
    assertEquals(six, BooleanUtils.toIntegerObject(JavaBoolean.TRUE, six, seven, eight))
    assertEquals(seven, BooleanUtils.toIntegerObject(JavaBoolean.FALSE, six, seven, eight))
    assertEquals(eight, BooleanUtils.toIntegerObject(null, six, seven, eight))
    assertNull(BooleanUtils.toIntegerObject(null, six, seven, null))
  }

  @Test def test_toBooleanObject_String(): Unit = {
    assertNull(BooleanUtils.toBooleanObject(null.asInstanceOf[String]))
    assertNull(BooleanUtils.toBooleanObject(""))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("false"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("no"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("off"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("FALSE"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("NO"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("OFF"))
    assertNull(BooleanUtils.toBooleanObject("oof"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("true"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("yes"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("on"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("TRUE"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("ON"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("YES"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("TruE"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("TruE"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("y")) // yes
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("Y"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("t")) // true
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("T"))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("1"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("f")) // false
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("F"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("n")) // No
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("N"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("0"))
    assertNull(BooleanUtils.toBooleanObject("z"))
    assertNull(BooleanUtils.toBooleanObject("ab"))
    assertNull(BooleanUtils.toBooleanObject("yoo"))
    assertNull(BooleanUtils.toBooleanObject("true "))
    assertNull(BooleanUtils.toBooleanObject("ono"))
  }

  @Test def test_toBooleanObject_String_String_String_String(): Unit = {
    assertSame(JavaBoolean.TRUE, BooleanUtils.toBooleanObject(null, null, "N", "U"))
    assertSame(JavaBoolean.FALSE, BooleanUtils.toBooleanObject(null, "Y", null, "U"))
    assertSame(null, BooleanUtils.toBooleanObject(null, "Y", "N", null))
    assertEquals(JavaBoolean.TRUE, BooleanUtils.toBooleanObject("Y", "Y", "N", "U"))
    assertEquals(JavaBoolean.FALSE, BooleanUtils.toBooleanObject("N", "Y", "N", "U"))
    assertNull(BooleanUtils.toBooleanObject("U", "Y", "N", "U"))
  }

  @Test def test_toBooleanObject_String_String_String_String_nullValue(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBooleanObject(null, "Y", "N", "U"))
    ()
  }

  @Test def test_toBooleanObject_String_String_String_String_noMatch(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBooleanObject("X", "Y", "N", "U"))
    ()
  }

  @Test def test_toBoolean_String(): Unit = {
    assertFalse(BooleanUtils.toBoolean(null.asInstanceOf[String]))
    assertFalse(BooleanUtils.toBoolean(""))
    assertFalse(BooleanUtils.toBoolean("off"))
    assertFalse(BooleanUtils.toBoolean("oof"))
    assertFalse(BooleanUtils.toBoolean("yep"))
    assertFalse(BooleanUtils.toBoolean("trux"))
    assertFalse(BooleanUtils.toBoolean("false"))
    assertFalse(BooleanUtils.toBoolean("a"))
    assertTrue(BooleanUtils.toBoolean("true")) // interned handled differently
    assertTrue(BooleanUtils.toBoolean(new StringBuilder("tr").append("ue").toString))
    assertTrue(BooleanUtils.toBoolean("truE"))
    assertTrue(BooleanUtils.toBoolean("trUe"))
    assertTrue(BooleanUtils.toBoolean("trUE"))
    assertTrue(BooleanUtils.toBoolean("tRue"))
    assertTrue(BooleanUtils.toBoolean("tRuE"))
    assertTrue(BooleanUtils.toBoolean("tRUe"))
    assertTrue(BooleanUtils.toBoolean("tRUE"))
    assertTrue(BooleanUtils.toBoolean("TRUE"))
    assertTrue(BooleanUtils.toBoolean("TRUe"))
    assertTrue(BooleanUtils.toBoolean("TRuE"))
    assertTrue(BooleanUtils.toBoolean("TRue"))
    assertTrue(BooleanUtils.toBoolean("TrUE"))
    assertTrue(BooleanUtils.toBoolean("TrUe"))
    assertTrue(BooleanUtils.toBoolean("TruE"))
    assertTrue(BooleanUtils.toBoolean("True"))
    assertTrue(BooleanUtils.toBoolean("on"))
    assertTrue(BooleanUtils.toBoolean("oN"))
    assertTrue(BooleanUtils.toBoolean("On"))
    assertTrue(BooleanUtils.toBoolean("ON"))
    assertTrue(BooleanUtils.toBoolean("yes"))
    assertTrue(BooleanUtils.toBoolean("yeS"))
    assertTrue(BooleanUtils.toBoolean("yEs"))
    assertTrue(BooleanUtils.toBoolean("yES"))
    assertTrue(BooleanUtils.toBoolean("Yes"))
    assertTrue(BooleanUtils.toBoolean("YeS"))
    assertTrue(BooleanUtils.toBoolean("YEs"))
    assertTrue(BooleanUtils.toBoolean("YES"))
    assertTrue(BooleanUtils.toBoolean("1"))
    assertFalse(BooleanUtils.toBoolean("yes?"))
    assertFalse(BooleanUtils.toBoolean("0"))
    assertFalse(BooleanUtils.toBoolean("tru"))
    assertFalse(BooleanUtils.toBoolean("no"))
    assertFalse(BooleanUtils.toBoolean("off"))
    assertFalse(BooleanUtils.toBoolean("yoo"))
  }

  @Test def test_toBoolean_String_String_String(): Unit = {
    assertTrue(BooleanUtils.toBoolean(null, null, "N"))
    assertFalse(BooleanUtils.toBoolean(null, "Y", null))
    assertTrue(BooleanUtils.toBoolean("Y", "Y", "N"))
    assertTrue(BooleanUtils.toBoolean("Y", new String("Y"), new String("N")))
    assertFalse(BooleanUtils.toBoolean("N", "Y", "N"))
    assertFalse(BooleanUtils.toBoolean("N", new String("Y"), new String("N")))
    assertTrue(BooleanUtils.toBoolean(null.asInstanceOf[String], null, null))
    assertTrue(BooleanUtils.toBoolean("Y", "Y", "Y"))
    assertTrue(BooleanUtils.toBoolean("Y", new String("Y"), new String("Y")))
  }

  @Test def test_toBoolean_String_String_String_nullValue(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBoolean(null, "Y", "N"))
    ()
  }

  @Test def test_toBoolean_String_String_String_noMatch(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.toBoolean("X", "Y", "N"))
    ()
  }

  @Test def test_toStringTrueFalse_Boolean(): Unit = {
    assertNull(BooleanUtils.toStringTrueFalse(null))
    assertEquals("true", BooleanUtils.toStringTrueFalse(JavaBoolean.TRUE))
    assertEquals("false", BooleanUtils.toStringTrueFalse(JavaBoolean.FALSE))
  }

  @Test def test_toStringOnOff_Boolean(): Unit = {
    assertNull(BooleanUtils.toStringOnOff(null))
    assertEquals("on", BooleanUtils.toStringOnOff(JavaBoolean.TRUE))
    assertEquals("off", BooleanUtils.toStringOnOff(JavaBoolean.FALSE))
  }

  @Test def test_toStringYesNo_Boolean(): Unit = {
    assertNull(BooleanUtils.toStringYesNo(null))
    assertEquals("yes", BooleanUtils.toStringYesNo(JavaBoolean.TRUE))
    assertEquals("no", BooleanUtils.toStringYesNo(JavaBoolean.FALSE))
  }

  @Test def test_toString_Boolean_String_String_String(): Unit = {
    assertEquals("U", BooleanUtils.toString(null, "Y", "N", "U"))
    assertEquals("Y", BooleanUtils.toString(JavaBoolean.TRUE, "Y", "N", "U"))
    assertEquals("N", BooleanUtils.toString(JavaBoolean.FALSE, "Y", "N", "U"))
  }

  @Test def test_toStringTrueFalse_boolean(): Unit = {
    assertEquals("true", BooleanUtils.toStringTrueFalse(true))
    assertEquals("false", BooleanUtils.toStringTrueFalse(false))
  }

  @Test def test_toStringOnOff_boolean(): Unit = {
    assertEquals("on", BooleanUtils.toStringOnOff(true))
    assertEquals("off", BooleanUtils.toStringOnOff(false))
  }

  @Test def test_toStringYesNo_boolean(): Unit = {
    assertEquals("yes", BooleanUtils.toStringYesNo(true))
    assertEquals("no", BooleanUtils.toStringYesNo(false))
  }

  @Test def test_toString_boolean_String_String_String(): Unit = {
    assertEquals("Y", BooleanUtils.toString(true, "Y", "N"))
    assertEquals("N", BooleanUtils.toString(false, "Y", "N"))
  }

  //  testXor
  //  -----------------------------------------------------------------------
  @Test def testXor_primitive_nullInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.xor(null.asInstanceOf[Array[Boolean]]))
    ()
  }

  @Test def testXor_primitive_emptyInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.xor(Array[Boolean]()))
    ()
  }

  @Test def testXor_primitive_validInput_2items(): Unit = {
    assertEquals("true ^ true", true ^ true, BooleanUtils.xor(Array[Boolean](true, true)))
    assertEquals("false ^ false", false ^ false, BooleanUtils.xor(Array[Boolean](false, false)))
    assertEquals("true ^ false", true ^ false, BooleanUtils.xor(Array[Boolean](true, false)))
    assertEquals("false ^ true", false ^ true, BooleanUtils.xor(Array[Boolean](false, true)))
  }

  @Test def testXor_primitive_validInput_3items(): Unit = {
    assertEquals("false ^ false ^ false", false ^ false ^ false, BooleanUtils.xor(Array[Boolean](false, false, false)))
    assertEquals("false ^ false ^ true", false ^ false ^ true, BooleanUtils.xor(Array[Boolean](false, false, true)))
    assertEquals("false ^ true ^ false", false ^ true ^ false, BooleanUtils.xor(Array[Boolean](false, true, false)))
    assertEquals("false ^ true ^ true", false ^ true ^ true, BooleanUtils.xor(Array[Boolean](false, true, true)))
    assertEquals("true ^ false ^ false", true ^ false ^ false, BooleanUtils.xor(Array[Boolean](true, false, false)))
    assertEquals("true ^ false ^ true", true ^ false ^ true, BooleanUtils.xor(Array[Boolean](true, false, true)))
    assertEquals("true ^ true ^ false", true ^ true ^ false, BooleanUtils.xor(Array[Boolean](true, true, false)))
    assertEquals("true ^ true ^ true", true ^ true ^ true, BooleanUtils.xor(Array[Boolean](true, true, true)))
  }

  @Test def testXor_object_nullInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.xor(null.asInstanceOf[Array[JavaBoolean]]))
    ()
  }

  @Test def testXor_object_emptyInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.xor(Array[JavaBoolean]()))
    ()
  }

  @Test def testXor_object_nullElementInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.xor(Array[JavaBoolean](null)))
    ()
  }

  @Test def testXor_object_validInput_2items(): Unit = {
    assertEquals(
      "false ^ false",
      false ^ false,
      BooleanUtils.xor(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertEquals(
      "false ^ true",
      false ^ true,
      BooleanUtils.xor(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertEquals(
      "true ^ false",
      true ^ false,
      BooleanUtils.xor(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertEquals(
      "true ^ true",
      true ^ true,
      BooleanUtils.xor(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
  }

  @Test def testXor_object_validInput_3items(): Unit = {
    assertEquals(
      "false ^ false ^ false",
      false ^ false ^ false,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.FALSE, JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertEquals(
      "false ^ false ^ true",
      false ^ false ^ true,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.FALSE, JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertEquals(
      "false ^ true ^ false",
      false ^ true ^ false,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.FALSE, JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertEquals(
      "true ^ false ^ false",
      true ^ false ^ false,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertEquals(
      "true ^ false ^ true",
      true ^ false ^ true,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertEquals(
      "true ^ true ^ false",
      true ^ true ^ false,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.TRUE, JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertEquals(
      "false ^ true ^ true",
      false ^ true ^ true,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.FALSE, JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
    assertEquals(
      "true ^ true ^ true",
      true ^ true ^ true,
      BooleanUtils.xor(Array[Boolean](JavaBoolean.TRUE, JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
  }

  //  testAnd
  @Test def testAnd_primitive_nullInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.and(null.asInstanceOf[Array[Boolean]]))
    ()
  }

  @Test def testAnd_primitive_emptyInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.and(Array[Boolean]()))
    ()
  }

  @Test def testAnd_primitive_validInput_2items(): Unit = {
    assertTrue("False result for (true, true)", BooleanUtils.and(Array[Boolean](true, true)))
    assertTrue("True result for (false, false)", !BooleanUtils.and(Array[Boolean](false, false)))
    assertTrue("True result for (true, false)", !BooleanUtils.and(Array[Boolean](true, false)))
    assertTrue("True result for (false, true)", !BooleanUtils.and(Array[Boolean](false, true)))
  }

  @Test def testAnd_primitive_validInput_3items(): Unit = {
    assertTrue("True result for (false, false, true)", !BooleanUtils.and(Array[Boolean](false, false, true)))
    assertTrue("True result for (false, true, false)", !BooleanUtils.and(Array[Boolean](false, true, false)))
    assertTrue("True result for (true, false, false)", !BooleanUtils.and(Array[Boolean](true, false, false)))
    assertTrue("False result for (true, true, true)", BooleanUtils.and(Array[Boolean](true, true, true)))
    assertTrue("True result for (false, false)", !BooleanUtils.and(Array[Boolean](false, false, false)))
    assertTrue("True result for (true, true, false)", !BooleanUtils.and(Array[Boolean](true, true, false)))
    assertTrue("True result for (true, false, true)", !BooleanUtils.and(Array[Boolean](true, false, true)))
    assertTrue("True result for (false, true, true)", !BooleanUtils.and(Array[Boolean](false, true, true)))
  }

  @Test def testAnd_object_nullInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.and(null.asInstanceOf[Array[JavaBoolean]]))
    ()
  }

  @Test def testAnd_object_emptyInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.and(Array[JavaBoolean]()))
    ()
  }

  @Test def testAnd_object_nullElementInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.and(Array[JavaBoolean](null)))
    ()
  }

  @Test def testAnd_object_validInput_2items(): Unit = {
    assertTrue(
      "False result for (true, true)",
      BooleanUtils.and(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "True result for (false, false)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "True result for (true, false)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "True result for (false, true)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
  }

  @Test def testAnd_object_validInput_3items(): Unit = {
    assertTrue(
      "True result for (false, false, true)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "True result for (false, true, false)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "True result for (true, false, false)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (true, true, true)",
      BooleanUtils.and(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "True result for (false, false)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "True result for (true, true, false)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "True result for (true, false, true)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "True result for (false, true, true)",
      !BooleanUtils.and(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
  }

  //  testOr
  @Test def testOr_primitive_nullInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.or(null.asInstanceOf[Array[Boolean]]))
    ()
  }

  @Test def testOr_primitive_emptyInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.or(Array[Boolean]()))
    ()
  }

  @Test def testOr_primitive_validInput_2items(): Unit = {
    assertTrue("False result for (true, true)", BooleanUtils.or(Array[Boolean](true, true)))
    assertTrue("True result for (false, false)", !BooleanUtils.or(Array[Boolean](false, false)))
    assertTrue("False result for (true, false)", BooleanUtils.or(Array[Boolean](true, false)))
    assertTrue("False result for (false, true)", BooleanUtils.or(Array[Boolean](false, true)))
  }

  @Test def testOr_primitive_validInput_3items(): Unit = {
    assertTrue("False result for (false, false, true)", BooleanUtils.or(Array[Boolean](false, false, true)))
    assertTrue("False result for (false, true, false)", BooleanUtils.or(Array[Boolean](false, true, false)))
    assertTrue("False result for (true, false, false)", BooleanUtils.or(Array[Boolean](true, false, false)))
    assertTrue("False result for (true, true, true)", BooleanUtils.or(Array[Boolean](true, true, true)))
    assertTrue("True result for (false, false)", !BooleanUtils.or(Array[Boolean](false, false, false)))
    assertTrue("False result for (true, true, false)", BooleanUtils.or(Array[Boolean](true, true, false)))
    assertTrue("False result for (true, false, true)", BooleanUtils.or(Array[Boolean](true, false, true)))
    assertTrue("False result for (false, true, true)", BooleanUtils.or(Array[Boolean](false, true, true)))
  }

  @Test def testOr_object_nullInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.or(null.asInstanceOf[Array[JavaBoolean]]))
    ()
  }

  @Test def testOr_object_emptyInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.or(Array[JavaBoolean]()))
    ()
  }

  @Test def testOr_object_nullElementInput(): Unit = {
    assertThrows[IllegalArgumentException](BooleanUtils.or(Array[JavaBoolean](null)))
    ()
  }

  @Test def testOr_object_validInput_2items(): Unit = {
    assertTrue(
      "False result for (true, true)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "True result for (false, false)",
      !BooleanUtils.or(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (true, false)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (false, true)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
  }

  @Test def testOr_object_validInput_3items(): Unit = {
    assertTrue(
      "False result for (false, false, true)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "False result for (false, true, false)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (true, false, false)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (true, true, true)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "True result for (false, false)",
      !BooleanUtils.or(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.FALSE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (true, true, false)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.TRUE, JavaBoolean.FALSE)).booleanValue)
    assertTrue(
      "False result for (true, false, true)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.TRUE, JavaBoolean.FALSE, JavaBoolean.TRUE)).booleanValue)
    assertTrue(
      "False result for (false, true, true)",
      BooleanUtils.or(Array[JavaBoolean](JavaBoolean.FALSE, JavaBoolean.TRUE, JavaBoolean.TRUE)).booleanValue)
  }

  @Test def testCompare(): Unit = {
    assertTrue(BooleanUtils.compare(true, false) > 0)
    assertEquals(0, BooleanUtils.compare(true, true))
    assertEquals(0, BooleanUtils.compare(false, false))
    assertTrue(BooleanUtils.compare(false, true) < 0)
  }
}
