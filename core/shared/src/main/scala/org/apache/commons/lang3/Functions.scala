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
//import org.apache.commons.lang3.Streams.FailableStream
//import org.apache.commons.lang3.function.FailableBooleanSupplier
//
///**
//  * This class provides utility functions, and classes for working with the {@code java.util.function} package, or more
//  * generally, with Java 8 lambdas. More specifically, it attempts to address the fact that lambdas are supposed not to
//  * throw Exceptions, at least not checked Exceptions, AKA instances of {@link Exception}. This enforces the use of
//  * constructs like:
//  *
//  * <pre>
//  * {@code
//  * Consumer<java.lang.reflect.Method> consumer = (m) -> {
//  * try {
//  * m.invoke(o, args);
//  * } catch (Throwable t) {
//  * throw Functions.rethrow(t);
//  * }
//  * };
//  * }</pre>
//  *
//  * <p>
//  * By replacing a {@link java.util.function.Consumer Consumer&lt;O&gt;} with a {@link FailableConsumer
//  * FailableConsumer&lt;O,? extends Throwable&gt;}, this can be written like follows:
//  * </p>
//  *
//  * <pre>
//  * {@code
//  * Functions.accept((m) -> m.invoke(o,args));
//  * }</pre>
//  *
//  * <p>
//  * Obviously, the second version is much more concise and the spirit of Lambda expressions is met better than the second
//  * version.
//  * </p>
//  * @since 3.9
//  * @deprecated Use {@link org.apache.commons.lang3.function.Failable}.
//  */
//@deprecated object Functions {
//
//  /**
//    * A functional interface like {@link BiConsumer} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < O1> Consumed type 1.
//    * @param < O2> Consumed type 2.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableBiConsumer}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableBiConsumer[O1, O2, T <: Throwable] {
//    /**
//      * Accepts the consumer.
//      *
//      * @param object1 the first parameter for the consumable to accept
//      * @param object2 the second parameter for the consumable to accept
//      * @throws T Thrown when the consumer fails.
//      */
//    @throws[T]
//    def accept(object1: O1, object2: O2): Unit
//  }
//
//  /**
//    * A functional interface like {@link BiFunction} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < O1> Input type 1.
//    * @param < O2> Input type 2.
//    * @param < R> Return type.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableBiFunction}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableBiFunction[O1, O2, R, T <: Throwable] {
//    /**
//      * Applies this function.
//      *
//      * @param input1 the first input for the function
//      * @param input2 the second input for the function
//      * @return the result of the function
//      * @throws T Thrown when the function fails.
//      */
//    @throws[T]
//    def apply(input1: O1, input2: O2): R
//  }
//
//  /**
//    * A functional interface like {@link BiPredicate} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < O1> Predicate type 1.
//    * @param < O2> Predicate type 2.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableBiPredicate}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableBiPredicate[O1, O2, T <: Throwable] {
//    /**
//      * Tests the predicate.
//      *
//      * @param object1 the first object to test the predicate on
//      * @param object2 the second object to test the predicate on
//      * @return the predicate's evaluation
//      * @throws T if the predicate fails
//      */
//    @throws[T]
//    def test(object1: O1, object2: O2): Boolean
//  }
//
//  /**
//    * A functional interface like {@link java.util.concurrent.Callable} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < R> Return type.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableCallable}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableCallable[R, T <: Throwable] {
//    /**
//      * Calls the callable.
//      *
//      * @return The value returned from the callable
//      * @throws T if the callable fails
//      */
//    @throws[T]
//    def call: R
//  }
//
//  /**
//    * A functional interface like {@link Consumer} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < O> Consumed type 1.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableConsumer}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableConsumer[O, T <: Throwable] {
//    /**
//      * Accepts the consumer.
//      *
//      * @param object the parameter for the consumable to accept
//      * @throws T Thrown when the consumer fails.
//      */
//    @throws[T]
//    def accept(`object`: O): Unit
//  }
//
//  /**
//    * A functional interface like {@link Function} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < I> Input type 1.
//    * @param < R> Return type.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableFunction}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableFunction[I, R, T <: Throwable] {
//    /**
//      * Applies this function.
//      *
//      * @param input the input for the function
//      * @return the result of the function
//      * @throws T Thrown when the function fails.
//      */
//    @throws[T]
//    def apply(input: I): R
//  }
//
//  /**
//    * A functional interface like {@link Predicate} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < I> Predicate type 1.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailablePredicate}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailablePredicate[I, T <: Throwable] {
//    /**
//      * Tests the predicate.
//      *
//      * @param object the object to test the predicate on
//      * @return the predicate's evaluation
//      * @throws T if the predicate fails
//      */
//    @throws[T]
//    def test(`object`: I): Boolean
//  }
//
//  /**
//    * A functional interface like {@link Runnable} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableRunnable}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableRunnable[T <: Throwable] {
//    /**
//      * Runs the function.
//      *
//      * @throws T Thrown when the function fails.
//      */
//    @throws[T]
//    def run(): Unit
//  }
//
//  /**
//    * A functional interface like {@link Supplier} that declares a {@code Throwable}.
//    *
//    * <p>TODO for 4.0: Move to org.apache.commons.lang3.function.</p>
//    *
//    * @param < R> Return type.
//    * @param < T> Thrown exception.
//    * @deprecated Use {@link org.apache.commons.lang3.function.FailableSupplier}.
//    */
//  @deprecated
//  @FunctionalInterface trait FailableSupplier[R, T <: Throwable] {
//    /**
//      * Supplies an object
//      *
//      * @return a result
//      * @throws T if the supplier fails
//      */
//    @throws[T]
//    def get: R
//  }
//
//  /**
//    * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param consumer the consumer to consume
//    * @param object1  the first object to consume by {@code consumer}
//    * @param object2  the second object to consume by {@code consumer}
//    * @param <        O1> the type of the first argument the consumer accepts
//    * @param <        O2> the type of the second argument the consumer accepts
//    * @param <        T> the type of checked exception the consumer may throw
//    */
//  def accept[O1, O2, T <: Throwable](consumer: Functions.FailableBiConsumer[O1, O2, T], object1: O1, object2: O2): Unit = {
//    run(() => consumer.accept(object1, object2))
//  }
//
//  /**
//    * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param consumer the consumer to consume
//    * @param object   the object to consume by {@code consumer}
//    * @param <        O> the type the consumer accepts
//    * @param <        T> the type of checked exception the consumer may throw
//    */
//  def accept[O, T <: Throwable](consumer: Functions.FailableConsumer[O, T], `object`: O): Unit = {
//    run(() => consumer.accept(`object`))
//  }
//
//  /**
//    * Applies a function and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param function the function to apply
//    * @param input1   the first input to apply {@code function} on
//    * @param input2   the second input to apply {@code function} on
//    * @param <        O1> the type of the first argument the function accepts
//    * @param <        O2> the type of the second argument the function accepts
//    * @param <        O> the return type of the function
//    * @param <        T> the type of checked exception the function may throw
//    * @return the value returned from the function
//    */
//  def apply[O1, O2, O, T <: Throwable](function: Functions.FailableBiFunction[O1, O2, O, T], input1: O1, input2: O2): O = get(() => function.apply(input1, input2))
//
//  /**
//    * Applies a function and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param function the function to apply
//    * @param input    the input to apply {@code function} on
//    * @param <        I> the type of the argument the function accepts
//    * @param <        O> the return type of the function
//    * @param <        T> the type of checked exception the function may throw
//    * @return the value returned from the function
//    */
//  def apply[I, O, T <: Throwable](function: Functions.FailableFunction[I, O, T], input: I): O = get(() => function.apply(input))
//
//  /**
//    * Converts the given {@link FailableBiConsumer} into a standard {@link BiConsumer}.
//    *
//    * @param <        O1> the type of the first argument of the consumers
//    * @param <        O2> the type of the second argument of the consumers
//    * @param consumer a failable {@code BiConsumer}
//    * @return a standard {@code BiConsumer}
//    * @since 3.10
//    */
//  def asBiConsumer[O1, O2](consumer: Functions.FailableBiConsumer[O1, O2, _]): BiConsumer[O1, O2] = (input1: O1, input2: O2) => accept(consumer, input1, input2)
//
//  /**
//    * Converts the given {@link FailableBiFunction} into a standard {@link BiFunction}.
//    *
//    * @param <        O1> the type of the first argument of the input of the functions
//    * @param <        O2> the type of the second argument of the input of the functions
//    * @param <        O> the type of the output of the functions
//    * @param function a {@code FailableBiFunction}
//    * @return a standard {@code BiFunction}
//    * @since 3.10
//    */
//  def asBiFunction[O1, O2, O](function: Functions.FailableBiFunction[O1, O2, O, _]): BiFunction[O1, O2, O] = (input1: O1, input2: O2) => apply(function, input1, input2)
//
//  /**
//    * Converts the given {@link FailableBiPredicate} into a standard {@link BiPredicate}.
//    *
//    * @param <         O1> the type of the first argument used by the predicates
//    * @param <         O2> the type of the second argument used by the predicates
//    * @param predicate a {@code FailableBiPredicate}
//    * @return a standard {@code BiPredicate}
//    * @since 3.10
//    */
//  def asBiPredicate[O1, O2](predicate: Functions.FailableBiPredicate[O1, O2, _]): BiPredicate[O1, O2] = (input1: O1, input2: O2) => test(predicate, input1, input2)
//
//  /**
//    * Converts the given {@link FailableCallable} into a standard {@link Callable}.
//    *
//    * @param <        O> the type used by the callables
//    * @param callable a {@code FailableCallable}
//    * @return a standard {@code Callable}
//    * @since 3.10
//    */
//  def asCallable[O](callable: Functions.FailableCallable[O, _]): Callable[O] = () => call(callable)
//
//  /**
//    * Converts the given {@link FailableConsumer} into a standard {@link Consumer}.
//    *
//    * @param <        I> the type used by the consumers
//    * @param consumer a {@code FailableConsumer}
//    * @return a standard {@code Consumer}
//    * @since 3.10
//    */
//  def asConsumer[I](consumer: Functions.FailableConsumer[I, _]): Consumer[I] = (input: I) => accept(consumer, input)
//
//  /**
//    * Converts the given {@link FailableFunction} into a standard {@link Function}.
//    *
//    * @param <        I> the type of the input of the functions
//    * @param <        O> the type of the output of the functions
//    * @param function a {code FailableFunction}
//    * @return a standard {@code Function}
//    * @since 3.10
//    */
//  def asFunction[I, O](function: Functions.FailableFunction[I, O, _]): Function[I, O] = (input: I) => apply(function, input)
//
//  /**
//    * Converts the given {@link FailablePredicate} into a standard {@link Predicate}.
//    *
//    * @param <         I> the type used by the predicates
//    * @param predicate a {@code FailablePredicate}
//    * @return a standard {@code Predicate}
//    * @since 3.10
//    */
//  def asPredicate[I](predicate: Functions.FailablePredicate[I, _]): Predicate[I] = (input: I) => test(predicate, input)
//
//  /**
//    * Converts the given {@link FailableRunnable} into a standard {@link Runnable}.
//    *
//    * @param runnable a {@code FailableRunnable}
//    * @return a standard {@code Runnable}
//    * @since 3.10
//    */
//  def asRunnable(runnable: Functions.FailableRunnable[_]): Runnable = () => run(runnable)
//
//  /**
//    * Converts the given {@link FailableSupplier} into a standard {@link Supplier}.
//    *
//    * @param <        O> the type supplied by the suppliers
//    * @param supplier a {@code FailableSupplier}
//    * @return a standard {@code Supplier}
//    * @since 3.10
//    */
//  def asSupplier[O](supplier: Functions.FailableSupplier[O, _]): Supplier[O] = () => get(supplier)
//
//  /**
//    * Calls a callable and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param callable the callable to call
//    * @param <        O> the return type of the callable
//    * @param <        T> the type of checked exception the callable may throw
//    * @return the value returned from the callable
//    */
//  def call[O, T <: Throwable](callable: Functions.FailableCallable[O, T]): O = get(callable.call)
//
//  /**
//    * Invokes a supplier, and returns the result.
//    *
//    * @param supplier The supplier to invoke.
//    * @param <        O> The suppliers output type.
//    * @param <        T> The type of checked exception, which the supplier can throw.
//    * @return The object, which has been created by the supplier
//    * @since 3.10
//    */
//  def get[O, T <: Throwable](supplier: Functions.FailableSupplier[O, T]): O = try supplier.get
//  catch {
//    case t: Throwable =>
//      throw rethrow(t)
//  }
//
//  /**
//    * Invokes a boolean supplier, and returns the result.
//    *
//    * @param supplier The boolean supplier to invoke.
//    * @param <        T> The type of checked exception, which the supplier can throw.
//    * @return The boolean, which has been created by the supplier
//    */
//  private def getAsBoolean[T <: Throwable](supplier: Nothing) = try supplier.getAsBoolean
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
//    * throw rethrow(myUncheckedException);</pre>
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
//    * @param <        T> the type of checked exception the runnable may throw
//    */
//  def run[T <: Throwable](runnable: Functions.FailableRunnable[T]): Unit = {
//    try runnable.run()
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
//    * Functions.stream(collection.stream());</pre>
//    *
//    * @param collection The collection, which is being converted into a {@link FailableStream}.
//    * @param <          O> The collections element type. (In turn, the result streams element type.)
//    * @return The created {@link FailableStream}.
//    * @since 3.10
//    */
//  def stream[O](collection: util.Collection[O]) = new Nothing(collection.stream)
//
//  /**
//    * Converts the given stream into a {@link FailableStream}. The {@link FailableStream} consists of the same
//    * elements, than the input stream. However, failable lambdas, like {@link FailablePredicate},
//    * {@link FailableFunction}, and {@link FailableConsumer} may be applied, rather than {@link Predicate},
//    * {@link Function}, {@link Consumer}, etc.
//    *
//    * @param stream The stream, which is being converted into a {@link FailableStream}.
//    * @param <      O> The streams element type.
//    * @return The created {@link FailableStream}.
//    * @since 3.10
//    */
//  def stream[O](stream: Stream[O]) = new Nothing(stream)
//
//  /**
//    * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param predicate the predicate to test
//    * @param object1   the first input to test by {@code predicate}
//    * @param object2   the second input to test by {@code predicate}
//    * @param <         O1> the type of the first argument the predicate tests
//    * @param <         O2> the type of the second argument the predicate tests
//    * @param <         T> the type of checked exception the predicate may throw
//    * @return the boolean value returned by the predicate
//    */
//  def test[O1, O2, T <: Throwable](predicate: Functions.FailableBiPredicate[O1, O2, T], object1: O1, object2: O2): Boolean = getAsBoolean(() => predicate.test(object1, object2))
//
//  /**
//    * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
//    *
//    * @param predicate the predicate to test
//    * @param object    the input to test by {@code predicate}
//    * @param <         O> the type of argument the predicate tests
//    * @param <         T> the type of checked exception the predicate may throw
//    * @return the boolean value returned by the predicate
//    */
//  def test[O, T <: Throwable](predicate: Functions.FailablePredicate[O, T], `object`: O): Boolean = getAsBoolean(() => predicate.test(`object`))
//
//  /**
//    * A simple try-with-resources implementation, that can be used, if your objects do not implement the
//    * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
//    * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
//    * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
//    * {@link Throwable} is rethrown. Example use:
//    *
//    * <pre>
//    * {@code
//    * final FileInputStream fis = new FileInputStream("my.file");
//    * Functions.tryWithResources(useInputStream(fis), null, () -> fis.close());
//    * }</pre>
//    *
//    * @param action The action to execute. This object <em>will</em> always be invoked.
//    * @param errorHandler An optional error handler, which will be invoked finally, if any error occurred. The error
//    * handler will receive the first error, AKA {@link Throwable}.
//    *
//    * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
//    *                  order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
//    * @see #tryWithResources(FailableRunnable, FailableRunnable...)
//    */
//  @SafeVarargs def tryWithResources(action: Functions.FailableRunnable[_ <: Throwable], errorHandler: Functions.FailableConsumer[Throwable, _ <: Throwable], resources: Functions.FailableRunnable[_ <: Throwable]*): Unit = {
//    var actualErrorHandler = null
//    if (errorHandler == null) actualErrorHandler = Functions.rethrow
//    else actualErrorHandler = errorHandler
//    if (resources != null) for (failableRunnable <- resources) {
//      Objects.requireNonNull(failableRunnable, "runnable")
//    }
//    var th = null
//    try action.run()
//    catch {
//      case t: Throwable =>
//        th = t
//    }
//    if (resources != null) for (runnable <- resources) {
//      try runnable.run()
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
//    * {@code
//    * final FileInputStream fis = new FileInputStream("my.file");
//    * Functions.tryWithResources(useInputStream(fis), () -> fis.close());
//    * }</pre>
//    *
//    * @param action The action to execute. This object <em>will</em> always be invoked.
//    * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
//    * order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
//    *
//    * @see #tryWithResources(FailableRunnable, FailableConsumer, FailableRunnable...)
//    */
//  @SafeVarargs def tryWithResources(action: Functions.FailableRunnable[_ <: Throwable], resources: Functions.FailableRunnable[_ <: Throwable]*): Unit = {
//    tryWithResources(action, null, resources)
//  }
//}
