package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.Core.Model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream

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

    fun addCategory(category: Category) {
        viewModelScope.launch {
            val response = categoryCases.addCategory(category)
            _categoryAdded.value = response.isSuccessful
        }
    }
    
    fun getAllCategory() {
        viewModelScope.launch {
            val response = categoryCases.getAllCategory()
            _categoryGetAll.value = response.body() ?: emptyList()
        }
    }

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

    fun deleteCategory(nameCategory: String) {
        viewModelScope.launch {
            val response = categoryCases.deleteCategory(nameCategory)
            _categoryDeleted.value = response.isSuccessful
        }
    }

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


    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
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

}
