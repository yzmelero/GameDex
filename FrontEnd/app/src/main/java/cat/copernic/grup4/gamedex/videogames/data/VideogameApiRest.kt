package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfície que conté les crides a la API REST per comunicar-se amb el backend.
 */
interface VideogameApiRest {

    /**
     * Crea un nou videojoc.
     *
     * @param videogame El videojoc a crear.
     * @return La resposta de la API amb el videojoc creat.
     */
    @POST("videogame/create")
    suspend fun createVideogame(@Body videogame: Videogame): Response<Videogame>

    /**
     * Obté un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb el videojoc obtingut.
     */
    @GET("videogame/byId/{gameId}")
    suspend fun videogamesById(@Path("gameId") gameId: String): Response<Videogame>

    /**
     * Obté tots els videojocs.
     *
     * @return La resposta de la API amb la llista de videojocs.
     */
    @GET("videogame/all")
    suspend fun getAllVideogames(): Response<List<Videogame>>

    /**
     * Obté tots els videojocs inactius.
     *
     * @return La resposta de la API amb la llista de videojocs inactius.
     */
    @GET("videogame/all/inactive")
    suspend fun getAllInactiveVideogames(): Response<List<Videogame>>

    /**
     * Obté totes les categories de videojocs.
     *
     * @return La resposta de la API amb la llista de categories.
     */
    @GET("videogame/categories")
    suspend fun getAllCategories(): Response<List<Category>>

    /**
     * Elimina un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API.
     */
    @DELETE("videogame/delete/{gameId}")
    suspend fun deleteVideogame(@Path("gameId") gameId: String): Response<Void>

    /**
     * Valida un videojoc pel seu ID.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb el videojoc validat.
     */
    @PUT("videogame/validate/{gameId}")
    suspend fun validateVideogame(@Path("gameId") gameId: String): Response<Videogame>

    @GET("videogame/byCategory/{categoryId}")
    suspend fun videogamesByCategory(@Path("categoryId") categoryId: String): Response<List<Videogame>>

    @GET("videogame/byName/{nameGame}")
    suspend fun videogamesByName(@Path("nameGame") nameGame: String): Response<List<Videogame>>

    @PUT("videogame/update/{gameId}")
    suspend fun updateVideogame(@Body videogame: Videogame): Response<Videogame>
}