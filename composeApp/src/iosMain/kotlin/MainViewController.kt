import androidx.compose.ui.window.ComposeUIViewController
import surik.simyan.locdots.app.App
import surik.simyan.locdots.app.di.KoinInitializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer().init()
    }
) { App() }