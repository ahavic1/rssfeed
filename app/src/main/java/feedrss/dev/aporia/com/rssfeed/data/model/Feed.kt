package feedrss.dev.aporia.com.rssfeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feed(@PrimaryKey var id: String,
                var title: String,
                var url: String,
                var refreshInterval: String,
                var lastRefreshTime: String) {
}