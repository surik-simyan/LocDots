package ui.screens

import Dot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.DotsApi

class HomeScreenViewModel(
    private val dotsApi: DotsApi
) : ViewModel() {

    private val _dots: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Idle)
    val dots = _dots.asStateFlow()

    private val geolocator: Geolocator = Geolocator.mobile()

    init {
        getItems()
    }

    sealed class HomeScreenState {
        data object Idle : HomeScreenState()
        data object Loading : HomeScreenState()
        data class Error(val error: String) : HomeScreenState()
        data class Success(val items: List<Dot>) : HomeScreenState()
    }

    fun getItems(isDescending: Boolean = true) {
        viewModelScope.launch {
            _dots.update { HomeScreenState.Loading }
            when (val location = geolocator.current(Priority.HighAccuracy)) {
                is GeolocatorResult.Success -> {
                    try {
                        _dots.update {
                            HomeScreenState.Success(
                                dotsApi.getAllDots(
                                    location.data.coordinates.latitude,
                                    location.data.coordinates.longitude,
                                    isDescending
                                )
                            )
                        }
                    } catch (e: Exception) {
                        _dots.update { HomeScreenState.Error(e.toString()) }
                    }
                }

                is GeolocatorResult.PermissionError -> {
                    _dots.update { HomeScreenState.Error("Please grant location permission") }
                }

                else -> {
                    _dots.update { HomeScreenState.Error("Something went wrong") }
                }
            }
        }
    }
}