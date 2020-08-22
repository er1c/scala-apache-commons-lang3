# Scala Apache Commons Lang

[![Build](https://github.com/er1c/scala-apache-commons-lang3/workflows/build/badge.svg?branch=main)](https://github.com/er1c/scala-apache-commons-lang3/actions?query=branch%3Amain+workflow%3Abuild) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.er1c/scala-apache-commons-lang3_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.er1c/scala-apache-commons-lang3_2.13)

Scala port of [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/).  Based upon `3.11` [java code](https://github.com/apache/commons-lang/tree/master/src/main/java/org/apache/commons/lang3).

Target Platforms: `Scala.js` 0.6.x/1.1.x, `Scala Native`

## Development State

* Rough Library Files Conversion (**210**/210) :white_check_mark:
* Rough Test Files Conversion (**182**/182) :white_check_mark:
* Core Lang3 Classes Compiling
- [ ] [AnnotationUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/AnnotationUtils.scala)
- [x] [ArchUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ArchUtils.scala)
- [x] [ArrayUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ArrayUtils.scala)
- [x] [BitField.scala](./core/shared/src/main/scala/org/apache/commons/lang3/BitField.scala)
- [x] [BooleanUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/BooleanUtils.scala)
- [x] [CharEncoding.scala](./core/shared/src/main/scala/org/apache/commons/lang3/CharEncoding.scala)
- [ ] [CharRange.scala](./core/shared/src/main/scala/org/apache/commons/lang3/CharRange.scala)
- [x] [CharSequenceUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/CharSequenceUtils.scala)
- [ ] [CharSet.scala](./core/shared/src/main/scala/org/apache/commons/lang3/CharSet.scala)
- [ ] [CharSetUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/CharSetUtils.scala)
- [x] [CharUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/CharUtils.scala)
- [x] [Charsets.scala](./core/shared/src/main/scala/org/apache/commons/lang3/Charsets.scala)
- [x] [ClassLoaderUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ClassLoaderUtils.scala)
- [x] [ClassPathUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ClassPathUtils.scala)
- [x] [ClassUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ClassUtils.scala)
- [x] [Conversion.scala](./core/shared/src/main/scala/org/apache/commons/lang3/Conversion.scala)
- [ ] [EnumUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/EnumUtils.scala)
- [ ] [Functions.scala](./core/shared/src/main/scala/org/apache/commons/lang3/Functions.scala)
- [x] [JavaVersion.scala](./core/shared/src/main/scala/org/apache/commons/lang3/JavaVersion.scala)
- [ ] [LocaleUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/LocaleUtils.scala)
- [x] [NotImplementedException.scala](./core/shared/src/main/scala/org/apache/commons/lang3/NotImplementedException.scala)
- [x] [ObjectUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ObjectUtils.scala)
- [ ] [RandomStringUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/RandomStringUtils.scala)
- [x] [RandomUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/RandomUtils.scala)
- [ ] [Range.scala](./core/shared/src/main/scala/org/apache/commons/lang3/Range.scala)
- [x] [RegExUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/RegExUtils.scala)
- [x] [SerializationException.scala](./core/shared/src/main/scala/org/apache/commons/lang3/SerializationException.scala)
- [x] [SerializationUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/SerializationUtils.scala)
- [x] [StringEscapeUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/StringEscapeUtils.scala)
- [x] [StringUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/StringUtils.scala)
- [x] [SystemUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/SystemUtils.scala)
- [ ] [ThreadUtils.scala](./core/shared/src/main/scala/org/apache/commons/lang3/ThreadUtils.scala)
- [x] [Validate.scala](./core/shared/src/main/scala/org/apache/commons/lang3/Validate.scala)
* Subpackages Compiling
- [x] arch
- [ ] builder (**14**/20)
- [x] compare (**2**/2)
- [x] exception (**6**/6)
- [ ] function (**25**/43)
- [x] math (**3**/3)
- [x] mutable (**9**/9)
- [ ] reflect (**1**/8)
- [x] text (**4**/4)
- [x] text/translate (**12**/12)
- [x] tuple (**5**/5)

### Milestone 1

The first milestone is `StringUtils` tests and dependency-libraries passing.

#### Milestone 1 Completion State:

- Core Package :white_check_mark:
  - [x] [ArchUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ArchUtilsTest.scala)
  - ArrayUtils :white_check_mark:
    - [x] [ArrayUtilsAddTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ArrayUtilsAddTest.scala)
    - [x] [ArrayUtilsInsertTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ArrayUtilsInsertTest.scala)
    - [x] [ArrayUtilsRemoveTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ArrayUtilsRemoveTest.scala)
    - [x] [ArrayUtilsRemoveMultipleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ArrayUtilsRemoveMultipleTest.scala)
    - [x] [ArrayUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ArrayUtilsTest.scala)
  - [x] [BitFieldTest](./core/jvm/src/test/scala/org/apache/commons/lang3/BitFieldTest.scala)
  - [x] [BooleanUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/BooleanUtilsTest.scala)
  - [x] [CharEncodingTest](./core/jvm/src/test/scala/org/apache/commons/lang3/CharEncodingTest.scala)
  - [x] [CharSequenceUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/CharSequenceUtilsTest.scala)
  - [x] [CharUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/CharUtilsTest.scala)
  - [x] [CharsetsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/CharsetsTest.scala)
  - [x] [ClassLoaderUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ClassLoaderUtilsTest.scala)
  - [x] [ClassPathUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ClassPathUtilsTest.scala)
  - [x] [ClassUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ClassUtilsTest.scala)
  - [x] [ConversionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ConversionTest.scala)
  - [x] [JavaVersionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/JavaVersionTest.scala)
  - [x] [NotImplementedExceptionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/NotImplementedExceptionTest.scala)
  - [x] [ObjectUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ObjectUtilsTest.scala)
  - [x] [RandomUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/RandomUtilsTest.scala)
  - [x] [RegExUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/RegExUtilsTest.scala)
  - [x] [SerializationUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/SerializationUtilsTest.scala)
  - [x] [StringEscapeUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringEscapeUtilsTest.scala)
  - StringUtils :white_check_mark:
    - [x] [StringUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsTest.scala) (**Except** `getLevenshteinDistance`, `getJaroWinklerDistance`, and `getFuzzyDistance`.)
    - [x] [StringUtilsContainsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsContainsTest.scala)
    - [x] [StringUtilsEmptyBlankTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsEmptyBlankTest.scala)
    - [x] [StringUtilsEqualsIndexOfTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsEqualsIndexOfTest.scala)
    - [x] [StringUtilsIsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsIsTest.scala)
    - [x] [StringUtilsStartsEndsWithTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsStartsEndsWithTest.scala)
    - [x] [StringUtilsSubstringTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsSubstringTest.scala)
    - [x] [StringUtilsTrimStripTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsTrimStripTest.scala)
    - [x] [StringUtilsValueOfTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StringUtilsValueOfTest.scala)
  - [x] [SystemUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/SystemUtilsTest.scala)
- Sub packages
  - builder
    - [x] [builder.CompareToBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/CompareToBuilderTest.scala)
    - [x] [builder.DefaultToStringBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/DefaultToStringBuilderTest.scala)
    - [x] [builder.HashCodeBuilderAndEqualsBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/HashCodeBuilderAndEqualsBuilderTest.scala)
    - [x] [builder.HashCodeBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/HashCodeBuilderTest.scala)
    - [x] [builder.JsonToStringStyle](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/JsonToStringStyle.scala)
    - [x] [builder.MultilineToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/MultilineToStringStyleTest.scala)
    - [x] [builder.NoClassNameToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/NoClassNameToStringStyleTest.scala)
    - [x] [builder.NoFieldNamesToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/NoFieldNamesToStringStyleTest.scala)
    - [x] [builder.RecursiveToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/RecursiveToStringStyleTest.scala)
    - [x] [builder.ShortPrefixToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ShortPrefixToStringStyleTest.scala)    
    - [x] [builder.SimpleToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/SimpleToStringStyleTest.scala)
    - [x] [builder.StandardToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/StandardToStringStyleTest.scala)
    - [x] [builder.ToStringBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ToStringBuilderTest.scala)
    - [x] [builder.ToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ToStringStyleTest.scala)
  - compare
    - [ ] [compare.ComparableUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/compare/ComparableUtilsTest.scala)
    - [x] [compare.ObjectToStringComparatorTest](./core/jvm/src/test/scala/org/apache/commons/lang3/compare/ObjectToStringComparatorTest.scala)
  - exception :white_check_mark:
    - [x] [exception.AbstractExceptionContextTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/AbstractExceptionContextTest.scala)
    - [x] [exception.AbstractExceptionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/AbstractExceptionTest.scala)
    - [x] [exception.CloneFailedExceptionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/CloneFailedExceptionTest.scala)
    - [x] [exception.ContextedExceptionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/ContextedExceptionTest.scala)
    - [x] [exception.ContextedRuntimeExceptionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/ContextedRuntimeExceptionTest.scala)
    - [x] [exception.DefaultExceptionContextTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/DefaultExceptionContextTest.scala)
    - [x] [exception.ExceptionUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/exception/ExceptionUtilsTest.scala)
  - math :white_check_mark:
    - [x] [math.FractionTest](./core/jvm/src/test/scala/org/apache/commons/lang3/math/FractionTest.scala)
    - [x] [math.IEEE754rUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/math/IEEE754rUtilsTest.scala)
    - [x] [math.NumberUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/math/NumberUtilsTest.scala)
  - mutable :white_check_mark:
    - [x] [mutable.MutableBooleanTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableBooleanTest.scala)
    - [x] [mutable.MutableByteTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableBooleanTest.scala)
    - [x] [mutable.MutableDoubleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableDoubleTest.scala)
    - [x] [mutable.MutableFloatTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableFloatTest.scala)
    - [x] [mutable.MutableIntTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableIntTest.scala)
    - [x] [mutable.MutableLongTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableLongTest.scala)
    - [x] [mutable.MutableObjectTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableObjectTest.scala)
    - [x] [mutable.MutableShortTest](./core/jvm/src/test/scala/org/apache/commons/lang3/mutable/MutableShortTest.scala)
  - text
    - [ ] [text.CompositeFormatTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/CompositeFormatTest.scala)
    - [ ] [text.ExtendedMessageFormatTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/ExtendedMessageFormatTest.scala)
    - [ ] [text.FormattableUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/FormattableUtilsTest.scala)
    - [ ] [text.StrBuilderAppendInsertTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/StrBuilderAppendInsertTest.scala)
    - [ ] [text.StrBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/StrBuilderTest.scala)
    - [ ] [text.StrLookupTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/StrLookupTest.scala)
    - [ ] [text.StrMatcherTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/StrMatcherTest.scala)
    - [ ] [text.StrSubstitutorTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/StrSubstitutorTest.scala)
    - [ ] [text.StrTokenizerTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/StrTokenizerTest.scala)
    - [x] [text.WordUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/WordUtilsTest.scala)
    - translate :white_check_mark:
      - [x] [text.translate.EntityArraysTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/EntityArraysTest.scala)
      - [x] [text.translate.LookupTranslatorTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/LookupTranslatorTest.scala)
      - [x] [text.translate.NumericEntityEscaperTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/NumericEntityEscaperTest.scala)
      - [x] [text.translate.NumericEntityUnescaperTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/NumericEntityUnescaperTest.scala)
      - [x] [text.translate.OctalUnescaperTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/OctalUnescaperTest.scala)
      - [x] [text.translate.UnicodeEscaperTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/UnicodeEscaperTest.scala)
      - [x] [text.translate.UnicodeUnescaperTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/UnicodeUnescaperTest.scala)
      - [x] [text.translate.UnicodeUnpairedSurrogateRemoverTest](./core/jvm/src/test/scala/org/apache/commons/lang3/text/translate/UnicodeUnpairedSurrogateRemoverTest.scala)
  - tuple :white_check_mark:
    - [x] [tuple.ImmutablePairTest](./core/jvm/src/test/scala/org/apache/commons/lang3/tuple/ImmutablePairTest.scala)
    - [x] [tuple.ImmutableTripleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/tuple/ImmutableTripleTest.scala)
    - [x] [tuple.MutablePairTest](./core/jvm/src/test/scala/org/apache/commons/lang3/tuple/MutablePairTest.scala)
    - [x] [tuple.MutableTripleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/tuple/MutableTripleTest.scala)
    - [x] [tuple.PairTest](./core/jvm/src/test/scala/org/apache/commons/lang3/tuple/PairTest.scala)
    - [x] [tuple.TripleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/tuple/TripleTest.scala)


### Milestone 2

All except `reflect`, `function`, `concurrent`

- Core Package
  - [ ] [StreamsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/StreamsTest.scala)
  - [ ] [ValidateTest](./core/jvm/src/test/scala/org/apache/commons/lang3/ValidateTest.scala)
- Sub packages
  - event
  - stream
    - [ ] [stream.StreamsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/stream/StreamsTest.scala)
  - text
    - **TODO**
  - time 
    - **TODO**

### RC1

All tests passing.

- Sub packages
  - builder
    - [ ] [builder.DiffBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/DiffBuilderTest.scala)
    - [ ] [builder.DiffResultTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/DiffResultTest.scala)
    - [ ] [builder.DiffTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/DiffTest.scala)
    - [ ] [builder.EqualsBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/EqualsBuilderTest.scala)
    - [ ] [builder.MultilineRecursiveToStringStyleTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/MultilineRecursiveToStringStyleTest.scala)
    - [ ] [builder.ReflectionDiffBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionDiffBuilderTest.scala)
    - [ ] [builder.ReflectionToStringBuilderConcurrencyTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderConcurrencyTest.scala)
    - [ ] [builder.ReflectionToStringBuilderExcludeNullValuesTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderExcludeNullValuesTest.scala)
    - [ ] [builder.ReflectionToStringBuilderExcludeTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderExcludeTest.scala)
    - [ ] [builder.ReflectionToStringBuilderExcludeWithAnnotationTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderExcludeWithAnnotationTest.scala)
    - [ ] [builder.ReflectionToStringBuilderMutateInspectConcurrencyTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderMutateInspectConcurrencyTest.scala)
    - [ ] [builder.ReflectionToStringBuilderSummaryTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderSummaryTest.scala)
    - [ ] [builder.ReflectionToStringBuilderTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ReflectionToStringBuilderTest.scala)
    - [ ] [builder.ToStringStyleConcurrencyTest](./core/jvm/src/test/scala/org/apache/commons/lang3/builder/ToStringStyleConcurrencyTest.scala)
  - concurrent
    - **TODO**
  - function
    - [ ] [function.FailableFunctionsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/function/FailableFunctionsTest.scala)
  - reflect
    - [ ] [reflect.ConstructorUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/reflect/ConstructorUtilsTest.scala)
    - [ ] [reflect.FieldUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/reflect/FieldUtilsTest.scala)
    - [ ] [reflect.InheritanceUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/reflect/InheritanceUtilsTest.scala)
    - [ ] [reflect.MethodUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/reflect/MethodUtilsTest.scala)
    - [ ] [reflect.TypeLiteralTest](./core/jvm/src/test/scala/org/apache/commons/lang3/reflect/TypeLiteralTest.scala)
    - [ ] [reflect.TypeUtilsTest](./core/jvm/src/test/scala/org/apache/commons/lang3/reflect/TypeUtilsTest.scala)


## Usage

The packages will be published on Maven Central.

### Scala JVM/Native Target

```scala
libraryDependencies += "io.github.er1c" %% "scala-apache-commons-lang3" % "<version>"
```

### Scala.js

```scala
libraryDependencies += "io.github.er1c" %%% "scala-apache-commons-lang3" % "<version>"
```

## Documentation

Links:

- [Website](https://er1c.github.io/scala-apache-commons-lang3/)
- [API documentation](https://er1c.github.io/scala-apache-commons-lang3/api/)

## Contributing

The Scala Apache Commons Lang project welcomes contributions from anybody wishing to participate.  All code or documentation that is provided must be licensed with the same license that Scala Apache Commons Lang is licensed with (Apache 2.0, see [LICENCE](./LICENSE.md)).

People are expected to follow the [Scala Code of Conduct](./CODE_OF_CONDUCT.md) when discussing Scala Apache Commons Lang on GitHub, Gitter channel, or other venues.

Feel free to open an issue if you notice a bug, have an idea for a feature, or have a question about the code. Pull requests are also gladly accepted. For more information, check out the [contributor guide](./CONTRIBUTING.md).

## License

All code in this repository is licensed under the Apache License, Version 2.0.  See [LICENCE](./LICENSE.md).
