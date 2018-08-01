package feedrss.dev.aporia.com.rssfeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(@PrimaryKey var id: String,
                var title: String,
                var description: String,
                var url: String,
                var bookmarked: Boolean = false,
                var read: Boolean = false)