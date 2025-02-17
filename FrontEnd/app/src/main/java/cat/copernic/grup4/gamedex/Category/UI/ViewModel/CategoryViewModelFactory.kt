package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCasesAdd
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCasesGetAll

class CategoryViewModelFactory(private val categoryCasesAdd: CategoryCasesAdd,
                               private val categoryCasesGetAll: CategoryCasesGetAll) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(ModelClass: Class<T>): T {
        if (ModelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryCasesAdd, categoryCasesGetAll) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

