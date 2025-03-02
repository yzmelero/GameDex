package cat.copernic.grup4.gamedex.Category.Data

import cat.copernic.grup4.gamedex.Core.Model.Category
import retrofit2.Response

/**
 * Repositori per gestionar les operacions de la API REST de categories.
 */
class CategoryRepository {

    /**
     * Afegeix una nova categoria.
     *
     * @param category La categoria a afegir.
     * @return La resposta de la API amb la categoria afegida.
     */
    suspend fun addCategory(category: Category): Response<Category> {
        return RetrofitInstance.api.addCategory(category)
    }

    /**
     * Obté totes les categories.
     *
     * @return La resposta de la API amb la llista de categories.
     */
    suspend fun getAllCategory(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategory()
    }

    /**
     * Obté una categoria pel seu ID.
     *
     * @param categoryId L'ID de la categoria.
     * @return La resposta de la API amb la categoria obtinguda.
     */
    suspend fun getCategoryById(categoryId: String): Response<Category> {
        return RetrofitInstance.api.getCategoryById(categoryId)
    }

    /**
     * Elimina una categoria pel seu nom.
     *
     * @param nameCategory El nom de la categoria a eliminar.
     * @return La resposta de la API.
     */
    suspend fun deleteCategory(nameCategory: String): Response<Unit> {
        return RetrofitInstance.api.deleteCategory(nameCategory)
    }

    /**
     * Filtra categories basades en una consulta.
     *
     * @param query La consulta de cerca.
     * @return La resposta de la API amb la llista de categories filtrades.
     */
    suspend fun filterCategories(query: String): Response<List<Category>> {
        return RetrofitInstance.api.filterCategories(query)
    }
}