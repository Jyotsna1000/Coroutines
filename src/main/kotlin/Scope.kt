import kotlin.system.measureNanoTime
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Scope {
    /**
    CoroutineScope defines the scope for coroutines to run in
    One coroutine scope is considered to be completed when all of its child coroutines complete their execution
     */
    suspend fun getNationality() {
        println("START PARALLEL RUNNING JOBS")
        coroutineScope {
            launch { println(Client.get("Jyotsna")) }
            launch { println(Client.get("Manish")) }
            launch { println(Client.get("John")) }
            //the coroutineScope will be considered completed only after all the launch has been completed
        }
        println("STOP PARALLEL RUNNING JOBS")
    }

    fun getNationalityBlocking() {
        println("START RUNNING JOBS")
        println(Client.get("Jyotsna"))
        println(Client.get("Manish"))
        println(Client.get("John"))
        println("STOP RUNNING JOBS")
    }
}

/**
 * RunBlocking is coroutine builder which bridges the gap b/w non-suspending and suspending code blocks
 * This is mostly used for main method and testing purposes
 * It will block main thread during its execution
 */
fun main() = runBlocking {
    val scope = Scope()
    val parallelExecTime = measureNanoTime { scope.getNationality() }
    val sequentialExecTime = measureNanoTime { scope.getNationalityBlocking() }
    println("Time taken by parallel calls : $parallelExecTime")
    println("Time taken by sequential calls : $sequentialExecTime")
}