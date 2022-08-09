package kg.junesqo.rickandmorty.domain.characters.repository

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.domain.characters.model.Character
import retrofit2.Response

interface CharactersRepository {
    fun getAllCharacters(searchString: String?, characterStatus: String?): PagingSource<Int, Character>
    suspend fun getSingleCharacter(characterId: Int): Response<Character>
}