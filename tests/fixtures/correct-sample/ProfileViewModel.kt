// Correct sample. Demonstrates:
// - @HiltViewModel + @Inject
// - private MutableStateFlow + public StateFlow
// - viewModelScope (never GlobalScope)
// - SavedStateHandle.toRoute<Profile>() for type-safe nav args
// - SharingStarted.WhileSubscribed for stateIn
package com.example.good

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Profile(val userId: String)

interface ProfileRepository {
    suspend fun refresh(userId: String)
    fun observeUser(userId: String): kotlinx.coroutines.flow.Flow<User>
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: ProfileRepository,
    savedState: SavedStateHandle,
) : ViewModel() {

    private val args = savedState.toRoute<Profile>()

    val state: StateFlow<ProfileUiState> = repo.observeUser(args.userId)
        .map { ProfileUiState.Success(it) as ProfileUiState }
        .catch { emit(ProfileUiState.Error(it.message ?: "Unknown")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProfileUiState.Loading,
        )

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.Refresh -> viewModelScope.launch { repo.refresh(args.userId) }
        }
    }
}
