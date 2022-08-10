package kg.junesqo.rickandmorty.data.characters.remote.remote_models

import com.google.gson.annotations.SerializedName
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBCharacter
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBLocation
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBOrigin
import java.io.Serializable

data class Character(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
):Serializable

fun Character.toRoom() = DBCharacter(
    created,
    episode,
    gender,
    id,
    image,
    DBLocation(location.name, location.url),
    name,
    DBOrigin(origin.name, origin.url),
    species,
    status,
    type,
    url
)