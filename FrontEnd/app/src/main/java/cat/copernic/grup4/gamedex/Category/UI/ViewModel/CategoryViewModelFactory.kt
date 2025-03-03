package cat.copernic.grup4.gamedex.Category.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.Category.Domain.CategoryCases
import cat.copernic.grup4.gamedex.R

/**
 * Factory per crear instàncies de CategoryViewModel.
 *
 * @param categoryCases Els casos d'ús per a les operacions de categories.
 */
class CategoryViewModelFactory(private val categoryCases: CategoryCases) : ViewModelProvider.Factory {
    
    /**
     * Crea una nova instància de CategoryViewModel.
     *
     * @param ModelClass La classe del ViewModel a crear.
     * @return Una nova instància de CategoryViewModel.
     * @throws IllegalArgumentException Si la classe no és assignable a CategoryViewModel.
     */
    override fun <T: ViewModel> create(ModelClass: Class<T>): T {
        if (ModelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryCases) as T
        }
        throw IllegalArgumentException(R.string.unkViewModel.toString())
    }
}