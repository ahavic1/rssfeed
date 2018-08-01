package feedrss.dev.aporia.com.rssfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Feed
import feedrss.dev.aporia.com.rssfeed.extensions.obtainViewModel
import feedrss.dev.aporia.com.rssfeed.viewmodel.FeedsViewModel

class FeedsFragment: BaseFragment() {

    private lateinit var viewModel: FeedsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.feeds_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = obtainViewModel(FeedsViewModel::class.java).apply {

            feedsObservable.observe(this@FeedsFragment, Observer {
                populateLayout(it)
            })

            errorObservable.observe(this@FeedsFragment, Observer {
                it?.let { onError(it) }
            })
        }

    }

    private fun populateLayout(it: List<Feed>?) {
        it?.let {
            view?.findViewById<LinearLayout>(R.id.container)!!.apply {
                it.forEach {
                    val textView = TextView(activity)
                    textView.text = it.title
                    textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
                    addView(textView)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FeedsFragment()
    }
}