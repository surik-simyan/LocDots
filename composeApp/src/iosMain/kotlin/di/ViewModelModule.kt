import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.screens.HomeScreenViewModel
import ui.screens.MessageScreenViewModel

actual val viewModelModule = module {
    singleOf(::HomeScreenViewModel)
    singleOf(::MessageScreenViewModel)
}