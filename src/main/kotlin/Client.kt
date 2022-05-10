import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

object Client {
    fun get(name: String): Result<String, FuelError> {
        val url = "https://api.nationalize.io/?name=$name"
        return url.httpGet().responseString().third
    }
}