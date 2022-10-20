package be.ecam.pattern.functional.closure

fun getCounter(): () -> Unit {
    var counter = 0
    val myClosure = { println(counter++) } // the closure has access to var counter
    return myClosure
}

fun main() {
    val counter = getCounter()

    counter()
    counter()
    counter()
    counter()
}