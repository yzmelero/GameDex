package cat.copernic.grup4.gamedex.videogames.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.R
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase

/**
 * Factory per crear instàncies de GameViewModel.
 *
 * @param videogameUseCase Els casos d'ús per a les operacions de videojocs.
 */
class GameViewModelFactory(private val videogameUseCase: VideogameUseCase) : ViewModelProvider.Factory {
    /**
     * Crea una nova instància de GameViewModel.
     *
     * @param modelClass La classe del ViewModel a crear.
     * @return Una nova instància de GameViewModel.
     * @throws IllegalArgumentException Si la classe no és assignable a GameViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(videogameUseCase) as T
        }
        throw IllegalArgumentException(R.string.unkViewModel.toString())
    }
}