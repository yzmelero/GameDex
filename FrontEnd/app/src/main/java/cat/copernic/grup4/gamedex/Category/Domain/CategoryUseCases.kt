package cat.copernic.grup4.gamedex.Category.Domain

import cat.copernic.grup4.gamedex.Category.Data.CategoryRepository
import cat.copernic.grup4.gamedex.Core.Model.Category

/**
 * Classe que encapsula els casos d'ús per a les operacions de categories.
 *
 * @param repository El repositori de categories.
 */
class CategoryCases(private val repository: CategoryRepository) {

    /**
     * Afegeix una nova categoria.
     *
     * @param category La categoria a afegir.
     * @return La resposta de la API amb la categoria afegida.
     */
    suspend fun addCategory(category: Category) = repository.addCategory(category)

    /**
     * Obté totes les categories.
     *
     * @return La resposta de la API amb la llista de categories.
     */
    suspend fun getAllCategory() = repository.getAllCategory()

    /**
     * Obté una categoria pel seu ID.
     *
     * @param categoryId L'ID de la categoria.
     * @return La resposta de la API amb la categoria obtinguda.
     */
    suspend fun getCategoryById(categoryId: String) = repository.getCategoryById(categoryId)

    /**
     * Elimina una categoria pel seu nom.
     *
     * @param nameCategory El nom de la categoria a eliminar.
     * @return La resposta de la API.
     */
    suspend fun deleteCategory(nameCategory: String) = repository.deleteCategory(nameCategory)

    /**
     * Filtra categories basades en una consulta.
     *
     * @param query La consulta de cerca.
     * @return La resposta de la API amb la llista de categories filtrades.
     */
    suspend fun filterCategories(query: String) = repository.filterCategories(query)

    /**
     * Modifica una categoria.
     * 
     * @param category La categoria a modificar.
     * @return La resposta de la API amb la categoria modificada.  
     */
    suspend fun modifyCategory(category: Category) = repository.modifyCategory(category)
}