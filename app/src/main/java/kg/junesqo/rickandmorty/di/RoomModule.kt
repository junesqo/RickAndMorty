package kg.junesqo.rickandmorty.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.junesqo.rickandmorty.data.characters.local.CharactersDao
import kg.junesqo.rickandmorty.data.characters.local.CharactersDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class )
object RoomModule {
    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context): CharactersDatabase {
        return CharactersDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun charactersDao(appDatabase: CharactersDatabase): CharactersDao {
        return appDatabase.charactersDao()
    }
}