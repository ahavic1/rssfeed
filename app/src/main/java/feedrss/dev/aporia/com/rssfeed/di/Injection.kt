package feedrss.dev.aporia.com.rssfeed.di

import android.content.Context
import feedrss.dev.aporia.com.rssfeed.Dependencies
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository
import feedrss.dev.aporia.com.rssfeed.data.repository.PostRepository

object Injection {

    fun providePostRepository(context: Context): PostRepository {
        val database = Dependencies.getDatabase(context)
        return PostRepository(database.postDao())
    }

    fun provideFeedRepository(context: Context): FeedRepository {
        val database = Dependencies.getDatabase(context)
        return FeedRepository(Dependencies.getRetrofit(), database.postDao(), database.feedDao())
    }
}