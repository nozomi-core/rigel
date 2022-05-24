package app.cloudcoffee.rigel

import android.util.Log
import app.cloudcoffee.rigel.signal.MinuteTimeoutSignalStrategy
import app.cloudcoffee.rigel.signal.SignalAwaiter
import app.cloudcoffee.rigel.signal.NoTimeoutSignalStrategy
import kotlinx.coroutines.*

const val EMPTY_STRING = "EMPTY-MESSAGE"

private val junitAssert = JUnitRigelAssert()

/* Reporting functions */
fun reportTag(logTag: String, logMessage: Any?, fallbackNull: String = EMPTY_STRING) {
    var tagName = logTag
    if(!logTag.equals(""))
        tagName = ".$logTag"

    logMessage?.let {
        Log.v("rigel.log$tagName", it.toString())
    } ?: Log.v("rigel.log$tagName", fallbackNull)
}

fun report(message: Any?, fallbackNull: String = EMPTY_STRING) {
    reportTag("main", message, fallbackNull)
}

private fun <Result> rigelExamBlock(testCallback: () -> Result): Result {
    return testCallback()
}

/* Exam functions */
fun <Result> exam( block: suspend () -> Result): Result {
    return examWith(0) {
        block()
    }
}
fun <Injector, Result> examWith(inject: Injector, block: suspend (context: Injector) -> Result): Result {
    return rigelExamBlock {
        val signal = SignalAwaiter<Result>(NoTimeoutSignalStrategy())
        GlobalScope.launch(Dispatchers.Main) {
            val result = block(inject)
            //delay(10) //TODO: without this exam will hang on empty
            signal.postResult(result)
        }
        signal.waitfor()
    }
}

fun <Result> examAsync(block: suspend (signalCall: SignalAwaiter<Result>) -> Unit): Result {
    return examAsyncWith(0){ _, signal ->
        block(signal)
    }
}

fun <Injector, Result> examAsyncWith(inject: Injector, block: suspend (context: Injector, signalCall: SignalAwaiter<Result>) -> Unit): Result {
    return rigelExamBlock() {
        val signal =
            SignalAwaiter<Result>(NoTimeoutSignalStrategy())
        GlobalScope.launch(Dispatchers.Main) {
            block(inject, signal)
        }
        signal.waitfor()
    }
}

//Timeout version of exam async
fun <Result> examAsyncTimeout(minutes: Int, block: suspend (signalCall: SignalAwaiter<Result>) -> Unit): Result {
    return examAsyncTimeoutWith(0, minutes){ _, signal ->
        block(signal)
    }
}

fun <Injector, Result> examAsyncTimeoutWith(inject: Injector, minutes: Int, block: suspend (context: Injector, signalCall: SignalAwaiter<Result>) -> Unit): Result {
    return rigelExamBlock {
        val signal = SignalAwaiter<Result>(MinuteTimeoutSignalStrategy(minutes))
        GlobalScope.launch(Dispatchers.Main) {
            block(inject, signal)
        }
        signal.waitfor()
    }
}

fun callCheck(): RigelCallCheck {
    return ActualRigelCaller(junitAssert)
}

fun callGroup(block: (callGroup: RigelCallGroup) -> Unit): RigelCallGroup {
    return ActualRigelCallGroup().also {
        block(it)
    }
}