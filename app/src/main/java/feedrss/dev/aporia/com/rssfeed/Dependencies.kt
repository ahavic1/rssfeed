package feedrss.dev.aporia.com.rssfeed

import android.content.Context
import androidx.room.Room
import feedrss.dev.aporia.com.rssfeed.data.network.WebService
import me.toptas.rssconverter.RssConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

object Dependencies {

    private var webService: WebService =
            Retrofit.Builder()
                    .baseUrl("https://github.com")
                    .client(provideHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(RssConverterFactory.create())
                    .build().create(WebService::class.java)

    var appDatabase: AppDatabase? = null

    fun getRetrofit(): WebService {
        return webService
    }

    fun getDatabase(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app-db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
        return appDatabase!!
    }

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
    }
}
