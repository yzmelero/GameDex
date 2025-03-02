package cat.copernic.grup4.gamedex.Library.Domain

import cat.copernic.grup4.gamedex.Core.Model.Library
import cat.copernic.grup4.gamedex.Core.Model.Videogame
import cat.copernic.grup4.gamedex.Library.Data.LibraryRepository
import retrofit2.Response

/**
 * Classe que encapsula els casos d'ús per a les operacions de biblioteques de videojocs.
 *
 * @param repository El repositori de biblioteques.
 */
class LibraryUseCase(private val repository: LibraryRepository) {

    /**
     * Afegeix un joc a la biblioteca d'un usuari.
     *
     * @param library La biblioteca que conté el joc i l'usuari.
     * @return La resposta de la API amb la biblioteca actualitzada.
     */
    suspend fun addGameToLibrary(library: Library): Response<Library> {
        return repository.addGameToLibrary(library)
    }

    /**
     * Obté la biblioteca d'un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return La resposta de la API amb la llista de biblioteques de l'usuari.
     */
    suspend fun getLibrary(username: String): Response<List<Library>> {
        return repository.getLibrary(username) // Aquesta funció farà la crida a la base de dades
    }

    /**
     * Obté els comentaris per a un videojoc específic.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb la llista de comentaris.
     */
    suspend fun getCommentsFromLibrary(gameId: String): Response<List<Library>> {
        return repository.getCommentsForVideogame(gameId)
    }

    /**
     * Elimina un joc de la biblioteca d'un usuari.
     *
     * @param gameId   L'ID del joc.
     * @param username El nom d'usuari.
     */
    suspend fun deleteVideogameFromLibrary(gameId: String, username: String) = repository.deleteVideogameFromLibrary(gameId, username)

    /**
     * Obté la puntuació mitjana d'un videojoc.
     *
     * @param gameId L'ID del videojoc.
     * @return La resposta de la API amb la puntuació mitjana.
     */
    suspend fun getAverageRating(gameId: String): Response<Double> {
        return repository.getAverageRating(gameId)
    }
}