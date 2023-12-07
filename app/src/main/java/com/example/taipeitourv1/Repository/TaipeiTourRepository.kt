package com.example.taipeitourv1.Repository

import com.example.taipeitourv1.DataClass.TaipeiTourData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaipeiTourRepository {
    suspend fun getTaipeiTourData(language: String): ArrayList<TaipeiTourData> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val TaipeiTourAPI = retrofit.create(TaipeiTourService::class.java)
        var taipeiTrourData = ArrayList<TaipeiTourData>()
        taipeiTrourData.add(TaipeiTourAPI.getTaipeiTourData(language))
        return taipeiTrourData
    }
}