package cat.copernic.grup4.gamedex.Library.Data

import cat.copernic.grup4.gamedex.Core.Model.Library
import retrofit2.Response

class LibraryRepository {

    suspend fun addGameToLibrary(library: Library): Response<Library> {
        return RetrofitInstance.api.addGameToLibrary(library)
    }
}