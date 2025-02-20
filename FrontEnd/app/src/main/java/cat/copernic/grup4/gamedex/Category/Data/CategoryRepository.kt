package cat.copernic.grup4.gamedex.Category.Data

import cat.copernic.grup4.gamedexandroid.Core.Model.Category
import retrofit2.Response

class CategoryRepository {
    suspend fun addCategory(category: Category): Response<Category> {
        return RetrofitInstance.api.addCategory(category)
    }
    suspend fun getAllCategory(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategory()
    }
    suspend fun getCategoryById(categoryName: String): Response<Category> {
        return RetrofitInstance.api.getCategoryById(categoryName)
    }
}
