package com.gds.marvelapp.ui.fragment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gds.marvelapp.data.model.character.CharacterModel
import com.gds.marvelapp.data.model.comics.ComicModelResponse
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
class DetailsCharacterViewModel @Inject constructor(
    private val repository: MarvelRepository
): ViewModel() {
    private val _details = MutableStateFlow<ResourceState<ComicModelResponse>>(ResourceState.Loanding())
    val details : StateFlow<ResourceState<ComicModelResponse>> get() = _details

    fun fetch(characterId: Int)= viewModelScope.launch{
        safeFetch(characterId)
    }

    private suspend fun safeFetch(characterId: Int) {
        _details.value = ResourceState.Loanding()
        try {
            val response = repository.getComics(characterId)
            _details.value = handlerResponse(response)
        }catch (t : Throwable){
            when(t){
                is IOException-> _details.value = ResourceState.Error("Erro de conecxao")
                else-> _details.value = ResourceState.Error("Falha de mapeamento")
            }
        }
    }

    private fun handlerResponse(response: Response<ComicModelResponse>): ResourceState<ComicModelResponse> {
        if (response.isSuccessful){
            response.body()?.let {values->
                return  ResourceState.Success(values)
            }
        }
        return ResourceState.Error(response.message())
    }

    fun insert(characterModel: CharacterModel) = viewModelScope.launch {
        repository.insert(characterModel)
    }

}