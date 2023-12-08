package be.ecam.pattern.concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

val channel = Channel<Int>()

fun main() = runBlocking {
    val producerJob = launch { // launch a coroutine to act as a producer
        producer()
    }

    val consumerJob = launch { // launch a coroutine to act as a consumer
        consumer()
    }

    // Ensure that the main function does not exit until both producer and consumer coroutines are done
    producerJob.join()
    consumerJob.join()
}

suspend fun producer() {
    repeat(5) {
        delay(1000) // simulate some work
        val data = it + 1
        println("Producing $data")
        channel.send(data)
    }
    channel.close() // close the channel when done producing
}

suspend fun consumer() {
    for (data in channel) {
        println("Consuming $data")
    }
}