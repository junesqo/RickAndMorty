package kg.junesqo.rickandmorty.data.characters.repository

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.domain.characters.data_source.CharactersDataSource
import kg.junesqo.rickandmorty.domain.characters.model.Character
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val charactersDataSource: CharactersDataSource) : CharactersRepository {

    override fun getAllCharacters(): PagingSource<Int, Character> {
        return charactersDataSource
    }


}