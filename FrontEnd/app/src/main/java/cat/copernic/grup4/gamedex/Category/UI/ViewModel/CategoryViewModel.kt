package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Core.Model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

/**
 * ViewModel per gestionar les operacions de categories.
 *
 * @param categoryCases Els casos d'ús per a les operacions de categories.
 */
class CategoryViewModel(private val categoryCases: CategoryCases) : ViewModel() {

    private val _categoryAdded = MutableStateFlow<Boolean?>(null)
    val categoryAdded: StateFlow<Boolean?> = _categoryAdded

    private val _categoryGetAll = MutableStateFlow<List<Category>>(emptyList())
    val category: StateFlow<List<Category>> = _categoryGetAll

    private val _categoryGetById = MutableStateFlow<Category?>(null)
    val categoryGetById: StateFlow<Category?> get() = _categoryGetById

    private val _categoryDeleted = MutableStateFlow<Boolean?>(null)
    val categoryDeleted: StateFlow<Boolean?> = _categoryDeleted

    val _categoryModified = MutableStateFlow<Boolean?>(null)
    val categoryModified: StateFlow<Boolean?> = _categoryModified
    
   /**
     * Afegeix una nova categoria.
     *
     * @param category La categoria a afegir.
     */
    fun addCategory(category: Category) {
        viewModelScope.launch {
            val response = categoryCases.addCategory(category)
            _categoryAdded.value = response.isSuccessful
        }
    }

    /**
     * Obté totes les categories.
     */
    fun getAllCategory() {
        viewModelScope.launch {
            val response = categoryCases.getAllCategory()
            _categoryGetAll.value = response.body() ?: emptyList()
        }
    }

    /**
     * Obté una categoria pel seu ID.
     *
     * @param categoryId L'ID de la categoria.
     * @return La categoria obtinguda o null si no es troba.
     */
    suspend fun getCategoryById(categoryId: String): Category? {
        return try {
            val response = categoryCases.getCategoryById(categoryId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Elimina una categoria pel seu nom.
     *
     * @param nameCategory El nom de la categoria a eliminar.
     */
    fun deleteCategory(nameCategory: String) {
        viewModelScope.launch {
            val response = categoryCases.deleteCategory(nameCategory)
            _categoryDeleted.value = response.isSuccessful
        }
    }

    /**
     * Filtra categories basades en una consulta.
     *
     * @param query La consulta de cerca.
     */
    fun filterCategories(query: String) {
        viewModelScope.launch {
            try {
                val response = categoryCases.filterCategories(query)
                if (response.isSuccessful) {
                    response.body()?.let { filteredList ->
                        _categoryGetAll.value = filteredList
                    }
                } else {
                    Log.e("CategoryViewModel", "API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error filtering categories: ${e.message}")
            }
        }
    }

    /**
     * Modifica una categoria.
     * 
     * @param modifyCategory La categoria a modificar.
     * @return La resposta de la API amb la categoria modificada.
     */
    fun modifyCategory(modifyCategory: Category) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Modifying category: $modifyCategory")

                val response = categoryCases.modifyCategory(modifyCategory)

                Log.d("ViewModel", "Response received: ${response?.code()} - ${response?.message()}")

                if (response != null) {
                    if (response.isSuccessful) {
                        Log.d("ViewModel", "Category modified successfully!")
                        _categoryModified.value = true

                        if (_categoryGetById.value?.nameCategory == modifyCategory.nameCategory) {
                            _categoryGetById.value = modifyCategory
                        }
                    } else {
                        Log.e("ViewModel", "Error modifying category: ${response.errorBody()?.string()}")
                        _categoryModified.value = false
                    }
                } else {
                    Log.e("ViewModel", "Response is null, possible network error.")
                    _categoryModified.value = false
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Exception while modifying category: ${e.message}", e)
                _categoryModified.value = false
            }
        }
    }

    /**
     * Converteix una cadena Base64 a un Bitmap.
     *
     * @param base64String La cadena Base64 a convertir.
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
     * Converteix un URI a una cadena Base64.
     *
     * @param context El context.
     * @param uri     El URI a convertir.
     * @return La cadena Base64 resultant o null si hi ha un error.
     */
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
}