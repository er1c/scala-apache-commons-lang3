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

package org.apache.commons.lang3.text

import java.util
import java.util.Collections
import java.util.NoSuchElementException
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import scala.collection.JavaConverters._
import scala.util.control.Breaks

/**
  * Tokenizes a string based on delimiters (separators)
  * and supporting quoting and ignored character concepts.
  * <p>
  * This class can split a String into many smaller strings. It aims
  * to do a similar job to {@link java.util.StringTokenizer StringTokenizer},
  * however it offers much more control and flexibility including implementing
  * the {@code ListIterator} interface. By default, it is set up
  * like {@code StringTokenizer}.
  * <p>
  * The input String is split into a number of <i>tokens</i>.
  * Each token is separated from the next String by a <i>delimiter</i>.
  * One or more delimiter characters must be specified.
  * <p>
  * Each token may be surrounded by quotes.
  * The <i>quote</i> matcher specifies the quote character(s).
  * A quote may be escaped within a quoted section by duplicating itself.
  * <p>
  * Between each token and the delimiter are potentially characters that need trimming.
  * The <i>trimmer</i> matcher specifies these characters.
  * One usage might be to trim whitespace characters.
  * <p>
  * At any point outside the quotes there might potentially be invalid characters.
  * The <i>ignored</i> matcher specifies these characters to be removed.
  * One usage might be to remove new line characters.
  * <p>
  * Empty tokens may be removed or returned as null.
  * <pre>
  * "a,b,c"         - Three tokens "a","b","c"   (comma delimiter)
  * " a, b , c "    - Three tokens "a","b","c"   (default CSV processing trims whitespace)
  * "a, ", b ,", c" - Three tokens "a, " , " b ", ", c" (quoted text untouched)
  * </pre>
  *
  * <table>
  * <caption>StrTokenizer properties and options</caption>
  * <tr>
  * <th>Property</th><th>Type</th><th>Default</th>
  * </tr>
  * <tr>
  * <td>delim</td><td>CharSetMatcher</td><td>{ \t\n\r\f}</td>
  * </tr>
  * <tr>
  * <td>quote</td><td>NoneMatcher</td><td>{}</td>
  * </tr>
  * <tr>
  * <td>ignore</td><td>NoneMatcher</td><td>{}</td>
  * </tr>
  * <tr>
  * <td>emptyTokenAsNull</td><td>boolean</td><td>false</td>
  * </tr>
  * <tr>
  * <td>ignoreEmptyTokens</td><td>boolean</td><td>true</td>
  * </tr>
  * </table>
  *
  * @since 2.2
  * @deprecated as of 3.6, use commons-text
  *             <a href="https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringTokenizer.html">
  *             StringTokenizer</a> instead
  */
@deprecated object StrTokenizer {

  /**
    * Our own copy of breaks to avoid conflicts with any other breaks:
    * "Calls to break from one instantiation of Breaks will never target breakable objects of some other instantiation."
    */
  private val breaks: Breaks = new Breaks

  private val CSV_TOKENIZER_PROTOTYPE: StrTokenizer = {
    new StrTokenizer()
      .setDelimiterMatcher(StrMatcher.commaMatcher)
      .setQuoteMatcher(StrMatcher.doubleQuoteMatcher)
      .setIgnoredMatcher(StrMatcher.noneMatcher)
      .setTrimmerMatcher(StrMatcher.trimMatcher)
      .setEmptyTokenAsNull(false)
      .setIgnoreEmptyTokens(false)
  }

  private val TSV_TOKENIZER_PROTOTYPE: StrTokenizer = {
    new StrTokenizer()
      .setDelimiterMatcher(StrMatcher.tabMatcher)
      .setQuoteMatcher(StrMatcher.doubleQuoteMatcher)
      .setIgnoredMatcher(StrMatcher.noneMatcher)
      .setTrimmerMatcher(StrMatcher.trimMatcher)
      .setEmptyTokenAsNull(false)
      .setIgnoreEmptyTokens(false)
  }

  /**
    * Returns a clone of {@code CSV_TOKENIZER_PROTOTYPE}.
    *
    * @return a clone of {@code CSV_TOKENIZER_PROTOTYPE}.
    */
  private def getCSVClone: StrTokenizer = CSV_TOKENIZER_PROTOTYPE.clone.asInstanceOf[StrTokenizer]

  /**
    * Gets a new tokenizer instance which parses Comma Separated Value strings
    * initializing it with the given input.  The default for CSV processing
    * will be trim whitespace from both ends (which can be overridden with
    * the setTrimmer method).
    * <p>
    * You must call a "reset" method to set the string which you want to parse.
    *
    * @return a new tokenizer instance which parses Comma Separated Value strings
    */
  def getCSVInstance: StrTokenizer = getCSVClone

  /**
    * Gets a new tokenizer instance which parses Comma Separated Value strings
    * initializing it with the given input.  The default for CSV processing
    * will be trim whitespace from both ends (which can be overridden with
    * the setTrimmer method).
    *
    * @param input the text to parse
    * @return a new tokenizer instance which parses Comma Separated Value strings
    */
  def getCSVInstance(input: String): StrTokenizer = {
    val tok = getCSVClone
    tok.reset(input)
    tok
  }

  def getCSVInstance(input: Array[Char]): StrTokenizer = {
    val tok = getCSVClone
    tok.reset(input)
    tok
  }

  /**
    * Returns a clone of {@code TSV_TOKENIZER_PROTOTYPE}.
    *
    * @return a clone of {@code TSV_TOKENIZER_PROTOTYPE}.
    */
  private def getTSVClone = TSV_TOKENIZER_PROTOTYPE.clone.asInstanceOf[StrTokenizer]

  /**
    * Gets a new tokenizer instance which parses Tab Separated Value strings.
    * The default for CSV processing will be trim whitespace from both ends
    * (which can be overridden with the setTrimmer method).
    * <p>
    * You must call a "reset" method to set the string which you want to parse.
    *
    * @return a new tokenizer instance which parses Tab Separated Value strings.
    */
  def getTSVInstance: StrTokenizer = getTSVClone

  /**
    * Gets a new tokenizer instance which parses Tab Separated Value strings.
    * The default for CSV processing will be trim whitespace from both ends
    * (which can be overridden with the setTrimmer method).
    *
    * @param input the string to parse
    * @return a new tokenizer instance which parses Tab Separated Value strings.
    */
  def getTSVInstance(input: String): StrTokenizer = {
    val tok = getTSVClone
    tok.reset(input)
    tok
  }

  def getTSVInstance(input: Array[Char]): StrTokenizer = {
    val tok = getTSVClone
    tok.reset(input)
    tok
  }
}

/**
  * Constructs a tokenizer splitting on space, tab, newline and formfeed
  * as per StringTokenizer, but with no text to tokenize.
  * <p>
  * This constructor is normally used with {@link #reset(input:String)*}.
  */
@deprecated class StrTokenizer() extends util.ListIterator[String] with Cloneable {
  import StrTokenizer.breaks._

  /** The text to work on. */
  private var chars: Array[Char] = null
  /** The parsed tokens */
  private var tokens: Array[String] = null
  /** The current iteration position */
  private var tokenPos = 0
  /** The delimiter matcher */
  private var delimMatcher = StrMatcher.splitMatcher
  /** The quote matcher */
  private var quoteMatcher = StrMatcher.noneMatcher
  /** The ignored matcher */
  private var ignoredMatcher = StrMatcher.noneMatcher
  /** The trimmer matcher */
  private var trimmerMatcher = StrMatcher.noneMatcher
  /** Whether to return empty tokens as null */
  private var emptyAsNull = false
  /** Whether to ignore empty tokens */
  private var ignoreEmptyTokens = true

  /**
    * Constructs a tokenizer splitting on space, tab, newline and formfeed
    * as per StringTokenizer.
    *
    * @param input the string which is to be parsed
    */
  def this(input: String) {
    this()
    if (input != null) chars = input.toCharArray
    else chars = null
  }

  /**
    * Constructs a tokenizer splitting on the specified delimiter character.
    *
    * @param input the string which is to be parsed
    * @param delim the field delimiter character
    */
  def this(input: String, delim: Char) {
    this(input)
    setDelimiterChar(delim)
  }

  /**
    * Constructs a tokenizer splitting on the specified delimiter string.
    *
    * @param input the string which is to be parsed
    * @param delim the field delimiter string
    */
  def this(input: String, delim: String) {
    this(input)
    setDelimiterString(delim)
  }

  /**
    * Constructs a tokenizer splitting using the specified delimiter matcher.
    *
    * @param input the string which is to be parsed
    * @param delim the field delimiter matcher
    */
  def this(input: String, delim: StrMatcher) {
    this(input)
    setDelimiterMatcher(delim)
  }

  /**
    * Constructs a tokenizer splitting on the specified delimiter character
    * and handling quotes using the specified quote character.
    *
    * @param input the string which is to be parsed
    * @param delim the field delimiter character
    * @param quote the field quoted string character
    */
  def this(input: String, delim: Char, quote: Char) {
    this(input, delim)
    setQuoteChar(quote)
  }

  /**
    * Constructs a tokenizer splitting using the specified delimiter matcher
    * and handling quotes using the specified quote matcher.
    *
    * @param input the string which is to be parsed
    * @param delim the field delimiter matcher
    * @param quote the field quoted string matcher
    */
  def this(input: String, delim: StrMatcher, quote: StrMatcher) {
    this(input, delim)
    setQuoteMatcher(quote)
  }

  /**
    * Constructs a tokenizer splitting on space, tab, newline and formfeed
    * as per StringTokenizer.
    *
    * @param input the string which is to be parsed, not cloned
    */
  def this(input: Array[Char]) {
    this()
    this.chars = ArrayUtils.clone(input)
  }

  /**
    * Constructs a tokenizer splitting on the specified character.
    *
    * @param input the string which is to be parsed, not cloned
    * @param delim the field delimiter character
    */
  def this(input: Array[Char], delim: Char) {
    this(input)
    setDelimiterChar(delim)
  }

  /**
    * Constructs a tokenizer splitting on the specified string.
    *
    * @param input the string which is to be parsed, not cloned
    * @param delim the field delimiter string
    */
  def this(input: Array[Char], delim: String) {
    this(input)
    setDelimiterString(delim)
  }

  /**
    * Constructs a tokenizer splitting using the specified delimiter matcher.
    *
    * @param input the string which is to be parsed, not cloned
    * @param delim the field delimiter matcher
    */
  def this(input: Array[Char], delim: StrMatcher) {
    this(input)
    setDelimiterMatcher(delim)
  }

  /**
    * Constructs a tokenizer splitting on the specified delimiter character
    * and handling quotes using the specified quote character.
    *
    * @param input the string which is to be parsed, not cloned
    * @param delim the field delimiter character
    * @param quote the field quoted string character
    */
  def this(input: Array[Char], delim: Char, quote: Char) {
    this(input, delim)
    setQuoteChar(quote)
  }

  /**
    * Constructs a tokenizer splitting using the specified delimiter matcher
    * and handling quotes using the specified quote matcher.
    *
    * @param input the string which is to be parsed, not cloned
    * @param delim the field delimiter character
    * @param quote the field quoted string character
    */
  def this(input: Array[Char], delim: StrMatcher, quote: StrMatcher) {
    this(input, delim)
    setQuoteMatcher(quote)
  }

  /**
    * Gets the number of tokens found in the String.
    *
    * @return the number of matched tokens
    */
  def size: Int = {
    checkTokenized()
    tokens.length
  }

  /**
    * Gets the next token from the String.
    * Equivalent to {@link #next} except it returns null rather than
    * throwing {@link java.util.NoSuchElementException} when no tokens remain.
    *
    * @return the next sequential token, or null when no more tokens are found
    */
  def nextToken: String = {
    if (hasNext) tokens({tokenPos += 1; tokenPos - 1})
    else null
  }

  /**
    * Gets the previous token from the String.
    *
    * @return the previous sequential token, or null when no more tokens are found
    */
  def previousToken: String = {
    if (hasPrevious) tokens({tokenPos -= 1; tokenPos})
    else null
  }

  /**
    * Gets a copy of the full token list as an independent modifiable array.
    *
    * @return the tokens as a String array
    */
  def getTokenArray: Array[String] = {
    checkTokenized()
    tokens.clone
  }

  /**
    * Gets a copy of the full token list as an independent modifiable list.
    *
    * @return the tokens as a String array
    */
  def getTokenList: util.List[String] = {
    checkTokenized()
    val list = new util.ArrayList[String](tokens.length)
    list.addAll(tokens.toList.asJava)
    list
  }

  /**
    * Resets this tokenizer, forgetting all parsing and iteration already completed.
    * <p>
    * This method allows the same tokenizer to be reused for the same String.
    *
    * @return this, to enable chaining
    */
  def reset: StrTokenizer = {
    tokenPos = 0
    tokens = null
    this
  }

  /**
    * Reset this tokenizer, giving it a new input string to parse.
    * In this manner you can re-use a tokenizer with the same settings
    * on multiple input lines.
    *
    * @param input the new string to tokenize, null sets no text to parse
    * @return this, to enable chaining
    */
  def reset(input: String): StrTokenizer = {
    reset
    if (input != null) this.chars = input.toCharArray
    else this.chars = null
    this
  }

  /**
    * Reset this tokenizer, giving it a new input string to parse.
    * In this manner you can re-use a tokenizer with the same settings
    * on multiple input lines.
    *
    * @param input the new character array to tokenize, not cloned, null sets no text to parse
    * @return this, to enable chaining
    */
  def reset(input: Array[Char]): StrTokenizer = {
    reset
    this.chars = ArrayUtils.clone(input)
    this
  }

  /**
    * Checks whether there are any more tokens.
    *
    * @return true if there are more tokens
    */
  override def hasNext: Boolean = {
    checkTokenized()
    tokenPos < tokens.length
  }

  /**
    * Gets the next token.
    *
    * @return the next String token
    * @throws java.util.NoSuchElementException if there are no more elements
    */
  override def next: String = {
    if (hasNext) return tokens({
      tokenPos += 1; tokenPos - 1
    })
    throw new NoSuchElementException
  }

  /**
    * Gets the index of the next token to return.
    *
    * @return the next token index
    */
  override def nextIndex: Int = tokenPos

  /**
    * Checks whether there are any previous tokens that can be iterated to.
    *
    * @return true if there are previous tokens
    */
  override def hasPrevious: Boolean = {
    checkTokenized()
    tokenPos > 0
  }

  /**
    * Gets the token previous to the last returned token.
    *
    * @return the previous token
    */
  override def previous: String = {
    if (hasPrevious) return tokens({
      tokenPos -= 1; tokenPos
    })
    throw new NoSuchElementException
  }

  /**
    * Gets the index of the previous token.
    *
    * @return the previous token index
    */
  override def previousIndex: Int = tokenPos - 1

  /**
    * Unsupported ListIterator operation.
    *
    * @throws java.lang.UnsupportedOperationException always
    */
  override def remove(): Unit = {
    throw new UnsupportedOperationException("remove() is unsupported")
  }

  /**
    * Unsupported ListIterator operation.
    *
    * @param obj this parameter ignored.
    * @throws java.lang.UnsupportedOperationException always
    */
  override def set(obj: String): Unit = {
    throw new UnsupportedOperationException("set() is unsupported")
  }

  override def add(obj: String): Unit = {
    throw new UnsupportedOperationException("add() is unsupported")
  }

  /**
    * Checks if tokenization has been done, and if not then do it.
    */
  private def checkTokenized(): Unit = {
    if (tokens == null) if (chars == null) { // still call tokenize as subclass may do some work
      val split = tokenize(null, 0, 0)
      tokens = split.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
    }
    else {
      val split = tokenize(chars, 0, chars.length)
      tokens = split.toArray(ArrayUtils.EMPTY_STRING_ARRAY)
    }
  }

  /**
    * Internal method to performs the tokenization.
    * <p>
    * Most users of this class do not need to call this method. This method
    * will be called automatically by other (public) methods when required.
    * <p>
    * This method exists to allow subclasses to add code before or after the
    * tokenization. For example, a subclass could alter the character array,
    * offset or count to be parsed, or call the tokenizer multiple times on
    * multiple strings. It is also be possible to filter the results.
    * <p>
    * {@code StrTokenizer} will always pass a zero offset and a count
    * equal to the length of the array to this method, however a subclass
    * may pass other values, or even an entirely different array.
    *
    * @param srcChars the character array being tokenized, may be null
    * @param offset   the start position within the character array, must be valid
    * @param count    the number of characters to tokenize, must be valid
    * @return the modifiable list of String tokens, unmodifiable if null array or zero count
    */
  protected def tokenize(srcChars: Array[Char], offset: Int, count: Int): util.List[String] = {
    if (srcChars == null || count == 0) return Collections.emptyList[String]
    val buf = new StrBuilder
    val tokenList = new util.ArrayList[String]
    var pos = offset

    // loop around the entire buffer
    while (pos >= 0 && pos < count) { // find next token
      pos = readNextToken(srcChars, pos, count, buf, tokenList)
      // handle case where end of string is a delimiter
      if (pos >= count) addToken(tokenList, StringUtils.EMPTY)
    }

    tokenList
  }

  /**
    * Adds a token to a list, paying attention to the parameters we've set.
    *
    * @param list the list to add to
    * @param tok  the token to add
    */
  private def addToken(list: util.List[String], tok: String): Unit = {
    if (StringUtils.isEmpty(tok)) {
      if (isIgnoreEmptyTokens) return
      if (isEmptyTokenAsNull) list.add(null)
    } else {
      list.add(tok)
    }
    ()
  }

  /**
    * Reads character by character through the String to get the next token.
    *
    * @param srcChars  the character array being tokenized
    * @param start     the first character of field
    * @param len       the length of the character array being tokenized
    * @param workArea  a temporary work area
    * @param tokenList the list of parsed tokens
    * @return the starting position of the next field (the character
    *         immediately after the delimiter), or -1 if end of string found
    */
  private def readNextToken(srcChars: Array[Char], start: Int, len: Int, workArea: StrBuilder, tokenList: util.List[String]): Int = { // skip all leading whitespace, unless it is the
    var pos: Int = start

    breakable {
      // field delimiter or the quote character
      while (pos < len) {
        val removeLen = Math.max(getIgnoredMatcher.isMatch(srcChars, pos, pos, len), getTrimmerMatcher.isMatch(srcChars, pos, pos, len))
        if (removeLen == 0 || getDelimiterMatcher.isMatch(srcChars, pos, pos, len) > 0 || getQuoteMatcher.isMatch(srcChars, pos, pos, len) > 0) break()
        pos += removeLen
      }
    }

    // handle reaching end
    if (pos >= len) {
      addToken(tokenList, StringUtils.EMPTY)
      return -1
    }

    // handle empty token
    val delimLen = getDelimiterMatcher.isMatch(srcChars, pos, pos, len)
    if (delimLen > 0) {
      addToken(tokenList, StringUtils.EMPTY)
      return pos + delimLen
    }

    // handle found token
    val quoteLen = getQuoteMatcher.isMatch(srcChars, pos, pos, len)
    if (quoteLen > 0) return readWithQuotes(srcChars, pos + quoteLen, len, workArea, tokenList, pos, quoteLen)
    readWithQuotes(srcChars, pos, len, workArea, tokenList, 0, 0)
  }

  /**
    * Reads a possibly quoted string token.
    *
    * @param srcChars   the character array being tokenized
    * @param start      the first character of field
    * @param len        the length of the character array being tokenized
    * @param workArea   a temporary work area
    * @param tokenList  the list of parsed tokens
    * @param quoteStart the start position of the matched quote, 0 if no quoting
    * @param quoteLen   the length of the matched quote, 0 if no quoting
    * @return the starting position of the next field (the character
    *         immediately after the delimiter, or if end of string found,
    *         then the length of string
    */
  private def readWithQuotes(srcChars: Array[Char], start: Int, len: Int, workArea: StrBuilder, tokenList: util.List[String], quoteStart: Int, quoteLen: Int): Int = { // Loop until we've found the end of the quoted
    // string or the end of the input
    workArea.clear
    var pos = start
    var quoting = quoteLen > 0
    var trimStart = 0

    while (pos < len) {
      // quoting mode can occur several times throughout a string
      // we must switch between quoting and non-quoting until we
      // encounter a non-quoted delimiter, or end of string
      if (quoting) { // In quoting mode
        // If we've found a quote character, see if it's
        // followed by a second quote.  If so, then we need
        // to actually put the quote character into the token
        // rather than end the token.
        if (isQuote(srcChars, pos, len, quoteStart, quoteLen)) {
          if (isQuote(srcChars, pos + quoteLen, len, quoteStart, quoteLen)) { // matched pair of quotes, thus an escaped quote
            workArea.append(srcChars, pos, quoteLen)
            pos += quoteLen * 2
            trimStart = workArea.size
          } else {
            // end of quoting
            quoting = false
            pos += quoteLen
          }
        } else {
          // copy regular character from inside quotes
          workArea.append(srcChars({pos += 1; pos - 1}))
          trimStart = workArea.size
        }
      }
      else { // Not in quoting mode
        // check for delimiter, and thus end of token
        val delimLen = getDelimiterMatcher.isMatch(srcChars, pos, start, len)
        if (delimLen > 0) { // return condition when end of token found
          addToken(tokenList, workArea.substring(0, trimStart))
          return pos + delimLen
        }
        // check for quote, and thus back into quoting mode
        if (quoteLen > 0 && isQuote(srcChars, pos, len, quoteStart, quoteLen)) {
          quoting = true
          pos += quoteLen
        } else {
          // check for ignored (outside quotes), and ignore
          val ignoredLen = getIgnoredMatcher.isMatch(srcChars, pos, start, len)
          if (ignoredLen > 0) {
            pos += ignoredLen
          } else {
            // check for trimmed character
            // don't yet know if its at the end, so copy to workArea
            // use trimStart to keep track of trim at the end
            val trimmedLen = getTrimmerMatcher.isMatch(srcChars, pos, start, len)
            if (trimmedLen > 0) {
              workArea.append(srcChars, pos, trimmedLen)
              pos += trimmedLen
            } else {
              // copy regular character from outside quotes
              workArea.append(srcChars({pos += 1; pos - 1}))
              trimStart = workArea.size
            }
          }
        }
      }
    }
    // return condition when end of string found
    addToken(tokenList, workArea.substring(0, trimStart))
    -1
  }

  /**
    * Checks if the characters at the index specified match the quote
    * already matched in readNextToken().
    *
    * @param srcChars   the character array being tokenized
    * @param pos        the position to check for a quote
    * @param len        the length of the character array being tokenized
    * @param quoteStart the start position of the matched quote, 0 if no quoting
    * @param quoteLen   the length of the matched quote, 0 if no quoting
    * @return true if a quote is matched
    */
  private def isQuote(srcChars: Array[Char], pos: Int, len: Int, quoteStart: Int, quoteLen: Int): Boolean = {
    for (i <- 0 until quoteLen) {
      if (pos + i >= len || srcChars(pos + i) != srcChars(quoteStart + i)) return false
    }
    true
  }

  /**
    * Gets the field delimiter matcher.
    *
    * @return the delimiter matcher in use
    */
  def getDelimiterMatcher: StrMatcher = this.delimMatcher

  /**
    * Sets the field delimiter matcher.
    * <p>
    * The delimiter is used to separate one token from another.
    *
    * @param delim the delimiter matcher to use
    * @return this, to enable chaining
    */
  def setDelimiterMatcher(delim: StrMatcher): StrTokenizer = {
    if (delim == null) this.delimMatcher = StrMatcher.noneMatcher
    else this.delimMatcher = delim
    this
  }

  /**
    * Sets the field delimiter character.
    *
    * @param delim the delimiter character to use
    * @return this, to enable chaining
    */
  def setDelimiterChar(delim: Char): StrTokenizer = setDelimiterMatcher(StrMatcher.charMatcher(delim))

  /**
    * Sets the field delimiter string.
    *
    * @param delim the delimiter string to use
    * @return this, to enable chaining
    */
  def setDelimiterString(delim: String): StrTokenizer = setDelimiterMatcher(StrMatcher.stringMatcher(delim))

  /**
    * Gets the quote matcher currently in use.
    * <p>
    * The quote character is used to wrap data between the tokens.
    * This enables delimiters to be entered as data.
    * The default value is '"' (double quote).
    *
    * @return the quote matcher in use
    */
  def getQuoteMatcher: StrMatcher = quoteMatcher

  /**
    * Set the quote matcher to use.
    * <p>
    * The quote character is used to wrap data between the tokens.
    * This enables delimiters to be entered as data.
    *
    * @param quote the quote matcher to use, null ignored
    * @return this, to enable chaining
    */
  def setQuoteMatcher(quote: StrMatcher): StrTokenizer = {
    if (quote != null) this.quoteMatcher = quote
    this
  }

  /**
    * Sets the quote character to use.
    * <p>
    * The quote character is used to wrap data between the tokens.
    * This enables delimiters to be entered as data.
    *
    * @param quote the quote character to use
    * @return this, to enable chaining
    */
  def setQuoteChar(quote: Char): StrTokenizer = setQuoteMatcher(StrMatcher.charMatcher(quote))

  /**
    * Gets the ignored character matcher.
    * <p>
    * These characters are ignored when parsing the String, unless they are
    * within a quoted region.
    * The default value is not to ignore anything.
    *
    * @return the ignored matcher in use
    */
  def getIgnoredMatcher: StrMatcher = ignoredMatcher

  /**
    * Set the matcher for characters to ignore.
    * <p>
    * These characters are ignored when parsing the String, unless they are
    * within a quoted region.
    *
    * @param ignored the ignored matcher to use, null ignored
    * @return this, to enable chaining
    */
  def setIgnoredMatcher(ignored: StrMatcher): StrTokenizer = {
    if (ignored != null) this.ignoredMatcher = ignored
    this
  }

  /**
    * Set the character to ignore.
    * <p>
    * This character is ignored when parsing the String, unless it is
    * within a quoted region.
    *
    * @param ignored the ignored character to use
    * @return this, to enable chaining
    */
  def setIgnoredChar(ignored: Char): StrTokenizer = setIgnoredMatcher(StrMatcher.charMatcher(ignored))

  /**
    * Gets the trimmer character matcher.
    * <p>
    * These characters are trimmed off on each side of the delimiter
    * until the token or quote is found.
    * The default value is not to trim anything.
    *
    * @return the trimmer matcher in use
    */
  def getTrimmerMatcher: StrMatcher = trimmerMatcher

  /**
    * Sets the matcher for characters to trim.
    * <p>
    * These characters are trimmed off on each side of the delimiter
    * until the token or quote is found.
    *
    * @param trimmer the trimmer matcher to use, null ignored
    * @return this, to enable chaining
    */
  def setTrimmerMatcher(trimmer: StrMatcher): StrTokenizer = {
    if (trimmer != null) this.trimmerMatcher = trimmer
    this
  }

  /**
    * Gets whether the tokenizer currently returns empty tokens as null.
    * The default for this property is false.
    *
    * @return true if empty tokens are returned as null
    */
  def isEmptyTokenAsNull: Boolean = this.emptyAsNull

  /**
    * Sets whether the tokenizer should return empty tokens as null.
    * The default for this property is false.
    *
    * @param emptyAsNull whether empty tokens are returned as null
    * @return this, to enable chaining
    */
  def setEmptyTokenAsNull(emptyAsNull: Boolean): StrTokenizer = {
    this.emptyAsNull = emptyAsNull
    this
  }

  /**
    * Gets whether the tokenizer currently ignores empty tokens.
    * The default for this property is true.
    *
    * @return true if empty tokens are not returned
    */
  def isIgnoreEmptyTokens: Boolean = ignoreEmptyTokens

  /**
    * Sets whether the tokenizer should ignore and not return empty tokens.
    * The default for this property is true.
    *
    * @param ignoreEmptyTokens whether empty tokens are not returned
    * @return this, to enable chaining
    */
  def setIgnoreEmptyTokens(ignoreEmptyTokens: Boolean): StrTokenizer = {
    this.ignoreEmptyTokens = ignoreEmptyTokens
    this
  }

  /**
    * Gets the String content that the tokenizer is parsing.
    *
    * @return the string content being parsed
    */
  def getContent: String = {
    if (chars == null) return null
    new String(chars)
  }

  /**
    * Creates a new instance of this Tokenizer. The new instance is reset so
    * that it will be at the start of the token list.
    * If a {@link java.lang.CloneNotSupportedException} is caught, return {@code null}.
    *
    * @return a new instance of this Tokenizer which has been reset.
    */
  override def clone: AnyRef = try {
    cloneReset
  } catch {
    case _: CloneNotSupportedException => null
  }

  /**
    * Creates a new instance of this Tokenizer. The new instance is reset so that
    * it will be at the start of the token list.
    *
    * @return a new instance of this Tokenizer which has been reset.
    * @throws CloneNotSupportedException if there is a problem cloning
    */
  @throws[CloneNotSupportedException]
  private[text] def cloneReset = { // this method exists to enable 100% test coverage
    val cloned = super.clone.asInstanceOf[StrTokenizer]
    if (cloned.chars != null) cloned.chars = cloned.chars.clone
    cloned.reset
    cloned
  }

  override def toString: String = {
    if (tokens == null) return "StrTokenizer[not tokenized yet]"
    "StrTokenizer" + getTokenList
  }
}