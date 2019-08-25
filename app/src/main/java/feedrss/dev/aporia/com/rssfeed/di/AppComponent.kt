package feedrss.dev.aporia.com.rssfeed.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import feedrss.dev.aporia.com.rssfeed.App
import feedrss.dev.aporia.com.rssfeed.data.di.CacheModule
import feedrss.dev.aporia.com.rssfeed.data.di.NetworkModule
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedDataModule
import feedrss.dev.aporia.com.rssfeed.data.repository.PostDataModule
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelFactoryModule
import javax.inject.Singleton

@Component(modules = [
    // Android
    AndroidSupportInjectionModule::class,

    // Application
    ActivityBuilder::class,
    AppModule::class,
    ViewModelFactoryModule::class,
    SchedulersModule::class,

    // Data
    CacheModule::class,
    NetworkModule::class,
    PostDataModule::class,
    FeedDataModule::class
])
@Singleton
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}