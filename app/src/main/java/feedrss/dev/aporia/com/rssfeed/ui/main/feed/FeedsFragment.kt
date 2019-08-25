package feedrss.dev.aporia.com.rssfeed.ui.main.feed

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import feedrss.dev.aporia.com.rssfeed.ui.base.ViewModelKey

class FeedsFragment: BaseFragment<FeedsViewModel>() {

    override val layoutId: Int
        get() = R.layout.feeds_fragment_layout
    override val viewModelClass: Class<FeedsViewModel>
        get() = FeedsViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
        viewModel.feedsObservable.observe(viewLifecycleOwner, Observer { feeds ->
            val feedsContainer = view?.findViewById<LinearLayout>(R.id.container)!!
            feeds?.forEach {
                val textView = TextView(activity)
                textView.text = it.title
                textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
                feedsContainer.addView(textView)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedsFragment()
    }
}

@Module
abstract class FeedsModule {
    @Binds
    @IntoMap
    @ViewModelKey(FeedsViewModel::class)
    abstract fun provideFeedsViewModel(viewModel: FeedsViewModel): ViewModel
}