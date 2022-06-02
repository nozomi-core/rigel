package app.cloudcoffee.rigel

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

data class Salmon(val name: String)
data class River(val area: String)

@RunWith(JUnit4::class)
class RigelInspectionValueTest {

    val useAssert = ExceptionRigelAssert()


    @Test
    fun testInstance() {
        testFail {
            val fish = Salmon("salmon")
            val river = River("yarra")

            val rigel = RigelInspection(useAssert, fish)
            rigel.isInstance(River::class)
        }

        testSuccess {
            val fish = Salmon("salmon")
            val river = River("yarra")

            val rigel = RigelInspection(useAssert, fish)
            rigel.isInstance(Salmon::class)
        }
    }
}