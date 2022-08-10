package kg.junesqo.rickandmorty.data.characters.repository

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.data.characters.local.CharactersDao
import kg.junesqo.rickandmorty.data.characters.local.local_models.toNetwork
import kg.junesqo.rickandmorty.data.characters.remote.CharactersApi
import kg.junesqo.rickandmorty.domain.characters.data_source.CharactersDataSource
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersDataSource: CharactersDataSource,
    private val charactersApi: CharactersApi,
    private val charactersDao: CharactersDao
    ) : CharactersRepository {

    override fun getAllCharacters(
        searchString: String?,
        characterStatus: String?,
    ): PagingSource<Int, Character> {
        charactersDataSource.setSearchQuery(searchString)
        charactersDataSource.setStatusFilter(characterStatus)
        return charactersDataSource
    }

    override suspend fun getSingleCharacter(characterId: Int, connectionStatus: Boolean): Character {
        return if (connectionStatus){
            charactersApi.getSingleCharacter(characterId)
        } else {
            charactersDao.getSingleCharacter(characterId).toNetwork()
        }
    }

}