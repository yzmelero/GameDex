package cat.copernic.grup4.gamedex.Category.Domain

import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedexandroid.Core.Model.Category

class CategoryCasesAdd(private val repository: CategoryRepository) {
    suspend fun addCategory(category: Category) = repository.addCategory(category)
}

class CategoryCasesGetAll(private val repository: CategoryRepository) {
    suspend fun getAllCategory() = repository.getAllCategory()
}