package app.cloudcoffee.rigel

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExpectTest {

    @Test
    fun testExpect2() {
        //test expect with 2 params
        var empty : String? = null

        testFail {
            expectAll(empty, ""){ a, b ->

            }
        }
        
        testFail {
            expectAll("", empty) { a, b ->

            }
        }

        testSuccess {
            expectAll("", ""){ a,b ->

            }
        }
    }

    @Test
    fun testExcpect3() {
        //test expect with 2 params
        var empty : String? = null

        testFail {
            expectAll(empty, "", ""){ a, b,c ->

            }
        }

        testFail {
            expectAll("", empty, "") { a, b,c ->

            }
        }

        testSuccess {
            expectAll("", "", ""){ a,b,c ->

            }
        }
    }
}