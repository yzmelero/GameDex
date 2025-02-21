package cat.copernic.grup4.gamedex.videogames.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/") // Canvia per l'IP del teu backend
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: VideogameApiRest by lazy {
        retrofit.create(VideogameApiRest::class.java)
    }
}