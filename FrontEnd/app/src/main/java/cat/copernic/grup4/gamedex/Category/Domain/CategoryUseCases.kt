package cat.copernic.grup4.gamedex.Category.Domain

import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Core.Model.Category

class CategoryCases(private val repository: CategoryRepository) {
    suspend fun addCategory(category: Category) = repository.addCategory(category)
    suspend fun getAllCategory() = repository.getAllCategory()
    suspend fun getCategoryById(categoryId: String) = repository.getCategoryById(categoryId)
}
