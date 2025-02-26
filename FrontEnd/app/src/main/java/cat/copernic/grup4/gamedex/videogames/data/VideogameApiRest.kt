package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VideogameApiRest {

    @POST("videogame/create")
    suspend fun createVideogame(@Body videogame: Videogame): Response<Videogame>

    @GET("videogame/byId/{gameId}")
    suspend fun videogamesById(@Path("gameId") gameId: String): Response<Videogame>

    @GET("videogame/all")
    suspend fun getAllVideogames(): Response<List<Videogame>>

    @GET("videogame/categories")
    suspend fun getAllCategories(): Response<List<Category>>

}