package com.guzelduatr.app.data

import com.guzelduatr.app.data.remote.AladhanApi
import com.guzelduatr.app.data.remote.AladhanResponse
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

    fun getLastFetchedAt(): Long = lastFetchedAt
}
