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
//import org.junit.Assert.assertSame
//import org.junit.Assert.assertThrows
//import org.junit.Assert.assertTrue
//import java.lang.reflect.Constructor
//import java.lang.reflect.Modifier
//import java.util
//import java.util.Collections
//import org.junit.jupiter.api.Nested
//import org.junit.Test
//import org.scalatestplus.junit.JUnitSuite
//
///**
//  * Unit tests {@link org.apache.commons.lang3.Validate}.
//  */
//class ValidateTest extends JUnitSuite {
//
//  @Nested private[lang3] class IsTrue {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowForTrueExpression() = Validate.isTrue(true)
//
//      @Test private[lang3] def shouldThrowExceptionWithDefaultMessageForFalseExpression() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isTrue(false))
//        assertEquals("The validated expression is false", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowForTrueExpression() = Validate.isTrue(true, "MSG")
//
//      @Test private[lang3] def shouldThrowExceptionWithGivenMessageForFalseExpression() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isTrue(false, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithLongTemplate {
//      @Test private[lang3] def shouldNotThrowForTrueExpression() = Validate.isTrue(true, "MSG", 6)
//
//      @Test private[lang3] def shouldThrowExceptionWithLongInsertedIntoTemplateMessageForFalseExpression() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isTrue(false, "MSG %s", 6))
//        assertEquals("MSG 6", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithDoubleTemplate {
//      @Test private[lang3] def shouldNotThrowForTrueExpression() = Validate.isTrue(true, "MSG", 7.4d)
//
//      @Test private[lang3] def shouldThrowExceptionWithDoubleInsertedIntoTemplateMessageForFalseExpression() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isTrue(false, "MSG %s", 7.4d))
//        assertEquals("MSG 7.4", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithObjectTemplate {
//      @Test private[lang3] def shouldNotThrowForTrueExpression() = Validate.isTrue(true, "MSG", "Object 1", "Object 2")
//
//      @Test private[lang3] def shouldThrowExceptionWithDoubleInsertedIntoTemplateMessageForFalseExpression() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isTrue(false, "MSG %s %s", "Object 1", "Object 2"))
//        assertEquals("MSG Object 1 Object 2", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class NotNull {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowForNonNullReference() = Validate.notNull(new Any)
//
//      @Test private[lang3] def shouldReturnTheSameInstance() = {
//        val str = "Hi"
//        val result = Validate.notNull(str)
//        assertSame(str, result)
//      }
//
//      @Test private[lang3] def shouldThrowExceptionWithDefaultMessageForNullReference() = {
//        val ex = assertThrows(classOf[NullPointerException], () => Validate.notNull(null))
//        assertEquals("The validated object is null", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowForNonNullReference() = Validate.notNull(new Any, "MSG")
//
//      @Test private[lang3] def shouldReturnTheSameInstance() = {
//        val str = "Hi"
//        val result = Validate.notNull(str, "MSG")
//        assertSame(str, result)
//      }
//
//      @Test private[lang3] def shouldThrowExceptionWithGivenMessageForNullReference() = {
//        val ex = assertThrows(classOf[NullPointerException], () => Validate.notNull(null, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class NotEmpty {
//
//    @Nested private[lang3] class WithArray {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForArrayContainingNullReference() = Validate.notEmpty(Array[AnyRef](null))
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val array = Array[String]("hi")
//          val result = Validate.notEmpty(array)
//          assertSame(array, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullArray() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[Array[AnyRef]]))
//          assertEquals("The validated array is empty", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForEmptyArray() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(new Array[AnyRef](0)))
//          assertEquals("The validated array is empty", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForArrayContainingNullReference() = Validate.notEmpty(Array[AnyRef](null), "MSG")
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val array = Array[String]("hi")
//          val result = Validate.notEmpty(array, "MSG")
//          assertSame(array, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithGivenMessageForNullArray() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[Array[AnyRef]], "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForEmptyArray() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(new Array[AnyRef](0), "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] class WithCollection {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForCollectionContainingNullReference() = Validate.notEmpty(Collections.singleton(null))
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val col = Collections.singleton("Hi")
//          val result = Validate.notEmpty(col)
//          assertSame(col, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullCollection() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[util.Collection[_]]))
//          assertEquals("The validated collection is empty", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForEmptyCollection() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(Collections.emptySet))
//          assertEquals("The validated collection is empty", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForCollectionContainingNullReference() = Validate.notEmpty(Collections.singleton(null), "MSG")
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val col = Collections.singleton("Hi")
//          val result = Validate.notEmpty(col, "MSG")
//          assertSame(col, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithGivenMessageForNullCollection() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[util.Collection[_]], "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForEmptyCollection() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(Collections.emptySet, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] class WithMap {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForMapContainingNullMapping() = Validate.notEmpty(Collections.singletonMap("key", null))
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val map = Collections.singletonMap("key", "value")
//          val result = Validate.notEmpty(map)
//          assertSame(map, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullMap() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[util.Map[_, _]]))
//          assertEquals("The validated map is empty", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForEmptyMap() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(Collections.emptyMap))
//          assertEquals("The validated map is empty", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForMapContainingNullMapping() = Validate.notEmpty(Collections.singletonMap("key", null), "MSG")
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val map = Collections.singletonMap("key", "value")
//          val result = Validate.notEmpty(map, "MSG")
//          assertSame(map, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithGivenMessageForNullMap() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[util.Map[_, _]], "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForEmptyMap() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(Collections.emptyMap, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] class WithCharSequence {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForNonEmptyString() = Validate.notEmpty("Hi")
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val str = "Hi"
//          val result = Validate.notEmpty(str)
//          assertSame(str, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullCharSequence() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[CharSequence]))
//          assertEquals("The validated character sequence is empty", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForEmptyString() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty(""))
//          assertEquals("The validated character sequence is empty", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForNonEmptyString() = Validate.notEmpty("Hi", "MSG")
//
//        @Test private[lang3] def shouldReturnTheSameInstance() = {
//          val str = "Hi"
//          val result = Validate.notEmpty(str, "MSG")
//          assertSame(str, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithGivenMessageForNullCharSequence() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.notEmpty(null.asInstanceOf[CharSequence], "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForEmptyString() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notEmpty("", "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//  }
//
//  @Nested private[lang3] class NotBlank {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowExceptionForNonEmptyString() = Validate.notBlank("abc")
//
//      @Test private[lang3] def shouldNotThrowExceptionForNonEmptyStringContainingSpaces() = Validate.notBlank("  abc   ")
//
//      @Test private[lang3] def shouldNotThrowExceptionForNonEmptyStringContainingWhitespaceChars() = Validate.notBlank(" \n \t abc \r \n ")
//
//      @Test private[lang3] def shouldReturnNonBlankValue() = {
//        val str = "abc"
//        val result = Validate.notBlank(str)
//        assertSame(str, result)
//      }
//
//      @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullString() = {
//        val ex = assertThrows(classOf[NullPointerException], () => Validate.notBlank(null))
//        assertEquals("The validated character sequence is blank", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForEmptyString() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notBlank(""))
//        assertEquals("The validated character sequence is blank", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForBlankString() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notBlank("   "))
//        assertEquals("The validated character sequence is blank", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForStringContainingOnlyWhitespaceChars() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notBlank(" \n \t \r \n "))
//        assertEquals("The validated character sequence is blank", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowExceptionForNonEmptyString() = Validate.notBlank("abc", "MSG")
//
//      @Test private[lang3] def shouldNotThrowExceptionForNonEmptyStringContainingSpaces() = Validate.notBlank("  abc   ", "MSG")
//
//      @Test private[lang3] def shouldNotThrowExceptionForNonEmptyStringContainingWhitespaceChars() = Validate.notBlank(" \n \t abc \r \n ", "MSG")
//
//      @Test private[lang3] def shouldReturnNonBlankValue() = {
//        val str = "abc"
//        val result = Validate.notBlank(str, "MSG")
//        assertSame(str, result)
//      }
//
//      @Test private[lang3] def shouldThrowNullPointerExceptionWithGivenMessageForNullString() = {
//        val ex = assertThrows(classOf[NullPointerException], () => Validate.notBlank(null, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForEmptyString() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notBlank("", "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForBlankString() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notBlank("   ", "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForStringContainingOnlyWhitespaceChars() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notBlank(" \n \t \r \n ", "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class NoNullElements {
//
//    @Nested private[lang3] class WithArray {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForNonEmptyArray() = Validate.noNullElements(Array[String]("a", "b"))
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val array = Array("a", "b")
//          val result = Validate.noNullElements(array)
//          assertSame(array, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullArray() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.noNullElements(null.asInstanceOf[Array[AnyRef]]))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForArrayWithNullElement() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.noNullElements(Array[String]("a", null)))
//          assertEquals("The validated array contains null element at index: 1", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForNonEmptyArray() = Validate.noNullElements(Array[String]("a", "b"), "MSG")
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val array = Array("a", "b")
//          val result = Validate.noNullElements(array, "MSG")
//          assertSame(array, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullArray() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.noNullElements(null.asInstanceOf[Array[AnyRef]], "MSG"))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForArrayWithNullElement() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.noNullElements(Array[String]("a", null), "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] class WithCollection {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForNonEmptyCollection() = Validate.noNullElements(Collections.singleton("a"))
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val col = Collections.singleton("a")
//          val result = Validate.noNullElements(col)
//          assertSame(col, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullCollection() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.noNullElements(null.asInstanceOf[util.Collection[_]]))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForCollectionWithNullElement() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.noNullElements(Collections.singleton(null)))
//          assertEquals("The validated collection contains null element at index: 0", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForNonEmptyCollection() = Validate.noNullElements(Collections.singleton("a"), "MSG")
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val col = Collections.singleton("a")
//          val result = Validate.noNullElements(col, "MSG")
//          assertSame(col, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullCollection() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.noNullElements(null.asInstanceOf[util.Collection[_]], "MSG"))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForCollectionWithNullElement() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.noNullElements(Collections.singleton(null), "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//  }
//
//  @Nested private[lang3] class ValidIndex {
//
//    @Nested private[lang3] class WithArray {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForValidIndex() = Validate.validIndex(Array[String]("a"), 0)
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val array = Array("a")
//          val result = Validate.validIndex(array, 0)
//          assertSame(array, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultForNullArray() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.validIndex(null.asInstanceOf[Array[AnyRef]], 1))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithDefaultMessageForNegativeIndex() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Array[String]("a"), -1))
//          assertEquals("The validated array index is invalid: -1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithDefaultMessageForIndexOutOfBounds() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Array[String]("a"), 1))
//          assertEquals("The validated array index is invalid: 1", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForValidIndex() = Validate.validIndex(Array[String]("a"), 0, "MSG")
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val array = Array("a")
//          val result = Validate.validIndex(array, 0, "MSG")
//          assertSame(array, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullArray() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.validIndex(null.asInstanceOf[Array[AnyRef]], 1, "MSG"))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithGivenMessageForNegativeIndex() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Array[String]("a"), -1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithGivenMessageForIndexOutOfBounds() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Array[String]("a"), 1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] class WithCollection {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForValidIndex() = Validate.validIndex(Collections.singleton("a"), 0)
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val col = Collections.singleton("a")
//          val result = Validate.validIndex(col, 0)
//          assertSame(col, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultForNullCollection() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.validIndex(null.asInstanceOf[util.Collection[_]], 1))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithDefaultMessageForNegativeIndex() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Collections.singleton("a"), -1))
//          assertEquals("The validated collection index is invalid: -1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithDefaultMessageForIndexOutOfBounds() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Collections.singleton("a"), 1))
//          assertEquals("The validated collection index is invalid: 1", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForValidIndex() = Validate.validIndex(Collections.singleton("a"), 0, "MSG")
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val col = Collections.singleton("a")
//          val result = Validate.validIndex(col, 0, "MSG")
//          assertSame(col, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullCollection() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.validIndex(null.asInstanceOf[util.Collection[_]], 1, "MSG"))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithGivenMessageForNegativeIndex() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Collections.singleton("a"), -1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithGivenMessageForIndexOutOfBounds() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex(Collections.singleton("a"), 1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] class WithCharSequence {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForValidIndex() = Validate.validIndex("a", 0)
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val str = "a"
//          val result = Validate.validIndex(str, 0)
//          assertSame(str, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultForNullString() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.validIndex(null.asInstanceOf[String], 1))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithDefaultMessageForNegativeIndex() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex("a", -1))
//          assertEquals("The validated character sequence index is invalid: -1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithDefaultMessageForIndexOutOfBounds() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex("a", 1))
//          assertEquals("The validated character sequence index is invalid: 1", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionForValidIndex() = Validate.validIndex("a", 0, "MSG")
//
//        @Test private[lang3] def shouldReturnSameInstance() = {
//          val str = "a"
//          val result = Validate.validIndex(str, 0, "MSG")
//          assertSame(str, result)
//        }
//
//        @Test private[lang3] def shouldThrowNullPointerExceptionWithDefaultMessageForNullStr() = {
//          val ex = assertThrows(classOf[NullPointerException], () => Validate.validIndex(null.asInstanceOf[String], 1, "MSG"))
//          assertEquals("The validated object is null", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithGivenMessageForNegativeIndex() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex("a", -1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIndexOutOfBoundsExceptionWithGivenMessageForIndexOutOfBounds() = {
//          val ex = assertThrows(classOf[IndexOutOfBoundsException], () => Validate.validIndex("a", 1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//  }
//
//  @Nested private[lang3] class MatchesPattern {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowExceptionWhenStringMatchesPattern() = Validate.matchesPattern("hi", "[a-z]*")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenStringDoesNotMatchPattern() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.matchesPattern("hi", "[0-9]*"))
//        assertEquals("The string hi does not match the pattern [0-9]*", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowExceptionWhenStringMatchesPattern() = Validate.matchesPattern("hi", "[a-z]*", "MSG")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWhenStringDoesNotMatchPattern() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.matchesPattern("hi", "[0-9]*", "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class NotNaN {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowExceptionForNumber() = Validate.notNaN(0.0)
//
//      @Test private[lang3] def shouldNotThrowExceptionForPositiveInfinity() = Validate.notNaN(Double.POSITIVE_INFINITY)
//
//      @Test private[lang3] def shouldNotThrowExceptionForNegativeInfinity() = Validate.notNaN(Double.NEGATIVE_INFINITY)
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForNaN() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notNaN(Double.NaN))
//        assertEquals("The validated value is not a number", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowExceptionForNumber() = Validate.notNaN(0.0, "MSG")
//
//      @Test private[lang3] def shouldNotThrowExceptionForPositiveInfinity() = Validate.notNaN(Double.POSITIVE_INFINITY, "MSG")
//
//      @Test private[lang3] def shouldNotThrowExceptionForNegativeInfinity() = Validate.notNaN(Double.NEGATIVE_INFINITY, "MSG")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageForNaN() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.notNaN(Double.NaN, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class Finite {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowExceptionForFiniteValue() = Validate.finite(0.0)
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForPositiveInfinity() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.finite(Double.POSITIVE_INFINITY))
//        assertEquals("The value is invalid: Infinity", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForNegativeInfinity() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.finite(Double.NEGATIVE_INFINITY))
//        assertEquals("The value is invalid: -Infinity", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForNaN() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.finite(Double.NaN))
//        assertEquals("The value is invalid: NaN", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowExceptionForFiniteValue() = Validate.finite(0.0, "MSG")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForPositiveInfinity() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.finite(Double.POSITIVE_INFINITY, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForNegativeInfinity() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.finite(Double.NEGATIVE_INFINITY, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageForNaN() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.finite(Double.NaN, "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class InclusiveBetween {
//
//    @Nested private[lang3] object WithComparable {
//      private val LOWER_BOUND = "1"
//      private val UPPER_BOUND = "3"
//    }
//
//    @Nested private[lang3] class WithComparable {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "2")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsLowerBound() = Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.LOWER_BOUND)
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsUpperBound() = Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.UPPER_BOUND)
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "0"))
//          assertEquals("The value 0 is not in the specified inclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "4"))
//          assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "2", "MSG")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsLowerBound() = Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.LOWER_BOUND, "MSG")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsUpperBound() = Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.UPPER_BOUND, "MSG")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "0", "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "4", "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] object WithLong {
//      private val LOWER_BOUND = 1
//      private val UPPER_BOUND = 3
//    }
//
//    @Nested private[lang3] class WithLong {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 2)
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsLowerBound() = Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.LOWER_BOUND)
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsUpperBound() = Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.UPPER_BOUND)
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 0))
//          assertEquals("The value 0 is not in the specified inclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 4))
//          assertEquals("The value 4 is not in the specified inclusive range of 1 to 3", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 2, "MSG")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsLowerBound() = Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.LOWER_BOUND, "MSG")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsUpperBound() = Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.UPPER_BOUND, "MSG")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 0, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 4, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] object WithDouble {
//      private val LOWER_BOUND = 0.1
//      private val UPPER_BOUND = 3.1
//    }
//
//    @Nested private[lang3] class WithDouble {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 2.1)
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsLowerBound() = Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.LOWER_BOUND)
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsUpperBound() = Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.UPPER_BOUND)
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 0.01))
//          assertEquals("The value 0.01 is not in the specified inclusive range of 0.1 to 3.1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 4.1))
//          assertEquals("The value 4.1 is not in the specified inclusive range of 0.1 to 3.1", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 2.1, "MSG")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsLowerBound() = Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.LOWER_BOUND, "MSG")
//
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsUpperBound() = Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.UPPER_BOUND, "MSG")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 0.01, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.inclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 4.1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//  }
//
//  @Nested private[lang3] class ExclusiveBetween {
//
//    @Nested private[lang3] object WithComparable {
//      private val LOWER_BOUND = "1"
//      private val UPPER_BOUND = "3"
//    }
//
//    @Nested private[lang3] class WithComparable {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "2")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.LOWER_BOUND))
//          assertEquals("The value 1 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.UPPER_BOUND))
//          assertEquals("The value 3 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "0"))
//          assertEquals("The value 0 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "4"))
//          assertEquals("The value 4 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "2", "MSG")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.LOWER_BOUND, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, WithComparable.UPPER_BOUND, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "0", "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithComparable.LOWER_BOUND, WithComparable.UPPER_BOUND, "4", "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] object WithLong {
//      private val LOWER_BOUND = 1
//      private val UPPER_BOUND = 3
//    }
//
//    @Nested private[lang3] class WithLong {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 2)
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.LOWER_BOUND))
//          assertEquals("The value 1 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.UPPER_BOUND))
//          assertEquals("The value 3 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 0))
//          assertEquals("The value 0 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 4))
//          assertEquals("The value 4 is not in the specified exclusive range of 1 to 3", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 2, "MSG")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.LOWER_BOUND, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, WithLong.UPPER_BOUND, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 0, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithLong.LOWER_BOUND, WithLong.UPPER_BOUND, 4, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//    @Nested private[lang3] object WithDouble {
//      private val LOWER_BOUND = 0.1
//      private val UPPER_BOUND = 3.1
//    }
//
//    @Nested private[lang3] class WithDouble {
//
//      @Nested private[lang3] class WithoutMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 2.1)
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExcdeptionWhenValueIsLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.LOWER_BOUND))
//          assertEquals("The value 0.1 is not in the specified exclusive range of 0.1 to 3.1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExcdeptionWhenValueIsUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.UPPER_BOUND))
//          assertEquals("The value 3.1 is not in the specified exclusive range of 0.1 to 3.1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 0.01))
//          assertEquals("The value 0.01 is not in the specified exclusive range of 0.1 to 3.1", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 4.1))
//          assertEquals("The value 4.1 is not in the specified exclusive range of 0.1 to 3.1", ex.getMessage)
//        }
//      }
//
//      @Nested private[lang3] class WithMessage {
//        @Test private[lang3] def shouldNotThrowExceptionWhenValueIsBetweenBounds() = Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 2.1, "MSG")
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExcdeptionWhenValueIsLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.LOWER_BOUND, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExcdeptionWhenValueIsUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, WithDouble.UPPER_BOUND, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsBelowLowerBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 0.01, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//
//        @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsAboveUpperBound() = {
//          val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.exclusiveBetween(WithDouble.LOWER_BOUND, WithDouble.UPPER_BOUND, 4.1, "MSG"))
//          assertEquals("MSG", ex.getMessage)
//        }
//      }
//
//    }
//
//  }
//
//  @Nested private[lang3] class IsInstanceOf {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowExceptionWhenValueIsInstanceOfClass() = Validate.isInstanceOf(classOf[String], "hi")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenValueIsNotInstanceOfClass() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isInstanceOf(classOf[util.List[_]], "hi"))
//        assertEquals("Expected type: java.util.List, actual: java.lang.String", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowExceptionWhenValueIsInstanceOfClass() = Validate.isInstanceOf(classOf[String], "hi", "MSG")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsNotInstanceOfClass() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isInstanceOf(classOf[util.List[_]], "hi", "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessageTemplate {
//      @Test private[lang3] def shouldNotThrowExceptionWhenValueIsInstanceOfClass() = Validate.isInstanceOf(classOf[String], "hi", "Error %s=%s", "Name", "Value")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGivenMessageWhenValueIsNotInstanceOfClass() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isInstanceOf(classOf[util.List[_]], "hi", "Error %s=%s", "Name", "Value"))
//        assertEquals("Error Name=Value", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class IsAssignable {
//
//    @Nested private[lang3] class WithoutMessage {
//      @Test private[lang3] def shouldNotThrowExceptionWhenClassIsAssignable() = Validate.isAssignableFrom(classOf[CharSequence], classOf[String])
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithDefaultMessageWhenClassIsNotAssignable() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isAssignableFrom(classOf[util.List[_]], classOf[String]))
//        assertEquals("Cannot assign a java.lang.String to a java.util.List", ex.getMessage)
//      }
//    }
//
//    @Nested private[lang3] class WithMessage {
//      @Test private[lang3] def shouldNotThrowExceptionWhenClassIsAssignable() = Validate.isAssignableFrom(classOf[CharSequence], classOf[String], "MSG")
//
//      @Test private[lang3] def shouldThrowIllegalArgumentExceptionWithGiventMessageWhenClassIsNotAssignable() = {
//        val ex = assertThrows(classOf[IllegalArgumentException], () => Validate.isAssignableFrom(classOf[util.List[_]], classOf[String], "MSG"))
//        assertEquals("MSG", ex.getMessage)
//      }
//    }
//
//  }
//
//  @Nested private[lang3] class UtilClassConventions {
//    @Test private[lang3] def instancesCanBeConstrcuted() = assertNotNull(new Validate.type)
//
//    @Test private[lang3] def hasOnlyOnePublicConstructor() = {
//      val cons = classOf[Validate.type].getDeclaredConstructors
//      assertEquals(1, cons.length)
//    }
//
//    @Test private[lang3] def isPublicClass() = assertTrue(Modifier.isPublic(classOf[Validate.type].getModifiers))
//
//    @Test private[lang3] def isNonFinalClass() = assertFalse(Modifier.isFinal(classOf[Validate.type].getModifiers))
//  }
//
//}
