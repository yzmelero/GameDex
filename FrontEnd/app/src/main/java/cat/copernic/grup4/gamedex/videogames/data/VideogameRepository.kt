package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response

/**
 * Repositori que actua com intermediari entre la capa de domini i la de dades.
 * Facilita la gestió de les dades i permet la comunicació amb la API REST sense haver de modificar la capa de domini.
 */
class VideogameRepository {

    /**
     * Crea un nou videojoc.
     *
     * @param videogame El videojoc a crear.
     * @return La resposta de la API amb el videojoc creat.
     */
    suspend fun createVideogame(videogame: Videogame): Response<Videogame> {
        return RetrofitInstance.api.createVideogame(videogame)
    }

    /**
     * Obté un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb el videojoc obtingut.
     */
    suspend fun videogamesById(gameId: String): Response<Videogame> {
        return RetrofitInstance.api.videogamesById(gameId)
    }

    /**
     * Obté tots els videojocs.
     *
     * @return La resposta de la API amb la llista de videojocs.
     */
    suspend fun getAllVideogames(): Response<List<Videogame>> {
        return RetrofitInstance.api.getAllVideogames()
    }

    /**
     * Obté tots els videojocs inactius.
     *
     * @return La resposta de la API amb la llista de videojocs inactius.
     */
    suspend fun getAllInactiveVideogames(): Response<List<Videogame>> {
        return RetrofitInstance.api.getAllInactiveVideogames()
    }

    /**
     * Obté totes les categories de videojocs.
     *
     * @return La resposta de la API amb la llista de categories.
     */
    suspend fun getAllCategories(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategories()
    }

    /**
     * Elimina un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API.
     */
    suspend fun deleteVideogame(gameId: String): Response<Void> {
        return RetrofitInstance.api.deleteVideogame(gameId)
    }

    /**
     * Valida un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb el videojoc validat.
     */
    suspend fun validateVideogame(gameId: String): Response<Videogame> {
        return RetrofitInstance.api.validateVideogame(gameId)
    }
}