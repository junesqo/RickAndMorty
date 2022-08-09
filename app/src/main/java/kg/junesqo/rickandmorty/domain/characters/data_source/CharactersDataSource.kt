package kg.junesqo.rickandmorty.domain.characters.data_source

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.junesqo.rickandmorty.data.characters.remote.CharactersApi
import kg.junesqo.rickandmorty.domain.characters.model.Character
import retrofit2.HttpException
import javax.inject.Inject

class CharactersDataSource @Inject constructor(

    private val api: CharactersApi,

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
        try {
            val page = params.key ?: 1
            val response = api.getAllCharacters(page = page)

            return if (response.isSuccessful) {

                //Searching
                val characters = if (searchQuery != null && characterStatus == null) {
                    response.body()!!.characters.filter {
                        it.name.contains(searchQuery!!, true)
                    }
                } else if (searchQuery == null && characterStatus != null) {
                    response.body()!!.characters.filter {
                        it.status == characterStatus!!
                    }
                } else if (searchQuery != null && characterStatus != null) {
                    response.body()!!.characters.filter {
                        it.name.contains(searchQuery!!, true)
                        it.status.contains(characterStatus!!)
                    }
                } else {
                    response.body()!!.characters
                }
                Log.e("DataSource", characterStatus.toString())
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
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}