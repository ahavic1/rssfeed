package feedrss.dev.aporia.com.rssfeed.data.dao

import androidx.room.Dao
import androidx.room.Query
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import io.reactivex.Single

@Dao
abstract class PostDao: BaseDao<Post> {

    @Query("SELECT * FROM post")
    abstract fun load(): Single<List<Post>>

    @Query("UPDATE post SET bookmarked = 1 WHERE id = :id")
    abstract fun bookmark(id: String)

    @Query("UPDATE post SET bookmarked = 0 WHERE id = :id")
    abstract fun unBookmark(id: String)

    @Query("UPDATE post SET read = 1 WHERE id = :id")
    abstract fun setRead(id: String)
}