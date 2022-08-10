package kg.junesqo.rickandmorty.domain.characters.repository

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character

interface CharactersRepository {
    fun getAllCharacters(
        searchString: String?,
        characterStatus: String?,
    ): PagingSource<Int, Character>

    suspend fun getSingleCharacter(characterId: Int, connectionStatus: Boolean): Character
}