package kg.junesqo.rickandmorty.data.characters

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.junesqo.rickandmorty.data.characters.remote.CharactersApi
import kg.junesqo.rickandmorty.data.characters.repository.CharactersRepositoryImpl
import kg.junesqo.rickandmorty.di.NetworkModule
import kg.junesqo.rickandmorty.domain.characters.data_source.CharactersDataSource
import kg.junesqo.rickandmorty.domain.characters.repository.CharactersRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object CharactersModule {

    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepository(charactersDataSource: CharactersDataSource):CharactersRepository{
        return CharactersRepositoryImpl(charactersDataSource)
    }
}