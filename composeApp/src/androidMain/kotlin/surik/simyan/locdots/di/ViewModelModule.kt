import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ui.screens.HomeScreenViewModel
import ui.screens.MessageScreenViewModel

actual val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::MessageScreenViewModel)
}