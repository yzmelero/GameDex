package cat.copernic.grup4.gamedex.Core.Model

/**
 * Classe segellada que representa els diferents estats d'una sol·licitud d'inici de sessió.
 */
sealed class LoginState {

    /**
     * Estat que indica que l'inici de sessió ha estat exitós.
     *
     * @param loginResponse La resposta d'inici de sessió.
     */
    data class Success(val loginResponse: LoginResponse) : LoginState()

    /**
     * Estat que indica que l'inici de sessió ha fallat.
     *
     * @param exception L'excepció que ha causat el fall.
     */
    data class Failure(val exception: Throwable) : LoginState()

    /**
     * Estat que indica que l'inici de sessió està en procés de càrrega.
     */
    object Loading : LoginState() // AIXÒ NOMÉS SI VOLEM GESTIONAR L'ESTAT DE CÀRREGA
}