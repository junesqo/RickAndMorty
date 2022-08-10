package kg.junesqo.rickandmorty.data.characters.local.local_models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Location
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Origin

@Entity(
    tableName = "characters"
)
data class DBCharacter(
    @ColumnInfo(name = "created")
    var created: String,
    @ColumnInfo(name = "episode")
    var episode: List<String>,
    @ColumnInfo(name = "gender")
    var gender: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "location")
    var location: DBLocation,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "origin")
    var origin: DBOrigin,
    @ColumnInfo(name = "species")
    var species: String,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "url")
    var url: String
)

fun DBCharacter.toNetwork() = Character(
    created, episode, gender, id, image, Location(location.name, location.url), name, Origin(origin.name, origin.url),
    species, status, type, url
)