import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Launch {
    suspend fun singleJob() {
        /**
         * the launch function takes an optional parameter i.e. coroutine context
         * As we're doing client call (IO operation), therefore setting the context to IO
         */
        coroutineScope {
            println("Launching Job")
            val job = launch(Dispatchers.IO) {
                println(Client.get("Peter"))
            }
            println("After launching")
            job.join()
//            waits for the job to complete (required when job completion is necessary for next block of code
            println("After job completion")
        }
    }

    suspend fun multiJob() {
        coroutineScope {
            println("Launching Jobs")
            val jobs = listOf("Imran", "William", "Raghav").map { name ->
                launch(Dispatchers.IO) {
                    println(Client.get(name))
                }
            }
            println("After launching")
            jobs.joinAll() // waits for list of jobs to be completed
            println("After job completion")
        }
    }

    suspend fun multiJob2() {
        coroutineScope {
            println("Launching Jobs")
            val job1 = launch(Dispatchers.IO) {
                println(Client.get("Peter"))
            }
            val job2 = launch(Dispatchers.IO) {
                println(Client.get("Sunil"))
            }
            val job3 = launch(Dispatchers.IO) {
                println(Client.get("Sukhwinder"))
            }
            println("After launching")
            joinAll(job1, job2, job3)   // this can be used when we want to wait for multiple jobs of different type
            println("After job completion")
        }
    }
}


fun main() = runBlocking {
    Launch().singleJob()
    Launch().multiJob()
    Launch().multiJob2()
}
