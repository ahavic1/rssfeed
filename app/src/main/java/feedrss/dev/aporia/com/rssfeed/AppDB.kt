package feedrss.dev.aporia.com.rssfeed

import androidx.room.Database
import androidx.room.RoomDatabase
import feedrss.dev.aporia.com.rssfeed.data.dao.FeedDao
import feedrss.dev.aporia.com.rssfeed.data.dao.PostDao
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.model.Post

@Database(entities = [Post::class, Feed::class], version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun feedDao(): FeedDao
}