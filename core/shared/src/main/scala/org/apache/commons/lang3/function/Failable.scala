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

//package org.apache.commons.lang3.function
//
//import java.io.IOException
//import java.io.UncheckedIOException
//import java.lang.reflect.UndeclaredThrowableException
//import java.util
//import java.util.Objects
//import java.util.concurrent.Callable
//import java.util.function.BiConsumer
//import java.util.function.BiFunction
//import java.util.function.BiPredicate
//import java.util.function.Consumer
//import java.util.function.Function
//import java.util.function.Predicate
//import java.util.function.Supplier
//import java.util.stream.Stream
//import org.apache.commons.lang3.stream.Streams.FailableStream
//
///**
//  * This class provides utility functions, and classes for working with the {@code java.util.function} package, or more
//  * generally, with Java 8 lambdas. More specifically, it attempts to address the fact that lambdas are supposed not to
//  * throw Exceptions, at least not checked Exceptions, AKA instances of {@link Exception}. This enforces the use of
//  * constructs like:
//  *
//  * <pre>
//  * Consumer&lt;java.lang.reflect.Method-&gt; consumer = (m) -&gt; {
//  * try {
//  * m.invoke(o, args);
//  * } catch (Throwable t) {
//  * throw Failable.rethrow(t);
//  * }
//  * };
//  * </pre>
//  *
//  * <p>
//  * By replacing a {@link java.util.function.Consumer Consumer&lt;O&gt;} with a {@link FailableConsumer
//  * FailableConsumer&lt;O,? extends Throwable&gt;}, this can be written like follows:
//  * </p>
//  *
//  * <pre>
//  * Functions.accept((m) -&gt; m.invoke(o, args));
//  * </pre>
//  *
//  * <p>
//  * Obviously, the second version is much more concise and the spirit of Lambda expressions is met better than the second
//  * version.
//  * </p>
//  *
//  * @since 3.11
//  */
//object Failable {
//  /**
//    * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param consumer the consumer to consume
//    * @param object1  the first object to consume by {@code consumer}
//    * @param object2  the second object to consume by {@code consumer}
//    * @param <        T> the type of the first argument the consumer accepts
//    * @param <        U> the type of the second argument the consumer accepts
//    * @param <        E> the type of checked exception the consumer may throw
//    */
//  def accept[T, U, E <: Throwable](consumer: Nothing, object1: T, object2: U): Unit = {
//    run(() => consumer.accept(object1, object2))
//  }
//
//  /**
//    * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param consumer the consumer to consume
//    * @param object   the object to consume by {@code consumer}
//    * @param <        T> the type the consumer accepts
//    * @param <        E> the type of checked exception the consumer may throw
//    */
//  def accept[T, E <: Throwable](consumer: Nothing, `object`: T): Unit = {
//    run(() => consumer.accept(`object`))
//  }
//
//  /**
//    * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param consumer the consumer to consume
//    * @param value    the value to consume by {@code consumer}
//    * @param <        E> the type of checked exception the consumer may throw
//    */
//  def accept[E <: Throwable](consumer: Nothing, value: Double): Unit = {
//    run(() => consumer.accept(value))
//  }
//
//  def accept[E <: Throwable](consumer: Nothing, value: Int): Unit = {
//    run(() => consumer.accept(value))
//  }
//
//  def accept[E <: Throwable](consumer: Nothing, value: Long): Unit = {
//    run(() => consumer.accept(value))
//  }
//
//  /**
//    * Applies a function and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param function the function to apply
//    * @param input1   the first input to apply {@code function} on
//    * @param input2   the second input to apply {@code function} on
//    * @param <        T> the type of the first argument the function accepts
//    * @param <        U> the type of the second argument the function accepts
//    * @param <        R> the return type of the function
//    * @param <        E> the type of checked exception the function may throw
//    * @return the value returned from the function
//    */
//  def apply[T, U, R, E <: Throwable](function: Nothing, input1: T, input2: U): R = get(() => function.apply(input1, input2))
//
//  /**
//    * Applies a function and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param function the function to apply
//    * @param input    the input to apply {@code function} on
//    * @param <        T> the type of the argument the function accepts
//    * @param <        R> the return type of the function
//    * @param <        E> the type of checked exception the function may throw
//    * @return the value returned from the function
//    */
//  def apply[T, R, E <: Throwable](function: Nothing, input: T): R = get(() => function.apply(input))
//
//  /**
//    * Applies a function and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param function the function to apply
//    * @param left     the first input to apply {@code function} on
//    * @param right    the second input to apply {@code function} on
//    * @param <        E> the type of checked exception the function may throw
//    * @return the value returned from the function
//    */
//  def applyAsDouble[E <: Throwable](function: Nothing, left: Double, right: Double): Double = getAsDouble(() => function.applyAsDouble(left, right))
//
//  /**
//    * Converts the given {@link FailableBiConsumer} into a standard {@link BiConsumer}.
//    *
//    * @param <        T> the type of the first argument of the consumers
//    * @param <        U> the type of the second argument of the consumers
//    * @param consumer a failable {@code BiConsumer}
//    * @return a standard {@code BiConsumer}
//    */
//  def asBiConsumer[T, U](consumer: Nothing): BiConsumer[T, U] = (input1: T, input2: U) => accept(consumer, input1, input2)
//
//  /**
//    * Converts the given {@link FailableBiFunction} into a standard {@link BiFunction}.
//    *
//    * @param <        T> the type of the first argument of the input of the functions
//    * @param <        U> the type of the second argument of the input of the functions
//    * @param <        R> the type of the output of the functions
//    * @param function a {@code FailableBiFunction}
//    * @return a standard {@code BiFunction}
//    */
//  def asBiFunction[T, U, R](function: Nothing): BiFunction[T, U, R] = (input1: T, input2: U) => apply(function, input1, input2)
//
//  /**
//    * Converts the given {@link FailableBiPredicate} into a standard {@link BiPredicate}.
//    *
//    * @param <         T> the type of the first argument used by the predicates
//    * @param <         U> the type of the second argument used by the predicates
//    * @param predicate a {@code FailableBiPredicate}
//    * @return a standard {@code BiPredicate}
//    */
//  def asBiPredicate[T, U](predicate: Nothing): BiPredicate[T, U] = (input1: T, input2: U) => test(predicate, input1, input2)
//
//  /**
//    * Converts the given {@link FailableCallable} into a standard {@link Callable}.
//    *
//    * @param <        V> the type used by the callables
//    * @param callable a {@code FailableCallable}
//    * @return a standard {@code Callable}
//    */
//  def asCallable[V](callable: Nothing): Callable[V] = () => call(callable)
//
//  /**
//    * Converts the given {@link FailableConsumer} into a standard {@link Consumer}.
//    *
//    * @param <        T> the type used by the consumers
//    * @param consumer a {@code FailableConsumer}
//    * @return a standard {@code Consumer}
//    */
//  def asConsumer[T](consumer: Nothing): Consumer[T] = (input: T) => accept(consumer, input)
//
//  /**
//    * Converts the given {@link FailableFunction} into a standard {@link Function}.
//    *
//    * @param <        T> the type of the input of the functions
//    * @param <        R> the type of the output of the functions
//    * @param function a {code FailableFunction}
//    * @return a standard {@code Function}
//    */
//  def asFunction[T, R](function: Nothing): Function[T, R] = (input: T) => apply(function, input)
//
//  /**
//    * Converts the given {@link FailablePredicate} into a standard {@link Predicate}.
//    *
//    * @param <         T> the type used by the predicates
//    * @param predicate a {@code FailablePredicate}
//    * @return a standard {@code Predicate}
//    */
//  def asPredicate[T](predicate: Nothing): Predicate[T] = (input: T) => test(predicate, input)
//
//  /**
//    * Converts the given {@link FailableRunnable} into a standard {@link Runnable}.
//    *
//    * @param runnable a {@code FailableRunnable}
//    * @return a standard {@code Runnable}
//    */
//  def asRunnable(runnable: Nothing): Runnable = () => run(runnable)
//
//  /**
//    * Converts the given {@link FailableSupplier} into a standard {@link Supplier}.
//    *
//    * @param <        T> the type supplied by the suppliers
//    * @param supplier a {@code FailableSupplier}
//    * @return a standard {@code Supplier}
//    */
//  def asSupplier[T](supplier: Nothing): Supplier[T] = () => get(supplier)
//
//  /**
//    * Calls a callable and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param callable the callable to call
//    * @param <        V> the return type of the callable
//    * @param <        E> the type of checked exception the callable may throw
//    * @return the value returned from the callable
//    */
//  def call[V, E <: Throwable](callable: Nothing): V = get(callable.call)
//
//  /**
//    * Invokes a supplier, and returns the result.
//    *
//    * @param supplier The supplier to invoke.
//    * @param <        T> The suppliers output type.
//    * @param <        E> The type of checked exception, which the supplier can throw.
//    * @return The object, which has been created by the supplier
//    */
//  def get[T, E <: Throwable](supplier: Nothing): T = try supplier.get
//  catch {
//    case t: Throwable =>
//      throw rethrow(t)
//  }
//
//  /**
//    * Invokes a boolean supplier, and returns the result.
//    *
//    * @param supplier The boolean supplier to invoke.
//    * @param <        E> The type of checked exception, which the supplier can throw.
//    * @return The boolean, which has been created by the supplier
//    */
//  def getAsBoolean[E <: Throwable](supplier: Nothing): Boolean = try supplier.getAsBoolean
//  catch {
//    case t: Throwable =>
//      throw rethrow(t)
//  }
//
//  /**
//    * Invokes a double supplier, and returns the result.
//    *
//    * @param supplier The double supplier to invoke.
//    * @param <        E> The type of checked exception, which the supplier can throw.
//    * @return The boolean, which has been created by the supplier
//    */
//  def getAsDouble[E <: Throwable](supplier: Nothing): Double = try supplier.getAsDouble
//  catch {
//    case t: Throwable =>
//      throw rethrow(t)
//  }
//
//  /**
//    * Invokes an int supplier, and returns the result.
//    *
//    * @param supplier The int supplier to invoke.
//    * @param <        E> The type of checked exception, which the supplier can throw.
//    * @return The boolean, which has been created by the supplier
//    */
//  def getAsInt[E <: Throwable](supplier: Nothing): Int = try supplier.getAsInt
//  catch {
//    case t: Throwable =>
//      throw rethrow(t)
//  }
//
//  /**
//    * Invokes a long supplier, and returns the result.
//    *
//    * @param supplier The long supplier to invoke.
//    * @param <        E> The type of checked exception, which the supplier can throw.
//    * @return The boolean, which has been created by the supplier
//    */
//  def getAsLong[E <: Throwable](supplier: Nothing): Long = try supplier.getAsLong
//  catch {
//    case t: Throwable =>
//      throw rethrow(t)
//  }
//
//  /**
//    * <p>
//    * Rethrows a {@link Throwable} as an unchecked exception. If the argument is already unchecked, namely a
//    * {@code RuntimeException} or {@code Error} then the argument will be rethrown without modification. If the
//    * exception is {@code IOException} then it will be wrapped into a {@code UncheckedIOException}. In every other
//    * cases the exception will be wrapped into a {@code
//    * UndeclaredThrowableException}
//    * </p>
//    *
//    * <p>
//    * Note that there is a declared return type for this method, even though it never returns. The reason for that is
//    * to support the usual pattern:
//    * </p>
//    *
//    * <pre>
//    * throw rethrow(myUncheckedException);
//    * </pre>
//    *
//    * <p>
//    * instead of just calling the method. This pattern may help the Java compiler to recognize that at that point an
//    * exception will be thrown and the code flow analysis will not demand otherwise mandatory commands that could
//    * follow the method call, like a {@code return} statement from a value returning method.
//    * </p>
//    *
//    * @param throwable The throwable to rethrow ossibly wrapped into an unchecked exception
//    * @return Never returns anything, this method never terminates normally.
//    */
//  def rethrow(throwable: Throwable): RuntimeException = {
//    Objects.requireNonNull(throwable, "throwable")
//    if (throwable.isInstanceOf[RuntimeException]) throw throwable.asInstanceOf[RuntimeException]
//    else if (throwable.isInstanceOf[Error]) throw throwable.asInstanceOf[Error]
//    else if (throwable.isInstanceOf[IOException]) throw new UncheckedIOException(throwable.asInstanceOf[IOException])
//    else throw new UndeclaredThrowableException(throwable)
//  }
//
//  /**
//    * Runs a runnable and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param runnable The runnable to run
//    * @param <        E> the type of checked exception the runnable may throw
//    */
//  def run[E <: Throwable](runnable: Nothing): Unit = {
//    try runnable.run
//    catch {
//      case t: Throwable =>
//        throw rethrow(t)
//    }
//  }
//
//  /**
//    * Converts the given collection into a {@link FailableStream}. The {@link FailableStream} consists of the
//    * collections elements. Shortcut for
//    *
//    * <pre>
//    * Functions.stream(collection.stream());
//    * </pre>
//    *
//    * @param collection The collection, which is being converted into a {@link FailableStream}.
//    * @param <          E> The collections element type. (In turn, the result streams element type.)
//    * @return The created {@link FailableStream}.
//    */
//  def stream[E](collection: util.Collection[E]) = new Nothing(collection.stream)
//
//  /**
//    * Converts the given stream into a {@link FailableStream}. The {@link FailableStream} consists of the same
//    * elements, than the input stream. However, failable lambdas, like {@link FailablePredicate},
//    * {@link FailableFunction}, and {@link FailableConsumer} may be applied, rather than {@link Predicate},
//    * {@link Function}, {@link Consumer}, etc.
//    *
//    * @param stream The stream, which is being converted into a {@link FailableStream}.
//    * @param <      T> The streams element type.
//    * @return The created {@link FailableStream}.
//    */
//  def stream[T](stream: Stream[T]) = new Nothing(stream)
//
//  /**
//    * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param predicate the predicate to test
//    * @param object1   the first input to test by {@code predicate}
//    * @param object2   the second input to test by {@code predicate}
//    * @param <         T> the type of the first argument the predicate tests
//    * @param <         U> the type of the second argument the predicate tests
//    * @param <         E> the type of checked exception the predicate may throw
//    * @return the boolean value returned by the predicate
//    */
//  def test[T, U, E <: Throwable](predicate: Nothing, object1: T, object2: U): Boolean = getAsBoolean(() => predicate.test(object1, object2))
//
//  /**
//    * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param predicate the predicate to test
//    * @param object    the input to test by {@code predicate}
//    * @param <         T> the type of argument the predicate tests
//    * @param <         E> the type of checked exception the predicate may throw
//    * @return the boolean value returned by the predicate
//    */
//  def test[T, E <: Throwable](predicate: Nothing, `object`: T): Boolean = getAsBoolean(() => predicate.test(`object`))
//
//  /**
//    * A simple try-with-resources implementation, that can be used, if your objects do not implement the
//    * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
//    * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
//    * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
//    * {@link Throwable} is rethrown. Example use:
//    *
//    * <pre>
//    * final FileInputStream fis = new FileInputStream("my.file");
//    * Functions.tryWithResources(useInputStream(fis), null, () -&gt; fis.close());
//    * </pre>
//    *
//    * @param action       The action to execute. This object <em>will</em> always be invoked.
//    * @param errorHandler An optional error handler, which will be invoked finally, if any error occurred. The error
//    *                     handler will receive the first error, AKA {@link Throwable}.
//    * @param resources    The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
//    *                     order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
//    * @see #tryWithResources(FailableRunnable, FailableRunnable...)
//    */
//  @SafeVarargs def tryWithResources(action: Nothing, errorHandler: Nothing, resources: Nothing*): Unit = {
//    var actualErrorHandler = null
//    if (errorHandler == null) actualErrorHandler = Failable.rethrow
//    else actualErrorHandler = errorHandler
//    if (resources != null) for (failableRunnable <- resources) {
//      Objects.requireNonNull(failableRunnable, "runnable")
//    }
//    var th = null
//    try action.run
//    catch {
//      case t: Throwable =>
//        th = t
//    }
//    if (resources != null) for (runnable <- resources) {
//      try runnable.run
//      catch {
//        case t: Throwable =>
//          if (th == null) th = t
//      }
//    }
//    if (th != null) try actualErrorHandler.accept(th)
//    catch {
//      case t: Throwable =>
//        throw rethrow(t)
//    }
//  }
//
//  /**
//    * A simple try-with-resources implementation, that can be used, if your objects do not implement the
//    * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
//    * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
//    * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
//    * {@link Throwable} is rethrown. Example use:
//    *
//    * <pre>
//    * final FileInputStream fis = new FileInputStream("my.file");
//    * Functions.tryWithResources(useInputStream(fis), () -&gt; fis.close());
//    * </pre>
//    *
//    * @param action    The action to execute. This object <em>will</em> always be invoked.
//    * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
//    *                  order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
//    * @see #tryWithResources(FailableRunnable, FailableConsumer, FailableRunnable...)
//    */
//  @SafeVarargs def tryWithResources(action: Nothing, resources: Nothing*): Unit = {
//    tryWithResources(action, null, resources)
//  }
//}
