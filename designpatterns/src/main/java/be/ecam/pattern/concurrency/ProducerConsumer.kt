package be.ecam.pattern.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

val channel = Channel<Int>()

fun main() = runBlocking {
    launch { // launch a coroutine to act as a producer
        repeat(5) {
            delay(1000) // simulate some work
            val data = it + 1
            println("Producing $data")
            channel.send(data)
        }
        channel.close() // close the channel when done producing
    }

    launch { // launch a coroutine to act as a consumer
        for (data in channel) {
            println("Consuming $data")
        }
    }

    // Ensure that the main function does not exit until both producer and consumer coroutines are done
    delay(6000)
}
