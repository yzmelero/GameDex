package cat.copernic.grup4.gamedex.Library.UI.ViewModel

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream

//TODO Afegir Strings
class LibraryViewModel(private val libraryUseCase: LibraryUseCase) : ViewModel() {


    private val _library = MutableStateFlow<List<Library>>(emptyList())
    val library: StateFlow<List<Library>> = _library

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    private val _libraryState = MutableStateFlow<Boolean?>(null)
    val libraryState: StateFlow<Boolean?> = _libraryState

    private val _comments = MutableStateFlow<List<Library>>(emptyList())
    val comments: StateFlow<List<Library>> = _comments

    private val _deleteSuccess = MutableStateFlow<Boolean?>(null)
    val deleteSuccess: StateFlow<Boolean?> = _deleteSuccess

    fun addGameToLibrary(library: Library, context: Context) {
        viewModelScope.launch {
            val response = libraryUseCase.addGameToLibrary(library)
            if (response.isSuccessful) {
                _libraryState.value = true
                _message.value = context.getString(R.string.succGameToLib)
            } else {
                _libraryState.value = false
                _message.value = context.getString(R.string.errorAddGameToLib)
                Log.e("LibraryRepository", "Failed to add game: ${response.code()}")
            }
        }

    }
    fun clearMessage() {
        _message.value = null

    }

    fun getLibrary(username: String) {
        viewModelScope.launch {
            _library.value = emptyList() //Força natejar la pantalla abans de càrregar la següent
            val response = libraryUseCase.getLibrary(username) // Aquesta funció ha de retornar la llista de biblioteques des de la base de dades
            if (response.isSuccessful) {
                _library.value = response.body() ?: emptyList()
            } else {
                _message.value = "Error retrieving library"
            }
        }
    }

    fun base64ToBitmap(base64: String): ImageBitmap? {
        return try {
            val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
            val byteArrayInputStream = ByteArrayInputStream(decodedBytes)
            val bitmap = BitmapFactory.decodeStream(byteArrayInputStream)
            bitmap.asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    fun getCommentsByGame(gameId: String) {
        viewModelScope.launch {
            try {
                _comments.value = emptyList()
                val response = libraryUseCase.getCommentsFromLibrary(gameId)
                if (response.isSuccessful){
                    response.body()?.let{
                        commentList -> _comments.value = commentList
                    }
                }
            } catch (e: Exception) {
                _message.value = "Error retrieving library"
            }
        }
    }
    //TODO Strings
    fun deleteVideogameFromLibrary(gameId: String, username: String){
        viewModelScope.launch {
            try {
                Log.d("LibraryViewModel", "Deleting gameId: $gameId for user: $username")
                val response = libraryUseCase.deleteVideogameFromLibrary(gameId, username)
                if (response.isSuccessful){
                    Log.d("LibraryViewModel", "Deletion successful. Refreshing library")
                    getLibrary(username)
                }else{
                    Log.e("LibraryViewModel", "Failed to delete. Response code: ${response.code()}")
                }
            } catch (e: Exception){
                Log.e("LibraryViewModel", "Error deleting the videogame: ${e.message}")
                _message.value = "Error deleting the videogame from the library."
            }
        }
    }
}