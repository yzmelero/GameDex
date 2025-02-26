package cat.copernic.grup4.gamedex.Category.Data

import cat.copernic.grup4.gamedex.Core.Model.Category
import retrofit2.Response

class CategoryRepository {
    suspend fun addCategory(category: Category): Response<Category> {
        return RetrofitInstance.api.addCategory(category)
    }
    suspend fun getAllCategory(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategory()
    }
    suspend fun getCategoryById(categoryId: String): Response<Category> {
        return RetrofitInstance.api.getCategoryById(categoryId)
    }
    suspend fun deleteCategory(nameCategory: String): Response<Unit> {
        return RetrofitInstance.api.deleteCategory(nameCategory)
    }
    suspend fun modifyCategory(nameCategory: String, category: Category): Response<Category> {
        return RetrofitInstance.api.modifyCategory(nameCategory, category)
    }
}
