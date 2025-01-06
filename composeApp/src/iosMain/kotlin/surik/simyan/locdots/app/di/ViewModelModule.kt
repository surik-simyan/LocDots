package surik.simyan.locdots.app.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import surik.simyan.locdots.app.ui.screens.HomeScreenViewModel
import surik.simyan.locdots.app.ui.screens.MessageScreenViewModel

actual val viewModelModule = module {
    singleOf(::HomeScreenViewModel)
    singleOf(::MessageScreenViewModel)
}