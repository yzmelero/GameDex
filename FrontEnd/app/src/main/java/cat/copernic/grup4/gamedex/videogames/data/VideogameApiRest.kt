package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Interfície que conté les crides a la API REST per comunicar-se amb el backend
interface VideogameApiRest {

    @POST("videogame/create") // Indica que es farà una petició POST a la ruta "videogame/create"
    // suspend fa que faci peticions en segon pla sense bloquejar la UI
    suspend fun createVideogame(@Body videogame: Videogame): Response<Videogame> 
    // Response representa la resposta de la petició HTTP més el cos de la resposta (en aquest cas, un Videogame)
    // La resposta del servidor amb el nou joc creat

    @GET("videogame/byId/{gameId}")
    suspend fun videogamesById(@Path("gameId") gameId: String): Response<Videogame>
    // @Path("gameId") indica que el valor de gameId es substituirà a la ruta de la petició retornant la informació del joc amb aquesta ID

    @GET("videogame/all")
    suspend fun getAllVideogames(): Response<List<Videogame>>

    @GET("videogame/all/inactive")
    suspend fun getAllInactiveVideogames(): Response<List<Videogame>>

    @GET("videogame/categories")
    suspend fun getAllCategories(): Response<List<Category>>

    @DELETE("videogame/delete/{gameId}")
    suspend fun deleteVideogame(@Path("gameId") gameId: String): Response<Void>
    // Es void perquè no retorna res, només elimina el joc amb la ID gameId i conté el codi de resposta HTTP
}