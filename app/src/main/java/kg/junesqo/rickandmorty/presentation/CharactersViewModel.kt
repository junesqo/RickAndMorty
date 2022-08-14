package kg.junesqo.rickandmorty.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character
import kg.junesqo.rickandmorty.domain.characters.use_case.GetAllCharactersUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    ) : ViewModel() {

    var selectedStatus = MutableLiveData<String>()

    fun select(status: String?){
        Log.e("ViewModel", status.toString())
        selectedStatus.value = status.toString()
    }

    fun getCharacters(searchString: String?, characterStatus: String?): Flow<PagingData<Character>> {
        val characters: StateFlow<PagingData<Character>> = Pager(
        PagingConfig(
            pageSize = 0,
            maxSize = 30,
            prefetchDistance = 1,
            initialLoadSize = 10,
            enablePlaceholders = false))
    {
        getAllCharactersUseCase.invoke(searchString, characterStatus)
    }
        .flow
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        return characters
    }

}