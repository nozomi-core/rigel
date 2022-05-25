package app.cloudcoffee.rigel

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RigelCallCheckTest {

    @Test
    fun testCallCheckNull() {
       val assertme = ExceptionRigelAssert()

        val callCheck = ActualRigelCaller(assertme)

        testSuccess {
            callCheck.isNotCalled()
        }
        testFail {
            callCheck.isCalled()
        }
    }

    @Test
    fun testCallCheckSingle() {
        val assertme = ExceptionRigelAssert()
        val callCheck = ActualRigelCaller(assertme)
        callCheck.call(this)

        testFail {
            callCheck.isNotCalled()
        }
        testSuccess {
            callCheck.isCalled()
        }
    }

    @Test
    fun testGroupCaller() {
        val assertme = ExceptionRigelAssert()
        var salmon : RigelCallCheck? = null
        var fish : RigelCallCheck? = null

        val group = callGroup {
            salmon = it.createCaller(assertme)
            fish = it.createCaller(assertme)
        }

        expectAll(salmon, fish){ itSalmon, itFish ->
            itSalmon.call(this)
        }

        testFail {
            group.allCalled()
        }
    }
}