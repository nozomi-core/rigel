package app.cloudcoffee.rigel

import org.junit.Assert
import java.lang.Exception

interface RigelAssert {
    fun assertTrue(statement: Boolean)
    fun assertEquals(expected: Any?, actual: Any?)
}

class JUnitRigelAssert: RigelAssert {
    override fun assertTrue(statement: Boolean) {
        Assert.assertTrue(statement)
    }

    override fun assertEquals(expected: Any?, actual: Any?) {
        Assert.assertEquals(expected, actual)
    }
}

class ExceptionRigelAssert: RigelAssert {
    override fun assertTrue(statement: Boolean) {
        if(!statement)
            throw Exception()
    }

    override fun assertEquals(expected: Any?, actual: Any?) {
        if(expected == null) {
            if(actual != null)
                throw Exception()
        } else {
            if(!expected.equals(actual))
                throw Exception()
        }
    }
}