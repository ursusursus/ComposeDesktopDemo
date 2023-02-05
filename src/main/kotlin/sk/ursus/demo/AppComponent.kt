package sk.ursus.demo

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import sk.ursus.demo.db.AppDatabase

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

    val dao: Dao by lazy {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        val database = AppDatabase(driver)
        AppDatabase.Schema.create(driver)
        Dao(database.playerQueries)
    }
}