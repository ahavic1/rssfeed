package feedrss.dev.aporia.com.rssfeed.data.repository

import feedrss.dev.aporia.com.rssfeed.data.dao.PostDao
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle

class PostRepository(private var postDao: PostDao) {

    fun getPosts(): Single<List<Post>> //= postDao.load()
    {
        val postsArray = ArrayList<Post>()
        for (i in 1..10) {
            val index = i.toString()
            postsArray.add(Post(index, "title $index", "description description " +
                    "description description description description v description v v v v v v " +
                    "vdescription description description description description description description " +
                    "description description description description description description  $index", "url $index",
                    bookmarked = i%2 == 0))
        }
        return postsArray.toSingle()
    }

    fun getBookmarkedPosts(): Single<List<Post>> {
        return getPosts().flattenAsObservable {
            it
        }.filter {
            it.bookmarked
        }.toList()

    }

    fun bookmarkPost(id: String): Observable<Unit> = Observable.fromCallable { postDao.bookmark(id) }

    fun markPostRead(id: String): Observable<Unit> = Observable.fromCallable { postDao.setRead(id) }

    fun unBookmarkPost(id: String): Observable<Unit> = Observable.fromCallable { postDao.unBookmark(id) }
}