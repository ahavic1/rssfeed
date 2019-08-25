package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository
import feedrss.dev.aporia.com.rssfeed.di.Schedulers
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import javax.inject.Inject

class FeedsViewModel @Inject constructor(
    private var feedRepository: FeedRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    var feedsObservable = MutableLiveData<List<Feed>>()

    init {
        fetchFeeds()
    }

    private fun fetchFeeds() {
        feedRepository.getFeeds().uiSubscribe {
            feedsObservable.value = it
        }
    }
}