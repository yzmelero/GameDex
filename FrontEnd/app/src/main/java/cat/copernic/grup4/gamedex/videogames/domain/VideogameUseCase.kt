package cat.copernic.grup4.gamedex.videogames.domain

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.videogames.data.VideogameRepository
import retrofit2.Response

/**
 * Classe que encapsula els casos d'ús per a les operacions de videojocs.
 *
 * @param repository El repositori de videojocs.
 */
class VideogameUseCase(private val repository: VideogameRepository) {

    /**
     * Crea un nou videojoc.
     *
     * @param videogame El videojoc a crear.
     * @return La resposta de la API amb el videojoc creat.
     */
    suspend fun createVideogame(videogame: Videogame) = repository.createVideogame(videogame)

    /**
     * Obté un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb el videojoc obtingut.
     */
    suspend fun videogamesById(gameId: String) = repository.videogamesById(gameId)

    /**
     * Obté tots els videojocs.
     *
     * @return La resposta de la API amb la llista de videojocs.
     */
    suspend fun getAllVideogames() = repository.getAllVideogames()

    /**
     * Obté tots els videojocs inactius.
     *
     * @return La resposta de la API amb la llista de videojocs inactius.
     */
    suspend fun getAllInactiveVideogames() = repository.getAllInactiveVideogames()

    /**
     * Obté totes les categories de videojocs.
     *
     * @return La resposta de la API amb la llista de categories.
     */
    suspend fun getAllCategories(): Response<List<Category>> = repository.getAllCategories()

    /**
     * Elimina un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API.
     */
    suspend fun deleteVideogame(gameId: String) = repository.deleteVideogame(gameId)

    /**
     * Valida un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb el videojoc validat.
     */
    suspend fun validateVideogame(gameId: String) = repository.validateVideogame(gameId)

    /**
     * Obté els videojocs d'una categoria.
     * 
     * @param categoryId L'ID de la categoria.
     * @return La resposta de la API amb la llista de videojocs de la categoria.
     */
    suspend fun videogamesByCategory(categoryId: String) = repository.videogamesByCategory(categoryId)

    /**
     * Obté els videojocs per nom.
     * 
     * @param nameGame El nom del videojoc.
     * @return La resposta de la API amb la llista de videojocs amb el nom.
     */
    suspend fun videogamesByName(nameGame: String) = repository.videogamesByName(nameGame)

    /**
     * Modificar un videojoc.
     * 
     * @param videogame El videojoc a modificar.
     * @return La resposta de la API amb el videojoc modificat.
     */
    suspend fun updateVideogame(videogame: Videogame) = repository.updateVideogame(videogame)
}