import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Cancel {
    /**
     * Jobs can be cancelled once they're launched by using cancel method on job object (cancel(), cancelAndJoin())
     *
     * cancel() will always throw CancellationException from the job that has been cancelled
     * but this exception is considered to be normal completion of a job in the parent coroutine
     * Therefore it is ignored and no need to handle this exception
     *
     *Cooperative jobs are the ones which can be cancelled by cancel(),
     *  e.g. (delay() is a cooperative jobs, which checks if this job has been cancelled or not
     */
    suspend fun cooperativeCancellation() {
        coroutineScope {
            val job = launch(Dispatchers.Default) {
                listOf(
                    "Eva", "Richard", "Mona", "Divya", "John", "David", "UWE", "Flower", "Daisy", "Random"
                ).map { name ->
                    delay(1)
                    println(Client.get(name))
                }
            }
            println("After launching")
            delay(500)
            println("After Delay")
            job.cancelAndJoin()
            println("Cancelled cooperative Job")
        }
    }

    /**
     * Non-cooperative jobs are those for which donot check for cancellation while execution,
     * Therefore we need to check isActive flag which is set to false on jab cancellation
     */
    suspend fun nonCooperativeCancellation() {
        coroutineScope {
            val job = launch(Dispatchers.Default) {
                listOf(
                    "Eva", "Richard", "Mona", "Divya", "John", "David", "UWE", "Flower", "Daisy", "Random"
                ).map { name ->
                    if (this.isActive)
                        println(Client.get(name))
                    else return@launch
                }
            }
            println("After launching")
            delay(500)
            println("After Delay")
            job.cancelAndJoin()
            println("Cancelled non-cooperative Job")
        }
    }

    /**
     * Non-cancellable Job is always active, and cannot be cancelled by cancel()
     * This is designed to be used with withContext only
     */
    suspend fun nonCancellable() {
        coroutineScope {
            val job = launch(Dispatchers.IO) {
                listOf(
                    "Eva", "Richard", "Mona", "Divya", "John", "David", "UWE", "Flower", "Daisy", "Random"
                ).map { name ->
                    withContext(NonCancellable) {
                        println(Client.get(name))
                        delay(50)
                    }
                }
            }
            println("After launching")
            delay(500)
            println("After Delay")
            job.cancelAndJoin() // Job at line 69 is still not cancelled
            println("Cancelled non-cooperative Job")
        }
    }
}

fun main() = runBlocking {
    Cancel().nonCooperativeCancellation()
    Cancel().cooperativeCancellation()
    Cancel().nonCancellable()
}