package cat.copernic.grup4.gamedex.Users.Domain


import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import cat.copernic.grup4.gamedex.Core.Model.User


class UseCases(private val repository: UserRepository) {
    suspend fun registerUser(user: User) = repository.registerUser(user)

    suspend fun listUsers() = repository.getAllUsers()
}

