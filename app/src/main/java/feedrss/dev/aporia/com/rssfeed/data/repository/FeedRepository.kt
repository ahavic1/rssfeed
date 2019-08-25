package feedrss.dev.aporia.com.rssfeed.data.repository

import androidx.room.Dao
import androidx.room.Query
import dagger.Binds
import dagger.Module
import dagger.Provides
import feedrss.dev.aporia.com.rssfeed.data.db.AppDatabase
import feedrss.dev.aporia.com.rssfeed.data.db.BaseDao
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.network.WebService
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import me.toptas.rssconverter.RssItem
import javax.inject.Inject
import javax.inject.Singleton

interface FeedRepository {
    fun getFeeds(): Single<List<Feed>>
    fun fetchFeed(id: String): Single<Unit>
    fun saveFeed(feed: Feed): Single<Unit>
    fun deleteFeed(id: String): Single<Unit>
}

class FeedRepositoryImpl @Inject constructor(
    private val webService: WebService,
    private val postDao: PostDao,
    private val feedDao: FeedDao
): FeedRepository {

    private val postMapper: (RssItem) -> Post = {
        Post(id = it.link, title = it.title, description = it.description, url = it.link)
    }

    override fun getFeeds(): Single<List<Feed>> //= feedDao.getPosts()
    {
        val postsArray = ArrayList<Feed>()
        for (i in 1..10) {
            val index = i.toString()
            postsArray.add(Feed(index, "title $index", "description description $index",
                "url $index", ""
            )
            )
        }
        return postsArray.toSingle()
    }

    override fun saveFeed(feed: Feed): Single<Unit> {
        return webService.getRss(feed.url)
            .map {
                it.items?.let {
                    feedDao.save(feed)
                    postDao.save(it.map(postMapper))
                }!!
            }
    }

    override fun deleteFeed(id: String): Single<Unit> {
        return feedDao.getFeed(id)
            .map {
                feedDao.delete(it)
            }
    }

    override fun fetchFeed(id: String): Single<Unit> {
        return feedDao.getFeed(id)
            .flatMap {
                webService.getRss(it.url)
            }
            .map {
                postDao.save(it.items?.map(postMapper)!!)
            }
    }
}

@Dao
abstract class FeedDao : BaseDao<Feed> {

    @Query("SELECT * FROM feed")
    abstract fun getFeeds(): Single<List<Feed>>

    @Query("SELECT * FROM feed WHERE id = :feedId")
    abstract fun getFeed(feedId: String): Single<Feed>

    @Query("UPDATE feed SET refreshInterval = :refreshInterval WHERE id = :feedId")
    abstract fun updateRefreshInterval(feedId: String, refreshInterval: Int)
}

@Module
abstract class FeedDataModule {

    @Binds
    @Singleton
    abstract fun provideFeedRepository(repository: FeedRepositoryImpl): FeedRepository

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideFeedDao(appDatabase: AppDatabase): FeedDao = appDatabase.feedDao()
    }
}
