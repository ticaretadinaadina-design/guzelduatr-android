package com.guzelduatr.app.data.remote

import com.squareup.moshi.Json

data class AladhanResponse(
    val code: Int,
    val status: String,
    val data: AladhanData
)

data class AladhanData(
    val timings: Map<String, String>,
    val date: AladhanDate
)

data class AladhanDate(
    val readable: String
)
