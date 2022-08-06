package kg.junesqo.rickandmorty.domain.characters.repository

import androidx.paging.PagingSource
import kg.junesqo.rickandmorty.domain.characters.model.Character

interface CharactersRepository {
    fun getAllCharacters(): PagingSource<Int, Character>
}