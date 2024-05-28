package network

import Dot
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class DotsApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        headers {
            "ngrok-skip-browser-warning" to "69420"
        }
    }

    suspend fun getAllLaunches(): List<Dot> {
        return httpClient.get("https://tight-heroic-labrador.ngrok-free.app/dots").body()
    }
}