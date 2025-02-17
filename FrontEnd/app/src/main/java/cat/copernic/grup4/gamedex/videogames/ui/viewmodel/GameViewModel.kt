package cat.copernic.grup4.gamedex.videogames.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val videogameUseCase: VideogameUseCase) : ViewModel() {

    private val _videogameCreated = MutableStateFlow<Boolean?>(null)
    val videogameCreated: StateFlow<Boolean?> = _videogameCreated

    fun createVideogame(videogame: Videogame) {
        viewModelScope.launch {
            val response = videogameUseCase.createVideogame(videogame)
            _videogameCreated.value = response.isSuccessful
        }
    }
}