package cat.copernic.grup4.gamedex.videogames.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Configura retrofit per gestionar les peticions HTTP a la API
object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply { // Registrar peticions i respostes HTTP
        level = HttpLoggingInterceptor.Level.BODY // Veure el cos de la petició i la resposta
    }

    private val client = OkHttpClient.Builder() // Es crea un client HTTP i s'afegeix l'interceptor de logs
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/") // URL base del backend
            .addConverterFactory(GsonConverterFactory.create()) // Utilitza Gson per convertir JSON a objectes de Kotlin
            .client(client) // Client configurat
            .build()
    }

    val api: VideogameApiRest by lazy { // Lazy perquè només es crea quan es necessita
        retrofit.create(VideogameApiRest::class.java) // Es crea una instància de l'API REST
    }
}