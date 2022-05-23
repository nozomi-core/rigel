package app.cloudcoffee.rigel

interface RigelListInspection<T> {
    fun isLength(length: Int)
    fun valueAt(position: Int, any: T?)
    fun contentEquals(another: Iterable<T>)
    fun isValues(vararg listing: T)
}

class JUnitRigelListInspection<T>(iterable: Iterable<T>): RigelListInspection<T> {
    val checkListing = iterable.toList()

    override fun isLength(length: Int) {
        inspect(checkListing.size){
            isEqual(length)
        }
    }

    override fun valueAt(position: Int, expectValue: T?) {
        inspect(checkListing.get(position)){
            isEqual(expectValue)
        }
    }

    override fun contentEquals(another: Iterable<T>) {
        val expectedList = another.toList()
        inspect(checkListing.size){
            isEqual(expectedList.size)
        }
        checkListing.mapIndexed { index, toCheck ->
            inspect(toCheck){
                isEqual(expectedList.get(index))
            }
        }
    }

    override fun isValues(vararg listing: T) {
        inspectList(checkListing){
            contentEquals(listing.toList())
        }
    }
}