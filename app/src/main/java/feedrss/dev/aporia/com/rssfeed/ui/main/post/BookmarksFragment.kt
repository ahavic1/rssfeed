package feedrss.dev.aporia.com.rssfeed.ui.main.post

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxSearchView
import feedrss.dev.aporia.com.rssfeed.R
import feedrss.dev.aporia.com.rssfeed.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_posts.recyclerView
import kotlinx.android.synthetic.main.fragment_posts.searchView
import kotlinx.android.synthetic.main.fragment_posts.swipeRefreshLayout

class BookmarksFragment: BaseFragment<PostsViewModel>() {

    private val postsAdapter by lazy {
        PostAdapter(viewModel, ArrayList(0))
    }

    override val layoutId: Int
        get() = R.layout.fragment_posts
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

    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }
}