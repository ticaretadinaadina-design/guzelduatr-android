package com.guzelduatr.app.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AladhanApi {
    @GET("timingsByCity")
    suspend fun getTimingsByCity(
        @Query("city") city: String,
        @Query("country") country: String,
        @Query("method") method: Int = 2
    ): Response<AladhanResponse>
}
