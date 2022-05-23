package app.cloudcoffee.rigel

import kotlinx.coroutines.delay
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class ExampleInject(val injectionName: String) {

}

@RunWith(JUnit4::class)
class GlobalRigelTest {

    @Test
    fun testExam() {
        val examResult = exam {
            delay(1000)
            report("test exam done")
            "done"
        }
        assertEquals("done", examResult)


        val asyncResult = examAsync<String> { signal ->
            delay(1000)
            signal.postResult("hello")
        }
        assertEquals("hello", asyncResult)
    }

    @Test
    fun testExamInject() {
        val myInject = ExampleInject("my-inject")
        val examResult = examWith(myInject){ context ->
            delay(1000)
            assertEquals("my-inject", context.injectionName)
            "exam-done"
        }
        assertEquals("exam-done", examResult)

        val asyncResult = examAsyncWith<String, ExampleInject>(myInject) { context, signal ->
            delay(1000)
            assertEquals(context.injectionName, "my-inject")
            signal.postResult("async-done")
        }
        assertEquals("async-done", asyncResult)

    }
}