package ui.screens

import Dot
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getItems()
    }

    sealed class HomeScreenState {
        data object Idle : HomeScreenState()
        data object Loading : HomeScreenState()
        data class Error(val error: String) : HomeScreenState()
        data class Success(val items: List<Dot>) : HomeScreenState()
    }

    fun getItems() {
        viewModelScope.launch {
            _dots.update { HomeScreenState.Loading }
            try {
                _dots.update { HomeScreenState.Success(dotsApi.getAllLaunches()) }
            } catch (e: Exception) {
                _dots.update { HomeScreenState.Error(e.toString()) }
            }
        }
    }
}