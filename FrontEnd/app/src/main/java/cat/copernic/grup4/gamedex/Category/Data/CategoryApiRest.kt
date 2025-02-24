package cat.copernic.grup4.gamedex.Category.Data

import cat.copernic.grup4.gamedex.Core.Model.Category
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryApiRest {
    @POST("category/create")
    suspend fun addCategory(@Body category: Category): Response<Category>

    @GET("category/all")
    suspend fun getAllCategory(): Response<List<Category>>

    @GET("category/{categoryId}")
    suspend fun getCategoryById(@Path("categoryId") categoryId: String): Response<Category>

}