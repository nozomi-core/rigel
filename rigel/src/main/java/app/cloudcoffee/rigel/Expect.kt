package app.cloudcoffee.rigel

import java.lang.Exception

fun <T> expectIt(param1: T, block: (item: T) -> Unit) {

}

fun <A, B> expectAll(paramA: A?, paramB: B?,  block: (paramA: A, paramB: B) -> Unit) {
    if(paramA != null && paramB != null)
        block(paramA, paramB)
    else
        throw Exception()
}

fun <A, B, C> expectAll(paramA: A?, paramB: B?, paramC: C?,  block: (paramA: A, paramB: B, paramC: C) -> Unit) {
    if(paramA != null && paramB != null && paramC != null)
        block(paramA, paramB, paramC)
    else
        throw Exception()
}