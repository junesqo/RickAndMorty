package kg.junesqo.rickandmorty.domain.characters.use_case

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    fun invoke(searchString: String?, characterStatus: String?): PagingSource<Int, Character> {
        return charactersRepository.getAllCharacters(searchString, characterStatus)
    }
}