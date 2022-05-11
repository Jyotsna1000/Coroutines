import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class Timeouts {
    /**
     * withTimeout is used to cancel the children couroutine after certain timeout, incase they're active
     * this throws TimeoutCancellationException which is child of CancellationException
     */
    suspend fun timeOutWithException() {
        try {
            withTimeout(100) {
                launch(Dispatchers.Default) {
                    listOf(
                        "Rohini", "Richard", "Mona", "Divya", "John", "David", "UWE", "Flower", "Daisy", "Random"
                    ).map { name ->
                        if (this.isActive)
                            println(Client.get(name))
                        else return@launch
                    }
                }
                println("After launching")
            }
        } catch (e: TimeoutCancellationException) {
            println("Timeout occurred")
        }
    }

    /**
     * Instead of throwing TimeoutCancellationException exception , if it is required to return null, we can use withTimeoutOrNull
     */
    suspend fun timeOutWithNull() {
        withTimeoutOrNull(100) {
            launch(Dispatchers.Default) {
                listOf(
                    "Rohini", "Richard", "Mona", "Divya", "John", "David", "UWE", "Flower", "Daisy", "Random"
                ).map { name ->
                    if (this.isActive)
                        println(Client.get(name))
                    else return@launch
                }
            }
            println("After launching")
        }
        println("Timeout occurred")
    }
}

fun main() = runBlocking {
    Timeouts().timeOutWithException()
    Timeouts().timeOutWithNull()
}