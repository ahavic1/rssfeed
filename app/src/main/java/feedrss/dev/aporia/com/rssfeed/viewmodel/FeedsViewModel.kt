package feedrss.dev.aporia.com.rssfeed.viewmodel

import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.*
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository

class FeedsViewModel(
    private var feedRepository: FeedRepository,
    schedulers: Schedulers
) : BaseViewModel(schedulers) {

    var feedsObservable = MutableLiveData<List<Feed>>()
    var errorObservable = MutableLiveData<AppError>()


    init {
        fetchFeeds()
    }

    private fun fetchFeeds() {
        feedRepository.getFeeds().uiSubscribe({
            feedsObservable.value = it
        }, errorObservable)
    }
}