package kg.junesqo.rickandmorty.data.characters.local.local_models

import androidx.room.ColumnInfo

data class DBCharactersResponse(
    @ColumnInfo(name = "info")
    val info: DBInfo,
    @ColumnInfo(name = "results")
    val characters: List<DBCharacter>
)







