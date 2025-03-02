package cat.copernic.grup4.gamedex.videogames.ui.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.videogames.domain.VideogameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

// ViewModel que conté la lògica de la vista de jocs
open class GameViewModel(private val videogameUseCase: VideogameUseCase) : ViewModel() {

    // Emmagatzema si s'ha creat un joc o no
    private val _videogameCreated = MutableStateFlow<Boolean?>(null)
    val videogameCreated: StateFlow<Boolean?> = _videogameCreated

    private val _getVideogame = MutableStateFlow<Videogame?>(null)
    open val gameById: StateFlow<Videogame?> = _getVideogame

    private val _videogameGetAll = MutableStateFlow<List<Videogame>>(emptyList())
    open val allVideogame: StateFlow<List<Videogame>> = _videogameGetAll

    private val _videogameGetAllInactive = MutableStateFlow<List<Videogame>>(emptyList())
    open val allInactiveVideogame: StateFlow<List<Videogame>> = _videogameGetAllInactive

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _videogameDeleted = MutableStateFlow<Boolean?>(null)
    val videogameDeleted: StateFlow<Boolean?> = _videogameDeleted

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
            val response = videogameUseCase.getAllVideogames()
            _videogameGetAll.value = response.body() ?: emptyList()
        }
    }

    fun getAllInactiveVideogames() {
        viewModelScope.launch {
            val response = videogameUseCase.getAllInactiveVideogames()
            _videogameGetAllInactive.value = response.body() ?: emptyList()
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            val response = videogameUseCase.getAllCategories()
            if (response.isSuccessful) {
                _categories.value = response.body() ?: emptyList()
            }
        }
    }

    fun deleteVideogame(gameId: String) {
        viewModelScope.launch {
            val response = videogameUseCase.deleteVideogame(gameId)
            _videogameDeleted.value = response.isSuccessful
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

    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()

            inputStream?.use { stream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (stream.read(buffer).also { bytesRead = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }
            }

            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT) // Convertir a Base64
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun validateVideogame(gameId: String) {
        viewModelScope.launch {
            val response = videogameUseCase.validateVideogame(gameId)
            if (response.isSuccessful) {
                getAllInactiveVideogames()
            }
        }
    }

    private val _videogameByCategory = MutableStateFlow<List<Videogame>>(emptyList())
    val videogameByCategory: StateFlow<List<Videogame>> = _videogameByCategory
    
    fun videogamesByCategory(categoryId: String) {
        viewModelScope.launch {
            val response = videogameUseCase.videogamesByCategory(categoryId)
            _videogameByCategory.value = response.body() ?: emptyList()
        }
    }

    private val _videogameByName = MutableStateFlow<List<Videogame>>(emptyList())
    val searchVideogames: StateFlow<List<Videogame>> = _videogameByName

    fun searchVideogames(nameGame: String) {
        viewModelScope.launch {
            val response = videogameUseCase.videogamesByName(nameGame)
            _videogameByName.value = response.body() ?: emptyList()
        }
    }
}