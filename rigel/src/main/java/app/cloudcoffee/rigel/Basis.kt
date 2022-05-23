package app.cloudcoffee.rigel

import app.cloudcoffee.rigel.signal.NoTimeoutSignalStrategy
import app.cloudcoffee.rigel.signal.SignalAwaiter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert

/** Base case for success / fail callbacks */
private fun testBlockThrows(expectThrow: Boolean, callback: ContextFreeCallback) {
    val signal = SignalAwaiter<Boolean>(NoTimeoutSignalStrategy())
    GlobalScope.launch(Dispatchers.Main) {
        try {
            callback()
            signal.postResult(false)
        }
        catch (e: Exception){
            signal.postResult(true)
        }
    }
    val didThrow = signal.waitfor()
    Assert.assertEquals(expectThrow, didThrow)
}

/** Tests whether the callback is executed without throwing an exception. Will assert no exception */
fun testSuccess(callback: ContextFreeCallback) {
    testBlockThrows(false, callback)
}

/** Callback is expected to throw an exception. Will assert thrown exception */
fun testFail(callback: ContextFreeCallback) {
    testBlockThrows(true, callback)
}