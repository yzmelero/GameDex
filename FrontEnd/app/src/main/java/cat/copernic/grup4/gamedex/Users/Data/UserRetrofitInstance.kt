package cat.copernic.grup4.gamedex.Users.Data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objecte que proporciona una instància de Retrofit configurada per a les operacions de la API REST d'usuaris.
 */
object RetrofitInstance {
    /**
     * Interceptor per registrar les peticions i respostes HTTP.
     */
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Client HTTP configurat amb l'interceptor de registre.
     */
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    /**
     * Instància de Retrofit configurada amb la URL base i el client HTTP.
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/") // Canvia per l'IP del teu backend
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /**
     * Instància de l'API d'usuaris creada per Retrofit.
     */
    val api: UserApiRest by lazy {
        retrofit.create(UserApiRest::class.java)
    }
}