package cat.copernic.grup4.gamedex.Users.UI.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.copernic.grup4.gamedex.Users.Domain.UseCases
import cat.copernic.grup4.gamedex.Core.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val useCases: UseCases) : ViewModel() {

    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> = _registrationSuccess

    fun registerUser(user: User) {
        viewModelScope.launch {
            val response = useCases.registerUser(user)
            _registrationSuccess.value = response.isSuccessful
        }
    }
}