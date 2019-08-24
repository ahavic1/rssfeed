package feedrss.dev.aporia.com.rssfeed.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.viewmodel.FeedsViewModel

class FeedsFragment: BaseFragment<FeedsViewModel>() {

    override val layoutId: Int
        get() = R.layout.feeds_fragment_layout
    override val viewModelClass: Class<FeedsViewModel>
        get() = FeedsViewModel::class.java

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

        viewModel.errorObservable.observe(this@FeedsFragment, Observer {
            it?.let { onError(it) }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedsFragment()
    }
}