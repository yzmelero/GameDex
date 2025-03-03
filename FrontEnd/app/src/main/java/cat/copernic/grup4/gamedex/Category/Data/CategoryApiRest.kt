package cat.copernic.grup4.gamedex.Category.Data

import cat.copernic.grup4.gamedex.Core.Model.Category
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfície per a les operacions de la API REST de categories.
 */
interface CategoryApiRest {

    /**
     * Afegeix una nova categoria.
     *
     * @param category La categoria a afegir.
     * @return La resposta de la API amb la categoria afegida.
     */
    @POST("category/create")
    suspend fun addCategory(@Body category: Category): Response<Category>

    /**
     * Obté totes les categories.
     *
     * @return La resposta de la API amb la llista de categories.
     */
    @GET("category/all")
    suspend fun getAllCategory(): Response<List<Category>>

    /**
     * Obté una categoria pel seu ID.
     *
     * @param categoryId L'ID de la categoria.
     * @return La resposta de la API amb la categoria obtinguda.
     */
    @GET("category/get/{categoryId}")
    suspend fun getCategoryById(@Path("categoryId") categoryId: String): Response<Category>

    /**
     * Elimina una categoria pel seu nom.
     *
     * @param nameCategory El nom de la categoria a eliminar.
     * @return La resposta de la API.
     */
    @DELETE("category/delete/{nameCategory}")
    suspend fun deleteCategory(@Path("nameCategory") nameCategory: String): Response<Unit>

    /**
     * Filtra categories basades en una consulta.
     *
     * @param query La consulta de cerca.
     * @return La resposta de la API amb la llista de categories filtrades.
     */
    @GET("category/filter/{query}")
    suspend fun filterCategories(@Path("query") query: String): Response<List<Category>>

    @PUT("category/modify/{nameCategory}")
    suspend fun modifyCategory(@Body category: Category): Response<Category>

}