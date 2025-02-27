package cat.copernic.grup4.gamedex.Users.Domain


import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Core.Model.User


class UseCases(private val repository: UserRepository) {
    suspend fun registerUser(user: User) = repository.registerUser(user)

    suspend fun loginUser(username: String, password: String) = repository.loginUser(username, password)

    suspend fun listUsers() = repository.getAllUsers()

    suspend fun listInactiveUsers() = repository.getAllInactiveUsers()

    suspend fun getUser(username: String) = repository.getUser(username)

    suspend fun validateUser(userId: String) = repository.validateUser(userId)

    suspend fun  updateUser(user: User) = repository.updateUser(user)

    suspend fun deleteUser(userId: String) = repository.deleteUser(userId)

    suspend fun getAllUsersByUserId(userId: String) = repository.getAllUsersByUserId(userId)
}

