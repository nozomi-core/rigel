package app.cloudcoffee.rigel

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay

import org.junit.Test
import org.junit.runner.RunWith

class Atom(val atoms: Int) {

}

class Child(val atom: Atom?) {

}

class Parent(val child: Child?) {

}

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun testFail() {
       testFail {
           delay(1000)
           throw Exception()
       }
    }

    @Test
    fun testSuccess() {
        testSuccess {
            delay(1000)
            try {
                throw Exception()
            }catch (e : Exception){

            }
        }
    }
}