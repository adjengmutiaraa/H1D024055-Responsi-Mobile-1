package com.example.pantaugempa.data.repository

import com.example.pantaugempa.data.model.GempaItem
import com.example.pantaugempa.data.network.ApiService

class GempaRepository(
    private val apiService: ApiService
) {
    suspend fun getGempaTerkini(): List<GempaItem> {
        val response = apiService.getGempaTerkini()
        return response.infogempa.gempaList
    }
}
