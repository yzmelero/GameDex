package cat.copernic.grup4.gamedex.Library.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase

class LibraryViewModelFactory {
    class LibraryViewModelFactory(private val libraryUseCase: LibraryUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                return LibraryViewModel(libraryUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }


    }
}