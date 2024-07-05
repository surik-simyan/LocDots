package surik.simyan.locdots

import Dot
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val repository by inject<DotsRepository>()
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }
    install(Koin) {
        slf4jLogger()
        modules(org.koin.dsl.module {
            single {
                MongoClient.create(
                    environment.config.propertyOrNull("ktor.mongo.uri")?.getString()
                        ?: throw RuntimeException("Failed to access MongoDB URI.")
                )
            }
            single {
                get<MongoClient>().getDatabase(
                    environment.config.property("ktor.mongo.database").getString()
                )
            }
        }, org.koin.dsl.module {
            single<DotsRepository> { DotsRepositoryImpl(get()) }
        })
    }
    routing {
        get("/dots") {
            val lat = call.request.queryParameters["lat"]?.toDouble()
            val lng = call.request.queryParameters["lng"]?.toDouble()
            val isDescending = call.request.queryParameters["isDescending"]?.toBoolean()
            if (lat != null && lng != null) {
                val dots = repository.getAll(lat, lng, isDescending ?: false)
                call.respond(dots)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Failed to retrieve current location.")
            }
        }
        post("/dots") {
            val dot = call.receive<Dot>()
            if (repository.insertOne(dot) != null) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Failed to insert dot.")
            }
        }
    }
}