package app.cloudcoffee.rigelapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cloudcoffee.rigel.exam
import app.cloudcoffee.rigel.report
import kotlinx.coroutines.delay

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    suspend fun delayApi(msg: String): String {
        delay(5000)
        return "$msg-done"
    }

    @Test
    fun useAppContext() {
        val resp = exam {
            val p1 = delayApi("hey")
            val p2 = delayApi("popcorn")
            listOf(p1,p2)
        }
        report(resp)
    }
}