package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessageScreenViewModel : ViewModel() {

    private val _uploadState: MutableStateFlow<MessageScreenState> =
        MutableStateFlow(MessageScreenState.Idle)
    val uploadState = _uploadState.asStateFlow()


    sealed class MessageScreenState {
        data object Idle : MessageScreenState()
        data object Loading : MessageScreenState()
        data class Error(val error: String) : MessageScreenState()
        data object Success : MessageScreenState()
    }

    fun onSendClick(message: String) {
        viewModelScope.launch {
            try {
                _uploadState.update { MessageScreenState.Loading }
//                locationTracker.startTracking()
//                Logger.i {
//                    """
//                        Location: ${locationTracker.getLocationsFlow().first()}
//                        Message: $message
//                    """.trimIndent()
//                }
                _uploadState.update { MessageScreenState.Success }
            } catch (exc: Exception) {
                _uploadState.update { MessageScreenState.Error(exc.toString()) }
            }
        }
    }
}