package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response

class VideogameRepository {
    suspend fun createVideogame(videogame: Videogame): Response<Videogame> {
        return RetrofitInstance.api.createVideogame(videogame)
    }
    suspend fun videogamesById(gameId: String): Response<Videogame> {
        return RetrofitInstance.api.videogamesById(gameId)
    }
    suspend fun getAllVideogames(): Response<List<Videogame>> {
        return RetrofitInstance.api.getAllVideogames()
    }
    suspend fun getAllCategories(): Response<List<Category>> {
        return RetrofitInstance.api.getAllCategories()
    }
    suspend fun deleteVideogame(gameId: String): Response<Void> {
        return RetrofitInstance.api.deleteVideogame(gameId)
    }
}