package feedrss.dev.aporia.com.rssfeed.data.repository

import androidx.room.Dao
import androidx.room.Query
import dagger.Binds
import dagger.Module
import dagger.Provides
import feedrss.dev.aporia.com.rssfeed.data.db.AppDatabase
import feedrss.dev.aporia.com.rssfeed.data.db.BaseDao
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface PostRepository {
    fun getPosts(): Single<List<Post>>
    fun getPost(postId: String): Single<Post>
    fun bookmarkPost(id: String): Observable<Unit>
    fun markPostRead(id: String): Observable<Unit>
    fun unBookmarkPost(id: String): Observable<Unit>
    fun populateDB(): Observable<Unit>
}

class PostRepositoryImpl @Inject constructor(
    private val postDao: PostDao
) : PostRepository {

    override fun getPosts(): Single<List<Post>> = postDao.getPosts()

    override fun getPost(postId: String): Single<Post> = postDao.getPost(postId)

    override fun bookmarkPost(id: String): Observable<Unit> =
        Observable.fromCallable { postDao.bookmark(id) }

    override fun markPostRead(id: String): Observable<Unit> =
        Observable.fromCallable { postDao.setRead(id) }

    override fun unBookmarkPost(id: String): Observable<Unit> =
        Observable.fromCallable { postDao.unBookmark(id) }

    override fun populateDB(): Observable<Unit> {
        val posts = mutableListOf<Post>()
        for (i in 1..10) {
            val index = i.toString()
            posts.add(Post(index,
                "title $index",
                "description description " +
                    "description description description description v description v v v v v v " +
                    "vdescription description description description description description description " +
                    "description description description description description description  $index",
                "url $index",
                bookmarked = i % 2 == 0
            )
            )
        }
        return Observable.fromCallable { postDao.save(posts) }
    }
}

@Dao
abstract class PostDao : BaseDao<Post> {

    @Query("SELECT * FROM post")
    abstract fun getPosts(): Single<List<Post>>

    @Query("SELECT * FROM post WHERE id = :postId")
    abstract fun getPost(postId: String): Single<Post>

    @Query("UPDATE post SET bookmarked = 1 WHERE id = :postId")
    abstract fun bookmark(postId: String)

    @Query("UPDATE post SET bookmarked = 0 WHERE id = :postId")
    abstract fun unBookmark(postId: String)

    @Query("UPDATE post SET read = 1 WHERE id = :postId")
    abstract fun setRead(postId: String)
}

@Module
abstract class PostDataModule {

    @Binds
    @Singleton
    abstract fun providePostRepository(repository: PostRepositoryImpl): PostRepository

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun providePostDao(appDatabase: AppDatabase): PostDao = appDatabase.postDao()
    }
}