package cat.copernic.grup4.gamedex.Library.Data

import cat.copernic.grup4.gamedex.Core.Model.Library
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfície per a les operacions de la API REST de biblioteques de videojocs.
 */
interface LibraryApiRest {

    /**
     * Afegeix un joc a la biblioteca d'un usuari.
     *
     * @param library La biblioteca que conté el joc i l'usuari.
     * @return La resposta de la API amb la biblioteca actualitzada.
     */
    @POST("library/add")
    suspend fun addGameToLibrary(@Body library: Library): Response<Library>

    /**
     * Obté la biblioteca d'un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return La resposta de la API amb la llista de biblioteques de l'usuari.
     */
    @GET("library/get")
    suspend fun getLibrary(@Query("username") username: String): Response<List<Library>>

    /**
     * Obté els comentaris per a un videojoc específic.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb la llista de comentaris.
     */
    @GET("library/comments/{gameId}")
    suspend fun getCommentsForVideogame(@Path("gameId") gameId: String): Response<List<Library>>

    /**
     * Elimina un joc de la biblioteca d'un usuari.
     *
     * @param gameId   L'ID del joc.
     * @param username El nom d'usuari.
     * @return La resposta de la API.
     */
    @DELETE("library/delete/{gameId}/{username}")
    suspend fun deleteVideogameFromLibrary(@Path("gameId") gameId: String, @Path("username") username: String): Response<Void>

    /**
     * Obté la puntuació mitjana d'un videojoc.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb la puntuació mitjana.
     */
    @GET("library/averagerating/{gameId}")
    suspend fun getAverageRating(@Path("gameId") gameId: String): Response<Double>

    @GET("library/verify/{gameId}/{username}")
    suspend fun getLibraryEntry(@Path("gameId") gameId: String, @Path("username") username: String): Response<Library>

    @PUT("library/update")
    suspend fun updateGameInLibrary(@Body library: Library): Response<Library>
}