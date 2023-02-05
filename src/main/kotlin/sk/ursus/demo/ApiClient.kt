package sk.ursus.demo

import com.squareup.moshi.JsonClass
import retrofit2.http.GET

class ApiClient(private val api: VersionCheckApi) {
    suspend fun foo(): Int {
        return api.minVersion().android ?: 0
    }
}

interface VersionCheckApi {
    @GET("version")
    suspend fun minVersion(): VersionResponse
}

@JsonClass(generateAdapter = true)
data class VersionResponse(
    val android: Int?,
    val androidTM: Int?,
    val androidRA: Int?,
)