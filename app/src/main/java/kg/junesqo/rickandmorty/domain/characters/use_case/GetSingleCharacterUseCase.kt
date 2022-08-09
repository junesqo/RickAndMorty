package kg.junesqo.rickandmorty.domain.characters.use_case

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.domain.characters.model.Character
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import retrofit2.Response
import javax.inject.Inject

class GetSingleCharacterUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend fun invoke(characterId: Int): Response<Character> {
        return charactersRepository.getSingleCharacter(characterId)
    }
}