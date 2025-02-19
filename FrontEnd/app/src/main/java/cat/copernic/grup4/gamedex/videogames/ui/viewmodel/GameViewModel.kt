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

    private val _getVideogame = MutableStateFlow<Videogame?>(null)
    open val gameById: StateFlow<Videogame?> = _getVideogame

    private val _videogameGetAll = MutableStateFlow<List<Videogame>>(emptyList())
    open val allVideogame: StateFlow<List<Videogame>> = _videogameGetAll


    fun createVideogame(videogame: Videogame) {
        viewModelScope.launch {
            val response = videogameUseCase.createVideogame(videogame)
            _videogameCreated.value = response.isSuccessful
        }
    }

    fun videogamesById(gameId: String) {
        viewModelScope.launch {
            val response = videogameUseCase.videogamesById(gameId)
            _getVideogame.value = response.body()
        }
    }

    fun getAllVideogames() {
        viewModelScope.launch {
            try {
                val response = videogameUseCase.getAllVideogames()
                _videogameGetAll.value = response.body() ?: emptyList()
                Log.d("DEBUG", "Juegos cargados: $response")
            } catch (e: Exception) {
                Log.e("ERROR", "Error obteniendo juegos: ${e.message}")
            }
        }
    }

}