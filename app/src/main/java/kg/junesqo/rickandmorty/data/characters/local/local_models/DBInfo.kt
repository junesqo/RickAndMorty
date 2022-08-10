package kg.junesqo.rickandmorty.data.characters.local.local_models

import androidx.room.ColumnInfo

data class DBInfo(
    @ColumnInfo(name = "count")
    val count: Int,
    @ColumnInfo(name = "next")
    val next: String,
    @ColumnInfo(name = "pages")
    val pages: Int,
    @ColumnInfo(name = "prev")
    val prev: Any
)