package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe que representa una categoria de videojocs.
 *
 * @param nameCategory El nom de la categoria.
 * @param description La descripció de la categoria.
 * @param categoryPhoto La foto de la categoria en format Base64 (opcional).
 */
data class Category(
    val nameCategory: String,
    val description: String,
    val categoryPhoto: String? = null
)