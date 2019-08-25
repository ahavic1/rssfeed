package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository
import feedrss.dev.aporia.com.rssfeed.di.Schedulers
import javax.inject.Inject

class AddFeedViewModel @Inject constructor(
    private var feedRepository: FeedRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    fun saveFeed(title: String, url: String, refreshInterval: String) {
    }
}