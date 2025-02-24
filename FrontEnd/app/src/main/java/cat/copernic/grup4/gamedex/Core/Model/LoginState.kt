package cat.copernic.grup4.gamedex.Core.Model

sealed class LoginState {
    data class Success(val loginResponse: LoginResponse) : LoginState()
    data class Failure(val exception: Throwable) : LoginState()
    object Loading : LoginState() // AIXÒ NOMÉS SI VOLEM GESTIONAR L'ESTAT DE CÀRREGA

}