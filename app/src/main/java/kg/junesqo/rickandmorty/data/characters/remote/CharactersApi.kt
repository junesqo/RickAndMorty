package kg.junesqo.rickandmorty.data.characters.remote

import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null
    ): Response<CharactersResponse>

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Character

}