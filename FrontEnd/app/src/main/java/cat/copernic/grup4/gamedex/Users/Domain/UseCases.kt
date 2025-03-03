package cat.copernic.grup4.gamedex.Users.Domain

import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Core.Model.User

/**
 * Classe que encapsula els casos d'ús per a les operacions d'usuaris.
 *
 * @param repository El repositori d'usuaris.
 */
class UseCases(private val repository: UserRepository) {

    /**
     * Registra un nou usuari.
     *
     * @param user L'usuari a registrar.
     * @return La resposta de la API amb l'usuari registrat.
     */
    suspend fun registerUser(user: User) = repository.registerUser(user)

    /**
     * Verifica les credencials d'inici de sessió.
     *
     * @param username El nom d'usuari.
     * @param password La contrasenya.
     * @return La resposta de la API amb la informació de l'usuari.
     */
    suspend fun loginUser(username: String, password: String) = repository.loginUser(username, password)

    /**
     * Obté tots els usuaris.
     *
     * @return La resposta de la API amb la llista d'usuaris.
     */
    suspend fun listUsers() = repository.getAllUsers()

    /**
     * Obté tots els usuaris inactius.
     *
     * @return La resposta de la API amb la llista d'usuaris inactius.
     */
    suspend fun listInactiveUsers() = repository.getAllInactiveUsers()

    /**
     * Obté un usuari pel seu nom d'usuari.
     *
     * @param username El nom d'usuari.
     * @return La resposta de la API amb l'usuari obtingut.
     */
    suspend fun getUser(username: String) = repository.getUser(username)

    /**
     * Valida un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb l'usuari validat.
     */
    suspend fun validateUser(userId: String) = repository.validateUser(userId)

    /**
     * Actualitza un usuari.
     *
     * @param user L'usuari a actualitzar.
     * @return La resposta de la API amb l'usuari actualitzat.
     */
    suspend fun updateUser(user: User) = repository.updateUser(user)

    /**
     * Elimina un usuari pel seu ID.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API.
     */
    suspend fun deleteUser(userId: String) = repository.deleteUser(userId)

    /**
     * Obté tots els usuaris associats a un ID d'usuari.
     *
     * @param userId L'ID de l'usuari.
     * @return La resposta de la API amb la llista d'usuaris.
     */
    suspend fun getAllUsersByUserId(userId: String) = repository.getAllUsersByUserId(userId)

    /**
     * Compta el nombre de jocs en una biblioteca per estat.
     *
     * @param userId L'ID de l'usuari.
     * @param state L'estat dels jocs.
     * @return La resposta de la API amb el nombre de jocs.
     */
    suspend fun countByUserAndState(userId: String, state: String) = repository.countByUserAndState(userId, state)
}