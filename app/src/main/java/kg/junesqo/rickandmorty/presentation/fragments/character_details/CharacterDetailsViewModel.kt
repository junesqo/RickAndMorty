package kg.junesqo.rickandmorty.presentation.fragments.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.junesqo.rickandmorty.domain.characters.model.Character
import kg.junesqo.rickandmorty.domain.characters.use_case.GetAllCharactersUseCase
import kg.junesqo.rickandmorty.domain.characters.use_case.GetSingleCharacterUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel() {

    suspend fun getSingleCharacter(characterId: Int) = flow {
        emit(getSingleCharacterUseCase.invoke(characterId))
    }

}