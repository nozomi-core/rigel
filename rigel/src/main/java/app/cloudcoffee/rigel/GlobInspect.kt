package app.cloudcoffee.rigel

fun <T> inspect(valueToCheck: T?, checkCallback: RigelValueInspection<T>.() -> Unit) {
    return checkCallback(RigelInspection(JUnitRigelAssert(), valueToCheck))
}

fun <T> inspectList(iterable: Iterable<T>, listCallback: RigelListInspection<T>.() -> Unit) {
    return listCallback(JUnitRigelListInspection(iterable))
}