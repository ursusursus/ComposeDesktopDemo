import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppComponent {
    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder().build()

    private val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl("https://api.o2.sk")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

    val apiClient: ApiClient by lazy {
        ApiClient(retrofit.create(VersionCheckApi::class.java))
    }
}