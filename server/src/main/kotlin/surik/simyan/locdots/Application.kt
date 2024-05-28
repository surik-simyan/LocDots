package surik.simyan.locdots

import Dot
import SERVER_PORT
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        module()
    }.start(wait = true)
}

fun Application.module() {
    routing {
        get("/dots") {
            val dots = mutableListOf<Dot>()
            for (i in 0..15) {
                dots.add(
                    Dot(
                        id = i,
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            .format(LocalDateTime.Formats.ISO),
                        lat = 49.193240,
                        lon = -0.343040,
                        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer fringilla lorem ac lectus porta, sed tincidunt eros varius. Aliquam eu feugiat velit. Aliquam semper, mauris sagittis ultrices accumsan, nibh magna fermentum neque, et auctor mauris nisi id lorem. Maecenas mollis at leo id pellentesque. Nunc ut molestie leo. Morbi ac diam tortor. Suspendisse tempor magna a lorem sodales faucibus. "
                    )
                )
            }
            call.respond(dots)
        }
    }
}