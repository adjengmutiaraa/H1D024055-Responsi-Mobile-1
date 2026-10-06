package com.example.pantaugempa.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantaugempa.data.model.GempaItem
import com.example.pantaugempa.data.network.ApiConfig
import com.example.pantaugempa.data.repository.GempaRepository
import com.example.pantaugempa.ui.state.GempaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GempaViewModel(
    private val repository: GempaRepository = GempaRepository(ApiConfig.apiService)
) : ViewModel() {

    private val _uiState = MutableStateFlow<GempaUiState>(GempaUiState.Loading)
    val uiState: StateFlow<GempaUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGempa = MutableStateFlow<GempaItem?>(null)
    val selectedGempa: StateFlow<GempaItem?> = _selectedGempa.asStateFlow()

    init {
        fetchDataGempa()
    }

    fun selectGempa(gempa: GempaItem) {
        _selectedGempa.value = gempa
    }

    fun fetchDataGempa() {
        viewModelScope.launch {
            _uiState.value = GempaUiState.Loading
            try {
                val listGempa = repository.getGempaTerkini()
                _uiState.value = GempaUiState.Success(listGempa)
            } catch (e: Exception) {
                _uiState.value = GempaUiState.Error(e.localizedMessage ?: "Gagal memuat data gempa")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}