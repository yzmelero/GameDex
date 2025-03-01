package cat.copernic.grup4.gamedex.Library.Data

import cat.copernic.grup4.gamedex.Core.Model.Library
import retrofit2.Response

class LibraryRepository {

    suspend fun addGameToLibrary(library: Library): Response<Library> {
        return RetrofitInstance.api.addGameToLibrary(library)
    }

    suspend fun getLibrary(username: String): Response<List<Library>> {
        return RetrofitInstance.api.getLibrary(username) // Aquesta funció crida a l'API o base de dades
    }

    suspend fun getCommentsForVideogame(gameId: String): Response<List<Library>> {
        val response = RetrofitInstance.api.getCommentsForVideogame(gameId)
        return response
    }

    suspend fun deleteVideogameFromLibrary(gameId: String, username: String): Response<Void> {
        return RetrofitInstance.api.deleteVideogameFromLibrary(gameId, username)
    }
}