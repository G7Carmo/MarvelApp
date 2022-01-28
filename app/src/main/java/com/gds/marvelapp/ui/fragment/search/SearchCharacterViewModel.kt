package com.gds.marvelapp.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gds.marvelapp.data.model.character.CharacterModelResponse
import com.gds.marvelapp.repository.MarvelRepository
import com.gds.marvelapp.ui.state.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
@HiltViewModel
class SearchCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

private val _searchCharacter = MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Empty())
val searchCharacter : StateFlow<ResourceState<CharacterModelResponse>> get() = _searchCharacter

     fun fetch(nameStartWith: String) = viewModelScope.launch {
         safeFetch(nameStartWith)
     }

    private suspend fun safeFetch(nameStartWith: String) {
        _searchCharacter.value = ResourceState.Loanding()
        try {
            val response = repository.list(nameStartWith)
            _searchCharacter.value = handleResponse(response)
        }catch (t : Throwable){
            when(t){
                is IOException-> _searchCharacter.value = ResourceState.Error("Erro de rede")
                else -> _searchCharacter.value = ResourceState.Error("Erro na conversao ")
            }
        }
    }

    private fun handleResponse(response: Response<CharacterModelResponse>): ResourceState<CharacterModelResponse> {
        if(response.isSuccessful){
            response.body()?.let {value->
                return ResourceState.Success(value)
            }
        }
        return ResourceState.Error(response.message())
    }
}