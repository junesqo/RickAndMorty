package kg.junesqo.rickandmorty.data.characters.remote.remote_models

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val characters: List<Character>
)







