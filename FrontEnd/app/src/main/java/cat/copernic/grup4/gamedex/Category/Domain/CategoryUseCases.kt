package cat.copernic.grup4.gamedex.Category.Domain

import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Core.Model.Category

class CategoryCases(private val repository: CategoryRepository) {
    suspend fun addCategory(category: Category) = repository.addCategory(category)
    suspend fun getAllCategory() = repository.getAllCategory()
    suspend fun getCategoryById(categoryId: String) = repository.getCategoryById(categoryId)
    suspend fun deleteCategory(nameCategory: String) = repository.deleteCategory(nameCategory)
    suspend fun modifyCategory(nameCategory: String, category: Category) = repository.modifyCategory(nameCategory, category)
}
