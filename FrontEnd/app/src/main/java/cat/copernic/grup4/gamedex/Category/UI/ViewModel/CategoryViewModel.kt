package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedexandroid.Core.Model.Category
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
