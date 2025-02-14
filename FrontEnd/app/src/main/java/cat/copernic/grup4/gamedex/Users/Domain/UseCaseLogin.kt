package cat.copernic.grup4.gamedex.Users.Domain

import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Users.Data.UserRepository


class UseCaseLogin(private val repository: UserRepository) {
    suspend fun loginUser(user: User) = repository.loginUser(user)
}

