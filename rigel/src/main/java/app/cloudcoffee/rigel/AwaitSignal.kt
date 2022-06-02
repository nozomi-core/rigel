package app.cloudcoffee.rigel.signal

import java.lang.RuntimeException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

interface AwaitSignal<T> {
    fun postSuccess(result: T)
    fun postException(e: Exception)
    fun postExceptionString(message: String)
}

interface ResultAwaiter<T> {
    fun waitfor(): T
    fun waitforSeconds(seconds: Long): T
}

class ActualSignal<T>: AwaitSignal<T>, ResultAwaiter<T> {
    val completableFuture = CompletableFuture<T>()

    override fun postSuccess(result: T) {
        completableFuture.complete(result)
    }

    override fun postException(e: Exception) {
        completableFuture.completeExceptionally(e)
    }

    override fun postExceptionString(message: String) {
        completableFuture.completeExceptionally(RuntimeException(message))
    }

    override fun waitfor(): T {
        return completableFuture.get()
    }

    override fun waitforSeconds(seconds: Long): T {
        return completableFuture.get(seconds, TimeUnit.SECONDS)
    }
}




