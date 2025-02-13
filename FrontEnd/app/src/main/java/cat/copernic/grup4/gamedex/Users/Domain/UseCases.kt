package cat.copernic.grup4.gamedex.Users.Domain

import cat.copernic.grup4.gamedex.Core.Model.User
import cat.copernic.grup4.gamedex.Users.Data.UserRepository
import retrofit2.Call
import retrofit2.Response

class UseCases(private val repository: UserRepository) {
    fun createUser(user: User): Response<User> {
        return repository.createUser(user)
    }
}