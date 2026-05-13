// Correct sample. Demonstrates:
// - stateless ProfileScreen + stateful ProfileRoute
// - hiltViewModel() injection
// - collectAsStateWithLifecycle()
// - sealed interface for UI state
// - Material 3 imports
// - PascalCase composables
// - state hoisting (state in, lambdas out)
package com.example.good

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val user: User) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

sealed interface ProfileIntent {
    data object Refresh : ProfileIntent
}

data class User(val id: String, val name: String, val email: String)

@Composable
fun ProfileRoute(vm: ProfileViewModel = hiltViewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    ProfileScreen(state, onIntent = vm::onIntent)
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onIntent: (ProfileIntent) -> Unit,
) {
    when (state) {
        ProfileUiState.Loading ->
            CircularProgressIndicator()

        is ProfileUiState.Success ->
            Column(Modifier.padding(16.dp)) {
                Text(state.user.name, style = MaterialTheme.typography.titleLarge)
                Text(state.user.email, style = MaterialTheme.typography.bodyMedium)
                Button(onClick = { onIntent(ProfileIntent.Refresh) }) {
                    Text("Refresh")
                }
            }

        is ProfileUiState.Error ->
            Text(state.message, color = MaterialTheme.colorScheme.error)
    }
}
