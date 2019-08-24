package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository
import feedrss.dev.aporia.com.rssfeed.ui.base.AppError
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.ui.base.Schedulers

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