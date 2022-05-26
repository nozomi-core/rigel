package app.cloudcoffee.rigel

import kotlin.reflect.KClass

interface ValueInspection<T> {
    fun value(): T?
    fun isEqual(other: T?)
    fun isNull()
    fun isNotNull()
    fun isInstance(aClass: KClass<*>)
}

class RigelInspection<T>(val rigelAssert: RigelAssert, val checkValue: T?): ValueInspection<T> {
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

    override fun isInstance(aClass: KClass<*>) {
        rigelAssert.assertTrue(aClass.isInstance(checkValue))
    }
}