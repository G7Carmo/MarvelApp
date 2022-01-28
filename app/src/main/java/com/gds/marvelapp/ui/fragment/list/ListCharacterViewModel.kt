package com.gds.marvelapp.ui.fragment.list

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
class ListCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

    private val _list = MutableStateFlow<ResourceState<CharacterModelResponse>>(ResourceState.Loanding())
    val list : StateFlow<ResourceState<CharacterModelResponse>> = _list

    init {
        fecth()
    }

    private fun fecth() = viewModelScope.launch {
        safeFetch()
    }

    private suspend fun safeFetch() {
        try {
            val response = repository.list()
            _list.value = handlerResponse(response)
        }catch (t : Throwable){
            when(t){
                is IOException-> _list.value = ResourceState.Error("Erro na conexão com a internet")
                else-> _list.value = ResourceState.Error("Falha na conversao de dados")
            }
        }
    }

    private fun handlerResponse(response: Response<CharacterModelResponse>): ResourceState<CharacterModelResponse> {
        if (response.isSuccessful){
            response.body()?.let {values->
                return ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

}