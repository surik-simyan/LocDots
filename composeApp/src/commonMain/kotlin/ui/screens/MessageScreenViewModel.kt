package ui.screens

import Dot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import network.DotsApi

class MessageScreenViewModel(
    private val dotsApi: DotsApi
) : ViewModel() {

    private val _uploadState: MutableStateFlow<MessageScreenState> =
        MutableStateFlow(MessageScreenState.Idle)
    val uploadState = _uploadState.asStateFlow()


    sealed class MessageScreenState {
        data object Idle : MessageScreenState()
        data object Loading : MessageScreenState()
        data class Error(val error: String) : MessageScreenState()
        data object Success : MessageScreenState()
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun onSendClick(message: String) {
        viewModelScope.launch {
            _uploadState.update { MessageScreenState.Loading }
            val dot = Dot(
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).format(
                    LocalDateTime.Format {
                        byUnicodePattern("dd/MM/yyyy HH:mm")
                    }
                ),
                lat = 32.0,
                log = 32.0,
                message = message
            )
            val response = dotsApi.createNewDot(dot)
            if (response.status == HttpStatusCode.OK) {
                _uploadState.update { MessageScreenState.Success }
            } else {
                _uploadState.update { MessageScreenState.Error(response.status.description) }
            }
        }
    }
}