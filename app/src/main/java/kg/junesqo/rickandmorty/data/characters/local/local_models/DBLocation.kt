package kg.junesqo.rickandmorty.data.characters.local.local_models

import androidx.room.ColumnInfo

data class DBLocation(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String
)