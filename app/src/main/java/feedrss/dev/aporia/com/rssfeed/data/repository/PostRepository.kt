package feedrss.dev.aporia.com.rssfeed.data.repository

import feedrss.dev.aporia.com.rssfeed.data.dao.PostDao
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle

class PostRepository(private var postDao: PostDao) {

    fun getPosts(): Single<List<Post>> {
        val posts = mutableListOf<Post>()
        for (i in 1..10) {
            val index = i.toString()
            posts.add(Post(index, "title $index", "description description " +
                    "description description description description v description v v v v v v " +
                    "vdescription description description description description description description " +
                    "description description description description description description  $index", "url $index",
                    bookmarked = i%2 == 0))
        }
        return posts.toSingle()
    }

    fun bookmarkPost(id: String): Observable<Unit> = Observable.fromCallable { postDao.bookmark(id) }

    fun markPostRead(id: String): Observable<Unit> = Observable.fromCallable { postDao.setRead(id) }

    fun unBookmarkPost(id: String): Observable<Unit> = Observable.fromCallable { postDao.unBookmark(id) }
}