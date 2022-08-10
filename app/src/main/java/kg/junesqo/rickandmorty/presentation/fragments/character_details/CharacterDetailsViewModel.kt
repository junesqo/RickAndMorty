package kg.junesqo.rickandmorty.presentation.fragments.character_details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.junesqo.rickandmorty.domain.characters.use_case.GetSingleCharacterUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel() {

    suspend fun getSingleCharacter(characterId: Int, connectionStatus: Boolean) = flow {
        emit(getSingleCharacterUseCase.invoke(characterId, connectionStatus))
    }

}