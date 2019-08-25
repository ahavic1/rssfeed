package feedrss.dev.aporia.com.rssfeed.data.di

import dagger.Module
import dagger.Provides
import feedrss.dev.aporia.com.rssfeed.data.network.WebService
import me.toptas.rssconverter.RssConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://github.com")
                .client(provideHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(RssConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideWebService(): WebService =
            provideRetrofit().create(WebService::class.java)
    }
}