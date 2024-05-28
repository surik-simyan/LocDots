plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "surik.simyan.locdots"
version = "1.0.0"
application {
    mainClass.set("surik.simyan.locdots.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.data.conversion)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}