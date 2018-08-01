package feedrss.dev.aporia.com.rssfeed.viewmodel

import androidx.lifecycle.MutableLiveData
import feedrss.dev.aporia.com.rssfeed.AppError
import feedrss.dev.aporia.com.rssfeed.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.SchedulersWrapper
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository
import feedrss.dev.aporia.com.rssfeed.extensions.disposeWith
import feedrss.dev.aporia.com.rssfeed.extensions.uiSubscribe

class FeedsViewModel(private var feedRepository: FeedRepository,
                     schedulers: SchedulersWrapper): BaseViewModel(schedulers) {


    var feedsObservable = MutableLiveData<List<Feed>>()
    var errorObservable = MutableLiveData<AppError>()


    init {
        getFeeds()
    }

    private fun getFeeds() {
        feedRepository.getFeeds().uiSubscribe({
            feedsObservable.value = it
        }, errorObservable, schedulers).disposeWith(disposeBag)
    }



}