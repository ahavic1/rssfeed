package feedrss.dev.aporia.com.rssfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.viewmodel.AddFeedViewModel

class AddFeedFragment: BaseFragment<AddFeedViewModel>() {
    override val layoutId: Int
        get() = R.layout.feeds_fragment_layout
    override val viewModelClass: Class<AddFeedViewModel>
        get() = AddFeedViewModel::class.java

    override fun bindViewModel() {
    }
}
