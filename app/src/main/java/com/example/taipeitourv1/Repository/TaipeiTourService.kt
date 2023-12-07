package com.example.taipeitourv1.Repository

import com.example.taipeitourv1.DataClass.TaipeiTourData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TaipeiTourService {
    @Headers("accept: application/json")
    @GET("open-api/{language}/Attractions/All")
    suspend fun getTaipeiTourData(@Path("language") language: String): TaipeiTourData
}