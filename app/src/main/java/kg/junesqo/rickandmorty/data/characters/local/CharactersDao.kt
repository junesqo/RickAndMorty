package kg.junesqo.rickandmorty.data.characters.local

import androidx.room.*
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBCharacter

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters WHERE id LIKE '%' || :characterId || '%'")
    suspend fun getSingleCharacter(characterId: Int): DBCharacter

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacters(characters: List<DBCharacter>)

    @Query("SELECT * FROM characters WHERE (:searchQuery IS NULL OR name LIKE '%' || :searchQuery || '%') AND (:statusFilter IS NULL OR status LIKE '%' || :statusFilter || '%') AND id IN (SELECT id FROM characters LIMIT :size) ")
    suspend fun getCharacters(size: Int, searchQuery: String?, statusFilter: String?): List<DBCharacter>

}