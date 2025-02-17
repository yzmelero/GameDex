package cat.copernic.grup4.gamedex.videogames.data

import cat.copernic.grup4.gamedex.Core.Model.Videogame
import retrofit2.Response

class VideogameRepository {
    suspend fun createVideogame(videogame: Videogame): Response<Videogame> {
        return RetrofitInstance.api.createVideogame(videogame)
    }
    suspend fun videogamesById(gameId: String): Response<Videogame> {
        return RetrofitInstance.api.videogamesById(gameId)
    }
}