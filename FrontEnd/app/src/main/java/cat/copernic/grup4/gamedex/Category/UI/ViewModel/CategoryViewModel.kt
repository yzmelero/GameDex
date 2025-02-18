package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedexandroid.Core.Model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


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
}
