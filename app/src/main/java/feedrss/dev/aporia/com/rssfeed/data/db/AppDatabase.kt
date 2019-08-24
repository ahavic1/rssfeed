package feedrss.dev.aporia.com.rssfeed.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedDao
import feedrss.dev.aporia.com.rssfeed.data.repository.PostDao

@Database(entities = [Post::class, Feed::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun feedDao(): FeedDao
}