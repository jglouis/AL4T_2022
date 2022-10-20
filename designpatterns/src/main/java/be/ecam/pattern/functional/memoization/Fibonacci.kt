package be.ecam.pattern.functional.memoization

/**
 * Returns the n_th element of the Fibonacci sequence
 */
fun fib(n: Int): Long {
    if (n <= 1) return 1L
    return memoizeIntToInt(::fib)(n - 1) + memoizeIntToInt(::fib)(n - 2)
}

val cache = mutableMapOf<Int, Long>()

fun memoizeIntToInt(f: (n: Int) -> Long): (Int) -> Long {
    return { n -> cache.getOrPut(n) { f(n) } }
}

fun main() {
    print(fib(200))
}