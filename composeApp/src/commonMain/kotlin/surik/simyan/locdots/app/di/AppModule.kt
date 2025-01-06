package surik.simyan.locdots.app.di

import surik.simyan.locdots.app.network.DotsApi
import org.koin.dsl.module

val appModule = module {
    single<DotsApi> { DotsApi() }
}