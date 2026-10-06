package com.example.pantaugempa.ui.state

import com.example.pantaugempa.data.model.GempaItem

sealed interface GempaUiState {
    object Loading : GempaUiState
    data class Success(val listGempa: List<GempaItem>) : GempaUiState
    data class Error(val message: String) : GempaUiState
}