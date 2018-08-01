package feedrss.dev.aporia.com.rssfeed.data.repository

import feedrss.dev.aporia.com.rssfeed.data.dao.FeedDao
import feedrss.dev.aporia.com.rssfeed.data.dao.PostDao
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.data.network.WebService
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import me.toptas.rssconverter.RssItem

class FeedRepository(var webService: WebService,
                     var postDao: PostDao,
                     var feedDao: FeedDao) {

    private val postMapper: (RssItem) -> Post = {
        Post(id = it.link, title = it.title, description = it.description, url = it.link)
    }

    fun getFeeds(): Single<List<Feed>> //= feedDao.load()
    {
        val postsArray = ArrayList<Feed>()
        for (i in 1..10) {
            val index = i.toString()
            postsArray.add(Feed(index, "title $index", "description description $index",
                    "url $index", ""))
        }
        return postsArray.toSingle()
    }

    fun saveFeed(feed: Feed): Single<Unit>  {
        return webService.getRss(feed.url)
                .map {
                    it.items?.let {
                        feedDao.save(feed)
                        postDao.save(it.map(postMapper))
                    }!!
                }
    }

    fun deleteFeed(id: String): Single<Unit> {
        return feedDao.loadById(id)
                .map {
                    feedDao.delete(it)
                }
    }

    fun fetchFeed(id: String): Single<Unit> {
        return feedDao.loadById(id)
                .flatMap {
                    webService.getRss(it.url)
                }
                .map {
                    postDao.save(it.items?.map(postMapper)!!)
                }
    }
}
