package kg.junesqo.rickandmorty.data.characters.repository

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.data.characters.remote.CharactersApi
import kg.junesqo.rickandmorty.domain.characters.data_source.CharactersDataSource
import kg.junesqo.rickandmorty.domain.characters.model.Character
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import retrofit2.Response
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersDataSource: CharactersDataSource,
    private val charactersApi: CharactersApi
    ) : CharactersRepository {

    override fun getAllCharacters(searchString: String?, characterStatus: String?): PagingSource<Int, Character> {
        charactersDataSource.setSearchQuery(searchString)
        charactersDataSource.setStatusFilter(characterStatus)
        return charactersDataSource
    }

    override suspend fun getSingleCharacter(characterId: Int): Response<Character> {
        return charactersApi.getSingleCharacter(characterId)
    }


}