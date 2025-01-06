package surik.simyan.locdots

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
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
import surik.simyan.locdots.shared.data.Dot
import surik.simyan.locdots.shared.data.DotSort


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
                MongoClient.create("mongodb://mongodb:27017")
            }
            single {
                get<MongoClient>().getDatabase("locdots")
            }
        }, org.koin.dsl.module {
            single<DotsRepository> { DotsRepositoryImpl(get()) }
        })
    }
    routing {
        get("/dots") {
            val lat = call.request.queryParameters["lat"]?.toDouble()
            val lng = call.request.queryParameters["lng"]?.toDouble()
            val sortingType = call.request.queryParameters["sortingType"].toDotSort()
            if (lat != null && lng != null) {
                val dots = repository.getAll(lat, lng, sortingType)
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

private fun String?.toDotSort() : DotSort {
    return DotSort.entries.firstOrNull { it.value == this } ?: DotSort.PostDate
}