package feedrss.dev.aporia.com.rssfeed.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import feedrss.dev.aporia.com.rssfeed.ui.main.MainActivity

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainFragmentBuilder::class])
    abstract fun provideMainActivity(): MainActivity
}
