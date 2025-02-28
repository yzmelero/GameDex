package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response

// Actua com intermediari entre la capa de domini i la de dades
// Facilita la gestió de les dades i permet la comunicació amb la API REST sense haver de modificar la capa de domini
class VideogameRepository {
    // Crida a la API REST per crear, obtenir i eliminar jocs...

    suspend fun createVideogame(videogame: Videogame): Response<Videogame> {
        return RetrofitInstance.api.createVideogame(videogame)
    }
    suspend fun videogamesById(gameId: String): Response<Videogame> {
        return RetrofitInstance.api.videogamesById(gameId)
    }
    suspend fun getAllVideogames(): Response<List<Videogame>> {
        return RetrofitInstance.api.getAllVideogames()
    }
    suspend fun getAllInactiveVideogames(): Response<List<Videogame>> {
        return RetrofitInstance.api.getAllInactiveVideogames()
    }
    suspend fun getAllCategories(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategories()
    }
    suspend fun deleteVideogame(gameId: String): Response<Void> {
        return RetrofitInstance.api.deleteVideogame(gameId)
    }
}