package com.guzelduatr.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzelduatr.app.data.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PrayerUiState(
    val isLoading: Boolean = false,
    val timings: Map<String, String> = emptyMap(),
    val dateReadable: String = "",
    val location: String = "",
    val error: String? = null
)

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val repo: PrayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrayerUiState(isLoading = true))
    val uiState: StateFlow<PrayerUiState> = _uiState

    init {
        fetchTimings("Istanbul", "Turkey")
    }

    fun fetchTimings(city: String, country: String = "Turkey") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val res = repo.getTimingsByCity(city, country)
            res.fold(onSuccess = { body ->
                _uiState.value = PrayerUiState(
                    isLoading = false,
                    timings = body.data.timings,
                    dateReadable = body.data.date.readable,
                    location = "$city, $country",
                    error = null
                )
            }, onFailure = { err ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = err.localizedMessage ?: "Bilinmeyen hata")
            })
        }
    }
}
