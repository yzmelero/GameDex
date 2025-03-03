package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe que representa una biblioteca de videojocs.
 *
 * @param idLibrary L'identificador de la biblioteca.
 * @param user L'usuari propietari de la biblioteca.
 * @param videogame El videojoc a la biblioteca.
 * @param state L'estat del videojoc a la biblioteca.
 * @param description La descripció del videojoc a la biblioteca.
 * @param rating La puntuació del videojoc a la biblioteca.
 * @param publishedDate La data de publicació del videojoc a la biblioteca.
 */
data class Library(
    val idLibrary: String,
    val user: User,
    val videogame: Videogame,
    val state: StateType,
    val description: String,
    val rating: Double,
    val publishedDate: String
)