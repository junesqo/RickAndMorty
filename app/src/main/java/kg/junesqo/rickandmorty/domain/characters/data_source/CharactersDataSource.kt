package kg.junesqo.rickandmorty.domain.characters.data_source

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.junesqo.rickandmorty.data.characters.local.CharactersDao
import kg.junesqo.rickandmorty.data.characters.local.local_models.toNetwork
//import kg.junesqo.rickandmorty.data.characters.local.CharactersDao
import kg.junesqo.rickandmorty.data.characters.remote.CharactersApi
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.toRoom
import retrofit2.HttpException
import javax.inject.Inject

class CharactersDataSource @Inject constructor(

    private val api: CharactersApi,
    private val dao: CharactersDao

    ) : PagingSource<Int, Character>() {

    private var searchQuery: String? = null
    private var characterStatus: String? = null

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    fun setSearchQuery(searchString: String?) {
        if (searchString != null) {
            searchQuery = searchString
        }
    }

    fun setStatusFilter(statusString: String?) {
        if (statusString != null) {
            characterStatus = statusString
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1
        return try {
            val response = api.getAllCharacters(page = page, searchQuery, characterStatus)

            val characters: List<Character>
            if (response.isSuccessful) {

                //save to room
                response.body()?.characters?.map { it.toRoom() }?.let { dao.addCharacters(it) }

                characters = response.body()!!.characters
                Log.e("DataSource", response.body()?.characters.toString())

                var nextPage: Int? = null
                val uri = Uri.parse(response.body()!!.info.next)
                nextPage = uri.getQueryParameter("page")?.toInt()

                LoadResult.Page(
                    data = characters,
                    prevKey = null,
                    nextKey = nextPage
                )
            } else {
                Log.e("DataSource", "No results")
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            Log.e("DataSource", "No internet 2")
            getLocalData(params, e)
        } catch (e: Exception) {
            Log.e("DataSource", "No internet 3")
            getLocalData(params, e)
        }
    }

    private suspend fun getLocalData(params: LoadParams<Int>, e: Exception): LoadResult<Int, Character>{
        val pageNumberLocal = params.key ?: 0
        val characters = dao.getCharacters(params.loadSize, searchQuery, characterStatus)
        val result = characters.map { it.toNetwork() }
        return if (result.isNotEmpty()) {
            Log.e("Data Source", "Fetching local data")
            LoadResult.Page(
                data = result,
                prevKey = if (pageNumberLocal == 0) null else pageNumberLocal - 1,
                nextKey = if (result.isEmpty()) null else pageNumberLocal + 1
            )
        }else{
            LoadResult.Error(e)
        }
    }
}