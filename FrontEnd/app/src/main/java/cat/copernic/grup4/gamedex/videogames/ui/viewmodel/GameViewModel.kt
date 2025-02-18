package cat.copernic.grup4.gamedex.videogames.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class GameViewModel(private val videogameUseCase: VideogameUseCase) : ViewModel() {

    private val _videogameCreated = MutableStateFlow<Boolean?>(null)
    val videogameCreated: StateFlow<Boolean?> = _videogameCreated
    fun createVideogame(videogame: Videogame) {
        viewModelScope.launch {
            val response = videogameUseCase.createVideogame(videogame)
            _videogameCreated.value = response.isSuccessful
        }
    }

    private val _getVideogame = MutableStateFlow<Videogame?>(null)
    open val gameById: StateFlow<Videogame?> = _getVideogame
    /*
    fun videogamesById(gameId: String) {
        viewModelScope.launch {
            val response = videogameUseCase.videogamesById(gameId)
            _getVideogame.value = response.body()
        }
    }*/
    fun videogamesById(gameId: String) {
        viewModelScope.launch {
            try {
                val response = videogameUseCase.videogamesById(gameId)
                if (response.isSuccessful) {
                    _getVideogame.value = response.body()
                } else {
                    Log.e("GameViewModel", "Error en la respuesta: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("GameViewModel", "Error obteniendo el videojuego", e)
            }
        }
    }


}