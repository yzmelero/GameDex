package cat.copernic.grup4.gamedex.Users.Data
/*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
*/

/***
 * Un objecte a kotlin és una sola instancia d'una classe.
 * És similar a una classe singleton de Java.
 * Si no està contingut a cap classe, la seva visibilitat per defecta és a nivell de package
 */

/*
object UserRetrofitInstance {


    //Cuando se usa el emulador de movil desde AndroidStudio, la 10.0.2.2 de la red virtual apunta a localhost de la máquina anfitrion
    private const val BASE_URL = "http://10.0.2.2:8080/api/user/"  // Reemplaza con la URL de tu servidor Spring Boot


    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Convierte JSON a objetos Kotlin
            .build()
    }
}
*/

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

    val api: UserApiRest by lazy {
        retrofit.create(UserApiRest::class.java)
    }
}