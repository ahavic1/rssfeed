package feedrss.dev.aporia.com.rssfeed.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import feedrss.dev.aporia.com.rssfeed.ui.main.category.CategoriesFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.category.CategoriesModule
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.AddFeedFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.AddFeedModule
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.FeedsFragment
import feedrss.dev.aporia.com.rssfeed.ui.main.feed.FeedsModule
import feedrss.dev.aporia.com.rssfeed.ui.main.post.*

@Module
abstract class MainFragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostsModule::class])
    abstract fun providePostsFragment(): PostsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostsModule::class])
    abstract fun providesBookmarsFragment(): BookmarksFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FeedsModule::class])
    abstract fun providesFeedsFragment(): FeedsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [AddFeedModule::class])
    abstract fun providesAddFeedFragment(): AddFeedFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CategoriesModule::class])
    abstract fun providesCategoriesFragment(): CategoriesFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostDetailsModule::class])
    abstract fun providePostDetailsFragment(): PostDetailsFragment
}
