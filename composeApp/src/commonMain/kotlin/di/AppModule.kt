package di

import network.DotsApi
import org.koin.dsl.module

val appModule = module {
    single<DotsApi> { DotsApi() }
}