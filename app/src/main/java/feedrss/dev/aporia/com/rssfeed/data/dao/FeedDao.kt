package feedrss.dev.aporia.com.rssfeed.data.dao

import androidx.room.Dao
import androidx.room.Query
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import io.reactivex.Single

@Dao
abstract class FeedDao: BaseDao<Feed> {

    @Query("SELECT * FROM feed")
    abstract fun load(): Single<List<Feed>>

    @Query("SELECT * FROM feed WHERE id = :id")
    abstract fun loadById(id: String): Single<Feed>

    @Query("UPDATE feed SET refreshInterval = :refreshInterval WHERE id = :id")
    abstract fun updateRefreshInterval(id: String, refreshInterval: Int)
}