package kg.junesqo.rickandmorty.data.characters.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.junesqo.rickandmorty.data.characters.local.local_models.DBCharacter

@Database(
    entities = [DBCharacter::class],
    version = 2,
)
@TypeConverters(Converters::class)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    companion object {
        @Volatile
        private var INSTANCE: CharactersDatabase? = null

        fun getDatabase(context: Context): CharactersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharactersDatabase::class.java,
                    "characters_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}