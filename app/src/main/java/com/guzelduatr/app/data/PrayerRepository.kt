package com.guzelduatr.app.data

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.guzelduatr.app.worker.NotificationWorker
import com.guzelduatr.app.data.remote.AladhanApi
import com.guzelduatr.app.data.remote.AladhanResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

class PrayerRepository @Inject constructor(
    private val api: AladhanApi
) {
    private var lastResponse: AladhanResponse? = null
    private var lastFetchedAt: Long = 0L

    suspend fun getTimingsByCity(city: String, country: String): Result<AladhanResponse> {
        return try {
            val response = api.getTimingsByCity(city, country)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    lastResponse = body
                    lastFetchedAt = System.currentTimeMillis()
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                lastResponse?.let { Result.success(it) } ?: Result.failure(Exception("API error ${response.code()}"))
            }
        } catch (e: Exception) {
            lastResponse?.let { Result.success(it) } ?: Result.failure(e)
        }
    }

    suspend fun getTimingsByLocation(lat: Double, lon: Double): Result<AladhanResponse> {
        return try {
            val response = api.getTimingsByCoordinates(lat, lon)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    lastResponse = body
                    lastFetchedAt = System.currentTimeMillis()
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                lastResponse?.let { Result.success(it) } ?: Result.failure(Exception("API error ${response.code()}"))
            }
        } catch (e: Exception) {
            lastResponse?.let { Result.success(it) } ?: Result.failure(e)
        }
    }

    fun scheduleNotifications(context: Context, timings: Map<String, String>) {
        // schedule a OneTimeWorkRequest per timing at the correct time
        timings.forEach { (name, timeStr) ->
            val delay = com.guzelduatr.app.util.TimeUtils.parseTimeToNextDelayMillis(timeStr)
            val data = workDataOf("title" to "Namaz Vakti", "message" to "$name - $timeStr")
            val request = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(java.time.Duration.ofMillis(delay))
                .setInputData(data)
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }

    fun getLastFetchedAt(): Long = lastFetchedAt
}
