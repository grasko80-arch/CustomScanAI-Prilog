package com.example.customscanapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// DTO для запроса
data class CheckRequest(
    val fromCountry: String,
    val toCountry: String,
    val borderType: String,
    val itemKeyword: String
)

// DTO для ответа (должен совпадать с backend CheckResponse)
data class CheckResponse(
    val result: String
)

// Retrofit API-интерфейс
interface ApiService {
    @POST("/check")
    fun checkItem(@Body request: CheckRequest): Call<CheckResponse>
}
