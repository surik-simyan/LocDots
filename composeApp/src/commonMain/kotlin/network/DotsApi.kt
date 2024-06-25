package network

import Dot
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class DotsApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        headers {
            "ngrok-skip-browser-warning" to "69420"
        }
    }

    suspend fun getAllDots(lat: Double, lng: Double): List<Dot> {
        return httpClient.get("https://tight-heroic-labrador.ngrok-free.app/dots") {
            url {
                parameters.append("lat", lat.toString())
                parameters.append("lng", lng.toString())
            }
        }.body()
    }

    suspend fun createNewDot(dot: Dot): HttpResponse {
        return httpClient.post("https://tight-heroic-labrador.ngrok-free.app/dots") {
            contentType(ContentType.Application.Json)
            setBody(dot)
        }
    }
}