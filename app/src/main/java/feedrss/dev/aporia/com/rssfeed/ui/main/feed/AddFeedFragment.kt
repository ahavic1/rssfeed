package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelKey

class AddFeedFragment: BaseFragment<AddFeedViewModel>() {
    override val layoutId: Int
        get() = R.layout.feeds_fragment_layout
    override val viewModelClass: Class<AddFeedViewModel>
        get() = AddFeedViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedsFragment()
    }
}

@Module
abstract class AddFeedModule {
    @Binds
    @IntoMap
    @ViewModelKey(FeedsViewModel::class)
    abstract fun provideAddFeedViewModel(viewModel: AddFeedViewModel): ViewModel
}
