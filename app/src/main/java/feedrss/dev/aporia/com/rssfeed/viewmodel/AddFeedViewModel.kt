package feedrss.dev.aporia.com.rssfeed.viewmodel

import feedrss.dev.aporia.com.rssfeed.BaseViewModel
import feedrss.dev.aporia.com.rssfeed.SchedulersWrapper
import feedrss.dev.aporia.com.rssfeed.data.repository.FeedRepository

class AddFeedViewModel(private var feedRepository: FeedRepository,
                       schedulers: SchedulersWrapper): BaseViewModel(schedulers) {


    fun saveFeed(title: String, url: String, refreshInterval: String) {

    }



}