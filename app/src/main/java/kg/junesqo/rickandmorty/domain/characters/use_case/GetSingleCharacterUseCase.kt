package kg.junesqo.rickandmorty.domain.characters.use_case

import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import javax.inject.Inject

class GetSingleCharacterUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend fun invoke(characterId: Int, connectionStatus: Boolean): Character {
        return charactersRepository.getSingleCharacter(characterId, connectionStatus)
    }
}