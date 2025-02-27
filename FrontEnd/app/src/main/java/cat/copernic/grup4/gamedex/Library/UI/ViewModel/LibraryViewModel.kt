package cat.copernic.grup4.gamedex.Library.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//TODO Afegir Strings
class LibraryViewModel(private val libraryUseCase: LibraryUseCase) : ViewModel() {

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _libraryState = MutableStateFlow<Boolean?>(null)
    val libraryState: StateFlow<Boolean?> = _libraryState

    fun addGameToLibrary(library: Library) {
        viewModelScope.launch {
            val response = libraryUseCase.addGameToLibrary(library)
            _message.value = if (response.isSuccessful) {
                _libraryState.value = true
                R.string.succGameToLib.toString()
            } else {
                _libraryState.value = false
                R.string.errorAddGameToLib.toString()
            }
        }

    }
    fun clearMessage() {
        _message.value = null

    }
}