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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import surik.simyan.locdots.BuildKonfig

class DotsApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllDots(lat: Double, lng: Double, isDescending: Boolean): List<Dot> {
        return httpClient.get("${BuildKonfig.API_URL}/dots") {
            url {
                parameters.append("lat", lat.toString())
                parameters.append("lng", lng.toString())
                parameters.append("isDescending", isDescending.toString())
            }
        }.body()
    }

    suspend fun createNewDot(dot: Dot): HttpResponse {
        return httpClient.post("${BuildKonfig.API_URL}/dots") {
            contentType(ContentType.Application.Json)
            setBody(dot)
        }
    }
}