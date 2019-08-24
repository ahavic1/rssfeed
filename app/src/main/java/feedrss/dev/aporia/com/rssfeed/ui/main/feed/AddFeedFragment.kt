package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment

class AddFeedFragment: BaseFragment<AddFeedViewModel>() {
    override val layoutId: Int
        get() = R.layout.feeds_fragment_layout
    override val viewModelClass: Class<AddFeedViewModel>
        get() = AddFeedViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
    }
}
