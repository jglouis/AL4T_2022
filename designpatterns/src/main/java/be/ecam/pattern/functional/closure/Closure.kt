package be.ecam.pattern.functional.closure

fun getCounter(): () -> Unit {
    var counter = 0
    return { println(counter++) }
}

fun main() {
    val counter = getCounter()

    counter()
    counter()
    counter()
    counter()
}