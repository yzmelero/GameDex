package cat.copernic.grup4.gamedex.videogames.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase

// Factory per obrir una instancia del viewModel
class GameViewModelFactory(private val videogameUseCase: VideogameUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(videogameUseCase) as T
        }
        throw IllegalArgumentException(R.string.unkViewModel.toString())
    }

    
}