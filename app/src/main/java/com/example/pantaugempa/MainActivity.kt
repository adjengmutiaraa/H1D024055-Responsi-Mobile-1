package com.example.pantaugempa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantaugempa.ui.screen.DetailGempaScreen
import com.example.pantaugempa.ui.screen.KatalogGempaScreen
import com.example.pantaugempa.ui.theme.PantauGempaTheme
import com.example.pantaugempa.ui.theme.Surface
import com.example.pantaugempa.ui.viewmodel.GempaViewModel

class MainActivity : ComponentActivity() {
    private val gempaViewModel: GempaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantauGempaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = gempaViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: GempaViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedGempa by viewModel.selectedGempa.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "katalog") {
        composable("katalog") {
            KatalogGempaScreen(
                uiState = uiState,
                searchQuery = searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                onGempaClick = { gempa ->
                    viewModel.selectGempa(gempa)
                    navController.currentBackStackEntry?.savedStateHandle?.set("selectedGempa", gempa)
                    navController.navigate("detail")
                },
                onRetry = viewModel::fetchDataGempa
            )
        }
        composable("detail") {
            val gempa = selectedGempa ?: navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<com.example.pantaugempa.data.model.GempaItem>("selectedGempa")

            gempa?.let {
                DetailGempaScreen(
                    gempa = it,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}