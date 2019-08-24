package feedrss.dev.aporia.com.rssfeed.ui.main.post

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxSearchView
import feedrss.dev.aporia.com.rssfeed.ui.base.OnFragmentInteractionListener
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.data.model.Post
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.recyclerView
import kotlinx.android.synthetic.main.fragment_list.searchView
import kotlinx.android.synthetic.main.fragment_list.swipeRefreshLayout

class BookmarksFragment: BaseFragment<PostsViewModel>() {

    private var listener: OnFragmentInteractionListener? = null
    private val postsAdapter by lazy {
        PostAdapter(viewModel, ArrayList(0))
    }

    override val layoutId: Int
        get() = R.layout.fragment_list
    override val viewModelClass: Class<PostsViewModel>
        get() = PostsViewModel::class.java
    override val viewModeRId: Int
        get() = 0

    override fun bindViewModel() {
        setupUI()

        viewModel.bookmarkedPosts.observe(viewLifecycleOwner, Observer {
            it?.let {
                swipeRefreshLayout.isRefreshing = false
                postsAdapter.update(it)
            }
        })

        viewModel.errorObservable.observe(viewLifecycleOwner, Observer {
            it?.let { onError(it) }
        })
    }

    @SuppressLint("CheckResult")
    private fun setupUI() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = postsAdapter
        }

        swipeRefreshLayout.isEnabled = false

        RxSearchView.queryTextChangeEvents(searchView)
            .skipWhile {
                it.queryText().length < 3
            }.subscribe {
                viewModel.searchPosts(it.queryText())
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }
}