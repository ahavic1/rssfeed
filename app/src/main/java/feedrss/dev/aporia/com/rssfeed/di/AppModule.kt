package feedrss.dev.aporia.com.rssfeed.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import feedrss.dev.aporia.com.rssfeed.App
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideApplication(app: App): Application

    @Binds
    @Singleton
    abstract fun provideContext(application: Application): Context
}