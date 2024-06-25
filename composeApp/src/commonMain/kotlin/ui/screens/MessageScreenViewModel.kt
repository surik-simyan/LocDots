package ui.screens

import Dot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import network.DotsApi

class MessageScreenViewModel(
    private val dotsApi: DotsApi
) : ViewModel() {

    private val _uploadState: MutableStateFlow<MessageScreenState> =
        MutableStateFlow(MessageScreenState.Idle)
    val uploadState = _uploadState.asStateFlow()

    private val geolocator: Geolocator = Geolocator.mobile()


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
            when (val latlong = geolocator.current(Priority.HighAccuracy)) {
                is GeolocatorResult.Success -> {
                    val dot = Dot(
                        timestamp = Clock.System.now().epochSeconds,
                        coordinates = arrayOf(
                            latlong.data.coordinates.longitude,
                            latlong.data.coordinates.latitude
                        ),
                        message = message
                    )
                    val response = dotsApi.createNewDot(dot)
                    if (response.status == HttpStatusCode.OK) {
                        _uploadState.update { MessageScreenState.Success }
                    } else {
                        _uploadState.update { MessageScreenState.Error(response.status.description) }
                    }
                }

                is GeolocatorResult.PermissionError -> {
                    _uploadState.update { MessageScreenState.Error("Please grant location permission") }
                }

                else -> {
                    _uploadState.update { MessageScreenState.Error("Something went wrong") }
                }
            }
        }
    }
}