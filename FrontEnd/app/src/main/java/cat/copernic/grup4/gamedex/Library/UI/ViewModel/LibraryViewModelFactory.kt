package cat.copernic.grup4.gamedex.Library.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.R


class LibraryViewModelFactory {

    /**
     * Factory per crear instàncies de LibraryViewModel.
     *
     * @param libraryUseCase Els casos d'ús per a les operacions de biblioteques.
     */
    class LibraryViewModelFactory(private val libraryUseCase: LibraryUseCase) :
        ViewModelProvider.Factory {
        /**
         * Crea una nova instància de LibraryViewModel.
         *
         * @param modelClass La classe del ViewModel a crear.
         * @return Una nova instància de LibraryViewModel.
         * @throws IllegalArgumentException Si la classe no és assignable a LibraryViewModel.
         */
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LibraryViewModel(libraryUseCase) as T
            }
            throw IllegalArgumentException(R.string.unkViewModel.toString())
        }
    }
}