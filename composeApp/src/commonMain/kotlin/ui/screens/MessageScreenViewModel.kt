package ui.screens

import Dot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
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

    fun onSendClick(message: String) {
        viewModelScope.launch {
            _uploadState.update { MessageScreenState.Loading }
            when (val location = geolocator.current(Priority.HighAccuracy)) {
                is GeolocatorResult.Success -> {
                    try {
                        dotsApi.createNewDot(
                            Dot(
                                timestamp = Clock.System.now().epochSeconds,
                                coordinates = arrayOf(
                                    location.data.coordinates.longitude,
                                    location.data.coordinates.latitude
                                ),
                                message = message
                            )
                        )
                        _uploadState.update {
                            MessageScreenState.Success
                        }
                    } catch (e: Exception) {
                        Logger.e(e.message.toString(), e)
                        _uploadState.update { MessageScreenState.Error(e.toString()) }
                    }
                }

                is GeolocatorResult.PermissionError -> {
                    Logger.e("PermissionError")
                    _uploadState.update { MessageScreenState.Error("Please grant location permission") }
                }

                else -> {
                    Logger.e(location.toString())
                    _uploadState.update { MessageScreenState.Error("Something went wrong") }
                }
            }
        }
    }
}