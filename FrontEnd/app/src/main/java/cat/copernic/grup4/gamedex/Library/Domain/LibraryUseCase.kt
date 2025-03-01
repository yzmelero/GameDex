package cat.copernic.grup4.gamedex.Library.Domain

import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Library.Data.LibraryRepository
import retrofit2.Response


class LibraryUseCase(private val repository: LibraryRepository) {
    suspend fun addGameToLibrary(library: Library): Response<Library> {
        return repository.addGameToLibrary(library)
    }

    suspend fun getLibrary(username: String): Response<List<Library>> {
        return repository.getLibrary(username) // Aquesta funció farà la crida a la base de dades
    }

    suspend fun getCommentsFromLibrary(gameId: String): Response<List<Library>> {
        return repository.getCommentsForVideogame(gameId)
    }

    suspend fun deleteVideogameFromLibrary(gameId: String, username: String) = repository.deleteVideogameFromLibrary(gameId, username)

}
