package com.example.pantaugempa.data.network

import com.example.pantaugempa.data.model.Gempa
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    // API BMKG Data Gempa Terkini
    @GET("DataMKG/TEWS/gempaterkini.json")
    suspend fun getGempaTerkini(): Gempa
}

object ApiConfig {
    private const val BASE_URL = "https://data.bmkg.go.id/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}