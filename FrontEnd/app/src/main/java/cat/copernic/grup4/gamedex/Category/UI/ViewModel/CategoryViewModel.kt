package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCasesAdd
import cat.copernic.grup4.gamedexandroid.Core.Model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CategoryViewModel(private val categoryCasesAdd: CategoryCasesAdd) : ViewModel() {

    private val _categoryAdded = MutableStateFlow<Boolean?>(null)
    val categoryAdded: StateFlow<Boolean?> = _categoryAdded

    fun addCategory(category: Category) {
        viewModelScope.launch {
            val response = categoryCasesAdd.addCategory(category)
            _categoryAdded.value = response.isSuccessful
        }
    }
}
