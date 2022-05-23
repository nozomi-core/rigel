package app.cloudcoffee.rigel

interface RigelValueInspection<T> {
    fun value(): T?
    fun isEqual(other: T?)
    fun isNull()
    fun isNotNull()
}

class RigelInspection<T>(val rigelAssert: RigelAssert, val checkValue: T?): RigelValueInspection<T> {
    override fun isEqual(expected: T?) {
        rigelAssert.assertEquals(checkValue, expected)
    }

    override fun isNull() {
        rigelAssert.assertTrue(checkValue == null)
    }

    override fun isNotNull() {
        rigelAssert.assertTrue(checkValue != null)
    }

    override fun value(): T? {
        return checkValue
    }
}