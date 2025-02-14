package cat.copernic.grup4.gamedex.Users.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cat.copernic.grup4.gamedex.Users.Domain.UseCases

class UserViewModelFactory(private val useCases: UseCases) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(useCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}