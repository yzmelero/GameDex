package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCasesAdd

class CategoryViewModelFactory(private val categoryCasesAdd: CategoryCasesAdd) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(ModelClass: Class<T>): T {
        if (ModelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryCasesAdd) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

