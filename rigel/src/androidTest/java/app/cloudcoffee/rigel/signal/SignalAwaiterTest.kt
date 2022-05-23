package app.cloudcoffee.rigel.signal

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SignalAwaiterTest {

    @Test
    fun testNoTimeout() {
        val noTimeout = SignalAwaiter<Int>(NoTimeoutSignalStrategy())
        GlobalScope.launch {
            //delay for 10min
            delay(60 * 1000 * 10)
            noTimeout.postResult(11)
        }
        val eleven = noTimeout.waitForResult()
        assertEquals(eleven, 11)
    }

    @Test
    fun test1MinuteTimeout() {
        val awaiter = SignalAwaiter<Int>(MinuteTimeoutSignalStrategy(1))
        GlobalScope.launch {

        }
        var doesThrow = false
        try {
            awaiter.waitForResult()
        }
        catch (e: Exception){
            doesThrow = true
        }
        assertTrue(doesThrow)
    }
}