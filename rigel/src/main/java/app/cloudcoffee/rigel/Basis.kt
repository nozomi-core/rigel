package app.cloudcoffee.rigel

import app.cloudcoffee.rigel.signal.ActualSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert

/** Base case for success / fail callbacks */
private fun testBlockThrows(expectThrow: Boolean, callback: suspend () -> Unit) {
    val signal = ActualSignal<Boolean>()
    GlobalScope.launch(Dispatchers.Main) {
        try {
            callback()
            signal.postSuccess(false)
        }
        catch (e: Exception){
            signal.postSuccess(true)
        }
    }
    val didThrow = signal.waitfor()
    Assert.assertEquals(expectThrow, didThrow)
}

/** Tests whether the callback is executed without throwing an exception. Will assert no exception */
fun testSuccess(callback: suspend () -> Unit) {
    testBlockThrows(false, callback)
}

/** Callback is expected to throw an exception. Will assert thrown exception */
fun testFail(callback: suspend () -> Unit) {
    testBlockThrows(true, callback)
}
