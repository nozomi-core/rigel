package app.cloudcoffee.rigel

import app.cloudcoffee.rigel.signal.ActualSignal
import app.cloudcoffee.rigel.signal.AwaitSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

private fun  <Inject, Result> rigelBlock(inject: Inject, secondsTimeout: Long, block: suspend (inject: Inject) -> Result, ): Result {
    val future = CompletableFuture<Result>()
    GlobalScope.launch(Dispatchers.Main) {
        val result = block(inject)
        future.complete(result)
    }
    return if(secondsTimeout > 0) {
        future.get(secondsTimeout, TimeUnit.SECONDS)
    } else {
        future.get()
    }
}

fun <Result> exam(block: suspend () -> Result): Result {
    return rigelBlock(0, 0) {
        block()
    }
}
fun <Injector, Result> examWith(inject: Injector, block: suspend (context: Injector) -> Result): Result {
    return rigelBlock(inject, 0) {
        block(inject)
    }
}

/**
 *  Async blocking calls. Uses a signal to post result back to main thread
 */

private fun  <Inject, Result> rigelBlockAsync(inject: Inject, secondsTimeout: Long, signal: ActualSignal<Result>, block: suspend (inject: Inject) -> Unit, ): Result {
    GlobalScope.launch(Dispatchers.Main) {
        block(inject)
    }
    return if(secondsTimeout > 0){
        signal.waitforSeconds(secondsTimeout)
    } else {
        signal.waitfor()
    }
}

fun <Result> examAsync(block: suspend (signal: AwaitSignal<Result>) -> Unit): Result {
    val signal = ActualSignal<Result>()
    return rigelBlockAsync(0, 0, signal) {
        block(signal)
    }
}

fun <Injector, Result> examAsyncWith(inject: Injector, block: suspend (context: Injector, signalCall: ActualSignal<Result>) -> Unit): Result {
    val signal = ActualSignal<Result>()
    return rigelBlockAsync(inject, 0, signal) {
        block(it, signal)
    }
}

fun <Result> examAsyncTimeout(secondsTimeout: Long, block: suspend (signalCall: ActualSignal<Result>) -> Unit): Result {
    val signal = ActualSignal<Result>()
    return rigelBlockAsync(0, secondsTimeout, signal) {
        block(signal)
    }
}

fun <Injector, Result> examAsyncTimeoutWith(inject: Injector, secondsTimeout: Long, block: suspend (context: Injector, signalCall: ActualSignal<Result>) -> Unit): Result {
    val signal = ActualSignal<Result>()
    return rigelBlockAsync(inject, secondsTimeout, signal) {
        block(it, signal)
    }
}