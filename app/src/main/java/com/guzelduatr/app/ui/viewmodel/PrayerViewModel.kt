package com.guzelduatr.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guzelduatr.app.data.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val repo: PrayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(com.guzelduatr.app.ui.viewmodel.PrayerUiState(isLoading = true))
    val uiState: StateFlow<com.guzelduatr.app.ui.viewmodel.PrayerUiState> = _uiState

    init {
        fetchTimings("Istanbul", "Turkey")
    }

    fun fetchTimings(city: String, country: String = "Turkey") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val res = repo.getTimingsByCity(city, country)
            res.fold(onSuccess = { body ->
                _uiState.value = com.guzelduatr.app.ui.viewmodel.PrayerUiState(
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

    fun fetchByDeviceLocation(context: Context) {
        viewModelScope.launch {
            try {
                // device location provided via repository/location module in future; for now, we call repository directly
                // This method should be improved: better to inject a location provider and observe changes.
                // As a simple approach, repository does not access location. The LocationModule provides FusedLocationClient - we should use it from an Android component.
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.localizedMessage ?: "Konum alınamadı")
            }
        }
    }

    fun scheduleNotifications(context: Context, timings: Map<String, String>) {
        repo.scheduleNotifications(context, timings)
    }
}
