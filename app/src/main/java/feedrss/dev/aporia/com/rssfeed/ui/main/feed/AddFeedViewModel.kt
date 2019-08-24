package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.ui.base.Schedulers
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository

class AddFeedViewModel(
    private var feedRepository: FeedRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    fun saveFeed(title: String, url: String, refreshInterval: String) {
    }
}