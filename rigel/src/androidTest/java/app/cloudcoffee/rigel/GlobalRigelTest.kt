package app.cloudcoffee.rigel

import kotlinx.coroutines.delay
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class ExampleInject(val injectionName: String) {

}

@RunWith(JUnit4::class)
class GlobaRigelTest {

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
            signal.postSuccess("hello")
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

        val asyncResult = examAsyncWith<ExampleInject, String >(myInject) { context, signal ->
            delay(1000)
            assertEquals(context.injectionName, "my-inject")
            signal.postSuccess("async-done")
        }
        assertEquals("async-done", asyncResult)
    }

    @Test
    fun testExamBasic() {
        exam {
            report("Hello World")
        }
    }

    @Test
    fun testExamAsyncTimeout() {
        testFail { examAsyncTimeout(1){ } }

        val result = examAsyncTimeout<Int>(1) {
            it.postSuccess(12)
        }
        assertEquals(12, result)
    }

    @Test
    fun testExamAsyncTimeoutWith() {
        testFail {
            examAsyncTimeoutWith("hello", 1) { context, signal ->
                assertEquals(context, "hello")
            }
        }
        val result = examAsyncTimeoutWith<String, String>("work", 1) { context, signal ->
            assertEquals(context, "work")
            signal.postSuccess("")
        }
        assertEquals("", result)

    }
}