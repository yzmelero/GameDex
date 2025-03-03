package cat.copernic.grup4.gamedex.Library.UI.ViewModel

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Library.Domain.LibraryUseCase
import cat.copernic.grup4.gamedex.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.ByteArrayInputStream

/**
 * ViewModel per gestionar les operacions de biblioteques de videojocs.
 *
 * @param libraryUseCase Els casos d'ús per a les operacions de biblioteques.
 */
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

    private val _rating = MutableStateFlow<Double?>(null)
    val rating: StateFlow<Double?> = _rating

    private val _existingLibraryEntry = MutableStateFlow<Library?>(null)
    val existingLibraryEntry: StateFlow<Library?> = _existingLibraryEntry

    /**
     * Afegeix un joc a la biblioteca d'un usuari.
     *
     * @param library La biblioteca que conté el joc i l'usuari.
     * @param context El context.
     */
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

    /**
     * Neteja el missatge actual.
     */
    fun clearMessage() {
        _message.value = null
    }

    /**
     * Obté la biblioteca d'un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     */
    fun getLibrary(username: String, context: Context) {
        viewModelScope.launch {
            _library.value = emptyList() // Força netejar la pantalla abans de carregar la següent
            val response =
                libraryUseCase.getLibrary(username) // Aquesta funció ha de retornar la llista de biblioteques des de la base de dades
            if (response.isSuccessful) {
                _library.value = response.body() ?: emptyList()
            } else {
                _message.value = context.getString(R.string.error_retrieving_library)
            }
        }
    }

    /**
     * Converteix una cadena Base64 a un Bitmap.
     *
     * @param base64 La cadena Base64 a convertir.
     * @return El Bitmap resultant o null si hi ha un error.
     */
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

    /**
     * Obté els comentaris per a un videojoc específic.
     *
     * @param gameId L'ID del videojoc.
     */
    fun getCommentsByGame(gameId: String, context: Context) {
        viewModelScope.launch {
            try {
                _comments.value = emptyList()
                val response = libraryUseCase.getCommentsFromLibrary(gameId)
                if (response.isSuccessful) {
                    response.body()?.let { commentList ->
                        _comments.value = commentList
                    }
                }
            } catch (e: Exception) {
                _message.value = context.getString(R.string.error_retrieving_library)
            }
        }
    }


    /**
     * Elimina un joc de la biblioteca d'un usuari.
     *
     * @param gameId   L'ID del joc.
     * @param username El nom d'usuari.
     */
    fun deleteVideogameFromLibrary(gameId: String, username: String, context: Context)  {
        viewModelScope.launch {
            try {
                Log.d("LibraryViewModel", "Deleting gameId: $gameId for user: $username")
                val response = libraryUseCase.deleteVideogameFromLibrary(gameId, username)
                if (response.isSuccessful)  {
                    Log.d("LibraryViewModel", "Deletion successful. Refreshing library")
                    getLibrary(username, context)
                }  else  {
                    Log.e("LibraryViewModel", "Failed to delete. Response code: ${response.code()}")
                }
            } catch (e: Exception)  {
                Log.e("LibraryViewModel", "Error deleting the videogame: ${e.message}")
                _message.value = context.getString(R.string.errordeletinglibrary)
            }
        }
    }

    /**
     * Obté la puntuació mitjana d'un videojoc.
     *
     * @param gameId L'ID del videojoc.
     */
    fun getAverageRating(gameId: String, context: Context) {
        viewModelScope.launch {
            try {
                val response: Response<Double> = libraryUseCase.getAverageRating(gameId)
                Log.d(
                    "LibraryViewModel",
                    "Response code: ${response.code()} - Body: ${response.body()}"
                )
                if (response.isSuccessful)  {
                    _rating.value = response.body() /*?: 0.0*/
                }  else  {
                    _message.value = "Error: ${response.code()}"
                }
            }  catch (e: Exception)  {
                _message.value = context.getString(R.string.errorretrievingrating)
            }
        }
    }

    //Mètode per a comprovar si ja hi ha una entrada per a un videojoc i un usuari concrets.
    fun checkLibraryEntry(gameId: String, username: String, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("LibraryViewModel", "Checking entry for gameId: $gameId, username: $username")
                val response = libraryUseCase.getLibraryEntry(gameId, username)
                if (response.isSuccessful) {
                    _existingLibraryEntry.value = response.body()
                    Log.d("LibraryViewModel", "Library entry found: ${_existingLibraryEntry.value}")
                } else {
                    _existingLibraryEntry.value = null
                    Log.d("LibraryViewModel", "Library entry not found")
                }
            } catch (e: Exception) {
                _message.value = context.getString(R.string.errorcheckinglibraryentry)
                Log.e("LibraryViewModel", "Error checking library entry: ${e.message}")
            }
        }
    }

    fun updateGameInLibrary(library: Library, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("LibraryViewModel", "Enviant update per Library ID: ${library.idLibrary}")
                val result = libraryUseCase.updateGameInLibrary(library)

                if (result.isSuccessful) {
                    _message.value = context.getString(R.string.commentmodifiedsuccessfully)
                    Log.d("LibraryViewModel", "Actualització correcta: ${result.body()}")
                } else {
                    _message.value = context.getString(R.string.errorupdatingcomment)
                    Log.e(
                        "LibraryViewModel",
                        "Error a la resposta del servidor: ${result.code()}"
                    )
                }
            } catch (e: Exception) {
                _message.value = context.getString(R.string.errorupdatingcomment, e.message)
                Log.e("LibraryViewModel", "Excepció actualitzant el joc: ${e.message}")
            }
        }
    }
}