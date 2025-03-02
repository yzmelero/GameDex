package cat.copernic.grup4.gamedex.videogames.domain

import cat.copernic.grup4.gamedex.Core.Model.Category
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.videogames.data.VideogameRepository
import retrofit2.Response

// Intermediari entre la capa de presentació i la de dades, implementa la lògica de negoci si fos necessari
// Facilita la gestió de les dades i permet la comunicació amb la API sense haver de modificar la UI
class VideogameUseCase (private val repository: VideogameRepository) {
    suspend fun createVideogame(videogame: Videogame) = repository.createVideogame(videogame)
    suspend fun videogamesById(gameId: String) = repository.videogamesById(gameId)
    suspend fun getAllVideogames() = repository.getAllVideogames()
    suspend fun getAllInactiveVideogames() = repository.getAllInactiveVideogames()
    suspend fun getAllCategories(): Response<List<Category>> = repository.getAllCategories()
    suspend fun deleteVideogame(gameId: String) = repository.deleteVideogame(gameId)
    suspend fun validateVideogame(gameId: String) = repository.validateVideogame(gameId)
}